package com.bomwebportal.lts.service.qc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ui.DsQCImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsDsQCProcessUI;
import com.bomwebportal.ims.dto.ui.ImsQcComOrderSearchUI;
import com.bomwebportal.lts.dao.qc.LtsDsQcDAO;
import com.bomwebportal.lts.dto.form.qc.LtsDsQcProcessDetailFormDTO;
import com.bomwebportal.lts.dto.qc.LtsDsQcProcessDTO;
import com.bomwebportal.lts.dto.qc.LtsDsQcSearchCriteriaDTO;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsDsQcServiceImpl implements LtsDsQcService {
	
	private LtsDsQcDAO ltsDsQcDao;
	private CodeLkupCacheService ltsDsQcOrdStatusGrpLkupCacheService;
	private CodeLkupCacheService ltsDsQcOrdStatusLkupCacheService;
	private CodeLkupCacheService ltsDsQcStatusLkupCacheService;
	private CodeLkupCacheService ltsDsQcCannotQcCodeLkupCacheService;
	private CodeLkupCacheService ltsDsQcFailedCodeLkupCacheService;
	
	public List<ImsAlertMsgDTO> getLtsDsQcProcessEnquiryInfo(
			ImsDsQCProcessUI enquiry, BomSalesUserDTO userDto) {
		
		List<String> orderStatusList = getLtsQcOrderStatusListByGroup(enquiry.getOrderStatus());
		
		List<ImsAlertMsgDTO> result = null;
		LtsDsQcSearchCriteriaDTO quiteria = new LtsDsQcSearchCriteriaDTO();
		
		quiteria.setOrderId(enquiry.getOrderId());
		quiteria.setDateType(enquiry.getDateType());
		quiteria.setAssignee(enquiry.getAssignee());
		if(StringUtils.equals(userDto.getCategory(),"FRONTLINE")){
			quiteria.setAssignee(userDto.getUsername());
		}
		quiteria.setStartDate(enquiry.getStartDate());
		quiteria.setEndDate(enquiry.getEndDate());
		quiteria.setOrderStatusList(orderStatusList);
		quiteria.setQcStatus(enquiry.getQcStatus());
		quiteria.setAssigned(true);
				
		try {
			List<String> channelCdList = ltsDsQcDao.getSalesChannelCodeByChannelID(userDto.getChannelId());
			quiteria.setSalesChannel(channelCdList);
			
			result = ltsDsQcDao.searchLtsQcOrder(quiteria, userDto.getChannelId());

		} catch (DAOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<ImsAlertMsgDTO> getLtsDsQcAssignEnquiryInfo(
			DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto) {
		
		List<String> orderStatusList = getLtsQcOrderStatusListByGroup(enquiry.getOrderStatus());
		
		List<ImsAlertMsgDTO> result = null;
		LtsDsQcSearchCriteriaDTO quiteria = new LtsDsQcSearchCriteriaDTO();
		
		quiteria.setOrderId(enquiry.getOrderId());
		quiteria.setDateType(enquiry.getDateType());
		quiteria.setStartDate(enquiry.getStartDate());
		quiteria.setEndDate(enquiry.getEndDate());
		quiteria.setOrderStatusList(orderStatusList);
		quiteria.setIsFilterAssigned("Y");
		
		try {
			List<String> channelCdList = ltsDsQcDao.getSalesChannelCodeByChannelID(userDto.getChannelId());
			quiteria.setSalesChannel(channelCdList);
			
			result = ltsDsQcDao.searchLtsQcOrder(quiteria, userDto.getChannelId());
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<ImsAlertMsgDTO> getLtsDsQcOrderEnquiryInfo(
			ImsQcComOrderSearchUI enquiry, BomSalesUserDTO userDto) {
		
		List<String> orderStatusList = getLtsQcOrderStatusListByGroup(enquiry.getOrderStatus());
 		
		List<ImsAlertMsgDTO> result = null;
		LtsDsQcSearchCriteriaDTO criteria = new LtsDsQcSearchCriteriaDTO();
				
		criteria.setOrderId(enquiry.getOrderId());
		criteria.setDateType(enquiry.getDateType());
		criteria.setAssignee(enquiry.getAssignee());
		criteria.setStartDate(enquiry.getStartDate());
		criteria.setEndDate(enquiry.getEndDate());
		criteria.setOrderStatusList(orderStatusList);
		criteria.setQcStatus(enquiry.getQcStatus());
		criteria.setAmendment(enquiry.getAmendment());
		criteria.setIsThirdParty(enquiry.getIs3rdParty());
		criteria.setPayMethod(enquiry.getPaymentMethod());
		criteria.setHouseType(enquiry.getHousingType());
		criteria.setSalesCode(enquiry.getSalesNum());
		criteria.setTeamCode(enquiry.getTeamSearch());
		criteria.setIdDocNum(enquiry.getDocNum());
		criteria.setIdDocType(enquiry.getDocType());
		
		try {
			List<String> channelCdList = ltsDsQcDao.getSalesChannelCodeByChannelID(userDto.getChannelId());
			criteria.setSalesChannel(channelCdList);
			
			result = ltsDsQcDao.searchLtsQcOrder(criteria, userDto.getChannelId());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public LtsDsQcProcessDTO getLtsDsQcProcessInfo(String orderId){
		try {
			return ltsDsQcDao.getQcProcessDetail(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public LookupItemDTO[] getLtsQcStatusList() {
		try {
			return ltsDsQcStatusLkupCacheService.getCodeLkupDAO().getCodeLkup();
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public LookupItemDTO[] getLtsQcOrderStatusGroupList() {
		try {
			return ltsDsQcOrdStatusGrpLkupCacheService.getCodeLkupDAO().getCodeLkup();
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getLtsQcOrderStatusListByGroup(String group) {
		List<String> statusList = new ArrayList<String>();
		try {
			LookupItemDTO[] orderStatusLkupItems = ltsDsQcOrdStatusLkupCacheService.getCodeLkupDAO().getCodeLkup();
			for(LookupItemDTO item: orderStatusLkupItems){
				if(StringUtils.equals(group, (String)item.getItemValue())){
					statusList.add((String)item.getItemKey());
				}
			}
			return statusList.size() > 0? statusList : null;
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public LookupItemDTO[] getCannotQcReasonList() {
		try {
			return ltsDsQcCannotQcCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public LookupItemDTO[] getFailedReasonList() {
		try {
			return ltsDsQcFailedCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void insertQcProcess(String orderId, String qcDate, String qcFindings, String district, String place, 
			String qcStatus, String reasonCode, String reasonString, String userId, String action){
		try {
			ltsDsQcDao.insertQcProcess(orderId, qcDate, getQcRemark(qcFindings, reasonString, userId, action), 
					district, place, qcStatus, reasonCode, userId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isQcProcessExist(String orderId){
		try {
			return ltsDsQcDao.isQcProcessExist(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void updateQcProcess(String orderId, String qcDate, String qcFindings, String district, String place, 
			String qcStatus, String reasonCode, String reasonString, String userId, String action){
		try {
			ltsDsQcDao.updateQcProcess(orderId, qcDate, getQcRemark(qcFindings, reasonString, userId, action), 
					district, place, qcStatus, reasonCode, userId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	private String getQcRemark(String qcFindings, String reasonString, String userId, String action){
		StringBuilder qcRemaksSb= new StringBuilder(DateTime.now().toString(LtsDsQcProcessDetailFormDTO.DATETIME_FORMATTER));
		qcRemaksSb.append(" BY " + userId);
		if(StringUtils.isNotBlank(action)){
			qcRemaksSb.append("\n");
			qcRemaksSb.append(action + " ");
		}
		if(StringUtils.isNotBlank(reasonString)){
			qcRemaksSb.append(reasonString);
		}
		if(StringUtils.isNotBlank(qcFindings)){
			qcRemaksSb.append("\n");
			qcRemaksSb.append(qcFindings + "\n");
		}
		return qcRemaksSb.toString();
	}
	
	public void updateQcRemarks(String orderId, String qcFindings, String userId){
		updateQcProcess(orderId, null, qcFindings, null, null, null, null, null, userId, null);
	}

	public LtsDsQcDAO getLtsDsQcDao() {
		return ltsDsQcDao;
	}

	public void setLtsDsQcDao(LtsDsQcDAO ltsDsQcDao) {
		this.ltsDsQcDao = ltsDsQcDao;
	}

	public CodeLkupCacheService getLtsDsQcOrdStatusGrpLkupCacheService() {
		return ltsDsQcOrdStatusGrpLkupCacheService;
	}

	public void setLtsDsQcOrdStatusGrpLkupCacheService(
			CodeLkupCacheService ltsDsQcOrdStatusGrpLkupCacheService) {
		this.ltsDsQcOrdStatusGrpLkupCacheService = ltsDsQcOrdStatusGrpLkupCacheService;
	}

	public CodeLkupCacheService getLtsDsQcOrdStatusLkupCacheService() {
		return ltsDsQcOrdStatusLkupCacheService;
	}

	public void setLtsDsQcOrdStatusLkupCacheService(
			CodeLkupCacheService ltsDsQcOrdStatusLkupCacheService) {
		this.ltsDsQcOrdStatusLkupCacheService = ltsDsQcOrdStatusLkupCacheService;
	}

	public CodeLkupCacheService getLtsDsQcStatusLkupCacheService() {
		return ltsDsQcStatusLkupCacheService;
	}

	public void setLtsDsQcStatusLkupCacheService(
			CodeLkupCacheService ltsDsQcStatusLkupCacheService) {
		this.ltsDsQcStatusLkupCacheService = ltsDsQcStatusLkupCacheService;
	}

	public CodeLkupCacheService getLtsDsQcCannotQcCodeLkupCacheService() {
		return ltsDsQcCannotQcCodeLkupCacheService;
	}

	public void setLtsDsQcCannotQcCodeLkupCacheService(
			CodeLkupCacheService ltsDsQcCannotQcCodeLkupCacheService) {
		this.ltsDsQcCannotQcCodeLkupCacheService = ltsDsQcCannotQcCodeLkupCacheService;
	}

	public CodeLkupCacheService getLtsDsQcFailedCodeLkupCacheService() {
		return ltsDsQcFailedCodeLkupCacheService;
	}

	public void setLtsDsQcFailedCodeLkupCacheService(
			CodeLkupCacheService ltsDsQcFailedCodeLkupCacheService) {
		this.ltsDsQcFailedCodeLkupCacheService = ltsDsQcFailedCodeLkupCacheService;
	}
}
