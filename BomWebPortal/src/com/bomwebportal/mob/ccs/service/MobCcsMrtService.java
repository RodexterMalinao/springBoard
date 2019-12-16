package com.bomwebportal.mob.ccs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;

public interface MobCcsMrtService {
    
    ArrayList<MobCcsMrtBaseDTO> getMobCcsMrtDTO(String orderId);
    int insertMobCcsMrt(MobCcsMrtBaseDTO dto);     
    /**
     *  
     * Covert MrtUI to List of MobCcsMrtBaseDTO
     * @param mrtUI
     * @param vasMrtSelectionDTO
     * @param userName
     * @return List of MobCcsMrtBaseDTO
     */
    public ArrayList<MobCcsMrtBaseDTO> uiDtoChangeToMrtDtoList(MRTUI mrtUI, VasMrtSelectionDTO vasMrtSelectionDTO, String userName);
    /**
     * Convert list of MobCcsMRtBaseDTO to MRTUI
     * @param mrtDto
     * @return
     */
    public ArrayList<MobCcsMrtBaseDTO> mnpDtoChangeToMrtDtoList(MnpDTO mnpDTO, VasMrtSelectionDTO vasMrtSelectionDTO, String userName);
    public MRTUI mrtDtoChangeToUiDto(List<MobCcsMrtBaseDTO> mrtDto);
    /**
      * Covert list of MobCcsMrtBaseDTO to MnpDTO.<br>
     * This coding logic is designed based on the design structure of bomweb_mrt table.<br>
     * Two seqId may exist if more than one mobile number are created in one CCS order. <br>
     * For example New Number + Mnp, New Number + China Number, Mnp + China Number etc...<br>
     * When we select or insert data into bomweb_mrt table,<br>
     * seqId 1 always represents new number (included golden number type)<br>
     * seqId 2 represents china number or mnp number<br>
     * @param mrtDtoList
     * @param shopCd
     * @return
     */
    MnpDTO mrtDtoChangeToMnpDto(ArrayList<? extends MobCcsMrtBaseDTO> mrtDtoList, String shopCd);
    List<String[]> getPCCW3GMrtNumByChannelCd(String channel_cd);
    List<String[]> getPCCW3GMrtNumByStaffId(String staff_id);
    List<String> getDistinctUNICOM1C2NCityCd(String channel_cd);    
    String getNewMRTMsisdnLvl(String msisdn, String msisdnStatus);
    List<String[]> getUnicomMrtNumAndLvl(String channel_cd, String cityCd);
    boolean handleExpiredMRT(String staffId);
    boolean handleFrozenMRT();
    boolean validateUnicomNumStatus(String msisdn);
    boolean validate3GnewMRTStatus(String msisdn);
    List<String[]> getRandomPCCW3GNumFromPool(String channelCd, String numType);
    boolean newMRTSearch(String msisdn, String channelCd, String numType);
    public boolean getNewGoldenNumRulesInd(Date appDate);
    List<HashMap<String, Integer>> getGoldenNumLvlCondDtl(String goldenLvl, String appDate, String mipBrand);
    List<String[]> getGoldenNumRandsomList(String vip, String period, String charge, String grossPlanFee, Date appDate, String mipBrand);
    List<String[]> getGoldenNumSearchList(String vip, String period, String charge, String searchMsisdn , String grossPlanFee, Date appDate, String mipBrand);
    void updateMnpTicketNum(String orderId, String mnpTicketNum, Date cutOverDate, String username);
    void updateMrtServiceDate(String orderId, String mnpInd, Date serviceDate, String username);
    List<String> getPendingOrderExistWithMsisdnByPendingAndFallout(String msisdn);
    List<String[]> getPatternNumSearchList(String channelCd, String teamCdorCenterCd, String searchMsisdn, String numType);
    List<String[]> getPatternNumSearchListCcs(String channelShopCd, String searchMsisdn, String numType);
    List<String[]> getPatternNumSearchListCcsMul(String searchMsisdn);
    List<String> getOrderExistWithMsisdnByPCancelling(String msisdn);
    List<String> getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout(String msisdn, String orderId);
    List<String> getOrderExistWithMsisdnOrderIdByPCancelling(String msisdn, String orderId);
    String getSpecialMsisdnLvl(String msisdn, String msisdnStatus, String approvalSerial);
    /**
     * Retrieve golden lvl from w_golden_num_lvl from
     * @param exclusiveGA true to exclude GA lvl
     * @return
     */
    List<String> getGoldenNumLvL(boolean exclusiveGA);
    /**
     * For report use
     * @param mrtDtoList
     * @return
     */
    MnpDTO convertToMnpDto(ArrayList<? extends MobCcsMrtBaseDTO> mrtDtoList);
	List<String> getFrozenWindow(String inDate);
	List<String[]> getOneCardTwoNumberByFullNumber(String channelCd, String searchMsisdn, String numType);
	List<String[]> getOneCardTwoNumberByRandom(String channelCd, String cityCd, String numType);
}
