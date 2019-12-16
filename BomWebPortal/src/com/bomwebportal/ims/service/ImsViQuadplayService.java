package com.bomwebportal.ims.service;

import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileServiceResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileSubscribedPlanResponse;

public interface ImsViQuadplayService {
	public SBGetProfileServiceResponse getProfileService(String pFsa) throws Exception ;
	public SBGetProfileSubscribedPlanResponse getProfileSubscirbedPlan(String pFsa) throws Exception ;
}
