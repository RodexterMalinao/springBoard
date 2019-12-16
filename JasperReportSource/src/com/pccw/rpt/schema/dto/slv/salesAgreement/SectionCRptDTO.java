package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.io.Serializable;
import java.util.ArrayList;

import com.pccw.rpt.util.ReportUtil;

public class SectionCRptDTO extends SectionRptDTO {



	/**
	 * 
	 */
	private static final long serialVersionUID = -3822596743752210574L;

	private String slvProfileId;
	
	private String eyeContractPeriod;

	private String eyeInstallDate;

	private String eyeApptTime;

	private String eyeSrvNum;

	private String eyeSrvNumStatus;
	
	private String eyePrewiringDate;
	
	private String eyePrewiringTime;
	
	private String eyeCutOrderDate;
	
	private String eyeCutOrderTime;

	private ArrayList<PackageItem> packageList = new ArrayList<PackageItem>();

	private String resTelContractPeriod;

	private String resTelInstallDate;

	private String resTelApptTime;

	private String resTelSrvNum;

	private String resTelSrvNumStatus;
	
	private String resTelPrewiringDate;
	
	private String resTelPrewiringTime;

	private String resTelCutOrderDate;
	
	private String resTelCutOrderTime;
	
	private boolean workingDNInd;

	//private ArrayList<ChargingItem> resTelPlanList = new ArrayList<ChargingItem>();
	
	private boolean exDirectory;
	
	private String exDirectoryText;
	
	private String notExDirectoryText;
	
	private boolean portIn;
	
	private String grandTotal;
	
	private String grandTotalPrice;
	

	

	public String getSlvProfileId() {
		return this.slvProfileId;
	}

	public void setSlvProfileId(String pSlvProfileId) {
		this.slvProfileId = pSlvProfileId;
	}

	public String getEyeContractPeriod() {
		return ReportUtil.defaultString(this.eyeContractPeriod);
	}

	public void setEyeContractPeriod(String pEyeContractPeriod) {
		this.eyeContractPeriod = pEyeContractPeriod;
	}
	
	public String getEyeInstallDate() {
		return ReportUtil.defaultString(this.eyeInstallDate);
	}

	public void setEyeInstallDate(String pEyeInstallDate) {
		this.eyeInstallDate = pEyeInstallDate;
	}

	public String getEyeApptTime() {
		return ReportUtil.defaultString(this.eyeApptTime);
	}

	public void setEyeApptTime(String pEyeApptTime) {
		this.eyeApptTime = pEyeApptTime;
	}

	public String getEyeSrvNum() {
		return ReportUtil.defaultString(this.eyeSrvNum);
	}

	public void setEyeSrvNum(String pEyeSrvNum) {
		this.eyeSrvNum = pEyeSrvNum;
	}


	/*
	public ArrayList<ChargingItem> getEyePlanList() {
		return this.eyePlanList;
	}

	public void addEyePlan(String pDescription, String pMonthlyFixedTermRate,
			String pMonthToMonthRate) {
		this.eyePlanList.add(new ChargingItem(this.replaceEyeDeviceKeyword(pDescription),
				pMonthlyFixedTermRate, pMonthToMonthRate));
	}
	 */
	public String getResTelContractPeriod() {
		return ReportUtil.defaultString(this.resTelContractPeriod);
	}

	public void setResTelContractPeriod(String pResTelContractPeriod) {
		this.resTelContractPeriod = pResTelContractPeriod;
	}
	
	public String getResTelInstallDate() {
		return ReportUtil.defaultString(this.resTelInstallDate);
	}

	public void setResTelInstallDate(String pResTelInstallDate) {
		this.resTelInstallDate = pResTelInstallDate;
	}

	public String getResTelApptTime() {
		return ReportUtil.defaultString(this.resTelApptTime);
	}

	public void setResTelApptTime(String pResTelApptTime) {
		this.resTelApptTime = pResTelApptTime;
	}

	public String getResTelSrvNum() {
		return ReportUtil.defaultString(this.resTelSrvNum);
	}

	public void setResTelSrvNum(String pResTelSrvNum) {
		this.resTelSrvNum = pResTelSrvNum;
	}

	public String getEyeSrvNumStatus() {
		return ReportUtil.defaultString(this.eyeSrvNumStatus);
	}

	public void setEyeSrvNumStatus(String pEyeSrvNumStatus) {
		this.eyeSrvNumStatus = pEyeSrvNumStatus;
	}

	public String getResTelSrvNumStatus() {
		return ReportUtil.defaultString(this.resTelSrvNumStatus);
	}

