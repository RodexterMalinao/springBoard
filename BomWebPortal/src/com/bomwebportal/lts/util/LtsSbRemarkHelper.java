package com.bomwebportal.lts.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;

public class LtsSbRemarkHelper {

	protected static final String RELATIONSHIP_SUB = "SUB";
	protected static final String RELATIONSHIP_OTHER = "NP";
	protected static final String RELATIONSHIP_OTHER_DISPLAY = "OTHERS";
	
	protected static final String REMARK_SB_ORDER = "Springboard ID: {0}";
	protected static final String REMARK_CREATE_ORDER = "Created: {0} {1} {2}({3})";
	protected static final String REMARK_ORDER_PLACED_BY = "ORDER PLACED BY: {0}";
	protected static final String REMARK_RELATIONSHIP = "RELATIONSHIP: {0} ";
	protected static final String REMARK_ID_NUM = "{0} NO. {1}";
	protected static final String REMARK_REAL_ID_NUM = "CUSTOMER REAL ID = {0} NO. {1}";
	protected static final String REMARK_LINE_SEPARATOR = "**";
	protected static final String REMARK_AF_SR_DATE = "AF SR DATE ON {0} {1}";
	protected static final String REMARK_BACK_DATE = "BACKDATE TO {0}";
	protected static final String REMARK_PREWIRING_DATE = "PREWIRING DATE ON {0} {1}";
	protected static final String REMARK_PORT_LATER = "CUSTOMER WILL {0} LATER (PIPB DN {1})";
	
	private static final String REMARK_RENEWAL_RETENTION = "{0} RETENTION";
	private static final String REMARK_RENEWAL_CONTACT_NAME_NUM = "CONTACT NAME / NO: {0} / {1}";
	private static final String REMARK_RENEWAL_CONTACT_MOBILE_NUM = "CONTACT MOBILE NO: {0}";
	
	private static final String REMARK_RENEWAL_STANDALONE_VAS = "STANDALONE VAS";
	
	private static final String REMARK_CONTACT_NAME_NUM = "INSTALL CONTACT NAME / NO: {0} / {1}";
	private static final String REMARK_CONTACT_MOBILE_NUM = "INSTALL CONTACT MOBILE NO: {0}";
	private static final String REMARK_MIGRATE_TO_EYEX = "EXTG DEL MIGRATE TO {0}({1})";
	private static final String REMARK_MIGRATE_EYE_TO_EYE = "{0} MIGRATE TO {1}({2})";
	private static final String REMARK_UPGRADE_TEL_NO = "ON TEL NO {0}";
	private static final String REMARK_RESOURCE_SHORTAGE = "SRD:NO RESOURCE, TID: {0}";
//	private static final String REMARK_IMS_BLACKLIST_CUST = "AF SR DATE ON {0} {1} AMEND TID TO {2} DUE TO IMS BLACKLIST CUSTOMER";
	private static final String REMARK_FIELD_WORK_PERMIT = "FIELD WORK PERMIT {0}";
//	private static final String REMARK_FROZEN_EXCHANGE = "PLEASE ADD EXTRA 5 DAYS SRD LEADTIME DUE TO NGN FROZEN EXCHANGE (EXCHANGE NAME {0}) = Y";
	private static final String REMARK_FROZEN_EXCHANGE = "NGN FROZEN EXCHANGE (EXCHANGE NAME {0}) = Y";
	private static final String REMARK_COLLECT_DEVICE = "FS: PLEASE COLLECT EYE DEVICE";
	
	private static final String REMARK_REFER_2ND_DEL = "REFER 2ND DEL OF DN {0}";
	private static final String REMARK_NN_CHANGE_CUTOVER_DATE = "CUTOVER DATE & TIME WINDOW = {0} {1}";
	private static final String REMARK_2N_PORTIN_NUM = "2N PORT-IN NO. {0} FS INFORMS CFMC TO PROVIDE PCF FROM NEW NN TO OLD NN IF THE SRD IS POSTPONED. FS TO ADD REMARKS IF NN HAS BEEN ARRANGED BY CFMC";
	private static final String REMARK_PRE_PAYMENT = "PRE-PAYMENT {0}"; 
	private static final String REMARK_SALES_MEMO_NUM = "SALES MEMO NO: {0}";
	private static final String REMARK_2N_BLOCKWIRE = "BLOCKWIRE OWND BY 2N/CUSTOMER - {0}";
	private static final String REMARK_EYE2A_FAULT_COLLECT_DEVICE = "FIELD SERVICE, PLEASE COLLECT EYE2A DEVICE (0GLV)";
	private static final String REMARK_RECONTRACT = "RE-CONTRACT {0} {1} FROM {2} (CUSTOMER NO: {3}) TO {4} (CUSTOMER NO: {5}) WEF FROM {6}"; 

	private static final String REMARK_SELF_PICKUP_LTS = "ALERT: (Self pick up with new device. If change to FS visit, pls cancel & re-issue order)";
	
	
	// IMS REMARK TEMPLATE
	private static final String REMARK_ADD_EYEX_SERVICE = "ADD {0} SERVICE {1}";
	private static final String REMARK_CASE_REC = "CASE REC'D ON ORDER RECEIVE DATE ";
	private static final String REMARK_CONTACT_CUST = "FS: PLEASE CONTACT CUSTOMER {0} @ {1} B/W";
	private static final String REMARK_LOGIN_ID = "LOGIN ID = {0}";
	private static final String REMARK_ACTUAL_APPOINTMENT = "ACTUAL APPOINTMENT 18:00 - 22:00";
	private static final String REMARK_BLACKLIST = "AMEND TID TO 31/12/{0} DUE TO {1} ADDRESS BLACKLIST";
	private static final String REMARK_BRM_1 = "FS: inform customer SSID / password of BRM";
	private static final String REMARK_BRM_2 = "FS: not ask customer to test the line";
	private static final String REMARK_SELF_PICKUP_IMS = "FS: no physical work (upgrade to eye3 by self pick up new device)";
	
	// CUSTOMER REMARK TEMPLATE
	private static final String CUST_REMARK_SB_ORDER = "*PS/{0}*";
//	private static final String CUST_REMARK_EYEX = "EYEX {0}";
	private static final String CUST_REMARK_UPGRADE_TEL_NO = "ON TEL NO {0}";
	private static final String CUST_REMARK_ORDER_PLACED_BY = "BY {0}";
	private static final String CUST_REMARK_RELATIONSHIP = "({0})";
	private static final String CUST_REMARK_CONTACT_NO = "CUSTOMER CONTACT NO: {0}";
	private static final String CUST_REMARK_ON_SHOP = "ON {0} AT {1} SHOP (S/N {2})";
	private static final String CUST_REMARK_ON_CALL_CENTER = "ON {0} AT {1} CALL CENTER (S/N {2})";
	private static final String CUST_REMARK_ON_PREMIER = "ON {0} AT {1} PREMIER TEAM (S/N {2})";
	private static final String CUST_REMARK_ON_DIRECT_SALES = "ON {0} AT DS {1} (S/N {2})";
	
	
	// 2DEL REMARK
	private static final String REMARK_REFER_1ST_DEL = "REFER 1ST DEL OF DN {0}";
	
	//DISCONNECT REMARK
	private static final String REMARK_DISCONNECT_SERVICE = "DISCONNECT {0} PE SIP ORDER";
	private static final String REMARK_REFER_DISCONNECT = "REFER PE DEL DN {0}";
	
	// New ACQUISITION REMARK (SBA)
	private static final String REMARK_NEW_ACQUISITION = "{0} ACQUISITION";
	
	// New ACQUISITION PIPB REMARK (SBA)
	private static final String PIPB_REMARK_ORDER_TYPE = "{0} ORDER";
	private static final String PIPB_PORT_BACK = "PORT BACK";
	private static final String PIPB_PORT_IN = "PORT IN";
	private static final String PIPB_REMARK_DNO = "DNO: {0}";
	private static final String PIPB_REMARK_SERIAL_NO = "{0} SERIAL NO: TO BE ADVISED";
	private static final String PIPB_REMARK_DN_FROM = "{0} DN FROM {1}: {2}";
	private static final String PIPB_REMARK_TIME_WINDOW = "TIME WINDOW: " + LtsConstant.VAR_SWITCHING_DATE;
	private static final String PIPB_REMARK_VISIT_TIME = "VISIT TIME: " + LtsConstant.VAR_INSTALLATION_DATE;
	private static final String PIPB_REMARK_ALERT = "ALERT F/S: PORT IN/OUT ORDER";
	private static final String PIPB_CUST_REMARK_PORT_IN_DN = "TEL NO. {0} IS PORTED-IN FROM {1}";
	private static final String PIPB_CUST_REMARK_LINE2 = "FOR E/R & CHANGE NO. STAFF SHOULD INFORM";
	private static final String PIPB_CUST_REMARK_LINE3 = "2N TEAM BY FAX TO 29625697 WHO WILL INFORM";
	private static final String PIPB_CUST_REMARK_LINE4 = "2N ACCORDINGLY";	
	private static final String PIPB_REMARK_WITH_EX_DIRECTORY = "EX-DIRY EXTG & TO BE CONT'D";
	private static final String PIPB_REMARK_REVISIT_SOCKET_LINE1 = "I/R AND SWAP THE HORIZONTAL BLOCKWIRING";
	private static final String PIPB_REMARK_REVISIT_SOCKET_LINE2 = "OF DN {0} WITH {1} DN {2}";
	
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_CUSTOMER = "Customer Name: " + LtsConstant.VAR_CUSTOMER_NAME;
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_NEW_DN = "New DN: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_PIPB_DN= "PIPB DN: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_WAIVE_CHARGE= "Waive Charge For DN Change (Code): C157 1DDV";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_REVISIT = "FS Re-Visit: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_APPLICATION = "Application Date: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_INSTALLATION = "Target Installation Date: " + LtsConstant.VAR_INSTALLATION_DATE;
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_UAMS = "UAMS No: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_SWITCHING = "Target telephone switching Date: " + LtsConstant.VAR_SWITCHING_DATE;
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_SALES = "Sales Information";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_STAFF = "Staff No: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_SALES_CODE= "Salesman Code: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_SALES_CHANNEL= "Sales Channel: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_TEAM = "Team / Shop Code: {0}";
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_SALES_CONTACT = "Sales Contact No: {0}";	
	private static final String NEW_DN_FIRST_THEN_PIPB_REMARK_BOC = "BOC: {0}";	
	
