package com.bomwebportal.dto.report;

import java.io.Serializable;

public class ReportSetDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = -1409567547773252580L;

	private String lob = null;
	private String channelId = null;
	private String rptSet = null;
	private String storePath = null;
	private String fileName = null;
	private String srcStorePath = null;
	private String srcFileName = null;
	private String permission = null;
	private String copies = null;
	private boolean encrypt = false;
	private boolean reGen = false;
	

	private ReportSetDetailDTO[] rptDtls = null;
	
	
	public String getLkupKey() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.lob);
		sb.append("|");
		sb.append(this.channelId);
		sb.append("|");
		sb.append(this.rptSet);
		return sb.toString();
	}
	
	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getRptSet() {
		return rptSet;
	}

	public void setRptSet(String rptSet) {
		this.rptSet = rptSet;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getCopies() {
		return copies;
	}

	public void setCopies(String copies) {
		this.copies = copies;
	}

	public ReportSetDetailDTO[] getRptDtls() {
		return rptDtls;
	}

	public void setRptDtls(ReportSetDetailDTO[] rptDtls) {
		this.rptDtls = rptDtls;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public String getSrcStorePath() {
		return srcStorePath;
	}

	public void setSrcStorePath(String srcStorePath) {
		this.srcStorePath = srcStorePath;
	}

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public boolean isReGen() {
		return reGen;
	}

	public void setReGen(boolean reGen) {
		this.reGen = reGen;
	}
}
