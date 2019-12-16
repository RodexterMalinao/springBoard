package com.bomwebportal.lts.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.LtsOrderSearchDTO;
import com.bomwebportal.lts.dto.OrderSrvStatusDetailDTO;
import com.bomwebportal.lts.dto.form.LtsCcOrderSearchFormDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.service.LtsOrderSearchService;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsCcOrderSearchResultController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private LtsOrderSearchService ltsOrderSearchService;
	private LtsSalesInfoService ltsSalesInfoService;
	private OrderStatusService orderStatusService;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		LtsCcOrderSearchFormDTO orderSearch = (LtsCcOrderSearchFormDTO)request.getSession().getAttribute("ltsCcOrderSearchCmd");
		ModelAndView mav = new ModelAndView("ltsccordersearchresult");

		if (orderSearch != null) {
			List<LtsOrderSearchDTO> ltsSearchResultList = searchLtsOnlineOrder(orderSearch, bomSalesUserDTO);
			setLtsAllowAmendOrder(ltsSearchResultList, bomSalesUserDTO);
			mav.addObject("ltsOrderSearchResult", ltsSearchResultList);
		}

		return mav;
	}
	
	private List<LtsOrderSearchDTO> searchLtsOnlineOrder(
			LtsCcOrderSearchFormDTO searchUI, BomSalesUserDTO bomSalesUserDTO) {
		/*from login sales profile*/
		String userId = bomSalesUserDTO.getUsername();
		String salesChannel = bomSalesUserDTO.getChannelCd();
		String staffRole = bomSalesUserDTO.getCategory();
		//String channelId = Integer.toString(bomSalesUserDTO.getChannelId());
		String[] channelIds = null;
		String[] salesCenters = null;

		/*from input*/
		String salesTeam = searchUI.getTeamCode();
		String staffNum = searchUI.getStaffNum();
		String orgStaffId = ltsSalesInfoService.getOrgStaffId(staffNum)[0];
		
		if(StringUtils.equals(staffRole, LtsConstant.SALES_ROLE_SALES_MANAGER) 
				|| StringUtils.equals(staffRole, LtsConstant.SALES_ROLE_SUPPORT)){
			salesChannel = null;
		}
		
		if(StringUtils.equals(bomSalesUserDTO.getChannelCd(), LtsConstant.CHANNEL_CD_DIRECT_SALES_QC)){
			channelIds = LtsConstant.CHANNEL_ID_DIRECT_SALES_QCC;
			salesChannel = null;
		}
		
		if(StringUtils.isBlank(searchUI.getOrderId())){
			if(!StringUtils.equals(bomSalesUserDTO.getChannelCd(), LtsConstant.CHANNEL_CD_DIRECT_SALES_QC)){
				channelIds = new String[]{Integer.toString(bomSalesUserDTO.getChannelId())};

				/* DS Frontline can only search his own order
				 * DS Supervisor (Manager) can only search his team*/
				for(String channelId : channelIds){
					if(Arrays.asList(LtsConstant.CHANNEL_ID_ALL_DIRECT_SALES).contains(channelId)){
						if(StringUtils.equals(staffRole, LtsConstant.SALES_ROLE_FRONTLINE)){
							staffNum = userId;
						}else if(StringUtils.equals(staffRole, LtsConstant.SALES_ROLE_MANAGER)){
							salesTeam = bomSalesUserDTO.getShopCd();
						}else if(StringUtils.equals(staffRole, LtsConstant.SALES_ROLE_SALES_MANAGER)){
							List<String> salesCenter = ltsOrderSearchService.getDsSalesCenter(userId, channelId);
							if(salesCenter != null && salesCenter.size() > 0){
								salesCenters = salesCenter.toArray(new String[0]);
							}
						}
						break;
					}
				}
			}
		}
		
		String srvNum = searchUI.getServiceNumber();
		srvNum = StringUtils.trim(srvNum);
		if(StringUtils.isNotBlank(srvNum)){
			srvNum = StringUtils.leftPad(srvNum, 12, '0') ;
		}
		String status = searchUI.getSbOrdStatus();
		
		return ltsOrderSearchService.searchLtsCcOrder(
				StringUtils.trim(searchUI.getOrderId()), searchUI.getIdDocType(),
				searchUI.getIdDocNum(), srvNum, 
				searchUI.getContactEmail(), userId, channelIds, staffNum, salesTeam, salesCenters, salesChannel, orgStaffId,
				searchUI.getStartDate(), searchUI.getEndDate(), status, searchUI.getSrdStartDate(), searchUI.getSrdEndDate());
	}
	
	public void setLtsAllowAmendOrder(List<LtsOrderSearchDTO> orderList, BomSalesUserDTO bomSalesUserDTO) {
		if (orderList == null || orderList.isEmpty()) {
			return;
		}

		String userStaffNum = ltsSalesInfoService.getOrgStaffId(bomSalesUserDTO.getUsername())[0];
		String userShopCd = bomSalesUserDTO.getShopCd();
		String userSalesRole = bomSalesUserDTO.getCategory();
		String userChannelCd = bomSalesUserDTO.getChannelCd();
		
		boolean isDsOrderSupport = LtsSessionHelper.isDirectSalesSupport(bomSalesUserDTO.getChannelId(), userChannelCd);
		
		orderloop:
		for (LtsOrderSearchDTO order : orderList) {
			
			if(isDsOrderSupport
					|| StringUtils.equals(bomSalesUserDTO.getChannelCd(), LtsConstant.CHANNEL_CD_DIRECT_SALES_QC)){
				order.setAllowAmendOrder(true);
				order.setAllowRecallOrder(true);
				order.setAllowCancelOrder(true);
			}else{
				if(!StringUtils.equals(order.getCreateChannel(), userChannelCd)){
					order.setAllowAmendOrder(false);
					order.setAllowRecallOrder(false);
					order.setAllowCancelOrder(false);
					continue;
				}
				
				if(LtsConstant.SALES_ROLE_FRONTLINE.equals(userSalesRole) 
						&& !StringUtils.equals(order.getStaffNum(), userStaffNum)
						&& !StringUtils.equals(order.getCreateBy(), bomSalesUserDTO.getUsername())){
					order.setAllowAmendOrder(false);
					order.setAllowRecallOrder(false);
					order.setAllowCancelOrder(false);
					continue;
				}

				if((LtsConstant.SALES_ROLE_MANAGER.equals(userSalesRole) || LtsConstant.SALES_ROLE_FRONTLINE.equals(userSalesRole))
						&& !StringUtils.equals(order.getShopCd(), userShopCd)
						&& !StringUtils.equals(order.getStaffNum(), userStaffNum)
						&& !StringUtils.equals(order.getCreateBy(), bomSalesUserDTO.getUsername()	)){
					order.setAllowAmendOrder(false);
					order.setAllowRecallOrder(false);
					order.setAllowCancelOrder(false);
					continue;
				}
			}
			
			
			if (!StringUtils.equals("LTS", order.getLob())
					|| StringUtils.isEmpty(order.getOcId())) {
				order.setAllowAmendOrder(true);
				order.setAllowRecallOrder(true);
				order.setAllowCancelOrder(true);
				continue;
			}

			/*If any serivce is pending to create BOM order (WQ), allow amendment*/
			OrderStatusDTO[] orderStatus = orderStatusService.getStatus(order.getOrderId());
			if(orderStatus != null && orderStatus.length > 0){
				for(OrderStatusDTO status: orderStatus){
					if(LtsConstant.ORDER_STATUS_PENDING_BOM.equals(status.getSbStatus())){
						order.setAllowAmendOrder(true);
						continue orderloop;
					}
				}
			}
			
			/*If no service is pending Bom order, check Bom order status*/
			if(StringUtils.isNotBlank(order.getOcId())){
				List<OrderSrvStatusDetailDTO> orderSrvStatusList = ltsOrderSearchService.getPendingOrderSrvStatusList(order.getOcId());
				order.setAllowAmendOrder(false);
				order.setAllowRecallOrder(true);
				order.setAllowCancelOrder(true);
				for(OrderSrvStatusDetailDTO srvStatus : orderSrvStatusList){
					if(StringUtils.equals(srvStatus.getTypeOfSrv(), LtsConstant.SERVICE_TYPE_TEL)){
						if (!StringUtils.equals(LtsConstant.DRG_ORDER_STATUS_COMPLETED, srvStatus.getLegacyStatus())
								&& !StringUtils.equals(LtsConstant.DRG_ORDER_STATUS_NUMBER_INVESTIGATE, srvStatus.getLegacyStatus())
								&& !StringUtils.equals(LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER, srvStatus.getBomStatus())
								&& !StringUtils.equals(LtsConstant.BOM_ORDER_STATUS_CANCELLED_WO_ORDER, srvStatus.getBomStatus())
								&& !StringUtils.equals(LtsConstant.BOM_ORDER_STATUS_COMPLETED, srvStatus.getBomStatus())) {
							order.setAllowAmendOrder(true);
							break;
						}
					}else if(StringUtils.equals(srvStatus.getTypeOfSrv(), LtsConstant.SERVICE_TYPE_IMS)){
						if(!StringUtils.equals(srvStatus.getBomStatus(), LtsConstant.IMS_ORDER_STATUS_CANCELLED)
								&& !StringUtils.equals(srvStatus.getBomStatus(), LtsConstant.IMS_ORDER_STATUS_COMPLETED)){
							order.setAllowAmendOrder(true);
							break;
						}
					}
				}
			}
		}
	}

	
	public LtsOrderSearchService getLtsOrderSearchService() {
		return ltsOrderSearchService;
	}
	public void setLtsOrderSearchService(LtsOrderSearchService ltsOrderSearchService) {
		this.ltsOrderSearchService = ltsOrderSearchService;
	}

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}
	
}
