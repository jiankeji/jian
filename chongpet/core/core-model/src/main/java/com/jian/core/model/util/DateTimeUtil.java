package com.jian.core.model.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName TimeDateUtil
 * @Description 日期时间管理
 * @version 1.0.0
 */
public class DateTimeUtil {
	private static Log log = LogFactory.getLog(DateTimeUtil.class);

	public static Calendar calendar;
	/** 日期转换格式 1990-09-09*/
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	/** 日期转换格式 1990-09-09 09*/
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	/** 日期转换格式 1990-09-09 09:19*/
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	/** 日期转换格式 1990-09-09 09:19:29*/
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/** 日期转换格式 1990-09-09 09:19:29*/
	public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";

    /**
     * @Description 获取当前时间戳
     * @return
     */
    public static String getCurrentTimeStamp(){
        String stampStr = "";
        Date mDate = new Date();
        long time = mDate.getTime();
        stampStr = String.valueOf(time);
        return stampStr;
    }

    /**
     * @Description 没有年的日期
     * @return
     * @throws ParseException
     */
    public static String formatDateAndNoYear(Date date) throws ParseException{
    	try {
    		SimpleDateFormat dateFormat = new SimpleDateFormat(MM_DD_HH_MM_SS);
    		return dateFormat.format(date);
		} catch (Exception e) {
			log.error("没有年的日期转换异常",e);
		}
    	return "";
    }

    /**
     * @Description 以yyyy-MM-dd HH:mm格式返回当前时间
     * @return
     * @throws ParseException
     */
    public static String formatCurrentDate() throws ParseException{
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(currentDate);
    }

    /**
     * @Description 根据毫秒数转换为yyyy-MM-dd HH:mm格式日期
     * @param dateTime
     * @return
     */
    public static String formatLongToDate(long dateTime){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date paramDate = new Date(dateTime);
		return dateFormat.format(paramDate);
    }

    /**
     * @Description 根据Date参数，将该日期转为yyyy-MM-dd HH:mm格式字符串
     * @param dateTime
     * @return
     */
    public static String formatDateToString(Date dateTime){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(dateTime);
    }

    /**
     * @Description 根据Date参数，将该日期转为yyyy-MM-dd HH:mm格式字符串
     * @param dateTime
     * @param format 像这样的yyyy-MM-dd格式
     * @return
     */
    public static String formatDateToString(Date dateTime, String format){
    	format = (format==null || format.length()==0) ? "" : format;
    	if (format.length() == 0) {
			log.error("formatDateToString操作,没有明确给出要转换的日期格式");
			return "";
		}
    	try {
    		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    		return dateFormat.format(dateTime);
		} catch (Exception e) {
			log.error("formatDateToString操作异常,没有明确给出要转换的日期格式",e);
			return "";
		}
    }

    /**
     * @Description 日期字符串转日期对象操作
     * @param dateStr
     * @return
     */
    public static Date formatStringToDate(String dateStr) {
    	dateStr = (dateStr==null || dateStr.length()==0) ? "" : dateStr;
    	try {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		return dateFormat.parse(dateStr);
		} catch (Exception e) {
			log.error("formatStringToDate操作异常",e);
			return null;
		}
    }

    /**
     * @Description 根据生日计算年龄
     * @param birthday
     * @return
     */
    public static int countAgeByBirthday(Date birthday) {
    	try {
    		Integer currentVal = getDateVal(Calendar.YEAR);
    		Calendar birthdayVal = Calendar.getInstance();
    		birthdayVal.setTime(birthday);
    		int age = currentVal-(birthdayVal.get(Calendar.YEAR));
    		return age;
    	} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }


    /**
     * @Description 获取当前时间年、月、日、时、分数值,如返回2018、4、13、14、03、25
     * @param CanendayType
     * @return
     */
    public static int getDateVal(int CanendayType) {
    	if (calendar == null) {
    		calendar = Calendar.getInstance();
		}
    	return calendar.get(CanendayType);
    }

	/**
	 * 计算两个时间之间的时间差
	 * @param time
	 * @return
	 */
	public static String getDistanceTime(long time){
		long day = 0;
		long hour = 0;
		long min = 0;
		if (time<60*1000){
			return "1分钟前";
		}

		if (time<=60*60*1000){
			min = ((time / (60 * 1000)));
			return min+"分钟前";
		}

		if (time<=24*60*60*1000){
			hour = (time / (60 * 60 * 1000) - day * 24);
			min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
			return hour+"小时前";//+min+"分钟前";
		}

		if (time>24*60*60*1000){
			day = time / (24 * 60 * 60 * 1000);
			//hour = (time / (60 * 60 * 1000) - day * 24);
			//min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
			return day+"天前";
		}
		return "";
	}

    public static void main(String args[]) throws Exception{

	}

    // 根据日期时间字符串计算其与当前时间的间隔，精确到分钟60 0,小时60/60 1,天60/60/24 2 传时间戳，字符串都行
	public static long countTimeSpace(String dateTime,int type){
		if(StringUtil.isEmpty(dateTime)) return -1;
		long dateTimeStamp = 0l;
		try{
			dateTimeStamp = Long.parseLong(dateTime);
		}catch(NumberFormatException e){}
		try {
			Date currentDate = new Date();
			long space = -1l;
			if(dateTimeStamp==0l){//字符串
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date paramDate = dateFormat.parse(dateTime);
				space = currentDate.getTime() - paramDate.getTime();
			}else{//时间戳
				space = currentDate.getTime() - dateTimeStamp;
			}
			switch (type) {
				case 0:
					return Math.abs(space / 1000 / 60);
				case 1:
					return Math.abs(space / 1000 / 60 / 60);
				case 2:
					return Math.abs(space / 1000 / 60 / 60 /24);
				default:
			}
		}catch (Exception e) {
			log.error("日期间隔计算操作异常：",e);
		}
		return -1l;
	}


	//日期转换 支持long type yyyy-MM-dd 0 yyyy-MM-dd HH:mm 1 yyyy-MM-dd HH:mm:ss 2
	public static Date formatDate(String dateTime,int type) {
		if(StringUtil.isEmpty(dateTime)) return null;
		long dateTimeStamp = 0l;
		try{
			dateTimeStamp = Long.parseLong(dateTime);
		}catch(NumberFormatException e){}
		try {
			String formatStr = "yyyy-MM-dd HH:mm:ss";
			switch (type){
				case 0:
					formatStr = "yyyy-MM-dd";
					break;
				case 1:
					formatStr = "yyyy-MM-dd HH:mm";
					break;
				default:
			}
			if(dateTimeStamp==0l){//字符串
				SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
				return dateFormat.parse(dateTime);
			}else{//时间戳
				return new Date(dateTimeStamp);
			}
		} catch (Exception e) {
			log.error("formatStringToDate操作异常",e);
			return null;
		}
	}

    public static long getDayNum(long time){
        long day = 0;
        day = time / (24 * 60 * 60 * 1000);
        return day;
    }

    public static long getHourNum(long time){
        long day = 0;
        long hour = 0;
        hour = (time / (60 * 60 * 1000) - day * 24);
        return hour;
    }
}
