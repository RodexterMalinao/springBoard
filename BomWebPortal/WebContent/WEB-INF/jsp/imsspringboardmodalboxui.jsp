<style>
.imsSpringboardModalBoxContainer {
display:none;
left:0;
top:0;
position:fixed;
width:100%;
height:100%;
z-index:1000;
/*overflow:auto;*/
}

.imsSpringboardModalBoxContainer.container--visible {
display:block;
}

.imsSBModalBox-overlay {
width:100%;
height:100%;
top:0;
left:0;
position:fixed;
background:#000;
opacity:0.3;
filter:alpha(opacity=30);
z-index:-1;
}

.imsSBModalBox-content_o {
overflow-y:auto;
height:100%;
}

.imsSBModalBox-content {
border:1px solid black;
background:#fff;
border-radius:2px;
box-shadow:0 4px 8px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
width:80%;
margin:0 auto;
left:25%;
font-family:"Helvetica", "Verdana", "Arial", "sans-serif", "PMingLiU";
z-index:1001;
-webkit-animation: fadein 0.5s;
-moz-animation: fadein 0.5s;
animation: fadein 0.5s;
}

@-webkit-keyframes fadein {
from {opacity:0; -webkit-transform:scale(0.89,0.89);}
to {opacity:1; -webkit-transform:scale(1,1);}
}

@-moz-keyframes fadein {
from {opacity:0; -moz-transform:scale(0.89,0.89);}
to {opacity:1; -moz-transform:scale(1,1);}
}

@keyframes fadein {
from {opacity:0; transform:scale(0.89,0.89);}
to {opacity:1; transform:scale(1,1);}
}

.imsSBModalBox-content.content--center {
top:50%;
left:50%;
position:absolute;
-webkit-transform:translate(-50%, -50%);
-moz-transform:translate(-50%,-50%);
-ms-transform:translate(-50%,-50%);
transform:translate(-50%,-50%);
-webkit-animation: centerFadein 0.5s;
-moz-animation: centerFadein 0.5s;
animation: centerFadein 0.5s;
}

@-webkit-keyframes centerFadein {
from {opacity:0; -webkit-transform:scale(0.89,0.89) translate(-50%, -50%);}
to {opacity:1; -webkit-transform:scale(1,1) translate(-50%, -50%);}
}

@-moz-keyframes centerFadein {
from {opacity:0; -moz-transform:scale(0.89,0.89) translate(-50%, -50%);}
to {opacity:1; -moz-transform:scale(1,1) translate(-50%, -50%);}
}

@keyframes centerFadein {
from {opacity:0; transform:scale(0.89,0.89) translate(-50%, -50%);}
to {opacity:1; transform:scale(1,1) translate(-50%, -50%);}
}

.imsSBModalBox-header {
padding-left:16px;
/*background-color:#6CA9F5;*/
color:#000;
}

.imsSBModalBox-header.header--no_close {
padding:0px 16px 2px 16px;
}

.imsSBModalBox-close {
float:right;
color:#000;
font-size:25px;
padding:0px 8px;
font-weight:bold;
font-family:"Times New Roman";
-webkit-user-select:none;
-moz-user-select:none;
-ms-user-select:none;
user-select:none;
-webkit-transition:0.3s;
-moz-transition:0.3s;
transition:0.3s;
text-decoration:none;
}

.imsSBModalBox-close.close--fix {
top:0;
right:10px;
position:fixed;
font-size:50px;
padding:0 16px;
border:1px solid transparent;
color:#fff;
font-size:50px;
}

.imsSBModalBox-close:hover, .imsSBModalBox-close:focus {
background-color:#6CA9F5;
color:#fff;
text-decoration:none;
cursor:pointer;
}

.imsSBModalBox-close.close--fix:hover, .imsSBModalBox-close.close--fix:focus {
background-color:#6CA9F5;
text-decoration:none;
cursor:pointer;
}

.imsSBModalBox-title {
font-size:15px;
font-weight:bold;
padding:6px 0;
display:block;
overflow:auto;
}

.imsSBModalBox-body {
width:100%;
height:100px;
overflow:auto;
display:block;
}

.imsSBModalBox-text {
padding:2px 16px;
word-wrap:break-word;
}

.imsSBModalBox-footer {
padding:5px 15px;
text-align:center;
background-color:#f3f3f3;
}

.imsSBModalBox-button-Y, .imsSBModalBox-button-N {
border-radius:2px;
display:inline-block;
padding:2px 0px;
max-width:125px;
width:30%;
margin:2px 5px;
font-weight:bold;
font-size:15px;
vertical-align:middle;
text-decoration:none;
-webkit-user-select:none;
-moz-user-select:none;
-ms-user-select:none;
user-select:none;
-webkit-transition:0.3s;
-moz-transition:0.3s;
transition:0.3s;
}

