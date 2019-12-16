package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.CustomerOrderHistoryDTO;
import com.bomwebportal.exception.DAOException;

public class CustomerOrderHistoryDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());

	/*
	 * modified by Joyce
	 * 20111201 (add service num)
	 * 20120119 (LTS revised)
	 * 20120813 (add checkpoint n reason code for CCS, appDate include time info)
	 */
	private static String getCustomerOrderHistoryDTOAllSQL = 
		"SELECT O.ORDER_ID, " 
		+"  O.OCID, " 
		+"  O.LOB, " 
		+"  OI.LOGIN_ID IMS_LOGIN_ID, " 
		+"  O.APP_DATE, " 
		+"  DECODE(O.LOB, 'LTS', OS.ORDER_STATUS, O.ORDER_STATUS) ORDER_STATUS, " 
		+"  DECODE(O.LOB, 'LTS', OS.ERR_MSG, O.ERR_MSG) ERR_MSG, " 
		+"  C.ID_DOC_NUM, " 
		+"  C.ID_DOC_TYPE, " 
		+"  DECODE(O.LOB, 'MOB', O.MSISDN, 'LTS', OS.SRV_NUM, C.SERVICE_NUM) SERVICE_NUM, " 
		+"  O.MSISDN, " 
		+"  C.LAST_NAME " 
		+"  ||' ' " 
		+"  ||C.FIRST_NAME cust_full_name, " 
		+"  o.check_point, o.reason_cd "
		+"FROM BOMWEB_ORDER O, " 
		+"  BOMWEB_CUSTOMER C, " 
		+"  BOMWEB_ORDER_IMS OI, " 
		+"  (SELECT bos.order_id ORDER_ID, " 
		+"    bos.srv_num SRV_NUM, " 
		+"    bols.sb_status ORDER_STATUS, " 
		+"    bols.rea_cd ERR_MSG " 
		+"  FROM BOMWEB_ORDER_SERVICE bos, " 
		+"    BOMWEB_ORDER_LATEST_STATUS bols " 
		+"  WHERE bos.order_id  = bols.order_id " 
		+"  AND bos.dtl_id      = bols.dtl_id " 
		+"  AND bos.type_of_srv = 'TEL' " 
		+"  ) OS " 
		+"WHERE O.ORDER_ID              = C.ORDER_ID " 
		+"AND O.ORDER_ID                = OI.ORDER_ID(+) " 
		+"AND O.ORDER_ID                = OS.ORDER_ID(+) " 
