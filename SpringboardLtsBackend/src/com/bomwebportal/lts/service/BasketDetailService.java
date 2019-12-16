package com.bomwebportal.lts.service;

import com.bomwebportal.exception.DAOException;

public interface BasketDetailService {

	public abstract String getBasketContractPeriod(String pBasketId)
			throws DAOException;

}