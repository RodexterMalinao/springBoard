package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
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
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.GoldenMrtAdminDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTAdminDetailUI;
import com.bomwebportal.mob.ccs.service.GoldenMrtAdminService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;

public class MobCcsMRTAdminDetailController extends SimpleFormController {
    protected final Log logger = LogFactory.getLog(getClass());
    protected GoldenMrtAdminService goldenMrtAdminService;
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

	public GoldenMrtAdminService getGoldenMrtAdminService() {
		return goldenMrtAdminService;
	}

	public void setGoldenMrtAdminService(GoldenMrtAdminService goldenMrtAdminService) {
		this.goldenMrtAdminService = goldenMrtAdminService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		MRTAdminDetailUI form = new MRTAdminDetailUI();
		String rowId = request.getParameter("requestRowId");
		if (StringUtils.isNotBlank(rowId)) {
			GoldenMrtAdminDTO detail = this.goldenMrtAdminService.getGoldenMrtAdminDTO(rowId);
			if (detail != null) {
				form.setMsisdn(detail.getMsisdn());
				form.setMsisdnlvl(detail.getMsisdnlvl());
				form.setReserveId(detail.getReserveId());
			}
		}
		return form;
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		String rowId = request.getParameter("requestRowId");
		GoldenMrtAdminDTO detail = this.goldenMrtAdminService.getGoldenMrtAdminDTO(rowId);
		referenceData.put("detail", detail);
		
		List<String> msisdnlvlList = mobCcsMrtService.getGoldenNumLvL(false);
		referenceData.put("msisdnlvlList", msisdnlvlList);
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		MRTAdminDetailUI form = (MRTAdminDetailUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		boolean reserveSuccess = false;
		boolean approveSuccess = false;
		GoldenMrtAdminDTO detail = this.goldenMrtAdminService.getGoldenMrtAdminDTO(form.getRowId());
		Date now = new Date();

		if (detail != null) {
			if ("01".equals(detail.getRequestStatus())) {
				// admin
				detail.setMsisdn(form.getMsisdn());
				detail.setMsisdnlvl(form.getMsisdnlvl());
				detail.setReserveId(form.getReserveId());
				detail.setRequestStatus("02");
				detail.setLastUpdDate(now);
				detail.setLastUpdBy(user.getUsername());
				this.goldenMrtAdminService.updateGoldenMrtAdminForAdmin(detail);
				reserveSuccess = true;
			} else if ("02".equals(detail.getRequestStatus())) {
				// manager
				detail.setRequestStatus("03");
				detail.setApprovedDate(now);
				detail.setApprovedBy(user.getUsername());
				detail.setLastUpdDate(now);
				detail.setLastUpdBy(user.getUsername());
				this.goldenMrtAdminService.updateGoldenMrtAdminForManager(detail);
				approveSuccess = true;
			}
		}
		RedirectView redirect = new RedirectView("mobccsmrtadmindetail.html");
		ModelAndView modelAndView = new ModelAndView(redirect);
		modelAndView.addObject("requestRowId", form.getRowId());
		if (reserveSuccess) {
			modelAndView.addObject("reserveSuccess", reserveSuccess);
		}
		if (approveSuccess) {
			modelAndView.addObject("approveSuccess", approveSuccess);
		}
		return modelAndView;
	}
}
