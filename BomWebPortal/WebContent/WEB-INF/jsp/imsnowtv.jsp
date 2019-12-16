<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ include file="imsloadingpanel.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>
<%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.ims.dto.ui.NowTVUI,
					com.bomwebportal.ims.dto.ui.ChannelUI,
					com.bomwebportal.ims.dto.ui.NowTVVasUI,
					java.util.*
					"
%>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>





<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	<spring:message code="ims.pcd.nowtv.012"/>
		</td>
	</tr>
</table>


<link href="css/imsds.css" rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/jsp/imssubheader.jsp" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>


<c:choose>
			<c:when test="${nowTVdetailinfo.ntvPricingInd eq 'Y'}">
					<%@ include file="/WEB-INF/jsp/imsnowtvnew.jsp" %>
			</c:when>
			<c:otherwise>
					<%@ include file="/WEB-INF/jsp/imsnowtvold.jsp" %>
			</c:otherwise>
</c:choose>	
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>