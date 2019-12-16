package com.bomwebportal.ims.web;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openuri.www.CustTagDTO;
import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.CustomerSearchResponse;
import org.openuri.www.SearchingKeyDTO;
import org.openuri.www.ServiceLineDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.MailSendException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.Ims1AMSFSAInfoDTO;
import com.bomwebportal.ims.dto.OrderImsSystemFindingDTO;
import com.bomwebportal.ims.service.Ims1AMSEnquiryService;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.ims.service.ImsOrderDetailService;

import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.wsclient.CustProfileClient;
import com.bomwebportal.wsclient.CustomerSearchClient;



public class ImsSysFAssignmentController  implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	private static boolean isProcessing;
	private CustProfileClient custProfileClient;
	
	 public CustProfileClient getCustProfileClient() {
			return custProfileClient;
	}
	public void setCustProfileClient(CustProfileClient custProfileClient) {
			this.custProfileClient = custProfileClient;
	}
	
	private ImsOrderDetailService imsOrderDetailService;
	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}
	
	private ImsAddressInfoService imsAddressInfoService;
    public void setImsAddressInfoService(ImsAddressInfoService imsAddressInfoService) {
		this.imsAddressInfoService = imsAddressInfoService;
	}
	public ImsAddressInfoService getImsAddressInfoService() {
		return imsAddressInfoService;
	}
	
	private Ims1AMSEnquiryService ims1AMSEnquiryService;
	public void setIms1AMSEnquiryService(Ims1AMSEnquiryService ims1AMSEnquiryService) {
		this.ims1AMSEnquiryService = ims1AMSEnquiryService;
	}
	public Ims1AMSEnquiryService getIms1AMSEnquiryService() {
		return ims1AMSEnquiryService;
	}
	
    private CustomerSearchClient custSearchClient;	
    
    public CustomerSearchClient getCustSearchClient() {
		return custSearchClient;
	}

	public void setCustSearchClient(CustomerSearchClient custSearchClient) {
		this.custSearchClient = custSearchClient;
	}
	
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();
		boolean isSuccess=false;
		int recordUpdated=0;
		boolean sameFSA=false;
		
		if(!isProcessing){
			
			try{
				isProcessing = true;
		
				List<OrderImsSystemFindingDTO> imsDTO = imsOrderDetailService.sysFchecking();
				logger.debug("imsDTO.size()" + imsDTO.size());
				
				
				
				for (int i=0;i<imsDTO.size();i++){
				
					     
						logger.debug("imsDTO().getSerbdyno(): " + imsDTO.get(i).getSerbdyno());
						logger.debug("imsDTO().getFloorNo(): " +  imsDTO.get(i).getFloorNo());
						logger.debug("imsDTO().getUnitNo(): " +  imsDTO.get(i).getUnitNo());
						//logger.debug("imsDTO().getCustNo().length(): " +  imsDTO.get(i).getBomCustNo().length());
						logger.debug("imsDTO.get(i).getBomCustNo(): " + imsDTO.get(i).getBomCustNo());
						logger.debug("imsDTO.get(i).getOrderId(): " + imsDTO.get(i).getOrderID());
						logger.debug("imsDTO.get(i).getSysF(): " + imsDTO.get(i).getSysF());
						
						sameFSA=false;
					
						if (imsDTO.get(i).getSysF()==null) imsDTO.get(i).setSysF("");
						logger.debug("imsDTO.get(i).getSysF().length()" + imsDTO.get(i).getSysF().length()); 
				
						if (imsDTO.get(i).getSysF().length()==0){
							List<CustomerBasicInfoDTO> custSearchResultList = Collections.emptyList();                                          
							SearchingKeyDTO searchKeyDto = new SearchingKeyDTO();                                                               
							String errorSeverity = null;                                                                                        
							                                                                                                                    
							try {                                                                                                               
								/********************* START CALLING API ******************/                                                      
							//	if (logger.isDebugEnabled()) {                                                                                    
									logger.debug("--------------------- 1D1I Checking ---------------------");                           
									logger.debug("sessionOrder.getCustomer().getIdDocType()" + imsDTO.get(i).getIdDocType());          
									logger.debug("sessionOrder.getCustomer().getIdDocNum()" + imsDTO.get(i).getIdDocNum());            
							//	}                                                                                                                 
								                                                                                                                  
								searchKeyDto.setIdDocNum(imsDTO.get(i).getIdDocNum());                                               
								if ("Passport".equals(imsDTO.get(i).getIdDocType())) {                                               
									searchKeyDto.setIdDocType("PASS");		                                                                          
								} else if ("BR".equals(imsDTO.get(i).getIdDocType())) {                                              
									searchKeyDto.setIdDocType("BS");                                                                                
								} else if ("Certificate No".equals(imsDTO.get(i).getIdDocType())) {                                  
									searchKeyDto.setIdDocType("CERT");                                                                              
								} else if ("HKID".equals(imsDTO.get(i).getIdDocType())) {                                            
									searchKeyDto.setIdDocType("HKID");                                                                              
								}                                                                                                                 
								//searchKeyDto.setLoginId(sessionOrder.getLoginId());                                                             
								//searchKeyDto.setDomainType("N");                                                                                
								searchKeyDto.setSystemId("IMS");                                                                                  
								searchKeyDto.setLoginId("");      //***must set for webservice                                                                                
								searchKeyDto.setServiceNum("");   //***must set for webservice                                                                                
								                                                                                                                  
								logger.debug("Potentail Discount Offer searchKeyDto: "+ searchKeyDto);                                            
								                                                                                                                  
								CustomerSearchResponse responses = custSearchClient.getCustomerSearchResponse(searchKeyDto);                      
								if (logger.isDebugEnabled()) {                                                                                    
									logger.debug("Cust Search - Response: " + responses);                                                           
								}                                                                                                                 
								errorSeverity = responses.getErrorSeverity();                                                                     
								if (logger.isDebugEnabled()) {                                                                                    
									logger.debug("Cust Search - Error Severity: " + errorSeverity);                                                 
								}                                                                                                                 
								if (logger.isDebugEnabled()) {                                                                                    
									logger.debug("End Cust Search Client API call");                                                                
								}     
								
								logger.debug("Cust Search - Response: " + responses);
								
								custSearchResultList = this.getCustomerBasicInfoDTOList(responses.getCustomerBasicInfoDTO());
								                                                                                                                  
							} catch (WebServiceException e) {                                                                                   
								if (logger.isWarnEnabled()) {                                                                                     
									logger.warn("Cust Search Fail: " + e);                                                                          
								}                                                                                                                 
							}                                                                                                                   
							                                                                                                                    
							List<ServiceLineDTO> serviceLineDTOList = new ArrayList<ServiceLineDTO>();                                          
							                                                                                                                    
							for (CustomerBasicInfoDTO customerBasicInfoDTO : custSearchResultList) {                                            
								for (ServiceLineDTO serviceLineDTO : customerBasicInfoDTO.getServiceLineDTO()) {                                  
									serviceLineDTOList.add(serviceLineDTO);                                                                         
								}                                                                                                                 
							}                                                                                                                   
							                                                                                                                    
							                                                                                                                    
							logger.debug("1D1I checking serviceLineDTOList.size():" + serviceLineDTOList.size());                    
							if (serviceLineDTOList != null){
								if (serviceLineDTOList.size() > 0){                                                                                 
									boolean check=false;
									
									for (int j=0;j<serviceLineDTOList.size();j++) { 
												
										if (serviceLineDTOList.get(j).getSystemId().equalsIgnoreCase(ImsConstants.IMS_ORDER_LOB)){
											check = imsOrderDetailService.checkPCDexistingServcie(serviceLineDTOList.get(j).getServiceNum());
											if (check){imsDTO.get(i).setSysF("1D1I"); break;}
										}
									}
									logger.debug("1D1I CHECKING If service exist: " + check);
								}                                                                                                                   
							}                                                                                                                  
							//1D1I checking for existing service for QC checking END----------------------------------------------------------------------
						}else imsDTO.get(i).setSysF("");
						
						logger.debug("1D1I_IA checking Start..............");
						if ( imsDTO.get(i).getSysF().length()==0){
							
							List<Ims1AMSFSAInfoDTO> ims1AMSFSAInfoDTOs = new ArrayList<Ims1AMSFSAInfoDTO>();
							ims1AMSFSAInfoDTOs = ims1AMSEnquiryService.getIms1AMSFSAInfoList2(imsDTO.get(i).getSerbdyno(), 
																							  imsDTO.get(i).getUnitNo(),
																							  imsDTO.get(i).getFloorNo(), 
																							  imsDTO.get(i).getHiLotNo());
							
						
							if (ims1AMSFSAInfoDTOs.size()>0){
								for (int j=0;j<ims1AMSFSAInfoDTOs.size();j++){ 
									if (ims1AMSFSAInfoDTOs.get(j).getFSA() != null){
										//logger.debug("SB FSA: " + imsDTO.get(i).getServiceNum());
										//logger.debug("ims1AMSFSAInfoDTOs.get(j).getStatus(): " + ims1AMSFSAInfoDTOs.get(j).getStatus());
										if (ims1AMSFSAInfoDTOs.get(j).getStatus().equalsIgnoreCase("Active")){
											//if (ims1AMSFSAInfoDTOs.get(j).getFSA().equalsIgnoreCase(imsDTO.get(i).getServiceNum())) 
											//sameFSA=true;
											imsDTO.get(i).setSysF("1D1I_IA");
										}
									}
								}
							}else imsDTO.get(i).setSysF("");
//							if (ims1AMSFSAInfoDTOs.size()>0 && !sameFSA){
//									imsDTO.get(i).setSysF("1D1I_IA");
//							}else imsDTO.get(i).setSysF("");
							logger.debug("1D1I_IA checking END..............");
						}
						//potential discount offer for QC checking      
						else if ( imsDTO.get(i).getSysF().length() ==0){
							List<CustomerBasicInfoDTO> custSearchResultList = Collections.emptyList();                                          
							SearchingKeyDTO searchKeyDto = new SearchingKeyDTO();                                                               
							String errorSeverity = null;                                                                                        
							                                                                                                                    
							try {                                                                                                               
								/********************* START CALLING API ******************/                                                      
								if (logger.isDebugEnabled()) {                                                                                    
									logger.debug("--------------------- Potential Discount Offer ---------------------");                           
									logger.debug("sessionOrder.getCustomer().getIdDocType()" + imsDTO.get(i).getIdDocType());          
									logger.debug("sessionOrder.getCustomer().getIdDocNum()" + imsDTO.get(i).getIdDocNum());            
								}                                                                                                                 
								                                                                                                                  
								searchKeyDto.setIdDocNum(imsDTO.get(i).getIdDocNum());                                               
								if ("Passport".equals(imsDTO.get(i).getIdDocType())) {                                               
									searchKeyDto.setIdDocType("PASS");		                                                                          
								} else if ("BR".equals(imsDTO.get(i).getIdDocType())) {                                              
									searchKeyDto.setIdDocType("BS");                                                                                
								} else if ("Certificate No".equals(imsDTO.get(i).getIdDocType())) {                                  
									searchKeyDto.setIdDocType("CERT");                                                                              
								} else if ("HKID".equals(imsDTO.get(i).getIdDocType())) {                                            
									searchKeyDto.setIdDocType("HKID");                                                                              
								}                                                                                                                 
								//searchKeyDto.setLoginId(sessionOrder.getLoginId());                                                             
								//searchKeyDto.setDomainType("N");                                                                                
								searchKeyDto.setSystemId("IMS");                                                                                  
								searchKeyDto.setLoginId("");      //***must set for webservice                                                                                
								searchKeyDto.setServiceNum("");   //***must set for webservice    
								searchKeyDto.setServiceType("");
								searchKeyDto.setDomainType("");
								                                                                                                                  
								logger.debug("Potentail Discount Offer searchKeyDto: "+ searchKeyDto);                                            
								                                                                                                                  
								CustomerSearchResponse responses = custSearchClient.getCustomerSearchResponse(searchKeyDto);                      
								//if (logger.isDebugEnabled()) {                                                                                    
									logger.debug("Cust Search - Response: " + responses);                                                           
								//}                                                                                                                 
								errorSeverity = responses.getErrorSeverity();                                                                     
								//if (logger.isDebugEnabled()) {                                                                                    
									logger.debug("Cust Search - Error Severity: " + errorSeverity);                                                 
								//}                                                                                                                 
								//if (logger.isDebugEnabled()) {                                                                                      
									logger.debug("End Cust Search Client API call");                                                                
								//}     
								
								logger.debug("Cust Search - Response: " + responses);
								
								custSearchResultList = this.getCustomerBasicInfoDTOList(responses.getCustomerBasicInfoDTO());
								                                                                                                                  
							} catch (WebServiceException e) {                                                                                   
								if (logger.isWarnEnabled()) {                                                                                     
									logger.warn("Cust Search Fail: " + e);                                                                          
								}                                                                                                                 
							}                                                                                                                   
							                                                                                                                    
							List<ServiceLineDTO> serviceLineDTOList = new ArrayList<ServiceLineDTO>();                                          
							                                                                                                                    
							for (CustomerBasicInfoDTO customerBasicInfoDTO : custSearchResultList) {                                            
								for (ServiceLineDTO serviceLineDTO : customerBasicInfoDTO.getServiceLineDTO()) {                                  
									serviceLineDTOList.add(serviceLineDTO);                                                                         
								}                                                                                                                 
							}                                                                                                                   
							                                                                                                                    
							                                                                                                                    
							logger.debug("Potential Discount Offer serviceLineDTOList.size():" + serviceLineDTOList.size());                    
							if (serviceLineDTOList != null){
								if (serviceLineDTOList.size() > 0){                                                                                 
									//logger.debug("Potential Discount Offer ServiceNum:" + serviceLineDTOList.get(0).getServiceNum());                 
									//logger.debug("Potential Discount Offer System ID:" + serviceLineDTOList.get(0).getSystemId());  
									
									boolean check=false;
									
									for (int j=0;j<serviceLineDTOList.size();j++) { 
												
										if (serviceLineDTOList.get(j).getSystemId().equalsIgnoreCase(ImsConstants.IMS_ORDER_LOB)){
											check = imsOrderDetailService.checkPCDretentionPeriod(serviceLineDTOList.get(j).getServiceNum());
											if (check){imsDTO.get(i).setSysF("DIST"); break;}
										}else {
											check = imsOrderDetailService.checkLTSretentionPeriod(serviceLineDTOList.get(j).getServiceNum());
											if (check){imsDTO.get(i).setSysF("DIST"); break;}
										} 
									}
									//logger.debug("PCD POTENTIAL DISCOUNT OFFER CHECKING: " + check);
									logger.debug("POTENTIAL DISCOUNT OFFER CHECKING If service exist: " + check);
								}                                                                                                                   
							}                                                                                                                  
							//potential discount offer for QC checking END----------------------------------------------------------------------
						}
						
						if (imsDTO !=null){
							if (imsDTO.get(i).getSysF().length()>0)
							    recordUpdated=imsOrderDetailService.updateDSSysFinding(imsDTO.get(i));
								logger.debug("Called update"+i);
								logger.debug("imsDTO.get(i).getOrderID(): "+imsDTO.get(i).getOrderID());
								logger.debug("imsDTO.get(i).getSysF():" +imsDTO.get(i).getSysF());
						}
						
						if (recordUpdated==1){
							jsonArray.add(
										  "{\"return\":\"" + "success" + " "+
										  "order id: "+imsDTO.get(i).getOrderID() + " "+ 
										  "sysF: "+imsDTO.get(i).getSysF() +
										  "\"}");
						}else {
							jsonArray.add(
									  "{\"return\":\"" + "failed" +" "+
									  "order id: "+imsDTO.get(i).getOrderID() +
									  "\"}");
						}
					}
			}catch(Exception e){
				logger.error("", e);
				throw new ServletException();
			}finally{
				isProcessing = false;
			}
		}else{
			jsonArray.add(
					"{\"return\":\"" + "previous request is processing" +				
					"\"}");
		}
		
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
   }
	


	private EmailReqResult parseExceptionS(OrdEmailReqDTO dto, Exception e) {

		if (e instanceof ServiceException) {
			return EmailReqResult.SMS_FAIL;
		}
		return EmailReqResult.FAIL;
	}
	private EmailReqResult parseExceptionE(OrdEmailReqDTO dto, Exception e) {
		if (e instanceof MailSendException) {
			return EmailReqResult.MAIL_SEND_EXCEPTION;
		}
		if (e instanceof FileNotFoundException) {
			return EmailReqResult.ATTACHMENT_NOT_FOUND;
		}
		if (e instanceof InvalidEncryptPasswordLengthException) {
			// mob customized message
			if ("MOB".equals(dto.getLob())) {
				return EmailReqResult.INVALID_ENCRYPT_PASSWORD_LENGTH_MOB;
			}
			return EmailReqResult.INVALID_ENCRYPT_PASSWORD_LENGTH;
		}
		if (e instanceof InvalidEncryptPasswordException) {
			return EmailReqResult.INVALID_ENCRYPT_PASSWORD;
		}
		return EmailReqResult.FAIL;
	}

	private class InvalidEncryptPasswordLengthException extends InvalidEncryptPasswordException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidEncryptPasswordLengthException() {
			super();
		}
	}

	private class InvalidEncryptPasswordException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidEncryptPasswordException() {
			super();
		}
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
