package com.bomwebportal.ims.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomwebSubscribedOfferImsDTO;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.ims.service.ImsBasketDetailsService;
import com.bomwebportal.ims.service.ImsOrderService;

public class ImsAmendProgOfferController extends SimpleFormController{
	
	private ImsOrderService orderService;    
	private ImsAddressInfoService service;
    private ImsBasketDetailsService basketDetailService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);

		
		String h264Ind = null;
		
		String adsl18mInd = null;

		h264Ind = service.getH264Ind(order.getInstallAddress().getSerbdyno());
		adsl18mInd = service.getAdsl18mInd(order.getInstallAddress().getSerbdyno());
		
		order.getInstallAddress().getAddrInventory().setH264Ind(h264Ind);
		order.getInstallAddress().getAddrInventory().setAdsl18MInd(adsl18mInd);
		
		orderService.getBackendOfferChange(order);
		
		BomwebSubscribedOfferImsDTO homeNetworkBackend = new BomwebSubscribedOfferImsDTO();
		
		homeNetworkBackend = orderService.getHomeNetworkRequired(order.getInstallAddress().getSerbdyno(), order.getInstallAddress().getUnitNo(), order.getInstallAddress().getFloorNo(), order.getInstallAddress().getAddrInventory().getTechnology());
		
		if(homeNetworkBackend!=null&&"I".equalsIgnoreCase(homeNetworkBackend.getIoInd())&&!homeNetworkBackend.getOfferCd().isEmpty()){
			
			SubscribedItemUI hnbackend = new SubscribedItemUI();
			hnbackend.setOfferCode(homeNetworkBackend.getOfferCd());
			hnbackend.setImsServiceType("PCD");
			hnbackend.setCreateBy("BACKEND");
			hnbackend.setLastUpdBy("BACKEND");
			
			if(order.getSubscribedBackEndItems() != null){
				SubscribedItemUI[] updateSubscribedItemUI = new SubscribedItemUI[order.getSubscribedBackEndItems().length+1];
				for(int i=0;i<order.getSubscribedBackEndItems().length;i++){
					SubscribedItemUI itemui = new SubscribedItemUI();
					BeanUtils.copyProperties(itemui, order.getSubscribedBackEndItems()[i]);
					updateSubscribedItemUI[i] = itemui;
				}
				SubscribedItemUI itemui = new SubscribedItemUI();
				BeanUtils.copyProperties(itemui,hnbackend);
				updateSubscribedItemUI[order.getSubscribedBackEndItems().length] = itemui;
				order.setSubscribedBackEndItems(updateSubscribedItemUI);
				
			}else{
				SubscribedItemUI[] updateSubscribedItemUI = new SubscribedItemUI[1];
				SubscribedItemUI itemui = new SubscribedItemUI();
				BeanUtils.copyProperties(itemui,hnbackend);
				updateSubscribedItemUI[0] = itemui;
				order.setSubscribedBackEndItems(updateSubscribedItemUI);
			}
		}

		if(order.getSubscribedBackEndItems() != null){
			for(int j=0;j<order.getSubscribedBackEndItems().length;j++){
				logger.info(order.getSubscribedBackEndItems()[j].getOfferCode());
			}
		}
		if (order.getSubscribedBackEndChannels()!=null) {
			for(int j=0;j<order.getSubscribedBackEndChannels().length;j++){
				logger.info(order.getSubscribedBackEndChannels()[j].getCampaignCd()+" "+order.getSubscribedBackEndChannels()[j].getPlanCd());
			}
		}
		
		//auto add item
		List<SubscribedItemUI> autoAddItemList = new ArrayList<SubscribedItemUI>();
		List<SubscribedItemUI> removeItemList = new ArrayList<SubscribedItemUI>();
		String subItemIdStr  = "";
		for(SubscribedItemUI subItem:order.getSubscribedItems()){
			if(("BE_F_REC").equalsIgnoreCase(subItem.getType())){
				removeItemList.add(subItem);
			}
			else if(StringUtils.isNotBlank(subItem.getId())){
				subItemIdStr += subItem.getId()+",";
			}
		}
		
		if(StringUtils.isNotBlank(subItemIdStr))
			autoAddItemList.addAll(basketDetailService.addAutoTieItems(subItemIdStr.substring(0,subItemIdStr.length()-1)));

		
		List<SubscribedItemUI> allSubscribedItems = new ArrayList<SubscribedItemUI>();
		
		if(autoAddItemList.size()>0||removeItemList.size()>0) {
			for(SubscribedItemUI item:order.getSubscribedItems()){
				allSubscribedItems.add(item);
			}
			
			if(removeItemList.size()>0){ 
				for(SubscribedItemUI item:removeItemList){
					allSubscribedItems.remove(item);
				}
			}
			
			if(autoAddItemList.size()>0||removeItemList.size()>0){
				for(SubscribedItemUI item:autoAddItemList) {
					allSubscribedItems.add(item);
				}
			}
			order.setSubscribedItems( allSubscribedItems.toArray(new SubscribedItemUI[allSubscribedItems.size()]));
		}
		//auto add item end
		
		
		
		String rmk = orderService.genProgOfferChangeRmk(order);

		System.out.println(rmk);
		
		request.getSession().setAttribute("_action", "_a");
		request.getSession().setAttribute("amend_rmk", rmk);
		
		request.getSession().setAttribute("order_for_amendment", order);
		
		return new ModelAndView(new RedirectView("blank.jsp"));
	}

	public void setOrderService(ImsOrderService orderService) {
		this.orderService = orderService;
	}

	public ImsOrderService getOrderService() {
		return orderService;
	}
    
	public void setService(ImsAddressInfoService service) {
		this.service = service;
	}
	
	public ImsAddressInfoService getService() {
		return service;
	}
	
	public ImsBasketDetailsService getBasketDetailService() {
		return basketDetailService;
	}
	
	public void setBasketDetailService(ImsBasketDetailsService basketDetailService) {
		this.basketDetailService = basketDetailService;
	}
}
