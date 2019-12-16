
<div id="dOrderSelfSrdDiv" style="display: none;">			 
	<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">	
				<tr>
					<td align="left" width="5%">&nbsp;</td>
					<td align="left" width="5%">SRD<span class="contenttext_red">*</span>: </td>
					<td align="left" >				
						<input id="termDOrderSelfSRD" value=""  readonly="readonly"/>                 
					</td>
					<td>&nbsp;</td>	
				</tr>	
	</table>
</div>
<div id="dOrderSelfPickDiv" style="display: none;">
	<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">
		<tbody> 		
		<c:choose>
			<c:when test='${amendOrderImsUI.termDorderSelfReturn ne "Y"}'>
				<tr>
					<td width="5%">&nbsp;</td>
					<td width="95%" id="CollectRequestTD">
						<b>At this moment, Bom order is Not Self Return<span class="contenttext_red"></span></b>
					</td>		
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td width="5%">&nbsp;</td>
					<td width="95%" id="CollectRequestTD">
						<b>At this moment, Bom order is Self Return<span class="contenttext_red"></span></b>
					</td>		
				</tr>
			</c:otherwise>	
		</c:choose>			
	</table>					
	<table width="100%" border="0" cellspacing="1" class="contenttext" bgcolor="#FFFFFF">			
				<tr>
					<td width="5%">&nbsp;</td>
					<td width="95%" id="CollectRequestTD">
						<input type="radio" name="CollectMtd" value="SR" id="CollectMtd"> Self Return <span class="contenttext_red"></span><br>
						<input type="radio" name="CollectMtd" value="FSWC" id="CollectMtd"> Collected By FS with Charge <span class="contenttext_red"></span><br>
						<input type="radio" name="CollectMtd" value="FSWOC" id="CollectMtd"> Collected By FS without Charge <span class="contenttext_red"></span><br>
					</td>		
				</tr>				
			<tr>
				<td width="5%">&nbsp;</td>
				<td width="95%" >
					<span style="color:red; display: none"  id="collectMethodVoid" class="error collectMethodVoid" >Please select collecting method</span>
					<span style="color:red; display: none"  id="notQualified" class="error collectMethodVoid" >Login staff does not have Supervisor right</span>
				</td>
			</tr>
			<tr><td width="5%">&nbsp;</td></tr>			
		</tbody>
	</table>
</div>