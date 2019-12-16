package com.bomwebportal.ims.service;

import com.pccw.wq.schema.dto.WorkQueueNatureDTO;


//steven created this java file, plz find steven if u find anything wrong
public interface ImsCOrderWQService {
	
	WorkQueueNatureDTO getTypeSubtypeByNatureID(String WQ_NATURE_ID);
	public Boolean checkPendingAlertWQExist (String orderId);
	public void insertAlertWQRemark(String rmk, String orderId, String salesCd);
	public void insertBomwebRemark(String orderId, String remark, String createBy);
	public String getChannelCd(String salesCd);
	public void updateStatusAlertWQ(String orderId, String assignee);
	public String getSuspendReasonByCode(String code);
	public String getSupportDocFaxSerial(String orderId,String docType);
	

	//steven added for suspend wq 20131217 start
	public Boolean needSuspendWQ(String orderStatus, String createBy);
	//steven added for suspend wq 20131217 end
}
