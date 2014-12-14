/**
 * A simple jquery plug-in for display tabs 
 *
 */
(function($){
$.fn.tab = function(style,titleTag,ele,show){
	return this.each(function(){
	var $titleTag = $(this).find(titleTag);
	$titleTag.css("cursor","pointer");
    $titleTag.next().not(":eq("+show+")").hide();
	if((show)>$titleTag.length) alert("Too much items to be displayed.");
    $titleTag.each(function(){
	  $titleTag.click(function(){
	    switch(style){
          case "normal":
			 $titleTag.parent().find(ele + ":visible").hide();			  
	         $(this).next().show();
			 break;
          case "slide":
	         $titleTag.parent().find(ele + ":visible").hide();
	         $(this).next().slideDown("slow");
			 break;
		  case "fade":
		     $titleTag.parent().find(ele + ":visible").hide();
	         $(this).next().fadeIn("fast");	
			 break;
		  case "toggle":
		 
		     $(this).next().toggle();
			 break;
		  default:
				return;
		  }
	  });
    });
  });
}
})(jQuery);


