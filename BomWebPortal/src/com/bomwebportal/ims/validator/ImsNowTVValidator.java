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
import com.bomwebportal.ims.dto.ui.ChannelUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.NowTVUI;
import com.bomwebportal.ims.service.ImsNowTVService;
import com.bomwebportal.service.VasDetailService;

public class ImsNowTVValidator implements Validator{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class clazz) {
		return clazz.equals(NowTVUI.class);
	}
	
	private ImsNowTVService imsNowTVService;
	
	public void setImsNowTVService(ImsNowTVService imsNowTVService) {
		this.imsNowTVService = imsNowTVService;
	}

	public ImsNowTVService getImsNowTVService() {
		return imsNowTVService;
	}
	
	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		logger.info("NowTVChannel exclusive validate is called");
		System.out.println("NowTVChannel exclusive validate is called");
	
		try {
			NowTVUI NowTVDetail = (NowTVUI) obj;
			
			
			if (NowTVDetail != null) {

				List<ChannelUI> exclusiveList = new ArrayList<ChannelUI>();

				exclusiveList = NowTVDetail.getExclusiveList();
				
				
				if (exclusiveList != null) {

					if (exclusiveList.size() != 0) {
						errors.rejectValue("exclusiveError", "ims.pcd.nowtv.msg.014");//print to the VASdetail header
						
						for (int i = 0; i < exclusiveList.size(); i++) {
							errors.rejectValue("channelError", "","");
							errors.rejectValue("channelError", "ims.pcd.nowtv.msg.015");
							errors.rejectValue("channelError", "","<"+ exclusiveList.get(i).getChannelDesc()+ ">");
							errors.rejectValue("channelError", "","<"+ exclusiveList.get(i).getChannelDesc_B()+ ">");
							
						}
					}

				} else {
					//System.out.println("exclusiveItemList==null");
					logger.info("exclusiveList==null");

				}

			} else {

				//System.out.println("vasDetail is null");
				logger.info("NowTVDetail is null");
			}

		} catch (Exception e) {
			logger.info("Exception: " + e.getMessage());
			//System.out.println("Exception" + e.getMessage());
		}

	}

}
