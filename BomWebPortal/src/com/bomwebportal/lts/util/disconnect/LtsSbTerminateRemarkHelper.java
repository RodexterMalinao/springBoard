package com.bomwebportal.lts.util.disconnect;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSbRemarkHelper;

public class LtsSbTerminateRemarkHelper extends LtsSbRemarkHelper{

	private static final String REMARK_DISCONNECT_CALLING_CARD = "DISCONNECT CALLING CARD";
	private static final String REMARK_D_FORM_SERIAL = "D-FORM SERIAL: {0}";
	private static final String REMARK_FCH_SERIAL = "FCH : {0}";
	private static final String REMARK_WAIVE_PENALTY_REASON = "WAIVE PENALTY DUE TO {0}";
	private static final String REMARK_WAIVE_APPROVED_BY = "WAIVE 30 DAYS APPROVED BY {0}";
	private static final String REMARK_MODEM_COLLECT_ADDR = "PLS COLLECT MODEM AT ADDRESS: {0}";
	private static final String REMARK_CONTACT_NAME_NUM = "CONTACT NAME / NO: {0} / {1}";
	private static final String REMARK_CONTACT_MOBILE_NUM = "CONTACT MOBILE NO: {0}";
	private static final String REMARK_SR_DATE = "SR DATE ON: {0} {1}";
	private static final String REMARK_THIRD_PARTY_REMARKS = "REMARKS: {0}";
	
	//IMS REMARK
	private static final String REMARK_DISCONNECT_EYE_SERVICE = "DISCONNECT EYE SERVICE";
	private static final String REMARK_CONTACT_NAME = "CUSTOMER CONTACT NAME: {0}";
	private static final String REMARK_CONTACT_NUMBER = "CUSTOMER CONTACT NUMBER: {0}";
	private static final String REMARK_FS_FCH = "FS: no need physical work, refer FCH serial {0}";
	private static final String REMARK_NO_FS_PHYSCIAL_WORK = "No FS physcial work";
	
	private static final String DISCONNECT_LOST_MODEM_HU_REMARK = "Invalid case - eye disconnect and have eye usage.\nModem is lost & waived (full set / partial), approved by\nSM name {0} (FS: no physical work)";
	private static final String DISCONNECT_LOST_MODEM_NU_REMARK = "Valid case - eye disconnect and no eye usage.\nModem is lost & waived (full set / partial), approved by\nUM/SIC/CIC name {0} (FS: no physical work)";
	
	
	
	public static String generateLtsDisconnetOrderRemark(SbOrderDTO sbOrder, String approverName){
		ServiceDetailLtsDTO coreServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		
		String dFormSerial = coreServiceDetail.getDFormSerial();
		boolean isBackDate = "Y".equals(sbOrder.getBackDateInd());
		String fchSerial = coreServiceDetail.getFchNum();
		//TODO
//		String waivePenaltyReason =  coreServiceDetail.get
//		String waiveApprove = coreServiceDetail.get
		String modemCollectAddr = coreServiceDetail.getEqtCollectionAddr();
		
		
		StringBuilder orderRemark = new StringBuilder();
		
		String[] custInfo = getCustomerInfo(coreServiceDetail);
		String applicantName = custInfo[0];
		String applicantDocId = custInfo[1];
		String applicantDocType = custInfo[2];
		String relationship = custInfo[3];
		
		String installContactName = coreServiceDetail.getAppointmentDtl().getInstContactName();
		String installContactNum = coreServiceDetail.getAppointmentDtl().getInstContactNum();
		String installMobileNum = coreServiceDetail.getAppointmentDtl().getInstContactMobile();
		
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER, new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER, 
					new Object[] { sbOrder.getStaffNum(),
						LtsDateFormatHelper.getSysDate(), sbOrder.getSalesTeam(),
						sbOrder.getSalesContactNum() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_ORDER_PLACED_BY, new Object[] { applicantName }));
		orderRemark.append("\n");
		
		if (StringUtils.isNotBlank(relationship)) {
			orderRemark.append(MessageFormat.format(REMARK_RELATIONSHIP, new Object[] { relationship }));
		}

