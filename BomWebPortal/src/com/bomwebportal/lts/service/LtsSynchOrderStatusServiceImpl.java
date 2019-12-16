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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.lts.dao.BomwebWqTransDAO;
import com.bomwebportal.lts.dao.bom.BomOrderAppointmentDAO;
import com.bomwebportal.lts.dao.bom.BomOrderStatusSynchDAO;
import com.bomwebportal.lts.dao.order.AppointmentLtsDAO;
import com.bomwebportal.lts.dao.order.OrderStatusSynchDAO;
import com.bomwebportal.lts.dto.order.BomOrderAppntDTO;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.CodeLkupCacheServiceImpl;
import com.pccw.util.cache.FIFOCacheMap;

@Transactional(rollbackFor={Exception.class})
public class LtsSynchOrderStatusServiceImpl implements LtsJobsService  {

	protected final Log logger = LogFactory.getLog(getClass());
	private static Map <String, String> oldBOrderCache;

	private static final SimpleDateFormat dateFormatter  = new SimpleDateFormat("yyyyMMddHHmmss");	
	private static final SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyyMMdd");		
	
	private static final int  ACTION_STATUS_CHANGE         = 0;
	private static final int  ACTION_DISTRIBUTED_NO_CHANGE = 1;
	private static final int  ACTION_FAILED                = 2;
	private static final int  ACTION_OTHERS_NO_CHANGE      = 3;
	private static final int  ACTION_OTHERS_NON_WQ_CHANGE  = 4;
	
	private static final String REGEXP_ANY_STATUS   = "\\w+";
		
	private static final Map<String,String []> wqTransActionMap = new HashMap<String,String []>();
	
    private static String hostIp = null;
    
    private static List<String> dActionList = null; 
	
    private static String REGEXP_D_2_L = null;
    
