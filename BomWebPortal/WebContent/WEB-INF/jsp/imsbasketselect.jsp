<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ include file="imsloadingpanel.jsp" %>
<%-- <%@ include file="imsvalidwindow.jsp" %> --%>
<%@ page import="			
					org.springframework.web.servlet.support.RequestContextUtils,
					com.bomwebportal.ims.dto.ImsBasketDTO,
					com.bomwebportal.ims.dto.ui.OrderImsUI,
					com.bomwebportal.ims.constant.ImsConstants,
					java.util.*
					"
%>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.10.custom.css" rel="stylesheet" />
<link href="css/ims.css" rel="stylesheet" type="text/css">
<style type="text/css">
	/*demo page css*/
	/*body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 50px;}*/
	.demoHeaders { margin-top: 2em; }
	#dialog_link {padding: .4em 1em .4em 20px;text-decoration: none;position: relative;}
	#dialog_link span.ui-icon {margin: 0 5px 0 0;position: absolute;left: .2em;top: 50%;margin-top: -8px;}
	ul#icons {margin: 0; padding: 0;}
	ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
	ul#icons span.ui-icon {float: left; margin: 0 4px;}
</style>

<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.10.custom.min.js"></script>

<script type="text/javascript">

	$(function(){
	
		// Tabs
		$('#tabs').tabs();
		
		$("[class='submitAnchor']").click(function(){
			submitShowLoading();
		});
		
		$("[class='preInstSubmitAnchor']").click(function(event){
			event.preventDefault();
			var self = this;
			var preInstSubmitBox = new imsSBModalBox({
				width: "80%", 
				height: "auto",
				maxHeight: "350px",
				title: "", 
				text: document.getElementById("preInstSubmitAnchor_Msg").innerHTML, 
				ButtonYtext: "OK", 
				ButtonNtext: "",
				centerBox: "Y",
				onClose: function() {
					window.location.href = self.href;
					submitShowLoading();//redirect after this box is closed
				}
			});
			preInstSubmitBox.openBox();
			//alert('Pre-Installation Service Plan (with Commitment Period):\n'
			//		+'Service Description / Entitlements:\n'
			//		+'- Only available to eligible customers subscribing for NETVIGATOR Services. \n'
			//		+'- The NETVIGATOR Services installation must be completed within 30 days from the date of this Application.\n'
			//		+'- The NETVIGATOR Services will be installed (but the Services will not be activated for use) at your Service Installation Address on the Target Installation Date. Now TV services and Residential Telephone Line Services (if applicable) will be installed and activated for use on the Target Installation Date. \n'
			//		+'- The Target Commencement Date of all the Services will commence at the later date (which must be at least 14 days from the installation date but not more than 12 months from the date of this Application), upon which the NETVIGATOR Services, Extra Services (such as F-Secure Safe Anywhere services) and New Media services (if applicable) will be activated for use. If the Target Commencement Date is left blank in this Application, we shall assign a date at our discretion, which normally will be around 12 months from the date of this Application. \n'
			//		+'- If you cancel or terminate the Services or your NETVIGATOR Contract before installation of the Services for whatever reason, you are required to pay us the Application Cancellation Charge. You are required to pay us the Early Termination Charge and other Cancellation Charges (if any) specified for all such Services if you terminate a Service after service installation date (despite the Service may not have been activated and cannot be used, and whether or not the Commencement Period has commenced) for whatever reason, and any prepayment will not be refunded to you.  \n'
			//		+'- The Commitment Period and the billing of all the Services will commence on the Target Commencement Date, unless HKT advises otherwise. \n'
			//		+'\n'
			//		+'\u9810\u5148\u5b89\u88dd\u5bec\u983b\u670d\u52d9\u8a08\u5283 (\u542b\u627f\u8afe\u671f)\uff1a\n'
			//		+'\n'
			//		+'\u670d\u52d9\u8aaa\u660e / \u5167\u5bb9\uff1a\n'
			//		+'- \u53ea\u9069\u7528\u65bc\u7533\u8acb\u7db2\u4e0a\u884c\u670d\u52d9\u7684\u5408\u8cc7\u683c\u5ba2\u6236\u3002\n'
			//		+'- \u9808\u65bc\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a0830\u65e5\u5167\u5b8c\u6210\u5b89\u88dd\u3002\n'
			//		+'- \u7db2\u4e0a\u884c\u670d\u52d9\u5c07\u65bc\u9810\u8a08\u5b89\u88dd\u65e5\u671f\u5b89\u88dd\u65bc\u5ba2\u6236\u7684\u670d\u52d9\u5b89\u88dd\u5730\u5740\uff08\u4f46\u670d\u52d9\u4e0d\u6703\u88ab\u555f\u52d5\uff0c\u56fa\u5c1a\u672a\u53ef\u4f9b\u4f7f\u7528\uff09\u3002Now TV\u670d\u52d9\u548c\u5bb6\u5c45\u96fb\u8a71\u7dda\u670d\u52d9\uff08\u5982\u9069\u7528\uff09\u5c07\u88ab\u5b89\u88dd\u4e26\u88ab\u555f\u52d5\uff0c\u4ee5\u4fbf\u5728\u9810\u8a08\u5b89\u88dd\u65e5\u671f\u8d77\u53ef\u4f9b\u4f7f\u7528\u3002\n'
			//		+'- \u6240\u6709\u670d\u52d9\u7684\u9810\u8a08\u751f\u6548\u65e5\u5c07\u65bc\u7a0d\u5f8c\u624d\u958b\u59cb\uff08\u4f46\u5fc5\u9808\u8981\u81ea\u5b89\u88dd\u65e5\u671f\u8d77\u8a08\u81f3\u5c1114\u5929\uff0c\u4f46\u4e0d\u53ef\u8d85\u904e\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a08\u768412\u500b\u6708\uff09\uff0c\u5c46\u6642\u7db2\u4e0a\u884c\u670d\u52d9\u3001\u984d\u5916\u670d\u52d9\uff08\u4f8b\u5982F-Secure Safe Anywhere\u9632\u79a6\u670d\u52d9\uff09\u53ca\u65b0\u8b00\u9ad4\u670d\u52d9\uff08\u5982\u9069\u7528\uff09\u5c07\u88ab\u555f\u52d5\u4ee5\u4f9b\u4f7f\u7528\u3002\u5982\u672c\u7533\u8acb\u66f8\u6c92\u6709\u586b\u5beb\u9810\u8a08\u751f\u6548\u65e5\uff0c\u672c\u516c\u53f8\u5c07\u81ea\u884c\u91d0\u5b9a\u9810\u8a08\u751f\u6548\u65e5\uff0c\u4e00\u822c\u6703\u65bc\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a08\u768412\u500b\u6708\u5f8c\u81ea\u52d5\u751f\u6548\u3002\n'
			//		+'- \u5982\u5ba2\u6236\u65bc\u670d\u52d9\u5b89\u88dd\u65e5\u524d\u4e0d\u8ad6\u4efb\u4f55\u539f\u56e0\u7d42\u6b62\u670d\u52d9\u6216\u7db2\u4e0a\u884c\u5408\u7d04\uff0c\u60a8\u9808\u7e73\u4ed8\u7533\u8acb\u53d6\u6d88\u8cbb\u7528\u3002\u5982\u5ba2\u6236\u65bc\u5b89\u88dd\u670d\u52d9\u5f8c\uff08\u5373\u4f7f\u670d\u52d9\u5c1a\u672a\u88ab\u555f\u52d5\uff0c\u5c1a\u672a\u53ef\u4f9b\u4f7f\u7528\uff1b\u59d1\u52ff\u8ad6\u627f\u8afe\u671f\u958b\u59cb\u4e86\u8207\u5426\uff09\uff0c\u4e0d\u8ad6\u4efb\u4f55\u539f\u56e0\u800c\u7d42\u6b62\u670d\u52d9\uff0c\u60a8\u9808\u7e73\u4ed8\u63d0\u65e9\u7d42\u6b62\u8cbb\u7528\u4ee5\u53ca\u5176\u4ed6\u6709\u95dc\u8a72\u7b49\u670d\u52d9\u7684\u53d6\u6d88\u8cbb\u7528\uff08\u5982\u6709\uff09\u3002\u4efb\u4f55\u9810\u4ed8\u6b3e\u9805\u4e0d\u6703\u9000\u9084\u7d66\u5ba2\u6236\u3002\n'
			//		+'- \u9664\u975e\u672c\u516c\u53f8\u53e6\u884c\u901a\u77e5\uff0c\u5426\u5247\u6240\u6709\u670d\u52d9\u7684\u627f\u8afe\u671f\u4ee5\u53ca\u8cec\u55ae\u6536\u8cbb\u6703\u65bc\u9810\u8a08\u751f\u6548\u65e5\u8d77\u958b\u59cb\u3002\n');
		});
		
		$("[class='preInstSubmitAnchor googleRouter']").click(function(event){
			event.preventDefault();
			var self = this;
			var preInstSubmitBox = new imsSBModalBox({
				width: "80%", 
				height: "auto",
				maxHeight: "350px",
				title: "", 
				text: document.getElementById("preInstSubmitAnchor_Msg").innerHTML, 
				ButtonYtext: "OK", 
				ButtonNtext: "",
				centerBox: "Y"
			});
			var googleRouterBox = new imsSBModalBox({
				width: "80%", 
				height: "auto",
				maxHeight: "350px",
				title: "", 
				text: document.getElementById("preInstSubmitAnchor_googleRouter_Msg").innerHTML, 
				ButtonYtext: "OK", 
				ButtonNtext: "",
				centerBox: "Y",
				onClose: function() {
					window.location.href = self.href;
					submitShowLoading();//redirect after this box is closed
				}
			});
			preInstSubmitBox.openBox();
			googleRouterBox.openBox();
			//alert('Pre-Installation Service Plan (with Commitment Period):\n'
			//		+'Service Description / Entitlements:\n'
			//		+'- Only available to eligible customers subscribing for NETVIGATOR Services. \n'
			//		+'- The NETVIGATOR Services installation must be completed within 30 days from the date of this Application.\n'
			//		+'- The NETVIGATOR Services will be installed (but the Services will not be activated for use) at your Service Installation Address on the Target Installation Date. Now TV services and Residential Telephone Line Services (if applicable) will be installed and activated for use on the Target Installation Date. \n'
			//		+'- The Target Commencement Date of all the Services will commence at the later date (which must be at least 14 days from the installation date but not more than 12 months from the date of this Application), upon which the NETVIGATOR Services, Extra Services (such as F-Secure Safe Anywhere services) and New Media services (if applicable) will be activated for use. If the Target Commencement Date is left blank in this Application, we shall assign a date at our discretion, which normally will be around 12 months from the date of this Application. \n'
			//		+'- If you cancel or terminate the Services or your NETVIGATOR Contract before installation of the Services for whatever reason, you are required to pay us the Application Cancellation Charge. You are required to pay us the Early Termination Charge and other Cancellation Charges (if any) specified for all such Services if you terminate a Service after service installation date (despite the Service may not have been activated and cannot be used, and whether or not the Commencement Period has commenced) for whatever reason, and any prepayment will not be refunded to you.  \n'
			//		+'- The Commitment Period and the billing of all the Services will commence on the Target Commencement Date, unless HKT advises otherwise. \n'
			//		+'\n'
			//		+'\u9810\u5148\u5b89\u88dd\u5bec\u983b\u670d\u52d9\u8a08\u5283 (\u542b\u627f\u8afe\u671f)\uff1a\n'
			//		+'\n'
			//		+'\u670d\u52d9\u8aaa\u660e / \u5167\u5bb9\uff1a\n'
			//		+'- \u53ea\u9069\u7528\u65bc\u7533\u8acb\u7db2\u4e0a\u884c\u670d\u52d9\u7684\u5408\u8cc7\u683c\u5ba2\u6236\u3002\n'
			//		+'- \u9808\u65bc\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a0830\u65e5\u5167\u5b8c\u6210\u5b89\u88dd\u3002\n'
			//		+'- \u7db2\u4e0a\u884c\u670d\u52d9\u5c07\u65bc\u9810\u8a08\u5b89\u88dd\u65e5\u671f\u5b89\u88dd\u65bc\u5ba2\u6236\u7684\u670d\u52d9\u5b89\u88dd\u5730\u5740\uff08\u4f46\u670d\u52d9\u4e0d\u6703\u88ab\u555f\u52d5\uff0c\u56fa\u5c1a\u672a\u53ef\u4f9b\u4f7f\u7528\uff09\u3002Now TV\u670d\u52d9\u548c\u5bb6\u5c45\u96fb\u8a71\u7dda\u670d\u52d9\uff08\u5982\u9069\u7528\uff09\u5c07\u88ab\u5b89\u88dd\u4e26\u88ab\u555f\u52d5\uff0c\u4ee5\u4fbf\u5728\u9810\u8a08\u5b89\u88dd\u65e5\u671f\u8d77\u53ef\u4f9b\u4f7f\u7528\u3002\n'
			//		+'- \u6240\u6709\u670d\u52d9\u7684\u9810\u8a08\u751f\u6548\u65e5\u5c07\u65bc\u7a0d\u5f8c\u624d\u958b\u59cb\uff08\u4f46\u5fc5\u9808\u8981\u81ea\u5b89\u88dd\u65e5\u671f\u8d77\u8a08\u81f3\u5c1114\u5929\uff0c\u4f46\u4e0d\u53ef\u8d85\u904e\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a08\u768412\u500b\u6708\uff09\uff0c\u5c46\u6642\u7db2\u4e0a\u884c\u670d\u52d9\u3001\u984d\u5916\u670d\u52d9\uff08\u4f8b\u5982F-Secure Safe Anywhere\u9632\u79a6\u670d\u52d9\uff09\u53ca\u65b0\u8b00\u9ad4\u670d\u52d9\uff08\u5982\u9069\u7528\uff09\u5c07\u88ab\u555f\u52d5\u4ee5\u4f9b\u4f7f\u7528\u3002\u5982\u672c\u7533\u8acb\u66f8\u6c92\u6709\u586b\u5beb\u9810\u8a08\u751f\u6548\u65e5\uff0c\u672c\u516c\u53f8\u5c07\u81ea\u884c\u91d0\u5b9a\u9810\u8a08\u751f\u6548\u65e5\uff0c\u4e00\u822c\u6703\u65bc\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a08\u768412\u500b\u6708\u5f8c\u81ea\u52d5\u751f\u6548\u3002\n'
			//		+'- \u5982\u5ba2\u6236\u65bc\u670d\u52d9\u5b89\u88dd\u65e5\u524d\u4e0d\u8ad6\u4efb\u4f55\u539f\u56e0\u7d42\u6b62\u670d\u52d9\u6216\u7db2\u4e0a\u884c\u5408\u7d04\uff0c\u60a8\u9808\u7e73\u4ed8\u7533\u8acb\u53d6\u6d88\u8cbb\u7528\u3002\u5982\u5ba2\u6236\u65bc\u5b89\u88dd\u670d\u52d9\u5f8c\uff08\u5373\u4f7f\u670d\u52d9\u5c1a\u672a\u88ab\u555f\u52d5\uff0c\u5c1a\u672a\u53ef\u4f9b\u4f7f\u7528\uff1b\u59d1\u52ff\u8ad6\u627f\u8afe\u671f\u958b\u59cb\u4e86\u8207\u5426\uff09\uff0c\u4e0d\u8ad6\u4efb\u4f55\u539f\u56e0\u800c\u7d42\u6b62\u670d\u52d9\uff0c\u60a8\u9808\u7e73\u4ed8\u63d0\u65e9\u7d42\u6b62\u8cbb\u7528\u4ee5\u53ca\u5176\u4ed6\u6709\u95dc\u8a72\u7b49\u670d\u52d9\u7684\u53d6\u6d88\u8cbb\u7528\uff08\u5982\u6709\uff09\u3002\u4efb\u4f55\u9810\u4ed8\u6b3e\u9805\u4e0d\u6703\u9000\u9084\u7d66\u5ba2\u6236\u3002\n'
			//		+'- \u9664\u975e\u672c\u516c\u53f8\u53e6\u884c\u901a\u77e5\uff0c\u5426\u5247\u6240\u6709\u670d\u52d9\u7684\u627f\u8afe\u671f\u4ee5\u53ca\u8cec\u55ae\u6536\u8cbb\u6703\u65bc\u9810\u8a08\u751f\u6548\u65e5\u8d77\u958b\u59cb\u3002\n');	
			//alert('Reminder for Home Networking Wireless Service King Of Coverage - Google Wifi Solution (Basic) :\n\n' 
			//	    +' - The installation of The Home Networking Wireless Service King Of Coverage- Google Wifi Solution (Basic) will not be on the same date with the installation of your NETVIGATOR Broadband Services. \n'
			//		+' - Customer will receive a service notice to arrange 2nd installation appointment for the Home Networking Wireless Service King Of Coverage- Google Wifi Solution (Basic). \n'
			//		+' - The service should be installed within 2 months after the NETVIGATOR Broadband Service has been activated in order to enjoy the offer. \n'
			//		+'\n'
			//		+'\u5BB6\u5C45\u7121\u7DDA\u7DB2\u7D61\u670D\u52D9\u8A08\u5283(\u8986\u84CB\u738B \u2013 Google Wifi\u57FA\u672C\u65B9\u6848:\n\n'
			//		+'- \u5BB6\u5C45\u7121\u7DDA\u7DB2\u7D61\u670D\u52D9\u8A08\u5283(\u8986\u84CB\u738B \u2013 Google Wifi\u57FA\u672C\u65B9\u6848)\u548C\u7DB2\u4E0A\u884C\u5BEC\u983B\u670D\u52D9\u7684\u5B89\u88DD\u4E0D\u6703\u70BA\u540C\u4E00\u5929\u3002\n'
			//		+'- \u5BA2\u6236\u5C07\u65BC\u5176\u7DB2\u4E0A\u884C\u5BEC\u983B\u670D\u52D9\u751F\u6548\u5F8C\u6536\u5230\u901A\u77E5\u5B89\u6392\u4E0A\u9580\u5B89\u88DD\u5BB6\u5C45\u7121\u7DDA\u7DB2\u7D61\u670D\u52D9\u8A08\u5283(\u8986\u84CB\u738B \u2013 Google Wifi\u57FA\u672C\u65B9\u6848)\n'
			//		+'- \u4EE5\u4E0A\u670D\u52D9\u9700\u65BC\u7DB2\u4E0A\u884C\u5BEC\u983B\u670D\u52D9\u751F\u6548\u5F8C2\u500B\u6708\u5167\u5B8C\u6210\u5B89\u88DD\uFF0C\u65B9\u53EF\u4FDD\u7559\u512A\u60E0\u3002\n');
		});
		
		$("[class='preUseSubmitAnchor']").click(function(event){
			event.preventDefault();
			var self = this;
			var preUseSubmitBox = new imsSBModalBox({
				width: "80%", 
				height: "auto",
				maxHeight: "350px",
				title: "", 
				text: document.getElementById("preUseSubmitAnchor_Msg").innerHTML, 
				ButtonYtext: "OK", 
				ButtonNtext: "",
				centerBox: "Y",
				onClose: function() {
					window.location.href = self.href;
					submitShowLoading();//redirect after this box is closed
				}
			});
			preUseSubmitBox.openBox();
			//alert('Pre-Use Service Plan (with Commitment Period):\n'
			//		+'Service Description / Entitlements:\n'
			//		+'- Only available to eligible customers subscribing for NETVIGATOR Services.\n'
			//		+'- The NETVIGATOR Services installation must be completed within 30 days from the date of this Application.\n'
			//		+'- The NETVIGATOR Services, Now TV services and Residential Telephone Line Services (if applicable) will be installed and activated for use at your Service Installation Address on the Target Installation Date.\n'
			//		+'- The Target Commencement Date of all the Services will commence at the later date (which must be at least 14 days from the installation date but not more than 12 months from the date of this Application), upon which Extra Services (such as F-Secure Safe Anywhere services) and New Media services (if applicable) will be activated for use. If the Target Commencement Date is left blank in this Application, we shall assign a date at our discretion, which normally will be around 12 months from the date of this Application.\n'
			//		+'- If you cancel or terminate the Services or your NETVIGATOR Contract before installation of the Services for whatever reason, you are required to pay us the Application Cancellation Charge. You are required to pay us the Early Termination Charge and other Cancellation Charges (if any) specified for all such Services if you terminate a Service after service installation and activation date for whatever reason, and any prepayment will not be refunded to you.  \n'
			//		+'-The Commitment Period and the billing of all the Services will commence on the Target Commencement Date (despite the Services have been activated for use from the service installation date), unless HKT advises otherwise.\n'
			//		+'\n'
			//		+'\u9810\u5148\u4eab\u7528\u5bec\u983b\u670d\u52d9\u8a08\u5283 (\u542b\u627f\u8afe\u671f)\uff1a\n'
			//		+'\n'
			//		+'\u670d\u52d9\u8aaa\u660e / \u5167\u5bb9\uff1a\n'
			//		+'- \u53ea\u9069\u7528\u65bc\u7533\u8acb\u7db2\u4e0a\u884c\u670d\u52d9\u7684\u5408\u8cc7\u683c\u5ba2\u6236\u3002\n'
			//		+'- \u9808\u65bc\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a0830\u65e5\u5167\u5b8c\u6210\u5b89\u88dd\u3002\n'
			//		+'- \u7db2\u4e0a\u884c\u670d\u52d9\u3001Now TV\u670d\u52d9\u548c\u5bb6\u5c45\u96fb\u8a71\u7dda\u670d\u52d9\uff08\u5982\u9069\u7528\uff09\u5c07\u65bc\u9810\u8a08\u5b89\u88dd\u65e5\u671f\u5b89\u88dd\u65bc\u5ba2\u6236\u7684\u670d\u52d9\u5b89\u88dd\u5730\u5740\uff0c\u4ee5\u4f9b\u4f7f\u7528\u3002\n'
			//		+'- \u6240\u6709\u670d\u52d9\u7684\u9810\u8a08\u751f\u6548\u65e5\u5c07\u65bc\u7a0d\u5f8c\u624d\u958b\u59cb\uff08\u4f46\u5fc5\u9808\u8981\u81ea\u5b89\u88dd\u65e5\u671f\u8d77\u8a08\u81f3\u5c1114\u5929\uff0c\u4f46\u4e0d\u53ef\u8d85\u904e\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a08\u768412\u500b\u6708\uff09\uff0c\u5c46\u6642\u984d\u5916\u670d\u52d9\uff08\u4f8b\u5982F-Secure Safe Anywhere\u9632\u79a6\u670d\u52d9\uff09\u53ca\u65b0\u8b00\u9ad4\u670d\u52d9\uff08\u5982\u9069\u7528\uff09\u5c07\u88ab\u555f\u52d5\u4ee5\u4f9b\u4f7f\u7528\u3002\u5982\u672c\u7533\u8acb\u66f8\u6c92\u6709\u586b\u5beb\u9810\u8a08\u751f\u6548\u65e5\uff0c\u672c\u516c\u53f8\u5c07\u81ea\u884c\u91d0\u5b9a\u9810\u8a08\u751f\u6548\u65e5\uff0c\u4e00\u822c\u6703\u65bc\u672c\u7533\u8acb\u66f8\u65e5\u671f\u8d77\u8a08\u768412\u500b\u6708\u5f8c\u81ea\u52d5\u751f\u6548\u3002\n'
			//		+'- \u5982\u5ba2\u6236\u65bc\u670d\u52d9\u5b89\u88dd\u65e5\u524d\u4e0d\u8ad6\u4efb\u4f55\u539f\u56e0\u7d42\u6b62\u670d\u52d9\u6216\u7db2\u4e0a\u884c\u5408\u7d04\uff0c\u60a8\u9808\u7e73\u4ed8\u7533\u8acb\u53d6\u6d88\u8cbb\u7528\u3002\u5982\u5ba2\u6236\u65bc\u5b89\u88dd\u670d\u52d9\u5f8c\uff0c\u4e0d\u8ad6\u4efb\u4f55\u539f\u56e0\u800c\u7d42\u6b62\u670d\u52d9\uff0c\u60a8\u9808\u7e73\u4ed8\u63d0\u65e9\u7d42\u6b62\u8cbb\u7528\u4ee5\u53ca\u5176\u4ed6\u6709\u95dc\u8a72\u7b49\u670d\u52d9\u7684\u53d6\u6d88\u8cbb\u7528\uff08\u5982\u6709\uff09\u3002\u4efb\u4f55\u9810\u4ed8\u6b3e\u9805\u4e0d\u6703\u9000\u9084\u7d66\u5ba2\u6236\u3002\n'
			//		+'- \u9664\u975e\u672c\u516c\u53f8\u53e6\u884c\u901a\u77e5\uff0c\u5426\u5247\u6240\u6709\u670d\u52d9\u7684\u627f\u8afe\u671f\u4ee5\u53ca\u8cec\u55ae\u6536\u8cbb\u6703\u65bc\u9810\u8a08\u751f\u6548\u65e5\u8d77\u958b\u59cb\uff0c\u96d6\u7136\u670d\u52d9\u5df3\u65e9\u65bc\u670d\u52d9\u5b89\u88dd\u65e5\u671f\u8d77\u88ab\u555f\u52d5\u4ee5\u4f9b\u4f7f\u7528\u3002\n');
		});
		
		$("[class='shortageCase']").click(function(){
// 			alert('Resource shortage. Please choose normal baskets.');
			alert("<spring:message code="ims.pcd.basketselect.msg.001"/>");
		});
		$("[class='nonPreInstOrder']").click(function(){
// 			alert('Please choose normal baskets only.');
			alert("<spring:message code="ims.pcd.basketselect.msg.002"/>");
		});
		$("[class='preInstOrder']").click(function(){
// 			alert('Please choose pre-installation baskets only.');
			alert("<spring:message code="ims.pcd.basketselect.msg.003"/>");
		});
		$("[class='preUseOrder']").click(function(){
// 			alert('Please choose pre-use baskets only.');
			alert("<spring:message code="ims.pcd.basketselect.msg.004"/>");
		});
		$("[class='nonPreInstPreUseOrder']").click(function(){
// 			alert('Please choose pre-use baskets only.');
			alert("<spring:message code="ims.pcd.basketselect.msg.005"/>");
		});
		$("[class='nonPreUsePreInstOrder']").click(function(){
// 			alert('Please choose pre-installation baskets only.');
			alert("<spring:message code="ims.pcd.basketselect.msg.006"/>");
		});
		
		
		//hover states on the static widgets
		$('#dialog_link, ul#icons li').hover(
			function() { $(this).addClass('ui-state-hover'); }, 
			function() { $(this).removeClass('ui-state-hover'); }
		);
		var image = new Array();
		
		<%
		List<ImsBasketDTO> imsBasketList = (List<ImsBasketDTO>)request.getAttribute("ImsBasketList");
		ImsBasketDTO basket;
		
		if(imsBasketList!=null&&imsBasketList.size()>0){
			int i=0;
			while(i<imsBasketList.size()){
				basket = (ImsBasketDTO)imsBasketList.get(i);
				if(!(basket.getImagePath().equals("NA"))){
				%>
				image.push(<%out.println("'basket/"+basket.getImagePath()+"'"); %> );
				<%				
				}					
				i++;
				if(i<imsBasketList.size()){
					basket = (ImsBasketDTO)imsBasketList.get(i);
				}
			}
		}
		%>
		preload(image);
	});
	
	function preload(images) {
	    if (document.images) {
	        var i = 0;
	        //var imageArray = new Array();
	        //imageArray = images.split(',');
	        var imageObj = new Image();
	        for(i=0; i<=images.length-1; i++) {
	           // imageObj.src=imageArray[i];
	        	 imageObj.src=images[i];
	        }
	    }
	}
	
	$(document).ready(function() {
		if("${ImsOrder.orderActionInd}" == "W" 
				&& ("${IMS_ApprovalRequested}" == "Y")){
			$(":input").attr("disabled", true);
		}		
	});
	
	function OnSearch(){		
		var BWCount=0;
		var PTCount=0;
		
// 		var BWCheckboxGroup=document.forms["basketselect"].bandwidthItem;
// 		var PTCheckboxGroup=document.forms["basketselect"].planTypeBox;
// 		for(i=0;i<BWCheckboxGroup.length;i++){
// 			if(BWCheckboxGroup[i].checked)BWCount++;
// 		}
// 		for(i=0;i<PTCheckboxGroup.length;i++){
// 			if(PTCheckboxGroup[i].checked)PTCount++;
// 		}

		$("input[name='bandwidthItem']").each(function(){
			if ($(this).attr("checked"))
			{
				BWCount++;
			}
		});
		
		$("input[name='planTypeBox']").each(function(){
			if ($(this).attr("checked"))
			{
				PTCount++;
			}
		});		
		
		if(BWCount>0&&PTCount>0){
			document.basketselect.submit();
		}else 
// 			alert("The search criteria is not selected!");
		alert("<spring:message code="ims.pcd.basketselect.msg.007"/>");
	}
	
