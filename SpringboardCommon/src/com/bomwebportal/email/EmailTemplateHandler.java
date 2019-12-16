package com.bomwebportal.email;

import java.util.Map;

import com.bomwebportal.dto.OrdEmailReqDTO;

public interface EmailTemplateHandler {
	void beforeEmailSent(OrdEmailReqDTO req);
	Map<String,?> getSubjectProperties(OrdEmailReqDTO req);
	Map<String,?> getContentProperties(OrdEmailReqDTO req);
	void afterEmailSent(OrdEmailReqDTO req);
}
