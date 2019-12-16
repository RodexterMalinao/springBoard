var modalWindow = {
	parent : "body",
	windowId : null,
	title : null,
	width : null,
	height : null,
	src   : null,
	scrollTop : null,
	open : function() {
		var div = "";
		div += "<div id=\"" + this.windowId
 			+ "\" title=\"" + this.title + "\" style=\"overflow: hidden; margin: 0; padding: 0;overflow:scroll !important; -webkit-overflow-scrolling:touch !important;\" >";
		div += "<iframe style=\"border: none; overflow: auto; overflow-x: scroll; overflow-y: scroll; width: 100%; height: 100%;\""
			+  " src='" 
			+ this.src + "'></iframe>";
		div += "</div>";
		
		var scrollHandler = function () { 
			divDialog.dialog("option","position","center"); 
		};

		var divDialog = $(div).dialog({
										title:  this.title,
										width:  this.width,
										height: this.height,
										modal:  true,
										resizable: true,
										autoResize: true,
										open : function(event, ui) {
											//$("body").css("overflow", "hidden");
											//$(window).scroll(scrollHandler);
											$(this).attr("id", this. windowId);  
										},
										close: function(event, ui) {
											
											$.ajax({
												url : "orderamendclearsession.html",
												type : "POST",	
											});
											//$("body").css("overflow", "auto");
											$(this).dialog('destroy').remove();
											//$(window).off("scroll", scrollHandler);											
								        }
		});
	}
};

var openModalDialog = function() {
	modalWindow.windowId = "divModalDialog";
	modalWindow.title = "";
    modalWindow.width = $(window).width() - 20;
    modalWindow.height = $(window).height() - 30;
    modalWindow.scrollTop = 0;//$(document).scrollTop();
    
    if (arguments.length <= 0) {
    	alert('Mandatory Parameter src is NULL !!');
    }
    
	if(arguments.length >= 1) {
		modalWindow.src = arguments[0];
		if(arguments.length >= 2) {
			modalWindow.title = arguments[1];
		}
		if(arguments.length >= 3) {
			modalWindow.windowId = arguments[2];
		}
		if(arguments.length >= 4) {
			modalWindow.width = arguments[3];
		}
		if(arguments.length >= 5) {
			modalWindow.height = arguments[4];
		}
	}
	
    modalWindow.open();  
};