	public void setResTelSrvNumStatus(String pResTelSrvNumStatus) {
		this.resTelSrvNumStatus = pResTelSrvNumStatus;
	}

	public String getEyePrewiringDate() {
		return ReportUtil.defaultString(this.eyePrewiringDate);
	}

	public void setEyePrewiringDate(String eyePrewiringDate) {
		this.eyePrewiringDate = eyePrewiringDate;
	}

	public String getEyePrewiringTime() {
		return ReportUtil.defaultString(this.eyePrewiringTime);
	}

	public void setEyePrewiringTime(String eyePrewiringTime) {
		this.eyePrewiringTime = eyePrewiringTime;
	}

	public String getResTelPrewiringDate() {
		return ReportUtil.defaultString(this.resTelPrewiringDate);
	}

	public void setResTelPrewiringDate(String resTelPrewiringDate) {
		this.resTelPrewiringDate = resTelPrewiringDate;
	}

	public String getResTelPrewiringTime() {
		return ReportUtil.defaultString(this.resTelPrewiringTime);
	}

	public void setResTelPrewiringTime(String resTelPrewiringTime) {
		this.resTelPrewiringTime = resTelPrewiringTime;
	}

	public String getEyeCutOrderDate() {
		return ReportUtil.defaultString(this.eyeCutOrderDate);
	}

	public void setEyeCutOrderDate(String eyeCutOrderDate) {
		this.eyeCutOrderDate = eyeCutOrderDate;
	}

	public String getEyeCutOrderTime() {
		return ReportUtil.defaultString(this.eyeCutOrderTime);
	}

	public void setEyeCutOrderTime(String eyeCutOrderTime) {
		this.eyeCutOrderTime = eyeCutOrderTime;
	}

	public String getResTelCutOrderDate() {
		return ReportUtil.defaultString(this.resTelCutOrderDate);
	}

	public void setResTelCutOrderDate(String resTelCutOrderDate) {
		this.resTelCutOrderDate = resTelCutOrderDate;
	}

	public String getResTelCutOrderTime() {
		return ReportUtil.defaultString(this.resTelCutOrderTime);
	}

	public void setResTelCutOrderTime(String resTelCutOrderTime) {
		this.resTelCutOrderTime = resTelCutOrderTime;
	}

	public boolean isWorkingDNInd() {
		return this.workingDNInd;
	}

	public void setWorkingDNInd(boolean workingDNInd) {
		this.workingDNInd = workingDNInd;
	}

	public static class PackageItem implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3628239461932973129L;
		private String packageDesc;
		private String packageSubTotal;
		private String packageUnitPrice;
		private String packageQty;
		private String serviceName;
		private String slvProfileId;
		private String paymentPhase;
		private ArrayList<ChargingItem> itemList = new ArrayList<ChargingItem>();
		private ArrayList<ChargingItem> discountItemList = new ArrayList<ChargingItem>();
		private String packageTotal;
		private String packageTotalPrice;
		private String packageTotalPrefix;
		private String packageTotalSufix;
		private double discount;
		private AddressRptDTO installationAddress = new AddressRptDTO();
		
