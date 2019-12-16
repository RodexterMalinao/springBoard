package com.bomwebportal.lts.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public class LtsBackendConstant extends LtsProfileConstant {

	public static final String IO_IND_INSTALL = "I";
	public static final String IO_IND_OUT = "O";
	public static final String IO_IND_SPACE = " ";
	
	public static final String ORDER_STATUS_INITIAL = "I";
	public static final String ORDER_STATUS_SUBMITTED = "D";
	public static final String ORDER_STATUS_SUSPENDED = "S";
	public static final String ORDER_STATUS_CANCELLED = "C";
	public static final String ORDER_STATUS_CLOSED = "L";
	public static final String ORDER_STATUS_CREATE_BOM = "B";
	public static final String ORDER_STATUS_PENDING_BOM = "P";
	public static final String ORDER_STATUS_EXTRACTED = "E";
	public static final String ORDER_STATUS_FORCED_WQ = "F";
	public static final String ORDER_STATUS_PENDING_APPROVAL = "G";
	public static final String ORDER_STATUS_APPROVAL_REJECTED = "R";
	public static final String ORDER_STATUS_PENDING_SIGNOFF = "O";
	public static final String ORDER_STATUS_AWAIT_PREPAYMENT = "W";
	public static final String ORDER_STATUS_AWAIT_QC = "Q";
	
	public static final String ORDER_STATUS_REASON_AWAIT_PREPAY = "PREPAY";
	public static final String ORDER_STATUS_REASON_AWAIT_APPROVAL = "APPROVAL";
	
	public static final String BOM_STATUS_DISTRIBUTED = "D";
	public static final String BOM_STATUS_COMPLETED = "L";
	public static final String BOM_STATUS_CANCELLED = "C";	
	public static final String BOM_READONLY_SWITCH_ON = "Y";
	public static final String BOM_READONLY_SWTICH_OFF = "N";	
	public static final String AUTO_AMEND_SWITCH_ON = "Y";
	public static final String AUTO_AMEND_SWTICH_OFF = "N";
	
	public static final String DRG_ORDER_STATUS_COMPLETED = "L";
	public static final String DRG_ORDER_STATUS_CANCELLED = "C";
	public static final String DRG_ORDER_STATUS_NUMBER_INVESTIGATE = "I";
	public static final String DRG_ORDER_STATUS_PAIR_INVESTIGATE = "P";
	public static final String DRG_ORDER_STATUS_ADDRESS_INVESTIGATE = "A";
	public static final String DRG_ORDER_STATUS_DISTRIBUTED = "D";
	public static final String DRG_ORDER_STATUS_SUSPENDED = "S";
	
	public static final String CC_MASK = "XXXXXXXX";
	
	public static final String ITEM_MDO_MANDATORY = "M";
	public static final String ITEM_MDO_DEFAULT = "D";
	public static final String ITEM_MDO_OPTIONAL = "O";
	
	public static final String CHANNEL_ID_SPRINGBOARD_RETAIL = "3000";
	public static final String CHANNEL_ID_ONLINE_EYE = "3100";
	public static final String CHANNEL_ID_ONLINE_DEL = "3200";
	public static final String CHANNEL_ID_SPRINGBOARD_ACQ_MASS_EYE = "3300";
	public static final String CHANNEL_ID_SPRINGBOARD_ACQ_MASS_DEL = "3400";
	public static final String CHANNEL_ID_SPRINGBOARD_RET_MASS_EYE = "3500";
	public static final String CHANNEL_ID_SPRINGBOARD_RET_MASS_DEL = "3600";
	public static final String CHANNEL_ID_SPRINGBOARD_STANDALONE_VAS_MASS_EYE = "3700";
	public static final String CHANNEL_ID_SPRINGBOARD_STANDALONE_VAS_MASS_DEL = "3800";
	public static final String CHANNEL_ID_SPRINGBOARD_PREMIER = "4000";
	public static final String CHANNEL_ID_SPRINGBOARD_ACQ_PT_EYE = "4300";
	public static final String CHANNEL_ID_SPRINGBOARD_ACQ_PT_DEL = "4400";
	public static final String CHANNEL_ID_SPRINGBOARD_RET_PT_EYE = "4500";
	public static final String CHANNEL_ID_SPRINGBOARD_RET_PT_DEL = "4600";
	public static final String CHANNEL_ID_SPRINGBOARD_STANDALONE_VAS_PT_EYE = "4700";
	public static final String CHANNEL_ID_SPRINGBOARD_STANDALONE_VAS_PT_DEL = "4800";
	
	public static final String ITEM_TYPE_PREPAY = "PREPAY";
	public static final String ITEM_TYPE_PLAN = "PLAN";
	public static final String ITEM_TYPE_BVAS = "BVAS";
	public static final String ITEM_TYPE_MOOV = "MOOV";
	public static final String ITEM_TYPE_CONTENT = "CONTENT";
	public static final String ITEM_TYPE_CONT_SET = "CONT-SET";
	public static final String ITEM_TYPE_BB_RENTAL = "BB-RENTAL";
	public static final String ITEM_TYPE_BB_RENTAL_WAIVE = "BB-RENT-W";
	public static final String ITEM_TYPE_EXIST_VAS = "EXIST-VAS";
	public static final String ITEM_TYPE_VAS_HOT = "VAS-HOT";
	public static final String ITEM_TYPE_DN_CHANGE = "CHG-DN-OT";
	public static final String ITEM_TYPE_DNY_CHANGE = "CHG-DNY-OT";
	public static final String ITEM_TYPE_DN_CHANGE_WAIVE = "CHG-DN-OT-WAIVE";
	public static final String ITEM_TYPE_DNY_CHANGE_WAIVE = "CHG-DNY-OT-WAIVE";
	public static final String ITEM_TYPE_SMART_WARRANTY = "S-WARRANTY";
	public static final String ITEM_TYPE_VAS_OTHER = "VAS-OTHER";
	public static final String ITEM_TYPE_PE_FREE = "PE-FREE";
	public static final String ITEM_TYPE_OPT_ACC = "OPT_ACC";
	public static final String ITEM_TYPE_PE_SOCKET = "PE-SOCKET";
	public static final String ITEM_TYPE_IDD = "IDD";
	public static final String ITEM_TYPE_IDD_OUT = "IDD-OUT";
	public static final String ITEM_TYPE_0060E = "0060E";
	public static final String ITEM_TYPE_0060E_OUT = "0060E-OUT";
	public static final String ITEM_TYPE_VAS_2DEL_HOT = "VAS-2DEL-H";
	public static final String ITEM_TYPE_VAS_2DEL_OTHER = "VAS-2DEL-O";
	public static final String ITEM_TYPE_VAS_2DEL_FREE = "VAS-2DEL-F";
	public static final String ITEM_TYPE_VAS_2DEL_STANDALONE = "VAS-2DEL-S";
	public static final String ITEM_TYPE_NOWTV_MIRR = "NOWTV-MIRR";
	public static final String ITEM_TYPE_NOWTV_SPEC = "NOWTV-SPEC";
	public static final String ITEM_TYPE_NOWTV_PAY = "NOWTV-PAY";
	public static final String ITEM_TYPE_NOWTV_STAR = "NOWTV-STAR";
	public static final String ITEM_TYPE_NOWTV_FREE = "NOWTV-FREE";
	public static final String ITEM_TYPE_NOWTV_CELE = "NOWTV-CELE";
	public static final String ITEM_TYPE_NOWTV_SPT = "NOWTV-SPT";
	public static final String ITEM_TYPE_PREM_PAY = "PREM-PAY";
	public static final String ITEM_TYPE_KEEP_EXIST_BILL = "EXIST-BILL";
	
    public static final String ITEM_TYPE_TV_EMAIL = "TV-EMAIL";
    public static final String ITEM_TYPE_TV_PRINT = "TV-PRINT";
    public static final String ITEM_TYPE_TV_DEVICE = "TV-DEVICE";
	
    public static final String ITEM_TYPE_REPLAC_VAS = "REPLAC-VAS";
    
	public static final String ITEM_TYPE_PAPER_BILL = "PAPER-BILL";
	public static final String ITEM_TYPE_PAPER_BILL_WAIVE = "PAPER-W";
	public static final String ITEM_TYPE_PAPER_BILL_GENERATE = "PAPER-G";
	public static final String ITEM_TYPE_PAPER_BILL_BR = "PAPER-BR";
	
	public static final String PAPER_BILL_CHARGE_WAIVE = "Waive";
	public static final String PAPER_BILL_CHARGE_GENERATE = "Charge";
	public static final String PAPER_BILL_CHARGE_REMAIN_UNCHANGE = "Remain Unchange";
	
	public static final String ITEM_TYPE_VIEW_ON_DEVICE = "NO-BILL";
	public static final String ITEM_TYPE_EBILL = "EBILL";
	public static final String ITEM_TYPE_MYHKT_BILL = "MYHKT-BILL";
	public static final String ITEM_TYPE_EXIST_MYHKT_BILL = "EXST-MYHKT";
	public static final String ITEM_TYPE_EMAIL_BILL = "EMAILBILL";
	public static final String ITEM_TYPE_THE_CLUB_BILL = "THE-CLUB";
	public static final String ITEM_TYPE_HKT_THE_CLUB_BILL = "MYHKT-CLUB";
	
	public static final String ITEM_TYPE_ADMIN_CHARGE = "ADM-CHRG";
	public static final String ITEM_TYPE_CANCEL_CHARGE = "CAN-CHRG";
	
	public static final String ITEM_TYPE_ER_CHARGE = "ER-OT";
	
	public static final String ITEM_TYPE_PREMIUM = "PREMIUM";
	public static final String ITEM_TYPE_PREM_SET = "PREM-SET";
    
	public static final String ITEM_LTS_VAS = "LTS_VAS";
	public static final String ITEM_LTS_TP = "LTS_TP";
	public static final String ITEM_LTS_VTP = "LTS_VTP";
	public static final String ITEM_IMS_VAS = "IMS_VAS";
	
	public static final String ITEM_TYPE_OFFER_CHANGE = "OFFER-CHG";
	
	public static final String ITEM_TYPE_EYE_MIRROR = "EYE-MIRROR";
	public static final String ITEM_TYPE_INSTALL = "INSTALL";
	public static final String ITEM_TYPE_INSTALL_WAIVE = "INSTALL-W";
	
	public static final String ITEM_TYPE_ITEM_IMG = "ITEM_IMG";
	
	public static final String ITEM_TYPE_ONLINE_EBILL = "OLS-EBILL";	
	public static final String ITEM_TYPE_ONLINE_VAS = "OLS-VAS";
	
	public static final String ITEM_TYPE_FFP_OTHER = "FFP-OTHER";
	public static final String ITEM_TYPE_FFP_STAFF = "FFP-STAFF";
	public static final String ITEM_TYPE_FFP_HOT = "FFP-HOT";
	public static final String ITEM_TYPE_VAS_FFP = "VAS-FFP";
	
	public static final String ITEM_TYPE_VAS_PRE_WIRING = "VAS-PREW";
	public static final String ITEM_TYPE_VAS_PRE_INSTALL = "VAS-PREI";
	
	public static final String ITEM_TYPE_RENTAL_ROUTER = "R-ROUTER";
	
	public static final String ITEM_TYPE_EPD_ACCEPT = "EPD-A";
	public static final String ITEM_TYPE_EPD_FORFEIT = "EPD-F";
	public static final String ITEM_TYPE_EPD_UNDER_CONSIDERATION = "EPD-UC";
	public static final String[] ITEM_TYPE_ALL_EPD_ITEMS = {ITEM_TYPE_EPD_ACCEPT, ITEM_TYPE_EPD_FORFEIT, ITEM_TYPE_EPD_UNDER_CONSIDERATION};
	
	public static final String ITEM_TYPE_SELF_PICKUP = "SELF-PICKUP";
	public static final String ITEM_TYPE_FIELD_SERVICE = "FIELD-SERVICE";
	
	public static final String ITEM_ATTB_INFO_KEY_TIME_AM_PM = "TIME_AM_PM";
	
	public static final String ITEM_ATTB_INFO_KEY_CLUB = "THE_CLUB";
	public static final String ITEM_ATTB_INFO_KEY_HKT = "MYHKT";
	
	public static final String ITEM_SET_TYPE_PREM_SET = "PREM-SET";
	public static final String ITEM_SET_TYPE_CONT_SET = "CONT-SET";
	public static final String ITEM_SET_TYPE_CONT_IMG = "CONT-IMG";
	public static final String ITEM_SET_TYPE_PREM_IMG = "PREM-IMG";
	public static final String ITEM_SET_TYPE_PREM_DEL = "PREM-DEL";
	public static final String ITEM_SET_TYPE_ER_DEL_PREMIUM = "ER-DEL-PRM";
	public static final String ITEM_SET_TYPE_ER_EYE_PREMIUM = "ER-EYE-PRM";
	public static final String ITEM_SET_TYPE_TEAM_PREM = "TEAM-PREM";
	public static final String ITEM_SET_TYPE_DFORM_PREM = "DFORM-PREM";
	public static final String ITEM_SET_TYPE_TEAM_VAS = "TEAM-VAS";
	public static final String ITEM_SET_TYPE_PREMIER_TEAM_VAS = "P-TEAM-VAS";
	public static final String ITEM_SET_TYPE_SMART_WARRANTY = "S-WARRANTY-SET";
	public static final String ITEM_SET_TYPE_BUNDLE_VAS = "BUNDLE-VAS-SET";
	public static final String ITEM_SET_TYPE_DEVICE_REDEMPTION_SET = "DEVICE-REDEMPTION-SET";

	public static final String OS_TYPE_AND = "AND";
	public static final String OS_TYPE_IOS = "IOS";
	public static final String OS_TYPE_ALL = "ALL";
	
	public static final int PRE_INSTALL_MINDAY = 14;
	
	public static final String APPOINTMENT_TIMESLOT_18_TO_20 = "18:00-20:00";
	public static final String APPOINTMENT_TIMESLOT_09_TO_13 = "09:00-13:00";
	public static final String APPOINTMENT_TIMESLOT_09_TO_18 = "09:00-18:00";
	public static final String APPOINTMENT_TIMESLOT_10_TO_13 = "10:00-13:00";
	public static final String APPOINTMENT_TIMESLOT_10_TO_18 = "10:00-18:00";
	public static final String APPOINTMENT_TIMESLOT_WHOLE = "09:00-18:00";
	public static final String APPOINTMENT_TIMESLOT_EVENING = "18:00-22:00";
	public static final String APPOINTMENT_TIMESLOT_TYPE_AM = "A";
	public static final String APPOINTMENT_TIMESLOT_TYPE_PM = "P";
	public static final String APPOINTMENT_TIMESLOT_TYPE_WHOLE = "W";
	public static final String APPOINTMENT_TIMESLOT_TYPE_EVENING = "T";
	public static final String APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE = "NS";
	public static final String APPOINTMENT_TIMESLOT_TYPE_RESTRICT = "RS";

	public static final String EARLIEST_SRD_REASON_CD_AMEND_NORMAL = "00";
	public static final String EARLIEST_SRD_REASON_CD_AMEND_NORMAL_DESC = "Amendment Leadtime ";
	public static final String EARLIEST_SRD_REASON_CD_FIELD_PERMIT_REQUIRED = "01";
	public static final String EARLIEST_SRD_REASON_CD_2N_LEAD_TIME = "06";
	public static final String EARLIEST_SRD_REASON_CD_2N_BLOCK_WIRING = "11";
	public static final String EARLIEST_SRD_REASON_CD_PIPB_NORMAL = "12";
	public static final String EARLIEST_SRD_REASON_CD_PIPB_DN_NOT_EXIST_DRG = "18";
	public static final String EARLIEST_SRD_REASON_CD_DS_NAME_NOT_MATCH = "50";
	public static final String EARLIEST_SRD_REASON_CD_DS_MUST_QC = "51";
	
	public static final String IMAGE_TYPE_JPEG = "jpeg";
	public static final String IMAGE_TYPE_PNG = "png";
	public static final String IMAGE_TYPE_GIF = "gif";
	
	public static final String ACCT_BILL_FREQ_MONTHLY = "M";
	public static final String ACCT_BILL_FREQ_QUATERLY = "Q";
	
	public static final String ACCT_BILL_LANG_ENGLISH = "E";
	public static final String ACCT_BILL_LANG_CHINESE = "C";
	
	public static final String GROUP_TYPE_EYE = "EYEX";
	
	public static final String MODEM_TYPE_1L2B = "1L2B";
	public static final String MODEM_TYPE_1L1B = "1L1B";
	public static final String MODEM_TYPE_2L2B = "2L2B";
	public static final String MODEM_TYPE_nLnB = "nLnB";
	
	public static final String ORDER_TYPE_INSTALL = "I";
	public static final String ORDER_TYPE_CHANGE = "C";
	public static final String ORDER_TYPE_DISCONNECT = "D";
	
	public static final String SRV_ACTION_TYPE_CD_NEW_DEL = "NEW-DEL";
	public static final String SRV_ACTION_TYPE_CD_NEW_EYE = "NEW-EYE";
	public static final String SRV_ACTION_TYPE_CD_NEW_PORT_IN_DEL = "NEW-PORT-IN-DEL";
	public static final String SRV_ACTION_TYPE_CD_NEW_PORT_IN_EYE = "NEW-PORT-IN-EYE";
	public static final String SRV_ACTION_TYPE_CD_CUST_NAME_NOT_MATCH = "CUST-NAME-NOT-MATCH";
	
	public static final String SRV_ACTION_TYPE_CD_DEL_EYEX_SIP = "DEL-EYEX_SIP";
	public static final String SRV_ACTION_TYPE_CD_DEL_EYEX_PE = "DEL-EYEX_PE";
	public static final String SRV_ACTION_TYPE_CD_DEL_2DEL = "DEL-2DEL";
	public static final String SRV_ACTION_TYPE_CD_NEW_2DEL = "NEW-2DEL";
	
	public static final String SRV_ACTION_TYPE_CD_DNX_EYEX_SIP = "DNX-EYEX_SIP";
	public static final String SRV_ACTION_TYPE_CD_DNX_EYEX_PE = "DNX-EYEX_PE";
	public static final String SRV_ACTION_TYPE_CD_DNX_2DEL = "DNX-2DEL";
	public static final String SRV_ACTION_TYPE_CD_DNX_REMOVE = "DNX-REMOVE";
	
	public static final String SRV_ACTION_TYPE_CD_DNY_EYEX_SIP = "DNY-EYEX_SIP";
	public static final String SRV_ACTION_TYPE_CD_DNY_EYEX_PE = "DNY-EYEX_PE";
	public static final String SRV_ACTION_TYPE_CD_DNY_2DEL = "DNY-2DEL";
	public static final String SRV_ACTION_TYPE_CD_DNY_REMOVE = "DNY-REMOVE";
	
	public static final String SRV_ACTION_TYPE_CD_NEW_WG = "NEW-WG";
	public static final String SRV_ACTION_TYPE_CD_NEW_PCD = "NEW-PCD";
	public static final String SRV_ACTION_TYPE_CD_NEW_PCD_SD = "NEW-PCD+SD";
	public static final String SRV_ACTION_TYPE_CD_NEW_PCD_HD = "NEW-PCD+HD";
	public static final String SRV_ACTION_TYPE_CD_NEW_SD = "NEW-SD";
	public static final String SRV_ACTION_TYPE_CD_NEW_HD = "NEW-HD";

	public static final String SRV_ACTION_TYPE_CD_PCD_PCD = "PCD-PCD";
	public static final String SRV_ACTION_TYPE_CD_PCD_PCD_SD = "PCD-PCD+SD";
	public static final String SRV_ACTION_TYPE_CD_PCD_PCD_HD = "PCD-PCD+HD";
	public static final String SRV_ACTION_TYPE_CD_PCD_SD_PCD_SD = "PCD+SD-PCD+SD";
	public static final String SRV_ACTION_TYPE_CD_PCD_SD_PCD_HD = "PCD+SD-PCD+HD";
	public static final String SRV_ACTION_TYPE_CD_PCD_HD_PCD_HD = "PCD+HD-PCD+HD";
	
	public static final String SRV_ACTION_TYPE_CD_SD_SD = "SD-SD";
	public static final String SRV_ACTION_TYPE_CD_SD_HD = "SD-HD";
	public static final String SRV_ACTION_TYPE_CD_SD_PCD_SD = "SD-PCD+SD";
	public static final String SRV_ACTION_TYPE_CD_SD_PCD_HD = "SD-PCD+HD";
	
	public static final String SRV_ACTION_TYPE_CD_HD_HD = "HD-HD";
	public static final String SRV_ACTION_TYPE_CD_HD_PCD_HD = "HD-PCD+HD";
	
	public static final String SRV_ACTION_TYPE_CD_PCD_SDTV = "PCD+SDTV";
	public static final String SRV_ACTION_TYPE_CD_PCD_HDTV = "PCD+HDTV";
	
	public static final String LTS_BILL_MEDIA_PAPER_BILL = "P";
	public static final String LTS_BILL_MEDIA_DEVICE_BILL = "N";
	public static final String LTS_BILL_MEDIA_ONLINE_BILL = "I";
	public static final String LTS_BILL_MEDIA_PPS_BILL = "S";
	
	public static final String PAYMENT_TYPE_CREDIT_CARD = "C";
	public static final String PAYMENT_TYPE_AUTO_PAY = "A";
	public static final String PAYMENT_TYPE_CASH = "M";
	public static final String PAYMENT_TYPE_AWAIT_PAYMENT_METHOD = "N";

	public static final String PAYMENT_TERM_CD_VERBAL = "81";
	
	public static final String FSA_SRV_TYPE_WG = "WG"; 
	public static final String FSA_SRV_TYPE_PCD = "PCD"; 
	public static final String FSA_SRV_TYPE_SDTV = "SDTV"; 
	public static final String FSA_SRV_TYPE_HDTV = "HDTV"; 
	public static final String FSA_SRV_TYPE_PCD_SDTV = "PCD+SDTV"; 
	public static final String FSA_SRV_TYPE_PCD_HDTV = "PCD+HDTV";
	public static final String FSA_SRV_TYPE_PCD_EW = "PCD+EW";
	public static final String FSA_SRV_TYPE_PCD_EW_SDTV = "PCD+EW+SDTV";
	public static final String FSA_SRV_TYPE_PCD_EW_HDTV = "PCD+EW+HDTV";
	public static final String FSA_SRV_TYPE_EW = "EW";
	public static final String FSA_SRV_TYPE_EW_SDTV = "EW+SDTV";
	public static final String FSA_SRV_TYPE_EW_HDTV = "EW+HDTV";
	public static final String FSA_SRV_TYPE_PS3 = "PS3";
	public static final String FSA_SRV_TYPE_PCD_PS3 = "PCD+PS3";
	public static final String FSA_SRV_TYPE_PCD_SDTV_PS3 = "PCD+SDTV+PS3";
	public static final String FSA_SRV_TYPE_PCD_HDTV_PS3 = "PCD+HDTV+PS3";
	public static final String FSA_SRV_TYPE_PCD_EW_SDTV_PS3 = "PCD+EW+SDTV+PS3";
	public static final String FSA_SRV_TYPE_PCD_EW_HDTV_PS3 = "PCD+EW+HDTV+PS3";
	public static final String FSA_SRV_TYPE_EW_SDTV_PS3 = "EW+SDTV+PS3";
	public static final String FSA_SRV_TYPE_EW_HDTV_PS3 = "EW+HDTV+PS3";
	public static final String FSA_SRV_TYPE_SDTV_PS3 = "SDTV+PS3";
	public static final String FSA_SRV_TYPE_HDTV_PS3 = "HDTV+PS3";
	public static final String FSA_SRV_TYPE_EYE = "eye";
	
	public static final String ADDRESS_COVERAGE_6M = "6M";
	public static final String ADDRESS_COVERAGE_8M = "8M";
	public static final String ADDRESS_COVERAGE_OVER_8M = ">8M";
	
	public static final String ROLLOUT_ADDRESS_PUBLIC_HOUSE = "PH";
	public static final String ROLLOUT_ADDRESS_HOME_OWNERSHIP_SCHEME = "HOS";
	public static final String ROLLOUT_ADDRESS_PRIVATE_HOUSE = "PRI";
	
	public static final String SP_ACTION_PEND_ORDER = "PEND-ORDER";
	public static final String SP_ACTION_APPV_MARKET_UPG = "APPV-MARKET_UPG";
	public static final String SP_ACTION_APPV_MARKET_2DEL = "APPV-MARKET_2DEL";
	public static final String SP_ACTION_LTS_EYE_ER = "LTS-EYE-ER";
	public static final String SP_ACTION_LTS_DEL_ER = "LTS-DEL-ER";
	public static final String SP_ACTION_IMS_ER = "IMS-ER";
	public static final String SP_ACTION_DUMMY_CUST = "DUMMY-CUST";
	public static final String SP_ACTION_2L2B_MIRROR = "2L2B-MIRROR";
	public static final String SP_ACTION_2L2B_NON_MIRROR = "2L2B-NON-MIRROR";
	public static final String SP_ACTION_CALL_PLAN_DOWN = "CALL-PLAN-DOWNGRADE";
	public static final String SP_ACTION_CANCEL_VAS = "CANCEL-VAS";
	public static final String SP_ACTION_UPGRADE_PCD = "UPGRADE-PCD";
	public static final String SP_ACTION_DEACT_NOWTV = "DEACT-NOWTV";
	public static final String SP_ACTION_SYSTEM_REJECT = "SYSTEM-REJECT";
	public static final String SP_ACTION_SUB_BUNDLE_TV = "SUBCRIPT-BUNDLE-TV";
	public static final String SP_ACTION_FROZEN_EXCHANGE = "FROZEN-EXCHANGE";
	public static final String SP_ACTION_CUST_NAME_NOT_MATCH_EYE = "CUST-NAME-NM-EYE";
	public static final String SP_ACTION_CUST_NAME_NOT_MATCH_DEL = "CUST-NAME-NM-DEL";
	public static final String SP_ACTION_TV = "TV";
	public static final String SP_ACTION_SWITCH_FSA = "SWITCH_FSA";
	public static final String SP_ACTION_RECONTRACT = "RECONTRACT";
	public static final String SP_ACTION_UPDATE_DN_PROFILE = "UPDATE_DN_PROFILE";
	public static final String SP_ACTION_APPV_WAIVE_TP = "APPV-WAIVE-TP";
	public static final String SP_ACTION_EDF = "EDF";
	public static final String SP_ACTION_123_TV = "123_TV";
	public static final String SP_ACTION_IN_IDD_FFP = "IN-IDD-FFP";
	public static final String SP_ACTION_OUT_IDD_FFP = "OUT-IDD-FFP";
	public static final String SP_ACTION_D_FORM_WAIVE_UM = "D-FORM-WAIVE-UM";
	public static final String SP_ACTION_D_FORM_WAIVE_SM = "D-FORM-WAIVE-SM";
	public static final String SP_ACTION_LOST_EQUIP_50_UM = "LOST-EQUIP-50-UM";
	public static final String SP_ACTION_LOST_EQUIP_70_SM = "LOST-EQUIP-70-SM";
	public static final String SP_ACTION_LOST_EQUIP_100_SM = "LOST-EQUIP-100-SM";
	public static final String SP_ACTION_LOST_EQUIP_100_M = "LOST-EQUIP-100-M";
	public static final String SP_ACTION_DEATH_CASE = "DEATH-CASE";
	public static final String SP_ACTION_CEASE_RENTAL_DATE = "CEASE-RENTAL-DATE";
	public static final String SP_ACTION_CHANGE_PLAN = "CHANGE-PLAN";
	public static final String SP_ACTION_TERMINATE_PCD_SERVICE = "D-WILL-REMOVE-PCD";
	public static final String SP_ACTION_TERMINATE_TV_SERVICE = "D-WILL-REMOVE-TV";
	public static final String SP_ACTION_PENDING_IMS_ORDER = "D-PENDING-IMS-ORDER";
	public static final String SP_ACTION_D_ORDER_WAIVE_TP = "D-WAIVE-TP";
	public static final String SP_ACTION_DEATH_CASE_NORMAL = "DEATH-CASE-UM";
	public static final String SP_ACTION_WAIVE_IDD_FFP = "WAIVE-IDD-FFP";
	public static final String SP_ACTION_BACKDATE_APPROVAL= "BACKDATE-APPROVAL";
	public static final String SP_ACTION_PIPB_NEW_DN_CREATE = "PIPB_NEW_DN_CREATE";
	public static final String SP_ACTION_WAIVE_DNCHANGE = "WAIVE-CHANGE-DN-ONETIME";
	public static final String SP_ACTION_DNCHANGE_FULL_WQ = "CHANGE-DN";
	public static final String SP_ACTION_MISMATCH_CUST_INFO = "MISMATCH-CUST-INFO";
	public static final String SP_ACTION_DRG_DOWNTIME_INFO = "DRG-DWNTIME-INFO";
	public static final String SP_ACTION_AWAIT_PREPAY = "AWAIT-PREPAY";
	public static final String SP_ACTION_WAIVE_QC = "WAIVE-QC";
	public static final String SP_ACTION_DRG_DOWNTIME = "DRG-DOWNTIME";
	public static final String SP_ACTION_REMOVE_MIRROR = "REMOVE-MIRROR";
	public static final String SP_ACTION_DIFF_CUST_EYE = "DIFF-CUST-EYE";
	public static final String SP_ACTION_DIFF_CUST_DEL = "DIFF-CUST-DEL";
	public static final String SP_ACTION_STANDALONE_VAS_FFP_ONLY = "STANDALONE-VAS-FFP-ONLY";
	public static final String SP_ACTION_VAS_FFP_FREE = "VAS-FFP-FREE";
	public static final String SP_ACTION_LOST_MODEM_UM = "LOST-MODEM-UM";
	public static final String SP_ACTION_LOST_MODEM_SM = "LOST-MODEM-SM";
	public static final String SP_ACTION_FORCE_RETAIN_OFFER = "FORCE-RETAIN-OFFER";
	public static final String SP_ACTION_SHARE_NEW_PCD_SB = "SHARE-NEW-PCD-SB";
	public static final String SP_ACTION_SHARE_NEW_PCD_NON_SB = "SHARE-NEW-PCD-NON-SB";
	public static final String SP_ACTION_FFP_CHG_DCA = "FFP-CHG-DCA";
	public static final String SP_ACTION_FFP_WAIVE_PEN_MKT = "FFP-WAIVE-PENALTY-MKT";
	public static final String SP_ACTION_FFP_WAIVE_PEN_UM = "FFP-WAIVE-PENALTY-UM";
	public static final String SP_ACTION_FFP_WAIVE_PEN_SM = "FFP-WAIVE-PENALTY-SM";
	
	
	public static final String WQ_TYPE_PARTIAL = "PARTIAL_WQ";
	public static final String WQ_TYPE_FULL = "FULL_WQ";
	public static final String WQ_TYPE_APPROVAL = "APPROVAL";
	public static final String WQ_TYPE_FOLLOWUP = "FOLLOWUP";
	public static final String WQ_TYPE_OL_CUST_INFO = "OL_CUS_INFO";
	public static final String WQ_TYPE_PORT_IN = "PORT_IN";
	
	public static final String WQ_TYPE_AMEND_OLS_DEL = "OLS-DEL";
	public static final String WQ_TYPE_AMEND_OLS_EYE = "OLS-EYE";
	public static final String WQ_TYPE_AMEND_SB_LTS = "SB-LTS";
	public static final String WQ_TYPE_AMEND_FS_E = "FS-E";	

	public static final String WQ_SUB_TYPE_AMEND = "ORDER_AMEND";
	
	public static final String SERVICE_TYPE_EYE = "EYE";
	public static final String SERVICE_TYPE_DEL = "DEL";
	public static final String SERVICE_TYPE_ALL = "ALL";
	
	public static final String RPT_SRV_TYPE_EYE = "EYE";
	public static final String RPT_SRV_TYPE_DEL = "RES_TEL";
	public static final String RPT_SRV_TYPE_2ND_DEL = "2ND_RES_TEL";
	public static final String RPT_SRV_TYPE_PORT_IN = "PORT_IN";
	public static final String RPT_SRV_TYPE_TDO_EYE = "TDO_EYE";
	public static final String RPT_SRV_TYPE_TDO_DEL = "TDO_DEL";
	public static final String RPT_SRV_TYPE_TDO_DEL_FREE = "TDO_DEL_FREE";
	public static final String RPT_SRV_TYPE_TDO_0060 = "TDO_0060";
	public static final String RPT_SRV_TYPE_TDO_NOWTV = "TDO_NOWTV";
	public static final String RPT_SRV_TYPE_EYE_SPEC = "EYE_SPEC";
	public static final String RPT_SRV_TYPE_RECONTRACT = "RECONTRACT_2017";
	public static final String RPT_SRV_TYPE_RECONTRACT_TERMS = "RECONTRACT_TERMS";
	public static final String RPT_SRV_TYPE_AMEND = "AMEND_COVER_SHEET";
	public static final String RPT_SRV_TYPE_RECONTRACT_PIPB = "RECONTRACT_PIPB";
	public static final String RPT_SRV_TYPE_NSD_PIPB = "NSD_PIPB";
	public static final String RPT_SRV_TYPE_CRF_PIPB = "CRF_PIPB";
	public static final String RPT_SRV_TYPE_AMEND_FORM = "AMENDMENT";
	public static final String RPT_SRV_TYPE_SMART_WARRANTY = "SMART_WARRANTY";
	public static final String RPT_SRV_TYPE_PAYMENT_SLIP = "PAYMENT_SLIP";
	public static final String RPT_SRV_TYPE_COOLING_OFF_PERIOD_STATEMENT = "COP_STATEMENT";
	public static final String RPT_SRV_TYPE_CSL_SIM = "CSL_SIM_TC";
	public static final String RPT_SRV_TYPE_IGUARD_CARECASH = "IGUARD_CARECASH";  //MB2016081 TC
	public static final String RPT_SRV_TYPE_IGUARD_PICS = "IGUARD_PICS";  //MB2016081 TC
	public static final String RPT_SRV_TYPE_CONCERT_TICKET = "CONCERT_TICKET";
	public static final String RPT_SRV_TYPE_WEEE = "WEEE";
	
	public static final String RPT_KEY_LOB = "LOB";
	public static final String RPT_KEY_SBORDER = "SBORDER";
	public static final String RPT_KEY_DTL_TYPE = "SB_DTL_TYPE";
	public static final String RPT_KEY_EDIT_BUTTON = "EDIT_BUTTON";
	public static final String RPT_KEY_RPT_TYPE = "RPT_TYPE";
	public static final String RPT_KEY_CUST_SIGNED = "CUSTOMER_SIGNED";
	public static final String RPT_KEY_FILE_PATH = "fileFullPath";
	public static final String RPT_KEY_NAME = "RPT_NAME";
	public static final String RPT_KEY_PDF_PERMISSION = "PDF_PERMISSION";
	public static final String RPT_KEY_ERASE_SIGNATURE = "ERASE_SIGNATURE";
	public static final String RPT_KEY_OVERRIDE_STORE_OPTION = "OVERRIDE_STORE_OPTION";
	public static final String RPT_KEY_ENCRYPT_PDF = "ENCRYPT_PDF";
	public static final String RPT_KEY_PRINT_TERMS = "PRINT_TERMS";
	public static final String RPT_KEY_AMENDMENT = "AMENDMENT";
	public static final String RPT_KEY_ACTION = "ACTION";
	public static final String RPT_KEY_PREPAYMENT_ACCT = "PREPAY_ACCT";
	public static final String RPT_KEY_PREPAYMENT_ITEM = "PREPAY_ITEM";
	public static final String RPT_KEY_PREPAYMENT_SERVICE = "PREPAY_SRV";
	
	public static final int RPT_KEY_STORE_CONDITION_NO_STORE = 0;
	public static final int RPT_KEY_STORE_CONDITION_ALWAYS = 1;
	public static final int RPT_KEY_STORE_CONDITION_SIGN_OFF = 2;
	
	public static final String RPT_TYPE_SIGNOFF = "SIGNOFF";
	public static final String RPT_TYPE_EYE_AF = "EYE_AF";
	public static final String RPT_TYPE_EYE_PARTIAL_WQ = "EYE_PARTIAL_WQ";
	public static final String RPT_TYPE_2ND_DEL_AF = "2ND_DEL_AF";
	public static final String RPT_TYPE_AMENDMENT = "AMENDMENT_FORM";
	public static final String RPT_TYPE_PORT_IN = "PORT_IN_FORM";
	public static final String RPT_TYPE_RECONTRACT = "RECONTRACT_FORM";
	public static final String RPT_TYPE_APP_EYE_AF = "APP_EYE_AF";
	public static final String RPT_TYPE_APP_DEL_AF = "APP_2ND_DEL_AF";
	public static final String RPT_TYPE_SUPPORT_DOC = "SUP_DOC";
	public static final String RPT_TYPE_EYE_AF_APR = "EYE_AF_APR";
	public static final String RPT_TYPE_2ND_DEL_AF_APR = "2ND_DEL_AF_APR";
	
	public static final String REMARK_TYPE_ORDER_REMARK = "L";
	public static final String REMARK_TYPE_ADD_ON_REMARK = "A";
	public static final String REMARK_TYPE_CUST_REMARK = "C";
	public static final String REMARK_TYPE_EARLIEST_SRD_REMARK = "D";
	public static final String REMARK_TYPE_2ND_DEL = "S";
	public static final String REMARK_TYPE_APPROVL_REMARK = "AP";
	public static final String REMARK_TYPE_SUSPEND_REMARK = "SP";
	
	public static final String DISTRIBUTE_MODE_PAPER = "P";
	public static final String DISTRIBUTE_MODE_ESIGNATURE = "E";
	
	public static final String NOWTV_BILL_MEDIA_PAPER_BILL = "P";
	public static final String NOWTV_BILL_MEDIA_EMAIL_BILL = "E";
	
	public static final String CODE_LKUP_IPAD_VERSION = "IPAD_VERSION";
	public static final String CODE_LKUP_EYE_ER_REMARK = "EYE_ER_REMARK";
	public static final String CODE_LKUP_EYE_ER_INSTALL_REMARK = "EYE_ER_INSTALL_REMARK";
	public static final String CODE_LKUP_INSTALL_FEE_ARPU = "INSTALL_FEE_ARPU";
	public static final String EMAIL_TEMPLATE_SIGNOFF = "EYEX001";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES = "EYEX002";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER = "EYEX003";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ = "EYEX004";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND = "EYEX005";
	public static final String EMAIL_TEMPLATE_SIGNOFF_PREMIER = "EYEX007";
	public static final String EMAIL_TEMPLATE_SIGNOFF_CALL_CENTER = "EYEX006";
	public static final String EMAIL_TEMPLATE_SIGNOFF_EYE_RENEWAL = "EYEX008";
	public static final String EMAIL_TEMPLATE_SIGNOFF_EYE_RENEWAL_PREMIER = "EYEX009";
		
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_DEL = "DEL002";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER_DEL = "DEL003";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ_DEL = "DEL004";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND_DEL = "DEL005";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_RENEWAL_DEL = "DEL006";
	public static final String EMAIL_TEMPLATE_SIGNOFF_FOR_RENEWAL_PREMIER_DEL = "DEL007";

	public static final String EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_RETAIL = "EYEX006";
	public static final String EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_CALL_CENTER = "EYEX006";
	public static final String EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_PREMIER = "EYEX007";
	public static final String EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_RETAIL = "DEL008";
	public static final String EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_CALL_CENTER = "DEL008";
	public static final String EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_PREMIER = "DEL009";
		
	public static final String EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_DIRECT_SALES = "EYEX010";
	public static final String EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_DIRECT_SALES = "DEL010";	
	public static final String EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_DIRECT_SALES_WITH_PREPAYMENT = "EYEX011";
	public static final String EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_DIRECT_SALES_WITH_PREPAYMENT = "DEL011";

	public static final String EMAIL_TEMPLATE_AMEND_NOTICE_EMAIL = "LAMNTM01"; 
	public static final String EMAIL_TEMPLATE_AMEND_NOTICE_SMS = "LAMNTS01"; 
	
	public static final String EMAIL_TEMPLATE_IGUARD_CUST_EMAIL = "IGUARD01"; 
	public static final String EMAIL_TEMPLATE_IGUARD_ADMIN_EMAIL = "IGUARD02"; 

	public static final String IGUARD_FORM_AF_NAME = "_IGUARD_CARECASH_AF";
	public static final String IGUARD_PICS_AF_NAME = "_IGUARD_PICS_AF";
	public static final String IGUARD_FORM_N_PICS_AF_NAME = "_IGC_N_PICS_AF";
	
	public static final String ORDER_DOC_EYE_AF_NAME = "EYE_AF";
	public static final String ORDER_DOC_2DEL_AF_NAME = "2ND_RESTEL_AF";
	public static final String ORDER_DOC_DEL_AF_NAME = "RESTEL_AF";
	public static final String EMAIL_FILE_PATTERN = "_AF.pdf";
	public static final String PAYMENT_SLIP_FILE_PATTERN_ENG = "_PS_EN.pdf";
	public static final String PAYMENT_SLIP_FILE_PATTERN_CHN = "_PS_ZH_HK.pdf";	
	
	public static final String ORDER_DOC_TYPE_EYEX_AF = "L001";
	public static final String ORDER_DOC_TYPE_EYEX_AF_SIGNED = "L002";
	public static final String ORDER_DOC_TYPE_2ND_DEL_AF = "L017";
	public static final String ORDER_DOC_TYPE_2ND_DEL_AF_SIGNED = "L018";
	public static final String ORDER_DOC_TYPE_DEL_AF = "L003";
	public static final String ORDER_DOC_TYPE_DEL_AF_SIGNED = "L004";
	public static final String ORDER_DOC_TYPE_ORDER_AMEND_FORM = "L005";
	public static final String ORDER_DOC_TYPE_CREDIT_CARD_COPY = "L006";
	public static final String ORDER_DOC_TYPE_SALES_MEMO_COPY = "L007";
	public static final String ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER = "L008";
	public static final String ORDER_DOC_TYPE_NSD= "L009";
	public static final String ORDER_DOC_TYPE_PREPAYMENT_SHEET= "L010";
	public static final String ORDER_DOC_TYPE_RECONTRACT_FORM = "L011";
	public static final String ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED = "L012";
	public static final String ORDER_DOC_TYPE_RECONTRACT_FAX_FORM = "L013";
	public static final String ORDER_DOC_TYPE_RECONTRACT_SUP_DOC = "L014";
	public static final String ORDER_DOC_TYPE_THIRD_PARTY_SUPPORT_DOCUMENT = "L015";
	public static final String ORDER_DOC_TYPE_DEATH_CERTIFICATE = "L016";
	public static final String ORDER_DOC_TYPE_STAFF_CARD = "L019";
	public static final String ORDER_DOC_TYPE_IDD_DEPOSIT = "L020";
	public static final String ORDER_DOC_TYPE_BR_SUP_DOC = "L021";
	public static final String ORDER_DOC_TYPE_PASS_SUP_DOC = "L022";
	public static final String ORDER_DOC_TYPE_THIRD_PARTY_AUTOPAY_FORM = "L023";
	public static final String ORDER_DOC_TYPE_CUSTOMER_REQUEST_FORM = "L024";
	public static final String ORDER_DOC_TYPE_ORDER_AMENDMENT_FORM = "L025";
	public static final String ORDER_DOC_TYPE_CUSTOMER_SUPPORT_DOC = "L026";
	
	public static final String ORDER_DOC_TYPE_RECONTRACT_TRANSFEREE_ID_DOC_COPY = "L030";
	public static final String ORDER_DOC_TYPE_RECONTRACT_TRANSFERER_ID_DOC_COPY = "L031";
	public static final String ORDER_DOC_TYPE_DECEASED_SUP_DOC = "L032";
	public static final String ORDER_DOC_TYPE_RECONTRACT_LETTER_OF_ADMINISTRATION = "L033";
	public static final String ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_OPTIONAL = "L034";
	public static final String ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_MANDATORY = "L035";
	public static final String ORDER_DOC_TYPE_LAWFUL_POSSESSION_SUPPORT_DOC = "L036";
	public static final String ORDER_DOC_TYPE_RECONTRACT_AUTHORIZATION_LETTER = "L041";
	
	public static final String AF_FORM_CUST_TYPE_NEW_CUST = "NC";
	public static final String AF_FORM_CUST_TYPE_EXIST_CUST = "EC";

	public static final String DUMMY_CUST_NO = "-1";
	public static final String DUMMY_ACCT_NO = "-1";
	
	public static final String URL_PARM_ENC_KEY = "0123456789ABCDEF";
	
	public static final String EMAIL_CONTENT_HEADER_EYE = "Thank you for your subscription of <span style=\"font-size:130%\">eye</span> service. Please find the attached Customer's Agreement for your record.";
	public static final String EMAIL_CONTENT_PRIVACY_STATEMENT_EYE = "In order to protect your privacy, the attached Customer's Agreement is protected by password. Please key in the first 6 characters of your identification document number set out in your online application in upper case format (e.g. A12345) to access your Customer's Agreement.";
	public static final String EMAIL_CONTENT_HEADER_DEL = "Thank you for your subscription of Residential Telephone Line service. Please find the attached Customer's Agreement for your record.";
	public static final String EMAIL_CONTENT_PRIVACY_STATEMENT_DEL = "In order to protect your privacy, the attached Customer's Agreement is protected by password. Please key in the first 6 characters of your identification document number set out in your online application in upper case format (e.g. A12345) to access your Customer's Agreement.";
	
	public static final String OLS_SHOP_CD = "SBO";
	
	public static final String DEVICE_TYPE_DEL = "1000";
	public static final String DEVICE_TYPE_1015 = "1015";
	public static final String DEVICE_TYPE_1020 = "1020";
	public static final String DEVICE_TYPE_EYE3A = "1030";
	public static final String DEVICE_TYPE_EYE1 = "1001";
	public static final String DEVICE_TYPE_EYE2 = "1002";
	public static final String DEVICE_TYPE_EYE2A = "1020";
	public static final String DEVICE_TYPE_EYE1_5_A = "1015";
	public static final String DEVICE_TYPE_SAMSUNG = "1003";
	
	public static final String CEKS_TITLE_DEL = "DEL_CEKS_TITLE";
	public static final String CEKS_TITLE_EYE = "EYE_CEKS_TITLE";
	public static final String CEKS_FEE_DESC = "LTS_CEKS_FEE_DESC";
	
	public static final String RPT_NAME_TDO_EYE = "tdo/keyInfoSheet/eye";
	public static final String RPT_NAME_TDO_2DEL = "tdo/keyInfoSheet/2del";
	public static final String RPT_NAME_TDO_0060 = "tdo/keyInfoSheet/0060";
	public static final String RPT_NAME_TDO_NOWTV = "tdo/keyInfoSheet/tv";
	public static final String RPT_NAME_TDO_DEL_FREE = "tdo/keyInfoSheet/DEL_FREE";
	public static final String RPT_NAME_TDO_STD_VAS = "tdo/keyInfoSheet/DEL Standalone VAS";
	
	public static final String EYE_GROUP_TYPE_EYE = "EYE";
	public static final String EYE_GROUP_TYPE_EYEX = "EYEX";
	public static final String EYE_GROUP_TYPE_EYE3 = "EYE3";
	
	public static final String OFFER_ACTION_CARRY_FORWARD = "CF";
	public static final String OFFER_ACTION_PENALTY = "P";
	public static final String OFFER_ACTION_TERMINATE = "T";
	
	public static final String OFFER_HANDLE_AUTO_GENERATE = "AG";
	public static final String OFFER_HANDLE_AUTO_WAIVE = "AW";
	public static final String OFFER_HANDLE_MANUAL_GENERATE = "MG";
	public static final String OFFER_HANDLE_MANUAL_WAIVE = "MW";
	
	public static final String LTS_OFFER_TYPE_VOICE = "V";
	public static final String LTS_OFFER_TYPE_CONTENT = "C";
	public static final String LTS_OFFER_TYPE_EQUIP = "E";
	
	public static final String LTS_SRV_TYPE_DEL = "DEL";
	public static final String LTS_SRV_TYPE_2DEL = "2DEL";
	public static final String LTS_SRV_TYPE_DNX = "DNX";
	public static final String LTS_SRV_TYPE_DNY = "DNY";
	public static final String LTS_SRV_TYPE_SIP = "SIP";
	public static final String LTS_SRV_TYPE_PE = "PE";
	public static final String LTS_SRV_TYPE_NEW = "NEW";
	public static final String LTS_SRV_TYPE_PORT_IN = "PORT-IN";
	public static final String LTS_SRV_TYPE_REMOVE = "REMOVE";
	
	public static final String LTS_PRODUCT_TYPE_DEL = "DEL";
	public static final String LTS_PRODUCT_TYPE_2DEL = "2DEL";
	public static final String LTS_PRODUCT_TYPE_EYE_1 = "EYE1";
	public static final String LTS_PRODUCT_TYPE_EYE_2 = "EYE2";
	public static final String LTS_PRODUCT_TYPE_EYE_1_5_A = "EYE1.5A";
	public static final String LTS_PRODUCT_TYPE_EYE_2_A = "EYE2A";
	public static final String LTS_PRODUCT_TYPE_EYE_3_A = "EYE3A";
	public static final String LTS_PRODUCT_TYPE_NEW = "NEW";
	public static final String LTS_PRODUCT_TYPE_PORT_IN = "PORT-IN";
	public static final String LTS_PRODUCT_TYPE_REMOVE = "REMOVE";
	public static final String LTS_PRODUCT_TYPE_NEW_DN_FIRST = "NEW-FIRST";
	public static final String LTS_PRODUCT_TYPE_PORT_LATER = "PORT-LATER";
		
	public static final String IMS_PRODUCT_TYPE_NEW = "NEW";
	public static final String IMS_PRODUCT_TYPE_WALL_GARDEN = "WG";
	public static final String IMS_PRODUCT_TYPE_PCD = "PCD";
	public static final String IMS_PRODUCT_TYPE_PCD_HD = "PCD+HD";
	public static final String IMS_PRODUCT_TYPE_PCD_SD = "PCD+SD";
	public static final String IMS_PRODUCT_TYPE_SD = "SD";
	public static final String IMS_PRODUCT_TYPE_HD = "HD";
	public static final String IMS_PRODUCT_TYPE_REMOVE = "REMOVE";
	
	public static final String ORDER_MODE_STAFF = "S";
	public static final String ORDER_MODE_ONLINE_SALES = "O";
	public static final String ORDER_MODE_BACKDOOR = "BD";
	
	public static final String ORDER_PREFIX_CALL_CENTRE = "C";
	public static final String ORDER_PREFIX_RETAIL = "R";
	public static final String ORDER_PREFIX_ONLINE_SALES = "O";
	public static final String ORDER_PREFIX_SERVICE_CENTRE = "S";
	public static final String ORDER_PREFIX_PREMIER_TEAM = "P";
	public static final String ORDER_PREFIX_DIRECT_SALES = "D";
	public static final String ORDER_PREFIX_DIRECT_SALES_NOW_TV_QA = "V";
	
	public static final String PENALTY_ACTION_AUTO_WAIVE = "AW";
	public static final String PENALTY_ACTION_MANUAL_WAIVE = "MW";
	public static final String PENALTY_ACTION_GENERATE = "G";
	public static final String PENALTY_ACTION_AWAIT_APPROVAL = "PA";
	public static final String PENALTY_WAIVED_IND = "Y";
	
	public static final String BILL_ADDR_ACTION_EXISTING = "E";
	public static final String BILL_ADDR_ACTION_IA = "I";
	public static final String BILLING_ADDR_ACTION_NEW = "N";
	
	public static final String RECONTRACT_SRV_HANDLE_CARRY = "C";
	public static final String RECONTRACT_SRV_HANDLE_RETAIN = "R";
	
	public static final String RECONTRACT_MODE_SINGLE = "S";
	public static final String RECONTRACT_MODE_BOTH = "B";
	
	public static final String ORDER_MODE_RETAIL = "R";
	public static final String ORDER_MODE_CALL_CENTER = "C";
	public static final String ORDER_MODE_PREMIER = "P";
	public static final String ORDER_MODE_DIRECT_SALES = "D";
	public static final String ORDER_MODE_DIRECT_SALES_NOW_TV_QA = "V";
	
	public static final String DISTRIBUTE_LANG_ENGLISH = "ENG";
	public static final String DISTRIBUTE_LANG_CHINESE = "CHN";
	
	public static final String SIGN_TYPE_EYE_TDO = "EYE_TDO_SIGN";
	public static final String SIGN_TYPE_DEL_TDO = "SEC_DEL_TDO_SIGN";
	public static final String SIGN_TYPE_IDD_0060_TDO = "IDD_0060_TDO_SIGN";
	public static final String SIGN_TYPE_NOWTV_TDO =  "NOWTV_TDO_SIGN";
	public static final String SIGN_TYPE_SEC_DEL_CUST = "SEC_DEL_CUST_SIGN";
	public static final String SIGN_TYPE_SEC_DEL_3RD_PARTY = "SEC_DEL_THIRD_PARTY_SIGN";
	public static final String SIGN_TYPE_DEL_CUST = "REL_DEL_CUST_SIGN";
	public static final String SIGN_TYPE_DEL_3RD_PARTY = "REL_DEL_THIRD_PARTY_SIGN";
	public static final String SIGN_TYPE_EYE_3RD_PARTY = "EYEX_THIRD_PARTY_SIGN";
	public static final String SIGN_TYPE_EYE_CUST = "EYEX_CUST_SIGN";
	public static final String SIGN_TYPE_RECONTRACT_FROM = "RECONTRACT_FROM_SIGN";
	public static final String SIGN_TYPE_RECONTRACT_TO = "RECONTRACT_TO_SIGN";
	public static final String SIGN_TYPE_BANK = "BANK_SIGN";
	public static final String SIGN_TYPE_CCARD = "CCARD_SIGN";
	public static final String SIGN_TYPE_PIPB_NSD = "PIPB_NSD_SIGN";
	
	public static final String ER_CHARGE_1ST_TIME_WAIVE = "1W";
	public static final String ER_CHARGE_MEET_REQUIRE_WAIVE = "RW";
	public static final String ER_CHARGE_GENERATE = "G";
	public static final String ER_CHARGE_AWAIT_APPROVAL = "PA";
	public static final String ER_CHARGE_MARKETING_APPROVE_WAIVE = "MW";
	public static final String ER_CHARGE_NO_CHARGE_2ND_LINE = "N";
	public static final String ER_CHARGE_PUBLIC_HSE_WAIVE = "PW";
	
	public static final String CHANNEL_ACTION_TP = "TP";
	public static final String CHANNEL_ACTION_NA = "N";
	
	public static final String SUSPEND_IND_WHOLE_OC = "O";
	public static final String SUSPEND_IND_BY_DTL = "D";
	
	public static final String[] CSP_DUMMY_DOC_STRINGS = {"DUMMY", "DUM", "TEST", "#", "@", "*", "?", "!"};
	public static final String CSP_DUMMY_EMAIL_DOMAIN = "@theclub.com.hk";
	public static final String CSP_DUMMY_MOBILE_NUM = "00000000";
	public static final String CSP_OPT_OUT_REASON_OTHER = "51";
	
	public static final String ITEM_ATTB_FORMAT_MANDATORY = "MANDATORY";
	public static final String ITEM_ATTB_FORMAT_DUPLEX_B_NUM = "DUPLEX_B_NUM";
	public static final String ITEM_ATTB_FORMAT_MOBILE_NUM = "MOBILE_NUM";
	public static final String ITEM_ATTB_FORMAT_EMAIL_ADDR = "EMAIL_ADDR";
	public static final String ITEM_ATTB_FORMAT_NUMBER = "NUMBER";
	public static final String ITEM_ATTB_FORMAT_TEXT = "TEXT";
	public static final String ITEM_ATTB_FORMAT_CONTACT_NAME = "CONTACT_NAME";
	public static final String ITEM_ATTB_FORMAT_CONTACT_NUM = "CONTACT_NUM";
	public static final String ITEM_ATTB_FORMAT_DATE = "DATE";
	public static final String ITEM_ATTB_FORMAT_REMARK = "REMARK";
	
	public static final String ORDER_TYPE_SB_UPGRADE = "SBU";
	public static final String ORDER_TYPE_SB_DISCONNECT = "SBD";
	public static final String ORDER_TYPE_SB_RETENTION = "SBR";
	public static final String ORDER_TYPE_SB_ACQUISITION = "SBA";
	public static final String ORDER_TYPE_ONLINE_ACQUISITION = "OLA";
	
	public static final String ORDER_SUB_TYPE_SB_STANDALONE_VAS = "SB_STANDALONE_VAS";
	
	public static final String DISCONNECT_REA_CD_DECEASED = "4012"; 
	
	public static final String WAIVE_D_FORM_CD_UM = "UM-APPROVE";
	public static final String WAIVE_D_FORM_CD_SM = "SM-APPROVE";
	
	public static final String LOST_EQUIP_CD_WAIVE_50 = "W50";
	public static final String LOST_EQUIP_CD_WAIVE_70 = "W70";
	public static final String LOST_EQUIP_CD_WAIVE_100_SM = "W100-SM";
	public static final String LOST_EQUIP_CD_WAIVE_100_M = "W100-MKT";
	
	public static final String REDEMPTION_MEDIA_POSTAL = "P" ;
	public static final String REDEMPTION_MEDIA_SMS = "M" ;
	public static final String REDEMPTION_MEDIA_EMAIL = "S" ;
	
	public static final String IDD_ACTION_REMOVE = "REMOVE";
	public static final String IDD_ACTION_RETAIN = "RETAIN";	
	public static final String IDD_ACTION_CHG_DCA = "CHG_DCA";
	public static final String IDD_ACTION_CHG_ACCT_DCA = "CHG_ACCT_DCA";
	
	public static final String DN_POOL_STATUS_LOCKED = "L";
	public static final String DN_POOL_STATUS_ASSIGNED = "A";	
	public static final String DN_POOL_STATUS_NORMAL = "N";
	public static final String DN_POOL_STATUS_PRE_ASSIGN = "P";	
	public static final String DN_POOL_STATUS_RESERVED = "R";
	public static final String DN_POOL_STATUS_CONFIRMED = "C";
	
	public static final String DRG_DN_SPARE_STATUS = "05";
	public static final String DRG_DN_ADVANCED_STATUS = "10";
	public static final String DRG_DN_PRE_ASSIGN_STATUS = "08";
	public static final String DRG_DN_ASSIGNED_STATUS = "15";
	public static final String DRG_DN_WORKING_STATUS = "20";
	public static final String DRG_DN_RESERVED_STATUS = "70";
	public static final String[] DRG_DN_PORT_OUT_STATUS =
		{"30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
		"50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
		"60", "61", "62", "63", "64", "65", "66", "67", "68", "69"};
	
	public static final String DRG_DN_SPARE = "Spare";
	public static final String DRG_DN_ADVANCED = "Advance";
	public static final String DRG_DN_PRE_ASSIGN = "Pre-assign";
	public static final String DRG_DN_ASSIGNED = "Assigned";
	public static final String DRG_DN_WORKING = "Working";
	public static final String DRG_DN_RESERVED = "Reserved";
	public static final String DRG_DN_PORT_OUT = "Port-out";
	
	public static final TreeMap<String, String> DRG_DN_STATUS_MAP = new TreeMap<String, String>();	
	static{
		DRG_DN_STATUS_MAP.put(DRG_DN_SPARE_STATUS, DRG_DN_SPARE);
		DRG_DN_STATUS_MAP.put(DRG_DN_ADVANCED_STATUS, DRG_DN_ADVANCED);
		DRG_DN_STATUS_MAP.put(DRG_DN_PRE_ASSIGN_STATUS, DRG_DN_PRE_ASSIGN);
		DRG_DN_STATUS_MAP.put(DRG_DN_ASSIGNED_STATUS, DRG_DN_ASSIGNED);
		DRG_DN_STATUS_MAP.put(DRG_DN_WORKING_STATUS, DRG_DN_WORKING);
		DRG_DN_STATUS_MAP.put(DRG_DN_RESERVED_STATUS, DRG_DN_RESERVED);
		for (int i=0; i<DRG_DN_PORT_OUT_STATUS.length; i++) {
			DRG_DN_STATUS_MAP.put(DRG_DN_PORT_OUT_STATUS[i], DRG_DN_PORT_OUT);
		}
	}
	
	public static final String DISC_DECEASE_NORMAL ="N";
	public static final String DISC_DECEASE_SPECIAL ="S";
	public static final String DISC_DECEASE_UNREACHED ="U";
	public static final String DISC_DECEASE_INHERIT ="I";
	
	public static final String DN_SOURCE_DN_RESERVED = "R";
	public static final String DN_SOURCE_DN_POOL = "D";
	public static final String DN_SOURCE_DN_PIPB = "P";
	public static final String DN_SOURCE_DN_WORKING = "W";
	
	public static final String ACCOUNT_CHARGE_TYPE_R = "R";
	public static final String ACCOUNT_CHARGE_TYPE_I = "I";
	public static final String ACCOUNT_CHARGE_TYPE_A = "A";
	public static final String ACCOUNT_CHARGE_TYPE_P = "P";
	
	public static final String INDUSTRY_TYPE_RESIDENTIAL = "CODE_10";
	public static final String INDUSTRY_SUBTYPE_RESIDENTIAL_BLANK = "RES";
	
	public static final String CUST_CATGORY_NORMAL = "NOR";
	
	public static final String PRODUCT_PARAM_2L2B_VAS = "VAS TYPE 88 - REFSA";
	public static final String PRODUCT_PARAM_VALUE_2L2B_VAS = "REFSA2";
	
	public static final String PRODUCT_PARAM_NLNB_VAS = "VAS TYPE 96 - REFSA";
	public static final String PRODUCT_PARAM_VALUE_NLNB_VAS = "REFSA";
	
	public static final String PRODUCT_PARAM_1L2B_VAS_MIRROR = "Mirror Mode";
	public static final String PRODUCT_PARAM_VALUE_1L2B_VAS_MIRROR = "M";
	
	public static final String PRODUCT_PARAM_1L2B_VAS_SPECIAL = "Special Mode";
	public static final String PRODUCT_PARAM_VALUE_1L2B_VAS_SPECIAL = "S";

	public static final String CSP_ALDY_REG = "R";
	public static final String CSP_REG = "Y";
	public static final String CSP_NOT_REG = "N";
	public static final String CSP_FAIL_REG = "F";

	public static final String LKUP_KEY_MINIMUM_PIPB_DAY = "MINDAY";
	public static final String LKUP_KEY_CONTACT_INFO_MONTH = "CNTCTMTH";
	public static final int MINIMUM_PIPB_DAY = 10;
	public static final int VALID_PIPB_HOURS = 8;
	
	public static final String LTS_PIPB_DUPLEX_ACTION_DISCONNECT = "DISCONNECT";
	public static final String LTS_PIPB_DUPLEX_ACTION_RETAIN = "RETAIN";
	public static final String LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER = "PORT_IN_TOGETHER";
	
	public static final String LTS_PIPB_ACTION_NEW = "NEW_DN";
	public static final String LTS_PIPB_ACTION_PIPB = "PIPB_DN";
	
	public static final String LTS_PIPB_WQ_ACTION_PIPB_TO_D = "PIPB_TO_D";
	public static final String LTS_PIPB_WQ_STATUS_COMPLETED = "COMPLETED";
	public static final String LTS_PIPB_WQ_STATUS_PENDING = "PENDING";
	
	public static final String LTS_PIPB_PORT_FROM_WTNT = "WT&T";
	public static final String LTS_PIPB_PORT_FROM_WTT = "WTT";
	public static final String LTS_PIPB_PORT_FROM_NWT = "NWT";
	public static final String LTS_PIPB_PORT_FROM_HGC = "HGC";
	public static final String LTS_PIPB_PORT_FROM_HKBN = "HKBN";
	public static final String LTS_PIPB_PORT_FROM_HKC = "HKC";
	public static final String LTS_PIPB_PORT_FROM_SSL = "SSL";
	public static final String LTS_PIPB_PORT_FROM_COMNET = "ComNet";
	public static final String LTS_PIPB_PORT_FROM_CSL = "CSL";
	public static final String LTS_PIPB_PORT_FROM_HKT = "HKT";
	public static final String LTS_PIPB_PORT_FROM_PC = "PC";
	public static final String LTS_PIPB_PORT_FROM_VZ = "VZ";
	public static final String LTS_PIPB_PORT_FROM_CMHK = "CMHK";
	public static final String LTS_PIPB_PORT_FROM_HKBNES = "HKBNES";
	
	public static final String LTS_PIPB_REUSE_SOCKET_PARALLEL_PHONE_LINE = "P";
	public static final String LTS_PIPB_REUSE_SOCKET_FORMER_FIXED_LINE = "F";
	
	public static final String RPT_SET_AMEND_AF = "AMEND_AF";
	public static final String RPT_SET_CRF_AF = "CRF_AF";
	public static final String RPT_SET_NSD_AF = "NSD_AF";
	public static final String LTS_PIPB_CRF_FORM_DOC_TYPE = "L024";
	public static final String LTS_PIPB_NSD_FORM_DOC_TYPE = "L009";
	public static final String[] LTS_PIPB_RPT_SETS = {RPT_SET_CRF_AF, RPT_SET_NSD_AF};
	public static final TreeMap<String, String> LTS_PIPB_FORM_DOC_TYPE_MAP = new TreeMap<String, String>();	
	static{
		LTS_PIPB_FORM_DOC_TYPE_MAP.put(RPT_SET_CRF_AF, LTS_PIPB_CRF_FORM_DOC_TYPE);
		LTS_PIPB_FORM_DOC_TYPE_MAP.put(RPT_SET_NSD_AF, LTS_PIPB_NSD_FORM_DOC_TYPE);
		LTS_PIPB_FORM_DOC_TYPE_MAP.put(RPT_TYPE_SUPPORT_DOC, ORDER_DOC_TYPE_CUSTOMER_SUPPORT_DOC);
	};		
	
	public static final String[] LTS_PIPB_REGENERATE_RPT_SETS = {RPT_SET_CRF_AF, RPT_TYPE_SUPPORT_DOC}; // force to re-generate
	
	public static final String DEFAULT_NRP_ACCOUNT_CD = "2N";
	public static final String DEFAULT_NRP_BOC = "000";
	public static final String DEFAULT_NRP_PROJECT_CD = "2N NUMBER";	
	public static final String DEFAULT_SPECIAL_SERVICE_GROUP = "PRODUCT";
	public static final String DEFAULT_SPECIAL_SERVICE_NAME = "PRODUCT";
	public static final String DEFAULT_WS_ASSIGN_DN_RELEASE = "R";
	public static final String DEFAULT_WS_ASSIGN_DN_PRE_ASSIGN = "P";
	public static final String DEFAULT_WS_ASSIGN_DN_GATEWAY_NUM = "000000000000";
	public static final String WS_RESPONSE_SUCCESS_CODE = "0";
	public static final String WS_ERROR_MESSAGE_DN_DEDICATED = "DN REQUESTED IS  DEDICATED";
	public static final String WS_ERROR_MESSAGE_NON_SPECIAL_SERVICE = "DN DOES NOT BELONG TO SPECIAL SRVCE";	
	public static final String WS_ERROR_MESSAGE_NO_RESPONSE = "SERVICE NO RESPONSE";
	public static final String WS_ERROR_MESSAGE_NON_2N_DN = "DN IS NOT 2N NUMBER";
	
	public static final String DOC_TYPE_PASSPORT = "PASS";
	public static final String DOC_TYPE_HKID = "HKID";
	public static final String DOC_TYPE_HKBR = "BS";
	public static final String DOC_TYPE_CERTIFICATE = "CERT";
	
	public static final String VAR_INSTALLATION_DATE = "#INSTALLATION_DATE#";
	public static final String VAR_SWITCHING_DATE = "#SWITCHING_DATE#";
	public static final String VAR_CUSTOMER_NAME = "#CUST_NAME#";
	
	public static final String EXCHANGE_CATEGORY_CODE_2N_DN = "2N-ONP";

	public static final String ACCOUNT_CREDIT_STATUS_NORMAL = "NORMAL";
	
	public static final int AGE_OF_ELDERLY_APPLICATION = 65;
	
	public static final String AWAIT_QC_REASON_CD_OF_ELDERLY_APPLICATION = "ELDERLY";
	public static final String AWAIT_QC_REASON_CD_OF_PE_LINK = "PELINK";
	public static final String AWAIT_QC_REASON_CD_OF_THIRD_PARTY = "3RDPARTY";
	
	public static final String FILE_SEQ= "seq";
	
	public static final String WQ_NATURE_REMOVE_MIRROR ="26";

	public static final String WQ_NATURE_ADD_IDD_FIX_FREE_PLAN="42";
	
	public static final String WQ_NATURE_AMEND_BA="301";
	public static final String WQ_NATURE_AMEND_SRD="302";
	public static final String WQ_NATURE_AMEND_DN="303";
	public static final String WQ_NATURE_AMEND_PCD="304";
	public static final String WQ_NATURE_AMEND_CUST_NAME_TITLE="305";
	public static final String WQ_NATURE_AMEND_IA="306";
	public static final String WQ_NATURE_AMEND_PAY_METHOD="307";
	public static final String WQ_NATURE_AMEND_VAS_PREMIUM = "310";
	public static final String WQ_NATURE_AMEND_CONTACT = "336";
	public static final String WQ_NATURE_AMEND_PROG_OFFER = "337";
	public static final String WQ_NATURE_AMEND_PDPO = "338";
	public static final String WQ_NATURE_AMEND_EQUIPMENT = "339";
	
	public static final String WQ_NATURE_CANCEL_EYE_PCD="311";
	public static final String WQ_NATURE_CANCEL_EYE_RETAIN_NEW_PCD="312";
	public static final String WQ_NATURE_CANCEL_EYE_RETAIN_EXTG_PCD="313";
	public static final String WQ_NATURE_CANCEL_NEW_PCD_RETAIN_EYE="314";
	public static final String WQ_NATURE_CANCEL_EXTG_PCD_REMAIN_EYE="315";
	public static final String WQ_NATURE_CANCEL_EYE_PCD_PREPAY="321";
	public static final String WQ_NATURE_CANCEL_EYE_RETAIN_NEW_PCD_PREPAY="322";
	public static final String WQ_NATURE_CANCEL_EYE_RETAIN_EXTG_PCD_PREPAY="323";
	public static final String WQ_NATURE_CANCEL_EYE="353";
	public static final String WQ_NATURE_CANCEL_EYE_PREPAY="354";
	public static final String WQ_NATURE_CANCEL_DEL="360";
	public static final String WQ_NATURE_CANCEL_DEL_PREPAY="361";
	
	public static final String WQ_NATURE_AMEND_PIPB_CUST_INFO="363";
	public static final String WQ_NATURE_AMEND_PIPB_FLAT_FLOOR="364";
	public static final String WQ_NATURE_AMEND_PIPB_SRD="365";
	public static final String WQ_NATURE_AMEND_AUTO_UPDATE="366";
	public static final String WQ_NATURE_AMEND_FFP_HANDLE="367";
	public static final String WQ_NATURE_AMEND_PENALTY_WAIVED = "369";
	
	public static final String[] WQ_NATURE_CANCEL_PCD_ARRAY = {WQ_NATURE_CANCEL_NEW_PCD_RETAIN_EYE, WQ_NATURE_CANCEL_EXTG_PCD_REMAIN_EYE};
	public static final List<String> WQ_NATURE_CANCEL_PCD_LIST = new ArrayList<String>();
	static {
		WQ_NATURE_CANCEL_PCD_LIST.addAll(Arrays.asList(WQ_NATURE_CANCEL_PCD_ARRAY));
	}
	
	public static final String[] WQ_NATURE_CANCEL_ARRAY = {WQ_NATURE_CANCEL_EYE_PCD,WQ_NATURE_CANCEL_EYE_RETAIN_NEW_PCD, WQ_NATURE_CANCEL_EYE_RETAIN_EXTG_PCD,
			                                               WQ_NATURE_CANCEL_NEW_PCD_RETAIN_EYE,WQ_NATURE_CANCEL_EXTG_PCD_REMAIN_EYE, WQ_NATURE_CANCEL_EYE_PCD_PREPAY,
			                                               WQ_NATURE_CANCEL_EYE_RETAIN_NEW_PCD_PREPAY,WQ_NATURE_CANCEL_EYE_RETAIN_EXTG_PCD_PREPAY,WQ_NATURE_CANCEL_EYE,
			                                               WQ_NATURE_CANCEL_EYE_PREPAY,WQ_NATURE_CANCEL_DEL,WQ_NATURE_CANCEL_DEL_PREPAY} ;
	public static final List<String> WQ_NATURE_CANCEL_LIST = new ArrayList<String>();
	static {
		WQ_NATURE_CANCEL_LIST.addAll(Arrays.asList(WQ_NATURE_CANCEL_ARRAY));
	}
	
	public static final String[] WQ_NATURE_AMEND_ARRAY = {WQ_NATURE_AMEND_BA, WQ_NATURE_AMEND_SRD, WQ_NATURE_AMEND_DN, WQ_NATURE_AMEND_PCD, WQ_NATURE_AMEND_CUST_NAME_TITLE,
														  WQ_NATURE_AMEND_IA, WQ_NATURE_AMEND_PAY_METHOD, WQ_NATURE_AMEND_VAS_PREMIUM, WQ_NATURE_AMEND_CONTACT,
														  WQ_NATURE_AMEND_PROG_OFFER,WQ_NATURE_AMEND_PDPO,WQ_NATURE_AMEND_EQUIPMENT,WQ_NATURE_AMEND_PIPB_CUST_INFO,
														  WQ_NATURE_AMEND_PIPB_FLAT_FLOOR,WQ_NATURE_AMEND_PIPB_SRD};
	public static final List<String> WQ_NATURE_AMEND_LIST = new ArrayList<String>();
	static {
		WQ_NATURE_AMEND_LIST.addAll(Arrays.asList(WQ_NATURE_AMEND_ARRAY));
	}

	public static final String WQ_NATURE_APPROVAL_LOST_MODEM_UM = "133";
	public static final String WQ_NATURE_APPROVAL_LOST_MODEM_SM = "134";
	
	public static final String WQ_NATURE_TYPE_INDICATOR = "INDICATOR";
	
	public static final String CONCAT_RPT_IND = "CONCAT";
	
	public static final String REPORT_ACTION_TYPE_SMS_PREPAYMENT = "SMSPS";
	public static final String REPORT_ACTION_TYPE_SMS_AF = "SMSAF";
	
	public static final String WAIVE_QC_STATUS_PENDING_APPROVAL = "P";
	public static final String WAIVE_QC_STATUS_APPROVED = "A";
	public static final String WAIVE_QC_STATUS_APPROVAL_REJECTED = "R";	
	
	public static final String NAME_MISMATCH_APR_CD_NO_MISMATCH = "N";
	public static final String NAME_MISMATCH_APR_CD_REQUIRE_APPROVAL = "Y";
	public static final String NAME_MISMATCH_APR_CD_PENDING_APPROVAL = "P";
	public static final String NAME_MISMATCH_APR_CD_APPROVED= "A";
	public static final String NAME_MISMATCH_APR_CD_APPROVED_WITH_DIFF_CUST= "D";
	public static final String NAME_MISMATCH_APR_CD_REJECTED= "R";

	public static final String[] NAME_MISMATCH_BEFORE_APPROVAL = 
		{NAME_MISMATCH_APR_CD_NO_MISMATCH, NAME_MISMATCH_APR_CD_REQUIRE_APPROVAL}; 
	public static final String[] NAME_MISMATCH_AFTER_APPROVAL = 
		{NAME_MISMATCH_APR_CD_APPROVED, NAME_MISMATCH_APR_CD_APPROVED_WITH_DIFF_CUST, NAME_MISMATCH_APR_CD_REJECTED};
	
	public static final String MERCHANT_CODE = "055";
	public static final String LTS_BILL_TYPE = "46";
	
	public static final String AMEND_APPROVAL_APPROVED_STATUS = "A";
	public static final String AMEND_APPROVAL_APPROVED_REJECT = "R";
	
	public static final String AMEND_CATEGORY_ORDER_CANCELLATION_VALUE = "C";
	public static final String AMEND_CATEGORY_APPOINTMENT_VALUE = "A";
	public static final String AMEND_CATEGORY_CREDIT_CARD_VALUE = "R";
	public static final String AMEND_CATEGORY_PIPB_INFO_VALUE = "P";
	public static final String AMEND_CATEGORY_DOCUMENT_VALUE = "D";
	public static final String AMEND_CATEGORY_FREE_INPUT_VALUE = "F";

	public static final String [] AMEND_CATEGORYS = {
		AMEND_CATEGORY_ORDER_CANCELLATION_VALUE,
		AMEND_CATEGORY_APPOINTMENT_VALUE,
		AMEND_CATEGORY_CREDIT_CARD_VALUE,
		AMEND_CATEGORY_PIPB_INFO_VALUE,
		AMEND_CATEGORY_DOCUMENT_VALUE,
		AMEND_CATEGORY_FREE_INPUT_VALUE
	};
	
	public static final String AMEND_ITEM_OF_ORDER_CANCEL_TYPE = "TYPE";
	public static final String AMEND_ITEM_OF_ORDER_CANCEL_REASON = "REASON";
	public static final String AMEND_ITEM_OF_ORDER_CANCEL_REMARK = "REMARK";
	
	public static final String AMEND_ITEM_OF_APPOINTMENT_START_TIME = "APPNT_START_TIME";
	public static final String AMEND_ITEM_OF_APPOINTMENT_END_TIME = "APPNT_END_TIME";
	public static final String AMEND_ITEM_OF_PREWIRING_START_TIME = "PREWIRING_START_TIME";
	public static final String AMEND_ITEM_OF_PREWIRING_END_TIME = "PREWIRING_END_TIME";
	public static final String AMEND_ITEM_OF_CUT_OVER_START_TIME = "CUT_OVER_START_TIME";
	public static final String AMEND_ITEM_OF_CUT_OVER_END_TIME = "CUT_OVER_END_TIME";
	public static final String AMEND_ITEM_OF_DELAY_REA_CD = "DEPLAY_REA_CD";
	public static final String AMEND_ITEM_OF_PRE_BOOK_SERIAL = "PRE_BOOK_SERIAL";
	public static final String AMEND_ITEM_OF_APPNT_TYPE = "APPNT_TYPE";
	public static final String AMEND_ITEM_OF_PREWIRING_TYPE = "PREWIRING_TYPE";
	public static final String AMEND_ITEM_OF_APPNT_REMARK = "APPNT_REMARK";
	
	public static final String AMEND_ITEM_OF_TITLE = "TITLE";
	public static final String AMEND_ITEM_OF_FIRST_NAME = "FIRST_NAME";
	public static final String AMEND_ITEM_OF_LAST_NAME = "LAST_NAME";
	public static final String AMEND_ITEM_OF_FLAT = "FLAT";
	public static final String AMEND_ITEM_OF_FLOOR = "FLOOR";
	
	public static final String AMEND_ITEM_OF_THIRTY_PARTY_INDICATOR = "THIRTY_PARTY_IND";
	public static final String AMEND_ITEM_OF_FAX_SERIAL = "FAX_SERIAL";
	public static final String AMEND_ITEM_OF_CREDIT_CARD_HOLDER_NAME = "CC_HOLD_NAME";
	public static final String AMEND_ITEM_OF_CREDIT_CARD_NUMBER = "CC_NUM";
	public static final String AMEND_ITEM_OF_CREDIT_CARD_TYPE = "CC_TYPE";
	public static final String AMEND_ITEM_OF_CREDIT_CARD_EXPIRY_DATE = "CC_EXP_DATE";
	public static final String AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_TYPE = "CC_HOLD_DOC_TYPE";
	public static final String AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_NUM = "CC_HOLD_DOC_NUM";
	public static final String AMEND_ITEM_OF_CREDIT_CARD_REMARK = "CREDIT_REMARK";
	
	public static final String AMEND_ITEM_OF_VAS = "VAS";
	public static final String AMEND_ITEM_OF_BILLING_ADDRESS = "BA";
	public static final String AMEND_ITEM_OF_CHANGE_DN = "CHG_DN";
	public static final String AMEND_ITEM_OF_FSA = "FSA";
	public static final String AMEND_ITEM_OF_CUST_NAME = "CUST_NAME";
	public static final String AMEND_ITEM_OF_INSTALLATION_ADDRESS = "IA";
	public static final String AMEND_ITEM_OF_CONTACT = "CONTACT";
	public static final String AMEND_ITEM_OF_OFFER = "OFFER";
	public static final String AMEND_ITEM_OF_PDPO = "PDPO";
	public static final String AMEND_ITEM_OF_EQUIPMENT = "EQUIP";
	
	public static final String AMEND_ITEM_OF_VAS_NATURE_ID = "310";
	public static final String AMEND_ITEM_OF_BILLING_ADDRESS_NATURE_ID = "301";
	public static final String AMEND_ITEM_OF_CHANGE_DN_ID = "303";
	public static final String AMEND_ITEM_OF_FSA_NATURE_ID = "304";
	public static final String AMEND_ITEM_OF_CUST_NAME_NATURE_ID = "305";
	public static final String AMEND_ITEM_OF_INSTALLATION_ADDRESS_NATURE_ID = "306";
	public static final String AMEND_ITEM_OF_CONTACT_NATURE_ID = "336";
	public static final String AMEND_ITEM_OF_OFFER_NATURE_ID = "337";
	public static final String AMEND_ITEM_OF_PDPO_NATURE_ID = "338";
	public static final String AMEND_ITEM_OF_EQUIPMENT_NATURE_ID = "339";
	public static final String REASON_CODE_DRG_DOWNTIME = "DRG_DOWN";
			
	public static final BiMap<String, String> AMEND_ITEM_OF_FREE_INPUT_MAP = new ImmutableBiMap.Builder<String, String>()
			.put(AMEND_ITEM_OF_VAS_NATURE_ID, AMEND_ITEM_OF_VAS)
			.put(AMEND_ITEM_OF_BILLING_ADDRESS_NATURE_ID, AMEND_ITEM_OF_BILLING_ADDRESS)
			.put(AMEND_ITEM_OF_FSA_NATURE_ID, AMEND_ITEM_OF_FSA)		
			.put(AMEND_ITEM_OF_CUST_NAME_NATURE_ID, AMEND_ITEM_OF_CUST_NAME)
			.put(AMEND_ITEM_OF_INSTALLATION_ADDRESS_NATURE_ID, AMEND_ITEM_OF_INSTALLATION_ADDRESS)
			.put(AMEND_ITEM_OF_CONTACT_NATURE_ID, AMEND_ITEM_OF_CONTACT)
			.put(AMEND_ITEM_OF_OFFER_NATURE_ID, AMEND_ITEM_OF_OFFER)
			.put(AMEND_ITEM_OF_PDPO_NATURE_ID, AMEND_ITEM_OF_PDPO)
			.put(AMEND_ITEM_OF_EQUIPMENT_NATURE_ID, AMEND_ITEM_OF_EQUIPMENT)
			.put(AMEND_ITEM_OF_CHANGE_DN_ID, AMEND_ITEM_OF_CHANGE_DN)
			.build();
	
	public static final String[] AMEND_ITEM_ORDER_CANCEL = {
		AMEND_ITEM_OF_ORDER_CANCEL_TYPE, 
		AMEND_ITEM_OF_ORDER_CANCEL_REASON, 
		AMEND_ITEM_OF_ORDER_CANCEL_REMARK};
	
	public static final String[] AMEND_ITEM_APPOINTMNET = {
		AMEND_ITEM_OF_APPOINTMENT_START_TIME, 
		AMEND_ITEM_OF_APPOINTMENT_END_TIME,
		AMEND_ITEM_OF_PREWIRING_START_TIME,
		AMEND_ITEM_OF_PREWIRING_END_TIME,
		AMEND_ITEM_OF_CUT_OVER_START_TIME,
		AMEND_ITEM_OF_CUT_OVER_END_TIME,
		AMEND_ITEM_OF_DELAY_REA_CD,
		AMEND_ITEM_OF_PRE_BOOK_SERIAL,
		AMEND_ITEM_OF_APPNT_TYPE,
		AMEND_ITEM_OF_PREWIRING_TYPE,
		AMEND_ITEM_OF_APPNT_REMARK};
	
	public static final String[] AMEND_ITEM_PIPB_INFO = {
		AMEND_ITEM_OF_TITLE,
		AMEND_ITEM_OF_FIRST_NAME,
		AMEND_ITEM_OF_LAST_NAME,
		AMEND_ITEM_OF_FLAT,
		AMEND_ITEM_OF_FLOOR
	};
	
	public static final String[] AMEND_ITEM_CREDIT_CARD = {
		AMEND_ITEM_OF_THIRTY_PARTY_INDICATOR,
		AMEND_ITEM_OF_FAX_SERIAL,
		AMEND_ITEM_OF_CREDIT_CARD_HOLDER_NAME,
		AMEND_ITEM_OF_CREDIT_CARD_NUMBER,
		AMEND_ITEM_OF_CREDIT_CARD_TYPE,
		AMEND_ITEM_OF_CREDIT_CARD_EXPIRY_DATE,
		AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_TYPE,
		AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_NUM,
		AMEND_ITEM_OF_CREDIT_CARD_REMARK
	};
	
	public static final String CANCEL_REASON_CD_AMEND = "AMEND";
	
	public static final String AMEND_LOG_UPDATE_STATUS_COMPLETE = "C";
	public static final String AMEND_LOG_UPDATE_STATUS_PARTIAL = "P";
	public static final String AMEND_LOG_UPDATE_STATUS_FAILED = "F";
	
	public static final String[] LTS_AMEND_NOTICE_TEMPLATE_IDS = {
		EMAIL_TEMPLATE_AMEND_NOTICE_SMS,
		EMAIL_TEMPLATE_AMEND_NOTICE_EMAIL
	};
	
	public static final String SEND_METHOD_EMAIL = "E";
	public static final String SEND_METHOD_SMS = "S";
	public static final String SEND_METHOD_PAPER = "P";
	public static final String SEND_DOCUMENT_AF = "AF";
	
	
	public static final String PDPO_UPDATE_BOM_STATUS_INGORE = "I";
	public static final String PDPO_UPDATE_BOM_STATUS_PENDING = "P";
	public static final String PDPO_UPDATE_BOM_STATUS_COMPLETED = "L";
	public static final String PDPO_UPDATE_BOM_STATUS_FAIL = "F";
	
	public static final String CALL_PLAN_TYPE_GIFT = "GIFT";
	public static final String CALL_PLAN_TYPE_FREE = "FREE";
	public static final String CALL_PLAN_TYPE_DUMMY = "DUMMY";
	
	public static final String[] DUMMY_FREE_CALL_PLAN_CDS = {"UMP0", "UM20"};
	
	public static final String SIGNOFF_MODE_RETAIL = "R";
	public static final String SIGNOFF_MODE_CALLCENTER = "C";
	
	public static final String LOST_MODEM_HAVE_EYE_USAGE = "HU";
	public static final String LOST_MODEM_NO_EYE_USAGE = "NU";

	public static final String LOST_MODEM_L2_JOB_CODE = "C20192";
	
	public static final String ROUTER_OPTION_SHARE_RENTAL_ROUTER = "SHARE_RENTAL_ROUTER";
	public static final String ROUTER_OPTION_BRM = "BRM";
	public static final String RPT_SRV_TYPE_ITEM_SPEC = "ITEM_SPEC";
	
	public static final String CALL_PLAN_WAIVE_PEN_MKT_APPV = "MW";
	public static final String CALL_PLAN_WAIVE_PEN_SM_APPV = "SM";
	public static final String CALL_PLAN_WAIVE_PEN_UM_APPV = "UM";
	public static final String CALL_PLAN_PEN_OTHER_PARTY_HNDL = "OTHER_PARTY_HNDL";
	public static final String CALL_PLAN_PEN_FREE_TO_GO = "FREE_TO_GO";
	public static final String CALL_PLAN_PEN_PLAN_NOT_START = "PLAN_NOT_START";
	
	public static final BiMap<String, String> WEEE_PSEF_2ND_CD_MAP = new ImmutableBiMap.Builder<String, String>()
			.put("0HFJ", "0HFM")
			.put("0HFK", "0HFN")
			.put("0HFL", "0HFP")
			.build();
	
}

