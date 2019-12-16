package com.bomwebportal.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dao.AllDocDAO;
import com.bomwebportal.dao.DisplayLkupDAO;
import com.bomwebportal.dao.HSTradeDescDAO;
import com.bomwebportal.dao.IGuardDAO;
import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dao.ReportDAO;
import com.bomwebportal.dao.VasDetailDAO;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.DisplayLkupDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.ItemDisplayDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.MultipleMrtSimDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.ServicePlanReportDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.dto.report.AdditionalChargeDTO;
import com.bomwebportal.dto.report.IPhoneTradeInFormDTO;
import com.bomwebportal.dto.report.MiscellaneousChargeDTO;
import com.bomwebportal.dto.report.ReportDTO;
import com.bomwebportal.dto.report.RptAppMobileServDTO;
import com.bomwebportal.dto.report.RptConciergeServiPadiPhoneDTO;
import com.bomwebportal.dto.report.RptCreditCardDDAuthDTO;
import com.bomwebportal.dto.report.RptGenericFormTemplateDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.dto.report.RptIGuardCareCashDTO;
import com.bomwebportal.dto.report.RptKeyInformationSheetDTO;
import com.bomwebportal.dto.report.RptMobPortAppDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneAFCDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneTnCDTO;
import com.bomwebportal.dto.report.RptMultiSimDTO;
import com.bomwebportal.dto.report.RptNFCConsentDTO;
import com.bomwebportal.dto.report.RptProjectEagleDTO;
import com.bomwebportal.dto.report.RptSecretarialServDTO;
import com.bomwebportal.dto.report.RptServiceGuideDTO;
import com.bomwebportal.dto.report.RptVasDetailDTO;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.dto.ui.SupportDocUI.GenerateSpringboardForm;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.form.MobCcsChgServCustInfoRefundDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.bomwebportal.util.PdfUtil;
import com.bomwebportal.util.ReportUtil;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.GenericReportHelper.GenericForm;
import com.bomwebportal.web.util.ProjectEagleReportHelper;
import com.bomwebportal.web.util.ReportHelper;
import com.bomwebportal.web.util.ReportHelper.PdfLang;
import com.bomwebportal.web.util.ReportSessionName;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;	
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {
	protected final Log logger = LogFactory.getLog(getClass());

	private static ArrayList<String> reportSeq = null;
	private static Map<String, String> m2Mapping = null;
	
	private static final String ITEM_SELECTION_GROUP_ID_HELPER_CARE = "6666666664";
	private static final String ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE= "6666666665";
	public static final String ITEM_TRAVEL_INSURANCE_CODE_TYPE= "TRAVEL_INSURANCE_STAFFID_ATTB_ID";
	public static final String ITEM_TRAVEL_INSURANCE_DM_CODE_TYPE= "TRAVEL_INSURANCE_DM_ATTB_ID";
	public static final String ITEM_HELPERCARE_INSURANCE_DM_CODE_TYPE= "HELPERCARE_INSURANCE_DM_ATTB_ID";
	public static final String ITEM_HELPERCARE_INSURANCE_CODE_TYPE= "HELPERCARE_INSURANCE_STAFFID_ATTB_ID";
	public static final String ITEM_PROJECT_EAGLE_INSURANCE_DM_CODE_TYPE = "PROJECT_EAGLE_INSURANCE_DM_ATTB_ID";
	
	private String imageFilePath;
//	private String tradeDescFormPath;
	
	private static String COMPANY_LOGO_FILE_CHI = "hkt_ch2_bw.png";
	private static String COMPANY_LOGO_FILE_ENG = "hkt_en2_bw.png";
	private static String COMPANY_BOTTOM_LOGO_FILE_CHI = "pccwlogo_ch.png";
	private static String COMPANY_BOTTOM_LOGO_FILE_ENG= "pccwlogo_en.png";
	private static String iGuardCareCashTNGzh = "careCash_tnc_zh.pdf";
	private static String iGuardCareCashTNGen = "careCash_tnc_en.pdf";
	private static String iGuardCareCashPICSzh = "careCash_pics_zh.pdf";
	private static String iGuardCareCashPICSen = "careCash_pics_en.pdf";
	private static String iGuardUadTNG = "iguard_uad_tnc.pdf";	
	
//	private static String COMPANY_LOGO_TOP_M2 = "csl_top_m2.png";
//	private static String COMPANY_LOGO_BOTTOM_RT_M2 = "one2free_bottom_m2.png";
//	private static String COMPANY_LOGO_BOTTOM_LT_CHI_M2 = "hkt_logo_m2_chi.png";
//	private static String COMPANY_LOGO_BOTTOM_LT_ENG_M2= "hkt_logo_m2_eng.png";	
	
/*	private static String COMPANY_LOGO_TOP_M2 = "csl_orange_300.PNG";
	private static String COMPANY_LOGO_BOTTOM_RT_M2 = "one2free_orange_300.PNG";
	private static String COMPANY_LOGO_BOTTOM_LT_CHI_M2 = "an-HKT-company_300dpi_zh.PNG";
	private static String COMPANY_LOGO_BOTTOM_LT_ENG_M2= "an-HKT-company_300dpi_en.PNG";*/	
	
	//move to ReportDTO setLogo()
	/*private static String COMPANY_LOGO_TOP_M2 = "CSL_300.jpg";
	private static String COMPANY_LOGO_BOTTOM_RT_M2 = "o2f_300.jpg";
	private static String COMPANY_LOGO_BOTTOM_LT_CHI_M2 = "hkt_zh_300.jpg";
	private static String COMPANY_LOGO_BOTTOM_LT_ENG_M2= "hkt_en_300.jpg";*/
	
	private static String COMPANY_CHOP_FILE = "hkt_logo.png";
	private static String IGUARD_DIRECT_LOGO_FILE = "iGuard_direct_logo.jpg";
	private static String IGUARD_PHONEPROTECTOR_LOGO_FILE = "HKTCare_logo.jpg";
	private static String MOBILE_SAFETY_PHONE_FILE = "mobile_safety_phone.jpg";
	
//	private static String OCT_CONSENT_FORM_P1_CHI = "oct_consent_chi_1.jpg"; //Athena 20130924
//	private static String OCT_CONSENT_FORM_P2_CHI = "oct_consent_chi_2.jpg";//Athena 20130924
//	private static String OCT_CONSENT_FORM_P1_ENG = "oct_consent_eng_1.jpg";//Athena 20130924
//	private static String OCT_CONSENT_FORM_P2_ENG = "oct_consent_eng_2.jpg";//Athena 20130924
	
	private static String FORM_ZH_EXT = "_zh";
	private static String RPT_LANG_ZH = "zh";
	private static String CUST_COPY = "_cust_copy";
	
	
	
	//add Eliot 20110831
	private OrderDAO orderDao;
    private DisplayLkupDAO displayLkupDAO;
    private HSTradeDescDAO hsTradeDescDAO;
	private DepositService depositService;// 20131031 Athena Deposit start
	private ReportDAO reportDAO;
	private VasDetailDAO vasDetailDao;
	private AllDocDAO allDocDao;
	private IGuardDAO iGuardDAO;

	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	private String appEnv;
	private String serverPath;

	private String oid; // 20131031 Athena Deposit
	private ReportHelper reportHelper;
	private GenericReportHelper genericReportHelper;
	private VasDetailService vasDetailService;
	private CodeLkupService codeLkupService;
	private IGuardService iGuardService;
	private String pdfFilePath;
	private OrderService orderService;
	private String mobPdfFilePath;
	private ItemDisplayService itemDisplayService;
	private OrdDocService ordDocService;
	private SupportDocService supportDocService;

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public IGuardService getiGuardService() {
		return iGuardService;
	}
	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	public OrderDAO getOrderDao() {
		return orderDao;
	}
	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public DisplayLkupDAO getDisplayLkupDAO() {
		return displayLkupDAO;
	}
	public void setDisplayLkupDAO(DisplayLkupDAO displayLkupDAO) {
		this.displayLkupDAO = displayLkupDAO;
	}

	public HSTradeDescDAO getHsTradeDescDAO() {
		return hsTradeDescDAO;
	}
	public void setHsTradeDescDAO(HSTradeDescDAO hsTradeDescDAO) {
		this.hsTradeDescDAO = hsTradeDescDAO;
	}
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}
	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}
	public DepositService getDepositService() {
		return depositService;
	}
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	public ReportDAO getReportDAO() {
		return reportDAO;
	}
	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	
	public VasDetailDAO getVasDetailDao() {
		return vasDetailDao;
	}
	public void setVasDetailDao(VasDetailDAO vasDetailDao) {
		this.vasDetailDao = vasDetailDao;
	}
	public AllDocDAO getAllDocDao() {
		return allDocDao;
	}
	public void setAllDocDao(AllDocDAO allDocDao) {
		this.allDocDao = allDocDao;
	}
	public IGuardDAO getiGuardDAO() {
		return iGuardDAO;
	}
	public void setiGuardDAO(IGuardDAO iGuardDAO) {
		this.iGuardDAO = iGuardDAO;
	}
	public String getPdfFilePath() {
		return pdfFilePath;
	}
	public void setPdfFilePath(String pdfFilePath) {
		this.pdfFilePath = pdfFilePath;
	}
	public String getMobPdfFilePath() {
		return mobPdfFilePath;
	}
	public void setMobPdfFilePath(String mobPdfFilePath) {
		this.mobPdfFilePath = mobPdfFilePath;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}

	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}
	
	public OrdDocService getOrdDocService() {
		return ordDocService;
	}
	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}
	
	public SupportDocService getSupportDocService() {
		return supportDocService;
	}
	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}

	static {
		initReportSeq();
		initM2Mapping();
	}

	private static void initReportSeq() {
		if (reportSeq == null) {
			reportSeq = new ArrayList<String>();
			//reportSeq.add(RptKeyInformationSheetDTO.JASPER_TEMPLATE);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE + FORM_ZH_EXT);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_CY);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_CY + FORM_ZH_EXT);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_RET);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_RET + FORM_ZH_EXT);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_UNI) ;
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_UNI + FORM_ZH_EXT );
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_NE);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_NE + FORM_ZH_EXT);	
			
			//reportSeq.add(RptKeyInformationSheetDTO.JASPER_TEMPLATE + CUST_COPY); 
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE + CUST_COPY);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE + FORM_ZH_EXT + CUST_COPY);
			
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_CY + CUST_COPY);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_CY + FORM_ZH_EXT + CUST_COPY);
			
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_UNI + CUST_COPY);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_UNI + FORM_ZH_EXT + CUST_COPY);
			
			//add bt eliot 20110824					
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_NE + CUST_COPY);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_NE + FORM_ZH_EXT + CUST_COPY);

			reportSeq.add(IPhoneTradeInFormDTO.JASPER_TEMPLATE);
			reportSeq.add(IPhoneTradeInFormDTO.JASPER_TEMPLATE + CUST_COPY);
			
			reportSeq.add(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8);
			reportSeq.add(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8 + CUST_COPY);


			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_RET + CUST_COPY);
			reportSeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_RET + FORM_ZH_EXT + CUST_COPY);


			reportSeq.add(RptCreditCardDDAuthDTO.JASPER_TEMPLATE);
			reportSeq.add(RptMobPortAppDTO.JASPER_TEMPLATE);
			
			reportSeq.add(MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS);
			reportSeq.add(MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS
					+ CUST_COPY);
	
			reportSeq.add(RptSecretarialServDTO.JASPER_TEMPLATE);
			// add by eliot 20110530
			reportSeq.add(RptHSTradeDescDTO.JASPER_TEMPLATE);

			// add by Margaret 20110610
			reportSeq.add(RptConciergeServiPadiPhoneDTO.JASPER_TEMPLATE);
			reportSeq.add(RptConciergeServiPadiPhoneDTO.JASPER_TEMPLATE
					+ FORM_ZH_EXT);
			
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE);
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE + FORM_ZH_EXT);
			// modified 20121008, 2 copies of SG
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE + CUST_COPY);
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE + FORM_ZH_EXT + CUST_COPY);
			
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_CY);
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_CY + FORM_ZH_EXT);
			// modified 20121008, 2 copies of SG
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_CY + CUST_COPY);
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_CY + FORM_ZH_EXT + CUST_COPY);
			
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_NE);
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_NE
					+ FORM_ZH_EXT);
			
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_Uni);
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_Uni + FORM_ZH_EXT);
			
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_Uni + CUST_COPY );
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_Uni + FORM_ZH_EXT + CUST_COPY );
			
			// modified 20121008, 2 copies of SG
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_NE + CUST_COPY);
			reportSeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_NE
					+ FORM_ZH_EXT + CUST_COPY);
			
			//iGuard LDS
			reportSeq.add(IGuardDTO.JASPER_TEMPLATE_LDS);
			reportSeq.add(IGuardDTO.JASPER_TEMPLATE_LDS + CUST_COPY);
			
			reportSeq.add(IGuardDTO.JASPER_TEMPLATE_TNC_LDS);
			//iGuard AD
			reportSeq.add(IGuardDTO.JASPER_TEMPLATE_AD);
			reportSeq.add(IGuardDTO.JASPER_TEMPLATE_AD + CUST_COPY);
			
			reportSeq.add(IGuardDTO.JASPER_TEMPLATE_TNC_AD);
			// mobile safety phone
			reportSeq.add(RptMobileSafetyPhoneDTO.JASPER_TEMPLATE);
			reportSeq.add(RptMobileSafetyPhoneAFCDTO.JASPER_TEMPLATE);
			reportSeq.add(RptMobileSafetyPhoneTnCDTO.JASPER_TEMPLATE);
			
			//NFC/Octopus/AIO Sim
			reportSeq.add(RptNFCConsentDTO.JASPER_TEMPLATE);
			
			//iGuardCareCash
			reportSeq.add(RptIGuardCareCashDTO.JASPER_TEMPLATE);
			reportSeq.add(RptIGuardCareCashDTO.JASPER_TEMPLATE + CUST_COPY);
			
			//iGuardUAD
			reportSeq.add(IGuardDTO.JASPER_TEMPLATE_UAD);
			reportSeq.add(IGuardDTO.JASPER_TEMPLATE_UAD + CUST_COPY);
		
			/*reportSeq.add(RptOctopusConsentDTO.JASPER_TEMPLATE);
			reportSeq.add(RptOctopusTnCDTO.JASPER_TEMPLATE);
			reportSeq.add(RptOctopusTnCDTO.JASPER_TEMPLATE + FORM_ZH_EXT);*/
			
			reportSeq.add(GenericForm.TRAVEL_INSURANCE_FORM.getFormName());
			reportSeq.add(GenericForm.TRAVEL_INSURANCE_FORM.getFormName() + CUST_COPY);
			
			reportSeq.add(GenericForm.HELPERCARE_INSURANCE_FORM.getFormName());
			reportSeq.add(GenericForm.HELPERCARE_INSURANCE_FORM.getFormName() + CUST_COPY);
			
			reportSeq.add(ProjectEagleReportHelper.FORM_NAME);
			reportSeq.add(ProjectEagleReportHelper.FORM_NAME + CUST_COPY);
			
		}
	}
	
	private static void initM2Mapping() {
		if (m2Mapping == null) {
			m2Mapping = new HashMap<String, String>();
			m2Mapping.put("AppMobileServ_zh", "M2AppMobileServ_zh");
			m2Mapping.put("AppMobileServ", "M2AppMobileServ");
			m2Mapping.put("M2AppMobileServCY_zh", "M2AppMobileServCY_zh");
			m2Mapping.put("M2AppMobileServCY", "M2AppMobileServCY");
			m2Mapping.put("M2AppMobileServRet_zh", "M2AppMobileServRet_zh");
			m2Mapping.put("M2AppMobileServRet", "M2AppMobileServRet");
			m2Mapping.put("M2AppMobileServUni", "M2AppMobileServUni");
			m2Mapping.put("M2AppMobileServUni_zh", "M2AppMobileServUni_zh");
			m2Mapping.put("ChgServCustInfoRefund", "M2ChgServCustInfoRefund");
			m2Mapping.put("CreditCardDDAuthorization", "M2CreditCardDDAuthorization");
			m2Mapping.put("CreditCardDDAuthorization_os", "M2CreditCardDDAuthorization_os");
			m2Mapping.put("HSTradeDescForm", "M2HSTradeDescForm");
			m2Mapping.put("IGuardCustInfo_LDS", "M2IGuardCustInfo");
			m2Mapping.put("IGuardTandC_LDS", "M2IGuardTandC");
			m2Mapping.put("IGuardCustInfo_AD", "M2IGuardCustInfo_AD");
			m2Mapping.put("IGuardTandC_AD", "M2IGuardTandC_AD");
			m2Mapping.put("KeyInformation", "M2KeyInformation");
			m2Mapping.put("MobileNumPortAppForm", "M2MobileNumPortAppForm");
			m2Mapping.put("NFCMobilePayment", "M2NFCMobilePayment");
			/*m2Mapping.put("OctopusMobilePayment", "M2OctopusMobilePayment");
			m2Mapping.put("OctopusMobilePayment_zh", "M2OctopusMobilePayment_zh");
			m2Mapping.put("M2OctopusMobilePaymentTnC", "M2OctopusMobilePaymentTnC");
			m2Mapping.put("M2OctopusMobilePaymentTnC_zh", "M2OctopusMobilePaymentTnC_zh");*/
			m2Mapping.put("SecretarialServiceForm", "M2SecretarialServiceForm");
			m2Mapping.put("ServiceGuide_zh", "M2ServiceGuide_zh");
			m2Mapping.put("ServiceGuide", "M2ServiceGuide");
			m2Mapping.put("M2ServiceGuideCY_zh", "M2ServiceGuideCY_zh");
			m2Mapping.put("M2ServiceGuideCY", "M2ServiceGuideCY");
			m2Mapping.put("M2ServiceGuideUni_zh", "M2ServiceGuideUni_zh");
			m2Mapping.put("M2ServiceGuideUni", "M2ServiceGuideUni");
			m2Mapping.put("TradeInHS", "M2TradeInHS");
			m2Mapping.put("CourierGuideline", "M2CourierGuideline");
			m2Mapping.put("DeliveryNote", "M2DeliveryNote");
			m2Mapping.put("DOADeliveryNote", "M2DOADeliveryNote");
			m2Mapping.put("StsDeliveryNote", "M2StsDeliveryNote");
			m2Mapping.put("StsDOADeliveryNote", "M2StsDOADeliveryNote");
			m2Mapping.put(GenericReportHelper.GENERIC_REPORT_TEMPLATE, GenericReportHelper.GENERIC_REPORT_TEMPLATE);
			m2Mapping.put(ProjectEagleReportHelper.FORM_NAME, ProjectEagleReportHelper.FORM_NAME);
		}
	}
	
	public Map<String, String> getM2Mapping() {
		if (m2Mapping == null) {
			initM2Mapping();
		}
		 return m2Mapping;
	}

	public ReportServiceImpl() {
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		logger.debug("imageFilePath=" + imageFilePath);
		this.imageFilePath = imageFilePath;
	}

	public ReportHelper getReportHelper() {
		return reportHelper;
	}
	public void setReportHelper(ReportHelper reportHelper) {
		this.reportHelper = reportHelper;
	}
	
	public GenericReportHelper getGenericReportHelper() {
		return genericReportHelper;
	}
	public void setGenericReportHelper(GenericReportHelper genericReportHelper) {
		this.genericReportHelper = genericReportHelper;
	}
	
	/**
	 * save pdf to server
	 */
	public String savePdfReports(List<Object> pReportArrList, String pLang, String orderId, String brand) {
		return savePdfReports(pReportArrList, pLang, orderId, brand, false);
	}
	
	public String savePdfReports(List<Object> pReportArrList, String pLang, String orderId, String brand, boolean isTemp) {
		
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("[ " + orderId + "] Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String locale = "EN";
		
		if ("zh".equalsIgnoreCase(pLang)) {
			locale = "CHI";
		} else {
			locale = "EN";
		}
		
		String path = StringUtils.replace(getServerPath(), "\\", "/") + orderId + (isTemp? "/temp" : "");
		logger.info("[" + orderId + "] savePdfReports path = " + path);
		File outputPdfPath = new File(path);
		File outputPdf = new File(path + "/" + orderId + "_" + locale + ".pdf");
		
		FileOutputStream fos = null;
		
		try {
			if (outputPdfPath.exists()) {
				if (outputPdf.exists()) {
					outputPdf.delete();
					outputPdf.createNewFile();
				} else {
					outputPdf.createNewFile();
				}
			} else {
				outputPdfPath.mkdirs();
				outputPdf.createNewFile();
			}

			fos = new FileOutputStream(outputPdf);
			this.generatePdfReports(pReportArrList, fos, pLang, orderId, brand);
			fos.close();
			fos = null;
			
		} catch (IOException ioe) {
			logger.error("[" + orderId + "] Exception caught in savePdfReports()", ioe);
			throw new AppRuntimeException(ioe);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
				}
			}
		}
		
		return StringUtils.substringAfter(StringUtils.replace(outputPdf.getAbsolutePath(), "\\", "/"), "/" + orderId + "/");
		
	}
	
	public String saveIGuardPdfReports(List<Object> pReportArrList, String orderId, String msisdn, String serialNo, String iGuardType, String brand) {
		return saveIGuardPdfReports(pReportArrList, orderId, msisdn, serialNo, iGuardType, brand, false);
	}
	
	/**
	 * save iGuard pdf to server
	 */
	public String saveIGuardPdfReports(List<Object> pReportArrList, String orderId, String msisdn, String serialNo, String iGuardType, String brand, boolean isTemp) {
		
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("[" + orderId + "] Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String path = StringUtils.replace(getServerPath(), "\\", "/") + orderId + (isTemp? "/temp" : "");
		logger.info("[" + orderId + "] saveIGuardPdfReports path = " + path);
		File outputPdfPath = new File(path);
		File outputPdf = new File(path + "/" + reportHelper.getIGuardSignedFormsFileName(orderId, msisdn, serialNo, iGuardType));
		
		FileOutputStream fos = null;
		
		try {
			if (outputPdfPath.exists()) {
				if (outputPdf.exists()) {
					outputPdf.delete();
					outputPdf.createNewFile();
				} else {
					outputPdf.createNewFile();
				}
			} else {
				outputPdfPath.mkdirs();
				outputPdf.createNewFile();
			}

			fos = new FileOutputStream(outputPdf);
			if("LDS".equalsIgnoreCase(iGuardType)){
				this.generatePdfReports(pReportArrList, fos, "en", orderId, Arrays.asList(Form.IGUARD_LDS), brand);
			}
			if("AD".equalsIgnoreCase(iGuardType)){
				this.generatePdfReports(pReportArrList, fos, "en", orderId, Arrays.asList(Form.IGUARD_AD), brand);
			}
			if("UAD".equalsIgnoreCase(iGuardType)){
				this.generatePdfReports(pReportArrList, fos, "en", orderId, Arrays.asList(Form.IGUARD_UAD), brand);
			}
			fos.close();
			fos = null;
			
		} catch (IOException ioe) {
			logger.error("[" + orderId + "] Exception caught in savePdfReports()", ioe);
			throw new AppRuntimeException(ioe);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
				}
			}
		}
		
		return StringUtils.substringAfter(StringUtils.replace(outputPdf.getAbsolutePath(), "\\", "/"), "/" + orderId + "/");
	}
	
	public String saveMobileSafetyPhoneReports(List<Object> pReportArrList,
			String pLang, String orderId, String brand) {
		return saveMobileSafetyPhoneReports(pReportArrList, pLang, orderId, brand, false);
	}
	
	public String saveMobileSafetyPhoneReports(List<Object> pReportArrList,
			String pLang, String orderId, String brand, boolean isTemp) {
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("[" + orderId +  "] Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String path = StringUtils.replace(getServerPath(), "\\", "/") + orderId + (isTemp? "/temp" : "");
		logger.info("[" + orderId + "] saveMobileSafetyPhoneReports path = " + path);
		File outputPdfPath = new File(path);
		File outputPdf = new File(path + "/" + this.reportHelper.getMobileSafetyPhoneFormsFileName(orderId));
		
		FileOutputStream fos = null;
		
		try {
			if (outputPdfPath.exists()) {
				if (outputPdf.exists()) {
					outputPdf.delete();
					outputPdf.createNewFile();
				} else {
					outputPdf.createNewFile();
				}
			} else {
				outputPdfPath.mkdirs();
				outputPdf.createNewFile();
			}

			fos = new FileOutputStream(outputPdf);
			this.generatePdfReports(pReportArrList, fos, pLang, orderId, Arrays.asList(Form.MOBILE_SAFETY_PHONE), brand);
			fos.close();
			fos = null;
			
		} catch (IOException ioe) {
			logger.error("[" + orderId + "] Exception caught in savePdfReports()", ioe);
			throw new AppRuntimeException(ioe);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
				}
			}
		}
		
		return StringUtils.substringAfter(StringUtils.replace(outputPdf.getAbsolutePath(), "\\", "/"), "/" + orderId + "/");
	}
	
	public String saveNFCSimReports(List<Object> pReportArrList,
			String pLang, String orderId, String brand) {
		return saveNFCSimReports(pReportArrList, pLang, orderId, brand, false);
	}
	
	public String saveNFCSimReports(List<Object> pReportArrList,
			String pLang, String orderId, String brand, boolean isTemp) {
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("[" + orderId + "] Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String path = StringUtils.replace(getServerPath(), "\\", "/") + orderId + (isTemp? "/temp" : "");
		logger.info("[" + orderId + "] saveNFCSimReports path = " + path);
		File outputPdfPath = new File(path);
		File outputPdf = new File(path + "/" + this.reportHelper.getNFCSimFormsFileName(orderId));
		
		FileOutputStream fos = null;
		
		try {
			if (outputPdfPath.exists()) {
				if (outputPdf.exists()) {
					outputPdf.delete();
					outputPdf.createNewFile();
				} else {
					outputPdf.createNewFile();
				}
			} else {
				outputPdfPath.mkdirs();
				outputPdf.createNewFile();
			}

			fos = new FileOutputStream(outputPdf);
			this.generatePdfReports(pReportArrList, fos, pLang, orderId, Arrays.asList(Form.NFC_SIM_SET), brand);
			fos.close();
			fos = null;
			
		} catch (IOException ioe) {
			logger.error("[" + orderId + "] Exception caught in savePdfReports()", ioe);
			throw new AppRuntimeException(ioe);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
				}
			}
		}
		
		return StringUtils.substringAfter(StringUtils.replace(outputPdf.getAbsolutePath(), "\\", "/"), "/" + orderId + "/");
	}
	/**
	 * save iGuard pdf to server
	 */
	public String saveIGuardCareCashPdfReports(List<Object> pReportArrList,String pLang,String orderId, String msisdn, String brand) {
		return saveIGuardCareCashPdfReports(pReportArrList, pLang, orderId, msisdn, brand, false);
	}
	
	public String saveIGuardCareCashPdfReports(List<Object> pReportArrList,String pLang,String orderId, String msisdn, String brand, boolean isTemp) {
		
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("[" + orderId + "] Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String path = StringUtils.replace(getServerPath(), "\\", "/") + orderId + (isTemp? "/temp" : "");
		logger.info("[" + orderId + "] saveIGuardCareCashPdfReports path = " + path);
		File outputPdfPath = new File(path);
		File outputPdf = new File(path + "/" + reportHelper.getIGuardCareCashFormsFileName(orderId,pLang));
		
		FileOutputStream fos = null;
		
		try {
			if (outputPdfPath.exists()) {
				if (outputPdf.exists()) {
					outputPdf.delete();
					outputPdf.createNewFile();
				} else {
					outputPdf.createNewFile();
				}
			} else {
				outputPdfPath.mkdirs();
				outputPdf.createNewFile();
			}

			fos = new FileOutputStream(outputPdf);
				this.generatePdfReports(pReportArrList, fos, pLang, orderId, Arrays.asList(Form.IGUARD_CARECASH), brand);
			fos.close();
			fos = null;
			
		} catch (IOException ioe) {
			logger.error("[" + orderId + "] Exception caught in savePdfReports()", ioe);
			throw new AppRuntimeException(ioe);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
				}
			}
		}
		
		return StringUtils.substringAfter(StringUtils.replace(outputPdf.getAbsolutePath(), "\\", "/"), "/" + orderId + "/");
	}

	
	/*public void saveOctopusSimReports(List<Object> pReportArrList,
			String pLang, String orderId) {
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		File outputPdfPath = new File(getServerPath() + "/" + orderId);
		File outputPdf = new File(getServerPath() + "/" + orderId + "/" + this.reportHelper.getOctopusSimFormsFileName(orderId));
		
		FileOutputStream fos = null;
		
		try {
			if (outputPdfPath.exists()) {
				if (outputPdf.exists()) {
					outputPdf.delete();
					outputPdf.createNewFile();
				} else {
					outputPdf.createNewFile();
				}
			} else {
				outputPdfPath.mkdir();
				outputPdf.createNewFile();
			}

			fos = new FileOutputStream(outputPdf);
			this.generatePdfReports(pReportArrList, fos, pLang, orderId, Arrays.asList(Form.NFC_SIM));
			fos.close();
			fos = null;
			
		} catch (IOException ioe) {
			logger.error("Exception caught in savePdfReports()", ioe);
			throw new AppRuntimeException(ioe);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
				}
			}
		}
	}*/
	
	public void generatePdfReports(List<Object> pReportArrList,
			OutputStream pOutputStream, String pLang, String orderId, String brand) {
		this.generatePdfReports(pReportArrList, pOutputStream, pLang, orderId, null, brand);
	}
	
	public void generatePdfReports(List<Object> pReportArrList,
			OutputStream pOutputStream, String pLang, String orderId, List<Form> forms, String brand) {
		try {
			logger.info("generatePdfReports(pReportArrList) is called in ReportServiceImpl.java");

			//add ReportDTO, Key: JasperName (Report Seq) / (Step 7) Value.JasperReport.JasperName: (Jasper XML)
			HashMap<String, JasperReport> reportDataMap = mapReportData(pReportArrList, pLang, forms,brand); 
			ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

			//init a report display sequence using JasperName
			if (reportSeq == null) {
				initReportSeq();
			}

			boolean isM2Ind = false;
			OrderDTO order = orderDao.getOrder(orderId);
			logger.info("appDate = " + order.getAppInDate());
			if (this.isGenerateM2Report(order.getAppInDate())) {
				isM2Ind = true;
				if (m2Mapping == null) {
					initM2Mapping();
				}
			}
			logger.debug("isM2Ind = " + isM2Ind);
			
			String key = null;
			// List<ReportDTO> keyValue = null;
			JasperReport keyValue = null;
			// loop thru each report template and generate report if the report
			// data existsA
			for (int i = 0; i < reportSeq.size(); i++) {
				key = reportSeq.get(i);
				keyValue = reportDataMap.get(key);
				if (keyValue != null ) {
				
					if (key.equalsIgnoreCase("HSTradeDescForm")){
						int hsTradeDescFormInd = 0 ; 
						List<HSTradeDescDTO> hsTradeDescList = hsTradeDescDAO.getHSTradeDescList(orderId);
						for (HSTradeDescDTO hsTradeDesc:hsTradeDescList){
							ArrayList<InputStream> hsTradeDescForm = new ArrayList<InputStream>(); 
							if (StringUtils.isNotEmpty(hsTradeDesc.getPisPath())){
								hsTradeDescForm.add(new FileInputStream(mobPdfFilePath+"/pis/" +hsTradeDesc.getPisPath()));
								pdfStreamList.addAll(hsTradeDescForm);
								hsTradeDescFormInd++;
							}else{
								ArrayList<ReportDTO> rptDtoArrList = new ArrayList<ReportDTO>();
								rptDtoArrList.add(keyValue.getRptDtoArrList().get(hsTradeDescFormInd));
								JasperReport hsTradeForm  = new JasperReport(RptHSTradeDescDTO.JASPER_TEMPLATE,rptDtoArrList);
								pdfStreamList.add(generatePdfInputStream(
										keyValue.getJasperTemplateName(),
										hsTradeForm.getRptDtoArrList(), isM2Ind, pLang, brand));
								
								hsTradeDescFormInd++;
							}
						}
						
					}else if (key.equalsIgnoreCase("ServiceGuide") || key.equalsIgnoreCase("ServiceGuide_zh") || key.equalsIgnoreCase("ServiceGuide_cust_copy") || key.equalsIgnoreCase("ServiceGuide_zh_cust_copy")){
						File M2ServiceGuide = new File ("");
						if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
							if("en".equals(pLang)){
								M2ServiceGuide =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.M2ServiceGuide_1010_eng);
							}
							else{
								M2ServiceGuide =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.M2ServiceGuide_1010_chi);
							}
						} else {
							if("en".equals(pLang)){
								M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.M2ServiceGuide_csl_eng);
							} else {
								M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.M2ServiceGuide_csl_chi);
							}
							
						}
						ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
						ServiceGuide.add(new FileInputStream(M2ServiceGuide));

						pdfStreamList.addAll(ServiceGuide);
						
					} else if (key.equalsIgnoreCase("M2ServiceGuideUni") || key.equalsIgnoreCase("M2ServiceGuideUni_zh") || key.equalsIgnoreCase("M2ServiceGuideUni_cust_copy") || key.equalsIgnoreCase("M2ServiceGuideUni_zh_cust_copy")){
						File M2ServiceGuide = new File ("");
						
						if("en".equals(pLang)){
							M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.UniServiceGuide_eng);
						}
						else{
							M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.UniServiceGuide_chi);
						}
						
						ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
						ServiceGuide.add(new FileInputStream(M2ServiceGuide));

						pdfStreamList.addAll(ServiceGuide);
						
					} else {
						pdfStreamList.add(generatePdfInputStream(
							keyValue.getJasperTemplateName(),
							keyValue.getRptDtoArrList(), isM2Ind, pLang, brand));
					}
					
					if (key.equals("IguardCareCash") || key.equals("IguardCareCash_cust_copy")){
						File careCashTNG = new File ("");
						File careCashPICS = new File("");
						if (pLang.equals("en")){
							careCashTNG = new File(pdfFilePath+"/"+iGuardCareCashTNGen);
							careCashPICS = new File(pdfFilePath+"/"+iGuardCareCashPICSen);
						}else{
						 careCashTNG =new File(pdfFilePath+"/"+iGuardCareCashTNGzh);
						 careCashPICS =new File(pdfFilePath+"/"+iGuardCareCashPICSzh);
						}
						ArrayList<InputStream> careCashForm = new ArrayList<InputStream>(); 
						careCashForm.add(new FileInputStream(careCashTNG));
						careCashForm.add(new FileInputStream(careCashPICS));
							
						pdfStreamList.addAll(careCashForm);
						
					}
					
					if (key.equals("M2IGuardCustInfo_UAD") || key.equals("M2IGuardCustInfo_UAD_cust_copy")){
						File UadTNG = new File ("");
						UadTNG =new File(mobPdfFilePath+"/"+iGuardUadTNG);
						ArrayList<InputStream> iGuardUadForm = new ArrayList<InputStream>(); 
						iGuardUadForm.add(new FileInputStream(UadTNG));

						pdfStreamList.addAll(iGuardUadForm);
						
					}
					
					// Travel Insurance TNC
					List<VasDetailDTO> vasSelectedList = vasDetailService.getVasDetailSelectedList("en", orderId);
					List<String> selectedVasItemList = new ArrayList<String>();
					for (VasDetailDTO vasSelected : vasSelectedList) {
						selectedVasItemList.add(vasSelected.getItemId());
					}
					String travelInsuranceDays = itemDisplayService.getTravelInsuranceDays(selectedVasItemList);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("travelInsuranceDays", travelInsuranceDays);
					
					if (StringUtils.containsIgnoreCase(key, GenericReportHelper.TRAVEL_INSURANCE_FORM_FORM_NAME)) {
						File travelInsuranceTnCFile = new File(mobPdfFilePath + "/" + Utility.fillInVariables(GenericReportHelper.TRAVEL_INSURANCE_FORM_TNC_PDF, params));
						if (travelInsuranceTnCFile != null && travelInsuranceTnCFile.exists()) {
							ArrayList<InputStream> travelInsuranceTnC = new ArrayList<InputStream>(); 
							travelInsuranceTnC.add(new FileInputStream(travelInsuranceTnCFile));
							pdfStreamList.addAll(travelInsuranceTnC);
						}
					}
					
					// Helper Insurance TNC
					if (StringUtils.containsIgnoreCase(key, GenericReportHelper.HELPERCARE_INSURANCE_FORM_FORM_NAME)) {
						File helperCareInsuranceTnCFile = new File(mobPdfFilePath + "/" + GenericReportHelper.HELPERCARE_INSURANCE_FORM_TNC_PDF);
						if (helperCareInsuranceTnCFile != null && helperCareInsuranceTnCFile.exists()) {
							ArrayList<InputStream> helperCareInsuranceTnC = new ArrayList<InputStream>(); 
							helperCareInsuranceTnC.add(new FileInputStream(helperCareInsuranceTnCFile));
							pdfStreamList.addAll(helperCareInsuranceTnC);
						}
					}
					
					// Project Eagle TNC
					if (StringUtils.containsIgnoreCase(key, ProjectEagleReportHelper.FORM_NAME)) {
						File tnCFile = new File(mobPdfFilePath + "/" + ProjectEagleReportHelper.TNC_PDF);
						if (tnCFile != null && tnCFile.exists()) {
							ArrayList<InputStream> tnC = new ArrayList<InputStream>(); 
							tnC.add(new FileInputStream(tnCFile));
							pdfStreamList.addAll(tnC);
						}
					}
				}
			}

			FastByteArrayOutputStream baosMerged = new FastByteArrayOutputStream();
			
			PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, null); // gen page no for each set of pdf

			byte[] pdfData = baosMerged.getByteArray();
			
			pOutputStream.write(pdfData);
			pOutputStream.flush();

			baosMerged.close();

		} catch (Exception de) {
			logger.error("[" + orderId + "] Exception caught in generatePdfReports()", de);
			throw new AppRuntimeException(de);
		}
	}

	/**
	 * Generate PDF form/report from jasper template
	 * 
	 * @param pJasperName
	 *            jasper template name
	 * @param dtoArrList
	 *            ArrayList storing report data
	 * @return InputStream of generated pdf
	 * @throws IOException
	 */
	private InputStream generatePdfInputStream(String pJasperName,
			List<ReportDTO> dtoArrList, boolean isM2Ind,String pLang, String brand) throws Exception {
		FastByteArrayOutputStream baos = new FastByteArrayOutputStream();


		String jasperPath = "";
		if (isM2Ind) {
			jasperPath = BomWebPortalConstant.JASPER_PATH_MOB;
			if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
				jasperPath = BomWebPortalConstant.JASPER_PATH_MOB_1010;
			}
			if (m2Mapping.containsKey(pJasperName)) {
				jasperPath = jasperPath + m2Mapping.get(pJasperName);
			} else {
				jasperPath = jasperPath + pJasperName;
			}
		} else {
			jasperPath = BomWebPortalConstant.JASPER_PATH_MOB + pJasperName;
		}
		
		for (ReportDTO rpt : dtoArrList){
			rpt.setLogo(pLang, isM2Ind, brand);
			if (isM2Ind) {
				if (m2Mapping.containsKey(pJasperName)) {
					String configFile = "reportConstantM2.xml";
					ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile);
					if ("M2AppMobileServ_zh".equalsIgnoreCase(m2Mapping.get(pJasperName))||"M2AppMobileServ".equalsIgnoreCase(m2Mapping.get(pJasperName))
							||"M2AppMobileServCY_zh".equalsIgnoreCase(m2Mapping.get(pJasperName))||"M2AppMobileServCY".equalsIgnoreCase(m2Mapping.get(pJasperName))){
						
						if (RPT_LANG_ZH.equalsIgnoreCase(pLang)){
							((RptAppMobileServDTO) rpt).setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateChi1").toString());
							((RptAppMobileServDTO) rpt).setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateChi2").toString());
							if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
								((RptAppMobileServDTO) rpt).setCustAgree(appCtx.getBean("ss1010CustAgreeChi").toString());
							}else{
								if (((RptAppMobileServDTO) rpt).isStudentPlanSubInd()) {
									((RptAppMobileServDTO) rpt).setCustAgree(appCtx.getBean("ssCustAgreeCYChi").toString());
								} else {
									((RptAppMobileServDTO) rpt).setCustAgree(appCtx.getBean("ssCustAgreeChi").toString());
								}
							}
						}else{
							((RptAppMobileServDTO) rpt).setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateEng1").toString());
							((RptAppMobileServDTO) rpt).setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateEng2").toString());
							if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
								((RptAppMobileServDTO) rpt).setCustAgree(appCtx.getBean("ss1010CustAgreeEng").toString());
							}else{
								if (((RptAppMobileServDTO) rpt).isStudentPlanSubInd()) {
									((RptAppMobileServDTO) rpt).setCustAgree(appCtx.getBean("ssCustAgreeCYEng").toString());
								} else {
									((RptAppMobileServDTO) rpt).setCustAgree(appCtx.getBean("ssCustAgreeEng").toString());
								}
							}
						}
					}
					if ("M2CreditCardDDAuthorization".equalsIgnoreCase(m2Mapping.get(pJasperName))){
						
						if (RPT_LANG_ZH.equalsIgnoreCase(pLang)){
							if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
								((RptCreditCardDDAuthDTO) rpt).setCcos7(appCtx.getBean("cc1010OSState7").toString());
								((RptCreditCardDDAuthDTO) rpt).setCcos8(appCtx.getBean("cc1010OSState8").toString());
							} else {
								((RptCreditCardDDAuthDTO) rpt).setCcos7(appCtx.getBean("ccOSState7").toString());
								((RptCreditCardDDAuthDTO) rpt).setCcos8(appCtx.getBean("ccOSState8").toString());
							}
							}else{
							if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
								((RptCreditCardDDAuthDTO) rpt).setCcos7(appCtx.getBean("cc1010OSState7").toString());
								((RptCreditCardDDAuthDTO) rpt).setCcos8(appCtx.getBean("cc1010OSState8").toString());
							} else {
								((RptCreditCardDDAuthDTO) rpt).setCcos7(appCtx.getBean("ccOSState7").toString());
								((RptCreditCardDDAuthDTO) rpt).setCcos8(appCtx.getBean("ccOSState8").toString());
							}
							}					
					}
					/*if ("M2OctopusMobilePayment".equalsIgnoreCase(m2Mapping.get(pJasperName))){
						
						if (RPT_LANG_ZH.equalsIgnoreCase(pLang)){
							((RptOctopusConsentDTO) rpt).setOctFormP1("CHI");
							}else{
								((RptOctopusConsentDTO) rpt).setOctFormP1("ENG");
							}					
					}*/
				}
			}
		}
		JRDataSource ds = new JRBeanCollectionDataSource(dtoArrList);
		logger.debug("jasperPath = " + jasperPath);
		try {
			ReportUtil.generatePdfReport(jasperPath, ds, baos);
		} catch (Exception e) {
			System.out.println(e);
			logger.debug("Exception @ReportServiceImp: generatePdfInputStream " + e.getMessage());
			return null;
		} finally {
			baos.close();
		}

		return baos.getInputStream();
	}

	/*
	private void emailPdfReport(String pEmailAddr, String pMobileNum,
			byte[] pPdfData) throws Exception {
		// send email
		String sender = "pccwmobile@pccw.com";
		String recipients[] = new String[1];
		recipients[0] = pEmailAddr;

		MailUtil.sendMail(sender, recipients, null, null,
				"Email from Project SpringBoard " + pMobileNum,
				"Test Pdf Attachment", MailUtil.MAIL_TYPE_HTML, pPdfData);
	}
	*/

	/**
	 * Assign report data to specific report. Size of Map return should be same
	 * of number of report template generate.
	 */
	// private HashMap<String, ArrayList<ReportDTO>>
	// mapReportData(List<Object> pDTOs, String pLang)
	private HashMap<String, JasperReport> mapReportData(
			List<Object> pDTOs, String pLang, List<Form> forms,String brand)
			throws InvocationTargetException, IllegalAccessException {
		// HashMap<String, ArrayList<ReportDTO>> map = new HashMap<String,
		// ArrayList<ReportDTO>>();
		
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();
		
		HashMap<String, String> genericReportDataMap = new HashMap<String, String>();
		HashMap<String, InputStream> genericReportSignatureMap = new HashMap<String, InputStream>();
		
		/*
		 * Step 1. Create ArrayList of ReportDTO 
		 */
		List<ReportDTO> keyInformationSheetList = new ArrayList<ReportDTO>(); 
		List<ReportDTO> keyInformationSheetCustCopyList = new ArrayList<ReportDTO>(); 
		List<ReportDTO> ssList = new ArrayList<ReportDTO>();
		List<ReportDTO> ssCustCopyList = new ArrayList<ReportDTO>();
		List<ReportDTO> ccList = new ArrayList<ReportDTO>();
		List<ReportDTO> mnpList = new ArrayList<ReportDTO>();
		List<ReportDTO> secSrvList = new ArrayList<ReportDTO>();
		List<ReportDTO> tradeDescList = new ArrayList<ReportDTO>(); 
		List<ReportDTO> conSrvList = new ArrayList<ReportDTO>(); 
		List<ReportDTO> serviceGuideList = new ArrayList<ReportDTO>(); 
		List<ReportDTO> chgServCustInfoRefundList = new ArrayList<ReportDTO>(); 
		List<ReportDTO> chgServCustInfoRefundCustCopyList = new ArrayList<ReportDTO>(); 
		List<ReportDTO> iGuardList = new ArrayList<ReportDTO>(); 
		List<ReportDTO> iGuardCareCashList = new ArrayList<ReportDTO>();
		List<ReportDTO> iGuardCustCopyList = new ArrayList<ReportDTO>();
		List<ReportDTO> mobileSafetyPhoneList = new ArrayList<ReportDTO>();
		List<ReportDTO> mobileSafetyPhoneAFCList = new ArrayList<ReportDTO>();
		List<ReportDTO> mobileSafetyPhoneTnCList = new ArrayList<ReportDTO>();
		List<ReportDTO> nfcSimList = new ArrayList<ReportDTO>();
		/*List<ReportDTO> octopusSimList = new ArrayList<ReportDTO>();
		List<ReportDTO> octopusSimTnCList = new ArrayList<ReportDTO>();*/
		List<ReportDTO> multiSimMnpList = new ArrayList<ReportDTO>();//MultiSim Athena 20140128
		List<ReportDTO> multiSimChgServCustInfoRefundList = new ArrayList<ReportDTO>(); //MultiSim Athena 20140128
		List<ReportDTO> multiSimChgServCustInfoRefundCustCopyList = new ArrayList<ReportDTO>(); //MultiSim Athena 20140128
		List<ReportDTO> iPhoneTradeInFormDTOList = new ArrayList<ReportDTO>();
		List<ReportDTO> iPhoneTradeInFormCustCopyDTOList = new ArrayList<ReportDTO>();
		List<ReportDTO> projectEagleList = new ArrayList<ReportDTO>();
		
		/*
		 * Step 2. Create each kind of report dto 
		 */
		RptKeyInformationSheetDTO rptKeyInformationSheetDTO = new RptKeyInformationSheetDTO();       
		RptKeyInformationSheetDTO rptKeyInformationSheetCustCopyDTO = new RptKeyInformationSheetDTO();   
		RptAppMobileServDTO rptAppMobileServDTO = new RptAppMobileServDTO();
		RptAppMobileServDTO rptAppMobileServCustCopyDTO = new RptAppMobileServDTO();
		RptCreditCardDDAuthDTO rptCreditCardDDAuthDTO = new RptCreditCardDDAuthDTO();
		RptMobPortAppDTO rptMobPortAppDTO = new RptMobPortAppDTO();
		RptSecretarialServDTO rptSecretarialServDTO = new RptSecretarialServDTO();
		RptHSTradeDescDTO rptRptHSTradeDescDTO = new RptHSTradeDescDTO(); // add by eliot 20110527
		RptConciergeServiPadiPhoneDTO rptConciergeServiPadiPhoneDTO = new RptConciergeServiPadiPhoneDTO(); // add by Margaret 20110610
		RptServiceGuideDTO rptServiceGuideDTO = new RptServiceGuideDTO();
		MobCcsChgServCustInfoRefundDTO rptChgServCustInfoRefundDTO = new MobCcsChgServCustInfoRefundDTO();
		MobCcsChgServCustInfoRefundDTO rptChgServCustInfoRefundCustCopyDTO = new MobCcsChgServCustInfoRefundDTO();
		IGuardDTO iGuardDTO = new IGuardDTO();
		IGuardDTO iGuardCustCopyDTO = new IGuardDTO();
		RptIGuardCareCashDTO rptIGuardCareCashDTO = new RptIGuardCareCashDTO();
		RptMobileSafetyPhoneDTO mobileSafetyPhoneDTO = new RptMobileSafetyPhoneDTO();
		RptMobileSafetyPhoneAFCDTO mobileSafetyPhoneAFCDTO = new RptMobileSafetyPhoneAFCDTO();
		RptMobileSafetyPhoneTnCDTO mobileSafetyPhoneTnCDTO = new RptMobileSafetyPhoneTnCDTO();
		IPhoneTradeInFormDTO iPhoneTradeInFormDTO = new IPhoneTradeInFormDTO();
		IPhoneTradeInFormDTO iPhoneTradeInFormCustCopyDTO = new IPhoneTradeInFormDTO();
		mobileSafetyPhoneTnCDTO.setMonthlyRate("0");
		mobileSafetyPhoneTnCDTO.setMonthlyRateAfter("0");
		mobileSafetyPhoneTnCDTO.setCommitmentPeriod("1");
		RptNFCConsentDTO rptNFCConsentDTO = new RptNFCConsentDTO();
		/*RptOctopusConsentDTO rptOctopusConsentDTO = new RptOctopusConsentDTO();
		RptOctopusTnCDTO rptOctopusTnCDTO = new RptOctopusTnCDTO();*/
		List<HSTradeDescDTO> hsTradeDescDTOs = null;
		RptProjectEagleDTO rptProjectEagleDTO = new RptProjectEagleDTO();
		
		/*
		 * xxStep 3. Set company logo
		 */
		rptRptHSTradeDescDTO.setCompanyChop(imageFilePath + "/" + COMPANY_CHOP_FILE);

		/*
		 * Step 4. Determine which form will be triggered
		 */
		boolean isReqCreditCardAuthForm = false;
		boolean isReqMnpForm = false;
		boolean isReqSecSrvForm = false;
//		boolean isReqTradeDescForm = false; // add by eliot 20110530
		boolean isConSrvForm = false; // add by Margaret 20110610
		boolean isNEForm = false; //add Eliot 20110823
		boolean isChgServCustInfoRefundForm = false;
		boolean isStudentPlan = false;
		boolean isUniversityPlan = false;
		boolean uniOptionalVasInd = false;
		boolean isIphone7TradeInBasket = false;
		boolean isIphone8TradeInBasket = false;
		List<CodeLkupDTO> iphoneSevenTradeInList= LookupTableBean.getInstance().getCodeLkupList().get("IP7_TRADE_IN");
		List<CodeLkupDTO> iphoneEightTradeInList= LookupTableBean.getInstance().getCodeLkupList().get("IP8_TRADE_IN");
		boolean isCRETPlan = false;
		
		String billMediaString ="";//Paper bill Athena 20130925
		String billMediaStringZh ="";//Paper bill Athena 20130925		
		String custDocNum = "";
		String mnpDocNum = "";
		String perPaidSim = "";
		DisMode distMode = OrderDTO.DisMode.P;
		boolean isAdvanceOrd = false;
		SignoffDTO custSignDTO = null;  //
		VasOnetimeAmtDTO simOnetimeCharge = null;
		Date appInDate = null;
	
		// Store staff info from MobileSimInfo page for all S&S use
		String salesName = "";
		String salesCode = "";
		String privacyInd99992="N";
		String privacyInd99993="N";
		String privacyInd99994="N";
		
		/*
		 * Step 5. Cast which type of dto is passing
		 * 			and copy the field of same name
		 * 			to corresponding report dto
		 */
		Object tempObj = null;
		
		for (int i = 0; i < pDTOs.size(); i++) {
			
			tempObj = pDTOs.get(i);
			
			if (tempObj instanceof CustomerProfileDTO) {
				CustomerProfileDTO customerProfileDto = (CustomerProfileDTO) tempObj;
				String idDocNumMask=null;
				if (StringUtils.isNotEmpty(customerProfileDto.getIdDocNum())){
				idDocNumMask = maskedDocNum(customerProfileDto.getIdDocNum());
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ID_DOC_NUM, idDocNumMask);
					rptProjectEagleDTO.setIdDocNum(idDocNumMask);
				}
				BeanUtils.copyProperties(rptKeyInformationSheetDTO, customerProfileDto); //20130709
				BeanUtils.copyProperties(rptAppMobileServDTO, customerProfileDto);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, customerProfileDto);
				BeanUtils.copyProperties(rptMobPortAppDTO, customerProfileDto);
				BeanUtils.copyProperties(rptSecretarialServDTO, customerProfileDto);
				rptIGuardCareCashDTO.setCustEngName(customerProfileDto.getTitle()+ " "+customerProfileDto.getCustLastName()+" "+customerProfileDto.getCustFirstName());
				rptIGuardCareCashDTO.setContactPhone(customerProfileDto.getContactPhone());
				rptIGuardCareCashDTO.setDob(customerProfileDto.getDob());
				rptIGuardCareCashDTO.setEmailAddr(customerProfileDto.getEmailAddr());
				rptIGuardCareCashDTO.setIdDocNum(customerProfileDto.getIdDocNum());
				if ("I".equals(customerProfileDto.getCareDmSupInd())){
				rptIGuardCareCashDTO.setPrivacyInd("Y");
				}else{
				rptIGuardCareCashDTO.setPrivacyInd("N");
				}
				rptSecretarialServDTO.setCompanyName(customerProfileDto.getCompanyName());
				rptSecretarialServDTO.setIdDocType(customerProfileDto.getIdDocType());
				rptCreditCardDDAuthDTO
						.setCompanyName(customerProfileDto.getCompanyName());
				BeanUtils.copyProperties(rptConciergeServiPadiPhoneDTO, customerProfileDto);
				iPhoneTradeInFormDTO.setCustEngName(customerProfileDto.getTitle()+" "+customerProfileDto.getCustLastName()+" "+customerProfileDto.getCustFirstName());

				//Paper bill Athena 20130925 start
				if (customerProfileDto.getSelectedBillMedia()!=null){
					List<MobBillMediaDTO> billMediaList  = LookupTableBean.getInstance().getBillMediaOption();
					
					for(MobBillMediaDTO mbm: billMediaList) {
						for (String billCd: customerProfileDto.getSelectedBillMedia()) {
							if (billCd.equals(mbm.getCodeId())) {
								billMediaString += "/ " +  mbm.getEngDesc();
								billMediaStringZh += "/ " +  mbm.getChiDesc();
							}
						}
					}
					
				}
				//Paper bill Athena 20130925 end				
				if ("PASS".equals(customerProfileDto.getIdDocType())) {
					rptChgServCustInfoRefundDTO.setIdDocType("Passport:");
					rptChgServCustInfoRefundDTO.setCustomerNameLabelDisp("Customer Name:");
					rptChgServCustInfoRefundDTO
							.setCustomerName(customerProfileDto
									.getTitle()
									+ " "
									+ customerProfileDto.getCustLastName()
									+ " "
									+ customerProfileDto.getCustFirstName());
					rptAppMobileServDTO.setIdDocNum(idDocNumMask);
					rptChgServCustInfoRefundDTO.setIdDocNum(idDocNumMask);
				} else if ("BS".equals(customerProfileDto.getIdDocType())) {
					rptChgServCustInfoRefundDTO.setIdDocType("BR No.:");
					rptChgServCustInfoRefundDTO.setCustomerNameLabelDisp("Company Name:");
					rptChgServCustInfoRefundDTO.setCustomerName(customerProfileDto.getCompanyName());
					rptAppMobileServDTO.setIdDocNum(customerProfileDto.getIdDocNum());
					rptChgServCustInfoRefundDTO.setIdDocNum(customerProfileDto.getIdDocNum());
				} else {
					rptChgServCustInfoRefundDTO.setIdDocType("HKID:");
					rptChgServCustInfoRefundDTO.setCustomerNameLabelDisp("Customer Name:");
					rptChgServCustInfoRefundDTO.setCustomerName(customerProfileDto
									.getTitle()
									+ " "
									+ customerProfileDto.getCustLastName()
									+ " "
									+ customerProfileDto.getCustFirstName());
					rptAppMobileServDTO.setIdDocNum(idDocNumMask);
					rptChgServCustInfoRefundDTO.setIdDocNum(idDocNumMask);
				}

				rptChgServCustInfoRefundDTO.setContactPhone(customerProfileDto.getContactPhone());
				
				custDocNum = customerProfileDto.getIdDocNum();

				BeanUtils.copyProperties(mobileSafetyPhoneDTO, customerProfileDto);
				BeanUtils.copyProperties(mobileSafetyPhoneAFCDTO, customerProfileDto);
				BeanUtils.copyProperties(mobileSafetyPhoneTnCDTO, customerProfileDto);
				BeanUtils.copyProperties(rptNFCConsentDTO, customerProfileDto);
				//BeanUtils.copyProperties(rptOctopusConsentDTO, customerProfileDto);
				
				isStudentPlan = customerProfileDto.isStudentPlanSubInd();
				
				genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUSTOMER_TITLE, customerProfileDto.getTitle());
				genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUST_LAST_NAME, customerProfileDto.getCustLastName());
				genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUST_FIRST_NAME, customerProfileDto.getCustFirstName());
				genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_EMAIL_ADDR, customerProfileDto.getEmailAddr());
				
				//Project Eagle Insurance
				
				rptProjectEagleDTO.setSurname(customerProfileDto.getCustLastName());
				rptProjectEagleDTO.setGivenName(customerProfileDto.getCustFirstName());
				rptProjectEagleDTO.setTitle(customerProfileDto.getTitle());
				rptProjectEagleDTO.setEmail(customerProfileDto.getEmailAddr());
				rptProjectEagleDTO.setContactNumber(customerProfileDto.getContactPhone());
				String maskedDob = Utility.maskedDob(customerProfileDto.getDobStr());
				rptProjectEagleDTO.setDateOfBirth(maskedDob);
				rptProjectEagleDTO.setAddressLine1(customerProfileDto.getProjectEagleAddressLine1());
				rptProjectEagleDTO.setAddressLine2(customerProfileDto.getProjectEagleAddressLine2());
				rptProjectEagleDTO.setAddressLine3(customerProfileDto.getProjectEagleAddressLine3());
			} else if (tempObj instanceof ServicePlanReportDTO) {
				
				ServicePlanReportDTO servicePlanReportDto = (ServicePlanReportDTO) tempObj;
				
				isReqSecSrvForm = servicePlanReportDto.getSecSrvContractPeriod() != null ? true : false;
				isConSrvForm = servicePlanReportDto.getConciergeInd() != null
						&& servicePlanReportDto.getConciergeInd()
								.equalsIgnoreCase("Y") ? true : false;
				
				BeanUtils.copyProperties(rptAppMobileServDTO, servicePlanReportDto);
				BeanUtils.copyProperties(rptSecretarialServDTO, servicePlanReportDto);

				BeanUtils.copyProperties(rptConciergeServiPadiPhoneDTO, servicePlanReportDto);
				iPhoneTradeInFormDTO.setHandsetModel(servicePlanReportDto.getHandsetDeviceDescription());
				
				if (!this.isEmpty(servicePlanReportDto.getMainServDtls())) {
					VasDetailDTO vasDetailDTO = servicePlanReportDto.getMainServDtls().get(0);
					mobileSafetyPhoneTnCDTO.setMonthlyRate(vasDetailDTO.getItemRecurrentAmt());
					mobileSafetyPhoneTnCDTO.setMonthlyRateAfter(vasDetailDTO.getItemRecurrentAmt());
				}
				
				rptProjectEagleDTO.setHandsetBrandModel(servicePlanReportDto.getHandsetDeviceDescription());
				
			} else if (tempObj instanceof PaymentDTO) {
				
				PaymentDTO paymentDto = (PaymentDTO)tempObj;
				
				if ("Y".equalsIgnoreCase(paymentDto.getThirdPartyInd())
						&& paymentDto.getCreditCardDocType() != null
						&& "C".equalsIgnoreCase(paymentDto.getPayMethodType())) {
					isReqCreditCardAuthForm = true;
					BeanUtils.copyProperties(rptCreditCardDDAuthDTO, paymentDto);
				}
				BeanUtils.copyProperties(rptAppMobileServDTO.getSectG(), paymentDto);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, paymentDto);
			
			} else if (tempObj instanceof MnpDTO) {
				
				MnpDTO mnpDto = (MnpDTO)tempObj;

				isReqMnpForm = "MNP".equalsIgnoreCase(mnpDto.getMnpType()) ? true : false;
				
				StringBuilder custReminder = new StringBuilder();
				StringBuilder custReminderChi = new StringBuilder();
				String serviceReqDateStr="";
				
				if ("N".equalsIgnoreCase(mnpDto.getMnp())
						&& (mnpDto.isFutherMnp() == false)) { // New No.
					if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
						custReminder.append("I declare that there will be no other mobile number "
								+ "ported-in to replace above 1O1O mobile number for now.");
						rptAppMobileServDTO.setCustReminder(custReminder.toString());

						custReminderChi.append("本人暫不須要轉攜其他流動電話號碼替代1O1O流動通訊之新號碼。");
						rptAppMobileServDTO.setCustReminderChi(custReminderChi.toString());
					} else {
						custReminder.append("I declare that there will be no other mobile number "
								+ "ported-in to replace above csl mobile number for now.");
						rptAppMobileServDTO.setCustReminder(custReminder.toString());

						custReminderChi.append("本人暫不須要轉攜其他流動電話號碼替代csl流動通訊之新號碼。");
						rptAppMobileServDTO.setCustReminderChi(custReminderChi.toString());
					}
					mobileSafetyPhoneDTO.setTargetCommencementDate(mnpDto.getServiceReqDate());
					mobileSafetyPhoneAFCDTO.setTargetCommencementDate(mnpDto.getServiceReqDate());
					mobileSafetyPhoneTnCDTO.setTargetCommencementDate(mnpDto.getServiceReqDate());
					serviceReqDateStr = mnpDto.getServiceReqDateStr();
				} else if ("N".equalsIgnoreCase(mnpDto.getMnp())
						&& (mnpDto.isFutherMnp() == true)) { // New No. + MNP
					if (mnpDto.getOrderId().startsWith("D")) {
						isReqMnpForm = true;
					}
					if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
						custReminder.append("I will have a mobile number ported-in to 1O1O mobile "
								+ "to replace above mobile number, "
								+ "and have already provided related documents and information.");
						rptAppMobileServDTO.setCustReminder(custReminder.toString());

						custReminderChi.append("本人須要轉攜其他流動電話號碼替代以上之1O1O流動通訊新號碼，並已提供所需之申請文件及資料。");
						rptAppMobileServDTO.setCustReminderChi(custReminderChi.toString());
					} else {
						custReminder.append("I will have a mobile number ported-in to csl mobile "
								+ "to replace above mobile number, "
								+ "and have already provided related documents and information.");
						rptAppMobileServDTO.setCustReminder(custReminder.toString());

						custReminderChi.append("本人須要轉攜其他流動電話號碼替代以上之csl流動通訊新號碼，並已提供所需之申請文件及資料。");
						rptAppMobileServDTO.setCustReminderChi(custReminderChi.toString());
					}
					mobileSafetyPhoneDTO.setTargetCommencementDate(mnpDto.getServiceReqDate());
					mobileSafetyPhoneAFCDTO.setTargetCommencementDate(mnpDto.getServiceReqDate());
					mobileSafetyPhoneTnCDTO.setTargetCommencementDate(mnpDto.getServiceReqDate());
					serviceReqDateStr = mnpDto.getServiceReqDateStr();
				} else { // MNP
					
					rptAppMobileServDTO.setCustReminder("");
					rptAppMobileServDTO.setCustReminderChi("");
					mobileSafetyPhoneDTO.setTargetCommencementDate(mnpDto.getCutoverDate());
					mobileSafetyPhoneAFCDTO.setTargetCommencementDate(mnpDto.getCutoverDate());
					mobileSafetyPhoneTnCDTO.setTargetCommencementDate(mnpDto.getCutoverDate());
					serviceReqDateStr = mnpDto.getCutoverDateStr();
				}
				
				BeanUtils.copyProperties(rptAppMobileServDTO, mnpDto);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, mnpDto);
				BeanUtils.copyProperties(rptMobPortAppDTO, mnpDto);
				BeanUtils.copyProperties(mobileSafetyPhoneDTO, mnpDto);
				BeanUtils.copyProperties(mobileSafetyPhoneAFCDTO, mnpDto);
				BeanUtils.copyProperties(mobileSafetyPhoneTnCDTO, mnpDto);
				BeanUtils.copyProperties(rptKeyInformationSheetDTO, mnpDto);
				BeanUtils.copyProperties(iPhoneTradeInFormDTO, mnpDto);

				if (!StringUtils.isEmpty(mnpDto.getMsisdn())) {
					rptChgServCustInfoRefundDTO.setNewMsisdn(mnpDto.getMsisdn());
				} else {
					rptChgServCustInfoRefundDTO.setNewMsisdn("N/A");
				}

				if (mnpDto.isFutherMnp()
						&& "N".equalsIgnoreCase(mnpDto.getMnp())) {
					if(mnpDto.getOrderId().startsWith("D")){
						rptMobPortAppDTO.setCustName(mnpDto.getFuthercustName());
						rptMobPortAppDTO.setCustNameChi(mnpDto.getFuthercustNameChi());
						rptMobPortAppDTO.setCustIdDocNum(mnpDto.getFuthercustIdDocNum());
						rptMobPortAppDTO.setMsisdn(mnpDto.getFutherMnpNumber());
						rptMobPortAppDTO.setCutoverDate(mnpDto.getFutherCutoverDate());
						rptMobPortAppDTO.setCutoverTime(mnpDto.getFutherCutoverTime());
						rptMobPortAppDTO.setMnpTicketNum(mnpDto.getFutherMnpTicketNum());
						rptMobPortAppDTO.setPrePaidSimDocWithCert(mnpDto.isFutherprePaidSimDocWithCert());
						rptMobPortAppDTO.setPrePaidSimDocWithoutCert(mnpDto.isFutherprePaidSimDocWithoutCert());
						rptMobPortAppDTO.setRno(mnpDto.getRno());
					}
					
					
					
					isChgServCustInfoRefundForm = true;
					
					rptChgServCustInfoRefundDTO.setChangeOfMobileNumInd(true);
					rptChgServCustInfoRefundDTO.setMnpMsisdn(mnpDto.getFutherMnpNumber());
					
					if (StringUtils.isNotEmpty(mnpDto.getFutherCutoverDateStr())) {
						rptChgServCustInfoRefundDTO
							.setChangeMobNumTargetCommDateStr(mnpDto.getFutherCutoverDateStr());
					} else {
						rptChgServCustInfoRefundDTO
							.setChangeMobNumTargetCommDateStr(mnpDto.getServiceReqDateStr());
					}
				}

				mnpDocNum = mnpDto.getCustIdDocNum();
				perPaidSim = mnpDto.getCustName();

				rptChgServCustInfoRefundDTO.setTransferee(perPaidSim);
				if (StringUtils.isNotEmpty(mnpDocNum)){
				rptChgServCustInfoRefundDTO.setTransfereeIdNum(maskedDocNum(mnpDocNum));
				}
				rptChgServCustInfoRefundDTO.setTransferOwnershipTargetCommDateStr(mnpDto.getCutoverDateStr());
				if (StringUtils.isNotEmpty(mnpDto.getCustIdDocNum())){
				String idDocNumMask = maskedDocNum(mnpDto.getCustIdDocNum());
				rptMobPortAppDTO.setCustIdDocNum(idDocNumMask);
				}
				
				rptProjectEagleDTO.setTargetCommencementDate(serviceReqDateStr);
				genericReportDataMap.put("serviceReqDate", serviceReqDateStr);
			} else if (tempObj instanceof OrderDTO) {
				
				OrderDTO orderDto = (OrderDTO)tempObj;
				appInDate = orderDto.getAppInDate();
				
				rptIGuardCareCashDTO.setAppDate(orderDto.getAppInDate());
				rptIGuardCareCashDTO.setOrderId(orderDto.getOrderId());
				rptKeyInformationSheetDTO.setAgreementNum(orderDto.getAgreementNum()); //20130709
				rptKeyInformationSheetDTO.setAppInDate(orderDto.getAppInDate()); 
				
				rptRptHSTradeDescDTO.setAgreementNum(orderDto.getAgreementNum());
				//rptRptHSTradeDescDTO.setAppInDate(orderDto.getAppInDate()); //20130725 
				rptRptHSTradeDescDTO.setShowSignAndDate(false);      //20130725
				rptServiceGuideDTO.setAgreementNum(orderDto.getAgreementNum());
				
				try {
					uniOptionalVasInd=vasDetailDao.getVasSelected(orderDto.getOrderId());
				} catch (DAOException e) {
					uniOptionalVasInd=false;
				}
				try {
					isCRETPlan= reportDAO.getCSubInd(orderDto.getOrderId());
				} catch (DAOException e2) {
					// TODO Auto-generated catch block
					isCRETPlan= false;
				}		
				BeanUtils.copyProperties(rptAppMobileServDTO, orderDto);
				BeanUtils.copyProperties(rptMobPortAppDTO, orderDto);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, orderDto);
				BeanUtils.copyProperties(rptSecretarialServDTO, orderDto);
				BeanUtils.copyProperties(rptConciergeServiPadiPhoneDTO, orderDto);
				BeanUtils.copyProperties(mobileSafetyPhoneDTO, orderDto);
				BeanUtils.copyProperties(mobileSafetyPhoneAFCDTO, orderDto);
				BeanUtils.copyProperties(mobileSafetyPhoneTnCDTO, orderDto);
				BeanUtils.copyProperties(rptNFCConsentDTO, orderDto);
				//BeanUtils.copyProperties(rptOctopusConsentDTO, orderDto);
								
				isIphone7TradeInBasket = orderService.isIphone7TradeInBasket(orderDto.getOrderId(),iphoneSevenTradeInList);
				isIphone8TradeInBasket = orderService.isIphone7TradeInBasket(orderDto.getOrderId(),iphoneEightTradeInList);
				iPhoneTradeInFormDTO.setImei(orderDto.getImei());
				rptProjectEagleDTO.setImeiNo(orderDto.getImei());
				
				try {
					hsTradeDescDTOs = hsTradeDescDAO.getHSTradeDescList(orderDto.getOrderId());
				} catch (DAOException e1) {
					hsTradeDescDTOs = null;
				}
				rptChgServCustInfoRefundDTO.setOrderId(orderDto.getOrderId());
				rptChgServCustInfoRefundDTO.setAppInDateStr(Utility.date2String(
						orderDto.getAppInDate(),
						BomWebPortalConstant.DATE_FORMAT));
				try {
					boolean octFlag = orderDao.getOctFlag();
					//System.out.print(" orderdao1: "+(octFlag?"Y":"N"));
					if (octFlag){//Athena 20130923
						rptAppMobileServDTO.setOctFlag("Y");
				
					}else{
						rptAppMobileServDTO.setOctFlag("N");
				
					}
				} catch (Exception e) {

				}
				/*rptAppMobileServDTO.setSimType("Normal");
				try {
					if (orderDao.hasNFCSim(orderDto.getOrderId())) {
						rptAppMobileServDTO.setSimType("NFC");
					} else if (orderDao.hasOctopusSim(orderDto.getOrderId())) {
						rptAppMobileServDTO.setSimType("Octopus");											
					}

				} catch (Exception e) {
					// TODO: handle exception
				}*/      
				try {
					if (orderDao.hasMobileSafetyPhoneOffer(orderDto.getOrderId())) {
						rptKeyInformationSheetDTO.setMspInd(true);
						rptKeyInformationSheetCustCopyDTO.setMspInd(true);
					} else {
						rptKeyInformationSheetDTO.setMspInd(false);
						rptKeyInformationSheetCustCopyDTO.setMspInd(false);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				oid=orderDto.getOrderId();// 20131031 Athena Deposit	
				
				
				if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_BRAND,"1010");
				} else if (BomWebPortalConstant.BRAND_CSL.equals(brand)) {
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_BRAND,"csl");
				}
				
				String locale = "en";
				List<VasDetailDTO> vasSelectedList = vasDetailService.getVasDetailSelectedList(locale,orderDto.getOrderId());
				if (!CollectionUtils.isEmpty(vasSelectedList)) {
					for (VasDetailDTO vas : vasSelectedList) {
						String itemId = vas.getItemId();
						boolean isTravelInsurance = vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE, itemId);
						if (isTravelInsurance) {
							VasDetailDTO travelVasDetail = getVasDetail(itemId);
							if (travelVasDetail == null) {
								logger.debug("check getVasDetail() sql, set up is invalid");
								continue;
							}
							genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_ITEM_ID, travelVasDetail.getItemId());
							genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_PROD_CD, travelVasDetail.getProdCd());
							genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_CONTRACT_PERIOD, travelVasDetail.getContractPeriod());
						}
						boolean isHelperCareInsurance = vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_HELPER_CARE, itemId);
						if (isHelperCareInsurance) {
							VasDetailDTO helperCareVasDetail = getVasDetail(itemId);
							if (helperCareVasDetail == null) {
								logger.debug("check getVasDetail() sql, set up is invalid");
								continue;
							}
							genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_PROD_CD, helperCareVasDetail.getProdCd());
							genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_CONTRACT_PERIOD, helperCareVasDetail.getContractPeriod());
						}
						boolean isProjectEagle = vasDetailService.existInSelectionGrpList(ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, itemId);
						if (isProjectEagle) {
							VasDetailDTO projectEagleVasDetail = getVasDetail(itemId);
							if (projectEagleVasDetail == null) {
								logger.debug("check getVasDetail() sql, set up is invalid");
								continue;
							}
							ItemDisplayDTO eaglePlanDetail = null;
							ItemDisplayDTO eagleMaxSwap = null;
							ItemDisplayDTO eaglePlanFee = null;
							ItemDisplayDTO eagleSwapFee = null;
							eaglePlanDetail = itemDisplayService.getItemDisplay(Integer.parseInt(itemId), "en","EG_FORM_PLANDTL");
							eagleMaxSwap = itemDisplayService.getItemDisplay(Integer.parseInt(itemId), "en","EG_FORM_MAXSWAP");
							eaglePlanFee = itemDisplayService.getItemDisplay(Integer.parseInt(itemId), "en","EG_FORM_PLANFEE");
							eagleSwapFee = itemDisplayService.getItemDisplay(Integer.parseInt(itemId), "en","EG_FORM_SWAPFEE");
							if (eaglePlanDetail != null && eagleMaxSwap != null && eaglePlanFee != null && eagleSwapFee != null ) {
								rptProjectEagleDTO.setPlanDetail(eaglePlanDetail.getHtml());
								rptProjectEagleDTO.setMaxSwap(eagleMaxSwap.getHtml());
								rptProjectEagleDTO.setPlanFee(eaglePlanFee.getHtml());
								rptProjectEagleDTO.setSwapFee(eagleSwapFee.getHtml());
							}
							
							rptProjectEagleDTO.setContractPeriod((projectEagleVasDetail.getContractPeriod() == null || ("0").equalsIgnoreCase(projectEagleVasDetail.getContractPeriod())) ? "Free to Go" : projectEagleVasDetail.getContractPeriod() + " Months");
						}
					}
				}
				
				genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_ID, orderDto.getOrderId());
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				genericReportDataMap.put("appInDate", format.format(appInDate));
				genericReportDataMap.put("msisdn", orderDto.getMsisdn());
				
				rptProjectEagleDTO.setOrderId(orderDto.getOrderId());
				rptProjectEagleDTO.setSubscriptionMobileNo(orderDto.getMsisdn());
				rptProjectEagleDTO.setHandsetReceivedDate(format.format(appInDate));
				rptProjectEagleDTO.setDateValue(format.format(appInDate));
			} else if (tempObj instanceof BomSalesUserDTO) {
				
				BomSalesUserDTO bomSalesUserDto = (BomSalesUserDTO) tempObj;
				rptIGuardCareCashDTO.setShopCd(bomSalesUserDto.getShopCd());
				rptIGuardCareCashDTO.setStaffId(bomSalesUserDto.getOrgStaffId());
				
				BeanUtils.copyProperties(rptAppMobileServDTO, bomSalesUserDto);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, bomSalesUserDto);
				
				rptChgServCustInfoRefundDTO.setStaffcodeName(
						bomSalesUserDto.getUsername()
						+ " / "
						+ bomSalesUserDto.getStaffName());
				
				genericReportDataMap.put("shopCode", bomSalesUserDto.getShopCd());
			    rptProjectEagleDTO.setShopCode("P" + bomSalesUserDto.getShopCd());
			    rptProjectEagleDTO.setChannelId(bomSalesUserDto.getChannelId());
			} else if (tempObj instanceof MobileSimInfoDTO) {
				
				MobileSimInfoDTO mobileSimInfoDto = (MobileSimInfoDTO)tempObj;
				BeanUtils.copyProperties(rptAppMobileServDTO, mobileSimInfoDto);
				BeanUtils.copyProperties(rptMobPortAppDTO, mobileSimInfoDto);
				
				if (!StringUtils.isEmpty((mobileSimInfoDto.getIccid()))) {
					rptChgServCustInfoRefundDTO.setIccidInd(true);
					rptChgServCustInfoRefundDTO.setIccid(mobileSimInfoDto.getIccid());
				}
				
				if ("Y".equalsIgnoreCase(mobileSimInfoDto.getAoInd())) {
					isAdvanceOrd = true;
				}
				
				salesName = mobileSimInfoDto.getSalesName();
				salesCode = mobileSimInfoDto.getSalesCd();
				
				if (mobileSimInfoDto.getSimCharge() > 0) {
					simOnetimeCharge = new VasOnetimeAmtDTO();
					if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
						simOnetimeCharge.setItemDesc("SIM卡費用");
					} else {
						simOnetimeCharge.setItemDesc("SIM Charge");
					}
					if (mobileSimInfoDto.getSimWaiveReason() != null && !"".equals(mobileSimInfoDto.getSimWaiveReason())) {
						simOnetimeCharge.setVasOnetimeAmt("0");
					} else {
						simOnetimeCharge.setVasOnetimeAmt("" + mobileSimInfoDto.getSimCharge());
					}
				}
				
			} else if (tempObj instanceof HSTradeDescDTO) {
				
				HSTradeDescDTO hsTradeDescDto = (HSTradeDescDTO)tempObj;
				
//				isReqTradeDescForm = hsTradeDescDto != null ? true : false;
				BeanUtils.copyProperties(rptRptHSTradeDescDTO, hsTradeDescDto);
				if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
					rptRptHSTradeDescDTO.setCompanyLogo(imageFilePath + "/"
							+ COMPANY_LOGO_FILE_CHI);
					rptRptHSTradeDescDTO.setCompanyBottomLogo(imageFilePath + "/"
							+ COMPANY_BOTTOM_LOGO_FILE_CHI);
					
				} else {
					rptRptHSTradeDescDTO.setCompanyLogo(imageFilePath + "/"
							+ COMPANY_LOGO_FILE_ENG);
					rptRptHSTradeDescDTO.setCompanyBottomLogo(imageFilePath + "/"
							+ COMPANY_BOTTOM_LOGO_FILE_ENG);
					
				}
				
				
			
			} else if (tempObj instanceof SignoffDTO) {
				
				if (distMode == OrderDTO.DisMode.E) {
				
					System.out.println("Sign OFF DTO "+((SignoffDTO) tempObj).getSignatureType());
					SignoffDTO signature = (SignoffDTO) tempObj;				
					if (SignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN 
							== ((SignoffDTO) tempObj).getSignatureType()) {
						
						custSignDTO = new SignoffDTO(); //20130709
						BeanUtils.copyProperties(custSignDTO, signature);  
						rptAppMobileServDTO.setCustSignature(signature
							.getSignatureInputStream());
						rptSecretarialServDTO.setCustSignature(signature
							.getSignatureInputStream());
						rptChgServCustInfoRefundDTO.setCustSignature(signature
							.getSignatureInputStream());
						mobileSafetyPhoneDTO.setCustSignature(signature.getSignatureInputStream());
						mobileSafetyPhoneAFCDTO.setCustSignature(signature.getSignatureInputStream());
						mobileSafetyPhoneTnCDTO.setCustSignature(signature.getSignatureInputStream());
						rptNFCConsentDTO.setCustSignature(signature.getSignatureInputStream());
						iPhoneTradeInFormDTO.setCustSignature(signature.getSignatureInputStream());
						//rptOctopusConsentDTO.setCustSignature(signature.getSignatureInputStream());
						//rptRptHSTradeDescDTO.setCustSignature(signature.getSignatureInputStream()); 
						
					    
					} else if (SignoffDTO.SignatureTypeEnum.BANK_SIGN 
							== ((SignoffDTO) tempObj).getSignatureType()) {
						
						rptAppMobileServDTO.getSectG().setCustSignatureAutoPay(signature
							.getSignatureInputStream());
						rptCreditCardDDAuthDTO.setCustSignature(signature
							.getSignatureInputStream());
						
					} else if (SignoffDTO.SignatureTypeEnum.MNP_SIGN 
							== ((SignoffDTO) tempObj).getSignatureType()) {
						
						rptMobPortAppDTO.setCustSignature(signature
							.getSignatureInputStream());
						rptChgServCustInfoRefundDTO.setTransfereeSignature(signature
							.getSignatureInputStream());
						
					} else if (SignoffDTO.SignatureTypeEnum.CONCIERGE_SIGN 
							== ((SignoffDTO) tempObj).getSignatureType()) {
						
						rptConciergeServiPadiPhoneDTO.setCustSignature(signature
							.getSignatureInputStream());
						
						if (!isAdvanceOrd) {
							rptConciergeServiPadiPhoneDTO.setCustSignature2(signature
									.getSignatureInputStream());
						}
						
					} else if (SignoffDTO.SignatureTypeEnum.TRADE_DESCRIPTION_ORDINANCE_SIGN    //20130709
							== ((SignoffDTO) tempObj).getSignatureType()) {
						
						rptKeyInformationSheetDTO.setCustSignature(signature.getSignatureInputStream());
								
					}else if (SignoffDTO.SignatureTypeEnum.IGUARD_SIGN    //20130709
							== ((SignoffDTO) tempObj).getSignatureType()){
						rptIGuardCareCashDTO.setCustSignature(signature.getSignatureInputStream());
						if (signature.isiGuard1()){
							privacyInd99992="Y";
						}
						if (signature.isiGuard2()){
							privacyInd99993="Y";
						}
						if (signature.isiGuard3()){
							privacyInd99994="Y";
						}
					} else if (SignoffDTO.SignatureTypeEnum.TRAVEL_INSURANCE_FORM_SIGN	== ((SignoffDTO) tempObj).getSignatureType()) {
						genericReportSignatureMap.put(GenericForm.TRAVEL_INSURANCE_FORM.getFormName(), signature.getSignatureInputStream());
					} else if (SignoffDTO.SignatureTypeEnum.HELPERCARE_INSURANCE_FORM_SIGN	== ((SignoffDTO) tempObj).getSignatureType()) {
						genericReportSignatureMap.put(GenericForm.HELPERCARE_INSURANCE_FORM.getFormName(), signature.getSignatureInputStream());
					} else if (SignoffDTO.SignatureTypeEnum.PROJECT_EAGLE_INSURANCE_FORM_SIGN == ((SignoffDTO) tempObj).getSignatureType()) {
						rptProjectEagleDTO.setSignature(signature.getSignatureInputStream());
					}
				}

			} else if (tempObj instanceof BasketDTO) {
				
				BasketDTO basketDto = (BasketDTO)tempObj;
				
				if (!StringUtils.isEmpty(basketDto.getModelId())) {
					rptChgServCustInfoRefundDTO.setHandsetind(true);
				}
				
				if (StringUtils.isNotEmpty(basketDto.getHsPosItemCd()) && appInDate != null) {
					BigDecimal hsPrice;
					
					try {
						hsPrice = iGuardDAO.getNsPrice(basketDto.getHsPosItemCd(), appInDate);
						rptProjectEagleDTO.setHandsetRetailPrice("HK$" + String.valueOf(hsPrice));
					} catch (DAOException de) {
						logger.error("Exception caught in getNsPrice()", de);
						throw new AppRuntimeException(de);
					} catch (NullPointerException ne) {
						logger.error("Exception caught in getNsPrice()", ne);
						throw new AppRuntimeException(ne);
					} catch (Exception e) {
						logger.error("Exception caught in getNsPrice()", e);
						throw new AppRuntimeException(e);
					}
					
				}
				
				rptAppMobileServDTO.setContractPeriod(basketDto.getContractPeriod());
				mobileSafetyPhoneTnCDTO.setCommitmentPeriod(basketDto.getContractPeriod());
				List<String> universityPlanBasketIdList = null;
				try {
					universityPlanBasketIdList = orderDao.getCodeIdList("UNI_STD_PLAN");
				} catch (DAOException e) {
					logger.error("Exception caught in getCodeIdList in reportService", e);

				}
				if (universityPlanBasketIdList!=null){
					for (int j = 0;j<universityPlanBasketIdList.size();j++){
						if (basketDto.getBasketId().equals(universityPlanBasketIdList.get(j))){
							isUniversityPlan=true;
						}
					}
				}
				
				boolean isStaffOffer = false;
				try {
					isStaffOffer = orderDao.isStaffOffer(basketDto.getBasketId());
				} catch (DAOException e1) {
					logger.error("Exception caught in isStaffOffer", e1);
				}
				StringBuilder staffReminder = new StringBuilder();
				StringBuilder staffReminderChi = new StringBuilder();
				if (isStaffOffer) {
					staffReminder.append("I declare that I have read and understand the PCCW Company’s policy about staff offer of mobile service plan and I am eligible for the staff offer of mobile service plans.");
					rptAppMobileServDTO.setStaffReminder(staffReminder.toString());

					staffReminderChi.append("本人為合資格員工已閱畢及了解電訊盈科員工流動通訊服務計劃優惠之政策。");
					rptAppMobileServDTO.setStaffReminderChi(staffReminderChi.toString());
				}else{
					staffReminder=null;
					staffReminderChi=null;
				}
			} else if (tempObj instanceof SupportDocUI) {
				
				SupportDocUI supportDocUi = (SupportDocUI)tempObj;
				
				distMode = supportDocUi.getDisMode();
				
				List<GenerateSpringboardForm> docAttList
					= supportDocUi.getGenerateSpringboardForms();
				
				if (docAttList != null) {
					for (GenerateSpringboardForm each : docAttList) {
						switch (each.getSpringboardForm()) {
							case TRADE_DESCRIPTIONS_FOR_ELECTIONIC_PRODUCTS:
							{
								if (each.isRequired()) {
									rptAppMobileServDTO.setDocAttTradeDesc(true);
								}
								break;
							}
							case PCCW_CONCIERGE_SERVICE_FORM:
							{
								if (each.isRequired()) {
									rptAppMobileServDTO.setDocAttConci(true);
								}
								break;
							}
							case APPLICATION_IN_RESPECT_OF_MOBILE_SECRETARIAL_SERVICE:
							{
								if (each.isRequired()) {
									rptAppMobileServDTO.setDocAttSecSrv(true);
								}
								break;
							}
							case MNP_APPLICATION_FORM:
							{
								if (each.isRequired()) {
									rptAppMobileServDTO.setDocAttMNP(true);
								}
								break;
							}
							case CHANGE_OF_SERVICE_FORM:
							{
								if (each.isRequired()) {
									rptAppMobileServDTO.setDocAttChgOfSrv(true);
								}
								break;
							}
							case THRID_PARTY_AUTOPAY_FORM:
							{
								if (each.isRequired()) {
									rptAppMobileServDTO.setDocAttThirdParty(true);
								}
								break;
							}
						}
					}
				}
				
				List<AllOrdDocAssgnDTO> pfList
					= supportDocUi.getAllOrdDocAssgnDTOs();
				
				if (pfList != null) {
					for (AllOrdDocAssgnDTO each : pfList) {
						switch (each.getDocType()) {
							case M002:
							{
								if (StringUtils.isEmpty(each.getWaiveReason())
										&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
									rptAppMobileServDTO.setPfHKIDPass(true);
								}
								break;
							}
							case M003:
							{
								if (StringUtils.isEmpty(each.getWaiveReason())
										&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
									rptAppMobileServDTO.setPfAddrPf(true);
								}
								break;
							}
							case M004:
							{
								if (StringUtils.isEmpty(each.getWaiveReason())
										&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
									rptAppMobileServDTO.setPfBRCopy(true);
								}
								break;
							}
							case M005:
							{
								if (StringUtils.isEmpty(each.getWaiveReason())
										&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
									rptAppMobileServDTO.setPfBRNameCard(true);
								}
								break;
							}
							case M006:
							{
								if (StringUtils.isEmpty(each.getWaiveReason())
										&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
									rptAppMobileServDTO.setPfEmpCont(true);
								}
								break;
							}
							case M007:
							{
								if (StringUtils.isEmpty(each.getWaiveReason())
										&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
									rptAppMobileServDTO.setPfPreparidSIM(true);
								}
								break;
							}
							case M008:
							{
								if (StringUtils.isEmpty(each.getWaiveReason())
										&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
									rptAppMobileServDTO.setPfCCCopy(true);
								}
								break;
							}
							case M009:
							{
								if (StringUtils.isEmpty(each.getWaiveReason())
										&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
									rptAppMobileServDTO.setPfMNPDoc(true);
								}
								break;
							}
						}
					}
				}
			} else if (tempObj instanceof IGuardDTO) {
				BeanUtils.copyProperties(iGuardDTO, (IGuardDTO)tempObj);
				
				if (BomWebPortalConstant.BRAND_1010.equals(brand)){
					iGuardDTO.setBrand1010Ind("Y");
				}else{
					iGuardDTO.setBrand1010Ind("N");
				}
				iGuardDTO.setDob(iGuardService.getIGuardUADDateOfBirth(appInDate, iGuardDTO.getDob()));
			} else if (tempObj instanceof VasMrtSelectionDTO) {
				VasMrtSelectionDTO vasMrtSelectionDTO = (VasMrtSelectionDTO)tempObj;
				if ("EX06".equalsIgnoreCase(vasMrtSelectionDTO.getFuncId()) && "Y".equalsIgnoreCase(vasMrtSelectionDTO.getChinaInd())) {
					rptAppMobileServDTO.setUnicomMsisdn(vasMrtSelectionDTO.getMsisdn());
				} else if ("EX07".equalsIgnoreCase(vasMrtSelectionDTO.getFuncId())) {
					rptAppMobileServDTO.setSecSrvNum(vasMrtSelectionDTO.getSecSrvNum());
				}
			}
			 else if (tempObj instanceof List<?>) {
				 //List<?> list = (List<?>) tempObj;
				 String ind = "N";
				 String componentInd="N";
				 for (Object o : (List<?>) tempObj){
					if (o instanceof MultiSimInfoDTO){
						 ind = "Y";
						 break;
					 }
					if (o instanceof ComponentDTO){
						componentInd="Y";
						break;
					}
				 }
					if (ind.equalsIgnoreCase("Y")){
					 //MultiSim Athena 20140128 
							List<RptMultiSimDTO> rptMultiSimDTOList= new ArrayList<RptMultiSimDTO>();
					 		List<MultiSimInfoDTO> multiSimInfoDTOs= (List<MultiSimInfoDTO>) tempObj;
					 		String locale="zh_HK";
					 		if ("en".equals(rptAppMobileServDTO.getLocale())) {
					 			locale= "en";
					 		}

							if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
								
								for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
									List<MultiSimInfoMemberDTO> multiSimInfoMemberDTOs=multiSimInfoDTO.getMembers();
									
									if (multiSimInfoMemberDTOs != null && multiSimInfoMemberDTOs.size() > 0) {
										for(MultiSimInfoMemberDTO multiSimInfoMemberDTO: multiSimInfoMemberDTOs){
											if (!"MUPS99".equals(multiSimInfoMemberDTO.getMemberOrderType())){
												RptMultiSimDTO rptMultiSimDTO= new RptMultiSimDTO();
												rptMultiSimDTO.setMemberNum(multiSimInfoMemberDTO.getMemberNum());
												rptMultiSimDTO.setMultiMsisdn(multiSimInfoMemberDTO.getMsisdn());
												rptMultiSimDTO.setMultiSimIccid(multiSimInfoMemberDTO.getSim().getIccid());
												
												List<RptVasDetailDTO> multiSimMainServDtls=vasDetailService.getReportUseMultiSimRPHSRPList(locale,multiSimInfoMemberDTO.getBasketId(),multiSimInfoMemberDTO.getParentOrderId(),multiSimInfoMemberDTO.getMemberNum());
												List<RptVasDetailDTO> multiSimvasDtls=vasDetailService.getReportUseMultiSimVasDetailtList(locale,multiSimInfoMemberDTO.getBasketId(),multiSimInfoMemberDTO.getParentOrderId(),multiSimInfoMemberDTO.getMemberNum());
												List<RptVasDetailDTO> multiSimvasOptionalDtls=vasDetailService.getReportUseMultiSimVasOptionalDetailtList(locale,multiSimInfoMemberDTO.getBasketId(),multiSimInfoMemberDTO.getParentOrderId(),multiSimInfoMemberDTO.getMemberNum());
												rptMultiSimDTO.setMultiSimMainServDtls(multiSimMainServDtls);
												rptMultiSimDTO.setMultiSimvasDtls(multiSimvasDtls);
												rptMultiSimDTO.setMultiSimvasOptionalDtls(multiSimvasOptionalDtls);
												
												String sim = multiSimInfoMemberDTO.getSim().getItemCode();
												try {
													rptMultiSimDTO.setMultiSimType(orderDao.getSimType(sim));
												} catch (DAOException e1) {
													logger.error("Exception caught in mapReportData", e1);
												}
												rptMultiSimDTOList.add(rptMultiSimDTO);
												if ("A".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpInd()) || "MUPS05".equalsIgnoreCase(multiSimInfoMemberDTO.getMemberOrderType())){
													RptMobPortAppDTO rptMultiSimMobPortAppDTO = new RptMobPortAppDTO();
													BeanUtils.copyProperties(rptMultiSimMobPortAppDTO, rptMobPortAppDTO);
													rptMultiSimMobPortAppDTO.setAgreementNum(multiSimInfoMemberDTO.getMemberOrderId());
													rptMultiSimMobPortAppDTO.setCustName(multiSimInfoMemberDTO.getMnpCustName());
													//rptMultiSimMobPortAppDTO.setCustNameChi("");
													rptMultiSimMobPortAppDTO.setMsisdn(multiSimInfoMemberDTO.getMnpNumber());
													if (StringUtils.isNotEmpty(multiSimInfoMemberDTO.getMnpDocNo())){
													rptMultiSimMobPortAppDTO.setCustIdDocNum(maskedDocNum(multiSimInfoMemberDTO.getMnpDocNo()));
													}
													rptMultiSimMobPortAppDTO.setDno(multiSimInfoMemberDTO.getMnpDno());
													rptMultiSimMobPortAppDTO.setCutoverDate(Utility.string2Date(multiSimInfoMemberDTO.getMnpCutOverDate()));
													rptMultiSimMobPortAppDTO.setCutoverTime(multiSimInfoMemberDTO.getMnpCutOverTime());
													rptMultiSimMobPortAppDTO.setMnpTicketNum(multiSimInfoMemberDTO.getMnpTicketNum());
													rptMultiSimMobPortAppDTO.setPrePaidSimDocWithCert("Y".equals(multiSimInfoMemberDTO.getPrePaidSimDocInd()));
													rptMultiSimMobPortAppDTO.setPrePaidSimDocWithoutCert("N".equals(multiSimInfoMemberDTO.getPrePaidSimDocInd()));
													if (distMode == OrderDTO.DisMode.E) {
														if (custSignDTO != null && custSignDTO.getSignatureInputStream()!=null){
															rptMultiSimMobPortAppDTO.setCustSignature(custSignDTO.getSignatureInputStream());
														
														}
													}
													multiSimMnpList.add(rptMultiSimMobPortAppDTO);
													
													
													if (distMode == OrderDTO.DisMode.E) {
														if (multiSimInfoMemberDTO.getSignDTO() != null) {
															rptMultiSimMobPortAppDTO.setCustSignature(multiSimInfoMemberDTO.getSignDTO().getSignatureInputStream());
														}
													}
													MobCcsChgServCustInfoRefundDTO rptMultiSimChgServCustInfoRefundDTO = new MobCcsChgServCustInfoRefundDTO(); 
													MobCcsChgServCustInfoRefundDTO rptMultiSimChgServCustInfoRefundCustCopyDTO = new MobCcsChgServCustInfoRefundDTO(); 				
													BeanUtils.copyProperties(rptMultiSimChgServCustInfoRefundDTO, rptChgServCustInfoRefundDTO);
													
													rptMultiSimChgServCustInfoRefundDTO.setOrderId(multiSimInfoMemberDTO.getMemberOrderId());
													rptMultiSimChgServCustInfoRefundDTO.setNewMsisdn(multiSimInfoMemberDTO.getMsisdn());
													rptMultiSimChgServCustInfoRefundDTO.setMnpMsisdn(multiSimInfoMemberDTO.getMnpNumber());
													rptMultiSimChgServCustInfoRefundDTO.setChangeMobNumTargetCommDateStr(multiSimInfoMemberDTO.getMnpCutOverDate());
													rptMultiSimChgServCustInfoRefundDTO.setChangeOfMobileNumInd(true);
													rptMultiSimChgServCustInfoRefundDTO.setStaffcodeName(salesCode + " / " + salesName);
													if (distMode == OrderDTO.DisMode.E) {
														if (custSignDTO != null && custSignDTO.getSignatureInputStream()!=null){
															rptMultiSimChgServCustInfoRefundDTO.setCustSignature(custSignDTO.getSignatureInputStream());
															rptMultiSimChgServCustInfoRefundDTO.setTransfereeSignature(custSignDTO.getSignatureInputStream());
														}
													}
													
													if (!"Prepaid Sim".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpCustName())) {
														if (StringUtils.isNotBlank(custDocNum) && StringUtils.isNotBlank(multiSimInfoMemberDTO.getMnpDocNo())) {
															if (!custDocNum.equalsIgnoreCase(multiSimInfoMemberDTO.getMnpDocNo())) {
																rptMultiSimChgServCustInfoRefundDTO.setOwnershipFormInd(true);
																rptMultiSimChgServCustInfoRefundDTO.setIccidInd(true);
																rptMultiSimChgServCustInfoRefundDTO.setTransferee(multiSimInfoMemberDTO.getMnpCustName());
																if (StringUtils.isNotEmpty(multiSimInfoMemberDTO.getMnpDocNo())){
																rptMultiSimChgServCustInfoRefundDTO.setTransfereeIdNum(maskedDocNum(multiSimInfoMemberDTO.getMnpDocNo()));
																}
																rptMultiSimChgServCustInfoRefundDTO.setIccid(multiSimInfoMemberDTO.getSim().getIccid());
																rptMultiSimChgServCustInfoRefundDTO.setTransferOwnershipTargetCommDateStr(multiSimInfoMemberDTO.getMnpCutOverDate());
																if (distMode == OrderDTO.DisMode.E) {
																	if (multiSimInfoMemberDTO.getSignDTO() != null) {
																		rptMultiSimChgServCustInfoRefundDTO.setTransfereeSignature(multiSimInfoMemberDTO.getSignDTO().getSignatureInputStream());
																	}
																}
															}
														}
													}
													multiSimChgServCustInfoRefundList.add(rptMultiSimChgServCustInfoRefundDTO);
													
													BeanUtils.copyProperties(rptMultiSimChgServCustInfoRefundCustCopyDTO, rptMultiSimChgServCustInfoRefundDTO);
													rptMultiSimChgServCustInfoRefundCustCopyDTO.setCustomerCopyInd("Y");
													multiSimChgServCustInfoRefundCustCopyList.add(rptMultiSimChgServCustInfoRefundCustCopyDTO); 
													
												}else if ("Y".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpInd())){
													RptMobPortAppDTO rptMultiSimMobPortAppDTO = new RptMobPortAppDTO();
													BeanUtils.copyProperties(rptMultiSimMobPortAppDTO, rptMobPortAppDTO);
													rptMultiSimMobPortAppDTO.setAgreementNum(multiSimInfoMemberDTO.getMemberOrderId());
													rptMultiSimMobPortAppDTO.setCustName(multiSimInfoMemberDTO.getMnpCustName());
													//rptMultiSimMobPortAppDTO.setCustNameChi("");
													rptMultiSimMobPortAppDTO.setMsisdn(multiSimInfoMemberDTO.getMnpNumber());
													if (StringUtils.isNotEmpty(multiSimInfoMemberDTO.getMnpDocNo())){
													rptMultiSimMobPortAppDTO.setCustIdDocNum(maskedDocNum(multiSimInfoMemberDTO.getMnpDocNo()));
													}
													rptMultiSimMobPortAppDTO.setDno(multiSimInfoMemberDTO.getMnpDno());
													rptMultiSimMobPortAppDTO.setCutoverDate(Utility.string2Date(multiSimInfoMemberDTO.getMnpCutOverDate()));
													rptMultiSimMobPortAppDTO.setCutoverTime(multiSimInfoMemberDTO.getMnpCutOverTime());
													rptMultiSimMobPortAppDTO.setMnpTicketNum(multiSimInfoMemberDTO.getMnpTicketNum());
											//		rptMultiSimMobPortAppDTO.setPrePaidSimDocWithCert("Y".equals(multiSimInfoMemberDTO.getPrePaidSimDocInd()));
											//		rptMultiSimMobPortAppDTO.setPrePaidSimDocWithoutCert("N".equals(multiSimInfoMemberDTO.getPrePaidSimDocInd()));
													if (distMode == OrderDTO.DisMode.E) {
														if (custSignDTO != null && custSignDTO.getSignatureInputStream()!=null){
															rptMultiSimMobPortAppDTO.setCustSignature(custSignDTO.getSignatureInputStream());
														
														}
													}
													multiSimMnpList.add(rptMultiSimMobPortAppDTO);
													
													
													if (distMode == OrderDTO.DisMode.E) {
														if (multiSimInfoMemberDTO.getSignDTO() != null) {
															rptMultiSimMobPortAppDTO.setCustSignature(multiSimInfoMemberDTO.getSignDTO().getSignatureInputStream());
														}
													}
													MobCcsChgServCustInfoRefundDTO rptMultiSimChgServCustInfoRefundDTO = new MobCcsChgServCustInfoRefundDTO(); 
													MobCcsChgServCustInfoRefundDTO rptMultiSimChgServCustInfoRefundCustCopyDTO = new MobCcsChgServCustInfoRefundDTO(); 				
													BeanUtils.copyProperties(rptMultiSimChgServCustInfoRefundDTO, rptChgServCustInfoRefundDTO);
													
													rptMultiSimChgServCustInfoRefundDTO.setOrderId(multiSimInfoMemberDTO.getMemberOrderId());
													rptMultiSimChgServCustInfoRefundDTO.setNewMsisdn(multiSimInfoMemberDTO.getMsisdn());
													rptMultiSimChgServCustInfoRefundDTO.setMnpMsisdn(multiSimInfoMemberDTO.getMnpNumber());
													rptMultiSimChgServCustInfoRefundDTO.setChangeMobNumTargetCommDateStr(multiSimInfoMemberDTO.getMnpCutOverDate());
													rptMultiSimChgServCustInfoRefundDTO.setChangeOfMobileNumInd(true);
													rptMultiSimChgServCustInfoRefundDTO.setStaffcodeName(salesCode + " / " + salesName);
													if (distMode == OrderDTO.DisMode.E) {
														if (custSignDTO != null && custSignDTO.getSignatureInputStream()!=null){
															rptMultiSimChgServCustInfoRefundDTO.setCustSignature(custSignDTO.getSignatureInputStream());
															rptMultiSimChgServCustInfoRefundDTO.setTransfereeSignature(custSignDTO.getSignatureInputStream());
														}
													}
													
													if (!"Prepaid Sim".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpCustName())) {
														if (StringUtils.isNotBlank(custDocNum) && StringUtils.isNotBlank(multiSimInfoMemberDTO.getMnpDocNo())) {
															if (!custDocNum.equalsIgnoreCase(multiSimInfoMemberDTO.getMnpDocNo())) {
																rptMultiSimChgServCustInfoRefundDTO.setOwnershipFormInd(true);
																rptMultiSimChgServCustInfoRefundDTO.setIccidInd(true);
																rptMultiSimChgServCustInfoRefundDTO.setTransferee(multiSimInfoMemberDTO.getMnpCustName());
																if (StringUtils.isNotEmpty(multiSimInfoMemberDTO.getMnpDocNo())){
																rptMultiSimChgServCustInfoRefundDTO.setTransfereeIdNum(maskedDocNum(multiSimInfoMemberDTO.getMnpDocNo()));
																}
																rptMultiSimChgServCustInfoRefundDTO.setIccid(multiSimInfoMemberDTO.getSim().getIccid());
																rptMultiSimChgServCustInfoRefundDTO.setTransferOwnershipTargetCommDateStr(multiSimInfoMemberDTO.getMnpCutOverDate());
																if (distMode == OrderDTO.DisMode.E) {
																	if (multiSimInfoMemberDTO.getSignDTO() != null) {
																		rptMultiSimChgServCustInfoRefundDTO.setTransfereeSignature(multiSimInfoMemberDTO.getSignDTO().getSignatureInputStream());
																	}
																}
															}
														}
													}
													multiSimChgServCustInfoRefundList.add(rptMultiSimChgServCustInfoRefundDTO);
													
													BeanUtils.copyProperties(rptMultiSimChgServCustInfoRefundCustCopyDTO, rptMultiSimChgServCustInfoRefundDTO);
													rptMultiSimChgServCustInfoRefundCustCopyDTO.setCustomerCopyInd("Y");
													multiSimChgServCustInfoRefundCustCopyList.add(rptMultiSimChgServCustInfoRefundCustCopyDTO); 
													
												}
	
											}

										}
									}
								}								
									
										rptAppMobileServDTO.setMultiSimList(rptMultiSimDTOList);
									} else {
									
										rptAppMobileServDTO.setMultiSimList(null);
									}
							}else if (componentInd.equalsIgnoreCase("Y")){
								List<ComponentDTO> componentList= (List<ComponentDTO>) tempObj;
								if(!CollectionUtils.isEmpty(componentList)){
									if(getCodeId(ITEM_TRAVEL_INSURANCE_CODE_TYPE)!=null){
										String travelStaffId=getAttbValue(componentList, getCodeId(ITEM_TRAVEL_INSURANCE_CODE_TYPE));
										if(StringUtils.isNotBlank(travelStaffId)){
											genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_STAFF_ID, travelStaffId);
										}
									}
									if(getCodeId(ITEM_HELPERCARE_INSURANCE_CODE_TYPE)!=null){
										String helperCareStaffId=getAttbValue(componentList, getCodeId(ITEM_HELPERCARE_INSURANCE_CODE_TYPE));
										if(StringUtils.isNotBlank(helperCareStaffId)){
											genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_STAFF_ID, helperCareStaffId);
										}
									}
									if(getCodeId(ITEM_TRAVEL_INSURANCE_DM_CODE_TYPE)!=null){
										String travelDMInd=getAttbValue(componentList, getCodeId(ITEM_TRAVEL_INSURANCE_DM_CODE_TYPE));
										if(StringUtils.isNotBlank(travelDMInd) && StringUtils.equalsIgnoreCase(travelDMInd,"Y")){
											genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DM_IND, "X");
										}
										if(StringUtils.isNotBlank(travelDMInd) && StringUtils.equalsIgnoreCase(travelDMInd,"N")){
											genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DM_IND, " ");
										}
									}
									if(getCodeId(ITEM_HELPERCARE_INSURANCE_DM_CODE_TYPE)!=null){
										String helperCareDMInd=getAttbValue(componentList, getCodeId(ITEM_HELPERCARE_INSURANCE_DM_CODE_TYPE));
										if(StringUtils.isNotBlank(helperCareDMInd) && StringUtils.equalsIgnoreCase(helperCareDMInd,"Y")){
											genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DM_IND, "X");
										}
										if(StringUtils.isNotBlank(helperCareDMInd) && StringUtils.equalsIgnoreCase(helperCareDMInd,"N")){
											genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DM_IND, " ");
										}
									}
									if(getCodeId(ITEM_PROJECT_EAGLE_INSURANCE_DM_CODE_TYPE)!=null){
										String projectEagleDMInd=getAttbValue(componentList, getCodeId(ITEM_PROJECT_EAGLE_INSURANCE_DM_CODE_TYPE));
										if(StringUtils.isNotBlank(projectEagleDMInd) && StringUtils.equalsIgnoreCase(projectEagleDMInd,"Y")){
											rptProjectEagleDTO.setDirectMarketingInd("X");
										}
										if(StringUtils.isNotBlank(projectEagleDMInd) && StringUtils.equalsIgnoreCase(projectEagleDMInd,"N")){
											rptProjectEagleDTO.setDirectMarketingInd(" ");
										}
									}
								}	
							} 
						 
					 
				 }
				
		}
		
		/*
		 * "5" indicates it is a NE basket
		 */
		if ("5".equals(rptAppMobileServDTO.getBasketType())){
			isNEForm = true;
		}

		/*
		 * Step 6. Additional & Miscellaneous charges
		 * 			content set in XXXConstant.xml
		 */
		// add Eliot 20110801
		ArrayList<AdditionalChargeDTO> aChargeList = new ArrayList<AdditionalChargeDTO>();
		ArrayList<MiscellaneousChargeDTO> mChargeList = new ArrayList<MiscellaneousChargeDTO>();
		String configFiles = "";
		if (isNEForm){
			configFiles = "NEreportConstant.xml";
		}else{
			configFiles = "reportConstant.xml";
		}
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(
				configFiles);
		
		if (isNEForm){
			if ("en".equals(rptAppMobileServDTO.getLocale())) {
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng1"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng2"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng3"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng4"));		

				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng5"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng6"));		

				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng1"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng2"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng3"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng4"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng5"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng6"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng7"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng8"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng9"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng10"));
			} else if ("zh_HK".equals(rptAppMobileServDTO.getLocale())) {
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi1"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi2"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi3"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi4"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi5"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi6"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi7"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi8"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi9"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi10"));
				
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi1"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi2"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi3"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi4"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi5"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi6"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi7"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi8"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi9"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi10"));
			}
		}else{
			if ("en".equals(rptAppMobileServDTO.getLocale())) {
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng1"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng2"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng3"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng4"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng5"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng6"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng7"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng8"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeEng9"));

				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng1"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng2"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng3"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng4"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng5"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng6"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng7"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng8"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng9"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeEng10"));
			} else if ("zh_HK".equals(rptAppMobileServDTO.getLocale())) {
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi1"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi2"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi3"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi4"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi5"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi6"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi7"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi8"));
				aChargeList.add((AdditionalChargeDTO) appCtx
						.getBean("additionalChargeChi9"));

				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi1"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi2"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi3"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi4"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi5"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi6"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi7"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi8"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi9"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx
						.getBean("miscellaneousChargeChi10"));
			}
		}
		rptAppMobileServDTO.setMiscellaneousChargeDtls(mChargeList);
		rptAppMobileServDTO.setAdditionalChargeDtls(aChargeList);
		
		// Bill Media Option
		if ("zh_HK".equals(rptAppMobileServDTO.getLocale())){
			rptAppMobileServDTO.setBillMedia("短訊賬單 (免費) "+billMediaStringZh);

		}else{
			rptAppMobileServDTO.setBillMedia("SMS Bill (Free)" +billMediaString);
		}
		
		//Paper bill Athena 20130925 end
		// 20131031 Athena Deposit start
		if (oid != null){
			if ("en".equals(rptAppMobileServDTO.getLocale())) {
				List<DepositDTO> depositDTOList = depositService.findDepositDTOByOrderId(oid,"en");
				if (depositDTOList!= null && !depositDTOList.isEmpty()){
					List<DepositDTO> depositDTOList2= new ArrayList<DepositDTO>();
					for(DepositDTO mbm: depositDTOList) {
							if (!"Y".equals(mbm.getWaiveInd())) {
								depositDTOList2.add(mbm);
							}
					}
					if (depositDTOList2!= null && !depositDTOList2.isEmpty()){
						rptAppMobileServDTO.setDepositList(depositDTOList2);
					}
				}
			}
			else if ("zh_HK".equals(rptAppMobileServDTO.getLocale())) {
				List<DepositDTO> depositDTOList = depositService.findDepositDTOByOrderId(oid,"zh_HK");
				if (depositDTOList!= null && !depositDTOList.isEmpty()){
					List<DepositDTO> depositDTOList2= new ArrayList<DepositDTO>();
					for(DepositDTO mbm: depositDTOList) {
							if (!"Y".equals(mbm.getWaiveInd())) {
								depositDTOList2.add(mbm);
							}
					}
					if (depositDTOList2!= null && !depositDTOList2.isEmpty()){
						rptAppMobileServDTO.setDepositList(depositDTOList2);
					}
				}
			}
			
			BigDecimal depositTotal= depositService.getDepositAmountForOrder(oid);
			if (depositTotal!=null){
				rptAppMobileServDTO.setDepositTotal(depositTotal.toString());
			}else{
				rptAppMobileServDTO.setDepositTotal("0");
			}
		}
		// 20131031 Athena Deposit end
		
		// 20140522 Athena add mob cust notice csl start
		List<CodeLkupDTO> custNotice=codeLkupService.getCodeLkupDTOALL("MOB_CUST_NOTICE");
		if (custNotice!=null && custNotice.size()>=1){
			if (custNotice.get(0).getCodeId()!=null && "Y".equalsIgnoreCase(custNotice.get(0).getCodeId())){
				rptAppMobileServDTO.setMobCustNotice(true);
			}
		}
		// 20140522 Athena add mob cust notice csl end
		if (isConSrvForm)
			rptAppMobileServDTO.setIsConSrv("Y");
		else
			rptAppMobileServDTO.setIsConSrv("N");

		rptAppMobileServDTO.setCustomerCopyInd("N");
		
		if (simOnetimeCharge != null) {
			rptAppMobileServDTO.getVasOnetimeAmtList().add(simOnetimeCharge);
			double vasOnetimeAmtFee = Double.parseDouble(rptAppMobileServDTO.getVasOnetimeAmtFee());
			double simOnetimeAmtFee = Double.parseDouble(simOnetimeCharge.getVasOnetimeAmt());
			rptAppMobileServDTO.setVasOnetimeAmtFee("" + (vasOnetimeAmtFee + simOnetimeAmtFee));
		}

		try {
			List<MultipleMrtSimDTO> multiSimList 
				= orderDao.getMultipleMrtSimDTOList(rptAppMobileServDTO.getAgreementNum());
			if (multiSimList != null && multiSimList.size() > 0) {
				rptAppMobileServDTO.setHaveMultiSim(true);
				rptAppMobileServDTO.setMultipleMrtSimList(multiSimList);
			} else {
				rptAppMobileServDTO.setHaveMultiSim(false);
				rptAppMobileServDTO.setMultipleMrtSimList(null);
			}
		} catch (DAOException e1) {
			logger.debug("DAOException @ReportServiceImp: getMultipleMrtSimDTOList");
			e1.printStackTrace();
		} 
		
		if (!"Prepaid Sim".equalsIgnoreCase(perPaidSim)) {
			if (StringUtils.isNotBlank(custDocNum) && StringUtils.isNotBlank(mnpDocNum)) {
				if (!custDocNum.equalsIgnoreCase(mnpDocNum)) {
					isChgServCustInfoRefundForm = true;
					rptChgServCustInfoRefundDTO.setOwnershipFormInd(true);
				}
			}
		}
		
		// sales info should use mobilesiminfo page
		rptAppMobileServDTO.setSalesName(salesName);
		rptAppMobileServDTO.setSalesCd(salesCode);
		
		rptChgServCustInfoRefundDTO.setStaffcodeName(salesCode + " / " + salesName);
		
		rptCreditCardDDAuthDTO.setUsername(salesCode);
		String creditCardDocNumMask=null;
		if (StringUtils.isNotEmpty(rptCreditCardDDAuthDTO.getCreditCardDocNum())){
			creditCardDocNumMask = maskedDocNum(rptCreditCardDDAuthDTO.getCreditCardDocNum());
		}
		rptCreditCardDDAuthDTO.setCreditCardDocNum(creditCardDocNumMask);
		rptMobPortAppDTO.setSalesCd(salesCode);
		rptSecretarialServDTO.setSalesCd(salesCode);
		
		// add "P" in front of shop code
		if (StringUtils.isNotEmpty(rptAppMobileServDTO.getShopCd())) {
			if (rptAppMobileServDTO.getShopCd().length() == 3) {
				rptAppMobileServDTO.setShopCd("P" + rptAppMobileServDTO.getShopCd());
			}
		}
		if (StringUtils.isNotEmpty(rptCreditCardDDAuthDTO.getShopCd())) {
			if (rptCreditCardDDAuthDTO.getShopCd().length() == 3) {
				rptCreditCardDDAuthDTO.setShopCd("P" + rptCreditCardDDAuthDTO.getShopCd());
			}
		}
		if (StringUtils.isNotEmpty(rptMobPortAppDTO.getShopCd())) {
			if (rptMobPortAppDTO.getShopCd().length() == 3) {
				rptMobPortAppDTO.setShopCd("P" + rptMobPortAppDTO.getShopCd());
			}
		}
		if (StringUtils.isNotEmpty(rptSecretarialServDTO.getShopCode())) {
			if (rptSecretarialServDTO.getShopCode().length() == 3) {
				rptSecretarialServDTO.setShopCode("P" + rptSecretarialServDTO.getShopCode());
			}
		}
		
		fillSSStatements(rptAppMobileServDTO, isNEForm);
		
		/*
		 * Step 7. Add dto into list
		 * 			list map into key
		 * 			key = jasper name
		 */
		
		
		//Key Information Form Cust Copy 20130709
		keyInformationSheetList.add(rptKeyInformationSheetDTO);
		
		
		BeanUtils.copyProperties(rptKeyInformationSheetCustCopyDTO,
				rptKeyInformationSheetDTO);

		rptKeyInformationSheetCustCopyDTO.setCustomerCopyInd("Y");
		keyInformationSheetCustCopyList.add(rptKeyInformationSheetCustCopyDTO);
		
		String key="";  
				
		
		key = RptKeyInformationSheetDTO.JASPER_TEMPLATE; 
				
		
		
		if (distMode == OrderDTO.DisMode.P && this.isEmpty(forms)) {
				map.put(key, new JasperReport(key, keyInformationSheetList));
		}
		if (this.isEmpty(forms)) {
				map.put(key + CUST_COPY, new JasperReport(key, keyInformationSheetCustCopyList));
		}
			
		if (isNEForm) {
			key = RptAppMobileServDTO.JASPER_TEMPLATE_NE
				+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		} else if (isStudentPlan) {
			rptAppMobileServDTO.setSectionH(this.getReportContentHtml("CY001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "sectionH"));
			
			key = RptAppMobileServDTO.JASPER_TEMPLATE_CY
					+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		}  else if (isUniversityPlan){
			rptAppMobileServDTO.setSectionH(this.getReportContentHtml("UNI001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "sectionH"));
			key = RptAppMobileServDTO.JASPER_TEMPLATE_UNI
					+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		} else if (isCRETPlan) {
			if (rptAppMobileServDTO.getSectG()!=null){
				if (StringUtils.isBlank(rptAppMobileServDTO.getSectG().getPayMethodType())){
					rptAppMobileServDTO.getSectG().setPayMethodType("M");
				}
			}
			//rptAppMobileServDTO.setSectionH(this.getReportContentHtml("CY001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "sectionH"));
			key = RptAppMobileServDTO.JASPER_TEMPLATE_RET	+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		}else{
			key = RptAppMobileServDTO.JASPER_TEMPLATE
				+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		}
		
		ssList.add(rptAppMobileServDTO);
		rptAppMobileServDTO.setOrderType("ACT");//set order type to ACT
		// S&S Customer Copy
		BeanUtils.copyProperties(rptAppMobileServCustCopyDTO, rptAppMobileServDTO);

		rptAppMobileServCustCopyDTO.setCustomerCopyInd("Y");
		ssCustCopyList.add(rptAppMobileServCustCopyDTO);

		// Add Application for Mobile Service Form
		// modify by eliot 20110824
		
		
		
		
		
		if (distMode == OrderDTO.DisMode.P && this.isEmpty(forms)) {
			map.put(key, new JasperReport(key, ssList));
		}
		if (this.isEmpty(forms)) {
			map.put(key + CUST_COPY, new JasperReport(key, ssCustCopyList));
		}
		
		fillCCStatements(rptCreditCardDDAuthDTO);

		// Add Credit Card Authorization Form
		if (isReqCreditCardAuthForm && this.isEmpty(forms)) {
			ccList.add(rptCreditCardDDAuthDTO);
			map.put(RptCreditCardDDAuthDTO.JASPER_TEMPLATE, new JasperReport(
					RptCreditCardDDAuthDTO.JASPER_TEMPLATE, ccList));
		}
		
		if (this.isEmpty(forms)) {
			if (isReqMnpForm && !isNEForm) {
				if (!isEmpty(multiSimMnpList)){//MultiSim Athena 20140128
					mnpList.add(rptMobPortAppDTO);
					mnpList.addAll(multiSimMnpList);
					map.put(RptMobPortAppDTO.JASPER_TEMPLATE, new JasperReport(
							RptMobPortAppDTO.JASPER_TEMPLATE, mnpList));
				}else{
					mnpList.add(rptMobPortAppDTO);
					map.put(RptMobPortAppDTO.JASPER_TEMPLATE, new JasperReport(
							RptMobPortAppDTO.JASPER_TEMPLATE, mnpList));
				}
			} else {
				if (!isEmpty(multiSimMnpList)){//MultiSim Athena 20140128
					map.put(RptMobPortAppDTO.JASPER_TEMPLATE, new JasperReport(
							RptMobPortAppDTO.JASPER_TEMPLATE, multiSimMnpList));
				}
			}
		}

		
		if (isReqSecSrvForm && this.isEmpty(forms)) {
			secSrvList.add(rptSecretarialServDTO);
			// map.put(RptSecretarialServDTO.JASPER_TEMPLATE, secSrvList);
			map.put(RptSecretarialServDTO.JASPER_TEMPLATE, new JasperReport(
					RptSecretarialServDTO.JASPER_TEMPLATE, secSrvList));
		}
		// add by eliot 20110527
		if (/*isReqTradeDescForm && */this.isEmpty(forms)) {
			if (hsTradeDescDTOs != null) {
				for (HSTradeDescDTO hsTradeDescDto: hsTradeDescDTOs) {
					RptHSTradeDescDTO rptHSTradeDescDTO = new RptHSTradeDescDTO();
					BeanUtils.copyProperties(rptHSTradeDescDTO, rptRptHSTradeDescDTO);
					BeanUtils.copyProperties(rptHSTradeDescDTO, hsTradeDescDto);
				
					if (custSignDTO != null) { //20130709
					rptHSTradeDescDTO.setCustSignature(custSignDTO.getSignatureInputStream()); 
					}
					
					tradeDescList.add(rptHSTradeDescDTO);
					
				}
				map.put(RptHSTradeDescDTO.JASPER_TEMPLATE, new JasperReport(
						RptHSTradeDescDTO.JASPER_TEMPLATE, tradeDescList));
			}
			
		}

		// add by Margaret 20110610
		if (isConSrvForm) {

			List<DisplayLkupDTO> tempList = new ArrayList<DisplayLkupDTO>();
			
			// concierge report 1 en
			DisplayLkupDTO displayLkupDto_1_en = new DisplayLkupDTO("CONCIERGE_REPORT", 1, "en", "");
			try {
				tempList = displayLkupDAO.getDisLkupByTypeIdLocale(displayLkupDto_1_en);
			} catch (DAOException e) {
				logger.debug("displayLkupDto_1_en: DAOException @MobCcsReportServiceImpl: " + e);
				e.printStackTrace();
			}
			if (tempList != null && tempList.size() > 0) {
				displayLkupDto_1_en = tempList.get(0);
			}
			
			// concierge report 2 en
			DisplayLkupDTO displayLkupDto_2_en = new DisplayLkupDTO("CONCIERGE_REPORT", 2, "en", "");
			try {
				tempList = displayLkupDAO.getDisLkupByTypeIdLocale(displayLkupDto_2_en);
			} catch (DAOException e) {
				logger.debug("displayLkupDto_2_en: DAOException @MobCcsReportServiceImpl: " + e);
				e.printStackTrace();
			}
			if (tempList != null && tempList.size() > 0) {
				displayLkupDto_2_en = tempList.get(0);
			}
			
			// concierge report 1 chi
			DisplayLkupDTO displayLkupDto_1_chi = new DisplayLkupDTO("CONCIERGE_REPORT", 1, "zh_HK", "");
			try {
				tempList = displayLkupDAO.getDisLkupByTypeIdLocale(displayLkupDto_1_chi);
			} catch (DAOException e) {
				logger.debug("displayLkupDto_1_chi: DAOException @MobCcsReportServiceImpl: " + e);
				e.printStackTrace();
			}
			if (tempList != null && tempList.size() > 0) {
				displayLkupDto_1_chi = tempList.get(0);
			}
			
			// concierge report 2 chi
			DisplayLkupDTO displayLkupDto_2_chi = new DisplayLkupDTO("CONCIERGE_REPORT", 2, "zh_HK", "");
			try {
				tempList = displayLkupDAO.getDisLkupByTypeIdLocale(displayLkupDto_2_chi);
			} catch (DAOException e) {
				logger.debug("displayLkupDto_2_en: DAOException @MobCcsReportServiceImpl: " + e);
				e.printStackTrace();
			}
			if (tempList != null && tempList.size() > 0) {
				displayLkupDto_2_chi = tempList.get(0);
			}
			

			//Athena 201309005
			if (rptConciergeServiPadiPhoneDTO.getModelName().toLowerCase().contains("iphone")){//iphone content
				rptConciergeServiPadiPhoneDTO.setNote(appCtx.getBean("concierge_note").toString());//eng version
				rptConciergeServiPadiPhoneDTO.setStatement(appCtx.getBean("concierge_statment").toString());
				
				rptConciergeServiPadiPhoneDTO.setNote_zh_hk(appCtx.getBean("concierge_note_zh_hk").toString());//chi version
				rptConciergeServiPadiPhoneDTO.setStatement_zh_hk(appCtx.getBean("concierge_statment_zh_hk").toString());
			}
			else if (rptConciergeServiPadiPhoneDTO.getModelName().toLowerCase().contains("ipad")){//ipad content
				rptConciergeServiPadiPhoneDTO.setNote(appCtx.getBean("concierge_note_ipad").toString());//eng version
				rptConciergeServiPadiPhoneDTO.setStatement(appCtx.getBean("concierge_statment_ipad").toString());
				
				rptConciergeServiPadiPhoneDTO.setNote_zh_hk(appCtx.getBean("concierge_note_ipad_zh_hk").toString());//chi version
				rptConciergeServiPadiPhoneDTO.setStatement_zh_hk(appCtx.getBean("concierge_statment_ipad_zh_hk").toString());
			}


			// rptConciergeServiPadiPhoneDTO.setCustomerCopyInd("N");
			String rptName = RptConciergeServiPadiPhoneDTO.JASPER_TEMPLATE
					+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
			conSrvList.add(rptConciergeServiPadiPhoneDTO);
			
			if (this.isEmpty(forms)) {
				map.put(rptName, new JasperReport(rptName, conSrvList));
			}
		}
		
		if (isStudentPlan) {
			rptServiceGuideDTO.setContentHtml1(this.getReportContentHtml("CY001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "contentHtml1"));
			rptServiceGuideDTO.setContentHtml2(this.getReportContentHtml("CY001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "contentHtml2"));
			rptServiceGuideDTO.setContentHtml3(this.getReportContentHtml("CY001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "contentHtml3"));
			rptServiceGuideDTO.setContentHtml4(this.getReportContentHtml("CY001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "contentHtml4"));
			rptServiceGuideDTO.setContentHtml5(this.getReportContentHtml("CY001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "contentHtml5"));
		}
		
		serviceGuideList.add(rptServiceGuideDTO);
		if (isNEForm) {
			if (this.isEmpty(forms)) {
				if (isUniversityPlan) {
					if (uniOptionalVasInd){
					map.put(RptServiceGuideDTO.JASPER_TEMPLATE_Uni + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""),
							new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_Uni
									+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), serviceGuideList));
					}
				}else{
				map.put(RptServiceGuideDTO.JASPER_TEMPLATE_NE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
						new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_NE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
										serviceGuideList));
				}
				// 20121008, 2 copies of SG
				if (distMode == OrderDTO.DisMode.P) {
					if (isUniversityPlan) {
						if (uniOptionalVasInd){
						map.put(RptServiceGuideDTO.JASPER_TEMPLATE_Uni + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "") + CUST_COPY,
								new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_Uni
										+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), serviceGuideList));
						}
					}else{
					map.put(RptServiceGuideDTO.JASPER_TEMPLATE_NE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "") + CUST_COPY, 
							new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_NE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
											serviceGuideList));
					}
				}
			}
		} else if (isStudentPlan) {
			/*if (this.isEmpty(forms)) {
				map.put(RptServiceGuideDTO.JASPER_TEMPLATE_CY + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
						new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_CY + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
										serviceGuideList));
				// 20121008, 2 copies of SG
				if (distMode == OrderDTO.DisMode.P) {
					map.put(RptServiceGuideDTO.JASPER_TEMPLATE_CY + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "") + CUST_COPY, 
							new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_CY + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
											serviceGuideList));
				}
			}*/
		} else {
			if (this.isEmpty(forms)) {
				if (isUniversityPlan) {
					if (uniOptionalVasInd){
					map.put(RptServiceGuideDTO.JASPER_TEMPLATE_Uni + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""),
							new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_Uni
									+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), serviceGuideList));
					}
				}else{
				map.put(RptServiceGuideDTO.JASPER_TEMPLATE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""),
						new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE	+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), serviceGuideList));
				}
				// 20121008, 2 copies of SG
				if (distMode == OrderDTO.DisMode.P) {
					if (isUniversityPlan) {
						if (uniOptionalVasInd){
						map.put(RptServiceGuideDTO.JASPER_TEMPLATE_Uni + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "") + CUST_COPY,
								new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_Uni
										+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), serviceGuideList));
						}
					}else{
					map.put(RptServiceGuideDTO.JASPER_TEMPLATE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "") + CUST_COPY,
							new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE	+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""),
									serviceGuideList));
					}
				}
			}
		}
		
		if (isChgServCustInfoRefundForm) {
			chgServCustInfoRefundList.add(rptChgServCustInfoRefundDTO);
			BeanUtils.copyProperties(rptChgServCustInfoRefundCustCopyDTO, rptChgServCustInfoRefundDTO);
			rptChgServCustInfoRefundCustCopyDTO.setCustomerCopyInd("Y");
			chgServCustInfoRefundCustCopyList.add(rptChgServCustInfoRefundCustCopyDTO);
		}
		if (!isEmpty(multiSimChgServCustInfoRefundList)){//MultiSim Athena 20140128
			chgServCustInfoRefundList.addAll(multiSimChgServCustInfoRefundList);
			chgServCustInfoRefundCustCopyList.addAll(multiSimChgServCustInfoRefundCustCopyList);
		}		
		if (!isEmpty(chgServCustInfoRefundList)) {
			if (this.isEmpty(forms)) {
				if (distMode == OrderDTO.DisMode.P) {
					map.put(MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS, 
							new JasperReport(MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS, 
									chgServCustInfoRefundList));
				}
				
				map.put(MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS + CUST_COPY, 
						new JasperReport(MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS, 
								chgServCustInfoRefundCustCopyList));
			}
			
		}
		
		
		BasketDTO basketDto = new BasketDTO();
		for (int i = 0; i < pDTOs.size(); i++) {
			
			tempObj = pDTOs.get(i);
			
			if (tempObj instanceof OrderDTO) {
					OrderDTO orderDto = (OrderDTO) tempObj;
					BeanUtils.copyProperties(basketDto, orderDto); //20130709
					//BeanUtils.copyProperties(iGuardService, orderDto);
				
				String locale="zh_HK";
		 		if ("en".equals(rptAppMobileServDTO.getLocale())) {
		 			locale= "en";
		 		}
		 		
				List<String> vasTempList = new ArrayList<String>();
				for (VasDetailDTO vas :vasDetailService.getVasDetailSelectedList(locale,orderDto.getOrderId() )) {
					vasTempList.add(vas.getItemId());
				}
				String [] vasGuardList = (String[]) vasTempList.toArray(new String[2]);
				List<String> srvPlanList= iGuardService.isIGuardOrder(basketDto.getBasketId(), vasGuardList,orderDto.getAppInDate());
				for(int j=0; j<srvPlanList.size();j++){
					String iGuardType = srvPlanList.get(j);
					if("LDS".equalsIgnoreCase(iGuardType)){
						if (this.containsForm(forms, Form.IGUARD_LDS)) {
							iGuardDTO.setDirectLogo(imageFilePath + "/" + IGUARD_DIRECT_LOGO_FILE);
							iGuardDTO.setPhoneProtectorLogo(imageFilePath + "/" + IGUARD_PHONEPROTECTOR_LOGO_FILE);
							
							BeanUtils.copyProperties(iGuardCustCopyDTO, iGuardDTO);
							
							iGuardDTO.setCopy("Office Copy");
							iGuardCustCopyDTO.setCopy("Customer Copy");
							
							iGuardList.add(iGuardDTO);
							iGuardCustCopyList.add(iGuardCustCopyDTO);
							
							if (distMode == OrderDTO.DisMode.P) {
								map.put(IGuardDTO.JASPER_TEMPLATE_LDS, 
										new JasperReport(IGuardDTO.JASPER_TEMPLATE_LDS, iGuardList));
							}
							map.put(IGuardDTO.JASPER_TEMPLATE_LDS + CUST_COPY, 
									new JasperReport(IGuardDTO.JASPER_TEMPLATE_LDS, iGuardCustCopyList));
							map.put(IGuardDTO.JASPER_TEMPLATE_TNC_LDS, 
									new JasperReport(IGuardDTO.JASPER_TEMPLATE_TNC_LDS, iGuardList));
				
						}
					}else if("AD".equalsIgnoreCase(iGuardType)){
						if (this.containsForm(forms, Form.IGUARD_AD)) {
							iGuardDTO.setDirectLogo(imageFilePath + "/" + IGUARD_DIRECT_LOGO_FILE);
							iGuardDTO.setPhoneProtectorLogo(imageFilePath + "/" + IGUARD_PHONEPROTECTOR_LOGO_FILE);
							
							BeanUtils.copyProperties(iGuardCustCopyDTO, iGuardDTO);
							
							iGuardDTO.setCopy("Office Copy");
							iGuardCustCopyDTO.setCopy("Customer Copy");
							
							iGuardList.add(iGuardDTO);
							iGuardCustCopyList.add(iGuardCustCopyDTO);
								
							if (distMode == OrderDTO.DisMode.P) {
								map.put(IGuardDTO.JASPER_TEMPLATE_AD, 
										new JasperReport(IGuardDTO.JASPER_TEMPLATE_AD, iGuardList));
							}
							map.put(IGuardDTO.JASPER_TEMPLATE_AD + CUST_COPY, 
									new JasperReport(IGuardDTO.JASPER_TEMPLATE_AD, iGuardCustCopyList));
							map.put(IGuardDTO.JASPER_TEMPLATE_TNC_AD, 
									new JasperReport(IGuardDTO.JASPER_TEMPLATE_TNC_AD, iGuardList));
				
						}
						
					}
					else if ("UAD".equalsIgnoreCase(iGuardType)){
						iPhoneTradeInFormDTO.setIsIGuardUAD("Y");
						if (this.containsForm(forms, Form.IGUARD_UAD)) {
							iGuardDTO.setDirectLogo(imageFilePath + "/" + IGUARD_DIRECT_LOGO_FILE);
							iGuardDTO.setPhoneProtectorLogo(imageFilePath + "/" + IGUARD_PHONEPROTECTOR_LOGO_FILE);
							iGuardDTO.setOrderId(orderDto.getOrderId());
							iGuardDTO.setPrivacyInd99992(privacyInd99992);
							iGuardDTO.setPrivacyInd99993(privacyInd99993);
							iGuardDTO.setPrivacyInd99994(privacyInd99994);
							
							BeanUtils.copyProperties(iGuardCustCopyDTO, iGuardDTO);
							
							iGuardDTO.setCopy("Office Copy");
							iGuardCustCopyDTO.setCopy("Customer Copy");
							
							iGuardList.add(iGuardDTO);
							iGuardCustCopyList.add(iGuardCustCopyDTO);
								
							if (distMode == OrderDTO.DisMode.P) {
								map.put(IGuardDTO.JASPER_TEMPLATE_UAD, 
										new JasperReport(IGuardDTO.JASPER_TEMPLATE_UAD, iGuardList));
							}
							map.put(IGuardDTO.JASPER_TEMPLATE_UAD + CUST_COPY, 
									new JasperReport(IGuardDTO.JASPER_TEMPLATE_UAD, iGuardCustCopyList));
				
						}
					}
				}
	
			}
		}
		
		if (this.isEmpty(forms)){
		if (isIphone7TradeInBasket || isIphone8TradeInBasket ) {	
			iPhoneTradeInFormDTO.setOrderType("Y");
			iPhoneTradeInFormDTO.setSignatureInd("Y");
			iPhoneTradeInFormDTO.setpdf(pLang, isIphone8TradeInBasket,brand);
			iPhoneTradeInFormDTO.setMobCustNum("");
			if (StringUtils.isEmpty(iPhoneTradeInFormDTO.getImei())){
				iPhoneTradeInFormDTO.setImei("");
			}
			BeanUtils.copyProperties(iPhoneTradeInFormCustCopyDTO, iPhoneTradeInFormDTO);
			
			iPhoneTradeInFormCustCopyDTO.setCustomerCopyInd("Y");
			iPhoneTradeInFormDTOList.add(iPhoneTradeInFormDTO);
			
			iPhoneTradeInFormCustCopyDTOList.add(iPhoneTradeInFormCustCopyDTO);
		
			if (isIphone7TradeInBasket){
				
				map.put(IPhoneTradeInFormDTO.JASPER_TEMPLATE+ CUST_COPY, 
							new JasperReport(IPhoneTradeInFormDTO.JASPER_TEMPLATE+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), iPhoneTradeInFormCustCopyDTOList));
				
				if (distMode == OrderDTO.DisMode.P) {
					map.put(IPhoneTradeInFormDTO.JASPER_TEMPLATE, 
							new JasperReport(IPhoneTradeInFormDTO.JASPER_TEMPLATE+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), iPhoneTradeInFormDTOList));
				}
				
			}else if (isIphone8TradeInBasket){
				
			
				map.put(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8+ CUST_COPY, 
						new JasperReport(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), iPhoneTradeInFormCustCopyDTOList));
				
				if (distMode == OrderDTO.DisMode.P) {
					map.put(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8, 
							new JasperReport(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), iPhoneTradeInFormDTOList));
					}
				}
			}
		}
		if (this.containsForm(forms, Form.MOBILE_SAFETY_PHONE)) {
			try {
				List<ComponentDTO> list = this.orderDao.getComponentList(mobileSafetyPhoneDTO.getOrderId());
				for (RptMobileSafetyPhoneDTO.ServiceAttb attb : RptMobileSafetyPhoneDTO.ServiceAttb.values()) {
					String value = this.nullAsBlank(this.getAttbValue(list, attb.getAttbId()));
					BeanUtils.setProperty(mobileSafetyPhoneDTO, attb.getField(), value);
					BeanUtils.setProperty(mobileSafetyPhoneAFCDTO, attb.getField(), value);
					BeanUtils.setProperty(mobileSafetyPhoneTnCDTO, attb.getField(), value);
				}
				mobileSafetyPhoneList.add(mobileSafetyPhoneDTO);
				mobileSafetyPhoneDTO.setMobileSafetyLogo(imageFilePath + "/" + MOBILE_SAFETY_PHONE_FILE);
				map.put(RptMobileSafetyPhoneDTO.JASPER_TEMPLATE, new JasperReport(RptMobileSafetyPhoneDTO.JASPER_TEMPLATE, mobileSafetyPhoneList));

				mobileSafetyPhoneAFCList.add(mobileSafetyPhoneAFCDTO);
				mobileSafetyPhoneAFCDTO.setMobileSafetyLogo(imageFilePath + "/" + MOBILE_SAFETY_PHONE_FILE);
				map.put(RptMobileSafetyPhoneAFCDTO.JASPER_TEMPLATE, new JasperReport(RptMobileSafetyPhoneAFCDTO.JASPER_TEMPLATE, mobileSafetyPhoneAFCList));

				mobileSafetyPhoneTnCList.add(mobileSafetyPhoneTnCDTO);
				mobileSafetyPhoneTnCDTO.setMobileSafetyLogo(imageFilePath + "/" + MOBILE_SAFETY_PHONE_FILE);
				map.put(RptMobileSafetyPhoneTnCDTO.JASPER_TEMPLATE, new JasperReport(RptMobileSafetyPhoneTnCDTO.JASPER_TEMPLATE, mobileSafetyPhoneTnCList));
				
			} catch (DAOException e) {
				throw new AppRuntimeException("Exception found", e);
			}
		}
		
		if (this.containsForm(forms, Form.NFC_SIM)) {
			nfcSimList.add(rptNFCConsentDTO);
			map.put(RptNFCConsentDTO.JASPER_TEMPLATE, new JasperReport(RptNFCConsentDTO.JASPER_TEMPLATE, nfcSimList));
		}
		if (this.containsForm(forms, Form.IGUARD_CARECASH)) {
		rptIGuardCareCashDTO.setMob("1");
		rptIGuardCareCashDTO.setSignatureInd("Y");
		iGuardCareCashList.add(rptIGuardCareCashDTO);
		map.put(RptIGuardCareCashDTO.JASPER_TEMPLATE, 
				new JasperReport(RptIGuardCareCashDTO.JASPER_TEMPLATE, iGuardCareCashList));
		if (distMode == OrderDTO.DisMode.P) {
			map.put(RptIGuardCareCashDTO.JASPER_TEMPLATE+ CUST_COPY, 
					new JasperReport(RptIGuardCareCashDTO.JASPER_TEMPLATE, iGuardCareCashList));
		}
	
		}
		
		if (this.containsForm(forms, Form.NFC_SIM_SET) || this.containsForm(forms, Form.OCTOPUS_SIM)) {
			nfcSimList.add(rptNFCConsentDTO);
			map.put(RptNFCConsentDTO.JASPER_TEMPLATE, new JasperReport(RptNFCConsentDTO.JASPER_TEMPLATE, nfcSimList));
			/*octopusSimTnCList.add(rptOctopusTnCDTO);
			map.put(RptOctopusTnCDTO.JASPER_TEMPLATE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
					new JasperReport(RptOctopusTnCDTO.JASPER_TEMPLATE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), octopusSimTnCList));*/
		}
		
		Map<String, JasperReport> genericReportData = mapGenericReportData(forms, pLang, genericReportDataMap, genericReportSignatureMap);
		if (genericReportData != null) {
			map.putAll(genericReportData);
		}
		
		// PROJECT EAGLE START
		if (this.containsForm(forms, Form.PROJECT_EAGLE_FORM)) {
			rptProjectEagleDTO = patchWithLabels(rptProjectEagleDTO);
			boolean isCallCenterOrder = StringUtils.startsWithIgnoreCase(oid, "C");
			if (isCallCenterOrder) {
				rptProjectEagleDTO.setSignatureLabel(null);
				rptProjectEagleDTO.setSignature(null);
			} 
			projectEagleList.add(rptProjectEagleDTO);
			map.put(ProjectEagleReportHelper.FORM_NAME, 
					new JasperReport(ProjectEagleReportHelper.FORM_NAME, projectEagleList));
			if (distMode == OrderDTO.DisMode.P) {
				map.put(ProjectEagleReportHelper.FORM_NAME + CUST_COPY, 
						new JasperReport(ProjectEagleReportHelper.FORM_NAME, projectEagleList));
			}
		}
		// PROJECT EAGLE END
		
		return map;
	}
	
	private HashMap<String, JasperReport> mapGenericReportData(
						List<Form> forms,
						String pdfLang,
						Map<String, String> genericReportDataMap,
						Map<String, InputStream> genericReportSignatureMap)
			throws InvocationTargetException, IllegalAccessException {
		
		if (CollectionUtils.isEmpty(forms)) {
			return null;
		}
		
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();
		
		for (Form form : forms) {
			GenericForm genericForm = genericReportHelper.getCorrespondingGenericForm(form);
			if (genericForm == null) {
				continue;
			}
			
			RptGenericFormTemplateDTO rptGenericFormTemplateDTO = genericReportHelper.getRptGenericFormTemplateDTO(
					genericForm, pdfLang, genericReportDataMap, genericReportSignatureMap);
			List<ReportDTO> reportDtoList = new ArrayList<ReportDTO>();
			reportDtoList.add(rptGenericFormTemplateDTO);
			map.put(genericForm.getFormName(), new JasperReport(rptGenericFormTemplateDTO.getJasperName(), reportDtoList));
		}
		
		return map;
	}

	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}
	public void setPropertyConfigurer(BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	public String getAppEnv() {
		return appEnv;
	}
	public void setAppEnv(String appEnv) {
		this.appEnv = appEnv;
	}

	public String getServerPath() {
		return serverPath;
	}
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	private class JasperReport {
		private String jasperTemplateName = null;
		private List<ReportDTO> rptDtoArrList = null;

		public JasperReport(String pJasperTemplateName,
				List<ReportDTO> pRptDtoArrList) {
			jasperTemplateName = pJasperTemplateName;
			rptDtoArrList = pRptDtoArrList;
		}

		protected String getJasperTemplateName() {
			return jasperTemplateName;
		}

		protected List<ReportDTO> getRptDtoArrList() {
			return rptDtoArrList;
		}
	}
	
	private void fillSSStatements(RptAppMobileServDTO input, boolean isNE) {
		
		String configFile = "reportConstant.xml";
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile);
		
		if ("en".equals(input.getLocale())) {
			input.setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateEng1").toString());
			input.setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateEng2").toString());
			if (isNE) {
				input.setCustAgree(appCtx.getBean("neCustAgreeEng").toString());
			} else {
				input.setCustAgree(appCtx.getBean("ssCustAgreeEng").toString());
			}
		} else {
			input.setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateChi1").toString());
			input.setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateChi2").toString());
			if (isNE) {
				input.setCustAgree(appCtx.getBean("neCustAgreeChi").toString());
			} else {
				input.setCustAgree(appCtx.getBean("ssCustAgreeChi").toString());
			}
		}		
		
	}
	
	private void fillCCStatements(RptCreditCardDDAuthDTO input) {
		
		String configFile = "reportConstant.xml";
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile);
		
		input.setCcos7(appCtx.getBean("ccOSState7").toString());
		input.setCcos8(appCtx.getBean("ccOSState8").toString());

	}
	
	private String nullAsBlank(String str) {
		if (str == null) return "";
		return str;
	}
	
	public String getAttbValue(List<ComponentDTO> list, String attbId) {
		if (this.isEmpty(list)) {
			return null;
		}
		for (ComponentDTO dto : list) {
			if (dto.getCompAttbId().equals(attbId)) {
				return dto.getCompAttbVal();
			}
		}
		return null;
	}
	
	private boolean containsForm(List<Form> forms, Form form) {
		if (form == null) {
			return false;
		}
		if (this.isEmpty(forms)) {
			return false;
		}
		return forms.contains(form);
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public boolean isGenerateM2Report(Date appDate) {
		try {
			logger.info("isGenerateM2Report() is called in StockManualAssgnServiceImpl");
			Date m2BraningDate = reportDAO.getM2BrandingDate();
			logger.info("m2BraningDate = " + m2BraningDate);
			logger.info("appDate = " + appDate);
			if (m2BraningDate == null) {
				return false;
			} else if (m2BraningDate.equals(appDate) || m2BraningDate.before(appDate)) {
				return true;
			}
			
			return false;
		} catch (DAOException de) {
			logger.error("Exception caught in isGenerateM2Report()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getReportContentHtml(String formId, String locale, String variableId) {
		try {
			return reportDAO.getReportContentHtml(formId, locale, variableId);
		} catch (DAOException de) {
			logger.error("Exception caught in isGenerateM2Report()", de);
			throw new AppRuntimeException(de);
		}
	}
	public String maskedDocNum (String docNum){
		if (docNum.length()<8){
			return docNum;
		}else{
			String maskedDocNum=docNum.substring(0,4) + "***" + docNum.substring(7);
			return maskedDocNum;
		}
	}
	
	public VasDetailDTO getVasDetail(String itemId){   	
		try{
			logger.debug("getVasDetail() is called in VasDetailService service");
			return vasDetailDao.getVasItemDetail(itemId);
		}catch (DAOException de) {
			logger.error("Exception caught in getVasDetail()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getCodeId(String codeType) {
		List<CodeLkupDTO> codeLkupDTOList = LookupTableBean.getInstance().getCodeLkupList().get(codeType);
		for (CodeLkupDTO codeLkupDTO : codeLkupDTOList) {
			return codeLkupDTO.getCodeId();
		}
		return null;
	}
	
	public String saveHKTCarePdfReports(List<Object> pReportArrList, String pLang, String orderId, String msisdn, String brand, Form form) {
		return saveHKTCarePdfReports(pReportArrList, pLang, orderId, msisdn, brand, form, false);
	}
	
	public String saveHKTCarePdfReports(List<Object> pReportArrList, String pLang, String orderId, String msisdn, String brand, Form form, boolean isTemp) {
		
		if (form == null) {
			return "";
		}
		
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv + "." + BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("[ " + orderId + "] Exception caught in saveHKTCarePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String path = StringUtils.replace(getServerPath(), "\\", "/") + orderId + (isTemp? "/temp" : "/HKTCare");
		logger.info("[" + orderId + "] savePdfReports path = " + path);
		File outputPdfPath = new File(path);
		File outputPdf = new File(path + "/" + genericReportHelper.getGenericFormFileName(orderId, msisdn, form));
		
		FileOutputStream fos = null;
		
		try {
			if (outputPdfPath.exists()) {
				if (outputPdf.exists()) {
					outputPdf.delete();
					outputPdf.createNewFile();
				} else {
					outputPdf.createNewFile();
				}
			} else {
				outputPdfPath.mkdirs();
				outputPdf.createNewFile();
			}

			fos = new FileOutputStream(outputPdf);
			this.generatePdfReports(pReportArrList, fos, pLang, orderId, Arrays.asList(form), brand);
			fos.close();
			fos = null;
			
		} catch (IOException ioe) {
			logger.error("[ " + orderId + "] Exception caught in saveHKTCarePdfReports()", ioe);
			throw new AppRuntimeException(ioe);
			
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
				}
			}
		}
		
		return StringUtils.substringAfter(StringUtils.replace(outputPdf.getAbsolutePath(), "\\", "/"), "/" + orderId + "/");
	}
	
	public RptProjectEagleDTO patchWithLabels(RptProjectEagleDTO rptProjectEagleDTO) {
		
		RptProjectEagleDTO finalRptProjectEagleDTO = rptProjectEagleDTO;
		if (finalRptProjectEagleDTO == null) {
			finalRptProjectEagleDTO = new RptProjectEagleDTO();
		}
		
		finalRptProjectEagleDTO.setJasperName(ProjectEagleReportHelper.FORM_NAME);
		
		finalRptProjectEagleDTO.setHeader(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "header"));
		finalRptProjectEagleDTO.setOrderIdLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "orderIdLabel") + finalRptProjectEagleDTO.getOrderId());
		finalRptProjectEagleDTO.setLogo(finalRptProjectEagleDTO.getImageFilePath() + "/" + getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "logo"));
		finalRptProjectEagleDTO.setProductContact(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "productContact"));
		finalRptProjectEagleDTO.setProductName(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "productName"));
		finalRptProjectEagleDTO.setNotesForFillIn(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "notesForFillIn"));
		finalRptProjectEagleDTO.setSurnameLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "surnameLabel"));
		finalRptProjectEagleDTO.setGivenNameLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "givenNameLabel"));
		finalRptProjectEagleDTO.setTitleLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "titleLabel"));
		finalRptProjectEagleDTO.setIdDocNumLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "idDocNumLabel"));
		finalRptProjectEagleDTO.setDateOfBirthLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "dateOfBirthLabel"));
		finalRptProjectEagleDTO.setAddressLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "addressLabel"));
		finalRptProjectEagleDTO.setEmailLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "emailLabel"));
		finalRptProjectEagleDTO.setContactNumberLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "contactNumberLabel"));
		finalRptProjectEagleDTO.setTargetCommencementDateLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "targetCommencementDateLabel"));
		finalRptProjectEagleDTO.setContractPeriodLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "contractPeriodLabel"));
		finalRptProjectEagleDTO.setMaxSwapLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "maxSwapLabel"));
		finalRptProjectEagleDTO.setPlanFeeLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "planFeeLabel"));
		finalRptProjectEagleDTO.setSwapFeeLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "swapFeeLabel"));
		finalRptProjectEagleDTO.setPlanRemark(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "planRemark"));
		finalRptProjectEagleDTO.setTitleForServiceDetails(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "titleForServiceDetails"));
		finalRptProjectEagleDTO.setSubTitleForServiceDetails(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "subTitleForServiceDetails"));
		finalRptProjectEagleDTO.setShopCodeLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "shopCodeLabel"));
		finalRptProjectEagleDTO.setHandsetBrandModelLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "handsetBrandModelLabel"));
		finalRptProjectEagleDTO.setHandsetRetailPriceLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "handsetRetailPriceLabel"));
		finalRptProjectEagleDTO.setImeiNoLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "imeiNoLabel"));
		finalRptProjectEagleDTO.setSubscriptionMobileNoLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "subscriptionMobileNoLabel"));
		finalRptProjectEagleDTO.setPicsContent(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "picsContent"));
		finalRptProjectEagleDTO.setDirectMarketingContent(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "directMarketingContent"));
		finalRptProjectEagleDTO.setDirectMarketingContentCHI(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "directMarketingContentCHI"));
		finalRptProjectEagleDTO.setDirectMarketingRemark(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "directMarketingRemark"));
		finalRptProjectEagleDTO.setSignatureLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "signatureLabel"));
		finalRptProjectEagleDTO.setHandsetReceivedDateLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "handsetReceivedDateLabel"));
		finalRptProjectEagleDTO.setFooter(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "footer"));
		finalRptProjectEagleDTO.setDateLabel(getTemplateFromDB(ProjectEagleReportHelper.FORM_ID, "en", "dateLabel"));
		
		return finalRptProjectEagleDTO;
	}
	
	public String getTemplateFromDB(String formId, String locale, String variableId) {
		String template = getReportContentHtml(formId, locale, variableId);
		return StringUtils.isBlank(template)? "" : template;
	}
	
	/**
	 * PDF Generation and Save and all ord doc save, copy from ConfirmationController
	 * @param esigEmailLang
	 * @param orderDto
	 */
	public void generateAndSaveDigitalModePdf(HttpServletRequest request, boolean isTemp) {
		
		OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute(ReportSessionName.SIGNOFF.getOrderDtoName());
		if (orderDto == null) {
			return;
		}
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
     	
		// Get necessary objects from sessions
    	SupportDocUI sd = (SupportDocUI) request.getSession().getAttribute(ReportSessionName.SIGNOFF.getSupportDocDtoName());
    	BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute(ReportSessionName.SIGNOFF.getBomSalesUserDtoName());
    	CustomerProfileDTO customerProfileDTO = (CustomerProfileDTO) request.getSession().getAttribute(ReportSessionName.SIGNOFF.getCustomerDtoName());
    	BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request, ReportSessionName.SIGNOFF.getBasketDtoName());
		String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		String appDateStr = (String) request.getSession().getAttribute("appDate");
		
		PdfLang pdfLang = (sd != null && (OrderDTO.EsigEmailLang.CHN == sd.getEsigEmailLang()))? PdfLang.ZH : PdfLang.EN;
		String locale = (sd != null && (OrderDTO.EsigEmailLang.CHN == sd.getEsigEmailLang()))? "zh" : "en";
		String orderId = orderDto.getOrderId();
		String msisdn = orderDto.getMsisdn();
		Date appDate = Utility.string2Date(appDateStr);
		String brand = customerProfileDTO.getBrand();
		String iGuardSerialNo = orderDto.getiGuardSerialNo();
		String nfcInd = orderDto.getNfcInd();
		String basketId = orderDto.getBasketId();
		Date appInDate = orderDto.getAppInDate();
		
		if (isTemp) {
			deleteTempDigitalModePdf(orderId);
		}
		
		boolean isIGuard = StringUtils.isNotBlank(iGuardSerialNo);
		boolean isTravelInsurance = vasDetailService.existInSelectionGrpList(GenericReportHelper.TRAVEL_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID, selectedItemList);
		boolean isHelperCareInsurance = vasDetailService.existInSelectionGrpList(GenericReportHelper.HELPERCARE_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID, selectedItemList);
		boolean isProjectEagleInsurance = vasDetailService.existInSelectionGrpList(ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, selectedItemList);
		boolean isMobileSafetyPhone = this.vasDetailService.isItemsInGroup(basketDTO.getBasketId(), selectedItemList, appDate, "100000103");
		boolean isNFCSim = (!customerProfileDTO.isStudentPlanSubInd() && this.vasDetailService.isExtraFunctionSimByNfcInd(nfcInd));
		boolean isCareCash = "Y".equals(customerProfileDTO.getCareStatus()) && "I".equals(customerProfileDTO.getCareOptInd());
		
		List<Form> forms = new ArrayList<Form>();
		
		List<String> iGuardList = iGuardService.isIGuardOrder(basketId, selectedItemList, appInDate);
		if (!CollectionUtils.isEmpty(iGuardList)) {
			for (String iGuardType : iGuardList) {
				if ("LDS".equalsIgnoreCase(iGuardType) && isIGuard) {
					forms.add(Form.IGUARD_LDS);
				} else if ("AD".equalsIgnoreCase(iGuardType)) {
					forms.add(Form.IGUARD_AD);
				} else if ("UAD".equalsIgnoreCase(iGuardType)) {
					forms.add(Form.IGUARD_UAD);
				}
			}
		}
		if (isTravelInsurance) {
			forms.add(Form.TRAVEL_INSURANCE_FORM);
		}
		if (isHelperCareInsurance) {
			forms.add(Form.HELPERCARE_INSURANCE_FORM);
		}
		if (isProjectEagleInsurance) {
			forms.add(Form.PROJECT_EAGLE_FORM);
		}
		if (isMobileSafetyPhone) {
			forms.add(Form.MOBILE_SAFETY_PHONE);
		}
		if (isNFCSim) {
			forms.add(Form.NFC_SIM_SET);
		}
		if (isCareCash) {
			forms.add(Form.IGUARD_CARECASH);
		}
		
		List<Object> arrayList = reportHelper.getData(request, ReportSessionName.SIGNOFF, locale, forms);
		String filePathName = savePdfReports(arrayList, locale, orderId, brand, isTemp);
		AllOrdDocDTO allOrdDocDTO = new AllOrdDocDTO();
		allOrdDocDTO.setOrderId(orderId);
		allOrdDocDTO.setDocType(AllDocDTO.DocType.M001);
		allOrdDocDTO.setDocTypeMob(AllDocDTO.DocType.M001.toString());
		allOrdDocDTO.setProcessDate(null);
		allOrdDocDTO.setCreateBy(salesUserDto.getUsername());
		allOrdDocDTO.setLastUpdBy(salesUserDto.getUsername());
		allOrdDocDTO.setFilePathName(filePathName);
		if (isTemp) {
			supportDocService.insertAllOrderDocTempDTO(allOrdDocDTO);
		} else {
			ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
		}
		
		if (!CollectionUtils.isEmpty(iGuardList)) {
			for (String iGuardType : iGuardList) {
				filePathName = saveIGuardPdfReports(arrayList, orderId, msisdn, iGuardSerialNo, iGuardType, brand, isTemp);
				if ("LDS".equalsIgnoreCase(iGuardType)) {
					allOrdDocDTO.setDocType(AllDocDTO.DocType.M011);
					allOrdDocDTO.setDocTypeMob(AllDocDTO.DocType.M011.toString());
				} else if("AD".equalsIgnoreCase(iGuardType)){
					allOrdDocDTO.setDocType(AllDocDTO.DocType.M037);
					allOrdDocDTO.setDocTypeMob(AllDocDTO.DocType.M037.toString());
				} else if("UAD".equalsIgnoreCase(iGuardType)){
					allOrdDocDTO.setDocTypeMob("M042");
				}
				allOrdDocDTO.setFilePathName(filePathName);
				if (isTemp) {
					supportDocService.insertAllOrderDocTempDTO(allOrdDocDTO);
				} else {
					ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
				}
			}
		}
		if (isTravelInsurance) {
			filePathName = saveHKTCarePdfReports(arrayList, locale, orderId, msisdn, brand, Form.TRAVEL_INSURANCE_FORM, isTemp);
			allOrdDocDTO.setDocTypeMob(AllDocDTO.DocType.M052.toString());
			allOrdDocDTO.setFilePathName(filePathName);
			if (isTemp) {
				supportDocService.insertAllOrderDocTempDTO(allOrdDocDTO);
			} else {
				ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
			}
		}
		if (isHelperCareInsurance) {
			filePathName = saveHKTCarePdfReports(arrayList, locale, orderId, msisdn, brand, Form.HELPERCARE_INSURANCE_FORM, isTemp);
			allOrdDocDTO.setDocTypeMob(AllDocDTO.DocType.M053.toString());
			allOrdDocDTO.setFilePathName(filePathName);
			if (isTemp) {
				supportDocService.insertAllOrderDocTempDTO(allOrdDocDTO);
			} else {
				ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
			}
		}
		if (isProjectEagleInsurance) {
			filePathName = saveHKTCarePdfReports(arrayList, locale, orderId, msisdn, brand, Form.PROJECT_EAGLE_FORM, isTemp);
			allOrdDocDTO.setDocTypeMob(AllDocDTO.DocType.M055.toString());
			allOrdDocDTO.setFilePathName(filePathName);
			if (isTemp) {
				supportDocService.insertAllOrderDocTempDTO(allOrdDocDTO);
			} else {
				ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
			}
		}
		if (isMobileSafetyPhone) {
			filePathName = saveMobileSafetyPhoneReports(arrayList, locale, orderId, brand, isTemp);
			allOrdDocDTO.setDocType(AllDocDTO.DocType.M013);
			allOrdDocDTO.setDocTypeMob(AllDocDTO.DocType.M013.toString());
			allOrdDocDTO.setFilePathName(filePathName);
			if (isTemp) {
				supportDocService.insertAllOrderDocTempDTO(allOrdDocDTO);
			} else {
				ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
			}
		}
		if (isNFCSim) {
			filePathName = saveNFCSimReports(arrayList, locale, orderId, brand, isTemp);
			allOrdDocDTO.setDocTypeMob("M015");
			allOrdDocDTO.setFilePathName(filePathName);
			if (isTemp) {
				supportDocService.insertAllOrderDocTempDTO(allOrdDocDTO);
			} else {
				ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
			}
		}
		if (isCareCash) {
			filePathName = saveIGuardCareCashPdfReports(arrayList, locale, orderId, msisdn, brand, isTemp);
			allOrdDocDTO.setDocTypeMob("M045");
			allOrdDocDTO.setFilePathName(filePathName);
			if (isTemp) {
				supportDocService.insertAllOrderDocTempDTO(allOrdDocDTO);
			} else {
				ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
			}
		}
		
		stopWatch.stop();
		
		logger.info("[" + orderId + "] generateAndSaveDigitalModePdf elasped time = " + stopWatch.getTime() + "ms");

	}
	
	public void deleteTempPdfReports(String orderId) {
		
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("[ " + orderId + "] Exception caught in deleteTempPdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String path = StringUtils.replace(getServerPath(), "\\", "/") + orderId + "/temp";
		logger.info("[" + orderId + "] deleteTempPdfReports path = " + path);
		File tempDirectory = new File(path);
		try {
			FileUtils.deleteDirectory(tempDirectory);
		} catch (IOException ioe) {
			logger.error("[" + orderId + "] Exception caught in deleteTempPdfReports(), Path = " + path, ioe);
			throw new AppRuntimeException(ioe);
		}
		
	}
	
	public void deleteTempDigitalModePdf(String orderId) {
		deleteTempPdfReports(orderId);
		supportDocService.deleteAllOrderDocTempDTO(orderId);
	}

}