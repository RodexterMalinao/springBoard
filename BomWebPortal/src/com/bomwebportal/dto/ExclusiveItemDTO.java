package com.bomwebportal.dto;
import java.io.Serializable;

public class ExclusiveItemDTO implements Serializable{

	private static final long serialVersionUID = -5140766437119101440L;
	
	String itemIdA;//ITEM_ID_A; 
	String itemTypeA;//ITEM_TYPE_A; 
	String itemIdB;//ITEM_ID_B; 
	String itemTypeB;//ITEM_TYPE_B;
	String itemDescriptionA;
	String itemDescriptionB;
	
	public String getItemDescriptionA() {
		return itemDescriptionA;
	}
	public void setItemDescriptionA(String itemDescriptionA) {
		this.itemDescriptionA = itemDescriptionA;
	}
	public String getItemDescriptionB() {
		return itemDescriptionB;
	}
	public void setItemDescriptionB(String itemDescriptionB) {
		this.itemDescriptionB = itemDescriptionB;
	}
	
	
	public String getItemIdA() {
		return itemIdA;
	}
	public void setItemIdA(String itemIdA) {
		this.itemIdA = itemIdA;
	}
	public String getItemTypeA() {
		return itemTypeA;
	}
	public void setItemTypeA(String itemTypeA) {
		this.itemTypeA = itemTypeA;
	}
	public String getItemIdB() {
		return itemIdB;
	}
	public void setItemIdB(String itemIdB) {
		this.itemIdB = itemIdB;
	}
	public String getItemTypeB() {
		return itemTypeB;
	}
	public void setItemTypeB(String itemTypeB) {
		this.itemTypeB = itemTypeB;
	}


	
}

