package com.bomwebportal.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AddressAreaDTO;
import com.bomwebportal.dto.AddressCategoryDTO;
import com.bomwebportal.dto.AddressDistrictDTO;
import com.bomwebportal.dto.AddressSectionDTO;
import com.bomwebportal.mob.ccs.dto.BomAddressDtlDTO;
import com.bomwebportal.service.CustomerProfileService;

public class BomAddressDtlController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustomerProfileService customerProfileService;

	public CustomerProfileService getCustomerProfileService() {
	    return customerProfileService;
	}

	public void setCustomerProfileService(
		CustomerProfileService customerProfileService) {
	    this.customerProfileService = customerProfileService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.info("BomAddressDtlController is called");
		
		String idDocType = request.getParameter("idDocType");
		String idDocNum = request.getParameter("idDocNum");

		BomAddressDtlDTO bomAddr = customerProfileService.getBomAddrDtl(idDocType, idDocNum);
		if (bomAddr == null) {
			return new ModelAndView("ajax_bomAddrDtl", "bomAddr", null);
		}
		
		if (StringUtils.isNotBlank(bomAddr.getStCatgCd())) {
			for (AddressCategoryDTO catgDTO : LookupTableBean.getInstance().getAddressCategoryList()) {
				if (catgDTO.getCategoryCode().equalsIgnoreCase(bomAddr.getStCatgCd())) {
					bomAddr.setStCatgDesc(catgDTO.getCategoryDesc());
				}
			}
		}
		
		if (StringUtils.isNotBlank(bomAddr.getSectCd())) {
			for (AddressSectionDTO sectionDTO : LookupTableBean.getInstance().getAddressSectionList()) {
				if (sectionDTO.getSectionCode().equalsIgnoreCase(bomAddr.getSectCd())) {
					bomAddr.setSectDesc(sectionDTO.getSectionDescription());
				}
			}
		}
		
		if (StringUtils.isNotBlank(bomAddr.getDistrNum())) {
			for (AddressDistrictDTO distDTO : LookupTableBean.getInstance().getAddressDistrictList()) {
				if (distDTO.getDistrictCode().equalsIgnoreCase(bomAddr.getDistrNum())) {
					bomAddr.setDistrDesc(distDTO.getDistrictDescription());
					bomAddr.setAreaCd(distDTO.getAreaCode());
				}
			}
		}
		
		if (StringUtils.isNotBlank(bomAddr.getAreaCd())) {
			for (AddressAreaDTO areaDTO : LookupTableBean.getInstance().getAddressAreaList()) {
				if (areaDTO.getAreaCode().equalsIgnoreCase(bomAddr.getAreaCd())) {
					bomAddr.setAreaDesc(areaDTO.getAreaDescription());
				}
			}
		}
		return new ModelAndView("ajax_bomAddrDtl", "bomAddr", bomAddr);
	}
	
}
