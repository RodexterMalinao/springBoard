/*
 * Created on Dec 02, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.service;

import java.util.Date;

import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileServiceResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileSubscribedPlanResponse;


public interface LtsViQuadplayService {

	public SBGetProfileServiceResponse getProfileService(String pFsa) throws Exception;
	public SBGetProfileSubscribedPlanResponse getProfileSubscirbedPlan(String pFsa) throws Exception;
	public float getARPU(String pFsa) throws Exception;
	public void setDateOfBirth(String pFsa, Date pDOB, String pModifiedBy) throws Exception;
	public void setDateOfBirth(String pFsa, String pDOByyyyMMdd, String pModifiedBy) throws Exception;
	public boolean isTVBSubscriber(String pFsa) throws Exception;
	public void setAdultContentReceivable(String pFsa, boolean pReceiveAdultContent, String pModifiedBy) throws Exception;

}
