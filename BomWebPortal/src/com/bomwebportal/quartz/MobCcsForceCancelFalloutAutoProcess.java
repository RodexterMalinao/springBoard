package com.bomwebportal.quartz;

import org.quartz.JobExecutionContext;

import com.bomwebportal.mob.ccs.service.MobCcsForceCancelService;

public class MobCcsForceCancelFalloutAutoProcess extends AutoProcessQuartzJobBase {
	private MobCcsForceCancelService mobCcsForceCancelService;
	
	public MobCcsForceCancelService getMobCcsForceCancelService() {
		return mobCcsForceCancelService;
	}

	public void setMobCcsForceCancelService(
			MobCcsForceCancelService mobCcsForceCancelService) {
		this.mobCcsForceCancelService = mobCcsForceCancelService;
	}

	@Override
	protected void executeQuartzJob(JobExecutionContext ctx) {
		this.mobCcsForceCancelService.forceCancelFalloutOrder();
		this.mobCcsForceCancelService.forceCancelDraftOrder();
		this.mobCcsForceCancelService.forceCancelPreorderOrder();
	}
}
