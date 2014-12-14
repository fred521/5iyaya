//Javascript Document For funswitcher by trance

$(document).ready(function(){
var j=1;
var MyTime=false;
var fot=200;
var fin=300;
var amt=300;
var speed=3000;
var maxpic=2;
	$("#banner").find("li").each(function(i){
		$("#banner").find("li").eq(i).mouseover(function(){											  
			var cur=$("#mpc").find("div:visible").prevAll().length;
			if(i!==cur){
				j=i;					
				$("#mpc").find("div:visible").fadeOut(fot,function(){
				$("#mpc").find("div").eq(i).fadeIn(fin);});
				$("#tri").animate({"top":i*87+"px"},amt,current(this,"li"));
				$("#banner").find("dl:visible").slideUp(fot,function(){
				$("#banner").find("dl").eq(i).slideDown(fin);});				
			}			
		})	
	})

function current(ele,tag){
	$(ele).addClass("cur").siblings(tag).removeClass("cur");
	}	

$('#imgnav').hover(function(){
			  if(MyTime){
				 clearInterval(MyTime);
			  }
	 },function(){
			  MyTime = setInterval(function(){
			    $("#banner").find("li").eq(j).mouseover();
				j++;
				if(j==maxpic){j=0;}
			  } , speed);
	 });

	 var MyTime = setInterval(function(){
		$("#banner").find("li").eq(j).mouseover();
		j++;
		if(j==maxpic){j=0;}
	 } , speed);

})