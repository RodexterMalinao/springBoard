package com.bomwebportal.validator;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.SummaryDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class SummaryValidator implements Validator {
	
	protected final Log logger = LogFactory.getLog(getClass());

	private MobileSimInfoService mobileSimInfoService;
	private MultiSimInfoService multiSimInfoService;
	private ValidateService validateService;
	private OrderDAO orderDAO;
	
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public MobileSimInfoService getMobileSimInfoService() {
		return mobileSimInfoService;
	}

	public void setMobileSimInfoService(MobileSimInfoService mobileSimInfoService) {
		this.mobileSimInfoService = mobileSimInfoService;
	}

	
	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}

	
	
	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}



	// MNP cutover date & time validation
	private MnpService mnpService;
	private String mobile2GRetiredInd;	
	
	public MnpService getMnpService() {
		return mnpService;
	}

	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	public String getMobile2GRetiredInd() {
		return mobile2GRetiredInd;
	}

	public void setMobile2GRetiredInd(String mobile2gRetiredInd) {
		mobile2GRetiredInd = mobile2gRetiredInd;
	}
	
	public boolean supports(Class clazz) {
		return clazz.equals(SummaryDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		

		SummaryDTO summaryDTO = (SummaryDTO) obj;
		
		List<String> multiSimInfoMRTList = new ArrayList<String>();
		List<String> multiSimInfoNewMRTList = new ArrayList<String>();
		for (MultiSimInfoDTO msi: summaryDTO.getMultiSimInfoList()) {
			if (msi.getMembers() != null) {
				for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
					if ("MUPS01".equals(msim.getMemberOrderType()) || "MUPS02".equals(msim.getMemberOrderType())) {
						multiSimInfoMRTList.add(msim.getMsisdn());
						multiSimInfoNewMRTList.add(msim.getMsisdn());
						if ("A".equals(msim.getMnpInd())) {
							multiSimInfoMRTList.add(msim.getMnpNumber());
						}
					}
				}
			}
		}
		
		if ("Y".equals(summaryDTO.getMnpDTO().getMnp())) {
			if (multiSimInfoMRTList.contains(summaryDTO.getMnpDTO().getMnpMsisdn())) {
				errors.rejectValue("summaryError", "dummy", "The MNP Service Number is conflicted with Service number in Multi-SIM options.");
			}
		} else {
			if (multiSimInfoMRTList.contains(summaryDTO.getMnpDTO().getMsisdn())) {
				errors.rejectValue("summaryError", "dummy", "The new Service Number is conflicted with service number in Multi-SIM options.");
			}
			if (summaryDTO.getMnpDTO().isFutherMnp()) {
				if (multiSimInfoMRTList.contains(summaryDTO.getMnpDTO().getFutherMnpNumber())) {
					errors.rejectValue("summaryError", "dummy", "The futher MNP Service Number is conflicted with service number in Multi-SIM options.");
				}
			}
		}
		
		if (summaryDTO.getMultiSimInfoList() != null && summaryDTO.getMultiSimInfoList().size() > 0) {
			for (MultiSimInfoDTO msi : summaryDTO.getMultiSimInfoList()) {
				for (MultiSimInfoMemberDTO msim : msi.getMembers()) {
					if ("10".equals(summaryDTO.getMnpDTO().getChannelId()) || "11".equals(summaryDTO.getMnpDTO().getChannelId())) {
						try {
							String orderIdUsingSameMRT = "";
								orderIdUsingSameMRT = orderDAO.getOrderIdUsingSameMRTShop(msim.getMsisdn(), summaryDTO.getOrderId(),summaryDTO.getMnpDTO().getShopCd());
							if (StringUtils.isNotEmpty(orderIdUsingSameMRT) && !summaryDTO.isIgnoreSameMRTcheckbox()) {
								errors.rejectValue("warnSameMRT", "dummy", 
										"Same MRT \"" +msim.getMsisdn() + "\" is already used in existing order(s) \"" 
										+ orderIdUsingSameMRT + "\"");
							}
						} catch (DAOException e) {
							logger.error("Exception caught in validate", e);
						}
					} else {
						String conflictOrderId = multiSimInfoService.validateSBPendingOrder(msim.getMsisdn(), summaryDTO.getOrderId());
						if (conflictOrderId != null) {
							errors.rejectValue("summaryError", "dummy", "Secondary MRT " + msim.getMsisdn() + " has pending order (" + conflictOrderId + ") in SB. Please go back and change before saving." );
						}
					}
				}
			} 
		}
		
		try {
			String orderIdUsingSameMRT = "";
			if ("10".equals(summaryDTO.getMnpDTO().getChannelId()) || "11".equals(summaryDTO.getMnpDTO().getChannelId())) {
				orderIdUsingSameMRT = orderDAO.getOrderIdUsingSameMRTShop(summaryDTO.getMsisdn(), summaryDTO.getOrderId(),summaryDTO.getMnpDTO().getShopCd());
			} else {
				orderIdUsingSameMRT = orderDAO.getOrderIdUsingSameMRT(summaryDTO.getMsisdn(), summaryDTO.getOrderId());
			}
			
			System.out.println("orderIdUsingSameMRT = " + orderIdUsingSameMRT);
			System.out.println("isIgnoreSameMRTcheckbox = " + summaryDTO.isIgnoreSameMRTcheckbox());
			
			if (StringUtils.isNotEmpty(orderIdUsingSameMRT) && !summaryDTO.isIgnoreSameMRTcheckbox()) {
				errors.rejectValue("warnSameMRT", "dummy", 
						"Same MRT \"" + summaryDTO.getMsisdn() + "\" is already used in existing order(s) \"" 
						+ orderIdUsingSameMRT + "\"");
			}

		} catch (DAOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//check cnm Inv status (all MULTISIM NEW MRT)
		if ( "1".equals(summaryDTO.getMnpDTO().getChannelId())  ){ 
			
			/*if (summaryDTO.getMnpDTO()!=null && !"Y".equals(summaryDTO.getMnpDTO().getMnp())){
				MnpDTO parentOutMnpDto = mnpService.validateNewMsisdn(summaryDTO.getMnpDTO());
				if (parentOutMnpDto == null || ( parentOutMnpDto !=null && BomWebPortalConstant.CNM_STATUS_NORMAL != parentOutMnpDto.getCnmStatus()) ){
					errors.rejectValue("hasAlert", "dummy",	"MRT Selection Error: " + mnpDto.getNewMsisdn() + " is not free in CNM inventory.");
				}
			}*/
			for (MultiSimInfoDTO msi : summaryDTO.getMultiSimInfoList()) {
				for (MultiSimInfoMemberDTO msim : msi.getMembers()) {
					if (multiSimInfoNewMRTList != null && multiSimInfoNewMRTList.size() > 0){
						for (String newMsisdn: multiSimInfoNewMRTList){
							MnpDTO inMnpDto = new MnpDTO();
							inMnpDto.setNewMsisdn(newMsisdn);
							inMnpDto.setShopCd(summaryDTO.getMnpDTO().getShopCd());
							MnpDTO memberOutMnpDto = mnpService.validateNewMsisdn(inMnpDto);
							if ("MUPS01".equals(msim.getMemberOrderType()) || "MUPS02".equals(msim.getMemberOrderType())) {
								if (memberOutMnpDto == null || ( memberOutMnpDto !=null && BomWebPortalConstant.CNM_STATUS_NORMAL != memberOutMnpDto.getCnmStatus()) ){
									errors.rejectValue("summaryError", "dummy",	"Multi-SIM MRT Selection Error: " + inMnpDto.getNewMsisdn() + " is not free in CNM inventory.");
								}
							}
						}
					}
				}
			}
		}
		
		
		try {
			String orderStatus = orderDAO.getOrderStatus(summaryDTO.getOrderId());
			logger.debug(summaryDTO.getOrderId() +" orderStatus:"+orderStatus);
			if (orderStatus != null 
					&& !BomWebPortalConstant.BWP_ORDER_INITIAL.equals(orderStatus)
					&& !BomWebPortalConstant.BWP_ORDER_REJECTED.equals(orderStatus)) {
				errors.rejectValue("summaryError", "invalid.orderStatusToSignOff");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//add by herbert 20111109
		logger.info("MNP Summary Checking:"+summaryDTO.getMnpDTO().getMnp());
		if ("Y".equals(summaryDTO.getMnpDTO().getMnp())) {
			logger.info("CutoverDate():"+summaryDTO.getMnpDTO().getCutoverDate());
			if (summaryDTO.getMnpDTO().getCutoverDate()==null){
				errors.rejectValue("summaryError", "serviceReqDate.required");
			}
			
		} else {
			logger.info("CutoverDate():"+summaryDTO.getMnpDTO().getServiceReqDate());
			if (summaryDTO.getMnpDTO().getServiceReqDate()==null){
				errors.rejectValue("summaryError", "serviceReqDate.required");
			}
		}
		
		
		if ("Y".equals(summaryDTO.getMnpDTO().getMnp())) {

			if ("NONE".equals(summaryDTO.getMnpDTO().getCutoverTime())) {
				errors.rejectValue("summaryError", "cutoverTime.required"); //summaryError==>mnpDTO.cutoverTime
			} else {
				if (!"".equals(summaryDTO.getMnpDTO().getMnpMsisdn().trim())) {
					//DENNIS MIP3
					MnpDTO result = new MnpDTO();
					result.setMnpMsisdn(summaryDTO.getMnpDTO().getMnpMsisdn());
					
					if ("Y".equals(summaryDTO.getMnpDTO().getOpssInd())){
						result.setPrepaidSimInd(true);
						result.setCustIdDocNum(summaryDTO.getMnpDTO().getCustIdDocNum());
					}
					com.bomwebportal.mob.validate.dto.ResultDTO validateResult = validateService.validateMNP(result, "summaryError", true, "ignoreFutherPostpaidcheckbox");
				    if (validateResult.hasError()){
				    	validateService.bindErrors(validateResult, errors);
				    }/*else{
				    	((MnpDTO) obj).setDno(result.getDno());
				    	((MnpDTO) obj).setActualDno(result.getActualDno());
				    	((MnpDTO) obj).setIgnoreFutherPostpaidcheckbox(result.isIgnorePostpaidcheckbox());
				    }*/
				    
					/*MnpDTO result = mnpService.validateMnpMsisdn(summaryDTO.getMnpDTO());
					if (result != null && !"".equals(result.getDno().trim())) {
						if (BomWebPortalConstant.DN_STR_PCCW3G.equals(result
								.getDno())
								|| BomWebPortalConstant.DN_STR_ERR
										.equals(result.getDno())) {
							errors.rejectValue("summaryError", "invalid.msisdn");
						} else {
							if ("Y".equals(mobile2GRetiredInd)
									&& BomWebPortalConstant.DN_STR_PCCW2G
											.equals(result.getDno())) {
								errors.rejectValue("summaryError",	"invalid.msisdn");
							} else {
								((SummaryDTO) obj).getMnpDTO().setDno(result.getDno());
							}
						}
					} else {
						errors.rejectValue("summaryError", "invalid.msisdn");
					}*/
				}
			}

			if (summaryDTO.getMnpDTO().getCutoverDateStr() !=null && summaryDTO.getMnpDTO().getCutoverDateStr().length()==0){
				logger.info("getCutoverDateStr().length()==0 ");
				return;
			}
			if (!Utility.isValidDate(summaryDTO.getMnpDTO().getCutoverDateStr())) {
				errors.rejectValue("summaryError", "invalid.Date");
			} else {
				try {
					List<String> results = orderDAO.getFrozenWindow(summaryDTO.getMnpDTO().getCutoverDateStr());
					if (results != null) {
						for (String s : results) {
							if (s.equals(summaryDTO.getMnpDTO().getCutoverTime())) {
								errors.rejectValue("summaryError", "invalid.cutOverTime", "This Cut Over Time is frozen");
							}
						}
					}
				} catch (DAOException e) {
					logger.error("getFrozenWindow:", e);
				}
				
				Calendar currentDate = Calendar.getInstance();
				int currentTime = currentDate.get(Calendar.HOUR) * 100
						+ currentDate.get(Calendar.MINUTE);
			
				Calendar cutoverDate = Calendar.getInstance();
				cutoverDate.setTime(Utility.string2Date(summaryDTO.getMnpDTO().getCutoverDateStr()));
				Calendar tPlus1Date = Calendar.getInstance();
				tPlus1Date.set(currentDate.get(Calendar.YEAR),
						currentDate.get(Calendar.MONTH),
						currentDate.get(Calendar.DATE) + 1);

				Calendar tPlus2Date = Calendar.getInstance();
				tPlus2Date.set(currentDate.get(Calendar.YEAR),
						currentDate.get(Calendar.MONTH),
						currentDate.get(Calendar.DATE) + 2);

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date cutover = Date.valueOf(df.format(cutoverDate.getTime()));
				Date tPlus1 = Date.valueOf(df.format(tPlus1Date.getTime()));
				Date tPlus2 = Date.valueOf(df.format(tPlus2Date.getTime()));
				String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
				if ("Y".equals(summaryDTO.getMnpDTO().getOpssInd())&& ArrayUtils.contains(opssCases, summaryDTO.getMnpDTO().getDno() + summaryDTO.getMnpDTO().getRno())){
					Calendar today = Calendar.getInstance();
					today.set(Calendar.HOUR_OF_DAY, 0);
					today.set(Calendar.MINUTE, 0);
					today.set(Calendar.SECOND, 0);
					today.set(Calendar.MILLISECOND,0);
						if (cutoverDate.before(today)){
							errors.rejectValue(
									"cutoverDateStr", "dummy", 
									"Cutover date should on or after "
											+ Utility.date2String(cutoverDate.getTime(), BomWebPortalConstant.DATE_FORMAT));
						}
				}else{
					if (cutover.before(tPlus1)) {
						logger.info("cutover date before T + 1===A");
						errors.rejectValue("summaryError", "cutoverDate.invalid");
					} else if (cutover.equals(tPlus1)) {
						logger.info("cutover date equals T + 1");
	
						if ((Calendar.AM == currentDate.get(Calendar.AM_PM) && currentTime < 1145)
								&& "0".equals(summaryDTO.getMnpDTO().getCutoverTime())) {
							errors.rejectValue("summaryError",
									"cutoverDate.invalid");
						}
						if ((Calendar.AM == currentDate.get(Calendar.AM_PM) && currentTime >= 1145)
								|| Calendar.PM == currentDate.get(Calendar.AM_PM)) {
							errors.rejectValue("summaryError",	"cutoverDate.invalid");
						}
					} else if (cutover.equals(tPlus2)) {
						logger.info("cutover date equals T + 2");
	
						if ((Calendar.PM == currentDate.get(Calendar.AM_PM) && currentTime >= 545)
								&& "0".equals(summaryDTO.getMnpDTO().getCutoverTime())) {
							errors.rejectValue("summaryError",
									"cutoverDate.invalid");
						}
					}
				}
				
			}

		} else if ("N".equals(summaryDTO.getMnpDTO().getMnp())) {
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "summaryError","msisdn.required");
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors,"serviceReqDateStr", "serviceReqDate.required");//mark 2011

			if (!"".equals(summaryDTO.getMnpDTO().getNewMsisdn().trim())) {

				MnpDTO outMnpDto = null;
				if ("1".equals(summaryDTO.getMnpDTO().getChannelId())) {
					outMnpDto = mnpService.validateNewMsisdn(summaryDTO.getMnpDTO());
				} else {
					outMnpDto = mnpService.validateNewMsisdn(summaryDTO.getMnpDTO()); //DENNIS MIP3
					//outMnpDto = mnpService.validateCnmMsindn(summaryDTO.getMnpDTO().getNewMsisdn(), summaryDTO.getMnpDTO().getShopCd());
				}

				
				if (outMnpDto != null
						&& BomWebPortalConstant.CNM_STATUS_NORMAL == outMnpDto.getCnmStatus()) {
					summaryDTO.getMnpDTO().setMsisdnLvl(outMnpDto.getMsisdnLvl());
				}  else if (("10".equals(summaryDTO.getMnpDTO().getChannelId()) || "11".equals(summaryDTO.getMnpDTO().getChannelId())) //ds only
						 && (outMnpDto != null && outMnpDto.getCnmStatus() == 18)){
					boolean isAssgn = false;
					String cnmReserveId = outMnpDto.getReserveId();
					if (StringUtils.isNotBlank(cnmReserveId)) {
						isAssgn = cnmReserveId.equals(summaryDTO.getMnpDTO().getReserveId()) ? true : false;
					}
					if(isAssgn) {
						summaryDTO.getMnpDTO().setCnmStatus(outMnpDto.getCnmStatus());
						summaryDTO.getMnpDTO().setMsisdnLvl(outMnpDto.getMsisdnLvl());
					} else{
						summaryDTO.getMnpDTO().setCnmStatus(0);//clear data
						summaryDTO.getMnpDTO().setMsisdnLvl("");//clear data
						errors.rejectValue("summaryError", "invalid.msisdn");
					}
				} else {
					errors.rejectValue("summaryError", "invalid.msisdn");
				}
			}
		
			if (summaryDTO.getMnpDTO().getServiceReqDateStr() != null && summaryDTO.getMnpDTO().getServiceReqDateStr().length()==0){
				logger.info("mnpDTO.getServiceReqDateStr().length()==0 ");
				return;
			}
			if (!Utility.isValidDate(summaryDTO.getMnpDTO().getServiceReqDateStr())) {
				errors.rejectValue("summaryError", "invalid.Date");
			} else {
				Calendar currentDate = Calendar.getInstance();
				Calendar compareDate = Calendar.getInstance();
				Calendar serviceReqDate = Calendar.getInstance();
				serviceReqDate.setTime(Utility.string2Date(summaryDTO.getMnpDTO().getServiceReqDateStr()));
				compareDate.set(serviceReqDate.get(Calendar.YEAR),
						serviceReqDate.get(Calendar.MONTH),
						serviceReqDate.get(Calendar.DATE));
				logger.info("currentDate: " + currentDate.getTime());
				logger.info("compareDate: " + compareDate.getTime());
				if (compareDate.before(currentDate)) {
					errors.rejectValue("summaryError", "invalid.BackDate");
				}
			}
			
		}
		logger.info("ICCID in SummaryValidator1: " + summaryDTO.getMobileSimInfoDTO()	.getIccid());
		if (!"UATSIMSIGNOFF".equalsIgnoreCase(summaryDTO.getMobileSimInfoDTO()			
				.getIccid())) {// this if for test gen pdf and send report only
			logger.info("ICCID in SummaryValidator2: " + summaryDTO.getMobileSimInfoDTO().getIccid());
			if ("UATSIM".equals(summaryDTO.getMobileSimInfoDTO().getIccid())) {
				
				logger.info("ICCID in SummaryValidator3: " + summaryDTO.getMobileSimInfoDTO().getIccid());
				errors.rejectValue("summaryError", "cutoverDate.invalid111",
						"[UATSIM] can't not signoff");

			}
			logger.info("ICCID in SummaryValidator4: " + summaryDTO.getMobileSimInfoDTO().getIccid());

			if (summaryDTO.getMobileSimInfoDTO().getIccid() != null
					&& !"".equals(summaryDTO.getMobileSimInfoDTO().getIccid()
							.trim()) && summaryDTO.getMobileSimInfoDTO().getChannelId() != 10 && summaryDTO.getMobileSimInfoDTO().getChannelId() != 11) {
				MobileSimInfoDTO resultMobileSimInfoDto = mobileSimInfoService
						.validateSim(summaryDTO.getMobileSimInfoDTO());
				if (resultMobileSimInfoDto != null
						&& summaryDTO.getMobileSimInfoDTO().getIccid()
								.equals(resultMobileSimInfoDto.getIccid())) {
					summaryDTO.getMobileSimInfoDTO().setImsi(
							resultMobileSimInfoDto.getImsi());
					summaryDTO.getMobileSimInfoDTO().setItemCd(
							resultMobileSimInfoDto.getItemCd());
					summaryDTO.getMobileSimInfoDTO().setPuk1(
							resultMobileSimInfoDto.getPuk1());
					summaryDTO.getMobileSimInfoDTO().setPuk2(
							resultMobileSimInfoDto.getPuk2());
					summaryDTO.getMobileSimInfoDTO().setHwInvStatus(
							resultMobileSimInfoDto.getHwInvStatus());

					if (resultMobileSimInfoDto.getHwInvStatus() != BomWebPortalConstant.HWINV_VALID_STATUS) {
						errors.rejectValue("summaryError", "sim.invalid");
					}

				} else {
					errors.rejectValue("summaryError", "invalid.iccid");
				}
			}

			try {

				int result = -1;

				List<String[]> usingSim = orderDAO.getUsingSim(summaryDTO
						.getOrderId(), summaryDTO.getMobileSimInfoDTO()
						.getIccid());

				if (usingSim == null || usingSim.size() == 0) {
					result = 0; // 0 - nothing is extracted
				} else {
					// String[] s = simItemList.get(0); // first set of data
					if (usingSim.get(0) == null) {
						result = 0;
					} else {
						result = 1; // 1 - something is extracted
					}
				}

				if (result == 1) { // something is extracted, i.e sim is using
					errors.rejectValue("summaryError", "sim.invalid11",
							"The status of SIM number is invalid, used by another Order");
				}
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
