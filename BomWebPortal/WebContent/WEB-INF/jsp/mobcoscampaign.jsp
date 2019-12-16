<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.10.3.js"></script>
<style type="text/css">
.btnRow {background-color: #FFFFFF;}
.legendTitle {color:#7F7F7F; font-weight:bold;}
div .dtlDiv {display:inline-block; width:100%; overflow-x: auto; overflow-y: hidden; white-space: nowrap;}
.clear {margin-bottom: 20px; background-color: #FFFFFF;}
.actionButton {color: #f3f3f3; background:URL(images/bottombut_blue.png) repeat; font-size: 12px; font-weight: bold;
	text-decoration: none; padding-top: 5px; padding-right: 12px; padding-bottom: 5px; padding-left: 12px;
	width: auto; font-family: "Helvetica", "Verdana", "Arial", "sans-serif"; margin-left: 5px; margin-bottom: 10px;
}

.ui-dialog-titlebar { background-color: #ABD078; border:none; height: 25px; 
	-moz-border-radius: 0px; -khtml-border-radius: 0px; -webkit-border-radius: 0px; border-radius: 0px;}
.ui-dialog-titlebar .ui-dialog-title {font-size: medium; margin-left:5px;}
.ui-dialog-titlebar-close {float:right; border:none; outline: 0;}
.ui-dialog .ui-dialog-titlebar-close .ui-button-text {font-size:0px;}
.ui-dialog .ui-dialog-titlebar-close:hover, .ui-dialog .ui-dialog-titlebar-close:focus { padding: 0; }
.ui-dialog-titlebar .ui-state-default, .ui-dialog-titlebar-close .ui-state-default {background-image: none; 
	background-color:transparent; border-width: 0px;}

</style>
<script type="text/javascript">
	$(function() {
		//Campaign Start/End Date
		$('#dateStart').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy',
			maxDate : "+100Y",
			yearRange : "0:+100"
		});
		$('#dateEnd').datepicker({
			beforeShow: function(input) {
		        setTimeout(function() {
		            var buttonPane = $(input)
		                .datepicker("widget")
		                .find(".ui-datepicker-buttonpane");

		            $( "<button>", {
		                text: "Clear",
		                click: function() {
		                    $.datepicker._clearDate(input);
		                }
		            }).appendTo(buttonPane).addClass("ui-state-default ui-priority-primary ui-corner-all");
		        }, 1 );
		    },
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy',
			minDate : $("#dateStart").datepicker("getDate"),
			maxDate : "+100Y",
			yearRange : "0:+100"
		});
		$("#dateStart").change(function(){
			$('#dateEnd').datepicker("option", "minDate", $("#dateStart").datepicker("getDate"));	
		});
		
		//VAS Start/End Date
		$('.Start').datepicker({
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy',
			maxDate : "+100Y",
			yearRange : "0:+100"
		});
		
		$('.End').datepicker({
			beforeShow: function(input) {
		        setTimeout(function() {
		            var buttonPane = $(input)
		                .datepicker("widget")
		                .find(".ui-datepicker-buttonpane");

		            $( "<button>", {
		                text: "Clear",
		                click: function() {
		                    $.datepicker._clearDate(input);
		                }
		            }).appendTo(buttonPane).addClass("ui-state-default ui-priority-primary ui-corner-all");
		        }, 1 );
		    },
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			dateFormat : 'dd/mm/yy',
			maxDate : "+100Y",
			yearRange : "0:+100"
		});
		
	});
	
	$(document).ready(function() {
		clearSearch();
		
		$(".sec").change(function() {
			var cpgTitle = $("input[name=secCpgTitle]").val();
			var cpgName =  $("input[name=secCpgName]").val();
			var handsetDesc = $("input[name=secHandset]").val();
			loadSearchResultList(cpgTitle, cpgName, handsetDesc);
			$('.editTab').hide();
		});
		$('.Start').each(function(){
			$(this).trigger('change');
		});
		
		$('#selectButton').click(function() {
			var camp = $("#secResult").val();
			if(camp == "") return;
			if(camp == "new") {
				var cpgTitle = $("input[name=secCpgTitle]").val();
				var cpgName =  $("input[name=secCpgName]").val();
				if($.trim(cpgTitle) == "" || $.trim(cpgName) == "") {
					alert("Campaign Title and Program Name is necessary to create new campaign");
					$('#actionType').val('');
					return false;
				} else {
					$('#actionType').val('CREATE');
					$('#campaignForm').submit();
				} 
			} else {
				var cpgId = $('#secResult').val();
				$('#curCpgId').val(cpgId);
				$('#actionType').val('SELECT');
				$('#campaignForm').submit();
			}
		});
		
		$('#updButton').click(function() {
			$('#actionType').val('UPDATE');
			$('#campaignForm').submit();
		});
		
		if($('#actionType').val()!= "") {
			$('.editTab').show();
		} else {
			$('.editTab').hide();
		}
		
		if ('${success}' == 'Update Successful') {
			$('#updButton').attr('disabled', true);
		}
	});
	
	function clearSearch() {
		 $("input[name=secCpgTitle]").val('');
		 $("input[name=secCpgName]").val('');
		 $("input[name=secHandset]").val('');
	}
	
	function loadSearchResultList(cpgTitle, cpgName, handsetDesc) {
		$.ajax({				
			url : 'mobcoscampaignajax.html',
			type : 'POST',
			cache : false,
			data : {
				type : 'SearchCampaignList',
				campTitle : cpgTitle,
				campName : cpgName,
				handsetDesc : handsetDesc,
			},
			dataType : "json",
			timeout : 5000,
			error : function() {
				alert('Error loading Campaign Search Result List!');
			},
			success : function(data) {
				$('#secResult option').remove();
				$.each(eval(data), function(i, item) {
					$("<option value='" + item.campaignId + "'>"
							+ item.campaignTitle + " - " + item.campaignName
							+ "</option>").appendTo("#secResult");
				});	
				$("<option value='new'>"
						+ "Create New Campaign"
						+ "</option>").appendTo("#secResult");
			}
		});
	}
	
	function genBasketId(index) {
		$('#curBasketSeq').val(index + 1);
		$('#actionType').val('GEN_BASKET_ID');
		$('#campaignForm').submit();
	}
	
	function turnActiveInd(index) {
		$('#curBasketSeq').val(index + 1);
		if($("#genButton_" + index).length == 0) {
			if($('#activeCheck_' + index).is(':checked')) {
				$('#activeInd_' + index).val('Y');
				$('#actionType').val('ACTIVE_BASKET');
				$('#campaignForm').submit();
			} else {
				$('#activeInd_' + index).val('N');
				$('#actionType').val('DEACTIVE_BASKET');
				$('#campaignForm').submit();
			}
		} else {
			if($('#activeCheck_' + index).is(':checked')) {
				$('#activeCheck_' + index).removeAttr('checked');
			}
		}
	}
	
	function setEndDate(index) {
		$('#dtlEndDate_' + index).datepicker("option", "minDate", $('#dtlStartDate_' + index).datepicker("getDate"));	
	}
	
	function addBasketClick() {
		var addBasketURL = "addcampaignbasket.html?campId=${mobCosCampaign.cpgHdrDTO.campaignId}";
		$('#addBasketDialog').html('<iframe style="border: 0px; " src="' + addBasketURL + '" width="100%" height="100%"></iframe>')
        .dialog({
            autoOpen: false,
            modal: true,
            height: 500,
            width: 1000,
            title: "Add Basket",
            open: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog || ui).hide(); }
        });
		$('#addBasketDialog').dialog('open');
	}
	
	function previewBtnClick(sourceBasketID) {
		var previewBasketURL = "previewcampaigndtl.html?basketId=" + sourceBasketID;
		$('#addBasketDialog').html('<iframe style="border: 0px; " src="' + previewBasketURL + '" width="100%" height="100%"></iframe>')
        .dialog({
            autoOpen: false,
            modal: true,
            height: 800,
            width: 1000,
            title: "Preview Basket " + sourceBasketID
        });
		$('#addBasketDialog').dialog('open');
	}
	
	function vasBtnClick(basketID) {
		var vasBasketURL = "editcampaignvas.html?basketId=" + basketID;
		$('#addBasketDialog').html('<iframe style="border: 0px; " src="' + vasBasketURL + '" width="100%" height="100%"></iframe>')
        .dialog({
            autoOpen: false,
            modal: true,
            height: 800,
            width: 1000,
            title: "VAS for Basket " + basketID
        });
		$('#addBasketDialog').dialog('open');
	}
