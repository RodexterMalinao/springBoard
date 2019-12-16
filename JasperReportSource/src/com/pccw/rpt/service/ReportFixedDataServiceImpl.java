package com.pccw.rpt.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.util.FastByteArrayOutputStream;
import com.pccw.rpt.dao.BomwebRptTemplateVDAO;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.amendmentForm.AmendmentFormDTO;
import com.pccw.rpt.schema.dto.crfFormPipb.CrfFormPipbRptDTO;
import com.pccw.rpt.schema.dto.nsdForm.NsdFormRptDTO;
import com.pccw.rpt.schema.dto.nsdFormPipb.NsdFormPipbRptDTO;
import com.pccw.rpt.schema.dto.recontractForm.RecontractFormRptDTO;
import com.pccw.rpt.util.ReportUtil;
import com.pccw.util.spring.SpringApplicationContext;

public class ReportFixedDataServiceImpl implements ReportFixedDataService {

	private static Logger logger = Logger.getLogger(ReportServiceImpl.class);
	

	@Override
	@Transactional(readOnly=true)
	public void fillReportFixedData(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO) throws Exception {
		fillReportFixedData(pReportName,  pLanguage, pRptParamMap, pReportDTO, false);
	}

	@Override
	@Transactional(readOnly=true)
	public void fillReportFixedData(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO, boolean appendDtlInd) throws Exception {
		InputStream pccwLogoStream = ReportFixedDataServiceImpl.class.getResourceAsStream("/images/pccw_logo.png");
		pReportDTO.setCompanyLogo(pccwLogoStream);
		
		BomwebRptTemplateVDAO criteria = SpringApplicationContext.getBean(BomwebRptTemplateVDAO.class);
		criteria.setSearchKey("rptName", pReportName);
		criteria.setSearchKey("language", pLanguage);
		BomwebRptTemplateVDAO[] rsRptTemplate =(BomwebRptTemplateVDAO[])criteria.doSelect(null, null);
		
		if (ArrayUtils.isEmpty(rsRptTemplate)) {
			return;
		}
		
		InputStream imageStream = null;
		for (BomwebRptTemplateVDAO dao : rsRptTemplate) {
			if (StringUtils.isNotBlank(dao.getImgResource())) {
				imageStream = ReportFixedDataServiceImpl.class.getResourceAsStream(dao.getImgResource());
				BeanUtils.setProperty(pReportDTO, dao.getAttribute(), inputStreamToByteArray(imageStream));
			} else {
				StringBuilder sbContents = new StringBuilder(ReportUtil.defaultString(dao.getContents()));
				
				if ("Y".equals(pRptParamMap.get("EDIT_BUTTON"))) {
					// Exclude image files because they will cause "byte data not found" error
					if (StringUtils.isNotBlank(dao.getContents()) && !dao.getContents().toLowerCase().contains(".jpg") && !dao.getContents().toLowerCase().contains(".png") && !dao.getContents().toLowerCase().contains(".gif")) {
						sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(dao));
					}
				}
				if (".css".equals(StringUtils.right(dao.getAttribute(), 4))) {
					pReportDTO.putFieldCss(dao.getAttribute(), sbContents.toString());
				} else {
					try {
							if(appendDtlInd){
								String oldContent = BeanUtils.getProperty(pReportDTO, dao.getAttribute());
								BeanUtils.setProperty(pReportDTO, dao.getAttribute(), StringUtils.isNotBlank(oldContent) ? oldContent + "<br/>" + sbContents.toString() : sbContents.toString() );	
							}else{
								BeanUtils.setProperty(pReportDTO, dao.getAttribute(), sbContents.toString());
							}
						}
					catch (Exception e) {
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
				}
			}
		}
	}
	
