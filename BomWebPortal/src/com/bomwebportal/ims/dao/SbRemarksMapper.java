package com.bomwebportal.ims.dao;

import org.springframework.jdbc.core.RowMapper;

import com.bomwebportal.ims.dto.SbRemarksDTO;
import com.bomwebportal.ims.dto.ServiceDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SbRemarksMapper implements RowMapper {
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	SbRemarksDTO dto = new SbRemarksDTO();
        dto.setHas_rbr(rs.getString("has_rbr"));
        return dto;
    }
}