package com.bomwebportal.dao;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dto.SbItemPreDTO;
import com.bomwebportal.exception.DAOException;

public class SbItemPreDAO extends BaseDAO {
	public List<SbItemPreDTO> getSbItemPreDTOList(List<String> itemIds) throws DAOException {
		final String sql = "SELECT" +
				"  B.ID SOURCE_ITEM_ID" +
				"  , B.DESCRIPTION SOURCE_DESC" +
				"  , C.ID TARGET_ITEM_ID" +
				"  , C.DESCRIPTION TARGET_DESC" +
				" FROM" +
				"  BOMWEB_CODE_LKUP A" +
				"  LEFT JOIN W_ITEM B ON (SUBSTR(A.CODE_DESC,1,INSTR(A.CODE_DESC,',')-1) = B.ID)" +
				"  LEFT JOIN W_ITEM C ON (SUBSTR(A.CODE_DESC,INSTR(A.CODE_DESC,',')+1,LENGTH(A.CODE_DESC)) = C.ID)" +
				" WHERE" +
				"  A.CODE_TYPE = 'SB_ITEM_PRE'" +
				"  AND A.CODE_ID = '03'" +
				"  AND " +
				"  (" +
				"    SUBSTR(A.CODE_DESC,1,INSTR(A.CODE_DESC,',')-1) in (:itemIds)" +// -- input SIM
				"    OR SUBSTR(A.CODE_DESC,INSTR(A.CODE_DESC,',')+1,LENGTH(A.CODE_DESC)) in (:itemIds)" +// -- input SIM
				"  )" +
				" ORDER BY" +
				"  SOURCE_ITEM_ID";
		if (logger.isDebugEnabled()) {
			logger.debug(sql);
			logger.debug("itemIds: " + StringUtils.join(itemIds, ' '));
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemIds", itemIds);
			return this.simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(SbItemPreDTO.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getSbItemPreDTOList");
			}
			return Collections.emptyList();
		} catch (Exception e) {
			logger.warn("Exception in getSbItemPreDTOList", e);
			throw new DAOException(e);
		}
	}
}
