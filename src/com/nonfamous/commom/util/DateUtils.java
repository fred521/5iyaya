package com.nonfamous.commom.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author fred
 * @version $Id: DateUtils.java,v 1.3 2009/09/12 05:12:58 fred Exp $
 */
public class DateUtils {

    private static class Formats {
        // ����ʱ��
        private final DateFormat simple          = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // ������
        private final DateFormat dtSimple        = new SimpleDateFormat("yyyy-MM-dd");

        // ������
        private final DateFormat dtSimpleChinese = new SimpleDateFormat("yyyy��MM��dd��");

        private final DateFormat dtFullChinese   = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��");

        // ������(���»���)
        private final DateFormat dtShort         = new SimpleDateFormat("yyyyMMdd");

        // ʱ����
        private final DateFormat hmsFormat       = new SimpleDateFormat("HH:mm:ss");

        private final DateFormat simpleFormat    = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        private final DateFormat gmtFormat    = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    }

    private static final ThreadLocal<Formats> local = new ThreadLocal<Formats>();

    private static final Formats getFormats() {
        Formats f = local.get();
        if (f == null) {
            f = new Formats();
            local.set(f);
        }
        return f;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * 
     * @return
     */
    public static final String simpleFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormats().simple.format(date);
    }

    /**
     * ����������ʱ���ʽ���ַ���ת��Ϊ����
     * @param strDate
     * @return
     */
    public static final Date gmtFormatStringDate(String strDate){
    	try {
			return getFormats().gmtFormat.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
    }
    /**
     * yyyy-MM-dd
     * 
     * @param date
     * 
     * @return
     */
    public static final String dtSimpleFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormats().dtSimple.format(date);
    }

