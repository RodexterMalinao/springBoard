package com.bomwebportal.mob.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BomAddressDtlDTO;

public class BomAddressDtlDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static String getBomAddrDtlSQL = 
			"select unit_num " +
			"      ,floor_num " +
			"      ,build_name " +
			"      ,hlot_num " +
			"      ,street_num " +
			"      ,street_name " +
			"      ,st_catg_cd " +
			"      ,sect_cd " +
			"      ,distr_num " +
			"      ,addr_line1 " +
			"from b_address_dtl " +
			"where addr_id in (select d.corr_addr " +
			"                  from b_customer_details d, b_customer c " +
			"                  where d.cust_num = c.cust_num " +
			"                  and c.id_doc_type = :idDocType " +
			"                  and c.id_doc_num = :idDocNum " +
			"                  and c.system_id = 'MOB' " +
			"                  and d.system_id = 'MOB') ";

	public BomAddressDtlDTO getBomAddrDtl(String idDocType, String idDocNum) throws DAOException {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idDocType", idDocType);
		params.addValue("idDocNum", idDocNum);
		
		ParameterizedRowMapper<BomAddressDtlDTO> mapper = new ParameterizedRowMapper<BomAddressDtlDTO>() {
			public BomAddressDtlDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomAddressDtlDTO temp = new BomAddressDtlDTO();
				temp.setUnitNum(rs.getString("unit_num"));
				temp.setFloorNum(rs.getString("floor_num"));
				temp.setBuildName(rs.getString("build_name"));
				temp.setHlotNum(rs.getString("hlot_num"));
				temp.setStreetNum(rs.getString("street_num"));
				temp.setStreetName(rs.getString("street_name"));
				temp.setStCatgCd(rs.getString("st_catg_cd"));
				temp.setSectCd(rs.getString("sect_cd"));
				temp.setDistrNum(rs.getString("distr_num"));
				temp.setAddrLine1(rs.getString("addr_line1"));
				return temp;
			}
		};
		
		try {
			logger.info("getBomAddrDtl() is called in BomAddressDtlDAO: " + getBomAddrDtlSQL);
			List<BomAddressDtlDTO> result = simpleJdbcTemplate.query(getBomAddrDtlSQL, mapper, params);

			if (CollectionUtils.isNotEmpty(result)) {
				return result.get(0);
			}
		} catch (Exception e) {
			logger.error("Exception caught in getBomOrderStaus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
}
