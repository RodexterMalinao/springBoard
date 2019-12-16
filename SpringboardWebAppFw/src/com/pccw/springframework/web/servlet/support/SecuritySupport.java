package com.pccw.springframework.web.servlet.support;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.form.AbstractHtmlElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.bomwebportal.exception.InsufficientPrivilegeException;
import com.pccw.dto.AbstractSecuritySupportFormDTO;

public class SecuritySupport {
	
	public static String getSecurity(RequestContext pRequestContext, String pNestedPath) throws Exception {
		return getSecurity(pRequestContext, pNestedPath, null);
	}
	
	public static String getSecurity(RequestContext pRequestContext, String pNestedPath, String pPath) throws Exception {
		Object bean = getModelObject(pRequestContext, pNestedPath);
		if (bean == null) {
			return AbstractSecuritySupportFormDTO.SECURITY_ENABLE;
		}
		if (!(bean instanceof AbstractSecuritySupportFormDTO)) {
			throw new JspException(
					"Bean " + bean.getClass().getName() + " is not instance of " 
							+ AbstractSecuritySupportFormDTO.class.getName() 
							+ ", commad = " + pNestedPath);
		}

		String security = null;
		if (pPath != null) {
			security = ((AbstractSecuritySupportFormDTO) bean).getPropertySecurity(pPath);
			if (security != null) {
				return security;
			}
		}
		
		security = ((AbstractSecuritySupportFormDTO) bean).getDefaultSecurity();
		if (AbstractSecuritySupportFormDTO.SECURITY_DISABLE.equals(security)) {
			throw new InsufficientPrivilegeException(((AbstractSecuritySupportFormDTO) bean).getFunctionId(), security);
		}
		return security;
	}

	private static Object getModelObject(RequestContext pRequestContext, String pNestedPath) throws JspException {
		String beanName = pNestedPath;
		int dotPos = beanName.indexOf('.');
		if (dotPos != -1) {
			beanName = beanName.substring(0, dotPos);
		}
		return pRequestContext.getModelObject(beanName);
	}
	
	public static int writeHiddenTagContent(AbstractHtmlElementTag pHtmlElementTag, TagWriter pTagWriter, String pValue) throws JspException {
		pTagWriter.startTag("input");
		pHtmlElementTag.writeOptionalAttribute(pTagWriter, "id", pHtmlElementTag.resolveId());
		pHtmlElementTag.writeOptionalAttribute(pTagWriter, "name", pHtmlElementTag.getName());
		pTagWriter.writeAttribute("type", "hidden");
		pTagWriter.writeAttribute("value", pValue);
		pTagWriter.endTag();
		return TagSupport.SKIP_BODY;
	}
}
