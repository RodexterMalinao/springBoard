package com.bomwebportal.quartz;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;

public abstract class AutoProcessBase {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	
	/**
	 * @return the propertyConfigurer
	 */
	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	/**
	 * @param propertyConfigurer the propertyConfigurer to set
	 */
	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}
	/**
	 * Environment checking before triggering auto process
	 * @throws DAOException
	 */
	public void execute() throws DAOException {
		try {
			
			String appEnv = propertyConfigurer.getMergedProperties().getProperty(
					BomWebPortalConstant.APP_ENV);
		
			logger.info("Environment: " + appEnv);
			
			if (!"dev".equalsIgnoreCase(appEnv)) {
				trigger();
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}
	}
	/**
	 * The execution process
	 */
	protected abstract void trigger() throws DAOException;
	
}
