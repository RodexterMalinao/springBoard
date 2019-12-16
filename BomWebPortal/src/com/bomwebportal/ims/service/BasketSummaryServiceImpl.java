package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.BasketSummaryDAO;
import com.bomwebportal.ims.dto.ui.BasketSumUI;
import com.bomwebportal.ims.dto.ui.ChannelSumUI;
import com.bomwebportal.ims.dto.ui.ImsSummaryUI;

@Transactional(readOnly=true)
public class BasketSummaryServiceImpl implements BasketSummaryService{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private BasketSummaryDAO basketSummaryDao;

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsSummaryUI> basketTitleDetail(String basketId, String locale){
		logger.info("basketTitleDetail is called");
		
		try{			
			return basketSummaryDao.basketTitleDetail(basketId, locale);
		}catch (DAOException de){
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<BasketSumUI> getBasketSumList(String itemId, String locale){
		logger.info("getBasketSumList is called");
		
		try{			
			return basketSummaryDao.getBasketSumList(itemId, locale);
		}catch (DAOException de){
			throw new AppRuntimeException(de);
		}
	}
		
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ChannelSumUI> getChannelSumList(String channelId, String locale){
		logger.info("getChannelSumList is called");
		
		try{			
			return basketSummaryDao.getChannelSumList(channelId, locale);
		}catch (DAOException de){
			throw new AppRuntimeException(de);
		}
	}

	public BasketSummaryDAO getBasketSummaryDao() {
		return basketSummaryDao;
	}

	public void setBasketSummaryDao(BasketSummaryDAO basketSummaryDao) {
		this.basketSummaryDao = basketSummaryDao;
	}
	
}
