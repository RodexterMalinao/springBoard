<%@ include file="/WEB-INF/jsp/imscommon/header.jsp" %>
<!doctype html>
<html>
	<head>
		<style>
			#profile td{
				text-align: center; 
			} 
		 
			#col4{
				background-color:#f2f8ff;
				width:95%;
				padding:8px;
			} 
			
					/* table----------------------------*/
				.table_style_sb{
					border-collapse:collapse;
					margin-right:5px;
				}
				.table_style_sb td, .table_style_sb th {
					font-size:1.2em;
					border:1px solid #FFFFFF;
					background-color:#F8F8F8;
					padding:3px 7px 2px 7px;
				}
				.table_style_sb td.itemPrice {
					background-color:#8dcdf4;
					text-align:center;
				}
				.table_style_sb th {
					text-align:center;
					padding-top:2px;
					padding-bottom:1px;
					background-color:#5d9de4 ;
					color:#fff;
				}
				.table_style_sb tr.alt td {
					color:#000;
					background-color:#e8e8e8;
				}
				.table_style_sb tr.alt td.itemPrice {
					background-color:#84c4f0;
					text-align:center;
				}
				.itemHtml ul {margin: 0 0 0 0;}
				.item_title_vas, .item_title_rp, .item_title_fee {
					font-family: "Helvetica", "Verdana", "Arial", "sans-serif";
					font-size: 1.2em;
					font-weight: bold;
				}
				.item_title_vas {
					color:#D58200;
				}
				.item_title_rp {
					color:#007d70;
				}
				.item_title_fee {
					color:#7c5db0;
				}
				.item_detail_wrapper{
					margin:20px;
				}
				 
		 </style>
		 
		 <script src="js/imsvasselect.js"></script>
	</head>
	<body>
	<form:form name="form" commandName="basketdetailinfo" method="POST">
	<div class="paper_w2">	
		<div id="tabs" style="width:98%; height:10%" class="paper_w">
			<div id="tabs-1">
		 			<spring:hasBindErrors name ="model">
						<div class="ui-widget">
				 			<div class="ui-state-error ui-corner-all" style="padding: 0.1em;"> 
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<div class="contenttext" style="font-weight:bold;">
							<form:errors path="*"/></div>
							</p>
							</div> 
						</div>
					 </spring:hasBindErrors>
		
			<c:set var="basket" value="${basketdetailinfo.basketDetailList[0]}"/>
			<c:set var="bvasList" value="${basketdetailinfo.BVasDetailList}"/>
			<c:set var="vasList" value="${basketdetailinfo.vasDetailList}"/>  
					
			<div id="cosContent"> 
				<table width="100%">
						<tbody>  
							<tr>
								<td><c:if test="${not empty basket.imagePath and basket.imagePath ne 'NA' and basket.imagePath ne 'N/A'}"><img src="images/${basket.imagePath}" width="150"/></c:if></td>  	
								<td width="100%">
									<table class="table_style_sb" width="95%"> 
										<colgroup></colgroup>
										<colgroup style="width: 150px; text-align: middle; vertical-align: top"></colgroup>
										<colgroup style="width: 150px; text-align: middle; vertical-align: top"></colgroup>  
										<tbody> 
											<tr>  
												<th>Basket Details</th>  
												<th>Monthly Fixed Term Rate</th>
												<th>Month-to-Month Rate</th>
											</tr>
											<tr>
												<td class="itemHtml"><span class="item_title_fee">${basket.bandwidth}M ${basket.summary}</span><br> 
													<div class="item_detail">
														${basket.itemDetail} 
													</div>
												</td>
												<td class="itemPrice">${basket.mthFixText}</td> 
												<td class="itemPrice">${basket.mthToMthText}</td> 
											</tr> 
											<c:forEach var="v" items="${bvasList}" varStatus="i"> 
												<tr>   
													<td class="itemHtml">
													<input id="paraFlag" class="MDO${v.itemMdoInd}" type="checkbox" name="VASItemBox" value="${v.itemId}" checked/><span class="item_title_rp">${v.VASTitle}</span><br>    
														<div class="item_detail">
																${v.VASDetail} 
														</div>
													</td>
													<td class="itemPrice">${v.mthFixText}</td>
													<td class="itemPrice">${v.mthToMthText}</td> 
												</tr>
											</c:forEach>
										</tbody>
									</table> 
								</td> 
							</tr>
							<tr>
								<td colspan="2">
									<table class="table_style_sb" width="95%">  
										<colgroup style="width: 10px;"></colgroup>
										<colgroup></colgroup>  
										<colgroup style="width: 150px; text-align: middle; vertical-align: top"></colgroup>
										<colgroup style="width: 150px; text-align: middle; vertical-align: top"></colgroup>
										<tr>
											<th colspan="2">Additional VAS</th>
											<th></th>
											<th></th>
										</tr>
										<c:set var="vasTypeDtl" value="default" scope="request"/>
										<c:set var="rownum" value="0" scope="request"/>
										<c:forEach var="vas" items="${vasList}" varStatus="i">
											<c:if test="${vas.VASTypeDtl ne vasTypeDtl}"> 
												<tr> 
													<th colspan="2">${vas.VASTypeDtl}</th>
													<th>Monthly Fixed Term Rate</th>
													<th>Month-to-Month Rate</th>
												</tr>
												<c:set var="rownum" value="0" scope="request"/>
												<c:set var="vasTypeDtl"  value="${vas.VASTypeDtl}" scope="request"/>  
											</c:if>
											<tr <c:if test="${rownum%2 eq 1}">class="alt"</c:if>> 
											<c:set var="rownum" value="${rownum+1}" scope="request"/>   
											<td class="itemHtml" id="r${i.index}"><input id="paraFlag" type="checkbox" name="VASItemBox" value="${vas.itemId}"/> 
											</td>  
												<td class="itemHtml"><span class="item_title_vas">${vas.VASTitle}</span>
													<div id="r${i.index}_d" class="item_detail" style="display:none;">
														 ${vas.VASDetail}
													</div>
												</td>
												<td class="itemPrice">${vas.mthFixText}</td>
												<td class="itemPrice">${vas.mthToMthText}</td>
											</tr>
											<tr>
											</tr>
										</c:forEach>							
									</table>
								</td>
							</tr> 
						</tbody>
					</table>  
			</div>
		</div>
		</div>
		</div>
		<div id="flowButton" style="float:right;margin-right:50px"> 
		   <a class="button submit">Next &gt;</a>
		</div>
		</form:form>
	</body>
</html>
<%@ include file="/WEB-INF/jsp/imscommon/footer.jsp" %>