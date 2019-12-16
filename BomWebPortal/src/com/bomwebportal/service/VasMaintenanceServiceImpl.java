package com.bomwebportal.service;

import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.VasMaintenanceCreateDAO;
import com.bomwebportal.dao.VasMaintenanceDAO;
import com.bomwebportal.dto.VasMaintenanceDTO;
import com.bomwebportal.dto.WCreateItemDTO;
import com.bomwebportal.dto.WCreateItemDtlDTO;
import com.bomwebportal.dto.ui.VasMaintenanceUI;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.Utility;

public class VasMaintenanceServiceImpl implements VasMaintenanceService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private VasMaintenanceDAO vasMaintenanceDAO;
	
	private VasMaintenanceCreateDAO vasMaintenanceCreateDAO;

	public VasMaintenanceCreateDAO getVasMaintenanceCreateDAO() {
		return vasMaintenanceCreateDAO;
	}

	public void setVasMaintenanceCreateDAO(
			VasMaintenanceCreateDAO vasMaintenanceCreateDAO) {
		this.vasMaintenanceCreateDAO = vasMaintenanceCreateDAO;
	}

	public VasMaintenanceDAO getVasMaintenanceDAO() {
		return vasMaintenanceDAO;
	}

	public void setVasMaintenanceDAO(VasMaintenanceDAO vasMaintenanceDAO) {
		this.vasMaintenanceDAO = vasMaintenanceDAO;
	}

	public List<VasMaintenanceDTO> getVasMaintenanceDTO(String offerCdSearch) {
		try {
			return vasMaintenanceDAO.getVasMaintenanceDTO(offerCdSearch);
		} catch (DAOException e) {
			logger.error("Exception caught in getVasMaintenanceDTO()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public Long createVasItem(VasMaintenanceUI vasMaintenanceUI) {
		if (vasMaintenanceUI == null) {
			logger.warn("vasMaintenanceUI is null");
			return null;
		}
		if (logger.isInfoEnabled()) {
			logger.info("offerCd: " + StringUtils.join(vasMaintenanceUI.getOfferCd(), ' '));
			logger.info("prodCd: " + StringUtils.join(vasMaintenanceUI.getProdCd(), ' '));
			logger.info("mth2MthRate: " + StringUtils.join(vasMaintenanceUI.getMth2MthRate(), ' '));
			logger.info("prodId: " + StringUtils.join(vasMaintenanceUI.getProdId(), ' '));
			logger.info("offerId: " + StringUtils.join(vasMaintenanceUI.getOfferId(), ' '));
			logger.info("offerSubId: " + StringUtils.join(vasMaintenanceUI.getOfferSubId(), ' '));
			logger.info("itemDisplayEng: " + vasMaintenanceUI.getItemDisplayEng());
			logger.info("itemDisplayChi: " + vasMaintenanceUI.getItemDisplayChi());
			logger.info("ssDisplayEng: " + vasMaintenanceUI.getSsDisplayEng());
			logger.info("ssDisplayChi: " + vasMaintenanceUI.getSsDisplayChi());
			logger.info("recurrentAmt: " + vasMaintenanceUI.getRecurrentAmt());
			logger.info("itemType: " + vasMaintenanceUI.getItemType());
			logger.info("rebateAmt: " + vasMaintenanceUI.getRebateInput());
			logger.info("brand: " + vasMaintenanceUI.getMipBrand());
			logger.info("simType: " + vasMaintenanceUI.getMipSimType());
			logger.info("itemDiscription: " + vasMaintenanceUI.getItemDescription());
		}
		if (!this.isValuesLenSafe(vasMaintenanceUI)) {
			return null;
		}
		if (vasMaintenanceUI.getMipBrand() == null||vasMaintenanceUI.getMipSimType()==null) {
			return null;
		}
		try {
			String batchNo = Utility.getUid();
			if (logger.isInfoEnabled()) {
				logger.info("batchNo: " + batchNo);
			}
			
			
			for (int i = 0; i < vasMaintenanceUI.getOfferCd().length; i++) {
				VasMaintenanceDTO dto = this.vasMaintenanceDAO.getUniqueVasMaintenanceDTO(vasMaintenanceUI.getOfferCd()[i], vasMaintenanceUI.getProdCd()[i], vasMaintenanceUI.getProdId()[i], vasMaintenanceUI.getOfferId()[i], vasMaintenanceUI.getOfferSubId()[i]);
				// set
				WCreateItemDTO wCreateItemDto = new WCreateItemDTO();
				wCreateItemDto.setBatchNo(batchNo);
				wCreateItemDto.setSeq((i+1) + "");
				wCreateItemDto.setMth2MthRate(vasMaintenanceUI.getMth2MthRate()[i]);
				wCreateItemDto.setOfferType(dto.getOfferType());
				wCreateItemDto.setOfferId(dto.getOfferId());
				wCreateItemDto.setOfferCd(dto.getOfferCd());
				wCreateItemDto.setOfferDesc(dto.getOfferDesc());
				wCreateItemDto.setOfferSelectQty("1");
				wCreateItemDto.setOfferSubId(dto.getOfferSubId());
				wCreateItemDto.setProdType(dto.getProdType());
				wCreateItemDto.setProdId(dto.getProdId());
				wCreateItemDto.setProdCd(dto.getProdCd());
				wCreateItemDto.setProdDesc(dto.getProdDesc());
				wCreateItemDto.setProdSelectQty("1");
				wCreateItemDto.setProdCd(dto.getProdCd());
				wCreateItemDto.setMipBrand(vasMaintenanceUI.getMipBrand());
				wCreateItemDto.setMipSimType(vasMaintenanceUI.getMipSimType());
				
				
				//update brand and simtype
				wCreateItemDto.setOfferBrand(dto.getOfferBrand());
				wCreateItemDto.setOfferSimType(dto.getOfferSimType());
				wCreateItemDto.setProdBrand(dto.getBrand());
				wCreateItemDto.setProdSimType(dto.getSimType());
				
				
				//missing POS ITEM CODE
				wCreateItemDto.setPosItemCd(dto.getPosItemCd());
				
				this.vasMaintenanceCreateDAO.insertWCreateItem(wCreateItemDto);

				//BeanUtils.copyProperties(dto, wCreateItemDto);

							
			}
			
						
			WCreateItemDtlDTO wCreateItemDtlDto = new WCreateItemDtlDTO();
			
			wCreateItemDtlDto.setBatchNo(batchNo);
			wCreateItemDtlDto.setType(vasMaintenanceUI.getItemType());
			wCreateItemDtlDto.setRecurrentAmt(vasMaintenanceUI.getRecurrentAmt());
			wCreateItemDtlDto.setDisplayEng(vasMaintenanceUI.getItemDisplayEng());
			wCreateItemDtlDto.setDisplayChi(vasMaintenanceUI.getItemDisplayChi());
			wCreateItemDtlDto.setDisplaySSEng(vasMaintenanceUI.getSsDisplayEng());
			wCreateItemDtlDto.setDisplaySSChi(vasMaintenanceUI.getSsDisplayChi());
			wCreateItemDtlDto.setItemDesc(vasMaintenanceUI.getItemDescription());

			wCreateItemDtlDto.setOneTimeAmt(vasMaintenanceUI.getOneTimeAmt());
			wCreateItemDtlDto.setEffStartDate(vasMaintenanceUI.getEffStartDate());
			wCreateItemDtlDto.setRebateAmt(vasMaintenanceUI.getRebateInput());
			wCreateItemDtlDto.setRebateDisplayEng(vasMaintenanceUI.getRebateDisplayEng());
			wCreateItemDtlDto.setRebateDisplayChi(vasMaintenanceUI.getRebateDisplayChi());
			wCreateItemDtlDto.setMipBrand(vasMaintenanceUI.getMipBrand());
			wCreateItemDtlDto.setMipSimType(vasMaintenanceUI.getMipSimType());
			
			
			this.vasMaintenanceCreateDAO.insertWCreateItemDtl(wCreateItemDtlDto);
			
			
	
			Long itemId = this.vasMaintenanceCreateDAO.createNewVasItemM(batchNo);
			wCreateItemDtlDto.setItemId(String.valueOf(itemId));

			if(wCreateItemDtlDto.getRebateAmt()!=null){
			 this.vasMaintenanceCreateDAO.updateRpRbVasDtl(wCreateItemDtlDto);
			}

			if(vasMaintenanceUI.isCheckOnlineInd()){
				this.vasMaintenanceCreateDAO.updateOnlineInd(wCreateItemDtlDto);
			}
			return itemId;
		} catch (DAOException e) {
			logger.warn("DAOException in createVasItem()", e);
			throw new AppRuntimeException(e);
		}	
	}
	
	private <E> int valuesLen(E[] values) {
		if (values == null) {
			return -1;
		}
		return values.length;
	}
	
	private boolean isValuesLenSafe(VasMaintenanceUI vasMaintenanceUI) {
		int offerCdLen = valuesLen(vasMaintenanceUI.getOfferCd());
		if (offerCdLen < 1) {
			logger.warn("offerCdLen: " + offerCdLen);
			return false;
		}
		int prodCdLen = valuesLen(vasMaintenanceUI.getProdCd());
		if (offerCdLen != prodCdLen) {
			logger.warn("offerCdLen: " + offerCdLen + ", prodCdLen: " + prodCdLen);
			return false;
		}
		int mth2MthRateLen = valuesLen(vasMaintenanceUI.getMth2MthRate());
		if (offerCdLen != mth2MthRateLen) {
			logger.warn("offerCdLen: " + offerCdLen + ", mth2MthRateLen: " + mth2MthRateLen);
			return false;
		}
		int prodIdLen = valuesLen(vasMaintenanceUI.getProdId());
		if (offerCdLen != prodIdLen) {
			logger.warn("offerCdLen: " + offerCdLen + ", prodIdLen: " + prodIdLen);
			return false;
		}
		int offerIdLen = valuesLen(vasMaintenanceUI.getOfferId());
		if (offerCdLen != offerIdLen) {
			logger.warn("offerCdLen: " + offerCdLen + ", offerIdLen: " + offerIdLen);
			return false;
		}
		int offerSubIdLen = valuesLen(vasMaintenanceUI.getOfferSubId());
		if (offerCdLen != offerSubIdLen) {
			logger.warn("offerCdLen: " + offerCdLen + ", offerSubIdLen: " + offerSubIdLen);
			return false;
		}
		return true;
	}

	public List<VasMaintenanceDTO> getRecurringAmt(String prodId) {
		try{
			return vasMaintenanceDAO.getRecurringAmt(prodId);
		}catch (DAOException e){
			logger.error("Exception caught in getRecurringAmt()",e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<VasMaintenanceDTO> getOneTimeAmt(String prodId) {
		try{
			return vasMaintenanceDAO.getOneTimeAmt(prodId);
		}catch (DAOException e){
			logger.error("Exception caught in getOneTimeAmt()",e);
			throw new AppRuntimeException(e);
		}
	}	
	//vincy getOneTimeRecurringAmt
	public List<VasMaintenanceDTO> getOneTimeRecurringAmt(String offerCd) {
		try{
			return vasMaintenanceDAO.getOneTimeRecurringAmt(offerCd);
		}catch (DAOException e){
			logger.error("Exception caught in getOneTimeRecurringAmt()",e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<VasMaintenanceDTO> getParamsAlert(String prodId) {
		try{
			return vasMaintenanceDAO.getParamsAlert(prodId);
		}catch (DAOException e){
			logger.error("Exception caught in getParamsAlert()",e);
			throw new AppRuntimeException(e);
		}
	}
}
