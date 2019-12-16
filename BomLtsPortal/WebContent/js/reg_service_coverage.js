function reg_open_chat(url){
	var reg_live_chat_link = url;

	reg_mywindow = window.open(reg_live_chat_link, "livechat", "height=650, width=800, location=no, scrollbars=no, menubar=no, status=no,toolbar=no");

	//window.open(reg_live_chat_link, "livechat", "height=650, width=800, location=no, scrollbars=no, menubar=no, status=no,toolbar=no");

	
	reg_mywindow.moveTo(400, 100);
}