		if(coreServiceDetail.getThirdPartyDtl() != null && StringUtils.isNotBlank(coreServiceDetail.getThirdPartyDtl().getRemarks())){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_THIRD_PARTY_REMARKS, new Object[]{StringUtils.upperCase(coreServiceDetail.getThirdPartyDtl().getRemarks())}));
		}
		
		orderRemark.append(MessageFormat.format(REMARK_ID_NUM, new Object[] { LtsConstant.DOC_TYPE_PASSPORT.equals(applicantDocType) ? "PASSPORT" : applicantDocType, applicantDocId }));
		
		if (StringUtils.equals("Y", coreServiceDetail.getDummyDocIdInd())) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_REAL_ID_NUM,
					new Object[] {LtsConstant.DOC_TYPE_PASSPORT.equals(coreServiceDetail.getActualDocType()) ? "PASSPORT" : coreServiceDetail.getActualDocType(), coreServiceDetail.getActualDocId() }));
		}
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_NAME_NUM, new Object[] {installContactName, installContactNum }));
		
		if (StringUtils.isNotBlank(installMobileNum) ) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_CONTACT_MOBILE_NUM, new Object[] {installMobileNum }));			
		}
		orderRemark.append("\n");
		orderRemark.append(REMARK_LINE_SEPARATOR);
		
		String addOnRemark = coreServiceDetail.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);

		if (StringUtils.isNotBlank(addOnRemark)) {
			orderRemark.append("\n");
			orderRemark.append(addOnRemark.toString());
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}
		
		orderRemark.append("\n");
		orderRemark.append("*");
		orderRemark.append("\n");
		orderRemark.append("DISCONNECTION ORDER");
		
