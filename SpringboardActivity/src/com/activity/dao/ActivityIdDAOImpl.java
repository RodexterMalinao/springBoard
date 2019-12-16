package com.activity.dao;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ActivityIdDAOImpl extends BaseDAO implements ActivityIdDAO {

	private static final String SQL_GET_NEXT_ACTV_ID = 
			"select sb_actv_id_seq.nextval " +
			"from dual";
	
	private static final String SQL_GET_MAX_TASK_SEQ = 
			"select nvl(max(task_seq), 0) " +
			"from sb_actv_task " +
			"where actv_id = ?";
	
	private static final String SQL_GET_MAX_DOC_SEQ = 
			"select nvl(max(seq_num), 0) " +
			"from bomweb_all_ord_doc " +
			"where order_id = ? and doc_type = ?";
	

	public String generateActvId() throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_NEXT_ACTV_ID, String.class);
		} catch (Exception e) {
			throw new DAOException("Fail to generateActvId\n" + e.getMessage(), e);
		}
	}

	public int getMaxTaskSeq(String pActvId) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForInt(SQL_GET_MAX_TASK_SEQ, pActvId);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int getMaxDocumentSeq(String pId, String pDocType) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForInt(SQL_GET_MAX_DOC_SEQ, pId, pDocType);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
}
