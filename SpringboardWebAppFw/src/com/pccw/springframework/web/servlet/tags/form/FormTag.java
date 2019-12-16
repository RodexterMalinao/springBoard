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

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import com.pccw.springframework.web.servlet.support.SecuritySupport;

/**
 * Databinding-aware JSP tag for rendering an HTML '<code>form</code>' whose
 * inner elements are bound to properties on a <em>form object</em>.
 *
 * <p>Users should place the form object into the
 * {@link org.springframework.web.servlet.ModelAndView ModelAndView} when
 * populating the data for their view. The name of this form object can be
 * configured using the {@link #setModelAttribute "modelAttribute"} property.
 *
 * <p>The default value for the {@link #setModelAttribute "modelAttribute"}
 * property is '<code>command</code>' which corresponds to the default name
 * when using the
 * {@link org.springframework.web.servlet.mvc.SimpleFormController SimpleFormController}.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 * @see org.springframework.web.servlet.mvc.SimpleFormController
 */
public class FormTag extends org.springframework.web.servlet.tags.form.FormTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6622969738810151293L;

	/**
	 * Writes the opening part of the block	'<code>form</code>' tag and exposes
	 * the form object name in the {@link javax.servlet.jsp.PageContext}.
	 * @param pTagWriter the {@link TagWriter} to which the form content is to be written
	 * @return {@link javax.servlet.jsp.tagext.Tag#EVAL_BODY_INCLUDE}
	 */
	public int writeTagContent(TagWriter pTagWriter) throws JspException {
		try {
			 SecuritySupport.getSecurity(this.getRequestContext(), this.getCommandName());
		} catch (Exception e) {
			if (e instanceof JspException) {
				throw (JspException) e;
			}
			throw new JspException(e);
		}
		return super.writeTagContent(pTagWriter);
	}
}
