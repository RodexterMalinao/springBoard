package com.bomwebportal.ims.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsDSDedicatedStaffDAO;
import com.bomwebportal.ims.dto.ui.DsDedicatedStaffUI;

public class ImsDsDedicatedStaffServiceImpl implements ImsDsDedicatedStaffService {

	protected final Log logger = LogFactory.getLog(getClass());

	private ImsDSDedicatedStaffDAO imsDSDedicatedStaffDAO;

	public ImsDSDedicatedStaffDAO getImsDSDedicatedStaffDAO() {
		return imsDSDedicatedStaffDAO;
	}

	public void setImsDSDedicatedStaffDAO(ImsDSDedicatedStaffDAO imsDSDedicatedStaffDAO) {
		this.imsDSDedicatedStaffDAO = imsDSDedicatedStaffDAO;
	}

	public List<DsDedicatedStaffUI> getDsDedicatedStaff() throws DAOException {
		List<DsDedicatedStaffUI> staffList = new ArrayList<DsDedicatedStaffUI>();
		try {
			List<String> staffListName = imsDSDedicatedStaffDAO.getStaffListName();
			for (String temp : staffListName) {
				DsDedicatedStaffUI dsDedicatedStaffUI = new DsDedicatedStaffUI();
				dsDedicatedStaffUI.setStaffListName(temp);
				dsDedicatedStaffUI.setStaffIdList(imsDSDedicatedStaffDAO.getStaffList(temp));
				dsDedicatedStaffUI.setOfferDescList(imsDSDedicatedStaffDAO.getOfferDescList(temp));
				dsDedicatedStaffUI.setVasDescList(imsDSDedicatedStaffDAO.getVasDescList(temp));
				dsDedicatedStaffUI.setGiftDescList(imsDSDedicatedStaffDAO.getGiftDescList(temp));

				String staffListInput = "";
				for (String staffId : dsDedicatedStaffUI.getStaffIdList()) {
					staffListInput += staffId + ", ";
				}

				if (staffListInput.length() > 0)
					staffListInput = staffListInput.substring(0, staffListInput.length() - 2);

				dsDedicatedStaffUI.setStaffListInput(staffListInput);

				staffList.add(dsDedicatedStaffUI);
			}

			return staffList;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isStaffExit(String staffId) throws DAOException {
		try {
			return imsDSDedicatedStaffDAO.isStaffExist(staffId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void updateDsDedicatedStaff(DsDedicatedStaffUI dsDedicatedStaffUI) throws DAOException {
		try {
			imsDSDedicatedStaffDAO.deleteDsDelicatedStaff(dsDedicatedStaffUI.getStaffListName());
			if(dsDedicatedStaffUI.getStaffIdList()!=null&&dsDedicatedStaffUI.getStaffIdList().size()>0){
				for (String staffId : dsDedicatedStaffUI.getStaffIdList()) {
					logger.debug(dsDedicatedStaffUI.getStaffListName() + " " + staffId.trim() + " "
							+ dsDedicatedStaffUI.getCreateBy());
					imsDSDedicatedStaffDAO.insertDsDelicatedStaff(dsDedicatedStaffUI.getStaffListName(), staffId.trim(),
							dsDedicatedStaffUI.getCreateBy());
				}
			}else{
				imsDSDedicatedStaffDAO.insertDsDelicatedStaff(dsDedicatedStaffUI.getStaffListName(), " ",
						dsDedicatedStaffUI.getCreateBy());
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

}
