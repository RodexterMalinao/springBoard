package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.WaiveLkupDTO;

public interface WaiveService {
	public WaiveLkupDTO getWaiveLkupDTO(String reasonType, String reasonCd);
	public List<WaiveLkupDTO> findWaiveLkupByReasonType(String reasonType, Date appDate);

}
