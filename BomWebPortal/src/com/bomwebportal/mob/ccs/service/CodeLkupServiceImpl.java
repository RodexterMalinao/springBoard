package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.CodeLkupDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;

@Transactional(readOnly = true)
public class CodeLkupServiceImpl implements CodeLkupService {

	protected final Log logger = LogFactory.getLog(getClass());

	private CodeLkupDAO codeLkupDao;

	public CodeLkupDAO getCodeLkupDao() {
		return codeLkupDao;
	}

	public void setCodeLkupDao(CodeLkupDAO codeLkupDao) {
		this.codeLkupDao = codeLkupDao;
	}

	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public CodeLkupDTO getCodeLkupDTO(String codeId) {
		try {
			logger.info("getCodeLkupDTO() is called in CodeLkupServiceImpl");
			return codeLkupDao.getCodeLkupDTO(codeId);

		} catch (DAOException de) {
			logger.error("Exception caught in getCodeLkupDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}*/

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<CodeLkupDTO> getCodeLkupDTOALL(String codeType) {
		try {
			logger.info("getCodeLkupDTOALL() is called in CodeLkupServiceImpl");
			return codeLkupDao.getCodeLkupDTOALL(codeType);

		} catch (DAOException de) {
			logger.error("Exception caught in getCodeLkupDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<CodeLkupDTO> getCodeLkupDTOALLDesc(String codeType) {
		try {
			logger.info("getCodeLkupDTOALL() is called in CodeLkupServiceImpl");
			return codeLkupDao.getCodeLkupDTOALLDesc(codeType);

		} catch (DAOException de) {
			logger.error("Exception caught in getCodeLkupDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public String getCodeDesc(String codeType, String codeId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodeDesc() is called in CodeLkupServiceImpl");
		}
		try {
			return codeLkupDao.getCodeDesc(codeType, codeId);
		} catch (DAOException de) {
			logger.error("Exception caught in getCodeDesc()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertCodeLkup(CodeLkupDTO dto) {
		try {
			logger.info("insertCodeLkup() is called in CodeLkupServiceImpl");
			return codeLkupDao.insertCodeLkup(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in insertCodeLkup()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateCodeLkup(CodeLkupDTO dto) {
		try {
			logger.info("updateCodeLkup() is called in CodeLkupServiceImpl");
			return codeLkupDao.updateCodeLkup(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in updateCodeLkup()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int deleteCodeLkup(CodeLkupDTO dto) {
		try {
			logger.info("deleteCodeLkup() is called in CodeLkupServiceImpl");
			return codeLkupDao.deleteCodeLkup(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in deleteCodeLkup()", de);
			throw new AppRuntimeException(de);
		}
	}*/
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getCodeTypeById(List<String> codeTypes, String codeId) {
		try {
			logger.info("getCodeTypeById() is called in CodeLkupServiceImpl");
			return codeLkupDao.getCodeTypeById(codeTypes,codeId);

		} catch (DAOException de) {
			logger.error("Exception caught in getCodeTypeById()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public  List<CodeLkupDTO> getPayMethodCodeLkup() {
		try {
			logger.info("getPayMethodCodeLkup() is called in CodeLkupServiceImpl");
			return codeLkupDao.getPayMethodCodeLkup();

		} catch (DAOException de) {
			logger.error("Exception caught in getPayMethodCodeLkup()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<CodeLkupDTO> findActiveReasonCodeLkupByType(String type,
			Date appDate) {
		logger.info("findActiveReasonCodeLkupByType is called in CodeLkupServiceImpl");
		try {
			return codeLkupDao.findActiveReasonCodeLkupByType(type, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in getPayMethodCodeLkup()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
	public Map<String,String> findReasonCodeTypeAsMap(String type, Date appDate) {
		logger.debug("findCodeTypeAsMap called");
		HashMap<String,String> codeMap = new HashMap<String,String>();
		try {
			List<CodeLkupDTO> codeList = codeLkupDao.findActiveReasonCodeLkupByType(type, appDate);
			if (CollectionUtils.isNotEmpty(codeList)) {
				for (CodeLkupDTO cl : codeList) {
					codeMap.put(cl.getCodeId(), cl.getCodeDesc());
				}
			}
			return codeMap;
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public Map<String,String> findAttbTypeAsMap( String locale) {
		logger.debug("findCodeTypeAsMap called");
		HashMap<String,String> codeMap = new HashMap<String,String>();
		try {
			
			List<CodeLkupDTO> codeList = codeLkupDao.findAttbLkup(locale );
			if (CollectionUtils.isNotEmpty(codeList)) {
				for (CodeLkupDTO cl : codeList) {
					codeMap.put(cl.getCodeId(), cl.getCodeDesc());
				}
			}
			List<CodeLkupDTO> codeList2 = codeLkupDao.findAttbInfoLkup(locale );
			if (CollectionUtils.isNotEmpty(codeList2)) {
				for (CodeLkupDTO cl : codeList2) {
					codeMap.put(cl.getCodeId(), cl.getCodeDesc());
				}
			}
			return codeMap;
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public List<CodeLkupDTO> findCodeLkupByType(String type) {
		logger.debug("findCodeLkupByType called");
		try {
			return codeLkupDao.findCodeLkupByType(type);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
}
