package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;

public class MobCcsSupportDocDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	private final String getMobCcsSupportDocDTOInitialListSQL = "select cl.code_id,     \n"
			+ "       cl.code_desc,     \n"
			+ "       decode(fax.code_id, null, 'N', 'Y' ) fax_ind,"
			+ "       decode(onsite.code_id, null, 'N', 'Y' ) onsite_ind, "     
			+ "       cl.create_by,     \n"
			+ "       cl.create_date,     \n"
			+ "       cl.last_upd_by,     \n"
			+ "       cl.last_upd_date,     \n"
			+ "       decode(cl.code_id, '01', 'Y', 'N') mandatory     \n"
			+ "  from bomweb_code_lkup cl, bomweb_code_lkup fax, bomweb_code_lkup onsite    \n"
			+ " where upper(cl.code_type) = 'SUPPORT_DOC'     \n"
			+ " and upper(fax.code_type(+)) = 'SUPPORT_DOC_FAX'     \n"
			+ " and upper(onsite.code_type(+)) = 'SUPPORT_DOC_ONSITE'     \n"
			+ " and cl.code_id = fax.code_id(+)     \n"
			+ " and cl.code_id = onsite.code_id(+)     \n"
			+ " order by cl.code_id    \n";

	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOInitialList(
			String locale) throws DAOException {
		logger.info(" getMobCcsSupportDocDTOInitialList is called");

		List<MobCcsSupportDocDTO> itemList = new ArrayList<MobCcsSupportDocDTO>();

		ParameterizedRowMapper<MobCcsSupportDocDTO> mapper = new ParameterizedRowMapper<MobCcsSupportDocDTO>() {

			public MobCcsSupportDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCcsSupportDocDTO dto = new MobCcsSupportDocDTO();
				dto.setDocId(rs.getString("code_id"));
				dto.setDocDesc(rs.getString("code_desc"));
				dto.setFaxMandatory("Y".equalsIgnoreCase(rs
						.getString("fax_ind")));
				dto.setOnsiteMandatory("Y".equalsIgnoreCase(rs.getString("onsite_ind"))); //20130827
				dto.setCreateBy(rs.getString("create_by"));
				dto.setCreateDate(rs.getDate("create_date"));
				dto.setLastUpdBy(rs.getString("last_upd_by"));
				dto.setLastUpdDate(rs.getDate("last_upd_date"));
				dto.setMandatory("Y".equalsIgnoreCase(rs.getString("mandatory")));
				dto.setRecalled(false);
				dto.setVerified(null);
				dto.setRemovable(false);

				return dto;
			}
		};

		try {
			logger.info("getMobCcsSupportDocDTOInitialList() @ MobCcsSupportDocDAO: "
					+ getMobCcsSupportDocDTOInitialListSQL);

			itemList = simpleJdbcTemplate.query(
					getMobCcsSupportDocDTOInitialListSQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsSupportDocDTOInitialList()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsSupportDocUIListSQL():",
					e);
			throw new DAOException(e.getMessage(), e);
		}

		return itemList;
	}
	
	public boolean isSupportDocRequired(String docType, String basketID, String[] vasItemList, String channel) throws DAOException {
		logger.debug("isSupportDocRequired is called");
		List<String> formList = new ArrayList<String>();
		String SQL = "select * from bomweb_support_doc_control " +
				"where channel = '" + channel + "' " +
				"and doc_type='" + docType + "' " +
				"and (1=0 ";
		if (basketID != null) {
			SQL += "OR (type='BASKET' and id='" + basketID + "') ";
		}
		if ((vasItemList != null) && (vasItemList.length > 0)) {
			SQL +="OR (type='VAS' and id in ('" + vasItemList[0] + "'";
			if (vasItemList.length > 1) {
				for (int i=1; i<vasItemList.length; i++) {
					SQL +=", '" + vasItemList[i] + "'";
				}
			}
			SQL +=")) ";
		}
		SQL +=")";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String brand = new String();
				brand = rs.getString("ID");
				return brand;
			}
		};
		try {
			formList = simpleJdbcTemplate.query(SQL, mapper);
			//System.out.println(SQL);
			if ((formList != null) && (formList.size() > 0))
				return true;
			else {
				return false;
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return false;
		} catch (Exception e) {
			logger.error("Exception caught in getSelectedItemList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	private final String getMobCcsSupportDocDTOAdditionalListSQL = "select doc_value, rec_ind,     \n"
			+ "       serial_id,     \n"
			+ "       verify_ind     \n"
			+ "  from bomweb_support_doc     \n"
			+ "  where order_id = ? and doc_id = ?     \n";

	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOAdditionalList(
			List<MobCcsSupportDocDTO> initialList, String orderId, String locale)
			throws DAOException {
		logger.info(" getMobCcsSupportDocDTOAdditionalList is called");

		if (initialList == null) {
			initialList = getMobCcsSupportDocDTOInitialList(locale);
		}

		List<MobCcsSupportDocDTO> itemList = new ArrayList<MobCcsSupportDocDTO>();

		ParameterizedRowMapper<MobCcsSupportDocDTO> mapper = new ParameterizedRowMapper<MobCcsSupportDocDTO>() {

			public MobCcsSupportDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCcsSupportDocDTO dto = new MobCcsSupportDocDTO();

				dto.setDocValue(rs.getString("DOC_VALUE"));
				dto.setReceivedByFax("Y".equalsIgnoreCase(rs
						.getString("REC_IND")));
				dto.setFaxServerSerialNo(rs.getString("SERIAL_ID"));
				dto.setStoredFaxServerSerialNo(rs.getString("SERIAL_ID"));
				dto.setVerified(rs.getString("VERIFY_IND"));
				return dto;
			}
		};

		logger.info("getMobCcsSupportDocDTOAdditionalList() @ MobCcsSupportDocDAO: "
				+ getMobCcsSupportDocDTOAdditionalListSQL);

		for (MobCcsSupportDocDTO doc : initialList) {
			try {
				itemList = simpleJdbcTemplate.query(
						getMobCcsSupportDocDTOAdditionalListSQL, mapper,
						orderId, doc.getDocId());
				
				//Handling the case of waved ID Document Copy				
				if("01".equals(doc.getDocId())){
					doc.setMandatory(false);	
				}

				if (itemList != null) {
					if (itemList.size() == 1) {
						doc.setMandatory(true);
						doc.setRecalled(true);
						doc.setReceivedByFax(doc.isFaxMandatory()
								&& doc.isMandatory() ? true : itemList.get(0)
								.isReceivedByFax());
						doc.setFaxServerSerialNo(itemList.get(0)
								.getFaxServerSerialNo());
						doc.setStoredFaxServerSerialNo(itemList.get(0)
								.getStoredFaxServerSerialNo());
						doc.setVerified(itemList.get(0).getVerified());
					} else if (itemList.size() > 1) {
						throw new Exception(
								"Error capture in MobCcsSupportDocDAO - Multiple documents find in table bomweb_support_doc for Order ID: "
										+ orderId
										+ " and Doc ID: "
										+ doc.getDocId());
					}
				}

			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException in getMobCcsSupportDocDTOAdditionalList()");
			} catch (Exception e) {
				logger.info(
						"Exception caught in getMobCcsSupportDocUIListSQL():",
						e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return initialList;
	}

	private static final String updateMobCcsSupportDocUISQL = "update bomweb_support_doc     \n"
			+ "   set rec_ind       = ?,     \n"
			+ "       serial_id     = ?,     \n"
			+ "       verify_ind     = ?,     \n"
			+ "       doc_value     = ?,     \n"
			+ "       last_upd_date = sysdate     \n"
			+ " where order_id = ?     \n" + "   and doc_id = ?     \n";

	public int updateMobCcsSupportDocUI(MobCcsSupportDocUI dto)
			throws DAOException {
		logger.info("updateMobCcsSupportDocUI is called");

		try {
			List<MobCcsSupportDocDTO> mobCcsSupportDocDTOList = dto
					.getMobCcsSupportDocDTOList();
			logger.info("updateMobCcsSupportDocUI() @ MobCcsSupportDocDAO: "
					+ updateMobCcsSupportDocUISQL);
			int updated = 0;
			for (MobCcsSupportDocDTO supportDoc : mobCcsSupportDocDTOList) {
				if (supportDoc.isMandatory()) {
					updated += simpleJdbcTemplate.update(
							updateMobCcsSupportDocUISQL, supportDoc
									.isReceivedByFax() == true ? "Y" : "N",
							supportDoc.getFaxServerSerialNo() == null ? null
									: supportDoc.getFaxServerSerialNo().trim(),
							supportDoc.getVerified() == null ? null
									: supportDoc.getVerified().trim(),
							supportDoc.getDocValue(), dto.getOrderId(),
							supportDoc.getDocId());
				}
				supportDoc.setStoredFaxServerSerialNo(supportDoc
						.getFaxServerSerialNo() == null ? null : supportDoc
						.getFaxServerSerialNo().trim());
			}
			logger.info("updateMobCcsSupportDocUI() : No. of updated record(s) - "
					+ updated);
			return updated;

		} catch (Exception e) {
			logger.error("Exception caught in updateMobCcsSupportDocUI()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

	private static final String insertMobCcsSupportDocUISQL = "insert into bomweb_support_doc     \n"
			+ "  (order_id,     \n"
			+ "   doc_id,     \n"
			+ "   doc_value,     \n"
			+ "   rec_ind,     \n"
			+ "   serial_id,     \n"
			+ "   verify_ind,     \n"
			+ "   create_date,     \n"
			+ "   last_upd_date)     \n"
			+ "values     \n"
			+ "  (?,     \n"
			+ "   ?,     \n"
			+ "   ?,     \n"
			+ "   ?,     \n"
			+ "   ?,     \n"
			+ "   ?,     \n"
			+ "   sysdate,     \n" + "   sysdate)     \n";

	public int insertMobCcsSupportDocUI(MobCcsSupportDocUI dto)
			throws DAOException {
		logger.info("insertMobCcsSupportDocUI is called");

		try {
			logger.info("insertMobCcsSupportDocUI() @ MobCcsSupportDocDAO: "
					+ insertMobCcsSupportDocUISQL);
			List<MobCcsSupportDocDTO> mobCcsSupportDocDTOList = dto
					.getMobCcsSupportDocDTOList();
			int inserted = 0;
			for (MobCcsSupportDocDTO supportDoc : mobCcsSupportDocDTOList) {
				if (supportDoc.isMandatory()) {
					inserted += simpleJdbcTemplate.update(
							insertMobCcsSupportDocUISQL,
							dto.getOrderId() == null ? null : dto.getOrderId()
									.trim(),
							supportDoc.getDocId() == null ? null : supportDoc
									.getDocId().trim(),
							supportDoc.getDocValue() == null ? null
									: supportDoc.getDocValue().trim(),
							supportDoc.isReceivedByFax() == true ? "Y" : "N",
							supportDoc.getFaxServerSerialNo() == null ? null
									: supportDoc.getFaxServerSerialNo().trim(),
							supportDoc.getVerified() == null ? supportDoc
									.getVerified() : supportDoc.getVerified()
									.trim());// Value can be "Y", "N" or null
				}
				// Assume the data was inserted successfully, updated the stored
				// fax server serial no as session one.
				supportDoc.setStoredFaxServerSerialNo(supportDoc
						.getFaxServerSerialNo() == null ? null : supportDoc
						.getFaxServerSerialNo().trim());
			}
			logger.info("insertMobCcsSupportDocUI() : No. of inserted record(s) - "
					+ inserted);
			return inserted;
		} catch (Exception e) {
			logger.error("Exception caught in insertMobCcsFalloutLkup()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// === End of INSERT function ===

	public MobCcsSupportDocUI getMobCcsSupportDocUI(String orderId,
			String locale) throws DAOException {
		logger.info("getMobCcsSupportDocUI is called");
		MobCcsSupportDocUI sd = new MobCcsSupportDocUI();
		sd.setOrderId(orderId);
		List<MobCcsSupportDocDTO> supportDocList = getMobCcsSupportDocDTOAdditionalList(
				null, orderId, locale);
		sd.setMobCcsSupportDocDTOList(supportDocList);
		return sd;
	}

	private final String getMobCcsSupportDocDTOForCourierGuidSQL = "select doc_id, doc_value, rec_ind,     \n"
		+ "       serial_id,     \n"
		+ "       verify_ind     \n"
		+ "  from bomweb_support_doc     \n"
		+ "  where order_id = ?";

	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOForCourierGuid(String orderId)
		throws DAOException {
	    
	    logger.info(" getMobCcsSupportDocDTOForCourierGuid is called");
        
	    List<MobCcsSupportDocDTO> itemList = new ArrayList<MobCcsSupportDocDTO>();
        
	    ParameterizedRowMapper<MobCcsSupportDocDTO> mapper = new ParameterizedRowMapper<MobCcsSupportDocDTO>() {
        
		public MobCcsSupportDocDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    MobCcsSupportDocDTO dto = new MobCcsSupportDocDTO();
		    
		    dto.setDocId(rs.getString("DOC_ID"));
		    dto.setDocValue(rs.getString("DOC_VALUE"));
		    dto.setReceivedByFax("Y".equalsIgnoreCase(rs.getString("REC_IND"))?true:false);
		    dto.setFaxServerSerialNo(rs.getString("SERIAL_ID"));
		    dto.setStoredFaxServerSerialNo(rs.getString("SERIAL_ID"));
		    dto.setVerified(rs.getString("VERIFY_IND"));
		    return dto;
		}
	    };
        
	    logger.info("getMobCcsSupportDocDTOForCourierGuid() @ MobCcsSupportDocDAO: "
        			+ getMobCcsSupportDocDTOForCourierGuidSQL);
	    try {
		itemList = simpleJdbcTemplate.query(getMobCcsSupportDocDTOForCourierGuidSQL, mapper, orderId);
        
	    } catch (EmptyResultDataAccessException erdae) {
		logger.info("EmptyResultDataAccessException in getMobCcsSupportDocDTOForCourierGuid()");
	    } catch (Exception e) {
		logger.info("Exception caught in getMobCcsSupportDocDTOForCourierGuid():", e);
		throw new DAOException(e.getMessage(), e);
	    }
        
	    return itemList;
	}

	public boolean isReqForm(String docId, String orderId) throws DAOException {
		
		String sql = "SELECT COUNT(1) CNT " 
				+"FROM BOMWEB_SUPPORT_DOC " 
				+"WHERE DOC_ID = :docId " 
				+"AND ORDER_ID = :orderId"; 

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CNT");
			}
		};
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("docId", docId);
		params.addValue("orderId", orderId);

		try {
			logger.debug("isReqForm() @ MobCcsSupportDocDAO:" + sql);
			List<String> result = simpleJdbcTemplate.query(sql, mapper, params);
			return !"0".equals(result.get(0)) ? true : false;
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in isReqForm()");
			
		} catch (Exception e) {
			logger.info("Exception caught in isReqForm():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return false;
	}

}
