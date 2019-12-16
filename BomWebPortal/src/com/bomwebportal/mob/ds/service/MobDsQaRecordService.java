package com.bomwebportal.mob.ds.service;

import java.util.List;

import com.bomwebportal.mob.ds.dto.MobDsQaRecordDTO;

public interface MobDsQaRecordService {

	public List<MobDsQaRecordDTO> getQARecord(String orderId);
	
	public MobDsQaRecordDTO getQARecordBySeq(List<MobDsQaRecordDTO> recordList, int seq);
	
	public int insertQARecord(String orderId, int seqNo,String mustQ, 
			int qaOption, String remarks, String username);
	
	public boolean isSupervisorApproved(String orderId);
	
	public int setMustQStatus(String orderId, String username);
	
	public int updateQaOrderStatus(String orderId, String status, String username);
	
	public int updateOrderApproveInd(String orderId, String username);
}
