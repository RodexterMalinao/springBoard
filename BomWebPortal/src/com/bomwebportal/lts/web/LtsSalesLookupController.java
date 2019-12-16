package com.bomwebportal.lts.web;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsSalesLookupController implements Controller {

    private LtsSalesInfoService ltsSalesInfoService;
	private Locale locale;
	private MessageSource messageSource;
    
	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String type = request.getParameter("T");
		JSONObject jsonObject = new JSONObject();
		String parameter = request.getParameter("parm");
		LtsSalesInfoFormDTO salesInfo = null;
		setLocale(RequestContextUtils.getLocale(request));
		
		if(StringUtils.equals(type, "IMS_SOURCE")){
			List<String> sourceList = ltsSalesInfoService.getImsApplSourceByMthdDesc(parameter);
			if(sourceList != null && sourceList.size() > 0){
				JSONArray jsonArray = new JSONArray();
				jsonArray.addAll(sourceList);
				return new ModelAndView("ajax_view", "jsonArray", jsonArray);
			}else{
				jsonObject.put("state", 0);
			}
		}
		else if (StringUtils.equals(type, "REFEREE")){
			salesInfo = ltsSalesInfoService.getSalesInfo(parameter);
			if (salesInfo != null){
					jsonObject.put("state", 1);
					jsonObject.put("refereeName", salesInfo.getStaffName());
					jsonObject.put("refereeId", salesInfo.getStaffId());
					jsonObject.put("refereeSalesTeam", salesInfo.getSalesTeam());
					jsonObject.put("refereeSalesCenter", salesInfo.getRefereeSalesCenter());
			}else{
				jsonObject.put("state", 0);
				jsonObject.put("errorMsg", messageSource.getMessage("lts.ltsSalesLkup.refSalesId", new Object[] {}, this.locale));
			}
		}
		else{
			BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			
			parameter = StringUtils.upperCase(parameter);
			
			if(bomSalesUserDTO != null && StringUtils.equals(parameter, bomSalesUserDTO.getUsername())){
				salesInfo = new LtsSalesInfoFormDTO();
				salesInfo.setStaffId(bomSalesUserDTO.getUsername());
				salesInfo.setStaffName(bomSalesUserDTO.getStaffName());
				salesInfo.setSalesCode(bomSalesUserDTO.getSalesCd());
				salesInfo.setSalesChannel(bomSalesUserDTO.getChannelCd());
				salesInfo.setBoc(bomSalesUserDTO.getBoc());
				salesInfo.setSalesCenter(bomSalesUserDTO.getAreaCd());
//				salesInfo.setSalesContact(ltsSalesInfoService.getShopContact(bomSalesUserDTO.getShopCd()));
//				salesInfo.setSalesTeam(bomSalesUserDTO.getShopCd());
			}else{
				salesInfo = ltsSalesInfoService.getSalesInfo(parameter);
			}
			
			if (salesInfo != null){
//				if(StringUtils.isBlank(salesInfo.getBoc())){
//					jsonObject.put("state", 0);
//					jsonObject.put("errorMsg", "Salesman does not have BOC, cannot issue Springboard order!");
//				}else 
				if((bomSalesUserDTO.getChannelId() == 3	&& salesInfo.isPremier())
						|| StringUtils.equals(salesInfo.getSalesChannel(), bomSalesUserDTO.getChannelCd())){
					jsonObject.put("state", 1);
					jsonObject.put("staffName", salesInfo.getStaffName());
					jsonObject.put("salesCode", salesInfo.getSalesCode());
					jsonObject.put("staffId", salesInfo.getStaffId());
					jsonObject.put("boc", salesInfo.getBoc());
					jsonObject.put("salesTeam", salesInfo.getSalesTeam());
					jsonObject.put("salesCenter", salesInfo.getSalesCenter());
					if(LtsSessionHelper.isChannelDirectSales(bomSalesUserDTO.getChannelId())){
						jsonObject.put("salesContact", ltsSalesInfoService.getShopContactAndChannel(salesInfo.getSalesChannel())[1]);
					}
				}else{
					jsonObject.put("state", 0);
					jsonObject.put("errorMsg", messageSource.getMessage("lts.ltsSalesLkup.staffIdSameCha", new Object[] {}, this.locale));
				}
//				jsonObject.put("salesTeam", salesInfo.getSalesTeam());
//				jsonObject.put("salesChannel", salesInfo.getSalesChannel());
//				jsonObject.put("salesContact", salesInfo.getSalesContact());
			}else{
				jsonObject.put("state", 0);
				jsonObject.put("errorMsg", messageSource.getMessage("lts.ltsSalesLkup.staffIdNotFound", new Object[] {}, this.locale));
			}
			
		}
		

		return new ModelAndView("ajax_ltssaleslookup", jsonObject);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
