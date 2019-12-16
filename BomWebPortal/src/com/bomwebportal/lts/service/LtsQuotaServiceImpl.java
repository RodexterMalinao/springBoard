package com.bomwebportal.lts.service;

import java.util.List;

import org.apache.commons.collections.ListUtils;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsQuotaDAO;
import com.bomwebportal.lts.dto.LtsQuotaDTO;

public class LtsQuotaServiceImpl implements LtsQuotaService {

	private LtsQuotaDAO ltsQuotaDao;
	
	public LtsQuotaDTO getQuota(String programCd, String psef) {
		try {
			List<LtsQuotaDTO> quotaList = ltsQuotaDao.getQuota(programCd, psef);
			if(quotaList != null && quotaList.size() > 0){
				return quotaList.get(0);
			}
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
		return null;
	}

	public List<LtsQuotaDTO> getQuotaListByPsef(String psef) {
		try {
			return ltsQuotaDao.getQuota(null, psef);
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public void increaseQuotaUsed(LtsQuotaDTO quota) {
		try {
			if(ltsQuotaDao.hasQuotaUsed(quota.getProgramCd()))
			{
				 ltsQuotaDao.updateQuotaUsed(quota.getProgramCd(),quota.getCurrentQuotaUsed());
			}
			else
			{
				ltsQuotaDao.insertQuotaUsed(quota.getProgramCd(),quota.getCurrentQuotaUsed());
			}
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public void increaseQuotaUsed(String programCd) {
		try {
			if(ltsQuotaDao.hasQuotaUsed(programCd))
			{
				 ltsQuotaDao.updateQuotaUsed(programCd);
			}
			else
			{
				ltsQuotaDao.insertQuotaUsed(programCd);
			}
		} catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public LtsQuotaDAO getLtsQuotaDao() {
		return ltsQuotaDao;
	}

	public void setLtsQuotaDao(LtsQuotaDAO ltsQuotaDao) {
		this.ltsQuotaDao = ltsQuotaDao;
	}

}
