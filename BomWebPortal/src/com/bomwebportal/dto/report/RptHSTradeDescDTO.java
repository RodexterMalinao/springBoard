package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptHSTradeDescDTO extends ReportDTO {
	private static final long serialVersionUID = 6967185475131988315L;
	
	public static final String JASPER_TEMPLATE = "HSTradeDescForm";
	
	private String brand;
	private String model;
	private String placeOfManufacture;
	private String networkFrequency;
	private String keyFeatures;       
	private String operatingSystem;    
	private String internalMemory;	  
	private String storageType;	
	private String storageCapacity;
	private String display;     
	private String networkProtocol;
	private String cameraResolution;
	private String packagingList; 
	private String price;        
	private String additionalDeliveryCharge; 
	private String repairSrvProvider;
	private String repairSrvAddress;
	private String exchangePolicy; 
	private String warrantyHandset;
	private String warrantyAccessory;
	private String pisPath;
	
	
	private String agreementNum;
	
	private String companyChop;
	
	private Date appInDate;  
	private InputStream custSignature;  
	private Boolean showSignAndDate;  
	
	public RptHSTradeDescDTO() {
		super(JASPER_TEMPLATE);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPlaceOfManufacture() {
		return placeOfManufacture;
	}

	public void setPlaceOfManufacture(String placeOfManufacture) {
		this.placeOfManufacture = placeOfManufacture;
	}

	public String getNetworkFrequency() {
		return networkFrequency;
	}

	public void setNetworkFrequency(String networkFrequency) {
		this.networkFrequency = networkFrequency;
	}

	public String getKeyFeatures(){
		return keyFeatures;
	}

	public void setKeyFeatures(String keyFeatures){
		this.keyFeatures = keyFeatures;
	}

	public String getOperatingSystem(){
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem){
		this.operatingSystem = operatingSystem;
	}

	public String getInternalMemory(){
		return internalMemory;
	}

	public void setInternalMemory(String internalMemory){
		this.internalMemory = internalMemory;
	}
	
	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getStorageCapacity() {
		return storageCapacity;
	}

	public void setStorageCapacity(String storageCapacity) {
		this.storageCapacity = storageCapacity;
	}
	
	public String getDisplay(){
		return display;
	}

	public void setDisplay(String display){
		this.display = display;
	}

	public String getNetworkProtocol() {
		return networkProtocol;
	}

	public void setNetworkProtocol(String networkProtocol) {
		this.networkProtocol = networkProtocol;
	}

	public String getCameraResolution() {
		return cameraResolution;
	}

	public void setCameraResolution(String cameraResolution) {
		this.cameraResolution = cameraResolution;
	}
	
	public String getPackagingList(){
		return packagingList;
	}
	
	public void setPackagingList(String packagingList){
		this.packagingList = packagingList;
	}

	public String getPrice(){
		return price;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getAdditionalDeliveryCharge(){
		return additionalDeliveryCharge;
	}

	public void setAdditionalDeliveryCharge(String additionalDeliveryCharge){
		this.additionalDeliveryCharge = additionalDeliveryCharge;
	}

	public String getRepairSrvProvider() {
		return repairSrvProvider;
	}

	public void setRepairSrvProvider(String repairSrvProvider) {
		this.repairSrvProvider = repairSrvProvider;
	}

	public String getRepairSrvAddress() {
		return repairSrvAddress;
	}

	public void setRepairSrvAddress(String repairSrvAddress) {
		this.repairSrvAddress = repairSrvAddress;
	}
	
	public String getExchangePolicy(){
		return exchangePolicy;
	}

	public void setExchangePolicy(String exchangePolicy){
		this.exchangePolicy = exchangePolicy;
	}

	public String getWarrantyHandset() {
		return warrantyHandset;
	}

	public void setWarrantyHandset(String warrantyHandset) {
		this.warrantyHandset = warrantyHandset;
	}

	public String getWarrantyAccessory() {
		return warrantyAccessory;
	}

	public void setWarrantyAccessory(String warrantyAccessory) {
		this.warrantyAccessory = warrantyAccessory;
	}

	/**
	 * same as order id
	 * @return the agreementNum
	 */
	public String getAgreementNum() {
		return agreementNum;
	}

	/**
	 * same as order id
	 * @param agreementNum the agreementNum to set
	 */
	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}

	/**
	 * @return the companyChop
	 */
	public String getCompanyChop() {
		return companyChop;
	}

	/**
	 * @param companyChop the companyChop to set
	 */
	public void setCompanyChop(String companyChop) {
		this.companyChop = companyChop;
	}	
	
	public Date getAppInDate() {
		return appInDate;
	}
	
	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}
	
	public InputStream getCustSignature() {
		return custSignature;
	}
	
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	
	public Boolean getShowSignAndDate(){
		return showSignAndDate;
	}
	
	public void setShowSignAndDate(Boolean showSignAndDate){
		this.showSignAndDate = showSignAndDate;
	}

	public String getPisPath() {
		return pisPath;
	}

	public void setPisPath(String pisPath) {
		this.pisPath = pisPath;
	}
}