package com.bomwebportal.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class VasMrtSelectionValidator implements Validator {


	
	//Alternate Approach  using StringUtils.isAlphanumeric("abcde")
	//import org.apache.commons.lang.StringUtils;
	//method:  isAlphanumeric(String str) and isEmpty(String str)
	//Reference: URL:http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/index.html?org/apache/commons/lang/StringUtils.html

	public boolean supports(Class clazz) {
		
		return clazz.equals(VasMrtSelectionDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		VasMrtSelectionDTO vasMrtSelection = (VasMrtSelectionDTO) obj;
		HttpServletRequest request = (HttpServletRequest) vasMrtSelection.getValue("request");

		if ("EX06".equals(vasMrtSelection.getFuncId())) {

			BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			String appMode = (String) request.getSession().getAttribute("appMode");
			// two session for mob/mobccs
			CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
					.getSession().getAttribute("customer");

			if ("mobccs".equals(appMode)) {
				sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil
						.getSession(request, "customer");
			}
			MnpDTO mnpDTO = (MnpDTO) vasMrtSelection.getSession().getAttribute("MNP");

			if (!"2".equals(user.getChannelId()) && mnpDTO != null && mnpDTO.isFutherMnp()
					&& "Y".equalsIgnoreCase(vasMrtSelection.getChinaInd())) {
				errors.rejectValue("msisdn", "furtherChina.invalid", "Not allow to select China Mobile Number for Further MNP");
			} else if (StringUtils.isEmpty(vasMrtSelection.getMsisdn())) {
				errors.rejectValue("msisdn", "ccsMobMsisdn.required");
			} else {
				if (user != null) {
					if ("mobccs".equals(appMode)) {

						/*if (StringUtils.isEmpty(vasMrtSelection.getChannelCd())
								|| !vasMrtSelection.getChannelCd().equals(user.getChannelCd())) {
							errors.rejectValue("channelCd", "msisdnChannelCd.invalid");
						}*/
					} else {
						/*if (StringUtils.isEmpty(vasMrtSelection.getShopCd())
								|| !vasMrtSelection.getShopCd().equals(user.getBomShopCd())) {
							errors.rejectValue("shopCd", "shopCd.1C2N.invalid");
						}*/
					}
				}

				if (StringUtils.isEmpty(vasMrtSelection.getMsisdnStatus())
						|| !"2".equals(vasMrtSelection.getMsisdnStatus())) {
					errors.rejectValue("msisdnStatus", "msisdnStatus.1C2N.invalid");
				}

				if (StringUtils.isEmpty(vasMrtSelection.getNumType())
						|| !vasMrtSelection.getNumType().equals(sessionCustomer.getNumType())) {
					errors.rejectValue("numType", "numType.1C2N.invalid");
				}

			}
		} else if ("EX07".equals(vasMrtSelection.getFuncId())) {

			BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			String appMode = (String) request.getSession().getAttribute("appMode");
			// two session for mob/mobccs
			CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
					.getSession().getAttribute("customer");

			if ("mobccs".equals(appMode)) {
				sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil
						.getSession(request, "customer");
			}			

			
			if (StringUtils.isEmpty(vasMrtSelection.getMsisdn())) {
				errors.rejectValue("msisdn", "ccsMobMsisdn.required");
			} else {
				if (user != null) {
					if ("mobccs".equals(appMode)) {
						
/*						if (StringUtils.isEmpty(vasMrtSelection.getChannelCd())
								|| !vasMrtSelection.getShopCd().equals(user.getBomShopCd())) {
							errors.rejectValue("shopCd", "shopCd.1C2N.invalid");
						}*/
					} else {
						/*if (StringUtils.isEmpty(vasMrtSelection.getShopCd())
								|| !vasMrtSelection.getShopCd().equals(user.getBomShopCd())) {
							errors.rejectValue("shopCd", "shopCd.1C2N.invalid");
						}*/
					}
				}

				if (StringUtils.isEmpty(vasMrtSelection.getMsisdnStatus())
						|| !"2".equals(vasMrtSelection.getMsisdnStatus())) {
					errors.rejectValue("msisdnStatus", "msisdnStatus.1C2N.invalid");
				}

				if (StringUtils.isEmpty(vasMrtSelection.getNumType())
						|| !vasMrtSelection.getNumType().equals(sessionCustomer.getNumType())) {
					errors.rejectValue("numType", "numType.1C2N.invalid");
				}

			}
		}

	}
}