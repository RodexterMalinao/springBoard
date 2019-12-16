package com.bomwebportal.ims.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.bomwebportal.exception.ImsAfException;
import com.bomwebportal.exception.ImsEmptySignatureException;
import com.bomwebportal.ims.dto.report.RptIms3PartyCreditCardDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;

public interface ImsReportService {	
	public void viewPdfReports(OutputStream pOutputStream, String orderId);
	public void generatePdfReports(ArrayList<Object> pReportArrList, OutputStream pOutputStream, String pLang, 
			String orderId, Boolean Signed, String disMode, Boolean isCC, Boolean isPT, Boolean isDs, Boolean cOrder, Boolean isPreview, Boolean signOffed) throws ImsAfException, ImsEmptySignatureException;
	public void generatePdfReports(ArrayList<Object> pReportArrList, OutputStream pOutputStream, String pLang, 
			String orderId, Boolean Signed, String disMode, Boolean isCC, Boolean isPT, Boolean isDs,  Boolean cOrder, Boolean isPreview) throws ImsAfException, ImsEmptySignatureException;
	public boolean isDBSignOffEd(String orderId);
	// martin 20160422, third party credit card form setup
	public void set3rdPartyCreditCardForm(RptIms3PartyCreditCardDTO thirdPartyCcDTO, HashMap<String, String> db, OrderImsUI order);
}
