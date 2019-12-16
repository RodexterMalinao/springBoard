package com.bomltsportal.service;

import com.bomltsportal.dto.OrderCaptureDTO;

public interface OnlineSalesLogService {

	public boolean serviceLog(String channel,String action,String ip_addr)throws Exception;
	public String insertOnlineExceptionLog(String reqId, String pageId, String msg);
	public String getNewRequest(String remoteAddr, String id, String header, String channel, String string, String subType);
	public void logPageTrackDetail(OrderCaptureDTO order, String reqId, String currentPage, String nextPage, String reqSeq);
	public void logOnlineDetailTrack(String reqId, String currentPage, String itemCd, String itemVal,String reqSeq);
}
