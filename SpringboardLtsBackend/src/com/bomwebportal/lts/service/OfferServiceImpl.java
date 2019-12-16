package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OfferDetailDAO;
import com.bomwebportal.lts.dao.OfferDetailProfileDAO;
import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemAttbInfoDTO;

public class OfferServiceImpl extends OfferProfileServiceImpl implements OfferService {

	protected OfferDetailDAO offerDetailDao = null;
	
	
	public String getServicePlanCode(String itemId) {
		try {
			return this.offerDetailDao.getServicePlanCode(itemId);
		} catch (DAOException de) {
			logger.error("Fail in getServicePlanCode()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String getVasPlanCode(String itemId) {
		try {
			return this.offerDetailDao.getVasPlanCode(itemId);
		} catch (DAOException de) {
			logger.error("Fail in getVasPlanCode()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String[] getItemOfferCodes(String itemId) {
		try {
			return this.offerDetailDao.getItemOfferCodes(itemId).toArray(new String[0]);
		} catch (DAOException de) {
			logger.error("Fail in getItemOfferCodes()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}	

	public String[] getItemPsefCodes(String itemId) {
		try {
			return this.offerDetailDao.getItemPsefCodes(itemId).toArray(new String[0]);
		} catch (DAOException de) {
			logger.error("Fail in getItemPsefCodes()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}	
	
	public ItemAttbDTO[] getItemAttb(String pItemId) {
		List<ItemAttbDTO> attbList = new ArrayList<ItemAttbDTO>();
		try {
			attbList = this.offerDetailDao.getItemAttb(pItemId);
			
			if (attbList != null && !attbList.isEmpty()) {
				for (ItemAttbDTO itemAttb : attbList) {
					if (StringUtils.isEmpty(itemAttb.getAttbInfoKey())) {
						continue;
					}
					List<ItemAttbInfoDTO> itemAttbInfoList = this.offerDetailDao
							.getItemAttbInfoList(itemAttb.getAttbInfoKey());
					itemAttb.setItemAttbInfoList(itemAttbInfoList);
				}
			}

			return attbList.toArray(new ItemAttbDTO[0]);
		} catch (DAOException de) {
			logger.error("Fail in OfferService.getItemAttb()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public String getItemContractPeriod(String pItemId) {
		
		try {
			return this.offerDetailDao.getItemContractPeriod(pItemId);
		} catch (DAOException de) {
			logger.error("Fail in OfferService.getItemContractPeriod()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public String getChannelDescription(String pChannelId, String pLocale) {
		
		try {
			return this.offerDetailDao.getChannelDescription(pChannelId, pLocale);
		} catch (DAOException de) {
			logger.error("Fail in OfferService.getChannelDescription()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public ChannelAttbDTO[][] getChannelAttbDisplay(String pChannelId, String pLocale) {
		
		List<ChannelAttbDTO> channelAttbList = this.getChannelAttb(pChannelId, pLocale);
		Map<String,List<ChannelAttbDTO>> attbMap = new HashMap<String,List<ChannelAttbDTO>>();
		List<ChannelAttbDTO> subAttbList = null;
		ChannelAttbDTO attb = null;
		String seq = null;		
		
		for (int i=0; channelAttbList!=null && i<channelAttbList.size(); ++i) {
			attb = channelAttbList.get(i);
			seq = String.valueOf(attb.getDisplaySeq());

			if (attbMap.containsKey(seq)) {
				subAttbList = attbMap.get(seq);
			} else {
				subAttbList = new ArrayList<ChannelAttbDTO>();
				attbMap.put(seq, subAttbList);
			}
			subAttbList.add(attb);
		}
		String[] seqKeys = attbMap.keySet().toArray(new String[attbMap.size()]);
		Arrays.sort(seqKeys);
		ChannelAttbDTO[][] attbDisplays = new ChannelAttbDTO[seqKeys.length][];
		
		for (int i=0; i<seqKeys.length; ++i) {
			subAttbList = attbMap.get(seqKeys[i]);
			attbDisplays[i] = subAttbList.toArray(new ChannelAttbDTO[subAttbList.size()]);
		}
		return attbDisplays;
	}
	
	protected List<ChannelAttbDTO> getChannelAttb(String pChannelId, String pLocale) {
		try{
			return this.offerDetailDao.getChannelAttb(pChannelId, pLocale);
		} catch (DAOException de) {
			logger.error("Fail in OfferService.getChannelAttb()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public ItemAttbDTO[] getItemAttbByItemAttbAssign(String pItemId) {
		List<ItemAttbDTO> attbList = new ArrayList<ItemAttbDTO>();
		try {
			attbList = this.offerDetailDao.getItemAttbByItemAttbAssign(pItemId);
			
			if (attbList != null && !attbList.isEmpty()) {
				for (ItemAttbDTO itemAttb : attbList) {
					if (StringUtils.isEmpty(itemAttb.getAttbInfoKey())) {
						continue;
					}
					List<ItemAttbInfoDTO> itemAttbInfoList = this.offerDetailDao
							.getItemAttbInfoList(itemAttb.getAttbInfoKey());
					itemAttb.setItemAttbInfoList(itemAttbInfoList);
				}
			}

			return attbList.toArray(new ItemAttbDTO[0]);
		} catch (DAOException de) {
			logger.error("Fail in OfferService.getItemAttbByItemAttbAssign()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String getItemDisplayDesc(String itemId, String locale){
		try {
			return this.offerDetailDao.getItemDisplayDesc(itemId, locale);
		} catch (DAOException de) {
			logger.error("Fail in OfferService.getItemDisplayDesc()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public void setOfferDetailProfileDao(OfferDetailProfileDAO offerDetailProfileDao) {
		super.setOfferDetailProfileDao(offerDetailProfileDao);
		this.offerDetailDao = (OfferDetailDAO)offerDetailProfileDao;
	}
	
}
