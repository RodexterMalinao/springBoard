
<%@ page contentType="text/html;charset=UTF-8"%>
<link rel="stylesheet" href="css/jquery-ui.css">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script src="js/jquery.blockUI.js"></script>
<link type="text/css" href="css/dataTable/table.jui.css"
	rel="stylesheet" />
<style type="text/css">
	#profile td{
		text-align: center; 
	}	
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
		
		.tvlist td, .tvlist th {
			font-size:1.1em;
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
		
		.contenttext {
			font-family: "Verdana", "Arial", "Helvetica", "sans-serif";
			font-size: 12px; 
		}
		.now, .eye {
			font-size: 130%;
		}
</style>

<script>

</script>

<!--################################# tabs #####################################-->
	<div>
		<div >
			<div>
<!-- 				<div id="s_line_text">Existing nowTV campaigns</div> -->
				<c:choose>
				<c:when test='${fn:length(VIMntvinfolist) != 0}'>
				<div style="">
					<table cellpadding="0" cellspacing="0" border="0" class="display paper_no" id="profileChannel" width="100%">
						<thead>
							<tr>
								<th width="15%">campaignCode</th>
								<th width="10%">planCode</th>
								<th width="15%">registeredDate</th>
								<th width="10%">price</th>
								<th width="10%">channelId</th>
								<th width="20%">nameChi</th>
								<th width="20%">nameEng</th>
							</tr>						
							</thead>						
						<tbody>
						<c:forEach var="c" items="${VIMntvinfolist}" varStatus="i">
							<tr valign="top" style="text-align: center; vertical-align: top;">
								<td width="15%">${c.campaignCode}<c:if test='${empty c.campaignCode}'>&nbsp;</c:if></td> 
								<td width="10%">${c.planCode}<c:if test='${empty c.planCode}'>&nbsp;</c:if></td> 
								<td width="15%">${c.registeredDate}<c:if test='${empty c.registeredDate}'>&nbsp;</c:if></td>
								<td width="10%">${c.charge}<c:if test='${empty c.charge}'>&nbsp;</c:if></td>
								<td width="10%">${c.channelId}<c:if test='${empty c.channelId}'>&nbsp;</c:if></td>
								<td width="20%">${c.nameChi}<c:if test='${empty c.nameChi}'>&nbsp;</c:if></td>
								<td width="20%">${c.nameEng}<c:if test='${empty c.nameEng}'>&nbsp;</c:if></td>
							</tr>	
						</c:forEach>
							<tr>
								<th width="15%">&nbsp;</th>
								<th width="10%">&nbsp;</th>
								<th width="15%">&nbsp;</th>
								<th width="10%">${VIMntvinfoPriceSum}</th>
								<th width="10%">&nbsp;</th>
								<th width="20%">&nbsp;</th>
								<th width="20%">&nbsp;</th>
							</tr>
						</tbody>
					</table>
				</div>
				</c:when >
				<c:otherwise>
				<c:choose>
				<c:when test='${fn:length(SOPHIEinfolist.campaigns) != 0}'>
					<div>
						<table>
							<thead>
								<tr>
									<th width="15%">Campaign Code</th>
									<th width="20%">Campaign Description</th>
									<th width="10%">Origin Price</th>
									<th width="10%">Discount Price</th>
									<th width="10%">Effective Date</th>
									<th width="10%">Termination Date</th>
									<th width="10%">Pack Code</th>
									<th width="15%">Pack Description</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="c" items="${SOPHIEinfolist.campaigns}" varStatus="j">
									<tr valign="top" style="text-align: center; vertical-align: top;">
										<td width="15%">${c.campCode}</td>
										<td width="20%">${c.campDesc}</td>
										<td width="10%">$${c.origPrice}</td>
										<td width="10%">$${c.discPrice}</td>
										<td width="10%">${c.effDateFmt}</td>
										<td width="10%">${c.termDateFmt}</td>
										<td colspan="2" valign="top">
											<table width="100%">
												<c:forEach var="d" items="${c.packs}" varStatus="k">
													<tr valign="top" style="text-align: center; vertical-align: top;">
														<td width="50%">${d.packCode}</td>
														<td width="50%">${d.packDesc}</td>
													</tr>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<th width="15%">&nbsp;</th>
									<th width="20%">&nbsp;</th>
									<th width="10%">$${SOPHIEinfolist.orisumNum}</th>
									<th width="10%">$${SOPHIEinfolist.dscsumNum}</th>
									<th width="10%">&nbsp;</th>
									<th width="10%">&nbsp;</th>
									<th width="10%">&nbsp;</th>
									<th width="15%">&nbsp;</th>
								</tr>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
				<div>
					<table>
						<tbody>
							<tr><th>No subscription is found.</th></tr>										
						</tbody>
					</table>
				</div>				
				</c:otherwise>
				</c:choose>
				</c:otherwise>
			</c:choose>

					
			</div>
		</div>
	</div>
<!--################################# tabs #####################################-->