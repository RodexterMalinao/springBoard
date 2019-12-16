package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomCustomerVerificationDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.dto.CustomerOrderHistoryDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.ims.dto.Ims1AMSInfoWithoutPendingDTO;
import com.bomwebportal.ims.service.Ims1AMSEnquiryService;
import com.bomwebportal.ims.service.PageTrackService;
import com.bomwebportal.service.CustomerInformationService;
import com.bomwebportal.service.LoggingService;
import com.bomwebportal.wsclient.CustProfileClient;
import com.bomwebportal.wsclient.CustomerSearchClient;
import com.google.gson.Gson;

public class CustomerInformationController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private PageTrackService pageTrackService;

	private CustomerInformationService customerInformationService;
	private CustomerSearchClient custSearchClient;	
	private LoggingService loggingService;
	private CustProfileClient custProfileClient;
	private String ntvDomain;
	private String bomDomain;

	private Ims1AMSEnquiryService ims1AMSEnquiryService;

	public CustomerInformationService getCustomerInformationService() {
		return customerInformationService;
	}

	public void setCustomerInformationService(
			CustomerInformationService customerInformationService) {
		this.customerInformationService = customerInformationService;
	}

	public CustomerSearchClient getCustSearchClient() {
		return custSearchClient;
	}

	public void setCustSearchClient(CustomerSearchClient custSearchClient) {
		this.custSearchClient = custSearchClient;
	}

	public LoggingService getLoggingService() {
		return loggingService;
	}

	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	public CustProfileClient getCustProfileClient() {
		return custProfileClient;
	}

	public void setCustProfileClient(CustProfileClient custProfileClient) {
		this.custProfileClient = custProfileClient;
	}
	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}

	public String getNtvDomain() {
		return ntvDomain;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		//IMS Direct Sales
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			this.setFormView("imsdscustomerinformation"); 
		} else {
			this.setFormView("customerinformation");
		}
		//IMS Direct Sales (end)
		request.setAttribute("ntvDomain",ntvDomain);		//Ims Celia 20150720
		request.setAttribute("bomDomain",bomDomain);
		if (logger.isDebugEnabled()) {
			logger.debug("CustomerInformationController formBackingObject called");
		}
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		request.setAttribute("isCC", user.getChannelId()==2||user.getChannelId()==3?"Y":"N");
		HttpSession session = request.getSession();
		
		// remove selected info in cust select page
		session.removeAttribute("selectedCustInfoSession");
		session.removeAttribute("selectedServiceLineSession");
		session.removeAttribute("customerInfoQuota");
		
		CustomerInformationDTO sessionCustInfo = (CustomerInformationDTO) session.getAttribute("customerInformationDTOSession");
		
		
		if (sessionCustInfo == null){
			// new, for no session saved
			CustomerInformationDTO custInfo = new CustomerInformationDTO();
			if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) custInfo.setIsDS(true);
			session.setAttribute("customerInformationDTOSession", custInfo);
			return custInfo;
		} else {
			// use session
			if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) sessionCustInfo.setIsDS(true);
			String idDocNum2 = StringUtils.trim(sessionCustInfo.getIdDocNum());
			
			String idDocType2 = StringUtils.trim(sessionCustInfo.getIdDocType());
			
			String serviceNumber2 = StringUtils.trim(sessionCustInfo.getServiceNumber());
			
			String serviceNumberType2 = StringUtils.trim(sessionCustInfo.getServiceNumberType());

			String loginIdPrefix2 = StringUtils.trim(sessionCustInfo.getLoginIdPrefix());
			
			String loginIdSuffix2 = StringUtils.trim(sessionCustInfo.getLoginIdSuffix());
			
			if (StringUtils.isNotBlank(idDocType2)
					|| StringUtils.isNotBlank(serviceNumberType2)
					|| StringUtils.isNotBlank(loginIdSuffix2)) {
				
				CustomerInformationDTO returnCustInfo = new CustomerInformationDTO();
				session.setAttribute("customerInformationDTOSession", returnCustInfo);
				
				returnCustInfo.setIsDS(sessionCustInfo.getIsDS());
				
				SearchingKeyDTO searchKeyDto = new SearchingKeyDTO(); 
				
				if (!("NONE".equalsIgnoreCase(idDocType2) || idDocType2 == null)) {
					idDocNum2 = idDocNum2.toUpperCase();
				}
				
				returnCustInfo.setIdDocNum(idDocNum2);
				searchKeyDto.setIdDocNum(idDocNum2);
				
				returnCustInfo.setIdDocType(idDocType2);
				if ("Passport".equals(idDocType2)) {
					searchKeyDto.setIdDocType("PASS");		
				} else if ("BR".equals(idDocType2)) {
					searchKeyDto.setIdDocType("BS");
				} else if ("Certificate No".equals(idDocType2)) {
					searchKeyDto.setIdDocType("CERT");
				} else if ("HKID".equals(idDocType2)) {
					searchKeyDto.setIdDocType("HKID");
				}
				
				// for LTS, padding zeros when service number is not in 12-digit
				if ("TEL".equals(serviceNumberType2)) {
					serviceNumber2 = StringUtils.leftPad(serviceNumber2, 12, '0');
				}
				returnCustInfo.setServiceNumber(serviceNumber2);
				searchKeyDto.setServiceNum(serviceNumber2);
				
				returnCustInfo.setServiceNumberType(serviceNumberType2);

				if ("TEL".equals(serviceNumberType2)) {
					searchKeyDto.setServiceType("TEL");
				} else if ("MRT".equals(serviceNumberType2)) {
					searchKeyDto.setServiceType("3G");
				} 
				
				returnCustInfo.setLoginIdPrefix(loginIdPrefix2);
				searchKeyDto.setLoginId(loginIdPrefix2);
				
				returnCustInfo.setLoginIdSuffix(loginIdSuffix2);
				if("@netvigator.com".equals(loginIdSuffix2)){
					searchKeyDto.setDomainType("N");
				}else if("@HKStar.com".equalsIgnoreCase(loginIdSuffix2)){
					searchKeyDto.setDomainType("S");					
				}
				
				if (!(("".equals(loginIdPrefix2)) && ("NONE".equals(loginIdSuffix2)))) {
					searchKeyDto.setSystemId("IMS");
					searchKeyDto.setIdDocNum(null);
					searchKeyDto.setIdDocType(null);
					searchKeyDto.setServiceNum(null);
					searchKeyDto.setServiceType(null);
				} else if (!("".equals(serviceNumber2)) && ("TEL".equals(serviceNumberType2))) {
					searchKeyDto.setSystemId("DRG");
					searchKeyDto.setIdDocNum(null);
					searchKeyDto.setIdDocType(null);
					searchKeyDto.setLoginId(null);
					searchKeyDto.setDomainType(null);
				} else if (!("".equals(serviceNumber2)) && ("MRT".equals(serviceNumberType2))) {
					searchKeyDto.setSystemId("MOB");
					searchKeyDto.setIdDocNum(null);
					searchKeyDto.setIdDocType(null);
					searchKeyDto.setLoginId(null);
					searchKeyDto.setDomainType(null);
				} 
					
				// search results - basic customer info & service in use
				List<CustomerBasicInfoDTO> custSearchResultList = Collections.emptyList();
				String errorSeverity = null;
				
				if (StringUtils.isNotBlank(searchKeyDto.getIdDocNum())
						|| StringUtils.isNotBlank(searchKeyDto.getServiceNum())
						|| StringUtils.isNotBlank(searchKeyDto.getLoginId())) {
					try {
						/********************* START CALLING API ******************/
						if (logger.isDebugEnabled()) {
							logger.debug("Start Cust Search Client API call");
						}
						
						// whole result, including error severity & cust basic info
						CustomerSearchResponse response = custSearchClient.getCustomerSearchResponse(searchKeyDto);
						if (logger.isDebugEnabled()) {
							logger.debug("Cust Search - Response: " + response);
						}
						
						// basic info array, including service line info
						custSearchResultList = this.getCustomerBasicInfoDTOList(response.getCustomerBasicInfoDTO());
						//logger.debug("Cust Search - Customer Basic Info DTO Array: " + tempResult);
						
						// error severity
						// 0: Success
						// 1: Over 30 service lines
						// 2: Application Exception
						// 3: System Exception
						errorSeverity = response.getErrorSeverity();
						if (logger.isDebugEnabled()) {
							logger.debug("Cust Search - Error Severity: " + errorSeverity);
						}

						if ("0".equals(errorSeverity)){
							BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)session.getAttribute("bomsalesuser");
							if (logger.isDebugEnabled()) {
								logger.debug("CDAL Logging Service: " + bomsalesuser.getUsername() + " : " + searchKeyDto);
							}
							loggingService.logSearchByCriteria(bomsalesuser.getUsername(), searchKeyDto);
						}
						if (logger.isDebugEnabled()) {
							logger.debug("End Cust Search Client API call");
						}
					} catch (WebServiceException e) {
						if (logger.isWarnEnabled()) {
							logger.warn("Cust Search Fail: " + e);
						}
					}
				}
				
				session.setAttribute("custSearchResultListSession", custSearchResultList);//save session

				List<ServiceLineDTO> serviceLineDTOList = new ArrayList<ServiceLineDTO>();
				
				for (CustomerBasicInfoDTO customerBasicInfoDTO : custSearchResultList) {
					for (ServiceLineDTO serviceLineDTO : customerBasicInfoDTO.getServiceLineDTO()) {
						serviceLineDTOList.add(serviceLineDTO);
					}
				}
				
				// bs & cert indicator
				String resultBRind="";
				String resultCertind="";
				
				// display result null with "-"
				for (CustomerBasicInfoDTO dto : custSearchResultList) {
					
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
				
				session.setAttribute("custServInUseListSession", serviceLineDTOList);//save session

				// search results - order history
				List<CustomerOrderHistoryDTO> custOrderHistoryList = null;

				if (StringUtils.isNotBlank(returnCustInfo.getIdDocNum())
						|| StringUtils.isNotBlank(returnCustInfo.getServiceNumber())
						|| StringUtils.isNotBlank(returnCustInfo.getLoginIdPrefix())) {
					custOrderHistoryList = customerInformationService.getCustomerOrderHistoryDTOALL_retail_only(
								returnCustInfo.getIdDocNum(), returnCustInfo.getServiceNumber(),
								returnCustInfo.getIdDocType(), returnCustInfo.getLoginIdPrefix(),
								returnCustInfo.getServiceNumberType());
				}
				session.setAttribute("custOrderHistoryListSession", custOrderHistoryList);//save session

				returnCustInfo.setCustOrderHistoryList(custOrderHistoryList);
				returnCustInfo.setActionType(sessionCustInfo.getActionType());
				returnCustInfo.setCustomerInformationError(sessionCustInfo.getCustomerInformationError());
				returnCustInfo.setSelectedCust(sessionCustInfo.getSelectedCust()); 	
				returnCustInfo.setSelectedNum(sessionCustInfo.getSelectedNum());
				if ("TEL".equals(serviceNumberType2)) {
					returnCustInfo.setSelectedNum(StringUtils.right(serviceNumber2, 8));
				}
				
				logger.info("return returnCustInfo : " + new Gson().toJson(returnCustInfo)); 
				/// for direct sales Jacky 20141009R
				if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
					boolean mismatch = false;
					String conmanyName = StringUtils.trim(sessionCustInfo.getCompanyName());
					String familyName = StringUtils.trim(sessionCustInfo.getFamilyName());
					String givenName = StringUtils.trim(sessionCustInfo.getGivenName());
					if (!StringUtils.isEmpty(returnCustInfo.getIdDocType()) && ("HKID".equalsIgnoreCase(returnCustInfo.getIdDocType().trim()) || "Passport".equalsIgnoreCase(returnCustInfo.getIdDocType().trim()))) { 
						returnCustInfo.setGivenName(givenName);
						returnCustInfo.setFamilyName(familyName);
					} else if (!StringUtils.isEmpty(returnCustInfo.getIdDocType()) && "BR".equalsIgnoreCase(returnCustInfo.getIdDocType().trim())) {
						returnCustInfo.setCompanyName(conmanyName);
					}
					
					if(custSearchResultList !=null){
						List<CustomerBasicInfoDTO> tmpList = custSearchResultList;
						List<CustomerBasicInfoDTO> tmpList2 = new ArrayList<CustomerBasicInfoDTO>();
						for(CustomerBasicInfoDTO c: custSearchResultList) {						
							if (!StringUtils.isEmpty(returnCustInfo.getIdDocType()) && ("HKID".equalsIgnoreCase(returnCustInfo.getIdDocType().trim()) || "Passport".equalsIgnoreCase(returnCustInfo.getIdDocType().trim()))) {
								if(
										(StringUtils.isEmpty(familyName) || StringUtils.equals(familyName, StringUtils.trim(c.getLastName()))) &&
										(StringUtils.isEmpty(givenName) || StringUtils.equals(givenName, StringUtils.trim(c.getFirstName()))) 
								) tmpList2.add(c);
							}else if (!StringUtils.isEmpty(returnCustInfo.getIdDocType()) && "BR".equalsIgnoreCase(returnCustInfo.getIdDocType().trim())) { 
								if(
										(StringUtils.isEmpty(conmanyName) || StringUtils.equals(conmanyName, StringUtils.trim(c.getCompanyName())))
								) tmpList2.add(c);
							}
						}
						if(!tmpList.isEmpty() &&tmpList2.isEmpty())
							mismatch =true;					
						
						if(mismatch) tmpList = new ArrayList<CustomerBasicInfoDTO>();	//remove all if mismatch
						returnCustInfo.setCustSearchResultList(tmpList);
						session.setAttribute("custSearchResultListSession", tmpList);//save session
						if(tmpList.size()==0) {
							returnCustInfo.setServiceLineDTOList(null);
							session.removeAttribute("custServInUseListSession");
						}
						session.setAttribute("custProfileMismatch", mismatch);//save session
					}

					if (returnCustInfo.getServiceLineDTOList()!=null) {

						returnCustInfo.setServiceLine1amsList(new ArrayList<Ims1AMSInfoWithoutPendingDTO>());
					
						for (ServiceLineDTO line : returnCustInfo.getServiceLineDTOList()) {
							returnCustInfo.getServiceLine1amsList().add(ims1AMSEnquiryService.getIms1AMSInfoWithoutPending(line.getServiceId()));
						}
					}
				}
				/// for direct sales Jacky 20141009(end)
				
				/*if (!"CLEAR".equalsIgnoreCase(sessionCustInfo.getActionType())
						&& !"CREATELTS".equalsIgnoreCase(sessionCustInfo.getActionType())
						&& !"DSCREATELTS".equalsIgnoreCase(sessionCustInfo.getActionType())
						&& !"CREATE".equalsIgnoreCase(sessionCustInfo.getActionType())
					) {
//					if("".equals(sessionCustInfo.getIdDocType())
//						||"".equals(sessionCustInfo.getIdDocNum())
//						||"".equals(sessionCustInfo.getFamilyName())
//						||"".equals(sessionCustInfo.getGivenName())
//						){
//						
//					}else{
						try{
							logger.debug("call customerInformationService");
							customerInformationService.doPageTrackCustSearch(request.getRemoteAddr(), user.getUsername(), "customerinformation", "Customer search", sessionCustInfo);
						}catch (Exception e){
							logger.debug("call pageTrackService");
							pageTrackService.doPageTrackCustSearch(request.getRemoteAddr(), user.getUsername(), "customerinformation", "Customer search", sessionCustInfo);
						}						
//					}
				}*/
				
				return returnCustInfo;
			}
		}
		return sessionCustInfo;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		if (logger.isDebugEnabled()) {
			logger.debug("CustomerInformationController is called!");
		}
		
		CustomerInformationDTO dto = (CustomerInformationDTO) command;
		HttpSession session = request.getSession();
		session.setAttribute("customerInformationDTOSession", dto);// save session
		
		if ("CLEAR".equalsIgnoreCase(dto.getActionType())) {
			
	    	session.removeAttribute("custSearchResultListSession");
	    	session.removeAttribute("custServInUseListSession");
	    	session.removeAttribute("custOrderHistoryListSession");
	    	session.removeAttribute("customerInformationDTOSession");
	    	
		} else if ("CREATELTS".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("ltsupgradeeyeorder.html"));
		} else if ("DSCREATELTS".equalsIgnoreCase(dto.getActionType())) {
			BomCustomerVerificationDTO verifyResult = customerInformationService.getBomCustomerVerificationResult(LtsConstant.DOC_TYPE.get(dto.getIdDocType())
					, dto.getIdDocNum(), dto.getGivenName(), dto.getFamilyName());
			dto.setBomVerifiedCustomerResult(verifyResult);
			session.setAttribute("customerInformationDTOSession", dto);// save session
			if(user.getChannelId()==13)			//NOWTVSALES 20151007
				return new ModelAndView(new RedirectView(bomDomain+"ltsorder.html?action=create&type=I&locale=en"));	
			return new ModelAndView(new RedirectView("ltsorder.html?action=create&type=I&locale=en"));	
		} else if ("CREATE".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("custselect.html"));
		}

		return new ModelAndView(new RedirectView("customerinformation.html"));// this is
	
	}

	protected Map<?, ?> referenceData(HttpServletRequest request) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("ReferenceData called");
		}

		HttpSession session = request.getSession();
		Map<String, Object> referenceData = new HashMap<String, Object>();

		// id doc type
		List<String> idDocTypeList = new ArrayList<String>();
		idDocTypeList.add("HKID");
		idDocTypeList.add("Passport");
		idDocTypeList.add("BR");
		//IMS Direct Sales
		if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			idDocTypeList.add("Certificate No");
		}

		referenceData.put("idDocTypeList", idDocTypeList);

		// service number
		List<String> serviceNumberTypeList = new ArrayList<String>();
		serviceNumberTypeList.add("MRT");
		serviceNumberTypeList.add("TEL");

		referenceData.put("serviceNumberTypeList", serviceNumberTypeList);

		// login id suffix
		List<String> loginIdSuffixList = new ArrayList<String>();
		loginIdSuffixList.add("@netvigator.com");
		int type = ((BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser")).getChannelId();
		if ( type==2||type==3)
			loginIdSuffixList.add("@hkstar.com");
		else
			loginIdSuffixList.add("@HKStar.com");

		referenceData.put("loginIdSuffixList", loginIdSuffixList);
		
		CustomerInformationDTO dto = (CustomerInformationDTO)session.getAttribute("customerInformationDTOSession");
		
		if (dto != null) {
			referenceData.put("actionType", dto.getActionType());
			referenceData.put("custSearchResultList", dto.getCustSearchResultList());
			
			// for display concatenation of address only, not in use
			if (dto.getServiceLineDTOList() != null) {
				for (ServiceLineDTO serviceLinDto : dto.getServiceLineDTOList()) {
					if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){		//jacky 20141203
						serviceLinDto.setValidationRulesID(LookupTableBean.getInstance().getImsDistrictLookupMap().get(serviceLinDto.getDistrNum()));
					} else {
						String fullAddress = getSingleLineAddress(serviceLinDto);
						serviceLinDto.setValidationRulesID(fullAddress);
					}
				}
			}
			
			referenceData.put("serviceLineDTOList", dto.getServiceLineDTOList());
			referenceData.put("custOrderHistoryList", dto.getCustOrderHistoryList());
			referenceData.put("errorSeverity", dto.getErrorSeverity());
			referenceData.put("serviceLine1amsList", dto.getServiceLine1amsList()); 
		}
		
		return referenceData;
	}
	
	private String getSingleLineAddress(ServiceLineDTO dto) {
		
		String floor = dto.getFloorNum();
		String flat = dto.getUnitNum();
		String buildingName = dto.getBuildName();
		String lotNum = dto.getHlotNum();
		String streetNum = dto.getStreetNum();
		String streetName = dto.getStreetName();
		String streetCatgDesc = customerInformationService.getStreetCatgDescFromStCatgCd(dto.getStCatgCd());
		String sectionDesc = customerInformationService.getSectDescFromSectCd(dto.getSectCd());
		String districtDesc = customerInformationService.getDistDscFromDistrNum(dto.getDistrNum());
		String areaDesc = customerInformationService.getAreaDescFromDistrNum(dto.getDistrNum());
		
		StringBuffer sb = new StringBuffer();

		if (flat != null && !"".equals(flat.trim()) && !"null".equalsIgnoreCase(flat)) {
			sb.append("Flat ");
			sb.append(flat);
			sb.append(" ");
		}
		
		if (floor != null && !"".equals(floor.trim()) && !"null".equalsIgnoreCase(floor)) {
			sb.append(floor);
			sb.append("/F ");
		}
		
		if (buildingName != null && !"".equals(buildingName.trim()) && !"null".equalsIgnoreCase(buildingName)) {
			sb.append(buildingName);
			sb.append(" ");
		}

		if (lotNum != null && !"".equals(lotNum.trim()) && !"null".equalsIgnoreCase(lotNum)) {
			sb.append(lotNum);
			sb.append(" ");
		} else if (streetNum != null
				&& !"".equals(streetNum.trim())
				&& !"null".equalsIgnoreCase(streetNum)) {
			sb.append(streetNum);
			sb.append(" ");
		}

		if (streetName != null && !"".equals(streetName.trim()) && !"null".equalsIgnoreCase(streetName)) {
			sb.append(streetName);
			sb.append(" ");
			sb.append(streetCatgDesc);
			sb.append(" ");
		}

		if (sectionDesc != null && !"".equals(sectionDesc.trim()) && !"null".equalsIgnoreCase(sectionDesc)) {
			sb.append(sectionDesc);
			sb.append(" ");
		}

		if (districtDesc != null && !"".equals(districtDesc.trim()) && !"null".equalsIgnoreCase(districtDesc)) {
			sb.append(districtDesc);
			sb.append(" ");
		}

		if (areaDesc != null && !"".equals(areaDesc.trim()) && !"null".equalsIgnoreCase(areaDesc)) {
			sb.append(areaDesc);
			sb.append(" ");
		}

		if ("".equals(sb.toString().trim())) {
			sb.append("--");
		}
		
		return sb.toString();
	}
	
	private List<CustomerBasicInfoDTO> getCustomerBasicInfoDTOList(CustomerBasicInfoDTO[] customerBasicInfoDTOs) {
		if (customerBasicInfoDTOs == null || customerBasicInfoDTOs.length == 0) {
			return Collections.emptyList();
		}
		
		List<CustomerBasicInfoDTO> customerBasicInfoDTOList = new ArrayList<CustomerBasicInfoDTO>();
		for (CustomerBasicInfoDTO customerBasicInfoDTO : customerBasicInfoDTOs) {
			CustomerPremierInfoDTO customerPremierInfoDTO = new CustomerPremierInfoDTO();
			BeanUtils.copyProperties(customerBasicInfoDTO, customerPremierInfoDTO);
			try {
				// call remote service
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
	
	//IMS Direct Sales ONLY
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
		if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) return;
		 
		CustomerInformationDTO dto = (CustomerInformationDTO) command;
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
					
		logger.debug("dto.getActionType(): " + dto.getActionType());
		
        String requestIp = request.getRemoteAddr();
        
        logger.debug("request.getRemoteAddr(): " + requestIp);
		logger.debug("user.getUsername(): " + user.getUsername());
		        
        if(request.getHeader("X-Forwarded-For")!=null&&!"".equals(request.getHeader("X-Forwarded-For"))){
            requestIp=request.getHeader("X-Forwarded-For");
            logger.info("use  X-Forwarded-For: "+ requestIp );
        }  
		
		if (!"CLEAR".equalsIgnoreCase(dto.getActionType())
				&& !"CREATELTS".equalsIgnoreCase(dto.getActionType())
				&& !"DSCREATELTS".equalsIgnoreCase(dto.getActionType())
				&& !"CREATE".equalsIgnoreCase(dto.getActionType())
			) {
				try{
					logger.debug("call customerInformationService");
					customerInformationService.doPageTrackCustSearch(requestIp, user.getUsername(), "customerinformation", "Customer search", dto);
				}catch (Exception e){
					logger.debug("call pageTrackService");
					pageTrackService.doPageTrackCustSearch(requestIp, user.getUsername(), "customerinformation", "Customer search", dto);
				}						
		}
		
		if (("HKID".equalsIgnoreCase(dto.getIdDocType().trim()) || "Passport".equalsIgnoreCase(dto.getIdDocType().trim())) && !StringUtils.isEmpty(dto.getIdDocNum().trim())) { 
			if(StringUtils.isEmpty(dto.getFamilyName().trim())) {
				errors.rejectValue("familyName", "","Please input the Family Name.");	
			}
			if(StringUtils.isEmpty(dto.getGivenName().trim())) {
				errors.rejectValue("givenName", "","Please input the Given Name.");	
			}
		}  else if ("BR".equalsIgnoreCase(dto.getIdDocType().trim()) && !StringUtils.isEmpty(dto.getIdDocNum().trim())) {
			if(StringUtils.isEmpty(dto.getCompanyName().trim())) {
				errors.rejectValue("givenName", "","Please input the Company Name."); 	
			}
		} 
		
		//IMS Direct Sales
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			this.setFormView("imsdscustomerinformation"); 
			request.setAttribute("ntvDomain",ntvDomain);		//Ims Celia 20150720
		} else {
			this.setFormView("customerinformation");
		}
		//IMS Direct Sales (end)
	}

	public void setIms1AMSEnquiryService(Ims1AMSEnquiryService ims1AMSEnquiryService) {
		this.ims1AMSEnquiryService = ims1AMSEnquiryService;
	}

	public Ims1AMSEnquiryService getIms1AMSEnquiryService() {
		return ims1AMSEnquiryService;
	}

	public void setBomDomain(String bomDomain) {
		this.bomDomain = bomDomain;
	}

	public String getBomDomain() {
		return bomDomain;
	}

	public void setPageTrackService(PageTrackService pageTrackService) {
		this.pageTrackService = pageTrackService;
	}

	public PageTrackService getPageTrackService() {
		return pageTrackService;
	}
}
