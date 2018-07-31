/*
 * Pprun's Public Domain.
 */
package org.pprun.common.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Daily used Calendar utility methods.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public final class CalendarUtil {

    private static final Log log = LogFactory.getLog(CalendarUtil.class);
    public static final String CREDIT_CARD_DATE_FORMAT = "MM/yyyy";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_FORMAT_NO_DASH = "yyyyMMdd";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ZONE_DATE_FORMAT_WITH_WEEK = "EEE yyyy-MM-dd HH:mm:ss zzz";
    public static final String ZONE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss zzz";
    public static final String TIME_FORMAT = "HH:mm";

    public static int daysBetween(Calendar startTime, Calendar endTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("startTime is null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("endTime is null");
        }
        if (startTime.compareTo(endTime) > 0) {
            throw new IllegalArgumentException("endTime is before the startTime");
        }
        return (int) ((endTime.getTimeInMillis() - startTime.getTimeInMillis())
                / (1000 * 60 * 60 * 24));
    }

    public static Calendar startOfDayTomorrow() {
        Calendar calendar = Calendar.getInstance();
        truncateDay(calendar);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    /**
     * create a calendar for start of day yesterday.
     * @return
     */
    public static Calendar startOfDayYesterday() {
        Calendar yesterday = Calendar.getInstance();
        truncateDay(yesterday);
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        return yesterday;
    }

    /**
     * Truncate the calendar's Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND to ZERO.
     * 
     * @param calendar
     * @return
     */
    public static Calendar truncateDay(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("input is null");
        }
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * format a calendar using {@link SimpleDateFormat}, with default pattern.
     * if a null calendar is passed in, empty string is returned.
     *
     * @param calendar
     * @return
     */
    public static String format(Calendar calendar) {
        String formatted = "";
        if (calendar != null) {
            formatted = new SimpleDateFormat().format(calendar.getTime());
        }
        return formatted;
    }

    /**
     * format a Time using {@link SimpleDateFormat}, with default pattern.
     * if a null calendar is passed in, empty string is returned.
     *
     * @param time
     * @return
     */
    public static String format(Time time) {
        String formatted = "";
        if (time != null) {
            formatted = new SimpleDateFormat(TIME_FORMAT).format(time.getTime());
        }
        return formatted;
    }

    /**
     * Return the String representation of the Calendar against the given format.
     * 
     * @param date
     *            the date to format, such as 'yyyy-MM-dd HH:mm:ss' for long date format with 24H
     * @param format
     *            the date format pattern
     * @return the format Date String.
     */
    public static String getDateString(Calendar calendar, String format) {
        if (calendar == null) {
            return null;
        }
        return getDateString(calendar.getTime(), format);
    }

    /**
     * Return the String representation of the Date against the given format.
     * @param date the date to format
     * @param format the date format pattern
     * @return the format Date String.
     */
    public static String getDateString(Date date, String format) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Return the String representation of the Calendar for the given format.
     * @param cal the Calendar to format
     * @param format the date format pattern, e.g., {@code "EEE yyyy-MM-dd HH:mm:ss zzz"}
     * @param timeZone the timeZone for formatter, e.g., {@code TimeZone.getTimeZone("UTC")}
     * @param locale the timeZone for formatter, e.g., {@code Locale.CHINA}, should be original from user profile
     *
     * @return the format Date String.
     */
    public static String getDateStringWithZone(Calendar cal, String format, TimeZone timeZone, Locale locale) {
        if (cal == null) {
            return null;
        }
        if (format == null) {
            format = ZONE_DATE_FORMAT_WITH_WEEK;
        }
        if (timeZone == null) {
            TimeZone.getTimeZone(CommonUtil.TIME_ZONE_UTC);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);

        sdf.setTimeZone(timeZone);
        return sdf.format(cal.getTime());
    }

    /**
     * Parses the <tt>Date</tt> from the given date string and the format pattern.
     *
     * @param dateString
     * @param pattern the date format
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Date parseDate(String dateString, String pattern) {
        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(dateString);
        } catch (ParseException ex) {
            log.error("Invalid date string: " + dateString, ex);
            throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
        }

        return date;
    }

    /**
     * Parses the <tt>Date</tt> from the given date string and the format pattern and return it as a {@link Calendar} instance.
     *
     * @param dateString
     * @param pattern the date format
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Calendar parseCalendar(String dateString, String pattern) {
        Date date = parseDate(dateString, pattern);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * Parses the <tt>Date</tt> from the given date string with the format pattern 'yyyy-MM-dd'.
     *
     * @param dateString
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Date parseShortDate(String dateString) {
        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat(SHORT_DATE_FORMAT);
            date = format.parse(dateString);
        } catch (ParseException ex) {
            log.error("Invalid date string: " + dateString, ex);
            throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
        }

        return date;
    }

    /**
     * return one day before the given date.
     * @param date
     *            the given date
     * @return the adjusted date.
     */
    public static Calendar backOneDay(Calendar date) {
        Calendar cal = (Calendar) date.clone();
        cal.add(Calendar.DATE, -1);

        return cal;
    }

    /**
     * Get how many days in current month.
     * 
     */
    public static int daysForCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        return days;
    }

    /**
     * Return the Calendar for the give date.
     * 
     * @param date
     * @return 
     */
    public static Calendar fromDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;
    }

    /**
     * return the EPOCH = "1970-01-01 00:00:00"
     * @param dateString
     * @param pattern
     * @return 
     */
    public static Calendar epoch() {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        return c;
    }
}