	private static final String LOST_MODEM_LTS_REMARK = "Valid case{0}. Modem is lost & waived (full set / partial)";
	private static final String LOST_MODEM_IMS_REMARK = "Valid case{0}. Modem is lost & waived (full set / partial)";
	
	//BOM2018061 WEEE REMARKS
//	private static final String REMARK_WEEE = "WEEE OPTION";
//	private static final String REMARK_WEEE_OPTION = "OPTION: {0}";

	
	public static String generateLtsCustomerRemark(SbOrderDTO sbOrder, DeviceDetailDTO selectedDevice, BasketDetailDTO selectedBasket, String thirdPartyRelation, String recontractRelation) {
		
		ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);

		String orderType = sbOrder.getOrderType();
		String orderSubType = sbOrder.getOrderSubType();
		
		String applicantName = null;
		String relationship = null;
		String applicantDocId = null;
		String applicantDocType = null;
		
		List<String> contactNumList = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(ltsServiceDetail.getAppointmentDtl().getCustContactMobile())) {
			contactNumList.add(ltsServiceDetail.getAppointmentDtl().getCustContactMobile());
		}
		if (StringUtils.isNotBlank(ltsServiceDetail.getAppointmentDtl().getCustContactFix())) {
			contactNumList.add(ltsServiceDetail.getAppointmentDtl().getCustContactFix());
		}
		
		String applicationDate = StringUtils.isEmpty(sbOrder.getAppDate()) ? "" : StringUtils
						.split(sbOrder.getAppDate(), " ")[0]; 
		
		if (ltsServiceDetail.getThirdPartyDtl() != null) {
			applicantName = ltsServiceDetail.getThirdPartyDtl().getTitle()
					+ " "
					+ ltsServiceDetail.getThirdPartyDtl().getAppntLastName()
					+ " "
					+ ltsServiceDetail.getThirdPartyDtl().getAppntFirstName();
			applicantDocId = ltsServiceDetail.getThirdPartyDtl().getAppntDocId();
			applicantDocType = ltsServiceDetail.getThirdPartyDtl().getAppntDocType();
			relationship = thirdPartyRelation;
		}
		else if (StringUtils.equals(ltsServiceDetail.getRecontractInd(), "Y") && ltsServiceDetail.getRecontract() != null) {
			if(LtsConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getRecontract().getIdDocType())) {
				applicantName = ltsServiceDetail.getRecontract().getCompanyName();
			}
			else {
				applicantName = ltsServiceDetail.getRecontract().getTitle()
				+ " "
				+ ltsServiceDetail.getRecontract().getCustLastName()
				+ " "
				+ ltsServiceDetail.getRecontract().getCustFirstName();	
			}
			
			applicantDocId = ltsServiceDetail.getRecontract().getIdDocNum();
			applicantDocType = ltsServiceDetail.getRecontract().getIdDocType();
