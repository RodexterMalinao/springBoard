package smartliving.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import smartliving.backend.dto.DocWaiveReasonDTO;
import smartliving.backend.dto.OrdDocSLVDTO;
import smartliving.backend.dto.OrderDocDTO;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.OrdDocDTO.VerifyInd;
import com.bomwebportal.exception.DAOException;
import com.pccw.util.db.OracleSelectHelper;

public class ActivityDocumentDAOImpl extends BaseDAO implements ActivityDocumentDAO {

	private static final String SQL_GET_ALL_DOC_BY_ACTV = 
			" select ass.order_id, ass.doc_type doc_type, ass.collected_ind,"+
			" dt.doc_name, dt.doc_name_chi, dt.last_upd_by, dt.last_upd_date"+ 
			" from bomweb_all_ord_doc_assgn ass, bomweb_all_doc dt"+
			" where ass.doc_type=dt.doc_type "+
			" and ass.order_id = ? "+
			" and nvl(ass.mark_del_ind,'N') <> 'Y'"+ 
			" and ass.waive_reason is null "+
			" order by ass.doc_type";
	
	private static final String SQL_GET_ALL_DOC_BY_TYPE = 
			" select distinct doc_type, doc_name, upload_method from w_slv_support_doc_v where type = ? ";
	
	private static final String SQL_GET_ALL_DOC = 
			" select null order_id, doc_type doc_type, null collected_ind,"+
			" doc_name, doc_name_chi, last_upd_by, last_upd_date, "+
			" null verify_ind,null verify_by,null verify_date"+
			" from bomweb_all_doc"+
			" where lob = 'SLV'"+
			" and type = ? ";

	private static final String SQL_GET_ALL_DOC_BY_ACTV_TASK_TYPE = 
			" select AD.DOC_TYPE \"docType\", " +
			"        ADME.MDO_IND \"mdoInd\", " +
			"        AD.type \"type\", " +
			"        AD.DOC_NAME \"docName\", " +
			"        AD.DOC_NAME_CHI \"docNameChi\", " +
			"        AD.DOC_DESC \"docDesc\", " +
			"        ADME.UPLOAD_METHOD \"uploadMethod\", " +
			"        AD.START_DATE \"startDate\", " +
			"        AD.END_DATE \"endDate\" " +
			"   from SB_ACTV_DOC_ASSGN SADA, BOMWEB_ALL_DOC AD, BOMWEB_ALL_DOC_MDO_EXT ADME " +
			"  where SADA.ACTV_TYPE = ? " +
			"    and SADA.TASK_TYPE = nvl(? , '*') " +
			"    and SADA.DOC_TYPE = AD.DOC_TYPE " +
			"    and AD.lob = 'SLV' " +
			"    and AD.DOC_TYPE = ADME.DOC_TYPE " +
			"    and ADME.MDO_KEY_TYPE = 'CHANNEL_ID' " +
			"    AND ( ADME.MDO_KEY_VALUE = '*' OR ADME.MDO_KEY_VALUE = ?) ";
	
	
	private static final String SQL_GET_LAST_SEQ = 
			"select max(seq_num) \n"
			+ " from bomweb_all_ord_doc \n"
			+ " where order_id=? and doc_type=?";
	
	private static final String SQL_INSERT_ORD_DOC = 
			"insert into bomweb_all_ord_doc ( \n"
			+ " order_id, doc_type, seq_num, file_path_name, linked_path, doc_desc, \n"
			+ " create_by, create_date, last_upd_by, last_upd_date, serial )\n"
			+ " values \n"
			+ " ( ?, ? ,? , ?, ?, ?, ?, sysdate, ?, sysdate, ? )";

