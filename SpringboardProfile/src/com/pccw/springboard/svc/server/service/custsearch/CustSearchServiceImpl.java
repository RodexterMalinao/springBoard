package com.pccw.springboard.svc.server.service.custsearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.util.LtsProfileConstant;
import com.pccw.springboard.svc.server.dao.DaoException;
import com.pccw.springboard.svc.server.dao.custsearch.CustSearchDAO;
import com.pccw.springboard.svc.server.dao.custsearch.CustomerSearchDao;
import com.pccw.springboard.svc.server.dto.custsearch.AccountDTO;
import com.pccw.springboard.svc.server.dto.custsearch.AlertDTO;
import com.pccw.springboard.svc.server.dto.custsearch.CustomerOverviewDTO;
import com.pccw.springboard.svc.server.dto.custsearch.PcdLogin;
import com.pccw.springboard.svc.server.dto.custsearch.ResultDTO;
import com.pccw.springboard.svc.server.dto.custsearch.SearchingKeyDTO;
import com.pccw.springboard.svc.server.dto.custsearch.ServiceSummaryDTO;

/**
 * 
 * Springboard Single View of Customer & Customer Journey(SVC)

 * 
 * <br>
 * <br>&copy; PCCW Solution Limited. All rights reserved.
 * <br>Revision History: <br>
 * <TABLE BORDER=1>
 * <TR><TH>Date</TH><TH>Name</TH><TH>Changes</TH></TR>
 * <TR><TD>05/03/2014</TD><TD>Kenny Yip</TD><TD>Initial version</TD></TR>
 * <TR><TD>23/04/2015</TD><TD>Kenny Yip</TD><TD>SVC Phase 2</TD></TR>
 * </TABLE>
 *
 */

public class CustSearchServiceImpl implements CustomerSearchDao {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustSearchDAO custSearchDAO;
	
