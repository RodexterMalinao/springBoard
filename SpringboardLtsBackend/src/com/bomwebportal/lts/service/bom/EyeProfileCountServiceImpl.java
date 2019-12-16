package com.bomwebportal.lts.service.bom;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.EyeProfileCountDAO;
import com.bomwebportal.lts.util.LtsSbHelper;

public class EyeProfileCountServiceImpl implements EyeProfileCountService {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private EyeProfileCountDAO eyeProfileCountDAO;

	public int getEyeProfileCountByCust(String pDocType, String pDocNum) {
		try {
			return eyeProfileCountDAO.getEyeProfileCountByCust(pDocType, pDocNum);
		} catch (DAOException de) {
			logger.error("Fail in EyeProfileCountService.getEyeProfileCountByCust: " +
					pDocType + " " + pDocNum, de);
			throw new AppRuntimeException(de);
		}
	}

	public int getEyeProfileCountByAddr(String pFlat, String pFloor, String pSrvBdry, boolean isCheckPattern) {
		try {

			//Normal Case
			int totalCount = 0;
			
			if(isCheckPattern){
				List<String[]> patternList = LtsSbHelper.getAddrCombinationPattern(pFlat, pFloor);
				for(String [] pattern: patternList){
					totalCount += eyeProfileCountDAO.getEyeProfileCountByAddr(pattern[0], pattern[1], pSrvBdry);
				}
			}
			else {
				totalCount = eyeProfileCountDAO.getEyeProfileCountByAddr(pFlat, pFloor, pSrvBdry);	
			}
			
			return totalCount;
			
		} catch (DAOException de) {
			logger.error("Fail in EyeProfileCountService.getEyeProfileCountByAddr: " +
					pFlat + " " + pFloor + " " + pSrvBdry , de);
			throw new AppRuntimeException(de);
		}
	}

	public EyeProfileCountDAO getEyeProfileCountDAO() {
		return eyeProfileCountDAO;
	}

	public void setEyeProfileCountDAO(EyeProfileCountDAO eyeProfileCountDAO) {
		this.eyeProfileCountDAO = eyeProfileCountDAO;
	}

}
