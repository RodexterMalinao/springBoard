package com.bomwebportal.ims.dao;

import org.springframework.jdbc.core.RowMapper;

import com.bomwebportal.ims.dto.BomwebOTDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class BomwebOTMapper implements RowMapper {
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	BomwebOTDTO dto = new BomwebOTDTO();
    	
    	dto.setId(rs.getInt("ID"));
    	dto.setInstallWaiveInd(rs.getString("INSTALL_WAIVE_IND"));
    	dto.setInstallOTAmt(rs.getString("INSTALL_OT_AMT"));
    	dto.setInstallOTCode(rs.getString("INSTALL_OT_CODE"));
    	dto.setInstallOTQty(rs.getString("install_ot_qty"));
    	dto.setInstallInstalmentCode(rs.getString("install_instalment_code"));
    	dto.setActivateWaiveInd(rs.getString("activate_waive_ind"));
    	dto.setActivateOTAmt(rs.getString("ACTIVATE_OT_AMT"));
    	dto.setActivateOTCode(rs.getString("ACTIVATE_OT_CODE"));
    	dto.setActivateOTQty(rs.getString("ACTIVATE_ot_qty"));
    	dto.setActivateInstalmentCode(rs.getString("ACTIVATE_instalment_code"));
    	dto.setInstallInstalmentAmt(rs.getString("install_instalment_amt"));
    	dto.setInstallInstalmentMth(rs.getString("install_instalment_mth"));
    	dto.setInstallChrgDescEn(rs.getString("INSTALL_CHRG_DESC_EN"));
    	dto.setInstallChrgDescCn(rs.getString("INSTALL_CHRG_DESC_CN"));
    	dto.setHideOriginalFee(rs.getString("hide_original_fee"));
        return dto;
    }
}