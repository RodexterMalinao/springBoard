package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface QWqWpSearchVDAO extends DaoBase {

	public String getSbId();

	public String getSbShopCd();

	public String getTypeOfSrv();

	public String getSrvId();

	public String getBomStatus();

	public String getBomLegacyOrdStatus();

	public String getWqSerial();

	public String getWqType();

	public String getWqTypeDesc();

	public String getWqSubtype();

	public String getWqSubTypeDesc();

	public String getAssignee();

	public String getReasonCd();

	public String getStatusCd();

	public String getStatusDesc();

	public String getWqId();

	public String getSbDtlId();

	public String getBomOcId();

	public String getBomDtlId();
	
	public String getBomDtlSeq();

	public String getWqWpAssgnId();

	public String getWpId();

	public String getWpDesc();
	
	public String getSrd();

	public String getReceiveDate();

	public OraDate getSrdORACLE();

	public OraDate getReceiveDateORACLE();

	public void setSbId(String pSbId);

	public void setSbShopCd(String pSbShopCd);

	public void setTypeOfSrv(String pTypeOfSrv);

	public void setSrvId(String pSrvId);

	public void setBomStatus(String pBomStatus);

	public void setBomLegacyOrdStatus(String pBomLegacyOrdStatus);

	public void setWqSerial(String pWqSerial);

	public void setWqType(String pWqType);

	public void setWqTypeDesc(String pWqTypeDesc);

	public void setWqSubtype(String pWqSubtype);

	public void setWqSubTypeDesc(String pWqSubTypeDesc);

	public void setAssignee(String pAssignee);

	public void setReasonCd(String pReasonCd);

	public void setStatusCd(String pStatusCd);

	public void setStatusDesc(String pStatusDesc);

	public void setWqId(String pWqId);

	public void setSbDtlId(String pSbDtlId);

	public void setBomOcId(String pBomOcId);

	public void setBomDtlId(String pBomDtlId);

	public void setBomDtlSeq(String pBomDtlSeq);

	public void setWqWpAssgnId(String pWqWpAssgnId);

	public void setWpId(String pWpId);

	public void setWpDesc(String pWpDesc);

	public void setSrd(String pSrd);

	public void setReceiveDate(String pReceiveDate);

}