</script>

<h2 class="table_title" style="font-size: medium; margin: 0">Campaign Management</h2>
<form:form method="POST" id="campaignForm" name="campaignForm" commandName="mobCosCampaign">
<div id="addBasketDialog"></div>
<fieldset>
<legend class="legendTitle">Search / Create Campaign</legend>
<table width="100%" class="contenttext">
	<tr>
		<td class="orderSearchLabel">Campaign:</td>
		<td><form:input path="secCpgTitle" cssClass="sec" size="100"/></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Program Name:</td>
		<td><form:input path="secCpgName" cssClass="sec" size="100"/></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Target customer/base:</td>
		<td><form:input path="secHandset" cssClass="sec" size="100"/></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Result:</td>
		<td><form:select path="secResult" id="secResult">
				<form:option value="new" label="Create New Campaign" />
			</form:select>
			<input type="submit" value="Select" id="selectButton"></td>
	</tr>
</table>
</fieldset>
<div class="clear"></div>
<div class="clear"></div>
<fieldset class="editTab">
<legend class="legendTitle">Edit Campaign (Campaign ID: ${mobCosCampaign.curCpgId}): </legend>
<table width="100%" class="contenttext">
	<tr>
		<td class="orderSearchLabel">Campaign:</td>
		<td><form:input path="cpgHdrDTO.campaignTitle" size="100"/></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Program Name:</td>
		<td><form:input path="cpgHdrDTO.campaignName" size="100"/></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Target customer/base:</td>
		<td><form:input path="cpgHdrDTO.handsetDesc" size="100"/></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Start Date:</td>
		<td><form:input id="dateStart" path="cpgHdrDTO.effStartDate" readonly="true"/></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">End Date:</td>
		<td><form:input id="dateEnd" path="cpgHdrDTO.effEndDate" readonly="true"/></td>
	</tr>
