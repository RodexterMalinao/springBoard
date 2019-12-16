<%@ include file="hkt_header.jsp"%>

<c:choose>
	<c:when test="${pageContext.response.locale == 'en'}">
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?${mapKey}&language=en"></script>
		<!--<script type="text/javascript" src="https://maps.google.com/maps/api/js?v=3.9&sensor=false&language=en"></script>-->
	</c:when>	
	<c:otherwise>
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?${mapKey}&language=zh_tw"></script>
		<!--<script type="text/javascript" src="https://maps.google.com/maps/api/js?v=3.9&sensor=false&language=zh_tw"></script>-->
	</c:otherwise>
</c:choose>
<script type="text/javascript" charset="utf-8" src="./js/map/infobubble.js"></script>
<script type="text/javascript" charset="utf-8" src="./js/map/markermanager_packed.js"></script>
<script type="text/javascript" charset="utf-8" src="./js/map/mymarker.js"></script>

 <script type="text/javascript">
 	var srvType = "${srv}";
 	var srvDesc = "<spring:message code='map.service.desc.${srv}'/>";
 	var srvType1 = "<spring:message code='map.${fn:toLowerCase(srv)}.srvtype1'/>";
 	var eyeSrvType2 = "<spring:message code='map.eye.srvtype2'/>";
 	var eyeSrvType3 = "<spring:message code='map.eye.srvtype3'/>";
 	var eyeSrvType3Form = "<spring:message code='map.eye.srvtype3form'/>";
 	var eyeSrvType3Chat = "<spring:message code='map.eye.srvtype3chat'/>";
 	var nextbtn = "<spring:message code='map.next'/>";
 	var fixLineBubbleLogoSrc = "./images/${lang}/fixline_logo_bubble.png";
 	var fixLineMarkerSrc = "./images/${lang}/fixline_marker.png";
 </script>
 
<script type="text/javascript" charset="utf-8" src="./js/map/lts-map.js?v=<fmt:formatDate pattern='yyyyMMddHH24mmss' value='<%=new java.util.Date()%>' />"></script>


<body onLoad="init()">
<c:if test="${sessionOnlineOrderCapture.serviceTypeInd=='EYE' && sessionOnlineOrderCapture.lang=='zh_HK'}">
	<!--
	Start of DoubleClick Floodlight Tag: Please do not remove
	Activity name of this tag: (New structure) Eye Search_Chi_Eye3 Registration Confirm Address Page Conv.
	URL of the webpage where the tag is expected to be placed: https://shop.hkt.com/lts/registratioconfirmation.html
	This tag must be placed between the <body> and </body> tags, as close as possible to the opening tag.
	Creation Date: 10/20/2017
	-->
	<script type="text/javascript">
		var axel = Math.random() + "";
		var a = axel * 10000000000000;
		document.write('<iframe src="https://962321.fls.doubleclick.net/activityi;src=962321;type=newst0;cat=newst00d;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;ord=' + a + '?" width="1" height="1" frameborder="0" style="display:none"></iframe>');
	</script>
	<noscript>
		<iframe src="https://962321.fls.doubleclick.net/activityi;src=962321;type=newst0;cat=newst00d;dc_lat=;dc_rdid=;tag_for_child_directed_treatment=;ord=1?" width="1" height="1" frameborder="0" style="display:none"></iframe>
	</noscript>
	<!-- End of DoubleClick Floodlight Tag: Please do not remove -->
	
	<!-- BEGIN: Marin Software Tracking Script (Confirmation Page) -->
		<script type='text/javascript'>
		var _mTrack = _mTrack || [];
		_mTrack.push(['addTrans', {
		    currency :'HKD',
		    items : [{
		        convType : 'Eye3_coveragemap_page'
		    }]
		}]);
		_mTrack.push(['processOrders']);
		(function() {
		    var mClientId = 'l2uf8fb490';
		    var mProto = (('https:' == document.location.protocol) ? 'https://' : 'http://');
		    var mHost = 'tracker.marinsm.com';
		    var mt = document.createElement('script'); mt.type = 'text/javascript'; mt.async = true; mt.src = mProto + mHost + '/tracker/async/' + mClientId + '.js';
		    var fscr = document.getElementsByTagName('script')[0]; fscr.parentNode.insertBefore(mt, fscr);
		})();
		</script>
		<noscript>
		<img width="1" height="1" src="https://tracker.marinsm.com/tp?act=2&cid=l2uf8fb490&script=no" />
		</noscript>
	<!-- END: Copyright Marin Software -->
