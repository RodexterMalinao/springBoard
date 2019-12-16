package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ui.DsDedicatedStaffUI;

public interface ImsDsDedicatedStaffService {
	
	public List<DsDedicatedStaffUI> getDsDedicatedStaff() throws DAOException;
	
	public boolean isStaffExit(String staffId) throws DAOException;
	
	public void updateDsDedicatedStaff(DsDedicatedStaffUI dsDedicatedStaffUI) throws DAOException;
}
