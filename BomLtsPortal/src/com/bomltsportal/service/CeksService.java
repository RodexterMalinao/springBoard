package com.bomltsportal.service;

import java.util.List;

import com.bomwebportal.exception.DAOException;

public interface CeksService {
	
	public String initCeks(String srvType, String locale, String conflictName, String resource, String wipInd,	String onpInd, String prepayInd, String amt, String refNo, String order_id, String environment);
	
	public void updateCTR(String order_id, String reference_no, String ret_code, String status, String card_pan, String ret_parm, String exp_date) throws DAOException;
	
	public List<String> checkCTR(String order_id, String status, String ret_code) throws DAOException;
	public boolean IsCeksReady();

}
