package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ChannelAttbLtsDAO;
import com.bomwebportal.lts.dto.order.ChannelAttbLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ChannelAttbLtsServiceImpl extends ServiceActionImplBase {

	public ChannelAttbLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "attbId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ChannelAttbLtsDTO channelAttb = new ChannelAttbLtsDTO();
		channelAttb.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, channelAttb);
		return channelAttb;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ChannelAttbLtsDTO channelAttb = (ChannelAttbLtsDTO)pBaseDTO;
		ChannelAttbLtsDAO channelAttbDao = (ChannelAttbLtsDAO)this.dao;
		this.DTO2DAO(channelAttb, channelAttbDao);
		channelAttbDao.setOrderId((String)args[0]);
		channelAttbDao.setDtlId((String)args[1]);
		channelAttbDao.setChannelId((String)args[2]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ChannelAttbLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
