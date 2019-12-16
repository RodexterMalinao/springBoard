package com.bomwebportal.mob.ccs.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.ContactDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.util.Utility;
import com.bomwebportal.sbs.dto.CcsDeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.CcsDeliveryTimeSlotDTO;

public class DeliveryDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	private static String dateFormat = "dd/MM/yyyy";
	
	private static String getMobCcsDeliveryDTOSQL = 
		"select D.ORDER_ID,\n" +
		"       D.DELIVERY_IND,\n" + 
		"       D.URGENT_IND,\n" + 
		"       D.THIRD_IND,\n" + 
		"       D.DELIVERY_DATE,\n" + 
		"       D.DELIVERY_TIME_SLOT,\n" + 
		"       D.DELIVERY_CENTRE,\n" + 
		"       D.LOCK_IND,\n" + 
		"       D.CREATE_BY,\n" + 
		"       nvl(D.CREATE_DATE, sysdate) CREATE_DATE,\n" + 
		"       D.LAST_UPD_BY,\n" + 
		"       nvl(D.LAST_UPD_DATE, sysdate) LAST_UPD_DATE ,\n" + 
		"       D.LOCATION,\n" +       // add by Joyce 20120222
		"       ----\n" + 
		"       CA.ADDR_USAGE,\n" + 
		"       CA.AREA_CD,\n" + 
		"       CA.DIST_NO,\n" + 
		"       CA.SECT_CD,\n" + 
		"       CA.STR_NAME,\n" + 
		"       CA.HI_LOT_NO,\n" + 
		"       CA.STR_CAT_CD,\n" + 
		"       CA.BUILD_NO,\n" + 
		"       CA.FOREIGN_ADDR_FLAG,\n" + 
		"       CA.FLOOR_NO,\n" + 
		"       CA.UNIT_NO,\n" + 
        "       CA.FREE_ADDR_1,\n" +
		"       CA.FREE_ADDR_2,\n" +
		"       CA.FREE_ADDR_3,\n" +
		"       CA.FREE_INPUT_IND,\n" +
		"       CA.PO_BOX_NO,\n" + 
		"       CA.ADDR_TYPE,\n" + 
		"       CA.STR_NO,\n" + 
		"       CA.SECT_DEP_IND,\n" + 
		"       CA.AREA_DESC,\n" + 
		"       CA.DIST_DESC,\n" + 
		"       CA.SECT_DESC,\n" + 
		"       CA.STR_CAT_DESC, \n" +
		"		D.BYPASS_IND, \n" +
		"		D.ACTUAL_DELIVERY_DATE, \n" + //add by wilson 20120524, D.ACTUAL_DELIVERY_DATE
		"		D.CV_ID, \n" + 
		"		D.DM_ID, \n" + 
		"		D.DUMMY_DELIVERY_DATE_IND \n" + 
		"		from BOMWEB_DELIVERY D, BOMWEB_CUST_ADDR CA\n" + 
		"		where D.ORDER_ID = CA.ORDER_ID(+)\n" + 
		"		and CA.ADDR_USAGE(+) = 'DA'\n" + 
		"		and D.ORDER_ID = ?\n" + 
		"		and ROWNUM = 1";

	public DeliveryUI getMobCcsDelivery(String orderID) throws DAOException {
		List<DeliveryUI> deliveryUIList= new ArrayList<DeliveryUI>();
		logger.debug(" getMobCcsDeliveryDTO is called");
		/**** ==ParameterizedRowMapper start== *********************************************************/

		ParameterizedRowMapper<DeliveryUI> mapper = new ParameterizedRowMapper<DeliveryUI>() {
			public DeliveryUI mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				DeliveryUI dto = new DeliveryUI();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setDeliveryInd(rs.getString("DELIVERY_IND"));

				dto.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
				dto.setDeliveryDateStr(Utility.date2String(dto.getDeliveryDate(), dateFormat));
				dto.setDeliveryTimeSlot(rs.getString("DELIVERY_TIME_SLOT"));
				dto.setDeliveryCentre(rs.getString("DELIVERY_CENTRE"));
				dto.setLockInd(rs.getString("LOCK_IND"));
				/*if ("Y".equals(rs.getString("URGENT_IND"))) {
					dto.setUrgentInd(true);
				} else {

					dto.setUrgentInd(false);
				}*/
				dto.setUrgentInd("Y".equalsIgnoreCase(rs.getString("URGENT_IND")));
				dto.setRecieveByThirdPartyInd("Y".equalsIgnoreCase(rs.getString("THIRD_IND")));//edit by wilson 20120316
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getDate("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("last_upd_by"));
				dto.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
				//address info
				dto.setAreaCode(rs.getString("AREA_CD"));
				dto.setDistrictCode(rs.getString("DIST_NO"));
				dto.setSectionCode(rs.getString("SECT_CD"));
				dto.setStreetName(rs.getString("STR_NAME"));
				dto.setLotNum(rs.getString("HI_LOT_NO"));
				dto.setStreetCatgCode(rs.getString("STR_CAT_CD"));
				dto.setStreetCatgDesc(rs.getString("STR_CAT_DESC"));
				dto.setBuildingName(rs.getString("BUILD_NO"));
				// "N"rs.getString("FOREIGN_ADDR_FLAG"));
				dto.setAddress1(rs.getString("FREE_ADDR_1"));
				dto.setAddress2(rs.getString("FREE_ADDR_2"));
				dto.setAddress3(rs.getString("FREE_ADDR_3"));
				dto.setCustAddressFlag2("Y".equalsIgnoreCase(rs.getString("FREE_INPUT_IND")));
				dto.setFloor(rs.getString("FLOOR_NO"));
				dto.setFlat(rs.getString("UNIT_NO"));
				// ""rs.getString("PO_BOX_NO"));
				// "S"rs.getString("ADDR_TYPE"));
				dto.setStreetNum(rs.getString("STR_NO"));
				// "N"rs.getString("SECT_DEP_IND"));
				dto.setAreaDesc(rs.getString("area_desc"));
				dto.setDistrictDesc(rs.getString("dist_desc"));
				dto.setSectionDesc(rs.getString("sect_desc"));

				dto.setQuickSearch(dto.getSingleLineAddress());
			
				
				// add by Joyce 20120222
				dto.setLocation(rs.getString("LOCATION"));
				dto.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND"))); //add by wilson 20120301
				dto.setActualDeliveryDate(rs.getDate("ACTUAL_DELIVERY_DATE")); //add by wilson 20120524
				
				dto.setCvId(rs.getString("CV_ID"));
				dto.setDmId(rs.getString("DM_ID"));
				dto.setDummyDeliveryDateInd("Y".equalsIgnoreCase(rs.getString("DUMMY_DELIVERY_DATE_IND")));
				return dto;
			}
		};
		/**** ==ParameterizedRowMapper end== *********************************************************/

		try {
			logger.debug("getMobCcsDeliveryDTO() @ MobCcsDeliveryDTO: "
					+ getMobCcsDeliveryDTOSQL);
		/*	return (DeliveryUI) simpleJdbcTemplate.query(getMobCcsDeliveryDTOSQL,
					mapper, orderID);*/
			
			List <DeliveryUI> resultList = simpleJdbcTemplate.query(getMobCcsDeliveryDTOSQL,
					mapper, orderID);
			if (resultList != null) {
				return resultList.get(0);
			}
//			deliveryUIList =simpleJdbcTemplate.query(getMobCcsDeliveryDTOSQL , mapper, orderID);
			
			 
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsDeliveryDTO()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsDeliveryDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
		
		
	}
	 
	// === Start of INSERT function ===
	private static final String insertBomwebDeliverySQL = 
			"		INSERT INTO BOMWEB_DELIVERY ( \n"
			+ "		ORDER_ID, \n"
			+ "		DELIVERY_IND, \n"
			+ "		URGENT_IND, \n"
			+ "		THIRD_IND, \n"
			+ "		DELIVERY_DATE, \n"
			+ "		DELIVERY_TIME_SLOT, \n"
			+ "		DELIVERY_CENTRE, \n"
			+ "		LOCK_IND, \n"
			+ "		CREATE_BY, \n"
			+ "		CREATE_DATE, \n"
			+ "		LAST_UPD_BY, \n"
			+ "		LAST_UPD_DATE, \n"
			+ "		LOCATION, \n"
			+ "		BYPASS_IND, \n"
			+ "		ACTUAL_DELIVERY_DATE, \n"
			+ "		CV_ID, \n"
			+ "		DM_ID, \n"
			+ "		DUMMY_DELIVERY_DATE_IND \n"
			+ "		) VALUES ( \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?, \n"
			+ "		?  \n"
			+ "		)  \n";

	public int insertBomwebDelivery(DeliveryUI dto) throws DAOException {
		logger.debug("insertBomwebDelivery is called");
		
		try {
			/*if ("undefined".equals(dto.getDeliveryTimeSlot())) {
				dto.setDeliveryTimeSlot("");//for error handle
			}*/
			
			logger.info("insertBomwebDelivery() @ BomwebDeliveryDAO: " + insertBomwebDeliverySQL);
			return simpleJdbcTemplate.update(
					insertBomwebDeliverySQL,
					// start sql mapping
					dto.getOrderId(), 
					dto.getDeliveryInd(),  
					dto.isUrgentInd() == true ? "Y" : "N", 
					dto.isRecieveByThirdPartyInd() == true ? "Y" : "N", 
					dto.getDeliveryDate(), 
					dto.getDeliveryTimeSlot(), 
					dto.getDeliveryCentre(), 
					dto.getLockInd(), 
					dto.getCreateBy(), 
					dto.getCreateDate(), 
					dto.getLastUpdBy(), 
					dto.getLastUpdDate(), 
					dto.getLocation(), // add by wilson 20120523
					dto.isByPassValidation() == true ? "Y" : "N", 
					dto.getActualDeliveryDate(), //add by wilson 20120524
					dto.getCvId(),
					dto.getDmId(),
					dto.isDummyDeliveryDateInd() == true ? "Y" : "N"
					// end sql mapping
					);
		} catch (Exception e) {
			logger.error("Exception caught in insertBomwebDelivery()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	// === End of INSERT function ===
	// === Start of UPDATE function ===
	/*
	 * private static final String updateBomwebDeliverySQL =
	 * " UPDATE BOMWEB_DELIVERY SET \n" + "     ORDER_ID = ? ,\n" +
	 * "     DELIVERY_IND = ? ,\n" + "     URGENT_IND = ? ,\n" +
	 * "     THIRD_IND = ? ,\n" + "     DELIVERY_DATE = ? ,\n" +
	 * "     DELIVERY_TIME_SLOT = ? ,\n" + "     DELIVERY_CENTRE = ? ,\n" +
	 * "     LOCK_IND = ? ,\n" + "     LAST_UPD_BY = ? ,\n" +
	 * "     LAST_UPD_DATE = ? \n" // YOU NEED TO REMOVE LAST ',' +
	 * " WHERE  ORDER_ID =? \n";
	 * 
	 * public int updateBomwebDelivery(DeliveryUI dto) throws DAOException {
	 * logger.info("updateBomwebDelivery is called");
	 * 
	 * try { String urgentInd = "Y"; if (dto.isUrgentInd() == true) { urgentInd
	 * = "Y";
	 * 
	 * } else {
	 * 
	 * urgentInd = "N"; }
	 * logger.info("updateBomwebDelivery() @ BomwebDeliveryDAO: " +
	 * updateBomwebDeliverySQL); return simpleJdbcTemplate.update(
	 * updateBomwebDeliverySQL, // start sql mapping dto.getOrderId(),
	 * dto.getDeliveryInd(), urgentInd, dto.getThirdInd(),
	 * dto.getDeliveryDate(), dto.getDeliveryTimeSlot(),
	 * dto.getDeliveryCentre(), dto.getLockInd(), dto.getLastUpdBy(),
	 * dto.getLastUpdDate(), // ---------------- // end sql mapping
	 * dto.getOrderId());
	 * 
	 * } catch (Exception e) {
	 * logger.error("Exception caught in updateBomwebDelivery()", e); throw new
	 * DAOException(e.getMessage(), e); }
	 * 
	 * }
	 * 
	 * // === End of UPDATE function === // === Start of DELETE function ===
	 * private static final String deleteBomwebDeliverySQL =
	 * "  delete from BOMWEB_DELIVERY where order_id=?";
	 * 
	 * public int deleteBomwebDelivery(DeliveryUI dto) throws DAOException {
	 * logger.info("deleteBomwebDelivery is called");
	 * 
	 * try { logger.info("deleteBomwebDelivery() @ BomwebDeliveryDAO: " +
	 * deleteBomwebDeliverySQL); return
	 * simpleJdbcTemplate.update(deleteBomwebDeliverySQL, // start sql Mapping
	 * dto.getOrderId() // end sql Mapping ); } catch (Exception e) {
	 * logger.error("Exception caught in deleteBomwebDelivery()", e); throw new
	 * DAOException(e.getMessage(), e); } }
	 */
	// === End of DELETE function ===
	
	public int insertBomWebCustomerAddress(DeliveryUI dto)
			throws DAOException {
		logger.debug("insertBomWebCustomerAddress() is called");

		// define SQL string
		String SQL = "insert into BOMWEB_CUST_ADDR\n"
				+ "  (ORDER_ID,\n"
				+ "   ADDR_USAGE,\n"
				+ "   AREA_CD,\n"
				+ "   DIST_NO,\n"
				+ "   SECT_CD,\n"
				+ "   STR_NAME,\n"
				+ "   HI_LOT_NO,\n"
				+ "   STR_CAT_CD,\n"
				+ "   BUILD_NO,\n"
				+ "   FOREIGN_ADDR_FLAG,\n"
				+ "   FLOOR_NO,\n"
				+ "   UNIT_NO,\n"
				+ "   PO_BOX_NO,\n"
				+ "   ADDR_TYPE,\n"
				+ "   STR_NO,\n"
				+ "   SECT_DEP_IND,\n"
				+ "   area_desc,\n"
				+ "   dist_desc,\n"
				+ "   sect_desc,\n"
				+ "   STR_CAT_DESC,\n"
				+ "   CREATE_DATE,\n"
				+ "   FREE_INPUT_IND,\n"
				+ "   FREE_ADDR_1,\n"
				+ "   FREE_ADDR_2,\n"
				+ "   FREE_ADDR_3)\n"
				+ "values\n"
				+ "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?, sysdate,?,?,?,?)";

		// insert to table
		try { 
			if (dto.isCustAddressFlag2()){			
				return simpleJdbcTemplate.update(SQL, dto.getOrderId(), "DA",
						dto.getAreaCode(), dto.getDistrictCode(),"","","","","","N","","","","S","","N",dto.getAreaDesc(), dto.getDistrictDesc(),
						"","",dto.isCustAddressFlag2()?"Y":"N",
						dto.getAddress1(),dto.getAddress2(),dto.getAddress3()
						);
			} else {
				return simpleJdbcTemplate.update(SQL, dto.getOrderId(), "DA",
						dto.getAreaCode(), dto.getDistrictCode(),
						dto.getSectionCode(), dto.getStreetName(), dto.getLotNum(),
						dto.getStreetCatgCode(), dto.getBuildingName(), "N",
						dto.getFloor(), dto.getFlat(), "", "S", dto.getStreetNum(),
						"N", dto.getAreaDesc(), dto.getDistrictDesc(),
						dto.getSectionDesc(), dto.getStreetCatgDesc(),dto.isCustAddressFlag2()?"Y":"N","","","" );

			}
		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebCustomerAddress()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int insertBomWebContact(ContactDTO dto)
			throws DAOException {
		logger.debug("insertBomWebContact() is called");

		
		String SQL = "insert into BOMWEB_CONTACT\n" + "  (ORDER_ID,\n"
				+ "   CONTACT_NAME,\n" + "   TITLE,\n"
				+ "   CONTACT_PHONE,\n"
				+ " OTHER_PHONE,\n" 
				+ "   EMAIL_ADDR,\n" + "   ACTION_IND,\n" + "   CREATE_DATE, contact_type, id_doc_type, id_doc_num)\n"
				+ "values\n" + "  (?, ?, ?, ?, ?, ?, ?, sysdate, ?,?,?)";

		
		try {
			return simpleJdbcTemplate.update(SQL, dto.getOrderId(),
					dto.getContactName(), dto.getTitle(),
					dto.getContactPhone(), dto.getOtherPhone(), 
					dto.getEmailAddr(), "A",  dto.getContactType(), dto.getIdDocType(), dto.getIdDocNum());

		} catch (Exception e) {
			logger.error("Exception caught in insertBomWebContact()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public List<ContactDTO> findContactDTOList(String orderId) throws DAOException {
		logger.debug("findContactDTO(String orderId)");
				
		String sql = "select * " +
					 "from bomweb_contact where order_id = ?";
		
		ParameterizedRowMapper<ContactDTO> mapper = new ParameterizedRowMapper<ContactDTO>() {

			public ContactDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				ContactDTO dto = new ContactDTO();
				
				dto.setOrderId(rs.getString("order_id"));
				dto.setTitle(rs.getString("title"));
				dto.setContactName(rs.getString("contact_name"));
				dto.setContactPhone(rs.getString("contact_phone"));
				dto.setEmailAddr(rs.getString("email_addr"));
				dto.setActionInd(rs.getString("action_ind"));
				dto.setCreateDate(rs.getDate("create_date"));
				dto.setOtherPhone(rs.getString("other_phone"));
				dto.setCreateBy(rs.getString("create_by"));
				dto.setLastUpdBy(rs.getString("last_upd_by"));
				dto.setContactType(rs.getString("contact_type"));
				dto.setLockInd(rs.getString("lock_ind"));
				dto.setIdDocType(rs.getString("id_doc_type"));
				dto.setIdDocNum(rs.getString("id_doc_num"));
				
				return dto;
			}
			
		};
		
		try {
			logger.debug("findContactDTO() @ ContactDTO: "
					+ sql);
			return simpleJdbcTemplate.query(sql,
					mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in findContactDTO()");
		} catch (Exception e) {
			logger.info("Exception caught in findContactDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return null;
	}
	
	public List<String[]> findTimeSlot(String districtCd, String slotType, java.util.Date appDate) throws DAOException {
		logger.debug("findTimeSlot(String district, String slotType)");
		
		String sql  =  " select slot.timeslot, slot.timefrom, slot.timeto "
					 + "from w_district_timeslot_assgn addr, w_delivery_timeslot slot "
					 + "where ADDR.DISTDESC = (SELECT DISTDSC FROM W_ADDRLKUP_DISTRICT WHERE CODE = ?) "
					 + "and addr.timeslot = slot.timeslot "
					 + "and slot.slot_type = ? "
					 + "and trunc(?) between trunc(addr.start_date) "
					 + "and trunc(nvl(addr.end_date, sysdate)) "
					 + "order by slot.timeslot asc";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				String[] result = new String[3];
				
				result[0] = rs.getString("timeslot");
				result[1] = rs.getString("timefrom");
				result[2] = rs.getString("timeto");
				
				return result;
			}
		};
		
		try {
			logger.debug("findTimeSlot() : "
					+ sql);
			return simpleJdbcTemplate.query(sql, mapper, districtCd, slotType, appDate);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in findTimeSlot()");
		} catch (Exception e) {
			logger.info("Exception caught in findTimeSlot():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public List<String[]> findUrgentTimeSlot() throws DAOException {
		logger.debug("findUrgentTimeSlot()");
		
		String sql  =  " select slot.timeslot, slot.timefrom, slot.timeto "
					 + "from w_delivery_timeslot slot "
					 + "where slot.slot_type = 'DED' order by timeslot";

		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				String[] result = new String[3];
				
				result[0] = rs.getString("timeslot");
				result[1] = rs.getString("timefrom");
				result[2] = rs.getString("timeto");
				
				return result;
			}
		};
		
		try {
			logger.debug("findUrgentTimeSlot() : "
					+ sql);
			return simpleJdbcTemplate.query(sql, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in findUrgentTimeSlot()");
		} catch (Exception e) {
			logger.info("Exception caught in findUrgentTimeSlot():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public String[] getTimeSlotDesc (String timeSlot) throws DAOException {
		
		logger.debug("getTimeSlotDesc (String timeSlot)");
		
		String sql  =  " select slot.timefrom, slot.timeto "
				 + "from w_delivery_timeslot slot "
				 + "where slot.timeslot = ? ";
	
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				String[] result = new String[2];
				
				result[0] = rs.getString("timefrom");
				result[1] = rs.getString("timeto");
				
				return result;
			}
		};
		
		try {
			logger.debug("getTimeSlotDesc() : " + sql);
			List<String[]> result = simpleJdbcTemplate.query(sql, mapper, timeSlot);
			if (result != null) {
				return result.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getTimeSlotDesc()");
		} catch (Exception e) {
			logger.info("Exception caught in getTimeSlotDesc():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public String getDateType(String date) throws DAOException{
		
		String sql = "Select day_type from w_job_schedule " +
				"where job_date = TO_DATE(?, 'DD/MM/YYYY')";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
								
				return rs.getString("day_type");
			}
		};
		
		try {
			logger.debug("getTimeSlotDesc() : " + sql);
			List<String> result = simpleJdbcTemplate.query(sql, mapper, date);
			if (result != null) {
				return result.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getTimeSlotDesc()");
		} catch (Exception e) {
			logger.info("Exception caught in getTimeSlotDesc():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	public List<String> getAvailableUrgentTimeSlot(String timeslot) throws DAOException {
		logger.debug("getAvailableUrgentTimeSlot()");
			
		StringBuffer sql  =  new StringBuffer(" select slot.timeslot ");
					 sql.append("from w_delivery_timeslot slot ");
					 sql.append("where slot.slot_type = 'DED' "); 
					 sql.append("and slot.timeslot > ? ");
					 sql.append(" order by timeslot");
					  
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
								
				return rs.getString("timeslot");
			}
		};
		
		try {
			logger.debug("getAvailableUrgentTimeSlot() : "
					+ sql);
			return simpleJdbcTemplate.query(sql.toString(), mapper, timeslot);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getAvailableUrgentTimeSlot()");
		} catch (Exception e) {
			logger.info("Exception caught in getAvailableUrgentTimeSlot():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return null;
	}
	
	@Deprecated
	public Date[] getDeliveryDateRange(String payMethod, String itemCode) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("GET_DELIVERY_DATE_RANGE");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_pay_method", Types.VARCHAR),
					new SqlParameter("i_item_code", Types.VARCHAR),
					new SqlOutParameter("o_e_del_date", Types.DATE),
					new SqlOutParameter("o_l_del_date", Types.DATE));
			
		
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_pay_method", payMethod);
			inMap.addValue("i_item_code", itemCode);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			Date[] resultDate = new Date[2];
			
			Date startDate = (Date)out.get("o_e_del_date");
			Date endDate = (Date)out.get("o_l_del_date");
			
			resultDate[0] = startDate;
			resultDate[1] = endDate;
			
			logger.info("PKG_SB_MOB_ORDER.GET_DELIVERY_DATE_RANGE() o_e_del_date = " + startDate);
			logger.info("PKG_SB_MOB_ORDER.GET_DELIVERY_DATE_RANGE() o_l_del_date = " + endDate);
			
			return resultDate;
			
		} catch (Exception e) {
			logger.error("Exception caught in getDeliveryDateRange()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	/*public Date[] normalDeliveryDateRange(String orderId, String payMethod, String itemCode, java.util.Date appDate) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("ORDER_DELIVERY_DATE_RANGE");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_appn_date", Types.DATE),
					new SqlParameter("i_pay_method", Types.VARCHAR),
					new SqlParameter("i_item_code", Types.VARCHAR),
					new SqlOutParameter("o_e_del_date", Types.DATE),
					new SqlOutParameter("o_l_del_date", Types.DATE));
			
		
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			inMap.addValue("i_pay_method", payMethod);
			inMap.addValue("i_item_code", itemCode);
			inMap.addValue("i_appn_date", appDate);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			Date[] resultDate = new Date[2];
			
			Date startDate = (Date)out.get("o_e_del_date");
			Date endDate = (Date)out.get("o_l_del_date");
			
			resultDate[0] = startDate;
			resultDate[1] = endDate;
			
			logger.info("PKG_SB_MOB_ORDER.NORMAL_DELIVERY_DATE_RANGE() o_e_del_date = " + startDate);
			logger.info("PKG_SB_MOB_ORDER.NORMAL_DELIVERY_DATE_RANGE() o_l_del_date = " + endDate);
			
			return resultDate;
			
		} catch (Exception e) {
			logger.error("Exception caught in normalDeliveryDateRange()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}*/
	@Deprecated
	public DeliveryDateRangeDTO normalDeliveryDateRange(String orderId, String payMethod, String itemCode, java.util.Date appDate) throws DAOException {
		DeliveryDateRangeDTO result= new DeliveryDateRangeDTO();
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_ORDER")
            	.withProcedureName("ACQ_DELIVERY_DATE_RANGE");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_app_date", Types.DATE),
					new SqlParameter("i_pay_mthd", Types.VARCHAR),
					new SqlParameter("i_item_code", Types.VARCHAR),
					new SqlOutParameter("o_min_date", Types.DATE),
					new SqlOutParameter("o_max_date", Types.DATE),
					new SqlOutParameter("o_ph_date", Types.VARCHAR));
			
		
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			logger.info("PKG_SB_MOB_COS_ORDER.ACQ_DELIVERY_DATE_RANGE() i_item_code = " + itemCode);
			logger.info("PKG_SB_MOB_COS_ORDER.ACQ_DELIVERY_DATE_RANGE() i_pay_mthd = " + payMethod);
			logger.info("PKG_SB_MOB_COS_ORDER.ACQ_DELIVERY_DATE_RANGE() i_order_id = " + orderId);
			logger.info("PKG_SB_MOB_COS_ORDER.ACQ_DELIVERY_DATE_RANGE() i_app_date = " + appDate);
			inMap.addValue("i_item_code", itemCode);
			inMap.addValue("i_pay_mthd", payMethod);
			inMap.addValue("i_order_id", orderId);
			inMap.addValue("i_app_date", appDate);
			
			SqlParameterSource in = inMap;
			
			Map<String, Object> out = jdbcCall.execute(in);
			
			Date startDate = (Date)out.get("o_min_date");
			Date endDate = (Date)out.get("o_max_date");
			String phDateString = (String)out.get("o_ph_date");
			result.setStartDate(startDate);
			result.setEndDate(endDate);
			result.setPhDateString(phDateString);
			
			logger.info("PKG_SB_MOB_COS_ORDER.ACQ_DELIVERY_DATE_RANGE() o_min_date = " + startDate);
			logger.info("PKG_SB_MOB_COS_ORDER.ACQ_DELIVERY_DATE_RANGE() o_max_date = " + endDate);
			logger.info("PKG_SB_MOB_COS_ORDER.ACQ_DELIVERY_DATE_RANGE() phDateString = " + phDateString);
			
			return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in normalDeliveryDateRange()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public void updateOrderDelivery(DeliveryUI deliveryUI)
			throws DAOException {
		logger.debug("updateOrderDeliveryDateTime is called");

		String SQL = "update bomweb_delivery set delivery_date = ?, delivery_time_slot = ?, " 
				+ "last_upd_by = ?, urgent_ind = ?, third_ind = ?, last_upd_date = sysdate where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, deliveryUI.getDeliveryDate(), deliveryUI.getDeliveryTimeSlot(), 
					deliveryUI.getLastUpdBy(), deliveryUI.isUrgentInd() ? "Y" : "N", 
					deliveryUI.isRecieveByThirdPartyInd() ? "Y" : "N", deliveryUI.getOrderId());

		} catch (Exception e) {
			logger.error("Exception caught in updateBomWebOrderStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateContactDto(ContactDTO dto) throws DAOException {
		logger.debug("updateOrderDeliveryDateTime is called");

		String SQL = "update bomweb_contact set contact_name = ?, title = ?, contact_phone = ?, " 
				+ "other_phone = ?, email_addr = ?, id_doc_type = ?, " 
				+ "id_doc_num = ?, last_upd_by = ?, last_upd_date = sysdate " 
				+ "where order_id = ? and contact_type = ?";

		try {
			simpleJdbcTemplate.update(SQL, dto.getContactName(), dto.getTitle(), dto.getContactPhone(),
					dto.getOtherPhone(), dto.getEmailAddr(), dto.getIdDocType(),
					dto.getIdDocNum(), dto.getLastUpdBy(), dto.getOrderId(), dto.getContactType());

		} catch (Exception e) {
			logger.error("Exception caught in updateOrderDeliveryDateTime()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public void updateBomWebCustomerAddress(DeliveryUI dto)	throws DAOException {
		logger.debug("updateBomWebCustomerAddress is called");
				
		String SQL = "update bomweb_cust_addr set FREE_INPUT_IND = ?,FREE_ADDR_1 = ?, FREE_ADDR_2 = ?, FREE_ADDR_3 = ?,area_cd = ?, dist_no = ?, sect_cd = ?, " 
				+ "str_name = ?, hi_lot_no = ?, str_cat_cd = ?, build_no = ?, " +
				"floor_no = ?, unit_no = ?,str_no = ?, area_desc = ?, dist_desc = ?, " +
				"sect_desc = ?, str_cat_desc = ?, last_upd_by = ?, last_upd_date = sysdate " +
				"where order_id = ? and addr_usage = 'DA'";

		try {
			if (dto.isCustAddressFlag2() == true){
				simpleJdbcTemplate.update(SQL, dto.isCustAddressFlag2()?"Y":"N",dto.getAddress1(),dto.getAddress2(),dto.getAddress3(),dto.getAreaCode(), dto.getDistrictCode(),"",
						"","","","","","","",dto.getAreaDesc(), dto.getDistrictDesc(),
						"","",dto.getLastUpdBy(), dto.getOrderId());
			
			} else {
							
				simpleJdbcTemplate.update(SQL, "","","","",dto.getAreaCode(), dto.getDistrictCode(), dto.getSectionCode(),
						dto.getStreetName(), dto.getLotNum(), dto.getStreetCatgCode(), dto.getBuildingName(),
						dto.getFloor(), dto.getFlat(), dto.getStreetNum(), dto.getAreaDesc(), dto.getDistrictDesc(), 
						dto.getSectionDesc(), dto.getStreetCatgDesc(), dto.getLastUpdBy(), dto.getOrderId());
			}
        } 
		catch (Exception e) {
			logger.error("Exception caught in updateBomWebCustomerAddress()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	public List<String> getSalesMemoNo(String osOrderId) throws Exception{
		//Athena 20131111 online sales report
		logger.debug("getSalesMemoNo is called");
		List<String> SmNoList = new ArrayList<String>();
		String SQL ="SELECT SM_NUM " 
				+"FROM Bomweb_St_Order_Pos_Assign " 
				+"WHERE Order_Id = :osOrderId " 
				+"AND sm_type_desc IN ('Normal Sales', 'Advance Sales' , 'Fast Track','Cash On Delivery')";

		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("osOrderId", osOrderId);
		
		ParameterizedRowMapper<String> params = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("SM_NUM");
			}
		};
		
		try {
			SmNoList = this.simpleJdbcTemplate.query(SQL, params, mapSql);
				return SmNoList;
		} catch (Exception e) {
			logger.error("Exception caught in getSalesMemoNo()", e);
			throw new DAOException(e.getMessage(), e);
		}

/*		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String smNo = new String();
				smNo = rs.getString("SM_NUM");
				return smNo;
			}};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", osOrderId);
			SmNoList = simpleJdbcTemplate.query(SQL, mapper,params);
			return SmNoList;
		} catch (EmptyResultDataAccessException erdae) {
			SmNoList = null;
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getSalesMemoNo()", e);
			throw new Exception(e.getMessage(), e);
		}*/
		
		
	}
	
public String getEnableGiftPackInd(java.util.Date appDate) throws DAOException {
		
		List<String> enableGiftPackInd = new ArrayList<String>();
		String SQLModel = "SELECT pkg_sb_mob_sbo_basket.get_enable_gift_pack_ind(:in_app_date) v_enable_gift_pack FROM dual";
		

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("v_enable_gift_pack");
			}
		};
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("in_app_date", appDate);

		try {

			logger.debug("getEnableGiftPackInd @ BomwebDeliveryDAO: " + SQLModel);
			enableGiftPackInd = simpleJdbcTemplate.query(SQLModel, mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			return null;
			
		} catch (Exception e) {
			logger.info("Exception caught in getSimOnlyAssignedSimItem():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (enableGiftPackInd.size() == 0 || enableGiftPackInd == null) {
			return null;
		}else{
			return enableGiftPackInd.get(0);
		}
		
	}
	
	public CcsDeliveryDateRangeDTO getCcsDeliveryDateRange(String orderType, String delMode, String delInd, String hsInd, String hsItemCd, String payMthd, String fsInd, String mnpInd, String orderId, java.util.Date appDate) throws DAOException {
		CcsDeliveryDateRangeDTO result= new CcsDeliveryDateRangeDTO();
		try {
			logger.debug("getCcsDeliveryDateRange @ DeliveryDAO is called");
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
	        	.withCatalogName("PKG_SB_MOB_CCS_UTIL")
	        	.withProcedureName("CCS_DELIVERY_DATE_RANGE");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_order_type", Types.VARCHAR),
					new SqlParameter("i_del_mode", Types.VARCHAR),
					new SqlParameter("i_del_ind", Types.VARCHAR),
					new SqlParameter("i_hs_ind", Types.VARCHAR),
					new SqlParameter("i_hs_item_cd", Types.VARCHAR),
					new SqlParameter("i_pay_mthd", Types.VARCHAR),
					new SqlParameter("i_fs_ind", Types.VARCHAR),
					new SqlParameter("i_mnp_ind", Types.VARCHAR),
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlParameter("i_app_date", Types.DATE),
					new SqlOutParameter("o_min_date", Types.DATE),
					new SqlOutParameter("o_max_date", Types.DATE),
					new SqlOutParameter("o_ph_date", Types.VARCHAR),
					new SqlOutParameter("o_srd_min_rule", Types.VARCHAR),
					new SqlOutParameter("o_srd_max_rule", Types.VARCHAR),
					new SqlOutParameter("o_min_timeslot", Types.VARCHAR),
					new SqlOutParameter("o_rule_no", Types.INTEGER),
					new SqlOutParameter("o_retvalue", Types.INTEGER),
					new SqlOutParameter("o_errcode", Types.INTEGER),
					new SqlOutParameter("o_errtext", Types.VARCHAR)
			);
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_type", orderType);
			inMap.addValue("i_del_mode", delMode);
			inMap.addValue("i_del_ind", delInd);
			inMap.addValue("i_hs_ind", hsInd);
			inMap.addValue("i_hs_item_cd", hsItemCd);
			inMap.addValue("i_pay_mthd", payMthd);
			inMap.addValue("i_fs_ind", fsInd);
			inMap.addValue("i_mnp_ind", mnpInd);
			inMap.addValue("i_order_id", orderId);
			inMap.addValue("i_app_date", appDate);
			SqlParameterSource in = inMap;
			Map<String, Object> out = jdbcCall.execute(in);
			Date startDate = (Date)out.get("o_min_date");
			Date endDate = (Date)out.get("o_max_date");
			String phDateString = (String)out.get("o_ph_date");
			String startDateString = (String)out.get("o_srd_min_rule");
			String endDateString = (String)out.get("o_srd_max_rule");
			String timeSlot = (String)out.get("o_min_timeslot");
			Integer ruleNo = (Integer)out.get("o_rule_no");
			Integer retValue = (Integer)out.get("o_retvalue");
			Integer errCode = (Integer)out.get("o_errcode");
			String errText = (String)out.get("o_errtext");
			result.setStartDate(startDate);
			result.setEndDate(endDate);
			result.setPhDateString(phDateString);
			result.setStartDateString(startDateString);
			result.setEndDateString(endDateString);
			result.setTimeSlot(timeSlot);
			result.setRuleNo(ruleNo);
			result.setRetValue(retValue);
			result.setErrCode(errCode);
			result.setErrText(errText);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_min_date = " + startDate);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_max_date = " + endDate);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_ph_date = " + phDateString);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_srd_min_rule = " + startDateString);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_srd_max_rule = " + endDateString);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_min_timeslot = " + timeSlot);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_rule_no = " + ruleNo);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_retvalue = " + retValue);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_errcode = " + errCode);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_DATE_RANGE() o_errtext = " + errText);
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in getCcsDeliveryDateRange() @ DeliveryDAO", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<CcsDeliveryTimeSlotDTO> getCcsDeliveryTimeslot (String delMode, String delInd, java.util.Date delDate, String distNo, java.util.Date minDate, String minTimeslot) throws DAOException {
		 List<CcsDeliveryTimeSlotDTO> result = new  ArrayList<CcsDeliveryTimeSlotDTO>();
		try {
			logger.debug("getCcsDeliveryTimeSlotList @ DeliveryDAO is called");
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
           	.withCatalogName("PKG_SB_MOB_CCS_UTIL")
           	.withProcedureName("CCS_DELIVERY_TIMESLOT_QUOTA");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_del_mode", Types.VARCHAR),
					new SqlParameter("i_del_ind", Types.VARCHAR),
					new SqlParameter("i_del_date", Types.DATE),
					new SqlParameter("i_dist_no", Types.VARCHAR),
					new SqlParameter("i_min_date", Types.DATE),
					new SqlParameter("i_min_timeslot", Types.VARCHAR),
					new SqlOutParameter("o_time_slot", Types.VARCHAR),
					new SqlOutParameter("o_time_range", Types.VARCHAR),
					new SqlOutParameter("o_quota_remain", Types.VARCHAR),
					new SqlOutParameter("o_retvalue", Types.INTEGER),
					new SqlOutParameter("o_errcode", Types.INTEGER),
					new SqlOutParameter("o_errtext", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			logger.info("i_del_mode " + delMode);
			logger.info("i_del_ind " + delInd);
			logger.info("i_del_date " + delDate);
			logger.info("i_dist_no " + distNo);
			logger.info("i_min_date " + minDate);
			logger.info("i_min_timeslot " + minTimeslot);
			inMap.addValue("i_del_mode", delMode);
			inMap.addValue("i_del_ind", delInd);
			inMap.addValue("i_del_date", delDate);
			inMap.addValue("i_dist_no", distNo);
			inMap.addValue("i_min_date", minDate);
			inMap.addValue("i_min_timeslot", minTimeslot);
			SqlParameterSource in = inMap;
			Map<String, Object> out = jdbcCall.execute(in);
			String timeslot = (String)out.get("o_time_slot");
			String timeRange = (String)out.get("o_time_range");
			String quotaRemain = (String)out.get("o_quota_remain");
			Integer retValue = (Integer)out.get("o_retvalue");
			Integer errCode = (Integer)out.get("o_errcode");
			String errText = (String)out.get("o_errtext");
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_TIMESLOT_QUOTA() o_retvalue = " + retValue);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_TIMESLOT_QUOTA() o_errcode = " + errCode);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_TIMESLOT_QUOTA() o_errtext = " + errText);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_TIMESLOT_QUOTA() o_time_slot = " + timeslot);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_TIMESLOT_QUOTA() o_time_range = " + timeRange);
			logger.info("PKG_SB_MOB_CCS_UTIL.CCS_DELIVERY_TIMESLOT_QUOTA() o_quota_remain = " + quotaRemain);
			if (errCode == null || errCode != 0 || retValue != 0) {
				return result;
			}
			timeslot = timeslot.replace("[","").replace("]", "");
			timeRange = timeRange.replace("[","").replace("]", "");
			quotaRemain = quotaRemain.replace("[","").replace("]", "");
			String[] timeslotArray = timeslot.split(",");
			String[] timeRangeArray = timeRange.split(",");
			String[] quotaRemainArray = quotaRemain.split(",");
			for (int i = 0 ; i< timeslotArray.length ; i++) {
				result.add(new CcsDeliveryTimeSlotDTO (timeslotArray[i],timeRangeArray[i],quotaRemainArray[i]));
			}
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in getCcsDeliveryTimeSlotList() @ DeliveryDAO ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
