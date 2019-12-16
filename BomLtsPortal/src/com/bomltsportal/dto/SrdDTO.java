package com.bomltsportal.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SrdDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1015304192718007256L;

	private Calendar date;
	private int leadTime = 0;
	private int skippedTime = 0;
	private String info;
	private String reasonCd;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	public SrdDTO(){
		date = GregorianCalendar.getInstance();
	}

	public SrdDTO(Date date){
		this();
		this.date.setTime(date);
	}
	
	public SrdDTO(String date){
		this();
		if(date != null){
			try {
				this.date.setTime(df.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
				this.date = GregorianCalendar.getInstance();
			}
		}
	}
	
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
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
		if(this.leadTime == 0){
			addDate(leadTime);
		}
		this.leadTime = leadTime;
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
		date.add(GregorianCalendar.DATE, num);
	}
	
	public String getDateString(){
		return df.format(date.getTime());
	}

	public String getDateString(String pattern){
		df.applyPattern(pattern);
		return df.format(date.getTime());
	}
}
