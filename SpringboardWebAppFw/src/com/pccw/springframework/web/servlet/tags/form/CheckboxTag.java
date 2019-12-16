/*
 * Copyright 2002-2008 the original author or authors.
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

import java.util.Collection;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pccw.dto.AbstractSecuritySupportFormDTO;
import com.pccw.springframework.web.servlet.support.SecuritySupport;

/**
 * Databinding-aware JSP tag for rendering an HTML '<code>input</code>'
 * element with a '<code>type</code>' of '<code>checkbox</code>'.
 *
 * <p>May be used in one of three different approaches depending on the
 * type of the {@link #getValue bound value}.
 *
 * <h3>Approach One</h3>
 * When the bound value is of type {@link Boolean} then the '<code>input(checkbox)</code>'
 * is marked as 'checked' if the bound value is <code>true</code>. The '<code>value</code>'
 * attribute corresponds to the resolved value of the {@link #setValue(Object) value} property.
 * <h3>Approach Two</h3>
 * When the bound value is of type {@link Collection} then the '<code>input(checkbox)</code>'
 * is marked as 'checked' if the configured {@link #setValue(Object) value} is present in
 * the bound {@link Collection}.
 * <h3>Approach Three</h3>
 * For any other bound value type, the '<code>input(checkbox)</code>' is marked as 'checked'
 * if the the configured {@link #setValue(Object) value} is equal to the bound value.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
public class CheckboxTag extends org.springframework.web.servlet.tags.form.CheckboxTag {

	/**
	 * 
	 */	
	private static final long serialVersionUID = 6707193736196219995L;

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
