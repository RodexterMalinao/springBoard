package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.RemarkLtsDAO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class RemarkLtsServiceImpl extends ServiceActionImplBase {

	public RemarkLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "rmkSeq", "rmkType"));
		this.selectOrderBy = "ORDER_ID, DTL_ID, RMK_TYPE, RMK_SEQ";
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		RemarkDetailLtsDTO remark = new RemarkDetailLtsDTO();
		remark.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, remark);
		return remark;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		RemarkDetailLtsDTO remark = (RemarkDetailLtsDTO)pBaseDTO;
		RemarkLtsDAO remarkDao = (RemarkLtsDAO)this.dao;
		this.DTO2DAO(remark, remarkDao);
		remarkDao.setOrderId((String)args[0]);
		remarkDao.setDtlId((String)args[1]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((RemarkLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
