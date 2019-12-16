package com.pccw.springframework.web.servlet.tags.form;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pccw.dto.AbstractSecuritySupportFormDTO;
import com.pccw.springframework.web.servlet.support.SecuritySupport;

public class RadioButtonsTag extends org.springframework.web.servlet.tags.form.RadioButtonsTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5283089182088710442L;
	
	
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
			this.setDisabled("true");
			SecuritySupport.writeHiddenTagContent(this, pTagWriter, this.getDisplayString(getBoundValue(), this.getPropertyEditor()));
		} else if (AbstractSecuritySupportFormDTO.SECURITY_HIDDEN.equals(security)) {
			return SecuritySupport.writeHiddenTagContent(this, pTagWriter, this.getDisplayString(getBoundValue(), this.getPropertyEditor()));
		}
		return super.writeTagContent(pTagWriter);
	}

}
