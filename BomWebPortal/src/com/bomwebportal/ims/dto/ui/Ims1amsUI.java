package com.bomwebportal.ims.dto.ui;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bomwebportal.ims.dto.Ims1AMSFSAInfoDTO;


public class Ims1amsUI {

	private List<Ims1AMSFSAInfoDTO> Ims1AMSFSAInforecords = new ArrayList<Ims1AMSFSAInfoDTO>();

	public List<Ims1AMSFSAInfoDTO> getIms1AMSFSAInforecords() {
		return Ims1AMSFSAInforecords;
	}

	public void setIms1AMSFSAInforecords(
			List<Ims1AMSFSAInfoDTO> ims1amsfsaInforecords) {
		Ims1AMSFSAInforecords = ims1amsfsaInforecords;
	}

	
}



