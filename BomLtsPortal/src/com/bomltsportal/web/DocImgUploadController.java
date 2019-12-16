package com.bomltsportal.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomltsportal.dto.DocImgUploadDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomltsportal.util.uENC;
import com.bomwebportal.dto.DocTypeDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.web.util.ReportRepositoryCommon;

public class DocImgUploadController extends SimpleFormController implements HandlerExceptionResolver {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private String saveDirectory;
	private OrdDocService ordDocService;
	private String[] allowedExtensions;
	private long maxUploadSizeInfo;
	
	private ReportRepositoryCommon docRepository;
	
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
	
	public ReportRepositoryCommon getDocRepository() {
		return docRepository;
	}

	public void setDocRepository(ReportRepositoryCommon docRepository) {
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

	public ModelAndView onSubmit(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception 
	{
		DocImgUploadDTO file = (DocImgUploadDTO)command;
		
		String fileName = "";
		String orderId = "";
		String docType = BomLtsConstant.DOC_TYPE_NSD;
		String username = BomLtsConstant.USER_ID;
		String docName = BomLtsConstant.DOC_NAME_NSD;
		String parmKey = "";
		
		// Get OrderId by decode the request parm
		parmKey = uENC.decAES(BomLtsConstant.URL_PARM_ENC_KEY, req.getParameter("key"));
		orderId = parmKey.split("-")[0];
		
		long size = 0;
		MultipartFile mpFile = file.getFile();
		
		try {
			int seq = getNextSeqNum(orderId, docType);
			
			String desc = docName + " (" + seq + ")";
			File uploadedFile = processMultipartFile(mpFile, orderId, docType, seq, desc);			
			fileName = uploadedFile.getName();
			saveRecord(orderId, docType, seq, fileName, username);
			size = uploadedFile.length();
			file.setSize(size);
			file.setFileName(fileName);
			logger.info("Uploaded file path=" + uploadedFile + ", size="+size);
		} catch (Exception e) {
			logger.error("Error while processing upload", e);
			return new ModelAndView("error", "exception",
					new AppRuntimeException("Error while uploading file", e));
		}

		return new ModelAndView("message", "msgCode", "uploadform.uploadsuccess");
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
			docRepository.convertFile(destFile, tempFile, fileName + " : " + desc);
			
		} finally {
			if (tempFile != null && !tempFile.delete()) {
				tempFile.deleteOnExit();
			}
		}

		return destFile;
		
	}
	
	private void saveRecord(String orderId, String docType, int seqNum, String fileName, String username) {
		OrdDocDTO dto = new OrdDocDTO();
		dto.setOrderId(orderId);
		dto.setDocType(docType);
		dto.setSeqNum(seqNum);
		dto.setFilePathName(fileName);
		dto.setCaptureBy(username);
		
		ordDocService.insertOrdDoc(dto);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String parmKey = null;
		String[] tokens = null;

		try {
			parmKey = uENC.decAES(BomLtsConstant.URL_PARM_ENC_KEY, request.getParameter("key"));
			tokens = parmKey.split("-");
			
			OrderCaptureDTO order = new OrderCaptureDTO();
			order.setTopNavInd("-");

			SessionHelper.setOrderCapture(request, order);
			SessionHelper.setOrderSrvType(request, tokens[1]);
			
		} catch (Exception e) {
			logger.error("Error while decode the parm key in link", e);
			return new ModelAndView("message", "msgCode", "uploadform.invalidlink");
		}
		SessionHelper.setLanguage(request, response, request.getParameter("lang"));
		return super.handleRequestInternal(request, response);
		
	}
	
 
	@Override
	protected Object formBackingObject(HttpServletRequest request)
		throws Exception {
		DocImgUploadDTO dto = new DocImgUploadDTO();

		dto.setAllowedExtensions(allowedExtensions);
		dto.setMaxUploadSize(maxUploadSizeInfo);
		
		dto.setOrderId(request.getParameter("orderId"));
		dto.setDocType(request.getParameter("docType"));
		dto.setUsername(request.getParameter("username"));
		
		if (!StringUtils.isEmpty(dto.getDocType())) {

			DocTypeDTO dtdto = ordDocService.getDocType(dto.getDocType(), "LTS");
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
}
