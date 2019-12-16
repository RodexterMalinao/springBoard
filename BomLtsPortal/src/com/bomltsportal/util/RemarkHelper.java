package com.bomltsportal.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;

public class RemarkHelper {

private static final String RELATIONSHIP_SUB = "SUB";
	
	private static final String REMARK_SB_ORDER = "Springboard ID: {0}";
	private static final String REMARK_CREATE_ORDER = "Created: {0} {1}";
	private static final String REMARK_ORDER_PLACED_BY = "ORDER PLACED BY: {0}";
	private static final String REMARK_RELATIONSHIP = "RELATIONSHIP: {0} ";
	private static final String REMARK_ID_NUM = "ID NO. {0}";
	private static final String REMARK_REAL_ID_NUM = "CUSTOMER REAL ID = {0}";
	private static final String REMARK_CONTACT_NAME_NUM = "INSTALL CONTACT NAME / NO: {0} / {1}";
	private static final String REMARK_CONTACT_MOBILE_NUM = "INSTALL CONTACT MOBILE NO: {0}";
	private static final String REMARK_LINE_SEPARATOR = "**";
	private static final String REMARK_MIGRATE_TO_EYEX = "EXTG DEL MIGRATE TO {0}({1})";
	private static final String REMARK_UPGRADE_TEL_NO = "ON TEL NO {0}";
	private static final String REMARK_AF_SR_DATE = "AF SR DATE ON {0} {1}";
//	private static final String REMARK_IMS_BLACKLIST_CUST = "AF SR DATE ON {0} {1} AMEND TID TO {2} DUE TO IMS BLACKLIST CUSTOMER";
	private static final String REMARK_FIELD_WORK_PERMIT = "FIELD WORK PERMIT {0}";
	private static final String REMARK_FROZEN_EXCHANGE = "PLEASE ADD EXTRA 5 DAYS SRD LEADTIME DUE TO NGN FROZEN EXCHANGE (EXCHANGE NAME {0}) = Y";
	
	private static final String REMARK_REFER_2ND_DEL = "REFER 2ND DEL OF DN {0}";
	private static final String REMARK_NN_CHANGE_CUTOVER_DATE = "CUTOVER DATE & TIME WINDOW = {0} {1}";
	private static final String REMARK_2N_PORTIN_NUM = "2N PORT-IN NO. {0} FS INFORMS CFMC TO PROVIDE PCF FROM NEW NN TO OLD NN IF THE SRD IS POSTPONED. FS TO ADD REMARKS IF NN HAS BEEN ARRANGED BY CFMC";
	private static final String REMARK_PRE_PAYMENT = "PRE-PAYMENT {0}"; 
	private static final String REMARK_SALES_MEMO_NUM = "SALES MEMO NO: {0}";
	private static final String REMARK_2N_BLOCKWIRE = "BLOCKWIRE OWND BY 2N/CUSTOMER - {0}";
	
	// IMS REMARK TEMPLATE
	private static final String REMARK_ADD_EYEX_SERVICE = "ADD {0} SERVICE {1}";
	private static final String REMARK_CASE_REC = "CASE REC'D ON ORDER RECEIVE DATE ";
	private static final String REMARK_CONTACT_CUST = "FS: PLEASE CONTACT CUSTOMER {0} @ {1} B/W";
	private static final String REMARK_LOGIN_ID = "LOGIN ID = {0}";
	private static final String REMARK_ACTUAL_APPOINTMENT = "ACTUAL APPOINTMENT 18:00 - 22:00";
	private static final String REMARK_BLACKLIST = "AMEND TID TO 31/12/{0} DUE TO {1} ADDRESS BLACKLIST";
	
