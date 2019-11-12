package com.adpf.modules.gdtm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 日期处理
 * @author zzj
 * @createTime 2017年1月12日下午3:18:52
 * @version
 */
public class DateUtil {

	public static final SimpleDateFormat TIME_FORMAT =
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT =
			new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATEKEY_FORMAT =
			new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat DATETIME_FORMAT =
			new SimpleDateFormat("HHmmss");

	private static final Integer ONE_DAY = 1;


	/**
	 * 获取当天日期（yyyy-MM-dd）
	 * @return 当天日期
	 */
	public static String getTodayDate() {
		return DATE_FORMAT.format(new Date());
	}


	/**
	 * 获取过去N天日期
	 * @param intervals
	 * @return
	 */
	public static ArrayList<String> getBeforeDate(int intervals) {  
		ArrayList<String> pastDaysList = new ArrayList<>();  
		for (int i = 0; i <intervals; i++) {  
			pastDaysList.add(getPastDate(i));  
		}  
		return pastDaysList;  
	}

	
	/**
	 * 获取未来N天日期
	 * @param intervals
	 * @return
	 */
	public static ArrayList<String> getFutureDate(int intervals) {  
		ArrayList<String> fetureDaysList = new ArrayList<>();  
		for (int i = 0; i <intervals; i++) {  
			fetureDaysList.add(getFetureDate(i));  
		}  
		return fetureDaysList;  
	}

	
	/** 
	 * 获取过去第几天的日期 
	 * 
	 * @param past 
	 * @return 
	 */  
	public static String getPastDate(int past) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past - ONE_DAY);  
		Date today = calendar.getTime();  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		String result = format.format(today);  
		return result;  
	}  

	
	/** 
	 * 获取未来 第 past 天的日期 
	 * @param past 
	 * @return 
	 */  
	public static String getFetureDate(int past) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past + ONE_DAY);  
		Date today = calendar.getTime();  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		String result = format.format(today);  
		return result;  
	}  

	
	/**
	 * 获取最近一周开始和结束日期
	 * @return
	 */
	public static String[] getFirstAndEndDate() {
		ArrayList<String> dates = getBeforeDate(7);
		String[] _dates = new String[2];
		if(null != dates) {
			_dates[0] = dates.get(0);
			_dates[1] = dates.get(6);
		}

		return _dates;
	}

	
	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public static List<Date> getBetweenDates(String startTime, String endTime) {
		Date start = null;
		Date end = null;
		try {
			start = DATEKEY_FORMAT.parse(startTime);
			end = DATEKEY_FORMAT.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    List<Date> result = new ArrayList<Date>();
	    result.add(start);
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(tempStart.getTime());
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    
	    result.add(end);
	    
	    return result;
	}
	
	/**
	 * 前一天
	 * @param d
	 * @return
	 */
	public static Date getBeforeDay(String d) {
		Date date = null;
		try {
			date = DATEKEY_FORMAT.parse(d);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, -1);
			date = calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 后一天
	 * @param d
	 * @return
	 */
	public static Date getNextDay(String d) {
		Date date = null;
		try {
			date = DATEKEY_FORMAT.parse(d);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, +1);
			date = calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
}