.imsSBModalBox-button-Y.button--hide, .imsSBModalBox-button-N.button--hide {
display:none;
}

.imsSBModalBox-button-Y {
border:1px solid transparent;
background-color:#6CA9F5;
color:#fff;
}

.imsSBModalBox-button-N {
border:1px solid #6CA9F5;
background-color:#fff;
color:#6CA9F5;
}

.imsSBModalBox-button-Y:hover, 
.imsSBModalBox-button-N:hover, 
.imsSBModalBox-button-Y:focus, 
.imsSBModalBox-button-N:focus {
color:#000;
text-decoration:none;
cursor:pointer;
}

.no-scroll {
overflow:hidden;
}

.iOS_fix {
position:fixed;
width:100%;
}

</style>


<script type="text/javascript">
var imsSBModalBox = (function(){
	"use strict";
	var boxStack = [];
	var boxId = 0;
	var documentTop = 0;
	var isIos = window.navigator.userAgent.match(/iPad|iPhone/i);
	
	return function(properties) {
		var imsSpringboardModalBox = {
			BoxId: ++boxId,
			BoxContainer: null, // box element
			BoxContent: null,   // box element
			BoxHeader: null,    // box element
			BoxCloseButton: null, // box element
			BoxTitle: null,     // box element
			BoxBody: null,      // box element
			BoxText: null,      // box element
			BoxFooter: null,    // box element
			ButtonY: null,      // box element
			ButtonN: null,      // box element
			ButtonYtext: null, // null - hide button-Y
			ButtonYvalue: false,
			ButtonNtext: null, // null - hide button-N
			title: null, // default: "&nbsp;"
			text: null,  // default: "&nbsp;"
			width: null,
			height: null,
			maxHeight: null, // null - overflow in browser; value: overflow in box's body
			onClose: null, // function to excute when box is closed
			focusableElements: [],
			centerBox: "N",    // Y: box shown at center (adjust maxHeight to achieve better display); N: box shown at top
			overlayClose: "N", // Y: close box by clicking overlay
			escapeClose: "N",  // Y: close box by pressing "Esc"
			fixCloseButton: "N", // Y: close button in fixed position
			srcollBack: "N" // Y: scroll back to orignal position (due to iOS Safari fix)
		};
		boxStack.push(imsSpringboardModalBox);
		
		function createContainerAndBox() {
			// container
			imsSpringboardModalBox.BoxContainer = document.createElement("div");
			imsSpringboardModalBox.BoxContainer.className = "imsSpringboardModalBoxContainer";
			// content
			imsSpringboardModalBox.BoxContent = document.createElement("div");
			imsSpringboardModalBox.BoxContent.className = "imsSBModalBox-content";
			// header
			imsSpringboardModalBox.BoxHeader = document.createElement("div");
			imsSpringboardModalBox.BoxHeader.className = "imsSBModalBox-header";
			// -- close button
			imsSpringboardModalBox.BoxCloseButton = document.createElement("a");
			imsSpringboardModalBox.BoxCloseButton.className = "imsSBModalBox-close";
			imsSpringboardModalBox.BoxCloseButton.innerHTML = "&times";
			imsSpringboardModalBox.BoxCloseButton.setAttribute("href","javascript:void(0)");
			if (imsSpringboardModalBox.fixCloseButton == "Y") {
				$(imsSpringboardModalBox.BoxHeader).addClass("header--no_close");
				$(imsSpringboardModalBox.BoxCloseButton).addClass("close--fix");
				imsSpringboardModalBox.BoxContainer.appendChild(imsSpringboardModalBox.BoxCloseButton);
			} else {
				imsSpringboardModalBox.BoxHeader.appendChild(imsSpringboardModalBox.BoxCloseButton);
			}
			// -- title
			imsSpringboardModalBox.BoxTitle = document.createElement("span");
			imsSpringboardModalBox.BoxTitle.className = "imsSBModalBox-title";
			imsSpringboardModalBox.BoxTitle.innerHTML = "&nbsp;";
			imsSpringboardModalBox.BoxHeader.appendChild(imsSpringboardModalBox.BoxTitle);
			// body
			imsSpringboardModalBox.BoxBody = document.createElement("div");
			imsSpringboardModalBox.BoxBody.className = "imsSBModalBox-body";
			imsSpringboardModalBox.BoxBody.tabIndex = "0";
			// -- text
			imsSpringboardModalBox.BoxText = document.createElement("div");
			imsSpringboardModalBox.BoxText.className = "imsSBModalBox-text";
			imsSpringboardModalBox.BoxText.innerHTML = "&nbsp;";
			imsSpringboardModalBox.BoxBody.appendChild(imsSpringboardModalBox.BoxText);
			// footer
			imsSpringboardModalBox.BoxFooter = document.createElement("div");
			imsSpringboardModalBox.BoxFooter.className = "imsSBModalBox-footer";
			// -- button Y
			imsSpringboardModalBox.ButtonY = document.createElement("a");
			imsSpringboardModalBox.ButtonY.className = "imsSBModalBox-button-Y";
			imsSpringboardModalBox.ButtonY.setAttribute("href","javascript:void(0)");
			imsSpringboardModalBox.BoxFooter.appendChild(imsSpringboardModalBox.ButtonY);
			// -- button N
			imsSpringboardModalBox.ButtonN = document.createElement("a");
			imsSpringboardModalBox.ButtonN.className = "imsSBModalBox-button-N";
			imsSpringboardModalBox.ButtonN.setAttribute("href","javascript:void(0)");
			imsSpringboardModalBox.BoxFooter.appendChild(imsSpringboardModalBox.ButtonN);
			// overlay, content_o
			var overlay = document.createElement("div");
			overlay.className = "imsSBModalBox-overlay";
			var content_o = document.createElement("div");
			content_o.className = "imsSBModalBox-content_o";
			
			// appendChild
			imsSpringboardModalBox.BoxContainer.appendChild(overlay);
			imsSpringboardModalBox.BoxContainer.appendChild(content_o);
			content_o.appendChild(imsSpringboardModalBox.BoxContent);
			imsSpringboardModalBox.BoxContent.appendChild(imsSpringboardModalBox.BoxHeader);
			imsSpringboardModalBox.BoxContent.appendChild(imsSpringboardModalBox.BoxBody);
			imsSpringboardModalBox.BoxContent.appendChild(imsSpringboardModalBox.BoxFooter);
			
			var bodySelector = document.getElementsByTagName("BODY")[0];
			if (bodySelector) {
				bodySelector.appendChild(imsSpringboardModalBox.BoxContainer);
			}
			
			//var _this = this;
			// eventListener
			$(imsSpringboardModalBox.BoxContainer).click(overlayClose);
			$(imsSpringboardModalBox.BoxContainer).keydown(function(e) {
				containerKeyDown(e);
			});
			$(imsSpringboardModalBox.BoxCloseButton).click(function(e) {
				e.stopPropagation();
				toggleBox(false);
			});
			$(imsSpringboardModalBox.BoxContent).click(function(e) {
				e.stopPropagation();
			});
			$(imsSpringboardModalBox.ButtonN).click(function () {
				buttonClick(false, false);
			});
			$(imsSpringboardModalBox.ButtonY).click(function () {
				buttonClick(true, false);
			});
		}
		
		function applyPropHtmlAndStyle() {
			imsSpringboardModalBox.focusableElements.push(imsSpringboardModalBox.BoxCloseButton);
			imsSpringboardModalBox.focusableElements.push(imsSpringboardModalBox.BoxBody);
			
			if (typeof imsSpringboardModalBox.ButtonYtext === "string" &&
					imsSpringboardModalBox.ButtonYtext != "") {
				imsSpringboardModalBox.ButtonY.innerHTML = imsSpringboardModalBox.ButtonYtext;
				imsSpringboardModalBox.focusableElements.push(imsSpringboardModalBox.ButtonY);
			} else {
				$(imsSpringboardModalBox.ButtonY).addClass("button--hide");
			}
			if (typeof imsSpringboardModalBox.ButtonNtext === "string" &&
					imsSpringboardModalBox.ButtonNtext != "") {
				imsSpringboardModalBox.ButtonN.innerHTML = imsSpringboardModalBox.ButtonNtext;
				imsSpringboardModalBox.focusableElements.push(imsSpringboardModalBox.ButtonN);
			} else {
				$(imsSpringboardModalBox.ButtonN).addClass("button--hide")
			}
			
			if (typeof imsSpringboardModalBox.title === "string" &&
					imsSpringboardModalBox.title != "") {
				imsSpringboardModalBox.BoxTitle.innerHTML = imsSpringboardModalBox.title;
			}
			if (typeof imsSpringboardModalBox.text === "string" &&
					imsSpringboardModalBox.text != "") {
				imsSpringboardModalBox.BoxText.innerHTML = imsSpringboardModalBox.text;
			}
			if (typeof imsSpringboardModalBox.width === "string" &&
					imsSpringboardModalBox.width != "") {
				$(imsSpringboardModalBox.BoxContent).css("width", imsSpringboardModalBox.width);
			}
			if (typeof imsSpringboardModalBox.height === "string" &&
					imsSpringboardModalBox.height != "") {
				$(imsSpringboardModalBox.BoxBody).css("height", imsSpringboardModalBox.height);
			}
			if (typeof imsSpringboardModalBox.maxHeight === "string" &&
					imsSpringboardModalBox.maxHeight != "") {
				$(imsSpringboardModalBox.BoxBody).css("maxHeight", imsSpringboardModalBox.maxHeight);
			}
		}
		
		function containerKeyDown(e) {
			var elem_len = imsSpringboardModalBox.focusableElements.length;
			if (e.keyCode === 9) {
				if (e.shiftKey) {
					if (document.activeElement === imsSpringboardModalBox.focusableElements[0]) {
						e.preventDefault();
						imsSpringboardModalBox.focusableElements[elem_len-1].focus();
					}
				} else {
					if (document.activeElement === imsSpringboardModalBox.focusableElements[elem_len-1]) {
						e.preventDefault();
						imsSpringboardModalBox.focusableElements[0].focus();
					}
				}
			}
			if (e.keyCode == 27) {
				if (imsSpringboardModalBox.escapeClose == "Y") {
					toggleBox(false);
				}
			}
		}
		
		function overlayClose() {
			if (imsSpringboardModalBox.overlayClose == "Y") {
				toggleBox(false);
			}
		}
		
		function buttonClick(buttonYvalue, visible) {
			imsSpringboardModalBox.ButtonYvalue = buttonYvalue;
			toggleBox(visible);
		}
		
		function toggleBox(visible) {
			var bodySelector = document.getElementsByTagName("BODY")[0];
			if (visible && bodySelector) {
				showBox(bodySelector, imsSpringboardModalBox);
			} else if (!visible && bodySelector) {
				boxStack.shift(); // remove current shown one from array
				
				$(imsSpringboardModalBox.BoxContainer).removeClass("container--visible");
				bodySelector.removeChild(imsSpringboardModalBox.BoxContainer);
				$(bodySelector).removeClass("no-scroll");
				if (isIos) {
					$(bodySelector).removeClass("iOS_fix");
				}
				if (boxStack.length >= 1) {
					showBox(bodySelector, boxStack[0]);
				} else {
					if (imsSpringboardModalBox.srcollBack == "Y") {
						window.scrollTo(0, documentTop);
					}
					documentTop = 0;
				}
				if (typeof imsSpringboardModalBox.onClose === "function") {
					imsSpringboardModalBox.onClose.call(this);
				}
			}
		}
		
		function showBox(bodySelector, box) {
			if (box.centerBox == "Y" && 
					typeof box.maxHeight === "string" &&
					box.maxHeight != "" && 
					(typeof box.BoxContent.style.transform !== "undefined" || 
						typeof box.BoxContent.style.msTransform !== "undefined" || 
						typeof box.BoxContent.style.WebkitTransform !== "undefined")) {
				$(box.BoxContent).addClass("content--center");
			}
			$(box.BoxContainer).addClass("container--visible");
			if (documentTop == 0) {
				documentTop = window.pageYOffset || document.documentElement.scrollTop;
			}
			$(bodySelector).addClass("no-scroll");
			if (isIos) {
				$(bodySelector).addClass("iOS_fix");
			} else {
				box.focusableElements[0].focus();
			}
		}
		
		return {
			openBox: function () {
				var property = null;
				for (property in properties) {
					if (properties.hasOwnProperty(property)) {
						imsSpringboardModalBox[property] = properties[property];
					}
				}
				createContainerAndBox();
				applyPropHtmlAndStyle();
				if (boxStack[0].BoxId == imsSpringboardModalBox.BoxId) {
					toggleBox(true);
				}
			},
			closeBox: function () {
				toogleBox(false);
			},
			getValueOfButtonY: function() {
				return imsSpringboardModalBox.ButtonYvalue;
			}
		};
	};
})();
/* 
 Usage: call imsSBModalBox.openBox to init and open a modal box as "function showSpringboardModalBox()".
 Note: 1. If using in <a href="url" onclick="func()" >, refer to below "function onClick()" to set-up.
       2. To excute some statements after the box is closed, put them in "onClose".
 Input variable:
	 ButtonYtext
	 title
	 text
	 width
	 maxHeight
	 centerBox
	 overlayClose
	 escapeClose
	 fixCloseButton
	 onClose
*/
/*
function showSpringboardModalBox(idForTitle, idForText, textForY, textForN) {
	var a = new imsSBModalBox({
		width: "80%",
		height: "auto",
		maxHeight: "350px",
		title: document.getElementById(idForTitle).innerHTML,
		text: document.getElementById(idForText).innerHTML + "ABC",
		ButtonYtext: textForY,
		ButtonNtext: textForN,
		centerBox: "Y",
		overlayClose: "N",
		escapeClose: "N",
		fixCloseButton: "N",
		onClose: function() {
			console.log("Button Y value: " + a.getValueOfButtonY());
		}
	});
	a.openBox();
}
*/
/*
function onClick(event) {
	event.preventDefault();
	var self = this;
	var b = new imsSBModalBox({
		...
		...
		onClose: function() {
			window.location.href = self.href;
		}
	});
	b.openBox();
}  
*/
</script>