package com.bomwebportal.mob.ccs.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsSupportDocDAO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;

@Transactional(readOnly = true)
public class MobCcsSupportDocServiceImpl implements MobCcsSupportDocService {
	protected final Log logger = LogFactory.getLog(getClass());

	private MobCcsSupportDocDAO mobCcsSupportDocDAO;

	public MobCcsSupportDocDAO getMobCcsSupportDocDAO() {
		return mobCcsSupportDocDAO;
	}

	public void setMobCcsSupportDocDAO(MobCcsSupportDocDAO mobCcsSupportDocDAO) {
		this.mobCcsSupportDocDAO = mobCcsSupportDocDAO;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateMobCcsSupportDocUI(MobCcsSupportDocUI dto) {
		try {
			logger.info("updateMobCcsSupportDocUI() is called in MobCcsSupportDocServiceImpl");
			return mobCcsSupportDocDAO.updateMobCcsSupportDocUI(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateMobCcsSupportDocUI()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertMobCcsSupportDocUI(MobCcsSupportDocUI dto) {
		try {
			logger.info("insertMobCcsSupportDocUI() is called in MobCcsSupportDocServiceImpl");
			return mobCcsSupportDocDAO.insertMobCcsSupportDocUI(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertMobCcsSupportDocUI()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOInitialList(
			String locale) {
		logger.info("getMobCcsSupportDocDTOInitialList is called");
		List<MobCcsSupportDocDTO> mobCcsSupportDocDTOInitialList = new ArrayList<MobCcsSupportDocDTO>();

		try {
			mobCcsSupportDocDTOInitialList = mobCcsSupportDocDAO
					.getMobCcsSupportDocDTOInitialList(locale);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}

		logger.debug("output count=" + mobCcsSupportDocDTOInitialList.size());
		return mobCcsSupportDocDTOInitialList;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOAdditionalList(
			List<MobCcsSupportDocDTO> initialList, String orderId, String locale) {
		logger.info("getMobCcsSupportDocDTOAdditionalList is called");

		try {
			return mobCcsSupportDocDAO.getMobCcsSupportDocDTOAdditionalList(
					initialList, orderId, locale);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public MobCcsSupportDocUI getMobCcsSupportDocUI(String orderId,
			String locale) {
		logger.info("getMobCcsSupportDocUI is called");

		try {
			return mobCcsSupportDocDAO.getMobCcsSupportDocUI(orderId, locale);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOForCourierGuid(String orderId){
	    
	    logger.info("getMobCcsSupportDocDTOForCourierGuid is called");

		try {
			return mobCcsSupportDocDAO.getMobCcsSupportDocDTOForCourierGuid(orderId);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}

	}

	public boolean isSupportDocRequired(String docType, String basketID,
			String[] vasItemList, String channel) {
		try {
			return mobCcsSupportDocDAO.isSupportDocRequired(docType, basketID, vasItemList, channel);
		} catch (DAOException e) {
			logger.error("Exception caught in isSupportDocRequired()", e);
			return false;
		}
		
	}

	/*public List<MobCcsSupportDocDTO> compareMobCcsSupportDoc(List<MobCcsSupportDocDTO> historyList,
			List<MobCcsSupportDocDTO> verifiedList) {
		List<MobCcsSupportDocDTO> combinedList = new ArrayList<MobCcsSupportDocDTO>();
		boolean removable;
		if (historyList.size() >= verifiedList.size()) {
			for (MobCcsSupportDocDTO his : historyList) {
				removable = false;

				MobCcsSupportDocDTO cmb = his;
				for (MobCcsSupportDocDTO ver : verifiedList) {
					if (cmb.getDocId() == ver.getDocId()) {
						if (cmb.isMandatory() && !ver.isMandatory()) {
							cmb.setMandatory(ver.isMandatory());
							cmb.setVerified(ver.isVerified());
							removable = true;
						}
						break;
					}
				}
				cmb.setRemovable(removable);
				combinedList.add(cmb);
			}
		} else {
			for (MobCcsSupportDocDTO ver : verifiedList) {
				removable = false;
				
				MobCcsSupportDocDTO cmb = ver;
				for (MobCcsSupportDocDTO his : historyList){
					if(cmb.getDocId() == his.getDocId()){
						cmb = his;
						if (cmb.isMandatory() && !ver.isMandatory()){
							cmb.setMandatory(ver.isMandatory());
							cmb.setVerified(ver.isVerified());
							removable = true;
						}
					}
					break;
				}
				cmb.setRemovable(removable);
				combinedList.add(cmb);

			}
		}
		
		return combinedList;

	}*/

}
