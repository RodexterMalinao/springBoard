package com.bomltsportal.configuration;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class BomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	Properties mergedProperties;

	public Properties getMergedProperties() throws IOException {
		if (mergedProperties == null) {
			mergedProperties = mergeProperties();
		}
		return mergedProperties;
	}
	
	public String getPropertyByEnvironment(String pPropertyName) throws Exception {
		return this.getMergedProperties().getProperty(
				this.getMergedProperties().getProperty("environment") + "." + pPropertyName);
	}
}
