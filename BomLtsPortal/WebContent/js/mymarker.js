function myMarker(opt_options) {

	var options = opt_options || {};

	if (options["marker_idx"])
		this.marker_idx = options["marker_idx"];
	if (options["lat"])
		this.lat = options["lat"];
	if (options["lng"])
		this.lng = options["lng"];
	if (options["bus_lat"])
		this.bus_lat = options["bus_lat"];
	if (options["bus_lng"])
		this.bus_lng = options["bus_lng"];
	if (options["res_lat"])
		this.res_lat = options["res_lat"];
	if (options["res_lng"])
		this.res_lng = options["res_lng"];
	if (options["BUILD_XY"])
		this.BUILD_XY = options["BUILD_XY"];
	if (options["NAME_EN"])
		this.NAME_EN = options["NAME_EN"];
	if (options["NAME_CH"])
		this.NAME_CH = options["NAME_CH"];
	if (options["STREET_NUM"])
		this.STREET_NUM = options["STREET_NUM"];
	if (options["STREET_NAME_EN"])
		this.STREET_NAME_EN = options["STREET_NAME_EN"];
	if (options["STREET_NAME_CH"])
		this.STREET_NAME_CH = options["STREET_NAME_CH"];
	if (options["SITE_GROUP"])
		this.SITE_GROUP = options["SITE_GROUP"];
	if (options["SF_BLDG_RES"])
		this.SF_BLDG_RES = options["SF_BLDG_RES"];
	if (options["SF_BLDG_BUS"])
		this.SF_BLDG_BUS = options["SF_BLDG_BUS"];
	if (options["SECT_CD"])
		this.SECT_CD = options["SECT_CD"];
	if (options["section_desc_en"])
		this.section_desc_en = options["section_desc_en"];
	if (options["section_desc_ch"])
		this.section_desc_ch = options["section_desc_ch"];
	if (options["DISTR_CD"])
		this.DISTR_CD = options["DISTR_CD"];
	if (options["district_desc_en"])
		this.district_desc_en = options["district_desc_en"];
	if (options["district_desc_ch"])
		this.district_desc_ch = options["district_desc_ch"];
	if (options["AREA_CD"])
		this.AREA_CD = options["AREA_CD"];
	if (options["area_desc_en"])
		this.area_desc_en = options["area_desc_en"];
	if (options["area_desc_ch"])
		this.area_desc_ch = options["area_desc_ch"];
	if (options["housing_addr_en"])
		this.housing_addr_en = options["housing_addr_en"];
	if (options["housing_addr_ch"])
		this.housing_addr_ch = options["housing_addr_ch"];
	if (options["RES_BASIC_BW"])
		this.RES_BASIC_BW = options["RES_BASIC_BW"];
	if (options["RES_FTTB_BW"])
		this.RES_FTTB_BW = options["RES_FTTB_BW"];
	if (options["RES_FTTH_BW"])
		this.RES_FTTH_BW = options["RES_FTTH_BW"];
	if (options["RES_TV_SD"])
		this.RES_TV_SD = options["RES_TV_SD"];
	if (options["RES_TV_HD"])
		this.RES_TV_HD = options["RES_TV_HD"];
	if (options["BUS_BASIC_BW"])
		this.BUS_BASIC_BW = options["BUS_BASIC_BW"];
	if (options["BUS_FTTB_BW"])
		this.BUS_FTTB_BW = options["BUS_FTTB_BW"];
	if (options["BUS_FTTH_BW"])
		this.BUS_FTTH_BW = options["BUS_FTTH_BW"];
	if (options["BUS_TV_SD"])
		this.BUS_TV_SD = options["BUS_TV_SD"];
	if (options["BUS_TV_HD"])
		this.BUS_TV_HD = options["BUS_TV_HD"];
	if (options["IS_RM"])
		this.IS_RM = options["IS_RM"];
	if (options["IS_PREMIER"])
		this.IS_PREMIER = options["IS_PREMIER"];
	if (options["IS_PH"])
		this.IS_PH = options["IS_PH"];
	if (options["IS_HOS"])
		this.IS_HOS = options["IS_HOS"];


	this._isNet = false;
	this._isNow = false;
	this._isNetB = false;
	this._isNowB = false;
	

	if ((this.RES_BASIC_BW || this.RES_FTTB_BW || this.RES_FTTH_BW ) && (this.SF_BLDG_RES && this.SF_BLDG_RES=="Y")) {
		this._isNet = true;
	} else {
		this._isNet = false;
	}
	
	if ((this.BUS_BASIC_BW || this.BUS_FTTB_BW || this.BUS_FTTH_BW) && (this.SF_BLDG_BUS && this.SF_BLDG_BUS=="Y")){
		this._isNetB = true;
	}else{
		this._isNetB = false;
	}

	if (this.RES_TV_SD=="Y" || this.RES_TV_HD=="Y"){
		this._isNow = true;
	} else {
		this._isNow = false;
	}
	
	if (this.BUS_TV_SD=="Y" || this.BUS_TV_HD=="Y") {
		this._isNowB = true;
	} else {
		this._isNowB = false;
	}

}

