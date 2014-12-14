function judgeBlank(txtValue,pos)
{
	var text="~`!#$%^&*|\/?<>;:'";
	var checkStr=txtValue.value;
	if(checkStr.length==0 | checkStr==" ")	
	{
			msg="此项为必填";
			showMsg(msg,pos);
			return false;
	}
	for(i=0;i<=checkStr.length-1;i++) 
	{
		char1=checkStr.charAt(i);
		index=text.indexOf(char1);
		if(index>=0 | char1=='"') 
		{
			msg="不能包含"+char1;
			showMsg(msg,pos);
			return false;
		}
	}
	//hideMsg(pos);
	return true;
}

function judgeLength(txtValue,pos)
{
	var checkStr=txtValue.value;
	if (checkStr.length<6)
	{
			msg="至少要有6位字符组成";
			showMsg(msg,pos);
			return false;
	}
	return true;
}

var multiChar=new Array("０","１","２","３","４","５","６","７","８","９","Ｑ","Ｗ","Ｅ","Ｒ","Ｔ","Ｙ","Ｕ","Ｉ","Ｏ","Ｐ","Ａ","Ｓ","Ｄ","Ｆ","Ｇ","Ｈ","Ｊ","Ｋ","Ｌ","Ｚ","Ｘ","Ｃ","Ｖ","Ｂ","Ｎ","Ｍ","ｑ","ｗ","ｅ","ｒ","ｔ","ｙ","ｕ","ｉ","ｏ","ｐ","ａ","ｓ","ｄ","ｆ","ｇ","ｈ","ｊ","ｋ","ｌ","ｚ","ｘ","ｃ","ｖ","ｂ","ｎ","ｍ","．","－","＿","＠"," ","　");
var asciiChar=new Array("0","1","2","3","4","5","6","7","8","9","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",".","-","_","@","","");

function replaceAll(str, strFrom, strTo)
{
	return str.replace(new RegExp(strFrom, "g"), strTo);
}

function multi2ascii(TxtValue,WareName,theForm)
{
	for (var i=0;i<multiChar.length;++i)
	TxtValue=replaceAll(TxtValue, multiChar[i], asciiChar[i]);
	eval("window."+theForm+"."+WareName).value=TxtValue;
	return true;
}
function checkrname(rnameStr,pos)
{return true;}
function checkEmailFormat(emailStr,pos)
{
/*
	var checkTLD=1;
	var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;
	var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
	var validChars="\[^\\s" + specialChars + "\]";
	var quotedUser="(\"[^\"]*\")";
	var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
	var atom=validChars + '+';
	var word="(" + atom + "|" + quotedUser + ")";
	var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
	var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");
	var emailPat=/^(.+)@(.+)$/;
	var matchArray=emailStr.match(emailPat);
	var wareName=document.main.selDomain;

	if(wareName.style.display=="none")
	{
		if(matchArray==null)
		{
			msg="邮件地址错误 (请检查@ 和 .)";
			showMsg(msg,pos);
			return false;
		}
	}
	else
	{
		if(matchArray==null)
		{
			return false;
		}		
	}
	var user=matchArray[1];
	var domain=matchArray[2];

	for (i=0; i<user.length; i++)
	{
		if (user.charCodeAt(i)>127)
		{
			msg="您输入的邮箱地址中用户名包含非法字符";
			showMsg(msg,pos);
			return false;
		}
	}
	for (i=0; i<domain.length; i++)
	{
		if (domain.charCodeAt(i)>127)
		{
			msg="您输入的邮箱地址中包含非法字符";
			showMsg(msg,pos);
			return false;
		}
	}
	if (user.match(userPat)==null)
	{
		msg="请正确输入您的邮箱地址";
		showMsg(msg,pos);
		return false;
	}
	var atomPat=new RegExp("^" + atom + "$");
	var domArr=domain.split(".");
	var len=domArr.length;
	for (i=0;i<len;i++)
	{
		if (domArr[i].search(atomPat)==-1)
		{
			msg="您输入的邮箱地址不正确";
			showMsg(msg,pos);
			return false;
		}
	}
	if (checkTLD && domArr[domArr.length-1].length!=2 && domArr[domArr.length-1].search(knownDomsPat)==-1)
	{
		msg="您输入的邮件域名必须是已知域名";
		showMsg(msg,pos);
		return false;
	}
	if (len<2)
	{
		msg="这个邮件地址没有主机名";
		showMsg(msg,pos);
		return false;
	}
	if (domain == "yahoo.com.cn" | domain == "yahoo.com" )
	{
		charAddr.style.display='none';
		wareName.style.display='none';
		msg="如果您已经拥有一个雅虎帐号，可以用该帐号直接登陆"+"<a href='http://www.yahoo.com'>点击这里登陆</a>";
		showMsg(msg,pos);
		return false;
	}
	if (domain == "21cn.com" | domain == "tom.com" | domain == "mail.china.com" | domain == "163.net" | domain == "123.com" | domain == "163.com.cn" | domain == "263.com" | domain == "2911.net" | domain == "371.net" | domain == "china.com" | domain == "com.cn" | domain == "elong.com" | domain == "etang.com" | domain == "sina.con" )
	{
		msg="抱歉，您填写的信箱"+emailStr+"由于不稳定或其它原因，不建议在“过来人”使用，请选择其它信箱。";
		showMsg(msg,pos);
		return false;
	}
	document.main.email.value=user;
	charAddr.style.display='';
	wareName.style.display='';
	hideMsg(pos);
	if(domain=="sina.com" | domain=="sina.com.cn" | domain=="sina.cn" | domain=="sian.com" | domain=="sian.com" | domain=="sin.com")
	{
		wareName.selectedIndex=0;
		return false;
	}
	if(domain=="vip.sina.com")
	{
		wareName.selectedIndex=1;
		return false;
	}
	if(domain=="163.com")
	{
		wareName.selectedIndex=2;
		return false;
	}
	if(domain=="hotmail.com")
	{
		wareName.selectedIndex=3;
		return false;
	}
	if(domain=="126.com")
	{
		wareName.selectedIndex=4;
		return false;
	}
	if(domain=="sohu.com" | domain=="sohu.com.cn")
	{
		wareName.selectedIndex=5;
		return false;
	}
	if(domain=="eyou.com")
	{
		wareName.selectedIndex=6;
		return false;
	}
	if(domain=="263.net")
	{
		wareName.selectedIndex=7;
		return false;
	}
	if(domain=="qq.com")
	{
		wareName.selectedIndex=8;
		return false;
	}
	if(domain=="msn.com")
	{
		wareName.selectedIndex=9;
		return false;
	}
	if(domain=="sina100.com")
	{
		wareName.selectedIndex=10;
		return false;
	}
	if(domain=="citiz.net")
	{
		wareName.selectedIndex=11;
		return false;
	}
	if(domain=="yeah.net")
	{
		wareName.selectedIndex=12;
		return false;
	}
	if(domain=="avl.com.cn")
	{
		wareName.selectedIndex=13;
		return false;
	}
	if(domain=="vip.163.com")
	{
		wareName.selectedIndex=14;
		return false;
	}
	if(domain=="chinaren.com")
	{
		wareName.selectedIndex=15;
		return false;
	}
	if(domain=="online.sh.cn")
	{
		wareName.selectedIndex=16;
		return false;
	}
	else
	{
		document.main.email1.value=emailStr;
		charAddr.style.display='none';
		wareName.style.display='none';
	}
	*/
	return true;
}

