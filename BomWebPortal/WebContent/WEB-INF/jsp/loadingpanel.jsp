<style type="text/css">
.loading {
    display: none;
    position: absolute;    
    background-color: #000;    
    opacity: 0.60;
    filter:alpha(opacity=60);    
   	top: 0px;
   	left: 0px;    
    z-index: 10000;
}
.loading_dialogue {
	font-family: sans-serif;
    display: none;
   	width: 100px;
   	height: 50px;
	padding: 2px;	
 	position: fixed;
 	text-align: center;
 	/*background-color: #ffffff;*/ 	
 	color: #dddddd; 	
 	text-shadow: 1px 1px #000000;
	z-index: 20000;
	top: 50%;
	margin-top: -25px;
	right: 50%;
	margin-right: -50px;
	text-align: center;
	_position: absolute;
	_top:expression(0+(((e=document.documentElement.scrollTop)?e:document.body.scrollTop) + document.innerHeight)+'px');
}
</style>

<div id="report-pane" class="loading"></div>

<div id="report-loading" class="loading_dialogue">
	<img src="${pageContext.request.contextPath}/images/spinner.gif" width="32" height="32">
	<div style="clear: both"></div>
	<strong>Loading</strong>
</div>     

<script type="text/javascript">
function resize() {
	var width = $(document).width();
	var height = $(document).height();
	
	$("#report-pane").css({ height: height, width: width });
}
function showLoading() {
	$("#report-pane,#report-loading").show();
	resize();
}
function hideLoading() {
	$("#report-pane,#report-loading").hide();
}
$(document).ready(function() {
	$("form").submit(function() {
		timer = setTimeout(function() {
 			showLoading();
 		}, 250);
	});
	
	$("#report-pane").ajaxStart(function() {
		showLoading();
	}).ajaxStop(function() {			    
		hideLoading();
	});
	$(window).resize(function() {
		if (!$("#report-pane").is(":visible")) {
			return;
		}
		resize();
	});
});
</script>