    /**
     * yyyy��MM��dd��
     * 
     * @param date
     * 
     * @return
     */
    public static final String dtSimpleChineseFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormats().dtSimpleChinese.format(date);
    }
    
    public static final String frontFullChineseDate(Date date) {
        try {
            return getFormats().dtFullChinese.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static final String frontFullChineseDate() {
        try {
            return getFormats().dtFullChinese.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * yyyy-MM-dd �����ַ�ת��Ϊʱ��
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final Date string2Date(String stringDate) {
        try {
            if (stringDate == null) {
                return null;
            }
            return getFormats().dtSimple.parse(stringDate);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * ��������ʱ�䣨�ַ�����ʱ��yyyy-MM-dd HH:mm:ss��
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final Date string2DateTime(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }

        return getFormats().simple.parse(stringDate);
    }

    /**
     * yyyy��MM��dd�� �����ַ�ת��Ϊʱ��
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final Date chineseString2Date(String stringDate) {
        if (stringDate == null) {
            return null;
        }
        try {
            return getFormats().dtSimpleChinese.parse(stringDate);
        } catch (ParseException e) {

        }
        return null;
    }

    /**
     * ���ض����ڸ�ʽ��yyyyMMdd��ʽ��
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final String shortDate(Date Date) {
        if (Date == null) {
            return null;
        }

        return getFormats().dtShort.format(Date);
    }

    /**
     * yyyy-MM-dd �����ַ�ת��Ϊ������
     * 
     * @param stringDate
     * 
     * @return
     * 
     * @throws ParseException
     */
    public static final Long string2DateLong(String stringDate) throws ParseException {
        Date d = string2Date(stringDate);

        if (d == null) {
            return null;
        }

        return new Long(d.getTime());
    }

    /**
     * ����ת��Ϊ�ַ��� HH:mm:ss
     * 
     * @param date
     * 
     * @return
     */
    public static final String hmsFormat(Date date) {
        if (date == null) {
            return "";
        }

        return getFormats().hmsFormat.format(date);
    }

    /**
     * ʱ��ת���ַ��� 2005-06-30 15:50
     * 
     * @param date
     * 
     * @return
     */
    public static final String simpleDate(Date date) {
        if (date == null) {
            return "";
        }

        return getFormats().simpleFormat.format(date);
    }

    /**
     * ��ȡ��ǰ���ڵ����ڲ� now= 2005-07-19 diff = 1 -> 2005-07-20 diff = -1 -> 2005-07-18
     * 
     * @param diff
     * 
     * @return
     */
    public static final String getDiffDate(int diff) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, diff);
        return dtSimpleFormat(c.getTime());
    }
    
    /**
     * ��ȡ����ʱ�����diff���ʱ��
     * 
     * @param diff
     * 
     * @return
     */
    public static final Date getDiffDateFromEnterDate(Date enterDate,int diff) {
        Calendar c = Calendar.getInstance();

        c.setTime(enterDate);
        c.add(Calendar.DATE, diff);
        return c.getTime();
    }

    /**
     * ��ȡ��ǰʱ�������ľ���ʱ��
     * 
     * @param diff
     * @return
     */
    public static final Date getDiffCurrentDate(int diff) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, diff);
        return c.getTime();
    }

    /**
     * ��ȡ����ʱ����·ݵĵ�һ��
     * 
     * @param dt
     * @param mon
     * @return
     */
    public static final Date getMonthFirstDay(Date dt, int mon) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONDAY, mon);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return string2Date(dtSimpleFormat(c.getTime()));
    }

    /**
     * ���ݵ�ǰ���ڻ�ȡ���ӻ���ٺ����ݣ����ظ�ʽ��2007.01��2007.10 getYearMonWithoutDot(new Date(),0) =
     * "2007.06" getYearMonWithoutDot(new Date(),-1) = "2007.05"
     * getYearMonWithoutDot(new Date(),1) = "2007.07"
     * 
     * @param dt
     *            ��Ҫ�����ʱ����������
     * @param mon
     *            ��Ҫ���ӵ��·���
     * @return
     */
    public static final String getYearMon(Date dt, int mon) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONDAY, mon);
        String year = String.valueOf(c.get(Calendar.YEAR));
        int month = c.get(Calendar.MONTH) + 1;
        String sMonth;
        if (month < 10) {
            sMonth = "0" + String.valueOf(month);
        } else {
            sMonth = String.valueOf(month);
        }
        return year + "." + sMonth;
    }

    /**
     * ���ݵ�ǰ���ڻ�ȡ���ӻ���ٺ����ݣ����ظ�ʽ��200701��200710 getYearMonWithoutDot(new Date(),0) =
     * "200706" getYearMonWithoutDot(new Date(),-1) = "200705"
     * getYearMonWithoutDot(new Date(),1) = "200707"
     * 
     * @param dt
     *            ��Ҫ�����ʱ����������
     * @param mon
     *            ��Ҫ���ӵ��·���
     * @return
     */
    public static final String getYearMonWithoutDot(Date dt, int mon) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONDAY, mon);
        String year = String.valueOf(c.get(Calendar.YEAR));
        int month = c.get(Calendar.MONTH) + 1;
        String sMonth;
        if (month < 10) {
            sMonth = "0" + String.valueOf(month);
        } else {
            sMonth = String.valueOf(month);
        }
        return year + "" + sMonth;
    }

    /**
     * ���ص�ǰ�·ݣ��� 01��02
     * 
     * @return
     */
    public static final String getCurrentMon() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        String sMonth;
        if (month < 10) {
            sMonth = "0" + String.valueOf(month);
        } else {
            sMonth = String.valueOf(month);
        }
        return sMonth;
    }

    public static int getBetweenDate(Date start, Date endDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        int end = c.get(Calendar.YEAR) * 365 + c.get(Calendar.DAY_OF_YEAR);
        c.setTime(start);
        return end - (c.get(Calendar.YEAR) * 365 + c.get(Calendar.DAY_OF_YEAR));
    }
    /**
     * compare with the current date
     * @param start
     * @return the int value
     */
    public static int getBetweenDate(Date start) {
    	Date currentDate=new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int end = c.get(Calendar.YEAR) * 365 + c.get(Calendar.DAY_OF_YEAR);
        c.setTime(start);
        return end - (c.get(Calendar.YEAR) * 365 + c.get(Calendar.DAY_OF_YEAR));
    }

    public static String getYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static void main(String[] argus) {
        
        System.out.println(DateUtils.chineseString2Date("2007��6��4��"));

    }
}
