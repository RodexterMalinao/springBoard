package com.bomwebportal.mob.ccs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.Utility;

public class MobCcsBasketCompareDetailController implements Controller {
	private MobileDetailService mobileDetailService;
	private VasDetailService vasDetailService;

	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}

	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobccsbasketcomparedetail");
		String[] basketIds = request.getParameterValues("basketIds");
		String locale = RequestContextUtils.getLocale(request).toString();
		String appDate=Utility.date2String(new Date(), "dd/MM/yyyy");
		Map<String, Map<String, Object>> records = new LinkedHashMap<String, Map<String, Object>>();
		if (basketIds != null) {
			int count = 0;
			final int basketCountLimit = 3;
			for (String basketId : basketIds) {
				count++;
				if (count > basketCountLimit) {
					break;
				}
				Map<String, Object> detail = new HashMap<String, Object>();

				ModelDTO mobileDetail = this.mobileDetailService.getMobileDetail(locale, basketId);
				
				String channelCd = "";
				SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
				if (superOrderDto != null) {
					channelCd = superOrderDto.getOrderShopCd();
				} else {
					BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
					channelCd = user.getChannelCd();
				}
				
				CustomerProfileDTO sessionCustomer=(CustomerProfileDTO) request.getSession().getAttribute("customer");

				// MIP.P4 modification
				String nature = vasDetailService.getBasketAttribute(basketId, appDate).getNature();
				List<VasDetailDTO> vasHSRPList = vasDetailService.getRPHSRPList(locale, basketId, appDate, channelCd, sessionCustomer.getBrand(), sessionCustomer.getSimType(), nature);
			
				//List<VasDetailDTO> vasBFEEList = vasDetailService.getBFEEList(locale, basketId);
				String basketDisplayTitle = vasDetailService.getBasketDisplayTitle(locale, basketId);
				
				BasketDTO basketDto = vasDetailService.getBasketAttribute(basketId, appDate);
				detail.put("mobileDetail", mobileDetail);
				detail.put("vasHSRPList", vasHSRPList);
				//detail.put("vasBFEEList", vasBFEEList);
				detail.put("basketDisplayTitle", basketDisplayTitle);
				detail.put("basket", basketDto);

				records.put(basketId, detail);
			}
		}
		modelAndView.addObject("records", records);
		return modelAndView;
	}

}
