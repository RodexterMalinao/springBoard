package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.CCSalesInfoDAO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;

public class CCSalesInfoServiceImpl implements CCSalesInfoService {
	public CCSalesInfoDAO dao;
	protected final Log logger = LogFactory.getLog(getClass());
	
	public CCSalesInfoDAO getDao() {
		return dao;
	}

	public void setDao(CCSalesInfoDAO dao) {
		this.dao = dao;
	}

	public List<String> getCCManagerTeamCds(String staffId) //throws DAOException 
	{
		try
		{
			return dao.getCCManagerTeamCds(staffId);
		}
		catch (DAOException de)
		{
			logger.info("Exception caught in getCCManagerTeamCds()");
			de.printStackTrace();

		}
		return null;
		
	}
	
	public ImsServiceSrdDTO getPTServiceByStaffId (String staffId, String housingType, String PONOTAmt, String PONOTChrgType)  
	{
		try
		{
			return dao.getPTServiceByStaffId(staffId, housingType, PONOTAmt, PONOTChrgType);
		}
		catch (DAOException de)
		{
			logger.info("Exception caught in getPTServiceByStaffId()");
			de.printStackTrace();

		}
		return null;
		
	}
	
	public String getAllowMassprojByStaff (String staffId, String housingType)  
	{
		try
		{
			return dao.getAllowMassprojByStaff(staffId, housingType);
		}
		catch (DAOException de)
		{
			logger.info("Exception caught in getPTServiceByStaffId()");
			de.printStackTrace();

		}
		return null;
		
	}
}
