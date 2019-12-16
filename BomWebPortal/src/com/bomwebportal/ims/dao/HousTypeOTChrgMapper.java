package com.bomwebportal.ims.dao;

import org.springframework.jdbc.core.RowMapper;

import com.bomwebportal.ims.dto.HousTypeOTChrgDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class HousTypeOTChrgMapper implements RowMapper {
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	HousTypeOTChrgDTO dto = new HousTypeOTChrgDTO();
    	dto.setOTChrg(rs.getString("install_ot_amount"));
    	dto.setOTChrgProdType(rs.getString("prod_type"));
    	dto.setOTChrgType(rs.getString("ot_chrg_type"));
    	dto.setInstallOtCode(rs.getString("install_ot_code"));
    	dto.setInstallOTDesc_en(rs.getString("install_ot_desc_en"));
    	dto.setInstallOTDesc_zh(rs.getString("install_ot_desc_zh"));
    	
    	return dto;
    }
}