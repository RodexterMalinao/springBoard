<%@page import="java.util.List, net.sf.json.*, com.bomwebportal.dto.*"%>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="offerdetail" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>
<div id="wrapper2">


<%
JSONArray brandListJson = new JSONArray();
if (request.getAttribute("brandList") instanceof List<?>) {
	for (BrandDTO brand : (List<BrandDTO>) request.getAttribute("brandList")) {
		JSONObject json = new JSONObject();
		json.put("brandId", brand.getBrandId());
		json.put("brandName", brand.getBrandName());
		brandListJson.add(json);
	}
}
pageContext.setAttribute("brandListJson", brandListJson);
JSONArray modelListJson = new JSONArray();
if (request.getAttribute("modelListAll") instanceof List<?>) {
	for (ModelDTO model : (List<ModelDTO>) request.getAttribute("modelListAll")) {
		JSONObject json = new JSONObject();
		json.put("modelId", model.getModelId());
		json.put("modelName", model.getModelName());
		json.put("brandId", model.getBrandId());
		modelListJson.add(json);
	}
}
pageContext.setAttribute("modelListJson", modelListJson);
%>
<script type="text/javascript">
var brandListJson = <c:out value="${brandListJson}" escapeXml="false"/>;
var modelListJson = <c:out value="${modelListJson}" escapeXml="false"/>;
function isBlank(str) {
	return str == null || $.trim(str).length == 0;
}
function isDefault(str) {
	return isBlank(str) || str == "NONE";
}
function brandChanged() {
	var selectedBrandId = $("select[name=brand]").val();
	$("select[name=model] option:gt(0)").remove()
	$.each(modelListJson, function(index, model) {
		if (isDefault(selectedBrandId) || model.brandId == selectedBrandId) {
			var $option = $("<option/>").text(model.modelName).val(model.modelId);
			$("select[name=model]").append($option);
		}
	});
}
$(document).ready(function() {
	var selectedBrandId = "<c:out value="${selectedBrandId}"/>";
	var selectedModelId = "<c:out value="${selectedModelId}"/>";
	$.each(brandListJson, function(index, brand) {
		var $option = $("<option/>").text(brand.brandName).val(brand.brandId);
		if (!isDefault(selectedBrandId) && brand.brandId == selectedBrandId) {
			$option.attr("selected", true);
		}
		$("select[name=brand]").append($option);
	});
	$.each(modelListJson, function(index, model) {
		if (isDefault(selectedBrandId) || selectedBrandId == model.brandId) {
			var $option = $("<option/>").text(model.modelName).val(model.modelId);
			if (!isDefault(selectedModelId) && selectedModelId == model.modelId) {
				$option.attr("selected", true);
			}
			$("select[name=model]").append($option);
		}
	});
	$("select[name=brand]").change(brandChanged);
	$("select[name=model]").change(function() {
		$("form[name=handset]").submit();
	});
});
</script>

<form name="handset" method="Post" action="modellist.html?sbuid=${param.sbuid}">

<table width="100%" style="border: 1px grey solid">

	<tr>
	  <td colspan="2" height="150" >
	    <p><span class="title_white_lb">SELECT</span><br>
	    <span class="title_white_l">YOUR</span> 
	    <span class="title_white_lb">HANDSET</span></p>
	   </td>
	</tr>
  <tr>
    <td valign="top" width="200">
    	<div id="sidebar">
	    <p></p>
		<div class="pulldown"> 
			<select name="brand">
				<option value="NONE">-----Select the brand-----</option>
			</select>
		</div>
		
		<p></p>
		<div class="pulldown"> 
			<select name="model">
				<option value="NONE">-----Select the model----</option>
			</select>
		</div>
        <p></p>
		</div>
	</td>
	
	<td align="left">
		<c:forEach items="${handsetDisplayList}" var="handsetDisplay">
			<div class="imgcaption" >
				<!-- right site 4 handset info -->
				<p><img class="imgleft" src="${handsetDisplay.modelImagePath}" width="80" alt="${handsetDisplay.modelName}" /></p>
				<p>${handsetDisplay.modelName}</p>
				<a href="mobiledetail.html?brand=${handsetDisplay.brandId}&model=${handsetDisplay.modelId}&customertier=${customerTier}&baskettype=${baskettype}&callList=${callList}&sbuid=${param.sbuid}"  class="imgdetail">&gt; Details</a>
				<br>
			</div>
		</c:forEach>
	</td>
	
  </tr>
  

</table>
<input type="hidden" name="currentView" value="modellist"/>
<input type="hidden" name="customertier" value= "${customerTier}"/>
<input type="hidden" name="baskettype" value= "${baskettype}"/>
<input type="hidden" name="rptype" value= "${rptype}"/>
<input type="hidden" name="callList" value= "${callList}"/>

</form>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>