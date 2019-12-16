package com.bomwebportal.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.MobSupportDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.service.MobSupportService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;

public class MobSupportController extends SimpleFormController {
	
	private static String CHECK_POINT = "C09999";
	private static String BULK_REQ_STS = "CO";
	private static String SOLD_SIM_STATUS = "4";
	private static String INITIAL_ORDER_STATUS = "INITIAL";
	
	private Map<String, String> simStatusMap = new HashMap<String, String>();
	private Map<String, String> mrtStatusMap = new HashMap<String, String>();
	
	enum SimStatus {
		NORMAL(2)
		, SOLD(4)
		;
		SimStatus(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public String getLabel() {
			return this.getValue() + " - " + this.toString();
		}
		private int value;
	}
	
	enum MrtStatus {
		NORMAL(2)
		, PRE_ASSGN(19)
		, RESERVE(18)
		;
		MrtStatus(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public String getLabel() {
			return this.getValue() + " - " + this.toString();
		}
		private int value;
	}
		
	public Map<String, String> getMrtStatusMap() {
		return mrtStatusMap;
	}
	public void setMrtStatusMap(Map<String, String> mrtStatusMap) {
		this.mrtStatusMap = mrtStatusMap;
	}
	
	public Map<String, String> getSimStatusMap() {
		return simStatusMap;
	}
	public void setSimStatusMap(Map<String, String> simStatusMap) {
		this.simStatusMap = simStatusMap;
	}

	private MobSupportService mobSupportService;
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	private OrderService orderService;

	public MobSupportService getMobSupportService() {
		return mobSupportService;
	}
	public void setMobSupportService(MobSupportService mobSupportService) {
		this.mobSupportService = mobSupportService;
	}
	public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}
	public void setMobCcsOrderRemarkService(MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		MobSupportDTO dto = null;
		
		String orderId = StringUtils.isNotBlank(request.getParameter("orderIdFmTextField")) ? 
				request.getParameter("orderIdFmTextField") : request.getParameter("orderIdFmDropDown");
		
		if (StringUtils.isNotBlank(orderId)) {
			orderId = orderId.trim().toUpperCase();
		}
				
		if("CLEAR".equalsIgnoreCase(request.getParameter("actionType"))) {
			return new MobSupportDTO();
		} else if ("ORDERSEARCH".equalsIgnoreCase(request.getParameter("actionType"))){
								
			if (null == orderId) {
				return new MobSupportDTO();
			} else {
				dto = doSearch(orderId);
			}
		} else if ("READFROMBOM".equalsIgnoreCase(request.getParameter("actionType"))) {
			String ocid = mobSupportService.getOcid(orderId, CHECK_POINT, BULK_REQ_STS);
			dto = doSearch(orderId);
			if (null != ocid) {
				dto.setnOcid(ocid);
			}
		} 
		return dto == null ? new MobSupportDTO() : dto;
	}
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<String[]> alertOrderList = mobSupportService.getAlertOrders();
		map.put("alertOrderList", alertOrderList);
		
		String[] orderStatusList = {"INITIAL", "SIGNOFF", "SUCCESS", "VOID"};
		map.put("orderStatusList", orderStatusList);
		
		//String[] simStatusList = {"", "2", "4"};
		//map.put("simStatusList", simStatusList);
		map.put("simStatusList", Arrays.asList(SimStatus.NORMAL, SimStatus.SOLD));
		
