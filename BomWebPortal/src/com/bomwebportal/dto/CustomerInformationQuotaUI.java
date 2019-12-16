package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerInformationQuotaUI implements Serializable {

	private String title;
	private String familyName;
	private String givenName;
	private String docId;
	private String dateOfBirth;
	private String companyName;
	private String eligibility;
	private String creditStatus;
	private String creditStatusDesc;
	private int channelId;
	private boolean mrtThresholdOverflow;
	private int mrtThreshold;
	private String orderStatus;
	private List<CustomerInformationQuotaDTO> customerInformationQuotaDTOList;
	private List<CustomerInformationQuotaDTO> customerInformationQuotaOverLimitDTOList;
	private List<EligibilityDTO> eligibilityDTOList;
	private Exception creditStatusException;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEligibility() {
		eligibility = "";
		if (eligibilityDTOList != null && eligibilityDTOList.size() > 0) {
			String separator = " / ";
			for (EligibilityDTO dto : eligibilityDTOList) {
				eligibility += dto.getCustomerTierDesc() + separator;
			}
			if (eligibility != null) {
				eligibility = eligibility.substring(0, eligibility.length()
						- separator.length());
			}
		}

		return eligibility;
	}

	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}

	public int getChannelId() {
		return channelId;
	}

	public boolean isMrtThresholdOverflow() {

		setMrtThreshold(0);
		if (creditStatus != null && "3".equals(creditStatus)){			
			if(creditStatusDesc != null){
				try{
					int mrtThreshold = Integer.parseInt(creditStatusDesc.substring(creditStatusDesc.lastIndexOf("(")+1, creditStatusDesc.lastIndexOf(")")).trim());
					setMrtThreshold(mrtThreshold);
					if (mrtThreshold >= 5){						
						return true;
					}else{						
						return false;
					}
				}catch(NumberFormatException nfe){
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}


	public int getMrtThreshold() {
		return mrtThreshold;
	}

	public void setMrtThreshold(int mrtThreshold) {
		this.mrtThreshold = mrtThreshold;
	}

	public String getCreditStatus() {
		return creditStatus;		
	}

	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}

	public String getCreditStatusDesc() {
		return creditStatusDesc;
	}

	public void setCreditStatusDesc(String creditStatusDesc) {

		this.creditStatusDesc = creditStatusDesc;
		if (creditStatusDesc != null) {
			/*
			String colon = ":";
			int index = creditStatusDesc.lastIndexOf(colon);
			if (index != -1) {
				this.creditStatusDesc = creditStatusDesc.substring(index + 1);
			}
			*/
		}else{
			this.creditStatusDesc = "Error";
		}
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<CustomerInformationQuotaDTO> getCustomerInformationQuotaDTOList() {
		return customerInformationQuotaDTOList;
	}

	public void setCustomerInformationQuotaDTOList(
			List<CustomerInformationQuotaDTO> customerInformationQuotaDTOList) {
		this.customerInformationQuotaDTOList = customerInformationQuotaDTOList;
	}

	public List<CustomerInformationQuotaDTO> getCustomerInformationQuotaOverLimitDTOList() {

		if (customerInformationQuotaDTOList != null
				&& customerInformationQuotaDTOList.size() > 0) {
			for (CustomerInformationQuotaDTO ciq : customerInformationQuotaDTOList) {
				if (!(ciq.getQuotaCeiling() > ciq.getQuotaInUse())) {
					if (customerInformationQuotaOverLimitDTOList == null) {
						customerInformationQuotaOverLimitDTOList = new ArrayList<CustomerInformationQuotaDTO>();
					}
					customerInformationQuotaOverLimitDTOList.add(ciq);
				}
			}

		}
		return customerInformationQuotaOverLimitDTOList;

	}

	public List<EligibilityDTO> getEligibilityDTOList() {
		return eligibilityDTOList;
	}

	public void setEligibilityDTOList(List<EligibilityDTO> eligibilityDTOList) {
		this.eligibilityDTOList = eligibilityDTOList;
	}

	public Exception getCreditStatusException() {
		return creditStatusException;
	}

	public void setCreditStatusException(Exception creditStatusException) {
		this.creditStatusException = creditStatusException;
	}
}
