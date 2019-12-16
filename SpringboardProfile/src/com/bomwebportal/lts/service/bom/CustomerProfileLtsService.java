package com.bomwebportal.lts.service.bom;

import java.util.List;

import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerContactProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerRemarkProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.RecontractRemarkDTO;
import com.pccw.springboard.svc.server.dto.AmAsmDTO;
import com.pccw.springboard.svc.server.dto.CampaignDTO;
import com.pccw.springboard.svc.server.dto.lts.LtsDataPrivacyDTO;


public interface CustomerProfileLtsService {

	public int getMaxLtsTenure(String pBomCustNum, String pUnit, String pFloor, String pSrvBdry);
	public int getMaxImsTenure(String pBomCustNum, String pUnit, String pFloor, String pSrvBdry);
	public CustomerRemarkProfileLtsDTO[] getCustomerRemark(CustomerDetailProfileLtsDTO pCustomer, String pSystemId);
	public CustomerRemarkProfileLtsDTO[] getCustomerSpecialRemark(CustomerDetailProfileLtsDTO pCustomer);
	public CustomerRemarkProfileLtsDTO[] getCustomerSpecialRemark(String pCustNum, String pSystemId);
	public String getWipMessage(CustomerDetailProfileLtsDTO pCustomer);
	public AccountDetailProfileLtsDTO[] getAcctCustByDoc(String pDocType, String pDocId, String sysId);
	public CustomerDetailProfileLtsDTO getCustByDoc(String pDocType, String pDocId, String sysId);
	public CustomerDetailProfileLtsDTO getCustByCustNum(String pCustNum, String pSystemId);
	public AccountDetailProfileLtsDTO getAccountbyAcctNum(String pAcctNum, String pSystemId);
	public CustomerContactProfileLtsDTO[] getCustContactInfo(String pCustNum, String pSystemId);
	public CustomerRemarkProfileLtsDTO[] getWipRemark(String pCustNum, String pSystemId);
	public RecontractRemarkDTO[] getRecontractRemark(String pCustNum, String pSystemId);	
	public AmAsmDTO getAmAsm(String systemId, String custNum);	
	public List<CampaignDTO> getCampaign(String systemId, String custNum, String desc);	
	public List<LtsDataPrivacyDTO> getLtsDataPrivacy(String systemId, String custNum);
	public List<CampaignDTO> getCampaign(String systemId, String custNum, String serviceNum, String campaignLob);
	public String getBomDummyCustNum();
	public String getBomDummyAcctNum();
}
