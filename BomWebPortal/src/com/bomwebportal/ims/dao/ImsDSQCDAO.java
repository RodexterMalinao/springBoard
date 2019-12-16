package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.QcStaffDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ui.CcLtsImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsDsQCProcessUI;
import com.bomwebportal.ims.dto.ui.ImsDsQCStaffAdminUI;
import com.bomwebportal.ims.dto.ui.DsQCImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsDsQcProcessDetailUI;
import com.bomwebportal.ims.dto.ui.ImsQcAssignUI;
import com.bomwebportal.ims.dto.ui.ImsQcComOrderSearchUI;
import com.bomwebportal.ims.dto.ui.QcImsAdminUI;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

public class ImsDSQCDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
 
	public boolean isDcStaffExist(String staffId) throws DAOException{
		// TODO Auto-generated method stub
		String SQL =  " select count(*) count from BOMWEB_SALES_TYPE bst "+ 
	      			  " where bst.staff_id = :staffId " +
	      			  " and staff_type = 'SALES' ";
	      			  
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("count"));
		        return temp;
		    }
		};
		
    	List<String> resultList = new ArrayList<String>();
		
		try {
			logger.debug("isDcStaffExist SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isDcStaffExist:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (resultList.size()>0 ? !"0".equals(resultList.get(0)):false);
	}
	
	public void insertSalesTypeAndLocation(BomSalesUserDTO amend) throws DAOException{
		//logger.info("insertSalesTypeAndLocation:"+amend.getSalesType()+", location: " + amend.getLocation());
		
		if(amend.getLocation() != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = " Insert into BOMWEB_SALES_TYPE "+
			   			 " (STAFF_ID, STAFF_TYPE,STAFF_CODE, EFF_START_DATE, CREATE_DATE, CREATE_BY, LAST_UPD_DATE, LAST_UPD_BY, LOCATION) "+
			   			 " Values "+
			   			 " (:staffId, 'SALES', :salesCode,sysdate, sysdate, :createBy, sysdate, :lastUpdBy, :location) ";
			
			params.addValue("staffId", amend.getOrgStaffId());
			params.addValue("salesCode", amend.getSalesType());
			params.addValue("location", amend.getLocation());
			params.addValue("createBy", amend.getUsername());
			params.addValue("lastUpdBy", amend.getUsername());
			
			try{
					logger.debug("insertSalesTypeAndLocation: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in insertSalesTypeAndLocation()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public void updateSalesTypeAndLocation(BomSalesUserDTO amend) throws DAOException{
		logger.info("updateSalesTypeAndLocation:"+amend.getSalesType()+", location: " + amend.getLocation());
		
		if(amend.getLocation() != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE BOMWEB_SALES_TYPE " + 
							" SET STAFF_CODE = :salesCode, " + 
							" LOCATION = :location, " + 
							" LAST_UPD_BY = :staffId, " +  
							" LAST_UPD_DATE = SYSDATE " + 
							" WHERE STAFF_ID = :staffId " +
							" AND STAFF_TYPE ='SALES' ";
			
            params.addValue("staffId", amend.getOrgStaffId());
			params.addValue("salesCode", amend.getSalesType());
			params.addValue("location", amend.getLocation());
			
			try{
					logger.debug("update salesType and location: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateSalesTypeAndLocation()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public String checkSalesTypeCode(String salesType) throws DAOException {
		String SQL = 
			" select Code from w_code_lkup where grp_id = 'SB_IMS_SALES_TYPE' and description = '" + salesType +"'";
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String salesTypeCode = new String();
				salesTypeCode = (String) rs.getString("Code");
				return salesTypeCode;
			}
		};
	
		List<String > salesTypeCode = new ArrayList<String >();
		
	try {
		logger.debug("checkSalesTypeCode SQL: " + SQL);
		salesTypeCode = simpleJdbcTemplate.query(SQL, mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		salesTypeCode = null;
	} catch (Exception e) {
		logger.debug("Exception caught in checkSalesTypeCode():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		if (salesTypeCode.size()>0){
			return salesTypeCode.get(0);
		}else return "";
	}	
	
	//QC Process
	public List<String> getOrderStatusList ()
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		String SQL = 
			" SELECT distinct description" +
			"  FROM w_code_lkup" +
			" WHERE grp_id = 'IMS_SB_ORDER_STATUS'" +
			" and description like 'Signed%' " ;

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dto = new String();
			dto = (String) rs.getString("DESCRIPTION");
			return dto;
		}
	};

	try {
		logger.debug("getOrderStatusList SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderStatusList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
    //QC process
	public List<String> getQcStatusList()
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		String SQL = 
			" SELECT distinct description" +
			"  FROM w_code_lkup" +
			" WHERE grp_id = 'SB_IMS_QC_STATUS' order by description  " ;

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dto = new String();
			dto = (String) rs.getString("DESCRIPTION");
			return dto;
		}
	};

	try {
		logger.debug("getQcStatusList SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getQcStatusList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
	
	
	
	public List<Map<String, String>> getQcOrderTypeSelectionList() throws DAOException{

		String SQL = "select code, description " +
					 "from w_code_lkup " +
					 "where grp_id = 'SB_IMS_QC_ORD_TYPE' " +
					 "order by code desc";
		
		ParameterizedRowMapper<Map<String, String>> mapper 
			= new ParameterizedRowMapper<Map<String, String>>() {		
				public Map<String, String> mapRow(ResultSet rs, int rowNum)throws SQLException {
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", rs.getString("code"));
					map.put("label", rs.getString("description"));
					return map;
				}
		};
		
		try {
			logger.debug("getQcOrderTypeSelectionList: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate.query(SQL, mapper);
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getQcOrderTypeSelectionList()", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
	}
/*	
	public Map<String,String> getQcOrderTypeSelectionList()
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		String SQL = 
			" SELECT distinct description" +
			"  FROM w_code_lkup" +
			" WHERE grp_id = 'SB_IMS_QC_STATUS' order by description  " ;

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String,String> dto = new HashMap<String,String>();
			dto = (Map<String, String>) rs.getString("DESCRIPTION");
			return dto;
		}
	};

	try {
		logger.debug("getQcStatusList SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getQcStatusList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
	*/
	
	public Boolean  isPendingExist (String orderId)
	throws DAOException {
		
		String 	SQL=" select count(*) pending from Q_WORK_QUEUE a, Q_WQ_WP_ASSGN_STATUS_LOG b , Q_WQ_WP_ASSGN c" +
				" where a.WQ_ID = c.WQ_ID" +
				" and b.WQ_WP_ASSGN_ID = c.WQ_WP_ASSGN_ID" +
				" and b.LATEST_STATUS_IND = 'Y'" +
				" and b.STATUS_CD not in (select code from Q_DIC_CODE_LKUP where grp_id ='WQ_ENDING_STATUS')" +
				" and a.SB_ID = '" + orderId +"'";
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  count = new String ();
		    	count = (rs.getString("pending"));
		        return count;
		    }
		};
		
    	List<String > countPending = new ArrayList<String >();
		
		try {
			countPending = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			countPending = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isPendingexist():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(countPending.get(0).equalsIgnoreCase("0")){
			logger.debug("is Pending exist: false");
			return false;
		}else{
			logger.debug("is Pending exist: true");
			return true;
		}
	}
	
	public Boolean  isPaymentMethodIsCC (String orderId)
	throws DAOException {
		logger.debug("isPaymentMethodIsCC orderId: " + orderId);
		
		String SQL = "select PAY_MTD_TYPE from bomweb_payment where order_id = '" + orderId +"' ";
		
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  temp = new String ();
		    	temp = (rs.getString("PAY_MTD_TYPE"));
		        return temp;
		    }
		};
		
    	List<String > tempList = new ArrayList<String >();
		
		try {
			logger.debug("isPaymentMethodIsCC SQL: " + SQL);
			tempList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isPaymentMethodIsCC():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(tempList.get(0).equalsIgnoreCase("C")){
			logger.debug("is Payment Method Is CC: true");
			return true;
		}else{
			logger.debug("is Payment Method Is CC: false");
			return false;
		}
	}

	
	public Boolean  isOCIDexist (String orderId)
	throws DAOException {
		logger.debug("isOCIDexist orderId: " + orderId);
		
		String SQL = 	" 	   select nvl(ocid,0) ocid from " +
				" bomweb_order where order_id ='" + orderId +"' ";
		
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  ocid = new String ();
		    	ocid = (rs.getString("ocid"));
		        return ocid;
		    }
		};
		
    	List<String > ocidList = new ArrayList<String >();
		
		try {
			logger.debug("isOCIDexist SQL: " + SQL);
			ocidList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			ocidList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isOCIDexist():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(ocidList.size()>0 && !ocidList.get(0).equalsIgnoreCase("0")){
			logger.debug("is OCID exist: true");
			return true;
		}else{
			logger.debug("is OCID exist: true");
			return false;
		}
	}
	
	//QC Enqury
	public List<ImsAlertMsgDTO> getImsDsQcOrderEnquiryInfo (DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();
		
		enquiry.print();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer( 
			" select * from (" +
			" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , " +
			" decode(b.ID_DOC_TYPE, 'BS', b.company_name, b.last_name || ' ' || b.first_name)  AS cust_name ," +
			" b.SERVICE_NUM, c.LOGIN_ID, to_char(a.APP_DATE,'YYYY/MM/DD HH24:MI:SS') APP_DATE, a.order_Status, '' err, '' recall, " +
			" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY, a.reason_cd, " +
			" to_char(a.SIGN_OFF_DATE,'YYYY/MM/DD HH24:MI:SS') SIGN_OFF_DATE,to_char(a.SRV_REQ_DATE,'YYYY/MM/DD HH24:MI:SS') SRV_REQ_DATE, c.source_cd,C.WAIVE_QC,C.WAIVE_QC_OK," +
			" DECODE( INSTR(d.qc_findings,'/',1,3), 0, d.qc_findings,SUBSTR(d.qc_findings,1,(INSTR(d.qc_findings,'/',1,3)-3))) qc_findings, a.order_type ,c.sys_F " +   //added for QC
			//" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c " +
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c, BOMWEB_ORDER_QC_ASSIGN d " +
			" where a.order_id = b.order_id   " +
			" and c.order_id = D.ORDERS_ID (+)  "+
			" and a.SIGN_OFF_DATE is not null "+
			" and a.order_id = c.order_id" );
		
		if (userDto.getChannelId() == 13){
			sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT where channel_id in (select CODE from  w_code_lkup where grp_id ='SB_QC_CHANNEL_ID_LIST' and description ='NOWTV')) ");
		}else if (userDto.getChannelId() == 12){
			sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT where channel_id in (select CODE from  w_code_lkup where grp_id ='SB_QC_CHANNEL_ID_LIST' and description ='PCD')) ");	
		}else {
			sql.append(" and a.SALES_CHANNEL in (select distinct channel_cd from bomweb_sales_assignment where channel_id = :channelId)");
		}
		
		params.addValue("channelId", userDto.getChannelId());
		
		//	" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT where channel_id = :channelId) ");
	    //" and a.order_id not in (select ORDERS_ID from BOMWEB_ORDER_QC_ASSIGN ) " );
		
		sql.append(" and a.order_id in (:orderIds) ");
	
		List<String> tempOrderIds =  this.getOrderIdsForEnquiry(enquiry, userDto);
		Gson gson = new Gson();
		System.out.println("num of tempOrderIds:"+tempOrderIds.size());
		System.out.println(gson.toJson(tempOrderIds));
		params.addValue("orderIds", tempOrderIds);
		
		sql.append(" order by a.create_date desc)" +
			" where rownum<100 ") ;

	ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
		public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
			dto.setAppDate((String) rs.getString("APP_DATE"));
			dto.setAlertStatus((String) rs.getString("alert_Status"));
			dto.setCustName((String) rs.getString("cust_Name"));
			dto.setError((String) rs.getString("err"));
			dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
			dto.setLoginId((String) rs.getString("LOGIN_ID"));
			dto.setOcid((String) rs.getString("OCID"));
			dto.setOrderId((String) rs.getString("order_id"));
			dto.setOrderStatus((String) rs.getString("order_Status"));
			dto.setServiceNum((String) rs.getString("SERVICE_NUM"));
			dto.setSalesCd((String) rs.getString("SALES_CD"));
			dto.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
			dto.setCreateBy((String) rs.getString("CREATE_BY"));
			dto.setReasonCD((String) rs.getString("reason_cd"));
			dto.setSignoffDate((String) rs.getString("SIGN_OFF_DATE"));
			dto.setServiceReqDate((String) rs.getString("SRV_REQ_DATE"));
			dto.setSourceCD((String) rs.getString("source_cd"));
			dto.setQcRemarks((String) rs.getString("QC_FINDINGS"));
			dto.setWaiveQCapproved((String) rs.getString("WAIVE_QC_OK"));
			dto.setWaiveQC((String) rs.getString("WAIVE_QC"));
			dto.setOrderType((String) rs.getString("order_type"));
			dto.setSysF((String) rs.getString("sys_f"));
			
			return dto;
		}
	};

	try {
		logger.debug("getImsDsQcOrderEnquiryInfo @ ImsDSQCDAO:" + sql);
//		result = simpleJdbcTemplate.query(SQL, mapper);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getImsDsQcOrderEnquiryInfo():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}
	
	//QC assign
	public List<ImsQcAssignUI> getQcStaffInfo(int channelID)	throws DAOException {
		List<ImsQcAssignUI> result = new ArrayList<ImsQcAssignUI>();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String SQL = 
			" select QC_STAFF_ID,STATUS,TODAY_OS,TODAY_ASSIGNED,PAST7_DAYS_OS,TOT_OS,B.STAFF_NAME " + 
			"	from BOMWEB_SALES_QC a,BOMWEB_SALES_PROFILE b, BOMWEB_SALES_ASSIGNMENT c " +
			"	where a.status IN ('A','IA') " +
			"	and A.QC_STAFF_ID=B.STAFF_ID " +
			"	AND B.END_DATE is null		  " +
			"   AND c.END_DATE is null        " + 
			"	and B.STAFF_ID = C.STAFF_ID   " +
			"	and C.CHANNEL_ID = :channelID " ;
			
		params.addValue("channelID", channelID);
		
		ParameterizedRowMapper<ImsQcAssignUI> mapper = new ParameterizedRowMapper<ImsQcAssignUI>() {
			public ImsQcAssignUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsQcAssignUI dto = new ImsQcAssignUI();
				dto.setQcStaffId((String) rs.getString("QC_STAFF_ID"));
				dto.setStatus((String) rs.getString("STATUS"));
				dto.setTodayOsOrders((String) rs.getString("TODAY_OS"));
				dto.setTodayAssignedOrders((String) rs.getString("TODAY_ASSIGNED"));
				dto.setPast7daysAssignedOrders((String) rs.getString("PAST7_DAYS_OS"));
				dto.setTotalOrders((String) rs.getString("TOT_OS"));
				dto.setStaffName((String) rs.getString("staff_Name"));
				try {
					dto.setSkillSet(getQcSkills(dto.getQcStaffId().toString()));
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				return dto;
			}
		};

	try {
		logger.debug("getQcStaffInfo SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper,params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getQcStaffInfo():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
	
	//QC assign
	public List<String> getQcSkills(String staffID)
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		String SQL = 
			" select SKILLS from BOMWEB_SALES_QC_SK_ASSIGN where STAFF_ID ='" + staffID +"' " ;
		
		//MapSqlParameterSource params = new MapSqlParameterSource();
		//params.addValue("staffID" ,staffID);
        
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("SKILLS");
				return dto;
			}
		};

	try {
		logger.debug("getQcSkills SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getQcSkills():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
	
	//QC Admin
	public List<QcImsAdminUI> getQcStaffInfoAdmin(int channelID)throws DAOException {
		List<QcImsAdminUI> result = new ArrayList<QcImsAdminUI>();
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String SQL = 
			" select QC_STAFF_ID,STATUS,TODAY_OS,TODAY_ASSIGNED,PAST7_DAYS_OS,TOT_OS,B.STAFF_NAME " + 
			"	from BOMWEB_SALES_QC a,BOMWEB_SALES_PROFILE b, BOMWEB_SALES_ASSIGNMENT c 		  " +
			"	where a.status IN ('A','IA' ) " +
			"	and A.QC_STAFF_ID=B.STAFF_ID  " + 
			"	and B.STAFF_ID = C.STAFF_ID   " +
			"   and channel_id in (12,13) 	  " +
			"	AND B.END_DATE is null		  " +
			"	AND c.END_DATE is null		  " +
			"   and C.CHANNEL_ID = :channelID " +
			"   order by a.CREATE_DATE        " ;
			
		
		params.addValue("channelID", channelID);
		
//		String SQL = 
//			" select QC_STAFF_ID,STATUS,B.STAFF_NAME " + 
//			"	from BOMWEB_SALES_QC a,BOMWEB_SALES_PROFILE b, BOMWEB_SALES_ASSIGNMENT c 		  " +
//			"	where a.status IN ('A','IA' ) " +
//			"	and A.QC_STAFF_ID=B.STAFF_ID  " + 
//			"	and B.STAFF_ID = C.STAFF_ID   " +
//			"   and channel_id in (12,13) 	  " +
//			"	AND B.END_DATE is null		  " +
//			"   order by a.CREATE_DATE        ";

		ParameterizedRowMapper<QcImsAdminUI> mapper = new ParameterizedRowMapper<QcImsAdminUI>() {
			public QcImsAdminUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				QcImsAdminUI dto = new QcImsAdminUI();
				dto.setQcStaffId((String) rs.getString("QC_STAFF_ID"));
				dto.setStatus((String) rs.getString("STATUS"));
				dto.setStaffName((String) rs.getString("staff_Name"));
				dto.setTodayOsOrders((String) rs.getString("TODAY_OS"));
				dto.setTodayAssignedOrders((String) rs.getString("TODAY_ASSIGNED"));
				dto.setPast7daysAssignedOrders((String) rs.getString("PAST7_DAYS_OS"));
				dto.setTotalOrders((String) rs.getString("TOT_OS"));
				
				try {
					dto.setSkillSet(getQcSkills(dto.getQcStaffId().toString()));
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				return dto;
			}
		};

	try {
		logger.debug("getQcStaffInfoAdmin SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper,params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getQcStaffInfoAdmin():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
	
	//QC Process
	public List<ImsAlertMsgDTO> getImsDsQcProcessEnquiryInfo (ImsDsQCProcessUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();
		
		enquiry.print();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		logger.debug("userDto.getChannelId():" +userDto.getChannelId());
		
		StringBuffer sql =  new StringBuffer();
		
		if(userDto.getChannelId() == 13){
			
			sql =  new StringBuffer( 
					" select * from (" +
					" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , " +
					" decode(b.ID_DOC_TYPE, 'BS', b.company_name, b.last_name || ' ' || b.first_name)  AS cust_name ," +
					" b.SERVICE_NUM, c.LOGIN_ID, to_char(a.APP_DATE,'YYYY/MM/DD HH24:MI:SS') APP_DATE, a.order_Status, '' err, '' recall, " +
					" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY, a.reason_cd, " +
					" to_char(a.SIGN_OFF_DATE,'YYYY/MM/DD HH24:MI:SS')SIGN_OFF_DATE,to_char(a.SRV_REQ_DATE,'YYYY/MM/DD HH24:MI:SS')SRV_REQ_DATE, c.source_cd , " +
					" DECODE( INSTR(qc_findings,'/',1,3), 0, qc_findings,SUBSTR(qc_findings,1,(INSTR(qc_findings,'/',1,3)-3))) qc_findings ," +
					" e.ASSIGN_DATE,nvl(e.SYS_F, c.SYS_F) SYS_F,(select description from w_code_lkup where grp_id ='SB_IMS_QC_STATUS' and code= d.qc_status) qc_status, a.order_type , f.description  " +   //added for QC
					" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c, BOMWEB_ORDER_QC_ASSIGN d, BOMWEB_SALES_QC_ASSIGN e , w_code_lkup f  " +
					" where a.order_id = b.order_id" +
					" and a.order_id = d.orders_id (+) " +
					" and a.order_id = e.orders_id     " +
					" and a.order_id = c.order_id " +
					" and a.SIGN_OFF_DATE is not null " +
					" and (d.QC_STATUS is null or d.QC_STATUS ='Q04' or (d.QC_STATUS ='Q02' and d.reason_code = 'F0001')) " );
				
		}else{
		
			sql =  new StringBuffer( 
					" select * from (" +
					" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , " +
					" decode(b.ID_DOC_TYPE, 'BS', b.company_name, b.last_name || ' ' || b.first_name)  AS cust_name ," +
					" b.SERVICE_NUM, c.LOGIN_ID, to_char(a.APP_DATE,'YYYY/MM/DD HH24:MI:SS') APP_DATE, a.order_Status, '' err, '' recall, " +
					" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY, a.reason_cd, " +
					" to_char(a.SIGN_OFF_DATE,'YYYY/MM/DD HH24:MI:SS')SIGN_OFF_DATE,to_char(a.SRV_REQ_DATE,'YYYY/MM/DD HH24:MI:SS')SRV_REQ_DATE, c.source_cd , " +
					" DECODE( INSTR(qc_findings,'/',1,3), 0, qc_findings,SUBSTR(qc_findings,1,(INSTR(qc_findings,'/',1,3)-3))) qc_findings ," +
					" e.ASSIGN_DATE,nvl(e.SYS_F, c.SYS_F) SYS_F,(select description from w_code_lkup where grp_id ='SB_IMS_QC_STATUS' and code= d.qc_status) qc_status, a.order_type " +   //added for QC
					" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c, BOMWEB_ORDER_QC_ASSIGN d, BOMWEB_SALES_QC_ASSIGN e " +
					" where a.order_id = b.order_id" +
					" and a.order_id = d.orders_id (+) " +
					" and a.order_id = e.orders_id     " +
					" and a.order_id = c.order_id " +
					" and a.SIGN_OFF_DATE is not null " +
					" and (d.QC_STATUS is null or d.QC_STATUS ='Q04') " );
		}
		
		
		
		sql.append(" and a.order_id in (:orderIds) ");
		if(userDto.getChannelId() == 13){
			sql.append(" and a.order_status = f.code(+) and f.GRP_ID(+) = 'IMS_QCNTV_PROCS_SORT' ");
		}
		List<String> tempOrderIds =  this.getOrderIdsForEnquiry(enquiry, userDto);
		Gson gson = new Gson();
		System.out.println("num of tempOrderIds:"+tempOrderIds.size());
		System.out.println(gson.toJson(tempOrderIds));
		params.addValue("orderIds", tempOrderIds);
		
		if(userDto.getChannelId() == 13){
			sql.append(" order by f.description, a.sign_off_date asc)"
					+
			" where rownum<100 ") ;
		}
		else{
		sql.append(" order by a.create_date desc)" +
			" where rownum<100 ") ;
		}
	ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
		public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
			dto.setAppDate((String) rs.getString("APP_DATE"));
			dto.setAlertStatus((String) rs.getString("alert_Status"));
			dto.setCustName((String) rs.getString("cust_Name"));
			dto.setError((String) rs.getString("err"));
			dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
			dto.setLoginId((String) rs.getString("LOGIN_ID"));
			dto.setOcid((String) rs.getString("OCID"));
			dto.setOrderId((String) rs.getString("order_id"));
			dto.setOrderStatus((String) rs.getString("order_Status"));
			dto.setServiceNum((String) rs.getString("SERVICE_NUM"));
			dto.setSalesCd((String) rs.getString("SALES_CD"));
			dto.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
			dto.setCreateBy((String) rs.getString("CREATE_BY"));
			dto.setReasonCD((String) rs.getString("reason_cd"));
			dto.setSignoffDate((String) rs.getString("SIGN_OFF_DATE"));
			dto.setServiceReqDate((String) rs.getString("SRV_REQ_DATE"));
			dto.setSourceCD((String) rs.getString("source_cd"));
			dto.setQcRemarks((String) rs.getString("QC_FINDINGS"));
			dto.setAssignDate((String) rs.getString("ASSIGN_DATE"));
			dto.setSysF((String) rs.getString("SYS_F"));
			dto.setQcStatus((String) rs.getString("qc_status"));
			dto.setOrderType((String) rs.getString("order_type"));
			
			return dto;
		}
	};

	try {
		logger.debug("QC Process getImsDsQcProcessEnquiryInfo@ ImsDSQCDAO:" + sql);
//		result = simpleJdbcTemplate.query(SQL, mapper);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in QC Process getImsDsQcProcessEnquiryInfo():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}
	
	public List<String> getChannelCodeListByChannelID (int channelId)
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	select distinct channel_cd from bomweb_sales_assignment where channel_id = :channelid and end_date is null	" );
		
		params.addValue("channelid", channelId);
		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("channel_cd");
			return tempResult;
		}
	};

	try {
		logger.debug("channelId @ getChannelCodeByChannelID: " + channelId);
		logger.debug("getChannelCodeByChannelID : " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getChannelCodeByChannelID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	System.out.print("Channel Code List of Channel Id:"+channelId);
	for(String channelCode : result){
		System.out.print("\t"+channelCode);
	}
	System.out.println("");
		return result;
	}
	
	//QC Enquiry (assignment)
	public String getLtsImsOrderEnquirySQL (DsQCImsOrderEnquiryUI enquiry, String role,BomSalesUserDTO userDto)
	throws DAOException {		
		
		StringBuffer sql =  new StringBuffer( 
			" select a.order_id, a.app_date " +
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c, BOMWEB_SALES_ASSIGNMENT d, bomweb_sales_profile e " +
			" where a.order_id = b.order_id" +
			" and a.order_id = c.order_id" +
			" and d.staff_id = :user " +
			" and d.STAFF_ID = e.staff_id " +
			" and (a.shop_cd not in 	(Select code_id from BOMWEB_CODE_LKUP sbof 	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD') " +
			" or a.shop_cd  is null) " +
			//" and a.order_id not in (select ORDERS_ID from BOMWEB_SALES_QC_ASSIGN where ASSIGNEE is not null ) "+ //added for QC
			" AND EXISTS (SELECT 1 FROM bomweb_sales_qc_assign  WHERE assignee IS NULL and orders_id = a.order_id ) " +
			//" and a.order_id like 'DDS%' " +   // tmp added for QC
			" and trunc(e.start_date) <= trunc(sysdate) " +
			" and (trunc(sysdate) < trunc(e.end_date) or e.end_date is null) " +
			" and trunc(d.start_date) <= trunc(sysdate) " +
			" and (trunc(sysdate) < trunc(d.end_date) or d.end_date is null) " );

		if(role.equalsIgnoreCase("CHANNEL_ID")){
			if (userDto.getChannelId() == 13){
				sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT f where channel_id in (select CODE from  w_code_lkup where grp_id ='SB_QC_CHANNEL_ID_LIST' and description ='NOWTV') and (trunc(sysdate) < trunc(f.end_date) or f.end_date is null)) ");
			}else if (userDto.getChannelId() == 12){
				sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT f where channel_id in (select CODE from  w_code_lkup where grp_id ='SB_QC_CHANNEL_ID_LIST' and description ='PCD') and (trunc(sysdate) < trunc(f.end_date) or f.end_date is null)) ");	
			}else {
				sql.append(" and a.SALES_CHANNEL in (select distinct channel_cd from bomweb_sales_assignment f where channel_id = :channelid and (trunc(sysdate) < trunc(end_date) or end_date is null)) ");
			}
			
			//sql.append(" and a.SALES_CHANNEL in (select distinct channel_cd from bomweb_sales_assignment where channel_id = :channelid and (trunc(sysdate) < trunc(end_date) or end_date is null)) ");
		}else if(role.equalsIgnoreCase("SALES_CD")){
			sql.append(" and e.ORG_STAFF_ID = a.SALES_CD");
		}else if(role.equalsIgnoreCase("TEAM_CD")){
			sql.append(" and d.team_cd = a.SALES_TEAM");
		}else if(role.equalsIgnoreCase("CHANNEL_CD")){
			sql.append(" and d.channel_cd = a.SALES_CHANNEL");
		}else{
			sql.append(" and d.STAFF_ID = a.create_by");
		}
		sql.append(" and a.LOB in (:LOB)");
		
		if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
			if(enquiry.getDateType().equalsIgnoreCase("S")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.srv_req_date >= TO_DATE(:SStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.srv_req_date < TO_DATE(:SEndDate, 'dd/mm/yyyy')+1 " );
				}
			}else if(enquiry.getDateType().equalsIgnoreCase("A")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.APP_DATE >= TO_DATE(:AStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.APP_DATE < TO_DATE(:AEndDate, 'dd/mm/yyyy')+1  ");
				}
			}else if(enquiry.getDateType().equalsIgnoreCase("F")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.SIGN_OFF_DATE >= TO_DATE(:FStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.SIGN_OFF_DATE < TO_DATE(:FEndDate, 'dd/mm/yyyy')+1  ");
				}
			}			
		}
		if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
			sql.append(" and a.order_id = :OrderID ");
		}
		if(enquiry.getDocType()!=null&&!enquiry.getDocType().equals("") &&
		   enquiry.getDocNum() !=null&&!enquiry.getDocNum() .equals("")	){
			sql.append(" and b.id_doc_type = :DocType ");
			sql.append(" and b.id_doc_num = :DocNum ");
		}
		if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
			sql.append(" and b.service_num = :SerNum ");
		}
		if(enquiry.getLoginId()!=null&&!enquiry.getLoginId().equals("")){
			sql.append(" and c.login_id = :LoginId ");
		}		
		
		if(enquiry.getOrderStatus()!=null && !enquiry.getOrderStatus().equals("")){
			sql.append(" and a.ORDER_STATUS in ( SELECT code" +
			"  FROM w_code_lkup" +
			" WHERE grp_id = 'IMS_SB_ORDER_STATUS'" +
			" and description = :description ) " );
		}
		if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
			sql.append(" and UPPER(a.SALES_CD) in UPPER(:salesNum) ");
		}
		if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
			sql.append(" and UPPER(a.create_by) in UPPER(:CreateStaff) ");
		}
		if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
			sql.append(" and UPPER(a.SALES_TEAM) in UPPER(:TeamSearch) ");
		}
		//added for QC
		if(enquiry.getWaiveQC()!=null&&!enquiry.getWaiveQC().equals("")){
			sql.append(" and UPPER(c.WAIVE_QC) = :waveqc ");
		}
		if(enquiry.getWaiveQCapproved()!=null&&!enquiry.getWaiveQCapproved().equals("")){
			sql.append(" and UPPER(c.WAIVE_QC_OK) in UPPER(:waiveQCapproved) ");
		}
		
		logger.info(role+" QC ENQUIRY getLtsImsOrderEnquirySQL:"+sql.toString());
		return sql.toString();
	}
	
	public Boolean checkIfSalesManager (String userId)
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	SELECT CATEGORY FROM BOMWEB_SALES_PROFILE where staff_id = :userId  and end_date is null  " );
		
		params.addValue("userId", userId);
		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("CATEGORY");
			return tempResult;
		}
	};

	try {
		logger.debug("userId @ checkIfSalesManager: " + userId);
		logger.debug("checkIfSalesManager : " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in checkIfSalesManager():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		//if(result.size()==0||!result.get(0).equalsIgnoreCase("SALES MANAGER")){
		if(result.size()==0||!result.get(0).contains("MANAGER")){
			return false;
		}else{
			return true;
		}
	}
	
	//QC Enqury
	public List<String> getRoleCodeByUserIDLkupCode (String userId, String lkupCode, String lkupFuncCode)
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	select CODE_DESC from bomweb_code_lkup 	"+
				"	where code_type like '"+lkupFuncCode+"'  and	"+
				"	code_id in(	"+
				"	select code_id from (	"+
				"	select code_id, '1' seq from bomweb_code_lkup c, BOMWEB_SALES_PROFILE a , BOMWEB_SALES_ASSIGNMENT b	"+
				"	where code_type = '"+lkupCode+"'	"+
				"	and code_desc = '\\'||CHANNEL_iD||'\\'||CATEGORY||'\\'||CHANNEL_CD||'\\'|| TEAM_CD 	"+
				"	and a.STAFF_ID=b.STAFF_ID	"+
				"	and a.staff_id = :userId	" +
				"	and a.START_DATE < sysdate" +
				"	and (sysdate < a.end_date or a.end_date is null) " +
				"	and b.START_DATE < sysdate" +
				"	and (sysdate < b.end_date or b.end_date is null) "+
				"	union all	"+
				"	select code_id, '2' seq from bomweb_code_lkup c, BOMWEB_SALES_PROFILE a , BOMWEB_SALES_ASSIGNMENT b	"+
				"	where code_type = '"+lkupCode+"'	"+
				"	and code_desc = '\\'||CHANNEL_iD||'\\'||CATEGORY||'\\'||CHANNEL_CD||'\\*'	"+
				"	and a.STAFF_ID=b.STAFF_ID	"+
				"	and a.staff_id = :userId	"+
				"	and a.START_DATE < sysdate" +
				"	and (sysdate < a.end_date or a.end_date is null) " +
				"	and b.START_DATE < sysdate" +
				"	and (sysdate < b.end_date or b.end_date is null) "+
				"	union all	"+
				"	select code_id, '3' seq from bomweb_code_lkup c, BOMWEB_SALES_PROFILE a , BOMWEB_SALES_ASSIGNMENT b	"+
				"	where code_type = '"+lkupCode+"'	"+
				"	and code_desc = '\\'||CHANNEL_iD||'\\'||CATEGORY||'\\*\\*'	"+
				"	and a.STAFF_ID=b.STAFF_ID	"+
				"	and a.staff_id = :userId	"+
				"	and a.START_DATE < sysdate" +
				"	and (sysdate < a.end_date or a.end_date is null) " +
				"	and b.START_DATE < sysdate" +
				"	and (sysdate < b.end_date or b.end_date is null) "+
				"	union all	"+
				"	select code_id, '4' seq from bomweb_code_lkup c, BOMWEB_SALES_PROFILE a , BOMWEB_SALES_ASSIGNMENT b	"+
				"	where code_type = '"+lkupCode+"'	"+
				"	and code_desc = '\\*\\*\\*\\*'	"+
				"	and a.STAFF_ID=b.STAFF_ID	"+
				"	and a.staff_id = :userId	"+
				"	and a.START_DATE < sysdate" +
				"	and (sysdate < a.end_date or a.end_date is null) " +
				"	and b.START_DATE < sysdate" +
				"	and (sysdate < b.end_date or b.end_date is null) "+
				"	order by seq )	"+
				"	where rownum = 1	"+
				"	)	"+
				"	union all	"+
				"	select 'CREATEBY' from dual  	" );
		
		params.addValue("userId", userId);
		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("CODE_DESC");
			return tempResult;
		}
	};

	try {
		logger.debug("userId @ getRoleCodeByUserID: " + userId);
		logger.debug("getRoleCodeByUserID @ ImsOrderAmendDAO: " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getRoleCodeByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	//System.out.print("role code List of User:"+userId+" with lkupRole:"+lkupCode);
	for(String code : result){
		System.out.print("\t"+code);
	}
	System.out.println("");
		return result;
	}
	
	
	//QC Enquiry
	public List<String> getOrderIdsForEnquiry (DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<String> result = null;
		

		logger.info("getOrderIdByUserID userId:"+userDto.getUsername());
		List<String> roleCodeList = this.getRoleCodeByUserIDLkupCode(userDto.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_READ, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION) ;
		
		Boolean needChannelCD = false;
		Boolean needSales = false;
		Boolean needTeam = false;
		Boolean needChannelID = false;
				
		/*for(String code : roleCodeList){
			logger.info("getRoleCodeByUserID:"+code);
			if(code.equalsIgnoreCase("SALES_CD")){
				needSales=true;
			}
			if(code.equalsIgnoreCase("TEAM_CD")){
				needTeam=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_CD")){
				needChannelCD=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_ID")){
				needChannelID=true;
			}
		}*/
		
		needChannelCD=true;
		needChannelID=true;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer(" select distinct order_id from (") ;
				
			//SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "create_by"));
		if(needSales){
			SQL.append( " union all ");
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "SALES_CD",userDto));
		}
		if(needTeam){
			SQL.append( " union all ");
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "TEAM_CD",userDto));
		}
		if(needChannelCD){
			//SQL.append( " union all ");
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CHANNEL_CD",userDto));
		}
		if(needChannelID){
			SQL.append( " union all ");
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CHANNEL_ID",userDto));
			params.addValue("channelid", Integer.valueOf(userDto.getChannelId()));
		}
				
		SQL.append("	order by app_date desc) where rownum<(select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'QC_ENQ_MAX')	" );
		
	
		params.addValue("LOB", "IMS");
	
	if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
		if(enquiry.getDateType().equalsIgnoreCase("S")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("SStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("SEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("A")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("AStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("AEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("F")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("FStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("FEndDate", enquiry.getEndDate());
			}
		}
	}
	
	if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
		params.addValue("OrderID", enquiry.getOrderId());
	}
	if(enquiry.getDocType()!=null&&!enquiry.getDocType().equals("")&&
	   enquiry.getDocNum() !=null&&!enquiry.getDocNum() .equals("")	){
		params.addValue("DocType", enquiry.getDocType());
		params.addValue("DocNum", enquiry.getDocNum());
	}
	if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
		params.addValue("SerNum", enquiry.getSerNum());
	}
	if(enquiry.getLoginId()!=null&&!enquiry.getLoginId().equals("")){
		params.addValue("LoginId", enquiry.getLoginId());
	}		
	if(enquiry.getOrderStatus()!=null && !enquiry.getOrderStatus().equals("")){
		params.addValue("description", enquiry.getOrderStatus());
	}
	if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
		params.addValue("salesNum", enquiry.getSalesNum());
	}
	if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
		params.addValue("CreateStaff", enquiry.getCreateStaff());
	}
	if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
		params.addValue("TeamSearch", enquiry.getTeamSearch());
	}
	//added for QC
	if(enquiry.getSourceCD() !=null&&!enquiry.getSourceCD().equals("")){
		params.addValue("sourceCD", enquiry.getSourceCD());
	}
	
	if(enquiry.getWaiveQC() !=null&&!enquiry.getWaiveQC().equals("")){
		params.addValue("waveqc", enquiry.getWaiveQC());
	}
	
	if(enquiry.getWaiveQCapproved() !=null&&!enquiry.getWaiveQCapproved().equals("")){
		params.addValue("waiveQCapproved", enquiry.getWaiveQCapproved());
	}
	
	logger.info("GET SOURCECD :"+enquiry.getSourceCD());
	logger.info("GET WAIVEQC :"+enquiry.getWaiveQC());
	logger.info("GET WAIVEQCAPPROVED :"+enquiry.getWaiveQCapproved());
	
	params.addValue("user", userDto.getUsername());

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("order_id");
			return tempResult;
		}
	};
	try {
		logger.debug("key search: " + params.getValues());
		logger.debug("userId @ getOrderIdByUserID: " + userDto.getUsername());
		logger.debug("getOrderIdByUserID @ ImsOrderAmendDAO: " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = new ArrayList<String>();
		result.add("''");
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderIdByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	if(result.size()==0){
		result.add("''");
	}
		return result;
	}
	
	//QC Process
	public String getLtsImsOrderEnquirySQL (ImsDsQCProcessUI enquiry, String role,BomSalesUserDTO userDto)
	throws DAOException {		
		
		StringBuffer sql =  new StringBuffer( 
			" select a.order_id, a.app_date " +
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c, BOMWEB_SALES_ASSIGNMENT d, bomweb_sales_profile e,BOMWEB_SALES_QC_ASSIGN f,BOMWEB_ORDER_QC_ASSIGN g " +
			" where a.order_id = b.order_id" +
			" and a.order_id = c.order_id" +
			" and d.staff_id = :user " +
			" and d.STAFF_ID = e.staff_id " +
			" and (a.shop_cd not in 	(Select code_id from BOMWEB_CODE_LKUP sbof 	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD') " +
			" or a.shop_cd  is null) " +
			//" and a.order_id in (select orders_id from BOMWEB_SALES_QC_ASSIGN ) " + //added for QC process
			" and a.order_id = f.orders_id " +//added for QC process
			//" and a.order_id like 'DDS%' " +   // tmp added for QC
			" and f.orders_id= g.orders_id (+)" +
			" and UPPER(f.assignee) = :user " +//added for QC process
			" and trunc(e.start_date) <= trunc(sysdate) " +
			" and (trunc(sysdate) < trunc(e.end_date) or e.end_date is null) " +
			" and trunc(d.start_date) <= trunc(sysdate) " +
			" and (trunc(sysdate) < trunc(d.end_date) or d.end_date is null) " );

		if(role.equalsIgnoreCase("CHANNEL_ID")){
			if (userDto.getChannelId() == 13){
				sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT where channel_id in (select CODE from  w_code_lkup where grp_id ='SB_QC_CHANNEL_ID_LIST' and description ='NOWTV')) ");
			}else if (userDto.getChannelId() == 12){
				sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT where channel_id in (select CODE from  w_code_lkup where grp_id ='SB_QC_CHANNEL_ID_LIST' and description ='PCD')) ");	
			}else {
				sql.append(" and a.SALES_CHANNEL in (select distinct channel_cd from bomweb_sales_assignment where channel_id = :channelid)");
			}
			
		}else if(role.equalsIgnoreCase("SALES_CD")){
			sql.append(" and e.ORG_STAFF_ID = a.SALES_CD");
		}else if(role.equalsIgnoreCase("TEAM_CD")){
			sql.append(" and d.team_cd = a.SALES_TEAM");
		}else if(role.equalsIgnoreCase("CHANNEL_CD")){
			sql.append(" and d.channel_cd = a.SALES_CHANNEL");
		}else{
			sql.append(" and d.STAFF_ID = a.create_by");
		}
		
			sql.append(" and a.LOB in (:LOB)");
		
		if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
			if(enquiry.getDateType().equalsIgnoreCase("S")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.srv_req_date >= TO_DATE(:SStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.srv_req_date < TO_DATE(:SEndDate, 'dd/mm/yyyy')+1 " );
				}
			}else if(enquiry.getDateType().equalsIgnoreCase("A")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.APP_DATE >= TO_DATE(:AStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.APP_DATE < TO_DATE(:AEndDate, 'dd/mm/yyyy')+1  ");
				}
			}else if(enquiry.getDateType().equalsIgnoreCase("F")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.SIGN_OFF_DATE >= TO_DATE(:FStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.SIGN_OFF_DATE < TO_DATE(:FEndDate, 'dd/mm/yyyy')+1  ");
				}
			}			
		}
		if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
			sql.append(" and a.order_id = :OrderID ");
		}
		if(enquiry.getDocType()!=null&&!enquiry.getDocType().equals("") &&
		   enquiry.getDocNum() !=null&&!enquiry.getDocNum() .equals("")	){
			sql.append(" and b.id_doc_type = :DocType ");
			sql.append(" and b.id_doc_num = :DocNum ");
		}
		if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
			sql.append(" and b.service_num = :SerNum ");
		}
		if(enquiry.getLoginId()!=null&&!enquiry.getLoginId().equals("")){
			sql.append(" and c.login_id = :LoginId ");
		}		
		
		if(enquiry.getOrderStatus()!=null && !enquiry.getOrderStatus().equals("")){
			sql.append(" and a.ORDER_STATUS in ( SELECT code" +
			"  FROM w_code_lkup" +
			" WHERE grp_id = 'IMS_SB_ORDER_STATUS'" +
			" and description = :description ) " );
		}
		if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
			sql.append(" and UPPER(a.SALES_CD) in UPPER(:salesNum) ");
		}
		if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
			sql.append(" and UPPER(a.create_by) in UPPER(:CreateStaff) ");
		}
		if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
			sql.append(" and UPPER(a.SALES_TEAM) in UPPER(:TeamSearch) ");
		}
		if(enquiry.getAssignee()!=null&&!enquiry.getAssignee().equals("")){
			sql.append(" and UPPER(f.Assignee) = :assignee ");
		}
		if(enquiry.getQcStatus()!=null && !enquiry.getQcStatus().equals("")){
			sql.append(" and g.QC_STATUS in ( SELECT code" +
			"  FROM w_code_lkup" +
			" WHERE grp_id = 'SB_IMS_QC_STATUS'" +
			" and description = :qcStatusDesc ) " );
		}
		if(enquiry.getQcOrderType()!=null && !enquiry.getQcOrderType().equals("")){
			sql.append(" and a.order_type = :qcOrderType  " );
		}
		
		
		logger.info(role+" getLtsImsOrderEnquirySQL:"+sql.toString());
		return sql.toString();
	}
	
	public List<String> getOrderIdsForEnquiry (ImsDsQCProcessUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<String> result = null;
		

		logger.info("getOrderIdByUserID userId:"+userDto.getUsername());
		List<String> roleCodeList = this.getRoleCodeByUserIDLkupCode(userDto.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_READ, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION) ;
		
		Boolean needChannelCD = false;
		Boolean needSales = false;
		Boolean needTeam = false;
		Boolean needChannelID = false;
				
		/*for(String code : roleCodeList){
			logger.info("getRoleCodeByUserID:"+code);
			if(code.equalsIgnoreCase("SALES_CD")){
				needSales=true;
			}
			if(code.equalsIgnoreCase("TEAM_CD")){
				needTeam=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_CD")){
				needChannelCD=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_ID")){
				needChannelID=true;
			}
		}*/
		
		
		needChannelCD=false;
		needChannelID=true;

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer(" select distinct order_id from (") ;
		//SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CHANNEL_CD"));
		//SQL.append( " union all ");
		SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CHANNEL_ID",userDto));
				
			/*SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "create_by"));
		if(needSales){
			SQL.append( " union all ");
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "SALES_CD"));
		}
		if(needTeam){
			SQL.append( " union all ");
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "TEAM_CD"));
		}
		if(needChannelCD){
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CHANNEL_CD"));
		}
		if(needChannelID){
			SQL.append( " union all ");
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CHANNEL_ID"));
			
		}
		*/
		SQL.append("	order by app_date desc) where rownum<(select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'QC_ENQ_MAX')	" );
		
		params.addValue("channelid", Integer.valueOf(userDto.getChannelId()));
		params.addValue("LOB", "IMS");
	
	if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
		if(enquiry.getDateType().equalsIgnoreCase("S")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("SStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("SEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("A")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("AStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("AEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("F")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("FStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("FEndDate", enquiry.getEndDate());
			}
		}
		
	}
	if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
		params.addValue("OrderID", enquiry.getOrderId());
	}
	if(enquiry.getDocType()!=null&&!enquiry.getDocType().equals("")&&
	   enquiry.getDocNum() !=null&&!enquiry.getDocNum() .equals("")	){
		params.addValue("DocType", enquiry.getDocType());
		params.addValue("DocNum", enquiry.getDocNum());
	}
	if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
		params.addValue("SerNum", enquiry.getSerNum());
	}
	if(enquiry.getLoginId()!=null&&!enquiry.getLoginId().equals("")){
		params.addValue("LoginId", enquiry.getLoginId());
	}		
	if(enquiry.getOrderStatus()!=null && !enquiry.getOrderStatus().equals("")){
		params.addValue("description", enquiry.getOrderStatus());
	}
	if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
		params.addValue("salesNum", enquiry.getSalesNum());
	}
	if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
		params.addValue("CreateStaff", enquiry.getCreateStaff());
	}
	if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
		params.addValue("TeamSearch", enquiry.getTeamSearch());
	}
	if(enquiry.getAssignee() !=null&&!enquiry.getAssignee().equals("")){
		params.addValue("assignee", enquiry.getAssignee());
	}
	if(enquiry.getQcStatus()!=null && !enquiry.getQcStatus().equals("")){
		params.addValue("qcStatusDesc", enquiry.getQcStatus());
	}
	if(enquiry.getQcOrderType()!=null && !enquiry.getQcOrderType().equals("")){
		params.addValue("qcOrderType", enquiry.getQcOrderType());
	}
		params.addValue("user", userDto.getUsername());

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("order_id");
			return tempResult;
		}
	};
	try {
		logger.debug("key search: " + params.getValues());
		logger.debug("userId @ getOrderIdByUserID: " + userDto.getUsername());
		logger.debug("getOrderIdByUserID @ ImsOrderAmendDAO: " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = new ArrayList<String>();
		result.add("''");
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderIdByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	if(result.size()==0){
		result.add("''");
	}
		return result;
	}

	public List<ImsAlertMsgDTO> getImsAlertMsgDTOListByOrderIdList (List<String> orderIdList)
	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();
				
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer( 
			" select * from (" +
			" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , b.FIRST_NAME||' '||b.LAST_NAME  as cust_Name," +
			" b.SERVICE_NUM, c.LOGIN_ID, to_char(a.APP_DATE,'DD/MM/YYYY HH:MI:SS AM') APP_DATE, a.order_Status, '' err, '' recall, " +
			" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY " +
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c " +
			" where a.order_id = b.order_id" +
			" and a.order_id = c.order_id" );
		
		sql.append(" and a.order_id in (:orderIds) ");
		Gson gson = new Gson();
		System.out.println("num of tempOrderIds:"+orderIdList.size());
		System.out.println(gson.toJson(orderIdList));
		params.addValue("orderIds", orderIdList);
		
		sql.append(" order by a.create_date desc)" +
			" ") ;

	ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
		public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
			dto.setAppDate((String) rs.getString("APP_DATE"));
			dto.setAlertStatus((String) rs.getString("alert_Status"));
			dto.setCustName((String) rs.getString("cust_Name"));
			dto.setError((String) rs.getString("err"));
			dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
			dto.setLoginId((String) rs.getString("LOGIN_ID"));
			dto.setOcid((String) rs.getString("OCID"));
			dto.setOrderId((String) rs.getString("order_id"));
			dto.setOrderStatus((String) rs.getString("order_Status"));
			dto.setServiceNum((String) rs.getString("SERVICE_NUM"));
			dto.setSalesCd((String) rs.getString("SALES_CD"));
			dto.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
			dto.setCreateBy((String) rs.getString("CREATE_BY"));
			return dto;
		}
	};

	try {
		logger.debug("getImsOrderListByOrderIdList :" + sql);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getImsOrderListByOrderIdList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}

	

	
	public String getRealStaffID(String salesCD)	throws DAOException {
		logger.debug("getRealStaffID: " + salesCD);
		
		String SQL = 	" select staff_id from bomweb_sales_profile		" +
				" where staff_id <> org_staff_id						" +
				" and start_date<=trunc(sysdate)						" +
				" and (trunc(sysdate) < end_date or end_date is null)	" +
				" and org_staff_id ='"+salesCD+"' 						" +
				" and rownum = 1 										" ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("staff_id"));
		        return temp;
		    }
		};
		
    	List<String> resultList = new ArrayList<String>();
		
		try {
			logger.debug("getRealStaffID SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getRealStaffID:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(resultList.size()>0){
			logger.info("getRealStaffID:"+resultList.get(0));
		}
		return (resultList.size()>0 ? resultList.get(0):null);
	}
	
	public BomSalesUserDTO getDSinfo(BomSalesUserDTO user) throws DAOException{
		//List<BomSalesUserDTO> salesList = new ArrayList<BomSalesUserDTO>();
		List<BomSalesUserDTO> salesList;
		
		String SQL = 
			" SELECT  lp.description, bst.location                                   "+    
			" FROM BOMWEB_SALES_ASSIGNMENT sa,BOMWEB_SALES_TYPE bst,w_code_lkup lp,       "+
			" BOMWEB_SALES_PROFILE sp                                                     "+
			" WHERE sa.STAFF_ID = :staffID                                              "+
			" AND trunc(sysdate) BETWEEN sa.START_DATE AND NVL(sa. END_DATE,sysdate)      "+
			" AND sa.staff_id=sp.staff_id                                                 "+
			" AND sa.staff_id=bst.staff_id 												"+ 
			" AND trunc(sysdate) BETWEEN sp.START_DATE AND NVL(sp. END_DATE,sysdate)      "+
			" and bst.staff_type = 'SALES' " +
			" and bst.staff_code = LP.CODE ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffID",user.getUsername());

		ParameterizedRowMapper<BomSalesUserDTO> mapper = new ParameterizedRowMapper<BomSalesUserDTO>() {
			public BomSalesUserDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BomSalesUserDTO dto = new BomSalesUserDTO();
				dto.setSalesType(rs.getString("description"));
				dto.setLocation(rs.getString("location"));

				return dto;
			}
		};

		try {
			//herbert 20111110 - remove useless SQL logger
			logger.debug("getDSinfo() @ ImsDSQCDAO: " + SQL);
			salesList = simpleJdbcTemplate.query(SQL, mapper, user.getUsername());
			
			if (salesList.size()>0){
				user.setSalesType(salesList.get(0).getSalesType());
				user.setLocation(salesList.get(0).getLocation());
			}else user.setSalesType("Survey Point");

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

			salesList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getDSinfo():", e);

			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return user;// only return the first one
		
	}
	
	//QC Assignment
	public void insertQcAssign(ImsQcAssignUI imsQcAssignUI) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("insertQcAssign:"+imsQcAssignUI.getSys_f());
		
		if(imsQcAssignUI != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = "Insert into BOMWEB_SALES_QC_ASSIGN (ORDERS_ID,ASSIGNEE,ASSIGN_DATE,CREATE_DATE,CREATE_BY,LAST_UPD_DATE,LAST_UPD_BY,SYS_F,LOB) Values "+
						 "(:orders_id,:assignee,Sysdate,SYSDATE,:createBy,SYSDATE,:last_up_by,:sys_f,'IMS') ";
			
	        params.addValue("orders_id", imsQcAssignUI.getOrderIds());
			params.addValue("assignee", imsQcAssignUI.getAssignee());
			params.addValue("createBy", imsQcAssignUI.getCreateBy());
			params.addValue("last_up_by", imsQcAssignUI.getLastUpBy());
			params.addValue("sys_f", imsQcAssignUI.getSys_f());
			
			try{
					logger.debug("insertQcAssign: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in insertQcAssign()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public void updateQcAssign(ImsQcAssignUI imsQcAssignUI) throws DAOException {
	
		if(imsQcAssignUI != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = "update BOMWEB_SALES_QC_ASSIGN "+
						 "	set assignee =:assignee, "+
						 "	    assign_date = sysdate, "+
						 "	    last_upd_date= sysdate, "+
						 "	    last_upd_by = :lastUpdBy "+
						 " 	where orders_id = :orderId ";
			
	        params.addValue("orderId", imsQcAssignUI.getOrderIds());
			params.addValue("assignee", imsQcAssignUI.getAssignee());
			params.addValue("lastUpdBy", imsQcAssignUI.getLastUpBy());
			
			
			try{
					logger.debug("updateQcAssign: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateQcAssign()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public List<ImsQcAssignUI> getTodayOutstandingOrders (String staffId)throws DAOException {
		List<ImsQcAssignUI> imsQcAssignUIList = new ArrayList<ImsQcAssignUI>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String SQL = " SELECT COUNT(*) count from ("+
					 " SELECT COUNT (a.orders_id) count " +
					 " FROM bomweb_sales_qc_assign a " +
					 " WHERE a.assignee = :assignee " +
					 " AND TRUNC (assign_date) = TRUNC (SYSDATE) " +
					 " MINUS " +
					 " SELECT COUNT (b.orders_id) count " +
					 " FROM bomweb_order_qc_assign b, " +
					 "      bomweb_sales_qc_assign c " +
					 " WHERE c.assignee = :assignee " +
					 " AND b.orders_id = c.orders_id  " +
					 " AND TRUNC (b.qc_date) = TRUNC (SYSDATE)) ";
		
		params.addValue("assignee", staffId);
		
		
		ParameterizedRowMapper<ImsQcAssignUI> mapper = new ParameterizedRowMapper<ImsQcAssignUI>() {
			public ImsQcAssignUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsQcAssignUI imsQcAssignUI =new ImsQcAssignUI();
				imsQcAssignUI.setTodayOsOrders((String) rs.getString("count"));
				
				return imsQcAssignUI;
			}
		};
	
		try {
			logger.debug("getTodayOutstandingOrders : " + SQL);
			imsQcAssignUIList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
			//logger.debug("getTodayOutstandingOrders : " +imsQcAssignUIList.get(0).getTodayOsOrders());
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsQcAssignUIList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getTodayOutstandingOrders():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return imsQcAssignUIList;
	}
	
	public List<ImsQcAssignUI> getTotalOrders (String staffId)throws DAOException {
		List<ImsQcAssignUI> imsQcAssignUIList = new ArrayList<ImsQcAssignUI>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String SQL = " select count(distinct orders_id) count from BOMWEB_SALES_QC_ASSIGN a "+
		  " where a.ASSIGNEE = :assignee  ";
		
		params.addValue("assignee", staffId);
		
		
		ParameterizedRowMapper<ImsQcAssignUI> mapper = new ParameterizedRowMapper<ImsQcAssignUI>() {
			public ImsQcAssignUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsQcAssignUI imsQcAssignUI =new ImsQcAssignUI();
				imsQcAssignUI.setTotalOrders((String) rs.getString("count"));
				
				return imsQcAssignUI;
			}
		};
	
		try {
			logger.debug("getTotalOrders : " + SQL);
			imsQcAssignUIList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
			//logger.debug("getTodayOutstandingOrders : " +imsQcAssignUIList.get(0).getTodayOsOrders());
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsQcAssignUIList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getTotalOrders():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return imsQcAssignUIList;
			
	}
	
	public List<ImsQcAssignUI> getP7dayOutstandingOrders (String staffId)throws DAOException {
		
		List<ImsQcAssignUI> imsQcAssignUIList = new ArrayList<ImsQcAssignUI>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String SQL =    " select count(distinct orders_id) count from BOMWEB_SALES_QC_ASSIGN a "+
						" where a.ASSIGNEE = :assignee "+
						" and trunc(assign_date) <= trunc(sysdate) "+
						" and trunc(assign_date) >= trunc(sysdate-7)";
		
		params.addValue("assignee", staffId);
		
		
		ParameterizedRowMapper<ImsQcAssignUI> mapper = new ParameterizedRowMapper<ImsQcAssignUI>() {
			public ImsQcAssignUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsQcAssignUI imsQcAssignUI =new ImsQcAssignUI();
				imsQcAssignUI.setPast7daysAssignedOrders((String) rs.getString("count"));
				
				return imsQcAssignUI;
			}
		};
	
		try {
			logger.debug("getP7dayOutstandingOrders : " + SQL);
			imsQcAssignUIList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
			//logger.debug("getTodayOutstandingOrders : " +imsQcAssignUIList.get(0).getTodayOsOrders());
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsQcAssignUIList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getP7dayOutstandingOrders():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return imsQcAssignUIList;
	}
	
	public List<ImsQcAssignUI> getTodayAssignedOrders (String staffId)throws DAOException {
		List<ImsQcAssignUI> imsQcAssignUIList = new ArrayList<ImsQcAssignUI>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		String SQL = " select count(distinct orders_id) count from BOMWEB_SALES_QC_ASSIGN a "+
					 " where a.ASSIGNEE = :assignee "+
					 " and trunc(assign_date) = trunc(sysdate) ";
		
		params.addValue("assignee", staffId);
		
		ParameterizedRowMapper<ImsQcAssignUI> mapper = new ParameterizedRowMapper<ImsQcAssignUI>() {
			public ImsQcAssignUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsQcAssignUI imsQcAssignUI =new ImsQcAssignUI();
				imsQcAssignUI.setTodayAssignedOrders((String) rs.getString("count"));
				
				return imsQcAssignUI;
			}
		};
	
		try {
			logger.debug("getP7dayOutstandingOrders : " + SQL);
			imsQcAssignUIList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
			//logger.debug("getTodayOutstandingOrders : " +imsQcAssignUIList.get(0).getTodayOsOrders());
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			imsQcAssignUIList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getP7dayOutstandingOrders():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return imsQcAssignUIList;
		
	}

	public void insertQcAssignBulk(List<ImsQcAssignUI> imsQcAssignUI) {
		// TODO Auto-generated method stub
//		if(imsQcAssignUI != null ){
//			MapSqlParameterSource params = new MapSqlParameterSource();
//			
//			String SQL = "Insert into BOMWEB_SALES_QC_ASSIGN (ORDERS_ID,ASSIGNEE,ASSIGN_DATE,CREATE_DATE,CREATE_BY,LAST_UPD_DATE,LAST_UPD_BY) Values "+
//						 "(:orders_id,:assignee,Sysdate,SYSDATE,:createBy,SYSDATE,:last_up_by) ";
//			
//	        params.addValue("orders_id", imsQcAssignUI.getOrderIds());
//			params.addValue("assignee", imsQcAssignUI.getAssignee());
//			params.addValue("createBy", imsQcAssignUI.getCreateBy());
//			params.addValue("last_up_by", imsQcAssignUI.getLastUpBy());
//			params.addValue("staffId", imsQcAssignUI);
//			
//			try{
//					logger.debug("insertQcAssign: " + SQL);
//					simpleJdbcTemplate.update(SQL,params);
//				
//			}catch (Exception e) {
//				logger.error("Exception caught in insertQcAssign()", e);
//				throw new DAOException(e.getMessage(), e);
//			}		
	}

	public void insertNewQcStaffSkills(QcStaffDTO qcStaffDTO) throws DAOException {
		// TODO Auto-generated method stub
		if(qcStaffDTO != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();

			String sqlSkills = " Insert into BOMWEB_SALES_QC_SK_ASSIGN "+
			   			 	   " (STAFF_ID, SKILLS, CREATE_DATE, CREATE_BY, LAST_UPD_DATE, LAST_UPD_BY) Values"+
			   			 	   " (:staffId, :skill, sysdate, :createBy, sysdate, :lastUpdBy)";
			
	        params.addValue("staffId", qcStaffDTO.getStaffId());
	        params.addValue("skill", qcStaffDTO.getSkills());
			params.addValue("createBy", qcStaffDTO.getCreateBy());
			params.addValue("lastUpdBy", qcStaffDTO.getLastUpBy());

			try{
				logger.debug("insertNewQcStaffSkills skills: " + sqlSkills);
				logger.debug("insertNewQcStaffSkills skills Inserted: " + qcStaffDTO.getSkills());
				simpleJdbcTemplate.update(sqlSkills,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in insertNewQcStaffSkills()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public void insertNewQcStaffInfo(QcStaffDTO qcStaffDTO) throws DAOException {
		// TODO Auto-generated method stub
		if(qcStaffDTO != null ){
			MapSqlParameterSource paramsStaff = new MapSqlParameterSource();
			
			String sqlStaff = " Insert into BOMWEB_SALES_QC (QC_STAFF_ID, STATUS, TODAY_OS, TODAY_ASSIGNED, PAST7_DAYS_OS, TOT_OS, CREATE_DATE, CREATE_BY, LAST_UPD_DATE, LAST_UPD_BY) Values "+
  			 				  " (:staffId, :status, 0, 0, 0, 0, sysdate,:createBy ,sysdate,:lastUpdBy)";

			paramsStaff.addValue("staffId", qcStaffDTO.getStaffId());
			paramsStaff.addValue("status", qcStaffDTO.getStatus());
			paramsStaff.addValue("createBy", qcStaffDTO.getCreateBy());
			paramsStaff.addValue("lastUpdBy", qcStaffDTO.getLastUpBy());
			
			try{
				logger.debug("insertNewQcStaff staff: " + sqlStaff);
				simpleJdbcTemplate.update(sqlStaff,paramsStaff);
				
			}catch (Exception e) {
				logger.error("Exception caught in insertNewQcStaffInfo()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}

	public void updateControlStaffRatio(ImsDsQCStaffAdminUI imsStaffAdminUI) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("updateControlStaffRatio called:");
		
		if(imsStaffAdminUI != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE W_CODE_LKUP " + 
							" SET description = :ratio " + 
							" WHERE grp_id = 'SB_IMS_QC_STAFF_RAT'" +
							" AND code = 'CONTROL' ";
			
            params.addValue("ratio", imsStaffAdminUI.getControlStaffRatio());
			
            logger.debug("updateControlStaffRatio imsStaffAdminUI.getControlStaffRatio(): " + imsStaffAdminUI.getControlStaffRatio());
			try{
					logger.debug("updateControlStaffRatio: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateControlStaffRatio()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public void updateCleanStaffRatio(ImsDsQCStaffAdminUI imsStaffAdminUI) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("updateControlStaffRatio called:");
		
		if(imsStaffAdminUI != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE W_CODE_LKUP " + 
							" SET description = :ratio " + 
							" WHERE grp_id = 'SB_IMS_QC_STAFF_RAT'" +
							" AND code = 'CLEAN' ";
			
            params.addValue("ratio", imsStaffAdminUI.getCleanStaffRatio());
            logger.debug("updateControlStaffRatio imsStaffAdminUI.getControlStaffRatio(): " + imsStaffAdminUI.getCleanStaffRatio());
			
			try{
					logger.debug("updateCleanStaffRatio: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateCleanStaffRatio()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public List<ImsDsQCStaffAdminUI> getQcStaffRatio(final String type)throws DAOException {
		List<ImsDsQCStaffAdminUI> result = new ArrayList<ImsDsQCStaffAdminUI>();
		
		String SQL = " select description from w_code_lkup where grp_id = 'SB_IMS_QC_STAFF_RAT' and code =:type " ; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("type", type);
		
		ParameterizedRowMapper<ImsDsQCStaffAdminUI> mapper = new ParameterizedRowMapper<ImsDsQCStaffAdminUI>() {
			public ImsDsQCStaffAdminUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQCStaffAdminUI dto = new ImsDsQCStaffAdminUI();
				
				if (type.equalsIgnoreCase("CONTROL"))
					dto.setControlStaffRatio((String) rs.getString("description"));
				else if (type.equalsIgnoreCase("CLEAN"))
					dto.setCleanStaffRatio((String) rs.getString("description"));
					
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
			logger.debug("getQcStaffRatio SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getQcStaffRatio():", e);
			throw new DAOException(e.getMessage(), e);
		}	
	
//		if (type.equalsIgnoreCase("CONTROL"))
//			return result;
//		else if (type.equalsIgnoreCase("CLEAN"))
//			return result;
		
		return result;
		
	}

	
	public void updateControlNTVStaffRatio(ImsDsQCStaffAdminUI imsStaffAdminUI) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("updateControlStaffRatio called:");
		
		if(imsStaffAdminUI != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE W_CODE_LKUP " + 
							" SET description = :ratio " + 
							" WHERE grp_id = 'SB_IMS_QC_NTVSTF_RAT'" +
							" AND code = 'CONTROL' ";
			
            params.addValue("ratio", imsStaffAdminUI.getControlStaffRatio());
			
            logger.debug("updateControlStaffRatio imsStaffAdminUI.getControlStaffRatio(): " + imsStaffAdminUI.getControlStaffRatio());
			try{
					logger.debug("updateControlStaffRatio: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateControlStaffRatio()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public void updateCleanNTVStaffRatio(ImsDsQCStaffAdminUI imsStaffAdminUI) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("updateControlStaffRatio called:");
		
		if(imsStaffAdminUI != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE W_CODE_LKUP " + 
							" SET description = :ratio " + 
							" WHERE grp_id = 'SB_IMS_QC_NTVSTF_RAT'" +
							" AND code = 'CLEAN' ";
			
            params.addValue("ratio", imsStaffAdminUI.getCleanStaffRatio());
            logger.debug("updateControlStaffRatio imsStaffAdminUI.getControlStaffRatio(): " + imsStaffAdminUI.getCleanStaffRatio());
			
			try{
					logger.debug("updateCleanStaffRatio: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateCleanStaffRatio()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	public List<ImsDsQCStaffAdminUI> getQcNTVStaffRatio(final String type)throws DAOException {
		List<ImsDsQCStaffAdminUI> result = new ArrayList<ImsDsQCStaffAdminUI>();
		
		String SQL = " select description from w_code_lkup where grp_id = 'SB_IMS_QC_NTVSTF_RAT' and code =:type " ; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("type", type);
		
		ParameterizedRowMapper<ImsDsQCStaffAdminUI> mapper = new ParameterizedRowMapper<ImsDsQCStaffAdminUI>() {
			public ImsDsQCStaffAdminUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQCStaffAdminUI dto = new ImsDsQCStaffAdminUI();
				
				if (type.equalsIgnoreCase("CONTROL"))
					dto.setControlStaffRatio((String) rs.getString("description"));
				else if (type.equalsIgnoreCase("CLEAN"))
					dto.setCleanStaffRatio((String) rs.getString("description"));
					
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
			logger.debug("getQcStaffRatio SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getQcStaffRatio():", e);
			throw new DAOException(e.getMessage(), e);
		}	
	
//		if (type.equalsIgnoreCase("CONTROL"))
//			return result;
//		else if (type.equalsIgnoreCase("CLEAN"))
//			return result;
		
		return result;
		
	}
	
	
	public boolean checkOrderExist(String orderId) throws DAOException {
		List<String> result = new ArrayList<String>();
				
				MapSqlParameterSource params = new MapSqlParameterSource();
				
				StringBuffer SQL =  new StringBuffer( 
						"	select count(*) count from BOMWEB_ORDER_QC_ASSIGN where orders_id = :orderId  	" );
				
				params.addValue("orderId", orderId);
				
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String tempResult = "";
					tempResult=(String) rs.getString("count");
					return tempResult;
				}
			};
		
			try {
				logger.debug("checkOrderExist : " + SQL);
				result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
				logger.debug("checkOrderExist : " + result.get(0));
			} catch (EmptyResultDataAccessException erdae) {
				logger.debug("EmptyResultDataAccessException");
				result = null;
			} catch (Exception e) {
				logger.debug("Exception caught in checkOrderExist():", e);
				throw new DAOException(e.getMessage(), e);
			}	
			   if (result.size()==0||(result.get(0).equalsIgnoreCase("0"))){
					return false;
				}else{
					return true;
				}
			}

	public void insertQcProcess(ImsDsQcProcessDetailUI imsDsQcProcessDetailUI) throws DAOException {
		// TODO Auto-generated method stub
		if(imsDsQcProcessDetailUI != null ){
			
			logger.info("insertQcProcess START: ");
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = " Insert into BOMWEB_ORDER_QC_ASSIGN "+
							  " (ORDERS_ID, QC_DATE, QC_FINDINGS, ORDER_DISTRICT,ORDER_PLACE, QC_Status,Reason_code, CREATE_DATE, CREATE_BY, LAST_UPD_DATE, LAST_UPD_BY) Values "+
							  " (:orderId, TO_DATE( :qcDate ,'YYYY/MM/DD HH24:MI:SS'), (TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) ||:action ||' '|| :reasonDesc || chr(10) ||:qcFindings || chr(10)), :ordDist,:orderPlace, :qcStatus, :reasonCode, sysdate, :createBy , sysdate, :lastUpdBy)";
			 
			params.addValue("orderId", imsDsQcProcessDetailUI.getOrderId());
			params.addValue("qcDate", imsDsQcProcessDetailUI.getQcDateTime());
			params.addValue("qcFindings", imsDsQcProcessDetailUI.getQcFinding());
			//params.addValue("qcCallTime", imsDsQcProcessDetailUI.getQcCallTime());
			params.addValue("ordDist", imsDsQcProcessDetailUI.getOrderDistict());
			params.addValue("orderPlace", imsDsQcProcessDetailUI.getOrderPlace());
			params.addValue("qcStatus", imsDsQcProcessDetailUI.getQcStatus());
			logger.debug("imsDsQcProcessDetailUI.getQcStatus(): " + imsDsQcProcessDetailUI.getQcStatus());
			params.addValue("action", imsDsQcProcessDetailUI.getAction());
			
			if (imsDsQcProcessDetailUI.getAction().equals("PASS")) {params.addValue("reasonCode", "N/A");params.addValue("reasonDesc", "");}
			else if (imsDsQcProcessDetailUI.getAction().equals("CANNOTQC")){params.addValue("reasonCode",imsDsQcProcessDetailUI.getReasonCQCode());params.addValue("reasonDesc", imsDsQcProcessDetailUI.getReasonCQDesc());}
			else if (imsDsQcProcessDetailUI.getAction().equals("FAIL")){params.addValue("reasonCode", imsDsQcProcessDetailUI.getReasonFailCode());params.addValue("reasonDesc", imsDsQcProcessDetailUI.getReasonFailDesc());}
			else if (imsDsQcProcessDetailUI.getAction().equals("RNA")){params.addValue("reasonCode", "N/A");params.addValue("reasonDesc", "");}
			else if (imsDsQcProcessDetailUI.getAction().equals("PASSWITHOUTQC")){params.addValue("reasonCode", "P0002");params.addValue("reasonDesc", "");}
			params.addValue("createBy", imsDsQcProcessDetailUI.getCreateBy());
			params.addValue("lastUpdBy", imsDsQcProcessDetailUI.getLastUpBy());
			
			try{
				logger.debug("insertQcProcess : " + SQL);
				simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in insertQcProcess()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}	
	
	public void updateQcProcess(ImsDsQcProcessDetailUI imsDsQcProcessDetailUI) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("updateQcProcess called:");
		
		if(imsDsQcProcessDetailUI != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			String SQL ="";
			String conditions1="";
			String conditions2="";
			
			if (imsDsQcProcessDetailUI.getAction().equals("PASS") 
					|| imsDsQcProcessDetailUI.getAction().equals("RNA")
					|| imsDsQcProcessDetailUI.getAction().equals("PASSWITHOUTQC")){
				conditions1 = "    qc_findings = TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) ||:action || chr(10) || :qc_findings || chr(10) || chr(10) || qc_findings, ";
			}else if (imsDsQcProcessDetailUI.getAction().equals("CANNOTQC") || imsDsQcProcessDetailUI.getAction().equals("FAIL")){
				conditions1 = "    qc_findings = TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) ||:action ||' '|| :reasonDesc  || chr(10) || :qc_findings || chr(10) || chr(10) || qc_findings, ";
			}
			
			if (imsDsQcProcessDetailUI.getAction().equals("PASS") 
					|| imsDsQcProcessDetailUI.getAction().equals("RNA")
					|| imsDsQcProcessDetailUI.getAction().equals("PASSWITHOUTQC")){
				conditions2 = "    qc_findings = TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) ||:action || chr(10) || chr(10) || qc_findings, ";
			}else if (imsDsQcProcessDetailUI.getAction().equals("CANNOTQC") || imsDsQcProcessDetailUI.getAction().equals("FAIL")){
				conditions2 = "    qc_findings = TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) ||:action ||' '|| :reasonDesc || chr(10) || chr(10)|| qc_findings, ";
			}
			//TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) ||:action ||' '|| :reasonDesc || chr(10) ||:qcFindings || chr(10) || chr(10) || qc_findings
			
			
			if (imsDsQcProcessDetailUI.getQcFinding().length()>0){
				SQL = 	" update BOMWEB_ORDER_QC_ASSIGN 				" +
								" set qc_date = TO_DATE( :qcDate ,'YYYY/MM/DD HH24:MI:SS ')," +
								//"    qc_findings =TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) || :qc_findings || chr(10) || chr(10) || qc_findings,	" +
								//"    qc_calling_time = :qcCallTime,				" +
								//"    qc_findings = TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) ||:action ||' '|| :reasonDesc || chr(10) ||:qcFindings || chr(10) || chr(10) || qc_findings, "+
									 conditions1	                              +
								"    order_district = :orderDist,				" +
								"    order_place = :orderPlace,	  				" +
								"    qc_status = :qcStatus,						" +
								"    Reason_code = :reasonCode,					" +
								"    LAST_UPD_BY = :lastUpdBy,					" +
								"    LAST_UPD_Date = sysdate					" +
								" where orders_id = :orderID					" ;
			}else{
				SQL = 	" update BOMWEB_ORDER_QC_ASSIGN 				" +
								" set qc_date = TO_DATE( :qcDate ,'YYYY/MM/DD HH24:MI:SS ')," +
								//"    qc_findings =TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) || :qc_findings || chr(10) || chr(10) || qc_findings,	" +
								//"    qc_calling_time = :qcCallTime,				" +
								//"    qc_findings = TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) ||:action ||' '|| :reasonDesc || chr(10) || chr(10) || qc_findings, "+
									 conditions2								  +	
								"    order_district = :orderDist,				" +
								"    order_place = :orderPlace,	  				" +
								"    qc_status = :qcStatus,						" +
								"    Reason_code = :reasonCode,					" +
								"    LAST_UPD_BY = :lastUpdBy,					" +
								"    LAST_UPD_Date = sysdate					" +
								" where orders_id = :orderID					" ;
			}
			
            params.addValue("orderDist", imsDsQcProcessDetailUI.getOrderDistict());
            params.addValue("qcStatus", imsDsQcProcessDetailUI.getQcStatus());
            params.addValue("qcDate", imsDsQcProcessDetailUI.getQcDateTime());
            if (imsDsQcProcessDetailUI.getQcFinding().length()>0){
            	params.addValue("qc_findings", imsDsQcProcessDetailUI.getQcFinding());
            }//params.addValue("qcCallTime", imsDsQcProcessDetailUI.getQcCallTime());
            params.addValue("orderDist", imsDsQcProcessDetailUI.getOrderDistict());
            params.addValue("orderPlace", imsDsQcProcessDetailUI.getOrderPlace());
            params.addValue("orderID", imsDsQcProcessDetailUI.getOrderId());
            params.addValue("lastUpdBy", imsDsQcProcessDetailUI.getLastUpBy());
            params.addValue("action", imsDsQcProcessDetailUI.getAction());
			if (imsDsQcProcessDetailUI.getAction().equals("PASS")) {params.addValue("reasonCode", "N/A"); }
			else if (imsDsQcProcessDetailUI.getAction().equals("CANNOTQC")){params.addValue("reasonCode",imsDsQcProcessDetailUI.getReasonCQCode());params.addValue("reasonDesc", imsDsQcProcessDetailUI.getReasonCQDesc());}
			else if (imsDsQcProcessDetailUI.getAction().equals("FAIL")){params.addValue("reasonCode", imsDsQcProcessDetailUI.getReasonFailCode());params.addValue("reasonDesc", imsDsQcProcessDetailUI.getReasonFailDesc());}
			else if (imsDsQcProcessDetailUI.getAction().equals("RNA")){params.addValue("reasonCode", "N/A");}
			else if (imsDsQcProcessDetailUI.getAction().equals("PASSWITHOUTQC")){params.addValue("reasonCode", "P0002");}
			/*
            if (imsDsQcProcessDetailUI.getAction().equals("PASS")) {params.addValue("reasonCode", "N/A");}
			else if (imsDsQcProcessDetailUI.getAction().equals("CANNOTQC")){params.addValue("reasonCode",imsDsQcProcessDetailUI.getReasonCQCode());}
			else if (imsDsQcProcessDetailUI.getAction().equals("FAIL")){params.addValue("reasonCode", imsDsQcProcessDetailUI.getReasonFailCode());}
			else if (imsDsQcProcessDetailUI.getAction().equals("RNA")){params.addValue("reasonCode", "N/A");}
			*/
            
            //logger.debug("updateQcProcess (): " + imsStaffAdminUI.getCleanStaffRatio());
			
			try{
					logger.debug("updateQcProcess: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateQcProcess()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
		
	}

	public String getAssignDate(String orderID) throws DAOException {
		// TODO Auto-generated method stub
		List<ImsDsQcProcessDetailUI> result = new ArrayList<ImsDsQcProcessDetailUI>();
		
		String SQL = " select TO_CHAR(Assign_Date, 'yyyy/mm/dd HH:mm:ss') Assign_Date from BOMWEB_SALES_QC_ASSIGN where orders_id = :orderId " ; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderID);
		
		ParameterizedRowMapper<ImsDsQcProcessDetailUI> mapper = new ParameterizedRowMapper<ImsDsQcProcessDetailUI>() {
			public ImsDsQcProcessDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQcProcessDetailUI dto = new ImsDsQcProcessDetailUI();
				dto.setAssignDate((String) rs.getString("Assign_Date"));
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
			logger.debug("getAssignDate SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getAssignDate():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if (result.size()>0)
			return result.get(0).getAssignDate();
		else return "";
	}

	public String getAssignee(String orderID) throws DAOException {
		List<ImsDsQcProcessDetailUI> result = new ArrayList<ImsDsQcProcessDetailUI>();
		
		String SQL = " select Assignee from BOMWEB_SALES_QC_ASSIGN where orders_id = :orderId " ; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderID);
		
		ParameterizedRowMapper<ImsDsQcProcessDetailUI> mapper = new ParameterizedRowMapper<ImsDsQcProcessDetailUI>() {
			public ImsDsQcProcessDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQcProcessDetailUI dto = new ImsDsQcProcessDetailUI();
				dto.setAssignee((String) rs.getString("Assignee"));
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
			logger.debug("getAssignee SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getAssignee():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		if (result.size()>0)
			return result.get(0).getAssignee();
		else return "";
	}
	
	public String getPreInstallInd(String orderID) throws DAOException {

		String SQL = " select PRE_INST_IND from BOMWEB_ORDER_IMS where order_id = ? " ; 
		String result;
		
		try {
			result = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class,orderID);
			
			if(StringUtils.isBlank(result))
				result="N";
			
			logger.debug("getPreInstallInd SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = "";
		} catch (Exception e) {
			logger.debug("Exception caught in getPreInstallInd():", e);
			throw new DAOException(e.getMessage(), e);
		}			
		
		return result;
	}
	
	public List<ImsDsQcProcessDetailUI> getQCProcessInfo(String orderId) throws DAOException {
		// TODO Auto-generated method stub
		List<ImsDsQcProcessDetailUI> result = new ArrayList<ImsDsQcProcessDetailUI>();
		/*
		String SQL = "  SELECT b.QC_CALL_TIME,a.ORDER_DISTRICT,a.ORDER_PLACE " +
					 " from BOMWEB_ORDER_QC_ASSIGN a,BOMWEB_ORDER_ims b" +
					 " where a.orders_id = :orderId  " +
					 " and a.orders_id = b.order_id " ; */
		
		String SQL = " SELECT c.QC_CALL_TIME,a.ORDER_DISTRICT,a.ORDER_PLACE " + 
					 " from BOMWEB_ORDER_QC_ASSIGN a,bomweb_sales_qc_assign b,BOMWEB_ORDER_ims c " +
					 " where c.order_id  = :orderId " +
					 " and   c.order_id  = a.orders_id (+) " + 
					 " and   c.order_id  = b.orders_id ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		
		ParameterizedRowMapper<ImsDsQcProcessDetailUI> mapper = new ParameterizedRowMapper<ImsDsQcProcessDetailUI>() {
			public ImsDsQcProcessDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQcProcessDetailUI dto = new ImsDsQcProcessDetailUI();
		
				dto.setQcCallTime((String) rs.getString("QC_CALL_TIME"));
				dto.setOrderDistict((String) rs.getString("ORDER_DISTRICT"));
				dto.setOrderPlace((String) rs.getString("ORDER_PLACE"));
				
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper,params);
			logger.debug("getQCProcessInfo SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getQCProcessInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		/*
		if (result.size()>0)
			return result.get(0).getQcCallTime();
		else return "";
		*/
		return result;
		
	}

	public List<ImsDsQcProcessDetailUI> getReasonCQDesc() throws DAOException {
		// TODO Auto-generated method stub
		List<ImsDsQcProcessDetailUI> result = new ArrayList<ImsDsQcProcessDetailUI>();
		
		String SQL = " select CODE,description from w_code_lkup where grp_id = 'SB_IMS_QC_REASON' and code like 'C%' " ; 
		
		ParameterizedRowMapper<ImsDsQcProcessDetailUI> mapper = new ParameterizedRowMapper<ImsDsQcProcessDetailUI>() {
			public ImsDsQcProcessDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQcProcessDetailUI dto = new ImsDsQcProcessDetailUI();
				
				dto.setReasonCQCode((String) rs.getString("CODE"));
				dto.setReasonCQDesc((String) rs.getString("description"));
				
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper);
			logger.debug("getReasonCQDesc SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getReasonCQDesc():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		return result;
		
	}

	public List<ImsDsQcProcessDetailUI> getReasonFailDesc() throws DAOException {
		List<ImsDsQcProcessDetailUI> result = new ArrayList<ImsDsQcProcessDetailUI>();
		
		String SQL = " select CODE,description from w_code_lkup where grp_id = 'SB_IMS_QC_REASON' and code like 'F%' " ; 
		
		ParameterizedRowMapper<ImsDsQcProcessDetailUI> mapper = new ParameterizedRowMapper<ImsDsQcProcessDetailUI>() {
			public ImsDsQcProcessDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQcProcessDetailUI dto = new ImsDsQcProcessDetailUI();
				
				dto.setReasonFailCode((String) rs.getString("CODE"));
				dto.setReasonFailDesc((String) rs.getString("description"));
				
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper);
			logger.debug("getReasonFailDesc SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getReasonFailDesc():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		return result;
		
	}

	public String getReasonCode(String desc) throws DAOException {
		List<ImsDsQcProcessDetailUI> result = new ArrayList<ImsDsQcProcessDetailUI>();
		
		String SQL = " select code from w_code_lkup where grp_id = 'SB_IMS_QC_REASON' and  description =:desc " ; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("desc", desc);
		
		ParameterizedRowMapper<ImsDsQcProcessDetailUI> mapper = new ParameterizedRowMapper<ImsDsQcProcessDetailUI>() {
			public ImsDsQcProcessDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQcProcessDetailUI dto = new ImsDsQcProcessDetailUI();
				dto.setReasonCode((String) rs.getString("code"));
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
			logger.debug("getReasonCode SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getReasonCode():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		if (result.size()>0)
			return result.get(0).getReasonCode();
		else return "";
	}

	public void releaseQcOrders(QcStaffDTO qcStaffDTO) throws DAOException {
		// TODO Auto-generated method stub
		//logger.info("updateSalesTypeAndLocation:"+amend.getSalesType()+", location: " + amend.getLocation());
		
		if(qcStaffDTO != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			MapSqlParameterSource params2 = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE BOMWEB_SALES_QC " + 
							" SET TODAY_OS = 0, " + 
							" TODAY_ASSIGNED = 0, " +  
							" PAST7_DAYS_OS = 0, " +
							" TOT_OS = 0, " + 
							" LAST_UPD_DATE = SYSDATE, " + 
							" LAST_UPD_BY = :lastUpdBy " + 
							" WHERE QC_STAFF_ID = :staffId ";
			
            params.addValue("staffId", qcStaffDTO.getStaffId());
            params.addValue("lastUpdBy",qcStaffDTO.getLastUpBy());
			
            String SQL2 = 	" UPDATE BOMWEB_SALES_QC_ASSIGN " + 
							" SET assignee = '' 		,    " + 
							" LAST_UPD_DATE = SYSDATE 	,	" + 
							" LAST_UPD_BY = :lastUpdBy 		" + 
							" WHERE assignee = :staffId 	";
            
            params2.addValue("staffId", qcStaffDTO.getStaffId());
            params2.addValue("lastUpdBy",qcStaffDTO.getLastUpBy());            
            
			try{
					logger.debug("releaseQcOrders BOMWEB_SALES_QC: " + SQL);
					logger.debug("releaseQcOrders BOMWEB_SALES_QC_ASSIGN: " + SQL2);
					simpleJdbcTemplate.update(SQL,params);
					simpleJdbcTemplate.update(SQL2,params2);
				
			}catch (Exception e) {
				logger.error("Exception caught in releaseQcOrders()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}

	public void removeQcStaff(QcStaffDTO qcStaffDTO) throws DAOException {
		// TODO Auto-generated method stub
		if(qcStaffDTO != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			MapSqlParameterSource params2 = new MapSqlParameterSource();
			MapSqlParameterSource params3 = new MapSqlParameterSource();
			
//			String SQL = 	" UPDATE BOMWEB_SALES_QC 	" + 
//							" SET STATUS = 'IA',		" + 
//							" TODAY_OS = 0, 			" + 
//							" TODAY_ASSIGNED = 0, 		" +  
//							" PAST7_DAYS_OS = 0, 		" +
//							" TOT_OS = 0, 				" + 
//							" LAST_UPD_DATE = SYSDATE, 	" + 
//							" LAST_UPD_BY = :lastUpdBy 	" + 
//							" WHERE QC_STAFF_ID = :staffId ";
			
			String SQL = " Delete from BOMWEB_SALES_QC where qc_staff_id=:staffId ";
			
			params.addValue("staffId", qcStaffDTO.getStaffId());
           // params.addValue("lastUpdBy",qcStaffDTO.getLastUpBy());
			
            String SQL2 = 	" UPDATE BOMWEB_SALES_QC_ASSIGN " + 
							" SET assignee = '', 		    " + 
							" LAST_UPD_DATE = SYSDATE, 		" + 
							" LAST_UPD_BY = :lastUpdBy 		" + 
							" WHERE assignee = :staffId 	";
            
            params2.addValue("staffId", qcStaffDTO.getStaffId());
            params2.addValue("lastUpdBy",qcStaffDTO.getLastUpBy());             
            
            String SQL3 = " Delete from BOMWEB_SALES_QC_SK_ASSIGN where staff_id=:staffId ";
            params3.addValue("staffId", qcStaffDTO.getStaffId());
            
			try{
					logger.debug("removeQcStaff BOMWEB_SALES_QC: " + SQL);
					logger.debug("removeQcStaff BOMWEB_SALES_QC_ASSIGN: " + SQL2);
					logger.debug("removeQcStaff BOMWEB_SALES_QC_SK_ASSIGN: " + SQL3);
					simpleJdbcTemplate.update(SQL,params);
					simpleJdbcTemplate.update(SQL2,params2);
					simpleJdbcTemplate.update(SQL3,params3);
				
			}catch (Exception e) {
				logger.error("Exception caught in removeQcStaff()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}

	public List<ImsDsQcProcessDetailUI>getQcRemark (String orderId) throws DAOException {
		// TODO Auto-generated method stub
		List<ImsDsQcProcessDetailUI> result = new ArrayList<ImsDsQcProcessDetailUI>();
		
		String SQL = " select qc_findings from BOMWEB_ORDER_QC_ASSIGN where orders_id=:orderId " ; 
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		
		ParameterizedRowMapper<ImsDsQcProcessDetailUI> mapper = new ParameterizedRowMapper<ImsDsQcProcessDetailUI>() {
			public ImsDsQcProcessDetailUI mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsDsQcProcessDetailUI dto = new ImsDsQcProcessDetailUI();
				dto.setQcFinding((String) rs.getString("qc_findings"));
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
			logger.debug("getQcRemark SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getQcRemark():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		return result;
	}

	public Boolean checkIfAssignisNull (String orderId)throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				" select count(*) count from BOMWEB_SALES_QC_ASSIGN where orders_id=:orderId " );
		
		params.addValue("orderId", orderId);
		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("count");
			return tempResult;
		}
	};

	try {
		logger.debug("userId @ checkIfAssignisNull: " + orderId);
		logger.debug("checkIfAssignisNull : " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in checkIfAssignisNull():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	logger.debug("result.get(0).toString() in checkIfAssignisNull():" + result.get(0).toString());
		if(result.get(0).toString().equalsIgnoreCase("0")){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isQcStaffExist(String staffId) throws DAOException {
		// TODO Auto-generated method stub
		String SQL =  " select count(*) count from bomweb_sales_lkup_v lv,BOMWEB_SALES_QC bsq "+ 
	      			  " where lv.staff_id = bsq.qc_staff_id "+
	      			  " and bsq.qc_staff_id =:staffId ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("count"));
		        return temp;
		    }
		};
		
    	List<String> resultList = new ArrayList<String>();
		
		try {
			logger.debug("isQcStaffExist SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper,params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isQcStaffExist:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (resultList.size()>0 ? !"0".equals(resultList.get(0)):false);
	}
	
	public Boolean isStaffExist(String staffId)	throws DAOException {
		logger.debug("isStaffExist: " + staffId);
		
		String SQL = 	" select count(*) cnt from " +
				" bomweb_sales_lkup_v " +
				" where staff_id like '"+staffId+"'  " +
				" or org_staff_id = '"+staffId+"' " ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("cnt"));
		        return temp;
		    }
		};
		
    	List<String> resultList = new ArrayList<String>();
		
		try {
			logger.debug("isStaffExist SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isStaffExist:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (resultList.size()>0 ? !"0".equals(resultList.get(0)):false);
	}
	
	public void updateQCstaffSkillsAndStatus(QcStaffDTO qcStaffDTO) throws DAOException {
		// TODO Auto-generated method stub
		//logger.info("updateSalesTypeAndLocation:"+amend.getSalesType()+", location: " + amend.getLocation());
		
		if(qcStaffDTO != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			MapSqlParameterSource params2 = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE BOMWEB_SALES_QC " + 
							" SET Status = :status, " + 
							" LAST_UPD_DATE = SYSDATE, " + 
							" LAST_UPD_BY = :lastUpdBy " + 
							" WHERE QC_STAFF_ID = :staffId ";
			
            params.addValue("staffId", qcStaffDTO.getStaffId());
            params.addValue("status", qcStaffDTO.getStatus());
            params.addValue("lastUpdBy",qcStaffDTO.getLastUpBy());
            
            logger.debug("updateQCstaffSkillsAndStatus  qcStaffDTO.getStatus(): " +  qcStaffDTO.getStatus());
			
            String SQL2 = 	" DELETE from BOMWEB_SALES_QC_SK_ASSIGN where staff_id = :staffId  ";
            params2.addValue("staffId", qcStaffDTO.getStaffId());
            
			try{
					logger.debug("updateQCstaffSkillsAndStatus BOMWEB_SALES_QC: " + SQL);
					logger.debug("updateQCstaffSkillsAndStatus BOMWEB_SALES_QC_SK_ASSIGN: " + SQL2);
					simpleJdbcTemplate.update(SQL,params);
					simpleJdbcTemplate.update(SQL2,params2);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateQCstaffSkillsAndStatus()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}

	public String getAmendRemarks(String orderId) throws DAOException {
			List<String> resultList = new ArrayList<String>();
		
		String SQL = " SELECT    create_by "+
					 "	       || '  ' "+
					"	       || create_date "+
					"	       || CHR (10)    "+
				    "	       || (SELECT wq_nature_desc "+
				    "		          FROM q_wq_nature "+
					"	           WHERE wq_nature_id IN (a.wq_nature_id)) "+
					"	       || CHR (10) "+
					"	       || remarks  Remark "+
					"	FROM q_wq_remarks a "+
					"	WHERE wq_id IN (SELECT wq_id "+
					"	                FROM q_work_queue "+
					"	                WHERE sb_id = :orderId) "+
					"	ORDER BY REMARK_SEQ DESC " ;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("Remark");
				return dto;
			}
		};
	
		try {
			resultList = simpleJdbcTemplate.query(SQL, mapper, params);
			logger.debug("getAmendRemarks SQL: " + SQL);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getAmendRemarks():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		String result="";
		for (String el:resultList){
			if (StringUtils.isNotBlank(el)){
				result = result + "\n" +el;
			}
		}
		return result;
		
	}

	public void updateQcRemarks(String orderId,BomSalesUserDTO userDto,String remarks) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("updateQcRemarks Start");
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = 		" update BOMWEB_ORDER_QC_ASSIGN 				" +
								" set qc_findings =TO_CHAR(SYSDATE, 'DD/MM/YYYY HH24:MI:SS') || ' BY ' || :lastUpdBy || chr(10) || :qc_findings || chr(10) || chr(10) || qc_findings,	" +
								"    LAST_UPD_BY = :lastUpdBy,					" +
								"    LAST_UPD_Date = sysdate					" +
								" where orders_id = :orderID					" ;
			
            params.addValue("orderID", orderId);
            params.addValue("lastUpdBy", userDto.getUsername());
            params.addValue("qc_findings", remarks);
			
			try{
					logger.debug("updateQcRemarks : " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateQcRemarks()", e);
				throw new DAOException(e.getMessage(), e);
			}		
	}
	
	public boolean isActiveQCstaff(String staffID) throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				" select count(*) count from BOMWEB_SALES_QC where qc_staff_id = :staffID " );
		
		params.addValue("staffID", staffID);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("count");
				return tempResult;
			}
		};

		try {
			//logger.debug("userId @ isActiveQCstaff: " + staffID);
			logger.debug("isActiveQCstaff : " + SQL);
			result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isActiveQCstaff():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		if(result.get(0).toString().equalsIgnoreCase("0")){
			return false;
		}else{
			return true;
		}
	}
	
	
	public boolean isOrderAwaitQC(String orderId) throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"select count(*) count from bomweb_order a, bomweb_order_ims b where a.order_status = '" +
				ImsConstants.IMS_ORDER_STATUS_AWAIT_QC +
				"' and a.order_id = :orderId " +
				"  and a.order_id = b.order_id" +
				"  and b.MUST_QC_IND = 'Y'");
		
		params.addValue("orderId", orderId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("count");
				return tempResult;
			}
		};

		try {
			//logger.debug("userId @ isActiveQCstaff: " + staffID);
			logger.debug("isOrderAwaitQC : " + SQL);
			result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isActiveQCstaff():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		if(result.get(0).toString().equalsIgnoreCase("0")){
			return false;
		}else{
			return true;
		}
	}
	
	
	public boolean isOrderAwaitSignOff(String orderId) throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"select count(*) count from bomweb_order a, bomweb_order_ims b where a.order_status = '" +
				ImsConstants.IMS_ORDER_STATUS_AWAIT_SIGNOFF +
				"' and a.order_id = :orderId " +
				"  and a.order_id = b.order_id" +
				"  and b.MUST_QC_IND = 'Y'");
		
		params.addValue("orderId", orderId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("count");
				return tempResult;
			}
		};

		try {
			//logger.debug("userId @ isActiveQCstaff: " + staffID);
			logger.debug("isOrderAwaitSignOff : " + SQL);
			result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isActiveQCstaff():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		if(result.get(0).toString().equalsIgnoreCase("0")){
			return false;
		}else{
			return true;
		}
	}
	
	public void updateOrderCount(ImsQcAssignUI imsQcAssignUI) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("updateOrderCount Start");
		
		if(imsQcAssignUI != null ){
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE BOMWEB_SALES_QC " + 
							" SET TODAY_OS = :todayOS, " + 
							" TODAY_ASSIGNED = :totalAssignOS, " + 
							" PAST7_DAYS_OS = :p7dayOS, " +
							" TOT_OS        = :totOS, " +
							" LAST_UPD_BY = :lastUpdBy, " + 
							" LAST_UPD_DATE = SYSDATE " + 
							" WHERE QC_STAFF_ID = :staffId ";
			
			/*logger.debug("updateOrderCount Today assigned: "+ this.getTodayAssignedOrders(imsQcAssignUI.getStaffId()).get(0).getTodayAssignedOrders());
			logger.debug("updateOrderCount Today OS: "+this.getTodayOutstandingOrders(imsQcAssignUI.getStaffId()).get(0).getTodayOsOrders());
			logger.debug("updateOrderCount Past 7 days: "+ this.getP7dayOutstandingOrders(imsQcAssignUI.getStaffId()).get(0).getPast7daysAssignedOrders());
			logger.debug("updateOrderCount Total Orders: " + this.getTotalOrders(imsQcAssignUI.getStaffId()).get(0).getTotalOrders());
			*/
			
            params.addValue("todayOS",this.getTodayOutstandingOrders(imsQcAssignUI.getStaffId()).get(0).getTodayOsOrders() );
			params.addValue("totalAssignOS", this.getTodayAssignedOrders(imsQcAssignUI.getStaffId()).get(0).getTodayAssignedOrders());
			params.addValue("p7dayOS", this.getP7dayOutstandingOrders(imsQcAssignUI.getStaffId()).get(0).getPast7daysAssignedOrders());
 		    params.addValue("totOS", this.getTotalOrders(imsQcAssignUI.getStaffId()).get(0).getTotalOrders());
			params.addValue("lastUpdBy", imsQcAssignUI.getStaffId());
			params.addValue("staffId", imsQcAssignUI.getAssignee());
				
			try{
					logger.debug("updateOrderCount: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateOrderCount()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
//celia
	public List<String> getTeamCodeList(int channelId) throws DAOException {
		List<String> result = new ArrayList<String>();
	    MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				 " SELECT distinct TEAM_CD  FROM BOMWEB_SALES_ASSIGNMENT WHERE CHANNEL_ID in (:channelId) order by team_cd ");
		
		if(channelId!=13){
			params.addValue("channelId", channelId);
		}else{
			List<String> channelIdList = new ArrayList<String>();
			channelIdList.add("13");
			channelIdList.add("99");
			params.addValue("channelId", channelIdList);
		}

		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("TEAM_CD");
			return tempResult;
		}
	};
	try {
		logger.debug("channelId @ getTeamCodeList: " + channelId);

		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getTeamCodeList():", e);
		throw new DAOException(e.getMessage(), e);
	}

		return result;
	}
	
	public List<String> getQCStaffType() throws DAOException {
		List<String> result = new ArrayList<String>();
		String SQL = " SELECT distinct description  FROM w_code_lkup WHERE grp_id = 'SB_IMS_SALES_TYPE'  AND CODE LIKE 'Q%' ";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("DESCRIPTION");
				return dto;
			}
		};
		try {
			logger.debug("getQCStaffType SQL: " + SQL);
			result = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getQCStaffType():", e);
			throw new DAOException(e.getMessage(), e);
		}	
			return result;
	}
	public String getAmendHistRemarks(String orderId) throws DAOException{
		MapSqlParameterSource params = new MapSqlParameterSource();
		List<String> resultList = new ArrayList<String>();
		StringBuffer sql = new StringBuffer(" SELECT    create_by  "
			+ "           || '  '  "
			+ "           || create_date  "
			+ "           || CHR (10)     "
			+ "           || (SELECT wq_nature_desc  "
			+ "                  FROM q_wq_nature  "
			+ "               WHERE wq_nature_id IN (a.wq_nature_id))  "
			+ "           || CHR (10)  "
			+ "           || remarks  Remark  "
			+ "    FROM q_wq_remarks a  "
			+ "    WHERE wq_id IN (SELECT wq_id  "
			+ "                    FROM q_work_queue  "
			+ "                    WHERE sb_id = :orderId)  "
			+ "    ORDER BY wq_id DESC  "
			);
		params.addValue("orderId", orderId);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("Remark");
				return dto;
			}
		};
		try {
			logger.debug("getAmendHistRemarks @ ImsDSQCDAO:" + sql);
			resultList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getAmendHistRemarks():", e);
			throw new DAOException(e.getMessage(), e);
		}
		String result="";
		for (String el:resultList){
			if (StringUtils.isNotBlank(el)){
				result = result + " " +el;
			}
		}
		return result;
	}
		
	public List<ImsAlertMsgDTO> getImsQCOrderEnquiryListInfo(
			ImsQcComOrderSearchUI enquiry, BomSalesUserDTO bomSalesUserDTO)	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();
		enquiry.print();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuffer sql =  new StringBuffer( 
				" select * from ( "
					+" select a.order_id,  "
					+ "    	a.SALES_TEAM,   "
					+ "		a.CREATE_BY,  "
					+"		decode(b.ID_DOC_TYPE, 'BS', b.company_name, b.last_name || ' ' || b.first_name)  AS cust_name , "
					+"		to_char(a.app_date,'YYYY/MM/DD HH24:MI:SS') app_date,  "
					+"		to_char(a.SRV_REQ_DATE,'YYYY/MM/DD HH24:MI:SS') SRV_REQ_DATE, "
					+"		to_char(a.SIGN_OFF_DATE,'YYYY/MM/DD HH24:MI:SS') SIGN_OFF_DATE, "
					+" 		(select description from w_code_lkup where  grp_id = 'IMS_SB_ORDER_STATUS' and code = a.order_status) ORDER_STATUS, "					
					+" 		(select description from w_code_lkup where  grp_id = 'SB_IMS_QC_STATUS' and code= c.qc_status) qc_status "
					+",		b.id_doc_num, d.login_ID||'@nevigator.com' loginId, DECODE(e.PAY_MTD_TYPE, 'M','Cash','C','Credit Card') payment_method"
					+",		e.THIRD_PARTY_IND "
					+", 	(select description from w_code_lkup where code = upper(g.building_type) and grp_id = decode(:channelId,12,'IMS_PCD_HOUSING_TYPE',13,'SB_IMS_OLNTV_HOU_TYP')) housing_type "//still miss housing type 
					+", 	(SELECT DESCRIPTION FROM W_CODE_LKUP WHERE grp_id = 'SB_IMS_SALES_TYPE'  AND CODE IN (select STAFF_CODE from BOMWEB_SALES_TYPE where staff_id = A.create_by and STAFF_TYPE ='SALES_QC' and location = decode(:channelId,12,'PCD',13,'NOWTV'))) staffType "
					+",		f.assignee "
					+",		c.QC_FINDINGS "
					+",		a.ORDER_TYPE "
					+" from bomweb_order a, bomweb_customer b"
					+", BOMWEB_ORDER_QC_ASSIGN c  "
					+", BOMWEB_ORDER_IMS d, bomweb_payment e, BOMWEB_SALES_QC_ASSIGN f"
					+", Bomweb_addr_inventory g "//still miss housing type
					+" where a.order_id = b.order_id "
					+" and a.order_id = c.orders_id(+) " 
					+" and a.order_id = d.order_id and  a.order_id = e.order_id and  a.order_id = f.orders_id "
					+" and a.order_id = g.order_id(+)  "//still miss housing type
					);
		sql.append(" and a.order_id in (:orderIds) ");
		params.addValue("channelId",bomSalesUserDTO.getChannelId());
		List<String> tempOrderIds =  this.getImsQCOrderIdsForEnquiry(enquiry, bomSalesUserDTO);		
		Gson gson = new Gson();
		System.out.println("num of tempOrderIds:"+tempOrderIds.size());
		System.out.println(gson.toJson(tempOrderIds));
		params.addValue("orderIds", tempOrderIds);
		
		sql.append(" order by a.create_date desc)" +
			" where rownum<100 ") ;

	ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
		public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
			dto.setOrderId((String) rs.getString("order_id"));
			dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
			dto.setCreateBy((String) rs.getString("CREATE_BY"));
			dto.setCustName((String) rs.getString("cust_Name"));
			dto.setAppDate((String) rs.getString("APP_DATE"));
			dto.setServiceReqDate((String) rs.getString("SRV_REQ_DATE"));
			dto.setSignoffDate((String) rs.getString("SIGN_OFF_DATE"));
			dto.setOrderStatus((String) rs.getString("order_Status"));
			dto.setQcStatus((String) rs.getString("QC_Status"));
			dto.setIdDocNum((String) rs.getString("id_doc_num"));
			dto.setLoginId((String) rs.getString("loginId"));
			dto.setPaymentMtd((String) rs.getString("payment_method"));
			dto.setIs3rdParty((String) rs.getString("THIRD_PARTY_IND"));
			dto.setHousingType((String) rs.getString("housing_type"));
			dto.setStaffType((String) rs.getString("staffType"));
			dto.setAssignee((String) rs.getString("assignee"));
			dto.setQcRemarks((String) rs.getString("QC_FINDINGS"));
			dto.setOrderType((String) rs.getString("ORDER_TYPE"));
			return dto;
		}
	};
	try {
		logger.debug("getImsQCOrderEnquiryListInfo @ ImsDSQCDAO:" + sql);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getImsQCOrderEnquiryListInfo():", e);
		throw new DAOException(e.getMessage(), e);
	}

	for (ImsAlertMsgDTO dto:result ){
		if (this.isPendingExist(dto.getOrderId())){
			dto.setAmendment("Y");	
			dto.setAmendmentHistory(this.getAmendHistRemarks(dto.getOrderId()));
		}else
			dto.setAmendment("N");	
		if (!StringUtils.isNotBlank(dto.getStaffType())){
			dto.setStaffType("Clean Staff");
		}	
			
		//if (StringUtils.isNotBlank(dto.getStaffType())){
		//	if (!dto.getStaffType().equalsIgnoreCase("Problem Staff")||!dto.getStaffType().equalsIgnoreCase("Control Staff")){
		//		dto.setStaffType("Clean Staff");
		//	}
		//}
	}
	
		return result;
	}


	private List<String> getImsQCOrderIdsForEnquiry(
			ImsQcComOrderSearchUI enquiry, BomSalesUserDTO bomSalesUserDTO) 	throws DAOException {
				List<String> result = null;
				logger.info("getOrderIdByUserID userId:"+bomSalesUserDTO.getUsername());
				MapSqlParameterSource params = new MapSqlParameterSource();
				
				StringBuffer sql =  new StringBuffer(" select distinct order_id from ") ;
						

				sql.append(	" ( select a.order_id, a.app_date "
						+" from bomweb_order a, bomweb_customer b,BOMWEB_ORDER_IMS c, BOMWEB_SALES_ASSIGNMENT d, bomweb_sales_profile e,BOMWEB_SALES_QC_ASSIGN f,BOMWEB_ORDER_QC_ASSIGN g  " 
						  +"where a.order_id = b.order_id "
						  +"  and a.order_id = c.order_id and d.staff_id = :userName"
						  +"  and d.STAFF_ID = e.staff_id "
						  +"  and a.order_id = f.orders_id AND  f.orders_id= g.orders_id (+) and " 
					      +"(a.shop_cd not in 	(Select code_id from BOMWEB_CODE_LKUP sbof  where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD')  or a.shop_cd  is null) " 
						  +"  and trunc(e.start_date) <= trunc(sysdate) "
						  +"  and (trunc(sysdate) < trunc(e.end_date) or e.end_date is null) "
						  +"  and trunc(d.start_date) <= trunc(sysdate)  "
						  +"  and (trunc(sysdate) < trunc(d.end_date)or d.end_date is null)  " 
						  +"  and f.orders_id = g.ORDERS_ID (+) ");
					
				if (bomSalesUserDTO.getChannelId() == 13){
					sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT where channel_id in (select CODE from  w_code_lkup where grp_id ='SB_QC_CHANNEL_ID_LIST' and description ='NOWTV')) ");
				}else if (bomSalesUserDTO.getChannelId() == 12){
					sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT where channel_id in (select CODE from  w_code_lkup where grp_id ='SB_QC_CHANNEL_ID_LIST' and description ='PCD')) ");	
				}else {
					sql.append(" and a.sales_channel in (select distinct channel_cd from BOMWEB_SALES_ASSIGNMENT where channel_id = :channelId) ") ;
				}
				
				if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
					if(enquiry.getDateType().equalsIgnoreCase("S")){
						if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
							sql.append(" and a.srv_req_date >= TO_DATE(:SStartDate, 'dd/mm/yyyy')  ");
							params.addValue("SStartDate", enquiry.getStartDate());
						}
						if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
							sql.append(" and a.srv_req_date < TO_DATE(:SEndDate, 'dd/mm/yyyy')+1 " );
							params.addValue("SEndDate", enquiry.getEndDate());
						}
					}else if(enquiry.getDateType().equalsIgnoreCase("A")){
						if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
							sql.append(" and a.APP_DATE >= TO_DATE(:AStartDate, 'dd/mm/yyyy')  ");
							params.addValue("AStartDate", enquiry.getStartDate());
						}
						if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
							sql.append(" and a.APP_DATE < TO_DATE(:AEndDate, 'dd/mm/yyyy')+1  ");
							params.addValue("AEndDate", enquiry.getEndDate());
						}
					}
					else if(enquiry.getDateType().equalsIgnoreCase("F")){
						if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
							sql.append(" and a.SIGN_OFF_DATE >= TO_DATE(:FStartDate, 'dd/mm/yyyy')  ");
							params.addValue("FStartDate", enquiry.getStartDate());
						}
						if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
							sql.append(" and a.SIGN_OFF_DATE < TO_DATE(:FEndDate, 'dd/mm/yyyy')+1  ");
							params.addValue("FEndDate", enquiry.getEndDate());
						}
					}
				}
				if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
					sql.append(" and a.order_id = :OrderID ");
					params.addValue("OrderID", enquiry.getOrderId());
				}
				if(StringUtils.isNotBlank(enquiry.getDocType())&&StringUtils.isNotBlank(  enquiry.getDocNum() )){
					sql.append(" and b.id_doc_type = :DocType ");
					params.addValue("DocType", enquiry.getDocType());
					sql.append(" and b.id_doc_num = :DocNum ");				
					params.addValue("DocNum", enquiry.getDocNum());
				}
				
//				if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
//					params.addValue("SerNum", enquiry.getSerNum());
//				}
				if(enquiry.getFirstName() !=null&&!enquiry.getFirstName().equals("")){
					sql.append(" and b.first_name = :firstName ");
					params.addValue("firstName", enquiry.getFirstName());
				}
				if(enquiry.getLastName() !=null&&!enquiry.getLastName().equals("")){
					sql.append(" and b.last_name = :lastName ");
					params.addValue("lastName", enquiry.getLastName());
				}
				if(enquiry.getLoginId()!=null&&!enquiry.getLoginId().equals("")){
					sql.append(" and UPPER(c.login_id) = UPPER(:LoginId )");
					params.addValue("LoginId", enquiry.getLoginId());
				}
				if(enquiry.getOrderStatus() !=null&&!enquiry.getOrderStatus().equals("")){
					sql.append(" and a.ORDER_STATUS in ( SELECT code" +
							"  FROM w_code_lkup" +
							" WHERE grp_id = 'IMS_SB_ORDER_STATUS'" +
							" and description = :description ) " );
					params.addValue("description", enquiry.getOrderStatus());
				}
				if(enquiry.getQcStatus() !=null&&!enquiry.getQcStatus().equals("")){
					sql.append(" and g.QC_STATUS in ( SELECT code" +
							"  FROM w_code_lkup" +
							" WHERE grp_id = 'SB_IMS_QC_STATUS'" +
							" and description = :qcStatusDesc ) " );
					params.addValue("qcStatusDesc", enquiry.getQcStatus());
				}
				if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
					sql.append(" and UPPER(a.create_by) in UPPER(:CreateStaff)" + " ");
					params.addValue("CreateStaff", enquiry.getCreateStaff());
				}//create by?
				if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
					sql.append(" and UPPER(a.SALES_TEAM) in UPPER(:TeamSearch) ");
					params.addValue("TeamSearch", enquiry.getTeamSearch());
				}//team code?
				if(enquiry.getSalesNum() !=null&&!enquiry.getSalesNum().equals("")){
					sql.append(" and UPPER(a.SALES_CD) in UPPER(:salesNum) ");
					params.addValue("salesNum", enquiry.getSalesNum());
				}//sales code?
				if(enquiry.getPaymentMethod() !=null&&!enquiry.getPaymentMethod().equals("")){
					sql.append(" and  A.order_id in (select order_id from bomweb_payment WHERE pay_mtd_type in :paymentMethod) ");
					params.addValue("paymentMethod", enquiry.getPaymentMethod());
				}
				if(enquiry.getIs3rdParty() !=null&&!enquiry.getIs3rdParty().equals("")){
					sql.append(" and  A.order_id in (select order_id from bomweb_payment WHERE THIRD_PARTY_IND in :is3rdParty) ");
					params.addValue("is3rdParty", enquiry.getIs3rdParty());
				}
				if(enquiry.getHousingType() !=null&&!enquiry.getHousingType().equals("")){
				sql.append(" and a.order_id in (select order_id from bomweb_addr_inventory where building_type in (select code from w_code_lkup where description =:housingType and grp_id = decode(:channelId,12,'IMS_PCD_HOUSING_TYPE',13,'SB_IMS_OLNTV_HOU_TYP'))) ");
					params.addValue("housingType", enquiry.getHousingType());
				}
				if(enquiry.getStaffType() !=null&&!enquiry.getStaffType().equals("")){
					System.out.println("staff type:       "+ enquiry.getStaffType());
					if (enquiry.getStaffType().equalsIgnoreCase("Clean Staff"))
					sql.append(" and A.create_by IN (SELECT STAFF_ID FROM BOMWEB_SALES_TYPE WHERE staff_type NOT LIKE 'Q%' ) ");
					else 
					sql.append(" and A.create_by IN (select staff_id from BOMWEB_SALES_TYPE where staff_code in (select code from w_code_lkup where grp_id = 'SB_IMS_SALES_TYPE' AND DESCRIPTION = :staffType)) ");
					params.addValue("staffType", enquiry.getStaffType());
				}if(enquiry.getAssignee()!=null&&!enquiry.getAssignee().equals("")){
					sql.append(" and UPPER(f.Assignee) = :assignee ");
					params.addValue("assignee", enquiry.getAssignee());
				}
				
				sql.append("	order by app_date desc) where rownum<(select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'QC_ENQ_MAX')	" );
				
				params.addValue("userName", bomSalesUserDTO.getUsername());
				params.addValue("channelId",bomSalesUserDTO.getChannelId());
				
				ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						String tempResult = "";
						tempResult=(String) rs.getString("order_id");
						return tempResult;
					}
				};
				try {
					logger.debug("key search: " + params.getValues());
					logger.debug("userId @ getOrderIdByUserID: " + bomSalesUserDTO.getUsername());
					logger.debug("getImsQCOrderIdsForEnquiry @ ImsDSQCDAO: " + sql);
					result = simpleJdbcTemplate.query(sql.toString(), mapper, params);

				} catch (EmptyResultDataAccessException erdae) {
					logger.debug("EmptyResultDataAccessException");
					result = new ArrayList<String>();
					result.add("''");
				} catch (Exception e) {
					logger.debug("Exception caught in getOrderIdByUserID():", e);
					throw new DAOException(e.getMessage(), e);
				}	
				if(result.size()==0){
					result.add("''");
				}else if(enquiry.getAmendment()!=null&&!enquiry.getAmendment().equals("")){
					List<String> amendmentResult = new ArrayList<String>();
					if (enquiry.getAmendment().equalsIgnoreCase("Y")){
						for (String orderId:result){
							if (this.isPendingExist(orderId)){
								amendmentResult.add(orderId);
							}								
						}
					}else if (enquiry.getAmendment().equalsIgnoreCase("N")){
						for (String orderId:result){
							if (!this.isPendingExist(orderId)){
								amendmentResult.add(orderId);
							}								
						}
					}
					if (amendmentResult.size() == 0){
						amendmentResult.add("''");
					}
					return amendmentResult;
					
				}
				
					return result;
				}


	//kinman QC front Enquiry
	public List<ImsAlertMsgDTO> getQcFrontOrderEnquiryInfo (DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();
		
		enquiry.print();
		
		MapSqlParameterSource params = this.getQCFrontOrderIdsForEnquiryGetParms(enquiry, userDto);
		
		StringBuffer sql =  new StringBuffer( 
				" select * from (" +
				" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , " +
				" decode(b.ID_DOC_TYPE, 'BS', b.company_name, b.last_name || ' ' || b.first_name)  AS cust_name ," +
				" b.SERVICE_NUM, c.LOGIN_ID, " + 
				" decode(substr(a.order_id, 6,1),'T',to_char(a.APP_DATE,'YYYY/MM/DD'), to_char(a.APP_DATE,'YYYY/MM/DD HH24:MI:SS')) APP_DATE, " +
				" a.order_Status, '' err, '' recall, " +
				" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY, a.reason_cd,  c.IMS_ORDER_TYPE, a.ORDER_TYPE  " +
				" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c " +
				" where a.order_id = b.order_id" +
				" and a.order_id = c.order_id" );
		sql.append(" and a.order_id in ("+this.getQCFrontOrderIdsForEnquiryGetSql(enquiry, userDto)+") ");
			
//		sql.append(" and a.order_id in (:orderIds) ");
//		
//		params.addValue("channelId", userDto.getChannelId());
//		
//		List<String> tempOrderIds =  this.getQCFrontOrderIdsForEnquiry(enquiry, userDto);
//		Gson gson = new Gson();
//		logger.debug("num of tempOrderIds:"+tempOrderIds.size());
//		logger.debug(gson.toJson(tempOrderIds));
//		params.addValue("orderIds", tempOrderIds);
		
		sql.append(" order by a.create_date desc)" +
			" where rownum<(select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'QC_EN_Q_MAX') ") ;

	ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
		public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
			
			dto.setAppDate((String) rs.getString("APP_DATE"));
			dto.setAlertStatus((String) rs.getString("alert_Status"));
			dto.setCustName((String) rs.getString("cust_Name"));
			dto.setError((String) rs.getString("err"));
			dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
			dto.setLoginId((String) rs.getString("LOGIN_ID"));
			dto.setOcid((String) rs.getString("OCID"));
			dto.setOrderId((String) rs.getString("order_id"));
			dto.setOrderStatus((String) rs.getString("order_Status"));
			dto.setServiceNum((String) rs.getString("SERVICE_NUM"));
			dto.setSalesCd((String) rs.getString("SALES_CD"));
			dto.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
			dto.setCreateBy((String) rs.getString("CREATE_BY"));
			dto.setReasonCD((String) rs.getString("reason_cd"));
			dto.setImsOrderType((String) rs.getString("IMS_ORDER_TYPE"));
			dto.setOrderType((String) rs.getString("ORDER_TYPE"));

			return dto;
		}
	};

	try {
		logger.debug("getImsDsQcOrderEnquiryInfo @ ImsDSQCDAO:" + sql);
//		result = simpleJdbcTemplate.query(SQL, mapper);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getImsDsQcOrderEnquiryInfo():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}
	
	
	
	
	public List<String> getQCFrontOrderIdsForEnquiry (DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<String> result = null;
		

		logger.info("getQCFrontOrderIdsForEnquiry@ userId:"+userDto.getUsername());
		logger.info("getQCFrontOrderIdsForEnquiry@ user Category:"+userDto.getCategory());
		
		//List<String> roleCodeList = this.getQCFrontRoleCodeByUserIDLkupCode(userDto.getUsername(), ImsConstants.IMS_QC_FRONT_ENQUIRY_READ, ImsConstants.IMS_QC_FRONT_ENQUIRY_FUNCTION) ;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer(" select distinct order_id from (") ;
				
		SQL.append(this.getQCFrontOrderEnquirySQL(enquiry));
		
		if("VQA".equals(userDto.getChannelCd())){
			List<String> ids = new ArrayList<String>();
			ids.add("13");
			ids.add("99");			
			params.addValue("channelid", ids);
		}else{
			params.addValue("channelid", Integer.valueOf(userDto.getChannelId()));
		}
		
		SQL.append("	order by app_date desc) where rownum<(select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'QC_ENQ_MAX')	" );
		
		params.addValue("LOB", "IMS");
	
	if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
		if(enquiry.getDateType().equalsIgnoreCase("S")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("SStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("SEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("A")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("AStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("AEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("F")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("FStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("FEndDate", enquiry.getEndDate());
			}
		}
	}
	
	if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
		params.addValue("OrderID", enquiry.getOrderId());
	}
	if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
		params.addValue("SerNum", enquiry.getSerNum());
	}
	if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
		params.addValue("salesNum", enquiry.getSalesNum());
	}
	if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
		params.addValue("CreateStaff", enquiry.getCreateStaff());
	}
	if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
		params.addValue("TeamSearch", enquiry.getTeamSearch());
	}
	
	
	params.addValue("user", userDto.getUsername());

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("order_id");
			return tempResult;
		}
	};
	try {
		logger.debug("key search: " + params.getValues());
		logger.debug("userDto.getUsername(): " + userDto.getUsername());
		logger.debug("SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = new ArrayList<String>();
		result.add("''");
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderIdByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	if(result.size()==0){
		result.add("''");
	}
		return result;
	}
	
	
	

	public MapSqlParameterSource getQCFrontOrderIdsForEnquiryGetParms (DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<String> result = null;
		

		logger.info("getQCFrontOrderIdsForEnquiry@ userId:"+userDto.getUsername());
		logger.info("getQCFrontOrderIdsForEnquiry@ user Category:"+userDto.getCategory());
		
		//List<String> roleCodeList = this.getQCFrontRoleCodeByUserIDLkupCode(userDto.getUsername(), ImsConstants.IMS_QC_FRONT_ENQUIRY_READ, ImsConstants.IMS_QC_FRONT_ENQUIRY_FUNCTION) ;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer(" select distinct order_id from (") ;
				
		SQL.append(this.getQCFrontOrderEnquirySQL(enquiry));
		
		if("VQA".equals(userDto.getChannelCd())){
			List<String> ids = new ArrayList<String>();
			ids.add("13");
			ids.add("99");			
			params.addValue("channelid", ids);
		}else{
			params.addValue("channelid", Integer.valueOf(userDto.getChannelId()));
		}
		
		SQL.append("	order by app_date desc) where rownum<(select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'QC_ENQ_MAX')	" );
		
		params.addValue("LOB", "IMS");
	
	if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
		if(enquiry.getDateType().equalsIgnoreCase("S")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("SStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("SEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("A")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("AStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("AEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("F")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("FStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("FEndDate", enquiry.getEndDate());
			}
		}
	}
	
	if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
		params.addValue("OrderID", enquiry.getOrderId());
	}
	if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
		params.addValue("SerNum", enquiry.getSerNum());
	}
	if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
		params.addValue("salesNum", enquiry.getSalesNum());
	}
	if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
		params.addValue("CreateStaff", enquiry.getCreateStaff());
	}
	if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
		params.addValue("TeamSearch", enquiry.getTeamSearch());
	}
	
	
	params.addValue("user", userDto.getUsername());

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("order_id");
			return tempResult;
		}
	};
	try {
		logger.debug("key search: " + params.getValues());
		logger.debug("userDto.getUsername(): " + userDto.getUsername());
		logger.debug("SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = new ArrayList<String>();
		result.add("''");
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderIdByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	if(result.size()==0){
		result.add("''");
	}
		return params;
	}
	
	
	

	public String getQCFrontOrderIdsForEnquiryGetSql (DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<String> result = null;
		

		logger.info("getQCFrontOrderIdsForEnquiry@ userId:"+userDto.getUsername());
		logger.info("getQCFrontOrderIdsForEnquiry@ user Category:"+userDto.getCategory());
		
		//List<String> roleCodeList = this.getQCFrontRoleCodeByUserIDLkupCode(userDto.getUsername(), ImsConstants.IMS_QC_FRONT_ENQUIRY_READ, ImsConstants.IMS_QC_FRONT_ENQUIRY_FUNCTION) ;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer(" select distinct order_id from (") ;
				
		SQL.append(this.getQCFrontOrderEnquirySQL(enquiry));
		
		if("VQA".equals(userDto.getChannelCd())){
			List<String> ids = new ArrayList<String>();
			ids.add("13");
			ids.add("99");			
			params.addValue("channelid", ids);
		}else{
			params.addValue("channelid", Integer.valueOf(userDto.getChannelId()));
		}
		
		SQL.append("	order by app_date desc) where rownum<(select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'QC_EN_Q_MAX')	" );
		
		params.addValue("LOB", "IMS");
	
	if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
		if(enquiry.getDateType().equalsIgnoreCase("S")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("SStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("SEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("A")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("AStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("AEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("F")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("FStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("FEndDate", enquiry.getEndDate());
			}
		}
	}
	
	if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
		params.addValue("OrderID", enquiry.getOrderId());
	}
	if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
		params.addValue("SerNum", enquiry.getSerNum());
	}
	if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
		params.addValue("salesNum", enquiry.getSalesNum());
	}
	if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
		params.addValue("CreateStaff", enquiry.getCreateStaff());
	}
	if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
		params.addValue("TeamSearch", enquiry.getTeamSearch());
	}
	
	
	params.addValue("user", userDto.getUsername());

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("order_id");
			return tempResult;
		}
	};
	try {
		logger.debug("key search: " + params.getValues());
		logger.debug("userDto.getUsername(): " + userDto.getUsername());
		logger.debug("SQL: " + SQL);
//		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = new ArrayList<String>();
		result.add("''");
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderIdByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
//	if(result.size()==0){
//		result.add("''");
//	}
		return SQL.toString();
	}
	
	public String getQCFrontOrderEnquirySQL (DsQCImsOrderEnquiryUI enquiry)
	throws DAOException {		
		
		StringBuffer sql =  new StringBuffer( 
			" select distinct a.order_id, a.app_date " +
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c, BOMWEB_SALES_ASSIGNMENT d, bomweb_sales_profile e " +
			" where a.order_id = b.order_id" +
			" and a.order_id = c.order_id" +
			" and d.channel_id in (:channelid) " +
			" and d.STAFF_ID = e.staff_id " +
			" and (c.ims_order_type ='DS' OR c.ims_order_type ='PCD_T'  OR c.ims_order_type ='P_R_T' OR c.ims_order_type IS NULL)" +
			" and a.create_by = e.STAFF_ID" +
            " and (a.shop_cd not in 	(Select code_id from BOMWEB_CODE_LKUP sbof 	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD') " +
			" or a.shop_cd  is null) " +
			//" and a.ORDER_TYPE in ('NTV-A','NTV-P') " +
			" and trunc(e.start_date) <= trunc(A.APP_DATE) " +
			" and (trunc(A.APP_DATE) <= trunc(e.end_date) or e.end_date is null) " +
			" and trunc(d.start_date) <= trunc(A.APP_DATE) " +
			" and (trunc(A.APP_DATE) <= trunc(d.end_date) or d.end_date is null)" ); 

/*
		if(role.equalsIgnoreCase("MANAGER")){
			sql.append("and d.TEAM_CD in (select distinct TEAM_CD " +
					   "from BOMWEB_SALES_ASSIGNMENT " +
					   "where staff_id = :user " +
					   "and trunc(start_date) <= trunc(sysdate) " +
					   "and (trunc(sysdate) < trunc(end_date) or end_date is null))");
		}else{
			if(!role.equalsIgnoreCase("SALES MANAGER")){
			sql.append("and f.ASSIGNEE = :user");
			}
		}
*/		
		sql.append(" and a.LOB in (:LOB)");
		
		if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
			if(enquiry.getDateType().equalsIgnoreCase("S")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.srv_req_date >= TO_DATE(:SStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.srv_req_date < TO_DATE(:SEndDate, 'dd/mm/yyyy')+1 " );
				}
			}else if(enquiry.getDateType().equalsIgnoreCase("A")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.APP_DATE >= TO_DATE(:AStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.APP_DATE < TO_DATE(:AEndDate, 'dd/mm/yyyy')+1  ");
				}
			}else if(enquiry.getDateType().equalsIgnoreCase("F")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.SIGN_OFF_DATE >= TO_DATE(:FStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.SIGN_OFF_DATE < TO_DATE(:FEndDate, 'dd/mm/yyyy')+1  ");
				}
			}			
		}
		if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
			sql.append(" and a.order_id = :OrderID ");
		}		
		if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
			sql.append(" and b.service_num = :SerNum ");
		}		
		if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
			sql.append(" and UPPER(a.SALES_CD) in UPPER(:salesNum) ");
		}
		if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
			sql.append(" and UPPER(a.create_by) in UPPER(:CreateStaff) ");
		}
		if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
			sql.append(" and UPPER(a.SALES_TEAM) in UPPER(:TeamSearch) ");
		}
		
		logger.info(" QC FRONT ENQUIRY getQCFrontOrderEnquirySQL:"+sql.toString());
		return sql.toString();
	}
	
	
		//end kinman QC front Enquiry
	
	
	public List<String> getSameCustOrder(String orderId)throws DAOException	{
		
		String SQL = "	select bo.order_id " +
					 "	from bomweb_order bo, bomweb_customer bc,bomweb_order_ims boi," +
					 "  (select a.ORDER_ID, LAST_NAME, FIRST_NAME ,ID_DOC_TYPE,ID_DOC_NUM,b.sign_off_date" +
					 "  from bomweb_customer a,bomweb_order b 	" +
					 "  where a.order_id = :orderid				" +
					 "  and a.order_id = b.order_id)tmp 		" +
					 "  where bc.LAST_NAME = tmp.LAST_NAME 		" +
					 "  and bc.FIRST_NAME = tmp.FIRST_NAME 		" +
					 "  and bc.ID_DOC_TYPE = tmp.ID_DOC_TYPE	" +
					 "  and bc.ID_DOC_NUM = tmp.ID_DOC_NUM	" +
					 "  and bc.ORDER_ID != :orderid 	" +
					 "  and bo.ORDER_ID = bc.ORDER_ID	" +
					 "  and bo.ORDER_ID = boi.ORDER_ID	" +
					 "  and (boi.ims_order_type ='DS' OR boi.ims_order_type IS NULL)	" +
					 "	and trunc(tmp.sign_off_date) >= trunc(bo.sign_off_DATE - 7) " +
					 "	and trunc(tmp.sign_off_date ) <= trunc(bo.sign_off_DATE + 7) " +
					 "	order by bo.CREATE_DATE	";
		
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String sameCustOrder = new String();
				sameCustOrder = (String) rs.getString("order_id");
				return sameCustOrder;
			}
		};
		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderid", orderId);
		
		List<String> sameCustOrder = new ArrayList<String>();
		try{
			logger.debug("getSameCustOrder: " + SQL);
			sameCustOrder = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			sameCustOrder = null;
		} catch (Exception e) {
			logger.debug("Exception caught in checkSalesTypeCode():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return sameCustOrder;		
		
	}
	
	public int changeAwaitQCOrderStatus(String orderId) throws DAOException {
		// TODO Auto-generated method stub
		logger.info("changeAwaitQCOrderStatus called:");
		int returnCode = 0;
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = 	" UPDATE BOMWEB_ORDER " + 
							" SET ORDER_STATUS = '" +  
							ImsConstants.IMS_ORDER_STATUS_AWAIT_SIGNOFF +
							"' WHERE ORDER_ID = :orderId" +
							" AND ORDER_STATUS = '" + 
							ImsConstants.IMS_ORDER_STATUS_AWAIT_QC +"'";
			
            params.addValue("orderId", orderId);
			
            logger.debug("changeAwaitQCOrderStatus for Order ID: " + orderId);
			try{
					logger.debug("changeAwaitQCOrderStatus: " + SQL);
					returnCode = simpleJdbcTemplate.update(SQL,params);
					logger.debug("rowcount changeAwaitQCOrderStatus SQL:"+ returnCode);
			}catch (Exception e) {
				logger.error("Exception caught in changeAwaitQCOrderStatus()", e);
				throw new DAOException(e.getMessage(), e);
			}	
			return returnCode;
	}
	
}

 
