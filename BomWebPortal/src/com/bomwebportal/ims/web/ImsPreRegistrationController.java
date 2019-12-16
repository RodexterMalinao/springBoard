package com.bomwebportal.ims.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsAddressInfoDAO;
import com.bomwebportal.ims.dao.PageTrackDAO;

import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.dto.OnlineSalesRequestDTO;
import com.bomwebportal.dto.DisplayPreRegDto;
import com.bomwebportal.ims.dto.ui.PreRegistrationUI;
import com.bomwebportal.ims.dto.ui.PreRegistrationUI.Action;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.service.ImsCommonService;
import com.google.gson.Gson;
import com.pccw.util.spring.SpringApplicationContext;

public class ImsPreRegistrationController  extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	private Gson gson = new Gson();

	private ImsAddressInfoDAO imsAddressInfoDao;
	private ConstantLkupService constantLkupService;
    private ImsCommonService imsCommonService;

	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}


	public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	} 

	public Object formBackingObject(HttpServletRequest request)
	throws Exception {
		PreRegistrationUI preRegui = new PreRegistrationUI();
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		Action action = Action.SEARCH;
		try {
			action = Action.valueOf(request.getParameter("action"));
		} catch (Exception e) {}
		preRegui.setAction(action);
		
		try {

			logger.info("RegPreRegistration formBackingObject is called");

			String floorNo = request.getParameter("floor");
			String flat = request.getParameter("flat");
			String sb_no = request.getParameter("sb_no");
//			String bw = request.getParameter("bw");
			
			Locale locale = RequestContextUtils.getLocale(request);
			request.setAttribute("lang", locale.toString().toUpperCase());

			String lang_preference = locale.toString().toUpperCase();
			logger.debug("order getPreRegDetails :"+gson.toJson(order));
			logger.debug("getPreRegRecords ui:"+preRegui.getSearched()+" "+gson.toJson(preRegui));
			if(order.getPreRegInd()!=null && order.getPreRegInd().equals("Y") &&
					order.getPreRegDetails()!=null ){
//				preRegui.setSearched("Y");
				preRegui.setSubmitted("Y");
				preRegui.setOnlineSalesReq(order.getPreRegDetails());
			}else{
			
				if(StringUtils.isBlank(sb_no))
					sb_no = order.getInstallAddress().getSerbdyno();	
					
				if(StringUtils.isBlank(floorNo))
					floorNo = order.getInstallAddress().getFloorNo();
				
				if(StringUtils.isBlank(flat))
					flat = order.getInstallAddress().getUnitNo();	
				
//				preRegui.setSearched("N");
				
			preRegui.setOnlineSalesReq(imsAddressInfoDao.getAddressDtl(sb_no));
			
			if(preRegui.getOnlineSalesReq().getHousing_addr_en()==null || preRegui.getOnlineSalesReq().getHousing_addr_en().isEmpty()){
				preRegui.getOnlineSalesReq().setHousing_addr_en(" ");
			}
			
			if(preRegui.getOnlineSalesReq().getHousing_addr_ch()==null || preRegui.getOnlineSalesReq().getHousing_addr_ch().isEmpty()){
				preRegui.getOnlineSalesReq().setHousing_addr_ch(" ");
			}

			BomSalesUserDTO sessionUser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			
			int channel = sessionUser.getChannelId(); 
			
			preRegui.getOnlineSalesReq().setIs_premier(order.getIsPT().equals("Y")?"Y":"N");
	
			if(StringUtils.isBlank(preRegui.getOnlineSalesReq().getStaff_id())&& !(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES))
				preRegui.getOnlineSalesReq().setStaff_id(sessionUser.getOrgStaffId());
			
//			if(sessionOrder.getIsCC().equals("Y"))
//				channel = "CC";
//			else if(sessionOrder.getIsDS().equals("Y"))
//				channel = "DS";
//			else if(sessionOrder.getIsPT().equals("Y"))
//				channel = "PT";
//			else
//				channel = "RS";
			
			preRegui.getOnlineSalesReq().setLang_preference(lang_preference);
			preRegui.getOnlineSalesReq().setChannel(channel+"");			

//			String addressDisplay = "";
//			if (locale.toString().equalsIgnoreCase("zh")){
//				addressDisplay = sessionOrder.getFullAddrCh();
//				request.setAttribute("pageTrackTag", constantLkupService.getPageTrackTag("zh_HK", "10G_PRE_REGISTRATION"));
//			} else {
//				addressDisplay = sessionOrder.getFullAddrEn();
//				request.setAttribute("pageTrackTag", constantLkupService.getPageTrackTag(locale.toString(), "10G_PRE_REGISTRATION"));
//			}

			preRegui.getOnlineSalesReq().setFlat(flat);
			preRegui.getOnlineSalesReq().setFloor(floorNo);

			preRegui.getOnlineSalesReq().setItem_id("1009");
			preRegui.getOnlineSalesReq().setProcess_status("02");

			preRegui.getOnlineSalesReq().setIs_pon_only("N");
			preRegui.getOnlineSalesReq().setSerbdy_no(sb_no);
//			preRegui.getOnlineSalesReq().setBandwidth(bw);
			}
			preRegui.setSerbdynoAddr(lang_preference.equalsIgnoreCase("zh")?preRegui.getOnlineSalesReq().getAddressCh():preRegui.getOnlineSalesReq().getAddressEn());
			
			preRegui.setInstAddr((StringUtils.isNotBlank(flat)?("Flat "+flat+","):"")+floorNo+"/F,"+preRegui.getSerbdynoAddr());
			
			logger.debug("getPreRegRecords OnlineSalesReq:"+gson.toJson(preRegui));
			
			List<DisplayPreRegDto> preRegRecords = SpringApplicationContext.getBean(ImsCommonService.class).preregSearchSB(null, null, "PONVILL",sb_no,null,null,null);
//			List<PreRegDTO> preRegRecords = preRegistrationService.getPreRegRecords(preRegui.getDocType(), preRegui.getDocNum(), "PONVILL",preRegui.getFirstName(),preRegui.getLastName(),preRegui.getOnlineSalesReq().getSerbdy_no() , null);
			logger.debug("getPreRegRecords output:"+gson.toJson(preRegRecords));
			preRegui.setPreRegSearchList(preRegRecords);
//			preRegui.setSearched("Y");
//			modelAndView.addObject("list", preRegRecords);
			logger.debug("getPreRegRecords ui:"+preRegui.getSearched()+" "+gson.toJson(preRegui.getPreRegSearchList()));

			
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
			
			return preRegui;
		} catch (Exception e) {
			logger.error("", e);
			throw new Exception();
		}
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
	throws Exception {
		ModelAndView modelAndView;
		PreRegistrationUI preRegui = (PreRegistrationUI) command;
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO sessionUser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
//			preRegui.setSearched("Y");
			modelAndView = new ModelAndView(new RedirectView("imspreregistration.html"));

			try {
				logger.info("ImsPreRegistrationController onSubmit called");

				String lang = "";
				Locale locale = RequestContextUtils.getLocale(request);
				if (locale.toString().equalsIgnoreCase("zh")){
					lang = "CHI";
				}else {
					lang = "ENG";
				}

				
				preRegui.getOnlineSalesReq().setRequest_type("SUBSCRIBE");
				logger.info(gson.toJson(preRegui));
				imsCommonService.insertOnlineSalesRequest(preRegui.getOnlineSalesReq(), null, lang, sessionUser.getUsername(), preRegui.getOnlineSalesReq().getStaff_id());
//				preRegistrationService.serviceReg(preRegui.getOnlineSalesReq(), lang);
				order.setPreRegDetails(preRegui.getOnlineSalesReq());
				order.setPreRegSubmitted("Y");
				request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);

			} catch (Exception e) {
				logger.error("", e);
				throw new Exception();
			}
		
		return modelAndView;
	

	}


		public void setImsCommonService(ImsCommonService imsCommonService) {
			this.imsCommonService = imsCommonService;
		}


		public ImsCommonService getImsCommonService() {
			return imsCommonService;
		}


		public void setImsAddressInfoDao(ImsAddressInfoDAO imsAddressInfoDao) {
			this.imsAddressInfoDao = imsAddressInfoDao;
		}


		public ImsAddressInfoDAO getImsAddressInfoDao() {
			return imsAddressInfoDao;
		}

}
