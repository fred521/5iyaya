function init_top(B){
	if(B.uname){
		$("topbar_usernmae").innerHTML=B.uname;
		$("topbar_baiduhi").onclick = function(){
			startBaiduHi('c2cmsg',B.uname,'',{method:'ccmsg',iid:''});
			return false;
		};
		$("login_top").style.display="";
		init_imUpdata();
	}else{
		$("unlogin_top").style.display="";
		var A=$("login_link").href;
		A+=encodeURIComponent(window.location.href);
		$("login_link").href=A;
	}
	if(B.my){
		if(B.my.order){
			$("topbar_my_bill").innerHTML=B.my.order;
			$("topbar_bill_container").style.display = "inline";
		}else{
			$("topbar_bill_container").style.display = "none";
		}
		if(B.my.message){
			$("topbar_my_msg").innerHTML=B.my.message;
		}
	}
}

function init_imUpdata(){
	var url="http://fetch.im.baidu.com/ihaloader?op=msgcount&charset=gbk&callback=init_imInit&refer=youa.baidu.com";
	if(document.getElementById('immsgcount')){
		document.getElementById('immsgcount').parentNode.removeChild(document.getElementById('immsgcount'));
	}
	script=document.createElement("SCRIPT");
	script.type="text/javascript";
	script.id="immsgcount";
    script.src=url+"?tamp="+new Date().getTime().toString(36);
    document.getElementsByTagName("HEAD")[0].appendChild(script);
};

function init_imInit(data){
	if(data){
		document.getElementById('topbar_my_msg').innerHTML=(data!=0?'(<b>'+data+'</b>)':'');
	}
	setTimeout('init_imUpdata()',30*1000);
};