	@Override
	public ResultDTO bomCustomerSearch(SearchingKeyDTO searchingKey) throws DaoException {
		ResultDTO result = new ResultDTO();
		List<CustomerOverviewDTO> customerOverview = null;
		List<ServiceSummaryDTO> serviceSummary = null;
		String idDocType = searchingKey.getIdDocTypeCode();
		String idDocNum = searchingKey.getIdDocNum();
		String serviceType = searchingKey.getServiceType();
		String serviceNum = searchingKey.getServiceNum();
		String loginId = searchingKey.getLoginID();
		String domainType = searchingKey.getDomainType();
		String acctNum = searchingKey.getAccountNum();
		String bsn = searchingKey.getBsn();
		String parentCustNum = searchingKey.getParentcustNum();		
		String singleSearchInd = searchingKey.getSingleSearchInd();		
		int maxServiceCnt = searchingKey.getMaxServiceCnt();
		String[] pCustNumList = new String[0];		
		
		try {
			// if not single search
			if (StringUtils.isEmpty(searchingKey.getSingleSearchInd()) || "N".equals(singleSearchInd)) {			
				// Search by id doc type and id doc number
				if (!StringUtils.isEmpty(idDocType) && !StringUtils.isEmpty(idDocNum)) {
					pCustNumList = custSearchDAO.getParentCustNumByIdDocNum(idDocType, idDocNum);
				}
				// Search by service type and service number
				if (!StringUtils.isEmpty(serviceType) && !StringUtils.isEmpty(serviceNum)) {
					if (LtsProfileConstant.SERVICE_TYPE_TEL.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_MOB.equals(serviceType)) {
						serviceNum = StringUtils.leftPad(serviceNum, 12, "0");
						pCustNumList = custSearchDAO.getParentCustNumByLts(serviceType, serviceNum);
					}
					if (LtsProfileConstant.SERVICE_TYPE_AGR.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_ITS.equals(serviceType)) {
						pCustNumList = custSearchDAO.getParentCustNumByLts(serviceType, serviceNum);
					}
                    if (LtsProfileConstant.SERVICE_TYPE_IMS.equals(serviceType)) {
                    	pCustNumList = custSearchDAO.getParentCustNumByIms(serviceType, serviceNum);
					}
                    if (LtsProfileConstant.SERVICE_TYPE_MRT.equals(serviceType)) {
                    	pCustNumList = custSearchDAO.getParentCustNumByMob(serviceType, serviceNum);
                    }					
				}
				// Search by IMS login
				if (!StringUtils.isEmpty(loginId) && !StringUtils.isEmpty(domainType)) {
					pCustNumList = custSearchDAO.getParentCustNumByImsLoginId(loginId, domainType);
				}
				// Search by account number
				if (!StringUtils.isEmpty(acctNum)) {
					pCustNumList = custSearchDAO.getParentCustNumByAcctNum(acctNum);
				}
				// Search by BSN
				if (!StringUtils.isEmpty(bsn)) {
					pCustNumList = custSearchDAO.getParentCustNumByBsn(bsn);
				}
				// Search by parent customer number
				if (!StringUtils.isEmpty(parentCustNum)) {
					customerOverview = custSearchDAO.customerSearchByParentCustNum(parentCustNum);
				}
				// if more than one parent customer found
				if (pCustNumList.length > 1) {				
					customerOverview = new ArrayList<CustomerOverviewDTO>();
					if (!StringUtils.isEmpty(idDocType) && !StringUtils.isEmpty(idDocNum)) {
						for (int i=0; i<pCustNumList.length; i++) {
							customerOverview.addAll(custSearchDAO.getMultipleParentCustNumListByParentCust(pCustNumList[i]));
					    }
					}
					if (!StringUtils.isEmpty(serviceType) && !StringUtils.isEmpty(serviceNum)) {
						if (LtsProfileConstant.SERVICE_TYPE_TEL.equals(serviceType)
								|| LtsProfileConstant.SERVICE_TYPE_MOB.equals(serviceType)
								|| LtsProfileConstant.SERVICE_TYPE_AGR.equals(serviceType)
								|| LtsProfileConstant.SERVICE_TYPE_ITS.equals(serviceType)) {
							customerOverview = custSearchDAO.getMultipleParentCustNumListByLts(serviceType, serviceNum);
						}
	                    if (LtsProfileConstant.SERVICE_TYPE_IMS.equals(serviceType)) {
	                    	customerOverview = custSearchDAO.getMultipleParentCustNumListByIms(serviceType, serviceNum);
						}
	                    if (LtsProfileConstant.SERVICE_TYPE_MRT.equals(serviceType)) {
	                    	customerOverview = custSearchDAO.getMultipleParentCustNumListByMob(serviceType, serviceNum);
	                    }					
					}
					if (!StringUtils.isEmpty(loginId) && !StringUtils.isEmpty(domainType)) {
						customerOverview = custSearchDAO.getMultipleParentCustNumListByLogin(loginId, domainType);
					}
					result.setResultCode(ResultDTO.RESULT_MULTIPLE_CUST_FOUND);
				} else {
					// if just one parent customer found
					if (pCustNumList.length == 1) {
						customerOverview = custSearchDAO.customerSearchByParentCustNum(pCustNumList[0]);
					}					
					if (customerOverview == null) {
						result.setResultCode(ResultDTO.RESULT_NO_CUST_OR_SERVICE_FOUND);
					} else {
					    // retrieve service summary
						List<ServiceSummaryDTO> srvSummaryList = null;
						for (int n=0; n<customerOverview.size(); n++) {
							// if the maximum of service line is exceeded, break
							if (custSearchDAO.isServiceExceedLimit(
									customerOverview.get(n).getCustNum(), customerOverview.get(n).getSystemId(), maxServiceCnt)) {							
								result.setResultCode(ResultDTO.RESULT_SERVICE_EXCEED_LIMIT);
								break;
							}
							srvSummaryList = custSearchDAO.getServiceSummary(
										customerOverview.get(n).getCustNum(), customerOverview.get(n).getSystemId());							
							if (srvSummaryList != null) {
								// set the list related account to each service line								
								List<AccountDTO> acctList = custSearchDAO.getRelatedAcctsOfService(
										customerOverview.get(n).getCustNum(), customerOverview.get(n).getSystemId());
								if (acctList != null) {									
						    		for (int v=0; v<srvSummaryList.size(); v++) {
						    			List<AccountDTO> relatedAccts = new ArrayList<AccountDTO>();
						    			for (int i=0; i<acctList.size(); i++) {
						    				if (acctList.get(i).getServiceId().equals(srvSummaryList.get(v).getServiceId())) {
						    					relatedAccts.add(acctList.get(i));
						    				}
						    			}
						    			if (relatedAccts.size()>0) {
						    				srvSummaryList.get(v).setRelatedAccts(relatedAccts);
						    			}						    			
						    		}
					    	    }								
								// for IMS, set the list of login id and domain type
								if (LtsProfileConstant.SYSTEM_ID_IMS.equals(customerOverview.get(n).getSystemId())) {
									List<PcdLogin> pcdLoginList = custSearchDAO.getPcdLoginByFsa(
											customerOverview.get(n).getCustNum(), customerOverview.get(n).getSystemId());
									if (pcdLoginList != null) {
							    		for (int v=0; v<srvSummaryList.size(); v++) {
							    			List<PcdLogin> relatedPcdLogin = new ArrayList<PcdLogin>();
							    			for (int i=0; i<pcdLoginList.size(); i++) {
								    			if (pcdLoginList.get(i).getServiceId().equals(srvSummaryList.get(v).getServiceId())) {
								    				relatedPcdLogin.add(pcdLoginList.get(i));	
								    			}								    				
							    			}
							    			if (relatedPcdLogin.size()>0) {
								    			srvSummaryList.get(v).setLogin(relatedPcdLogin);
							    			}							    									    			
							    		}
						    	    }									
								}								
								if (serviceSummary == null) {
									serviceSummary = new ArrayList<ServiceSummaryDTO>();
								}								
								serviceSummary.addAll(srvSummaryList);
								// if the maximum of service line is exceeded, break
								if (serviceSummary.size() > maxServiceCnt) {
									result.setResultCode(ResultDTO.RESULT_SERVICE_EXCEED_LIMIT);
									break;
								}
							}						
						}						
					}
				}
			}
			
			// if single search
			if ("Y".equals(singleSearchInd)) {
				// Search by parent customer number and service type and service number
				if (!StringUtils.isEmpty(parentCustNum) && 
						!StringUtils.isEmpty(serviceType) && !StringUtils.isEmpty(serviceNum)) {
					if (LtsProfileConstant.SERVICE_TYPE_TEL.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_MOB.equals(serviceType)) {
						serviceNum = StringUtils.leftPad(serviceNum, 12, "0");
						customerOverview = custSearchDAO.getLtsCustomerOverviewBySingleSearch(
								parentCustNum, serviceType, serviceNum);
					}
					if (LtsProfileConstant.SERVICE_TYPE_AGR.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_ITS.equals(serviceType)) {
						customerOverview = custSearchDAO.getLtsCustomerOverviewBySingleSearch(
								parentCustNum, serviceType, serviceNum);
					}
                    if (LtsProfileConstant.SERVICE_TYPE_IMS.equals(serviceType)) {
                    	customerOverview = custSearchDAO.getImsCustomerOverviewBySingleSearch(
                    			parentCustNum, serviceType, serviceNum);
					}
                    if (LtsProfileConstant.SERVICE_TYPE_MRT.equals(serviceType)) {
                    	customerOverview = custSearchDAO.getMobCustomerOverviewBySingleSearch(
                    			parentCustNum, serviceType, serviceNum);
                    }
				}
				// Search by service type and service number
				if (StringUtils.isEmpty(parentCustNum) &&
						!StringUtils.isEmpty(serviceType) && !StringUtils.isEmpty(serviceNum)) {
					if (LtsProfileConstant.SERVICE_TYPE_TEL.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_MOB.equals(serviceType)) {
						serviceNum = StringUtils.leftPad(serviceNum, 12, "0");
						pCustNumList = custSearchDAO.getParentCustNumByLts(serviceType, serviceNum);
					}
					if (LtsProfileConstant.SERVICE_TYPE_AGR.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_ITS.equals(serviceType)) {
						pCustNumList = custSearchDAO.getParentCustNumByLts(serviceType, serviceNum);
					}
                    if (LtsProfileConstant.SERVICE_TYPE_IMS.equals(serviceType)) {
                    	pCustNumList = custSearchDAO.getParentCustNumByIms(serviceType, serviceNum);
					}
                    if (LtsProfileConstant.SERVICE_TYPE_MRT.equals(serviceType)) {
                    	pCustNumList = custSearchDAO.getParentCustNumByMob(serviceType, serviceNum);
                    }                    
				}				
				// if more than one parent customer found
				if (pCustNumList.length > 1) {				
					customerOverview = new ArrayList<CustomerOverviewDTO>();
					if (LtsProfileConstant.SERVICE_TYPE_TEL.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_MOB.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_AGR.equals(serviceType)
							|| LtsProfileConstant.SERVICE_TYPE_ITS.equals(serviceType)) {
						customerOverview = custSearchDAO.getMultipleParentCustNumListByLts(serviceType, serviceNum);
					}
                    if (LtsProfileConstant.SERVICE_TYPE_IMS.equals(serviceType)) {
                    	customerOverview = custSearchDAO.getMultipleParentCustNumListByIms(serviceType, serviceNum);
					}
                    if (LtsProfileConstant.SERVICE_TYPE_MRT.equals(serviceType)) {
                    	customerOverview = custSearchDAO.getMultipleParentCustNumListByMob(serviceType, serviceNum);
                    }
					result.setResultCode(ResultDTO.RESULT_MULTIPLE_CUST_FOUND);
				} else {
					// if just one parent customer found
					if (pCustNumList.length == 1) {
						if (LtsProfileConstant.SERVICE_TYPE_TEL.equals(serviceType)
								|| LtsProfileConstant.SERVICE_TYPE_MOB.equals(serviceType)
								|| LtsProfileConstant.SERVICE_TYPE_AGR.equals(serviceType)
								|| LtsProfileConstant.SERVICE_TYPE_ITS.equals(serviceType)) {
							customerOverview = custSearchDAO.getLtsCustomerOverviewBySingleSearch(
									pCustNumList[0], serviceType, serviceNum);
						}
	                    if (LtsProfileConstant.SERVICE_TYPE_IMS.equals(serviceType)) {
	                    	customerOverview = custSearchDAO.getImsCustomerOverviewBySingleSearch(
	                    			pCustNumList[0], serviceType, serviceNum);
						}
	                    if (LtsProfileConstant.SERVICE_TYPE_MRT.equals(serviceType)) {
	                    	customerOverview = custSearchDAO.getMobCustomerOverviewBySingleSearch(
	                    			pCustNumList[0], serviceType, serviceNum);
	                    }
					}
					if (customerOverview == null) {
						result.setResultCode(ResultDTO.RESULT_NO_CUST_OR_SERVICE_FOUND);
					} else {
						// retrieve service summary
						List<ServiceSummaryDTO> srvSummaryList = null;
						srvSummaryList = custSearchDAO.getServiceSummaryBySingleSearch(
								customerOverview.get(0).getCustNum(), serviceType, serviceNum, customerOverview.get(0).getSystemId());
						if (srvSummaryList != null) {
							// set the list of related account to the service line								
							List<AccountDTO> relatedAccts = custSearchDAO.getRelatedAcctsOfServiceBySingleSearch(
									customerOverview.get(0).getCustNum(), srvSummaryList.get(0).getServiceId(),
									srvSummaryList.get(0).getServiceType(), customerOverview.get(0).getSystemId());
							if (relatedAccts != null) {
					    		srvSummaryList.get(0).setRelatedAccts(relatedAccts);					    		
				    	    }
							// for IMS, set login id and domain type
							if (LtsProfileConstant.SYSTEM_ID_IMS.equals(customerOverview.get(0).getSystemId())) {	
								List<PcdLogin> relatedPcdLogin = custSearchDAO.getPcdLoginByFsaBySingleSearch(srvSummaryList.get(0).getServiceId());
								if (relatedPcdLogin != null) {
						    		srvSummaryList.get(0).setLogin(relatedPcdLogin);
					    	    }
							}							
							if (serviceSummary == null) {
								serviceSummary = new ArrayList<ServiceSummaryDTO>();
							}								
							serviceSummary.addAll(srvSummaryList);
						}
					}	    					
				}
			}
						
			// result set
			if (result.getResultCode() == ResultDTO.RESULT_SERVICE_EXCEED_LIMIT
					|| result.getResultCode() == ResultDTO.RESULT_NO_CUST_OR_SERVICE_FOUND) {
				return result;
			} else {
				if (customerOverview != null && customerOverview.size() > 0
						&& result.getResultCode() == ResultDTO.RESULT_MULTIPLE_CUST_FOUND) {
					result.setCustomerInfo(customerOverview);	
				} else if (customerOverview != null && customerOverview.size() > 0
							&& serviceSummary == null) {
						result.setCustomerInfo(customerOverview);
						result.setResultCode(ResultDTO.RESULT_NO_SERVICE_LINE_FOUND);
				} else if (customerOverview != null && customerOverview.size() > 0
							&& serviceSummary != null && serviceSummary.size() > 0) {
						result.setCustomerInfo(customerOverview);
						result.setServiceInfo(serviceSummary);				
						result.setResultCode(ResultDTO.RESULT_SUCCESS);
				}
			}
			
			return result;
			
		} catch (Exception e) {
			logger.error("Fail in CustSearchService.bomCustomerSearch()", e);
			throw new DaoException(e.getMessage(), e);
		}
				
	}
	
	@Override
	public AlertDTO checkSpecialComplaintCase(String idDocNum) throws DaoException {
		try {
			return custSearchDAO.checkSpecialComplaintCase(idDocNum);
		} catch (Exception e) {
			logger.error("Fail in CustSearchService.checkSpecialComplaintCase()", e);
			throw new DaoException(e.getMessage(), e);
		}		
	}

	/**
	 * @return the custSearchDAO
	 */
	public CustSearchDAO getCustSearchDAO() {
		return custSearchDAO;
	}

	/**
	 * @param custSearchDAO the custSearchDAO to set
	 */
	public void setCustSearchDAO(CustSearchDAO custSearchDAO) {
		this.custSearchDAO = custSearchDAO;
	}	

}
