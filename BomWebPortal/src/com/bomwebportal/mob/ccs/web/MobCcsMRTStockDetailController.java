package com.bomwebportal.mob.ccs.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTStockDetailUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService.MsisdnStatus;
import com.bomwebportal.mob.ccs.service.MrtInventoryService;
import com.bomwebportal.mob.ccs.service.MrtInventoryService.FunctionCd;
import com.bomwebportal.mob.ccs.service.MrtInventoryService.ParmType;

public class MobCcsMRTStockDetailController extends SimpleFormController {
    protected final Log logger = LogFactory.getLog(getClass());
    
    private MrtInventoryService mrtInventoryService;
    private CodeLkupService codeLkupService;
    private MobCcsMrtService mobCcsMrtService;
    private MobCcsMrtStatusService mobCcsMrtStatusService;
    
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

	public MobCcsMrtStatusService getMobCcsMrtStatusService() {
		return mobCcsMrtStatusService;
	}

	public void setMobCcsMrtStatusService(
			MobCcsMrtStatusService mobCcsMrtStatusService) {
		this.mobCcsMrtStatusService = mobCcsMrtStatusService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		MRTStockDetailUI form = new MRTStockDetailUI();
		
		String rowId = request.getParameter("rowId");
		if (StringUtils.isNotBlank(rowId)) {
			MrtInventoryDTO record = this.mrtInventoryService.getMrtInventoryDTO(rowId);
			if (record != null) {
				form.setMsisdn(record.getMsisdn());
				form.setMsisdnlvl(record.getMsisdnlvl());
				form.setNumType(record.getNumType());
				form.setServiceType(record.getSrvNumType());
				form.setCityCode(record.getCityCd());
				form.setMsisdnStatus(record.getMsisdnStatus());
				form.setChannelCode(record.getChannelCd());
				form.setRowId(record.getRowId());
				form.setReserveId(record.getReserveId());
				form.setOperId(record.getResOperId());
				form.setMsisdnReserveRecords(this.mobCcsMrtStatusService.getMrtStatusDTOByMsisdnAndStatus(record.getMsisdn(), MsisdnStatus.RESERVE));
			}
		}
		
		return form;
	}

	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		referenceData.put("serviceTypeList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.SERVICE_TYPE)); 
		referenceData.put("msisdnlvlList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.GRADE));
		referenceData.put("channelCodeList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.CHANNEL));
		referenceData.put("goldenNumChannelCodeList", codeLkupService.getCodeLkupDTOALL("CCS_CODE"));
		referenceData.put("msisdnStatusList", Arrays.asList(MsisdnStatus.values()));
		referenceData.put("cityCodeList", Arrays.asList("SH", "GZ"));
		
		return referenceData;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		MRTStockDetailUI form = (MRTStockDetailUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		MrtInventoryDTO record = this.mrtInventoryService.getMrtInventoryDTO(form.getRowId());
		
		// test if the record's channel cd can be updated by the user
		this.checkPermission(user, record, ParmType.SERVICE_TYPE, errors);
		this.checkPermission(user, record, ParmType.CHANNEL, errors);
		this.checkPermission(user, record, ParmType.GRADE, errors);
		if (errors.hasErrors()) {
			Map model = errors.getModel();
			model.putAll(referenceData(request));
			return new ModelAndView("mobccsmrtstockdetail", model);
		}
		
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsmrtstockdetail.html"));
		modelAndView.addObject("rowId", form.getRowId());
		modelAndView.addObject("msisdnUpdated", record.getMsisdn());
		
		// clear existing reserve status
		if (form.isOverride()) {
			if (logger.isInfoEnabled()) {
				logger.info("delete mrtStatus, msisdn: " + record.getMsisdn() + ", status: " + MsisdnStatus.RESERVE);
			}
			int count = this.mobCcsMrtStatusService.deleteMrtStatusDTOByMsisdnAndStatus(record.getMsisdn(), MsisdnStatus.RESERVE);
			if (logger.isDebugEnabled()) {
				logger.debug("count: " + count);
			}
			modelAndView.addObject("override", true);
			return modelAndView;
		}

		// update record info
		Date now = new Date();
		record.setMsisdnlvl(form.getMsisdnlvl());
		record.setMsisdnStatus(form.getMsisdnStatus());
		record.setChannelCd(form.getChannelCode());
		record.setLastUpdBy(user.getUsername());
		record.setLastUpdDate(now);
		this.mrtInventoryService.updateMrtInventory(record);
		
		modelAndView.addObject("recordUpdated", true);
		return modelAndView;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private void checkPermission(BomSalesUserDTO user, MrtInventoryDTO record, ParmType parmType, BindException errors) {
		String value = null;
		String field = null;
		switch (parmType) {
		case SERVICE_TYPE:
			value = record.getSrvNumType();
			field = "serviceType";
			break;
		case CHANNEL:
			value = record.getChannelCd();
			field = "channelCode";
			break;
		case GRADE:
			value = record.getMsisdnlvl();
			field = "msisdnlvl";
			break;
		default:
			return;
		}

		List<MaintParmLkupDTO> list = this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, parmType);
		boolean allowed = false;
		if (!this.isEmpty(list)) {
			for (MaintParmLkupDTO dto : list) {
				if (dto.getParmValue().equals(value)) {
					allowed = true;
					break;
				}
			}
		}
		if (!allowed) {
			errors.rejectValue(field, null, "Not allowed to edit " + parmType + ": " + value);
		}
	}
}
