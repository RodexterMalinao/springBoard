package smartliving.backend.dto;

import java.io.Serializable;
import java.util.List;

public class OrderDocDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1066507900269502868L;
	
	private boolean selected;
	private String docType;
	private String mdoInd;
	private String type;
	private String docName;
	private String docNameChi;
	private String docDesc;
	private String startDate;
	private String endDate;
	private String uploadMethod;
	
	private List<DocWaiveReasonDTO> waiveReasonList;
	private boolean collected;
	private String waiveReasonCd;
	private String waiveReasonBy;
	private boolean markDelete;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getMdoInd() {
		return mdoInd;
	}
	public void setMdoInd(String mdoInd) {
		this.mdoInd = mdoInd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocNameChi() {
		return docNameChi;
	}
	public void setDocNameChi(String docNameChi) {
		this.docNameChi = docNameChi;
	}
	public String getDocDesc() {
		return docDesc;
	}
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<DocWaiveReasonDTO> getWaiveReasonList() {
		return waiveReasonList;
	}
	public void setWaiveReasonList(List<DocWaiveReasonDTO> waiveReasonList) {
		this.waiveReasonList = waiveReasonList;
	}
	public boolean isCollected() {
		return collected;
	}
	public void setCollected(boolean collected) {
		this.collected = collected;
	}
	public String getWaiveReasonCd() {
		return waiveReasonCd;
	}
	public void setWaiveReasonCd(String waiveReasonCd) {
		this.waiveReasonCd = waiveReasonCd;
	}
	public String getWaiveReasonBy() {
		return waiveReasonBy;
	}
	public void setWaiveReasonBy(String waiveReasonBy) {
		this.waiveReasonBy = waiveReasonBy;
	}
	public boolean isMarkDelete() {
		return markDelete;
	}
	public void setMarkDelete(boolean markDelete) {
		this.markDelete = markDelete;
	}
	public String getUploadMethod() {
		return this.uploadMethod;
	}
	public void setUploadMethod(String pUploadMethod) {
		this.uploadMethod = pUploadMethod;
	}
	
	

}
