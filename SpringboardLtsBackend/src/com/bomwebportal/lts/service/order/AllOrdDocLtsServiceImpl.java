package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.AllOrdDocAssgnLtsDAO;
import com.bomwebportal.lts.dao.order.AllOrdDocLtsDAO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class AllOrdDocLtsServiceImpl extends ServiceActionImplBase {

	public AllOrdDocLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "docType", "seqNum"));
	}
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		AllOrdDocLtsDTO allOrdDoc = new AllOrdDocLtsDTO();
		AllOrdDocLtsDAO allOrdDocDao = (AllOrdDocLtsDAO)pDaoBase;
		allOrdDoc.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, allOrdDoc);
		allOrdDoc.setProcessDate(LtsDateFormatHelper.dateConvertFromDAO2DTO(allOrdDocDao.getProcessDate()));
		return allOrdDoc;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		AllOrdDocLtsDTO allOrdDoc = (AllOrdDocLtsDTO)pBaseDTO;
		AllOrdDocLtsDAO allOrdDocDao = (AllOrdDocLtsDAO)this.dao;
		this.DTO2DAO(allOrdDoc, allOrdDocDao);
		allOrdDocDao.setProcessDate(LtsDateFormatHelper.dateConvertFromDTO2DAO(allOrdDoc.getProcessDate()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((AllOrdDocLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
