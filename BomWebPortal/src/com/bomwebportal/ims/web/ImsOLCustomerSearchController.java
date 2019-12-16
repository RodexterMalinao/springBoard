package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.SearchingKeyDTO;
import org.openuri.www.ServiceLineDTO;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.dto.ImsCustomerInformationDTO;
import com.bomwebportal.ims.dto.ImsCustomerOrderHistoryDTO;
import com.bomwebportal.ims.service.ImsOLOrderSearchService;
import com.bomwebportal.service.CustomerInformationService;
import com.bomwebportal.service.LoggingService;
import com.bomwebportal.wsclient.CustomerSearchClient;

public class ImsOLCustomerSearchController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	private CustomerInformationService customerInformationService;
	
	private ImsOLOrderSearchService imsOlOrderSearchService;
	
	public ImsOLOrderSearchService getImsOlOrderSearchService() {
		return imsOlOrderSearchService;
	}

	public void setImsOlOrderSearchService(
			ImsOLOrderSearchService imsOlOrderSearchService) {
		this.imsOlOrderSearchService = imsOlOrderSearchService;
	}

	public CustomerInformationService getCustomerInformationService() {
		return customerInformationService;
	}

	public void setCustomerInformationService(
			CustomerInformationService customerInformationService) {
		this.customerInformationService = customerInformationService;
	}
	
	private CustomerSearchClient custSearchClient;	

	public CustomerSearchClient getCustSearchClient() {
		return custSearchClient;
	}

	public void setCustSearchClient(CustomerSearchClient custSearchClient) {
		this.custSearchClient = custSearchClient;
	}

	private LoggingService loggingService;
	
	public LoggingService getLoggingService() {
		return loggingService;
	}

	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("CustomerInformationController formBackingObject called");
		
