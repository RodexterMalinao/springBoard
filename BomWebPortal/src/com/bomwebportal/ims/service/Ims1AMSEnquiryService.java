package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.Ims1AMSFSAInfoDTO;
import com.bomwebportal.ims.dto.Ims1AMSInfoWithoutPendingDTO;


public interface Ims1AMSEnquiryService{
		
		public List<Ims1AMSFSAInfoDTO> getIms1AMSFSAInfoList2(String i_serbdyno, String i_flat, String i_floor, String i_h_lot_no);
	
		public Ims1AMSInfoWithoutPendingDTO getIms1AMSInfoWithoutPending(String in_FSA);
		
		public String getIms1AMSBandwidth(String in_pid);	
		
	    public boolean checkDs1D1ISimilarAddress(String i_serbdyno, String i_flat, String i_floor, String i_h_lot_no);
		
}