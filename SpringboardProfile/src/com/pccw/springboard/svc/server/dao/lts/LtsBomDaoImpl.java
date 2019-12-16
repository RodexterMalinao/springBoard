package com.pccw.springboard.svc.server.dao.lts;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.bom.OrderAccountDTO;
import com.bomwebportal.lts.dto.bom.OrderServiceDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountServiceLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerContactProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerRemarkProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.RecontractRemarkDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.bom.BomOrderInfoService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.OfferProfileLtsService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.service.order.SbOrderInfoLtsService;
import com.bomwebportal.lts.util.LtsProfileConstant;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.springboard.svc.server.dao.DaoException;
import com.pccw.springboard.svc.server.dto.AmAsmDTO;
import com.pccw.springboard.svc.server.dto.CampaignDTO;
import com.pccw.springboard.svc.server.dto.Contact;
import com.pccw.springboard.svc.server.dto.RelatedAcctDTO;
import com.pccw.springboard.svc.server.dto.custsearch.AccountDTO;
import com.pccw.springboard.svc.server.dto.custsearch.ServiceSummaryDTO;
import com.pccw.springboard.svc.server.dto.lts.IddffpInfoDTO;
import com.pccw.springboard.svc.server.dto.lts.LtsAcctProfileDTO;
import com.pccw.springboard.svc.server.dto.lts.LtsCustomerProfileDTO;
import com.pccw.springboard.svc.server.dto.lts.LtsDataPrivacyDTO;
import com.pccw.springboard.svc.server.dto.lts.LtsServiceProfileDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailCommitmentDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailDiscountDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailIncentiveDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailRecurringChargesDTO;
import com.pccw.springboard.svc.server.dto.remark.CustomerRemark;
import com.pccw.springboard.svc.server.dto.remark.RecontractRemark;
import com.pccw.springboard.svc.server.dto.remark.SpecialHandlingRemark;
import com.pccw.springboard.svc.server.dto.remark.WrittenInPersonRemark;

public class LtsBomDaoImpl implements LtsBomDao {