//			relationship = RELATIONSHIP_SUB;
			relationship = recontractRelation;
		}
		else {
			
			if(LtsConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getAccount().getCustomer().getIdDocType())) {
				applicantName = ltsServiceDetail.getAccount().getCustomer().getCompanyName();
			}
			else {
				applicantName = ltsServiceDetail.getAccount().getCustomer().getTitle()
						+ " "
						+ ltsServiceDetail.getAccount().getCustomer().getLastName()
						+ " "
						+ ltsServiceDetail.getAccount().getCustomer().getFirstName();
			}
			applicantDocId = ltsServiceDetail.getAccount().getCustomer().getIdDocNum();
			applicantDocType = ltsServiceDetail.getAccount().getCustomer().getIdDocType();
			relationship = RELATIONSHIP_SUB;
		}
		

		StringBuilder custRemark = new StringBuilder();
		String srDate = StringUtils.isEmpty(ltsServiceDetail
				.getAppointmentDtl().getAppntStartTime()) ? "" : StringUtils
				.split(ltsServiceDetail.getAppointmentDtl()
						.getAppntStartTime(), " ")[0];

		if (((ServiceDetailLtsDTO)ltsServiceDetail).getUpdateDnProfile() != null) {
			String existCustName = ltsServiceDetail.getProfileAccount().getCustomer().getTitle() + " " + ltsServiceDetail.getProfileAccount().getCustomer().getLastName() + " " + ltsServiceDetail.getProfileAccount().getCustomer().getFirstName();
			String existCustNum = ltsServiceDetail.getProfileAccount().getCustomer().getCustNo();
			
			String recontractName = null;
			if (LtsConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getRecontract().getIdDocType())) {
				recontractName = ltsServiceDetail.getRecontract().getCompanyName();
			}
			else {
				recontractName = ltsServiceDetail.getRecontract().getTitle() + " " + ltsServiceDetail.getRecontract().getCustLastName() + " " + ltsServiceDetail.getRecontract().getCustFirstName(); 
			}
			
			String recontractCustNum =  ltsServiceDetail.getRecontract().getCustNum();
			
			String updDnProfile = "< Update DN Profile >";
	
			custRemark.append("*");
			custRemark.append("\n");
			custRemark.append("SBID: ").append(sbOrder.getOrderId());
			custRemark.append("\n");
			custRemark.append(MessageFormat.format(REMARK_RECONTRACT, new Object[] {updDnProfile, ltsServiceDetail.getSrvNum(), existCustName.toUpperCase(), existCustNum, recontractName.toUpperCase(), StringUtils.defaultIfEmpty(recontractCustNum, ""), srDate}));
			custRemark.append("\n");
		}
		
		custRemark.append("*");
		custRemark.append("\n");
		if (LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_SB_ORDER, new Object[] {sbOrder.getOrderId()}));	
		}
		else {
			custRemark.append(sbOrder.getOrderId());
		}
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderType)) {
			custRemark.append("\n");
			if(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderSubType)){
				custRemark.append(REMARK_RENEWAL_STANDALONE_VAS);
			}else{
				custRemark.append(MessageFormat.format(REMARK_RENEWAL_RETENTION, new Object[] {ltsServiceDetail.getFromProd()}));
			}
		}
		
		if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)) {
			custRemark.append("\n");
			custRemark.append(MessageFormat.format(REMARK_NEW_ACQUISITION, new Object[] {ltsServiceDetail.getToProd()}));
		}
		
		if (selectedDevice != null) {
			custRemark.append("\n");
			custRemark.append(selectedDevice.getDesc()).append(" ").append(StringUtils.equals(selectedBasket.getPeInd(), "Y") ? "PE" : "VOICE");	
		}
		
		custRemark.append("\n");
		custRemark.append(selectedBasket.getDesc());
		custRemark.append("\n");
		//##Modified by Max.R.Meng
		custRemark.append(MessageFormat.format(CUST_REMARK_UPGRADE_TEL_NO, new Object[] { ltsServiceDetail.getNewSrvNum() != null? ltsServiceDetail.getNewSrvNum() : ltsServiceDetail.getSrvNum() }));
		custRemark.append("\n");
		custRemark.append(MessageFormat.format(CUST_REMARK_ORDER_PLACED_BY, new Object[] { applicantName }));
		
		if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)) {
			if (StringUtils.isNotBlank(relationship)) {
				custRemark.append(" ").append(MessageFormat.format(CUST_REMARK_RELATIONSHIP, new Object[] { relationship }));			
			}
		} else {		
			if (StringUtils.isNotBlank(relationship)) {
				custRemark.append(" ").append(MessageFormat.format(CUST_REMARK_RELATIONSHIP, new Object[] { relationship }));
				custRemark.append(" ").append(MessageFormat.format(REMARK_ID_NUM, new Object[] {LtsConstant.DOC_TYPE_PASSPORT.equals(applicantDocType) ? "PASSPORT" : applicantDocType, applicantDocId }));
			}
			if (StringUtils.equals("Y", ltsServiceDetail.getDummyDocIdInd())) {
				custRemark.append("\n");
				custRemark.append(MessageFormat.format(REMARK_REAL_ID_NUM,
						new Object[] { LtsConstant.DOC_TYPE_PASSPORT.equals(ltsServiceDetail.getActualDocType()) ? "PASSPORT" : ltsServiceDetail.getActualDocType(), ltsServiceDetail.getActualDocId() }));
			}
		}	
		if (!contactNumList.isEmpty()) {
			custRemark.append("\n");
			custRemark.append(MessageFormat.format(CUST_REMARK_CONTACT_NO, new Object[] {StringUtils.join(contactNumList, " ")}));
		}
		custRemark.append("\n");
		
		if (LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_ON_SHOP, new Object[] {applicationDate, sbOrder.getShopCd(), sbOrder.getStaffNum() }));	
		}
		else if (LtsConstant.ORDER_MODE_CALL_CENTER.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_ON_CALL_CENTER, new Object[] {applicationDate, sbOrder.getSalesTeam(), sbOrder.getStaffNum() }));
		}
		else if (LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_ON_PREMIER, new Object[] {applicationDate, sbOrder.getSalesTeam(), sbOrder.getStaffNum() }));
		} 
		else if(LtsConstant.ORDER_MODE_DIRECT_SALES.equals(sbOrder.getOrderMode()) || LtsConstant.ORDER_MODE_DIRECT_SALES_NOW_TV_QA.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_ON_DIRECT_SALES, new Object[] {applicationDate, sbOrder.getSalesTeam(), sbOrder.getStaffNum() }));
		}
		
		
		return custRemark.toString();
	}
	
	public static String generateSecondDelCustomerRemark(SbOrderDTO sbOrder, ItemDetailDTO planItemDetail, String thirdPartyRelation) {
		
		ServiceDetailDTO secondDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		
		String applicantName = null;
		String applicantDocId = null;
		String applicantDocType = null;
		String relationship = null;
		
		List<String> contactNumList = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(secondDelServiceDetail.getAppointmentDtl().getCustContactMobile())) {
			contactNumList.add(secondDelServiceDetail.getAppointmentDtl().getCustContactMobile());
		}
		if (StringUtils.isNotBlank(secondDelServiceDetail.getAppointmentDtl().getCustContactFix())) {
			contactNumList.add(secondDelServiceDetail.getAppointmentDtl().getCustContactFix());
		}
		
		String applicationDate = StringUtils.isEmpty(sbOrder.getAppDate()) ? "" : StringUtils
						.split(sbOrder.getAppDate(), " ")[0]; 
		
		if (StringUtils.equals(secondDelServiceDetail.getThirdPartyAppln(), "Y") && secondDelServiceDetail.getThirdPartyDtl() != null) {
			
			applicantName = secondDelServiceDetail.getThirdPartyDtl().getTitle()
					+ " "
					+ secondDelServiceDetail.getThirdPartyDtl().getAppntLastName()
					+ " "
					+ secondDelServiceDetail.getThirdPartyDtl().getAppntFirstName();
			applicantDocId = secondDelServiceDetail.getThirdPartyDtl().getAppntDocId();
			applicantDocType = secondDelServiceDetail.getThirdPartyDtl().getAppntDocType();
			relationship = thirdPartyRelation;
		}
		else {
			if(LtsConstant.DOC_TYPE_HKBR.equals(secondDelServiceDetail.getAccount().getCustomer().getIdDocType())) {
				applicantName = secondDelServiceDetail.getAccount().getCustomer().getCompanyName();
			}
			else {
				applicantName = secondDelServiceDetail.getAccount().getCustomer().getTitle()
				+ " "
				+ secondDelServiceDetail.getAccount().getCustomer().getLastName()
				+ " "
				+ secondDelServiceDetail.getAccount().getCustomer().getFirstName();	
			}
			
			
			applicantDocId = secondDelServiceDetail.getAccount().getCustomer().getIdDocNum();
			applicantDocType = secondDelServiceDetail.getAccount().getCustomer().getIdDocType();
			relationship = RELATIONSHIP_SUB;
		}
		
		StringBuilder custRemark = new StringBuilder();
		
		custRemark.append("*");
		custRemark.append("\n");
		if (LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_SB_ORDER, new Object[] {sbOrder.getOrderId()}));	
		}
		else {
			custRemark.append(sbOrder.getOrderId());
		}
		custRemark.append("\n");
		custRemark.append(StringUtils.replace(planItemDetail.getItemDisplayHtml(), "<br/>", ""));
		custRemark.append("\n");
		custRemark.append(MessageFormat.format(CUST_REMARK_UPGRADE_TEL_NO, new Object[] { secondDelServiceDetail.getSrvNum() }));
		custRemark.append("\n");
		custRemark.append(MessageFormat.format(CUST_REMARK_ORDER_PLACED_BY, new Object[] { applicantName }));
		
		if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)) {
			if (StringUtils.isNotBlank(relationship)) {
				custRemark.append(" ").append(MessageFormat.format(CUST_REMARK_RELATIONSHIP, new Object[] { relationship }));			
			}			
		} else {
			if (StringUtils.isNotBlank(relationship)) {
				custRemark.append(" ").append(MessageFormat.format(CUST_REMARK_RELATIONSHIP, new Object[] { relationship }));
				custRemark.append(" ").append(MessageFormat.format(REMARK_ID_NUM, new Object[] {LtsConstant.DOC_TYPE_PASSPORT.equals(applicantDocType) ? "PASSPORT" : applicantDocType, applicantDocId }));
			}
			if (StringUtils.equals("Y", secondDelServiceDetail.getDummyDocIdInd())) {
				custRemark.append("\n");
				custRemark.append(MessageFormat.format(REMARK_REAL_ID_NUM,
						new Object[] {LtsConstant.DOC_TYPE_PASSPORT.equals(secondDelServiceDetail.getActualDocType()) ? "PASSPORT" : secondDelServiceDetail.getActualDocType(), secondDelServiceDetail.getActualDocId() }));
			}			
		}	
		
		if (!contactNumList.isEmpty()) {
			custRemark.append("\n");
			custRemark.append(MessageFormat.format(CUST_REMARK_CONTACT_NO, new Object[] {StringUtils.join(contactNumList, " ")}));
		}
		custRemark.append("\n");
		if (LtsConstant.ORDER_MODE_RETAIL.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_ON_SHOP, new Object[] {applicationDate, sbOrder.getShopCd(), sbOrder.getStaffNum() }));	
		}
		else if (LtsConstant.ORDER_MODE_CALL_CENTER.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_ON_CALL_CENTER, new Object[] {applicationDate, sbOrder.getSalesTeam(), sbOrder.getStaffNum() }));
		}
		else if (LtsConstant.ORDER_MODE_PREMIER.equals(sbOrder.getOrderMode())) {
			custRemark.append(MessageFormat.format(CUST_REMARK_ON_PREMIER, new Object[] {applicationDate, sbOrder.getSalesTeam(), sbOrder.getStaffNum() }));
		} 
		
		return custRemark.toString();
	}
	
	public static String generateSecondDelOrderRemark(SbOrderDTO sbOrder, ItemDetailDTO planItemDetail, String thirdPartyRelation) {
		ServiceDetailDTO upgradeServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailDTO secondDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		ServiceDetailDTO imsServiceDetail = LtsSbOrderHelper.getImsService(sbOrder);
		String applicantName = null;
		String applicantDocId = null;
		String applicantDocType = null;
		String relationship = null;
		
		if (upgradeServiceDetail == null || secondDelServiceDetail == null) {
			return null;
		}
		
		if (StringUtils.equals(secondDelServiceDetail.getThirdPartyAppln(), "Y") && secondDelServiceDetail.getThirdPartyDtl() != null) {
			applicantName = secondDelServiceDetail.getThirdPartyDtl().getTitle()
					+ " "
					+ secondDelServiceDetail.getThirdPartyDtl().getAppntLastName()
					+ " "
					+ secondDelServiceDetail.getThirdPartyDtl().getAppntFirstName();
			applicantDocId = secondDelServiceDetail.getThirdPartyDtl().getAppntDocId();
			applicantDocType = secondDelServiceDetail.getThirdPartyDtl().getAppntDocType();
			relationship = thirdPartyRelation;
		}
		else {
			if(LtsConstant.DOC_TYPE_HKBR.equals(secondDelServiceDetail.getAccount().getCustomer().getIdDocType())) {
				applicantName = secondDelServiceDetail.getAccount().getCustomer().getCompanyName();
			}
			else {
				applicantName = secondDelServiceDetail.getAccount().getCustomer().getTitle()
				+ " "
				+ secondDelServiceDetail.getAccount().getCustomer().getLastName()
				+ " "
				+ secondDelServiceDetail.getAccount().getCustomer().getFirstName();
			}
	
			applicantDocId = secondDelServiceDetail.getAccount().getCustomer().getIdDocNum();
			applicantDocType = secondDelServiceDetail.getAccount().getCustomer().getIdDocType();
			relationship = RELATIONSHIP_SUB;
		}
		

		String installContactName = secondDelServiceDetail.getAppointmentDtl().getInstContactName();
		String installContactNum = secondDelServiceDetail.getAppointmentDtl().getInstContactNum();
		String installMobileNum = secondDelServiceDetail.getAppointmentDtl().getInstContactMobile();
		
		StringBuilder orderRemark = new StringBuilder();
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER, new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER, 
					new Object[] { sbOrder.getStaffNum(),
						LtsDateFormatHelper.getSysDate(), sbOrder.getSalesTeam(),
						sbOrder.getSalesContactNum() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_ORDER_PLACED_BY, new Object[] { applicantName }));
		orderRemark.append("\n");
		
		if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)) {
			if (StringUtils.isNotBlank(relationship)) {
				orderRemark.append(MessageFormat.format(REMARK_RELATIONSHIP, new Object[] { relationship }));
			}			
		} else {
			if (StringUtils.isNotBlank(relationship)) {
				orderRemark.append(MessageFormat.format(REMARK_RELATIONSHIP, new Object[] { relationship }));
			}
			orderRemark.append(MessageFormat.format(REMARK_ID_NUM, new Object[] { LtsConstant.DOC_TYPE_PASSPORT.equals(applicantDocType) ? "PASSPORT" : applicantDocType, applicantDocId }));
			
			if (StringUtils.equals("Y", secondDelServiceDetail.getDummyDocIdInd())) {
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_REAL_ID_NUM,
						new Object[] {LtsConstant.DOC_TYPE_PASSPORT.equals(secondDelServiceDetail.getActualDocType()) ? "PASSPORT" : secondDelServiceDetail.getActualDocType(), secondDelServiceDetail.getActualDocId() }));
			}
		}
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_NAME_NUM, new Object[] {installContactName, installContactNum }));
		
		if (StringUtils.isNotBlank(installMobileNum)) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_CONTACT_MOBILE_NUM, new Object[] {installMobileNum }));			
		}
		orderRemark.append("\n");
		orderRemark.append(REMARK_LINE_SEPARATOR);
		
		if (planItemDetail != null) {
			orderRemark.append("\n");
			orderRemark.append(StringUtils.replace(planItemDetail.getItemDisplayHtml(), "<br/>", ""));	
		}
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_UPGRADE_TEL_NO, secondDelServiceDetail.getSrvNum()));
		orderRemark.append("\n");
		orderRemark.append(REMARK_LINE_SEPARATOR);
		
		if (upgradeServiceDetail != null) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_REFER_1ST_DEL, new Object[] {upgradeServiceDetail.getSrvNum()}));
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}

		if (StringUtils.equals(sbOrder.getAddress().getAddrInventory().getResourceShortageInd(), "Y") &&
				StringUtils.equals("Y", upgradeServiceDetail.getAppointmentDtl().getTidInd())
				&& StringUtils.isNotBlank(upgradeServiceDetail.getAppointmentDtl().getTidStartTime())) {
			
			String tiDate = StringUtils.isEmpty(upgradeServiceDetail
					.getAppointmentDtl().getTidStartTime()) ? "" : StringUtils
					.split(upgradeServiceDetail.getAppointmentDtl()
							.getTidStartTime(), " ")[0]; 
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_RESOURCE_SHORTAGE, new Object[] {tiDate}));
			orderRemark.append("\n");
		}
		else {
			
			String srDate = StringUtils.isEmpty(secondDelServiceDetail
					.getAppointmentDtl().getAppntStartTime()) ? "" : StringUtils
					.split(secondDelServiceDetail.getAppointmentDtl()
							.getAppntStartTime(), " ")[0]; 
			String assignTech = imsServiceDetail != null ? ((ServiceDetailOtherLtsDTO)imsServiceDetail).getAssgnTechnology() : null;
			String srTime = getRemarkTimeSlot(LtsDateFormatHelper.getFromToTimeFormat(secondDelServiceDetail.getAppointmentDtl().getAppntStartTime(), secondDelServiceDetail.getAppointmentDtl().getAppntEndTime()), assignTech);

			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_AF_SR_DATE, new Object[] {srDate , srTime}));
			orderRemark.append("\n");	
		}
		
		return orderRemark.toString();
	}
	
	public static String generateLtsOrderRemark(SbOrderDTO sbOrder,
			DeviceDetailDTO selectedDevice, BasketDetailDTO selectedBasket,
			String prepayment, String thirdPartyRelation,
			LookupItemDTO[] remarkTemplates, boolean pDisconnect,
			String recontractRelation, ItemDetailDTO[] manualWaivedItems, ItemDetailDTO epdItem) {
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailDTO imsServiceDetail = LtsSbOrderHelper.getImsService(sbOrder);
		ServiceDetailDTO secondDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		
		String applicantName = null;
		String applicantDocId = null;
		String applicantDocType = null;
		String relationship = null;
		
		if (StringUtils.equals(ltsServiceDetail.getThirdPartyAppln(), "Y") && ltsServiceDetail.getThirdPartyDtl() != null) {
			applicantName = ltsServiceDetail.getThirdPartyDtl().getTitle()
					+ " "
					+ ltsServiceDetail.getThirdPartyDtl().getAppntLastName()
					+ " "
					+ ltsServiceDetail.getThirdPartyDtl().getAppntFirstName();
			applicantDocId = ltsServiceDetail.getThirdPartyDtl().getAppntDocId();
			applicantDocType = ltsServiceDetail.getThirdPartyDtl().getAppntDocType();
			relationship = thirdPartyRelation;
		}
		else if (StringUtils.equals(ltsServiceDetail.getRecontractInd(), "Y") && ltsServiceDetail.getRecontract() != null) {
			
			if(LtsConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getRecontract().getIdDocType())) {
				applicantName = ltsServiceDetail.getRecontract().getCompanyName();
			}
			else {
				applicantName = ltsServiceDetail.getRecontract().getTitle()
				+ " "
				+ ltsServiceDetail.getRecontract().getCustLastName()
				+ " "
				+ ltsServiceDetail.getRecontract().getCustFirstName();	
			}
			
			applicantDocId = ltsServiceDetail.getRecontract().getIdDocNum();
			applicantDocType = ltsServiceDetail.getRecontract().getIdDocType();
			relationship = recontractRelation;
		}
		else {
			if(LtsConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getAccount().getCustomer().getIdDocType())) {
				applicantName = ltsServiceDetail.getAccount().getCustomer().getCompanyName();
			}
			else {
				applicantName = ltsServiceDetail.getAccount().getCustomer().getTitle()
						+ " "
						+ ltsServiceDetail.getAccount().getCustomer().getLastName()
						+ " "
						+ ltsServiceDetail.getAccount().getCustomer().getFirstName();
			}
			applicantDocId = ltsServiceDetail.getAccount().getCustomer().getIdDocNum();
			applicantDocType = ltsServiceDetail.getAccount().getCustomer().getIdDocType();
			relationship = RELATIONSHIP_SUB;
		}

		String installContactName = ltsServiceDetail.getAppointmentDtl().getInstContactName();
		String installContactNum = ltsServiceDetail.getAppointmentDtl().getInstContactNum();
		String installMobileNum = ltsServiceDetail.getAppointmentDtl().getInstContactMobile();
		
		StringBuilder orderRemark = new StringBuilder();
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER, new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER, 
					new Object[] { sbOrder.getStaffNum(),
						LtsDateFormatHelper.getSysDate(), sbOrder.getSalesTeam(),
						sbOrder.getSalesContactNum() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_ORDER_PLACED_BY, new Object[] { applicantName }));
		orderRemark.append("\n");
		
		if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)) {
			if (StringUtils.isNotBlank(relationship)) {
				orderRemark.append(MessageFormat.format(REMARK_RELATIONSHIP, new Object[] { relationship }));
			}			
		} else {
			if (StringUtils.isNotBlank(relationship)) {
				orderRemark.append(MessageFormat.format(REMARK_RELATIONSHIP, new Object[] { relationship }));
			}
			orderRemark.append(MessageFormat.format(REMARK_ID_NUM, new Object[] { LtsConstant.DOC_TYPE_PASSPORT.equals(applicantDocType) ? "PASSPORT" : applicantDocType, applicantDocId }));
			
			if (StringUtils.equals("Y", ltsServiceDetail.getDummyDocIdInd())) {
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_REAL_ID_NUM,
						new Object[] {LtsConstant.DOC_TYPE_PASSPORT.equals(ltsServiceDetail.getActualDocType()) ? "PASSPORT" : ltsServiceDetail.getActualDocType(), ltsServiceDetail.getActualDocId() }));
			}
		}		

		orderRemark.append("\n");
		
		orderRemark.append(MessageFormat.format(
				LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder
						.getOrderType()) ? REMARK_RENEWAL_CONTACT_NAME_NUM
						: REMARK_CONTACT_NAME_NUM, new Object[] {
						installContactName, installContactNum }));
		
		if (StringUtils.isNotBlank(installMobileNum)) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder
					.getOrderType()) ? REMARK_RENEWAL_CONTACT_MOBILE_NUM : REMARK_CONTACT_MOBILE_NUM, new Object[] {installMobileNum }));			
		}
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
			orderRemark.append("\n");
			if(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(sbOrder.getOrderSubType())){
				orderRemark.append(REMARK_RENEWAL_STANDALONE_VAS);
			}else{
				orderRemark.append(MessageFormat.format(REMARK_RENEWAL_RETENTION, new Object[] {ltsServiceDetail.getFromProd()}));
			}
		}
		
		if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)) {
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_NEW_ACQUISITION, new Object[] {ltsServiceDetail.getToProd()}));
		}
		
		orderRemark.append("\n");
		orderRemark.append(REMARK_LINE_SEPARATOR);

		String addOnRemark = ltsServiceDetail.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);

		if (StringUtils.isNotBlank(addOnRemark)) {
			orderRemark.append("\n");
			orderRemark.append(addOnRemark.toString());
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}

		orderRemark.append("\n");
		
		if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)) {
			if (selectedDevice != null) {
				orderRemark.append("\n");
				orderRemark.append(selectedDevice.getDesc()).append(" ").append(StringUtils.equals(selectedBasket.getPeInd(), "Y") ? "PE" : "VOICE");	
			}			
			orderRemark.append("\n");
			orderRemark.append(selectedBasket.getDesc());
			orderRemark.append("\n");
		}
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())) {
			if (StringUtils.equals(LtsConstant.LTS_PRODUCT_TYPE_DEL, ltsServiceDetail.getFromProd())) {
				orderRemark.append(MessageFormat.format(REMARK_MIGRATE_TO_EYEX,
						new Object[] {
								selectedDevice.getDesc(),
								StringUtils.equals(selectedBasket.getPeInd(),
										"Y") ? "PE" : "VOICE" }));		
			}
			else {
				orderRemark.append(MessageFormat.format(REMARK_MIGRATE_EYE_TO_EYE,
						new Object[] { ltsServiceDetail.getFromProd(),
								ltsServiceDetail.getToProd(),
								StringUtils.equals(selectedBasket.getPeInd(),
										"Y") ? "PE" : "VOICE" }));
			}
			
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
			orderRemark.append("\n");
			
			
			if(!pDisconnect){
				if (!StringUtils.equals(selectedDevice.getType(), LtsConstant.DEVICE_TYPE_EYE3A) && imsServiceDetail != null) {
					if (StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, ((ServiceDetailOtherLtsDTO) imsServiceDetail).getModemArrangement())
							&& !StringUtils.equals("Y", ((ServiceDetailOtherLtsDTO) imsServiceDetail).getNowtvMirrorInd())) {
						orderRemark.append(LtsConstant.MODEM_TYPE_1L1B);	
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
				
			}else{
				orderRemark.append(MessageFormat.format(REMARK_DISCONNECT_SERVICE, ltsServiceDetail.getFromProd()));
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_REFER_DISCONNECT, ltsServiceDetail.getSrvNum()));
				orderRemark.append("\n");
				orderRemark.append(REMARK_LINE_SEPARATOR);
			}
			
		}

		String srDate = StringUtils.isEmpty(ltsServiceDetail
				.getAppointmentDtl().getAppntStartTime()) ? "" : StringUtils
				.split(ltsServiceDetail.getAppointmentDtl()
						.getAppntStartTime(), " ")[0]; 
		
		String assignTech = imsServiceDetail != null ? ((ServiceDetailOtherLtsDTO)imsServiceDetail).getAssgnTechnology() : null;
		String srTime = getRemarkTimeSlot(LtsDateFormatHelper.getFromToTimeFormat(ltsServiceDetail.getAppointmentDtl().getAppntStartTime(), ltsServiceDetail.getAppointmentDtl().getAppntEndTime()), assignTech);

		if (StringUtils.equals(sbOrder.getAddress().getAddrInventory().getResourceShortageInd(), "Y") 
				&& StringUtils.equals("Y", ltsServiceDetail.getAppointmentDtl().getTidInd())
				&& StringUtils.isNotBlank(ltsServiceDetail.getAppointmentDtl().getTidStartTime())) {
			
			String tiDate = StringUtils.isEmpty(ltsServiceDetail
					.getAppointmentDtl().getTidStartTime()) ? "" : StringUtils
					.split(ltsServiceDetail.getAppointmentDtl()
							.getTidStartTime(), " ")[0]; 
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_RESOURCE_SHORTAGE, new Object[] {tiDate}));
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}
		else {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_AF_SR_DATE, new Object[] {srDate , srTime}));
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}
		
		if(StringUtils.isNotBlank(ltsServiceDetail.getAppointmentDtl().getPreWiringStartTime())
				&& StringUtils.isNotBlank(ltsServiceDetail.getAppointmentDtl().getPreWiringEndTime())){
			
			String preWiringDate = StringUtils.isEmpty(ltsServiceDetail
					.getAppointmentDtl().getPreWiringStartTime()) ? "" : StringUtils
					.split(ltsServiceDetail.getAppointmentDtl()
							.getPreWiringStartTime(), " ")[0]; 
			String preWiringTime = getRemarkTimeSlot(LtsDateFormatHelper.getFromToTimeFormat(ltsServiceDetail.getAppointmentDtl().getPreWiringStartTime(), ltsServiceDetail.getAppointmentDtl().getPreWiringEndTime()), assignTech);
			
			if(StringUtils.isNotBlank(preWiringDate)){
				orderRemark.append(MessageFormat.format(REMARK_PREWIRING_DATE, new Object[]{preWiringDate, preWiringTime}));
			}
		}
		
		if(!pDisconnect){
			if (StringUtils.isNotEmpty(prepayment)) {
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_PRE_PAYMENT, new Object[] {prepayment}));
				
				String salesMemoNum = LtsSbOrderHelper.getSalesMemoNum(ltsServiceDetail);
				if (StringUtils.isNotEmpty(salesMemoNum)) {
					orderRemark.append("\n");
					orderRemark.append(MessageFormat.format(REMARK_SALES_MEMO_NUM, new Object[] {salesMemoNum}));	
				}
				orderRemark.append("\n");
				orderRemark.append(REMARK_LINE_SEPARATOR);
				orderRemark.append("\n\n");
			}
			
			if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())) {
				if (StringUtils.isNotEmpty(sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay()) 
						&& !StringUtils.equals(sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay(), "0")) {
					orderRemark.append("\n");
					orderRemark.append(MessageFormat.format(REMARK_FIELD_WORK_PERMIT, new Object[] {sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay()}));
				}	
			}
			
			
			if (secondDelServiceDetail != null) {
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_REFER_2ND_DEL, new Object[] {secondDelServiceDetail.getSrvNum()}));
			}
			
			if (!StringUtils.equals(LtsConstant.DN_SOURCE_DN_PIPB, ltsServiceDetail.getDnSource())) {
				if (StringUtils.isNotEmpty(ltsServiceDetail.getAppointmentDtl().getCutOverStartTime())
						&& StringUtils.isNotEmpty(ltsServiceDetail.getAppointmentDtl().getCutOverEndTime())) {
					if (!StringUtils.equals(ltsServiceDetail.getAppointmentDtl().getAppntStartTime(), ltsServiceDetail.getAppointmentDtl().getCutOverStartTime())
							|| !StringUtils.equals(ltsServiceDetail.getAppointmentDtl().getAppntEndTime(), ltsServiceDetail.getAppointmentDtl().getCutOverEndTime())) {
						
						String cutOverDate = StringUtils.isEmpty(ltsServiceDetail.getAppointmentDtl().getCutOverStartTime()) ? "" : StringUtils
								.split(ltsServiceDetail.getAppointmentDtl().getCutOverStartTime(), " ")[0];
						
						String cutOverTime = getRemarkTimeSlot(LtsDateFormatHelper.getFromToTimeFormat(
								ltsServiceDetail.getAppointmentDtl().getCutOverStartTime(), ltsServiceDetail.getAppointmentDtl().getCutOverEndTime()), assignTech);
						
						orderRemark.append("\n");	
						orderRemark.append(MessageFormat.format(REMARK_NN_CHANGE_CUTOVER_DATE, new Object[] {cutOverDate, cutOverTime}));				
					}
				}
			}
			
			if (StringUtils.equals("Y", ((ServiceDetailLtsDTO)ltsServiceDetail).getTwoNInd())) {
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_2N_PORTIN_NUM, new Object[] {ltsServiceDetail.getSrvNum()}));
			}
			
			if (StringUtils.isNotBlank(sbOrder.getAddress().getAddrInventory()
					.getN2BuildingInd())
					&& StringUtils.equals("Y", ltsServiceDetail.getErInd())) {
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_2N_BLOCKWIRE, new Object[] {sbOrder.getAddress().getAddrInventory().getN2BuildingInd()}));
			}
			
			if (StringUtils.equals(((ServiceDetailLtsDTO)ltsServiceDetail).getFrozenExchInd(), "Y") && StringUtils.isNotBlank(((ServiceDetailLtsDTO)ltsServiceDetail).getDnExchangeId())) {
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_FROZEN_EXCHANGE, new Object[] {((ServiceDetailLtsDTO)ltsServiceDetail).getDnExchangeId()}));
				orderRemark.append("\n");
				orderRemark.append(REMARK_LINE_SEPARATOR);				
			}
			
			if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())) {
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
			}
			
			if (StringUtils.equals("Y", ltsServiceDetail.getRecontractInd()) && ltsServiceDetail.getRecontract() != null) {
				
				String existCustName = ltsServiceDetail.getProfileAccount().getCustomer().getTitle() + " " + ltsServiceDetail.getProfileAccount().getCustomer().getLastName() + " " + ltsServiceDetail.getProfileAccount().getCustomer().getFirstName();
				String existCustNum = ltsServiceDetail.getProfileAccount().getCustomer().getCustNo();
				
				String recontractName = null;
				if (LtsConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getRecontract().getIdDocType())) {
					recontractName = ltsServiceDetail.getRecontract().getCompanyName();
				}
				else {
					recontractName = ltsServiceDetail.getRecontract().getTitle() + " " + ltsServiceDetail.getRecontract().getCustLastName() + " " + ltsServiceDetail.getRecontract().getCustFirstName(); 
				}
				
				String recontractCustNum =  ltsServiceDetail.getRecontract().getCustNum();
				orderRemark.append("\n");
				
				String updDnProfile = "";
				if (((ServiceDetailLtsDTO)ltsServiceDetail).getUpdateDnProfile() != null) {
					updDnProfile = "< Update DN Profile >";
				}
				
				orderRemark.append(MessageFormat.format(REMARK_RECONTRACT, new Object[] {updDnProfile, ltsServiceDetail.getSrvNum(), existCustName.toUpperCase(), existCustNum, recontractName.toUpperCase(), StringUtils.defaultIfEmpty(recontractCustNum, ""), srDate}));
			}
			
			if (StringUtils.equals("Y", ((ServiceDetailLtsDTO)ltsServiceDetail).getReturnDeviceInd())) {
				orderRemark.append("\n");
				orderRemark.append(REMARK_COLLECT_DEVICE);
			}
			
		}

		
		if (ArrayUtils.isNotEmpty(manualWaivedItems)) {
			for (ItemDetailDTO manualWaivedItem : manualWaivedItems) {
				orderRemark.append("\n");
				orderRemark.append(StringUtils.replace(manualWaivedItem.getItemDisplayHtml(), "<br/>", ""));
				orderRemark.append(" - Waive Penalty Approved.");
				orderRemark.append("\n");
				orderRemark.append(REMARK_LINE_SEPARATOR);
			}
		}
		

		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())
				&& "Y".equals(sbOrder.getBackDateInd())) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_BACK_DATE, new Object[] {sbOrder.getAppDate()}));
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}
				
		if (LtsSbOrderHelper.isNewAcquistionOrder(sbOrder)) {
			ServiceDetailLtsDTO portLaterSrv = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
			if (portLaterSrv!=null) {
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_PORT_LATER, new Object[] {
						LtsSbHelper.isPortBackForPipb(portLaterSrv)?PIPB_PORT_BACK:PIPB_PORT_IN,
						LtsSbHelper.getDisplaySrvNum(portLaterSrv.getSrvNum())
						}));
				orderRemark.append("\n");
				orderRemark.append(REMARK_LINE_SEPARATOR);
			}			
		}
		
		if(LtsSbHelper.isPcdBundleFree(sbOrder))
		{
			orderRemark.append("\n");
			orderRemark.append("PCD SBID "+LtsSbHelper.getPcdSbid(sbOrder)+".");
			orderRemark.append("\n");
			orderRemark.append("FS, please work together with corresponding PCD IMS order.");
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}

		// Removed starting on 31 Oct due to policy changed. 