</script>

<table width="100%" height="30">
	<tr>
		<td class="contenttextgreen" align="center">
		 	<spring:message code="ims.pcd.basketselect.011"/> 
		</td>
	</tr>
</table>

<link href="css/imsds.css" rel="stylesheet" type="text/css">
<%@ include file="/WEB-INF/jsp/imssubheader.jsp" %>
<%@ include file="/WEB-INF/jsp/imsspringboardmodalboxui.jsp" %>


<form:form name="basketselect" commandName="basketinfo" method="POST" >
<input type="hidden" name="IMS_UID" value="<%= request.getAttribute("IMS_UID") %>" />
<br>
<table width="100%" border="0">

	<tr>
		<td class="table_title" ><spring:message code="ims.pcd.basketselect.001"/></td>
	</tr>
  	<tr>
  		<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF">
  			<tr>
  				<td class="item_title_rp" width="15%"><spring:message code="ims.pcd.basketselect.002"/> </td>
 <%
	List<String> bandwidthParamList = (List<String>)request.getAttribute("bandwidthParamList");
	int bandwidthInFrag = 1;
	String bandwidthParamStr = "";

	List<String> bwItemList = (List<String>)request.getAttribute("bwItemList");
	List<String> ptItemList = (List<String>)request.getAttribute("ptItemList");
	List<String> PlanTypeList = (List<String>)request.getAttribute("imsPlanTypeList");

	String isPonShort = (String)request.getAttribute("isPonShort");
	String isVdslShort = (String)request.getAttribute("isVdslShort");
	String isVectorShort = (String)request.getAttribute("isVectorShort");
	String isAdslShort = (String)request.getAttribute("isAdslShort");
	//PlanTypeList.add("Broadband Only");
	//PlanTypeList.add("Broadband + Premium");
	//PlanTypeList.add("Broadband (Cross-Selling)");
	
	
	if(bandwidthParamList!=null && bandwidthParamList.size()>0){
		int i=0;

		do{
			bandwidthParamStr = bandwidthParamList.get(i);
			
			
			if(bandwidthInFrag == 8){
%>
			</tr>
			<tr>
				<td></td>
<%			
				bandwidthInFrag = 1;
			}
%>
  				<td width="10%">						
					<INPUT TYPE=CHECKBOX name="bandwidthItem" value="<%=bandwidthParamStr%>" 
<%
			for(int l=0; l< bwItemList.size(); l++){	
				if(bwItemList.get(l).equals(bandwidthParamStr)){
%>
					checked
<%
				}
			}
%>										
					><%=bandwidthParamStr%></td>
<%
			bandwidthInFrag++;
			i++;
			if(i==bandwidthParamList.size()){
				for(int j=0;j<9-bandwidthInFrag;j++){
%>
				<td>&nbsp;</td>
<%
				}
			}
		}while(i<bandwidthParamList.size());
	}
