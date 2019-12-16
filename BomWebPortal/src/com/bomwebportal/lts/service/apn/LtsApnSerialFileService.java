package com.bomwebportal.lts.service.apn;

import java.util.Date;
import java.util.List;

import com.bomwebportal.lts.dto.apn.LtsApnDnDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.apn.LtsApnResultDTO;

public interface LtsApnSerialFileService {
	public List<LtsApnFileDTO> searchForApnSerialFiles(String pFrom, String pTo, String pStatus);
	
	public void inputApnSerialFile(String pBatchSeq, String pFileName, String pFileStatus, String pFailReason, byte[] pFile, String pUser);
	
	public void inputApnSerialDtlRecord(LtsApnDnDTO pInfo, String pUser);
	
	public LtsApnDnDTO getApnDtlInfo(String pSrvNum, String pSrvNn, String pCounter, String pBatchSeq, Date pStartTime, Date pEndTime);
	
	public String assignBatchSeq();
	
	public LtsApnFileDTO getApnSerialFileInfo(String pBatchSeq);
	
	public boolean isFileNameAvailable(String pFileName);
	
	public boolean isApnExist(String orderId);
	
	public boolean isDuplexCompleted(String pOrderId);
}
