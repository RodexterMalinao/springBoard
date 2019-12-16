package com.bomwebportal.mob.ccs.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public abstract class MRTBaseValidator implements Validator{
	
	// add by Joyce 20120525
	MobCcsMrtService mobCcsMrtService;
	
	/**
	 * @return the mobCcsMrtService
	 */
	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	/**
	 * @param mobCcsMrtService the mobCcsMrtService to set
	 */
	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}
	

	// add by Joyce 20120525
	/** 
	 * 
	 * @param sessionBasket
	 * @param sessionMnp
	 * @param errors
	 * 
	 * Golden Number Checking
	 * <br>
	 * New number + MRT level = GA, GB, GC, GD, GE or GF
	 * 
	 */
	public void goldenNumCheck(Log logger, BasketDTO sessionBasket, 
								MnpDTO sessionMnp, Errors errors, String appDate) {
		
		logger.debug("msisdnlvl@sessionMnp: " + sessionMnp.getMsisdnLvl());
		
		//Dennis MIP3
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)sessionMnp.getValue("customer");
		List<String> goldenPool =  mobCcsMrtService.getGoldenNumLvL(false);
		
		if (sessionBasket != null && sessionMnp.getMsisdnLvl() != null) {
			
			if (goldenPool != null && !goldenPool.isEmpty()) {
				if (goldenPool.contains(sessionMnp.getMsisdnLvl())) {
					/*
					 * service plan and contract period will limit msisdn selection
					 * base on msisdn Lvl
					 */
					
					String type = getSbType();
					String errorPath = null;
					String displayMRTLvl = "Subscribed MRT level : " + sessionMnp.getMsisdnLvl();
					String displayErrMsg = "";
					
					if ("VasDetail".equalsIgnoreCase(type)) {
						errorPath = VasDetailDTO.ERROR_PATH;
					} else if ("MobCcsMrt".equalsIgnoreCase(type)) {
						errorPath = MRTUI.ERROR_PATH;
					}
					
					if (StringUtils.isBlank(appDate)) {
						appDate = Utility.date2String(new Date(), 
													BomWebPortalConstant.DATE_FORMAT);
					}
					
					List<HashMap<String, Integer>> validMapList = mobCcsMrtService
							.getGoldenNumLvlCondDtl(sessionMnp.getMsisdnLvl(),
													appDate, sessionCustomer.getBrand());
				
					// add by Joyce 20120626
					if (StringUtils.isBlank(sessionBasket.getContractPeriod())) {
						sessionBasket.setContractPeriod("0");
					}
					if (StringUtils.isBlank(sessionBasket.getRecurrentAmt())) {
						sessionBasket.setRecurrentAmt("0");
					}
					//add by Vincy 20141029
					if (StringUtils.isBlank(sessionBasket.getGrossPlanFee())) {
						sessionBasket.setGrossPlanFee("0");
					}
					
					boolean isPlanError = true;
					List<String> planErrorMessage = new ArrayList<String>();
				
			    boolean newGoldenNumRulesInd =  mobCcsMrtService.getNewGoldenNumRulesInd(Utility.string2Date(appDate));
				for (HashMap<String, Integer> validMap: validMapList) {
						/*System.out.println("CONTRACT_PERIOD:"
								+ String.valueOf(validMap.get("CONTRACT_PERIOD")));
						System.out.println("RP_RECUR_CHARGE:"
								+ String.valueOf(validMap.get("RP_RECUR_CHARGE")));
						System.out.println("IS_VIP:"
								+ String.valueOf(validMap.get("IS_VIP")));*/
				  
						//boolean 11-10 
					if(! newGoldenNumRulesInd){
							if ((Integer.parseInt(sessionBasket.getContractPeriod()) >= validMap.get("CONTRACT_PERIOD"))
									&& (Integer.parseInt(sessionBasket.getRecurrentAmt()) >= validMap.get("RP_RECUR_CHARGE"))) {
								if (validMap.get("IS_VIP") != 1) {
									isPlanError = false;
								} else {
									planErrorMessage.add(displayMRTLvl);
									displayErrMsg = "Co-approval by Chief Marketing Officer & head of Mobile Marketing is required. Please issue order through BOM.";
									planErrorMessage.add(displayErrMsg);
								}
							} else  {
								displayErrMsg = "Please choose rate plan >= $" 
														+ String.valueOf(validMap.get("RP_RECUR_CHARGE"))
														+ " and contract period >= " 
														+ String.valueOf(validMap.get("CONTRACT_PERIOD")) 
														+ " months";
								planErrorMessage.add(displayMRTLvl);
								planErrorMessage.add(displayErrMsg);
							}
							
							
					}
						
					else {
						
							if ((Integer.parseInt(sessionBasket.getContractPeriod()) >= validMap.get("CONTRACT_PERIOD"))
									&& (Integer.parseInt(sessionBasket.getGrossPlanFee()) >= validMap.get("GROSS_PLAN_FEE"))) {
								if (validMap.get("IS_VIP") != 1) {
									isPlanError = false;
								} else {
									planErrorMessage.add(displayMRTLvl);
									displayErrMsg = "Co-approval by Chief Marketing Officer & head of Mobile Marketing is required. Please issue order through BOM.";
									planErrorMessage.add(displayErrMsg);
								}
							} else {
										if ( !"Y".equalsIgnoreCase(sessionBasket.getByPassGoldenNum()) ){
											displayErrMsg = "Please choose gross plan >= $" 
																	+ String.valueOf(validMap.get("GROSS_PLAN_FEE"))
																	+ " and contract period >= " 
																	+ String.valueOf(validMap.get("CONTRACT_PERIOD")) 
																	+ " months";
											planErrorMessage.add(displayMRTLvl);
											planErrorMessage.add(displayErrMsg);
											sessionBasket.setShowGoldenNumAuth("Y");
										}
							 		}	
					 }
					}
			
					if (isPlanError) {
						for (String msg: planErrorMessage) {
							errors.rejectValue(errorPath, "dummy", msg);
						}
					}
				}
			}
		}
	}
	
	abstract protected String getSbType();
		
}
