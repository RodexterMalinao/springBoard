package com.bomwebportal.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtChinaDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtMnpDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BulkNewActClient;

public class CcsCreateBomOrder {

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

	private CustomerProfileService customerProfileService;

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}
	
	private MobCcsMrtService mobCcsMrtService;

	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	public String shopCode;
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	
    private MobileSimInfoService mobileSimInfoService;
	public MobileSimInfoService getMobileSimInfoService() {
		return mobileSimInfoService;
	}
	public void setMobileSimInfoService(MobileSimInfoService mobileSimInfoService) {
		this.mobileSimInfoService = mobileSimInfoService;
	}

	public void processCcsRequest(String orderId) {

		logger.info("Create CCS BOM order for orderId: " + orderId);
		try {

			/**************** Update Spring Data ****************/
		    //Confirm the bomweb_sim are updated before submit to BOM
		    List<StockDTO> stockList = orderService.getStockAssignment(orderId);
		    /*
		     * Get SIM information from stockList
		     */
		    MobileSimInfoDTO newMobileSimInfo = new MobileSimInfoDTO();
		    for (StockDTO stock : stockList) {
		    	if (stock.getType().equalsIgnoreCase("SIM")){
		    		MobileSimInfoDTO inMobileSimInfoDto = new MobileSimInfoDTO();
		    		inMobileSimInfoDto.setIccid(stock.getItemSerial());
		    		logger.info("validateSim before, iccid:"+stock.getItemSerial()+", model:"+stock.getItemDesc());
		    		MobileSimInfoDTO resultMobileSimInfoDto = mobileSimInfoService.validateSim(inMobileSimInfoDto);
		    		logger.info("validateSim after, iccid:"+resultMobileSimInfoDto.getIccid()+" , model:" + resultMobileSimInfoDto.getItemCd());
		    		newMobileSimInfo = resultMobileSimInfoDto;
		    		newMobileSimInfo.setItemCd(stock.getItemCode());
		    		newMobileSimInfo.setOrderId(orderId);
		    	}
		    }
		    orderService.updateBomWebSim(newMobileSimInfo);
			
			
			/**************** Get the data from database ***************/
			OrderDTO orderDto = orderService.getOrder(orderId);
			CustomerProfileDTO customerInfoDto = customerProfileService.getCustomerProfile(orderId);
			MnpDTO mnpDto = orderService.getMnp(orderId);
			PaymentDTO paymentDto = orderService.getPayment(orderId);
			MobileSimInfoDTO mobileSimInfo = orderService.getSim(orderId);
			SubscriberDTO subscriberDto = orderService.getBomWebSub(orderId);
			AccountDTO accountDto = orderService.getAccount(orderId);
			List<ComponentDTO> componentList = orderService.getPassToBomComponentList(orderId);//getComponentList
			//For getting Golden Number & 1C2N
		    ArrayList<MobCcsMrtBaseDTO> mobCcsMrtDtoList = mobCcsMrtService.getMobCcsMrtDTO(orderId);
		   		    
		    String multiIMEIs = orderService.getMultiIMEI(orderId);
		    
			/******************* Create BOM order dto objects *******************************/
			com.pccw.bom.mob.schemas.CustomerDTO bomCustomerDto = new com.pccw.bom.mob.schemas.CustomerDTO();
			com.pccw.bom.mob.schemas.AccountDTO bomAccountDto = new com.pccw.bom.mob.schemas.AccountDTO();
			com.pccw.bom.mob.schemas.CreateOrdReqDTO bomCreateOrdReqDTO = new com.pccw.bom.mob.schemas.CreateOrdReqDTO();
			com.pccw.bom.mob.schemas.AddressDTO bomAddrDto = new com.pccw.bom.mob.schemas.AddressDTO();
			com.pccw.bom.mob.schemas.AddressMaintDTO bomAddrMaintDto = new com.pccw.bom.mob.schemas.AddressMaintDTO();
			com.pccw.bom.mob.schemas.AddressMaintDTO bomBillingAddrMaintDto = new com.pccw.bom.mob.schemas.AddressMaintDTO();
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
			// BeanUtils.copyProperties(bomAcctAddrDto, customerInfoDto); //mark
			// by wilson 20110525
			BeanUtils.copyProperties(bomPaymentDto, paymentDto);

			BeanUtils.copyProperties(bomSimDto, mobileSimInfo);
			BeanUtils.copyProperties(bomSubDto, subscriberDto);
			BeanUtils.copyProperties(bomMnpDto, mnpDto);
			
			if ("BS".equals(customerInfoDto.getIdDocType())) {//add 20110621 wilson 
				bomCustomerDto.setTitle(null);
				bomCustomerDto.setCustFirstName(null);//add 20110623 wilson
				bomCustomerDto.setCustLastName(null);//add 20110623 wilson
			}

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
			// bomAddrDto.setHlLotNum(customerInfoDto.getStreetNum());//mark
			// 20110530
			bomAddrDto.setHlLotNum(customerInfoDto.getLotNum()); // edit
																	// 20110530
			bomAddrDto.setStreetNum(customerInfoDto.getStreetNum()); // edit
																		// 20110530
			bomAddrDto.setFloorNum(customerInfoDto.getFloor());
			bomAddrDto.setUnitNum(customerInfoDto.getFlat());
			bomAddrDto.setForeignAddrFlag("N");
			bomAddrDto.setAddrType("S");
			bomAddrDto.setSectionDependencyInd("N");
			bomAddrDto.setSection(customerInfoDto.getSectionDesc());
			bomAddrDto.setBuildNum(customerInfoDto.getBuildingName());
			bomAddrDto.setStreetCatCode(customerInfoDto.getStreetCatgCode());
			bomAddrDto
					.setStreetCatCodeDesc(customerInfoDto.getStreetCatgDesc());

			bomAddrMaintDto.setAddressDTO(bomAddrDto);
			bomCustomerDto.setAddressMaintDTO(bomAddrMaintDto);
			bomCustomerDto.setContactInfoDTO(bomContactInfoDto);
			bomCustomerDto.setDob(Utility.date2String(customerInfoDto.getDob(),
					"dd/MM/yyyy"));
			/**************************************/

			/******** Setup a AccountDTO *********/
			bomAcctAddrDto.setAddrUsage("BA");
			bomAcctAddrDto.setSysId("MOB");
			/*
			 * bomAcctAddrDto.setDistrictNum(customerInfoDto.getDistrictCode());
			 * bomAcctAddrDto.setDistrict(customerInfoDto.getDistrictDesc());
			 * bomAcctAddrDto.setHlLotNum(customerInfoDto.getHouseLotNum());
			 * bomAcctAddrDto.setFloorNum(customerInfoDto.getFloor());
			 * bomAcctAddrDto.setUnitNum(customerInfoDto.getFlat());
			 * bomAcctAddrDto.setAddrType("S");
			 * bomAcctAddrDto.setForeignAddrFlag("N");
			 * bomAcctAddrDto.setSectionDependencyInd("N");
			 * bomAcctAddrDto.setSection(customerInfoDto.getSectionDesc());
			 * bomAcctAddrDto.setBuildNum(customerInfoDto.getBuildingName());
			 * bomAcctAddrDto
			 * .setStreetCatCode(customerInfoDto.getStreetCatgCode());
			 * bomAcctAddrDto
			 * .setStreetCatCodeDesc(customerInfoDto.getStreetCatgDesc());
			 */
			bomAcctAddrDto.setDistrictNum(customerInfoDto
					.getBillingDistrictCode());
			bomAcctAddrDto
					.setDistrict(customerInfoDto.getBillingDistrictDesc());
			// /bomAcctAddrDto.setHlLotNum(customerInfoDto.getBillingStreetNum());//mark
			// 20110530
			bomAcctAddrDto.setHlLotNum(customerInfoDto.getBillingLotNum()); // edit
																			// 20110530
			bomAcctAddrDto.setStreetNum(customerInfoDto.getBillingStreetNum()); // edit
																				// 20110530
			bomAcctAddrDto.setFloorNum(customerInfoDto.getBillingFloor());
			bomAcctAddrDto.setUnitNum(customerInfoDto.getBillingFlat());
			bomAcctAddrDto.setAddrType("S");
			bomAcctAddrDto.setForeignAddrFlag("N");
			bomAcctAddrDto.setSectionDependencyInd("N");
			bomAcctAddrDto.setSection(customerInfoDto.getBillingSectionDesc());
			bomAcctAddrDto
					.setBuildNum(customerInfoDto.getBillingBuildingName());
			bomAcctAddrDto.setStreetCatCode(customerInfoDto
					.getBillingStreetCatgCode());
			bomAcctAddrDto.setStreetCatCodeDesc(customerInfoDto
					.getBillingStreetCatgDesc());

			// add by wilson
			bomAcctAddrDto
					.setStreetName(customerInfoDto.getBillingStreetName());
			bomAcctAddrDto.setSectionCode(customerInfoDto
					.getBillingSectionCode());
			bomAcctAddrDto.setAreaCode(customerInfoDto.getBillingAreaCode());
			bomAcctAddrDto.setAreaDesc(customerInfoDto.getBillingAreaDesc());

			// end add by wilson

			bomBillingAddrMaintDto.setAddressDTO(bomAcctAddrDto);
			bomAccountDto.setAddressMaintDTO(bomBillingAddrMaintDto);
			bomPaymentDto.setSystemId("MOB");
			if ("C".equals(paymentDto.getPayMethodType())) {
				bomPaymentDto.setCreditCardExpiryDate(paymentDto
						.getCreditExpiryMonth()
						+ "/" + paymentDto.getCreditExpiryYear());
				bomPaymentDto.setCreditCardIdDocNum(paymentDto
						.getCreditCardDocNum());
				bomPaymentDto.setCreditCardIdDocType(paymentDto
						.getCreditCardDocType());

				bomPaymentDto.setCreditCardIssueBank(paymentDto
						.getCreditCardIssueBankCd());
			}

			bomAccountDto.setPaymentDTO(bomPaymentDto);

			/**************************************/

			/******** Setup a bomCreateOrdReqDTO *********/
			bomCreateOrdReqDTO.setRefNum(orderDto.getOrderId());

			bomSimDto.setItemCode(mobileSimInfo.getItemCd());

			bomCreateOrdReqDTO.setSimDTO(bomSimDto);
			bomSubDto.setActUsrSameAsCustInd("Y");
			bomSubDto.setTelemktSuppressValue("N");
			
			//add by Herbert 20120314 - Multi IMEIs
			logger.info(orderId +" Multi-IMEIs details:"+ multiIMEIs);
			bomSubDto.setSubTier(multiIMEIs);
			
			bomCreateOrdReqDTO.setSubscriberDTO(bomSubDto);	
			
			if ("Y".equals(bomCreateOrdReqDTO.getMnpInd())) {
				// logger.info("Cutover Date: " +
				// Utility.date2String(mnpDto.getCutoverDate(), "dd/MM/yyyy"));
				bomMnpDto.setCutoverDate(Utility.date2String(mnpDto
						.getCutoverDate(), "dd/MM/yyyy"));
				bomMnpDto.setDocNo(mnpDto.getCustIdDocNum());
				bomCreateOrdReqDTO.setSrvReqDate(Utility.date2String(mnpDto
						.getCutoverDate(), "dd/MM/yyyy"));
			} else {
				// logger.info("Service Request Date: " +
				// Utility.date2String(mnpDto.getCutoverDate(), "dd/MM/yyyy"));
				bomCreateOrdReqDTO.setSrvReqDate(Utility.date2String(mnpDto
						.getServiceReqDate(), "dd/MM/yyyy"));
			}
			
			//by herbert, clean MNP data
			//A = New no. + MNP 
			if ("A".equals(bomCreateOrdReqDTO.getMnpInd())){
				bomMnpDto = null;
				bomCreateOrdReqDTO.setMnpInd("N"); 
			}
			
			bomCreateOrdReqDTO.setMnpDTO(bomMnpDto);
			List<com.pccw.bom.mob.schemas.ProductDTO> productList = orderService
					.getBomProductList(orderDto.getOrderId());
			com.pccw.bom.mob.schemas.ProductDTO[] productDTOArray = (com.pccw.bom.mob.schemas.ProductDTO[]) productList
					.toArray(new com.pccw.bom.mob.schemas.ProductDTO[0]);
			bomCreateOrdReqDTO.setProductDTO(productDTOArray);
			bomCreateOrdReqDTO.setApplnDate(Utility.date2String(orderDto
					.getAppInDate(), "dd/MM/yyyy"));
			if (componentList != null && componentList.size() > 0) {
				com.pccw.bom.mob.schemas.ComponentDTO[] componentDTOArray = new com.pccw.bom.mob.schemas.ComponentDTO[componentList
						.size()];
				for (int i = 0; i < componentList.size(); i++) {
					ComponentDTO component = (ComponentDTO) componentList
							.get(i);
					com.pccw.bom.mob.schemas.ComponentDTO bomComponent = new com.pccw.bom.mob.schemas.ComponentDTO();
					BeanUtils.copyProperties(bomComponent, component);
					// bomCreateOrdReqDTO.setComponentDTO(bomComponent);
					componentDTOArray[i] = bomComponent;
				}
				bomCreateOrdReqDTO.setComponentDTO(componentDTOArray);
			}
			
			//MrtInd(): O = New no./MNP, C = 1C2N , A = New no. + MNP 
			if (mobCcsMrtDtoList != null) {
				for (MobCcsMrtBaseDTO dto : mobCcsMrtDtoList) {
					
					bomCreateOrdReqDTO.setReserveId(dto.getReserveId());
					bomCreateOrdReqDTO.setResOperId(dto.getOperId());
					
					if (dto instanceof MobCcsMrtMnpDTO) {
						bomCreateOrdReqDTO.setResCustName(((MobCcsMrtMnpDTO) dto).getCustName());
					} else if (dto instanceof MobCcsMrtChinaDTO) {
						bomCreateOrdReqDTO.setCnNum(dto.getMsisdn());
						bomCreateOrdReqDTO.setCnCityCode(((MobCcsMrtChinaDTO) dto).getCityCd());
						bomCreateOrdReqDTO.setCnGoldenNoLvlId(dto.getMsisdnLvl());
					}
					
				}
			}
			
			//All CCS Order are On Hold with reason code 99
			bomCreateOrdReqDTO.setOnHoldInd("Y");
			bomCreateOrdReqDTO.setOnHoldReaCd("99");
			
			//by herbert, ShopCode CC2 in SIT/UAT,  CCS in PRD
			bomCreateOrdReqDTO.setShopCode(shopCode);
			logger.debug("ShopCode:"+shopCode);
			
			//by herbert, "ACT" submit to BOM
			bomCreateOrdReqDTO.setBusTxnType("ACT");
			/******************* Create BOM order dto objects(END) *******************************/

			logger.info("call bulkNewActClient for orderId: " + orderId);
			if (bulkNewActClient == null) {
				logger.error("bulkNewActClient is null");
			}
			
			//modify by eliot 20110829			
			bulkNewActClient.bulkNewActCcsClient(orderDto.getOrderId(),
					bomCustomerDto, bomAccountDto, bomCreateOrdReqDTO, orderDto.getLastUpdateBy());

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return;
	}

}