	private CustomerProfileLtsService customerProfileLtsService = null;
	private ServiceProfileLtsService serviceProfileLtsService = null;
	private OfferProfileLtsService offerProfileLtsService = null;
	private SbOrderInfoLtsService sbOrderInfoLtsService = null;
	private BomOrderInfoService bomOrderInfoService = null;
	private CodeLkupCacheService idDocTypeLkupCacheService = null;
	private CodeLkupCacheService custCatgLkupCacheService = null;
	private CodeLkupCacheService billFreqLkupCacheService = null;
	private CodeLkupCacheService billMediaLkupCacheService = null;
	private CodeLkupCacheService langCodeLkupCacheService = null;
	private CodeLkupCacheService payMethodLkupCacheService = null;
	
	
	public LtsCustomerProfileDTO getLtsCustomerProfile(String pSystemId, String pCustNum) throws DaoException {

		CustomerDetailProfileLtsDTO cust = this.customerProfileLtsService.getCustByCustNum(pCustNum, pSystemId);
		
		if (cust == null) {
			return null;
		}
		LtsCustomerProfileDTO rtnCust = new LtsCustomerProfileDTO();
		rtnCust.setBlacklistInd(cust.isBlacklistCustInd() ? "Y" : "N");
		rtnCust.setCompanyName(cust.getCompanyName());
		rtnCust.setCustCatCode(cust.getCustCatg());
		rtnCust.setCustCatDesc((String)this.custCatgLkupCacheService.get(cust.getCustCatg()));
		rtnCust.setCustomerRemarksInd(ArrayUtils.isNotEmpty(this.customerProfileLtsService.getCustomerRemark(cust, pSystemId)) ? "Y" : "N");
		rtnCust.setFirstName(cust.getFirstName());
		rtnCust.setGrpIdDocNum(cust.getDocNum());
		rtnCust.setGrpIdDocTypeCode(cust.getDocType());
		rtnCust.setGrpIdDocTypeDesc((String)this.idDocTypeLkupCacheService.get(cust.getDocType()));
		rtnCust.setIdDocNum(cust.getDocNum());
		rtnCust.setIdDocTypeCode(cust.getDocType());
		rtnCust.setIdDocTypeDesc((String)this.idDocTypeLkupCacheService.get(cust.getDocType()));
		rtnCust.setIdVerified(cust.isIdVerifyInd() ? "Y" : "N");
		rtnCust.setLastName(cust.getLastName());
		rtnCust.setPremier(cust.getPremierType());
		rtnCust.setSpecialHandlingInd(cust.isSpecialHandle() ? "Y" : "N");
		rtnCust.setTitle(cust.getTitle());
		rtnCust.setWipInd(cust.getWipInd());
		
		CustomerContactProfileLtsDTO[] contacts = this.customerProfileLtsService.getCustContactInfo(pCustNum, pSystemId);
		
		if (ArrayUtils.isNotEmpty(contacts)) {
			Contact rtnContact = new Contact();
			rtnContact.setContactPerson(contacts[0].getContactName());
			
			for (int i=0; i<contacts.length; ++i) {
				if (StringUtils.equals(LtsProfileConstant.CONTACT_MEDIA_TYPE_PHONE, contacts[i].getMediaType())) {
					if (StringUtils.equals(LtsProfileConstant.CONTACT_PHONE_DAY, contacts[i].getMediaKey())) {
						rtnContact.setDayPhone(contacts[i].getMediaNum());
					} else if (StringUtils.equals(LtsProfileConstant.CONTACT_PHONE_NIGHT, contacts[i].getMediaKey())) {
						rtnContact.setNightPhone(contacts[i].getMediaNum());
					}
				} else if (StringUtils.equals(LtsProfileConstant.CONTACT_MEDIA_TYPE_MOBILE, contacts[i].getMediaType())) {
					rtnContact.setMobileNum(contacts[i].getMediaNum());
				} else if (StringUtils.equals(LtsProfileConstant.CONTACT_MEDIA_TYPE_EMAIL, contacts[i].getMediaType())) {
					rtnContact.setContactEmail(contacts[i].getMediaNum());
				}
			}
			rtnCust.setPrimaryContact(rtnContact);
		}
		return rtnCust;
	}
	
	public List<CustomerRemark> getCustomerRemark(String pSystemId, String pCustNum) throws DaoException {
		
		CustomerDetailProfileLtsDTO cust = new CustomerDetailProfileLtsDTO();
		cust.setCustNum(pCustNum);
		CustomerRemarkProfileLtsDTO[] custRmks = this.customerProfileLtsService.getCustomerRemark(cust, pSystemId);
		
		if (custRmks == null || custRmks.length == 0) {
			return null;
		}
		List<CustomerRemark> custRmkList = new ArrayList<CustomerRemark>();
		CustomerRemark rtnCustRmk = null;
		
		for (int i=0; i<custRmks.length; ++i) {
			rtnCustRmk = new CustomerRemark();
			rtnCustRmk.setRemark(custRmks[i].getRemarks());
			rtnCustRmk.setUpdatedBy(custRmks[i].getLastUpdBy());	
			rtnCustRmk.setUpdatedTime(custRmks[i].getLastUpdDate());
			custRmkList.add(rtnCustRmk);
		}
		return custRmkList;
	}
	
	public List<WrittenInPersonRemark> getWrittenInPersonRemark(String pSystemId, String pCustNum) throws DaoException{

		CustomerRemarkProfileLtsDTO[] wipRmks = this.customerProfileLtsService.getWipRemark(pCustNum, pSystemId);
		
		if (ArrayUtils.isEmpty(wipRmks)) {
			return null;
		}
		List<WrittenInPersonRemark> wipRmkList = new ArrayList<WrittenInPersonRemark>();
		WrittenInPersonRemark wipRmk = null;
		
		for (int i=0; i<wipRmks.length; ++i) {
			wipRmk = new WrittenInPersonRemark();
			wipRmk.setUpdatedBy(wipRmks[i].getLastUpdBy());
			wipRmk.setUpdatedTime(wipRmks[i].getLastUpdDate());
			wipRmk.setWipMessage(wipRmks[i].getRemarks());
			wipRmkList.add(wipRmk);
		}
		return wipRmkList;
	}
	
