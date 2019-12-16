package com.bomwebportal.ims.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.ExclusiveItemDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.ims.dto.ui.BasketDetailUI;
import com.bomwebportal.ims.dto.ui.ChannelUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.NowTVUI;
import com.bomwebportal.ims.dto.ui.VASDetailUI;
import com.bomwebportal.ims.service.ImsBasketDetailsService;
import com.bomwebportal.ims.service.ImsNowTVService;
import com.bomwebportal.service.VasDetailService;

public class ImsVASValidator implements Validator{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class clazz) {
		return clazz.equals(BasketDetailUI.class);
	}
	
	private ImsBasketDetailsService imsBasketDetailsService;
	
	public void setImsBasketDetailsService(ImsBasketDetailsService imsBasketDetailsService) {
		this.imsBasketDetailsService = imsBasketDetailsService;
	}

	public ImsBasketDetailsService getImsBasketDetailsService() {
		return imsBasketDetailsService;
	}
	
	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		logger.info("BasketDetailsVAS exclusive validate is called");
		System.out.println("BasketDetailsVAS exclusive validate is called");
	
		try {
			BasketDetailUI BasketDetail = (BasketDetailUI) obj;
			
			
			if (BasketDetail != null) {

				List<VASDetailUI> exclusiveList = new ArrayList<VASDetailUI>();

				exclusiveList = BasketDetail.getExclusiveItemList();
				
				List<VASDetailUI> nowExclusiveList = new ArrayList<VASDetailUI>();

				nowExclusiveList = BasketDetail.getNowExclusiveItemList();

				
//				if (exclusiveList != null) {
//
//					if (exclusiveList.size() != 0) {
//						errors.rejectValue("exclusiveError", "",	"VAS selection error");//print to the VASdetail header
//						
//						for (int i = 0; i < exclusiveList.size(); i++) {
//							errors.rejectValue("VASError", "","");
//							errors.rejectValue("VASError", "","VAS cannot be selected together:");
//							errors.rejectValue("VASError", "","<"+ exclusiveList.get(i).getVASTitle()+ ">");
//							errors.rejectValue("VASError", "","<"+ exclusiveList.get(i).getVASTitle_B()+ ">");
//							
//						}
//					}
//
//				} else {
//					//System.out.println("exclusiveItemList==null");
//					logger.info("exclusiveList==null");
//
//				}
//				
//				if (nowExclusiveList != null) {
//
//					if (nowExclusiveList.size() > 1) {
//						if(exclusiveList!=null&&exclusiveList.size() == 0){
//							errors.rejectValue("exclusiveError", "",	"VAS selection error");//print to the VASdetail header
//						}
//						errors.rejectValue("VASError", "","");
//						errors.rejectValue("VASError", "","VAS cannot be selected together:");
//						
//						for (int i = 0; i < 2; i++) {
//							errors.rejectValue("VASError", "","<"+ nowExclusiveList.get(i).getVASTitle()+ ">");
//						}
//					}
//
//				} else {
//					//System.out.println("exclusiveItemList==null");
//					logger.info("nowExclusiveList==null");
//
//				}

			} else {

				//System.out.println("vasDetail is null");
				logger.info("vasDetail is null");
			}

		} catch (Exception e) {
			logger.info("Exception: " + e.getMessage());
			//System.out.println("Exception" + e.getMessage());
		}

	}

}
