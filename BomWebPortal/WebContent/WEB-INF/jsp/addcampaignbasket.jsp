<%@ include file="/WEB-INF/jsp/header.jsp"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript">
$.urlParam = function(name){
    var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results[1] || 0;
};
$(document).ready(function() {
	$('#rpSelect').change(function() {
		var rp = $('#rpSelect').val();
		if(rp == "") return;
		loadBundleList(rp);
	});
	$('#bundleSelect').change(function() {
		var rp = $('#rpSelect').val();
		var bundle = $('#bundleSelect').val();
		if(rp == "" || bundle == "") return;
		loadContractList(rp, bundle);
	});
	
	$('#contractSelect').change(function() {
		var rp = $('#rpSelect').val();
		var bundle = $('#bundleSelect').val();
		var contract = $('#contractSelect').val();
		if(rp == "" || bundle == "" || contract == "") return;
		loadHandsetList(rp, bundle, contract);
		loadBasketList(rp, bundle, contract);
	});
	
	$('#basketSelect').change(function() {
		if($('#basketSelect').val() == "") {
			$('#addButton').hide();
		} else {
			$('#addButton').show();
			$('#basketDesc').val($('#basketSelect option:selected').text());
		}
	});
	$('#addButton').click(function(e) {
		if($('#basketSelect').val() == "") {
			 var createInd = confirm("No new campaign basket will be created, would you like to continue?" + 
					 "\n(please select basket to create a new one)");
			    if (!createInd) return false;
		}
		/*window.opener.document.getElementById('curCpgId').value = $.urlParam('campId');
		window.opener.document.getElementById('actionType').value = "SELECT";
		window.opener.$("#campaignForm").submit();*/
		
		parent.document.getElementById('curCpgId').value = $.urlParam('campId');
		parent.document.getElementById('actionType').value = "ADDBASKET";
		parent.$("#campaignForm").submit();
		parent.$('#addBasketDialog').dialog('close');
	});
});
function loadBundleList(rp){
	$('#contractSelect option').remove();
	$("<option value=''>"
			+ "Select.."
			+ "</option>").appendTo("#contractSelect");
	
	$('#hsSelect option').remove();
	$("<option value=''>"
			+ "Select.."
			+ "</option>").appendTo("#hsSelect");
	
	$('#basketSelect option').remove();
	$("<option value=''>"
			+ "Select.."
			+ "</option>").appendTo("#basketSelect");
	$('#addButton').hide();
	$.ajax({				
		url : 'addcampaignbasketajax.html',
		type : 'POST',
		cache : false,
		data : {
			type : 'getBundle',
			ratePlan : rp,
		},
		dataType : "json",
		timeout : 5000,
		error : function() {
			alert('Error loading Bundle List!');
		},
		success : function(data) {
			$('#bundleSelect option').remove();
			$("<option value=''>"
					+ "Select.."
					+ "</option>").appendTo("#bundleSelect");
			$.each(eval(data), function(i, item) {
				$("<option value='" + item.codeId + "'>"
						+ item.codeDesc
						+ "</option>").appendTo("#bundleSelect");
			});
		}
	});
}
function loadContractList(rp, bundle){
	$('#hsSelect option').remove();
	$("<option value=''>"
			+ "Select.."
			+ "</option>").appendTo("#hsSelect");
	
	$('#basketSelect option').remove();
	$("<option value=''>"
			+ "Select.."
			+ "</option>").appendTo("#basketSelect");
	$('#addButton').hide();
	$.ajax({				
		url : 'addcampaignbasketajax.html',
		type : 'POST',
		cache : false,
		data : {
			type : 'getContract',
			ratePlan : rp,
			bundle: bundle,
		},
		dataType : "json",
		timeout : 5000,
		error : function() {
			alert('Error loading Contract List!');
		},
		success : function(data) {
			$('#contractSelect option').remove();
			$("<option value=''>"
					+ "Select.."
					+ "</option>").appendTo("#contractSelect");
			$.each(eval(data), function(i, item) {
				$("<option value='" + item.codeId + "'>"
						+ item.codeDesc
						+ "</option>").appendTo("#contractSelect");
			});
		}
	});
}
function loadHandsetList(rp, bundle, contract){
	$('#basketSelect option').remove();
	$("<option value=''>"
			+ "Select.."
			+ "</option>").appendTo("#basketSelect");
	$('#addButton').hide();
	$.ajax({				
		url : 'addcampaignbasketajax.html',
		type : 'POST',
		cache : false,
		data : {
			type : 'getHandset',
			ratePlan : rp,
			bundle: bundle,
			contract: contract,
		},
		dataType : "json",
		timeout : 5000,
		error : function() {
			alert('Error loading Handset List!');
		},
		success : function(data) {
			$('#hsSelect option').remove();
			$("<option value=''>"
					+ "Select.."
					+ "</option>").appendTo("#hsSelect");
			$.each(eval(data), function(i, item) {
					if($.trim(item.codeId).length > 0) {
						$("<option value='" + item.codeId + "'>"
								+ item.codeDesc
								+ "</option>").appendTo("#hsSelect");
					}
			});
		}
	});
}
function loadBasketList(rp, bundle, contract){
	$.ajax({				
		url : 'addcampaignbasketajax.html',
		type : 'POST',
		cache : false,
		data : {
			type : 'getBasket',
			ratePlan : rp,
			bundle: bundle,
			contract: contract,
		},
		dataType : "json",
		timeout : 5000,
		error : function() {
			alert('Error loading Basket List!');
		},
		success : function(data) {
			$('#basketSelect option').remove();
			$("<option value=''>"
					+ "Select.."
					+ "</option>").appendTo("#basketSelect");
			$.each(eval(data), function(i, item) {
				$("<option value='" + item.codeId + "'>"
						+ item.codeDesc
						+ "</option>").appendTo("#basketSelect");
			});
		}
	});
}
</script>
<form:form method="POST" name="addCampaignBasketForm" commandName="addCampaignBasket">
<span class="error"><br/>WARNING: Add Basket will discard unsaved change in Campaign.  <br/>Please save Campaign changes before adding basket.</span>
<h3 class="table_title" style="font-size: medium; margin: 0">Create new Basket</h3>
<table>
	<tr>
		<td class="orderSearchLabel" width="30%">Rate Plan:</td>
		<td><select id="rpSelect">
			<option value="">Select..</option>
			<c:forEach items="${rpList}" var="rp" varStatus="rpStatus">
				<option value="${rp.codeId}">${rp.codeDesc}</option>
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Bundle:</td>
		<td><select id="bundleSelect">
			<option value="">Select..</option>
		</select></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Contract:</td>
		<td><select id="contractSelect">
			<option value="">Select..</option>
		</select></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Handset:</td>
		<td><select id="hsSelect">
			<option value="">Select..</option>
		</select></td>
	</tr>
	<tr>
		<td class="orderSearchLabel">Result Basket:</td>
		<td><form:select path="basketId" id="basketSelect">
				<form:option value="" label="Select.."/>
			</form:select>
			<input type="submit" value="Add Basket" id="addButton" style="display:none;">
		</td>
	</tr>
</table>
<form:hidden path="basketDesc" id="basketDesc"/>
</form:form>