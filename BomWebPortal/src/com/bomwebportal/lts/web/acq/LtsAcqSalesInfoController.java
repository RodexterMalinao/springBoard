package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.acq.LtsRefereeSaleDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheServiceImpl;
import com.google.common.collect.Lists;

public class LtsAcqSalesInfoController extends SimpleFormController{

    protected final Log log = LogFactory.getLog(getClass());
    
    private LtsSalesInfoService ltsSalesInfoService;
    private CodeLkupCacheServiceImpl svcDnisCodeLkupCacheService;

	private Locale locale;
	private MessageSource messageSource;
	
	private final String nextView = "ltsacqpersonalinfo.html";
    
	public LtsAcqSalesInfoController(){
		this.setFormView("lts/acq/ltsacqsalesinfo");	
		this.setCommandName("ltssalesinfo");
		this.setCommandClass(LtsAcqSalesInfoFormDTO.class);
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
//		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
//		if (orderCaptureDTO == null) {
//			return new ModelAndView(LtsConstant.ERROR_VIEW);
//		}
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		setLocale(RequestContextUtils.getLocale(request));
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqSalesInfoFormDTO ltsAcqSalesInfoFormDTO = orderCaptureDTO.getLtsAcqSalesInfoForm();
		
		if(ltsAcqSalesInfoFormDTO == null){
			
			ltsAcqSalesInfoFormDTO = new LtsAcqSalesInfoFormDTO();
			
			String[] staff = ltsSalesInfoService.getOrgStaffId(bomSalesUserDTO.getUsername());
			String code = bomSalesUserDTO.getShopCd();
			if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS)
					|| bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER)
					|| orderCaptureDTO.isChannelDirectSales()){
				code = bomSalesUserDTO.getChannelCd();
			}
			String[] shopInfo = ltsSalesInfoService.getShopContactAndChannel(code);
			ltsAcqSalesInfoFormDTO.setSalesChannel(shopInfo[0]);
			if(orderCaptureDTO.isChannelDirectSales()){
				String contactNum = ltsSalesInfoService.getSalesContact(staff[0]);
				ltsAcqSalesInfoFormDTO.setSalesContact(contactNum);
			}else{
				ltsAcqSalesInfoFormDTO.setSalesContact(StringUtils.isBlank(shopInfo[1])? "1000" : shopInfo[1]);
			}
			
			if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER))
			{
				ltsAcqSalesInfoFormDTO.setSalesContact("");
			}
			
			ltsAcqSalesInfoFormDTO.setStaffName(staff[1]);
			ltsAcqSalesInfoFormDTO.setStaffId(staff[0]);
			ltsAcqSalesInfoFormDTO.setSalesCode(bomSalesUserDTO.getSalesCd());
			ltsAcqSalesInfoFormDTO.setSalesTeam(bomSalesUserDTO.getShopCd());
			ltsAcqSalesInfoFormDTO.setSalesCenter(bomSalesUserDTO.getAreaCd());
			
			//Modified by Max.R.Meng
			ltsAcqSalesInfoFormDTO.setSourceCode(ltsSalesInfoService.getSalesInfo(staff[0]) != null ? 
					ltsSalesInfoService.getSalesInfo(staff[0]).getSourceCode():null );					
			
			if(orderCaptureDTO.getSbOrder() != null && StringUtils.isNotBlank(orderCaptureDTO.getSbOrder().getOrderId())){
				LtsRefereeSaleDTO refSales = ltsSalesInfoService.getRefSalesInfo(orderCaptureDTO.getSbOrder().getOrderId());
				if(refSales != null){
					ltsAcqSalesInfoFormDTO.setRefStaffId(refSales.getRefSalesCode());
					ltsAcqSalesInfoFormDTO.setRefStaffName(refSales.getRefSalesName());
					ltsAcqSalesInfoFormDTO.setRefSalesTeam(refSales.getRefSalesTeam());
					ltsAcqSalesInfoFormDTO.setRefSalesCenter(refSales.getRefSalesCentre());
				}
			}			
		}

		
		ltsAcqSalesInfoFormDTO.setCallCenter(false);
		ltsAcqSalesInfoFormDTO.setPremier(false);
		ltsAcqSalesInfoFormDTO.setDirectSales(false);
		ltsAcqSalesInfoFormDTO.setModifySalesInfo(true);
		ltsAcqSalesInfoFormDTO.setVaildateMobileNo(false);
		if((StringUtils.isNotBlank(bomSalesUserDTO.getCategory()) && StringUtils.equals(bomSalesUserDTO.getCategory(), LtsConstant.SALES_ROLE_FRONTLINE))
				&& (bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_DIRECT_SALES) || bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_DIRECT_SALES_NOW_TV_QA))
				&& !StringUtils.equals(bomSalesUserDTO.getChannelCd(), LtsConstant.CHANNEL_CD_DIRECT_SALES_AGENT_FRONTLINE)){
			ltsAcqSalesInfoFormDTO.setModifySalesInfo(false);
		}
		if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS)){
			ltsAcqSalesInfoFormDTO.setImsApplicationMethod(LtsConstant.IMS_APPLICATION_METHOD_CALL_CENTER);
			ltsAcqSalesInfoFormDTO.setCallCenter(true);
		}else if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER)){
			ltsAcqSalesInfoFormDTO.setImsApplicationMethod(LtsConstant.IMS_APPLICATION_METHOD_PREMIER);
			ltsAcqSalesInfoFormDTO.setPremier(true);
		}else if(orderCaptureDTO.isChannelDirectSales()){
			ltsAcqSalesInfoFormDTO.setDirectSales(true);
		}
		
		if(orderCaptureDTO.isChannelDirectSales()){
			ltsAcqSalesInfoFormDTO.setVaildateMobileNo(true);
		}
		
		if(ltsAcqSalesInfoFormDTO.isCallCenter() || ltsAcqSalesInfoFormDTO.isPremier()){
			if(StringUtils.isBlank(ltsAcqSalesInfoFormDTO.getDate())
					&& StringUtils.isBlank(ltsAcqSalesInfoFormDTO.getTime())){
				String[] dateTime = LtsDateFormatHelper.getSysDate().split(" ");
				if(dateTime.length == 2){
					ltsAcqSalesInfoFormDTO.setDate(dateTime[0]);
					ltsAcqSalesInfoFormDTO.setTime(dateTime[1].substring(0, 5));
				}
			}
		}
		
		if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_RETAIL) || ltsAcqSalesInfoFormDTO.isDirectSales()){	// Referee Staff does not apply to Retail
			ltsAcqSalesInfoFormDTO.setDisplayReferee(false);
		}
		else{
			ltsAcqSalesInfoFormDTO.setDisplayReferee(true);
		}
		
		orderCaptureDTO.setLtsAcqSalesInfoForm(ltsAcqSalesInfoFormDTO);
		return ltsAcqSalesInfoFormDTO;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqSalesInfoFormDTO ltsAcqSalesInfoFormDTO = (LtsAcqSalesInfoFormDTO)command;
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}

		if(ltsAcqSalesInfoFormDTO.isCallCenter() || ltsAcqSalesInfoFormDTO.isPremier()){
			ltsAcqSalesInfoFormDTO.setSalesChannel(ltsAcqSalesInfoFormDTO.getImsApplicationMethod());
			ltsAcqSalesInfoFormDTO.setSalesTeam(ltsAcqSalesInfoFormDTO.getImsSource());
		}
		
		orderCaptureDTO.setLtsAcqSalesInfoForm(ltsAcqSalesInfoFormDTO);
		return new ModelAndView(new RedirectView(nextView));
		
	}
	
	protected Map<String, List> referenceData(HttpServletRequest request) throws Exception {
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		Map<String, List> referenceData = new HashMap<String, List>();
		referenceData.put("imsApplMthds", Lists.newArrayList(ltsSalesInfoService.getImsApplMthdLkup()));

		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqSalesInfoFormDTO ltsSalesInfoFormDTO = orderCaptureDTO.getLtsAcqSalesInfoForm();

		String imsApplMthd = ltsSalesInfoFormDTO == null? null: ltsSalesInfoFormDTO.getImsApplicationMethod();
		if(imsApplMthd == null){
			if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_CS)){
				imsApplMthd = LtsConstant.IMS_APPLICATION_METHOD_CALL_CENTER;
			}else if(bomSalesUserDTO.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_PREMIER)){
				imsApplMthd = LtsConstant.IMS_APPLICATION_METHOD_PREMIER;
			}
		}
		
		referenceData.put("defaultImsSources", ltsSalesInfoService.getImsApplSourceByMthdDesc(imsApplMthd));
		referenceData.put("svcDnisList", Arrays.asList(svcDnisCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		
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

	public CodeLkupCacheServiceImpl getSvcDnisCodeLkupCacheService() {
		return svcDnisCodeLkupCacheService;
	}

	public void setSvcDnisCodeLkupCacheService(
			CodeLkupCacheServiceImpl svcDnisCodeLkupCacheService) {
		this.svcDnisCodeLkupCacheService = svcDnisCodeLkupCacheService;
	}
	
    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
