package com.pccw.rpt.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.wq.ImsWorkQueueRptDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public class WorkQueueImsDataDAO extends BaseDAO{

	//this file created by ims steven 20130208
	protected final Log logger = LogFactory.getLog(getClass());
	
	public ImsWorkQueueRptDTO getImsWQdata (ReportDTO pReportDTO, String orderId)
	throws DAOException {

		ImsWorkQueueRptDTO _dto = (ImsWorkQueueRptDTO) pReportDTO;
		String SQL=	" select a.shop_cd, TO_CHAR (a.app_date, 'DD/MM/YYYY') AD, b.sales_channel SC, b.sales_channel AM " +
				" from bomweb_shop b, bomweb_order a " +
				" where a.order_id = '"+orderId+"' " +
				" and b.shop_cd = a.shop_cd";

		ParameterizedRowMapper<ImsWorkQueueRptDTO> mapper = new ParameterizedRowMapper<ImsWorkQueueRptDTO>() {
		    public ImsWorkQueueRptDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	ImsWorkQueueRptDTO temp = new ImsWorkQueueRptDTO();
		    	temp.setApplicationDate(rs.getString("AD"));
		    	temp.setApplicationMethod(rs.getString("AM"));
		    	temp.setSourceCode(rs.getString("SC"));
				return temp;
		    }
		};

		List<ImsWorkQueueRptDTO> temp2 = new ArrayList<ImsWorkQueueRptDTO>() ;
		try {
			logger.debug("getGift @ ImsGiftDAO: " + SQL);
			temp2=  simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.debug("Exception caught in getGift@ImsGiftDAO():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(temp2.size()>0){
			_dto.setApplicationDate(temp2.get(0).getApplicationDate());
			_dto.setApplicationMethod(temp2.get(0).getApplicationMethod());
			_dto.setSourceCode(temp2.get(0).getSourceCode());
		}else{
			_dto.setApplicationDate(" ");
			_dto.setApplicationMethod("SBONLINE");
			_dto.setSourceCode("SBONLINE");
		}
		
		return _dto;
	}
}