</table>

<div class="dtlDiv">
<div class="clear"></div>
<table width="100%">
	<tr class="btnRow" style="background-color:transparent;">
		<td style="text-align:left;">
		<span class="actionButton" onclick="addBasketClick();" >Add Basket</span>
		</td>
		<td><span class="error"><c:out value="${success}"/></span><br/><form:errors path="*" cssClass="error"/></td>
		<td style="text-align:right;">
		<span class="actionButton" id="updButton" >Save Campaign</span>
		</td>
	</tr>
</table>
<div class="clear"></div>
<table width="100%" class="cpgTable">
	<thead>
		<tr class="table_title" style="background-color: #ABD078;">
			<th></th>
			<th>Basket Description</th>
			<th>Tier</th>
			<th>Products</th>
			<th>Effective Date</th>
			<th>Active</th>
			<th>VAS</th>
			<th>Basket ID</th>
			<th>Action</th>
		</tr>
	</thead>
	<c:forEach items="${mobCosCampaign.cpgDtlDTOList}" var="dtl" varStatus="dtlStatus">
	<tr style="background-color: ${dtlStatus.count % 2 == 1 ? '#F0F0F0' : '#FFFFFF'}">
		<td><form:label path="cpgDtlDTOList[${dtlStatus.index}].campaignBasketSeq">${dtl.campaignBasketSeq}</form:label></td>
		<td><form:textarea cols="30" rows="5" path="cpgDtlDTOList[${dtlStatus.index}].basketDesc"/></td>
		<td><form:select path="cpgDtlDTOList[${dtlStatus.index}].tier">
				<form:option value="" label="Select"/>
				<form:option value="1" label="1"/>
				<form:option value="2" label="2"/>
				<form:option value="3" label="3"/>
				<form:option value="4" label="4"/>
				<form:option value="5" label="5"/>
				<form:option value="6" label="6"/>
				<form:option value="7" label="7"/>
			</form:select></td>
		<td><table>
			<tr><td style="text-align:right; font-weight:bold;">RP:</td><td><form:label path="cpgDtlDTOList[${dtlStatus.index}].ratePlan">${dtl.ratePlan}</form:label></td></tr>
			<tr><td style="text-align:right; font-weight:bold;">Bundle: </td><td><form:label path="cpgDtlDTOList[${dtlStatus.index}].bundle">${dtl.bundle}</form:label></td></tr>
			<tr><td style="text-align:right; font-weight:bold;">Contract: </td><td><form:label path="cpgDtlDTOList[${dtlStatus.index}].contract">${dtl.contract}</form:label></td></tr>
			<tr><td style="text-align:right; font-weight:bold;">Handset: </td><td><form:label path="cpgDtlDTOList[${dtlStatus.index}].handset">${dtl.handset}</form:label></td></tr>
		</table></td>
		<td><table>
			<tr><td style="text-align:right; font-weight:bold;">Start: </td><td><form:input cssClass="datepicker Start" id="dtlStartDate_${dtlStatus.index}" path="cpgDtlDTOList[${dtlStatus.index}].effStartDate" readonly="true" onchange="setEndDate(${dtlStatus.index})"/></td></tr>
			<tr><td style="text-align:right; font-weight:bold;">End: </td><td><form:input cssClass="datepicker End" id="dtlEndDate_${dtlStatus.index}" path="cpgDtlDTOList[${dtlStatus.index}].effEndDate" readonly="true"/></td></tr>
		</table></td>
		<td><form:hidden path="cpgDtlDTOList[${dtlStatus.index}].activeInd" id="activeInd_${dtlStatus.index}" />
			<c:if test="${not empty dtl.basketId}">
			<input type="checkbox" onclick="turnActiveInd(${dtlStatus.index})" id="activeCheck_${dtlStatus.index}"
				<c:if test="${dtl.activeInd == 'Y'}">checked</c:if>/>
			</c:if>
		</td>
		<td>
			<c:if test="${not empty dtl.basketId}">
				<span class="actionButton" onclick="vasBtnClick(${dtl.basketId});" >VAS</span>
			</c:if>
		</td>
		<td>
			<span style="font-size:80%; font-weight:bold">(Source: <form:label path="cpgDtlDTOList[${dtlStatus.index}].sourceBasketId">${dtl.sourceBasketId}</form:label>)<br/></span>
			<c:if test="${not empty dtl.basketId}">
				<form:label path="cpgDtlDTOList[${dtlStatus.index}].basketId">${dtl.basketId}</form:label>
			</c:if>
			<c:if test="${empty dtl.basketId}">
				<input type="submit" value="Generate" id="genButton_${dtlStatus.index}"
					onclick="genBasketId(${dtlStatus.index})">
			</c:if>
		</td>
		<td><span class="actionButton" onclick="previewBtnClick(${empty dtl.basketId ? dtl.sourceBasketId : dtl.basketId});" >Preview</span></td>
	</tr>
	</c:forEach>
	<tfoot>
	
	</tfoot>
</table></div></fieldset>

<form:hidden path="actionType" id="actionType"/>
<form:hidden path="curCpgId" id="curCpgId"/>
<form:hidden path="curBasketSeq" id="curBasketSeq"/>

</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>