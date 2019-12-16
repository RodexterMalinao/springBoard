package com.bomltsportal.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.dto.CsPortalResponseDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.CsPortalService.CsPortalRes;
import com.bomltsportal.dao.ConnectionCheckDAO;

public class ConnectionCheckServiceImpl implements ConnectionCheckService {

	private final Log logger = LogFactory.getLog(getClass());
	private ConnectionCheckDAO connectionCheckDAO;
	private String wsclient;
	private CeksService ceksService;
	private CsPortalService csPortalService;
	private String dataFilePath;
	
	
	public void setCeksService(CeksService ceksService) {
		this.ceksService = ceksService;
	}

	public void setWsclient(String wsclient) {
		this.wsclient = wsclient;
	}

	public ConnectionCheckDAO getConnectionCheckDAO() {
		return connectionCheckDAO;
	}

	public void setConnectionCheckDAO(ConnectionCheckDAO connectionCheckDAO) {
		this.connectionCheckDAO = connectionCheckDAO;
	}

	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}

	public boolean IsBomWebPortalDSConnected() {
		
		try {
		
			this.connectionCheckDAO.QueryBomWebPortalDS();
			
		} catch (Exception e) {
			logger.error("bomWebPortalDS_DataSource connection failed",e);
			return false;
		} 
		logger.info("bomWebPortalDS_DataSource connected!");
		return true;
		
		
	}

	public boolean IsBomDSConnnected() {
		
		try {	
		
			this.connectionCheckDAO.QueryBomDS();
			
		} catch (Exception e) {
			logger.error("bomDS_DataSource connection failed", e);
			return false;
		} 
		logger.info("bomDS_DataSource connected!");
		return true;
		
		
	}

	public boolean IsUamsDSConnected() {

		try {	
		
			this.connectionCheckDAO.QueryUamsDS();
			
		} catch (Exception e) {
			logger.error("uamsDS_DataSource connection failed",e);
			return false;
		} 
		logger.info("uamsDS_DataSource connected!");
		return true;
		
		
	}

	public boolean IsCeksReady() {
		return this.ceksService.IsCeksReady();
	}
	
	public boolean IsDataFileReady(){
		
	Writer writer = null;

    Date date = new Date();

	try {
	    writer = new BufferedWriter(new OutputStreamWriter(
	          new FileOutputStream(dataFilePath + "lts_check.txt"), "utf-8"));
	    writer.write(date.toString());
	    writer.close();
	    return true;
	} catch (IOException ex) {
		logger.error(ex);
		return false;
	}
	}

	public String getDataFilePath() {
		return dataFilePath;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public boolean IsCspReady() {
		CsPortalResponseDTO response = csPortalService.checkCsIdForTheClubAndHkt("", "", "", "", "");
		
		return !CsPortalResponseDTO.RETURN_FATAL.equals(response.getRtnCd());
	}
	
}
