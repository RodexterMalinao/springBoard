package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.exception.DAOException;



public class PageTrackDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public int insertPageTrack (String ipAddress, String staffId, String pageId, String func) throws DAOException{
				
		try{
			String SQL = "INSERT INTO "
				+ " W_PAGE_TRACK ("
				+ " REQ_ID , IP_ADDRESS, STAFF_ID, TIME_STAMP, PAGE, FUNC,"
				+ " CREATE_DATE, CREATE_BY "
				+ " ) VALUES ( "
				+ " W_PAGE_IMS_REQ_SEQ.nextval, :IP_ADDRESS, :STAFF_ID, sysdate, :PAGE, :FUNC,"
				+ " sysdate, :CREATE_BY"
				+ " ) "
				;
		
			MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("IP_ADDRESS", ipAddress)
			.addValue("STAFF_ID", staffId)
			.addValue("PAGE", pageId)
			.addValue("FUNC", func)
			.addValue("CREATE_BY", "SBIBATCH");
			
		return simpleJdbcTemplate.update(SQL, params);					
		}
		catch(Exception e) {
			throw new DAOException("Failed to insert W_PAGE_TRACK.", e);
		}		
	}
	
	public void doPageTrackCustSearch(String ipAddress, String staffId, String pageId, String func, CustomerInformationDTO customerInformationDTO) throws DAOException{
		int reqId = getReqId();
		int trackDtlId = getTrackDtlId();
		
		insertPageTrackCustSearch(reqId, ipAddress, staffId, pageId, func, trackDtlId);
		
		if(func.equals("Customer search")){
			insertPageTrackDtl(trackDtlId, "ID DOC Type", customerInformationDTO.getIdDocType());
			insertPageTrackDtl(trackDtlId, "ID DOC Num", customerInformationDTO.getIdDocNum());
			insertPageTrackDtl(trackDtlId, "Family Name", customerInformationDTO.getFamilyName());
			insertPageTrackDtl(trackDtlId, "Given Name", customerInformationDTO.getGivenName());
		}
	}
	
	public void doPageTrackRolloutSearch(String ipAddress, String staffId, String pageId, String func, String address) throws DAOException{
		int reqId = getReqId();
		int trackDtlId = getTrackDtlId();
		
		insertPageTrackCustSearch(reqId, ipAddress, staffId, pageId, func, trackDtlId);
		
		if(func.equals("Rollout search")){
			insertPageTrackDtl(trackDtlId, "Address", address);
		}
	}
	
	private int getReqId() throws DAOException{
		try{
			String SQL = "select W_PAGE_IMS_REQ_SEQ.nextval from dual";
			
			return this.simpleJdbcTemplate.queryForInt(SQL);
		}catch(Exception e) {
			logger.error("Exception caught in getReqId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private int getTrackDtlId() throws DAOException{
		try{
			String SQL = "select W_PAGE_TRACK_DTL_ID_SEQ.nextval from dual";
			
			return this.simpleJdbcTemplate.queryForInt(SQL);
		}catch(Exception e) {
			logger.error("Exception caught in getTrackDtlId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public int insertPageTrackCustSearch (int reqId, String ipAddress, String staffId, String pageId, String func, int trackDtlId) throws DAOException{
		
		try{
			String SQL = "INSERT INTO "
					   + " W_PAGE_TRACK ("
					   + " REQ_ID , IP_ADDRESS, STAFF_ID, TIME_STAMP, PAGE, FUNC,"
					   + " CREATE_DATE, CREATE_BY, TRACK_DTL_ID "
					   + " ) VALUES ( "
					   + " :REQ_ID, :IP_ADDRESS, :STAFF_ID, sysdate, :PAGE, :FUNC,"
					   + " sysdate, :CREATE_BY, :TRACK_DTL_ID"
					   + " ) ";
		
			MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("REQ_ID", reqId)
			.addValue("IP_ADDRESS", ipAddress)
			.addValue("STAFF_ID", staffId)
			.addValue("PAGE", pageId)
			.addValue("FUNC", func)
			.addValue("CREATE_BY", "NOWTVRET")
			.addValue("TRACK_DTL_ID", trackDtlId);
			
			return simpleJdbcTemplate.update(SQL, params);					
		}catch(Exception e) {
			logger.error("Exception caught in insertPageTrack()", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public int insertPageTrackDtl (int trackDtlId, String dtlType, String dtlValue) throws DAOException{
		try{
			String SQL = "INSERT INTO "
					   + " W_PAGE_TRACK_DTL ("
					   + " TRACK_DTL_ID , DTL_TYPE, DTL_VALUE,"
					   + " CREATE_DATE, CREATE_BY "
					   + " ) VALUES ( "
					   + " :TRACK_DTL_ID, :DTL_TYPE, :DTL_VALUE,"
					   + " sysdate, :CREATE_BY"
					   + " ) ";
		
			MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("TRACK_DTL_ID", trackDtlId)
			.addValue("DTL_TYPE", dtlType)
			.addValue("DTL_VALUE", dtlValue)
			.addValue("CREATE_BY", "NOWTVRET");
			
			return simpleJdbcTemplate.update(SQL, params);
		}catch(Exception e) {
			logger.error("Exception caught in insertPageTrackDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}

