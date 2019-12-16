package com.bomwebportal.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

import com.bomwebportal.dto.PcRelationshipDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.Utility;

public class BPcMobApiPkgDAO extends BaseDAO {
	private final static String DATE_FORMAT = "yyyyMMdd";
	
	/*
		String[] itemIds = { "1000001205-1000001204-491104","1000001544-1000030612-581334","1000026305-1000026304-519368","1000028341-376563-392243","1000028701-1000030612-581334","1000028724-1000030294-579744","1000029245-1000031441-586119","1000030613-1000030612-581334","1000030704-1000030703-581898","1000031225-1000031224-585270","1000031227-1000031226-585270","1000031229-1000031228-585269","159716-234253-199866","162340-236940-201801","162356-1000030614-581342","162360-235116-200594","162362-235116-200594","162363-235116-200594","162365-235116-200594","162366-235111-200591","162368-235111-200591","162369-236780-201657","162375-235111-200591","162377-235111-200591","162378-236780-201657","162381-236780-201657","162391-1000030614-581342","162412-235111-200591","162417-235111-200591","162418-235111-200591","162420-235111-200591","162421-235111-200591","162455-235118-200596","162487-237869-202414","162563-235360-200749","162566-235400-200778","217121-217122-191288","236774-236773-201651","287750-1000031441-586119","314749-314751-347739","376564-376563-392243","417810-417807-421659","191288","199866","200591","200594","200596","200749","200778","201651","201657","201801","202414","347739","392243","421659","491104","519368","579744","581334","581342","581898","585269","585270","586119","1000001204-491104","1000026304-519368","1000030294-579744","1000030612-581334","1000030614-581342","1000030703-581898","1000031224-585270","1000031226-585270","1000031228-585269","1000031441-586119","217122-191288","234253-199866","235111-200591","2351116-200594","235118-200596","235360-200749","235400-200778","236773-201651","236780-201657","236940-201801","237869-202414","314751-347739","376563-392243","417807-421659" };
		String[] itemTypes = { "C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","O","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P","P" };
		String[] itemSeqs = null;
		String[] relTypes = { "CO", "EX", "PR", "CP", "PO"};
	 */
	@SuppressWarnings("unchecked")
	public List<PcRelationshipDTO> checkRelationship(final String[] itemIds
			, final String[] itemTypes
			, final String[] itemSeqs
			, final String[] relTypes
			, Date pApplicationDate) throws DAOException {
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
													.withSchemaName("ops$bom")
													.withCatalogName("b_pc_mob_api_pkg")
													.withProcedureName("CHECK_RELATIONSHIP");
			// declare procedure parameters 
			simpleJdbcCall.declareParameters(
			new SqlParameter("v_item_id_varray", OracleTypes.ARRAY, "VARRTYPE_VARCHAR50")
			, new SqlParameter("v_item_type_varray", OracleTypes.ARRAY, "ITEM_TYPE_VARRAY")
			, new SqlParameter("v_rel_type_varray", OracleTypes.ARRAY, "RELATION_VARTYPE")
			, new SqlParameter("v_applicationdate", Types.VARCHAR)
			/*
			, new SqlOutParameter("relationshipcursor", OracleTypes.CURSOR, new ParameterizedRowMapper<PcRelationshipDTO>() {
				public PcRelationshipDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					PcRelationshipDTO dto = new PcRelationshipDTO();
					dto.setDependsOnItemID(rs.getString("dependsOnItemID"));
					dto.setDependsOnItemType(rs.getString("dependsOnItemType"));
					dto.setDependsOnItemCode(rs.getString("dependsOnItemCode"));
					dto.setPrimaryItemID(rs.getString("primaryItemID"));
					dto.setPrimaryItemType(rs.getString("primaryItemType"));
					dto.setPrimaryItemCode(rs.getString("primaryItemCode"));
					dto.setRelationship(rs.getString("relationship"));
					dto.setSecondaryItemID(rs.getString("secondaryItemID"));
					dto.setSecondaryItemType(rs.getString("secondaryItemType"));
					dto.setSecondaryItemCode(rs.getString("secondaryItemCode"));
					return dto;
				}
			})
			*/
			, new SqlOutParameter("relationshipcursor", OracleTypes.CURSOR, ParameterizedBeanPropertyRowMapper.newInstance(PcRelationshipDTO.class))
			, new SqlOutParameter("o_return_value", Types.INTEGER)
			, new SqlOutParameter("o_error_code", Types.INTEGER)
			, new SqlOutParameter("o_error_text", Types.VARCHAR));
			
			// procedure parameters value
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("v_item_id_varray", new AbstractSqlTypeValue() {
				@Override
				protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
					final Connection dConn = (new CommonsDbcpNativeJdbcExtractor()).getNativeConnection(conn);
					ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, dConn);
					return new ARRAY(arrayDescriptor, dConn, itemIds);
				}
			});
			params.addValue("v_item_type_varray", new AbstractSqlTypeValue() {
				@Override
				protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
					final Connection dConn = (new CommonsDbcpNativeJdbcExtractor()).getNativeConnection(conn);
					ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, dConn);
					return new ARRAY(arrayDescriptor, dConn, itemTypes);
				}
			});
			params.addValue("v_rel_type_varray", new AbstractSqlTypeValue() {
				@Override
				protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
					final Connection dConn = (new CommonsDbcpNativeJdbcExtractor()).getNativeConnection(conn);
					ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, dConn);
					return new ARRAY(arrayDescriptor, dConn, relTypes);
				}
			});
			params.addValue("v_applicationdate", Utility.date2String(pApplicationDate, DATE_FORMAT));
			
			// execute
			Map<String, Object> out = simpleJdbcCall.execute(params);
			
			// retrieve
			List<PcRelationshipDTO> list = Collections.emptyList();
			Integer oReturnValue = null;
			Integer oErrorCode = null;
			String oErrorText = null;
			if (out.get("relationshipcursor") instanceof List<?>) {
				list = (List<PcRelationshipDTO>) out.get("relationshipcursor");
			}
			if (out.get("o_return_value") instanceof Integer) {
				oReturnValue = (Integer) out.get("o_return_value");
			}
			if (out.get("o_error_code") instanceof Integer) {
				oErrorCode = (Integer) out.get("o_error_code");
			}
			if (out.get("o_error_text") instanceof String) {
				oErrorText = (String) out.get("o_error_text");
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("relationshipcursor size: " + (this.isEmpty(list) ? 0 : list.size()));
				logger.debug("oReturnValue: " + oReturnValue);
				logger.debug("oErrorCode: " + oErrorCode);
				logger.debug("oErrorText: " + oErrorText);
			}
			
			return list;
		} catch (Exception e) {
			logger.error("Exception caught in checkRelationship()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
