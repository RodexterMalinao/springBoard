package com.bomwebportal.lts.service.qc;

import java.util.List;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.LtsAddressSrvNumDAO;
import com.bomwebportal.lts.dao.qc.OrderLtsSystemFindingDAO;
import com.bomwebportal.lts.dto.qc.OrderLtsSystemFindingDTO;

public class LtsDsOrderDetailServiceImpl implements LtsDsOrderDetailService {
	
	private OrderLtsSystemFindingDAO orderLtsSystemFindingDAO;
	private LtsAddressSrvNumDAO ltsAddressSrvNumDAO;

	public LtsAddressSrvNumDAO getLtsAddressSrvNumDAO() {
		return ltsAddressSrvNumDAO;
	}

	public void setLtsAddressSrvNumDAO(LtsAddressSrvNumDAO ltsAddressSrvNumDAO) {
		this.ltsAddressSrvNumDAO = ltsAddressSrvNumDAO;
	}

	public OrderLtsSystemFindingDAO getOrderLtsSystemFindingDAO() {
		return orderLtsSystemFindingDAO;
	}

	public void setOrderLtsSystemFindingDAO(
			OrderLtsSystemFindingDAO orderLtsSystemFindingDAO) {
		this.orderLtsSystemFindingDAO = orderLtsSystemFindingDAO;
	}

	public boolean checkPCDretentionPeriod(String service_num) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkLTSretentionPeriod(String service_num) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkPCDexistingServcie(String service_num) {
        return false;
		
	};
	
	public List<String> getAddressSrvNum(String i_serbdyno, String i_flat, String i_floor){
		 try{
			 return ltsAddressSrvNumDAO.getLtsAddressSrvNum(i_serbdyno, i_flat, i_floor);
	     }catch (DAOException de) {			
		     throw new AppRuntimeException(de);
	     }
	}

	public List<OrderLtsSystemFindingDTO> sysFchecking() {
		boolean check =false;
		
		try{	
			return orderLtsSystemFindingDAO.sysFchecking();
			
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
		
	};

	public int updateDSSysFinding(OrderLtsSystemFindingDTO dto)
			throws DAOException {
		return orderLtsSystemFindingDAO.updateDSSysFinding(dto);

	}

}
