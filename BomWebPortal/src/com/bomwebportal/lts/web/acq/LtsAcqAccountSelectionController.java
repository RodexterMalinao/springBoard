package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
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

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAccountSelectionFormDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileAcqDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.acq.LtsAcqAccountProfileService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqAccountSelectionController extends SimpleFormController{

    protected final Log log = LogFactory.getLog(getClass());
    
    private LtsAcqAccountProfileService ltsAcqAccountProfileService =null;
	private CustomerProfileLtsService customerProfileLtsService = null;
	private LtsCommonService ltsCommonService;
    
	private Locale locale;
	private MessageSource messageSource;
	
	private final String nextView = "ltsacqbillingaddress.html";
    	
	public LtsAcqAccountSelectionController(){
		this.setFormView("lts/acq/ltsacqaccountselection");	
		this.setCommandName("ltsacctselect");
		this.setCommandClass(LtsAcqAccountSelectionFormDTO.class);
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		//}else if(orderCapture.isChannelDirectSales()){
		//	return new ModelAndView(new RedirectView(nextView));
		}else if (orderCapture.getLtsAcqPersonalInfoForm().isCreateNewCust()) {
			// New customer, skip account selection page
			AccountDetailProfileLtsDTO[] newAcctArray = {new AccountDetailProfileLtsDTO()};
			orderCapture.setAccountDetailProfileLtsDTO(newAcctArray);			
			LtsAcqAccountSelectionFormDTO ltsAcqAccountSelectionForm = new LtsAcqAccountSelectionFormDTO();
			ltsAcqAccountSelectionForm.setNewAccount(true);
			ltsAcqAccountSelectionForm.setNewAccountSelected(true);
			orderCapture.setLtsAcqAccountSelectionForm(ltsAcqAccountSelectionForm);	
			
			//System.out.println("DS acc select nextView:"+nextView);
			
			return new ModelAndView(new RedirectView(nextView));
		}
				
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqAccountSelectionFormDTO ltsAcqpAccountselectionFormDTO = orderCaptureDTO.getLtsAcqAccountSelectionForm();
		
		if(ltsAcqpAccountselectionFormDTO == null){
			ltsAcqpAccountselectionFormDTO =  new LtsAcqAccountSelectionFormDTO();
		}

		if(ltsCommonService.isNowDrgDownTime()&&true){
			request.setAttribute("isNowDrgDownTime", true);
		}
		
		if(orderCaptureDTO.getLtsAcqBasketSelectionForm()!=null && orderCaptureDTO.getLtsAcqBasketSelectionForm().isDelFreeBundle())
		{
			request.setAttribute("isDelFree", true);
			ltsAcqpAccountselectionFormDTO.setNewAccount(true);
			ltsAcqpAccountselectionFormDTO.setNewAccountSelected(true);
		}
		
		return ltsAcqpAccountselectionFormDTO;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
	    AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqAccountSelectionFormDTO ltsAcqAccountSelectionForm = (LtsAcqAccountSelectionFormDTO)command;
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		if(!ltsAcqAccountSelectionForm.isNewAccount())
		{
			AccountDetailProfileLtsDTO selectAcctInfo = new AccountDetailProfileLtsDTO();
			AccountDetailProfileLtsDTO acctInfo = new AccountDetailProfileLtsDTO();
			List<AccountDetailProfileLtsDTO> acctInfoList = new ArrayList<AccountDetailProfileLtsDTO>();
			List<AccountDetailProfileAcqDTO> acctList = (List<AccountDetailProfileAcqDTO>) request.getSession().getAttribute("acctList");

			String index = ltsAcqAccountSelectionForm.getSelectedAccount();
			ltsAcqAccountSelectionForm.setNewAccountSelected(false);
			
			if(StringUtils.isBlank(index)){
				return new ModelAndView(new RedirectView("ltsacqaccountselection.html"));
			}
			
			AccountDetailProfileAcqDTO selectAcct = acctList.get(Integer.parseInt(index));

			selectAcctInfo = customerProfileLtsService.getAccountbyAcctNum(selectAcct.getAcctNum(), LtsConstant.SYSTEM_ID_DRG);
			selectAcctInfo.setAcctChrgType(selectAcct.getChargeType());
			
			if(StringUtils.isBlank(selectAcct.getSrvNum()) || StringUtils.isBlank(selectAcct.getChargeType())){
				selectAcctInfo.setAcctChrgType(LtsConstant.CUSTOMER_ACCOUNT_CHARGE_TYPE_RIAP);
				selectAcctInfo.setPrimaryAcctInd(true);
			}
			
			acctInfoList.add(selectAcctInfo);

			for (AccountDetailProfileAcqDTO temp : acctList) {
				if (StringUtils.isNotBlank(selectAcct.getSrvNum()) && StringUtils.equals(temp.getSrvNum(), selectAcct.getSrvNum()) 
						&& !StringUtils.equals(temp.getAcctNum(), selectAcct.getAcctNum()))
				{
					acctInfo = customerProfileLtsService.getAccountbyAcctNum(temp.getAcctNum(), LtsConstant.SYSTEM_ID_DRG);
					acctInfo.setAcctChrgType(temp.getChargeType());
					acctInfoList.add(acctInfo);
					break;
				}
			}

			for(AccountDetailProfileLtsDTO acctProrile: acctInfoList){
				if(StringUtils.contains(acctProrile.getAcctChrgType(), 'R')){
					acctProrile.setPrimaryAcctInd(true);
					break;
				}
			}
			
			orderCaptureDTO.setAccountDetailProfileLtsDTO(acctInfoList.toArray(new AccountDetailProfileLtsDTO[acctInfoList.size()]));
		}
		else
		{
			AccountDetailProfileLtsDTO[] newAcctArray = {new AccountDetailProfileLtsDTO()};
			orderCaptureDTO.setAccountDetailProfileLtsDTO(newAcctArray);
			ltsAcqAccountSelectionForm.setNewAccountSelected(true);
		}
		
		orderCaptureDTO.setLtsAcqAccountSelectionForm(ltsAcqAccountSelectionForm);
		orderCaptureDTO.setLtsAcqBillMediaBillLangForm(null);
		orderCaptureDTO.setLtsAcqPaymentMethodFormDTO(null);
		orderCaptureDTO.setLtsAcqBillingAddressForm(null);
		
		return new ModelAndView(new RedirectView(nextView));
		
		
	}
	
	protected Map<String, List> referenceData(HttpServletRequest request) throws Exception {
//		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");		
		Map<String, List> referenceData = new HashMap<String, List>();
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		List<AccountDetailProfileAcqDTO> acctList = new ArrayList<AccountDetailProfileAcqDTO>();
		CustomerDetailProfileLtsDTO cust = orderCaptureDTO.getCustomerDetailProfileLtsDTO();
		
		String custNum  = orderCaptureDTO.getCustomerDetailProfileLtsDTO().getCustNum();
	    
//		String custNum = "14000186";
		
		acctList=this.ltsAcqAccountProfileService.getAcctListByCustNum(custNum);
			
		if(acctList != null && acctList.size() > 0){
			for(AccountDetailProfileAcqDTO acct : acctList){
				if(acct != null){
					if(acct.getChargeType() == null){
						acct.setChargeType(LtsConstant.CUSTOMER_ACCOUNT_CHARGE_TYPE_RIAP);

					}

					String chargeType = LtsConstant.CUSTOMER_ACCOUNT_CHARGE_TYPE_MAP.get(acct.getChargeType());
					if(StringUtils.isNotBlank(chargeType)){
						acct.setDisplayChargeType(chargeType);
					}
				}
			}
		}
		
		request.getSession().setAttribute("acctList", acctList);
		
		referenceData.put("acctList", acctList);
		
		return referenceData;
	}
	
	public String getKeyByDesc(LookupItemDTO[] lkupItems, String desc){
		for(LookupItemDTO lkupItem: lkupItems){
			if(StringUtils.equals((String)(lkupItem.getItemValue()), desc)){
				return (String)(lkupItem.getItemValue());
			}
		}
		return null;
	}

	public LtsAcqAccountProfileService getLtsAcqAccountProfileService() {
		return ltsAcqAccountProfileService;
	}

	public void setLtsAcqAccountProfileService(
			LtsAcqAccountProfileService ltsAcqAccountProfileService) {
		this.ltsAcqAccountProfileService = ltsAcqAccountProfileService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
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
