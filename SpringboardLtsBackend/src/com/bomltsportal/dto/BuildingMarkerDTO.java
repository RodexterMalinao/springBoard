package com.bomltsportal.dto;


import org.apache.commons.lang.StringUtils;


public class BuildingMarkerDTO extends MarkerDTO implements Comparable<BuildingMarkerDTO>{
			
	private static final long serialVersionUID = 7009536449115967334L;	
	
	private String indexKey;
	private String bldgNameEn;
	private String bldgNameCh;
	private String streetNum;
	private String streetNameEn;
	private String streetNameCh;
	private String sectDescEn;
	private String sectDescCh;
	private String distDescEn;
	private String distDescCh;
	private String areaDescEn;
	private String areaDescCh;
	private String buildXy;
	private String siteGroup;
	private String sfBldgRes;
	private String sfBldgBus;
	private String sectCd;
	private String distrCd;
	private String areaCd;	
	private String resBasicBw;
	private String resFttbBw;
	private String resFtthBw;
	private String resTvSd;
	private String resTvHd;
	private String busBasicBw;
	private String busFttbBw;
	private String busFtthBw;
	private String busTvSd;
	private String busTvHd;
	private String isRm;
	private String isPremier;
	private String housingAddrEn;
	private String housingAddrCh;
	private String extNameEn;
	private String extNameCh;
	private String isPh;
	private String isHos;
	private String isCleanedVillage;
	
	public String getIsCleanedVillage() { 
		return isCleanedVillage;
	}
	public void setIsCleanedVillage(String isCleanedVillage) { 
		this.isCleanedVillage = isCleanedVillage;
	}
	public String getisPh() {
			return isPh;
	}
	public void setisPh(String isPh) {
		this.isPh = isPh;
	}
	public String getisHos() {
			return isHos;
	}
	public void setisHos(String isHos) {
		this.isHos = isHos;
	}
	
