package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.activity.dao.ActivityDetailDAOImpl;
import com.activity.dao.dataAccess.ActivityRemarkDAO;
import com.activity.util.ActivityConstants;
import com.activity.util.ActivityHelper;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;
import com.pccw.util.spring.SpringApplicationContext;

public class ActivityRemarkServiceImpl extends ServiceActionImplBase implements ActivityRemarkService {

	public ActivityRemarkServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "taskSeq", "statusSeq"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "taskSeq", "statusSeq", "rmkSeq"));
		this.selectOrderBy = "ACTV_ID, TASK_SEQ, STATUS_SEQ, RMK_SEQ";
	}

	public void updateWqWpAssgnId(String pActvId, String pTaskSeq, String pWqWpAssgnId) throws DAOException{
		try {
			((ActivityDetailDAOImpl) SpringApplicationContext.getBean("activityDetailDao")).updateActvTaskWqWpAssgnId(pActvId, pTaskSeq, pWqWpAssgnId);
		} catch (DAOException e) {
			throw new DAOException("Fail to updateActvTaskWqWpAssgnId\n" + e.getMessage(), e);
		}
	}	
	
	public void saveCurrentActvStatusRemark(String pRemarkContent, String pUser, String pActvId, String pTaskSeq) {
		
		if (StringUtils.isBlank(pRemarkContent)) {
			return;
		}
		RemarkDTO[] remarks = ActivityHelper.createRemarkDetails(pRemarkContent, null, ActivityConstants.ACTV_RMK_LENGTH);
		
		for (int i=0; i<remarks.length; ++i) {
			this.doSave(remarks[i], pUser, pActvId, pTaskSeq, null);
		}
	}
	
	public RemarkDTO[] getRemarksByTaskSeq(String pActvid, String pTaskSeq) {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "taskSeq"));
		DaoBase[] remarkDaos = this.doRetrieve(pActvid, pTaskSeq);

		
		if (ArrayUtils.isEmpty(remarkDaos)) {
			return null;
		}
		RemarkDTO[] remarks = new RemarkDTO[remarkDaos.length];
		
		for (int i=0; i<remarkDaos.length; ++i) {
			remarks[i] = (RemarkDTO)this.convertToDto(remarkDaos[i]);
		}
		return remarks;
	}
	
	public RemarkDTO[] convertWqRemarkToActvRemark(com.pccw.wq.schema.dto.RemarkDTO[] pWqRemarks){
		if (ArrayUtils.isEmpty(pWqRemarks))
			return null;
		
		
		StringBuffer wqRemarkSb = new StringBuffer();
		for (int i=0; i<pWqRemarks.length; i++){
			wqRemarkSb.append(pWqRemarks[i].getRemarkContent());
		}
		RemarkDTO[] pActvRemarks = ActivityHelper.createRemarkDetails(wqRemarkSb.toString(), "", ActivityConstants.ACTV_RMK_LENGTH);
		return pActvRemarks;
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		RemarkDTO remark = (RemarkDTO)this.convertToDto(new RemarkDTO(), pDaoBase);
		remark.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);	
		return remark;
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ActivityRemarkDAO)this.dao).setActvId((String)pArgs[0]);
		((ActivityRemarkDAO)this.dao).setTaskSeq((String)pArgs[1]);
		if (pArgs.length > 2 ){
			((ActivityRemarkDAO)this.dao).setStatusSeq((String)pArgs[2]);
		}
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		RemarkDTO remark = (RemarkDTO)pBaseDTO;
		ActivityRemarkDAO remarkDao = (ActivityRemarkDAO)this.dao;
		this.DTO2DAO(remark, remarkDao);
		remarkDao.setActvId((String)args[0]);
		remarkDao.setTaskSeq((String)args[1]);
		remarkDao.setStatusSeq((String)args[2]);
	}
}
