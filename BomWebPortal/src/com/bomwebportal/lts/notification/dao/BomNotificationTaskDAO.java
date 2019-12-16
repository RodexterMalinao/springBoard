package com.bomwebportal.lts.notification.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
/**
 * This class connects to BomDS and retrieves notification details. 
 * This class intenses to replace the old SMS sending task on BOM batch job
 * as Oracle 9i does not support SHA-256 certificate, and job sending SMS out using
 * SP on Oracle 9i failed.
 * 
 * This class mainly run SQL, and the logic of sending SMS resides on controller. 
 * 
 * @author 01517028
 *
 */
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.lts.notification.dto.BomNotificationDetailDTO;
public class BomNotificationTaskDAO extends BaseDAO  {

	//SQL to get notification info from BOM DB and update status
	//bom_sqlplus $BOM_ORA_USER $SQLDIR/ocmsendsmsalerts.sql 
	// $jobname=EYECONTENTTRIAL $strictcheck=Y $isdebug=N $smsgatewayid=EYESURVEY
	private static final String SQL_SELECT_ALL_FIELDS_B_OC_NOTIFY_MSG = "SELECT MSG_ID, MSG_TYPE, MSG_GRP, MSG_SEQ, MSG_TEXT, "
			+ "MSG_CHAR_SET, MSG_FROM, MSG_TO, JOBNAME, CKSUM_BASE64, "
			+ "STATUS, RETRY_CNT, RETRY_LIMIT, SEND_AFTER_DATE, LAST_UPD_DATE, "
			+ "LAST_UPD_BY, CREATE_DATE, CREATE_BY "
			+ "FROM B_OC_NOTIFY_MSG ";
	private static final String SQL_GET_NOTIFICATION_NOT_IN_STATUS = 
			SQL_SELECT_ALL_FIELDS_B_OC_NOTIFY_MSG
			+ "WHERE STATUS  != ? "
			+ "AND   MSG_TYPE = ? "
			+ "AND   JOBNAME  = ? "
			+ "AND   RETRY_CNT < RETRY_LIMIT "
			+ "AND NVL(SEND_AFTER_DATE,SYSDATE - 1)  <= SYSDATE "
			+ "ORDER BY MSG_GRP, MSG_SEQ, CREATE_DATE ASC";
	private static final String SQL_WHERE_JOBNAME_IN = "JOBNAME in (?)";
	private static final String SQL_GET_NOTIFICATION_NOT_IN_STATUS_IN_JOBNAME = 
			SQL_SELECT_ALL_FIELDS_B_OC_NOTIFY_MSG
			+ "WHERE STATUS  != ? "
			+ "AND   MSG_TYPE = ? "
			+ "AND   "+SQL_WHERE_JOBNAME_IN+" "
			+ "AND   RETRY_CNT < RETRY_LIMIT "
			+ "AND NVL(SEND_AFTER_DATE,SYSDATE - 1)  <= SYSDATE "
			+ "ORDER BY MSG_GRP, MSG_SEQ, CREATE_DATE ASC";
	
	private static final String SQL_GET_NOTIFICATION_BY_MSG_ID = 
			SQL_SELECT_ALL_FIELDS_B_OC_NOTIFY_MSG
			+ "WHERE "
			+ "MSG_ID = ? ";
	
