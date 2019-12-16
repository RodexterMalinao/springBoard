package com.bomwebportal.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.IGuardDAO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.eagle.dto.BlacklistRequest;
import com.bomwebportal.eagle.dto.EagleErrorResponse;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

@Transactional(readOnly=true)
public class IGuardServiceImpl implements IGuardService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	Gson gson = new Gson();
	
	String username;
	String password;
	String apiBaseUrl;
	
	private final static int CONN_TIMEOUT = 20000;
	
	private IGuardDAO iGuardDAO;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

	public IGuardDAO getiGuardDAO() {
		return iGuardDAO;
	}

	public void setiGuardDAO(IGuardDAO iGuardDAO) {
		this.iGuardDAO = iGuardDAO;
	}

	public IGuardDTO getRsIGuardDTO(CustomerProfileDTO customerInfo, 
			BasketDTO basketInfo, MobileSimInfoDTO mobileSimInfo, SignoffDTO signInfo,
			MnpDTO mnpInfo, OrderDTO orderInfo
			, List<ComponentDTO> componentList,String srvPlanType) {
		try{
			//RS mode

			IGuardDTO dto = new IGuardDTO();
			
			Date appDate = orderInfo.getAppInDate();
			dto.setHsReceivedDate(Utility.date2String(appDate, BomWebPortalConstant.DATE_FORMAT));
			
			String itemCd = basketInfo.getHsPosItemCd();
			int contractPeriod = Integer.valueOf(basketInfo.getContractPeriod());
			BigDecimal hsPrice = iGuardDAO.getNsPrice(itemCd, appDate);
			
			if (hsPrice != null){

				dto.setHsPurchasePrice(hsPrice.doubleValue() + "");
				
				String servicePlan = iGuardDAO.getIGuardPlan(hsPrice, contractPeriod, srvPlanType,appDate);
				if (servicePlan != null){

					if("LDS".equalsIgnoreCase(srvPlanType)){
						dto.setiGuardLDSPlanCode(servicePlan);
						String servicePrice = iGuardDAO.getIGuardPlanPrice(servicePlan);
						String servicePlanDesc = servicePlan + " $" + servicePrice;
						dto.setLdsSrvPlanFee(servicePlanDesc.toUpperCase());
					}else if("AD".equalsIgnoreCase(srvPlanType)){
						dto.setiGuardADPlanCode(servicePlan);
						String servicePrice = iGuardDAO.getIGuardPlanPrice(servicePlan);
						String servicePlanDesc = servicePlan + " $" + servicePrice;
						dto.setAdSrvPlanFee(servicePlanDesc.toUpperCase());
					}else if("UAD".equalsIgnoreCase(srvPlanType)){
//						dto.setiGuardADPlanCode(servicePlan);
//						String servicePrice = iGuardDAO.getIGuardPlanPrice(servicePlan);
//						String servicePlanDesc = servicePlan + " $" + servicePrice;
//						dto.setAdSrvPlanFee(servicePlanDesc.toUpperCase());
					}
					String mnp = mnpInfo.getMnp();
					if (mnp.equalsIgnoreCase("Y")){
						dto.setTgtEffDate(mnpInfo.getCutoverDateStr());
					}else{
						dto.setTgtEffDate(mnpInfo.getServiceReqDateStr());
					}
					dto.setDob(customerInfo.getDobStr());
					
					if (signInfo != null){
						dto.setCustSignature(signInfo.getSignatureInputStream());	
					}
					//StringUtils.isBlank("")?  "" : 
					dto.setSerialNo(StringUtils.isBlank(orderInfo.getiGuardSerialNo())?  "" : orderInfo.getiGuardSerialNo().toUpperCase());
					dto.setBuilding(StringUtils.isBlank(customerInfo.getBuildingName())?  "" : customerInfo.getBuildingName().toUpperCase());
					dto.setStreet((StringUtils.isBlank(customerInfo.getLotNum())?  "" : (customerInfo.getLotNum().toUpperCase() + " "))
							+ (StringUtils.isBlank(customerInfo.getStreetNum())?  "" : (customerInfo.getStreetNum().toUpperCase() + " "))
							+ (StringUtils.isBlank(customerInfo.getStreetName())?  "" : (customerInfo.getStreetName().toUpperCase() + " "))
							+ (StringUtils.isBlank(customerInfo.getStreetCatgDesc())?  "" : customerInfo.getStreetCatgDesc().toUpperCase()));
					dto.setSection(StringUtils.isBlank(customerInfo.getSectionDesc())?  "" : customerInfo.getSectionDesc().toUpperCase());
					dto.setDistrict(StringUtils.isBlank(customerInfo.getDistrictDesc())?  "" : customerInfo.getDistrictDesc().toUpperCase());
					dto.setRegion(StringUtils.isBlank(customerInfo.getAreaDesc())?  "" : customerInfo.getAreaDesc().toUpperCase());
					dto.setEmail(StringUtils.isBlank(customerInfo.getEmailAddr())?  "" : customerInfo.getEmailAddr());
					dto.setContactNo(StringUtils.isBlank(customerInfo.getContactPhone())?  "" : customerInfo.getContactPhone().toUpperCase());
					dto.setOtherContactNo(StringUtils.isBlank(customerInfo.getOtherContactPhone())?  "" : customerInfo.getOtherContactPhone().toUpperCase());
					dto.setHandsetDeviceDescription(basketInfo.getBrandDesc()+" "+basketInfo.getModelDesc()+" "+basketInfo.getColorDesc().toUpperCase());
					dto.setOrderId(StringUtils.isBlank(orderInfo.getOrderId())?  "" : orderInfo.getOrderId());
					
					dto.setCustFirstName(StringUtils.isBlank(customerInfo.getCustFirstName())?  "" : customerInfo.getCustFirstName().toUpperCase());
					dto.setCustLastName(StringUtils.isBlank(customerInfo.getCustLastName())?  "" : customerInfo.getCustLastName().toUpperCase());
					dto.setTitle(StringUtils.isBlank(customerInfo.getTitle())?  "" : customerInfo.getTitle().toUpperCase());
					dto.setIdDocNum(StringUtils.isBlank(customerInfo.getIdDocNum())?  "" : customerInfo.getIdDocNum().toUpperCase());
					dto.setFlat(StringUtils.isBlank(customerInfo.getFlat())?  "" : customerInfo.getFlat().toUpperCase());
					dto.setFloor(StringUtils.isBlank(customerInfo.getFloor())?  "" : customerInfo.getFloor().toUpperCase());
					dto.setContractPeriod(StringUtils.isBlank(basketInfo.getContractPeriod())?  "" : basketInfo.getContractPeriod().toUpperCase());
					dto.setImei(StringUtils.isBlank(mobileSimInfo.getImei())?  "" : mobileSimInfo.getImei().toUpperCase());
					dto.setSalesCd(StringUtils.isBlank(mobileSimInfo.getSalesCd())?  "" : mobileSimInfo.getSalesCd().toUpperCase());
					dto.setShopCd(StringUtils.isBlank(orderInfo.getShopCode())?  "" : orderInfo.getShopCode().toUpperCase());
					dto.setMsisdn(StringUtils.isBlank(mnpInfo.getMsisdn())?  "" : mnpInfo.getMsisdn().toUpperCase());
					dto.setPrivacyInd10011("Y".equals(this.getValue(componentList, "10011")));
					dto.setPrivacyInd10012("Y".equals(this.getValue(componentList, "10012")));
					if("UAD".equalsIgnoreCase(srvPlanType)){
						dto.setPrivacyInd99992(this.getValue(componentList, "99992"));
						dto.setPrivacyInd99993(this.getValue(componentList, "99993"));
						dto.setPrivacyInd99994(this.getValue(componentList, "99994"));
					}
					return dto;
				}
			}

		}catch(Exception e){
			logger.error("Exception caught in getRsIGuardDTO" + e.getMessage());
		}
		return null;
	}
	
	
	public IGuardDTO getCcsIGuardDTO(CustomerProfileDTO customerInfo,
			OrderDTO orderInfo, MRTUI ccsmnpInfo, 
			BasketDTO basketInfo,  List<StockDTO> stockList
			, List<ComponentDTO> componentList,String srvPlanType) {
		try{
			
			//CCS mode
			
			IGuardDTO dto = new IGuardDTO();
			Date appDate = orderInfo.getAppInDate();
			dto.setHsReceivedDate(""); // leave blank in CCS
			
			String itemCd = basketInfo.getHsPosItemCd();
			int contractPeriod = Integer.valueOf(basketInfo.getContractPeriod());
			BigDecimal hsPrice = iGuardDAO.getNsPrice(itemCd, appDate);
			
			if (hsPrice != null){
				dto.setHsPurchasePrice(hsPrice.doubleValue() + "");
				
				String servicePlan = iGuardDAO.getIGuardPlan(hsPrice, contractPeriod, srvPlanType, appDate);
				if (servicePlan != null){
					if("LDS".equalsIgnoreCase(srvPlanType)){
						dto.setiGuardLDSPlanCode(servicePlan);
						String servicePrice = iGuardDAO.getIGuardPlanPrice(servicePlan);
						String servicePlanDesc = servicePlan + " $" + servicePrice;
						dto.setLdsSrvPlanFee(servicePlanDesc.toUpperCase());
					}else if("AD".equalsIgnoreCase(srvPlanType)){
						dto.setiGuardADPlanCode(servicePlan);
						String servicePrice = iGuardDAO.getIGuardPlanPrice(servicePlan);
						String servicePlanDesc = servicePlan + " $" + servicePrice;
						dto.setAdSrvPlanFee(servicePlanDesc.toUpperCase());
					}
					String mnp = ccsmnpInfo.getMnpInd();
					if (mnp.equalsIgnoreCase("Y")){
						dto.setTgtEffDate(ccsmnpInfo.getCutOverDateStr());
						dto.setMsisdn(ccsmnpInfo.getMnpMsisdn());
					}else{
						dto.setTgtEffDate(ccsmnpInfo.getServiceReqDateStr());
						dto.setMsisdn(ccsmnpInfo.getMobMsisdn());
					}
					dto.setDob(customerInfo.getDobStr());
					dto.setSerialNo(StringUtils.isBlank(orderInfo.getiGuardSerialNo())?  "" : orderInfo.getiGuardSerialNo().toUpperCase());
					dto.setBuilding(StringUtils.isBlank(customerInfo.getBuildingName())?  "" : customerInfo.getBuildingName().toUpperCase());
					dto.setStreet((StringUtils.isBlank(customerInfo.getLotNum())?  "" : customerInfo.getLotNum().toUpperCase())
							+ (StringUtils.isBlank(customerInfo.getStreetNum())?  "" : customerInfo.getStreetNum().toUpperCase())
							+ (StringUtils.isBlank(customerInfo.getStreetName())?  "" : customerInfo.getStreetName().toUpperCase())
							+ (StringUtils.isBlank(customerInfo.getStreetCatgDesc())?  "" : customerInfo.getStreetCatgDesc().toUpperCase()));
					dto.setSection(StringUtils.isBlank(customerInfo.getSectionDesc())?  "" : customerInfo.getSectionDesc().toUpperCase());
					dto.setDistrict(StringUtils.isBlank(customerInfo.getDistrictDesc())?  "" : customerInfo.getDistrictDesc().toUpperCase());
					dto.setRegion(StringUtils.isBlank(customerInfo.getAreaDesc())?  "" : customerInfo.getAreaDesc().toUpperCase());
					dto.setEmail(StringUtils.isBlank(customerInfo.getEmailAddr())?  "" : customerInfo.getEmailAddr());
					dto.setContactNo(StringUtils.isBlank(customerInfo.getContactPhone())?  "" : customerInfo.getContactPhone().toUpperCase());
					dto.setOtherContactNo(StringUtils.isBlank(customerInfo.getOtherContactPhone())?  "" : customerInfo.getOtherContactPhone().toUpperCase());
					dto.setHandsetDeviceDescription(basketInfo.getBrandDesc()+" "+basketInfo.getModelDesc()+" "+basketInfo.getColorDesc().toUpperCase());
					dto.setOrderId(StringUtils.isBlank(orderInfo.getOrderId())?  "" : orderInfo.getOrderId());
					
					dto.setCustFirstName(StringUtils.isBlank(customerInfo.getCustFirstName())?  "" : customerInfo.getCustFirstName().toUpperCase());
					dto.setCustLastName(StringUtils.isBlank(customerInfo.getCustLastName())?  "" : customerInfo.getCustLastName().toUpperCase());
					dto.setTitle(StringUtils.isBlank(customerInfo.getTitle())?  "" : customerInfo.getTitle().toUpperCase());
					dto.setIdDocNum(StringUtils.isBlank(customerInfo.getIdDocNum())?  "" : customerInfo.getIdDocNum().toUpperCase());
					dto.setFlat(StringUtils.isBlank(customerInfo.getFlat())?  "" : customerInfo.getFlat().toUpperCase());
					dto.setFloor(StringUtils.isBlank(customerInfo.getFloor())?  "" : customerInfo.getFloor().toUpperCase());
					dto.setContractPeriod(StringUtils.isBlank(basketInfo.getContractPeriod())?  "" : basketInfo.getContractPeriod().toUpperCase());
					
					//get imei in ccs mode
					//get imei in ccs mode
					String imei="NOT ASSIGN YET";
					for (int i=0; stockList!= null && i< stockList.size() ; i++){
						if("HANDSET".equals(stockList.get(i).getType())){
							imei =stockList.get(i).getItemSerial();
							break;
						}
	
					}
					dto.setImei(StringUtils.isBlank(imei)? "" : imei);
					
					
					dto.setSalesCd(StringUtils.isBlank(orderInfo.getSalesCd())?  "" : orderInfo.getSalesCd().toUpperCase());
					dto.setShopCd(StringUtils.isBlank(orderInfo.getShopCode())?  "" : orderInfo.getShopCode().toUpperCase());
					dto.setPrivacyInd10011("Y".equals(this.getValue(componentList, "10011")));
					dto.setPrivacyInd10012("Y".equals(this.getValue(componentList, "10012")));
					
					return dto;
				}
				else{
					//No IGuardPlan is selected.
					return null;
				}
			}else{
				//No hsPrice is selected.
				return null;
			}

		}catch(Exception e){
				return null;
		}
	}
	
	public IGuardDTO getCcsPreviewIGuardDTO(String orderId, Date appDate,String iGuardSerialNo,  CustomerProfileDTO customerInfo,
			 MRTUI mrtUI, 
			BasketDTO basketDto, StaffInfoUI staffInfoUI, DeliveryUI deliveryUI, BomSalesUserDTO salesUserDto, List<StockDTO> stockList
			, List<ComponentDTO> componentList,String srvPlanType) {
		try{
			
			//CCS mode
			IGuardDTO dto = new IGuardDTO();
			dto.setHsReceivedDate(Utility.date2String(appDate, BomWebPortalConstant.DATE_FORMAT));
			
			String itemCd = basketDto.getHsPosItemCd();
			int contractPeriod = Integer.valueOf(basketDto.getContractPeriod());
			BigDecimal hsPrice = iGuardDAO.getNsPrice(itemCd, appDate);
			
			if (hsPrice != null){
				dto.setHsPurchasePrice(hsPrice.doubleValue() + "");
				
				String servicePlan = iGuardDAO.getIGuardPlan(hsPrice, contractPeriod,srvPlanType, appDate);
				if (servicePlan != null){
					if("LDS".equalsIgnoreCase(srvPlanType)){
						dto.setiGuardLDSPlanCode(servicePlan);
						String servicePrice = iGuardDAO.getIGuardPlanPrice(servicePlan);
						String servicePlanDesc = servicePlan + " $" + servicePrice;
						dto.setLdsSrvPlanFee(servicePlanDesc.toUpperCase());
					}else if("AD".equalsIgnoreCase(srvPlanType)){
						dto.setiGuardADPlanCode(servicePlan);
						String servicePrice = iGuardDAO.getIGuardPlanPrice(servicePlan);
						String servicePlanDesc = servicePlan + " $" + servicePrice;
						dto.setAdSrvPlanFee(servicePlanDesc.toUpperCase());
					}
					String mnp = mrtUI.getMnpInd();
					if (mnp.equalsIgnoreCase("Y")){
						dto.setTgtEffDate(mrtUI.getCutOverDateStr());
						dto.setMsisdn(mrtUI.getMnpMsisdn());
					}else{
						dto.setTgtEffDate(mrtUI.getServiceReqDateStr());
						dto.setMsisdn(mrtUI.getMobMsisdn());
					}
					dto.setDob(customerInfo.getDobStr());				
					dto.setSerialNo(StringUtils.isBlank(iGuardSerialNo)?  "" : iGuardSerialNo.toUpperCase());
					dto.setBuilding(StringUtils.isBlank(customerInfo.getBuildingName())?  "" : customerInfo.getBuildingName().toUpperCase());
					dto.setStreet((StringUtils.isBlank(customerInfo.getLotNum())?  "" : customerInfo.getLotNum().toUpperCase())
							+ (StringUtils.isBlank(customerInfo.getStreetName())?  "" : customerInfo.getStreetName().toUpperCase())
							+ (StringUtils.isBlank(customerInfo.getStreetCatgDesc())?  "" : customerInfo.getStreetCatgDesc().toUpperCase()));
					dto.setSection(StringUtils.isBlank(customerInfo.getSectionDesc())?  "" : customerInfo.getSectionDesc().toUpperCase());
					dto.setDistrict(StringUtils.isBlank(customerInfo.getDistrictDesc())?  "" : customerInfo.getDistrictDesc().toUpperCase());
					dto.setRegion(StringUtils.isBlank(customerInfo.getAreaDesc())?  "" : customerInfo.getAreaDesc().toUpperCase());
					dto.setEmail(StringUtils.isBlank(customerInfo.getEmailAddr())?  "" : customerInfo.getEmailAddr());
					dto.setContactNo(StringUtils.isBlank(customerInfo.getContactPhone())?  "" : customerInfo.getContactPhone().toUpperCase());
					dto.setOtherContactNo(StringUtils.isBlank(customerInfo.getOtherContactPhone())?  "" : customerInfo.getOtherContactPhone().toUpperCase());
					dto.setHandsetDeviceDescription(basketDto.getBrandDesc()+" "+basketDto.getModelDesc()+" "+basketDto.getColorDesc().toUpperCase());
					dto.setOrderId(orderId);
					
					dto.setCustFirstName(StringUtils.isBlank(customerInfo.getCustFirstName())?  "" : customerInfo.getCustFirstName().toUpperCase());
					dto.setCustLastName(StringUtils.isBlank(customerInfo.getCustLastName())?  "" : customerInfo.getCustLastName().toUpperCase());
					dto.setTitle(StringUtils.isBlank(customerInfo.getTitle())?  "" : customerInfo.getTitle().toUpperCase());
					dto.setIdDocNum(StringUtils.isBlank(customerInfo.getIdDocNum())?  "" : customerInfo.getIdDocNum().toUpperCase());
					dto.setFlat(StringUtils.isBlank(customerInfo.getFlat())?  "" : customerInfo.getFlat().toUpperCase());
					dto.setFloor(StringUtils.isBlank(customerInfo.getFloor())?  "" : customerInfo.getFloor().toUpperCase());
					dto.setContractPeriod(StringUtils.isBlank(basketDto.getContractPeriod())?  "" : basketDto.getContractPeriod().toUpperCase());
					
					//get imei in ccs mode
					String imei="NOT ASSIGN YET";
					for (int i=0; stockList!= null && i< stockList.size() ; i++){
						if("HANDSET".equals(stockList.get(i).getType())){
							imei =stockList.get(i).getItemSerial();
							break;
						}
	
					}
					dto.setImei(imei);
					
					
					
					dto.setSalesCd(StringUtils.isBlank(staffInfoUI.getSalesId())?  "" : staffInfoUI.getSalesId().toUpperCase());
					dto.setShopCd(StringUtils.isBlank(salesUserDto.getChannelCd())?  "" : salesUserDto.getChannelCd().toUpperCase());
					dto.setPrivacyInd10011("Y".equals(this.getValue(componentList, "10011")));
					dto.setPrivacyInd10012("Y".equals(this.getValue(componentList, "10012")));
					
					return dto;
				}
				else{
					//No IGuardPlan is selected.
					return null;
				}
			}else{
				//No hsPrice is selected.
				return null;
			}

		}catch(Exception e){
				return null;
		}
	}

	public String getIGuardSN(){
		try {
			return iGuardDAO.getIGuardSn();
		} catch (DAOException e) {
			return null;
		}
	}
	
	public List<String> isIGuardOrder(String basketid, String[] vasList, Date appDate){
		try {
			return iGuardDAO.isIGuardOrder(basketid, vasList, appDate);
		} catch (DAOException e) {
			return null;
		}
	}
	
	public Boolean getIGuardPlan( Date appDate,BasketDTO basketDto,String srvPlanType, String errorMessage) {
		Boolean result=false;
		try{

			String itemCd = basketDto.getHsPosItemCd();
			if (StringUtils.isBlank(itemCd)){
				errorMessage ="get getHsPosItemCd error";
				result = false;
				return result;
			}
			int contractPeriod = Integer.valueOf(basketDto.getContractPeriod());
			BigDecimal hsPrice = iGuardDAO.getNsPrice(itemCd, appDate);

				if (hsPrice != null){
					
					
					String servicePlan = iGuardDAO.getIGuardPlan(hsPrice, contractPeriod, srvPlanType, appDate);
					if (servicePlan != null){
						result = true;
						
					}
					else{
						//No IGuardPlan is selected.
						errorMessage ="get IGuardPlan error";
						result = false;
					}
				}else{
					//No hsPrice is selected.
					errorMessage ="get hs price error";
					result = false;
				}
		
		}catch(Exception e){
			errorMessage ="Exception";
			result = false;
		}
		return result;
	}
	
	public boolean fulfillSubscribeProjectEagle(BigDecimal nsPrice, String srvPlanCd, Date appDate) {
		try {
			String sqlResult = iGuardDAO.getProjectEaglePlan(nsPrice, srvPlanCd, appDate);
			if (StringUtils.isNotBlank(sqlResult)) {
				return true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			logger.debug(e);
			return false;
		}
		return false;
	}

	private String getValue(List<ComponentDTO> componentList, String compAttbId) {
		if (componentList == null) {
			return null;
		}
		for (ComponentDTO dto : componentList) {
			if (dto.getCompAttbId().equals(compAttbId)) {
				return dto.getCompAttbVal();
			}
		}
		return null;
	}
	
	public BigDecimal getNsPrice(String posItemCd, Date appDate){
		try {
			return iGuardDAO.getNsPrice(posItemCd, appDate);
		} catch (DAOException e) {
			return null;
		}
	}
	
	public String getIGuardPlan(BigDecimal nsPrice, int contractPeriod,String srvPlanType, Date appDate){
		try {
			return iGuardDAO.getIGuardPlan(nsPrice, contractPeriod, srvPlanType, appDate);
		} catch (DAOException e) {
			return null;
		}
	}
	
	public String getIGuardPlanPrice(String iGuardPlan){
		try {
			return iGuardDAO.getIGuardPlanPrice(iGuardPlan);
		} catch (DAOException e) {
			return null;
		}
	}
	
	public String getIGuardSnByOrder(String orderId) {
		try {
			return iGuardDAO.getIGuardSnByOrder(orderId);
		} catch (DAOException e) {
			return null;
		}
	}
	
	public boolean isIGuardUADPlanSubscribed(String orderId ,String grpId ) {
		try {
			return iGuardDAO.isIGuardUADPlanSubscribed(orderId,grpId);
		} catch (DAOException e) {
			return false;
		}
	}
	public String getIGuardUADDateOfBirth(Date appDate, String dateOfBirth){
		try {
			return iGuardDAO.getIGuardUADDateOfBirth(appDate,dateOfBirth);
		} catch (DAOException e) {
			return " ";
		}
	}
	
	public int updateUADOptInd(String orderId, boolean optInInd){
		try {
			String optInIndString = "N";
			if (optInInd ){
				optInIndString = "Y";
			}
			return iGuardDAO.updateUADOptInd(orderId, optInIndString);
		} catch (DAOException e) {
			logger.error("Exception caught in updateUADOptInd()", e);
			return 0;
		}
	
	}
	
	public String getProductCodeBySelectGrpAndLineGrp(String itemId, String itemSelectionGroupId, String serviceLineGroupCategory) {
		try {
			return iGuardDAO.getProductCodeBySelectGrpAndLineGrp(itemId, itemSelectionGroupId, serviceLineGroupCategory);
		} catch (DAOException e) {
			return null;
		} 
	}
	
	public EagleErrorResponse eagleBlacklist (BlacklistRequest req) {
		HttpURLConnection conn = null;
		OutputStream os = null;
		try {
			String url = apiBaseUrl + "/blacklist";
			logger.debug("Call SBExt eagleBlacklist():" + url);
			conn = (HttpURLConnection)new URL(url).openConnection();
			setHeaders(conn);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			String request = new Gson().toJson(req);
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			osw.write(request);
			osw.flush();
			int httpStatus = conn.getResponseCode();
			InputStream is = null;
			if (httpStatus == 200) {
				is = conn.getInputStream();
			} else {
				is = conn.getErrorStream();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String s;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			logger.debug("HTTP Status from SBExt eagleBlacklist():" + httpStatus);
			logger.debug("Result from SBExt eagleBlacklist():" + s);
			if (httpStatus == 200) {
				EagleErrorResponse response = gson.fromJson(sb.toString(), EagleErrorResponse.class);
				return response;
			}  else {
				logger.info("HTTP Connection Error in Project Eagle SBExt Call: " + httpStatus);
				EagleErrorResponse response = gson.fromJson(sb.toString(), EagleErrorResponse.class);
				return response;
			}
		} catch (Exception e) {
			logger.info("Exception Error in Project Eagle SBExt Call: " + e);
			return null;
		} finally {
			IOUtils.closeQuietly(os);
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	private void setHeaders(HttpURLConnection conn) {
		String userPassword = username + ":" + password;
		String encoded;
		try {
			encoded = new String(Base64.encodeBase64(userPassword.getBytes(), false),  "UTF-8");
			conn.setRequestProperty("Authorization", "Basic " + encoded);
			conn.setConnectTimeout(CONN_TIMEOUT);
		} catch (UnsupportedEncodingException e) {
			logger.debug(e);
		}
	}
	
}