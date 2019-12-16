package com.bomwebportal.web.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.util.PdfHeaderFooter;
import com.bomwebportal.util.PdfUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.GifImage;
import com.lowagie.text.pdf.codec.TiffImage;

public class ReportRepository {

	protected final Log logger = LogFactory.getLog(getClass());

	private String dataFilePath;
	private ReportHelper reportHelper;

	public String getDataFilePath() {
		return dataFilePath;
	}
	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public ReportHelper getReportHelper() {
		return reportHelper;
	}
	
	public void setReportHelper(ReportHelper reportHelper) {
		this.reportHelper = reportHelper;
	}
	
	public File getSignedForms(String orderId) {

		File f =  getSignedForms(orderId, "zh");
		return (f != null) ? f : getSignedForms(orderId, "en");
	}

	public File getSignedForms(String orderId, String lang) {

		if (StringUtils.isBlank(orderId)) return null;

		String path;
		if (StringUtils.startsWithIgnoreCase(lang, "zh")) {
			path = getDataFilePath() + "/" + orderId + "/" + orderId + "_CHI.pdf";
		} else {
			path = getDataFilePath() + "/" + orderId + "/" + orderId + "_EN.pdf";
		}

		File f = new File(path);
		return (f.canRead()) ? f : null;

	}

	public boolean isSignedFromsExist(String orderId) {
		return (getSignedForms(orderId) != null);
	}


