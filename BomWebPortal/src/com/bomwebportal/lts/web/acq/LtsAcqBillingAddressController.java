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

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAccountSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO.BillingAddrDtl;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqBillingAddressController extends SimpleFormController{

    protected final Log log = LogFactory.getLog(getClass());
    
	private final String nextView = "ltsacqbillmediabilllang.html";
    
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqBillingAddressController(){
		this.setFormView("lts/acq/ltsacqbillingaddress");	
		this.setCommandName("ltsbillingaddress");
		this.setCommandClass(LtsAcqBillingAddressFormDTO.class);
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqAccountSelectionFormDTO ltsAcqAccountSelectionFormDTO = orderCaptureDTO.getLtsAcqAccountSelectionForm();
		LtsAcqBillingAddressFormDTO ltsAcqBillingAddressFormDTO = orderCaptureDTO.getLtsAcqBillingAddressForm();
		
		if(ltsAcqBillingAddressFormDTO == null){
			ltsAcqBillingAddressFormDTO =  new LtsAcqBillingAddressFormDTO();
			List<BillingAddrDtl> dtlList = new ArrayList<BillingAddrDtl>();
			if(ltsAcqAccountSelectionFormDTO.isNewAccount()){
				BillingAddrDtl dtl = ltsAcqBillingAddressFormDTO.new BillingAddrDtl();
				dtl.setAcctNum(messageSource.getMessage("lts.acq.billingAddress.newAccount", new Object[] {}, this.locale));
				dtl.setBillingAddressTextArea(getLTSDisplayAddr(orderCaptureDTO.getLtsAddressRolloutForm()));
				dtl.setBaCaAction(LtsConstant.BILL_ADDR_ACTION_IA);
				dtlList.add(dtl);
			}else{
				for(AccountDetailProfileLtsDTO acct :orderCaptureDTO.getAccountDetailProfileLtsDTO()){
					BillingAddrDtl dtl = ltsAcqBillingAddressFormDTO.new BillingAddrDtl();
					dtl.setAcctNum(acct.getAcctNum());
					dtl.setBillingAddressTextArea(acct.getBillAddr());
					dtl.setBaCaAction(LtsConstant.BILL_ADDR_ACTION_EXISTING);
					dtlList.add(dtl);
				}
			}
			ltsAcqBillingAddressFormDTO.setBillingAddrDtlList(dtlList);
		}
		
		for(BillingAddrDtl dtl: ltsAcqBillingAddressFormDTO.getBillingAddrDtlList()){
			for(AccountDetailProfileLtsDTO acct :orderCaptureDTO.getAccountDetailProfileLtsDTO()){
				if(StringUtils.equals(dtl.getAcctNum(), acct.getAcctNum())){
					dtl.setAcctBillingAddress(acct.getBillAddr());
				}
			}
		}
			
		return ltsAcqBillingAddressFormDTO;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
	AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqBillingAddressFormDTO ltsAcqBillingAddressFormDTO = (LtsAcqBillingAddressFormDTO)command;
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
	
		for(BillingAddrDtl addrDtl:ltsAcqBillingAddressFormDTO.getBillingAddrDtlList())
		{
			String fullAddr = addrDtl.getBillingAddressTextArea();
			if(StringUtils.isNotBlank(fullAddr)){
				
//	            String addr = splitBillingAddress(fullAddr);
				
				String[] addrLines = fullAddr.split("\n");
				addrDtl.setAddrLine1(StringUtils.strip(addrLines[0]));
				addrDtl.setAddrLine2(addrLines.length > 1? StringUtils.strip(addrLines[1]) :null);
				addrDtl.setAddrLine3(addrLines.length > 2? StringUtils.strip(addrLines[2]) :null);
				addrDtl.setAddrLine4(addrLines.length > 3? StringUtils.strip(addrLines[3]) :null);
				addrDtl.setAddrLine5(addrLines.length > 4? StringUtils.strip(addrLines[4]) :null);
				addrDtl.setAddrLine6(addrLines.length > 5? StringUtils.strip(addrLines[5]) :null);
			}
		}
	
		orderCaptureDTO.setLtsAcqBillingAddressForm(ltsAcqBillingAddressFormDTO);
		
		return new ModelAndView(new RedirectView(nextView));
		
	}
	
	protected Map<String, String> referenceData(HttpServletRequest request) throws Exception {
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
//		String fullAddress = orderCaptureDTO.getAddressRollout().getFullAddress();
		String fullAddress = getLTSDisplayAddr(orderCaptureDTO.getLtsAddressRolloutForm());
		Map<String, String> referenceData = new HashMap<String, String>();

		
		referenceData.put("installAddress", fullAddress);
		
		return referenceData;
	}

	private String splitBillingAddress (String fullAddr)
	{
	    // Split the full address by adding "\n" into the string , 40 characters in max for one row
				String[] addrArray = StringUtils.split(fullAddr, ",");
				 String addr ="";
				 int i=0;
				 
				 while(i < addrArray.length)
				 {
					 if (i== addrArray.length)
					 {
						 addr += addrArray[i] +  "\n";
						 i++;
					 }
					 else{
						if ((addrArray[i] + addrArray[i + 1]).length() > 40 - 3 )
						{
							addr +=  addrArray[i] + "," + "\n";
							i++;
						} else
						{
							if (i+1== addrArray.length)
							{
								addr += addrArray[i] + "," + addrArray[i + 1] + "\n";
							}
							else{
							    addr += addrArray[i] + "," + addrArray[i + 1] + "," + "\n";
							}
							 i = i + 2;
						}
					 }
				 }
				 return fullAddr;
	}

	/**
	 * Convert the LTS address content in an Address object into free format.
	 */
	private static String getLTSDisplayAddr(LtsAddressRolloutFormDTO addrForm){
		String newlineChar = "\\n";
		StringBuilder sb = new StringBuilder();
		
			//line 1
			if (StringUtils.isNotEmpty(addrForm.getFlat())) {
				sb.append("FT ");
				sb.append(addrForm.getFlat());
			}
			if (StringUtils.isNotEmpty(addrForm.getFloor())) {
				if (StringUtils.isNotEmpty(addrForm.getFlat())) {
					sb.append(" ");
				}
				sb.append(addrForm.getFloor());
				sb.append("/F");
			}
			if (StringUtils.isNotEmpty(addrForm.getBuildingName())) {
				if (StringUtils.isNotEmpty(addrForm.getFlat()) ||
					StringUtils.isNotEmpty(addrForm.getFloor())) {
					sb.append(" ");
				}
				sb.append(addrForm.getBuildingName());
			}
			if (StringUtils.isNotEmpty(addrForm.getFlat()) ||
				StringUtils.isNotEmpty(addrForm.getFloor()) ||
				StringUtils.isNotEmpty(addrForm.getBuildingName())) {
				sb.append(newlineChar);
			}
			//line 2
			if (StringUtils.isNotEmpty(addrForm.getLotNum())) {
				sb.append("LAND LOT NO ");
				sb.append(addrForm.getLotNum());
				sb.append(newlineChar);
			} else {
				if (StringUtils.isNotEmpty(addrForm.getStreetNum())) {
					sb.append(addrForm.getStreetNum());
				}
				if (StringUtils.isNotEmpty(addrForm.getStreetName())) {
					if (StringUtils.isNotEmpty(addrForm.getStreetNum())) {
						sb.append(" ");
					}
					sb.append(addrForm.getStreetName());
					sb.append(" ");
					sb.append(StringUtils.trimToEmpty(addrForm.getStreetCatgDesc()));
				}
				if (StringUtils.isNotEmpty(addrForm.getStreetNum()) ||
					StringUtils.isNotEmpty(addrForm.getStreetName())) {
					sb.append(newlineChar);
				}
			}
			//line 3
			if (StringUtils.isNotEmpty(addrForm.getSectionDesc())) {
				sb.append(addrForm.getSectionDesc());
				sb.append(newlineChar);
			}
			//line 4
			if (StringUtils.isNotEmpty(addrForm.getDistrictDesc())) {
				sb.append(addrForm.getDistrictDesc());
			}
			if (StringUtils.isNotEmpty(addrForm.getAreaDesc())) {
				if (StringUtils.isNotEmpty(addrForm.getDistrictDesc())) {
					sb.append(" ");
				}
				sb.append(addrForm.getAreaDesc());
			}


		// [start] HW on 20071204
		// Bug Fix - DM Campaign Problem (Invalid Chars Find in DRG BA)
		//return addrBuffer.toString();
	    String tab = String.valueOf((char)9);
		return sb.toString().replaceAll(tab,"");
		// [end] HW on 20071204
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
