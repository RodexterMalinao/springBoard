package com.bomltsportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.MarkerDTO;
import com.bomltsportal.service.AddressLookupService;
import com.bomltsportal.util.SessionConstant;

public class MarkerController implements Controller{

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected AddressLookupService addressLookupService;
	
	public AddressLookupService getAddressLookupService() {
		return addressLookupService;
	}

	public void setAddressLookupService(AddressLookupService addressLookupService) {
		this.addressLookupService = addressLookupService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String zoomlevel = request.getParameter(SessionConstant.PARAM_ZOOM_LEVEL);
		String latLower = request.getParameter(SessionConstant.PARAM_LAT_LOWER);
		String latUpper = request.getParameter(SessionConstant.PARAM_LAT_UPPER);
		String lngLower = request.getParameter(SessionConstant.PARAM_ING_LOWER);
		String lngUpper = request.getParameter(SessionConstant.PARAM_ING_UPPER);
		
		
		JSONArray jsonArray = new JSONArray();
		MarkerDTO[] markers = null;
		
		if (StringUtils.isBlank(zoomlevel)) {
			return new ModelAndView("ajax_view", "jsonArray", jsonArray);
		}
		else if (Integer.parseInt(zoomlevel) <= 3) {
			markers = addressLookupService.getMarkerByLevel(zoomlevel, latLower,
					latUpper, lngLower, lngUpper);
		} else if (Integer.parseInt(zoomlevel) > 3) {
			markers = addressLookupService.searchBuildingByRange(latLower,
					latUpper, lngLower, lngUpper);
		}
		
		if (ArrayUtils.isNotEmpty(markers)) {
			for (MarkerDTO marker : markers) {
				jsonArray.element(createJsonMarker(marker));
			}
		}
		
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
	}

	
	private JSONObject createJsonMarker(MarkerDTO marker) {
		
		JSONObject jsonObject = new JSONObject();
		
		if (marker instanceof BuildingMarkerDTO) {
			
			jsonObject.put("marker_idx", ((BuildingMarkerDTO)marker).getIndexKey());
			jsonObject.put("BUILD_XY", ((BuildingMarkerDTO)marker).getBuildXy());
			jsonObject.put("lat", ((BuildingMarkerDTO)marker).getLat());
			jsonObject.put("lng", ((BuildingMarkerDTO)marker).getLng());
			jsonObject.put("NAME_EN", ((BuildingMarkerDTO)marker).getBldgNameEn());
			jsonObject.put("NAME_CH", ((BuildingMarkerDTO)marker).getBldgNameCh());
			jsonObject.put("STREET_NUM", ((BuildingMarkerDTO)marker).getStreetNum());
			jsonObject.put("STREET_NAME_EN", ((BuildingMarkerDTO)marker).getStreetNameEn());
			jsonObject.put("STREET_NAME_CH", ((BuildingMarkerDTO)marker).getStreetNameCh());
			jsonObject.put("SITE_GROUP", ((BuildingMarkerDTO)marker).getSiteGroup());
			jsonObject.put("SF_BLDG_RES", ((BuildingMarkerDTO)marker).getSfBldgRes());
			jsonObject.put("SF_BLDG_BUS", ((BuildingMarkerDTO)marker).getSfBldgBus());
			jsonObject.put("SECT_CD", ((BuildingMarkerDTO)marker).getSectCd());
			jsonObject.put("section_desc_en", ((BuildingMarkerDTO)marker).getSectDescEn());
			jsonObject.put("section_desc_ch", ((BuildingMarkerDTO)marker).getSectDescCh());
			jsonObject.put("DISTR_CD", ((BuildingMarkerDTO)marker).getDistrCd());
			jsonObject.put("district_desc_en", ((BuildingMarkerDTO)marker).getDistDescEn());
			jsonObject.put("district_desc_ch", ((BuildingMarkerDTO)marker).getDistDescCh());
			jsonObject.put("AREA_CD", ((BuildingMarkerDTO)marker).getAreaCd());
			jsonObject.put("area_desc_en", ((BuildingMarkerDTO)marker).getAreaDescEn());
			jsonObject.put("area_desc_ch", ((BuildingMarkerDTO)marker).getAreaDescCh());
			jsonObject.put("housing_addr_en", ((BuildingMarkerDTO)marker).getHousingAddrEn());
			jsonObject.put("housing_addr_ch", ((BuildingMarkerDTO)marker).getHousingAddrCh());
			jsonObject.put("RES_BASIC_BW", ((BuildingMarkerDTO)marker).getResBasicBw());
			jsonObject.put("RES_FTTB_BW", ((BuildingMarkerDTO)marker).getResFttbBw());
			jsonObject.put("RES_FTTH_BW", ((BuildingMarkerDTO)marker).getResFtthBw());
			jsonObject.put("RES_TV_SD", ((BuildingMarkerDTO)marker).getResTvSd());
			jsonObject.put("RES_TV_HD", ((BuildingMarkerDTO)marker).getResTvHd());
			jsonObject.put("BUS_BASIC_BW", ((BuildingMarkerDTO)marker).getBusBasicBw());
			jsonObject.put("BUS_FTTB_BW", ((BuildingMarkerDTO)marker).getBusFttbBw());
			jsonObject.put("BUS_FTTH_BW", ((BuildingMarkerDTO)marker).getBusFtthBw());
			jsonObject.put("BUS_TV_SD", ((BuildingMarkerDTO)marker).getBusTvSd());
			jsonObject.put("BUS_TV_HD", ((BuildingMarkerDTO)marker).getBusTvHd());
			jsonObject.put("IS_RM", ((BuildingMarkerDTO)marker).getIsRm());
			jsonObject.put("IS_PREMIER", ((BuildingMarkerDTO)marker).getIsPremier());
			jsonObject.put("IS_PH", ((BuildingMarkerDTO)marker).getisPh());
			jsonObject.put("IS_HOS", ((BuildingMarkerDTO)marker).getisHos());
			jsonObject.put("IS_CLEANED_VILLAGE", ((BuildingMarkerDTO)marker).getIsCleanedVillage());
			jsonObject.put("res_eye", ((BuildingMarkerDTO)marker).getResEyeInd());
			jsonObject.put("res_eye_pe", ((BuildingMarkerDTO)marker).getResEyePeInd());
			jsonObject.put("res_tel", ((BuildingMarkerDTO)marker).getResTelInd());
		}
		else {
			jsonObject.put("marker_lat", marker.getLat());
			jsonObject.put("marker_lng", marker.getLng());
			jsonObject.put("bus_lat", marker.getBusLat());
			jsonObject.put("bus_lng", marker.getBusLng());
			jsonObject.put("res_lat", marker.getResLat());
			jsonObject.put("res_lng", marker.getResLng());
			jsonObject.put("marker_desc_en", marker.getDescEn());
			jsonObject.put("marker_desc_ch", marker.getDescCh());
			jsonObject.put("res_tv_ind", marker.getResTvInd());
			jsonObject.put("res_bb_ind", marker.getResBbInd());
			jsonObject.put("bus_bb_ind", marker.getBusBbInd());
			jsonObject.put("bus_tv_ind", marker.getBusTvInd());
			jsonObject.put("res_eye_ind", marker.getResEyeInd());
			jsonObject.put("res_eye_pe_ind", marker.getResEyePeInd());
			jsonObject.put("res_tel_ind", marker.getResTelInd());
		}
		
		return jsonObject;
	}
	
	
}