</c:if>

<script type="text/javascript">
var keyword_srh_title = "<spring:message code="map.suggestion"/>";
var keyword_sel_addr = "<spring:message code="map.select_addr"/>";
var keyword_result_no = "<spring:message code="map.noOfResult"/>";

var viewportHeight = $(window).height();

$(document).ready(function() {  
	$("#map_canvas").css("min-height","480px");
	resizeMap();
	//var m_height = $("#middle_content").outerHeight();
	//$("#map_canvas").css("height",m_height-60+"px"); 
	initDistrictPanel();
	$("input[id=quickSearch]").autocomplete("ajaxaddresslookup.html", $("#quickSearchBtn"),  {
		//minChars: 4,
		minChars : 1,
		delay : 600,
		selectFirst : false,
		max : 30,
		matchSubset : false,
		highlight : false,
		//extraParams: {random: (function(){return Math.random()})},
		formatItem : function(row, i, max) {
			return row;
		}
	});
	
	$("#quickSearch").keypress(function(event){
	    if ( event.which == 13 ) {
	        event.preventDefault();
	    }
	});
	
	$("#map_canvas").click(function(){
		$("#quickSearch").blur();
	}); 
	
	$.ajax({
					url : "districtsearch.html?T=AREA&K=",
					type : 'POST',
					dataType : 'json',
					timeout : 5000,
//					error : function(XMLHttpRequest,
//							textStatus, errorThrown) {
//						if (textStatus == 'parsererror') {
//							alert("Your session has been timed out, please re-login.");
//						} else {
//							//alert("Error loading area data!");
//						}
//					},
					success : function(msg) {
						$("#areaSelect").empty();
						$("#areaSelect").removeAttr('disabled');
						$(
								"<option value=''>"
										+ "<spring:message code="map.searchByDistrict.area"/>"
										+ "</option>")
								.appendTo($("#areaSelect"));
						$
								.each(
										eval(msg),
										function(i, item) {
											if (item.resultKey == "${installAddress.areaCd}") {
												$(
														"<option value='" + item.resultKey + "' selected='selected'>"
																+ (lang == "en" ? item.resultEn
																		: item.resultCh)
																+ "</option>")
														.appendTo(
																$("#areaSelect"));
											} else {
												$(
														"<option value='" + item.resultKey + "' >"
																+ (lang == "en" ? item.resultEn
																		: item.resultCh)
																+ "</option>")
														.appendTo(
																$("#areaSelect"));
											}
										});

						$('#areaSelect').trigger("change"); //for assign value
					}
				});

		$("#areaSelect").change(function() {
			srh_lat = null;
			srh_lng = null;
			srh_indx = null;
			if ($("#areaSelect").val() == "") {
				//alert("area empty");
				$("#districtSelect").empty();
				$("#districtSelect").attr('disabled', 'true');
				$("#sectionSelect").empty();
				$("#sectionSelect").attr('disabled', 'true');
				$("#bldgSelect").empty();
				$("#bldgSelect").attr('disabled', 'true');
				/*areaSearchInput = " ";
				districtSearchInput = " ";
				sectionSearchInput = " ";*/
			} else {
				loadDistrict($("#areaSelect").val());
				//areaSearchInput = " " + $("#areaSelect option:selected").text();
			}
		});

		$("#districtSelect").change(function() {
			srh_lat = null;
			srh_lng = null;
			srh_indx = null;
			if (($("#districtSelect").val()) == "") {
				//alert("district empty");
				$("#sectionSelect").empty();
				$("#sectionSelect").attr('disabled', 'true');
				$("#bldgSelect").empty();
				$("#bldgSelect").attr('disabled', 'true');
				/*districtSearchInput = " ";
				sectionSearchInput = " ";*/
			} else {
				loadSection($("#districtSelect").val());
				//districtSearchInput = " " + $("#districtSelect option:selected").text();
			}
		});

		$("#sectionSelect").change(function() {
			srh_lat = null;
			srh_lng = null;
			srh_indx = null;
			if (($("#sectionSelect").val()) == "") {
				//alert("district empty");
				$("#bldgSelect").empty();
				$("#bldgSelect").attr('disabled', 'true');
				/*districtSearchInput = " ";
				sectionSearchInput = " ";*/
			} else {
				loadBldg($("#sectionSelect").val());
				$("#bldgSelect").removeAttr('disabled');
				//sectionSearchInput = " " + $("#sectionSelect option:selected").text();
			}
		});

		$("#bldgSelect").change(
				function() {
					//bldgSearchInput = " " + $("#bldgSelect option:selected").text();
					if (bldg_latlng[$("#bldgSelect").val()]
							&& bldg_latlng[$("#bldgSelect")
									.val()][0]) {
						srh_lat = bldg_latlng[$("#bldgSelect")
								.val()][0];
						srh_lng = bldg_latlng[$("#bldgSelect")
								.val()][1];
						srh_indx = bldg_latlng[$("#bldgSelect")
								.val()][2];
					}
				});

		function loadDistrict(parentid) {
			$
					.ajax({
						url : 'districtsearch.html?T=DIST&K='
								+ parentid,
						type : 'POST',
						dataType : 'json',
						timeout : 5000,
						error : function(XMLHttpRequest,
								textStatus, errorThrown) {
							if (textStatus == 'parsererror') {
								alert("Your session has been timed out, please re-login.");
							} else {
								alert("Error loading district data!");
							}
						},
						success : function(msg) {
							$("#districtSelect").empty();
							$("#districtSelect").removeAttr(
									'disabled');
							$(
									"<option value=''>"
											+ "<spring:message code="map.searchByDistrict.district"/>"
											+ "</option>")
									.appendTo(
											$("#districtSelect"));
							$
									.each(
											eval(msg),
											function(i, item) {
												if (item.resultKey == "${installAddress.distNo}") {
													$(
															"<option value='" + item.resultKey  + "' selected='selected'>"
																	+ (lang == "en" ? item.resultEn
																			: item.resultCh)
																	+ "</option>")
															.appendTo(
																	$("#districtSelect"));
												} else {
													$(
															"<option value='" + item.resultKey  + "' >"
																	+ (lang == "en" ? item.resultEn
																			: item.resultCh)
																	+ "</option>")
															.appendTo(
																	$("#districtSelect"));
												}
											});
							$('#districtSelect').trigger(
									"change");
						}
					});
		}

		function loadSection(parentid) {
			var varual = '';
			varual = 'districtsearch.html?T=DIST_FILTER&K='
					+ parentid;
			$
					.ajax({
						url : varual,
						type : 'POST',
						dataType : 'json',
						timeout : 5000,
						error : function(XMLHttpRequest,
								textStatus, errorThrown) {
							if (textStatus == 'parsererror') {
								alert("Your session has been timed out, please re-login.");
							} else {
								alert("Error loading section data!");
							}
						},
						success : function(msg) {
							section_latlng = [];
							$("#sectionSelect").empty();
							$("#sectionSelect").removeAttr(
									'disabled');
							$(
									"<option value=''>"
											+ "<spring:message code="map.searchByDistrict.estate"/>"
											+ "</option>")
									.appendTo(
											$("#sectionSelect"));
							$
									.each(
											eval(msg),
											function(i, item) {
												if (item.resultKey == "${installAddress.sectCd}") {
													$(
															"<option value='" + item.resultKey + "' selected='selected'>"
																	+ (lang == "en" ? item.resultEn
																			: item.resultCh)
																	+ "</option>")
															.appendTo(
																	$("#sectionSelect"));
												} else {
													$(
															"<option value='" + item.resultKey + "' >"
																	+ (lang == "en" ? item.resultEn
																			: item.resultCh)
																	+ "</option>")
															.appendTo(
																	$("#sectionSelect"));
												}
												section_latlng[item.resultKey] = [
														item.lat,
														item.lng,
														item.indx ];
											});
							$('#sectionSelect').trigger(
									"change");
						}
					});
		}

		function loadBldg(parentid) {
			var varual = '';
			varual = 'districtsearch.html?T=BLDG_FILTER&K='
					+ parentid;
			$
					.ajax({
						url : varual,
						type : 'POST',
						dataType : 'json',
						timeout : 5000,
						error : function(XMLHttpRequest,
								textStatus, errorThrown) {
							if (textStatus == 'parsererror') {
								alert("Your session has been timed out, please re-login.");
							} else {
								alert("Error loading building data!");
							}
						},
						success : function(msg) {
							bldg_latlng = [];
							$("#bldgSelect").empty();
							$("#bldgSelect").removeAttr(
									'disabled');
							$(
									"<option value='' >"
											+ "<spring:message code="map.searchByDistrict.bldg"/>"
											+ "</option>")
									.appendTo($("#bldgSelect"));
							var cnt = 0;
							$
									.each(
											eval(msg),
											function(i, item) {
												if (item.resultKey == "${installAddress.bldgCd}") {
													$(
															"<option value='" + cnt + "' selected='selected'>"
																	+ (lang == "en" ? item.resultEn
																			: item.resultCh)
																	+ "</option>")
															.appendTo(
																	$("#bldgSelect"));
												} else {
													$(
															"<option value='" + cnt + "' >"
																	+ (lang == "en" ? item.resultEn
																			: item.resultCh)
																	+ "</option>")
															.appendTo(
																	$("#bldgSelect"));
												}
												bldg_latlng[cnt] = [
														item.lat,
														item.lng,
														item.indx ];
												cnt++;
											});
							if (cnt == 0) {
								if (section_latlng[$(
										"#sectionSelect").val()][0] != 'null') {
									srh_lat = section_latlng[$(
											"#sectionSelect")
											.val()][0];
									srh_lng = section_latlng[$(
											"#sectionSelect")
											.val()][1];
									srh_indx = section_latlng[$(
											"#sectionSelect")
											.val()][2];
									$("#bldgSelect").attr(
											'disabled', 'true');
								}
							}
							$('#bldgSelect').trigger("change");
						}
					});
		}
	
	
}); 

