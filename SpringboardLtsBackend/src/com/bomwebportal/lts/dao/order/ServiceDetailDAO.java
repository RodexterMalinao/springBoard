package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ServiceDetailDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -72206381928291420L;

	private String orderId; // BOMWEB_ORDER_SERVICE.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_SERVICE.DTL_ID
	private String orderType; // BOMWEB_ORDER_SERVICE.ORDER_TYPE
	private String typeOfSrv; // BOMWEB_ORDER_SERVICE.TYPE_OF_SRV
	private String srvNum; // BOMWEB_ORDER_SERVICE.SRV_NUM
	private String srvActionTypeCd; // BOMWEB_ORDER_SERVICE.SRV_ACTION_TYPE_CD
	private OraNumber tenure; // BOMWEB_ORDER_SERVICE.TENURE
	private String dummyDocIdInd; // BOMWEB_ORDER_SERVICE.DUMMY_DOC_ID_IND
	private String actualDocType; // BOMWEB_ORDER_SERVICE.ACTUAL_DOC_TYPE
	private String actualDocId; // BOMWEB_ORDER_SERVICE.ACTUAL_DOC_ID
	private String thirdPartyApplnInd; // BOMWEB_ORDER_SERVICE.THIRD_PARTY_APPLN_IND
	private OraDate suggestSrd = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_SERVICE.SUGGEST_SRD
	private String suggestSrdReasonCd; // BOMWEB_ORDER_SERVICE.SUGGEST_SRD_REASON_CD
	private String copyNewIaToBaInd; // BOMWEB_ORDER_SERVICE.COPY_NEW_IA_TO_BA_IND
	private OraDate srvReqDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_SERVICE.SRV_REQ_DATE
	private String createBy; // BOMWEB_ORDER_SERVICE.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_SERVICE.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_SERVICE.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_SERVICE.LAST_UPD_DATE
	private String ccServiceId; // BOMWEB_ORDER_SERVICE.CC_SERVICE_ID
	private String ccServiceMemNum; // BOMWEB_ORDER_SERVICE.CC_SERVICE_MEM_NUM
	private String erInd; // BOMWEB_ORDER_SERVICE.ER_IND
	private String suspendBomInd; // BOMWEB_ORDER_SERVICE.SUSPEND_BOM_IND
	private String suspendBomReasonCd; // BOMWEB_ORDER_SERVICE.SUSPEND_BOM_REASON_CD
	private String acctNo; // BOMWEB_ORDER_SERVICE.ACCT_NO
	private String grpType; // BOMWEB_ORDER_SERVICE.GRP_TYPE
	private OraDate pendingOcidSrd; // BOMWEB_ORDER_SERVICE.PENDING_OCID_SRD
	private OraNumber pendingOcid; // BOMWEB_ORDER_SERVICE.PENDING_OCID
	private String fromProd; // BOMWEB_ORDER_SERVICE.FROM_PROD
	private String toProd; // BOMWEB_ORDER_SERVICE.TO_PROD
	private String recontractInd; // BOMWEB_ORDER_SERVICE.RECONTRACT_IND
	private String staffPlanApplicantId; // BOMWEB_ORDER_SERVICE.STAFF_PLAN_APPLICANT_ID
	private String discReasonCode; // BOMWEB_ORDER_SERVICE.DISC_REASON_CODE
	private OraDate ceaseRentalDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_SERVICE.CEASE_RENTAL_DATE
	private OraDate backDateApplnDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_SERVICE.BACK_DATE_APPLN_DATE
	private String forceFieldVisitInd; // BOMWEB_ORDER_SERVICE.FORCE_FIELD_VISIT_IND
	private String approvedInd; // BOMWEB_ORDER_SERVICE.APPROVED_IND
	
	private String newSrvNum; // BOMWEB_ORDER_SERVICE.NEW_SRV_NUM

	public ServiceDetailDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId" };
	}

	public String getTableName() {
		return "BOMWEB_ORDER_SERVICE";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public String getTypeOfSrv() {
		return this.typeOfSrv;
	}

	public String getSrvNum() {
		return this.srvNum;
	}

	public String getSrvActionTypeCd() {
		return this.srvActionTypeCd;
	}

	public String getDummyDocIdInd() {
		return this.dummyDocIdInd;
	}

	public String getActualDocType() {
		return this.actualDocType;
	}

	public String getActualDocId() {
		return this.actualDocId;
	}

	public String getThirdPartyApplnInd() {
		return this.thirdPartyApplnInd;
	}

	public String getSuggestSrdReasonCd() {
		return this.suggestSrdReasonCd;
	}

	public String getCopyNewIaToBaInd() {
		return this.copyNewIaToBaInd;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getCcServiceId() {
		return this.ccServiceId;
	}

	public String getCcServiceMemNum() {
		return this.ccServiceMemNum;
	}

	public String getErInd() {
		return this.erInd;
	}

	public String getSuspendBomInd() {
		return this.suspendBomInd;
	}

	public String getSuspendBomReasonCd() {
		return this.suspendBomReasonCd;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	public String getTenure() {
		return this.tenure != null ? this.tenure.toString() : null;
	}
	
	public String getPendingOcid() {
		return this.pendingOcid != null ? this.pendingOcid.toString() : null;
	}
	
	public String getSuggestSrd() {
		return this.suggestSrd != null ? this.suggestSrd.toString() : null;
	}
	
	public String getBackDateApplnDate() {
		return this.backDateApplnDate != null ? this.backDateApplnDate.toString() : null;
	}
	
	public String getCeaseRentalDate() {
		return this.ceaseRentalDate != null ? this.ceaseRentalDate.toString() : null;
	}
	
	public String getPendingOcidSrd() {
		return this.pendingOcidSrd != null ? this.pendingOcidSrd.toString() : null;
	}

	public String getSrvReqDate() {
		return this.srvReqDate != null ? this.srvReqDate.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getSuggestSrdORACLE() {
		return this.suggestSrd;
	}
	
	public OraDate getBackDateApplnDateORACLE() {
		return this.backDateApplnDate;
	}
	
	public OraDate getCeaseRentalDateORACLE() {
		return this.ceaseRentalDate;
	}
	
	public OraDate getPendingOcidSrdORACLE() {
		return this.pendingOcidSrd;
	}

	public OraDate getSrvReqDateORACLE() {
		return this.srvReqDate;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setOrderType(String pOrderType) {
		this.orderType = pOrderType;
	}

	public void setTypeOfSrv(String pTypeOfSrv) {
		this.typeOfSrv = pTypeOfSrv;
	}

	public void setSrvNum(String pSrvNum) {
		this.srvNum = pSrvNum;
	}

	public void setSrvActionTypeCd(String pSrvActionTypeCd) {
		this.srvActionTypeCd = pSrvActionTypeCd;
	}

	public void setDummyDocIdInd(String pDummyDocIdInd) {
		this.dummyDocIdInd = pDummyDocIdInd;
	}

	public void setActualDocType(String pActualDocType) {
		this.actualDocType = pActualDocType;
	}

	public void setActualDocId(String pActualDocId) {
		this.actualDocId = pActualDocId;
	}

	public void setThirdPartyApplnInd(String pThirdPartyApplnInd) {
		this.thirdPartyApplnInd = pThirdPartyApplnInd;
	}

	public void setSuggestSrdReasonCd(String pSuggestSrdReasonCd) {
		this.suggestSrdReasonCd = pSuggestSrdReasonCd;
	}

	public void setCopyNewIaToBaInd(String pCopyNewIaToBaInd) {
		this.copyNewIaToBaInd = pCopyNewIaToBaInd;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setCcServiceId(String pCcServiceId) {
		this.ccServiceId = pCcServiceId;
	}

	public void setCcServiceMemNum(String pCcServiceMemNum) {
		this.ccServiceMemNum = pCcServiceMemNum;
	}

	public void setErInd(String pErInd) {
		this.erInd = pErInd;
	}

	public void setSuspendBomInd(String pSuspendBomInd) {
		this.suspendBomInd = pSuspendBomInd;
	}

	public void setSuspendBomReasonCd(String pSuspendBomReasonCd) {
		this.suspendBomReasonCd = pSuspendBomReasonCd;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	public void setTenure(String pTenure) {
		this.tenure = new OraNumber(pTenure);
	}
	
	public void setPendingOcid(String pPendingOcid) {
		this.pendingOcid = new OraNumber(pPendingOcid);
	}
	
	public void setSuggestSrd(String pSuggestSrd) {
		this.suggestSrd = new OraDateYYYYMMDDHH24MISS(pSuggestSrd);
	}
	
	public void setBackDateApplnDate(String pBackDateApplnDate) {
		this.backDateApplnDate = new OraDateYYYYMMDDHH24MISS(pBackDateApplnDate);
	}
	
	public void setCeaseRentalDate(String pCeaseRentalDate) {
		this.ceaseRentalDate = new OraDateYYYYMMDDHH24MISS(pCeaseRentalDate);
	}
	
	public void setPendingOcidSrd(String pPendingOcidSrd) {
		this.pendingOcidSrd = new OraDateYYYYMMDDHH24MISS(pPendingOcidSrd);
	}

	public void setSrvReqDate(String pSrvReqDate) {
		this.srvReqDate = new OraDateYYYYMMDDHH24MISS(pSrvReqDate);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getGrpType() {
		return grpType;
	}

	public void setGrpType(String grpType) {
		this.grpType = grpType;
	}

	public String getFromProd() {
		return fromProd;
	}

	public void setFromProd(String fromProd) {
		this.fromProd = fromProd;
	}

	public String getToProd() {
		return toProd;
	}

	public void setToProd(String toProd) {
		this.toProd = toProd;
	}

	public String getRecontractInd() {
		return recontractInd;
	}

	public void setRecontractInd(String recontractInd) {
		this.recontractInd = recontractInd;
	}

	public String getStaffPlanApplicantId() {
		return staffPlanApplicantId;
	}

	public void setStaffPlanApplicantId(String staffPlanApplicantId) {
		this.staffPlanApplicantId = staffPlanApplicantId;
	}

	public String getDiscReasonCode() {
		return discReasonCode;
	}

	public void setDiscReasonCode(String discReasonCode) {
		this.discReasonCode = discReasonCode;
	}

	public String getForceFieldVisitInd() {
		return forceFieldVisitInd;
	}

	public void setForceFieldVisitInd(String forceFieldVisitInd) {
		this.forceFieldVisitInd = forceFieldVisitInd;
	}

	public String getApprovedInd() {
		return approvedInd;
	}

	public void setApprovedInd(String approvedInd) {
		this.approvedInd = approvedInd;
	}

	public String getNewSrvNum() {
		return newSrvNum;
	}

	public void setNewSrvNum(String newSrvNum) {
		this.newSrvNum = newSrvNum;
	}
}
