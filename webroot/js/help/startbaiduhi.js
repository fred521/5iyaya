window.startBaiduHi=(function(){
  var isIE=navigator.userAgent.indexOf("MSIE")>-1&&!window.opera, isFF=navigator.userAgent.indexOf('Firefox')!=-1;
  function getVersion(){
  	var version="";
	try{version=new ActiveXObject("WebDetect.Detect").GetVersion();}catch(ex){}
	try{if(typeof(navigator.mimeTypes['application/x-baiduhi']) == 'object' ) version= '.*';}catch(ex){}
	return version;
  }
  function B(un,iid){
  	var x=new Date(),d=x.getTime();x.setTime(d+30*1000);
    document.cookie="IM_add="+escape(un+"|youa.baidu.com|"+(new Date().getTime()).toString(36)+"|dataC2cType|{sellerUsername:'"+un+"'" + ((iid) ? ",productId:'"+iid+"'" : "") + "}") + ";domain=baidu.com;path=/;expires="+x.toGMTString();
    if(!/(^| )IM_=/.test(document.cookie)){O("http://web.im.baidu.com");}
  }
  function O(url){
	  if(O.f&&O.f.tagName=="FORM"){O.f.action=url;O.f.submit();return;}
      var d=document.createElement("DIV"); d.style.display="none";
      document.body.insertBefore(d,document.body.firstChild);
      d.innerHTML="<form name='__IM_REDIRECT_FORM' method='get' target='baidu_webim' action='"+url+"'></form>";
      O.f=document.forms["__IM_REDIRECT_FORM"];
      O.f.submit(); d=null;
	}
  function dump(object){var l = [];for (prop in object) {if(prop!="onim" && prop!="onwebim")l.push(prop + '=' + object[prop]);}return (l.length>0 ? '&'+l.join('&') : '');}
  function report(I,E,G,F){new Image().src='http://im.baidu.com/nop?type=' + I + '&sid=' + E + '&id=' + G + dump(F) + "&t=" + new Date().getTime();}
  return function(I,E,G,F){
	report(I,E,G,F);
	var v=getVersion();
    if(v==""){if (/c2cmsg|message|addcontact|addgroup|creategroup/i.test(I)) {B(G,F.iid);if(F && F.onwebim) F.onwebim();}return false;}
    if (new RegExp(v.replace(/(\.)/g, "\\."), "i").test(I)) {
		var href = "baidu://" + I + "/?sid=" + E + "&id=" + G + dump(F);
		if (isIE) href = href + '&browser=IE';
		else if (isFF) href = href + '&browser=FF&promo=c2cmsg|message|addgroup|creategroup|addcontact';
		if (F && F.onim) F.onim();
		window.location.href = href;
	}else if(I){
		O('http://im.baidu.com/promo/' + I + '.html');
	}
  }
})();