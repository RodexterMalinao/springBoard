package com.bomwebportal.mob.ccs.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MobCcsDoaUpdateMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsDoaUpdateMrtService.AmendScenario;
import com.bomwebportal.mob.ccs.service.MobCcsDoaUpdateMrtService.MnpInd;
import com.bomwebportal.mob.ccs.service.MobCcsOrderSearchService;
import com.bomwebportal.mob.ccs.util.BeanUtilsHelper;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsDoaUpdateMrtController extends SimpleFormController {
	private OrderService orderService;
	private MobCcsDoaUpdateMrtService mobCcsDoaUpdateMrtService;
	private MobCcsOrderSearchService mobCcsOrderSearchService;
	private MobCcsApprovalLogService mobCcsApprovalLogService;
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MobCcsDoaUpdateMrtService getMobCcsDoaUpdateMrtService() {
		return mobCcsDoaUpdateMrtService;
	}

	public void setMobCcsDoaUpdateMrtService(
			MobCcsDoaUpdateMrtService mobCcsDoaUpdateMrtService) {
		this.mobCcsDoaUpdateMrtService = mobCcsDoaUpdateMrtService;
	}

	public MobCcsOrderSearchService getMobCcsOrderSearchService() {
		return mobCcsOrderSearchService;
	}

	public void setMobCcsOrderSearchService(
			MobCcsOrderSearchService mobCcsOrderSearchService) {
		this.mobCcsOrderSearchService = mobCcsOrderSearchService;
	}

	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	@Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException, IllegalAccessException, InvocationTargetException {
		if (logger.isInfoEnabled()) {
			logger.info("MobCcsUpdateAfterDeliveryController formBackingObject called");
		}
		DoaUpdateMrtUI mrtUi = new DoaUpdateMrtUI();
		BeanUtilsHelper.copyProperties(mobCcsDoaUpdateMrtService.getMrtUI(ServletRequestUtils.getRequiredStringParameter(request, "orderId")), mrtUi);
		if ("Prepaid Sim".equals(mrtUi.getCustName())) {
			mrtUi.setPrepaidSimInd(true);
		} else {
			mrtUi.setPrepaidSimInd(false);
		}
		mrtUi.setPreviousMrtUi(new DoaUpdateMrtUI());
		BeanUtilsHelper.copySpecificProperties(mrtUi, mrtUi.getPreviousMrtUi(), new String[]{"serviceReqDateStr", "cutOverDateStr", "cutOverTime"});
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		mrtUi.setChannelCd(user.getChannelCd());
		mrtUi.setChannelId(user.getChannelId());
		switch (MnpInd.valueOf(mrtUi.getMnpInd())) {
		case A:
			mrtUi.setBomActivated(this.mobCcsDoaUpdateMrtService.isBomActivated(mrtUi.getMnpMsisdn()));
			break;
		}
		
		// check MNP Extend log
		if(mobCcsApprovalLogService.isApproval(ServletRequestUtils.getRequiredStringParameter(request, "orderId"),"AU19")){
			mrtUi.setMnpExtendAuthInd("Y");
		}else{
			mrtUi.setMnpExtendAuthInd(null);
		}
		
		return mrtUi;
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();

		Map<String, String> cutOverTimes = new LinkedHashMap<String, String>();
		cutOverTimes.put("0", "01:00-04:00");
		cutOverTimes.put("1", "12:00-14:00");
		referenceData.put("cutOverTimes", cutOverTimes);
		
		OrderDTO orderDto = this.orderService.getOrder(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
		referenceData.put("orderDto", orderDto);
		if (StringUtils.isNotBlank(orderDto.getOcid())) {
			referenceData.put("ocidStatus", this.mobCcsOrderSearchService.getOcidStatus(orderDto.getOcid()));
		}

		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		referenceData.put("team", this.mobCcsDoaUpdateMrtService.getTeam(user.getChannelCd()));
		
		
		referenceData.put("ccsFurtherMnpMaxDate", BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS);	
		referenceData.put("smApprovalMnpExtendDays", BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);	
		
		/*Date activeSrvReqDateStart = new Date();
		Date activeCutOverDateStart = new Date();
		Date activeSrvReqDateEnd = new Date();
		Date activeCutOverDateEnd = new Date(); 
		
		
		AmendScenario amendScenario = mobCcsDoaUpdateMrtService.getAmendScenario(orderDto);
		MRTUI currentMrtUI = mobCcsDoaUpdateMrtService.getMrtUI(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
		if (currentMrtUI != null) {
			MnpInd mnpInd = MnpInd.valueOf(currentMrtUI.getMnpInd());
			Calendar now = Calendar.getInstance();
			switch (mnpInd) {
			case N:
				this.validateNewNumber(orderDto, amendScenario, form, currentMrtUI, errors, team);
				break;
			case Y:
				switch (amendScenario) {
				case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
				case DOA:
					activeCutOverDateStart = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
					activeCutOverDateEnd = addDate(orderDto.getAppInDate(), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
					if ("Y".equalsIgnoreCase(form.getMnpExtendAuthInd())){
						activeCutOverDateEnd = DateUtils.dateAfterdays(activeCutOverDateEnd, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
					}
					break;
				case MNP_REJECT:
					activeCutOverDateStart = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
					//maxDate = addDate(orderDto.getAppInDate(), 180);
					break;
				case SRD_EXP:
					activeCutOverDateStart = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
					//maxDate = addDate(orderDto.getAppInDate(), 180);
					break;
				case ONSITE_DOA:
					activeCutOverDateStart = getDate(3);
					activeCutOverDateEnd = addDate(orderDto.getAppInDate(), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
					if ("Y".equalsIgnoreCase(form.getMnpExtendAuthInd())){
						activeCutOverDateEnd = DateUtils.dateAfterdays(activeCutOverDateEnd, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
					}
					break;
				case SALES_FOLLOW_UP:
					activeCutOverDateStart = getDate(now.get(Calendar.HOUR_OF_DAY) < 15 ? 2 : 3);
					activeCutOverDateEnd = addDate(orderDto.getAppInDate(), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
					if ("Y".equalsIgnoreCase(form.getMnpExtendAuthInd())){
						activeCutOverDateEnd = DateUtils.dateAfterdays(activeCutOverDateEnd, BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
					}
					break;
				}
				
				break;
			case A:
				this.validateNewNumberAndMnp(orderDto, amendScenario, form, currentMrtUI, errors, team);
				break;
			}
		}*/
		
		return referenceData;
	}

	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		final String KEY_DELIVERYUI = "doaDelivery";
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		MRTUI form = (MRTUI) command;

		MRTUI currentMrtUI = mobCcsDoaUpdateMrtService.getMrtUI(form.getOrderId());
		MnpInd mnpInd = MnpInd.valueOf(currentMrtUI.getMnpInd());
		OrderDTO orderDto = this.orderService.getOrder(form.getOrderId());
		AmendScenario amendScenario = this.mobCcsDoaUpdateMrtService.getAmendScenario(orderDto);
		DeliveryUI deliveryUI = null;

		// no change in mrt info, only update delivery info
		boolean allowUpdateSRD = this.mobCcsDoaUpdateMrtService.allowUpdateSRD(orderDto, user.getChannelCd());
		boolean allowUpdateMnp = this.mobCcsDoaUpdateMrtService.allowUpdateMnp(orderDto, user.getChannelCd());
		switch (amendScenario) {
		case DOA:
		case ONSITE_DOA:
			if (MobCcsSessionUtil.getSession(request, KEY_DELIVERYUI) instanceof DeliveryUI) {
				deliveryUI = (DeliveryUI) MobCcsSessionUtil.getSession(request, KEY_DELIVERYUI);
				if (allowUpdateSRD || allowUpdateMnp) {
					// cannot access session in Validator, further checking on deliveryDate in ONSITE_DOA
					// ONSITE_DOA -> service not yet activate
					if (AmendScenario.ONSITE_DOA.equals(amendScenario)) {
						// check against new srvReqDate and cutOverDate when delivery info changed, and in status allow to change MRT info in page
						// check for ONSITE_DOA, reasonCd = Gxxx
						// DOA + reasonCd = Nxxx
						Date deliveryDate = Utility.string2Date(deliveryUI.getDeliveryDateStr());
						boolean invalidSrvReqDate = false;
						boolean invalidCutOverDate = false;
						
						switch (mnpInd) {
						case N:
						case A:
							Date minSrvReqDate = addDate(deliveryDate, 1);
							if (Utility.string2Date(form.getServiceReqDateStr()).before(minSrvReqDate)) {
								invalidSrvReqDate = true;
							}
							break;
						case Y:
							Date minCutOverDate = addDate(deliveryDate, 3);
							if (Utility.string2Date(form.getCutOverDateStr()).before(minCutOverDate)) {
								invalidCutOverDate = true;
							}
							break;
						}
						// stop update when srvReqDate / cutOverDate before deliveryDate +1(New#/New#+MNP) / +3(MNP) 
						if (invalidSrvReqDate || invalidCutOverDate) {
							return this.failView(form.getOrderId(), invalidSrvReqDate, invalidCutOverDate);
						}
					}
				} else {
					this.mobCcsDoaUpdateMrtService.updateDeliveryUI(deliveryUI, form.getOrderId(), user.getUsername(), user.getChannelCd());
					return this.successView(form.getOrderId());
				}
			}
			break;
		case SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION:
		case MNP_REJECT:
		case SRD_EXP:
		case SALES_FOLLOW_UP:
			break;
		}
		
		// fallout to sales, no update in mrt info, only allow f&s to process
		if (form.isByPassValidation()) {
			if (this.mobCcsDoaUpdateMrtService.allowFalloutToSales(orderDto, user.getChannelCd())) {
				this.mobCcsDoaUpdateMrtService.updateForFalloutToSales(form.getOrderId(), user.getUsername(), user.getChannelCd());
			}
			return this.successView(form.getOrderId());
		}

		switch (mnpInd) {
		case N:
			this.mobCcsDoaUpdateMrtService.updateForNewNumber(deliveryUI
					, form.getOrderId()
					, Utility.string2Date(form.getServiceReqDateStr())
					, user.getUsername(), user.getChannelCd());
			break;
		case Y:
			this.mobCcsDoaUpdateMrtService.updateForMnp(deliveryUI
					, form.getOrderId()
					, form.getMnpMsisdn(), form.getMnpTicketNum(), Utility.string2Date(form.getCutOverDateStr()), form.getCutOverTime()
					, form.getCustName(), form.getDocNum(), form.getDno(), form.getActualDno()
					, user.getUsername(), user.getChannelCd());
			break;
		case A:
			this.mobCcsDoaUpdateMrtService.updateForNewNumberAndMnp(deliveryUI
					, form.getOrderId()
					, Utility.string2Date(form.getServiceReqDateStr())
					, form.getMnpMsisdn(), form.getMnpTicketNum(), Utility.string2Date(form.getCutOverDateStr()), form.getCutOverTime()
					, form.getCustName(), form.getDocNum(), form.getDno(), form.getActualDno()
					, user.getUsername(), user.getChannelCd());
			break;
		}
		// clean up in page
		//MobCcsSessionUtil.setSession(request, KEY_DELIVERYUI, null);
		
		return this.successView(form.getOrderId());
	}
	
	private Date addDate(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, amount);
		return calendar.getTime();
	}
	
	private ModelAndView successView(String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsdoaupdatemrt.html"));
		modelAndView.addObject("orderId", orderId);
		modelAndView.addObject("recordUpdated", true);
		return modelAndView;
	}
	
	private ModelAndView failView(String orderId, boolean invalidSrvReqDate, boolean invalidCutOverDate) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsdoaupdatemrt.html"));
		modelAndView.addObject("orderId", orderId);
		if (invalidSrvReqDate) {
			modelAndView.addObject("invalidSrvReqDate", invalidSrvReqDate);
		}
		if (invalidCutOverDate) {
			modelAndView.addObject("invalidCutOverDate", invalidCutOverDate);
		}
		return modelAndView;
	}
	
	public class DoaUpdateMrtUI extends MRTUI {
		private String channelCd;
		private boolean bomActivated;
	  	private String fulfillmentAuth = "N";
	  	private int channelId;

		public String getChannelCd() {
			return channelCd;
		}

		public void setChannelCd(String channelCd) {
			this.channelCd = channelCd;
		}

		public boolean isBomActivated() {
			return bomActivated;
		}

		public void setBomActivated(boolean bomActivated) {
			this.bomActivated = bomActivated;
		}

		public String getFulfillmentAuth() {
			return fulfillmentAuth;
		}

		public void setFulfillmentAuth(String fulfillmentAuth) {
			this.fulfillmentAuth = fulfillmentAuth;
		}

		public int getChannelId() {
			return channelId;
		}

		public void setChannelId(int channelId) {
			this.channelId = channelId;
		}
		
	}
}
