package com.bomwebportal.mob.ccs.service;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MrtInventoryDAO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.MrtStatusDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService.MsisdnStatus;
import com.bomwebportal.util.Utility;

@Transactional(readOnly=true)
public class MrtInventoryServiceImpl implements MrtInventoryService{
    
    protected final Log logger = LogFactory.getLog(getClass());

    private MobCcsMaintParmLkupService mobCcsMaintParmLkupService;
    private MobCcsMrtStatusService mobCcsMrtStatusService;
    private MrtInventoryDAO mrtInventoryDao;
    
    public MobCcsMaintParmLkupService getMobCcsMaintParmLkupService() {
		return mobCcsMaintParmLkupService;
	}

	public void setMobCcsMaintParmLkupService(
			MobCcsMaintParmLkupService mobCcsMaintParmLkupService) {
		this.mobCcsMaintParmLkupService = mobCcsMaintParmLkupService;
	}

	public MobCcsMrtStatusService getMobCcsMrtStatusService() {
		return mobCcsMrtStatusService;
	}

	public void setMobCcsMrtStatusService(
			MobCcsMrtStatusService mobCcsMrtStatusService) {
		this.mobCcsMrtStatusService = mobCcsMrtStatusService;
	}

	public MrtInventoryDAO getMrtInventoryDao() {
        return mrtInventoryDao;
    }

