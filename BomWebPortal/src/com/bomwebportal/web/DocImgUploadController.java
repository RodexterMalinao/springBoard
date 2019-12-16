package com.bomwebportal.web;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dao.CNMRTSUpportDocUploadDAO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.DocImgUploadDTO;
import com.bomwebportal.dto.DocTypeDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.CNMRTSupportDocDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.ConfigProperties;
import com.bomwebportal.util.MD5Util;
import com.bomwebportal.web.util.ReportRepository;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DocImgUploadController extends SimpleFormController implements HandlerExceptionResolver {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private String saveDirectory;
	private String imageFileDirectory;
	private OrdDocService ordDocService;
	private OrderService orderService;
	private String[] allowedExtensions;
	private long maxUploadSizeInfo;
	private ImsOrderService orderServiceIms;
	private CNMRTSUpportDocUploadDAO docImgUploadDAO;
	private MD5Util md5Util;
	
	public ImsOrderService getOrderServiceIms() {
		return orderServiceIms;
	}

	public void setOrderServiceIms(ImsOrderService orderServiceIms) {
		this.orderServiceIms = orderServiceIms;
	}

	private ReportRepository docRepository;
	
	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}
	
	public String getSaveDirectory() {
		return saveDirectory;
	}
	
	public void setSaveDirectory(String saveDirectory) {
		this.saveDirectory = saveDirectory;
	}
	
	
	
	public String getImageFileDirectory() {
		return imageFileDirectory;
	}

	public void setImageFileDirectory(String imageFileDirectory) {
		this.imageFileDirectory = imageFileDirectory;
	}

	public ReportRepository getDocRepository() {
		return docRepository;
	}

	public void setDocRepository(ReportRepository docRepository) {
		this.docRepository = docRepository;
	}

	public String[] getAllowedExtensions() {
		return allowedExtensions;
	}

	public void setAllowedExtensions(String[] allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}

	public long getMaxUploadSizeInfo() {
		return maxUploadSizeInfo;
	}

	public void setMaxUploadSizeInfo(long maxUploadSizeInfo) {
		this.maxUploadSizeInfo = maxUploadSizeInfo;
	}	
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public CNMRTSUpportDocUploadDAO getDocImgUploadDAO() {
		return docImgUploadDAO;
	}

	public void setDocImgUploadDAO(CNMRTSUpportDocUploadDAO docImgUploadDAO) {
		this.docImgUploadDAO = docImgUploadDAO;
	}

	public MD5Util getMd5Util() {
		return md5Util;
	}

	public void setMd5Util(MD5Util md5Util) {
		this.md5Util = md5Util;
	}

	public ModelAndView onSubmit(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception 
	{
		
		logger.info("imageFileDirectory : " + imageFileDirectory);

		DocImgUploadDTO file = (DocImgUploadDTO)command;
		
		String fileName = "";
		String orderId = StringUtils.chomp(file.getOrderId());
		String docType = StringUtils.chomp(file.getDocType());
		String username = StringUtils.chomp(file.getUsername());
		String docName = file.getDocName();
		String seqNo = file.getSeqNo();
		Boolean newRecord = false;
		logger.info("seqNo : " + seqNo);
		if (seqNo == null){
			newRecord = true;
		    CNMRTSupportDocDTO getNewSeqNoDto = new CNMRTSupportDocDTO();
		    getNewSeqNoDto.setMrtCn(orderId.replace("MRTCN", ""));
		    getNewSeqNoDto.setImageType(docType);
			seqNo = orderService.getCNMRTSupportDocNewSeqNo(getNewSeqNoDto);
			logger.info("seq no not exists!!!!! select MAX + 1 in DB : " + seqNo);
		}
		
		long size = 0;

		MultipartFile mpFile = file.getFile();

		try {
			if (orderId.startsWith("MRTCN") && orderId.length() == 16){
				File orderDir = new File(imageFileDirectory);
				if (!orderDir.isDirectory()) {
					orderDir.mkdir();
				}

				File source = convert(mpFile);
				File dest = new File(imageFileDirectory + "/" + orderId.replace("MRTCN","") + "_" + seqNo + "_" + docType + ".crypt");
				logger.info("destination : " + imageFileDirectory + "/" + orderId.replace("MRTCN","") + "_" + seqNo + "_" + docType + ".crypt");
				try {
					String md5Key = md5Util.getMD5(orderId + "_" + seqNo + "_" + docType);
					//logger.info("MD5 KEY : " + md5Key);
					
					byte[] text = md5Util.file2bytes(source);

					byte[] encrypted = md5Util.crypt(text, Cipher.ENCRYPT_MODE, md5Key);
					
					FileUtils.writeByteArrayToFile(dest, encrypted);
					
				    CNMRTSupportDocDTO updateJob = new CNMRTSupportDocDTO();
					updateJob.setMrtCn(orderId.replace("MRTCN", ""));
					updateJob.setImageType(docType);
					updateJob.setSeqNo(Integer.parseInt(seqNo));
					updateJob.setStatus("UPLOADED");
					if (newRecord){
						orderService.createCNMRTSupportDoc(updateJob);
					} else {
						orderService.updateCNMRTSupportDoc(updateJob);
					}
					source.delete();
				} catch (IOException e) {
				    e.printStackTrace();
				} catch (Exception e) {
				    e.printStackTrace();
				}
			} else {
				int seq = getNextSeqNum(orderId, docType);
				
				String desc = docName + " (" + seq + ")";
				File uploadedFile = processMultipartFile(mpFile, orderId, docType, seq, desc);
				
				fileName = uploadedFile.getName();

				saveRecord(orderId, docType, seq, fileName, username);

				size = uploadedFile.length();
				file.setSize(size);
				file.setFileName(fileName);
				logger.info("Uploaded file path=" + uploadedFile + ", size="+size);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while processing upload", e);
			return new ModelAndView("error", "exception",
					new AppRuntimeException("Error while uploading file", e));
		}

		ModelAndView view = new ModelAndView(getSuccessView(),
				errors.getModel());
		view.addObject("orderId", orderId);
		/* test uid*/
		String attrUid=(String)req.getParameter("sbuid");
		view.addObject("sbuid", attrUid);
		/* test uid*/

		return view;

	}
	
	protected String getFileExtension(String filename) {
		if (StringUtils.isBlank(filename)) return "";
		String ext = "";
		int dot = filename.lastIndexOf('.');
		if (dot >= 0 && dot < filename.length())
			ext = filename.substring(dot);
		return StringUtils.lowerCase(ext);
	}
	
	private int getNextSeqNum(String orderId, String docType) {
		return ordDocService.getLastSeqNum(orderId, docType)+1;
	}
	
	private String composeFileName(String orderId, String docType, int seqNum, String ext) {
		return orderId + '_' + docType + '_' + seqNum + ext;
	}
	
	
	private File processMultipartFile(MultipartFile mpFile, String orderId, String docType, int seq, String desc) throws Exception {
		
		File orderDir = new File(saveDirectory + "/" + orderId);
		
		if (!orderDir.isDirectory()) orderDir.mkdir();
		
		String ext = getFileExtension(mpFile.getOriginalFilename());

		/*
		boolean noconvert;
		if (".pdf".equals(ext)) {
			noconvert = true;
		} else {
			noconvert = false;
		}
		*/

		String fileName = composeFileName(orderId, docType, seq, ".pdf");
		File destFile = new File(orderDir, fileName);
		
		/*
		if (noconvert) {
			mpFile.transferTo(destFile);
			return destFile;
		}
		*/
		
		
		
		// convert file ..
		File tempFile = null;
		try {
			tempFile = File.createTempFile("bomwebportal", ext);
			mpFile.transferTo(tempFile);
			DocTypeDTO docTypeDtl = ordDocService.getDocType(docType, null);
			if (docTypeDtl != null && StringUtils.isNotBlank(docTypeDtl.getWaterMark())) {
				addWaterMark(tempFile, docTypeDtl.getWaterMark());
			}
			docRepository.convertFile(destFile, tempFile, fileName + " : " + desc);
			
		} finally {
			if (tempFile != null && !tempFile.delete()) {
				tempFile.deleteOnExit();
			}
		}

		return destFile;
		
	}

	
	private void addWaterMark(File imageFile, String waterMark) {
		try {
			byte[] imageData = new byte[(int) imageFile.length()];
			FileInputStream fis = new FileInputStream(imageFile);
			fis.read(imageData);
	        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(imageData));
	        watermark(bi, waterMark);
	        byte[] resultData = encodeJPEG(bi, 90);
	        FileOutputStream fos = new FileOutputStream(imageFile);
	        fos.write(resultData);
	        fis.close();
	        fos.close();			
		}
		catch (FileNotFoundException e) {
			logger.error("File not found:" + imageFile.getAbsolutePath());
		}
		catch (Exception e) {
			logger.error("Add water mark exception:" + ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	private void watermark(BufferedImage original, String watermarkText) {
        // create graphics context and enable anti-aliasing
        Graphics2D g2d = original.createGraphics();
        g2d.scale(1, 1);
        g2d.addRenderingHints(
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                   RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // create watermark text shape for rendering
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
        GlyphVector fontGV = font.createGlyphVector(g2d.getFontRenderContext(), watermarkText);
        Rectangle size = fontGV.getPixelBounds(g2d.getFontRenderContext(), 0, 0);
        Shape textShape = fontGV.getOutline();
        double textWidth = size.getWidth();
        double textHeight = size.getHeight();
        AffineTransform rotate45 = AffineTransform.getRotateInstance(Math.PI / 4d);
        Shape rotatedText = rotate45.createTransformedShape(textShape);

        // use a gradient that repeats 4 times
        g2d.setPaint(new GradientPaint(0, 0,
                            new Color(0.917f, 0.58f, 0.56f, 0.3f),
                            original.getWidth() / 2, original.getHeight() / 2,
                            new Color(0.917f, 0.58f, 0.56f, 0.3f)));
        g2d.setStroke(new BasicStroke(0.5f));

        // step in y direction is calc'ed using pythagoras + 5 pixel padding
        double yStep = Math.sqrt(textWidth * textWidth / 2) + 5;

        // step over image rendering watermark text
        for (double x = -textHeight * 3; x < original.getWidth(); x += (textHeight * 3)) {
            double y = -yStep;
            for (; y < original.getHeight(); y += yStep) {
                g2d.draw(rotatedText);
                g2d.fill(rotatedText);
                g2d.translate(0, yStep);
            }
            g2d.translate(textHeight * 3, -(y + yStep));
        }
	}
	
	private byte[] encodeJPEG(BufferedImage image, int quality) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) ((float) image.getWidth() * image.getHeight() / 4));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
        quality = Math.max(0, Math.min(quality, 100));
        param.setQuality((float) quality / 100.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(image);
        byte[] result = baos.toByteArray();
        baos.close();
        return result;
    }
	
	private void saveRecord(String orderId, String docType, int seqNum, String fileName, String username) {
		OrdDocDTO dto = new OrdDocDTO();
		dto.setOrderId(orderId);
		dto.setDocType(docType);
		dto.setSeqNum(seqNum);
		dto.setFilePathName(fileName);
		dto.setCaptureBy(username);
		
		ordDocService.insertOrdDoc(dto);
		orderService.saveCslOrderRecord(orderId, username);

		if("I".equals(docType.substring(0, 1))){
			orderServiceIms.updateWqAttachment(orderId, docType);
		}
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
		throws Exception {
		DocImgUploadDTO dto = new DocImgUploadDTO();
		
		dto.setAllowedExtensions(allowedExtensions);
		dto.setMaxUploadSize(maxUploadSizeInfo);
		
		dto.setOrderId(request.getParameter("orderId"));
		dto.setDocType(request.getParameter("docType"));
		
		BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		if (bomsalesuser != null) {
			dto.setUsername(bomsalesuser.getUsername());
		} else {
			dto.setUsername(request.getParameter("username"));
		}
		
		if (!StringUtils.isEmpty(dto.getDocType())) {

			DocTypeDTO dtdto = ordDocService.getDocTypeForOrder(dto.getDocType(), dto.getOrderId());
			if (dtdto != null) {
				dto.setDocName(dtdto.getDocName());
				dto.setDocNameChi(dtdto.getDocNameChi());
			}
		}
		return dto;
		
	}
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		String exts[] = getAllowedExtensions();
		String jsexts;
		if (exts != null && exts.length > 0) {
			jsexts = StringUtils.join(exts, "|");
			jsexts = "|" + jsexts + "|";
		} else {
			jsexts = "||";
		}
		map.put("jsAllowedExtensions", jsexts.toLowerCase());
		return map;
	}
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {


		try {
			if (exception instanceof MaxUploadSizeExceededException) {
				MaxUploadSizeExceededException musee = (MaxUploadSizeExceededException)exception;
				long maxSize = musee.getMaxUploadSize();

				BindException errors = new BindException(formBackingObject(request), getCommandName());
				errors.rejectValue("file", "limit.file", "File over size limit of " + (maxSize/1024) + " kbytes.");
				ModelAndView view = new ModelAndView(getFormView(),errors.getModel());
				view.addAllObjects(referenceData(request));
				return view;
			}
		} catch (Exception e) {
			logger.error("Error while processing file upload", e);
			throw new AppRuntimeException("Error while uploading file", e);
		}
		return null;
		
	}
	
	
	/*
	class Footer extends PdfPageEventHelper {
		public void onEndPage(PdfWriter writer, Document doc) {
			Rectangle rect = writer.getPageSize();
			logger.info(rect);
			ColumnText.showTextAligned(
					writer.getDirectContent(),
					Element.ALIGN_CENTER,
					new Phrase("TESTING"),
					(rect.getLeft()+rect.getRight())/2, rect.getBottom()+18, 0);
		}
	}
	*/
	
	public File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}

}
