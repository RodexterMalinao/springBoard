package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.ItemsRelationshipDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.Utility;

public class ItemsRelationshipDAO extends BaseDAO {
private String getCoExistedSql(String[] items) {
		
		String coExisted = "SELECT r.item_a, r.item_b, r.description, r.relation_type "
				 + "  FROM w_item_relationship r "
				 + " WHERE (   r.item_a IN (" + getItems(items) + ") "
				 + "        OR r.item_b IN (" + getItems(items) + ") "
				 + "       ) "
				 + "   AND r.relation_type = 'C' "
				 + "   AND r.channel_id = :channelId "
				 + "   AND trunc(:appDate) BETWEEN trunc(r.eff_start_date) AND TRUNC(NVL (r.eff_end_date,SYSDATE)) "
				 + " MINUS "
				 + " SELECT item_a, item_b, description, r.relation_type "
				 + "  FROM w_item_relationship r "
				 + " WHERE r.item_a IN (" + getItems(items) + ") "
				 + "   AND r.item_b IN (" + getItems(items) + ") "
				 + "   AND r.relation_type = 'C' "
				 + "   AND r.channel_id = :channelId "
				 + "   AND trunc(:appDate) BETWEEN trunc(r.eff_start_date) AND TRUNC(NVL (r.eff_end_date,SYSDATE)) ";
		
		
		return coExisted;
	}
	
	private String getPreRequiredSql (String[] items) {
		
		String  preRequired = "SELECT r.item_a, r.item_b, r.description, r.relation_type "
			 + "  FROM w_item_relationship r "
			 + " WHERE r.item_b IN (" + getItems(items) + ") "
			 + "   AND r.relation_type = 'P' "
			 + "   AND r.channel_id = :channelId "
			 + "   AND trunc(:appDate) BETWEEN trunc(r.eff_start_date) AND TRUNC(NVL (r.eff_end_date,SYSDATE)) "
			 + " MINUS "
			 + " SELECT r.item_a, r.item_b, r.description, r.relation_type "
			 + "  FROM w_item_relationship r "
			 + " WHERE r.item_a IN (" + getItems(items) + ") "
			 + "   AND r.relation_type = 'P' "
			 + "   AND r.channel_id = :channelId "
			 + "   AND trunc(:appDate) BETWEEN trunc(r.eff_start_date) AND TRUNC(NVL (r.eff_end_date,SYSDATE)) ";
	
		return preRequired;
	}
	
	private String getExclusiveSql (String[] items) {
		
		String preRequired = "SELECT r.item_a, r.item_b, r.description, r.relation_type "
				 + "  FROM w_item_relationship r "
				 + " WHERE r.item_a IN (" + getItems(items) + ") "
				 + "   AND r.item_b IN (" + getItems(items) + ") "
				 + "   AND r.relation_type = 'E' "
				 + "   AND r.channel_id = :channelId "
				 + "   AND trunc(:appDate) BETWEEN trunc(r.eff_start_date) AND TRUNC(NVL (r.eff_end_date,SYSDATE)) ";
		
		return preRequired;
	}
	
	private String getPreRequiredOrSql (String[] items) {
		
		String  preRequired = "SELECT 'X' item_a, r.item_b, r.description, r.relation_type "
			 + "  FROM w_item_relationship r "
			 + " WHERE r.item_b IN (" + getItems(items) + ") "
			 + "   AND r.relation_type = 'PO' "
			 + "   AND r.channel_id = :channelId "
			 + "   AND trunc(:appDate) BETWEEN trunc(r.eff_start_date) AND TRUNC(NVL(r.eff_end_date,SYSDATE)) "
			 + " MINUS "
			 + " SELECT 'X' item_a, r.item_b, r.description, r.relation_type "
			 + "  FROM w_item_relationship r "
			 + " WHERE r.item_a IN (" + getItems(items) + ") "
			 + "   AND r.relation_type = 'PO' "
			 + "   AND r.channel_id = :channelId "
			 + "   AND trunc(:appDate) BETWEEN trunc(r.eff_start_date) AND TRUNC(NVL(r.eff_end_date,SYSDATE)) ";
	
		return preRequired;
	}
	
