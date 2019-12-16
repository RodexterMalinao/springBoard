package com.bomltsportal.service;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.dao.OnlineSalesLogDAO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.RequestLogDTO;
import com.bomwebportal.exception.DAOException;

public class OnlineSalesLogServiceImpl implements OnlineSalesLogService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private OnlineSalesLogDAO dao;

	public boolean serviceLog(String channel,String action, String ip_addr) throws Exception {
		try{
			logger.info("OnlineSalesLogServiceImpl.serviceLog");
			dao.insertOnlineSalesLog(channel,action,ip_addr);
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return false;
		}
		return true;
	}

	public String insertOnlineExceptionLog(String reqId, String pageId, String msg){
		try{
			logger.info("OnlineSalesLogServiceImpl.insertOnlineExceptionLog");
			//reqId = dao.getNewOnlineExceptionLogId();
			if(StringUtils.isNotBlank(reqId)){
				dao.insertOnlineExceptionLog(reqId, pageId, msg);
				return reqId;
			}
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}
	
	public String getNewRequest(String ip, String sessionId, String ua, String channel, String lob, String subType){
		logger.debug("getNewRequest");
		String reqId = "";
		
		try{
			
			RequestLogDTO dto = new RequestLogDTO();
			dto.setIpAddress(ip);
			dto.setSessionId(sessionId);
			dto.setLob(lob);
			dto.setSubType(subType);
			dto.setUseragent(ua);
			dto.setDeviceType(getDeviceType(ua));
			dto.setChannel(channel);
			
			reqId = dao.getRequestId();			
			dto.setReqId(reqId);
			
			dao.insertOnlineRequest(dto);
			
		}catch(Exception e){
			logger.error("", e);
		}
		
		return reqId;
	}
	
	private String getDeviceType(String ua) {
		String device = null;
		
		try{
			ua = ua.toLowerCase();
			
			if(ua.indexOf("ipad") >= 0 && ua.indexOf("safari") >= 0){
				device = "ipad";
			}else if(ua.indexOf("iphone") >= 0 && ua.indexOf("safari") >= 0){
				device = "iphone";
			}else if(ua.indexOf("android") >= 0 && ua.indexOf("linux") >= 0){
				device = "android";
			}
			
			
		}catch(Exception e){
			logger.error("", e);
			device = null;
		}
		
		return device;
	}
	
	public void logPageTrackDetail(OrderCaptureDTO order, String reqId, String currentPage, String nextPage, String reqSeq){
		logger.debug("logPageTrackDetail is call");		
		long starttime = System.currentTimeMillis();
		logger.debug("Start time - logPageTrackDetail: " + starttime);
		
		try{			
//			
//			if(order!=null){
//				ArrayList<ItemDTO> detailList = getLogItemList(order);
//				for(ItemDTO item : detailList){		
//					dao.insertOnlineDetailTrack(reqId, currentPage, item.ItemCd, item.ItemVal, reqSeq);
//				}			
//			}	
		
			dao.insertOnlinePageTrack(reqId, nextPage, currentPage, String.valueOf((Integer.valueOf(reqSeq))+1));
			
		}catch(Exception e){
			logger.error("", e);
		}
		
		long endtime = System.currentTimeMillis();		
		long callTime = endtime - starttime;
		logger.debug("End time - logPageTrackDetail: " + endtime);
		logger.debug("Total call time - logPageTrackDetail: " + callTime + " ms");
		
	}

	public void logOnlineDetailTrack(String reqId, String currentPage, String itemCd, String itemVal,String reqSeq){
		try {
			dao.insertOnlineDetailTrack(reqId, currentPage, itemCd, itemVal, reqSeq);
		} catch (DAOException e) {
			logger.info("fail to insertOnlineDetailTrack!!!");
			return;
		}
	}
	
	public OnlineSalesLogDAO getOnlineSalesLogDAO() {
		return dao;
	}

	public void setOnlineSalesLogDAO(OnlineSalesLogDAO onlineSalesLogDAO) {
		this.dao = onlineSalesLogDAO;
	}
}
