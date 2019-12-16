package com.bomwebportal.mob.ccs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dao.DisplayLkupDAO;
import com.bomwebportal.dao.HSTradeDescDAO;
import com.bomwebportal.dao.IGuardDAO;
import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dao.VasDetailDAO;
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
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.ServicePlanReportDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.report.AdditionalChargeDTO;
import com.bomwebportal.dto.report.IPhoneTradeInFormDTO;
import com.bomwebportal.dto.report.MiscellaneousChargeDTO;
import com.bomwebportal.dto.report.ReportDTO;
import com.bomwebportal.dto.report.RptAppMobileServDTO;
import com.bomwebportal.dto.report.RptConciergeServiPadiPhoneDTO;
import com.bomwebportal.dto.report.RptCreditCardDDAuthDTO;
import com.bomwebportal.dto.report.RptCreditCardDDAuthOSDTO;
import com.bomwebportal.dto.report.RptGenericFormTemplateDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.dto.report.RptIGuardCareCashDTO;
import com.bomwebportal.dto.report.RptMobPortAppDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneSuppAppDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneTnCDTO;
import com.bomwebportal.dto.report.RptMultiSimDTO;
import com.bomwebportal.dto.report.RptNFCConsentDTO;
import com.bomwebportal.dto.report.RptProjectEagleDTO;
import com.bomwebportal.dto.report.RptSecretarialServDTO;
import com.bomwebportal.dto.report.RptServiceGuideDTO;
import com.bomwebportal.dto.report.RptTnGServiceFormDTO;
import com.bomwebportal.dto.report.RptTradeInHSDTO;
import com.bomwebportal.dto.report.RptVasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.CodeLkupDAO;
import com.bomwebportal.mob.ccs.dao.DeliveryDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsSupportDocDAO;
import com.bomwebportal.mob.ccs.dao.PaymentUpfrontDAO;
import com.bomwebportal.mob.ccs.dao.StaffInfoDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ContactDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMagentoCouponDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.form.MobCcsChgServCustInfoRefundDTO;
import com.bomwebportal.mob.ccs.dto.form.MobCcsCourierDeliveryGuidDTO;
import com.bomwebportal.mob.ccs.dto.form.MobCcsDOADeliveryNoteDTO;
import com.bomwebportal.mob.ccs.dto.form.MobCcsDeliveryNoteDTO;
import com.bomwebportal.mob.ccs.dto.form.StsDOADeliveryNoteDTO;
import com.bomwebportal.mob.ccs.dto.form.StsDeliveryNoteDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.service.DepositService;
import com.bomwebportal.service.ItemDisplayService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReportService;
import com.bomwebportal.service.ReportServiceImpl;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.bomwebportal.util.PdfUtil;
import com.bomwebportal.util.ReportUtil;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.GenericReportHelper.GenericForm;
import com.bomwebportal.web.util.ProjectEagleReportHelper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MobCcsReportServiceImpl implements MobCcsReportService {
	
	private static final Logger logger = Logger.getLogger(MobCcsReportServiceImpl.class);
	
	private static ArrayList<String> ssOffCopySeq = null;
	private static ArrayList<String> ssCustCopySeq = null;
	private static ArrayList<String> conciergeSeq = null;
	private static ArrayList<String> reportIGuardCustCopySeq = null;
	private static ArrayList<String> servGuideCopySeq = null;
	private static ArrayList<String> iPhoneTradeInFormOffSeq = null;
	private static ArrayList<String> iPhoneTradeInFormCustSeq = null;

	private String imageFilePath;
//	private static String COMPANY_LOGO_TOP_M2 = "csl_top_m2.png";
//	private static String COMPANY_LOGO_BOTTOM_RT_M2 = "one2free_bottom_m2.png";
//	private static String COMPANY_LOGO_BOTTOM_LT_CHI_M2 = "hkt_logo_m2_chi.png";
//	private static String COMPANY_LOGO_BOTTOM_LT_ENG_M2= "hkt_logo_m2_eng.png";	
	
/*	private static String COMPANY_LOGO_TOP_M2 = "csl_orange_300.PNG";
	private static String COMPANY_LOGO_BOTTOM_RT_M2 = "one2free_orange_300.PNG";
	private static String COMPANY_LOGO_BOTTOM_LT_CHI_M2 = "an-HKT-company_300dpi_zh.PNG";
	private static String COMPANY_LOGO_BOTTOM_LT_ENG_M2= "an-HKT-company_300dpi_en.PNG";*/
	
	private static String COMPANY_LOGO_TOP_M2 = "CSL_300.jpg";
	private static String COMPANY_LOGO_BOTTOM_RT_M2 = "o2f_300.jpg";
	private static String COMPANY_LOGO_BOTTOM_LT_CHI_M2 = "hkt_zh_300.jpg";
	private static String COMPANY_LOGO_BOTTOM_LT_ENG_M2= "hkt_en_300.jpg";
	
	private static String COMPANY_LOGO_FILE_CHI = "hkt_ch2_bw.png";
	private static String COMPANY_LOGO_FILE_ENG = "hkt_en2_bw.png";
	private static String COMPANY_BOTTOM_LOGO_FILE_CHI = "pccwlogo_ch.png";
	private static String COMPANY_BOTTOM_LOGO_FILE_ENG= "pccwlogo_en.png";
	private static String COMPANY_CHOP_FILE = "hkt_logo.png";
	private static String IGUARD_DIRECT_LOGO_FILE = "iGuard_direct_logo.jpg";
	private static String IGUARD_PHONEPROTECTOR_LOGO_FILE = "HKTCare_logo.jpg";
	private static String MOBILE_SAFETY_PHONE_FILE = "mobile_safety_phone.jpg";
	private static String OCT_CONSENT_FORM_P1_CHI = "oct_consent_chi_1.jpg"; //Athena 20130924
	private static String OCT_CONSENT_FORM_P2_CHI = "oct_consent_chi_2.jpg";//Athena 20130924
	private static String OCT_CONSENT_FORM_P1_ENG = "oct_consent_eng_1.jpg";//Athena 20130924
	private static String OCT_CONSENT_FORM_P2_ENG = "oct_consent_eng_2.jpg";//Athena 20130924
	private static String TNG_TOP_LOGO = "tng_logo.jpg";
	private static String TNG_BOTTOM_LT_LOGO = "tng_bottom_lt_logo.jpg";
	private static String TNG_BOTTOM_RT_LOGO = "tng_bottom_rt_logo.jpg";
	
	private static String FORM_ZH_EXT = "_zh";
	private static String RPT_LANG_ZH = "zh";
	private static String CUST_COPY = "_cust_copy";
	
	private static String iGuardCareCashTNGzh = "careCash_tnc_zh.pdf";
	private static String iGuardCareCashTNGen = "careCash_tnc_en.pdf";
	private static String iGuardCareCashPICSzh = "careCash_pics_zh.pdf";
	private static String iGuardCareCashPICSen = "careCash_pics_en.pdf";
	
	/*private static String JASPER_PATH_MOB = "mob/";
	private static String JASPER_PATH_CCS = "mob/ccs/";*/
	
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	private String appEnv;
	private String serverPath;

	private DeliveryDAO deliveryDao;
	private CodeLkupDAO codeLkupDao;
	private DisplayLkupDAO displayLkupDAO;
	private OrderDAO orderDao;
	private MobCcsSupportDocDAO mobCcsSupportDocDAO;
	private PaymentUpfrontDAO paymentUpfrontDAO;
	private HSTradeDescDAO hsTradeDescDAO;
	private VasDetailDAO vasDetailDao;
	private DepositService depositService;
	private VasDetailService vasDetailService;
	private CodeLkupService codeLkupService;
	private OrderService orderService;
	private String pdfFilePath;
	private MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService;
	private StaffInfoDAO staffInfoDao;
	private String mobPdfFilePath;
	private IGuardDAO iGuardDAO;
	
	private ReportService reportService;
	private Map<String, String> m2Mapping = null;
	
	private GenericReportHelper genericReportHelper;
	private ItemDisplayService itemDisplayService;
	
	public MobCcsReportServiceImpl() {
		
	}
	
	public DeliveryDAO getDeliveryDao() {
		return deliveryDao;
	}
	public void setDeliveryDao(DeliveryDAO deliveryDao) {
		this.deliveryDao = deliveryDao;
	}

	public CodeLkupDAO getCodeLkupDao() {
		return codeLkupDao;
	}
	public void setCodeLkupDao(CodeLkupDAO codeLkupDao) {
		this.codeLkupDao = codeLkupDao;
	}

	public DisplayLkupDAO getDisplayLkupDAO() {
		return displayLkupDAO;
	}
	public void setDisplayLkupDAO(DisplayLkupDAO displayLkupDAO) {
		this.displayLkupDAO = displayLkupDAO;
	}

	public OrderDAO getOrderDao() {
		return orderDao;
	}
	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public MobCcsSupportDocDAO getMobCcsSupportDocDAO() {
		return mobCcsSupportDocDAO;
	}
	public void setMobCcsSupportDocDAO(MobCcsSupportDocDAO mobCcsSupportDocDAO) {
		this.mobCcsSupportDocDAO = mobCcsSupportDocDAO;
	}

	public PaymentUpfrontDAO getPaymentUpfrontDAO() {
		return paymentUpfrontDAO;
	}
	public void setPaymentUpfrontDAO(PaymentUpfrontDAO paymentUpfrontDAO) {
		this.paymentUpfrontDAO = paymentUpfrontDAO;
	}
	public HSTradeDescDAO getHsTradeDescDAO() {
		return hsTradeDescDAO;
	}
	public void setHsTradeDescDAO(HSTradeDescDAO hsTradeDescDAO) {
		this.hsTradeDescDAO = hsTradeDescDAO;
	}
	public VasDetailDAO getVasDetailDao() {
		return vasDetailDao;
	}
	public void setVasDetailDao(VasDetailDAO vasDetailDao) {
		this.vasDetailDao = vasDetailDao;
	}
	
	
	public DepositService getDepositService() {
		return depositService;
	}
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	public String getImageFilePath() {
		return imageFilePath;
	}
	public void setImageFilePath(String imageFilePath) {
		logger.debug("imageFilePath=" + imageFilePath);
		this.imageFilePath = imageFilePath;
	}
	
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}
	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	public ReportService getReportService() {
		return reportService;
	}
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
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

	public MobCcsPaymentUpfrontService getMobCcsPaymentUpfrontService() {
		return mobCcsPaymentUpfrontService;
	}

	public void setMobCcsPaymentUpfrontService(MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService) {
		this.mobCcsPaymentUpfrontService = mobCcsPaymentUpfrontService;
	}
	
	public StaffInfoDAO getStaffInfoDao() {
		return staffInfoDao;
	}

	public void setStaffInfoDao(StaffInfoDAO staffInfoDao) {
		this.staffInfoDao = staffInfoDao;
	}
	
	public IGuardDAO getiGuardDAO() {
		return iGuardDAO;
	}
	public void setiGuardDAO(IGuardDAO iGuardDAO) {
		this.iGuardDAO = iGuardDAO;
	}
	
	public GenericReportHelper getGenericReportHelper() {
		return genericReportHelper;
	}

	public void setGenericReportHelper(GenericReportHelper genericReportHelper) {
		this.genericReportHelper = genericReportHelper;
	}

	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}

	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}

	static {
		initSSOffCopySeq();
	}
	static {
		initSSCustCopySeq();
	}
	static {
		initConciergeSeq();
	}
	static {
		initIGuardCustCopySeq();
	}
	static {
		initServGuideCopySeq();
	}
	static {
		initIPhoneTradeInFormOffSeq();
	}
	static {
		initIPhoneTradeInFormCustSeq();
	}
	
	/**
	 * S&S Office Copy - Jasper template collection<br>
	 * Mobile Eng, Chi Form<br>
	 * NE Eng, Chi Form
	 */
	private static void initSSOffCopySeq() {
		if (ssOffCopySeq == null) {
			ssOffCopySeq = new ArrayList<String>();
			ssOffCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE);
			ssOffCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE + FORM_ZH_EXT);
			ssOffCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_CY);
			ssOffCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_CY + FORM_ZH_EXT);
			ssOffCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_UNI);
			ssOffCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_UNI + FORM_ZH_EXT);
			ssOffCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_NE);
			ssOffCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_NE + FORM_ZH_EXT);
		}
	}
	
	/**
	 * S&S Customer Copy - Jasper template collection<br>
	 * Mobile Eng, Chi Cust Form<br>
	 * NE Eng, Chi Cust Form
	 */
	private static void initSSCustCopySeq() {
		if (ssCustCopySeq == null) {
			ssCustCopySeq = new ArrayList<String>();
			ssCustCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE + CUST_COPY);
			ssCustCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE + FORM_ZH_EXT	+ CUST_COPY);
			ssCustCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_CY + CUST_COPY);
			ssCustCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_CY + FORM_ZH_EXT	+ CUST_COPY);
			ssCustCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_UNI + CUST_COPY);
			ssCustCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_UNI + FORM_ZH_EXT	+ CUST_COPY);
			ssCustCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_NE + CUST_COPY);
			ssCustCopySeq.add(RptAppMobileServDTO.JASPER_TEMPLATE_NE + FORM_ZH_EXT + CUST_COPY);
		}
	}
	
	/**
	 * Concierge - Jasper template collection<br>
	 * Concierge Eng, Chi Form
	 */
	private static void initConciergeSeq() {
		if (conciergeSeq == null) {
			conciergeSeq = new ArrayList<String>();
			conciergeSeq.add(RptConciergeServiPadiPhoneDTO.JASPER_TEMPLATE);
			conciergeSeq.add(RptConciergeServiPadiPhoneDTO.JASPER_TEMPLATE + FORM_ZH_EXT);
		}
	}
	
	/**
	 * I-Guard - Jasper template collection<br>
	 * I-Guard Cust Copy & TnC
	 */
	private static void initIGuardCustCopySeq() {
		if (reportIGuardCustCopySeq == null) {
			reportIGuardCustCopySeq = new ArrayList<String>();
			reportIGuardCustCopySeq.add(RptIGuardCareCashDTO.JASPER_TEMPLATE + CUST_COPY);
			reportIGuardCustCopySeq.add(IGuardDTO.JASPER_TEMPLATE_LDS + CUST_COPY);
			reportIGuardCustCopySeq.add(IGuardDTO.JASPER_TEMPLATE_AD + CUST_COPY);
			reportIGuardCustCopySeq.add(IGuardDTO.JASPER_TEMPLATE_TNC_LDS);
			reportIGuardCustCopySeq.add(IGuardDTO.JASPER_TEMPLATE_TNC_AD);
		}
	}
	
	private static void initServGuideCopySeq() {
		if (servGuideCopySeq == null) {
			servGuideCopySeq = new ArrayList<String>();
			servGuideCopySeq.add(RptServiceGuideDTO.JASPER_TEMPLATE);
			servGuideCopySeq.add(RptServiceGuideDTO.JASPER_TEMPLATE + FORM_ZH_EXT);
			servGuideCopySeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_NE);
			servGuideCopySeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_NE + FORM_ZH_EXT);
			servGuideCopySeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_Uni);
			servGuideCopySeq.add(RptServiceGuideDTO.JASPER_TEMPLATE_Uni+ FORM_ZH_EXT);
		}
	}
	
	private static void initIPhoneTradeInFormOffSeq() {
		if (iPhoneTradeInFormOffSeq == null) {
			iPhoneTradeInFormOffSeq = new ArrayList<String>();
			iPhoneTradeInFormOffSeq.add(IPhoneTradeInFormDTO.JASPER_TEMPLATE);
			iPhoneTradeInFormOffSeq.add(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8);
		}
	}
	
	private static void initIPhoneTradeInFormCustSeq() {
		if (iPhoneTradeInFormCustSeq == null) {
			iPhoneTradeInFormCustSeq = new ArrayList<String>();
			iPhoneTradeInFormCustSeq.add(IPhoneTradeInFormDTO.JASPER_TEMPLATE+ CUST_COPY);
		}
	}

	/* Athena 20131111 online sales report (start) */
	private ArrayList<InputStream> generateHsTradeDescByItemCode(List<HSTradeDescDTO> hstd_DTOs,String pLang,String orderId, String brand){
		try{
			if (hstd_DTOs != null) {
				HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();
				ArrayList<ReportDTO> hsTradeDescList = new ArrayList<ReportDTO>(); 
				RptHSTradeDescDTO rptHSTradeDescDTOTemplate = new RptHSTradeDescDTO();
				for (HSTradeDescDTO hsTradeDescDto: hstd_DTOs) {
					RptHSTradeDescDTO rptHSTradeDescDTO = new RptHSTradeDescDTO();
					if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
						rptHSTradeDescDTOTemplate.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
						rptHSTradeDescDTOTemplate.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
					} else {
						rptHSTradeDescDTOTemplate.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
						rptHSTradeDescDTOTemplate.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
					}
					if (orderId!= null)
						rptHSTradeDescDTOTemplate.setAgreementNum(orderId);
					else
						rptHSTradeDescDTOTemplate.setAgreementNum("N/A");
					BeanUtils.copyProperties(rptHSTradeDescDTO, rptHSTradeDescDTOTemplate);
					BeanUtils.copyProperties(rptHSTradeDescDTO, hsTradeDescDto);
					hsTradeDescList.add(rptHSTradeDescDTO);
				}
				
				map.put(RptHSTradeDescDTO.JASPER_TEMPLATE, new JasperReport(
						RptHSTradeDescDTO.JASPER_TEMPLATE, hsTradeDescList));
				
				ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();
				
				JasperReport keyValue = null;
				keyValue = map == null? null : map.get(RptHSTradeDescDTO.JASPER_TEMPLATE);
				int i = 0 ; 
				if (keyValue != null) {
					for (HSTradeDescDTO hsTradeDesc : hstd_DTOs) {
							ArrayList<InputStream> hsTradeDescForm = new ArrayList<InputStream>();
							if (StringUtils.isNotEmpty(hsTradeDesc.getPisPath())) {
								hsTradeDescForm.add(new FileInputStream(mobPdfFilePath + "/pis/" + hsTradeDesc.getPisPath()));
								pdfStreamList.addAll(hsTradeDescForm);
								i ++ ;
							} else {
								ArrayList<ReportDTO> rptDtoArrList = new ArrayList<ReportDTO>();
								rptDtoArrList.add(keyValue.getRptDtoArrList().get(i));
								JasperReport hsTradeForm  = new JasperReport(RptHSTradeDescDTO.JASPER_TEMPLATE,rptDtoArrList);
								pdfStreamList.add(generatePdfInputStream(keyValue.getJasperTemplateName(),
										hsTradeForm.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB, pLang,
										orderId, brand));
								i ++ ;
							}
					}
				}
					/*pdfStreamList.add(generatePdfInputStream(
							keyValue.getJasperTemplateName(),
							keyValue.getRptDtoArrList(),  BomWebPortalConstant.JASPER_PATH_MOB,pLang, orderId, brand));*/
				return pdfStreamList;
				
				}
			return null;
			} catch (Exception de) {
				logger.error("Exception caught in generateHsTradeDescByItemCode()", de);
				throw new AppRuntimeException(de);
			}
		
	}
	public void generatePISform(List<HSTradeDescDTO> hstd_DTOs, OutputStream pOutputStream, String pLang,String orderId, String brand){
		try{

				ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();
				pdfStreamList.addAll(generateHsTradeDescByItemCode(hstd_DTOs,pLang,orderId, brand));

				
				FastByteArrayOutputStream baosMerged = new FastByteArrayOutputStream();
				PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, null, null, null, null, null);
	
				byte[] pdfData = baosMerged.getByteArray();
	
				pOutputStream.write(pdfData);
				pOutputStream.flush();
	
				baosMerged.close();
				
			} catch (Exception de) {
				logger.error("Exception caught in generatePISform()", de);
				throw new AppRuntimeException(de);
			}
		
	}
	private HashMap<String, JasperReport> mapStsDeliveryReportData(
			ArrayList<Object> pDTOs, String pLang,Double paid,Double totalPayment, List<String> SmNoList)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("mapStsDeliveryReportData is called in MobCcsReportServiceImpl.java");
		// Map form name with data set
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();

		try {
			// Delivery Note (DN)
			ArrayList<ReportDTO> dnList = new ArrayList<ReportDTO>();
			ArrayList<ReportDTO> dnCustCopyList = new ArrayList<ReportDTO>();
						
			// Create a new form dto so as to input data
			StsDeliveryNoteDTO OsDNDTO = new StsDeliveryNoteDTO();
			StsDeliveryNoteDTO OsDNCustCopyDTO = new StsDeliveryNoteDTO();
			BasketDTO tempBasketDTO = new BasketDTO();
			
			// Copy necessary dto(s) into whole DN report dto
			Double totalDeliveryCharge = 0.0;
			String deliveryChargeInd = "N";
			Object tempObj = null;
			
			for (int i = 0; i < pDTOs.size(); i++) {

				tempObj = pDTOs.get(i);
				if (tempObj instanceof OrderDTO) {
					OsDNDTO.setOrderDto((OrderDTO) tempObj);
					OsDNDTO.setAppInDateStr(Utility.date2String(((OrderDTO) tempObj).getAppInDate(),
																	BomWebPortalConstant.DATE_FORMAT));
					String orderId = ((OrderDTO) tempObj).getOrderId();
					if (vasDetailDao.isYahooCoupon(orderId)) {
						OsDNDTO.setPaymentMethod("Yahoo Coupon");
					}

					List<CodeLkupDTO> posItemCdList=codeLkupService.getCodeLkupDTOALL("ST_DELIVERY_CHARGE_POS_ITEM_CD");
					for (CodeLkupDTO posItemCd : posItemCdList){
						double deliveryCharge =0.0; 
						deliveryCharge = mobCcsPaymentUpfrontService.getStsDeliveryChargeByOrderId(((OrderDTO) tempObj).getOrderId(), posItemCd.getCodeId());
						totalDeliveryCharge += deliveryCharge;
					}
					
					if (totalDeliveryCharge.intValue() > 0){
						deliveryChargeInd = "Y";	
					}
					OsDNDTO.setDeliveryPayment(totalDeliveryCharge);
					OsDNDTO.setDeliveryPaymentInd(deliveryChargeInd);
					
					MobCcsMagentoCouponDTO magentoCouponDTO = mobCcsPaymentUpfrontService.getOrderCouponInfo(orderId);
					
					if (magentoCouponDTO !=null){
						OsDNDTO.setCouponNum(magentoCouponDTO.getCouponNum());
						OsDNDTO.setCouponFaceVal(magentoCouponDTO.getFaceVal());
					}
					
					
				} else if (tempObj instanceof DeliveryUI) {
					if (tempObj != null) {
						
						OsDNDTO.setDeliveryUi((DeliveryUI) tempObj);
						logger.debug("Delivery single line address : "
								+ ((DeliveryUI) tempObj).getSingleLineAddress());

						String collectionType = "";
						if ("D".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
							collectionType = "Delivery by Courier";
							OsDNDTO.setDeliveryAddress(((DeliveryUI) tempObj).getSingleLineAddress());

						} else if ("P".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
							collectionType = "Pick Up at Centre";
							OsDNDTO.setDeliveryAddress(codeLkupDao.getCodeDesc(
													BomWebPortalCcsConstant.DELIVERY_PICKCENTER_LOOKUP_DESC,
													((DeliveryUI) tempObj).getDeliveryCentre()));
						}

						if (StringUtils.isNotEmpty(((DeliveryUI) tempObj).getLocation())) {
							String locDesc = codeLkupDao.getCodeDesc("STOCK_LOC", ((DeliveryUI) tempObj).getLocation());
							if (StringUtils.isNotEmpty(locDesc)) {
								collectionType += " / " + locDesc;
							}
						}
						String DeliveryDateAndTimeSlotStr = "";
						String timeSlotCode = ((DeliveryUI) tempObj).getDeliveryTimeSlot();
						String timeSlot[] = null;

						if (((DeliveryUI) tempObj).getDeliveryDateStr() != null
								&& !"".equals(((DeliveryUI) tempObj).getDeliveryDateStr())) {
							DeliveryDateAndTimeSlotStr += ((DeliveryUI) tempObj).getDeliveryDateStr();
						}
						if (StringUtils.isNotBlank(timeSlotCode)) {
							timeSlot = deliveryDao.getTimeSlotDesc(timeSlotCode);

							if (timeSlot != null) {
								DeliveryDateAndTimeSlotStr += " " + timeSlot[0] + "-" + timeSlot[1];
							}
						}
						OsDNDTO.setDeliveryDateAndTimeSlot(DeliveryDateAndTimeSlotStr);
					}
				} else if (tempObj instanceof ContactDTO) {
					OsDNDTO.setCustName(((ContactDTO) tempObj).getTitle()+" "+((ContactDTO) tempObj).getContactName());
					OsDNDTO.setContactNum(((ContactDTO) tempObj).getContactPhone());
					
				} else if (tempObj instanceof StaffInfoUI) {
					OsDNDTO.setStaffInfoUi((StaffInfoUI) tempObj);
				} else if (tempObj instanceof PaymentUpFrontUI) {
					if ("M".equalsIgnoreCase(((PaymentUpFrontUI) tempObj).getPayMethodType())) {
						if(!"Yahoo Coupon".equalsIgnoreCase(OsDNDTO.getPaymentMethod())) {
							OsDNDTO.setPaymentMethod("Cash");
						}
					} else if ("C".equalsIgnoreCase(((PaymentUpFrontUI) tempObj).getPayMethodType())) {
						OsDNDTO.setPaymentMethod("Credit Card");
					} else {
						StringBuilder sb = new StringBuilder("Credit Card Installment");

						if (StringUtils.isNotBlank(((PaymentUpFrontUI) tempObj).getCcInstSchedule())) {
							sb.append(" (" + ((PaymentUpFrontUI) tempObj).getCcInstSchedule() + ")");
						}

						OsDNDTO.setPaymentMethod(sb.toString());
						
					}
					OsDNDTO.setPaymentUpFrontUi((PaymentUpFrontUI) tempObj);
				} else if (tempObj instanceof BasketDTO) {
					BeanUtils.copyProperties(tempBasketDTO, (BasketDTO) tempObj);
					OsDNDTO.setBasketDto(tempBasketDTO);
				}
				else if (tempObj instanceof StaffInfoUI) {
					OsDNDTO.setStaffInfoUi((StaffInfoUI) tempObj);
				} 
				else if (tempObj instanceof List<?>) {
					if (((List)tempObj).size() > 0 && ((List)tempObj).get(0) instanceof StockDTO){
							OsDNDTO.setStockList((List<StockDTO>) tempObj);
						}
				
				} else if (tempObj instanceof Double) {
					OsDNDTO.setOsBalance((Double) tempObj);
				} 

			}
			OsDNDTO.setPaidAmt((Double)paid);
			OsDNDTO.setTotalPayment((Double)totalPayment);
			String SmNoString = new String();
			if (SmNoList != null && !SmNoList.isEmpty()) {
				for (String mem : SmNoList) {
						SmNoString = SmNoString+mem+"/";
				}
				SmNoString=SmNoString.substring(0,SmNoString.length()-1);
				OsDNDTO.setSmNo(SmNoString);
			}

			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				OsDNDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
				OsDNDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				OsDNDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
				OsDNDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
			OsDNDTO.setCustomerCopyInd("N");

			dnList.add(OsDNDTO);

			// copy set of data from original to cust copy & set cust copy to
			// "Y"
			BeanUtils.copyProperties(OsDNCustCopyDTO, OsDNDTO);
			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				OsDNCustCopyDTO.setCompanyLogo(imageFilePath + "/"
						+ COMPANY_LOGO_FILE_CHI);
				OsDNCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/"
						+ COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				OsDNCustCopyDTO.setCompanyLogo(imageFilePath + "/"
						+ COMPANY_LOGO_FILE_ENG);
				OsDNCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/"
						+ COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
			OsDNCustCopyDTO.setCustomerCopyInd("Y");

			dnCustCopyList.add(OsDNCustCopyDTO);

			String key_DN = "";
			key_DN = StsDeliveryNoteDTO.JASPER_TEMPLATE_DN;

			map.put(key_DN, new JasperReport(key_DN, dnList));
			map.put(key_DN + CUST_COPY, new JasperReport(key_DN, dnCustCopyList));

		} catch (Exception e) {
			logger.error("Exception caught in mapCCSDeliveryReportData() ", e);
			throw new AppRuntimeException(e);
		}
		return map;
	}
	private ArrayList<InputStream> getPDFStsDeliveryFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy,Double paid,Double totalPayment,List<String> SmNoList, String osOrderId, String brand) {

		logger.info("getPDFOsDeliveryFromStreamList is called");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			HashMap<String, JasperReport> reportDataMap = mapStsDeliveryReportData(
					pReportArrList, pLang, paid,totalPayment,SmNoList);

			String key = isCustCopy?
							StsDeliveryNoteDTO.JASPER_TEMPLATE_DN + CUST_COPY
							: StsDeliveryNoteDTO.JASPER_TEMPLATE_DN;

			JasperReport keyValue = reportDataMap == null? null : reportDataMap.get(key);

			if (keyValue != null) {
				pdfStreamList.add(generatePdfInputStream(
						keyValue.getJasperTemplateName(),
						keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_CCS, pLang, osOrderId, brand));
				
				//add the new form for SalesMemo
				File DeliveryNote = new File ("");
				if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
					
						DeliveryNote =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.SalesMemo_1010);
				}
				else{
					
						DeliveryNote =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.SalesMemo_csl);
					
				}
				ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
				ServiceGuide.add(new FileInputStream(DeliveryNote));

				pdfStreamList.addAll(ServiceGuide);
			}

			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFStsDeliveryFromStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	public void generateStsDeliveryNote(ArrayList<Object> StsDNReportArrList, OutputStream pOutputStream,String pLang, boolean previewInd,Double paid,Double totalPayment,List<String> SmNoList,String orderId, boolean custCopy, String brand){
		try{

				ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();
				if (custCopy){
					pdfStreamList.addAll(getPDFStsDeliveryFromStreamList(StsDNReportArrList, pLang, false,paid,totalPayment,SmNoList, orderId, brand));					
					pdfStreamList.addAll(getPDFStsDeliveryFromStreamList(StsDNReportArrList, pLang, true,paid,totalPayment,SmNoList, orderId, brand));
					//if ("Y".equalsIgnoreCase(giftPackInd)) pdfStreamList.addAll(generateStsGiftPackMessage(StsDNReportArrList, pLang, false, orderId, giftOptionDTO));
				}else{
					pdfStreamList.addAll(getPDFStsDeliveryFromStreamList(StsDNReportArrList, pLang, false,paid,totalPayment,SmNoList, orderId, brand));
					
					
				}
				Object tempObj = null;
				for (int i = 0; i < StsDNReportArrList.size(); i++) {

					tempObj = StsDNReportArrList.get(i);
					if (tempObj instanceof ArrayList) {
						List<StockDTO> stkDtoList = (List<StockDTO>) tempObj;
						List<HSTradeDescDTO> hstdDTOs = new ArrayList<HSTradeDescDTO>();
						for (StockDTO mem: stkDtoList) {
							HSTradeDescDTO hsDesc =getHSTradeDescByItemCode(mem.getItemCode());
							if(hsDesc!= null){
								hstdDTOs.add(hsDesc);
							}
						}
						if (hstdDTOs.size()>0)
							pdfStreamList.addAll(generateHsTradeDescByItemCode(hstdDTOs,pLang,orderId, brand));
					}
				}
				
				
				FastByteArrayOutputStream baosMerged = new FastByteArrayOutputStream();
				
				if (previewInd) {
					PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, "DRAFT", null, null, null, null);
				} else {
					PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, null, null, null, null, null);
				}
				byte[] pdfData = baosMerged.getByteArray();
	
				pOutputStream.write(pdfData);
				pOutputStream.flush();
	
				baosMerged.close();
				
			} catch (Exception de) {
				logger.error("Exception caught in generateOsDeliveryNote()", de);
				throw new AppRuntimeException(de);
			}
		
	}
	private HashMap<String, JasperReport> mapStsDOADeliveryReportData(ArrayList<Object> pDTOs, String pLang,List<String> SmNoList)
			throws InvocationTargetException, IllegalAccessException {

	logger.info("mapStsDOADeliveryReportData is called in MobCcsReportServiceImpl.java");
	// Map form name with data set
	HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();

	try {
		// Delivery Note (DN)
		ArrayList<ReportDTO> dnList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> dnCustCopyList = new ArrayList<ReportDTO>();

		// Create a new form dto so as to input data
		StsDOADeliveryNoteDTO stsDOADNDTO = new StsDOADeliveryNoteDTO();
		StsDOADeliveryNoteDTO stsDOADNCustCopyDTO = new StsDOADeliveryNoteDTO();

		// Copy necessary dto(s) into whole DN report dto
		Object tempObj = null;
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);

			if (tempObj instanceof OrderDTO) {
				stsDOADNDTO.setOrderDto((OrderDTO) tempObj);
				stsDOADNDTO.setAppInDateStr(Utility.date2String(((OrderDTO) tempObj).getAppInDate(),
																	BomWebPortalConstant.DATE_FORMAT));

			} else if (tempObj instanceof ContactDTO) {
				stsDOADNDTO.setCustName(((ContactDTO) tempObj).getTitle()+" "+((ContactDTO) tempObj).getContactName());
				stsDOADNDTO.setContactNum(((ContactDTO) tempObj).getContactPhone());
				
			} else if (tempObj instanceof DeliveryUI) {
				if (tempObj != null) {
					stsDOADNDTO.setDeliveryUi((DeliveryUI) tempObj);
					logger.debug("Delivery single line address : "	+ ((DeliveryUI) tempObj).getSingleLineAddress());

					String collectionType = "";
					if ("D".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
						collectionType = "Delivery by Courier";
						stsDOADNDTO.setDeliveryAddress(((DeliveryUI) tempObj).getSingleLineAddress());
					} else if ("P".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
						collectionType = "Pick Up at Centre";
						if ((((DeliveryUI) tempObj).getDeliveryCentre() != null)
								&& !((DeliveryUI) tempObj).getDeliveryCentre().isEmpty()) {
							collectionType += " / " + ((DeliveryUI) tempObj).getDeliveryCentre();
						}

						stsDOADNDTO.setDeliveryAddress(codeLkupDao.getCodeDesc(
												BomWebPortalCcsConstant.DELIVERY_PICKCENTER_LOOKUP_DESC,
												((DeliveryUI) tempObj).getDeliveryCentre()));
					}
					
					String locDesc = codeLkupDao.getCodeDesc("STOCK_LOC", ((DeliveryUI) tempObj).getDeliveryCentre());
					if (StringUtils.isNotEmpty(locDesc)) {
						collectionType += " / " + locDesc;
					}

					String DeliveryDateAndTimeSlotStr = "";
					String timeSlotCode = ((DeliveryUI) tempObj).getDeliveryTimeSlot();
					String timeSlot[] = null;

					if (StringUtils.isNotBlank(((DeliveryUI) tempObj).getDeliveryDateStr())) {
						DeliveryDateAndTimeSlotStr += ((DeliveryUI) tempObj).getDeliveryDateStr();
					}

					if (timeSlotCode != null && !"".equals(timeSlotCode)) {
						timeSlot = deliveryDao.getTimeSlotDesc(timeSlotCode);

						if (timeSlot != null) {
							DeliveryDateAndTimeSlotStr += " " + timeSlot[0] + "-" + timeSlot[1];
						}
					}
					stsDOADNDTO.setDeliveryDateAndTimeSlot(DeliveryDateAndTimeSlotStr);
				}
			} else if (tempObj instanceof StaffInfoUI) {
				stsDOADNDTO.setStaffInfoUi((StaffInfoUI) tempObj);
/*			} else if (tempObj instanceof BasketDTO) {
				mobCcsDOADNDTO.setBasketDto((BasketDTO) tempObj);*/
			} 	
			else if (tempObj instanceof List<?>) {
				if (((List)tempObj).size() > 0 && ((List)tempObj).get(0) instanceof StockDTO){
						stsDOADNDTO.setDoaStockList((List<StockDTO>) tempObj);
					}
			}
			
		}
		String SmNoString = new String();
		if (SmNoList != null && !SmNoList.isEmpty()) {
			for (String mem : SmNoList) {
					SmNoString = SmNoString+mem+"/";
			}
			SmNoString=SmNoString.substring(0,SmNoString.length()-1);
			stsDOADNDTO.setSmNo(SmNoString);
		}
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			stsDOADNDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			stsDOADNDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
		} else {
			stsDOADNDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			stsDOADNDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
		}
		stsDOADNDTO.setCustomerCopyInd("N");
		
		

		dnList.add(stsDOADNDTO);

		// copy set of data from original to cust copy & set cust copy to
		// "Y"
		BeanUtils.copyProperties(stsDOADNCustCopyDTO, stsDOADNDTO);
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			stsDOADNCustCopyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			stsDOADNCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
		} else {
			stsDOADNCustCopyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			stsDOADNCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
		}
		stsDOADNCustCopyDTO.setCustomerCopyInd("Y");

		dnCustCopyList.add(stsDOADNCustCopyDTO);

		String key_DOADN = "";
		key_DOADN = StsDOADeliveryNoteDTO.JASPER_TEMPLATE_DN;

		map.put(key_DOADN, new JasperReport(key_DOADN, dnList));
		map.put(key_DOADN + CUST_COPY, new JasperReport(key_DOADN, dnCustCopyList));

	} catch (Exception e) {
		logger.error("Exception caught in mapStsDOADeliveryReportData() ", e);
		throw new AppRuntimeException(e);
	}
	return map;
}
	private ArrayList<InputStream> getPDFStsDOADeliveryFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy,List<String> SmNoList, String osOrderId, String brand) {

		logger.info("getPDFStsDOADeliveryFromStreamList is called");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			HashMap<String, JasperReport> reportDataMap = mapStsDOADeliveryReportData(
					pReportArrList, pLang,SmNoList);

			String key = isCustCopy?
							StsDOADeliveryNoteDTO.JASPER_TEMPLATE_DN + CUST_COPY
							: StsDOADeliveryNoteDTO.JASPER_TEMPLATE_DN;

			JasperReport keyValue = reportDataMap == null? null : reportDataMap.get(key);

			if (keyValue != null) {
				pdfStreamList.add(generatePdfInputStream(
						keyValue.getJasperTemplateName(),
						keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_CCS,pLang, osOrderId, brand));
				
				//add the new form for SalesMemo
				File DeliveryNote = new File ("");
				if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
					
						DeliveryNote =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.SalesMemo_1010);
				}
				else{
					
						DeliveryNote =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.SalesMemo_csl);
					
				}
				ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
				ServiceGuide.add(new FileInputStream(DeliveryNote));

				pdfStreamList.addAll(ServiceGuide);
			}

			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFStsDOADeliveryFromStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	public void generateStsDOADeliveryNote(ArrayList<Object> StsDNReportArrList, OutputStream pOutputStream,String pLang,List<String> SmNoList,boolean custCopy, String osOrderId, String brand){
		try{

				ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();
				if (custCopy){
					pdfStreamList.addAll(getPDFStsDOADeliveryFromStreamList(StsDNReportArrList, pLang, false,SmNoList, osOrderId, brand));
					pdfStreamList.addAll(getPDFStsDOADeliveryFromStreamList(StsDNReportArrList, pLang, true,SmNoList, osOrderId, brand));
				}else
				{
					pdfStreamList.addAll(getPDFStsDOADeliveryFromStreamList(StsDNReportArrList, pLang, false,SmNoList, osOrderId, brand));
				}
				FastByteArrayOutputStream baosMerged = new FastByteArrayOutputStream();
				

				PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, null, null, null, null, null);
				
				byte[] pdfData = baosMerged.getByteArray();
	
				pOutputStream.write(pdfData);
				pOutputStream.flush();
	
				baosMerged.close();
				
			} catch (Exception de) {
				logger.error("Exception caught in generateStsDOADeliveryNote()", de);
				throw new AppRuntimeException(de);
			}
		
	}
	public void saveStssDeliveryPdfReports(ArrayList<Object> OsDNReportArrList, 
    		String pLang, boolean previewInd, Double paid,Double totalPayment, List<String> SmNoList,String orderId, String brand) {
		
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String locale = "EN";
		
		if ("zh".equalsIgnoreCase(pLang)) {
			locale = "CHI";
		} else {
			locale = "EN";
		}
		
		File outputPdfPath = new File(getServerPath() + "/" + orderId);
		File outputPdf = new File(getServerPath() + "/" + orderId + "/" + orderId + "_" + locale + ".pdf");
		
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
			this.generateStsDeliveryNote(OsDNReportArrList,fos,pLang,previewInd,paid,totalPayment,SmNoList,orderId, false, brand);
			
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
	}
