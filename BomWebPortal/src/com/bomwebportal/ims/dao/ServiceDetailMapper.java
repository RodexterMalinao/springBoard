package com.bomwebportal.ims.dao;

import org.springframework.jdbc.core.RowMapper;

import com.bomwebportal.ims.dto.ServiceDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ServiceDetailMapper implements RowMapper {
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ServiceDetailDTO dto = new ServiceDetailDTO();
        dto.setTechnology(rs.getString("technology"));
        dto.setTechnologySupported(rs.getString("technology_supported"));
        dto.setIsDeadCase(rs.getString("is_dead_case"));
        dto.setIsResrcShort(rs.getString("is_resrc_short"));
        dto.setLeadTime(rs.getInt("leadtime"));
        dto.setEstEarliestSrd(rs.getDate("est_earliest_srd"));
        dto.setRtnCd(rs.getString("rtn_cd"));
        dto.setRtnDesc(rs.getString("rtn_desc"));
        dto.setHasSDTV(rs.getString("sdtv"));
        dto.setHasHDTV(rs.getString("hdtv"));
        dto.setHasSPHDTV(rs.getString("sphdtv"));
        return dto;
    }
}