		//String[] mrtStatusList = {"", "2", "19"};
		//map.put("mrtStatusList", mrtStatusList);
		map.put("mrtStatusList", Arrays.asList(MrtStatus.NORMAL, MrtStatus.PRE_ASSGN, MrtStatus.RESERVE));
		return map;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws ServletException, AppRuntimeException {
		
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		
		MobSupportDTO inputDto = (MobSupportDTO)command;		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = null;
		
		if("CONFLICTORDERSEARCH".equalsIgnoreCase(request.getParameter("orderAction")))  {
			String orderId = request.getParameter("conflictOrderId");
			if (null == orderId) {
				inputDto = new MobSupportDTO();
			} else {
				inputDto = doSearch(orderId);
			}
			model.put("mobSupport", inputDto);
			return modelAndView = new ModelAndView(getSuccessView(), model);
		}
		
		List<String> failReason = null;
		String sbOcid = mobSupportService.getSbOcid(inputDto.getOrderId());
		if (StringUtils.isNotBlank(sbOcid) && !"BACKUP_ORDER".equalsIgnoreCase(request.getParameter("orderAction"))) {
			errors.reject("submittion.failure", "You are not allow to update because OCID found in SB");
			try {
				showForm(request, response, errors);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if("UPDATE".equalsIgnoreCase(request.getParameter("orderAction")))  {
			failReason = mobSupportService.executeUpdate(inputDto);
			
			OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO(); //add by wilson 20120402
		    Date now = new Date();
			orderRemarkDTO.setOrderId(inputDto.getOrderId());
			orderRemarkDTO.setRemark(user.getUsername()+" MOB Support Update Order Status");
			orderRemarkDTO.setCreateBy(user.getUsername());
			orderRemarkDTO.setCreateDate(now);
			orderRemarkDTO.setLastUpdBy(user.getUsername());
			orderRemarkDTO.setLastUpdDate(now);
			orderRemarkDTO.setType("S");
			mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemarkDTO);
			
			if (failReason != null && !failReason.isEmpty()) {
				for (String s : failReason) {
					errors.reject("submittion.failure", s);
				}
				try {
					showForm(request, response, errors);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (StringUtils.isNotBlank(inputDto.getOrderId())) {
					model.put("success", "Update Successful");
					compareCopyValue(inputDto);
				}
				return modelAndView = new ModelAndView(getSuccessView(), model);
			}
		} else if("MEMBER_UPDATE".equalsIgnoreCase(request.getParameter("orderAction")))  {
			failReason = mobSupportService.executeMemUpdate(inputDto);
			
			OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO(); //add by wilson 20120402
		    Date now = new Date();
			orderRemarkDTO.setOrderId(inputDto.getOrderId());
			orderRemarkDTO.setRemark(user.getUsername()+" MOB Support Update Member Order Status");
			orderRemarkDTO.setCreateBy(user.getUsername());
			orderRemarkDTO.setCreateDate(now);
			orderRemarkDTO.setLastUpdBy(user.getUsername());
			orderRemarkDTO.setLastUpdDate(now);
			orderRemarkDTO.setType("S");
			mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemarkDTO);
			
			if (failReason != null && !failReason.isEmpty()) {
				for (String s : failReason) {
					errors.reject("submittion.failure", s);
				}
				try {
					showForm(request, response, errors);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (StringUtils.isNotBlank(inputDto.getOrderId())) {
					model.put("success", "Update Successful");
					//compareCopyValue(inputDto);
					inputDto = doSearch(inputDto.getOrderId());
				}
				model.put("mobSupport", inputDto);
				return modelAndView = new ModelAndView(getSuccessView(), model);
			}
		} else if("CCS_UPDATE".equalsIgnoreCase(request.getParameter("orderAction")))  {
			failReason = mobSupportService.executeCcsUpdate(inputDto);
			
			OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO(); //add by wilson 20120402
		    Date now = new Date();
			orderRemarkDTO.setOrderId(inputDto.getOrderId());
			orderRemarkDTO.setRemark(user.getUsername()+" MOB Support Update Call Centre Order Status");
			orderRemarkDTO.setCreateBy(user.getUsername());
			orderRemarkDTO.setCreateDate(now);
			orderRemarkDTO.setLastUpdBy(user.getUsername());
			orderRemarkDTO.setLastUpdDate(now);
			orderRemarkDTO.setType("S");
			mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemarkDTO);
			
			if (failReason != null && !failReason.isEmpty()) {
				for (String s : failReason) {
					errors.reject("submittion.failure", s);
				}
				try {
					showForm(request, response, errors);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (StringUtils.isNotBlank(inputDto.getOrderId())) {
					model.put("success", "Update Successful");
					//compareCopyValue(inputDto);
					inputDto = doSearch(inputDto.getOrderId());
				}
				model.put("mobSupport", inputDto);
				return modelAndView = new ModelAndView(getSuccessView(), model);
			}
		} else if ("CCS_UPDATE_ReasonCd".equalsIgnoreCase(request.getParameter("orderAction")))  {
			failReason = mobSupportService.updateCcsReasonCd(inputDto.getOrderId());
			
			OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO(); //add by wilson 20120402
		    Date now = new Date();
			orderRemarkDTO.setOrderId(inputDto.getOrderId());
			orderRemarkDTO.setRemark(user.getUsername()+" MOB Support Update Call Centre Order ReasonCd");
			orderRemarkDTO.setCreateBy(user.getUsername());
			orderRemarkDTO.setCreateDate(now);
			orderRemarkDTO.setLastUpdBy(user.getUsername());
			orderRemarkDTO.setLastUpdDate(now);
			orderRemarkDTO.setType("S");
			mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemarkDTO);
			
			if (failReason != null && !failReason.isEmpty()) {
				for (String s : failReason) {
					errors.reject("submittion.failure", s);
				}
				try {
					showForm(request, response, errors);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (StringUtils.isNotBlank(inputDto.getOrderId())) {
					model.put("success", "Update Successful");
					//compareCopyValue(inputDto);
					inputDto = doSearch(inputDto.getOrderId());
				}
				model.put("mobSupport", inputDto);
				return modelAndView = new ModelAndView(getSuccessView(), model);
			}
		} else if ("BACKUP_ORDER".equalsIgnoreCase(request.getParameter("orderAction"))) {
			model.put("success", "Update Successful");
			if (StringUtils.isNotBlank(inputDto.getOrderId())) {
				orderService.backupOrder(inputDto.getOrderId());
				model.put("success", "Backup Successful");
			}
		} else if ("SIM_UPDATE".equalsIgnoreCase(request.getParameter("orderAction"))) {
			failReason = mobSupportService.executeSIMUpdate(inputDto);
			model.put("success", "Update Successful");
		} else if ("MRT_UPDATE".equalsIgnoreCase(request.getParameter("orderAction"))) {
			failReason = mobSupportService.executeMRTUpdate(inputDto);
			model.put("success", "Update Successful");
		}
			
		model.put("mobSupport", inputDto);

		return modelAndView = new ModelAndView(getSuccessView(), model);
	}
	
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder dataBinder) throws Exception{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		dataBinder.registerCustomEditor(Date.class,new CustomDateEditor(df,false));
		super.initBinder(request,dataBinder);
	}
	
	
	private void compareCopyValue(MobSupportDTO dto) {
		//compare ocid 
		if (StringUtils.isNotBlank(dto.getnOcid())) {
			if (!dto.getnOcid().equalsIgnoreCase(dto.getoOcid())) {
				dto.setoOcid(dto.getnOcid());
			}
		}
		//compare order status
		if (StringUtils.isNotBlank(dto.getnOrderStatus())) {
			if (!dto.getnOrderStatus().equalsIgnoreCase(dto.getoOrderStatus())) {
				dto.setoOrderStatus(dto.getnOrderStatus());
			}
		}
		//compare sim status
		if (StringUtils.isNotBlank(dto.getnSimStatus())) {
			if (!dto.getnSimStatus().equalsIgnoreCase(dto.getoSimStatus())) {
				dto.setoSimStatus(dto.getnSimStatus());
				dto.setoSimStatusDesc(simStatusMap.get(dto.getnSimStatus()));
			}
		}
		//compare mrt status
		if (StringUtils.isNotBlank(dto.getnMrtStatus())) {
			if (!dto.getnMrtStatus().equalsIgnoreCase(dto.getoMrtStatus())) {
				dto.setoMrtStatus(dto.getnMrtStatus());
				dto.setoMrtStatusDesc(mrtStatusMap.get(dto.getnMrtStatus()));
			}
		}
		if (INITIAL_ORDER_STATUS.equalsIgnoreCase(dto.getnOrderStatus())) {
			if (!Utility.date2String(dto.getnAppDate(), "dd/MM/yyyy").equalsIgnoreCase(dto.getoAppDate())) {
				dto.setoAppDate(Utility.date2String(dto.getnAppDate(), "dd/MM/yyyy"));
			}
		} else if ("REJECTED".equalsIgnoreCase(dto.getnOrderStatus()) && dto.getOrderId().startsWith("D")) {
			dto.setErrMsg(dto.getnErrMsg());
		}
	}
	
	private MobSupportDTO doSearch(String orderId) {
		
		MobSupportDTO inputDto = new MobSupportDTO();
		
		if (orderId.substring(orderId.length() - 1).matches("[A-Z]{1}")) {
			inputDto = mobSupportService.getSBMUPOrderBasicInfo(orderId);
		} else {
			inputDto = mobSupportService.getSBOrderBasicInfo(orderId);
		}
		
		inputDto.setnOrderStatus(inputDto.getoOrderStatus());
		//inputDto.setoSimStatus(mobSupportService.getSBsimStatus(inputDto.getSim()));
		inputDto.setoSimStatus(mobSupportService.getWSsimStatus(inputDto.getSim()));
		inputDto.setnSimStatus(inputDto.getoSimStatus());
		inputDto.setConflictOrder(mobSupportService.getConflictOrder(orderId, inputDto.getSim(), inputDto.getMrt()));
		
		if (StringUtils.isNotBlank(inputDto.getMnpInd()) && ("N".equalsIgnoreCase(inputDto.getMnpInd()) || "A".equalsIgnoreCase(inputDto.getMnpInd()))) {
			if ("1".equals(inputDto.getChannelId())) {
				inputDto.setoMrtStatus(mobSupportService.getCentralNumPoolWSmrtStatus(inputDto.getMrt(), inputDto.getShopCode()));
			} else {
				inputDto.setoMrtStatus(mobSupportService.getWSmrtStatus(inputDto.getMrt(), inputDto.getShopCode()));
			}
			//inputDto.setoMrtStatus(mobSupportService.getWSmrtStatus(inputDto.getMrt(), inputDto.getShopCode()));
			inputDto.setoMrtStatus(mobSupportService.getCentralNumPoolWSmrtStatus(inputDto.getMrt(), inputDto.getShopCode()));
			inputDto.setnMrtStatus(inputDto.getoMrtStatus());
			inputDto.setBomMrtStatus(mobSupportService.getBomMrtStatus(inputDto.getMrt()));
		} else if (StringUtils.isNotBlank(inputDto.getMnpInd()) 
				&& ("MUPS01".equalsIgnoreCase(inputDto.getMnpInd()) || 
					"MUPS02".equalsIgnoreCase(inputDto.getMnpInd()) ||
					"MUPS03".equalsIgnoreCase(inputDto.getMnpInd()) ||
					"MUPS04".equalsIgnoreCase(inputDto.getMnpInd()) ||
					"MUPS05".equalsIgnoreCase(inputDto.getMnpInd()))) {
			if ("1".equals(inputDto.getChannelId())) {
				inputDto.setoMrtStatus(mobSupportService.getCentralNumPoolWSmrtStatus(inputDto.getMrt(), inputDto.getShopCode()));
			} else {
				inputDto.setoMrtStatus(mobSupportService.getWSmrtStatus(inputDto.getMrt(), inputDto.getShopCode()));
			}
			inputDto.setoMrtStatus(mobSupportService.getCentralNumPoolWSmrtStatus(inputDto.getMrt(), inputDto.getShopCode()));
			inputDto.setnMrtStatus(inputDto.getoMrtStatus());
			inputDto.setBomMrtStatus(mobSupportService.getBomMrtStatus(inputDto.getMrt()));
		} else {
			inputDto.setoMrtStatus(null);
			inputDto.setnMrtStatus(null);
			inputDto.setBomMrtStatus(null);
		}
		
		inputDto.setBomSimStatus(mobSupportService.getBomSimStatus(inputDto.getSim()));
		
		if (StringUtils.isBlank(inputDto.getoOcid()) && SOLD_SIM_STATUS.equals(inputDto.getoSimStatus())) {
			String ocid = mobSupportService.getOcid(orderId, CHECK_POINT, BULK_REQ_STS);
			if (ocid != null) {
				inputDto.setnOcid(ocid);
			}
		}
		
		inputDto.setoSimStatusDesc(simStatusMap.get(inputDto.getoSimStatus()));
		inputDto.setoMrtStatusDesc(mrtStatusMap.get(inputDto.getoMrtStatus()));
		
		return inputDto;
	}
}