//		// remove selected info in cust select page
//		request.getSession().removeAttribute("selectedCustInfoSession");
//		request.getSession().removeAttribute("selectedServiceLineSession");
//		request.getSession().removeAttribute("customerInfoQuota");
		
		ImsCustomerInformationDTO sessionCustInfo = (ImsCustomerInformationDTO) request.getSession().getAttribute("imscustomerInformationDTOSession");
		
		if (sessionCustInfo == null){
			// new, for no session saved
			ImsCustomerInformationDTO custInfo = new ImsCustomerInformationDTO();
			custInfo.setOrderType("PCD"); //default order type 
			request.getSession().setAttribute("imscustomerInformationDTOSession", custInfo);
			return custInfo;
		} else {
			// use session
			// modified by Joyce 20120608
			String idDocNum2 = null;
			if (sessionCustInfo.getIdDocNum() != null) {
				idDocNum2 = sessionCustInfo.getIdDocNum().trim();
			}
			
			String idDocType2 = null;
			if (sessionCustInfo.getIdDocType() != null) {
				idDocType2 = sessionCustInfo.getIdDocType().trim();
			}
			
			String serviceNumber2 = null;
			if (sessionCustInfo.getServiceNumber() != null) {
				serviceNumber2 = sessionCustInfo.getServiceNumber().trim();
			}
			
			String serviceNumberType2 = null;
			if (sessionCustInfo.getServiceNumberType() != null) {
				serviceNumberType2 = sessionCustInfo.getServiceNumberType().trim();
			}
			
			String loginIdPrefix2 = null;
			if (sessionCustInfo.getLoginIdPrefix() != null) {
				loginIdPrefix2 = sessionCustInfo.getLoginIdPrefix().trim();
			}
			
			String loginIdSuffix2 = null;
			if (sessionCustInfo.getLoginIdSuffix() != null) {
				loginIdSuffix2 = sessionCustInfo.getLoginIdSuffix().trim();
			}
			
			String emailAddress2 = null;
			if (sessionCustInfo.getEmailAddress() != null) {
				emailAddress2 = sessionCustInfo.getEmailAddress().trim();
			}
			
			String orderType = null;
			if(sessionCustInfo.getOrderType()!=null){
				orderType = sessionCustInfo.getOrderType();
			}
			
			if ((idDocNum2 != null && idDocType2 != null) 
					|| (serviceNumber2 != null && serviceNumberType2 != null)
					|| (loginIdPrefix2 != null && loginIdSuffix2 != null) || emailAddress2 != null) {
				
				ImsCustomerInformationDTO returnCustInfo = new ImsCustomerInformationDTO();
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
				// modified by Joyce 20111107
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
				}else if("@HKStar.com".equals(loginIdSuffix2)){
					searchKeyDto.setDomainType("S");					
				}
				
				// add by Joyce 20111107
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
				
				returnCustInfo.setEmailAddress(emailAddress2);
				returnCustInfo.setOrderType(orderType);
				
				// search results - order history
				List<ImsCustomerOrderHistoryDTO> custOrderHistoryList = null;

				custOrderHistoryList = 
					imsOlOrderSearchService.getCustomerOrderHistoryDTOALL(
							returnCustInfo.getIdDocNum(), returnCustInfo.getServiceNumber(),
							returnCustInfo.getIdDocType(), returnCustInfo.getLoginIdPrefix(),
							returnCustInfo.getServiceNumberType(), returnCustInfo.getEmailAddress(), null,returnCustInfo.getOrderType(), null );
				request.getSession().setAttribute("imscustOrderHistoryListSession", custOrderHistoryList);//save session

				returnCustInfo.setCustOrderHistoryList(custOrderHistoryList);
				returnCustInfo.setActionType(sessionCustInfo.getActionType());
				returnCustInfo.setCustomerInformationError(sessionCustInfo.getCustomerInformationError());
				returnCustInfo.setSelectedCust(sessionCustInfo.getSelectedCust());
				returnCustInfo.setSelectedNum(sessionCustInfo.getSelectedNum());
				request.getSession().setAttribute("imscustomerInformationDTOSession", returnCustInfo);
				
				return returnCustInfo;
			}
		}
		return sessionCustInfo;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("CustomerInformationController is called!");
		
		ImsCustomerInformationDTO dto = (ImsCustomerInformationDTO) command;
		request.getSession().setAttribute("imscustomerInformationDTOSession", dto);// save session
		
		
		logger.info("order type: " + dto.getOrderType());
		logger.info("getActionType: " +dto.getActionType());
		
		//add joyce 20111110
		if ("CLEAR".equalsIgnoreCase(dto.getActionType())) {
			
	    	request.getSession().removeAttribute("custSearchResultListSession");
	    	request.getSession().removeAttribute("custServInUseListSession");
	    	request.getSession().removeAttribute("imscustOrderHistoryListSession");
	    	request.getSession().removeAttribute("imscustomerInformationDTOSession");
	    	
		} else if ("CREATELTS".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("ltsupgradeeyeorder.html"));
		} else if ("CREATE".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("custselect.html"));
		}

		return new ModelAndView(new RedirectView("imsolcustomersearch.html"));// this is
	
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("ReferenceData called");

		Map referenceData = new HashMap<String, List<String>>();

		// id doc type
		List<String> idDocTypeList = new ArrayList<String>();
		idDocTypeList.add("HKID");
		idDocTypeList.add("Passport");
		idDocTypeList.add("BR");
		idDocTypeList.add("Certificate No");

		referenceData.put("idDocTypeList", idDocTypeList);

		// service number
		List<String> serviceNumberTypeList = new ArrayList<String>();
	//	serviceNumberTypeList.add("MRT");
	//	serviceNumberTypeList.add("TEL");
		serviceNumberTypeList.add("FSA");

		referenceData.put("serviceNumberTypeList", serviceNumberTypeList);

		// login id suffix
		List<String> loginIdSuffixList = new ArrayList<String>();
		loginIdSuffixList.add("@netvigator.com");
		//loginIdSuffixList.add("@HKStar.com");

		referenceData.put("loginIdSuffixList", loginIdSuffixList);
		
		ImsCustomerInformationDTO dto = (ImsCustomerInformationDTO)request.getSession().getAttribute("imscustomerInformationDTOSession");
		
		if (dto != null) {
			referenceData.put("actionType", dto.getActionType());
			referenceData.put("custSearchResultList", dto.getCustSearchResultList());
			
			// for display concatenation of address only, not in use
			if (dto.getServiceLineDTOList() != null) {
				for (int i=0; i<dto.getServiceLineDTOList().size(); i++) {
					String fullAddress = 
						getSingleLineAddress(dto.getServiceLineDTOList().get(i));
					
					dto.getServiceLineDTOList().get(i).setValidationRulesID(fullAddress);
				}
			}
			
			referenceData.put("serviceLineDTOList", dto.getServiceLineDTOList());
			referenceData.put("custOrderHistoryList", dto.getCustOrderHistoryList());
			referenceData.put("errorSeverity", dto.getErrorSeverity());
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

}
