package com.bomwebportal.lts.wsClientLts;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO.BillMediaDtl;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.pccw.custProfileGateway.acctInfo.AccountDTO;
import com.pccw.custProfileGateway.acctInfo.AccountInfoDTO;
import com.pccw.custProfileGateway.acctInfo.CreateAccountInfo;
import com.pccw.custProfileGateway.acctInfo.CreateAccountInfoResponse;
import com.pccw.custProfileGateway.acctInfo.CustAddressDTO;
import com.pccw.custProfileGateway.acctInfo.CustFuturePaymentMethodDTO;


public class BomCreateAccountWsClient {

	private WebServiceTemplate webServiceTemplate;	

	private final Log logger = LogFactory.getLog(getClass());	
	
	public AccountDTO ltsAcqCreateNewAcct(CustomerDetailProfileLtsDTO cust, BillMediaDtl bill,  LtsAddressRolloutFormDTO addressRollout, LtsAcqBillingAddressFormDTO billAddr, boolean isEyeOrder, String userId) {

		try {
	    	AccountInfoDTO acct = new AccountInfoDTO();
	    	
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    	String custTitle = "";
	    	String acctName = "";
	    	
	    	if(LtsConstant.DOC_TYPE_HKID.equals(cust.getDocType())
					  || LtsConstant.DOC_TYPE_PASSPORT.equals(cust.getDocType())) {
	    		if(StringUtils.isNotBlank(cust.getTitle())){
	    			custTitle=cust.getTitle()+ " ";
	    		}
	    		acctName = custTitle + cust.getLastName()+ " " + cust.getFirstName();
	    		acct.setAcctName(acctName.toUpperCase());	    		
	    	} else if (LtsConstant.DOC_TYPE_HKBR.equals(cust.getDocType())) {
	    		acct.setAcctName(cust.getCompanyName().toUpperCase());
	    	}	    	
	    	acct.setAcctType("T");
	    	acct.setAcctSubType(" ");
	    	acct.setBillFreq(isEyeOrder?LtsConstant.ACCT_BILL_FREQ_MONTHLY:LtsConstant.ACCT_BILL_FREQ_QUATERLY);	    	
	    	acct.setBillLang(bill.getBillLang());
	    	acct.setCustCatg(cust.getCustCatg());
	    	acct.setCustNum(cust.getCustNum());
	    	acct.setEffStartDate(sdf.format(date));
	    	acct.setLastUpdBy(userId);	    	
	    	acct.setNetvigatorInd("Y");	    	
	    		    	
	    	acct.setSystemId(LtsConstant.SYSTEM_ID_DRG);
	    	
	    	CustAddressDTO address = new CustAddressDTO();	    	
	    	address.setAddrIDStr("-1");
	    	address.setAddrID(-1);
	    	address.setAddrType("F");
	    	address.setAddrUsage("BA");
	    	address.setAreaCode(addressRollout.getAreaCode());
	    	address.setAreaDesc(addressRollout.getAreaDesc());
	    	address.setBuildNum(addressRollout.getBuildingName());
	    	address.setDistrict(addressRollout.getDistrictDesc());
	    	address.setDistrictNum(addressRollout.getDistrictCode());
	    	address.setPostalCode("0");
	    	address.setFloorNum(addressRollout.getFloor());
	    	address.setForeignAddrFlag("N");
	    	address.setStreetCatCode(addressRollout.getStreetCatgCode());
	    	address.setStreetCatCodeDesc(addressRollout.getStreetCatgDesc());
	    	address.setStreetName(addressRollout.getStreetName());
	    	address.setStreetNum(addressRollout.getStreetNum());
	    	address.setUnitNum(addressRollout.getFlat());
	    	address.setAddrLine1(billAddr.getBillingAddrDtlList().get(0).getAddrLine1()); // Get the strings in the only billAddressDtl in the list
	    	address.setAddrLine2(billAddr.getBillingAddrDtlList().get(0).getAddrLine2());
	    	address.setAddrLine3(billAddr.getBillingAddrDtlList().get(0).getAddrLine3());
	    	address.setAddrLine4(billAddr.getBillingAddrDtlList().get(0).getAddrLine4());
	    	address.setAddrLine5(billAddr.getBillingAddrDtlList().get(0).getAddrLine5());
	    	address.setAddrLine6(billAddr.getBillingAddrDtlList().get(0).getAddrLine6());
	    	address.setLastUpdBy(userId);
	    	address.setSrvId(LtsConstant.SYSTEM_ID_DRG);
	    	address.setModify(true);
	    	acct.setCustAddressDTO(address);
	    	
	    	CustFuturePaymentMethodDTO payment = new CustFuturePaymentMethodDTO();	    	
	    	if(bill.getEmailBillItem()!=null && bill.getEmailBillItem().isSelected()) {
	    		payment.setBillMedia(LtsConstant.LTS_BILL_MEDIA_PPS_BILL);
	    		acct.setEmailAddr(bill.getBillMediaEmail());
	    	} else if (bill.getPaperBillItem()!=null && bill.getPaperBillItem().isSelected()) {
		    	payment.setBillMedia(LtsConstant.LTS_BILL_MEDIA_PAPER_BILL);
	    	}
	    	payment.setBillFmt("Y");
	    	payment.setCustNum(cust.getCustNum());
	    	payment.setEffStartDate(sdf.format(date));
	    	payment.setPayMethodType(LtsConstant.PAYMENT_TYPE_CASH);
	    	payment.setSystemId(LtsConstant.SYSTEM_ID_DRG);
	    	acct.setCustFuturePaymentMethodDTO(payment);
			
	    	CreateAccountInfo request = new CreateAccountInfo();
	    	request.setAccountInfoDTO(acct);
			  
			CreateAccountInfoResponse response = (CreateAccountInfoResponse) webServiceTemplate.marshalSendAndReceive(request);
			
			if (!"0".equals(response.getCreateAccountInfoResult().getErrorSeverity())) {
			     throw new Exception();
			}
			
			return response.getCreateAccountInfoResult().getAccountDTO();
		
		} catch (Exception ex) {
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}
	}
	
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}
}