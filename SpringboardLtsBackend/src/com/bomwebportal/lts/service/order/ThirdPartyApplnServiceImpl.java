package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ThirdPartyApplnDAO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class ThirdPartyApplnServiceImpl extends ServiceActionImplBase {

	public ThirdPartyApplnServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ThirdPartyDetailLtsDTO thirdPartyDtl = new ThirdPartyDetailLtsDTO();
		ThirdPartyApplnDAO thirdPartyDao = (ThirdPartyApplnDAO)pDaoBase;
		thirdPartyDtl.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, thirdPartyDtl);
		thirdPartyDtl.setDob(DateFormatHelper.dateConvertFromDAO2DTO(thirdPartyDao.getDob()));
		return thirdPartyDtl;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ThirdPartyDetailLtsDTO thirdPartyDtl = (ThirdPartyDetailLtsDTO)pBaseDTO;
		ThirdPartyApplnDAO thirdPartyDtlDao = (ThirdPartyApplnDAO)this.dao;
		DTO2DAO(thirdPartyDtl, thirdPartyDtlDao);
		thirdPartyDtlDao.setOrderId((String)args[0]);
		thirdPartyDtlDao.setDtlId((String)args[1]);
		thirdPartyDtlDao.setDob(DateFormatHelper.dateConvertFromDTO2DAO(thirdPartyDtlDao.getDob()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ThirdPartyApplnDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