	// CUSTOMER REMARK TEMPLATE
	private static final String CUST_REMARK_SB_ORDER = "*OL/{0}*";
//	private static final String CUST_REMARK_EYEX = "EYEX {0}";
	private static final String CUST_REMARK_UPGRADE_TEL_NO = "ON TEL NO {0}";
	private static final String CUST_REMARK_ORDER_PLACED_BY = "BY {0}";
	private static final String CUST_REMARK_RELATIONSHIP = "({0})";
	private static final String CUST_REMARK_CONTACT_NO = "CUSTOMER CONTACT NO: {0}";
	private static final String CUST_REMARK_ON_SHOP = "ON {0} AT ONLINE SELLING ";
	
	
	public static String generateLtsCustomerRemark(SbOrderDTO sbOrder, DeviceDetailDTO selectedDevice, BasketDetailDTO selectedBasket) {
		
		ServiceDetailDTO eyeServiceDetail = LtsSbHelper.getLtsEyeService(sbOrder);
		ServiceDetailDTO delServiceDetail = LtsSbHelper.getDelServices(sbOrder);
		ServiceDetailDTO ltsServiceDetail = (eyeServiceDetail != null ? eyeServiceDetail : delServiceDetail);
		
		String applicantName = null;
		String relationship = null;
		String applicantDocId = null;
		
		List<String> contactNumList = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(ltsServiceDetail.getAppointmentDtl().getCustContactMobile())) {
			contactNumList.add(ltsServiceDetail.getAppointmentDtl().getCustContactMobile());
		}
		if (StringUtils.isNotBlank(ltsServiceDetail.getAppointmentDtl().getCustContactFix())) {
			contactNumList.add(ltsServiceDetail.getAppointmentDtl().getCustContactFix());
		}
		
		String applicationDate = StringUtils.isEmpty(sbOrder.getAppDate()) ? "" : StringUtils
						.split(sbOrder.getAppDate(), " ")[0]; 
		
		applicationDate = LtsDateFormatHelper.reformatDate(applicationDate, "dd/MM/yyyy", "yyyy/MM/dd");
		
		
//		if (StringUtils.equals(ltsServiceDetail.getThirdPartyAppln(), "Y") && ltsServiceDetail.getThirdPartyDtl() != null) {
//			applicantName = ltsServiceDetail.getThirdPartyDtl().getTitle()
//					+ " "
//					+ ltsServiceDetail.getThirdPartyDtl().getAppntLastName()
//					+ " "
//					+ ltsServiceDetail.getThirdPartyDtl().getAppntFirstName();
//			applicantDocId = ltsServiceDetail.getThirdPartyDtl().getAppntDocId();
//			relationship = thirdPartyRelation;
//		}
//		else {
			applicantName = ltsServiceDetail.getAccount().getCustomer().getTitle()
					+ " "
					+ ltsServiceDetail.getAccount().getCustomer().getLastName()
					+ " "
					+ ltsServiceDetail.getAccount().getCustomer().getFirstName();
			applicantDocId = ltsServiceDetail.getAccount().getCustomer().getIdDocNum();
			relationship = RELATIONSHIP_SUB;
//		}
		

		StringBuilder custRemark = new StringBuilder();
		custRemark.append("*");
		custRemark.append("\n");
		custRemark.append(MessageFormat.format(CUST_REMARK_SB_ORDER, new Object[] {sbOrder.getOrderId()}));
		if (selectedDevice != null) {
			custRemark.append("\n");
			custRemark.append(selectedDevice.getDesc());
		}
//		if(eyeServiceDetail != null) {
//			custRemark.append(" ").append(StringUtils.equals(selectedBasket.getPeInd(), "Y") ? "PE" : "VOICE");	
//		}
		custRemark.append("\n");
		custRemark.append(selectedBasket.getDesc());
		custRemark.append("\n");
		custRemark.append(MessageFormat.format(CUST_REMARK_UPGRADE_TEL_NO, new Object[] { ltsServiceDetail.getSrvNum() }));
		custRemark.append("\n");
		custRemark.append(MessageFormat.format(CUST_REMARK_ORDER_PLACED_BY, new Object[] { applicantName }));
		
