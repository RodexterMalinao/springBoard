/*
 * Created on Dec 02, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.bomwebportal.lts.wsClientLts;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.lts.wsViQuadplayClient.GetARPU;
import com.bomwebportal.lts.wsViQuadplayClient.GetARPUResponse;
import com.bomwebportal.lts.wsViQuadplayClient.GetProfileService;
import com.bomwebportal.lts.wsViQuadplayClient.GetProfileServiceResponse;
import com.bomwebportal.lts.wsViQuadplayClient.GetProfileSubscribedPlan;
import com.bomwebportal.lts.wsViQuadplayClient.GetProfileSubscribedPlanResponse;
import com.bomwebportal.lts.wsViQuadplayClient.IsTVBSubscriber;
import com.bomwebportal.lts.wsViQuadplayClient.IsTVBSubscriberResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetARPURequest;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetARPUResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileServiceRequest;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileServiceResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileSubscribedPlanRequest;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileSubscribedPlanResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBIsTVBSubscriberRequest;
import com.bomwebportal.lts.wsViQuadplayClient.SBIsTVBSubscriberResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBSetAdultContentReceivableRequest;
import com.bomwebportal.lts.wsViQuadplayClient.SBSetAdultContentReceivableResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SBSetDateOfBirthRequest;
import com.bomwebportal.lts.wsViQuadplayClient.SBSetDateOfBirthResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SetAdultContentReceivable;
import com.bomwebportal.lts.wsViQuadplayClient.SetAdultContentReceivableResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SetDateOfBirth;
import com.bomwebportal.lts.wsViQuadplayClient.SetDateOfBirthResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SpringBoardResponseCode;

public class ViQuadplayWsClient {

	private final Log logger = LogFactory.getLog(getClass());
	
	private static HashMap <SpringBoardResponseCode,String> wsErrorMap = new HashMap<SpringBoardResponseCode,String>();
	static {
		wsErrorMap.put(SpringBoardResponseCode.SUCCESS,"SUCCESS");
		wsErrorMap.put(SpringBoardResponseCode.ACCOUNT_NOT_FOUND,"ACCOUNT NOT FOUND!");
		wsErrorMap.put(SpringBoardResponseCode.MISSING_INPUT,"MISSING INPUT!");
		wsErrorMap.put(SpringBoardResponseCode.INTERNAL_ERROR,"INTERNAL ERROR!");
		wsErrorMap.put(SpringBoardResponseCode.INVALID_INPUT,"INVALID INPUT!");
	}
	
	private WebServiceTemplate webServiceTemplate;	
	
	public SBGetProfileServiceResponse getProfileService(String pFsa) throws Exception {
		
		SBGetProfileServiceRequest requestArg = new SBGetProfileServiceRequest();		
		requestArg.setFsa(pFsa);
		
		GetProfileService request = new GetProfileService();
		request.setArg0(requestArg);
				
		try {
			
			GetProfileServiceResponse response = (GetProfileServiceResponse) webServiceTemplate
					.marshalSendAndReceive(request);

			SBGetProfileServiceResponse result = response.getSBGetProfileServiceResponse();
			
			return (SBGetProfileServiceResponse) getValidResponse(result,
					result.getSbRespCode());
			
		} catch (WebServiceException ex) {
			logger.error(ex.getMessage());
			throw ex;			
		} catch (Exception ex) {
			logger.error("Problem calling WS in VIM - " + ex.getMessage());
			throw ex;
		}
	}

	public SBGetProfileSubscribedPlanResponse getProfileSubscirbedPlan(String pFsa) throws Exception {
		
		SBGetProfileSubscribedPlanRequest requestArg = new SBGetProfileSubscribedPlanRequest();		
		requestArg.setFsa(pFsa);
				
		GetProfileSubscribedPlan request = new GetProfileSubscribedPlan();
		request.setArg0(requestArg);
		
		try {
			
			GetProfileSubscribedPlanResponse response = (GetProfileSubscribedPlanResponse) webServiceTemplate
			.marshalSendAndReceive(request);			
			
			SBGetProfileSubscribedPlanResponse result = response.getSBGetProfileSubscribedPlanResponse();
			
			return (SBGetProfileSubscribedPlanResponse) getValidResponse(result,
					result.getSbRespCode());
			
		} catch (WebServiceException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("Problem calling WS in VIM - " + ex.getMessage());
			throw ex;
		}
	}
	
	public SBGetARPUResponse getARPU(String pFsa) throws Exception {
		
		SBGetARPURequest requestArg = new SBGetARPURequest();		
		requestArg.setFsa(pFsa);
		
		GetARPU request = new GetARPU();
		request.setArg0(requestArg);
				
		try {
			
			GetARPUResponse response = (GetARPUResponse) webServiceTemplate
			.marshalSendAndReceive(request);				
			
			SBGetARPUResponse result = response.getSBGetARPUResponse();		
			
			return (SBGetARPUResponse) getValidResponse(result,
					result.getSbRespCode());
			
		} catch (WebServiceException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("Problem calling WS in VIM - " + ex.getMessage());
			throw ex;
		}
	}	
	
	public SBSetDateOfBirthResponse setDateOfBirth(String pFsa, Date pDOB, String pModifiedBy) throws Exception {
		GregorianCalendar gregCalendar = new GregorianCalendar();
		gregCalendar.setTimeInMillis(pDOB.getTime());
		
		XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory
				.newInstance().newXMLGregorianCalendar(gregCalendar);
				
		SBSetDateOfBirthRequest requestArg = new SBSetDateOfBirthRequest();		
		requestArg.setFsa(pFsa);
		requestArg.setDateOfBirth(xmlGregorianCalendar);
		requestArg.setCsrId(pModifiedBy);
		
		SetDateOfBirth request = new SetDateOfBirth();
		request.setSBSetDateOfBirthRequest(requestArg);
				
		try {
			
			SetDateOfBirthResponse response = (SetDateOfBirthResponse) webServiceTemplate
			.marshalSendAndReceive(request);				
			
			SBSetDateOfBirthResponse result = response.getSBSetDateOfBirthResponse();		
			
			return (SBSetDateOfBirthResponse) getValidResponse(result,
					result.getSbRespCode());
			
		} catch (WebServiceException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("Problem calling WS in VIM - " + ex.getMessage());
			throw ex;
		}
	}		
	
	public SBIsTVBSubscriberResponse isTVBSubscriber(String pFsa) throws Exception {
						
		SBIsTVBSubscriberRequest requestArg = new SBIsTVBSubscriberRequest();		
		requestArg.setFsa(pFsa);
		
		IsTVBSubscriber request = new IsTVBSubscriber();
		request.setArg0(requestArg);
				
		try {
			
			IsTVBSubscriberResponse response = (IsTVBSubscriberResponse) webServiceTemplate
			.marshalSendAndReceive(request);				
			
			SBIsTVBSubscriberResponse result = response.getIsTVBSubscriberResponse();		
			
			return (SBIsTVBSubscriberResponse) getValidResponse(result,
					result.getSbRespCode());
			
		} catch (WebServiceException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("Problem calling WS in VIM - " + ex.getMessage());
			throw ex;
		}
	}	
	
	public SBSetAdultContentReceivableResponse setAdultContentReceivable(String pFsa, boolean pReceiveAdultContent, String pModifiedBy) throws Exception {
		
		SBSetAdultContentReceivableRequest requestArg = new SBSetAdultContentReceivableRequest();		
		requestArg.setFsa(pFsa);
		requestArg.setReceiveAdultContent(pReceiveAdultContent);
		requestArg.setModifiedBy(pModifiedBy);
		
		SetAdultContentReceivable request = new SetAdultContentReceivable();
		request.setArg0(requestArg);
				
		try {
			
			SetAdultContentReceivableResponse response = (SetAdultContentReceivableResponse) webServiceTemplate
			.marshalSendAndReceive(request);				
			
			SBSetAdultContentReceivableResponse result = response.getSBSetAdultContentReceivableResponse();	
			
			return (SBSetAdultContentReceivableResponse) getValidResponse(result,
					result.getSbRespCode());
			
		} catch (WebServiceException ex) {
			logger.error(ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("Problem calling WS in VIM - " + ex.getMessage());
			throw ex;
		}
	}	
	
	private Object getValidResponse(Object pResponse, SpringBoardResponseCode pResponseCode)
			throws Exception {
		if (SpringBoardResponseCode.SUCCESS != pResponseCode) {
			throw new WebServiceException((String) wsErrorMap.get(pResponseCode));
		} else {
			return pResponse;
		}
	}
	
	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}	
	
}
