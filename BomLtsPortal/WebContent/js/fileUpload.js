// common variables
var iBytesUploaded = 0;
var iBytesTotal = 0;
var iPreviousBytesLoaded = 0;
var iMaxFilesize; // 1MB
var oTimer = 0;
var sResultFileSize = '';
var uploadType = '';
var originalImgSize = false;
var maxWidthLimit = '250px';
var browserName=navigator.appName; 

function secondsToTime(secs) { // we will use this function to convert seconds in normal time format
    var hr = Math.floor(secs / 3600);
    var min = Math.floor((secs - (hr * 3600))/60);
    var sec = Math.floor(secs - (hr * 3600) -  (min * 60));

    if (hr < 10) {hr = "0" + hr; }
    if (min < 10) {min = "0" + min;}
    if (sec < 10) {sec = "0" + sec;}
    if (hr) {hr = "00";}
    return hr + ':' + min + ':' + sec;
};

function bytesToSize(bytes) {
    var sizes = ['Bytes', 'KB', 'MB'];
    if (bytes == 0) return 'n/a';
    var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
    return (bytes / Math.pow(1024, i)).toFixed(1) + ' ' + sizes[i];
};

function fileSelected() {

    // hide different warnings
    document.getElementById('upload_response').style.display = 'none';
    document.getElementById('error').style.display = 'none';
    document.getElementById('error2').style.display = 'none';
    document.getElementById('abort').style.display = 'none';
    document.getElementById('warnsize').style.display = 'none';
    document.getElementById('submitButton').disabled = true;
    document.getElementById('fileinfo').style.display = 'none';
    document.getElementById('warnhtml5notsupport').style.display = 'none';
    
    if(typeof(window.FormData) == 'undefined'){
    	document.getElementById('preview').style.display = 'none';
    }else{
    	document.getElementById('preview').style.display = 'block';
    }
    // get selected file element
    var elem = document.getElementById('fileUpload'), oFile = elem.files && elem.files[0];
    if(oFile != 'undefined' && typeof(window.FileReader) != 'undefined'){
	    // filter for image files
	    var rFilter  = /^(image\/gif|image\/jpeg|image\/png|application\/pdf)$/i;
	 
	    if(!oFile){
	    	return;
	    }
	    
	    if (! rFilter.test(oFile.type)) {
	        document.getElementById('error').style.display = 'block';
	        return;
	    }
	
	    // little test for filesize
	    if (oFile.size > iMaxFilesize) {
	        document.getElementById('warnsize').style.display = 'block';
	        return;
	    }
	
	    // prepare HTML5 FileReader
	    var oReader = new FileReader();
	    
	    oReader.onload = function(e){
	        // we are going to display some custom image information here
	        sResultFileSize = bytesToSize(oFile.size);
	        document.getElementById('submitButton').disabled = false;
	        document.getElementById('fileinfo').style.display = 'block';
	        
        	// get preview element
        	var oImage = document.getElementById('preview');
            // e.target.result contains the DataURL which we will use as a source of the image        	
        	oImage.src = e.target.result;
	        
	        var img = new Image();
	        img.onload = function() {		//Update all the upload image information once the image is on-loaded
		      document.getElementById('filename').innerHTML = 'Name: ' + oFile.name;
		      document.getElementById('filesize').innerHTML = 'Size: ' + sResultFileSize;
		      document.getElementById('filetype').innerHTML = 'Type: ' + oFile.type;		        	
	          document.getElementById('filedim').innerHTML = 'Dimension: ' + this.naturalWidth + ' x ' + this.naturalHeight;
	        };
	        img.src = oImage.src; 
	    };
	    // read selected file as DataURL
	    oReader.readAsDataURL(oFile);
    }
    else{
    	document.getElementById('submitButton').disabled = false;
    	document.getElementById('warnhtml5notsupport').style.display = 'block';
    }
}

function startUploading() {
	if(typeof(window.FormData) != 'undefined'){
	    // cleanup all temp states
	    iPreviousBytesLoaded = 0;
	    document.getElementById('upload_response').style.display = 'none';
	    document.getElementById('error').style.display = 'none';
	    document.getElementById('error2').style.display = 'none';
	    document.getElementById('abort').style.display = 'none';
	    document.getElementById('warnsize').style.display = 'none';
	    document.getElementById('warnhtml5notsupport').style.display = 'none';
	    document.getElementById('progress_percent').innerHTML = '';
	    var oProgress = document.getElementById('progress');
	    var url = document.getElementById('docImgUploadForm').getAttribute('action');
	    
	    oProgress.style.display = 'block';
	    oProgress.style.width = '0px';
	
	    
	    // create XMLHttpRequest object, adding few event listeners, and POSTing our data
	    var oXHR = new XMLHttpRequest();        
	    oXHR.upload.addEventListener('progress', uploadProgress, false);
	    oXHR.addEventListener('load', uploadFinish, false);
	    oXHR.addEventListener('error', uploadError, false);
	    oXHR.addEventListener('abort', uploadAbort, false);
	    

	    oXHR.open('POST', url);
		    
		// get form data for POSTing
		var vFD = new FormData(document.getElementById('docImgUploadForm'));
		oXHR.send(vFD);
		
		oXHR.onreadystatechange = function() {
			if (oXHR.readyState == 4 && oXHR.status == 200) {
			  	document.open();
			   	document.write(oXHR.responseText);
			}
			document.close();
		};	    
	    // set inner timer
	    //oTimer = setInterval(doInnerUpdates, 300);
	    
	} else{
		document.docImgUploadForm.submit();
	}
}

