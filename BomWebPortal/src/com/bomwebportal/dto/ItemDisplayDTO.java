package com.bomwebportal.dto;
import java.io.Serializable;
import java.util.List;

public class ItemDisplayDTO implements Serializable{

	private static final long serialVersionUID = 7457229651730615334L;
	
	private int itemId;
	private String locale;
	private String description;
	private String displayType;
	private String html;
	//add eliot 20110714
	private String html2;
	private String imagePath;
	
	private List<ItemDisplayDTO> itemDisplayList;//for display
	
	private String formAction; //for form controller SEARCH, INSERT, UPDATE, DELETE
	private String formMessage;//for form controller return message
	
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}	
	public String getHtml2() {
		return html2;
	}
	public void setHtml2(String html2) {
		this.html2 = html2;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	public String getFormAction() {
		return formAction;
	}
	public void setFormMessage(String formMessage) {
		this.formMessage = formMessage;
	}
	public String getFormMessage() {
		return formMessage;
	}
	public void setItemDisplayList(List<ItemDisplayDTO> itemDisplayList) {
		this.itemDisplayList = itemDisplayList;
	}
	public List<ItemDisplayDTO> getItemDisplayList() {
		return itemDisplayList;
	}


}


