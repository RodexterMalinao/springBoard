package com.bomwebportal.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobBean extends QuartzJobBean {

	protected final Log logger = LogFactory.getLog(getClass());

	private AutoProcess autoProcess;

	public void executeInternal(JobExecutionContext context) {

		try {
			autoProcess.trigger();
		} catch (Exception e) {			
			logger.error(e);
			if(e.getClass().equals(JobExecutionException.class)){
				JobExecutionException e2 = new JobExecutionException(e);				
				logger.info("Job Exception detail message: "+ e2.getMessage());
				logger.info("Refire job immediately");
				e2.refireImmediately();
			}
			
		}
	}

	public AutoProcess getAutoProcess() {
		return autoProcess;
	}

	public void setAutoProcess(AutoProcess autoProcess) {
		this.autoProcess = autoProcess;
	}

}
