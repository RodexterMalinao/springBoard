package com.bomwebportal.lts.dao;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.SignatureDTO;
import com.bomwebportal.lts.dto.SignatureDTO.SignatureType;
import com.bomwebportal.lts.dto.order.SignatureLtsDTO;
import com.bomwebportal.util.SignatureToImage;

public class OrderSignatureDAO extends BaseDAO {

	
	public int insertOrderSignature(String orderId, SignatureDTO signature, String userId) throws DAOException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO BOMWEB_ORDER_SIGNATURE");
		sql.append(" (order_id, sign_type, signature, create_by, create_date, last_upd_by, last_upd_date, replicated_sign_type)");
		sql.append(" VALUES");
		sql.append(" (:orderId, :signType, :signature, :createBy, sysdate, :lastUpdBy, sysdate, :replicatedSignType)");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("signType", signature.getSignType() == null ? null : signature.getSignType().name());
			params.addValue("signature", signature.getSignatureString());
			params.addValue("createBy", userId);
			params.addValue("lastUpdBy", userId);
			params.addValue("replicatedSignType", signature.getReplicatedSignType() == null ? null :signature.getReplicatedSignType().name());
			
			return simpleJdbcTemplate.update(sql.toString(), params);
			
		} catch (Exception e) {
			logger.error("Exception caught in insertOrderSignature(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int deleteOrderSignature(String orderId, String signType) throws DAOException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BOMWEB_ORDER_SIGNATURE");
		sql.append(" WHERE ORDER_ID = :orderId");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			
			if (StringUtils.isNotEmpty(signType)) {
				sql.append(" AND SIGN_TYPE = :signType");
				params.addValue("signType", signType);
			}
			
			return this.simpleJdbcTemplate.update(sql.toString(), params);
		}
		catch (Exception e) {
			logger.error("Exception caught in deleteOrderSignature(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public SignatureDTO getOrderSignature(String orderId, String signType) throws DAOException {
		
		ParameterizedRowMapper<SignatureDTO> orderSignatureRowMapper = new ParameterizedRowMapper<SignatureDTO>() {
			public SignatureDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SignatureDTO signature = new SignatureDTO();
				signature.setSignatureString(rs.getString("signature"));
				signature.setTitleEng(rs.getString("title_en"));
				signature.setTitleChi(rs.getString("title_chi"));
				signature.setNoteEng(rs.getString("note_en"));
				signature.setNoteChi(rs.getString("note_chi"));

				String signType = rs.getString("sign_type");
				if (StringUtils.isNotBlank(signType)) {
					signature.setSignType(SignatureType.valueOf(signType));	
				}
				
				String replicatedSignType = rs.getString("replicated_sign_type");
				if (StringUtils.isNotBlank(replicatedSignType)) {
					signature.setReplicatedSignType(SignatureType.valueOf(replicatedSignType));	
				}
				
				return signature;
			}
	    };
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT bos.order_id, bos.sign_type, bos.signature, bos.replicated_sign_type,"); 
		sql.append(" title_en.contents as title_en, title_chi.contents as title_chi,"); 
		sql.append(" note_en.contents as note_en, note_chi.contents as note_chi");
		sql.append(" FROM bomweb_order_signature bos, ");
		sql.append("  (SELECT attribute, contents FROM bomweb_rpt_template");
		sql.append("	WHERE rpt_name = 'SIGNATURE_TITLE'");
		sql.append("    AND language = 'en') title_en,");
		sql.append("  (SELECT attribute, contents FROM bomweb_rpt_template");
		sql.append("    WHERE rpt_name = 'SIGNATURE_TITLE'");
		sql.append("    AND language = 'zh_HK') title_chi,");
		sql.append("  (SELECT attribute, contents FROM bomweb_rpt_template");
		sql.append("    WHERE rpt_name = 'SIGNATURE_NOTE'");
		sql.append("    AND language = 'en') note_en,");
		sql.append("  (SELECT attribute, contents FROM bomweb_rpt_template");
		sql.append("    WHERE rpt_name = 'SIGNATURE_NOTE'");
		sql.append("    AND language = 'zh_HK') note_chi");
		sql.append(" WHERE title_en.attribute = bos.sign_type");
		sql.append(" AND title_chi.attribute = bos.sign_type");
		sql.append(" AND note_en.attribute = bos.sign_type");
		sql.append(" AND note_chi.attribute = bos.sign_type");
		sql.append(" AND bos.sign_type = :signType");
		sql.append(" AND bos.order_id = :orderId");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("signType", signType);
			return this.simpleJdbcTemplate.queryForObject(sql.toString(), orderSignatureRowMapper, params);
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}
		catch (Exception e) {
			logger.error("Exception caught in getOrderSignature(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public SignatureLtsDTO[] getOrderSignature(String orderId) throws DAOException {
		
		ParameterizedRowMapper<SignatureLtsDTO> orderSignatureRowMapper = new ParameterizedRowMapper<SignatureLtsDTO>() {
			public SignatureLtsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SignatureLtsDTO signature = new SignatureLtsDTO();
				
				String signatureString = rs.getString("signature");
				if (StringUtils.isNotBlank(signatureString)) {
					SignatureToImage sti = new SignatureToImage();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					sti.saveSignature(signatureString, baos);
					signature.setSignatureBytes(baos.toByteArray());
				}
				signature.setSignType(rs.getString("sign_type"));	
				return signature;
			}
	    };
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM BOMWEB_ORDER_SIGNATURE ");
			sql.append(" WHERE ORDER_ID = :orderId ");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			List<SignatureLtsDTO> orderSignatureList = this.simpleJdbcTemplate.query(sql.toString(), orderSignatureRowMapper, params);
			if (orderSignatureList == null || orderSignatureList.isEmpty()) {
				return null;
			}
			return orderSignatureList.toArray(new SignatureLtsDTO[orderSignatureList.size()]);
		}
		catch (EmptyResultDataAccessException erdae) {
			return null;
		}
		catch (Exception e) {
			logger.error("Exception caught in getOrderSignature(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
