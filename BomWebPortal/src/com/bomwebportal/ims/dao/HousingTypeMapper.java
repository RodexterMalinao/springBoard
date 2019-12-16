package com.bomwebportal.ims.dao;

import org.springframework.jdbc.core.RowMapper;

import com.bomwebportal.ims.dto.HousingTypeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class HousingTypeMapper implements RowMapper {
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	HousingTypeDTO dto = new HousingTypeDTO();
    	dto.setHousingType(rs.getString("housing_type"));
        return dto;
    }
}