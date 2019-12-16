package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrBasketTypeDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrBrandModelDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrCustomerTierDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrRatePlanDTO;

public interface MobCcsBasketAssoWBasketAttrService {
	List<BasketAssoWBasketAttrCustomerTierDTO> getWBasketAttrCustomerTierDTOALL();
	List<BasketAssoWBasketAttrBasketTypeDTO> getWBasketAttrBasketTypeDTOALL();
	List<BasketAssoWBasketAttrRatePlanDTO> getWBasketAttrRatePlanDTOBySearch(BasketAssoWBasketAttrDTO dto);
	List<BasketAssoWBasketAttrRatePlanDTO> getWBasketAttrRatePlanDTOALL();
	List<BasketAssoWBasketAttrBrandModelDTO> getWBasketAttrBrandModelDTOALL();
	List<BasketAssoWBasketAttrBrandModelDTO> getWBasketAttrBrandModelDTOBySearch(BasketAssoWBasketAttrDTO dto);
	List<BasketAssoWBasketAttrDTO> getBasketAssoWBasketAttrDTOBySearch(BasketAssoWBasketAttrDTO dto);
	List<BasketAssoWBasketAttrDTO> getBasketAssoWBasketAttrDTOBySearch(String basketDesc);
	List<BasketAssoWBasketAttrDTO> getBasketAssoWBasketAttrDTOBySearch(BasketAssoWBasketAttrDTO dto, String basketDesc);
}
