/**
 * @desc: this function serves as an plug-in of jquery, it can support round corner for an div element
 */
jQuery.fn.roundCorner = function(borderColor, bgColor, borderWidth, onlyBottom){
	borderColor = (borderColor||"black").toLowerCase();
	bgColor = (bgColor||"white").toLowerCase();
	borderWidth = (borderWidth||1);
	onlyBottom = onlyBottom || false;

	$(this).wrapInner("<div class='wrappedContent'></div>");

	//window.alert($(this).html());
	var wrapDiv = document.createElement("div");
	wrapDiv.style.overflow = "hidden";
	wrapDiv.style.background = "transparent";
	wrapDiv.style.display = "block";
	wrapDiv.style.borderColor = borderColor;
	wrapDiv.style.borderStyle = "solid";
	wrapDiv.style.borderWidth = "0 " + borderWidth*1.2 + "px";
	$(".wrappedContent", this).wrap(wrapDiv);
	
	var preDiv='<div class="wrapTop"><div class="wrap1"></div><div class="wrap2"></div><div class="wrap3"></div><div class="wrap4"></div></div>';
	var appDiv='<div class="wrapBottom"><div class="wrap4"></div><div class="wrap3"></div><div class="wrap2"></div><div class="wrap1"></div></div>';
	if(!onlyBottom){
		$(this).prepend(preDiv);
	}
	$(this).append(appDiv);

	$(this).css("background", "transparent");
	$(".wrappedContent", this).css({
		padding: "4px",
		width: "auto",
		height: $(this).height(),
		wordWrap: "break-word",
		lineHeight: "20px",
		background: bgColor
	});
	
	$(".wrapTop,.wrapBottom",this).css({
		display: "block",
		background: "transparent",
		width: $(this).width()
	});
	$(".wrap1,.wrap2,.wrap3,.wrap4",this).css({
		display: "block",
		overflow: "hidden"
	});
	$(".wrap1,.wrap2,.wrap3",this).css({
		height: borderWidth + "px"
	});
	$(".wrap2,.wrap3,.wrap4",this).css({
		borderLeft: (borderWidth*1.2 + "px solid " + borderColor),
		borderRight:(borderWidth*1.2 + "px solid " + borderColor),
		background: bgColor
	});
	$(".wrap1", this).css({
		margin: "0 3px",
		background: borderColor
	});
	$(".wrap2", this).css({
		margin: "0 2px",
		borderWidth: "0 " + borderWidth*1.6 + "px"
	});
	$(".wrap3", this).css({margin: "0 1px"});
	$(".wrap4", this).css({margin: "0 1px", height: "1px"});

}
