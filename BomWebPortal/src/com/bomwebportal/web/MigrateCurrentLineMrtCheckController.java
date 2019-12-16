package com.bomwebportal.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MigrateCurrentLineMrtCheckDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BomCosOrderWsClient;
import com.bomwebportal.wsclient.BulkNewActClient;
import com.bomwebportal.wsclient.exception.WsClientException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pccw.bom.mob.schemas.CustomerResultDTO;
import bom.mob.schema.javabean.si.springboard.xsd.SubInfoDTO;
import net.sf.json.JSONArray;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class MigrateCurrentLineMrtCheckController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	private MultiSimInfoService multiSimInfoService;
	private BomCosOrderWsClient bomCosOrderWsClient;
	private OrderService orderService;
	private CodeLkupService codeLkupService;
	private BulkNewActClient bulkNewActClient;

	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}

	public BomCosOrderWsClient getBomCosOrderWsClient() {
		return bomCosOrderWsClient;
	}

	public void setBomCosOrderWsClient(BomCosOrderWsClient bomCosOrderWsClient) {
		this.bomCosOrderWsClient = bomCosOrderWsClient;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public BulkNewActClient getBulkNewActClient() {
		return bulkNewActClient;
	}

	public void setBulkNewActClient(BulkNewActClient bulkNewActClient) {
		this.bulkNewActClient = bulkNewActClient;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Boolean subActiveContract = false;
		String msisdn = this.getStringValue(request, "msisdn");
		JSONArray jsonArray = new JSONArray();
		SubInfoDTO subInfoDto = null;
		CustomerProfileDTO customerProfileDTO = null;
		SubscriberDTO subscriber = new SubscriberDTO();
		MigrateCurrentLineMrtCheckDTO result = new MigrateCurrentLineMrtCheckDTO();
		MnpDTO mnpDTO = (MnpDTO) request.getSession().getAttribute("MNP");
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String appMode = (String) request.getSession().getAttribute("appMode");

		logger.info("appMode = " + appMode);
		if ("shop".equals(appMode) || "directsales".equals(appMode)) {
			customerProfileDTO = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		} else {
			customerProfileDTO = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		}
		
		logger.info("msisdn = " + msisdn);
		if (StringUtils.isEmpty(msisdn)) {
			result.setWsResult("FAILED");
			result.setWsErrorMessage("Please input current mobile no.");
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			return new ModelAndView("ajax_csportal", "gson", gson.toJson(result));
		}
		
		if (StringUtils.isNotEmpty(msisdn)) {
			try {
				String serviceId = multiSimInfoService.getBomServiceId(msisdn);
				if (StringUtils.isNotEmpty(serviceId)) { 
					logger.info("bomCosOrderWsClient.getSubInfo(" + serviceId + ")");
					subInfoDto = bomCosOrderWsClient.getSubInfo(serviceId);
					result.setOneNumber(multiSimInfoService.checkOneNumber(serviceId));
				} else {
					logger.info("Service ID is empty");
				}
			} catch (WsClientException e) {
				result.setWsResult("FAILED");
				result.setWsErrorMessage("No record found.");
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				return new ModelAndView("ajax_csportal", "gson", gson.toJson(result));
			}
		}
		
		if (subInfoDto != null) {
			result.setCustLastName(subInfoDto.getCustLastName());
			result.setCustFirstName(subInfoDto.getCustFirstName());
			result.setIdDocType(subInfoDto.getIdDocType());
			result.setIdDocNum(subInfoDto.getIdDocNum());
			result.setBomCustNum(subInfoDto.getBomCustNum());
			result.setAcctNum(subInfoDto.getAcctNum());
			result.setMobCustNo(subInfoDto.getMobCustNum());
			result.setSubSimIccid(subInfoDto.getSubSimIccid());
			result.setSubSimItemCd(subInfoDto.getSubSimItemCd());
			result.setSubSimType(Utility.checkSimType(subInfoDto.getSubSimIccid()));
			result.setTitle(subInfoDto.getTitle());
			result.setSubSimDesc(multiSimInfoService.getItemCodeLkup(subInfoDto.getSubSimItemCd()));
			result.setSubBrand(subInfoDto.getSubBrand());
			
			if (subInfoDto.getSubActiveContract() != null) {
				try {
					logger.info("subInfoDto.getSubActiveContract() != null");
					result.setSubActiveContract(subInfoDto.getSubActiveContract().get(0));
					subActiveContract = true;
				} catch (Exception ex) {
					result.setSubActiveContract(null);
					subActiveContract = false;
				}
			}
			
			logger.info("customerProfileDTO.getBrand() = " + customerProfileDTO.getBrand());
			logger.info("result.getSubBrand() = " + result.getSubBrand());
			if (!customerProfileDTO.getBrand().equals(result.getSubBrand())) {
				result.setToo1BrmOrder("BRM");
				if (!customerProfileDTO.getSimType().equals(result.getSubSimType())) {
					if ("H".equals(result.getSubSimType())) {
						result.setMdoInd("M");
						result.setMnpOrder("Y");
					} else if (StringUtils.isNotEmpty(result.getMdoInd())) {
						if (StringUtils.isNotEmpty(result.getMdoInd())) {
							if ("Y".equals(result.getMdoInd())) {
								result.setMdoInd("D");
							} else {
								result.setMdoInd("O");
							}
						}
					} else {
						result.setMdoInd("O");
					}
				} else {
					result.setMdoInd("O");
				}
			} else {
				result.setToo1BrmOrder("TOO1");
			}
			
			logger.info("customerProfileDTO.getSimType() = " + customerProfileDTO.getSimType());
			logger.info("result.getSubSimType() = " + result.getSubSimType());
			if (!customerProfileDTO.getSimType().equals(result.getSubSimType())) {
				result.setMnpOrder("Y");
			} else {
				result.setMnpOrder("N");
			}

			result.setWsResult("PASS");
		}

		if (bomSalesUserDTO.getChannelId() != 2) {
			logger.info("Checking - Pending MRT in SB");
			String pendingOrderResult = orderService.getSbPendingOrderListString(msisdn, mnpDTO.getOrderId());
			if (pendingOrderResult != null && pendingOrderResult.length() > 0) {
				result.setWsResult("FAILED");
				result.setWsErrorMessage("Pending MRT in SB.");
			}

			logger.info("Checking - Same MRT with parent");
			if (StringUtils.isNotBlank(mnpDTO.getMsisdn()) && mnpDTO.getMsisdn().equals(msisdn)) {
				result.setWsResult("FAILED");
				result.setWsErrorMessage("Same MRT with parent.");
			}
		}

		logger.info("Checking - Service ID empty");
		if (StringUtils.isEmpty(multiSimInfoService.getBomServiceId(msisdn))) {
			result.setWsResult("FAILED");
			result.setWsErrorMessage("Service ID empty.");
		}

		logger.info("Checking - Profile error");
		if (subInfoDto == null) {
			result.setWsResult("FAILED");
			result.setWsErrorMessage("Profile error.");
		}
		
		if (bomSalesUserDTO.getChannelId() != 1) {
			logger.info("Checking - Only applies to the same ID number");
			if (!StringUtils.equals(customerProfileDTO.getBrand(), subInfoDto.getSubBrand())) {
				if (!subInfoDto.getIdDocNum().equalsIgnoreCase(customerProfileDTO.getIdDocNum())) {
					result.setWsResult("FAILED");
					result.setWsErrorMessage("Only applies to the same ID number.");
				}
			}
		}

		if (bomSalesUserDTO.getChannelId() == 2) {
			logger.info("Checking - CCS not allow TOO");
			if (!subInfoDto.getIdDocNum().equalsIgnoreCase(customerProfileDTO.getIdDocNum())) {
				result.setWsResult("FAILED");
				result.setWsErrorMessage("CCS not allow TOO.");
			}
		}

		int MUPS04_SIM_RP_REM_MONTH = 0;
		int MUPS04_HS_RP_REM_MONTH = 0;
		List<CodeLkupDTO> mups04SimRpRemMonthList = codeLkupService.findCodeLkupByType("MUPS04_SIM_RP_REM_MONTH");
		if (CollectionUtils.isNotEmpty(mups04SimRpRemMonthList)) {
			MUPS04_SIM_RP_REM_MONTH = Integer.parseInt(mups04SimRpRemMonthList.get(0).getCodeId());
		}
		List<CodeLkupDTO> mups04HsRpRemMonthList = codeLkupService.findCodeLkupByType("MUPS04_HS_RP_REM_MONTH");
		if (CollectionUtils.isNotEmpty(mups04HsRpRemMonthList)) {
			MUPS04_HS_RP_REM_MONTH = Integer.parseInt(mups04SimRpRemMonthList.get(0).getCodeId());
		}

		if (bomSalesUserDTO.getChannelId() != 2) {
			if (subActiveContract == true) {
				logger.info("Checking - Current contract end date");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date serviceRequestDate = sdf.parse(mnpDTO.getServiceReqDateStr());
				Date contractEndDate = sdf.parse(subInfoDto.getSubActiveContract().get(0).getEffEndDate());
				Date curLineRemContractPeriod = new Date();
				curLineRemContractPeriod = DateUtils.addMonths(serviceRequestDate, MUPS04_SIM_RP_REM_MONTH);
				if (curLineRemContractPeriod.before(contractEndDate) || curLineRemContractPeriod.equals(contractEndDate)) {
					result.setWsResult("FAILED");
					result.setWsErrorMessage("Current contract end date is " + subInfoDto.getSubActiveContract().get(0).getEffEndDate() + ". Migrate current line need remaining contract within " + MUPS04_HS_RP_REM_MONTH + " month(s)");
				}
			}
		}
		
		logger.info("Checking - Pending order in BOM");
		String pendingBomOrderResult = multiSimInfoService.getBomPendingOrderListString(multiSimInfoService.getBomServiceId(msisdn), Integer.toString(bomSalesUserDTO.getChannelId()));
		if (pendingBomOrderResult != null && pendingBomOrderResult.length() > 0) {
			if (bomSalesUserDTO.getChannelId() == 1) {
				result.setWsResult("FAILED");
				result.setWsErrorMessage(pendingBomOrderResult);
			} else if (bomSalesUserDTO.getChannelId() == 2) {
				result.setWsResult("FAILED");
				result.setWsErrorMessage(pendingBomOrderResult);
			}
		}

		try {
			logger.info("Checking - Credit Check");
			CustomerResultDTO customerResult = bulkNewActClient.checkCustomer(subInfoDto.getIdDocType(), subInfoDto.getIdDocNum());
			if (!"0".equals(customerResult.getErrCode())) {
				logger.warn(customerResult.getErrCode());
				result.setWsResult("FAILED");
				result.setWsErrorMessage("(ID Doc Type: " + subInfoDto.getIdDocType() + "/ID Doc No.: " + subInfoDto.getIdDocNum() + ") Credit Check Failed: " + customerResult.getErrMsg());
			}
			customerProfileDTO.setIsBomWsAvailable(true);
		} catch(Exception e) {
			customerProfileDTO.setIsBomWsAvailable(false);
			logger.error(e.getMessage(), e);
		}

		request.getSession().setAttribute("subInfo", subInfoDto);

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		return new ModelAndView("ajax_csportal", "gson", gson.toJson(result));
	}

	private String getStringValue(HttpServletRequest request, String name) {
		String value = "";
		try {
			if (StringUtils.isNotBlank((String) request.getParameter(name))) {
				value = new String(request.getParameter(name));
			}
		} catch (NumberFormatException nfe) {
		}
		return value;
	}
}
