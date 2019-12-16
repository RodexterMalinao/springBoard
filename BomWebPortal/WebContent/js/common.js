function selectLocaleChanged(currentLanguage, localeToChange) {
	//alert(localeToChange);
	var changeLocaleuUrl = location.href;
	//alert(changeLocaleuUrl);
	
	
	if(changeLocaleuUrl.indexOf('language')>0){
		changeLocaleuUrl = changeLocaleuUrl.replace(new RegExp(currentLanguage, 'g'), localeToChange);
	// start - avoid ? already exist in url
	} else if (changeLocaleuUrl.indexOf('?')>0) {
		changeLocaleuUrl = changeLocaleuUrl + "&language=" + localeToChange;
	// end	
	}else{
		changeLocaleuUrl = changeLocaleuUrl + "?language=" + localeToChange;
	}
	
	window.location.href = changeLocaleuUrl;
}