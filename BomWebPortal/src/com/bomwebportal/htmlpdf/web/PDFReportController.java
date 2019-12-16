package com.bomwebportal.htmlpdf.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.util.XRLog;

import com.lowagie.text.pdf.BaseFont;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.htmlpdf.BarcodeReplacedElementFactory;
import com.bomwebportal.htmlpdf.FontFaceInfo;
import com.bomwebportal.htmlpdf.IPDFReport;
import com.bomwebportal.htmlpdf.PDFReportList;
import com.bomwebportal.htmlpdf.ResourceUserAgent;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.bomwebportal.util.PdfUtil;
import com.bomwebportal.web.util.ReportRepository;

@Controller
public class PDFReportController {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	@Qualifier("viewResolver")
	private ViewResolver viewResolver;
	
	@Autowired
	private ReportRepository reportRepository;
	
	@RequestMapping("/pdfreport")
	public void createPDF(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		XRLog.setLoggingEnabled(true);
		logger.info("Start create HTML to PDF @ PDFReportController");
		String filename = request.getParameter("filename");
		String watermark = request.getParameter("wm");
		boolean inline = "true".equals(request.getParameter("inline"));
		boolean save = "true".equals(request.getParameter("save"));
		String filepath = request.getParameter("filepath");
		filename = (StringUtils.isEmpty(filename) ? "report.pdf" : filename);
		boolean isAddpageNumber = !"false".equals(request.getParameter("addPageNum")); //default empty = true
		
		String reportName = request.getParameter("name");
		
		if (StringUtils.isEmpty(reportName)) {
			throw new AppRuntimeException("Missing report name");
		}
		
		if (save && StringUtils.isEmpty(filepath)) {
			throw new AppRuntimeException("Missing file path");
		}
		
		ArrayList<IPDFReport> handlers = new ArrayList<IPDFReport>();
		
		resolveReportBeans(handlers, reportName, request);
		
		ArrayList<InputStream> reportStreams = new ArrayList<InputStream>();
		
		for (IPDFReport handler : handlers) {
			request.removeAttribute("__PDF_FONT_FACES__");
			String html = renderHtml(handler, request, response, model);
			InputStream reportStream = renderReportAsStream(request, html);
			reportStreams.add(reportStream);
		}
		
		response.setContentType("application/pdf");
		if (inline) {
			response.addHeader("Content-disposition", "inline; filename=" + filename);
		} else {
			response.addHeader("Content-disposition", "attachment; filename=" + filename);
		}
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "private");
		
		FastByteArrayOutputStream baosMerged = new FastByteArrayOutputStream();
		PdfUtil.concatPDFs(reportStreams, baosMerged, isAddpageNumber, false, watermark, null, null, null, null);
		byte[] pdfData = baosMerged.getByteArray();
		if (save) {
			logger.debug("filepath = " + filepath + ", filename = " + filename);
			reportRepository.savaFile(filepath, filename, pdfData);
			IOUtils.closeQuietly(baosMerged);
		} else {
			OutputStream os = response.getOutputStream();
			os.write(pdfData);
			os.flush();
			IOUtils.closeQuietly(baosMerged);
			IOUtils.closeQuietly(os);
		}
		logger.info("End create HTML to PDF @ PDFReportController");
	}
	
	private List<IPDFReport> resolveReportBeans(List<IPDFReport> beans, String name, HttpServletRequest request) {
		IPDFReport bean = getReportBean(name);
		if (bean instanceof PDFReportList) {
			List<String> seq = ((PDFReportList) bean).getReports(request);
			for (String rn : seq) {
				resolveReportBeans(beans, rn, request);
			}
			
		} else {
			beans.add(bean);
		}
		return beans;
	}
	
	private IPDFReport getReportBean(String name) {
		return (IPDFReport)context.getBean("pdf." + name, IPDFReport.class);
	}
	
	
	private String renderHtml(IPDFReport handler, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		MockHttpServletResponse mockRes = new MockHttpServletResponse();
		mockRes.setCharacterEncoding(response.getCharacterEncoding());
		String viewName = handler.getView(request);
		
		Map<String, Object> data = new HashMap<String,Object>();
		data.putAll(model.asMap());
		handler.fillModel(request, data);
		View view = viewResolver.resolveViewName(viewName, response.getLocale());
		view.render(data, request, mockRes);
		
		if (mockRes.getStatus() != 200) {
			throw new AppRuntimeException(
					"Failed to render report view : " + viewName
					+ " status: "+mockRes.getStatus()
					+ " error:" + mockRes.getErrorMessage());
		}
		
		//System.out.println("status = " + mockRes.getStatus());
		return mockRes.getContentAsString();
		
	}
	
	private InputStream renderReportAsStream(HttpServletRequest req, String content) throws Exception {
		
		List<FontFaceInfo> ffs = (List<FontFaceInfo>)req.getAttribute("__PDF_FONT_FACES__");
		
		URI currentUri = new URI(req.getRequestURI());

		ITextRenderer renderer = new ITextRenderer();
		SharedContext sc = renderer.getSharedContext();
		sc.setReplacedElementFactory(new BarcodeReplacedElementFactory(
				renderer.getOutputDevice()));
		ResourceUserAgent callback = new ResourceUserAgent(renderer.getOutputDevice(), context, req);

		callback.setSharedContext(renderer.getSharedContext());
		renderer.getSharedContext().setUserAgentCallback(callback);
		

		ITextFontResolver resolver = renderer.getFontResolver();
		//resolver.addFont("C:\\WINDOWS\\FONTS\\ARIAL.TTF", true);
		
		//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.pccw.com", 8080));
		//Jsoup.
		Document doc = Jsoup.parse(content);
		doc.outputSettings().charset("UTF-8");
		//System.out.println(doc.toString());
		
		renderer.setDocumentFromString(doc.toString());


		try {
			//resolver.addFont("C:/Windows/Fonts/msjh.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//resolver.addFont("C:/Windows/Fonts/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			if (CollectionUtils.isNotEmpty(ffs)) {
				for (FontFaceInfo ffi: ffs) {
					Resource fr = context.getResource(ffi.getUri());
					//System.out.println("ffffff =>" + fr.getURI());
					String enc = ffi.getFontEncoding();
					if (StringUtils.isEmpty(enc)) enc = BaseFont.CP1250;
					resolver.addFont(fr.getURI().toString(), ffi.getFontFamily(), enc, ffi.isEmbedded(), null);
				}
			}
			renderer.layout();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			renderer.createPDF(baos);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			
			return bais;
		} catch (Exception e) {
			logger.error("Error while rendering", e);
			throw e;
		}
	}
	

}
