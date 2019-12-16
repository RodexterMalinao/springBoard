package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.CollectDocDto;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.form.LtsCollectDocFormDTO;
import com.bomwebportal.lts.dto.form.LtsDocCaptureFormDTO;
import com.bomwebportal.lts.dto.form.LtsOrderAmendmentFormDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.order.AllOrdDocLtsServiceImpl;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.SupportDocService;
import com.google.common.collect.Lists;
import com.bomwebportal.lts.dto.acq.LtsAcqFaxImageDTO;
import com.bomwebportal.lts.util.acq.LtsAcqFaxSerialHelper;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsSbOrderHelper;


public class LtsAcqCollectDocController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String commandName = "ltsAcqCollectDocCmd";
	private final String viewName = "/lts/acq/ltsacqcollectdoc";
	private final String nextView = "ltsacqcollectdoc.html?submit=true";
	private final String amendNextView = "ltsacqcollectdoc.html?submit=true&isAmend=true";
	private final String currentView = "ltsacqcollectdoc.html?";
	private final String closePopupPage = "closepopup.jsp";

	private CodeLkupCacheService ltsWaiveReasonCacheService;
	private CodeLkupCacheService ltsDocTypeLkupCacheService;
	private AllOrdDocLtsServiceImpl allOrdDocLtsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsAcqFaxSerialHelper faxSerialHelper;
	
	private final String SESSION_FAX_SERIAL_FORM = "sessionAmendFaxSerialForm";

	public LtsAcqCollectDocController() {
		setCommandClass(LtsCollectDocFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		boolean isAmend = Boolean.valueOf(request.getParameter("isAmend"));
		
		if(isAmend){
			SbOrderDTO sbOrder = LtsSessionHelper.getDummySbOrder(request);
			if(sbOrder == null){
				return new ModelAndView(LtsConstant.ERROR_VIEW);
			}
		}else{
			AcqOrderCaptureDTO acqorderCapture = null;
			acqorderCapture = LtsSessionHelper.getAcqOrderCapture(request);
			
			if (acqorderCapture == null) {
				return new ModelAndView(LtsConstant.ERROR_VIEW);
			}
		}
		
		if(request.getSession().getAttribute("readAlready") == null){
			request.getSession().setAttribute("readAlready", false);			
		}
		else{
			request.getSession().setAttribute("readAlready", true);
		}		
		
		return super.handleRequestInternal(request, response);
	}

	@Override
	public Object formBackingObject(HttpServletRequest request)	throws ServletException {

		AcqOrderCaptureDTO acqorderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		LtsCollectDocFormDTO form = (LtsCollectDocFormDTO) request.getSession().getAttribute(SESSION_FAX_SERIAL_FORM);
		LtsOrderAmendmentFormDTO amendForm = (LtsOrderAmendmentFormDTO) LtsSessionHelper.getOrderAmendForm(request);
		boolean isAmend = Boolean.valueOf(request.getParameter("isAmend"));
		
		if(form == null 
				|| (isAmend && (amendForm == null || !StringUtils.equals(amendForm.getSbOrderNum(), form.getOrderId()))
				|| acqorderCapture != null && acqorderCapture.getSbOrder() != null && !StringUtils.equals(form.getOrderId(), acqorderCapture.getSbOrder().getOrderId()) ) ){
			form = new LtsCollectDocFormDTO();
			form.setAmend(isAmend);
			
			if(isAmend){
				Map<String, String> requiredDocMap = amendForm.getRequiredDocMap();//request.getParameterValues("docType");
				SbOrderDTO sbOrder = LtsSessionHelper.getDummySbOrder(request);
				initialize(sbOrder, form, requiredDocMap);
			}else{
				initialize(acqorderCapture.getSbOrder(), form, null);
			}
		}
		
		request.getSession().setAttribute(SESSION_FAX_SERIAL_FORM, form);

		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		LtsCollectDocFormDTO form = (LtsCollectDocFormDTO) command;
		String user = ((BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER)).getUsername();
		
		if(form.isAmend() && StringUtils.equals(form.getSubmitType(), "AMEND_CONT")){
			boolean uploaded = false;
			for(CollectDocDto collectDoc: form.getCollectDocDtoList()){
				if(collectDoc.isMandatory() && !"Y".equals(collectDoc.getCollectedInd())){
					errors.rejectValue("submitType", "lts.upload.mandatory.required");
				}
				if("Y".equals(collectDoc.getCollectedInd())){
					uploaded = true;
				}
			}
			
			if(!uploaded){
				errors.rejectValue("submitType", "lts.no.doc.collected");
			}

			if(errors.hasErrors()){
				ModelAndView mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject("isAmend", true);
				return mav;
			}
			
			return new ModelAndView(new RedirectView("orderamendsubmit.html"));
		}
		
		for(CollectDocDto doc : form.getCollectDocDtoList()){
			doc.setFaxSerial(StringUtils.isBlank(doc.getFaxSerial())? null : StringUtils.trim(doc.getFaxSerial()));
			ltsOrderDocumentService.updateOutdatedInd(form.getOrderId(), doc.getDocType(), true);
		}

		List<AllOrdDocDTO> allOrdDocDtoList = null;
		allOrdDocDtoList = createAllOrdDocDTO(form, form.getOrderId(), user);
		if(allOrdDocDtoList != null){
			ltsOrderDocumentService.insertAllOrdDocDTO(allOrdDocDtoList);
		}

		
		SbOrderDTO sbOrder = null;
		if(form.isAmend()){
			sbOrder = LtsSessionHelper.getDummySbOrder(request);
		}else{
			AcqOrderCaptureDTO acqorderCapture = LtsSessionHelper.getAcqOrderCapture(request);
			sbOrder = acqorderCapture.getSbOrder();
		}
		
		String warningMessage = "";
		ServiceDetailLtsDTO srv = LtsSbOrderHelper.getAcqPipbService(sbOrder.getSrvDtls());
		boolean isPipbOrder = false;
		if(srv != null){
			isPipbOrder = true;
		}
		
		AllOrdDocAssgnLtsDTO[] allOrdDocAssgns = sbOrder.getAllOrdDocAssgns();
		List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnsList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		List<AllOrdDocAssgnLtsDTO> newOrdDocAssgnsList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		
		TreeMap<String, String> faxSerialMap = new TreeMap<String, String>(); 
		
		for(CollectDocDto collectDoc: form.getCollectDocDtoList()){
			boolean matchFlag = false;
			if(allOrdDocAssgns != null && allOrdDocAssgns.length > 0){
				for(AllOrdDocAssgnLtsDTO allOrdDocAssgn: allOrdDocAssgns){
//					allOrdDocAssgn.setCollectedInd("N");
					if(StringUtils.equals(allOrdDocAssgn.getDocType(), collectDoc.getDocType())){
						matchFlag = true;
						if(StringUtils.isNotBlank(collectDoc.getFaxSerial())){
							warningMessage = verifyFaxSerial(sbOrder, collectDoc, isPipbOrder, allOrdDocAssgn, faxSerialMap);
							if("Y".equals(allOrdDocAssgn.getCollectedInd()) && StringUtils.isEmpty(warningMessage)){
								collectDoc.setCollectedInd("Y");
								allOrdDocAssgn.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
								allOrdDocAssgnsList.add(allOrdDocAssgn);
							}
						}
						break;
					}
				}
			}
			
			//New required doc
			if(!matchFlag && StringUtils.isNotBlank(collectDoc.getFaxSerial())){
				AllOrdDocAssgnLtsDTO newAllOrdDocAssgn = new AllOrdDocAssgnLtsDTO();
				newAllOrdDocAssgn.setCollectedInd("Y");
				collectDoc.setCollectedInd("Y");
				newAllOrdDocAssgn.setDocType(collectDoc.getDocType());
				allOrdDocAssgnsList.add(newAllOrdDocAssgn);
				newOrdDocAssgnsList.add(newAllOrdDocAssgn);
			}
		}
		ltsOrderDocumentService.saveAllAllOrdDocAssgn(allOrdDocAssgnsList, user, form.getOrderId());
		
		if(newOrdDocAssgnsList.size() > 0){
			newOrdDocAssgnsList.addAll(Lists.newArrayList(allOrdDocAssgns));
			sbOrder.setAllOrdDocAssgns(newOrdDocAssgnsList.toArray(new AllOrdDocAssgnLtsDTO[newOrdDocAssgnsList.size()]));
		}
//		ltsOrderDocumentService.saveAllAllOrdDocAssgn(Lists.newArrayList(allOrdDocAssgns), user, form.getOrderId());
		
		request.getSession().setAttribute("warning", warningMessage);
		request.getSession().setAttribute("readAlready", null);
		
		if(form.isAmend()){
			return new ModelAndView(new RedirectView(amendNextView)); //closePopupPage
		}
		
		if(isPipbOrder && StringUtils.isNotBlank(warningMessage)){
			return new ModelAndView(new RedirectView(currentView)); //remain current page for PIPB case
		}

		request.getSession().setAttribute(SESSION_FAX_SERIAL_FORM, form);
		return new ModelAndView(new RedirectView(nextView)); //closePopupPage
	}
	
	
	private String verifyFaxSerial(SbOrderDTO sbOrder, CollectDocDto collectDoc, boolean isPipbOrder, AllOrdDocAssgnLtsDTO allOrdDocAssgn, TreeMap<String, String> pFaxSerialMap){
		int docSeq = 0;
		List<AllOrdDocDTO> docInfoList = ltsOrderDocumentService.getAllOrdDocListByDocTypeOrderId(sbOrder.getOrderId(), collectDoc.getDocType());
		if(docInfoList != null){
			docSeq = docInfoList.size();
		}										
		
		if(docSeq == 0){
			docSeq = 1;
		}
		String warningMessage = null;
		LtsAcqFaxImageDTO faxImageUrl = faxSerialHelper.retrieveFaxImageUrl(collectDoc.getFaxSerial(), isPipbOrder);
		String fileName = sbOrder.getOrderId() + "_" + allOrdDocAssgn.getDocType() + "_" + String.valueOf(docSeq) + ".pdf";
		logger.info("LtsAcqCollectDocController.verifyFaxSerial - orderId: " + sbOrder.getOrderId());
		logger.info("LtsAcqCollectDocController.verifyFaxSerial - faxImageUrl: " + faxImageUrl.getFileImageUrl());
		logger.info("LtsAcqCollectDocController.verifyFaxSerial - fileName: " + fileName);
		logger.info("LtsAcqCollectDocController.verifyFaxSerial - serial: " + collectDoc.getFaxSerial());
		if(isPipbOrder){
			if(faxImageUrl != null && StringUtils.isBlank(faxImageUrl.getErrorMsg())){
				String result = faxSerialHelper.getFaxImage(faxImageUrl, sbOrder.getOrderId(), fileName, pFaxSerialMap);
				if(faxSerialHelper.FAX_SUCCESS.equals(result)){								
					ltsOrderDocumentService.updateCapturedFaxImageFileName(sbOrder.getOrderId(), allOrdDocAssgn.getDocType()
								, String.valueOf(docSeq), fileName);
					allOrdDocAssgn.setCollectedInd("Y");
				}
				else{
					warningMessage = result;
					//return new ModelAndView(new RedirectView(currentView));
				}
			}
			else if(StringUtils.isNotBlank(faxImageUrl.getErrorMsg())){
				warningMessage = faxImageUrl.getErrorMsg();
			}
		}
		else{
			if(faxImageUrl != null && StringUtils.isNotBlank(faxImageUrl.getErrorMsg())){
				warningMessage = faxImageUrl.getErrorMsg();
			}else{
				allOrdDocAssgn.setCollectedInd("Y");
			}
		}	
		
		if(StringUtils.isNotBlank(warningMessage)){
			logger.error("LtsAcqCollectDocController.verifyFaxSerial - orderId: " + sbOrder.getOrderId());
			logger.error("LtsAcqCollectDocController.verifyFaxSerial - faxImageUrl: " + faxImageUrl.getFileImageUrl());
			logger.error("LtsAcqCollectDocController.verifyFaxSerial - fileName: " + fileName);
			logger.error("LtsAcqCollectDocController.verifyFaxSerial - serial: " + collectDoc.getFaxSerial());
			logger.error("LtsAcqCollectDocController.verifyFaxSerial - warningMessage: " + warningMessage);
		}
		
		return warningMessage;
	}
	
	
	private List<AllOrdDocDTO> createAllOrdDocDTO(LtsCollectDocFormDTO form, String orderId, String user){
		if(form.getCollectDocDtoList() == null){
				return null;
		}
		
		List<AllOrdDocDTO> allOrdDocDtoList = new ArrayList<AllOrdDocDTO>();
		for(CollectDocDto doc : form.getCollectDocDtoList()){
			if(StringUtils.isNotBlank(doc.getFaxSerial())){
				AllOrdDocDTO allOrdDocDto = new AllOrdDocDTO();
				allOrdDocDto.setDocType(doc.getDocType());
				allOrdDocDto.setOrderId(orderId);
				allOrdDocDto.setSerial(doc.getFaxSerial());
				allOrdDocDto.setLastUpdBy(user);
				allOrdDocDto.setCreateBy(user);
				allOrdDocDtoList.add(allOrdDocDto);
			}
		}

		return allOrdDocDtoList.size() > 0? allOrdDocDtoList: null;
		
	}
	
	private void initialize(SbOrderDTO pSbOrder, LtsCollectDocFormDTO pForm, Map<String, String> requiredDoc){
		if (pSbOrder == null) {
			return;
		}
		
		List<CollectDocDto> collectDocDtoList = new ArrayList<CollectDocDto>();
		
		if(pForm.isAmend()){
			if(requiredDoc != null && requiredDoc.size() > 0){
				for(String docType: requiredDoc.keySet()){
					OrderDocDTO ordDoc = ltsOrderDocumentService.getOrderDoc(docType);
					CollectDocDto collectDoc = new CollectDocDto();
					collectDoc.setDocType(docType);
					collectDoc.setDocTypeDisplay(ordDoc.getDocName());
					collectDoc.setCollectedInd("N");
					collectDoc.setMandatory(StringUtils.equals(requiredDoc.get(docType), LtsConstant.ITEM_MDO_MANDATORY));
					collectDocDtoList.add(collectDoc);
				}
			}
		}else{
			if(ArrayUtils.isEmpty(pSbOrder.getAllOrdDocAssgns())){
				return;
			}
			
			HashMap<String, String> faxSerialMap = ltsOrderDocumentService.getFaxSerialMap(pSbOrder.getOrderId()); // new HashMap<String, String>();
			
			for(AllOrdDocAssgnLtsDTO ordDocAssgn: pSbOrder.getAllOrdDocAssgns()){
				CollectDocDto collectDoc = new CollectDocDto();
				collectDoc.setDocType(ordDocAssgn.getDocType());
				collectDoc.setDocTypeDisplay((String) ltsDocTypeLkupCacheService.get(ordDocAssgn.getDocType()));
				collectDoc.setWaiveReason(ordDocAssgn.getWaiveReason());
				collectDoc.setWaiveReasonDisplay((String) ltsWaiveReasonCacheService.get(ordDocAssgn.getWaiveReason()));
				collectDoc.setMarkDelInd(ordDocAssgn.getMarkDelInd());
				collectDoc.setCollectedInd(ordDocAssgn.getCollectedInd());
				collectDoc.setFaxSerial(faxSerialMap.get(ordDocAssgn.getDocType()));
				collectDocDtoList.add(collectDoc);
			}
		}
		
		pForm.setOrderId(pSbOrder.getOrderId());
		pForm.setCollectDocDtoList(collectDocDtoList);
		
	}

	public CodeLkupCacheService getLtsWaiveReasonCacheService() {
		return ltsWaiveReasonCacheService;
	}

	public void setLtsWaiveReasonCacheService(
			CodeLkupCacheService ltsWaiveReasonCacheService) {
		this.ltsWaiveReasonCacheService = ltsWaiveReasonCacheService;
	}

	public CodeLkupCacheService getLtsDocTypeLkupCacheService() {
		return ltsDocTypeLkupCacheService;
	}

	public void setLtsDocTypeLkupCacheService(
			CodeLkupCacheService ltsDocTypeLkupCacheService) {
		this.ltsDocTypeLkupCacheService = ltsDocTypeLkupCacheService;
	}

	public AllOrdDocLtsServiceImpl getAllOrdDocLtsService() {
		return allOrdDocLtsService;
	}

	public void setAllOrdDocLtsService(AllOrdDocLtsServiceImpl allOrdDocLtsService) {
		this.allOrdDocLtsService = allOrdDocLtsService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	public LtsAcqFaxSerialHelper getFaxSerialHelper() {
		return faxSerialHelper;
	}

	public void setFaxSerialHelper(LtsAcqFaxSerialHelper faxSerialHelper) {
		this.faxSerialHelper = faxSerialHelper;
	}
}
