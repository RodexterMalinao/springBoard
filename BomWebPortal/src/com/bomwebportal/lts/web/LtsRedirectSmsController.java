package com.bomwebportal.lts.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.bomwebportal.lts.dto.form.LtsRedirectSmsFormDTO;
import com.bomwebportal.lts.service.sms.LtsSmsService;

public class LtsRedirectSmsController extends AbstractCommandController {

	protected final Log logger = LogFactory.getLog(getClass());
	
//	private final String formView = "ltsredirectsms";
	private final String formView = "json_ltsredirectsms";
	private final String commandName = "ltsredirectsmsCmd";
	
	protected LtsSmsService ltsSmsService;
	
	private String reportServerPath; //dataFilePath

	public String getReportServerPath() {
		return reportServerPath;
	}

	public void setReportServerPath(String reportServerPath) {
		this.reportServerPath = reportServerPath;
	}

	public LtsSmsService getLtsSmsService() {
		return ltsSmsService;
	}

	public void setLtsSmsService(LtsSmsService ltsSmsService) {
		this.ltsSmsService = ltsSmsService;
	}

	public LtsRedirectSmsController() {
		setCommandClass(LtsRedirectSmsFormDTO.class);
		setCommandName(commandName);
//		setFormView(formView);
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ModelAndView ret = null;
		Map<String, Object> returningMessage = new HashMap<String, Object>();
//		returningMessage.put("request", command);
		
		if(errors.hasErrors()){//there is error...

			for(Object error: errors.getAllErrors()){
				returningMessage.put("formErrors", error.toString());
			}
			returningMessage.put("formErrors", errors.toString());
		}else{
			LtsRedirectSmsFormDTO form = (LtsRedirectSmsFormDTO) command;
			logger.info("Sending SMS, info: "+form.toString());
			String smsSendingResult = this.ltsSmsService.sendSms(form.getNumber(), form.getMsg(), "");
			returningMessage.put("sendingSmsResult", smsSendingResult);
		}
		
		ret = new ModelAndView(formView,"Sending_SMS_Result",returningMessage);
		return ret;
	}
	
}
