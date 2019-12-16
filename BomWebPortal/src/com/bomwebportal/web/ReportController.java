package com.bomwebportal.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.service.ReportService;
import com.bomwebportal.service.ReportService.Form;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.ProjectEagleReportHelper;
import com.bomwebportal.web.util.ReportHelper;
import com.bomwebportal.web.util.ReportSessionName;

public class ReportController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());

	private ReportService service;
	private ReportHelper reportHelper;
	private GenericReportHelper genericReportHelper;

	public ReportService getService() {
		return service;
	}
	public void setService(ReportService service) {
		this.service = service;
	}	

	public ReportHelper getReportHelper() {
		return reportHelper;
	}
	public void setReportHelper(ReportHelper reportHelper) {
		this.reportHelper = reportHelper;
	}
	
	public GenericReportHelper getGenericReportHelper() {
		return genericReportHelper;
	}
	public void setGenericReportHelper(GenericReportHelper genericReportHelper) {
		this.genericReportHelper = genericReportHelper;
	}

	public ReportController() {
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("onSubmit is called");
		try {
			
			String pdfLang = request.getParameter("pdfLang");
			String printFrom = request.getParameter("printFrom");
			String nfcInd = request.getParameter("nfcInd");
			String genericFormName = request.getParameter("genericForm");
			String formName = request.getParameter("formName");

			Form form = null;
			if ("LDS".equalsIgnoreCase(request.getParameter("iGuardType"))) {
				form = Form.IGUARD_LDS;
			}else if ("AD".equalsIgnoreCase(request.getParameter("iGuardType"))) {
				form = Form.IGUARD_AD;
			} else if ("Y".equalsIgnoreCase(request.getParameter("mobileSafetyPhone"))) {
				form = Form.MOBILE_SAFETY_PHONE;
			} else if ("Y".equalsIgnoreCase(request.getParameter("nfcSim"))) {
				form = Form.NFC_SIM;
			} else if ("Y".equalsIgnoreCase(request.getParameter("octopusSim"))) {
				form = Form.OCTOPUS_SIM;
			} else if (StringUtils.isNotBlank(nfcInd) && !"0".equals(nfcInd)) {
				form = Form.NFC_SIM_SET;
			} else if ("CARECASH".equalsIgnoreCase(request.getParameter("iGuardType"))){
				form = Form.IGUARD_CARECASH;
			}else if ("UAD".equalsIgnoreCase(request.getParameter("iGuardType"))){
				form = Form.IGUARD_UAD;
			} else if (StringUtils.isNotBlank(genericFormName)) {
				form = genericReportHelper.getGenericForm(genericFormName);
			} else if (StringUtils.equalsIgnoreCase(formName, ProjectEagleReportHelper.FORM_NAME)) {
				form = Form.PROJECT_EAGLE_FORM;
			}
			ReportSessionName reportSessionName = "orderdetail".equals(printFrom) ? ReportSessionName.ORDER_DETAIL : ReportSessionName.SIGNOFF;

			List<Object> allDtoList = reportHelper.getData(request, reportSessionName, pdfLang, form);
			
			OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute(reportSessionName.getOrderDtoName());

			if (orderDto == null) {
				throw new IOException("Order session is missing");
			}
			
			CustomerProfileDTO  customerProfileDTO = (CustomerProfileDTO)request.getSession().getAttribute(reportSessionName.getCustomerDtoName());
			if (customerProfileDTO == null) {
				throw new IOException("CustomerProfile session is missing");
			}
			
			String orderId = orderDto.getOrderId();
			String msisdn = orderDto.getMsisdn();
			String pdfName = orderDto.getOrderId() + ("zh".equals(pdfLang) ? "_CHI.pdf" : "_EN.pdf");
			boolean isSeparateForm = (form != null);
			
			if (isSeparateForm) {
				switch (form) {
					case IGUARD_LDS:
						pdfName = orderDto.getMsisdn() + "_" + orderDto.getiGuardSerialNo() + "_LDS" + ".pdf";
						break;
					case IGUARD_AD:
						pdfName = orderDto.getMsisdn() + "_AD" + ".pdf";
						break;
					case MOBILE_SAFETY_PHONE:
						pdfName = this.reportHelper.getMobileSafetyPhoneFormsFileName(orderDto.getOrderId());
						break;
					case NFC_SIM:
					case NFC_SIM_SET:
						pdfName = this.reportHelper.getNFCSimFormsFileName(orderDto.getOrderId());
						break;
					case OCTOPUS_SIM:
						pdfName = this.reportHelper.getOctopusSimFormsFileName(orderDto.getOrderId());
						break;
					case IGUARD_CARECASH:
						pdfName = this.reportHelper.getIGuardCareCashFormsFileName(orderDto.getOrderId(), pdfLang);
						break;
						
					case IGUARD_UAD:
						pdfName = orderDto.getOrderId()+ "_UAD" + ".pdf";
						break;
						
					case PROJECT_EAGLE_FORM:
						pdfName = ProjectEagleReportHelper.getFileName(orderId, msisdn, form);
						break;
						
					default:
						pdfName = genericReportHelper.getGenericFormFileName(orderId, msisdn, form);
						break;
				}
			}
			
			response.setContentType("application/pdf");
			response.addHeader("Content-disposition","attachment; filename=" + pdfName + "; charset=UTF-8");
			response.setHeader("Cache-Control", "private");
			OutputStream ouputStream = response.getOutputStream();
			
			if (!isSeparateForm) {
				service.generatePdfReports(allDtoList, ouputStream, pdfLang, orderDto.getOrderId(), customerProfileDTO.getBrand());
			} else {
				service.generatePdfReports(allDtoList, ouputStream, pdfLang, orderDto.getOrderId(), Arrays.asList(form), customerProfileDTO.getBrand());
			}
			ouputStream.flush();

			logger.info("onSubmit <end>");

		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			logger.error("Exception in ReportController", ioEx);

			response.resetBuffer();
			response.setContentType("text/html");
			response.setHeader("Content-Disposition", null);
			response.getWriter().println(
					"Exception in ReportController " + ioEx.toString());
		}

		return new ModelAndView();
	}

}