	public List<SpecialHandlingRemark> getSpecialHandlingRemark(String pSystemId, String pCustNum) throws DaoException {
				
		CustomerRemarkProfileLtsDTO[] specRmks = this.customerProfileLtsService.getCustomerSpecialRemark(pCustNum, pSystemId);
		
		if (specRmks == null || specRmks.length == 0) {
			return null;
		}
		 List<SpecialHandlingRemark> specRmkList = new ArrayList<SpecialHandlingRemark>();
		 SpecialHandlingRemark specRmk = null;
		 
		 for (int i=0; i<specRmks.length; ++i) {
			 specRmk = new SpecialHandlingRemark();
			 specRmk.setRemark(specRmks[i].getRemarks());
			 specRmk.setUpdatedBy(specRmks[i].getLastUpdBy());
			 specRmk.setUpdatedTime(specRmks[i].getLastUpdDate());
			 specRmkList.add(specRmk);
		 }
		 return specRmkList;
	}
	
	public List<RecontractRemark> getRecontractRemark(String pSystemId, String pCustNum) throws DaoException {
		
		RecontractRemarkDTO[] recontractRmks = this.customerProfileLtsService.getRecontractRemark(pCustNum, pSystemId);
		
		if (ArrayUtils.isEmpty(recontractRmks)) {
			return null;
		}
		List<RecontractRemark> recontractRmkList = new ArrayList<RecontractRemark>();
		RecontractRemark rtnRecontractRmk = null;
		
		for (int i=0; i<recontractRmks.length; ++i) {
			rtnRecontractRmk = new RecontractRemark();
			rtnRecontractRmk.setRemark(recontractRmks[i].getRemark());
			rtnRecontractRmk.setSrcAcctNum(recontractRmks[i].getSrcAcctNum());
			rtnRecontractRmk.setSrcCustName(recontractRmks[i].getSrcCustName());
			rtnRecontractRmk.setSrcCustNum(recontractRmks[i].getSrcCustNum());
			rtnRecontractRmk.setSrcDocNum(recontractRmks[i].getSrcDocNum());
			rtnRecontractRmk.setSrcDocTypeCode(recontractRmks[i].getSrcDocType());
			rtnRecontractRmk.setSrcDocTypeDesc((String)this.idDocTypeLkupCacheService.get(recontractRmks[i].getSrcDocType()));
			rtnRecontractRmk.setSrvNum(recontractRmks[i].getSrvNum());
			rtnRecontractRmk.setTargetAcctNum(recontractRmks[i].getTargetAcctNum());
			rtnRecontractRmk.setTargetCustName(recontractRmks[i].getTargetCustName());
			rtnRecontractRmk.setTargetCustNum(recontractRmks[i].getTargetCustNum());
			rtnRecontractRmk.setTargetDocNum(recontractRmks[i].getTargetDocNum());
			rtnRecontractRmk.setTargetDocTypeCode(recontractRmks[i].getTargetDocType());
			rtnRecontractRmk.setTargetDocTypeDesc((String)this.idDocTypeLkupCacheService.get(recontractRmks[i].getTargetDocType()));
			rtnRecontractRmk.setUpdatedBy(recontractRmks[i].getLastUpdBy());
			rtnRecontractRmk.setUpdatedTime(recontractRmks[i].getLastUpdDate());
			recontractRmkList.add(rtnRecontractRmk);
		}
		return recontractRmkList;
	}

