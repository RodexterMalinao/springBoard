package com.bomltsportal.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.ShoppingCartFormDTO;
import com.bomltsportal.service.ItemDetailService;
import com.bomltsportal.service.SummaryService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class ShoppingCartController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private SummaryService summaryService;
	private ItemDetailService itemDetailService;
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		logger.info("ShoppingCartController formBackingObject is called");
		
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		ShoppingCartFormDTO form = new ShoppingCartFormDTO();
		
		try{
		if(orderCaptureDTO != null){
			List<ItemDetailDTO> allItems = new ArrayList<ItemDetailDTO>();
			if(orderCaptureDTO.getBasketSelectForm() != null){
				for(OnlineBasketDTO basket :orderCaptureDTO.getBasketSelectForm().getOnlineBasketList()){
					if(basket.isSelected()){
						form.setBasketDetail(basket.getBasketDetail());
						break;
					}
				}
				
				if(orderCaptureDTO.getBasketDetailForm() != null){
					List<ItemDetailDTO> contentItemList = new ArrayList<ItemDetailDTO>();
					List<ItemDetailDTO> premiumItemList = new ArrayList<ItemDetailDTO>();
					for(ItemSetDetailDTO itemSet : orderCaptureDTO.getBasketDetailForm().getContItemSetList()){
						for(ItemDetailDTO itemDetail : itemSet.getItemDetails()){
							if(itemDetail.isSelected()){
								contentItemList.add(itemDetail);
								allItems.add(itemDetail);
							}
						}
					}
					List<ItemSetDetailDTO> premItemSetList = new ArrayList<ItemSetDetailDTO>();
					premItemSetList.addAll(orderCaptureDTO.getBasketDetailForm().getPremiumItemSetList());
					if(BomLtsConstant.SERVICE_TYPE_DEL.equals(orderCaptureDTO.getServiceTypeInd())){
						for(OnlineBasketDTO onlineBasket: orderCaptureDTO.getBasketSelectForm().getOnlineBasketList()){
							if(onlineBasket.isSelected()){
								premItemSetList.addAll(onlineBasket.getPremiumDelItemSetList());
							}
						}
					}
					for(ItemSetDetailDTO itemSet : premItemSetList){
						for(ItemDetailDTO itemDetail : itemSet.getItemDetails()){
							if(itemDetail.isSelected()){
								premiumItemList.add(itemDetail);
								allItems.add(itemDetail);
							}
						}
					}
					form.setContentItemList(contentItemList);
					form.setPremiumItemList(premiumItemList);
					
					List<ItemDetailDTO> bbRental = orderCaptureDTO.getBasketDetailForm().getBbRentalItemList();
					List<ItemDetailDTO> installFee =  orderCaptureDTO.getBasketDetailForm().getInstallFeeItemList();
					if(bbRental != null && bbRental.size() > 0){
						if(StringUtils.isNotEmpty(bbRental.get(0).getRecurrentAmtTxt())){
							form.setServiceCharge(bbRental.get(0).getRecurrentAmtTxt());
						}else{
							form.setServiceCharge(bbRental.get(0).getMthToMthAmtTxt());
						}
					}else{
						form.setServiceCharge("");
					}
					if(installFee != null && installFee.size() > 0){
						form.setInstallFee(installFee.get(0).getOneTimeAmtTxt());
//						if(StringUtils.isNotEmpty(installFee.get(0).getRecurrentAmtTxt()) && !"0".equals((installFee.get(0).getRecurrentAmt()))){
//							form.setInstallFee(installFee.get(0).getRecurrentAmtTxt());
//						}else{
//							form.setInstallFee(installFee.get(0).getMthToMthAmtTxt());
//						}
					}else {
						form.setInstallFee("");
					}
				}
				if(orderCaptureDTO.getVasDetailForm() != null){
					List<ItemDetailDTO> vasMoovItems = new ArrayList<ItemDetailDTO>();
					List<ItemDetailDTO> vasOtherItems = new ArrayList<ItemDetailDTO>();
					List<ChannelDetailDTO> vasNowTvPayItems = new ArrayList<ChannelDetailDTO>();
					List<ChannelDetailDTO> vasNowTvSpecItems = new ArrayList<ChannelDetailDTO>();
						
					if(orderCaptureDTO.getVasDetailForm().getMoovItems() != null){
						for(ItemDetailDTO itemDetail : orderCaptureDTO.getVasDetailForm().getMoovItems()){
							if(itemDetail.isSelected()){
								vasMoovItems.add(itemDetail);
								allItems.add(itemDetail);
							}
						}
					}
					if(orderCaptureDTO.getVasDetailForm().getOtherItems() != null){
						for(ItemDetailDTO itemDetail : orderCaptureDTO.getVasDetailForm().getOtherItems()){
							if(itemDetail.isSelected()){
								vasOtherItems.add(itemDetail);
								allItems.add(itemDetail);
							}
						}
					}
					if(orderCaptureDTO.getVasDetailForm().getSelectedNowTvPayChannelId() != null){
						for(ChannelGroupDetailDTO channelGrp : orderCaptureDTO.getVasDetailForm().getPayChannelGroupList()){
							for(ChannelDetailDTO channel : channelGrp.getChannelDetails()){
								if(orderCaptureDTO.getVasDetailForm().getSelectedNowTvPayChannelId().equals(channel.getChannelId())){
									vasNowTvPayItems.add(channel);
									allItems.add(channel.getItemDetail());
								}
							}
						}
					}
					/*for(ItemDetailDTO itemDetail : orderCaptureDTO.getVasDetailForm().getNowTvPayItems()){
						if(itemDetail.isSelected()){
							vasNowTvPayItems.add(itemDetail);
						}
					}*/
					if(orderCaptureDTO.getVasDetailForm().getSelectedNowTvSpecChannelId() != null){
						for(ChannelGroupDetailDTO channelGrp : orderCaptureDTO.getVasDetailForm().getSpecChannelGroupList()){
							for(ChannelDetailDTO channel : channelGrp.getChannelDetails()){
								if(orderCaptureDTO.getVasDetailForm().getSelectedNowTvSpecChannelId().equals(channel.getChannelId())){
									vasNowTvSpecItems.add(channel);
									allItems.add(channel.getItemDetail());
								}
							}
						}
					}
					/*for(ItemDetailDTO itemDetail : orderCaptureDTO.getVasDetailForm().getNowTvSpecItems()){
						if(itemDetail.isSelected()){
							vasNowTvSpecItems.add(itemDetail);
						}
					}*/
					form.setVasMoovItems(vasMoovItems);
					form.setVasNowTvPayItems(vasNowTvPayItems);
					form.setVasNowTvSpecItems(vasNowTvSpecItems);
					form.setVasOtherItems(vasOtherItems);
				}
				
			}
			if(form.getBasketDetail() != null){
				int totalAmount = 0;
				totalAmount = summaryService.calculateItemPaymentAmount(allItems);
				if(StringUtils.isBlank(form.getBasketDetail().getRecurrentAmt())
						|| "0".equals(form.getBasketDetail().getRecurrentAmt())){
					if(StringUtils.isNotBlank(form.getBasketDetail().getMthToMthAmt())
							&& !"0".equals(form.getBasketDetail().getMthToMthAmt())){
						totalAmount += Integer.parseInt(form.getBasketDetail().getMthToMthAmt());
					}
				}else{
					totalAmount += Integer.parseInt(form.getBasketDetail().getRecurrentAmt());
				}
//				totalAmount += 
				form.setTotalAmount("$" + Integer.toString(totalAmount));
			}
		}
		
//		BomLtsConstant.ITEM_TYPE_INSTALL_WAIVE
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return form;
	}
	public SummaryService getSummaryService() {
		return summaryService;
	}
	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}
	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}
	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}

}
