package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.util.ReportUtil;

public class SectionFRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3680128402617464036L;

	private FixedCharge[] eyeFixedCharges = new FixedCharge[30];
	private FixedCharge[] resTelFixedCharges = new FixedCharge[30];
	
	private ArrayList<FixedCharge> eyeItemFixedChargesList = new ArrayList<FixedCharge>();
	private TreeSet<FixedCharge> eyeItemFixedChargesSet = new TreeSet<FixedCharge>();
	private ArrayList<FixedCharge> resTelItemFixedChargesList = new ArrayList<FixedCharge>();
	private TreeSet<FixedCharge> resTelItemFixedChargesSet = new TreeSet<FixedCharge>();

	public SectionFRptDTO() {
		for (int i = 0; i < this.eyeFixedCharges.length; i++) {
			this.eyeFixedCharges[i] = new FixedCharge();
		}
		for (int i = 0; i < this.resTelFixedCharges.length; i++) {
			this.resTelFixedCharges[i] = new FixedCharge();
		}
	}
	
	public void addEyeItemFixedCharge(String pChargeDesc, String pCharge) {
		FixedCharge fixedCharge = new FixedCharge(this.replaceEyeDeviceKeyword(pChargeDesc), pCharge);
		if (!this.eyeItemFixedChargesSet.contains(fixedCharge)) {
			this.eyeItemFixedChargesSet.add(fixedCharge);
			this.eyeItemFixedChargesList.add(fixedCharge);
		}
	}
	
	public void addResTelItemFixedCharge(String pChargeDesc, String pCharge) {
		FixedCharge fixedCharge = new FixedCharge(pChargeDesc, pCharge);
		if (!this.resTelItemFixedChargesSet.contains(fixedCharge)) {
			this.resTelItemFixedChargesSet.add(fixedCharge);
			this.resTelItemFixedChargesList.add(fixedCharge);
		}
	}
	
	private void appendToArrayList(ArrayList<FixedCharge> pList, FixedCharge[] pFixedCharges) {
		if (ArrayUtils.isEmpty(pFixedCharges)) {
			return;
		}
		for (int i = 0; i < pFixedCharges.length; i++) {
			if (StringUtils.isEmpty(pFixedCharges[i].getDescription())) {
				continue;
			}
			pList.add(pFixedCharges[i]);
		}
	}
	
	public ArrayList<FixedCharge> getEyeFixedChargesList() {
		ArrayList<FixedCharge> eyeFixedChargesList = new ArrayList<FixedCharge>();
		appendToArrayList(eyeFixedChargesList, this.eyeFixedCharges);
		return eyeFixedChargesList;
	}
	
	public FixedCharge[] getEyeFixedCharges() {
		return this.eyeFixedCharges;
	}

	public void setEyeFixedCharges(FixedCharge[] pEyeFixedCharges) {
		this.eyeFixedCharges = pEyeFixedCharges;
	}

	public ArrayList<FixedCharge> getEyeItemFixedChargesList() {
		return eyeItemFixedChargesList;
	}

	public boolean isEyeItemFixedChargesListEmpty() {
		return  this.getEyeItemFixedChargesList().isEmpty();
	}

	public boolean isEyeFixedChargesListEmpty() {
		return  this.getEyeFixedChargesList().isEmpty();
	}

	public ArrayList<FixedCharge> getResTelFixedChargesList() {
		ArrayList<FixedCharge> resTelFixedChargesList = new ArrayList<FixedCharge>();
		appendToArrayList(resTelFixedChargesList, this.resTelFixedCharges);
		return resTelFixedChargesList;
	}
	
	public FixedCharge[] getResTelFixedCharges() {
		return this.resTelFixedCharges;
	}

	public void setResTelFixedCharges(FixedCharge[] pResTelFixedCharges) {
		this.resTelFixedCharges = pResTelFixedCharges;
	}

	public ArrayList<FixedCharge> getResTelItemFixedChargesList() {
		return resTelItemFixedChargesList;
	}

	public boolean isResTelItemFixedChargesListEmpty() {
		return  this.getResTelItemFixedChargesList().isEmpty();
	}

	public boolean isResTelFixedChargesListEmpty() {
		return  this.getResTelFixedChargesList().isEmpty();
	}
	
	/**
	 * 
	 */
	public static class FixedCharge implements Serializable, Comparable<FixedCharge> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -710964560077302703L;
		private String description;
		private String charge;

		public FixedCharge() {
			super();
		}

		public FixedCharge(String description, String charge) {
			super();
			this.description = description;
			this.charge = charge;
		}

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