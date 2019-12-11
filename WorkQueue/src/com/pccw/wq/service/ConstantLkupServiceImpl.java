package com.pccw.wq.service;

import java.util.List;


import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.dao.ConstantLkupDAO;
import com.pccw.wq.schema.dto.SelectionItemDTO;
import com.pccw.wq.service.ConstantLkupService;

public class ConstantLkupServiceImpl implements ConstantLkupService{
	
	@Override
	public List<SelectionItemDTO> getWQNature() {
		return ((ConstantLkupDAO) SpringApplicationContext
				.getBean("WQNatureLkup")).getCodeLkup();
	}
	
	@Override
	public List<SelectionItemDTO> getWQType() {
		return ((ConstantLkupDAO) SpringApplicationContext
				.getBean("WQTypeLkup")).getCodeLkup();
	}

	@Override
	public List<SelectionItemDTO> getWQSubType() {
		return ((ConstantLkupDAO) SpringApplicationContext
				.getBean("WQSubTypeLkup")).getCodeLkup();
	}

	@Override
	public List<SelectionItemDTO> getWQStatus() {
		return ((ConstantLkupDAO) SpringApplicationContext
				.getBean("WQStatusLkup")).getCodeLkup();
	}

	@Override
	public List<SelectionItemDTO> getReason() {
		return ((ConstantLkupDAO) SpringApplicationContext
				.getBean("WQReasonLkup")).getCodeLkup();
	}	

//	<!--added by ims steven 20130228 start-->
	@Override
	public List<SelectionItemDTO> getCSPendingReason() {
		return ((ConstantLkupDAO) SpringApplicationContext
				.getBean("WQCSPendingReasonLkup")).getCodeLkup();
	}	

	@Override
	public List<SelectionItemDTO> getCSCancelReason() {
		return ((ConstantLkupDAO) SpringApplicationContext
				.getBean("WQCSCancelReasonLkup")).getCodeLkup();
//		<!--added by ims steven 20130228 end-->
	}	

	@Override
	public List<SelectionItemDTO> getWorkingParty() {
		return ((ConstantLkupDAO) SpringApplicationContext
				.getBean("WorkingPartyLkup")).getCodeLkup();
	}
}