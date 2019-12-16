package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.util.ArrayList;

import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionCRptDTO.ChargingItem;
import com.pccw.rpt.util.ReportUtil;

public class SectionDRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6189017947744402360L;
	
	private ArrayList<ChargingItem> eyeSubscribedItemList = new ArrayList<ChargingItem>();

	private String subTitleEyeBbRental;
	private String subTitleEyePeList;
	private String subTitleEyeContent;
	private String subTitleEyeMoov;
	private String subTitleEyeNowTv;
	private String subTitleEyeNowTvChannel;
	private String subTitleEyeViewAv;
	private String subTitleEyeBill;
	private String subTitleEyeTvBill;
	private String subTitleEyeExistVas;
	private String subTitleEyeReplaceVas;
	private String subTitleEyeNewVas;
	private String subTitleEyeIdd;
	private String subTitleEyeOptPremium;
	private String subTitleEyeOptAcc;
	private String subTitleResTelBill;
	private String subTitleResTelExistVas;
	private String subTitleResTelReplaceVas;
	private String subTitleResTelNewVas;
	private String subTitleResTelIdd;
	private String subTitleResTelOptPremium;
	private String subTitleEyeInstall;
	private String subTitleEyeInstallWaive;
	private String subTitleEyeAdmin;
	private String subTitleEyeAdminWaive;
	private String subTitleEyeCancel;
	private String subTitleEyeCancelWaive;
	
	
	public void addEyeSubscribedItem(String pItemType, String pDescription, String pMonthlyFixedTermRate,
			String pMonthToMonthRate) {
		this.eyeSubscribedItemList.add(new ChargingItem(pItemType, this.replaceEyeDeviceKeyword(pDescription),
				pMonthlyFixedTermRate, pMonthToMonthRate));
	}

	public void addEyeSubscribedItem(String pItemType, String pDescription, int pQty, String pMonthlyFixedTermRate,
			String pMonthToMonthRate) {
		this.eyeSubscribedItemList.add(new ChargingItem(pItemType, pDescription + " X " + String.valueOf(pQty),
				pMonthlyFixedTermRate, pMonthToMonthRate));
	}
	
	private ArrayList<ChargingItem> getEyeSubscribedItemList(String pItemType) {
		ArrayList<ChargingItem> rtnList = new ArrayList<ChargingItem>();
		if (this.eyeSubscribedItemList.isEmpty()) {
			return rtnList;
		}
		this.addEyeSubscribedItemList(pItemType, rtnList);
		return rtnList;
	}

	private void addEyeSubscribedItemList(String pItemType, ArrayList<ChargingItem> pList) {
		if (this.eyeSubscribedItemList.isEmpty()) {
			return;
		}
		for (ChargingItem chargingItem : this.eyeSubscribedItemList) {
			if (pItemType.equals(chargingItem.getItemType())) {
				pList.add(chargingItem);
			}
		}
		return;
	}

	private boolean isEyeSubscribedItemListEmpty(String pItemType) {
		if (this.eyeSubscribedItemList.isEmpty()) {
			return true;
		}
		for (ChargingItem chargingItem : this.eyeSubscribedItemList) {
			if (pItemType.equals(chargingItem.getItemType())) {
				return false;
			}
		}
		return true;
	}

	public boolean isEyeInstallListEmpty() {
		return this.isEyeSubscribedItemListEmpty("INSTALL");
	}

	public ArrayList<ChargingItem> getEyeInstallList() {
		return this.getEyeSubscribedItemList("INSTALL");
	}

	public boolean isEyeInstallWaiveListEmpty() {
		return this.isEyeSubscribedItemListEmpty("INSTALL-W");
	}

	public ArrayList<ChargingItem> getEyeInstallWaiveList() {
		return this.getEyeSubscribedItemList("INSTALL-W");
	}	
	
	public boolean isEyeAdminListEmpty() {
		return this.isEyeSubscribedItemListEmpty("ADM-CHRG");
	}

	public ArrayList<ChargingItem> getEyeAdminList() {
		return this.getEyeSubscribedItemList("ADM-CHRG");
	}

	public boolean isEyeAdminWaiveListEmpty() {
		return this.isEyeSubscribedItemListEmpty("ADM-CHRG-W");
	}

	public ArrayList<ChargingItem> getEyeAdminWaiveList() {
		return this.getEyeSubscribedItemList("ADM-CHRG-W");
	}	
	
	public boolean isEyeCancelListEmpty() {
		return this.isEyeSubscribedItemListEmpty("CAN-CHRG");
	}

	public ArrayList<ChargingItem> getEyeCancelList() {
		return this.getEyeSubscribedItemList("CAN-CHRG");
	}

	public boolean isEyeCancelWaiveListEmpty() {
		return this.isEyeSubscribedItemListEmpty("CAN-CHRG-W");
	}

	public ArrayList<ChargingItem> getEyeCancelWaiveList() {
		return this.getEyeSubscribedItemList("CAN-CHRG-W");
	}	
	
	public boolean isEyeBbRentalListEmpty() {
		return this.isEyeSubscribedItemListEmpty("BB-RENTAL");
	}
	
	public ArrayList<ChargingItem> getEyeBbRentalList() {
		return this.getEyeSubscribedItemList("BB-RENTAL");
	}
	
	public boolean isEyePeListEmpty() {
		return this.isEyeSubscribedItemListEmpty("PE-FREE") 
				&& this.isEyeSubscribedItemListEmpty("PE-SOCKET");
	}

	public ArrayList<ChargingItem> getEyePeList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("PE-FREE");
		this.addEyeSubscribedItemList("PE-SOCKET", rtnList);
		return rtnList;
	}
	
	public boolean isEyeContentListEmpty() {
		return this.isEyeSubscribedItemListEmpty("CONTENT")
				&& this.isEyeSubscribedItemListEmpty("SELF-PICKUP")
				&& this.isEyeSubscribedItemListEmpty("FIELD-SERVICE");
	}
	
	public ArrayList<ChargingItem> getEyeContentList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("CONTENT");
		this.addEyeSubscribedItemList("SELF-PICKUP", rtnList);
		this.addEyeSubscribedItemList("FIELD-SERVICE", rtnList);
		return rtnList;
	}
	
	public boolean isEyeMoovListEmpty() {
		return this.isEyeSubscribedItemListEmpty("MOOV");
	}
	
	public ArrayList<ChargingItem> getEyeMoovList() {
		return this.getEyeSubscribedItemList("MOOV");
	}
	
	public boolean isEyeNowTvListEmpty() {
		return this.isEyeSubscribedItemListEmpty("NOWTV-SPEC")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-CELE")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-SPT")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-MIRR")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-PAY")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-STAR");
	}

	public ArrayList<ChargingItem> getEyeNowTvList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("NOWTV-MIRR");
		this.addEyeSubscribedItemList("NOWTV-CELE", rtnList);
		this.addEyeSubscribedItemList("NOWTV-SPT", rtnList);
		this.addEyeSubscribedItemList("NOWTV-SPEC", rtnList);
		this.addEyeSubscribedItemList("NOWTV-PAY", rtnList);
		this.addEyeSubscribedItemList("NOWTV-STAR", rtnList);
		return rtnList;
	}
	
	private String eyeViewAvInd;

	public String getEyeViewAvInd() {
		return ReportUtil.defaultString(this.eyeViewAvInd);
	}

	public void setEyeViewAvInd(String eyeViewAvInd) {
		this.eyeViewAvInd = eyeViewAvInd;
	}

	public boolean isEyeViewAv() {
		return "Y".equals(this.eyeViewAvInd);
	}
	
	public boolean isEyeBillListEmpty() {
		return this.isEyeSubscribedItemListEmpty("NO-BILL") 
				&& this.isEyeSubscribedItemListEmpty("PAPER-BILL");
	}

	public ArrayList<ChargingItem> getEyeBillList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("NO-BILL");
		this.addEyeSubscribedItemList("PAPER-BILL", rtnList);
		return rtnList;
	}
	
	public boolean isEyeTvBillListEmpty() {
		return this.isEyeSubscribedItemListEmpty("TV-EMAIL") 
				&& this.isEyeSubscribedItemListEmpty("TV-PRINT")
				&& this.isEyeSubscribedItemListEmpty("TV-DEVICE");
	}

	public ArrayList<ChargingItem> getEyeTvBillList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("TV-EMAIL");
		this.addEyeSubscribedItemList("TV-PRINT", rtnList);
		this.addEyeSubscribedItemList("TV-DEVICE", rtnList);
		return rtnList;
	}
	
	public boolean isEyeExistVasListEmpty() {
		return this.isEyeSubscribedItemListEmpty("EXIST-VAS");
	}
	
	public ArrayList<ChargingItem> getEyeExistVasList() {
		return this.getEyeSubscribedItemList("EXIST-VAS");
	}
	
	public boolean isEyeReplaceVasListEmpty() {
		return this.isEyeSubscribedItemListEmpty("REPLAC-VAS");
	}
	
	public ArrayList<ChargingItem> getEyeReplaceVasList() {
		return this.getEyeSubscribedItemList("REPLAC-VAS");
	}
	
	public boolean isEyeNewVasListEmpty() {
		return  this.isEyeSubscribedItemListEmpty("LTS_TP")
				&& this.isEyeSubscribedItemListEmpty("BVAS")
		 		&& this.isEyeSubscribedItemListEmpty("VAS-HOT") 
				&& this.isEyeSubscribedItemListEmpty("VAS-OTHER")
				&& this.isEyeSubscribedItemListEmpty("OLS-VAS")
				&& this.isEyeSubscribedItemListEmpty("FFP-HOT")
				&& this.isEyeSubscribedItemListEmpty("FFP-OTHER")
				&& this.isEyeSubscribedItemListEmpty("FFP-STAFF")
				&& this.isEyeSubscribedItemListEmpty("VAS-FFP")
				&& this.isEyeSubscribedItemListEmpty("VAS-PREW")
				&& this.isEyeSubscribedItemListEmpty("VAS-PREI")
				&& this.isEyeSubscribedItemListEmpty("S-WARRANTY")
		        && this.isEyeSubscribedItemListEmpty("CHG-DN-OT")
		        && this.isEyeSubscribedItemListEmpty("CHG-DNY-OT")
		        && this.isEyeSubscribedItemListEmpty("CHG-DN-OT-WAIVE")
		        && this.isEyeSubscribedItemListEmpty("CHG-DNY-OT-WAIVE");
	}

	public ArrayList<ChargingItem> getEyeNewVasList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("LTS_TP");
		this.addEyeSubscribedItemList("BVAS", rtnList);
		this.addEyeSubscribedItemList("VAS-HOT", rtnList);
		this.addEyeSubscribedItemList("VAS-OTHER", rtnList);
		this.addEyeSubscribedItemList("OLS-VAS", rtnList);
		this.addEyeSubscribedItemList("FFP-HOT", rtnList);
		this.addEyeSubscribedItemList("FFP-OTHER", rtnList);
		this.addEyeSubscribedItemList("FFP-STAFF", rtnList);
		this.addEyeSubscribedItemList("VAS-FFP", rtnList);
		this.addEyeSubscribedItemList("VAS-PREW", rtnList);
		this.addEyeSubscribedItemList("VAS-PREI", rtnList);
		this.addEyeSubscribedItemList("S-WARRANTY", rtnList);
		this.addEyeSubscribedItemList("CHG-DN-OT", rtnList);
		this.addEyeSubscribedItemList("CHG-DNY-OT", rtnList);
		this.addEyeSubscribedItemList("CHG-DN-OT-WAIVE", rtnList);
		this.addEyeSubscribedItemList("CHG-DNY-OT-WAIVE", rtnList);
		return rtnList;
	}

	public boolean isEyeIddListEmpty() {
		return this.isEyeSubscribedItemListEmpty("IDD") 
				&& this.isEyeSubscribedItemListEmpty("0060E");
	}

	public ArrayList<ChargingItem> getEyeIddList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("IDD");
		this.addEyeSubscribedItemList("0060E", rtnList);
		return rtnList;
	}
	
	public boolean isEyeOptPremiumListEmpty() {
		return this.isEyeSubscribedItemListEmpty("PREM-PAY");
	}
	
	public ArrayList<ChargingItem> getEyeOptPremiumList() {
		return this.getEyeSubscribedItemList("PREM-PAY");
	}
	
	public boolean isEyeOptAccListEmpty() {
		return this.isEyeSubscribedItemListEmpty("OPT_ACC");
	}
	
	public ArrayList<ChargingItem> getEyeOptAccList() {
		return this.getEyeSubscribedItemList("OPT_ACC");
	}
	
	//resTel
	
	private ArrayList<ChargingItem> resTelSubscribedItemList = new ArrayList<ChargingItem>();

	public void addResTelSubscribedItem(String pItemType, String pDescription, String pMonthlyFixedTermRate,
			String pMonthToMonthRate) {
		this.resTelSubscribedItemList.add(new ChargingItem(pItemType, this.replaceEyeDeviceKeyword(pDescription),
				pMonthlyFixedTermRate, pMonthToMonthRate));
	}

	public void addResTelSubscribedItem(String pItemType, String pDescription, int pQty, String pMonthlyFixedTermRate,
			String pMonthToMonthRate) {
		this.resTelSubscribedItemList.add(new ChargingItem(pItemType, pDescription + " X " + String.valueOf(pQty),
				pMonthlyFixedTermRate, pMonthToMonthRate));
	}
	
	private ArrayList<ChargingItem> getResTelSubscribedItemList(String pItemType) {
		ArrayList<ChargingItem> rtnList = new ArrayList<ChargingItem>();
		if (this.resTelSubscribedItemList.isEmpty()) {
			return rtnList;
		}
		this.addResTelSubscribedItemList(pItemType, rtnList);
		return rtnList;
	}

	private void addResTelSubscribedItemList(String pItemType, ArrayList<ChargingItem> pList) {
		if (this.resTelSubscribedItemList.isEmpty()) {
			return;
		}
		for (ChargingItem chargingItem : this.resTelSubscribedItemList) {
			if (pItemType.equals(chargingItem.getItemType())) {
				pList.add(chargingItem);
			}
		}
		return;
	}

	private boolean isResTelSubscribedItemListEmpty(String pItemType) {
		if (this.resTelSubscribedItemList.isEmpty()) {
			return true;
		}
		for (ChargingItem chargingItem : this.resTelSubscribedItemList) {
			if (pItemType.equals(chargingItem.getItemType())) {
				return false;
			}
		}
		return true;
	}

	public boolean isResTelBillListEmpty() {
		return this.isResTelSubscribedItemListEmpty("NO-BILL") 
				&& this.isResTelSubscribedItemListEmpty("PAPER-BILL");
	}

	public ArrayList<ChargingItem> getResTelBillList() {
		ArrayList<ChargingItem> rtnList = this.getResTelSubscribedItemList("NO-BILL");
		this.addResTelSubscribedItemList("PAPER-BILL", rtnList);
		return rtnList;
	}
	
	public boolean isResTelExistVasListEmpty() {
		return this.isResTelSubscribedItemListEmpty("EXIST-VAS");
	}
	
	public ArrayList<ChargingItem> getResTelExistVasList() {
		return this.getResTelSubscribedItemList("EXIST-VAS");
	}
	
	public boolean isResTelReplaceVasListEmpty() {
		return this.isResTelSubscribedItemListEmpty("REPLAC-VAS");
	}
	
	public ArrayList<ChargingItem> getResTelReplaceVasList() {
		return this.getResTelSubscribedItemList("REPLAC-VAS");
	}
	
	public boolean isResTelNewVasListEmpty() {
		return this.isResTelSubscribedItemListEmpty("LTS_TP")
				&& this.isResTelSubscribedItemListEmpty("BVAS") 
				&& this.isResTelSubscribedItemListEmpty("VAS-2DEL-H") 
				&& this.isResTelSubscribedItemListEmpty("VAS-2DEL-O")
				&& this.isResTelSubscribedItemListEmpty("VAS-2DEL-S")
				&& this.isResTelSubscribedItemListEmpty("VAS-2DEL-F")
				&& this.isResTelSubscribedItemListEmpty("OLS-VAS")
				&& this.isResTelSubscribedItemListEmpty("FFP-HOT")
				&& this.isResTelSubscribedItemListEmpty("FFP-OTHER")
				&& this.isResTelSubscribedItemListEmpty("FFP-STAFF")
				&& this.isResTelSubscribedItemListEmpty("VAS-FFP")
				&& this.isResTelSubscribedItemListEmpty("VAS-HOT")
				&& this.isResTelSubscribedItemListEmpty("VAS-OTHER")
				&& this.isResTelSubscribedItemListEmpty("VAS-PREW")
				&& this.isResTelSubscribedItemListEmpty("VAS-PREI")
				&& this.isResTelSubscribedItemListEmpty("S-WARRANTY")
				&& this.isResTelSubscribedItemListEmpty("CHG-DN-OT")
		        && this.isResTelSubscribedItemListEmpty("CHG-DNY-OT")
		        && this.isResTelSubscribedItemListEmpty("CHG-DN-OT-WAIVE")
		        && this.isResTelSubscribedItemListEmpty("CHG-DNY-OT-WAIVE");
	}

	public ArrayList<ChargingItem> getResTelNewVasList() {
		ArrayList<ChargingItem> rtnList = this.getResTelSubscribedItemList("LTS_TP");
		this.addResTelSubscribedItemList("BVAS", rtnList);
		this.addResTelSubscribedItemList("VAS-2DEL-H", rtnList);
		this.addResTelSubscribedItemList("VAS-2DEL-O", rtnList);
		this.addResTelSubscribedItemList("VAS-2DEL-S", rtnList);
		this.addResTelSubscribedItemList("VAS-2DEL-F", rtnList);
		this.addResTelSubscribedItemList("OLS-VAS", rtnList);
		this.addResTelSubscribedItemList("FFP-HOT", rtnList);
		this.addResTelSubscribedItemList("FFP-OTHER", rtnList);
		this.addResTelSubscribedItemList("FFP-STAFF", rtnList);
		this.addResTelSubscribedItemList("VAS-FFP", rtnList);
		this.addResTelSubscribedItemList("VAS-HOT", rtnList);
		this.addResTelSubscribedItemList("VAS-OTHER", rtnList);
		this.addResTelSubscribedItemList("VAS-PREW", rtnList);
		this.addResTelSubscribedItemList("VAS-PREI", rtnList);
		this.addResTelSubscribedItemList("S-WARRANTY", rtnList);
		this.addResTelSubscribedItemList("CHG-DN-OT", rtnList);
		this.addResTelSubscribedItemList("CHG-DNY-OT", rtnList);
		this.addResTelSubscribedItemList("CHG-DN-OT-WAIVE", rtnList);
		this.addResTelSubscribedItemList("CHG-DNY-OT-WAIVE", rtnList);
		return rtnList;
	}

	public boolean isResTelIddListEmpty() {
		return this.isResTelSubscribedItemListEmpty("IDD") 
				&& this.isResTelSubscribedItemListEmpty("0060E");
	}

	public ArrayList<ChargingItem> getResTelIddList() {
		ArrayList<ChargingItem> rtnList = this.getResTelSubscribedItemList("IDD");
		this.addResTelSubscribedItemList("0060E", rtnList);
		return rtnList;
	}
	
	public boolean isResTelOptPremiumListEmpty() {
		return this.isResTelSubscribedItemListEmpty("PREM-PAY");
	}
	
	public ArrayList<ChargingItem> getResTelOptPremiumList() {
		return this.getResTelSubscribedItemList("PREM-PAY");
	}
	
	public boolean isEyeListEmpty() {
		return this.isEyeBbRentalListEmpty() && this.isEyeBillListEmpty() 
				&& this.isEyeContentListEmpty() && this.isEyeExistVasListEmpty() 
				&& this.isEyeIddListEmpty() && this.isEyeMoovListEmpty()
				&& this.isEyeNewVasListEmpty() && this.isEyeNowTvListEmpty()
				&& this.isEyeOptPremiumListEmpty() && this.isEyePeListEmpty()
				&& this.isEyeReplaceVasListEmpty() && this.isEyeTvBillListEmpty()
				&& this.isEyeInstallListEmpty() && this.isEyeInstallWaiveListEmpty()
				&& this.isEyeAdminListEmpty() && this.isEyeAdminWaiveListEmpty()
				&& this.isEyeCancelListEmpty() && this.isEyeCancelWaiveListEmpty();
	}
	
	public boolean isResTelListEmpty() {
		return this.isResTelBillListEmpty() 
				&& this.isResTelExistVasListEmpty() 
				&& this.isResTelIddListEmpty() 
				&& this.isResTelNewVasListEmpty() 
				&& this.isResTelOptPremiumListEmpty() 
				&& this.isResTelReplaceVasListEmpty();
	}

	private ArrayList<ChargingItem> eyeNowTvChannelList = new ArrayList<ChargingItem>();
	
	public ArrayList<ChargingItem> getEyeNowTvChannelList() {
		return this.eyeNowTvChannelList;
	}

	public void addEyeNowTvChannel(String pDescription) {
		this.eyeNowTvChannelList.add(new ChargingItem(pDescription,
				null, null));
	}
	public boolean isEyeNowTvChannelListEmpty() {
		return this.eyeNowTvChannelList.isEmpty();
	}

	public String getSubTitleEyeInstall() {
		return subTitleEyeInstall;
	}

	public void setSubTitleEyeInstall(String subTitleEyeInstall) {
		this.subTitleEyeInstall = subTitleEyeInstall;
	}

	public String getSubTitleEyeInstallWaive() {
		return subTitleEyeInstallWaive;
	}

	public void setSubTitleEyeInstallWaive(String subTitleEyeInstallWaive) {
		this.subTitleEyeInstallWaive = subTitleEyeInstallWaive;
	}

	public String getSubTitleEyeAdmin() {
		return this.subTitleEyeAdmin;
	}

	public void setSubTitleEyeAdmin(String pSubTitleEyeAdmin) {
		this.subTitleEyeAdmin = pSubTitleEyeAdmin;
	}

	public String getSubTitleEyeAdminWaive() {
		return this.subTitleEyeAdminWaive;
	}

	public void setSubTitleEyeAdminWaive(String pSubTitleEyeAdminWaive) {
		this.subTitleEyeAdminWaive = pSubTitleEyeAdminWaive;
	}

	public String getSubTitleEyeCancel() {
		return this.subTitleEyeCancel;
	}

	public void setSubTitleEyeCancel(String pSubTitleEyeCancel) {
		this.subTitleEyeCancel = pSubTitleEyeCancel;
	}

	public String getSubTitleEyeCancelWaive() {
		return this.subTitleEyeCancelWaive;
	}

	public void setSubTitleEyeCancelWaive(String pSubTitleEyeCancelWaive) {
		this.subTitleEyeCancelWaive = pSubTitleEyeCancelWaive;
	}

	public String getSubTitleEyeBbRental() {
		return this.subTitleEyeBbRental;
	}

	public void setSubTitleEyeBbRental(String pSubTitleEyeBbRental) {
		this.subTitleEyeBbRental = pSubTitleEyeBbRental;
	}

	public String getSubTitleEyePeList() {
		return this.subTitleEyePeList;
	}

	public void setSubTitleEyePeList(String pSubTitleEyePeList) {
		this.subTitleEyePeList = pSubTitleEyePeList;
	}

	public String getSubTitleEyeContent() {
		return this.subTitleEyeContent;
	}

	public void setSubTitleEyeContent(String pSubTitleEyeContent) {
		this.subTitleEyeContent = pSubTitleEyeContent;
	}

	public String getSubTitleEyeMoov() {
		return this.subTitleEyeMoov;
	}

	public void setSubTitleEyeMoov(String pSubTitleEyeMoov) {
		this.subTitleEyeMoov = pSubTitleEyeMoov;
	}

	public String getSubTitleEyeNowTv() {
		return this.subTitleEyeNowTv;
	}

	public void setSubTitleEyeNowTv(String pSubTitleEyeNowTv) {
		this.subTitleEyeNowTv = pSubTitleEyeNowTv;
	}

	public String getSubTitleEyeViewAv() {
		return this.subTitleEyeViewAv;
	}

	public void setSubTitleEyeViewAv(String pSubTitleEyeViewAv) {
		this.subTitleEyeViewAv = pSubTitleEyeViewAv;
	}

	public String getSubTitleEyeBill() {
		return this.subTitleEyeBill;
	}

	public void setSubTitleEyeBill(String pSubTitleEyeBill) {
		this.subTitleEyeBill = pSubTitleEyeBill;
	}

	public String getSubTitleEyeTvBill() {
		return this.subTitleEyeTvBill;
	}

	public void setSubTitleEyeTvBill(String pSubTitleEyeTvBill) {
		this.subTitleEyeTvBill = pSubTitleEyeTvBill;
	}

	public String getSubTitleEyeExistVas() {
		return this.subTitleEyeExistVas;
	}

	public void setSubTitleEyeExistVas(String pSubTitleEyeExistVas) {
		this.subTitleEyeExistVas = pSubTitleEyeExistVas;
	}

	public String getSubTitleEyeReplaceVas() {
		return this.subTitleEyeReplaceVas;
	}

	public void setSubTitleEyeReplaceVas(String pSubTitleEyeReplaceVas) {
		this.subTitleEyeReplaceVas = pSubTitleEyeReplaceVas;
	}

	public String getSubTitleEyeNewVas() {
		return this.subTitleEyeNewVas;
	}

	public void setSubTitleEyeNewVas(String pSubTitleEyeNewVas) {
		this.subTitleEyeNewVas = pSubTitleEyeNewVas;
	}

	public String getSubTitleEyeIdd() {
		return this.subTitleEyeIdd;
	}

	public void setSubTitleEyeIdd(String pSubTitleEyeIdd) {
		this.subTitleEyeIdd = pSubTitleEyeIdd;
	}

	public String getSubTitleEyeOptPremium() {
		return this.subTitleEyeOptPremium;
	}

	public void setSubTitleEyeOptPremium(String pSubTitleEyeOptPremium) {
		this.subTitleEyeOptPremium = pSubTitleEyeOptPremium;
	}

	public String getSubTitleEyeOptAcc() {
		return this.subTitleEyeOptAcc;
	}

	public void setSubTitleEyeOptAcc(String pSubTitleEyeOptAcc) {
		this.subTitleEyeOptAcc = pSubTitleEyeOptAcc;
	}

	public String getSubTitleResTelBill() {
		return this.subTitleResTelBill;
	}

	public void setSubTitleResTelBill(String pSubTitleResTelBill) {
		this.subTitleResTelBill = pSubTitleResTelBill;
	}

	public String getSubTitleResTelExistVas() {
		return this.subTitleResTelExistVas;
	}

	public void setSubTitleResTelExistVas(String pSubTitleResTelExistVas) {
		this.subTitleResTelExistVas = pSubTitleResTelExistVas;
	}

	public String getSubTitleResTelReplaceVas() {
		return this.subTitleResTelReplaceVas;
	}

	public void setSubTitleResTelReplaceVas(String pSubTitleResTelReplaceVas) {
		this.subTitleResTelReplaceVas = pSubTitleResTelReplaceVas;
	}

	public String getSubTitleResTelNewVas() {
		return this.subTitleResTelNewVas;
	}

	public void setSubTitleResTelNewVas(String pSubTitleResTelNewVas) {
		this.subTitleResTelNewVas = pSubTitleResTelNewVas;
	}

	public String getSubTitleResTelIdd() {
		return this.subTitleResTelIdd;
	}

	public void setSubTitleResTelIdd(String pSubTitleResTelIdd) {
		this.subTitleResTelIdd = pSubTitleResTelIdd;
	}

	public String getSubTitleResTelOptPremium() {
		return this.subTitleResTelOptPremium;
	}

	public void setSubTitleResTelOptPremium(String pSubTitleResTelOptPremium) {
		this.subTitleResTelOptPremium = pSubTitleResTelOptPremium;
	}

	public String getSubTitleEyeNowTvChannel() {
		return ReportUtil.defaultString(this.subTitleEyeNowTvChannel);
	}

	public void setSubTitleEyeNowTvChannel(String subTitleEyeNowTvChannel) {
		this.subTitleEyeNowTvChannel = subTitleEyeNowTvChannel;
	}
}
