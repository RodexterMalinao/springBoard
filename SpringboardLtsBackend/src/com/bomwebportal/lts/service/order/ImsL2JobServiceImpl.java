package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ImsL2JobDAO;
import com.bomwebportal.lts.dto.order.ImsL2JobDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ImsL2JobServiceImpl extends ServiceActionImplBase {

	public ImsL2JobServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "l2Cd", "ftInd"));
	}
	
	@Override
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO,
			Object... args) {
		ImsL2JobDTO imsL2JobDto = (ImsL2JobDTO)pBaseDTO;
		ImsL2JobDAO imsL2JobDao = (ImsL2JobDAO)this.dao;
		this.DTO2DAO(imsL2JobDto, imsL2JobDao);
		imsL2JobDao.setOrderId((String)args[0]);
		imsL2JobDao.setDtlId((String)args[1]);
	}

	@Override
	protected void setDAO2DTODetails(Object... args) {
		((ImsL2JobDAO)this.dao).setOrderId((String)args[0]);
	}

	@Override
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		ImsL2JobDTO imsL2Job = new ImsL2JobDTO();
		imsL2Job.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, imsL2Job);
		return imsL2Job;
	}

}
