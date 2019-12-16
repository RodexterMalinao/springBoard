package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.*;
import com.bomwebportal.exception.*;


public class ItemDisplayDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public ItemDisplayDTO getItemDisplay(int itemId, String locale,
			String displayType) throws DAOException {
		logger.info("getItemDisplay() is called");

		List<ItemDisplayDTO> itemDisplayList = new ArrayList<ItemDisplayDTO>();

		StringBuilder SQL= new StringBuilder();
		//mofify eliot 20110714		
		SQL.append(" SELECT item_id, locale, description, display_type, html, html2, image_path \n");
		SQL.append("   FROM w_item_display_lkup \n");
		SQL.append("  WHERE item_id = ? AND locale = ? AND DISPLAY_TYPE = ? \n");

		ParameterizedRowMapper<ItemDisplayDTO> mapper = new ParameterizedRowMapper<ItemDisplayDTO>() {
			public ItemDisplayDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ItemDisplayDTO dto = new ItemDisplayDTO();
				dto.setItemId(rs.getInt("ITEM_ID"));
				dto.setLocale(rs.getString("LOCALE"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setDisplayType(rs.getString("DISPLAY_TYPE"));
				dto.setHtml(rs.getString("HTML"));
				dto.setHtml2(rs.getString("HTML2"));
				dto.setImagePath(rs.getString("IMAGE_PATH"));

				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.info("getItemDisplay() @ ItemDisplayDAO: ");
			logger.debug("getItemDisplay() @ ItemDisplayDAO: " + SQL);
			itemDisplayList = simpleJdbcTemplate.query(SQL.toString(), mapper, itemId, locale, displayType);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItemDisplay()");

			itemDisplayList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemDisplay():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		if (CollectionUtils.isNotEmpty(itemDisplayList)) {
			return itemDisplayList.get(0);// only return the first one
		} else {
			return null;
		}
	}
	
	public List<ItemDisplayDTO> getItemDisplayAll(int itemId, String locale,
			String displayType) throws DAOException {
		logger.info("getItemDisplay() is called");

		List<ItemDisplayDTO> itemDisplayList = new ArrayList<ItemDisplayDTO>();

		StringBuilder SQL= new StringBuilder();
		//modify eliot 20110714
		SQL.append(" SELECT item_id, locale, description, display_type, html, html2, image_path \n");
		SQL.append("   FROM w_item_display_lkup \n");
		SQL.append("  WHERE item_id = nvl(?,item_id)  AND locale = nvl(?,locale) AND DISPLAY_TYPE = nvl(?,DISPLAY_TYPE) \n");

		ParameterizedRowMapper<ItemDisplayDTO> mapper = new ParameterizedRowMapper<ItemDisplayDTO>() {
			public ItemDisplayDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ItemDisplayDTO dto = new ItemDisplayDTO();
				dto.setItemId(rs.getInt("ITEM_ID"));
				dto.setLocale(rs.getString("LOCALE"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setDisplayType(rs.getString("DISPLAY_TYPE"));
				dto.setHtml(rs.getString("HTML"));
				dto.setHtml2(rs.getString("HTML2"));
				dto.setImagePath(rs.getString("IMAGE_PATH"));

				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.info("getItemDisplay() @ ItemDisplayDAO: " );
			logger.debug("getItemDisplay() @ ItemDisplayDAO: " + SQL);
			itemDisplayList = simpleJdbcTemplate.query(SQL.toString(), mapper, itemId, locale, displayType);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getItemDisplay()");

			itemDisplayList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getItemDisplay():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemDisplayList;
	}

	public int updateItemDisplay(ItemDisplayDTO dto) throws DAOException {
		logger.info("updateItemDisplay is called");
		
		String SQL = "update W_ITEM_DISPLAY_LKUP set DESCRIPTION = ? ,HTML=? , IMAGE_PATH=?where ITEM_ID = ? and LOCALE=? and DISPLAY_TYPE=?";
		
		try {
			return simpleJdbcTemplate.update(SQL, dto.getDescription(),
					dto.getHtml(), dto.getImagePath(), dto.getItemId(),
					dto.getLocale(), dto.getDisplayType());

		} catch (Exception e) {
			logger.error("Exception caught in updateItemDisplay()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	public int insertItemDisplay(ItemDisplayDTO dto) throws DAOException {
		logger.info("insertItemDisplay is called");

		StringBuilder SQL= new StringBuilder();
		SQL.append(" INSERT INTO w_item_display_lkup \n");
		SQL.append("        (item_id, locale, description, display_type, html, image_path) \n");
		SQL.append(" VALUES (?, ?, ?, ?, ?, ? ) \n");

		try {
			
			return simpleJdbcTemplate.update(SQL.toString(), dto.getItemId(), dto.getLocale(),
					dto.getDescription(), dto.getDisplayType(), dto.getHtml(),
					dto.getImagePath());
		}catch(Exception e){
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int deleteItemDisplay(ItemDisplayDTO dto) throws DAOException {
		logger.info("deleteItemDisplay is called");

		String SQL="  delete from W_ITEM_DISPLAY_LKUP where item_id=? and LOCALE=? and DISPLAY_TYPE=? ";

		try {

			return simpleJdbcTemplate.update(SQL, dto.getItemId(), dto.getLocale(),	dto.getDisplayType());
		}catch (Exception e) {
			logger.error("Exception caught in deleteItemDisplay()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}


}
