package com.bomwebportal.dto.report;

import java.io.Serializable;

public class ReportSetDetailDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = 3297397049614893408L;

	private String rptType = null;
	private String rptName = null;
	private int rptSeq = -1;
	private String lang = null;
	private String isEditable = null;
	private String isPrintSignature = null;
	private String isPrintTerms = null;
	private String storePath = null;
	private String fileName = null;
	private String srcStorePath = null;
	private String srcFileName = null;
	private boolean reGen = false;

	
	public String getRptType() {
		return rptType;
	}

	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

	public String getRptName() {
		return rptName;
	}

	public void setRptName(String rptName) {
		this.rptName = rptName;
	}

	public int getRptSeq() {
		return rptSeq;
	}

	public void setRptSeq(int rptSeq) {
		this.rptSeq = rptSeq;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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

	public String getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}

	public String getIsPrintSignature() {
		return isPrintSignature;
	}

	public void setIsPrintSignature(String isPrintSignature) {
		this.isPrintSignature = isPrintSignature;
	}

	public String getIsPrintTerms() {
		return isPrintTerms;
	}

	public void setIsPrintTerms(String isPrintTerms) {
		this.isPrintTerms = isPrintTerms;
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
