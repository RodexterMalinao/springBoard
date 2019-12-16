package com.bomwebportal.lts.service.qc;

import java.util.List;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ui.DsQCImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsDsQCProcessUI;
import com.bomwebportal.ims.dto.ui.ImsQcComOrderSearchUI;
import com.bomwebportal.lts.dto.qc.LtsDsQcProcessDTO;

public interface LtsDsQcService {
	public List<ImsAlertMsgDTO> getLtsDsQcProcessEnquiryInfo(ImsDsQCProcessUI enquiry, BomSalesUserDTO userDto);
	public List<ImsAlertMsgDTO> getLtsDsQcAssignEnquiryInfo(DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto);
	public List<ImsAlertMsgDTO> getLtsDsQcOrderEnquiryInfo(ImsQcComOrderSearchUI enquiry, BomSalesUserDTO userDto);
	public LtsDsQcProcessDTO getLtsDsQcProcessInfo(String orderId);
	public LookupItemDTO[] getLtsQcStatusList();
	public LookupItemDTO[] getLtsQcOrderStatusGroupList();
	public List<String> getLtsQcOrderStatusListByGroup(String group);
	public LookupItemDTO[] getCannotQcReasonList();
	public LookupItemDTO[] getFailedReasonList();
	public void insertQcProcess(String orderId, String qcDate, String qcFindings, String district, String place, 
			String qcStatus, String reasonCode, String reasonString, String userId, String action);
	public boolean isQcProcessExist(String orderId);
	public void updateQcProcess(String orderId, String qcDate, String qcFindings, String district, String place, 
			String qcStatus, String reasonCode, String reasonString, String userId, String action);
	public void updateQcRemarks(String orderId, String qcFindings, String userId);
}
