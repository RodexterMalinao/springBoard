package com.bomwebportal.lts.dto.order;

public class AmendPipbInfoDTO extends AmendCategoryLtsDTO {
	
	private static final long serialVersionUID = 9087457442521342286L;
	
	private String firstName;
	private String lastName;
	private String title;
	private CustomerDetailLtsDTO referenceOrdCust;
	
	private String flat;
	private String floor;
	private AddressDetailLtsDTO referenceOrdAddr;
	
	private PipbLtsDTO newPipbInfo;
	private PipbLtsDTO referencePipbInfo;
	
	private boolean uploadDocument;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public CustomerDetailLtsDTO getReferenceOrdCust() {
		return referenceOrdCust;
	}
	public void setReferenceOrdCust(CustomerDetailLtsDTO referenceOrdCust) {
		this.referenceOrdCust = referenceOrdCust;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public AddressDetailLtsDTO getReferenceOrdAddr() {
		return referenceOrdAddr;
	}
	public void setReferenceOrdAddr(AddressDetailLtsDTO referenceOrdAddr) {
		this.referenceOrdAddr = referenceOrdAddr;
	}
	public PipbLtsDTO getNewPipbInfo() {
		return newPipbInfo;
	}
	public void setNewPipbInfo(PipbLtsDTO newPipbInfo) {
		this.newPipbInfo = newPipbInfo;
	}
	public PipbLtsDTO getReferencePipbInfo() {
		return referencePipbInfo;
	}
	public void setReferencePipbInfo(PipbLtsDTO referencePipbInfo) {
		this.referencePipbInfo = referencePipbInfo;
	}
	public boolean isUploadDocument() {
		return uploadDocument;
	}
	public void setUploadDocument(boolean uploadDocument) {
		this.uploadDocument = uploadDocument;
	}
	
}
