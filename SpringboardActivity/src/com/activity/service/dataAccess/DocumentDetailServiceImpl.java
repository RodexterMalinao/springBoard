package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;

import com.activity.dao.dataAccess.DocumentDetailDAO;
import com.activity.dto.DocumentDTO;
import com.activity.dto.DocumentDetailDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class DocumentDetailServiceImpl extends ServiceActionImplBase {

	public DocumentDetailServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "docType"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "docType", "seqNum"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		DocumentDetailDAO docDao = (DocumentDetailDAO)pDaoBase;
		DocumentDetailDTO doc = (DocumentDetailDTO)this.convertToDto(new DocumentDetailDTO(), docDao);
		doc.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		doc.setProcessDate(DateFormatHelper.dateConvertFromDAO2DTO(docDao.getProcessDate()));
		return doc;
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((DocumentDetailDAO)this.dao).setOrderId((String)pArgs[0]);
		((DocumentDetailDAO)this.dao).setDocType((String)pArgs[1]);
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {

		DocumentDetailDAO docDao = (DocumentDetailDAO)this.dao;
		DocumentDetailDTO doc = (DocumentDetailDTO)pBaseDTO;
		this.DTO2DAO(doc, docDao);
		docDao.setOrderId((String)args[0]);
		docDao.setProcessDate(DateFormatHelper.dateConvertFromDTO2DAO(doc.getProcessDate()));
	}
}
