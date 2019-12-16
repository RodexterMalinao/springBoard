package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.ChannalBasketDTO;
import com.bomwebportal.dto.EligibilityDTO;

public interface ServiceSelectionService {
	public List<String[]> getSelectList(String locale, String type);
	/**
	 * find list of call list based on channel 
	 * @param locale
	 * @param channel
	 * @return
	 */
	public List<String[]> getCallList(String appDate, String channelCode);
	/**
	 * find drop down list of rate plan
	 * @param channelCd Channel Code
	 * @param centreCd Centre Code
	 * @param teamCd Team Code
	 * @param jobList Job List
	 * @return
	 */
	public List<String[]> getRatePlan2(String jobList, String appDate);
	/**
	 * find eligibility customer tier list
	 * @param locale
	 * @param eligibilityDto List of eligibility data object
	 * @return
	 */
	public List<String[]> getEligibilityCustomerTierList (String locale, List<EligibilityDTO> eligibilityDto, int channelId);
	public ChannalBasketDTO getChannelBasketStatus(String channelId, String basketId, String appDate);
	public List<String[]> getEligibilityCustomerTierListByPeriorty(String locale,
			List<EligibilityDTO> eligibilityDto, int channelId);
}
