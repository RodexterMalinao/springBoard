package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AllOrdDocDmsDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO.DmsInd;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;

public class SupportDocAdminDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());

	
	private ParameterizedRowMapper<OrdDocAssgnAdminDTO> ordDocAssgnAdminDTOMapper = new ParameterizedRowMapper<OrdDocAssgnAdminDTO>() {
		public OrdDocAssgnAdminDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			OrdDocAssgnAdminDTO dto = new OrdDocAssgnAdminDTO();
			dto.setOrderId(rs.getString("order_id"));
			dto.setDocType(rs.getString("doc_type"));
			dto.setDocName(rs.getString("doc_name"));
			dto.setDocNameChi(rs.getString("doc_name_chi"));
			dto.setCollectedInd(rs.getString("collected_ind"));
			dto.setLastUpdBy(rs.getString("last_upd_by"));
			dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
			dto.setWaiveReason(rs.getString("waive_reason"));
			dto.setWaivedBy(rs.getString("waived_by"));
			dto.setWaiveDesc(rs.getString("waive_desc"));
			dto.setShopCd(rs.getString("shop_cd"));
			dto.setLob(rs.getString("lob"));
			dto.setCollectMethod(rs.getString("collect_method"));
			dto.setDisMode(rs.getString("dis_mode"));
			String dmsInd = rs.getString("dms_ind");
			if (StringUtils.isNotBlank(dmsInd)) {
				dto.setDmsInd(DmsInd.valueOf(dmsInd));
			}
			return dto;
		}
	};

	private ParameterizedRowMapper<AllOrdDocDmsDTO> allOrdDocDmsDTOMapper = new ParameterizedRowMapper<AllOrdDocDmsDTO>() {
		public AllOrdDocDmsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

			AllOrdDocDmsDTO dto = new AllOrdDocDmsDTO();
			dto.setProcessDate(rs.getTimestamp("process_date"));
			dto.setOrderId(rs.getString("order_id"));
			dto.setMsisdn(rs.getString("msisdn"));
			dto.setIdDocNum(rs.getString("id_doc_num"));
			dto.setDmsFileName(rs.getString("dms_file_name"));
			dto.setMergedFileList(rs.getString("merged_file_list"));
			dto.setProcessStatus(rs.getString("process_status"));
			dto.setCreateBy(rs.getString("create_by"));
			dto.setCreateDate(rs.getTimestamp("create_date"));
			dto.setLastUpdBy(rs.getString("last_upd_by"));
			dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
			dto.setLob(rs.getString("lob"));
			dto.setRowId(rs.getString("ROW_ID"));
			return dto;
		}
	};

	// null for "all"
	// start from 1
	// end inclusive
	// end <= 0 for no limit..
	// collected = 'N' , only not-waived will be selected.
	public List<OrdDocAssgnAdminDTO> findDocAssigned(String shopCd, String lob
			, Date startDate, Date endDate
			, String orderId
			, CollectMethod collectMethod, DmsInd dmsInd
			, int start, int end)
		throws DAOException {
		if (!StringUtils.isEmpty(orderId)) {
			lob = null;
		}
		logger.debug("findDocAssigned: shopCd="+shopCd
				+",lob="+lob+",startDate="+startDate+",endDate="+endDate
				+",orderId="+orderId
				+",collectMtd="+collectMethod+",dmsIn="+dmsInd);
		

		String SELECT_SQL = "SELECT \n"
			+ "   oa.*, w.waive_desc, t.doc_name, t.doc_name_chi \n"
			+ " FROM \n"
			+ "   ( SELECT o.shop_cd, o.lob, o.collect_method, o.dis_mode, o.app_date, \n"
			+ "       a.* \n"
			+ "      , o.dms_ind "
			+ "     FROM bomweb_order o, bomweb_all_ord_doc_assgn a \n"
			+ "     WHERE o.order_id=a.order_id ) oa, \n "
			+ "   bomweb_all_doc t, \n"
			+ "   bomweb_all_ord_doc_waive w \n"
			+ " WHERE \n"
			+ "   oa.doc_type=t.doc_type \n"
			+ "   AND oa.lob=t.lob \n"
			+ "   AND oa.lob=w.lob(+) \n"
			+ "   AND oa.waive_reason=w.waive_reason(+) \n";
			if (collectMethod != null) {
				SELECT_SQL += "     AND oa.collect_method = :collectMethod";
			}
			if (dmsInd != null) {
				SELECT_SQL += "     AND NVL(oa.dms_ind,'N') = :dmsInd";
			}
			SELECT_SQL += ("   AND (NVL(:lob, 'ALL')='ALL' OR oa.lob=:lob) \n"
			+ "   AND NVL(oa.mark_del_ind,'N') <> 'Y' \n"
			+ "   AND (NVL(:shopCd, 'ALL')='ALL' OR oa.shop_cd=:shopCd) \n"
			//+ "   AND oa.shop_cd in (SELECT DISTINCT SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID='1') \n"

			+ "   AND (  oa.order_id=:orderId \n"
			+ "        OR ( NVL(:orderId, '*')='*' AND \n" 
			+ "           TRUNC(oa.app_date) between --range search \n"
			+ "           TRUNC(:startDate) AND \n"
			+ "           TRUNC(:endDate)) \n" 
			+ "   ) \n"
			//+ "   AND ( NVL(:collectedInd, '*')='*' \n"
			//+ "         OR NVL(oa.collected_ind,'N')=:collectedInd) \n"
			//+ "   AND ( NVL(:collectedInd, '*') <> 'N' OR oa.waive_reason IS NULL) \n"
			+ " ORDER BY oa.shop_cd, oa.order_id, oa.doc_type");
		
		
		
		
		final String PAGING_SQL = "SELECT * \n"
			+ "  FROM (  SELECT result.*, rownum rnum \n"
			+ "            FROM ( " + SELECT_SQL + " ) result \n"
			+ "       ) \n"
			+ "  WHERE rnum >= :start \n"
			+ "    AND (:end <= 0 OR rnum <= :end)";
		
		List<OrdDocAssgnAdminDTO> list = new ArrayList<OrdDocAssgnAdminDTO>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("shopCd", shopCd);
		params.addValue("lob", lob);
		params.addValue("orderId", orderId);
		params.addValue("startDate", startDate, Types.DATE);
		params.addValue("endDate", endDate, Types.DATE);
		//params.addValue("collectedInd", collected);
		params.addValue("start", start);
		params.addValue("end", end);
		if (collectMethod != null) {
			params.addValue("collectMethod", collectMethod.toString());
		}
		if (dmsInd != null) {
			params.addValue("dmsInd", dmsInd.toString());
		}
		
		try {
			logger.debug("findDocAssigned @ SupportDocAdminDAO: \n" + PAGING_SQL);

			list = simpleJdbcTemplate.query(PAGING_SQL, ordDocAssgnAdminDTOMapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in findDocAssigned()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;

	}
	
	public long countDocAssigned(String shopCd, String lob, Date startDate, Date endDate, String orderId
			, CollectMethod collectMethod, DmsInd dmsInd) throws DAOException {
		if (!StringUtils.isEmpty(orderId)) {
			lob = null;
		}
		String SELECT_SQL = "SELECT \n"
			+ "   count(*) c \n"
			+ " FROM \n"
			+ "   ( SELECT o.shop_cd, o.lob, o.collect_method, o.dis_mode, o.app_date, \n"
			+ "       a.* \n"
			+ "      , o.dms_ind "
			+ "     FROM bomweb_order o, bomweb_all_ord_doc_assgn a \n"
			+ "     WHERE o.order_id=a.order_id ) oa, \n "
			+ "   bomweb_all_doc t, \n"
			+ "   bomweb_all_ord_doc_waive w \n"
			+ " WHERE \n"
			+ "   oa.doc_type=t.doc_type \n"
			+ "   AND oa.lob=t.lob \n"
			+ "   AND oa.lob=w.lob(+) \n"
			+ "   AND oa.waive_reason=w.waive_reason(+) \n";
			if (collectMethod != null) {
				SELECT_SQL += "     AND oa.collect_method = :collectMethod";
			}
			if (dmsInd != null) {
				SELECT_SQL += "     AND NVL(oa.dms_ind,'N') = :dmsInd";
			}
			SELECT_SQL += ("   AND (NVL(:lob, 'ALL')='ALL' OR oa.lob=:lob) \n"
			+ "   AND NVL(oa.mark_del_ind,'N') <> 'Y' \n"
			+ "   AND (NVL(:shopCd, 'ALL')='ALL' OR oa.shop_cd=:shopCd) \n"
			//+ "   AND oa.shop_cd in (SELECT DISTINCT BOM_SHOP_CD FROM BOMWEB_SHOP WHERE CHANNEL_ID in ('1', '3')) \n"			
			+ "   AND (  oa.order_id=:orderId \n"
			+ "        OR ( NVL(:orderId, '*')='*' AND \n" 
			+ "           TRUNC(oa.app_date) between --range search \n"
			+ "           TRUNC(:startDate) AND \n"
			+ "           TRUNC(:endDate)) \n" 
			+ "   ) \n"
			//+ "   AND ( NVL(:collectedInd, '*')='*' \n"
			//+ "         OR NVL(oa.collected_ind,'N')=:collectedInd) \n"
			//+ "   AND ( NVL(:collectedInd, '*') <> 'N' OR oa.waive_reason IS NULL) \n"
			+ " ORDER BY oa.shop_cd, oa.order_id, oa.doc_type");
		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("shopCd", shopCd);
		params.addValue("lob", lob);
		params.addValue("orderId", orderId);
		params.addValue("startDate", startDate, Types.DATE);
		params.addValue("endDate", endDate, Types.DATE);
		//params.addValue("collectedInd", collectedInd);
		if (collectMethod != null) {
			params.addValue("collectMethod", collectMethod.toString());
		}
		if (dmsInd != null) {
			params.addValue("dmsInd", dmsInd.toString());
		}
		
		try {
			logger.debug("countDocAssigned @ SupportDocAdminDAO: " + SELECT_SQL);
			
			return simpleJdbcTemplate.queryForLong(SELECT_SQL, params);

		} catch (Exception e) {
			logger.error("Exception caught in countDocAssigned()", e);
			throw new DAOException(e.getMessage(), e);
		}


	}
	
	
	public List<OrdDocAssgnAdminDTO> getRequiredProofTypes(String orderId) throws DAOException {
		Date startDate = null, endDate = null;
		CollectMethod collectMethod = null;
		DmsInd dmsInd = null;
		return findDocAssigned(null, null, startDate, endDate, orderId, collectMethod, dmsInd, 0, 0);
		/*
		String SQL = "SELECT \n"
			+ "   o.order_id, o.shop_cd, o.lob, o.collect_method, o.dis_mode, \n"
			+ "   a.doc_type, a.collected_ind, a.last_upd_by, a.last_upd_date, \n"
			+ "   a.waive_reason, a.waived_by, w.waive_desc, \n"
			+ "   d.doc_name, d.doc_name_chi \n"
			+ "   FROM bomweb_order o, bomweb_all_ord_doc_assgn a, bomweb_all_doc d, bomweb_all_ord_doc_waive w \n"
			+ " WHERE \n"
			+ "   a.doc_type=d.doc_type \n"
			//+ " AND a.doc_type=w.doc_type "
			+ "   AND a.waive_reason=w.waive_reason(+) \n"
			+ "   AND a.order_id=o.order_id \n"
			+ "   AND o.lob=d.lob \n"
			+ "   AND o.lob=w.lob \n"
			+ "   AND NVL(a.mark_del_ind,'N') <> 'Y' \n"
			+ "   AND a.order_id=:orderId \n"
			+ " ORDER BY a.order_id, a.doc_type"
			;
		
		List<OrdDocAssgnAdminDTO> list = new ArrayList<OrdDocAssgnAdminDTO>();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);

		try {
			logger.debug("getRequiredProofTypes @ SupportDocAdminDAO: \n" + SQL);

			list = simpleJdbcTemplate.query(SQL, ordDocAssgnAdminDTOMapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredProofTypes()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	*/
	}
	
	public int insertOrdDocAssgn(OrdDocAssgnDTO dto) throws DAOException {
		String SQL = "INSERT INTO bomweb_all_ord_doc_assgn ("
			+ " order_id, doc_type, waived_by, waive_reason, collected_ind, mark_del_ind, "
			+ " create_by, create_date, last_upd_by, last_upd_date ) "
			+ " VALUES ( "
			+ " :orderId, :docType, :waivedBy, :waiveReason, :collectedInd, :markDelInd, "
			+ " :createdBy, sysdate, :lastUpdBy, sysdate)";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("docType", dto.getDocType());
			params.addValue("waivedBy", dto.getWaivedBy());
			params.addValue("waiveReason", dto.getWaiveReason());
			params.addValue("collectedInd", dto.getCollectedInd()==null?null:dto.getCollectedInd());
			params.addValue("markDelInd", null);
			params.addValue("createdBy", dto.getLastUpdBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateWaiveReason(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int updateOrdDocAssgn(OrdDocAssgnDTO dto) throws DAOException {
		String SQL = "UPDATE bomweb_all_ord_doc_assgn "
			+ " SET waived_by=:waivedBy,"
			+ " waive_reason=:waiveReason,"
			+ " collected_ind=:collectedInd,"
			+ " last_upd_by=:lastUpdBy,"
			+ " last_upd_date=sysdate "
			+ " WHERE "
			+ " order_id=:orderId "
			+ " AND doc_type=:docType "
			+ " AND NVL(mark_del_ind,'N') <> 'Y' ";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("docType", dto.getDocType());
			params.addValue("waivedBy", dto.getWaivedBy());
			params.addValue("waiveReason", dto.getWaiveReason());
			params.addValue("collectedInd", dto.getCollectedInd()==null?null:dto.getCollectedInd());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateWaiveReason(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int updateWaiveReason(String orderId, String docType, String waiveReason, String waivedBy) throws DAOException {
		String SQL = "UPDATE bomweb_all_ord_doc_assgn "
			+ " SET waive_reason=:waiveReason, "
			+ " waived_by=:waivedBy,"
			+ " last_upd_by=:lastUpdBy, "
			+ " last_upd_date=sysdate "
			+ " WHERE "
			+ " order_id=:orderId "
			+ " AND doc_type=:docType "
			+ " AND NVL(mark_del_ind,'N') <> 'Y' ";

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);
			params.addValue("lastUpdBy", waivedBy);
			params.addValue("waivedBy", waivedBy);
			params.addValue("waiveReason", waiveReason);
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateWaiveReason(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public int markDeleteDocAssgn(String orderId, String docType, String lastUpdBy) throws DAOException {
		String SQL = "UPDATE bomweb_all_ord_doc_assgn a "
			+ " SET mark_del_ind='Y', "
			+ " last_upd_by=:lastUpdBy, "
			+ " last_upd_date=sysdate"
			+ " WHERE "
			+ " order_id=:orderId "
			+ " AND doc_type=:docType "
			+ " AND NVL(mark_del_ind,'N') <> 'Y' ";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			params.addValue("docType", docType);
			params.addValue("lastUpdBy", lastUpdBy);
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in markDeleteDocAssgn(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<AllOrdDocDmsDTO> findAllOrdDocDmsDTO(Date processDate, String status, String orderId, String lob) throws DAOException {
		String SQL = "SELECT \n "
			+ "  process_date, order_id, msisdn, id_doc_num, dms_file_name, \n "
			+ "  merged_file_list, process_status, create_by, create_date, \n "
			+ "  last_upd_by, last_upd_date, lob, ROWID AS ROW_ID \n "
			+ "FROM bomweb_all_ord_doc_dms \n "
			+ "WHERE \n "
			+ "  ( :processDate IS NULL OR TRUNC(:processDate)=TRUNC(process_date) ) \n "
			+ "  AND (NVL(:status, '*')='*' OR process_status=:status) \n "
			+ "  AND (NVL(:orderId, '*')='*' OR order_id=:orderId) \n "
			+ "  AND (NVL(:lob, 'ALL')='ALL' OR lob=:lob) ";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("processDate", processDate);
			params.addValue("orderId", orderId);
			params.addValue("status", status);
			params.addValue("lob", lob);
			return simpleJdbcTemplate.query(SQL, allOrdDocDmsDTOMapper, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in findAllOrdDocDmsDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int insertOrdDocDms(AllOrdDocDmsDTO dto) throws DAOException {
		String SQL = "INSERT INTO bomweb_all_ord_doc_dms ("
			+ " process_date, order_id, msisdn, id_doc_num, dms_file_name, merged_file_list, "
			+ " process_status, create_by, create_date, last_upd_by, last_upd_date, lob ) "
			+ " VALUES ( "
			+ " :processDate, :orderId, :msisdn, :idDocNum, :dmsFileName, :mergedFileList, "
			+ " :processStatus, :createdBy, sysdate, :lastUpdBy, sysdate, :lob)";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("processDate", dto.getProcessDate());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("msisdn", dto.getMsisdn());
			params.addValue("idDocNum", dto.getIdDocNum());
			params.addValue("dmsFileName", dto.getDmsFileName());
			params.addValue("mergedFileList", dto.getMergedFileList());
			params.addValue("processStatus", dto.getProcessStatus());
			params.addValue("createdBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("lob", dto.getLob());
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in insertOrdDocDms(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public int prepareOrdDocDmsForProcessing(String lob, String updatedBy) throws DAOException {
		String SQL = "INSERT INTO \n "
			+ " bomweb_all_ord_doc_dms ( \n "
			+ " process_date, order_id, msisdn, id_doc_num, dms_file_name, merged_file_list, \n "
			+ " process_status, create_by, create_date, last_upd_by, last_upd_date, lob ) \n "
			+ " 	select sysdate, o.order_id, o.msisdn, c.id_doc_num, NULL, NULL, \n "
			+ "			'INITIAL', :updatedBy, sysdate, :updatedBy, sysdate, o.lob \n "
			+ " 	from bomweb_order o, bomweb_customer c \n "
			+ "			where o.order_id=c.order_id \n "
			+ "			AND (NVL(:lob, 'ALL')='ALL' OR o.lob=:lob) \n "
			+ "			and o.shop_cd in (select distinct s.bom_shop_cd from bomweb_shop s where s.channel_id in ('1', '3')) \n "
			+ "			and o.order_status=:orderStatus \n "
			+ "			and o.dis_mode='E' \n "
			+ "			and o.COLLECT_METHOD='D' \n "
			+ "			and NVL(o.dms_ind,'N') <> 'Y' \n " 
			+ "			and exists ( select 1 from bomweb_all_ord_doc_assgn a \n "
			+ "				where o.order_id=a.order_id and a.waive_reason is null ) \n " 
			+ "			and not exists ( select 1 from bomweb_all_ord_doc_assgn a \n "
			+ "				where  o.order_id = a.order_id \n "
			+ "				and a.waive_reason is null \n "
			+ "				and NVL(a.collected_ind, 'N') <> 'Y' ) ";

		
		try {
			logger.debug("prepareOrdDocDmsForProcessing(): SQL="+SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("updatedBy", updatedBy);
			params.addValue("orderStatus", BomWebPortalConstant.BWP_ORDER_SUCCESS);
			params.addValue("lob", lob);
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in prepareOrdDocDmsForProcessing(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}	
	}
	
	
	public List<AllOrdDocDmsDTO> findOrderForDmsPreprocessing(String lob) throws DAOException {
		String SQL = "select sysdate process_date, o.order_id, o.msisdn, c.id_doc_num, \n "
			+ " NULL dms_file_name, NULL merged_file_list, :status process_status, \n "
			+ " :updateBy create_by, sysdate create_date, :updateBy last_upd_by, sysdate last_upd_date, o.lob, NULL ROW_ID \n "
			+ " 	from bomweb_order o, bomweb_customer c \n "
			+ "			where o.order_id=c.order_id \n "
			+ "			AND (NVL(:lob, 'ALL')='ALL' OR o.lob=:lob) \n "
			+ "			and o.shop_cd in (select distinct s.bom_shop_cd from bomweb_shop s where s.channel_id in ('1', '3')) \n "
			+ "			and o.order_status=:orderStatus \n "
			+ "			and o.dis_mode='E' \n "
			+ "			and o.COLLECT_METHOD='D' \n "
			+ "			and NVL(o.dms_ind,'N') <> 'Y' \n " 
			+ "			and exists ( select 1 from bomweb_all_ord_doc_assgn a \n "
			+ "				where o.order_id=a.order_id and a.waive_reason is null ) \n " 
			+ "			and not exists ( select 1 from bomweb_all_ord_doc_assgn a \n "
			+ "				where  o.order_id = a.order_id \n "
			+ "				and a.waive_reason is null \n "
			+ "				and NVL(a.collected_ind, 'N') <> 'Y' ) ";

		try {
			logger.debug("findOrderForDmsPreprocessing(): SQL="+SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("status", "INITIAL");
			params.addValue("updateBy", "SYSTEM");
			params.addValue("orderStatus", BomWebPortalConstant.BWP_ORDER_SUCCESS);
			params.addValue("lob", lob);
			return simpleJdbcTemplate.query(SQL, allOrdDocDmsDTOMapper, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in findOrderForDmsPreprocessing(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public int updateOrdDocDms(AllOrdDocDmsDTO dto) throws DAOException {
		String SQL = "UPDATE bomweb_all_ord_doc_dms d \n "
			+ "  SET dms_file_name=:dmsFileName, \n "
			+ "    merged_file_list=:mergedFileList, \n "
			+ "    process_status=:processStatus, \n "
			+ "    last_upd_by=:lastUpdBy, \n "
			+ "    last_upd_date=sysdate \n "
			+ "  WHERE \n "
			+ "    TRUNC(process_date)=TRUNC(:processDate) \n "
			+ "    AND order_id=:orderId \n "
			+ "    AND process_status=:origProcessStatus";
		
		try {
			logger.debug("updateOrdDocDms(): SQL="+SQL);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("dmsFileName", dto.getDmsFileName());
			params.addValue("mergedFileList", dto.getMergedFileList());
			params.addValue("processStatus", dto.getProcessStatus());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("processDate", dto.getProcessDate());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("origProcessStatus", "INITIAL");
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateOrdDocDms(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}	
			
	}
	
}
