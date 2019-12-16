package com.bomwebportal.mob.cos.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.cos.dao.MobCosCampaignDAO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignDtlDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignHdrDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignVasDTO;

@Transactional(readOnly=true)
public class MobCosCampaignServiceImpl implements MobCosCampaignService{
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosCampaignDAO mobCosCampaignDAO;
	
	public MobCosCampaignDAO getMobCosCampaignDAO() {
		return mobCosCampaignDAO;
	}
	public void setMobCosCampaignDAO(MobCosCampaignDAO mobCosCampaignDAO) {
		this.mobCosCampaignDAO = mobCosCampaignDAO;
	}
	
	/****** Campaign Page (header) ******/
	public List<MobCosCampaignHdrDTO> getCampaignTitle(String cpgTitle, String cpgName, String handsetDesc) {
		try {
			return mobCosCampaignDAO.getCampaignTitle(cpgTitle, cpgName, handsetDesc);
		} catch (DAOException e) {
			logger.error("Exception caught in getCampaignTitle()", e);
		}
		return null;
	}	

	public List<MobCosCampaignHdrDTO> getResultOption(String campId) {
		try {
			return mobCosCampaignDAO.getResultOption(campId);
		} catch (DAOException e) {
			logger.error("Exception caught in getResultOption()", e);
		}
		return null;
	}
	
	public MobCosCampaignHdrDTO getCpgHdr(String cpgId) {
		try {
			return mobCosCampaignDAO.getCpgHdr(cpgId);
		} catch (DAOException e) {
			logger.error("Exception caught in getCpgHdr()", e);
		}
		return null;
	}
	