	private static final String SQL_UPDATE_NOTIFICATION_STATUS = "UPDATE B_OC_NOTIFY_MSG SET STATUS = ?, "
			+ "LAST_UPD_DATE = SYSTIMESTAMP, LAST_UPD_BY = ?, "
			+ "RETRY_CNT = ? "
			+ "where MSG_ID = ?";
//	private static final String SQL_GET_NOTIFICATION = "SELECT MSG_ID, JOBNAME, MSG_GRP, MSG_SEQ FROM B_OC_NOTIFY_MSG WHERE STATUS  != STATUS_COMPLETED AND   MSG_TYPE = MSG_TYPE_SMS AND   JOBNAME  = v_jobname AND   RETRY_CNT < RETRY_LIMIT AND NVL(SEND_AFTER_DATE,SYSDATE - 1)  <= SYSDATE ORDER BY MSG_GRP,MSG_SEQ,CREATE_DATE ASC";
//	private static final String SQL_ = "SELECT BOM_DESC INTO vSMSMsgType  FROM B_LOOKUP WHERE BOM_GRP_ID = 'NOTIFY_SMS_MSGTP' AND   BOM_STATUS = 'A' AND   NVL(LTS_GRP_ID,DEF_SMS_ID) = NVL(i_sms_gateway_id,DEF_SMS_ID);";
	
//	private static final String SQL_GET_SMSHOST = "SELECT BOM_DESC INTO vSMSHost  FROM B_LOOKUP WHERE BOM_GRP_ID = 'NOTIFY_SMS_HOST' AND BOM_STATUS = 'A' AND NVL(LTS_GRP_ID,DEF_SMS_ID) = NVL(i_sms_gateway_id,DEF_SMS_ID)";
//	private static final String SQL_GET_SMSUSER = "SELECT BOM_CODE INTO vSMSUser  FROM B_LOOKUP WHERE BOM_GRP_ID = 'NOTIFY_SMS_USER' AND BOM_STATUS = 'A' AND NVL(LTS_GRP_ID,DEF_SMS_ID) = NVL(i_sms_gateway_id,DEF_SMS_ID)";
//	private static final String SQL_GET_BOM_CODE = "SELECT BOM_CODE INTO vBomCode  FROM B_LOOKUP WHERE BOM_GRP_ID = 'NOTIFY_SMS_PSWD' AND BOM_STATUS = 'A' AND NVL(LTS_GRP_ID,DEF_SMS_ID) = NVL(i_sms_gateway_id,DEF_SMS_ID)";
	/**
	 * 
	 * Retrieving record by msgId.
	 * 
	 * @param msgId
	 * @return
	 */
	public List getNotificationsByMsgId(Integer msgId){
		Object[] args = new Object[1];
		args[0] = msgId;

		String sqlStr = SQL_GET_NOTIFICATION_BY_MSG_ID;
//		BomNotificationDetailDTO result = (BomNotificationDetailDTO) this.jdbcTemplate.queryForObject(sqlStr, BomNotificationDetailDTO.class);
		List result = this.jdbcTemplate.query(sqlStr, args, getBomNotificationDetailDTOMapper());
		return result;
	}
	/**
	 * Retrieving notification records that not in $notificationStatus status, by $msgType (in this class, SMS), $jobName, and retry count 
	 * is less than $retryLimit.
	 * 
	 * Usually $notificationStatus will be "COMPLETED" as the work flow will send notification that is pending or failed.
	 * 
	 * @param notificationStatus
	 * @param msgType
	 * @param jobName
	 * @return
	 */
	public List getNotificationsNotInStatus(String notificationStatus, String msgType, String[] jobName){
		String sqlStr = SQL_GET_NOTIFICATION_NOT_IN_STATUS_IN_JOBNAME;
		
		String jobnameInStr = SQL_WHERE_JOBNAME_IN;
		StringBuffer sb = new StringBuffer().append("?");
		for(int i=1; i<jobName.length; i++){
			sb.append(",?");
		}
		jobnameInStr = StringUtils.replace(jobnameInStr, "?", sb.toString());
		sqlStr = StringUtils.replace(sqlStr, SQL_WHERE_JOBNAME_IN,jobnameInStr);
		
		Object[] args = new Object[2+jobName.length];
		args[0] = notificationStatus;
		args[1] = msgType;
		// args[2] = jobName;
		
		for(int i=0; i<jobName.length; i++){
			args[i+2] = jobName[i]; 
		}
		
		List result = this.jdbcTemplate.query(sqlStr, args, getBomNotificationDetailDTOMapper());
		return result;
	}
	
	public int updateNotificationStatus(String status, String lastUpdBy, Integer retryCnt, Integer msgId){
		Object[] args = new Object[4];
		args[0] = status;
		args[1] = lastUpdBy;
		args[2] = retryCnt;
		args[3] = msgId;
		String sqlStr = SQL_UPDATE_NOTIFICATION_STATUS;
		int ret = this.jdbcTemplate.update(sqlStr, args);
		return ret;
	}
	
	public void getNotificationDetails(){
		
	}
	
	private static ParameterizedRowMapper<BomNotificationDetailDTO> getBomNotificationDetailDTOMapper(){
		return new ParameterizedRowMapper<BomNotificationDetailDTO>() {
			

			public BomNotificationDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BomNotificationDetailDTO obj = new BomNotificationDetailDTO();
				
				Integer msgId = rs.getInt("MSG_ID");
				String msgType = rs.getString("MSG_TYPE");
				String msgGrp = rs.getString("MSG_GRP");
				Integer msgSeq = rs.getInt("MSG_SEQ");
				String msgText = rs.getString("MSG_TEXT");
				String msgCharSet = rs.getString("MSG_CHAR_SET");
				String msgFrom = rs.getString("MSG_FROM");
				String msgTo = rs.getString("MSG_TO");
				String jobname = rs.getString("JOBNAME");
				String cksumBase64 = rs.getString("CKSUM_BASE64");
				String status = rs.getString("STATUS");
				Integer retryCnt = rs.getInt("RETRY_CNT");
				Integer retryLimit = rs.getInt("RETRY_LIMIT");
				Date sendAfterDate = rs.getDate("SEND_AFTER_DATE");
				String lastUpdDate = rs.getString("LAST_UPD_DATE");
				String lastUpdBy = rs.getString("LAST_UPD_BY");
				String createDate = rs.getString("CREATE_DATE");
				String createBy = rs.getString("CREATE_BY");
				obj.setMsgId(msgId);
				obj.setMsgType(msgType);				
				obj.setMsgGrp(msgGrp);
				obj.setMsgSeq(msgSeq);
				obj.setMsgText(msgText);
				obj.setMsgCharSet(msgCharSet);
				obj.setMsgFrom(msgFrom);
				obj.setMsgTo(msgTo);
				obj.setJobname(jobname);
				obj.setCksumBase64(cksumBase64);
				obj.setStatus(status);
				obj.setRetryCnt(retryCnt);
				obj.setRetryLimit(retryLimit);
				obj.setSendAfterDate(sendAfterDate);
				obj.setLastUpdDate(lastUpdDate);
				obj.setLastUpdBy(lastUpdBy);
				obj.setCreateDate(createDate);
				obj.setCreateBy(createBy);

				return obj;
			}
		};
	}
}
