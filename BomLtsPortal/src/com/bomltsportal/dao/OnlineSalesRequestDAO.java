package com.bomltsportal.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomltsportal.dto.OnlineSalesRequestDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class OnlineSalesRequestDAO extends BaseDAO {
	
	
	private static final String SQL_INSERT_ONLINE_SALES_REQ = 
		"insert into w_online_sales_request " +
		" (request_id, request_cd, channel, request_type, item_id, build_xy, flat, floor, " +
		" housing_addr_en, housing_addr_ch, section_desc_en, section_desc_ch, district_desc_en, district_desc_ch, " +
		" area_desc_en, area_desc_ch, is_pon_only, is_premier, title, name, doc_type, doc_num, cont_phone_no, email_addr, " +
		" lang_preference, process_status, send_ack_email, send_report, update_by, update_date, create_by, create_date, sales_lead_remark, service_dn) " +
		"values" +
		" (w_online_sales_request_seq.nextVal, :requestCd, :channel, :requestType, :itemId, :buildXy, :flat, :floor, " +
		" :housingAddrEn, :housingAddrCh, :sectionDescEn, :sectionDescCh, :districtDescEn, :districtDescCh, " +
		" :areaDescEn, :areaDescCh, 'N', :premier, :title, :name, :docType, :docNum, :contactNum, :email, " +
		" :langPref, :processStatus, :sendAckEmail, :sendReport, :updateBy, sysdate, :createBy, sysdate, :salesLeadRemark, :serviceDN)";
	
	
	public void insertOnlineSalesRequest(OnlineSalesRequestDTO onlineSalesRequest) throws DAOException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("requestCd", onlineSalesRequest.getRequestCd());
		paramSource.addValue("channel", onlineSalesRequest.getChannel());
		paramSource.addValue("requestType", onlineSalesRequest.getRequestType());
		paramSource.addValue("itemId", onlineSalesRequest.getItemId());
		paramSource.addValue("buildXy", onlineSalesRequest.getBuildXy());
		paramSource.addValue("flat", onlineSalesRequest.getFlat());
		paramSource.addValue("floor", onlineSalesRequest.getFloor());
		paramSource.addValue("housingAddrEn", onlineSalesRequest.getHousingAddrEn());
		paramSource.addValue("housingAddrCh", onlineSalesRequest.getHousingAddrCh());
		paramSource.addValue("sectionDescEn", onlineSalesRequest.getSectionDescEn());
		paramSource.addValue("sectionDescCh", onlineSalesRequest.getSectionDescCh());
		paramSource.addValue("districtDescEn", onlineSalesRequest.getDistrictDescEn());
		paramSource.addValue("districtDescCh", onlineSalesRequest.getDistrictDescCh());
		paramSource.addValue("areaDescEn", onlineSalesRequest.getAreaDescEn());
		paramSource.addValue("areaDescCh", onlineSalesRequest.getAreaDescCh());
		paramSource.addValue("premier", onlineSalesRequest.getPremier());
		paramSource.addValue("title", onlineSalesRequest.getTitle());
		paramSource.addValue("name", onlineSalesRequest.getName());
		paramSource.addValue("docType", onlineSalesRequest.getDocType());
		paramSource.addValue("docNum", onlineSalesRequest.getDocNum());
		paramSource.addValue("contactNum", onlineSalesRequest.getContactNum());
		paramSource.addValue("email", onlineSalesRequest.getEmail());
		paramSource.addValue("langPref", onlineSalesRequest.getLangPref());
		paramSource.addValue("processStatus", BomLtsConstant.ONLINE_SALES_REQ_PROCESS_STATUS_INITIAL);
		paramSource.addValue("sendAckEmail", onlineSalesRequest.getSendAckEmail());
		paramSource.addValue("sendReport", onlineSalesRequest.getSendReport());
		paramSource.addValue("updateBy", BomLtsConstant.USER_ID);
		paramSource.addValue("createBy", BomLtsConstant.USER_ID);
		paramSource.addValue("salesLeadRemark", onlineSalesRequest.getRemarkEn());
		paramSource.addValue("serviceDN", onlineSalesRequest.getServiceDN());
		
		try {
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(SQL_INSERT_ONLINE_SALES_REQ, paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in insertOnlineSalesRequest():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

}
