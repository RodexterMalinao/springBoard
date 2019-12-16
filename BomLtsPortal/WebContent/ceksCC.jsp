 <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html>

	<head>
		<meta http-equiv="pragma" content="no-cache"> 
		<meta http-equiv="cache-control" content="no-cache">  
		<meta http-equiv="expires" content="0">   
	</head>

	<script language="javascript">
			function onLoadHandler()
			{
				var queryString = '<%= request.getQueryString()!= null?request.getQueryString():"" %>';
				var responseCode = '<%= request.getParameter("ResponseCode")!= null?request.getParameter("ResponseCode"):"" %>';
				var cardnum = '<%= request.getParameter("V5")!= null?request.getParameter("V5"):"" %>';
				var expiryDay = '<%= request.getParameter("V6")!= null?request.getParameter("V6"):"" %>';
				//window.opener.refreshForm(cardnum);		
				//parent.formSubmit(cardnum);
				
				if (parent != null && parent != this)
				{
// 					if ( cardnum==null || cardnum=="" )
// 					{
// 						caseBack = true;
// 					}
					
// 					if ( !caseBack )
// 					{
// 						parent.formSubmit(cardnum, expiryDay, responseCode, queryString);
// 					}
// 					else
// 					{
// 						parent.formSubmitBack();
// 					}
					parent.formSubmitCC(cardnum, expiryDay, responseCode, queryString);
				}else{
					top.location = "creditpayment.html?"+queryString;
				}
				
				//self.close();
			}
	</script>
		
	<body onload="onLoadHandler()">
	</body>
	
</html>