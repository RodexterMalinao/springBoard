package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumConfirmationFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumConfirmationFormDTO.FormAction;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqNumConfirmationController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    
    private final String viewName = "/lts/acq/ltsacqnumconfirmation";
    private final String nextView = "ltsacqothervoiceservice.html";
	private final String commandName = "ltsacqnumconfirmationCmd";

	private LtsAcqDnPoolService ltsAcqDnPoolService;
	
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private CodeLkupCacheService ltsDsSuspendReasonLkupCacheService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqNumConfirmationController() {
		setCommandClass(LtsAcqNumConfirmationFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
    @Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	setLocale(RequestContextUtils.getLocale(request));
    	AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		if (acqOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
    	
		return super.handleRequestInternal(request, response);
		
	}
    
    @Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
    	setLocale(RequestContextUtils.getLocale(request));
    	AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);    	
    	String sessionId = acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId();
    	
    	BomSalesUserDTO staffDto = LtsSessionHelper.getBomSalesUser(request);    	
    	String staffId = "TLTSACQ";
    	if(staffDto!=null)
    		staffId = staffDto.getUsername();
    	    	
    	Selection selection = acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection();
    	LtsAcqNumConfirmationFormDTO acqNumConfirmationForm = new LtsAcqNumConfirmationFormDTO();    	
    	List<LtsAcqNumberSelectionInfoDTO> reservedDnList = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
    	List <String> sList = new ArrayList<String>();
    	
    	if (acqOrderCapture.getLtsAcqNumConfirmationForm()!=null) {
    		acqNumConfirmationForm = acqOrderCapture.getLtsAcqNumConfirmationForm();
    		if (acqOrderCapture.getLtsAcqNumConfirmationForm().getSessionId()!=null) {
    			sessionId = acqOrderCapture.getLtsAcqNumConfirmationForm().getSessionId();
    		}    		
    	}
    	
    	if (selection==Selection.USE_NEW_DN || selection==Selection.USE_NEW_DN_AND_PIPB_DN) {    	    		
	    	if (LtsConstant.DN_SOURCE_DN_POOL.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource())) {
	    		if (acqNumConfirmationForm.getNewDn()!=null) {
	    			sList.add(acqNumConfirmationForm.getNewDn());
	    		    ltsAcqDnPoolService.updDnStatusToReserve(sList, LtsConstant.DN_POOL_STATUS_CONFIRMED, sessionId, staffId);
	    		    acqNumConfirmationForm.setNewDn(null);
	    		} else {
	    			for (LtsAcqNumberSelectionInfoDTO obj : acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedDnList()) {
	    				sList.add(obj.getSrvNum());
	    			}
	    		}
	    		reservedDnList = getDnListFromPool(sessionId, sList);
				if (reservedDnList!=null && reservedDnList.size()>0) {
				    acqNumConfirmationForm.setNewDn(reservedDnList.size()==1?
				    		LtsSbHelper.getDisplaySrvNum(reservedDnList.get(0).getSrvNum()):
				    			reservedDnList.get(0).getSrvNum());
				}
	    	} else if (LtsConstant.DN_SOURCE_DN_RESERVED.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource())) {				
				acqNumConfirmationForm.setNewDn(LtsSbHelper.getDisplaySrvNum(
						acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedSrvNum()));
			}
    	}
    	
    	if (selection==Selection.USE_NEW_DN_AND_PIPB_DN || selection==Selection.USE_PIPB_DN) {
    		if (LtsConstant.DN_SOURCE_DN_PIPB.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnSource())) {
    			acqNumConfirmationForm.setPipbDn(LtsSbHelper.getDisplaySrvNum(
    					acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getPipbSrvNum()));
    			if (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().isDuplexInd()) {
    				acqNumConfirmationForm.setDuplexDn(LtsSbHelper.getDisplaySrvNum(
    						acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexDn()));
    			}
    		}
    	}
    	
		acqNumConfirmationForm.setSessionId(sessionId);
		acqNumConfirmationForm.setReservedDnList(reservedDnList);
		
		if(StringUtils.isNotBlank(acqOrderCapture.getSuspendRemark())){
			acqNumConfirmationForm.setSuspendRemarks(acqOrderCapture.getSuspendRemark());
	    }else{
	    	acqNumConfirmationForm.setSuspendRemarks(null);
	    }
		
		request.setAttribute("singleChoiceForDnPoolCase", reservedDnList.size()==1?true:false);
		
	    return acqNumConfirmationForm;
	}
    
    @Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
    	
    	LtsAcqNumConfirmationFormDTO acqNumConfirmationForm = (LtsAcqNumConfirmationFormDTO) command;
    	AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
    	acqNumConfirmationForm.setNewDn(LtsSbHelper.leftPadSrvNum(acqNumConfirmationForm.getNewDn()));
    	acqNumConfirmationForm.setPipbDn(LtsSbHelper.leftPadSrvNum(acqNumConfirmationForm.getPipbDn()));
    	acqNumConfirmationForm.setDuplexDn(LtsSbHelper.leftPadSrvNum(acqNumConfirmationForm.getDuplexDn()));
    	List <String> sList = new ArrayList<String>();
    	
    	if (acqOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
    	
    	BomSalesUserDTO staffDto = LtsSessionHelper.getBomSalesUser(request);    	
    	String staffId = "TLTSACQ";
    	if(staffDto!=null)
    		staffId = staffDto.getUsername();
    	
    	String sbId = "";    	
    	SbOrderDTO sborderDto = acqOrderCapture.getSbOrder();
    	if(sborderDto!=null)
    		sbId = sborderDto.getOrderId();
    	
    	if(StringUtils.isNotBlank(acqNumConfirmationForm.getSuspendRemarks())){
    		acqOrderCapture.setSuspendRemark(acqNumConfirmationForm.getSuspendRemarks());
	    }else{
	    	acqOrderCapture.setSuspendRemark(null);
	    }
    	
    	if (FormAction.SUSPEND == acqNumConfirmationForm.getFormAction()) {
    		acqNumConfirmationForm.setNewDn(null);
    		acqNumConfirmationForm.setPipbDn(null);
    		acqNumConfirmationForm.setDuplexDn(null);    		
    		acqOrderCapture.setLtsAcqNumConfirmationForm(acqNumConfirmationForm);
    		Map<String, Object> paramMap = new HashMap<String, Object>();
        	paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
    		paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, acqNumConfirmationForm.getSuspendReason());
			paramMap.put(LtsConstant.REQUEST_PARAM_FROM_VIEW, "ltsacqnumconfirmation");
			return new ModelAndView(new RedirectView(LtsConstant.NEW_ACQ_ORDER_VIEW), paramMap);
		}
    	    	   	
    	ModelAndView mav = new ModelAndView(viewName);
    	   	    	
    	ValidationResultDTO result = validate(request, acqNumConfirmationForm);
    	
    	if (Status.INVAILD == result.getStatus()) {
    		if (acqNumConfirmationForm.getReservedDnList()!=null && acqNumConfirmationForm.getReservedDnList().size()>0){
    		    acqNumConfirmationForm.setNewDn(acqNumConfirmationForm.getReservedDnList().get(0).getSrvNum());
    		}			
			mav.addObject(commandName, acqNumConfirmationForm);
			mav.addObject("errorMsgList", result.getMessageList());
			return mav;
		}
    	
    	if (LtsConstant.DN_SOURCE_DN_POOL.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource())) {
	    	String srvNumList = "";
    		
    		// update DN status to Normal(N) for DN not selected
	    	for (LtsAcqNumberSelectionInfoDTO obj : acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedDnList()) {
				if (!obj.getSrvNum().equals(acqNumConfirmationForm.getNewDn())) {
		    		sList.add(obj.getSrvNum());
		    		srvNumList += obj.getSrvNum()+",";
				}
			}
	    	if (sList.size()>0) {
		    	ltsAcqDnPoolService.updDnStatusToNormalBySrvNum(sList, LtsConstant.DN_POOL_STATUS_RESERVED, 
		    			acqNumConfirmationForm.getSessionId(), staffId);
		    	logger.info("update DN status to Normal(N) for DN not selected, DN:"+srvNumList+" salesID:"+staffId+", sbId:"+sbId);
	    	}
	    	
	    	// update DN status to Confirm(C) for confirmed DN  	
	    	ltsAcqDnPoolService.updDnStatusToConfirm(acqNumConfirmationForm.getNewDn(), 
					acqNumConfirmationForm.getSessionId(), staffId);   
	    	logger.info("update DN status to Confirm(C) for confirmed DN, DN:"+acqNumConfirmationForm.getNewDn()+", salesID:"+staffId+", sbId:"+sbId);
    	}
    	
    	acqOrderCapture.setLtsAcqNumConfirmationForm(acqNumConfirmationForm);    	    			
		return new ModelAndView(new RedirectView(nextView));
				
    }
    
    // Retrieve the DN list from DN pool where status = R(Reserved)    
    private List<LtsAcqNumberSelectionInfoDTO> getDnListFromPool(String sessionId, List<String> dnList) {
    	List<String> list = new ArrayList<String>();
    	List<LtsAcqNumberSelectionInfoDTO> result = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
    	LtsAcqNumberSelectionInfoDTO numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
    	list = ltsAcqDnPoolService.getReservedDnList(sessionId, dnList);
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
				numberSelectionObj.setSrvNum(list.get(i));
				numberSelectionObj.setDisplaySrvNum(LtsSbHelper.getDisplaySrvNum(list.get(i)));
				result.add(numberSelectionObj);						
			}
		}
		return result;
    }
    
    private ValidationResultDTO validate(HttpServletRequest request, LtsAcqNumConfirmationFormDTO acqNumConfirmationForm) {
    	List<String> messageList = new ArrayList<String>();
    	if (acqNumConfirmationForm.getReservedDnList().size()>0) {	    	
	    	for (int i=0; i<acqNumConfirmationForm.getReservedDnList().size(); i++) {
	    		if (acqNumConfirmationForm.getNewDn().equals(acqNumConfirmationForm.getReservedDnList().get(i).getSrvNum())) {
	    			return new ValidationResultDTO(Status.VALID, messageList, null);
	    		}
	    	}
	    	//messageList.add("The number " + LtsSbHelper.getDisplaySrvNum(acqNumConfirmationForm.getNewDn()) + " is already released.");
	    	messageList.add(messageSource.getMessage("lts.acq.numConfirm.numberAlreadyReleased", new Object[] {LtsSbHelper.getDisplaySrvNum(acqNumConfirmationForm.getNewDn())}, this.locale));
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
    	}
    	return new ValidationResultDTO(Status.VALID, messageList, null);
    }
    
    @Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		if(acqOrderCapture.isChannelDirectSales()){
			referenceData.put("suspendReasonList", Arrays.asList(ltsDsSuspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}else{
			referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}
		return referenceData;
	}


	/**
	 * @return the ltsAcqDnPoolService
	 */
	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}

	/**
	 * @param ltsAcqDnPoolService the ltsAcqDnPoolService to set
	 */
	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}
	
	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}

	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
	}

	public CodeLkupCacheService getLtsDsSuspendReasonLkupCacheService() {
		return ltsDsSuspendReasonLkupCacheService;
	}

	public void setLtsDsSuspendReasonLkupCacheService(
			CodeLkupCacheService ltsDsSuspendReasonLkupCacheService) {
		this.ltsDsSuspendReasonLkupCacheService = ltsDsSuspendReasonLkupCacheService;
	}
	
    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
}

