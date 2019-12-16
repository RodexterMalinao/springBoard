package com.bomwebportal.lts.dao.apn;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraBLOB;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebPipbApnFileDAO extends DaoBaseImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4945789506254183227L;
	private String fileName; //BOMWEB_PIPB_APN_FILE.FILE_NAME  
	private OraNumber batchSeq; //BOMWEB_PIPB_APN_FILE.BATCH_SEQ
	private String fileStatus; //BOMWEB_PIPB_APN_FILE.FILE_STATUS
	private String createBy; //BOMWEB_PIPB_APN_FILE.CREATE_BY
	private OraDate createDate= new OraDateCreateDate(); //BOMWEB_PIPB_APN_FILE.CREATE_DATE
	private String lastUpdBy; //BOMWEB_PIPB_APN_FILE.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); //BOMWEB_PIPB_APN_FILE.LAST_UPD_DATE
	private OraBLOB serialFile; //BOMWEB_PIPB_APN_FILE.SERIAL_FILE
	private String failedReason; //BOMWEB_PIPB_APN_FILE.FAILED_REASON
	
	public BomwebPipbApnFileDAO() { 
		primaryKeyFields = new String[] {"batchSeq", "fileName"};  
	}	
	public String getTableName() {
		return "BOMWEB_PIPB_APN_FILE";
	}
	public String getFileName() { 
		return this.fileName; 
	}
	public String getFileStatus() { 
		return this.fileStatus; 
	}
	public String getCreateBy() { 
		return this.createBy; 
	}
	public String getLastUpdBy() { 
		return this.lastUpdBy; 
	}
	public String getFailedReason() { 
		return this.failedReason; 
	}
	public String getBatchSeq() { 
		return this.batchSeq != null ? this.batchSeq.toString() : null; 
	}
	public byte[] getSerialFile() { 
		return this.serialFile != null ? this.serialFile.getValue() : null; 
	}
	public String getCreateDate() { 
		return this.createDate != null ? this.createDate.toString() : null; 
	}
	public String getLastUpdDate() { 
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null; 
	}
	public OraDate getCreateDateORACLE() { 
		return this.createDate; 
	}
	public OraDate getLastUpdDateORACLE() { 
		return this.lastUpdDate; 
	}
	public void setFileName(String pFileName) { 
		this.fileName = pFileName; 
	}
	public void setFileStatus(String pFileStatus) { 
		this.fileStatus = pFileStatus; 
	}
	public void setCreateBy(String pCreateBy) { 
		this.createBy = pCreateBy; 
	}
	public void setLastUpdBy(String pLastUpdBy) { 
		this.lastUpdBy = pLastUpdBy; 
	}
	public void setFailedReason(String pFailedReason) { 
		this.failedReason = pFailedReason; 
	}
	public void setBatchSeq(String pBatchSeq) { 
		this.batchSeq = new OraNumber(pBatchSeq); 
	}
	public void setSerialFile(byte[] pSerialFile) { 
		this.serialFile = new OraBLOB(pSerialFile); 
	}
	public void setCreateDate(String pCreateDate) { 
		this.createDate = new OraDateCreateDate(pCreateDate); 
	}
	public void setLastUpdDate(String pLastUpdDate) { 
		this.lastUpdDate = new OraDateLastUpdDate (pLastUpdDate); 
	}
}