	public List<ItemsRelationshipDTO> getItemsRelations(String[] items, String channelId, Date appDate) throws DAOException {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		List<ItemsRelationshipDTO> results = new ArrayList<ItemsRelationshipDTO>();
		
		params.addValue("channelId", channelId);
		params.addValue("appDate", appDate);
		
		String[] inputItems;
		if (items == null || items.length == 0){
			inputItems = new String[1];
			inputItems[0] = "999999999999999";
		} else {
			inputItems = items;
		}
		
		ParameterizedRowMapper<ItemsRelationshipDTO> mapper = new ParameterizedRowMapper<ItemsRelationshipDTO>() {

			public ItemsRelationshipDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				ItemsRelationshipDTO dto = new ItemsRelationshipDTO();
				
				dto.setItemA(rs.getString("item_a"));
				dto.setItemB(rs.getString("item_b"));
				dto.setDescription(rs.getString("description"));
				dto.setRelationType(rs.getString("relation_type"));
		
				return dto;
			}
		};
		
		try {
			 long startTime = System.currentTimeMillis();
			
			 List<ItemsRelationshipDTO> coExist = simpleJdbcTemplate.query(getCoExistedSql(inputItems), mapper, params);
			 long endTime   = System.currentTimeMillis();
			 logger.info("totalTime call VasDetailValidator in itemsRelationshipService.getItemsRelations.getCoExistedSql(): "+((endTime - startTime))+"ms");
			 
			 startTime = System.currentTimeMillis();
			 List<ItemsRelationshipDTO> preReq = simpleJdbcTemplate.query(getPreRequiredSql(inputItems), mapper, params);
			 endTime   = System.currentTimeMillis();
			 logger.info("totalTime call VasDetailValidator in itemsRelationshipService.getItemsRelations.getPreRequiredSql(): "+((endTime - startTime))+"ms");
			 
			 startTime = System.currentTimeMillis();
			 List<ItemsRelationshipDTO> exclusive = simpleJdbcTemplate.query(getExclusiveSql(inputItems), mapper, params);
			 endTime   = System.currentTimeMillis();
			 logger.info("totalTime call VasDetailValidator in itemsRelationshipService.getItemsRelations.getExclusiveSql(): "+((endTime - startTime))+"ms");
			 
			 
			 startTime = System.currentTimeMillis();
			 List<ItemsRelationshipDTO> preReqOr = simpleJdbcTemplate.query(getPreRequiredOrSql(inputItems), mapper, params);
			 endTime   = System.currentTimeMillis();
			 logger.info("totalTime call VasDetailValidator in itemsRelationshipService.getItemsRelations.getPreRequiredOrSql(): "+((endTime - startTime))+"ms");

			 if (coExist != null && !coExist.isEmpty()) {
				 results.addAll(coExist);
			 }
			 
			 if (preReq != null && !preReq.isEmpty()) {
				 results.addAll(preReq);
			 }
			 
			 if (exclusive != null && !exclusive.isEmpty()) {
				 results.addAll(exclusive);
			 }
			 
			 if (preReqOr != null && !preReqOr.isEmpty()) {
				 results.addAll(preReqOr);
			 }
			 
			 return results;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.error("Exception caught in getItemsRelations()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
		
	}
	
	private String getItems(String[] items) {
	
		StringBuffer sb = new StringBuffer();
		
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				if (items.length == 1 || items.length <= i + 1)	{
					sb.append("'" + items[i] + "'");
				} else {
					sb.append("'" + items[i] + "',");
				}
				
			}
		}
		return sb.toString();
	}
	
	private String getSql(int type, String[] items) {
		
		String sql = "";
		
		 switch (type) {
	     case 1:  sql = getCoExistedSql(items);
	              break;
	     case 2:  sql = getPreRequiredSql(items);
	              break;
	     case 3:  sql = getExclusiveSql(items);
	              break;
	     default: sql = "Invalid values";
	              break;
		 }
	 return sql;
		 
	}
	
}