function SelOption(theForm,txtValue,emailValue)
{
	if(eval("window."+theForm+"."+txtValue).value=="other")
	{
		charAddr.style.display='none';
		eval("window."+theForm+"."+txtValue).style.display='none';
		eval("window."+theForm+"."+emailValue).focus();
	}
}


function showMsg(msg,pos)
{
	document.getElementById('WarnMsg'+pos).innerHTML="<div id='TipError' nowrap='nowrap'>"+msg+"</div><img id='ErrorPointer' src='/img/reg/ae.gif' />";
	document.getElementById('WarnMsg'+pos).style.visibility="visible";
}
function hideMsg(pos)
{
	document.getElementById('WarnMsg'+pos).innerHTML="";
	document.getElementById('WarnMsg'+pos).style.visibility="hidden";
}

function sTip(j)
{
	for (i=1;i<5;i++)
	{
		i=i+"";
		document.getElementById("Dt"+i).style.border="1px solid #fff";
		document.getElementById("Dt"+i).style.background="#fff";
		document.getElementById("Dt"+i).style.color="#999";
	}
	document.getElementById("Dt"+j).style.border="1px solid #8cbfce";
	document.getElementById("Dt"+j).style.color="#000";
	document.getElementById("Dt"+j).style.background="#ebfafe";
	document.getElementById("Dt"+j).style.backgroundImage="url(/img/reg/tip.gif)";
	document.getElementById("Dt"+j).style.backgroundRepeat="no-repeat";
	document.getElementById("Dt"+j).style.backgroundPosition="10px 10px";
}

function eecTip(j)
{
	for (i=1;i<=11;i++)
	{
		i=i+"";
		document.getElementById("Dt"+i).style.border="1px solid #fff";
		document.getElementById("Dt"+i).style.background="#fff";
		document.getElementById("Dt"+i).style.color="#999";
	}
	document.getElementById("Dt"+j).style.border="1px solid #8cbfce";
	document.getElementById("Dt"+j).style.color="#000";
	document.getElementById("Dt"+j).style.background="#ebfafe";
	document.getElementById("Dt"+j).style.backgroundImage="url(/img/reg/tip.gif)";
	document.getElementById("Dt"+j).style.backgroundRepeat="no-repeat";
	document.getElementById("Dt"+j).style.backgroundPosition="10px 10px";
}

function haveYet(pam)
{
	document.getElementById("Tra").style.display=pam;
	document.getElementById("Trb").style.display=pam;
	document.getElementById("Trc").style.display=pam;
}
