package com.bomltsportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OfferDetailDAO;
import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ChannelIconDTO;
import com.bomwebportal.lts.dto.ExclusiveChannelDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;

public class NowTvChannelServiceImpl implements NowTvChannelService {
	
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected ItemDetailService itemDetailService;
	protected OfferDetailDAO offerDetailDao;

	public OfferDetailDAO getOfferDetailDao() {
		return offerDetailDao;
	}

	public void setOfferDetailDao(OfferDetailDAO offerDetailDao) {
		this.offerDetailDao = offerDetailDao;
	}

	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}

	public List<ExclusiveChannelDetailDTO> getExclusiveChannelList(
			List<ChannelGroupDetailDTO> channelGroupList, String locale) {
		try {
			return this.offerDetailDao.getExclusiveChannelList(channelGroupList, locale);
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public ValidationResultDTO validateExclusiveChannel(
			List<ChannelGroupDetailDTO> channelGroupList, String locale) {

		List<ExclusiveChannelDetailDTO> exclusiveChannelList = getExclusiveChannelList(channelGroupList, locale);

		if (exclusiveChannelList == null || exclusiveChannelList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}

		List<String> errorMsgList = new ArrayList<String>();

		for (ExclusiveChannelDetailDTO exclusiveChannel : exclusiveChannelList) {
			errorMsgList.add(" Cannot select ("
					+ exclusiveChannel.getChannelACd() + ") "
					+ exclusiveChannel.getChannelADesc() + " and ("
					+ exclusiveChannel.getChannelBCd() + ") "
					+ exclusiveChannel.getChannelBDesc() + " together.");
		}

		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}

		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);

	}
	
	public String getChannelDescription(String pChannelId, String pLocale) {
		
		try {
			return this.offerDetailDao.getChannelDescription(pChannelId, pLocale);
		} catch (DAOException de) {
			logger.error("Fail in LtsdfferService.getChannelDescription()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	
	public List<ChannelGroupDetailDTO> getChannelGroupList(String itemType,
			String deviceType, String pLocale, boolean selectDefault, String credit) {
	    
		String formType = null;
		
		try {
			
			if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, itemType)) {
				formType = BomLtsConstant.NOWTV_FORM_TYPE_LIST_A;
			}
			else if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, itemType)) {
				formType = BomLtsConstant.NOWTV_FORM_TYPE_LIST_B;
			}
			else {
				return null;
			}
			
			List<ChannelGroupDetailDTO> channelGroupList = this.offerDetailDao.getChannelGroupList(formType, deviceType, pLocale);
			
			if (channelGroupList == null || channelGroupList.isEmpty()) {
				return null;
			}
		
			List<ChannelDetailDTO> channelList = null;
		    List<ChannelAttbDTO> channelAttbList = null;
		    List<ChannelIconDTO> channelIconList = null;
			
		    List<ChannelGroupDetailDTO> newChannelGroupList = new ArrayList<ChannelGroupDetailDTO>();
		    
			for (ChannelGroupDetailDTO channelGroup : channelGroupList) {
				channelList = getChannelList(channelGroup.getChannelGroupId(), pLocale, selectDefault, credit);
				if (channelList == null || channelList.isEmpty()) {
					continue;
				}
				for (ChannelDetailDTO channel : channelList) {
					channelAttbList = getChannelAttbList(channel.getChannelId(), pLocale);
					if (channelAttbList != null && !channelAttbList.isEmpty()) {
						channel.setChannelAttbs(channelAttbList.toArray(new ChannelAttbDTO[channelAttbList.size()]));	
					}
					
					 channelIconList = getChannelIconList(channel.getChannelId(), pLocale);
					 if (channelIconList != null && !channelIconList.isEmpty()) {
						 channel.setChannelIcons(channelIconList.toArray(new ChannelIconDTO[channelIconList.size()]));
					 }
				}
				channelGroup.setChannelDetails(channelList.toArray(new ChannelDetailDTO[channelList.size()]));
				newChannelGroupList.add(channelGroup);
			}
			
			return newChannelGroupList;
		
		} catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	
	private List<ChannelAttbDTO> getChannelAttbList(String channelId, String locale) {
		try {
			return this.offerDetailDao.getChannelAttb(channelId, locale);
		} catch (DAOException de) {
			logger.error("Fail in getChannelIconList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	} 
	
	private List<ChannelDetailDTO> getChannelList(String channelGroupId, String locale, boolean selectDefault, String credit) {
		try {
			return this.offerDetailDao.getGroupChannel(channelGroupId, locale, selectDefault, credit);
		} catch (DAOException de) {
			logger.error("Fail in getChannelList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	private List<ChannelIconDTO> getChannelIconList(String channelId, String locale) {
		try {
			return this.offerDetailDao.getChannelIcon(channelId, locale);
		} catch (DAOException de) {
			logger.error("Fail in getChannelIconList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	
}
