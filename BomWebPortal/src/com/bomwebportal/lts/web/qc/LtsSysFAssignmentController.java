package com.bomwebportal.lts.web.qc;

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
import com.bomwebportal.ims.service.Ims1AMSEnquiryService;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.lts.dto.qc.OrderLtsSystemFindingDTO;
import com.bomwebportal.lts.service.qc.LtsDsOrderDetailService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.wsclient.CustProfileClient;
import com.bomwebportal.wsclient.CustomerSearchClient;
//why


public class LtsSysFAssignmentController  implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	private static boolean isProcessing;
	private CustProfileClient custProfileClient;
	
	 public CustProfileClient getCustProfileClient() {
			return custProfileClient;
	}
	public void setCustProfileClient(CustProfileClient custProfileClient) {
			this.custProfileClient = custProfileClient;
	}
	
//	private ImsOrderDetailService imsOrderDetailService;
//	public ImsOrderDetailService getImsOrderDetailService() {
//		return imsOrderDetailService;
//	}
	
	private LtsDsOrderDetailService ltsDsOrderDetailService;
	

	public LtsDsOrderDetailService getLtsDsOrderDetailService() {
		return ltsDsOrderDetailService;
	}
	public void setLtsDsOrderDetailService(
			LtsDsOrderDetailService ltsDsOrderDetailService) {
		this.ltsDsOrderDetailService = ltsDsOrderDetailService;
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
		
				List<OrderLtsSystemFindingDTO> ltsDTO = ltsDsOrderDetailService.sysFchecking();
				logger.debug("ltsDTO.size()" + ltsDTO.size());
				
				
				
				for (int i=0;i<ltsDTO.size();i++){
				
					     
						logger.debug("ltsDTO().getSerbdyno(): " + ltsDTO.get(i).getSerbdyno());
						logger.debug("ltsDTO().getFloorNo(): " +  ltsDTO.get(i).getFloorNo());
						logger.debug("ltsDTO().getUnitNo(): " +  ltsDTO.get(i).getUnitNo());
						//logger.debug("ltsDTO().getCustNo().length(): " +  ltsDTO.get(i).getBomCustNo().length());
						logger.debug("ltsDTO.get(i).getBomCustNo(): " + ltsDTO.get(i).getBomCustNo());
						logger.debug("ltsDTO.get(i).getOrderId(): " + ltsDTO.get(i).getOrderID());
						logger.debug("ltsDTO.get(i).getSysF(): " + ltsDTO.get(i).getSysF());
						
						sameFSA=false;
					
						if (ltsDTO.get(i).getSysF()==null) ltsDTO.get(i).setSysF("");
						logger.debug("ltsDTO.get(i).getSysF().length()" + ltsDTO.get(i).getSysF().length()); 
				
						if (ltsDTO.get(i).getSysF().length()==0){
							List<CustomerBasicInfoDTO> custSearchResultList = Collections.emptyList();                                          
							SearchingKeyDTO searchKeyDto = new SearchingKeyDTO();                                                               
							String errorSeverity = null;                                                                                        
							                                                                                                                    
							try {                                                                                                               
								/********************* START CALLING API ******************/                                                      
							//	if (logger.isDebugEnabled()) {                                                                                    
									logger.debug("--------------------- 1D1I Checking ---------------------");                           
									logger.debug("sessionOrder.getCustomer().getIdDocType()" + ltsDTO.get(i).getIdDocType());          
									logger.debug("sessionOrder.getCustomer().getIdDocNum()" + ltsDTO.get(i).getIdDocNum());            
							//	}                                                                                                                 
								                                                                                                                  
								searchKeyDto.setIdDocNum(ltsDTO.get(i).getIdDocNum());                                               
								if ("Passport".equals(ltsDTO.get(i).getIdDocType())) {                                               
									searchKeyDto.setIdDocType("PASS");		                                                                          
								} else if ("BR".equals(ltsDTO.get(i).getIdDocType())) {                                              
									searchKeyDto.setIdDocType("BS");                                          	                                       
								} else if ("Certificate No".equals(ltsDTO.get(i).getIdDocType())) {                                  
									searchKeyDto.setIdDocType("CERT");                                                                              
								} else if ("HKID".equals(ltsDTO.get(i).getIdDocType())) {                                            
									searchKeyDto.setIdDocType("HKID");                                                                              
								}                                                                                                                 
								//searchKeyDto.setLoginId(sessionOrder.getLoginId());                                                             
								//searchKeyDto.setDomainType("N");                                                                                
								searchKeyDto.setSystemId("DRG");                                                                                  
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
												
//										if (serviceLineDTOList.get(j).getSystemId().equalsIgnoreCase(ImsConstants.IMS_ORDER_LOB)){
										if (serviceLineDTOList.get(j).getSystemId().equalsIgnoreCase("DRG")){
//											check = ltsDsOrderDetailService.checkPCDexistingServcie(serviceLineDTOList.get(j).getServiceNum());
											if(!"".equals(serviceLineDTOList.get(j).getServiceNum())){
												check = true;
											}else {check = false;}
											if (check){ltsDTO.get(i).setSysF("1D1I"); break;}
										}
									}
									logger.debug("1D1I CHECKING If service exist: " + check);
								}                                                                                                                   
							}                                                                                                                  
							//1D1I checking for existing service for QC checking END----------------------------------------------------------------------
						}else ltsDTO.get(i).setSysF("");
						
						logger.debug("1D1I_IA checking Start..............");
						if ( ltsDTO.get(i).getSysF().length()==0){
							
//							List<Ims1AMSFSAInfoDTO> ims1AMSFSAInfoDTOs = new ArrayList<Ims1AMSFSAInfoDTO>();
//							ims1AMSFSAInfoDTOs = ims1AMSEnquiryService.getIms1AMSFSAInfoList2(ltsDTO.get(i).getSerbdyno(), 
//																							  ltsDTO.get(i).getUnitNo(),
//																							  ltsDTO.get(i).getFloorNo(), 
//																							  ltsDTO.get(i).getHiLotNo());
							List<String> ltsAddressSrvNumList = new ArrayList<String>();
							ltsAddressSrvNumList = ltsDsOrderDetailService.getAddressSrvNum(ltsDTO.get(i).getSerbdyno(), 
									                                                        ltsDTO.get(i).getUnitNo(), 
									                                                        ltsDTO.get(i).getFloorNo());
							
						if((ltsAddressSrvNumList).size()>0){
							for (int j=0;j<ltsAddressSrvNumList.size();j++){ 
								if(ltsAddressSrvNumList.get(j) != null){
									ltsDTO.get(i).setSysF("1D1I_IA");
								}
							}
//							if (ims1AMSFSAInfoDTOs.size()>0){
//								for (int j=0;j<ims1AMSFSAInfoDTOs.size();j++){ 
//									if (ims1AMSFSAInfoDTOs.get(j).getFSA() != null){
//										//logger.debug("SB FSA: " + ltsDTO.get(i).getServiceNum());
//										//logger.debug("ims1AMSFSAInfoDTOs.get(j).getStatus(): " + ims1AMSFSAInfoDTOs.get(j).getStatus());
//										if (ims1AMSFSAInfoDTOs.get(j).getStatus().equalsIgnoreCase("Active")){
//											//if (ims1AMSFSAInfoDTOs.get(j).getFSA().equalsIgnoreCase(ltsDTO.get(i).getServiceNum())) 
//											//sameFSA=true;
//											ltsDTO.get(i).setSysF("1D1I_IA");
//										}
//									}
//								}
							}else ltsDTO.get(i).setSysF("");
//							if (ims1AMSFSAInfoDTOs.size()>0 && !sameFSA){
//									ltsDTO.get(i).setSysF("1D1I_IA");
//							}else ltsDTO.get(i).setSysF("");
							logger.debug("1D1I_IA checking END..............");
						}
						//potential discount offer for QC checking      
