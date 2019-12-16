package com.bomwebportal.exception;


public class InsufficientPrivilegeException extends Exception {

	private static final long serialVersionUID = 7501527339463365590L;

	public InsufficientPrivilegeException() {
		super();
	}
	
	public InsufficientPrivilegeException(String pFunctionId, String pSecurity) {
		super("Insufficent Privileges to run [" + pFunctionId + "] with privileges: " + pSecurity);
	}
}