$(window).resize(function(){
	//var m_height = $("#middle_content").outerHeight();
	//$("#map_canvas").css("height",m_height-60+"px"); 
	resizeMap();
	var viewportHeight = $(window).height();
	var btn = document.getElementById("search_by_district_btn");
	var offset = $(btn).offset();
	var panel = document.getElementById("search_by_district_panel");
	//panel.style.left = offset.left-350+"px";
	//alert($(panel).outerWidth());
	//alert($(panel).offset().left);
	//alert($(btn).outerWidth());
	//alert($(btn).offset().left);
	panel.style.left = btn.style.left;
	$(panel).offset({left: ($(btn).offset().left-$(panel).outerWidth()+$(btn).outerWidth()+13)});
}); 

function resizeMap(){
	var viewportHeight = $(window).height();
	$("#map_canvas").css("height",viewportHeight-200+"px"); 
	$("#content").css("height",viewportHeight-200+"px"); 
}

function show_search_dist() {
	var sPage = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
	var panel = document.getElementById("search_by_district_panel");
	var btn = document.getElementById("search_by_district_btn");
	var offset = $(btn).offset(); 
	
	if (panel.offsetTop ==hide_pos) {
		if (sPage == 'coveragecheck.html') btn.childNodes[0].src=district_btn_on;
		show_dist_action=setTimeout("slideDown(5)", 30);
		//$('#button_clicked').show();
	} else if (panel.offsetTop == show_pos){
		if (sPage == 'coveragecheck.html') btn.childNodes[0].src=district_btn_off;
		show_dist_action=setTimeout("slideDown(-5)", 30);
		//$('#button_clicked').hide();
	}
}

