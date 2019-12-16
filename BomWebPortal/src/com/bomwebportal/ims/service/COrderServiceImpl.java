package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.COrderDAO;
import com.bomwebportal.ims.dto.GetCOrderDTO;
//steven created this java file, plz find steven if u find anything wrong
public class COrderServiceImpl implements COrderService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private COrderDAO cOrderDAO;

	public GetCOrderDTO getCOrder(String sub_pcd, String sub_nowtv, String tech,
			String custNum, String sb, String unit, String floor) {
		try {
			return this.cOrderDAO.getCOrder(sub_pcd,sub_nowtv,tech,custNum,sb,unit,floor);
		} catch (DAOException de) {
			logger.error("Fail in SbFlatFloorServiceImpl.getSB", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	// martin
	public List<GetCOrderDTO> getAvailFSAforNowTV(String sb, String unit, String floor, String custNum) {
		List<GetCOrderDTO> cList = null;
		try {
			cList = this.cOrderDAO.getAvailFSAforNowTV(sb, unit, floor, custNum);
		} catch (DAOException de) {
			logger.info("Failed in getListCOrder@COrderServiceImpl");
			de.printStackTrace();
		}
		if (cList != null && cList.size() > 0) {
			return cList;
		} else {
			return null;
		}
	}
	
	public String checkMultipleFSAUnderAcct(String acctNo){
		String result = "N";
		try {
			result = this.cOrderDAO.checkMultipleFSAUnderAcct(acctNo);
		} catch (DAOException de) {
			logger.info("Failed in checkMultipleFSAUnderAcct@COrderServiceImpl");
			de.printStackTrace();
		}
		return result;
	}
	
	public String getNAcctByFSA(String fsa){
		String result = null;
		try {
			result = this.cOrderDAO.getNAcctByFSA(fsa);
		} catch (DAOException de) {
			logger.info("Failed in getNAcctByFSA@COrderServiceImpl");
			de.printStackTrace();
		}
		return result;
	}

	public void setcOrderDAO(COrderDAO cOrderDAO) {
		this.cOrderDAO = cOrderDAO;
	}

	public COrderDAO getcOrderDAO() {
		return cOrderDAO;
	}

}