	public LtsAcctProfileDTO getLtsAcctProfile(String pSystemId, String pAcctNum) throws DaoException {
		
		AccountDetailProfileLtsDTO acct = this.customerProfileLtsService.getAccountbyAcctNum(pAcctNum, pSystemId);
		
		if (acct == null) {
			return null;
		}
		LtsAcctProfileDTO rtnAcct = new LtsAcctProfileDTO();
		rtnAcct.setBillDay(acct.getBillPeriod());
		rtnAcct.setBillFrequencyCode(acct.getBillFreq());
		rtnAcct.setBillFrequencyDesc((String)this.billFreqLkupCacheService.get(acct.getBillFreq()));
		rtnAcct.setBillingAccount(pAcctNum);
		rtnAcct.setBillingAccountStatus(acct.getCreditStatus());
		rtnAcct.setBillingAddress(acct.getBillAddr());
		rtnAcct.setBillingEmailAddress(acct.getEmailAddr());
		rtnAcct.setBillingName(acct.getAcctName());
		rtnAcct.setBillLanguageCode(acct.getBillLang());
		rtnAcct.setBillLanguageDesc((String)this.langCodeLkupCacheService.get(acct.getBillLang()));
		rtnAcct.setBillMediaCode(acct.getBillMedia());
		rtnAcct.setBillMediaDesc((String)this.billMediaLkupCacheService.get(acct.getBillMedia()));
		rtnAcct.setOsAmount(acct.getOutstandingAmount());
		rtnAcct.setPaymentMethodCode(acct.getPayMethod());
		rtnAcct.setPaymentMethodDesc((String)this.payMethodLkupCacheService.get(acct.getPayMethod()));
//		rtnAcct.setSmsNumber(smsNumber);
		return rtnAcct;
	}

	public LtsServiceProfileDTO getLtsServiceProfile(String pSystemId, String pServiceId) throws DaoException {
		
		ServiceDetailProfileLtsDTO srv = this.serviceProfileLtsService.getSimpleServiceProfile(pServiceId, pSystemId);
		
		if (srv == null) {
			return null;
		}
		this.offerProfileLtsService.getProfileDetails(srv);
		LtsServiceProfileDTO rtnSrv = new LtsServiceProfileDTO();
		rtnSrv.setDatCode(srv.getDatCd());
		rtnSrv.setDuplexBnumber(srv.getDuplexNum());
		rtnSrv.setEyeInd(srv.getSrvGrp() == null ? "N" : "Y");
		rtnSrv.setEyeType(srv.getExistEyeType());
		rtnSrv.setServiceLineStatus(srv.getInventStsDesc());
		rtnSrv.setServiceNumber(srv.getSrvNum());
		rtnSrv.setServiceStartDate(srv.getEffStartDate());
		rtnSrv.setServiceType(srv.getSrvType());
		rtnSrv.setTariff(srv.getTariff());
		rtnSrv.setPendingOrder((StringUtils.isBlank(this.checkBomPendingOrder(srv.getSrvNum(), srv.getSrvType()))
				&& StringUtils.isBlank(this.sbOrderInfoLtsService.getSbLtsLatestPendingOrderBySrvNum(srv.getSrvNum(), srv.getSrvType()))) ? "N" : "Y");
		return rtnSrv;
	}
	
	public LtsServiceProfileDTO getLtsTerminatedServiceProfile(String pSystemId, String pServiceId) throws DaoException {

		ServiceDetailProfileLtsDTO srv = this.serviceProfileLtsService.getTerminatedServiceProfile(pServiceId, pSystemId);
		
		if (srv == null) {
			return null;
		}
		this.offerProfileLtsService.getProfileDetails(srv);
		LtsServiceProfileDTO rtnSrv = new LtsServiceProfileDTO();
		rtnSrv.setDatCode(srv.getDatCd());
		rtnSrv.setDuplexBnumber(srv.getDuplexNum());
		rtnSrv.setEyeInd(srv.getSrvGrp() == null ? "N" : "Y");
		rtnSrv.setEyeType(srv.getExistEyeType());
		rtnSrv.setServiceNumber(srv.getSrvNum());
		rtnSrv.setServiceStartDate(srv.getEffStartDate());
		rtnSrv.setServiceEndDate(srv.getEffEndDate());
		rtnSrv.setServiceType(srv.getSrvType());
		rtnSrv.setTariff(srv.getTariff());
		return rtnSrv;
	}
	
