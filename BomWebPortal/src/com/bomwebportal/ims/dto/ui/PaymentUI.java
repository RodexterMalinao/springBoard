package com.bomwebportal.ims.dto.ui;

import com.bomwebportal.ims.dto.PaymentDTO;

public class PaymentUI extends PaymentDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7882600480217772882L;
	private String ActionInd;
	private String textAreaInfo;
	private String textAreaInfoWP;
	private String textAreaInfoWO;
	private String textAreaInfoDO;

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}

	public String getTextAreaInfo() {
		return textAreaInfo;
	}

	public void setTextAreaInfo(String textAreaInfo) {
		this.textAreaInfo = textAreaInfo;
	}

	public String getTextAreaInfoWP() {
		return textAreaInfoWP;
	}

	public void setTextAreaInfoWP(String textAreaInfoWP) {
		this.textAreaInfoWP = textAreaInfoWP;
	}

		public String getTextAreaInfoWO() {
			return textAreaInfoWO;
		}

		public void setTextAreaInfoWO(String textAreaInfoWO) {
			this.textAreaInfoWO = textAreaInfoWO;
	}

		public void setTextAreaInfoDO(String textAreaInfoDO) {
			this.textAreaInfoDO = textAreaInfoDO;
		}

		public String getTextAreaInfoDO() {
			return textAreaInfoDO;
		}

}
