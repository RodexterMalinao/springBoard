package com.bomwebportal.lts.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.BasketDetailDAO;
import com.bomwebportal.lts.dto.BasketCriteriaDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ret.RenewBasketPolicyDTO;
import com.google.common.collect.Lists;

public class LtsBasketServiceImpl implements LtsBasketService {

	protected BasketDetailDAO basketDetailDao;
	
	public BasketDetailDAO getBasketDetailDao() {
		return basketDetailDao;
	}

	public void setBasketDetailDao(BasketDetailDAO basketDetailDao) {
		this.basketDetailDao = basketDetailDao;
	}

	public List<String> getEligiblePsefList()  {
		try {
			List<String> eligiblePsefList = basketDetailDao.getEligiblePsefList();
			if (eligiblePsefList == null || eligiblePsefList.isEmpty()) {
				return null;
			}
			Set<String> eligiblePsefSet = new HashSet<String>();
			for (String eligiblePsef : eligiblePsefList) {
				String[] eligiblePsefs = StringUtils.split(eligiblePsef, "||");
				if (ArrayUtils.isNotEmpty(eligiblePsefs)) {
					for (int i=0; i<eligiblePsefs.length; i++){
						eligiblePsefSet.add(eligiblePsefs[i].trim());	
					}
				}
			}
			return Lists.newArrayList(eligiblePsefSet);
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public boolean isPcdSbidValid(String PcdSbid) {
		try {
			String countNo = basketDetailDao.checkSbpcdOrderNotCancNorComp(PcdSbid);
			boolean isValid = false;
			if(countNo != null)
			{
				isValid = !countNo.equalsIgnoreCase("") && !countNo.equalsIgnoreCase("0");
			}
			return isValid;
			// non-cancelled and non-completed 
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public boolean isAllowArpuChange(String toProd,  String existArpu, String newArpu) {
		try {
			String allowArpuChange = basketDetailDao.getAllowArpuChange(toProd, existArpu, newArpu);
			return  Integer.valueOf(StringUtils.defaultIfEmpty(allowArpuChange, "0")) >= 1;
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<RenewBasketPolicyDTO> getRenewBasketPolicyList(String basketChannelId, String existTpCatg, String remainContractMth) {
		try {
			return basketDetailDao.getRenewBasketPolicyList(basketChannelId, existTpCatg, null, remainContractMth);
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<RenewBasketPolicyDTO> getRenewBasketPolicyList(String basketChannelId, String existTpCatg, String newBasketCatg, String remainContractMth) {
		try {
			return basketDetailDao.getRenewBasketPolicyList(basketChannelId, existTpCatg, newBasketCatg, remainContractMth);
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<BasketDetailDTO> getAcqBasketList(String basketType,
			String paralleExtension, String locale, String deviceType,
			String basketCategory, String teamCode, String basketChannelId,  String[] bridgingMth,
			boolean contractPeriodGt24, boolean contractPeriodEq18,
			boolean contractPeriodLt12, boolean staffPlan, String housingType, boolean twoNCoverage,
			String[] filterProjectCds, String[] defaultProjectCds, String hktPremier, String pSrvBoundary, 
			String pAreaCode, String pRole, String pOsType, String ltsHousingType) {
		try {
			
			
			List<BasketDetailDTO> basketList = basketDetailDao.getAcqBasketList(basketType,
					paralleExtension, locale, deviceType, basketCategory, teamCode, basketChannelId, bridgingMth,
					contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, staffPlan, housingType, twoNCoverage, filterProjectCds, defaultProjectCds, hktPremier, pSrvBoundary, pAreaCode, pRole, pOsType, ltsHousingType);
			// For PT channel, get the Premier IND
			if (basketList != null && !basketList.isEmpty()
					&& StringUtils.startsWith(basketChannelId, "4")) {
				for (BasketDetailDTO basketDetail : basketList) {
					basketDetail.setPremierInd(basketDetailDao.getBasketPremierInd(basketDetail.getBasketId()));
				}
			}
			return basketList;
			
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional(readOnly=false)
	public List<BasketDetailDTO> getRetBasketList(BasketCriteriaDTO basketCriteria){

		try {
			List<BasketDetailDTO> basketList = basketDetailDao.getRetBasketList(basketCriteria); 
			
			// For PT channel, get the Premier IND
			if (basketList != null && !basketList.isEmpty()
					&& StringUtils.startsWith(basketCriteria.getBasketChannelId(), "4")) {
				for (BasketDetailDTO basketDetail : basketList) {
					basketDetail.setPremierInd(basketDetailDao.getBasketPremierInd(basketDetail.getBasketId()));
				}
			}
			return basketList;
			
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	

	@Transactional(readOnly=false)
	public List<BasketDetailDTO> getStandaloneVasDummyBasketList(BasketCriteriaDTO basketCriteria){

		try {
			return basketDetailDao.getStandaloneVasDummyBasket(basketCriteria);
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	@Transactional(readOnly=false)
	public List<BasketDetailDTO> getBasketList(BasketCriteriaDTO basketCriteria) {

		try {
			List<BasketDetailDTO> basketList =  basketDetailDao.getBasketList(basketCriteria);
			// For PT channel, get the Premier IND
			if (basketList != null && !basketList.isEmpty()
					&& StringUtils.startsWith(basketCriteria.getBasketChannelId(), "4")) {
				for (BasketDetailDTO basketDetail : basketList) {
					basketDetail.setPremierInd(basketDetailDao.getBasketPremierInd(basketDetail.getBasketId()));
				}
			}
			return basketList;
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	@Transactional(readOnly=false)
	public BasketDetailDTO getBasket(String basketId, String locale, String displayType) {
		
		try{
			return basketDetailDao.getBasket(basketId, locale, displayType);
		}catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	@Transactional(readOnly=false)
	public String getMaxIddFreeMin(String basketChannelId, String basketType,
			String iddFreeMin, boolean parallelExtension, String locale, String contractPeriod, boolean isNonNowTvCustOnly, String applicationDate) {
		
		try{
			return basketDetailDao.getMaxIddFreeMin(basketChannelId, basketType, iddFreeMin, parallelExtension, locale, contractPeriod, isNonNowTvCustOnly, applicationDate);
		}catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String getBasketContractPeriod(String pBasketId) {
		
		try {
			return basketDetailDao.getBasketContractPeriod(pBasketId);
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<LookupItemDTO> getProjectCdList(String channelCd, String teamCd, String orderType) {
		try {
			return basketDetailDao.getProjectCdList(channelCd, teamCd, orderType);
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
}