myMarker.prototype.is_PH = function(){
	return this.is_PH;
};
myMarker.prototype.setIs_PH = function(is_PH) {
this.is_PH = is_PH;
};
myMarker.prototype['setIs_PH'] = myMarker.prototype.setIs_PH;

myMarker.prototype.is_HOS = function(){
	return this.is_HOS;
};
myMarker.prototype.setIs_HOS = function(is_HOS) {
this.is_HOS = is_HOS;
};
myMarker.prototype['Is_HOS'] = myMarker.prototype.setIs_HOS;

myMarker.prototype.getLat = function(){
	return this.lat;
};
myMarker.prototype.setLat = function(lat) {
  this.lat = lat;
};
myMarker.prototype['setLat'] =	myMarker.prototype.setLat;

myMarker.prototype.getLng = function(){
	return this.lng;
};
myMarker.prototype.setLng = function(lng) {
this.lng = lng;
};
myMarker.prototype['setLng'] =	myMarker.prototype.setLng;


myMarker.prototype.getBusLat = function(){
	return this.bus_lat;
};
myMarker.prototype.setBusLat = function(bus_lat) {
  this.bus_lat = bus_lat;
};
myMarker.prototype['setBusLat'] =	myMarker.prototype.setBusLat;

myMarker.prototype.getBusLng = function(){
	return this.bus_lng;
};
myMarker.prototype.setBusLng = function(bus_lng) {
this.bus_lng = bus_lng;
};
myMarker.prototype['setBusLng'] =	myMarker.prototype.setBusLng;



myMarker.prototype.getResLat = function(){
	return this.res_lat;
};
myMarker.prototype.setResLat = function(res_lat) {
  this.res_lat = res_lat;
};
myMarker.prototype['setResLat'] =	myMarker.prototype.setResLat;

myMarker.prototype.getResLng = function(){
	return this.res_lng;
};
myMarker.prototype.setResLng = function(res_lng) {
this.res_lng = res_lng;
};
myMarker.prototype['setResLng'] =	myMarker.prototype.setResLng;


myMarker.prototype.getBuild_xy = function(){
	return this.BUILD_XY;
};
myMarker.prototype.setBuild_xy = function(BUILD_XY) {
this.BUILD_XY = BUILD_XY;
};
myMarker.prototype['setBuild_xy'] =	myMarker.prototype.setBuild_xy;

myMarker.prototype.getName_en = function(){
	return this.NAME_EN;
};
myMarker.prototype.setName_en = function(name_en) {
this.NAME_EN = name_en;
};
myMarker.prototype['setName_en'] =	myMarker.prototype.setName_en;

myMarker.prototype.getName_ch = function(){
	return this.NAME_CH;
};
myMarker.prototype.setName_ch = function(name_ch) {
this.NAME_CH = name_ch;
};
myMarker.prototype['setName_ch'] =	myMarker.prototype.setName_ch;

myMarker.prototype.getStreet_num = function(){
	return this.STREET_NUM;
};
myMarker.prototype.setStreet_num = function(street_num) {
this.STREET_NUM = street_num;
};
myMarker.prototype['setStreet_num'] =	myMarker.prototype.setStreet_num;

myMarker.prototype.getStreet_name_en = function(){
	return this.STREET_NAME_EN;
};
myMarker.prototype.setStreet_name_en = function(street_name_en) {
this.STREET_NAME_EN = street_name_en;
};
myMarker.prototype['setStreet_name_en'] =	myMarker.prototype.setStreet_name_en;

