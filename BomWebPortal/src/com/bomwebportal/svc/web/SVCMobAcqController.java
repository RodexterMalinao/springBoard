package com.bomwebportal.svc.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustTagDTO;
import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.CustomerSearchResponse;
import org.openuri.www.SearchingKeyDTO;
import org.openuri.www.ServiceLineDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.service.CustomerInformationService;
import com.bomwebportal.service.LoggingService;
import com.bomwebportal.wsclient.CustProfileClient;
import com.bomwebportal.wsclient.CustomerSearchClient;

@Controller
public class SVCMobAcqController {
	
	protected final Log logger = LogFactory.getLog(getClass());

	
	@Autowired
	private CustomerInformationService customerInformationService;
	@Autowired
	private CustomerSearchClient custSearchClient;	
	@Autowired
	private LoggingService loggingService;
	@Autowired
	private CustProfileClient custProfileClient;

	
	
	@RequestMapping(value="/svcmobacq", params={"idDocType", "idDocNum"})
	public String svcMobAcq(
			@RequestParam String idDocType,
			@RequestParam String idDocNum,
			@RequestParam(required=false) String lob,
			@RequestParam(required=false) String serviceNum,
			HttpSession session) throws Exception {
		
		
		logger.debug("MOB ACQ from SVC : idDocType="+idDocType+",idDocNum="+idDocNum+", lob="+lob+", serviceNum="+serviceNum);
		
		cleanUpSession(session);
		if (StringUtils.isEmpty(idDocType) || StringUtils.isEmpty(idDocNum)) {
			//throw new UserTimeoutException("Cannot create order");
			logger.debug("MOB ACQ from SVC - without ID num");
			return "redirect:/customerprofile.html";
		}
		
		CustomerInformationDTO returnCustInfo = new CustomerInformationDTO();
		session.setAttribute("customerInformationDTOSession", returnCustInfo);
		
		idDocNum = idDocNum.toUpperCase();
		returnCustInfo.setIdDocNum(idDocNum);
		returnCustInfo.setIdDocType(idDocType);
		returnCustInfo.setLoginIdPrefix("");
		returnCustInfo.setServiceNumber("");
		// idDocType = PASS, BS, CERT, HKID
		SearchingKeyDTO searchKeyDto = new SearchingKeyDTO();
		searchKeyDto.setIdDocNum(idDocNum);
		searchKeyDto.setIdDocType(idDocType);
		searchKeyDto.setLoginId("");
		searchKeyDto.setServiceNum("");
		
		
		List<CustomerBasicInfoDTO> custSearchResultList = Collections.emptyList();
		String errorSeverity = null;
		
		try {
			CustomerSearchResponse response = custSearchClient.getCustomerSearchResponse(searchKeyDto);
			logger.debug("Cust Search - Response: " + response);
			
			custSearchResultList = this.getCustomerBasicInfoDTOList(response.getCustomerBasicInfoDTO());
			// error severity
			// 0: Success
			// 1: Over 30 service lines
			// 2: Application Exception
			// 3: System Exception
			errorSeverity = response.getErrorSeverity();
			logger.debug("Cust Search - Error Severity: " + errorSeverity);
			
			if ("0".equals(errorSeverity)){
				BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)session.getAttribute("bomsalesuser");
				logger.debug("CDAL Logging Service: " + bomsalesuser.getUsername() + " : " + searchKeyDto);
				loggingService.logSearchByCriteria(bomsalesuser.getUsername(), searchKeyDto);
			}
			
			logger.debug("End Cust Search Client API call");
		} catch (WebServiceException e) {
				logger.error("Cust Search Fail: " + e);
				session.setAttribute("custSearchException", e);
		}
		
		
		session.setAttribute("custSearchResultListSession", custSearchResultList);
		
		
		List<ServiceLineDTO> serviceLineDTOList = new ArrayList<ServiceLineDTO>();
		
		for (CustomerBasicInfoDTO customerBasicInfoDTO : custSearchResultList) {
			for (ServiceLineDTO serviceLineDTO : customerBasicInfoDTO.getServiceLineDTO()) {
				serviceLineDTOList.add(serviceLineDTO);
			}
		}
		
		
		String resultBRind="";
		String resultCertind="";
		
