package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.ImsSbOrderDAO;
import com.bomwebportal.lts.dto.ImsSbOrderDetailDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.ServiceActionBase;
import com.pccw.util.spring.SpringApplicationContext;

public class ImsSbOrderServiceImpl implements ImsSbOrderService {

	protected final Log logger = LogFactory.getLog(getClass());

	private ImsSbOrderDAO imsSbOrderDAO;
	private ImsOfferLookupService imsOfferLookupService;
	private CodeLkupCacheService applMethodLkupCacheService;
	private CodeLkupCacheService sourceCdLkupCacheService;
	private SbOrderInfoLtsService sbOrderInfoLtsService;
	
	
	public void createImsEyeOrder(SbOrderDTO pSbOrder, String pUser) {
		
		ServiceDetailLtsDTO srvDtlEyeLts = LtsSbHelper.getLtsService(pSbOrder);
		ServiceDetailOtherLtsDTO srvDtlIms = LtsSbHelper.getImsEyeService(pSbOrder.getSrvDtls());
		
		if (srvDtlIms == null) {
			return;
		}
		try {
			AppointmentDetailLtsDTO appnt = srvDtlEyeLts.getAppointmentDtl();
			String viewAvInd = null;
			
			if (srvDtlIms.getNowtvDetail() != null) {
				viewAvInd = srvDtlIms.getNowtvDetail().getViewAvInd();
			}
			String langPref = pSbOrder.getLangPref();
			
			if (LtsSbHelper.isOnlineAcquistionOrder(pSbOrder) ) {
				langPref = StringUtils.equals(srvDtlEyeLts.getAccount().getBillLang(), LtsBackendConstant.ACCT_BILL_LANG_ENGLISH) ? "ENG" : "CHI";
			}
			
			String vimInd = null;
			
			if (LtsBackendConstant.ORDER_TYPE_SB_UPGRADE.equals(pSbOrder.getOrderType())
					&& StringUtils.equals(srvDtlEyeLts.getDeviceType(), LtsBackendConstant.DEVICE_TYPE_EYE2A)) {
				 vimInd = "P";
			}
			
			String imsOrderType = null;
			
			if (LtsBackendConstant.ORDER_PREFIX_DIRECT_SALES.equals(pSbOrder.getOrderId().substring(0, 1))
					||LtsBackendConstant.ORDER_PREFIX_DIRECT_SALES_NOW_TV_QA.equals(pSbOrder.getOrderId().substring(0, 1))){
				imsOrderType = "DS";
			}
			
			String fieldInd = "Y";
			
			this.imsSbOrderDAO.createEyeImsOrder(pSbOrder.getOrderId(), srvDtlIms.getLoginId(), vimInd, viewAvInd, langPref, 
					StringUtils.removeStart(srvDtlEyeLts.getSrvNum(), "0000"), pSbOrder.getSalesTeam(), 
					(String)this.applMethodLkupCacheService.get(pSbOrder.getSalesChannel()),
					pUser, imsOrderType, fieldInd);
			
			if (pSbOrder.getContact()!=null) {
				if (StringUtils.isBlank(pSbOrder.getContact().getContactPhone())) {
					pSbOrder.getContact().setContactPhone(appnt.getInstContactNum());
				}
				if (StringUtils.isBlank(pSbOrder.getContact().getEmailAddr())) {
					pSbOrder.getContact().setEmailAddr(pSbOrder.getEsigEmailAddr());
				}
				this.imsSbOrderDAO.updateImsContact(pSbOrder.getOrderId(), pSbOrder.getContact(), pUser);
			}
			this.createImsOffers(pSbOrder, pUser);
		} catch (DAOException de) {
			logger.error("Fail in ImsOrderService.createImsInstallOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private void createImsOffers(SbOrderDTO pSbOrder, String pUser) {
		
		ServiceDetailDTO[] srvDtls = pSbOrder.getSrvDtls();
		ImsOfferDetailDTO[] offerDtls = null;
		String orderId = pSbOrder.getOrderId();
		ServiceDetailLtsDTO srvDtlLts = LtsSbHelper.getLtsService(pSbOrder);
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			if (srvDtls[i] instanceof ServiceDetailOtherLtsDTO) {
				offerDtls = this.imsOfferLookupService.getImsOffersByService((ServiceDetailOtherLtsDTO)srvDtls[i], srvDtlLts.getDeviceType(), 
						ArrayUtils.isNotEmpty(((ServiceDetailOtherLtsDTO)srvDtls[i]).getOffersByType(LtsBackendConstant.ITEM_TYPE_WALL_GARDEN)) ? "Y" : "N");
				
				for (int j=0; offerDtls!=null && j<offerDtls.length; ++j) {
					offerDtls[j].setIoInd(LtsBackendConstant.IO_IND_INSTALL);
					((ServiceDetailOtherLtsDTO)srvDtls[i]).appendOfferDtl(offerDtls[j]);
					this.saveImsOfferDetails(offerDtls[j], orderId, srvDtls[i].getDtlId(), pUser);
				}
			}
		}
	}
	
	private void saveImsOfferDetails(ImsOfferDetailDTO pOfferDtl, String pOrderId, String pDtlId, String pUser) {
		
		if (pOfferDtl == null) {
			return;
		}
		ServiceActionBase imsOfferDetailService = SpringApplicationContext.getBean("imsOfferDetailService");
		imsOfferDetailService.doSave(pOfferDtl, pUser, pOrderId, pDtlId);
	}

	public ImsSbOrderDTO getPcdSbOrder(String pIOrderId) {
		
//		try {
//			ImsSbOrderDTO imsSbOrder = this.imsSbOrderDAO.getPcdSbOrder(pIOrderId);
			ImsSbOrderDetailDTO imsSbOrderDetail = sbOrderInfoLtsService.getPcdSbOrder(pIOrderId);
			ImsSbOrderDTO imsSbOrder = convertImsSbOrderDetailDTO(imsSbOrderDetail);

			setPreInstallOrder(imsSbOrder);
			setImsSbOrderFullAddress(imsSbOrder);
			return imsSbOrder;
//		} catch (DAOException de) {
//			logger.error("Fail in ImsOrderService.getPcdSbOrder()", de);
//			throw new AppRuntimeException(de);
//		}
	}

	
	public String getPcdSbOrderHasDel(String pIOrderId, String pcdBundleFreeType) {
		
		return sbOrderInfoLtsService.getPcdSbOrderHasDel(pIOrderId,pcdBundleFreeType);
		
	}
	
	public String checkAnyActiveServiceWithinXMonths(String srvbdry_num, String floor_num, String unit_num, String prevSerTermMth) {
		return sbOrderInfoLtsService.checkAnyActiveServiceWithinXMonths(srvbdry_num, floor_num, unit_num, prevSerTermMth);
	}
	
	private void setPreInstallOrder(ImsSbOrderDTO imsSbOrder) {
		String preInstallInd = getPreInstallInd(imsSbOrder.getOrderId());
		imsSbOrder.setPreInstallInd(preInstallInd);
	}
	
	private ImsSbOrderDTO convertImsSbOrderDetailDTO(ImsSbOrderDetailDTO imsSbOrderDetail){
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.0";
		ImsSbOrderDTO imsSbOrder = new ImsSbOrderDTO();
		
		imsSbOrder.setAppntEndTime(new DateTime(imsSbOrderDetail.getAppntEndTime()).toString(DateTimeFormat.forPattern(DATE_FORMAT)));
		imsSbOrder.setAppntStartTime(new DateTime(imsSbOrderDetail.getAppntStartTime()).toString(DateTimeFormat.forPattern(DATE_FORMAT)));
		imsSbOrder.setAppntType(imsSbOrderDetail.getAppntType());
		imsSbOrder.setAreaCd(imsSbOrderDetail.getAreaCd());
		imsSbOrder.setAreaDesc(imsSbOrderDetail.getAreaDesc());
		imsSbOrder.setBandwidth(imsSbOrderDetail.getBandwidth());
		imsSbOrder.setBuildNo(imsSbOrderDetail.getBuildNo());
		imsSbOrder.setDistDesc(imsSbOrderDetail.getDistDesc());
		imsSbOrder.setDistNo(imsSbOrderDetail.getDistNo());
//		imsSbOrder.setErrCode(pErrCode);
//		imsSbOrder.setErrText(pErrText);
		imsSbOrder.setFloorNo(imsSbOrderDetail.getFloorNo());
//		imsSbOrder.setFullAddress(imsSbOrderDetail.get);
		imsSbOrder.setHiLotNo(imsSbOrderDetail.getHiLotNo());
		imsSbOrder.setIdDocNum(imsSbOrderDetail.getIdDocNum());
		imsSbOrder.setIdDocType(imsSbOrderDetail.getIdDocType());
		imsSbOrder.setInstContactName(imsSbOrderDetail.getInstContactName());
		imsSbOrder.setInstContactNum(imsSbOrderDetail.getInstContactNum());
		imsSbOrder.setInstSmsNum(imsSbOrderDetail.getInstSmsNum());
		imsSbOrder.setOrderId(imsSbOrderDetail.getOrderId());
//		imsSbOrder.setRetVal(imsSbOrderDetail.get);
		imsSbOrder.setSectCd(imsSbOrderDetail.getSectCd());
		imsSbOrder.setSectDesc(imsSbOrderDetail.getSectDesc());
		imsSbOrder.setSerbdyno(imsSbOrderDetail.getSerbdyno());
		imsSbOrder.setSerialNum(imsSbOrderDetail.getSerialNum());
		imsSbOrder.setSrvReqDate(new DateTime(imsSbOrderDetail.getSrvReqDate()).toString(DateTimeFormat.forPattern(DATE_FORMAT)));
		imsSbOrder.setSrvType(imsSbOrderDetail.getSrvType());
		imsSbOrder.setStrCatCd(imsSbOrderDetail.getStrCatCd());
		imsSbOrder.setStrCatDesc(imsSbOrderDetail.getStrCatDesc());
		imsSbOrder.setStrName(imsSbOrderDetail.getStrName());
		imsSbOrder.setStrNo(imsSbOrderDetail.getStrNo());
		imsSbOrder.setTechnology(imsSbOrderDetail.getTechnology());
		imsSbOrder.setUnitNo(imsSbOrderDetail.getUnitNo());
		imsSbOrder.setHasRentalRouter(imsSbOrderDetail.getHasRentalRouter());
		imsSbOrder.setHasMeshRouter(imsSbOrderDetail.getHasMeshRouter());
		imsSbOrder.setHasBrmWifi(imsSbOrderDetail.getHasBrmWifi());
		return imsSbOrder;
	}
	
	private void setImsSbOrderFullAddress(ImsSbOrderDTO imsSbOrder) {
		
		if (imsSbOrder == null) {
			return;
		}
		
		StringBuilder fullAddress = new StringBuilder();
		if (StringUtils.isNotBlank(imsSbOrder.getUnitNo())) {
			fullAddress.append("FLAT ").append(imsSbOrder.getUnitNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getFloorNo())) {
			fullAddress.append(imsSbOrder.getFloorNo()).append("/FLOOR ").append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getBuildNo())) {
			fullAddress.append(imsSbOrder.getBuildNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getSectDesc())) {
			fullAddress.append(imsSbOrder.getSectDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getHiLotNo())) {
			fullAddress.append("HSE ").append(imsSbOrder.getHiLotNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getStrNo())) {
			fullAddress.append(imsSbOrder.getStrNo()).append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getStrName())) {
			fullAddress.append(imsSbOrder.getStrName()).append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getStrCatDesc())) {
			fullAddress.append(imsSbOrder.getStrCatDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getDistDesc())) {
			fullAddress.append(imsSbOrder.getDistDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(imsSbOrder.getAreaDesc())) {
			fullAddress.append(imsSbOrder.getAreaDesc()).append(", ");	
		}
		
		imsSbOrder.setFullAddress(fullAddress.substring(0, fullAddress.length()-2).toString());
	}
	
	public String getFsaNumOnImsSbOrder(String pImsSbOrderId) {
		
		try {
			return this.imsSbOrderDAO.getFsaNumOnImsSbOrder(pImsSbOrderId);
		} catch (DAOException de) {
			logger.error("Fail in ImsOrderService.getFsaNumOnImsSbOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getLangPreferenceImsSbOrder(String pOrderId) {
		
		try {
			return this.imsSbOrderDAO.getLangPreferenceImsSbOrder(pOrderId);
		} catch (DAOException de) {
			logger.error("Fail in ImsOrderService.getLangPreferenceImsSbOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getPreInstallInd(String imsSbOrderId) {		
		try {
			return this.imsSbOrderDAO.getPreInstallInd(imsSbOrderId);
		} catch (DAOException de) {
			logger.error("Fail in ImsOrderService.getPreInstallInd()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ImsSbOrderDAO getImsSbOrderDAO() {
		return this.imsSbOrderDAO;
	}

	public void setImsSbOrderDAO(ImsSbOrderDAO pImsSbOrderDAO) {
		this.imsSbOrderDAO = pImsSbOrderDAO;
	}

	public ImsOfferLookupService getImsOfferLookupService() {
		return this.imsOfferLookupService;
	}

	public void setImsOfferLookupService(
			ImsOfferLookupService pImsOfferLookupService) {
		this.imsOfferLookupService = pImsOfferLookupService;
	}

	public CodeLkupCacheService getApplMethodLkupCacheService() {
		return applMethodLkupCacheService;
	}

	public void setApplMethodLkupCacheService(
			CodeLkupCacheService applMethodLkupCacheService) {
		this.applMethodLkupCacheService = applMethodLkupCacheService;
	}

	public CodeLkupCacheService getSourceCdLkupCacheService() {
		return sourceCdLkupCacheService;
	}

	public void setSourceCdLkupCacheService(
			CodeLkupCacheService sourceCdLkupCacheService) {
		this.sourceCdLkupCacheService = sourceCdLkupCacheService;
	}

	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}
}
