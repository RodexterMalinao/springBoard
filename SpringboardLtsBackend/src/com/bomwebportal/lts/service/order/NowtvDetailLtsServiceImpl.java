package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.NowtvDetailLtsDAO;
import com.bomwebportal.lts.dto.order.NowtvDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class NowtvDetailLtsServiceImpl extends ServiceActionImplBase {

	public NowtvDetailLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		NowtvDetailLtsDTO nowtvDtl = new NowtvDetailLtsDTO();
		NowtvDetailLtsDAO nowtvDtlDao = (NowtvDetailLtsDAO)pDaoBase;
		nowtvDtl.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, nowtvDtl);
		nowtvDtl.setDob(DateFormatHelper.dateConvertFromDAO2DTO(nowtvDtlDao.getDob()));
		return nowtvDtl;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		NowtvDetailLtsDTO nowTvDtl = (NowtvDetailLtsDTO)pBaseDTO;
		NowtvDetailLtsDAO nowTvDao = (NowtvDetailLtsDAO)this.dao;
		this.DTO2DAO(nowTvDtl, nowTvDao);
		nowTvDao.setOrderId((String)args[0]);
		nowTvDao.setDtlId((String)args[1]);
		nowTvDao.setDob(DateFormatHelper.dateConvertFromDTO2DAO(nowTvDtl.getDob()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((NowtvDetailLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
