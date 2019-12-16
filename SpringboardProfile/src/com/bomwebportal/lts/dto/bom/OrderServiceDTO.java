package com.bomwebportal.lts.dto.bom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;

public class OrderServiceDTO implements Serializable {

	private static final long serialVersionUID = -2329605239904741832L;

	private String srvNum = null;
	private String srvType = null;
	private String datCd = null;
	private String tariff = null;
	private String duplexNum = null;
	private String pendingOcid = null;
	private String pendingOcSrd = null;
	private String eyeGrpId = null;

	private List<OrderAccountDTO> accountList = null;
	private AddressDetailProfileLtsDTO address = null;

	
	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getSrvType() {
		return srvType;
	}

	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}

	public String getDatCd() {
		return datCd;
	}

	public void setDatCd(String datCd) {
		this.datCd = datCd;
	}

	public String getTariff() {
		return tariff;
	}

	public void setTariff(String tariff) {
		this.tariff = tariff;
	}

	public String getDuplexNum() {
		return duplexNum;
	}

	public void setDuplexNum(String duplexNum) {
		this.duplexNum = duplexNum;
	}

	public String getPendingOcid() {
		return pendingOcid;
	}

	public void setPendingOcid(String pendingOcid) {
		this.pendingOcid = pendingOcid;
	}

	public String getPendingOcSrd() {
		return pendingOcSrd;
	}

	public void setPendingOcSrd(String pendingOcSrd) {
		this.pendingOcSrd = pendingOcSrd;
	}

	public String getEyeGrpId() {
		return eyeGrpId;
	}

	public void setEyeGrpId(String eyeGrpId) {
		this.eyeGrpId = eyeGrpId;
	}

	public OrderAccountDTO[] getAccounts() {
		
		if (this.accountList == null || this.accountList.size() == 0) {
			return null;
		}
		return this.accountList.toArray(new OrderAccountDTO[this.accountList.size()]);
	}

	public void setAccounts(OrderAccountDTO[] accounts) {
		
		if (ArrayUtils.isEmpty(accounts)) {
			return;
		}
		this.accountList = Arrays.asList(accounts);
	}
	
	public void appendAccount(OrderAccountDTO pAcct) {
		
		if (this.accountList == null) {
			this.accountList = new ArrayList<OrderAccountDTO>();
		}
		this.accountList.add(pAcct);
	}

	public AddressDetailProfileLtsDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDetailProfileLtsDTO address) {
		this.address = address;
	}
}
