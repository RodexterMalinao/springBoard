package com.bomltsportal.web;

import java.util.ArrayList;
import java.util.List;

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

import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.VasDetailFormDTO;
import com.bomltsportal.service.ItemDetailService;
import com.bomltsportal.service.NowTvChannelService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;


public class VasDetailController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ItemDetailService itemDetailService;
	private NowTvChannelService nowTvChannelService;
	private final String nextView = "applicantinfo.html";
	private final String viewName = "vasdetail";
	private final String commandName = "vasDetailForm";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		if(orderCapture == null || orderCapture.isOrderSignoffed()){
			return SessionHelper.getSessionTimeOutView();
		}else{
			for(OnlineBasketDTO basket : orderCapture.getBasketSelectForm().getOnlineBasketList()){
				if(basket.isSelected() && BomLtsConstant.DEVICE_TYPE_EYE3A.equals(basket.getBasketDetail().getType())){
					orderCapture.setVasDetailForm(new VasDetailFormDTO());
					return new ModelAndView(new RedirectView(nextView));
				}
			}
		}
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.info("VasDetailController formBackingObject is called");
		
		VasDetailFormDTO form;
		SessionHelper.setupSession(request);
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		orderCaptureDTO.setTopNavInd(BomLtsConstant.TOP_NAV_IND_SELECT_SERVICE);
		if(orderCaptureDTO.getVasDetailForm() != null){
			form = orderCaptureDTO.getVasDetailForm();
		}else{
			form = new VasDetailFormDTO();
		}

		String channelId = null;
		String selectedBasketId = orderCaptureDTO.getBasketSelectForm().getSelectedBasketId();
		
		if(BomLtsConstant.SERVICE_TYPE_EYE.equals(orderCaptureDTO.getServiceTypeInd())){
			channelId = BomLtsConstant.CHANNEL_ID_ONLINE_EYE;
			//get nowtv items
			if(StringUtils.isEmpty(orderCaptureDTO.getBasketDetailForm().getSelectedNowTvGroupId()) ||
					StringUtils.isEmpty(orderCaptureDTO.getBasketDetailForm().getSelectedNowTvChannelId()))
			{
				String deviceType = "";//"1020";
				String basketId = orderCaptureDTO.getBasketSelectForm().getSelectedBasketId();
				for(OnlineBasketDTO basket : orderCaptureDTO.getBasketSelectForm().getOnlineBasketList()){
					if(basket.isSelected()){
						deviceType = basket.getBasketDetail().getType();
						break;
					}
				}
				if(form.getNowTvPayItems() == null || form.getNowTvPayItems().size() == 0){
					form.setNowTvPayItems(itemDetailService.getBasketItemList(
					channelId, basketId, BomLtsConstant.ITEM_TYPE_NOWTV_PAY, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCaptureDTO.getLang(), true, false));
					if(form.getNowTvPayItems() != null && form.getNowTvPayItems().size() != 0){
						int largestCredit = 0;
						for(ItemDetailDTO nowTvPayItem : form.getNowTvPayItems()){
							int currCredit = Integer.parseInt(nowTvPayItem.getCredit());
							largestCredit = currCredit>largestCredit?currCredit:largestCredit;
						}
						form.setPayChannelGroupList(nowTvChannelService.getChannelGroupList(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, deviceType, orderCaptureDTO.getLang(), false, Integer.toString(largestCredit)));
						if(form.getPayChannelGroupList() != null){
							for(ChannelGroupDetailDTO channelGroup: form.getPayChannelGroupList()){
								if(channelGroup.getChannelDetails() != null && channelGroup.getChannelDetails().length > 0){
									for(ChannelDetailDTO channel : channelGroup.getChannelDetails()){
										for(ItemDetailDTO nowTvItem: form.getNowTvPayItems()){
											if(StringUtils.equals(channel.getCredit(), nowTvItem.getCredit())){
												channel.setItemDetail(nowTvItem);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				if(form.getNowTvSpecItems() == null || form.getNowTvSpecItems().size() == 0){
					form.setNowTvSpecItems(itemDetailService.getBasketItemList(
					channelId, basketId, BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCaptureDTO.getLang(), true, false));
					if(form.getNowTvSpecItems() != null && form.getNowTvSpecItems().size() != 0){
						int largestCredit = 0;
						for(ItemDetailDTO nowTvSpecItem : form.getNowTvSpecItems()){
							int currCredit = Integer.parseInt(nowTvSpecItem.getCredit());
							largestCredit = currCredit>largestCredit?currCredit:largestCredit;
						}
						form.setSpecChannelGroupList(nowTvChannelService.getChannelGroupList(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, deviceType, orderCaptureDTO.getLang(), false, Integer.toString(largestCredit)));
						if(form.getSpecChannelGroupList() != null){
							for(ChannelGroupDetailDTO channelGroup: form.getSpecChannelGroupList()){
								if(channelGroup.getChannelDetails() != null && channelGroup.getChannelDetails().length > 0){
									for(ChannelDetailDTO channel : channelGroup.getChannelDetails()){
										for(ItemDetailDTO nowTvItem: form.getNowTvSpecItems()){
											if(StringUtils.equals(channel.getCredit(), nowTvItem.getCredit())){
												channel.setItemDetail(nowTvItem);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}else{
				form.setSelectedAdultChannel(false);
				form.setSelectedNowTv(false);
				form.setSelectedNowTvPayChannelId(null);
				form.setSelectedNowTvSpecChannelId(null);
				form.setSelectedNowTvPayGroupId(null);
				form.setSelectedNowTvSpecGroupId(null);
				form.setNowTvPayItems(null);
				form.setNowTvSpecItems(null);
				form.setPayChannelGroupList(null);
				form.setSpecChannelGroupList(null);
				form.setSelectedAdultChannel(false);
			}


			//get MOOV and Other items
			List<ItemSetDetailDTO> contItemSetList = orderCaptureDTO.getBasketDetailForm().getContItemSetList();
			List<ItemDetailDTO> selectedContItemList = new ArrayList<ItemDetailDTO>();
			List<ItemDetailDTO> itemDetailListB = new ArrayList<ItemDetailDTO>();
			
			List<ItemDetailDTO> moovItems = itemDetailService.getBasketItemList(channelId, selectedBasketId, BomLtsConstant.ITEM_TYPE_MOOV, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCaptureDTO.getLang(), true, false);
			if(form.getMoovItems() != null && !form.getMoovItems().isEmpty()){
				for(ItemDetailDTO moovItem :moovItems){
					moovItem.setSelected(getSelectedItemIdList(form.getMoovItems()).contains(moovItem.getItemId()));
				}
			}
			itemDetailListB.addAll(moovItems);
			
			List<ItemDetailDTO> otherItems = itemDetailService.getItemList(channelId, BomLtsConstant.ITEM_TYPE_VAS_HOT, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCaptureDTO.getLang(), true, false);
			if(form.getOtherItems() != null && !form.getOtherItems().isEmpty()){
				for(ItemDetailDTO otherItem :otherItems){
					otherItem.setSelected(getSelectedItemIdList(form.getOtherItems()).contains(otherItem.getItemId()));
				}
			}
			itemDetailListB.addAll(otherItems);
			
			//exclusion check
			if(!itemDetailListB.isEmpty() && contItemSetList != null && !contItemSetList.isEmpty()){
				for(ItemSetDetailDTO contItemSet : contItemSetList){
					for(ItemDetailDTO itemDetail :contItemSet.getItemDetails()){
						if(itemDetail.isSelected()){
							selectedContItemList.add(itemDetail);
						}
					}
				}
				List<ExclusiveItemDetailDTO> exclusiveItemList = itemDetailService.getExclusiveItemDetailList(selectedContItemList, itemDetailListB, orderCaptureDTO.getLang(), false);
				List<ItemDetailDTO> unwantedItemList = new ArrayList<ItemDetailDTO>();
				for(ItemDetailDTO item : itemDetailListB){
					for(ExclusiveItemDetailDTO exclusiveItem : exclusiveItemList){
						if(StringUtils.equals(item.getItemId(), exclusiveItem.getItemAId())
								|| StringUtils.equals(item.getItemId(), exclusiveItem.getItemBId())){
							unwantedItemList.add(item);
							break;
						}
					}
				}
				itemDetailListB.removeAll(unwantedItemList);

				moovItems = new ArrayList<ItemDetailDTO>();
				otherItems = new ArrayList<ItemDetailDTO>();
				for(ItemDetailDTO item : itemDetailListB){
					if(BomLtsConstant.ITEM_TYPE_MOOV.equals(item.getItemType())){
						moovItems.add(item);
					}else{
						otherItems.add(item);
					}
				}
				
				
				form.setMoovItems(moovItems);
				form.setOtherItems(otherItems);
			}
		}else if(BomLtsConstant.SERVICE_TYPE_DEL.equals(orderCaptureDTO.getServiceTypeInd())){
			channelId = BomLtsConstant.CHANNEL_ID_ONLINE_DEL;
			List<ItemDetailDTO> otherItems = itemDetailService.getItemList(channelId, BomLtsConstant.ITEM_TYPE_ONLINE_VAS, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCaptureDTO.getLang(), true, false);
			if(form.getOtherItems() != null && !form.getOtherItems().isEmpty()){
				for(ItemDetailDTO otherItem :otherItems){
					otherItem.setSelected(getSelectedItemIdList(form.getOtherItems()).contains(otherItem.getItemId()));
				}
			}
			form.setOtherItems(otherItems);
		}
		
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException {
		logger.info("VasDetailController onSubmit is called");
		logger.info("Next View: " + nextView);
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		VasDetailFormDTO form = (VasDetailFormDTO) command;

		/*validate exclusive item*/
		List<ItemDetailDTO> vasItemList = new  ArrayList<ItemDetailDTO>();
		if(form.getMoovItems() != null){
			vasItemList.addAll(form.getMoovItems());
		}
		if(form.getNowTvPayItems() != null){
			vasItemList.addAll(form.getNowTvPayItems());
		}
		if(form.getNowTvSpecItems() != null){
			vasItemList.addAll(form.getNowTvSpecItems());
		}
		if(form.getOtherItems() != null){
			vasItemList.addAll(form.getOtherItems());
		}
		
		ValidationResultDTO result = itemDetailService.validateExclusiveItem(vasItemList, vasItemList, BomLtsConstant.LOCALE_ENG);
		if (Status.INVAILD == result.getStatus()) {
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			mav.addObject("errorMsgList", StringUtils.join(result.getMessageList(), "<br/>"));
			return mav;
		}
		
		if(form.getPayChannelGroupList() != null){
			String credit = null;
			for(ChannelGroupDetailDTO channelGroup :form.getPayChannelGroupList()){
				if(channelGroup.getChannelGroupId().equals(form.getSelectedNowTvPayGroupId())){
					channelGroup.setSelected(true);
					for(ChannelDetailDTO channelDetail:  channelGroup.getChannelDetails()){
						if(channelDetail.getChannelId().equals(form.getSelectedNowTvPayChannelId())){
							channelDetail.setSelected(true);
							credit = channelDetail.getCredit();
							break;
						}else{
							channelDetail.setSelected(false);
						}
					}
					break;
				}else{
					channelGroup.setSelected(false);
				}
			}

			if(credit != null){
				for(ItemDetailDTO nowTvItem :form.getNowTvPayItems()){
					if(credit.equals(nowTvItem.getCredit())){
						nowTvItem.setSelected(true);
						break;
					}
				}
			}
		}
		if(form.getSpecChannelGroupList() != null){
			String credit = null;
			for(ChannelGroupDetailDTO channelGroup :form.getSpecChannelGroupList()){
				if(channelGroup.getChannelGroupId().equals(form.getSelectedNowTvSpecGroupId())){
					channelGroup.setSelected(true);
					for(ChannelDetailDTO channelDetail:  channelGroup.getChannelDetails()){
						if(channelDetail.getChannelId().equals(form.getSelectedNowTvSpecChannelId())){
							channelDetail.setSelected(true);
							credit = channelDetail.getCredit();
							break;
						}else{
							channelDetail.setSelected(false);
						}
					}
					break;
				}else{
					channelGroup.setSelected(false);
				}
			}

			if(credit != null){
				for(ItemDetailDTO nowTvItem :form.getNowTvSpecItems()){
					if(credit.equals(nowTvItem.getCredit())){
						nowTvItem.setSelected(true);
						break;
					}
				}
			}
		}
		

		
		
		orderCapture.setVasDetailForm(form);
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private List<String> getSelectedItemIdList(List<ItemDetailDTO> itemDetailList){
		List<String> selectedItemIdList = new ArrayList<String>();
		for(ItemDetailDTO item: itemDetailList){
			if(item.isSelected()){
				selectedItemIdList.add(item.getItemId());
			}
		}
		return selectedItemIdList;
	}

	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}

	public NowTvChannelService getNowTvChannelService() {
		return nowTvChannelService;
	}

	public void setNowTvChannelService(NowTvChannelService nowTvChannelService) {
		this.nowTvChannelService = nowTvChannelService;
	}
}
	
	