//		if (LtsConstant.BASKET_PROJECT_CD_EYE2A_FAULT.equals(selectedBasket.getProjectCd())
//				|| LtsConstant.BASKET_PROJECT_CD_EYE2A_FAULT_SPECIAL.equals(selectedBasket.getProjectCd())) {
//			orderRemark.append("\n");
//			orderRemark.append(REMARK_EYE2A_FAULT_COLLECT_DEVICE);
//		}
		
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
		
		
		String lostModemInd = LtsSbOrderHelper.getLostModemIndIfTrue(sbOrder);
		if(lostModemInd != null){
			String orderTypeDesc = null;
			if(LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())){
				orderTypeDesc = "eye3 ACQ";
			}
			if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())){
				if(LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(LtsSbOrderHelper.getLtsEyeService(sbOrder).getFromProd())){
					orderTypeDesc = "DEL upgrade";
				}else{
					orderTypeDesc = "eye upgrade";
				}
			}
			if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())){
				orderTypeDesc = "eye retention order with new device";
			}
			
			orderRemark.append("\n");
			orderTypeDesc = orderTypeDesc != null ? " - " + orderTypeDesc : "";
			orderRemark.append(MessageFormat.format(LOST_MODEM_LTS_REMARK, orderTypeDesc));
			orderRemark.append("\n");
			orderRemark.append(REMARK_LINE_SEPARATOR);
		}
		
