package com.bomwebportal.lts.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.CodeLkupDAOImpl;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.EyeImsActionLkupDTO;
import com.bomwebportal.lts.dto.order.ImsActionLkupDTO;
import com.bomwebportal.lts.dto.order.LtsActionLkupDTO;
import com.bomwebportal.lts.dto.order.OtherActionLkupDTO;
import com.bomwebportal.lts.dto.order.ServiceActionLkupBaseDTO;
import com.bomwebportal.lts.dto.order.WqActionLkupDTO;

public class ServiceActionLkupDAO extends CodeLkupDAOImpl {
		
	private static final String W_IMS_ACTION_LKUP     = "W_IMS_ACTION_WQ_LKUP_V";	
	private static final String W_LTS_ACTION_LKUP     = "W_LTS_ACTION_WQ_LKUP_V";
	private static final String W_OTHER_ACTION_LKUP   = "W_OTHER_ACTION_WQ_LKUP_V";
	private static final String W_EYE_IMS_ACTION_LKUP = "W_EYE_IMS_ACTION_WQ_LKUP_V";

	private String lkupTable;
	private String itemKey;
	
	public LookupItemDTO [] getCodeLkup() throws DAOException {

		lkupTable = this.getLkupTable();
		itemKey   = this.getItemKey();
		
		ParameterizedRowMapper<LookupItemDTO> mapper = new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				ServiceActionLkupBaseDTO serviceAction = null;
				WqActionLkupDTO wqActionLkup = null;
				
				long wqKey = rs.getLong("WQ_KEY");
				if (!rs.wasNull()) {
					wqActionLkup = new WqActionLkupDTO();
					wqActionLkup.setWqKey(wqKey);
					wqActionLkup.setWqNatureId(rs.getLong("WQ_NATURE_ID"));
					wqActionLkup.setWqSubtype(rs.getString("WQ_SUBTYPE"));
					wqActionLkup.setWqType(rs.getString("WQ_TYPE"));										
				}
				
				if (W_LTS_ACTION_LKUP.equals(lkupTable) || W_IMS_ACTION_LKUP.equals(lkupTable)) {
					if (W_LTS_ACTION_LKUP.equals(lkupTable)) {
						serviceAction = new LtsActionLkupDTO();
					} else {
						serviceAction = new ImsActionLkupDTO();
						((ImsActionLkupDTO)serviceAction).setMirror(rs.getString("MIRROR"));
						((ImsActionLkupDTO)serviceAction).setModemArrangement(rs.getString("MODEM_ARRANGEMENT"));
						((ImsActionLkupDTO)serviceAction).setOfferGrpId(rs.getString("OFFER_GRP_ID"));
						((ImsActionLkupDTO)serviceAction).setOrderType(rs.getString("ORDER_TYPE"));
					}										
					((LtsActionLkupDTO)serviceAction).setFromProd(rs.getString("FROM_PROD"));
					((LtsActionLkupDTO)serviceAction).setToProd(rs.getString("TO_PROD"));					                                        
				} else if (W_OTHER_ACTION_LKUP.equals(lkupTable)) {
					serviceAction = new OtherActionLkupDTO();
					((OtherActionLkupDTO)serviceAction).setActionType(rs.getString("ACTION_TYPE"));
					((OtherActionLkupDTO)serviceAction).setStatus(rs.getString("STATUS"));
				} else if (W_EYE_IMS_ACTION_LKUP.equals(lkupTable)) {
					serviceAction = new EyeImsActionLkupDTO();
					((EyeImsActionLkupDTO)serviceAction).setExistMirror(rs.getString("EXIST_MIRROR"));
					((EyeImsActionLkupDTO)serviceAction).setExistModemArrangement(rs.getString("EXIST_MODEM_ARRANGEMENT"));
					((EyeImsActionLkupDTO)serviceAction).setFromEyeProd(rs.getString("FROM_EYE_PROD"));
					((EyeImsActionLkupDTO)serviceAction).setToEyeProd(rs.getString("TO_EYE_PROD"));
					((EyeImsActionLkupDTO)serviceAction).setFromImsProd(rs.getString("FROM_IMS_PROD"));
				}

				serviceAction.setSuspendBomReasonCd(rs.getString("SUSPEND_BOM_REASON_CD"));
				serviceAction.setWqActionLkup(wqActionLkup);
																
            	LookupItemDTO lookupItemDTO = new LookupItemDTO();
            	if (StringUtils.isNotBlank(itemKey)) {
            	  lookupItemDTO.setItemKey(rs.getString(itemKey));
            	} else {            	
            	  lookupItemDTO.setItemKey(serviceAction.getLkupKey());
            	}
            	
            	lookupItemDTO.setItemValue(serviceAction);
                return lookupItemDTO;                
			}};
		
			try {
				StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ");
	            sqlBuilder.append(lkupTable);	            
	            if (StringUtils.isNotBlank(this.getLkupCode())) {
	            	sqlBuilder.append(" WHERE ");	
	            	sqlBuilder.append(this.getLkupCode());	
	            }
	            logger.debug("SQL: " + sqlBuilder.toString());
				List<LookupItemDTO> lookupList = this.simpleJdbcTemplate.query(sqlBuilder.toString(),mapper);
				
				if (W_EYE_IMS_ACTION_LKUP.equals(lkupTable)) {
					return this.mergeLookupByKey(lookupList);
				} else {
					return lookupList.toArray(new LookupItemDTO[lookupList.size()]);
				}
			} catch (Exception e) {
				logger.error("Error in getStatus()", e);
				throw new DAOException(e.getMessage(), e);
			}						
	}	 	
	
	@SuppressWarnings("unchecked")
	private LookupItemDTO[] mergeLookupByKey(List<LookupItemDTO> pLookupList) {
		
		Map<String, LookupItemDTO> lookupMap = new HashMap<String, LookupItemDTO>();
		List<ServiceActionLkupBaseDTO> srvActionList = null;
		
		for (int i=0; i<pLookupList.size(); ++i) {
			
			if (lookupMap.containsKey(pLookupList.get(i).getItemKey())) {
				srvActionList = (List<ServiceActionLkupBaseDTO>)lookupMap.get(pLookupList.get(i).getItemKey()).getItemValue();
			} else {
				LookupItemDTO lookup = new LookupItemDTO();
				lookup.setItemKey(pLookupList.get(i).getItemKey());
				lookup.setItemValue(new ArrayList<ServiceActionLkupBaseDTO>());
				lookupMap.put(pLookupList.get(i).getItemKey(), lookup);
				srvActionList = (List<ServiceActionLkupBaseDTO>)lookup.getItemValue();
			}
			srvActionList.add((ServiceActionLkupBaseDTO)pLookupList.get(i).getItemValue());
		}
		return lookupMap.values().toArray(new LookupItemDTO[lookupMap.size()]);
	}
}
