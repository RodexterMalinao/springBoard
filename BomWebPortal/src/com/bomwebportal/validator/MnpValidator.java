package com.bomwebportal.validator;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MnpValidator implements Validator {

	protected final Log logger = LogFactory.getLog(getClass());

	private MnpService service;
	private String mobile2GRetiredInd;
	private OrderService orderService;
	private ValidateService validateService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MnpService getService() {
		return service;
	}

	public void setService(MnpService service) {
		this.service = service;
	}

	public String getMobile2GRetiredInd() {
		return mobile2GRetiredInd;
	}

	public void setMobile2GRetiredInd(String mobile2gRetiredInd) {
		mobile2GRetiredInd = mobile2gRetiredInd;
	}
	
	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(MnpDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		MnpDTO mnpDTO = (MnpDTO) obj;
		MnpDTO oldMnpDTO = orderService.getMnp(mnpDTO.getOrderId());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mnp", "mnp.required");
		
		String appDate = (String)mnpDTO.getValue("appDate");
		if (appDate==null){
			System.out.println("appdate:" +appDate);
			appDate = Utility.date2String(Calendar.getInstance().getTime(), BomWebPortalConstant.DATE_FORMAT);
		}
		
		//dennis enable mnp
		boolean enableMNP = true;
		List<CodeLkupDTO> enableMNPList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_SHOW_MNP_TICKET_BTN");
		if (CollectionUtils.isNotEmpty(enableMNPList)){
			for (CodeLkupDTO dto: enableMNPList){
				if ("N".equalsIgnoreCase(dto.getCodeId())){
					enableMNP = false;
					break;
				}
			}
		}
		
		String msisdn = mnpDTO.getMnpMsisdn();
		java.util.Date cutoverDate1 = Utility.string2Date(mnpDTO.getCutoverDateStr());
		
		if (!StringUtils.isEmpty(msisdn) && cutoverDate1 != null){
			SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

			Calendar cal = Calendar.getInstance();
			cal.setTime(cutoverDate1);

			Integer day = Integer.parseInt(dayFormat.format(cutoverDate1));
			Integer month = cal.get(Calendar.MONTH) + 1;
			Integer checkIsWhiteList = service.checkIsWhiteList(msisdn);
			if (checkIsWhiteList > 0 ){
				Integer billPeriod = service.getCSLBillPeriod(msisdn);
				if (billPeriod != null){
//					System.out.println("day: " + day);
//					System.out.println("month: " + month);
//					System.out.println("billPeriod: " + billPeriod);
					if (month == 3 && (day == 1 || day == 2) && billPeriod == 28){
						day = day + billPeriod;
					}
					if (month == 3 && day == 1 && billPeriod == 27){
						day = day + billPeriod;
					}
					if ((day >= billPeriod) &&  (day <= (billPeriod + 2))){
						errors.rejectValue("cutoverDateStr","outOfBillPeriodDate");
						return;
					}
				}
			}
		}
		
		if ("Y".equals(mnpDTO.getMnp())) {
			mnpDTO.setCnmStatus(0);//clear data
			mnpDTO.setMsisdnLvl("");//clear data
			CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)mnpDTO.getValue("customer");	
			mnpDTO.setNumType(sessionCustomer.getNumType()); //clear data
			mnpDTO.setRno(Utility.getRno(sessionCustomer.getSimType()));
			//mnpDTO.setNumType(null);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mnpMsisdn",
					"msisdn.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custName",
					"custName.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custIdDocNum",
					"custIdDocNum.required");
			
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cutoverDateStr",	"cutoverDate.required"); //mark 20110609 for concierge
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mnpTicketNum",	"mnpTicketNum.required");
			
			
			MnpDTO checkCcsMnpDTO = new MnpDTO();
			checkCcsMnpDTO = service.checkCCSPendingOrder(mnpDTO.getOrderId(), mnpDTO.getMnpMsisdn());
			if (checkCcsMnpDTO!=null){
				errors.rejectValue("mnpMsisdn", "dummy", 
						"Other channel issued pending order on this MRT, order id = "+checkCcsMnpDTO.getOrderId()+". Please contact relevant channel for proper handling.");
				return;
			}
			
			
			if (!mnpDTO.isIgnoreSameMRTinSBcheckbox()){
				//check same mrt in sb db.
				//not empty and not ignore
				try {
					String orderId=mnpDTO.getOrderId();
					if (StringUtils.isEmpty(orderId)){
						orderId="New Order";
					}
					String orderIdUsingSameMRT = null;
					if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
						orderIdUsingSameMRT = orderService.getOrderIdUsingSameMRTShop(mnpDTO.getMnpMsisdn(),orderId,mnpDTO.getShopCd());
					} else {
						orderIdUsingSameMRT = orderService.getOrderIdUsingSameMRT(mnpDTO.getMnpMsisdn(),orderId);
					}
					
					if (StringUtils.isNotEmpty(orderIdUsingSameMRT) && !mnpDTO.isIgnoreSameMRTinSBcheckbox()) {
						errors.rejectValue("ignoreSameMRTinSBcheckbox", "dummy", 
								"[MNP]Same MRT \"" + mnpDTO.getMnpMsisdn() + "\" is already used in existing order(s) \"" 
								+ orderIdUsingSameMRT + "\"");
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			//add by wilson 
			if ("Prepaid Sim".equalsIgnoreCase(mnpDTO.getCustName())){
				if(StringUtils.isEmpty(mnpDTO.getPrePaidSimDocInd())){
					errors.rejectValue("prePaidSimDocWithCert", "prePaidSimDocInd.required", "Please choose the Declaration");
				}
			}
			//add by herbert 20111124
			if (mnpDTO.getCustName().indexOf(',') != -1) {
				errors.rejectValue("custName", "custName.noComma");
			}
			//mnpDTO.setCustName(mnpDTO.getCustName().replace(',', ' ')); 
			
			if ("NONE".equals(mnpDTO.getCutoverTime())) {
				errors.rejectValue("cutoverTime", "cutoverTime.required");
			} else {
				
				if (!"".equals(mnpDTO.getMnpMsisdn().trim())) {
					
					if (oldMnpDTO != null && mnpDTO.getMnpMsisdn().trim().equals(oldMnpDTO.getMnpMsisdn()) && !mnpDTO.isPrepaidSimInd()) {
						
						if (!Utility.validatePhoneNum(mnpDTO.getMnpMsisdn())){
							errors.rejectValue("mnpMsisdn", "invalid.contactPhone");	
						}
						
					/*if (service.isPccw3gPrepaidNumber(mnpDTO.getMnpMsisdn().trim())){// add 3GPP number check, 20130118
						errors.rejectValue("mnpMsisdn", "dummy", "This is a PCCW 3G prepaid number, please use POSBOM to issue order.");*/
					}else{
						
						
						
						if (!Utility.validatePhoneNum(mnpDTO.getMnpMsisdn())){
							errors.rejectValue("mnpMsisdn", "invalid.contactPhone");	
						}
							
						String bomPendingOrderMessage =service.checkBomPendingOrder(mnpDTO.getMnpMsisdn());
						if(StringUtils.isNotEmpty(bomPendingOrderMessage)){
							errors.rejectValue("mnpMsisdn", "dummy", bomPendingOrderMessage);
						}
						
						//DENNIS MIP3
						MnpDTO result = new MnpDTO();
						result.setMnpMsisdn(mnpDTO.getMnpMsisdn());
						result.setPrepaidSimInd(mnpDTO.isPrepaidSimInd());
						if (mnpDTO.isPrepaidSimInd()){
							result.setCustIdDocNum(mnpDTO.getCustIdDocNum());
						}
						com.bomwebportal.mob.validate.dto.ResultDTO validateResult = validateService.validateMNP(result, "mnpMsisdn", mnpDTO.isIgnorePostpaidcheckbox(), "ignorePostpaidcheckbox");
					    if (validateResult.hasError()){
					    	validateService.bindErrors(validateResult, errors);
					    }else{
					    	((MnpDTO) obj).setDno(result.getDno());
					    	((MnpDTO) obj).setActualDno(result.getActualDno());
					    	((MnpDTO) obj).setIgnorePostpaidcheckbox(result.isIgnorePostpaidcheckbox());
					    	((MnpDTO) obj).setOpssInd(validateResult.getOnePssInd());
					    	((MnpDTO) obj).setStarterPack(validateResult.getStartPackNumber());
					    }
						
						
						/*MnpDTO result = service.validateMnpMsisdn(mnpDTO);
						if (result != null && !"".equals(result.getDno().trim())) {
							if (BomWebPortalConstant.DN_STR_PCCW3G.equals(result
									.getDno())
									|| BomWebPortalConstant.DN_STR_ERR
											.equals(result.getDno())) {
								errors.rejectValue("mnpMsisdn", "invalid.msisdn");
							} else {
								if ("Y".equals(mobile2GRetiredInd)
										&& BomWebPortalConstant.DN_STR_PCCW2G
												.equals(result.getDno())) {
									errors.rejectValue("mnpMsisdn",
											"invalid.msisdn");
								} else {
									((MnpDTO) obj).setDno(result.getDno());
								}
							}
						} else {
							errors.rejectValue("mnpMsisdn", "invalid.msisdn");
						}
						
						
						logger.info("mnpDTO.getMnpMsisdn().trim()"+mnpDTO.getMnpMsisdn().trim());
						logger.info("result.getDno()"+result.getDno());
						logger.info("service.isPccw3gPrepaidNumber(mnpDTO.getMnpMsisdn().trim())"+service.isPccw3gPrepaidNumber(mnpDTO.getMnpMsisdn().trim()));
						//  3GPP number check, 20130319
						if (BomWebPortalConstant.DN_STR_PCCW2G.equals(result.getDno()) && service.isPccw3gPrepaidNumber(mnpDTO.getMnpMsisdn().trim())){
							errors.rejectValue("mnpMsisdn", "dummy", "This is a PCCW 3G prepaid number, please use POSBOM to issue order.");
						}else if(BomWebPortalConstant.DN_STR_PCCW2G.equals(result.getDno()) ){
							errors.rejectValue("mnpMsisdn", "dummy", "DNO = MP(HKT 2G number), Should be handle in New Mobile Number instead of MNP.");
						}
						// 20130506, IM or EL DNO not allow to create order in SB
						if ("IM".equals(result.getDno()) ){
							errors.rejectValue("mnpMsisdn", "dummy", "DNO = IM. MVNO porting found and need special arrangement in porting. Please contact BOM Mobile support and helpdesk for arrangement.");
						}
						if ("EL".equals(result.getDno()) ){
							errors.rejectValue("mnpMsisdn", "dummy", "DNO = EL. MVNO porting found and need special arrangement in porting. Please contact BOM Mobile support and helpdesk for arrangement.");
						}*/
						
					}
					
				}
			}

			if (mnpDTO.getCutoverDateStr() !=null && mnpDTO.getCutoverDateStr().length()==0){
				//System.out.println("mnpDTO.getCutoverDateStr().length():"+mnpDTO.getCutoverDateStr().length());
				//System.out.println("mnpDTO.getCutoverDateStr():"+mnpDTO.getCutoverDateStr());
				//errors.rejectValue("cutoverDate", "invalid.msisdn");
				logger.info("getCutoverDateStr().length()==0 ");
				return;
			}else if (!Utility.isValidDate(mnpDTO.getCutoverDateStr())) {
				errors.rejectValue("cutoverDateStr", "invalid.Date");
				return;
				
			} else {
				
				Calendar cutoverDate = Calendar.getInstance();
				cutoverDate.setTime(Utility.string2Date(mnpDTO.getCutoverDateStr()));
				Calendar currentDate = Calendar.getInstance();
				String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
				if (StringUtils.isNotBlank(mnpDTO.getOpssInd()) && "Y".equals(mnpDTO.getOpssInd()) && ArrayUtils.contains(opssCases, mnpDTO.getDno() + mnpDTO.getRno())){
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
				}
				else{
					//Check the year range
				
					Calendar appDatePlus = Calendar.getInstance();
					Calendar compareDate = Calendar.getInstance();
	
					appDatePlus.setTime(Utility.string2Date(appDate));
					compareDate.setTime(Utility.string2Date(mnpDTO.getCutoverDateStr()));
					
					//DENNIS201606
					//compareDate.add(Calendar.YEAR, -100);
					if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
						appDatePlus.add(Calendar.HOUR,  BomWebPortalConstant.DS_MNP_APP_EXTEND_DAYS*24);
					}else{
						appDatePlus.add(Calendar.HOUR,  BomWebPortalConstant.RS_MNP_APP_EXTEND_DAYS*24);
					}
					
					if ("Y".equalsIgnoreCase(mnpDTO.getMnpExtendAuthInd())){
						appDatePlus.add(Calendar.HOUR,  BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS*24);
					}
					
					
					logger.info("appDatePlus: " + appDatePlus.getTime());
					logger.info("compareDate: " + compareDate.getTime());
					/*if(compareDate.after(currentDate)){
						errors.rejectValue("cutoverDateStr", "invalid.Date");
						return;
					}*/
					if (compareDate.compareTo(appDatePlus) > 0) {
						//DENNIS201606
						errors.rejectValue(
								"cutoverDateStr", "dummy", 
								"Cutover date should on or before "
										+ Utility.date2String(appDatePlus.getTime(), BomWebPortalConstant.DATE_FORMAT));
						return;
					}
					
					
					
					List<String> results = new ArrayList<String>();
					try {
						 results = orderService.getFrozenWindow(mnpDTO.getCutoverDateStr());
						
					} catch (Exception e) {
						logger.error("getFrozenWindow:", e);
					}
					
					if (results != null) {
						for (String s : results) {
							if (s.equals(mnpDTO.getCutoverTime())) {
								errors.rejectValue("cutOeverTimeError", "invalid.cutOverTime", "This Cut Over Time is frozen");
							}
						}
					}
					
					int currentTime = currentDate.get(Calendar.HOUR) * 100
							+ currentDate.get(Calendar.MINUTE);
				
	
					Calendar tPlus1Date = Calendar.getInstance();
					tPlus1Date.set(currentDate.get(Calendar.YEAR),
							currentDate.get(Calendar.MONTH),
							currentDate.get(Calendar.DATE) + 1);
	
					Calendar tPlus2Date = Calendar.getInstance();
					tPlus2Date.set(currentDate.get(Calendar.YEAR),
							currentDate.get(Calendar.MONTH),
							currentDate.get(Calendar.DATE) + 2);
					
					//add by herbert 20110920 -  Cutover Date > Sysdate + 30 && MNP no == null 
					Calendar tPlus30Date = Calendar.getInstance();
					tPlus30Date.set(currentDate.get(Calendar.YEAR),
							currentDate.get(Calendar.MONTH),
							currentDate.get(Calendar.DATE));
					tPlus30Date.add(Calendar.DATE, 30);
	
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date cutover = Date.valueOf(df.format(cutoverDate.getTime()));
					Date tPlus1 = Date.valueOf(df.format(tPlus1Date.getTime()));
					Date tPlus2 = Date.valueOf(df.format(tPlus2Date.getTime()));
					//System.out.println("currentDate: " + currentDate.getTime());
					//System.out.println("cutoverDate: " + cutoverDate.getTime());
					//System.out.println("tPlus1Date: " + tPlus1Date.getTime());
					//System.out.println("tPlus2Date: " + tPlus2Date.getTime());
					//System.out.println("tPlus30Date: " + tPlus30Date.getTime());
					//System.out.println("cutoverDate.before(tPlus1Date): "+ cutoverDate.before(tPlus1Date));
					//System.out.println("cutoverDate.equals(tPlus1Date): "+ cutoverDate.equals(tPlus1Date));
					//System.out.println("cutoverDate.equals(tPlus30Date): "+ cutoverDate.after(tPlus30Date));
					
					if (cutover.before(tPlus1)) {
						logger.info("cutover date before T + 1===A");
						//System.out.println("cutover date before T + 1==>A");
						errors.rejectValue("cutoverDateStr", "cutoverDate.invalid");
					} else if (cutover.equals(tPlus1)) {
						logger.info("cutover date equals T + 1");
						//System.out.println("cutover date equals T + 1");
	
						if ((Calendar.AM == currentDate.get(Calendar.AM_PM) && currentTime < 1145)
								&& "0".equals(mnpDTO.getCutoverTime())) {
							//System.out.println("Calendar.AM==currentDate.get(Calendar.AM_PM)&&currentTime<1145");
							errors.rejectValue("cutoverDateStr",
									"cutoverDate.invalid");
						}
						if ((Calendar.AM == currentDate.get(Calendar.AM_PM) && currentTime >= 1145)
								|| Calendar.PM == currentDate.get(Calendar.AM_PM)) {
							//System.out.println("Calendar.AM==currentDate.get(Calendar.AM_PM)&&currentTime>=1145");
							errors.rejectValue("cutoverDateStr",
									"cutoverDate.invalid");
						}
					} else if (cutover.equals(tPlus2)) {
						logger.info("cutover date equals T + 2");
						//System.out.println("cutover date equals T + 2");
	
						if ((Calendar.PM == currentDate.get(Calendar.AM_PM) && currentTime >= 545)
								&& "0".equals(mnpDTO.getCutoverTime())) {//edit by wilson 20110713
							//System.out.println("Calendar.PM==currentDate.get(Calendar.AM_PM)&&currentTime>=545");
							errors.rejectValue("cutoverDateStr",
									"cutoverDate.invalid");
						}
					} //add by herbert 20110920 - Cutover Date > Sysdate + 30 && MNP no == null
					else if (enableMNP && cutoverDate.after(tPlus30Date)) {
						logger.info("cutover date after T + 30");
						if (mnpDTO.getMnpTicketNum().length()!=0 && mnpDTO.getMnpTicketNum() != null)
							errors.rejectValue("mnpTicketNum", "mnpTicketNum.isblank");
					}
				}
				//Dennis enable mnp
				if (enableMNP){
				
					if(!"".equals(mnpDTO.getMnpTicketNum())){
						if(mnpDTO.getMnpTicketNum()!=null&&mnpDTO.getMnpTicketNum().length()!=10){
							errors.rejectValue("mnpTicketNum", "mnpTicketNum.invalid");
						}else{
							String mmdd = Utility.date2String(cutoverDate.getTime(), "MMdd");
							if(!mmdd.equals(mnpDTO.getMnpTicketNum().substring(0,4))){
								errors.rejectValue("mnpTicketNum", "mnpTicketNum.invalid");
							}else if(mnpDTO.getMnpTicketNum().charAt(4)!=mnpDTO.getCutoverTime().charAt(0)){
								errors.rejectValue("mnpTicketNum", "mnpTicketNum.invalid");
							}else if(!Utility.isDigit(mnpDTO.getMnpTicketNum().substring(5))){						
								errors.rejectValue("mnpTicketNum", "mnpTicketNum.invalid");
							}
						}
					}
				}
				
			}
			
			
			//validation getOrigActDateStr
			if (mnpDTO.getOrigActDateStr() !=null && mnpDTO.getOrigActDateStr().length()==0){
				
				logger.info("getOrigActDateStr().length()==0 ");
				return;
			}else {
				if (!Utility.isValidDate(mnpDTO.getOrigActDateStr())) {
					errors.rejectValue("origActDateStr", "invalid.Date");
					return;
				}
				//original active date , future date checking
				Calendar yesterday  = Calendar.getInstance();
				Calendar origActDate= Calendar.getInstance();
				origActDate.setTime(Utility.string2Date( mnpDTO.getOrigActDateStr() ) );
				yesterday .add(Calendar.DATE, -1); 
				if (origActDate.after(yesterday) ) {
					errors.rejectValue("origActDateStr", "dummy", "Do not allow today's and future dates!!");
					return;
				}
			}

		} else if ("N".equals(mnpDTO.getMnp())) {
			mnpDTO.setCnmStatus(0);//clear data
			mnpDTO.setMsisdnLvl("");//clear data
			//mnpDTO.setNumType(null);
			if ("NONE".equals(mnpDTO.getNewMsisdn())){
				errors.rejectValue("newMsisdn", "msisdn.required");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newMsisdn", "msisdn.required");
			if(mnpDTO.isFutherMnp()){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "futherCutoverDateStr", "futherCutoverDate.required", "Please input the Futher MNP Cutover Date");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "futherMnpNumber", "futherMnpNumber.required", "Please input the Futher MNP number");

			}
			
			if (!mnpDTO.isIgnoreSameMRTinSBcheckbox()){
				//check same mrt in sb db.
				//not empty and not ignore
				try {
					String orderId=mnpDTO.getOrderId();
					if (StringUtils.isEmpty(orderId)){
						orderId="New Order";
					}
					String orderIdUsingSameMRT = null;
					if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
						orderIdUsingSameMRT = orderService.getOrderIdUsingSameMRTShop(mnpDTO.getNewMsisdn(),orderId,mnpDTO.getShopCd());
					} else {
						orderIdUsingSameMRT = orderService.getOrderIdUsingSameMRT(mnpDTO.getNewMsisdn(),orderId);
					}
					if ( StringUtils.isNotEmpty(orderIdUsingSameMRT) && !mnpDTO.isIgnoreSameMRTinSBcheckbox()) {
						mnpDTO.setCnmStatus(0);//clear data
						mnpDTO.setMsisdnLvl("");//clear data
						//mnpDTO.setNumType(null);
						errors.rejectValue("ignoreSameMRTinSBcheckbox", "dummy", 
								"[New number ]Same MRT \"" + mnpDTO.getNewMsisdn() + "\" is already used in existing order(s) \"" 
								+ orderIdUsingSameMRT + "\"");
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			
			
			
			//further MNP check
			if (mnpDTO.isFutherMnp()){
				
				if (mnpDTO.isChinaMobile()) {
					errors.rejectValue("futherMnpNumber", "furtherChina.invalid", "Not allow to select China Mobile Number for Further MNP");
				}
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "futhercustName",
						"custName.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "futhercustIdDocNum",
						"custIdDocNum.required");


				if ("Prepaid Sim".equalsIgnoreCase(mnpDTO.getFuthercustName())){
					if(StringUtils.isEmpty(mnpDTO.getPrePaidSimDocInd())){
						errors.rejectValue("futherprePaidSimDocWithCert", "prePaidSimDocInd.required", "Please choose the Declaration");
					}
				}
				if (mnpDTO.getFuthercustName().indexOf(',') != -1) {
					errors.rejectValue("futhercustName", "custName.noComma");
				}
				
				if (!"".equals(mnpDTO.getFutherMnpNumber().trim())) {
					if (!Utility.validateHKMobilePrefix(mnpDTO.getFutherMnpNumber())){
						errors.rejectValue("futherMnpNumber", "custInfoMRTPrefix.invalid", "custInfoMRTPrefix.invalid");
					}
					//DENNIS MIP3
					MnpDTO result = new MnpDTO();
					result.setMnpMsisdn(mnpDTO.getFutherMnpNumber());
					result.setPrepaidSimInd(mnpDTO.isFutherPrepaidSimInd());
					if (result.isPrepaidSimInd()){
						result.setCustIdDocNum(mnpDTO.getFuthercustIdDocNum());
					}
					
					com.bomwebportal.mob.validate.dto.ResultDTO validateResult = validateService.validateMNP(result, "futherMnpNumber", mnpDTO.isIgnoreFutherPostpaidcheckbox(), "ignoreFutherPostpaidcheckbox");
				    if (validateResult.hasError()){
				    	validateService.bindErrors(validateResult, errors);
				    }else{
				    	((MnpDTO) obj).setDno(result.getDno());
				    	((MnpDTO) obj).setActualDno(result.getActualDno());
				    	((MnpDTO) obj).setIgnoreFutherPostpaidcheckbox(result.isIgnorePostpaidcheckbox());
				    	((MnpDTO) obj).setOpssInd(validateResult.getOnePssInd());
				    	((MnpDTO) obj).setStarterPack(validateResult.getStartPackNumber());
				    	
				    }
					
					//MnpDTO result = service.validateMnpMsisdn(mnpDTO.getFutherMnpNumber().trim());
				    
				    
					/*if (service.isPccw3gPrepaidNumber(mnpDTO.getFutherMnpNumber().trim())){// add 3GPP number check, 20130118
						errors.rejectValue("futherMnpNumber", "dummy", "This is a PCCW3G Prepaid Number.");
					}else{*/
				    
				    
						/*if (result != null && !"".equals(result.getDno().trim())) {
							if (BomWebPortalConstant.DN_STR_PCCW3G.equals(result
									.getDno())
									|| BomWebPortalConstant.DN_STR_ERR
											.equals(result.getDno())) {
								errors.rejectValue("futherMnpNumber", "invalid.msisdn", "Futher Mnp invalid.msisdn1");
							} else {
								if ("Y".equals(mobile2GRetiredInd)
										&& BomWebPortalConstant.DN_STR_PCCW2G
												.equals(result.getDno())) {
									errors.rejectValue("futherMnpNumber","invalid.msisdn", "Futher Mnp invalid.msisdn2");
								} else {
									((MnpDTO) obj).setDno(result.getDno());
								}
							}
						} else {
							errors.rejectValue("futherMnpNumber", "invalid.msisdn", "Futher Mnp invalid.msisdn3" );
						}
						
						//  3GPP number check, 20130319
						if (BomWebPortalConstant.DN_STR_PCCW2G.equals(result.getDno()) && service.isPccw3gPrepaidNumber(mnpDTO.getFutherMnpNumber().trim())){
							errors.rejectValue("futherMnpNumber", "dummy", "This is a PCCW 3G prepaid number, please use POSBOM to issue order.");
						}else if(BomWebPortalConstant.DN_STR_PCCW2G.equals(result.getDno()) ){
							errors.rejectValue("futherMnpNumber", "dummy", "DNO = MP(HKT 2G number), Should be handle in New Mobile Number instead of MNP.");
						}
						// 20130506, IM or EL DNO not allow to create order in SB
						if ("IM".equals(result.getDno()) ){
							errors.rejectValue("futherMnpNumber", "dummy", "DNO = IM. MVNO porting found and need special arrangement in porting. Please contact BOM Mobile support and helpdesk for arrangement.");
						}
						if ("EL".equals(result.getDno()) ){
							errors.rejectValue("futherMnpNumber", "dummy", "DNO = EL. MVNO porting found and need special arrangement in porting. Please contact BOM Mobile support and helpdesk for arrangement.");
						}*/
						
						
					//}
				}
				
				if(StringUtils.isNotEmpty(mnpDTO.getServiceReqDateStr()) && StringUtils.isNotEmpty(mnpDTO.getFutherCutoverDateStr())){
					if (!Utility.isValidDate(mnpDTO.getFutherCutoverDateStr())) {
						errors.rejectValue("futherCutoverDateStr", "invalid.Date", "Please input a valid date.");
						return;
					}
					// add by nancy
					if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
						List<String> results = new ArrayList<String>();
						try {
							 results = orderService.getFrozenWindow(mnpDTO.getFutherCutoverDateStr());
						} catch (Exception e) {
							logger.error("getFrozenWindow:", e);
						}
						
						if (results != null) {
							for (String s : results) {
								if (s.equals(mnpDTO.getFutherCutoverTime())) {
									errors.rejectValue("futherCutOeverTimeError", "invalid.cutOverTime", "This Cut Over Time is frozen");
								}
							}
						}
					}
					
					try {
						Calendar serviceReqDate= Calendar.getInstance();
						serviceReqDate.setTime(Utility.string2Date( mnpDTO.getServiceReqDateStr() ) );
						//.mnpDTO.getServiceReqDate();
						Calendar futherMnpDate= Calendar.getInstance();
						futherMnpDate.setTime(Utility.string2Date( mnpDTO.getFutherCutoverDateStr()));
												
						Calendar serviceReqDatePlusFurther= Calendar.getInstance();
						serviceReqDatePlusFurther.setTime(Utility.string2Date( mnpDTO.getServiceReqDateStr() ) );
						
						//DENNIS201606
						if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
							serviceReqDatePlusFurther.add(Calendar.HOUR, BomWebPortalConstant.DS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS*24);//add further MNP days
						}else{
							serviceReqDatePlusFurther.add(Calendar.HOUR, BomWebPortalConstant.RS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS*24);//add further MNP days
						} 
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						logger.info("serviceReqDate:"+sdf.format(serviceReqDate.getTime()));
						logger.info("futherMnpDate:"+sdf.format(futherMnpDate.getTime()));
						logger.info("serviceReqDatePlus90:"+sdf.format(serviceReqDatePlusFurther.getTime()));
						
						long milliseconds1 = serviceReqDate.getTimeInMillis();
						long milliseconds2 = futherMnpDate.getTimeInMillis();
						long diff = milliseconds2 - milliseconds1;
						long diffHours = diff / (60 * 60 * 1000);
						
						long diff2inHour = (serviceReqDatePlusFurther.getTimeInMillis() - futherMnpDate.getTimeInMillis()) / (60 * 60 * 1000);
						String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
						//long diffHours = diff / (60 * 60 * 1000);
	
						if (StringUtils.isNotBlank(mnpDTO.getOpssInd()) && "Y".equals(mnpDTO.getOpssInd()) && ArrayUtils.contains(opssCases, mnpDTO.getDno() + mnpDTO.getRno())){
								if (diffHours<0){
									errors.rejectValue("futherCutoverDateStr",
											"futherCutoverDate.invalid",
											"Futher Mnp Cutover date should be larger or same as Service request");
								}
							}
							else{
								if (diffHours < 72) {
									errors.rejectValue("futherCutoverDateStr",
											"futherCutoverDate.invalid",
											"Futher Mnp Cutover date should after Service request + 3 days");
								}
							}
						logger.info("diff2inHour:"+diff2inHour);
						if (diff2inHour < 0){
							
							//DENNIS201606
							int errorMsg = BomWebPortalConstant.DS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS;
							if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
								errorMsg = BomWebPortalConstant.RS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS;
					
							}
							
							/*errors.rejectValue("futherCutoverDateStr",
									"futherCutoverDate.invalid2",
									"Futher Mnp Cutover date should in Service request + "+BomWebPortalConstant.RS_FURTHER_MNP_MAX_DATE+" days");*/
							errors.rejectValue("futherCutoverDateStr",
									"dummy",
									"Futher Mnp Cutover date should in Service request + "+errorMsg+" days");
						}
						
						//add by nancy
						if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
							//dennis enable mnp
							if (enableMNP){
								Calendar currentDate = Calendar.getInstance();
								Calendar tPlus30Date = Calendar.getInstance();
								tPlus30Date.set(currentDate.get(Calendar.YEAR),
										currentDate.get(Calendar.MONTH),
										currentDate.get(Calendar.DATE));
								tPlus30Date.add(Calendar.DATE, 30);
								if (futherMnpDate.after(tPlus30Date)) {
									logger.info("futhercutover date after T + 30");
									if (mnpDTO.getFutherMnpTicketNum().length()!=0 && mnpDTO.getFutherMnpTicketNum() != null)
										errors.rejectValue("futherMnpTicketNum", "mnpTicketNum.isblank");
								}
								
								if(!"".equals(mnpDTO.getFutherMnpTicketNum())){
									if(mnpDTO.getFutherMnpTicketNum()!=null&&mnpDTO.getFutherMnpTicketNum().length()!=10){
										errors.rejectValue("futherMnpTicketNum", "mnpTicketNum.invalid");
									}else{
										String mmdd = Utility.date2String(futherMnpDate.getTime(), "MMdd");
										if(!mmdd.equals(mnpDTO.getFutherMnpTicketNum().substring(0,4))){
											errors.rejectValue("futherMnpTicketNum", "mnpTicketNum.invalid");
										}else if(mnpDTO.getFutherMnpTicketNum().charAt(4)!=mnpDTO.getFutherCutoverTime().charAt(0)){
											errors.rejectValue("futherMnpTicketNum", "mnpTicketNum.invalid");
										}else if(!Utility.isDigit(mnpDTO.getFutherMnpTicketNum().substring(5))){						
											errors.rejectValue("futherMnpTicketNum", "mnpTicketNum.invalid");
										}
									}
								}
							}
							
							
						}						
					} catch (Exception e) {
						errors.rejectValue("futherCutoverDateStr",
								"futherCutoverDate.invalid.exception",	"e.getMessage()" + e.getMessage());
						logger.info("Exception: " + e.getMessage());
					}
				}else if (!"10".equals(mnpDTO.getChannelId()) && !"11".equals(mnpDTO.getChannelId())){
					errors.rejectValue("futherCutoverDateStr",
							"futherCutoverDate.invalid.exception",	"futherCutoverDate and serviceReqDate must input");
				}				  
			}
			if (!"".equals(mnpDTO.getNewMsisdn().trim())) {
				if (oldMnpDTO != null && mnpDTO.getNewMsisdn().trim().equals(oldMnpDTO.getNewMsisdn()) &&  !"1".equals(mnpDTO.getChannelId())  ) {
					// Recall
					if (!Utility.validatePhoneNum(mnpDTO.getNewMsisdn())){
						errors.rejectValue("newMsisdn", "invalid.contactPhone");	
					}
					
					if (StringUtils.isBlank(mnpDTO.getShopCd())){
						errors.rejectValue("newMsisdn","", "BOM Shop Code is empty.");	
					}else{
					
						MnpDTO outMnpDto = null;
						if ("1".equals(mnpDTO.getChannelId())) {
							outMnpDto = service.validateNewMsisdn(mnpDTO);
						} else {
							outMnpDto = service.validateNewMsisdn(mnpDTO); //DENNIS MIP3	
							//outMnpDto = service.validateCnmMsindn(mnpDTO.getNewMsisdn(), mnpDTO.getShopCd());
						}
						
						if (outMnpDto != null) {
							mnpDTO.setCnmStatus(outMnpDto.getCnmStatus());
							mnpDTO.setMsisdnLvl(outMnpDto.getMsisdnLvl());
							
							mnpDTO.setNumType(outMnpDto.getNumType()); //Dennis MIP3
						}
						
						//Dennis MIP3
						CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)mnpDTO.getValue("customer");
						String selectedNumType = sessionCustomer.getNumType();
						com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateNumType(mnpDTO.getNumType(), selectedNumType, "numType", false);
						validateService.bindErrors(result, errors);
						
						if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
							if (outMnpDto != null && outMnpDto.getCnmStatus() == 0) {
								if (StringUtils.isNotBlank(mnpDTO.getReserveId())) {
									errors.rejectValue("newMsisdn","","Mobile number reserve status expired.");
								} else {
									errors.rejectValue("newMsisdn","invalid.msisdn");
								}
							}
							if (outMnpDto != null && StringUtils.isNotBlank(outMnpDto.getReserveId())) {
								if (!outMnpDto.getReserveId().equals(mnpDTO.getReserveId())) {
									mnpDTO.setIsReserveMrt("Y");
									errors.rejectValue("reserveId", "invalid.reserveId");
								}
							}
						}
						
					}
				} else {
					String bomPendingOrderMessage =service.checkBomPendingOrder(mnpDTO.getNewMsisdn());
					if(StringUtils.isNotEmpty(bomPendingOrderMessage)){
						errors.rejectValue("newMsisdn", "dummy", bomPendingOrderMessage);
					}
					
					if (!Utility.validatePhoneNum(mnpDTO.getNewMsisdn())){
						errors.rejectValue("newMsisdn", "invalid.contactPhone");	
					}	
					
					if (StringUtils.isBlank(mnpDTO.getShopCd())){
						errors.rejectValue("newMsisdn","", "BOM Shop Code is empty.");	
					}else{
					
					//if (mnpDTO.getActionType().equalsIgnoreCase("SUBMIT")) {
						MnpDTO outMnpDto = null;
						
						if ("1".equals(mnpDTO.getChannelId())) {
							outMnpDto = service.validateNewMsisdn(mnpDTO);
						} else {
							outMnpDto = service.validateNewMsisdn(mnpDTO); //DENNIS MIP3
							//outMnpDto = service.validateCnmMsindn(mnpDTO.getNewMsisdn(), mnpDTO.getShopCd());
						}
						if (outMnpDto != null) {
							mnpDTO.setCnmStatus(outMnpDto.getCnmStatus());
							mnpDTO.setMsisdnLvl(outMnpDto.getMsisdnLvl());
							
							mnpDTO.setNumType(outMnpDto.getNumType()); //Dennis MIP3
							/*if ("1".equals(mnpDTO.getChannelId()) ){								
								mnpDTO.setNumType(outMnpDto.getNumType());
							}*/
						}
						
						//Dennis MIP3
						CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)mnpDTO.getValue("customer");
						String selectedNumType = "";
						if (sessionCustomer!=null){
							selectedNumType  = sessionCustomer.getNumType();
						}
						
						com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateNumType(mnpDTO.getNumType(), selectedNumType, "numType",false);
						validateService.bindErrors(result, errors);
						
						
						if (("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId()))
								 && outMnpDto != null && BomWebPortalConstant.CNM_STATUS_RESERVE != outMnpDto.getCnmStatus()
								&& "Y".equalsIgnoreCase(mnpDTO.getIsReserveMrt())) {
							errors.rejectValue("reserveId", "reserveId.notReserved");
						} else if (outMnpDto != null && BomWebPortalConstant.CNM_STATUS_NORMAL == outMnpDto.getCnmStatus()) {
							if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
								mnpDTO.setReserveId("");
							}
							
						} else if (("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId()))
								 && ((outMnpDto != null && BomWebPortalConstant.CNM_STATUS_RESERVE == outMnpDto.getCnmStatus()) 
								 || "Y".equalsIgnoreCase(mnpDTO.getIsReserveMrt()))) {
							//DS Reserve MRT
							mnpDTO.setIsReserveMrt("Y");
							
							if (StringUtils.isBlank(mnpDTO.getReserveId())) {
								errors.rejectValue("reserveId", "reserveId.required");
							} else {
								String cnmReserveId = outMnpDto.getReserveId();
								if (StringUtils.isNotBlank(cnmReserveId) &&
										!cnmReserveId.equals(mnpDTO.getReserveId())) {
									errors.rejectValue("reserveId", "invalid.reserveId");
								}
							}
						} else {
												
								mnpDTO.setCnmStatus(0);//clear data
								mnpDTO.setMsisdnLvl("");//clear data
								//mnpDTO.setNumType(null);
								errors.rejectValue("newMsisdn","invalid.msisdn");
					
							
						}
						//}
						/*
						if (mnpDTO.getActionType().equalsIgnoreCase("SUBMIT")
								&& BomWebPortalConstant.CNM_STATUS_NORMAL != mnpDTO.getCnmStatus()){
							errors.rejectValue("newMsisdn", "invalid.msisdn");
						}
						*/
					}
				}
			}
		
			if (mnpDTO.getServiceReqDateStr() != null && mnpDTO.getServiceReqDateStr().length()==0){
				//System.out.println("getServiceReqDateStr().length():"+mnpDTO.getServiceReqDateStr().length());
				//System.out.println("mnpDTO.getCutoverDateStr():"+mnpDTO.getServiceReqDateStr());
				//errors.rejectValue("cutoverDate", "invalid.msisdn");
				logger.info("mnpDTO.getServiceReqDateStr().length()==0 ");
				return;
			}else if (!Utility.isValidDate(mnpDTO.getServiceReqDateStr())) {
				errors.rejectValue("serviceReqDateStr", "invalid.Date");
			} else {
				
				if(mnpDTO.isFutherMnp() && StringUtils.isNotEmpty(mnpDTO.getServiceReqDateStr())){
					
					//DENNIS201606				
					//Check the year range
					Calendar appDatePlus = Calendar.getInstance();
					Calendar compareDate = Calendar.getInstance();

					appDatePlus.setTime(Utility.string2Date(appDate));
					compareDate.setTime(Utility.string2Date(mnpDTO.getServiceReqDateStr()));
					
					//DENNIS201606
					//compareDate.add(Calendar.YEAR, -100);
					if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())) {
						appDatePlus.add(Calendar.HOUR,  BomWebPortalConstant.DS_NEWMRTMNP_SRD_APP_EXTEND_DAYS*24);
					}else{
						appDatePlus.add(Calendar.HOUR,  BomWebPortalConstant.RS_NEWMRTMNP_SRD_APP_EXTEND_DAYS*24);
					}
					
					
					logger.info("appDatePlus: " + appDatePlus.getTime());
					logger.info("compareDate: " + compareDate.getTime());
					/*if(compareDate.after(currentDate)){
						errors.rejectValue("cutoverDateStr", "invalid.Date");
						return;
					}*/
					if (compareDate.compareTo(appDatePlus) > 0) {
						//DENNIS201606
						errors.rejectValue(
								"serviceReqDateStr", "dummy", 
								"Service Request Date should on or before "
										+ Utility.date2String(appDatePlus.getTime(), BomWebPortalConstant.DATE_FORMAT));
					}
					
				}else{
					Calendar currentDate = Calendar.getInstance();
					Calendar compareDate = Calendar.getInstance();
					Calendar serviceReqDate = Calendar.getInstance();
					serviceReqDate.setTime(Utility.string2Date(mnpDTO.getServiceReqDateStr()));
					compareDate.set(serviceReqDate.get(Calendar.YEAR),
							serviceReqDate.get(Calendar.MONTH),
							serviceReqDate.get(Calendar.DATE));				
					logger.info("currentDate: " + currentDate.getTime());
					logger.info("compareDate: " + compareDate.getTime());
					if (compareDate.before(currentDate)) {
						errors.rejectValue("serviceReqDateStr", "invalid.BackDate");
					} else {
						compareDate.add(Calendar.YEAR, -100);
						logger.info("compareDate: " + compareDate.getTime());
						if(compareDate.after(currentDate)){
							errors.rejectValue("serviceReqDateStr","invalid.Date");
						}
					}
				}
			}
			
			if ("10".equals(mnpDTO.getChannelId()) || "11".equals(mnpDTO.getChannelId())){
				if (service.checkPendingOrder(mnpDTO.getNewMsisdn(), mnpDTO.getOrderId()) > 0 || service.checkPendingMUPOrder(mnpDTO.getNewMsisdn(), mnpDTO.getOrderId()) > 0){
					errors.rejectValue("newMsisdn","invalid.msisdn");
				}
			}
			
			
	}
	
		
}}