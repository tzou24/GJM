package com.abina.basetype;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期工具类
 * 
 * @author abina
 * @date 20170309
 */
public class DateUtils {

	/** 一天毫秒数 */
	private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
	/** 一小时毫秒数 */
	private static final long HOUR_IN_MILLIS = 3600 * 1000;
	/** 一分钟毫秒数 */
	private static final long MINUTE_IN_MILLIS = 60 * 1000;
	/** 一秒毫秒数 */
	private static final long SECOND_IN_MILLIS = 1000;

	public static final SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 指定毫秒数表示的日历
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日历
	 */
	public static Calendar getCalendar(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(millis));
		return cal;
	}

	/**
	 * 时间戳转换为字符串
	 * 
	 * @param time
	 * @param simpleDateFormat
	 * @return
	 */
	public static String formatToString(Timestamp time, SimpleDateFormat simpleDateFormat) {
		if (time == null) {
			return null;
		}
		return formatToString(new Date(time.getTime()), simpleDateFormat);
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param simpleDateFormat
	 *            日期格式
	 * @return 字符串
	 */
	public static String formatToString(Date date, SimpleDateFormat simpleDateFormat) {
		if (null == date) {
			return null;
		}
		return simpleDateFormat.format(date);
	}

	/**
	 * 指定日期的默认显示，
	 * 
	 * @param calendar
	 *            指定的日期
	 * @param simpleDateFormat
	 *            格式化对象
	 * @return
	 */
	public static String formatToString(Calendar calendar, SimpleDateFormat simpleDateFormat) {
		if (calendar == null) {
			return null;
		}
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 指定毫秒数的时间戳
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数的时间戳
	 */
	public static Timestamp getTimestampInstance(long millis) {
		if (millis == 0) {
			return getTimestampInstance();
		}
		return new Timestamp(millis);
	}

	/**
	 * 指定毫秒数的时间戳
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数的时间戳
	 */
	public static Timestamp getTimestampInstance() {
		//System.nanoTime()  纳秒级时间值
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 指定毫秒数的时间戳
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定毫秒数的时间戳
	 */
	public static Timestamp getTimestampInstance(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 指定日历的时间戳
	 * 
	 * @param calendar
	 *            指定日历
	 * @return 指定日历的时间戳
	 */
	public static Timestamp getCalendarTimestamp(Calendar calendar) {
		return new Timestamp(calendar.getTime().getTime());
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param dateStr
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 */
	public static Calendar parseCalendar(String dateStr, String pattern) throws ParseException {

		Date date = parseDate(dateStr, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param dateStr
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr, String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(dateStr);
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的时间戳
	 * @throws ParseException
	 * @throws AIDateFormatException
	 */
	public static Timestamp parseTimestamp(String src, String pattern) throws ParseException {
		Date date = parseDate(src, pattern);
		return new Timestamp(date.getTime());
	}

	/**
	 * 获取传入变量对应的时间
	 * 
	 * @param var
	 *            -1-昨天，0-今天，1-明天...
	 * @param date_sdf
	 *            格式化
	 * @return
	 */
	public static String toOtherDate(int var, SimpleDateFormat date_sdf) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, var);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); //
		return date_sdf.format(date);
	}

	/**
	 * 查询范围内的日期数据, 包含前后日期
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return 集合返回
	 */
	public static List<String> scopeDateToList(String startDateStr, String endDateStr) {
		List<String> lDate = new ArrayList<String>();
		try {
			Date beginDate = parseDate(startDateStr, "yyyy-MM-dd");
			Date endDate = parseDate(endDateStr, "yyyy-MM-dd");
			if (beginDate.after(endDate)) {
				return null;
			}
			lDate.add(startDateStr);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(beginDate);
			while (endDate.after(calBegin.getTime())) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				lDate.add(formatToString(calBegin, new SimpleDateFormat("yyyy-MM-dd")));
			}
			return lDate;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 比较日期大小，进行从小到大排序。 升序排序
	 * @param dateList
	 */
	public static void dateSort(List<Date> dateList) {
		// list date排序
		Collections.sort(dateList, new Comparator<Object>() {
			@Override
			public int compare(Object obj1, Object obj2) {
				Date begin = (Date) obj1;
				Date end = (Date) obj2;
				if (begin.after(end)) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
	
	public static void main(String[] args) throws ParseException {
		List<Date> dateList = new ArrayList<Date>();
		Date parseDate1 = parseDate("20170309", "yyyyMMdd");
		dateList.add(parseDate1);
		Date parseDate2 = parseDate("20170311", "yyyyMMdd");
		dateList.add(parseDate2);
		Date parseDate3 = parseDate("20170201", "yyyyMMdd");
		dateList.add(parseDate3);
		dateSort(dateList);
		System.out.println(dateList);
	}
}
