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
</style>

<script type="text/javascript">
$(document).ready(function() {
	$(".func_list input[type=button]").click(function() {
		window.location.href = $(this).attr("alt");
	});
	
	function redirectHttps() {
		var href = window.location.href;
		var httpsRedirect = href.indexOf("isRedirect");
		if (httpsRedirect > 0) {
			var protocol = window.location.protocol;
			if (protocol.indexOf("https") < 0) {
				var qIdx = href.indexOf("?");
				var qStr = href.substring(qIdx);
				var newHref = href.replace(qStr,"").replace("http","https");
				window.location.assign(newHref);
			}
		}
	}
	
	redirectHttps();
});
</script>
<c:if test="${param.isRedirect == null}">
<div id="header_inner">
	<p class="title_white_l">Hi, <b><c:out value="${user.username}"/></b></p>
	<p class="title_white_l">Welcome to the</p>
	<p class="title_white_l">
	<c:choose>
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
		Call Centre Sales Express Centre
	</c:otherwise>
	</c:choose>
	</p>
</div>

<div class="func_list">
	<!-- <c:out value="${channelFlow}"/> -->
	<!-- right panel -->
	<!-- display #35 on the right only -->
	<div class="preset">
	<c:forEach items="${maintFuncInfoList}" var="mfi" varStatus="status">
		<c:choose>
		<c:when test="${not empty leftPanelExclude[mfi.maintId]}">
			<!-- CSS_SALES -->

			<a class="nextbutton" href="<c:url value="${mfi.funcHtml}"/>">
				<c:out value="${mfi.funcName}"/>
				<c:if test="${mfi.maintId == '31' && user.falloutOrderCount > 0}">
					<span class="fallout_order_count">(<c:out value="${user.falloutOrderCount}"/>)</span>
				</c:if>
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
		</c:choose>
	</c:forEach>
	</div>
	
	<!-- left panel -->
	<div class="default">
	<c:forEach items="${maintFuncInfoList}" var="mfi" varStatus="status">
		<c:choose>
		<c:when test="${channelFlow == 'CCS_MAINT'}">
			<input type="button" value="<c:out value="${mfi.funcName}"/>" alt="<c:url value="${mfi.funcHtml}"/>">
			<c:if test="${status.count % 4 == 0}">
				<div style="clear:both"></div>
			</c:if>
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
			</c:choose>
		</c:otherwise>
		</c:choose>
	</c:forEach>
	</div>
</div>
</c:if>

<%---------------------------------------  end content -------------------------------------------%>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>