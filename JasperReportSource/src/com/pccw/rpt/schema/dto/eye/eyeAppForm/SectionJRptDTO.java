package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.util.ArrayList;

import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionCRptDTO.ChargingItem;
import com.pccw.rpt.util.ReportUtil;

public class SectionJRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4007153664957691988L;

	private ArrayList<ChargingItem> eyeSubscribedItemList = new ArrayList<ChargingItem>();
	
	private String eyeContent;
	
	private String subTitleEyeBbRental;
	private String subTitleEyePeList;
	private String subTitleEyeContent;
	private String subTitleEyeMoov;
	private String subTitleEyeNowTv;
	private String subTitleEyeViewAv;
	private String subTitleEyeBill;
	private String subTitleEyeTvBill;
	private String subTitleEyeExistVas;
	private String subTitleEyeReplaceVas;
	private String subTitleEyeNewVas;
	private String subTitleEyeIdd;
	private String subTitleEyeOptPremium;
	private String subTitleEyeOptAcc;
	
	public void addEyeSubscribedItem(String pItemType, String pDescription) {
		this.eyeSubscribedItemList.add(new ChargingItem(pItemType, this.replaceEyeDeviceKeyword(pDescription), null, null));
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

	public boolean isEyePlanListEmpty() {
		return this.isEyeSubscribedItemListEmpty("PLAN");
	}
	
	public ArrayList<ChargingItem> getEyePlanList() {
		return this.getEyeSubscribedItemList("PLAN");
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
		return this.isEyeSubscribedItemListEmpty("CONTENT");
	}
	
	public ArrayList<ChargingItem> getEyeContentList() {
		return this.getEyeSubscribedItemList("CONTENT");
	}
	
	public boolean isEyeMoovListEmpty() {
		return this.isEyeSubscribedItemListEmpty("MOOV");
	}
	
	public ArrayList<ChargingItem> getEyeMoovList() {
		return this.getEyeSubscribedItemList("MOOV");
	}
	
	public boolean isEyeNowTvListEmpty() {
		return this.isEyeSubscribedItemListEmpty("NOWTV-FREE")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-SPEC")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-CELE")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-SPT")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-MIRR")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-PAY")
				&& this.isEyeSubscribedItemListEmpty("NOWTV-STAR");
	}

	public ArrayList<ChargingItem> getEyeNowTvList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("NOWTV-FREE");
		this.addEyeSubscribedItemList("NOWTV-MIRR", rtnList);
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
		return this.isEyeSubscribedItemListEmpty("LTS_TP") 
				&& this.isEyeSubscribedItemListEmpty("VAS-HOT") 
				&& this.isEyeSubscribedItemListEmpty("VAS-OTHER");
	}

	public ArrayList<ChargingItem> getEyeNewVasList() {
		ArrayList<ChargingItem> rtnList = this.getEyeSubscribedItemList("LTS_TP");
		this.addEyeSubscribedItemList("VAS-HOT", rtnList);
		this.addEyeSubscribedItemList("VAS-OTHER", rtnList);
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

	public String getEyeContent() {
		return ReportUtil.defaultString(eyeContent);
	}

	public void setEyeContent(String eyeContent) {
		this.eyeContent = eyeContent;
	}
	
	public boolean isEyeOptAccListEmpty() {
		return this.isEyeSubscribedItemListEmpty("OPT_ACC");
	}
	
	public ArrayList<ChargingItem> getEyeOptAccList() {
		return this.getEyeSubscribedItemList("OPT_ACC");
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

	//ResTel
	private ArrayList<ChargingItem> resTelSubscribedItemList = new ArrayList<ChargingItem>();
	
	private String resTelContent;

	private String subTitleResTelBill;
	private String subTitleResTelExistVas;
	private String subTitleResTelReplaceVas;
	private String subTitleResTelNewVas;
	private String subTitleResTelIdd;
	private String subTitleResTelOptPremium;
	
	public void addResTelSubscribedItem(String pItemType, String pDescription) {
		this.resTelSubscribedItemList.add(new ChargingItem(pItemType, this.replaceEyeDeviceKeyword(pDescription), null, null));
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

	public boolean isResTelPlanListEmpty() {
		return this.isResTelSubscribedItemListEmpty("PLAN");
	}
	
	public ArrayList<ChargingItem> getResTelPlanList() {
		return this.getResTelSubscribedItemList("PLAN");
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
				&& this.isResTelSubscribedItemListEmpty("VAS-2DEL-F");
	}

	public ArrayList<ChargingItem> getResTelNewVasList() {
		ArrayList<ChargingItem> rtnList = this.getResTelSubscribedItemList("LTS_TP");
		this.addResTelSubscribedItemList("BVAS", rtnList);
		this.addResTelSubscribedItemList("VAS-2DEL-H", rtnList);
		this.addResTelSubscribedItemList("VAS-2DEL-O", rtnList);
		this.addResTelSubscribedItemList("VAS-2DEL-S", rtnList);
		this.addResTelSubscribedItemList("VAS-2DEL-F", rtnList);
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
	
	public String getResTelContent() {
		return ReportUtil.defaultString(resTelContent);
	}

	public void setResTelContent(String resTelContent) {
		this.resTelContent = resTelContent;
	}
	
	public boolean isEyeListEmpty() {
		return this.isEyeBbRentalListEmpty() && this.isEyeBillListEmpty() 
				&& this.isEyeContentListEmpty() && this.isEyeExistVasListEmpty() 
				&& this.isEyeIddListEmpty() && this.isEyeMoovListEmpty()
				&& this.isEyeNewVasListEmpty() && this.isEyeNowTvListEmpty()
				&& this.isEyeOptPremiumListEmpty() && this.isEyePeListEmpty()
				&& this.isEyeReplaceVasListEmpty() && this.isEyeTvBillListEmpty();
	}
	
	public boolean isResTelListEmpty() {
		return this.isResTelBillListEmpty() 
				&& this.isResTelExistVasListEmpty() 
				&& this.isResTelIddListEmpty()
				&& this.isResTelNewVasListEmpty()
				&& this.isResTelOptPremiumListEmpty()
				&& this.isResTelReplaceVasListEmpty();
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
}
