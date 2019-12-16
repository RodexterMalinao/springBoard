package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerBasicInfoDTO;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.CustomerInformationQuotaDAO;
import com.bomwebportal.dao.EligibilityDAO;
import com.bomwebportal.dto.CustomerInformationQuotaDTO;
import com.bomwebportal.dto.CustomerInformationQuotaUI;
import com.bomwebportal.dto.EligibilityDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.wsclient.CustCreditCheckClient;
import com.bomwebportal.wsclient.CustCreditCheckClient.CreditCheckResult;

import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultDetailsV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultStatusDescTextV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultStatusTextV10CT;

@Transactional(readOnly = true)
public class CustomerInformationQuotaServiceImpl implements
		CustomerInformationQuotaService {

	protected final Log logger = LogFactory.getLog(getClass());

	private CustomerInformationQuotaDAO customerInformationQuotaDAO;
	private EligibilityDAO eligibilityDAO;
	private CustCreditCheckClient custCreditCheckClient;

	public CustomerInformationQuotaDAO getCustomerInformationQuotaDAO() {
		return customerInformationQuotaDAO;
	}

	public void setCustomerInformationQuotaDAO(
			CustomerInformationQuotaDAO customerInformationQuotaDAO) {
		this.customerInformationQuotaDAO = customerInformationQuotaDAO;
	}

	public EligibilityDAO getEligibilityDAO() {
		return eligibilityDAO;
	}

	public void setEligibilityDAO(EligibilityDAO eligibilityDAO) {
		this.eligibilityDAO = eligibilityDAO;
	}

	public CustCreditCheckClient getCustCreditCheckClient() {
		return custCreditCheckClient;
	}

	public void setCustCreditCheckClient(
			CustCreditCheckClient custCreditCheckClient) {
		this.custCreditCheckClient = custCreditCheckClient;
	}

	public List<CustomerInformationQuotaDTO> getCustomerInformationQuotaDTOList(
			String idDocType, String idDocNum) {
		// TODO Auto-generated method stub
		try {
			return customerInformationQuotaDAO
					.getCustomerInformationQuotaDTOList(idDocType, idDocNum,
							null);

		} catch (DAOException de) {
			logger.error(
					"Exception caught in getCustomerInformationQuotaDTOList()",
					de);
			throw new AppRuntimeException(de);
		}
	}

	public List<CustomerInformationQuotaDTO> getCustomerInformationQuotaOverLimitDTOList(
			String idDocType, String idDocNum, String orderId) {
		// TODO Auto-generated method stub
		List<CustomerInformationQuotaDTO> customerInformationQuotaOverLimitDTOList = null;
		try {
			List<CustomerInformationQuotaDTO> ciqList = customerInformationQuotaDAO
					.getCustomerInformationQuotaDTOList(idDocType, idDocNum,
							orderId);
			if (ciqList != null && ciqList.size() > 0) {
				for (CustomerInformationQuotaDTO ciq : ciqList) {
					if (!(ciq.getQuotaCeiling() > ciq.getQuotaInUse())) {
						if (customerInformationQuotaOverLimitDTOList == null) {
							customerInformationQuotaOverLimitDTOList = new ArrayList<CustomerInformationQuotaDTO>();
						}
						customerInformationQuotaOverLimitDTOList.add(ciq);
					}
				}

			}
			
			return customerInformationQuotaOverLimitDTOList;

		} catch (DAOException de) {
			logger.error(
					"Exception caught in getCustomerInformationQuotaOverLimitDTOList() @ CustomerInformationQuotaServiceImpl",
					de);
			throw new AppRuntimeException(de);
		}
		
	}

	public List<EligibilityDTO> getEligibilityDTOList(String idDocType,
			String idDocNum) {
		// TODO Auto-generated method stub
		try {
			return eligibilityDAO.getEligibilityDTOList(idDocType, idDocNum);

		} catch (DAOException de) {
			logger.error("Exception caught in getEligibilityDTOList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<EligibilityDTO> getEligibilityDTOWithDefaultValuesList(
			String idDocType, String idDocNum) {
		// Default value LOB

		List<EligibilityDTO> defaultList = new ArrayList<EligibilityDTO>();

		

		List<EligibilityDTO> eligibilityDTOList = getEligibilityDTOList(
				idDocType, idDocNum);
		
		if (eligibilityDTOList != null && eligibilityDTOList.size() >0){// modify by wilson, 20120402
			EligibilityDTO lob = new EligibilityDTO();
			lob.setIdDocType(idDocType);
			lob.setIdDocNum(idDocNum);
			lob.setCustomerTierDesc("LOB");
			lob.setCustomerTierId("18");
			defaultList.add(lob);
		}

		for (EligibilityDTO dto : eligibilityDTOList) {
			defaultList.add(dto);
		}
		return defaultList;
	}

	public CustomerInformationQuotaUI getCustomerInformationQuotaUI(CustomerBasicInfoDTO customer) {
		try {
			CustomerInformationQuotaUI ui = new CustomerInformationQuotaUI();
			ui.setTitle(customer.getTitle());
			ui.setFamilyName(customer.getLastName());
			ui.setGivenName(customer.getFirstName());
			ui.setDocId(customer.getIdDocNum());
			ui.setDateOfBirth(customer.getDob());
			ui.setCompanyName(customer.getCompanyName());

			try {
				CreditCheckResult ccr = custCreditCheckClient.checkCustomer(customer.getIdDocType(), customer.getIdDocNum());
				if (ccr != null) {
					ui.setCreditStatus(ccr.getStatus());
					ui.setCreditStatusDesc(ccr.getDescription());
				}
				/*
				QueryResultDetailsV10CT result = custCreditCheckClient.checkCustomerCredit(customer.getIdDocType(), customer.getIdDocNum());
				QueryResultStatusTextV10CT creditStatus = null;
				QueryResultStatusDescTextV10CT creditStatusDesc = null;
				if (result != null) {
					creditStatus = result.getStatus();
					creditStatusDesc = result.getStatusDesc();
				}
				// set null can help to display as "error" text
				ui.setCreditStatus(creditStatus == null ? null : creditStatus.toString());
				ui.setCreditStatusDesc(creditStatusDesc == null ? null : creditStatusDesc.toString());
				*/
			} catch (Exception e) {
				// no need to throw exception on calling remote checkCredit service. can bypass on failure
				logger.error("Exception caught in calling checkCustomerCredit()", e);
				ui.setCreditStatusException(e);
			}
			
			ui.setCustomerInformationQuotaDTOList(getCustomerInformationQuotaDTOList(customer.getIdDocType(), customer.getIdDocNum()));
			ui.setEligibilityDTOList(getEligibilityDTOWithDefaultValuesList(customer.getIdDocType(), customer.getIdDocNum()));
			
			return ui;
		} catch (Exception e) {
			logger.error("Exception caught in getCustomerInformationQuotaUI()", e);
			throw new AppRuntimeException(e);
		}
	}

}
