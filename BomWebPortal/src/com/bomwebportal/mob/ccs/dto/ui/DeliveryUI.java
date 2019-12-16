package com.bomwebportal.mob.ccs.dto.ui;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bomwebportal.mob.ccs.dto.ContactDTO;

public class DeliveryUI implements java.io.Serializable {

	private static final long serialVersionUID = -3311684335459264341L;

	ContactDTO thirdPartyContact;
	ContactDTO primaryContact;

	public DeliveryUI() {
		this.systemId = "MOB";
		this.thirdPartyContact = new ContactDTO();
		this.thirdPartyContact.setContactType("3C");
		this.primaryContact = new ContactDTO();
		this.primaryContact.setContactType("DC");
		
	}

	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	private String orderId;

	private String systemId;

	// D=delivery, P=pick up
	private String deliveryInd;

	private boolean urgentInd = false;

	private String lockInd;

	private Date deliveryDate;
	
	//address field
	private String address;// Single line address

	private String quickSearch;

	private String flat;

	private String floor;
	
	private String address1;
	private String address2;
	private String address3;

	private String serviceBoundaryNum;

	private String lotHouseInd;

	private String streetNum; // 20110530 b4 call houseLotNum

	private String lotNum;

	private String buildingName;

	private String streetName;

	private String streetCatgDesc;

	private String streetCatgCode;

	private String sectionDesc;

	private String sectionCode;

	private String districtDesc;

	private String districtCode;;

	private String areaDesc;

	private String areaCode;

	private String deliveryDateStr;

	private String deliveryCentre;

	private boolean custAddressFlag = false;// add by wilson
											// 20110302,false=quick search, true
											// self input
	private boolean custAddressFlag2 = false;

	private boolean unlinkSectionFlag = false;// add by wilson
												// 20110315,false=link , true
												// self unlink

	//private String otherContactPhone; // add by herbert 20110720

	private boolean recieveByThirdPartyInd = false;
	
	private boolean allowUrgentDelivery = true;

	private String deliveryTimeSlot;
	/**
	 * Date object that stores Service Request Date of MRTUI object
	 */
	private Date serviceDate;
	/**
	 * whether if it is a new number (MRTUI object)
	 */
	private boolean mnp;

	/**
	 * Payment method (PaymentUpFrontUI)
	 */
	private String paymentMethod;
	
	/**
	 * An object map key to DTO value
	 */
	private Map<String, Object> objectsMap;
	// add by Joyce 20120222
	private String location;
	//add by wilson, 20120524
	private Date actualDeliveryDate;
	
	private boolean byPassValidation=false;// add by wilson 20120301, for draft-pre-pend order
	/**
	 * for display only
	 */
	private String timeSlotFrom;
	/**
	 * for display only
	 */
	private String timeSlotTo;
	
	private String cvId;
	private String dmId;
	private boolean dummyDeliveryDateInd = false;
	
	/**
	 * @return the timeSlotFrom
	 */
	public String getTimeSlotFrom() {
		return timeSlotFrom;
	}

	/**
	 * @param timeSlotFrom the timeSlotFrom to set
	 */
	public void setTimeSlotFrom(String timeSlotFrom) {
		this.timeSlotFrom = timeSlotFrom;
	}

	/**
	 * @return the timeSlotTo
	 */
	public String getTimeSlotTo() {
		return timeSlotTo;
	}

	/**
	 * @param timeSlotTo the timeSlotTo to set
	 */
	public void setTimeSlotTo(String timeSlotTo) {
		this.timeSlotTo = timeSlotTo;
	}

	public boolean isByPassValidation() {
		return byPassValidation;
	}

	public void setByPassValidation(boolean byPassValidation) {
		this.byPassValidation = byPassValidation;
	}
	
	/**
	 * @return the objectsMap
	 */
	public Map<String, Object> getObjectsMap() {
		return objectsMap;
	}

	/**
	 * @param objectsMap the objectsMap to set
	 */
	public void setObjectsMap(Map<String, Object> objectsMap) {
		this.objectsMap = objectsMap;
	}

