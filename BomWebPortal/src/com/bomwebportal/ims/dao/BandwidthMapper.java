package com.bomwebportal.ims.dao;

import org.springframework.jdbc.core.RowMapper;

import com.bomwebportal.ims.dto.BandwidthDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class BandwidthMapper implements RowMapper {
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        BandwidthDTO dto = new BandwidthDTO();
        dto.setBandwidth(rs.getString("bandwidth"));
        return dto;
    }
}