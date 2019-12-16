package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.ImsSbOrderDetailDTO;
import com.bomwebportal.lts.dto.PcdOrderCheckAttrDTO;
import com.bomwebportal.lts.dto.order.LtsPendingSbOrderDTO;
import com.bomwebportal.lts.util.LtsProfileConstant;
import com.pccw.springboard.svc.server.dao.DaoException;
import com.pccw.springboard.svc.server.dto.OrderHistoryDTO;
import com.pccw.util.dao.ParameterizedRowMapperByFieldName;

public class SbOrderInfoLtsDAO extends BaseDAO {

	private static final String SQL_GET_SB_LTS_PEND_LATEST_ORD_BY_DN = 
		"select order_id " + 
		"from (select srv.order_id " +
			  "from bomweb_order ord, bomweb_order_service srv, bomweb_order_latest_status sts " +
			  "where ord.order_id = srv.order_id and srv.order_id = sts.order_id and srv.dtl_id = sts.dtl_id " +
			  "and sts.sb_status not in ('L', 'C') " + 
			  "and srv_num = ? and type_of_srv = ? " +
			  "order by app_date desc) " +
		"where rownum = 1";
	
	private static final String SQL_GET_LTS_SB_PENDING_ORDER = 
		"SELECT bo.order_id, bo.OCID, bo.order_type, bo.app_date, bols.SB_STATUS, wcl.description, " + 
		"  bos.ER_IND, boso.EDF_REF, ba.APPNT_START_TIME, ba.appnt_end_time, bos.force_field_visit_ind" +
		" FROM " +
		"  bomweb_order bo, " +
		"  bomweb_order_service bos, " +
		"  bomweb_order_service_other boso, " +
		"  bomweb_order_latest_status bols, " +
		"  bomweb_appointment ba, " +
		"  w_code_lkup wcl " +
		" WHERE bo.order_id = bos.order_id " +
		"  and bos.order_id = boso.order_id (+) " +
		"  and bos.order_id = bols.order_id " +
		"  and bos.dtl_id = bols.dtl_id " +
		"  and bols.sb_status not in ('C', 'L') " +
		"  and wcl.grp_id = 'SB_ORD_STATUS' " +
		"  and bols.sb_status = wcl.code " +
		"  and bos.srv_num = :srvNum " +
		"  and bos.type_of_srv = :typeOfSrv " +
		"  and (bos.type_of_srv = 'TEL' OR (bos.type_of_srv = 'IMS' AND bos.order_type not in ('I')) ) " +
		"  and bos.order_id = ba.order_id (+) " +
		"  and bos.dtl_id = ba.dtl_id (+) ";
		
	
	public String getSbLtsLatestPendingOrderBySrvNum(String pSrvNum, String pSrvType) throws DAOException {

		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_SB_LTS_PEND_LATEST_ORD_BY_DN, String.class, pSrvNum, pSrvType);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getSbLtsLatestPendingOrderBySrvNum()", e);
			throw new DAOException(SQL_GET_SB_LTS_PEND_LATEST_ORD_BY_DN + e.getMessage(), e);
		}
	}
	
	private static final String SQL_GET_LTS_SB_ORDER_HIST = 
			"select TO_CHAR(APP_DATE, 'dd/mm/yyyy HH24:mi:ss') \"applicationDate\", " +
            "       OCID \"bomOcid\", " + 
            "       CUST_NAME \"customerName\", " +
            "       ERR_MSG \"errMessage\", " +
            "       LOB \"lob\", " +
            "       wcl.description \"orderStatus\", " + 
            "       ORDER_ID \"sbOrderID\", " +
            "       SRV_NUM \"serviceNo\", " +
            "       TYPE_OF_SRV \"serviceType\" " +
            "  from bomweb_order_lts_search_v bolsv, w_code_lkup wcl " +
            " where wcl.GRP_ID = 'SB_ORD_STATUS' " +
            "   and code = bolsv.sb_status ";
	
	public List<OrderHistoryDTO> getSbOrderHistory(String pIdDocType, String pIdDocNum, String pServiceType, String pServiceNumber) throws DaoException {
		try {
			if (StringUtils.isNotBlank(pIdDocType) && StringUtils.isBlank(pServiceType)) {
				return simpleJdbcTemplate.query(SQL_GET_LTS_SB_ORDER_HIST + " AND ID_DOC_TYPE = ? AND ID_DOC_NUM = ? ", new ParameterizedRowMapperByFieldName<OrderHistoryDTO>(OrderHistoryDTO.class), pIdDocType, pIdDocNum);	
			} else if (StringUtils.isBlank(pIdDocType) && StringUtils.isNotBlank(pServiceType)) {
				return simpleJdbcTemplate.query(SQL_GET_LTS_SB_ORDER_HIST + " AND TYPE_OF_SRV = ? AND SRV_NUM = ? ", new ParameterizedRowMapperByFieldName<OrderHistoryDTO>(OrderHistoryDTO.class), pServiceType, pServiceNumber);	
			} else if (StringUtils.isBlank(pIdDocType) && StringUtils.isNotBlank(pServiceType)) {
				return simpleJdbcTemplate.query(SQL_GET_LTS_SB_ORDER_HIST + " AND ID_DOC_TYPE = ? AND ID_DOC_NUM = ? AND TYPE_OF_SRV = ? AND SRV_NUM = ? ", 
												new ParameterizedRowMapperByFieldName<OrderHistoryDTO>(OrderHistoryDTO.class), 
												pIdDocType, pIdDocNum, pServiceType, pServiceNumber);
			}
			throw new DAOException("INPUT PARAMETER ERROR - NOT ALLOW EMPTY INPUT - pIdDocType: " + pIdDocType + 
					" / pIdDocNum: " + pIdDocNum + " / pServiceType: " + pServiceType + 
					" / pServiceNumber: " + pServiceNumber);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getSLVCustSearchResult()");
			return null;	
		} catch (Exception e) {
			logger.info("Exception caught in getSLVCustSearchResult():", e);
			throw new DaoException(e.getMessage(), e);
		}		
	}
	
	public List<ImsPendingOrderDTO> getSbImsLatestPendingOrderBySrvNum(String pSrvNum) throws DAOException
	{
		logger.info("getSbImsLatestPendingOrderBySrvNum is called");
		logger.info("FSA:" + pSrvNum);
		
		List<ImsPendingOrderDTO> rtnList = new ArrayList<ImsPendingOrderDTO>();
		String pendOrderInd;
		
		try {			
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$cnm")
			.withCatalogName("pkg_IMS_ORDER")
			.withProcedureName("get_pending_order_by_fsa")
			.declareParameters(
					new SqlParameter("i_fsa", Types.VARCHAR),
					new SqlOutParameter("o_pending_order_ind", Types.VARCHAR),
					new SqlOutParameter("o_order_id_list", OracleTypes.CURSOR, new Mapper1()),
					new SqlOutParameter("gnretval", Types.INTEGER), 
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR)
					);
			 
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_fsa",  pSrvNum);
			
			Map out = jdbcCall.execute(in); 
			
			pendOrderInd = (String)out.get("o_pending_order_ind");
			
			if (pendOrderInd.equalsIgnoreCase("Y")){
				rtnList = (List)out.get("o_order_id_list");
			}else{
				rtnList = null;
			}
			
			return rtnList; 
			
		}catch (Exception e) {
			logger.error("Exception caught in getSbImsLatestPendingOrderBySrvNum()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public final class Mapper1 implements RowMapper {
	    
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	ImsPendingOrderDTO dto = new ImsPendingOrderDTO();
	    	dto.setOrderId(rs.getString("order_id"));
	    	dto.setOcId(rs.getString("ocid"));
	    	dto.setEdfRef(rs.getString("edf_ref"));
	    	dto.setErInd(rs.getString("er_ind"));
	    	dto.setSrdStart(rs.getString("srv_start_day"));
	    	dto.setSrdEnd(rs.getString("srv_end_day"));
	    	dto.setSbPendingInd("Y");
	    	dto.setBomPendingInd("N");
	    	dto.setBossPendingInd("N");
	    	dto.setL2OrderNum(null);
	    	dto.setOrderStatus(rs.getString("order_status_desc"));
	    	
	        return dto;
	    }
	}
	
	
	public List<LtsPendingSbOrderDTO> getPendingSbOrderList(String pSrvNum, String pSrvType) throws DAOException {
		try {
			return simpleJdbcTemplate.query(SQL_GET_LTS_SB_PENDING_ORDER,
					getLtsPendingSbOrderMapper(), StringUtils.equals("TEL",pSrvType) ? StringUtils.leftPad(pSrvNum, 12, "0")
							: pSrvNum, pSrvType);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getSbLtsLatestPendingOrderBySrvNum()", e);
			throw new DAOException(SQL_GET_LTS_SB_PENDING_ORDER + e.getMessage(), e);
		}
	}
	
	protected ParameterizedRowMapper<LtsPendingSbOrderDTO> getLtsPendingSbOrderMapper() {
		
		return new ParameterizedRowMapper<LtsPendingSbOrderDTO>() {
			public LtsPendingSbOrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LtsPendingSbOrderDTO ltsPendingSbOrder = new LtsPendingSbOrderDTO();
				ltsPendingSbOrder.setSbOrderId(rs.getString("ORDER_ID"));
				ltsPendingSbOrder.setBomOcId(rs.getString("OCID"));
				ltsPendingSbOrder.setAppDate(rs.getString("APP_DATE"));
				ltsPendingSbOrder.setSbStatusCd(rs.getString("SB_STATUS"));
				ltsPendingSbOrder.setSbStatusDesc(rs.getString("DESCRIPTION"));
				ltsPendingSbOrder.setErInd(rs.getString("ER_IND"));
				ltsPendingSbOrder.setEdfRefNum(rs.getString("EDF_REF"));
				ltsPendingSbOrder.setAppntStartTime(rs.getString("APPNT_START_TIME"));
				ltsPendingSbOrder.setAppntEndTime(rs.getString("APPNT_END_TIME"));
				ltsPendingSbOrder.setFieldVisitInd(rs.getString("force_field_visit_ind"));
				ltsPendingSbOrder.setOrderType(rs.getString("order_type"));
				return ltsPendingSbOrder;
			}
		};

	}
	
	public List<PcdOrderCheckAttrDTO> getPcdSbOrderDetails(String orderId) throws DAOException { 
		List<PcdOrderCheckAttrDTO> result = new ArrayList<PcdOrderCheckAttrDTO>();
		try {			
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$cnm")
			.withCatalogName("pkg_SB_IMS_LTS")
			.withProcedureName("get_pcd_sb_order_detail")
			.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("order_detail_cursor", OracleTypes.CURSOR, new PcdOrderCheckMapper1()),
					new SqlOutParameter("gnRetVal", Types.INTEGER), 
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR)
					);
			 
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_order_id",  orderId);
			
			Map out = jdbcCall.execute(in); 			

			if(out.get("gnErrCode") != null){
				logger.error("getPcdSbOrderHasDel gnErrCode:" + out.get("gnErrCode"));
			}
			if(out.get("gsErrText") != null){
				logger.error("getPcdSbOrderHasDel gsErrText:" + out.get("gsErrText"));
			}
	
			result = (List)out.get("order_detail_cursor");
			
			if(result!=null && result.size()>=1){
				return result;
			}else{
				return null;
			}
			
		}catch (Exception e) {
			logger.error("Exception caught in getPcdSbOrderHasDel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getPcdSbOrderHasDel(String orderId, String pcdBundleFreeType) throws DAOException
	{
		logger.info("getPcdSbOrder is called");
		logger.info("Order Id:" + orderId + ", pcdBundleFreeType:" + pcdBundleFreeType);
		
		List<PcdOrderCheckAttrDTO> result = new ArrayList<PcdOrderCheckAttrDTO>();
		try {			
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$cnm")
			.withCatalogName("pkg_SB_IMS_LTS")
			.withProcedureName("get_pcd_sb_order_detail")
			.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("order_detail_cursor", OracleTypes.CURSOR, new PcdOrderCheckMapper1()),
					new SqlOutParameter("gnRetVal", Types.INTEGER), 
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR)
					);
			 
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_order_id",  orderId);
			
			Map out = jdbcCall.execute(in); 			

			if(out.get("gnErrCode") != null){
				logger.error("getPcdSbOrderHasDel gnErrCode:" + out.get("gnErrCode"));
			}
			if(out.get("gsErrText") != null){
				logger.error("getPcdSbOrderHasDel gsErrText:" + out.get("gsErrText"));
			}
	
			result = (List)out.get("order_detail_cursor");
			String targetFreeType = "has_del_vas";
			if(StringUtils.isNotBlank(pcdBundleFreeType)){
				targetFreeType = pcdBundleFreeType;
			}
			
			if(result!=null && result.size()>=1)
			{
				String returnVal = null;
				for (int i=0; i<result.size(); i++)
				{
					PcdOrderCheckAttrDTO tempDto = result.get(i);
					if( targetFreeType.equalsIgnoreCase(tempDto.getCheckAttribute()) && "Y".equalsIgnoreCase(tempDto.getResult()) )
					{
						returnVal = "Y";
					}					
				}				
				return returnVal;
			}
			else
			{
				return null;
			}
			
		}catch (Exception e) {
			logger.error("Exception caught in getPcdSbOrderHasDel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getPcdActivationDate(String orderId) throws DAOException{

		String pcdActivationDate = null;
		List<PcdOrderCheckAttrDTO> pcdOrderCheckAttrList = new ArrayList<PcdOrderCheckAttrDTO>();
					
		try {
			pcdOrderCheckAttrList = getPcdSbOrderDetails(orderId);
		
		if(pcdOrderCheckAttrList != null){
			for(PcdOrderCheckAttrDTO attr : pcdOrderCheckAttrList){
				if("ACTIVATION_DATE".equalsIgnoreCase(attr.getCheckAttribute())){
					pcdActivationDate = attr.getResult();
				}
			}
		}
		
		return pcdActivationDate;
		
		} catch (DAOException e) {
			logger.error("Exception caught in getPcdActivationDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public ImsSbOrderDetailDTO getPcdSbOrder(String orderId) throws DAOException
	{
		logger.info("getPcdSbOrder is called");
		logger.info("Order Id:" + orderId);
		
		List<ImsSbOrderDetailDTO> result = new ArrayList<ImsSbOrderDetailDTO>();
		try {			
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("ops$cnm")
			.withCatalogName("pkg_SB_IMS_LTS")
			.withProcedureName("get_pcd_sb_order")
			.declareParameters(
					new SqlParameter("i_order_id", Types.VARCHAR),
					new SqlOutParameter("order_detail_cursor", OracleTypes.CURSOR, new ImsSbOrderMapper1()),
					new SqlOutParameter("gnretval", Types.INTEGER), 
					new SqlOutParameter("gnerrcode", Types.INTEGER),
					new SqlOutParameter("gserrtext", Types.VARCHAR)
					);
			 
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("i_order_id",  orderId);
			
			Map out = jdbcCall.execute(in); 
			

			if(out.get("gnerrcode") != null){
				logger.error(out.get("gnerrcode"));
			}
			if(out.get("gserrtext") != null){
				logger.error(out.get("gserrtext"));
			}
	
			result = (List)out.get("order_detail_cursor");

			if(result!=null && result.size()>=1)
			{
				ImsSbOrderDetailDTO tempDto = new ImsSbOrderDetailDTO();
				tempDto = result.get(0);
				
				String tempStr = getPcdSbOrderHasDel(orderId, "");				
				if (tempStr == null) {
					tempStr = "N";
				}
				tempDto.setHasDel(tempStr);
				
				
				tempDto.setHasRentalRouter("N");
				tempDto.setHasMeshRouter("N");
				List<PcdOrderCheckAttrDTO> pcdOrderAttrs = getPcdSbOrderDetails(orderId);
				if(pcdOrderAttrs != null){
					for(PcdOrderCheckAttrDTO attr : pcdOrderAttrs){
						if(LtsProfileConstant.PCD_ATTB_HAS_RENTAL_ROUTER.equalsIgnoreCase(attr.getCheckAttribute())){
							tempDto.setHasRentalRouter(attr.getResult());
						}else if(LtsProfileConstant.PCD_ATTB_HAS_MESH_ROUTER.equalsIgnoreCase(attr.getCheckAttribute())){
							tempDto.setHasMeshRouter(attr.getResult());
						}else if(LtsProfileConstant.PCD_ATTB_HAS_BRM_WIFI.equalsIgnoreCase(attr.getCheckAttribute())){
							tempDto.setHasBrmWifi(attr.getResult()); 
						}
					}
				}
				
				return tempDto;
			}
			else
			{
				return null;
			}
			
		}catch (Exception e) {
			logger.error("Exception caught in getPcdSbOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public final class PcdOrderCheckMapper1 implements RowMapper {
	    
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	PcdOrderCheckAttrDTO dto = new PcdOrderCheckAttrDTO();
	    	dto.setCheckAttribute(rs.getString("check_attribute"));
	    	dto.setResult(rs.getString("Result"));	        
	        return dto;
	    }
	}
	
	public final class ImsSbOrderMapper1 implements RowMapper {
	    
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	ImsSbOrderDetailDTO dto = new ImsSbOrderDetailDTO();
	    	dto.setOrderId(rs.getString("order_id"));
	    	dto.setIdDocType(rs.getString("id_doc_type"));
	    	dto.setIdDocNum(rs.getString("id_doc_num"));
	    	dto.setSerbdyno(rs.getString("serbdyno"));
	    	dto.setUnitNo(rs.getString("unit_no"));
	    	dto.setFloorNo(rs.getString("floor_no"));
	        dto.setHiLotNo(rs.getString("hi_lot_no"));
	        dto.setBuildNo(rs.getString("build_no"));
	        dto.setStrNo(rs.getString("str_no"));
	        dto.setStrName(rs.getString("str_name"));
	        dto.setStrCatCd(rs.getString("str_cat_cd"));
	        dto.setStrCatDesc(rs.getString("str_cat_desc"));
	        dto.setSectCd(rs.getString("sect_cd"));
	        dto.setSectDesc(rs.getString("sect_desc"));
	        dto.setDistNo(rs.getString("dist_no"));
	        dto.setDistDesc(rs.getString("dist_desc"));
	        dto.setAreaCd(rs.getString("area_cd"));
	        dto.setAreaDesc(rs.getString("area_desc"));
	        dto.setSerialNum(rs.getString("serial_num"));
	        dto.setInstContactName(rs.getString("inst_contact_name"));
	        dto.setInstContactNum(rs.getString("inst_contact_num"));
	        dto.setInstSmsNum(rs.getString("inst_sms_num"));
	        dto.setSrvType(rs.getString("srv_type"));
	        dto.setBandwidth(rs.getString("bandwidth"));
	        dto.setTechnology(rs.getString("technology"));
	        dto.setSrvReqDate(rs.getDate("srv_req_date"));
	        dto.setAppntType(rs.getString("appnt_type"));
	        dto.setAppntStartTime(rs.getTimestamp("appnt_start_time"));
	        dto.setAppntEndTime(rs.getTimestamp("appnt_end_time"));
	    	dto.setHasDel(rs.getString("has_del"));
	        return dto;
	    }
	}
	
	
}