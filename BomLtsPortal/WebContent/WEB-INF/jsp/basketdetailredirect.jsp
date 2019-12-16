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
		
		
		<script language="javascript">
			function onLoadHandler()
			{
				var basketId = "${basketId}";
				if ( parent != null ){
					parent.formSubmit(basketId);
				}
				
				self.close();
			}
		</script>
	</head>
	
	
	
	<body onload="onLoadHandler()">
	</body>
	
</html>