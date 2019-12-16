package com.bomwebportal.lts.service;

public interface PaymentService {

	public abstract String getBankNameByCode(String pBankCd);

	public abstract String getBranchNameByCode(String pBankCd, String pBranchCd);

}