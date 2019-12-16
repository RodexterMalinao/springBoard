package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.exception.DAOException;

public class HSTradeDescDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public HSTradeDescDTO getHSTradeDesc(String orderId) throws DAOException {

		logger.debug("getRptHSTradeDesc() is called");

		HSTradeDescDTO hsTradeDescDTO = new HSTradeDescDTO();

		StringBuilder SQL= new StringBuilder();
		SQL.append("SELECT \n");
		SQL.append("htd.id , \n");
		SQL.append("htd.brand , \n");
		SQL.append("htd.model , \n");
		SQL.append("htd.place_of_manufacture , \n");
		SQL.append("htd.network_freq , \n");
		SQL.append("htd.key_features , \n");     
		SQL.append("htd.operation_system , \n");     
		SQL.append("htd.internal_memory , \n");     
		SQL.append("htd.storage_type , \n");
		SQL.append("htd.storage_capacity , \n");
		SQL.append("htd.display , \n");     
		SQL.append("htd.network_protocol , \n");
		SQL.append("htd.camera_resolution , \n");
		SQL.append("htd.packaging_list , \n");     
		SQL.append("htd.price , \n");     
		SQL.append("htd.add_delivery_charge , \n");     
		SQL.append("htd.repair_srv_prdr , \n");
		SQL.append("htd.repair_srv_addr , \n");
		SQL.append("htd.exchange_policy , \n");     
		SQL.append("htd.warranty_period_hs , \n");
		SQL.append("htd.warranty_period_acc , \n");
		SQL.append("htd.PIS_PATH   \n");
		SQL.append("FROM \n");
		SQL.append("w_hs_trade_desc htd \n");
		SQL.append("INNER JOIN \n");
		SQL.append("( \n");
		SQL.append("SELECT \n");
		SQL.append("ptda.trade_desc_id \n");
		SQL.append("FROM \n");
		SQL.append("w_pos_trade_desc_assign ptda \n");
		SQL.append("INNER JOIN \n");
		SQL.append("( \n");
		SQL.append("SELECT \n");
		SQL.append("ippa.pos_item_cd \n");
		SQL.append("FROM \n");
		SQL.append("w_item_product_pos_assgn ippa \n");
		SQL.append("INNER JOIN bomweb_subscribed_item bsi \n");
		SQL.append("ON \n");
		SQL.append("ippa.item_id =bsi.id \n");
		SQL.append("WHERE \n");
		SQL.append("bsi.order_id = ? \n");
		SQL.append("AND bsi.type = 'HS' \n");
		SQL.append(") \n");
		SQL.append("tmp1 ON ptda.pos_item_cd = tmp1.pos_item_cd \n");
		SQL.append(") \n");
		SQL.append("tmp2 ON htd.id = tmp2.trade_desc_id"); 
		


		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getRptHSTradeDesc() @ HSTradeDescDAO: " + SQL);