	private static final String SQL_UPDATE_WAIVE_REASON = 
			"update bomweb_all_ord_doc_assgn \n"
			+ " set waive_reason = ?, \n"
			+ " waived_by = ?, \n"					
			+ " last_upd_by = ?, last_upd_date=sysdate \n"
			+ " where order_id=? \n"
			+ " and doc_type=? \n"
			+ " and nvl(mark_del_ind,'N') <> 'Y'";
	
	private static final String SQL_MARK_COLLECT_IND = 
			"update bomweb_all_ord_doc_assgn \n"
			+ " set collected_ind=?, \n"
			+ " last_upd_by=?, last_upd_date=sysdate \n"
			+ " where order_id=? \n"
			+ " and doc_type=? \n"
			+ " and nvl(mark_del_ind,'N') <> 'Y'";
	
	
	private static final String SQL_GET_CAPTURED_ORD_DOC = 
			" select od.order_id, od.doc_type doc_type, od.seq_num,"+
			" od.file_path_name, od.create_by, od.create_date,"+
			" dt.doc_name, dt.doc_name_chi"+
			" , od.last_upd_by, od.last_upd_date"+
			" , od.verify_ind, od.verify_by, od.verify_date, od.doc_desc, od.serial"+
			" from bomweb_all_ord_doc od, bomweb_all_doc dt"+
			" where od.doc_type=dt.doc_type "+
			" and od.order_id LIKE (? || '%') "+
			" order by od.doc_type, od.seq_num";
	
	private static final String SQL_INSERT_ALL_ORD_DOC_ASSGN = 
			"insert into bomweb_all_ord_doc_assgn ( \n"
			+ " order_id, doc_type, collected_ind, \n"
			+ " create_by, create_date, last_upd_by, last_upd_date )\n"
			+ " values \n"
			+ " ( ?, ?, ?, ?, sysdate, ?, sysdate )";
	
	private static final String SQL_GET_SALES_MEMO_PATH = 
			"SELECT sat.actv_id||sat.task_seq " + 
			"FROM slv_payment sp, " + 
			"  sb_actv_task sat " + 
			"WHERE sp.actv_id           = sat.actv_id " + 
			"AND ( (sat.task_type       = 'PAY_SM_CR' " + 
			"AND sp.sales_memo_num      = ?) " + 
			"OR (sat.task_type          = 'PAY_SM_VO' " + 
			"AND sp.void_sales_memo_num = ?) )";
	