myMarker.prototype.getStreet_name_ch = function(){
	return this.STREET_NAME_CH;
};
myMarker.prototype.setStreet_name_ch = function(street_name_ch) {
this.STREET_NAME_CH = street_name_ch;
};
myMarker.prototype['setStreet_name_ch'] =	myMarker.prototype.setStreet_name_ch;

myMarker.prototype.getSite_group = function(){
	return this.SITE_GROUP;
};
myMarker.prototype.setSite_group = function(site_group) {
this.SITE_GROUP = site_group;
};
myMarker.prototype['setSite_group'] =	myMarker.prototype.setSite_group;

myMarker.prototype.getSf_bldg_res = function(){
	return this.SF_BLDG_RES;
};
myMarker.prototype.setSf_bldg_res = function(sf_bldg_res) {
this.SF_BLDG_RES = sf_bldg_res;
};
myMarker.prototype['setSf_bldg_res'] =	myMarker.prototype.setSf_bldg_res;

myMarker.prototype.getSf_bldg_bus = function(){
	return this.SF_BLDG_BUS;
};
myMarker.prototype.setSf_bldg_bus = function(sf_bldg_bus) {
this.SF_BLDG_BUS = sf_bldg_bus;
};
myMarker.prototype['setSf_bldg_bus'] =	myMarker.prototype.setSf_bldg_bus;

myMarker.prototype.getSect_cd = function(){
	return this.SECT_CD;
};
myMarker.prototype.setSect_cd = function(sect_cd) {
this.SECT_CD = sect_cd;
};
myMarker.prototype['setSect_cd'] =	myMarker.prototype.setSect_cd;

myMarker.prototype.getSection_desc_en = function(){
	return this.section_desc_en;
};
myMarker.prototype.setSection_desc_en = function(section_desc_en) {
this.section_desc_en = section_desc_en;
};
myMarker.prototype['setSection_desc_en'] =	myMarker.prototype.setSection_desc_en;

myMarker.prototype.getSection_desc_ch = function(){
	return this.section_desc_ch;
};
myMarker.prototype.setSection_desc_ch = function(section_desc_ch) {
this.section_desc_ch = section_desc_ch;
};
myMarker.prototype['setSection_desc_ch'] =	myMarker.prototype.setSection_desc_ch;

myMarker.prototype.getDistr_cd = function(){
	return this.DISTR_CD;
};
myMarker.prototype.setDistr_cd = function(distr_cd) {
this.DISTR_CD = distr_cd;
};
myMarker.prototype['setDistr_cd'] =	myMarker.prototype.setDistr_cd;

myMarker.prototype.getDistrict_desc_en = function(){
	return this.district_desc_en;
};
myMarker.prototype.setDistrict_desc_en = function(district_desc_en) {
this.district_desc_en = district_desc_en;
};
myMarker.prototype['setDistrict_desc_en'] =	myMarker.prototype.setDistrict_desc_en;

myMarker.prototype.getDistrict_desc_ch = function(){
	return this.district_desc_ch;
};
myMarker.prototype.setDistrict_desc_ch = function(district_desc_ch) {
this.district_desc_ch = district_desc_ch;
};
myMarker.prototype['setDistrict_desc_ch'] =	myMarker.prototype.setDistrict_desc_ch;

myMarker.prototype.getArea_cd = function(){
	return this.AREA_CD;
};
myMarker.prototype.setArea_cd = function(area_cd) {
this.AREA_CD = area_cd;
};
myMarker.prototype['setArea_cd'] =	myMarker.prototype.setArea_cd;

myMarker.prototype.getArea_desc_en = function(){
	return this.area_desc_en;
};
myMarker.prototype.setArea_desc_en = function(area_desc_en) {
this.area_desc_en = area_desc_en;
};
myMarker.prototype['setArea_desc_en'] =	myMarker.prototype.setArea_desc_en;

myMarker.prototype.getArea_desc_ch = function(){
	return this.area_desc_ch;
};
myMarker.prototype.setArea_desc_ch = function(area_desc_ch) {
this.area_desc_en = area_desc_ch;
};
myMarker.prototype['setArea_desc_ch'] =	myMarker.prototype.setArea_desc_ch;

