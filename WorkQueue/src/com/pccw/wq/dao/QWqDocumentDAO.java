package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public interface QWqDocumentDAO extends DaoBase {

	public String getAttachmentHost();

	public String getAttachmentPath();

	public String getCreateBy();

	public String getLastUpdBy();

	public String getWqDocumentId();

	public OraNumberInsertValueFromSelect getWqDocumentIdORACLE();

	public String getWqId();

	public String getDocumentTypeId();

	public String getCreateDate();

	public String getLastUpdDate();

	public OraDate getCreateDateORACLE();

	public OraDate getLastUpdDateORACLE();

	public void setAttachmentHost(String pAttachmentHost);

	public void setAttachmentPath(String pAttachmentPath);

	public void setCreateBy(String pCreateBy);

	public void setLastUpdBy(String pLastUpdBy);

	public void setWqDocumentIdORACLE(
			OraNumberInsertValueFromSelect pWqDocumentId);

	public void setWqDocumentId(String pWqDocumentId);

	public void setWqId(String pWqId);

	public void setDocumentTypeId(String pDocumentTypeId);

	public void setCreateDate(String pCreateDate);

	public void setLastUpdDate(String pLastUpdDate);

}