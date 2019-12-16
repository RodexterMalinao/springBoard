package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;

import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.AmendCategoryLtsDTO;
import com.bomwebportal.lts.dto.order.AmendDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.wq.schema.dto.RemarkDTO;
import com.pccw.wq.schema.dto.WorkQueueAttributeDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.service.WorkQueueService;

public class WorkQueueSubmissionServiceImpl implements WorkQueueSubmissionService {

	private final Log logger = LogFactory.getLog(getClass());
	
	private Mapper dozerMapper;
	private WorkQueueService workQueueService;
	private CodeLkupCacheService delayReasonCodeLkupCacheService;
	private ImsSbOrderService imsSbOrderService;
	private OrderDetailService orderDetailService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	
	public void submitToWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser, String pTeamCd) {
		
		WorkQueueDTO resultWorkQueue = null;
		
		List<WorkQueueDTO> workQueueList = this.createOtherDelServiceToWorkQueue(pSbOrder, pSrvDtls, pSbOrder.getOrderId(), pUser, pTeamCd);
		
		WorkQueueDTO delSrvWorkQueue = this.createDelServiceToWorkQueue(pSbOrder, LtsSbHelper.getDelServices(pSbOrder), pSrvDtls, pSbOrder.getOrderId(), pUser, pSbOrder.getSalesTeam());
		
		if (delSrvWorkQueue != null) {
			workQueueList.add(delSrvWorkQueue);
		}
		
		WorkQueueDTO eyeSrvWorkQueue = this.createEyeServiceToWorkQueue(pSbOrder, LtsSbHelper.getLtsEyeService(pSbOrder), pSrvDtls, pSbOrder.getOrderId(), pUser, pSbOrder.getSalesTeam());

		if (eyeSrvWorkQueue != null) {
			workQueueList.add(eyeSrvWorkQueue);	
		}
		for (int i=0; i<workQueueList.size(); ++i) {
			if (ArrayUtils.isNotEmpty(workQueueList.get(i).getWorkQueueNatures())) {
				logger.debug("Submit to WQ begin. order id: " + pSbOrder.getOrderId());
				resultWorkQueue = this.submitToWorkQueue(workQueueList.get(i), pUser);
				logger.debug("Submit to WQ end. order id: " + pSbOrder.getOrderId());
				this.attachDocumentToWorkQueue(resultWorkQueue, pSbOrder, pUser);
			}
		}
	}
	
	public void submitToApprovalWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser, String pTeamCd) {
		
		for (int i=0; pSrvDtls!=null && i<pSrvDtls.length; ++i) {
			
			WorkQueueDTO workQueue = this.createWorkQueueDTOByService(pSbOrder, pSrvDtls[i], pSbOrder.getOrderId(), pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());
			List<ServiceActionTypeDTO> actionList = new ArrayList<ServiceActionTypeDTO>();
			WorkQueueDTO resultWorkQueue = null;
			
			
			if (ArrayUtils.isNotEmpty(pSrvDtls[i].getSrvActions())) {
				actionList.addAll(Arrays.asList(pSrvDtls[i].getSrvActions()));
			}
			
			if (actionList.isEmpty()) {
				return;
			}
			
			workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(actionList.toArray(new ServiceActionTypeDTO[actionList.size()])));
			
			if (ArrayUtils.isNotEmpty(workQueue.getWorkQueueNatures())) {
				resultWorkQueue = this.submitToWorkQueue(workQueue, pUser);
				this.attachDocumentToWorkQueue(resultWorkQueue, pSbOrder, pUser);
			}
		}
		
		
	}
	
	public void submitToPendingOrderWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser, String pTeamCd) {
		
		WorkQueueDTO primarySrvWorkQueue = this.createPrimaryServiceToWorkQueuePending(pSbOrder, pSrvDtls, pUser, pSbOrder.getSalesTeam());
		List<WorkQueueDTO> workQueueList = this.createOtherDelServiceToWorkQueuePending(pSbOrder, pSrvDtls, pSbOrder.getOrderId(), pUser, pSbOrder.getSalesTeam());
		
		if (primarySrvWorkQueue != null) {
			workQueueList.add(primarySrvWorkQueue);
		}
		for (int i=0; i<workQueueList.size(); ++i) {
			if (ArrayUtils.isNotEmpty(workQueueList.get(i).getWorkQueueNatures())) {
				this.submitToWorkQueue(workQueueList.get(i), pUser);
			}
		}
	}
	
	public void submitToDsPrepaymentWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser) {
		logger.warn("WorkQueueSubmissionService : submitToDsPrepaymentWorkQueue [STARTS] ");
		for (int i=0; pSrvDtls!=null && i<pSrvDtls.length; ++i) {	
			WorkQueueDTO workQueue = this.createWorkQueueDTOByService(pSbOrder, pSrvDtls[i], pSbOrder.getOrderId(), pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());
			List<ServiceActionTypeDTO> actionList = new ArrayList<ServiceActionTypeDTO>();
			
			if (ArrayUtils.isNotEmpty(pSrvDtls[i].getSrvActions())) {
				actionList.addAll(Arrays.asList(pSrvDtls[i].getSrvActions()));
			}
			
			if (actionList.isEmpty()) {
				logger.warn("ServiceActionTypeDTO list is empty!! ");
				return;
			}
			
			workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(actionList.toArray(new ServiceActionTypeDTO[actionList.size()])));
			
			if (ArrayUtils.isNotEmpty(workQueue.getWorkQueueNatures())) {
				logger.warn("WorkQueueSubmissionService : submitToWorkQueue [STARTS] ");
				this.submitToWorkQueue(workQueue, pUser);
				logger.warn("WorkQueueSubmissionService : submitToWorkQueue [END] ");
			}
		}
		logger.warn("WorkQueueSubmissionService : submitToDsPrepaymentWorkQueue [END] ");
	}
	
	public void submitDsWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser) {
		logger.warn("WorkQueueSubmissionService : submitDsWorkQueue [STARTS] ");
		for (int i=0; pSrvDtls!=null && i<pSrvDtls.length; ++i) {	
			WorkQueueDTO workQueue = this.createWorkQueueDTOByService(pSbOrder, pSrvDtls[i], pSbOrder.getOrderId(), pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());
			List<ServiceActionTypeDTO> actionList = new ArrayList<ServiceActionTypeDTO>();
			
			if (ArrayUtils.isNotEmpty(pSrvDtls[i].getSrvActions())) {
				actionList.addAll(Arrays.asList(pSrvDtls[i].getSrvActions()));
			}
			
			if (actionList.isEmpty()) {
				logger.warn("ServiceActionTypeDTO list is empty!! ");
				return;
			}
			
			workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(actionList.toArray(new ServiceActionTypeDTO[actionList.size()])));
			
			if (ArrayUtils.isNotEmpty(workQueue.getWorkQueueNatures())) {
				logger.warn("WorkQueueSubmissionService : submitDsWorkQueue [STARTS] ");
				this.submitToWorkQueue(workQueue, pUser);
				logger.warn("WorkQueueSubmissionService : submitDsWorkQueue [END] ");
			}
		}
		logger.warn("WorkQueueSubmissionService : submitDrgDownTimeWorkQueue [END] ");
	}
	
	public WorkQueueDTO createPrimaryServiceToWorkQueuePending(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser, String pTeamCd) {
		
		ServiceDetailLtsDTO srvDtlLts = LtsSbHelper.getLtsService(pSbOrder);
		
		if (srvDtlLts == null) {
			return null;
		}

		ServiceDetailOtherLtsDTO srvDtlIms = LtsSbHelper.getImsEyeService(pSbOrder.getSrvDtls());
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		List<RemarkDTO> remarkList = new ArrayList<RemarkDTO>();
		
		WorkQueueDTO workQueue = null;
		
		if ((StringUtils.isNotEmpty(srvDtlLts.getPendingOcid()) && ArrayUtils.isNotEmpty(srvDtlLts.getSrvActions()))) {
			workQueue = this.createWorkQueueDTOByService(pSbOrder, srvDtlLts, pSbOrder.getOrderId(), pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());
			if (srvDtlLts.getSrvActions() != null) {
				srvActionList.addAll(Arrays.asList(srvDtlLts.getSrvActions()));	
			}
			remarkList.addAll(Arrays.asList(this.createRemarks(new String[] {"LTS service under pending OCID " + srvDtlLts.getPendingOcid()}, null)));
		}
		
		if (srvDtlIms != null) {
			if ((StringUtils.isNotEmpty(srvDtlIms.getPendingOcid()) && ArrayUtils.isNotEmpty(srvDtlIms.getSrvActions()))) {
				if (workQueue == null) {
					workQueue = this.createWorkQueueDTOByService(pSbOrder, srvDtlLts, pSbOrder.getOrderId(), pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());	
				}
				
				if (srvDtlIms.getSrvActions() != null) {
					srvActionList.addAll(Arrays.asList(srvDtlIms.getSrvActions()));	
				}
				workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()])));
				remarkList.addAll(Arrays.asList(this.createRemarks(new String[] {"IMS service under pending OCID " + srvDtlIms.getPendingOcid()}, null)));
			}	
		}
		
		
		if (workQueue != null) {
			workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()])));
			workQueue.setRemarks(remarkList.toArray(new RemarkDTO[remarkList.size()]));	
		}
		
		
		return workQueue;
	}
	
	private List<WorkQueueDTO> createOtherDelServiceToWorkQueuePending(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pOrderId, String pUser, String pTeamCd) {
		
		List<WorkQueueDTO> workQueueList = new ArrayList<WorkQueueDTO>();
		WorkQueueDTO workQueue = null;
		ServiceDetailDTO ltsPrimarySrv = LtsSbHelper.getLtsService(pSbOrder);
		for (int i=0; i<pSrvDtls.length; ++i) {
			if (!(pSrvDtls[i] instanceof ServiceDetailLtsDTO) 
					|| StringUtils.equals(ltsPrimarySrv.getDtlId(), pSrvDtls[i].getDtlId())
					|| StringUtils.isEmpty(pSrvDtls[i].getPendingOcid())) {
				continue;
			}
			
			if (ArrayUtils.isEmpty(pSrvDtls[i].getSrvActions())) {
				continue;
			}
			
			workQueue = this.createWorkQueueDTOByService(pSbOrder, pSrvDtls[i], pOrderId, pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());
			workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(pSrvDtls[i].getSrvActions()));
			workQueue.setRemarks(this.createRemarks(new String[] {"LTS service under pending OCID " + pSrvDtls[i].getPendingOcid()}, null));
			workQueueList.add(workQueue);
		}
		return workQueueList;
	}	
	
	public void clearWorkQueue(SbOrderDTO pSbOrder, String pAction, String pUser) {
		
		try {
			this.workQueueService.changeWqStatusBySbAction(pSbOrder.getOrderId(), pAction, pUser);
		} catch (Exception ex) {
			logger.error("Fail to clear follow up Work Queue.");
			throw new AppRuntimeException("Fail to clear follow up Work Queue.", ex);
		}
	}
	
	private WorkQueueDTO submitToWorkQueue(WorkQueueDTO pWorkQueue, String pUser) {
		
		try {
			return this.workQueueService.createWorkQueue(pWorkQueue, pWorkQueue.getWorkQueueNatures(), pUser);
		} catch (Exception ex) {
			logger.error("Fail to submit to Work Queue.");
			throw new AppRuntimeException("Fail to submit to Work Queue.", ex);
		}
	}
	
	// Set Duplex DN to Related Service Num for DN Change WQ 
	private void setDuplexRelatedSrvNum(WorkQueueDTO workQueue, SbOrderDTO sbOrder, ServiceDetailLtsDTO pPrimarySrv) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (StringUtils.equals(pPrimarySrv.getDtlId(), serviceDetail.getDtlId())
					|| ! (serviceDetail instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			if (LtsBackendConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())
					|| LtsBackendConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())) {
				if (StringUtils.isNotEmpty(serviceDetail.getNewSrvNum())) {
					workQueue.setRelatedSrvNum(serviceDetail.getSrvNum());
					workQueue.setRelatedSrvType(serviceDetail.getTypeOfSrv());
					return;
				}
			}
		}
	}
	
	private WorkQueueDTO createDelServiceToWorkQueue(SbOrderDTO pSbOrder, ServiceDetailLtsDTO pPrimarySrv, ServiceDetailDTO[] pSrvDtls, String pOrderId, String pUser, String pTeamCd) {
		
		if (pPrimarySrv == null || ArrayUtils.isEmpty(pPrimarySrv.getSrvActions())) {
			return null;
		}
		
		WorkQueueDTO workQueue = this.createWorkQueueDTOByService(pSbOrder, pPrimarySrv, pOrderId, pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());
		
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		srvActionList.addAll(Arrays.asList(pPrimarySrv.getSrvActions()));
		this.setDuplexRelatedSrvNum(workQueue, pSbOrder, pPrimarySrv);
		this.setWorkQueueRemarks(workQueue, pPrimarySrv);
		workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()])));
		return workQueue;
	}
	
	
	private WorkQueueDTO createEyeServiceToWorkQueue(SbOrderDTO pSbOrder, ServiceDetailLtsDTO pPrimarySrv, ServiceDetailDTO[] pSrvDtls, String pOrderId, String pUser, String pTeamCd) {
		
		if (pPrimarySrv == null) {
			return null;
		}
		
		ServiceDetailLtsDTO srvDtlLts = LtsSbHelper.getLtsEyeService(pSbOrder);
		ServiceDetailOtherLtsDTO srvDtlIms = LtsSbHelper.getImsEyeService(pSrvDtls);
		WorkQueueDTO workQueue = this.createWorkQueueDTOByService(pSbOrder, pPrimarySrv, pOrderId, pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());
		List<ServiceActionTypeDTO> srvActionList = new ArrayList<ServiceActionTypeDTO>();
		
		if (srvDtlIms != null) {
			if (ArrayUtils.isNotEmpty(srvDtlIms.getSrvActions())) {
				srvActionList.addAll(Arrays.asList(srvDtlIms.getSrvActions()));	
			}
		} else {
			srvDtlIms = LtsSbHelper.getImsEyeService(pSbOrder.getSrvDtls());
		}
		if (srvDtlIms != null) {
			this.setWorkQueueRemarks(workQueue, srvDtlIms);
			
			if (StringUtils.isEmpty(srvDtlIms.getRelatedSbOrderId())) {
				workQueue.setRelatedSrvNum(srvDtlIms.getSrvNum());	
			} else {
				workQueue.setRelatedSrvNum(this.imsSbOrderService.getFsaNumOnImsSbOrder(srvDtlIms.getRelatedSbOrderId()));	
			}
			workQueue.setRelatedSrvType(srvDtlIms.getTypeOfSrv());
		}
		if (srvDtlLts != null && ArrayUtils.isNotEmpty(srvDtlLts.getSrvActions())) {
			srvActionList.addAll(Arrays.asList(srvDtlLts.getSrvActions()));
			this.setWorkQueueRemarks(workQueue, srvDtlLts);
		}
		// TEMP solution to handle sip service
		ServiceDetailLtsDTO sipService = LtsSbHelper.getEyeSipService(pSbOrder.getSrvDtls());
		
		if (sipService != null && ! StringUtils.equals(sipService.getDtlId(), pPrimarySrv.getDtlId())) {
			this.setWorkQueueRemarks(workQueue, sipService);
			if (ArrayUtils.isNotEmpty(sipService.getSrvActions())) {
				srvActionList.addAll(Arrays.asList(sipService.getSrvActions()));	
			}
			
		}
		
		if (srvActionList.isEmpty()) {
			return null;
		}
		
		// TEMP solution to handle sip service
		workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(srvActionList.toArray(new ServiceActionTypeDTO[srvActionList.size()])));
		return workQueue;
	}
	
	private void setWorkQueueRemarks(WorkQueueDTO pWorkQueue, ServiceDetailDTO pSrvDtl) {
		
		RemarkDTO[] remarks = this.createRemarks(pSrvDtl.getWqRemarks(), null);
		
		for (int i=0; remarks!=null && i<remarks.length; ++i) {
			pWorkQueue.addRemark(remarks[i]);
		}
	}
	
	private List<WorkQueueDTO> createOtherDelServiceToWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pOrderId, String pUser, String pTeamCd) {
		
		List<WorkQueueDTO> workQueueList = new ArrayList<WorkQueueDTO>();
		
		WorkQueueDTO workQueue = null;
		ServiceDetailLtsDTO srvDtlLts = null;
		
		ServiceDetailDTO ltsPrimaryService = LtsSbHelper.getLtsService(pSbOrder);
		for (int i=0; i<pSrvDtls.length; ++i) {
			if (!(pSrvDtls[i] instanceof ServiceDetailLtsDTO) 
					|| StringUtils.equals(ltsPrimaryService.getDtlId(), pSrvDtls[i].getDtlId())
					|| LtsBackendConstant.LTS_SRV_TYPE_SIP.equals(((ServiceDetailLtsDTO)pSrvDtls[i]).getFromSrvType())) {
				continue;
			}
			
			if (ArrayUtils.isEmpty(pSrvDtls[i].getSrvActions())) {
				continue;
			}
			
			pSrvDtls[i].setWqRemarks(pSrvDtls[i].remarkToArray(LtsBackendConstant.REMARK_TYPE_2ND_DEL));
			workQueue = this.createWorkQueueDTOByService(pSbOrder, pSrvDtls[i], pOrderId, pSbOrder.getSalesTeam(), pSbOrder.getStaffNum());
			workQueue.setWorkQueueNatures(this.createWorkQueueNatureSrvActions(pSrvDtls[i].getSrvActions()));
			workQueueList.add(workQueue);
			
			if (StringUtils.equals(LtsBackendConstant.LTS_PRODUCT_TYPE_2DEL, pSrvDtls[i].getToProd())) {
				srvDtlLts = LtsSbHelper.getLtsService(pSbOrder);
			}
			if (srvDtlLts != null) {
				workQueue.setRelatedSrvNum(srvDtlLts.getSrvNum());
				workQueue.setRelatedSrvType(srvDtlLts.getTypeOfSrv());
			}
		}
		return workQueueList;
	}
	
	public void attachDocumentToWorkQueue(WorkQueueDTO pWorkQueueResult, SbOrderDTO pSbOrder, String pUser) {
		
		WorkQueueDocumentDTO[] wqDocs = pWorkQueueResult.getDocuments();
		
		if (ArrayUtils.isEmpty(wqDocs)) {
			return;
		}
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(LtsBackendConstant.RPT_KEY_SBORDER, pSbOrder);
		inputMap.put(LtsBackendConstant.RPT_KEY_LOB, LtsBackendConstant.LOB_LTS);
		String action = null;
		
		for (int i=0; i<wqDocs.length; ++i) {
			if (StringUtils.equals(LtsBackendConstant.RPT_TYPE_EYE_AF, wqDocs[i].getReportName())
					|| StringUtils.equals(LtsBackendConstant.RPT_TYPE_EYE_AF_APR, wqDocs[i].getReportName())) {
				if (LtsSbHelper.getLtsEyeService(pSbOrder) == null) {
					action = ReportCreationLtsService.RPT_SET_WQ_DEL_AF;	
				}
				else {
					action = ReportCreationLtsService.RPT_SET_WQ_EYE_AF;
				}
			} else if (StringUtils.equals(LtsBackendConstant.RPT_TYPE_2ND_DEL_AF, wqDocs[i].getReportName())
						|| StringUtils.equals(LtsBackendConstant.RPT_TYPE_2ND_DEL_AF_APR, wqDocs[i].getReportName())) {
				action = ReportCreationLtsService.RPT_SET_WQ_2DEL_AF;
			} else if (StringUtils.equals(LtsBackendConstant.RPT_TYPE_PORT_IN, wqDocs[i].getReportName())) {
				action = ReportCreationLtsService.RPT_SET_WQ_PORT_IN_FORM;
			} else if (StringUtils.equals(LtsBackendConstant.RPT_TYPE_APP_EYE_AF, wqDocs[i].getReportName())) {
				action = ReportCreationLtsService.RPT_SET_APP_EYE_AF;
			} else if (StringUtils.equals(LtsBackendConstant.RPT_TYPE_APP_DEL_AF, wqDocs[i].getReportName())) {
				action = ReportCreationLtsService.RPT_SET_APP_DEL_AF;
			} else if (StringUtils.equals(LtsBackendConstant.RPT_TYPE_SUPPORT_DOC, wqDocs[i].getReportName())) {
				action = ReportCreationLtsService.RPT_SET_SUPPORT_DOC;
			} else {
				action = ReportCreationLtsService.RPT_SET_SUPPORT_DOC;
				inputMap.put(LtsBackendConstant.RPT_TYPE_SUPPORT_DOC, wqDocs[i].getReportName());
			}
			
			ReportSetDTO rptSet = new ReportSetDTO();
			rptSet.setLob(LtsBackendConstant.LOB_LTS);
			rptSet.setChannelId(pSbOrder.getOrderId().substring(0,1));
			rptSet.setRptSet(action);
			rptSet = (ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey());
			if (rptSet != null) {
				if (LtsSbHelper.isNewAcquistionOrder(pSbOrder)) {
					// SBA, force re-generate
					rptSet.setReGen(true);
				}
				ReportOutputDTO rptOut = this.reportCreationLtsService.generateReport(rptSet, inputMap);
				if (rptOut != null && StringUtils.isNotEmpty(rptOut.getFileStoragePath())
						&& rptOut.getOutputFileStream() != null && rptOut.getOutputFileStream().getSize() != 0) {
					wqDocs[i].setAttachmentFullPath(rptOut.getFileStoragePath());	
				}
			}
			
//			if (LtsBackendConstant.RPT_TYPE_RECONTRACT.equals(wqDocs[i].getReportName())) {
//				if (StringUtils.startsWith(pSbOrder.getOrderId(), "P")
//						&& StringUtils.equalsIgnoreCase("Y", ltsCoreService.getRecontractInd())) {
//					List<AllOrdDocDTO> allOrdDocList = ltsOrderDocumentService.getAllOrdDocListByOrderId(pSbOrder.getOrderId());
//					
//					if (allOrdDocList != null && !allOrdDocList.isEmpty()) {
//						for (AllOrdDocDTO allOrdDoc : allOrdDocList) {
//							if (LtsBackendConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED.equals(allOrdDoc.getDocType())) {
//								String fullPath = dataFilePath + "/" + pSbOrder.getOrderId() + "/" + allOrdDoc.getFilePathName();
//								wqDocs[i].setAttachmentFullPath(fullPath);
//								break;
//							}
//						}
//					}
//				}
//			}
		}
		try {
			logger.debug("Attach doc to WQ begin. order id: " + pSbOrder.getOrderId());
			this.workQueueService.attachWorkQueueDocument(pWorkQueueResult, pWorkQueueResult.getDocuments(), pUser);
			logger.debug("Attach doc to WQ end. order id: " + pSbOrder.getOrderId());
		} catch (Exception ex) {
			logger.error("Fail to attach document to Work Queue.");
			throw new AppRuntimeException("Fail to attach document to Work Queue.", ex);
		}
	}
	
	private WorkQueueDTO createWorkQueueDTOByService(SbOrderDTO pSbOrder, ServiceDetailDTO pSrvDtl, String pOrderId, String pTeamCd, String pSalesNum) {
		
		WorkQueueDTO workQueueDtl = new WorkQueueDTO();
		workQueueDtl.setSbDtlId(pSrvDtl.getDtlId());
		workQueueDtl.setSbId(pOrderId);
		workQueueDtl.setSbShopCode(pSbOrder.getSalesTeam());
		workQueueDtl.setServiceId(pSrvDtl.getSrvNum());
		workQueueDtl.setSrd(LtsDateFormatHelper.dateConvertFromDTO2DAONoTime(pSrvDtl.getAppointmentDtl().getAppntStartTime()));
		workQueueDtl.setTypeOfService(pSrvDtl.getTypeOfSrv());
		workQueueDtl.setRemarks(this.createRemarks(pSrvDtl.getWqRemarks(), null));
		workQueueDtl.setBomOcId(pSbOrder.getOcid());
		
		String wqAttbLtsFromProd = pSrvDtl.getFromProd();
		if(LtsSbHelper.getAcqPipbService(pSbOrder.getSrvDtls()) != null){
			wqAttbLtsFromProd = "NEW-PIPB";
		}
		
		workQueueDtl.setAttributes(new WorkQueueAttributeDTO[] {
				this.createWorkQueueAttb("SB_ISSUE_BY", pSalesNum), 
				this.createWorkQueueAttb("LTS_FROM_PROD", wqAttbLtsFromProd), 
				this.createWorkQueueAttb("LTS_TO_PROD", pSrvDtl.getToProd())});
		return workQueueDtl;
	}
	
	private WorkQueueNatureDTO[] createWorkQueueNatureSrvActions(ServiceActionTypeDTO[] pSrvActions) {
		
		Map<String,WorkQueueNatureDTO> natureMap = new HashMap<String,WorkQueueNatureDTO>();
		Map<String,List<String>> natureRemarkMap = new HashMap<String,List<String>>();
		WorkQueueNatureDTO nature = null;
		String[] remarkContents = null;
		List<String> remarkList = null;
		String key = null;
		
		for (int i=0; pSrvActions!=null && i<pSrvActions.length; ++i) {
			key = pSrvActions[i].getWqType() + "|" + pSrvActions[i].getWqSubtype() + "|" + pSrvActions[i].getWqNatureId();
			
			if (StringUtils.isNotEmpty(pSrvActions[i].getWqNatureId()) && !natureMap.containsKey(key)) {
				nature = new WorkQueueNatureDTO();
				this.dozerMapper.map(pSrvActions[i], nature);
				remarkContents = pSrvActions[i].getWqNatureRemarks();
				remarkList = new ArrayList<String>();
				natureRemarkMap.put(key, remarkList);
				
				for (int j=0; remarkContents!=null && j<remarkContents.length; ++j) {
					remarkList.add(remarkContents[j]);
				}					
				natureMap.put(key, nature);
			} else {
				remarkList = natureRemarkMap.get(key);
				remarkContents = pSrvActions[i].getWqNatureRemarks();
				
				for (int j=0; remarkContents!=null && j<remarkContents.length; ++j) {
					remarkList.add(remarkContents[j]);
				}	
			}
		}
		Iterator<String> it = natureRemarkMap.keySet().iterator();
		
		while (it.hasNext()) {
			key = it.next();
			nature = natureMap.get(key);
			nature.setRemarks(this.createRemarks(natureRemarkMap.get(key).toArray(new String[0]), nature.getWorkQueueNatureId()));
		}
		return natureMap.values().toArray(new WorkQueueNatureDTO[natureMap.size()]);
	}
	
	private RemarkDTO[] createRemarks(String[] pRemarkContents, String pNatureId) {
		
		if (ArrayUtils.isEmpty(pRemarkContents)) {
			return null;
		}
		RemarkDTO[] remarks = new RemarkDTO[pRemarkContents.length];		
		
		for (int i=0; i < pRemarkContents.length; ++i) {
			if (StringUtils.isBlank(pRemarkContents[i])) {
				continue;
			}
			remarks[i] = new RemarkDTO();
			remarks[i].setRemarkContent(pRemarkContents[i]);
			remarks[i].setRemarkSequence(String.valueOf(i));
			remarks[i].setRemarkNatureId(pNatureId);
		}
		return remarks;
	}
	
	public void submitAmendmentToWorkQueue(AmendLtsDTO pAmend, String pUser, String pTeamCd) {
	
		AmendDetailLtsDTO[] amendDtls = pAmend.getAmendDtls();
		WorkQueueDTO workQueue = null;
		WorkQueueDTO resultWorkQueue = null;
		AmendCategoryLtsDTO[] categories = null;
		
		for (int i=0; amendDtls!=null && i<amendDtls.length; ++i) {
			workQueue = this.createAmendWorkQueue(pAmend, amendDtls[i], pTeamCd, pUser);
			categories = amendDtls[i].getCategories();
			
			for (int j=0; categories!=null && j<categories.length; ++j) {
				workQueue.setSrd(LtsDateFormatHelper.dateConvertFromDTO2DAONoTime(categories[j].getSrd()));
				workQueue.addWorkQueueNature(this.createAmendWorkQueueNature(categories[j], pAmend.getShopCd(), amendDtls[i].getGrpType()));
			}
			resultWorkQueue = this.submitToWorkQueue(workQueue, pUser);
			//max.r.meng
			String[] requiredBatchIds = null;
			try {
				requiredBatchIds = OracleSelectHelper.getSqlFirstColumnStrings("BomWebPortalDS", 
						" Select WQ_BATCH_ID From Q_Wq_Wp_Search_V Where Wq_Wp_Assgn_Id In ("
						+ StringUtils.join(resultWorkQueue.getCreatedWpWqAssignIds(), ",")
						+ " ) And Status_Cd Not In "
						+ " (Select Code From Q_Dic_Code_Lkup "
						+ " Where Grp_Id = 'WQ_ENDING_STATUS')");
			} catch (Exception e) {
				requiredBatchIds =  StringUtils.split(resultWorkQueue.getCreatedBatchId(), ",");
			}
			for(String batchId: requiredBatchIds){
				this.saveAmendCoverSheet(pAmend.getOrderId(), resultWorkQueue.getWqId(), batchId, pUser);
			}
			
			//this.saveAmendCoverSheet(pAmend.getOrderId(), resultWorkQueue.getWqId(), resultWorkQueue.getCreatedBatchId(), pUser);
		}
	}
	
	private WorkQueueDTO createAmendWorkQueue(AmendLtsDTO pAmend, AmendDetailLtsDTO pAmendDtl, String pTeamCd, String pUser) {
		
		WorkQueueDTO amendWq = new WorkQueueDTO();
		amendWq.setSbId(pAmend.getOrderId());
		amendWq.setBomOcId(pAmend.getOcid());
		amendWq.setSbDtlId(pAmendDtl.getDtlId());
		amendWq.setSbShopCode(pTeamCd);
		amendWq.setServiceId(pAmendDtl.getSrvNum());
		amendWq.setTypeOfService(pAmendDtl.getSrvType());		
		
		String wqAttbLtsFromProd = pAmendDtl.getFromProd();
		if(pAmend.isPipbOrder()){
			wqAttbLtsFromProd = "NEW-PIPB";
		}
		
		amendWq.setAttributes(new WorkQueueAttributeDTO[] {
				this.createWorkQueueAttb("SB_ISSUE_BY", pUser), 
				this.createWorkQueueAttb("LTS_FROM_PROD", wqAttbLtsFromProd), 
				this.createWorkQueueAttb("LTS_TO_PROD", pAmendDtl.getToProd())});
		amendWq.setRelatedSrvNum(pAmendDtl.getRelatedSrvNum());
		amendWq.setRelatedSrvType(pAmendDtl.getRelatedSrvType());
		amendWq.setRemarks(this.createRemarks(pAmend.remarkToStringArray(), null));
		return amendWq;
	}
	
	private WorkQueueAttributeDTO createWorkQueueAttb(String pAttbName, String pAttbValue) {
		
		WorkQueueAttributeDTO attb = new WorkQueueAttributeDTO();
		attb.setAttbName(pAttbName);
		attb.setAttbValue(pAttbValue);
		return attb;
	}
	
	private WorkQueueNatureDTO createAmendWorkQueueNature(AmendCategoryLtsDTO pCategory, String pShopCd, String grpType) {
		
		WorkQueueNatureDTO nature = new WorkQueueNatureDTO();
		if (LtsBackendConstant.OLS_SHOP_CD.equals(pShopCd)) {
			if(!LtsBackendConstant.GROUP_TYPE_EYE.equals(grpType)){
				nature.setWorkQueueType(LtsBackendConstant.WQ_TYPE_AMEND_OLS_DEL);
				nature.setWorkQueueSubType(LtsBackendConstant.WQ_SUB_TYPE_AMEND);
			} else {
				nature.setWorkQueueType(LtsBackendConstant.WQ_TYPE_AMEND_OLS_EYE);
				nature.setWorkQueueSubType(LtsBackendConstant.WQ_SUB_TYPE_AMEND);
			}
		} else {
			nature.setWorkQueueType(LtsBackendConstant.WQ_TYPE_AMEND_SB_LTS);
			nature.setWorkQueueSubType(LtsBackendConstant.WQ_SUB_TYPE_AMEND);
		}
		nature.setWorkQueueNatureId(pCategory.getNature());
		nature.setRemarks(this.createRemarks(pCategory.remarkToStringArray(), pCategory.getNature()));
		return nature;
	}
	
	private void saveAmendCoverSheet(String pOrderId, String pWqId, String pBatchId, String pUser) {
		
		try {
			List<byte[]> fileList = this.workQueueService.getWqCoverSheets(pWqId, pBatchId);
		
			if (fileList == null || fileList.size() == 0) {
				return;
			}
			int seq = this.orderDetailService.getNextDocSeq(pOrderId, LtsBackendConstant.ORDER_DOC_TYPE_ORDER_AMEND_FORM);
			ReportSetDTO rptSet = new ReportSetDTO();
			rptSet.setLob(LtsBackendConstant.LOB_LTS);
			rptSet.setChannelId(pOrderId.substring(0,1));
			rptSet.setRptSet(ReportCreationLtsService.RPT_SET_AMEND_COVER);
			rptSet = (ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey());
		
			for (int i=0; i<fileList.size(); ++i) {
				this.reportCreationLtsService.saveReportDocFile(rptSet, pOrderId, seq, fileList.get(i), pUser);
				seq++;
			}
		} catch (Exception ex) {
			logger.error("Fail to saveAmendCoverSheet.");
			throw new AppRuntimeException("Fail to saveAmendCoverSheet.", ex);
		}
	}
	
	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}

	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}

	public Mapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(Mapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	public CodeLkupCacheService getDelayReasonCodeLkupCacheService() {
		return this.delayReasonCodeLkupCacheService;
	}

	public void setDelayReasonCodeLkupCacheService(
			CodeLkupCacheService pDelayReasonCodeLkupCacheService) {
		this.delayReasonCodeLkupCacheService = pDelayReasonCodeLkupCacheService;
	}

	public ImsSbOrderService getImsSbOrderService() {
		return this.imsSbOrderService;
	}

	public void setImsSbOrderService(ImsSbOrderService pImsSbOrderService) {
		this.imsSbOrderService = pImsSbOrderService;
	}

	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	public ReportCreationLtsService getReportCreationLtsService() {
		return reportCreationLtsService;
	}

	public void setReportCreationLtsService(
			ReportCreationLtsService reportCreationLtsService) {
		this.reportCreationLtsService = reportCreationLtsService;
	}

	public CodeLkupCacheService getReportSetLkupCacheService() {
		return reportSetLkupCacheService;
	}

	public void setReportSetLkupCacheService(
			CodeLkupCacheService reportSetLkupCacheService) {
		this.reportSetLkupCacheService = reportSetLkupCacheService;
	}
	
}
