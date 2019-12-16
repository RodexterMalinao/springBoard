package com.bomwebportal.ims.wsclient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.wsClientIms.BomwebTmpDTO;
import com.bomwebportal.ims.wsClientIms.ImsLoginTransaction;
import com.bomwebportal.ims.wsClientIms.InsertBomwebTmp;
import com.bomwebportal.ims.wsClientIms.InsertBomwebTmpResponse;
import com.bomwebportal.ims.wsClientIms.ReleaseLoginId;
import com.bomwebportal.ims.wsClientIms.ReleaseLoginIdResponse;
import com.bomwebportal.ims.wsClientIms.ReserveLoginId;
import com.bomwebportal.ims.wsClientIms.ReserveLoginIdResponse;


public class ImsWSClient {
	
	private WebServiceTemplate webServiceTemplate;	
	private final Log logger = LogFactory.getLog(getClass());		
	
	public int reserveLoginID(String loginName, String idNo, String idType){
		logger.info("reserveLoginID["+loginName+"]");
		
		long starttime = System.currentTimeMillis();
		logger.info("Start time - reserveLoginID["+loginName+"]: " + starttime);
		
		try{
			ReserveLoginId request = new ReserveLoginId();
			ImsLoginTransaction imsLoginTransaction = new ImsLoginTransaction();
			
			imsLoginTransaction.setLoginID(loginName);
			imsLoginTransaction.setDomain("N");
			imsLoginTransaction.setIdDocNo(idNo);
			imsLoginTransaction.setIdDocType(idType);
			imsLoginTransaction.setSessionID("-99999999");
			
			request.setPImsLoginTransaction(imsLoginTransaction);			
			
			ReserveLoginIdResponse response = (ReserveLoginIdResponse)webServiceTemplate.
				marshalSendAndReceive(request);
			
			logger.info("returncode:"+response.getReturn().getReturnCode());
			logger.info("errorcode:"+response.getReturn().getErrorCode());
			logger.info("errormsg:"+response.getReturn().getErrorMsg());
			
			long endtime = System.currentTimeMillis();			
			long callTime = endtime - starttime;
			logger.info("End time - reserveLoginID["+loginName+"]: " + endtime);
			logger.info("Total call time - reserveLoginID["+loginName+"]: " + callTime + " ms");
			
			if(Integer.parseInt(response.getReturn().getErrorCode()) < 0){
				return Integer.parseInt(response.getReturn().getErrorCode());
			}						
			
			return 0;
		}catch(Exception e){
			throw new AppRuntimeException(e);
		}
	}
	
	public int releaseLoginID(String loginName, String idNo, String idType){
		logger.info("releaseLoginID["+loginName+"]");
		
		long starttime = System.currentTimeMillis();
		logger.info("Start time - releaseLoginID["+loginName+"]: " + starttime);
		
		try{
			ReleaseLoginId request = new ReleaseLoginId();
			ImsLoginTransaction imsLoginTransaction = new ImsLoginTransaction();
			
			imsLoginTransaction.setLoginID(loginName);
			imsLoginTransaction.setDomain("N");
			imsLoginTransaction.setIdDocNo(idNo);
			imsLoginTransaction.setIdDocType(idType);
			imsLoginTransaction.setSessionID("-99999999");
			
			request.setPImsLoginTransaction(imsLoginTransaction);			
			
			ReleaseLoginIdResponse response = (ReleaseLoginIdResponse)webServiceTemplate.
				marshalSendAndReceive(request);
			
			System.out.println(response.getReturn().getReturnCode());
			System.out.println(response.getReturn().getErrorCode());
			System.out.println(response.getReturn().getErrorMsg());
			
			if(Integer.parseInt(response.getReturn().getErrorCode()) < 0){
				return Integer.parseInt(response.getReturn().getErrorCode());
			}
			
			long endtime = System.currentTimeMillis();			
			long callTime = endtime - starttime;
			logger.info("End time - releaseLoginID["+loginName+"]: " + endtime);
			logger.info("Total call time - releaseLoginID["+loginName+"]: " + callTime + " ms");
			
			return 0;
		}catch(Exception e){
			throw new AppRuntimeException(e);
		}
	}
	
	public void InsertBomwebTmp(BomwebTmpDTO data){
		try{
			InsertBomwebTmp request = new InsertBomwebTmp();			
			request.setPBomwebTmpDTO(data);		
			
			InsertBomwebTmpResponse response = (InsertBomwebTmpResponse)webServiceTemplate.
				marshalSendAndReceive(request);
			
			logger.info("retcode:"+response.getReturn().getRetCode());
			logger.info("errcode:"+response.getReturn().getErrCode());
			logger.info("errtext:"+response.getReturn().getErrText());
			logger.info("reqid:"+response.getReturn().getReqId());
			
			if(response.getReturn().getRetCode()!=0 || !response.getReturn().isInsertBomwebTmpSuccess()){
				throw new Exception("Web service returned error: retcode="+
						response.getReturn().getRetCode()+
						"; errcode="+response.getReturn().getErrCode()+
						"; errtext="+response.getReturn().getErrText());
			}
			
		}catch(Exception ex){
			logger.error("Error in calling WS to Bom");
			throw new AppRuntimeException(ex);
		}
	}

	public WebServiceTemplate getWebServiceTemplate() {
		return webServiceTemplate;
	}

	public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}		
	
	

}