myMarker.prototype.getHousing_addr_en = function(){
	return this.housing_addr_en;
};
myMarker.prototype.setHousing_addr_en = function(housing_addr_en) {
this.housing_addr_en = housing_addr_en;
};
myMarker.prototype['setHousing_addr_en'] =	myMarker.prototype.setHousing_addr_en;


myMarker.prototype.getHousing_addr_ch = function(){
	return this.housing_addr_ch;
};
myMarker.prototype.setHousing_addr_ch = function(housing_addr_ch) {
this.housing_addr_ch = housing_addr_ch;
};
myMarker.prototype['setHousing_addr_ch'] =	myMarker.prototype.setHousing_addr_ch;


myMarker.prototype.getRes_basic_bw = function(){
	return this.RES_BASIC_BW;
};
myMarker.prototype.setRes_basic_bw = function(res_basic_bw) {
this.RES_BASIC_BW = res_basic_bw;
};
myMarker.prototype['setRes_basic_bw'] =	myMarker.prototype.setRes_basic_bw;

myMarker.prototype.getRes_fttb_bw = function(){
	return this.RES_FTTB_BW;
};
myMarker.prototype.setRes_fttb_bw = function(res_fttb_bw) {
this.RES_FTTB_BW = res_fttb_bw;
};
myMarker.prototype['setRes_fttb_bw'] =	myMarker.prototype.setRes_fttb_bw;

myMarker.prototype.getRes_ftth_bw = function(){
	return this.RES_FTTH_BW;
};
myMarker.prototype.setRes_ftth_bw = function(res_ftth_bw) {
this.RES_FTTH_BW = res_ftth_bw;
};
myMarker.prototype['setRes_ftth_bw'] =	myMarker.prototype.setRes_ftth_bw;

myMarker.prototype.getRes_tv_sd = function(){
	return this.RES_TV_SD;
};
myMarker.prototype.setRes_tv_sd = function(res_tv_sd) {
this.RES_TV_SD = res_tv_sd;
};
myMarker.prototype['setRes_tv_sd'] =	myMarker.prototype.setRes_tv_sd;

myMarker.prototype.getRes_tv_hd = function(){
	return this.RES_TV_HD;
};
myMarker.prototype.setRes_tv_hd = function(res_tv_hd) {
this.RES_TV_HD = res_tv_hd;
};
myMarker.prototype['setRes_tv_hd'] =	myMarker.prototype.setRes_tv_hd;

myMarker.prototype.getBus_basic_bw = function(){
	return this.BUS_BASIC_BW;
};
myMarker.prototype.setBus_basic_bw = function(bus_basic_bw) {
this.BUS_BASIC_BW = bus_basic_bw;
};
myMarker.prototype['setBus_basic_bw'] =	myMarker.prototype.setBus_basic_bw;

myMarker.prototype.getBus_fttb_bw = function(){
	return this.BUS_FTTB_BW;
};
myMarker.prototype.setBus_fttb_bw = function(bus_fttb_bw) {
this.BUS_FTTB_BW = bus_fttb_bw;
};
myMarker.prototype['setBus_fttb_bw'] = myMarker.prototype.setBus_fttb_bw;

myMarker.prototype.getBus_ftth_bw = function(){
	return this.BUS_FTTH_BW;
};
myMarker.prototype.setBus_ftth_bw = function(bus_ftth_bw) {
this.BUS_FTTH_BW = bus_ftth_bw;
};
myMarker.prototype['setBus_ftth_bw'] = myMarker.prototype.setBus_ftth_bw;

myMarker.prototype.getBus_tv_sd = function(){
	return this.BUS_TV_SD;
};
myMarker.prototype.setBus_tv_sd = function(bus_tv_sd) {
this.BUS_TV_SD = bus_tv_sd;
};
myMarker.prototype['setBus_tv_sd'] = myMarker.prototype.setBus_tv_sd;

myMarker.prototype.getBus_tv_hd = function(){
	return this.BUS_TV_HD;
};
myMarker.prototype.setBus_tv_hd = function(bus_tv_hd) {
this.BUS_TV_HD = bus_tv_hd;
};
myMarker.prototype['setBus_tv_hd'] = myMarker.prototype.setBus_tv_hd;

