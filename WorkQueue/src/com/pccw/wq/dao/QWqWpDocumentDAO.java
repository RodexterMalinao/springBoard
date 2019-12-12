package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface QWqWpDocumentDAO extends DaoBase {

	public String getCreateBy();

	public String getLastUpdBy();

	public String getWqWpAssgnId();

	public String getWqWpAssgnDocId();

	public String getWqDocumentId();

	public String getPrintCount();

	public String getPrintDate();

	public String getDownloadDate();

	public String getCreateDate();

	public String getLastUpdDate();

	public OraDate getPrintDateORACLE();

	public OraDate getDownloadDateORACLE();

	public OraDate getCreateDateORACLE();

	public OraDate getLastUpdDateORACLE();

	public void setCreateBy(String pCreateBy);

	public void setLastUpdBy(String pLastUpdBy);

	public void setWqWpAssgnId(String pWqWpAssgnId);

	public void setWqWpAssgnDocId(String pWqWpAssgnDocId);

	public void setWqDocumentId(String pWqDocumentId);

	public void setPrintCount(String pPrintCount);

	public void setPrintDate(String pPrintDate);

	public void setDownloadDate(String pDownloadDate);

	public void setCreateDate(String pCreateDate);

	public void setLastUpdDate(String pLastUpdDate);

}