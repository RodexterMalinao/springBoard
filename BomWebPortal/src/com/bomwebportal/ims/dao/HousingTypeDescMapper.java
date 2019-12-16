package com.bomwebportal.ims.dao;

import org.springframework.jdbc.core.RowMapper;

import com.bomwebportal.ims.dto.HousingTypeDescDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class HousingTypeDescMapper implements RowMapper {
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	HousingTypeDescDTO dto = new HousingTypeDescDTO();
    	dto.setHousingTypeDesc(rs.getString("housing_type_desc"));
        return dto;
    }
}