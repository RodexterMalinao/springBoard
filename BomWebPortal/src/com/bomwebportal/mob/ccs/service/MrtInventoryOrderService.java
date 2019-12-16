package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.mob.ccs.dto.MrtInventoryOrderDTO;

public interface MrtInventoryOrderService {
	MrtInventoryOrderDTO getMrtInventoryOrderDTO(String msisdn, Date stockInDate);
	List<MrtInventoryOrderDTO> getMrtInventoryOrderDTOALL(String msisdn);
}
