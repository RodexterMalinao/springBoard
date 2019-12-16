package com.bomwebportal.mob.ccs.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.SbOrderAmendDTO;

public class MobCcsSbOrderAmendDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    
	public int insertSbOrderAmendDTO(SbOrderAmendDTO dto) throws DAOException {
		String sql = "INSERT INTO bomweb_sb_order_amend" +
				" (" +
				" ORDER_ID" +
				" , ORDER_AMEND_TYPE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , PREV_SEQ_NO" +
				" , RPT_SENT" +
				" , MEMBER_NUM" +
				" ) VALUES" +
				" (" +
				" :orderId" +
				" , :orderAmendType" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" , (SELECT MAX(SEQ_NO) FROM bomweb_order_hist WHERE ORDER_ID = :orderId)" +
				" , :rptSent" +
				" , :memberNum" +
				" )";
		try {
		    logger.info("insertSbOrderAmendDTO() @ MobCcsSbOrderAmendDAO: " + sql);
		    
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("orderId", dto.getOrderId());
		    params.addValue("orderAmendType", dto.getOrderAmendType());
		    params.addValue("createBy", dto.getCreateBy());
		    params.addValue("lastUpdBy", dto.getLastUpdBy());
		    params.addValue("rptSent", dto.getRptSent());
		    params.addValue("memberNum", dto.getMemberNum());
		    return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
		    logger.error("Exception caught in insertSbOrderAmendDTO()", e);
		    throw new DAOException(e.getMessage(), e);
		}
	}
}