function show_search_dist() {
	var sPage = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1);
	var panel = document.getElementById("search_by_district_panel");
	var btn = document.getElementById("search_by_district_btn");
	var offset = $(btn).offset(); 
	
	if (panel.offsetTop ==hide_pos) {
		if (sPage == 'coveragecheck.html') btn.childNodes[0].src=district_btn_on;
		show_dist_action=setTimeout("slideDown(5)", 30);
		//$('#button_clicked').show();
	} else if (panel.offsetTop == show_pos){
		if (sPage == 'coveragecheck.html') btn.childNodes[0].src=district_btn_off;
		show_dist_action=setTimeout("slideDown(-5)", 30);
		//$('#button_clicked').hide();
	}
}

function slideDown(inc) {
	var obj = document.getElementById("search_by_district_panel");
	if ((inc > 0 && obj.offsetTop < show_pos) || (inc < 0 && obj.offsetTop > hide_pos)){
		obj.style.top = (obj.offsetTop + inc)+"px";
		show_dist_action=setTimeout("slideDown(" + inc + ")", 30);
	};
}

function initDistrictPanel(){
	var outter = document.getElementById("map");
	show_pos = "40";
	var btn = document.getElementById("search_by_district_btn");
	var offset = $(btn).offset(); 
	var panel = document.getElementById("search_by_district_panel");
	var myWidth = 0; 
	//var live_chat = document.getElementById("live_chat");
		myWidth =$(outter).outerWidth();
	panel.style.position="absolute";
	panel.style.top = hide_pos+"px"; 
	panel.style.left = (myWidth - $(panel).outerWidth())+"px"; 
	panel.style.zindex="0";
	//live_chat.style.top = "7px";
	//live_chat.style.left = (myWidth - $(live_chat).outerWidth() - 7)+"px";
	panel.style.display = "block";
	//live_chat.style.display="block";
}

