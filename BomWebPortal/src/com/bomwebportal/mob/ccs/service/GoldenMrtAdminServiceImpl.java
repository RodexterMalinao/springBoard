package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.GoldenMrtAdminDAO;
import com.bomwebportal.mob.ccs.dto.GoldenMrtAdminDTO;

@Transactional(readOnly = true)
public class GoldenMrtAdminServiceImpl implements GoldenMrtAdminService {

    protected final Log logger = LogFactory.getLog(getClass());

    private GoldenMrtAdminDAO goldenMrtAdminDao;

    public GoldenMrtAdminDAO getGoldenMrtAdminDao() {
	return goldenMrtAdminDao;
    }

    public void setGoldenMrtAdminDao(GoldenMrtAdminDAO goldenMrtAdminDao) {
	this.goldenMrtAdminDao = goldenMrtAdminDao;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public GoldenMrtAdminDTO getGoldenMrtAdminDTO(String rowId) {
	try {
	    logger.info("getGoldenMrtAdminDTO() is called in GoldenMrtAdminServiceImpl");
	    return goldenMrtAdminDao.getGoldenMrtAdminDTO(rowId);

	} catch (DAOException de) {
	    logger.error("Exception caught in getGoldenMrtAdminDTO()", de);
	    throw new AppRuntimeException(de);
	}
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<GoldenMrtAdminDTO> getGoldenMrtAdminDTOALL(String orderId) {
	try {
	    logger.info("getGoldenMrtAdminDTOALL() is called in GoldenMrtAdminServiceImpl");
	    return goldenMrtAdminDao.getGoldenMrtAdminDTOALL(orderId);

	} catch (DAOException de) {
	    logger.error("Exception caught in getGoldenMrtAdminDTOALL()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public GoldenMrtAdminDTO getGoldenMrtAdminDTOByOrderId(String orderId) {
	try {
	    logger.info("getGoldenMrtAdminDTOByOrderId() is called in GoldenMrtAdminServiceImpl");
	    return goldenMrtAdminDao.getGoldenMrtAdminDTOByOrderId(orderId);

	} catch (DAOException de) {
	    logger.error("Exception caught in getGoldenMrtAdminDTOByOrderId()", de);
	    throw new AppRuntimeException(de);
	}
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public int insertGoldenMrtAdmin(GoldenMrtAdminDTO dto) {
	try {
	    logger.info("insertGoldenMrtAdmin() is called in GoldenMrtAdminServiceImpl");
	    return goldenMrtAdminDao.insertGoldenMrtAdmin(dto);

	} catch (DAOException de) {
	    logger.error("Exception caught in insertGoldenMrtAdmin()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public int updateGoldenMrtAdmin(GoldenMrtAdminDTO dto) {
	try {
	    logger.info("updateGoldenMrtAdmin() is called in GoldenMrtAdminServiceImpl");
	    return goldenMrtAdminDao.updateGoldenMrtAdmin(dto);

	} catch (DAOException de) {
	    logger.error("Exception caught in updateGoldenMrtAdmin()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public int updateGoldenMrtAdminMsisdnStatus(String orderId, String status) {
	try {
	    logger.info("updateGoldenMrtAdminMsisdnStatus() is called in GoldenMrtAdminServiceImpl");
	    return goldenMrtAdminDao.updateGoldenMrtAdminMsisdnStatus(orderId, status);

	} catch (DAOException de) {
	    logger.error("Exception caught in updateGoldenMrtAdminMsisdnStatus()", de);
	    throw new AppRuntimeException(de);
	}
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public int deleteGoldenMrtAdmin(GoldenMrtAdminDTO dto) {
	try {
	    logger.info("deleteGoldenMrtAdmin() is called in GoldenMrtAdminServiceImpl");
	    return goldenMrtAdminDao.deleteGoldenMrtAdmin(dto);

	} catch (DAOException de) {
	    logger.error("Exception caught in deleteGoldenMrtAdmin()", de);
	    throw new AppRuntimeException(de);
	}
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<GoldenMrtAdminDTO> getGoldenMrtAdminDTOByDate(Date startDate, Date endDate) {
    	try {
    	    logger.info("getGoldenMrtAdminDTOByDate() is called in GoldenMrtAdminServiceImpl");
    	    return goldenMrtAdminDao.getGoldenMrtAdminDTOByDate(startDate, endDate);

    	} catch (DAOException de) {
    	    logger.error("Exception caught in getGoldenMrtAdminDTOByDate()", de);
    	    throw new AppRuntimeException(de);
    	}
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public int updateGoldenMrtAdminForAdmin(GoldenMrtAdminDTO dto) {
    	try {
    	    logger.info("updateGoldenMrtAdminForAdmin() is called in GoldenMrtAdminServiceImpl");
    	    return goldenMrtAdminDao.updateGoldenMrtAdminForAdmin(dto);

    	} catch (DAOException de) {
    	    logger.error("Exception caught in updateGoldenMrtAdminForAdmin()", de);
    	    throw new AppRuntimeException(de);
    	}
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public int updateGoldenMrtAdminForManager(GoldenMrtAdminDTO dto) {
    	try {
    	    logger.info("updateGoldenMrtAdminForManager() is called in GoldenMrtAdminServiceImpl");
    	    return goldenMrtAdminDao.updateGoldenMrtAdminForManager(dto);

    	} catch (DAOException de) {
    	    logger.error("Exception caught in updateGoldenMrtAdminForManager()", de);
    	    throw new AppRuntimeException(de);
    	}
    }
    
//    public void insertGoldenMrtAdmin(CustomerProfileDTO customerInfoDto, StaffInfoUI sessionStaffInfo,
//	    				MRTUI mrtUI, BasketDTO basketDto, String orderId) {
//	
//	GoldenMrtAdminDTO goldenMrtDto = new GoldenMrtAdminDTO();
//	goldenMrtDto.setTitle(customerInfoDto.getTitle());     	    
//	goldenMrtDto.setSalesName(sessionStaffInfo.getSalesName());
//	goldenMrtDto.setRowId("");
//	goldenMrtDto.setRemarks(mrtUI.getGoldenRemarks());
//	goldenMrtDto.setReserveId("");
//	goldenMrtDto.setRequestDate(Utility.string2Date(mrtUI.getGoldenServiceReqDateStr()));
//	goldenMrtDto.setLastUpdDate(new Date());
//	goldenMrtDto.setCreatedDate(new Date());
//	goldenMrtDto.setOrderId(orderId);
//	goldenMrtDto.setMsisdnlvl("");
//	goldenMrtDto.setMsisdn("");
//	goldenMrtDto.setMonthlyCharge(basketDto.getRecurrentAmt());
//	goldenMrtDto.setLastUpdBy(sessionStaffInfo.getSalesId());
//	goldenMrtDto.setLastName(customerInfoDto.getCustLastName());
//	goldenMrtDto.setCreatedBy(sessionStaffInfo.getSalesId());
//	goldenMrtDto.setContractPeriod(basketDto.getContractPeriod());
//	goldenMrtDto.setApprovedDate(null);
//	goldenMrtDto.setApprovedBy("");
//	goldenMrtDto.setGoldenSuffix("**" + mrtUI.getGoldenSuffix());
//	goldenMrtDto.setFirstName(customerInfoDto.getCustFirstName());
//	goldenMrtDto.setTitle(customerInfoDto.getTitle());
//	goldenMrtDto.setSalesName(sessionStaffInfo.getSalesName());
//	    
//	insertGoldenMrtAdmin(goldenMrtDto);
//	//1 = request
//	updateGoldenMrtAdminMsisdnStatus(orderId, BomWebPortalCcsConstant.MRT_GOLDEN_NUM_REQUEST);
//    }
//    
//    public void updateGoldenMrtAdmin(CustomerProfileDTO customerInfoDto, StaffInfoUI sessionStaffInfo,
//		MRTUI mrtUI, BasketDTO basketDto, String orderId){
//	
//	GoldenMrtAdminDTO goldenMrtDto = getGoldenMrtAdminDTOByOrderId(orderId);
//	goldenMrtDto.setTitle(customerInfoDto.getTitle());
//	goldenMrtDto.setSalesName(sessionStaffInfo.getSalesName());
//	goldenMrtDto.setRemarks(mrtUI.getGoldenRemarks());
//	goldenMrtDto.setReserveId(mrtUI.getGoldenReserveId());
//	goldenMrtDto.setOrderId(orderId);
//	goldenMrtDto.setLastUpdDate(new Date());
//	goldenMrtDto.setMonthlyCharge(basketDto.getRecurrentAmt());
//	goldenMrtDto.setLastUpdBy(sessionStaffInfo.getSalesId());
//	goldenMrtDto.setLastName(customerInfoDto.getCustLastName());
//	goldenMrtDto.setContractPeriod(basketDto.getContractPeriod());
//	goldenMrtDto.setGoldenSuffix("**" + mrtUI.getGoldenSuffix());
//	goldenMrtDto.setFirstName(customerInfoDto.getCustFirstName());
//	goldenMrtDto.setTitle(customerInfoDto.getTitle());
//	goldenMrtDto.setSalesName(sessionStaffInfo.getSalesName());
//	updateGoldenMrtAdmin(goldenMrtDto);	
//    }
}
