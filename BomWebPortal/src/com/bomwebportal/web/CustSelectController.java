package com.bomwebportal.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.ServiceLineDTO;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.CustomerInformationService;

public class CustSelectController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustomerInformationService customerInformationService;
	
	public CustomerInformationService getCustomerInformationService() {
		return customerInformationService;
	}

	public void setCustomerInformationService(
			CustomerInformationService customerInformationService) {
		this.customerInformationService = customerInformationService;
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		logger.info("CustSelectController formBackingObject called");
		
		request.getSession().removeAttribute("selectedCustInfoSession");
		request.getSession().removeAttribute("selectedServiceLineSession");
		request.getSession().removeAttribute("customerInfoQuota");
		
		CustomerInformationDTO sessionCustInfo = (CustomerInformationDTO) request.getSession().getAttribute("customerInformationDTOSession");
		
		if (sessionCustInfo == null){
			
			// new, for no session saved
			CustomerInformationDTO custInfo = new CustomerInformationDTO();
			request.getSession().setAttribute("customerInformationDTOSession", custInfo);
			return custInfo;
			
		} else {
			
			List<CustomerBasicInfoDTO> custSearchResultList 
				= (List<CustomerBasicInfoDTO>) request.getSession().getAttribute("custSearchResultListSession");
			
			List<ServiceLineDTO> serviceLineDTOList 
				= (List<ServiceLineDTO>) request.getSession().getAttribute("custServInUseListSession");
			
			if (custSearchResultList == null) {
				return sessionCustInfo;
			} else {
				for(int i = 0 ; i < custSearchResultList.size(); i++ ){
					if ("DRG".equalsIgnoreCase(custSearchResultList.get(i).getSystemId())) {
						custSearchResultList.get(i).setSystemId("LTS");
					}
				}
			}
			
			if (serviceLineDTOList != null) {
				for(int i = 0 ; i < serviceLineDTOList.size(); i++ ){
					if ("DRG".equalsIgnoreCase(serviceLineDTOList.get(i).getSystemId())) {
						serviceLineDTOList.get(i).setSystemId("LTS");
					}
				}
			}
		
			for (int i=0; i<custSearchResultList.size(); i++) {
				
				if (custSearchResultList.get(i).getSystemId() == null
						|| "".equals(custSearchResultList.get(i).getSystemId().trim())) {
					custSearchResultList.get(i).setSystemId("--");
				}
				
				if (custSearchResultList.get(i).getTitle() == null
						|| "".equals(custSearchResultList.get(i).getTitle().trim())) {
					custSearchResultList.get(i).setTitle("--");
				}
				
				if (custSearchResultList.get(i).getFirstName() == null
						|| "".equals(custSearchResultList.get(i).getFirstName().trim())) {
					custSearchResultList.get(i).setFirstName("--");
				}
				
				if (custSearchResultList.get(i).getLastName() == null
						|| "".equals(custSearchResultList.get(i).getLastName().trim())) {
					custSearchResultList.get(i).setLastName("--");
				}
				
				if (custSearchResultList.get(i).getCompanyName() == null
						|| "".equals(custSearchResultList.get(i).getCompanyName().trim())) {
					custSearchResultList.get(i).setCompanyName("--");
				}
				
				if (custSearchResultList.get(i).getBlackListInd() == null
						|| "".equals(custSearchResultList.get(i).getBlackListInd().trim())) {
					custSearchResultList.get(i).setBlackListInd("--");
				}
				
				if (custSearchResultList.get(i).getOverdueInd() == null
						|| "".equals(custSearchResultList.get(i).getOverdueInd().trim())) {
					custSearchResultList.get(i).setOverdueInd("--");
				}
				
				if (custSearchResultList.get(i).getIdVerifyInd() == null
						|| "".equals(custSearchResultList.get(i).getIdVerifyInd().trim())) {
					custSearchResultList.get(i).setIdVerifyInd("--");
				}
				
				if (custSearchResultList.get(i).getIdDocNum() == null
						|| "".equals(custSearchResultList.get(i).getIdDocNum().trim())) {
					custSearchResultList.get(i).setIdDocNum("--");
				}
				
				if (custSearchResultList.get(i).getIdDocType() == null
						|| "".equals(custSearchResultList.get(i).getIdDocType().trim())) {
					custSearchResultList.get(i).setIdDocType("--");
				}
			}
			
			if (!(custSearchResultList == null || custSearchResultList.size() == 0)) {
				for (int i=0; i<custSearchResultList.size(); i++) {
					if ("MOB".equalsIgnoreCase(custSearchResultList.get(i).getSystemId())) {
						sessionCustInfo.setMobInd("true");
						break;
					}
				}
			}
			
			if (!(serviceLineDTOList == null || serviceLineDTOList.size() == 0)) {
				for (int i=0; i<serviceLineDTOList.size(); i++) {
					if ("MOB".equalsIgnoreCase(serviceLineDTOList.get(i).getSystemId())) {
						sessionCustInfo.setSrvLnMobInd("true");
						break;
					}
				}
			}

			request.setAttribute("mobInd", sessionCustInfo.getMobInd());
			request.setAttribute("srvLnMobInd", sessionCustInfo.getSrvLnMobInd());
			sessionCustInfo.setCustSearchResultList(custSearchResultList);
			sessionCustInfo.setServiceLineDTOList(serviceLineDTOList);
			return sessionCustInfo;
		}
	}

	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		request.getSession().removeAttribute("customerInfoQuota");
		request.getSession().removeAttribute("custSearchException");
		
		CustomerInformationDTO dto = (CustomerInformationDTO) command;
		
		if ("CONTINUE".equalsIgnoreCase(dto.getActionType())) {

			// if not select, clear session
			if (dto.getSelectedCust() == null || dto.getCustSearchResultList().size() <= 0) {
				request.getSession().removeAttribute("selectedCustInfoSession");
			}	
			if (dto.getSelectedNum() == null || dto.getServiceLineDTOList().size() <= 0) {
				request.getSession().removeAttribute("selectedServiceLineSession");
			}
			
			if (dto.getSelectedCust() != null && dto.getCustSearchResultList().size() > 0) {
				CustomerBasicInfoDTO selectedCustInfo 
					= dto.getCustSearchResultList().get(Integer.parseInt(dto.getSelectedCust()));
				
				request.getSession().setAttribute("selectedCustInfoSession", selectedCustInfo);
			}	
			if (dto.getSelectedNum() != null && dto.getServiceLineDTOList().size() > 0) {
				ServiceLineDTO selectedServiceLine
					= dto.getServiceLineDTOList().get(Integer.parseInt(dto.getSelectedNum()));
				
				request.getSession().setAttribute("selectedServiceLineSession", selectedServiceLine);
			}
		}
		
		return new ModelAndView(new RedirectView("customerinformationquota.html"));
	}
	
	public Map referenceData(HttpServletRequest request) {
		
		logger.info("ReferenceData called");
		
		Map referenceData = new HashMap<String, List<String>>();

		CustomerInformationDTO dto = (CustomerInformationDTO)request.getSession().getAttribute("customerInformationDTOSession");
			
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
				referenceData.put("selectedCust", dto.getSelectedCust());
				referenceData.put("selectedNum", dto.getSelectedNum());
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
