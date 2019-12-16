package com.bomwebportal.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

import com.bomwebportal.dto.ItemOfferProductDTO;
import com.bomwebportal.dto.PcRelationshipDTO;
import com.bomwebportal.dto.PcRelationshipDTO.ItemType;
import com.bomwebportal.dto.PcRelationshipDTO.RelType;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.Utility;

public class BSbMobApiPkgDAO extends BaseDAO {
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final char DELIMITER = '-';
	
	@SuppressWarnings("unchecked")
	public List<PcRelationshipDTO> checkRelationshipSb(List<ItemOfferProductDTO> itemOfferProductDTOs
			, List<RelType> relTypes
			, Date pApplicationDate) throws DAOException {
		if (this.isEmpty(itemOfferProductDTOs)) {
			return Collections.emptyList();
		}

		List<SbItem> sbItems = this.getSbItemList(itemOfferProductDTOs);
		final String[] sbItemIds = this.getSbItemIds(sbItems);
		final String[] sbItemTypes = this.getSbItemTypes(sbItems);
		final String[] relTypesInString = this.getRelTypesInString(relTypes);
		if (logger.isDebugEnabled()) {
			logger.debug("sbItemIds: " + StringUtils.join(sbItemIds, ','));
			logger.debug("sbItemTypes: " + StringUtils.join(sbItemTypes, ','));
			logger.debug("relTypesInString: " + StringUtils.join(relTypesInString, ','));
		}
		
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
													.withSchemaName("ops$bom")
													.withCatalogName("b_sb_mob_api_pkg")
													.withProcedureName("CHECK_RELATIONSHIP_SB");
			// declare procedure parameters 
			simpleJdbcCall.declareParameters(
			new SqlParameter("v_sb_item_id_varray", OracleTypes.ARRAY, "VARRTYPE_VARCHAR50")
			, new SqlParameter("v_sb_item_type_varray", OracleTypes.ARRAY, "ITEM_TYPE_VARRAY")
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
			params.addValue("v_sb_item_id_varray", new AbstractSqlTypeValue() {
				@Override
				protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
					final Connection dConn = (new CommonsDbcpNativeJdbcExtractor()).getNativeConnection(conn);
					ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, dConn);
					return new ARRAY(arrayDescriptor, dConn, sbItemIds);
				}
			});
			params.addValue("v_sb_item_type_varray", new AbstractSqlTypeValue() {
				@Override
				protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
					final Connection dConn = (new CommonsDbcpNativeJdbcExtractor()).getNativeConnection(conn);
					ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, dConn);
					return new ARRAY(arrayDescriptor, dConn, sbItemTypes);
				}
			});
			params.addValue("v_rel_type_varray", new AbstractSqlTypeValue() {
				@Override
				protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
					final Connection dConn = (new CommonsDbcpNativeJdbcExtractor()).getNativeConnection(conn);
					ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, dConn);
					return new ARRAY(arrayDescriptor, dConn, relTypesInString);
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
	
	private List<SbItem> getSbItemList(List<ItemOfferProductDTO> itemOfferProductDTOs) {
		// create a map with key = offerId
		Map<String, List<ItemOfferProductDTO>> map = new LinkedHashMap<String, List<ItemOfferProductDTO>>();
		for (ItemOfferProductDTO dto : itemOfferProductDTOs) {
			List<ItemOfferProductDTO> members;
			if (map.containsKey(dto.getOfferId())) {
				members = map.get(dto.getOfferId());
			} else {
				members = new ArrayList<ItemOfferProductDTO>();
				map.put(dto.getOfferId(), members);
			}
			
			members.add(dto);
		}
		// extra step, sort productId values under offerId key
		for (Map.Entry<String, List<ItemOfferProductDTO>> entry : map.entrySet()) {
			List<ItemOfferProductDTO> list = entry.getValue();
			Collections.sort(list, new Comparator<ItemOfferProductDTO>() {
				public int compare(ItemOfferProductDTO obj0, ItemOfferProductDTO obj1) {
					return obj0.getProductId().compareTo(obj1.getProductId());
				}
			});
		}
		List<SbItem> sbItems = new ArrayList<SbItem>();
		/*
		 * productId, offerCd pair
		 * 
		 * Pair Item in seq of O, P-O, O, P1-O, P2-O
		 */
		for (Map.Entry<String, List<ItemOfferProductDTO>> entry : map.entrySet()) {
			String offerId = entry.getKey();
			
			SbItem sbItem = new SbItem();
			sbItem.setId(offerId);
			sbItem.setType(ItemType.O);
			sbItems.add(sbItem);
			
			for (ItemOfferProductDTO dto : entry.getValue()) {
				SbItem sbItemPair = new SbItem();
				sbItemPair.setId(dto.getProductId() + DELIMITER + offerId);
				sbItemPair.setType(ItemType.P);
				sbItems.add(sbItemPair);
			}
		}
		return sbItems;
	}
	
	private String[] getSbItemIds(List<SbItem> sbItems) {
		List<String> sbItemIds = new ArrayList<String>();
		for (SbItem sbItem : sbItems) {
			sbItemIds.add(sbItem.getId());
		}
		return sbItemIds.toArray(new String[0]);
	}
	
	private String[] getSbItemTypes(List<SbItem> sbItems) {
		List<String> sbItemTypes = new ArrayList<String>();
		for (SbItem sbItem : sbItems) {
			sbItemTypes.add(sbItem.getType().toString());
		}
		return sbItemTypes.toArray(new String[0]);
	}
	
	private class SbItem {
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public ItemType getType() {
			return type;
		}
		public void setType(ItemType type) {
			this.type = type;
		}
		private String id;
		private ItemType type;
	}

	private String[] getRelTypesInString(List<RelType> relTypes) {
		List<String> relTypesInString = new ArrayList<String>();
		if (!this.isEmpty(relTypes)) {
			for (RelType relType : relTypes) {
				relTypesInString.add(relType.toString());
			}
		}
		return relTypesInString.toArray(new String[0]);
	}
}