		if (StringUtils.isNotBlank(relationship)) {
			custRemark.append(" ").append(MessageFormat.format(CUST_REMARK_RELATIONSHIP, new Object[] { relationship }));
			custRemark.append(" ").append(MessageFormat.format(REMARK_ID_NUM, new Object[] { applicantDocId }));
		}
		if (StringUtils.equals("Y", ltsServiceDetail.getDummyDocIdInd())) {
			custRemark.append("\n");
			custRemark.append(MessageFormat.format(REMARK_REAL_ID_NUM,
					new Object[] { ltsServiceDetail.getActualDocId() }));
		}
		if (!contactNumList.isEmpty()) {
			custRemark.append("\n");
			custRemark.append(MessageFormat.format(CUST_REMARK_CONTACT_NO, new Object[] {StringUtils.join(contactNumList, " ")}));
		}
		custRemark.append("\n");
		custRemark.append(MessageFormat.format(CUST_REMARK_ON_SHOP, new Object[] {applicationDate}));
		
		return custRemark.toString();
	}
	
	
	public static String generateLtsOrderRemark(SbOrderDTO sbOrder, DeviceDetailDTO selectedDevice, BasketDetailDTO selectedBasket
			, String prepayAmtTxt) {
		
		ServiceDetailDTO imsServiceDetail = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());
		ServiceDetailDTO eyeServiceDetail = LtsSbHelper.getLtsEyeService(sbOrder);
		ServiceDetailDTO delServiceDetail = LtsSbHelper.getDelServices(sbOrder);
		ServiceDetailDTO ltsServiceDetail = (eyeServiceDetail != null ? eyeServiceDetail : delServiceDetail);
		
		String applicantName = null;
		String applicantDocId = null;
		String relationship = null;
		
//		if (StringUtils.equals(ltsServiceDetail.getThirdPartyAppln(), "Y") && ltsServiceDetail.getThirdPartyDtl() != null) {
//			applicantName = ltsServiceDetail.getThirdPartyDtl().getTitle()
//					+ " "
//					+ ltsServiceDetail.getThirdPartyDtl().getAppntLastName()
//					+ " "
//					+ ltsServiceDetail.getThirdPartyDtl().getAppntFirstName();
//			applicantDocId = ltsServiceDetail.getThirdPartyDtl().getAppntDocId();
//			relationship = thirdPartyRelation;
//		}
//		else {
			applicantName = ltsServiceDetail.getAccount().getCustomer()
					.getTitle()
					+ " "
					+ ltsServiceDetail.getAccount().getCustomer().getLastName()
					+ " "
					+ ltsServiceDetail.getAccount().getCustomer().getFirstName();
			applicantDocId = ltsServiceDetail.getAccount().getCustomer().getIdDocNum();
			relationship = RELATIONSHIP_SUB;
//		}

		String installContactName = ltsServiceDetail.getAppointmentDtl().getInstContactName();
		String installContactNum = ltsServiceDetail.getAppointmentDtl().getInstContactNum();
		String installMobileNum = ltsServiceDetail.getAppointmentDtl().getInstContactMobile();
		
		StringBuilder orderRemark = new StringBuilder();
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER, new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER, 
					new Object[] { sbOrder.getStaffNum(),
						LtsDateFormatHelper.getSysDate()}));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_ORDER_PLACED_BY, new Object[] { applicantName }));
		orderRemark.append("\n");
		
		if (StringUtils.isNotBlank(relationship)) {
			orderRemark.append(MessageFormat.format(REMARK_RELATIONSHIP, new Object[] { relationship }));
		}
		orderRemark.append(MessageFormat.format(REMARK_ID_NUM, new Object[] { applicantDocId }));
		
		if (StringUtils.equals("Y", ltsServiceDetail.getDummyDocIdInd())) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_REAL_ID_NUM,
					new Object[] { ltsServiceDetail.getActualDocId() }));
		}

		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_NAME_NUM, new Object[] {installContactName, installContactNum }));
		
		if (StringUtils.isNotBlank(installMobileNum)) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_CONTACT_MOBILE_NUM, new Object[] {installMobileNum }));			
		}
		orderRemark.append("\n");
		orderRemark.append(REMARK_LINE_SEPARATOR);
		
