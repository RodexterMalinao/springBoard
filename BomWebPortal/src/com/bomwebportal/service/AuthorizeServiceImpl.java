package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.AuthorizeDAO;
import com.bomwebportal.dto.AuthorizeDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.dao.bom.BomSalesInfoDAO;

public class AuthorizeServiceImpl implements AuthorizeService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private AuthorizeDAO authorizeDAO;
	private BomSalesInfoDAO bomSalesInfoDAO;
	
	public AuthorizeDAO getAuthorizeDAO() {
		return authorizeDAO;
	}

	public void setAuthorizeDAO(AuthorizeDAO authorizeDAO) {
		this.authorizeDAO = authorizeDAO;
	}
	
	
	
	public BomSalesInfoDAO getBomSalesInfoDAO() {
		return bomSalesInfoDAO;
	}

	public void setBomSalesInfoDAO(BomSalesInfoDAO bomSalesInfoDAO) {
		this.bomSalesInfoDAO = bomSalesInfoDAO;
	}

	public Map<String, List<AuthorizeDTO>> getAuthorizeList(String staffId, String category, String channelId) {
		
		List<AuthorizeDTO> results = null;
		
		try{
			results = authorizeDAO.getRoleLkup(staffId, category, channelId);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
		
		return getAuthoMap(results);
	}
	
	private Map<String, List<AuthorizeDTO>> getAuthoMap(List<AuthorizeDTO> authList) {
		
		Map<String, List<AuthorizeDTO>> attrKey = new HashMap<String, List<AuthorizeDTO>>();
		
		for (AuthorizeDTO dto : authList) {
			List<AuthorizeDTO> aList = new ArrayList<AuthorizeDTO>();
			if (!attrKey.containsKey(dto.getHtml())) {
				aList.add(dto);
				attrKey.put(dto.getHtml(), aList);
			} else {
				aList = attrKey.get(dto.getHtml());
				aList.add(dto);
			}
		}
		
		return attrKey;
	}
	
	public String getBomSalesRoleDTOByID(String staffId) {
		try {
			logger.info("getBomSalesRoleDTOByID() is called @ AuthorizeService");
			return bomSalesInfoDAO.getBomSalesRoleDTOByID(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomSalesRoleDTOByID()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getAuthorizeRight(String authorizedAction,String channelId, String sbCategory ,String bomCategory) {
		try {
			logger.info("getAuthorizeRight() is called @ AuthorizeService");
			return authorizeDAO.getAuthorizeRight(authorizedAction,channelId,sbCategory,bomCategory);
		} catch (DAOException de) {
			logger.error("Exception caught in getAuthorizeRight()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getAuthorizeCategoryText(String authorizedAction, String channelId){
		try {
			logger.info("getAuthorizeCategoryText() is called @ AuthorizeService");
			return authorizeDAO.getAuthorizeCategoryText(authorizedAction, channelId);
		} catch (DAOException de) {
			logger.error("Exception caught in getAuthorizeCategoryText()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public List<SalesInfoDTO> getSalesInfoDTOByID(String staffId) {
		try {
			logger.info("getSalesInfoDTOByID() is called @ AuthorizeService");
			return authorizeDAO.getSalesInfoDTOByID(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in getSalesInfoDTOByID()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int updateAdditionalApprovalLog(String authorizedId, String orderId, String orderNature, String rpGrossPlanFee, String basketId, double minVas) {
		try {
			logger.info("updateAdditionalApprovalLog() is called @ ApprovalLogService");
			return authorizeDAO.updateAdditionalApprovalLog(authorizedId, orderId, orderNature, rpGrossPlanFee, basketId, minVas);
		} catch (DAOException de) {
			logger.error("Exception caught in updateAdditionalApprovalLog()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int updateApprovalLogOrderId(String authorizedId, String orderId) {
		try {
			logger.info("updateApprovalLogOrderId() is called @ ApprovalLogService");
			return authorizeDAO.updateApprovalLogOrderId(authorizedId, orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in updateApprovalLogOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
}