	static {
		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          LtsConstant.BOM_ORDER_STATUS_UNDER_INVESTIGATE,
						          REGEXP_ANY_STATUS),
				new String[] { LtsConstant.WQ_TRANS_ACTION_TO_INVSTGT });
		
		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          "((" + LtsConstant.BOM_ORDER_STATUS_SUSPWDORDER + ")|(" + LtsConstant.BOM_ORDER_STATUS_SUSPWOORDER + "))" ,
						          REGEXP_ANY_STATUS),
						new String[] { LtsConstant.WQ_TRANS_ACTION_CHECK_FOR_BLACKLIST });		
		
		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER,
						          REGEXP_ANY_STATUS),
				new String[] { LtsConstant.WQ_TRANS_ACTION_CANCEL_WQ });
		
		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS,
						          LtsConstant.DRG_ORDER_STATUS_CANCELLED),
				new String[] { LtsConstant.WQ_TRANS_ACTION_PIPB_TO_C});		
		
		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS,
						          LtsConstant.DRG_ORDER_STATUS_DISTRIBUTED),
				new String[] { 
					    LtsConstant.WQ_TRANS_ACTION_UPDATE_OCID,
						LtsConstant.WQ_TRANS_ACTION_VIM_FREE,
						LtsConstant.WQ_TRANS_ACTION_BACK_ORDER,
						LtsConstant.WQ_TRANS_ACTION_PIPB_TO_D });

		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS,
						          LtsConstant.IMS_ORDER_STATUS_ISSUED),
				new String [] {LtsConstant.WQ_TRANS_ACTION_UPDATE_OCID});		
		
		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS,
						          LtsConstant.DRG_ORDER_STATUS_COMPLETED),
                new String[] {
						LtsConstant.WQ_TRANS_ACTION_CHECK_FOR_IDD_FREE,
						LtsConstant.WQ_TRANS_ACTION_UPDATE_OCID,
						LtsConstant.WQ_TRANS_ACTION_VIM_FREE,
						LtsConstant.WQ_TRANS_ACTION_BACK_ORDER,
						LtsConstant.WQ_TRANS_ACTION_PIPB_TO_L });

		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS,
						          LtsConstant.IMS_ORDER_STATUS_L1_FALLOUT),
			    new String [] {LtsConstant.WQ_TRANS_ACTION_TO_FALLOUT});		

		
		// BOM status changed from INVESTIGATE to NOT(and not cancelled) = "(?:(?!01|02).)*" 
		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS, 
						          LtsConstant.BOM_ORDER_STATUS_UNDER_INVESTIGATE,
						          REGEXP_ANY_STATUS, 
						          REGEXP_ANY_STATUS,
						          "(?:(?!" + LtsConstant.BOM_ORDER_STATUS_UNDER_INVESTIGATE + "|" + LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER + ").)*" ,						          
						          REGEXP_ANY_STATUS),
			    new String [] {LtsConstant.WQ_TRANS_ACTION_FROM_INVSTGT});		
		
		// IMS status changed from L1 FALLOUT to NOT(but not cancelled and exempted) = "(?:(?!01|02).)*" 
		wqTransActionMap.put(
				buildRegExpString(REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS,
						          LtsConstant.IMS_ORDER_STATUS_L1_FALLOUT,
						          REGEXP_ANY_STATUS,
						          "(?:(?!" + LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER + ").)*",
						          "(?:(?!" + LtsConstant.IMS_ORDER_STATUS_L1_FALLOUT + ").)*" ),
			    new String [] {LtsConstant.WQ_TRANS_ACTION_FROM_FALLOUT});			
		
		// Sb status changed from 'F' to 'B'
		wqTransActionMap.put(
				buildRegExpString(LtsConstant.ORDER_STATUS_FORCED_WQ,
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS,
						          LtsConstant.ORDER_STATUS_CREATE_BOM,
						          REGEXP_ANY_STATUS,
						          REGEXP_ANY_STATUS),
			    new String [] {LtsConstant.WQ_TRANS_ACTION_FAILED_RESUME});			
				
		dActionList = Arrays.asList(wqTransActionMap.get(buildRegExpString(REGEXP_ANY_STATUS,
				                                                           REGEXP_ANY_STATUS,
		                                                                   REGEXP_ANY_STATUS, 
		                                                                   REGEXP_ANY_STATUS,
		                                                                   REGEXP_ANY_STATUS,
		                                                                   LtsConstant.DRG_ORDER_STATUS_DISTRIBUTED)));		
		
		REGEXP_D_2_L = buildRegExpString(REGEXP_ANY_STATUS, 
                                         REGEXP_ANY_STATUS,
                                         LtsConstant.DRG_ORDER_STATUS_DISTRIBUTED,
                                         REGEXP_ANY_STATUS, 
                                         REGEXP_ANY_STATUS,
                                         LtsConstant.DRG_ORDER_STATUS_COMPLETED);
		
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
    private OrderStatusSynchDAO orderStatusSynch;
	private BomOrderStatusSynchDAO bomOrderStatusSynch;
	private BomwebWqTransDAO bomwebWqTrans;
	private BomOrderAppointmentDAO bomOrderAppointment;
	private AppointmentLtsDAO appointmentLts;
	private int fifoMaxCache = DEFAULT_FIFO_MAX_CACHE;
	private CodeLkupCacheServiceImpl l1ExemptCache;
	private CodeLkupCacheServiceImpl bomOrdStatusLkupCache;
	//Injections ends
	
	private AppointmentLtsDAO emptyAppointmentLts;
	
	private int orderSuffix = 0;
	
	private int loopStopper = 0;
	
	private int orderAge;
	
	private int pipbOrderAge;
	
	private String orderId;
	
	private String sbOrderStatus;
	
	private static final String LAST_UPD_BY = "ltsSync";
	
	private static final int DEFAULT_FIFO_MAX_CACHE = 15000;
	
	private static final int MAX_LOOP_RETRY = 10;
	
	public void exec() throws Exception {	
		int updateCnt  = 0;
	    int wqTransCnt = 0;
	    int appntTransCnt = 0;
	    int NNLegacyCount = 0;
	    int SRDCount  = 0;
	    Date newSrvReqDate = null;
		
		Map <String,ArrayList<OrderStatusSynchDTO>> orderIdOcIdMap = new HashMap <String,ArrayList<OrderStatusSynchDTO>>();
		
		logger.info("hostIp = " + hostIp);
		
		if (emptyAppointmentLts == null) {
			emptyAppointmentLts = (AppointmentLtsDAO)BeanUtils.cloneBean(appointmentLts);
		}
		
		if (oldBOrderCache == null) {
			oldBOrderCache = new FIFOCacheMap <String, String> (fifoMaxCache);
			l1ExemptCache.get("0000");  //initialize the lookup so containsKey will not fail 
			orderSuffix =  Calendar.getInstance().get(Calendar.MINUTE)%10;  //use the last digit of minute as starting point
		}
		
		try {
			logger.info("Synchronizing status from BOM to SB for orders not more than " + this.getOrderAge() + " days old (for non-pipb) or not more than " + this.getPipbOrderAge() + " days old (for pipb).");

			OrderStatusSynchDTO[] bomOrderStatusSynchDTO;

			OrderStatusSynchDTO [] orderStatusSynchDTOs = null;
			
			orderStatusSynchDTOs = this.getPendingSbOrders(MAX_LOOP_RETRY); 
			
			logger.info("No. of LTS SB order pending synchronization: " + (orderStatusSynchDTOs==null?0:orderStatusSynchDTOs.length));
			
			for (OrderStatusSynchDTO orderStatusSynchDTO : orderStatusSynchDTOs) {
				
				orderId = orderStatusSynchDTO.getOrderId();
											
				if (oldBOrderCache.containsKey(this
						.getFIFOKey(orderStatusSynchDTO))) {
					continue;
				} else {
					bomOrderStatusSynchDTO = this.getStatus(orderStatusSynchDTO,orderIdOcIdMap);
				}
												
				if (bomOrderStatusSynchDTO != null && bomOrderStatusSynchDTO.length > 0) {					
					
					//Update NN, Legacy Ord Num/Actv Seq
					NNLegacyCount = NNLegacyCount + synchNNLegacyOrd(bomOrderStatusSynchDTO,orderStatusSynchDTO);
					
					// Update the SRD 
					newSrvReqDate = synchSrvReqDate(bomOrderStatusSynchDTO,orderStatusSynchDTO);
					if (newSrvReqDate != null) {
						SRDCount++;
					}
					
					// Update the DRC, Appointment Start/End Date 
					appntTransCnt = appntTransCnt + this.synchAppntDelayRea(orderStatusSynchDTO,bomOrderStatusSynchDTO,newSrvReqDate);				
															
					// Check for status change
					int action = this.getAction(bomOrderStatusSynchDTO,orderStatusSynchDTO);
					switch (action) {
					        case ACTION_OTHERS_NON_WQ_CHANGE:   // update the status but no WQ creation					        	
					        	 logger.info("ACTION_OTHERS_NON_WQ_CHANGE for " + orderId);
					        	
					        case ACTION_STATUS_CHANGE:   // update the status
					        	
								 if (LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER.equals(bomOrderStatusSynchDTO[0].getBomStatus()) || 
							 		 	 LtsConstant.BOM_ORDER_STATUS_CANCELLED_WO_ORDER.equals(bomOrderStatusSynchDTO[0].getBomStatus()) ) {
					  	 			 sbOrderStatus = LtsConstant.ORDER_STATUS_CANCELLED;
								 } else if (LtsConstant.BOM_ORDER_STATUS_COMPLETED.equals(bomOrderStatusSynchDTO[0].getBomStatus())) {
							 		 sbOrderStatus = LtsConstant.ORDER_STATUS_CLOSED;
								 } else {
								 	 sbOrderStatus = LtsConstant.ORDER_STATUS_CREATE_BOM;							
							 	 }
								
								 orderStatusSynch
										.updateSbStatus(orderStatusSynchDTO.getRowId(),
												sbOrderStatus,
												bomOrderStatusSynchDTO[0].getBomStatus(), 
												bomOrderStatusSynchDTO[0].getBomLegacyStatus(),
												LAST_UPD_BY, 
												null,
												null,
												bomOrderStatusSynchDTO[0].getL1ReaCd(),
												bomOrderStatusSynchDTO[0].getL1OrdStatus()
												);
								
								 updateCnt++;
						 		
								 wqTransCnt = wqTransCnt + this.createWQTrans(bomOrderStatusSynchDTO, orderStatusSynchDTO, sbOrderStatus,action);
								 break;
								 
					        case ACTION_DISTRIBUTED_NO_CHANGE:   // put in the FIFO cache to be checked later
					        	
								 oldBOrderCache.put(this
										.getFIFOKey(orderStatusSynchDTO),"Y");
								 break;
							
					        case ACTION_FAILED: // set status = 'F'
					        	
								 orderStatusSynch.updateSbStatus(orderStatusSynchDTO
										.getRowId(),
										LtsConstant.ORDER_STATUS_FORCED_WQ, 
										null,
										null,
										LAST_UPD_BY,
										null,
										null,
										null,
										null);							
								
								 updateCnt++;				
									
								 this.createWQTrans(orderStatusSynchDTO,LtsConstant.WQ_TRANS_ACTION_FAILED_OE,bomOrderStatusSynchDTO[0].getErrMsg());
								 wqTransCnt++;
								 break;
								 
					}						
					
					
					/* If status is 'E' means(1st update) 
					 * or from 'F' but no longer has an error and has no OCID before 
					 * or status changed from 'B' to 'F'
					 * then update the BOM_ORDER.
					 */
					if (LtsConstant.ORDER_STATUS_EXTRACTED
							.equals(orderStatusSynchDTO.getSbStatus())
							|| (LtsConstant.ORDER_STATUS_FORCED_WQ
									.equals(orderStatusSynchDTO.getSbStatus())
									&& bomOrderStatusSynchDTO[0].getErrCd() == null && StringUtils
									.isBlank(orderStatusSynchDTO.getOcId()))
							|| (LtsConstant.ORDER_STATUS_CREATE_BOM
									.equals(orderStatusSynchDTO.getSbStatus()) && bomOrderStatusSynchDTO[0]
									.getErrCd() != null)									 
					) {	
						
						orderStatusSynchDTO.setOcId(bomOrderStatusSynchDTO[0].getOcId());
						orderStatusSynchDTO.setBomDtlId(bomOrderStatusSynchDTO[0].getBomDtlId());
						orderStatusSynchDTO.setErrCd(bomOrderStatusSynchDTO[0].getErrCd());
						orderStatusSynchDTO.setErrMsg(bomOrderStatusSynchDTO[0].getErrMsg());
						orderStatusSynchDTO.setBomStatus(bomOrderStatusSynchDTO[0].getBomStatus());
						orderStatusSynchDTO.setBomLegacyStatus(bomOrderStatusSynchDTO[0].getBomLegacyStatus());
						orderStatusSynchDTO.setGrpId(bomOrderStatusSynchDTO[0].getGrpId());
						orderStatusSynchDTO.setGrpIoInd(bomOrderStatusSynchDTO[0].getGrpIoInd());
						orderStatusSynchDTO.setGrpType(bomOrderStatusSynchDTO[0].getGrpType());
						
																									
						this.addToSummaryMap(orderIdOcIdMap,orderStatusSynchDTO);						
					}
				}								
				
			}
			
			//Update ocid, err_cd and err_msg & WQ one time. No need to check status 'E' as already filtered above. 
			for (Map.Entry<String,ArrayList<OrderStatusSynchDTO>> entry : orderIdOcIdMap.entrySet()) {
				orderStatusSynch.updateOcId(entry.getKey(), entry.getValue()
						.get(0).getOcId(), entry.getValue().get(0).getErrCd(),
						entry.getValue().get(0).getErrMsg(), LAST_UPD_BY);
			}
			
			logger.info("No. of Order Status Updated: " + updateCnt);
			logger.info("No. of Appointment WQ Trans Created: " + appntTransCnt);
			logger.info("No. of Status WQ Trans Created: " + wqTransCnt);			
			logger.info("No. of NN/Legacy Order Updated: " + NNLegacyCount);
			logger.info("No. of SRD Updated: " + SRDCount);
			
		} catch (Exception e) {
			logger.error("[" + orderId + "] ", e);
			throw e;
		}
	}
	
	private static String buildRegExpString(String pSbStatusFrom, String pBomStatusFrom, String pLegacyStatusFrom, String pSbStatusTo, String pBomStatusTo, String pLegacyStatusTo) {
		StringBuffer regExp =  new StringBuffer();
		
		regExp.append(StringUtils.defaultIfEmpty(StringUtils.trimToNull(pSbStatusFrom),"_"));
		regExp.append("-");
		regExp.append(StringUtils.defaultIfEmpty(StringUtils.trimToNull(pBomStatusFrom),"_"));
		regExp.append("-");
		regExp.append(StringUtils.defaultIfEmpty(StringUtils.trimToNull(pLegacyStatusFrom),"_"));

		regExp.append(":");
				
		regExp.append(StringUtils.defaultIfEmpty(StringUtils.trimToNull(pSbStatusTo),"_"));
		regExp.append("-");
		regExp.append(StringUtils.defaultIfEmpty(StringUtils.trimToNull(pBomStatusTo),"_"));
		regExp.append("-");
		regExp.append(StringUtils.defaultIfEmpty(StringUtils.trimToNull(pLegacyStatusTo),"_"));
		
		return regExp.toString();
	}
	
	private int synchNNLegacyOrd(OrderStatusSynchDTO[] pBomOrderStatusSynchDTO, OrderStatusSynchDTO pOrderStatusSynchDTO) throws Exception {
		if ("TEL".equals(pOrderStatusSynchDTO.getTypeOfSrv()) && 
			((pBomOrderStatusSynchDTO[0].getSrvNN() != 0 && pBomOrderStatusSynchDTO[0]
				.getSrvNN() != pOrderStatusSynchDTO.getSrvNN())
				|| (pBomOrderStatusSynchDTO[0].getLegacyOrdNum() != null && !pBomOrderStatusSynchDTO[0]
						.getLegacyOrdNum().equals(
								pOrderStatusSynchDTO.getLegacyOrdNum()))
				|| (pBomOrderStatusSynchDTO[0].getLegacyOrdNum() != null && pBomOrderStatusSynchDTO[0]
						.getLegacyActvSeq() != pOrderStatusSynchDTO
						.getLegacyActvSeq())				)
			)
		{
			orderStatusSynch.updateNNLegacyOrd(
					pOrderStatusSynchDTO.getOrderId(),
					pOrderStatusSynchDTO.getDtlId(),
					pBomOrderStatusSynchDTO[0].getSrvNN(),
					pBomOrderStatusSynchDTO[0].getLegacyOrdNum(),
					pBomOrderStatusSynchDTO[0].getLegacyActvSeq(),
					LAST_UPD_BY);
			
			return 1;
		} else {
			return 0;
		}						
	}	
	
	private Date synchSrvReqDate(OrderStatusSynchDTO[] pBomOrderStatusSynchDTO, OrderStatusSynchDTO pOrderStatusSynchDTO) throws Exception {
		Date newSrvReqDate = null;
		
		if (pBomOrderStatusSynchDTO[0].getSrvReqDate() != null
				&& ! pBomOrderStatusSynchDTO[0].getSrvReqDate()
						.equals(pOrderStatusSynchDTO
								.getBomSrvReqDate())) {
			orderStatusSynch.updateSrvReqDate(
					pOrderStatusSynchDTO.getOrderId(),
					pOrderStatusSynchDTO.getDtlId(),
					pBomOrderStatusSynchDTO[0].getSrvReqDate(),
					LAST_UPD_BY);
			
			newSrvReqDate = pBomOrderStatusSynchDTO[0].getSrvReqDate();
			
			if (pOrderStatusSynchDTO.getBomSrvReqDate() != null) {
				this.createWQTrans(pOrderStatusSynchDTO,LtsConstant.WQ_TRANS_ACTION_CHANGE_SRD, "New SRD: " + dateFormatter2.format(newSrvReqDate));
			}
			
		} 
		
		return newSrvReqDate;
		
	}
	
	private int getAction(OrderStatusSynchDTO[] pBomOrderStatusSynchDTO, OrderStatusSynchDTO pOrderStatusSynchDTO) {
		
		/* (1) if sb status 'E' or 'F' or 'P' and err_cd is null OR
		 * (2) if sb status 'B' and err_cd is null and the bom_status or legacy_status has changed 
		*/
		if ((LtsConstant.ORDER_STATUS_FORCED_WQ.equals(pOrderStatusSynchDTO.getSbStatus())      ||   
				LtsConstant.ORDER_STATUS_PENDING_BOM.equals(pOrderStatusSynchDTO.getSbStatus()) ||
				LtsConstant.ORDER_STATUS_EXTRACTED.equals(pOrderStatusSynchDTO.getSbStatus()))
				&& pBomOrderStatusSynchDTO[0].getErrCd() == null
				|| (LtsConstant.ORDER_STATUS_CREATE_BOM
						.equals(pOrderStatusSynchDTO.getSbStatus()) && pBomOrderStatusSynchDTO[0].getErrCd() == null &&
					(!StringUtils
						.equals(pOrderStatusSynchDTO.getBomStatus(),
								pBomOrderStatusSynchDTO[0]
										.getBomStatus()) 
							|| 
					 !StringUtils
						.equals(pOrderStatusSynchDTO.getBomLegacyStatus(),
								pBomOrderStatusSynchDTO[0]
										.getBomLegacyStatus()))
					)
		 ) 		
		{			
			return ACTION_STATUS_CHANGE;
			
		} else if (LtsConstant.SERVICE_TYPE_IMS.equals(pOrderStatusSynchDTO.getTypeOfSrv())  // if 'B' and err_cd is null and IMS and L1 Status changed
				     && LtsConstant.ORDER_STATUS_CREATE_BOM.equals(pOrderStatusSynchDTO.getSbStatus())
				       && pBomOrderStatusSynchDTO[0].getErrCd() == null
				         && (!StringUtils.equals(pOrderStatusSynchDTO.getL1OrdStatus(),pBomOrderStatusSynchDTO[0].getL1OrdStatus()) ||
				     !StringUtils.equals(pOrderStatusSynchDTO.getL1ReaCd(),pBomOrderStatusSynchDTO[0].getL1ReaCd()))  
				   ) {
			return ACTION_OTHERS_NON_WQ_CHANGE;			
			
		} else if (LtsConstant.ORDER_STATUS_CREATE_BOM    //  if sb status 'B' and order is distributed
				.equals(pOrderStatusSynchDTO.getSbStatus())
				&& (LtsConstant.DRG_ORDER_STATUS_DISTRIBUTED
						.equals(pOrderStatusSynchDTO.getBomLegacyStatus()) || LtsConstant.IMS_ORDER_STATUS_ISSUED
						.equals(pOrderStatusSynchDTO.getBomLegacyStatus()))) {			
			return ACTION_DISTRIBUTED_NO_CHANGE;   
			
		} else if ((LtsConstant.ORDER_STATUS_EXTRACTED.equals(pOrderStatusSynchDTO.getSbStatus()) 	// if sb status is 'E' or 'B' and error_cd is not null
				|| LtsConstant.ORDER_STATUS_CREATE_BOM.equals(pOrderStatusSynchDTO.getSbStatus())) 
	             && pBomOrderStatusSynchDTO[0].getErrCd() != null) {			
			return ACTION_FAILED;
			
		} else {
			return ACTION_OTHERS_NO_CHANGE;
		}
			
	}
	
	private int createWQTrans(OrderStatusSynchDTO[] pBomOrderStatusSynchDTO, OrderStatusSynchDTO pOrderStatusSynchDTO, String pNewSbOrderStatus, int pAction) throws Exception {
		
		if (pAction == ACTION_OTHERS_NON_WQ_CHANGE) {
			return 0;
		}
		
		ArrayList<String> wqActionArrAL = new ArrayList<String>();
		String wqRemarks = null;
		int wqTransCnt = 0;
		
		String orderStatus = buildRegExpString(pOrderStatusSynchDTO.getSbStatus(),
				                               pOrderStatusSynchDTO.getBomStatus(),
				                               pOrderStatusSynchDTO.getBomLegacyStatus(),
				                               (pNewSbOrderStatus==null?pBomOrderStatusSynchDTO[0].getSbStatus():pNewSbOrderStatus),
				                               pBomOrderStatusSynchDTO[0].getBomStatus(),
				                               pBomOrderStatusSynchDTO[0].getBomLegacyStatus());
		
        for (Map.Entry<String,String[]> entry : wqTransActionMap.entrySet())
        {
			if (orderStatus.matches(entry.getKey())) {
				wqActionArrAL.addAll(Arrays.asList((String[])(entry.getValue())));
			}
        }
		
		String [] wqActionArr = wqActionArrAL.toArray(new String[wqActionArrAL.size()]);
		if (wqActionArr != null) {
			for (String wqAction : wqActionArr) {

				logger.info("Processing " + wqAction + " for order "
						+ pOrderStatusSynchDTO.getOrderId() + "/"
						+ pOrderStatusSynchDTO.getDtlId());

				// If new status is 'L' and the action is also found in 'D' and the old status = 'D' then ignore
				if (dActionList.contains(wqAction)
						&& orderStatus.matches(REGEXP_D_2_L)) {
					continue;
				}

				// If back date action but not a back dated transaction or not auto complete then ignore
				if (LtsConstant.WQ_TRANS_ACTION_BACK_ORDER.equals(wqAction)
						&& (!("Y".equals(pBomOrderStatusSynchDTO[0].getBackDateInd()) && "N".equals(pBomOrderStatusSynchDTO[0].getAutoCompleteInd())))) {
					continue;
				}

				if (LtsConstant.WQ_TRANS_ACTION_TO_FALLOUT.equals(wqAction)) {
					wqRemarks = "(" + pBomOrderStatusSynchDTO[0].getL1ReaCd() + ") " + pBomOrderStatusSynchDTO[0].getL1ReaDesc();
				} else if (LtsConstant.WQ_TRANS_ACTION_FAILED_RESUME.equals(wqAction)) {
					wqRemarks = "Resumed" + (pOrderStatusSynchDTO.getErrCd() != null ? ": (" + pOrderStatusSynchDTO.getErrCd() + ")" : " " + pOrderStatusSynchDTO.getErrMsg());
				} else {
					wqRemarks = null;
				}
				
				// If not PIPB then don't create PIPB WQ trans record
				if ((LtsConstant.WQ_TRANS_ACTION_PIPB_TO_D.equals(wqAction) || 
						LtsConstant.WQ_TRANS_ACTION_PIPB_TO_L.equals(wqAction) || 
						   LtsConstant.WQ_TRANS_ACTION_PIPB_TO_C.equals(wqAction)) 
						&&  ! pOrderStatusSynchDTO.isPIPB()) {
					continue;
				}
				
				// FL 20170106 No need to send WQ to DS Support for address blacklist case
				if (LtsConstant.WQ_TRANS_ACTION_CHECK_FOR_BLACKLIST.equals(wqAction)
						&& StringUtils.equals(wqRemarks, "addressBlacklist")
						&& StringUtils.startsWith(pOrderStatusSynchDTO.getOrderId(), "D")){
					continue;
				}
				
				// If investigate case then add status description
				if (LtsConstant.WQ_TRANS_ACTION_TO_INVSTGT.equals(wqAction)) {
					wqRemarks = (String)bomOrdStatusLkupCache.get(pBomOrderStatusSynchDTO[0].getBomStatus()+"^"+pBomOrderStatusSynchDTO[0].getBomLegacyStatus());
				}
					
				this.createWQTrans(pOrderStatusSynchDTO, wqAction, wqRemarks);
				wqTransCnt++;
			}
		} else {
			this.createWQTrans(pOrderStatusSynchDTO,pOrderStatusSynchDTO.getReaCd(),wqRemarks!=null?wqRemarks:pBomOrderStatusSynchDTO[0].getErrMsg());
			wqTransCnt++;							
		}
		
        return wqTransCnt;
		
	}
	
	private OrderStatusSynchDTO[] getStatus(OrderStatusSynchDTO pOrderStatusSynchDTO,Map<String, ArrayList<OrderStatusSynchDTO>> pOrderIdOcIdMap)
			throws Exception {
		
		OrderStatusSynchDTO[] bomOrderStatusSynchDTO;
		
		bomOrderStatusSynchDTO = bomOrderStatusSynch.getStatus(
				pOrderStatusSynchDTO.getOrderId(),
				pOrderStatusSynchDTO.getTypeOfSrv(),
				pOrderStatusSynchDTO.getSrvNum(),
				pOrderStatusSynchDTO.getCcServiceId(),
				pOrderStatusSynchDTO.getCcServiceMemNum(),
				null,
				null,
				pOrderStatusSynchDTO.getToProd());
		
		//If IMS not found then retry with an ocId and grpid from LTS
		if ( (bomOrderStatusSynchDTO == null || bomOrderStatusSynchDTO.length < 1)
				&& LtsConstant.SERVICE_TYPE_IMS
						.equals(pOrderStatusSynchDTO.getTypeOfSrv())) {
			
			OrderStatusSynchDTO ltsOrderStatusSynchDTO = getCorrLtsLineByOrderId(pOrderIdOcIdMap,pOrderStatusSynchDTO.getOrderId());
			if (ltsOrderStatusSynchDTO != null) {
				bomOrderStatusSynchDTO = bomOrderStatusSynch.getStatus(
						pOrderStatusSynchDTO.getOrderId(),
						pOrderStatusSynchDTO.getTypeOfSrv(),
						pOrderStatusSynchDTO.getSrvNum(),
						pOrderStatusSynchDTO.getCcServiceId(),
						pOrderStatusSynchDTO.getCcServiceMemNum(),
						ltsOrderStatusSynchDTO.getOcId(),
						ltsOrderStatusSynchDTO.getGrpId(),
						ltsOrderStatusSynchDTO.getToProd());							
			}			
		}		
		
		return bomOrderStatusSynchDTO;
	}
	
	private OrderStatusSynchDTO [] getPendingSbOrders(int pRetry) throws Exception {
		loopStopper = 0;
		return this.getPendingSbOrders(pRetry,0);
	}
	
	private OrderStatusSynchDTO [] getPendingSbOrders(int pRetry, int pRetryCnt) throws Exception {
		if (loopStopper > MAX_LOOP_RETRY*1.25) {
			logger.info("Looping for over " + loopStopper + "times. Could be an endless loop. Stopping...");
			return null;
		} else {
			loopStopper++;
		}
		
		logger.info("Getting pending orders ending with " + orderSuffix);
		
		pRetryCnt++;
				
		OrderStatusSynchDTO [] orderStatusSynchDTOs;
		try {
	        //Get pending orders
			orderStatusSynchDTOs = orderStatusSynch.getPendingSbOrders(this.getOrderAge(), "%" + orderSuffix, this.getPipbOrderAge());
			incrementOrderSuffix();
			if ((orderStatusSynchDTOs == null || orderStatusSynchDTOs.length <= 0) && pRetryCnt < pRetry) {
				return this.getPendingSbOrders(pRetry,pRetryCnt);
			}
		} catch (CannotAcquireLockException e1) {
			if (pRetryCnt < pRetry) {
				logger.info("Retrying " + pRetryCnt);
				incrementOrderSuffix();
				return this.getPendingSbOrders(pRetry,pRetryCnt);
			} else {
				logger.info("RECORDS ARE STILL LOCKED. GIVING UP AND WILL RETRY AGAIN LATER!");
				orderStatusSynchDTOs = new OrderStatusSynchDTO[0];				
			}
		}
		
		return orderStatusSynchDTOs;
	}
	
	private void createWQTrans(OrderStatusSynchDTO pOrderStatusSynchDTO,String pAction, String pRemarks) throws Exception {
		if (StringUtils.isBlank(pAction)) {
			return;
		}
		bomwebWqTrans.setOrderId(pOrderStatusSynchDTO.getOrderId());
		bomwebWqTrans.setDtlId(pOrderStatusSynchDTO.getDtlId());
		bomwebWqTrans.setStatus(LtsConstant.WQ_TRANS_STATUS_PENDING);
		bomwebWqTrans.setWqRemarks(pRemarks);
		bomwebWqTrans.setLkupKey("?");
		bomwebWqTrans.setLkupCache("?");
		bomwebWqTrans.setLastUpdBy(LAST_UPD_BY);
		bomwebWqTrans.setCreateBy(LAST_UPD_BY);
		bomwebWqTrans.setAction(pAction);
		bomwebWqTrans.setShopCd(pOrderStatusSynchDTO.getShopCd());
		bomwebWqTrans.setUserId(pOrderStatusSynchDTO.getCreateBy());
		bomwebWqTrans.setStandardRemarks("N");
		bomwebWqTrans.setHostIp(StringUtils.isBlank(hostIp)?"UNKNOWN":hostIp);
		bomwebWqTrans.doInsert();
	}
	
    private String getFIFOKey(OrderStatusSynchDTO pOrderStatusSynchDTO) {
    	GregorianCalendar calendar = new GregorianCalendar();
    	
    	long timeDiff = ((pOrderStatusSynchDTO.getBomSrvReqDate()==null?calendar.getTime().getTime():pOrderStatusSynchDTO.getBomSrvReqDate().getTime()) - calendar.getTime().getTime())/(1000 * 60 * 60 * 24);
    			
    	if (pOrderStatusSynchDTO != null) {
    		// if 'B' and legacy status is 'D' for LTS and L1 Order Status is 'D' for IMS and SRD is > 1 day then check only every 6 hours at most
    		if (LtsConstant.ORDER_STATUS_CREATE_BOM.equals(pOrderStatusSynchDTO.getSbStatus()) && timeDiff > 1 && 
    			 (LtsConstant.DRG_ORDER_STATUS_DISTRIBUTED.equals(pOrderStatusSynchDTO.getBomLegacyStatus()) 
    			  && ! LtsConstant.SERVICE_TYPE_IMS.equals(pOrderStatusSynchDTO.getTypeOfSrv())
    					 || LtsConstant.DRG_ORDER_STATUS_DISTRIBUTED.equals(pOrderStatusSynchDTO.getL1OrdStatus())
    					    && LtsConstant.SERVICE_TYPE_IMS.equals(pOrderStatusSynchDTO.getTypeOfSrv()))
    			) 
    		{
            	return calendar.get(Calendar.DAY_OF_YEAR)      + "^"
            			+ (int)(calendar.get(Calendar.HOUR_OF_DAY)/6) + "^^"
    					+ pOrderStatusSynchDTO.getOrderId()    + "^"
    					+ pOrderStatusSynchDTO.getDtlId();   		
    			
    		} else { 
    		
    			return calendar.get(Calendar.DAY_OF_YEAR)    + "^"
    					+ calendar.get(Calendar.HOUR_OF_DAY) + "^"
    					+ pOrderStatusSynchDTO.getOrderId()  + "^"
    					+ pOrderStatusSynchDTO.getDtlId();
    		}
    	} else {
    		return null;
    	}
    }
	
	private void addToSummaryMap(
			Map<String, ArrayList<OrderStatusSynchDTO>> pSummaryMap,
			OrderStatusSynchDTO pOrderStatusSynchDTO) {
		ArrayList <OrderStatusSynchDTO> tmpAL= null; 
		if (pSummaryMap.containsKey(pOrderStatusSynchDTO.getOrderId())) {
			tmpAL = pSummaryMap.get(pOrderStatusSynchDTO.getOrderId());
			tmpAL.add(pOrderStatusSynchDTO);
		} else {
			tmpAL = new ArrayList<OrderStatusSynchDTO>();
			tmpAL.add(pOrderStatusSynchDTO);
			pSummaryMap.put(pOrderStatusSynchDTO.getOrderId(),tmpAL);
		}		
		
	}
	
	private OrderStatusSynchDTO getCorrLtsLineByOrderId(Map<String, ArrayList<OrderStatusSynchDTO>>  pSummaryMap, String pOrderId) throws Exception {
		
		OrderStatusSynchDTO ltsOrderStatusSynchDTO = null;
		
	    if (pSummaryMap != null) {
			ArrayList<OrderStatusSynchDTO> tmpAL = pSummaryMap.get(pOrderId);
			if (tmpAL != null) {
				for (OrderStatusSynchDTO orderStatusSynchDTO : tmpAL) {
					if (LtsConstant.SERVICE_TYPE_TEL.equals(orderStatusSynchDTO
							.getTypeOfSrv())) {
						ltsOrderStatusSynchDTO = orderStatusSynchDTO;
						break;
					}
				}
			}
		}
	    
	    //If it cannot be found in the map then ....
	    if (ltsOrderStatusSynchDTO == null) {
	    	//get it from from SB...
			OrderStatusSynchDTO [] ltsSbOrderStatusSynchDTO = orderStatusSynch.getRelatedTelLines(pOrderId);
			
			if (ltsSbOrderStatusSynchDTO != null && ltsSbOrderStatusSynchDTO.length > 0) {
				//then get the it again from BOM to get the GrpId and OcId
				OrderStatusSynchDTO [] ltsBomOrderStatusSynchDTO = bomOrderStatusSynch.getStatus(
						ltsSbOrderStatusSynchDTO[0].getOrderId(),
						ltsSbOrderStatusSynchDTO[0].getTypeOfSrv(),
						ltsSbOrderStatusSynchDTO[0].getSrvNum(),
						ltsSbOrderStatusSynchDTO[0].getCcServiceId(),
						ltsSbOrderStatusSynchDTO[0].getCcServiceMemNum(),
						null,
						null,
						ltsSbOrderStatusSynchDTO[0].getToProd());	
				if (ltsBomOrderStatusSynchDTO != null && ltsBomOrderStatusSynchDTO.length > 0) {
					ltsOrderStatusSynchDTO = ltsBomOrderStatusSynchDTO[0];
				}
			}
	    }
	    
		return ltsOrderStatusSynchDTO;		
	}
	
	/*
	private void clearAppointmentLts() {
		appointmentLts.setAppntEndTime(null);
		appointmentLts.setAppntStartTime(null);
		appointmentLts.setAppntType(null);
		appointmentLts.setBomAppntEndTime(null);
		appointmentLts.setBomAppntStartTime(null);
		appointmentLts.setCreateBy(null);
		appointmentLts.setCreateDate(null);
		appointmentLts.setCustContactFix(null);
		appointmentLts.setCustContactMobile(null);
		appointmentLts.setCutOverEndTime(null);
		appointmentLts.setCutOverStartTime(null);
		appointmentLts.setDelayReaCd(null);
		appointmentLts.setDtlId(null);
		appointmentLts.setExactAppntTime(null);
		appointmentLts.setForcedDelayInd(null);
		appointmentLts.setInstContactMobile(null);
		appointmentLts.setInstContactName(null);
		appointmentLts.setInstContactNum(null);
		appointmentLts.setInstSmsNum(null);
		appointmentLts.setLastUpdBy(null);
		appointmentLts.setLastUpdDate(null);
		appointmentLts.setOracleRowID(null);
		appointmentLts.setOrderId(null);
		appointmentLts.setPreWiringEndTime(null);
		appointmentLts.setPreWiringStartTime(null);
		appointmentLts.setPreWiringType(null);
		appointmentLts.setSerialNum(null);
		appointmentLts.setTidEndTime(null);
		appointmentLts.setTidInd(null);
		appointmentLts.setTidStartTime(null);
	}
	*/
	
    private int synchAppntDelayRea(OrderStatusSynchDTO orderStatusSynchDTO, OrderStatusSynchDTO [] bomOrderStatusSynchDTO, Date pNewSrvReqDate) throws Exception {
    	int updInsCnt = 0;
    	
		BeanUtils.copyProperties(appointmentLts, emptyAppointmentLts); //Clear the DAO
    	//clearAppointmentLts();  //Temp solution to the problem of the DAO.
    	
    	appointmentLts.setOrderId(orderStatusSynchDTO.getOrderId());
    	appointmentLts.setDtlId(orderStatusSynchDTO.getDtlId());
    	appointmentLts.setOracleRowID(null);
    	appointmentLts.setDelayReaCd(null);    	
    	appointmentLts.doSelect();
    	
    	logger.debug("Selected "  + appointmentLts.getOrderId() + "/Current DRC = " + appointmentLts.getDelayReaCd());
    	
    	if (StringUtils.isNotBlank(appointmentLts.getOracleRowID())) {

			List<BomOrderAppntDTO> bomOrderAppntList = bomOrderAppointment
					.getBomAppointment(bomOrderStatusSynchDTO[0].getOcId(),
							bomOrderStatusSynchDTO[0].getBomDtlId());
			for (BomOrderAppntDTO bomOrderAppntDTO : bomOrderAppntList) {
				
				/* FL 20170106 don't sync if last update date in SB is newer than BOM */
				if(appointmentLts.getLastUpdDateORACLE().toSqlDate().after(bomOrderAppntDTO.getLastUpdDate())){
					continue;
				}
				
				// Check only the 'TO' side and there is a change of value
				if ("T".equals(bomOrderAppntDTO.getToFromSide()) && 
					 (
						(!StringUtils.equals(appointmentLts.getDelayReaCd(),bomOrderAppntDTO.getDelayReaCd())                 || 
				 		 !StringUtils.equals(appointmentLts.getBomAppntStartTime(),bomOrderAppntDTO.getAppntStartTimeAsStr()) || 
						 !StringUtils.equals(appointmentLts.getBomAppntEndTime(),bomOrderAppntDTO.getAppntEndTimeAsStr())
						)
					 )
					) 
				{

					logger.debug("sb/bom " + appointmentLts.getOrderId() + "-"+ appointmentLts.getDelayReaCd() + "/"+ bomOrderAppntDTO.getDelayReaCd());

					if (StringUtils.isNotBlank(bomOrderAppntDTO.getDelayReaCd()) && 
							!bomOrderAppntDTO.getDelayReaCd().equals(appointmentLts.getDelayReaCd())) 
					{
						logger.debug("Creating " + LtsConstant.WQ_TRANS_ACTION_APPNT_DELAY + " for " + appointmentLts.getOrderId());						
						this.createWQTrans(orderStatusSynchDTO,
								LtsConstant.WQ_TRANS_ACTION_APPNT_DELAY,orderStatusSynchDTO.getTypeOfSrv() +  " - ("
										+ bomOrderAppntDTO.getDelayReaCd()
										+ ") "
										+ bomOrderAppntDTO.getDelayReaDesc());
					}					
										
					appointmentLts.setDelayReaCd(StringUtils
							.trimToNull(bomOrderAppntDTO.getDelayReaCd()));
					appointmentLts.setBomAppntStartTime(bomOrderAppntDTO.getAppntStartTimeAsStr());
					appointmentLts.setBomAppntEndTime(bomOrderAppntDTO.getAppntEndTimeAsStr());
					
					// Aug 13, 2014 - Instructed by Dick to directly update the appointment date
					// Apr 16, 2015 - Requested by Sam to exclude PIPB from synchronizing APPNT
					if (!orderStatusSynchDTO.isPIPB()) { 
						appointmentLts.setAppntStartTime(bomOrderAppntDTO.getAppntStartTimeAsStr());
						appointmentLts.setAppntEndTime(bomOrderAppntDTO.getAppntEndTimeAsStr());
					}
										
					appointmentLts.setLastUpdBy(LAST_UPD_BY);
					appointmentLts.doUpdate();

					updInsCnt++;
				}
			}
			
			// Aug 13, 2014 - Instructed by Dick to directly update the appointment date with the SRD if no appointment in BOM.
			// Apr 16, 2015 - Requested by Sam to exclude PIPB from synchronizing APPNT
			if (!orderStatusSynchDTO.isPIPB() && pNewSrvReqDate != null	&& (bomOrderAppntList == null || bomOrderAppntList.size() <= 0)) {
				logger.info("Updating the appointment start/end date with the SRD "  + appointmentLts.getOrderId());				
				appointmentLts.setAppntStartTime(dateFormatter
						.format(pNewSrvReqDate));
				appointmentLts.setAppntEndTime(dateFormatter
						.format(pNewSrvReqDate));

				appointmentLts.setLastUpdBy(LAST_UPD_BY);
				appointmentLts.doUpdate();
			}
		} else {
			logger.info("NO record found in BOMWEB_APPOINTMENT for "  + appointmentLts.getOrderId());
		}
    	return updInsCnt;
    }
	
    private void incrementOrderSuffix() {
    	orderSuffix++;    	
    	if (orderSuffix > 9) {
    		orderSuffix = 0;
    	}
    }
    
	public OrderStatusSynchDAO getOrderStatusSynch() {
		return orderStatusSynch;
	}

	public void setOrderStatusSynch(OrderStatusSynchDAO pOrderStatusSynch) {
		this.orderStatusSynch = pOrderStatusSynch;
	}
	

	public BomOrderStatusSynchDAO getBomOrderStatusSynch() {
		return bomOrderStatusSynch;
	}

	public void setBomOrderStatusSynch(BomOrderStatusSynchDAO pBomOrderStatusSynch) {
		this.bomOrderStatusSynch = pBomOrderStatusSynch;
	}

	public int getOrderAge() {
		return orderAge;
	}

	public void setOrderAge(int orderAge) {
		this.orderAge = orderAge;
	}
	
	public int getPipbOrderAge() {
		return pipbOrderAge;
	}

	public void setPipbOrderAge(int pipbOrderAge) {
		this.pipbOrderAge = pipbOrderAge;
	}

	public int getFifoMaxCache() {
		return fifoMaxCache;
	}

	public void setFifoMaxCache(int fifoMaxCache) {
		this.fifoMaxCache = fifoMaxCache;
	}

	public BomwebWqTransDAO getBomwebWqTrans() {
		return bomwebWqTrans;
	}

	public void setBomwebWqTrans(BomwebWqTransDAO bomwebWqTrans) {
		this.bomwebWqTrans = bomwebWqTrans;
	}

	/**
	 * @return the bomOrderAppointment
	 */
	public BomOrderAppointmentDAO getBomOrderAppointment() {
		return bomOrderAppointment;
	}

	/**
	 * @param bomOrderAppointment the bomOrderAppointment to set
	 */
	public void setBomOrderAppointment(BomOrderAppointmentDAO bomOrderAppointment) {
		this.bomOrderAppointment = bomOrderAppointment;
	}

	/**
	 * @return the appointmentLts
	 */
	public AppointmentLtsDAO getAppointmentLts() {
		return appointmentLts;
	}

	/**
	 * @param appointmentLts the appointmentLts to set
	 */
	public void setAppointmentLts(AppointmentLtsDAO appointmentLts) {
		this.appointmentLts = appointmentLts;
	}

	public CodeLkupCacheServiceImpl getL1ExemptCache() {
		return l1ExemptCache;
	}

	public void setL1ExemptCache(CodeLkupCacheServiceImpl exemptCache) {
		l1ExemptCache = exemptCache;
	}

	/**
	 * @return the bomOrdStatusLkupCache
	 */
	public CodeLkupCacheServiceImpl getBomOrdStatusLkupCache() {
		return bomOrdStatusLkupCache;
	}

	/**
	 * @param bomOrdStatusLkupCache the bomOrdStatusLkupCache to set
	 */
	public void setBomOrdStatusLkupCache(
			CodeLkupCacheServiceImpl bomOrdStatusLkupCache) {
		this.bomOrdStatusLkupCache = bomOrdStatusLkupCache;
	}



}