//		//BOM2018061
//		if(epdItem != null){
//			orderRemark.append(REMARK_LINE_SEPARATOR);
//			orderRemark.append("\n");
//			orderRemark.append("\n");
//			orderRemark.append(REMARK_WEEE);
//			orderRemark.append("\n");
//			orderRemark.append(MessageFormat.format(REMARK_WEEE_OPTION, epdItem.getItemDesc()));
//			if(epdItem.getItemAttbs() != null && epdItem.getItemAttbs().length > 0){
//				for(ItemAttbDTO attb: epdItem.getItemAttbs()){
//					if(!StringUtils.equals(attb.getVisualInd(), "N")
//							&& StringUtils.isNotEmpty(attb.getAttbValue())){
//						orderRemark.append("\n");
//						orderRemark.append(StringUtils.upperCase(attb.getAttbDesc() + ": " + attb.getAttbValue()));
//					}
//					
//				}
//			}
//			orderRemark.append("\n");
//			orderRemark.append(REMARK_LINE_SEPARATOR);
//		}
		

		//TODO BOM2019014
		boolean selfPickFlag = false;
		if (ltsServiceDetail != null && ArrayUtils.isNotEmpty(ltsServiceDetail.getItemDtls())) {
			for (ItemDetailLtsDTO itemDetail : ltsServiceDetail.getItemDtls()) {
				if(LtsConstant.ITEM_TYPE_SELF_PICKUP.equals(itemDetail.getItemType())){
					selfPickFlag = true;
					break;
				}
			}
		}
		
		if(selfPickFlag){
			orderRemark.append("\n");
			orderRemark.append("\n*******************\n");
			orderRemark.append(REMARK_SELF_PICKUP_LTS);
			orderRemark.append("\n*******************\n");
		}
		
		
		return orderRemark.toString();
	}
	
	
	public static String generateImsOrderRemark(SbOrderDTO sbOrder, DeviceDetailDTO selectedDevice, ItemDetailDTO[] manualWaivedItems) {
		
		ServiceDetailOtherLtsDTO imsServiceDetail = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		
		String installContactName = ltsServiceDetail.getAppointmentDtl().getInstContactName();
		String installContactNum = ltsServiceDetail.getAppointmentDtl().getInstContactNum();
		String installMobileNum = ltsServiceDetail.getAppointmentDtl().getInstContactMobile();

		String assignTech = imsServiceDetail != null ? imsServiceDetail.getAssgnTechnology() : null;
		
		String srTime = getRemarkTimeSlot(LtsDateFormatHelper.getFromToTimeFormat(
				imsServiceDetail.getAppointmentDtl().getAppntStartTime(),
				imsServiceDetail.getAppointmentDtl().getAppntEndTime()), assignTech);
		
		String addOnRemark = ltsServiceDetail.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);
		
		List<String> blacklistLobList = new ArrayList<String>();  
		
		StringBuilder orderRemark = new StringBuilder();
		
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER, new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER, 
				new Object[] { sbOrder.getStaffNum(),
					LtsDateFormatHelper.getSysDate(), sbOrder.getSalesTeam(),
					sbOrder.getSalesContactNum() }));
		
		String imsFromProd = imsServiceDetail.getFromProd();
		String imsToProd = imsServiceDetail.getToProd();
		if (!(LtsConstant.IMS_PRODUCT_TYPE_NEW.equals(imsFromProd) && LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN.equals(imsToProd)
				|| StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getModemArrangement())
				|| LtsConstant.IMS_PRODUCT_TYPE_SD.equals(imsFromProd)  
				|| LtsConstant.IMS_PRODUCT_TYPE_HD.equals(imsFromProd)
				|| LtsConstant.IMS_PRODUCT_TYPE_NEW.equals(imsFromProd) && LtsConstant.IMS_PRODUCT_TYPE_PCD.equals(imsToProd)
				|| LtsConstant.IMS_PRODUCT_TYPE_NEW.equals(imsFromProd) && LtsConstant.IMS_PRODUCT_TYPE_PCD_HD.equals(imsToProd)
				|| LtsConstant.IMS_PRODUCT_TYPE_NEW.equals(imsFromProd) && LtsConstant.IMS_PRODUCT_TYPE_PCD_SD.equals(imsToProd)
				)
				|| LtsConstant.IMS_PRODUCT_TYPE_NEW.equals(imsFromProd) && LtsConstant.IMS_PRODUCT_TYPE_HD.equals(imsToProd)
				|| LtsConstant.IMS_PRODUCT_TYPE_NEW.equals(imsFromProd) && LtsConstant.IMS_PRODUCT_TYPE_SD.equals(imsToProd)) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_LOGIN_ID, new Object[] {((ServiceDetailOtherLtsDTO)imsServiceDetail).getLoginId()}));
		}
		
