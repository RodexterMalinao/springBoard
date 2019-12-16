package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CustAddrDTO;

public class MobCcsCustAddressDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	
	 private static String getCustAddrDTOSQL = "SELECT \n"+
     "     ORDER_ID,\n"+
     "     ADDR_USAGE,\n"+
     "     FLAT,\n"+
     "     AREA_CD,\n"+
     "     DIST_NO,\n"+
     "     SECT_CD,\n"+
     "     STR_NAME,\n"+
     "     HI_LOT_NO,\n"+
     "     STR_CAT_CD,\n"+
     "     BUILD_NO,\n"+
     "     FOREIGN_ADDR_FLAG,\n"+
     "     FLOOR_NO,\n"+
     "     UNIT_NO,\n"+
     "     PO_BOX_NO,\n"+
     "     ADDR_TYPE,\n"+
     "     STR_NO,\n"+
     "     SECT_DEP_IND,\n"+
     "     CREATE_DATE,\n"+
     "     AREA_DESC,\n"+
     "     DIST_DESC,\n"+
     "     SECT_DESC,\n"+
     "     STR_CAT_DESC,\n"+
     "     CREATE_BY,\n"+
     "     LAST_UPD_BY,\n"+
     "     LAST_UPD_DATE,\n"+
     "     SERBDYNO,\n"+
     "     BLACKLIST_IND,\n"+
     "     DTL_ID,\n"+
     "     LOCK_IND\n"+
      "from BOMWEB_CUST_ADDR Where ORDER_ID =? and ADDR_USAGE =?\n"; 
	 
	 
