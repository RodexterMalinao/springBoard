<%@ include file="/WEB-INF/jsp/header.jsp" %>

<%-- Define exclude list in left panel, and will be included in right panel --%>
<jsp:useBean id="leftPanelExclude" class="java.util.HashMap" />
<c:set target="${leftPanelExclude}" property="35" value="Customer Search" />

<style type="text/css">
#wrapper { padding: 60px 25px 40px 25px }
#header_inner p { margin-top: 0; margin-bottom: 0; font-size: 30px }
.func_list { padding: 63px 5px 0 5px }
.func_list .preset { float: right }
.func_list .nextbutton { display: inline-block }
.func_list input { width: 24%; margin-bottom: 20px }
.fallout_order_count { color: red }
 #IMSDSacq {cursor:pointer;}
</style>

<script type="text/javascript">
$(document).ready(function() {
	$(".func_list input[type=button]").click(function() {
		window.location.href = $(this).attr("alt");
	});
	
	$("#location").attr('maxlength','20');
	
	$("#IMSDSacq").click(function(){
		saveSalesType(); 
	});
	
	var staffid =   "${user.username}";
	
	$.ajax({
		url : 'imsalertcountajax.html?username='+staffid,
		type : 'GET',
		dataType : 'json',
		timeout : 25000,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		     if(textStatus=='parsererror'){
				alert("Your session has been timed out, please re-login."); 
		     } else { 
		    	 alert("Get IMS Alert Count Error!");
		     }
		     return false; 
		},
		success : function(msg) { 
				$.each(eval(msg), function(i, item) {
					//alert( i );
					var arr = JSON.stringify(item);
					var obj = jQuery.parseJSON(arr);
					var span = document.getElementById('ims_alert');
					
						$(obj).each(function(i,val){
						    $.each(val,function(k,v){
						         $("#ims_alert").text('('+v+')'); 
						    });
					});
				});
			 
			//window.location.replace("customerinformation.html"); 
		}
	});
	
	$.ajax({
		url : 'ltsalertcountajax.html?username='+staffid,
		type : 'GET',
		dataType : 'json',
		timeout : 25000,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		     if(textStatus=='parsererror'){
				alert("Your session has been timed out, please re-login."); 
		     } else { 
		    	 alert("Get LTS Alert Count Error!");
		     }
		     return false; 
		},
		success : function(msg) { 
				$.each(eval(msg), function(i, item) {
					//alert( i );
					var arr = JSON.stringify(item);
					var obj = jQuery.parseJSON(arr);
					var span = document.getElementById('lts_alert');
					
						$(obj).each(function(i,val){
						    $.each(val,function(k,v){
						         $("#lts_alert").text('('+v+')'); 
						    });
					});
				});
			 
			//window.location.replace("customerinformation.html"); 
		}
	});
	
});



function saveSalesType(callback) {
	var salesType = $( "input:checked[id='salesSP']" ).val();
    var location =  $( "input[id='location']" ).val();
    var staffid =   "${user.username}";
    
    if(!$( "input[id='salesSP']" ).is(':checked')){
    	alert("Please choose sales type!");
    	return false;
    }    
   
    if($( "input[id='location']" ).val().length == 0){
    	alert("Please input location!");
    	return false;
    }
    
	$.ajax({
		url : 'imsdsajax.html?st='+salesType+'&lo='+location+'&sId='+staffid,
		type : 'GET',
		dataType : 'json',
		timeout : 25000,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		     if(textStatus=='parsererror'){
				alert("Your session has been timed out, please re-login."); 
		     } else { 
		    	 alert("Loading Direct Sales Info Error!");
		     }
		     return false; 
		},
		success : function(msg) { 
			$.each(eval(msg), function(i, item) {
				//alert("Location and Sales Type successfully saved."); 
			});
			 
			window.location.replace("customerinformation.html"); 
		}
	});
}



</script>

