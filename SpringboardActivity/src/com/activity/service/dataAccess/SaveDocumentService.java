package com.activity.service.dataAccess;

import com.activity.dto.ActivityAttachDocDTO;

public interface SaveDocumentService {

	void saveAttachDocList(ActivityAttachDocDTO[] pAvailEquips, String pActvId, String pTaskSeq, String pUser);
	
}
