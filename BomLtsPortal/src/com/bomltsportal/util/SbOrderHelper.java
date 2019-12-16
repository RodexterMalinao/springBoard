package com.bomltsportal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class SbOrderHelper {
	public static final ServiceDetailDTO getEyeServiceDetail(ServiceDetailDTO[] srvDtls){
		for(ServiceDetailDTO srvDtl: srvDtls){
			if(BomLtsConstant.SERVICE_TYPE_TEL.equals(srvDtl.getTypeOfSrv())){
				return srvDtl;
			}
		}
		return null;
	}
	
	public static final ServiceDetailDTO getImsServiceDetail(ServiceDetailDTO[] srvDtls){
		for(ServiceDetailDTO srvDtl: srvDtls){
			if(BomLtsConstant.SERVICE_TYPE_IMS.equals(srvDtl.getTypeOfSrv())){
				return srvDtl;
			}
		}
		return null;
	}
	
//	public static ServiceDetailLtsDTO getUpgradeService(SbOrderDTO sbOrder) {
//		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
//			if (isUpgradeService(serviceDetail)) {
//				return (ServiceDetailLtsDTO)serviceDetail;
//			}
//		}
//		return null;
//	}
//	
//	public static boolean isUpgradeService(ServiceDetailDTO serviceDetail) {
//		if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
//			return false;
//		}
//		if (StringUtils.equals(LtsBackendConstant.SRV_ACTION_TYPE_CD_DEL_EYEX_SIP, serviceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsBackendConstant.SRV_ACTION_TYPE_CD_DEL_EYEX_PE, serviceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsBackendConstant.SRV_ACTION_TYPE_CD_DNX_EYEX_SIP, serviceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsBackendConstant.SRV_ACTION_TYPE_CD_DNX_EYEX_PE, serviceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsBackendConstant.SRV_ACTION_TYPE_CD_DNY_EYEX_SIP, serviceDetail.getSrvTypeCdAction())
//				|| StringUtils.equals(LtsBackendConstant.SRV_ACTION_TYPE_CD_DNY_EYEX_PE, serviceDetail.getSrvTypeCdAction())
//				|| StringUtils.indexOf(serviceDetail.getSrvTypeCdAction(), "NEW") == 0) {
//			return true;
//		}
//		return false;
//	}
	
	public static boolean validateEmail(String val) {

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(val);
		if (matcher.matches()) {
			return true;
		} else {
			// this.setMsg("E-mail validate fail");
			return false;
		}
	}
}