myMarker.prototype.isNow = function(){
	return this._isNow;
};
myMarker.prototype.setNow = function(isNow) {
this._isNow = isNow;
};
myMarker.prototype['setNow'] = myMarker.prototype.setNow;

myMarker.prototype.isNowB = function(){
	return this._isNowB;
};
myMarker.prototype.setNowB = function(isNowB) {
this._isNowB = isNowB;
};
myMarker.prototype['setNowB'] = myMarker.prototype.setNowB;

myMarker.prototype.isNet = function(){
	return this._isNet;
};
myMarker.prototype.setNet = function(isNet) {
this._isNet = isNet;
};
myMarker.prototype['setNet'] = myMarker.prototype.setNet;

myMarker.prototype.isNetB = function(){
	return this._isNetB;
};
myMarker.prototype.setNetB = function(isNetB) {
this._isNetB = isNetB;
};
myMarker.prototype['setNetB'] = myMarker.prototype.setNetB;

myMarker.prototype.isRm = function(){
	return this.IS_RM;
};
myMarker.prototype.setRm = function(IS_RM) {
this.IS_RM = IS_RM;
};
myMarker.prototype['setRm'] = myMarker.prototype.setRm;

myMarker.prototype.isPremier = function(){
	return this.IS_PREMIER;
};
myMarker.prototype.setPremier = function(isNetB) {
this.IS_PREMIER = IS_PREMIER;
};
myMarker.prototype['setPremier'] = myMarker.prototype.setPremier;

myMarker.prototype.getMarkerTitle = function(lang){
	var title_str = "";
	if (lang=="ch"){
		if (this.area_desc_ch && this.area_desc_ch!="" && this.area_desc_ch!="null")
			title_str += (this.area_desc_ch);
		if (this.district_desc_ch && this.district_desc_ch!="" && this.district_desc_ch!="null")
			title_str += (this.district_desc_ch);
		if (this.section_desc_ch && this.section_desc_ch!="" && this.section_desc_ch!="null")
			title_str += (this.section_desc_ch);
		if (this.housing_addr_ch && this.housing_addr_ch!="" && this.housing_addr_ch!="null")
			title_str += (this.housing_addr_ch);
		
	}else{
		if (this.housing_addr_en && this.housing_addr_en!="" && this.housing_addr_en!="null")
			title_str += (this.housing_addr_en+", ");
		if (this.section_desc_en && this.section_desc_en!="" && this.section_desc_en!="null")
			title_str += (this.section_desc_en+", ");
		if (this.district_desc_en && this.district_desc_en!="" && this.district_desc_en!="null")
			title_str += (this.district_desc_en+", ");
		if (this.area_desc_en && this.area_desc_en!="" && this.area_desc_en!="null")
			title_str += (this.area_desc_en+", ");
		if (title_str.length>0)
			title_str = title_str.substring(0, title_str.length-2);
	}
	return title_str;
};
myMarker.prototype['getMarkerTitle'] = myMarker.prototype.getMarkerTitle;

//myMarker.prototype.getServiceList=function(type){
//	var services = [];
//	if (type=="nowB"){
//		services.push(this.BUS_TV_HD);
//		services.push(this.BUS_TV_SD);
//	}
//	if (type=="now"){
//		services.push(this.RES_TV_HD);
//		services.push(this.RES_TV_SD);
//	}
//	if (type=="netB"){
//		services.push(this.BUS_FTTH_BW);
//		services.push(this.BUS_FTTB_BW);
//		services.push(this.BUS_BASIC_BW);
//	}
//	if (type=="net"){
//		services.push(this.RES_FTTH_BW);
//		services.push(this.RES_FTTB_BW);
//		services.push(this.RES_BASIC_BW);
//	}
//};
//myMarker.prototype['getServiceList'] = myMarker.prototype.getServiceList;

myMarker.prototype.isPon = function(){
	if (this.RES_FTTH_BW && this.RES_FTTH_BW!="")
		return "Y";
	else
		return "F";
};
myMarker.prototype['isPon'] = myMarker.prototype.isPon;

myMarker.prototype.getMarker_idx = function(){
	return this.marker_idx;
};
myMarker.prototype.setMarker_idx = function(marker_idx) {
	this.marker_idx = marker_idx;
};
myMarker.prototype['setMarker_idx'] = myMarker.prototype.setMarker_idx;