/*
 * Created on Dec 02, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.CodeLkupDAO;
import com.bomwebportal.dto.LookupItemDTO;


public class LtsJobsImpl implements LtsJobs  {

	protected final Log logger = LogFactory.getLog(getClass());
	
	//Injections starts
	private CodeLkupDAO jobParmLkup;
	private LtsJobsService ltsJobsService;
	
	private String [] skipHours = null;
	private List <String> skipHoursList = null;
	private long secInterval    = 0;
	private int orderAge        = DEFAULT_ORDER_AGE;
	private int pipbOrderAge    = DEFAULT_PIPB_ORDER_AGE;
	private boolean standBy     = false; 
	private Date startTime      = null;  //HHMM format
	private Date endTime        = null;  //HHMM format	
	private String jobSignature = null;
	//Injections ends
		
	private static final String STAND_BY      = "SB";
	private static final String START_TIME    = "STIM";
	private static final String END_TIME      = "ETIM";
	private static final String SKIP_HOURS    = "SKHR";
	private static final String SEC_INTERVAL  = "INTV";
	private static final String ORDER_AGE     = "OAGE";
	private static final String JOB_SIG       = "SIG";
	
	private String jobSignatureLkup = null;
	
	private static final int    DEFAULT_ORDER_AGE = 14;      // not over 30 days old
	private static final int    DEFAULT_PIPB_ORDER_AGE = 90;
	private long lastExec       = 0;
	
	public synchronized void refreshParam() throws Exception {
		logger.info("Refreshing the LTS BOM/SB status synchronization parameter..");		
		
		LookupItemDTO [] lookupItemDTO = jobParmLkup.getCodeLkup();
		for (int i = 0; lookupItemDTO != null && i < lookupItemDTO.length;i++) {
			if (STAND_BY.equals(lookupItemDTO[i].getItemKey())) {
				this.setStandBy((String)lookupItemDTO[i].getItemValue());
				logger.info("Standy mode is now " + this.getStandBy());
			} else  			
			if (START_TIME.equals(lookupItemDTO[i].getItemKey())) {
				this.setStartTime((String)lookupItemDTO[i].getItemValue());
				logger.info("Start time is now " + this.getStartTime());
			} else  			
			if (END_TIME.equals(lookupItemDTO[i].getItemKey())) {
				this.setEndTime((String)lookupItemDTO[i].getItemValue());
				logger.info("End time is now " + this.getEndTime());
			} else  
			if (SKIP_HOURS.equals(lookupItemDTO[i].getItemKey())) {
				this.setSkipHours((String)lookupItemDTO[i].getItemValue());
				logger.info("The hours to skip are now " + this.getSkipHours());
			} else  
			if (ORDER_AGE.equals(lookupItemDTO[i].getItemKey())) {
				this.setOrderAge((String)lookupItemDTO[i].getItemValue());
				logger.info("Order must not be over " + this.getOrderAge() + " days old.");
			} else  
			if (SEC_INTERVAL.equals(lookupItemDTO[i].getItemKey())) {
				this.setSecInterval((String)lookupItemDTO[i].getItemValue());
				logger.info("Run interval is now " +  this.getSecInterval());
			} else  
			if (JOB_SIG.equals(lookupItemDTO[i].getItemKey())) {
				jobSignatureLkup = (String)lookupItemDTO[i].getItemValue();
				logger.info("Job Signature is " +  jobSignatureLkup);				
			}			
		}
	}
	
	public synchronized void start() throws Exception {	
		try {
			logger.debug("start()....");
			if (this.skipIt()) {
				logger.info("Skipping...");
				return;
			}
			
			ltsJobsService.setOrderAge(orderAge);
			ltsJobsService.setPipbOrderAge(pipbOrderAge);
			
			ltsJobsService.exec();

		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	
	private boolean skipIt() {
		if (this.isStandBy()) { 
			return true;
		}
		
		if (jobSignatureLkup == null) {
			this.setJobSignatureFromLookup();
		}
		
		if (jobSignatureLkup == null || ! jobSignatureLkup.equals(jobSignature)) {
			logger.info("Job Signature not matching....");
			return true;
		}
		
		
		Calendar now = Calendar.getInstance();
		
		if (skipHours != null
				&& skipHours.length > 0
				&& skipHoursList.contains(
						String.valueOf(now.get(Calendar.HOUR_OF_DAY)))) { 	//Is this hour ?
			logger.info("Skipping this hour of day "
					+ now.get(Calendar.HOUR_OF_DAY));
			return true;
		} else if (secInterval != 0 && (long) ((now.getTimeInMillis() - lastExec) / 1000) < secInterval) { //Exceed interval? 
			logger.info("Skipping! Not yet exceeded the time interval " + secInterval);
			return true;
		} else {
			String startHHMM = this.getStartTime();
			if (startHHMM != null
					&& (now.get(Calendar.HOUR_OF_DAY) * 100 + now.get(Calendar.MINUTE)) 
					      < Integer.parseInt(startHHMM)) {  //Not yet the start time?
				logger.info("Skipping! Not yet the start time of " + startHHMM);
				return true;
			} 
			
			String endHHMM   = this.getEndTime();
			if (endHHMM != null
					&& (now.get(Calendar.HOUR_OF_DAY) * 100 + now.get(Calendar.MINUTE)) 
					      > Integer.parseInt(endHHMM)) { //Passed the end time
				logger.info("Skipping! Passed the end time of " + endHHMM);
				return true;
			} 
		}		
		
		lastExec = now.getTimeInMillis();
		
		return false;
	}
	

	private String getTimeInStr(Date pTime) {
		try {
			return new SimpleDateFormat("HHmm").format(pTime);
		} catch (Exception e) {
			return null;
		}
	}
	
	private Date getTimeFromStr(String pTime) {
		try {
			return new SimpleDateFormat("HHmm").parse(pTime);			
		} catch (Exception e) {
			return null;
		}
	}	
	
	private void setJobSignatureFromLookup() {
		try {
			LookupItemDTO[] lookupItemDTO = jobParmLkup.getCodeLkup();
			for (int i = 0; lookupItemDTO != null && i < lookupItemDTO.length; i++) {
				if (JOB_SIG.equals(lookupItemDTO[i].getItemKey())) {
					jobSignatureLkup = (String) lookupItemDTO[i].getItemValue();
					logger.info("Job Signature is " + jobSignatureLkup);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isStandBy() {
		return standBy;
	}

	public String getStandBy() {
		return standBy?"Y":"N";
	}

	public void setStandBy(String pStandBy) {
		this.standBy = "Y".equals(pStandBy)?true:false;;
	}

	public String getStartTime() {
		return getTimeInStr(this.startTime);
	}

	public void setStartTime(String pStartTime) {
		this.startTime = getTimeFromStr(pStartTime);
	}

	public String getEndTime() {
		return getTimeInStr(this.endTime);
	}

	public void setEndTime(String pEndTime) {
		this.endTime = getTimeFromStr(pEndTime);
	}

	public String getSkipHours() {
		return StringUtils.join(this.skipHours,';');
	}

	public void setSkipHours(String pSkipHours) {
		this.skipHours = StringUtils.split(pSkipHours,';');
		if (this.skipHours != null && this.skipHours.length > 0) {
			skipHoursList = Arrays.asList(skipHours);			
		} else {
			skipHoursList = null;
		}
	}

	public long getSecInterval() {
		return secInterval;
	}

	public void setSecInterval(String secInterval) {
		try {
			this.secInterval = Long.parseLong(secInterval);
		} catch (Exception e) {
		    this.secInterval = 0;			
		}
	}

	public String getOrderAge() {
		return String.valueOf(orderAge);
	}

	public void setOrderAge(String orderAge) {
		try {
			this.orderAge = Integer.valueOf(orderAge);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.orderAge = DEFAULT_ORDER_AGE;
		}
	}
	
	public String getPipbOrderAge() {
		return String.valueOf(pipbOrderAge);
	}

	public void setPipbOrderAge(String pipbOrderAge) {
		try {
			this.pipbOrderAge = Integer.valueOf(pipbOrderAge);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.pipbOrderAge = DEFAULT_PIPB_ORDER_AGE;
		}
	}
	
	public CodeLkupDAO getJobParmLkup() {
		return jobParmLkup;
	}

	public void setJobParmLkup(CodeLkupDAO jobParmLkup) {
		this.jobParmLkup = jobParmLkup;
	}

	public LtsJobsService getLtsJobsService() {
		return ltsJobsService;
	}

	public void setLtsJobsService(LtsJobsService ltsJobsService) {
		this.ltsJobsService = ltsJobsService;
	}

	public String getJobSignature() {
		return jobSignature;
	}

	public void setJobSignature(String jobSignature) {
		this.jobSignature = jobSignature;
	}



}
