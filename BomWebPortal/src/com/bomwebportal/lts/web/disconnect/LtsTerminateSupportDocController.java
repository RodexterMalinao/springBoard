package com.bomwebportal.lts.web.disconnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.DocWaiveReasonDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateSupportDocFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateSupportDocFormDTO.FormAction;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.disconnect.LtsTerminateConstant;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsTerminateSupportDocController extends SimpleFormController {

	
	private final String viewName = "lts/disconnect/ltsterminatesupportdoc";
	private final String commandName = "ltsTerminateSupportDocCmd";
	
	private LtsOrderDocumentService ltsOrderDocumentService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	
	public LtsTerminateSupportDocController() {
		setCommandClass(LtsTerminateSupportDocFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}

	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
	}

	
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		LtsTerminateSupportDocFormDTO form = orderCapture.getLtsTerminateSupportDocForm();
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		
		if (form != null) {
			return form;
		}
		
		form = new LtsTerminateSupportDocFormDTO();
		
		AccountDetailProfileLtsDTO profileAcct = LtsSbOrderHelper.getPrimaryAccount(orderCapture.getLtsServiceProfile());
		if (profileAcct != null && StringUtils.equalsIgnoreCase("C", profileAcct.getBillLang())) {
			form.setDistributeLang(LtsConstant.DISTRIBUTE_LANG_CHINESE);
		}
		else {
			form.setDistributeLang(LtsConstant.DISTRIBUTE_LANG_ENGLISH);
		}
		form.setDistributionMode(LtsConstant.DISTRIBUTE_MODE_PAPER);
		form.setSupportDocumentList(getSupportDocList(orderCapture, form, bomSalesUser.getUsername()));
		
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		LtsTerminateSupportDocFormDTO form = (LtsTerminateSupportDocFormDTO) command;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
	    
	    if (FormAction.CHANGE_DIS_MODE == form.getFormAction()) {
	    	form.setSupportDocumentList(getSupportDocList(orderCapture, form, bomSalesUser.getUsername()));
	    	ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			orderCapture.setLtsTerminateSupportDocForm(form);
			try {
				mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));	
			}
			catch (DAOException e) {
				throw new AppRuntimeException(e);
			}
			return mav;
	    }
	    else if (FormAction.SUSPEND == form.getFormAction()) {
	    	orderCapture.setLtsTerminateSupportDocForm(null);	
	    	paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, form.getSuspendReason());	
	    }
	    else {
	    	
			
			if (errors.hasFieldErrors()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				return mav;
			}
			
	    	orderCapture.setLtsTerminateSupportDocForm(form);
	    }
		return new ModelAndView(new RedirectView(LtsTerminateConstant.TERMINATION_ORDER_VIEW), paramMap);
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		return referenceData;
	}
	
	
	private List<OrderDocDTO> getSupportDocList(TerminateOrderCaptureDTO orderCapture, LtsTerminateSupportDocFormDTO form, String userId) {
		
		List<OrderDocDTO> supportDocList = new ArrayList<OrderDocDTO>();
		
//		Third-Party Authorization Letter
		if (isThirdPartyApplication(orderCapture)) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER);
		}

		setDeceasedCaseSupportDoc(orderCapture, form, userId, supportDocList);
		return supportDocList;
	}

	private void setDeceasedCaseSupportDoc(TerminateOrderCaptureDTO orderCapture, LtsTerminateSupportDocFormDTO form, String userId, List<OrderDocDTO> supportDocList) {
		LtsTerminateServiceSelectionFormDTO termServiceForm = orderCapture.getLtsTerminateServiceSelectionForm();
		if (!LtsSbOrderHelper.isDeceasedCase(termServiceForm)) {
			return;
		}
		if (LtsConstant.DISC_DECEASE_NORMAL.equals(termServiceForm.getDeceasedType())) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DECEASED_SUP_DOC, userId, form.getDistributionMode());
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_OPTIONAL, userId, form.getDistributionMode());
			
