package com.bomwebportal.ims.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.SalesmanDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.BasketInfoDAO;
import com.bomwebportal.ims.dao.GetShopContactDAO;
import com.bomwebportal.ims.dao.OnetimeChargeDAO;
import com.bomwebportal.ims.dao.PTDAO;
import com.bomwebportal.ims.dto.BasketDetailsDTO;
import com.bomwebportal.ims.dto.BomwebOTDTO;
import com.bomwebportal.ims.dto.ImsInstallationInstallmentPlanDTO;
import com.bomwebportal.ims.dto.ui.InstallFeeUI;
import com.bomwebportal.wsclient.BulkNewActClient;

@Transactional(readOnly=true)
public class ImsPaymentServiceImpl implements ImsPaymentService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private BulkNewActClient bulkNewActClient;
	private GetShopContactDAO getShopContactDao;
	private OnetimeChargeDAO onetimeChargeDao;
	private BasketInfoDAO basketInfoDao;
	private PTDAO ptDao;
	
	
	public BulkNewActClient getBulkNewActClient() {
		return bulkNewActClient;
	}

	public void setBulkNewActClient(BulkNewActClient bulkNewActClient) {
		this.bulkNewActClient = bulkNewActClient;
	}
	
	public GetShopContactDAO getGetShopContactDao() {
		return getShopContactDao;
	}

	public void setGetShopContactDao(GetShopContactDAO getShopContactDao) {
		this.getShopContactDao = getShopContactDao;
	}
	
	public OnetimeChargeDAO getOnetimeChargeDao() {
		return onetimeChargeDao;
	}

	public void setOnetimeChargeDao(OnetimeChargeDAO onetimeChargeDao) {
		this.onetimeChargeDao = onetimeChargeDao;
	}
	
	public BasketInfoDAO getBasketInfoDao() {
		return basketInfoDao;
	}

	public void setBasketInfoDao(BasketInfoDAO basketInfoDao) {
		this.basketInfoDao = basketInfoDao;
	}
	
	public PTDAO getPtDao() {
		return ptDao;
	}

	public void setPtDao(PTDAO ptDao) {
		this.ptDao = ptDao;
	}

	/*
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public boolean validateSalesCd(MobileSimInfoDTO mobileSimInfoDTO){

		boolean result = false;
		
		try{
			logger.info("MobileSimInfoServiceImpl validateSalesCd() is called");
			logger.debug("MobileSimInfoDTO SalesCd = " + mobileSimInfoDTO.getSalesCd());
			
			ResultDTO resultDTO= bulkNewActClient.checkBomLogin(mobileSimInfoDTO.getSalesCd());
			
			if (resultDTO != null) {
				logger.info("MobileSimInfoServiceImpl  bulkNewActClient.checkBomLogin() output ResultDTO Valid = " + resultDTO.getValid());
				
				if (BomWebPortalConstant.ERRCODE_STR_SUCCESS.equals(resultDTO.getErrCode())){
						result = resultDTO.getValid();
				}
			}
			
			logger.debug("MobileSimInfoServiceImpl validateSalesCd() result = " + result);
				
			return result;
			//return true;
		} catch (Exception e) {
			logger.error("Exception caught in validateSalesCd()", e);
			throw new AppRuntimeException(e);
		}
	}
*/		
	//add by Eliot 20110622
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public SalesmanDTO getSalesman (String salesType, String salesCd){

		SalesmanDTO salesmanDTO = new SalesmanDTO();
		
		try{
			logger.info("ImsPaymentServiceImpl getSalesman() is called");
			logger.debug("SalesCd = " + salesCd);
			logger.debug("SalesType = " + salesType);
			
			salesmanDTO = bulkNewActClient.getSalesman(salesType, salesCd);
						
			return salesmanDTO;
		} catch (Exception e) {
			logger.error("Exception caught in getSaleName()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getOrgSalesCd(String username){
		String orgSalesCd = null;
		
		try{
			orgSalesCd = getShopContactDao.getOrgSalesCd(username);
			
			return orgSalesCd;
		}catch (Exception e){
			logger.error("Exception caught in getOrgSalesCd()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getStaffNameByOrgSalesCd(String username){
		String staffName = null;
		
		try{
			staffName = getShopContactDao.getStaffNameByOrgSalesCd(username);
			
			return staffName;
		}catch (Exception e){
			logger.error("Exception caught in getStaffNameByOrgSalesCd()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getStaffNameBySalesCd(String salesCd){
		String staffName = null;
		
		try{
			staffName = getShopContactDao.getStaffNameBySalesCd(salesCd);
			
			return staffName;
		}catch (Exception e){
			logger.error("Exception caught in getStaffNameBySalesCd()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getReferrerStaffNameByNo(String referrerStaffNo){
		String referrerStaffName = null;
		
		try{
			referrerStaffName = getShopContactDao.getReferrerStaffNameByNo(referrerStaffNo);
			
			return referrerStaffName;
		}catch (Exception e){
			logger.error("Exception caught in getReferrerStaffNameByNo()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getStaffChannelCdBySalesCd(String salesCd){
		String staffName = null;
		
		try{
			staffName = getShopContactDao.getStaffChannelCdBySalesCd(salesCd);
			
			return staffName;
		}catch (Exception e){
			logger.error("Exception caught in getStaffNameBySalesCd()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getShopContact(String shopCd){
		String contactPhone = null;
		
		try{
			contactPhone = getShopContactDao.getShopContact(shopCd);
			
			return contactPhone;
		}catch (Exception e){
			logger.error("Exception caught in getShopContact()", e);
			throw new AppRuntimeException(e);
		}
	}
	//test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public InstallFeeUI getOtAmount(String technology, String housingtype, String serbdyno){

		InstallFeeUI InstallFee = new InstallFeeUI();
		
		try{
			
			//InstallFee = onetimeChargeDao.onetimeAmount(technology, housingtype, serbdyno);
			
			System.out.println("InstallFee:" + InstallFee.getOnetimeAmount() + "," + InstallFee.getInstallInstalmentAmt() +","+ InstallFee.getInstallInstalmentMth());

			
			return InstallFee;
		}catch (Exception e){
			logger.error("Exception caught in getOtAmount()", e);
			throw new AppRuntimeException(e);
		}
	}
		
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public InstallFeeUI getImsInstallationInstallmentPlanList(String technology, String housingtype, String serbdyno, String floor, String flat)
	{
		//List<ImsInstallationInstallmentPlanDTO> imsInstallationInstallmentPlanList=new ArrayList<ImsInstallationInstallmentPlanDTO>();
		InstallFeeUI InstallFee = new InstallFeeUI();
		
		try{
		InstallFee = onetimeChargeDao.onetimeAmount(technology, housingtype, serbdyno, floor, flat);
		//return basketInfoList;
		
		//imsInstallationInstallmentPlanList[]
		
		//ImsInstallationInstallmentPlanDTO dto1 = new ImsInstallationInstallmentPlanDTO();
		//ImsInstallationInstallmentPlanDTO dto2 = new ImsInstallationInstallmentPlanDTO();
		
		//dto1.setInstallmentPlanAmt("100");
		//dto1.setInstallmentPlanMnth("6");
		
		//dto2.setInstallmentPlanAmt("50");
		//dto2.setInstallmentPlanMnth("12");
		
		//imsInstallationInstallmentPlanList.add(dto1);
		//imsInstallationInstallmentPlanList.add(dto2);

		//imsInstallationInstallmentPlanList.set(0, dto1);
		//imsInstallationInstallmentPlanList.set(1, dto2);
		//		try{
		//			logger.info("getImsInstallationInstallmentPlanList() is called in service");
		//			imsInstallationInstallmentPlanList = onetimeChargeDao.getImsInstallationInstallmentPlan(technology, housingtype);
		//		}catch (DAOException de) {
		//			logger.error("Exception caught in getImsInstallationInstallmentPlanList()", de);
		//			throw new AppRuntimeException(de);
		//		}
		//		
		//		final int testcharge = 234;
		//		int testmnth = 12; 
		//		
		//		ParameterizedRowMapper<ImsInstallationInstallmentPlanDTO> mapper = new ParameterizedRowMapper<ImsInstallationInstallmentPlanDTO>() {
		//	        public ImsInstallationInstallmentPlanDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		//	        	ImsInstallationInstallmentPlanDTO imsInstallationInstallmentPlan = new ImsInstallationInstallmentPlanDTO();
		//	        	imsInstallationInstallmentPlan.setInstallmentPlanAmt(rs.getString(testcharge));        	
		//	        	imsInstallationInstallmentPlan.setInstallmentPlanMth(rs.getString("TYPE_DESC"));
		//	            return imsInstallationInstallmentPlan;
		//	        }};											
		
		System.out.println("InstallFee:" + InstallFee.getOnetimeAmount() + "," + InstallFee.getInstallInstalmentAmt() +","+ InstallFee.getInstallInstalmentMth());

		
		return InstallFee;
		}catch (Exception e){
			logger.error("Exception caught in getOtAmount()", e);
			throw new AppRuntimeException(e);
		}
		
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getIsValidForInstallInstallment(String technology, String housingtype){
		String IsValidForInstallInstallment = "Y";
		
		try{
			
			//IsValidForWaive = onetimeChargeDao.isValidForWaive(serbdyno, prodType, bandwidth, housingType, appdate);
			//return IsValidForWaive;
			
			return IsValidForInstallInstallment; 
			
		}catch (Exception e){
			logger.error("Exception caught in getIsValidForInstallInstallment()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getIsValidForWaive(String serbdyno, String prodType, String bandwidth, String housingType, Date appdate){
		String IsValidForWaive = null;
		
		try{
			
			IsValidForWaive = onetimeChargeDao.isValidForWaive(serbdyno, prodType, bandwidth, housingType, appdate);
			return IsValidForWaive;
			
		}catch (Exception e){
			logger.error("Exception caught in getIsValidForWaive()", e);
			//logger.error("Exception caught in getOtAmount()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<BasketDetailsDTO> getBasketInfo(String basketId){
		
		List<BasketDetailsDTO> basketInfoList = new ArrayList<BasketDetailsDTO>();
		
		try{
			
			basketInfoList = basketInfoDao.getBasketInfo(basketId);
			return basketInfoList;
			
		}catch (Exception e){
			logger.error("Exception caught in getBasketInfo()", e);
			throw new AppRuntimeException(e);
		}
	}

	//kinman20140428	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getDeflaultSourceCode(String username){
		String deflaultSourceCode = null;
		
		try{
			deflaultSourceCode = getShopContactDao.getDeflaultSourceCode(username);
			
			return deflaultSourceCode;
		}catch (Exception e){
			logger.error("Exception caught in getDeflaultSourceCode()", e);
			throw new AppRuntimeException(e);
		}
	}

	public void logCreditCardInfo(String orderId,String loginId, String srvNo,  String cardNo, String staffNo) {
		// TODO Auto-generated method stub
		try{
			getShopContactDao.insertCreditCardLog(orderId,loginId,srvNo, cardNo, staffNo);
		}catch (Exception e){
			logger.error("Exception caught in logCreditCardInfo()", e);
			//throw new AppRuntimeException(e);
		}
		
	}
	
	// 20190503, BOM2019038, order_id and reference_no = null
	public void insertCeksRecord(String order_id, String app_id, String reference_no, String ret_code, String pay_amount, String card_pan, 
			String exp_date, String ret_parm, String create_by, String gateway_code) {
		try{
			getShopContactDao.insertCeksRecord(order_id, app_id, reference_no, ret_code, pay_amount, card_pan, exp_date, ret_parm, create_by, gateway_code);
		}catch (Exception e){
			logger.error("Exception caught in insertCeksRecord()", e);
			//throw new AppRuntimeException(e);
		}
		
	}
	
	public String getSalemanContactNumByStaffId(String staff_id) {
		String contactPhone = null;
		
		try{
			contactPhone = getShopContactDao.getSalemanContactNumByStaffId(staff_id);
			
			return contactPhone;
		}catch (Exception e){
			logger.error("Exception caught in getSalemanContactNumByStaffId()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public String getSalesContactNumber(int channelId, String shopCd, String staffId, String salesCd) {
		String contactPhone = null;
		
		try{
			switch(channelId)
			{
				case 99:
					if (!StringUtil.isBlank(staffId))
					{
						contactPhone = getShopContactDao.getSalemanContactNumByStaffId(staffId);
					}
					if (!StringUtil.isBlank(shopCd) && StringUtil.isBlank(contactPhone))
					{
						contactPhone = getShopContactDao.getShopContact(shopCd);
					}
					if (StringUtil.isBlank(contactPhone))
					{
						contactPhone = "1000";
					}
					break;
				case 3:
					if (!StringUtil.isBlank(salesCd))
					{
						int saleNum = ptDao.getSalesNum(salesCd);
						if (saleNum > 0)
						{
							contactPhone = ""+saleNum;
							break;
						}
						
					}
					// if saleNum is 0, do the same logic with channel 2
				case 2:
					if (!StringUtil.isBlank(shopCd))
					{
						contactPhone = getShopContactDao.getShopContact(shopCd);
					}
					if (StringUtil.isBlank(contactPhone))
					{
						contactPhone = "1000";
					}
					break;
				default:
					if (!StringUtil.isBlank(shopCd))
					{
						contactPhone = getShopContactDao.getShopContact(shopCd);
					}
					if (!StringUtil.isBlank(staffId) && StringUtil.isBlank(contactPhone))
					{
						contactPhone = getShopContactDao.getSalemanContactNumByStaffId(staffId);
					}
					break;
			}
			
			return contactPhone;
		}catch (Exception e){
			logger.error("Exception caught in getSalemanContactNumByStaffId()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<BomwebOTDTO> getOverrideOTC(String staffId, String application, String prodType, String housingType, String sbno, String orderType, String fsaMarker){
		List<BomwebOTDTO> result = null;
		
		try{
			result = ptDao.getOverrideOTC(staffId, application, prodType, housingType, sbno, orderType, fsaMarker);
			
			return result;
		}catch (Exception e){
			logger.error("Exception caught in getStaffNameBySalesCd()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public InstallFeeUI getOTCByBasketId(String basketId){
		InstallFeeUI InstallFee = new InstallFeeUI();
		
		try{
			InstallFee = basketInfoDao.getOTCByBasketId(basketId);
			
			return InstallFee;
		}catch (Exception e){
			logger.error("Exception caught in getOTCByBasketId()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public InstallFeeUI getSpecialOTC(String basketId,String itemIdStr[]){
		InstallFeeUI InstallFee = new InstallFeeUI();
		
		try{
			InstallFee = basketInfoDao.getSpecialOTC(basketId,itemIdStr);
			
			return InstallFee;
		}catch (Exception e){
			logger.error("Exception caught in getSpecialOTC()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
}
