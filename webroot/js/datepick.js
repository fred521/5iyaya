var date_arr = new Array;
var days_arr = new Array;

date_arr[0] =new Option("1", 31);
date_arr[1] =new Option("2", 28);
date_arr[2] =new Option("3", 31);
date_arr[3] =new Option("4", 30);
date_arr[4] =new Option("5", 31);
date_arr[5] =new Option("6", 30);
date_arr[6] =new Option("7", 31);
date_arr[7] =new Option("8", 31);
date_arr[8] =new Option("9", 30);
date_arr[9] =new Option("10",31);
date_arr[10]=new Option("11",30);
date_arr[11]=new Option("12",31);

function fillDatePickerIndex(f){

        document.writeln("<SELECT name=\"years\" id=\"years\" onchange=\"update_days(document.mainfrm)\">")

        if (f.presetYear.value == ''){
            document.writeln("<OPTION value=\"\" selected=selected>----</OPTION>");
        }

        for(x=1990;x<2010;x++) {
            document.writeln("<OPTION value=\""+x+"\">"+x+"</OPTION>");
        }
        document.writeln("</SELECT>");

        document.writeln("&nbsp;&nbsp;<SELECT name=\"months\" id=\"months\" onchange=\"update_days(document.mainfrm)\">");

        if (f.presetMonth.value == ''){
            document.writeln("<OPTION value=\"\" selected=selected>---</OPTION>");
        }

        for(x=0;x<12;x++)
            document.writeln("<OPTION value=\""+date_arr[x].text+"\">"+date_arr[x].text+"</OPTION>");

        document.writeln("</SELECT>&nbsp;&nbsp;<SELECT name=\"days\" id=\"days\" onchange=\"create_newsletter(document.mainfrm)\"></SELECT>");
        selection=f.months[f.months.selectedIndex].value;

        update_days(f)

        //Do preset values 
        if (f.presetYear.value != ''){
            for (var i=0; i<f.years.options.length; i++){
                if (f.years.options[i].value == f.presetYear.value)
                    f.years.options[i].selected = true;
            }
        }

        if (f.presetMonth.value != ''){
            for (var i=0; i<f.months.options.length; i++){
                if (f.months.options[i].value == f.presetMonth.value)
                    f.months.options[i].selected = true;
            }
        }

        if (f.presetDay.value != ''){
            for (var i=0; i<f.days.options.length; i++){
                if (f.days.options[i].text == f.presetDay.value)
                    f.days.options[i].selected = true;
                
            }
        }

        


}



function fillDatePicker(f){

        document.writeln("<SELECT name=\"years\" id=\"years\" onchange=\"update_days(document.mainfrm)\">")

        if (f.presetYear.value == ''){
            document.writeln("<OPTION value=\"\" selected=selected>----</OPTION>");
        }

        for(x=1990;x<2010;x++) {
            document.writeln("<OPTION value=\""+x+"\">"+x+"</OPTION>");
        }
        document.writeln("</SELECT>");

        document.writeln("年&nbsp;&nbsp;&nbsp;<SELECT name=\"months\" id=\"months\" onchange=\"update_days(document.mainfrm)\">");

        if (f.presetMonth.value == ''){
            document.writeln("<OPTION value=\"\" selected=selected>---</OPTION>");
        }

        for(x=0;x<12;x++)
            document.writeln("<OPTION value=\""+date_arr[x].text+"\">"+date_arr[x].text+"</OPTION>");

        document.writeln("</SELECT>月&nbsp;&nbsp;&nbsp;<SELECT name=\"days\" id=\"days\" onchange=\"create_newsletter(document.mainfrm)\"></SELECT>日");
        selection=f.months[f.months.selectedIndex].value;

        update_days(f)

        //Do preset values 
        if (f.presetYear.value != ''){
            for (var i=0; i<f.years.options.length; i++){
                if (f.years.options[i].value == f.presetYear.value)
                    f.years.options[i].selected = true;
            }
        }

        if (f.presetMonth.value != ''){
            for (var i=0; i<f.months.options.length; i++){
                if (f.months.options[i].value == f.presetMonth.value)
                    f.months.options[i].selected = true;
            }
        }

        if (f.presetDay.value != ''){
            for (var i=0; i<f.days.options.length; i++){
                if (f.days.options[i].text == f.presetDay.value)
                    f.days.options[i].selected = true;
                
            }
        }

        


}

