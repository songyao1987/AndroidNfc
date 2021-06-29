package com.pos.cpoc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author eric.song
 * @date 2021/5/21 13:21
 */
public class DateUtil {

    public static String getNetworkTime() throws Exception {
        URL url = new URL("http://www.bjtime.cn");
        URLConnection uc = url.openConnection();
        uc.connect();
        long ld = uc.getDate();
        Date date = new Date(ld);
        return date.getYear()+"-"+date.getMonth()+"-"+date.getDay()+"  "+
                date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
    }

    /**
     * 判断当前网络  
     * @param context
     * @return -1：没有网绿 ; 1：WIFI网络 ;  2：wap网络 ; 3：net网络
     */
    public static int getNetype(Context context){
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if( nType == ConnectivityManager.TYPE_MOBILE) {
            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){
                netType = 3;
            }else{
                netType = 2;
            }
        }else if(nType== ConnectivityManager.TYPE_WIFI){
            netType = 1;
        }
        return netType ;
    }

    public static String getSysTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getHMS(){
        Calendar calendar = Calendar.getInstance() ;
        return str2int(calendar.get(Calendar.HOUR_OF_DAY))+
                str2int(calendar.get(Calendar.MINUTE))+
                str2int(calendar.get(Calendar.SECOND)) ;
    }

    public static String getYMD(){
        Calendar calendar = Calendar.getInstance() ;
        return str2int(calendar.get(Calendar.YEAR))+
                str2int(calendar.get(Calendar.MONTH))+
                str2int(calendar.get(Calendar.SECOND)) ;
    }

    public static String str2int(int date){
        String temp = String.valueOf(date) ;
        if(temp.length() == 1){
            return "0"+temp ;
        }
        return temp;
    }

    public static String strToDateFormat(String str, String format) {
        return DateToStr(StrToDate(str), format);
    }

    /**
     * 字符串转换成日期
     * @param str //yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date StrToDate(String str, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);// "yyyy-MM-dd HH:mm:ss"
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date, String formatString) {
        String str = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);// formatString
            str = format.format(date);
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 获取当前广
     * @return
     */
    public static int getYear(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return year;
    }

    /**
     * 时间格式(打印甿)
     * @param date
     * @param time
     * @return  yyyy/dd/MM HH:mm:ss
     */
    public static String printStr(String date, String time){
        String newdate="";
        if(!StringUtil.isNullWithTrim(date)&&!StringUtil.isNullWithTrim(time)){
            if(time.length() == 5){
                newdate = date.substring(0,4)+"/"+date.substring(4,6)+"/"+date.substring(6,8)+"  "
                        +"0"+time.substring(0,1)+":"+time.substring(1,3) ;
            }else {
                newdate = date.substring(0,4)+"/"+date.substring(4,6)+"/"+date.substring(6,8)+"  "
                        +time.substring(0,2)+":"+time.substring(2,4) ;
            }
            return newdate ;
        }return "    " ;

//			if(date.length()==8&&time.length()==6){
//				newdate=date.substring(0,4)+"/"
//						+date.substring(4,6)+"/"
//						+date.substring(6,8)+" "
//						+time.substring(0,2)+":"
//						+time.substring(2,4)+":"
//						+time.substring(4,6);
//			}

    }
}
