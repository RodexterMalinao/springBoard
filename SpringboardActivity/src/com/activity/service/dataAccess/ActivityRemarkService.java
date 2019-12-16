package com.activity.service.dataAccess;

import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.service.ServiceActionBase;

public interface ActivityRemarkService extends ServiceActionBase {

	public abstract void saveCurrentActvStatusRemark(String pRemarkContent, String pUser, String pActvId, String pTaskSeq);

	public abstract RemarkDTO[] getRemarksByTaskSeq(String pActvid, String pTaskSeq);

	public abstract void updateWqWpAssgnId(String pActvId, String pTaskSeq, String pWqWpAssgnId) throws DAOException;
	
	public abstract RemarkDTO[] convertWqRemarkToActvRemark(com.pccw.wq.schema.dto.RemarkDTO[] pWqRemarks);
}