function goto_result() {
	if (srh_lat != null && srh_lng != null) {
		show_search_dist();
		goToMarker(srh_lat, srh_lng, srh_indx);
	} else {
		alert("<spring:message code="map.searchByDistrict.plz_sel"/>");
	}
}

function showoption(type){
	if(type == "eye"){
	    $("#OldCus").show();
	    $("#NewC").show();
	    $("#SrvDN").show();
	}else if(type = "tel"){
//		formSubmit();
	    $("#NewC").show();
	    $("#SrvDN").show();
	}
}

function hideoption(){ 
    $("#OldCus").hide();
    $("#NewC").hide();
    
    }

function updateselected(marker_idx){
	$(".bubbleoutside").remove();
	$("#NewC").remove();
	$("#OldCus").remove();
	$("#SrvDN").remove();
	$(".OldCus").remove();
	$(".SrvDN").remove();
	$("#marker_idx").val(marker_idx);
}

function showSubMenuOptions(id){
	hideSubMenuOptions();
    $("."+id).toggle();
}

function hideSubMenuOptions(){
    $(".SrvDN").hide();
    $(".OldCus").hide();
}

function formSubmit() {
	//submit
	var submitInd = true;
	for(var i = 0; i < markers.length; i++){
		if(markers[i].getMarker_idx() == $("#marker_idx").val()){
			if(markers[i].isRm() == "Y" || markers[i].isPremier() == "Y"){
				marker_idx = $("#marker_idx").val();
				reason = "PREMIER";
				if(srvType == 'EYE' || srvType == 'DEL'){
					service = srvType1;
				}
				submitInd = false;
				showSalesLeadForm(marker_idx, reason, service);
			}else if(markers[i].isCleanedVillage() != "Y"){
				marker_idx = $("#marker_idx").val();
				reason = "NOT_CLEANED_VILLAGE";
				if(srvType == 'EYE' || srvType == 'DEL'){
					service = srvType1;
				}
				submitInd = false;
				showSalesLeadForm(marker_idx, reason, service);
			}
			break;
		};
	}
	if(submitInd){
		document.addressLookupForm.submit();
	};
}

function OldCusSubmit(){
	marker_idx = $("#marker_idx").val();
	reason = "UPGRADE_EYE";
	service = eyeSrvType2;
	showSalesLeadForm(marker_idx, reason, service);
}

function SrvDNSubmit(){
	marker_idx = $("#marker_idx").val();
	if (srvType == 'EYE'){
		reason = "PORTINGDN_EYE"; 
	} else if(srvType == 'DEL'){
		reason = "PORTINGDN_DEL";
	}
	service = eyeSrvType3;
	showSalesLeadForm(marker_idx, reason, service);
}

