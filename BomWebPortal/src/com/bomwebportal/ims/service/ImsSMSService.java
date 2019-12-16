package com.bomwebportal.ims.service;

import java.util.Date;

import javax.xml.rpc.ServiceException;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;


public interface ImsSMSService {
	public void sendSMSMessage(String mobileno, OrdEmailReqDTO dto, String aPIcode) throws ServiceException ;//celia ims 20141210
	public EmailReqResult createSMSReq(String orderId, Date requestDate, String SMSno, String username, String templateId);
	public EmailReqResult resendSMSReq(String orderId, String templateId, String SMSno, String createBy);
	public EmailReqResult resendSMSReq(String orderId, String templateId, String SMSno, String createBy, int _seq);
	public EmailReqResult createSMSReq(String orderId, Date requestDate, String SMSno, String username, String templateId, int _seq);
	public String sendNowRetSms(String number, String templateId, String order_id, String locale,Date signOffDate, String afShortUrl);
	public void addSendNowRetSmsRecord(String orderId, String templateId, String SMSno, String createBy, EmailReqResult result, int _seq);
	public void sendNTVSMS(OrdEmailReqDTO dto, EmailReqResult result, String smsNo) throws ServiceException; //martin ims 20151106
	
	public String shortenUrl(String longUrl);
	public String getShortenUrl(String order_id);
	public String getShortenUrlQrCode(String order_id); //martin, 20170125, BOM2016144
}