		for (CustomerBasicInfoDTO dto : custSearchResultList) {
			if ("DRG".equalsIgnoreCase(dto.getSystemId())) {
				dto.setSystemId("LTS");
			}
			if ("BS".equals(dto.getIdDocType()) || "CERT".equals(dto.getIdDocType())) {
				resultBRind="Y";
			}
			
			if("CERT".equals(dto.getIdDocType())){
				resultCertind="Y";
			}
			
			if (StringUtils.isBlank(dto.getSystemId())) {
				dto.setSystemId("--");
			}

			if (StringUtils.isBlank(dto.getTitle())) {
				dto.setTitle("--");
			}
			
			if (StringUtils.isBlank(dto.getFirstName())) {
				dto.setFirstName("--");
			}
			
			if (StringUtils.isBlank(dto.getLastName())) {
				dto.setLastName("--");
			}
			
			if (StringUtils.isBlank(dto.getCompanyName())) {
				dto.setCompanyName("--");
			}
			
			if (StringUtils.isBlank(dto.getBlackListInd())) {
				dto.setBlackListInd("--");
			}
			
			if (StringUtils.isBlank(dto.getOverdueInd())) {
				dto.setOverdueInd("--");
			}
			
			if (StringUtils.isBlank(dto.getIdVerifyInd())) {
				dto.setIdVerifyInd("--");
			}
			
			if (StringUtils.isBlank(dto.getIdDocNum())) {
				dto.setIdDocNum("--");
			}
			
			if (StringUtils.isBlank(dto.getIdDocType())) {
				dto.setIdDocType("--");
			}
		}
		
		returnCustInfo.setImsLtsButtonInd(resultBRind);
		returnCustInfo.setLtsButtonInd(resultCertind);
		
		for (ServiceLineDTO dto : serviceLineDTOList) {
			if (StringUtils.isBlank(dto.getSystemId())) {
				dto.setSystemId("--");
			}
			
			if ("DRG".equalsIgnoreCase(dto.getSystemId())) {
				dto.setSystemId("LTS");
			}
			
			if (StringUtils.isBlank(dto.getServiceNum())) {
				dto.setServiceNum("--");
			} else {
				if ("DRG".equalsIgnoreCase(dto.getSystemId())) {
					dto.setServiceNum(dto.getServiceNum().replaceAll("^0+", ""));
				}
			}
			
			if (StringUtils.isBlank(dto.getServiceStartDate())) {
				dto.setServiceStartDate("--");
			}
			
			if (StringUtils.isBlank(dto.getServiceEndDate())) {
				dto.setServiceEndDate("--");
			}

			if (StringUtils.isBlank(dto.getEyeGroupNum())) {
				dto.setEyeGroupNum("--");
			}
			
			if (StringUtils.isBlank(dto.getEyeGroupType())) {
				dto.setEyeGroupType("--");
			}

		}
		returnCustInfo.setCustSearchResultList(custSearchResultList);
		returnCustInfo.setServiceLineDTOList(serviceLineDTOList);
		returnCustInfo.setErrorSeverity(errorSeverity);
		
		session.setAttribute("custServInUseListSession", serviceLineDTOList);
		session.setAttribute("customerInformationDTOSession", returnCustInfo);
		
		boolean hasMobCust = false;
		if (!(custSearchResultList == null || custSearchResultList.size() == 0)) {
			for (int i=0; i<custSearchResultList.size(); i++) {
				if ("MOB".equalsIgnoreCase(custSearchResultList.get(i).getSystemId())) {
					returnCustInfo.setMobInd("true");
					hasMobCust = true;
					break;
				}
			}
		}
		
