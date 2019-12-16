package com.activity.service.dataAccess;

import org.springframework.transaction.annotation.Transactional;

import com.activity.dto.ActivityAttachDocDTO;
import com.activity.dto.ActivityDTO;
import com.bomwebportal.dto.RemarkDTO;


public interface RetrieveActivityService {

	@Transactional
	public abstract ActivityDTO retrieveActivity(String pActvId, String pUser);
	
	public abstract  ActivityAttachDocDTO[] getAttachedDocs(String pActvid, String pTaskSeq);
	
	public abstract RemarkDTO[] getRemarks(String pActvid, String pTaskSeq, String pStatusSeq);

}