package com.bomwebportal.wsclient;

import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.Stub;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pccw.bom.mob.CreateBulk3GNewAct;
import com.pccw.bom.mob.CreateBulk3GNewActSoap;
import com.pccw.bom.mob.CreateBulk3GNewAct_Impl;
import com.pccw.bom.mob.schemas.CustomerDTO;
import com.pccw.bom.mob.schemas.CustomerResultDTO;
import com.pccw.bom.mob.schemas.ResultDTO;

import weblogic.webservice.context.WebServiceContext;
import weblogic.webservice.context.WebServiceSession;
import weblogic.webservice.core.handler.WSSEClientHandler;
import weblogic.xml.security.UserInfo;
import weblogic.xml.security.wsse.Security;
import weblogic.xml.security.wsse.SecurityElementFactory;

import com.bomwebportal.dto.SalesmanDTO;
import com.bomwebportal.exception.*;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.*;

import java.util.*;
import java.rmi.RemoteException;

import com.pccw.bom.mob.CreateBulk3GNewAct;
import com.pccw.bom.mob.CreateBulk3GNewActSoap;
import com.pccw.bom.mob.CreateBulk3GNewAct_Impl;
import com.pccw.bom.mob.schemas.AccountDTO;
import com.pccw.bom.mob.schemas.AccountResultDTO;
import com.pccw.bom.mob.schemas.ActualUserDTO;
import com.pccw.bom.mob.schemas.AddressDTO;
import com.pccw.bom.mob.schemas.AddressMaintDTO;
import com.pccw.bom.mob.schemas.ContactInfoDTO;
import com.pccw.bom.mob.schemas.CreateOrdReqDTO;
import com.pccw.bom.mob.schemas.CreateOrdResultDTO;
import com.pccw.bom.mob.schemas.CustomerDTO;
import com.pccw.bom.mob.schemas.CustomerResultDTO;
import com.pccw.bom.mob.schemas.MnpDTO;
import com.pccw.bom.mob.schemas.PaymentDTO;
import com.pccw.bom.mob.schemas.ProductDTO;
import com.pccw.bom.mob.schemas.SalesmanLookUpDTO;
import com.pccw.bom.mob.schemas.SecurityUserDTO;
import com.pccw.bom.mob.schemas.SimDTO;
import com.pccw.bom.mob.schemas.SubscriberDTO;
import com.bomwebportal.wsclient.CnmClient;
import com.bomwebportal.wsclient.HwInvClient;
import com.bomwebportal.util.BomWebPortalConstant;

public class BulkNewActClient {
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private OrderService orderService;
    	
	private String wsdl;
		
	private String bulkNewActSecurityUser;
	
	private String bulkNewActSecurityPassword;
	
	private  CreateBulk3GNewAct createBulk3GNewAct = null;	
	private  CreateBulk3GNewActSoap createBulk3GNewActSoap = null;
	private  CustomerResultDTO customerResultDTO = null;
	private  CustomerResultDTO newCustomerResultDTO = null;
	private  AccountResultDTO newAccountResultDTO = null;
	private  CreateOrdResultDTO createOrdResultDTO = null;
	private  boolean isHwCallSuccess = false;
	private  boolean isCnmCallSuccess = false;
	
	private  CnmClient cnmClient;
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public OrderService getOrderService() {
		return orderService;
	}
	
	public CnmClient getCnmClient() {
		return cnmClient;
	}

	public void setCnmClient(CnmClient cnmClient) {
		this.cnmClient = cnmClient;
	}

	public HwInvClient getHwInvClient() {
		return hwInvClient;
	}

	public void setHwInvClient(HwInvClient hwInvClient) {
		this.hwInvClient = hwInvClient;
	}

	private  HwInvClient hwInvClient;
	
	public String getBulkNewActSecurityUser() {
		return bulkNewActSecurityUser;
	}

	public void setBulkNewActSecurityUser(String bulkNewActSecurityUser) {
		this.bulkNewActSecurityUser = bulkNewActSecurityUser;
	}

	public String getBulkNewActSecurityPassword() {
		return bulkNewActSecurityPassword;
	}

	public void setBulkNewActSecurityPassword(String bulkNewActSecurityPassword) {
		this.bulkNewActSecurityPassword = bulkNewActSecurityPassword;
	}

	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	/*
	 * This main function is for testing uses
	 * It would not run in web application!
	 */
	public static void main(String arg[]){
		
		String refNum = new String();
		CustomerDTO customerDTO = null;
		CustomerResultDTO customerResultDTO = new CustomerResultDTO();
		CreateBulk3GNewActSoap createBulk3GNewActSoap = null;
		try {
		for(int i=0; i<200; i++){
			BulkNewActClient client = new BulkNewActClient();
			customerDTO = new CustomerDTO();
			//setParaForCreateCust(customerDTO);
			refNum = "1";
			customerDTO.setSystemId("MOB");
			customerDTO.setIdDocType("HKID");
			customerDTO.setIdDocNum("E397939(7)");
			//CreateBulk3GNewAct service = new CreateBulk3GNewAct_Impl("http://10.87.122.15:7401/BOM_MOB_SALESTOOL_Web/ws/CreateBulk3GNewAct.jws?WSDL");
			CreateBulk3GNewAct service = new CreateBulk3GNewAct_Impl("http://bomeaiprd:7081/BOM_MOB_SALESTOOL_Web/ws/CreateBulk3GNewAct.jws?WSDL");
			client.setBulkNewActSecurityUser("pSalestoolUser");
			client.setBulkNewActSecurityPassword("salestoolUser");
			client.setSecurity(service);
			createBulk3GNewActSoap = service.getCreateBulk3GNewActSoap();
			customerResultDTO = createBulk3GNewActSoap.searchMobCustomer(refNum, customerDTO);
			if (customerResultDTO!=null){
				System.out.println("Result ["+i+"]: CustNum:" + customerResultDTO.getCustNum() + "\n BomErrCode():" + customerResultDTO.getErrCode() + "\n ErrMsg:" + customerResultDTO.getErrMsg());
			}
			//return customerResultDTO;
		}
		} catch (JAXRPCException jre) {
			jre.printStackTrace();
			throw new AppRuntimeException(jre);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new AppRuntimeException(e);
		}				
	}
	
