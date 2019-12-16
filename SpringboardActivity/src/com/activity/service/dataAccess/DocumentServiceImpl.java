package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;


import com.activity.dao.dataAccess.DocumentDAO;
import com.activity.dto.DocumentDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class DocumentServiceImpl extends ServiceActionImplBase {

	public DocumentServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "docType"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		DocumentDTO doc = (DocumentDTO)this.convertToDto(new DocumentDTO(), pDaoBase);
		doc.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		return doc;
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((DocumentDAO)this.dao).setOrderId((String)pArgs[0]);
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {

		DocumentDAO docDao = (DocumentDAO)this.dao;
		this.DTO2DAO(pBaseDTO, docDao);
		docDao.setOrderId((String)args[0]);
	}
}