//		if (!(StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_NEW_WG, imsServiceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getModemArrangement())
//				|| StringUtils.equals(FsaServiceType.SDTV.getDesc(), ((ServiceDetailOtherLtsDTO)imsServiceDetail).getExistSrvTypeCd())  
//				|| StringUtils.equals(FsaServiceType.HDTV.getDesc(), ((ServiceDetailOtherLtsDTO)imsServiceDetail).getExistSrvTypeCd()))
//				|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_NEW_HD, imsServiceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_NEW_SD, imsServiceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD, imsServiceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD_HD, imsServiceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD_SD, imsServiceDetail.getSrvTypeCdAction())) {
//			orderRemark.append("\n");
//			orderRemark.append(MessageFormat.format(REMARK_LOGIN_ID, new Object[] {((ServiceDetailOtherLtsDTO)imsServiceDetail).getLoginId()}));
//		}
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_NAME_NUM, new Object[] {installContactName, installContactNum }));
		
		if (StringUtils.isNotBlank(installMobileNum)) {
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(REMARK_CONTACT_MOBILE_NUM, new Object[] {installMobileNum }));			
		}
		if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getAssgnTechnology()) 
				&& StringUtils.equals(srTime, LtsConstant.APPOINTMENT_TIMESLOT_EVENING)) {
			orderRemark.append("\n");
			orderRemark.append(REMARK_ACTUAL_APPOINTMENT);
		}
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())
				|| LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
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
		}
		
		if (StringUtils.isNotBlank(addOnRemark)) {
			orderRemark.append("\n");
			orderRemark.append(addOnRemark.toString());
		}
		
		if (ArrayUtils.isNotEmpty(manualWaivedItems)) {
			for (ItemDetailDTO manualWaivedItem : manualWaivedItems) {
				orderRemark.append("\n");
				orderRemark.append(StringUtils.replace(manualWaivedItem.getItemDisplayHtml(), "<br/>", ""));
				orderRemark.append(" - Waive Penalty Approved.");
			}
		}
		
		String tiDate = null;
		if (StringUtils.equals("Y", imsServiceDetail.getAppointmentDtl().getTidInd())
				&& StringUtils.isNotBlank(imsServiceDetail.getAppointmentDtl().getTidStartTime())) {
			
			tiDate = StringUtils.isEmpty(imsServiceDetail.getAppointmentDtl().getTidStartTime()) 
						? "" 
						: StringUtils.split(imsServiceDetail.getAppointmentDtl().getTidStartTime(), " ")[0]; 
			
			if(StringUtils.equals("Y", sbOrder.getAddress().getAddrInventory().getResourceShortageInd())
					&& StringUtils.isNotBlank(tiDate)){
				orderRemark.append("\n");
				orderRemark.append(MessageFormat.format(REMARK_RESOURCE_SHORTAGE, new Object[] {tiDate}));
			}
		}
		
		String lostModemInd = LtsSbOrderHelper.getLostModemIndIfTrue(sbOrder);
		if(lostModemInd != null){
			String orderTypeDesc = null;
			if(LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())){
				orderTypeDesc = "eye3 ACQ";
			}
			if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())){
				if(LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(LtsSbOrderHelper.getLtsEyeService(sbOrder).getFromProd())){
					orderTypeDesc = "DEL upgrade";
				}else{
					orderTypeDesc = "eye upgrade";
				}
			}
			if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())){
				orderTypeDesc = "eye retention";
			}

			orderRemark.append("\n");
			orderTypeDesc = orderTypeDesc != null ? " - " + orderTypeDesc : "";
			orderRemark.append(MessageFormat.format(LOST_MODEM_IMS_REMARK, orderTypeDesc));
		}
		
		if(LtsConstant.OS_TYPE_IOS.equals(ltsServiceDetail.getNewOsType())){
			if(StringUtils.contains(imsServiceDetail.getToProd(), LtsConstant.IMS_PRODUCT_TYPE_PCD)){
				if(!StringUtils.equals("Y", imsServiceDetail.getShareBrmWifi())
						&& !StringUtils.equals("E", imsServiceDetail.getShareRentalRouter())
						&& !StringUtils.equals("Y", imsServiceDetail.getShareRentalRouter())){
					orderRemark.append("\n");
					orderRemark.append(REMARK_BRM_1);
				}
			}else{
				orderRemark.append("\n");
				orderRemark.append(REMARK_BRM_1);
				orderRemark.append("\n");
				orderRemark.append(REMARK_BRM_2);
			}
		}
		
		

		//TODO BOM2019014
		boolean selfPickFlag = false;
		ServiceDetailLtsDTO coreLtsService = LtsSbOrderHelper.getLtsService(sbOrder);
		if (coreLtsService != null && ArrayUtils.isNotEmpty(coreLtsService.getItemDtls())) {
			for (ItemDetailLtsDTO itemDetail : coreLtsService.getItemDtls()) {
				if(LtsConstant.ITEM_TYPE_SELF_PICKUP.equals(itemDetail.getItemType())){
					selfPickFlag = true;
					break;
				}
			}
		}
		
		if(selfPickFlag){
			if(StringUtils.isNotBlank(tiDate)){
				orderRemark.append("\nSRD: " + tiDate);
			}
			orderRemark.append("\n");
			orderRemark.append(REMARK_SELF_PICKUP_IMS);
		}
		
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
		
		for (int start = 0; start < remark.length(); start += 250) {
			RemarkDetailLtsDTO remarkDetailLts = new RemarkDetailLtsDTO();
			remarkDetailLts.setRmkType(remarkType);
			remarkDetailLts.setRmkDtl(remark.substring(start, Math.min(remark.length(), start + 250))); 
			remarkDetailLts.setRmkSeq(String.valueOf(remarkDetailLtsList.size() + 1));
			remarkDetailLtsList.add(remarkDetailLts);
	    }
		
		if (remarkDetailLtsList.size() == 0) { 
			return null;
		}
		
		return remarkDetailLtsList.toArray(new RemarkDetailLtsDTO[remarkDetailLtsList.size()]);
	}

	private static String getRemarkTimeSlot(String timeSlot, String assignTech) {
		if (LtsConstant.TECHNOLOGY_PON.equals(assignTech)) {
			return LtsDateFormatHelper.convertToPonTime(timeSlot);
		}
		return LtsDateFormatHelper.convertToSBTime(timeSlot);
		
	}
	
	public static String generateAcqPipbOrderRemark(SbOrderDTO sbOrder, ServiceDetailLtsDTO ltsServiceDetail) {
		ServiceDetailLtsDTO pipbSrv = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls()); // get core PIPB service
		StringBuilder orderRemark = new StringBuilder();
		if (pipbSrv!=null && StringUtils.equals(LtsConstant.DN_SOURCE_DN_PIPB, ltsServiceDetail.getDnSource())) {
			orderRemark.append("*");
			orderRemark.append("\n");
			orderRemark.append("*****");
			orderRemark.append(MessageFormat.format(
					PIPB_REMARK_ORDER_TYPE, new Object[] {
							LtsSbHelper.isPortBackForPipb(ltsServiceDetail)?PIPB_PORT_BACK:PIPB_PORT_IN 
							}));
			orderRemark.append("*****");
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(
					PIPB_REMARK_DNO, new Object[] {pipbSrv.getPipb().getOperator2n()}));			
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(PIPB_REMARK_SERIAL_NO, new Object[] {
					pipbSrv.getPipb().getOperator2n()
					}));
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(PIPB_REMARK_DN_FROM, new Object[] {
					LtsSbHelper.isPortBackForPipb(ltsServiceDetail)?PIPB_PORT_BACK:PIPB_PORT_IN,
					pipbSrv.getPipb().getOperator2n(), 
					LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum())
					}));
			if (pipbSrv.getAppointmentDtl().getCutOverStartTime()!=null
					&& pipbSrv.getAppointmentDtl().getCutOverEndTime()!=null) {
				orderRemark.append("\n");
				if (LtsSbOrderHelper.isAcqPortLaterService(ltsServiceDetail)) {
					orderRemark.append(PIPB_REMARK_TIME_WINDOW);
				} else {
					orderRemark.append(StringUtils.replace(PIPB_REMARK_TIME_WINDOW, LtsConstant.VAR_SWITCHING_DATE,
							LtsDateFormatHelper.getDateFromDTOFormat(pipbSrv.getAppointmentDtl().getCutOverStartTime())
							+ " (" + LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(
									pipbSrv.getAppointmentDtl().getCutOverStartTime(), 
									pipbSrv.getAppointmentDtl().getCutOverEndTime())) + ")"));
				}				
			}			
			if (StringUtils.equals(LtsConstant.LTS_PIPB_REUSE_SOCKET_FORMER_FIXED_LINE, pipbSrv.getPipb().getReuseSocketType())) {
				if (pipbSrv.getAppointmentDtl().getAppntStartTime()!=null
						&& pipbSrv.getAppointmentDtl().getAppntEndTime()!=null) {
					orderRemark.append("\n");
					if (LtsSbOrderHelper.isAcqPortLaterService(ltsServiceDetail)) {
						orderRemark.append(PIPB_REMARK_VISIT_TIME);
					} else {
						orderRemark.append(StringUtils.replace(PIPB_REMARK_VISIT_TIME, LtsConstant.VAR_INSTALLATION_DATE,
								LtsDateFormatHelper.getDateFromDTOFormat(pipbSrv.getAppointmentDtl().getAppntStartTime())
								+ " (" + LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(
										pipbSrv.getAppointmentDtl().getAppntStartTime(), 
										pipbSrv.getAppointmentDtl().getAppntEndTime())) + ")"
						));
					}
				}
			}
			if (!LtsSbOrderHelper.isAcqPortLaterService(ltsServiceDetail)
					|| StringUtils.equals(LtsConstant.LTS_PIPB_REUSE_SOCKET_FORMER_FIXED_LINE, pipbSrv.getPipb().getReuseSocketType())) {
				orderRemark.append("\n");
				orderRemark.append(PIPB_REMARK_ALERT);
			}						
		}
		return orderRemark.toString();
	}
	
	public static String generateAcqPipbCustomerRemark(SbOrderDTO sbOrder, ServiceDetailLtsDTO ltsServiceDetail) {
		ServiceDetailLtsDTO pipbSrv = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls()); // get core PIPB service
		StringBuilder custRemark = new StringBuilder();
		if (pipbSrv!=null && StringUtils.equals(LtsConstant.DN_SOURCE_DN_PIPB, ltsServiceDetail.getDnSource())) {
			custRemark.append("*");
			custRemark.append("\n");
			custRemark.append(MessageFormat.format(
					PIPB_CUST_REMARK_PORT_IN_DN, new Object[] {
							LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()), 
							pipbSrv.getPipb().getOperator2n()
					}));
			custRemark.append("\n");
			custRemark.append(PIPB_CUST_REMARK_LINE2);
			custRemark.append("\n");
			custRemark.append(PIPB_CUST_REMARK_LINE3);
			custRemark.append("\n");
			custRemark.append(PIPB_CUST_REMARK_LINE4);
		}	
		
		return custRemark.toString();
	}
	
	public static String generateAcqPortLaterLtsAddOnRemark(SbOrderDTO sbOrder) {
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO portLaterSrv = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
		
		StringBuilder orderRemark = new StringBuilder();
	
		orderRemark.append(NEW_DN_FIRST_THEN_PIPB_REMARK_CUSTOMER);
				
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_NEW_DN, new Object[] { 
				LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum())
		}));
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_PIPB_DN, new Object[] { 
				LtsSbHelper.getDisplaySrvNum(portLaterSrv.getSrvNum())
		}));
		
		if (StringUtils.equals("Y", portLaterSrv.getPipb().getWaiveDnChangeInd())&& !LtsSbHelper.isPreInstall(sbOrder)) {
			orderRemark.append("\n");
			orderRemark.append(NEW_DN_FIRST_THEN_PIPB_REMARK_WAIVE_CHARGE);
		}	
			
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_REVISIT, new Object[] { 
				StringUtils.equals(LtsConstant.LTS_PIPB_REUSE_SOCKET_FORMER_FIXED_LINE, 
						portLaterSrv.getPipb().getReuseSocketType())?"Y":"N"
		}));
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_APPLICATION, new Object[] { 
				StringUtils.isEmpty(sbOrder.getAppDate()) ? "" : StringUtils
						.split(sbOrder.getAppDate(), " ")[0]
		}));
				
		if (StringUtils.equals(LtsConstant.LTS_PIPB_REUSE_SOCKET_FORMER_FIXED_LINE, portLaterSrv.getPipb().getReuseSocketType())) {		
			orderRemark.append("\n");
			orderRemark.append(NEW_DN_FIRST_THEN_PIPB_REMARK_INSTALLATION);
			
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_UAMS, new Object[] { 
					ltsServiceDetail.getAppointmentDtl().getSerialNum() 
					}));		
		}
		
		orderRemark.append("\n");
		orderRemark.append(NEW_DN_FIRST_THEN_PIPB_REMARK_SWITCHING);
		
		orderRemark.append("\n\n");
		
		orderRemark.append(NEW_DN_FIRST_THEN_PIPB_REMARK_SALES);
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_STAFF, new Object[] { 
				StringUtils.isEmpty(sbOrder.getStaffNum()) ? "" : sbOrder.getStaffNum()
		}));
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_SALES_CODE, new Object[] { 
				StringUtils.isEmpty(sbOrder.getSalesCd()) ? "" : sbOrder.getSalesCd()
		}));
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_SALES_CHANNEL, new Object[] { 
				StringUtils.isEmpty(sbOrder.getSalesChannel()) ? "" : sbOrder.getSalesChannel()
		}));
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_TEAM, new Object[] { 
				StringUtils.isEmpty(sbOrder.getSalesTeam()) ? "" : sbOrder.getSalesTeam()
		}));
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_SALES_CONTACT, new Object[] { 
				StringUtils.isEmpty(sbOrder.getSalesContactNum()) ? "" : sbOrder.getSalesContactNum()
		}));
		
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(NEW_DN_FIRST_THEN_PIPB_REMARK_BOC, new Object[] { 
				StringUtils.isEmpty(sbOrder.getBoc()) ? "" : sbOrder.getBoc()
		}));
		
		return orderRemark.toString();
	}
	
	public static String generateAcqPortLaterLtsOrderRemark(SbOrderDTO sbOrder,
			DeviceDetailDTO selectedDevice, BasketDetailDTO selectedBasket,
			String prepayment, String thirdPartyRelation,
			LookupItemDTO[] remarkTemplates, boolean pDisconnect,
			String recontractRelation, ItemDetailDTO[] manualWaivedItems) {
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO portLaterSrv = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
		
		String applicantName = null;
		String relationship = null;
				
		if (StringUtils.equals(ltsServiceDetail.getThirdPartyAppln(), "Y") && ltsServiceDetail.getThirdPartyDtl() != null) {
			applicantName = ltsServiceDetail.getThirdPartyDtl().getTitle()
					+ " "
					+ ltsServiceDetail.getThirdPartyDtl().getAppntLastName()
					+ " "
					+ ltsServiceDetail.getThirdPartyDtl().getAppntFirstName();
			relationship = thirdPartyRelation;
		}
		else if (StringUtils.equals(ltsServiceDetail.getRecontractInd(), "Y") && ltsServiceDetail.getRecontract() != null) {
			
			if(LtsConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getRecontract().getIdDocType())) {
				applicantName = ltsServiceDetail.getRecontract().getCompanyName();
			}
			else {
				applicantName = ltsServiceDetail.getRecontract().getTitle()
				+ " "
				+ ltsServiceDetail.getRecontract().getCustLastName()
				+ " "
				+ ltsServiceDetail.getRecontract().getCustFirstName();	
			}
			relationship = recontractRelation;
		}
		else {
			if(LtsConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getAccount().getCustomer().getIdDocType())) {
				applicantName = ltsServiceDetail.getAccount().getCustomer().getCompanyName();
			}
			else {
				applicantName = ltsServiceDetail.getAccount().getCustomer().getTitle()
						+ " "
						+ ltsServiceDetail.getAccount().getCustomer().getLastName()
						+ " "
						+ ltsServiceDetail.getAccount().getCustomer().getFirstName();
			}
			relationship = RELATIONSHIP_SUB;
		}

		String installContactName = ltsServiceDetail.getAppointmentDtl().getInstContactName();
		String installContactNum = ltsServiceDetail.getAppointmentDtl().getInstContactNum();
		
		StringBuilder orderRemark = new StringBuilder();
				
		orderRemark.append(MessageFormat.format(REMARK_SB_ORDER.toUpperCase(), new Object[] { sbOrder.getOrderId() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_CREATE_ORDER.toUpperCase(), 
					new Object[] { sbOrder.getStaffNum(),
						LtsDateFormatHelper.getSysDate(), sbOrder.getSalesTeam(),
						sbOrder.getSalesContactNum() }));
		orderRemark.append("\n");
		orderRemark.append(MessageFormat.format(REMARK_ORDER_PLACED_BY, new Object[] { applicantName }));
		orderRemark.append("\n");
		
		if (StringUtils.isNotBlank(relationship)) {
			orderRemark.append(MessageFormat.format(REMARK_RELATIONSHIP, new Object[] { relationship }));
		}	

		orderRemark.append("\n");
		
		orderRemark.append(MessageFormat.format(REMARK_CONTACT_NAME_NUM, new Object[] {
				installContactName, installContactNum }));
						
		if (StringUtils.equals("Y", portLaterSrv.getExDirInd())) {
			orderRemark.append("\n");
			orderRemark.append("**");
			orderRemark.append("\n");
			orderRemark.append(PIPB_REMARK_WITH_EX_DIRECTORY);
			orderRemark.append("\n");
			orderRemark.append("**");
		}
			
		if (StringUtils.equals(LtsConstant.LTS_PIPB_REUSE_SOCKET_FORMER_FIXED_LINE, 
				portLaterSrv.getPipb().getReuseSocketType())) {
			orderRemark.append("\n");
			orderRemark.append("**");
			orderRemark.append("\n");
			orderRemark.append(PIPB_REMARK_REVISIT_SOCKET_LINE1);
			orderRemark.append("\n");
			orderRemark.append(MessageFormat.format(PIPB_REMARK_REVISIT_SOCKET_LINE2, 
					new Object[] { LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()),
					LtsSbHelper.isPortBackForPipb(portLaterSrv)?PIPB_PORT_BACK:PIPB_PORT_IN,
					LtsSbHelper.getDisplaySrvNum(portLaterSrv.getSrvNum())
					}));
			orderRemark.append("\n");
			orderRemark.append("**");
		}			
		
		return orderRemark.toString();
	}
	
}
