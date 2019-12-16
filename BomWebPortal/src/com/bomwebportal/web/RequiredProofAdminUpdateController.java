package com.bomwebportal.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.OrdDocDTO.VerifyInd;
import com.bomwebportal.service.OrdDocService;

public class RequiredProofAdminUpdateController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());
	public static enum Action {
		VERIFY
		;
	}
	
	private OrdDocService ordDocService;

	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Action action = Action.valueOf(ServletRequestUtils.getRequiredStringParameter(request, "action"));
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String username = bomsalesuser.getUsername();
		
		Result result = null;
		switch (action) {
		case VERIFY:
			String docType = ServletRequestUtils.getRequiredStringParameter(request, "docType");
			int seqNum = ServletRequestUtils.getRequiredIntParameter(request, "seqNum");
			VerifyInd verifyInd = VerifyInd.valueOf(ServletRequestUtils.getRequiredStringParameter(request, "verifyInd"));
			
			int updatedCount = this.ordDocService.updateVerifyInd(orderId, docType, seqNum, verifyInd, username);
			OrdDocDTO ordDocDto = this.ordDocService.getOrdDoc(orderId, docType, seqNum);
			result = new VerifyResult();
			((VerifyResult) result).setUpdatedCount(updatedCount);
			((VerifyResult) result).setVerifyInd(ordDocDto.getVerifyInd());
			((VerifyResult) result).setVerifyBy(username);
			((VerifyResult) result).setVerifyDate(ordDocDto.getVerifyDate());
			break;
		}

		return new ModelAndView("ajax_adminupdate", "jsonArray", JSONArray.fromObject(result));
	}
	
	public class VerifyResult extends Result {
		public VerifyInd getVerifyInd() {
			return verifyInd;
		}
		public void setVerifyInd(VerifyInd verifyInd) {
			this.verifyInd = verifyInd;
		}
		public String getVerifyBy() {
			return verifyBy;
		}
		public void setVerifyBy(String verifyBy) {
			this.verifyBy = verifyBy;
		}
		public Date getVerifyDate() {
			return verifyDate;
		}
		public void setVerifyDate(Date verifyDate) {
			this.verifyDate = verifyDate;
		}
		public int getUpdatedCount() {
			return updatedCount;
		}
		public void setUpdatedCount(int updatedCount) {
			this.updatedCount = updatedCount;
		}
		private VerifyInd verifyInd;
		private String verifyBy;
		private Date verifyDate;
		private int updatedCount;
	}
	
	abstract class Result {/*
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		private Status status;*/
	}
}