/* Athena 20131111 online sales report (end) */
	/**
	 * 1. Main function of generating forms (call from controller)<br>
	 * 2. Control the printing sequence of whole pdf
	 * 
	 * @param pReportArrList - RS dto(s) that are needed in the form
	 * @param pCCSReportArrList - CCS dto(s) that are needed in the form
	 * @param pOutputStream
	 * @param pLang - zh (Chinese), en (English)
	 * @param orderId
	 * @param includeForm - Which form is included in pdf<br>
	 * 						S = SSForm, D = Delivery Note, G = Courier Guideline,
	 * 						C = Change of Service, O = DOA Delivery Note,
	 * 						I = I-Guard, M = SSForm W/O other forms (e.g. MNP, Concierge, ...)
	 * 						E = Service Guide
	 * 						P = Mobile Safety Phone
	 * 						F  = Tap & Go Service Form
	 * 						R = Travel Insurance Form
	 * 						H = Helper Insurance Form
	 *                      A = Restart Service Form
	 */
	public void generateCCSPdfReports(ArrayList<Object> pReportArrList,
			ArrayList<Object> pCCSReportArrList, OutputStream pOutputStream,
			String pLang, String orderId, String includeForm, boolean needWatermark, List<String> iGuardType, String brand) {
		try {
			logger.info("generateCCSPdfReports is called");

			ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();
			
			boolean isOnlineSales = false;
			if (StringUtils.contains(orderId, "SBO")) {
				isOnlineSales = true;
			}

			// Only for Mobile Online Sales
			if (includeForm.contains("M")) {
				
				//pdfStreamList.addAll(getPDFKeyInformationSheetFormStreamList(pReportArrList, pLang, true, orderId));//add KIS 
				pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)1, true, isOnlineSales, orderId, brand));
				pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)4, true, isOnlineSales, orderId, brand));
				pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)2, true, isOnlineSales, orderId, brand));
				pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)7, true, isOnlineSales, orderId, brand));//add production information sheet
		
				if (includeForm.contains("ME")) {
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)6, true, isOnlineSales, orderId, brand));
				}
				
			} else{
				//********** Office Copy **********//
				if (includeForm.contains("D")) {
					pdfStreamList.addAll(getPDFDeliveryFromStreamList(pCCSReportArrList, pLang, false, orderId, brand));
				}
				if (includeForm.contains("O")) {
					pdfStreamList.addAll(getPDFDOADeliveryFromStreamList(pCCSReportArrList, pLang, false, orderId, brand));
				}
				
				if (includeForm.contains("G")) {
					pdfStreamList.addAll(getPDFCourierGuidFromStreamList(pCCSReportArrList, pLang, orderId, brand));
				}
				
				if (includeForm.contains("S")) {
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)1, false, isOnlineSales, orderId, brand));
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)8, false, isOnlineSales, orderId, brand));
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)4, false, isOnlineSales, orderId, brand));
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)2, false, isOnlineSales, orderId, brand));
				}
				if (includeForm.contains("C")) {
					pdfStreamList.addAll(getPDFChangeServiceFromStreamList(pCCSReportArrList, pLang, false, orderId, brand));
				}
				if (includeForm.contains("S")) {
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)3, false, isOnlineSales, orderId, brand));
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)5, false, isOnlineSales, orderId, brand));
					//pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)7, false, isOnlineSales));
				}
				if (includeForm.contains("I")) {
					for(int i=0; i<iGuardType.size();i++){
						if("LDS".equalsIgnoreCase(iGuardType.get(i))){
							pdfStreamList.addAll(getPDFIGuardFromStreamList(pReportArrList, pLang, false, orderId ,"LDS", brand));
						}else if("AD".equalsIgnoreCase(iGuardType.get(i))){
							pdfStreamList.addAll(getPDFIGuardFromStreamList(pReportArrList, pLang, false, orderId ,"AD", brand));
						}else if ("CARECASH".equalsIgnoreCase(iGuardType.get(i))){
							pdfStreamList.addAll(getPDFIGuardFromStreamList(pReportArrList, pLang, false, orderId ,"CARECASH", brand));
						}
					}
				}
				if (includeForm.contains(ProjectEagleReportHelper.RESTART_SERVICE_FORM_CCS_FORM_ABBREVIATION)) {
					pdfStreamList.addAll(getPDFRestartServiceFromStreamList(pReportArrList, pLang, orderId, brand));
				}
				if (includeForm.contains(GenericForm.TRAVEL_INSURANCE_FORM.getCcsformAbbreviation())) {
					pdfStreamList.addAll(getPDFGenericFromStreamList(GenericForm.TRAVEL_INSURANCE_FORM, pReportArrList, pLang, orderId, brand));
				}
				if (includeForm.contains(GenericForm.HELPERCARE_INSURANCE_FORM.getCcsformAbbreviation())) {
					pdfStreamList.addAll(getPDFGenericFromStreamList(GenericForm.HELPERCARE_INSURANCE_FORM, pReportArrList, pLang, orderId, brand));
				}
				if (includeForm.contains("P")) {
					pdfStreamList.addAll(getPDFMobileSafetyFromStreamList(pReportArrList, pLang, false, orderId, brand));
				}
				if (includeForm.contains("T")) {
					pdfStreamList.addAll(getPDFTradeInHSFromStreamList(pReportArrList, pLang, false, orderId, brand));
				}
				if (includeForm.contains("N")) {
					pdfStreamList.addAll(getPDFNFCSimFormStreamList(pReportArrList, pLang, false, orderId, brand));
					//pdfStreamList.addAll(getPDFOctopusSimFormStreamList(pReportArrList, pLang, false, orderId));
				}
				
				
				if (includeForm.contains("F")) {
					pdfStreamList.addAll(getPDFTnGServiceFormStreamList(pCCSReportArrList, pLang, false, orderId, brand));
				}
								
				//********** Customer Copy **********//
				if (includeForm.contains("D")) {
					pdfStreamList.addAll(getPDFDeliveryFromStreamList(pCCSReportArrList, pLang, true, orderId, brand));
				}
				if (includeForm.contains("O")) {
					pdfStreamList.addAll(getPDFDOADeliveryFromStreamList(pCCSReportArrList, pLang, true, orderId, brand));
				}
				if (includeForm.contains("S")) {
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)1, true, isOnlineSales, orderId, brand));
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)8, true, isOnlineSales, orderId, brand));
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)4, false, isOnlineSales, orderId, brand));
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)7, true, isOnlineSales, orderId, brand));
				}
				if (includeForm.contains("C")) {
					pdfStreamList.addAll(getPDFChangeServiceFromStreamList(pCCSReportArrList, pLang, true, orderId, brand));
				}
				if (includeForm.contains("I")) {
					for(int i=0; i<iGuardType.size();i++){
						pdfStreamList.addAll(getPDFIGuardFromStreamList(pReportArrList, pLang, true, orderId ,iGuardType.get(i), brand));
					}
				}
				if (includeForm.contains(ProjectEagleReportHelper.RESTART_SERVICE_FORM_CCS_FORM_ABBREVIATION)) {
					pdfStreamList.addAll(getPDFRestartServiceFromStreamList(pReportArrList, pLang, orderId, brand));
				}
				if (includeForm.contains(GenericForm.TRAVEL_INSURANCE_FORM.getCcsformAbbreviation())) {
					pdfStreamList.addAll(getPDFGenericFromStreamList(GenericForm.TRAVEL_INSURANCE_FORM, pReportArrList, pLang, orderId, brand));
				}
				if (includeForm.contains(GenericForm.HELPERCARE_INSURANCE_FORM.getCcsformAbbreviation())) {
					pdfStreamList.addAll(getPDFGenericFromStreamList(GenericForm.HELPERCARE_INSURANCE_FORM, pReportArrList, pLang, orderId, brand));
				}
				if (includeForm.contains("P")) {
					pdfStreamList.addAll(getPDFMobileSafetyFromStreamList(pReportArrList, pLang, true, orderId, brand));
				}
				if (includeForm.contains("T")) {
					pdfStreamList.addAll(getPDFTradeInHSFromStreamList(pReportArrList, pLang, true, orderId, brand));
				}
				if (includeForm.contains("N")) {
					pdfStreamList.addAll(getPDFNFCSimFormStreamList(pReportArrList, pLang, true, orderId, brand));
					//pdfStreamList.addAll(getPDFOctopusSimFormStreamList(pReportArrList, pLang, true, orderId));
				}
			
				if (includeForm.contains("F")) {
					pdfStreamList.addAll(getPDFTnGServiceFormStreamList(pCCSReportArrList, pLang, true, orderId, brand));
				}
				
				//********** Service Guide **********//
				if (includeForm.contains("E")) {
					pdfStreamList.addAll(getPDFSSFromStreamList(pReportArrList, pLang, (short)6, false, isOnlineSales, orderId, brand));
				}
			}

			FastByteArrayOutputStream baosMerged = new FastByteArrayOutputStream();
			
			if (needWatermark) {
				PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, "DRAFT", null, null, null, null);
			} else {
				PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, null, null, null, null, null);
			}

			byte[] pdfData = baosMerged.getByteArray();

			pOutputStream.write(pdfData);
			pOutputStream.flush();

			baosMerged.close();

		} catch (Exception de) {
			logger.error("Exception caught in generateCCSPdfReports()", de);
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
			ArrayList<ReportDTO> dtoArrList, String path,String pLang, String osOrderId, String brand) throws Exception {
		FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
		JRDataSource ds = new JRBeanCollectionDataSource(dtoArrList);
		
		String pReportName = path + pJasperName;
		
		boolean isM2;
		if (osOrderId == null) {
			Calendar c = Calendar.getInstance();
		    Date today = c.getTime();
		    
			if (reportService.isGenerateM2Report(today)) {
				isM2 = true;
			} else {
				isM2 = false;
			}
		} else {
			OrderDTO order = orderService.getOrder(osOrderId);
			if (reportService.isGenerateM2Report(order.getAppInDate())) {
				isM2 = true;
			} else {
				isM2 = false;
			}
		}
		
		
		if (isM2) {
			if (m2Mapping == null) {
				this.m2Mapping = reportService.getM2Mapping();
			}
			
			if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
				if (BomWebPortalConstant.JASPER_PATH_MOB.equalsIgnoreCase(path)) {
					path = BomWebPortalConstant.JASPER_PATH_MOB_1010;
				} else {
					path = BomWebPortalConstant.JASPER_PATH_CCS_1010;
				}
				pReportName = pReportName + "1010/";
			}
			
			if (m2Mapping.containsKey(pJasperName)) {
				pReportName = path + m2Mapping.get(pJasperName);
			} else {
				pReportName = path + pJasperName;
			}
		}
		
		for (ReportDTO rpt : dtoArrList){
			rpt.setLogo(pLang, isM2, brand);
			if (isM2) {
				if (m2Mapping.containsKey(pJasperName)) {
					/*//rpt.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_LOGO_BOTTOM_RT_M2);
					rpt.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_TOP_M2);
					if (RPT_LANG_ZH.equalsIgnoreCase(pLang))
						rpt.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_LOGO_BOTTOM_LT_CHI_M2);
					else
						rpt.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_LOGO_BOTTOM_LT_ENG_M2);*/
					String configFile = "reportConstantM2.xml";
					ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile);
					if ("M2AppMobileServ_zh".equalsIgnoreCase(m2Mapping.get(pJasperName))||"M2AppMobileServ".equalsIgnoreCase(m2Mapping.get(pJasperName))
							||"M2AppMobileServCY_zh".equalsIgnoreCase(m2Mapping.get(pJasperName))||"M2AppMobileServCY".equalsIgnoreCase(m2Mapping.get(pJasperName))){
						
						if (RPT_LANG_ZH.equalsIgnoreCase(pLang)){
								((RptAppMobileServDTO) rpt).setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateChi1").toString());
								((RptAppMobileServDTO) rpt).setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateChi2").toString());
								if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
									((RptAppMobileServDTO) rpt).setCustAgree(appCtx.getBean("ss1010CustAgreeChi").toString());
								} else {
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
								} else {
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
								((RptCreditCardDDAuthDTO) rpt).setCcos7(appCtx.getBean("ccOSState7").toString());
								((RptCreditCardDDAuthDTO) rpt).setCcos8(appCtx.getBean("ccOSState8").toString());
							}else{
								((RptCreditCardDDAuthDTO) rpt).setCcos7(appCtx.getBean("ccOSState7").toString());
								((RptCreditCardDDAuthDTO) rpt).setCcos8(appCtx.getBean("ccOSState8").toString());
							}					
					}
					if ("M2CreditCardDDAuthorization_os".equalsIgnoreCase(m2Mapping.get(pJasperName))){
						
						if (RPT_LANG_ZH.equalsIgnoreCase(pLang)){
								((RptCreditCardDDAuthOSDTO) rpt).setCcos7(appCtx.getBean("ccOSState7").toString());
								((RptCreditCardDDAuthOSDTO) rpt).setCcos8(appCtx.getBean("ccOSState8").toString());
							}else{
								((RptCreditCardDDAuthOSDTO) rpt).setCcos7(appCtx.getBean("ccOSState7").toString());
								((RptCreditCardDDAuthOSDTO) rpt).setCcos8(appCtx.getBean("ccOSState8").toString());
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
		logger.info("pReportName = " + pReportName);

		ReportUtil.generatePdfReport(pReportName, ds, baos);
		baos.close();

		return baos.getInputStream();
	}

	/**
	 * Assign delivery report data to specific report. Size of Map return should be same
	 * of number of report template generate.
	 */
	private HashMap<String, JasperReport> mapCCSDeliveryReportData(
			ArrayList<Object> pDTOs, String pLang, String brand)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("mapCCSDeliveryReportData is called in MobCcsReportServiceImpl.java");
		// Map form name with data set
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();

		try {
			// Delivery Note (DN)
			ArrayList<ReportDTO> dnList = new ArrayList<ReportDTO>();
			ArrayList<ReportDTO> dnCustCopyList = new ArrayList<ReportDTO>();

			// Create a new form dto so as to input data
			MobCcsDeliveryNoteDTO mobCcsDNDTO = new MobCcsDeliveryNoteDTO();
			MobCcsDeliveryNoteDTO mobCcsDNCustCopyDTO = new MobCcsDeliveryNoteDTO();
			BasketDTO tempBasketDTO = new BasketDTO();
			List<MultiSimInfoDTO> multiSimInfoDTOs= new ArrayList<MultiSimInfoDTO>();
			// Copy necessary dto(s) into whole DN report dto
			Object tempObj = null;
			Double paid = 0.0;
			for (int i = 0; i < pDTOs.size(); i++) {

				tempObj = pDTOs.get(i);

				if (tempObj instanceof OrderDTO) {
					mobCcsDNDTO.setOrderDto((OrderDTO) tempObj);
					mobCcsDNDTO.setAppInDateStr(Utility.date2String(((OrderDTO) tempObj).getAppInDate(),
																	BomWebPortalConstant.DATE_FORMAT));
					String orderId = ((OrderDTO) tempObj).getOrderId();
					if (vasDetailDao.isYahooCoupon(orderId)) {
						mobCcsDNDTO.setPaymentMethod("Yahoo Coupon");
					}
					if (StringUtils.isNotEmpty(((OrderDTO) tempObj).getPaidAmt())) {
						paid = Double.parseDouble(((OrderDTO) tempObj).getPaidAmt());
					}
				} else if (tempObj instanceof MnpDTO) {
					mobCcsDNDTO.setMnpDto((MnpDTO) tempObj);
				} else if (tempObj instanceof DeliveryUI) {
					if (tempObj != null) {
						mobCcsDNDTO.setDeliveryUi((DeliveryUI) tempObj);
						logger.debug("Delivery single line address : "
								+ ((DeliveryUI) tempObj).getSingleLineAddress());

						String collectionType = "";
						if ("D".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
							collectionType = "Delivery by Courier";
							mobCcsDNDTO.setDeliveryAddress(((DeliveryUI) tempObj).getSingleLineAddress());

						} else if ("P".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
							collectionType = "Pick Up at Centre";
							mobCcsDNDTO.setDeliveryAddress(codeLkupDao.getCodeDesc(
													BomWebPortalCcsConstant.DELIVERY_PICKCENTER_LOOKUP_DESC,
													((DeliveryUI) tempObj).getDeliveryCentre()));
						}

						if (StringUtils.isNotEmpty(((DeliveryUI) tempObj).getLocation())) {
							String locDesc = codeLkupDao.getCodeDesc("STOCK_LOC", ((DeliveryUI) tempObj).getLocation());
							if (StringUtils.isNotEmpty(locDesc)) {
								collectionType += " / " + locDesc;
							}
						}

						mobCcsDNDTO.setCollectionType(collectionType);

						String DeliveryDateAndTimeSlotStr = "";
						String timeSlotCode = ((DeliveryUI) tempObj).getDeliveryTimeSlot();
						String timeSlot[] = null;

						if (((DeliveryUI) tempObj).getDeliveryDateStr() != null
								&& !"".equals(((DeliveryUI) tempObj).getDeliveryDateStr())) {
							DeliveryDateAndTimeSlotStr += ((DeliveryUI) tempObj).getDeliveryDateStr();
						}

						if (StringUtils.isNotBlank(timeSlotCode)) {
							timeSlot = deliveryDao.getTimeSlotDesc(timeSlotCode);

							if (timeSlot != null) {
								DeliveryDateAndTimeSlotStr += " " + timeSlot[0] + "-" + timeSlot[1];
							}
						}
						mobCcsDNDTO.setDeliveryDateAndTimeSlot(DeliveryDateAndTimeSlotStr);
					}
				} else if (tempObj instanceof StaffInfoUI) {
					StaffInfoUI staffInfo = (StaffInfoUI) tempObj;
					mobCcsDNDTO.setStaffInfoUi(staffInfo);

					String hotline = staffInfoDao.getCcsHotline(staffInfo.getSalesId(), brand);
					if (StringUtils.isNotBlank(hotline)){
						mobCcsDNDTO.getStaffInfoUi().setHotLine(hotline);
					}
					
				} else if (tempObj instanceof PaymentUpFrontUI) {
					if ("M".equalsIgnoreCase(((PaymentUpFrontUI) tempObj).getPayMethodType())) {
						if(!"Yahoo Coupon".equalsIgnoreCase(mobCcsDNDTO.getPaymentMethod())) {
							mobCcsDNDTO.setPaymentMethod("Cash");
						}
					} else if ("C".equalsIgnoreCase(((PaymentUpFrontUI) tempObj).getPayMethodType())) {
						mobCcsDNDTO.setPaymentMethod("Credit Card");
					} else {
						StringBuilder sb = new StringBuilder("Credit Card Installment");

						if (StringUtils.isNotBlank(((PaymentUpFrontUI) tempObj).getCcInstSchedule())) {
							sb.append(" (" + ((PaymentUpFrontUI) tempObj).getCcInstSchedule() + ")");
						}

						mobCcsDNDTO.setPaymentMethod(sb.toString());
					}
					mobCcsDNDTO.setPaymentUpFrontUi((PaymentUpFrontUI) tempObj);
				} else if (tempObj instanceof MobileSimInfoDTO) {
					//included in waive reason upfront
					/*if (((MobileSimInfoDTO)tempObj).getSimWaiveReason()!= null && !"".equals(((MobileSimInfoDTO)tempObj).getSimWaiveReason())) {
						simPaymentAmt = ((MobileSimInfoDTO)tempObj).getSimCharge();
					} else {
						simPaymentAmt = 0;
					}*/
				} else if (tempObj instanceof BasketDTO) {
					BeanUtils.copyProperties(tempBasketDTO, (BasketDTO) tempObj);
					mobCcsDNDTO.setBasketDto(tempBasketDTO);
				} else if (tempObj instanceof List<?>) {//MultiSim Athena 20140128
					if (((List)tempObj).size() > 0 && ((List)tempObj).get(0) instanceof StockDTO) {
						mobCcsDNDTO.setStockList((List<StockDTO>) tempObj);
					}
				}else if (tempObj instanceof Double) {
					mobCcsDNDTO.setOsBalance((Double) tempObj);
				} else if (tempObj instanceof String) {
					mobCcsDNDTO.setiGuardRemark((String) tempObj);
				}else if (tempObj instanceof List<?>) {
					if (((List)tempObj).size() > 0 && ((List)tempObj).get(0) instanceof MultiSimInfoDTO) {
						multiSimInfoDTOs= (List<MultiSimInfoDTO>)tempObj;					
					}
				} 
			}
			
			//Adjustment for 2nd Device Installment Payment
			if ((mobCcsDNDTO.getBasketDto() != null) && (mobCcsDNDTO.getOrderDto() != null)) {
				Double vasHSAmt = vasDetailDao.getVasHSAmt(mobCcsDNDTO.getOrderDto().getOrderId());
				Double upfrontAmt = new Double(mobCcsDNDTO.getBasketDto().getUpfrontAmt()) + vasHSAmt;
				Double adminCharge= vasDetailDao.getAdminCharge(mobCcsDNDTO.getOrderDto().getOrderId());//Athena 20130909
				Double too1AdminCharge = vasDetailDao.getMupAdminChargeAmount(mobCcsDNDTO.getOrderDto().getOrderId());
				
				Double prePaymentAmt = new Double(mobCcsDNDTO.getBasketDto().getPrePaymentAmt()) -  adminCharge;//Athena 20130909
				
				BigDecimal depositTotal= depositService.getDepositAmountForOrder(mobCcsDNDTO.getOrderDto().getOrderId());
				
				
				adminCharge +=too1AdminCharge; //add admin Charge with Too1AdminCharge to avoid  prePaymentAmt deduction by Tommy
				
				mobCcsDNDTO.getBasketDto().setUpfrontAmt(upfrontAmt.toString());
				mobCcsDNDTO.getBasketDto().setPrePaymentAmt(prePaymentAmt.toString());
				mobCcsDNDTO.getBasketDto().setAdminCharge(adminCharge.toString());//Athena 20130909
				if (depositTotal==null){
					depositTotal = new BigDecimal("0") ;
				}
				
				mobCcsDNDTO.setOsBalance(upfrontAmt + adminCharge  + prePaymentAmt + depositTotal.doubleValue() -paid);
			}

			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				mobCcsDNDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
				mobCcsDNDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				mobCcsDNDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
				mobCcsDNDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
			//20140220 deposit report Athena
			if(mobCcsDNDTO.getOrderDto().getAgreementNum()!=null){
				BigDecimal depositTotal= depositService.getDepositAmountForOrder(mobCcsDNDTO.getOrderDto().getAgreementNum());
				if (depositTotal!=null){
					mobCcsDNDTO.setDepositTotal(depositTotal.toString());
				}else{
					mobCcsDNDTO.setDepositTotal("0");
				}
			}
			mobCcsDNDTO.setCustomerCopyInd("N");

			dnList.add(mobCcsDNDTO);

			// copy set of data from original to cust copy & set cust copy to
			// "Y"
			BeanUtils.copyProperties(mobCcsDNCustCopyDTO, mobCcsDNDTO);
			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				mobCcsDNCustCopyDTO.setCompanyLogo(imageFilePath + "/"
						+ COMPANY_LOGO_FILE_CHI);
				mobCcsDNCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/"
						+ COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				mobCcsDNCustCopyDTO.setCompanyLogo(imageFilePath + "/"
						+ COMPANY_LOGO_FILE_ENG);
				mobCcsDNCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/"
						+ COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
			mobCcsDNCustCopyDTO.setCustomerCopyInd("Y");

			dnCustCopyList.add(mobCcsDNCustCopyDTO);

			String key_DN = "";
			key_DN = MobCcsDeliveryNoteDTO.JASPER_TEMPLATE_DN;

			map.put(key_DN, new JasperReport(key_DN, dnList));
			map.put(key_DN + CUST_COPY, new JasperReport(key_DN, dnCustCopyList));

		} catch (Exception e) {
			logger.error("Exception caught in mapCCSDeliveryReportData() ", e);
			throw new AppRuntimeException(e);
		}
		return map;
	}
	
	//add by Eliot 2012/05/09
	/**
	 * Assign DOA delivery report data to specific report. Size of Map return should be same
	 * of number of report template generate.
	 */
	private HashMap<String, JasperReport> mapCCSDOADeliveryReportData(ArrayList<Object> pDTOs, String pLang, String brand)
				throws InvocationTargetException, IllegalAccessException {

		logger.info("mapCCSDOADeliveryReportData is called in MobCcsReportServiceImpl.java");
		// Map form name with data set
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();

		try {
			// Delivery Note (DN)
			ArrayList<ReportDTO> dnList = new ArrayList<ReportDTO>();
			ArrayList<ReportDTO> dnCustCopyList = new ArrayList<ReportDTO>();

			// Create a new form dto so as to input data
			MobCcsDOADeliveryNoteDTO mobCcsDOADNDTO = new MobCcsDOADeliveryNoteDTO();
			MobCcsDOADeliveryNoteDTO mobCcsDOADNCustCopyDTO = new MobCcsDOADeliveryNoteDTO();

			// Copy necessary dto(s) into whole DN report dto
			Object tempObj = null;
			for (int i = 0; i < pDTOs.size(); i++) {
				tempObj = pDTOs.get(i);

				if (tempObj instanceof OrderDTO) {
					mobCcsDOADNDTO.setOrderDto((OrderDTO) tempObj);
					mobCcsDOADNDTO.setAppInDateStr(Utility.date2String(((OrderDTO) tempObj).getAppInDate(),
																		BomWebPortalConstant.DATE_FORMAT));

				} else if (tempObj instanceof MnpDTO) {
					mobCcsDOADNDTO.setMnpDto((MnpDTO) tempObj);
				} else if (tempObj instanceof DeliveryUI) {
					if (tempObj != null) {
						mobCcsDOADNDTO.setDeliveryUi((DeliveryUI) tempObj);
						logger.debug("Delivery single line address : "	+ ((DeliveryUI) tempObj).getSingleLineAddress());

						String collectionType = "";
						if ("D".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
							collectionType = "Delivery by Courier";
							mobCcsDOADNDTO.setDeliveryAddress(((DeliveryUI) tempObj).getSingleLineAddress());
						} else if ("P".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
							collectionType = "Pick Up at Centre";
							if ((((DeliveryUI) tempObj).getDeliveryCentre() != null)
									&& !((DeliveryUI) tempObj).getDeliveryCentre().isEmpty()) {
								collectionType += " / " + ((DeliveryUI) tempObj).getDeliveryCentre();
							}

							mobCcsDOADNDTO.setDeliveryAddress(codeLkupDao.getCodeDesc(
													BomWebPortalCcsConstant.DELIVERY_PICKCENTER_LOOKUP_DESC,
													((DeliveryUI) tempObj).getDeliveryCentre()));
						}

						String locDesc = codeLkupDao.getCodeDesc("STOCK_LOC", ((DeliveryUI) tempObj).getDeliveryCentre());
						if (StringUtils.isNotEmpty(locDesc)) {
							collectionType += " / " + locDesc;
						}

						mobCcsDOADNDTO.setCollectionType(collectionType);

						String DeliveryDateAndTimeSlotStr = "";
						String timeSlotCode = ((DeliveryUI) tempObj).getDeliveryTimeSlot();
						String timeSlot[] = null;

						if (StringUtils.isNotBlank(((DeliveryUI) tempObj).getDeliveryDateStr())) {
							DeliveryDateAndTimeSlotStr += ((DeliveryUI) tempObj).getDeliveryDateStr();
						}

						if (timeSlotCode != null && !"".equals(timeSlotCode)) {
							timeSlot = deliveryDao.getTimeSlotDesc(timeSlotCode);

							if (timeSlot != null) {
								DeliveryDateAndTimeSlotStr += " " + timeSlot[0] + "-" + timeSlot[1];
							}
						}
						mobCcsDOADNDTO.setDeliveryDateAndTimeSlot(DeliveryDateAndTimeSlotStr);
					}
				} else if (tempObj instanceof StaffInfoUI) {
					StaffInfoUI staffInfo = (StaffInfoUI) tempObj;
					mobCcsDOADNDTO.setStaffInfoUi(staffInfo);
					String hotline = staffInfoDao.getCcsHotline(staffInfo.getSalesId(), brand);
					if (StringUtils.isNotBlank(hotline)){
						mobCcsDOADNDTO.getStaffInfoUi().setHotLine(hotline);
					}
					
				} else if (tempObj instanceof BasketDTO) {
					mobCcsDOADNDTO.setBasketDto((BasketDTO) tempObj);
				} 
					
				else if (tempObj instanceof List<?>) {
					if (((List)tempObj).size() > 0 && ((List)tempObj).get(0) instanceof StockDTO){
							mobCcsDOADNDTO.setDoaStockList((List<StockDTO>) tempObj);
						}
				}
			}

			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				mobCcsDOADNDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
				mobCcsDOADNDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				mobCcsDOADNDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
				mobCcsDOADNDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
			mobCcsDOADNDTO.setCustomerCopyInd("N");
			
			

			dnList.add(mobCcsDOADNDTO);

			// copy set of data from original to cust copy & set cust copy to
			// "Y"
			BeanUtils.copyProperties(mobCcsDOADNCustCopyDTO, mobCcsDOADNDTO);
			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				mobCcsDOADNCustCopyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
				mobCcsDOADNCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				mobCcsDOADNCustCopyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
				mobCcsDOADNCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
			mobCcsDOADNCustCopyDTO.setCustomerCopyInd("Y");

			dnCustCopyList.add(mobCcsDOADNCustCopyDTO);

			String key_DOADN = "";
			key_DOADN = MobCcsDOADeliveryNoteDTO.JASPER_TEMPLATE_DN;

			map.put(key_DOADN, new JasperReport(key_DOADN, dnList));
			map.put(key_DOADN + CUST_COPY, new JasperReport(key_DOADN, dnCustCopyList));

		} catch (Exception e) {
			logger.error("Exception caught in mapCCSDeliveryReportData() ", e);
			throw new AppRuntimeException(e);
		}
		return map;
	}

	/**
	 * Assign change service report data to specific report. Size of Map return should be same
	 * of number of report template generate.
	 */

	private HashMap<String, JasperReport> mapCCSChangeServiceAndRefundData(
			ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("mapCCSChangeServiceAndRefundData is called in MobCcsReportServiceImpl.java");
		// Map form name with data set
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();

		try {
			// Courier Guide (CS)
			ArrayList<ReportDTO> csList = new ArrayList<ReportDTO>();
			ArrayList<ReportDTO> csCustCopyList = new ArrayList<ReportDTO>();
			List<ReportDTO> multiSimChgServCustInfoRefundList = new ArrayList<ReportDTO>(); //MultiSim Athena 20140128
			List<ReportDTO> multiSimChgServCustInfoRefundCustCopyList = new ArrayList<ReportDTO>(); //MultiSim Athena 20140128
			// Create a new form dto so as to input data
			MobCcsChgServCustInfoRefundDTO mobCcsCSDTO = new MobCcsChgServCustInfoRefundDTO();
			MobCcsChgServCustInfoRefundDTO mobCcsCSCustCopyDTO = new MobCcsChgServCustInfoRefundDTO();

			// Copy necessary dto(s) into whole DN report dto
			Object tempObj = null;
			boolean hasChangeOfServiceForm = false;
			String mnpInd = "";
			String mnpDocNum = "";
			String custDocNum = "";
			String perPaidSim = "";
			String iccid = "";
			boolean handSetInd = false;
			String cutOverDateStr = "";
			for (int i = 0; i < pDTOs.size(); i++) {

				tempObj = pDTOs.get(i);

				if (tempObj instanceof OrderDTO) {
					// Change Service form
					mobCcsCSDTO.setOrderId(((OrderDTO) tempObj).getOrderId());
					mobCcsCSDTO.setAppInDateStr(Utility.date2String(((OrderDTO) tempObj).getAppInDate(),
																	BomWebPortalConstant.DATE_FORMAT));
				} else if (tempObj instanceof MRTUI) {
					
					MRTUI mrtUI = (MRTUI) tempObj;
					
					if (StringUtils.isNotBlank(mrtUI.getUnicomMobMsisdn())) {
						if (StringUtils.isNotBlank(mrtUI.getMnpMsisdn())) {
							mobCcsCSDTO.setNewMsisdn(mrtUI.getMnpMsisdn());
						} else {
							mobCcsCSDTO.setNewMsisdn(mrtUI.getMobMsisdn());
						}
					} else if (StringUtils.isNotBlank(mrtUI.getMnpMsisdn()) 
							&& StringUtils.isNotBlank(mrtUI.getMobMsisdn())) {
						// new number + mnp
						mobCcsCSDTO.setNewMsisdn(mrtUI.getMobMsisdn());
					} else if (StringUtils.isNotBlank(mrtUI.getMnpMsisdn()) 
							&& StringUtils.isBlank(mrtUI.getMobMsisdn())) {
						// mnp
						mobCcsCSDTO.setNewMsisdn(mrtUI.getMnpMsisdn());
					} else if (StringUtils.isBlank(mrtUI.getMnpMsisdn()) 
							&& StringUtils.isNotBlank(mrtUI.getMobMsisdn())) {
						// mrt
						mobCcsCSDTO.setNewMsisdn(mrtUI.getMobMsisdn());
					}
					
					if (BomWebPortalCcsConstant.MRT_NEW_MRT_AND_MNP.equalsIgnoreCase(mrtUI.getMnpInd())) {
						mobCcsCSDTO.setChangeOfMobileNumInd(true);
						mobCcsCSDTO.setMnpMsisdn(mrtUI.getMnpMsisdn());
						mobCcsCSDTO.setChangeMobNumTargetCommDateStr(mrtUI.getCutOverDateStr());
					}
					
					mnpInd = mrtUI.getMnpInd();

					cutOverDateStr = mrtUI.getCutOverDateStr();
					mnpDocNum = mrtUI.getDocNum();
					perPaidSim = mrtUI.getCustName();

					mobCcsCSDTO.setTransferee(perPaidSim);
					if (StringUtils.isNotEmpty(mnpDocNum)){
						mobCcsCSDTO.setTransfereeIdNum(maskedDocNum(mnpDocNum));
					}
				} else if (tempObj instanceof BasketDTO) {
					if (StringUtils.isNotBlank(((BasketDTO) tempObj).getModelId())) {
						handSetInd = true;
					}
				} else if (tempObj instanceof StaffInfoUI) {
					// Change Service form
					mobCcsCSDTO.setStaffcodeName(((StaffInfoUI) tempObj).getSalesId()
							+ " / " + ((StaffInfoUI) tempObj).getSalesName());
				} else if (tempObj instanceof MobileSimInfoDTO) {
					// Change Service form
					iccid = (((MobileSimInfoDTO) tempObj).getIccid());
				} else if (tempObj instanceof CustomerProfileDTO) {
					custDocNum = ((CustomerProfileDTO) tempObj).getIdDocNum();
					// Change Service form
					if ("PASS".equals(((CustomerProfileDTO) tempObj).getIdDocType())) {
						mobCcsCSDTO.setIdDocType("Passport:");
						mobCcsCSDTO.setCustomerNameLabelDisp("Customer Name:");
						mobCcsCSDTO.setCustomerName(((CustomerProfileDTO) tempObj).getTitle()
										+ " " + ((CustomerProfileDTO) tempObj).getCustLastName()
										+ " " + ((CustomerProfileDTO) tempObj).getCustFirstName());
						mobCcsCSDTO.setIdDocNum(maskedDocNum(custDocNum));
					} else if ("BS".equals(((CustomerProfileDTO) tempObj).getIdDocType())) {
						mobCcsCSDTO.setIdDocType("BR No.:");
						mobCcsCSDTO.setCustomerNameLabelDisp("Company Name:");
						mobCcsCSDTO.setCustomerName(((CustomerProfileDTO) tempObj).getCompanyName());
						mobCcsCSDTO.setIdDocNum(custDocNum);

					} else {
						mobCcsCSDTO.setIdDocType("HKID:");
						mobCcsCSDTO.setCustomerNameLabelDisp("Customer Name:");
						mobCcsCSDTO.setCustomerName(((CustomerProfileDTO) tempObj).getTitle()
										+ " " + ((CustomerProfileDTO) tempObj).getCustLastName()
										+ " " + ((CustomerProfileDTO) tempObj).getCustFirstName());
						mobCcsCSDTO.setIdDocNum(maskedDocNum(custDocNum));
					}

					mobCcsCSDTO.setContactPhone(((CustomerProfileDTO) tempObj).getContactPhone());
					
				} else if (tempObj instanceof MobCcsSupportDocUI) {
					List<MobCcsSupportDocDTO> supportDocList = ((MobCcsSupportDocUI) tempObj)
							.getMobCcsSupportDocDTOList();

					Iterator<MobCcsSupportDocDTO> supportItr = supportDocList.iterator();
					while (supportItr.hasNext()) {
						MobCcsSupportDocDTO supportDTO = supportItr.next();
						// 10 Change of Service Form
						if ("10".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							hasChangeOfServiceForm = true;
						}
					}
				}else if (tempObj instanceof List<?>) {
					 List<?> list = (List<?>) tempObj;
					 String ind = "N";
					 for (Object o : (List<?>) tempObj){
						if (o instanceof MultiSimInfoDTO){
							 ind = "Y";
							 break;
						 }
					 }
						if (ind.equalsIgnoreCase("Y")){
						 //MultiSim Athena 20140128 
								//List<RptMultiSimDTO> rptMultiSimDTOList= new ArrayList<RptMultiSimDTO>();
						 		List<MultiSimInfoDTO> multiSimInfoDTOs= (List<MultiSimInfoDTO>) tempObj;

								if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
									
									for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
										List<MultiSimInfoMemberDTO> multiSimInfoMemberDTOs=multiSimInfoDTO.getMembers();
										
										if (multiSimInfoMemberDTOs != null && multiSimInfoMemberDTOs.size() > 0) {
											for(MultiSimInfoMemberDTO multiSimInfoMemberDTO: multiSimInfoMemberDTOs){

												if ("A".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpInd()) || "MUPS05".equals(multiSimInfoMemberDTO.getMemberOrderType())){
													MobCcsChgServCustInfoRefundDTO rptMultiSimChgServCustInfoRefundDTO = new MobCcsChgServCustInfoRefundDTO(); 
													MobCcsChgServCustInfoRefundDTO rptMultiSimChgServCustInfoRefundCustCopyDTO = new MobCcsChgServCustInfoRefundDTO(); 				
													BeanUtils.copyProperties(rptMultiSimChgServCustInfoRefundDTO, mobCcsCSDTO);
													
													rptMultiSimChgServCustInfoRefundDTO.setOrderId(multiSimInfoMemberDTO.getMemberOrderId());
													rptMultiSimChgServCustInfoRefundDTO.setNewMsisdn(multiSimInfoMemberDTO.getMsisdn());
													rptMultiSimChgServCustInfoRefundDTO.setMnpMsisdn(multiSimInfoMemberDTO.getMnpNumber());
													rptMultiSimChgServCustInfoRefundDTO.setChangeMobNumTargetCommDateStr(multiSimInfoMemberDTO.getMnpCutOverDate());
													if ("A".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpInd())){
														rptMultiSimChgServCustInfoRefundDTO.setChangeOfMobileNumInd(true);
													}
													/*rptMultiSimChgServCustInfoRefundDTO.setStaffcodeName(rptAppMobileServDTO.getSalesCd() + " / " + rptAppMobileServDTO.getSalesName());
													rptMultiSimChgServCustInfoRefundDTO.setCustomerName(rptAppMobileServDTO.getTitle()+ " " +rptAppMobileServDTO.getCustFirstName()+ " " +rptAppMobileServDTO.getCustLastName());
													rptMultiSimChgServCustInfoRefundDTO.setIdDocNum(rptAppMobileServDTO.getIdDocNum());
													rptMultiSimChgServCustInfoRefundDTO.setOrderId(rptMobPortAppDTO.getAgreementNum());
													rptMultiSimChgServCustInfoRefundDTO.setAppInDateStr(Utility.date2String(rptMobPortAppDTO.getAppInDate(),"dd/MM/yyyy"));*/

													if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
														rptMultiSimChgServCustInfoRefundDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
														rptMultiSimChgServCustInfoRefundDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
													} else {
														rptMultiSimChgServCustInfoRefundDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
														rptMultiSimChgServCustInfoRefundDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
													}
													
													if (!"Prepaid Sim".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpCustName())) {
														if (StringUtils.isNotBlank(custDocNum) && StringUtils.isNotBlank(multiSimInfoMemberDTO.getMnpDocNo())) {
															if (!custDocNum.equalsIgnoreCase(multiSimInfoMemberDTO.getMnpDocNo())) {
																rptMultiSimChgServCustInfoRefundDTO.setOwnershipFormInd(true);
																rptMultiSimChgServCustInfoRefundDTO.setIccidInd(true);
																rptMultiSimChgServCustInfoRefundDTO.setTransferee(multiSimInfoMemberDTO.getMnpCustName());
																rptMultiSimChgServCustInfoRefundDTO.setTransfereeIdNum(maskedDocNum(multiSimInfoMemberDTO.getMnpDocNo()));
																rptMultiSimChgServCustInfoRefundDTO.setIccid(multiSimInfoMemberDTO.getSim().getIccid());
																rptMultiSimChgServCustInfoRefundDTO.setTransferOwnershipTargetCommDateStr(multiSimInfoMemberDTO.getMnpCutOverDate());
															}
														}
													}
													BeanUtils.copyProperties(rptMultiSimChgServCustInfoRefundCustCopyDTO, rptMultiSimChgServCustInfoRefundDTO);
													rptMultiSimChgServCustInfoRefundCustCopyDTO.setCustomerCopyInd("Y");
													if ("A".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpInd()) || ("MUPS05".equals(multiSimInfoMemberDTO.getMemberOrderType()) && !custDocNum.equalsIgnoreCase(multiSimInfoMemberDTO.getMnpDocNo()))){
														multiSimChgServCustInfoRefundList.add(rptMultiSimChgServCustInfoRefundDTO);
														multiSimChgServCustInfoRefundCustCopyList.add(rptMultiSimChgServCustInfoRefundCustCopyDTO); 
													}
												}

											}
										}
									}								
										
											
										} 
								}
							 
							 
							 
						 
					 }
			}

			if (!"Prepaid Sim".equalsIgnoreCase(perPaidSim)) {

				if (StringUtils.isNotBlank(custDocNum)
						&& StringUtils.isNotBlank(mnpDocNum)) {

					if (!custDocNum.equalsIgnoreCase(mnpDocNum)) {

						mobCcsCSDTO.setOwnershipFormInd(true);
						mobCcsCSDTO.setHandsetind(handSetInd);
						if (!"".equals(iccid) && null != iccid) {
							mobCcsCSDTO.setIccidInd(true);
							mobCcsCSDTO.setIccid(iccid);
						}
						mobCcsCSDTO.setTransferOwnershipTargetCommDateStr(cutOverDateStr);
					}
				}
			}

			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				mobCcsCSDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
				mobCcsCSDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				mobCcsCSDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
				mobCcsCSDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
			mobCcsCSDTO.setCustomerCopyInd("N");

			csList.add(mobCcsCSDTO);

			BeanUtils.copyProperties(mobCcsCSCustCopyDTO, mobCcsCSDTO);
			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				mobCcsCSCustCopyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
				mobCcsCSCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				mobCcsCSCustCopyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
				mobCcsCSCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
			mobCcsCSCustCopyDTO.setCustomerCopyInd("Y");

			csCustCopyList.add(mobCcsCSCustCopyDTO);

			String key_CS = "";
			key_CS = MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS;

			if (hasChangeOfServiceForm && ("A".equals(mnpInd) || ("Y".equals(mnpInd) && !custDocNum.equals(mnpDocNum)))) {//MultiSim Athena 20140128
				if (!(multiSimChgServCustInfoRefundList== null || multiSimChgServCustInfoRefundList.isEmpty())){
					csList.addAll((ArrayList)multiSimChgServCustInfoRefundList);
					csCustCopyList.addAll((ArrayList)multiSimChgServCustInfoRefundCustCopyList);
					map.put(key_CS, new JasperReport(key_CS, csList));
					map.put(key_CS + CUST_COPY, new JasperReport(key_CS, csCustCopyList));
				}else{
					map.put(key_CS, new JasperReport(key_CS, csList));
					map.put(key_CS + CUST_COPY, new JasperReport(key_CS, csCustCopyList));
				}
			}else{
				if (!(multiSimChgServCustInfoRefundList== null || multiSimChgServCustInfoRefundList.isEmpty())){					
					map.put(key_CS, new JasperReport(key_CS, (ArrayList)multiSimChgServCustInfoRefundList));
					map.put(key_CS + CUST_COPY, new JasperReport(key_CS, (ArrayList)multiSimChgServCustInfoRefundCustCopyList));
				}
			}

		} catch (Exception e) {
			logger.error("Exception caught in mapCCSChangeServiceAndRefundData()", e);
			throw new AppRuntimeException(e);
		}
		return map;
	}
	
	/**
	 * generate courier guide jasper reprot object 
	 */
	private JasperReport ccsCourierGuidReportData(ArrayList<Object> pDTOs,
			String pLang) throws InvocationTargetException,
			IllegalAccessException {

		logger.info("ccsCourierGuidReportData is called in MobCcsReportServiceImpl.java");

		String key_CG = "";

		// Courier Guide (CG)
		ArrayList<ReportDTO> cgList = new ArrayList<ReportDTO>();

		try {

			// Create a new form dto so as to input data
			MobCcsCourierDeliveryGuidDTO mobCcsCGDTO = new MobCcsCourierDeliveryGuidDTO();

			// Copy necessary dto(s) into whole DN report dto
			Object tempObj = null;
			String mnpDocNum = "";
			String custDocNum = "";
			String perPaidSim = "";
			boolean isPayByCash = false;
			Double cash = 0.0;
			boolean mrtAndMrt = false;
			boolean mnpInd = false;
			Double upfrontAmt  = 0.0;
			Double prePaymentAmt = 0.0;
			Double paid = 0.0;
			String orderId = null;

			for (int i = 0; i < pDTOs.size(); i++) {

				tempObj = pDTOs.get(i);

				if (tempObj instanceof OrderDTO) {
					
					OrderDTO ord = (OrderDTO) tempObj;
					
					BeanUtils.copyProperties(mobCcsCGDTO, ord);

					orderId = ((OrderDTO) tempObj).getOrderId();
					if (vasDetailDao.isYahooCoupon(orderId)) {
						mobCcsCGDTO.setYahooCouponInd(true);
						mobCcsCGDTO.setYahooCouponSerial(vasDetailDao.getYahooCouponSerial(orderId));	
					}
					if (StringUtils.isNotBlank(ord.getiGuardSerialNo())) {
						mobCcsCGDTO.setiGuardForm(true);
					}

					if (StringUtils.isNotBlank(ord.getOrderId())
							&& StringUtils.contains(ord.getOrderId(), "SBO")) {
						mobCcsCGDTO.setDdaOSForm(true);
					}
					if (StringUtils.isNotBlank(ord.getNfcInd()) && !"0".equals(ord.getNfcInd())) {
						mobCcsCGDTO.setHasNFCSimForm(true);
					}
					if (StringUtils.isNotEmpty(((OrderDTO) tempObj).getPaidAmt())) {
						paid = Double.parseDouble(((OrderDTO) tempObj).getPaidAmt());
					}

				}else if (tempObj instanceof IGuardDTO) {
					
					IGuardDTO iGuardType = (IGuardDTO) tempObj;
					if (iGuardType.getLdsSrvPlanFee()!=null) {
						mobCcsCGDTO.setiGuardFormLDS(true);
					}
					if(iGuardType.getAdSrvPlanFee()!=null){
						mobCcsCGDTO.setiGuardFormAD(true);
					}
				} else if (tempObj instanceof RptMobileSafetyPhoneDTO) {
					mobCcsCGDTO.setMobileSafetyForm(true);
				} /*else if (tempObj instanceof RptNFCConsentDTO) {
					mobCcsCGDTO.setHasNFCSimForm(true);
				} else if (tempObj instanceof RptOctopusConsentDTO) {
					mobCcsCGDTO.setHasOctopusSimForm(true);
				}*/ else if (tempObj instanceof RptTradeInHSDTO) {
					mobCcsCGDTO.setTradeInHSForm(true);
				} else if (tempObj instanceof CustomerProfileDTO) {
					custDocNum = ((CustomerProfileDTO) tempObj).getIdDocNum();
					mobCcsCGDTO.setBrCust("BS".equalsIgnoreCase(((CustomerProfileDTO) tempObj).getIdDocType()));
					mobCcsCGDTO.setCustomerContactName(((CustomerProfileDTO) tempObj).getContactName());
					boolean tngDummyVas = false;
					if (StringUtils.isNotEmpty(orderId)){
						tngDummyVas= orderService.hasTNGServiceDummyVas(orderId);
					}
					
					if (tngDummyVas && custDocNum.startsWith("J") && "HKID".equalsIgnoreCase(((CustomerProfileDTO) tempObj).getIdDocType())){
						mobCcsCGDTO.setjPrefixTngServiceInd(true);					
					}
				} else if (tempObj instanceof DeliveryUI) {

					String DeliveryDateAndTimeSlotStr = "";
					String timeSlotCode = ((DeliveryUI) tempObj).getDeliveryTimeSlot();
					String timeSlot[] = null;

					if (StringUtils.isNotBlank(((DeliveryUI) tempObj).getDeliveryDateStr())) {
						DeliveryDateAndTimeSlotStr += ((DeliveryUI) tempObj).getDeliveryDateStr();
					}

					if (StringUtils.isNotBlank(timeSlotCode)) {
						timeSlot = deliveryDao.getTimeSlotDesc(timeSlotCode);

						if (timeSlot != null) {
							DeliveryDateAndTimeSlotStr += " " + timeSlot[0]	+ "-" + timeSlot[1];
						}
					}
					mobCcsCGDTO.setDeliveryDayStr(DeliveryDateAndTimeSlotStr);

					// replace third ind (no use)
					if (((DeliveryUI) tempObj).isRecieveByThirdPartyInd()) {
						mobCcsCGDTO.setAuthorizationLetterAndIDCollection(true);
						mobCcsCGDTO.setAuthorizationLetterName(((DeliveryUI) tempObj)
										.getThirdPartyContact()
										.getContactName());
						mobCcsCGDTO.setAuthorizationLetterContractNum(((DeliveryUI) tempObj)
										.getThirdPartyContact()
										.getContactPhone());
						mobCcsCGDTO.setDeliveryContactName(((DeliveryUI) tempObj).getThirdPartyContact().getContactName());
						if (((DeliveryUI) tempObj).getThirdPartyContact().getOtherPhone() != null) {
							mobCcsCGDTO.setDeliveryContactNum(
									((DeliveryUI) tempObj).getThirdPartyContact().getContactPhone()
									+ " / " + ((DeliveryUI) tempObj).getThirdPartyContact().getOtherPhone());
						} else {
							mobCcsCGDTO.setDeliveryContactNum(((DeliveryUI) tempObj).getThirdPartyContact().getContactPhone());
						}
					} else {
						mobCcsCGDTO.setDeliveryContactName(((DeliveryUI) tempObj).getPrimaryContact().getContactName());
						if (((DeliveryUI) tempObj).getPrimaryContact().getOtherPhone() != null) {
							mobCcsCGDTO.setDeliveryContactNum(
									((DeliveryUI) tempObj).getPrimaryContact().getContactPhone()
									+ " / " + ((DeliveryUI) tempObj).getPrimaryContact().getOtherPhone());
						} else {
							mobCcsCGDTO.setDeliveryContactNum(((DeliveryUI) tempObj).getPrimaryContact().getContactPhone());
						}
					}
					
					if ("D".equalsIgnoreCase(((DeliveryUI) tempObj).getDeliveryInd())) {
						mobCcsCGDTO.setThroughDelivery(true);
						mobCcsCGDTO.setThroughPickUp(false);
					} else {
						mobCcsCGDTO.setThroughDelivery(false);
						mobCcsCGDTO.setThroughPickUp(true);
					}
					
				} else if (tempObj instanceof PaymentUpFrontUI) {
					if ("M".equalsIgnoreCase(((PaymentUpFrontUI) tempObj).getPayMethodType()) && !mobCcsCGDTO.isYahooCouponInd()) {
						isPayByCash = true;
					}

					if ("C".equals(((PaymentUpFrontUI) tempObj).getPayMethodType())
							|| "I".equals(((PaymentUpFrontUI) tempObj).getPayMethodType())) {
						mobCcsCGDTO.setUpFrontPaymentByCreditCard(true);
					}

				} else if (tempObj instanceof Double) {
					cash = ((Double) tempObj);

				} else if (tempObj instanceof MobileSimInfoDTO) {
					// Courier form
					mobCcsCGDTO.setIccid(((MobileSimInfoDTO) tempObj).getIccid());

				} else if (tempObj instanceof PaymentDTO) {
					if ("C".equals(((PaymentDTO) tempObj).getPayMethodType())) {
						mobCcsCGDTO.setCreditCardCopy(true);
					}

				} else if (tempObj instanceof ServicePlanReportDTO) {

					if ("5".equals(((ServicePlanReportDTO) tempObj).getBasketType())) {
						mobCcsCGDTO.setNeFormInd(true);
						mobCcsCGDTO.setSsFormNewMrtInd(false);
					} else {
						mobCcsCGDTO.setNeFormInd(false);
						mobCcsCGDTO.setSsFormNewMrtInd(true);
					}
					
				} else if (tempObj instanceof MRTUI) {
					if (BomWebPortalCcsConstant.MRT_NEW_MRT_AND_MNP
							.equalsIgnoreCase(((MRTUI) tempObj).getMnpInd())) {
						mobCcsCGDTO.setChangeOfMobileNumInd(true);
					}
					mnpDocNum = ((MRTUI) tempObj).getDocNum();

					if ("A".equals(((MRTUI) tempObj).getMnpInd())) {
						mrtAndMrt = true;
					} else if ("Y".equals(((MRTUI) tempObj).getMnpInd())) {
						mnpInd = true;
					}

					perPaidSim = ((MRTUI) tempObj).getCustName();
				} else if (tempObj instanceof MobCcsSupportDocUI) {
					List<MobCcsSupportDocDTO> supportDocList = ((MobCcsSupportDocUI) tempObj)
							.getMobCcsSupportDocDTOList();

					Iterator<MobCcsSupportDocDTO> supportItr = supportDocList.iterator();
					while (supportItr.hasNext()) {
						MobCcsSupportDocDTO supportDTO = supportItr.next();

						// 01 ID Document Copy
						if ("1".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setIdDocCopy(true);
						}
						// 02 Address Proof
						if ("2".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setAddressProof(true);
						}
						// 04 3rd Party Credit Card Authorization
						if ("4".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setThirdPartyCreditCardAuthorization(true);
						}
						// 05 3rd Party Credit Card Holder ID Document Copy
						if ("5".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setThirdPartyCreditCardHolderIDDocumentCopy(true);
						}
						// 06 Credit Card Installment Form
						// if("06".equals(supportDTO.getDocId()) &&
						// supportDTO.isReceivedByFax() == false){
						// mobCcsCGDTO.setCreditCardInstallmentForm(true);
						// }
						// 07 MNP Application Form
						if ("7".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setMnpApplicationForm(true);
						}
						// 08 Staff Copy
						// if("01".equals(supportDTO.getDocId()) &&
						// supportDTO.isReceivedByFax() == false){
						// mobCcsCGDTO.setStaffCopy(true);
						// }
						// 09 Student Card Copy
						if ("9".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setStudentCardCopy(true);
						}
						// 10 Change of Service Form
						if ("10".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setChangeOfServiceForm(true);
						}
						// 11 Valid Foreign Domestic Helper Contract Copy within
						// 6
						// months
						if ("11".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setValidForeignDomesticHelperContractCopy(true);
						}
						// 12 Prepaid SIM copy
						if ("12".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setPrepaidSIMcopy(true);
						}
						// 13 Concierge Service Authorization Letter
						if ("13".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setConciergeServiceAuthorizationLetter(true);
						}
						// 14 Authorization Letter for 3rd party collection
						if ("14".equals(supportDTO.getDocId())
								&& supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setAuthorizationLetterfor3rdPartycollection(true);
						}
						// 15 (BR customer) Business Name Card of Contact person
						if ("15".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setBrCustomerBusinessNameCardOfContactPerson(true);
						}
						/*// 16 (BR customer) Business Name Card / Staff copy of
						// User
						if ("16".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setBrCustomerBusinessNameCardStaffCopy(true);
						}*/
						// 16 2N register HKID copy (MNP owner ID copy)
						// User
						if ("16".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setTwoNRegisterHKIDCopy(true);
						}  
						if ("30".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							mobCcsCGDTO.setHkbnInd(true);
						} 
						// 33 Student transcript
						if ("33".equals(supportDTO.getDocId()) && supportDTO.isReceivedByFax() == false) {
							System.out.println("this is docid 33");
							mobCcsCGDTO.setTranscriptCopy(true);
						} 
						
					}

				}else if (tempObj instanceof BasketDTO) {
					BasketDTO basketDto = ((BasketDTO) tempObj);
					upfrontAmt = new Double(basketDto.getUpfrontAmt());
					prePaymentAmt = new Double (basketDto.getPrePaymentAmt());
					
				}
			}

			if (isPayByCash) {
				
				Double vasHSAmt = vasDetailDao.getVasHSAmt(mobCcsCGDTO.getOrderId());
				upfrontAmt = new Double(upfrontAmt) + vasHSAmt;
				Double adminCharge= vasDetailDao.getAdminCharge(mobCcsCGDTO.getOrderId());//Athena 20130909
				Double too1AdminCharge = vasDetailDao.getMupAdminChargeAmount(mobCcsCGDTO.getOrderId());
				
				prePaymentAmt = new Double(prePaymentAmt) -  adminCharge;//Athena 20130909
				
				BigDecimal depositTotal= depositService.getDepositAmountForOrder(mobCcsCGDTO.getOrderId());
				
				
				adminCharge +=too1AdminCharge; //add admin Charge with Too1AdminCharge to avoid  prePaymentAmt deduction by Tommy

				if (depositTotal==null){
					depositTotal = new BigDecimal("0") ;
				}
				
				mobCcsCGDTO.setCash(upfrontAmt + adminCharge  + prePaymentAmt + depositTotal.doubleValue() -paid);
				mobCcsCGDTO.setCashInd(true);
			}

			/* ownership form ind is true
			 * when 1) MNP_IND = 'Y' or 'A'
			 * 		2) MNP ID Doc No <> Cust ID Doc No 
			 * 		3) NOT prepaid SIM
			 */
			
			if (mrtAndMrt == true || mnpInd == true) { // mnpInd == 'A' (MNP + MRT)
				if (!"Prepaid Sim".equalsIgnoreCase(perPaidSim)) {
					if (StringUtils.isNotBlank(custDocNum) && StringUtils.isNotBlank(mnpDocNum)) {
						if (!custDocNum.equalsIgnoreCase(mnpDocNum)) {
							mobCcsCGDTO.setOwnershipFormInd(true);
						}
					}
				}
			}
			//Athena 20130923
			try {
				boolean octFlag = orderDao.getOctFlag();
				if (octFlag){
					mobCcsCGDTO.setOctFlag("Y");
				}else{
					mobCcsCGDTO.setOctFlag("N");
				}
			} catch (Exception e) {

			}
			
			//Dennis 20151125
			mobCcsCGDTO.setTngServiceFlag(orderService.hasTNGServiceDummyVas(mobCcsCGDTO.getOrderId()));
			
			try {
				List<MultipleMrtSimDTO> multiSimList 
					= orderDao.getMultipleMrtSimDTOList(mobCcsCGDTO.getOrderId());
				
				List<MultipleMrtSimDTO> multiSimListOdd 
					= new ArrayList<MultipleMrtSimDTO>();
				List<MultipleMrtSimDTO> multiSimListEven 
				= new ArrayList<MultipleMrtSimDTO>();
				
				if (multiSimList != null && multiSimList.size() > 0) {
					mobCcsCGDTO.setHaveMultiSim(true);
					
					Iterator<MultipleMrtSimDTO> itr = multiSimList.iterator();
				    while(itr.hasNext()){
				    	MultipleMrtSimDTO temp = itr.next();
				    	if (temp != null) {
				    		if (Integer.parseInt(temp.getRownum()) % 2 == 1) {
				    			multiSimListOdd.add(temp);
				    		} else {
				    			multiSimListEven.add(temp);
				    		}
				    	}
				    }
				    
				    if (multiSimListOdd != null && multiSimListOdd.size() == 0) {
				    	multiSimListOdd = null;
				    }
				    
				    if (multiSimListEven != null && multiSimListEven.size() == 0) {
				    	multiSimListEven = null;
				    }
				    
				    mobCcsCGDTO.setMultipleMrtSimListOdd(multiSimListOdd);
					mobCcsCGDTO.setMultipleMrtSimListEven(multiSimListEven);
				    
				} else {
					mobCcsCGDTO.setHaveMultiSim(false);
					mobCcsCGDTO.setMultipleMrtSimListOdd(null);
					mobCcsCGDTO.setMultipleMrtSimListEven(null);
				}
			} catch (DAOException e1) {
				logger.debug("DAOException @ReportServiceImp: getMultipleMrtSimDTOList");
				e1.printStackTrace();
			} 	
			
			if (mobCcsCGDTO.isIdDocCopy() != false
					 || mobCcsCGDTO.isAddressProof() != false
					 || mobCcsCGDTO.isCashInd() != false
					 || mobCcsCGDTO.isThirdPartyCreditCardHolderIDDocumentCopy() != false
					 || (mobCcsCGDTO.isOwnershipFormInd() != false && mobCcsCGDTO.isTwoNRegisterHKIDCopy() != false)
					 || mobCcsCGDTO.isStudentCardCopy() != false
					 || mobCcsCGDTO.isConciergeServiceAuthorizationLetter() != false
					 || mobCcsCGDTO.isValidForeignDomesticHelperContractCopy() != false
					 || mobCcsCGDTO.isjPrefixTngServiceInd()!=false
					 || mobCcsCGDTO.isPrepaidSIMcopy()!=false
					 ) {
				mobCcsCGDTO.setReceiveCopy(true);
			}
			
			String[] installBankInfo = paymentUpfrontDAO.getInstallBankInfo(mobCcsCGDTO.getOrderId());

			mobCcsCGDTO.setBankNameChi(installBankInfo[0]);
			mobCcsCGDTO.setNoOfSign(installBankInfo[1]);
			
			mobCcsCGDTO.setStudentPlanFlag(orderService.hasStudentPlanTNGStock(mobCcsCGDTO.getOrderId()));
			
			cgList.add(mobCcsCGDTO);

			key_CG = MobCcsCourierDeliveryGuidDTO.JASPER_TEMPLATE_CG;

		} catch (Exception e) {
			logger.error("Exception caught in ccsCourierGuidReportData()", e);
			throw new AppRuntimeException(e);
		}
		return new JasperReport(key_CG, cgList);

	}
	
	/**
	 * Assign S&S report data to specific report. Size of Map return should be same
	 * of number of report template generate.
	 */
	private HashMap<String, JasperReport> mapReportData(ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();
		String billMediaString ="";//Paper bill Athena 20130925
		String billMediaStringZh ="";//Paper bill Athena 20130925
		ArrayList<ReportDTO> ssList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> ssCustCopyList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> ccList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> ccOSList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> mnpList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> secSrvList = new ArrayList<ReportDTO>();
		// ArrayList<ReportDTO> tradeDescList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> conSrvList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> serviceGuideList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> hsTradeDescList = new ArrayList<ReportDTO>(); 
		List<HSTradeDescDTO> hsTradeDescDTOs = null;
		ArrayList<ReportDTO> iPhoneTradeInFormDTOList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> iPhoneTradeInFormCustCopyDTOList = new ArrayList<ReportDTO>();
		

		RptAppMobileServDTO rptAppMobileServDTO = new RptAppMobileServDTO();
		RptAppMobileServDTO rptAppMobileServCustCopyDTO = new RptAppMobileServDTO();
		RptCreditCardDDAuthDTO rptCreditCardDDAuthDTO = new RptCreditCardDDAuthDTO();
		RptCreditCardDDAuthOSDTO rptCreditCardDDAuthOSDTO = new RptCreditCardDDAuthOSDTO();
		RptMobPortAppDTO rptMobPortAppDTO = new RptMobPortAppDTO();
		RptSecretarialServDTO rptSecretarialServDTO = new RptSecretarialServDTO();
//		RptHSTradeDescDTO rptRptHSTradeDescDTO = new RptHSTradeDescDTO();
		RptConciergeServiPadiPhoneDTO rptConciergeServiPadiPhoneDTO = new RptConciergeServiPadiPhoneDTO();
		RptServiceGuideDTO rptServiceGuideDTO = new RptServiceGuideDTO();
		RptHSTradeDescDTO rptHSTradeDescDTOTemplate = new RptHSTradeDescDTO();
		List<ReportDTO> multiSimMnpList = new ArrayList<ReportDTO>();//MultiSim Athena 20140128
		IPhoneTradeInFormDTO iPhoneTradeInFormDTO = new IPhoneTradeInFormDTO();
		IPhoneTradeInFormDTO iPhoneTradeInFormCustCopyDTO = new IPhoneTradeInFormDTO();
		
		boolean isUniversityPlan = false;
		boolean uniOptionalVasInd = false;
	    boolean isIphone7TradeInBasket = false;
	    boolean isIphone8TradeInBasket = false;
		List<CodeLkupDTO> iphoneSevenTradeInList= LookupTableBean.getInstance().getCodeLkupList().get("IP7_TRADE_IN");
		List<CodeLkupDTO> iphoneEightTradeInList= LookupTableBean.getInstance().getCodeLkupList().get("IP8_TRADE_IN");

		
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
//			rptRptHSTradeDescDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);

			rptAppMobileServDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptAppMobileServDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptAppMobileServCustCopyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptAppMobileServCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptCreditCardDDAuthDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptCreditCardDDAuthDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptCreditCardDDAuthOSDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptCreditCardDDAuthOSDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptMobPortAppDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptMobPortAppDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptSecretarialServDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptSecretarialServDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptConciergeServiPadiPhoneDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptConciergeServiPadiPhoneDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptServiceGuideDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptServiceGuideDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptHSTradeDescDTOTemplate.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptHSTradeDescDTOTemplate.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
		} else {
//			rptRptHSTradeDescDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			
			rptAppMobileServDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptAppMobileServDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptAppMobileServCustCopyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptAppMobileServCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptCreditCardDDAuthDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptCreditCardDDAuthDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptCreditCardDDAuthOSDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptCreditCardDDAuthOSDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptMobPortAppDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptMobPortAppDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptSecretarialServDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptSecretarialServDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptConciergeServiPadiPhoneDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptConciergeServiPadiPhoneDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptServiceGuideDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptServiceGuideDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptHSTradeDescDTOTemplate.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptHSTradeDescDTOTemplate.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
		}
		rptHSTradeDescDTOTemplate.setCompanyChop(imageFilePath + "/" + COMPANY_CHOP_FILE);

		Object obj = null;
		boolean isReqCreditCardAuthForm = false;
		boolean isReqCreditCardAuthFormOS = false;
		boolean isReqMnpForm = false;
		boolean isReqSecSrvForm = false;
		// boolean isReqTradeDescForm = false;
		boolean isConSrvForm = false;
		SignoffDTO signature = null;
		boolean isNEForm = false;
		String oid = null;//Athena deposit
		boolean isStudentPlanInd = true;
		String brand = null;
		
		String mnpMsisdn = "";
		boolean isMNPnNewNum = false;
		try {//Athena 20130923
			boolean octFlag = orderDao.getOctFlag();
			//System.out.print(" orderdao1: "+(octFlag?"Y":"N"));
			if (octFlag){
				rptAppMobileServDTO.setOctFlag("Y");
		
			}else{
				rptAppMobileServDTO.setOctFlag("N");
		
			}
		} catch (Exception e) {

		}
		for (int i = 0; i < pDTOs.size(); i++) {
			obj = pDTOs.get(i);
			
			if (obj instanceof CustomerProfileDTO) {
				
				CustomerProfileDTO custProf = (CustomerProfileDTO) obj;

				BeanUtils.copyProperties(rptAppMobileServDTO, custProf);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, custProf);
				BeanUtils.copyProperties(rptMobPortAppDTO, custProf);
				BeanUtils.copyProperties(rptSecretarialServDTO, custProf);
				BeanUtils.copyProperties(rptConciergeServiPadiPhoneDTO, custProf);

				rptSecretarialServDTO.setCompanyName(custProf.getCompanyName());
				rptSecretarialServDTO.setIdDocType(custProf.getIdDocType());
				rptCreditCardDDAuthDTO.setCompanyName(custProf.getCompanyName());
				
				if ("BS".equals(custProf.getIdDocType())) {
					rptCreditCardDDAuthOSDTO.setCustName(custProf.getCompanyName());
					rptAppMobileServDTO.setIdDocNum(custProf.getIdDocNum());
				} else {
					rptCreditCardDDAuthOSDTO.setCustName(custProf.getCustLastName()
															+ " "
															+ custProf.getCustFirstName());
					rptAppMobileServDTO.setIdDocNum(maskedDocNum(custProf.getIdDocNum()));
				}
				
				rptCreditCardDDAuthOSDTO.setContactPhone(custProf.getContactPhone());
				//*******Paper bill Athena 20130925 start******//
				if (custProf.getSelectedBillMedia()!=null){
					List<MobBillMediaDTO> billMediaList  = LookupTableBean.getInstance().getBillMediaOption();

					for(MobBillMediaDTO mbm: billMediaList) {
						for (String billCd: custProf.getSelectedBillMedia()) {
							if (billCd.equals(mbm.getCodeId())) {
								billMediaString += "/ " +  mbm.getEngDesc();
								billMediaStringZh += "/ " +  mbm.getChiDesc();
							}
						}
					}
					
				}
				//*******Paper bill Athena 20130925 end******//
				
				isStudentPlanInd = custProf.isStudentPlanSubInd();
				
				iPhoneTradeInFormDTO.setCustEngName(custProf.getTitle()+" "+custProf.getCustLastName()+" "+custProf.getCustFirstName());
				brand = custProf.getBrand();
			} else if (obj instanceof ServicePlanReportDTO) {
				isReqSecSrvForm = ((ServicePlanReportDTO) obj).getSecSrvContractPeriod() != null ? true : false;
				isConSrvForm = ((ServicePlanReportDTO) obj).getConciergeInd() != null
						&& ((ServicePlanReportDTO) obj).getConciergeInd().equalsIgnoreCase("Y") ? true : false;

				BeanUtils.copyProperties(rptAppMobileServDTO, obj);
				BeanUtils.copyProperties(rptSecretarialServDTO, obj);

				BeanUtils.copyProperties(rptConciergeServiPadiPhoneDTO, obj);
				iPhoneTradeInFormDTO.setHandsetModel(((ServicePlanReportDTO) obj).getHandsetDeviceDescription());

			} else if (obj instanceof PaymentDTO) {
				
				PaymentDTO pay = (PaymentDTO) obj;
				
				if ("Y".equalsIgnoreCase(pay.getThirdPartyInd())
						&& StringUtils.isNotBlank(pay.getCreditCardDocType())
						&& "C".equalsIgnoreCase(pay.getPayMethodType())) {
					isReqCreditCardAuthForm = true;
					BeanUtils.copyProperties(rptCreditCardDDAuthDTO, pay);
				}

				BeanUtils.copyProperties(rptAppMobileServDTO.getSectG(), pay);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, pay);
				
				rptCreditCardDDAuthOSDTO.setCreditCardType(pay.getCreditCardType());
				rptCreditCardDDAuthOSDTO.setCreditCardNum(pay.getCreditCardNum());
				rptCreditCardDDAuthOSDTO.setCreditCardValidMonth(pay.getCreditExpiryMonth());
				rptCreditCardDDAuthOSDTO.setCreditCardValidYear(pay.getCreditExpiryYear());
				rptCreditCardDDAuthOSDTO.setCreditCardHolderName(pay.getCreditCardHolderName());
				if(StringUtils.isNotEmpty(pay.getCreditCardDocNum())){
					rptCreditCardDDAuthOSDTO.setIdDocNum(maskedDocNum(pay.getCreditCardDocNum()));
					rptCreditCardDDAuthDTO.setCreditCardDocNum(maskedDocNum(pay.getCreditCardDocNum()));
				}
				
			} else if (obj instanceof MnpDTO) {
				
				MnpDTO mnpDto = (MnpDTO)obj;
				
				mnpMsisdn = mnpDto.getMnpMsisdn();
				
				if (StringUtils.isNotBlank(mnpDto.getMnpMsisdn()) 
						&& StringUtils.isNotBlank(mnpDto.getNewMsisdn())) {
					if (!mnpDto.getMnpMsisdn().equals(mnpDto.getNewMsisdn()))
						isMNPnNewNum = true;
				}
				
				BeanUtils.copyProperties(rptAppMobileServDTO, mnpDto);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, mnpDto);
				BeanUtils.copyProperties(rptMobPortAppDTO, mnpDto);
				BeanUtils.copyProperties(iPhoneTradeInFormDTO, mnpDto);

				
				rptCreditCardDDAuthOSDTO.setMsisdn(mnpDto.getMsisdn());
				if(StringUtils.isNotEmpty(mnpDto.getCustIdDocNum())){
				rptCreditCardDDAuthDTO.setCreditCardDocNum(maskedDocNum(mnpDto.getCustIdDocNum()));
				rptMobPortAppDTO.setCustIdDocNum(maskedDocNum(mnpDto.getCustIdDocNum()));
				}
			} else if (obj instanceof BasketDTO) {
				
				BasketDTO basketDTO = (BasketDTO)obj;
				List<String> universityPlanBasketIdList = null;
				try {
					universityPlanBasketIdList = orderDao.getCodeIdList("UNI_STD_PLAN");
				} catch (DAOException e) {
					logger.error("Exception caught in getCodeIdList in reportService", e);

				}
				if (universityPlanBasketIdList!=null){
					for (int j = 0;j<universityPlanBasketIdList.size();j++){
						if (basketDTO.getBasketId().equals(universityPlanBasketIdList.get(j))){
							isUniversityPlan=true;
						}
					}
				}
				boolean isStaffOffer = false;
				try {
					isStaffOffer = orderDao.isStaffOffer(basketDTO.getBasketId());
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
				
				//BeanUtils.copyProperties(rptAppMobileServDTO, basketDTO);
				
			} else if (obj instanceof OrderDTO) {
				
				OrderDTO ord = (OrderDTO) obj;
								
				BeanUtils.copyProperties(rptAppMobileServDTO, ord);
				BeanUtils.copyProperties(rptMobPortAppDTO, ord);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, ord);
				BeanUtils.copyProperties(rptSecretarialServDTO, ord);
				BeanUtils.copyProperties(rptConciergeServiPadiPhoneDTO, ord);
				iPhoneTradeInFormDTO.setImei(ord.getImei());
				
				rptServiceGuideDTO.setAgreementNum(ord.getAgreementNum());
				try {
					uniOptionalVasInd=vasDetailDao.getVasSelected(ord.getOrderId());
				} catch (DAOException e) {
					uniOptionalVasInd=false;
				}
				rptCreditCardDDAuthOSDTO.setOrderId(ord.getOrderId());
				rptHSTradeDescDTOTemplate.setAgreementNum(ord.getAgreementNum());
				rptHSTradeDescDTOTemplate.setShowSignAndDate(false); //20130709
				try {
					hsTradeDescDTOs = hsTradeDescDAO.getHSTradeDescList(ord.getOrderId());
				} catch (DAOException e1) {
					hsTradeDescDTOs = null;
				}
				
				if (hsTradeDescDTOs != null ) {
					rptAppMobileServDTO.setDocAttTradeDesc(true);
				}
				
				if (StringUtils.contains(ord.getOrderId(), "SBO")) {
					isReqCreditCardAuthFormOS = true;
				}
				oid=ord.getOrderId();// 20131031 Athena Deposit
				
				isIphone7TradeInBasket = orderService.isIphone7TradeInBasket(oid,iphoneSevenTradeInList);
				isIphone8TradeInBasket = orderService.isIphone7TradeInBasket(oid,iphoneEightTradeInList);
				
			} else if (obj instanceof BomSalesUserDTO) {
				BeanUtils.copyProperties(rptAppMobileServDTO, obj);
				BeanUtils.copyProperties(rptCreditCardDDAuthDTO, obj);
			} else if (obj instanceof MobileSimInfoDTO) {
				BeanUtils.copyProperties(rptAppMobileServDTO, obj);
				BeanUtils.copyProperties(rptMobPortAppDTO, obj);
				// } else if (obj instanceof HSTradeDescDTO) {
				// isReqTradeDescForm = ((HSTradeDescDTO) obj) != null ? true :
				// false;
				// BeanUtils.copyProperties(rptRptHSTradeDescDTO, obj);
				// rptRptHSTradeDescDTO.setCompanyLogo(imageFilePath + "/" +
				// COMPANY_LOGO_FILE);
			} else if (obj instanceof SignoffDTO) {
				signature = (SignoffDTO) obj;
				rptAppMobileServDTO.setCustSignature(signature.getSignatureInputStream());
				rptAppMobileServDTO.getSectG().setCustSignatureAutoPay(signature.getSignatureInputStream());
				rptMobPortAppDTO.setCustSignature(signature.getSignatureInputStream());
				rptCreditCardDDAuthDTO.setCustSignature(signature.getSignatureInputStream());
				rptConciergeServiPadiPhoneDTO.setCustSignature(signature.getSignatureInputStream());
			} else if (obj instanceof MRTUI) {
				if ("A".equals(((MRTUI) obj).getMnpInd()) 
						|| "Y".equals(((MRTUI) obj).getMnpInd())) {
					isReqMnpForm = true;
				}
			} else if (obj instanceof StaffInfoUI) {
				if (((StaffInfoUI) obj).getOrderId().contains("SBO")) {
					rptAppMobileServDTO.setSalesName(((StaffInfoUI) obj).getSalesName());
				}
			} else if (obj instanceof List<?>) {
				 List<?> list = (List<?>) obj;
				 String ind = "N";
				 for (Object o : (List<?>) obj){
					if (o instanceof MultiSimInfoDTO){
						 ind = "Y";
						 break;
					 }
				 }
					if (ind.equalsIgnoreCase("Y")){
					 //MultiSim Athena 20140128 
							List<RptMultiSimDTO> rptMultiSimDTOList= new ArrayList<RptMultiSimDTO>();
					 		List<MultiSimInfoDTO> multiSimInfoDTOs= (List<MultiSimInfoDTO>) obj;
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
											if(StringUtils.isNotEmpty(sim)){
												try {
													rptMultiSimDTO.setMultiSimType(orderDao.getSimType(sim));
												} catch (DAOException e) {
													logger.error("Exception caught in mapReportData", e);
												}
											}
											rptMultiSimDTOList.add(rptMultiSimDTO);
												if ("A".equalsIgnoreCase(multiSimInfoMemberDTO.getMnpInd()) || "MUPS05".equals(multiSimInfoMemberDTO.getMemberOrderType())){
													RptMobPortAppDTO rptMultiSimMobPortAppDTO = new RptMobPortAppDTO();
													BeanUtils.copyProperties(rptMultiSimMobPortAppDTO, rptMobPortAppDTO);
													rptMultiSimMobPortAppDTO.setAgreementNum(multiSimInfoMemberDTO.getMemberOrderId());
													rptMultiSimMobPortAppDTO.setCustName(multiSimInfoMemberDTO.getMnpCustName());
													//rptMultiSimMobPortAppDTO.setCustNameChi("");
													rptMultiSimMobPortAppDTO.setMsisdn(multiSimInfoMemberDTO.getMnpNumber());
													rptMultiSimMobPortAppDTO.setCustIdDocNum(multiSimInfoMemberDTO.getMnpDocNo());
													rptMultiSimMobPortAppDTO.setDno(multiSimInfoMemberDTO.getMnpDno());
													rptMultiSimMobPortAppDTO.setCutoverDate(Utility.string2Date(multiSimInfoMemberDTO.getMnpCutOverDate()));
													rptMultiSimMobPortAppDTO.setCutoverTime(multiSimInfoMemberDTO.getMnpCutOverTime());
													rptMultiSimMobPortAppDTO.setMnpTicketNum(multiSimInfoMemberDTO.getMnpTicketNum());
													rptMultiSimMobPortAppDTO.setPrePaidSimDocWithCert("Y".equals(multiSimInfoMemberDTO.getPrePaidSimDocInd()));
													rptMultiSimMobPortAppDTO.setPrePaidSimDocWithoutCert("N".equals(multiSimInfoMemberDTO.getPrePaidSimDocInd()));
													multiSimMnpList.add(rptMultiSimMobPortAppDTO);
												
		
												}
											}
										}
									}
								}								
									
										rptAppMobileServDTO.setMultiSimList(rptMultiSimDTOList);
									} else {
									
										rptAppMobileServDTO.setMultiSimList(null);
									}
							}
						 
						 
						 
					 
				 }
		}

		if ("5".equals(rptAppMobileServDTO.getBasketType())) {
			isNEForm = true;
		}

		ArrayList<AdditionalChargeDTO> aChargeList = new ArrayList<AdditionalChargeDTO>();
		ArrayList<MiscellaneousChargeDTO> mChargeList = new ArrayList<MiscellaneousChargeDTO>();
		String configFiles = "";

		if (isNEForm) {
			configFiles = "NEreportConstant.xml";
		} else {
			configFiles = "reportConstant.xml";
		}

		ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFiles);

		if (isNEForm) {
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
		} else {
			if ("en".equals(rptAppMobileServDTO.getLocale())) {
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng1"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng2"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng3"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng4"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng5"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng6"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng7"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng8"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng9"));

				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng1"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng2"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng3"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng4"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng5"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng6"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng7"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng8"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng9"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng10"));

			} else if ("zh_HK".equals(rptAppMobileServDTO.getLocale())) {
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi1"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi2"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi3"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi4"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi5"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi6"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi7"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi8"));
				aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi9"));

				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi1"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi2"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi3"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi4"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi5"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi6"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi7"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi8"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi9"));
				mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi10"));
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
		
		//*******Paper bill Athena 20130925 end******//
		// 20131031 Athena Deposit start
		if (oid != null){		
			if ("en".equals(rptAppMobileServDTO.getLocale())) {
				List<DepositDTO> depositDTOList = depositService.findDepositDTOByOrderId(rptAppMobileServDTO.getAgreementNum(),"en");
				if (depositDTOList!= null && !depositDTOList.isEmpty()){
					//System.out.print(rptAppMobileServDTO.getFirstMonthFee());
					//System.out.print(rptAppMobileServDTO.getFirstMonthServiceLicenceFee());
					logger.info("1"+depositDTOList);
					List<DepositDTO> depositDTOList2= new ArrayList<DepositDTO>();
					//logger.info("2"+depositDTOList2);
					for(DepositDTO mbm: depositDTOList) {
							if (!"Y".equals(mbm.getWaiveInd())) {
								depositDTOList2.add(mbm);
							}
					}
					if (depositDTOList2!= null && !depositDTOList2.isEmpty()){
						logger.info("3"+depositDTOList2);
						rptAppMobileServDTO.setDepositList(depositDTOList2);
					}
				}
			}
			else if ("zh_HK".equals(rptAppMobileServDTO.getLocale())) {
				List<DepositDTO> depositDTOList = depositService.findDepositDTOByOrderId(rptAppMobileServDTO.getAgreementNum(),"zh_HK");
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
			
			
			BigDecimal depositTotal= depositService.getDepositAmountForOrder(rptAppMobileServDTO.getAgreementNum());
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
		
		try {
			List<MultipleMrtSimDTO> multiSimList 
				= orderDao.getMultipleMrtSimDTOList(rptAppMobileServDTO.getAgreementNum());
			if (multiSimList != null && multiSimList.size() > 0) {
				rptAppMobileServDTO.setHaveMultiSim(true);
				//multiSimList.add(0, new MultipleMrtSimDTO("PCCW Mobile Number", "SIM Number"));
				rptAppMobileServDTO.setMultipleMrtSimList(multiSimList);
			} else {
				rptAppMobileServDTO.setHaveMultiSim(false);
				rptAppMobileServDTO.setMultipleMrtSimList(null);
			}
		} catch (DAOException e1) {
			logger.debug("DAOException @ReportServiceImp: getMultipleMrtSimDTOList");
			e1.printStackTrace();
		} 			
		
		fillInDocAttSect(isReqMnpForm || !(multiSimMnpList== null || multiSimMnpList.isEmpty()), isReqSecSrvForm, isReqCreditCardAuthForm || isReqCreditCardAuthFormOS,
				isConSrvForm, rptAppMobileServDTO);
		
		fillSSStatements(rptAppMobileServDTO, isNEForm);
		
		if (isStudentPlanInd) {
			if (rptAppMobileServDTO.getSectG() != null && 
					(StringUtils.isBlank(rptAppMobileServDTO.getSectG().getPayMethodType())) || "M".equalsIgnoreCase(rptAppMobileServDTO.getSectG().getPayMethodType())) {
				rptAppMobileServDTO.getSectG().setPayMethodType("C");
				rptAppMobileServDTO.getSectG().setCreditCardType("02");
				rptAppMobileServDTO.getSectG().setCreditCardHolderName(rptAppMobileServDTO.getCustLastName() + " " + rptAppMobileServDTO.getCustFirstName());
				rptAppMobileServDTO.getSectG().setCreditCardNum("559911XXXXXX[    ][    ][    ][    ]");
			}
		}
		String key = "";
		if (isNEForm) {
			key = RptAppMobileServDTO.JASPER_TEMPLATE_NE
					+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		} else if (isStudentPlanInd) {
			rptAppMobileServDTO.setSectionH(reportService.getReportContentHtml("CY001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "sectionH"));
			
			key = RptAppMobileServDTO.JASPER_TEMPLATE_CY
					+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		} else if (isUniversityPlan){
			rptAppMobileServDTO.setSectionH(reportService.getReportContentHtml("UNI001", (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? "zh_HK" : "en"), "sectionH"));
			key = RptAppMobileServDTO.JASPER_TEMPLATE_UNI
					+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		}else {
			key = RptAppMobileServDTO.JASPER_TEMPLATE
					+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		}
		
		ssList.add(rptAppMobileServDTO);
		rptAppMobileServDTO.setOrderType("ACT");//set order type to ACT
		BeanUtils.copyProperties(rptAppMobileServCustCopyDTO, rptAppMobileServDTO);
		if (signature != null) {
			rptAppMobileServCustCopyDTO.setCustSignature(signature.getSignatureInputStream());
			rptAppMobileServCustCopyDTO.getSectG().setCustSignatureAutoPay(signature.getSignatureInputStream());
		}

		rptAppMobileServCustCopyDTO.setCustomerCopyInd("Y");
		ssCustCopyList.add(rptAppMobileServCustCopyDTO);
		
		map.put(key, new JasperReport(key, ssList));
		map.put(key + CUST_COPY, new JasperReport(key, ssCustCopyList));
		
		fillCCOSStatements(rptCreditCardDDAuthOSDTO);
		
		fillCCStatements(rptCreditCardDDAuthDTO);

		if (isReqCreditCardAuthForm) {
			if (isReqCreditCardAuthFormOS) {
				ccOSList.add(rptCreditCardDDAuthOSDTO);
				map.put(RptCreditCardDDAuthOSDTO.JASPER_TEMPLATE, new JasperReport(
						RptCreditCardDDAuthOSDTO.JASPER_TEMPLATE, ccOSList));
			} else {
				ccList.add(rptCreditCardDDAuthDTO);
				map.put(RptCreditCardDDAuthDTO.JASPER_TEMPLATE, new JasperReport(
						RptCreditCardDDAuthDTO.JASPER_TEMPLATE, ccList));
			}
		
		} else {
			if (isReqCreditCardAuthFormOS) {
				ccOSList.add(rptCreditCardDDAuthOSDTO);
				map.put(RptCreditCardDDAuthOSDTO.JASPER_TEMPLATE, new JasperReport(
						RptCreditCardDDAuthOSDTO.JASPER_TEMPLATE, ccOSList));
			}
		}

/*		if (isReqMnpForm && !isNEForm) {
			if (isMNPnNewNum) {
				rptMobPortAppDTO.setMsisdn(mnpMsisdn);
			}
			mnpList.add(rptMobPortAppDTO);
			map.put(RptMobPortAppDTO.JASPER_TEMPLATE, new JasperReport(
					RptMobPortAppDTO.JASPER_TEMPLATE, mnpList));
		}*/
		
		
		
		if (isReqMnpForm && !isNEForm) {
			if (!(multiSimMnpList== null || multiSimMnpList.isEmpty())){//MultiSim Athena 20140128
				if (isMNPnNewNum) {
					rptMobPortAppDTO.setMsisdn(mnpMsisdn);
				}
				mnpList.add(rptMobPortAppDTO);
				mnpList.addAll((ArrayList)multiSimMnpList);
				map.put(RptMobPortAppDTO.JASPER_TEMPLATE, new JasperReport(
						RptMobPortAppDTO.JASPER_TEMPLATE, mnpList));
			}else{
				if (isMNPnNewNum) {
					rptMobPortAppDTO.setMsisdn(mnpMsisdn);
				}
				mnpList.add(rptMobPortAppDTO);
				map.put(RptMobPortAppDTO.JASPER_TEMPLATE, new JasperReport(
						RptMobPortAppDTO.JASPER_TEMPLATE, mnpList));
			}
			
		}else{
			if (!(multiSimMnpList== null || multiSimMnpList.isEmpty())){//MultiSim Athena 20140128
				
				map.put(RptMobPortAppDTO.JASPER_TEMPLATE, new JasperReport(
						RptMobPortAppDTO.JASPER_TEMPLATE, (ArrayList)multiSimMnpList));
			}
		}
		
		if (isReqSecSrvForm) {
			secSrvList.add(rptSecretarialServDTO);
			map.put(RptSecretarialServDTO.JASPER_TEMPLATE, new JasperReport(
					RptSecretarialServDTO.JASPER_TEMPLATE, secSrvList));
		}
		
		if (hsTradeDescDTOs != null) {
			for (HSTradeDescDTO hsTradeDescDto: hsTradeDescDTOs) {
				RptHSTradeDescDTO rptHSTradeDescDTO = new RptHSTradeDescDTO();
				BeanUtils.copyProperties(rptHSTradeDescDTO, rptHSTradeDescDTOTemplate);
				BeanUtils.copyProperties(rptHSTradeDescDTO, hsTradeDescDto);
				hsTradeDescList.add(rptHSTradeDescDTO);
			}
			map.put(RptHSTradeDescDTO.JASPER_TEMPLATE, new JasperReport(
					RptHSTradeDescDTO.JASPER_TEMPLATE, hsTradeDescList));
		}

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

			String rptName = RptConciergeServiPadiPhoneDTO.JASPER_TEMPLATE
					+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
			conSrvList.add(rptConciergeServiPadiPhoneDTO);
			map.put(rptName, new JasperReport(rptName, conSrvList));
		}
		serviceGuideList.add(rptServiceGuideDTO);
		if (rptAppMobileServDTO.getShopCd().contains("SBO")) {
			if (isUniversityPlan) {
				if (uniOptionalVasInd){
				map.put(RptServiceGuideDTO.JASPER_TEMPLATE_Uni + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""),
						new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_Uni
								+ (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), serviceGuideList));
				}
			}else{
				if (isNEForm) {
					map.put(RptServiceGuideDTO.JASPER_TEMPLATE_NE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
							new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE_NE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
											serviceGuideList));
				} else {
					map.put(RptServiceGuideDTO.JASPER_TEMPLATE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
							new JasperReport(RptServiceGuideDTO.JASPER_TEMPLATE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), 
											serviceGuideList));
				}
			}
		}

	
		if (isIphone7TradeInBasket || isIphone8TradeInBasket) {
			iPhoneTradeInFormDTO.setOrderType("Y");
			iPhoneTradeInFormDTO.setSignatureInd("Y");
			iPhoneTradeInFormDTO.setMobCustNum("");
			iPhoneTradeInFormDTO.setpdf(pLang, isIphone8TradeInBasket,brand);
			BeanUtils.copyProperties(iPhoneTradeInFormCustCopyDTO, iPhoneTradeInFormDTO);
			iPhoneTradeInFormDTO.setCustomerCopyInd("N");
			iPhoneTradeInFormCustCopyDTO.setCustomerCopyInd("Y");
			iPhoneTradeInFormDTOList.add(iPhoneTradeInFormDTO);
			
			iPhoneTradeInFormCustCopyDTOList.add(iPhoneTradeInFormCustCopyDTO);
			
			if (isIphone7TradeInBasket){
			map.put(IPhoneTradeInFormDTO.JASPER_TEMPLATE, new JasperReport(
					IPhoneTradeInFormDTO.JASPER_TEMPLATE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), iPhoneTradeInFormDTOList));
			
			map.put(IPhoneTradeInFormDTO.JASPER_TEMPLATE + CUST_COPY, new JasperReport(
					IPhoneTradeInFormDTO.JASPER_TEMPLATE + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), iPhoneTradeInFormCustCopyDTOList));
			}else if (isIphone8TradeInBasket){
				
			map.put(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8, new JasperReport(
					IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8 + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), iPhoneTradeInFormDTOList));
			
			map.put(IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8 + CUST_COPY, new JasperReport(
					IPhoneTradeInFormDTO.JASPER_TEMPLATE_IPHONE8 + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : ""), iPhoneTradeInFormCustCopyDTOList));
			}
		}
		return map;
	}

	private class JasperReport {
		private String jasperTemplateName = null;
		private ArrayList<ReportDTO> rptDtoArrList = null;

		public JasperReport(String pJasperTemplateName,
				ArrayList<ReportDTO> pRptDtoArrList) {
			jasperTemplateName = pJasperTemplateName;
			rptDtoArrList = pRptDtoArrList;
		}

		protected String getJasperTemplateName() {
			return jasperTemplateName;
		}

		protected ArrayList<ReportDTO> getRptDtoArrList() {
			return rptDtoArrList;
		}

		public void setRptDtoArrList(ArrayList<ReportDTO> rptDtoArrList) {
			this.rptDtoArrList = rptDtoArrList;
		}
		
		
	}
	
	/**
	 * generate inputStream list for S&S Form  <br>
	 * formType - 1 = S&S & NE, 2 = Credit Card, 3 = Concierge,
	 * 				4 = MNP, 5 = Secretary
	 */
	private ArrayList<InputStream> getPDFSSFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, short formType, boolean isCustCopy, boolean isOnlineSales, String osOrderId, String brand) {

		logger.info("getPDFSSFromStreamList is called");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			HashMap<String, JasperReport> reportDataMap = mapReportData(
					pReportArrList, pLang);
			
			String key = null;
			JasperReport keyValue = null;
			
			switch (formType) {
				//********** S&S form OR NE form (Eng, Chi, CustCopy)**********//
				case 1: 
						if (isCustCopy) {
							if (ssCustCopySeq == null) {
								initSSCustCopySeq();
							}
							for (int i = 0; i < ssCustCopySeq.size(); i++) {
								key = ssCustCopySeq.get(i);
								keyValue = reportDataMap.get(key);
								if (keyValue != null) {
									pdfStreamList.add(generatePdfInputStream(
											keyValue.getJasperTemplateName(),
											keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
								}
							}
						} else {
							if (ssOffCopySeq == null) {
								initSSOffCopySeq();
							}
							for (int i = 0; i < ssOffCopySeq.size(); i++) {
								key = ssOffCopySeq.get(i);
								keyValue = reportDataMap.get(key);
								if (keyValue != null) {
									pdfStreamList.add(generatePdfInputStream(
											keyValue.getJasperTemplateName(),
											keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
								}
							}
						}
						break;
						
				//********** Credit Card form (only 1) **********//
				case 2: 
						keyValue = reportDataMap == null? 
									null :
									(isOnlineSales?
											reportDataMap.get(RptCreditCardDDAuthOSDTO.JASPER_TEMPLATE) :
											reportDataMap.get(RptCreditCardDDAuthDTO.JASPER_TEMPLATE));
						if (keyValue != null) {
							pdfStreamList.add(generatePdfInputStream(
									keyValue.getJasperTemplateName(),
									keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
						}
						break;
						
				//********** Concierge form (Eng or Chi) **********//
				case 3:
						if (conciergeSeq == null) {
							initConciergeSeq();
						}
						for (int i = 0; i < conciergeSeq.size(); i++) {
							key = conciergeSeq.get(i);
							keyValue = reportDataMap.get(key);
							if (keyValue != null) {
								pdfStreamList.add(generatePdfInputStream(
										keyValue.getJasperTemplateName(),
										keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
							}
						}
						break;
						
				//********** MNP form (only 1) **********//
				case 4: 
						keyValue = reportDataMap == null? 
									null : 
									reportDataMap.get(RptMobPortAppDTO.JASPER_TEMPLATE);
						if (keyValue != null) {
							pdfStreamList.add(generatePdfInputStream(
									keyValue.getJasperTemplateName(),
									keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
						}
						break;
						
				//********** Secretary form (only 1) **********//
				case 5: 
						keyValue = reportDataMap == null? 
									null : 
									reportDataMap.get(RptSecretarialServDTO.JASPER_TEMPLATE);
						if (keyValue != null) {
							pdfStreamList.add(generatePdfInputStream(
									keyValue.getJasperTemplateName(),
									keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
						}
						break;
						
				//********** Service Guide (only 1) **********//
				case 6: 
						if (servGuideCopySeq == null) {
							initServGuideCopySeq();
						}
						for (int i = 0; i < servGuideCopySeq.size(); i++) {
							key = servGuideCopySeq.get(i);
							keyValue = reportDataMap.get(key);
							
							if (keyValue != null) {
								if(key.equals("ServiceGuide") || key.equals("ServiceGuide_zh")){
									File M2ServiceGuide = new File ("");
									if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
										if("en".equals(pLang)){
											M2ServiceGuide =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.RetServiceGuide_1010_eng);
										}
										else{
											M2ServiceGuide =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.RetServiceGuide_1010_chi);
										}
									}
									else{
										if("en".equals(pLang)){
											M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.RetServiceGuide_csl_eng);
										}
										else{
											M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.RetServiceGuide_csl_chi);
										}
										
									}
									ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
									ServiceGuide.add(new FileInputStream(M2ServiceGuide));

									pdfStreamList.addAll(ServiceGuide);
									
								}
								else if (key.equals("M2ServiceGuideUni") || key.equals("M2ServiceGuideUni_zh")){
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
									
								}
								else{
								
									pdfStreamList.add(generatePdfInputStream(
										keyValue.getJasperTemplateName(),
										keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
								}
							}
							
							/*
							File M2ServiceGuide = new File ("");
							if (keyValue != null){
									if("en".equals(pLang)){
										M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.M2ServiceGuide_csl_eng);
									}
									else{
										M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.M2ServiceGuide_csl_chi);
									}
								
							}
							ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
							ServiceGuide.add(new FileInputStream(M2ServiceGuide));

							pdfStreamList.addAll(ServiceGuide);
							*/
							
						}
						break;
				//********** HS Trade Description (only 1) **********//
				case 7: 
						keyValue = reportDataMap == null? 
									null : 
									reportDataMap.get(RptHSTradeDescDTO.JASPER_TEMPLATE);
						int hsTradeFormSeq = 0;
						if (keyValue != null) {
							
							List<HSTradeDescDTO> hsTradeDescList = hsTradeDescDAO.getHSTradeDescList(osOrderId);
							for (HSTradeDescDTO hsTradeDesc:hsTradeDescList){
								ArrayList<InputStream> hsTradeDescForm = new ArrayList<InputStream>(); 
								if (StringUtils.isNotEmpty(hsTradeDesc.getPisPath())){
									hsTradeDescForm.add(new FileInputStream(mobPdfFilePath+"/pis/" +hsTradeDesc.getPisPath()));
									pdfStreamList.addAll(hsTradeDescForm);
									hsTradeFormSeq++;
								}else{
									ArrayList<ReportDTO> rptDtoArrList = new ArrayList<ReportDTO>();
									rptDtoArrList.add(keyValue.getRptDtoArrList().get(hsTradeFormSeq));
									JasperReport hsTradeForm  = new JasperReport(RptHSTradeDescDTO.JASPER_TEMPLATE,rptDtoArrList);
									pdfStreamList.add(generatePdfInputStream(
											keyValue.getJasperTemplateName(),
											hsTradeForm.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
									hsTradeFormSeq++;
								}
							}
							/*pdfStreamList.add(generatePdfInputStream(
									keyValue.getJasperTemplateName(),
									keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));*/
						}
						break;
						
				case 8: 
					if (isCustCopy) {
						if (iPhoneTradeInFormCustSeq == null) {
							initIPhoneTradeInFormCustSeq();
						}
						for (int i = 0; i < iPhoneTradeInFormCustSeq.size(); i++) {
							key = iPhoneTradeInFormCustSeq.get(i);
							keyValue = reportDataMap.get(key);
							if (keyValue != null) {
								pdfStreamList.add(generatePdfInputStream(
										keyValue.getJasperTemplateName(),
										keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
							}
						}
					} else {
						if (iPhoneTradeInFormOffSeq == null) {
							initIPhoneTradeInFormOffSeq();
						}
						for (int i = 0; i < iPhoneTradeInFormOffSeq.size(); i++) {
							key = iPhoneTradeInFormOffSeq.get(i);
							keyValue = reportDataMap.get(key);
							if (keyValue != null) {
								pdfStreamList.add(generatePdfInputStream(
										keyValue.getJasperTemplateName(),
										keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
							}
						}
					}
					break;		
			}

			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFSSFromStreamList()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	//add by Eliot 20120509
	/**
	 * generate inputStream list for DOA Delivery Note  
	 */
	private ArrayList<InputStream> getPDFDOADeliveryFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId, String brand) {

		logger.info("getPDFDOADeliveryFromStreamList is called in MobCcsReportServiceImpl.java");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			HashMap<String, JasperReport> reportDataMap = mapCCSDOADeliveryReportData(
					pReportArrList, pLang,brand);

			String key = isCustCopy?
							MobCcsDOADeliveryNoteDTO.JASPER_TEMPLATE_DN + CUST_COPY
							: MobCcsDOADeliveryNoteDTO.JASPER_TEMPLATE_DN;

			JasperReport keyValue = reportDataMap == null? null : reportDataMap.get(key);

			if (keyValue != null) {
				pdfStreamList.add(generatePdfInputStream(
						keyValue.getJasperTemplateName(),
						keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_CCS,pLang, osOrderId, brand));
				
				//add the new form for SalesMemo
				File DeliveryNote = new File ("");
				if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
					
						DeliveryNote =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.SalesMemo_1010);
				}
				else{			
						DeliveryNote =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.SalesMemo_csl);
					
				}
				ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
				ServiceGuide.add(new FileInputStream(DeliveryNote));

				pdfStreamList.addAll(ServiceGuide);
			}

			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFDOADeliveryFromStreamList()",
					de);
			throw new AppRuntimeException(de);
		}

	}
	
	/**
	 * generate inputStream list (Jasper with data assigned) for Delivery note  
	 */
	private ArrayList<InputStream> getPDFDeliveryFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId, String brand) { 

		logger.info("getPDFDeliveryFromStreamList is called");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			HashMap<String, JasperReport> reportDataMap = mapCCSDeliveryReportData(
					pReportArrList, pLang, brand);

			String key = isCustCopy?
							MobCcsDeliveryNoteDTO.JASPER_TEMPLATE_DN + CUST_COPY
							: MobCcsDeliveryNoteDTO.JASPER_TEMPLATE_DN;

			JasperReport keyValue = reportDataMap == null? null : reportDataMap.get(key);

			if (keyValue != null) {
				pdfStreamList.add(generatePdfInputStream(
						keyValue.getJasperTemplateName(),
						keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_CCS,pLang, osOrderId, brand));
				
				//add the new form for SalesMemo
				File DeliveryNote = new File ("");
				if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
					
						DeliveryNote =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.SalesMemo_1010);
				}
				else{
					
						DeliveryNote =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.SalesMemo_csl);
					
				}
				ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
				ServiceGuide.add(new FileInputStream(DeliveryNote));

				pdfStreamList.addAll(ServiceGuide);
			}

			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFDeliveryFromStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	/**
	 * generate inputStream list for Courier Guid   
	 */
	private ArrayList<InputStream> getPDFCourierGuidFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, String osOrderId, String brand) {

		logger.info("getPDFCourierGuidFromStreamList is called");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			JasperReport jasperData = ccsCourierGuidReportData(pReportArrList,
					pLang);

			pdfStreamList.add(generatePdfInputStream(
					MobCcsCourierDeliveryGuidDTO.JASPER_TEMPLATE_CG,
					jasperData.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_CCS,pLang, osOrderId, brand));

			return pdfStreamList;
		} catch (Exception de) {
			logger.error(
					"Exception caught in getPDFCourierGuidFromStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	/**
	 * generate inputStream list for Change Service Form  
	 */
	private ArrayList<InputStream> getPDFChangeServiceFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId, String brand) {

		logger.info("getPDFChangeServiceFromStreamList is called");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			HashMap<String, JasperReport> reportDataMap = mapCCSChangeServiceAndRefundData(
					pReportArrList, pLang);

			String key = isCustCopy?
							MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS + CUST_COPY
							: MobCcsChgServCustInfoRefundDTO.JASPER_TEMPLATE_CS;

			JasperReport keyValue = reportDataMap == null? null : reportDataMap.get(key);
			
			if (keyValue != null) {
				pdfStreamList.add(generatePdfInputStream(
						keyValue.getJasperTemplateName(),
						keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
			}

			return pdfStreamList;
		} catch (Exception de) {
			logger.error(
					"Exception caught in getPDFChangeServiceFromStreamList()",
					de);
			throw new AppRuntimeException(de);
		}
	}
	
	private ArrayList<InputStream> getPDFTradeInHSFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId, String brand) {
		logger.info("getPDFTradeInHSFromStreamList is called in MobCcsReportServiceImpl.java");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			//ArrayList<String> reportMobileSafetySeq = new ArrayList<String>();
			//reportMobileSafetySeq.add(RptMobileSafetyPhoneDTO.JASPER_TEMPLATE);

			JasperReport mobileSafetyReport = getTradeInHSReport(pReportArrList, pLang);

			if (mobileSafetyReport != null) {
				pdfStreamList.add(generatePdfInputStream(
						mobileSafetyReport.getJasperTemplateName(),
						mobileSafetyReport.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
			}
			
			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFIGuardFromStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private ArrayList<InputStream> getPDFMobileSafetyFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId, String brand) {
		logger.info("getPDFMobileSafetyFromStreamList is called in MobCcsReportServiceImpl.java");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			//ArrayList<String> reportMobileSafetySeq = new ArrayList<String>();
			//reportMobileSafetySeq.add(RptMobileSafetyPhoneDTO.JASPER_TEMPLATE);

			JasperReport mobileSafetyReport = getMobileSafetyReport(pReportArrList, pLang, 1);

			if (mobileSafetyReport != null) {
				pdfStreamList.add(generatePdfInputStream(
						mobileSafetyReport.getJasperTemplateName(),
						mobileSafetyReport.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
			}
			
			JasperReport mobileSafetySuppReport = getMobileSafetyReport(pReportArrList, pLang, 2);

			if (mobileSafetySuppReport != null) {
				pdfStreamList.add(generatePdfInputStream(
						mobileSafetySuppReport.getJasperTemplateName(),
							mobileSafetySuppReport.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
			}
			
			JasperReport mobileSafetyTnCReport = getMobileSafetyReport(pReportArrList, pLang, 3);

			if (mobileSafetyTnCReport != null) {
				pdfStreamList.add(generatePdfInputStream(
						mobileSafetyTnCReport.getJasperTemplateName(),
						mobileSafetyTnCReport.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
			}


			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFMobileSafetyFromStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private ArrayList<InputStream> getPDFNFCSimFormStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId, String brand) {
		logger.info("getPDFNFCSimFormStreamList is called in MobCcsReportServiceImpl.java");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			JasperReport nfcSimReport = getNFCSimReport(pReportArrList, pLang);
			//JasperReport octopusTnCReport = getOctopusSimTnCReport(pReportArrList, pLang);
			
			if (nfcSimReport != null) {
				pdfStreamList.add(generatePdfInputStream(
						nfcSimReport.getJasperTemplateName(),
						nfcSimReport.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
			}
			
			/*if (octopusTnCReport != null) {
				if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
					pdfStreamList.add(generatePdfInputStream(
							octopusTnCReport.getJasperTemplateName() + FORM_ZH_EXT,
							octopusTnCReport.getRptDtoArrList(), JASPER_PATH_MOB,pLang, osOrderId));
				} else {
					pdfStreamList.add(generatePdfInputStream(
							octopusTnCReport.getJasperTemplateName(),
							octopusTnCReport.getRptDtoArrList(), JASPER_PATH_MOB,pLang, osOrderId));
				}
			}*/
			
			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFNFCSimFormStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	/*private ArrayList<InputStream> getPDFOctopusSimFormStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId) {
		logger.info("getPDFOctopusSimFormStreamList is called in MobCcsReportServiceImpl.java");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			JasperReport octopusSimReport = getOctopusSimReport(pReportArrList, pLang);

			if (octopusSimReport != null) {
				pdfStreamList.add(generatePdfInputStream(
						octopusSimReport.getJasperTemplateName(),
						octopusSimReport.getRptDtoArrList(), JASPER_PATH_MOB,pLang, osOrderId));
			}
			
			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFOctopusSimFormStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}*/
	
	private ArrayList<InputStream> getPDFIGuardFromStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId ,String iGuardType, String brand) {

		logger.info("getPDFIGuardFromStreamList is called in MobCcsReportServiceImpl.java");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			HashMap<String, JasperReport> reportDataMap = mapIGuardReportData(
					pReportArrList, pLang, iGuardType);

			reportIGuardCustCopySeq = null;
			if (isCustCopy) {
				initIGuardCustCopySeq();
			} else {
				reportIGuardCustCopySeq = new ArrayList<String>();
				reportIGuardCustCopySeq.add(IGuardDTO.JASPER_TEMPLATE_LDS);
				reportIGuardCustCopySeq.add(IGuardDTO.JASPER_TEMPLATE_AD);
				reportIGuardCustCopySeq.add(RptIGuardCareCashDTO.JASPER_TEMPLATE);
			}

			String key = null;
			JasperReport keyValue = null;

			for (int i = 0; i < reportIGuardCustCopySeq.size(); i++) {

				key = reportIGuardCustCopySeq.get(i);
				keyValue = reportDataMap.get(key);
				if (keyValue != null) {
					pdfStreamList.add(generatePdfInputStream(
							keyValue.getJasperTemplateName(),
							keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB,pLang, osOrderId, brand));
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
				}
				
			}

			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFIGuardFromStreamList()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	/**
	 * generate inputStream list (Jasper with data assigned) for TnGServiceForm  
	 */
	private ArrayList<InputStream> getPDFTnGServiceFormStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String orderId, String brand) {

		logger.info("getPDFTnGServiceFormStreamList is called");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {

			
			/*JasperReport getTnGServiceFormReport = getTnGServiceFormReport(pReportArrList, pLang);
			
			if (getTnGServiceFormReport != null) {
				pdfStreamList.add(generatePdfInputStream(
						getTnGServiceFormReport.getJasperTemplateName(),
						getTnGServiceFormReport.getRptDtoArrList(), JASPER_PATH_CCS,pLang, orderId));
			}
			*/
			
			HashMap<String, JasperReport> reportDataMap = mapTnGServiceFormData(
					pReportArrList, pLang);

			String key = isCustCopy?
							RptTnGServiceFormDTO.JASPER_TEMPLATE + CUST_COPY
							: RptTnGServiceFormDTO.JASPER_TEMPLATE;

			JasperReport keyValue = reportDataMap == null? null : reportDataMap.get(key);

			if (keyValue != null) {
				pdfStreamList.add(generatePdfInputStream(
						keyValue.getJasperTemplateName(),
						keyValue.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_CCS,pLang, orderId, brand));
			}

			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFTnGServiceFormStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	private JasperReport getMobileSafetyReport(ArrayList<Object> pDTOs, String pLang, int page)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("getMobileSafetyReport is called in MobCcsReportServiceImpl.java");
		
		ArrayList<ReportDTO> mobileSafetyList = new ArrayList<ReportDTO>(); 		
		
		JasperReport result = null;
		Object tempObj = null;
		
		RptMobileSafetyPhoneDTO mobileSafetyDTO = null; 
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);
			if (tempObj instanceof RptMobileSafetyPhoneDTO) {
				//BeanUtils.copyProperties(mobileSafetyDTO, tempObj);
				mobileSafetyDTO = (RptMobileSafetyPhoneDTO)tempObj;
			}
		}
		if (mobileSafetyDTO == null) return null;
		
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			mobileSafetyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			mobileSafetyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
		} else {
			mobileSafetyDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			mobileSafetyDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
		}
		mobileSafetyDTO.setMobileSafetyLogo(imageFilePath + "/" + MOBILE_SAFETY_PHONE_FILE);
		
		if (page==1) {		
			mobileSafetyList.add(mobileSafetyDTO);
			result = new JasperReport(RptMobileSafetyPhoneDTO.JASPER_TEMPLATE, mobileSafetyList); 
		} else if (page == 2) {
			RptMobileSafetyPhoneSuppAppDTO mobileSafetySuppDTO = new RptMobileSafetyPhoneSuppAppDTO();
			BeanUtils.copyProperties(mobileSafetySuppDTO, mobileSafetyDTO);
			mobileSafetyList.add(mobileSafetySuppDTO);
			result = new JasperReport(RptMobileSafetyPhoneSuppAppDTO.JASPER_TEMPLATE, mobileSafetyList); 
		} else if (page == 3) {
			RptMobileSafetyPhoneTnCDTO mobileSafetyTnCDTO = new RptMobileSafetyPhoneTnCDTO();
			BeanUtils.copyProperties(mobileSafetyTnCDTO, mobileSafetyDTO);
			mobileSafetyTnCDTO.setMonthlyRate("0");
			mobileSafetyTnCDTO.setMonthlyRateAfter("0");
			mobileSafetyTnCDTO.setCommitmentPeriod("1");
			for (int i = 0; i < pDTOs.size(); i++) {
				tempObj = pDTOs.get(i);
				if (tempObj instanceof ServicePlanReportDTO) {
					ServicePlanReportDTO servicePlanReport = new ServicePlanReportDTO();
					BeanUtils.copyProperties(servicePlanReport, tempObj);
					if (servicePlanReport.getMainServDtls() != null) {
						if (servicePlanReport.getMainServDtls().size() > 0) {
							VasDetailDTO vasDetailDTO = servicePlanReport.getMainServDtls().get(0);
							mobileSafetyTnCDTO.setMonthlyRate(vasDetailDTO.getItemRecurrentAmt());
							mobileSafetyTnCDTO.setMonthlyRateAfter(vasDetailDTO.getItemRecurrentAmt());
						}
					}
				}
				if (tempObj instanceof BasketDTO) {
					BasketDTO basketDto = new BasketDTO();
					BeanUtils.copyProperties(basketDto, tempObj);
					mobileSafetyTnCDTO.setCommitmentPeriod(basketDto.getContractPeriod());
				}
			}
						
			mobileSafetyList.add(mobileSafetyTnCDTO);
			result = new JasperReport(RptMobileSafetyPhoneTnCDTO.JASPER_TEMPLATE, mobileSafetyList); 
		}
		return result;
	}
	
	/*private JasperReport getOctopusSimReport(ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("getNFCSimReport is called in MobCcsReportServiceImpl.java");
		
		ArrayList<ReportDTO> nfcSimList = new ArrayList<ReportDTO>(); 		
		
		JasperReport result = null;
		Object tempObj = null;
		
		RptOctopusConsentDTO rptOctopusConsentDTO = null; 
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);
			if (tempObj instanceof RptOctopusConsentDTO) {
				rptOctopusConsentDTO = (RptOctopusConsentDTO)tempObj;
			}
		}
		if (rptOctopusConsentDTO == null) return null;
		
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			rptOctopusConsentDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptOctopusConsentDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
			rptOctopusConsentDTO.setOctFormP1(imageFilePath + "/" + OCT_CONSENT_FORM_P1_CHI);//Athena 20130924
			rptOctopusConsentDTO.setOctFormP2(imageFilePath + "/" + OCT_CONSENT_FORM_P2_CHI);//Athena 20130924
			
		} else {
			rptOctopusConsentDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptOctopusConsentDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
			rptOctopusConsentDTO.setOctFormP1(imageFilePath + "/" + OCT_CONSENT_FORM_P1_ENG);//Athena 20130924
			rptOctopusConsentDTO.setOctFormP2(imageFilePath + "/" + OCT_CONSENT_FORM_P2_ENG);//Athena 20130924
		}
			
		nfcSimList.add(rptOctopusConsentDTO);
		result = new JasperReport(RptOctopusConsentDTO.JASPER_TEMPLATE, nfcSimList); 
		
		return result;
	}*/
	
	private JasperReport getNFCSimReport(ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("getNFCSimReport is called in MobCcsReportServiceImpl.java");
		
		ArrayList<ReportDTO> nfcSimList = new ArrayList<ReportDTO>(); 		
		
		JasperReport result = null;
		Object tempObj = null;
		
		RptNFCConsentDTO rptNFCConsentDTO = null; 
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);
			if (tempObj instanceof RptNFCConsentDTO) {
				rptNFCConsentDTO = (RptNFCConsentDTO)tempObj;
			}
		}
		if (rptNFCConsentDTO == null) return null;
		
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			rptNFCConsentDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptNFCConsentDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
		} else {
			rptNFCConsentDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptNFCConsentDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
		}
			
		nfcSimList.add(rptNFCConsentDTO);
		result = new JasperReport(RptNFCConsentDTO.JASPER_TEMPLATE, nfcSimList); 
		
		return result;
	}
	
	/*private JasperReport getOctopusSimTnCReport(ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("getOctopusSimTnCReport is called in MobCcsReportServiceImpl.java");
		
		ArrayList<ReportDTO> octopusTnCList = new ArrayList<ReportDTO>(); 		
		
		JasperReport result = null;
		Object tempObj = null;
		
		RptOctopusTnCDTO rptOctopusTnCDTO = null; 
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);
			if (tempObj instanceof RptOctopusTnCDTO) {
				rptOctopusTnCDTO = (RptOctopusTnCDTO)tempObj;
			}
		}
		if (rptOctopusTnCDTO == null) return null;
		
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			rptOctopusTnCDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptOctopusTnCDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
		} else {
			rptOctopusTnCDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptOctopusTnCDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
		}
			
		octopusTnCList.add(rptOctopusTnCDTO);
		result = new JasperReport(RptOctopusTnCDTO.JASPER_TEMPLATE, octopusTnCList); 
		
		return result;
	}*/
	
	private JasperReport getTradeInHSReport(ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("getTradeInHSReport is called in MobCcsReportServiceImpl.java");
		
		ArrayList<ReportDTO> tradeInHSList = new ArrayList<ReportDTO>(); 		
		
		JasperReport result = null;
		Object tempObj = null;
		
		RptTradeInHSDTO tradeInHSDTO = null; 
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);
			if (tempObj instanceof RptTradeInHSDTO) {
				//BeanUtils.copyProperties(mobileSafetyDTO, tempObj);
				tradeInHSDTO = (RptTradeInHSDTO)tempObj;
			}
		}
		if (tradeInHSDTO == null) return null;
		
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			tradeInHSDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			tradeInHSDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
		} else {
			tradeInHSDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			tradeInHSDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
		}
		
		tradeInHSList.add(tradeInHSDTO);
		result = new JasperReport(RptTradeInHSDTO.JASPER_TEMPLATE, tradeInHSList); 
		
		return result;
	}
	
	
	private JasperReport getTnGServiceFormReport(ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("getTnGServiceFormReport is called in MobCcsReportServiceImpl.java");
		
		ArrayList<ReportDTO> rptTnGServiceFormList = new ArrayList<ReportDTO>(); 		
		
		JasperReport result = null;
		Object tempObj = null;
		
		RptTnGServiceFormDTO rptTnGServiceFormDTO = null; 
		RptTnGServiceFormDTO rptTnGServiceFormCustCopyDTO = null;
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);
			if (tempObj instanceof RptTnGServiceFormDTO) {
				//BeanUtils.copyProperties(mobileSafetyDTO, tempObj);
				rptTnGServiceFormDTO = (RptTnGServiceFormDTO)tempObj;
				rptTnGServiceFormCustCopyDTO = (RptTnGServiceFormDTO)tempObj;
			}
		}
		if (rptTnGServiceFormDTO == null) return null;
		
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			rptTnGServiceFormDTO.setCompanyLogo(imageFilePath + "/" + TNG_TOP_LOGO);
			rptTnGServiceFormDTO.setCompanyBottomLeftLogo(imageFilePath + "/" + TNG_BOTTOM_LT_LOGO);
			rptTnGServiceFormDTO.setCompanyBottomLogo(imageFilePath + "/" + TNG_BOTTOM_RT_LOGO);
		} else {
			rptTnGServiceFormDTO.setCompanyLogo(imageFilePath + "/" + TNG_TOP_LOGO);
			rptTnGServiceFormDTO.setCompanyBottomLeftLogo(imageFilePath + "/" + TNG_BOTTOM_LT_LOGO);
			rptTnGServiceFormDTO.setCompanyBottomLogo(imageFilePath + "/" + TNG_BOTTOM_RT_LOGO);
		}
		rptTnGServiceFormCustCopyDTO.setCustomerCopyInd("N");
		rptTnGServiceFormDTO.setIdDocNum(maskedDocNum(rptTnGServiceFormDTO.getIdDocNum()));

		BeanUtils.copyProperties(rptTnGServiceFormCustCopyDTO, rptTnGServiceFormDTO);
		
		
		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			rptTnGServiceFormCustCopyDTO.setCompanyLogo(imageFilePath + "/" + TNG_TOP_LOGO);
			rptTnGServiceFormCustCopyDTO.setCompanyBottomLeftLogo(imageFilePath + "/" + TNG_BOTTOM_LT_LOGO);
			rptTnGServiceFormCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + TNG_BOTTOM_RT_LOGO);
		} else {
			rptTnGServiceFormCustCopyDTO.setCompanyLogo(imageFilePath + "/" + TNG_TOP_LOGO);
			rptTnGServiceFormCustCopyDTO.setCompanyBottomLeftLogo(imageFilePath + "/" + TNG_BOTTOM_LT_LOGO);
			rptTnGServiceFormCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + TNG_BOTTOM_RT_LOGO);
		}
		rptTnGServiceFormCustCopyDTO.setCustomerCopyInd("Y");

		
		rptTnGServiceFormList.add(rptTnGServiceFormDTO);
		rptTnGServiceFormList.add(rptTnGServiceFormCustCopyDTO);

		result = new JasperReport(RptTnGServiceFormDTO.JASPER_TEMPLATE, rptTnGServiceFormList); 
		
		return result;
	}
	
	
	private HashMap<String, JasperReport> mapIGuardReportData(
			ArrayList<Object> pDTOs, String pLang, String iGuardType)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("mapIGuardReportData is called in MobCcsReportServiceImpl.java");
		
		boolean isIGuard = false;
		
		ArrayList<ReportDTO> iGuardList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> iGuardCustCopyList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> iGuardCareCashList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> iGuardCareCashCustCopyList = new ArrayList<ReportDTO>();
		
		IGuardDTO iGuardDTO = new IGuardDTO();
		IGuardDTO iGuardCustCopyDTO = new IGuardDTO();
		RptIGuardCareCashDTO rptIGuardCareCashDTO = new RptIGuardCareCashDTO();
		
		Object tempObj = null;
		
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);
			if (tempObj instanceof OrderDTO){
				if (StringUtils.isNotBlank(((OrderDTO) tempObj).getiGuardSerialNo())) {
					isIGuard = true;
				}
				rptIGuardCareCashDTO.setAppDate(((OrderDTO)tempObj).getAppInDate());
				rptIGuardCareCashDTO.setOrderId(((OrderDTO)tempObj).getOrderId());
			} else if (tempObj instanceof IGuardDTO) {
				BeanUtils.copyProperties(iGuardDTO, tempObj);
			}else if (tempObj instanceof CustomerProfileDTO){
			rptIGuardCareCashDTO.setCustEngName(((CustomerProfileDTO) tempObj).getTitle() +" "+((CustomerProfileDTO) tempObj).getCustLastName()+" "+((CustomerProfileDTO) tempObj).getCustFirstName());
			rptIGuardCareCashDTO.setContactPhone(((CustomerProfileDTO) tempObj).getContactPhone());
			rptIGuardCareCashDTO.setDob(((CustomerProfileDTO) tempObj).getDob());
			rptIGuardCareCashDTO.setEmailAddr(((CustomerProfileDTO) tempObj).getEmailAddr());
			rptIGuardCareCashDTO.setIdDocNum(((CustomerProfileDTO) tempObj).getIdDocNum());
			if ("I".equals(((CustomerProfileDTO) tempObj).getCareDmSupInd())){
				rptIGuardCareCashDTO.setPrivacyInd("Y");
				}else{
				rptIGuardCareCashDTO.setPrivacyInd("N");
				}
			}
			else if (tempObj instanceof BomSalesUserDTO){
				rptIGuardCareCashDTO.setShopCd(((BomSalesUserDTO) tempObj).getShopCd());
				rptIGuardCareCashDTO.setStaffId(((BomSalesUserDTO) tempObj).getOrgStaffId());
			}
		}
			 
		// Map form name with data set
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();
		
		iGuardDTO.setDirectLogo(imageFilePath + "/" + IGUARD_DIRECT_LOGO_FILE);
		iGuardDTO.setPhoneProtectorLogo(imageFilePath + "/" + IGUARD_PHONEPROTECTOR_LOGO_FILE);
		
		BeanUtils.copyProperties(iGuardCustCopyDTO, iGuardDTO);
		
		iGuardDTO.setCopy("Office Copy");
		iGuardCustCopyDTO.setCopy("Customer Copy");
		
		iGuardList.add(iGuardDTO);
		iGuardCustCopyList.add(iGuardCustCopyDTO);
			
		if (isIGuard&&iGuardType=="LDS") {
			map.put(IGuardDTO.JASPER_TEMPLATE_LDS, 
					new JasperReport(IGuardDTO.JASPER_TEMPLATE_LDS, iGuardList));
			map.put(IGuardDTO.JASPER_TEMPLATE_LDS + CUST_COPY, 
					new JasperReport(IGuardDTO.JASPER_TEMPLATE_LDS, iGuardCustCopyList));
			map.put(IGuardDTO.JASPER_TEMPLATE_TNC_LDS, 
					new JasperReport(IGuardDTO.JASPER_TEMPLATE_TNC_LDS, iGuardList));
		}
		if(isIGuard&&iGuardType=="AD"){
			map.put(IGuardDTO.JASPER_TEMPLATE_AD, 
					new JasperReport(IGuardDTO.JASPER_TEMPLATE_AD, iGuardList));
			map.put(IGuardDTO.JASPER_TEMPLATE_AD + CUST_COPY, 
					new JasperReport(IGuardDTO.JASPER_TEMPLATE_AD, iGuardCustCopyList));
			map.put(IGuardDTO.JASPER_TEMPLATE_TNC_AD, 
					new JasperReport(IGuardDTO.JASPER_TEMPLATE_TNC_AD, iGuardList));
		}
		if (iGuardType =="CARECASH"){
			rptIGuardCareCashDTO.setMob("1");
			rptIGuardCareCashDTO.setSignatureInd("N");
			iGuardCareCashList.add(rptIGuardCareCashDTO);
			/*map.put(RptIGuardCareCashDTO.JASPER_TEMPLATE, 
					new JasperReport(RptIGuardCareCashDTO.JASPER_TEMPLATE, iGuardCareCashList));*/
			map.put(RptIGuardCareCashDTO.JASPER_TEMPLATE + CUST_COPY, 
					new JasperReport(RptIGuardCareCashDTO.JASPER_TEMPLATE, iGuardCareCashList));
			/*if (distMode == OrderDTO.DisMode.P) {
				map.put(RptIGuardCareCashDTO.JASPER_TEMPLATE+ CUST_COPY, 
						new JasperReport(RptIGuardCareCashDTO.JASPER_TEMPLATE, iGuardCareCashList));
			}*/
		}
		
		return map;
	}
	
	
	/**
	 * Assign TnG Service Form data to specific report. Size of Map return should be same
	 * of number of report template generate.
	 */

	private HashMap<String, JasperReport> mapTnGServiceFormData(
			ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("mapTnGServiceFormData is called in MobCcsReportServiceImpl.java");
		// Map form name with data set
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();

		try {
			// Courier Guide (CS)
			ArrayList<ReportDTO> rptTnGServiceFormList = new ArrayList<ReportDTO>();
			ArrayList<ReportDTO> rptTnGServiceFormCustCopyList = new ArrayList<ReportDTO>();

			// Create a new form dto so as to input data
			RptTnGServiceFormDTO rptTnGServiceFormDTO = null;
			RptTnGServiceFormDTO rptTnGServiceFormCustCopyDTO = new RptTnGServiceFormDTO();

			// Copy necessary dto(s) into whole DN report dto
			Object tempObj = null;
			boolean existTnGVas = false;
			
			for (int i = 0; i < pDTOs.size(); i++) {
				tempObj = pDTOs.get(i);
				if (tempObj instanceof RptTnGServiceFormDTO) {
					rptTnGServiceFormDTO = (RptTnGServiceFormDTO)tempObj;
					existTnGVas = true;
				}
			}
			if (rptTnGServiceFormDTO == null) return null;
			
			/*for (int i = 0; i < pDTOs.size(); i++) {
				
				tempObj = pDTOs.get(i);

				if (tempObj instanceof OrderDTO) {
					rptTnGServiceFormDTO.setAppInDateStr(Utility.date2String(((OrderDTO) tempObj).getAppInDate(),
																	BomWebPortalConstant.DATE_FORMAT));
				} else if (tempObj instanceof CustomerProfileDTO) {
					//Assume HKID
					if ("HKID".equals(((CustomerProfileDTO) tempObj).getIdDocType())) {
						rptTnGServiceFormDTO.setIdDocNum(((CustomerProfileDTO) tempObj).getIdDocNum());
						rptTnGServiceFormDTO.setCustName(((CustomerProfileDTO) tempObj).getTitle()
								+ " " + ((CustomerProfileDTO) tempObj).getCustLastName()
								+ " " + ((CustomerProfileDTO) tempObj).getCustFirstName());
					}
				}
			}*/
		
	

			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				rptTnGServiceFormDTO.setCompanyLogo(imageFilePath + "/" + TNG_TOP_LOGO);
				rptTnGServiceFormDTO.setCompanyBottomLeftLogo(imageFilePath + "/" + TNG_BOTTOM_LT_LOGO);
				rptTnGServiceFormDTO.setCompanyBottomLogo(imageFilePath + "/" + TNG_BOTTOM_RT_LOGO);
			} else {
				rptTnGServiceFormDTO.setCompanyLogo(imageFilePath + "/" + TNG_TOP_LOGO);
				rptTnGServiceFormDTO.setCompanyBottomLeftLogo(imageFilePath + "/" + TNG_BOTTOM_LT_LOGO);
				rptTnGServiceFormDTO.setCompanyBottomLogo(imageFilePath + "/" + TNG_BOTTOM_RT_LOGO);
			}
			rptTnGServiceFormDTO.setCustomerCopyInd("N");
			rptTnGServiceFormDTO.setIdDocNum(maskedDocNum(rptTnGServiceFormDTO.getIdDocNum()));

			BeanUtils.copyProperties(rptTnGServiceFormCustCopyDTO, rptTnGServiceFormDTO);
			
			
			if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
				rptTnGServiceFormCustCopyDTO.setCompanyLogo(imageFilePath + "/" + TNG_TOP_LOGO);
				rptTnGServiceFormCustCopyDTO.setCompanyBottomLeftLogo(imageFilePath + "/" + TNG_BOTTOM_LT_LOGO);
				rptTnGServiceFormCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + TNG_BOTTOM_RT_LOGO);
			} else {
				rptTnGServiceFormCustCopyDTO.setCompanyLogo(imageFilePath + "/" + TNG_TOP_LOGO);
				rptTnGServiceFormCustCopyDTO.setCompanyBottomLeftLogo(imageFilePath + "/" + TNG_BOTTOM_LT_LOGO);
				rptTnGServiceFormCustCopyDTO.setCompanyBottomLogo(imageFilePath + "/" + TNG_BOTTOM_RT_LOGO);
			}
			rptTnGServiceFormCustCopyDTO.setCustomerCopyInd("Y");

			
			rptTnGServiceFormList.add(rptTnGServiceFormDTO);
			rptTnGServiceFormCustCopyList.add(rptTnGServiceFormCustCopyDTO);
			
			if (existTnGVas) {
				map.put(RptTnGServiceFormDTO.JASPER_TEMPLATE, 
						new JasperReport(RptTnGServiceFormDTO.JASPER_TEMPLATE, rptTnGServiceFormList));
				map.put(RptTnGServiceFormDTO.JASPER_TEMPLATE + CUST_COPY, 
						new JasperReport(RptTnGServiceFormDTO.JASPER_TEMPLATE, rptTnGServiceFormCustCopyList));
			}

		} catch (Exception e) {
			logger.error("Exception caught in mapTnGServiceFormData()", e);
			throw new AppRuntimeException(e);
		}
		return map;
	}
	
	
	private void fillInDocAttSect(boolean isMNP, boolean isSecSrv,
			boolean isThirdAP, boolean isConci, RptAppMobileServDTO input) {
		
		input.setDocAttMNP(isMNP);
		input.setDocAttSecSrv(isSecSrv);
		input.setDocAttThirdParty(isThirdAP);
		input.setDocAttConci(isConci);
		
		boolean isCOS = false;
		try {
			isCOS = mobCcsSupportDocDAO.isReqForm("10", input.getAgreementNum());
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}
		input.setDocAttChgOfSrv(isCOS);
		
	}
	
	/**
	 * save pdf to server
	 */
	public void savePdfReports(ArrayList<Object> pReportArrList, String pLang, String orderId, List<String> iGuardType, String brand) {
		
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String locale = "EN";
		
		if ("zh".equalsIgnoreCase(pLang)) {
			locale = "CHI";
		} else {
			locale = "EN";
		}
		
		File outputPdfPath = new File(getServerPath() + "/" + orderId);
		File outputPdf = new File(getServerPath() + "/" + orderId + "/" + orderId + "_" + locale + ".pdf");
		
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

				this.generateCCSPdfReports(pReportArrList, null, fos, pLang, orderId, "M", false, iGuardType, brand);
			
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
	}
	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}
	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
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
	
	private void fillSSStatements(RptAppMobileServDTO input, boolean isNE) {
		
		String configFile = "reportConstant.xml";
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile);
		
		if ("en".equals(input.getLocale())) {
			input.setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateEng1").toString());
			input.setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateEng2").toString());
			if (isNE) {
				input.setCustAgree(appCtx.getBean("neCustAgreeEng").toString());
			} else if (input.getShopCd().contains("SBO")) {
				input.setCustAgree(appCtx.getBean("osCustAgreeEng").toString());
			} else {
				input.setCustAgree(appCtx.getBean("ssCustAgreeEng").toString());
			}
		} else {
			input.setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateChi1").toString());
			input.setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateChi2").toString());
			if (isNE) {
				input.setCustAgree(appCtx.getBean("neCustAgreeChi").toString());
			} else if (input.getShopCd().contains("SBO")) {
				input.setCustAgree(appCtx.getBean("osCustAgreeChi").toString());
			} else {
				input.setCustAgree(appCtx.getBean("ssCustAgreeChi").toString());
			}
		}		
		
	}
	
	private void fillCCOSStatements(RptCreditCardDDAuthOSDTO input) {
		
		String configFile = "reportConstant.xml";
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile);
		
		input.setCcos1(appCtx.getBean("ccOSState1").toString());
		input.setCcos2(appCtx.getBean("ccOSState2").toString());
		input.setCcos3(appCtx.getBean("ccOSState3").toString());
		input.setCcos4(appCtx.getBean("ccOSState4").toString());
		input.setCcos5(appCtx.getBean("ccOSState5").toString());
		input.setCcos6(appCtx.getBean("ccOSState6").toString());
		input.setCcos7(appCtx.getBean("ccOSState7").toString());
		input.setCcos8(appCtx.getBean("ccOSState8").toString());

	}
	
	private void fillCCStatements(RptCreditCardDDAuthDTO input) {
		
		String configFile = "reportConstant.xml";
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile);
		
		input.setCcos7(appCtx.getBean("ccOSState7").toString());
		input.setCcos8(appCtx.getBean("ccOSState8").toString());

	}
	
	public boolean hasMobileSafetyPhoneOffer(String orderId) {
		try {
			return orderDao.hasMobileSafetyPhoneOffer(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getTradeInRBSchedule(String tradeInItemID, String locale) {
		// TODO Auto-generated method stub
		try {
			return orderDao.getTradeInRBSchedule(tradeInItemID, locale);
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String hasTradeInHS(String orderId) {
		// TODO Auto-generated method stub
		try {
			return orderDao.hasTradeInHS(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getTradeInIMEIAttributeID(){
		// TODO Auto-generated method stub
		try {
			return orderDao.getTradeInIMEIAttributeID();
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*public boolean hasNFCSim(String orderId) {
		try {
			return orderDao.hasNFCSim(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}*/
	/*public boolean hasOctopusSim(String orderId) {
		try {
			return orderDao.hasOctopusSim(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	/*private ArrayList<InputStream> getPDFKeyInformationSheetFormStreamList(
			ArrayList<Object> pReportArrList, String pLang, boolean isCustCopy, String osOrderId) {
		logger.info("getPDFKeyInformationSheetFormStreamList is called in MobCcsReportServiceImpl.java");

		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();

		try {
			JasperReport nfcSimReport = mapKeyInformationSheetReportData(pReportArrList, pLang);

			if (nfcSimReport != null) {
				pdfStreamList.add(generatePdfInputStream(
						nfcSimReport.getJasperTemplateName(),
						nfcSimReport.getRptDtoArrList(), JASPER_PATH_MOB,pLang, osOrderId, brand));
			}
			
			return pdfStreamList;
		} catch (Exception de) {
			logger.error("Exception caught in getPDFKeyInformationSheetFormStreamList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	private JasperReport mapKeyInformationSheetReportData(
			ArrayList<Object> pDTOs, String pLang)
			throws InvocationTargetException, IllegalAccessException {

		logger.info("mapKeyInformationSheetReportData is called in MobCcsReportServiceImpl.java");
		
		ArrayList<ReportDTO> kisFormsList = new ArrayList<ReportDTO>(); 		
		JasperReport result = null;
		Object tempObj = null;
		
		RptKeyInformationSheetDTO rptKeyInformationSheetDTO = new RptKeyInformationSheetDTO(); 
		for (int i = 0; i < pDTOs.size(); i++) {
			tempObj = pDTOs.get(i);

			if (tempObj instanceof OrderDTO) {
				OrderDTO orderDto = (OrderDTO) tempObj;
				rptKeyInformationSheetDTO.setAgreementNum(orderDto.getAgreementNum());
				rptKeyInformationSheetDTO.setAppInDate(orderDto.getAppInDate());
				rptKeyInformationSheetDTO.setMsisdn(orderDto.getMsisdn());

			} else if (tempObj instanceof CustomerProfileDTO) {
				CustomerProfileDTO customerProfileDto = (CustomerProfileDTO) tempObj;
				if ("BS".equals(customerProfileDto.getIdDocType())) {
					rptKeyInformationSheetDTO.setCustFirstName(customerProfileDto.getCompanyName());
				} else {
					rptKeyInformationSheetDTO.setCustFirstName(customerProfileDto.getCustFirstName());
					rptKeyInformationSheetDTO.setCustLastName(customerProfileDto.getCustLastName());
				}
				rptKeyInformationSheetDTO.setTitle(customerProfileDto.getTitle());
			}
			
		}

		if (RPT_LANG_ZH.equalsIgnoreCase(pLang)) {
			rptKeyInformationSheetDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_CHI);
			rptKeyInformationSheetDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_CHI);
		} else {
			rptKeyInformationSheetDTO.setCompanyLogo(imageFilePath + "/" + COMPANY_LOGO_FILE_ENG);
			rptKeyInformationSheetDTO.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_BOTTOM_LOGO_FILE_ENG);
		}
			
		kisFormsList.add(rptKeyInformationSheetDTO);
		result = new JasperReport(RptKeyInformationSheetDTO.JASPER_TEMPLATE, kisFormsList); 
		
		return result;
	}*/
	//Athena 20131111 online sales HsTradeDesc report by item code
	public HSTradeDescDTO getHSTradeDescByItemCode(String itemCode) {
		try {
			HSTradeDescDTO hsDesc= hsTradeDescDAO.getHSTradeDescByItemCode(itemCode);
			return hsDesc;
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
public void saveCareCashPdfReports(ArrayList<Object> pReportArrList, String pLang, String orderId, List<String> iGuardType, String brand) {
		
		try {
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			setServerPath(propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH));
		} catch (Exception e) {
			logger.error("Exception caught in savePdfReports()", e);
			throw new AppRuntimeException(e);
		}
		
		String locale = "EN";
		
		if ("zh".equalsIgnoreCase(pLang)) {
			locale = "CHI";
		} else {
			locale = "EN";
		}
		
		File outputPdfPath = new File(getServerPath() + "/" + orderId);
		File outputPdf = new File(getServerPath() + "/" + orderId + "/" + orderId + "_CareCash_" + locale + ".pdf");
		
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
				this.generateCCSPdfReports(pReportArrList, null, fos, pLang, orderId, "I", false, iGuardType, brand);
			
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
	}
		public String maskedDocNum (String docNum){
			if (docNum.length()<8){
				return docNum;
			}else{
				String maskedDocNum=docNum.substring(0,4) + "***" + docNum.substring(7);
				return maskedDocNum;
			}
		}
		
		private List<InputStream> getPDFRestartServiceFromStreamList(
				ArrayList<Object> pReportArrList, String pLang, String orderId, String brand) {

			logger.info("getPDFRestartServiceFromStreamList is called");

			List<InputStream> pdfStreamList = new ArrayList<InputStream>();
			
			try {

				JasperReport jasper = getRestartServiceJasper(pLang, pReportArrList, brand);
				if (jasper != null && CollectionUtils.isNotEmpty(jasper.getRptDtoArrList())) {
					pdfStreamList.add(generatePdfInputStream(
							jasper.getJasperTemplateName(),
							jasper.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB, pLang, orderId, brand));
				}
				
				File tnCFile = new File(mobPdfFilePath + "/" + ProjectEagleReportHelper.TNC_PDF);
				if (tnCFile != null && tnCFile.exists()) {
					ArrayList<InputStream> tnCInputStream = new ArrayList<InputStream>(); 
					tnCInputStream.add(new FileInputStream(tnCFile));
					pdfStreamList.addAll(tnCInputStream);
				}

				return pdfStreamList;
			
			} catch (InvocationTargetException invocationTargetException) {
				logger.error("Exception caught in getPDFRestartServiceFromStreamList()", invocationTargetException);
				throw new AppRuntimeException(invocationTargetException);
				
			} catch (IllegalAccessException illegalAccessException) {
				logger.error("Exception caught in getPDFRestartServiceFromStreamList()", illegalAccessException);
				throw new AppRuntimeException(illegalAccessException);
				
			} catch (Exception exception) {
				logger.error("Exception caught in getPDFRestartServiceFromStreamList()", exception);
				throw new AppRuntimeException(exception);
			}
			
		}
		
		private JasperReport getRestartServiceJasper(
				String pdfLang, List<Object> pReportArrList, String brand)
				throws InvocationTargetException, IllegalAccessException {
			logger.info("getRestartServiceJasper is called in MobCcsReportServiceImpl.java");
			
			RptProjectEagleDTO rptProjectEagleDTO = new RptProjectEagleDTO();
			Date appInDate = null;
			boolean isCallCenterOrder = false;
			for (Object dto : pReportArrList) {
				if (dto instanceof OrderDTO) {
					OrderDTO orderDTO = ((OrderDTO) dto);
					isCallCenterOrder = StringUtils.startsWithIgnoreCase(orderDTO.getOrderId(), "C");
					rptProjectEagleDTO.setOrderId(orderDTO.getOrderId());   
					rptProjectEagleDTO.setSubscriptionMobileNo(orderDTO.getMsisdn());
					Date srvReqDate = orderDTO.getSrvReqDate();
					String srvReqDateStr = Utility.date2String(srvReqDate);
					if (StringUtils.isNotBlank(srvReqDateStr)) {
						rptProjectEagleDTO.setTargetCommencementDate(srvReqDateStr);
					}
					appInDate = orderDTO.getAppInDate();
					String appInDateStr = Utility.date2String(appInDate);
					if (StringUtils.isNotBlank(appInDateStr)) {
						rptProjectEagleDTO.setHandsetReceivedDate(appInDateStr);
						rptProjectEagleDTO.setDateValue(appInDateStr);
					}
					rptProjectEagleDTO.setImeiNo(orderDTO.getImei());

				} else if (dto instanceof CustomerProfileDTO) {
					CustomerProfileDTO customerProfileDTO = ((CustomerProfileDTO) dto);
					String idDocNum = customerProfileDTO.getIdDocNum();
					String idDocNumMask=null;
					if (StringUtils.isNotBlank(idDocNum)) {
						idDocNumMask = maskedDocNum(customerProfileDTO.getIdDocNum());
						rptProjectEagleDTO.setIdDocNum(idDocNumMask);
					}
					rptProjectEagleDTO.setSurname(customerProfileDTO.getCustLastName());
					rptProjectEagleDTO.setGivenName(customerProfileDTO.getCustFirstName());
					rptProjectEagleDTO.setTitle(customerProfileDTO.getTitle());
					rptProjectEagleDTO.setEmail(customerProfileDTO.getEmailAddr());
					rptProjectEagleDTO.setContactNumber(customerProfileDTO.getContactPhone());
					String maskedDob = Utility.maskedDob(customerProfileDTO.getDobStr());
					rptProjectEagleDTO.setDateOfBirth(maskedDob);
					rptProjectEagleDTO.setAddressLine1(customerProfileDTO.getProjectEagleAddressLine1());
					rptProjectEagleDTO.setAddressLine2(customerProfileDTO.getProjectEagleAddressLine2());
					rptProjectEagleDTO.setAddressLine3(customerProfileDTO.getProjectEagleAddressLine3());
				} else if (dto instanceof BomSalesUserDTO) {
					BomSalesUserDTO bomSalesUserDTO = ((BomSalesUserDTO) dto);
					 rptProjectEagleDTO.setShopCode("P" + bomSalesUserDTO.getChannelCd());
					 rptProjectEagleDTO.setChannelId(bomSalesUserDTO.getChannelId());
				} else if (dto instanceof ServicePlanReportDTO) {
					ServicePlanReportDTO servicePlanReportDTO = ((ServicePlanReportDTO) dto);
					rptProjectEagleDTO.setHandsetBrandModel(servicePlanReportDTO.getHandsetDeviceDescription());
					List<VasDetailDTO> allSelectedVasList = getAllSelectedVasList(servicePlanReportDTO);
					for (VasDetailDTO selectedVas : allSelectedVasList) {
						String itemId = selectedVas.getItemId();
						boolean isProjectEagle = vasDetailService.existInSelectionGrpList(ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, itemId);
						
						if (isProjectEagle) {
							VasDetailDTO vasDetailDTOFromDB;
							try {
								vasDetailDTOFromDB = vasDetailDao.getVasItemDetail(itemId);
							} catch (DAOException e) {
								vasDetailDTOFromDB = null;
							}
							if (vasDetailDTOFromDB != null) {
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
								
								rptProjectEagleDTO.setContractPeriod((vasDetailDTOFromDB.getContractPeriod() == null || ("0").equalsIgnoreCase(vasDetailDTOFromDB.getContractPeriod())) ? "Free to Go" : vasDetailDTOFromDB.getContractPeriod() + " Months");
							}
						}
					}
				}  else if (dto instanceof BasketDTO) {
					BasketDTO basketDto = (BasketDTO) dto;
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
				}  else if (dto instanceof List<?>) {
					boolean isComponentList = false;
					for (Object o : (List<?>) dto) {
						if (o instanceof ComponentDTO) {
							isComponentList = true;
							break;
						}
					}
					if (isComponentList) {
						
						String restartServiceDMAttributeId = reportService.getCodeId(ProjectEagleReportHelper.ITEM_PROJECT_EAGLE_INSURANCE_DM_CODE_TYPE);
						List<ComponentDTO> componentList = (List<ComponentDTO>) dto;
						if (StringUtils.isNotBlank(restartServiceDMAttributeId)) {
							String restartServiceDMInd = reportService.getAttbValue(componentList, restartServiceDMAttributeId);
							if (StringUtils.isNotBlank(restartServiceDMInd) && StringUtils.equalsIgnoreCase(restartServiceDMInd,"Y")) {
								rptProjectEagleDTO.setDirectMarketingInd("X");
							}
							if (StringUtils.isNotBlank(restartServiceDMInd) && StringUtils.equalsIgnoreCase(restartServiceDMInd,"N")) {
								rptProjectEagleDTO.setDirectMarketingInd(" ");
							}
						}
					}
				}
			}
			rptProjectEagleDTO = reportService.patchWithLabels(rptProjectEagleDTO);
			
			if (isCallCenterOrder) {
				rptProjectEagleDTO.setSignatureLabel(null);
				rptProjectEagleDTO.setSignature(null);
			} 
			ArrayList<ReportDTO> reportDtoList = new ArrayList<ReportDTO>();
			reportDtoList.add(rptProjectEagleDTO);
			
			return new JasperReport(rptProjectEagleDTO.getJasperName(), reportDtoList);
			
		}
		
		private List<InputStream> getPDFGenericFromStreamList(
				GenericForm genericForm, List<Object> pReportArrList, String pLang, String osOrderId, String brand) {

			logger.info("getPDFGenericFromStreamList is called in MobCcsReportServiceImpl.java");

			List<InputStream> pdfStreamList = new ArrayList<InputStream>();

			try {

				JasperReport jasper = getGenericReportJasper(genericForm, pLang, pReportArrList, brand);
				if (jasper != null && CollectionUtils.isNotEmpty(jasper.getRptDtoArrList())) {
					pdfStreamList.add(generatePdfInputStream(
							jasper.getJasperTemplateName(),
							jasper.getRptDtoArrList(), BomWebPortalConstant.JASPER_PATH_MOB, pLang, osOrderId, brand));
				}
				
				// TnC
				String travelInsuranceDays = "";
				for (Object dto : pReportArrList) {
					if (dto instanceof ServicePlanReportDTO) {
						ServicePlanReportDTO servicePlanReportDTO = ((ServicePlanReportDTO) dto);
						List<VasDetailDTO> allSelectedVasList = getAllSelectedVasList(servicePlanReportDTO);
						for (VasDetailDTO selectedVas : allSelectedVasList) {
							String itemId = selectedVas.getItemId();
							boolean isTravelInsurance = vasDetailService.existInSelectionGrpList(GenericForm.TRAVEL_INSURANCE_FORM.getItemSelectionGroupId(), itemId);
							if (isTravelInsurance) {
								travelInsuranceDays = itemDisplayService.getTravelInsuranceDays(itemId);
							}
						}
					}
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("travelInsuranceDays", travelInsuranceDays);
				
				File tnCFile = new File(mobPdfFilePath + "/" + Utility.fillInVariables(genericForm.getTncPdf(), params));
				if (tnCFile != null && tnCFile.exists()) {
					ArrayList<InputStream> tnCInputStream = new ArrayList<InputStream>(); 
					tnCInputStream.add(new FileInputStream(tnCFile));
					pdfStreamList.addAll(tnCInputStream);
				}

				return pdfStreamList;
			
			} catch (InvocationTargetException invocationTargetException) {
				logger.error("Exception caught in getPDFGenericFromStreamList()", invocationTargetException);
				throw new AppRuntimeException(invocationTargetException);
				
			} catch (IllegalAccessException illegalAccessException) {
				logger.error("Exception caught in getPDFGenericFromStreamList()", illegalAccessException);
				throw new AppRuntimeException(illegalAccessException);
				
			} catch (Exception exception) {
				logger.error("Exception caught in getPDFGenericFromStreamList()", exception);
				throw new AppRuntimeException(exception);
			}

		}
		
		private JasperReport getGenericReportJasper(
				GenericForm genericForm, String pdfLang, List<Object> pReportArrList, String brand)
				throws InvocationTargetException, IllegalAccessException {
			
			Map<String, String> genericReportDataMap = null;
			genericReportDataMap = getGenericReportDataMap(pReportArrList, brand);
			
			if (genericReportDataMap == null || genericReportDataMap.size() == 0) {
				return null;
			}
			
			RptGenericFormTemplateDTO rptGenericFormTemplateDTO = genericReportHelper.getRptGenericFormTemplateDTO(
					genericForm, pdfLang, genericReportDataMap, null);
			ArrayList<ReportDTO> reportDtoList = new ArrayList<ReportDTO>();
			reportDtoList.add(rptGenericFormTemplateDTO);
			
			return new JasperReport(rptGenericFormTemplateDTO.getJasperName(), reportDtoList);
			
		}
		
		private Map<String, String> getGenericReportDataMap(List<Object> pReportArrList, String brand) {
			Map<String, String> genericReportDataMap = new HashMap<String, String>();
			for (Object dto : pReportArrList) {
				if (dto instanceof OrderDTO) {
					OrderDTO orderDTO = ((OrderDTO) dto);
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_ID, orderDTO.getOrderId());
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_MSISDN, orderDTO.getMsisdn());
					Date srvReqDate = orderDTO.getSrvReqDate();
					String srvReqDateStr = Utility.date2String(srvReqDate);
					if (StringUtils.isNotBlank(srvReqDateStr)) {
						genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_SERVICE_REQ_DATE, srvReqDateStr);
					}
					Date appInDate = orderDTO.getAppInDate();
					String appInDateStr = Utility.date2String(appInDate);
					if (StringUtils.isNotBlank(appInDateStr)) {
						genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_APP_IN_DATE, appInDateStr);
					}
					if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
						genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_BRAND,"1010");
					} else if (BomWebPortalConstant.BRAND_CSL.equals(brand)) {
						genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_BRAND,"csl");
					}
				} else if (dto instanceof CustomerProfileDTO) {
					CustomerProfileDTO customerProfileDTO = ((CustomerProfileDTO) dto);
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUSTOMER_TITLE, customerProfileDTO.getTitle());
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUST_LAST_NAME, customerProfileDTO.getCustLastName());
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUST_FIRST_NAME, customerProfileDTO.getCustFirstName());
					String idDocNum = customerProfileDTO.getIdDocNum();
					if (StringUtils.isNotBlank(idDocNum)) {
						genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ID_DOC_NUM, maskedDocNum(idDocNum));
					}
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_EMAIL_ADDR, customerProfileDTO.getEmailAddr());
				} else if (dto instanceof BomSalesUserDTO) {
					BomSalesUserDTO bomSalesUserDTO = ((BomSalesUserDTO) dto);
					genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_SHOP_CODE, bomSalesUserDTO.getChannelCd());
				} else if (dto instanceof ServicePlanReportDTO) {
					ServicePlanReportDTO servicePlanReportDTO = ((ServicePlanReportDTO) dto);
					List<VasDetailDTO> allSelectedVasList = getAllSelectedVasList(servicePlanReportDTO);
					for (VasDetailDTO selectedVas : allSelectedVasList) {
						String itemId = selectedVas.getItemId();
						boolean isTravelInsurance = vasDetailService.existInSelectionGrpList(GenericForm.TRAVEL_INSURANCE_FORM.getItemSelectionGroupId(), itemId);
						boolean isHelperCareInsurance = vasDetailService.existInSelectionGrpList(GenericForm.HELPERCARE_INSURANCE_FORM.getItemSelectionGroupId(), itemId);
						
						if (isTravelInsurance || isHelperCareInsurance) {
							VasDetailDTO vasDetailDTOFromDB;
							try {
								vasDetailDTOFromDB = vasDetailDao.getVasItemDetail(itemId);
							} catch (DAOException e) {
								vasDetailDTOFromDB = null;
							}
							if (vasDetailDTOFromDB != null) {
								if (isTravelInsurance) {
									genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_ITEM_ID, vasDetailDTOFromDB.getItemId());
									genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_PROD_CD, vasDetailDTOFromDB.getProdCd());
									genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DISPLAY, vasDetailDTOFromDB.getItemHtml());
									genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_CONTRACT_PERIOD, vasDetailDTOFromDB.getContractPeriod());
								} else if (isHelperCareInsurance) {
									genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_PROD_CD, vasDetailDTOFromDB.getProdCd());
									genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DISPLAY, vasDetailDTOFromDB.getItemHtml());
									genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_CONTRACT_PERIOD, vasDetailDTOFromDB.getContractPeriod());
								}
							}
						}
					}
				} else if (dto instanceof List<?>) {
					boolean isComponentList = false;
					for (Object o : (List<?>) dto) {
						if (o instanceof ComponentDTO) {
							isComponentList = true;
							break;
						}
					}
					if (isComponentList) {
						
						String travelStaffIdAttributeId = reportService.getCodeId(ReportServiceImpl.ITEM_TRAVEL_INSURANCE_CODE_TYPE);
						String helperCareStaffIdAttributeId = reportService.getCodeId(ReportServiceImpl.ITEM_HELPERCARE_INSURANCE_CODE_TYPE);
						String travelDMAttributeId = reportService.getCodeId(ReportServiceImpl.ITEM_TRAVEL_INSURANCE_DM_CODE_TYPE);
						String helperCareDMAttributeId = reportService.getCodeId(ReportServiceImpl.ITEM_HELPERCARE_INSURANCE_DM_CODE_TYPE);
						List<ComponentDTO> componentList = (List<ComponentDTO>) dto;
						
						if (StringUtils.isNotBlank(travelStaffIdAttributeId)) {
							String travelStaffId = reportService.getAttbValue(componentList, travelStaffIdAttributeId);
							if (StringUtils.isNotBlank(travelStaffId)) {
								genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_STAFF_ID, travelStaffId);
							}
						}
						if (StringUtils.isNotBlank(helperCareStaffIdAttributeId)) {
							String helperCareStaffId = reportService.getAttbValue(componentList, helperCareStaffIdAttributeId);
							if (StringUtils.isNotBlank(helperCareStaffId)) {
								genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_STAFF_ID, helperCareStaffId);
							}
						}
						if (StringUtils.isNotBlank(travelDMAttributeId)) {
							String travelDMInd = reportService.getAttbValue(componentList, travelDMAttributeId);
							if (StringUtils.isNotBlank(travelDMInd) && StringUtils.equalsIgnoreCase(travelDMInd,"Y")) {
								genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DM_IND, "X");
							}
							if (StringUtils.isNotBlank(travelDMInd) && StringUtils.equalsIgnoreCase(travelDMInd,"N")) {
								genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DM_IND, " ");
							}
						}
						if (StringUtils.isNotBlank(helperCareDMAttributeId)) {
							String helperCareDMInd = reportService.getAttbValue(componentList, helperCareDMAttributeId);
							if (StringUtils.isNotBlank(helperCareDMInd) && StringUtils.equalsIgnoreCase(helperCareDMInd,"Y")) {
								genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DM_IND, "X");
							}
							if (StringUtils.isNotBlank(helperCareDMInd) && StringUtils.equalsIgnoreCase(helperCareDMInd,"N")) {
								genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DM_IND, " ");
							}
						}
					}
				}
			}
			return genericReportDataMap;
		}
		
		private boolean existInSpecifiedSelectionGrpList(String itemSelectionGrpId, ServicePlanReportDTO servicePlanReportDTO) {
			
			List<VasDetailDTO> allSubscribedVasList = getAllSelectedVasList(servicePlanReportDTO);
			
			List<String> allSubscribedVasItemIdList = new ArrayList<String>();
			for (VasDetailDTO allSubscribedVas : allSubscribedVasList) {
				allSubscribedVasItemIdList.add(allSubscribedVas.getItemId());
			}
			
			return vasDetailService.existInSelectionGrpList(itemSelectionGrpId, allSubscribedVasItemIdList);

		}
		
		private List<VasDetailDTO> getAllSelectedVasList(ServicePlanReportDTO servicePlanReportDTO) {
			
			List<VasDetailDTO> allSubscribedVasList = new ArrayList<VasDetailDTO>();
			
			List<VasDetailDTO> vasReportUseDetailList = servicePlanReportDTO.getMainServDtls();
			if (CollectionUtils.isNotEmpty(vasReportUseDetailList)) {
				allSubscribedVasList.addAll(vasReportUseDetailList);
			}
			
			List<VasDetailDTO> vasGifsReportUseDetailList = servicePlanReportDTO.getVasFreeGifsDtls();
			if (CollectionUtils.isNotEmpty(vasGifsReportUseDetailList)) {
				allSubscribedVasList.addAll(vasGifsReportUseDetailList);
			}
			
			List<VasDetailDTO> vasOptionalReportUseList = servicePlanReportDTO.getVasOptionalDtls();
			if (CollectionUtils.isNotEmpty(vasOptionalReportUseList)) {
				allSubscribedVasList.addAll(vasOptionalReportUseList);
			}
			
			return allSubscribedVasList;

		}
		
		private String getTemplateFromDB(String formId, String pdfLang, String sectionNumber) {
			String template = reportService.getReportContentHtml(formId, pdfLang, sectionNumber);
			return StringUtils.isBlank(template)? "" : template;
		}
}