package com.bomwebportal.lts.dto.order;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;

public class SbOrderDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -5037478666498599381L;

	private String orderId = null;
	private ServiceDetailDTO[] srvDtls = null;
	private String lob = null;
	private String salesChannel = null;
	private String salesCd = null;
	private String salesTeam = null;
	private String staffNum = null;
	private String salesName = null;
	private String salesContactNum = null;
	private String signOffDate = null;
	private String boc = null;
	private String appDate = null;
	private String shopCd = null;
	private String ocid = null;
	private String disMode = null;
	private String signoffMode = null;
	private String collectMethod = null;
	private String esigEmailAddr = null;
	private String esigEmailLang = null;
	private String dmsInd = null;
	private String salesPosition = null;
	private String salesJob = null;
	private String salesProcessDate = null;
	private AccountDetailLtsDTO[] accounts = null;
	private AddressDetailLtsDTO address = null;
	private CustomerDetailLtsDTO[] customers = null;
	private AllOrdDocAssgnLtsDTO[] allOrdDocAssgns = null;
	private SignatureLtsDTO[] signatures = null;
	private BuildingMarkerDTO buildingMarker = null;
	private StaffInfoLtsDTO staffInfo = null;
	private ContactLtsDTO contact = null;
	private String createChannel = null;
	private String smsNo = null;
	private String orderType = null;
	private String orderSubType = null;
	private String backDateInd = null;
	private Service0060DetailLtsDTO[] srv0060Dtls = null;
	private String lastServiceInd = null;
	private String srvReqDate = null;
	private ResDnLtsDTO[] resDn = null;
	private String prepaymentSlipInd = null;
	
	// Prepayment Information for Payment Slip  [Felix SF Cheung]
	private ItemDetailDTO prepayItem;
	private String prepayAcctNum;
	private BasketDetailDTO selectedBasket;
	private LtsDsOrderInfoDTO ltsDsOrderInfo;
	private PrepayLtsDTO[] prepay = null;
	// [END - Felix SF Cheung] 
	
	private CustomerIguardRegDTO custIguardReg;
	
	public String getOrderMode() {
		if (StringUtils.isBlank(orderId)) {
			return null;
		}
		return StringUtils.substring(orderId, 0, 1);
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ServiceDetailDTO[] getSrvDtls() {
		return srvDtls;
	}

	public void setSrvDtls(ServiceDetailDTO[] srvDtls) {
		this.srvDtls = srvDtls;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getStaffNum() {
		return staffNum;
	}

	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}

	public String getSalesContactNum() {
		return salesContactNum;
	}

	public void setSalesContactNum(String salesContactNum) {
		this.salesContactNum = salesContactNum;
	}

	public String getSignOffDate() {
		return signOffDate;
	}

	public void setSignOffDate(String signOffDate) {
		this.signOffDate = signOffDate;
	}

	public String getBoc() {
		return boc;
	}

	public void setBoc(String boc) {
		this.boc = boc;
	}

	public AccountDetailLtsDTO[] getAccounts() {
		return accounts;
	}

	public void setAccounts(AccountDetailLtsDTO[] accounts) {
		this.accounts = accounts;
	}

	public AddressDetailLtsDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDetailLtsDTO address) {
		this.address = address;
	}

	public CustomerDetailLtsDTO[] getCustomers() {
		return customers;
	}

	public void setCustomers(CustomerDetailLtsDTO[] customers) {
		this.customers = customers;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public String getSalesTeam() {
		return salesTeam;
	}

	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getSalesName() {
		return this.salesName;
	}

	public void setSalesName(String pSalesName) {
		this.salesName = pSalesName;
	}

	public String getLangPref() {
		return "ENG".equals(esigEmailLang) ? "ENG" : "CHI";
	}

	public String getOcid() {
		return ocid;
	}

	public void setOcid(String ocid) {
		this.ocid = ocid;
	}

	public String getDisMode() {
		return disMode;
	}

	public void setDisMode(String disMode) {
		this.disMode = disMode;
	}

	public String getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}

	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}

	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}

	public String getEsigEmailLang() {
		return esigEmailLang;
	}

	public void setEsigEmailLang(String esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}

	public String getDmsInd() {
		return dmsInd;
	}

	public void setDmsInd(String dmsInd) {
		this.dmsInd = dmsInd;
	}

	public AllOrdDocAssgnLtsDTO[] getAllOrdDocAssgns() {
		return allOrdDocAssgns;
	}

	public void setAllOrdDocAssgns(AllOrdDocAssgnLtsDTO[] allOrdDocAssgns) {
		this.allOrdDocAssgns = allOrdDocAssgns;
	}

	public SignatureLtsDTO[] getSignatures() {
		return signatures;
	}

	public void setSignatures(SignatureLtsDTO[] signatures) {
		this.signatures = signatures;
	}

	public BuildingMarkerDTO getBuildingMarker() {
		return buildingMarker;
	}

	public void setBuildingMarker(BuildingMarkerDTO buildingMarker) {
		this.buildingMarker = buildingMarker;
	}

	public String getSalesPosition() {
		return salesPosition;
	}

	public void setSalesPosition(String salesPosition) {
		this.salesPosition = salesPosition;
	}

	public String getSalesJob() {
		return salesJob;
	}

	public void setSalesJob(String salesJob) {
		this.salesJob = salesJob;
	}

	public String getSalesProcessDate() {
		return salesProcessDate;
	}

	public void setSalesProcessDate(String salesProcessDate) {
		this.salesProcessDate = salesProcessDate;
	}

	public String getCreateChannel() {
		return createChannel;
	}

	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel;
	}

	public String getSmsNo() {
		return smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}

	public StaffInfoLtsDTO getStaffInfo() {
		return staffInfo;
	}

	public void setStaffInfo(StaffInfoLtsDTO staffInfo) {
		this.staffInfo = staffInfo;
	}
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderSubType() {
		return orderSubType;
	}

	public void setOrderSubType(String orderSubType) {
		this.orderSubType = orderSubType;
	}

	public ContactLtsDTO getContact() {
		return contact;
	}

	public void setContact(ContactLtsDTO contact) {
		this.contact = contact;
	}

	public String getBackDateInd() {
		return backDateInd;
	}

	public void setBackDateInd(String backDateInd) {
		this.backDateInd = backDateInd;
	}

	public Service0060DetailLtsDTO[] getSrv0060Dtls() {
		return srv0060Dtls;
	}

	public void setSrv0060Dtls(Service0060DetailLtsDTO[] srv0060Dtls) {
		this.srv0060Dtls = srv0060Dtls;
	} 
	
	
	public String getLastServiceInd() {
		return lastServiceInd;
	}

	public void setLastServiceInd(String lastServiceInd) {
		this.lastServiceInd = lastServiceInd;
	}

	public String getSrvReqDate() {
		return srvReqDate;
	}

	public void setSrvReqDate(String srvReqDate) {
		this.srvReqDate = srvReqDate;
	}

	public ResDnLtsDTO[] getResDn() {
		return resDn;
	}

	public void setResDn(ResDnLtsDTO[] resDn) {
		this.resDn = resDn;
	}
    	
	public ItemDetailDTO getPrepayItem() {
		return prepayItem;
	}

	public void setPrepayItem(ItemDetailDTO prepayItem) {
		this.prepayItem = prepayItem;
	}

	public String getPrepayAcctNum() {
		return prepayAcctNum;
	}

	public void setPrepayAcctNum(String prepayAcctNum) {
		this.prepayAcctNum = prepayAcctNum;
	}

	public BasketDetailDTO getSelectedBasket() {
		return selectedBasket;
	}

	public void setSelectedBasket(BasketDetailDTO selectedBasket) {
		this.selectedBasket = selectedBasket;
	}
	
	/**
	 * @return the prepaymentSlipInd
	 */
	public String getPrepaymentSlipInd() {
		return prepaymentSlipInd;
	}

	/**
	 * @param prepaymentSlipInd the prepaymentSlipInd to set
	 */
	public void setPrepaymentSlipInd(String prepaymentSlipInd) {
		this.prepaymentSlipInd = prepaymentSlipInd;
	}

	public LtsDsOrderInfoDTO getLtsDsOrderInfo() {
		return ltsDsOrderInfo;
	}

	public void setLtsDsOrderInfo(LtsDsOrderInfoDTO ltsDsOrderInfo) {
		this.ltsDsOrderInfo = ltsDsOrderInfo;
	}

	public PrepayLtsDTO[] getPrepay() {
		return prepay;
	}

	public void setPrepay(PrepayLtsDTO[] prepay) {
		this.prepay = prepay;
	}

	public CustomerIguardRegDTO getCustIguardReg() {
		return custIguardReg;
	}

	public void setCustIguardReg(CustomerIguardRegDTO custIguardReg) {
		this.custIguardReg = custIguardReg;
	}

	public String getSignoffMode() {
		return signoffMode;
	}

	public void setSignoffMode(String signoffMode) {
		this.signoffMode = signoffMode;
	}
		
}