//		String addOnRemark = upgradeServiceDetail.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);
//
//		if (StringUtils.isNotBlank(addOnRemark)) {
//			orderRemark.append("\n");
//			orderRemark.append(addOnRemark.toString());
//			orderRemark.append("\n");
//			orderRemark.append(REMARK_LINE_SEPARATOR);
//		}

//		orderRemark.append("\n");
//		orderRemark.append(MessageFormat.format(REMARK_MIGRATE_TO_EYEX,
//						new Object[] {
//								selectedDevice.getDesc(),
//								StringUtils.equals(selectedBasket.getPeInd(),
//										"Y") ? "PE" : "VOICE" }));
//		orderRemark.append("\n");
//		orderRemark.append(REMARK_LINE_SEPARATOR);
		
		if (eyeServiceDetail != null && !StringUtils.equals(selectedDevice.getType(), BomLtsConstant.DEVICE_TYPE_EYE3A)) {
			orderRemark.append("\n");
			if (StringUtils.equals(LtsBackendConstant.MODEM_TYPE_2L2B, ((ServiceDetailOtherLtsDTO) imsServiceDetail).getModemArrangement())
					&& !StringUtils.equals("Y", ((ServiceDetailOtherLtsDTO) imsServiceDetail).getNowtvMirrorInd())) {
				orderRemark.append(LtsBackendConstant.MODEM_TYPE_1L1B);	
			}
			else {
				orderRemark.append(((ServiceDetailOtherLtsDTO) imsServiceDetail).getModemArrangement());
			}	
		}
		
		
		
		orderRemark.append("\n");
		orderRemark.append(selectedBasket.getDesc());
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_UPGRADE_TEL_NO, ltsServiceDetail.getSrvNum()));
		orderRemark.append("\n");
		orderRemark.append(REMARK_LINE_SEPARATOR);
		
		
		String srDate = StringUtils.isEmpty(ltsServiceDetail
				.getAppointmentDtl().getAppntStartTime()) ? "" : StringUtils
				.split(ltsServiceDetail.getAppointmentDtl()
						.getAppntStartTime(), " ")[0]; 
		String srTime = LtsDateFormatHelper.getFromToTimeFormat(ltsServiceDetail.getAppointmentDtl().getAppntStartTime(), ltsServiceDetail.getAppointmentDtl().getAppntEndTime());
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_AF_SR_DATE, new Object[] {srDate , srTime}));
		orderRemark.append("\n");
		orderRemark.append(REMARK_LINE_SEPARATOR);
		
		if (StringUtils.isNotEmpty(prepayAmtTxt)) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_PRE_PAYMENT, new Object[] {"$"+prepayAmtTxt}));
			
//			String salesMemoNum = LtsSbHelper.getSalesMemoNum(ltsServiceDetail);
//			if (StringUtils.isNotEmpty(salesMemoNum)) {
//				orderRemark.append("\n");
//				orderRemark.append(MessageFormat.format(REMARK_SALES_MEMO_NUM, new Object[] {salesMemoNum}));	
//			}
		}

		if (StringUtils.isNotEmpty(sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay()) 
				&& !StringUtils.equals(sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay(), "0")) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_FIELD_WORK_PERMIT, new Object[] {sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay()}));
		}
		
//		if (secondDelServiceDetail != null) {
//			orderRemark.append("\n");
//			orderRemark.append(MessageFormat.format(REMARK_REFER_2ND_DEL, new Object[] {secondDelServiceDetail.getSrvNum()}));
//		}
		
