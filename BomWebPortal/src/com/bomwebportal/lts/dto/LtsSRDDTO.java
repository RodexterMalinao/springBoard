package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.lts.dto.srvAccess.BmoPermitDetail;
import com.bomwebportal.lts.util.LtsAppointmentHelper;

public class LtsSRDDTO implements Serializable, Comparable<LtsSRDDTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1015304192718007256L;

	private DateTime baseDate;
	private DateTime sRDate;
	private int leadTime = 0;
	private int skippedTime = 0;
	private boolean excludeSat = false;
	private boolean excludeSunPH = false;
	private String info;
	private String reasonCd;
	private boolean backDateAppln;
	private BmoPermitDetail bmoPermit;
	
	public LtsSRDDTO(){
		setDatesToToday();
	}

	public LtsSRDDTO(String startDate){
		if(startDate != null){
			try {
				this.sRDate = DateTime.parse(startDate, DateTimeFormat.forPattern("dd/MM/yyyy"));
				this.baseDate = DateTime.parse(startDate, DateTimeFormat.forPattern("dd/MM/yyyy"));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				setDatesToToday();
			}
		}
	}

	public LtsSRDDTO(String startDate, int leadTime, String reasonCd){
		this(startDate);
		this.info = LtsAppointmentHelper.earliestSrdReason.get(reasonCd);
		this.reasonCd = reasonCd;
		setLeadTime(leadTime);
	}
	
	public LtsSRDDTO(String startDate, int leadTime, String info, String reasonCd){
		this(startDate);
		this.info = info;
		this.reasonCd = reasonCd;
		setLeadTime(leadTime);
	}
	
	/*legacy*/
	public Calendar getDate(){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.sRDate.toDate());
		return calendar;
	}
	
	/*legacy*/
	public void setDate(Calendar date){
		setSRDate(new DateTime(date.getTime()));
	}
	
	private void setDatesToToday(){
		this.sRDate = new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth(), 0, 0, 0);
		this.baseDate = new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth(), 0, 0, 0);
	}
	
	public DateTime getSRDate() {
		return sRDate;
	}
	public void setSRDate(DateTime date) {
		if(this.sRDate == null && this.baseDate == null){
			this.baseDate = date;
		}
		this.sRDate = date;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
		this.sRDate = new DateTime(baseDate);
		addDate(leadTime);
	}
	public void addLeadTime(int amount){
		this.leadTime += amount;
		addDate(amount);
	}
	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public int getSkippedTime() {
		return skippedTime;
	}

	public void setSkippedTime(int skippedTime) {
		this.skippedTime = skippedTime;
	}

	public void addSkippedTime(int skipped){
		skippedTime += skipped;
		addDate(skipped);
	}
	
	public void addSkippedTime(){
		skippedTime += 1;
		addDate(1);
	}
	
	public void addDate(int num){
		sRDate = sRDate.plusDays(num);
	}
	
	public String getDateString(){
		return sRDate.toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
	}

	public int compareTo(LtsSRDDTO o) {
		return (this.leadTime + this.skippedTime) - (o.getLeadTime() + o.getSkippedTime());
	}

	public boolean isExcludeSat() {
		return excludeSat;
	}

	public void setExcludeSat(boolean excludeSat) {
		this.excludeSat = excludeSat;
	}

	public boolean isExcludeSunPH() {
		return excludeSunPH;
	}

	public void setExcludeSunPH(boolean excludeSunPH) {
		this.excludeSunPH = excludeSunPH;
	}
	
	public String toString(){
		return getDateString();
		
	}

	public boolean isBackDateAppln() {
		return backDateAppln;
	}

	public void setBackDateAppln(boolean backDateAppln) {
		this.backDateAppln = backDateAppln;
	}

	public BmoPermitDetail getBmoPermit() {
		return bmoPermit;
	}

	public void setBmoPermit(BmoPermitDetail bmoPermit) {
		this.bmoPermit = bmoPermit;
	}

	
}