			hsTradeDescDTO = simpleJdbcTemplate.queryForObject(SQL.toString(), getRowMapper(), orderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			hsTradeDescDTO = null;

		} catch (Exception e) {

			logger.info("Exception caught in getRptHSTradeDesc():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return hsTradeDescDTO;

	}
	
	public boolean isTradeDescExist(String basketId, Date appDate) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("isTradeDescExist @ HSTradeDescDAO is called");
		}
		String sql = "SELECT count(*) count" +
				" FROM w_pos_trade_desc_assign ptda" +
				" LEFT JOIN w_hs_trade_desc htd ON (ptda.TRADE_DESC_ID = htd.id)" +
				" WHERE ptda.POS_ITEM_CD IN (" +
				"  SELECT ippa.POS_ITEM_CD" +
				"  FROM w_basket_item_assgn bia" +
				"  LEFT JOIN w_item_product_pos_assgn ippa ON (bia.ITEM_ID = ippa.ITEM_ID)" +
				"  LEFT JOIN w_item i ON (bia.ITEM_ID = i.ID)" +
				"  WHERE bia.BASKET_ID = :basketId" +
				"  AND trunc(:appDate) BETWEEN bia.EFF_START_DATE AND NVL(bia.EFF_END_DATE, trunc(sysdate))" +
				"  AND i.TYPE = 'HS'" +
				" )";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("basketId", basketId);
			params.addValue("appDate", appDate, Types.DATE);
			if (logger.isDebugEnabled()) {
				logger.debug("isTradeDescExist() @ HSTradeDescDAO: " + sql);
			}
			int count = simpleJdbcTemplate.queryForInt(sql, params);
			return count > 0;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getHSTradeDescDTOByModelId(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<HSTradeDescDTO> getHSTradeDescList(String orderId) throws DAOException {
		logger.debug("getHSTradeDescList() is called");

		List<HSTradeDescDTO> hsTradeDescDTOs = new ArrayList<HSTradeDescDTO>();

		StringBuilder SQL= new StringBuilder();
		SQL.append("SELECT \n");
		SQL.append("htd.id , \n");
		SQL.append("htd.brand , \n");
		SQL.append("htd.model , \n");
		SQL.append("htd.place_of_manufacture , \n");
		SQL.append("htd.network_freq , \n");
		SQL.append("htd.key_features , \n");     
		SQL.append("htd.operation_system , \n");     
		SQL.append("htd.internal_memory , \n");     
		SQL.append("htd.storage_type , \n");
		SQL.append("htd.storage_capacity , \n");
		SQL.append("htd.display , \n");     
		SQL.append("htd.network_protocol , \n");
		SQL.append("htd.camera_resolution , \n");
		SQL.append("htd.packaging_list , \n");     
		SQL.append("htd.price , \n");     
		SQL.append("htd.add_delivery_charge , \n");     
		SQL.append("htd.exchange_policy , \n");     
		SQL.append("htd.repair_srv_prdr , \n");
		SQL.append("htd.repair_srv_addr , \n");
		SQL.append("htd.exchange_policy , \n");     
		SQL.append("htd.warranty_period_hs , \n");
		SQL.append("htd.warranty_period_acc, \n");
		SQL.append("htd.PIS_PATH   \n");
		SQL.append("FROM \n");
		SQL.append("w_hs_trade_desc htd \n");
		SQL.append("INNER JOIN \n");
		SQL.append("( \n");
		SQL.append("SELECT \n");
		SQL.append("ptda.trade_desc_id \n");
		SQL.append("FROM \n");
		SQL.append("w_pos_trade_desc_assign ptda \n");
		SQL.append("INNER JOIN \n");
		SQL.append("( \n");
		SQL.append("SELECT \n");
		SQL.append("ippa.pos_item_cd \n");
		SQL.append("FROM \n");
		SQL.append("w_item_product_pos_assgn ippa \n");
		SQL.append("INNER JOIN bomweb_subscribed_item bsi \n");
		SQL.append("ON \n");
		SQL.append("ippa.item_id =bsi.id \n");
		SQL.append("WHERE \n");
		SQL.append("bsi.order_id = ? \n");
		SQL.append(") \n");
		SQL.append("tmp1 ON ptda.pos_item_cd = tmp1.pos_item_cd \n");
		SQL.append(") \n");
		SQL.append("tmp2 ON htd.id = tmp2.trade_desc_id"); 
		
		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getHSTradeDescList() @ HSTradeDescDAO: " + SQL.toString());

			hsTradeDescDTOs =simpleJdbcTemplate.query(SQL.toString(), getRowMapper(), orderId);
			if (hsTradeDescDTOs.size() == 0) {
				hsTradeDescDTOs = null;
			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			hsTradeDescDTOs = null;

		} catch (Exception e) {

			logger.info("Exception caught in getHSTradeDescList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return hsTradeDescDTOs;
	}
	
	private ParameterizedRowMapper<HSTradeDescDTO> getRowMapper() {
		return new ParameterizedRowMapper<HSTradeDescDTO>() {
			public HSTradeDescDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				HSTradeDescDTO dto = new HSTradeDescDTO();

				dto.setBrand(rs.getString("brand"));
				dto.setModel(rs.getString("model"));
				dto.setPlaceOfManufacture(rs.getString("place_of_manufacture"));
				dto.setNetworkFrequency(rs.getString("network_freq"));
				dto.setKeyFeatures(rs.getString("key_features"));    
				dto.setOperatingSystem(rs.getString("operation_system"));  
				dto.setInternalMemory(rs.getString("internal_memory"));		 	
				dto.setStorageType(rs.getString("storage_type"));
				dto.setStorageCapacity(rs.getString("storage_capacity"));
				dto.setDisplay(rs.getString("display"));                  
				dto.setNetworkProtocol(rs.getString("network_protocol"));
				dto.setCameraResolution(rs.getString("camera_resolution"));
				dto.setPackagingList(rs.getString("packaging_list"));       
				dto.setPrice(rs.getString("price"));                     
				dto.setAdditionalDeliveryCharge(rs.getString("add_delivery_charge")); 				
				dto.setRepairSrvProvider(rs.getString("repair_srv_prdr"));
				dto.setRepairSrvAddress(rs.getString("repair_srv_addr"));
				dto.setExchangePolicy(rs.getString("exchange_policy"));    
				dto.setWarrantyHandset(rs.getString("warranty_period_hs"));
				dto.setWarrantyAccessory(rs.getString("warranty_period_acc"));
				dto.setPisPath(rs.getString("PIS_PATH"));

				return dto;
			}
		};
	}
	//Athena 20131111 online sales HsTradeDesc report by item code
	public HSTradeDescDTO getHSTradeDescByItemCode(String itemCode) throws DAOException {

		logger.debug("getHSTradeDescByItemCode() is called");

		HSTradeDescDTO hsTradeDescDTO = new HSTradeDescDTO();

		String SQL = "SELECT htd.id , " 
		+"  htd.brand , " 
		+"  htd.model , " 
		+"  htd.place_of_manufacture , " 
		+"  htd.network_freq , " 
		+"  htd.key_features , " 
		+"  htd.operation_system , " 
		+"  htd.internal_memory , " 
		+"  htd.storage_type , " 
		+"  htd.storage_capacity , " 
		+"  htd.display , " 
		+"  htd.network_protocol , " 
		+"  htd.camera_resolution , " 
		+"  htd.packaging_list , " 
		+"  htd.price , " 
		+"  htd.add_delivery_charge , " 
		+"  htd.repair_srv_prdr , " 
		+"  htd.repair_srv_addr , " 
		+"  htd.exchange_policy , " 
		+"  htd.warranty_period_hs , " 
		+"  htd.warranty_period_acc , "
		+ " htd.pis_path " 
		+"FROM w_hs_trade_desc htd, " 
		+"  w_pos_trade_desc_assign ptda " 
		+"WHERE htd.id   =ptda.trade_desc_id " 
		+"AND pos_item_cd= :itemCode";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("itemCode", itemCode);

		try {
			logger.debug("getHSTradeDescByItemCode @ HSTradeDescDAO: " + SQL);

			hsTradeDescDTO = simpleJdbcTemplate.queryForObject(SQL.toString(), getRowMapper(), itemCode);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			hsTradeDescDTO = null;

		} catch (Exception e) {

			logger.info("Exception caught in getHSTradeDescByItemCode:", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return hsTradeDescDTO;

	}
}