//		if (StringUtils.isNotEmpty(upgradeServiceDetail.getAppointmentDtl().getCutOverStartTime())
//				&& StringUtils.isNotEmpty(upgradeServiceDetail.getAppointmentDtl().getCutOverEndTime())) {
//			if (!StringUtils.equals(upgradeServiceDetail.getAppointmentDtl().getAppntStartTime(), upgradeServiceDetail.getAppointmentDtl().getCutOverStartTime())
//					|| !StringUtils.equals(upgradeServiceDetail.getAppointmentDtl().getAppntEndTime(), upgradeServiceDetail.getAppointmentDtl().getCutOverEndTime())) {
//				
//				String cutOverDate = StringUtils.isEmpty(upgradeServiceDetail.getAppointmentDtl().getCutOverStartTime()) ? "" : StringUtils
//						.split(upgradeServiceDetail.getAppointmentDtl().getCutOverStartTime(), " ")[0];
//				
//				String cutOverTime = LtsDateFormatHelper.getFromToTimeFormat(
//						upgradeServiceDetail.getAppointmentDtl().getCutOverStartTime(), upgradeServiceDetail.getAppointmentDtl().getCutOverEndTime());
//				
//				orderRemark.append("\n");	
//				orderRemark.append(MessageFormat.format(REMARK_NN_CHANGE_CUTOVER_DATE, new Object[] {cutOverDate, cutOverTime}));				
//			}
//		}
		
		
		// PORT-IN
		if ((delServiceDetail != null && StringUtils.equals(BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_PORT_IN_DEL, delServiceDetail.getSrvTypeCdAction())) ||
			(eyeServiceDetail != null && StringUtils.equals(BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_PORT_IN_EYE, eyeServiceDetail.getSrvTypeCdAction()))) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_2N_PORTIN_NUM, new Object[] {ltsServiceDetail.getSrvNum()}));
		}
		
		
		// Remove 2N Blockwire remark due to duplicated with BOM auto-gen remark
//		if (StringUtils.isNotBlank(sbOrder.getAddress().getAddrInventory()
//				.getN2BuildingInd())) {
//			orderRemark.append("\n");
//			orderRemark.append(MessageFormat.format(REMARK_2N_BLOCKWIRE, new Object[] {sbOrder.getAddress().getAddrInventory().getN2BuildingInd()}));
//		}
//		
//		if (StringUtils.equals(((ServiceDetailLtsDTO)upgradeServiceDetail).getFrozenExchInd(), "Y") && StringUtils.isNotBlank(((ServiceDetailLtsDTO)upgradeServiceDetail).getDnExchangeId())) {
//			orderRemark.append("\n");
//			orderRemark.append(MessageFormat.format(REMARK_FROZEN_EXCHANGE, new Object[] {((ServiceDetailLtsDTO)upgradeServiceDetail).getDnExchangeId()}));
//			orderRemark.append("\n");
//			orderRemark.append(REMARK_LINE_SEPARATOR);				
//		}
		
		List<String> blacklistLobList = new ArrayList<String>(); 
		if (StringUtils.equals(sbOrder.getAddress().getBlacklistInd(), "Y") ) {
			blacklistLobList.add("IMS");
		}
		if (StringUtils.equals(sbOrder.getAddress().getLtsBlacklistInd(), "Y") ) {
			blacklistLobList.add("LTS");
		} 
		if (!blacklistLobList.isEmpty()) {
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.YEAR, 1);
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_BLACKLIST, new Object[] {String.valueOf(cal.get(Calendar.YEAR)), StringUtils.join(blacklistLobList, " & ") }));
		}
		
		// ER Remark