	private final static ParameterizedRowMapper<OrdDocAssgnDTO> ordDocAssgnDTOMapper = new ParameterizedRowMapper<OrdDocAssgnDTO>() {
		public OrdDocAssgnDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			OrdDocAssgnDTO dto = new OrdDocAssgnDTO();
			dto.setOrderId(rs.getString("order_id"));
			dto.setDocType(rs.getString("doc_type"));
			dto.setDocName(rs.getString("doc_name"));
			dto.setDocNameChi(rs.getString("doc_name_chi"));
			dto.setCollectedInd(rs.getString("collected_ind"));
			dto.setLastUpdBy(rs.getString("last_upd_by"));
			dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
			
			return dto;
		}
	};
	
	private final static ParameterizedRowMapper<OrderDocDTO> ordDocDTO2Mapper = new ParameterizedRowMapper<OrderDocDTO>() {
		public OrderDocDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			OrderDocDTO dto = new OrderDocDTO();
			
			dto.setDocType(rs.getString("doc_type"));
			dto.setDocName(rs.getString("doc_name"));
			dto.setUploadMethod(rs.getString("upload_method"));
			
			return dto;
		}
	};
	
	
	private final static ParameterizedRowMapper<OrdDocSLVDTO> ordDocDTOMapper = new ParameterizedRowMapper<OrdDocSLVDTO>() {
		public OrdDocSLVDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			OrdDocSLVDTO dto = new OrdDocSLVDTO();
			dto.setOrderId(rs.getString("order_id"));
			dto.setDocType(rs.getString("doc_type"));
			dto.setSeqNum(rs.getInt("seq_num"));
			dto.setFilePathName(rs.getString("file_path_name"));
			dto.setCaptureBy(rs.getString("create_by"));
			dto.setCaptureDate(rs.getTimestamp("create_date"));
			dto.setDocName(rs.getString("doc_name"));
			dto.setDocNameChi(rs.getString("doc_name_chi"));
			dto.setLastUpdBy(rs.getString("last_upd_by"));
			dto.setLastUpdDate(rs.getTimestamp("last_upd_date"));
			dto.setVerifyBy(rs.getString("verify_by"));
			dto.setVerifyDate(rs.getTimestamp("verify_date"));
			dto.setRemark(rs.getString("doc_desc"));
			dto.setSerial(rs.getString("serial"));
			String verifyInd = rs.getString("verify_ind");
			if (StringUtils.isNotBlank(verifyInd)) {
				dto.setVerifyInd(VerifyInd.valueOf(verifyInd));
			}
			return dto;
		}
	};
	
	@Override
	public List<OrdDocAssgnDTO> getRequiredDocByActv(String pActvIdTaskSeq) throws DAOException {
		List<OrdDocAssgnDTO> rs = new ArrayList<OrdDocAssgnDTO>();
				      

		try {			
			rs = simpleJdbcTemplate.query(SQL_GET_ALL_DOC_BY_ACTV, ordDocAssgnDTOMapper, pActvIdTaskSeq);
			if (rs == null){
				return null;
			}
			return rs;		
		} catch (EmptyResultDataAccessException erdae) {
			//logger.info("EmptyResultDataAccessException in getEyeDeviceList()");
			return null;	
		} catch (Exception e) {
			//logger.info("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
	}	
	
	@Override 
	public List<OrderDocDTO> getRequiredDocByActv(String pActvIdTaskSeq, Map<String, String> pMdoKeyValueMap) throws DAOException {
		ParameterizedRowMapper<OrderDocDTO> mapper = new ParameterizedRowMapper<OrderDocDTO>() {
			public OrderDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDocDTO orderDoc = new OrderDocDTO();
				orderDoc.setDocType(rs.getString("DOC_TYPE"));
				orderDoc.setDocName(rs.getString("DOC_NAME"));
				orderDoc.setDocNameChi(rs.getString("DOC_NAME_CHI"));
				orderDoc.setDocDesc(rs.getString("DOC_DESC"));
				orderDoc.setMdoInd(rs.getString("MDO_IND"));
				orderDoc.setCollected("Y".equals(rs.getString("COLLECTED_IND")));
				orderDoc.setUploadMethod(rs.getString("UPLOAD_METHOD"));
//				orderDoc.setWaiveReasonCd(rs.getString("WAIVE_REASON"));
//				orderDoc.setWaiveReasonBy(rs.getString("WAIVED_BY"));
				return orderDoc;
			}
		};
		StringBuilder sql= new StringBuilder();		
		sql.append(" select ass.order_id, ass.doc_type DOC_TYPE, ass.collected_ind COLLECTED_IND,")
			.append(" sdv.DOC_NAME DOC_NAME, sdv.DOC_NAME_CHI DOC_NAME_CHI, sdv.DOC_DESC, sdv.MDO_IND, sdv.UPLOAD_METHOD ")
			.append(" from bomweb_all_ord_doc_assgn ass, W_SLV_SUPPORT_DOC_V sdv")	   
			.append(" where ASS.DOC_TYPE=sdv.DOC_TYPE ")
			.append(" and ass.order_id = ?")
			.append(" and nvl(ass.mark_del_ind,'N') <> 'Y' ")
			.append(" and ass.waive_reason is null ");

		int i=1;
		if (pMdoKeyValueMap != null){
			for (String mdoKey: pMdoKeyValueMap.keySet()){
				sql.append(" AND mdo_key_type" + (i==1? "": "_"+i )+ " = '" + mdoKey +"' ");
				sql.append(" AND (mdo_key_value"+ (i==1? "": "_"+i )+" ='*' or mdo_key_value" + (i==1? "": "_"+i )+ " = '" + pMdoKeyValueMap.get(mdoKey) + "') ");
				i++;
			}	
		}
		List<OrderDocDTO> rs = new ArrayList<OrderDocDTO>();
		try {			
			rs = simpleJdbcTemplate.query(sql.toString(), mapper, pActvIdTaskSeq);
			if (rs == null){
				return null;
			}
			return rs;		
		} catch (EmptyResultDataAccessException erdae) {
			//logger.info("EmptyResultDataAccessException in getEyeDeviceList()");
			return null;	
		} catch (Exception e) {
			logger.info("Exception caught in getRequiredDocByActv():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<OrdDocAssgnDTO> getRequiredDoc(String pActvIdTaskSeq, String pSrvType) throws DAOException {
		List<OrdDocAssgnDTO> rs = new ArrayList<OrdDocAssgnDTO>();
				      

		try {			
			rs = simpleJdbcTemplate.query(SQL_GET_ALL_DOC, ordDocAssgnDTOMapper, pSrvType);
			if (rs == null){
				return null;
			}
			return rs;		
		} catch (EmptyResultDataAccessException erdae) {
			//logger.info("EmptyResultDataAccessException in getEyeDeviceList()");
			return null;	
		} catch (Exception e) {
			//logger.info("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
	}
	
	@Override
	public List<OrderDocDTO> getRequiredSupportDoc(String pSrvType,String pChannel) throws DAOException {
		List<OrderDocDTO> rs = new ArrayList<OrderDocDTO>();
				      

		try {
				rs = simpleJdbcTemplate.query(SQL_GET_ALL_DOC_BY_TYPE+" AND mdo_key_type = 'CHANNEL_ID' and (mdo_key_value = '*' or mdo_key_value = ?) ", ordDocDTO2Mapper, pSrvType,pChannel);
			if (rs == null){
				return null;
			}
			return rs;		
		} catch (Exception e) {
			//logger.info("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}	
	}
	
	@Override
	public List<OrderDocDTO> getRequiredSupportDocByActvTaskType(String pActvType, String pTaskType, String pChannelId) throws DAOException {
		      
		try {
			OrderDocDTO[] rs = OracleSelectHelper.getSqlResultObjects(this.getDataSource(), SQL_GET_ALL_DOC_BY_ACTV_TASK_TYPE, 
						new Object[]{pActvType, pTaskType, pChannelId}, OrderDocDTO.class);
			if (ArrayUtils.isEmpty(rs)){
				return null;
			}
			return Arrays.asList(rs);		
			
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredSupportDocByActvTaskType():", e);
			throw new DAOException(e.getMessage(), e);
		}	
	}	
	
	@Override
	public int getLastSeqNum(String orderId, String docType) throws DAOException {

		int seqnum = simpleJdbcTemplate.queryForInt(SQL_GET_LAST_SEQ, orderId, docType);
		return seqnum;
	}
	
	@Override
	public void insertOrdDoc(OrdDocDTO pOrdDoc, String pDocDesc) throws DAOException{	
		try {
			logger.debug("insertOrdDoc @ ActivityDocumentDAO: \n" + SQL_INSERT_ORD_DOC);
			simpleJdbcTemplate.update(SQL_INSERT_ORD_DOC,
					pOrdDoc.getOrderId(),
					pOrdDoc.getDocType(),
					pOrdDoc.getSeqNum(),
					pOrdDoc.getFilePathName(),
					pOrdDoc.getLinkedPath(),
					pDocDesc,
					pOrdDoc.getCaptureBy(),
					pOrdDoc.getCaptureBy(),
					pOrdDoc.getSerial());

		} catch (Exception e) {
			logger.error("Exception caught in insertOrdDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@Override
	public void updateWaiveReason(String pOrderId, String pDocType, String pWaiveReason, String pUsername) throws DAOException {
		try {
			logger.debug("updateWaiveReason @ ActivityDocumentDAO: \n" + SQL_UPDATE_WAIVE_REASON);
			
			simpleJdbcTemplate.update(SQL_UPDATE_WAIVE_REASON, pWaiveReason, pUsername, pUsername, pOrderId, pDocType);

		} catch (Exception e) {
			logger.error("Exception caught in updateWaiveReason()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@Override
	public void markOrdDocCollected(String pOrderId, String pDocType, String pCollectedInd, String pUsername) throws DAOException {
		try {
			logger.debug("markOrdDocCollected @ ActivityDocumentDAO: \n" + SQL_MARK_COLLECT_IND);
			
			int updateNum = simpleJdbcTemplate.update(SQL_MARK_COLLECT_IND, pCollectedInd, pUsername, pOrderId, pDocType);
			
			if (updateNum <= 0){
				this.insertAllOrdDocAssgn(pOrderId, pDocType, pCollectedInd, pUsername);
			}

		} catch (Exception e) {
			logger.error("Exception caught in markOrdDocCollected()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void insertAllOrdDocAssgn(String pOrderId, String pDocType, String pCollectedInd, String pUsername) throws DAOException{
		try {
			logger.debug("insertAllOrdDocAssgn @ ActivityDocumentDAO: \n" + SQL_MARK_COLLECT_IND);
			
			simpleJdbcTemplate.update(SQL_INSERT_ALL_ORD_DOC_ASSGN, pOrderId, pDocType, pCollectedInd, pUsername, pUsername);

		} catch (Exception e) {
			logger.error("Exception caught in insertAllOrdDocAssgn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<OrdDocSLVDTO> getCapturedOrdDoc(String pOrderId) throws DAOException {

		List<OrdDocSLVDTO> list = new ArrayList<OrdDocSLVDTO>();
		try {
			logger.debug("getCapturedOrdDoc @ ActivityDocumentDAO: \n" + SQL_GET_CAPTURED_ORD_DOC);

			list = simpleJdbcTemplate.query(SQL_GET_CAPTURED_ORD_DOC, ordDocDTOMapper, pOrderId);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
			list = null;
		} catch (Exception e) {
			logger.error("Exception caught in getRequiredDoc()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
		
	}
	
	@Override
	public List<DocWaiveReasonDTO> getWaiveReasonList(String docType) throws DAOException {
		
		ParameterizedRowMapper<DocWaiveReasonDTO> mapper = new ParameterizedRowMapper<DocWaiveReasonDTO>() {
			public DocWaiveReasonDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				DocWaiveReasonDTO waiveReason = new DocWaiveReasonDTO();
				waiveReason.setDocType(rs.getString("DOC_TYPE"));
				waiveReason.setWaiveReasonCd(rs.getString("WAIVE_REASON"));
				waiveReason.setWaiveReasonDesc(rs.getString("WAIVE_DESC"));
				waiveReason.setDefaultWaiveInd(rs.getString("DEFAULT_WAIVE_IND"));
				return waiveReason;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT DOC_TYPE, WAIVE_REASON, WAIVE_DESC, DEFAULT_WAIVE_IND ")
			.append(" FROM BOMWEB_ALL_ORD_DOC_WAIVE")	   
			.append(" WHERE lob = 'LTS'")
			.append(" AND doc_type = ? ")
			.append(" ORDER BY WAIVE_REASON ");
			

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getWaiveReasonList() Sql: " + sql.toString());	
			}
			
			return simpleJdbcTemplate.query(sql.toString(), mapper, docType);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWaiveReasonList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getWaiveReasonList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@Override
	public OrderDocDTO getOrderDoc(String pDocType) throws DAOException {
		
		ParameterizedRowMapper<OrderDocDTO> mapper = new ParameterizedRowMapper<OrderDocDTO>() {
			public OrderDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDocDTO orderDoc = new OrderDocDTO();
				orderDoc.setDocType(rs.getString("DOC_TYPE"));
				orderDoc.setDocName(rs.getString("DOC_NAME"));
				orderDoc.setDocDesc(rs.getString("DOC_DESC"));
				orderDoc.setStartDate(rs.getString("START_DATE"));
				orderDoc.setEndDate(rs.getString("END_DATE"));
				orderDoc.setMdoInd(rs.getString("MDO_IND"));
				return orderDoc;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT DOC_TYPE, TYPE, DOC_NAME, DOC_NAME_CHI, DOC_DESC")
			.append(" , TO_CHAR(START_DATE, 'MM/DD/YYYY') START_DATE")
			.append(" , TO_CHAR(END_DATE, 'MM/DD/YYYY') END_DATE")
			.append(" , MDO_IND")
			.append(" FROM bomweb_all_doc")	   
			.append(" WHERE lob = 'SLV'")
			.append(" AND doc_type = ? ");
			

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrderDoc() Sql: " + sql.toString());	
			}
			
			List<OrderDocDTO> orderDocList = simpleJdbcTemplate.query(sql.toString(), mapper, pDocType); 
			
			if (orderDocList == null || orderDocList.isEmpty()) {
				return null;
			}
			
			return orderDocList.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOrderDoc()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrderDoc():", e);
			throw new DAOException(e.getMessage(), e);
		}
	
	}
	
	@Override
	public OrderDocDTO getOrderDocByMdoKey(String pDocType, Map<String, String> pMdoKeyValueMap) throws DAOException {
		
		ParameterizedRowMapper<OrderDocDTO> mapper = new ParameterizedRowMapper<OrderDocDTO>() {
			public OrderDocDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDocDTO orderDoc = new OrderDocDTO();
				orderDoc.setDocType(rs.getString("DOC_TYPE"));
				orderDoc.setDocName(rs.getString("DOC_NAME"));
				orderDoc.setDocDesc(rs.getString("DOC_DESC"));
				orderDoc.setMdoInd(rs.getString("MDO_IND"));
				return orderDoc;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		
		sql.append("SELECT DOC_TYPE, TYPE, DOC_NAME, DOC_NAME_CHI, DOC_DESC")
			.append(" , MDO_IND")
			.append(" FROM W_SLV_SUPPORT_DOC_V")	   
			.append(" WHERE doc_type = ? ");

		int i=1;
		if (pMdoKeyValueMap != null){
			for (String mdoKey: pMdoKeyValueMap.keySet()){
				sql.append(" AND mdo_key_type" + (i==1? "": "_"+i )+ " = '" + mdoKey +"' ");
				sql.append(" AND (mdo_key_value"+ (i==1? "": "_"+i )+" ='*' or mdo_key_value" + (i==1? "": "_"+i )+ " = '" + pMdoKeyValueMap.get(mdoKey) + "') ");
				i++;
			}	
		}
		
			

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrderDoc() Sql: " + sql.toString());	
			}
			
			List<OrderDocDTO> orderDocList = simpleJdbcTemplate.query(sql.toString(), mapper, pDocType); 
			
			if (orderDocList == null || orderDocList.isEmpty()) {
				return null;
			}
			
			return orderDocList.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOrderDoc()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getOrderDoc():", e);
			throw new DAOException(e.getMessage(), e);
		}
	
	}

	@Override
	public String getMemoPath(String pSalesMemoNum) {
		try {
			return OracleSelectHelper.getSqlFirstRowColumnString(this.getDataSource(), SQL_GET_SALES_MEMO_PATH, new Object[] { pSalesMemoNum, pSalesMemoNum });
		} catch (Exception e) {
			logger.error("fail to get salesMemo path /n");
		}
		return null;
	}
}
