package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.StaffInfoLtsDAO;
import com.bomwebportal.lts.dto.order.StaffInfoLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class StaffInfoServiceImpl extends ServiceActionImplBase {

	public StaffInfoServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		StaffInfoLtsDTO staff = new StaffInfoLtsDTO();
		StaffInfoLtsDAO staffdao = (StaffInfoLtsDAO)pDaoBase;
		staff.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, staff);
		staff.setCallDate(DateFormatHelper.dateConvertFromDAO2DTO(staffdao.getCallDate()));
		return staff;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		StaffInfoLtsDTO staff = (StaffInfoLtsDTO)pBaseDTO;
		StaffInfoLtsDAO staffdao = (StaffInfoLtsDAO)this.dao;
		this.DTO2DAO(staff, staffdao);
		staffdao.setOrderId((String)args[0]);
		staffdao.setCallDate(DateFormatHelper.dateConvertFromDTO2DAO(staff.getCallDate()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((StaffInfoLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
