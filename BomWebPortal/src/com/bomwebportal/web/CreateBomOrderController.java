package com.bomwebportal.web;

import java.awt.Component;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.ComponentDTO;

import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BulkNewActClient;

public class CreateBomOrderController implements Controller{

	protected final Log logger = LogFactory.getLog(getClass());
	
    private BulkNewActClient bulkNewActClient;
    
	public BulkNewActClient getBulkNewActClient() {
		return bulkNewActClient;
	}
	public void setBulkNewActClient(BulkNewActClient bulkNewActClient) {
		this.bulkNewActClient = bulkNewActClient;
	}
	
	   private OrderService orderService;
	   
		public void setOrderService(OrderService orderService) {
			this.orderService = orderService;
		}
		public OrderService getOrderService() {
			return orderService;
		}
		
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    		String orderId= (String)request.getSession().getAttribute("orderIdSession");
    		logger.info("Create BOM order for orderId: " + orderId);
    	try{
    		
    		
			CustomerProfileDTO  customerInfoDto = (CustomerProfileDTO)request.getSession().getAttribute("customer");
			AccountDTO accountDto = (AccountDTO)request.getSession().getAttribute("accountDto");
			OrderDTO orderDto = (OrderDTO)request.getSession().getAttribute("orderDto");
			PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
			MobileSimInfoDTO mobileSimInfo = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
			SubscriberDTO subscriberDto = (SubscriberDTO)request.getSession().getAttribute("subscriberDto");
			MnpDTO mnpDto = (MnpDTO)request.getSession().getAttribute("MNP");
			List<ComponentDTO> componentList = (List<ComponentDTO>)request.getSession().getAttribute("componentList");
			//add by Eliot 20110829
			BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			/******************* Update order status and remove session *******************************/	     		
	         	//Delete Session
			
	         	request.getSession().removeAttribute("customer");
	         	request.getSession().removeAttribute("payment");
	         	request.getSession().removeAttribute("MNP");
	         	request.getSession().removeAttribute("MobileSimInfo");
	         	request.getSession().removeAttribute("orderIdSession");
	        	request.getSession().removeAttribute("customerTierSession");
	        	request.getSession().removeAttribute("baskettypeSession");
	        	request.getSession().removeAttribute("rptypeSession");        	
	        	request.getSession().removeAttribute("selectedVasItemList");
	        	request.getSession().removeAttribute("componentList");
	        	//add eliot 20110824
	        	request.getSession().removeAttribute("signoffDtoSession");
	    	
	    	
	     	/******************* Update order status and remove session(END) ***************************/   
	     	
	        /******************* Create BOM order dto objects *******************************/	
			com.pccw.bom.mob.schemas.CustomerDTO bomCustomerDto = new com.pccw.bom.mob.schemas.CustomerDTO();
			com.pccw.bom.mob.schemas.AccountDTO bomAccountDto = new com.pccw.bom.mob.schemas.AccountDTO();
			com.pccw.bom.mob.schemas.CreateOrdReqDTO bomCreateOrdReqDTO = new com.pccw.bom.mob.schemas.CreateOrdReqDTO();
			com.pccw.bom.mob.schemas.AddressDTO bomAddrDto = new com.pccw.bom.mob.schemas.AddressDTO();
			com.pccw.bom.mob.schemas.AddressMaintDTO bomAddrMaintDto = new com.pccw.bom.mob.schemas.AddressMaintDTO();
			com.pccw.bom.mob.schemas.ContactInfoDTO bomContactInfoDto = new com.pccw.bom.mob.schemas.ContactInfoDTO();
			com.pccw.bom.mob.schemas.AddressDTO bomAcctAddrDto = new com.pccw.bom.mob.schemas.AddressDTO();
			com.pccw.bom.mob.schemas.PaymentDTO bomPaymentDto = new com.pccw.bom.mob.schemas.PaymentDTO();
			com.pccw.bom.mob.schemas.SimDTO bomSimDto = new com.pccw.bom.mob.schemas.SimDTO();
			com.pccw.bom.mob.schemas.SubscriberDTO bomSubDto = new com.pccw.bom.mob.schemas.SubscriberDTO();
			com.pccw.bom.mob.schemas.MnpDTO bomMnpDto = new com.pccw.bom.mob.schemas.MnpDTO();
			
			
			
			BeanUtils.copyProperties(bomCustomerDto, customerInfoDto);
			BeanUtils.copyProperties(bomAddrDto, customerInfoDto);
			BeanUtils.copyProperties(bomAccountDto, accountDto);
			BeanUtils.copyProperties(bomCreateOrdReqDTO, orderDto);
			//BeanUtils.copyProperties(bomAcctAddrDto, customerInfoDto); //mark by wilson 20110525
			BeanUtils.copyProperties(bomPaymentDto, paymentDto);
			
			BeanUtils.copyProperties(bomSimDto, mobileSimInfo);
			BeanUtils.copyProperties(bomSubDto, subscriberDto);
			BeanUtils.copyProperties(bomMnpDto, mnpDto);
			
			bomContactInfoDto.setSystemId("MOB");
			bomContactInfoDto.setCntctName(customerInfoDto.getContactName());
			bomContactInfoDto.setTitle(customerInfoDto.getTitle());
			bomContactInfoDto.setPrimaryInd("Y");
			bomContactInfoDto.setDayPhone(customerInfoDto.getContactPhone());
			bomContactInfoDto.setNightPhone(customerInfoDto.getOtherContactPhone()); //add 20110720 herbert
			bomContactInfoDto.setEmail(customerInfoDto.getEmailAddr());
			bomContactInfoDto.setActionInd("A");
		
			/******** Setup a CustomerDTO *********/
			bomAddrDto.setSysId("MOB");
			bomAddrDto.setAddrUsage("MA");	
			bomAddrDto.setDistrictNum(customerInfoDto.getDistrictCode());
			bomAddrDto.setDistrict(customerInfoDto.getDistrictDesc());
			//bomAddrDto.setHlLotNum(customerInfoDto.getStreetNum());//mark 20110530
			bomAddrDto.setHlLotNum(customerInfoDto.getLotNum()); //edit 20110530
			bomAddrDto.setStreetNum(customerInfoDto.getStreetNum()); //edit 20110530
			bomAddrDto.setFloorNum(customerInfoDto.getFloor());
			bomAddrDto.setUnitNum(customerInfoDto.getFlat());
			bomAddrDto.setForeignAddrFlag("N");
			bomAddrDto.setAddrType("S");
			bomAddrDto.setSectionDependencyInd("N");
			bomAddrDto.setSection(customerInfoDto.getSectionDesc());
			bomAddrDto.setBuildNum(customerInfoDto.getBuildingName());
			bomAddrDto.setStreetCatCode(customerInfoDto.getStreetCatgCode());
			bomAddrDto.setStreetCatCodeDesc(customerInfoDto.getStreetCatgDesc());			
			
			bomAddrMaintDto.setAddressDTO(bomAddrDto);
			bomCustomerDto.setAddressMaintDTO(bomAddrMaintDto);
			bomCustomerDto.setContactInfoDTO(bomContactInfoDto);
			bomCustomerDto.setDob(Utility.date2String(customerInfoDto.getDob(), "dd/MM/yyyy"));
			/**************************************/
     	
			/******** Setup a AccountDTO *********/
			bomAcctAddrDto.setAddrUsage("BA");				
			bomAcctAddrDto.setSysId("MOB");
			/*
			bomAcctAddrDto.setDistrictNum(customerInfoDto.getDistrictCode());
			bomAcctAddrDto.setDistrict(customerInfoDto.getDistrictDesc());
			bomAcctAddrDto.setHlLotNum(customerInfoDto.getHouseLotNum());
			bomAcctAddrDto.setFloorNum(customerInfoDto.getFloor());
			bomAcctAddrDto.setUnitNum(customerInfoDto.getFlat());
			bomAcctAddrDto.setAddrType("S");
			bomAcctAddrDto.setForeignAddrFlag("N");
			bomAcctAddrDto.setSectionDependencyInd("N");
			bomAcctAddrDto.setSection(customerInfoDto.getSectionDesc());
			bomAcctAddrDto.setBuildNum(customerInfoDto.getBuildingName());
			bomAcctAddrDto.setStreetCatCode(customerInfoDto.getStreetCatgCode());
			bomAcctAddrDto.setStreetCatCodeDesc(customerInfoDto.getStreetCatgDesc());
			*/
			bomAcctAddrDto.setDistrictNum(customerInfoDto.getBillingDistrictCode());
			bomAcctAddrDto.setDistrict(customerInfoDto.getBillingDistrictDesc());
			///bomAcctAddrDto.setHlLotNum(customerInfoDto.getBillingStreetNum());//mark 20110530
			bomAcctAddrDto.setHlLotNum(customerInfoDto.getLotNum()); //edit 20110530
			bomAcctAddrDto.setStreetNum(customerInfoDto.getStreetNum()); //edit 20110530
			bomAcctAddrDto.setFloorNum(customerInfoDto.getBillingFloor());
			bomAcctAddrDto.setUnitNum(customerInfoDto.getBillingFlat());
			bomAcctAddrDto.setAddrType("S");
			bomAcctAddrDto.setForeignAddrFlag("N");
			bomAcctAddrDto.setSectionDependencyInd("N");
			bomAcctAddrDto.setSection(customerInfoDto.getBillingSectionDesc());
			bomAcctAddrDto.setBuildNum(customerInfoDto.getBillingBuildingName());
			bomAcctAddrDto.setStreetCatCode(customerInfoDto.getBillingStreetCatgCode());
			bomAcctAddrDto.setStreetCatCodeDesc(customerInfoDto.getBillingStreetCatgDesc());
			
			//add by wilson 
			bomAcctAddrDto.setStreetName(customerInfoDto.getBillingStreetName());
			bomAcctAddrDto.setSectionCode(customerInfoDto.getBillingSectionCode());
			bomAcctAddrDto.setAreaCode(customerInfoDto.getBillingAreaCode());
			bomAcctAddrDto.setAreaDesc(customerInfoDto.getBillingAreaDesc());
		
			//add by eliot 20110610
			/************ Remove first name, last name, title ***************************/
			/************ when DocType is BR ********************************************/
			if ("BS".equals(bomCustomerDto.getIdDocType()))
			{
				bomCustomerDto.setCustFirstName(null);
				bomCustomerDto.setCustLastName(null);				
			}
			/**************************************** END **************************************************/

			//end add by wilson 
			
			
			bomAddrMaintDto.setAddressDTO(bomAcctAddrDto);
			bomAccountDto.setAddressMaintDTO(bomAddrMaintDto);
			bomPaymentDto.setSystemId("MOB");
			if ("C".equals(paymentDto.getPayMethodType())){
				bomPaymentDto.setCreditCardExpiryDate(paymentDto.getCreditExpiryMonth()+"/"+ paymentDto.getCreditExpiryYear());
				bomPaymentDto.setCreditCardIdDocNum(paymentDto.getCreditCardDocNum());
				bomPaymentDto.setCreditCardIdDocType(paymentDto.getCreditCardDocType());
			
				bomPaymentDto.setCreditCardIssueBank(paymentDto.getCreditCardIssueBankCd());
			}
			
			
			
			bomAccountDto.setPaymentDTO(bomPaymentDto);
			
			/**************************************/
			
			/******** Setup a bomCreateOrdReqDTO *********/
			bomCreateOrdReqDTO.setRefNum(orderDto.getOrderId());
			
			bomSimDto.setItemCode(mobileSimInfo.getItemCd());
			
			bomCreateOrdReqDTO.setSimDTO(bomSimDto);
			bomSubDto.setActUsrSameAsCustInd("Y");
			bomSubDto.setTelemktSuppressValue("N");
			bomCreateOrdReqDTO.setSubscriberDTO(bomSubDto);
			if("Y".equals(bomCreateOrdReqDTO.getMnpInd())){
				//logger.info("Cutover Date: " + Utility.date2String(mnpDto.getCutoverDate(), "dd/MM/yyyy"));
				bomMnpDto.setCutoverDate(Utility.date2String(mnpDto.getCutoverDate(), "dd/MM/yyyy"));
				bomMnpDto.setDocNo(mnpDto.getCustIdDocNum());
				bomCreateOrdReqDTO.setSrvReqDate(Utility.date2String(mnpDto.getCutoverDate(), "dd/MM/yyyy"));
			}else{
				//logger.info("Service Request Date: " + Utility.date2String(mnpDto.getCutoverDate(), "dd/MM/yyyy"));
				bomCreateOrdReqDTO.setSrvReqDate(Utility.date2String(mnpDto.getServiceReqDate(), "dd/MM/yyyy"));
			}
			bomCreateOrdReqDTO.setMnpDTO(bomMnpDto);
			List<com.pccw.bom.mob.schemas.ProductDTO> productList = orderService.getBomProductList(orderDto.getOrderId());
			com.pccw.bom.mob.schemas.ProductDTO[] productDTOArray = (com.pccw.bom.mob.schemas.ProductDTO[])productList.toArray(new com.pccw.bom.mob.schemas.ProductDTO[0]);
			bomCreateOrdReqDTO.setProductDTO(productDTOArray);
			bomCreateOrdReqDTO.setApplnDate(Utility.date2String(orderDto.getAppInDate(), "dd/MM/yyyy"));
			if(componentList!=null&&componentList.size()>0){
				com.pccw.bom.mob.schemas.ComponentDTO[] componentDTOArray = new com.pccw.bom.mob.schemas.ComponentDTO[componentList.size()];
				for(int i=0; i<componentList.size(); i++){
					ComponentDTO component = (ComponentDTO)componentList.get(i);
					com.pccw.bom.mob.schemas.ComponentDTO bomComponent = new com.pccw.bom.mob.schemas.ComponentDTO();
					BeanUtils.copyProperties(bomComponent, component); 
					//bomCreateOrdReqDTO.setComponentDTO(bomComponent);
					componentDTOArray[i] = bomComponent;
				}
				bomCreateOrdReqDTO.setComponentDTO(componentDTOArray);
			}
			/******************* Create BOM order dto objects(END) *******************************/
			
			logger.info("call bulkNewActClient for orderId: " + orderId);
			if(bulkNewActClient==null){
				logger.error("bulkNewActClient is null");
			}
			//modify by Eliot 20110829
			bulkNewActClient.bulkNewActClient(orderDto.getOrderId(), bomCustomerDto, bomAccountDto, bomCreateOrdReqDTO, user.getUsername());
			
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}    	
		
		
    	return null;
    }
	
}
