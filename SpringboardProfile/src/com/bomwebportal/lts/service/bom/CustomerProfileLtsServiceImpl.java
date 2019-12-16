package com.bomwebportal.lts.service.bom;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.CustomerProfileLtsDAO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerContactProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerRemarkProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.RecontractRemarkDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.util.LtsProfileConstant;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.springboard.svc.server.dto.AmAsmDTO;
import com.pccw.springboard.svc.server.dto.CampaignDTO;
import com.pccw.springboard.svc.server.dto.lts.LtsDataPrivacyDTO;

public class CustomerProfileLtsServiceImpl implements CustomerProfileLtsService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final int REMARK_LENGTH = 58;
	
	private CustomerProfileLtsDAO customerProfileLtsDao;
	private CodeLkupCacheService wipMessageCodeLkupCacheService;
	private ImsProfileService imsProfileService;
	
	
	public CustomerProfileLtsDAO getCustomerProfileLtsDao() {
		return customerProfileLtsDao;
	}

	public void setCustomerProfileLtsDao(CustomerProfileLtsDAO customerProfileLtsDao) {
		this.customerProfileLtsDao = customerProfileLtsDao;
	}

	public CodeLkupCacheService getWipMessageCodeLkupCacheService() {
		return this.wipMessageCodeLkupCacheService;
	}

	public void setWipMessageCodeLkupCacheService(
			CodeLkupCacheService pWipMessageCodeLkupCacheService) {
		this.wipMessageCodeLkupCacheService = pWipMessageCodeLkupCacheService;
	}

	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public int getMaxLtsTenure(String pBomCustNum, String pUnit, String pFloor, String pSrvBdry) {
		
		try {
			return this.customerProfileLtsDao.getMaxLtsTenure(pBomCustNum, pUnit, pFloor, pSrvBdry);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getMaxLtsTenure()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int getMaxImsTenure(String pBomCustNum, String pUnit, String pFloor, String pSrvBdry) {
		
		TenureDTO[] tenures = this.imsProfileService.getImsTenureByAddress(pUnit, pFloor, pSrvBdry);
		int maxTenure = 0;
		
		for (int i=0; tenures!=null && i<tenures.length; ++i) {
			if (StringUtils.equals(tenures[i].getCustNum(), pBomCustNum) && tenures[i].getTenure() > maxTenure) {
				maxTenure = tenures[i].getTenure();
			}
		}
		return maxTenure;
	}

	public CustomerRemarkProfileLtsDTO[] getCustomerRemark(CustomerDetailProfileLtsDTO pCustomer, String pSystemId) {

		try {
			CustomerRemarkProfileLtsDTO[] remark = this.customerProfileLtsDao.getCustomerRemark(pCustomer.getCustNum(), pSystemId, pCustomer);
			StringBuilder remarks = null;
			
			for (int i = 0; i < remark.length && remark!=null; i++) {
				remarks = new StringBuilder();
				
				if (StringUtils.isNotEmpty(remark[i].getRemarks()) && remark[i].getRemarks().length() > REMARK_LENGTH) {
					int firstIndex=0;
					int lastIndex=REMARK_LENGTH;
					
					for (int j = 0; j <= remark[i].getRemarks().length(); j = firstIndex) {
						if (StringUtils.substring(remark[i].getRemarks(), firstIndex, lastIndex).lastIndexOf(" ") == -1){
					        remarks.append(StringUtils.substring(remark[i].getRemarks(), firstIndex, lastIndex) + " ");
							firstIndex = lastIndex;
							lastIndex = firstIndex + REMARK_LENGTH;
							if (lastIndex > remark[i].getRemarks().length()) {
								lastIndex = remark[i].getRemarks().length() + 1;
							}
						} else {
							remarks.append(StringUtils.substring(remark[i].getRemarks(), firstIndex, StringUtils.substring(remark[i].getRemarks(), firstIndex, lastIndex).lastIndexOf(" ")+firstIndex)+" ");
							firstIndex=StringUtils.substring(remark[i].getRemarks(), firstIndex, lastIndex).lastIndexOf(" ") + firstIndex + 1;
							lastIndex = firstIndex + REMARK_LENGTH;
							if (lastIndex > remark[i].getRemarks().length()) {
								lastIndex = remark[i].getRemarks().length() + 1;
							}
						}
					}
					remark[i].setRemarks(remarks.length() > 0 ? StringUtils.replace(remarks.toString(), "\n", "<br>") : null);
				}
			}
			return remark;
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getCustomerRemark()", de); 
			throw new AppRuntimeException(de);
		}
	}
	
	public String getWipMessage(CustomerDetailProfileLtsDTO pCustomer) {
		
		if (StringUtils.isEmpty(pCustomer.getWipInd())) {
			return null;
		}
		pCustomer.setWipMessage((String)this.wipMessageCodeLkupCacheService.get(pCustomer.getWipInd()));
		return pCustomer.getWipMessage();
	}
	
	public CustomerRemarkProfileLtsDTO[] getCustomerSpecialRemark(CustomerDetailProfileLtsDTO pCustomer) {
		
		try {
			return customerProfileLtsDao.getCustomerSpecialRemark(pCustomer.getCustNum(), LtsProfileConstant.SYSTEM_ID_DRG, pCustomer);
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getCustomerSpecialRemark()", de); 
			throw new AppRuntimeException(de);
		}
	}
	
	public CustomerRemarkProfileLtsDTO[] getCustomerSpecialRemark(String pCustNum, String pSystemId) {
		
		try {
			return customerProfileLtsDao.getCustomerSpecialRemark(pCustNum, pSystemId, null);
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getCustomerSpecialRemark()", de); 
			throw new AppRuntimeException(de);
		}
	}
	
	public AccountDetailProfileLtsDTO[] getAcctCustByDoc(String pDocType, String pDocId, String sysId) {
		try {
			return this.customerProfileLtsDao.getAcctCustByDoc(pDocType, pDocId, sysId);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getCustAcctByDoc()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public CustomerDetailProfileLtsDTO getCustByDoc(String pDocType, String pDocId, String sysId) {
		try {
			return this.customerProfileLtsDao.getCustByDoc(pDocType, pDocId, sysId);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getCustByDoc()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public CustomerDetailProfileLtsDTO getCustByCustNum(String pCustNum, String pSystemId) {
		try {
			return this.customerProfileLtsDao.getCustByCustNum(pCustNum, pSystemId);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getCustByCustNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public AccountDetailProfileLtsDTO getAccountbyAcctNum(String pAcctNum, String pSystemId) {
		try {
			return this.customerProfileLtsDao.getAccountbyAcctNum(pAcctNum, pSystemId);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getAccountbyAcctNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public CustomerContactProfileLtsDTO[] getCustContactInfo(String pCustNum, String pSystemId) {
		try {
			return this.customerProfileLtsDao.getCustContactInfo(pCustNum, pSystemId);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getCustContactInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public CustomerRemarkProfileLtsDTO[] getWipRemark(String pCustNum, String pSystemId) {
		try {
			return this.customerProfileLtsDao.getWipRemark(pCustNum, pSystemId);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getWipRemark()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public RecontractRemarkDTO[] getRecontractRemark(String pCustNum, String pSystemId) {
		try {
			return this.customerProfileLtsDao.getRecontractRemark(pCustNum, pSystemId);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getRecontractRemark()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public AmAsmDTO getAmAsm(String systemId, String custNum) {
		try {
			return this.customerProfileLtsDao.getAmAsm(systemId, custNum);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getAmAsm()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<CampaignDTO> getCampaign(String systemId, String custNum, String desc) {
		try {
			return this.customerProfileLtsDao.getCampaign(systemId, custNum, desc);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getCampaign()", de);
			throw new AppRuntimeException(de);
		}		
	}
	
	public List<CampaignDTO> getCampaign(String systemId, String custNum, String serviceNum, String campaignLob) {
		try {
			return this.customerProfileLtsDao.getCampaign(systemId, custNum, serviceNum, campaignLob);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getCampaign()", de);
			throw new AppRuntimeException(de);
		}	
	}
	
	public List<LtsDataPrivacyDTO> getLtsDataPrivacy(String systemId, String custNum) {
		try {
			return this.customerProfileLtsDao.getLtsDataPrivacy(systemId, custNum);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getLtsDataPrivacy()", de);
			throw new AppRuntimeException(de);
		}		
	}
	
	public String getBomDummyCustNum() {
		try {
			String seq = this.customerProfileLtsDao.getBomDummyCustNumSeq();
			return LtsProfileConstant.DUMMY_CUST_NUM_PREFIX + StringUtils.leftPad(seq, 7, "0");
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getDummyCustNum()", de);
			throw new AppRuntimeException(de);
		}		
	}
	
	public String getBomDummyAcctNum() {
		try {
			String seq = this.customerProfileLtsDao.getBomDummyAcctNumSeq();
			return LtsProfileConstant.DUMMY_ACCT_NUM_PREFIX + StringUtils.leftPad(seq, 13, "0");
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getDummyAcctNum()", de);
			throw new AppRuntimeException(de);
		}		
	}
	
}
