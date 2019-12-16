package com.bomwebportal.service;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.email.EmailReqResult;
import com.bomwebportal.email.EmailTemplateHandler;

public interface EmailService {

	public abstract String getSenderEmailAddress(OrdEmailReqDTO pOrdEmailReqDto);

	public abstract String getSenderEmailName(OrdEmailReqDTO pOrdEmailReqDto);

	public abstract String getEncryptFilePassword(OrdEmailReqDTO pOrdEmailReqDto, String pPassword);
	
	public EmailTemplateHandler getTemplateHandler(String lob, String templateId);

	public EmailReqResult createEmailReqAndSendEmail(String password, OrdEmailReqDTO ordEmailReqDTO, String serverPath);
}
