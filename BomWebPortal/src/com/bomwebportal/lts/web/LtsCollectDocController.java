package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.CollectDocDto;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqFaxImageDTO;
import com.bomwebportal.lts.dto.form.LtsCollectDocFormDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.order.AllOrdDocLtsServiceImpl;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.acq.LtsAcqFaxSerialHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;

public class LtsCollectDocController extends SimpleFormController {

	private final String commandName = "ltsCollectDocCmd";
	private final String viewName = "ltscollectdoc";
	private final String nextView = "ltscollectdoc.html?submit=true";

	private CodeLkupCacheService ltsWaiveReasonCacheService;
	private CodeLkupCacheService ltsDocTypeLkupCacheService;
	private AllOrdDocLtsServiceImpl allOrdDocLtsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsAcqFaxSerialHelper faxSerialHelper;
	
	
	public LtsCollectDocController() {
		setCommandClass(LtsCollectDocFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		String orderType = request.getParameter("orderType");
		OrderCaptureDTO orderCapture = null;
		
		orderCapture = LtsSessionHelper.getOrderCapture(request);
		if(orderCapture == null){
			orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		}

		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}

	@Override
	public Object formBackingObject(HttpServletRequest request)	throws ServletException {
		
//		String orderType = request.getParameter("orderType");
		LtsCollectDocFormDTO form = new LtsCollectDocFormDTO();
		OrderCaptureDTO orderCapture  = LtsSessionHelper.getOrderCapture(request);
		
		if(orderCapture == null){
			orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);	
		}
		
		initialize(orderCapture.getSbOrder(), form);
		
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if(orderCapture == null){
			orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		}
		LtsCollectDocFormDTO form = (LtsCollectDocFormDTO) command;		
		String user = ((BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER)).getUsername();
		
		for(CollectDocDto doc : form.getCollectDocDtoList()){
			doc.setFaxSerial(StringUtils.isBlank(doc.getFaxSerial())? null : StringUtils.trim(doc.getFaxSerial()));
			ltsOrderDocumentService.updateOutdatedInd(form.getOrderId(), doc.getDocType(), true);
		}

		List<AllOrdDocDTO> allOrdDocDtoList = null;
		allOrdDocDtoList = createAllOrdDocDTO(form, form.getOrderId(), user);
		if(allOrdDocDtoList != null){
			ltsOrderDocumentService.insertAllOrdDocDTO(allOrdDocDtoList);
		}
		
		TreeMap<String, String> faxSerialMap = new TreeMap<String, String>(); 
		StringBuilder returnMsg = new StringBuilder();
		String faxWarningMsg = null;
		AllOrdDocAssgnLtsDTO[] allOrdDocAssgns = orderCapture.getSbOrder().getAllOrdDocAssgns();
		for(AllOrdDocAssgnLtsDTO allOrdDocAssgn: allOrdDocAssgns){
			allOrdDocAssgn.setCollectedInd("N");
			for(CollectDocDto collectDoc: form.getCollectDocDtoList()){
				if(StringUtils.equals(allOrdDocAssgn.getDocType(), collectDoc.getDocType())
						&& StringUtils.isNotBlank(collectDoc.getFaxSerial())){
					if (collectDoc.isFaxUpload()) {
						faxWarningMsg = verifyFaxSerial(orderCapture.getSbOrder(), collectDoc, allOrdDocAssgn, faxSerialMap);
						if (StringUtils.isNotBlank(faxWarningMsg)) {
							returnMsg.append(faxWarningMsg).append("\n");
						}	
					}
					else {
						allOrdDocAssgn.setCollectedInd("Y");	
					}
					break;
				}
			}
			allOrdDocAssgn.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}
		ltsOrderDocumentService.saveAllAllOrdDocAssgn(Lists.newArrayList(allOrdDocAssgns), user, form.getOrderId());
		request.getSession().setAttribute("faxWarningMsg", returnMsg.toString());
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private List<AllOrdDocDTO> createAllOrdDocDTO(LtsCollectDocFormDTO form, String orderId, String user){
		if(form.getCollectDocDtoList() == null){
				return null;
		}
		
		List<AllOrdDocDTO> allOrdDocDtoList = new ArrayList<AllOrdDocDTO>();
		for(CollectDocDto doc : form.getCollectDocDtoList()){
			AllOrdDocDTO allOrdDocDto = new AllOrdDocDTO();
			allOrdDocDto.setDocType(doc.getDocType());
			allOrdDocDto.setOrderId(orderId);
			allOrdDocDto.setSerial(doc.getFaxSerial());
			allOrdDocDto.setLastUpdBy(user);
			allOrdDocDto.setCreateBy(user);
			allOrdDocDtoList.add(allOrdDocDto);
		}

		return allOrdDocDtoList.size() > 0? allOrdDocDtoList: null;
		
	}
	
	private void initialize(SbOrderDTO pSbOrder, LtsCollectDocFormDTO pForm){
		if (pSbOrder == null || ArrayUtils.isEmpty(pSbOrder.getAllOrdDocAssgns())) {
			return;
		}
		
		List<CollectDocDto> collectDocDtoList = new ArrayList<CollectDocDto>();
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
			
			OrderDocDTO orderDoc = ltsOrderDocumentService.getOrderDoc(ordDocAssgn.getDocType());
			if (orderDoc != null) {
				collectDoc.setFaxUpload(StringUtils.equalsIgnoreCase("Y", orderDoc.getFaxUploadInd()));
			}
			
			collectDocDtoList.add(collectDoc);
		}
		
		pForm.setOrderId(pSbOrder.getOrderId());
		pForm.setCollectDocDtoList(collectDocDtoList);
		
	}
	
	private String verifyFaxSerial(SbOrderDTO sbOrder, CollectDocDto collectDoc, AllOrdDocAssgnLtsDTO allOrdDocAssgn, TreeMap<String, String> pFaxSerialMap){
		int docSeq = 0;
		List<AllOrdDocDTO> docInfoList = ltsOrderDocumentService.getAllOrdDocListByDocTypeOrderId(sbOrder.getOrderId(), collectDoc.getDocType());
		if(docInfoList != null){
			docSeq = docInfoList.size();
		}										
		
		if(docSeq == 0){
			docSeq = 1;
		}
		String warningMessage = null;
		LtsAcqFaxImageDTO faxImageUrl = faxSerialHelper.retrieveFaxImageUrl(collectDoc.getFaxSerial(), collectDoc.isFaxUpload());
		String fileName = sbOrder.getOrderId() + "_" + allOrdDocAssgn.getDocType() + "_" + String.valueOf(docSeq) + ".pdf";
		
		if(collectDoc.isFaxUpload()){
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
		
		return warningMessage;
	}
	
	
//	private HashMap<String, String> getFaxSerialMap(String orderId){
//
//		List<AllOrdDocDTO> allOrdDocList = ltsOrderDocumentService.getAllOrdDocListByOrderId(orderId);
//		if(allOrdDocList == null){
//			return new HashMap<String, String>();
//		}
//		
//		HashMap<String, String> faxSerialMap = new HashMap<String, String>();
//		for(AllOrdDocDTO allOrdDoc : allOrdDocList){
//			faxSerialMap.put(allOrdDoc.getDocType(), allOrdDoc.getSerial());
//		}
//		
//		return faxSerialMap;
//		
//	}

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
