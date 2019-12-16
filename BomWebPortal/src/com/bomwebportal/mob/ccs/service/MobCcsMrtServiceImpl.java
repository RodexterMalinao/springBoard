package com.bomwebportal.mob.ccs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.MnpDAO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsMrtDAO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtChinaDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtMnpDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.util.BeanUtilsHelper;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsMrtServiceImpl implements MobCcsMrtService{
    
    protected final Log logger = LogFactory.getLog(getClass()); 
    
    private MobCcsMrtDAO  mobCcsMrtDAO;
    
    private MnpDAO mnpDAO;
    
    /**
	 * @return the mnpDAO
	 */
	public MnpDAO getMnpDAO() {
		return mnpDAO;
	}

	/**
	 * @param mnpDAO the mnpDAO to set
	 */
	public void setMnpDAO(MnpDAO mnpDAO) {
		this.mnpDAO = mnpDAO;
	}

	public MobCcsMrtDAO getMobCcsMrtDAO() {
        return mobCcsMrtDAO;
    }

    public void setMobCcsMrtDAO(MobCcsMrtDAO mobCcsMrtDAO) {
        this.mobCcsMrtDAO = mobCcsMrtDAO;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public ArrayList<MobCcsMrtBaseDTO> getMobCcsMrtDTO(String orderId) {
	try {
	    logger.info("getMobCcsMrtDTO() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getMobCcsMrtDTO(orderId);

	} catch (DAOException de) {
	    logger.error("Exception caught in getMobCcsMrtDTO()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public int insertMobCcsMrt(MobCcsMrtBaseDTO dto) {
	try {
	    logger.info("insertMobCcsMrt() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.insertMobCcsMrt(dto);

	} catch (DAOException de) {
	    logger.error("Exception caught in insertMobCcsMrt()", de);
	    throw new AppRuntimeException(de);
	}
    }
    /**
     * Covert MrtUI to List of MobCcsMrtBaseDTO
     */
    public ArrayList<MobCcsMrtBaseDTO> uiDtoChangeToMrtDtoList(MRTUI mrtUI, VasMrtSelectionDTO vasMrtSelectionDTO, String userName){
    	
	ArrayList<MobCcsMrtBaseDTO> mobCcsMrtList = new ArrayList<MobCcsMrtBaseDTO>();
	
		if (vasMrtSelectionDTO != null && "Y".equalsIgnoreCase(vasMrtSelectionDTO.getChinaInd())) {//China number + ?
			
			if ("Y".equals(mrtUI.getMnpInd())) {//China number + mnp
				MobCcsMrtMnpDTO mnp = new MobCcsMrtMnpDTO();
				MobCcsMrtChinaDTO china = new MobCcsMrtChinaDTO();
				MnpDTO mnpDto = new MnpDTO();
				
				BeanUtilsHelper.copyProperties(mrtUI, china);
				BeanUtilsHelper.copyProperties(mrtUI, mnp);
				
				mnp.setChinaInd("Y");
				china.setChinaInd("Y");
				
				mnp.setMsisdn(mrtUI.getMnpMsisdn());
				mnp.setCutOverDate(Utility.string2Date(mrtUI.getCutOverDateStr()));
				mnp.setCutOverTime(mrtUI.getCutOverTime());
				
				//mnp.setRno("M3");
				mnp.setRno(Utility.getRno(mrtUI.getSimType()));
				
				
				mnp.setSeqId(1);
				if(StringUtils.isNotEmpty(mrtUI.getOrigActDateStr())){
					mnp.setOrigActDate(Utility.string2Date(mrtUI.getOrigActDateStr()));
				}
				
				//to get dno of mnp bean
				BeanUtilsHelper.copyProperties(mnp, mnpDto);
				
				/*try {
					mnpDto = mnpDAO.checkAdMsisdn(mnpDto);
				} catch (DAOException e) {
					e.printStackTrace();
				}*/
				mnp.setNumType(mrtUI.getNumType()); //DENNIS MIP3
				mnp.setDno(mrtUI.getDno());  //DENNIS MIP3
				//mnp.setDno(mnpDto.getDno());
				mnp.setActualDno(mrtUI.getActualDno());
				
				
				china.setMsisdn(vasMrtSelectionDTO.getMsisdn());
				china.setMsisdnLvl(vasMrtSelectionDTO.getMsisdnLvl());
				china.setCityCd(vasMrtSelectionDTO.getCityCd());
				china.setCutOverDate(Utility.string2Date(mrtUI.getCutOverDateStr()));
				china.setCutOverTime(mrtUI.getCutOverTime());
				china.setSeqId(2);
				china.setNumType(vasMrtSelectionDTO.getNumType()); //DENNIS MIP3
				
				mobCcsMrtList.add(mnp);
				mobCcsMrtList.add(china);
				
			} else {//china number + new number
				
				MobCcsMrtDTO mrt = new MobCcsMrtDTO();
				MobCcsMrtChinaDTO china = new MobCcsMrtChinaDTO();
				
				BeanUtilsHelper.copyProperties(mrtUI, china);
				BeanUtilsHelper.copyProperties(mrtUI, mrt);
				
				mrt.setChinaInd("Y");
				china.setChinaInd("Y");
				
				mrt.setMsisdn(mrtUI.getMobMsisdn());
				mrt.setServiceReqDate(Utility.string2Date(mrtUI.getServiceReqDateStr()));
				//mrt.setRno("M3");
				mrt.setRno(Utility.getRno(mrtUI.getSimType()));
				
				mrt.setDno(null);
				mrt.setActualDno(null);
				
				
				mrt.setSeqId(1);
				mrt.setNumType(mrtUI.getNumType()); //DENNIS MIP3

				china.setMsisdn(vasMrtSelectionDTO.getMsisdn());
				china.setMsisdnLvl(vasMrtSelectionDTO.getMsisdnLvl());
				china.setCityCd(vasMrtSelectionDTO.getCityCd());
				china.setServiceReqDate(Utility.string2Date(mrtUI.getServiceReqDateStr()));
				china.setSeqId(2);
				china.setNumType(vasMrtSelectionDTO.getNumType()); //DENNIS MIP3
				
				mobCcsMrtList.add(mrt);
				mobCcsMrtList.add(china);
			}
						
		
		} else if ("Y".equals(mrtUI.getMnpInd())) {//Mnp only
			
			MobCcsMrtMnpDTO mnp = new MobCcsMrtMnpDTO();
			MnpDTO mnpDto = new MnpDTO();
			
			BeanUtilsHelper.copyProperties(mrtUI, mnp);
			
			mnp.setMsisdn(mrtUI.getMnpMsisdn());
			mnp.setCutOverDate(Utility.string2Date(mrtUI.getCutOverDateStr()));
			mnp.setCutOverTime(mrtUI.getCutOverTime());
			//mnp.setRno("M3");
			mnp.setRno(Utility.getRno(mrtUI.getSimType()));
			
			
			mnp.setSeqId(1);
			if(StringUtils.isNotEmpty(mrtUI.getOrigActDateStr())){
				mnp.setOrigActDate(Utility.string2Date(mrtUI.getOrigActDateStr()));
			}
			
			//to get dno of mnp bean
			BeanUtilsHelper.copyProperties(mnp, mnpDto);
			
			/*try {
				mnpDto = mnpDAO.checkAdMsisdn(mnpDto);
			} catch (DAOException e) {
				e.printStackTrace();
			}*/
			
			mnp.setNumType(mrtUI.getNumType()); //DENNIS MIP3
			mnp.setDno(mrtUI.getDno());  //DENNIS MIP3
			//mnp.setDno(mnpDto.getDno());
			mnp.setActualDno(mrtUI.getActualDno());
			
			mobCcsMrtList.add(mnp);
			
		} else if ("A".equals(mrtUI.getMnpInd())) { //mnp + new number
			
			MobCcsMrtDTO mrt = new MobCcsMrtDTO();
			MobCcsMrtMnpDTO mnp = new MobCcsMrtMnpDTO();
			MnpDTO mnpDto = new MnpDTO();
			
			BeanUtilsHelper.copyProperties(mrtUI, mrt);
			BeanUtilsHelper.copyProperties(mrtUI, mnp);
			
			mrt.setMsisdn(mrtUI.getMobMsisdn());
			mrt.setServiceReqDate(Utility.string2Date(mrtUI.getServiceReqDateStr()));
			//mrt.setRno("M3");
			mrt.setRno(Utility.getRno(mrtUI.getSimType()));
			
			mrt.setSeqId(1);
			
			mrt.setDno(null);
			mrt.setActualDno(null);
			
			mnp.setMsisdn(mrtUI.getMnpMsisdn());
			mnp.setCutOverDate(Utility.string2Date(mrtUI.getCutOverDateStr()));
			mnp.setCutOverTime(mrtUI.getCutOverTime());
			//mnp.setRno("M3");
			mnp.setRno(Utility.getRno(mrtUI.getSimType()));
			
			mnp.setSeqId(2);
			
			//to get dno of mnp bean
			BeanUtilsHelper.copyProperties(mnp, mnpDto);
			
			/*try {
				mnpDto = mnpDAO.checkAdMsisdn(mnpDto);
			} catch (DAOException e) {
				e.printStackTrace();
			}*/
			
			mnp.setNumType(mrtUI.getNumType()); //DENNIS MIP3
			mnp.setDno(mrtUI.getDno());  //DENNIS MIP3
			//mnp.setDno(mnpDto.getDno());
			mnp.setActualDno(mrtUI.getActualDno());
			
			mobCcsMrtList.add(mrt);
			mobCcsMrtList.add(mnp);
			
		} else { //new number only
			
			MobCcsMrtDTO mrt = new MobCcsMrtDTO();
			
			BeanUtilsHelper.copyProperties(mrtUI, mrt);
			
			mrt.setMsisdn(mrtUI.getMobMsisdn());
			mrt.setServiceReqDate(Utility.string2Date(mrtUI.getServiceReqDateStr()));
			//mrt.setRno("M3");
			mrt.setRno(Utility.getRno(mrtUI.getSimType()));
			
			mrt.setSeqId(1);
			mrt.setDno(null);
			mrt.setActualDno(null);
			
			mrt.setNumType(mrtUI.getNumType()); //DENNIS MIP3
			
			mobCcsMrtList.add(mrt);
		}
	
	return mobCcsMrtList;
	
    }
    
    public ArrayList<MobCcsMrtBaseDTO> mnpDtoChangeToMrtDtoList(MnpDTO mnpDTO, VasMrtSelectionDTO vasMrtSelectionDTO, String userName){
    	
    	System.out.println("=========mnpDtoChangeToMrtDtoList========");
    	System.out.println(Utility.toPrettyJson(mnpDTO));
	ArrayList<MobCcsMrtBaseDTO> mobCcsMrtList = new ArrayList<MobCcsMrtBaseDTO>();
	
		if (vasMrtSelectionDTO != null && "Y".equalsIgnoreCase(vasMrtSelectionDTO.getChinaInd())) {//China number + ?
			
			if ("Y".equals(mnpDTO.getMnp())) {//China number + mnp
				MobCcsMrtMnpDTO mnp = new MobCcsMrtMnpDTO();
				MobCcsMrtChinaDTO china = new MobCcsMrtChinaDTO();
				MnpDTO mnpDto = new MnpDTO();
				
				BeanUtilsHelper.copyProperties(mnpDTO, china);
				BeanUtilsHelper.copyProperties(mnpDTO, mnp);
				
				mnp.setChinaInd("Y");
				china.setChinaInd("Y");
				
				mnp.setMsisdn(mnpDTO.getMnpMsisdn());
				mnp.setCutOverDate(Utility.string2Date(mnpDTO.getCutoverDateStr()));
				mnp.setCutOverTime(mnpDTO.getCutoverTime());
				
				//mnp.setRno("M3");
				mnp.setRno(Utility.getRno(mnpDTO.getRno()));
				
				
				mnp.setSeqId(1);
				if(StringUtils.isNotEmpty(mnpDTO.getOrigActDateStr())){
					mnp.setOrigActDate(Utility.string2Date(mnpDTO.getOrigActDateStr()));
				}
				
				//to get dno of mnp bean
				BeanUtilsHelper.copyProperties(mnp, mnpDto);
				
				/*try {
					mnpDto = mnpDAO.checkAdMsisdn(mnpDto);
				} catch (DAOException e) {
					e.printStackTrace();
				}*/
				mnp.setNumType(mnpDTO.getNumType()); //DENNIS MIP3
				mnp.setDno(mnpDTO.getDno());  //DENNIS MIP3
				//mnp.setDno(mnpDto.getDno());
				mnp.setActualDno(mnpDTO.getActualDno());
				
				
				china.setMsisdn(vasMrtSelectionDTO.getMsisdn());
				china.setMsisdnLvl(vasMrtSelectionDTO.getMsisdnLvl());
				china.setCityCd(vasMrtSelectionDTO.getCityCd());
				china.setCutOverDate(Utility.string2Date(mnpDTO.getCutoverDateStr()));
				china.setCutOverTime(mnpDTO.getCutoverTime());
				china.setSeqId(2);
				china.setNumType(vasMrtSelectionDTO.getNumType()); //DENNIS MIP3
				
				mobCcsMrtList.add(mnp);
				mobCcsMrtList.add(china);
				
			} else {//china number + new number
				
				MobCcsMrtDTO mrt = new MobCcsMrtDTO();
				MobCcsMrtChinaDTO china = new MobCcsMrtChinaDTO();
				
				BeanUtilsHelper.copyProperties(mnpDTO, china);
				BeanUtilsHelper.copyProperties(mnpDTO, mrt);
				
				mrt.setChinaInd("Y");
				china.setChinaInd("Y");
				
				mrt.setMsisdn(mnpDTO.getMsisdn());
				mrt.setServiceReqDate(Utility.string2Date(mnpDTO.getServiceReqDateStr()));
				//mrt.setRno("M3");
				mrt.setRno(Utility.getRno(mnpDTO.getRno()));
				
				mrt.setDno(null);
				mrt.setActualDno(null);
				
				
				mrt.setSeqId(1);
				mrt.setNumType(mnpDTO.getNumType()); //DENNIS MIP3

				china.setMsisdn(vasMrtSelectionDTO.getMsisdn());
				china.setMsisdnLvl(vasMrtSelectionDTO.getMsisdnLvl());
				china.setCityCd(vasMrtSelectionDTO.getCityCd());
				china.setServiceReqDate(Utility.string2Date(mnpDTO.getServiceReqDateStr()));
				china.setSeqId(2);
				china.setNumType(vasMrtSelectionDTO.getNumType()); //DENNIS MIP3
				
				mobCcsMrtList.add(mrt);
				mobCcsMrtList.add(china);
			}
						
		
		} else if ("Y".equals(mnpDTO.getMnp())) {//Mnp only
			
			MobCcsMrtMnpDTO mnp = new MobCcsMrtMnpDTO();
			MnpDTO mnpDto = new MnpDTO();
			
			BeanUtilsHelper.copyProperties(mnpDTO, mnp);
			
			mnp.setMsisdn(mnpDTO.getMnpMsisdn());
			mnp.setCutOverDate(Utility.string2Date(mnpDTO.getCutoverDateStr()));
			mnp.setCutOverTime(mnpDTO.getCutoverTime());
			//mnp.setRno("M3");
			mnp.setRno(Utility.getRno(mnpDTO.getRno()));
			
			
			mnp.setSeqId(1);
			if(StringUtils.isNotEmpty(mnpDTO.getOrigActDateStr())){
				mnp.setOrigActDate(Utility.string2Date(mnpDTO.getOrigActDateStr()));
			}
			
			//to get dno of mnp bean
			BeanUtilsHelper.copyProperties(mnp, mnpDto);
			
			/*try {
				mnpDto = mnpDAO.checkAdMsisdn(mnpDto);
			} catch (DAOException e) {
				e.printStackTrace();
			}*/
			
			mnp.setNumType(mnpDTO.getNumType()); //DENNIS MIP3
			mnp.setDno(mnpDTO.getDno());  //DENNIS MIP3
			//mnp.setDno(mnpDto.getDno());
			mnp.setActualDno(mnpDTO.getActualDno());
			
			mobCcsMrtList.add(mnp);
			
		} else if (mnpDTO.isFutherMnp()) { //mnp + new number
			
			MobCcsMrtDTO mrt = new MobCcsMrtDTO();
			MobCcsMrtMnpDTO mnp = new MobCcsMrtMnpDTO();
			
			BeanUtilsHelper.copyProperties(mnpDTO, mrt);
			BeanUtilsHelper.copyProperties(mnpDTO, mnp);
			
			mrt.setMsisdn(mnpDTO.getMsisdn());
			mrt.setServiceReqDate(Utility.string2Date(mnpDTO.getServiceReqDateStr()));
			mrt.setRno(Utility.getRno(mnpDTO.getRno()));
			
			mrt.setSeqId(1);
			
			mrt.setDno(null);
			mrt.setActualDno(null);
			
			//Further MNP
			mnp.setOrderId(mnpDTO.getOrderId());
			mnp.setSeqId(2);
			mnp.setMsisdn(mnpDTO.getFutherMnpNumber());
			mnp.setCutOverDate(mnpDTO.getFutherCutoverDate());
			mnp.setCutOverTime(mnpDTO.getFutherCutoverTime());
			mnp.setCustName(mnpDTO.getFuthercustName());
			mnp.setCustNameChi(mnpDTO.getFuthercustNameChi());
			mnp.setDocNum(mnpDTO.getFuthercustIdDocNum());
			mnp.setPrePaidSimDocInd(mnpDTO.getPrePaidSimDocInd());
			//mnp.setRno(Utility.getRno(mnpDTO.getRno()));
			mnp.setMnpTicketNum(mnpDTO.getFutherMnpTicketNum());
			mnp.setNumType(mnpDTO.getNumType()); //DENNIS MIP3
			//mnp.setDno(mnpDTO.getDno());  //DENNIS MIP3
			//mnp.setActualDno(mnpDTO.getActualDno());
			
			mobCcsMrtList.add(mrt);
			mobCcsMrtList.add(mnp);
			
		} else { //new number only
			
			MobCcsMrtDTO mrt = new MobCcsMrtDTO();
			
			BeanUtilsHelper.copyProperties(mnpDTO, mrt);
			
			mrt.setMsisdn(mnpDTO.getMsisdn());
			mrt.setServiceReqDate(Utility.string2Date(mnpDTO.getServiceReqDateStr()));
			//mrt.setRno("M3");
			mrt.setRno(Utility.getRno(mnpDTO.getRno()));
			
			mrt.setSeqId(1);
			mrt.setDno(null);
			mrt.setActualDno(null);
			
			mrt.setNumType(mnpDTO.getNumType()); //DENNIS MIP3
			
			mobCcsMrtList.add(mrt);
		}
	
	return mobCcsMrtList;
	
    }
    /**
     * Convert list of MobCcsMRtBaseDTO to MRTUI
     */
    public MRTUI mrtDtoChangeToUiDto(List<MobCcsMrtBaseDTO> mrtDtoList){
	
		MRTUI mrtUI = new MRTUI();
		
		for (int i = 0; i < mrtDtoList.size(); i++) {
			
			if (i == 0) {
				BeanUtilsHelper.copyProperties(mrtDtoList.get(i), mrtUI);
			}
						
			if (mrtDtoList.get(i) instanceof MobCcsMrtChinaDTO) {
				mrtUI.setUnicomMobMsisdn(mrtDtoList.get(i).getMsisdn());
				mrtUI.setUnicomMobGrade(mrtDtoList.get(i).getMsisdnLvl());
				mrtUI.setCityCd(((MobCcsMrtChinaDTO) mrtDtoList.get(i)).getCityCd());
				mrtUI.setNumType(mrtDtoList.get(i).getNumType()); //DENNIS MIP3
			} else if (mrtDtoList.get(i) instanceof MobCcsMrtMnpDTO) {
				mrtUI.setMnpMsisdn(mrtDtoList.get(i).getMsisdn());
				mrtUI.setCutOverDateStr(Utility.date2String(((MobCcsMrtMnpDTO) mrtDtoList.get(i)).getCutOverDate(), BomWebPortalConstant.DATE_FORMAT));
				mrtUI.setCutOverTime(((MobCcsMrtMnpDTO) mrtDtoList.get(i)).getCutOverTime());
				mrtUI.setMnpTicketNum(((MobCcsMrtMnpDTO) mrtDtoList.get(i)).getMnpTicketNum());
				mrtUI.setCustName(((MobCcsMrtMnpDTO) mrtDtoList.get(i)).getCustName());
				mrtUI.setDocNum(((MobCcsMrtMnpDTO) mrtDtoList.get(i)).getDocNum());
				mrtUI.setNumType(mrtDtoList.get(i).getNumType()); //DENNIS MIP3
				mrtUI.setOrigActDateStr(Utility.date2String(((MobCcsMrtMnpDTO) mrtDtoList.get(i)).getOrigActDate(), BomWebPortalConstant.DATE_FORMAT));
			} else if (mrtDtoList.get(i) instanceof MobCcsMrtDTO) {
				mrtUI.setMobMsisdn(mrtDtoList.get(i).getMsisdn());
				mrtUI.setServiceReqDateStr(Utility.date2String(((MobCcsMrtDTO) mrtDtoList.get(i)).getServiceReqDate(), BomWebPortalConstant.DATE_FORMAT));
				mrtUI.setNumType(mrtDtoList.get(i).getNumType()); //DENNIS MIP3
			}
			
			if (mrtDtoList.size() == i + 1) {
				if ("A".equalsIgnoreCase(mrtDtoList.get(i).getMnpInd())) {
					mrtUI.setMrtInd("A");
				} else if ("Y".equalsIgnoreCase(mrtDtoList.get(i).getChinaInd())) {
					mrtUI.setMrtInd("O");
					mrtUI.setMnpInd(mrtDtoList.get(i).getMnpInd());
				} else {
					mrtUI.setMrtInd("O");
				}
			}
			
		}
				
		return mrtUI;
    }
    /**
     * For report use
     * @param mrtDtoList
     * @param shopCd
     * @return
     */
	public MnpDTO convertToMnpDto(ArrayList<? extends MobCcsMrtBaseDTO> mrtDtoList){
	    	
	    	MnpDTO mnpDto = new MnpDTO(); 
	    	
	        for (MobCcsMrtBaseDTO dto : mrtDtoList) {
	              if (dto instanceof MobCcsMrtChinaDTO) {
	                    String[] ignoreAttr = new String[] {"rno", "dno", "msisdn"};
	                    BeanUtilsHelper.copyProperties(dto, mnpDto, ignoreAttr);
	              } else {
	                    BeanUtilsHelper.copyProperties(dto, mnpDto);
	              }
	              
	              if ("Y".equalsIgnoreCase(dto.getMnpInd()) && "N".equalsIgnoreCase(dto.getChinaInd())) {
	                    mnpDto.setMnpType("MNP");
	              } else if ("Y".equalsIgnoreCase(dto.getChinaInd()) && "N".equalsIgnoreCase(dto.getMnpInd())) {
	                    mnpDto.setMnpType("1-Card-2-Number (New Number)");
	                    mnpDto.setUnicomMsisdn(dto.getMsisdn());
	              } else if ("Y".equalsIgnoreCase(dto.getChinaInd()) && "Y".equalsIgnoreCase(dto.getMnpInd())) {
	                    mnpDto.setMnpType("1-Card-2-Number (MNP)");
	                    mnpDto.setUnicomMsisdn(dto.getMsisdn());
	              } else {
	                    mnpDto.setMnpType("New Number");
	              }
	
	              mnpDto.setMnp(dto.getMnpInd());
	              if (dto instanceof MobCcsMrtMnpDTO) {
	                    mnpDto.setCustIdDocNum(((MobCcsMrtMnpDTO) dto).getDocNum());
	                    mnpDto.setCutoverDate(((MobCcsMrtMnpDTO) dto).getCutOverDate());
	                    mnpDto.setCutoverTime(((MobCcsMrtMnpDTO) dto).getCutOverTime());
	              } else if (dto instanceof MobCcsMrtDTO) {
	            	  	mnpDto.setServiceReqDate(((MobCcsMrtDTO) dto).getServiceReqDate());
	              }
	        }
	                
	        if (mrtDtoList.size() > 1) {
	
	            for (MobCcsMrtBaseDTO dto : mrtDtoList) {
	                  if (dto instanceof MobCcsMrtDTO) {
	                	  mnpDto.setNewMsisdn(dto.getMsisdn());
	                  } else if (dto instanceof MobCcsMrtMnpDTO) {
	                	  mnpDto.setMnpMsisdn(dto.getMsisdn());
	                      mnpDto.setMsisdn(mnpDto.getNewMsisdn());
	                  } else if (dto instanceof MobCcsMrtChinaDTO) {
	                	  mnpDto.setUnicomMsisdn(dto.getMsisdn());
	                	  if (StringUtils.isNotBlank(mnpDto.getNewMsisdn())) {
	                		  mnpDto.setMsisdn(mnpDto.getNewMsisdn());
	                	  } else if (StringUtils.isNotBlank(mnpDto.getMnpMsisdn())) {
	                		  mnpDto.setMsisdn(mnpDto.getMnpMsisdn());
	                	  }
	                  }
	            }
	        }
	
		return mnpDto;
	
    }


    /**
     * Covert list of MobCcsMrtBaseDTO to MnpDTO.
     * This coding logic is designed based on the design structure of bomweb_mrt table.
     * Two seqId may exist if more than one mobile number are created in one CCS order. 
     * For example New Number + Mnp, New Number + China Number, Mnp + China Number etc...
     * When we select or insert data into bomweb_mrt table,
     * seqId 1 always represents new number (included golden number type)
     * seqId 2 represents china number or mnp number
     * 
     */
    public MnpDTO mrtDtoChangeToMnpDto(ArrayList<? extends MobCcsMrtBaseDTO> mrtDtoList, String shopCd){
	
	MnpDTO mnpDto = new MnpDTO();	
	
	for (MobCcsMrtBaseDTO dto : mrtDtoList) {
		
		BeanUtilsHelper.copyProperties(dto, mnpDto);
		
		if (dto instanceof MobCcsMrtMnpDTO) {
			
			mnpDto.setCustIdDocNum(((MobCcsMrtMnpDTO) dto).getDocNum());
			mnpDto.setCutoverDate(((MobCcsMrtMnpDTO) dto).getCutOverDate());
			mnpDto.setCutoverTime(((MobCcsMrtMnpDTO) dto).getCutOverTime());
			
		}
		//to check mnp incentive
		if (mrtDtoList.size() > 1) {
			
			boolean isMnp = false;
			boolean isMrt = false;
			
			for (MobCcsMrtBaseDTO innerDto : mrtDtoList) {
				if (innerDto instanceof MobCcsMrtMnpDTO) {
					isMnp = true;
					continue;
				} else if (innerDto instanceof MobCcsMrtDTO) {
					isMrt = true;
					continue;
				}
			}
			
			if (isMnp == true && isMrt == true) {
				mnpDto.setMnpIncentive(true);
			}
		} else {
			if (dto instanceof MobCcsMrtMnpDTO) {
				mnpDto.setMnpIncentive(true);
			}
		}
		
		break;
	}
	
	return mnpDto;
	
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getPCCW3GMrtNumByChannelCd(String channel_cd) {
	try {
	    logger.info("getPCCW3GMrtNum() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getPCCW3GMrtNumByChannelCd(channel_cd);

	} catch (DAOException de) {
	    logger.error("Exception caught in getPCCW3GMrtNum()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getPCCW3GMrtNumByStaffId(String staff_id) {
	try {
	    logger.info("getPCCW3GMrtNum() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getPCCW3GMrtNumByStaffId(staff_id);

	} catch (DAOException de) {
	    logger.error("Exception caught in getPCCW3GMrtNum()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getRandomPCCW3GNumFromPool(String channelCd, String numType) {
	try {
	    logger.info("getRandomPCCW3GNumFromPool() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getRandomPCCW3GNumFromPool(channelCd, numType);

	} catch (DAOException de) {
	    logger.error("Exception caught in getRandomPCCW3GNumFromPool()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getUnicomMrtNumAndLvl(String channel_cd, String cityCd) {
	try {
	    logger.info("getUnicomMrtNum() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getUnicomMrtNumAndLvl(channel_cd, cityCd);

	} catch (DAOException de) {
	    logger.error("Exception caught in getUnicomMrtNum()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String> getDistinctUNICOM1C2NCityCd(String channel_cd) {
	try {
	    logger.info("getDistinctUNICOM1C2NCityCd() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getDistinctUNICOM1C2NCityCd(channel_cd);

	} catch (DAOException de) {
	    logger.error("Exception caught in getDistinctUNICOM1C2NCityCd()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public String getNewMRTMsisdnLvl(String msisdn, String msisdnStatus) {
	try {
	    logger.info("getNewMRTMsisdnLvl() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getNewMRTMsisdnLvl(msisdn, msisdnStatus);

	} catch (DAOException de) {
	    logger.error("Exception caught in getNewMRTMsisdnLvl()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    // add by Joyce 20120208
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public boolean handleExpiredMRT(String staffId) {
    	try {
    	    logger.info("handleExpiredMRT() is called in MobCcsMrtServiceImpl");
    	    return mobCcsMrtDAO.handleExpiredMRT(staffId);

    	} catch (DAOException de) {
    	    logger.error("Exception caught in handleExpiredMRT()", de);
    	    throw new AppRuntimeException(de);
    	}
    }
    
    // add by Joyce 20120208
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public boolean handleFrozenMRT() {
    	try {
    	    logger.info("handleFrozenMRT() is called in MobCcsMrtServiceImpl");
    	    return mobCcsMrtDAO.handleFrozenMRT();

    	} catch (DAOException de) {
    	    logger.error("Exception caught in handleFrozenMRT()", de);
    	    throw new AppRuntimeException(de);
    	}
    }
    
    //add by eliot 20120301
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public boolean validateUnicomNumStatus(String msisdn) {
	try {
	    logger.info("validateUnicomNumStatus() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.validateUnicomNumStatus(msisdn);

	} catch (DAOException de) {
	    logger.error("Exception caught in validateUnicomNumStatus()", de);
	    throw new AppRuntimeException(de);
	}
    }

    //add by Vincy 20141029

    public boolean getNewGoldenNumRulesInd(Date appDate) {
		try {
			logger.info("getNewGoldenNumRulesInd() is called");
			Date newGoldenNumRulesInd = mobCcsMrtDAO.getNewGoldenNumRulesDate();
			logger.info("newGoldenNumRulesInd = " + newGoldenNumRulesInd);
			logger.info("appDate = " + appDate);
			if (newGoldenNumRulesInd == null) {
				return false;
			} else if (newGoldenNumRulesInd.equals(appDate) || newGoldenNumRulesInd.before(appDate)) {
				return true;
			}
			
			return false;
		} catch (DAOException de) {
			logger.error("Exception caught in getNewGoldenNumRulesInd()", de);
			throw new AppRuntimeException(de);
		}
	}
    
    
    //add by eliot 20120301
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public boolean validate3GnewMRTStatus(String msisdn) {
	try {
	    logger.info("validate3GnewMRTStatus() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.validate3GnewMRTStatus(msisdn);

	} catch (DAOException de) {
	    logger.error("Exception caught in validate3GnewMRTStatus()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    //add by eliot 20120313
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public boolean newMRTSearch(String msisdn, String channelCd, String numType) {
	try {
	    logger.info("newMRTSearch() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.newMRTSearch(msisdn, channelCd, numType);

	} catch (DAOException de) {
	    logger.error("Exception caught in newMRTSearch()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    //add by eliot 20120320
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<HashMap<String, Integer>> getGoldenNumLvlCondDtl(String goldenLvl, String appDate, String mipBrand ){
	try {
	    logger.info("getGoldenNumLvlCondDtl() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getGoldenNumLvlCondDtl(goldenLvl, appDate, mipBrand);

	} catch (DAOException de) {
	    logger.error("Exception caught in getGoldenNumLvlCondDtl()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
  //add by eliot 20120321
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)    
    public List<String[]> getGoldenNumRandsomList(String vip, String period, String charge, String grossPlanFee, Date appDate, String mipBrand){
	try {
	    logger.info("getGoldenNumRandsomList() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getGoldenNumRandsomList(vip, period, charge, grossPlanFee, appDate, mipBrand);

	} catch (DAOException de) {
	    logger.error("Exception caught in getGoldenNumRandsomList()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getGoldenNumSearchList(String vip, String period, String charge, String searchMsisdn, String grossPlanFee, Date appDate, String mipBrand){
	try {
	    logger.info("getGoldenNumSearchList() is called in MobCcsMrtServiceImpl");
	    return mobCcsMrtDAO.getGoldenNumSearchList(vip, period, charge, searchMsisdn, grossPlanFee, appDate, mipBrand);

	} catch (DAOException de) {
	    logger.error("Exception caught in getGoldenNumSearchList()", de);
	    throw new AppRuntimeException(de);
	}
    }
    

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public void updateMnpTicketNum(String orderId, String mnpTicketNum, Date cutOverDate, String username) {
		try {
		    logger.info("updateMnpTicketNum() is called");
		    mobCcsMrtDAO.updateMnpTicketNum(orderId, mnpTicketNum, cutOverDate, username);

		} catch (DAOException de) {
		    logger.error("Exception caught in updateMnpTicketNum()", de);
		    throw new AppRuntimeException(de);
		}
	}
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public void updateMrtServiceDate(String orderId, String mnpInd, Date serviceDate, String username) {
	try {
	    logger.info("updateMrtServiceDate() is called");
	    mobCcsMrtDAO.updateMrtServiceDate(orderId, mnpInd, serviceDate, username);

	} catch (DAOException de) {
	    logger.error("Exception caught in updateMrtServiceDate()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getPatternNumSearchList(String channelCd, String teamCdorCenterCd, String searchMsisdn, String numType) {
	try {
	    logger.info("getPatternNumSearchList() is called");
	    return mobCcsMrtDAO.getPatternNumSearchList(channelCd, teamCdorCenterCd, searchMsisdn, numType);

	} catch (DAOException de) {
	    logger.error("Exception caught in getPatternNumSearchList()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getPatternNumSearchListCcs(String channelShopCd, String searchMsisdn, String numType) {
	try {
	    logger.info("getPatternNumSearchListCcs() is called");
	    return mobCcsMrtDAO.getPatternNumSearchListCcs(channelShopCd, searchMsisdn, numType);

	} catch (DAOException de) {
	    logger.error("Exception caught in getPatternNumSearchListCcs()", de);
	    throw new AppRuntimeException(de);
	}
    }
	
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getPatternNumSearchListCcsMul(String searchMsisdn) {
	try {
	    logger.info("getPatternNumSearchListCcsMul() is called");
	    return mobCcsMrtDAO.getPatternNumSearchListCcsMul(searchMsisdn);

	} catch (DAOException de) {
	    logger.error("Exception caught in getPatternNumSearchListCcsMul()", de);
	    throw new AppRuntimeException(de);
	}
 }
    //add by Eliot 20120418
    //modify by Eliot 20120423
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String> getPendingOrderExistWithMsisdnByPendingAndFallout(String msisdn){
	
	try {
	    logger.info("getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout() is called");
	    return mobCcsMrtDAO.getPendingOrderExistWithMsisdnByPendingAndFallout(msisdn);

	} catch (DAOException de) {
	    logger.error("Exception caught in getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout()", de);
	    throw new AppRuntimeException(de);
	}
    }    
  
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String> getOrderExistWithMsisdnByPCancelling(String msisdn){
	
	try {
	    logger.info("getOrderExistWithMsisdnOrderIdByPCancelling() is called");
	    return mobCcsMrtDAO.getOrderExistWithMsisdnByPCancelling(msisdn);

	} catch (DAOException de) {
	    logger.error("Exception caught in getOrderExistWithMsisdnOrderIdByPCancelling()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    //add by Eliot 20120423
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String> getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout(String msisdn, String orderId){
	
	try {
	    logger.info("getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout() is called");
	    return mobCcsMrtDAO.getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout(msisdn, orderId);

	} catch (DAOException de) {
	    logger.error("Exception caught in getPendingOrderExistWithMsisdnOrderIdByPendingAndFallout()", de);
	    throw new AppRuntimeException(de);
	}
    }    
  
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String> getOrderExistWithMsisdnOrderIdByPCancelling(String msisdn, String orderId){
	
	try {
	    logger.info("getOrderExistWithMsisdnOrderIdByPCancelling() is called");
	    return mobCcsMrtDAO.getOrderExistWithMsisdnOrderIdByPCancelling(msisdn, orderId);

	} catch (DAOException de) {
	    logger.error("Exception caught in getOrderExistWithMsisdnOrderIdByPCancelling()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    //add by eliot 20120514
    public String getSpecialMsisdnLvl(String msisdn, String msisdnStatus, String approvalSerial){
    try{
    	logger.info("getSpecialMsisdnLvl() is called");
	    return mobCcsMrtDAO.getSpecialMsisdnLvl(msisdn, msisdnStatus, approvalSerial);

	} catch (DAOException de) {
	    logger.error("Exception caught in getSpecialMsisdnLvl()", de);
	    throw new AppRuntimeException(de);
	}
    }

	public List<String> getGoldenNumLvL(boolean exclusiveGA) {
		try{
	    	logger.info("getGoldenNumLvL() is called");
		    return mobCcsMrtDAO.getGoldenNumLvL(exclusiveGA);

		} catch (DAOException de) {
		    logger.error("Exception caught in getGoldenNumLvL()", de);
		    throw new AppRuntimeException(de);
		}
	}
	
	public List<String> getFrozenWindow(String inDate) {
		
		List<String> result = null;
		
		try {
			result = mobCcsMrtDAO.getFrozenWindow(inDate);
		} catch (Exception e) {
			logger.error("Exception caught in getFrozenWindow()", e);
		}
		
		return result;
	}

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getOneCardTwoNumberByFullNumber(String channelCd, String searchMsisdn, String numType) {
	try {
	    logger.info("getOneCardTwoNumberByFullNumber() is called");
	    return mobCcsMrtDAO.getOneCardTwoNumberByFullNumber(channelCd, searchMsisdn, numType);

	} catch (DAOException de) {
	    logger.error("Exception caught in getOneCardTwoNumberByFullNumber()", de);
	    throw new AppRuntimeException(de);
	}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
    public List<String[]> getOneCardTwoNumberByRandom(String channelCd, String cityCd, String numType) {
	try {
	    logger.info("getOneCardTwoNumberByRandom() is called");
	    return mobCcsMrtDAO.getOneCardTwoNumberByRandom(channelCd, cityCd, numType);

	} catch (DAOException de) {
	    logger.error("Exception caught in getOneCardTwoNumberByRandom()", de);
	    throw new AppRuntimeException(de);
	}
    }


}
