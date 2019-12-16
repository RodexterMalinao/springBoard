package com.bomwebportal.configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.bomwebportal.util.BomWebPortalConstant;

import java.util.Properties;
import java.io.IOException;

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
				this.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV) + "." + pPropertyName);
	}
}