//		+" and not exists (" +
//				"	Select 1 from BOMWEB_CODE_LKUP sbof " +
//				"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD' and O.SHOP_CD = sbof.CODE_ID) -- Filter IMS non-retail orders\n"
		+"AND NVL(C.ID_DOC_NUM, 'XXX')  = NVL(?, NVL(C.ID_DOC_NUM, 'XXX')) " 
		+"AND NVL(O.MSISDN, 'XXX')      = NVL(?, NVL(O.MSISDN, 'XXX')) " 
		+"AND NVL(C.ID_DOC_TYPE, 'XXX') = NVL(?, NVL(C.ID_DOC_TYPE, 'XXX')) " 
		+"AND NVL(OI.LOGIN_ID, 'XXX')   = NVL(?, NVL(OI.LOGIN_ID, 'XXX')) " 
		+"AND NVL(OS.SRV_NUM, 'XXX')    = NVL(?, NVL(OS.SRV_NUM, 'XXX'))";

	public List<CustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL(
			String idDocNum, String serviceNum, String idDocType, String loginId, String serviceType) throws DAOException {
		logger.info(" getCustomerOrderHistoryDTO is called");
		List<CustomerOrderHistoryDTO> itemList = new ArrayList<CustomerOrderHistoryDTO>();

		ParameterizedRowMapper<CustomerOrderHistoryDTO> mapper = new ParameterizedRowMapper<CustomerOrderHistoryDTO>() {
			public CustomerOrderHistoryDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CustomerOrderHistoryDTO dto = new CustomerOrderHistoryDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setOcid(rs.getString("OCID"));
				dto.setLob(rs.getString("LOB"));
				dto.setImsLoginId(rs.getString("IMS_LOGIN_ID"));
				dto.setAppDate(rs.getTimestamp("APP_DATE"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				dto.setErrMsg(rs.getString("ERR_MSG"));
				dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
				dto.setServiceNum(rs.getString("SERVICE_NUM"));
				dto.setOrderHistCustName(rs.getString("cust_full_name"));
				dto.setCheckPoint(rs.getString("check_point"));
				dto.setReasonCd(rs.getString("reason_cd"));
				
				return dto;
			}
		};

		try {
			logger.info("getCustomerOrderHistoryDTO() @ CustomerOrderHistoryDTO: "
					+ getCustomerOrderHistoryDTOAllSQL);
			
			idDocNum = idDocNum.trim();
			serviceNum = serviceNum.trim();
			idDocType = idDocType.trim();
			
			if ("BR".equals(idDocType)) {
				idDocType = "BS";
			} else if ("Passport".equals(idDocType)) {
				idDocType = "PASS";
			} else if ("Certificate No".equals(idDocType)) {
				idDocType = "CERT";
			} else if ("NONE".equals(idDocType)) {
				idDocType = null;
			}
			
			if ("".equals(idDocNum)) {
				idDocNum = null;
			}
			
			if ("".equals(serviceNum)) {
				serviceNum = null;
			}
			
			if ("".equals(loginId)) {
				loginId = null;
			}
			
			if ("MRT".equalsIgnoreCase(serviceType)) {
				itemList = simpleJdbcTemplate.query(
						getCustomerOrderHistoryDTOAllSQL, mapper, idDocNum,
						serviceNum, idDocType, loginId, null);
			} else if ("TEL".equalsIgnoreCase(serviceType)){
				itemList = simpleJdbcTemplate.query(
						getCustomerOrderHistoryDTOAllSQL, mapper, idDocNum,
						null, idDocType, loginId, serviceNum);
			} else {
				itemList = simpleJdbcTemplate.query(
						getCustomerOrderHistoryDTOAllSQL, mapper, idDocNum,
						null, idDocType, loginId, null);
			}
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCustomerOrderHistoryDTO()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCustomerOrderHistoryDTO():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	
	private static String getCustomerOrderHistoryDTOAll_retail_only_SQL = 
			"SELECT O.ORDER_ID, " 
			+"  O.OCID, " 
			+"  O.LOB, " 
			+"  OI.LOGIN_ID IMS_LOGIN_ID, " 
			+"  O.APP_DATE, " 
			+"  DECODE(O.LOB, 'LTS', OS.ORDER_STATUS, O.ORDER_STATUS) ORDER_STATUS, " 
			+"  DECODE(O.LOB, 'LTS', OS.ERR_MSG, O.ERR_MSG) ERR_MSG, " 
			+"  C.ID_DOC_NUM, " 
			+"  C.ID_DOC_TYPE, " 
			+"  DECODE(O.LOB, 'MOB', O.MSISDN, 'LTS', OS.SRV_NUM, C.SERVICE_NUM) SERVICE_NUM, " 
			+"  O.MSISDN, " 
			+"  C.LAST_NAME " 
			+"  ||' ' " 
			+"  ||C.FIRST_NAME cust_full_name, " 
			+"  o.check_point, o.reason_cd "
			+"FROM BOMWEB_ORDER O, " 
			+"  BOMWEB_CUSTOMER C, " 
			+"  BOMWEB_ORDER_IMS OI, " 
			+"  (SELECT bos.order_id ORDER_ID, " 
			+"    bos.srv_num SRV_NUM, " 
			+"    bols.sb_status ORDER_STATUS, " 
			+"    bols.rea_cd ERR_MSG " 
			+"  FROM BOMWEB_ORDER_SERVICE bos, " 
			+"    BOMWEB_ORDER_LATEST_STATUS bols " 
			+"  WHERE bos.order_id  = bols.order_id " 
			+"  AND bos.dtl_id      = bols.dtl_id " 
			+"  AND bos.type_of_srv = 'TEL' " 
			+"  ) OS " 
			+"WHERE O.ORDER_ID              = C.ORDER_ID " 
			+"AND O.ORDER_ID                = OI.ORDER_ID(+) " 
			+"AND O.ORDER_ID                = OS.ORDER_ID(+) " 
			+" and not exists (" +
					"	Select 1 from BOMWEB_CODE_LKUP sbof " +
					"	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD' and O.SHOP_CD = sbof.CODE_ID) -- Filter IMS non-retail orders\n"
			+"AND NVL(C.ID_DOC_NUM, 'XXX')  = NVL(?, NVL(C.ID_DOC_NUM, 'XXX')) " 
			+"AND NVL(O.MSISDN, 'XXX')      = NVL(?, NVL(O.MSISDN, 'XXX')) " 
			+"AND NVL(C.ID_DOC_TYPE, 'XXX') = NVL(?, NVL(C.ID_DOC_TYPE, 'XXX')) " 
			+"AND NVL(OI.LOGIN_ID, 'XXX')   = NVL(?, NVL(OI.LOGIN_ID, 'XXX')) " 
			+"AND NVL(OS.SRV_NUM, 'XXX')    = NVL(?, NVL(OS.SRV_NUM, 'XXX'))";

	public List<CustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL_retail_only(
				String idDocNum, String serviceNum, String idDocType, String loginId, String serviceType) throws DAOException {
			logger.info(" getCustomerOrderHistoryDTOALL_retail_only is called");
			List<CustomerOrderHistoryDTO> itemList = new ArrayList<CustomerOrderHistoryDTO>();

			ParameterizedRowMapper<CustomerOrderHistoryDTO> mapper = new ParameterizedRowMapper<CustomerOrderHistoryDTO>() {
				public CustomerOrderHistoryDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					CustomerOrderHistoryDTO dto = new CustomerOrderHistoryDTO();
					dto.setOrderId(rs.getString("ORDER_ID"));
					dto.setOcid(rs.getString("OCID"));
					dto.setLob(rs.getString("LOB"));
					dto.setImsLoginId(rs.getString("IMS_LOGIN_ID"));
					dto.setAppDate(rs.getTimestamp("APP_DATE"));
					dto.setOrderStatus(rs.getString("ORDER_STATUS"));
					dto.setErrMsg(rs.getString("ERR_MSG"));
					dto.setIdDocNum(rs.getString("ID_DOC_NUM"));
					dto.setServiceNum(rs.getString("SERVICE_NUM"));
					dto.setOrderHistCustName(rs.getString("cust_full_name"));
					dto.setCheckPoint(rs.getString("check_point"));
					dto.setReasonCd(rs.getString("reason_cd"));
					
					return dto;
				}
			};

			try {
				logger.info("getCustomerOrderHistoryDTOALL_retail_only() @ CustomerOrderHistoryDTO: "
						+ getCustomerOrderHistoryDTOAll_retail_only_SQL);
				
				idDocNum = idDocNum.trim();
				serviceNum = serviceNum.trim();
				idDocType = idDocType.trim();
				
				if ("BR".equals(idDocType)) {
					idDocType = "BS";
				} else if ("Passport".equals(idDocType)) {
					idDocType = "PASS";
				} else if ("Certificate No".equals(idDocType)) {
					idDocType = "CERT";
				} else if ("NONE".equals(idDocType)) {
					idDocType = null;
				}
				
				if ("".equals(idDocNum)) {
					idDocNum = null;
				}
				
				if ("".equals(serviceNum)) {
					serviceNum = null;
				}
				
				if ("".equals(loginId)) {
					loginId = null;
				}
				
				if ("MRT".equalsIgnoreCase(serviceType)) {
					itemList = simpleJdbcTemplate.query(
							getCustomerOrderHistoryDTOAll_retail_only_SQL, mapper, idDocNum,
							serviceNum, idDocType, loginId, null);
				} else if ("TEL".equalsIgnoreCase(serviceType)){
					itemList = simpleJdbcTemplate.query(
							getCustomerOrderHistoryDTOAll_retail_only_SQL, mapper, idDocNum,
							null, idDocType, loginId, serviceNum);
				} else {
					itemList = simpleJdbcTemplate.query(
							getCustomerOrderHistoryDTOAll_retail_only_SQL, mapper, idDocNum,
							null, idDocType, loginId, null);
				}
			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException in getCustomerOrderHistoryDTOALL_retail_only()");

				itemList = null;
			} catch (Exception e) {
				logger.info("Exception caught in getCustomerOrderHistoryDTOALL_retail_only():", e);

				throw new DAOException(e.getMessage(), e);
			}
			return itemList;
		}

		
		
	
	
	
}
