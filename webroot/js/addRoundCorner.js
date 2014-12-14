/**
 * @Desc: this function serves as a simple function to add round corner for special element
 */
function addRoundCorner(bdColor, bgColor){
	bdColor = bdColor || "hotpink" || "#ff1b5f";

	$("div.roundCorner,td.roundCorner").each(function(){
		var borderColor = $(this).hasClass("gray") ? "#aaa" : bdColor;
		var borderWidth = $(this).hasClass("bold") ? 2 : 1;
		var onlyBottom = $(this).hasClass("onlyBottom") ? true : false;
		bgColor = ($(this).css('background-color') != 'white' ?  $(this).css('background-color') : "#CCFFCC");
		
		if(onlyBottom){
			$(this).corner('bottom');
		}
		else{
			$(this).corner();
		}
	});
}
