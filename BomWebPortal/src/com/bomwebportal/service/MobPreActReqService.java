package com.bomwebportal.service;

import com.bomwebportal.dto.ShopDTO;
import com.bomwebportal.dto.report.MobPreActReqDTO;

public interface MobPreActReqService {
	public String generatePreActivationCode();
	public MobPreActReqDTO getProcessActivationLetterDetail(String msisdn);
	public ShopDTO getShopDetail(String shopCd);
}
