package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ContactLtsDAO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class ContactLtsServiceImpl extends ServiceActionImplBase {

	public ContactLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		ContactLtsDTO contact = new ContactLtsDTO();
		ContactLtsDAO contactDao = (ContactLtsDAO)pDaoBase;
		contact.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(contactDao, contact);
		contact.setOldContactMobileDate(DateFormatHelper.dateConvertFromDAO2DTO(contactDao.getOldContactMobileDate()));
		contact.setOldEmailAddrDate(DateFormatHelper.dateConvertFromDAO2DTO(contactDao.getOldEmailAddrDate()));
		return contact;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ContactLtsDTO contact = (ContactLtsDTO) pBaseDTO;
		ContactLtsDAO contactDao = (ContactLtsDAO)this.dao;
		this.DTO2DAO(contact, contactDao);
		contactDao.setOrderId((String)args[0]);
		contactDao.setOldContactMobileDate(DateFormatHelper.dateConvertFromDTO2DAO(contact.getOldContactMobileDate()));
		contactDao.setOldEmailAddrDate(DateFormatHelper.dateConvertFromDTO2DAO(contact.getOldEmailAddrDate()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ContactLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
