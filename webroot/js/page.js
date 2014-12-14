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
 /*
 *@function name:翻页函数
 *@param:总页数、当前页数、总记录数、每页显示记录数、翻页数、FORM名称、隐藏翻页控件名称、图片连接地址
 *desc:rollpage可以不传，默认为20，增加了错误处理
 *调用方式：try{
 *   _do(100,1,100000,'','form1','dddd.d','http://img.taobao.net');
 *}catch(ex){
 *    alert('翻页输入参数传递错误');
 *}
 */
 function pageSlider(totalPage,currentPage,totalCount,perPage,rollpage,frmName,pageControl,imgUrlPrex){
 	//imgUrlPrex='..\images\admin'
     try{
       if ((rollpage == '') ||  isNaN(rollpage)){
         rollpage = 20;
       }
       if ((perPage == '') ||  isNaN(perPage)){
         perPage = 20;
       }
         var curPage = 0 ;
         var divisor = (currentPage - currentPage%rollpage)/rollpage;
         if ( currentPage%rollpage == 0){
             divisor = divisor - 1;
         }
         var preRollPage = ( divisor - 1 ) * rollpage + 1;
         var nextRollPage = ( divisor + 1 ) * rollpage + 1;
         var txt = "";
         txt = txt + "<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"margin-top:5px;\">";
         txt = txt + "<tr>";
         txt = txt + "<td align=\"left\" width=\"25%\">";
         if ( totalCount <= 0 ){
            txt = txt + 0 ;
         }else{
            txt = txt + ((currentPage - 1)*perPage + 1);
         }
         txt = txt + " - ";
         if ((currentPage*perPage) > totalCount ){
             txt = txt + totalCount;
         }else{
             txt = txt + (currentPage*perPage);
         }
         txt = txt + " 共 " + totalCount + " 条 " + totalPage + " 页";
         txt = txt + "</td>";
         txt = txt + "<td align=\"right\">";
         if (preRollPage > 0){
             txt = txt + "<a href=\"#?page=1\"><img src=\""+ imgUrlPrex + "/arrow4-1.gif\" border=\"0\" alt=\"首页\" onclick=\"findObj('" + pageControl + "').value=1; " + frmName + ".submit(); \"></img></a>";
             txt = txt + "&nbsp;";
             txt = txt + "<a href=\"#?page=" + preRollPage + "\"><img src=\""+ imgUrlPrex + "/arrow3-1.gif\" border=\"0\" alt=\"前一页\" onclick=\"findObj('" + pageControl + "').value="+preRollPage+"; " + frmName + ".submit(); \" align=absmiddle></img></a>";
         }else{
             txt = txt + "<img src=\""+ imgUrlPrex + "/arrow4-1.gif\" border=\"0\" alt=\"首页\" align=absmiddle></img>";
             txt = txt + "&nbsp;";
             txt = txt + "<img src=\""+ imgUrlPrex + "/arrow3-1.gif\" border=\"0\" alt=\"前一页\" align=absmiddle></img>";
         }
         txt = txt + "&nbsp;";
         for(i = 1; i <= rollpage; i++){
             curPage = rollpage*divisor + i;
             if (curPage <= totalPage){
                 if (curPage != currentPage){
                     txt = txt + "<a href=\"#?page=" +curPage+ "\" onclick=\"findObj('" + pageControl + "').value=" +curPage+ "; " + frmName + ".submit(); \">" +curPage+ "</a>";
                     txt = txt + "&nbsp;";
                 }else{
                     txt = txt + "<strong>" + curPage + "</strong>";
                     txt = txt + "&nbsp;";
                 }
             }
         }
         if ( nextRollPage <= totalPage ){
             txt = txt + "<a href=\"#?page=" + nextRollPage + "\" onclick=\"findObj('" + pageControl + "').value=" +nextRollPage+ "; " + frmName + ".submit(); \"><img src=\""+ imgUrlPrex + "/arrow1-1.gif\" border=\"0\" alt=\"后一页\"></img></a>";
             txt = txt + "&nbsp;";
             txt = txt + "<a href=\"#?page=" + totalPage + "\" onclick=\"findObj('" + pageControl + "').value=" +totalPage+ "; " + frmName + ".submit(); \"><img src=\""+ imgUrlPrex + "/arrow2-1.gif\" border=\"0\" alt=\"尾页\" align=absmiddle></img></a>";
         }else{
             txt = txt + "<img src=\""+ imgUrlPrex + "/arrow1-1.gif\" border=\"0\" alt=\"后一页\" align=absmiddle></img>";
             txt = txt + "&nbsp;";
             txt = txt + "<img src=\""+ imgUrlPrex + "/arrow2-1.gif\" border=\"0\" alt=\"尾页\" align=absmiddle></img>";
         }
         txt = txt + "</td>";
         txt = txt + "</tr>";
         txt = txt + "</table>";
         document.write(txt);
         //alert(txt);
     }catch(e){
     }
 }