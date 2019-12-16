package com.bomwebportal.ims.service;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsOrderAmendBomDAO;
import com.bomwebportal.ims.dao.ImsOrderAmendDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.AmendWqDTO;
import com.bomwebportal.ims.dto.AmendmentModeDTO;
import com.bomwebportal.ims.dto.AppointmentDTO;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.DelayReasonDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ImsAutoSyncWQDTO;
import com.bomwebportal.ims.dto.ui.AmendOrderImsUI;
import com.bomwebportal.ims.dto.ui.CcLtsImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.pccw.wq.schema.dto.WorkQueueAttributeDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.service.WorkQueueService;

@Transactional(readOnly=true)
public class ImsOrderAmendServiceImpl implements ImsOrderAmendService{
	
	

	public String getEqtReturnMethod(String ocid, String fsa){
		return imsAmendOrderBomDao.getEqtReturnMethod( ocid,  fsa);
	}
	
	
	public String auto_term_camp_order_amend(String orderId) {
		try {
			return imsAmendOrderBomDao.auto_term_camp_order_amend(orderId);
			
		} catch (DAOException e) {
			logger.error("Exception caught in ImsOrderAmendServiceImpl auto_term_camp_order_amend:", e);
		}
		return "auto_term_camp_order_amend() Error";
	}

	protected final Log logger = LogFactory.getLog(getClass());

	private WorkQueueService workQueueService;
	private ImsOrderAmendDAO imsAmendOrderDao;
	private ImsOrderService orderService;

	public ImsOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(ImsOrderService orderService) {
		this.orderService = orderService;
	}

	private ImsOrderAmendBomDAO imsAmendOrderBomDao;
	private Gson gson = new Gson();
	
	public ImsOrderAmendBomDAO getImsAmendOrderBomDao() {
		return imsAmendOrderBomDao;
	}

