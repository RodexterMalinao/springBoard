package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ItemLtsDAO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class ItemLtsServiceImpl extends ServiceActionImplBase {

	public ItemLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "srvItemSeq"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		ItemDetailLtsDTO item = new ItemDetailLtsDTO();
		ItemLtsDAO itemDao = (ItemLtsDAO)pDaoBase;
		item.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(itemDao, item);
		item.setExistStartDate(DateFormatHelper.dateConvertFromDAO2DTO(itemDao.getExistStartDate()));
		item.setExistEndDate(DateFormatHelper.dateConvertFromDAO2DTO(itemDao.getExistEndDate()));
		return item;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ItemDetailLtsDTO item = (ItemDetailLtsDTO)pBaseDTO;
		ItemLtsDAO itemDao = (ItemLtsDAO)this.dao;
		this.DTO2DAO(item, itemDao);
		itemDao.setOrderId((String)args[0]);
		itemDao.setDtlId((String)args[1]);
		itemDao.setExistStartDate(DateFormatHelper.dateConvertFromDTO2DAO(item.getExistStartDate()));
		itemDao.setExistEndDate(DateFormatHelper.dateConvertFromDTO2DAO(item.getExistEndDate()));
		
		if (StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_MANUAL_WAIVE, item.getPenaltyHandling())
				|| StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_AUTO_WAIVE, item.getPenaltyHandling())) {
			itemDao.setPenaltyWaiveInd("Y");
		} else {
			itemDao.setPenaltyWaiveInd(null);
		}
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ItemLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
