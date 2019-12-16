package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.List;

public class StockModelDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4534084401540114773L;
	
	private String type;
	private String model;
	private List<StockModelResultDTO> stockModelResult;
	
	private String actionType;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setStockModelResult(List<StockModelResultDTO> stockModelResult) {
		this.stockModelResult = stockModelResult;
	}
	public List<StockModelResultDTO> getStockModelResult() {
		return stockModelResult;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
