package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.AppointmentLtsDAO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class AppointmentLtsServiceImpl extends ServiceActionImplBase {

	public AppointmentLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		AppointmentDetailLtsDTO appointment = new AppointmentDetailLtsDTO();
		AppointmentLtsDAO appointmentDao = (AppointmentLtsDAO)pDaoBase;
		appointment.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(appointmentDao, appointment);
		appointment.setAppntStartTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getAppntStartTime()));
		appointment.setAppntEndTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getAppntEndTime()));
		appointment.setCutOverStartTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getCutOverStartTime()));
		appointment.setCutOverEndTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getCutOverEndTime()));
		appointment.setExactAppntTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getExactAppntTime()));
		appointment.setPreWiringStartTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getPreWiringStartTime()));
		appointment.setPreWiringEndTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getPreWiringEndTime()));
		appointment.setTidStartTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getTidStartTime()));
		appointment.setTidEndTime(DateFormatHelper.dateConvertFromDAO2DTO(appointmentDao.getTidEndTime()));
		return appointment;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {

		AppointmentDetailLtsDTO appntDtl = (AppointmentDetailLtsDTO)pBaseDTO;
		AppointmentLtsDAO appntDao = (AppointmentLtsDAO)this.dao;
		this.DTO2DAO(appntDtl, appntDao);
		appntDao.setOrderId((String)args[0]);
		appntDao.setDtlId((String)args[1]);
		appntDao.setAppntStartTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getAppntStartTime()));
		appntDao.setAppntEndTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getAppntEndTime()));
		appntDao.setCutOverStartTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getCutOverStartTime()));
		appntDao.setCutOverEndTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getCutOverEndTime()));
		appntDao.setExactAppntTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getExactAppntTime()));
		appntDao.setPreWiringStartTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getPreWiringStartTime()));
		appntDao.setPreWiringEndTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getPreWiringEndTime()));
		appntDao.setTidStartTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getTidStartTime()));
		appntDao.setTidEndTime(DateFormatHelper.dateConvertFromDTO2DAO(appntDtl.getTidEndTime()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((AppointmentLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