<div id="header_inner">
	<p class="title_white_l">Hi, <b><c:out value="${user.username}"/></b></p>
	<p class="title_white_l">Welcome to the</p>
	<p class="title_white_l">
	<c:choose>
	
	<c:when test='${ims_direct_sales eq true and (user.channelCd == "QCC" or user.channelCd == "VQA")}'> 
		PCCW QC Express Centre
	</c:when>
	<c:when test='${ims_direct_sales eq true}'>  
		PCCW Direct Sales Express Centre
	</c:when>
	<c:when test='${user.channelId == 1}'>
		PCCW SHOP Sales Express Centre
	</c:when>
	<c:when test='${user.channelId == 3}'>
		PCCW Premier Sales Express Centre
	</c:when>
	<c:when test='${user.channelId == 2 and user.channelCd=="SBO"}'>
		Online Sales Express Centre
	</c:when>
	<c:when test='${user.channelId == 10 or user.channelId == 11}'>
		Direct Sales Express Centre
	</c:when>
	<c:when test='${user.channelId == 3}'>
		HKT PREMIER Sales Express Centre
	</c:when>
	<c:otherwise>
		Call Centre Sales Express Centre ${ims_direct_sales}
	</c:otherwise> 
	</c:choose>
	</p>
</div>


<%-- <c:if test='${user.channelId == 12 or user.channelId == 13}'> --%>
<c:if test='${ims_direct_sales eq true and (user.channelCd != "QCC" and user.channelCd != "VQA")}'>
	<div>
		<form:radiobutton path="user.salesType" id="salesSP" value="Survey Point" /> Survey Point
		<form:radiobutton path="user.salesType" id="salesSP" value="Door Knocked"/> Door Knocked
		<form:radiobutton path="user.salesType" id="salesSP" value="Roadshow"/> Roadshow
		<form:radiobutton path="user.salesType" id="salesSP" value="Appointment"/> Appointment
	</div>
	<div>
	    <br>Location: 
		<input type="text" id ="location" name="location" value="${user.location}"/>
<!-- 		<button onclick="saveSalesType()">submit</button>  -->
	</div>
</c:if>


