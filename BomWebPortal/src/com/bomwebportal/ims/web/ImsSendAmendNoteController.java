package com.bomwebportal.ims.web;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.mail.MailSendException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsEmailParamHelperDAO;
import com.bomwebportal.ims.dto.ui.DSOrderSendEmailHistUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsSMSService;

import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.google.gson.Gson;


public class ImsSendAmendNoteController  implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	private ImsSMSService imsSMSService;
	public ImsSMSService getImsSMSService() {
		return imsSMSService;
	}
	private ImsOrderService orderservice;
	public void setImsSMSService(ImsSMSService imsSMSService) {
		this.imsSMSService = imsSMSService;
	}

	private OrdEmailReqService ordEmailReqService;
	private OrderEsignatureService orderEsignatureService;

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}

	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}
	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}
	private ImsEmailParamHelperDAO imsEmailParamHelperDAO; 
	public ImsEmailParamHelperDAO getImsEmailParamHelperDAO() {
		return imsEmailParamHelperDAO;
	}
	public void setImsEmailParamHelperDAO(
			ImsEmailParamHelperDAO imsEmailParamHelperDAO) {
		this.imsEmailParamHelperDAO = imsEmailParamHelperDAO;
	}


	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		List<OrdEmailReqDTO> requestList = ordEmailReqService.getOrdEmailReqDTOALLBySearchIMS(null, null,null);
