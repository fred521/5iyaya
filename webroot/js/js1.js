function MenuCityList(show,div,iframe,btn)
	{
		var objdiv = document.getElementById(div);
		var objiframe =  document.getElementById(iframe);
		var objbtn = document.getElementById(btn);
		var ie = (navigator.userAgent.indexOf("MSIE") > -1 && navigator.userAgent.indexOf("Opera") == -1)?true:false;
		var opera = (navigator.userAgent.indexOf("Opera") > -1)?true:false;
		var firefox = (navigator.userAgent.indexOf("Firefox") > -1)?true:false;
		if (show)
		{
			objdiv.style.display = "block";
			if (ie)
			{
				objiframe.style.display = "block";
			}
			objiframe.style.width = objdiv.offsetWidth;
			objiframe.style.height = objdiv.offsetHeight;
			objdiv.style.top = (objbtn.offsetTop + objbtn.offsetHeight - 80) + "px";
			objdiv.style.left = (objbtn.offsetLeft + 80) + "px";
			objiframe.style.top = objdiv.offsetTop + "px";
			objiframe.style.left = objdiv.offsetLeft + "px";
		}
		else
		{
			objiframe.style.display = "none";
			objdiv.style.display = "none";
		}
	}
function toseach(){
if (document.all.searchtype.value==1){
location.href="search1.html";
}
if (document.all.searchtype.value==2){
location.href="search2.html";
}
if (document.all.searchtype.value==3){
location.href="search2.html";
}
}

function catedis(){
if (document.all.searchtype.value==1){
document.all.searchcate.style.display=""
}
if (document.all.searchtype.value==2){
document.all.searchcate.style.display=""
}
if (document.all.searchtype.value==3){
document.all.searchcate.style.display="none"
}
}

function _chat(url){
   //alert(url);
   window.open(url,'win_chat','width=700,height=535,left=300,top=100');
}