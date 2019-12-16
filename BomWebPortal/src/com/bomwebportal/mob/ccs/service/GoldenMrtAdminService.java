package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.mob.ccs.dto.GoldenMrtAdminDTO;

public interface GoldenMrtAdminService {
    public GoldenMrtAdminDTO getGoldenMrtAdminDTO(String rowId);
    public List<GoldenMrtAdminDTO> getGoldenMrtAdminDTOALL(String orderId);
    public GoldenMrtAdminDTO getGoldenMrtAdminDTOByOrderId(String orderId);
    public int insertGoldenMrtAdmin(GoldenMrtAdminDTO dto);
    public int updateGoldenMrtAdmin(GoldenMrtAdminDTO dto);
    public int updateGoldenMrtAdminMsisdnStatus(String orderId, String status);
    public int deleteGoldenMrtAdmin(GoldenMrtAdminDTO dto);
    
    public List<GoldenMrtAdminDTO> getGoldenMrtAdminDTOByDate(Date startDate, Date endDate);
    public int updateGoldenMrtAdminForAdmin(GoldenMrtAdminDTO dto);
    public int updateGoldenMrtAdminForManager(GoldenMrtAdminDTO dto);
    
    //add Eliot 20120210
//    public void insertGoldenMrtAdmin(CustomerProfileDTO customerInfoDto, StaffInfoUI sessionStaffInfo,
//		MRTUI mrtUI, BasketDTO basketDto, String orderId);
//    public void updateGoldenMrtAdmin(CustomerProfileDTO customerInfoDto, StaffInfoUI sessionStaffInfo,
//		MRTUI mrtUI, BasketDTO basketDto, String orderId);
}