function doInnerUpdates() { // we will use this function to display upload speed
    var iCB = iBytesUploaded;
    var iDiff = iCB - iPreviousBytesLoaded;

    // if nothing new loaded - exit
    if (iDiff == 0)
        return;

    iPreviousBytesLoaded = iCB;
    iDiff = iDiff * 2;
    var iBytesRem = iBytesTotal - iPreviousBytesLoaded;
    var secondsRemaining = iBytesRem / iDiff;

    // update speed info
    var iSpeed = iDiff.toString() + 'B/s';
    if (iDiff > 1024 * 1024) {
        iSpeed = (Math.round(iDiff * 100/(1024*1024))/100).toString() + 'MB/s';
    } else if (iDiff > 1024) {
        iSpeed =  (Math.round(iDiff * 100/1024)/100).toString() + 'KB/s';
    }

    document.getElementById('speed').innerHTML = iSpeed;
    document.getElementById('remaining').innerHTML = '| ' + secondsToTime(secondsRemaining);        
}

function uploadProgress(e) { // upload process in progress
    if (e.lengthComputable) {
        iBytesUploaded = e.loaded;
        iBytesTotal = e.total;
        var iPercentComplete = Math.round(e.loaded * 100 / e.total);
        var iBytesTransfered = bytesToSize(iBytesUploaded);

        document.getElementById('progress_percent').innerHTML = iPercentComplete.toString() + '%';
        document.getElementById('progress').style.width = (iPercentComplete * 4).toString() + 'px';
        document.getElementById('b_transfered').innerHTML = iBytesTransfered;
        if (iPercentComplete < 100) {
            var oUploadResponse = document.getElementById('upload_response');
            oUploadResponse.innerHTML = '<h1>'+ processMsg +'</h1>';
            oUploadResponse.style.display = 'block';
        }
    } else {
        document.getElementById('progress').innerHTML = 'unable to compute';
    }
}

function uploadFinish(e) { // upload successfully finished
	var oUploadResponse = document.getElementById('upload_response');
    //oUploadResponse.innerHTML = e.target.responseText;
	
	oUploadResponse.innerHTML = '<h1>' + completeMsg + '</h1>';
    oUploadResponse.style.display = 'block';

    document.getElementById('progress_percent').innerHTML = '100%';
    document.getElementById('progress').style.width = '400px';
    document.getElementById('filesize').innerHTML = sResultFileSize;
    document.getElementById('remaining').innerHTML = '| 00:00:00';

    clearInterval(oTimer);
}

function uploadError(e) { // upload error
    document.getElementById('error2').style.display = 'block';
    clearInterval(oTimer);
}  

function uploadAbort(e) { // upload abort
    document.getElementById('abort').style.display = 'block';
    clearInterval(oTimer);
}

function imageResizing() { // re-size viewing uploaded image
	var img = document.getElementById('uploadedImage');
    originalImgSize = !(originalImgSize);
    
    if(browserName == "Microsoft Internet Explorer"){
    	var maxWidth = 250;
    	var i = new Image();
    	i.src = img.src;
    	
    	if(originalImgSize && i.width > maxWidth){
    		img.width = i.width;
    	}
    	else if(!originalImgSize && i.width > maxWidth){
    		img.width = maxWidth;
    	}
    }
    else{
	    if(originalImgSize){
	    	img.style.maxWidth = 'none';
	    }
	    else{
	    	img.style.maxWidth = maxWidthLimit;
	    }
    }
};

function imageResizingOnIE(){
	if(browserName == "Microsoft Internet Explorer"){
		var maxWidth = 250;
		var curImg = document.getElementById('currentImg');
		var upImg = document.getElementById('preview');
		if(curImg.width > maxWidth){
			curImg.width = maxWidth;
		}
		if(upImg.width > maxWidth){
			upImg.width = maxWidth;
		}
	}
};

function resultImageResizingOnIE(){
	if(browserName == "Microsoft Internet Explorer"){
		var maxWidth = 250;
		var resultImg = document.getElementById('uploadedImage');
		if(resultImg.width > maxWidth){
			resultImg.width = maxWidth;
		}
	}
};

function imageResizingOnImageFileList(listImg){
	if(browserName == "Microsoft Internet Explorer"){
		var maxWidth = 250;
		if(listImg.width > maxWidth){
			listImg.width = maxWidth;
		}
	}
};