//		if (StringUtils.equals(((ServiceDetailLtsDTO)upgradeServiceDetail).getErInd(), "Y")) {
//			if (!ArrayUtils.isEmpty(remarkTemplates)) {
//				
//				String remarkTemplateKey = LtsConstant.CODE_LKUP_EYE_ER_REMARK;
//				ItemDetailLtsDTO[] subcItems = upgradeServiceDetail.getItemDtls();
//				
//				if (ArrayUtils.isNotEmpty(subcItems)) {
//					for (ItemDetailLtsDTO itemDetail : subcItems) {
//						if (StringUtils.equals(LtsConstant.ITEM_TYPE_INSTALL, itemDetail.getItemType())) {
//							remarkTemplateKey = LtsConstant.CODE_LKUP_EYE_ER_INSTALL_REMARK;
//							break;
//						}
//					}
//				}
//				
//				for (LookupItemDTO template : remarkTemplates) {
//					if (StringUtils.equals(remarkTemplateKey, template.getItemKey())
//							&& StringUtils.isNotBlank((String)template.getItemValue())) {
//						orderRemark.append("\n");
//						orderRemark.append((String)template.getItemValue());
//					}	
//				}	
//			}
//		}
		
		
		
		return orderRemark.toString();
	}
	
	public static String generateImsOrderRemark(SbOrderDTO sbOrder, DeviceDetailDTO selectedDevice) {
		
		ServiceDetailDTO imsServiceDetail = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());
		ServiceDetailDTO eyeServiceDetail = LtsSbHelper.getLtsEyeService(sbOrder);
		ServiceDetailDTO delServiceDetail = LtsSbHelper.getDelServices(sbOrder);
		ServiceDetailDTO ltsServiceDetail = (eyeServiceDetail != null ? eyeServiceDetail : delServiceDetail);
		
		String installContactName = ltsServiceDetail.getAppointmentDtl().getInstContactName();
		String installContactNum = ltsServiceDetail.getAppointmentDtl().getInstContactNum();
		String installMobileNum = ltsServiceDetail.getAppointmentDtl().getInstContactMobile();

		String srDate = StringUtils.isEmpty(imsServiceDetail
				.getAppointmentDtl().getAppntStartTime()) ? ""
				: StringUtils.split(imsServiceDetail.getAppointmentDtl()
						.getAppntStartTime(), " ")[0];
		String srTime = LtsDateFormatHelper.getFromToTimeFormat(
				imsServiceDetail.getAppointmentDtl().getAppntStartTime(),
				imsServiceDetail.getAppointmentDtl().getAppntEndTime());
		
//		String addOnRemark = upgradeServiceDetail.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);
		
		List<String> blacklistLobList = new ArrayList<String>();  
		
		StringBuilder orderRemark = new StringBuilder();
		
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER, new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER, 
				new Object[] { sbOrder.getStaffNum(),
					LtsDateFormatHelper.getSysDate()}));
		if (!(StringUtils.equals(BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_WG, imsServiceDetail.getSrvTypeCdAction())
				|| StringUtils.equals(BomLtsConstant.MODEM_TYPE_2L2B, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getModemArrangement())
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_SDTV, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getExistSrvTypeCd())  
				|| StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_HDTV, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getExistSrvTypeCd()))
				|| StringUtils.equals(BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_HD, imsServiceDetail.getSrvTypeCdAction())
				|| StringUtils.equals(BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_SD, imsServiceDetail.getSrvTypeCdAction())) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_LOGIN_ID, new Object[] {((ServiceDetailOtherLtsDTO)imsServiceDetail).getLoginId()}));
		}
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_NAME_NUM, new Object[] {installContactName, installContactNum }));
		
		if (StringUtils.isNotBlank(installMobileNum)) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_CONTACT_MOBILE_NUM, new Object[] {installMobileNum }));			
		}
		if (StringUtils.equals(BomLtsConstant.TECHNOLOGY_PON, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getAssgnTechnology()) 
				&& StringUtils.equals(srTime, "18:00 - 20:00")) {
			orderRemark.append("\n");
			orderRemark.append(REMARK_ACTUAL_APPOINTMENT);
		}
		if (StringUtils.isNotEmpty(sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay()) 
				&& !StringUtils.equals(sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay(), "0")) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_FIELD_WORK_PERMIT, new Object[] {sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay()}));
		}
		if (StringUtils.equals(sbOrder.getAddress().getBlacklistInd(), "Y") ) {
			blacklistLobList.add("IMS");
		}
		if (StringUtils.equals(sbOrder.getAddress().getLtsBlacklistInd(), "Y") ) {
			blacklistLobList.add("LTS");
		} 
		
		if (!blacklistLobList.isEmpty()) {
			Calendar cal = new GregorianCalendar();
			cal.add(Calendar.YEAR, 1);
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_BLACKLIST, new Object[] {String.valueOf(cal.get(Calendar.YEAR)), StringUtils.join(blacklistLobList, " & ") }));
		}
		