		public String getServiceName() {
			return this.serviceName;
		}
		public void setServiceName(String pServiceName) {
			this.serviceName = pServiceName;
		}
		public String getSlvProfileId() {
			return this.slvProfileId;
		}
		public void setSlvProfileId(String pSlvProfileId) {
			this.slvProfileId = pSlvProfileId;
		}
		public String getPaymentPhase() {
			return this.paymentPhase;
		}
		public void setPaymentPhase(String pPaymentPhase) {
			this.paymentPhase = pPaymentPhase;
		}
		public ArrayList<ChargingItem> getItemList() {
			return this.itemList;
		}
		public void setItemList(ArrayList<ChargingItem> pItemList) {
			this.itemList = pItemList;
		}
		public String getPackageTotal() {
			return this.packageTotal;
		}
		public void setPackageTotal(String pPackageTotal) {
			this.packageTotal = pPackageTotal;
		}
		public String getPackageTotalPrice() {
			return this.packageTotalPrice;
		}
		public void setPackageTotalPrice(String pPackageTotalPrice) {
			this.packageTotalPrice = pPackageTotalPrice;
		}
		public String getPackageDesc() {
			return this.packageDesc;
		}
		public void setPackageDesc(String pPackageDesc) {
			this.packageDesc = pPackageDesc;
		}
		public String getPackageSubTotal() {
			return this.packageSubTotal;
		}
		public void setPackageSubTotal(String pPackageSubTotal) {
			this.packageSubTotal = pPackageSubTotal;
		}
		public String getPackageUnitPrice() {
			return this.packageUnitPrice;
		}
		public void setPackageUnitPrice(String pPackageUnitPrice) {
			this.packageUnitPrice = pPackageUnitPrice;
		}
		public String getPackageQty() {
			return this.packageQty;
		}
		public void setPackageQty(String pPackageQty) {
			this.packageQty = pPackageQty;
		}
		public ArrayList<ChargingItem> getDiscountItemList() {
			return this.discountItemList;
		}
		public void setDiscountItemList(ArrayList<ChargingItem> pDiscountItemList) {
			this.discountItemList = pDiscountItemList;
		}
		public double getDiscount() {
			return this.discount;
		}
		public void setDiscount(double pDiscount) {
			this.discount = pDiscount;
		}
		public String getPackageTotalPrefix() {
			return this.packageTotalPrefix;
		}
		public void setPackageTotalPrefix(String pPackageTotalPrefix) {
			this.packageTotalPrefix = pPackageTotalPrefix;
		}
		public String getPackageTotalSufix() {
			return this.packageTotalSufix;
		}
		public void setPackageTotalSufix(String pPackageTotalSufix) {
			this.packageTotalSufix = pPackageTotalSufix;
		}
		public AddressRptDTO getInstallationAddress() {
			return installationAddress;
		}
		public void setInstallationAddress(AddressRptDTO installationAddress) {
			this.installationAddress = installationAddress;
		}
	}
	public static class ChargingItem implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5812781052571879834L;

		//private String itemType;

		private String packageName;
		
		private String description;

		private String quantity;

		private String unitPrice;
		
		private String subTotal;

		public ChargingItem() {
			super();
		}

		public ChargingItem(String pPackageName, String pDescription, String pQuantity, String pUnitPrice,
				String pSubTotal) {
			super();
			this.packageName = pPackageName;
			this.description = pDescription;
			this.quantity = pQuantity;
			this.unitPrice = pUnitPrice;
			this.subTotal = pSubTotal;
		}

		/*public ChargingItem(String pItemType, String pDescription, String pMonthlyFixedTermRate,
				String pMonthToMonthRate) {
			super();
			this.itemType = pItemType;
			this.description = pDescription;
			this.monthlyFixedTermRate = pMonthlyFixedTermRate;
			this.monthToMonthRate = pMonthToMonthRate;
		}*/
		
		public String getDescription() {
			return ReportUtil.defaultString(this.description);
		}

		public String getPackageName() {
			return this.packageName;
		}

		public void setPackageName(String pPackageName) {
			this.packageName = pPackageName;
		}

		public void setDescription(String pDescription) {
			this.description = pDescription;
		}

		public String getQuantity() {
			return this.quantity;
		}

		public void setQuantity(String pQuantity) {
			this.quantity = pQuantity;
		}

		public String getUnitPrice() {
			return this.unitPrice;
		}

		public void setUnitPrice(String pUnitPrice) {
			this.unitPrice = pUnitPrice;
		}

		public String getSubTotal() {
			return this.subTotal;
		}

		public void setSubTotal(String pSubTotal) {
			this.subTotal = pSubTotal;
		}
	}

	public boolean isExDirectory() {
		return this.exDirectory;
	}

	public void setExDirectory(boolean pExDirectory) {
		this.exDirectory = pExDirectory;
	}

	public String getExDirectoryText() {
		return this.exDirectoryText;
	}

	public void setExDirectoryText(String pExDirectoryText) {
		this.exDirectoryText = pExDirectoryText;
	}

	public String getNotExDirectoryText() {
		return this.notExDirectoryText;
	}

	public void setNotExDirectoryText(String pNotExDirectoryText) {
		this.notExDirectoryText = pNotExDirectoryText;
	}

	public boolean isPortIn() {
		return this.portIn;
	}

	public void setPortIn(boolean pPortIn) {
		this.portIn = pPortIn;
	}

	public String getGrandTotal() {
		return this.grandTotal;
	}

	public void setGrandTotal(String pGrandTotal) {
		this.grandTotal = pGrandTotal;
	}

	public ArrayList<PackageItem> getPackageList() {
		return this.packageList;
	}

	public void setPackageList(ArrayList<PackageItem> pPackageList) {
		this.packageList = pPackageList;
	}

	public String getGrandTotalPrice() {
		return this.grandTotalPrice;
	}

	public void setGrandTotalPrice(String pGrandTotalPrice) {
		this.grandTotalPrice = pGrandTotalPrice;
	}
}
