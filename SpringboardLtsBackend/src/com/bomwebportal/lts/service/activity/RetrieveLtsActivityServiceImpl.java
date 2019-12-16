package com.bomwebportal.lts.service.activity;

import org.apache.commons.lang.ArrayUtils;

import com.activity.dto.ActivityDTO;
import com.activity.service.ActivityDetailService;
import com.activity.service.dataAccess.RetrieveActivityServiceImpl;
import com.bomwebportal.lts.dto.activity.LtsActivityDTO;
import com.bomwebportal.lts.dto.activity.PIPBActivityDTO;
import com.bomwebportal.lts.util.LtsActvBackendConstant;

public class RetrieveLtsActivityServiceImpl extends RetrieveActivityServiceImpl implements RetrieveLtsActivityService {

	private ActivityDetailService activityDetailService = null;
	
	@Override
	protected ActivityDTO getActivity(String pActvId)
    {
        return getActivity(new LtsActivityDTO(null), pActvId);
    }
	
	public LtsActivityDTO retrieveLtsActivityByAssgnId(String pWpWqAssgnId, String pUser) {
		
		this.user = pUser;
		String[] actvId = this.activityDetailService.getActvByWqWpAssgnId(pWpWqAssgnId);
		LtsActivityDTO ltsActv = null;
		
		if (ArrayUtils.isEmpty(actvId)) {
			return null;
		} else {
			ltsActv = (LtsActivityDTO)this.getActivity(actvId[0]);
		}
		return ltsActv;
	}

	
	public LtsActivityDTO retrievePendingLtsActivityByOrderIdActvType(String pOrderId, String pUser, String pActvType) {
		
		this.user = pUser;
		String[] actvId = this.activityDetailService.getPendingActvByOrderId(pOrderId, pActvType);
		LtsActivityDTO ltsActv = null;
		
		if (ArrayUtils.isEmpty(actvId)) {
			ltsActv = new LtsActivityDTO(pActvType);
	
		} else {
			ltsActv = (LtsActivityDTO)this.getActivity(actvId[0]);
		}
		return ltsActv;
	}
	
	public LtsActivityDTO retrievePendingLtsActivityByOrderId(String pOrderId, String pUser) {
		
		this.user = pUser;
		String[] actvId = this.activityDetailService.getPendingActvByOrderId(pOrderId, LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC);
		LtsActivityDTO ltsActv = null;
		
		if (ArrayUtils.isEmpty(actvId)) {
			ltsActv = new LtsActivityDTO(LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC);
	
		} else {
			ltsActv = (LtsActivityDTO)this.getActivity(actvId[0]);
		}
		return ltsActv;
	}
	
	public PIPBActivityDTO retrievePendingPipbActivityByOrderId(String pOrderId, String pUser) {
		
		this.user = pUser;
		String[] actvId = this.activityDetailService.getPendingActvByOrderId(pOrderId, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ);
		PIPBActivityDTO pipbActv = null;
		
		if (ArrayUtils.isEmpty(actvId)) {
			pipbActv = new PIPBActivityDTO(LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ);
	
		} else {
			pipbActv = (PIPBActivityDTO)this.getActivity(actvId[0]);
		}
		return pipbActv;
	}	
	
	public String[] getRelatedAssgnIdFromSearchSourceByAssgnId(String pWqWpAssgnId, String pUser) {
		this.user = pUser;
		return this.activityDetailService.getRelatedAssgnIdFromSearchSourceByAssgnId(pWqWpAssgnId);
	}
	
	public ActivityDetailService getActivityDetailService() {
		return activityDetailService;
	}

	public void setActivityDetailService(ActivityDetailService activityDetailService) {
		this.activityDetailService = activityDetailService;
	}
}
