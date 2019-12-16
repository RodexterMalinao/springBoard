package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.ServiceLineDTO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.ims.dto.ChannelDetailDTO;
import com.bomwebportal.ims.dto.NtvInfo;
import com.bomwebportal.ims.dto.NtvInfo.Campaign;
import com.bomwebportal.ims.service.ImsNowTVService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class ImsDSNtvInfoController extends SimpleFormController{
	private final Gson gson = new Gson();
	private ImsNowTVService imsNowTVService;
	private String sophieUrl;
	protected final Log logger = LogFactory.getLog(getClass());
	public ImsNowTVService getImsNowTVService() {
		return imsNowTVService;
	}

	public void setImsNowTVService(ImsNowTVService imsNowTVService) {
		this.imsNowTVService = imsNowTVService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fsa = request.getParameter("fsa");
		CustomerInformationDTO returnCustInfo = (CustomerInformationDTO) request.getSession().getAttribute("customerInformationDTOSession");
		logger.info("fsa: "+fsa);
		if(returnCustInfo.getChannelDetailList()==null){
			List<ChannelDetailDTO> list =new ArrayList<ChannelDetailDTO>();
			Map<String, List<ChannelDetailDTO>> m = new HashMap<String, List<ChannelDetailDTO>>();
			for(ServiceLineDTO line : returnCustInfo.getServiceLineDTOList()){
				list = imsNowTVService.getVIChannelInfo(line.getServiceId());									
				m.put(line.getServiceId(),list);
			}
			returnCustInfo.setChannelDetailList(m);
		}
		Double VIMntvinfoPriceSum = 0.0;
		if(returnCustInfo.getChannelDetailList().get(fsa)!=null){
			for(ChannelDetailDTO dto:returnCustInfo.getChannelDetailList().get(fsa)){
				VIMntvinfoPriceSum += Double.parseDouble(dto.getCharge().substring(1,dto.getCharge().length()));
			}
			request.setAttribute("VIMntvinfoPriceSum","$"+VIMntvinfoPriceSum.toString());
		}
		NtvInfo SOFHIEinfoList = this.SOFHIEinfo(fsa);	
//		logger.info("SOFHIEinfoList: "+gson.toJson(SOFHIEinfoList));
		if(SOFHIEinfoList !=null){
			logger.info("SOFHIEinfoList: "+gson.toJson(SOFHIEinfoList));
			Double OriSumNum = 0.0;
			Double DscSumNum = 0.0;
			if(SOFHIEinfoList.getCampaigns()!=null)
			for(Campaign dto:SOFHIEinfoList.getCampaigns()){
				OriSumNum += Double.parseDouble(dto.getOrigPrice());
				DscSumNum += Double.parseDouble(dto.getDiscPrice());
			}
			SOFHIEinfoList.setOrisumNum(OriSumNum.toString());
			SOFHIEinfoList.setDscsumNum(DscSumNum.toString());
			returnCustInfo.setNtvInfo(SOFHIEinfoList);
		}
		
		request.setAttribute("VIMntvinfolist", returnCustInfo.getChannelDetailList().get(fsa));
		request.setAttribute("SOPHIEinfolist", SOFHIEinfoList);

		request.getSession().setAttribute("customerInformationDTOSession",returnCustInfo);
		return new ModelAndView("imsdsntvinfo", "ntvinfolist", returnCustInfo);
		
	}
	public NtvInfo SOFHIEinfo(String fsa){
		Map<String, String> m = new HashMap<String, String>(); 
		m.put("fsa", fsa);
		m.put("callerID", "SPB");
		m.put("callerReferenceNo", "test123123123123");
		m.put("version", "1.0");
		
//		String url = "http://uat.sophie.pccw.com:8080/sophie/app/ws/getCustSubCamp";
		
		String str = Utility.doRESTcall("POST", sophieUrl, m);
		
		NtvInfo SOFHIEinfo = new NtvInfo();

		return gson.fromJson(str, SOFHIEinfo.getClass());
	}

	public void setSophieUrl(String sophieUrl) {
		this.sophieUrl = sophieUrl;
	}

	public String getSophieUrl() {
		return sophieUrl;
	}
	

}

