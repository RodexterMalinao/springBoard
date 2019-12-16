package com.activity.dao;

import com.activity.dto.SbActvTaskWqActionDTO;
import com.bomwebportal.exception.DAOException;

public interface ActivityTaskWqActionLookupDAO {

	public SbActvTaskWqActionDTO[] getSbActvTaskWqAction(String pActvId,
			String pTaskSeq) throws DAOException;

}