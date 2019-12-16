package com.bomwebportal.lts.dto.acq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bomwebportal.lts.dto.BasketDetailDTO;

public class AcqBasketSelectionInfoDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4117914047437026010L;
	private List<BasketDetailDTO> hotBasketList;
	private List<BasketDetailDTO> eyeBasketList;
	private List<BasketDetailDTO> eyeOtherBasketList;
	private List<BasketDetailDTO> fixLinePremiumBasketList;
	private List<BasketDetailDTO> fixLineRebateBasketList;
	private List<BasketDetailDTO> fixLineRebateCouponBasketList;
	private List<BasketDetailDTO> fixLineOtherBasketList;
	private List<BasketDetailDTO> fixLineRebatePremiumBasketList;
	private List<BasketDetailDTO> pcdBundleBasketList;
	private List<BasketDetailDTO> delfreeBundleBasketList;
	
	private Map<String, BasketDetailDTO> allBasketMap;
	private boolean eyeCoverage;
	
	public List<BasketDetailDTO> getHotBasketList() {
		return hotBasketList;
	}
	public void setHotBasketList(List<BasketDetailDTO> hotBasketList) {
		this.hotBasketList = hotBasketList;
	}
	public List<BasketDetailDTO> getEyeBasketList() {
		return eyeBasketList;
	}
	public void setEyeBasketList(List<BasketDetailDTO> eyeBasketList) {
		this.eyeBasketList = eyeBasketList;
	}
	public List<BasketDetailDTO> getEyeOtherBasketList() {
		return eyeOtherBasketList;
	}
	public void setEyeOtherBasketList(List<BasketDetailDTO> eyeOtherBasketList) {
		this.eyeOtherBasketList = eyeOtherBasketList;
	}
	public List<BasketDetailDTO> getFixLinePremiumBasketList() {
		return fixLinePremiumBasketList;
	}
	public void setFixLinePremiumBasketList(
			List<BasketDetailDTO> fixLinePremiumBasketList) {
		this.fixLinePremiumBasketList = fixLinePremiumBasketList;
	}
	public List<BasketDetailDTO> getFixLineRebateBasketList() {
		return fixLineRebateBasketList;
	}
	public void setFixLineRebateBasketList(
			List<BasketDetailDTO> fixLineRebateBasketList) {
		this.fixLineRebateBasketList = fixLineRebateBasketList;
	}
	public List<BasketDetailDTO> getFixLineRebateCouponBasketList() {
		return fixLineRebateCouponBasketList;
	}
	public void setFixLineRebateCouponBasketList(
			List<BasketDetailDTO> fixLineRebateCouponBasketList) {
		this.fixLineRebateCouponBasketList = fixLineRebateCouponBasketList;
	}
	public List<BasketDetailDTO> getFixLineOtherBasketList() {
		return fixLineOtherBasketList;
	}
	public void setFixLineOtherBasketList(
			List<BasketDetailDTO> fixLineOtherBasketList) {
		this.fixLineOtherBasketList = fixLineOtherBasketList;
	}	
	public List<BasketDetailDTO> getPcdBundleBasketList() {
		return pcdBundleBasketList;
	}
	public void setPcdBundleBasketList(List<BasketDetailDTO> pcdBundleBasketList) {
		this.pcdBundleBasketList = pcdBundleBasketList;
	}
	public List<BasketDetailDTO> getDelfreeBundleBasketList() {
		return delfreeBundleBasketList;
	}
	public void setDelfreeBundleBasketList(
			List<BasketDetailDTO> delfreeBundleBasketList) {
		this.delfreeBundleBasketList = delfreeBundleBasketList;
	}
	public List<BasketDetailDTO> getAllBasketList() {
		if(this.allBasketMap == null || this.allBasketMap.size() == 0){
			return null;
		}
		return new ArrayList<BasketDetailDTO>(this.allBasketMap.values());
	}
	public Map<String, BasketDetailDTO> getAllBasketMap() {
		return allBasketMap;
	}
	public void setAllBasketMap(Map<String, BasketDetailDTO> allBasketMap) {
		this.allBasketMap = allBasketMap;
	}
	public boolean isEyeCoverage() {
		return eyeCoverage;
	}
	public void setEyeCoverage(boolean eyeCoverage) {
		this.eyeCoverage = eyeCoverage;
	}
	public List<BasketDetailDTO> getFixLineRebatePremiumBasketList() {
		return fixLineRebatePremiumBasketList;
	}
	public void setFixLineRebatePremiumBasketList(
			List<BasketDetailDTO> fixLineRebatePremiumBasketList) {
		this.fixLineRebatePremiumBasketList = fixLineRebatePremiumBasketList;
	}
}
