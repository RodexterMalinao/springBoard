/**
 * 
 */
package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.dto.ui.DsDedicatedStaffUI;
import com.bomwebportal.ims.service.ImsDsDedicatedStaffService;

public class DsDedicatedStaffController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	private ImsDsDedicatedStaffService imsDsDedicatedStaffService;

	public ImsDsDedicatedStaffService getImsDsDedicatedStaffService() {
		return imsDsDedicatedStaffService;
	}

	public void setImsDsDedicatedStaffService(ImsDsDedicatedStaffService imsDsDedicatedStaffService) {
		this.imsDsDedicatedStaffService = imsDsDedicatedStaffService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		logger.debug("formBackingObject");

		DsDedicatedStaffUI model = (DsDedicatedStaffUI) request.getSession().getAttribute("DsDedicatedStaffPage");

		if (model == null) {
			model = new DsDedicatedStaffUI();
			logger.debug("model is null");
			try {
				List<DsDedicatedStaffUI> dsDelicatedStaffList = new ArrayList<DsDedicatedStaffUI>();
				dsDelicatedStaffList = imsDsDedicatedStaffService.getDsDedicatedStaff();
				model.setStaffList(dsDelicatedStaffList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors)
			throws Exception {
		Boolean allStaffExist = true;
		Boolean duplicateStaff = false;
		String invalidStaffId = "";

		DsDedicatedStaffUI dsDedicatedStaffUI = (DsDedicatedStaffUI) command;
		logger.debug("onBindAndValidate: " + dsDedicatedStaffUI.getStaffListName() + " "
				+ dsDedicatedStaffUI.getStaffListInput());

		if (dsDedicatedStaffUI.getStaffListInput().trim().length() > 0) {
			List<String> staffIds = Arrays.asList(dsDedicatedStaffUI.getStaffListInput().split(","));

			// Check if staff id is exist
			for (String staffId : staffIds) {
				// check whether staff Id exist or not
				if (!imsDsDedicatedStaffService.isStaffExit(staffId.trim())) {
					allStaffExist = false;
					invalidStaffId += staffId.trim() + " ";
				}
			}
			if (!allStaffExist)
				errors.rejectValue("staffListInput", "", invalidStaffId + "do not exist.");

			// Check duplicate staff Id
			for (int i = 0; i < staffIds.size(); i++) {
				for (int k = i + 1; k < staffIds.size(); k++) {
					if (staffIds.get(i).trim().equals(staffIds.get(k).trim())) {
						errors.rejectValue("staffListInput", "", staffIds.get(i) + " duplicated.");
						duplicateStaff = true;
					}
				}
			}
		}

		List<DsDedicatedStaffUI> staffList = dsDedicatedStaffUI.getStaffList();
		List<DsDedicatedStaffUI> newStaffList = new ArrayList<DsDedicatedStaffUI>();
		for (DsDedicatedStaffUI temp : staffList) {
			if (temp.getStaffListName().equals(dsDedicatedStaffUI.getStaffListName()))
				temp.setStaffListInput(dsDedicatedStaffUI.getStaffListInput());
			newStaffList.add(temp);
		}
		dsDedicatedStaffUI.setStaffList(newStaffList);

		if (!allStaffExist || duplicateStaff) {
			request.getSession().setAttribute("DsDedicatedStaffPage", dsDedicatedStaffUI);
		} else {
			request.getSession().setAttribute("DsDedicatedStaffPage", null);
		}
	}

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		logger.debug("onSubmit");

		DsDedicatedStaffUI dsDedicatedStaffUI = (DsDedicatedStaffUI) command;

		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		dsDedicatedStaffUI.setCreateBy(bomSalesUserDTO.getUsername());

		if (dsDedicatedStaffUI.getStaffListInput().trim().length() > 0) {
			List<String> staffIds = Arrays.asList(dsDedicatedStaffUI.getStaffListInput().split(","));
			dsDedicatedStaffUI.setStaffIdList(staffIds);
		}
		imsDsDedicatedStaffService.updateDsDedicatedStaff(dsDedicatedStaffUI);

		return new ModelAndView(new RedirectView("dsdedicatedstaff.html"));
	}
}
