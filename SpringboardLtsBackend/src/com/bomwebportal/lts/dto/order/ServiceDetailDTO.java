package com.bomwebportal.lts.dto.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public abstract class ServiceDetailDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -5227936926319254823L;

	private String dtlId;
	private String orderType = null;
	private String srvNum = null;
	private String duplexNum = null;
	private String typeOfSrv = null;
	private String srvTypeCdAction = null;
	private String tenure = null;
	private String dummyDocIdInd = null;
	private String actualDocType = null;
	private String actualDocId = null;
	private String pendingOcid = null;
	private String pendingOcidSrd = null;
	private String thirdPartyAppln = null;
	private String suggestSrd = null;
	private String suggestSrdReasonCd = null;
	private String copyErIaToBa = null;
	private String sbStatus = null;
	private String statusDate = null;
	private String sbReasonCd = null;
	private String discReasonCode = null;
	private String ceaseRentalDate = null;
	private String backDateApplnDate = null;
	private String forceFieldVisitInd = null;
	private ThirdPartyDetailLtsDTO thirdPartyDtl = null;
	private RemarkDetailLtsDTO[] remarks = null;
	private AppointmentDetailLtsDTO appointmentDtl = null;
	private RecontractLtsDTO recontract = null;
	private ServiceCallPlanDetailLtsDTO[] srvCallPlanDtls = null;

	private AmendLtsDTO[] amends = null;
	private String suspendBomInd = null;
	private String suspendBomReasonCd = null;
	private List<ServiceActionTypeDTO> srvActionList = null;
	private String workQueueType = null;
	private String ccServiceId = null;
	private String ccServiceMemNum = null;
	private String erInd = null;
