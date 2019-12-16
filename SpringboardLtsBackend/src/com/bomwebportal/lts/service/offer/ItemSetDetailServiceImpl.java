package com.bomwebportal.lts.service.offer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.offer.ItemSetDetailDAO;
import com.google.common.collect.Lists;

public class ItemSetDetailServiceImpl implements ItemSetDetailService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ItemSetDetailDAO itemSetDetailDao;
	
	public List<String> getItemSetEligiblePsefList() {
		try{
			List<String> eligiblePsefList = itemSetDetailDao.getItemSetEligiblePsefList();
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
			
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}

	public ItemSetDetailDAO getItemSetDetailDao() {
		return itemSetDetailDao;
	}

	public void setItemSetDetailDao(ItemSetDetailDAO itemSetDetailDao) {
		this.itemSetDetailDao = itemSetDetailDao;
	}
	
}