	/**
	 * Get specific DTO object value which map to certain key
	 * @param key
	 * @return DTO object
	 */
	public Object getValue(String key) {
		if (this.objectsMap == null || this.objectsMap.isEmpty()) {
			return null;
		} else {
			return this.objectsMap.get(key);
		}
	}
	/**
	 * Set specific DTO object value into map which match with a unique key
	 * @param key
	 * @param value DTO object
	 */
	public void setValue(String key, Object value) {
		if (this.objectsMap == null) {
			objectsMap = new HashMap<String, Object>();
		}
		
		objectsMap.put(key, value);
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public boolean isMnp() {
		return mnp;
	}

	public void setMnp(boolean mnp) {
		this.mnp = mnp;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getDeliveryCentre() {
		return deliveryCentre;
	}

	public void setDeliveryCentre(String deliveryCentre) {
		this.deliveryCentre = deliveryCentre;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}


	public String getAddress1(){
		return address1;
	}
	
	public void setAddress1(String address1){
		this.address1 = address1;
	}
	
	
	public String getAddress2(){
		return address2;
	}
	
	public void setAddress2(String address2){
		this.address2= address2;
	}
	
	
	public String getAddress3(){
		  return address3;
	}
	
	public void setAddress3(String address3){
		this.address3 = address3;
	}
	
	
	public String getFlat() {
		return flat;
	}
	
	public void setFlat(String flat) {
		if (flat != null)
			this.flat = flat.toUpperCase();
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		if (floor != null)
			this.floor = floor.toUpperCase();
	}

	public String getServiceBoundaryNum() {
		return serviceBoundaryNum;
	}

	public void setServiceBoundaryNum(String serviceBoundaryNum) {
		this.serviceBoundaryNum = serviceBoundaryNum.toUpperCase();
	}

	public String getLotHouseInd() {
		return lotHouseInd;
	}

	public void setLotHouseInd(String lotHouseInd) {
		if (lotHouseInd != null)
			this.lotHouseInd = lotHouseInd.toUpperCase();
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		if (streetNum != null) {
			this.streetNum = streetNum.toUpperCase();
		}
		if (streetNum != null && !"".equals(streetNum)) {
			this.lotHouseInd = "S";

		}
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		if (lotNum != null) {
			this.lotNum = lotNum.toUpperCase();
		}

		if (lotNum != null && !"".equals(lotNum.trim())) {// assign lotHouseInd
			this.lotHouseInd = "L";

		}

	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		if (buildingName != null)
			this.buildingName = buildingName.toUpperCase();
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		if (streetName != null)
			this.streetName = streetName.toUpperCase();

	}

	public String getStreetCatgDesc() {
		return streetCatgDesc;
	}

	public void setStreetCatgDesc(String streetCatgDesc) {
		if (streetCatgDesc != null)
			this.streetCatgDesc = streetCatgDesc.toUpperCase();
	}

	public String getStreetCatgCode() {
		return streetCatgCode;
	}

	public void setStreetCatgCode(String streetCatgCode) {
		if (streetCatgCode != null)
			this.streetCatgCode = streetCatgCode.toUpperCase();
	}

	public String getSectionDesc() {
		return sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		if (sectionDesc != null)
			this.sectionDesc = sectionDesc.toUpperCase();
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		if (sectionCode != null)
			this.sectionCode = sectionCode.toUpperCase();
	}

	public String getDistrictDesc() {
		return districtDesc;
	}

	public void setDistrictDesc(String districtDesc) {
		if (districtDesc != null)
			this.districtDesc = districtDesc.toUpperCase();
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		if (districtCode != null)
			this.districtCode = districtCode.toUpperCase();
	}

	public String getAreaDesc() {
		return areaDesc;
	}

	public void setAreaDesc(String areaDesc) {
		if (areaDesc != null)
			this.areaDesc = areaDesc.toUpperCase();
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		if (areaCode != null)
			this.areaCode = areaCode.toUpperCase();
	}

	public String getQuickSearch() {
		return quickSearch;
	}

	public void setQuickSearch(String quickSearch) {
		if (quickSearch != null)
			this.quickSearch = quickSearch.toUpperCase();
	}

	public void setOrderId(String orderId) {
		this.primaryContact.setOrderId(orderId);
		this.thirdPartyContact.setOrderId(orderId);
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getSingleLineAddress() {
		StringBuffer sb = new StringBuffer();
		if (this.custAddressFlag2){
			if (this.address1 != null && !"".equals(this.address1.trim())) {
				sb.append(this.address1);
				sb.append(" ");
			}
			if (this.address2 != null && !"".equals(this.address2.trim())) {
				sb.append(this.address2);
				sb.append(" ");
			}
			if (this.address3 != null && !"".equals(this.address3.trim())) {
				sb.append(this.address3);
				sb.append(" ");
			}

			if (this.districtDesc != null && !"".equals(this.districtDesc.trim())) {
				sb.append(this.districtDesc);
				sb.append(" ");
			}

			if (this.areaDesc != null && !"".equals(this.areaDesc.trim())) {
				sb.append(this.areaDesc);
				sb.append(" ");
			}
			return sb.toString();
			}
			else{
			    if (this.flat != null && !"".equals(this.flat.trim())) {
			    sb.append("Flat " + this.flat);
			    sb.append(" ");
		        }

		        if (this.floor != null && !"".equals(this.floor.trim())) {
				sb.append("Floor " + this.floor);
				sb.append(" ");
			}
	
			if (this.buildingName != null && !"".equals(this.buildingName.trim())) {
				sb.append(this.buildingName);
				sb.append(" ");
			}
	
			if (this.lotNum != null && !"".equals(this.lotNum.trim())) {
				sb.append(this.lotNum);
				sb.append(" ");
				
			} else if (this.streetNum != null && !"".equals(this.streetNum.trim())) {
				sb.append(this.streetNum);
				sb.append(" ");
			}
	
			if (this.streetName != null && !"".equals(this.streetName.trim())) {
				sb.append(this.streetName);
				sb.append(" ");
			}
			
			if (this.streetCatgDesc != null && !"".equals(this.streetCatgDesc.trim())) {
				sb.append(this.streetCatgDesc);
				sb.append(" ");
			}
	
			if (this.sectionDesc != null && !"".equals(this.sectionDesc.trim())) {
				sb.append(this.sectionDesc);
				sb.append(" ");
			}

			if (this.districtDesc != null && !"".equals(this.districtDesc.trim())) {
				sb.append(this.districtDesc);
				sb.append(" ");
			}
	
			if (this.areaDesc != null && !"".equals(this.areaDesc.trim())) {
				sb.append(this.areaDesc);
				sb.append(" ");
			}
	
			
			return sb.toString();
			}
		}

	public String getAddress() {
		this.address = getSingleLineAddress();
		return this.address;
	}

	public void setAddress(String address) {
		this.address = getSingleLineAddress();
	}

	public void setCustAddressFlag(boolean custAddressFlag) {
		this.custAddressFlag = custAddressFlag;
	}

	public boolean isCustAddressFlag() {
		return custAddressFlag;
	}
	
	public void setCustAddressFlag2(boolean custAddressFlag2) {
		this.custAddressFlag2 = custAddressFlag2;
	}

	public boolean isCustAddressFlag2() {
		return custAddressFlag2;
	}

	public void setUnlinkSectionFlag(boolean unlinkSectionFlag) {
		this.unlinkSectionFlag = unlinkSectionFlag;
	}

	public boolean isUnlinkSectionFlag() {
		return unlinkSectionFlag;
	}



	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDateStr(String deliveryDateStr) {
		this.deliveryDateStr = deliveryDateStr;
	}

	public String getDeliveryDateStr() {
		return deliveryDateStr;
	}

	public void setDeliveryInd(String deliveryInd) {
		this.deliveryInd = deliveryInd;
	}

	public String getDeliveryInd() {
		return deliveryInd;
	}

	public void setRecieveByThirdPartyInd(boolean recieveByThirdPartyInd) {
		this.recieveByThirdPartyInd = recieveByThirdPartyInd;
	}

	public boolean isRecieveByThirdPartyInd() {
		return recieveByThirdPartyInd;
	}

	public void setDeliveryTimeSlot(String deliveryTimeSlot) {
		this.deliveryTimeSlot = deliveryTimeSlot;
	}

	public String getDeliveryTimeSlot() {
		return deliveryTimeSlot;
	}

	public void setUrgentInd(boolean urgentInd) {
		this.urgentInd = urgentInd;
	}

	public boolean isUrgentInd() {
		return urgentInd;
	}

	public ContactDTO getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(ContactDTO primaryContact) {
		this.primaryContact = primaryContact;
	}

	public ContactDTO getThirdPartyContact() {
		return thirdPartyContact;
	}

	public void setThirdPartyContact(ContactDTO thirdPartyContact) {
		this.thirdPartyContact = thirdPartyContact;
	}

	public void setLockInd(String lockInd) {
		this.lockInd = lockInd;
	}

	public String getLockInd() {
		return lockInd;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public Date getActualDeliveryDate() {
		return actualDeliveryDate;
	}

	public void setActualDeliveryDate(Date actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}

	public boolean isAllowUrgentDelivery() {
		return allowUrgentDelivery;
	}

	public void setAllowUrgentDelivery(boolean allowUrgentDelivery) {
		this.allowUrgentDelivery = allowUrgentDelivery;
	}

	public String getCvId() {
		return cvId;
	}
	
	public void setCvId(String cvId) {
		this.cvId = cvId;
	}

	public String getDmId() {
		return dmId;
	}

	public void setDmId(String dmId) {
		this.dmId = dmId;
	}

	public boolean isDummyDeliveryDateInd() {
		return dummyDeliveryDateInd;
	}

	public void setDummyDeliveryDateInd(boolean dummyDeliveryDateInd) {
		this.dummyDeliveryDateInd = dummyDeliveryDateInd;
	}
	
}
