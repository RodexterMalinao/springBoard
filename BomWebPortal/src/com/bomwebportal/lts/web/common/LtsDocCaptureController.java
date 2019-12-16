package com.bomwebportal.lts.web.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.form.LtsDocCaptureFormDTO;
import com.bomwebportal.lts.dto.form.LtsDocCaptureFormDTO.LtsDocImgUploadDTO;
import com.bomwebportal.lts.dto.form.LtsOrderAmendmentFormDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.web.util.ReportRepository;
import com.google.common.collect.Lists;

public class LtsDocCaptureController extends SimpleFormController implements HandlerExceptionResolver {

	private OrdDocService ordDocService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private CodeLkupCacheService ltsDocTypeLkupCacheService;
	
	private String saveDirectory;
	private OrderService orderService;
	private String[] allowedExtensions;
	private long maxUploadSizeInfo;
	private ReportRepository docRepository;
	
	private final String SESSION_DOCCAPTURE = "sessionAmendDocCapture"; 
	
	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}
	
//	@Override
//	protected ModelAndView handleRequestInternal(
//			HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
		/*
		String orderId = request.getParameter("orderId");

		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession()
			.getAttribute("bomsalesuser");
		
		List<OrdDocAssgnDTO> docReq = ordDocService.getRequiredDoc(orderId);
		List<OrdDocDTO> docRecord = ordDocService.getOrdDoc(orderId);
		
		
		ModelAndView view = new ModelAndView("lts/common/ltsdoccapture");
		view.addObject("orderId", orderId);
		view.addObject("requiredDocList", docReq);
		view.addObject("capturedRecordList", docRecord);
		
		return view;
		*/


//		List<OrdDocAssgnDTO> docReq = new ArrayList<OrdDocAssgnDTO>();
		
		
//		List<OrdDocAssgnDTO> docReq = ordDocService.getRequiredDoc(orderId);
//		List<OrdDocDTO> docRecord = ordDocService.getOrdDoc(orderId);
		