function fill_select(f)
{
        document.writeln("Month<SELECT name=\"months\" id=\"months\"onchange=\"update_days(document.mainfrm)\">");
        for(x=0;x<12;x++)
                document.writeln("<OPTION value=\""+date_arr[x].value+"\">"+date_arr[x].text);
        document.writeln("</SELECT>Day<SELECT name=\"days\" id=\"days\"></SELECT>Year");
        selection=f.months[f.months.selectedIndex].value;
}

function update_days(f)
{
        temp=f.days.selectedIndex;
        for(x=days_arr.length;x>0;x--)
        {
                days_arr[x]=null;
                f.days.options[x]=null;
        }

        if (f.months[f.months.selectedIndex].value != ''){
            selection=parseInt(f.months[f.months.selectedIndex].value);

            monthDays = date_arr[selection-1].value;
            monthDays = Number(monthDays);
        }else{
            monthDays = 31;
        }
        
        ret_val = 0;
        if(monthDays == 28)
        {
                year=parseInt(f.years.options[f.years.selectedIndex].value);

                if (isLeapYear(year))
                    ret_val=1;
                else
                    ret_val=0;

        }
        monthDays = monthDays + ret_val;

        pos_offset = 0;

        for(x=0;x < monthDays+1;x++)
        {

            if (x==0){
                if (f.presetDay.value == '' && f.presetMonth.value == ''){
                    days_arr[0]=new Option('--','');
                    f.days.options[0] = days_arr[0];
                    pos_offset = 1;
                }
            }else{
                    curpos   = x-1;
                    curpos_1 = x;
                    days_arr[curpos]=new Option(curpos_1);
                    f.days.options[curpos+pos_offset]=days_arr[curpos];
            }
        }
        if (temp == -1) f.days.options[0].selected=true;
        else{
            if ((temp == 29 || temp==28) && monthDays == 28 ){
                if (temp == 29){
                    f.days.options[1].selected=true;
                }else{
                    f.days.options[0].selected=true;
                }
            }else{
                f.days.options[temp].selected=true;
            }
        }
        create_newsletter(f);
}
function year_install(f)
{
        document.writeln("<SELECT name=\"years\" id=\"years\" onchange=\"update_days(document.mainfrm)\">")
        for(x=1990;x<2010;x++) document.writeln("<OPTION value=\""+x+"\">"+x);
        document.writeln("</SELECT>");
        update_days(f)
}

function isLeapYear($year)
{
       
    if (year % 4 != 0)
        return false;
    else
        if ($year % 100 != 0)
            return true;
        else
            if ($year % 400 != 0)
                return false;
            else
                return true;
} 

function create_newsletter(f){
    year=parseInt(f.years.options[f.years.selectedIndex].value);
    month=parseInt(f.months.options[f.months.selectedIndex].value);
    day=f.days.selectedIndex;
    if(!isNaN(year) && !isNaN(month) && day!=0){
        var birthday = new Date(year,month-1,day,0,0,0);
        var now = new Date();
        duration=now.getTime()-birthday.getTime();
        if(duration<52*7*86400*1000 && duration>-40*7*86400*1000){
            if(document.getElementById("newsletter")){
                document.getElementById("newsletter").innerHTML="<br/><br/><input type=\"checkbox\" class=\"Inpr\" name=\"subscribe_nl\" id=\"subscribe_nl\" value=\"1\" checked=\"checked\" /> 接受《宝宝树每周一报》邮件";
            }
        }
    }
}
