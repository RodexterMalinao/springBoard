package com.bomwebportal.lts.service.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.pccw.rpt.service.ReportService;

public class LtsJasperReport implements Serializable {

	private static final long serialVersionUID = 5175310539802595098L;

	static final int STORE_CONDITION_NO_STORE = 0;
	static final int STORE_CONDITION_ALWAYS = 1;
	static final int STORE_CONDITION_SIGN_OFF = 2;

	static final String RPT_SEQ_EYE_ONLY = "RPT_SEQ_EYE_ONLY";
	static final String RPT_SEQ_DEL_ONLY = "RPT_SEQ_DEL_ONLY";
	static final String RPT_SEQ_EYE_RESTEL_SAME_CUST = "RPT_SEQ_EYE_RESTEL_SAME_CUST";
	static final String RPT_SEQ_EYE_RESTEL_DIFF_CUST = "RPT_SEQ_EYE_RESTEL_DIFF_CUST";
	static final String RPT_SEQ_EYE_ONLY_WQ = "RPT_SEQ_EYE_ONLY_WQ";
	static final String RPT_SEQ_RESTEL_ONLY_WQ = "RPT_SEQ_RESTEL_ONLY_WQ";
	static final String RPT_SEQ_EYE_RESTEL_SAME_CUST_WQ = "RPT_SEQ_EYE_RESTEL_SAME_CUST_WQ";
	static final String RPT_SEQ_EYE_RESTEL_DIFF_CUST_WQ = "RPT_SEQ_EYE_RESTEL_DIFF_CUST_WQ";
	static final String RPT_SEQ_AMEND_COVER_SHEET = "RPT_SEQ_AMEND_COVER_SHEET";
	static final String RPT_SEQ_ORDER = "RPT_SEQ_ORDER";
	static final String RPT_SEQ_PORT_IN = "RPT_PORT_IN";
	static final String RPT_SEQ_EYE_TDO = "RPT_SEQ_EYE_TDO";
	static final String RPT_SEQ_DEL_TDO = "RPT_SEQ_DEL_TDO";
	static final String RPT_SEQ_0060_TDO = "RPT_SEQ_0060_TDO";
	static final String RPT_SEQ_NOWTV_TDO = "RPT_SEQ_NOWTV_TDO";
	static final String RPT_SEQ_PRODUCT_SPEC_EYE = "RPT_SEQ_PRODUCT_SPEC_EYE";
	static final String RPT_SEQ_UPGRADE_SUFFIX = "_UPG";
	static final String RPT_SEQ_2ND_DEL_SUFFIX = "_2DEL";
	static final String RPT_SEQ_RECONTRACT = "RPT_SEQ_RECONTRACT";

	
	private String sbDtlType;
	private String rptName;
	private boolean printCustomerCopy;
	private boolean generatePageNumber;
	private int storeCondition;
	private String outputFileName;
	private String storagePath;
	private boolean replacementInd = false;
	

	public LtsJasperReport(String pSbDtlType, String pRptName, boolean pPrintCustomerCopy, boolean pGeneratePageNumber, int pStoreCondition, 
			String pOutputFileName, String pStoragePath, boolean pReplacementInd) {
		super();
		this.sbDtlType = pSbDtlType;
		this.rptName = pRptName;
		this.printCustomerCopy = pPrintCustomerCopy;
		this.generatePageNumber = pGeneratePageNumber;
		this.storeCondition = pStoreCondition;
		this.outputFileName = pOutputFileName;
		this.storagePath = pStoragePath;
		this.replacementInd = pReplacementInd;
	}

	public String getSbDtlType() {
		return this.sbDtlType;
	}

	public String getRptName() {
		return this.rptName;
	}

	public boolean isGeneratePageNumber() {
		return this.generatePageNumber;
	}

	public String saveOutputFile(boolean pIsSignedOff, boolean pIsCustomerSigned, String pSbOrderId, int pExportType, byte[] pOutputFile) {
		if (this.storeCondition == STORE_CONDITION_NO_STORE) {
			return null;
		} else if (this.storeCondition == STORE_CONDITION_SIGN_OFF) {
			if (!pIsSignedOff 
					&& !StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_PORT_IN, this.sbDtlType)
					&& !StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_RECONTRACT, this.sbDtlType)) {
				return null;
			}
		}
		
		// save pdf file into server - start
		try {
			File directory = null;
			directory = new File(storagePath + pSbOrderId);
			if (!directory.exists()) {
				directory.mkdir();
			}
			
			String fileName = this.getFileName(pExportType, false);
			File f = new File(directory, fileName);
			if (f.exists() && this.storeCondition == STORE_CONDITION_SIGN_OFF && !pIsCustomerSigned) {
				return f.getAbsolutePath();
			}
			FileOutputStream fout = new FileOutputStream(f);
			fout.write(pOutputFile);
			fout.flush();
			fout.close();
			
			return f.getAbsolutePath();
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public FastByteArrayOutputStream openOutputFile(String pSbOrderId, int pExportType) {
		
		try {
			InputStream inputStream = new FileInputStream(this.storagePath + pSbOrderId + "//" + this.getFileName(pExportType, false));
			FastByteArrayOutputStream outStream = new FastByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len;
			
			while ((len = inputStream.read(buf)) > 0){
				outStream.write(buf, 0, len);
			}
			inputStream.close();
	        return outStream;
		} catch (FileNotFoundException e) {
			return null;
		} catch(IOException e) {
			return null;
		}
	}

	public String getFileName(int pExportType, boolean pIsCustomerCopy) {
		String filename = this.outputFileName;
		if (pIsCustomerCopy) {
			filename += "_CUST_COPY";
		}
		if (pExportType == ReportService.EXPORT_TYPE_PDF) {
			filename += ".pdf";
		} else if (pExportType == ReportService.EXPORT_TYPE_RTF) {
			filename += ".rtf";
		} else if (pExportType == ReportService.EXPORT_TYPE_RPT_DTO_JAVA_OBJ) {
			filename += ".obj";
		}
		return filename;
	}
	
	public boolean isPrintCustomerCopy() {
		return this.printCustomerCopy;
	}
	
	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String pStoragePath) {
		storagePath = pStoragePath;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public boolean isReplacementInd() {
		return replacementInd;
	}

	public int getStoreCondition() {
		return storeCondition;
	}

	public void setStoreCondition(int storeCondition) {
		this.storeCondition = storeCondition;
	}
}