//						else if ( ltsDTO.get(i).getSysF().length() ==0){
//							List<CustomerBasicInfoDTO> custSearchResultList = Collections.emptyList();                                          
//							SearchingKeyDTO searchKeyDto = new SearchingKeyDTO();                                                               
//							String errorSeverity = null;                                                                                        
//							                                                                                                                    
//							try {                                                                                                               
//								/********************* START CALLING API ******************/                                                      
//								if (logger.isDebugEnabled()) {                                                                                    
//									logger.debug("--------------------- Potential Discount Offer ---------------------");                           
//									logger.debug("sessionOrder.getCustomer().getIdDocType()" + ltsDTO.get(i).getIdDocType());          
//									logger.debug("sessionOrder.getCustomer().getIdDocNum()" + ltsDTO.get(i).getIdDocNum());            
//								}                                                                                                                 
//								                                                                                                                  
//								searchKeyDto.setIdDocNum(ltsDTO.get(i).getIdDocNum());                                               
//								if ("Passport".equals(ltsDTO.get(i).getIdDocType())) {                                               
//									searchKeyDto.setIdDocType("PASS");		                                                                          
//								} else if ("BR".equals(ltsDTO.get(i).getIdDocType())) {                                              
//									searchKeyDto.setIdDocType("BS");                                                                                
//								} else if ("Certificate No".equals(ltsDTO.get(i).getIdDocType())) {                                  
//									searchKeyDto.setIdDocType("CERT");                                                                              
//								} else if ("HKID".equals(ltsDTO.get(i).getIdDocType())) {                                            
//									searchKeyDto.setIdDocType("HKID");                                                                              
//								}                                                                                                                 
//								//searchKeyDto.setLoginId(sessionOrder.getLoginId());                                                             
//								//searchKeyDto.setDomainType("N");                                                                                
//								searchKeyDto.setSystemId("DRG");                                                                                  
//								searchKeyDto.setLoginId("");      //***must set for webservice                                                                                
//								searchKeyDto.setServiceNum("");   //***must set for webservice    
//								searchKeyDto.setServiceType("");
//								searchKeyDto.setDomainType("");
//								                                                                                                                  
//								logger.debug("Potentail Discount Offer searchKeyDto: "+ searchKeyDto);                                            
//								                                                                                                                  
//								CustomerSearchResponse responses = custSearchClient.getCustomerSearchResponse(searchKeyDto);                      
//								//if (logger.isDebugEnabled()) {                                                                                    
//									logger.debug("Cust Search - Response: " + responses);                                                           
//								//}                                                                                                                 
//								errorSeverity = responses.getErrorSeverity();                                                                     
//								//if (logger.isDebugEnabled()) {                                                                                    
//									logger.debug("Cust Search - Error Severity: " + errorSeverity);                                                 
//								//}                                                                                                                 
//								//if (logger.isDebugEnabled()) {                                                                                      
//									logger.debug("End Cust Search Client API call");                                                                
//								//}     
//								
//								logger.debug("Cust Search - Response: " + responses);
//								
//								custSearchResultList = this.getCustomerBasicInfoDTOList(responses.getCustomerBasicInfoDTO());
//								                                                                                                                  
//							} catch (WebServiceException e) {                                                                                   
//								if (logger.isWarnEnabled()) {                                                                                     
//									logger.warn("Cust Search Fail: " + e);                                                                          
//								}                                                                                                                 
//							}                                                                                                                   
//							                                                                                                                    
//							List<ServiceLineDTO> serviceLineDTOList = new ArrayList<ServiceLineDTO>();                                          
//							                                                                                                                    
//							for (CustomerBasicInfoDTO customerBasicInfoDTO : custSearchResultList) {                                            
//								for (ServiceLineDTO serviceLineDTO : customerBasicInfoDTO.getServiceLineDTO()) {                                  
//									serviceLineDTOList.add(serviceLineDTO);                                                                         
//								}                                                                                                                 
//							}                                                                                                                   
//							                                                                                                                    
//							                                                                                                                    
//							logger.debug("Potential Discount Offer serviceLineDTOList.size():" + serviceLineDTOList.size());                    
//							if (serviceLineDTOList != null){
//								if (serviceLineDTOList.size() > 0){                                                                                 
//									//logger.debug("Potential Discount Offer ServiceNum:" + serviceLineDTOList.get(0).getServiceNum());                 
//									//logger.debug("Potential Discount Offer System ID:" + serviceLineDTOList.get(0).getSystemId());  
//									
//									boolean check=false;
//									
//									for (int j=0;j<serviceLineDTOList.size();j++) { 
//												
//										if (serviceLineDTOList.get(j).getSystemId().equalsIgnoreCase(ImsConstants.IMS_ORDER_LOB)){
//											check = imsOrderDetailService.checkPCDretentionPeriod(serviceLineDTOList.get(j).getServiceNum());
//											if (check){ltsDTO.get(i).setSysF("DIST"); break;}
//										}else {
//											check = imsOrderDetailService.checkLTSretentionPeriod(serviceLineDTOList.get(j).getServiceNum());
//											if (check){ltsDTO.get(i).setSysF("DIST"); break;}
//										} 
//									}
//									//logger.debug("PCD POTENTIAL DISCOUNT OFFER CHECKING: " + check);
//									logger.debug("POTENTIAL DISCOUNT OFFER CHECKING If service exist: " + check);
//								}                                                                                                                   
//							}                                                                                                                  
//							//potential discount offer for QC checking END----------------------------------------------------------------------
//						}
						
						if (ltsDTO !=null){
							if (ltsDTO.get(i).getSysF().length()>0)
							    recordUpdated=ltsDsOrderDetailService.updateDSSysFinding(ltsDTO.get(i));
								logger.debug("Called update"+i);
								logger.debug("ltsDTO.get(i).getOrderID(): "+ltsDTO.get(i).getOrderID());
								logger.debug("ltsDTO.get(i).getSysF():" +ltsDTO.get(i).getSysF());
						}
						
						if (recordUpdated==1){
							jsonArray.add(
										  "{\"return\":\"" + "success" + " "+
										  "order id: "+ltsDTO.get(i).getOrderID() + " "+ 
										  "sysF: "+ltsDTO.get(i).getSysF() +
										  "\"}");
						}else {
							jsonArray.add(
									  "{\"return\":\"" + "failed" +" "+
									  "order id: "+ltsDTO.get(i).getOrderID() +
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