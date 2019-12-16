package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.util.LtsActvBackendConstant.ActvAction;

public interface PipbActvLtsService {

	public abstract void submitPipbActivity(SbOrderDTO pSbOrder, ServiceDetailDTO pSrvDtl, String pUser, String pShopCd, boolean isStatusChange, boolean isSrdChange, String pTargetBomStatus);	
	
	public abstract void updatePipbActivityStatus(String pSbOrderId, String pUser, String pStatusCd, String pAction); 
	
	public abstract void updatePipbActivity(SbOrderDTO pSbOrder, String pUser, String pShopCd, String pAction, String pStatusCd);

	public abstract void insertDummyRecordToBInvPreassgn(SbOrderDTO pSbOrder);
}