var exitpop=false;
function PopPage(strURL, name, width, height, left, top)
{
    if(width==null) width=800;
    if(height==null) height=500;
    if(left==null) left = ( screen.width - width ) / 2;
    if(top==null) top  = ( screen.height - height ) / 2;
    var temp = "width="+width+",height="+height+",left="+left+",top="+top+",scrollbars=1,toolbar=no,location=no,directories=no,status=no,resizable=no";
    var w = window.open(strURL,name,temp);
    w.focus();
}
function GetCookie(cookiename)
{
    var thebigcookie = document.cookie;
    var firstchar = thebigcookie.indexOf(cookiename);
    if (firstchar != -1) {
        firstchar += cookiename.length + 1;
        lastchar = thebigcookie.indexOf(";",firstchar);
        if(lastchar == -1) lastchar = thebigcookie.length;
        return unescape(thebigcookie.substring(firstchar, lastchar));
    }
    return "";
}
function setCookie(cookiename,cookievalue,cookieexpdate,domainname)
{
    document.cookie = cookiename + "=" + cookievalue
    + "; domain=" + domainname
    + "; path=" + "/"
    + "; expires=" + cookieexpdate.toGMTString();

}
function unloadpopup(cookiename,popurl,popwidth,popheight,domainname,tr)
{
    //return;
    try {
        if (!tr)
            return;
        if( GetCookie(cookiename) == "" )
        {
            var expdate = new Date();
            expdate.setTime(expdate.getTime() + 1 * (24 * 60 * 60 * 1000)); //+1 day
            setCookie(cookiename,"yes",expdate,domainname);
            if( exitpop )
            {
                w = window.open(popurl,cookiename,"width="+popwidth+",height="+popheight+",scrollbars=1,toolbar=yes,location=yes,menubar=yes,status=yes,resizable=yes");
                w.focus;
            }
        }
    }catch (e) {}
}
function setCheckboxes(theForm, elementName, isChecked)
{
    var chkboxes = document.forms[theForm].elements[elementName];
    var count = chkboxes.length;

    if (count)
    {
        for (var i = 0; i < count; i++)
        {
            chkboxes[i].checked = isChecked;
        }
    }
    else
    {
        chkboxes.checked = isChecked;
    }
    return true;
}

var imageObject;
function ResizeImage(obj, MaxW, MaxH)
{
    if (obj != null) imageObject = obj;
    var state=imageObject.readyState;
    var oldImage = new Image();
    oldImage.src = imageObject.src;
    var dW=oldImage.width; var dH=oldImage.height;
    if(dW>MaxW || dH>MaxH) {
        a=dW/MaxW; b=dH/MaxH;
        if(b>a) a=b;
        dW=dW/a; dH=dH/a;
    }
    if(dW > 0 && dH > 0)
        imageObject.width=dW;imageObject.height=dH;
    if(state!='complete' || imageObject.width>MaxW || imageObject.height>MaxH) {
        setTimeout("ResizeImage(null,"+MaxW+","+MaxH+")",40);
    }
}

//from admin/default.vm
function sel() {
    var o = document.forms[0];
    if (!o) return;
    var e = o.elements;
    for (i = 0; i < e.length; i++) {
        if (e[i].type == 'checkbox') {
            if (e[i].checked) {
                e[i].checked = false;
            }else {
                e[i].checked = true;
            }
        }
    }
}

function reverseCheckbox(formName, eleName) {
	if(eleName==null||eleName==''){
	    var o = document.forms[formName];
	    if (!o) return;
	    var e = o.elements;
	    for (i = 0; i < e.length; i++) {
	        if (e[i].type == 'checkbox') {
	            if (e[i].checked) {
	                e[i].checked = false;
	            }else {
	                e[i].checked = true;
	            }
	        }
	    }
    }else{
    	eles = document.getElementsByName(eleName);
    	if(eles!=null){
    		for(i=0;i<eles.length;i++){
    			eles[i].checked = !eles[i].checked
    		}
    	}
    }
}

