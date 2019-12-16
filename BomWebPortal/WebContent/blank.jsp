<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.4.4.js"> </script> 
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script>
var rmk = "${amend_rmk}";
var action = "${_action}";
var isDS = "${ims_direct_sales}" == "true"?"Y":"N";
var qcOrderId ="${_qcOrderId}";


function _redirect(){
	if(action == '_a') {  
		if (isDS == 'Y') {
			parent.parent.$("#amendPage iframe").contents().find("#progOfferChangeRemark").val(rmk);
			parent.parent.$("#amendPage iframe").contents().find("#fsInstdate").val("");
		} else {
			parent.parent.$("#amendPage iframe").contents().find("#amendCreateFrame").contents().find("#progOfferChangeRemark").val(rmk);
			parent.parent.$("#amendPage iframe").contents().find("#amendCreateFrame").contents().find("#fsInstdate").val("");
		}
		parent.parent.parent.$("#amendProgOfferPage").dialog('close'); 
	}else if(action == 'ntvawaitsignoff'){
		parent.location.replace("${ntvDomain}"+"ntvorderdetail.html?_al=new&orderId="+qcOrderId+"&status=11&awaitSignOff=Y");
	}
	else parent.location.reload();
} 
</script>
<style>
div.screenMask
{
    position: absolute;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 100%;
    z-index: 99999;
    background-color: #000000;
    background:rgba(255,255,255,1) url('./images/loading_icon.gif') no-repeat center center;
    opacity: 0.7;
    filter: alpha(opacity=70)    
}

</style>
</head>
<body onload="javascript:_redirect();">
	<div class="screenMask"></div>
</body>
</html>