public CustAddrDTO getCustAddrDTO(String orderId, String addrUsage) throws DAOException {
logger.info(" getCustAddrDTO is called");
List<CustAddrDTO> itemList = new ArrayList<CustAddrDTO>();
/**** ==ParameterizedRowMapper start== *********************************************************/
ParameterizedRowMapper<CustAddrDTO> mapper = new ParameterizedRowMapper<CustAddrDTO>() {
    public CustAddrDTO mapRow(ResultSet rs, int rowNum)
    		throws SQLException {
          	CustAddrDTO dto = new CustAddrDTO();
                dto.setOrderId(rs.getString("ORDER_ID"));
                dto.setAddrUsage(rs.getString("ADDR_USAGE"));
                dto.setFlat(rs.getString("FLAT"));
                dto.setAreaCd(rs.getString("AREA_CD"));
                dto.setDistNo(rs.getString("DIST_NO"));
                dto.setSectCd(rs.getString("SECT_CD"));
                dto.setStrName(rs.getString("STR_NAME"));
                dto.setHiLotNo(rs.getString("HI_LOT_NO"));
                dto.setStrCatCd(rs.getString("STR_CAT_CD"));
                dto.setBuildNo(rs.getString("BUILD_NO"));
                dto.setForeignAddrFlag(rs.getString("FOREIGN_ADDR_FLAG"));
                dto.setFloorNo(rs.getString("FLOOR_NO"));
                dto.setUnitNo(rs.getString("UNIT_NO"));
                dto.setPoBoxNo(rs.getString("PO_BOX_NO"));
                dto.setAddrType(rs.getString("ADDR_TYPE"));
                dto.setStrNo(rs.getString("STR_NO"));
                dto.setSectDepInd(rs.getString("SECT_DEP_IND"));
                dto.setCreateDate(rs.getDate("CREATE_DATE"));
                dto.setAreaDesc(rs.getString("AREA_DESC"));
                dto.setDistDesc(rs.getString("DIST_DESC"));
                dto.setSectDesc(rs.getString("SECT_DESC"));
                dto.setStrCatDesc(rs.getString("STR_CAT_DESC"));
                dto.setCreateBy(rs.getString("CREATE_BY"));
                dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
                dto.setLastUpdDate(rs.getDate("LAST_UPD_DATE"));
                dto.setSerbdyno(rs.getString("SERBDYNO"));
                dto.setBlacklistInd(rs.getString("BLACKLIST_IND"));
                dto.setDtlId(rs.getString("DTL_ID"));
                dto.setLockInd(rs.getString("LOCK_IND"));
          	return dto;
    }
};
/**** ==ParameterizedRowMapper end== *********************************************************/
		try {		
			logger.info("getCustAddrDTO() @ CustAddrDTO: " + getCustAddrDTOSQL);	
			itemList = simpleJdbcTemplate.query(getCustAddrDTOSQL, mapper ,orderId,  addrUsage);	
		} catch (EmptyResultDataAccessException erdae) {		
			logger.info("EmptyResultDataAccessException in getCustAddrDTO()");	
				
			itemList = null;	
		} catch (Exception e) {		
			logger.info("Exception caught in getCustAddrDTO():", e);	
				
			throw new DAOException(e.getMessage(), e);	
		}		
		return itemList.get(0);// only return the first one		
}

	// === Start of INSERT function ===
	private static final String insertCustAddrSQL = " INSERT INTO BOMWEB_CUST_ADDR ( \n"
			+ "    ORDER_ID,  \n"
			+ "    ADDR_USAGE,  \n"
			+ "    FLAT,  \n"
			+ "    AREA_CD,  \n"
			+ "    DIST_NO,  \n"
			+ "    SECT_CD,  \n"
			+ "    STR_NAME,  \n"
			+ "    HI_LOT_NO,  \n"
			+ "    STR_CAT_CD,  \n"
			+ "    BUILD_NO,  \n"
			+ "    FOREIGN_ADDR_FLAG,  \n"
			+ "    FLOOR_NO,  \n"
			+ "    UNIT_NO,  \n"
			+ "    PO_BOX_NO,  \n"
			+ "    ADDR_TYPE,  \n"
			+ "    STR_NO,  \n"
			+ "    SECT_DEP_IND,  \n"
			+ "    CREATE_DATE,  \n"
			+ "    AREA_DESC,  \n"
			+ "    DIST_DESC,  \n"
			+ "    SECT_DESC,  \n"
			+ "    STR_CAT_DESC,  \n"
			+ "    CREATE_BY,  \n"
			+ "    LAST_UPD_BY,  \n"
			+ "    LAST_UPD_DATE,  \n"
			+ "    SERBDYNO,  \n"
			+ "    BLACKLIST_IND,  \n"
			+ "    DTL_ID,  \n"
			+ "    LOCK_IND  \n"
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
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "? , \n"
			+ "?  \n"
			+ " ) \n";

	public int insertCustAddr(CustAddrDTO dto) throws DAOException {
		logger.info("insertCustAddr is called");

		try {
			logger.info("insertCustAddr() @ CustAddrDAO: "+ insertCustAddrSQL);
			return simpleJdbcTemplate.update(insertCustAddrSQL,
					//start sql mapping
					dto.getOrderId(),
					dto.getAddrUsage(),
					dto.getFlat(),
					dto.getAreaCd(),
					dto.getDistNo(),
					dto.getSectCd(),
					dto.getStrName(),
					dto.getHiLotNo(),
					dto.getStrCatCd(),
					dto.getBuildNo(),
					dto.getForeignAddrFlag(),
					dto.getFloorNo(),
					dto.getUnitNo(),
					dto.getPoBoxNo(),
					dto.getAddrType(),
					dto.getStrNo(),
					dto.getSectDepInd(),
					dto.getCreateDate(),
					dto.getAreaDesc(),
					dto.getDistDesc(),
					dto.getSectDesc(),
					dto.getStrCatDesc(),
					dto.getCreateBy(),
					dto.getLastUpdBy(),
					dto.getLastUpdDate(),
					dto.getSerbdyno(),
					dto.getBlacklistInd(),
					dto.getDtlId(),
					dto.getLockInd()
					//end sql mapping
					);
		} catch (Exception e) {
			logger.error("Exception caught in insertCustAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	// === End of INSERT function ===
	// === Start of UPDATE function ===
	private static final String updateCustAddrSQL = " UPDATE BOMWEB_CUST_ADDR SET \n"

+ "     FLAT = ? ,\n"
+ "     AREA_CD = ? ,\n"
+ "     DIST_NO = ? ,\n"
+ "     SECT_CD = ? ,\n"
+ "     STR_NAME = ? ,\n"
+ "     HI_LOT_NO = ? ,\n"
+ "     STR_CAT_CD = ? ,\n"
+ "     BUILD_NO = ? ,\n"
+ "     FOREIGN_ADDR_FLAG = ? ,\n"
+ "     FLOOR_NO = ? ,\n"
+ "     UNIT_NO = ? ,\n"
+ "     PO_BOX_NO = ? ,\n"
+ "     ADDR_TYPE = ? ,\n"
+ "     STR_NO = ? ,\n"
+ "     SECT_DEP_IND = ? ,\n"
+ "     CREATE_DATE = ? ,\n"
+ "     AREA_DESC = ? ,\n"
+ "     DIST_DESC = ? ,\n"
+ "     SECT_DESC = ? ,\n"
+ "     STR_CAT_DESC = ? ,\n"
+ "     CREATE_BY = ? ,\n"
+ "     LAST_UPD_BY = ? ,\n"
+ "     LAST_UPD_DATE = ? ,\n"
+ "     SERBDYNO = ? ,\n"
+ "     BLACKLIST_IND = ? ,\n"
+ "     DTL_ID = ? ,\n"
+ "     LOCK_IND = ? ,\n"
//  YOU NEED TO REMOVE LAST ',' 
			+ " WHERE  ORDER_ID= ? and  ADDR_USAGE=?\n";



	public int updateCustAddr(CustAddrDTO dto) throws DAOException {
		logger.info("updateCustAddr is called");


		try {
			logger.info("updateCustAddr() @ CustAddrDAO: "	+ updateCustAddrSQL);
			return simpleJdbcTemplate.update(updateCustAddrSQL,
					//start sql mapping
			
			dto.getFlat() ,
			dto.getAreaCd() ,
			dto.getDistNo() ,
			dto.getSectCd() ,
			dto.getStrName() ,
			dto.getHiLotNo() ,
			dto.getStrCatCd() ,
			dto.getBuildNo() ,
			dto.getForeignAddrFlag() ,
			dto.getFloorNo() ,
			dto.getUnitNo() ,
			dto.getPoBoxNo() ,
			dto.getAddrType() ,
			dto.getStrNo() ,
			dto.getSectDepInd() ,
			dto.getCreateDate() ,
			dto.getAreaDesc() ,
			dto.getDistDesc() ,
			dto.getSectDesc() ,
			dto.getStrCatDesc() ,
			dto.getCreateBy() ,
			dto.getLastUpdBy() ,
			dto.getLastUpdDate() ,
			dto.getSerbdyno() ,
			dto.getBlacklistInd() ,
			dto.getDtlId() ,
			dto.getLockInd() ,
					//----------------
			dto.getOrderId() ,
			dto.getAddrUsage() 
					//end sql mapping
					);


		} catch (Exception e) {
			logger.error("Exception caught in updateCustAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}


	}
	// === End of UPDATE function ===
	// === Start of DELETE function ===
	private static final String deleteCustAddrSQL = "  delete from BOMWEB_CUST_ADDR Where ORDER_ID =? and ADDR_USAGE =?"
;
	public int deleteCustAddr(String orderId, String addrUsage) throws DAOException {
		logger.info("deleteCustAddr is called");

		try {
			logger.info("deleteCustAddr() @ CustAddrDAO: "+ deleteCustAddrSQL);
			return simpleJdbcTemplate.update(deleteCustAddrSQL, orderId,  addrUsage
					//start sql Mapping
					//end sql Mapping
					);
		} catch (Exception e) {
			logger.error("Exception caught in deleteCustAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	// === End of DELETE function ===

}
