package com.activity.dao;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.search.SearchResult;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.schema.dto.WorkQueueInquiryCriteriaDTO;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.service.WorkQueueService;

public class ActivityInquiryDAOImpl extends DaoBaseImpl implements ActivityInquiryDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5835303202708822059L;

	@Override
	public String getActvLob(String pWqWpAssgnId, String pSbId, String pSbActvId) throws Exception{
		StringBuilder sql = new StringBuilder();
		Object[] bind = new Object[] {};
		bind = ArrayUtils.add(bind, pWqWpAssgnId);
		
		sql.append("select lob from SB_ACTV actv ");
		sql.append("where ACTV_ID in ( select ACTV_ID ");
		sql.append("from SB_ACTV_TASK task ");
		sql.append("where WQ_WP_ASSGN_ID = ? ");
		
		if (StringUtils.isNotBlank(pSbId)){
			sql.append("or actv.ORDER_ID = ? ");
			bind = ArrayUtils.add(bind, pSbId);
		}
		
		if (StringUtils.isNotBlank(pSbActvId)){
			sql.append("or task.ACTV_ID = ? ");
			bind = ArrayUtils.add(bind, pSbActvId);
		}
		
		sql.append(")");
		
		String lob = OracleSelectHelper.getSqlFirstRowColumnString(this.getDataSource(), sql.toString(), bind);
		if (StringUtils.isBlank(lob) && StringUtils.isNotBlank(pWqWpAssgnId)) {
			WorkQueueInquiryCriteriaDTO criteria = new WorkQueueInquiryCriteriaDTO();
			criteria.setWqWpAssgnId(pWqWpAssgnId);
			SearchResult<WqInquiryResultFormDTO> result = SpringApplicationContext.getBean(WorkQueueService.class).searchWorkQueue(criteria, null, null);
			if (result.getTotalCount() > 0) {
				lob = result.getResult().get(0).getTypeOfService();
			}
		}
		return lob;
	}
	
}
