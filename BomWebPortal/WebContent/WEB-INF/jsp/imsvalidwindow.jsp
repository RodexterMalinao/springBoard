<%
	String referer = request.getHeader("referer");
%>
<script src="js/imscommon.js"></script>
<script>
	//var validwindow = false; //PRD
	var validwindow = true;
	validWindow('<%=referer %>');
	var ua = navigator.userAgent;
	var isIMobile = /Mobile/i.test(ua) && /Safari/i.test(ua);	
	$(document).ready(function() {						
		if(isIMobile){			
			/*
			window.onunload = function() {
				if(validwindow==true){
					releaseImsWindow();	
				}
			}*/
			window.addEventListener("pagehide", releaseImsWindow, false);			
		}else{
			window.onbeforeunload = function() {
				if(validwindow==true){
					releaseImsWindow();	
				}
			}
		}		
	});	
</script>