	public void setImsAmendOrderBomDao(ImsOrderAmendBomDAO imsAmendOrderBomDao) {
		this.imsAmendOrderBomDao = imsAmendOrderBomDao;
	}
	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}

	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}

	public void setImsAmendOrderDao(ImsOrderAmendDAO imsAmendOrderDao) {
		this.imsAmendOrderDao = imsAmendOrderDao;
	}

	public ImsOrderAmendDAO getImsAmendOrderDao() {
		return imsAmendOrderDao;
	}

	public String getOrgStaffID(String id) {
		try {
			return imsAmendOrderDao.getOrgStaffID(id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public List<DelayReasonDTO> getCancelReason() {
		try {
			return imsAmendOrderDao.getCancelReason();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getIsRetPreRenew (String orderId){
		try {
			return imsAmendOrderDao.getIsRetPreRenew(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getIsRetPreRenew():", e);
		}
		return "N";
	}

	public List<AmendWqDTO> getAmendNature(String system) {
		try {
			return imsAmendOrderDao.getAmendNature(system);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateBomwebAmendCategory(AmendOrderImsUI amend) throws DAOException{
			imsAmendOrderDao.updateBomwebAmendCategory(amend);
	}

	public void updateBomwebAmendCategoryMarkX(AmendOrderImsUI amend) throws DAOException{
			imsAmendOrderDao.updateBomwebAmendCategoryMarkX(amend);
	}

	public void insertBomwebAmendCategory(AmendOrderImsUI amend, String rmk, String isNowRetAmendTvOnly) {
		try {
			imsAmendOrderDao.insertBomwebAmendCategory(amend, rmk, isNowRetAmendTvOnly);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new AppRuntimeException("", new Exception("insertBomwebAmendCategory failed"));
		}
	}
	
	public List<WorkQueueNatureDTO> getTypeSubtypeByNatureID(List<String> wqNatureIdList) {
		try {
			return  imsAmendOrderDao.getTypeSubTypeByWqNatureID(wqNatureIdList);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	public void add_PRIORITY_WP_ID(OrderImsUI order, WorkQueueDTO wqDTO, String grpId) {
		try {
			String priorityWpId = imsAmendOrderDao.getPriorityWpId(order.getSalesCd(), order.getCreateBy(), grpId);
			if (priorityWpId != null) {
				WorkQueueAttributeDTO attb2 = new WorkQueueAttributeDTO();
				attb2.setAttbName("PRIORITY_WP_ID");
				attb2.setAttbValue(priorityWpId);
				wqDTO.addAttribute(attb2);
			}
		} catch (DAOException e1) {
			logger.error("add_PRIORITY_WP_ID Error: " +
					" order: " + gson.toJson(order) +
					" wqDTO: " + gson.toJson(order) +
					" grpId: " + gson.toJson(grpId)
					, e1);
		}
	}
	
	public List<WorkQueueDTO> imsAmendWorkQueue(AmendOrderImsUI amend){
		List<WorkQueueDTO> resultWqDtoList= new ArrayList<WorkQueueDTO>();
		logger.info("imsAmendWorkQueue is called");

		WorkQueueDTO wqDTO = new WorkQueueDTO();
		WorkQueueAttributeDTO attb = new WorkQueueAttributeDTO();
		if("Y".equalsIgnoreCase(amend.isFS())||"Y".equalsIgnoreCase(amend.getIsDOrderSelfPickEnabled())){
			attb.setAttbName("SB_ISSUE_BY");
			try {
				attb.setAttbValue(imsAmendOrderDao.getRealStaffID(amend.getBomSalesUserDTO().getUsername()));
			} catch (DAOException e) {
				attb.setAttbValue(amend.getBomSalesUserDTO().getUsername());
				e.printStackTrace();
			}
			if(attb.getAttbValue()==null){
				attb.setAttbValue(amend.getBomSalesUserDTO().getUsername());
			}

			add_PRIORITY_WP_ID(amend.getOrderImsUI(), wqDTO, "BOM2017038");
			add_PRIORITY_WP_ID(amend.getOrderImsUI(), wqDTO, "delightToCSL");
		}else{
			attb.setAttbName("SB_CHANNEL_CD");
			attb.setAttbValue("SB-SYS");
		}
		wqDTO.addAttribute(attb);

		com.pccw.wq.schema.dto.RemarkDTO salesRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO amendRemark1 = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO amendRemark2 = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO amendRemark3 = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO creditCardRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO orderCancelRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO srdRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO loginIdRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO contactEmailRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO contactMobileRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		com.pccw.wq.schema.dto.RemarkDTO combinedWqCoverSheetRemark = new com.pccw.wq.schema.dto.RemarkDTO();
//		com.pccw.wq.schema.dto.RemarkDTO bomWebAmendCategoryRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		
		com.pccw.wq.schema.dto.RemarkDTO progOfferChangeRemark = new com.pccw.wq.schema.dto.RemarkDTO();
		progOfferChangeRemark.setRemarkContent(amend.getProgOfferChangeRemark());
		
		salesRemark.setRemarkContent(createWqSalesRemark(amend)+"\n");
		amendRemark1.setRemarkContent("Details:"+getFSRemark1(amend));
		amendRemark2.setRemarkContent("Details:"+getFSRemark2(amend));
		amendRemark3.setRemarkContent("Details:"+getFSRemark3(amend));
		
		// create Credit Card remark
		String tempMergeCreditCardRemark = createWqCreditCardRemark(amend);
		if(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS.equalsIgnoreCase(amend.getAmendNature1())){
			tempMergeCreditCardRemark="Details:"+getFSRemark1(amend)+"\n"+tempMergeCreditCardRemark;
		}
		if(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS.equalsIgnoreCase(amend.getAmendNature2())){
			tempMergeCreditCardRemark="Details:"+getFSRemark2(amend)+"\n"+tempMergeCreditCardRemark;
		}
		if(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS.equalsIgnoreCase(amend.getAmendNature3())){
			tempMergeCreditCardRemark="Details:"+getFSRemark3(amend)+"\n"+tempMergeCreditCardRemark;
		}
		creditCardRemark.setRemarkContent(tempMergeCreditCardRemark);

		
		// create SRD remark
		String tempMergeSRDRemark = createWqSrdRemark(amend);
		if(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equalsIgnoreCase(amend.getAmendNature1())){
			tempMergeSRDRemark="Details:"+getFSRemark1(amend)+"\n"+tempMergeSRDRemark;
		}
		if(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equalsIgnoreCase(amend.getAmendNature2())){
			tempMergeSRDRemark="Details:"+getFSRemark2(amend)+"\n"+tempMergeSRDRemark;
		}
		if(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equalsIgnoreCase(amend.getAmendNature3())){
			tempMergeSRDRemark="Details:"+getFSRemark3(amend)+"\n"+tempMergeSRDRemark;
		}
		srdRemark.setRemarkContent(tempMergeSRDRemark);
		
		// create login id remark
		String tempMergeLoginIdRemark = createWqLoginIdRemark(amend);
		loginIdRemark.setRemarkContent(tempMergeLoginIdRemark);
		
		// create contact email remark
		String tempMergeContactEmailRemark = createWqContEmailRemark(amend);
		contactEmailRemark.setRemarkContent(tempMergeContactEmailRemark);
		
		// create contact mobile remark
		String tempMergeContactMobileRemark = createWqContMobileRemark(amend);
		contactMobileRemark.setRemarkContent(tempMergeContactMobileRemark);
		
		
		orderCancelRemark.setRemarkContent(createWqOrderCancelRemark(amend));
		combinedWqCoverSheetRemark.setRemarkContent(createWqCombinedCoverSheetRemark(amend, false));
//		bomWebAmendCategoryRemark.setRemarkContent(createBomWebAmendCategoryRemark(amend));
		

		logger.info("Sales Remark:"+salesRemark.getRemarkContent());
		logger.info("fs Remark1:"+amendRemark1.getRemarkContent());
		logger.info("fs Remark2:"+amendRemark2.getRemarkContent());
		logger.info("fs Remark3:"+amendRemark3.getRemarkContent());
		logger.info("credit card Remark:"+creditCardRemark.getRemarkContent());
		logger.info("order cancel Remark:"+orderCancelRemark.getRemarkContent());
		logger.info("srd Remark:"+srdRemark.getRemarkContent());
		logger.info("combined Wq Cover Sheet Remark:"+combinedWqCoverSheetRemark.getRemarkContent());
//		logger.info("bomweb Amend Category Remark:"+bomWebAmendCategoryRemark.getRemarkContent());
		

		setWqDTO(amend, wqDTO);
		wqDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {salesRemark});
		
		List<WorkQueueNatureDTO> wqNatureDtoList = new ArrayList<WorkQueueNatureDTO>();
		wqNatureDtoList = this.getTypeSubtypeByNatureID(amend.getWqNatureIDList());

		if(amend.getWqNatureIDList().size()>1){
			logger.info("amend.getWqNatureIDList():" + gson.toJson(amend.getWqNatureIDList()));
			for(int i=0;i<wqNatureDtoList.size();i++){
				wqNatureDtoList.get(i).setWorkQueueType("SB-PCD");
				if("NTV-A".equals(amend.getOrderImsUI().getOrderType()) ||amend.getOrderImsUI().isOrderTypeNowRet()){
					wqNatureDtoList.get(i).setWorkQueueType("SB-NOW");
				}
				if(i==0){
					wqNatureDtoList.get(i).setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {amendRemark1} );	
				}else if(i==1){
					wqNatureDtoList.get(i).setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {amendRemark2} );	
				}else if(i==2){
					wqNatureDtoList.get(i).setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {amendRemark3} );	
				}
				if(wqNatureDtoList.get(i).getWorkQueueNatureId().equals(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS)){
					wqNatureDtoList.get(i).setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {addUAMS(srdRemark,amend)} );	
				}
				if(wqNatureDtoList.get(i).getWorkQueueNatureId().equals(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS)){
					wqNatureDtoList.get(i).setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {creditCardRemark} );	
				}
				if(wqNatureDtoList.get(i).getWorkQueueNatureId().equals(ImsConstants.IMS_AMEND_WQ_NATURE_DS_offer_Plan_VAS_Premium)||
				   wqNatureDtoList.get(i).getWorkQueueNatureId().equals(ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE)){
					wqNatureDtoList.get(i).setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {progOfferChangeRemark} );
				}
			}
		}else{
			wqNatureDtoList.get(0).setWorkQueueType("SB-PCD");
			if("NTV-A".equals(amend.getOrderImsUI().getOrderType()) || 
					"NTVAO".equals(amend.getOrderImsUI().getOrderType()) || 
					"NTVUS".equals(amend.getOrderImsUI().getOrderType()) || 
					"NTVRE".equals(amend.getOrderImsUI().getOrderType())	){
				wqNatureDtoList.get(0).setWorkQueueType("SB-NOW");
			}
			wqNatureDtoList.get(0).setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {combinedWqCoverSheetRemark} );	
		}
		
		WorkQueueNatureDTO[] array = wqNatureDtoList.toArray(new WorkQueueNatureDTO[wqNatureDtoList.size()]);
		
		
		wqDTO.setWorkQueueNatures(array);
		WorkQueueDTO resultWqDto = null;
		logger.info("go to wq, by amendment, order_id:"+amend.getOrderImsUI().getOrderId());
		try {
			if(amend.getAutoWQCount()>0){
				logger.info(amend.getAutoWQCount()+" auto wqs, order_id:"+amend.getOrderImsUI().getOrderId());
				if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
					WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
					wqNatureDTO.setWorkQueueType("SB-PCD");
					wqNatureDTO.setWorkQueueSubType("ORDER_AMEND");
					wqNatureDTO.setWorkQueueNatureId("350");
					attb = new WorkQueueAttributeDTO();
					attb.setAttbName("SB_CHANNEL_CD");
					attb.setAttbValue("SB-SYS");
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+srdRemark.getRemarkContent());
					wqDTO= new WorkQueueDTO();
					wqDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});
					setWqDTO(amend, wqDTO);
					wqDTO.addAttribute(attb);
					wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
					logger.info("350 appointment start");
					resultWqDto = workQueueService.createWorkQueue(	wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, amend.getBomSalesUserDTO().getUsername());
					logger.info("350 appointment done, result:" + gson.toJson(resultWqDto));
					resultWqDtoList.add(resultWqDto);
				}
				
				
				if("Y".equalsIgnoreCase(amend.getIsUpdateCreditEnabled())){

					WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
					wqNatureDTO.setWorkQueueType("SB-PCD");
					wqNatureDTO.setWorkQueueSubType("ORDER_AMEND");
					wqNatureDTO.setWorkQueueNatureId("351");
					attb = new WorkQueueAttributeDTO();
					attb.setAttbName("SB_CHANNEL_CD");
					attb.setAttbValue("SB-SYS");
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+creditCardRemark.getRemarkContent());
					wqDTO= new WorkQueueDTO();
					wqDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});
					setWqDTO(amend, wqDTO);
					wqDTO.addAttribute(attb);
					wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
					resultWqDto = null;
					logger.info("351 creditCard start");
					resultWqDto = workQueueService.createWorkQueue(	wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, amend.getBomSalesUserDTO().getUsername());
					logger.info("351 creditCard done, result:" + gson.toJson(resultWqDto));
					resultWqDtoList.add(resultWqDto);
					
				}

				
				if("Y".equalsIgnoreCase(amend.getIsLoginIdEnabled())){

					WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
					wqNatureDTO.setWorkQueueType("SB-PCD");
					wqNatureDTO.setWorkQueueSubType("ORDER_AMEND");
					wqNatureDTO.setWorkQueueNatureId("370");
					attb = new WorkQueueAttributeDTO();
					attb.setAttbName("SB_CHANNEL_CD");
					attb.setAttbValue("SB-SYS");
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+loginIdRemark.getRemarkContent());
					wqDTO= new WorkQueueDTO();
					wqDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});
					setWqDTO(amend, wqDTO);
					wqDTO.addAttribute(attb);
					wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
					resultWqDto = null;
					logger.info("370 loginId start");
					resultWqDto = workQueueService.createWorkQueue(	wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, amend.getBomSalesUserDTO().getUsername());
					logger.info("370 loginId done, result:" + gson.toJson(resultWqDto));
					resultWqDtoList.add(resultWqDto);
					
				}
				
				if("Y".equalsIgnoreCase(amend.getIsContactEmailEnabled())){

					WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
					wqNatureDTO.setWorkQueueType("SB-PCD");
					wqNatureDTO.setWorkQueueSubType("ORDER_AMEND");
					wqNatureDTO.setWorkQueueNatureId("371");
					attb = new WorkQueueAttributeDTO();
					attb.setAttbName("SB_CHANNEL_CD");
					attb.setAttbValue("SB-SYS");
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+contactEmailRemark.getRemarkContent());
					wqDTO= new WorkQueueDTO();
					wqDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});
					setWqDTO(amend, wqDTO);
					wqDTO.addAttribute(attb);
					wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
					resultWqDto = null;
					logger.info("371 contactEmail start");
					resultWqDto = workQueueService.createWorkQueue(	wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, amend.getBomSalesUserDTO().getUsername());
					logger.info("371 contactEmail done, result:" + gson.toJson(resultWqDto));
					resultWqDtoList.add(resultWqDto);
					
				}
				
				if("Y".equalsIgnoreCase(amend.getIsContactMobileEnabled())){

					WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
					wqNatureDTO.setWorkQueueType("SB-PCD");
					wqNatureDTO.setWorkQueueSubType("ORDER_AMEND");
					wqNatureDTO.setWorkQueueNatureId("373");
					attb = new WorkQueueAttributeDTO();
					attb.setAttbName("SB_CHANNEL_CD");
					attb.setAttbValue("SB-SYS");
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+contactMobileRemark.getRemarkContent());
					wqDTO= new WorkQueueDTO();
					wqDTO.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});
					setWqDTO(amend, wqDTO);
					wqDTO.addAttribute(attb);
					wqDTO.setWorkQueueNatures(new WorkQueueNatureDTO[] {wqNatureDTO});
					resultWqDto = null;
					logger.info("373 contactMobile start");
					resultWqDto = workQueueService.createWorkQueue(	wqDTO, new WorkQueueNatureDTO[]{wqNatureDTO}, amend.getBomSalesUserDTO().getUsername());
					logger.info("373 contactMobile done, result:" + gson.toJson(resultWqDto));
					resultWqDtoList.add(resultWqDto);
					
				}
			}else{
				resultWqDto = workQueueService.createWorkQueue(	wqDTO, array, amend.getBomSalesUserDTO().getUsername());
				logger.info("retDto.getCreatedBatchId():"+resultWqDto.getCreatedBatchId());		
				resultWqDtoList.add(resultWqDto);
			}			
			
		} catch (Exception e) {
			logger.error("imsAmendWorkQueue WQ_ERROR:" + ExceptionUtils.getFullStackTrace(e));
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException("", new Exception("imsAmendWorkQueue WQ_ERROR:" + ExceptionUtils.getFullStackTrace(e)));
		}
		if(resultWqDto==null || resultWqDtoList.size()<1){
			logger.error("WQ_ERROR, wqDto:" + gson.toJson(resultWqDto));
			logger.error("WQ_ERROR, resultWqDtoList.size():"+resultWqDtoList.size()+" cannot create WQ for order_id:"+amend.getOrderImsUI().getOrderId());
			
			if(amend.getAutoWQCount()>0){
				logger.info(amend.getAutoWQCount()+" auto wqs, order_id:"+amend.getOrderImsUI().getOrderId());
				if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
					resultWqDto = new WorkQueueDTO();
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+srdRemark.getRemarkContent());
					resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});	
					resultWqDtoList.add(resultWqDto);
				}
				
				
				if("Y".equalsIgnoreCase(amend.getIsUpdateCreditEnabled())){
					resultWqDto= new WorkQueueDTO();
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+creditCardRemark.getRemarkContent());
					resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});	
					resultWqDtoList.add(resultWqDto);
					
				}

				
				if("Y".equalsIgnoreCase(amend.getIsLoginIdEnabled())){
					resultWqDto= new WorkQueueDTO();
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+loginIdRemark.getRemarkContent());
					resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});	
					resultWqDtoList.add(resultWqDto);
					
				}
				
				if("Y".equalsIgnoreCase(amend.getIsContactEmailEnabled())){
					resultWqDto= new WorkQueueDTO();
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+contactEmailRemark.getRemarkContent());
					resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});	
					resultWqDtoList.add(resultWqDto);
					
				}
				
				if("Y".equalsIgnoreCase(amend.getIsContactMobileEnabled())){
					resultWqDto= new WorkQueueDTO();
					com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
					newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+contactMobileRemark.getRemarkContent());
					resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});	
					resultWqDtoList.add(resultWqDto);
					
				}
			}
			
			
			
			
			
			/*
			if("Y".equals(amend.getTwoAutoWQs())){
				com.pccw.wq.schema.dto.RemarkDTO newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
				newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+srdRemark.getRemarkContent());
				resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});	
				resultWqDtoList.add(resultWqDto);
				resultWqDto= new WorkQueueDTO();
				newRmk = new com.pccw.wq.schema.dto.RemarkDTO();
				newRmk.setRemarkContent(salesRemark.getRemarkContent()+"\n"+creditCardRemark.getRemarkContent());
				resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {newRmk});	
				resultWqDtoList.add(resultWqDto);
			}else{
//				resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {bomWebAmendCategoryRemark});	
				combinedWqCoverSheetRemark = new com.pccw.wq.schema.dto.RemarkDTO();
				combinedWqCoverSheetRemark.setRemarkContent(createWqCombinedCoverSheetRemark(amend, true));
				resultWqDto.setRemarks(new com.pccw.wq.schema.dto.RemarkDTO[] {combinedWqCoverSheetRemark});	
				resultWqDtoList.add(resultWqDto);
			}*/
		}
		if(("DS".equals(amend.getOrderImsUI().getImsOrderType())||amend.getOrderImsUI().isDs()) && "Y".equals(amend.isFS())){
			orderService.dsAlertWQ(amend.getOrderImsUI(), ImsConstants.DS_Alert_WQ_DS_S_B4_AMEND_FS, 
					"Alert DS Support before F&S Amendment",
					this.createDSFSPendingRemarks(amend, amendRemark1, amendRemark2, amendRemark3, srdRemark, creditCardRemark, progOfferChangeRemark, salesRemark));
		}
		logger.error("resultWqDtoList:" + gson.toJson(resultWqDtoList));
		return resultWqDtoList;
	}
	
	public String createDSFSPendingRemarks(AmendOrderImsUI amend, com.pccw.wq.schema.dto.RemarkDTO amendRemark1, com.pccw.wq.schema.dto.RemarkDTO amendRemark2
			, com.pccw.wq.schema.dto.RemarkDTO amendRemark3, com.pccw.wq.schema.dto.RemarkDTO srdRemark, com.pccw.wq.schema.dto.RemarkDTO creditCardRemark
			, com.pccw.wq.schema.dto.RemarkDTO progOfferChangeRemark, com.pccw.wq.schema.dto.RemarkDTO salesRemark) {
		String result="";
		logger.info("amend.getWqNatureIDList():" + gson.toJson(amend.getWqNatureIDList()));
		result+=salesRemark.getRemarkContent()+"\n";	
		for(int i=0;i<amend.getWqNatureIDList().size();i++){
			result+="\n";	
			try {
				result+=this.imsAmendOrderDao.getNatureDesc(amend.getWqNatureIDList().get(i))+"\n";
			} catch (DAOException e) {
				e.printStackTrace();
			}
			if(amend.getWqNatureIDList().get(i).equals(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS)){
				result+=addUAMS(srdRemark,amend).getRemarkContent()+"\n";	
			}else if(amend.getWqNatureIDList().get(i).equals(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS)){
				result+=creditCardRemark.getRemarkContent()+"\n";	
			}else if(amend.getWqNatureIDList().get(i).equals(ImsConstants.IMS_AMEND_WQ_NATURE_DS_offer_Plan_VAS_Premium)||
					 amend.getWqNatureIDList().get(i).equals(ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE)){
				result+=progOfferChangeRemark.getRemarkContent()+"\n";	
			}else if(i==0){
				result+=amendRemark1.getRemarkContent()+"\n";	
			}else if(i==1){
				result+=amendRemark2.getRemarkContent()+"\n";	
			}else if(i==2){
				result+=amendRemark3.getRemarkContent()+"\n";	
			}
		}
		logger.info("createDSFSPendingRemarks:"+result);
		return result;
	}

	public void setWqDTO(AmendOrderImsUI amend, WorkQueueDTO wqDTO) {
		wqDTO.setBomOcId(amend.getOrderImsUI().getOcId());
		wqDTO.setSbId(amend.getOrderImsUI().getOrderId());
		wqDTO.setSbDtlId("1"); //IMS default "1"
		String shopOrTeam = amend.getOrderImsUI().getShopCd();
		if("Y".equals(amend.getOrderImsUI().getIsCC())||"DS".equals(amend.getOrderImsUI().getImsOrderType())){
			shopOrTeam = amend.getOrderImsUI().getSourceCd();
		}
		wqDTO.setSbShopCode(shopOrTeam);//should be SBO for online sales
		wqDTO.setTypeOfService("FSA");// Nanon said FSA
		wqDTO.setServiceId(amend.getOrderImsUI().getCustomer().getServiceNum());
		wqDTO.setSrd(amend.getBomSRD());//"yyyy/MM/dd");
	}
	
	public String createWqSalesRemark(AmendOrderImsUI amend) {
		DateFormat printTimeNow = new SimpleDateFormat("dd/MM/yy HH:mm");
		Date timeNow = new Date();
		System.out.println("time now:"+printTimeNow.format(timeNow));
		String modiBy = amend.getBomSalesUserDTO().getUsername();
		modiBy = this.getOrgStaffID(modiBy);
		return "Modi by: "+ modiBy +", "+amend.getBomSalesUserDTO().getChannelCd()+"  "+(printTimeNow.format(timeNow))+
			"\nFr: "+amend.getApplicantName()+"/"+amend.getApplicantRelationship()+"/"+amend.getApplicantContactNo();
	}
	
	public String createWqCreditCardRemark(AmendOrderImsUI amend) {
		String result = "";
		if("Y".equals(amend.getIsUpdateCreditEnabled())){
			String cctype ="American Express";
			if("V".equalsIgnoreCase(amend.getCcType())){
				cctype="Visa";
			}else if("M".equalsIgnoreCase(amend.getCcType())){
				cctype="Master";
			}
			
			result= "Card Holder Name:"+amend.getCcHolderName() +
						"	\nCard Type:"+ cctype+
						"	\nCard Number:"+amend.getCcNum() +
						" 	\nExpiry Date:"+amend.getCcExpDate()+
						"	\nIs 3rd Party Card:"+amend.getThirdPartyInd() +
						((amend.getFaxSerialNum()!=null&&!"".equals(amend.getFaxSerialNum()))?"	\nFax Serial Num:"+amend.getFaxSerialNum():"")+
						" \n ";
		};
		return result;
	}
	
	public String createWqLoginIdRemark(AmendOrderImsUI amend) {
		String result = "";
		if("Y".equals(amend.getIsLoginIdEnabled())){
			
			result= "Old Login ID:" + amend.getOrderImsUI().getLoginId() + "\nNew Login ID:"+amend.getLoginId() +
					" \n";
			
		};
		return result;
	}
	
	public String createWqContEmailRemark(AmendOrderImsUI amend) {
		String result = "";
		if("Y".equals(amend.getIsContactEmailEnabled())){
			
			result= "New Contact Email:"+amend.getContactEmail() +
					" \n";
			
		};
		return result;
	}
	
	public String createWqContMobileRemark(AmendOrderImsUI amend) {
		String result = "";
		if("Y".equals(amend.getIsContactMobileEnabled())){
			
			result= "New Contact Mobile:"+amend.getContactMobile() +
					" \n";
			
		};
		return result;
	}

	public String createWqSrdRemark(AmendOrderImsUI amend) {
		String result = "";
		if("Y".equals(amend.getIsAppointmentEnabled())){
			if(!"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())&&!"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd())
					||(("Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())||"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd()))
							&&"Y".equalsIgnoreCase(amend.getIsPreInstApptEnabled()))){
				Date instdate= new Date();
				if(amend.getOrderImsUI().getAppointment().getTargetInstallDate()!=null && !"".equals(amend.getOrderImsUI().getAppointment().getTargetInstallDate())){
					logger.info("getTargetInstallDate:"+amend.getOrderImsUI().getAppointment().getTargetInstallDate());
					String dateString = amend.getOrderImsUI().getAppointment().getTargetInstallDate();
					SimpleDateFormat takeInstDate = new SimpleDateFormat("yyyy/MM/dd");
					try {
						instdate = takeInstDate.parse(dateString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					logger.info("instdate:"+instdate);
				}
				DateFormat printInstDate = new SimpleDateFormat("dd/MM/yy");
				if("Y".equals(amend.getCanAmendAppointment())){
					result = "change appt date to :"+(printInstDate.format(instdate))+" " +amend.getTimeSlotDisplay().replaceAll(":00", "")+
					" 	\nDelay Reason Code:"+amend.getDelayReasonCode() +
					" 	\nDelay Reason:"+amend.getDelayReason() ;
					if(amend.getOrderImsUI().getAppointment().getBmoRmk()!=null 
							&& !amend.getOrderImsUI().getAppointment().getBmoRmk().equals("")){
						result += " 	\nBMO Remark:"+amend.getOrderImsUI().getAppointment().getBmoRmk();
					}
				}else{
					result = "change SRD to :"+(printInstDate.format(instdate));
				}
			}
			if(("Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())||"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd()))
					&&"Y".equalsIgnoreCase(amend.getIsPreInstCommDateEnabled())){
				if(!result.isEmpty()){
					result = result + "\n";
				}
				Date targetcommdate= new Date();
				if(amend.getOrderImsUI().getAppointment().getTargetCommDate()!=null && !"".equals(amend.getOrderImsUI().getAppointment().getTargetCommDate())){
					String dateString = amend.getOrderImsUI().getAppointment().getTargetCommDate();
					SimpleDateFormat takeInstDate = new SimpleDateFormat("yyyy/MM/dd");
					try {
						targetcommdate = takeInstDate.parse(dateString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				DateFormat printInstDate = new SimpleDateFormat("dd/MM/yy");
				result = result + "change target comm date to :" +(printInstDate.format(targetcommdate));
			}
			result = result + "\n";
		}
		return result;
	}

	public String createWqOrderCancelRemark(AmendOrderImsUI amend) {
			return"Cancel Reason: "+amend.getCancelReason()+"\nCancel Remark: "+amend.getCancelRemark() ;
	}

	public String getFSRemark1(AmendOrderImsUI amend) {
		String result = amend.getAmendRemark1();
		if(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equals(amend.getAmendNature1())){
			result = getFsAppointmentRemark(amend);
		}else if(ImsConstants.IMS_AMEND_WQ_NATURE_CancelOrder_FS.equals(amend.getAmendNature1())){
			result = getFsCancelOrderRemark(amend);
		}
			return result;
	}

	public String getFSRemark2(AmendOrderImsUI amend) {
		String result = amend.getAmendRemark2();
		if(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equals(amend.getAmendNature2())){
			result = getFsAppointmentRemark(amend);
		}else if(ImsConstants.IMS_AMEND_WQ_NATURE_CancelOrder_FS.equals(amend.getAmendNature2())){
			result = getFsCancelOrderRemark(amend);
		}
			return result;
	}

	public String getFSRemark3(AmendOrderImsUI amend) {
		String result = amend.getAmendRemark3();
		if(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equals(amend.getAmendNature3())){
			result = getFsAppointmentRemark(amend);
		}else if(ImsConstants.IMS_AMEND_WQ_NATURE_CancelOrder_FS.equals(amend.getAmendNature3())){
			result = getFsCancelOrderRemark(amend);
		}
			return result;
	}

	public String getFsCancelOrderRemark(AmendOrderImsUI amend) {
		return "Cancel Type:"+amend.getFsCancelType()+
		"\nCancel Reason:"+amend.getFsCancelReason()+
		"\nCancel Remark:"+amend.getFsCancelRemark()+
		"\n";
	}

	
	public String getDorderSelfSrdRemark (AmendOrderImsUI amend){
		String remark = "";
		remark += this.addStringIfNotNull("SRD:", amend.getOrderImsUI().getAppointment().getTargetInstallDate());
		return remark;
	}
	
	public String getDorderFSRemark (AmendOrderImsUI amend){
		String remark = "";
		remark += this.addStringIfNotNull("Target Installation Date:", amend.getFsTargetInstallDate());
		remark += this.addStringIfNotNull("Time Slot:", amend.getFsTimeSlot());
		remark += this.addStringIfNotNull("Delay Reason Code:", amend.getFsDelayReasonCode());
		remark += this.addStringIfNotNull("UAMS / Force Serial:", amend.getFsPreBkSerialNum());
		remark += this.addStringIfNotNull("BMO Remarks:", amend.getOrderImsUI().getAppointment().getBmoRmk());
		remark += this.addStringIfNotNull("Addtional Remarks:", amend.getFsAddtionalRemarks());
		return remark;
	}
	
	public String getFsAppointmentRemark (AmendOrderImsUI amend){
		String remark = "";
		if("Y".equalsIgnoreCase(amend.getCanAmendAppointment())){
			remark += this.addStringIfNotNull("Target Installation Date:", amend.getFsTargetInstallDate());
			remark += this.addStringIfNotNull("Time Slot:", amend.getFsTimeSlot());
			remark += this.addStringIfNotNull("Delay Reason Code:", amend.getFsDelayReasonCode());
			remark += this.addStringIfNotNull("UAMS / Force Serial:", amend.getFsPreBkSerialNum());
			remark += this.addStringIfNotNull("BMO Remarks:", amend.getOrderImsUI().getAppointment().getBmoRmk());
			remark += this.addStringIfNotNull("Addtional Remarks:", amend.getFsAddtionalRemarks());
			if("Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())||"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd())){
				remark += this.addStringIfNotNull("Target Commencement Date:", amend.getFsTargetCommDate());
			}
		}else{
			remark += this.addStringIfNotNull("change SRD to:", amend.getFsTargetInstallDate());
			remark += this.addStringIfNotNull("Addtional Remarks:", amend.getFsAddtionalRemarks());
			if("Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())||"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd())){
				remark += this.addStringIfNotNull("Target Commencement Date:", amend.getFsTargetCommDate());
			}
		}
		return remark;
	}
	
	public String addStringIfNotNull (String hardCode, String variable){
		String result = "";
		if(variable!=null && !"".equals(variable)){
			result = "\n"+hardCode+variable;
		}
		return result;
	}
	
	public String createWqCombinedCoverSheetRemark(AmendOrderImsUI amend, Boolean needSalesInfo) {
//		String wqRemarkString = createBomWebAmendCategoryRemark(amend);
		String wqRemarkString = "";
		if(needSalesInfo){ 
			wqRemarkString += createWqSalesRemark(amend);
		}
		
		if("Y".equalsIgnoreCase(amend.getIsCancelOrderEnabled())){
			return createWqOrderCancelRemark(amend);
		}
		// prog offer change
		if("Y".equalsIgnoreCase(amend.getIsChangeProgOfferEnabled())){
			wqRemarkString+= amend.getProgOfferChangeRemark();
		} 
		// prog offer change (end)
		if("Y".equalsIgnoreCase(amend.getIsFsAmendEnabled())){
			wqRemarkString+=this.addStringIfNotNull("Detail:",getFSRemark1(amend));
			wqRemarkString+=this.addStringIfNotNull("Detail2:",getFSRemark2(amend));
			wqRemarkString+=this.addStringIfNotNull("Detail3:",getFSRemark3(amend));
		}
		if("Y".equalsIgnoreCase(amend.getIsUpdateCreditEnabled())||"Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())
				||"Y".equalsIgnoreCase(amend.getIsLoginIdEnabled())||"Y".equalsIgnoreCase(amend.getIsContactEmailEnabled())){
			wqRemarkString+="\n";
		}
		wqRemarkString+=createWqCreditCardRemark(amend);
		wqRemarkString+=createWqSrdRemark(amend);
		wqRemarkString+=createWqLoginIdRemark(amend);
		wqRemarkString+=createWqContEmailRemark(amend);
		wqRemarkString+=createWqContMobileRemark(amend);
		wqRemarkString+=createWqDOrderSwapSelfPickFsRemark(amend);
		if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())&&"Y".equals(amend.getCanAmendAppointment())){
			wqRemarkString+="\nUAMS serial no:"+" " +amend.getPreBkSerialNum();
		}
		return wqRemarkString;
	}
	
	public com.pccw.wq.schema.dto.RemarkDTO addUAMS(com.pccw.wq.schema.dto.RemarkDTO dto,AmendOrderImsUI amend) {
		String wqRemarkString = "";
		if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())&&"Y".equals(amend.getCanAmendAppointment())){
			wqRemarkString +="\nUAMS serial no:"+" " +amend.getPreBkSerialNum();
		}
		dto.setRemarkContent(dto.getRemarkContent()+wqRemarkString);
		return dto;
	}
	
	//steven added end

	public AppointmentTimeSlotDTO getAmendSRDInfoStorProd(String orderId, String amendType)
			throws DAOException {
		logger.info("getAmendSRDInfoStorProd orderId:" + orderId + "  amendType:"+amendType);

		try{
		
			AppointmentDTO appointmentDTO = imsAmendOrderBomDao.getSrdInfo(orderId, amendType);
			AppointmentTimeSlotDTO appointmentTimeSlotDTO = new AppointmentTimeSlotDTO();
			
			String SRDInfoApptDate = Utility.date2String(appointmentDTO.getAppntStartDate(),"yyyyMMdd");
			String SRDInfoApptTimeStart = Utility.date2String(appointmentDTO.getAppntStartDate(),"HH:mm");
			String SRDInfoApptTimeEnd = Utility.date2String(appointmentDTO.getAppntEndDate(),"HH:mm");
			
			String SRDInfoApptTimeSlot = SRDInfoApptTimeStart + "-" + SRDInfoApptTimeEnd; 
			
	
			appointmentTimeSlotDTO.setApptDate(SRDInfoApptDate);
			appointmentTimeSlotDTO.setTimeSlot(SRDInfoApptTimeSlot);
			
			appointmentTimeSlotDTO.setReturnValue(appointmentDTO.getStorProReturnValue());
			appointmentTimeSlotDTO.setErrorCode(appointmentDTO.getStorProErrorCode());
			appointmentTimeSlotDTO.setErrorMsg(appointmentDTO.getStorProErrorText());
			
			
			logger.debug("AmendSRDInfo() SRDInfoApptDate = "+SRDInfoApptDate);
			logger.debug("AmendSRDInfo() SRDInfoApptTimeStart = "+SRDInfoApptTimeStart);
			logger.debug("AmendSRDInfo() SRDInfoApptTimeEnd = "+SRDInfoApptTimeEnd);
			
			return appointmentTimeSlotDTO;
		}catch (DAOException de) {		
			logger.error("Exception caught in AmendSRDInfo()", de);
			throw new AppRuntimeException(de);
		}
	}

	public String getBomLastestTechBySbId(String orderId, String sbType) {
		try {
			return imsAmendOrderBomDao.getBomLastestTechBySbId(orderId,sbType);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public List<HousingTypeDTO> getHousingTypeByOrderID(String orderId) {
		try {
			return imsAmendOrderBomDao.getHousingTypeByOrderID(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<DelayReasonDTO> getDelayReasons(String what) {
		try {
			return imsAmendOrderDao.getDelayReason(what);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean isPendingExist(String orderId) {
		try {
			return imsAmendOrderDao.isPendingExist(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Boolean isOpenRetailAmend() {
		try {
			return imsAmendOrderDao.isOpenRetailAmend();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean isRmkEmpty(AmendOrderImsUI amend) {
		String wqRemarkString = "";
		
		if("Y".equalsIgnoreCase(amend.getIsCancelOrderEnabled())){
			wqRemarkString+=createWqOrderCancelRemark(amend);
		}
		// prog offer change
		if("Y".equalsIgnoreCase(amend.getIsChangeProgOfferEnabled())){
			wqRemarkString+= amend.getProgOfferChangeRemark();
		} 
		// prog offer change (end)
		if("Y".equalsIgnoreCase(amend.getIsFsAmendEnabled())){
			wqRemarkString+=this.addStringIfNotNull("Detail:",getFSRemark1(amend));
			wqRemarkString+=this.addStringIfNotNull("Detail2:",getFSRemark2(amend));
			wqRemarkString+=this.addStringIfNotNull("Detail3:",getFSRemark3(amend));
		}
		wqRemarkString+=createWqCreditCardRemark(amend);
		wqRemarkString+=createWqLoginIdRemark(amend);
		wqRemarkString+=createWqContEmailRemark(amend);
		wqRemarkString+=createWqSrdRemark(amend);
		wqRemarkString+=createWqDOrderSwapSelfPickFsRemark(amend);
		if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())&&"Y".equals(amend.getCanAmendAppointment())){
			wqRemarkString+=amend.getPreBkSerialNum();
		}
		if("".equals(wqRemarkString)){
			return true;
		}else{
			return false;
		}
	}

	private String createWqDOrderSwapSelfPickFsRemark(AmendOrderImsUI amend) {

		try {
		if("Y".equals(amend.getIsDOrderSelfPickEnabled())){
			String remark ="";
			
			if("Y".equals(amend.getIsCollectRequestClicked())){
				remark+="Amend to self return";
				remark += "\n\n";
				remark += getDorderSelfSrdRemark(amend);
			}else if("Y".equals(amend.getIsVisitWithChargeClicked())){
				remark+="Amend to FS with Charge";				
				remark += "\n\n";
				remark += getDorderFSRemark(amend);	
			}else if("Y".equals(amend.getIsVisitWithoutChargeClicked())){
				remark+="Amend to FS without Charge";			
				remark += "\n\n";
				remark += getDorderFSRemark(amend);		
			}
			
			return remark;
		}

		} catch (Exception e) {
			logger.error("get in d order wq remark:", e);
		}
		return "";
	}
	

	private void setupAppointmentTimeDisplayDetail(OrderImsUI order){			
//		logger.info("setupAppointmentTimeDisplayDetail");
		
		if(order.getAppointment().getAppntStartDate()!=null || order.getSrvReqDate()!=null){
			Calendar appntStartTime = Calendar.getInstance();
			Calendar appntEndTime = Calendar.getInstance();
			
			//set timeslot display
			logger.debug(gson.toJson(order.getInstallAddress()));
			
			if(order.getAppointment().getAppntStartDate()!=null && 
					!"Y".equals(order.getInstallAddress().getAddrInventory().getResourceShortage())){			
				appntStartTime.setTime(order.getAppointment().getAppntStartDate());			
				appntEndTime.setTime(order.getAppointment().getAppntEndDate());
				
				if("PON".equals(order.getInstallAddress().getAddrInventory().getTechnology()) &&
						appntStartTime.get(Calendar.HOUR_OF_DAY)==18 && appntEndTime.get(Calendar.HOUR_OF_DAY)==20){				
					appntEndTime.set(Calendar.HOUR_OF_DAY, 22);				
				}			
			}else{
				appntStartTime.setTime(order.getSrvReqDate());
				appntStartTime.set(Calendar.HOUR_OF_DAY, 9);
				appntStartTime.set(Calendar.MINUTE, 0);
				
				appntEndTime.setTime(order.getSrvReqDate());
				appntEndTime.set(Calendar.HOUR_OF_DAY, 18);
				appntEndTime.set(Calendar.MINUTE, 0);
			}						
			
			order.getAppointment().setAppntStartDateDisplay(appntStartTime.getTime());
			order.getAppointment().setAppntEndDateDisplay(appntEndTime.getTime());
		}
		
	}
	private String addStringIfNotNull(String header, String content, String newLineChar){
		if(content!=null&&!"".equals(content)){
			return newLineChar+header+content;
		}
		return "";
	}

	public Boolean isOCIDexist(String orderId) {
		try {
			return imsAmendOrderDao.isOCIDexist(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ImsAlertMsgDTO> getImsAlertMsgList(List<String> sbid) {
		try {
			return imsAmendOrderDao.getImsAlertMsgList(sbid);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ImsAlertMsgDTO> getLtsImsOrderEnquiryListInfo(CcLtsImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto) {
		try {
			
			return imsAmendOrderDao.getLtsImsOrderEnquiryListInfo(enquiry, userDto);
		} catch (DAOException e) { 
			e.printStackTrace();
		}
		return null;
	}

	// Celia Ims DS 20150729
	public List<ImsAlertMsgDTO> getImsDSOrderEnquiryListInfo(CcLtsImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto){
		List<ImsAlertMsgDTO> rtnList = new ArrayList<ImsAlertMsgDTO>();
		List<String> added = new ArrayList<String>();
		List<String> notAdded = new ArrayList<String>();
		try{			
			List<ImsAlertMsgDTO> oriList = imsAmendOrderDao.getLtsImsOrderEnquiryListInfo(enquiry, userDto);
			logger.info("oriList: "+gson.toJson(oriList));
			Date currentDate = new Date();			
			for(ImsAlertMsgDTO dto:oriList){
				if(dto.getOrderType()!=null && (
						dto.getOrderType().equalsIgnoreCase("NTV-A") || 
						dto.getOrderType().equalsIgnoreCase("NTVAO") || 
						dto.getOrderType().equalsIgnoreCase("NTVUS") || 
						dto.getOrderType().equalsIgnoreCase("NTVRE")  )){
					dto.setLoginId("");
				}
				if (userDto.getChannelId()==13 && !userDto.getChannelCd().equalsIgnoreCase("VQA")){
					if(dto.getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_CREATED_COMPLETED)
							||dto.getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_CANCELLED)
							||dto.getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_PROFILE_MISMATCH_CANCELLED)
							||dto.getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_CREATED_CANCELLED)){
						if(dto.getOrderType()==null || dto.getOrderType().equalsIgnoreCase("PCD-A"))
							dto.setLoginId("********");
						dto.setCustName(dto.getMaskName());
						Calendar c = Calendar.getInstance();
						if (dto.getLastUpdDate() == null) {
							c.setTime(dto.getCreateDate());
						} else {
							c.setTime(dto.getLastUpdDate());
						}
						c.add(Calendar.DATE, 7);
						Date expirdDate = c.getTime();
						if(expirdDate.before(currentDate)){
//							logger.info("not added:"+dto.getOrderId());
							notAdded.add(dto.getOrderId());
							continue;
						}
					}
				}
//				logger.info("added:"+dto.getOrderId());
				added.add(dto.getOrderId());
				rtnList.add(dto);
			}
		} catch (DAOException e) { 
			e.printStackTrace();
		}
		logger.info("added:"+gson.toJson(added));
		logger.info("notAdded:"+gson.toJson(notAdded));
		logger.info("rtnList: "+gson.toJson(rtnList));
		return rtnList;
	}
	
	
	
	public Boolean checkIfSalesManager(String user) {
		try {
			return imsAmendOrderDao.checkIfSalesManager(user);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getRoleCodeByUserIDLkupCode(String userId, String lkupCode, String lkupFuncCode) {
		try {
			return imsAmendOrderDao.getRoleCodeByUserIDLkupCode(userId, lkupCode, lkupFuncCode);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getChannelCodeListByChannelID(int channelId) {
		try {
			return imsAmendOrderDao.getChannelCodeListByChannelID(channelId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getSBEndingStatus() {
		try {
			return imsAmendOrderDao.getSBEndingStatus();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getTeamCodeListOfCentreCd(String username) {
		try {
			return imsAmendOrderDao.getTeamCodeListOfCentreCd(username);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getPrivilegedOrderIdList(List<String> orderIdList, BomSalesUserDTO userDto, String lkupCode, String lkupFuncCode) {
		logger.info("getPrivilegedOrderIdList is called");
		List<String> result = new ArrayList<String>();
		List<String> roleCodeList = this.getRoleCodeByUserIDLkupCode(userDto.getUsername(), lkupCode, lkupFuncCode);
		List<String> channelCodeList = this.getChannelCodeListByChannelID(userDto.getChannelId());
		List<ImsAlertMsgDTO> imsOrderList =this.getImsOrderListByOrderIdList(orderIdList);
		for(ImsAlertMsgDTO temp:imsOrderList){
			temp.checkRecallAmend(roleCodeList,	userDto, channelCodeList);
			if("Y".equalsIgnoreCase(temp.getRecall())){
				result.add(temp.getOrderId());
			}
		}
		logger.info("result:" + gson.toJson(result));
		return result;
	}

	private List<ImsAlertMsgDTO> getImsOrderListByOrderIdList(
			List<String> orderIdList) {
		try {
			return imsAmendOrderDao.getImsAlertMsgDTOListByOrderIdList(orderIdList);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ImsAutoSyncWQDTO> getPendingFromSpringBoard() {
		try {
			return imsAmendOrderDao.getPendingFromSpringBoard();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ImsAutoSyncWQDTO getPendingFromBOM (ImsAutoSyncWQDTO dto) {
		try {
			return imsAmendOrderBomDao.getPendingFromBOM(dto);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean insertNewWQStatus(String wqWpAssgnId) {
		try {
			imsAmendOrderDao.insertNewWQStatus(wqWpAssgnId);
			return true;
		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("insertNewWQStatus fail wqWpAssgnId:"+wqWpAssgnId);
		}
		return false;
	}

	public Boolean setWQStatusOutdated(String wqWpAssgnId) {
		try {
			imsAmendOrderDao.setWQStatusOutdated(wqWpAssgnId);
			return true;
		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("updateCCAutoWQStatus fail wqWpAssgnId:"+wqWpAssgnId);
		}
		return false;
	}
	
	public Boolean updateBomwebAmendCategory(String sbid, String wqNatureId){
		try {
			imsAmendOrderDao.updateBomwebAmendCategory(sbid, wqNatureId);
			return true;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		logger.error("updateBomwebAmendCategory fail sbid:"+sbid);
		return false;
	}
	
	public Boolean updateBomwebAmendCategoryFS(String sbid){
		try {
			imsAmendOrderDao.updateBomwebAmendCategoryFS(sbid);
			return true;
		} catch (DAOException e) {
			e.printStackTrace();
			logger.error("updateBomwebAmendCategoryFS fail sbid:"+sbid);
		}
		return false;
	}
	
	public Boolean  isPaymentMethodIsCC (String orderId){
		try {
			return imsAmendOrderDao.isPaymentMethodIsCC(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean  checkIfNeedAppointment (String orderId){
		try {
			return imsAmendOrderDao.checkIfNeedAppointment(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean  isNowRetNeedAppointment (String orderId){
		try {
			return imsAmendOrderDao.isNowRetNeedAppointment(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Boolean  isEyeGroupAttach (String orderId){
		try {
			return imsAmendOrderBomDao.isEyeGroupAttach(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public List<String> getOrderStatusList() {
		try {
			return imsAmendOrderDao.getOrderStatusList();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	


	public String getWQChannelCD(String wqNatureID, String wqType) {
		try {
			return imsAmendOrderDao.getWQChannelCD(wqNatureID,wqType);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	
	public String lockBy(String orderId) {
		try {
			return imsAmendOrderBomDao.lockBy(orderId);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	
	public String getWqSRD(String orderId) {
		try {
			return imsAmendOrderDao.getWqSrd(orderId);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}

	public void updateWqSrd(AmendOrderImsUI amend){
		try {
			imsAmendOrderDao.updateWqSrd(amend);
		} catch (DAOException e) {
			e.printStackTrace();
		}	
	}


	public void updateFsAmendToDummy(AmendOrderImsUI amend){
		try {
			imsAmendOrderDao.updateFsAmendToDummy(amend);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	

	public void setWqSrdNull(String orderId){
		try {
		imsAmendOrderDao.setWqSrdNull(orderId);

	} catch (DAOException e) {
		e.printStackTrace();
	}
		
	}

	public Boolean isShortage(String orderId) {
		try {
			return imsAmendOrderDao.isShortage(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean isL1Distributed(String orderId) {
		try {
			return imsAmendOrderBomDao.isL1Distributed(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean isBomDRC55(String orderId) {
		try {
			return imsAmendOrderBomDao.isBomDRC55(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public String getBomSB(String orderId) {
		try {
			return imsAmendOrderBomDao.getBomSB(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Boolean isOrderStatusOkayForAmend(String orderId) {
		try {
			return imsAmendOrderDao.isOrderStatusOkayForAmend(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getOrderStatusDesc(String orderId) {
		try {
			return imsAmendOrderDao.getOrderStatusDesc(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "No record found";
	}

	public Boolean isBomOrderSuspended(String orderId) {
		try {
			return imsAmendOrderBomDao.isBomOrderSuspended(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getOcidBySbid(String orderId) {
		try {
			return imsAmendOrderBomDao.getOcidBySbid(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateNowRetAmendTvOnlyToAuto(AmendOrderImsUI amend) {
		try {
			imsAmendOrderDao.updateNowRetAmendTvOnlyToAuto(amend);
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}

	public List<String> getWqNatureWhichNeedSyncBackFromBom() {
		try {
			return imsAmendOrderDao.getWqNatureWhichNeedSyncBackFromBom();
		} catch (DAOException e) {
			logger.error("Exception caught in getWqNatureWhichNeedSyncBackFromBom():", e);
			e.printStackTrace();
		}
		return null;	
	}

	public AmendmentModeDTO getPreInstallAmendMode(String orderId) {
		try {
			return imsAmendOrderBomDao.getPreInstallAmendMode(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getPreInstallAmendMode():", e);
			e.printStackTrace();
		}
		return null;	
	}

	public Boolean isPonTo1G1GProfilePonAndNot1G1G(String orderId) {
		return imsAmendOrderBomDao.isPonTo1G1GProfilePonAndNot1G1G(orderId);
	}

	public Boolean isPonTo1G1GSpecialServiceCode(String orderId) {
		return imsAmendOrderBomDao.isPonTo1G1GSpecialServiceCode(orderId);
	}

	public Boolean isPonTo1G1GNewBuyBasket1G1G(String orderId) {
		return imsAmendOrderBomDao.isPonTo1G1GNewBuyBasket1G1G(orderId);
	}
	
	public Boolean  isRemakeAppointmentNeeded (String orderId){
		try {
			return imsAmendOrderBomDao.isRemakeAppointmentNeeded(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public List<ImsAlertMsgDTO> getImsOrderEnquiryListInfo(CcLtsImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto) {
		try {
			
			return imsAmendOrderDao.getImsOrderEnquiryListInfo(enquiry, userDto);
		} catch (DAOException e) { 
			e.printStackTrace();
		}
		return null;
	}
}
