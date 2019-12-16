/*
 * Created on Dec 02, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.service;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.lts.dao.BomwebWqTransDAO;
import com.bomwebportal.lts.dao.LtsWqTransDAO;
import com.bomwebportal.lts.dto.order.LtsActionLkupDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceActionLkupBaseDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.WqActionLkupDTO;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.WorkQueueRemarkFactory;
import com.bomwebportal.lts.service.order.WorkQueueSubmissionService;
import com.bomwebportal.lts.service.PipbActvLtsService;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.service.WorkQueueService;

@Transactional(rollbackFor={Exception.class})
public class LtsWqTransServiceImpl implements LtsJobsService  {

	protected final Log logger = LogFactory.getLog(getClass());
	private static final String LAST_UPD_BY = "ltsWqJob";  
	private static final String DEFAULT_LKUP_KEY = "S99";   
	private static final String DEFAULT_LKUP_CACHE = "OE";   
	private static final Map<String,String> pipbAction2StatusMap = new HashMap<String,String>();
	private static final Map<String,String> isPIPBMap = new HashMap<String,String>();
	
	static {
		pipbAction2StatusMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_TO_C, "C");
		pipbAction2StatusMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_TO_D, "D");
		pipbAction2StatusMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_TO_L, "L");
	}
	
	static {
		isPIPBMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_TO_D, "Y");
		isPIPBMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_CHG_SRD, "Y");
		isPIPBMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_TO_C, "Y");
		isPIPBMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_TO_L, "Y");
		isPIPBMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_APN_RMK_W, "Y");
		isPIPBMap.put(LtsConstant.WQ_TRANS_ACTION_PIPB_APN_TO_C, "Y");				
	}
	
    private static String hostIp = null;	
	
	static {
		try {
			InetAddress ia = null;			
		    Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		    while(en.hasMoreElements())
		    {
		        NetworkInterface ni = (NetworkInterface)en.nextElement();
		        Enumeration<InetAddress> eia = ni.getInetAddresses();
		        while (eia.hasMoreElements())
		        {
		            ia = (InetAddress) eia.nextElement();
		            if (!ia.isLoopbackAddress()) {
			            hostIp = ia.getHostAddress();		            	   
		         	   break; 
		            }    
		        }
	            if (!ia.isLoopbackAddress()) {
	         	   break; 
	            }		           
		    }
	        if (hostIp == null) {
	            ia = Inet4Address.getLocalHost();
	            hostIp = ia.getHostAddress();
	            if (hostIp == null || ia.isLoopbackAddress()) {
	         	   hostIp = ia.getHostName();
	            }
	        }
      } catch (Exception e) {
				e.printStackTrace();
		}
		if (hostIp == null) {
			try {
				hostIp = System.getProperty("os.name");
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
	}
	
	//Injections starts
	private Map <String, CodeLkupCacheService> srvActionCacheMap;	
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private WorkQueueSubmissionService workQueueSubmissionService;	
	private WorkQueueService workQueueService;
	private BomwebWqTransDAO bomwebWqTrans;
	private LtsWqTransDAO ltsWqTrans;
	private PipbActvLtsService pipbActvLtsService;
	//Injections ends
	
	private CodeLkupCacheService srvActionCache;
	
	private int orderAge;
	private int pipbOrderAge;
	private boolean searchKeySet = false;
	private String orderId;
					
	public void exec() throws Exception {
		int procCnt = ltsWqTrans.readyWqTransaction();
		logger.info("No. of records changed from PENDING to READY: " + procCnt);
		
		this.processReadyWqTrans();
	}
	
	private void processReadyWqTrans() throws Exception {	

		ArrayList<String> wqRemarksList;		
		
		ServiceActionLkupBaseDTO serviceActionLkup = null;
		WqActionLkupDTO  wqActionLkup = null;
		ServiceActionTypeDTO serviceAction = null;
		Map<String, Map<BomwebWqTransDAO, ServiceActionTypeDTO>> orderMap = new HashMap<String, Map<BomwebWqTransDAO, ServiceActionTypeDTO>>();
		
		String lkupKey = null;
		String lkupCache = null;
		
		try {
			logger.info("Processing 'READY' WQ Transactions!");

			if (!searchKeySet) {
				bomwebWqTrans.setSearchKey("status",
						LtsConstant.WQ_TRANS_STATUS_READY);
				bomwebWqTrans.setAdditionWhere("1=1 FOR UPDATE NOWAIT");
				searchKeySet = true;
				
				logger.info("Size of isPIPBMap: " + isPIPBMap.size());
				logger.info("Size of pipbAction2StatusMap: " + pipbAction2StatusMap.size());
			}
			
			BomwebWqTransDAO[] oBomwebWqTransDAOs = (BomwebWqTransDAO[]) bomwebWqTrans
					.doSelect(null, null);
			
			logger.info("No. of 'READY' WQ Transactions: " + oBomwebWqTransDAOs.length);

			for (BomwebWqTransDAO oBomwebWqTransDAO : oBomwebWqTransDAOs) {
				
				orderId = oBomwebWqTransDAO.getOrderId();
				
				logger.info("Processing order Id " + orderId + "/" + oBomwebWqTransDAO.getAction());				
				
				if (isPIPBTrans(oBomwebWqTransDAO.getAction())) {
					this.createPIPBTrans(oBomwebWqTransDAO);
					continue;
				}
				
				
				if (LtsConstant.WQ_TRANS_ACTION_UPDATE_OCID
						.equals(oBomwebWqTransDAO.getAction())) {
					this.updateWqOcId(oBomwebWqTransDAO.getOrderId(),
							oBomwebWqTransDAO.getDtlId(), oBomwebWqTransDAO
									.getLkupKey());
					this.updateWqTrans(oBomwebWqTransDAO,LtsConstant.WQ_TRANS_STATUS_COMPLETED,"");
					continue;
				}

				if (LtsConstant.WQ_TRANS_ACTION_CANCEL_WQ
						.equals(oBomwebWqTransDAO.getAction())) {
					workQueueService.changeWqStatusBySbAction(oBomwebWqTransDAO.getOrderId(), "BOM_CANCEL", LAST_UPD_BY);
					this.updateWqTrans(oBomwebWqTransDAO,LtsConstant.WQ_TRANS_STATUS_COMPLETED,"");
					continue;
				}
				
				
				serviceActionLkup = null;				
				if (StringUtils.isNotBlank(oBomwebWqTransDAO.getWqNatureId())
						&& StringUtils.isNotBlank(oBomwebWqTransDAO
								.getWqSubtype())
						&& StringUtils
								.isNotBlank(oBomwebWqTransDAO.getWqType())) {

					serviceActionLkup = new LtsActionLkupDTO();
					wqActionLkup      = new WqActionLkupDTO();
					wqActionLkup.setWqNatureId(Long.parseLong(oBomwebWqTransDAO
							.getWqNatureId()));
					wqActionLkup.setWqType(oBomwebWqTransDAO.getWqType());
					wqActionLkup.setWqSubtype(oBomwebWqTransDAO
							.getWqSubtype());
					serviceActionLkup.setWqActionLkup(wqActionLkup);
				} else {
					lkupKey = oBomwebWqTransDAO.getLkupKey() != null ? oBomwebWqTransDAO
							.getLkupKey()
							:DEFAULT_LKUP_KEY;

					lkupCache = oBomwebWqTransDAO.getLkupCache() != null ? oBomwebWqTransDAO
							.getLkupCache()
							:DEFAULT_LKUP_CACHE;

					srvActionCache = srvActionCacheMap.get(lkupCache);
					if (srvActionCache != null) {
						serviceActionLkup = (ServiceActionLkupBaseDTO) srvActionCache
								.get(lkupKey);
						if (serviceActionLkup == null) {
							try {
								srvActionCache = srvActionCacheMap
										.get(DEFAULT_LKUP_CACHE);
								serviceActionLkup = (ServiceActionLkupBaseDTO) srvActionCache
										.get(DEFAULT_LKUP_KEY);
							} catch (Exception e) {
                                logger.error(e);
							}
						}
					}
				}

				if (serviceActionLkup != null) {
					serviceAction = new ServiceActionTypeDTO();
					BeanUtils.copyProperties(serviceAction,serviceActionLkup);
					BeanUtils.copyProperties(serviceAction,serviceActionLkup.getWqActionLkup());
					
					wqRemarksList = new ArrayList<String>();	
					
					if (StringUtils
							.isNotBlank(oBomwebWqTransDAO.getWqRemarks())) {
						wqRemarksList.add(oBomwebWqTransDAO.getWqRemarks());
					}					
					
					if ("Y".equals(oBomwebWqTransDAO.getStandardRemarks())) {
						wqRemarksList
								.addAll(Arrays
										.asList(WorkQueueRemarkFactory
												.generateStandardWqRemark(orderRetrieveLtsService
														.retrieveSbOrder(
																oBomwebWqTransDAO
																		.getOrderId(),
																false))));
					}
										
					if (wqRemarksList.size() > 0) {
						serviceAction
								.setWqNatureRemarks((String[]) wqRemarksList
										.toArray(new String[wqRemarksList
												.size()]));
					}
					
				}				
				
				this.addToSummaryMap(orderMap, oBomwebWqTransDAO,
						serviceAction);

			}
			
			this.createWorkQueue(orderMap);
			
			logger.info("Processing of WQ Transactions Completed!");			
			
		} catch (CannotAcquireLockException e) {
			logger.info("TABLE IS LOCKED. WILL RETRY LATER. " + e);
		} catch (Exception e) { 			
			logger.error("[" + orderId + "] ", e);
			throw e;
		}		
		
	}
	
	private static boolean isPIPBTrans(String pWqAction) {
		return isPIPBMap.containsKey(pWqAction);

	}
	
	private void updateWqOcId(String pOrderId,String pDtlId,String pOcId) throws Exception {
		WorkQueueDTO workQueueDTO = new WorkQueueDTO();
		workQueueDTO.setBomOcId(pOcId);
		workQueueDTO.setSbId(pOrderId);
		workQueueDTO.setSbDtlId(pDtlId);
		workQueueService.updateWorkQueue(workQueueDTO,
				LAST_UPD_BY);		
	}
	
	private void createPIPBTrans(BomwebWqTransDAO pBomwebWqTransDAO) {
		
    	logger.info("WQ Trans identified as PIPB -> " + pBomwebWqTransDAO.getOrderId() + "/" + pBomwebWqTransDAO.getAction());	
    	
		SbOrderDTO sbOrderDTO;
		ServiceDetailDTO srvDtl = null;
		String targetBomStatus = null;
		String wqAction;
		String wqTransStatus = LtsConstant.WQ_TRANS_STATUS_ERROR;
		String msgLog = "";
		
		try {		
			sbOrderDTO = orderRetrieveLtsService.retrieveSbOrder(pBomwebWqTransDAO.getOrderId(), true);
			if (sbOrderDTO == null) {
				throw new Exception("Cannot find the SbOrderDTO -> " + pBomwebWqTransDAO.getOrderId());
			}

			for (ServiceDetailDTO srvDtls : sbOrderDTO.getSrvDtls()) {
				if (StringUtils.equals(srvDtls.getDtlId(),pBomwebWqTransDAO.getDtlId())) {
					srvDtl = srvDtls;
					break;
				}
			}

			if (srvDtl == null) {
				throw new Exception("Cannot find the dtl in the SbOrderDTO -> "
						+ pBomwebWqTransDAO.getOrderId() + "/"
						+ pBomwebWqTransDAO.getDtlId());
			}

			wqAction = pBomwebWqTransDAO.getAction();
			if (pipbAction2StatusMap.containsKey(wqAction)) {
				targetBomStatus = pipbAction2StatusMap.get(wqAction);
			}		

			if (LtsConstant.WQ_TRANS_ACTION_PIPB_APN_RMK_W.equals(wqAction)) {
				pipbActvLtsService.updatePipbActivityStatus(
						sbOrderDTO.getOrderId(), pBomwebWqTransDAO.getUserId(),
						LtsActvBackendConstant.ACTV_STATUS_NO_OCID,
						LtsActvBackendConstant.ACTV_ACTION_APN_UPLOAD);
			} else if (LtsConstant.WQ_TRANS_ACTION_PIPB_APN_TO_C.equals(wqAction)) {
				pipbActvLtsService.updatePipbActivityStatus(
						sbOrderDTO.getOrderId(), pBomwebWqTransDAO.getUserId(),
						LtsActvBackendConstant.ACTV_STATUS_SRD_EXPIRED,
						LtsActvBackendConstant.ACTV_ACTION_APN_UPLOAD);
			} else {
				pipbActvLtsService.submitPipbActivity(sbOrderDTO, srvDtl,
						pBomwebWqTransDAO.getUserId(), pBomwebWqTransDAO.getShopCd(), 
								  (targetBomStatus == null ? false: true), 
								     LtsConstant.WQ_TRANS_ACTION_CHANGE_SRD.equals(wqAction), targetBomStatus);
			}
			
			wqTransStatus = LtsConstant.WQ_TRANS_STATUS_COMPLETED;
		} catch (Exception e) {
			wqTransStatus = LtsConstant.WQ_TRANS_STATUS_ERROR;
			e.printStackTrace();
			msgLog = e.getMessage();
		} finally {
			try {
			   this.updateWqTrans(pBomwebWqTransDAO,wqTransStatus,msgLog);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}		
	}
	
	private void createWorkQueue(
			Map<String, Map<BomwebWqTransDAO, ServiceActionTypeDTO>> pOrderMap)
			throws Exception {
		
    	logger.info("Creating Work Queue entries ...");
		if (pOrderMap == null || pOrderMap.size() == 0) {
			return;
		}    	
    	
		SbOrderDTO sbOrderDTO;
		ArrayList <ServiceDetailDTO> srvDtlsList = new ArrayList <ServiceDetailDTO>();
		ServiceActionTypeDTO serviceActionType = null;
		BomwebWqTransDAO bomwebWqTransDAO = null;
		ArrayList <BomwebWqTransDAO> bomwebWqTransComplList = new ArrayList <BomwebWqTransDAO>();
		String shopCd = null;
		String createBy = null;
		String [] wqTransStatus = null;
		
        for (Map.Entry<String, Map<BomwebWqTransDAO, ServiceActionTypeDTO>> entry : pOrderMap.entrySet()) {
        	sbOrderDTO = orderRetrieveLtsService.retrieveSbOrder(entry.getKey(),true);
        	if (sbOrderDTO == null) {
        		continue;
        	}
        	
        	logger.info("Adding to Work Queue the order-> " + sbOrderDTO.getOrderId());
			srvDtlsList.clear();
        	for (Map.Entry<BomwebWqTransDAO, ServiceActionTypeDTO> dtlEntry : entry.getValue().entrySet()) {

        		bomwebWqTransDAO = dtlEntry.getKey();
            	serviceActionType = dtlEntry.getValue();            	
            	if (serviceActionType == null) {
    				this.updateWqTrans(bomwebWqTransDAO,LtsConstant.WQ_TRANS_STATUS_ERROR,"Null serviceActionType!");
                	continue;            		
            	}
            	
            	logger.info("Getting service details of the order-> " + sbOrderDTO.getOrderId());	            	
				for (ServiceDetailDTO srvDtls : sbOrderDTO.getSrvDtls()) {
	            	logger.info("Checking service detail with dtl id " + srvDtls.getDtlId());	            	
					if (StringUtils.equals(srvDtls.getDtlId(), bomwebWqTransDAO.getDtlId())) {
						if (srvDtlsList.contains(srvDtls)) {
							ServiceActionTypeDTO[] tmpSrvActions = srvDtls.getSrvActions();
							ServiceActionTypeDTO[] newSrvActions = new ServiceActionTypeDTO[tmpSrvActions.length+1];
							System.arraycopy(tmpSrvActions,0,newSrvActions,0,tmpSrvActions.length);
							newSrvActions[tmpSrvActions.length] = serviceActionType;
						    srvDtls.setSrvActions(newSrvActions);
						    break;
						} else {
						    srvDtls.setSrvActions(new ServiceActionTypeDTO[] { serviceActionType });
						    srvDtlsList.add(srvDtls);
						    break;
						}
					}					
				}

				//this.updateWqTrans(bomwebWqTransDAO,LtsConstant.WQ_TRANS_STATUS_COMPLETED);
				bomwebWqTransComplList.add(bomwebWqTransDAO);
				shopCd = bomwebWqTransDAO.getShopCd();
				createBy = bomwebWqTransDAO.getUserId();
			}
        	
        	if (srvDtlsList.size() > 0) {
/*        		try {
					workQueueSubmissionService.submitToWorkQueue(sbOrderDTO,
							srvDtlsList
									.toArray(new ServiceDetailDTO[srvDtlsList
											.size()]), createBy, shopCd);
					wqTransStatus = LtsConstant.WQ_TRANS_STATUS_COMPLETED;
				} catch (Exception e) {
					wqTransStatus = LtsConstant.WQ_TRANS_STATUS_ERROR;
					logger.error(e.getMessage());
				}*/
        		wqTransStatus = this.submitToWorkQueue(sbOrderDTO, srvDtlsList
						.toArray(new ServiceDetailDTO[srvDtlsList.size()]),
						createBy, shopCd);
        		
				for (BomwebWqTransDAO bomwebWqTransCompl : bomwebWqTransComplList) {
					this.updateWqTrans(bomwebWqTransCompl,wqTransStatus[0],wqTransStatus[1]);					
				}				
				bomwebWqTransComplList.clear();
			}
        }
		
	}
	
	private void updateWqTrans(BomwebWqTransDAO bomwebWqTransDAO, String pStatus, String pMsgLog) throws Exception {
		bomwebWqTransDAO.setLastUpdBy(LAST_UPD_BY);
		bomwebWqTransDAO.setStatus(pStatus);
		bomwebWqTransDAO.setMsgLog(StringUtils.substring( ("(" + hostIp + ") " + pMsgLog), 0, 4000));
		bomwebWqTransDAO.doUpdate();
	}
	
	private String [] submitToWorkQueue(SbOrderDTO pSbOrder,ServiceDetailDTO [] pServiceDetails, String pCreateBy, String pShopCd) {
		try {
        	logger.info("Calling workQueueSubmissionService for order id STARTS-> " + pSbOrder.getOrderId());			
			workQueueSubmissionService.submitToWorkQueue(pSbOrder,
					pServiceDetails, pCreateBy, pShopCd);
        	logger.info("Calling workQueueSubmissionService for order id DONE!-> " + pSbOrder.getOrderId());			
			return new String[] {LtsConstant.WQ_TRANS_STATUS_COMPLETED,""};
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new String[] {LtsConstant.WQ_TRANS_STATUS_ERROR,e.getMessage()};
		}		
	}
	
	private void addToSummaryMap(
			Map<String, Map<BomwebWqTransDAO, ServiceActionTypeDTO>> pSummaryMap,
			BomwebWqTransDAO pBomwebWqTransDAO,ServiceActionTypeDTO pServiceActionTypeDTO) {
		
		if (pServiceActionTypeDTO == null || pSummaryMap == null
				|| pBomwebWqTransDAO == null) {
			return;
		}
		
		Map <BomwebWqTransDAO,ServiceActionTypeDTO> tmpMap= null; 
		if (pSummaryMap.containsKey(pBomwebWqTransDAO.getOrderId())) {
			tmpMap = pSummaryMap.get(pBomwebWqTransDAO.getOrderId());
			tmpMap.put(pBomwebWqTransDAO,pServiceActionTypeDTO);
		} else {
			tmpMap = new HashMap <BomwebWqTransDAO,ServiceActionTypeDTO>();
			tmpMap.put(pBomwebWqTransDAO,pServiceActionTypeDTO);
			pSummaryMap.put(pBomwebWqTransDAO.getOrderId(),tmpMap);
		}		
		
	}
	
	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public WorkQueueSubmissionService getWorkQueueSubmissionService() {
		return workQueueSubmissionService;
	}

	public void setWorkQueueSubmissionService(
			WorkQueueSubmissionService workQueueSubmissionService) {
		this.workQueueSubmissionService = workQueueSubmissionService;
	}

	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}

	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}

	public int getOrderAge() {
		return orderAge;
	}

	public void setOrderAge(int orderAge) {
		this.orderAge = orderAge;
	}

	public BomwebWqTransDAO getBomwebWqTrans() {
		return bomwebWqTrans;
	}

	public void setBomwebWqTrans(BomwebWqTransDAO bomwebWqTrans) {
		this.bomwebWqTrans = bomwebWqTrans;
	}

	public Map<String, CodeLkupCacheService> getSrvActionCacheMap() {
		return srvActionCacheMap;
	}

	public void setSrvActionCacheMap(
			Map<String, CodeLkupCacheService> srvActionCacheMap) {
		this.srvActionCacheMap = srvActionCacheMap;
	}

	public LtsWqTransDAO getLtsWqTrans() {
		return ltsWqTrans;
	}

	public void setLtsWqTrans(LtsWqTransDAO ltsWqTrans) {
		this.ltsWqTrans = ltsWqTrans;
	}

	public PipbActvLtsService getPipbActvLtsService() {
		return pipbActvLtsService;
	}

	public void setPipbActvLtsService(PipbActvLtsService pipbActvLtsService) {
		this.pipbActvLtsService = pipbActvLtsService;
	}

	public void setPipbOrderAge(int pipbOrderAge) {
		this.pipbOrderAge = pipbOrderAge;
	}

}
