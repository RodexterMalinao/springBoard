package smartliving.backend.dto;

import com.bomwebportal.dto.OrdDocDTO;

public class OrdDocSLVDTO extends OrdDocDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8774149620416934236L;
	private String remark;

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String pRemark) {
		this.remark = pRemark;
	}
	
	
}
