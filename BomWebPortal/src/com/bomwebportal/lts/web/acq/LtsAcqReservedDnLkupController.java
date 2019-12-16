package com.bomwebportal.lts.web.acq;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.lts.dao.LtsOrderSearchDAO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.LtsOrderSearchDTO;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.wsClientLts.BomWsBackendClient;
import com.pccw.custProfileGateway.serviceProf.DNAssignmentResultDTO;
import com.pccw.custProfileGateway.serviceProf.DnAssignReqResponse;

public class LtsAcqReservedDnLkupController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
		
	private BomWsBackendClient bomWsBackendClient;
	private LtsProfileService ltsProfileService;
	private LtsOrderSearchDAO ltsOrderSearchDAO;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = new JSONObject();
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);		
		boolean isDnChange = BooleanUtils.toBoolean(request.getParameter("isdnchange"));
		if(!isDnChange){
			if (acqOrderCapture == null) {
				jsonResult.put("status", "false");
				jsonResult.put("errorMsg", "Session timeout, please re-issue order again.");
				return new ModelAndView("ajax_ltsacqreserveddnlkup", jsonResult); 
			}
		}
		
		
		String srvNum = request.getParameter("srvNum");
		String acctCd = request.getParameter("acctCd");
		String boc = request.getParameter("boc");
		String projectCd = request.getParameter("projectCd");
		String sbOrderId = request.getParameter("sbOrderId");
		String specialSrvGrp = "";
		String specialSrvName = "";
		
		if (StringUtils.isBlank(srvNum) || StringUtils.isBlank(acctCd) 
				|| StringUtils.isBlank(boc) || StringUtils.isBlank(projectCd)) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", "Missing Service Number / Account Code / BOC / Project Code");
			return new ModelAndView("ajax_ltsacqreserveddnlkup", jsonResult);
		}
		
		List<LtsOrderSearchDTO> sbOrders = ltsOrderSearchDAO.searchLtsCcOrder(
				null, null, null, StringUtils.leftPad(srvNum,12,"0"), null, null, null, null, null, null, null, null, null, null, null, null); 
		
		if (!sbOrders.isEmpty() && sbOrders.size() > 0) {
			for (LtsOrderSearchDTO sbOrder : sbOrders) {				
				if (!StringUtils.equals(sbOrder.getOrderId(), sbOrderId) &&
						(StringUtils.equals(sbOrder.getStatus(), LtsConstant.ORDER_STATUS_SUBMITTED)
						|| StringUtils.equals(sbOrder.getStatus(), LtsConstant.ORDER_STATUS_SUSPENDED)
						|| StringUtils.equals(sbOrder.getStatus(), LtsConstant.ORDER_STATUS_CREATE_BOM)
						|| StringUtils.equals(sbOrder.getStatus(), LtsConstant.ORDER_STATUS_PENDING_BOM)
						|| StringUtils.equals(sbOrder.getStatus(), LtsConstant.ORDER_STATUS_EXTRACTED)
						|| StringUtils.equals(sbOrder.getStatus(), LtsConstant.ORDER_STATUS_FORCED_WQ)
						|| StringUtils.equals(sbOrder.getStatus(), LtsConstant.ORDER_STATUS_PENDING_SIGNOFF))) {
					jsonResult.put("status", "false");
					jsonResult.put("errorMsg", "The inputted DN " + srvNum + " is used by SB Oder " 
							+ sbOrder.getOrderId() + ", please check the DN");
					return new ModelAndView("ajax_ltsacqreserveddnlkup", jsonResult);
				}
			}
		}		
		
	    String serviceInventSts = ltsProfileService.getServiceInventoryStatus(srvNum, LtsConstant.SERVICE_TYPE_TEL);
		if (!StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, serviceInventSts) ) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", "The inputted DN " + srvNum + " is not " 
					+ LtsConstant.DRG_DN_RESERVED + ", please input target DN again");
			return new ModelAndView("ajax_ltsacqreserveddnlkup", jsonResult);
	    }		
		
		List<DNAssignmentResultDTO> itemList = bomWsBackendClient.getDNAssignment(srvNum, acctCd, boc, projectCd, specialSrvGrp, specialSrvName);
		if (itemList!=null && itemList.size()>0) {
			DNAssignmentResultDTO dnAssignmentResultDTO = (DNAssignmentResultDTO)itemList.get(0);
			if (!StringUtils.equals(LtsBackendConstant.WS_RESPONSE_SUCCESS_CODE, dnAssignmentResultDTO.getErrSeverity())) {
				if (StringUtils.equals(LtsBackendConstant.WS_ERROR_MESSAGE_DN_DEDICATED, dnAssignmentResultDTO.getErrDesc())) {
					specialSrvGrp = LtsBackendConstant.DEFAULT_SPECIAL_SERVICE_GROUP;
					specialSrvName = LtsBackendConstant.DEFAULT_SPECIAL_SERVICE_NAME;
					itemList = bomWsBackendClient.getDNAssignment(srvNum, acctCd, boc, projectCd, specialSrvGrp, specialSrvName);
					if (itemList!=null && itemList.size()>0) {
						dnAssignmentResultDTO = (DNAssignmentResultDTO)itemList.get(0);
					}
				}
			}			
			if (StringUtils.equals(LtsBackendConstant.WS_RESPONSE_SUCCESS_CODE, dnAssignmentResultDTO.getErrSeverity())) {
				DnAssignReqResponse result = bomWsBackendClient.dnAssignReq(srvNum, LtsBackendConstant.DRG_DN_RESERVED_STATUS, 
						LtsBackendConstant.DEFAULT_WS_ASSIGN_DN_RELEASE, LtsBackendConstant.DEFAULT_WS_ASSIGN_DN_GATEWAY_NUM);
				if (StringUtils.equals(LtsBackendConstant.WS_RESPONSE_SUCCESS_CODE, result.getDnAssignReqResult().getDrgRtnSts().getErrorSeverity())) {
					jsonResult.put("status", "true");
				} else {
					jsonResult.put("status", "false");
					jsonResult.put("errorMsg", "DN " + srvNum + " - " + result.getDnAssignReqResult().getDrgRtnSts().getErrorDesc());
				}
			} else {
				jsonResult.put("status", "false");
				jsonResult.put("errorMsg", "DN " + srvNum + " - " + dnAssignmentResultDTO.getErrDesc());
			}
		} else {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", "Error in ajax_ltsacqreserveddnlkup");
		}
		
		return new ModelAndView("ajax_ltsacqreserveddnlkup", jsonResult);
	}

	/**
	 * @return the bomWsBackendClient
	 */
	public BomWsBackendClient getBomWsBackendClient() {
		return bomWsBackendClient;
	}

	/**
	 * @param bomWsBackendClient the bomWsBackendClient to set
	 */
	public void setBomWsBackendClient(BomWsBackendClient bomWsBackendClient) {
		this.bomWsBackendClient = bomWsBackendClient;
	}

	/**
	 * @return the ltsProfileService
	 */
	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	/**
	 * @param ltsProfileService the ltsProfileService to set
	 */
	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}

	/**
	 * @return the ltsOrderSearchDAO
	 */
	public LtsOrderSearchDAO getLtsOrderSearchDAO() {
		return ltsOrderSearchDAO;
	}

	/**
	 * @param ltsOrderSearchDAO the ltsOrderSearchDAO to set
	 */
	public void setLtsOrderSearchDAO(LtsOrderSearchDAO ltsOrderSearchDAO) {
		this.ltsOrderSearchDAO = ltsOrderSearchDAO;
	}
	
}
