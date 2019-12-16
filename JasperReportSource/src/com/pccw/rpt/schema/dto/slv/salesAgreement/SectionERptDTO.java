package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.util.ReportUtil;

public class SectionERptDTO extends FixContentRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8037760748694852960L;
	private FixedCharge[] slvCharges = new FixedCharge[30];

	private ArrayList<FixedCharge> slvChargesList = new ArrayList<FixedCharge>();
	//private TreeSet<FixedCharge> slvChargesSet = new TreeSet<FixedCharge>();
	
	public SectionERptDTO() {
		for (int i = 0; i < this.slvCharges.length; i++) {
			this.slvCharges[i] = new FixedCharge();
		}
	}
	
	public void addSlvCharge(String pChargeDesc, String pCharge) {
		if (StringUtils.isBlank(pChargeDesc)) {
			return;
		}
		this.slvChargesList.add(new FixedCharge(pChargeDesc, pCharge));
	}
	
	public ArrayList<FixedCharge> getSlvChargesList() {
		return this.slvChargesList;
	}
	
	public FixedCharge[] getSlvCharges() {
		return this.slvCharges;
	}

	public void setSlvCharges(FixedCharge[] pSlvCharges) {
		this.slvCharges = pSlvCharges;
	}

	public boolean isSlvChargesListEmpty() {
		return this.slvChargesList.isEmpty();
	}
	
	/**
	 * 
	 */
	public static class FixedCharge implements Serializable, Comparable<FixedCharge> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -710964560077302703L;
		//private String productLine;
		private String description;
		private String charge;

		public FixedCharge() {
			super();
		}

		public FixedCharge(String description, String charge) {
			super();
			//this.productLine = productLine;
			this.description = description;
			this.charge = charge;
		}
/*		
		public String getProductLine() {
			return this.productLine;
		}

		public void setProductLine(String pProductLine) {
			this.productLine = pProductLine;
		}
*/
		public String getDescription() {
			return ReportUtil.defaultString(this.description);
		}

		public void setDescription(String pDescription) {
			this.description = pDescription;
		}

		public String getCharge() {
			return ReportUtil.defaultString(this.charge);
		}

		public void setCharge(String pCharge) {
			this.charge = pCharge;
		}
		
		@Override
		public int compareTo(FixedCharge pFixedCharge) {
			return (StringUtils.defaultIfEmpty(this.description, "") + "^" + StringUtils.defaultIfEmpty(this.charge, "")).compareTo(
						(StringUtils.defaultIfEmpty(pFixedCharge.getDescription(), "") + "^" + StringUtils.defaultIfEmpty(pFixedCharge.getCharge(), "")));
		}
	}
}