package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTStockUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService.MsisdnStatus;
import com.bomwebportal.mob.ccs.service.MrtInventoryService;
import com.bomwebportal.mob.ccs.service.MrtInventoryService.FunctionCd;
import com.bomwebportal.mob.ccs.service.MrtInventoryService.ParmType;

public class MobCcsMRTStockController extends SimpleFormController {
    protected final Log logger = LogFactory.getLog(getClass());
    
    private MrtInventoryService mrtInventoryService;
    private CodeLkupService codeLkupService;
    private MobCcsMrtService mobCcsMrtService;
    
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

	public MrtInventoryService getMrtInventoryService() {
		return mrtInventoryService;
	}

	public void setMrtInventoryService(MrtInventoryService mrtInventoryService) {
		this.mrtInventoryService = mrtInventoryService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	@Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		MRTStockUI form = new MRTStockUI();
		
		return form;
    }
    
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		List<CodeLkupDTO> numTypeList = codeLkupService.getCodeLkupDTOALL("MIP_NUM_TYPE");
		Map<String, List<MaintParmLkupDTO>> serviceTypeMap = new HashMap<String, List<MaintParmLkupDTO>>();
		for (CodeLkupDTO numType : numTypeList) {
			List<MaintParmLkupDTO> serviceTypeList = this.mrtInventoryService.getMaintParmValue(numType.getCodeId(), FunctionCd.MRT_INVENTORY, ParmType.SERVICE_TYPE);
			if (CollectionUtils.isNotEmpty(serviceTypeList)) {
				for (MaintParmLkupDTO serviceType : serviceTypeList) {
					
				}
			}
			//serviceTypeMap.put(numType.getCodeId(), arg1);
		}
		
		referenceData.put("numTypeList", numTypeList);
		
		referenceData.put("serviceTypeList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.SERVICE_TYPE)); 
		referenceData.put("msisdnlvlList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.GRADE));
		referenceData.put("channelCodeList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.CHANNEL));
		referenceData.put("goldenNumChannelCodeList", codeLkupService.getCodeLkupDTOALL("CCS_CODE"));
		referenceData.put("msisdnStatusList", Arrays.asList(MsisdnStatus.values()));
		
		return referenceData;
    }

	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		MRTStockUI form = (MRTStockUI) command;
		ModelAndView modelAndView = new ModelAndView("mobccsmrtstock", model);
		modelAndView.addObject("command", command);
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		List<MrtInventoryDTO> recordList = null;
		if (StringUtils.isNotBlank(form.getOrderId())) {
			recordList = this.searchByOrderId(form.getOrderId(), user);
		} else if (StringUtils.isNotBlank(form.getMsisdn())) {
			recordList = this.searchByMsisdn(form.getMsisdn(), user);
		} else {
			recordList = this.searchByCriteria(form, user);
		}
		modelAndView.addObject("recordList", recordList);

		return modelAndView;
	}

	private List<MrtInventoryDTO> searchByOrderId(String orderId, BomSalesUserDTO user) {
		MrtInventoryDTO record = this.mrtInventoryService.getMrtInventoryDTOByOrderId(StringUtils.trim(orderId), this.getAllowedChannelCds(user.getChannelCd()));
		if (record != null) {
			return Arrays.asList(record);
		}
		return Collections.emptyList();
	}
	
	private List<MrtInventoryDTO> searchByMsisdn(String msisdn, BomSalesUserDTO user) {
		return this.mrtInventoryService.getMrtInventoryDTOALL(StringUtils.trim(msisdn), this.getAllowedChannelCds(user.getChannelCd()));
	}
	
	private List<MrtInventoryDTO> searchByCriteria(MRTStockUI form, BomSalesUserDTO user) {
		if (StringUtils.isNotBlank(form.getServiceType()) 
				|| StringUtils.isNotBlank(form.getMsisdnlvl())
				|| StringUtils.isNotBlank(form.getChannelCode())
				|| form.getMsisdnStatus() != null
				|| StringUtils.isNotBlank(form.getNumType())) {
			MrtInventoryDTO dto = new MrtInventoryDTO();
			dto.setNumType(form.getNumType());
			dto.setSrvNumType(form.getServiceType());
			dto.setMsisdnlvl(form.getMsisdnlvl());
			dto.setChannelCd(form.getChannelCode());
			dto.setMsisdnStatus(form.getMsisdnStatus());
			return this.mrtInventoryService.getMrtInventoryDTOBySearch(dto, this.getAllowedChannelCds(user.getChannelCd())); 
		}
		return Collections.emptyList();
	}
	
	private List<String> getAllowedChannelCds(String userChannelCd) {
		List<String> channelCds = new ArrayList<String>();
		List<MaintParmLkupDTO> list = this.mrtInventoryService.getMaintParmValue(userChannelCd, FunctionCd.MRT_INVENTORY, ParmType.CHANNEL);
		if (list != null) {
			for (MaintParmLkupDTO dto : list) {
				channelCds.add(dto.getParmValue());
			}
		}
		return channelCds;
	}

}
