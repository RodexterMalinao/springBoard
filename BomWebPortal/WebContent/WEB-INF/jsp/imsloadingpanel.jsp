<style>
.loading {
    position: absolute;
    display: none;        
    background-color: #000;    
    opacity: 0.30;
    filter:alpha(opacity=30);    
   	top: 0px;
   	left: 0px;    
    z-index: 10000;
}
.loading_dialogue {
	top: 0px;
   	left: 0px;
   	width: 100px;
   	height: 50px;
	display: none; 
	padding: 2px;	
 	position: absolute;
 	text-align: center;
 	background-color: #ffffff; 	
	z-index: 20000;
}
</style>

<div id="report-pane-submit" class="loading"></div>

<div id="report-loading-submit" class="loading_dialogue">
	<img src="images/large-loading.gif" width="32" height="32" /><br />
	<strong>Loading</strong>
</div>     

<div id="report-pane" class="loading"></div>

<div id="report-loading" class="loading_dialogue">
	<img src="images/large-loading.gif" width="32" height="32" /><br />
	<strong>Loading</strong>
</div>    

<script>

	$(document).ready(function(){
		$("#report-pane").ajaxStart(function() {
			showLoading();
		}).ajaxStop(function() {			    
			hideLoading();
		});
				
		var e=document.getElementById("refreshed");
		if(e!=null && e.value=="no"){
			e.value="yes";
		}else{
			if(e!=null){
				e.value="no";location.reload();
			}
		}		
	});
	
	function showLoading(){		
		var width = $(document).width();
	    var height = $(document).height();
	    var box_width = $(window).width();
	    var box_height = $(window).height();
	 
	    $("#report-pane").css({
	        height: height,
	        width: width
	    }).show();
	    $("#report-loading").css({
	    	top: box_height / 2 - 25 + $(document).scrollTop(),
	    	left: box_width / 2 - 50 + $(document).scrollLeft()
	    }).show();
	}
	
	function submitShowLoading(){		
		var width = $(document).width();
	    var height = $(document).height();
	    var box_width = $(window).width();
	    var box_height = $(window).height();
	 
	    $("#report-pane-submit").css({
	        height: height,
	        width: width
	    }).show();
	    $("#report-loading-submit").css({
	    	top: box_height / 2 - 25 ,
	    	left: box_width / 2 - 50
	    }).show();
	}
	
	function hideLoading(){
		$("#report-pane").hide();
	    $("#report-loading").hide();
	}
	
	function hideSubmitLoading(){
		$("#report-pane-submit").hide();
	    $("#report-loading-submit").hide();
	}
	
</script>

<input type="hidden" id="refreshed" value="no"></input>