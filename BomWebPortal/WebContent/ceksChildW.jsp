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
		<script type="text/javascript" src="js/jquery-1.4.4.js"> </script>
	</head>

	<script language="javascript">
			function onLoadHandler(){							
					var cardnum = '<%= request.getParameter("V5")%>';	
					var expireDate = '<%= request.getParameter("V6")%>';
					var responseCode = '<%= request.getParameter("ResponseCode")%>';
					$.ajax({
						url : "logceks.html?cardnum=" +cardnum,
						type : 'POST',
						dataType : 'json',
						timeout : 60000,
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							if (expireDate == '' || expireDate == 'null'|| expireDate == null)
							{
								window.opener.refreshForm(cardnum);
							}
							else
							{
								window.opener.refreshForm(cardnum, expireDate, responseCode);
							}
							self.close();
						},
						success : function(msg) {
							if (expireDate == '' || expireDate == 'null'|| expireDate == null)
							{
								window.opener.refreshForm(cardnum);
							}
							else
							{
								window.opener.refreshForm(cardnum, expireDate, responseCode);
							}		
							self.close();							
						}
					});
					
			}
	</script>
		
	<body onload="onLoadHandler()">
	
	

	</body>
	
</html>