//	private AccountDetailLtsDTO account = null; //For single acct case
	private AccountServiceAssignLtsDTO[] accounts = null;  //For multi acct case
	private String grpType = null;
	private String[] wqRemarks = null;
	private String fromProd = null;
	private String toProd = null;
	private String recontractInd = null;
	private String staffPlanApplicantId = null;
	private String approvedInd = null;
	private PipbLtsDTO pipb = null;
    private String newSrvNum = null;
    private OrderAttbDTO[] orderAttbs = null;
    private Map<String, String> orderAttbMap = null;
    
	public String getDtlId() {
		return dtlId;
	}

	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getTypeOfSrv() {
		return typeOfSrv;
	}

	public void setTypeOfSrv(String typeOfSrv) {
		this.typeOfSrv = typeOfSrv;
	}

	public String getSrvTypeCdAction() {
		return srvTypeCdAction;
	}

	public void setSrvTypeCdAction(String srvTypeCdAction) {
		this.srvTypeCdAction = srvTypeCdAction;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getDummyDocIdInd() {
		return dummyDocIdInd;
	}

	public void setDummyDocIdInd(String dummyDocIdInd) {
		this.dummyDocIdInd = dummyDocIdInd;
	}

	public String getActualDocType() {
		return actualDocType;
	}

	public void setActualDocType(String actualDocType) {
		this.actualDocType = actualDocType;
	}

	public String getActualDocId() {
		return actualDocId;
	}

	public void setActualDocId(String actualDocId) {
		this.actualDocId = actualDocId;
	}

	public String getPendingOcid() {
		return pendingOcid;
	}

	public void setPendingOcid(String pendingOcid) {
		this.pendingOcid = pendingOcid;
	}

	public String getPendingOcidSrd() {
		return pendingOcidSrd;
	}

	public void setPendingOcidSrd(String pendingOcidSrd) {
		this.pendingOcidSrd = pendingOcidSrd;
	}

	public String getThirdPartyAppln() {
		return thirdPartyAppln;
	}

	public void setThirdPartyAppln(String thirdPartyAppln) {
		this.thirdPartyAppln = thirdPartyAppln;
	}

	public String getSuggestSrd() {
		return suggestSrd;
	}

	public void setSuggestSrd(String suggestSrd) {
		this.suggestSrd = suggestSrd;
	}

	public String getSuggestSrdReasonCd() {
		return suggestSrdReasonCd;
	}

	public void setSuggestSrdReasonCd(String suggestSrdReasonCd) {
		this.suggestSrdReasonCd = suggestSrdReasonCd;
	}

	public String getCopyErIaToBa() {
		return copyErIaToBa;
	}

	public void setCopyErIaToBa(String copyErIaToBa) {
		this.copyErIaToBa = copyErIaToBa;
	}

	public ThirdPartyDetailLtsDTO getThirdPartyDtl() {
		return thirdPartyDtl;
	}

	public void setThirdPartyDtl(ThirdPartyDetailLtsDTO thirdPartyDtl) {
		this.thirdPartyDtl = thirdPartyDtl;
	}

	public RemarkDetailLtsDTO[] getRemarks() {
		return remarks;
	}

	public void setRemarks(RemarkDetailLtsDTO[] remarks) {
		this.remarks = remarks;
	}

	public void appendRemark(RemarkDetailLtsDTO remark) {
		this.appendRemarks(new RemarkDetailLtsDTO[] { remark });
	}

	public void appendRemarks(RemarkDetailLtsDTO[] remarks) {

		List<RemarkDetailLtsDTO> remarkList = new ArrayList<RemarkDetailLtsDTO>();

		if (ArrayUtils.isNotEmpty(this.remarks)) {
			remarkList.addAll(Arrays.asList(this.remarks));
		}
		remarkList.addAll(Arrays.asList(remarks));
		this.remarks = remarkList.toArray(new RemarkDetailLtsDTO[remarkList
				.size()]);
	}

	public AppointmentDetailLtsDTO getAppointmentDtl() {
		return appointmentDtl;
	}

	public void setAppointmentDtl(AppointmentDetailLtsDTO appointmentDtl) {
		this.appointmentDtl = appointmentDtl;
	}

	public AmendLtsDTO[] getAmends() {
		return amends;
	}

	public void setAmends(AmendLtsDTO[] amends) {
		this.amends = amends;
	}

	public String getSbStatus() {
		return sbStatus;
	}

	public void setSbStatus(String sbStatus) {
		this.sbStatus = sbStatus;
	}

	public String getSbReasonCd() {
		return sbReasonCd;
	}

	public void setSbReasonCd(String sbReasonCd) {
		this.sbReasonCd = sbReasonCd;
	}

	public String getSuspendBomInd() {
		return suspendBomInd;
	}

	public void setSuspendBomInd(String suspendBomInd) {
		this.suspendBomInd = suspendBomInd;
	}

	public String getSuspendBomReasonCd() {
		return suspendBomReasonCd;
	}

	public void setSuspendBomReasonCd(String suspendBomReasonCd) {
		this.suspendBomReasonCd = suspendBomReasonCd;
	}

	public ServiceActionTypeDTO[] getSrvActions() {
		if (this.srvActionList == null || this.srvActionList.size() == 0) {
			return null;
		}
		return this.srvActionList
				.toArray(new ServiceActionTypeDTO[this.srvActionList.size()]);
	}

	public void setSrvActions(ServiceActionTypeDTO[] srvActions) {
		this.srvActionList = new ArrayList<ServiceActionTypeDTO>();
		this.srvActionList.addAll(Arrays.asList(srvActions));
	}

	public void addSrvAction(ServiceActionTypeDTO pSrvAction) {

		if (this.srvActionList == null) {
			this.srvActionList = new ArrayList<ServiceActionTypeDTO>();
		}
		this.srvActionList.add(pSrvAction);
	}

	public String getWorkQueueType() {
		return workQueueType;
	}

	public void setWorkQueueType(String workQueueType) {
		this.workQueueType = workQueueType;
	}

	public String getCcServiceId() {
		return ccServiceId;
	}

	public void setCcServiceId(String ccServiceId) {
		this.ccServiceId = ccServiceId;
	}

	public String getCcServiceMemNum() {
		return ccServiceMemNum;
	}

	public void setCcServiceMemNum(String ccServiceMemNum) {
		this.ccServiceMemNum = ccServiceMemNum;
	}

	public String getErInd() {
		return erInd;
	}

	public void setErInd(String erInd) {
		this.erInd = erInd;
	}

	// Only Get Future "R" Account
	public AccountDetailLtsDTO getAccount() {
		if (ArrayUtils.isEmpty(accounts)) {
			return null;
		}
		for (AccountServiceAssignLtsDTO accountSrvAssgn : accounts) {
			if (accountSrvAssgn.getAccount() != null 
					&& !(ACTION_DELETE == accountSrvAssgn.getObjectAction()) 
					&& LtsBackendConstant.ACCOUNT_CHARGE_TYPE_R.equals(accountSrvAssgn.getChrgType())) {
				
				if (LtsBackendConstant.IO_IND_INSTALL.equals(accountSrvAssgn.getAction())
						|| LtsBackendConstant.IO_IND_SPACE.equals(accountSrvAssgn.getAction())) {
							return accountSrvAssgn.getAccount();				
				}
				
				if (LtsBackendConstant.ORDER_TYPE_DISCONNECT.equals(orderType)) {
					if (LtsBackendConstant.IO_IND_OUT.equals(accountSrvAssgn.getAction())) {
						return accountSrvAssgn.getAccount();				
					}
				}
			}
		}
		return null;
	}
	
	// Get Profile "R" Account() 
	public AccountDetailLtsDTO getProfileAccount() {
		if (ArrayUtils.isEmpty(accounts)) {
			return null;
		}
		for (AccountServiceAssignLtsDTO accountSrvAssgn : accounts) {
			if (accountSrvAssgn.getAccount() != null 
					&& !(ACTION_DELETE == accountSrvAssgn.getObjectAction()) 
					&& LtsBackendConstant.ACCOUNT_CHARGE_TYPE_R.equals(accountSrvAssgn.getChrgType())
					&& (LtsBackendConstant.IO_IND_OUT.equals(accountSrvAssgn.getAction())
							|| LtsBackendConstant.IO_IND_SPACE.equals(accountSrvAssgn.getAction()))) {
				return accountSrvAssgn.getAccount();
			}
		}
		return null;
	}
	

//	public void setAccount(AccountDetailLtsDTO account) {
//		this.account = account;
//	}

	public String getGrpType() {
		return grpType;
	}

	public void setGrpType(String grpType) {
		this.grpType = grpType;
	}

	public String getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(String pStatusDate) {
		this.statusDate = pStatusDate;
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

	public String getDuplexNum() {
		return duplexNum;
	}

	public void setDuplexNum(String duplexNum) {
		this.duplexNum = duplexNum;
	}

	public String remarkToString(String pRmkType) {

		String[] remarks = this.remarkToArray(pRmkType);
		StringBuilder sb = new StringBuilder();

		for (int i = 0; remarks != null && i < remarks.length; ++i) {
			sb.append(remarks[i]);
		}
		return sb.toString();
	}

	public String[] remarkToArray(String pRmkType) {

		List<RemarkDetailLtsDTO> remarkList = new ArrayList<RemarkDetailLtsDTO>();

		for (int i = 0; this.remarks != null && i < remarks.length; ++i) {
			if (StringUtils.equals(pRmkType, this.remarks[i].getRmkType())) {
				remarkList.add(this.remarks[i]);
			}
		}
		if (remarkList.size() == 0) {
			return null;
		}
		RemarkDetailLtsDTO[] remarks = remarkList
				.toArray(new RemarkDetailLtsDTO[remarkList.size()]);
		Arrays.sort(remarks, new Comparator<RemarkDetailLtsDTO>() {
			public int compare(RemarkDetailLtsDTO pRmk1,
					RemarkDetailLtsDTO pRmk2) {
				return pRmk1.getRmkSeq().compareTo(pRmk2.getRmkSeq());
			}
		});
		String[] remarkArr = new String[remarkList.size()];

		for (int i = 0; i < remarks.length; ++i) {
			remarkArr[i] = remarks[i].getRmkDtl();
		}
		return remarkArr;
	}

	public String[] getWqRemarks() {
		return this.wqRemarks;
	}

	public void setWqRemarks(String[] pWqRemarks) {
		this.wqRemarks = pWqRemarks;
	}

	public RecontractLtsDTO getRecontract() {
		return recontract;
	}

	public void setRecontract(RecontractLtsDTO recontract) {
		this.recontract = recontract;
	}

	public String getRecontractInd() {
		return recontractInd;
	}

	public void setRecontractInd(String recontractInd) {
		this.recontractInd = recontractInd;
	}

	public ServiceCallPlanDetailLtsDTO[] getSrvCallPlanDtls() {
		return srvCallPlanDtls;
	}

	public void setSrvCallPlanDtls(ServiceCallPlanDetailLtsDTO[] srvCallPlanDtls) {
		this.srvCallPlanDtls = srvCallPlanDtls;
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

	public String getCeaseRentalDate() {
		return ceaseRentalDate;
	}

	public void setCeaseRentalDate(String ceaseRentalDate) {
		this.ceaseRentalDate = ceaseRentalDate;
	}

	public String getBackDateApplnDate() {
		return backDateApplnDate;
	}

	public void setBackDateApplnDate(String backDateApplnDate) {
		this.backDateApplnDate = backDateApplnDate;
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

	public PipbLtsDTO getPipb() {
		return pipb;
	}

	public void setPipb(PipbLtsDTO pipb) {
		this.pipb = pipb;
	}

	public AccountServiceAssignLtsDTO[] getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountServiceAssignLtsDTO[] accounts) {
		this.accounts = accounts;
	}

	public String getNewSrvNum() {
		return newSrvNum;
	}

	public void setNewSrvNum(String newSrvNum) {
		this.newSrvNum = newSrvNum;
	}

	public OrderAttbDTO[] getOrderAttbs() {
		return orderAttbs;
	}

	public void setOrderAttbs(OrderAttbDTO[] orderAttbs) {
		this.orderAttbs = orderAttbs;
		orderAttbMap = new HashMap<String, String>();
		if(orderAttbs != null){
			for(OrderAttbDTO attb: orderAttbs){
				if(attb.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE){
					orderAttbMap.put(attb.getAttbName(), attb.getAttbValue());
				}
			}
		}
			
	}

	public String getOrderAttb(Enum<?> attbEnum) {
		return orderAttbMap == null? null : orderAttbMap.get(attbEnum.name());
	}
	
	public String getOrderAttb(String attbName) {
		return orderAttbMap == null? null : orderAttbMap.get(attbName);
	}
	
	public Map<String, String> getOrderAttbMap() {
		return orderAttbMap;
	}


}