	public String getBldgNameEn() {
		return bldgNameEn;
	}
	public void setBldgNameEn(String bldgNameEn) {
		this.bldgNameEn = bldgNameEn;
	}
	public String getBldgNameCh() {
		return bldgNameCh;
	}
	public void setBldgNameCh(String bldgNameCh) {
		this.bldgNameCh = bldgNameCh;
	}
	public String getStreetNum() {
		return streetNum;
	}
	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}
	public String getStreetNameEn() {
		return streetNameEn;
	}
	public void setStreetNameEn(String streetNameEn) {
		this.streetNameEn = streetNameEn;
	}
	public String getStreetNameCh() {
		return streetNameCh;
	}
	public void setStreetNameCh(String streetNameCh) {
		this.streetNameCh = streetNameCh;
	}
	public String getSectDescEn() {
		return sectDescEn;
	}
	public void setSectDescEn(String sectDescEn) {
		this.sectDescEn = sectDescEn;
	}
	public String getSectDescCh() {
		return sectDescCh;
	}
	public void setSectDescCh(String sectDescCh) {
		this.sectDescCh = sectDescCh;
	}
	public String getDistDescEn() {
		return distDescEn;
	}
	public void setDistDescEn(String distDescEn) {
		this.distDescEn = distDescEn;
	}
	public String getDistDescCh() {
		return distDescCh;
	}
	public void setDistDescCh(String distDescCh) {
		this.distDescCh = distDescCh;
	}
	public String getAreaDescEn() {
		return areaDescEn;
	}
	public void setAreaDescEn(String areaDescEn) {
		this.areaDescEn = areaDescEn;
	}
	public String getAreaDescCh() {
		return areaDescCh;
	}
	public void setAreaDescCh(String areaDescCh) {
		this.areaDescCh = areaDescCh;
	}
	public String getBuildXy() {
		return buildXy;
	}
	public void setBuildXy(String buildXy) {
		this.buildXy = buildXy;
	}
	public String getSiteGroup() {
		return siteGroup;
	}
	public void setSiteGroup(String siteGroup) {
		this.siteGroup = siteGroup;
	}
	public String getSfBldgRes() {
		return sfBldgRes;
	}
	public void setSfBldgRes(String sfBldgRes) {
		this.sfBldgRes = sfBldgRes;
	}
	public String getSfBldgBus() {
		return sfBldgBus;
	}
	public void setSfBldgBus(String sfBldgBus) {
		this.sfBldgBus = sfBldgBus;
	}
	public String getSectCd() {
		return sectCd;
	}
	public void setSectCd(String sectCd) {
		this.sectCd = sectCd;
	}
	public String getDistrCd() {
		return distrCd;
	}
	public void setDistrCd(String distrCd) {
		this.distrCd = distrCd;
	}
	public String getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	public String getResBasicBw() {
		return resBasicBw;
	}
	public void setResBasicBw(String resBasicBw) {
		this.resBasicBw = resBasicBw;
	}
	public String getResFttbBw() {
		return resFttbBw;
	}
	public void setResFttbBw(String resFttbBw) {
		this.resFttbBw = resFttbBw;
	}
	public String getResFtthBw() {
		return resFtthBw;
	}
	public void setResFtthBw(String resFtthBw) {
		this.resFtthBw = resFtthBw;
	}
	public String getResTvSd() {
		return resTvSd;
	}
	public void setResTvSd(String resTvSd) {
		this.resTvSd = resTvSd;
	}
	public String getResTvHd() {
		return resTvHd;
	}
	public void setResTvHd(String resTvHd) {
		this.resTvHd = resTvHd;
	}
	public String getBusBasicBw() {
		return busBasicBw;
	}
	public void setBusBasicBw(String busBasicBw) {
		this.busBasicBw = busBasicBw;
	}
	public String getBusFttbBw() {
		return busFttbBw;
	}
	public void setBusFttbBw(String busFttbBw) {
		this.busFttbBw = busFttbBw;
	}
	public String getBusFtthBw() {
		return busFtthBw;
	}
	public void setBusFtthBw(String busFtthBw) {
		this.busFtthBw = busFtthBw;
	}
	public String getBusTvSd() {
		return busTvSd;
	}
	public void setBusTvSd(String busTvSd) {
		this.busTvSd = busTvSd;
	}
	public String getBusTvHd() {
		return busTvHd;
	}
	public void setBusTvHd(String busTvHd) {
		this.busTvHd = busTvHd;
	}
	public String getIsRm() {
		return isRm;
	}
	public void setIsRm(String isRm) {
		this.isRm = isRm;
	}
	public String getIsPremier() {
		return isPremier;
	}
	public void setIsPremier(String isPremier) {
		this.isPremier = isPremier;
	}
	public String getHousingAddrEn() {
		return housingAddrEn;
	}
	public void setHousingAddrEn(String housingAddrEn) {
		this.housingAddrEn = housingAddrEn;
	}
	public String getHousingAddrCh() {
		return housingAddrCh;
	}
	public void setHousingAddrCh(String housingAddrCh) {
		this.housingAddrCh = housingAddrCh;
	}
	public String getIndexKey() {
		return indexKey;
	}
	public void setIndexKey(String indexKey) {
		this.indexKey = indexKey;
	}
	
	public String getExtNameEn() {
		return extNameEn;
	}
	public void setExtNameEn(String extNameEn) {
		this.extNameEn = extNameEn;
	}
	public String getExtNameCh() {
		return extNameCh;
	}
	public void setExtNameCh(String extNameCh) {
		this.extNameCh = extNameCh;
	}
	public String getIsPonOnly(){
		if (this.getResFtthBw()!=null && !this.getResFtthBw().equals(""))
			return "Y";
		else
			return "N";
	}

	public String getAddressEn(){
		String address="";
		address = this.housingAddrEn +", ";
		if(this.sectDescEn != null && !this.sectDescEn.equals(""))
			address += this.sectDescEn +", ";
		if(this.distDescEn != null && !this.distDescEn.equals(""))
			address += this.distDescEn +", ";
		if(this.areaDescEn != null && !this.areaDescEn.equals(""))
			address += this.areaDescEn +", ";
		if (address.length()>0)
			address = address.substring(0, address.lastIndexOf(","));
		return address;
				
	}
	
	public String getAddressCh(){
		String address="";
		if(this.areaDescCh != null && !this.areaDescCh.equals(""))
			address += this.areaDescCh;
		if(this.distDescCh != null && !this.distDescCh.equals(""))
			address += this.distDescCh ;
		if(this.sectDescCh != null && !this.sectDescCh.equals(""))
			address += this.sectDescCh ;
		address += this.housingAddrCh ;
		return address;
	}
	
	public String getBusFiberInd(){
		if(busFttbBw != null || busFtthBw != null){
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String getDisplayLabel(){
		String label = "";
		if (StringUtils.isNotBlank(housingAddrEn)){
			label += housingAddrEn +", ";
		}
		if (StringUtils.isNotBlank(sectDescEn)){
			label += sectDescEn +", ";
		}
		if (StringUtils.isNotBlank(distDescEn)){
			label += distDescEn +", ";
		}
		if (StringUtils.isNotBlank(areaDescEn)){
			label += areaDescEn +", ";
		}
		label = label.substring(0, label.lastIndexOf(","));
		return label;	
	}
	
	/* Overload compareTo method */
	public int compareTo(BuildingMarkerDTO obj){		
		return this.getDisplayLabel().compareToIgnoreCase(obj.getDisplayLabel());
	}		
	
}

