package com.bomwebportal.lts.service.qc;

import java.util.List;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.OrderImsSystemFindingDTO;
import com.bomwebportal.lts.dto.qc.OrderLtsSystemFindingDTO;



public interface LtsDsOrderDetailService {
	public boolean checkPCDretentionPeriod(String service_num);
	public boolean checkLTSretentionPeriod(String service_num);
	public boolean checkPCDexistingServcie(String service_num);
	public List<OrderLtsSystemFindingDTO> sysFchecking();
	public List<String> getAddressSrvNum(String i_serbdyno, String i_flat, String i_floor);
	public int updateDSSysFinding(OrderLtsSystemFindingDTO dto) throws DAOException;
}
