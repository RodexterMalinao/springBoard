package com.bomwebportal.lts.service.activity;

import com.activity.service.dataAccess.RetrieveActivityService;
import com.bomwebportal.lts.dto.activity.LtsActivityDTO;
import com.bomwebportal.lts.dto.activity.PIPBActivityDTO;

public interface RetrieveLtsActivityService extends RetrieveActivityService {

	public abstract LtsActivityDTO retrieveLtsActivityByAssgnId(String pWpWqAssgnId, String pUser);
	
	public abstract LtsActivityDTO retrievePendingLtsActivityByOrderIdActvType(String pOrderId, String pUser, String pActvType);
	
	public abstract LtsActivityDTO retrievePendingLtsActivityByOrderId(String pOrderId, String pUser);
	
	public abstract PIPBActivityDTO retrievePendingPipbActivityByOrderId(String pOrderId, String pUser);
	
	public abstract String[] getRelatedAssgnIdFromSearchSourceByAssgnId(String pWqWpAssgnId, String pUser);
	
}