<%@ include file="/WEB-INF/jsp/imscommon/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="us">
	<head>
		<script type="text/javascript" charset="utf-8" src="js/imsaddressinfo.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.imsautocomplete.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/inputInterceptor.js"></script>
		
		<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<form:form method="POST" name="imsaddressForm" commandName="addressinfo">
			<table width="100%">
				<tr><td width="100%"><div id="line_text">Rollout</div></td> 
				</tr>
			</table>
			<div class="paper_w2">
				<!-- customer block -->
				<table <c:if test="${ImsOrder.channelCd eq 'DS'}">style="display:none;"</c:if>>  
	        	<tr>
	         		<td colspan="6">&nbsp;</td>
	        	</tr>
	        	<tr>
		   			<td class="table_title" colspan="6">Profile Retrieval</td>
	        	</tr>
	        	<tr>
					<th align="left">ID DOC TYPE:</th>
					<td><input type=text id="idDocType" value="${addressinfo.customer.idDocType}"/></td>
					<th align="left">ID DOC NUM:</th>
					<td><input type=text id="idDocNum" value="${addressinfo.customer.idDocNum}"/></td>
				</tr>
		        <tr>
		        	<td colspan="6"><hr/></td>
		        </tr>
		        <tr>
	    	    	<td colspan="6">
	        			<table width="100%" border="0" cellspacing="1" class="contenttext" >
	        			<tr class="personal">
							<th align="left">Title:</th>
							<td><input type=text maxlength="5" id="title" value="${addressinfo.customer.title}"/></td>
							<th align="left">Family Name:</th>
							<td><input type=text maxlength="40" id="lastName" value="${addressinfo.customer.lastName}"/></td>
							<th align="left">Given Name:</th>
							<td><input type=text maxlength="40" id="firstName" value="${addressinfo.customer.firstName}"/></td>
						</tr>
						<tr class="personal">
							<th align="left">Date of Birth:</th>
							<td><form:input maxlength="10" path="dobStr" /></td>
						</tr>
						<tr class="business">
							<th align="left">Company Name:</th>
							<td><input type=text maxlength="40" id="companyName" value="${addressinfo.customer.companyName}"/></td>
							<th align="left">Contact Person Name:</th>
							<td><input type="text" maxlength="40" id="contactPersonName" value="${addressinfo.customer.firstName}"/></td>
						</tr>
	        			</table>
	        		</td>
	        	</tr>    
				<tr>
					<td colspan="6">
		 			<div id="warning_profile_1" class="ui-widget"  style="visibility:hidden">	
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<div id="warning_profile_msg1" class="contenttext" style="font-weight:bold;"></div>
						</div>					
					</div>
					</td>
				</tr>
	        	</table> 
			
				<div id="s_line_text">Installation Address</div> 
				<table>
					<tr>
						<th align="left">Quick Search by SB:</th>
						<td colspan="4" align="left">
							<form:input size="19%"	path="installAddress.quickSearchSB" onfocus="keyDownOnQuickSearchSB()" onkeydown="keyDownOnQuickSearchSB()" onkeyup="keyUpOnQuickSearchSB()" onclick="setCurrentSearchFrom('installAddress.quickSearchSB')" />
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="4"><form:errors path="installAddress.quickSearchSB" cssClass="error" /></td> 
					</tr>
					<tr>
						<td align="right">&nbsp;</td>
						<td colspan="5">
							<span class="contenttext">Please input
								the service boundary to search the address. 
							</span>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="4" id="quickSearchSBMsg" style="color:red"></td>
					</tr>
					
					<tr height="15px"></tr>
				
					<tr>
						<th align="left">Key:</th>
						<td>
							<select id="areaCdSelect" style="width: 150px;"></select> 
		<!-- 					<div id='loaderImagePlaceholder'></div> -->
						</td>
						
						<td>
							<select id='distNoSelect' style="width: 150px;"></select> 
		<!-- 					<div id='loaderImagePlaceholder'></div> -->
						</td>
						
						<td valign="top">
							<select id='sectCdSelect' style="width: 150px;"></select>
		<!-- 					<div id='loaderImagePlaceholder'></div> -->
						</td>
					</tr>
		
					<tr height="15px"></tr>
		
					<tr>
						<th align="left">Quick Search:</th>
						<td colspan="4" align="left" id="addrsearch">
							<form:input size="100%"	path="installAddress.quickSearch" onfocus="keyDownOnQuickSearch()" onkeydown="keyDownOnQuickSearch()" onkeyup="keyUpOnQuickSearch()" onclick="setCurrentSearchFrom('installAddress.quickSearch')" />
						</td>
						<td align="right">
			 				<a class='func_button' onclick="allClear()">Clear</a>  
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="4"><form:errors path="installAddress.quickSearch" cssClass="error" /></td>
					</tr>
					<tr>
						<td align="right">&nbsp;</td>
						<td colspan="5">
							<span class="contenttext">Simply input
								the estate name, block no. or the building name of your
								installation address, separate with comma.<br></span>
								<span class="contenttext">E.g., Block No., Estate, Street, District.
							</span>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="4" id="quickSearchMsg" style="color:red"></td>
					</tr>
		 
					<!-- Flat Floor -->
					<tr>
						<th align="left">Flat/ Room:</th>
						<td><form:input maxlength="5" path="installAddress.unitNo" size="16%" onkeyup="keyUpOnUnitNo()" onblur="blurOnUnitNo()" cssClass="noSymbol"/></td>
			
						<th align="left">Floor:</th>
						<td><form:input maxlength="3" path="installAddress.floorNo" size="16%" onkeyup="keyUpOnFloorNo()" onfocus="keyDownOnFloorNo()" onblur="blurOnFloorNo()" cssClass="noSymbol" onclick="clickOnFloorNo()"/></td>
						<td><form:errors path="installAddress.floorNo" cssClass="error" /></td>
					</tr>
		
					<tr height="15px">
						<th/>
						<td colspan="2" ><span class="installAddress.unitNonoSymbolError error"></span></td>
						<td colspan="2" ><span class="installAddress.floorNonoSymbolError error"></span></td>
					</tr>
		
					<!-- Block Building -->
					<tr>
						<th align="left">Lot No.:</th>
						<td><form:input path="installAddress.hiLotNo" size="16%" readonly="readonly" /></td>
		
						<th align="left">Building/ Estate:</th>
						<td colspan="3">
							<form:input path="installAddress.buildNo" size="50%" readonly="readonly" />
						</td>
					</tr>
		
					<!-- Street No & Name -->
					<tr>
						<th align="left">Street No.:</th>
						<td><form:input path="installAddress.strNo" size="16%" readonly="readonly" /></td>
						
						<th align="left">Street Name:</th>
						<td><form:input path="installAddress.strName" size="16%" readonly="readonly" /></td>
						
						<th align="left">Street Category:</th>
						<td><form:input path="installAddress.strCatDesc" size="16%" readonly="readonly" /></td>
					</tr>
					
					<!-- Section District Area -->
					<tr>
						<th align="left" valign="top">Section:</th>
						<td valign="top">
							<form:input path="installAddress.sectDesc" readonly="readonly" />
							<form:hidden path="installAddress.sectCd" />
						</td>
		
						<th align="left" valign="top">District:</th>
						<td>
							<form:input path="installAddress.distDesc" readonly="readonly" />
							<form:hidden path="installAddress.distNo" /> 
						</td>
		
						<th align="left" valign="top">Area:</th>
						<td>
							<form:input path="installAddress.areaDesc" readonly="readonly" /> 
							<form:hidden path="installAddress.areaCd" /> 
						</td>
					</tr>
				</table>	
					<!-- address info -->
	    	    <table> 
	    	    <tr>
	         		<td colspan="2">&nbsp;</td>
	        	</tr>
	    	    
				<tr>
					<th align="left">Housing Type:</th>
					<td><form:input path="hosType" readonly="" size="100%" /></td>
				</tr>
				<tr>
					<th align="left">Estimated FTTH Earliest SRD:</th>
					<td><form:input path="ponSrd" readonly="readonly" size="100%" /></td>
				</tr>
				<tr>
					<th align="left">Estimated VDSL Earliest SRD:</th>
					<td><form:input path="vdslSrd" readonly="readonly" size="100%" /></td>
				</tr>
				<tr>
					<th align="left">Estimated ADSL Earliest SRD:</th>
					<td><form:input path="adslSrd" readonly="readonly" size="100%" /></td>
				</tr>
				<!-- kinman -->
	<!-- 			Anthony -->
				<tr>
					<th class="PONInstallation" align="left">PON Install Fee:</th>
					<th class="PONActivation" align="left" style="display:none">PON Activation Fee:</th>
					<td><form:input path="ponInstFee" readonly="readonly" size="100%" /></td>
				</tr>
				<tr>
					<th class="VDSLInstallation" align="left">VDSL Install Fee:</th>
					<th class="VDSLActivation" align="left" style="display:none">VDSL Activation Fee:</th>
					<td><form:input path="vdslInstFee" readonly="readonly" size="100%" /></td>
				</tr>
				<tr>
					<th class="ADSLInstallation" align="left">ADSL Install Fee:</th>
					<th class="ADSLActivation" align="left" style="display:none">ADSL Activation Fee:</th>
					<td><form:input path="adslInstFee" readonly="readonly" size="100%" /></td>
				</tr>
				</table>
				<!-- end address info -->		
				<!-- message -->		 													    
	    	    <table width="100%" border="0" cellspacing="1" class="contenttext">
				<tr>
				    <td colspan="3">
				    <div id="brm_vas_desc" class="ui-widget"  style="visibility:hidden">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<div id="brm_vas_desc_msg" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
							</p>		
						</div>
					</div>
					</td>
				</tr>		
				<tr>
					<td colspan="3">
		 			<div id="error_addr_1" class="ui-widget"  style="visibility:hidden">
						<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<div id="error_addr_msg1" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
							</p>
						</div>
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="3">
		 			<div id="error_addr_2" class="ui-widget"  style="visibility:hidden">
						<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<div id="error_addr_msg2" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
							</p>
						</div>
					</div>
					</td>
				</tr>
	
				<tr>
					<td colspan="3">
		 			<div id="warning_addr_1" class="ui-widget"  style="visibility:hidden">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<div id="warning_addr_msg1" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
							</p>
						</div>
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="3">
		 			<div id="warning_addr_2" class="ui-widget"  style="visibility:hidden">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<div id="warning_addr_msg2" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
							</p>
						</div>
					</div>
					</td>
				</tr>
				<tr <c:if test="${ImsOrder.channelCd eq 'DS'}">style="display:none;"</c:if>>  
					<td colspan="3">
		 			<div id="warning_addr_3" class="ui-widget"  style="visibility:hidden">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<div id="warning_addr_msg3" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
							</p>
						</div>
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="3">
		 			<div id="warning_addr_4" class="ui-widget"  style="visibility:hidden">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<div id="warning_addr_msg4" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
							</p>
						</div>
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="3">
		 			<div id="warning_addr_5" class="ui-widget"  style="visibility:hidden">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
							<p>
							<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<div id="warning_addr_msg5" class="contenttext" style="font-weight:bold; margin-left: 1.5em;"></div>
							</p>
						</div>
					</div>
					</td>
				</tr>
				
				</table>
				<!-- end mesage -->
							 
				<table width="100%" border="0" cellspacing="1"> 
					<tr height="15px"></tr>
					<tr>
						<td align="right" colspan="6">
							<div>
								<a id="continueButton" class="button" onClick="javascript:formSubmit();" >Continue &gt;</a> 
							</div> 
						</td>
					</tr>
					<tr height="15px"></tr>
				</table>	
			</div> 
			
			<input type="hidden" id="imsSearchFrom" value="" />
			<input type="hidden" name="currentView" value="addressinfo" />
			<form:hidden path="customer.blacklistInd" />
			<form:hidden path="customer.lob" />
			<form:hidden path="installAddress.serbdyno" />
			<form:hidden path="installAddress.blacklistInd" />
			<form:hidden path="installAddress.strCatCd" />
			<form:hidden path="blacklistCustInfo" />
			<form:hidden path="prevSbNo" />
			<form:hidden path="phInd" />
			<input type="hidden" id="blockageCode" value="N" />
			<input type="hidden" id="isError1" value="N" />
			<input type="hidden" id="isError2" value="N" /> 

		</form:form>
	</body>
</html>
<%@ include file="/WEB-INF/jsp/imscommon/footer.jsp" %>