	public void bulkNewActClient(String refNum, CustomerDTO pCustomerDTO, AccountDTO pAccountDTO, CreateOrdReqDTO pCreateOrdReqDTO, String loginUser){
		try {			
			logger.info("Call bulkNewActClient to create BOM order");
			if (refNum==null || pCustomerDTO==null || pAccountDTO==null || pCreateOrdReqDTO==null){
				throw new WsOrderException(WsConstants.ERR_STATUS_INVALID_INPUT, WsConstants.ERR_MSG_INVALID_INPUT);
			}
			logger.info("refNum:" + refNum + " ;pCustomerDTO:"+pCustomerDTO.getIdDocType()+pCustomerDTO.getIdDocNum()+" ;getMsisdn:"+pCreateOrdReqDTO.getMsisdn()+" ;getIccid:"+pCreateOrdReqDTO.getSimDTO().getIccid());
			logger.info("processing " + loginUser + " order. - refNum:" + refNum );
			logger.info("Search Customer Profile by Document# - refNum:" + refNum);
			//---Customer Lvl ---
			customerResultDTO = getWebServiceSOAP().searchMobCustomer(refNum, pCustomerDTO);
			logger.info("Customer number: " + customerResultDTO.getCustNum());
			if (customerResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(customerResultDTO.getErrCode())){
				if ("".equals(customerResultDTO.getCustNum()) || "".equals(customerResultDTO.getBomCustNum())){
					logger.info("Create mobile customer profile - refNum:" + refNum);
					newCustomerResultDTO = getWebServiceSOAP().createMobCustomer(refNum, pCustomerDTO);	
					logger.info("Create mobile customer profile -- Return from createrMobCustomer - refNum:" + refNum);
					if (newCustomerResultDTO==null || "".equals(newCustomerResultDTO.getCustNum()) || newCustomerResultDTO.getCustNum()==null ){
						logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
						throw new WsOrderException(newCustomerResultDTO.getErrCode(), newCustomerResultDTO.getErrMsg());
					}
					logger.info("Set custNum["+newCustomerResultDTO.getCustNum()+"] to the accountDTO - refNum:" + refNum);
					pAccountDTO.setCustNum(newCustomerResultDTO.getCustNum());
					
					logger.info("Set custNum["+newCustomerResultDTO.getCustNum()+"] and mobCustNum["+newCustomerResultDTO.getCustNum()+"] to createOrdReqDTO - refNum:" + refNum);
					pCreateOrdReqDTO.setBomCustNum(newCustomerResultDTO.getBomCustNum());
					pCreateOrdReqDTO.setMobCustNum(newCustomerResultDTO.getCustNum());
				} else {
					logger.info("Set custNum["+customerResultDTO.getCustNum()+"] to the accountDTO - refNum:" + refNum);
					pAccountDTO.setCustNum(customerResultDTO.getCustNum());
					
					logger.info("Set custNum["+customerResultDTO.getCustNum()+"] and mobCustNum["+customerResultDTO.getCustNum()+"] to createOrdReqDTO - refNum:" + refNum);
					pCreateOrdReqDTO.setBomCustNum(customerResultDTO.getBomCustNum());
					pCreateOrdReqDTO.setMobCustNum(customerResultDTO.getCustNum());
				}
			} else if (customerResultDTO!=null){
				logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
				throw new WsOrderException(customerResultDTO.getErrCode(), customerResultDTO.getErrMsg());
			} else {
				logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
				throw new WsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_OTHERS_ERROR);
			}
			//---Account Lvl ---
			logger.info("New account profile in BOM - refNum:" + refNum);
			newAccountResultDTO = getWebServiceSOAP().createMobAccount(refNum, pAccountDTO);
			if (newAccountResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(newAccountResultDTO.getErrCode())){
				logger.info("Set accountNum["+newAccountResultDTO.getAcctNum()+"] to createOrdReqDTO - refNum:" + refNum);
				pCreateOrdReqDTO.setAcctNum(newAccountResultDTO.getAcctNum());
			} else if (newAccountResultDTO!=null){
				logger.error("Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum);
				throw new WsOrderException(newAccountResultDTO.getErrCode(), newAccountResultDTO.getErrMsg());
			} else {
				logger.error("Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum);
				throw new WsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_OTHERS_ERROR);
			}
			//---INV Lvl ---
			//cnmClient = new CnmClient();
			//hwInvClient = new HwInvClient();
			String[] iccid = new String[1];
			iccid[0] = pCreateOrdReqDTO.getSimDTO().getIccid();
			
			isHwCallSuccess = hwInvClient.updateSIM("", "", WsConstants.HW_INV_STATUS_NORMAL, WsConstants.HW_INV_STATUS_SOLD, iccid, WsConstants.OPER_CODE);
			logger.info("Return from HW call status is " + isHwCallSuccess + " - refNum:" + refNum);
			if (!isHwCallSuccess){				
				throw new WsOrderException(WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE);
			}
			
			if (!"Y".equals(pCreateOrdReqDTO.getMnpInd())){
				logger.info("MNP case - refNum:" + refNum);
				isCnmCallSuccess = cnmClient.updateMSISDN(pCreateOrdReqDTO.getMsisdn(), WsConstants.CNM_STATUS_NORMAL, WsConstants.CNM_STATUS_SOLD, WsConstants.CNM_TYPE, "", WsConstants.INV_MOB_NO_TYP_CD_3G, null);
				logger.info("Return from CNM call status is " + isCnmCallSuccess + "- refNum:" + refNum);
				if (!isCnmCallSuccess){
					updateBulkReqStatus(refNum, BomWebPortalConstant.BWP_ORDER_FAILED, WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE, loginUser);
					throw new WsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE);
				}
			}
			
			//---Order Lvl ---
			logger.info("Create order request in BOM - refNum:" + refNum);
			createOrdResultDTO = getWebServiceSOAP().createBomOrder(refNum, pCreateOrdReqDTO);
			logger.info("Create order request in BOM -- Return from createBomOrder - refNum:" + refNum);
			if (createOrdResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(createOrdResultDTO.getErrCode())){
				logger.info("The BOM order " + createOrdResultDTO.getRefNum() + " is created successful in BOM and the OCID is "+createOrdResultDTO.getOcid() + "- refNum:" + refNum);
				updateBulkReqStatusSuccess(refNum, createOrdResultDTO.getOcid(), BomWebPortalConstant.BWP_ORDER_SUCCESS, WsConstants.ERR_STATUS_NO_ERROR, loginUser);
				
				
			}else if (createOrdResultDTO!=null){
				logger.error("The BOM order is created failure and the error message is ["+createOrdResultDTO.getErrMsg()+"] - refNum:" + refNum);
				updateBulkReqStatus(refNum, BomWebPortalConstant.BWP_ORDER_FAILED, createOrdResultDTO.getErrCode(), createOrdResultDTO.getErrMsg(), loginUser);
				throw new WsOrderException(createOrdResultDTO.getErrCode(), createOrdResultDTO.getErrMsg());
			} else {
				logger.error("The BOM order is created failure and the error message is ["+WsConstants.ERR_STATUS_OTHERS_ERROR+"] - refNum:" + refNum);
				updateBulkReqStatus(refNum, BomWebPortalConstant.BWP_ORDER_FAILED, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_STATUS_OTHERS_ERROR, loginUser);
				throw new WsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_STATUS_OTHERS_ERROR);
			}

		}catch (WsOrderException wsOrderException){
			logger.error(wsOrderException.getMessage());
			updateBulkReqStatus(refNum, BomWebPortalConstant.BWP_ORDER_FAILED, wsOrderException.getErrCode(), wsOrderException.getMessage(), loginUser);
		}catch (RemoteException ex){
			logger.error(ex.getMessage());
		}catch (Exception ex){
			logger.error(ex.getMessage());
		}
		return;
	}

	public void bulkNewActCcsClient(String refNum, CustomerDTO pCustomerDTO, AccountDTO pAccountDTO, CreateOrdReqDTO pCreateOrdReqDTO, String loginUser){
		try {			
			logger.info("Call bulkNewActCcsClient to create CCS BOM order");
			if (refNum==null || pCustomerDTO==null || pAccountDTO==null || pCreateOrdReqDTO==null){
				updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_OTHERS_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_OTHERS_ERROR, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_INVALID_INPUT, WsConstants.ERR_MSG_INVALID_INPUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CREATE_ORDER_FAIL);
			}
			logger.info("refNum:" + refNum + " ;pCustomerDTO:"+pCustomerDTO.getIdDocType()+pCustomerDTO.getIdDocNum()+" ;getMsisdn:"+pCreateOrdReqDTO.getMsisdn()+ " ;getMsisdnLvl:"+ pCreateOrdReqDTO.getMsisdnLvl()+" ;getIccid:"+pCreateOrdReqDTO.getSimDTO().getIccid());
			logger.info("processing " + loginUser + " order. - refNum:" + refNum );
			logger.info("Search Customer Profile by Document# - refNum:" + refNum);
			//---Customer Lvl ---
			customerResultDTO = getWebServiceSOAP().searchMobCustomer(refNum, pCustomerDTO);
			logger.info("Customer number: " + customerResultDTO.getCustNum());
			if (customerResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(customerResultDTO.getErrCode())){
				if ("".equals(customerResultDTO.getCustNum()) || "".equals(customerResultDTO.getBomCustNum())){
					logger.info("Create mobile customer profile - refNum:" + refNum);
					newCustomerResultDTO = getWebServiceSOAP().createMobCustomer(refNum, pCustomerDTO);	
					logger.info("Create mobile customer profile -- Return from createrMobCustomer - refNum:" + refNum);
					if (newCustomerResultDTO==null || "".equals(newCustomerResultDTO.getCustNum()) || newCustomerResultDTO.getCustNum()==null ){
						logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
						//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_CUST_PROFILE_FAIL, loginUser);
						throw new WsCcsOrderException(newCustomerResultDTO.getErrCode(), newCustomerResultDTO.getErrMsg(),BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL);
					}
					logger.info("Set custNum["+newCustomerResultDTO.getCustNum()+"] to the accountDTO - refNum:" + refNum);
					pAccountDTO.setCustNum(newCustomerResultDTO.getCustNum());
					
					logger.info("Set custNum["+newCustomerResultDTO.getCustNum()+"] and mobCustNum["+newCustomerResultDTO.getCustNum()+"] to createOrdReqDTO - refNum:" + refNum);
					pCreateOrdReqDTO.setBomCustNum(newCustomerResultDTO.getBomCustNum());
					pCreateOrdReqDTO.setMobCustNum(newCustomerResultDTO.getCustNum());
				} else {
					logger.info("Set custNum["+customerResultDTO.getCustNum()+"] to the accountDTO - refNum:" + refNum);
					pAccountDTO.setCustNum(customerResultDTO.getCustNum());
					
					logger.info("Set custNum["+customerResultDTO.getCustNum()+"] and mobCustNum["+customerResultDTO.getCustNum()+"] to createOrdReqDTO - refNum:" + refNum);
					pCreateOrdReqDTO.setBomCustNum(customerResultDTO.getBomCustNum());
					pCreateOrdReqDTO.setMobCustNum(customerResultDTO.getCustNum());
				}
			} else if (customerResultDTO!=null){
				logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_CUST_PROFILE_FAIL, loginUser);
				throw new WsCcsOrderException(customerResultDTO.getErrCode(), customerResultDTO.getErrMsg(),BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL);
			} else {
				logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_CUST_PROFILE_FAIL, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_OTHERS_ERROR,BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL);
			}
			//---Account Lvl ---
			logger.info("New account profile in BOM - refNum:" + refNum);
			newAccountResultDTO = getWebServiceSOAP().createMobAccount(refNum, pAccountDTO);
			if (newAccountResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(newAccountResultDTO.getErrCode())){
				logger.info("Set accountNum["+newAccountResultDTO.getAcctNum()+"] to createOrdReqDTO - refNum:" + refNum);
				pCreateOrdReqDTO.setAcctNum(newAccountResultDTO.getAcctNum());
			} else if (newAccountResultDTO!=null){
				logger.error("Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_ACCT_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, "Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum, loginUser);
				throw new WsCcsOrderException(newAccountResultDTO.getErrCode(), newAccountResultDTO.getErrMsg(),BomWebPortalConstant.BWP_MOBCCS_BOM_ACCT_PROFILE_FAIL);
			} else {
				logger.error("Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_ACCT_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, "Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_OTHERS_ERROR,BomWebPortalConstant.BWP_MOBCCS_BOM_ACCT_PROFILE_FAIL);
			}
			//---INV Lvl ---
			//cnmClient = new CnmClient();
			//hwInvClient = new HwInvClient();
			
			String[] iccid = new String[1];
			iccid[0] = pCreateOrdReqDTO.getSimDTO().getIccid();
			
			logger.info("INV (HW SIM) Lvl :" +iccid[0]);
			
			isHwCallSuccess = hwInvClient.updateSIM("", "", WsConstants.HW_INV_STATUS_NORMAL, WsConstants.HW_INV_STATUS_SOLD, iccid, WsConstants.OPER_CODE);
			logger.info("Return from HW call status is " + isHwCallSuccess + " - refNum:" + refNum);
			if (!isHwCallSuccess){				
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL, WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE,BomWebPortalConstant.BWP_MOBCCS_BOM_SIM_FAIL);
			}
			
			//update the PCCW3G Mobile number status
			if (!"Y".equals(pCreateOrdReqDTO.getMnpInd())){
				logger.info("New mobile no. case - refNum:" + refNum + "; Msisdn Lvl:"+pCreateOrdReqDTO.getMsisdnLvl());
				
				//Normal number
				/*if ("N".equalsIgnoreCase(pCreateOrdReqDTO.getMsisdnLvl()) 
						|| "N1".equalsIgnoreCase(pCreateOrdReqDTO.getMsisdnLvl()) 
						|| "N2".equalsIgnoreCase(pCreateOrdReqDTO.getMsisdnLvl())) {*/
					//logger.info("Normal number...");
					isCnmCallSuccess = cnmClient.updateMSISDN(pCreateOrdReqDTO.getMsisdn(), WsConstants.CNM_STATUS_NORMAL, WsConstants.CNM_STATUS_SOLD, WsConstants.CNM_TYPE, "", WsConstants.INV_MOB_NO_TYP_CD_3G, null);
				/*} else {
				//Golden Number
					logger.info("Golden Number...");
					logger.info("Reserve ID: " + pCreateOrdReqDTO.getReserveId());
					isCnmCallSuccess = cnmClient.updateMSISDN(pCreateOrdReqDTO.getMsisdn(), WsConstants.CNM_STATUS_NORMAL, WsConstants.CNM_STATUS_SOLD, WsConstants.CNM_TYPE, pCreateOrdReqDTO.getReserveId(), WsConstants.INV_MOB_NO_TYP_CD_3G, null);
				}*/
				logger.info("Return from CNM call status is " + isCnmCallSuccess + "- refNum:" + refNum);
				if (!isCnmCallSuccess){
					
					//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_MRT_FAIL,WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE, loginUser);
					throw new WsCcsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE,BomWebPortalConstant.BWP_MOBCCS_BOM_MRT_FAIL);
				}
			}
			
			isCnmCallSuccess = false;
			//update the China number status
			if (pCreateOrdReqDTO.getCnNum()!=null && !"".equals(pCreateOrdReqDTO.getCnNum())){
				logger.info("China No. case - refNum:" + refNum + "- CnNum:" + pCreateOrdReqDTO.getCnNum());
				isCnmCallSuccess = cnmClient.updateMSISDN(pCreateOrdReqDTO.getCnNum(), WsConstants.CNM_STATUS_NORMAL, WsConstants.CNM_STATUS_SOLD, WsConstants.CNM_TYPE_CN, "", WsConstants.INV_MOB_NO_TYP_CD_3G_CN, null);
				logger.info("Return from CNM call status is " + isCnmCallSuccess + "- refNum:" + refNum);
				if (!isCnmCallSuccess){
					//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_MRT_FAIL, WsConstants.ERR_STATUS_CNM_FAILURE_CN, WsConstants.ERR_MSG_CNM_FAILURE_CN, loginUser);
					throw new WsCcsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE,BomWebPortalConstant.BWP_MOBCCS_BOM_MRT_FAIL);
				}
			}
			
			
			//---Order Lvl ---
			logger.info("Create CCS order request in BOM - refNum:" + refNum);
			createOrdResultDTO = getWebServiceSOAP().createBomOrder(refNum, pCreateOrdReqDTO);
			logger.info("Create order request in BOM -- Return from createBomOrder - refNum:" + refNum);
			if (createOrdResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(createOrdResultDTO.getErrCode())){
				logger.info("The BOM order " + createOrdResultDTO.getRefNum() + " is created successful in BOM and the OCID is "+createOrdResultDTO.getOcid() + "- refNum:" + refNum);
				updateCcsBulkReqStatusSuccess(refNum, createOrdResultDTO.getOcid(), BomWebPortalConstant.BWP_MOBCCS_ORDER_PENDING, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_SUCCESS,"",WsConstants.ERR_STATUS_NO_ERROR, loginUser);
				
				
			}else if (createOrdResultDTO!=null){
				logger.error("The BOM order is created failure and the error message is ["+createOrdResultDTO.getErrMsg()+"] - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CREATE_ORDER_FAIL, createOrdResultDTO.getErrCode(), createOrdResultDTO.getErrMsg(), loginUser);
				throw new WsCcsOrderException(createOrdResultDTO.getErrCode(), createOrdResultDTO.getErrMsg(),BomWebPortalConstant.BWP_MOBCCS_BOM_CREATE_ORDER_FAIL);
			} else {
				logger.error("The BOM order is created failure and the error message is ["+WsConstants.ERR_STATUS_OTHERS_ERROR+"] - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CREATE_ORDER_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_STATUS_OTHERS_ERROR, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_STATUS_OTHERS_ERROR,BomWebPortalConstant.BWP_MOBCCS_BOM_CREATE_ORDER_FAIL);
			}

		}catch (WsCcsOrderException wsCcsOrderException){
			logger.error(refNum + ":                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               " + wsCcsOrderException.getReasonCd() + ":" +wsCcsOrderException.getMessage());
			updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, wsCcsOrderException.getReasonCd(), wsCcsOrderException.getErrCode(), wsCcsOrderException.getMessage(), loginUser);
		}catch (RemoteException ex){
			logger.error(ex.getMessage());
		}catch (Exception ex){
			logger.error(ex.getMessage());
		}
		return;
	}
	
	public void bulkNewActCcsHistClient(String refNum, CustomerDTO pCustomerDTO, AccountDTO pAccountDTO, CreateOrdReqDTO pCreateOrdReqDTO, String loginUser,String seqNo){
		try {			
			logger.info("Call bulkNewActCcsClient to create CCS Hist BOM order");
			if (refNum==null || pCustomerDTO==null || pAccountDTO==null || pCreateOrdReqDTO==null){
				updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_OTHERS_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_OTHERS_ERROR, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_INVALID_INPUT, WsConstants.ERR_MSG_INVALID_INPUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CREATE_ORDER_FAIL);
			}
			logger.info("refNum:" + refNum + " ,SeqNo:"+seqNo+" ;pCustomerDTO:"+pCustomerDTO.getIdDocType()+pCustomerDTO.getIdDocNum()+" ;getMsisdn:"+pCreateOrdReqDTO.getMsisdn()+ " ;getMsisdnLvl:"+ pCreateOrdReqDTO.getMsisdnLvl()+" ;getIccid:"+pCreateOrdReqDTO.getSimDTO().getIccid());
			logger.info("processing " + loginUser + " order. - refNum:" + refNum );
			logger.info("Search Customer Profile by Document# - refNum:" + refNum);
			//---Customer Lvl ---
			customerResultDTO = getWebServiceSOAP().searchMobCustomer(refNum, pCustomerDTO);
			logger.info("Customer number: " + customerResultDTO.getCustNum());
			if (customerResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(customerResultDTO.getErrCode())){
				if ("".equals(customerResultDTO.getCustNum()) || "".equals(customerResultDTO.getBomCustNum())){
					logger.info("Create mobile customer profile - refNum:" + refNum);
					newCustomerResultDTO = getWebServiceSOAP().createMobCustomer(refNum, pCustomerDTO);	
					logger.info("Create mobile customer profile -- Return from createrMobCustomer - refNum:" + refNum);
					if (newCustomerResultDTO==null || "".equals(newCustomerResultDTO.getCustNum()) || newCustomerResultDTO.getCustNum()==null ){
						logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
						//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_CUST_PROFILE_FAIL, loginUser);
						throw new WsCcsOrderException(newCustomerResultDTO.getErrCode(), newCustomerResultDTO.getErrMsg(),BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_CUST_PROFILE_FAIL);
					}
					logger.info("Set custNum["+newCustomerResultDTO.getCustNum()+"] to the accountDTO - refNum:" + refNum);
					pAccountDTO.setCustNum(newCustomerResultDTO.getCustNum());
					
					logger.info("Set custNum["+newCustomerResultDTO.getCustNum()+"] and mobCustNum["+newCustomerResultDTO.getCustNum()+"] to createOrdReqDTO - refNum:" + refNum);
					pCreateOrdReqDTO.setBomCustNum(newCustomerResultDTO.getBomCustNum());
					pCreateOrdReqDTO.setMobCustNum(newCustomerResultDTO.getCustNum());
				} else {
					logger.info("Set custNum["+customerResultDTO.getCustNum()+"] to the accountDTO - refNum:" + refNum);
					pAccountDTO.setCustNum(customerResultDTO.getCustNum());
					
					logger.info("Set custNum["+customerResultDTO.getCustNum()+"] and mobCustNum["+customerResultDTO.getCustNum()+"] to createOrdReqDTO - refNum:" + refNum);
					pCreateOrdReqDTO.setBomCustNum(customerResultDTO.getBomCustNum());
					pCreateOrdReqDTO.setMobCustNum(customerResultDTO.getCustNum());
				}
			} else if (customerResultDTO!=null){
				logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_CUST_PROFILE_FAIL, loginUser);
				throw new WsCcsOrderException(customerResultDTO.getErrCode(), customerResultDTO.getErrMsg(),BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_CUST_PROFILE_FAIL);
			} else {
				logger.error("Unable to create customer profile in BOM - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_CUST_PROFILE_FAIL, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_OTHERS_ERROR,BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_CUST_PROFILE_FAIL);
			}
			//---Account Lvl ---
			//check combine account
			if (StringUtils.isEmpty(pCreateOrdReqDTO.getAcctNum()) ) {
			logger.info("New account profile in BOM - refNum:" + refNum);
			newAccountResultDTO = getWebServiceSOAP().createMobAccount(refNum, pAccountDTO);
			if (newAccountResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(newAccountResultDTO.getErrCode())){
				logger.info("Set accountNum["+newAccountResultDTO.getAcctNum()+"] to createOrdReqDTO - refNum:" + refNum);
				pCreateOrdReqDTO.setAcctNum(newAccountResultDTO.getAcctNum());
			} else if (newAccountResultDTO!=null){
				logger.error("Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_ACCT_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, "Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum, loginUser);
				throw new WsCcsOrderException(newAccountResultDTO.getErrCode(), newAccountResultDTO.getErrMsg(),BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_ACCT_PROFILE_FAIL);
			} else {
				logger.error("Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_ACCT_PROFILE_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, "Return error code of newAccountResultDTO != 0 error code["+newAccountResultDTO.getErrMsg()+"] - refNum:" + refNum, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_MSG_OTHERS_ERROR,BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_ACCT_PROFILE_FAIL);
			}
			}
			//---INV Lvl ---
			//cnmClient = new CnmClient();
			//hwInvClient = new HwInvClient();
			
			String[] iccid = new String[1];
			iccid[0] = pCreateOrdReqDTO.getSimDTO().getIccid();
			
			logger.info("INV (HW SIM) Lvl :" +iccid[0]);
			
			isHwCallSuccess = hwInvClient.updateSIM("", "", WsConstants.HW_INV_STATUS_NORMAL, WsConstants.HW_INV_STATUS_SOLD, iccid, WsConstants.OPER_CODE);
			logger.info("Return from HW call status is " + isHwCallSuccess + " - refNum:" + refNum);
			if (!isHwCallSuccess){				
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CUST_PROFILE_FAIL, WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_HW_INV_FAILURE, WsConstants.ERR_MSG_HW_INV_FAILURE,BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_SIM_FAIL);
			}
			
			//update the PCCW3G Mobile number status
			if (!"Y".equals(pCreateOrdReqDTO.getMnpInd())){
				logger.info("New mobile no. case - refNum:" + refNum + "; Msisdn Lvl:"+pCreateOrdReqDTO.getMsisdnLvl());
				
				//Normal number
				/*if ("N".equalsIgnoreCase(pCreateOrdReqDTO.getMsisdnLvl()) 
						|| "N1".equalsIgnoreCase(pCreateOrdReqDTO.getMsisdnLvl()) 
						|| "N2".equalsIgnoreCase(pCreateOrdReqDTO.getMsisdnLvl())) {*/
					//logger.info("Normal number...");
					isCnmCallSuccess = cnmClient.updateMSISDN(pCreateOrdReqDTO.getMsisdn(), WsConstants.CNM_STATUS_NORMAL, WsConstants.CNM_STATUS_SOLD, WsConstants.CNM_TYPE, "", WsConstants.INV_MOB_NO_TYP_CD_3G, null);
				/*} else {
				//Golden Number
					logger.info("Golden Number...");
					logger.info("Reserve ID: " + pCreateOrdReqDTO.getReserveId());
					isCnmCallSuccess = cnmClient.updateMSISDN(pCreateOrdReqDTO.getMsisdn(), WsConstants.CNM_STATUS_NORMAL, WsConstants.CNM_STATUS_SOLD, WsConstants.CNM_TYPE, pCreateOrdReqDTO.getReserveId(), WsConstants.INV_MOB_NO_TYP_CD_3G, null);
				}*/
				logger.info("Return from CNM call status is " + isCnmCallSuccess + "- refNum:" + refNum);
				if (!isCnmCallSuccess){
					
					//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_MRT_FAIL,WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE, loginUser);
					throw new WsCcsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE,BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_MRT_FAIL);
				}
			}
			
			isCnmCallSuccess = false;
			//update the China number status
			if (pCreateOrdReqDTO.getCnNum()!=null && !"".equals(pCreateOrdReqDTO.getCnNum())){
				logger.info("China No. case - refNum:" + refNum + "- CnNum:" + pCreateOrdReqDTO.getCnNum());
				isCnmCallSuccess = cnmClient.updateMSISDN(pCreateOrdReqDTO.getCnNum(), WsConstants.CNM_STATUS_NORMAL, WsConstants.CNM_STATUS_SOLD, WsConstants.CNM_TYPE_CN, "", WsConstants.INV_MOB_NO_TYP_CD_3G_CN, null);
				logger.info("Return from CNM call status is " + isCnmCallSuccess + "- refNum:" + refNum);
				if (!isCnmCallSuccess){
					//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_MRT_FAIL, WsConstants.ERR_STATUS_CNM_FAILURE_CN, WsConstants.ERR_MSG_CNM_FAILURE_CN, loginUser);
					throw new WsCcsOrderException(WsConstants.ERR_STATUS_CNM_FAILURE, WsConstants.ERR_MSG_CNM_FAILURE,BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_MRT_FAIL);
				}
			}
			
			
			//---Order Lvl ---
			logger.info("Create CCS order request in BOM - refNum:" + refNum);
			createOrdResultDTO = getWebServiceSOAP().createBomOrder(refNum, pCreateOrdReqDTO);
			logger.info("Create order request in BOM -- Return from createBomOrder - refNum:" + refNum);
			if (createOrdResultDTO!=null && WsConstants.ERR_STATUS_NO_ERROR.equals(createOrdResultDTO.getErrCode())){
				logger.info("The BOM order " + createOrdResultDTO.getRefNum() + " is created successful in BOM and the OCID is "+createOrdResultDTO.getOcid() + "- refNum:" + refNum);
				updateCcsBulkReqStatusSuccessHist(refNum, createOrdResultDTO.getOcid(), BomWebPortalConstant.BWP_MOBCCS_ORDER_CANCELLING, BomWebPortalConstant.BWP_MOBCCS_HIST_CHECK_POINT_SUCCESS,"",WsConstants.ERR_STATUS_NO_ERROR, loginUser);
				
				
			}else if (createOrdResultDTO!=null){
				logger.error("The BOM order is created failure and the error message is ["+createOrdResultDTO.getErrMsg()+"] - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CREATE_ORDER_FAIL, createOrdResultDTO.getErrCode(), createOrdResultDTO.getErrMsg(), loginUser);
				throw new WsCcsOrderException(createOrdResultDTO.getErrCode(), createOrdResultDTO.getErrMsg(),BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_CREATE_ORDER_FAIL);
			} else {
				logger.error("The BOM order is created failure and the error message is ["+WsConstants.ERR_STATUS_OTHERS_ERROR+"] - refNum:" + refNum);
				//updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_BOM_CREATE_ORDER_FAIL, WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_STATUS_OTHERS_ERROR, loginUser);
				throw new WsCcsOrderException(WsConstants.ERR_STATUS_OTHERS_ERROR, WsConstants.ERR_STATUS_OTHERS_ERROR,BomWebPortalConstant.BWP_MOBCCS_HIST_BOM_CREATE_ORDER_FAIL);
			}

		}catch (WsCcsOrderException wsCcsOrderException){
			logger.error(refNum + ":                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               " + wsCcsOrderException.getReasonCd() + ":" +wsCcsOrderException.getMessage());
			updateCcsBulkReqStatus(refNum, BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT, BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_FALLOUT, wsCcsOrderException.getReasonCd(), wsCcsOrderException.getErrCode(), wsCcsOrderException.getMessage(), loginUser);
		}catch (RemoteException ex){
			logger.error(ex.getMessage());
		}catch (Exception ex){
			logger.error(ex.getMessage());
		}
		return;
	}
	
	
	private void setSecurity(CreateBulk3GNewAct service){	
		WebServiceContext context = service.context();
		WebServiceSession session = context.getSession();
		/*
		 * Registers a handler for the SOAP message traffic.
		 */
		HandlerRegistry registry = service.getHandlerRegistry();
		List list = new ArrayList();
		list.add(new HandlerInfo(WSSEClientHandler.class, null, null));
		
		registry.setHandlerChain(new QName( "CreateBulk3GNewAct"), list);

		/*
		 * Set the username and password token for SOAP message sent from the client, through
		 * the proxy, to the web service.
		 */
		//-----UAT User-----
		//UserInfo ui = new UserInfo("iSalestoolUser", "password");
		//-----My PC User-----
		UserInfo ui = new UserInfo(bulkNewActSecurityUser, bulkNewActSecurityPassword);
		//UserInfo ui = new UserInfo("iSalestoolUser", "password");
		session.setAttribute(WSSEClientHandler.REQUEST_USERINFO, ui);

		/*
		 * Adds the username / password token to the SOAP header.
		 */
		SecurityElementFactory factory = SecurityElementFactory.getDefaultFactory();
		Security security = factory.createSecurity(null);
		security.addToken(ui);       
		session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);
	}
	
	public CustomerResultDTO checkCustomer(String idDocType, String idDocNum) throws AppRuntimeException{
		String refNum = new String();
		CustomerDTO customerDTO = null;
		CustomerResultDTO customerResultDTO = new CustomerResultDTO();
		CreateBulk3GNewActSoap createBulk3GNewActSoap = null;
		try {
      
			customerDTO = new CustomerDTO();
			//setParaForCreateCust(customerDTO);
			refNum = "9999999999999";
			//Comment & modify by herbert 20110822, standardize the log
			/*
			 * System.out.println("bulkNewActSecurityUser: " + bulkNewActSecurityUser);
			 * System.out.println("bulkNewActSecurityPassword: " + bulkNewActSecurityPassword);
			 */
			logger.info("bulkNewActSecurityUser: " + bulkNewActSecurityUser);
			logger.info("bulkNewActSecurityPassword: " + bulkNewActSecurityPassword);
			customerDTO.setSystemId("MOB");
			customerDTO.setIdDocType(idDocType);
			customerDTO.setIdDocNum(idDocNum);
			CreateBulk3GNewAct service = new CreateBulk3GNewAct_Impl(wsdl);
			setSecurity(service);
			createBulk3GNewActSoap = service.getCreateBulk3GNewActSoap();
			
			fixEndPoint(createBulk3GNewActSoap);

			customerResultDTO = createBulk3GNewActSoap.searchMobCustomer(refNum, customerDTO);
			if (customerResultDTO!=null){
				//Comment & modify by herbert 20110822, standardize the log
				//System.out.println("Result: CustNum:" + customerResultDTO.getCustNum() + "\n BomCustNum:" + customerResultDTO.getErrCode() + "\n ErrMsg:" + customerResultDTO.getErrMsg());
				logger.info("Result: CustNum:" + customerResultDTO.getCustNum() + "\n BomCustNum:" + customerResultDTO.getErrCode() + "\n ErrMsg:" + customerResultDTO.getErrMsg());
			}
			return customerResultDTO;
		} catch (JAXRPCException jre) {
			jre.printStackTrace();
			throw new AppRuntimeException(jre);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new AppRuntimeException(e);
		}	
	}
	
	public ResultDTO checkBomLogin(String bomLogin) throws AppRuntimeException{
		ResultDTO resultDTO = new ResultDTO();
		CreateBulk3GNewActSoap createBulk3GNewActSoap = null;
		try {
			
			CreateBulk3GNewAct service = new CreateBulk3GNewAct_Impl(wsdl);
			setSecurity(service);
			createBulk3GNewActSoap = service.getCreateBulk3GNewActSoap();
			
			fixEndPoint(createBulk3GNewActSoap);

			resultDTO = createBulk3GNewActSoap.isValidBOMLoginId(bomLogin);
			if (resultDTO!=null){
				//Comment & modify by herbert 20110822, standardize the log
				//System.out.println("Result: Valid:" + resultDTO.getValid() + "\n ErrCode:" + resultDTO.getErrCode() + "\n ErrMsg:" + resultDTO.getErrMsg());
				logger.info("Result: Valid:" + resultDTO.getValid() + "\n ErrCode:" + resultDTO.getErrCode() + "\n ErrMsg:" + resultDTO.getErrMsg());
			}
			return resultDTO;
		} catch (JAXRPCException jre) {
			jre.printStackTrace();
			throw new AppRuntimeException(jre);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new AppRuntimeException(e);
		}	
	}
	
	//add by eliot 20110622
	public SalesmanDTO getSalesman(String salesType, String salesCd) throws AppRuntimeException{
		
		SalesmanDTO salesmanDTO = new SalesmanDTO();
		CreateBulk3GNewActSoap createBulk3GNewActSoap = null;
		try {			
			CreateBulk3GNewAct service = new CreateBulk3GNewAct_Impl(wsdl);
			setSecurity(service);
			createBulk3GNewActSoap = service.getCreateBulk3GNewActSoap();
			
			fixEndPoint(createBulk3GNewActSoap);

			if ("C".equalsIgnoreCase(salesType)){
				salesmanDTO.setSalesType(salesType);
				
				SalesmanLookUpDTO salesman = createBulk3GNewActSoap.getSalesmanInfo(salesCd);
				salesmanDTO.setSalesName(salesman.getSalesmanName());
				salesmanDTO.setErrMsg(salesman.getErrMsg());
				salesmanDTO.setErrCode(salesman.getErrCode());
			}else if ("S".equalsIgnoreCase(salesType)){
				salesmanDTO.setSalesName(salesType);
				
				SecurityUserDTO salesman = createBulk3GNewActSoap.getUserInfo(salesCd);
				salesmanDTO.setSalesName(salesman.getUserName());
				salesmanDTO.setErrMsg(salesman.getErrMsg());
				salesmanDTO.setErrCode(salesman.getErrCode());
		}
			return salesmanDTO;
		} catch (JAXRPCException jre) {
			jre.printStackTrace();
			throw new AppRuntimeException(jre);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new AppRuntimeException(e);
		}	
	}
	
	private CreateBulk3GNewActSoap getWebServiceSOAP() throws Exception{
		try{
			//createBulk3GNewAct = new CreateBulk3GNewAct_Impl(WsConstants.wsSWDL);
			/******************************************/
			if(createBulk3GNewAct==null){
				logger.info("create WebServiceSOAP object");
				createBulk3GNewAct = new CreateBulk3GNewAct_Impl(wsdl);
			}
			/******************************************/
			setSecurity(createBulk3GNewAct);
			createBulk3GNewActSoap = createBulk3GNewAct.getCreateBulk3GNewActSoap();
			
			// fix the end point address
			fixEndPoint(createBulk3GNewActSoap);


		}catch (Exception ex){
			logger.error("Error in getWebServiceSOAP(): " + ex.getMessage());
			throw new Exception ("Error in getWebServiceSOAP(): " + ex.getMessage());
		}
		return createBulk3GNewActSoap;
	}


	
	private class WsOrderException extends Exception{
		private String errCode = null;
		
		public WsOrderException(String pErrCode, String pErrMsg){
			super(pErrMsg);
			errCode = pErrCode;
		}
		public String getErrCode(){
			return errCode;
		}
		
	}
	
	//add by Herbert 20120229
	private class WsCcsOrderException extends Exception{
		private String errCode = null;
		private String reasonCd = null;
		
		public WsCcsOrderException(String pErrCode, String pErrMsg, String pReasonCd){
			super(pErrMsg);
			errCode = pErrCode;
			reasonCd = pReasonCd;
		}
		public String getErrCode(){
			return errCode;
		}
		public String getReasonCd(){
			return reasonCd;
		}
	}
	
	//modify by Eliot 20110829
	private void updateBulkReqStatus(String refNum, String status, String errCode, String errString, String lastUpdateBy) throws AppRuntimeException{
		orderService.updateOrderStatus(refNum, status, errCode, errString, lastUpdateBy);
	}
	
	//modify by Eliot 20110829
	private void updateBulkReqStatusSuccess(String refNum, String ocid, String status, String errCode, String lastUpdateBy) throws AppRuntimeException{
		orderService.updateBulkReqStatusSuccess(refNum, ocid, status, errCode, lastUpdateBy);
	}
	
	//add by Herbert 20120229
	private void updateCcsBulkReqStatusSuccess(String refNum, String ocid, String status,String checkPoint, String reasonCd,
			String errCode, String lastUpdateBy) throws AppRuntimeException{
		orderService.updateCcsBulkReqStatusSuccess(refNum, ocid, status,checkPoint,reasonCd, errCode, lastUpdateBy);
	}
	
	//add by Herbert 20120229
	private void updateCcsBulkReqStatus(String refNum, String status, String checkPoint, String reasonCd,
				String errCode, String errString, String lastUpdateBy) throws AppRuntimeException{
		orderService.updateCcsCreateOrderStatus(refNum, status, checkPoint, reasonCd,	errCode, errString, lastUpdateBy);
	}

	private void updateCcsBulkReqStatusSuccessHist(String refNum, String ocid, String status,String checkPoint, String reasonCd,
			String errCode, String lastUpdateBy) throws AppRuntimeException{
		orderService.updateCcsBulkReqStatusSuccessHist(refNum, ocid, status,checkPoint,reasonCd, errCode, lastUpdateBy);
	}
	
	// fix the end point address
	private CreateBulk3GNewActSoap fixEndPoint(CreateBulk3GNewActSoap soap) {
		String addr = wsdl.split("\\?", 2)[0];
		Stub stub = (Stub)soap;
		
		stub._setProperty("javax.xml.rpc.service.endpoint.address", addr);
		return soap;
	}
}
