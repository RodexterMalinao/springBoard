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
import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomltsportal.dto.form.BasketDetailSlideFormDTO;
import com.bomltsportal.service.BasketDetailService;
import com.bomltsportal.service.ItemDetailService;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class BasketDetailSlideController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ItemDetailService itemDetailService;
	private final String nextView = "basketdetailredirect";
	private final String currView = "basketdetailslide.html";
	private BasketDetailService basketDetailService;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		if(orderCapture == null || orderCapture.isOrderSignoffed()){
			return SessionHelper.getSessionTimeOutView();
		}	
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		BasketDetailSlideFormDTO slideForm = new BasketDetailSlideFormDTO();
		List<BasketDetailFormDTO> basketFormList = new ArrayList<BasketDetailFormDTO>();

		slideForm.setSelectedBasketId(request.getParameter("basketId"));
		
		for(int i = 0; i < orderCapture.getBasketSelectForm().getOnlineBasketList().size(); i++){
			OnlineBasketDTO basket = orderCapture.getBasketSelectForm().getOnlineBasketList().get(i);
			BasketDetailFormDTO basketDetail = new BasketDetailFormDTO();
			String basketId = basket.getBasketDetail().getBasketId();
			
			basketDetailService.setBasketDetailForm(orderCapture, basketDetail, orderCapture.getLang(), basketId);
			basketDetail.setDisplayBasketId(basketId);
			basketDetail.setBasketDetail(basket.getBasketDetail());
			basketFormList.add(i, basketDetail);
		}

		slideForm.setBasketFormList(basketFormList);
		
		if(orderCapture.getBasketDetailForm() != null){
			for(int i = 0; i < slideForm.getBasketFormList().size(); i++){
				BasketDetailFormDTO basketDetailForm = slideForm.getBasketFormList().get(i);
				if(StringUtils.equals(orderCapture.getBasketDetailForm().getDisplayBasketId(), basketDetailForm.getDisplayBasketId())){
					slideForm.getBasketFormList().set(i, orderCapture.getBasketDetailForm());
					break;
				}
			}
			
			
			/*for(BasketDetailFormDTO basketDetailForm : slideForm.getBasketFormList()){
				if(StringUtils.equals(orderCapture.getBasketDetailForm().getDisplayBasketId(), basketDetailForm.getDisplayBasketId())){
					basketDetailForm = orderCapture.getBasketDetailForm();
					break;
				}
			}*/
		}
		
		return slideForm;
	}

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
			throws ServletException {
		logger.info("BasketDetailSlideController onSubmit is called");
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		BasketDetailSlideFormDTO slideForm = (BasketDetailSlideFormDTO)command;
		
		if(StringUtils.isEmpty(slideForm.getSelectedBasketId())){
			return new ModelAndView(new RedirectView(currView));
		}
		
		for(BasketDetailFormDTO basketDetailForm : slideForm.getBasketFormList()){
			if(slideForm.getSelectedBasketId().equals(basketDetailForm.getDisplayBasketId())){
				if(!StringUtils.isEmpty(basketDetailForm.getSelectedNowTvGroupId()) && !StringUtils.isEmpty(basketDetailForm.getSelectedNowTvChannelId())){
					for(ChannelGroupDetailDTO channelGroup: basketDetailForm.getChannelGroupList()){
						if(StringUtils.equals(basketDetailForm.getSelectedNowTvGroupId(), channelGroup.getChannelGroupId())){
							channelGroup.setSelected(true);
						}else{
							channelGroup.setSelected(false);
						}
						for(ChannelDetailDTO channelDetail : channelGroup.getChannelDetails()){
							if(StringUtils.equals(channelDetail.getChannelId(), basketDetailForm.getSelectedNowTvChannelId())){
								channelDetail.setSelected(true);
							}else{
								channelDetail.setSelected(false);
							}
						}
					}
				}else{
					basketDetailForm.setSelectedNowTvGroupId(null);
					basketDetailForm.setSelectedNowTvChannelId(null);
				}
				for(ItemSetDetailDTO itemSet: basketDetailForm.getPremiumItemSetList()){
					for(ItemDetailDTO item: itemSet.getItemDetails()){
						if(item.getItemId().equals(itemSet.getSelectedItemId())){
							item.setSelected(true);
						}else{
							item.setSelected(false);
						}
					}
				}

				orderCapture.setBasketDetailForm(basketDetailForm);
				
				break;
			}
		}

//		ModelAndView mav = new ModelAndView(new RedirectView(nextView + "?basketId=" + slideForm.getSelectedBasketId()));
//		mav.addObject("basketId", slideForm.getSelectedBasketId());
		
		ModelAndView mav = new ModelAndView(nextView, "basketId", slideForm.getSelectedBasketId());
		
		return mav;
		
//		return new ModelAndView(new RedirectView(nextView));
	}
	
	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}

	public BasketDetailService getBasketDetailService() {
		return basketDetailService;
	}

	public void setBasketDetailService(BasketDetailService basketDetailService) {
		this.basketDetailService = basketDetailService;
	}
}