function clearDoc(formName) {
    var o = document.forms[formName];
    if (!o) return;
    var e = o.elements;
    for (i = 0; i < e.length; i++) {
        if (e[i].type == 'checkbox'){
            e[i].checked = false;
        }else if(e[i].type=='radio'){
        	e[i].checked = false;
        }else if(e[i].type=='text'){
        	e[i].value = '';
        }else if(e[i].type=='select-one'){
			// it's strange that the type name is "select-one", find by debug it
        	e[i].options[0].selected = true;
        }else if(e[i].type=='textarea'){
        	e[i].value='';
        }
    }
}
function emptyDates(obj1,obj2){
	  if(obj1){
		  document.getElementsByName("gdc_"+obj1)[0].value=''
		  document.getElementsByName(obj1)[0].value=''
		  document.getElementsByName("time_"+obj1)[0].value=''
	  }
	  if(obj2){
		  document.getElementsByName("gdc_"+obj2)[0].value=''
		  document.getElementsByName(obj2)[0].value=''
		  document.getElementsByName("time_"+obj2)[0].value=''
	  }
}

/*
   *@function name:查找页面对象函数
   *@param:控件名称
   */
function findObj(controlName) { //v1.01
    var control;
    try{
        control = document.getElementsByName(controlName)[0];
        return control;
    }catch(e){
        return null;
    }
}    

function trim(str){
    return str.replace(/^\s*|\s*$/g,"");
}
    
function sel() {
    var o = document.mainform;
    if (!o) return;
    var e = o.elements;
    for (i = 0; i < e.length; i++) {
        if (e[i].type == 'checkbox') {
            if (e[i].checked) {
                e[i].checked = false;
            }else {
                e[i].checked = true;
            }
        }
    }
}
function cleanall(){
    var ids = null;
    if(document.all){
        ids = document.forms[1].all.tags("INPUT");
    }else{
        ids = document.forms[1].elements;
    }
    for(i = 0; i < ids.length; i++){
        if(ids[i].type == "checkbox"){
            ids[i].checked = false;
        }else if(ids[i].type == "text" || ids[i].type =="hidden"){
            ids[i].value = "";
        }
    }
    var others = null;
    others = document.forms[1].all.tags("SELECT");
    for(i = 0; i < others.length; i++){
        others[i].selectedIndex = 0;
    }
}

function emptyCommonDateTime(obj1,obj2){
	  document.getElementsByName("gdc_"+obj1)[0].value='';
	  document.getElementsByName("gdc_"+obj2)[0].value='';
	  document.getElementsByName(obj1)[0].value='';
	  document.getElementsByName(obj2)[0].value='';
	  document.getElementsByName("time_"+obj1)[0].value='';
	  document.getElementsByName("time_"+obj2)[0].value='';
}

function emptyCommonDate(obj1,obj2){
	  document.getElementsByName("gdc_"+obj1)[0].value='';
	  document.getElementsByName("gdc_"+obj2)[0].value='';
	  document.getElementsByName(obj1)[0].value='';
	  document.getElementsByName(obj2)[0].value='';
}
//???????????????
function tellIsChecked(controlName,alertMsg)
    {
        var ids = null;
        var iTotal = 0;
		if (document.all) {                               
			ids = document.forms[1].all.tags("INPUT");
		}else{
			ids = document.forms[1].elements;
		}
		for (i = 0; i < ids.length; i++){
			if (ids[i].name == controlName){
				if (ids[i].checked == true){
				    iTotal ++;
				}
			}
                 }
        if ( iTotal >= 1){
           return true;
        }
        alert(alertMsg);
        return false;
     }
     
     //?????????
     function CompareStartWithEndDate(sDate,eDate,info){
     		if ( findObj(sDate).value > findObj(eDate).value){
     			alert(info);
     			return false;
     		}
     		return true;
     	}
  //???????????
 function ByteWordCount(value) {
      var txt = value;
      txt = txt.replace(/(<.*?>)/ig,'');  
      txt = txt.replace(/([\u0391-\uFFE5])/ig,'11');
      return txt.length;
}