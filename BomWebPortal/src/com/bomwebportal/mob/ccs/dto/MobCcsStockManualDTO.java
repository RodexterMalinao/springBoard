package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.List;

public class MobCcsStockManualDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 558052356778043560L;
	
	private String type;
	private String model;
	private String itemCode;
	private List<MobCcsStockManualResultDTO> mobCcsStockManualResult;
	
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
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public List<MobCcsStockManualResultDTO> getMobCcsStockManualResult() {
		return mobCcsStockManualResult;
	}
	public void setMobCcsStockManualResult(
			List<MobCcsStockManualResultDTO> mobCcsStockManualResult) {
		this.mobCcsStockManualResult = mobCcsStockManualResult;
	}

}