	// return list of files merged ...
	public File[] generateMergedPDF(File dest, String orderId) throws Exception {
		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(dest));
		File files[] = generateMergedPDF(os, orderId);
		IOUtils.closeQuietly(os);
		if (dest.length() == 0) {
			FileUtils.deleteQuietly(dest);
			return null;
		}
		return files;
	}
	
	// return list of files merged ...
	public File[] generateMergedPDF(OutputStream out, String orderId) throws Exception {
		File[] files = getAllFiles(orderId);
		if (files == null || files.length == 0) {
			out.close();
			return null;
		}

		List<InputStream>  iss = new ArrayList<InputStream>();
		
		
		for (File f: files) {
			iss.add(new FileInputStream(f));
		}
		
		/*
		PdfHeaderFooter hf = new PdfHeaderFooter();
		hf.setFooterText(orderId);
		hf.setFooterAlign(PdfHeaderFooter.ALIGN_LEFT);

		PdfUtil.concatPDFs(iss, out, false, hf);
		*/
		
		PdfUtil.concatPDFs(iss, out, false);
		
		return files;

	}
	
	
	public void convertFile(File dest, File src) throws Exception {
		convertFile(dest, dest, dest.getName());
	}
	

	public void convertFile(File dest, File src, String desc) throws Exception {

		logger.debug("Converting uploaded document file to PDF format");
		String srcPath = src.getCanonicalPath();

		Document doc = new Document(PageSize.A4);
		String ext = getFileExtension(srcPath);

		
		PdfHeaderFooter hf = new PdfHeaderFooter();
		hf.setHeaderText(desc != null ? desc : dest.getName());

			
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(dest));
		writer.setPageEvent(hf);
		
		doc.open();
		
		if ("pdf".equalsIgnoreCase(ext)) {
			addPdf(doc, writer, srcPath);
		} else if ("tif".equalsIgnoreCase(ext) || "tiff".equalsIgnoreCase(ext)) {
			addTif(doc, srcPath);
		} else if ("gif".equalsIgnoreCase(ext)) {
			addGif(doc, srcPath);
		} else {
			addImg(doc, srcPath);
		}			


		
		doc.close();

		logger.debug("File format Conversion done");
	}
	
	@Deprecated
	public void copyPdf(PdfCopy copy, String path) throws Exception {
		PdfReader reader = new PdfReader(new FileInputStream(path));
		int n = reader.getNumberOfPages();
		for (int i=1; i <= n; i++) {
			copy.addPage(copy.getImportedPage(reader, i));
		}
		copy.freeReader(reader);
	}

	// pdf -> pdf .. it is dummy.... for adding header only ..
	public void addPdf(Document doc, PdfWriter writer, String path) throws Exception {
		
		
		PdfContentByte cb = writer.getDirectContent();
		PdfReader reader = new PdfReader(new FileInputStream(path));
		int n = reader.getNumberOfPages();
		for (int i=1; i <= n; i++) {
			doc.newPage();

			PdfImportedPage page = writer.getImportedPage(reader, i);
			
			//float dw = doc.right() - doc.left() + 1;
			//float dh = doc.top() - doc.bottom() + 1;
			
			// http://stackoverflow.com/questions/5345067/itextsharp-how-to-rotate-switch-page-from-landscape-to-portrait
			
			
			
			float w = reader.getPageSize(i).getWidth();
			float h = reader.getPageSize(i).getHeight();
			int r = reader.getPageRotation(i);
			switch (r) {
			case 0:
				cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
				break;
			case 90:
				cb.addTemplate(page,  0, -1f, 1f, 0, 0, h);
				break;
			case 180:
				cb.addTemplate(page,  -1f,  0, 0, -1f, 0, 0);
				break;
			case 270:
				cb.addTemplate(page, 0, 1.0f, -1.0f, 0, h, 0);
				break;
				
			}

		}	
	}
	
	
	public void addTif(Document document, String path) throws Exception {
		RandomAccessFileOrArray ra = new RandomAccessFileOrArray(path);
		int n = TiffImage.getNumberOfPages(ra);
		Image img;
		for (int i = 1; i <= n; i++) {
			img = TiffImage.getTiffImage(ra, i);
			if (i > 1) document.newPage();
			addImageToPdf(document, img);
		}
	}

	public void addGif(Document document, String path) throws Exception {
		GifImage img = new GifImage(path);
		int n = img.getFrameCount();
		for (int i = 1; i <= n; i++) {
			if (i > 1) document.newPage(); 
			addImageToPdf(document, img.getImage(i));
		}
	}
	
	public void addImg(Document document, String path) throws Exception {
		Image img = Image.getInstance(path);
		addImageToPdf(document, img);

	}
	 

	private void addImageToPdf(Document doc, Image img) throws Exception {

		float pw = doc.right() - doc.left() + 1;
		float ph = doc.top() - doc.bottom() + 1;


		if ( img.getScaledWidth()  > pw ) {
			if (img.getScaledWidth() > img.getScaledHeight()) {
				img.setRotationDegrees(90f);
			}
		}
		if (img.getScaledWidth() > pw || img.getScaledHeight() > ph) {
			img.scaleToFit(pw, ph);
		}

		doc.add(img);

	}


	
	public File[] getAllFiles(String orderId) {
		 File folder = new File(getDataFilePath() + "/" + orderId);

		 if (! (folder.isDirectory() && folder.exists()) ) {
			 logger.error("Data folder for order " + orderId + " does not extist ...");
			 return null;
		 }
		 FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				String ext = getFileExtension(name);
				return "pdf".equalsIgnoreCase(ext);
			}
			 
		 };
		 return folder.listFiles(filter);
		
	}
	
	private String getFileExtension(String filename) {
		if (StringUtils.isBlank(filename)) return "";
		String ext = "";
		int dot = filename.lastIndexOf('.');
		if (dot >= 0 && dot < filename.length()-1)
			ext = filename.substring(dot+1);
		return StringUtils.lowerCase(ext);
	}
	
	/*
	public static void main(String args[]) throws Exception {
		ReportRepository rep = new ReportRepository();
		rep.setDataFilePath("c:/sbwp_data");

		File outfile = new File("c:/temp/combined.pdf");
		rep.generateMergedPDF(outfile, "RTTWM000373");
	}
	*/
	
	public File getIGuardSignedForms(String orderId, String msisdn, String iGuardSerialNo,String iGuardType,EsigEmailLang esigEmailLang,String plang) {

		if (!"IGUARD_CARECASH".equals(iGuardType) && !"UAD".equals(iGuardType)){
			if (StringUtils.isBlank(msisdn)
					|| StringUtils.isBlank(iGuardSerialNo)) {
				return null;
			}
		}

		String path;
		if("LDS".equalsIgnoreCase(iGuardType)){
			path = getDataFilePath() + "/" + orderId + "/" +  msisdn +"_" + iGuardSerialNo +"_"+ iGuardType+ ".pdf";
			File f = new File(path);
			return (f.canRead()) ? f : null;
		}else if("AD".equalsIgnoreCase(iGuardType)){
			path = getDataFilePath() + "/" + orderId + "/" + msisdn +"_"+ iGuardType + ".pdf";
			File f = new File(path);
			return (f.canRead()) ? f : null;
		}else if ("IGUARD_CARECASH".equalsIgnoreCase(iGuardType)){
			if (esigEmailLang!=null){
				if ("EN".equals(esigEmailLang)){
					path = getDataFilePath() + "/" + orderId + "/" + orderId + "_CareCash_" +"EN"  + ".pdf";
					File f = new File(path);
					return (f.canRead()) ? f : null;
				}else{
				path = getDataFilePath()  + "/" +orderId + "/" + orderId + "_CareCash_" + "CHI" + ".pdf";
				File f = new File(path);
				return (f.canRead()) ? f : null;
				}
			}else{
				if ("02".equals(plang)){
					path = getDataFilePath() + "/" + orderId + "/" + orderId + "_CareCash_" +"EN"  + ".pdf";
					File f = new File(path);
					return (f.canRead()) ? f : null;
				}else{
				path = getDataFilePath()  + "/" +orderId + "/" + orderId + "_CareCash_" + "CHI" + ".pdf";
				File f = new File(path);
				return (f.canRead()) ? f : null;
				}
			}
		}
		else if("UAD".equalsIgnoreCase(iGuardType)){
			path = getDataFilePath() + "/" + orderId + "/" + orderId +"_"+ iGuardType + ".pdf";
			File f = new File(path);
			return (f.canRead()) ? f : null;
		}

		return null;

	}

	public boolean isIGuardSignedFromsExist(String orderId, String msisdn, String iGuardSerialNo,String iGuardType) {
		return (getIGuardSignedForms(orderId, msisdn, iGuardSerialNo ,iGuardType,null,null) != null);
	}
	
	public File getMobileSafetyPhoneSignedForm(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		File file = new File(this.dataFilePath + "/" + orderId + "/" + this.reportHelper.getMobileSafetyPhoneFormsFileName(orderId));
		if (file.canRead()) {
			return file;
		}
		return null;
	}
	
	public File getNFCSimSignedForm(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		File file = new File(this.dataFilePath + "/" + orderId + "/" + this.reportHelper.getNFCSimFormsFileName(orderId));
		if (file.canRead()) {
			return file;
		}
		return null;
	}
	
	public File getOctopusSimSignedForm(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		File file = new File(this.dataFilePath + "/" + orderId + "/" + this.reportHelper.getOctopusSimFormsFileName(orderId));
		if (file.canRead()) {
			return file;
		}
		return null;
	}
	
	public File getAuthLetter() {

		File file = new File(this.dataFilePath + "/authoriz_br_specific_140410.pdf");
		if (file.canRead()) {
			return file;
		}
		return null;
	}
	/*
	
	public static void main(String args[]) throws Exception {
		ReportRepository rep = new ReportRepository();
		rep.setDataFilePath("c:/temp");

		File outfile = new File("c:/temp/combined.pdf");
		rep.convertFile(new File("C:/temp/dest.pdf"), new File("C:/temp/src.pdf"), "test");
	}
	*/

	public void savaFile(String filePath, String fileName, byte[] pdfData) {
		File outputPdfPath = null;
		File outputPdf = null;
		
		FileOutputStream fos = null;
		try {
			
			outputPdfPath = new File(this.formatFilePath(filePath));
			outputPdf = new File(outputPdfPath + "/" + fileName);
			if (outputPdfPath.exists()) {
				if (outputPdf.exists()) {
					outputPdf.delete();
				}
			} else {
				outputPdfPath.mkdirs();
			}

			if (pdfData != null && pdfData.length > 0) {
				outputPdf.createNewFile();
				fos = new FileOutputStream(outputPdf);
				fos.write(pdfData);
				fos.flush();
			}
			
		} catch (Exception e) {
			logger.error("Exception caught in savaFile()", e);
			throw new AppRuntimeException(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					logger.error("Exception caught in savaFile()", e);
				}
			}
			
		}
	}
	
	private String formatFilePath(String filePath) {
		String formatPath = filePath;
		if (!"".equals(formatPath) && formatPath.startsWith("/")) {
			formatPath = this.dataFilePath + formatPath.substring(1);
		}
		if (!"".equals(formatPath)) {
				formatPath = formatPath.replaceAll("\\\\", "/");
		}
		logger.info("save file at path = " + formatPath);
		return formatPath;
	}
	
	public File getHKTCareSignedForms(String orderId, String pdfFileName) {
		String path;
		path = getDataFilePath() + "/" + orderId + "/HKTCare/" + pdfFileName;
		File f = new File(path);
		return (f != null && f.exists() && f.canRead()) ? f : null;
	}	
	
	public File getFormByFilePath(String orderId, String filePath) {
		if (StringUtils.isBlank(orderId) || StringUtils.isBlank(filePath)) {
			return null;
		}
		File file = new File(this.dataFilePath + "/" + orderId + "/" + filePath);
		if (file.canRead()) {
			return file;
		}
		return null;
	}
	
}