	public List<RelatedAcctDTO> getServiceRelatedAccounts(String pSystemId, String pServiceId) throws DaoException {

		AccountServiceLtsDTO[] acctSrvAssgns = this.serviceProfileLtsService.getServiceAccoutAssgn(pServiceId, pSystemId);
		
		if (acctSrvAssgns == null || acctSrvAssgns.length == 0) {
			return null;
		}
		List<RelatedAcctDTO> acctList = new ArrayList<RelatedAcctDTO>();
		RelatedAcctDTO acct = null;
		
		for (int i=0; i<acctSrvAssgns.length; ++i) {
			acct = new RelatedAcctDTO();
			acct.setAccountNum(acctSrvAssgns[i].getAcctNum());
			acct.setAccountType(acctSrvAssgns[i].getAcctType());
			acct.setChargeCat(acctSrvAssgns[i].getChargeType());
			acct.setCustName(acctSrvAssgns[i].getAcctName());
			acct.setCustNum(acctSrvAssgns[i].getCustNum());
			acct.setEffectiveDate(acctSrvAssgns[i].getEffStartDate());
			acct.setTermDate(acctSrvAssgns[i].getEffEndStart());
			acctList.add(acct);
		}
		return acctList;
	}

	public String checkBomPendingOrder(String pSrvNum, String pSrvType) throws DaoException {
		
		PendingOrdStatusDetailDTO status = this.serviceProfileLtsService.getPendingOrder(pSrvNum, pSrvType);
		
		if (status == null) {
			return null;
		}
		return status.getOcid();
	}
	
	public List<ServiceSummaryDTO> getBomPendingInstallService(String pSystemId, String pCustNum) throws DaoException {
		
		OrderServiceDTO[] srvs = this.bomOrderInfoService.getLtsInstallPendingOrderByCust(pCustNum, pSystemId);
		List<ServiceSummaryDTO> rtnSrvList = new ArrayList<ServiceSummaryDTO>();
		ServiceSummaryDTO rtnSrv = null;
		
		if (ArrayUtils.isEmpty(srvs)) {
			return null;
		}
		for (int i=0; i<srvs.length; ++i) {
			rtnSrv = new ServiceSummaryDTO();
			rtnSrv.setAddress_id(srvs[i].getAddress() == null ? null : srvs[i].getAddress().getAddrId());
//			rtnSrv.setAddressPremier(addressPremier);
			rtnSrv.setDatCode(srvs[i].getDatCd());
			rtnSrv.setDuplexBnumber(srvs[i].getDuplexNum());
			rtnSrv.setEyeGroupId(srvs[i].getEyeGrpId());
			rtnSrv.setLob(LtsProfileConstant.LOB_LTS);
			rtnSrv.setPendingOrderId(srvs[i].getPendingOcid());
			rtnSrv.setServiceBoundary(srvs[i].getAddress() == null ? null : srvs[i].getAddress().getSrvBdry());
			rtnSrv.setServiceNo(srvs[i].getSrvNum());
			rtnSrv.setServiceStartDate(srvs[i].getPendingOcSrd());
			rtnSrv.setServiceStatus("Pending");
			rtnSrv.setServiceType(srvs[i].getSrvType());
			rtnSrv.setTariff(srvs[i].getTariff());
			
			OrderAccountDTO[] accts = srvs[i].getAccounts();
			List<AccountDTO> rtnAcctList = new ArrayList<AccountDTO>();
			AccountDTO rtnAcct = null;
			
			for (int j=0; accts!=null && j<accts.length;++j) {
				rtnAcct = new AccountDTO();
				rtnAcct.setAccountNum(accts[j].getAcctNum());
				rtnAcct.setAccountType(accts[j].getAcctType());
				rtnAcct.setChargeCat(accts[j].getChrgCatgCd());
				rtnAcctList.add(rtnAcct);
			}
			rtnSrv.setRelatedAccts(rtnAcctList);
			rtnSrvList.add(rtnSrv);
		}
		return rtnSrvList;
	}

	