%>				

			</tr> 
			<tr>
				<td class="item_title_rp"><spring:message code="ims.pcd.basketselect.003"/> </td>
	
<%
	for(int i=0; i<PlanTypeList.size(); i++){
		if(PlanTypeList.get(i).contains("Cross-Selling")){
			out.println("<td colspan ='2'><input type='checkbox' name='planTypeDisableBox' value='"+ 
					PlanTypeList.get(i)
					+ "' disabled>" + 
					PlanTypeList.get(i) + "</td>");
		}else{
			out.println("<td colspan ='2'><input type='checkbox' name='planTypeBox' value='"+ 
				PlanTypeList.get(i)
				+ "' ");
			
			for(int l=0; l< ptItemList.size(); l++){	
				if(ptItemList.get(l).equals(PlanTypeList.get(i))){
						out.println("checked ");
				}
			}
			out.println(">"+ PlanTypeList.get(i) + "</td>");
		}
	}
%>		

				<td colspan ="7" align="right">
					<a href="#" class='nextbutton' onclick='OnSearch()'><spring:message code="ims.pcd.basketselect.004"/></a>
				</td>
			</tr>
		</table>
	</tr>

	<tr>
		<td>	
<%
	//List<ImsBasketDTO> imsBasketList = (List<ImsBasketDTO>)request.getAttribute("ImsBasketList");
	//ImsBasketDTO basket;
	OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
	
	boolean ptMatch = true;
	
	if(imsBasketList!=null&&imsBasketList.size()>0){
		int i=0;
		while(i<imsBasketList.size()){
			basket = (ImsBasketDTO)imsBasketList.get(i);
			for(int l=0; l< bwItemList.size(); l++){
				if(bwItemList.get(l).equals(String.valueOf(basket.getBandwidth_desc())))
				{			
					out.println("<table width='100%' border='1' cellspacing='2'>");
					
						out.println("<tr><td class='table_title'>"+
								bwItemList.get(l)+
								"</td></tr><tr><td><table width='100%' border='0' cellspacing='1' background='images/background2.jpg'>");
					if((basket.getBandwidth_desc().equals(bwItemList.get(l)))
							&&(i<imsBasketList.size())){	
						basket = (ImsBasketDTO)imsBasketList.get(i);
						for(int k=0; k< ptItemList.size(); k++){									
							if((basket.getBandwidth_desc().equals(bwItemList.get(l)))
									&&ptItemList.get(k).equals(basket.getPlanType())){
								
								out.println("<table width='100%' border='1' cellspacing='2'>");
								out.println("<tr><td class='table_title'>"+
										ptItemList.get(k)+
										"</td></tr><tr><td>");
								
								while((basket.getBandwidth_desc().equals(bwItemList.get(l)))
										&&(basket.getPlanType().equals(ptItemList.get(k)))
										&&(i<imsBasketList.size())){
									basket = (ImsBasketDTO)imsBasketList.get(i);
									out.println("<div class='imgcaption4'><table><tr>"+
											"<td width='70%'><span class='purple_text_16'><strong>");
									if(Integer.valueOf(basket.getContractPeriod())>0){
										out.println(basket.getContractPeriod()+ "Mth : ");
										out.println(basket.getMthFixText()+"<br />");
									}else{
										out.println(basket.getMthToMthText()+"<br />");
									}
									
// 									if(basket.getIsPreInst().equals("Y")){
// 										out.println("(Pre-Installation)");
// 									}
									out.println("</strong></span></td><td class='contenttextgary'>("+
											basket.getOfferCode()+
											")</td></tr><tr><td valign='top'><span class='contenttextgary'><br />");
									if ( basket.getBandwidth() != null && !"".equalsIgnoreCase(basket.getBandwidth()) )
									{
										
											out.println("- "+basket.getBandwidth_desc()+" " + 	basket.getSummary()+	"</span></td>");

									}
									else
									{
										out.println("- "+
												basket.getSummary()+
												"</span></td>");
									}
									
									if(!(basket.getImagePath().equals("NA"))){
										out.println("<td><img width='70' src='basket/"+
													basket.getImagePath()+
													"'/></td>");
									}
									
									if("Y".equalsIgnoreCase(order.isSignoffed())&&"Y".equalsIgnoreCase(order.getPreInstallInd())){
											out.println("</tr></table><br><a class='preInstOrder' href='#'>");%><spring:message code='ims.pcd.basketselect.006'/><%out.println("</a>");
									}else if("Y".equalsIgnoreCase(order.isSignoffed())&&"Y".equalsIgnoreCase(order.getPreUseInd())){
										 out.println("</tr></table><br><a class='preUseOrder' href='#'>");%><spring:message code='ims.pcd.basketselect.006'/><%out.println("</a>");
									}else{
										
										 out.println("</tr></table><br><a class='submitAnchor' href='imsbasketdetails.html?basketID="+basket.getBasketId()+"&isPreinstall=N&isPreuse=N");
										
										if(request.getSession().getAttribute("IMS_BasketID")!=null && request.getSession().getAttribute("IMS_BasketID").equals(basket.getBasketId())
												&&!(order.getPreInstallInd()!=null&&"Y".equalsIgnoreCase(order.getPreInstallInd()))
												&&!(order.getPreUseInd()!=null&&"Y".equalsIgnoreCase(order.getPreUseInd()))){
											out.println("'>");%><spring:message code='ims.pcd.basketselect.007'/><%out.println("</a>");
										}else if(order.getOrderActionInd()!=null && order.getOrderStatus() != null){
											if(order.getOrderActionInd().equals("W") && 
												(order.getOrderStatus().equals("31") || order.getOrderStatus().equals("32"))){
													out.println("'></a><font color='#999999'>");%><spring:message code='ims.pcd.basketselect.006'/><%out.println("</font>");		
											}else{
												out.println("'>");%><spring:message code='ims.pcd.basketselect.006'/><%out.println("</a>");
											}
											
										}else{
											out.println("'>");%><spring:message code='ims.pcd.basketselect.006'/><%out.println("</a>");
										}
									}
									if(basket.getIsPreInst().equals("Y")){
										
										if("Y".equalsIgnoreCase(order.isSignoffed())&&!"Y".equalsIgnoreCase(order.getPreInstallInd())&&!"Y".equalsIgnoreCase(order.getPreUseInd())){
												out.println("<br><br><a class='nonPreInstOrder' href='#'>");%><spring:message code='ims.pcd.basketselect.005'/><%out.println("</a>");
										}else if("Y".equalsIgnoreCase(order.isSignoffed())&&!"Y".equalsIgnoreCase(order.getPreInstallInd())&&"Y".equalsIgnoreCase(order.getPreUseInd())){
												out.println("<br><br><a class='nonPreInstPreUseOrder' href='#'>");%><spring:message code='ims.pcd.basketselect.005'/><%out.println("</a>");
										}else{
// 											if(isPonShort.equalsIgnoreCase("Y")&&basket.getTechnology().equalsIgnoreCase("PON")
// 													||isVdslShort.equalsIgnoreCase("Y")&&basket.getTechnology().equalsIgnoreCase("VDSL")
// 													||isVectorShort.equalsIgnoreCase("Y")&&basket.getTechnology().equalsIgnoreCase("Vectoring")
// 													||isAdslShort.equalsIgnoreCase("Y")&&basket.getTechnology().equalsIgnoreCase("ADSL")){
	

												
// 											}else{
												if("Y".equals(basket.getHasGoogleRouter())){
													out.println("<br><br><a class='preInstSubmitAnchor googleRouter' href='imsbasketdetails.html?basketID="+
															basket.getBasketId()+"&isPreinstall=Y&isPreuse=N");
												}else{
												out.println("<br><br><a class='preInstSubmitAnchor' href='imsbasketdetails.html?basketID="+
														basket.getBasketId()+"&isPreinstall=Y&isPreuse=N");
												}
												if(request.getSession().getAttribute("IMS_BasketID")!=null && request.getSession().getAttribute("IMS_BasketID").equals(basket.getBasketId())
														&&order.getPreInstallInd()!=null&&"Y".equalsIgnoreCase(order.getPreInstallInd())){
													out.println("'>");%><spring:message code="ims.pcd.basketselect.008"/><%out.println("</a>");
												}else if(order.getOrderActionInd()!=null && order.getOrderStatus() != null){
													if(order.getOrderActionInd().equals("W") && 
															(order.getOrderStatus().equals("31") || order.getOrderStatus().equals("32"))){
														out.println("'></a><font color='#999999'>");%><spring:message code='ims.pcd.basketselect.005'/><%out.println("</font>");									
														}else{
																out.println("'>");%><spring:message code='ims.pcd.basketselect.005'/><%out.println("</a>");
														}
													
												}else{
													out.println("'>");%><spring:message code="ims.pcd.basketselect.005"/><%out.println("</a>");
												}
// 											}
										}
										
									}
										
									if(basket.getIsPreUse().equals("Y")){
											
										if("Y".equalsIgnoreCase(order.isSignoffed())&&!"Y".equalsIgnoreCase(order.getPreInstallInd())&&!"Y".equalsIgnoreCase(order.getPreUseInd())){
											out.println("<br><br><a class='nonPreInstOrder' href='#'>");%><spring:message code='ims.pcd.basketselect.009'/><%out.println("</a></div>");
										}else if("Y".equalsIgnoreCase(order.isSignoffed())&&"Y".equalsIgnoreCase(order.getPreInstallInd())&&!"Y".equalsIgnoreCase(order.getPreUseInd())){
											out.println("<br><br><a class='nonPreUsePreInstOrder' href='#'>");%><spring:message code='ims.pcd.basketselect.009'/><%out.println("</a></div>");
										}else{
// 											if(isPonShort.equalsIgnoreCase("Y")&&basket.getTechnology().equalsIgnoreCase("PON")
// 													||isVdslShort.equalsIgnoreCase("Y")&&basket.getTechnology().equalsIgnoreCase("VDSL")
// 													||isVectorShort.equalsIgnoreCase("Y")&&basket.getTechnology().equalsIgnoreCase("Vectoring")
// 													||isAdslShort.equalsIgnoreCase("Y")&&basket.getTechnology().equalsIgnoreCase("ADSL")){

												
// 											}else{
												out.println("<br><br><a class='preUseSubmitAnchor' href='imsbasketdetails.html?basketID="+
														basket.getBasketId()+"&isPreinstall=N&isPreuse=Y");
												if(request.getSession().getAttribute("IMS_BasketID")!=null && request.getSession().getAttribute("IMS_BasketID").equals(basket.getBasketId())
														&&order.getPreUseInd()!=null&&"Y".equalsIgnoreCase(order.getPreUseInd())){
													out.println("'>");%><spring:message code='ims.pcd.basketselect.010'/><%out.println("</a></div>");
												}else if(order.getOrderActionInd()!=null && order.getOrderStatus() != null){
													if(order.getOrderActionInd().equals("W") && 
															(order.getOrderStatus().equals("31") || order.getOrderStatus().equals("32"))){
															out.println("'></a><font color='#999999'>");%><spring:message code='ims.pcd.basketselect.009'/><%out.println("</font></div>");									
														}else{
															out.println("'>");%><spring:message code='ims.pcd.basketselect.009'/><%out.println("</a></div>");
														}
													
												}else{
													out.println("'>");%><spring:message code='ims.pcd.basketselect.009'/><%out.println("</a></div>");
												}
// 											}
										}
										
									}else{
										out.println("</div>");
									}
									
												
									i++;
									if(i<imsBasketList.size()){
										basket = (ImsBasketDTO)imsBasketList.get(i);
									}
								}out.println("</td></tr></table>");								
							}out.println("</td></tr></table>");
						}				
					}out.println("</td></tr></table>");
				}
			}
			i++;
			if(i<imsBasketList.size()){
				basket = (ImsBasketDTO)imsBasketList.get(i);
			}
		}
	}
%>		
		</td>
	</tr>
</table>
<div id="preInstSubmitAnchor_Msg" style="display:none">
	<c:out value="${preInstSubmitAnchor_Msg}" escapeXml="false" />
</div>
<div id="preInstSubmitAnchor_googleRouter_Msg" style="display:none">
	<c:out value="${preInstSubmitAnchor_googleRouter_Msg}" escapeXml="false" />
</div>
<div id="preUseSubmitAnchor_Msg" style="display:none">
	<c:out value="${preUseSubmitAnchor_Msg}" escapeXml="false" />
</div>
</form:form>

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>