<div class="func_list">
	<!-- <c:out value="${channelFlow}"/> -->
	<!-- right panel -->
	<!-- display #35 on the right only -->
	<div class="preset">
	
	<c:forEach items="${maintFuncInfoList}" var="mfi" varStatus="status">
		<c:choose>
		<c:when test="${not empty leftPanelExclude[mfi.maintId]}">
			<!-- CSS_SALES -->
			<c:when test = "${mfi.maintId == '21'}">
				<a class="nextbutton" id="IMSDSacq" ">
						<c:out value="${mfi.funcName}"/>
						<c:if test="${mfi.maintId == '31' && user.falloutOrderCount > 0}">
							<span class="fallout_order_count">(<c:out value="${user.falloutOrderCount}"/>)</span>
						</c:if>
						<c:out value="${user.falloutOrderCount}"/>
						<!-- added by karen -->
						<c:if test="${mfi.maintId == '31a' ||mfi.maintId == '27a' || mfi.maintId == '63a'}">
							<span class="fallout_order_count">(<c:out value="${user.ltsAlertCount}"/>)</span>
						</c:if>
						<c:if test="${mfi.maintId == '31b' ||mfi.maintId == '27b' || mfi.maintId == '63b'}">
								<span class="fallout_order_count">(<c:out value="${user.imsAlertCount}"/>)</span>
						</c:if>
						<c:if test="${mfi.maintId == '55' && user.reviewOrderCount > 0}">
							<span class="fallout_order_count">(<c:out value="${user.reviewOrderCount}"/>)</span>
						</c:if>
					</a>
				</c:when>
				<c:otherwise>
					<a class="nextbutton" href="<c:url value="${mfi.funcHtml}"/>">
						<c:out value="${mfi.funcName}"/>
						<c:if test="${mfi.maintId == '31' && user.falloutOrderCount > 0}">
							<span class="fallout_order_count">(<c:out value="${user.falloutOrderCount}"/>)</span>
						</c:if>
						<c:out value="${user.falloutOrderCount}"/>
						<!-- added by karen -->
						<c:if test="${mfi.maintId == '31a' ||mfi.maintId == '27a' || mfi.maintId == '63a'}">
							<span class="fallout_order_count">(<c:out value="${user.ltsAlertCount}"/>)</span>
						</c:if>
						<c:if test="${mfi.maintId == '31b' ||mfi.maintId == '27b' || mfi.maintId == '63b'}">
								<span class="fallout_order_count">(<c:out value="${user.imsAlertCount}"/>)</span>
						</c:if>
						<c:if test="${mfi.maintId == '55' && user.reviewOrderCount > 0}">
							<span class="fallout_order_count">(<c:out value="${user.reviewOrderCount}"/>)</span>
						</c:if>
					</a>
				</c:otherwise>	
		</c:when>
		</c:choose>
	</c:forEach>
	</div>
	
	

	<!-- left panel -->
	<div class="default">
	<c:forEach items="${maintFuncInfoList}" var="mfi" varStatus="status">
		<c:choose>
		<c:when test = "${mfi.maintId == '21'}">
				<a class="nextbutton" id="IMSDSacq" value="${mfi.funcHtml}">
						<c:out value="${mfi.funcName}"/>
						<c:if test="${mfi.maintId == '31' && user.falloutOrderCount > 0}">
							<span class="fallout_order_count">(<c:out value="${user.falloutOrderCount}"/>)</span>
						</c:if>
						<c:out value="${user.falloutOrderCount}"/>
						<!-- added by karen -->
						<c:if test="${mfi.maintId == '31a' ||mfi.maintId == '27a' || mfi.maintId == '63a'}">
							<span class="fallout_order_count">(<c:out value="${user.ltsAlertCount}"/>)</span>
						</c:if>
						<c:if test="${mfi.maintId == '31b' ||mfi.maintId == '27b' || mfi.maintId == '63b'}">
								<span class="fallout_order_count">(<c:out value="${user.imsAlertCount}"/>)</span>
						</c:if>
						<c:if test="${mfi.maintId == '55' && user.reviewOrderCount > 0}">
							<span class="fallout_order_count">(<c:out value="${user.reviewOrderCount}"/>)</span>
						</c:if>
					</a>
				</c:when>
		<c:otherwise>
		   <c:choose>
			<c:when test="${not empty leftPanelExclude[mfi.maintId]}"></c:when>
			<c:otherwise>
				<a class="nextbutton" href="<c:url value="${mfi.funcHtml}"/>">
					<c:out value="${mfi.funcName}"/>
					<c:if test="${mfi.maintId == '31' && user.falloutOrderCount > 0}">
						<span class="fallout_order_count">(<c:out value="${user.falloutOrderCount}"/>)</span>
					</c:if>
					<c:if test="${mfi.maintId == '31b' ||mfi.maintId == '27b' || mfi.maintId == '63b'}">
						<span id="ims_alert"  class="fallout_order_count" ><img src="./images/imsalertloader.gif" style="width: 10px;top: 1px;position: relative;left: 3px;"/></span> 
					</c:if>
					<c:if test="${mfi.maintId == '31a' ||mfi.maintId == '27a' || mfi.maintId == '63a'}"> 
						<span id="lts_alert"  class="fallout_order_count" ><img src="./images/imsalertloader.gif" style="width: 10px;top: 1px;position: relative;left: 3px;"/></span> 
					</c:if>
					
					<c:if test="${mfi.maintId == '55' && user.reviewOrderCount > 0}">
					<span class="fallout_order_count">(<c:out value="${user.reviewOrderCount}"/>)</span>
					</c:if>
				</a>
			</c:otherwise>
			</c:choose>
		</c:otherwise>
		</c:choose>
	</c:forEach>
	<!-- added by andy--> 
<!-- 	<a id="qcAssign" class="nextbutton" href="dsqcimsorderenquiry.html?_q=1">QC Assignment</a> -->
<!-- 	<a id="qcProcess" class="nextbutton" href="imsdsqcprocess.html?_q=1">QC Process</a> -->
<!-- 	<a id="qcEnquiry" class="nextbutton" href="imsqccomordersearch.html">QC Enquiry</a> -->
<!-- 	<a id="qcStaffAdmin" class="nextbutton" href="dsimsstaffadmin.html">Staff Admin</a> -->
	</div>
</div>



<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>