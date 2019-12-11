package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public interface QWorkQueueDAO extends DaoBase {

	public String getSbId();

	public String getSbShopCd();

	public String getTypeOfSrv();

	public String getSrvId();

	public String getBomStatus();

	public String getBomLegacyOrdStatus();

	public String getCreateBy();

	public String getLastUpdBy();

	public String getWqId();

	public OraNumberInsertValueFromSelect getWqIdORACLE();
	
	public String getSbDtlId();

	public String getBomOcId();

	public String getBomDtlId();

	public String getBomDtlSeq();

	public String getSrd();

	public String getCreateDate();

	public String getLastUpdDate();

	public OraDate getSrdORACLE();

	public OraDate getCreateDateORACLE();

	public OraDate getLastUpdDateORACLE();

	public void setSbId(String pSbId);

	public void setSbShopCd(String pSbShopCd);

	public void setTypeOfSrv(String pTypeOfSrv);

	public void setSrvId(String pSrvId);

	public void setBomStatus(String pBomStatus);

	public void setBomLegacyOrdStatus(String pBomLegacyOrdStatus);

	public void setCreateBy(String pCreateBy);

	public void setLastUpdBy(String pLastUpdBy);

	public void setWqId(String pWqId);

	public void setWqIdORACLE(OraNumberInsertValueFromSelect pWqId);
	
	public void setSbDtlId(String pSbDtlId);

	public void setBomOcId(String pBomOcId);

	public void setBomDtlId(String pBomDtlId);

	public void setBomDtlSeq(String pBomDtlSeq);

	public void setSrd(String pSrd);

	public void setCreateDate(String pCreateDate);

	public void setLastUpdDate(String pLastUpdDate);
	
	public String getRelatedSrvType();

	public void setRelatedSrvType(String pRelatedSrvType);

	public String getRelatedSrvNum();

	public void setRelatedSrvNum(String pRelatedSrvNum);
	
	public String getSbActvId();
	
	public void setSbActvId(String pSbActvId);

}