	public AmAsmDTO getAmAsm(String systemId, String custNum) throws DaoException {		
		return this.customerProfileLtsService.getAmAsm(systemId, custNum);
	}

	public List<CampaignDTO> getCampaign(String systemId, String custNum, String desc) throws DaoException {
		return this.customerProfileLtsService.getCampaign(systemId, custNum, desc);
	}
		
	public List<LtsDataPrivacyDTO> getLtsDataPrivacy(String systemId, String custNum) throws DaoException {
		return this.customerProfileLtsService.getLtsDataPrivacy(systemId, custNum);
	}	
	
	public List<IddffpInfoDTO> getIddffpInfo(String pSystemId, String pServiceId) throws DaoException {
		return null;
	}
	
	public  List<OfferDetailCommitmentDTO> getCommitment(String pSystemId, String pServiceId, String effectiveInd) throws DaoException {
		return this.offerProfileLtsService.getCommitment(pSystemId, pServiceId, effectiveInd);
	}
	
	public  List<OfferDetailRecurringChargesDTO> getRecurringCharges(String pSystemId, String pServiceId, String effectiveInd) throws DaoException {
		return this.offerProfileLtsService.getRecurringCharges(pSystemId, pServiceId, effectiveInd);
	}

	public  List<OfferDetailDiscountDTO> getDiscount(String pSystemId, String pServiceId, String effectiveInd) throws DaoException {
		return this.offerProfileLtsService.getDiscount(pSystemId, pServiceId, effectiveInd);
	}

	public  List<OfferDetailIncentiveDTO> getIncentive(String pSystemId, String pServiceId, String effectiveInd) throws DaoException {
		return this.offerProfileLtsService.getIncentive(pSystemId, pServiceId, effectiveInd);
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}

	public CodeLkupCacheService getIdDocTypeLkupCacheService() {
		return idDocTypeLkupCacheService;
	}

	public void setIdDocTypeLkupCacheService(
			CodeLkupCacheService idDocTypeLkupCacheService) {
		this.idDocTypeLkupCacheService = idDocTypeLkupCacheService;
	}

	public CodeLkupCacheService getCustCatgLkupCacheService() {
		return custCatgLkupCacheService;
	}

	public void setCustCatgLkupCacheService(
			CodeLkupCacheService custCatgLkupCacheService) {
		this.custCatgLkupCacheService = custCatgLkupCacheService;
	}

	public CodeLkupCacheService getBillFreqLkupCacheService() {
		return billFreqLkupCacheService;
	}

	public void setBillFreqLkupCacheService(
			CodeLkupCacheService billFreqLkupCacheService) {
		this.billFreqLkupCacheService = billFreqLkupCacheService;
	}

	public CodeLkupCacheService getBillMediaLkupCacheService() {
		return billMediaLkupCacheService;
	}

	public void setBillMediaLkupCacheService(
			CodeLkupCacheService billMediaLkupCacheService) {
		this.billMediaLkupCacheService = billMediaLkupCacheService;
	}

	public CodeLkupCacheService getLangCodeLkupCacheService() {
		return langCodeLkupCacheService;
	}

	public void setLangCodeLkupCacheService(
			CodeLkupCacheService langCodeLkupCacheService) {
		this.langCodeLkupCacheService = langCodeLkupCacheService;
	}

	public CodeLkupCacheService getPayMethodLkupCacheService() {
		return payMethodLkupCacheService;
	}

	public void setPayMethodLkupCacheService(
			CodeLkupCacheService payMethodLkupCacheService) {
		this.payMethodLkupCacheService = payMethodLkupCacheService;
	}

	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}

	public BomOrderInfoService getBomOrderInfoService() {
		return bomOrderInfoService;
	}

	public void setBomOrderInfoService(BomOrderInfoService bomOrderInfoService) {
		this.bomOrderInfoService = bomOrderInfoService;
	}
	
}
