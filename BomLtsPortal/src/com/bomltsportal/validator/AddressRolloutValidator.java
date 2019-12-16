package com.bomltsportal.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;

import com.bomltsportal.dto.form.AddressRolloutFormDTO;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

public class AddressRolloutValidator implements Validator {

	protected final Log logger = LogFactory.getLog(getClass());
	private GenericManageableCaptchaService captchaService;
	@Override
	public boolean supports(Class clazz) {
		return AddressRolloutFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object command, Errors errors) {
		AddressRolloutFormDTO form = (AddressRolloutFormDTO) command;
		boolean isResponseCorrect = false;
		
		String captchaId = RequestContextHolder.currentRequestAttributes().getSessionId();
		String response = form.getCaptchaInput();
		logger.info("captchaInput = "+response);
		try { 
			if(response != null){
				isResponseCorrect = captchaService.validateResponseForID(captchaId, response);
				logger.info("isResponseCorrect = "+isResponseCorrect);
			}
		} catch (CaptchaServiceException e) {logger.info("captcha error");}
		
		if(!isResponseCorrect){
			errors.rejectValue("captchaInput", "addr.captchaerror");
		} 

	}

	public GenericManageableCaptchaService getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(GenericManageableCaptchaService captchaService) {
		this.captchaService = captchaService;
	}

}
