/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pccw.springframework.web.servlet.tags.form;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pccw.dto.AbstractSecuritySupportFormDTO;
import com.pccw.springframework.web.servlet.support.SecuritySupport;

/**
 * Databinding-aware JSP tag for rendering an HTML '<code>input</code>'
 * element with a '<code>type</code>' of '<code>password</code>'.
 *
 * @author Rob Harrop
 * @author Rick Evans
 * @since 2.0
 */
public class PasswordInputTag extends org.springframework.web.servlet.tags.form.PasswordInputTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6237944305318301839L;

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