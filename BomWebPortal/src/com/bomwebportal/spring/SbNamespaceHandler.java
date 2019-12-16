package com.bomwebportal.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SbNamespaceHandler extends NamespaceHandlerSupport {
	public SbNamespaceHandler() {
	}

	public void init() {

		registerBeanDefinitionParser("resources",
				new ResourcesBeanDefinitionParser());
	}
}