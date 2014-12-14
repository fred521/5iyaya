// JavaScript Document
(function($){  
$.fn.changeClass = function(ele,cName,show){
  return this.each(function(){
	 $(this).find(ele+":eq("+show+")").addClass(cName);
      $(this).find(ele).click(function(){
         $(this).parent().find(ele).removeClass(cName);
         $(this).addClass(cName);
      });
    });
}
})(jQuery);