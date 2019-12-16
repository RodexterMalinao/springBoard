package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.ForceCancelDTO;

public class MobCcsForceCancelDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    
	private static final String getForceCancelDTOALLSQL = "select" +
			" O.ORDER_ID" +
			" , O.OCID" +
			" , O.ORDER_STATUS" +
			" , O.CHECK_POINT" +
			" , O.REASON_CD" +
			" , FA.MAX_FALLOUT_DATE" +
			" , trunc(sysdate - FA.MAX_FALLOUT_DATE) FALLOUT_DATE_DIFF" +
			" , (" +
			"  select DECODE(count(*), 0, 'N', 'Y')" +
			"  from BOMWEB_HOTTEST_MODEL HM" +
			"  , BOMWEB_SUBSCRIBED_ITEM   SII" +
			"  , W_ITEM_PRODUCT_POS_ASSGN IPPA" +
            "  where SII.ORDER_ID = O.ORDER_ID" +
            "  and SII.ID = IPPA.ITEM_ID" +
            "  and IPPA.POS_ITEM_CD = HM.ITEM_CODE" +
            "  and TRUNC(O.APP_DATE) between HM.START_DATE and TRUNC(NVL(HM.END_DATE, sysdate))" +
            " ) HOTTEST_MODEL_IND" +
            " , (" +
            "  select count(*)" +
            "  from BOMWEB_ORDER_FALLOUT FC" +
            "  inner join BOMWEB_CODE_LKUP CL on (CL.CODE_TYPE = 'FORCE_CANCEL_DC_CODE' and FC.REASON_CODE = CL.CODE_ID)" +
            "  where FC.ORDER_ID = O.ORDER_ID" +
            "  and FC.FALLOUT_CAT in ('ON_DOA', 'DEL_FAIL')" +
            " ) D_COUNT" +
            ", O.SHOP_CD" +
            ", O.LAST_UPDATE_DATE" +
            ", floor(sysdate - O.LAST_UPDATE_DATE) LAST_UPDATE_DATE_DIFF" +
            ", O.APP_DATE" +
            ", floor((sysdate - nvl(O.APP_DATE, sysdate)) * 24) APP_DATE_HR_DIFF" +
            " from (" +
            "  select F.ORDER_ID, max(F.OCCURANCE_DATE) MAX_FALLOUT_DATE" +
            "  from BOMWEB_ORDER_FALLOUT F, bomweb_code_lkup cl " +
            "  where f.reason_code = cl.code_id and cl.code_type ='FORCE_CANCEL_FO_CODE' group by F.ORDER_ID" +
            " ) FA" +
            " LEFT JOIN BOMWEB_ORDER O ON (FA.ORDER_ID = O.ORDER_ID)" +
            " WHERE O.ORDER_STATUS = :orderStatus" +
          	" and O.LOB = 'MOB'\n" +
          	"  and O.REASON_CD in\n" + 
          	"      (select CL.CODE_ID\n" + 
          	"         from BOMWEB_CODE_LKUP CL\n" + 
          	"        where CL.CODE_TYPE = 'FORCE_CANCEL_FO_CODE')\n" + 
          	" order by O.ORDER_ID";//add reason cd must in FORCE_CANCEL_FO_CODE

	
	public List<ForceCancelDTO> getForceCancelDTOALL(String orderStatus) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getForceCancelDTOALL() is called");
		}
		List<ForceCancelDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderStatus", orderStatus);
			if (logger.isInfoEnabled()) {
				logger.info("getForceCancelDTOALL() @ MobCcsForceCancelDAO: " + getForceCancelDTOALLSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getForceCancelDTOALLSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getForceCancelDTOALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getForceCancelDTOALL():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	private static final String getForceCancelExpireDTOALLSQL = "select" +
			" O.ORDER_ID" +
			" , O.OCID" +
			" , O.ORDER_STATUS" +
			" , O.CHECK_POINT" +
			" , O.REASON_CD" +
			" , null MAX_FALLOUT_DATE" +
			" , 0 FALLOUT_DATE_DIFF" +
			" , (" +
			"  select DECODE(count(*), 0, 'N', 'Y')" +
			"  from BOMWEB_HOTTEST_MODEL HM" +
			"  , BOMWEB_SUBSCRIBED_ITEM   SII" +
			"  , W_ITEM_PRODUCT_POS_ASSGN IPPA" +
            "  where SII.ORDER_ID = O.ORDER_ID" +
            "  and SII.ID = IPPA.ITEM_ID" +
            "  and IPPA.POS_ITEM_CD = HM.ITEM_CODE" +
            "  and TRUNC(O.APP_DATE) between HM.START_DATE and TRUNC(NVL(HM.END_DATE, sysdate))" +
            " ) HOTTEST_MODEL_IND" +
            ", 0 D_COUNT" +
            ", O.SHOP_CD" +
            ", O.LAST_UPDATE_DATE" +
            ", floor(sysdate - O.LAST_UPDATE_DATE) LAST_UPDATE_DATE_DIFF" +
            ", O.APP_DATE" +
            ", floor((sysdate - nvl(O.APP_DATE, sysdate)) * 24) APP_DATE_HR_DIFF" +
            " FROM BOMWEB_ORDER O" +
            " WHERE O.ORDER_TYPE = :orderType" +
            " AND O.ORDER_STATUS NOT IN ('03', '04')" +
            " AND O.LOB = 'MOB'" +
            " ORDER BY O.ORDER_ID";
	
	public List<ForceCancelDTO> getForceCancelExpireDTOALL(String orderType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("getForceCancelExpireDTOALL() is called");
		}
		List<ForceCancelDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderType", orderType);
			if (logger.isInfoEnabled()) {
				logger.info("getForceCancelExpireDTOALL() @ MobCcsForceCancelDAO: " + getForceCancelExpireDTOALLSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getForceCancelExpireDTOALLSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getForceCancelExpireDTOALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getForceCancelExpireDTOALL():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
		
	}
	
	public ParameterizedRowMapper<ForceCancelDTO> getRowMapper() {
		return new ParameterizedRowMapper<ForceCancelDTO>() {
			public ForceCancelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ForceCancelDTO dto = new ForceCancelDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setOcid(rs.getString("OCID"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				dto.setCheckPoint(rs.getString("CHECK_POINT"));
				dto.setReasonCd(rs.getString("REASON_CD"));
				dto.setMaxFalloutDate(rs.getDate("MAX_FALLOUT_DATE"));
				dto.setFalloutDateDiff(rs.getInt("FALLOUT_DATE_DIFF"));
				dto.setHottestModel("Y".equals(rs.getString("HOTTEST_MODEL_IND")));
				dto.setDeliveryFailCount(rs.getInt("D_COUNT"));
				dto.setShopCd(rs.getString("SHOP_CD"));
				dto.setLastUpdateDate(rs.getTimestamp("LAST_UPDATE_DATE"));
				dto.setLastUpdateDateDiff(rs.getInt("LAST_UPDATE_DATE_DIFF"));
				dto.setAppDate(rs.getTimestamp("APP_DATE"));
				dto.setAppDateHrDiff(rs.getInt("APP_DATE_HR_DIFF"));
				return dto;
			}
		};
	}
}
