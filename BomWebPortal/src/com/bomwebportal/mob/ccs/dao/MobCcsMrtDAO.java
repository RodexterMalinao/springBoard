package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
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
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtChinaDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtMnpDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsMrtDAO extends BaseDAO {

    protected final Log logger = LogFactory.getLog(getClass());
    MobCcsMrtService mobCcsMrtService;

    // modify by Eliot @20120302 add byPass
    private static String getMobCcsMrtDTOSQL = "SELECT \n" + "     ORDER_ID,\n"
	    + "     MNP_IND,\n" + "     GOLDEN_IND,\n" + "     CHINA_IND,\n"
	    + "     MSISDN,\n" + "     MSISDNLVL,\n" + "     SEQ_ID,\n"
	    + "     CITY_CD,\n" + "     SRV_REQ_DATE,\n"
	    + "     CUT_OVER_DATE,\n" + "     CUT_OVER_TIME,\n" + "     RNO,\n"
	    + "     DNO,\n" + "     ACT_DNO,\n" + "   CUST_NAME,\n" + "     DOC_NO,\n"
	    + "     MNP_TICKET_NUM,\n" + "     CREATE_BY,\n"
	    + "     CREATE_DATE,\n" + "     RESERVE_ID,\n" + "     OPER_ID,\n"
	    + "     NUM_TYPE,\n"
	    + "     LAST_UPD_BY,\n" + "     LAST_UPD_DATE, \n"
	    + "BYPASS_IND,\n"+ "OPSS_IND, \n"+ "STARTER_PACK \n"
	    + "from BOMWEB_MRT Where ORDER_ID=? order by SEQ_ID\n";

    public ArrayList<MobCcsMrtBaseDTO> getMobCcsMrtDTO(String orderId)
	    throws DAOException {
	logger.info(" getMobCcsMrtDTO is called");
	ArrayList<MobCcsMrtBaseDTO> itemList = new ArrayList<MobCcsMrtBaseDTO>();
	
	ParameterizedRowMapper<MobCcsMrtBaseDTO> mapper = new ParameterizedRowMapper<MobCcsMrtBaseDTO>() {
	    public MobCcsMrtBaseDTO mapRow(ResultSet rs, int rowNum)
		    throws SQLException {
		
	    MobCcsMrtBaseDTO dto = null;
			    
	    if ("Y".equalsIgnoreCase(rs.getString("CHINA_IND")) && 2 == rs.getInt("SEQ_ID")) {
	    	dto = new MobCcsMrtChinaDTO();
	    } else if ("A".equalsIgnoreCase(rs.getString("MNP_IND")) && 2 == rs.getInt("SEQ_ID")) {
	    	dto = new MobCcsMrtMnpDTO();
	    } else if ("Y".equalsIgnoreCase(rs.getString("MNP_IND"))) {
	    	dto = new MobCcsMrtMnpDTO();
	    } else {
	    	dto = new MobCcsMrtDTO();
	    }
		
		dto.setOrderId(rs.getString("ORDER_ID"));
		dto.setMnpInd(rs.getString("MNP_IND"));
		dto.setGoldenInd(rs.getString("GOLDEN_IND"));
		dto.setChinaInd(rs.getString("CHINA_IND"));
		dto.setMsisdn(rs.getString("MSISDN"));
		dto.setMsisdnLvl(rs.getString("MSISDNLVL"));
		dto.setRno(rs.getString("RNO"));
		dto.setDno(rs.getString("DNO"));
		dto.setActualDno(rs.getString("ACT_DNO"));
		dto.setCreatedBy(rs.getString("CREATE_BY"));
		dto.setCreatedDate(rs.getDate("CREATE_DATE"));
		dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
		dto.setSeqId(rs.getInt("SEQ_ID"));
		dto.setReserveId(rs.getString("RESERVE_ID"));
		dto.setOperId(rs.getString("OPER_ID"));
		dto.setNumType(rs.getString("NUM_TYPE")); //DENNIS MIP3
		dto.setByPassValidation("Y".equalsIgnoreCase(rs.getString("BYPASS_IND")) ? true
			: false);
		if (dto instanceof MobCcsMrtChinaDTO) {
			((MobCcsMrtChinaDTO) dto).setCityCd(rs.getString("CITY_CD"));
			((MobCcsMrtChinaDTO) dto).setServiceReqDate(rs.getDate("SRV_REQ_DATE"));
		} else if (dto instanceof MobCcsMrtMnpDTO) {
			((MobCcsMrtMnpDTO) dto).setCutOverDate(rs.getDate("CUT_OVER_DATE"));
			((MobCcsMrtMnpDTO) dto).setCutOverTime(rs.getString("CUT_OVER_TIME"));
			((MobCcsMrtMnpDTO) dto).setCustName(rs.getString("CUST_NAME"));
			((MobCcsMrtMnpDTO) dto).setDocNum(rs.getString("DOC_NO"));
			((MobCcsMrtMnpDTO) dto).setMnpTicketNum(rs.getString("MNP_TICKET_NUM"));
			((MobCcsMrtMnpDTO) dto).setOpssInd(rs.getString("OPSS_IND"));
			((MobCcsMrtMnpDTO) dto).setStarterPack(rs.getString("STARTER_PACK"));
			
		} else if (dto instanceof MobCcsMrtDTO) {
			((MobCcsMrtDTO) dto).setServiceReqDate(rs.getDate("SRV_REQ_DATE"));
			//((MobCcsMrtDTO) dto).setNumType(rs.getString("NUM_TYPE")); //DENNIS MIP3
		}
		
		
		return dto;
	    }
	};

	try {
	    logger.info("getMobCcsMrtDTO() @ MobCcsMrtDTO: "
		    + getMobCcsMrtDTOSQL);
	    itemList = (ArrayList<MobCcsMrtBaseDTO>) simpleJdbcTemplate.query(
		    getMobCcsMrtDTOSQL, mapper, orderId);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getMobCcsMrtDTO()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getMobCcsMrtDTO():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    // modify by Eliot @20120302 add byPass
    // === Start of INSERT function ===
    private static final String insertMobCcsMrtSQL = " INSERT INTO BOMWEB_MRT ( \n"
	    + "    ORDER_ID,  \n"
	    + "    SEQ_ID,  \n"
	    + "    MNP_IND,  \n"
	    + "    GOLDEN_IND,  \n"
	    + "    CHINA_IND,  \n"
	    + "    MSISDN,  \n"
	    + "    MSISDNLVL,  \n"
	    + "    CITY_CD,  \n"
	    + "    SRV_REQ_DATE,  \n"
	    + "    CUT_OVER_DATE,  \n"
	    + "    CUT_OVER_TIME,  \n"
	    + "    RNO,  \n"
	    + "    DNO,  \n"
	    + "    ACT_DNO,  \n"
	    + "    CUST_NAME,  \n"
	    + "    DOC_NO,  \n"
	    + "    MNP_TICKET_NUM,  \n"
	    + "    RESERVE_ID,  \n"
	    + "    OPER_ID,  \n"
	    + "    NUM_TYPE,  \n"
	    + "    CREATE_BY,  \n"
	    + "    LAST_UPD_BY,  \n"
	    + "    BYPASS_IND, \n"
	    + "    OPSS_IND, \n"
	    + "    STARTER_PACK, \n"
	    + "    LAST_UPD_DATE,  \n"
	    + "    CREATE_DATE  \n"
	    + ") VALUES ( \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n"
	    + "? , \n" + "? , \n" + "? , \n" + "sysdate , \n" + "sysdate  \n" + " ) \n";

    public int insertMobCcsMrt(MobCcsMrtBaseDTO dto) throws DAOException {
	logger.info("insertMobCcsMrt is called");

	try {
	    logger.info("insertMobCcsMrt() @ mobCcsMrtDAO: "
		    + insertMobCcsMrtSQL);
	    return simpleJdbcTemplate.update(
		    insertMobCcsMrtSQL,
		    // start sql mapping
		    dto.getOrderId(), dto.getSeqId(), dto.getMnpInd(),
		    dto.getGoldenInd(), dto.getChinaInd(), dto.getMsisdn(),
		    dto.getMsisdnLvl(), 
		    (dto instanceof MobCcsMrtChinaDTO ? ((MobCcsMrtChinaDTO) dto).getCityCd(): ""),
		    (dto instanceof MobCcsMrtChinaDTO ? ((MobCcsMrtChinaDTO) dto).getServiceReqDate(): dto instanceof MobCcsMrtDTO ? ((MobCcsMrtDTO) dto).getServiceReqDate() : "") , 
		    (dto instanceof MobCcsMrtMnpDTO ? ((MobCcsMrtMnpDTO) dto).getCutOverDate(): ""),
		    (dto instanceof MobCcsMrtMnpDTO ? ((MobCcsMrtMnpDTO) dto).getCutOverTime(): ""),
		    dto.getRno(), dto.getDno(), dto.getActualDno(),
		    (dto instanceof MobCcsMrtMnpDTO ? ((MobCcsMrtMnpDTO) dto).getCustName(): ""),
		    (dto instanceof MobCcsMrtMnpDTO ? ((MobCcsMrtMnpDTO) dto).getDocNum(): ""),
		    (dto instanceof MobCcsMrtMnpDTO ? ((MobCcsMrtMnpDTO) dto).getMnpTicketNum(): ""),
		    dto.getReserveId(), dto.getOperId(), dto.getNumType(), dto.getCreatedBy(), dto.getLastUpdBy(),
		    dto.isByPassValidation() == true ? "Y" : "N",
		    (dto instanceof MobCcsMrtMnpDTO ? ((MobCcsMrtMnpDTO) dto).getOpssInd() : "N") , 
		    (dto instanceof MobCcsMrtMnpDTO ? ((MobCcsMrtMnpDTO) dto).getStarterPack() : "N") 

	    // end sql mapping
		    );
	} catch (Exception e) {
	    logger.error("Exception caught in insertMobCcsMrt()", e);
	    throw new DAOException(e.getMessage(), e);
	}
    }

    private static final String getPCCW3GMrtNumByStaffIdSQL = /*"SELECT DISTINCT msisdn, msisdnlvl, reserve_type "
	    + "FROM bomweb_mrt_status "
	    + "WHERE staff_id = ? "
	    + "AND status = 18 " + "AND srv_num_type='PCCW3G' order by msisdn";*/
    		
    		"SELECT DISTINCT ms.msisdn, " +
    		"  ms.msisdnlvl, " +
    		"  ms.reserve_type , " +
    		"  mi.num_type " +
    		"FROM bomweb_mrt_status ms , " +
    		"  bomweb_mrt_inventory mi " +
    		"WHERE ms.msisdn     = mi.msisdn " +
    		"AND ms.status       = mi.msisdn_status " +
    		"AND ms.srv_num_type = mi.srv_num_type " +
    		"AND ms.staff_id     = ? " +
    		"AND ms.status       = 18 " +
    		"AND ms.srv_num_type ='PCCW3G' " +
    		"ORDER BY msisdn " ;  //DENNIS MIP3

    public List<String[]> getPCCW3GMrtNumByStaffId(String staff_id) throws DAOException {
	logger.info(" getPCCW3GMrtNumByStaffId is called");
	List<String[]> itemList = new ArrayList<String[]>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
	    public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		String[] temp = new String[4];
		temp[0] = rs.getString("msisdn");
		temp[1]= rs.getString("msisdnlvl");
		temp[2]= rs.getString("reserve_type");
		temp[3]= rs.getString("num_type");
		return temp;
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getPCCW3GMrtNumByStaffId() @ ArrayList<String[]>: "
		    + getPCCW3GMrtNumByStaffIdSQL);
	    itemList = simpleJdbcTemplate.query(getPCCW3GMrtNumByStaffIdSQL, mapper,
		    staff_id);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getPCCW3GMrtNumByStaffId()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getPCCW3GMrtNumByStaffId():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static final String getPCCW3GMrtNumByChannelCdSQL = "SELECT DISTINCT srv_num_type, "
	    + "  msisdn, "
	    + "  msisdnlvl, "
	    + "  num_type "
	    + "FROM bomweb_mrt_inventory "
	    + "WHERE channel_cd= ? "
	    + "AND msisdn_status=2 "
	    + "AND srv_num_type ='PCCW3G' order by msisdn";

    public List<String[]> getPCCW3GMrtNumByChannelCd(String channel_cd)
	    throws DAOException {
	logger.info(" getPCCW3GMrtNumByChannelCd is called");
	List<String[]> itemList = new ArrayList<String[]>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
	    public String[] mapRow(ResultSet rs, int rowNum)
		    throws SQLException {
		String temp[] = new String[4];
		temp[0] = rs.getString("srv_num_type");
		temp[1] = rs.getString("msisdn");
		temp[2] = rs.getString("msisdnlvl");
		temp[3] = rs.getString("num_type");
		return temp;
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getPCCW3GMrtNumByChannelCd() @ ArrayList<String[]>: "
		    + getPCCW3GMrtNumByChannelCdSQL);
	    itemList = simpleJdbcTemplate.query(getPCCW3GMrtNumByChannelCdSQL, mapper,
		    channel_cd);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getPCCW3GMrtNumByChannelCd()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getPCCW3GMrtNumByChannelCd():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }


    private static final String getUnicomMrtNumAndLvlSQL = "SELECT msisdn, msisdnLvl "
	    + "FROM bomweb_mrt_inventory " + "WHERE channel_cd =? "
	    + "AND city_cd      =? " + "AND msisdn_status=2 "
	    + "AND srv_num_type ='UNICOM1C2N' order by msisdn";

    public List<String[]> getUnicomMrtNumAndLvl(String channel_cd, String cityCd)
	    throws DAOException {
	logger.info(" getUnicomMrtNumAndLvl is called");
	List<String[]> itemList = new ArrayList<String[]>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
	    public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		String[] temp = new String[2];
		temp[0] = rs.getString("msisdn");
		temp[1] = rs.getString("msisdnlvl");
		return temp;
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getUnicomMrtNumAndLvl() @ ArrayList<String[]>: "
		    + getUnicomMrtNumAndLvlSQL);
	    itemList = simpleJdbcTemplate.query(getUnicomMrtNumAndLvlSQL, mapper,
		    channel_cd, cityCd);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getUnicomMrtNumAndLvl()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getUnicomMrtNumAndLvl():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }
    
	private static final String getSpecialMsisdnLvlSQL = "SELECT DISTINCT msisdnlvl "
			+ "           FROM bomweb_mrt_inventory i "
			+ "          WHERE i.msisdn = ? "
			+ "            AND i.msisdn_status = ? "
			+ "            AND i.srv_num_type = 'PCCW3G' "
			+ "            AND i.APPROVAL_SERIAL = ? ";

	public String getSpecialMsisdnLvl(String msisdn, String msisdnStatus,
			String approvalSerial) throws DAOException {
		logger.info(" getSpecialMsisdnLvl is called");

		String lvl = "";

		try {
			logger.info("getSpecialMsisdnLvl() @ ArrayList<String>: "
					+ getSpecialMsisdnLvlSQL);

			lvl = (String) simpleJdbcTemplate.queryForObject(
					getSpecialMsisdnLvlSQL, String.class, msisdn, msisdnStatus,
					approvalSerial);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getSpecialMsisdnLvl()");

			lvl = "";
		} catch (Exception e) {
			logger.info("Exception caught in getSpecialMsisdnLvl():", e);

			throw new DAOException(e.getMessage(), e);
		}
		return lvl;
	}

    private static final String getNewMRTMsisdnLvlSQL = "SELECT DISTINCT msisdnlvl FROM bomweb_mrt_inventory "
	    + "WHERE msisdn=? "
	    + "AND msisdn_status=? "
	    + "AND srv_num_type='PCCW3G'";

    public String getNewMRTMsisdnLvl(String msisdn, String msisdnStatus)
	    throws DAOException {
	logger.info(" getNewMRTMsisdnLvlSQL is called");

	String lvl = "";

	try {
	    logger.info("getNewMRTMsisdnLvl() @ ArrayList<String>: "
		    + getNewMRTMsisdnLvlSQL);

	    lvl = (String) simpleJdbcTemplate.queryForObject(
		    getNewMRTMsisdnLvlSQL, String.class, msisdn, msisdnStatus);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getNewMRTMsisdnLvl()");

	    lvl = "";
	} catch (Exception e) {
	    logger.info("Exception caught in getNewMRTMsisdnLvl():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return lvl;
    }

    private static final String getDistinctUNICOM1C2NCityCdSQL = " SELECT DISTINCT city_cd \n"
	    + " FROM bomweb_mrt_inventory \n"
	    + " WHERE channel_cd =? \n"
	    + " AND msisdn_status=2 \n"
	    + " AND srv_num_type ='UNICOM1C2N' \n"
	    + " ORDER BY city_cd";

    public List<String> getDistinctUNICOM1C2NCityCd(String channel_cd)
	    throws DAOException {
	logger.info(" getDistinctUNICOM1C2NCityCd is called");
	List<String> itemList = new ArrayList<String>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
	    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getString("city_cd");
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getDistinctUNICOM1C2NCityCd() @ ArrayList<String>: "
		    + getDistinctUNICOM1C2NCityCdSQL);
	    itemList = simpleJdbcTemplate.query(getDistinctUNICOM1C2NCityCdSQL,
		    mapper, channel_cd);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getDistinctUNICOM1C2NCityCd()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getDistinctUNICOM1C2NCityCd():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    /*private static final String getRandomPCCW3GNumFromPoolSQL = "SELECT msisdn, msisdnlvl "
												    		 + "  FROM (SELECT DISTINCT msisdn, msisdnlvl "
												    		 + "                   FROM bomweb_mrt_inventory "
												    		 + "                  WHERE channel_cd = ? "
												    		 + "                    AND msisdn_status = 2 "
												    		 + "                    AND msisdnlvl IN ('N1', 'N2', 'GD') "
												    		 + "                    AND srv_num_type = 'PCCW3G' "
												    		 + "               ORDER BY DBMS_RANDOM.VALUE) "
												    		 + " WHERE ROWNUM <= 20 ";*/

    public List<String[]> getRandomPCCW3GNumFromPool(String channelCd, String numType)
	    throws DAOException {
	logger.info(" getRandomPCCW3GNumFromPool is called");
	
	MapSqlParameterSource params = new MapSqlParameterSource();
	
	StringBuffer sql = new StringBuffer("SELECT msisdn, msisdnlvl "
		+ "  FROM (SELECT DISTINCT msisdn, msisdnlvl "
		+ "                   FROM bomweb_mrt_inventory "
		+ "                  WHERE channel_cd = :channelCd ");
	params.addValue("channelCd", channelCd);
	if (numType != null) {
		sql.append(" AND num_type = :numType ");
		params.addValue("numType", numType);
	}
	
	sql.append(" AND msisdn_status = 2 "
		+ "                    AND msisdnlvl IN ('N1', 'N2', 'GD') "
		+ "                    AND srv_num_type = 'PCCW3G' "
		+ "               ORDER BY DBMS_RANDOM.VALUE) "
		+ " WHERE ROWNUM <= 20 "
		);
	
	List<String[]> itemList = new ArrayList<String[]>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
	    public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		String[] temp = new String[2];
		temp[0] = rs.getString("msisdn");
		temp[1] = rs.getString("msisdnlvl");
		return temp;
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getRandomPCCW3GNumFromPool() @ ArrayList<String>: "
		    + sql);

	    itemList = simpleJdbcTemplate.query(sql.toString(), mapper, params);
	} catch (EmptyResultDataAccessException erdae) {
	    logger.error("EmptyResultDataAccessException in getRandomPCCW3GNumFromPool()");

	    itemList = null;
	} catch (Exception e) {
	    logger.error("Exception caught in getRandomPCCW3GNumFromPool():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }

    private static final String getValidate3GnewMRTStatusSQl = 
    		   "select valid from (		 "
    		 + "SELECT  "
    		 + "  CASE WHEN msisdn_status = 2  "
    		 + "    OR msisdn_status = 18 THEN 'Y' ELSE 'N'  "
    		 + "  END AS valid  "
    		 + "  FROM (select msisdn_status from bomweb_mrt_inventory "
    		 + "  	  	WHERE msisdn = ? AND srv_num_type ='PCCW3G' order by stock_in_date desc) ) a "
    		 + "where rownum = 1 ";

    // add by Eliot 20120301
    public boolean validate3GnewMRTStatus(String msisdn) throws DAOException {
	logger.info(" validate3GnewMRTStatus is called");
	boolean validator = false;

	try {
	    logger.info("validate3GnewMRTStatus() @ ArrayList<String>: "
		    + getValidate3GnewMRTStatusSQl);
	    String validStr = (String) simpleJdbcTemplate.queryForObject(
		    getValidate3GnewMRTStatusSQl, String.class, msisdn);
	    if ("Y".equalsIgnoreCase(validStr)) {
		validator = true;
	    }
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in validate3GnewMRTStatus()");

	    validator = false;
	} catch (Exception e) {
	    logger.info("Exception caught in validate3GnewMRTStatus():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return validator;
    }

  /*  private static final String newMRTSearchSQl = 
    		   "select valid from ( "
    		 + "SELECT  CASE   "
    		 + "    WHEN msisdn_status = 2  THEN 'Y' ELSE 'N' END AS valid   "
    		 + "	FROM bomweb_mrt_inventory   "
    		 + "WHERE msisdn     = ?   "
    		 + "AND channel_cd = ?   "
    		 + "AND msisdnlvl in ('N1', 'N2', 'GD') order by stock_in_date desc) a "
    		 + "where rownum = 1 ";*/

    // add by Eliot 20120301
    public boolean newMRTSearch(String msisdn, String channelCd, String numType) throws DAOException {
	logger.info(" newMRTSearch is called");
	
	StringBuffer sql = new StringBuffer();
	sql.append("select valid from ( "
    		 + "SELECT  CASE   "
    		 + "    WHEN msisdn_status = 2  THEN 'Y' ELSE 'N' END AS valid   "
    		 + "	FROM bomweb_mrt_inventory   "
    		 + "WHERE msisdn     = :msisdn   ");
	
	if (numType != null) { //Dennis MIP3
		sql.append(" AND num_type = :numType ");
	}
	sql.append("AND channel_cd = :channelCd   "
    		 + "AND msisdnlvl in ('N1', 'N2', 'GD') order by stock_in_date desc) a "
    		 + "where rownum = 1 ");
	
	boolean validator = false;
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("msisdn", msisdn);
	params.addValue("channelCd", channelCd);
	params.addValue("numType", numType);

	try {
	    logger.info("newMRTSearch() @ ArrayList<String>: "
		    + sql.toString());
	    String validStr = (String) simpleJdbcTemplate.queryForObject(
	    		sql.toString(), String.class,params);
	    if ("Y".equalsIgnoreCase(validStr)) {
		validator = true;
	    }
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in newMRTSearch()");

	    validator = false;
	} catch (Exception e) {
	    logger.info("Exception caught in newMRTSearch():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return validator;
    }

    private static final String getValidateUnicomNumStatusSQl = 
    		   "select valid from ( "
    		 + "SELECT  "
    		 + "  CASE WHEN msisdn_status = 2 THEN 'Y'  "
    		 + "    ELSE 'N' END AS valid  "
    		 + "FROM (select msisdn_status from bomweb_mrt_inventory "
    		 + "  	  	WHERE msisdn = ? AND srv_num_type ='UNICOM1C2N' order by stock_in_date desc) ) a "
    		 + "where rownum = 1 ";

    // add by Eliot 20120301
    public boolean validateUnicomNumStatus(String msisdn) throws DAOException {
	logger.info(" validateUnicomNumStatus is called");
	boolean validator = false;

	try {
	    logger.info("validateUnicomNumStatus() @ ArrayList<String>: "
		    + getValidateUnicomNumStatusSQl);
	    String validStr = (String) simpleJdbcTemplate.queryForObject(
		    getValidateUnicomNumStatusSQl, String.class, msisdn);
	    if ("Y".equalsIgnoreCase(validStr)) {
		validator = true;
	    }
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in validateUnicomNumStatus()");

	    validator = false;
	} catch (Exception e) {
	    logger.info("Exception caught in validateUnicomNumStatus():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return validator;
    }

    // add by Joyce 20120208
    public boolean handleExpiredMRT(String staffId) throws DAOException {

	logger.info("handleExpiredMRT is called");

	boolean result = true;

	try {

	    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
		    .withSchemaName("OPS$CNM")
		    .withCatalogName("PKG_SB_MOB_MRT")
		    .withProcedureName("CHK_EXPIRED_MRT_BY_STAFF");
	    jdbcCall.setAccessCallParameterMetaData(false);
	    jdbcCall.declareParameters(new SqlParameter("i_staff_id",
		    Types.VARCHAR), new SqlOutParameter("errCode",
		    Types.INTEGER), new SqlOutParameter("errText",
		    Types.VARCHAR));

	    MapSqlParameterSource inMap = new MapSqlParameterSource();
	    inMap.addValue("i_staff_id", staffId);

	    SqlParameterSource in = inMap;
	    Map out = jdbcCall.execute(in);

	    int error_code = -1;
	    String error_text = null;

	    if (((Integer) out.get("errCode")) != null) {
		error_code = ((Integer) out.get("errCode")).intValue();
	    }

	    if ((out.get("errText")) != null) {
		error_text = out.get("errText").toString();
	    }

	    logger.info("OPS$CNM.pkg_sb_mob_mrt.chk_expired_mrt_by_staff() output errCode = "
		    + error_code);
	    logger.info("OPS$CNM.pkg_sb_mob_mrt.chk_expired_mrt_by_staff() output errText = "
		    + error_text);

	    logger.info("handleExpiredMRT result = " + result);

	    return result;

	} catch (Exception e) {
	    logger.error("Exception caught in handleExpiredMRT()", e);
	    throw new DAOException(e.getMessage(), e);
	}
    }

    // add by Joyce 20120208
    public boolean handleFrozenMRT() throws DAOException {

	logger.info("handleFrozenMRT is called");

	boolean result = true;

	try {

	    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
		    .withSchemaName("OPS$CNM")
		    .withCatalogName("pkg_sb_mob_mrt")
		    .withProcedureName("chk_frozen_mrt_by_batch");
	    jdbcCall.setAccessCallParameterMetaData(false);
	    jdbcCall.declareParameters(new SqlOutParameter("errCode",
		    Types.INTEGER), new SqlOutParameter("errText",
		    Types.VARCHAR));

	    MapSqlParameterSource inMap = new MapSqlParameterSource();

	    SqlParameterSource in = inMap;
	    Map out = jdbcCall.execute(in);

	    int error_code = -1;
	    String error_text = null;

	    if (((Integer) out.get("errCode")) != null) {
		error_code = ((Integer) out.get("errCode")).intValue();
	    }

	    if ((out.get("errText")) != null) {
		error_text = out.get("errText").toString();
	    }

	    logger.info("OPS$CNM.pkg_sb_mob_mrt.chk_frozen_mrt_by_batch() output errCode = "
		    + error_code);
	    logger.info("OPS$CNM.pkg_sb_mob_mrt.chk_frozen_mrt_by_batch() output errText = "
		    + error_text);

	    logger.info("handleFrozenMRT result = " + result);

	    return result;

	} catch (Exception e) {
	    logger.error("Exception caught in handleFrozenMRT()", e);
	    throw new DAOException(e.getMessage(), e);
	}
    }

    private static final String getGoldenNumLvlCondDtlCdSQL = "SELECT cond_type, " 
	    +"  cond_value, cond_id " 
	    +"FROM w_mip_golden_num_lvl_cond_dtl " 
	    +"WHERE golden_num_lvl = ? " 
	    +"AND mrt_type ='PCCW3G' "
	    +"AND trunc(to_date(?, 'DD/MM/YYYY')) "
	    +"BETWEEN trunc(eff_start_date) AND trunc(NVL(eff_end_date, SYSDATE)) "
	    + "AND nvl(decode (MIP_BRAND, '9', ?, MIP_BRAND ), '1') = ? \n" 
	    +"ORDER BY cond_id";
  

    public List<HashMap<String, Integer>> getGoldenNumLvlCondDtl(String goldenLvl, String appDate, String mipBrand)
	    throws DAOException {
		logger.info(" getGoldenNumLvlCondDtl is called");
		List<HashMap<String, Integer>> itemMapList = new ArrayList<HashMap<String, Integer>>();
		HashMap<String, Integer> itemMap = new HashMap<String, Integer>();
		List<Object[]> itemList = new ArrayList<Object[]>();
		/**** ==ParameterizedRowMapper start== *********************************************************/
		ParameterizedRowMapper<Object[]> mapper = new ParameterizedRowMapper<Object[]>() {
		    public Object[] mapRow(ResultSet rs, int rowNum)
			    throws SQLException {
			Object temp[] = new Object[3];
			temp[0] = rs.getString("cond_type");
			temp[1] = rs.getInt("cond_value");
			temp[2] = rs.getInt("cond_id");
			return temp;
		    }
		};
		/**** ==ParameterizedRowMapper end== *********************************************************/
		try {
		    logger.info("getGoldenNumLvlCondDtl() @ HashMap<String, Integer>: "
			    + getGoldenNumLvlCondDtlCdSQL);
		    itemList = simpleJdbcTemplate.query(getGoldenNumLvlCondDtlCdSQL, mapper, goldenLvl, appDate, mipBrand , mipBrand);
		    
		    Iterator<Object[]> itr = itemList.iterator();
		    int currentCondId = -1;
		    while(itr.hasNext()){
		    	Object[] temp = itr.next();
		    	if (currentCondId == -1) {
		    		currentCondId = (Integer)temp[2];
		    	} else if (currentCondId != (Integer)temp[2]) {
		    		currentCondId = (Integer)temp[2];
		    		itemMapList.add(itemMap);
		    		itemMap = new HashMap<String, Integer>(); 
		    	}
		    	itemMap.put((String)temp[0], (Integer)temp[1]);
		    }
		    itemMapList.add(itemMap);
		    
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getGoldenNumLvlCondDtl()");
	
		    itemMapList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getGoldenNumLvlCondDtl():", e);
	
		    throw new DAOException(e.getMessage(), e);
		}
		return itemMapList;
    }
   
    private static final String getGoldenNumRandsomListSQL = "SELECT msisdn, " 
	    +"  msisdnlvl " 
	    +"FROM " 
	    +"  (SELECT msisdn, " 
	    +"    msisdnlvl " 
	    +"  FROM bomweb_mrt_inventory " 
	    +"  WHERE srv_num_type = 'PCCW3G' " 
	    +"  AND channel_cd     = " 
	    +"    (SELECT code_id " 
	    +"    FROM bomweb_code_lkup " 
	    +"    WHERE code_desc = 'Call Centre Code in POS' " 
	    +"    AND code_type   ='CCS_CODE' " 
	    +"    ) " 
	    +"  AND msisdn_status = '2' " 
	    +"  AND msisdnLvl    IN " 
	    +"    (select distinct golden_num_lvl from "
	    + "	    (select golden_num_lvl, cond_id,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t2"
	    + "			where t2.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t2.cond_id = t1.cond_id"
	    + "			and t2.COND_TYPE = 'CONTRACT_PERIOD'"
	    + "			and trunc(:appDate) BETWEEN trunc(t2.eff_start_date) AND trunc(NVL(t2.eff_end_date, SYSDATE))"  
	    + "         and nvl(decode (t2.MIP_BRAND, '9', :mipBrand, t2.MIP_BRAND ), '1') = :mipBrand \n" 
	    + "			and rownum<=1"
	    + "		) CONTRACT_PERIOD,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t3"
	    + "			where t3.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t3.cond_id = t1.cond_id"
	    + "			and t3.COND_TYPE = 'IS_VIP'"
	    + "			and trunc(:appDate) BETWEEN trunc(t3.eff_start_date) AND trunc(NVL(t3.eff_end_date, SYSDATE))"  
	    + "         and nvl(decode (t3.MIP_BRAND, '9', :mipBrand, t3.MIP_BRAND ), '1') = :mipBrand \n" 
	    + "			and rownum<=1"
	    + "		) IS_VIP,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t4"
	    + "			where t4.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t4.cond_id = t1.cond_id"
	    + "			and t4.COND_TYPE = 'RP_RECUR_CHARGE'"
	    + "			and trunc(:appDate) BETWEEN trunc(t4.eff_start_date) AND trunc(NVL(t4.eff_end_date, SYSDATE))"
	    + "         and nvl(decode (t4.MIP_BRAND, '9', :mipBrand, t4.MIP_BRAND ), '1') = :mipBrand \n" 
	    + "			and rownum<=1 "
	    + "		) RP_RECUR_CHARGE "
	    + "  from w_mip_golden_num_lvl_cond_dtl t1 "
	    + "  WHERE TRUNC(:appDate) BETWEEN TRUNC(t1.eff_start_date) AND TRUNC(NVL(t1.eff_end_date, SYSDATE)) "  //add 20141103
	    + "  and nvl(decode (t1.MIP_BRAND, '9', :mipBrand, t1.MIP_BRAND ), '1') = :mipBrand \n" 
	    + "	 group by golden_num_lvl, cond_id) "
	    + "  where RP_RECUR_CHARGE is not null "
	    + "  and IS_VIP = :isVip "
	    + "  and CONTRACT_PERIOD <= :contractPeriod "
	    + "  and RP_RECUR_CHARGE <= :rpRecurCharge "
	    
	    /**************************add by D 20141103********************/
	    + "  UNION " 
	    + "  select distinct golden_num_lvl from "
	    + "	    (select golden_num_lvl, cond_id,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t2"
	    + "			where t2.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t2.cond_id = t1.cond_id"
	    + "			and t2.COND_TYPE = 'CONTRACT_PERIOD'"
	    + "			and trunc(:appDate) BETWEEN trunc(t2.eff_start_date) AND trunc(NVL(t2.eff_end_date, SYSDATE))"
	    + "         and nvl(decode (t2.MIP_BRAND, '9', :mipBrand, t2.MIP_BRAND ), '1') = :mipBrand \n" 
	    + "			and rownum<=1"
	    + "		) CONTRACT_PERIOD,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t3"
	    + "			where t3.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t3.cond_id = t1.cond_id"
	    + "			and t3.COND_TYPE = 'IS_VIP'"
	    + "			and trunc(:appDate) BETWEEN trunc(t3.eff_start_date) AND trunc(NVL(t3.eff_end_date, SYSDATE))"  
	    + "         and nvl(decode (t3.MIP_BRAND, '9', :mipBrand, t3.MIP_BRAND ), '1') = :mipBrand \n"
	    + "			and rownum<=1"
	    + "		) IS_VIP,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t4"
	    + "			where t4.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t4.cond_id = t1.cond_id"
	    + "			and t4.COND_TYPE = 'GROSS_PLAN_FEE'"
	    + "			and trunc(:appDate) BETWEEN trunc(t4.eff_start_date) AND trunc(NVL(t4.eff_end_date, SYSDATE))" 
	    + "         and nvl(decode (t4.MIP_BRAND, '9', :mipBrand, t4.MIP_BRAND ), '1') = :mipBrand \n"
	    + "			and rownum<=1 "
	    + "		) GROSS_PLAN_FEE "
	    + "  from w_mip_golden_num_lvl_cond_dtl t1 "
	    + "  WHERE TRUNC(:appDate) BETWEEN TRUNC(t1.eff_start_date) AND TRUNC(NVL(t1.eff_end_date, SYSDATE)) "  //add 20141103
	    + "  and nvl(decode (t1.MIP_BRAND, '9', :mipBrand, t1.MIP_BRAND ), '1') = :mipBrand \n" 
	    + "	 group by golden_num_lvl, cond_id) "
	    + "  where GROSS_PLAN_FEE is not null "
	    + "  and IS_VIP = :isVip "
	    + "  and CONTRACT_PERIOD <= :contractPeriod "
	    + "  and GROSS_PLAN_FEE <= :grossPlanFee "
	    /**************************add by D 20141103********************/
	    +"   ) " 
	    
	    +"  ORDER BY dbms_random.value " 
	    +"  ) " 
	    +"WHERE rownum <= 3";

    
    
    
    public List<String[]> getGoldenNumRandsomList(String vip, String period, String charge, String grossPlanFee, Date appDate, String mipBrand)
	    throws DAOException {
	logger.info(" getGoldenNumRandsomList is called");	
	/**** ==ParameterizedRowMapper start== *********************************************************/
	List<String[]> itemList = new ArrayList<String[]>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
	    public String[] mapRow(ResultSet rs, int rowNum)
		    throws SQLException {
		String temp[] = new String[2];
		temp[0] = rs.getString("msisdn");
		temp[1] = rs.getString("msisdnlvl");
		return temp;
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("isVip", vip);
	params.addValue("contractPeriod", period);
	params.addValue("rpRecurCharge", charge);
	params.addValue("grossPlanFee", grossPlanFee);
	params.addValue("appDate", appDate);
	params.addValue("mipBrand",mipBrand); //DENNIS MIP3
	try { 
	    logger.info("getGoldenNumRandsomList() @ List<String[]>: " + getGoldenNumRandsomListSQL);
	    itemList = simpleJdbcTemplate.query(getGoldenNumRandsomListSQL, mapper, params);
	       
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getGoldenNumRandsomList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getGoldenNumRandsomList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }
    
    private static final String getGoldenNumSearchListSQL = "SELECT msisdn, " 
	    + "  msisdnlvl " 
	    + "FROM bomweb_mrt_inventory " 
	    + "WHERE srv_num_type = 'PCCW3G' " 
	    + "AND channel_cd     = " 
	    + "  (SELECT code_id " 
	    + "  FROM bomweb_code_lkup " 
	    + "  WHERE code_desc = 'Call Centre Code in POS' " 
	    + "  AND code_type   ='CCS_CODE' " 
	    + "  ) " 
	    + "AND msisdn_status = '2' " 
	    + "AND msisdn LIKE :searchMsisdn " 
	    + "AND msisdnLvl IN " 
	    + "  (select distinct golden_num_lvl from "
	    + "	    (select golden_num_lvl, cond_id,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t2"
	    + "			where t2.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t2.cond_id = t1.cond_id"
	    + "			and t2.COND_TYPE = 'CONTRACT_PERIOD'"
	    + "			and trunc(:appDate) BETWEEN trunc(t2.eff_start_date) AND trunc(NVL(t2.eff_end_date, SYSDATE))"  
	    + "         and nvl(decode (t2.MIP_BRAND, '9', :mipBrand, t2.MIP_BRAND ), '1') = :mipBrand \n"	    
	    + "			and rownum<=1"
	    + "		) CONTRACT_PERIOD,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t3"
	    + "			where t3.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t3.cond_id = t1.cond_id"
	    + "			and t3.COND_TYPE = 'IS_VIP'"
	    + "			and trunc(:appDate) BETWEEN trunc(t3.eff_start_date) AND trunc(NVL(t3.eff_end_date, SYSDATE))"  
	    + "         and nvl(decode (t3.MIP_BRAND, '9', :mipBrand, t3.MIP_BRAND ), '1') = :mipBrand \n"
	    + "			and rownum<=1"
	    + "		) IS_VIP,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t4"
	    + "			where t4.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t4.cond_id = t1.cond_id"
	    + "			and t4.COND_TYPE = 'RP_RECUR_CHARGE'"
	    + "			and trunc(:appDate) BETWEEN trunc(t4.eff_start_date) AND trunc(NVL(t4.eff_end_date, SYSDATE))"
	    + "         and nvl(decode (t4.MIP_BRAND, '9', :mipBrand, t4.MIP_BRAND ), '1') = :mipBrand \n"
	    + "			and rownum<=1 "
	    + "		) RP_RECUR_CHARGE "
	    + "  from w_mip_golden_num_lvl_cond_dtl t1 "
	    + "  WHERE TRUNC(:appDate) BETWEEN TRUNC(t1.eff_start_date) AND TRUNC(NVL(t1.eff_end_date, SYSDATE)) "  //add  20141103
	    + "  and nvl(decode (t1.MIP_BRAND, '9', :mipBrand, t1.MIP_BRAND ), '1') = :mipBrand \n"
	    + "	 group by golden_num_lvl, cond_id) "
	    + "  where RP_RECUR_CHARGE is not null "
	    + "  and IS_VIP = :isVip "
	    + "  and CONTRACT_PERIOD <= :contractPeriod "
	    + "  and RP_RECUR_CHARGE <= :rpRecurCharge "
	    
	    /**************************add by D 20141103********************/
	    + "  UNION " 
	    + "  select distinct golden_num_lvl from "
	    + "	    (select golden_num_lvl, cond_id,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t2"
	    + "			where t2.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t2.cond_id = t1.cond_id"
	    + "			and t2.COND_TYPE = 'CONTRACT_PERIOD'"
	    + "			and trunc(:appDate) BETWEEN trunc(t2.eff_start_date) AND trunc(NVL(t2.eff_end_date, SYSDATE))"  
	    + "         and nvl(decode (t2.MIP_BRAND, '9', :mipBrand, t2.MIP_BRAND ), '1') = :mipBrand \n"
	    + "			and rownum<=1"
	    + "		) CONTRACT_PERIOD,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t3"
	    + "			where t3.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t3.cond_id = t1.cond_id"
	    + "			and t3.COND_TYPE = 'IS_VIP'"
	    + "			and trunc(:appDate) BETWEEN trunc(t3.eff_start_date) AND trunc(NVL(t3.eff_end_date, SYSDATE))"
	    + "         and nvl(decode (t3.MIP_BRAND, '9', :mipBrand, t3.MIP_BRAND ), '1') = :mipBrand \n"
	    + "			and rownum<=1"
	    + "		) IS_VIP,"
	    + "		(select cond_value from w_mip_golden_num_lvl_cond_dtl t4"
	    + "			where t4.golden_num_lvl = t1.golden_num_lvl"
	    + "			and t4.cond_id = t1.cond_id"
	    + "			and t4.COND_TYPE = 'GROSS_PLAN_FEE'"
	    + "			and trunc(:appDate) BETWEEN trunc(t4.eff_start_date) AND trunc(NVL(t4.eff_end_date, SYSDATE))"  
	    + "         and nvl(decode (t4.MIP_BRAND, '9', :mipBrand, t4.MIP_BRAND ), '1') = :mipBrand \n"
	    + "			and rownum<=1 "
	    + "		) GROSS_PLAN_FEE "
	    + "  from w_mip_golden_num_lvl_cond_dtl t1 "
	    + "  WHERE TRUNC(:appDate) BETWEEN TRUNC(t1.eff_start_date) AND TRUNC(NVL(t1.eff_end_date, SYSDATE)) "  //add  20141103
	    + "  and nvl(decode (t1.MIP_BRAND, '9', :mipBrand, t1.MIP_BRAND ), '1') = :mipBrand \n"
	    + "	 group by golden_num_lvl, cond_id) "
	    + "  where GROSS_PLAN_FEE is not null "
	    + "  and IS_VIP = :isVip "
	    + "  and CONTRACT_PERIOD <= :contractPeriod "
	    + "  and GROSS_PLAN_FEE <= :grossPlanFee "
	    /**************************add by D 20141103********************/
	    
	    + "  ) " 
	    + "AND rownum <= 3";

    public List<String[]> getGoldenNumSearchList(String vip, String period, String charge, String searchMsisdn, String grossPlanFee, Date appDate, String mipBrand)
	    throws DAOException {
	logger.info(" getGoldenNumSearchList is called");	
	/**** ==ParameterizedRowMapper start== *********************************************************/
	List<String[]> itemList = new ArrayList<String[]>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
	    public String[] mapRow(ResultSet rs, int rowNum)
		    throws SQLException {
		String temp[] = new String[2];
		temp[0] = rs.getString("msisdn");
		temp[1] = rs.getString("msisdnlvl");
		return temp;
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("searchMsisdn", searchMsisdn);
	params.addValue("isVip", vip);
	params.addValue("contractPeriod", period);
	params.addValue("rpRecurCharge", charge);
	params.addValue("grossPlanFee", grossPlanFee);
	params.addValue("appDate", appDate);
	params.addValue("mipBrand", mipBrand);
	try {
	    logger.info("getGoldenNumSearchList() @ List<String[]>: " + getGoldenNumSearchListSQL);
	    itemList = simpleJdbcTemplate.query(getGoldenNumSearchListSQL, mapper,params);
	    
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getGoldenNumSearchList()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getGoldenNumSearchList():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
    }
    
    /*private static final String getPatternNumSearchListSQL = "SELECT msisdn, " 
    	    + "  msisdnlvl " 
    	    + "FROM bomweb_mrt_inventory " 
    	    + "WHERE srv_num_type = 'PCCW3G' " 
    	    + "AND channel_cd     = " 
    	    + "  (SELECT code_id " 
    	    + "  FROM bomweb_code_lkup " 
    	    + "  WHERE code_id = 'MDV' " 
    	    + "  AND code_type   ='DS_CH' " 
    	    + "  ) " 
    	    + "AND msisdn_status = '2' " 
    	    + "AND msisdn LIKE :searchMsisdn " ;*/
    
    //DS
    public List<String[]> getPatternNumSearchList(String channelCd, String teamCdorCenterCd, String searchMsisdn, String numType)
    	    throws DAOException {
    	logger.info(" getPatternNumSearchList is called");	
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("select msisdn, msisdnlvl from bomweb_mrt_inventory "
    			+ "where srv_num_type = 'PCCW3G' "
    			+ "and rownum <= (select code_id from bomweb_code_lkup where code_type = 'MRT_SEARCH_MAX') "
    			+ "and msisdn_status ='2' "
    			+ "AND msisdn LIKE :searchMsisdn  "
    			+ "AND channel_cd = :channelCd ");
    	if (channelCd.equalsIgnoreCase("MDV")) {
    		sql.append(" and team_cd =:teamCdorCenterCd ");
    	} else if(channelCd.equalsIgnoreCase("SIS")){
    		sql.append(" and centre_cd = :teamCdorCenterCd ");
    	}else{
		}
    	
    	
    	if (numType != null) { //Dennis MIP3
    		sql.append(" AND num_type = :numType ");
    	}
    	//sql.append("AND msisdn LIKE :searchMsisdn ");
    	
    	/**** ==ParameterizedRowMapper start== *********************************************************/
    	List<String[]> itemList = new ArrayList<String[]>();
    	/**** ==ParameterizedRowMapper start== *********************************************************/
    	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
    	    public String[] mapRow(ResultSet rs, int rowNum)
    		    throws SQLException {
    		String temp[] = new String[2];
    		temp[0] = rs.getString("msisdn");
    		temp[1] = rs.getString("msisdnlvl");
    		return temp;
    	    }
    	};
    	/**** ==ParameterizedRowMapper end== *********************************************************/
    	
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("searchMsisdn", searchMsisdn);
    	params.addValue("channelCd", channelCd);
    	params.addValue("teamCdorCenterCd", teamCdorCenterCd);
    	params.addValue("numType", numType);
    	
    	try {
    	    logger.info("getPatternNumSearchList() @ List<String[]>: " );
    	    itemList = simpleJdbcTemplate.query(sql.toString(), mapper,params);
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.info("EmptyResultDataAccessException in getPatternNumSearchList()");
    	    itemList = null;
    	} catch (Exception e) {
    	    logger.info("Exception caught in getPatternNumSearchList():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	return itemList;
        }
  
    
    public List<String[]> getPatternNumSearchListCcs(String channelShopCd, String searchMsisdn, String numType)
    	    throws DAOException {
    	logger.info(" getPatternNumSearchList is called");	
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("select msisdn, msisdnlvl from bomweb_mrt_inventory "
    			+ "where srv_num_type = 'PCCW3G' "
    			+ "and msisdn_status ='2' "
    			+ "and rownum <= (select code_id from bomweb_code_lkup where code_type = 'MRT_SEARCH_MAX') "
    			//+ "and msisdnlvl like 'N%'"
    			+ "AND msisdn LIKE :searchMsisdn  "
    			+ "and channel_cd= (select code_id from bomweb_code_lkup where code_type='CCS_CH' and code_id= :channelShopCd)");
    	
    	if (numType != null) { //Dennis MIP3
    		sql.append(" AND num_type = :numType ");
    	}
    	
    	//JOptionPane.showMessageDialog(null,channelShopCd);
    	/**** ==ParameterizedRowMapper start== *********************************************************/
    	List<String[]> itemList = new ArrayList<String[]>();
    	/**** ==ParameterizedRowMapper start== *********************************************************/
    	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
    	    public String[] mapRow(ResultSet rs, int rowNum)
    		    throws SQLException {
    		String temp[] = new String[2];
    		temp[0] = rs.getString("msisdn");
    		temp[1] = rs.getString("msisdnlvl");
    		return temp;
    	    }
    	};
    	/**** ==ParameterizedRowMapper end== *********************************************************/
    	
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("searchMsisdn", searchMsisdn);
    	params.addValue("channelShopCd", channelShopCd);
    	params.addValue("numType", numType);
    	try {
    	    logger.info("getPatternNumSearchList() @ List<String[]>: " );
    	    itemList = simpleJdbcTemplate.query(sql.toString(), mapper,params);
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.info("EmptyResultDataAccessException in getPatternNumSearchList()");

    	    itemList = null;
    	} catch (Exception e) {
    	    logger.info("Exception caught in getPatternNumSearchList():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	return itemList;
        }
    
    public List<String[]> getPatternNumSearchListCcsMul(String searchMsisdn)
    	    throws DAOException {
    	logger.info(" getPatternNumSearchList is called");	
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("select msisdn, msisdnlvl from bomweb_mrt_inventory "
    			+ "where srv_num_type = 'PCCW3G' "
    			+ "and msisdn_status ='2' "
    			+ "and msisdnlvl like 'N%' "
    			+ "and rownum <= (select code_id from bomweb_code_lkup where code_type = 'MRT_SEARCH_MAX') "
    			+ "AND msisdn LIKE :searchMsisdn  "
    			+ "and channel_cd= 'MUL'");
 
    	/**** ==ParameterizedRowMapper start== *********************************************************/
    	List<String[]> itemList = new ArrayList<String[]>();
    	/**** ==ParameterizedRowMapper start== *********************************************************/
    	ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
    	    public String[] mapRow(ResultSet rs, int rowNum)
    		    throws SQLException {
    		String temp[] = new String[2];
    		temp[0] = rs.getString("msisdn");
    		temp[1] = rs.getString("msisdnlvl");
    		return temp;
    	    }
    	};
    	/**** ==ParameterizedRowMapper end== *********************************************************/
    	
    	MapSqlParameterSource params = new MapSqlParameterSource();
    	params.addValue("searchMsisdn", searchMsisdn);
    	
    	try {
    	    logger.info("getPatternNumSearchList() @ List<String[]>: " );
    	    itemList = simpleJdbcTemplate.query(sql.toString(), mapper,params);
    	} catch (EmptyResultDataAccessException erdae) {
    	    logger.info("EmptyResultDataAccessException in getPatternNumSearchList()");

    	    itemList = null;
    	} catch (Exception e) {
    	    logger.info("Exception caught in getPatternNumSearchList():", e);

    	    throw new DAOException(e.getMessage(), e);
    	}
    	return itemList;
    }
    
     public void updateMnpTicketNum(String orderId, String mnpTicketNum, Date cutOverDate, String username) throws DAOException {
    	logger.info("updateMnpTicketNum is called");

		String SQL = "update bomweb_mnp set mnp_ticket_num = ?, cut_over_date = ?, " 
				+ "last_upd_by = ?, last_upd_date = sysdate where order_id = ? ";

		try {
			simpleJdbcTemplate.update(SQL, mnpTicketNum, cutOverDate, username, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateMnpTicketNum()", e);
			throw new DAOException(e.getMessage(), e);
		}
    }
    
    public void updateMrtServiceDate(String orderId, String mnpInd, Date serviceDate, String username) throws DAOException {
    	logger.info("updateMrtServiceDate is called");
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("update bomweb_mrt set last_upd_by = ?, last_upd_date = sysdate, ");
    	if (mnpInd.equalsIgnoreCase("Y")) {
    		sql.append("cut_over_date = ? ");
    	} else {
    		sql.append("srv_req_date = ? ");
    	}
    	
    	sql.append("where order_id = ?");
    	
    	try {
			simpleJdbcTemplate.update(sql.toString(), username, serviceDate, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in updateMrtServiceDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
    }
    
    
    
    //add by Eliot 20120418
    //modify by Eliot 201204123
    private static final String getPendingOrderExistWithMsisdnByPendingAndFalloutSQL = "SELECT O.ORDER_ID " 
	    +"FROM BOMWEB_ORDER O " 
	    +"WHERE (O.ORDER_STATUS IN ('01', '99') " 
	    +"OR (o.order_status     = 'SUCCESS' " 
	    +"AND O.SRV_REQ_DATE     > sysdate) " 
	    +"OR (O.ORDER_STATUS     = '02' " 
	    +"AND O.SRV_REQ_DATE     > sysdate)) " 
	    +"AND O.MSISDN           = ? " 
	    +"AND O.LOB              = 'MOB'"
	    +"AND O.APP_DATE         > sysdate - 90";
    
    public List<String> getPendingOrderExistWithMsisdnByPendingAndFallout(String msisdn) throws DAOException{
	logger.info(" getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout is called");
	List<String> itemList = new ArrayList<String>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
	    public String mapRow(ResultSet rs, int rowNum)
		    throws SQLException {
		return rs.getString("ORDER_ID");
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getPendingOrderExistWithMsisdnByPendingAndFallout() @ List<String>: " + getPendingOrderExistWithMsisdnByPendingAndFalloutSQL);
	    itemList = simpleJdbcTemplate.query(getPendingOrderExistWithMsisdnByPendingAndFalloutSQL, mapper, msisdn);
	    
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getPendingOrderExistWithMsisdnByPendingAndFallout()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getPendingOrderExistWithMsisdnByPendingAndFallout():", e);

	    throw new DAOException(e.getMessage(), e);
	}
		return itemList;
	
    }
    
    //add by Eliot 201204123
    private static final String getPendingOrderExistWithMsisdnOrderIdByPendingAndFalloutSQL = "SELECT O.ORDER_ID " 
	    +"FROM BOMWEB_ORDER O " 
	    +"WHERE (O.ORDER_STATUS IN ('01', '99') " 
	    +"OR (o.order_status     = 'SUCCESS' " 
	    +"AND O.SRV_REQ_DATE     > sysdate) " 
	    +"OR (O.ORDER_STATUS     = '02' " 
	    +"AND O.SRV_REQ_DATE     > sysdate)) " 
	    +"AND O.MSISDN           = ? " 
	    +"AND O.ORDER_ID         != ? "
	    +"AND O.LOB              = 'MOB'"
	    +"AND O.APP_DATE         > sysdate - 90";
    
    public List<String> getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout(String msisdn, String orderId) throws DAOException{
	logger.info(" getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout is called");
	List<String> itemList = new ArrayList<String>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
	    public String mapRow(ResultSet rs, int rowNum)
		    throws SQLException {
		return rs.getString("ORDER_ID");
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout() @ List<String>: " + getPendingOrderExistWithMsisdnOrderIdByPendingAndFalloutSQL);
	    itemList = simpleJdbcTemplate.query(getPendingOrderExistWithMsisdnOrderIdByPendingAndFalloutSQL, mapper, msisdn, orderId);
	    
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout():", e);

	    throw new DAOException(e.getMessage(), e);
	}
		return itemList;
	
    }
    
    private static final String getOrderExistWithMsisdnByPCancellingSQL = "SELECT O.ORDER_ID " 
	    +"FROM BOMWEB_ORDER O " 
	    +"WHERE O.ORDER_STATUS = '03' " 
	    +"AND O.MSISDN         = ? " 
	    +"AND O.LOB            = 'MOB'";
    
    public List<String> getOrderExistWithMsisdnByPCancelling(String msisdn) throws DAOException{
	logger.info(" getOrderExistWithMsisdnOrderIdByPCancelling is called");
	List<String> itemList = new ArrayList<String>();
	/**** ==ParameterizedRowMapper start== *********************************************************/
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
	    public String mapRow(ResultSet rs, int rowNum)
		    throws SQLException {
		return rs.getString("ORDER_ID");
	    }
	};
	/**** ==ParameterizedRowMapper end== *********************************************************/
	try {
	    logger.info("getOrderExistWithMsisdnByPCancelling() @ List<String>: " + getOrderExistWithMsisdnByPCancellingSQL);
	    itemList = simpleJdbcTemplate.query(getOrderExistWithMsisdnByPCancellingSQL, mapper, msisdn);
	    
	} catch (EmptyResultDataAccessException erdae) {
	    logger.info("EmptyResultDataAccessException in getOrderExistWithMsisdnByPCancelling()");

	    itemList = null;
	} catch (Exception e) {
	    logger.info("Exception caught in getOrderExistWithMsisdnByPCancelling():", e);

	    throw new DAOException(e.getMessage(), e);
	}
	return itemList;
	
    }
    
    private static final String getOrderExistWithMsisdnOrderIdByPCancellingSQL = "SELECT O.ORDER_ID " 
    	     +"FROM BOMWEB_ORDER O " 
    	     +"WHERE O.ORDER_STATUS = '03' " 
    	     +"AND O.MSISDN         = ? "
    	     +"AND O.ORDER_ID       != ? "
    	     +"AND O.LOB            = 'MOB'";
    	    
    public List<String> getOrderExistWithMsisdnOrderIdByPCancelling(String msisdn, String orderId) throws DAOException{
    	 logger.info(" getOrderExistWithMsisdnOrderIdByPCancelling is called");
    	 List<String> itemList = new ArrayList<String>();
    	 /**** ==ParameterizedRowMapper start== *********************************************************/
    	 ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
    	     public String mapRow(ResultSet rs, int rowNum) throws SQLException {
    	    	 return rs.getString("ORDER_ID");
    	     }
    	 };
    	 /**** ==ParameterizedRowMapper end== *********************************************************/
    	 try {
    	     logger.info("getOrderExistWithMsisdnOrderIdByPCancelling() @ List<String>: " + getOrderExistWithMsisdnOrderIdByPCancellingSQL);
    	     itemList = simpleJdbcTemplate.query(getOrderExistWithMsisdnOrderIdByPCancellingSQL, mapper, msisdn, orderId);
    	     
    	 } catch (EmptyResultDataAccessException erdae) {
    	     logger.info("EmptyResultDataAccessException in getOrderExistWithMsisdnOrderIdByPCancelling()");

    	     itemList = null;
    	 } catch (Exception e) {
    	     logger.info("Exception caught in getOrderExistWithMsisdnOrderIdByPCancelling():", e);

    	     throw new DAOException(e.getMessage(), e);
    	 }
    	 return itemList;
    	 
   }
    /**
     * Retrieve all data from W_golden_num_lvl tables
     * @return
     * @throws DAOException 
     */
    public List<String> getGoldenNumLvL(boolean exclusiveGA) throws DAOException {
    	logger.info(" getGoldenNumLvL is called");
    	List<String> itemList = null;
    	
    	String sql = "select golden_num_lvl from w_golden_num_lvl";
    	
    	if (exclusiveGA) {
    		sql = sql + " where golden_num_lvl <> 'GA'";
    	}
    	
    	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
   	     public String mapRow(ResultSet rs, int rowNum) throws SQLException {
   	    	 return rs.getString("GOLDEN_NUM_LVL");
   	     }
    	};
    	
    	try {
   	     logger.info("getGoldenNumLvL() @ List<String>: " + sql);
   	     itemList = simpleJdbcTemplate.query(sql, mapper);
   	     
    	} catch (EmptyResultDataAccessException erdae) {
   	     logger.info("EmptyResultDataAccessException in getGoldenNumLvL()");
   	 	} catch (Exception e) {
   	     logger.info("Exception caught in getGoldenNumLvL():", e);
   	     throw new DAOException(e.getMessage(), e);
   	 	}
    	
    	return itemList;
    }
    
    public List<String> getFrozenWindow(String inDate) throws DAOException {
		
		logger.info("getFrozenWindow is called");
		logger.debug("getFrozenWindow inDate = " + inDate);
		
		String sql = "select frozen_window from w_mnp_frozen_window "
				 + "	where frozen_date = to_date(:inDate, 'DD/MM/YYYY') ";

		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("inDate", inDate);
		
		ParameterizedRowMapper<String> params = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("frozen_window");
			}
		};
		
		
		
		try {
			List<String> resultList = this.simpleJdbcTemplate.query(sql, params, mapSql);
				return resultList;
		} catch (Exception e) {
			logger.error("Exception caught in getFrozenWindow()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

    //add by V 20141029
    private static String getNewGoldenNumDateSQL= "SELECT to_date(code_id, 'yyyymmdd') report_date "
		    + "FROM bomweb_code_lkup "
		    + "WHERE code_type = 'GROSS_PLAN_FEE_GOLDEN_NO_RULE_START_DATE'";

	public Date getNewGoldenNumRulesDate()
		    throws DAOException {
		logger.info("getNewGoldenNumRulesDate @ MobCcsMrtDAO is called");

		ParameterizedRowMapper<Date> mapper = new ParameterizedRowMapper<Date>() {
		    public Date mapRow(ResultSet rs, int rowNum)
			    throws SQLException {

			return rs.getDate("report_date");
		    }
		};

		try {
		    logger.info("getNewGoldenNumRulesDate() @ MobCcsMrtDAO: "
			    + getNewGoldenNumDateSQL);

		    List<Date> result = simpleJdbcTemplate.query(getNewGoldenNumDateSQL, mapper);
		    
		    return result.get(0);
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getNewGoldenNumDate()");

		    return null;
		} catch (Exception e) {
		    logger.info("Exception caught in getNewGoldenNumDate():", e);

		    throw new DAOException(e.getMessage(), e);
		}
    }
	
	/**
	 * 
	 * @param channelCd
	 * @param msisdn
	 * @param numType
	 * @return
	 * @throws DAOException
	 */
	 public List<String[]> getOneCardTwoNumberByFullNumber(String channelCd, String msisdn,  String numType) throws DAOException {
		 logger.info("getOneCardTwoNumberByFullNumber is called");
			logger.debug("getOneCardTwoNumberByFullNumber channelCd = " + channelCd 
					+ ", msisdn=" + msisdn + ", numType=" + numType);
			
			String sql = "SELECT msisdn , " +
					"  msisdnLvl , " +
					"  channel_cd , " +
					"  city_cd , " +
					"  MSISDN_STATUS , " +
					"  num_type , " +
					"  srv_num_type " +
					"FROM bomweb_mrt_inventory " +
					"WHERE channel_cd          = :channelCd " +
					"AND msisdn_status         =2 " +
					"AND msisdn                = :msisdn " +
					"AND NVL(num_type,'H')     = :numType " +
					"AND srv_num_type = DECODE( :numType, 'C', 'CCU1C2N', 'UNICOM1C2N') " +
					"ORDER BY msisdn";

			MapSqlParameterSource mapSql = new MapSqlParameterSource();
			mapSql.addValue("channelCd", channelCd);
			mapSql.addValue("msisdn", msisdn);
			mapSql.addValue("numType", numType);
			
			ParameterizedRowMapper<String[]> params = new ParameterizedRowMapper<String[]>() {

				public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		    		String temp[] = new String[7];
		    		temp[0] = rs.getString("msisdn");
		    		temp[1] = rs.getString("msisdnlvl");
		    		temp[2] = rs.getString("channel_cd");
		    		temp[3] = rs.getString("city_cd");
		    		temp[4] = rs.getString("msisdn_status");
		    		temp[5]	= rs.getString("num_type");
		    		temp[6] = rs.getString("srv_num_type");
		    		return temp;
				}
			};
			
			
			
			try {
				List<String[]> resultList = this.simpleJdbcTemplate.query(sql, params, mapSql);
					return resultList;
			} catch (Exception e) {
				logger.error("Exception caught in getOneCardTwoNumberByFullNumber()", e);
				throw new DAOException(e.getMessage(), e);
			}
			
		}
	 
	 
	 /**
		 * 
		 * @param channelCd
		 * @param cityCd
		 * @param numType
		 * @return
		 * @throws DAOException
		 */
		 public List<String[]> getOneCardTwoNumberByRandom(String channelCd, String cityCd,  String numType) throws DAOException {
				logger.info("getOneCardTwoNumberByRandom is called");
				logger.debug("getOneCardTwoNumberByRandom channelCd = " + channelCd 
						+ ", cityCd=" + cityCd + ", numType=" + numType);
				
				String sql = "SELECT msisdn , " +
						"  msisdnLvl , " +
						"  channel_cd , " +
						"  city_cd , " +
						"  MSISDN_STATUS , " +
						"  num_type , " +
						"  srv_num_type " +
						"FROM bomweb_mrt_inventory " +
						"WHERE channel_cd          = :channelCd " +
						"AND city_cd               = :cityCd " +
						"AND msisdn_status         =2 " +
						"AND NVL(num_type,'H')     = :numType " +
						"AND srv_num_type = DECODE( :numType, 'C', 'CCU1C2N', 'UNICOM1C2N') " +
						"ORDER BY msisdn";

				MapSqlParameterSource mapSql = new MapSqlParameterSource();
				mapSql.addValue("channelCd", channelCd);
				mapSql.addValue("cityCd", cityCd);
				mapSql.addValue("numType", numType);
				
				ParameterizedRowMapper<String[]> params = new ParameterizedRowMapper<String[]>() {

					public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
			    		String temp[] = new String[7];
			    		temp[0] = rs.getString("msisdn");
			    		temp[1] = rs.getString("msisdnlvl");
			    		temp[2] = rs.getString("channel_cd");
			    		temp[3] = rs.getString("city_cd");
			    		temp[4] = rs.getString("msisdn_status");
			    		temp[5]	= rs.getString("num_type");
			    		temp[6] = rs.getString("srv_num_type");
			    		return temp;
					}
				};
				
				
				
				try {
					List<String[]> resultList = this.simpleJdbcTemplate.query(sql, params, mapSql);
						return resultList;
				} catch (Exception e) {
					logger.error("Exception caught in getOneCardTwoNumberByRandom()", e);
					throw new DAOException(e.getMessage(), e);
				}			
			}
}