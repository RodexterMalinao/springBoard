function setCartSize(height, width){
   	 $("#cartarea").height(height +5);
}
	
function toggleCart(){
   	 if( $("#cartarea").attr("src") == ""){
       	 $("#cartarea").attr("src", "shoppingcart.html");
       	 $("#cartarea").height(800);
       	 $("#cartarea").width(380);
   	 }
   		 if($("#cartarea").is(":visible")){
   	   		 $("#cartarea").hide();
   		 }else{
   	   	   	 $("#cartarea").fadeIn(500);
   	   	   	 setCartPosition();
   		 }
}

function setCartPosition(){
  	 $("#cartarea").offset({top:$(".cart_icon").offset().top+33, left:$(".cart_icon").offset().left-310});
}
     
$(document).ready(function(){
   	$("body").click(function(){
   		if($("#cartarea").is(":visible")){
   	   		$("#cartarea").hide();
   		}
   	}); 
   	
   	$(".cart_icon").click(function(e){
   		e.stopPropagation();
   	});
});

$(window).resize(function() {
	setCartPosition();
}); 