//		if (StringUtils.isNotBlank(addOnRemark)) {
//			orderRemark.append("\n");
//			orderRemark.append(addOnRemark.toString());
//		}
		
		/** Order Remark Template From FRS 
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER, new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_ADD_EYEX_SERVICE,
				new Object[] {selectedDevice.getDesc(), ((ServiceDetailOtherLtsDTO) imsServiceDetail)
						.getModemArrangement()}));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER, 
					new Object[] { sbOrder.getStaffNum(),
						LtsDateFormatHelper.getSysDate(), sbOrder.getShopCd(),
						sbOrder.getSalesContactNum() }));
		orderRemark.append("\n");
		orderRemark.append(REMARK_CASE_REC).append(StringUtils.isEmpty(sbOrder.getAppDate()) ? "" : StringUtils
				.split(sbOrder.getAppDate(), " ")[0]);
		orderRemark.append("\n");

		orderRemark.append(MessageFormat.format(REMARK_AF_SR_DATE, new Object[] {srDate , srTime}));
		
		if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getAssgnTechnology()) 
				&& StringUtils.equals(srTime, "18:00 - 20:00")) {
			orderRemark.append("\n");
			orderRemark.append(REMARK_ACTUAL_APPOINTMENT);
		}
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_CUST,
				new Object[] {
						imsServiceDetail.getAppointmentDtl().getInstContactName(),
						imsServiceDetail.getAppointmentDtl().getInstContactNum() + "(PHONE) " +
						(StringUtils.isNotEmpty(imsServiceDetail.getAppointmentDtl().getInstContactMobile()) ? imsServiceDetail.getAppointmentDtl().getInstContactMobile() + "(MOBILE) " : "") }));
		
		if (!(StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_NEW_WG, imsServiceDetail.getSrvTypeCdAction())
				|| StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getModemArrangement()))) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_LOGIN_ID, new Object[] {((ServiceDetailOtherLtsDTO)imsServiceDetail).getLoginId()}));
		}
		
		if (StringUtils.isNotEmpty(sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay()) 
				&& !StringUtils.equals(sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay(), "0")) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_FIELD_WORK_PERMIT, new Object[] {sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay()}));
		}
		
		if (StringUtils.isNotBlank(addOnRemark)) {
			orderRemark.append("\n");
			orderRemark.append(addOnRemark.toString());
		}
		**/
		
		return orderRemark.toString();
	}

	public static RemarkDetailLtsDTO[] createRemarkDetails(String remark, String remarkType) {
		if (StringUtils.isEmpty(remark)) {
			return null;
		}
		
		List<RemarkDetailLtsDTO> remarkDetailLtsList = new ArrayList<RemarkDetailLtsDTO>();
		
		for (int start = 0; start < remark.length(); start += 240) {
			RemarkDetailLtsDTO remarkDetailLts = new RemarkDetailLtsDTO();
			remarkDetailLts.setRmkType(remarkType);
			remarkDetailLts.setRmkDtl(remark.substring(start, Math.min(remark.length(), start + 240))); 
			remarkDetailLts.setRmkSeq(String.valueOf(remarkDetailLtsList.size() + 1));
			remarkDetailLtsList.add(remarkDetailLts);
	    }
		
		if (remarkDetailLtsList.size() == 0) { 
			return null;
		}
		
		return remarkDetailLtsList.toArray(new RemarkDetailLtsDTO[remarkDetailLtsList.size()]);
	}
	
	
	
	
}