	@Override
	public void postFillReportData(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO) throws Exception {

		if ("pdfTemplate/lts/nsdForm".equals(pReportName)) {
			this.fillNsdFormRptDTOFixValue(pReportDTO, pLanguage);
		}
		if ("pdfTemplate/lts/recontractForm".equals(pReportName)) {
			this.fillRecontractFormRptDTOFixValue(pReportDTO, pLanguage);
		}
		if ("pdfTemplate/lts/nsdFormPipb".equals(pReportName)) {
			this.fillNsdFormPipbRptDTOFixValue(pReportDTO, pLanguage);
		}
		if ("pdfTemplate/lts/amendmentForm".equals(pReportName)) {
			this.fillAmendmentFormDTOFixValue(pReportDTO, pLanguage);
		} 
		if ("pdfTemplate/lts/crfFormPipb".equals(pReportName)) {
			this.fillCrfFormPipbRptDTOFixValue(pReportDTO, pLanguage);
		} 
	}
	
	public void fillAmendmentFormDTOFixValue(ReportDTO pReportDTO, String pLanguage) throws Exception{
		AmendmentFormDTO formDTO = (AmendmentFormDTO) pReportDTO;
		
		formDTO.setAmendStrikeThru(formDTO.isAmendment() ? "" : "______________");
		formDTO.setCancelStrikeThru(formDTO.isCancellation() ? "" : "______________");
		
		formDTO.setIdStrikethru(formDTO.isHKID() ? "" : "___");
		formDTO.setPassStrikeThru(formDTO.isPass() ? "" : "___________");
		
		formDTO.setChangeBAChkBox(formDTO.isChgBA() ? "X" : null);
		formDTO.setChangeCredCrdNumChkBox(formDTO.isChgCardNum() ? "X" : null);
		formDTO.setChangeCustInfoChkBox(formDTO.isChgCustInfo() ? "X" : null);
		formDTO.setChangeDNchkBox(formDTO.isChgDN() ? "X" : null);
		formDTO.setChangeIAchkBox(formDTO.isChgIA() ? "X" : null);
		formDTO.setChangeOfferChkBox(formDTO.isChgOffer() ? "X" : null);
		formDTO.setOtherChkBox(formDTO.isOther() ?  "X" : null);
		formDTO.setChangeShareModFsaChkBox(formDTO.isChgShareModFsa() ? "X" : null);
		formDTO.setChangeSRDchkBox(formDTO.isChgSRD()? "X" : null);
		formDTO.setChangeTvChannelChkBox(formDTO.isChgTvChannel() ? "X" : null);
		formDTO.setChangeVasPremiChkBox(formDTO.isChgVasPremi() ? "X" : null);
	}
	
	public void fillCrfFormPipbRptDTOFixValue(ReportDTO pReportDTO, String pLanguage) throws Exception {
		CrfFormPipbRptDTO formDTO = (CrfFormPipbRptDTO) pReportDTO;
		formDTO.setNewAddrChkBox(formDTO.isNewAddr() ? "X" : null);
		formDTO.setExistingAddrChkBox(formDTO.isNewAddr() ? null : "X");
		formDTO.setWtChkBox(formDTO.isPortFromWt() ? "X" : null);
		formDTO.setNwtChkBox(formDTO.isPortFromNwt() ? "X" : null);
		formDTO.setHgcChkBox(formDTO.isPortFromHgc() ? "X" : null);
		formDTO.setHkbnChkBox(formDTO.isPortFromHkbn() ? "X" : null);
		formDTO.setHkcChkBox(formDTO.isPortFromHkc() ? "X" : null);
		formDTO.setSslChkBox(formDTO.isPortFromSsl() ? "X" : null);
		formDTO.setComnetChkBox(formDTO.isPortFromComnet() ? "X" : null);
		formDTO.setCslChkBox(formDTO.isPortFromCsl() ? "X" : null);
		formDTO.setPcChkBox(formDTO.isPortFromPc() ? "X" : null);
		formDTO.setVzChkBox(formDTO.isPortFromVz() ? "X" : null);
		formDTO.setCmhkChkBox(formDTO.isPortFromCmhk() ? "X" : null);
		formDTO.setHkbnesChkBox(formDTO.isPortFromHkbnes() ? "X" : null);
		
		if (StringUtils.equalsIgnoreCase("HK", formDTO.getArea())){
			formDTO.setKlnST("____");
			formDTO.setNtST("___");
		} else if (StringUtils.equalsIgnoreCase("KN", formDTO.getArea())){
			formDTO.setHkST("___");
			formDTO.setNtST("___");
		} else if (StringUtils.equalsIgnoreCase("NT", formDTO.getArea()) 
				|| StringUtils.equalsIgnoreCase("LT", formDTO.getArea())){
			formDTO.setHkST("___");
			formDTO.setKlnST("____");
		}
		
	}
	
