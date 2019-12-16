package com.pccw.springframework.web.servlet.tags.form;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pccw.dto.AbstractSecuritySupportFormDTO;
import com.pccw.springframework.web.servlet.support.SecuritySupport;

public class SelectTag extends org.springframework.web.servlet.tags.form.SelectTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7853011986973349785L;
	
	@Override
	protected int writeTagContent(TagWriter pTagWriter) throws JspException {
		String security = null;
		try {
			 security = SecuritySupport.getSecurity(this.getRequestContext(), this.getNestedPath(), this.getPath());
		} catch (Exception e) {
			if (e instanceof JspException) {
				throw (JspException) e;
			}
			throw new JspException(e);
		}
		if (AbstractSecuritySupportFormDTO.SECURITY_READONLY.equals(security)) {
			this.setReadonly("true");
		} else if (AbstractSecuritySupportFormDTO.SECURITY_HIDDEN.equals(security)) {
			return SecuritySupport.writeHiddenTagContent(this, pTagWriter, this.getDisplayString(getBoundValue(), this.getPropertyEditor()));
		}
		  
		return super.writeTagContent(pTagWriter);
	}

}
