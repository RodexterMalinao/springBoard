package com.bomltsportal.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomltsportal.dto.form.BasketSelectFormDTO;
import com.bomltsportal.service.ItemDetailService;
import com.bomltsportal.service.NowTvChannelService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.google.common.collect.Lists;

public class BasketDetailController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());

	private static final String nextView = "vasdetail.html";
	
	private NowTvChannelService nowTvChannelService;
	private ItemDetailService itemDetailService;
	
	public NowTvChannelService getNowTvChannelService() {
		return nowTvChannelService;
	}

	public void setNowTvChannelService(NowTvChannelService nowTvChannelService) {
		this.nowTvChannelService = nowTvChannelService;
	}

	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		if(orderCapture == null || orderCapture.isOrderSignoffed()){
			return SessionHelper.getSessionTimeOutView();
		}		
		if(BomLtsConstant.SERVICE_TYPE_DEL.equals(orderCapture.getServiceTypeInd())){
			BasketDetailFormDTO form = orderCapture.getBasketDetailForm();
			if(form == null){
				form = new BasketDetailFormDTO();
				setBasketDetailForm(orderCapture, form, orderCapture.getLang());
				orderCapture.setBasketDetailForm(form);
			}
			if((form.getContItemSetList() == null || form.getContItemSetList().size() == 0)
					&& (form.getPremiumItemSetList() == null || form.getPremiumItemSetList().size() == 0)
					){
				return new ModelAndView(new RedirectView(nextView));
			}
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
//		String locale = RequestContextUtils.getLocale(request).toString();
		BasketDetailFormDTO form = orderCapture.getBasketDetailForm();
		if(form == null){
			form = new BasketDetailFormDTO();
			setBasketDetailForm(orderCapture, form, orderCapture.getLang());
			orderCapture.setBasketDetailForm(form);
		}
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException {
		
		BasketDetailFormDTO form = (BasketDetailFormDTO) command;
		
		if(!StringUtils.isEmpty(form.getSelectedNowTvGroupId()) && !StringUtils.isEmpty(form.getSelectedNowTvChannelId())){
			for(ChannelGroupDetailDTO channelGroup: form.getChannelGroupList()){
				if(StringUtils.equals(form.getSelectedNowTvGroupId(), channelGroup.getChannelGroupId())){
					channelGroup.setSelected(true);
					for(ChannelDetailDTO channelDetail : channelGroup.getChannelDetails()){
						if(StringUtils.equals(channelDetail.getChannelId(), form.getSelectedNowTvChannelId())){
							channelDetail.setSelected(true);
							break;
						}
					}
					break;
				}
			}
		}else{
			form.setSelectedNowTvGroupId(null);
			form.setSelectedNowTvChannelId(null);
		}
		
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		orderCapture.setBasketDetailForm(form);
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	private void setBundledNowtvChannel(OrderCaptureDTO orderCapture, BasketDetailFormDTO form, String locale) {
		List<ItemSetDetailDTO> contItemSetList = form.getContItemSetList();
		
		if (contItemSetList == null || contItemSetList.isEmpty()) {
			return;
		}
		
		ItemDetailDTO[] itemDetails = null;
		for (ItemSetDetailDTO contItemSet : contItemSetList) {
			itemDetails = contItemSet.getItemDetails();
			
			if (ArrayUtils.isEmpty(itemDetails)) {
				continue;
			}
			
			for (ItemDetailDTO itemDetail : itemDetails) {
				if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType())
						|| StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType())) {
					
					String deviceType = getSelectedDeviceType(orderCapture);
					
					List<ChannelGroupDetailDTO> channelGroupList = nowTvChannelService.getChannelGroupList(itemDetail.getItemType(), deviceType, locale, true, itemDetail.getCredit());
					form.setChannelGroupList(channelGroupList);
					return;
				}
			}
		}
		
	}
	
	private String getSelectedDeviceType(OrderCaptureDTO orderCapture) {
		
		BasketSelectFormDTO basketSelectForm = orderCapture.getBasketSelectForm();
		
		if (basketSelectForm == null) {
			return null;
		}
		
		for (OnlineBasketDTO onlineBasket : basketSelectForm.getOnlineBasketList()) {
			if (onlineBasket.isSelected()) {
				return onlineBasket.getBasketDetail().getType();
			}
		}
		
		return null;
	}
	
	private ItemSetCriteriaDTO getItemSetCriteria(OrderCaptureDTO orderCapture, String itemSetType, String locale) {
		ItemSetCriteriaDTO itemSetCriteria = new ItemSetCriteriaDTO();
		itemSetCriteria.setBasketId(orderCapture.getBasketSelectForm().getSelectedBasketId());
		itemSetCriteria.setLocale(locale);
		itemSetCriteria.setItemSetType(itemSetType);
		itemSetCriteria.setEligibleItemTypeList(Lists.newArrayList(BomLtsConstant.ITEM_TYPE_PLAN));
		itemSetCriteria.setSelectDefault(true);
		itemSetCriteria.setChannelId(orderCapture.getChannelId());
		itemSetCriteria.setDisplayType(BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC);
		return itemSetCriteria;
	}
	
	
	private void setBasketDetailForm(OrderCaptureDTO orderCapture, BasketDetailFormDTO form, String locale){

		String selectedBasketId = orderCapture.getBasketSelectForm().getSelectedBasketId();
		if(form.getPlanItemList() == null || form.getPlanItemList().size() == 0){
			List<ItemDetailDTO> planItemList = itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					selectedBasketId, BomLtsConstant.ITEM_TYPE_PLAN,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, locale, true, false);
			form.setPlanItemList(planItemList);
		}
		
		if(form.getContItemSetList() == null || form.getContItemSetList().size() == 0){
			
			ItemSetCriteriaDTO itemSetCriteria = new ItemSetCriteriaDTO();
			itemSetCriteria.setBasketId(selectedBasketId);
			itemSetCriteria.setLocale(locale);
			itemSetCriteria.setItemSetType(BomLtsConstant.ITEM_SET_TYPE_CONT_SET);
			itemSetCriteria.setEligibleItemTypeList(Lists.newArrayList(BomLtsConstant.ITEM_TYPE_PLAN));
			itemSetCriteria.setSelectDefault(true);
			itemSetCriteria.setChannelId(orderCapture.getChannelId());
			itemSetCriteria.setDisplayType(BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC);
			
			List<ItemSetDetailDTO> contItemSetList = itemDetailService.getBasketItemSetList(getItemSetCriteria(orderCapture, BomLtsConstant.ITEM_SET_TYPE_CONT_SET, locale));
			form.setContItemSetList(contItemSetList);
		}

		if(form.getPremiumItemSetList() == null || form.getPremiumItemSetList().size() == 0){
			List<ItemSetDetailDTO> premiumItemSetList = itemDetailService.getBasketItemSetList(getItemSetCriteria(orderCapture, BomLtsConstant.ITEM_SET_TYPE_PREM_SET, locale));
			form.setPremiumItemSetList(premiumItemSetList);
		}
		
		if(form.getChannelGroupList() == null || form.getChannelGroupList().size() == 0){
			setBundledNowtvChannel(orderCapture, form, locale);
		}
		
		if(form.getBbRentalItemList() == null || form.getBbRentalItemList().size() == 0){
			List<ItemDetailDTO> bbRentalItemList = new ArrayList<ItemDetailDTO>();
			
			bbRentalItemList.addAll(itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					orderCapture.getBasketSelectForm().getSelectedBasketId(), BomLtsConstant.ITEM_TYPE_BB_RENTAL,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCapture.getLang(), true, false));
			bbRentalItemList.addAll(itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					orderCapture.getBasketSelectForm().getSelectedBasketId(), BomLtsConstant.ITEM_TYPE_BB_RENTAL_WAIVE,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCapture.getLang(), true, false));
			
			form.setBbRentalItemList(bbRentalItemList);
		}

		if(form.getInstallFeeItemList() == null || form.getInstallFeeItemList().size() == 0){
			List<ItemDetailDTO> installFeeItemList = new ArrayList<ItemDetailDTO>();
			installFeeItemList.addAll(itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					orderCapture.getBasketSelectForm().getSelectedBasketId(), BomLtsConstant.ITEM_TYPE_INSTALL,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCapture.getLang(), true, false));
			installFeeItemList.addAll(itemDetailService.getBasketItemList(orderCapture.getChannelId(),
					orderCapture.getBasketSelectForm().getSelectedBasketId(), BomLtsConstant.ITEM_TYPE_INSTALL_WAIVE,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCapture.getLang(), true, false));
			
			form.setInstallFeeItemList(installFeeItemList);
		}
	}
	
	
}
	
	