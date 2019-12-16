package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.PcRelationshipDTO;

public interface RelationshipCheckService {
	List<PcRelationshipDTO> checkRelationshipSb(String basketId, Date appInDate, String[] vasItemIds, String mipBrand, String mipSimType);
}
