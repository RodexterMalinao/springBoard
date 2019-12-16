package com.bomwebportal.service;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public abstract class SaveService {

	protected String user = null;
	
	protected void setObjectAction(ObjectActionBaseDTO[] pDtos, int pAction) {
		
		for (int i=0; pDtos!=null && i<pDtos.length; ++i) {
			this.setObjectAction(pDtos[i], pAction);
		}
	}
	
	protected void setObjectAction(ObjectActionBaseDTO pDto, int pAction) {
		
		if (pDto != null) {
			pDto.setObjectAction(pAction);	
		}
	}
}
