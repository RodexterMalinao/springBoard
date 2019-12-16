package com.bomwebportal.mob.ccs.web;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.ApprovalLogDTO;
import com.bomwebportal.dto.MobReqLogDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MrtInventoryService;
import com.bomwebportal.mob.ccs.service.SpecialMRTReserveService;
import com.bomwebportal.mob.ccs.service.MobCcsMaintParmLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsSpecialMrtRequestService;
import com.bomwebportal.service.MobReqLogService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsSpecialMrtRequestController extends SimpleFormController{
	static final String SPECIAL_MRT_REQUEST_STATUS_REQUEST = "REQUEST";
	static final String SPECIAL_MRT_REQUEST_STATUS_CANCELLED = "CANCELLED";
	static final String SPECIAL_MRT_REQUEST_STATUS_APPROVED = "APPROVED";
	static final String SPECIAL_MRT_REQUEST_STATUS_REJECTED = "REJECTED";
	static final String SPECIAL_MRT_REQUEST_STATUS_PROCEED = "PROCEED";
	
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsSpecialMrtRequestService mobCcsSpecialMrtRequestService;
	private MobReqLogService mobReqLogService;
	private MobCcsMaintParmLkupService mobCcsMaintParmLkupService;
	private SpecialMRTReserveService specialMrtReserveService;
	private MrtInventoryService mrtInventoryService;
	private MobCcsApprovalLogService mobCcsApprovalLogService;
	
	public MobCcsSpecialMrtRequestService getMobCcsSpecialMrtRequestService() {
		return mobCcsSpecialMrtRequestService;
	}

	public void setMobCcsSpecialMrtRequestService(
			MobCcsSpecialMrtRequestService mobCcsSpecialMrtRequestService) {
		this.mobCcsSpecialMrtRequestService = mobCcsSpecialMrtRequestService;
	}	
	
	public MobReqLogService getMobReqLogService() {
		return mobReqLogService;
	}

	public void setMobReqLogService(MobReqLogService mobReqLogService) {
		this.mobReqLogService = mobReqLogService;
	}

	public MobCcsMaintParmLkupService getMobCcsMaintParmLkupService() {
		return mobCcsMaintParmLkupService;
	}

	public void setMobCcsMaintParmLkupService(
			MobCcsMaintParmLkupService mobCcsMaintParmLkupService) {
		this.mobCcsMaintParmLkupService = mobCcsMaintParmLkupService;
	}	
	
	public SpecialMRTReserveService getSpecialMrtReserveService() {
		return specialMrtReserveService;
	}

	public void setSpecialMrtReserveService(
			SpecialMRTReserveService specialMrtReserveService) {
		this.specialMrtReserveService = specialMrtReserveService;
	}

	public MrtInventoryService getMrtInventoryService() {
		return mrtInventoryService;
	}

	public void setMrtInventoryService(MrtInventoryService mrtInventoryService) {
		this.mrtInventoryService = mrtInventoryService;
	}

	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(
			MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}


	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("MobCcsSpecialMrtRequestController@formBackingObject is called");
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		SpecialMrtRequestDTO specialMrtRequestDTO = new SpecialMrtRequestDTO();
		String requestId = ServletRequestUtils.getRequiredStringParameter(request, "requestId");
		

		if (StringUtils.isNotBlank(requestId)){   //recall
			specialMrtRequestDTO = mobCcsSpecialMrtRequestService.getSpecialMrtRequestDTO(requestId);
			if (specialMrtRequestDTO != null){
				specialMrtRequestDTO.setOriginalMsisdn(specialMrtRequestDTO.getMsisdn());
			}else{
				return new SpecialMrtRequestDTO();
			}
		}
		else{ //new create request
			
			Integer count = specialMrtReserveService.noOfRequests(salesUserDto.getUsername());
			if (count >= 2) {			
				request.setAttribute("twoRequestsAlready", true);
			
			} else {				
				request.setAttribute("twoRequestsAlready", false);
				
			}
			
			specialMrtRequestDTO.setChannel(salesUserDto.getChannelCd());
			specialMrtRequestDTO.setRequestor(salesUserDto.getUsername());
		}
			
		return specialMrtRequestDTO;
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsSpecialMrtRequestController@ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		referenceData.put("user", salesUserDto);
		
		/*------requestChannelCd---*/
		String requestChannelCd = "";
		String requestId = ServletRequestUtils.getRequiredStringParameter(request, "requestId");
		if (StringUtils.isNotBlank(requestId)){	
			SpecialMrtRequestDTO specialMrtRequestDTO = mobCcsSpecialMrtRequestService.getSpecialMrtRequestDTO(requestId);
			if (specialMrtRequestDTO!=null){
				requestChannelCd = specialMrtRequestDTO.getChannel();
			}else{
				requestChannelCd = salesUserDto.getChannelCd();;
			}
		}else{
			requestChannelCd = salesUserDto.getChannelCd();
		}
;	
		
		referenceData.put("titleList", this.mobCcsMaintParmLkupService.getMaintParmLkupDTO(requestChannelCd, "MOB", "SPECIAL_MRT_REQUEST", "TITLE" ));	
		referenceData.put("contractPeriodList", this.mobCcsMaintParmLkupService.getMaintParmLkupDTO(requestChannelCd, "MOB", "SPECIAL_MRT_REQUEST", "PERIOD" ));
		referenceData.put("recurrentAmtList", this.mobCcsMaintParmLkupService.getMaintParmLkupDTO(requestChannelCd, "MOB", "SPECIAL_MRT_REQUEST", "MTHLY_RATE" ));
		//referenceData.put("msisdnlvlList", this.mobCcsMaintParmLkupService.getMaintParmLkupDTO(salesUserDto.getChannelCd(), "MOB", "SPECIAL_MRT_REQUEST", "MRT_GRADE" ));
		referenceData.put("msisdnlvlList", this.mobCcsMaintParmLkupService.getMaintParmLkupDTO("CPA", "MOB", "SPECIAL_MRT_REQUEST", "MRT_GRADE" ));
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String requestId = ServletRequestUtils.getRequiredStringParameter(request, "requestId");
		String from =ServletRequestUtils.getRequiredStringParameter(request, "from");
		SpecialMrtRequestDTO specialMrtRequestDTO = (SpecialMrtRequestDTO) command;	
		MrtInventoryDTO oldMrtInventoryDTO = new MrtInventoryDTO(); 
		MrtInventoryDTO mrtInventoryDTO = new MrtInventoryDTO();
		String actionType = specialMrtRequestDTO.getActionType();
		String createRequestId ="";
		String viewStatus = "mobccsspecialmrtrequest.html";
		String logCase = "";
		
		if ("SAVE".equalsIgnoreCase(actionType)){		
			if (StringUtils.isNotBlank(requestId)){	
				specialMrtRequestDTO.setRequestId(requestId);
				specialMrtRequestDTO.setLastUpdBy(salesUserDto.getUsername());
				mobCcsSpecialMrtRequestService.updateSpecialMrtRequestDTO(specialMrtRequestDTO);
				//viewStatus += "?requestId=" + requestId;
			}
			else{   //insert
				specialMrtRequestDTO.setCreateBy(salesUserDto.getUsername());
				specialMrtRequestDTO.setLastUpdBy(salesUserDto.getUsername());
				specialMrtRequestDTO.setRequestBy(salesUserDto.getUsername());
				specialMrtRequestDTO.setApprovalResult(SPECIAL_MRT_REQUEST_STATUS_REQUEST);
				createRequestId = mobCcsSpecialMrtRequestService.insertSpecialMrtRequest(specialMrtRequestDTO);
				viewStatus = "mobccsmrtreserve.html";
			}
		}
		else if ("CANCEL".equalsIgnoreCase(actionType)){
			specialMrtRequestDTO.setRequestId(requestId);
			specialMrtRequestDTO.setApprovalResult(SPECIAL_MRT_REQUEST_STATUS_CANCELLED);
			specialMrtRequestDTO.setLastUpdBy(salesUserDto.getUsername());
			mobCcsSpecialMrtRequestService.updateSpecialMrtRequestStatus(specialMrtRequestDTO);
			
			//viewStatus += "?requestId=" + requestId;
		}
		else if ("APPROVE".equalsIgnoreCase(actionType)){
			specialMrtRequestDTO.setRequestId(requestId);
			specialMrtRequestDTO.setApprovalResult(SPECIAL_MRT_REQUEST_STATUS_APPROVED);
			specialMrtRequestDTO.setLastUpdBy(salesUserDto.getUsername());
			mobCcsSpecialMrtRequestService.updateSpecialMrtRequestStatus(specialMrtRequestDTO);
			
			/*insert approval log*/
			String authorizedId = mobCcsApprovalLogService.getNextAuthorizedId();
			
			if (!StringUtils.isBlank(authorizedId)) {
				ApprovalLogDTO dto = new ApprovalLogDTO();
				dto.setAuthorizedId(authorizedId);
				dto.setOrderId(requestId);
				dto.setAuthorizedBy(salesUserDto.getUsername());
				dto.setAuthorizedAction("AU08");
				dto.setShopCode(salesUserDto.getChannelCd());
				dto.setLastUpdBy(salesUserDto.getUsername());
				mobCcsApprovalLogService.insertApprovalLogDTO(dto);
			} 
			/**/
			
			
			//viewStatus += "?requestId=" + requestId;
		}
		else if ("REJECT".equalsIgnoreCase(actionType)){
			SpecialMrtRequestDTO rejectDto = new SpecialMrtRequestDTO();
			rejectDto.setRequestId(requestId);
			rejectDto.setApprovalResult(SPECIAL_MRT_REQUEST_STATUS_REJECTED);
			rejectDto.setLastUpdBy(salesUserDto.getUsername());
			rejectDto.setAdminRemark(specialMrtRequestDTO.getAdminRemark());
			mobCcsSpecialMrtRequestService.updateSpecialMrtRequestDTO(rejectDto);
			mobCcsSpecialMrtRequestService.updateSpecialMrtRequestStatus(rejectDto);
			
			//viewStatus += "?requestId=" + requestId;
		}
		else if ("UPDATE".equalsIgnoreCase(actionType)){
			
			/*update SPECIAL_MRT_REQUEST*/
			specialMrtRequestDTO.setRequestId(requestId);
			specialMrtRequestDTO.setLastUpdBy(salesUserDto.getUsername());
			specialMrtRequestDTO.setApprovalResult(SPECIAL_MRT_REQUEST_STATUS_PROCEED);
			mobCcsSpecialMrtRequestService.updateSpecialMrtRequestDTO(specialMrtRequestDTO);
			mobCcsSpecialMrtRequestService.updateSpecialMrtRequestStatus(specialMrtRequestDTO);
			
			/*update bomweb_mrt_status*/
			String staffId = salesUserDto.getUsername();
			String srvNumType = "PCCW3G";
			
			if (!specialMrtReserveService.getSpecialMrtReserveByRequestId(requestId)){	
				//record not exist, do insert
				specialMrtReserveService.insertSpecialMrtReserve(specialMrtRequestDTO, staffId, srvNumType);
			}else{		
				//record exist, do update
				specialMrtReserveService.updateSpecialMrtReserve(specialMrtRequestDTO, staffId, srvNumType);
			}
			
			
			/*update bomweb_mrt_inventory*/
			oldMrtInventoryDTO = mobCcsSpecialMrtRequestService.getMrtInventoryByRequestId(requestId);
			if (oldMrtInventoryDTO == null){
				//record not exist
				Date now = new Date();
				//MrtInventoryDTO mrtInventoryDTO = new MrtInventoryDTO();
				if (StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId())){
					//if reserve_id exist-->new add CNM MRT-->insert
					mrtInventoryDTO.setMsisdn(specialMrtRequestDTO.getMsisdn());
					mrtInventoryDTO.setMsisdnlvl(specialMrtRequestDTO.getMsisdnlvl());
					mrtInventoryDTO.setMsisdnStatus(18);
					mrtInventoryDTO.setChannelCd("CCS");
					mrtInventoryDTO.setSrvNumType("PCCW3G");
					mrtInventoryDTO.setReserveId(specialMrtRequestDTO.getReserveId());
					mrtInventoryDTO.setResOperId(specialMrtRequestDTO.getResOperId());
					mrtInventoryDTO.setApprovalSerial(specialMrtRequestDTO.getApprovalSerial());
					mrtInventoryDTO.setRequestId(specialMrtRequestDTO.getRequestId());
					mrtInventoryDTO.setStockInDate(now);
					mrtInventoryDTO.setCreatedDate(now);
					mrtInventoryDTO.setLastUpdDate(now);
					mrtInventoryDTO.setCreatedBy(staffId);
					mrtInventoryDTO.setLastUpdBy(staffId);
					mrtInventoryService.insertMrtInventory(mrtInventoryDTO);
				}else{
					//if reserve_id not exist-->from mrt pool-->update
					
					mrtInventoryDTO.setMsisdn(specialMrtRequestDTO.getMsisdn());
					mrtInventoryDTO.setMsisdnlvl(specialMrtRequestDTO.getMsisdnlvl());
					mrtInventoryDTO.setMsisdnStatus(18);
					mrtInventoryDTO.setChannelCd(specialMrtRequestDTO.getChannel());
					mrtInventoryDTO.setSrvNumType("PCCW3G");
					mrtInventoryDTO.setApprovalSerial(specialMrtRequestDTO.getApprovalSerial());
					mrtInventoryDTO.setRequestId(specialMrtRequestDTO.getRequestId());
					mrtInventoryDTO.setLastUpdBy(staffId);
						
					mobCcsSpecialMrtRequestService.updateMrtInventoryByMrt(mrtInventoryDTO);
					
				}
				
			}else{
				
				//record exist
				if (StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isNotBlank(oldMrtInventoryDTO.getReserveId())){
					//case 1
					
					mrtInventoryDTO.setRequestId(requestId);
					mrtInventoryDTO.setMsisdn(specialMrtRequestDTO.getMsisdn());
					mrtInventoryDTO.setMsisdnlvl(specialMrtRequestDTO.getMsisdnlvl());
					mrtInventoryDTO.setMsisdnStatus(18);
					mrtInventoryDTO.setChannelCd("CCS");
					mrtInventoryDTO.setSrvNumType("PCCW3G");
					mrtInventoryDTO.setApprovalSerial(specialMrtRequestDTO.getApprovalSerial());
					mrtInventoryDTO.setReserveId(specialMrtRequestDTO.getReserveId());
					mrtInventoryDTO.setResOperId(specialMrtRequestDTO.getResOperId());
					mrtInventoryDTO.setRequestId(specialMrtRequestDTO.getRequestId());
					mrtInventoryDTO.setLastUpdBy(staffId);
					
					mobCcsSpecialMrtRequestService.updateMrtInventoryByRequestId(mrtInventoryDTO);
					//mobCcsSpecialMrtRequestService.updateMrtInventoryByMrt(mrtInventoryDTO);
					
					if (oldMrtInventoryDTO.getMsisdn().equals(specialMrtRequestDTO.getMsisdn())){
						logCase = " (Update information for same number from CNM)";
					}else{
						logCase = " (Change number that obtained from CNM: " +oldMrtInventoryDTO.getMsisdn()+" -> a number from CNM "+specialMrtRequestDTO.getMsisdn()+".Record of MRT in inventory table is replaced.)";
					}
				}else if (StringUtils.isBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isNotBlank(oldMrtInventoryDTO.getReserveId())){
					//case 2
					
					
					// 25 = suspend
					mobCcsSpecialMrtRequestService.updateMrtInventoryStatusByMrt("25", staffId, specialMrtRequestDTO.getOriginalMsisdn(), specialMrtRequestDTO.getRequestId());
					
					mrtInventoryDTO.setMsisdn(specialMrtRequestDTO.getMsisdn());
					mrtInventoryDTO.setMsisdnlvl(specialMrtRequestDTO.getMsisdnlvl());
					mrtInventoryDTO.setMsisdnStatus(18);
					mrtInventoryDTO.setChannelCd(specialMrtRequestDTO.getChannel());
					mrtInventoryDTO.setSrvNumType(oldMrtInventoryDTO.getSrvNumType());
					mrtInventoryDTO.setApprovalSerial(specialMrtRequestDTO.getApprovalSerial());
					mrtInventoryDTO.setRequestId(specialMrtRequestDTO.getRequestId());
					
					mrtInventoryDTO.setLastUpdBy(staffId);
					
					mobCcsSpecialMrtRequestService.updateMrtInventoryByMrt(mrtInventoryDTO);
					
					logCase = " (Change number that obtained from CNM: "+oldMrtInventoryDTO.getMsisdn()+" -> a number from inventory "+specialMrtRequestDTO.getMsisdn()+". Status of old MRT "+oldMrtInventoryDTO.getMsisdn()+": RESERVE -> SUSPENDED)";
					
				}else if (StringUtils.isNotBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isBlank(oldMrtInventoryDTO.getReserveId())){
					//case 3
					
					
					//frozen old MRT
					mobCcsSpecialMrtRequestService.updateMrtInventoryStatusByMrt("05", staffId, specialMrtRequestDTO.getOriginalMsisdn(), specialMrtRequestDTO.getRequestId());
					
					//insert new MRT
					Date now = new Date();
					mrtInventoryDTO.setMsisdn(specialMrtRequestDTO.getMsisdn());
					mrtInventoryDTO.setMsisdnlvl(specialMrtRequestDTO.getMsisdnlvl());
					mrtInventoryDTO.setMsisdnStatus(18);
					mrtInventoryDTO.setChannelCd("CCS");
					mrtInventoryDTO.setSrvNumType("PCCW3G");
					mrtInventoryDTO.setReserveId(specialMrtRequestDTO.getReserveId());
					mrtInventoryDTO.setResOperId(specialMrtRequestDTO.getResOperId());
					mrtInventoryDTO.setApprovalSerial(specialMrtRequestDTO.getApprovalSerial());
					mrtInventoryDTO.setRequestId(specialMrtRequestDTO.getRequestId());
					mrtInventoryDTO.setStockInDate(now);
					mrtInventoryDTO.setCreatedDate(now);
					mrtInventoryDTO.setLastUpdDate(now);
					mrtInventoryDTO.setCreatedBy(staffId);
					mrtInventoryDTO.setLastUpdBy(staffId);
					mrtInventoryService.insertMrtInventory(mrtInventoryDTO);
					
					logCase = " (Change number that obtained from inventory: "+oldMrtInventoryDTO.getMsisdn()+" -> a number from CNM "+specialMrtRequestDTO.getMsisdn()+". Status of old MRT "+oldMrtInventoryDTO.getMsisdn()+": RESERVE -> FROZEN)";
					
				}else if (StringUtils.isBlank(specialMrtRequestDTO.getReserveId()) && StringUtils.isBlank(oldMrtInventoryDTO.getReserveId())){
					//case 4
					
					
					if (!specialMrtRequestDTO.getOriginalMsisdn().equals(specialMrtRequestDTO.getMsisdn())){
						//05 = frozen
						mobCcsSpecialMrtRequestService.updateMrtInventoryStatusByMrt("05", staffId, specialMrtRequestDTO.getOriginalMsisdn(), specialMrtRequestDTO.getRequestId());
				
						mrtInventoryDTO.setMsisdn(specialMrtRequestDTO.getMsisdn());
						mrtInventoryDTO.setMsisdnlvl(specialMrtRequestDTO.getMsisdnlvl());
						mrtInventoryDTO.setMsisdnStatus(18);
						mrtInventoryDTO.setChannelCd(specialMrtRequestDTO.getChannel());
						mrtInventoryDTO.setSrvNumType(oldMrtInventoryDTO.getSrvNumType());
						mrtInventoryDTO.setApprovalSerial(specialMrtRequestDTO.getApprovalSerial());
						mrtInventoryDTO.setRequestId(specialMrtRequestDTO.getRequestId());
						mrtInventoryDTO.setLastUpdBy(staffId);
						
						mobCcsSpecialMrtRequestService.updateMrtInventoryByMrt(mrtInventoryDTO);
						
						logCase = " (Change number that obtained from inventory: "+oldMrtInventoryDTO.getMsisdn()+" -> a number from inventory "+specialMrtRequestDTO.getMsisdn()+". Status of old MRT "+oldMrtInventoryDTO.getMsisdn()+": RESERVE -> FROZEN)";
						
					}
					else{
						mrtInventoryDTO.setMsisdn(specialMrtRequestDTO.getMsisdn());
						mrtInventoryDTO.setMsisdnlvl(specialMrtRequestDTO.getMsisdnlvl());
						mrtInventoryDTO.setMsisdnStatus(oldMrtInventoryDTO.getMsisdnStatus());
						mrtInventoryDTO.setChannelCd(specialMrtRequestDTO.getChannel());
						mrtInventoryDTO.setSrvNumType(oldMrtInventoryDTO.getSrvNumType());
						mrtInventoryDTO.setApprovalSerial(specialMrtRequestDTO.getApprovalSerial());
						mrtInventoryDTO.setRequestId(specialMrtRequestDTO.getRequestId());
						mrtInventoryDTO.setLastUpdBy(staffId);
						
						mobCcsSpecialMrtRequestService.updateMrtInventoryByMrt(mrtInventoryDTO);
						
						logCase = " (Update information for same number from inventory)";
						
					}
				}
				
			}
			
			
			//viewStatus += "?requestId=" + requestId;
		}
		else if ("EXIT".equalsIgnoreCase(actionType)){ //exit

			if ("smr".equalsIgnoreCase(from)){
				viewStatus = "mobccsspecialmrtsummary.html";
			}else{
				viewStatus = "mobccsmrtreserve.html";
			}
			/*if ("CPA".equalsIgnoreCase(salesUserDto.getChannelCd())){
				viewStatus = "mobccsspecialmrtsummary.html";
			}else{
				viewStatus = "mobccsmrtreserve.html";
			}*/
			return new ModelAndView(new RedirectView(viewStatus));

		}
		
		/*update MOB_REQ_LOG*/
		if (!"EXIT".equalsIgnoreCase(actionType)){
			MobReqLogDTO mobReqLogDTO = new MobReqLogDTO();
			
			if (StringUtils.isNotBlank(requestId)){	
				mobReqLogDTO.setRequestId(requestId);
				mobReqLogDTO.setAction(salesUserDto.getUsername()+" "+actionType+ " "+logCase);
				
			}
			else{ //first create
				mobReqLogDTO.setRequestId(createRequestId);
				mobReqLogDTO.setAction(salesUserDto.getUsername()+" "+"CREATE");		 
			}
			mobReqLogDTO.setLastUpdBy(salesUserDto.getUsername());
			mobReqLogDTO.setCreateBy(salesUserDto.getUsername());
			mobReqLogDTO.setType("SP_MRT_RQ");
			mobReqLogService.insertMobReqLog(mobReqLogDTO);  
			
		}
	
		
		ModelAndView modelAndView = new ModelAndView(new RedirectView(viewStatus));	
		modelAndView.addObject("requestId", requestId);
		modelAndView.addObject("from", from);
		modelAndView.addObject("updateDate",Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
		modelAndView.addObject("success", true);
	
		
		return modelAndView;
	}
}

