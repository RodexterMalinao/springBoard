package com.activity.dao;

import com.activity.dto.SbActvTaskWqActionDTO;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.pccw.util.db.OracleSelectHelper;

public class ActivityTaskWqActionLookupDAOImpl extends BaseDAO implements ActivityTaskWqActionLookupDAO {

	private static final String SQL_GET_SB_ACTV_TASK_WQ_ACTION_LKUP_V = 
			"SELECT ACTV_ID \"actvId\", TASK_SEQ \"taskSeq\", RELATED_TASK_SEQ \"relatedTaskSeq\", LOB \"lob\", ACTV_CHANNEL_ID \"actvChannelId\", "
		+ 	"       ACTV_TYPE \"actvType\", TASK_TYPE \"taskType\", CRITERIA_CHANNEL_ID \"criteriaChannelId\", "
		+   "       WQ_WP_ASSGN_ID \"wqWpAssgnId\", ACTV_ACTION \"actvAction\", WQ_ACTION \"wqAction\", WQ_TYPE \"wqType\", "
		+   "       WQ_SUBTYPE \"wqSubType\", WQ_NATURE_ID \"wqNatureId\", URL \"url\", WQ_ATTB \"wqAttributeString\" "
		+   "  FROM SB_ACTV_TASK_WQ_ACTION_LKUP_V"
		+   " WHERE ACTV_ID = ? AND TASK_SEQ = ? "
		+   " ORDER BY EXECUTE_SEQUENCE ";
	

	@Override
	public SbActvTaskWqActionDTO[] getSbActvTaskWqAction(String pActvId, String pTaskSeq) throws DAOException {
		
		try {
			return OracleSelectHelper.getSqlResultObjects(this.getDataSource(), SQL_GET_SB_ACTV_TASK_WQ_ACTION_LKUP_V, new Object[] {pActvId, pTaskSeq}, SbActvTaskWqActionDTO.class);
		} catch (Exception e) {
			throw new DAOException("Fail to getSbActvTaskWqAction\n" + e.getMessage(), e);
		}
	}
}