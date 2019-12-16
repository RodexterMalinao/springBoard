package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO.Action;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO.Method;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO.Selection;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsDnChangeController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    
    private final String viewName = "ltsdnchange";
	private final String nextView = "ltsaddressrollout.html";
	private final String dn_duplexView = "ltsdnchangeduplex.html";
	private final String commandName = "ltsdnchangeCmd";
	
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	protected LtsOfferService ltsOfferService;
	protected ServiceProfileLtsService serviceProfileLtsService;
	
	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsDnChangeController() {
		setCommandClass(LtsDnChangeFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
    @Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    
    	OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
    	if (orderCapture == null) {
    		return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
		
	}
    
    @Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
    	OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);    	
    	BomSalesUserDTO SatffChannel = LtsSessionHelper.getBomSalesUser(request);
    	String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    	LtsDnChangeFormDTO dnChangeForm = new LtsDnChangeFormDTO();
    	List<String> sList = new ArrayList<String>();
    	 
    	if (orderCapture.getLtsDnChangeForm()!=null) {
    		dnChangeForm = orderCapture.getLtsDnChangeForm();
    		if (orderCapture.getLtsDnChangeForm().getSessionId()!=null) {
    			sessionId = orderCapture.getLtsDnChangeForm().getSessionId();
    		}
    	}
	
    	if(dnChangeForm!=null 
    			&& dnChangeForm.getConfirmedDn()!=null){
			if (LtsConstant.DN_SOURCE_DN_POOL.equals(dnChangeForm.getDnSource())) {
				sList.add(dnChangeForm.getConfirmedDn());
		    	ltsAcqDnPoolService.updDnStatusToReserve(sList, LtsConstant.DN_POOL_STATUS_CONFIRMED, sessionId);
		    }
			dnChangeForm.setConfirmedDn(null);
		    dnChangeForm.setReservedDnList(getReservedDnList(sessionId, sList));
		    ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
		} else if (dnChangeForm.getReservedDnList()!=null
				&& dnChangeForm.getReservedDnList().size()>0) {
			for (LtsAcqNumberSelectionInfoDTO obj : dnChangeForm.getReservedDnList()) {
				sList.add(obj.getSrvNum());
			}
			dnChangeForm.setReservedDnList(getReservedDnList(sessionId, sList));
		} else {
			dnChangeForm.setReservedDnList(new ArrayList<LtsAcqNumberSelectionInfoDTO>());
		}
    	
		if (StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getDuplexNum())) {
			dnChangeForm.setDuplexProfile(true);
		}
		
		String applicationDate = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
		String locale = RequestContextUtils.getLocale(request).toString();
		List<ItemDetailDTO> chargeItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_DN_CHANGE, locale, true, orderCapture.getBasketChannelId(), applicationDate);
		List<ItemDetailDTO> rebateItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_DN_CHANGE_WAIVE, locale, true, orderCapture.getBasketChannelId(), applicationDate);

		dnChangeForm.setChargeItem(chargeItemList.get(0));
		if(rebateItemList !=null){
			dnChangeForm.setRebateItem(rebateItemList.get(0));
			dnChangeForm.getRebateItem().setSelected(false);
		}

		dnChangeForm.getChargeItem().setSelected(true);
		dnChangeForm.setPenaltyApprovalInd(false);
		if(orderCapture.getApprovalLoginForm() != null){
			dnChangeForm.setPenaltyApprovalInd(orderCapture.getApprovalLoginForm().isApprovalInd());
		}
		
		
		dnChangeForm.setChannelID(SatffChannel.getChannelId());
		dnChangeForm.setSessionId(sessionId);
		dnChangeForm.setNumSelectionList(getNumSelectionList(sessionId));		
		return dnChangeForm;
		    
	}
    
    @Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
    	
    	LtsDnChangeFormDTO dnChangeForm = (LtsDnChangeFormDTO) command;
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);		
		List<LtsAcqNumberSelectionInfoDTO> dnList = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
		List<String> sList = new ArrayList<String>();
				
    	if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
    	
    	ModelAndView mav = new ModelAndView(viewName);
    	
    	validate(request, dnChangeForm, errors);    	
    	if (errors.hasFieldErrors()) {
			mav.addAllObjects(errors.getModel());
			mav.addObject(commandName, dnChangeForm);
			return mav;
		}
    	
		switch (dnChangeForm.getFormAction()) {
		case SUBMIT:
			    if(dnChangeForm.getNumSelection() == Selection.KEEP_EXIST_DN){
			    	orderCapture.setLtsDnChangeForm(null);
			    	orderCapture.setApprovalLoginForm(null);
			    	return new ModelAndView(new RedirectView(StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getDuplexNum())? dn_duplexView:nextView));
			    }
				if (dnChangeForm.getSearchMethod()==Method.RESERVED_DN) {
					dnChangeForm.setConfirmedDn(null);
					dnChangeForm.setReservedSrvNum(StringUtils.leftPad(dnChangeForm.getReservedSrvNum(),12,"0"));
					if(serviceProfileLtsService.getPendingOrder(dnChangeForm.getReservedSrvNum(), LtsConstant.SERVICE_TYPE_TEL) != null){
						errors.rejectValue("searchErrMsg", "lts.acq.dnSearch.dnNotUsable");
		    			mav.addAllObjects(errors.getModel());
		    			dnChangeForm.setReservedSrvNum(null);
		    			mav.addObject(commandName, dnChangeForm);
		            	orderCapture.setLtsDnChangeForm(dnChangeForm);
		    			return mav;		    			
					}else{
						dnChangeForm.setDnSource(LtsConstant.DN_SOURCE_DN_RESERVED);
						if (dnChangeForm.getReservedDnList()!=null
								&& dnChangeForm.getReservedDnList().size()>0) {
							for (LtsAcqNumberSelectionInfoDTO obj : dnChangeForm.getReservedDnList()) {
								sList.add(obj.getSrvNum());
							}
						ltsAcqDnPoolService.updDnStatusToNormalBySrvNum(sList, LtsConstant.DN_POOL_STATUS_RESERVED, 
								dnChangeForm.getSessionId());	
						}
					}
				
				} 
				if (dnChangeForm.getSearchMethod()==Method.DN_POOL ) {
					dnChangeForm.setReservedSrvNum(null);
					if(dnChangeForm.getReservedDnList() != null && dnChangeForm.getReservedDnList().size() != 0){
						if(serviceProfileLtsService.getPendingOrder(dnChangeForm.getReservedDnList().get(0).getSrvNum(), LtsConstant.SERVICE_TYPE_TEL) != null){
							errors.rejectValue("searchErrMsg", "lts.acq.dnSearch.dnNotUsable");
			    			mav.addAllObjects(errors.getModel());
			    			mav.addObject(commandName, dnChangeForm);
			            	orderCapture.setLtsDnChangeForm(dnChangeForm);
			    			return mav;
						}else{
							dnChangeForm.setConfirmedDn(dnChangeForm.getReservedDnList().get(0).getSrvNum());	
							ltsAcqDnPoolService.updDnStatusToConfirm(dnChangeForm.getConfirmedDn(), 
					    			dnChangeForm.getSessionId());
							dnChangeForm.setDnSource(LtsConstant.DN_SOURCE_DN_POOL);
						}						
					}
				}
				if(dnChangeForm.getChannelID() == 1){
					if(StringUtils.isNotEmpty(dnChangeForm.getConfirmedDn()) || StringUtils.isNotEmpty(dnChangeForm.getReservedSrvNum())){
						if(StringUtils.equals(dnChangeForm.getChargeItem().getPenaltyHandling() , "MW") && !dnChangeForm.isPenaltyApprovalInd()){
							errors.rejectValue("lockNumErrMsg", "lts.waiveApprove.required");
							mav.addAllObjects(errors.getModel());
			    			mav.addObject(commandName, dnChangeForm);
			            	orderCapture.setLtsDnChangeForm(dnChangeForm);
							return mav;
						}
					}
			    }
				if(orderCapture.getApprovalLoginForm() != null){
					if(orderCapture.getApprovalLoginForm().isApprovalInd())
					dnChangeForm.getChargeItem().setPenaltyHandling(LtsConstant.PENALTY_ACTION_AUTO_WAIVE);
				}
				if(dnChangeForm.getConfirmedDn() != null || dnChangeForm.getReservedSrvNum() != null){
					if(StringUtils.equals(dnChangeForm.getChargeItem().getPenaltyHandling(),LtsConstant.PENALTY_ACTION_AUTO_WAIVE)
							|| StringUtils.equals(dnChangeForm.getChargeItem().getPenaltyHandling(),LtsConstant.PENALTY_ACTION_MANUAL_WAIVE)){
						dnChangeForm.getRebateItem().setSelected(true);
					}
				}				
			ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, dnChangeForm.getSessionId());
		break;
					
        case SEARCH_BY_NO_CRITERIA:
        case SEARCH_BY_FIRST_8_DIGITS:
        case SEARCH_BY_LAST_3_DIGITS: 
        	
        	ltsAcqDnPoolService.updDnStatusToNormal(
    				LtsConstant.DN_POOL_STATUS_LOCKED, dnChangeForm.getSessionId());
        	dnList = searchDnFromDnPool(dnChangeForm);        	
        	dnChangeForm.setNumSelectionList(dnList);
        	if (dnList.size() == 0) {
    			errors.rejectValue("searchErrMsg", "lts.acq.dnSearch.noDnFound");
    			mav.addAllObjects(errors.getModel());
			}
        	mav.addObject(commandName, dnChangeForm);
        	orderCapture.setLtsDnChangeForm(dnChangeForm);
			return mav;
			
		case LOCK_NUMBER:					
			    if(dnChangeForm.getReservedDnList().size() != 0){
			    	errors.rejectValue("chooseOneErrMsg", "lts.acq.lockNum.error2");
				    mav.addAllObjects(errors.getModel());
			    }else{
			    	ltsAcqDnPoolService.updDnStatusToReserve(dnChangeForm.getNumSelectionStringList(), 
							LtsConstant.DN_POOL_STATUS_LOCKED, dnChangeForm.getSessionId());
					sList = new ArrayList<String>();
					dnList.addAll(dnChangeForm.getNumSelectionList());
					dnList.addAll(dnChangeForm.getReservedDnList());
				
					for (LtsAcqNumberSelectionInfoDTO obj : dnList) {
						sList.add(obj.getSrvNum());
					}
					dnChangeForm.setNumSelectionList(getNumSelectionList(dnChangeForm.getSessionId()));
					dnChangeForm.setReservedDnList(getReservedDnList(dnChangeForm.getSessionId(), sList));
				
				    mav.addObject(commandName, dnChangeForm);
				    orderCapture.setLtsDnChangeForm(dnChangeForm);
			    }
				
			return mav;	
			
        case RELEASE_NUMBER:
        	
	        	ltsAcqDnPoolService.releaseDnStatusToLockedByReservedDn(
	        			dnChangeForm.getReservedDnStringList(), dnChangeForm.getSessionId());				
	        	sList = new ArrayList<String>();	        	
				dnList.addAll(dnChangeForm.getNumSelectionList());
				dnList.addAll(dnChangeForm.getReservedDnList());
				for (LtsAcqNumberSelectionInfoDTO obj : dnList) {
					sList.add(obj.getSrvNum());
				}
	        	dnChangeForm.setNumSelectionList(getNumSelectionList(dnChangeForm.getSessionId()));
				dnChangeForm.setReservedDnList(getReservedDnList(dnChangeForm.getSessionId(), sList));
        	
			mav.addObject(commandName, dnChangeForm);
			orderCapture.setLtsDnChangeForm(dnChangeForm);
			return mav;	
        case WAIVE_APPROVED:
        	mav.addObject(commandName, dnChangeForm);
			orderCapture.setLtsDnChangeForm(dnChangeForm);
			return mav;
		default:
			break;
		}
		
		orderCapture.setLtsDnChangeForm(dnChangeForm);
		return new ModelAndView(new RedirectView(orderCapture.getLtsDnChangeForm().isDuplexProfile() == true? dn_duplexView:nextView));
		//return new ModelAndView(new RedirectView(nextView));
		
    }
 
    // Search the DN list from DN pool
    private List<LtsAcqNumberSelectionInfoDTO> searchDnFromDnPool(LtsDnChangeFormDTO form) {
    	List<String> list = new ArrayList<String>();
    	List<LtsAcqNumberSelectionInfoDTO> result = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
    	LtsAcqNumberSelectionInfoDTO numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
    	switch (form.getFormAction()) {
			case SEARCH_BY_NO_CRITERIA:
		    	list = ltsAcqDnPoolService.searchDnListFromPoolByNoCriteria(form.getSessionId());
		    	break;
			case SEARCH_BY_FIRST_8_DIGITS:
		    	list = ltsAcqDnPoolService.searchDnListFromPoolByFirst8Digits(form.getSessionId(), form.getFirst5To8Digits());
		    	break;
			case SEARCH_BY_LAST_3_DIGITS:
		    	list = ltsAcqDnPoolService.searchDnListFromPoolByLast3Digits(form.getSessionId(), form.getLast1To3Digits());
		    	break;
			default:
				break;
		}		
    	if (list != null) {
			for (int i=0; i<list.size(); i++) {
				numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
				numberSelectionObj.setSrvNum(list.get(i));
				numberSelectionObj.setDisplaySrvNum(list.get(i).substring(4,12));
				result.add(numberSelectionObj);						
			}
		}    	
		return result;
    }
    
    // Retrieve the DN list from DN pool where status = R(Reserved)    
    private List<LtsAcqNumberSelectionInfoDTO> getReservedDnList(String sessionId, List<String> dnList) {
    	List<String> list = new ArrayList<String>();
    	List<LtsAcqNumberSelectionInfoDTO> result = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
    	LtsAcqNumberSelectionInfoDTO numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
    	list = ltsAcqDnPoolService.getReservedDnList(sessionId, dnList);
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
				numberSelectionObj.setSrvNum(list.get(i));
				numberSelectionObj.setDisplaySrvNum(list.get(i).substring(4,12));
				result.add(numberSelectionObj);						
			}
		}
		return result;
    }
    
    // Retrieve the DN list from DN pool where status = L(Locked)
    private List<LtsAcqNumberSelectionInfoDTO> getNumSelectionList(String sessionId) {
    	List<String> list = new ArrayList<String>();
    	List<LtsAcqNumberSelectionInfoDTO> result = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
    	LtsAcqNumberSelectionInfoDTO numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
    	list = ltsAcqDnPoolService.getLockedDnList(sessionId);
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
				numberSelectionObj.setSrvNum(list.get(i));
				numberSelectionObj.setDisplaySrvNum(list.get(i).substring(4,12));
				result.add(numberSelectionObj);						
			}
		}
		return result;
    }
    
    private void validate(HttpServletRequest request, 
    		LtsDnChangeFormDTO dnChangeForm, BindException errors) {
    	if (dnChangeForm.getFormAction()==Action.SUBMIT && !dnChangeForm.isDuplexProfile() &&  // ||dnChangeForm.getNumSelection()==Selection.USE_NEW_DN_AND_PIPB_DN)
    			dnChangeForm.getNumSelection()==Selection.USE_NEW_DN){    		
    		if ((dnChangeForm.getReservedDnList()==null||dnChangeForm.getReservedDnList().size()==0) 
    				&& dnChangeForm.getSearchMethod()!=Method.RESERVED_DN) {
    			errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.required");
    		}
    	}
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
	
}

