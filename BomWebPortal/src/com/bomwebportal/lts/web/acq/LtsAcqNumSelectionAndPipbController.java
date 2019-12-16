package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO.DuplexAction;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Action;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Method;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqNumSelectionAndPipbController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    
    private final String viewName = "/lts/acq/ltsacqnumselectionandpipb";
	private final String nextView = "ltsacqnumconfirmation.html";	
	private final String commandName = "ltsacqnumselectionandpipbCmd";
	
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	protected CodeLkupCacheService pipb2NPortingLkupCacheService;
	private CodeLkupCacheService newCustTitleAcqLkupCacheService;
	private LtsCommonService ltsCommonService;
		
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqNumSelectionAndPipbController() {
		setCommandClass(LtsAcqNumSelectionAndPipbFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		if (order == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		setReferenceData(request, order);
		return super.handleRequestInternal(request, response);
	}
    
    @Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
    	setLocale(RequestContextUtils.getLocale(request));
    	AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);    	
    	String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    	LtsAcqNumSelectionAndPipbFormDTO numSelectionAndPipbForm = new LtsAcqNumSelectionAndPipbFormDTO();
    	List<String> sList = new ArrayList<String>();
    	
    	BomSalesUserDTO staffDto = LtsSessionHelper.getBomSalesUser(request);    	
    	String staffId = "TLTSACQ";
    	if(staffDto!=null)
    		staffId = staffDto.getUsername();
    	
    	if (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm()!=null) {
    		numSelectionAndPipbForm = acqOrderCapture.getLtsAcqNumSelectionAndPipbForm();
    		if (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId()!=null) {
    			sessionId = acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId();
    		}
    	}
		
		if (acqOrderCapture.getLtsAcqNumConfirmationForm()!=null
				&& acqOrderCapture.getLtsAcqNumConfirmationForm().getNewDn()!=null) {
			if (LtsConstant.DN_SOURCE_DN_POOL.equals(numSelectionAndPipbForm.getDnSource())) {
				sList.add(acqOrderCapture.getLtsAcqNumConfirmationForm().getNewDn());
		    	ltsAcqDnPoolService.updDnStatusToReserve(sList, LtsConstant.DN_POOL_STATUS_CONFIRMED, sessionId, staffId);
		    }			
		    acqOrderCapture.getLtsAcqNumConfirmationForm().setNewDn(null);
		    numSelectionAndPipbForm.setReservedDnList(getReservedDnList(sessionId, sList));
		    ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
		} else if (numSelectionAndPipbForm.getReservedDnList()!=null
				&& numSelectionAndPipbForm.getReservedDnList().size()>0) {
			for (LtsAcqNumberSelectionInfoDTO obj : numSelectionAndPipbForm.getReservedDnList()) {
				sList.add(obj.getSrvNum());
			}
			numSelectionAndPipbForm.setReservedDnList(getReservedDnList(sessionId, sList));
		} else {
			numSelectionAndPipbForm.setReservedDnList(new ArrayList<LtsAcqNumberSelectionInfoDTO>());
		}
				
		numSelectionAndPipbForm.setReservedSrvNum(
				LtsSbHelper.getDisplaySrvNum(numSelectionAndPipbForm.getReservedSrvNum()));
		numSelectionAndPipbForm.getPipbInfo().setPipbSrvNum(
				LtsSbHelper.getDisplaySrvNum(numSelectionAndPipbForm.getPipbInfo().getPipbSrvNum()));
		numSelectionAndPipbForm.getPipbInfo().setDuplexDn(
				LtsSbHelper.getDisplaySrvNum(numSelectionAndPipbForm.getPipbInfo().getDuplexDn()));
		
		numSelectionAndPipbForm.setSessionId(sessionId);
		numSelectionAndPipbForm.setNumSelectionList(getNumSelectionList(sessionId));
		numSelectionAndPipbForm.getPipbInfo().setRolloutAddress(acqOrderCapture.getLtsAddressRolloutForm());
		
		request.setAttribute("isNowDrgDownTime", ltsCommonService.isNowDrgDownTime()?"Y":"N");
		
		
		return numSelectionAndPipbForm;
		    
	}
    
    @Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
    	
    	LtsAcqNumSelectionAndPipbFormDTO numSelectionAndPipbForm = (LtsAcqNumSelectionAndPipbFormDTO) command;
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);		
		List<LtsAcqNumberSelectionInfoDTO> dnList = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
		List<String> sList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView(viewName);
		    	
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
    	
		validate(acqOrderCapture, numSelectionAndPipbForm, errors);
    	if (errors.hasFieldErrors()) {
    		mav.addAllObjects(errors.getModel());
			mav.addObject(commandName, numSelectionAndPipbForm);
			acqOrderCapture.setLtsAcqNumSelectionAndPipbForm(numSelectionAndPipbForm);	    	
	    	return mav;
		}
    	
		switch (numSelectionAndPipbForm.getFormAction()) {
		case SUBMIT:	
			 
			if (numSelectionAndPipbForm.getSearchMethod()==Method.RESERVED_DN
					|| numSelectionAndPipbForm.getSearchMethod()==Method.RESERVED_DN_THEN_PIPB
					|| numSelectionAndPipbForm.getSearchMethod()==Method.PIPB_DN) {
				if (numSelectionAndPipbForm.getSearchMethod()==Method.RESERVED_DN
						|| numSelectionAndPipbForm.getSearchMethod()==Method.RESERVED_DN_THEN_PIPB) {
					numSelectionAndPipbForm.setReservedSrvNum(
							LtsSbHelper.leftPadSrvNum(numSelectionAndPipbForm.getReservedSrvNum()));
					numSelectionAndPipbForm.setDnSource(LtsConstant.DN_SOURCE_DN_RESERVED);
					acqOrderCapture.setCreate1stDelByReserveDnInd(true);
				}
				if (numSelectionAndPipbForm.getReservedDnList()!=null
						&& numSelectionAndPipbForm.getReservedDnList().size()>0) {
					for (LtsAcqNumberSelectionInfoDTO obj : numSelectionAndPipbForm.getReservedDnList()) {
						sList.add(obj.getSrvNum());
					}
				ltsAcqDnPoolService.updDnStatusToNormalBySrvNum(sList, LtsConstant.DN_POOL_STATUS_RESERVED, 
						numSelectionAndPipbForm.getSessionId(), staffId);	
				}
			}
			
			if (numSelectionAndPipbForm.getSearchMethod()==Method.RESERVED_DN_THEN_PIPB
					|| numSelectionAndPipbForm.getSearchMethod()==Method.DN_POOL_THEN_PIPB
					|| numSelectionAndPipbForm.getSearchMethod()==Method.PIPB_DN) {
				numSelectionAndPipbForm.getPipbInfo().setPipbSrvNum(
						LtsSbHelper.leftPadSrvNum(numSelectionAndPipbForm.getPipbInfo().getPipbSrvNum()));
				if (numSelectionAndPipbForm.getPipbInfo().isDuplexInd()) {
					numSelectionAndPipbForm.getPipbInfo().setDuplexDn(
							LtsSbHelper.leftPadSrvNum(numSelectionAndPipbForm.getPipbInfo().getDuplexDn()));
				}
				numSelectionAndPipbForm.getPipbInfo().setDnSource(LtsConstant.DN_SOURCE_DN_PIPB);					
			}
			
			if (numSelectionAndPipbForm.getSearchMethod()==Method.PIPB_DN) {
				acqOrderCapture.setCreate1stDelByReserveDnInd(false);
			}
			
			if (numSelectionAndPipbForm.getSearchMethod()==Method.DN_POOL
					|| numSelectionAndPipbForm.getSearchMethod()==Method.DN_POOL_THEN_PIPB) {
				numSelectionAndPipbForm.setDnSource(LtsConstant.DN_SOURCE_DN_POOL);
				acqOrderCapture.setCreate1stDelByReserveDnInd(false);
			}
			
			ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, numSelectionAndPipbForm.getSessionId(), staffId);
			
			// set default directory name for Non-BS customer
			if (LtsConstant.DOC_TYPE_HKID.equals(acqOrderCapture.getCustomerDetailProfileLtsDTO().getDocType())
					|| LtsConstant.DOC_TYPE_PASSPORT.equals(acqOrderCapture.getCustomerDetailProfileLtsDTO().getDocType())) {
				StringBuilder sb = new StringBuilder();
				
				String title = acqOrderCapture.getCustomerDetailProfileLtsDTO().getTitle();
				String lastName = acqOrderCapture.getCustomerDetailProfileLtsDTO().getLastName();
				String firstName = acqOrderCapture.getCustomerDetailProfileLtsDTO().getFirstName();
				
				if(title != null){
					sb.append(title + " ");
				}
				if(lastName != null){
					sb.append(lastName + " ");
				}
				if(firstName != null){
					sb.append(firstName);
				}
				
				numSelectionAndPipbForm.setDirectoryName(sb.toString());
			}
			
			acqOrderCapture.setLtsAcqNumConfirmationForm(null);
			
			break;
					
        case SEARCH_BY_NO_CRITERIA:
        case SEARCH_BY_FIRST_8_DIGITS:
        case SEARCH_BY_LAST_3_DIGITS: 
        	
        	ltsAcqDnPoolService.updDnStatusToNormal(
    				LtsConstant.DN_POOL_STATUS_LOCKED, numSelectionAndPipbForm.getSessionId());
        	dnList = searchDnFromDnPool(numSelectionAndPipbForm);        	
        	numSelectionAndPipbForm.setNumSelectionList(dnList);
        	if (dnList.size() == 0) {
    			errors.rejectValue("searchErrMsg", "lts.acq.dnSearch.noDnFound");
    			mav.addAllObjects(errors.getModel());
			}
        	mav.addObject(commandName, numSelectionAndPipbForm);
        	acqOrderCapture.setLtsAcqNumSelectionAndPipbForm(numSelectionAndPipbForm);
			return mav;
			
		case LOCK_NUMBER:					
			
				ltsAcqDnPoolService.updDnStatusToReserve(numSelectionAndPipbForm.getNumSelectionStringList(), 
						LtsConstant.DN_POOL_STATUS_LOCKED, numSelectionAndPipbForm.getSessionId(), staffId);
				logger.info("lock number, DN:"+numSelectionAndPipbForm.getNumSelectionStringList()+" salesID:"+staffId+", sbId:"+sbId);
				
				sList = new ArrayList<String>();
				dnList.addAll(numSelectionAndPipbForm.getNumSelectionList());
				dnList.addAll(numSelectionAndPipbForm.getReservedDnList());
				for (LtsAcqNumberSelectionInfoDTO obj : dnList) {
					sList.add(obj.getSrvNum());
				}
				numSelectionAndPipbForm.setNumSelectionList(getNumSelectionList(numSelectionAndPipbForm.getSessionId()));
				numSelectionAndPipbForm.setReservedDnList(getReservedDnList(numSelectionAndPipbForm.getSessionId(), sList));
			
			mav.addObject(commandName, numSelectionAndPipbForm);
			acqOrderCapture.setLtsAcqNumSelectionAndPipbForm(numSelectionAndPipbForm);
			return mav;	
			
        case RELEASE_NUMBER:
        	
	        	ltsAcqDnPoolService.releaseDnStatusToLockedByReservedDn(
	        			numSelectionAndPipbForm.getReservedDnStringList(), numSelectionAndPipbForm.getSessionId(), staffId);	
	        	logger.info("release number, DN:"+numSelectionAndPipbForm.getReservedDnStringList()+" salesID:"+staffId+", sbId:"+sbId);
	        	
	        	sList = new ArrayList<String>();	        	
				dnList.addAll(numSelectionAndPipbForm.getNumSelectionList());
				dnList.addAll(numSelectionAndPipbForm.getReservedDnList());
				for (LtsAcqNumberSelectionInfoDTO obj : dnList) {
					sList.add(obj.getSrvNum());
				}
	        	numSelectionAndPipbForm.setNumSelectionList(getNumSelectionList(numSelectionAndPipbForm.getSessionId()));
				numSelectionAndPipbForm.setReservedDnList(getReservedDnList(numSelectionAndPipbForm.getSessionId(), sList));
        	
			mav.addObject(commandName, numSelectionAndPipbForm);
			acqOrderCapture.setLtsAcqNumSelectionAndPipbForm(numSelectionAndPipbForm);
			return mav;	
			
		default:
			break;
		}
		
		acqOrderCapture.setLtsAcqNumSelectionAndPipbForm(numSelectionAndPipbForm);
		return new ModelAndView(new RedirectView(nextView));
		
    }
    
    private void setReferenceData(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture) throws Exception {
    	request.setAttribute("isBrCust", LtsConstant.DOC_TYPE_HKBR.equals(
				acqOrderCapture.getCustomerDetailProfileLtsDTO().getDocType())?true:false);		
		request.setAttribute("pipb2NPortingList", Arrays.asList(pipb2NPortingLkupCacheService.getCodeLkupDAO().getCodeLkup()));		
		request.setAttribute("titleList", Arrays.asList(newCustTitleAcqLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		request.setAttribute("isRecallOrder", acqOrderCapture.getSbOrder()!=null 
				&& acqOrderCapture.getSbOrder().getOrderId()!=null?true:false);
		request.setAttribute("sbOrderId", acqOrderCapture.getSbOrder()!=null?
				acqOrderCapture.getSbOrder().getOrderId():"");
		request.setAttribute("isChannelDS", acqOrderCapture.isChannelDirectSales());
		request.setAttribute("isContainPreInstallVAS", acqOrderCapture.isContainPreInstallVAS()?true:false);
		request.setAttribute("isDNOptionND", acqOrderCapture.isDNOptionND()?true:false);
		request.setAttribute("isDNOptionNP", acqOrderCapture.isDNOptionNP()?true:false);
		request.setAttribute("isDNOptionPD", acqOrderCapture.isDNOptionPD()?true:false);
    }
    
    // Search the DN list from DN pool
    private List<LtsAcqNumberSelectionInfoDTO> searchDnFromDnPool(LtsAcqNumSelectionAndPipbFormDTO form) {
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
				numberSelectionObj.setDisplaySrvNum(LtsSbHelper.getDisplaySrvNum(list.get(i)));
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
				numberSelectionObj.setDisplaySrvNum(LtsSbHelper.getDisplaySrvNum(list.get(i)));
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
				numberSelectionObj.setDisplaySrvNum(LtsSbHelper.getDisplaySrvNum(list.get(i)));
				result.add(numberSelectionObj);						
			}
		}
		return result;
    }
    
    private void validate(AcqOrderCaptureDTO acqOrderCapture, 
    		LtsAcqNumSelectionAndPipbFormDTO form, BindException errors) {
    	if (form.getFormAction()==Action.SUBMIT) {    		
    		if ((form.getReservedDnList()==null 
    				|| form.getReservedDnList().size()==0) 
    				&& (form.getSearchMethod()==Method.DN_POOL
    						|| form.getSearchMethod()==Method.DN_POOL_THEN_PIPB)) {
    			errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.required");
    		}
	    	if (LtsConstant.DOC_TYPE_HKBR.equals(acqOrderCapture.getCustomerDetailProfileLtsDTO().getDocType()) 
					&& StringUtils.isBlank(form.getDirectoryName())) {
				errors.rejectValue("directoryName", "lts.acq.directoryName.required");
			}
	    	if (acqOrderCapture.isContainPrewiringVAS()
	    			&& form.getNumSelection()==Selection.USE_PIPB_DN
	    			&& form.getPipbInfo().isDuplexInd() 
	    			&& form.getPipbInfo().getDuplexAction()==DuplexAction.PORT_IN_TOGETHER) {
	    		errors.rejectValue("pipbInfo.duplexRadio", "lts.acq.portInTogether.invalid");
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

	/**
	 * @return the pipb2NPortingLkupCacheService
	 */
	public CodeLkupCacheService getPipb2NPortingLkupCacheService() {
		return pipb2NPortingLkupCacheService;
	}

	/**
	 * @param pipb2nPortingLkupCacheService the pipb2NPortingLkupCacheService to set
	 */
	public void setPipb2NPortingLkupCacheService(
			CodeLkupCacheService pipb2nPortingLkupCacheService) {
		pipb2NPortingLkupCacheService = pipb2nPortingLkupCacheService;
	}

	/**
	 * @return the newCustTitleAcqLkupCacheService
	 */
	public CodeLkupCacheService getNewCustTitleAcqLkupCacheService() {
		return newCustTitleAcqLkupCacheService;
	}

	/**
	 * @param newCustTitleAcqLkupCacheService the newCustTitleAcqLkupCacheService to set
	 */
	public void setNewCustTitleAcqLkupCacheService(
			CodeLkupCacheService newCustTitleAcqLkupCacheService) {
		this.newCustTitleAcqLkupCacheService = newCustTitleAcqLkupCacheService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
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

