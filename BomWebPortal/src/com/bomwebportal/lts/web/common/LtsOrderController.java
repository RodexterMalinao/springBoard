package com.bomwebportal.lts.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.LtsTeamFunctionDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.order.OrderDetailService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsOrderController extends AbstractController{
	
	private static final String issueCOrderView = "ltscustomerinquiry.html";
	private static final String issueDOrderView = "ltsterminatecustomerinquiry.html";
	private static final String issueIOrderView = "ltsacqorder.html";
	private static final String enquiryCOrderView = "ltsupgradeeyeorder.html";
	private static final String enquiryDOrderView = "ltsterminateorder.html";
	private static final String enquiryIOrderView = "ltsacqorder.html";
	
	private LtsCommonService ltsCommonService;
	private OrderDetailService orderDetailService;
	
	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String action = (String)request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ACTION);
		String mode =  (String)request.getParameter(LtsConstant.REQUEST_PARAM_MODE);
		LtsSessionHelper.clearAllSessionAttb(request);
		if (StringUtils.isNotBlank(mode)) {
			request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_MODE, mode.toUpperCase());
		}
		
		if (LtsConstant.REQUEST_PARAM_ORDER_ACTION_CREATE.equalsIgnoreCase(action)) {
			String type = (String)request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_TYPE);
			
			if (LtsConstant.ORDER_TYPE_INSTALL.equalsIgnoreCase(type)) {
				return issueIOrder(request);
			}
			if (LtsConstant.ORDER_TYPE_CHANGE.equalsIgnoreCase(type)) {
				return issueCOrder(request);
			}
			if (LtsConstant.ORDER_TYPE_DISCONNECT.equalsIgnoreCase(type)) {
				return issueDOrder(request);
			}	
		}
		
		if (LtsConstant.REQUEST_PARAM_ORDER_ACTION_ENQUIRY.equalsIgnoreCase(action)) {
			String orderId = (String)request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
			
			if (StringUtils.isBlank(orderId)) {
				return null;
			}
			
			String orderType = orderDetailService.getOrderType(orderId);
				
			if(StringUtils.isNotEmpty(orderType)) {
				if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderType) 
						|| LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderType)) {
					return enquiryCOrder(request, orderId);
				}
				if (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(orderType)) {
					return enquiryIOrder(request, orderId);
				}
				if (LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(orderType)) {
					return enquiryDOrder(request, orderId);
				}
			}
			return enquiryCOrder(request, orderId);
		}
		
		if (LtsConstant.REQUEST_PARAM_ORDER_ACTION_RECALL.equalsIgnoreCase(action)) {
			String orderId = (String)request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
			
			if (StringUtils.isBlank(orderId)) {
				return null;
			}
			
			String orderType = orderDetailService.getOrderType(orderId);
				
			if(StringUtils.isNotEmpty(orderType)) {
				if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderType) 
						|| LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderType)) {
					return recallCOrder(request, orderId);
				}
				if (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(orderType)) {
					return recallIOrder(request, orderId);
				}
				if (LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(orderType)) {
					return recallDOrder(request, orderId);
				}
			}
			return recallCOrder(request, orderId);
		}
		
		
		if (LtsConstant.REQUEST_PARAM_ORDER_ACTION_ENQUIRY_N_CANCEL.equalsIgnoreCase(action)) {
			String orderId = (String)request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
			
			if (StringUtils.isBlank(orderId)) {
				return null;
			}
			
			String orderType = orderDetailService.getOrderType(orderId);
			if(StringUtils.isNotEmpty(orderType)) {
				if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderType) 
						|| LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderType)) {
					return enquiryAndCancelCOrder(request, orderId);
				}
				if (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(orderType)) {
					return enquiryAndCancelIOrder(request, orderId);
				}
				if (LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(orderType)) {
					return enquiryAndCancelDOrder(request, orderId);
				}
			}
		}
		
		if (LtsConstant.REQUEST_PARAM_ORDER_ACTION_RECALL_N_UPDATE_PREPAYMENT.equalsIgnoreCase(action)) {
			String orderId = (String)request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
			
			if (StringUtils.isBlank(orderId)) {
				return null;
			}
			
			String orderType = orderDetailService.getOrderType(orderId);
			if(StringUtils.isNotEmpty(orderType)) {
				if (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(orderType)) {
					return recallAndUpdatePrepaymentIOrder(request, orderId);
				}
			}
		}
		
		if (LtsConstant.REQUEST_PARAM_ORDER_ACTION_RECALL_N_UPDATE_APPNT.equalsIgnoreCase(action)) {
			String orderId = (String)request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
			
			if (StringUtils.isBlank(orderId)) {
				return null;
			}
			
			String orderType = orderDetailService.getOrderType(orderId);
				
			if(StringUtils.isNotEmpty(orderType)) {
				if (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(orderType)) {
					return recallAndUpdateAppointmentIOrder(request, orderId);
				}
			}
		}
		
		return null;
	}
	

	private ModelAndView enquiryAndCancelCOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryCOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView enquiryCOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryCOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_ENQUIRY);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView enquiryAndCancelIOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryIOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView enquiryIOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryIOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_ENQUIRY);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView enquiryAndCancelDOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryDOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView enquiryDOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryDOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_ENQUIRY);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView recallIOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryIOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_RECALL);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView recallCOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryCOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_RECALL);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView recallDOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryDOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_RECALL);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
		
	private ModelAndView recallAndUpdatePrepaymentIOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryIOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_PREPAYMENT);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView recallAndUpdateAppointmentIOrder(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(enquiryIOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_APPOINTMENT);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_ORDER_ID, orderId);
		return modelAndView;
	}
	
	private ModelAndView issueCOrder(HttpServletRequest request) {
		
		LtsSessionHelper.clearAllSessionAttb(request);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		LtsTeamFunctionDTO ltsTeamFunction = ltsCommonService.getTeamFunction(bomSalesUser.getChannelCd(), bomSalesUser.getShopCd());
		LtsSessionHelper.setLtsTeamFunction(request, ltsTeamFunction);

		String srvNum = (String)request.getParameter(LtsConstant.REQUEST_PARAM_SRV_NUM);
		String tp = (String)request.getParameter(LtsConstant.REQUEST_PARAM_TYPE_OF_SRV);
		String datCd = (String)request.getParameter(LtsConstant.REQUEST_PARAM_DAT_CD);
		
		ModelAndView modelAndView = new ModelAndView(new RedirectView(issueCOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_SRV_NUM, srvNum);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_TYPE_OF_SRV, tp);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_DAT_CD, datCd);
		return modelAndView;
	}
	
	private ModelAndView issueIOrder(HttpServletRequest request) {
		LtsSessionHelper.clearAllSessionAttb(request);
		String srvNum = (String)request.getParameter(LtsConstant.REQUEST_PARAM_SRV_NUM);
		String bomCustNum = (String)request.getParameter(LtsConstant.REQUEST_PARAM_BOM_CUST_NUM);
		String legacyCustNum = (String)request.getParameter(LtsConstant.REQUEST_PARAM_LEGACY_CUST_NUM);

		ModelAndView modelAndView = new ModelAndView(new RedirectView(issueIOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_SRV_NUM, srvNum);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_BOM_CUST_NUM, bomCustNum);
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_LEGACY_CUST_NUM, legacyCustNum);
		return modelAndView;
	}
	
	private ModelAndView issueDOrder(HttpServletRequest request) {
		LtsSessionHelper.clearAllTerminateOrderSessionAttb(request);
		String srvNum = (String)request.getParameter(LtsConstant.REQUEST_PARAM_SRV_NUM);
		ModelAndView modelAndView = new ModelAndView(new RedirectView(issueDOrderView));
		modelAndView.addObject(LtsConstant.REQUEST_PARAM_SRV_NUM, srvNum);
		return modelAndView;
	}
	

}
