package com.bomwebportal.lts.service.order;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDetailDTO;
import com.bomwebportal.lts.dto.order.PipbLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;

public interface SaveSbOrderLtsService {

	@Transactional
	public abstract void saveSbOrder(SbOrderDTO pSbOrder, String pUser);

	@Transactional
	public abstract void saveAppointmentDetail(AppointmentDetailLtsDTO pAppntDtl, String pOrderId, String pDtlId, String pUser);
	
	@Transactional
	public abstract void saveService(ServiceDetailDTO pSrvDtl, String pOrderId, String pUser);

	@Transactional
	public abstract void saveAddress(AddressDetailLtsDTO pAddress, String pOrderId, String pUser);
	
	@Transactional
    public abstract void savePipb(PipbLtsDTO pPipb, String pOrderId, String pDtlId, String pUser);
	
	@Transactional
    public abstract void saveCustomer(CustomerDetailLtsDTO pCustomer, String pOrderId, String custNo, String pUser);
	
	@Transactional
    public abstract void saveCustomerIguardReg(CustomerIguardRegDTO pCustomerIguardReg, String pOrderId, String pUser);
	
	@Transactional
	public abstract void saveAmendmentAuditLog(OrderLtsAmendDTO orderLtsAmendDTO, String pOrderId, String pDtlId, String batchSeq, String pUser);
	
	@Transactional
	public abstract void saveAmendmentDetailAuditLog(OrderLtsAmendDetailDTO orderLtsAmendDetailDTO, String pOrderId, String pDtlId, String batchSeq, String itemSeq, String pUser);
	
}