package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.service.LtsViQuadplayService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.wsViQuadplayClient.SBGetProfileSubscribedPlanResponse;
import com.bomwebportal.lts.wsViQuadplayClient.SpChannel;
import com.bomwebportal.lts.wsViQuadplayClient.SpProfileSubscribedPlan;

public class LtsNowTvChannelController extends AbstractController {

	
	private static final String STATUS_ACTIVE = "ACTIVE";
	
	private Locale locale;
	private MessageSource messageSource;
	
	private final String viewName = "ltsnowtvchannel";
	
	protected LtsViQuadplayService ltsViQuadplayService;
	
	public LtsViQuadplayService getLtsViQuadplayService() {
		return ltsViQuadplayService;
	}

	public void setLtsViQuadplayService(LtsViQuadplayService ltsViQuadplayService) {
		this.ltsViQuadplayService = ltsViQuadplayService;
	}


	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fsa = request.getParameter("fsa");
		
		ModelAndView mav = new ModelAndView(viewName);
		
		
		String locale = RequestContextUtils.getLocale(request).toString();
		
		
		try {
			List<ChannelDetailDTO> channelDetailList = new ArrayList<ChannelDetailDTO>(); 
			
			SBGetProfileSubscribedPlanResponse profileSubscribedPlan = ltsViQuadplayService
					.getProfileSubscirbedPlan(fsa);
			
			float arpu = ltsViQuadplayService.getARPU(fsa);
			
			
			if (profileSubscribedPlan == null) {
				mav.addObject("errorMsg", messageSource.getMessage("lts.ltsNowTVChannel.noRec", new Object[] {}, this.locale));
				return mav;
			}
			
			List<SpProfileSubscribedPlan> subscribePlanList = profileSubscribedPlan.getProfSubPlen();
			
			for (int i=0; subscribePlanList != null && i < subscribePlanList.size(); i++) {
				
				if (!StringUtils.equals(STATUS_ACTIVE, subscribePlanList.get(i).getStatus())) {
					continue;
				}
				
				List<SpChannel> spChannelList = subscribePlanList.get(i).getChannels();
				
				for (int j=0; spChannelList != null && j < spChannelList.size(); j++) {
					if (!StringUtils.equals(STATUS_ACTIVE, spChannelList.get(j).getStatus())) {
						continue;
					}
					
					ChannelDetailDTO channelDetail = new ChannelDetailDTO();
					channelDetail.setChannelId(spChannelList.get(j).getChannelId());
					if (StringUtils.equals(locale, LtsConstant.LOCALE_CHI)) {
						channelDetail.setChannelHtml(spChannelList.get(j).getNameChi());
					}
					else {
						channelDetail.setChannelHtml(spChannelList.get(j).getNameEng());
					}
					channelDetailList.add(channelDetail);
				}
			}
			
			if (channelDetailList.isEmpty()) {
				mav.addObject("errorMsg", messageSource.getMessage("lts.ltsNowTVChannel.noRec", new Object[] {}, this.locale));
			}
			else {
				mav.addObject("channelDetailList", channelDetailList);
				mav.addObject("arpu", arpu);
//				mav.addObject("channelDetailList", getSampleChannelDetailList());
//				mav.addObject("arpu", 184.5f);
			}
			
		}
		catch (Exception e) {
			mav.addObject("errorMsg", e.getMessage());
			return mav;
		}
		
		return mav;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
//	private List<ChannelDetailDTO> getSampleChannelDetailList() {
//		List<ChannelDetailDTO> sampleList = new ArrayList<ChannelDetailDTO>();
//		
//		ChannelDetailDTO channelDetail = null;
//		
//		channelDetail = new ChannelDetailDTO();
//		channelDetail.setChannelId("111");
//		channelDetail.setChannelHtml("Apple Channel");
//		sampleList.add(channelDetail);
//		
//		channelDetail = new ChannelDetailDTO();
//		channelDetail.setChannelId("222");
//		channelDetail.setChannelHtml("DoG Channel");
//		sampleList.add(channelDetail);
//		
//		return sampleList;
//	}
	

}
