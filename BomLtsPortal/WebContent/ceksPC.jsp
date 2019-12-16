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
				var queryString = "";
				//var caseBack = false;
				queryString = '<%= request.getQueryString()!= null?request.getQueryString():"" %>';
				
				queryString = decode(queryString);
				var responseCode = requestGet(queryString, "EPayLinkRespCode");
				var cardnum = requestGet(queryString, "TOK");
				var expiryDay = requestGet(queryString, "EX");
				if ( expiryDay != null )
				{
					expiryDay = expiryDay.substring(2) + expiryDay.substring(0 , 2);
				}
				
				var referenceNum = requestGet(queryString, "orderNumber");
				var amt = requestGet(queryString, "Amount");
				var ePayLinkTxId = requestGet(queryString, "EPayLinkTxID");
				var merchantId = '';
				var locale = '';
				var currCode = '';
				var opCode = '';
				var authCode = '';
				var acqOrderId = '';

				if (parent != null && parent != this)
				{
					parent.formSubmit(cardnum, expiryDay, responseCode, queryString, merchantId, referenceNum, amt, locale, currCode,
							opCode, ePayLinkTxId, authCode, acqOrderId);
				}else{
					top.location = "creditpayment.html?"+queryString;
				}
				
				//self.close();
			}
			
			function decode(str) {
			     return unescape(str.replace(/\+/g, " "));
			}
			
			function requestGet(rStr, key)
			{
				var requestStr = "";
				requestStr = rStr + "";
				var searching = true;
				var targetStr = "";
				while ( searching )
				{
					var startPoint = requestStr.search(key);
					if ( startPoint == -1 )
					{
						return null;
					}
					 
					if ( 	startPoint + key.length < requestStr.length 
							&& requestStr.charAt( startPoint + key.length ) == '=' 
							)
					{
						var i = startPoint + key.length + 1;
						while ( i < requestStr.length &&
								requestStr.charAt(i) != '&' )
						{
							targetStr += requestStr.charAt(i);
							i++;
						}
						return targetStr;
						searching = false;
					}
					else if ( startPoint + key.length < requestStr.length )
					{
						requestStr = requestStr.substring( 0, startPoint) + requestStr.substring(startPoint + key.length + 1 );
						searching = true;
					}
					else
					{
						return null;
					}
				}
			}
	</script>
		
	<body onload="onLoadHandler()">
	</body>
	
</html>