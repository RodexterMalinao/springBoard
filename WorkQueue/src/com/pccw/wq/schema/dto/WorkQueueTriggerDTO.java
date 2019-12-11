package com.pccw.wq.schema.dto;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.dao.QDicHndlMethodDtlDAO;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.service.WorkQueueDataService;

public class WorkQueueTriggerDTO extends WqInquiryResultFormDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8176507678486193589L;

	private String wqBatchId;
	
	private String handleMethodId;
	
	private String triggerWhen;
	
	private String triggerWhenWqStatus;
	
	private String handleMethod;
	
	private TreeMap<String, String> attributeMap = new TreeMap<String, String>();

	public String getWqBatchId() {
		return wqBatchId;
	}

	public void setWqBatchId(String wqBatchId) {
		this.wqBatchId = wqBatchId;
	}

	public String getHandleMethodId() {
		return this.handleMethodId;
	}

	public void setHandleMethodId(String pHandleMethodId) {
		this.handleMethodId = pHandleMethodId;
	}

	public String getTriggerWhen() {
		return this.triggerWhen;
	}

	public void setTriggerWhen(String pTriggerWhen) {
		this.triggerWhen = pTriggerWhen;
	}

	public String getTriggerWhenWqStatus() {
		return this.triggerWhenWqStatus;
	}

	public void setTriggerWhenWqStatus(String pTriggerWhenWqStatus) {
		this.triggerWhenWqStatus = pTriggerWhenWqStatus;
	}

	public String getHandleMethod() {
		return this.handleMethod;
	}

	public void setHandleMethod(String pHandleMethod) {
		this.handleMethod = pHandleMethod;
	}
	
	public void setHandlingAttributes(QDicHndlMethodDtlDAO[] pHandlingAttributes) {
		if (ArrayUtils.isEmpty(pHandlingAttributes)) {
			return;
		}
		for (QDicHndlMethodDtlDAO qDicHndlMethodDtlDAO : pHandlingAttributes) {
			this.attributeMap.put(qDicHndlMethodDtlDAO.getAttbName(), qDicHndlMethodDtlDAO.getAttbValue());
		}
	}
	
	public String getAttributeValue(String pAttbName) throws Exception {
		if ("{".equals(StringUtils.left(pAttbName, 1))
				&& "}".equals(StringUtils.right(pAttbName, 1))) {
			String attbValue = this.attributeMap.get(pAttbName);
			if (StringUtils.isNotBlank(attbValue)) {
				if ("{".equals(StringUtils.left(attbValue, 1))
						&& "}".equals(StringUtils.right(attbValue, 1))) {
					return SpringApplicationContext.getBean(WorkQueueDataService.class)
								.getHandlingAttributeValue(this, attbValue.substring(1, attbValue.length() - 1));
				} else if ("\"".equals(StringUtils.left(attbValue, 1))
						&& "\"".equals(StringUtils.right(attbValue, 1))) {
					return attbValue.substring(1, attbValue.length() - 1);
				}
				return StringUtils.defaultIfEmpty(BeanUtils.getProperty(this, attbValue), "");
			}
		}
		String msgTemplate = this.attributeMap.get(pAttbName);
		if (StringUtils.isEmpty(msgTemplate)) {
			return "";
		}
		
		if ("{".equals(StringUtils.left(msgTemplate, 1))
				&& "}".equals(StringUtils.right(msgTemplate, 1))) {
			return SpringApplicationContext.getBean(WorkQueueDataService.class)
						.getHandlingAttributeValue(this, msgTemplate.substring(1, msgTemplate.length() - 1));
		}
		
		msgTemplate = handleBindingValue(this, msgTemplate);
		
		if (!this.attributeMap.containsKey("{0}")) {
			return msgTemplate;
		}

		ArrayList<String> attbValueList = new ArrayList<String>();
		String attbName = "{0}";
		int attbCnt = 0;
		while (this.attributeMap.containsKey(attbName)) {
			attbValueList.add(this.getAttributeValue(attbName));
			attbCnt++;
			attbName = "{" + String.valueOf(attbCnt) + "}";
		}
		
		return StringUtils.defaultIfEmpty(MessageFormat.format(msgTemplate, attbValueList.toArray()), "");
	}
	
	public static String handleBindingValue(WorkQueueTriggerDTO pWorkQueueTriggerDTO, String pMsgTemplate) throws Exception {
		int startPos = StringUtils.indexOf(pMsgTemplate, "{");
		int endPos = StringUtils.indexOf(pMsgTemplate, "}");
		String replaceAttbName = null;
		String replaceValue = null;
		while (startPos != -1 && endPos != -1 && endPos > startPos) {
			replaceAttbName = pMsgTemplate.substring(startPos + 1, endPos);
			if ("'{'".equals(pMsgTemplate.substring(startPos - 1, startPos + 2))) {
				startPos = StringUtils.indexOf(pMsgTemplate, "{", startPos + 1);
				endPos = StringUtils.indexOf(pMsgTemplate, "}", ("'}'".equals(pMsgTemplate.substring(endPos - 1, endPos + 2)) ? endPos + 1: startPos + 1));
				continue;
			} else if (StringUtils.isNumeric(replaceAttbName)) {
				startPos = StringUtils.indexOf(pMsgTemplate, "{", endPos);
				endPos = StringUtils.indexOf(pMsgTemplate, "}", endPos + 1);
				continue;
			}
			replaceValue = SpringApplicationContext.getBean(WorkQueueDataService.class)
					.getHandlingAttributeValue(pWorkQueueTriggerDTO, replaceAttbName);
			pMsgTemplate = StringUtils.replace(pMsgTemplate, "{" + replaceAttbName + "}", StringUtils.defaultIfEmpty(replaceValue, " "));
			startPos = StringUtils.indexOf(pMsgTemplate, "{");
			endPos = StringUtils.indexOf(pMsgTemplate, "}");
		}
		return pMsgTemplate;
	}
}