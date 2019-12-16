package com.bomwebportal.mob.ccs.service;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.MailService;

public class MobCcsUrgentOrderAlertServiceImpl implements MobCcsUrgentOrderAlertService {
	protected final Log logger = LogFactory.getLog(getClass());

	enum EmailTemplateVariable {
		FALLOUT_DESC("falloutDesc")
		, ORDER_ID("orderId")
		;
		private EmailTemplateVariable(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		private String name;
	}
	
	private MailService mailService;
	private CodeLkupService codeLkupService;	
	
	private String senderEmailAddr;
	private String receiverEmailAddr;
	private Resource emailTemplate;
	
	private String emailTitleTemplate;
	private String emailContentTemplate;
	
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public String getSenderEmailAddr() {
		return senderEmailAddr;
	}

	public void setSenderEmailAddr(String senderEmailAddr) {
		this.senderEmailAddr = senderEmailAddr;
	}

	public String getReceiverEmailAddr() {
		return receiverEmailAddr;
	}

	public void setReceiverEmailAddr(String receiverEmailAddr) {
		this.receiverEmailAddr = receiverEmailAddr;
	}

	public Resource getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(Resource emailTemplate) {
		this.emailTemplate = emailTemplate;
		InputStream is = null;
		try {
			is = emailTemplate.getInputStream();
			String fileContent = IOUtils.toString(is, "UTF-8");
			if (StringUtils.isNotBlank(fileContent)) {
				String[] lines = fileContent.split("\\r?\\n");
				if (lines != null && lines.length > 0) {
					this.emailTitleTemplate = lines[0];
					StringBuilder sb = new StringBuilder();
					for (int i = 2; i < lines.length; i++) {
						if (i > 2) {
							sb.append("\n");
						}
						sb.append(lines[i]);
					}
					this.emailContentTemplate = sb.toString();
				}
			}
			is.close();
			is = null;
		} catch (IOException e) {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e1) {}
			}
		}
	}

	public void sendAlert(OrderDTO orderDto) throws MailException, MessagingException, IOException, CompilationFailedException, ClassNotFoundException {
		if (StringUtils.isBlank(senderEmailAddr)) {
			if (logger.isInfoEnabled()) {
				logger.info("senderEmailAddr is blank");
			}
			return;
		}
		if (StringUtils.isBlank(receiverEmailAddr)) {
			if (logger.isInfoEnabled()) {
				logger.info("receiverEmailAddr is blank");
			}
			return;
		}
		InternetAddress from = new InternetAddress(senderEmailAddr);
		if (receiverEmailAddr.indexOf(",") == -1) {
			InternetAddress to = new InternetAddress(receiverEmailAddr);
			List<File> attachments = null;
			this.mailService.send(from, to, null, null, this.replace(emailTitleTemplate, orderDto), this.replace(emailContentTemplate,  orderDto), attachments);
		} else {
			String[] receiverEmailAddrs = receiverEmailAddr.split(",");
			List<InternetAddress> tos = new ArrayList<InternetAddress>();
			for (String addr : receiverEmailAddrs) {
				tos.add(new InternetAddress(addr));
			}
			List<File> attachments = null;
			this.mailService.send(from, tos.toArray(new InternetAddress[0]), null, null, this.replace(emailTitleTemplate, orderDto), this.replace(emailContentTemplate,  orderDto), attachments);
		}
	}
	
	private String replace(String templateContent, OrderDTO orderDto) throws CompilationFailedException, ClassNotFoundException, IOException {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Template template = engine.createTemplate(templateContent);
	
		Map<String, String> params = new HashMap<String, String>();
		for (EmailTemplateVariable var : EmailTemplateVariable.values()) {
			String value = null;
			switch (var) {
			case FALLOUT_DESC:
			{
				List<CodeLkupDTO> codeLkupList = this.codeLkupService.getCodeLkupDTOALL("ORD_FALLOUT_CODE");
				if (StringUtils.isNotBlank(orderDto.getReasonCd())) {
					if (!this.isEmpty(codeLkupList)) {
						for (CodeLkupDTO dto : codeLkupList) {
							if (orderDto.getReasonCd().equals(dto.getCodeId())) {
								value = dto.getCodeDesc();
								break;
							}
						}
					}
				}
			}
				break;
			case ORDER_ID:
				value = orderDto.getOrderId();
				break;
			}
			if (value != null) {
				params.put(var.getName(), value);
			}
		}
		Writable writable = template.make(params);
		return writable.toString();
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
