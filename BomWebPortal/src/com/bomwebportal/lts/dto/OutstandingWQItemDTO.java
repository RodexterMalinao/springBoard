package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class OutstandingWQItemDTO implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4921216914508858368L;
		private String wqBatchId;
		private String wqId;
		private String sbId;
		public String getWqBatchId() {
			return this.wqBatchId;
		}
		public void setWqBatchId(String pWqBatchId) {
			this.wqBatchId = pWqBatchId;
		}
		public String getWqId() {
			return this.wqId;
		}
		public void setWqId(String pWqId) {
			this.wqId = pWqId;
		}
		public String getSbId() {
			return this.sbId;
		}
		public void setSbId(String pSbId) {
			this.sbId = pSbId;
		}
	}