	public int createCpgHdr(String cpgTitle, String cpgName, String cpgHS, String userId) {
		try {
			return mobCosCampaignDAO.createCpgHdr(cpgTitle, cpgName, cpgHS, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in createCpgHdr()", e);
		}
		return 0;
	}
	
	public boolean createCpgChannelAssgn(String channelId, String cpgId, String customerTier, String userId) {
		try {
			return mobCosCampaignDAO.createCpgChannelAssgn(channelId, cpgId, customerTier, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in createCpgChannelAssgn()", e);
		}
		return false;
	}
	
	/****** Campaign Page (detail) ******/
	public List<MobCosCampaignDtlDTO> getCpgDtl(String cpgId) {
		try {
			return mobCosCampaignDAO.getCpgDtl(cpgId);
		} catch (DAOException e) {
			logger.error("Exception caught in getCpgDtl()", e);
		}
		return null;
	}
	
	public List<CodeLkupDTO> getTierOption() {
		try {
			return mobCosCampaignDAO.getTierOption();
		} catch (DAOException e) {
			logger.error("Exception caught in getTierOption()", e);
		}
		return null;
	}
	
	public String genBasketId(String cpgId, String basketSeq, String basketId, String basketDesc, String userId) {
		try {
			return mobCosCampaignDAO.genBasketId(cpgId, basketSeq, basketId, basketDesc, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in genBasketId()", e);
		}
		return null;
	}
	
	public boolean activeCpgBasket(String cpgId, String basketId, String userId) {
		try {
			return mobCosCampaignDAO.activeCpgBasket(cpgId, basketId, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in activeCpgBasket()", e);
		}
		return false;
	}
	
	public boolean deactiveCpgBasket(String cpgId, String basketId, String userId){
		try {
			return mobCosCampaignDAO.deactiveCpgBasket(cpgId, basketId, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in deactiveCpgBasket()", e);
		}
		return false;
	}

	/****** Edit Campaign VAS Page******/
	public List<MobCosCampaignVasDTO> getCpgVasList(String locale) {
		try {
			List<MobCosCampaignVasDTO> displayVasList = mobCosCampaignDAO.getCpgVasList();
			if(!CollectionUtils.isEmpty(displayVasList)) {
				for(MobCosCampaignVasDTO vas : displayVasList) {
					vas.setItemHtml(mobCosCampaignDAO.getDisplayVasList(vas.getItemId(), locale));
				}
			}
			return displayVasList;
		} catch (DAOException e) {
			logger.error("Exception caught in getCpgVasList()", e);
		}
		return null;
	}
	
	public List<MobCosCampaignVasDTO> getBasVasList(String basketId) {
		try {
			return mobCosCampaignDAO.getBasVasList(basketId);
		} catch (DAOException e) {
			logger.error("Exception caught in getBasVasList()", e);
		}
		return null;
	}
	
	/****** Add Campaign Basket Page ******/
	public List<CodeLkupDTO> getRpList() {
		try {
			return mobCosCampaignDAO.getRpList();
		} catch (DAOException e) {
			logger.error("Exception caught in getRpList()", e);
		}
		return null;
	}
	
	public List<CodeLkupDTO> getBundleList(String ratePlan) {
		try {
			return mobCosCampaignDAO.getBundleList(ratePlan);
		} catch (DAOException e) {
			logger.error("Exception caught in getBundleList()", e);
		}
		return null;
	}
	
	public List<CodeLkupDTO> getContractList(String ratePlan, String bundle) {
		try {
			return mobCosCampaignDAO.getContractList(ratePlan, bundle);
		} catch (DAOException e) {
			logger.error("Exception caught in getContractList()", e);
		}
		return null;
	}
	
	public List<CodeLkupDTO> getHandsetList(String ratePlan, String bundle, String contract) {
		try {
			return mobCosCampaignDAO.getHandsetList(ratePlan, bundle, contract);
		} catch (DAOException e) {
			logger.error("Exception caught in getHandsetList()", e);
		}
		return null;
	}
	
	public List<CodeLkupDTO> getBasketList(String ratePlan, String bundle, String contract) {
		try {
			return mobCosCampaignDAO.getBasketList(ratePlan, bundle, contract);
		} catch (DAOException e) {
			logger.error("Exception caught in getBasketList()", e);
		}
		return null;
	}
	
	public boolean createCpgBasket(String cpgId, String basketId, String basketDesc, String tier, String userId) {
		try {
			return mobCosCampaignDAO.createCpgBasket(cpgId, basketId, basketDesc, tier, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in createCpgBasket()", e);
		}
		return false;
	}

	/****** Update Campaign ******/
	public boolean updCpgHdr(String cpgId, String cpgTitle, String cpgName, String handset, Date effStartDate, Date effEndDate, String userId) {
		try {
			return mobCosCampaignDAO.updCpgHdr(cpgId, cpgTitle, cpgName, handset, effStartDate, effEndDate, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in updCpgHdr()", e);
		}
		return false;
	}

	public boolean updCpgDtl(String cpgId, String basketId, String basketDesc, String seq, String tier, 
			Date effStartDate, Date effEndDate, String userId) {
		try {
			return mobCosCampaignDAO.updCpgDtl(cpgId, basketId, basketDesc, seq, tier, effStartDate, effEndDate, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in updCpgDtl()", e);
		}
		return false;
	}
	
	public boolean updCpgVas(String basketId, String itemId, String action, String userId) {
		try {
			return mobCosCampaignDAO.updCpgVas(basketId, itemId, action, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in updCpgVas()", e);
		}
		return false;
	}

	public boolean updCpgChannelAssgn(String channelId, String cpgId, String customerTier, String userId) {
		try {
			return mobCosCampaignDAO.updCpgChannelAssgn(channelId, cpgId, customerTier, userId);
		} catch (DAOException e) {
			logger.error("Exception caught in updCpgChannelAssgn()", e);
		}
		return false;
	}
	
	// MIP.P4 modification
	public BasketDTO getBasketAttribute(String basketId, String appDate) {
		try{
			return mobCosCampaignDAO.getBasketAttribute(basketId, appDate);
		}catch (DAOException de){
			logger.error("Exception caught in getBasketAttribute()", de);
			throw new AppRuntimeException(de);			
		}
	}
		
	/****** Preview Campaign Basket******/
	// MIP.P4 modification
	public List<VasDetailDTO> getPreviewBundleList(String basketId, String locale, String appDate, String channelCd, String offerNature) {
		try {
			// MIP.P4 modification
			return mobCosCampaignDAO.getPreviewBundleList(basketId, locale, appDate, channelCd, offerNature);
		} catch (DAOException e) {
			logger.error("Exception caught in getPreviewBundleList()", e);
		}
		return null;
	}
	
	public List<VasDetailDTO> getPreviewSimList(String basketId, String locale, String appDate, String channelCd, String offerNature) {
		try {
			return mobCosCampaignDAO.getPreviewSimList(basketId, locale, appDate, channelCd, offerNature);
		} catch (DAOException e) {
			logger.error("Exception caught in getPreviewSimList()", e);
		}
		return null;
	}
	
	
	// reference getPreviewOptionList without ", String offerNature". Cannot get references, which means no program use getPreviewOptionList in this project
	public List<VasDetailDTO> getPreviewOptionList(String basketId, String locale, String appDate, String channelId, String channelCd, String offerNature) {
		try {
			return mobCosCampaignDAO.getPreviewOptionList(basketId, locale, appDate, channelId, channelCd, offerNature);
		} catch (DAOException e) {
			logger.error("Exception caught in getPreviewOptionList()", e);
		}
		return null;
	}
}
