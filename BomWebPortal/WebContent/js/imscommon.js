function serverValid(referer){		
		var valid;
		$.ajax({
			url : 'imsiscurrenttab.html?referer='+referer+"&ts="+new Date().getTime(),
			cache : false,
			type : 'POST',
			dataType : 'json',
			timeout : 60000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus=='parsererror'){
					alert("Your session has been timed out, please re-login.");	
				}else if(textStatus == 'timeout'){
			    	alert("Server response timeout, please try again.");
			    }					
			},
			success : function(msg) {				
				$.each(eval(msg), function(i, item) {
					valid = item.valid;					
				});					
				if(valid=="N"){					
					window.location = "ImsError.jsp";
				}else{
					validwindow = true;					
				}				
			}
		});				
}

function validWindow(referer){	
	if(history.length < 4){	
		window.location = "ImsError.jsp";
	}else{		
		serverValid(referer);
	}	
}

function releaseImsWindow(){	
	$.ajax({
		url : 'imsiscurrenttab.html?action=release&ts='+new Date().getTime(),
		async : false,
		type : 'POST',
		cache : false,
		dataType : 'json',
		timeout : 1000,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if(textStatus=='parsererror'){
				alert("Your session has been timed out, please re-login.");	
			}else if(textStatus == 'timeout'){
		    	alert("Server response timeout, please try again.");
		    }					
		},
		success : function(msg) {			
		}
	});		
}