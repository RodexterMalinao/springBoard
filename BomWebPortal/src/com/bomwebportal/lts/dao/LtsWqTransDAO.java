package com.bomwebportal.lts.dao;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class LtsWqTransDAO extends BaseDAO {

	private final Log logger = LogFactory.getLog(getClass());
			
	public int readyWqTransaction() throws DAOException {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
				.withCatalogName("pkg_sb_wq_trans")
				.withProcedureName("processPendingWqTrans")
				.declareParameters(
						new SqlOutParameter("o_process_cnt", Types.INTEGER));
		
		jdbcCall.setAccessCallParameterMetaData(false);

		try {
			Map<String, Object> resultMap =  jdbcCall.execute();
			if (resultMap != null && resultMap.size() > 0) {
				return ((Integer)resultMap.get("o_process_cnt")).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			logger.error("Exception caught in readyWqTransaction()", e);
			if (e instanceof UncategorizedSQLException && 
				((UncategorizedSQLException)e).getSQLException().getErrorCode() == 4068) {
				logger.info("LOOKS LIKE THE PACKAGE HAS BEEN RE-COMPILED. WILL TRY AGAIN LATER!");
				return 0;
			} else {
			    throw new DAOException(e.getMessage(), e);
			}
		}
	}

}
