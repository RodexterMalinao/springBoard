package com.bomwebportal.mob.ccs.web;
import java.util.HashMap;
import java.util.List;
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

import com.bomwebportal.dto.BasketMinVasLkupDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.ApprovalLogDTO;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.ccs.dto.ui.AuthorizeUI;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MobCcsSalesInfoService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.AuthorizeService;
import com.bomwebportal.service.VasDetailService;

public class MobCcsAuthorizeController extends SimpleFormController {
	private MobCcsSalesInfoService mobCcsSalesInfoService;
	private MobCcsApprovalLogService mobCcsApprovalLogService;
	private AuthorizeService authorizeService;
	private VasDetailService vasDetailService;
	
	public MobCcsSalesInfoService getMobCcsSalesInfoService() {
		return mobCcsSalesInfoService;
	}

	public void setMobCcsSalesInfoService(
			MobCcsSalesInfoService mobCcsSalesInfoService) {
		this.mobCcsSalesInfoService = mobCcsSalesInfoService;
	}

	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(
			MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	
	public AuthorizeService getAuthorizeService() {
		return authorizeService;
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		AuthorizeUI form = new AuthorizeUI();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		form.setAction(ServletRequestUtils.getStringParameter(request, "action"));
		form.setOrderId(ServletRequestUtils.getStringParameter(request, "orderId"));
		form.setBasketId(ServletRequestUtils.getStringParameter(request, "basketId"));
		form.setCategoryLogin(authorizeService.getAuthorizeCategoryText(form.getAction(),""+ user.getChannelId()));
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("MobCcsAuthorizeController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		//boolean userIsManager = false;
		
		AuthorizeUI form = (AuthorizeUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String appDate = (String) request.getSession().getAttribute("appDate");
		Boolean allowApprover = false;
		
		if (!"Y".equals(form.getByPassAuthInd())){
			//2. CHECK whether Approver Channel Cd same with sales channel Cd
			
			List<SalesInfoDTO> approver = authorizeService.getSalesInfoDTOByID(form.getUsername());
			//List<SalesInfoDTO> approver = mobCcsSalesInfoService.getSalesInfoDTOByID(form.getUsername(), Utility.date2String(new Date(),"dd/MM/yyyy")  );
			if (approver == null ||  approver.size() == 0 || approver.size() > 1){
				ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsauthorize.html"));
				modelAndView.addObject("errorMsg", "Profile error, please check account profile");
				modelAndView.addObject("action", form.getAction());
				if (StringUtils.isNotBlank(form.getOrderId())) {
					modelAndView.addObject("orderId", form.getOrderId());
				}
				if (StringUtils.isNotBlank(form.getBasketId())) {
					modelAndView.addObject("basketId", form.getBasketId());
				}
				return modelAndView;
						
			}else if (!approver.get(0).getChannelCd().equals(user.getChannelCd())){
				ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsauthorize.html"));
				modelAndView.addObject("errorMsg", "Channel code must be the same");
				modelAndView.addObject("action", form.getAction());
				if (StringUtils.isNotBlank(form.getOrderId())) {
					modelAndView.addObject("orderId", form.getOrderId());
				}
				if (StringUtils.isNotBlank(form.getBasketId())) {
					modelAndView.addObject("basketId", form.getBasketId());
				}
				return modelAndView;
						
			}
		
				
		
			//3. CHECK approver SB_CATEGORY / BOM_CATEGORY allow to authorize to bomweb_auth_right
			String authRight = null;
			allowApprover = false;
					
			String bomCategory = authorizeService.getBomSalesRoleDTOByID(form.getUsername());
			String sbCategory = approver.get(0).getCategory();
					
			authRight = authorizeService.getAuthorizeRight(form.getAction(), ""+user.getChannelId(), sbCategory, bomCategory);
			if ("Y".equals(authRight)){
				allowApprover = true;
			}	
		}else{
			allowApprover = true;
		}
		
		
		if (allowApprover) {
			String authorizedId = mobCcsApprovalLogService.getNextAuthorizedId();
			
			if (StringUtils.isBlank(authorizedId)) {
				ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsauthorize.html"));
				modelAndView.addObject("action", form.getAction());
				if (StringUtils.isNotBlank(form.getOrderId())) {
					modelAndView.addObject("orderId", form.getOrderId());
				}
				if (StringUtils.isNotBlank(form.getBasketId())) {
					modelAndView.addObject("basketId", form.getBasketId());
				}
				modelAndView.addObject("systemError", true);
				return modelAndView;
			} else {
				//Date now = new Date();
				ApprovalLogDTO dto = new ApprovalLogDTO();
				dto.setAuthorizedId(authorizedId);
				dto.setOrderId(StringUtils.isBlank(form.getOrderId()) ? null : form.getOrderId());
				//dto.setAuthorizedDate(now);
				
				if (!"Y".equals(form.getByPassAuthInd())){
					dto.setAuthorizedBy(form.getUsername());
				}else{
					dto.setAuthorizedBy(user.getUsername());
				}
				
				dto.setAuthorizedAction(form.getAction());
				dto.setShopCode(user.getChannelCd());
				dto.setLastUpdBy(user.getUsername());
				
				if ("Y".equals(form.getReasonRemarkInputInd())){
					dto.setReasonType(form.getReasonType());
					dto.setRemark(form.getRemark());
					dto.setReasonCd(form.getReasonCd());
				}
				
				if ("AU18".equals(form.getAction())){
					dto.setBasketId(form.getBasketId());
					BasketMinVasLkupDTO minVasDTO = vasDetailService.getBasketMinVasLkup(form.getBasketId(), appDate);
					Double minVas = 0.0;
					if (minVasDTO != null) {
						minVas = minVasDTO.getMinVas();
					} else{
						minVas = 0.0;
					}
					dto.setMinVas(String.valueOf(minVas));
					dto.setOrderNature("ACQ");
					dto.setRpGrossPlanFee(null);
				}
				
				//dto.setCreateDate(now);
				//dto.setLastUpdDate(now);
				mobCcsApprovalLogService.insertApprovalLogDTO(dto);
				
				/*
				 *  action: 
				 *  AU01 = mobccsdelivery
				 *  AU02 = customerinformationquota
				 */
				MobCcsSessionUtil.setSession(request, form.getAction(), authorizedId);
				MobCcsSessionUtil.setSession(request, form.getAction()+"AuthBy", dto.getAuthorizedBy());
				ModelAndView modelAndView = new ModelAndView("mobccsauthorizesuccess");
				return modelAndView;
			}
		} else {

			ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsauthorize.html"));
			modelAndView.addObject("action", form.getAction());
			if (StringUtils.isNotBlank(form.getOrderId())) {
				modelAndView.addObject("orderId", form.getOrderId());
			}
			if (StringUtils.isNotBlank(form.getBasketId())) {
				modelAndView.addObject("basketId", form.getBasketId());
			}
			modelAndView.addObject("userIsManager", allowApprover);
			return modelAndView;
		}
		
		/*
		(2) Check approval right via ST4 hirearchy
		List<SalesInfoDTO> managers = new ArrayList<SalesInfoDTO> ();
		if (user.getChannelId() == 1 || user.getChannelId() == 2 || user.getChannelId() == 3){
			managers = this.mobCcsSalesInfoService.getSalesInfoDTO(user.getChannelCd(), "MANAGER");
		} else if (user.getChannelId() == 10){
			managers = this.mobCcsSalesInfoService.getSalesInfoDTO(user.getChannelCd(), "SUPERVISOR");
		}
		
		
		if (!this.isEmpty(managers)) {
			for (SalesInfoDTO manager : managers) {
				if (StringUtils.isNotBlank(form.getUsername()) && form.getUsername().equals(manager.getStaffId())) {
					userIsManager = true;
					break;
				}
			}
		}
		
		(2) Check approval right via ST4 hirearchy
		
		
		(3) or Check POSBOM Supervisor right from BOM (retails, pt sales only) 
		if (!userIsManager && (user.getChannelId() == 1 || user.getChannelId() == 3 )){
			userIsManager = this.mobCcsSalesInfoService.getBomSalesInfoDTOByID(form.getUsername());
		}
		(3) Check POSBOM Supervisor right from BOM 
		
		
		if (userIsManager) {
			String authorizedId = mobCcsApprovalLogService.getNextAuthorizedId();
			
			if (StringUtils.isBlank(authorizedId)) {
				ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsauthorize.html"));
				modelAndView.addObject("action", form.getAction());
				if (StringUtils.isNotBlank(form.getOrderId())) {
					modelAndView.addObject("orderId", form.getOrderId());
				}
				modelAndView.addObject("systemError", true);
				return modelAndView;
			} else {
				//Date now = new Date();
				ApprovalLogDTO dto = new ApprovalLogDTO();
				dto.setAuthorizedId(authorizedId);
				dto.setOrderId(StringUtils.isBlank(form.getOrderId()) ? null : form.getOrderId());
				//dto.setAuthorizedDate(now);
				dto.setAuthorizedBy(form.getUsername());
				dto.setAuthorizedAction(form.getAction());
				dto.setShopCode(user.getChannelCd());
				dto.setLastUpdBy(user.getUsername());
				//dto.setCreateDate(now);
				//dto.setLastUpdDate(now);
				mobCcsApprovalLogService.insertApprovalLogDTO(dto);
				
				
				 *  action: 
				 *  AU01 = mobccsdelivery
				 *  AU02 = customerinformationquota
				 
				MobCcsSessionUtil.setSession(request, form.getAction(), authorizedId);
				MobCcsSessionUtil.setSession(request, form.getAction()+"AuthBy", dto.getAuthorizedBy());
				ModelAndView modelAndView = new ModelAndView("mobccsauthorizesuccess");
				return modelAndView;
			}
		} else {
			ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsauthorize.html"));
			modelAndView.addObject("action", form.getAction());
			if (StringUtils.isNotBlank(form.getOrderId())) {
				modelAndView.addObject("orderId", form.getOrderId());
			}
			modelAndView.addObject("userIsManager", userIsManager);
			return modelAndView;
		}*/
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public static String getSessionAuthorizedBy(HttpServletRequest request, String action) {
		return (String) MobCcsSessionUtil.getSession(request, action+"AuthBy");
	}
}
