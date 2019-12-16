package com.bomwebportal.lts.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bomwebportal.lts.dto.OutstandingWQItemDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.WorkQueueSubmissionServiceImpl;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.service.WorkQueueDataService;
import com.pccw.wq.service.WorkQueueService;

public class LtsWQRedispatchJob extends QuartzJobBean {

	private Log logger;
	private WorkQueueDataService workQueueDataService;
	private WorkQueueService workQueueService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private WorkQueueSubmissionServiceImpl workQueueSubmissionServiceImpl;

	private static final String SQL_GET_OUTSTANDING_WQ = 
					"SELECT qna.wq_batch_id \"wqBatchId\", qna.wq_id \"wqId\", qwq.sb_id \"sbId\" " + 
					"FROM q_wq_wq_nature_assgn qna, " + 
					"  q_work_queue qwq " + 
					"WHERE qna.dispatch_wq_wp_assgn_id IS NULL " + 
					"AND qna.wq_type                   IN ('SB-EYE', 'SB-DEL') " + 
					"AND (qwq.sb_id LIKE '____D%' OR qwq.sb_id LIKE '____E%') " + 
					"AND qna.wq_id                 = qwq.wq_id " + 
					"AND qna.wq_nature_id > 0 " + 
					"AND qna.wq_batch_id >=0 " +
					"AND TRUNC(qna.LAST_UPD_date) >= TO_DATE ( " + 
					"  (SELECT description " + 
					"  FROM w_code_lkup " + 
					"  WHERE grp_id = 'LTS_WQ_REDISPATCH' " + 
					"  AND CODE     = 'START_DATE' " + 
					"  ), 'DD/MM/YYYY HH24:MI:SS') ";
	private static final String SQL_GET_HOST_NAME = "SELECT description " + 
			"FROM w_code_lkup " + 
			"WHERE grp_id = 'LTS_WQ_REDISPATCH' " + 
			"AND CODE     = 'HOST_NAME'";


	@Override
	protected void executeInternal(JobExecutionContext pArg0) throws JobExecutionException {
		
		try {
			String hostname = OracleSelectHelper.getSqlFirstRowColumnString("BomWebPortalDS", SQL_GET_HOST_NAME);
			if (!InetAddress.getLocalHost().getHostName().equals(hostname))
				return;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		logger = LogFactory.getLog(getClass());
		logger.info("Workqueue redispatch job start");
		
		OutstandingWQItemDTO[] items=null;
		try {
			items = OracleSelectHelper.getSqlResultObjects("BomWebPortalDS", SQL_GET_OUTSTANDING_WQ, new Object[] {}, OutstandingWQItemDTO.class);

			for (OutstandingWQItemDTO item : items) {
				logger.info("begin to redispatch sbId = " + item.getSbId());
				workQueueDataService.dispatchWorkQueue(item.getWqId(), item.getWqBatchId(), null, "SYSTEM");
				
				//setup param needed
				WorkQueueDTO wqDto = new WorkQueueDTO();
				wqDto.setWqId(item.getWqId());
				wqDto.setDocuments(workQueueService.getWorkQueueRequiredDocuments(wqDto, "SYSTEM"));

				SbOrderDTO sbOrderDTO = orderRetrieveLtsService.retrieveSbOrder(item.getSbId(), false);
				workQueueSubmissionServiceImpl.attachDocumentToWorkQueue(wqDto, sbOrderDTO, "SYSTEM");
			}

		} catch (Exception e) {
			logger.error("redispathch WQ failed",e);
		}
		finally{
			logger.info("Workqueue redispatch job end");
		}

	}

	public WorkQueueDataService getWorkQueueDataService() {
		return this.workQueueDataService;
	}

	public void setWorkQueueDataService(WorkQueueDataService pWorkQueueDataService) {
		this.workQueueDataService = pWorkQueueDataService;
	}

	public WorkQueueService getWorkQueueService() {
		return this.workQueueService;
	}

	public void setWorkQueueService(WorkQueueService pWorkQueueService) {
		this.workQueueService = pWorkQueueService;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return this.orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(OrderRetrieveLtsService pOrderRetrieveLtsService) {
		this.orderRetrieveLtsService = pOrderRetrieveLtsService;
	}

	public WorkQueueSubmissionServiceImpl getWorkQueueSubmissionServiceImpl() {
		return this.workQueueSubmissionServiceImpl;
	}

	public void setWorkQueueSubmissionServiceImpl(WorkQueueSubmissionServiceImpl pWorkQueueSubmissionServiceImpl) {
		this.workQueueSubmissionServiceImpl = pWorkQueueSubmissionServiceImpl;
	}

}