//				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_SUPPORT_DOCUMENT, userId, form.getDistributionMode());
//				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER, userId, form.getDistributionMode());
		}
		else if (LtsConstant.DISC_DECEASE_SPECIAL.equals(termServiceForm.getDeceasedType())) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_SUPPORT_DOCUMENT, userId, form.getDistributionMode());
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEATH_CERTIFICATE, userId, form.getDistributionMode());
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_MANDATORY, userId, form.getDistributionMode());
		}
		else if (LtsConstant.DISC_DECEASE_INHERIT.equals(termServiceForm.getDeceasedType())) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_SUPPORT_DOCUMENT, userId, form.getDistributionMode());
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_LETTER_OF_ADMINISTRATION, userId, form.getDistributionMode());
		}
		else if (LtsConstant.DISC_DECEASE_UNREACHED.equals(termServiceForm.getDeceasedType())) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEATH_CERTIFICATE, userId, form.getDistributionMode());
		}
		else{
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_SUPPORT_DOCUMENT);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEATH_CERTIFICATE);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_OPTIONAL);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_MANDATORY);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_LETTER_OF_ADMINISTRATION);
		}
	}
	
	
	private boolean isThirdPartyApplication(TerminateOrderCaptureDTO orderCapture) {
		
		LtsTerminateServiceSelectionFormDTO form = orderCapture.getLtsTerminateServiceSelectionForm();
		if (form != null) {
			if (form.isThirdParty()) {
				if(StringUtils.equals(form.getThirdRelationship(),"NP")) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		return false;
	}
	
	private void addOrderDoc(List<OrderDocDTO> orderDocList, String orderDocType, String userId, String distributeMode){
		addOrderDoc(orderDocList, orderDocType, userId, distributeMode, null);
	}
	
	private void addOrderDoc(List<OrderDocDTO> orderDocList, String orderDocType, String userId, String distributeMode, String defaultWaiveReason) {
		OrderDocDTO newOrderDoc = getOrderDoc(orderDocType, userId, distributeMode);
		if (newOrderDoc != null) {
			if(StringUtils.isNotBlank(defaultWaiveReason)){
				newOrderDoc.setWaiveReasonCd(defaultWaiveReason);
			}
			removeOrderDoc(orderDocList, orderDocType);
			orderDocList.add(newOrderDoc);	
		}
	}
	
	private void removeOrderDoc(List<OrderDocDTO> orderDocList, String orderDocType) {
		
		OrderDocDTO removeOrderDoc = null;
		for (OrderDocDTO orderDoc : orderDocList) {
			if (StringUtils.equals(orderDocType, orderDoc.getDocType())) {
				removeOrderDoc = orderDoc;
			}
		}
		
		if (removeOrderDoc != null) {
			orderDocList.remove(removeOrderDoc);	
		}
	}
	
	private OrderDocDTO getOrderDoc(String orderDocType, String userId, String distributeMode) {
		OrderDocDTO orderDoc = ltsOrderDocumentService.getOrderDoc(orderDocType);
		
		if (orderDoc == null) {
			return null;
		}
		
		if (orderDoc.getWaiveReasonList() != null && !orderDoc.getWaiveReasonList().isEmpty()) {
			
			List<DocWaiveReasonDTO> filteredWaiveReasonList = new ArrayList<DocWaiveReasonDTO>();
			
			for (DocWaiveReasonDTO docWaiveReason : orderDoc.getWaiveReasonList()) {
				
				if (StringUtils.equals("Y", docWaiveReason.getDefaultWaiveInd())) {
					orderDoc.setWaiveReasonCd(docWaiveReason.getWaiveReasonCd());
					orderDoc.setWaiveReasonBy(userId);
				}
				else if (StringUtils.isNotBlank(docWaiveReason.getDefaultWaiveInd()) && 
						!StringUtils.equals(docWaiveReason.getDefaultWaiveInd(), distributeMode)) {
					continue;
				}
				
				filteredWaiveReasonList.add(docWaiveReason);
			}
			orderDoc.setWaiveReasonList(filteredWaiveReasonList);
		}
		return orderDoc;
	}	
}