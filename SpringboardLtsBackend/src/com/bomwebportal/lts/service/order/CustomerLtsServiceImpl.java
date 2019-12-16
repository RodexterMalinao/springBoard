package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.CustomerLtsDAO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class CustomerLtsServiceImpl extends ServiceActionImplBase {

	public CustomerLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "custNo"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		CustomerDetailLtsDTO customer = new CustomerDetailLtsDTO();
		CustomerLtsDAO customerLtsDao = (CustomerLtsDAO)pDaoBase;
		customer.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, customer);
		customer.setDob(DateFormatHelper.dateConvertFromDAO2DTO(customerLtsDao.getDob()));
		return customer;
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		CustomerDetailLtsDTO customer = (CustomerDetailLtsDTO)pBaseDTO;
		CustomerLtsDAO customerDao = (CustomerLtsDAO)this.dao;
		this.DTO2DAO(customer, customerDao);
		customerDao.setOrderId((String)args[0]);
		customerDao.setDob(DateFormatHelper.dateConvertFromDTO2DAO(customer.getDob()));
	}
	
	protected void setDAO2DTODetails(Object... pArgs) {
		((CustomerLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}