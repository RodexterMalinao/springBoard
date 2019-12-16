package com.bomwebportal.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.DisplayLkupDAO;
import com.bomwebportal.dao.HSTradeDescDAO;
import com.bomwebportal.dao.MobCosRptDAO;
import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dao.VasDetailDAO;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
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
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.ServicePlanReportDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.SignoffDTO.SignatureTypeEnum;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.dto.report.AdditionalChargeDTO;
import com.bomwebportal.dto.report.IPhoneTradeInFormDTO;
import com.bomwebportal.dto.report.MiscellaneousChargeDTO;
import com.bomwebportal.dto.report.MobAppFormDTO;
import com.bomwebportal.dto.report.ReportDTO;
import com.bomwebportal.dto.report.RptAppMobileServDTO;
import com.bomwebportal.dto.report.RptConciergeServiPadiPhoneDTO;
import com.bomwebportal.dto.report.RptCreditCardDDAuthDTO;
import com.bomwebportal.dto.report.RptGenericFormTemplateDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.dto.report.RptKeyInformationSheetDTO;
import com.bomwebportal.dto.report.RptMobPortAppDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneAFCDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneSuppAppDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneTnCDTO;
import com.bomwebportal.dto.report.RptMultiSimDTO;
import com.bomwebportal.dto.report.RptNFCConsentDTO;
import com.bomwebportal.dto.report.RptOctopusConsentDTO;
import com.bomwebportal.dto.report.RptOctopusTnCDTO;
import com.bomwebportal.dto.report.RptPrintIndDTO;
import com.bomwebportal.dto.report.RptProjectEagleDTO;
import com.bomwebportal.dto.report.RptRetAppMobileServDTO;
import com.bomwebportal.dto.report.RptRetConfirmForCopDTO;
import com.bomwebportal.dto.report.RptRetCosDTO;
import com.bomwebportal.dto.report.RptRetCosDeliveryNoteDTO;
import com.bomwebportal.dto.report.RptRetCountRejDTO;
import com.bomwebportal.dto.report.RptRetCourierDeliveryGuidelineDTO;
import com.bomwebportal.dto.report.RptSecretarialServDTO;
import com.bomwebportal.dto.report.RptServiceGuideDTO;
import com.bomwebportal.dto.report.RptTnGServiceFormDTO;
import com.bomwebportal.dto.report.RptVasDetailDTO;
import com.bomwebportal.dto.report.RptWarmTipsDTO;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.dto.ui.SupportDocUI.GenerateSpringboardForm;
import com.bomwebportal.dto.ui.SupportDocUI.SpringboardForm;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StaffInfoDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.form.MobCcsChgServCustInfoRefundDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsDoaRequestService;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.cos.dto.MobCosCopDTO;
import com.bomwebportal.mob.cos.service.MobCosCopService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.bomwebportal.util.PdfUtil;
import com.bomwebportal.util.ReportUtil;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.GenericReportHelper.GenericForm;
import com.bomwebportal.web.util.ProjectEagleReportHelper;
import com.bomwebportal.web.util.ReportRepository;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MobCosReportServiceImpl implements MobCosReportService {

	protected final Log logger = LogFactory.getLog(getClass()); 
	
	private ReportService reportService;
	private ReportRepository reportRepository;
	private MobCosRptDAO mobCosRptDAO;
	private VasDetailService vasDetailService;
	private OrderService orderService;
	private VasDetailDAO vasDetailDao;
	private MobileDetailService mobileDetailService;
	private CustomerProfileService customerProfileService;
	private DeliveryService deliveryService;
	private CodeLkupService codeLkupService;
	private IGuardService iGuardService;
	private DisplayLkupDAO displayLkupDAO;
	private MultiSimInfoService multiSimInfoService;
	private OrderDAO orderDao;
	private MobCosCopService mobCosCopService;
	private MobCcsDoaRequestService mobCcsDoaRequestService;
	private DepositService depositService;
	private SupportDocService supportDocService;
	private ItemFuncMobService itemFuncMobService;
	private StaffInfoDAO staffInfoDao;
	private HSTradeDescDAO hsTradeDescDAO;
	private GenericReportHelper genericReportHelper;
	private ItemDisplayService itemDisplayService;
	
	private String mobPdfFilePath;
	private String imageFilePath;
	private Boolean isMemberHaveNFC = false;
	
	private final String CUST_SIGN = SignatureTypeEnum.CUSTOMER_SIGN.toString();
	private final String BANK_SIGN = SignatureTypeEnum.BANK_SIGN.toString();
	private final String IGUARD_SIGN = SignatureTypeEnum.IGUARD_SIGN.toString();
	private final String TRANSFEREE_SIGN = SignatureTypeEnum.TRANSFEREE_SIGN.toString();
	private final String TRAVEL_INSURANCE_FORM_SIGN = SignatureTypeEnum.TRAVEL_INSURANCE_FORM_SIGN.toString();
	private final String HELPERCARE_INSURANCE_FORM_SIGN = SignatureTypeEnum.HELPERCARE_INSURANCE_FORM_SIGN.toString();
	private final String PROJECT_EAGLE_INSURANCE_FORM_SIGN = SignatureTypeEnum.PROJECT_EAGLE_INSURANCE_FORM_SIGN.toString();
	private static final String ITEM_SELECTION_GROUP_ID_HELPER_CARE = "6666666664";
	private static final String ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE= "6666666665";
	public static final String ITEM_TRAVEL_INSURANCE_CODE_TYPE= "TRAVEL_INSURANCE_STAFFID_ATTB_ID";
	public static final String ITEM_HELPERCARE_INSURANCE_CODE_TYPE= "HELPERCARE_INSURANCE_STAFFID_ATTB_ID";
	public static final String ITEM_TRAVEL_INSURANCE_DM_CODE_TYPE= "TRAVEL_INSURANCE_DM_ATTB_ID";
	public static final String ITEM_HELPERCARE_INSURANCE_DM_CODE_TYPE= "HELPERCARE_INSURANCE_DM_ATTB_ID";
	public static final String ITEM_PROJECT_EAGLE_INSURANCE_DM_CODE_TYPE = "PROJECT_EAGLE_INSURANCE_DM_ATTB_ID";
	
	//private static String COMPANY_CHOP_FILE = "hkt_logo.png";
	private static String IGUARD_DIRECT_LOGO_FILE = "iGuard_direct_logo.jpg";
	private static String IGUARD_PHONEPROTECTOR_LOGO_FILE = "HKTCare_logo.jpg";
	private static String MOBILE_SAFETY_PHONE_FILE = "mobile_safety_phone.jpg";

	private static String TNG_TOP_LOGO = "tng_logo.jpg";
	private static String TNG_BOTTOM_LT_LOGO = "tng_bottom_lt_logo.jpg";
	private static String TNG_BOTTOM_RT_LOGO = "tng_bottom_rt_logo.jpg";
	private static String iGuardUadTNG = "iguard_uad_tnc.pdf";
	

	public String getMobPdfFilePath() {
		return mobPdfFilePath;
	}


	public void setMobPdfFilePath(String mobPdfFilePath) {
		this.mobPdfFilePath = mobPdfFilePath;
	}


	public byte[] generateMobAppForm(FastByteArrayOutputStream baosMerged,
					String orderId, String locale, String save, String nature, String channel, 
					String companyCopyInd, Map<String, SignoffDTO> signOffMap, String copInd,
					String delInd, String dmsInd,
					String filePath, String watermark, String updateStatus, List<String> formIds
			) throws Exception {
		String brmTooGenerateFormType[] = {"B2N","BNN","T2N"};
		String brmGenerationFormType[] = {"B2N","BNN"};
		String tooGenerationFormType[] = {"T2N"};
		List<MobAppFormDTO> mobAppFormList;
		if (formIds != null) {
			mobAppFormList = this.getReportListByFormIds(channel, nature, formIds);
		} else {
			mobAppFormList = LookupTableBean.getInstance().getMobAppFormList(channel, nature);
		}
		logForInitialMobAppForm(orderId, mobAppFormList);

		List<MobAppFormDTO> requiredMobAppFormList = new ArrayList<MobAppFormDTO>();
		List<MobAppFormDTO> requiredCompanyCopyMobAppFormList = new ArrayList<MobAppFormDTO>();

		int requiredMobAppFormListSizeBefore = 0;
		int requiredMobAppFormListSizeAfter = 0;
		
		String orderNature = orderService.getOrderNature(orderId);
		RptPrintIndDTO rptPrintIndDTO = this.retrieveAdditionalServiceInd(orderId);	
		
		logForAdditionalServicePrintInd(orderId, rptPrintIndDTO);
		
		OrderDTO orderDto = orderService.getOrder(orderId);
		CustomerProfileDTO customerProfileDto = customerProfileService.getCosCustomerProfile(orderId);
		
		String brand = customerProfileDto.getBrand();
		
		boolean isStudentPlan = customerProfileService.isStudentPlan(orderId);
		
		String mdoInd = orderService.getMdoInd(orderId);
		
		List<CodeLkupDTO> iphoneSevenTradeInList= LookupTableBean.getInstance().getCodeLkupList().get("IP7_TRADE_IN");
		List<CodeLkupDTO> iphoneEightTradeInList= LookupTableBean.getInstance().getCodeLkupList().get("IP8_TRADE_IN");
		boolean isIphone7TradeInBasket = orderService.isIphone7TradeInBasket(orderId,iphoneSevenTradeInList);
		boolean isIphone8TradeInBasket = orderService.isIphone7TradeInBasket(orderId,iphoneEightTradeInList);
		
		String simType = orderService.getSimTypeByCosOrder(orderId);
		List<MultiSimInfoDTO> multiSimInfoDTOs = multiSimInfoService.getMultiSimInfoDTO(orderId, locale, Utility.date2String(orderDto.getAppInDate(), "dd/MM/yyyy"), channel, orderDto.getShopCode(), brand, customerProfileDto.getSimType());
		if ((multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0)){
			for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
				for(MultiSimInfoMemberDTO member: multiSimInfoDTO.getMembers()){
					if (StringUtils.isNotBlank(member.getNfcInd()) && !"0".equals(member.getNfcInd())){
					isMemberHaveNFC=true;
					}
				}
			}
		}
		for(MobAppFormDTO mobAppForm: mobAppFormList) {
			
			if (this.isGenerate(mobAppForm, channel, copInd, delInd, dmsInd, orderDto.getReasonCd())) {
				if("AF001".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& ("RET".equalsIgnoreCase(orderNature) || "UPG".equalsIgnoreCase(orderNature))){
					RptKeyInformationSheetDTO rptKeyInformationSheetDTO = this.retrieveRptKeyInformationSheetDTO(orderId);
					if (rptKeyInformationSheetDTO != null) {
						rptKeyInformationSheetDTO.setCustomerCopyInd("Y");
						
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptKeyInformationSheetDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF001");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							RptKeyInformationSheetDTO compRptKeyInformationSheetDTO = new RptKeyInformationSheetDTO();
							BeanUtils.copyProperties(compRptKeyInformationSheetDTO, rptKeyInformationSheetDTO);
							compRptKeyInformationSheetDTO.setCustomerCopyInd(null);
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptKeyInformationSheetDTO, locale, false, brand);
						}
					}
				} else if( (("AF002".equalsIgnoreCase(mobAppForm.getAppFormId()) && !isStudentPlan) 
							|| ("AF034".equalsIgnoreCase(mobAppForm.getAppFormId()) && isStudentPlan))
						&& ("RET".equalsIgnoreCase(orderNature) || "UPG".equalsIgnoreCase(orderNature))) {
					RptRetAppMobileServDTO rptRetAppMobileServDTO = 
							this.retrieveRptRetAppMobileServSec(orderId, locale, orderNature, rptPrintIndDTO, orderDto,brand);
					/*******************************************************/
					String ceInd = orderDto.getCeInd();
					if("Y".equalsIgnoreCase(ceInd)) {
						rptRetAppMobileServDTO.setServiceReqDate(rptRetAppMobileServDTO.getAppendTcStartDate());
					}
					String basketId = orderService.findBasketId(orderId);
					boolean isStaffOffer = false;
					try {
						isStaffOffer = orderDao.isStaffOffer(basketId);
					} catch (DAOException e1) {
						logger.error("Exception caught in isStaffOffer", e1);
					}
					StringBuilder staffReminder = new StringBuilder();
					StringBuilder staffReminderChi = new StringBuilder();
					if (isStaffOffer) {
						staffReminder.append("I declare that I have read and understand the PCCW Company’s policy about staff offer of mobile service plan and I am eligible for the staff offer of mobile service plans.");
						rptRetAppMobileServDTO.setStaffReminder(staffReminder.toString());

						staffReminderChi.append("本人為合資格員工已閱畢及了解電訊盈科員工流動通訊服務計劃優惠之政策。");
						rptRetAppMobileServDTO.setStaffReminderChi(staffReminderChi.toString());
					}else{
						staffReminder=null;
						staffReminderChi=null;
					}
					if (rptRetAppMobileServDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptRetAppMobileServDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						if (signOffMap.containsKey(BANK_SIGN)) {
							rptRetAppMobileServDTO.getSectG().setCustSignatureAutoPay(signOffMap.get(BANK_SIGN).getSignatureInputStream());
						}
						rptRetAppMobileServDTO.setMob0060OptOutInd(this.isMob0060Optout(orderId));
						rptRetAppMobileServDTO.setCustomerCopyInd("Y");
                        
						/********************Multi Sim***************************/
						
						List<RptMultiSimDTO> rptMultiSimDTOList= new ArrayList<RptMultiSimDTO>();
						
						if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
							
							for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
								List<MultiSimInfoMemberDTO> multiSimInfoMemberDTOs=multiSimInfoDTO.getMembers();
								
								if (multiSimInfoMemberDTOs != null && multiSimInfoMemberDTOs.size() > 0) {
									for(MultiSimInfoMemberDTO multiSimInfoMemberDTO: multiSimInfoMemberDTOs){
										if (!"MUPS06".equals(multiSimInfoMemberDTO.getMemberOrderType()) &&
												!"MUPS07".equals(multiSimInfoMemberDTO.getMemberOrderType()) && 
												!"MUPS99".equals(multiSimInfoMemberDTO.getMemberOrderType())){
											RptMultiSimDTO rptMultiSimDTO= new RptMultiSimDTO();
											rptMultiSimDTO.setMemberNum(multiSimInfoMemberDTO.getMemberNum());
											rptMultiSimDTO.setMultiMsisdn(multiSimInfoMemberDTO.getMsisdn());
											rptMultiSimDTO.setMultiSimIccid(multiSimInfoMemberDTO.getSim().getIccid());
											rptMultiSimDTO.setMemberOrderType(multiSimInfoMemberDTO.getMemberOrderType());
											rptMultiSimDTO.setCsimInd(multiSimInfoMemberDTO.getCsimInd());
											rptMultiSimDTO.setCurSimIccid(multiSimInfoMemberDTO.getCurSimIccid());
											
											List<RptVasDetailDTO> multiSimMainServDtls=vasDetailService.getReportUseMultiSimRPHSRPList(locale,multiSimInfoMemberDTO.getBasketId(),multiSimInfoMemberDTO.getParentOrderId(),multiSimInfoMemberDTO.getMemberNum());
											List<RptVasDetailDTO> multiSimvasDtls=vasDetailService.getReportUseMultiSimVasDetailtList(locale,multiSimInfoMemberDTO.getBasketId(),multiSimInfoMemberDTO.getParentOrderId(),multiSimInfoMemberDTO.getMemberNum());
											List<RptVasDetailDTO> multiSimvasOptionalDtls=vasDetailService.getReportUseMultiSimVasOptionalDetailtList(locale,multiSimInfoMemberDTO.getBasketId(),multiSimInfoMemberDTO.getParentOrderId(),multiSimInfoMemberDTO.getMemberNum());
											rptMultiSimDTO.setMultiSimMainServDtls(multiSimMainServDtls);
											rptMultiSimDTO.setMultiSimvasDtls(multiSimvasDtls);
											rptMultiSimDTO.setMultiSimvasOptionalDtls(multiSimvasOptionalDtls);
											
											
											rptMultiSimDTO.setMultiSimType(multiSimInfoMemberDTO.getNfcInd());
	
											rptMultiSimDTOList.add(rptMultiSimDTO);
	
										}
									}
								}
							}								
								
									rptRetAppMobileServDTO.setMultiSimList(rptMultiSimDTOList);
								} else {
								
									rptRetAppMobileServDTO.setMultiSimList(null);
								}						
						
						try {
							List<MultipleMrtSimDTO> multiSimList = orderDao
									.getMultipleMrtSimDTOList(rptRetAppMobileServDTO
											.getAgreementNum());
							if (multiSimList != null && multiSimList.size() > 0) {
								rptRetAppMobileServDTO.setHaveMultiSim(true);
								rptRetAppMobileServDTO
										.setMultipleMrtSimList(multiSimList);
							} else {
								rptRetAppMobileServDTO.setHaveMultiSim(false);
								rptRetAppMobileServDTO
										.setMultipleMrtSimList(null);
							}
						} catch (DAOException e1) {
							logger.debug("DAOException @ReportServiceImp: getMultipleMrtSimDTOList");
							e1.printStackTrace();
						}
			
						/*******************************************************/
						
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptRetAppMobileServDTO, locale, true, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF002 AF034");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							RptRetAppMobileServDTO compRptRetAppMobileServDTO = new RptRetAppMobileServDTO();
							BeanUtils.copyProperties(compRptRetAppMobileServDTO, rptRetAppMobileServDTO);
							compRptRetAppMobileServDTO.setCustomerCopyInd(null);
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptRetAppMobileServDTO, locale, true, brand);
						}
					}
				} else if ("AF003".equals(mobAppForm.getAppFormId())
						&& this.hasThirdPartyAutopayViaCreditCard(orderId)) {
					RptCreditCardDDAuthDTO rptCreditCardDDAuthDTO = this.retrieveRptCreditCardDDAuthDTO(orderId);
					
					if (rptCreditCardDDAuthDTO != null) {
						if (signOffMap.containsKey(BANK_SIGN)) {
							rptCreditCardDDAuthDTO.setCustSignature(signOffMap.get(BANK_SIGN).getSignatureInputStream());
							if (StringUtils.isNotEmpty(rptCreditCardDDAuthDTO.getCreditCardDocNum())){
								rptCreditCardDDAuthDTO.setCreditCardDocNum(maskedDocNum(rptCreditCardDDAuthDTO.getCreditCardDocNum()));
							}
						}
						
						this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, rptCreditCardDDAuthDTO, locale, false, brand);
					}
				} else if ("AF005".equalsIgnoreCase(mobAppForm.getAppFormId())) {
					List<RptHSTradeDescDTO> rptHSTradeDescDTOList = this.retrieveRptHSTradeDescDTO(orderId);
					if (!CollectionUtils.isEmpty(rptHSTradeDescDTOList)) {
						for (RptHSTradeDescDTO rptHSTradeDescDTO:rptHSTradeDescDTOList){
							requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
							this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptHSTradeDescDTO, locale, false, brand);
							requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
							
							if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
								logger.info("[" + orderId + "] Missing COS Report AF005");
							}
						}
					}
				} else if("AF006".equals(mobAppForm.getAppFormId()) && "Y".equalsIgnoreCase(copInd) && orderId.startsWith("C")) {
					RptRetConfirmForCopDTO rptRetConfirmForCopDTO = new RptRetConfirmForCopDTO();
					rptRetConfirmForCopDTO = this.retrieveRptRetConfirmForCopDTO(orderId);
					if (rptRetConfirmForCopDTO!=null){
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptRetConfirmForCopDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF006");
						}
					}
				} else if ( (("AF007".equalsIgnoreCase(mobAppForm.getAppFormId()) && !isStudentPlan) 
							|| ("AF033".equalsIgnoreCase(mobAppForm.getAppFormId()) && isStudentPlan))
						&& ("RET".equalsIgnoreCase(orderNature) || "UPG".equalsIgnoreCase(orderNature))) {
					
					RptServiceGuideDTO rptServiceGuideDTO = new RptServiceGuideDTO();
					rptServiceGuideDTO.setAgreementNum(orderId);
					if (isStudentPlan) {
						rptServiceGuideDTO.setContentHtml1(reportService.getReportContentHtml("AF033", locale, "contentHtml1"));
						rptServiceGuideDTO.setContentHtml2(reportService.getReportContentHtml("AF033", locale, "contentHtml2"));
						rptServiceGuideDTO.setContentHtml3(reportService.getReportContentHtml("AF033", locale, "contentHtml3"));
						rptServiceGuideDTO.setContentHtml4(reportService.getReportContentHtml("AF033", locale, "contentHtml4"));
						rptServiceGuideDTO.setContentHtml5(reportService.getReportContentHtml("AF033", locale, "contentHtml5"));
					}
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptServiceGuideDTO, locale, true, brand);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report AF007 AF033");
					}
				} else if ("AF008".equals(mobAppForm.getAppFormId()) && this.hasWarmTips(orderId)) {
					RptWarmTipsDTO rptWarmTipsDTO = new RptWarmTipsDTO();
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptWarmTipsDTO, locale, false, brand);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report AF008");
					}
				} else if ("AF009".equals(mobAppForm.getAppFormId())
						&& "Y".equalsIgnoreCase(orderService.getConciergeInd(orderId))) {
					RptConciergeServiPadiPhoneDTO rptConciergeServiPadiPhoneDTO = this.retrieveRptConciergeServiPadiPhoneDTO(orderId, locale);

					if (rptConciergeServiPadiPhoneDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptConciergeServiPadiPhoneDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptConciergeServiPadiPhoneDTO, locale, true, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF009");
						}
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							RptConciergeServiPadiPhoneDTO compRptConciergeServiPadiPhoneDTO = new RptConciergeServiPadiPhoneDTO();
							BeanUtils.copyProperties(compRptConciergeServiPadiPhoneDTO, rptConciergeServiPadiPhoneDTO);
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptConciergeServiPadiPhoneDTO, locale, true, brand);
						}
					}
				} else if ("AF010".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& "Y".equalsIgnoreCase(delInd)
						&& this.hasDeliveryItem(orderId)) {
					RptRetCourierDeliveryGuidelineDTO rptRetCourierDeliveryGuidelineDTO = 
							this.retrieveRptRetCourierDeliveryGuidelineDTO(orderId, rptPrintIndDTO, locale);
					if (rptRetCourierDeliveryGuidelineDTO != null) {
						String mnpInd = orderService.getMnpInd(orderId);
						if (ArrayUtils.contains(brmTooGenerateFormType, orderNature)) {
							rptRetCourierDeliveryGuidelineDTO.setSsFormNewMrtInd(true);
							rptRetCourierDeliveryGuidelineDTO.setSsFormRenewalInd(false);
						}
						if (ArrayUtils.contains(brmGenerationFormType, orderNature) && "Y".equals(mnpInd)) {
							rptRetCourierDeliveryGuidelineDTO.setMnpApplicationForm(true);
						}

						if (ArrayUtils.contains(tooGenerationFormType, orderNature)) {
							CustomerProfileDTO currentCustProfile = customerProfileService.getCurrentCustomerProfile(orderId);
							if (!currentCustProfile.getIdDocNum().equals(customerProfileDto.getIdDocNum())) {
								rptRetCourierDeliveryGuidelineDTO.setOwnershipFormInd(true);
								rptRetCourierDeliveryGuidelineDTO.setChangeOfServiceForm(true);
							}
						}
						if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
							List<MultipleMrtSimDTO> multiSimListOdd 
							= new ArrayList<MultipleMrtSimDTO>();
							List<MultipleMrtSimDTO> multiSimListEven 
							= new ArrayList<MultipleMrtSimDTO>();
							int index = 0;
							
							for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
								for(MultiSimInfoMemberDTO member: multiSimInfoDTO.getMembers()){
									rptRetCourierDeliveryGuidelineDTO.setHaveMultiSim(true);
									if ("MUPS02".equalsIgnoreCase(member.getMemberOrderType()) || "MUPS05".equalsIgnoreCase(member.getMemberOrderType())) {
										rptRetCourierDeliveryGuidelineDTO.setMnpApplicationForm(true);
										rptRetCourierDeliveryGuidelineDTO.setChangeOfMobileNumInd(true);
									}
									
									MultipleMrtSimDTO multiMrtSimDTO = new MultipleMrtSimDTO();
									multiMrtSimDTO.setOrderId(member.getMemberOrderId());
									
									if ("MUPS01".equalsIgnoreCase(member.getMemberOrderType()) || "MUPS02".equalsIgnoreCase(member.getMemberOrderType()) || "MUPS05".equalsIgnoreCase(member.getMemberOrderType())) {
										multiMrtSimDTO.setSimIccid(member.getOriginalSimICCID());
									}else if ("MUPS03".equalsIgnoreCase(member.getMemberOrderType()) || "MUPS04".equalsIgnoreCase(member.getMemberOrderType())){
											if ("Y".equalsIgnoreCase(member.getCsimInd())){
												multiMrtSimDTO.setSimIccid(member.getOriginalSimICCID());
											}
										}
									
									
						    		if (index % 2 == 1) {
						    			multiSimListOdd.add(multiMrtSimDTO);
						    		} else {
						    			multiSimListEven.add(multiMrtSimDTO);
						    		}
						    		index += 1;
								}
							}
							
							rptRetCourierDeliveryGuidelineDTO.setMultipleMrtSimListOdd(multiSimListOdd);
							rptRetCourierDeliveryGuidelineDTO.setMultipleMrtSimListEven(multiSimListEven);
						}
					}
					if (rptRetCourierDeliveryGuidelineDTO != null) {
						this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, rptRetCourierDeliveryGuidelineDTO, locale, false, brand);
					}
				} else if ("Y".equalsIgnoreCase(delInd)
						&& (("AF011".equalsIgnoreCase(mobAppForm.getAppFormId()) && this.hasDeliveryItem(orderId) && !this.hasDoaRequest(orderId))
						|| ("AF022".equalsIgnoreCase(mobAppForm.getAppFormId()) && this.hasDoaRequest(orderId)))) {
					RptRetCosDeliveryNoteDTO mobCosDeliveryNoteDTO = this.retrieveCosDeliveryNoteCustDTO(orderId, mobAppForm, locale);
					if (mobCosDeliveryNoteDTO != null) {
						/**** Override hotline no. ****/
						String hotline = staffInfoDao.getCcsHotline(mobCosDeliveryNoteDTO.getSalesCd(), brand);
						if (StringUtils.isNotBlank(hotline)){
							mobCosDeliveryNoteDTO.setHotLine(hotline);
						}
						
						/****MultiSim****/
						if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
							if (mobCosDeliveryNoteDTO.getStockList() != null && mobCosDeliveryNoteDTO.getStockList().size() > 0) {
								for (StockDTO stksItem:mobCosDeliveryNoteDTO.getStockList()){
									MobileSimInfoDTO mobileSimInfoDTO=orderService.getChangedSim(orderId);
									if (mobileSimInfoDTO != null) {
										if (stksItem.getItemSerial().equalsIgnoreCase(mobileSimInfoDTO.getIccid())){
											stksItem.setItemDesc(stksItem.getItemDesc()+" (Primary MRT: "+orderDto.getMsisdn()+")");
											//stksItem.setItemSerial(stksItem.getItemSerial()+" (Primary MRT: "+orderDto.getMsisdn()+")");
										}
									}
									for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
										for(MultiSimInfoMemberDTO member: multiSimInfoDTO.getMembers()){
											if ("MUPS01".equalsIgnoreCase(member.getMemberOrderType()) || "MUPS02".equalsIgnoreCase(member.getMemberOrderType()) || "MUPS05".equalsIgnoreCase(member.getMemberOrderType())) {
												if (stksItem.getType().equalsIgnoreCase("SIM") && member.getOriginalSimICCID()!=null){	
													if (member.getOriginalSimICCID().equalsIgnoreCase(stksItem.getItemSerial())){
														stksItem.setItemDesc(stksItem.getItemDesc()+" (MRT: "+member.getMsisdn()+")");
													}
												}
											}else if ("MUPS03".equalsIgnoreCase(member.getMemberOrderType()) || "MUPS04".equalsIgnoreCase(member.getMemberOrderType())){
													if ("Y".equalsIgnoreCase(member.getCsimInd())){
														if (stksItem.getType().equalsIgnoreCase("SIM") && member.getOriginalSimICCID()!=null){	
															if (member.getOriginalSimICCID().equalsIgnoreCase(stksItem.getItemSerial())){
																stksItem.setItemDesc(stksItem.getItemDesc()+" (MRT: "+member.getMsisdn()+")");
															}
														}
													}else{
														if (stksItem.getType().equalsIgnoreCase("SIM") && member.getCurSimIccid()!=null){	
															if (member.getCurSimIccid().equalsIgnoreCase(stksItem.getItemSerial())){
																stksItem.setItemDesc(stksItem.getItemDesc()+" (MRT: "+member.getMsisdn()+")");
															}
													}
												}
											}
										}
									}
								}
							}
						}
						/****************/
						mobCosDeliveryNoteDTO.setCustomerCopyInd("Y");
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, mobCosDeliveryNoteDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF011 AF022");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							RptRetCosDeliveryNoteDTO compRptRetCosDeliveryNoteDTO = new RptRetCosDeliveryNoteDTO();
							BeanUtils.copyProperties(compRptRetCosDeliveryNoteDTO, mobCosDeliveryNoteDTO);
							compRptRetCosDeliveryNoteDTO.setCustomerCopyInd(null);
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptRetCosDeliveryNoteDTO, locale, false, brand);
						}
					}
				} else if ("AF012".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& ((StringUtils.isNotBlank(simType) && !"0".equals(simType)) || isMemberHaveNFC)) {
					RptNFCConsentDTO rptNFCConsentDTO = this.retrieveRptNFCConsentDTO(orderId);
					if (rptNFCConsentDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptNFCConsentDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptNFCConsentDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF012");
						}
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							RptNFCConsentDTO compRptNFCConsentDTO = new RptNFCConsentDTO();
							BeanUtils.copyProperties(compRptNFCConsentDTO, rptNFCConsentDTO);
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptNFCConsentDTO, locale, false, brand);
						}
					}
				} else if ("AF013".equalsIgnoreCase(mobAppForm.getAppFormId()) 
						&& this.hasOctopusSim(orderId)) {
					
					RptOctopusConsentDTO rptOctopusConsentDTO = this.retrieveRptOctopusConsentDTO(orderId, locale);
					
					if (rptOctopusConsentDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptOctopusConsentDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptOctopusConsentDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF013");
						}
					}
				} else if ("AF014".equalsIgnoreCase(mobAppForm.getAppFormId()) 
						&& rptPrintIndDTO.hasSecretarialService()) {
					
					RptSecretarialServDTO rptSecretarialServDTO = this.retrieveRptSecretarialServDTO(orderId);
					
					if (rptSecretarialServDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptSecretarialServDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptSecretarialServDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF014");
						}
					}
					
				} else if ("AF015".equalsIgnoreCase(mobAppForm.getAppFormId())
					&& ("CRP".equalsIgnoreCase(orderNature))
					&& ("1".equals(channel) || "10".equals(channel))) {
				
					RptRetCosDTO rptRetCosDTO = this.retrieveCosCustDetailDTO(orderId, locale, orderDto);
					
					if (rptRetCosDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptRetCosDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						if (StringUtils.isNotEmpty(rptRetCosDTO.getIdDocNum())){
						rptRetCosDTO.setIdDocNum(maskedDocNum(rptRetCosDTO.getIdDocNum()));
						}
						rptRetCosDTO.setCustomerCopyInd("Y");
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptRetCosDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF015");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							RptRetCosDTO rptRetCosCompCopyDTO=new RptRetCosDTO();
							BeanUtils.copyProperties(rptRetCosCompCopyDTO, rptRetCosDTO);
							rptRetCosCompCopyDTO.setCustomerCopyInd(null);
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, rptRetCosCompCopyDTO, locale, false, brand);
						}
					}
				} else if ("AF016".equalsIgnoreCase(mobAppForm.getAppFormId()) 
						&& rptPrintIndDTO.isiGuardLDS()) {
					IGuardDTO iGuardDTO = this.retrieveIGuardDTOCustomerInfo(orderId, channel,orderDto, "LDS");
					if (iGuardDTO != null) {
						if (signOffMap.containsKey(IGUARD_SIGN)) {
							iGuardDTO.setCustSignature(signOffMap.get(IGUARD_SIGN).getSignatureInputStream());
						}
						
						iGuardDTO.setCopy("Customer Copy");
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, iGuardDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF016");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							IGuardDTO iGuardCompCopyDTO = new IGuardDTO();
							BeanUtils.copyProperties(iGuardCompCopyDTO, iGuardDTO);
							iGuardCompCopyDTO.setCopy("Office Copy");
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, iGuardCompCopyDTO, locale, false, brand);
						}
					}
					
				} else if ("AF017".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& rptPrintIndDTO.isiGuardLDS()) {
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					this.addRequiredFormList(requiredMobAppFormList, mobAppForm, null, locale, false, brand);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report AF017");
					}
				} else if ("AF030".equalsIgnoreCase(mobAppForm.getAppFormId()) 
						&&  rptPrintIndDTO.isiGuardAD()) {
					IGuardDTO iGuardDTO = this.retrieveIGuardDTOCustomerInfo(orderId, channel,orderDto, "AD");
					if (iGuardDTO != null) {
						if (signOffMap.containsKey(IGUARD_SIGN)) {
							iGuardDTO.setCustSignature(signOffMap.get(IGUARD_SIGN).getSignatureInputStream());
						}
						
						iGuardDTO.setCopy("Customer Copy");
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, iGuardDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF030");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							IGuardDTO iGuardCompCopyDTO = new IGuardDTO();
							BeanUtils.copyProperties(iGuardCompCopyDTO, iGuardDTO);
							iGuardCompCopyDTO.setCopy("Office Copy");
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, iGuardCompCopyDTO, locale, false, brand);
						}
					}
					
				} else if ("AF031".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& rptPrintIndDTO.isiGuardAD()) {
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					this.addRequiredFormList(requiredMobAppFormList, mobAppForm, null, locale, false, brand);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report AF031");
					}
				}else if ("AF018".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& rptPrintIndDTO.hasMobileSafePhoneSafety()) {
					
					RptMobileSafetyPhoneDTO rptMobileSafetyPhoneDTO = this.retrieveRptMobileSafetyPhoneDTO(orderId);
					
					if (rptMobileSafetyPhoneDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptMobileSafetyPhoneDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptMobileSafetyPhoneDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF018");
						}
					}
				} else if ("AF019".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& rptPrintIndDTO.hasMobileSafePhoneSafety()) {
					RptMobileSafetyPhoneTnCDTO rptMobileSafetyPhoneTnCDTO = this.retrieveRptMobileSafetyPhoneTnCDTO(orderId, locale, orderDto);
					
					if (rptMobileSafetyPhoneTnCDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptMobileSafetyPhoneTnCDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptMobileSafetyPhoneTnCDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF019");
						}
					}
				} else if ("AF020".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& rptPrintIndDTO.hasMobileSafePhoneSafety()
						&& "2".equals(channel)) {
					RptMobileSafetyPhoneTnCDTO rptMobileSafetyPhoneTnCDTO = this.retrieveRptMobileSafetyPhoneTnCDTO(orderId, locale, orderDto);
					if (rptMobileSafetyPhoneTnCDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptMobileSafetyPhoneTnCDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptMobileSafetyPhoneTnCDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF020");
						}
					}
				} else if ("AF021".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& rptPrintIndDTO.hasMobileSafePhoneSafety()
						&& ("1".equals(channel) || "10".equals(channel))) {
					RptMobileSafetyPhoneAFCDTO rptMobileSafetyPhoneAFCDTO = this.retrieveRptMobileSafetyPhoneAFCDTO(orderId);
					if (rptMobileSafetyPhoneAFCDTO != null) {
						if (signOffMap.containsKey(CUST_SIGN)) {
							rptMobileSafetyPhoneAFCDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptMobileSafetyPhoneAFCDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF021");
						}
					}
				} else if ("AF023".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& this.hasDoaRequest(orderId)) {
					int seqNo = mobCcsDoaRequestService.getLastSeqNo(orderId);
					if ("SBS".equals(orderId.substring(1, 4))) {
						List<RptRetCountRejDTO> rptRetCountRejDTOList = this.retrieveSBSRptRetCountRejDTO(orderId,seqNo);
						if (!CollectionUtils.isEmpty(rptRetCountRejDTOList)){
							for (RptRetCountRejDTO rptRetCountRejDTO :rptRetCountRejDTOList){
								if (rptRetCountRejDTO != null) {
									rptRetCountRejDTO.setCustomerCopyInd("Y");
									requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
									this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptRetCountRejDTO, locale,
											false, brand);
									requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
									
									if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
										logger.info("[" + orderId + "] Missing COS Report SBS AF023");
									}
									if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
										RptRetCountRejDTO RptRetCountRejCompCopyDTO = new RptRetCountRejDTO();
										BeanUtils.copyProperties(RptRetCountRejCompCopyDTO, rptRetCountRejDTO);
										RptRetCountRejCompCopyDTO.setCustomerCopyInd(null);
										this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm,
												RptRetCountRejCompCopyDTO, locale, false, brand);
									}
								}
							}
						}
					} else {
						RptRetCountRejDTO rptRetCountRejDTO = this.retrieveRptRetCountRejDTO(orderId, seqNo);
						if (rptRetCountRejDTO != null) {
							rptRetCountRejDTO.setCustomerCopyInd("Y");
							requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
							this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptRetCountRejDTO, locale,
									false, brand);
							requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
							
							if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
								logger.info("[" + orderId + "] Missing COS Report AF023");
							}

							if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
								RptRetCountRejDTO RptRetCountRejCompCopyDTO = new RptRetCountRejDTO();
								BeanUtils.copyProperties(RptRetCountRejCompCopyDTO, rptRetCountRejDTO);
								RptRetCountRejCompCopyDTO.setCustomerCopyInd(null);
								this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm,
										RptRetCountRejCompCopyDTO, locale, false, brand);
							}
						}
					}
				} else if ("AF024".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& StringUtils.isNotBlank(simType) && !"0".equals(simType)) {
					RptOctopusTnCDTO rptOctopusTnCDTO = new RptOctopusTnCDTO();
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptOctopusTnCDTO, locale, true, brand);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report AF024");
					}
					
					if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
						RptOctopusTnCDTO compRptOctopusTnCDTO = new RptOctopusTnCDTO();
						BeanUtils.copyProperties(compRptOctopusTnCDTO, rptOctopusTnCDTO);
						this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptOctopusTnCDTO, locale, true, brand);
					}
				} else if ("AF025".equalsIgnoreCase(mobAppForm.getAppFormId()) && ((ArrayUtils.contains(brmGenerationFormType, orderNature))|| (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0))) {
					if (ArrayUtils.contains(brmGenerationFormType, orderNature)){
						if ("Y".equals(orderDto.getMnpInd())){
							RptMobPortAppDTO rptMobPortAppDTO = new RptMobPortAppDTO();
							rptMobPortAppDTO.setAgreementNum(orderDto.getOrderId());
							MnpDTO mnpDto = orderService.getMnp(orderId);
							BeanUtils.copyProperties(rptMobPortAppDTO, mnpDto);
							if (signOffMap.containsKey(CUST_SIGN)) {
								rptMobPortAppDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
							}
							if (StringUtils.isNotEmpty(rptMobPortAppDTO.getCustIdDocNum())){
							rptMobPortAppDTO.setCustIdDocNum(maskedDocNum(rptMobPortAppDTO.getCustIdDocNum()));
							}
							requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
							this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptMobPortAppDTO, locale, false, brand);
							requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
							
							if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
								logger.info("[" + orderId + "] Missing COS Report AF025");
							}
							
							if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
								RptMobPortAppDTO compRptMobPortAppDTO = new RptMobPortAppDTO();
								BeanUtils.copyProperties(compRptMobPortAppDTO, rptMobPortAppDTO);
								this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptMobPortAppDTO, locale, false, brand);
							}
						}
					}
					
					if ((multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0)){
						for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
							for(MultiSimInfoMemberDTO member: multiSimInfoDTO.getMembers()){
								if("MUPS02".equalsIgnoreCase(member.getMemberOrderType()) || "MUPS05".equalsIgnoreCase(member.getMemberOrderType())) {
	
										RptMobPortAppDTO rptMultiSimMobPortAppDTO = new RptMobPortAppDTO();
										rptMultiSimMobPortAppDTO.setAgreementNum(member.getMemberOrderId());
										rptMultiSimMobPortAppDTO.setCustName(member.getMnpCustName());
										rptMultiSimMobPortAppDTO.setCustNameChi("");
										rptMultiSimMobPortAppDTO.setMsisdn(member.getMnpNumber());
										if (StringUtils.isNotEmpty(member.getMnpDocNo())){
											rptMultiSimMobPortAppDTO.setCustIdDocNum(maskedDocNum(member.getMnpDocNo()));
										}
										rptMultiSimMobPortAppDTO.setDno(member.getMnpDno());
										rptMultiSimMobPortAppDTO.setRno(member.getMnpRno());
										if (member.getMnpCutOverDate() != null && !member.getMnpCutOverDate().isEmpty()) {
											rptMultiSimMobPortAppDTO.setCutoverDate(Utility.string2Date(member.getMnpCutOverDate()));
										} else {
											rptMultiSimMobPortAppDTO.setCutoverDate(orderDto.getSrvReqDate());
										}
										rptMultiSimMobPortAppDTO.setCutoverTime(member.getMnpCutOverTime());
										rptMultiSimMobPortAppDTO.setMnpTicketNum(member.getMnpTicketNum());
										rptMultiSimMobPortAppDTO.setPrePaidSimDocWithCert("Y".equals(member.getPrePaidSimDocInd()));
										rptMultiSimMobPortAppDTO.setPrePaidSimDocWithoutCert("N".equals(member.getPrePaidSimDocInd()));
									
										if (!"Prepaid Sim".equals(member.getMnpCustName())
												&& !customerProfileDto.getIdDocNum().equalsIgnoreCase(member.getMnpDocNo())) {
											if (signOffMap.containsKey(member.getMemberOrderId())) {
												rptMultiSimMobPortAppDTO.setCustSignature(signOffMap.get(member.getMemberOrderId()).getSignatureInputStream());
											}
										} else {
											if (signOffMap.containsKey(CUST_SIGN)) {
												rptMultiSimMobPortAppDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
											}
										}
										
										requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
										this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptMultiSimMobPortAppDTO, locale, false, brand);
										requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
										
										if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
											logger.info("[" + orderId + "] Missing COS Report MUP AF025");
										}
										
										if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
											RptMobPortAppDTO compRptMultiSimMobPortAppDTO = new RptMobPortAppDTO();
											BeanUtils.copyProperties(compRptMultiSimMobPortAppDTO, rptMultiSimMobPortAppDTO);
											this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptMultiSimMobPortAppDTO, locale, false, brand);
										}
								}
							}
						}
					}
				} else if ("AF026".equalsIgnoreCase(mobAppForm.getAppFormId())
						&&  ((ArrayUtils.contains(tooGenerationFormType, orderNature))||(multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0))) {
					if (ArrayUtils.contains(tooGenerationFormType, orderNature)){
							CustomerProfileDTO currentCustProfile = customerProfileService.getCurrentCustomerProfile(orderId);
							if (!currentCustProfile.getIdDocNum().equals(customerProfileDto.getIdDocNum())){
							MobCcsChgServCustInfoRefundDTO rptChgServCustInfoRefundDTO = new MobCcsChgServCustInfoRefundDTO();
							rptChgServCustInfoRefundDTO.setOwnershipFormInd(true);
							/*MobileSimInfoDTO mobileSimInfoDto=orderService.getSim(orderId);
							
							if (!StringUtils.isEmpty((mobileSimInfoDto.getIccid()))) {
								rptChgServCustInfoRefundDTO.setIccidInd(true);
								rptChgServCustInfoRefundDTO.setIccid(mobileSimInfoDto.getIccid());
							}*/
							String NewMsisdn = orderService.getTooOrderMrt(orderId);
							rptChgServCustInfoRefundDTO.setNewMsisdn(NewMsisdn);
							String basketId = orderService.findBasketId(orderId);
							BasketDTO basketDto = this.getBasketAttributeCOS(basketId,
									Utility.date2String(orderDto.getAppInDate(),
											BomWebPortalConstant.DATE_FORMAT));
						
							if (!StringUtils.isEmpty(basketDto.getModelId())) {
								rptChgServCustInfoRefundDTO.setHandsetind(true);
							}
						
							
							if (!StringUtils.isEmpty(basketDto.getModelId())) {
								rptChgServCustInfoRefundDTO.setHandsetind(true);
							}
							
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
								rptChgServCustInfoRefundDTO.setIdDocNum(maskedDocNum(customerProfileDto.getIdDocNum()));
	
							} else if ("BS".equals(customerProfileDto.getIdDocType())) {
								rptChgServCustInfoRefundDTO.setIdDocType("BR No.:");
								rptChgServCustInfoRefundDTO.setCustomerNameLabelDisp("Company Name:");
								rptChgServCustInfoRefundDTO.setCustomerName(customerProfileDto.getCompanyName());
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
								rptChgServCustInfoRefundDTO.setIdDocNum(maskedDocNum(customerProfileDto.getIdDocNum()));
							}
							rptChgServCustInfoRefundDTO.setContactPhone(customerProfileDto.getContactPhone());
							//rptChgServCustInfoRefundDTO.setIdDocNum(customerProfile.getIdDocNum());
							rptChgServCustInfoRefundDTO.setOrderId(orderDto.getOrderId());
							rptChgServCustInfoRefundDTO.setAppInDateStr(Utility.date2String(
								orderDto.getAppInDate(),
								BomWebPortalConstant.DATE_FORMAT));
							rptChgServCustInfoRefundDTO.setStaffcodeName(orderDto.getSalesCd() + " / " + orderDto.getSalesName());
							
							rptChgServCustInfoRefundDTO.setCustomerCopyInd("Y");
							rptChgServCustInfoRefundDTO.setTransferee(currentCustProfile
									.getTitle()
									+ " "
									+ currentCustProfile.getCustLastName()
									+ " "
									+ currentCustProfile.getCustFirstName());
							if (StringUtils.isNotEmpty(currentCustProfile.getIdDocNum())){
							rptChgServCustInfoRefundDTO.setTransfereeIdNum(maskedDocNum(currentCustProfile.getIdDocNum()));
							}
							rptChgServCustInfoRefundDTO.setTransferOwnershipTargetCommDateStr(Utility.date2String(orderDto.getSrvReqDate(),BomWebPortalConstant.DATE_FORMAT));
							if (signOffMap.containsKey(CUST_SIGN)) {
								rptChgServCustInfoRefundDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
								if (rptChgServCustInfoRefundDTO.isOwnershipFormInd()){
									rptChgServCustInfoRefundDTO.setTransfereeSignature(signOffMap.get(TRANSFEREE_SIGN).getSignatureInputStream());
								}
							}
							rptChgServCustInfoRefundDTO.setCustomerCopyInd("Y");
							requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
							this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptChgServCustInfoRefundDTO, locale, false, brand);
							requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
							
							if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
								logger.info("[" + orderId + "] Missing COS Report AF026");
							}
							
							if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
								MobCcsChgServCustInfoRefundDTO compRptChgServCustInfoRefundDTO = new MobCcsChgServCustInfoRefundDTO();
								BeanUtils.copyProperties(compRptChgServCustInfoRefundDTO, rptChgServCustInfoRefundDTO);
								compRptChgServCustInfoRefundDTO.setCustomerCopyInd(null);
								this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptChgServCustInfoRefundDTO, locale, false, brand);
							}
						}
					}
					
					for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
						for(MultiSimInfoMemberDTO member: multiSimInfoDTO.getMembers()){
							if(("MUPS02".equalsIgnoreCase(member.getMemberOrderType())) || ("MUPS05".equalsIgnoreCase(member.getMemberOrderType()))
									|| ("MUPS04".equalsIgnoreCase(member.getMemberOrderType()) && StringUtils.isNotEmpty(customerProfileDto.getIdDocNum()) && !customerProfileDto.getIdDocNum().equalsIgnoreCase(member.getCurIdDocNum()))
								) {
									MobCcsChgServCustInfoRefundDTO rptChgServCustInfoRefundDTO = new MobCcsChgServCustInfoRefundDTO();
								
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
										rptChgServCustInfoRefundDTO.setIdDocNum(maskedDocNum(customerProfileDto.getIdDocNum()));
										
									} else if ("BS".equals(customerProfileDto.getIdDocType())) {
										rptChgServCustInfoRefundDTO.setIdDocType("BR No.:");
										rptChgServCustInfoRefundDTO.setCustomerNameLabelDisp("Company Name:");
										rptChgServCustInfoRefundDTO.setCustomerName(customerProfileDto.getCompanyName());
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
										rptChgServCustInfoRefundDTO.setIdDocNum(maskedDocNum(customerProfileDto.getIdDocNum()));
									}
									rptChgServCustInfoRefundDTO.setContactPhone(customerProfileDto.getContactPhone());
									//rptChgServCustInfoRefundDTO.setIdDocNum(customerProfile.getIdDocNum());
								
									rptChgServCustInfoRefundDTO.setOrderId(member.getMemberOrderId());
									rptChgServCustInfoRefundDTO.setAppInDateStr(Utility.date2String(
										orderDto.getAppInDate(),
										BomWebPortalConstant.DATE_FORMAT));
									rptChgServCustInfoRefundDTO.setStaffcodeName(orderDto.getSalesCd() + " / " + orderDto.getSalesName());
									//Transfer content
									if ("MUPS04".equalsIgnoreCase(member.getMemberOrderType())) {
										rptChgServCustInfoRefundDTO.setOwnershipFormInd(true);
										rptChgServCustInfoRefundDTO.setChangeOfMobileNumInd(false);
										rptChgServCustInfoRefundDTO.setTransferee(member.getCurFirstName() + " " + member.getCurLastName());
										rptChgServCustInfoRefundDTO.setTransfereeIdNum(maskedDocNum(member.getCurIdDocNum()));
										rptChgServCustInfoRefundDTO.setTransferOwnershipTargetCommDateStr(Utility.date2String(orderDto.getSrvReqDate(),BomWebPortalConstant.DATE_FORMAT));
									} else {
										rptChgServCustInfoRefundDTO.setChangeOfMobileNumInd(true);
										rptChgServCustInfoRefundDTO.setMnpMsisdn(member.getMnpNumber());
										rptChgServCustInfoRefundDTO.setTransferee(member.getMnpCustName());
										rptChgServCustInfoRefundDTO.setTransfereeIdNum(maskedDocNum(member.getMnpDocNo()));
										rptChgServCustInfoRefundDTO.setTransferOwnershipTargetCommDateStr(member.getMnpCutOverDate());
									
										if (StringUtils.isNotEmpty(member.getMnpCutOverDate())) {
											rptChgServCustInfoRefundDTO.setChangeMobNumTargetCommDateStr(member.getMnpCutOverDate());
										} else {
											rptChgServCustInfoRefundDTO.setChangeMobNumTargetCommDateStr(Utility.date2String(orderDto.getSrvReqDate(),BomWebPortalConstant.DATE_FORMAT));
										}
									}
								
									if (StringUtils.isNotEmpty(member.getMsisdn())) {
										rptChgServCustInfoRefundDTO.setNewMsisdn(member.getMsisdn());
									} else {
										rptChgServCustInfoRefundDTO.setNewMsisdn("N/A");
									}
				
									if ("Y".equalsIgnoreCase(member.getCsimInd()) || StringUtils.isNotEmpty((member.getSim().getIccid()))) {
										rptChgServCustInfoRefundDTO.setIccidInd(true);
										rptChgServCustInfoRefundDTO.setIccid(member.getSim().getIccid());
									} else if (StringUtils.isNotEmpty((member.getCurSimIccid()))) {
										rptChgServCustInfoRefundDTO.setIccidInd(true);
										rptChgServCustInfoRefundDTO.setIccid(member.getCurSimIccid());
									}
								
/*									rptChgServCustInfoRefundDTO.setCustSignature(signature.getSignatureInputStream());
									rptChgServCustInfoRefundDTO.setTransfereeSignature(signature.getSignatureInputStream());*/
									
									String basketId = orderService.findBasketId(orderId);
									BasketDTO basketDto = this.getBasketAttributeCOS(basketId,
											Utility.date2String(orderDto.getAppInDate(),
													BomWebPortalConstant.DATE_FORMAT));
								
									if (!StringUtils.isEmpty(basketDto.getModelId())) {
										rptChgServCustInfoRefundDTO.setHandsetind(true);
									}
								
									if (!"Prepaid Sim".equalsIgnoreCase(member.getMnpCustName())) {
										if (StringUtils.isNotBlank(customerProfileDto.getIdDocNum()) && StringUtils.isNotBlank(member.getMnpDocNo())) {
											if (!customerProfileDto.getIdDocNum().equalsIgnoreCase(member.getMnpDocNo())) {
												rptChgServCustInfoRefundDTO.setOwnershipFormInd(true);
												}
										}
									}
								
									if (!"Prepaid Sim".equals(member.getMnpCustName())
											&& !customerProfileDto.getIdDocNum().equalsIgnoreCase(member.getMnpDocNo())) {
										if (signOffMap.containsKey(CUST_SIGN)) {
											rptChgServCustInfoRefundDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
											if (signOffMap.containsKey(member.getMemberOrderId())) {
												rptChgServCustInfoRefundDTO.setTransfereeSignature(signOffMap.get(member.getMemberOrderId()).getSignatureInputStream());
											}
										}
									} else {
										if (signOffMap.containsKey(CUST_SIGN)) {
											rptChgServCustInfoRefundDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
											if (rptChgServCustInfoRefundDTO.isOwnershipFormInd()){
												rptChgServCustInfoRefundDTO.setTransfereeSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
											}
										}
									}
								
									rptChgServCustInfoRefundDTO.setCustomerCopyInd("Y");
									rptChgServCustInfoRefundDTO.setIdDocNum(maskedDocNum(customerProfileDto.getIdDocNum()));
									requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
									this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptChgServCustInfoRefundDTO, locale, false, brand);
									requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
									
									if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
										logger.info("[" + orderId + "] Missing COS Report MUP AF026");
									}
									
									if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
										MobCcsChgServCustInfoRefundDTO compRptChgServCustInfoRefundDTO = new MobCcsChgServCustInfoRefundDTO();
										BeanUtils.copyProperties(compRptChgServCustInfoRefundDTO, rptChgServCustInfoRefundDTO);
										compRptChgServCustInfoRefundDTO.setCustomerCopyInd(null);
										this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptChgServCustInfoRefundDTO, locale, false, brand);
									}
							}
						}
					}
				}else if ("AF032".equalsIgnoreCase(mobAppForm.getAppFormId())
						&& this.hasTNGServiceDummyVas(orderId)) {
		
					RptTnGServiceFormDTO rptTnGServiceFormDTO = this.retrieveRptTnGServiceFormDTO(orderId);
					if (rptTnGServiceFormDTO != null) {
						rptTnGServiceFormDTO.setCustomerCopyInd("Y");
						if (StringUtils.isNotEmpty(rptTnGServiceFormDTO.getIdDocNum())){
							rptTnGServiceFormDTO.setIdDocNum(maskedDocNum(rptTnGServiceFormDTO.getIdDocNum()));
						}
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptTnGServiceFormDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF032");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							RptTnGServiceFormDTO compRptTnGServiceFormDTO = new RptTnGServiceFormDTO();
							BeanUtils.copyProperties(compRptTnGServiceFormDTO, rptTnGServiceFormDTO);
							compRptTnGServiceFormDTO.setCustomerCopyInd("N");
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, compRptTnGServiceFormDTO, locale, false, brand);
						}
					}
				}else if ( ("AF038".equals(mobAppForm.getAppFormId()) && isIphone7TradeInBasket) || ("AF039".equals(mobAppForm.getAppFormId()) && isIphone8TradeInBasket)){
					IPhoneTradeInFormDTO iPhoneTradeInFormDTO = this.retrieveiPhoneTradeInFormDTO(orderId);
					if (iPhoneTradeInFormDTO!=null){
					boolean subscribedUADInd=iGuardService.isIGuardUADPlanSubscribed(orderId,"6666666669");
					if (subscribedUADInd){
						iPhoneTradeInFormDTO.setIsIGuardUAD("Y");
					}
					iPhoneTradeInFormDTO.setHandsetModel(mobileDetailService.getSSFormDesc(orderId));
					iPhoneTradeInFormDTO.setOrderType("N");
					iPhoneTradeInFormDTO.setpdf(locale.equals(BomWebPortalConstant.RPT_LANG_ZH_HK)?"zh":"en",isIphone8TradeInBasket, brand);
					iPhoneTradeInFormDTO.setMnpType("N");
					iPhoneTradeInFormDTO.setCustomerCopyInd("Y");
						if (signOffMap!=null && signOffMap.size()>0){
							if (signOffMap.containsKey(CUST_SIGN)) {
								iPhoneTradeInFormDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
							}
						}
						
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, iPhoneTradeInFormDTO, locale, true, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF038 AF039");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							IPhoneTradeInFormDTO iPhoneTradeInFormCustCopyDTO = new IPhoneTradeInFormDTO();
							BeanUtils.copyProperties(iPhoneTradeInFormCustCopyDTO, iPhoneTradeInFormDTO);
							iPhoneTradeInFormCustCopyDTO.setCustomerCopyInd("N");
							
							//iPhoneTradeInFormCustCopyDTO.setCustomerCopyInd("N");
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, iPhoneTradeInFormCustCopyDTO, locale, true, brand);
						}
					}

					
				}else if ("AF050".equals(mobAppForm.getAppFormId()) && ArrayUtils.contains(brmTooGenerateFormType, orderNature)){
					String configFiles = "reportConstantM2.xml";
					ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFiles);
					RptAppMobileServDTO rptAppMobileServDTO = this.retrieveRptAppMobileDTO(orderId, locale, orderNature, rptPrintIndDTO, orderDto,brand , multiSimInfoDTOs, mdoInd);
					DisMode distMode = orderDto.getDisMode();
					
					if(orderDto.getBrmHsHlInd() != null && orderDto.getBrmHsHlInd().equalsIgnoreCase("Y")){
						rptAppMobileServDTO.setAppendTcStartDate(orderDto.getCurContractEndDate());
						rptAppMobileServDTO.setBrmHsHlInd(orderDto.getBrmHsHlInd());
						rptAppMobileServDTO.setServiceReqDate(orderDto.getSrvReqDate());
					}
					
					if (rptAppMobileServDTO != null) {
						rptAppMobileServDTO.setCustomerCopyInd("Y");
						if (signOffMap!=null && signOffMap.size()>0){
							if (signOffMap.containsKey(CUST_SIGN)) {
								rptAppMobileServDTO.setCustSignature(signOffMap.get(CUST_SIGN).getSignatureInputStream());
							}
							if (signOffMap.containsKey(BANK_SIGN)) {
								rptAppMobileServDTO.getSectG().setCustSignatureAutoPay(signOffMap.get(BANK_SIGN).getSignatureInputStream());
							}
						}
						
						if ("zh_HK".equalsIgnoreCase(locale)){
							rptAppMobileServDTO.setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateChi1").toString());
							rptAppMobileServDTO.setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateChi2").toString());
							if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
								rptAppMobileServDTO.setCustAgree(appCtx.getBean("ss1010CustAgreeChi").toString());
							}else{
								if (rptAppMobileServDTO.isStudentPlanSubInd()) {
									rptAppMobileServDTO.setCustAgree(appCtx.getBean("ssCustAgreeCYChi").toString());
								} else {
									rptAppMobileServDTO.setCustAgree(appCtx.getBean("ssCustAgreeChi").toString());
								}
							}
						}else{
							rptAppMobileServDTO.setPersonInfoCollectState1(appCtx.getBean("ssPersonInfoCollectStateEng1").toString());
							rptAppMobileServDTO.setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateEng2").toString());
							if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
								rptAppMobileServDTO.setCustAgree(appCtx.getBean("ss1010CustAgreeEng").toString());
							}else{
								if (rptAppMobileServDTO.isStudentPlanSubInd()) {
									rptAppMobileServDTO.setCustAgree(appCtx.getBean("ssCustAgreeCYEng").toString());
								} else {
									rptAppMobileServDTO.setCustAgree(appCtx.getBean("ssCustAgreeEng").toString());
								}
							}
						}
					}
					if (orderNature.equals("T2N")){
					rptAppMobileServDTO.setOrderType("TOO1");
					}else{
						rptAppMobileServDTO.setOrderType("BRM");
					}
					if (StringUtils.isEmpty(rptAppMobileServDTO.getMnpType())){
						rptAppMobileServDTO.setMnpType("New Number");
					}
					if (StringUtils.isNotEmpty(rptAppMobileServDTO.getIdDocNum())){
						rptAppMobileServDTO.setIdDocNum(maskedDocNum(rptAppMobileServDTO.getIdDocNum()));
					}
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptAppMobileServDTO, locale, true, brand);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report AF050");
					}
					
					if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
						RptAppMobileServDTO rptAppMobileServCustCopyDTO = new RptAppMobileServDTO();
						BeanUtils.copyProperties(rptAppMobileServCustCopyDTO, rptAppMobileServDTO);
						rptAppMobileServCustCopyDTO.setCustomerCopyInd(null);
						this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, rptAppMobileServCustCopyDTO, locale, true, brand);
					}
					/*if (distMode!=null){
						if (distMode.equals(OrderDTO.DisMode.P)) {
							BeanUtils.copyProperties(rptAppMobileServCustCopyDTO, rptAppMobileServDTO);
							rptAppMobileServCustCopyDTO.setCustomerCopyInd("Y");
							this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptAppMobileServCustCopyDTO,locale, true, brand);
						}
					}*/
					
				}
				else if ("AF051".equals(mobAppForm.getAppFormId()) && ArrayUtils.contains(brmTooGenerateFormType, orderNature)){
					RptServiceGuideDTO rptServiceGuideDTO = new RptServiceGuideDTO();
					rptServiceGuideDTO.setAgreementNum(orderDto.getAgreementNum());
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptServiceGuideDTO,locale, true, brand);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report AF051");
					}
				}
				
				else if ("AF037".equalsIgnoreCase(mobAppForm.getAppFormId()) 
						&&  rptPrintIndDTO.isiGuardUAD()) {
					IGuardDTO iGuardDTO = this.retrieveIGuardDTOCustomerInfo(orderId, channel,orderDto, "UAD");
					if (iGuardDTO != null) {
						iGuardDTO.setDob(iGuardService.getIGuardUADDateOfBirth(orderDto.getAppInDate(), iGuardDTO.getDob()));			
						if (signOffMap.containsKey(IGUARD_SIGN)) {
							iGuardDTO.setPrivacyInd99992(signOffMap.get(IGUARD_SIGN).isiGuard1()?"Y":"N");

							iGuardDTO.setPrivacyInd99993(signOffMap.get(IGUARD_SIGN).isiGuard2()?"Y":"N");

							iGuardDTO.setPrivacyInd99994(signOffMap.get(IGUARD_SIGN).isiGuard3()?"Y":"N");
							iGuardDTO.setCustSignature(signOffMap.get(IGUARD_SIGN).getSignatureInputStream());
						}
						if ("1".equalsIgnoreCase(brand)){
							iGuardDTO.setBrand1010Ind("N");
						}else{
							iGuardDTO.setBrand1010Ind("Y");
						}
						iGuardDTO.setCopy("Customer Copy");
						iGuardDTO.setMsisdn(iGuardDTO.getContactNo());
						
						requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
						this.addRequiredFormList(requiredMobAppFormList, mobAppForm, iGuardDTO, locale, false, brand);
						requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
						
						if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
							logger.info("[" + orderId + "] Missing COS Report AF037");
						}
						
						if (this.needCompanyCopy(mobAppForm, companyCopyInd)) {
							IGuardDTO iGuardCompCopyDTO = new IGuardDTO();
							BeanUtils.copyProperties(iGuardCompCopyDTO, iGuardDTO);
							iGuardCompCopyDTO.setCopy("Office Copy");
							this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, iGuardCompCopyDTO, locale, false, brand);
						}
					}
					
				// TRAVEL INSURANCE
				} else if (GenericForm.TRAVEL_INSURANCE_FORM.getFormId().equalsIgnoreCase(mobAppForm.getAppFormId()) 
						&&  rptPrintIndDTO.isHasTravelInsurance()) {
					String travelStaffId = "";
					String travelDMInd = "";
					VasDetailDTO travelVasDetailDto=null;
					List<VasDetailDTO> vasSelectedList = vasDetailService.getVasDetailSelectedList("en",orderDto.getOrderId());
					for (VasDetailDTO vas : vasSelectedList) {
						String itemId = vas.getItemId();
						boolean isTravelInsurance = vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE, itemId);
						if (isTravelInsurance) {
							travelVasDetailDto=getVasDetail(itemId);
						}
					}
					List<ComponentDTO> componentList = this.orderService.getComponentList(orderId);
					if(!CollectionUtils.isEmpty(componentList)){
						if(getCodeId(ITEM_TRAVEL_INSURANCE_CODE_TYPE)!=null){
							travelStaffId = getAttbValue(componentList, getCodeId(ITEM_TRAVEL_INSURANCE_CODE_TYPE));
						}
						if(getCodeId(ITEM_TRAVEL_INSURANCE_DM_CODE_TYPE)!=null){
							travelDMInd = getAttbValue(componentList, getCodeId(ITEM_TRAVEL_INSURANCE_DM_CODE_TYPE));
						}
					}	
					
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					printTravelInsuranceForm(locale, signOffMap, requiredMobAppFormList, requiredCompanyCopyMobAppFormList, orderDto, customerProfileDto, brand, mobAppForm, travelVasDetailDto, travelStaffId, travelDMInd, companyCopyInd);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report Travel Insurance");
					}
								
				// HELPER INSURANCE
				} else if (GenericForm.HELPERCARE_INSURANCE_FORM.getFormId().equalsIgnoreCase(mobAppForm.getAppFormId()) 
						&&  rptPrintIndDTO.isHasHelperCareInsurance()) {
					String helperCareStaffId = "";
					String helperCareDMInd = "";
					VasDetailDTO helperCareVasDetailDto = null;
					List<VasDetailDTO> vasSelectedList = vasDetailService.getVasDetailSelectedList("en",orderDto.getOrderId());
					for (VasDetailDTO vas : vasSelectedList) {
						String itemId = vas.getItemId();
						boolean isHelperCareInsurance = vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_HELPER_CARE, itemId);
						if (isHelperCareInsurance) {
							helperCareVasDetailDto=getVasDetail(itemId);
						}
					}
					List<ComponentDTO> componentList = this.orderService.getComponentList(orderId);
					if (!CollectionUtils.isEmpty(componentList)) {
						if(getCodeId(ITEM_HELPERCARE_INSURANCE_CODE_TYPE)!=null){
							helperCareStaffId=getAttbValue(componentList, getCodeId(ITEM_HELPERCARE_INSURANCE_CODE_TYPE));
						}
						if(getCodeId(ITEM_HELPERCARE_INSURANCE_DM_CODE_TYPE)!=null){
							helperCareDMInd = getAttbValue(componentList, getCodeId(ITEM_HELPERCARE_INSURANCE_DM_CODE_TYPE));
						}
					}	
					
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					printHelperCareInsuranceForm(locale, signOffMap, requiredMobAppFormList, requiredCompanyCopyMobAppFormList, orderDto, customerProfileDto, brand, mobAppForm, helperCareVasDetailDto, helperCareStaffId, helperCareDMInd, companyCopyInd);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report Helper Insurance");
					}
					
				// PROJECT EAGLE
				} else if (ProjectEagleReportHelper.FORM_ID.equalsIgnoreCase(mobAppForm.getAppFormId()) 
						&&  rptPrintIndDTO.isHasProjectEagleInsurance()) {

					VasDetailDTO projectEagleVasDetailDto = null;
					String projectEagleDMInd = "";
					ItemDisplayDTO eaglePlanDetail = null;
					ItemDisplayDTO eagleMaxSwap = null;
					ItemDisplayDTO eaglePlanFee = null;
					ItemDisplayDTO eagleSwapFee = null;
					List<VasDetailDTO> vasSelectedList = vasDetailService.getVasDetailSelectedList("en",orderDto.getOrderId());
					for (VasDetailDTO vas : vasSelectedList) {
						String itemId = vas.getItemId();
						boolean isProjectEagleInsurance = vasDetailService.existInSelectionGrpList(ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, itemId);
						if (isProjectEagleInsurance) {
							projectEagleVasDetailDto=getVasDetail(itemId);
							eaglePlanDetail = itemDisplayService.getItemDisplay(Integer.parseInt(itemId), "en","EG_FORM_PLANDTL");
							eagleMaxSwap = itemDisplayService.getItemDisplay(Integer.parseInt(itemId), "en","EG_FORM_MAXSWAP");
							eaglePlanFee = itemDisplayService.getItemDisplay(Integer.parseInt(itemId), "en","EG_FORM_PLANFEE");
							eagleSwapFee = itemDisplayService.getItemDisplay(Integer.parseInt(itemId), "en","EG_FORM_SWAPFEE");
							
						}
					}
					List<ComponentDTO> componentList = this.orderService.getComponentList(orderId);
					if (!CollectionUtils.isEmpty(componentList)) {
						if(getCodeId(ITEM_PROJECT_EAGLE_INSURANCE_DM_CODE_TYPE)!=null){
							projectEagleDMInd = getAttbValue(componentList, getCodeId(ITEM_PROJECT_EAGLE_INSURANCE_DM_CODE_TYPE));
						}
					}	
					
					requiredMobAppFormListSizeBefore = requiredMobAppFormList.size();
					printProjectEagleInsuranceForm(locale, signOffMap, requiredMobAppFormList, requiredCompanyCopyMobAppFormList, orderDto, brand, mobAppForm, projectEagleVasDetailDto, projectEagleDMInd, eaglePlanDetail, eagleMaxSwap, eaglePlanFee, eagleSwapFee, companyCopyInd, channel);
					requiredMobAppFormListSizeAfter = requiredMobAppFormList.size();
					
					if (requiredMobAppFormListSizeBefore == requiredMobAppFormListSizeAfter) {
						logger.info("[" + orderId + "] Missing COS Report Project Eagle");
					}
					
				} else {
					logger.info(String.format("[%s] Not meet the conditions for form generation, Nature = %s, Order Nature = %s, Brand = %s, Locale = %s"
											, orderId, nature, orderNature, brand, locale));
					logForMobAppForm(orderId, mobAppForm);
				}
			} else {
				logger.info(String.format("[%s] No need to gen according to isGenerate() @ MobCosReportServiceImpl.java, Channel = %s, CopInd = %s, DelInd = %s, DmsInd = %s, ReasonCd = %s"
											, orderId, channel, copInd, delInd, dmsInd, orderDto.getReasonCd()));
				logForMobAppForm(orderId, mobAppForm);
			}
		}

		requiredMobAppFormList.addAll(0, requiredCompanyCopyMobAppFormList);
		byte[] pdfData = generateMobAppForminPDF(baosMerged, requiredMobAppFormList, orderId, locale, watermark, brand);
		
		if ("Y".equalsIgnoreCase(save)) {
			String separateFilePath = filePath;
			String fileName = "";
			
			if (filePath.indexOf(".pdf") > 0) {
				separateFilePath = filePath.substring(0, filePath.lastIndexOf("/")+1);
				fileName = filePath.substring(filePath.lastIndexOf("/")+1);
			} else {
				fileName = orderId + (BomWebPortalConstant.RPT_LANG_ZH_HK.equalsIgnoreCase(locale) ? "_CHI" : "_EN") + ".pdf";
			}
			
			reportRepository.savaFile(separateFilePath, fileName, pdfData);
		}
		
		if ("Y".equalsIgnoreCase(updateStatus) && pdfData.length > 0 && orderId.contains("CSBS")) {
			orderService.updatePendingOrderCheckPoint(orderId, "500", "599");
		}
		
		
		
		return pdfData;
	}


	private void logForMobAppForm(String orderId, MobAppFormDTO mobAppForm) {
		logger.info(String.format("[%s] JasperTemplateName = %s, AppFormId = %s, AppFormDesc = %s",
							orderId, mobAppForm.getJasperTemplateName(), mobAppForm.getAppFormId(), mobAppForm.getAppFormDesc()));
	}


	private void logForInitialMobAppForm(String orderId, List<MobAppFormDTO> mobAppFormList) {
		logger.info("*****[" + orderId + "] Start Initializing MobAppForm *****");
		for (MobAppFormDTO mobAppForm : mobAppFormList) {
			logForMobAppForm(orderId, mobAppForm);
		}
		logger.info("*****[" + orderId + "] End Initializing MobAppForm *****");
	}


	private void logForAdditionalServicePrintInd(String orderId, RptPrintIndDTO rptPrintIndDTO) {
		logger.info("[" + orderId + "] hasHandsetTradeIn = " + rptPrintIndDTO.hasHandsetTradeIn());
		logger.info("[" + orderId + "] hasIGuard = " + rptPrintIndDTO.hasIGuard());
		logger.info("[" + orderId + "] hasMobileSafePhoneSafety = " + rptPrintIndDTO.hasMobileSafePhoneSafety());
		logger.info("[" + orderId + "] hasSecretarialService = " + rptPrintIndDTO.hasSecretarialService());
		logger.info("[" + orderId + "] isHasHelperCareInsurance = " + rptPrintIndDTO.isHasHelperCareInsurance());
		logger.info("[" + orderId + "] isHasProjectEagleInsurance = " + rptPrintIndDTO.isHasProjectEagleInsurance());
		logger.info("[" + orderId + "] isHasTravelInsurance = " + rptPrintIndDTO.isHasTravelInsurance());
		logger.info("[" + orderId + "] isiGuardAD = " + rptPrintIndDTO.isiGuardAD());
		logger.info("[" + orderId + "] isiGuardLDS = " + rptPrintIndDTO.isiGuardLDS());
		logger.info("[" + orderId + "] isiGuardUAD = " + rptPrintIndDTO.isiGuardUAD());
	}


	private void printTravelInsuranceForm(String locale, Map<String, SignoffDTO> signOffMap,
			List<MobAppFormDTO> requiredMobAppFormList, List<MobAppFormDTO> requiredCompanyCopyMobAppFormList, 
			OrderDTO orderDto, CustomerProfileDTO customerProfileDto, String brand, MobAppFormDTO mobAppForm, VasDetailDTO travelVasDetailDto, String travelStaffId, 
			String travelDMInd, String companyCopyInd) throws IllegalAccessException, InvocationTargetException {

		
		if (orderDto == null && travelVasDetailDto == null) {
			return;
		}
		
		RptGenericFormTemplateDTO rptGenericFormTemplateDTO = new RptGenericFormTemplateDTO();
		
		String orderId = orderDto.getOrderId();
		
		Map<String, String> genericReportDataMap = new HashMap<String, String>();
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_ID, orderId);
		if (customerProfileDto != null) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUSTOMER_TITLE, customerProfileDto.getTitle());
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUST_LAST_NAME, customerProfileDto.getCustLastName());
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUST_FIRST_NAME, customerProfileDto.getCustFirstName());
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ID_DOC_NUM, maskedDocNum(customerProfileDto.getIdDocNum()));
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_EMAIL_ADDR, customerProfileDto.getEmailAddr());
		}
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_MSISDN, orderDto.getMsisdn());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_APP_IN_DATE, format.format(orderDto.getAppInDate()));
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_SERVICE_REQ_DATE, format.format(orderDto.getSrvReqDate()));
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_SHOP_CODE, orderDto.getShopCode());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_ITEM_ID, travelVasDetailDto.getItemId());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_PROD_CD, travelVasDetailDto.getProdCd());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DISPLAY, travelVasDetailDto.getItemHtml());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_CONTRACT_PERIOD, travelVasDetailDto.getContractPeriod());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_STAFF_ID, travelStaffId);
		
		if (StringUtils.isNotBlank(travelDMInd) && StringUtils.equalsIgnoreCase(travelDMInd,"Y")) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DM_IND, "X");
		}
		if (StringUtils.isNotBlank(travelDMInd) && StringUtils.equalsIgnoreCase(travelDMInd,"N")) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DM_IND, " ");
		}
		
		if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_BRAND,"1010");
		} else if (BomWebPortalConstant.BRAND_CSL.equals(brand)) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_BRAND,"csl");
		}
		
		Map<String, InputStream> genericReportSignatureMap = null;
		InputStream signature = null;
		SignoffDTO travelSign = signOffMap.get(TRAVEL_INSURANCE_FORM_SIGN);
		if (travelSign != null) {
			signature = travelSign.getSignatureInputStream();
			if (signature != null) {
				genericReportSignatureMap = new HashMap<String, InputStream>();
				genericReportSignatureMap.put(GenericForm.TRAVEL_INSURANCE_FORM.getFormName(), signature);
			}
		}
		
		rptGenericFormTemplateDTO = genericReportHelper.getRptGenericFormTemplateDTO(
				GenericForm.TRAVEL_INSURANCE_FORM, locale, genericReportDataMap, genericReportSignatureMap);
			
		this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptGenericFormTemplateDTO, locale, false, brand);
		
		// For Customer Copy (Call Center only)
		if (this.needCompanyCopy(mobAppForm, companyCopyInd)
				&& StringUtils.startsWith(orderId, "C")) {
			this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, rptGenericFormTemplateDTO, locale, false, brand);
		}
	}
	
	private void printHelperCareInsuranceForm(String locale, Map<String, SignoffDTO> signOffMap,
			List<MobAppFormDTO> requiredMobAppFormList, List<MobAppFormDTO> requiredCompanyCopyMobAppFormList, 
			OrderDTO orderDto, CustomerProfileDTO customerProfileDto, String brand, MobAppFormDTO mobAppForm, VasDetailDTO helperCareVasDetailDto, String helperCareStaffId, 
			String helperCareDMInd, String companyCopyInd) throws IllegalAccessException, InvocationTargetException {
	
		if (orderDto == null && helperCareVasDetailDto == null) {
			return;
		}
		
		RptGenericFormTemplateDTO rptGenericFormTemplateDTO = new RptGenericFormTemplateDTO();
		
		String orderId = orderDto.getOrderId();
		
		Map<String, String> genericReportDataMap = new HashMap<String, String>();
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_ID, orderId);
		if (customerProfileDto != null) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUSTOMER_TITLE, customerProfileDto.getTitle());
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUST_LAST_NAME, customerProfileDto.getCustLastName());
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_CUST_FIRST_NAME, customerProfileDto.getCustFirstName());
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ID_DOC_NUM, maskedDocNum(customerProfileDto.getIdDocNum()));
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_EMAIL_ADDR, customerProfileDto.getEmailAddr());
		}
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_MSISDN, orderDto.getMsisdn());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_APP_IN_DATE, format.format(orderDto.getAppInDate()));
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_SERVICE_REQ_DATE, format.format(orderDto.getSrvReqDate()));
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_SHOP_CODE, orderDto.getShopCode());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_PROD_CD, helperCareVasDetailDto.getProdCd());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DISPLAY, helperCareVasDetailDto.getItemHtml());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_CONTRACT_PERIOD, helperCareVasDetailDto.getContractPeriod());
		genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_STAFF_ID, helperCareStaffId);
		
		if (StringUtils.isNotBlank(helperCareDMInd) && StringUtils.equalsIgnoreCase(helperCareDMInd,"Y")) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DM_IND, "X");
		}
		if (StringUtils.isNotBlank(helperCareDMInd) && StringUtils.equalsIgnoreCase(helperCareDMInd,"N")) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DM_IND, " ");
		}
		
		if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_BRAND,"1010");
		} else if (BomWebPortalConstant.BRAND_CSL.equals(brand)) {
			genericReportDataMap.put(GenericReportHelper.REPORT_DATA_MAP_KEY_ORDER_BRAND,"csl");
		}
		
		Map<String, InputStream> genericReportSignatureMap = null;
		InputStream signature = null;
		SignoffDTO helperCareSign = signOffMap.get(HELPERCARE_INSURANCE_FORM_SIGN);
		if (helperCareSign != null) {
			signature = helperCareSign.getSignatureInputStream();
			if (signature != null) {
				genericReportSignatureMap = new HashMap<String, InputStream>();
				genericReportSignatureMap.put(GenericForm.HELPERCARE_INSURANCE_FORM.getFormName(), signature);
			}
		}
		
		rptGenericFormTemplateDTO = genericReportHelper.getRptGenericFormTemplateDTO(
				GenericForm.HELPERCARE_INSURANCE_FORM, locale, genericReportDataMap, genericReportSignatureMap);
			
		this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptGenericFormTemplateDTO, locale, false, brand);
		
		// For Customer Copy (Call Center only)
		if (this.needCompanyCopy(mobAppForm, companyCopyInd)
				&& StringUtils.startsWith(orderId, "C")) {
			this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, rptGenericFormTemplateDTO, locale, false, brand);
		}
	}
	
	private void printProjectEagleInsuranceForm(String locale, Map<String, SignoffDTO> signOffMap,
			List<MobAppFormDTO> requiredMobAppFormList, List<MobAppFormDTO> requiredCompanyCopyMobAppFormList, 
			OrderDTO orderDto, String brand, MobAppFormDTO mobAppForm, VasDetailDTO projectEagleVasDetailDto, String projectEagleDMInd, 
			ItemDisplayDTO eaglePlanDetail, ItemDisplayDTO eagleMaxSwap, ItemDisplayDTO eaglePlanFee, ItemDisplayDTO eagleSwapFee, String companyCopyInd, String channel) throws IllegalAccessException, InvocationTargetException {
	
		if (orderDto == null && projectEagleVasDetailDto == null) {
			return;
		}
		
		RptProjectEagleDTO rptProjectEagleDTO = new RptProjectEagleDTO();
		
		String orderId = orderDto.getOrderId();
		rptProjectEagleDTO.setOrderId(orderId);
		
		CustomerProfileDTO customerProfileDto = customerProfileService.getCustomerProfile(orderId);
		if (customerProfileDto != null) {
			rptProjectEagleDTO.setSurname(customerProfileDto.getCustLastName());
			rptProjectEagleDTO.setGivenName(customerProfileDto.getCustFirstName());
			rptProjectEagleDTO.setTitle(customerProfileDto.getTitle());
			
			rptProjectEagleDTO.setIdDocNum(maskedDocNum(customerProfileDto.getIdDocNum()));
			rptProjectEagleDTO.setDateOfBirth(Utility.maskedDob(customerProfileDto.getDobStr()));
			
			rptProjectEagleDTO.setAddressLine1(customerProfileDto.getProjectEagleAddressLine1());
			rptProjectEagleDTO.setAddressLine2(customerProfileDto.getProjectEagleAddressLine2());
			rptProjectEagleDTO.setAddressLine3(customerProfileDto.getProjectEagleAddressLine3());
			
			rptProjectEagleDTO.setEmail(customerProfileDto.getEmailAddr());
			
			rptProjectEagleDTO.setContactNumber(customerProfileDto.getContactPhone());
		}
		
		if (StringUtils.isNotBlank(projectEagleDMInd) && StringUtils.equalsIgnoreCase(projectEagleDMInd,"Y")) {
			rptProjectEagleDTO.setDirectMarketingInd("X");
		}
		if (StringUtils.isNotBlank(projectEagleDMInd) && StringUtils.equalsIgnoreCase(projectEagleDMInd,"N")) {
			rptProjectEagleDTO.setDirectMarketingInd(" ");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		rptProjectEagleDTO.setDateValue(format.format(orderDto.getAppInDate()));
		rptProjectEagleDTO.setHandsetReceivedDate(format.format(orderDto.getAppInDate()));
		rptProjectEagleDTO.setTargetCommencementDate(format.format(orderDto.getSrvReqDate()));
		
		rptProjectEagleDTO.setShopCode("P" + orderDto.getShopCode());
		rptProjectEagleDTO.setContractPeriod((projectEagleVasDetailDto.getContractPeriod() == null || ("0").equalsIgnoreCase(projectEagleVasDetailDto.getContractPeriod())) ? "Free to Go" : projectEagleVasDetailDto.getContractPeriod() + " Months");
		rptProjectEagleDTO.setImeiNo(orderDto.getImei());
		rptProjectEagleDTO.setSubscriptionMobileNo(orderDto.getMsisdn());
		
		if (eaglePlanDetail != null && eagleMaxSwap != null && eaglePlanFee != null && eagleSwapFee != null ) {
			rptProjectEagleDTO.setPlanDetail(eaglePlanDetail.getHtml());
			rptProjectEagleDTO.setMaxSwap(eagleMaxSwap.getHtml());
			rptProjectEagleDTO.setPlanFee(eaglePlanFee.getHtml());
			rptProjectEagleDTO.setSwapFee(eagleSwapFee.getHtml());
		}
		
		String basketId = orderService.findBasketId(orderId);
		BasketDTO basketDTO = vasDetailService.getBasketAttribute(
						basketId, 
						Utility.date2String(orderDto.getAppInDate(), BomWebPortalConstant.DATE_FORMAT));
		
		if (StringUtils.isNotEmpty(basketDTO.getHsPosItemCd()) && orderDto.getAppInDate() != null) {
			BigDecimal hsPrice = iGuardService.getNsPrice(basketDTO.getHsPosItemCd(), orderDto.getAppInDate());
			rptProjectEagleDTO.setHandsetRetailPrice("HK$" + String.valueOf(hsPrice));
		}
		rptProjectEagleDTO.setHandsetBrandModel((basketDTO.getBrandDesc()+" "+basketDTO.getModelDesc()+" "+basketDTO.getColorDesc()).toUpperCase());
		rptProjectEagleDTO.setChannelId(Integer.parseInt(channel));
		rptProjectEagleDTO = reportService.patchWithLabels(rptProjectEagleDTO);
		

		InputStream signature = null;
		SignoffDTO projectEagleSign = signOffMap.get(PROJECT_EAGLE_INSURANCE_FORM_SIGN);
		if (projectEagleSign != null) {
			signature = projectEagleSign.getSignatureInputStream();
			if (signature != null) {
				rptProjectEagleDTO.setSignature(signature);
			}
		}
		
		boolean isCallCenterOrder = StringUtils.startsWithIgnoreCase(orderId, "C");
		if (isCallCenterOrder) {
			rptProjectEagleDTO.setSignatureLabel(null);
			rptProjectEagleDTO.setSignature(null);
		} 
		this.addRequiredFormList(requiredMobAppFormList, mobAppForm, rptProjectEagleDTO, locale, false, brand);
		
		// For Customer Copy (Call Center only)
		if (this.needCompanyCopy(mobAppForm, companyCopyInd)
				&& StringUtils.startsWith(orderId, "C")) {
			this.addRequiredFormList(requiredCompanyCopyMobAppFormList, mobAppForm, rptProjectEagleDTO, locale, false, brand);
		}
	
	}


	public byte[] generateMobAppForminPDF(FastByteArrayOutputStream baosMerged, List<MobAppFormDTO> requiredMobAppFormList, String orderId, String locale, String watermark, String brand) throws Exception {
		
		logger.info("[" + orderId + "] Prepare final pdf");
		
		ArrayList<InputStream> pdfStreamList = new ArrayList<InputStream>();
		for(MobAppFormDTO mobAppForm: requiredMobAppFormList){
			FastByteArrayOutputStream baos = new FastByteArrayOutputStream();		
			JRDataSource ds = new JRBeanCollectionDataSource(mobAppForm.getpRptDtoArrList());
			logForMobAppForm(orderId, mobAppForm);
			String jasperPath = "";
			if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
				jasperPath = BomWebPortalConstant.JASPER_PATH_MOB_1010;
			} else {
				jasperPath = BomWebPortalConstant.JASPER_PATH_MOB;
			}
			
			
			
			ReportUtil.generatePdfReport(jasperPath + mobAppForm.getJasperTemplateName(), ds, baos);
			if (mobAppForm.getAppFormId().equalsIgnoreCase("AF005")) {
				
				List<ReportDTO> hsTradeDescList = (List<ReportDTO>)mobAppForm.getpRptDtoArrList();
				if (!CollectionUtils.isEmpty(hsTradeDescList)){
					for (ReportDTO reportDto : hsTradeDescList){
						RptHSTradeDescDTO hsTradeDesc = (RptHSTradeDescDTO) reportDto;
						if (StringUtils.isNotEmpty(hsTradeDesc.getPisPath())){
							File pisPdf = new File ("");
							pisPdf =new File(mobPdfFilePath+"/pis/" +hsTradeDesc.getPisPath());
							logger.info("pisPdf : " + pisPdf);
							ArrayList<InputStream> pisForm = new ArrayList<InputStream>(); 
							pisForm.add(new FileInputStream(pisPdf));
							pdfStreamList.addAll(pisForm);
						}else{
							InputStream pdfInStream = baos.getInputStream();
							pdfStreamList.add(pdfInStream);
						}
					}
				}
			}
			else if (mobAppForm.getAppFormId().equalsIgnoreCase("AF007")) {
				File M2ServiceGuide = new File ("");
				if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
					if("en".equals(locale)){
						M2ServiceGuide =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.RetServiceGuide_1010_eng);
					}
					else{
						M2ServiceGuide =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.RetServiceGuide_1010_chi);
					}
				}
				else{
					if("en".equals(locale)){
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
			else if (mobAppForm.getAppFormId().equalsIgnoreCase("AF011") || mobAppForm.getAppFormId().equalsIgnoreCase("AF022")){
				//add the original form
				InputStream pdfInStream = baos.getInputStream();
				pdfStreamList.add(pdfInStream);
				
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
			else if (mobAppForm.getAppFormId().equalsIgnoreCase("AF051")) {
				File M2ServiceGuide = new File ("");
				if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
					if("en".equals(locale)){
						M2ServiceGuide =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.M2ServiceGuide_1010_eng);
					}
					else{
						M2ServiceGuide =new File(mobPdfFilePath+"/"+"1010"+"/"+BomWebPortalConstant.M2ServiceGuide_1010_chi);
					}
				}
				else{
					if("en".equals(locale)){
						M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.M2ServiceGuide_csl_eng);
					}
					else{
						M2ServiceGuide =new File(mobPdfFilePath+"/"+"csl"+"/"+BomWebPortalConstant.M2ServiceGuide_csl_chi);
					}
					
				}
				ArrayList<InputStream> ServiceGuide = new ArrayList<InputStream>(); 
				ServiceGuide.add(new FileInputStream(M2ServiceGuide));

				pdfStreamList.addAll(ServiceGuide);
			}
			else{
				InputStream pdfInStream = baos.getInputStream();
				pdfStreamList.add(pdfInStream);
			}
			
			if ("AF037".equals(mobAppForm.getAppFormId())){
				
				File UadTNG = new File ("");
				UadTNG =new File(mobPdfFilePath+"/"+iGuardUadTNG);
				ArrayList<InputStream> iGuardUadForm = new ArrayList<InputStream>(); 
				iGuardUadForm.add(new FileInputStream(UadTNG));

				pdfStreamList.addAll(iGuardUadForm);
			}
			
			// Travel Insurance TNC
			if (StringUtils.equalsIgnoreCase(mobAppForm.getAppFormId(), GenericReportHelper.TRAVEL_INSURANCE_FORM_FORM_ID)) {
				String travelInsuranceTnCPdf = "";
				List<ReportDTO> pRptDtoArrList = (List<ReportDTO>) mobAppForm.getpRptDtoArrList();
				if (!CollectionUtils.isEmpty(pRptDtoArrList)) {
					for (ReportDTO reportDto : pRptDtoArrList) {
						RptGenericFormTemplateDTO dto = (RptGenericFormTemplateDTO) reportDto;
						travelInsuranceTnCPdf = dto.getTravelTnC();
						break;
					}
				}
				File travelInsuranceTnCFile = new File(mobPdfFilePath + "/" + travelInsuranceTnCPdf);
				if (travelInsuranceTnCFile != null && travelInsuranceTnCFile.exists()) {
					ArrayList<InputStream> travelInsuranceTnC = new ArrayList<InputStream>(); 
					travelInsuranceTnC.add(new FileInputStream(travelInsuranceTnCFile));
					pdfStreamList.addAll(travelInsuranceTnC);
				}
			}
			
			// Helper Insurance TNC
			if (StringUtils.equalsIgnoreCase(mobAppForm.getAppFormId(), GenericReportHelper.HELPERCARE_INSURANCE_FORM_FORM_ID)) {
				File helperCareInsuranceTnCFile = new File(mobPdfFilePath + "/" + GenericReportHelper.HELPERCARE_INSURANCE_FORM_TNC_PDF);
				if (helperCareInsuranceTnCFile != null && helperCareInsuranceTnCFile.exists()) {
					ArrayList<InputStream> helperCareInsuranceTnC = new ArrayList<InputStream>(); 
					helperCareInsuranceTnC.add(new FileInputStream(helperCareInsuranceTnCFile));
					pdfStreamList.addAll(helperCareInsuranceTnC);
				}
			}			
			
			// Project Eagle TNC
			if (StringUtils.equalsIgnoreCase(mobAppForm.getAppFormId(), ProjectEagleReportHelper.FORM_ID)) {
			       File projectEagleTnCFile = new File(mobPdfFilePath + "/" + ProjectEagleReportHelper.TNC_PDF);
			       if (projectEagleTnCFile != null && projectEagleTnCFile.exists()) {
			             ArrayList<InputStream> projectEagleTnC = new ArrayList<InputStream>(); 
			             projectEagleTnC.add(new FileInputStream(projectEagleTnCFile));
			             pdfStreamList.addAll(projectEagleTnC);
			       }
			}
		}
		
		if (!"N".equalsIgnoreCase(watermark)) {
			PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, watermark, null, null, null, null);
		} else {
			PdfUtil.concatPDFs(pdfStreamList, baosMerged, true, false, null); // gen page no for each set of pdf
		}
		
		for (InputStream stream : pdfStreamList) {
			stream.close();
		}
		
		return baosMerged.getByteArray();
	}
	
	private void addRequiredFormList(List<MobAppFormDTO> requiredMobAppFormList,
			MobAppFormDTO mobAppForm, ReportDTO reportDTO, String locale,
			boolean hasChiVersion, String brand) {
		//create a temp MobAppFormDTO to prevent pointer update
		try {
			MobAppFormDTO tempMobAppForm = new MobAppFormDTO();
			BeanUtils.copyProperties(tempMobAppForm, mobAppForm);

			if (hasChiVersion && BomWebPortalConstant.RPT_LANG_ZH_HK.equals(locale)) {
				tempMobAppForm.setJasperTemplateName(tempMobAppForm.getJasperTemplateName() + "_zh");
			}
			
			if (tempMobAppForm.getAppFormId().equalsIgnoreCase("AF010")) {
				tempMobAppForm.setJasperTemplateName("ccs/" + tempMobAppForm.getJasperTemplateName());
			}
			
		
			if (reportDTO != null) {
				reportDTO.setLogo(locale, true, brand);
			}
			
			if (tempMobAppForm.getAppFormId().equalsIgnoreCase("AF032")) {
				tempMobAppForm.setJasperTemplateName("ccs/" + tempMobAppForm.getJasperTemplateName());
				reportDTO.setCompanyLogo(getImageFilePath() + TNG_TOP_LOGO);
				reportDTO.setCompanyBottomLeftLogo(getImageFilePath() + TNG_BOTTOM_LT_LOGO);
				reportDTO.setCompanyBottomLogo(getImageFilePath() + TNG_BOTTOM_RT_LOGO);
			}
			
			if (tempMobAppForm.getAppFormId().equalsIgnoreCase("AF050")) {
				tempMobAppForm.setJasperTemplateName(tempMobAppForm.getJasperTemplateName());
				/*reportDTO.setCompanyLogo(getImageFilePath() + TNG_TOP_LOGO);
				reportDTO.setCompanyBottomLeftLogo(getImageFilePath() + TNG_BOTTOM_LT_LOGO);
				reportDTO.setCompanyBottomLogo(getImageFilePath() + TNG_BOTTOM_RT_LOGO);*/
			}
			
			/*if (tempMobAppForm.getAppFormId().equalsIgnoreCase("AF005")) {
				tempMobAppForm.setJasperTemplateName(tempMobAppForm.getJasperTemplateName());
				RptHSTradeDescDTO hsTradeDesc = (RptHSTradeDescDTO)reportDTO;
				if (StringUtils.isNotEmpty(hsTradeDesc.getPisPath())){
					hsTradeDescForm.add(new FileInputStream(mobPdfFilePath+"/" +hsTradeDesc.getPisPath()));
					pdfStreamList.addAll(hsTradeDescForm);
				}else{
					pdfStreamList.add(generatePdfInputStream(
							keyValue.getJasperTemplateName(),
							keyValue.getRptDtoArrList(), isM2Ind, pLang, brand));
				}
			}*/

			ArrayList<ReportDTO> reportList = new ArrayList<ReportDTO>();
			reportList.add(reportDTO);
			tempMobAppForm.setpRptDtoArrList(reportList);

			requiredMobAppFormList.add(tempMobAppForm);
		} catch (Exception e) {
			logger.error("Exception caught in addRequiredFormList()", e);
		}
	}
	
	private boolean isGenerate(MobAppFormDTO mobAppForm, String channel, String copInd,
					String delInd, String dmsInd, String reasonCd) {
		boolean isPrint = true;
		
		if (mobAppForm.isMandatory()) {
			return true;
		}
		
		if (reasonCd != null && reasonCd.startsWith("N") && !"Y".equalsIgnoreCase(copInd)) {
			if ("AF022".equalsIgnoreCase(mobAppForm.getAppFormId())) {
				return true;
			} else if ("AF023".equalsIgnoreCase(mobAppForm.getAppFormId())) {
				return true;
			} else {
				return false;
			}
		}
		
		if ("Y".equalsIgnoreCase(copInd) && !mobAppForm.getCopInd().equalsIgnoreCase(copInd)) {
			return false;
		} else if ("Y".equalsIgnoreCase(delInd) && !mobAppForm.getDelInd().equalsIgnoreCase(delInd)) {
			return false;
		} else if ("Y".equalsIgnoreCase(dmsInd) && !mobAppForm.getDmsInd().equalsIgnoreCase(dmsInd)) {
			return false;
		}
		
		return isPrint;
	}
	
	private boolean needCompanyCopy(MobAppFormDTO mobAppForm, String companyCopyInd) {
		boolean isPrint = false;
		
		if ("Y".equalsIgnoreCase(companyCopyInd) && mobAppForm.getCompanyCopyInd().equalsIgnoreCase(companyCopyInd)) {
			return true;
		}
		
		return isPrint;
	}
	
	
	/*------------------- Retrieve Data -------------------*/
	public RptRetAppMobileServDTO retrieveRptRetAppMobileServSec(String orderId, String locale, String orderNature, 
			RptPrintIndDTO rptPrintIndDTO, OrderDTO orderDto,String brand) throws Exception {
		RptRetAppMobileServDTO rptRetAppMobileServDTO = new RptRetAppMobileServDTO();
		
		String basketId = orderService.findBasketId(orderId);
		String olInd = orderService.getOnlineInd(orderId);
		
		
		rptRetAppMobileServDTO=this.retrieveRptRetAppMobileServSecAtoC(orderId);
		rptRetAppMobileServDTO=this.retrieveRptRetAppMobileServSecAtoC(rptRetAppMobileServDTO,locale,orderId,basketId,olInd);
		rptRetAppMobileServDTO=this.retrieveAppFormSecECharges(rptRetAppMobileServDTO,locale,brand);
		rptRetAppMobileServDTO.setBillLang("N/A");
		rptRetAppMobileServDTO.setBillMedia("N/A");
		rptRetAppMobileServDTO.setAgreementNum(orderId);

		rptRetAppMobileServDTO.setHandsetDeviceAmount(Long.toString(orderService.getHandsetDeviceAmount(orderId)));
		rptRetAppMobileServDTO.setFirstMonthFee(Long.toString(orderService.getFirstMonthFee(orderId)));
		rptRetAppMobileServDTO.setFirstMonthServiceLicenceFee(Long.toString(orderService.getFirstMonthServiceLicenceFee(orderId)));
		
		AccountDTO accountDTO=orderService.getAccount(orderId);
		if (accountDTO!=null  && accountDTO.getBillPeriod()!=null){
			rptRetAppMobileServDTO.setBillPeriod(accountDTO.getBillPeriod());
		}
		PaymentDTO paymentDTO=orderService.getPayment(orderId);
		if (paymentDTO!=null  && paymentDTO.getPayMethodType()!=null){
			BeanUtils.copyProperties(rptRetAppMobileServDTO.getSectG(), paymentDTO);
		}
		
		rptRetAppMobileServDTO.setAppInDate(orderDto.getAppInDate());
		//MobileSimInfoDTO mobileSimInfoDTO=orderService.getSim(orderId);
		rptRetAppMobileServDTO.setSalesCd(orderDto.getSalesCd());
		rptRetAppMobileServDTO.setSalesName(orderDto.getSalesName());
		rptRetAppMobileServDTO.setShopCd(orderDto.getShopCode());
		BasketDTO basketDTO =this.getBasketAttributeCOS(basketId,Utility.date2String(orderDto.getAppInDate(), "dd/MM/yyyy"));
		rptRetAppMobileServDTO.setContractPeriod(basketDTO.getContractPeriod());
		BigDecimal depositTotal = this.getDepositAmountForOrder(orderId);
		if (depositTotal!=null){
			rptRetAppMobileServDTO.setDepositTotal(depositTotal.toString());
		} else {
			rptRetAppMobileServDTO.setDepositTotal("0");
		}
		
		if ("Y".equalsIgnoreCase(orderService.getConciergeInd(orderId))){
			rptRetAppMobileServDTO.setDocAttConci(true);
		}
		HSTradeDescDTO hSTradeDescDTOtemp = mobileDetailService.getHSTradeDesc(orderId);
		if (hSTradeDescDTOtemp != null) {
			rptRetAppMobileServDTO.setDocAttTradeDesc(true);
		}
		if (this.hasThirdPartyAutopayViaCreditCard(orderId)){
			rptRetAppMobileServDTO.setDocAttThirdParty(true);
		}
		if (rptPrintIndDTO.hasSecretarialService()){
			rptRetAppMobileServDTO.setDocAttSecSrv(true);
		}
		if (("CRP".equalsIgnoreCase(orderNature))//CRP nature
				&& ("R".equalsIgnoreCase(orderId.substring(0,1))||"D".equalsIgnoreCase(orderId.substring(0,1)))){
			rptRetAppMobileServDTO.setDocAttChgOfSrv (true);
		}
		List<AllOrdDocAssgnDTO> pfList= this.getRetAppDocCollected(orderId);
		if (pfList != null) {
			for (AllOrdDocAssgnDTO each : pfList) {
				switch (each.getDocType()) {
					case M002:
					{
						if (StringUtils.isEmpty(each.getWaiveReason())
								&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
							rptRetAppMobileServDTO.setPfHKIDPass(true);
						}
						break;
					}
					case M003:
					{
						if (StringUtils.isEmpty(each.getWaiveReason())
								&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
							rptRetAppMobileServDTO.setPfAddrPf(true);
						}
						break;
					}
					case M004:
					{
						if (StringUtils.isEmpty(each.getWaiveReason())
								&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
							rptRetAppMobileServDTO.setPfBRCopy(true);
						}
						break;
					}
					case M005:
					{
						if (StringUtils.isEmpty(each.getWaiveReason())
								&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
							rptRetAppMobileServDTO.setPfBRNameCard(true);
						}
						break;
					}
					case M006:
					{
						if (StringUtils.isEmpty(each.getWaiveReason())
								&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
							rptRetAppMobileServDTO.setPfEmpCont(true);
						}
						break;
					}
					case M007:
					{
						if (StringUtils.isEmpty(each.getWaiveReason())
								&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
							rptRetAppMobileServDTO.setPfPreparidSIM(true);
						}
						break;
					}
					case M008:
					{
						if (StringUtils.isEmpty(each.getWaiveReason())
								&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
							rptRetAppMobileServDTO.setPfCCCopy(true);
						}
						break;
					}
					case M009:
					{
						if (StringUtils.isEmpty(each.getWaiveReason())
								&& each.getCollectedInd() == AllOrdDocAssgnDTO.CollectedInd.Y) {
							rptRetAppMobileServDTO.setPfMNPDoc(true);
						}
						break;
					}
				default:
					break;
				}
			}
		}
		
		rptRetAppMobileServDTO.setSimType(orderService.getSimTypeByCosOrder(orderId));
		return rptRetAppMobileServDTO;
	}
	
	public RptAppMobileServDTO retrieveRptAppMobileDTO(String orderId, String locale, String orderNature, 
			RptPrintIndDTO rptPrintIndDTO, OrderDTO orderDto,String brand, List<MultiSimInfoDTO>multiSimInfoDTOs, String mdoInd) throws Exception {
		RptAppMobileServDTO rptAppMobileServDTO = new RptAppMobileServDTO();
		DisMode distMode = OrderDTO.DisMode.P;
		String billMediaString ="";
		String billMediaStringZh ="";	
		VasOnetimeAmtDTO simOnetimeCharge = null;
		final String RPT_LANG_ZH = "zh_HK";
		String basketId = orderService.findBasketId(orderId);
		/**** Copy customer Profile DTO to ACQ Application Form ****/
		CustomerProfileDTO customerInfoDto = customerProfileService.getCustomerProfile(orderId);
		AccountDTO accountDto = orderService.getAccount(orderId);
		customerInfoDto.setBillLang(accountDto.getBillLang());
		if (customerInfoDto.getSelectedBillMedia()!=null){
			List<MobBillMediaDTO> billMediaList  = LookupTableBean.getInstance().getBillMediaOption();
			
			for(MobBillMediaDTO mbm: billMediaList) {
				for (String billCd: customerInfoDto.getSelectedBillMedia()) {
					if (billCd.equals(mbm.getCodeId())) {
						billMediaString += "/ " +  mbm.getEngDesc();
						billMediaStringZh += "/ " +  mbm.getChiDesc();
					}
				}
			}
			
		}
		logger.info("Copy customerInfoDto");
		BeanUtils.copyProperties(rptAppMobileServDTO, customerInfoDto);
		/**** ServicePlanReportDTO ****/
		String[] basketbrandModel = orderService.getTOOBRMBasketBrandModelInfoList(orderId);
		List<VasDetailDTO> vasReportUseDetailList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasGifsReportUseDetailList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasOptionalReportUseList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasHSRPReportUseList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasHSRPList = new ArrayList<VasDetailDTO>();
		String selectedBasketId = null;
		String selectedBrandId = null;
		String selectedModelId = null;
		String selectedColorId = null;
		ServicePlanReportDTO servicePlanReport = new ServicePlanReportDTO();
		if (basketbrandModel!=null){
			selectedBasketId = basketbrandModel[0];
			selectedBrandId = basketbrandModel[1];
			selectedModelId = basketbrandModel[2];
			selectedColorId = basketbrandModel[3];
		}

		if (!"".equals(selectedBasketId) && selectedBasketId != null) {

			// HTML HS+VAS
			vasHSRPList = vasDetailService.getVasDetailSelectedList(locale,
					orderId);

			// REPORT use HS
			vasReportUseDetailList = vasDetailService.getReportUseRPHSRPList(
					locale, selectedBasketId, "SS_FORM_RP", orderId); // rate
																		// plan

			// REPORT use VAS
			vasHSRPReportUseList = vasDetailService.getReportUseVasDetailtList(
					locale, orderId, selectedBasketId);

			// REPORT use optional VAS
			vasOptionalReportUseList = vasDetailService
					.getReportUseVasOptionalDetailtList(locale, orderId,
							selectedBasketId);

			// REPORT use free Gifs VAS
			vasGifsReportUseDetailList = vasDetailService
					.getReportUseFreeGifsDetailtList(locale, orderId,
							selectedBasketId);

		}
		

		
		servicePlanReport.setMainServDtls(vasReportUseDetailList);
		servicePlanReport.setVasDtls(vasHSRPReportUseList);
		servicePlanReport.setVasFreeGifsDtls(vasGifsReportUseDetailList); 
		servicePlanReport.setVasOptionalDtls(vasOptionalReportUseList); 
		servicePlanReport.setHandsetDeviceAmount(Long.toString(orderService.getHandsetDeviceAmount(orderId)));
		servicePlanReport.setFirstMonthFee(Long.toString(orderService.getFirstMonthFee(orderId)));
		servicePlanReport.setFirstMonthServiceLicenceFee(Long.toString(orderService.getFirstMonthServiceLicenceFee(orderId))); 		
		List<VasOnetimeAmtDTO> vasOnetimeAmtList= orderService.getVasOnetimeAmtList(locale, orderId);
		int vasOnetimeAmtFee = 0;
		servicePlanReport.setVasOnetimeAmtList(vasOnetimeAmtList);
		if (vasOnetimeAmtList != null
				&& vasOnetimeAmtList.size() >= 0) {
			for (VasOnetimeAmtDTO dto: vasOnetimeAmtList) {
				vasOnetimeAmtFee += Integer.parseInt(dto.getVasOnetimeAmt());
			}
		}
		servicePlanReport.setVasOnetimeAmtFee("" + vasOnetimeAmtFee);
		
		servicePlanReport.setRebateList(orderService.getRebateList(locale,
				orderId));

		int billPeriod = Integer.parseInt(accountDto.getBillPeriod());
		servicePlanReport.setBillPeriod("" + (billPeriod - 1));// edit 20110621,
																// past to
																// report value
																// =
																// accountDto.getBillPeriod()-1

		servicePlanReport.setPenaltyinfo(orderService.getPenaltyInfoList(orderId));// [0]contract_period,
												// [1]penalty_type
												// [2]penalty_amt
		// System.out.println("selectedModelId:"+selectedModelId);
		if (!"".equals(selectedModelId) && selectedModelId != null) { 
			servicePlanReport.setHandsetDeviceInfo(orderService.getHandsetDeviceDescription(locale, selectedBrandId,selectedModelId, selectedColorId));	
			}
		servicePlanReport.setSecSrvContractPeriod(orderService.getSecSrvContractPeriod(orderId));

		// add 20110615, set setConciergeInd
		MnpDTO mnpDto = orderService.getMnp(orderId);
		if (mnpDto!=null){
			if (("".equals(mnpDto.getCutoverDateStr()) || mnpDto.getCutoverDateStr() == null)
					&& ("".equals(mnpDto.getServiceReqDateStr()) || mnpDto.getServiceReqDateStr() == null)) {
				logger.info("ConciergeInd=Y");
				servicePlanReport.setConciergeInd("Y");
	
			} else {
				logger.info("ConciergeInd=N");
				servicePlanReport.setConciergeInd("N");
			}
		}else{
			logger.info("ConciergeInd=N");
			servicePlanReport.setConciergeInd("N");
		}
		servicePlanReport.setConciergeInd(orderService.getConciergeInd(orderId));// edit
																		// 20110620
		logger.info("ConciergeInd:" + servicePlanReport.getConciergeInd());

		// add by Eliot 20110822
		servicePlanReport.setBasketType(orderService.getBasketType(orderId));// edit
																				// 20110822
		logger.info("BasketType:" + servicePlanReport.getBasketType());
		
		logger.info("Copy ServicePlanReportDTO");
		servicePlanReport.setLocale(locale);
		BeanUtils.copyProperties(rptAppMobileServDTO,servicePlanReport);
		
		//**** ServicePlanReportDTO END ****//*
		//**** PaymentDTO ****//*
		PaymentDTO paymentDto = orderService.getPayment(orderId);
		boolean isReqCreditCardAuthForm = false;
		if ("Y".equalsIgnoreCase(paymentDto.getThirdPartyInd())
				&& paymentDto.getCreditCardDocType() != null
				&& "C".equalsIgnoreCase(paymentDto.getPayMethodType())) {
			isReqCreditCardAuthForm = true;
		}
		
		logger.info("Copy paymentDto");
		BeanUtils.copyProperties(rptAppMobileServDTO.getSectG(), paymentDto);
		//**** PaymentDTO END ****//*
		
		//**** MNP DTO ****//*
	
		boolean isReqMnpForm = false;
		if (mnpDto!=null){
			isReqMnpForm = "MNP".equalsIgnoreCase(mnpDto.getMnpType()) ? true : false;
		}
		StringBuilder custReminder = new StringBuilder();
		StringBuilder custReminderChi = new StringBuilder();
		
		if (mnpDto!=null){
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
			} else { // MNP
				
				rptAppMobileServDTO.setCustReminder("");
				rptAppMobileServDTO.setCustReminderChi("");
			}
		}
		logger.info("Copy mnpDto");
		if (mnpDto !=null){
		
		BeanUtils.copyProperties(rptAppMobileServDTO, mnpDto);
		
		}else{
			// For TOO order since no insert data in BOMWEB_MNP
			rptAppMobileServDTO.setServiceReqDate(orderDto.getSrvReqDate());
		}
		//**** MNP DTO END****//
		
		//**** ORDER DTO ****//
		logger.info("Copy orderDto");
		BeanUtils.copyProperties(rptAppMobileServDTO, orderDto);
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
		//**** ORDER DTO ****//*
		
		//**** BOMSalesUSerDTO ****//*
		//BeanUtils.copyProperties(rptAppMobileServDTO, bomSalesUserDto);
		//**** BomSalesUserDTO END ****//*
		
		//**** MobileSimInfoDTO ****//*
		MobileSimInfoDTO mobileSimInfo = new MobileSimInfoDTO();
		if (orderNature.equals("T2N") || ((orderNature.equals("BNN") || orderNature.equals("B2N")) && "N".equals(mdoInd))){
			mobileSimInfo = orderService.getTooMobileSimInfo(orderId);
		}else{
		 mobileSimInfo = orderService.getSim(orderId);
		}
		if (mobileSimInfo!=null){
			if (mobileSimInfo.getSimCharge() > 0) {
				simOnetimeCharge = new VasOnetimeAmtDTO();
				if (RPT_LANG_ZH.equalsIgnoreCase(locale)) {
					simOnetimeCharge.setItemDesc("SIM卡費用");
				} else {
					simOnetimeCharge.setItemDesc("SIM Charge");
				}
				if (mobileSimInfo.getSimWaiveReason() != null && !"".equals(mobileSimInfo.getSimWaiveReason())) {
					simOnetimeCharge.setVasOnetimeAmt("0");
				} else {
					simOnetimeCharge.setVasOnetimeAmt("" + mobileSimInfo.getSimCharge());
				}
			}
		}
		logger.info("Copy mobileSimInfo");
		if (mobileSimInfo!=null){
			BeanUtils.copyProperties(rptAppMobileServDTO, mobileSimInfo);
		}
		//**** MobileSimInfoDTO END ****//*
		
		//****BasketDTO ****//*
		BasketDTO basketDTO = vasDetailService.getBasketAttribute(
				selectedBasketId, Utility.date2String(
						orderDto.getAppInDate(),
						BomWebPortalConstant.DATE_FORMAT));
		rptAppMobileServDTO.setContractPeriod(basketDTO.getContractPeriod());
		//****BasketDTO END ****//*
		
		//**** Support DOC UI ****//*
		SupportDocUI supportDocUI = new SupportDocUI();
		// TODO: setGenerateSpringForms
		String[] gSelectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
		List<GenerateSpringboardForm> generateSpringboardForms = new ArrayList<GenerateSpringboardForm>();
		String applicationDate = Utility.date2String(orderDto.getAppInDate(),"dd/MM/yyyy");
		OrderMobDTO orderMobDTO = orderService.getOrderMobDTO(orderId);
		for (SpringboardForm springboardForm : SpringboardForm.values()) {
			GenerateSpringboardForm generateSpringboardForm = new GenerateSpringboardForm();
			boolean required = false;
			switch (springboardForm) {
			case MOBILE_APPLICATION_FORM:
			case SERVICE_GUIDE:
				// By Default
				required = true;
				break;
			case TRADE_DESCRIPTIONS_FOR_ELECTIONIC_PRODUCTS: {
				// Device description defined in w_hs_trade_desc
				if (StringUtils.isNotBlank(selectedBasketId)) {
					required = this.mobileDetailService.isTradeDescExist(selectedBasketId, new Date());
					if(required==false){
						required =vasDetailService.hasProductionInfo(gSelectedItemList);//check VAS item
					}
				}
				break;
			}
			case PCCW_CONCIERGE_SERVICE_FORM: {
				// select Rate plan Category : Concierge
				basketDTO = vasDetailService.getBasketAttribute(
						selectedBasketId, Utility.date2String(
								orderDto.getAppInDate(),
								BomWebPortalConstant.DATE_FORMAT));
				required = "6".equals(basketDTO.getBasketTypeId());
				break;
			}
			case APPLICATION_IN_RESPECT_OF_MOBILE_SECRETARIAL_SERVICE: {
				// Basket offer inculde secretarial service
				String[] selectedItemList = (String[]) orderService
						.getSelectedItemList(orderId).toArray(new String[0]);
				if (selectedItemList != null) {
					for (String itemId : selectedItemList) {
						if (this.vasDetailService.isSecretarialItem(itemId)) {
							required = true;
							break;
						}
					}
				}
				break;
			}
			case MNP_APPLICATION_FORM: {
				// MNP in Mobile number
				// MnpDTO mnpDto = (MnpDTO)
				// request.getSession().getAttribute("MNP");
				if (mnpDto != null) {
					//For Direct Sales Only, 20131002
				/*	if (salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11){
						required = isMnp(mnpDto) || mnpDto.isFutherMnp();
					} else {
						required = isMnp(mnpDto);
					}*/
				}
				break;
			}
			case CHANGE_OF_SERVICE_FORM: {
				// MNP's owner doc ID different from customer or
				// New MRT + MNP
				// MnpDTO mnpDto = (MnpDTO)
				// request.getSession().getAttribute("MNP");
				if (mnpDto != null) {
					if (isMnp(mnpDto)) {
						if ("Y".equals(mnpDto.getMnp())) {
							if (StringUtils.isBlank(mnpDto.getCustIdDocNum())) {
								required = true;
							} else {
								// CustomerProfileDTO customerInfoDto =
								// (CustomerProfileDTO)
								// request.getSession().getAttribute("customer");
								if (changedCustomer(mnpDto, customerInfoDto)) {
									required = true;
								}
							}
						} else {
							required = true;
						}
					} else {
						// further MNP
						if (mnpDto.isFutherMnp()) {
							required = true;
						}
					}
				}
				break;
			}
			case THRID_PARTY_AUTOPAY_FORM: {
				// Payment method, choose Autopay with Third party
				// PaymentDTO paymentDto = (PaymentDTO)
				// request.getSession().getAttribute("payment");
				if (paymentDto != null) {
					if ("A".equals(paymentDto.getPayMethodType())) {
						if ("Y".equals(paymentDto.getThirdPartyInd())) {
							required = true;
						}
					}
				}
				break;
			}
			case IGUARD_FORM_LDS: {
				if (StringUtils.isNotBlank(orderDto.getiGuardSerialNo())) {
					required = true;
				}
			}
				break;
				
			case IGUARD_FORM_AD: {
				if (StringUtils.isNotBlank(orderDto.getiGuardSerialNo())) {
					required = true;
				}
			}
				break;
				
			case MOBILE_SAFETY_PHONE: {
				String[] selectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
				if (this.vasDetailService.isItemsInGroup(selectedBasketId, selectedItemList, Utility.string2Date(applicationDate), "100000103")) {
					required = true;
					orderDto.setMobileSafetyPhone(true);				
				}
				break;	
			}
			case NFC_SIM: {
				if (mobileSimInfo!=null){
					if (this.vasDetailService.isExtraFunctionSim(mobileSimInfo.getItemCd())) {
						required = true;
						//orderDto.setNfcSim(true);				
					}
				}
				break;
			}
			/*case OCTOPUS_SIM: {
				if (this.vasDetailService.hasOctopusSim(orderId)) {
					required = true;
					//orderDto.setOctopusSim(true);				
				}
				break;
			}*/
			}
			generateSpringboardForm.setSpringboardForm(springboardForm);
			generateSpringboardForm.setRequired(required);
			generateSpringboardForms.add(generateSpringboardForm);
		}
		supportDocUI.setGenerateSpringboardForms(generateSpringboardForms);
		supportDocUI.setCollectMethod(orderDto.getCollectMethod());
		supportDocUI.setAllOrdDocAssgnDTOs(supportDocService.getInUseAllOrdDocAssgnDTOALL(orderId));
		supportDocUI.setDisMode(orderDto.getDisMode());
		supportDocUI.setEsigEmailAddr(orderDto.getEsigEmailAddr());
		supportDocUI.setEsigEmailLang(orderDto.getEsigEmailLang());
		if (orderMobDTO !=null ){
			supportDocUI.setManualAfNo(orderMobDTO.getManualAfNo());
		}
		
		
		distMode = supportDocUI.getDisMode();
		
		List<GenerateSpringboardForm> docAttList
			= supportDocUI.getGenerateSpringboardForms();
		
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
			= supportDocUI.getAllOrdDocAssgnDTOs();
		
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
		//****Support DOC UI  ****//*
		//**** VasMrtSelectionDTO ****//*
		VasMrtSelectionDTO vasMrtSelectionDTO = orderService.getVasMrtSelectionDTO(orderId);
		VasMrtSelectionDTO ssMrtSelectionDTO = orderService.getSsMrtSelectionDTO(orderId);
		String[] selectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
		for (String itemId : selectedItemList) {
			if (itemFuncMobService.getItemFuncAssgnMobDTO(itemId, "EX06") != null) {
				if (vasMrtSelectionDTO!=null){
					vasMrtSelectionDTO.setVasItemId(itemId);
					vasMrtSelectionDTO.setFuncId("EX06");
				}
			}
			
			if (itemFuncMobService.getItemFuncAssgnMobDTO(itemId, "EX07") != null) {
				if (ssMrtSelectionDTO!=null){
					ssMrtSelectionDTO.setVasItemId(itemId);
					ssMrtSelectionDTO.setFuncId("EX07");
				}
			}
		}
		if (vasMrtSelectionDTO!=null){
			if ("EX06".equalsIgnoreCase(vasMrtSelectionDTO.getFuncId()) && "Y".equalsIgnoreCase(vasMrtSelectionDTO.getChinaInd())) {
				rptAppMobileServDTO.setUnicomMsisdn(vasMrtSelectionDTO.getMsisdn());
			} else if ("EX07".equalsIgnoreCase(vasMrtSelectionDTO.getFuncId())) {
				rptAppMobileServDTO.setSecSrvNum(vasMrtSelectionDTO.getSecSrvNum());
			}
		}
		/**** VasMrtSelectionDTO****/
		
		
		//MultiSim Info 
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			// MultiSim Athena 20140128
			List<RptMultiSimDTO> rptMultiSimDTOList = new ArrayList<RptMultiSimDTO>();
			// String locale="zh_HK";
			if ("en".equals(rptAppMobileServDTO.getLocale())) {
				locale = "en";
			}

			if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {

				for (MultiSimInfoDTO multiSimInfoDTO : multiSimInfoDTOs) {
					List<MultiSimInfoMemberDTO> multiSimInfoMemberDTOs = multiSimInfoDTO.getMembers();

					if (multiSimInfoMemberDTOs != null && multiSimInfoMemberDTOs.size() > 0) {
						for (MultiSimInfoMemberDTO multiSimInfoMemberDTO : multiSimInfoMemberDTOs) {
							RptMultiSimDTO rptMultiSimDTO = new RptMultiSimDTO();
							rptMultiSimDTO.setMemberNum(multiSimInfoMemberDTO.getMemberNum());
							rptMultiSimDTO.setMultiMsisdn(multiSimInfoMemberDTO.getMsisdn());
							SimDTO simDto = new SimDTO();
							String sim = new String();
							if (("MUPS03".equals(multiSimInfoMemberDTO.getMemberOrderType()) || "MUPS04".equals(multiSimInfoMemberDTO.getMemberOrderType())) && "Y".equals(multiSimInfoMemberDTO.getBrmChgSimInd())) {
								simDto = multiSimInfoService.getBrmChgSimInfo(multiSimInfoMemberDTO.getMemberOrderId());
								sim = simDto.getSimType();
								rptMultiSimDTO.setMultiSimIccid(simDto.getIccid());
							} else {
								sim = multiSimInfoMemberDTO.getSim().getItemCode();
								rptMultiSimDTO.setMultiSimIccid(multiSimInfoMemberDTO.getSim().getIccid());
							}
							List<RptVasDetailDTO> multiSimMainServDtls = vasDetailService
									.getReportUseMultiSimRPHSRPList(locale, multiSimInfoMemberDTO.getBasketId(),
											multiSimInfoMemberDTO.getParentOrderId(),
											multiSimInfoMemberDTO.getMemberNum());
							List<RptVasDetailDTO> multiSimvasDtls = vasDetailService.getReportUseMultiSimVasDetailtList(
									locale, multiSimInfoMemberDTO.getBasketId(),
									multiSimInfoMemberDTO.getParentOrderId(), multiSimInfoMemberDTO.getMemberNum());
							List<RptVasDetailDTO> multiSimvasOptionalDtls = vasDetailService
									.getReportUseMultiSimVasOptionalDetailtList(locale,
											multiSimInfoMemberDTO.getBasketId(),
											multiSimInfoMemberDTO.getParentOrderId(),
											multiSimInfoMemberDTO.getMemberNum());
							rptMultiSimDTO.setMultiSimMainServDtls(multiSimMainServDtls);
							rptMultiSimDTO.setMultiSimvasDtls(multiSimvasDtls);
							rptMultiSimDTO.setMultiSimvasOptionalDtls(multiSimvasOptionalDtls);
							
							try {
								rptMultiSimDTO.setMultiSimType(orderDao.getSimType(sim));
							} catch (DAOException e1) {
								logger.error("Exception caught in mapReportData", e1);
							}
							rptMultiSimDTOList.add(rptMultiSimDTO);
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
						}
					}
				}

				rptAppMobileServDTO.setMultiSimList(rptMultiSimDTOList);
			} else {

				rptAppMobileServDTO.setMultiSimList(null);
			}
		}
		if ("zh_HK".equals(rptAppMobileServDTO.getLocale())){
			rptAppMobileServDTO.setBillMedia("短訊賬單 (免費) "+billMediaStringZh);

		}else{
			rptAppMobileServDTO.setBillMedia("SMS Bill (Free)" +billMediaString);
		}
		
		//Part 5 total amount
				if (orderId != null){		
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
				
				
				// 20140522 Athena add mob cust notice csl start
				List<CodeLkupDTO> custNotice=codeLkupService.getCodeLkupDTOALL("MOB_CUST_NOTICE");
				if (custNotice!=null && custNotice.size()>=1){
					if (custNotice.get(0).getCodeId()!=null && "Y".equalsIgnoreCase(custNotice.get(0).getCodeId())){
						rptAppMobileServDTO.setMobCustNotice(true);
					}
				}
				
				// 20140522 Athena add mob cust notice csl end
		//		if (isConSrvForm)
		//			rptAppMobileServDTO.setIsConSrv("Y");
		//		else
		//			rptAppMobileServDTO.setIsConSrv("N");

				rptAppMobileServDTO.setCustomerCopyInd("N");
				
				/*if (simOnetimeCharge != null) {
					rptAppMobileServDTO.getVasOnetimeAmtList().add(simOnetimeCharge);
					double vasOnetimeAmtFee = Double.parseDouble(rptAppMobileServDTO.getVasOnetimeAmtFee());
					double simOnetimeAmtFee = Double.parseDouble(simOnetimeCharge.getVasOnetimeAmt());
					rptAppMobileServDTO.setVasOnetimeAmtFee("" + (vasOnetimeAmtFee + simOnetimeAmtFee));
				}*/
			
		
			//	rptAppMobileServDTO.setSalesName(salesName);
		//		rptAppMobileServDTO.setSalesCd(salesCode);
				
				if (StringUtils.isNotEmpty(rptAppMobileServDTO.getShopCd())) {
					if (rptAppMobileServDTO.getShopCd().length() == 3) {
						rptAppMobileServDTO.setShopCd("P" + rptAppMobileServDTO.getShopCd());
					}
				}
				
		return rptAppMobileServDTO;
	}
	private boolean isMnp(MnpDTO mnpDto) {
		return "Y".equals(mnpDto.getMnp()) || "A".equals(mnpDto.getMnp());
	}
	private boolean changedCustomer(MnpDTO mnpDto,
			CustomerProfileDTO customerInfoDto) {
		if (mnpDto == null) {
			return false;
		}
		if (customerInfoDto == null) {
			return false;
		}
		if ("Prepaid Sim".equals(mnpDto.getCustName())) {
			return false;
		}
		if (!"HKID".equals(customerInfoDto.getIdDocType())) {
			return false;
		}
		if (StringUtils.isBlank(mnpDto.getCustIdDocNum())
				|| StringUtils.isBlank(customerInfoDto.getIdDocNum())) {
			return false;
		}
		if("Y".equals(mnpDto.getMnp())) {
			if (!mnpDto.getCustIdDocNum().equals(customerInfoDto.getIdDocNum())) {
				return true;
			}
		} else if (mnpDto.isFutherMnp()) {
			if (!mnpDto.getFuthercustIdDocNum().equals(customerInfoDto.getIdDocNum())) {
				return true;
			}
		}
		return false;
	}
	

	
	public RptRetAppMobileServDTO retrieveRptRetAppMobileServSecAtoC(String orderId) {
		try {
			RptRetAppMobileServDTO rptRetAppMobileServ = mobCosRptDAO.retrieveRptRetAppMobileServSecAtoC(orderId);
			return rptRetAppMobileServ;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptRetAppMobileServSecAtoC(String orderId)", e);
		}
		return null;
	}

	public RptRetAppMobileServDTO retrieveRptRetAppMobileServSecAtoC(
			RptRetAppMobileServDTO dto, String locale, String orderId,
			String selectedBasketId, String olInd) {

		List<VasDetailDTO> vasReportUseDetailList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasHSRPReportUseList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasGifsReportUseDetailList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasOptionalReportUseList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> rebateReportUseList = new ArrayList<VasDetailDTO>();
		List<VasOnetimeAmtDTO> vasOnetimeAmtList = new ArrayList<VasOnetimeAmtDTO>();
		List<VasOnetimeAmtDTO> chgSimAdmCharge = new ArrayList<VasOnetimeAmtDTO>();
		List<VasOnetimeAmtDTO> too1AdminCharge = new ArrayList<VasOnetimeAmtDTO>();
		int vasOnetimeAmtFee = 0;

		if (!"".equals(selectedBasketId) && selectedBasketId != null) {

			vasReportUseDetailList = vasDetailService.getReportUseRPHSRPList(locale, selectedBasketId, "SS_FORM_RP", orderId);
			if ("Y".equalsIgnoreCase(olInd) && !CollectionUtils.isEmpty(vasReportUseDetailList)){
				List<VasDetailDTO>vasOnlineReportUseDetailList=vasDetailService.getOnlineReportUseRPHSRPList(orderId);
				if ("en".equalsIgnoreCase(locale) && !CollectionUtils.isEmpty(vasOnlineReportUseDetailList)){
					vasReportUseDetailList.get(0).setItemHtml(vasOnlineReportUseDetailList.get(0).getItemHtml());
				}else{
					vasReportUseDetailList.get(0).setItemHtml(vasOnlineReportUseDetailList.get(0).getItemHtml2());
				}
				
				
			}
			vasHSRPReportUseList = vasDetailService.getReportUseVasDetailtList(locale, orderId, selectedBasketId);
			// REPORT use free Gifs VAS
			vasGifsReportUseDetailList = vasDetailService.getReportUseFreeGifsDetailtList(locale, orderId, selectedBasketId);
			// REPORT use optional VAS
			vasOptionalReportUseList = vasDetailService.getReportUseVasOptionalDetailtList(locale, orderId, selectedBasketId);
			rebateReportUseList = orderService.getRebateList(locale, orderId);
			vasOnetimeAmtList = orderService.getVasOnetimeAmtList(locale, orderId);
			chgSimAdmCharge = orderService.getRetChgSimAdmChargeList(locale, orderId);
			vasOnetimeAmtList.addAll(chgSimAdmCharge);
			too1AdminCharge = orderService.getRetToo1AdmChargeList(locale, orderId);
			System.out.println("too1AdminCharge = " + locale + "," + orderId);
			System.out.println("too1AdminCharge = " + !CollectionUtils.isEmpty(too1AdminCharge));
			vasOnetimeAmtList.addAll(too1AdminCharge);
			System.out.println("vasOnetimeAmtList = " + vasOnetimeAmtList.size());
			if (vasOnetimeAmtList != null && vasOnetimeAmtList.size() >= 0) {
				for (VasOnetimeAmtDTO temp : vasOnetimeAmtList) {
					vasOnetimeAmtFee += Integer.parseInt(temp.getVasOnetimeAmt());
				}
			};
		}
		
		List<DepositDTO> depositDTOList = depositService.findDepositDTOByOrderId(orderId, locale);
		for (Iterator<?> i = depositDTOList.iterator(); i.hasNext(); ) {
			if ("Y".equals(((DepositDTO)i.next()).getWaiveInd())) {
				i.remove();
			}
		}

		if (dto != null) {
			dto.setLocale(locale);
			dto.setMainServDtls((ArrayList) vasReportUseDetailList);
			dto.setVasFreeGifsDtls((ArrayList) vasGifsReportUseDetailList);
			dto.setVasOptionalDtls((ArrayList) vasOptionalReportUseList);
			dto.setVasDtls((ArrayList) vasHSRPReportUseList);
			dto.setRebateList((ArrayList) rebateReportUseList);
			dto.setHandsetDeviceDescription(mobileDetailService.getSSFormDesc(orderId));
			dto.setVasOnetimeAmtList((ArrayList) vasOnetimeAmtList);
			dto.setVasOnetimeAmtFee("" + vasOnetimeAmtFee);
			if (depositDTOList != null && !depositDTOList.isEmpty()){
				dto.setDepositList(depositDTOList);
			}
		}

		return dto;
	}

	public RptRetAppMobileServDTO retrieveAppFormSecECharges(RptRetAppMobileServDTO dto, String locale,String brand) {
		ArrayList<AdditionalChargeDTO> aChargeList = new ArrayList<AdditionalChargeDTO>();
		ArrayList<MiscellaneousChargeDTO> mChargeList = new ArrayList<MiscellaneousChargeDTO>();
		String configFiles = "reportConstantM2.xml";

		ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFiles);
		if (BomWebPortalConstant.RPT_LANG_EN.equals(locale)) {
/*			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng1"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng2"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("retAdditionalChargeEng3"));*/
			//aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng4"));
			//aChargeList.add((AdditionalChargeDTO) appCtx.getBean("retAdditionalChargeEng5"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng6"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("retAdditionalChargeEng7"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("retAdditionalChargeEng8"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeEng9"));

			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng1"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng2"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng3"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng4"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng5"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng6"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("retMiscellaneousChargeEng7"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng8"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng9"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeEng10"));
			dto.setPersonInfoCollectState1(appCtx.getBean("retssPersonInfoCollectStateEng1").toString());
			//dto.setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateEng2").toString());
			if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
				dto.setCustAgree(appCtx.getBean("ret1010ssCustAgreeEng").toString());
			} else {
				dto.setCustAgree(appCtx.getBean("retssCustAgreeEng").toString());
			}
		} else if (BomWebPortalConstant.RPT_LANG_ZH_HK.equals(locale)) {
/*			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi1"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi2"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("retAdditionalChargeChi3"));*/
			//aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi4"));
			//aChargeList.add((AdditionalChargeDTO) appCtx.getBean("retAdditionalChargeChi5"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi6"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("retAdditionalChargeChi7"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("retAdditionalChargeChi8"));
			aChargeList.add((AdditionalChargeDTO) appCtx.getBean("additionalChargeChi9"));

			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi1"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("retMiscellaneousChargeChi2"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi3"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi4"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("miscellaneousChargeChi5"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("retMiscellaneousChargeChi6"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("retMiscellaneousChargeChi7"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("retMiscellaneousChargeChi8"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("retMiscellaneousChargeChi9"));
			mChargeList.add((MiscellaneousChargeDTO) appCtx.getBean("retMiscellaneousChargeChi10"));
			dto.setPersonInfoCollectState1(appCtx.getBean("retssPersonInfoCollectStateChi1").toString());
			//dto.setPersonInfoCollectState2(appCtx.getBean("ssPersonInfoCollectStateChi2").toString());

			if (BomWebPortalConstant.BRAND_1010.equalsIgnoreCase(brand)) {
				dto.setCustAgree(appCtx.getBean("ret1010ssCustAgreeChi").toString());
			} else {
				dto.setCustAgree(appCtx.getBean("retssCustAgreeChi").toString());
			}
		}
		dto.setMiscellaneousChargeDtls(mChargeList);
		dto.setAdditionalChargeDtls(aChargeList);

		return dto;
	}

	public List<RptHSTradeDescDTO> retrieveRptHSTradeDescDTO(String orderId) {
		List<RptHSTradeDescDTO> rptHSTradeDescDTOList = new ArrayList<RptHSTradeDescDTO>();
		RptHSTradeDescDTO rptHSTradeDescDTO = null;
		//HSTradeDescDTO hSTradeDescDTO = mobileDetailService.getHSTradeDesc(orderId);
		List<HSTradeDescDTO> hSTradeDescDTOList = new ArrayList<HSTradeDescDTO>();
		try {
			hSTradeDescDTOList = hsTradeDescDAO.getHSTradeDescList(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			logger.info("exception caught in retrieveRptHSTradeDescDTO");
		}
		if (!CollectionUtils.isEmpty(hSTradeDescDTOList)){
			for (HSTradeDescDTO hSTradeDescDTO:hSTradeDescDTOList){
					rptHSTradeDescDTO = new RptHSTradeDescDTO();
					rptHSTradeDescDTO.setAgreementNum(orderId);
					rptHSTradeDescDTO.setBrand(hSTradeDescDTO.getBrand());
					rptHSTradeDescDTO.setModel(hSTradeDescDTO.getModel());
					rptHSTradeDescDTO.setPlaceOfManufacture(hSTradeDescDTO.getPlaceOfManufacture());
					rptHSTradeDescDTO.setNetworkFrequency(hSTradeDescDTO.getNetworkFrequency());
					rptHSTradeDescDTO.setKeyFeatures(hSTradeDescDTO.getKeyFeatures());
					rptHSTradeDescDTO.setOperatingSystem(hSTradeDescDTO.getOperatingSystem());
					rptHSTradeDescDTO.setInternalMemory(hSTradeDescDTO.getInternalMemory());
					rptHSTradeDescDTO.setStorageType(hSTradeDescDTO.getStorageType());
					rptHSTradeDescDTO.setStorageCapacity(hSTradeDescDTO.getStorageCapacity());
					rptHSTradeDescDTO.setDisplay(hSTradeDescDTO.getDisplay());
					rptHSTradeDescDTO.setNetworkProtocol(hSTradeDescDTO.getNetworkProtocol());
					rptHSTradeDescDTO.setCameraResolution(hSTradeDescDTO.getCameraResolution());
					rptHSTradeDescDTO.setPackagingList(hSTradeDescDTO.getPackagingList());
					rptHSTradeDescDTO.setPrice(hSTradeDescDTO.getPrice());
					rptHSTradeDescDTO.setAdditionalDeliveryCharge(hSTradeDescDTO.getAdditionalDeliveryCharge());
					rptHSTradeDescDTO.setRepairSrvProvider(hSTradeDescDTO.getRepairSrvProvider());
					rptHSTradeDescDTO.setRepairSrvAddress(hSTradeDescDTO.getRepairSrvAddress());
					rptHSTradeDescDTO.setExchangePolicy(hSTradeDescDTO.getExchangePolicy());
					rptHSTradeDescDTO.setWarrantyHandset(hSTradeDescDTO.getWarrantyHandset());
					rptHSTradeDescDTO.setWarrantyAccessory(hSTradeDescDTO.getWarrantyAccessory());
					rptHSTradeDescDTO.setPisPath(hSTradeDescDTO.getPisPath());
					//rptHSTradeDescDTO.setCompanyChop(getImageFilePath() + COMPANY_CHOP_FILE);
					rptHSTradeDescDTO.setShowSignAndDate(false);
					rptHSTradeDescDTOList.add(rptHSTradeDescDTO);
			}
		}
		return rptHSTradeDescDTOList;
	}

	public RptCreditCardDDAuthDTO retrieveRptCreditCardDDAuthDTO(String orderId) {
		try {
			RptCreditCardDDAuthDTO rptCreditCardDDAuthDTO = mobCosRptDAO.getRptCreditCardDDAuthDTO(orderId);
			rptCreditCardDDAuthDTO.setCreditCardDocNum(maskedDocNum(rptCreditCardDDAuthDTO.getCreditCardDocNum()));
			if (rptCreditCardDDAuthDTO != null) {
				String configFile = "reportConstantM2.xml";
				ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFile);
				
				rptCreditCardDDAuthDTO.setCcos7(appCtx.getBean("ccOSState7").toString());
				rptCreditCardDDAuthDTO.setCcos8(appCtx.getBean("ccOSState8").toString());
			}
			
			return rptCreditCardDDAuthDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptCreditCardDDAuthDTO()", e);
		}
		return null;
	}
	
	public RptKeyInformationSheetDTO retrieveRptKeyInformationSheetDTO(String orderId) {
		try {
			RptKeyInformationSheetDTO rptKeyInformationSheetDTO = mobCosRptDAO.retrieveRptKeyInformationSheetDTO(orderId);
			return rptKeyInformationSheetDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptKeyInformationSheetDTO()", e);
		}
		return null;
	}
	
	public RptRetConfirmForCopDTO retrieveRptRetConfirmForCopDTO(String orderId) {
		try {
			RptRetConfirmForCopDTO rptRetConfirmForCopDTO = mobCosRptDAO.retrieveRptRetConfirmForCopDTO(orderId);
			MobCosCopDTO mobCosCopDTO =mobCosCopService.getCosCopProcessOrderDetail(orderId);
			if (mobCosCopDTO!=null){
				BeanUtils.copyProperties(rptRetConfirmForCopDTO, mobCosCopDTO);
			}
			return rptRetConfirmForCopDTO;
		} catch (Exception e) {
			logger.error("Exception caught in retrieveRptRetConfirmForCopDTO(String orderId)", e);
		}
		return null;
	}

	public RptSecretarialServDTO retrieveRptSecretarialServDTO(String orderId) {
		try {
			RptSecretarialServDTO rptSecretarialServDTO = mobCosRptDAO.retrieveRptSecretarialServDTO(orderId);
			if (rptSecretarialServDTO != null) {
				rptSecretarialServDTO.setSecSrvContractPeriod(orderService.getSecSrvContractPeriod(orderId));
			}
			return rptSecretarialServDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptSecretarialServDTO()", e);
		}
		return null;
	}
	
	public RptNFCConsentDTO retrieveRptNFCConsentDTO(String orderId) {
		try {
			RptNFCConsentDTO rptNFCConsentDTO = mobCosRptDAO.retrieveRptNFCConsentDTO(orderId);
			return rptNFCConsentDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptNFCConsentDTO()", e);
		}
		return null;
	}
	
	public RptOctopusConsentDTO retrieveRptOctopusConsentDTO(String orderId, String locale) {
		try {
			RptOctopusConsentDTO rptOctopusConsentDTO = mobCosRptDAO.retrieveRptOctopusConsentDTO(orderId);
			if (rptOctopusConsentDTO != null) {
				if (BomWebPortalConstant.RPT_LANG_ZH_HK.equalsIgnoreCase(locale)){
					rptOctopusConsentDTO.setOctFormP1("CHI");
				} else {
					rptOctopusConsentDTO.setOctFormP1("ENG");
				}
			}
			return rptOctopusConsentDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptOctopusConsentDTO()", e);
		}
		return null;
	}
	
	
	public IGuardDTO retrieveIGuardDTOCustomerInfo(String orderId, String channel, OrderDTO orderDto,String srvPlanType ) {
		try {
			IGuardDTO iGuardDTO = mobCosRptDAO.retrieveIGuardDTOCustomerInfo(orderId, channel);
			if (iGuardDTO != null) {
				iGuardDTO.setDirectLogo(getImageFilePath() + IGUARD_DIRECT_LOGO_FILE);
				iGuardDTO.setPhoneProtectorLogo(getImageFilePath() + IGUARD_PHONEPROTECTOR_LOGO_FILE);
				
				CustomerProfileDTO customerInfoDto = customerProfileService.getCustomerProfile(orderId);
				if (customerInfoDto != null) {
					iGuardDTO.setEmail(StringUtils.isBlank(customerInfoDto.getEmailAddr()) ? iGuardDTO.getEmail(): customerInfoDto.getEmailAddr());
					iGuardDTO.setOtherContactNo(StringUtils.isBlank(customerInfoDto.getOtherContactPhone()) ? "" : customerInfoDto.getOtherContactPhone());
					iGuardDTO.setDob(StringUtils.isBlank(customerInfoDto.getDobStr()) ? "" : customerInfoDto.getDobStr());
					iGuardDTO.setFlat(StringUtils.isBlank(customerInfoDto.getFlat())?  "" : customerInfoDto.getFlat().toUpperCase());
					iGuardDTO.setFloor(StringUtils.isBlank(customerInfoDto.getFloor())?  "" : customerInfoDto.getFloor().toUpperCase());
					iGuardDTO.setBuilding(StringUtils.isBlank(customerInfoDto.getBuildingName())?  "" : customerInfoDto.getBuildingName().toUpperCase());
					iGuardDTO.setStreet((StringUtils.isBlank(customerInfoDto.getLotNum())?  "" : customerInfoDto.getLotNum().toUpperCase())
							+ (StringUtils.isBlank(customerInfoDto.getStreetName())?  "" : customerInfoDto.getStreetName().toUpperCase())
							+ (StringUtils.isBlank(customerInfoDto.getStreetCatgDesc())?  "" : customerInfoDto.getStreetCatgDesc().toUpperCase()));
					iGuardDTO.setSection(StringUtils.isBlank(customerInfoDto.getSectionDesc())?  "" : customerInfoDto.getSectionDesc().toUpperCase());
					iGuardDTO.setDistrict(StringUtils.isBlank(customerInfoDto.getDistrictDesc())?  "" : customerInfoDto.getDistrictDesc().toUpperCase());
					iGuardDTO.setRegion(StringUtils.isBlank(customerInfoDto.getAreaDesc())?  "" : customerInfoDto.getAreaDesc().toUpperCase());
				}
				
				String basketId = orderService.findBasketId(orderId);
				BasketDTO basketDto = this.getBasketAttributeCOS(basketId,
						Utility.date2String(orderDto.getAppInDate(),
								BomWebPortalConstant.DATE_FORMAT));
				
				String itemCd = basketDto.getHsPosItemCd();
				int contractPeriod = Integer.valueOf(basketDto.getContractPeriod());
				BigDecimal hsPrice = iGuardService.getNsPrice(itemCd, orderDto.getAppInDate());
				
				if (hsPrice != null) {
					iGuardDTO.setHsPurchasePrice(hsPrice.doubleValue() + "");
					
					String servicePlan = iGuardService.getIGuardPlan(hsPrice, contractPeriod,srvPlanType, orderDto.getAppInDate());
					if (servicePlan != null) {
						if("LDS".equalsIgnoreCase(srvPlanType)){
							iGuardDTO.setiGuardLDSPlanCode(servicePlan);
							String servicePrice = iGuardService.getIGuardPlanPrice(servicePlan);
							String servicePlanDesc = servicePlan + " $" + servicePrice;
							iGuardDTO.setLdsSrvPlanFee(servicePlanDesc.toUpperCase());
						}else if("AD".equalsIgnoreCase(srvPlanType)){
							iGuardDTO.setiGuardADPlanCode(servicePlan);
							String servicePrice = iGuardService.getIGuardPlanPrice(servicePlan);
							String servicePlanDesc = servicePlan + " $" + servicePrice;
							iGuardDTO.setAdSrvPlanFee(servicePlanDesc.toUpperCase());
						}
						iGuardDTO.setHandsetDeviceDescription((basketDto.getBrandDesc()+" "+basketDto.getModelDesc()+" "+basketDto.getColorDesc()).toUpperCase());
						iGuardDTO.setContractPeriod(basketDto.getContractPeriod());
						
						List<ComponentDTO> componentList = this.orderService.getComponentList(orderId);
						iGuardDTO.setPrivacyInd10011("Y".equals(this.getAttbValue(componentList, "10011")));
						iGuardDTO.setPrivacyInd10012("Y".equals(this.getAttbValue(componentList, "10012")));
					}
				}
			}
			return iGuardDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveIGuardDTOCustomerInfo(String orderId, IGuardDTO iGuardDTO)", e);
		}
		return null;
	}
	
	public RptConciergeServiPadiPhoneDTO retrieveRptConciergeServiPadiPhoneDTO(String orderId, String locale) {
		try {
			RptConciergeServiPadiPhoneDTO rptConciergeServiPadiPhoneDTO = mobCosRptDAO.retrieveRptConciergeServiPadiPhoneDTO(orderId, locale);
			if (rptConciergeServiPadiPhoneDTO != null) {
				rptConciergeServiPadiPhoneDTO.setHandsetDeviceAmount(Long.toString(orderService.getHandsetDeviceAmount(orderId)));

				String configFiles = "";
				boolean isNEForm = false;

				if (isNEForm) {
					configFiles = "NEreportConstant.xml";
				} else {
					configFiles = "reportConstant.xml";
				}
				ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFiles);
				
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
			}
			return rptConciergeServiPadiPhoneDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptConciergeServiPadiPhoneDTO()", e);
		}
		return null;
	}
	
	public RptRetCosDTO retrieveCosCustDetailDTO(String orderId, String locale, OrderDTO orderDto) {
		try {
			RptRetCosDTO rptRetCosDTO = mobCosRptDAO.retrieveCosCustDetailDTO(orderId);
			if (rptRetCosDTO != null) {
				rptRetCosDTO.setStaffcodeName(orderDto.getSalesCd()+ " / " + orderDto.getSalesName());
				List<VasDetailDTO> rpDesc= new ArrayList<VasDetailDTO>();
				String basketId = orderService.findBasketId(orderId);
				rpDesc=retrieveCosRPDetail(orderId,basketId,locale);
				if (rpDesc!=null && !rpDesc.isEmpty()){
					String mthRate=rpDesc.get(0).getItemRecurrentAmt();
					rptRetCosDTO.setMthRate(mthRate);
					rptRetCosDTO.setRpDescList((ArrayList)rpDesc);
				}
			}
			return rptRetCosDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveCosCustDetailDTO(String orderId)", e);
		} 
		return null;
	}
	
	public List<VasDetailDTO> retrieveCosRPDetail(String orderId,String basketId,String locale) {
		try {
			List<VasDetailDTO> rpDetailList = new ArrayList<VasDetailDTO>();
			rpDetailList = mobCosRptDAO.retrieveCosRPDetail(orderId,basketId,locale);
			return rpDetailList;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveCosRPDetail(String orderId)", e);
		} 
		return null;
	}
	
	public RptRetCourierDeliveryGuidelineDTO retrieveRptRetCourierDeliveryGuidelineDTO(String orderId, RptPrintIndDTO rptPrintIndDTO, String locale) {
		try {
			RptRetCourierDeliveryGuidelineDTO rptRetCourierDeliveryGuidelineDTO = mobCosRptDAO.retrieveRptRetCourierDeliveryGuidelineDTO(orderId);
			if (rptRetCourierDeliveryGuidelineDTO != null) {
				
				if(rptPrintIndDTO.isiGuardAD()){
					rptRetCourierDeliveryGuidelineDTO.setiGuardFormAD(true);
				}else if(rptPrintIndDTO.isiGuardLDS()){
					rptRetCourierDeliveryGuidelineDTO.setiGuardFormAD(true);
				}
				
				rptRetCourierDeliveryGuidelineDTO.setTradeInHSForm(rptPrintIndDTO.hasHandsetTradeIn());
				rptRetCourierDeliveryGuidelineDTO.setThirdPartyCreditCardAuthorization(this.hasThirdPartyAutopayViaCreditCard(orderId));
				List<MobCcsSupportDocDTO> allSupportDocList = this.getAllSupportDocList(orderId);
				if (allSupportDocList != null) {
					for (MobCcsSupportDocDTO dto : allSupportDocList) {
						if ("1".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setIdDocCopy(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						} else if ("2".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setAddressProof(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						} else if ("5".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setThirdPartyCreditCardHolderIDDocumentCopy(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						} else if ("16".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setTwoNRegisterHKIDCopy(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setOwnershipFormInd(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						} else if ("9".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setStudentCardCopy(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						} else if ("13".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setConciergeServiceAuthorizationLetter(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						} else if ("11".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setValidForeignDomesticHelperContractCopy(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						} else if ("10".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setChangeOfServiceForm(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						} else if ("12".equals(dto.getDocId())) {
							rptRetCourierDeliveryGuidelineDTO.setPrepaidSIMcopy(!dto.isReceivedByFax());
							rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(!dto.isReceivedByFax());
						}
					}
				}
				
				String[] creditCardAutoPay = this.getCreditCardAutoPayNumOfSign(orderId);
				if (creditCardAutoPay != null) {
					rptRetCourierDeliveryGuidelineDTO.setBankNameChi(creditCardAutoPay[0]);
					rptRetCourierDeliveryGuidelineDTO.setNoOfSign(creditCardAutoPay[1]);
				}
				
				rptRetCourierDeliveryGuidelineDTO.setiGuardForm(rptPrintIndDTO.hasIGuard());
				rptRetCourierDeliveryGuidelineDTO.setMobileSafetyForm(rptPrintIndDTO.hasMobileSafePhoneSafety());
				
				rptRetCourierDeliveryGuidelineDTO.setOctFlag(orderService.getOctFlag() ? "Y" : "N");
				String simType = orderService.getSimTypeByCosOrder(orderId);
				if (StringUtils.isNotBlank(simType) && !"0".equals(simType)) {
					rptRetCourierDeliveryGuidelineDTO.setHasNFCSimForm(true);
				}
				/*if (this.hasOctopusSim(orderId)) {
					rptRetCourierDeliveryGuidelineDTO.setHasOctopusSimForm(true);
				} else if (this.hasNFCSim(orderId)) {
					rptRetCourierDeliveryGuidelineDTO.setHasNFCSimForm(true);
				}*/
				
				
				//Dennis 20151125
				boolean tngDummyVas = orderService.hasTNGServiceDummyVas(orderId);
				rptRetCourierDeliveryGuidelineDTO.setTngServiceFlag(tngDummyVas);				
				if (StringUtils.isNotEmpty(rptRetCourierDeliveryGuidelineDTO.getCustIdDocNum()) && StringUtils.isNotEmpty(rptRetCourierDeliveryGuidelineDTO.getCustIdDocType())){
					if (tngDummyVas && rptRetCourierDeliveryGuidelineDTO.getCustIdDocNum().startsWith("J") && "HKID".equalsIgnoreCase(rptRetCourierDeliveryGuidelineDTO.getCustIdDocType())){
						rptRetCourierDeliveryGuidelineDTO.setjPrefixTngServiceInd(true);		
						rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(true);
					}
				}
				
				PaymentUpFrontUI paymentUpFrontUI = this.getPaymentUpfront(orderId);
				if (paymentUpFrontUI != null) {
					if ("M".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType())) {
						rptRetCourierDeliveryGuidelineDTO.setCashInd(true);
						Double depositTotal= (Double)this.getDepositAmountForOrder(orderId).doubleValue();
						Double hsPayment=(Double)this.getHSPayment(orderId).doubleValue();
						Double prePayment=(Double)this.getPrePayment(orderId).doubleValue();			
						Double adminCharge=this.getAdminCharge(orderId, locale);
						if (hsPayment==null){
							hsPayment=0.0;
						}
						if (prePayment==null){
							prePayment=0.0;
						}
						if (adminCharge==null){
							adminCharge=0.0;
						}
						if (depositTotal==null){
							depositTotal=0.0;
						}
						
						Double cash = hsPayment + prePayment + adminCharge + depositTotal;
						rptRetCourierDeliveryGuidelineDTO.setCash(cash);
						rptRetCourierDeliveryGuidelineDTO.setReceiveCopy(true);
					}else if ("C".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType()) || "I".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType())) {
						rptRetCourierDeliveryGuidelineDTO.setUpFrontPaymentByCreditCard(true);
					}
				}
				String[] authLetterId = this.retrieveAuthorizationLetteIDInfo(orderId);
				String authLetterIdName="";
				if (authLetterId != null) {
					if (authLetterId[0]!=null)
						authLetterIdName += authLetterId[0]+" ";
					if (authLetterId[1]!=null)
						authLetterIdName += authLetterId[1];
					if (authLetterIdName!=null&&authLetterIdName!="")
						rptRetCourierDeliveryGuidelineDTO.setAuthorizationLetterName(authLetterIdName);
					if (authLetterId[2]!=null)
						rptRetCourierDeliveryGuidelineDTO.setAuthorizationLetterContractNum(authLetterId[2]);
					rptRetCourierDeliveryGuidelineDTO.setAuthorizationLetterAndIDCollection(true);
				}

				RptRetCourierDeliveryGuidelineDTO bsContactDTO =this.getBSContact(orderId);
				
				if (bsContactDTO!=null){
					if (bsContactDTO.getBsDeliveryContactName()!=null){
						rptRetCourierDeliveryGuidelineDTO.setBsDeliveryContactName(bsContactDTO.getBsDeliveryContactName());
					}
					rptRetCourierDeliveryGuidelineDTO.setBrCust(true);
						
				}
				
			}
			return rptRetCourierDeliveryGuidelineDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptRetCourierDeliveryGuidelineDTO(String orderId)", e);
		}
		return null;
	}
	public RptRetCosDeliveryNoteDTO retrieveCosDeliveryNoteCustDTO(String orderId, MobAppFormDTO mobAppForm, String locale) {
		try {
			RptRetCosDeliveryNoteDTO rptRetCosDeliveryNoteDTO = new RptRetCosDeliveryNoteDTO ();
			if ("SBS".equals(orderId.substring(1, 4))) {
				rptRetCosDeliveryNoteDTO = mobCosRptDAO.retrieveSBSCosDeliveryNoteCustDTO(orderId);
			} else {
				rptRetCosDeliveryNoteDTO = mobCosRptDAO.retrieveCosDeliveryNoteCustDTO(orderId);
			}
			if (rptRetCosDeliveryNoteDTO != null) {
				List<StockDTO> stockList = null;
				if ("AF011".equalsIgnoreCase(mobAppForm.getAppFormId())) {
					stockList = orderService.getStockAssignment(orderId);
				} else if ("AF022".equalsIgnoreCase(mobAppForm.getAppFormId())) {
					stockList = orderService.getDOAStockAssignment(orderId);
					
					String[] defectInd = mobCcsDoaRequestService.getRetDoaRequestDefectInd(orderId);
					if ("Y".equalsIgnoreCase(defectInd[0]) && "Y".equalsIgnoreCase(defectInd[1])) {
						rptRetCosDeliveryNoteDTO.setFullPackageInd("Y");
					} else {
						rptRetCosDeliveryNoteDTO.setHsDefectInd(defectInd[0]);
						rptRetCosDeliveryNoteDTO.setAcDefectInd(defectInd[1]);
						if ("Y".equalsIgnoreCase(rptRetCosDeliveryNoteDTO.getAcDefectInd())) {
							int seqNo = mobCcsDoaRequestService.getLastSeqNo(orderId);
							List<String> acDefectList = mobCcsDoaRequestService.getDoaRequestAcDefectItems(orderId, seqNo);
							if (acDefectList != null) {
								logger.info("acDefectList = " + acDefectList.size());
								for (String str : acDefectList) {
									StockDTO dto = new StockDTO();
									dto.setItemDesc(str);
									stockList.add(dto);
								}
							}
							logger.info("stockList = " + stockList.size());
						}
					}
				}
				rptRetCosDeliveryNoteDTO.setStockList(stockList);
				String upfrontPaymentInd=this.checkUpfrontPaymentInd(orderId);
				if (upfrontPaymentInd!=null&&"Y".equalsIgnoreCase(upfrontPaymentInd)){
					PaymentUpFrontUI paymentUpFrontUI = this.getPaymentUpfront(orderId);
					if (paymentUpFrontUI != null) {
						if ("M".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType())) {
							rptRetCosDeliveryNoteDTO.setPaymentMethod("Cash");
						} else if ("C".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType())) {
							rptRetCosDeliveryNoteDTO.setPaymentMethod("Credit Card");

						}else if ("I".equalsIgnoreCase(paymentUpFrontUI.getPayMethodType())) {
							rptRetCosDeliveryNoteDTO.setPaymentMethod("Credit Card Installment");
						}
					
					
						if (paymentUpFrontUI.getCreditCardHolderName()!=null)
							rptRetCosDeliveryNoteDTO.setCreditCardHolderName(paymentUpFrontUI.getCreditCardHolderName());
						if (paymentUpFrontUI.getCreditCardNum()!=null)
							rptRetCosDeliveryNoteDTO.setCreditCardNum(paymentUpFrontUI.getCreditCardNum());
					}
				}else{
					rptRetCosDeliveryNoteDTO.setPaymentMethod("N/A");
				}
				
				Double depositTotal= (Double)this.getDepositAmountForOrder(orderId).doubleValue();
				Double hsPayment=(Double)this.getHSPayment(orderId).doubleValue();
				Double prePayment=(Double)this.getPrePayment(orderId).doubleValue();			
				Double adminCharge=this.getAdminCharge(orderId, locale);
				Double paidAmt=(Double)this.getPaidAmt(orderId).doubleValue();
				if (hsPayment==null){
					hsPayment=0.0;
				}
				if (prePayment==null){
					prePayment=0.0;
				}
				if (adminCharge==null){
					adminCharge=0.0;
				}
				if (paidAmt==null){
					paidAmt=0.0;
				}
				if (depositTotal==null){
					depositTotal=0.0;
				}
				Long prepay = orderService.getPrepaymentWithoutHandset(orderId);
				if (prepay != null) {
					prePayment = prepay.doubleValue() ;
					rptRetCosDeliveryNoteDTO.setPrePayment("" + prePayment);
				}
				Double osBalance = hsPayment + prePayment + adminCharge + depositTotal - paidAmt;
				
				rptRetCosDeliveryNoteDTO.setHsPayment(hsPayment.toString());
				rptRetCosDeliveryNoteDTO.setPrePayment(prePayment.toString());
				rptRetCosDeliveryNoteDTO.setAdminCharge(adminCharge.toString());
				rptRetCosDeliveryNoteDTO.setPaidAmt(paidAmt.toString());
				rptRetCosDeliveryNoteDTO.setDepositTotal(depositTotal.toString());
				rptRetCosDeliveryNoteDTO.setOsBalance(osBalance.toString());
				DeliveryUI deliveryUI=retrieveCosDeliveryUI(orderId);
				DeliveryUI deliveryAddrUI=retrieveCosDeliveryAddrUI(orderId);
				String collectionType = "";
				
				if ("D".equalsIgnoreCase(deliveryUI.getDeliveryInd())) {
					collectionType = "Delivery by Courier";
					rptRetCosDeliveryNoteDTO.setDeliveryAddress(deliveryAddrUI.getSingleLineAddress());
					
				} else if ("P".equalsIgnoreCase(deliveryUI.getDeliveryInd())) {
					collectionType = "Pick Up at Centre";
					rptRetCosDeliveryNoteDTO.setDeliveryAddress(codeLkupService.getCodeDesc(
											BomWebPortalCcsConstant.DELIVERY_PICKCENTER_LOOKUP_DESC,
											deliveryUI.getDeliveryCentre()));
				}

				String locDesc = codeLkupService.getCodeDesc("STOCK_LOC", deliveryUI.getLocation());
				if (StringUtils.isNotEmpty(locDesc)) {
					collectionType += " / " + locDesc;
				}
				rptRetCosDeliveryNoteDTO.setCollectionType(collectionType);

				String DeliveryDateStr = "";
				String timeSlotDesc="";
				if(deliveryUI.getDeliveryDate()!=null)
					DeliveryDateStr=Utility.date2String(deliveryUI.getDeliveryDate(), "dd/MM/yyyy");

				String timeSlotCode = deliveryUI.getDeliveryTimeSlot();
				if (timeSlotCode != null && !"".equals(timeSlotCode)) {
					if (deliveryUI.isUrgentInd())
						timeSlotDesc = this.getTimeSlot("DED",timeSlotCode);
					else if (!deliveryUI.isUrgentInd())
						timeSlotDesc = this.getTimeSlot("SCH",timeSlotCode);

				}
				rptRetCosDeliveryNoteDTO.setDeliveryDateAndTimeSlot(DeliveryDateStr+" "+timeSlotDesc);
			}
			return rptRetCosDeliveryNoteDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveCosDeliveryNoteCustDTO(String orderId)", e);
		} 
		return null;
	}
	
	public DeliveryUI retrieveCosDeliveryAddrUI (String orderId){
		try {
			return mobCosRptDAO.retrieveCosDeliveryAddrUI(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in retrieveCosDeliveryAddrUI()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public String[] retrieveAuthorizationLetteIDInfo(String orderId) {
		try {
			String[] numOfSign = mobCosRptDAO.retrieveAuthorizationLetteIDInfo(orderId);

			return numOfSign;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveAuthorizationLetteIDInfo(String orderId)", e);
		}
		return null;
	}
	

	public RptMobileSafetyPhoneDTO retrieveRptMobileSafetyPhoneDTO(String orderId) {
		try {
			RptMobileSafetyPhoneDTO rptMobileSafetyPhoneDTO = mobCosRptDAO.retrieveRptMobileSafetyPhoneDTO(orderId);
			if (rptMobileSafetyPhoneDTO != null) {
				rptMobileSafetyPhoneDTO.setMobileSafetyLogo(getImageFilePath() + MOBILE_SAFETY_PHONE_FILE);
				
				List<ComponentDTO> componentList = this.orderService.getComponentList(orderId);
				for (ComponentDTO dto : componentList) {
					if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.CONTACT_PERSON.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceContactPerson(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.CONTACT_PHONE.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceContactPhone(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.USER_NAME.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceUserName(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.USER_TITLE.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceUserTitle(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR1.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr1(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR2.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr2(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR3.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr3(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR4.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr4(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR5.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr5(dto.getCompAttbVal());
					} 
				}
			}
			return rptMobileSafetyPhoneDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptMobileSafetyPhoneDTO()", e);
		}
		return null;
	}
	
	public RptMobileSafetyPhoneTnCDTO retrieveRptMobileSafetyPhoneTnCDTO(String orderId, String locale, OrderDTO orderDto) {
		try {
			RptMobileSafetyPhoneTnCDTO rptMobileSafetyPhoneTnCDTO = mobCosRptDAO.retrieveRptMobileSafetyPhoneTnCDTO(orderId);
			if (rptMobileSafetyPhoneTnCDTO != null) {
				rptMobileSafetyPhoneTnCDTO.setMonthlyRate("0");
				rptMobileSafetyPhoneTnCDTO.setMonthlyRateAfter("0");
				rptMobileSafetyPhoneTnCDTO.setCommitmentPeriod("1");
				
				String basketId = orderService.findBasketId(orderId);
				List<VasDetailDTO> vasReportUseDetailList = vasDetailService.getReportUseRPHSRPList(locale, basketId, "SS_FORM_RP",	orderId);
				if (vasReportUseDetailList != null && !vasReportUseDetailList.isEmpty()) {
						VasDetailDTO vasDetailDTO = vasReportUseDetailList.get(0);
						rptMobileSafetyPhoneTnCDTO.setMonthlyRate(vasDetailDTO.getItemRecurrentAmt());
						rptMobileSafetyPhoneTnCDTO.setMonthlyRateAfter(vasDetailDTO.getItemRecurrentAmt());
				}
				
				BasketDTO basketDTO = this.getBasketAttributeCOS(basketId,Utility.date2String(orderDto.getAppInDate(), "dd/MM/yyyy"));
				rptMobileSafetyPhoneTnCDTO.setCommitmentPeriod(basketDTO.getContractPeriod());
			}
			return rptMobileSafetyPhoneTnCDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptMobileSafetyPhoneTnCDTO()", e);
		}
		return null;
	}
	
	public RptMobileSafetyPhoneSuppAppDTO retrieveRptMobileSafetyPhoneSuppAppDTO(String orderId) {
		try {
			RptMobileSafetyPhoneSuppAppDTO rptMobileSafetyPhoneSuppAppDTO = mobCosRptDAO.retrieveRptMobileSafetyPhoneSuppAppDTO(orderId);
			return rptMobileSafetyPhoneSuppAppDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptMobileSafetyPhoneSuppAppDTO()", e);
		}
		return null;
	}
	
	public RptMobileSafetyPhoneAFCDTO retrieveRptMobileSafetyPhoneAFCDTO(String orderId) {
		try {
			return mobCosRptDAO.retrieveRptMobileSafetyPhoneAFCDTO(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptMobileSafetyPhoneAFCDTO()", e);
		}
		return null;
	}
	
	public DeliveryUI retrieveCosDeliveryUI (String orderId){
		try {
			return mobCosRptDAO.retrieveCosDeliveryUI(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in retrieveCosDeliveryUI()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public RptRetCountRejDTO retrieveRptRetCountRejDTO(String orderId, int seqNo) throws DAOException {
		try {
			RptRetCountRejDTO rptRetCountRejDTO = mobCosRptDAO.retrieveRptRetCountRejDTO(orderId, seqNo);
			if (rptRetCountRejDTO != null) {
				List<String> hsDefectList = mobCcsDoaRequestService.getDoaRequestSelectedHsDefectAll(orderId, seqNo);
				rptRetCountRejDTO.setHsDefect(hsDefectList);
				List<String> acDefectList = mobCcsDoaRequestService.getDoaRequestSelectedAcDefectAll(orderId, seqNo);
				rptRetCountRejDTO.setAcDefect(acDefectList);
			}
			return rptRetCountRejDTO;
		} catch (DAOException de) {
			logger.error("Exception caught in retrieveCosDeliveryUI()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<RptRetCountRejDTO> retrieveSBSRptRetCountRejDTO(String orderId, int seqNo) throws DAOException {
		try {
			List<RptRetCountRejDTO> rptRetCountRejDTOList = mobCosRptDAO.retrieveSBSRptRetCountRejDTO(orderId, seqNo);
			if (!CollectionUtils.isEmpty(rptRetCountRejDTOList) ) {
				for (RptRetCountRejDTO rptRetCountRejDTO : rptRetCountRejDTOList) {
					List<String> hsDefectList = mobCcsDoaRequestService.getDoaRequestSelectedHsDefectAll(orderId, seqNo);
					rptRetCountRejDTO.setHsDefect(hsDefectList);
					List<String> acDefectList = mobCcsDoaRequestService.getDoaRequestSelectedAcDefectAll(orderId, seqNo);
					rptRetCountRejDTO.setAcDefect(acDefectList);
				}
			}
			return rptRetCountRejDTOList;
		} catch (DAOException de) {
			logger.error("Exception caught in retrieveSBSRptRetCountRejDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public RptTnGServiceFormDTO retrieveRptTnGServiceFormDTO(String orderId) {
		try {
			RptTnGServiceFormDTO rptTnGServiceFormDTO = mobCosRptDAO.retrieveRptTnGServiceFormDTO(orderId);
			return rptTnGServiceFormDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptTnGServiceFormDTO()", e);
		}
		return null;
	}
	
	public IPhoneTradeInFormDTO retrieveiPhoneTradeInFormDTO(String orderId) {
		try {
			IPhoneTradeInFormDTO iPhoneTradeInFormDTO = mobCosRptDAO.retrieveIPhoneTradeInFormDTO(orderId);
			return iPhoneTradeInFormDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveRptTnGServiceFormDTO()", e);
		}
		return null;
	}

	/*------------------- End Retrieve Data -------------------*/
	
	/*------------------- Condition Check -------------------*/
	public RptPrintIndDTO retrieveAdditionalServiceInd(String orderId) {
		try {
			RptPrintIndDTO rptPrintIndDTO = mobCosRptDAO.retrieveAdditionalServiceInd(orderId);
			if (rptPrintIndDTO != null) {
				rptPrintIndDTO.setHasIGuard(this.hasIGuard(orderId));
			}
			return rptPrintIndDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in retrieveAdditionalServiceInd()", e);
		}
		return null;
	}
	
	public boolean hasThirdPartyAutopayViaCreditCard(String orderId) {
		try {
			return mobCosRptDAO.hasThirdPartyAutopayViaCreditCard(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in hasThirdPartyAutopayViaCreditCard()", e);
		} 
		return false;
	}

	public boolean hasWarmTips(String orderId) {
		try {
			return mobCosRptDAO.hasWarmTips(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in hasWarmTips()", e);
		}
		return false;
	}

	public boolean hasDeliveryItem(String orderId) {
		try {
			return mobCosRptDAO.hasDeliveryItem(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in hasDeliveryItem()", e);
		}
		return false;
	}
	
	public boolean hasDoaRequest(String orderId) {
		try {
			return mobCosRptDAO.hasDoaRequest(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in hasDoaRequest()", e);
		}
		return false;
	}

	public boolean hasTNGServiceDummyVas(String orderId) {
		try {
			return orderDao.hasTNGServiceDummyVas(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in hasWarmTips()", e);
		}
		return false;
	}
	
	public String checkUpfrontPaymentInd (String orderId) {
		try {
			String UpfrontPaymentInd = mobCosRptDAO.checkUpfrontPaymentInd(orderId);
			return UpfrontPaymentInd;
		} catch (DAOException e) {
			logger.error("Exception caught in checkUpfrontPaymentInd(String orderId)", e);
		}
		return null;
	}
	
	/*public boolean hasNFCSim(String orderId) {
		String simType = orderService.getSimTypeByCosOrder(orderId);
		if (simType == null) {
			return false;
		} else if ("1".equalsIgnoreCase(simType)) {
			return true;
		} else if ("4".equalsIgnoreCase(simType)) {
			return true;
		}
		return false;
	}*/
	
	public boolean hasOctopusSim(String orderId) {
		String simType = orderService.getSimTypeByCosOrder(orderId);
		if (simType == null) {
			return false;
		} else if ("2".equalsIgnoreCase(simType)) {
			return true;
		} else if ("4".equalsIgnoreCase(simType)) {
			return true;
		}
		return false;
	}
	
	public boolean hasIGuard(String orderId) {
		String iGuardSn = iGuardService.getIGuardSnByOrder(orderId);
		return (iGuardSn == null || "".equals(iGuardSn)) ? false : true;
	}
	/*------------------- End Condition Check -------------------*/
	

	
	
	public PaymentUpFrontUI getPaymentUpfront (String orderId ){
		try {
			return mobCosRptDAO.getBomwebUpfrontPayment(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getPaymentUpfront()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	public List<MobCcsSupportDocDTO> getAllSupportDocList(String orderId) {
		try {
			List<MobCcsSupportDocDTO> allSupportDocList = mobCosRptDAO.getAllSupportDocList(orderId);

			return allSupportDocList;
		} catch (DAOException e) {
			logger.error("Exception caught in getAllSupportDocList(String orderId)", e);
		}
		return null;
	}
	
	public String[] getCreditCardAutoPayNumOfSign(String orderId) {
		try {
			String[] numOfSign = mobCosRptDAO.getCreditCardAutoPayNumOfSign(orderId);

			return numOfSign;
		} catch (DAOException e) {
			logger.error("Exception caught in getCreditCardAutoPayNumOfSign(String orderId)", e);
		}
		return null;
	}
	public BigDecimal getHSPayment(String orderId) {
		try {
			BigDecimal amount = mobCosRptDAO.getHSPayment(orderId);
			return amount;
		} catch (DAOException e) {
			logger.error("Exception caught in checkUpfrontPaymentInd(String orderId)", e);
		}
		return null;
	}
	public BigDecimal getPrePayment(String orderId) {
		try {
			BigDecimal amount = mobCosRptDAO.getPrePayment(orderId);
			return amount;
		} catch (DAOException e) {
			logger.error("Exception caught in checkUpfrontPaymentInd(String orderId)", e);
		}
		return null;
	}
	public Double getAdminCharge(String orderId, String locale) {
		try {
			
			BigDecimal adminChargeItem = mobCosRptDAO.getAdminChargeItem(orderId);
			if (adminChargeItem==null){
				adminChargeItem=BigDecimal.ZERO;
			}
			
			/*******MultiSim********/
			List<VasOnetimeAmtDTO> adminChargeMemberToo1List = new ArrayList<VasOnetimeAmtDTO>();
			List<VasOnetimeAmtDTO> adminChargeMemberChgSIMList = new ArrayList<VasOnetimeAmtDTO>();
			Integer adminChargeMemberToo1 = 0;
			Integer adminChargeMemberChgSIM = 0;
			
			adminChargeMemberToo1List = orderService.getRetToo1AdmChargeList(locale, orderId);
			adminChargeMemberChgSIMList = orderService.getRetChgSimAdmChargeList(locale, orderId);
			
			if (adminChargeMemberToo1List != null && adminChargeMemberToo1List.size() >= 0) {
				for (VasOnetimeAmtDTO temp : adminChargeMemberToo1List) {
					adminChargeMemberToo1 += Integer.parseInt(temp.getVasOnetimeAmt());
				}
			};
			
			if (adminChargeMemberChgSIMList != null && adminChargeMemberChgSIMList.size() >= 0) {
				for (VasOnetimeAmtDTO temp1 : adminChargeMemberChgSIMList) {
					adminChargeMemberChgSIM += Integer.parseInt(temp1.getVasOnetimeAmt());
				}
			};
			
			/*********************/
			
			Double amount=(Double)adminChargeMemberChgSIM.doubleValue()+(Double)adminChargeItem.doubleValue()+(Double)adminChargeMemberToo1.doubleValue();
			return amount;
		} catch (DAOException e) {
			logger.error("Exception caught in checkUpfrontPaymentInd(String orderId)", e);
		}
		return null;
	}
	public BigDecimal getPaidAmt(String orderId) {
		try {
			BigDecimal amount = mobCosRptDAO.getPaidAmt(orderId);
			return amount;
		} catch (DAOException e) {
			logger.error("Exception caught in checkUpfrontPaymentInd(String orderId)", e);
		}
		return null;
	}
	
	public String getTimeSlot(String slotType, String timeSlot) {
		try {
			String dTimeSlot = mobCosRptDAO.getTimeSlot(slotType,timeSlot);
			return dTimeSlot;
		} catch (DAOException e) {
			logger.error("Exception caught in getTimeSlot(String orderId)", e);
		}
		return null;
	}
	
	public BigDecimal getDepositAmountForOrder(String orderId) {
		try {
			return mobCosRptDAO.getDepositAmountForOrder(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDepositAmountForOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public RptRetCourierDeliveryGuidelineDTO getBSContact(String orderId) {
		try {
			RptRetCourierDeliveryGuidelineDTO rptRetCourierDeliveryGuidelineDTO = mobCosRptDAO.getBSContact(orderId);

			return rptRetCourierDeliveryGuidelineDTO;
		} catch (DAOException e) {
			logger.error("Exception caught in getBSContact(String orderId)", e);
		}
		return null;
	}
	
	private String getAttbValue(List<ComponentDTO> componentList, String compAttbId) {
		if (componentList == null) {
			return null;
		}
		for (ComponentDTO dto : componentList) {
			if (dto.getCompAttbId().equals(compAttbId)) {
				return dto.getCompAttbVal();
			}
		}
		return null;
	}
	public BasketDTO getBasketAttributeCOS(String basketId, String appDate) {
		
		try{
			BasketDTO basketDTO = mobCosRptDAO.getBasketAttributeCOS(basketId);
			if (mobCosRptDAO.getHSItemCode(basketId,appDate)!=null)
				basketDTO.setHsPosItemCd(mobCosRptDAO.getHSItemCode(basketId,appDate));
			return basketDTO;
		}catch (DAOException de){
			logger.error("Exception caught in getBasketAttributeCOS()", de);
			throw new AppRuntimeException(de);			
		}
	}

	public List<AllOrdDocAssgnDTO> getRetAppDocCollected(String orderId) {
		try {
			return this.mobCosRptDAO.getRetAppDocCollected(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getRetAppDocCollected", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<MobAppFormDTO> getReportListByFormIds(String channelId, String orderNature, List<String> formIds) {
		try {
			return this.mobCosRptDAO.getReportListByFormIds(channelId, orderNature, formIds);
		} catch (DAOException de) {
			logger.error("Exception caught in getRetAppDocCollected", de);
			throw new AppRuntimeException(de);
		}
	}	
	/****************** Getter/Setter *************************/
	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}

	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
	}

	
	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public ReportRepository getReportRepository() {
		return reportRepository;
	}

	public void setReportRepository(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

	public MobCosRptDAO getMobCosRptDAO() {
		return mobCosRptDAO;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setMobCosRptDAO(MobCosRptDAO mobCosRptDAO) {
		this.mobCosRptDAO = mobCosRptDAO;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}
	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}
	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}
	public OrderDAO getOrderDao() {
		return orderDao;
	}
	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}
	public MobCcsDoaRequestService getMobCcsDoaRequestService() {
		return mobCcsDoaRequestService;
	}

	public void setMobCcsDoaRequestService(
			MobCcsDoaRequestService mobCcsDoaRequestService) {
		this.mobCcsDoaRequestService = mobCcsDoaRequestService;
	}

	public DisplayLkupDAO getDisplayLkupDAO() {
		return displayLkupDAO;
	}

	public void setDisplayLkupDAO(DisplayLkupDAO displayLkupDAO) {
		this.displayLkupDAO = displayLkupDAO;
	}

	public MobCosCopService getMobCosCopService() {
		return mobCosCopService;
	}

	public void setMobCosCopService(MobCosCopService mobCosCopService) {
		this.mobCosCopService = mobCosCopService;
	}

	public String getImageFilePath() {
		return this.imageFilePath + "/";
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	
	public SupportDocService getSupportDocService() {
		return supportDocService;
	}


	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}

	public ItemFuncMobService getItemFuncMobService() {
		return itemFuncMobService;
	}


	public void setItemFuncMobService(ItemFuncMobService itemFuncMobService) {
		this.itemFuncMobService = itemFuncMobService;
	}
	
	public StaffInfoDAO getStaffInfoDao() {
		return staffInfoDao;
	}


	public void setStaffInfoDao(StaffInfoDAO staffInfoDao) {
		this.staffInfoDao = staffInfoDao;
	}
	


	public HSTradeDescDAO getHsTradeDescDAO() {
		return hsTradeDescDAO;
	}


	public void setHsTradeDescDAO(HSTradeDescDAO hsTradeDescDAO) {
		this.hsTradeDescDAO = hsTradeDescDAO;
	}


	public GenericReportHelper getGenericReportHelper() {
		return genericReportHelper;
	}


	public void setGenericReportHelper(GenericReportHelper genericReportHelper) {
		this.genericReportHelper = genericReportHelper;
	}

	public VasDetailDAO getVasDetailDao() {
		return vasDetailDao;
	}


	public void setVasDetailDao(VasDetailDAO vasDetailDao) {
		this.vasDetailDao = vasDetailDao;
	}

	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}

	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}
	
	public boolean isMob0060Optout(String orderId) {
		try {
			return mobCosRptDAO.isMob0060Optout(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in isMob0060Optout()", e);
		}
		return false;
	}
	public String maskedDocNum (String docNum){
		String maskedDocNum= docNum;
		if (docNum.length()>7){
			maskedDocNum=docNum.substring(0,4) + "***" + docNum.substring(7);
		}
		return maskedDocNum;
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
	
}