    public void setMrtInventoryDao(MrtInventoryDAO mrtInventoryDao) {
        this.mrtInventoryDao = mrtInventoryDao;
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public MrtInventoryDTO getMrtInventoryDTO(String rowId){
		try {
			logger.info("getMrtInventoryDTO() is called in MrtInventoryServiceImpl");
			return mrtInventoryDao.getMrtInventoryDTO(rowId);
	
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtInventoryDTO()", de);
			throw new AppRuntimeException(de);
		}
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public MrtInventoryDTO getMrtInventoryDTOByOrderId(String orderId, List<String> channelCds){
		try {
			logger.info("getMrtInventoryDTOByOrderId() is called in MrtInventoryServiceImpl");
			return mrtInventoryDao.getMrtInventoryDTOByOrderId(orderId, channelCds);
	
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtInventoryDTOByOrderId()", de);
			throw new AppRuntimeException(de);
		}
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public List<MrtInventoryDTO> getMrtInventoryDTOALL(String msisdn){
    	return this.getMrtInventoryDTOALL(msisdn, null);
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public List<MrtInventoryDTO> getMrtInventoryDTOALL(String msisdn, List<String> channelCds){
		try {
			logger.info("getMrtInventoryDTOALL() is called in MrtInventoryServiceImpl");
			return mrtInventoryDao.getMrtInventoryDTOALL(msisdn, channelCds);
	
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtInventoryDTOALL()", de);
			throw new AppRuntimeException(de);
		}
    }
    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<MrtInventoryDTO> getMrtInventoryDTOBySearch(MrtInventoryDTO dto, List<String> channelCds) {
    	try {
    		logger.info("getMrtInventoryDTOBySearch() is called in MrtInventoryServiceImpl");
    		return mrtInventoryDao.getMrtInventoryDTOBySearch(dto, channelCds);

    	} catch (DAOException de) {
    		logger.error("Exception caught in getMrtInventoryDTOBySearch()", de);
    		throw new AppRuntimeException(de);
    	}
	}

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public int insertMrtInventory(MrtInventoryDTO dto){
	try {
		logger.info("insertMrtInventory() is called in MrtInventoryServiceImpl");
		return mrtInventoryDao.insertMrtInventory(dto);

	} catch (DAOException de) {
		logger.error("Exception caught in insertMrtInventory()", de);
		throw new AppRuntimeException(de);
	}
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public int updateMrtInventory(MrtInventoryDTO dto){
		logger.info("updateMrtInventory() is called in MrtInventoryServiceImpl");
		try {
			List<MrtStatusDTO> msisdnRecords = this.mobCcsMrtStatusService.getMrtStatusDTOByMsisdnAndStatus(dto.getMsisdn(), MsisdnStatus.RESERVE);
			if (!this.isEmpty(msisdnRecords)) {
				// we expect no reserve record
				if (logger.isInfoEnabled()) {
					logger.info("mrtStatus record exists: " + dto.getMsisdn() + " in " + MsisdnStatus.RESERVE);
				}
				return -1;
			}
			return mrtInventoryDao.updateMrtInventory(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateMrtInventory()", de);
			throw new AppRuntimeException(de);
		}
    }
    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public int deleteMrtInventory(MrtInventoryDTO dto){
	try {
		logger.info("deleteMrtInventory() is called in MrtInventoryServiceImpl");
		return mrtInventoryDao.deleteMrtInventory(dto);

	} catch (DAOException de) {
		logger.error("Exception caught in deleteMrtInventory()", de);
		throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public int updateMrtInventoryStatus(MrtInventoryDTO dto){
	try {
		logger.info("updateMrtInventoryStatus() is called in MrtInventoryServiceImpl");
		return mrtInventoryDao.updateMrtInventoryStatus(dto);

	} catch (DAOException de) {
		logger.error("Exception caught in updateMrtInventoryStatus()", de);
		throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
    public String getStsFmMRT(String msisdn) {
    	try {
    		logger.info("getStsFmMRT() is called in MrtInventoryServiceImpl");
    		return mrtInventoryDao.getStsFmMRT(msisdn);

    	} catch (DAOException de) {
    		logger.error("Exception caught in getStsFmMRT()", de);
    		throw new AppRuntimeException(de);
    	}
    }
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public List<MaintParmLkupDTO> getMaintParmValue(String channelCd, FunctionCd functionCd, ParmType parmType) {
    	logger.info("getMaintParmValue() is called in MrtInventoryServiceImpl");
    	return this.mobCcsMaintParmLkupService.getMaintParmLkupDTO(channelCd, "MOB", functionCd.getValue(), parmType.toString());
	}
	
	public List<MaintParmLkupDTO> getParmControl(String channelCd, String controlType, FunctionCd functionCd, FunctionCd controlFunctionCd, ParmType parmType) {
    	logger.info("getParmControl() is called in MrtInventoryServiceImpl");
    	try {
			return mrtInventoryDao.getParmControl(channelCd, controlType, functionCd.getValue(), controlFunctionCd.getValue(), parmType.toString());
		} catch (DAOException de) {
			logger.error("Exception caught in getParmControl()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public JSONObject getMrtControlJson(String channelCd) {
		logger.info("getMrtControlJson() is called in MrtInventoryServiceImpl");
		JSONObject mrtControl = new JSONObject();
		List<MaintParmLkupDTO> numTypeList = this.getMaintParmValue(channelCd, FunctionCd.MRT_INVENTORY, ParmType.NUM_TYPE);
		for (MaintParmLkupDTO numType : numTypeList) {
			JSONObject numJson = new JSONObject();
			
			List<MaintParmLkupDTO> serviceTypeList = this.getParmControl(channelCd, numType.getParmValue(), FunctionCd.MRT_INVENTORY, FunctionCd.MRT_CONTROL, ParmType.SERVICE_TYPE);
			numJson.put("serviceType", this.toMaintParmLkupArray(serviceTypeList));
			
			for (MaintParmLkupDTO serviceType : serviceTypeList) {
				JSONObject serviceTypeJson = new JSONObject();
				
				List<MaintParmLkupDTO> cityList = this.getMaintParmValue(serviceType.getParmValue(), FunctionCd.MRT_CONTROL, ParmType.CITY);
				serviceTypeJson.put("city", this.toMaintParmLkupArray(cityList));
				
				/*List<MaintParmLkupDTO> gradeList = this.getParmControl(channelCd, serviceType.getParmValue(), FunctionCd.MRT_INVENTORY, FunctionCd.MRT_CONTROL, ParmType.GRADE);
				serviceTypeJson.put("grade", this.toMaintParmLkupArray(gradeList));*/
				
				numJson.put(serviceType.getParmValue(), serviceTypeJson);
			}
			
			mrtControl.put(numType.getParmValue(), numJson);
		}
		
		System.out.println(Utility.toPrettyJson(mrtControl));
		return mrtControl;
	}
	
	private String[] toMaintParmLkupArray(List<MaintParmLkupDTO> parmList) {
		if (CollectionUtils.isEmpty(parmList)) {
			return null;
		}
		
		String[] array = new String[parmList.size()];
		for (int i = 0; i < parmList.size(); i++) {
			array[i] = parmList.get(i).getParmValue();
		}
		
		return array;
	}
}
