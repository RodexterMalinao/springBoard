package com.bomwebportal.lts.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.form.LtsCcOrderSearchFormDTO;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsSimpleOrderSearchController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());

	private final String viewName = "ltssimpleordersearch";
	private final String commandName = "ltsCcOrderSearchCmd";
	
	private LtsSalesInfoService ltsSalesInfoService;
	private CodeLkupCacheService sbOrdStatusLkupCacheService;
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		LtsCcOrderSearchFormDTO form = (LtsCcOrderSearchFormDTO)command;
		
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		//form.setStaffNum(bomSalesUserDTO.getUsername());
		
		if ("SEARCH".equals(form.getAction())) {
			validateSearchInput(form, errors);
			if(!errors.hasErrors()){
				request.getSession().setAttribute(commandName, form);
			}
		}
		
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		
		ModelAndView modelAndView = new ModelAndView(viewName, model);
		return modelAndView;	
	}
	
	private void validateSearchInput(LtsCcOrderSearchFormDTO searchUI, BindException errors){
		if(StringUtils.isBlank(searchUI.getOrderId())
				&& StringUtils.isBlank(searchUI.getServiceNumber())
				&& StringUtils.isBlank(searchUI.getContactEmail())
				&& StringUtils.isBlank(searchUI.getIdDocNum())){
			errors.reject("lts.simpleOrderSearch.input.required");
		}
		
		
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		request.getSession().removeAttribute(commandName);
		return new LtsCcOrderSearchFormDTO();
	}
	
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		Map<String,String> idDocTypeList = new LinkedHashMap<String,String>();
		idDocTypeList.put("HKID", "HKID");
		idDocTypeList.put("PASS", "Passport");
		idDocTypeList.put("BS", "BR");
		
		referenceData.put("idDocTypeList", idDocTypeList);
//		referenceData.put("sbOrdStatusList", sbOrdStatusLkupCacheService.getCodeLkupDAO().getCodeLkup());

//		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
//		List<String> teamCodeList = ltsSalesInfoService.getTeamCdListByChannelCd(bomSalesUserDTO.getChannelCd());
//		referenceData.put("teamCodeList", teamCodeList);
//		referenceData.put("channelCd", bomSalesUserDTO.getChannelCd());
		return referenceData;
	}
	

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}


	public CodeLkupCacheService getSbOrdStatusLkupCacheService() {
		return sbOrdStatusLkupCacheService;
	}


	public void setSbOrdStatusLkupCacheService(
			CodeLkupCacheService sbOrdStatusLkupCacheService) {
		this.sbOrdStatusLkupCacheService = sbOrdStatusLkupCacheService;
	}
}
