package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ChannelLtsDAO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ChannelLtsServiceImpl extends ServiceActionImplBase {
	
	public ChannelLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "channelId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		ChannelDetailLtsDTO channel = new ChannelDetailLtsDTO();
		channel.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, channel);
		return channel;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ChannelDetailLtsDTO channelDtl = (ChannelDetailLtsDTO)pBaseDTO;
		ChannelLtsDAO channeldao = (ChannelLtsDAO)this.dao;
		this.DTO2DAO(channelDtl, channeldao);
		channeldao.setOrderId((String)args[0]);
		channeldao.setDtlId((String)args[1]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ChannelLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