		boolean hasMobSrvLn = false;
		if (!(serviceLineDTOList == null || serviceLineDTOList.size() == 0)) {
			for (int i=0; i<serviceLineDTOList.size(); i++) {
				if ("MOB".equalsIgnoreCase(serviceLineDTOList.get(i).getSystemId())) {
					returnCustInfo.setSrvLnMobInd("true");
					hasMobSrvLn = true;
					break;
				}
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////
		
		if (StringUtils.isNotEmpty(lob)) {
			for (int i=0; i < custSearchResultList.size(); i++) {
				CustomerBasicInfoDTO bi = custSearchResultList.get(i);
				if ("MOB".equals(bi.getSystemId()) || !hasMobCust) {
					if (bi.getSystemId().equals(lob)) {
						returnCustInfo.setSelectedCust(""+i);
						break;
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(serviceNum)) {
			for (int i=0; i < serviceLineDTOList.size(); i++) {
				ServiceLineDTO sl = serviceLineDTOList.get(i);
				if ("MOB".equals(sl.getSystemId()) || !hasMobSrvLn) {
					if (sl.getServiceNum().equals(serviceNum)) {
						returnCustInfo.setSelectedNum(""+i);
						break;
					}
				}
			}
		}
		return "redirect:/custselect.html";
	}
	
	
	@RequestMapping("/svcmobacq")
	public String svcMobAcq(HttpSession session) {
		
		logger.debug("MOB ACQ from SVC - without ID num");
		cleanUpSession(session);
		return "redirect:/customerprofile.html";
	}
	

	private void cleanUpSession(HttpSession session) {

		/*
		session.removeAttribute("customer");
		session.removeAttribute("payment");
		session.removeAttribute("MNP");
		session.removeAttribute("MobileSimInfo");
		session.removeAttribute("orderIdSession");
		session.removeAttribute("customerTierSession");
		session.removeAttribute("baskettypeSession");
		session.removeAttribute("rptypeSession");
		session.removeAttribute("selectedVasItemList");
		session.removeAttribute("componentList");
		// add eliot 20110824
		session.removeAttribute("signoffDtoSession");
		session.removeAttribute("custSearchResultListSession");
		session.removeAttribute("custServInUseListSession");
		session.removeAttribute("custOrderHistoryListSession");
		session.removeAttribute("customerInformationDTOSession");
		session.removeAttribute("sessionCcsOrderHash");
		session.removeAttribute("selectedCustInfoSession");
		session.removeAttribute("selectedServiceLineSession");
		session.removeAttribute("appDate");
		// add by Margaret 20120111
		session.removeAttribute("customerInfoQuota");
		// added by James 20120209
		session.removeAttribute("basketSession");
		session.removeAttribute("orderType");
		session.removeAttribute("supportDoc");
		
		session.removeAttribute("customerSignSession");
		session.removeAttribute("bankSignSession");
		session.removeAttribute("mnpSignSession");
		session.removeAttribute("conciergeSignSession");
		
		session.removeAttribute("sessionOrderList");
		session.removeAttribute("sbuid");
		session.removeAttribute("iGuardSignSession");
		session.removeAttribute("approvedSrvLineExceed");
		session.removeAttribute("tdoSignSession"); //20130709
		*/
		
		
		//////////////////////////////////////////////
		
		session.removeAttribute("selectedCustInfoSession");
		session.removeAttribute("selectedServiceLineSession");
		session.removeAttribute("customerInfoQuota");
		
		
		for (Enumeration<String> e = session.getAttributeNames(); e.hasMoreElements();) {
			String attrib = new String(e.nextElement());
		
			if (!"bomsalesuser".equals(attrib) 
					&& !"appMode".equals(attrib)
					&& !"channelCd".equals(attrib) 
					&& !"authorizationInfo".equals(attrib) 
					&& !"ssoAuthContext".equals(attrib)) {	
				logger.info("DELETE SessionAttribute: " + attrib);
				session.removeAttribute(attrib);
				
			}else{
				
				logger.info("KEEP SessionAttribute: " + attrib);
			}
		}
	}
	
	
	
	
	private List<CustomerBasicInfoDTO> getCustomerBasicInfoDTOList(CustomerBasicInfoDTO[] customerBasicInfoDTOs) {
		if (customerBasicInfoDTOs == null || customerBasicInfoDTOs.length == 0) {
			return Collections.emptyList();
		}
		
		List<CustomerBasicInfoDTO> customerBasicInfoDTOList = new ArrayList<CustomerBasicInfoDTO>();
		for (CustomerBasicInfoDTO customerBasicInfoDTO : customerBasicInfoDTOs) {
			
			// patch the "title" to "Title"
			String title = customerBasicInfoDTO.getTitle();
			if (StringUtils.isNotEmpty(title)) {
				title = StringUtils.capitalize(title.toLowerCase());
				customerBasicInfoDTO.setTitle(title);
			}
			
			CustomerPremierInfoDTO customerPremierInfoDTO = new CustomerPremierInfoDTO();
			BeanUtils.copyProperties(customerBasicInfoDTO, customerPremierInfoDTO);
			try {
				// call remote serivce
				CustTagDTO custTagDTO = null; 
				if (StringUtils.isNotBlank(customerBasicInfoDTO.getIdDocNum())) {
					CustProfileClient.SystemId systemId = CustProfileClient.SystemId.valueOf(customerBasicInfoDTO.getSystemId());
					CustProfileClient.IdDocType idDocType = CustProfileClient.IdDocType.valueOf(customerBasicInfoDTO.getIdDocType());
					custTagDTO = this.custProfileClient.getCustomerTagByIdDoc(systemId, idDocType, customerBasicInfoDTO.getIdDocNum());
				}
				customerPremierInfoDTO.setCustTagDTO(custTagDTO);
			} catch (Exception e) {
				logger.warn("Exception in call remote service", e);
			}
			customerBasicInfoDTOList.add(customerPremierInfoDTO);
		}
		return customerBasicInfoDTOList;
	}
	
	public class CustomerPremierInfoDTO extends CustomerBasicInfoDTO {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private CustTagDTO custTagDTO;

		public CustTagDTO getCustTagDTO() {
			return custTagDTO;
		}

		public void setCustTagDTO(CustTagDTO custTagDTO) {
			this.custTagDTO = custTagDTO;
		}
	}
	
}