//		if(LtsConstant.GROUP_TYPE_EYE.equals(coreServiceDetail.getGrpType())){
//			orderRemark.append("\n");
//			orderRemark.append("FIELD SHOULD BUNDLE THIS ORDER WITH ANOTHER IMS ORDER FOR THE COLLECTION OF EYE TEL PHONE EQUIPMENT ON S/A");
//		}

		orderRemark.append("\n");
		orderRemark.append("*");
		
		if(StringUtils.equals(coreServiceDetail.getDiscCallingCardInd(), "Y")){
			orderRemark.append("\n");
			orderRemark.append(REMARK_DISCONNECT_CALLING_CARD);
		}

		if(StringUtils.isNotBlank(dFormSerial)){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_D_FORM_SERIAL, new Object[]{dFormSerial}));
		}

		if(StringUtils.isNotBlank(fchSerial)){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_FCH_SERIAL, new Object[]{fchSerial}));
		}
		
		if(isBackDate){
			String backDateSrd = coreServiceDetail.getAppointmentDtl().getAppntStartTime().substring(0, 10);
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_BACK_DATE, new Object[]{backDateSrd}));
		}
		
		if(StringUtils.equals("Y",coreServiceDetail.getForceFieldVisitInd()) && StringUtils.isNotBlank(modemCollectAddr) && StringUtils.equals("Y",coreServiceDetail.getExtEqtCollect())){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_MODEM_COLLECT_ADDR, new Object[]{modemCollectAddr}));
		}

		String lostModemInd = LtsSbOrderHelper.getLostModemIndIfTrue(sbOrder);
		if(LtsConstant.LOST_MODEM_HAVE_EYE_USAGE.equals(lostModemInd)){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(DISCONNECT_LOST_MODEM_HU_REMARK, approverName));
		}else if(LtsConstant.LOST_MODEM_NO_EYE_USAGE.equals(lostModemInd)){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(DISCONNECT_LOST_MODEM_NU_REMARK, approverName));
		}
		
		orderRemark.append("\n");
		orderRemark.append(REMARK_LINE_SEPARATOR);
		
		
		return orderRemark.toString();
	}
			
	public static String generateLtsDisconnetOrderImsRemark(SbOrderDTO sbOrder, String approverName){
		ServiceDetailOtherLtsDTO imsServiceDetail = (ServiceDetailOtherLtsDTO) (LtsSbOrderHelper.getImsService(sbOrder));
		ServiceDetailLtsDTO coreServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		
		if(imsServiceDetail == null){
			return null;
		}

		StringBuilder orderRemark = new StringBuilder();
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER, new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		
		orderRemark.append(REMARK_DISCONNECT_EYE_SERVICE);
		orderRemark.append("\n");
		
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER, 
				new Object[] { sbOrder.getStaffNum(),
					LtsDateFormatHelper.getSysDate(), sbOrder.getSalesTeam(),
					sbOrder.getSalesContactNum() }));
		orderRemark.append("\n");
		
		String srdate = LtsDateFormatHelper.getDateFromDTOFormat(imsServiceDetail.getAppointmentDtl().getAppntStartTime());
		String timeSlot = LtsDateFormatHelper.getTimeFromDateTimeSlot(imsServiceDetail.getAppointmentDtl().getAppntStartTime())
							+ "-"
							+  LtsDateFormatHelper.getTimeFromDateTimeSlot(imsServiceDetail.getAppointmentDtl().getAppntEndTime());
		
		orderRemark.append(MessageFormat.format(REMARK_SR_DATE, new Object[] {srdate, timeSlot}));
		orderRemark.append("\n");
		

		String[] custInfo = getCustomerInfo(coreServiceDetail);
		String applicantName = custInfo[0];
		
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_NAME, new Object[] {applicantName}));
		orderRemark.append("\n");

		String contactNum = StringUtils.isNotBlank(imsServiceDetail.getAppointmentDtl().getCustContactMobile())
				? imsServiceDetail.getAppointmentDtl().getCustContactMobile() 
						: imsServiceDetail.getAppointmentDtl().getCustContactFix();
		
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_NUMBER, new Object[] {contactNum}));
		orderRemark.append("\n");
		orderRemark.append("*");

		String addOnRemark = coreServiceDetail.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);
		if (StringUtils.isNotBlank(addOnRemark)) {
			orderRemark.append("\n");
			orderRemark.append(addOnRemark.toString());
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}
		
		String fchSerial = coreServiceDetail.getFchNum();
		if(StringUtils.isNotBlank(fchSerial)){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_FS_FCH, new Object[]{fchSerial}));
		}

		String modemCollectAddr = coreServiceDetail.getEqtCollectionAddr();
		if(StringUtils.equals("Y",coreServiceDetail.getForceFieldVisitInd()) && StringUtils.isNotBlank(modemCollectAddr) && StringUtils.equals("Y",coreServiceDetail.getExtEqtCollect())){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_MODEM_COLLECT_ADDR, new Object[]{modemCollectAddr}));
		}
		
		// For terminate eye service only (C order) and keep other PCD / TV profile. 
		if (!LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN.equals(imsServiceDetail.getFromProd())
				&& isOnlyTerminateEyeService(sbOrder)) {
			orderRemark.append("\n");
			orderRemark.append(REMARK_NO_FS_PHYSCIAL_WORK);
		}
		
		String lostModemInd = LtsSbOrderHelper.getLostModemIndIfTrue(sbOrder);
		if(LtsConstant.LOST_MODEM_HAVE_EYE_USAGE.equals(lostModemInd)){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(DISCONNECT_LOST_MODEM_HU_REMARK, approverName));
		}else if(LtsConstant.LOST_MODEM_NO_EYE_USAGE.equals(lostModemInd)){
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(DISCONNECT_LOST_MODEM_NU_REMARK, approverName));
		}
		
		return orderRemark.toString();
		
	}
	
	private static boolean isOnlyTerminateEyeService(SbOrderDTO pSbOrder) {
		
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(pSbOrder);
		
		if (imsService == null) {
			return false;
		}
		
		if (LtsConstant.FSA_SRV_TYPE_HDTV.equals(imsService.getFromProd())
				|| LtsConstant.FSA_SRV_TYPE_SDTV.equals(imsService.getFromProd())) {
			if (!StringUtils.equalsIgnoreCase("Y", imsService.getTerminateTv())) {
				return true;
			}
		}
		
		if (LtsConstant.FSA_SRV_TYPE_PCD.equals(imsService.getFromProd())) {
			if (!StringUtils.equalsIgnoreCase("Y", imsService.getTerminatePcd())) {
				return true;
			}
		}
		
		if (LtsConstant.FSA_SRV_TYPE_PCD_SDTV.equals(imsService.getFromProd())
				|| LtsConstant.FSA_SRV_TYPE_PCD_HDTV.equals(imsService.getFromProd())) {
			if (!StringUtils.equalsIgnoreCase("Y", imsService.getTerminatePcd())
					&& !StringUtils.equalsIgnoreCase("Y", imsService.getTerminateTv())) {
				return true;
			}
		}
		
		return false;
	}
	
	private static String[] getCustomerInfo(ServiceDetailDTO srvDtl){
		
		String applicantName = null;
		String applicantDocId = null;
		String applicantDocType = null;
		String relationship = null;
		
		ThirdPartyDetailLtsDTO thirdPartyDtl = srvDtl.getThirdPartyDtl();
		if (StringUtils.equals(srvDtl.getThirdPartyAppln(), "Y") && thirdPartyDtl != null) {
			applicantName = thirdPartyDtl.getTitle()
					+ " "
					+ thirdPartyDtl.getAppntLastName()
					+ " "
					+ thirdPartyDtl.getAppntFirstName();
			applicantDocId = thirdPartyDtl.getAppntDocId();
			applicantDocType = thirdPartyDtl.getAppntDocType();
			relationship = thirdPartyDtl.getRelationshipCode();
			
		}else {
			CustomerDetailLtsDTO custDtl = srvDtl.getAccount().getCustomer();
			if(LtsConstant.DOC_TYPE_HKBR.equals(custDtl.getIdDocType())) {
				applicantName = custDtl.getCompanyName();
			}
			else {
				applicantName = custDtl.getTitle()
						+ " "
						+ custDtl.getLastName()
						+ " "
						+ custDtl.getFirstName();
			}
			applicantDocId = custDtl.getIdDocNum();
			applicantDocType = custDtl.getIdDocType();
			relationship = RELATIONSHIP_SUB;
		}
		
		if(RELATIONSHIP_OTHER.equals(relationship)){
			relationship = RELATIONSHIP_OTHER_DISPLAY;
		}
		
		String[] custInfo = {applicantName, applicantDocId, applicantDocType, relationship};
		return custInfo;
	}
	
}
