package com.bomwebportal.service;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

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
}
