package com.bomwebportal.ims.dao;


import java.sql.Types;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.google.gson.Gson;

public class ImsSignOffLogDAO extends BaseDAO{
    private Gson gson = new Gson();

	public void signOffOrderLog(OrderImsUI order, String action, String errMsg) throws DAOException{
		logger.info("signOffOrderLog orderId:"+order.getOrderId());
		String systemFlow = order.getImsOrderType();
		if(systemFlow==null||"".equals(systemFlow)){
			systemFlow=order.getOrderType();
			if(systemFlow==null||"".equals(systemFlow)){
				if("Y".equalsIgnoreCase(order.getIsPT())){
					systemFlow = "PT-ACQ";
				}else if("Y".equalsIgnoreCase(order.getIsCC())){
						systemFlow = "CC-ACQ";
				}else{
					if(order.getOrderId()!=null&&!order.getOrderId().isEmpty()&&order.getOrderId().subSequence(0, 1).equals("P")){
						systemFlow = "PT-ACQ";
					}else if(order.getOrderId()!=null&&!order.getOrderId().isEmpty()&&order.getOrderId().subSequence(0, 1).equals("C")){
						systemFlow = "CC-ACQ";
					}else{
						systemFlow = "Retail-ACQ";
					}
				}
			}
		}else{
			if(order.getOrderType()!=null&&!"".equals(order.getOrderType())){
				systemFlow=systemFlow+"-"+order.getOrderType();
			}			
		}
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$CNM")
			.withCatalogName("PKG_SB_IMS_LOG")
			.withProcedureName("SIGN_OFF_ORDER_LOG");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
			new SqlParameter("i_order_id", Types.VARCHAR),
			new SqlParameter("i_system_flow", Types.VARCHAR),
			new SqlParameter("i_action", Types.VARCHAR),
			new SqlParameter("i_create_by", Types.VARCHAR),
			new SqlParameter("i_err_msg", Types.CLOB),
			new SqlOutParameter("o_result", Types.VARCHAR),
			new SqlOutParameter("o_gnRetVal", Types.INTEGER),
			new SqlOutParameter("o_errcode", Types.INTEGER),
			new SqlOutParameter("o_errtext", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", order.getOrderId());
			inMap.addValue("i_system_flow", systemFlow);
			inMap.addValue("i_action", action);
			inMap.addValue("i_create_by", order.getCreateBy());
			inMap.addValue("i_err_msg", errMsg);
			SqlParameterSource in = inMap;

			logger.debug("signOffOrderLog input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.debug("signOffOrderLog output:"+gson.toJson(out));
				
		} catch (Exception e) {
			logger.error("Exception caught in signOffOrderLog()", e);
		}
	}
}
