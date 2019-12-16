package com.bomwebportal.mob.dao.bom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;


import com.bomwebportal.exception.DAOException;

public class BomSalesInfoDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	
	private static String getBomSalesRoleDTOByIDSQL =
			"SELECT DECODE(COUNT(*),0,'DUMMY','POSBOM_SUPERVISOR') BOM_CATEGORY " +
					"FROM b_sec_user su, " +
					"  b_sec_user_role sur " +
					"WHERE su.userid                =:staffId " +
					"AND su.u_id                    = sur.u_id " +
					"AND NVL (sur.enable_flag, 'N') = 'Y' " +
					"AND sur.r_id                  IN " +
					"  (SELECT b.r_id " +
					"  FROM b_sec_role_attribute a, " +
					"    b_sec_role b " +
					"  WHERE a.a_id IN " +
					"    (SELECT DISTINCT a_id " +
					"    FROM b_sec_attribute " +
					"    WHERE attribute='POSBOM_SUPERVISOR' " +
					"    ) " +
					"  AND a.r_id = b.r_id " +
					"  )";


	public String getBomSalesRoleDTOByID(String staffId) throws DAOException {
		
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);
			String role = (String) simpleJdbcTemplate.queryForObject(getBomSalesRoleDTOByIDSQL,String.class,params);
			//int count = this.simpleJdbcTemplate.queryForInt(getBomSalesInfoDTOByIDSQL, params);
			if (logger.isDebugEnabled()) {
				logger.debug("SQL: " + getBomSalesRoleDTOByIDSQL);
				logger.debug("role: " + role);
			}
			return role;

		} catch (Exception e) {
			logger.error("Exception caught in getBomSalesRoleDTOByID()", e);
			throw new DAOException(e.getMessage(), e);
		}
		

	}
}
