package com.bomwebportal.sso.event;

import org.springframework.context.ApplicationEvent;

import com.bomwebportal.sso.SSOAuthContext;

public class SSOAuthenticationSuccessEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4943591827030276617L;

	public SSOAuthenticationSuccessEvent(SSOAuthContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SSOAuthContext getSSOAuthContext() {
		return (SSOAuthContext)super.getSource();
	}
}