function showSalesLeadForm(marker_idx, reason, service){
// 	<!-- $.colorbox({innerWidth:800, innerHeight:510, iframe:true, fixed:true, -->
	$.colorbox({innerWidth:800, innerHeight:640, iframe:true, fixed:true,
		href:"salesleadform.html?marker_idx="+marker_idx+"&reason="+reason+"&service="+service, 
		opacity:"0.6"});

}

</script>

<script type="text/javascript" charset="utf-8" src="./js/jquery.imsautocomplete.js"></script>

<form:form id="addressLookupForm" name="addressLookupForm" method="POST" commandName="addressLookupForm" action="addresslookup.html">
<form:hidden id="marker_idx" path="markerIdx"/>
<div id="wrapper">
	<div id="content">
		<div id="searchbar" style="position: relative; font-size: 11px; float: left; margin-left: 40px; padding-bottom: 10px;z-index:2; width:875px;background-color: #FFFFFF;">  
		<!-- <img src ="<spring:message code="reg.map.inputdesc"/>" style="position: relative;float: right;right: 432px;">-->
				<div class="inputdesc inline"><spring:message code="reg.map.inputdesctxt"/></div>
				<div id="search_by_keyword_con" class="inline"> 
					<div id="search_by_keyword_btn">
						<img id="quickSearchBtn" src="./images/OnlineRegistration/search_icon.png" />
					</div>
					<div id="search_by_keyword_txt">
						<input type="text" size="100%" placeholder="<spring:message code="map.searchByKeyword"/>" id="quickSearch"/>
					</div>
				</div> 
				<div id="search_by_district_btn" style="width:90px" class="inline">
					<img id="arrow" src="images/OnlineRegistration/arrow_down_black.png" style="margin-bottom: 2px;float:right;position: relative;top: 1px;"/>
						<img id="button" onclick="show_search_dist()" style="float:right;margin-right: 4px;" src ="./images/${lang}/district_search.png"/>
						<a style="height:30px;overflow:hidden;">
							<img id="button_clicked" style="display:none" onclick="closeDistSch()" src="./images/${lang}/district_search.png"/>
						</a>
				</div>
		</div>
		<div id="upper_content" style="height:20px;"></div>
		<div id="middle_content" style="visibility:hidden; padding-left:30px; float:left; ">
				<div id="map" style="width:890px; height: 100%">
						<div id="map_canvas" style="overflow: hidden; min-height: 469px; position: relative; background-color: rgb(229, 227, 223);"></div>

						<div id="search_by_district_panel" style="display:none;">
						<div style="height:5px;">&nbsp;</div>
						<div class="dist_sel">
							<select id="areaSelect" disabled>
								<option><spring:message code="map.searchByDistrict.area" /></option>
							</select>
						</div>
						<div class="dist_sel_sep">&nbsp;</div>
						<div class="dist_sel">
							<select id="districtSelect">
								<option><spring:message code="map.searchByDistrict.district" /></option>
							</select>
						</div>
						<div class="dist_sel_sep">&nbsp;</div>
						<div class="dist_sel">
							<select id="sectionSelect" disabled>
								<option><spring:message code="map.searchByDistrict.estate" /></option>
							</select> 
						</div>
						<div class="dist_sel_sep">&nbsp;</div>
						<div class="dist_sel" >
							<select id="bldgSelect" disabled>
								<option><spring:message code="map.searchByDistrict.bldg" /></option>
							</select> 
						</div>
						<img id="go_btn" src="<spring:message code="map.searchByDistrict.go_btn"/>" onclick="goto_result();"/>
					</div>
					<div id="map_disclaimer" style="display:none"></div>
				</div>
		</div>
				</div>
	<div id="floating_bar">
		<div class="grad"></div>
		<div id="bottom_content">
			<table width=100% style="bottom: -8pt;position: relative;">  
				<tr> 
					<td width=60%>&nbsp;</td>
					<td width=20%>&nbsp;</td>
					<td width=3% align="center"></td>
					<td width=20% align="center"></td>
				</tr>
			</table>
		</div> 
		<img src="images/${lang}/bottom_bar.png" />
		<span style="font-size: 9pt; position: fixed; text-align: center; width: 950px; bottom: 15px; display: block; z-index: 4;">
			<br><spring:message code="map.browser.warn"/>
		</span>
	</div>

<!--end of wrapper-->
</div>
</form:form>
</body>
