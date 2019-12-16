package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.util.ArrayList;

import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionCRptDTO.ChargingItem;

public class SectionGRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -605887486080607150L;
	
	private String subTitleEyeBill;
	private String subTitleEyeTvBill;
	private String subTitleResTelBill;
	
	private ArrayList<ChargingItem> eyeBillPreferenceList = new ArrayList<ChargingItem>();
	private ArrayList<ChargingItem> resTelBillPreferenceList = new ArrayList<ChargingItem>();
	
	public void addEyeBillPreference(String pItemType, String pDescription) {
		this.eyeBillPreferenceList.add(new ChargingItem(pItemType, this.replaceEyeDeviceKeyword(pDescription), null, null));
	}
	
	public void addResTelBillPreference(String pItemType, String pDescription) {
		this.resTelBillPreferenceList.add(new ChargingItem(pItemType, pDescription, null, null));
	}
	
	private ArrayList<ChargingItem> getEyeBillPreferenceList(String pItemType) {
		ArrayList<ChargingItem> rtnList = new ArrayList<ChargingItem>();
		if (this.eyeBillPreferenceList.isEmpty()) {
			return rtnList;
		}
		this.addEyeBillPreferenceList(pItemType, rtnList);
		return rtnList;
	}

	private void addEyeBillPreferenceList(String pItemType, ArrayList<ChargingItem> pList) {
		if (this.eyeBillPreferenceList.isEmpty()) {
			return;
		}
		for (ChargingItem chargingItem : this.eyeBillPreferenceList) {
			if (pItemType.equals(chargingItem.getItemType())) {
				pList.add(chargingItem);
			}
		}
		return;
	}
	
	
	private boolean isEyeBillPreferenceListEmpty(String pItemType) {
		if (this.eyeBillPreferenceList.isEmpty()) {
			return true;
		}
		for (ChargingItem chargingItem : this.eyeBillPreferenceList) {
			if (pItemType.equals(chargingItem.getItemType())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isEyeBillPreferenceListEmpty() {
		return this.isEyeBillPreferenceListEmpty("NO-BILL") 
				&& this.isEyeBillPreferenceListEmpty("PAPER-BILL") 
				&& this.isEyeBillPreferenceListEmpty("PAPER-G") 
				&& this.isEyeBillPreferenceListEmpty("PAPER-W")
				&& this.isEyeBillPreferenceListEmpty("PAPER-BR") 
				&& this.isEyeBillPreferenceListEmpty("OLS-EBILL")
				&& this.isEyeBillPreferenceListEmpty("EBILL")
				&& this.isEyeBillPreferenceListEmpty("MYHKT-BILL")
				&& this.isEyeBillPreferenceListEmpty("EXST-MYHKT")
				&& this.isEyeBillPreferenceListEmpty("EMAILBILL");
	}
	
	public ArrayList<ChargingItem> getEyeBillPreferenceList() {
		ArrayList<ChargingItem> rtnList = this.getEyeBillPreferenceList("NO-BILL");
		this.addEyeBillPreferenceList("PAPER-BILL", rtnList);
		this.addEyeBillPreferenceList("PAPER-W", rtnList);
		this.addEyeBillPreferenceList("PAPER-G", rtnList);
		this.addEyeBillPreferenceList("PAPER-BR", rtnList);
		this.addEyeBillPreferenceList("OLS-EBILL", rtnList);
		this.addEyeBillPreferenceList("EBILL", rtnList);
		this.addEyeBillPreferenceList("MYHKT-BILL", rtnList);
		this.addEyeBillPreferenceList("EXST-MYHKT", rtnList);
		this.addEyeBillPreferenceList("EMAILBILL", rtnList);
		return rtnList;
	}
	
	public boolean isEyeTvBillPreferenceListEmpty() {
		return this.isEyeBillPreferenceListEmpty("TV-EMAIL") 
				&& this.isEyeBillPreferenceListEmpty("TV-PRINT") 
				&& this.isEyeBillPreferenceListEmpty("TV-DEVICE");
	}
	
	public ArrayList<ChargingItem> getEyeTvBillPreferenceList() {
		ArrayList<ChargingItem> rtnList = this.getEyeBillPreferenceList("TV-EMAIL");
		this.addEyeBillPreferenceList("TV-PRINT", rtnList);
		this.addEyeBillPreferenceList("TV-DEVICE", rtnList);
		return rtnList;
	}
	//Res Tel
	private ArrayList<ChargingItem> getResTelBillPreferenceList(String pItemType) {
		ArrayList<ChargingItem> rtnList = new ArrayList<ChargingItem>();
		if (this.resTelBillPreferenceList.isEmpty()) {
			return rtnList;
		}
		this.addResTelBillPreferenceList(pItemType, rtnList);
		return rtnList;
	}

	private void addResTelBillPreferenceList(String pItemType, ArrayList<ChargingItem> pList) {
		if (this.resTelBillPreferenceList.isEmpty()) {
			return;
		}
		for (ChargingItem chargingItem : this.resTelBillPreferenceList) {
			if (pItemType.equals(chargingItem.getItemType())) {
				pList.add(chargingItem);
			}
		}
		return;
	}
	
	
	private boolean isResTelBillPreferenceListEmpty(String pItemType) {
		if (this.resTelBillPreferenceList.isEmpty()) {
			return true;
		}
		for (ChargingItem chargingItem : this.resTelBillPreferenceList) {
			if (pItemType.equals(chargingItem.getItemType())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isResTelBillPreferenceListEmpty() {
		return this.isResTelBillPreferenceListEmpty("NO-BILL") 
				&& this.isResTelBillPreferenceListEmpty("PAPER-BILL") 
				&& this.isResTelBillPreferenceListEmpty("PAPER-G") 
				&& this.isResTelBillPreferenceListEmpty("PAPER-W") 
				&& this.isResTelBillPreferenceListEmpty("PAPER-BR") 
				&& this.isResTelBillPreferenceListEmpty("OLS-EBILL")
				&& this.isResTelBillPreferenceListEmpty("EBILL")
				&& this.isResTelBillPreferenceListEmpty("MYHKT-BILL")
				&& this.isResTelBillPreferenceListEmpty("EXST-MYHKT")
				&& this.isResTelBillPreferenceListEmpty("EMAILBILL");
	}
	
	public ArrayList<ChargingItem> getResTelBillPreferenceList() {
		ArrayList<ChargingItem> rtnList = this.getResTelBillPreferenceList("NO-BILL");
		this.addResTelBillPreferenceList("PAPER-BILL", rtnList);
		this.addResTelBillPreferenceList("PAPER-W", rtnList);
		this.addResTelBillPreferenceList("PAPER-G", rtnList);
		this.addResTelBillPreferenceList("PAPER-BR", rtnList);
		this.addResTelBillPreferenceList("OLS-EBILL", rtnList);
		this.addResTelBillPreferenceList("EBILL", rtnList);
		this.addResTelBillPreferenceList("MYHKT-BILL", rtnList);
		this.addResTelBillPreferenceList("EXST-MYHKT", rtnList);
		this.addResTelBillPreferenceList("EMAILBILL", rtnList);
		return rtnList;
	}

	public String getSubTitleEyeBill() {
		return this.subTitleEyeBill;
	}

	public void setSubTitleEyeBill(String subTitleEyeBill) {
		this.subTitleEyeBill = subTitleEyeBill;
	}

	public String getSubTitleEyeTvBill() {
		return this.subTitleEyeTvBill;
	}

	public void setSubTitleEyeTvBill(String subTitleEyeTvBill) {
		this.subTitleEyeTvBill = subTitleEyeTvBill;
	}

	public String getSubTitleResTelBill() {
		return this.subTitleResTelBill;
	}

	public void setSubTitleResTelBill(String subTitleResTelBill) {
		this.subTitleResTelBill = subTitleResTelBill;
	}
}
