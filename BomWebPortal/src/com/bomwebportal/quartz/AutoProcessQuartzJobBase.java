package com.bomwebportal.quartz;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.util.BomWebPortalConstant;

public abstract class AutoProcessQuartzJobBase extends QuartzJobBean {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	
	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		try {
			String appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			if (!"dev".equalsIgnoreCase(appEnv)) {
				if (logger.isInfoEnabled()) {
					logger.info("executeInternal() is called");
				}
				this.executeQuartzJob(ctx);
			}
		} catch (IOException e) {
			logger.error("IOException in executeInternal()", e);
			throw new JobExecutionException(e);
		}
	}
	
	protected abstract void executeQuartzJob(JobExecutionContext ctx);
}