	public void fillNsdFormPipbRptDTOFixValue(ReportDTO pReportDTO, String pLanguage) throws Exception {

		NsdFormPipbRptDTO formDTO = (NsdFormPipbRptDTO) pReportDTO;
		
		if (StringUtils.equalsIgnoreCase("en", pLanguage)){
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getCustDocType())){
				formDTO.setCustPassStrikeThrough("_________");
				formDTO.setCustHkbrStrikeThrough("___");
			} else if (StringUtils.equalsIgnoreCase("PASS", formDTO.getCustDocType())){
				formDTO.setCustHkidStrikeThrough("___");
				formDTO.setCustHkbrStrikeThrough("___");
			} else if (StringUtils.equalsIgnoreCase("BS", formDTO.getCustDocType())){
				formDTO.setCustHkidStrikeThrough("___");
				formDTO.setCustPassStrikeThrough("_________");
			}
				
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getUserDocType())){
				formDTO.setUserPassStrikeThrough("_________");
				formDTO.setUserHkbrStrikeThrough("___");
			} else if (StringUtils.equalsIgnoreCase("PASS", formDTO.getUserDocType())){
				formDTO.setUserHkidStrikeThrough("___");
				formDTO.setUserHkbrStrikeThrough("___");
			} else if (StringUtils.equalsIgnoreCase("BS", formDTO.getUserDocType())){
				formDTO.setUserHkidStrikeThrough("___");
				formDTO.setCustPassStrikeThrough("_________");
			}
			
			if (StringUtils.equalsIgnoreCase("HK", formDTO.getArea())){
				formDTO.setKnStrikeThrough("___");
				formDTO.setNtStrikeThrough("___");
			} else if (StringUtils.equalsIgnoreCase("KN", formDTO.getArea())){
				formDTO.setHkStrikeThrough("___");
				formDTO.setNtStrikeThrough("___");
			} else if (StringUtils.equalsIgnoreCase("NT", formDTO.getArea())
					|| StringUtils.equalsIgnoreCase("LT", formDTO.getArea())){
				formDTO.setHkStrikeThrough("___");
				formDTO.setKnStrikeThrough("___");
			}
			
		} else {
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getCustDocType())){
				formDTO.setCustPassStrikeThrough("___________");
				formDTO.setCustHkbrStrikeThrough("__________");
			} else if (StringUtils.equalsIgnoreCase("PASS", formDTO.getCustDocType())){
				formDTO.setCustHkidStrikeThrough("______");
				formDTO.setCustHkbrStrikeThrough("__________");
			} else if (StringUtils.equalsIgnoreCase("BS", formDTO.getCustDocType())){
				formDTO.setCustHkidStrikeThrough("______");
				formDTO.setCustPassStrikeThrough("___________");
			}
				
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getUserDocType())){
				formDTO.setUserPassStrikeThrough("___________");
				formDTO.setUserHkbrStrikeThrough("__________");
			} else if (StringUtils.equalsIgnoreCase("PASS", formDTO.getUserDocType())){
				formDTO.setUserHkidStrikeThrough("______");
				formDTO.setUserHkbrStrikeThrough("__________");
			} else if (StringUtils.equalsIgnoreCase("BS", formDTO.getUserDocType())){
				formDTO.setUserHkidStrikeThrough("______");
				formDTO.setCustPassStrikeThrough("___________");
			}
			
			if (StringUtils.equalsIgnoreCase("HK", formDTO.getArea())){
				formDTO.setKnStrikeThrough("_____");
				formDTO.setNtStrikeThrough("_____");
			} else if (StringUtils.equalsIgnoreCase("KN", formDTO.getArea())){
				formDTO.setHkStrikeThrough("_____");
				formDTO.setNtStrikeThrough("_____");
			} else if (StringUtils.equalsIgnoreCase("NT", formDTO.getArea()) 
					|| StringUtils.equalsIgnoreCase("LT", formDTO.getArea())){
				formDTO.setHkStrikeThrough("_____");
				formDTO.setKnStrikeThrough("_____");
			}
		}

		if (formDTO.isDuplex()){
			formDTO.setDuplexCross("X");
		}
		
		if (StringUtils.equalsIgnoreCase("CONT", formDTO.getEquipment())){
			formDTO.setEquipContCross("X");
		} else if (StringUtils.equalsIgnoreCase("RETURN", formDTO.getEquipment())){
			formDTO.setEquipReturnCross("X");
		} else {
			formDTO.setEquipNilCross("X");
		}
	
		// trim leading zero
