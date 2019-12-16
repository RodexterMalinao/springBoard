package com.bomwebportal.service;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DataOutdatedException;
import com.pccw.util.db.DaoBase;

public abstract class ServiceActionImplBase implements ServiceActionBase {

	protected Mapper dozerMapper;
	protected final Log logger = LogFactory.getLog(getClass());
	protected DaoBase dao;
	
	protected ArrayList<String> selectConditionFieldList = null;
	protected ArrayList<String> updateDeleteConditionFieldList = null;
	protected String selectOrderBy = null;
	
	protected abstract void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args);
	protected abstract void setDAO2DTODetails(Object... args);
	public abstract ObjectActionBaseDTO convertToDto(DaoBase pDaoBase);
		
	
	public void doSave(ObjectActionBaseDTO pBaseDTO, String pUser, Object... args) {
				
		if (pBaseDTO.getObjectAction() == ObjectActionBaseDTO.ACTION_ADD) {
			pBaseDTO.setCreateBy(pUser);
			pBaseDTO.setLastUpdBy(pUser);
			this.setDTO2DAODetails(pBaseDTO, args);
			this.doInsert();
		} else if (pBaseDTO.getObjectAction() == ObjectActionBaseDTO.ACTION_UPDATED) {
			pBaseDTO.setLastUpdBy(pUser);
			this.setDTO2DAODetails(pBaseDTO, args);
			this.doUpdate();
		} else if (pBaseDTO.getObjectAction() == ObjectActionBaseDTO.ACTION_DELETE) {
			this.setDTO2DAODetails(pBaseDTO, args);
			this.doDelete();
		}
	}
	
	public DaoBase[] doRetrieve(Object... args) {
		
		this.setDAO2DTODetails(args);

		try {
			 return this.dao.doSelect(selectConditionFieldList, null, null, selectOrderBy);
		} catch (Exception ex) {
			logger.error("Fail in ServiceLtsBase.doSelect()", ex);
			throw new AppRuntimeException("Fail in ServiceLtsBase.doSelect()", ex);
		}
	}
	
	private void doInsert() {

		try {
			this.dao.doInsert();
		} catch (Exception ex) {
			logger.error("Fail in ServiceLtsBase.doInsert()", ex);
			
			if (this.dao != null) {
				logger.error(this.dao.toString());
			}
			throw new AppRuntimeException("Fail in ServiceLtsBase.doInsert()", ex);
		}
	}

	private void doUpdate() {
		
		boolean updateCondition = true;
		
		try {
			updateCondition = this.dao.doUpdate(updateDeleteConditionFieldList);
		} catch (Exception ex) {
			logger.error("Fail in ServiceLtsBase.doUpdate()", ex);
			
			if (this.dao != null) {
				logger.error(this.dao.toString());
			}
			throw new AppRuntimeException("Fail in ServiceLtsBase.doUpdate()", ex);
		}
		if (!updateCondition) {
			logger.error("Fail in ServiceLtsBase.doUpdate()");
			logger.error(this.dao.getErrMsg());
			throw new DataOutdatedException(this.dao.getClass().getName() + " record outdated.");
		}
	}

	private void doDelete() {

		try {
			this.dao.doDelete(updateDeleteConditionFieldList);
		} catch (Exception ex) {
			logger.error("Fail in ServiceLtsBase.doDelete()", ex);
			
			if (this.dao != null) {
				logger.error(this.dao.toString());
			}
			throw new AppRuntimeException("Fail in ServiceLtsBase.doDelete()", ex);
		}
	}
	
	public ObjectActionBaseDTO convertToDto(ObjectActionBaseDTO pObject, DaoBase pDaoBase) {
		this.DAO2DTO(pDaoBase, pObject);
		return pObject;
	}
	
	protected void DTO2DAO(ObjectActionBaseDTO pDTO, DaoBase pDAO) {
		BeanUtils.copyProperties(pDTO, pDAO);
		this.dozerMapper.map(pDTO, pDAO);
	}
	
	protected void DAO2DTO(DaoBase pDAO, Object pDTO) {
		BeanUtils.copyProperties(pDAO, pDTO);
		this.dozerMapper.map(pDAO, pDTO);
	}

	public Mapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(Mapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}
	
	public DaoBase getDao() {
		return this.dao;
	}

	public void setDao(DaoBase pDao) {
		this.dao = pDao;
	}
}
