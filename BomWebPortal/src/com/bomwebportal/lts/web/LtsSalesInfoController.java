package com.bomwebportal.lts.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.google.common.collect.Lists;

public class LtsSalesInfoController extends SimpleFormController{

    protected final Log log = LogFactory.getLog(getClass());
    
    private LtsSalesInfoService ltsSalesInfoService;

	private final String nextView = "ltspayment.html";
    

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request);
		LtsSalesInfoFormDTO ltsSalesInfoFormDTO = orderCaptureDTO.getLtsSalesInfoForm();
		if(ltsSalesInfoFormDTO == null){
			if(bomSalesUserDTO == null){
				return new LtsSalesInfoFormDTO();
			}
			
//			ltsSalesInfoFormDTO = ltsSalesInfoService.getSalesInfo(bomSalesUserDTO.getUsername(), null);
//			if(ltsSalesInfoFormDTO == null){
//				ltsSalesInfoFormDTO = new LtsSalesInfoFormDTO();
//			}
			
			ltsSalesInfoFormDTO = new LtsSalesInfoFormDTO();
			String[] staff = ltsSalesInfoService.getOrgStaffId(bomSalesUserDTO.getUsername());
			String code = bomSalesUserDTO.getShopCd();
			if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS)
					|| bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER)){
				code = bomSalesUserDTO.getChannelCd();
			}
			String[] shopInfo = ltsSalesInfoService.getShopContactAndChannel(code);
			ltsSalesInfoFormDTO.setSalesChannel(shopInfo[0]);
			ltsSalesInfoFormDTO.setSalesContact(StringUtils.isBlank(shopInfo[1])? "1000" : shopInfo[1]);
			ltsSalesInfoFormDTO.setStaffName(staff[1]);
			ltsSalesInfoFormDTO.setStaffId(staff[0]);
			ltsSalesInfoFormDTO.setSalesCode(bomSalesUserDTO.getSalesCd());
			ltsSalesInfoFormDTO.setSalesTeam(bomSalesUserDTO.getShopCd());
			ltsSalesInfoFormDTO.setSalesCenter(bomSalesUserDTO.getAreaCd());
			
			if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER))
			{
				ltsSalesInfoFormDTO.setSalesContact("");
			}
			
			//Modified by Max.R.Meng
			ltsSalesInfoFormDTO.setSourceCode(ltsSalesInfoService.getSalesInfo(staff[0]) != null ? 
					ltsSalesInfoService.getSalesInfo(staff[0]).getSourceCode():null );					
		}

		
		ltsSalesInfoFormDTO.setCallCenter(false);
		ltsSalesInfoFormDTO.setPremier(false);
		if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS)){
			ltsSalesInfoFormDTO.setImsApplicationMethod(LtsConstant.IMS_APPLICATION_METHOD_CALL_CENTER);
			ltsSalesInfoFormDTO.setCallCenter(true);
		}else if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER)){
			ltsSalesInfoFormDTO.setImsApplicationMethod(LtsConstant.IMS_APPLICATION_METHOD_PREMIER);
			ltsSalesInfoFormDTO.setPremier(true);
		}
		
		if(ltsSalesInfoFormDTO.isCallCenter() || ltsSalesInfoFormDTO.isPremier()){
			if(StringUtils.isBlank(ltsSalesInfoFormDTO.getDate())
					&& StringUtils.isBlank(ltsSalesInfoFormDTO.getTime())){
				String[] dateTime = LtsDateFormatHelper.getSysDate().split(" ");
				if(dateTime.length == 2){
					ltsSalesInfoFormDTO.setDate(dateTime[0]);
					ltsSalesInfoFormDTO.setTime(dateTime[1].substring(0, 5));
				}
			}
		}
		
		orderCaptureDTO.setLtsSalesInfoForm(ltsSalesInfoFormDTO);
		return ltsSalesInfoFormDTO;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request);
		LtsSalesInfoFormDTO ltsSalesInfoFormDTO = (LtsSalesInfoFormDTO)command;
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		orderCaptureDTO.setLtsSalesInfoForm(ltsSalesInfoFormDTO);
		
		if(ltsSalesInfoFormDTO.isCallCenter() || ltsSalesInfoFormDTO.isPremier()){
			ltsSalesInfoFormDTO.setSalesChannel(ltsSalesInfoFormDTO.getImsApplicationMethod());
			ltsSalesInfoFormDTO.setSalesTeam(ltsSalesInfoFormDTO.getImsSource());
		}
		return new ModelAndView(new RedirectView(nextView));
		
	}
	
	protected Map<String, List> referenceData(HttpServletRequest request) throws Exception {
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		Map<String, List> referenceData = new HashMap<String, List>();
		referenceData.put("imsApplMthds", Lists.newArrayList(ltsSalesInfoService.getImsApplMthdLkup()));

		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request);
		LtsSalesInfoFormDTO ltsSalesInfoFormDTO = orderCaptureDTO.getLtsSalesInfoForm();
		String imsApplMthd = ltsSalesInfoFormDTO == null? null: ltsSalesInfoFormDTO.getImsApplicationMethod();
		if(imsApplMthd == null){
			if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS)){
				imsApplMthd = LtsConstant.IMS_APPLICATION_METHOD_CALL_CENTER;
			}else if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER)){
				imsApplMthd = LtsConstant.IMS_APPLICATION_METHOD_PREMIER;
			}
		}
		referenceData.put("defaultImsSources", ltsSalesInfoService.getImsApplSourceByMthdDesc(imsApplMthd));
		return referenceData;
	}
	
	public String getKeyByDesc(LookupItemDTO[] lkupItems, String desc){
		for(LookupItemDTO lkupItem: lkupItems){
			if(StringUtils.equals((String)(lkupItem.getItemValue()), desc)){
				return (String)(lkupItem.getItemValue());
			}
		}
		return null;
	}

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}
}