//		ModelAndView view = new ModelAndView("lts/common/ltsdoccapture");
//		view.addObject("orderId", orderId);
//		view.addObject("requiredDocList", docReq);
//		view.addObject("capturedRecordList", docRecord);
//		return view;
//	}
	
	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orderId = request.getParameter("orderId");
		if(StringUtils.isBlank(orderId)){
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		String orderId = request.getParameter("orderId");
		String username = LtsSessionHelper.getBomSalesUser(request).getUsername();
		
		LtsDocCaptureFormDTO form = (LtsDocCaptureFormDTO) request.getSession().getAttribute(SESSION_DOCCAPTURE);
		LtsOrderAmendmentFormDTO amendForm = (LtsOrderAmendmentFormDTO) LtsSessionHelper.getOrderAmendForm(request);;
		Map<String, String> requiredDocType = amendForm.getRequiredDocMap();//request.getParameterValues("docType");
		if(form == null || amendForm == null
				|| !StringUtils.equals(form.getOrderId(), amendForm.getSbOrderNum())){
			form = new LtsDocCaptureFormDTO();
			form.setOrderId(orderId);
			form.setMaxUploadSize(maxUploadSizeInfo);
			form.setAllowedExtensions(allowedExtensions);
			
			List<LtsDocImgUploadDTO> docImgUploadList = new ArrayList<LtsDocImgUploadDTO>();
			if(requiredDocType != null && requiredDocType.size() > 0){
				for(String type : requiredDocType.keySet()){
					OrderDocDTO ordDoc = ltsOrderDocumentService.getOrderDoc(type);
					LtsDocImgUploadDTO doc = form.new LtsDocImgUploadDTO();
					doc.setDocType(ordDoc.getDocType());
					doc.setDocName(ordDoc.getDocName());
					doc.setOrderId(orderId);
					doc.setUsername(username);
					doc.setMaxUploadSize(maxUploadSizeInfo);
					doc.setAllowedExtensions(allowedExtensions);
					doc.setCollected(false);
					doc.setMandatory(StringUtils.equals(requiredDocType.get(type), LtsConstant.ITEM_MDO_MANDATORY));
					docImgUploadList.add(doc);
				}
			}
			form.setDocImgUploadList(docImgUploadList);
		}
		
		request.getSession().setAttribute(SESSION_DOCCAPTURE, form);
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception 
	{
		SbOrderDTO sbOrder = LtsSessionHelper.getDummySbOrder(request);
		LtsDocCaptureFormDTO form = (LtsDocCaptureFormDTO) command;
		String username = LtsSessionHelper.getBomSalesUser(request).getUsername();
		
		if(StringUtils.isNotBlank(form.getSubmitDocType())){
			if(form.getDocImgUploadList() != null
					&& form.getDocImgUploadList().size() > 0){
				for(LtsDocImgUploadDTO docImg :form.getDocImgUploadList()){
					if(StringUtils.equals(docImg.getDocType(), form.getSubmitDocType())){

						String fileName = "";
						String orderId = StringUtils.chomp(form.getOrderId());
						String docType = StringUtils.chomp(docImg.getDocType());
						String docName = docImg.getDocName();
						
						long size = 0;
						
						MultipartFile mpFile = docImg.getFile();
						
						try {
							int seq = getNextSeqNum(orderId, docType);
							
							String desc = docName + " (" + seq + ")";
							File uploadedFile = processMultipartFile(mpFile, orderId, docType, seq, desc);
							
							fileName = uploadedFile.getName();
							
							saveRecord(orderId, docType, seq, fileName, username);
							

					
							size = uploadedFile.length();
							docImg.setSize(size);
							docImg.setFileName(fileName);
							docImg.setCollected(true);
							logger.info("Uploaded file path=" + uploadedFile + ", size="+size);

						} catch (Exception e) {
							logger.error("Error while processing upload", e);
							return new ModelAndView("error", "exception",
									new AppRuntimeException("Error while uploading file", e));
						}

						
					}
				}
			}
			
			
			String orderId= form.getOrderId();
			String attrUid=(String)request.getParameter("sbuid");
			request.getSession().setAttribute(SESSION_DOCCAPTURE, form);
			ModelAndView view = new ModelAndView(new RedirectView("ltsdoccapture.html"));
			view.addObject("orderId", orderId);
			view.addObject("isAmend", true);
			view.addObject("sbuid", attrUid);
			
			return view;
		}
		

		for(LtsDocImgUploadDTO docImg :form.getDocImgUploadList()){
			if(docImg.isMandatory() && !docImg.isCollected()){
				errors.rejectValue("submitDocType", "lts.upload.mandatory.required");
				
				String orderId= form.getOrderId();
				String attrUid=(String)request.getParameter("sbuid");
				request.getSession().setAttribute(SESSION_DOCCAPTURE, form);
				ModelAndView mav = new ModelAndView("lts/common/ltsdoccapture", referenceData(request, command, errors));
				mav.addAllObjects(errors.getModel());
				mav.addObject("orderId", orderId);
				mav.addObject("isAmend", true);
				mav.addObject("sbuid", attrUid);
				
				return mav;
			}
		}
		
		if(form.getDocImgUploadList() != null
				&& form.getDocImgUploadList().size() > 0){
			
			AllOrdDocAssgnLtsDTO[] allOrdDocAssgns = sbOrder.getAllOrdDocAssgns();
			List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnsList = new ArrayList<AllOrdDocAssgnLtsDTO>();
			for(LtsDocImgUploadDTO docImg: form.getDocImgUploadList()){
				boolean matchFlag = false;
				if(allOrdDocAssgns != null && allOrdDocAssgns.length > 0){
					for(AllOrdDocAssgnLtsDTO allOrdDocAssgn: allOrdDocAssgns){
//						allOrdDocAssgn.setCollectedInd("N");
						if(StringUtils.equals(allOrdDocAssgn.getDocType(), docImg.getDocType())){
							matchFlag = true;
							if(docImg.isCollected()){
								allOrdDocAssgn.setCollectedInd("Y");
								allOrdDocAssgn.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
								allOrdDocAssgnsList.add(allOrdDocAssgn);
							}
							break;
						}
					}
				}
				//New required doc
				if(!matchFlag && docImg.isCollected()){
					AllOrdDocAssgnLtsDTO newAllOrdDocAssgn = new AllOrdDocAssgnLtsDTO();
					newAllOrdDocAssgn.setCollectedInd("Y");
					newAllOrdDocAssgn.setDocType(docImg.getDocType());
					allOrdDocAssgnsList.add(newAllOrdDocAssgn);
				}
			}

			ltsOrderDocumentService.saveAllAllOrdDocAssgn(allOrdDocAssgnsList, username, form.getOrderId());
			
		}
		
		return new ModelAndView(new RedirectView("orderamendsubmit.html"));
//		return view;

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

		if(seqNum > 1){
			ltsOrderDocumentService.updateOutdatedInd(orderId, docType, true);
		}
		ordDocService.insertOrdDoc(dto);
		orderService.saveCslOrderRecord(orderId, username);

//		if("I".equals(docType.substring(0, 1))){
//			orderServiceIms.updateWqAttachment(orderId, docType);
//		}
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
	
	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public CodeLkupCacheService getLtsDocTypeLkupCacheService() {
		return ltsDocTypeLkupCacheService;
	}

	public void setLtsDocTypeLkupCacheService(
			CodeLkupCacheService ltsDocTypeLkupCacheService) {
		this.ltsDocTypeLkupCacheService = ltsDocTypeLkupCacheService;
	}

	public String getSaveDirectory() {
		return saveDirectory;
	}

	public void setSaveDirectory(String saveDirectory) {
		this.saveDirectory = saveDirectory;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
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

	public ReportRepository getDocRepository() {
		return docRepository;
	}

	public void setDocRepository(ReportRepository docRepository) {
		this.docRepository = docRepository;
	}
	
}
