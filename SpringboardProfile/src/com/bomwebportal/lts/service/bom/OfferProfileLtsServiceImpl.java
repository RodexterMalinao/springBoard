package com.bomwebportal.lts.service.bom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.OfferProfileLtsDAO;
import com.bomwebportal.lts.dto.bom.BomAttbDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailCommitmentDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailDiscountDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailIncentiveDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailRecurringChargesDTO;

public class OfferProfileLtsServiceImpl implements OfferProfileLtsService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private OfferProfileLtsDAO offerProfileLtsDao;
	private CodeLkupCacheService del2eyeCoreOfferCacheService;
	private CodeLkupCacheService eyeOfferCacheService;
	private CodeLkupCacheService eyeDeviceCacheService;
	
	public CodeLkupCacheService getEyeDeviceCacheService() {
		return eyeDeviceCacheService;
	}

	public void setEyeDeviceCacheService(CodeLkupCacheService eyeDeviceCacheService) {
		this.eyeDeviceCacheService = eyeDeviceCacheService;
	}

	public OfferProfileLtsDAO getOfferProfileLtsDao() {
		return offerProfileLtsDao;
	}

	public void setOfferProfileLtsDao(OfferProfileLtsDAO offerProfileLtsDao) {
		this.offerProfileLtsDao = offerProfileLtsDao;
	}
	
	public CodeLkupCacheService getDel2eyeCoreOfferCacheService() {
		return del2eyeCoreOfferCacheService;
	}

	public void setDel2eyeCoreOfferCacheService(
			CodeLkupCacheService del2eyeCoreOfferCacheService) {
		this.del2eyeCoreOfferCacheService = del2eyeCoreOfferCacheService;
	}	

	public CodeLkupCacheService getEyeOfferCacheService() {
		return eyeOfferCacheService;
	}

	public void setEyeOfferCacheService(CodeLkupCacheService eyeOfferCacheService) {
		this.eyeOfferCacheService = eyeOfferCacheService;
	}

	public OfferDetailProfileLtsDTO[] getLtsEndedOfferProfile(String pCcSrvId, String pApplnDate) {

		try {
			if (StringUtils.isBlank(pApplnDate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				pApplnDate = dateFormat.format(new Date());
			}
			return this.offerProfileLtsDao.getLtsEndedOfferProfile(pCcSrvId, pApplnDate);
		} catch (DAOException de) {
			logger.error("Fail in OfferProfileLtsService.getLtsEndedOfferProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public OfferDetailProfileLtsDTO[] getLtsOfferProfile(String pCcSrvId, String pApplnDate) {

		try {
			if (StringUtils.isBlank(pApplnDate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				pApplnDate = dateFormat.format(new Date());
			}
			return this.offerProfileLtsDao.getLtsOfferProfile(pCcSrvId, pApplnDate);
		} catch (DAOException de) {
			logger.error("Fail in OfferProfileLtsService.getLtsOfferProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public OfferDetailProfileLtsDTO[] getImsOfferProfile(String pFsa, String pApplnDate) {

		try {
			if (StringUtils.isBlank(pApplnDate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				pApplnDate = dateFormat.format(new Date());
			}
			return this.offerProfileLtsDao.getImsOfferProfile(pFsa, pApplnDate);
		} catch (DAOException de) {
			logger.error("Fail in OfferProfileLtsService.getImsOfferProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public void getProfileDetails(ServiceDetailProfileLtsDTO pProfileSrv) {
		
		String[] comptPsefs = null;
		
		try {
			comptPsefs = this.offerProfileLtsDao.getComponentPsefProfile(pProfileSrv.getCcSrvId());
		} catch (DAOException de) {
			logger.error("Fail in OfferProfileLtsService.getProfileDetails()", de);
			throw new AppRuntimeException(de);
		}
		LookupItemDTO[] del2EyeOfferLookups = (LookupItemDTO[])this.del2eyeCoreOfferCacheService.get(CodeLkupCacheService.WILD_CARDS);
		Set<String> del2EyeOfferSet = new HashSet<String>();
		
		if (ArrayUtils.isNotEmpty(del2EyeOfferLookups)) {
			for (int i=0; i<del2EyeOfferLookups.length; ++i) {
				del2EyeOfferSet.add(del2EyeOfferLookups[i].getItemKey());
			}
		}
		boolean containsCoreOffer = false;
		
		for (int i=0; comptPsefs!=null && i<comptPsefs.length; ++i) {
			if (!containsCoreOffer && del2EyeOfferSet.contains(comptPsefs[i])) {
				containsCoreOffer = true;
			}
			if (StringUtils.isEmpty(pProfileSrv.getExistEyeType())) {	
				pProfileSrv.setExistEyeType((String)this.eyeOfferCacheService.get(comptPsefs[i]));
			}
		}
		if (!containsCoreOffer) {
			pProfileSrv.setMissingCorePsefs(del2EyeOfferSet.toArray(new String[del2EyeOfferSet.size()]));
		}		
	}
	
	public List<String> getPsefCdByOfferId(List<String> pOfferList) {
		
		try {
			return this.offerProfileLtsDao.getPsefCdByOfferId(pOfferList);
		} catch (DAOException de) {
			logger.error("Fail in OfferProfileLtsService.getPsefCdByOfferId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<OfferDetailCommitmentDTO> getCommitment(String pSystemId, String pServiceId, String effectiveInd) {
		try {
			return this.offerProfileLtsDao.getCommitment(pSystemId, pServiceId, effectiveInd);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getCommitment()", de);
			throw new AppRuntimeException(de);
		}		
	}
	
	public  List<OfferDetailRecurringChargesDTO> getRecurringCharges(String pSystemId, String pServiceId, String effectiveInd) {
		try {
			return this.offerProfileLtsDao.getRecurringCharges(pSystemId, pServiceId, effectiveInd);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getRecurringCharges()", de);
			throw new AppRuntimeException(de);
		}		
	}
	
	public  List<OfferDetailDiscountDTO> getDiscount(String pSystemId, String pServiceId, String effectiveInd) {
		try {
			return this.offerProfileLtsDao.getDiscount(pSystemId, pServiceId, effectiveInd);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getDiscount()", de);
			throw new AppRuntimeException(de);
		}		
	}
	
	public  List<OfferDetailIncentiveDTO> getIncentive(String pSystemId, String pServiceId, String effectiveInd) {
		try {
			return this.offerProfileLtsDao.getIncentive(pSystemId, pServiceId, effectiveInd);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getIncentive()", de);
			throw new AppRuntimeException(de);
		}		
	}
	
	public  List<BomAttbDTO> getBomAttbByOfferId(String offerId, String appDate) {
		try {
			return this.offerProfileLtsDao.getBomAttb(offerId, null, StringUtils.defaultIfEmpty(appDate, DateFormatHelper.getSysDate("yyyyMMdd")));
		} catch (DAOException de) {
			logger.error("Fail in getBomAttb", de);
			throw new AppRuntimeException(de);
		}		
	}
	
	
	public  List<BomAttbDTO> getBomAttb(String offerCd, String appDate) {
		try {
			return this.offerProfileLtsDao.getBomAttb(null, offerCd, StringUtils.defaultIfEmpty(appDate, DateFormatHelper.getSysDate("yyyyMMdd")));
		} catch (DAOException de) {
			logger.error("Fail in getBomAttb", de);
			throw new AppRuntimeException(de);
		}		
	}
}