//		if (!StringUtils.isEmpty(formDTO.getSrvNum())){
//			formDTO.setSrvNum(formDTO.getSrvNum().replaceFirst("^0+(?!$)", ""));	
//		}
		
		if (!StringUtils.isEmpty(formDTO.getTermSrvNum())){
			formDTO.setTermSrvNum(formDTO.getTermSrvNum().replaceFirst("^0+(?!$)", ""));
		}
		
		if (!StringUtils.isEmpty(formDTO.getContactPhone())){
			formDTO.setContactPhone(formDTO.getContactPhone().replaceFirst("^0+(?!$)", ""));
		}
		
		if (!StringUtils.isEmpty(formDTO.getSrvNumList())){
			formDTO.setSrvNumList(formDTO.getSrvNumList().replaceFirst("^0+(?!$)", ""));
		}
		formDTO.setTermChkbox((formDTO.isTermDuplex() ? "X" : null));
	}
	
	/*
	public void fillRecontractFormRptDTOFixValue(ReportDTO pReportDTO, String pLanguage) throws Exception {
		RecontractFormRptDTO formDTO = (RecontractFormRptDTO) pReportDTO;
		if (StringUtils.equalsIgnoreCase("en", pLanguage)){
			if (StringUtils.equalsIgnoreCase("Mr", formDTO.getFromCustTitle())){
				formDTO.setFromCustMsStrikeThrough("__");
				formDTO.setFromCustMrsStrikeThrough("___");
			}
			else if (StringUtils.equalsIgnoreCase("Ms", formDTO.getFromCustTitle())){
				formDTO.setFromCustMrStrikeThrough("__");
				formDTO.setFromCustMrsStrikeThrough("___");
			}
			else {
				formDTO.setFromCustMrStrikeThrough("__");
				formDTO.setFromCustMsStrikeThrough("__");
			}
			
			if (StringUtils.equalsIgnoreCase("Mr", formDTO.getToCustTitle())){
				formDTO.setToCustMsStrikeThrough("__");
				formDTO.setToCustMrsStrikeThrough("___");
			}
			else if (StringUtils.equalsIgnoreCase("Ms", formDTO.getToCustTitle())){
				formDTO.setToCustMrStrikeThrough("__");
				formDTO.setToCustMrsStrikeThrough("___");
			}
			else {
				formDTO.setToCustMrStrikeThrough("__");
				formDTO.setToCustMsStrikeThrough("__");
			}
		}
		
		
		else {
			if (StringUtils.equalsIgnoreCase("Mr", formDTO.getFromCustTitle())){
				formDTO.setFromCustMsStrikeThrough("__");
			}
			else if (StringUtils.equalsIgnoreCase("Ms", formDTO.getFromCustTitle())){
				formDTO.setFromCustMrStrikeThrough("__");
			}	
			if (StringUtils.equalsIgnoreCase("Mr", formDTO.getToCustTitle())){
				formDTO.setToCustMsStrikeThrough("__");
			}
			else if (StringUtils.equalsIgnoreCase("Ms", formDTO.getToCustTitle())){
				formDTO.setToCustMrStrikeThrough("__");
			}	
		}
	}
	*/
	
	public void fillRecontractFormRptDTOFixValue(ReportDTO pReportDTO, String pLanguage) throws Exception {
		RecontractFormRptDTO formDTO = (RecontractFormRptDTO) pReportDTO;
		formDTO.setCallingCardChkbox(formDTO.isCallingCardSrv() ? "X" : null);
		formDTO.setMobIDDChkbox(formDTO.isMobileIDDSrv() ? "X" : null); 
		formDTO.setFixedIDDChkbox(formDTO.isFixedIDDSrv() ? "X" : null);
		
		if (StringUtils.equalsIgnoreCase("en", pLanguage)){
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getFromCustDocType())){
				formDTO.setFromCustPassStrikeThrough("_________");
			} else {
				formDTO.setFromCustHkidStrikeThrough("_______");
			}
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getToCustDocType())){
				formDTO.setToCustPassStrikeThrough("_________");
			} else {
				formDTO.setToCustHkidStrikeThrough("_______");
			}
			if (StringUtils.equalsIgnoreCase("Mr", formDTO.getFromCustTitle())){
				formDTO.setFromCustMsStrikeThrough("__");
				formDTO.setFromCustMrsStrikeThrough("___");
			}
			else if (StringUtils.equalsIgnoreCase("Ms", formDTO.getFromCustTitle())){
				formDTO.setFromCustMrStrikeThrough("__");
				formDTO.setFromCustMrsStrikeThrough("___");
			}
			else {
				formDTO.setFromCustMrStrikeThrough("__");
				formDTO.setFromCustMsStrikeThrough("__");
			}
			
			if (StringUtils.equalsIgnoreCase("Mr", formDTO.getToCustTitle())){
				formDTO.setToCustMsStrikeThrough("__");
				formDTO.setToCustMrsStrikeThrough("___");
			}
			else if (StringUtils.equalsIgnoreCase("Ms", formDTO.getToCustTitle())){
				formDTO.setToCustMrStrikeThrough("__");
				formDTO.setToCustMrsStrikeThrough("___");
			}
			else {
				formDTO.setToCustMrStrikeThrough("__");
				formDTO.setToCustMsStrikeThrough("__");
			}
			if (StringUtils.equalsIgnoreCase("S", formDTO.getTransMode())){
				formDTO.setBothST1("__");
				formDTO.setBothST2("_");
				formDTO.setBothST3("_");
				formDTO.setBothST4("_");
				formDTO.setBothST5("_");
				formDTO.setBothST6("_");
				formDTO.setBothST7("_");
				formDTO.setBothST8("_");
				formDTO.setBothST9("__");
				formDTO.setBothST10("__");
			} else {
				formDTO.setSingleST1("__");
				formDTO.setSingleST2("__");
				formDTO.setSingleST3("_");
				formDTO.setSingleST4("__");
				formDTO.setSingleST5("_");
				formDTO.setSingleST6("_");
				formDTO.setSingleST7("_");
				formDTO.setSingleST8("_");
				formDTO.setSingleST9("__");
				formDTO.setSingleST10("__");
			}
			
			if(formDTO.isOptOut()){
				formDTO.setOptOutStrikeThru1("_______________________________");
				formDTO.setOptOutStrikeThru2("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru3("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru4("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru5("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru6("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru7("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru8("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru9("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru10("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru11("______________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru12("______________________________________________________________________________________________");
			}
		} else {
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getFromCustDocType())){
				formDTO.setFromCustPassStrikeThrough("_________");
			} else {
				formDTO.setFromCustHkidStrikeThrough("_________");
			}
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getToCustDocType())){
				formDTO.setToCustPassStrikeThrough("___________");
			} else {
				formDTO.setToCustHkidStrikeThrough("_________");
			}
			if (StringUtils.equalsIgnoreCase("Mr", formDTO.getFromCustTitle())){
				formDTO.setFromCustMsStrikeThrough("__");
			}
			else if (StringUtils.equalsIgnoreCase("Ms", formDTO.getFromCustTitle())){
				formDTO.setFromCustMrStrikeThrough("__");
			}	
			if (StringUtils.equalsIgnoreCase("Mr", formDTO.getToCustTitle())){
				formDTO.setToCustMsStrikeThrough("__");
			}
			else if (StringUtils.equalsIgnoreCase("Ms", formDTO.getToCustTitle())){
				formDTO.setToCustMrStrikeThrough("__");
			}	
			if (StringUtils.equalsIgnoreCase("S", formDTO.getTransMode())){
				formDTO.setBothST1("___");
				formDTO.setBothST2("___");
				formDTO.setBothST3("___");
				formDTO.setBothST4("___");
				formDTO.setBothST5("___");
				formDTO.setBothST6("___");
				formDTO.setBothST7("___");
				formDTO.setBothST8("___");
				formDTO.setBothST9("___");
				formDTO.setBothST10("___");
			} else {
				formDTO.setSingleST1("___");
				formDTO.setSingleST2("___");
				formDTO.setSingleST3("___");
				formDTO.setSingleST4("___");
				formDTO.setSingleST5("___");
				formDTO.setSingleST6("___");
				formDTO.setSingleST7("___");
				formDTO.setSingleST8("___");
				formDTO.setSingleST9("___");
				formDTO.setSingleST10("___");
			}
			if(formDTO.isOptOut()){
				formDTO.setOptOutStrikeThru1("_________________");
				formDTO.setOptOutStrikeThru2("____________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru3("____________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru4("____________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru5("____________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru6("____________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru7("____________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru8("____________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru9("____________________________________________________________________________________________");
				formDTO.setOptOutStrikeThru10("______________________________________");
			}
		}
	}
	
	public void fillNsdFormRptDTOFixValue(ReportDTO pReportDTO, String pLanguage) throws Exception {

		NsdFormRptDTO formDTO = (NsdFormRptDTO) pReportDTO;
		
		if (StringUtils.equalsIgnoreCase("en", pLanguage)){
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getCustDocType())){
				formDTO.setCustPassStrikeThrough("_________");
				formDTO.setCustHkbrStrikeThrough("____");
			} else if (StringUtils.equalsIgnoreCase("PASS", formDTO.getCustDocType())){
				formDTO.setCustHkidStrikeThrough("____");
				formDTO.setCustHkbrStrikeThrough("____");
			}
				
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getUserDocType())){
				formDTO.setUserPassStrikeThrough("_________");
				formDTO.setUserHkbrStrikeThrough("____");
			} else if (StringUtils.equalsIgnoreCase("PASS", formDTO.getUserDocType())){
				formDTO.setUserHkidStrikeThrough("____");
				formDTO.setUserHkbrStrikeThrough("____");
			}
			
			if (StringUtils.equalsIgnoreCase("HK", formDTO.getArea())){
				formDTO.setKnStrikeThrough("___");
				formDTO.setNtStrikeThrough("___");
			} else if (StringUtils.equalsIgnoreCase("KN", formDTO.getArea())){
				formDTO.setHkStrikeThrough("___");
				formDTO.setNtStrikeThrough("___");
			} else if (StringUtils.equalsIgnoreCase("NT", formDTO.getArea())
					|| StringUtils.equalsIgnoreCase("LT", formDTO.getArea())){
				formDTO.setHkStrikeThrough("___");
				formDTO.setKnStrikeThrough("___");
			}
			
		} else {
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getCustDocType())){
				formDTO.setCustPassStrikeThrough("___________");
				formDTO.setCustHkbrStrikeThrough("_____________");
			} else if (StringUtils.equalsIgnoreCase("PASS", formDTO.getCustDocType())){
				formDTO.setCustHkidStrikeThrough("______");
				formDTO.setCustHkbrStrikeThrough("_____________");
			}
				
			if (StringUtils.equalsIgnoreCase("HKID", formDTO.getUserDocType())){
				formDTO.setUserPassStrikeThrough("___________");
				formDTO.setUserHkbrStrikeThrough("_____________");
			} else if (StringUtils.equalsIgnoreCase("PASS", formDTO.getUserDocType())){
				formDTO.setUserHkidStrikeThrough("______");
				formDTO.setUserHkbrStrikeThrough("_____________");
			}
			
			if (StringUtils.equalsIgnoreCase("HK", formDTO.getArea())){
				formDTO.setKnStrikeThrough("_____");
				formDTO.setNtStrikeThrough("_____");
			} else if (StringUtils.equalsIgnoreCase("KN", formDTO.getArea())){
				formDTO.setHkStrikeThrough("_____");
				formDTO.setNtStrikeThrough("_____");
			} else if (StringUtils.equalsIgnoreCase("NT", formDTO.getArea()) 
					|| StringUtils.equalsIgnoreCase("LT", formDTO.getArea())){
				formDTO.setHkStrikeThrough("_____");
				formDTO.setKnStrikeThrough("_____");
			}
		}

		if (formDTO.isDuplex()){
			formDTO.setDuplexCross("X");
		}
		
		if (StringUtils.equalsIgnoreCase("CONT", formDTO.getEquipment())){
			formDTO.setEquipContCross("X");
		} else if (StringUtils.equalsIgnoreCase("RETURN", formDTO.getEquipment())){
			formDTO.setEquipReturnCross("X");
		} else {
			formDTO.setEquipNilCross("X");
		}
	
		// trim leading zero
		if (!StringUtils.isEmpty(formDTO.getSrvNum())){
			formDTO.setSrvNum(formDTO.getSrvNum().replaceFirst("^0+(?!$)", ""));	
		}
		
		if (!StringUtils.isEmpty(formDTO.getTermSrvNum())){
			formDTO.setTermSrvNum(formDTO.getTermSrvNum().replaceFirst("^0+(?!$)", ""));
		}
		
		if (!StringUtils.isEmpty(formDTO.getContactPhone())){
			formDTO.setContactPhone(formDTO.getContactPhone().replaceFirst("^0+(?!$)", ""));
		}
		
		if (!StringUtils.isEmpty(formDTO.getSrvNumList())){
			formDTO.setSrvNumList(formDTO.getSrvNumList().replaceFirst("^0+(?!$)", ""));
		}
		formDTO.setTermChkbox((formDTO.isTermDuplex() ? "Yes" : "No"));
	}
	
	private static byte[] inputStreamToByteArray(InputStream pInputStream) {
		FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();
        try {
			byte[] b = new byte[1024];
			int noOfBytes = 0;
      
			//read bytes from source file and write to destination file
			while( (noOfBytes = pInputStream.read(b)) != -1 ) {
			   fbaos.write(b, 0, noOfBytes);
			}
			return fbaos.getByteArray();
      
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			//close the streams
			try {
				pInputStream.close();
				fbaos.close();
			} catch (IOException ignore) {}
		}
	}
}