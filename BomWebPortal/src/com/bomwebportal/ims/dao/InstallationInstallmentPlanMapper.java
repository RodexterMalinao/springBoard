package com.bomwebportal.ims.dao;

import org.springframework.jdbc.core.RowMapper;

import com.bomwebportal.ims.dto.ImsInstallationInstallmentPlanDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InstallationInstallmentPlanMapper implements RowMapper {
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ImsInstallationInstallmentPlanDTO dto = new ImsInstallationInstallmentPlanDTO();
    	dto.setOneTimeFee(rs.getString("install_ot_amount"));
    	dto.setOtChrgType(rs.getString("OT_CHRG_TYPE"));
    	dto.setInstallOtCode(rs.getString("install_ot_code"));
    	dto.setInstallOTDesc_en(rs.getString("install_ot_desc_en"));
    	dto.setInstallOTDesc_zh(rs.getString("install_ot_desc_zh"));
    	dto.setInstallmentCode(rs.getString("INSTALL_INSTALMENT_CODE"));
    	dto.setInstallmentPlanAmt(rs.getString("INSTALL_INSTALMENT_AMT"));
    	dto.setInstallmentPlanMnth(rs.getString("INSTALL_INSTALMENT_MTH"));
        dto.setInstallPlanValue(rs.getString("INSTALL_INSTALMENT_AMT")+","+rs.getString("INSTALL_INSTALMENT_MTH"));
        dto.setInstallPlanDisplay(rs.getString("INSTALL_INSTALMENT_AMT")+" X "+rs.getString("INSTALL_INSTALMENT_MTH") +" Month ");
        dto.setInstallOtQty(rs.getString("install_ot_qty"));
    	return dto;
    }
}