//        logger.info("requestList:   "+ new Gson().toJson(requestList));
		for (OrdEmailReqDTO insertedDto:requestList){

			EmailReqResult result;
//			OrdEmailReqDTO insertedDto;
//			insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(dto.getOrderId(), dto.getSeqNo(), dto.getTemplateId());
//	        logger.info("insertedDto:   "+ new Gson().toJson(insertedDto));
			OrderImsUI order = orderservice.getBomWebImsOrder(insertedDto.getOrderId()); 
			if(insertedDto.getMethod()==DisMode.S){
				String shortenUrl = imsSMSService.getShortenUrl(insertedDto.getOrderId());
				String locale = EsigEmailLang.ENG.equals(insertedDto.getEsigEmailLang())?"ENG":"CHN";
				try {
					 if(order.isOrderTypeNowRet()){ 
						result = sendNowRetSms(insertedDto, insertedDto.getSMSno(),  shortenUrl, order, locale);
						if(EmailReqResult.SUCCESS==result){
							insertedDto.setSentDate(new Date());
							insertedDto.setErrMsg(null);
						}else{
							insertedDto.setErrMsg(EmailReqResult.FAIL.toString());
						}
					 }else{
						String api_code = ImsConstants.IMS_API_CODE;	
	
						if (imsEmailParamHelperDAO.isDSPCD(insertedDto.getTemplateId())){
							api_code = ImsConstants.IMS_DS_PCD_API_CODE;
						}
						else if (imsEmailParamHelperDAO.isDSNTV(insertedDto.getTemplateId())){
							api_code = ImsConstants.IMS_DS_NTV_API_CODE;
						} 
	//					logger.info("api_code:   "+api_code);
						//imsSMSService.sendSMSMessage(insertedDto.getSMSno(), insertedDto, api_code);
						//result = EmailReqResult.SUCCESS;
						
						if (!StringUtils.isEmpty(imsEmailParamHelperDAO.isNTV(insertedDto.getTemplateId()))) {
							result = EmailReqResult.SUCCESS;
							imsSMSService.sendNTVSMS(insertedDto, result, insertedDto.getSMSno());
							insertedDto.setSentDate(new Date());
						} else {
							imsSMSService.sendSMSMessage(insertedDto.getSMSno(), insertedDto, api_code);
							result = EmailReqResult.SUCCESS;
							insertedDto.setSentDate(new Date());
							insertedDto.setErrMsg(null);
						}
					}
//					insertedDto.setSentDate(new Date());
//					insertedDto.setErrMsg(null);
				} catch (Exception e) {
					logger.warn("Exception in sendOrderSMS", e);

					result = parseExceptionS(insertedDto, e);

					StringBuilder errMsg = new StringBuilder(result.getMessage()); 
					if (e.getMessage() != null) {
						errMsg.append(" " + e.getMessage());
					}
					insertedDto.setErrMsg(errMsg.length() > 100 ? errMsg.substring(0, 100) : errMsg.toString());
				}

				try {
					ordEmailReqService.updateOrdSMSReq(insertedDto);
				} catch (Exception e) {
					logger.warn("Exception in updateOrdEmailReq", e);
					throw new AppRuntimeException(e == null ? "" : e.getMessage());
				}
				if (logger.isDebugEnabled()) {
					logger.debug("result: " + result);
				}
			}
			else if (insertedDto.getMethod()==DisMode.E || insertedDto.getMethod()==DisMode.I){
				try {
					orderEsignatureService.sendOrderEmail(insertedDto);
					result = EmailReqResult.SUCCESS;

					insertedDto.setSentDate(new Date());
					insertedDto.setErrMsg(null);
				} catch (Exception e) {
					logger.warn("Exception in sendOrderEmail", e);

					result = parseExceptionE(insertedDto, e);

					StringBuilder errMsg = new StringBuilder(result.getMessage()); 
					if (e.getMessage() != null) {
						errMsg.append(" " + e.getMessage());
					}
					insertedDto.setErrMsg(errMsg.length() > 100 ? errMsg.substring(0, 100) : errMsg.toString());
				}
				try {
					ordEmailReqService.updateOrdEmailReq(insertedDto);
				} catch (Exception e) {
					logger.warn("Exception in updateOrdEmailReq", e);
					throw new AppRuntimeException(e == null ? "" : e.getMessage());
				}
				if (logger.isDebugEnabled()) {
					logger.debug("result: " + result);
				}
			}




		}
		return null;

	}


	private EmailReqResult parseExceptionS(OrdEmailReqDTO dto, Exception e) {

		if (e instanceof ServiceException) {
			return EmailReqResult.SMS_FAIL;
		}
		return EmailReqResult.FAIL;
	}
	private EmailReqResult parseExceptionE(OrdEmailReqDTO dto, Exception e) {
		if (e instanceof MailSendException) {
			return EmailReqResult.MAIL_SEND_EXCEPTION;
		}
		if (e instanceof FileNotFoundException) {
			return EmailReqResult.ATTACHMENT_NOT_FOUND;
		}
		if (e instanceof InvalidEncryptPasswordLengthException) {
			// mob customized message
			if ("MOB".equals(dto.getLob())) {
				return EmailReqResult.INVALID_ENCRYPT_PASSWORD_LENGTH_MOB;
			}
			return EmailReqResult.INVALID_ENCRYPT_PASSWORD_LENGTH;
		}
		if (e instanceof InvalidEncryptPasswordException) {
			return EmailReqResult.INVALID_ENCRYPT_PASSWORD;
		}
		return EmailReqResult.FAIL;
	}

	private class InvalidEncryptPasswordLengthException extends InvalidEncryptPasswordException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidEncryptPasswordLengthException() {
			super();
		}
	}

	private class InvalidEncryptPasswordException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidEncryptPasswordException() {
			super();
		}
	}

	public EmailReqResult sendNowRetSms(OrdEmailReqDTO insertedDto, String smsNo, String shortenUrl, OrderImsUI order, 	String locale) {
		try{
			String rs = imsSMSService.sendNowRetSms(smsNo,insertedDto.getTemplateId(), insertedDto.getOrderId(), locale, order.getSignOffDate(), shortenUrl);
			if(rs.equals("Error")){
				return EmailReqResult.SMS_FAIL;
			}else{
				return EmailReqResult.SUCCESS;
			}
		}catch (Exception e){
			logger.error("Exception caught in sendNowRetSms()", e);
		}
		return EmailReqResult.SMS_FAIL;
	}

	public ImsOrderService getOrderservice() {
		return orderservice;
	}

	public void setOrderservice(ImsOrderService orderservice) {
		this.orderservice = orderservice;
	}
}
