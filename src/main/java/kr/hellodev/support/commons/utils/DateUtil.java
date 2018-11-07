package kr.hellodev.support.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 6:17 PM
 */
public class DateUtil {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat STRING_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat HOUR24_FORMAT = new SimpleDateFormat("yyyyMMddHH");
    private static final SimpleDateFormat MINUTE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
    private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private static TimeZone timeZone;

    static {
        try {
            timeZone = TimeZone.getTimeZone("GMT+09:00");
        } catch (Exception ignore) {
        }
    }

    private DateUtil() {
    }

    /**
     * date의 시간을 'yyyy-MM-dd' 형태로 리턴한다.
     *
     * @return {@link String}
     */
    public static String dateFormat() {
        return dateFormat(new Date());
    }

    /**
     * date의 시간을 'yyyy-MM-dd' 형태로 리턴한다.
     *
     * @param date {@link Date}
     * @return {@link String}
     */
    public static String dateFormat(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String format() {
        return format(new Date());
    }

    public static String stringFormat() {
        return STRING_FORMAT.format(new Date());
    }

    public static String format(Date date) {
        return FORMAT.format(date);
    }

    /**
     * date의 시간을 'yyyyMMddHH24MI' 형태로 리턴한다.
     *
     * @param date date
     * @return {@link String}
     */
    public static String hourlyformat(Date date) {
        return HOUR24_FORMAT.format(date);
    }

    public static String minuteformat(Date date) {
        return MINUTE_FORMAT.format(date);
    }

    public static String dailyformat(Date date) {
        return DAY_FORMAT.format(date);
    }

    /**
     * 시작과 끝 날짜의 기간을 리턴 한다.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return {@link long}
     */
    public static long getDateTerm(Date startDate, Date endDate) {
        long start = startDate.getTime();
        long end = endDate.getTime();
        if ((end - start) < 0) {
            return -1;
        }
        return (end - start) / (1000 * 60 * 60 * 24);
    }

    /**
     * 'yyyy-MM-dd HH:mm:ss'을 Date 로 변환한다.
     *
     * @param datetime date time
     * @return {@link Date}
     */
    public static Date parse(String datetime) {
        try {
            return FORMAT.parse(datetime);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * date 의 시간을 'HH:mm:ss' 형태로 리턴한다.
     *
     * @return {@link String}
     */
    public static String timeFormat() {
        return timeFormat(new Date());
    }

    /**
     * date 의 시간을 'HH:mm:ss' 형태로 리턴한다.
     *
     * @param date date
     * @return {@link String}
     */
    public static String timeFormat(Date date) {
        return TIME_FORMAT.format(date);
    }

    /**
     * datetime 에서 숫자만큼 년도를 더한 datetime 을 리턴한다.
     *
     * @param datetime date time
     * @param year     year
     * @return {@link String}
     */
    public static String formatByYear(String datetime, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        cal.add(Calendar.YEAR, year);
        return format(cal.getTime());
    }

    /**
     * datetime 에서 숫자만큼 월을 더한 datetime 을 리턴한다.
     *
     * @param datetime {@link String}
     * @param month    month
     * @return {@link String}
     */
    public static String formatByMonth(String datetime, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        cal.add(Calendar.MONTH, month);
        return format(cal.getTime());
    }

    public static String formatDateByMonth(String datetime, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        cal.add(Calendar.MONTH, month);
        return dateFormat(cal.getTime());
    }

    /**
     * datetime 에서 숫자만큼 날짜를 더한 datetime 을 리턴한다.
     *
     * @param datetime datetime
     * @param date     date
     * @return {@link String}
     */
    public static String formatByDate(String datetime, int date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        cal.add(Calendar.DAY_OF_YEAR, date);
        return format(cal.getTime());
    }

    public static String formatDateByDay(String datetime, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        cal.add(Calendar.DAY_OF_YEAR, day);
        return dateFormat(cal.getTime());
    }

    /**
     * datetime 에서 숫자만큼 시간을 더한 datetime 을 리턴한다.
     *
     * @param datetime date time
     * @param hour     hour
     * @return {@link String}
     */
    public static String formatByHour(String datetime, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return format(cal.getTime());
    }

    /**
     * datetime 에서 숫자만큼 분을 더한 datetime 을 리턴한다.
     *
     * @param datetime date time
     * @param minute   minute
     * @return {@link String}
     */
    public static String formatByMinute(String datetime, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        cal.add(Calendar.MINUTE, minute);
        return format(cal.getTime());
    }

    public static Date dateByMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    /**
     * datetime 에서 숫자만큼 초를 더한 datetime 을 리턴한다.
     *
     * @param datetime date time
     * @param second   second
     * @return {@link String}
     */
    public static String formatBySecond(String datetime, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        cal.add(Calendar.SECOND, second);
        return format(cal.getTime());
    }

    /**
     * 해당년 해당월의 마지막 날짜 구한다.
     *
     * @param datetime date time
     * @return {@link int}
     */
    public static int getLastDayOfMonth(String datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        switch (month) {
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
                    return (29); // 2월 윤년계산을 위해서
                else
                    return (28);
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return (31);
            case 4:
            case 6:
            case 9:
            case 11:
                return (30);
            default:
                return (0);
        }
    }

    /**
     * 그달의 주차를 구한다.
     *
     * @param datetime date time
     * @return {@link int}
     */
    public static int getWeekOfMonth(String datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));

        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 입력받은 날짜의 요일을 가져온다. 일요일(1), 월요일(2), 화요일(3) ~~ 토요일(7)
     *
     * @param datetime date time
     * @return {@link int}
     */
    public static int getDayOfWeek(String datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(datetime));

        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 현재 연도값을 리턴
     *
     * @return 년(年)
     */
    public static int getCurrentYearAsInt() {

        Calendar cd = new GregorianCalendar(Locale.KOREA);

        return cd.get(Calendar.YEAR);
    }

    /**
     * 현재 월을 리턴
     *
     * @return 월(月)
     */
    public static int getCurrentMonthAsInt() {

        Calendar cd = new GregorianCalendar(Locale.KOREA);

        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 현재 일을 리턴
     *
     * @return 일(日)
     */
    public static int getCurrentDayAsInt() {

        Calendar cd = new GregorianCalendar(Locale.KOREA);

        return cd.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 현재 시간을 리턴
     *
     * @return 시(時)
     */
    public static int getCurrentHourAsInt() {

        Calendar cd = new GregorianCalendar(Locale.KOREA);

        return cd.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 현재 분을 리턴
     *
     * @return 분(分)
     */
    public static int getCurrentMinuteAsInt() {

        Calendar cd = new GregorianCalendar(Locale.KOREA);

        return cd.get(Calendar.MINUTE);
    }

    /**
     * 현재 초를 리턴
     *
     * @return 밀리초
     */
    public static int getCurrentMilliSecondAsInt() {

        Calendar cd = new GregorianCalendar(Locale.KOREA);

        return cd.get(Calendar.MILLISECOND);
    }

    /**
     * 해당 년,월,일을 받아 요일을 리턴하는 메소드
     *
     * @param sYear  년도
     * @param sMonth 월
     * @param sDay   일
     * @return 요일(한글)
     */
    public static String getDayOfWeekAsString(String sYear, String sMonth, String sDay) {

        Calendar cd = new GregorianCalendar(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay));

        SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.KOREA); // "EEE"
        // -
        // Day
        // in
        // Week

        Date d1 = cd.getTime();

        return sdf.format(d1);
    }

    /**
     * 해당 년의 특정월의 일자를 구한다.
     *
     * @param year  년도4자리
     * @param month 월 1자리 또는 2자리
     * @return 특정월의 일자
     */
    public static int getDayCountForMonth(int year, int month) {

        int[] DOMonth = {
                31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        }; // 평년
        int[] lDOMonth = {
                31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        }; // 윤년

        if ((year % 4) == 0) {

            if ((year % 100) == 0 && (year % 400) != 0) {
                return DOMonth[month - 1];
            }

            return lDOMonth[month - 1];

        } else {
            return DOMonth[month - 1];
        }
    }

    // ****** 시작일자와 종료일자 사이의 일자를 구하는 메소드군 *******//

    /**
     * 8자리로된(yyyyMMdd) 시작일자와 종료일자 사이의 일수를 구함.
     *
     * @param from 8자리로된(yyyyMMdd)시작일자
     * @param to   8자리로된(yyyyMMdd)종료일자
     * @return 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일수 리턴
     * @throws ParseException 형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
     */
    public static int getDayCount(String from, String to) throws ParseException {

        return getDayCountWithFormatter(from, to, "yyyyMMdd");
    }

    /**
     * 해당 문자열이 "yyyyMMdd" 형식에 합당한지 여부를 판단하여 합당하면 Date 객체를 리턴한다.
     *
     * @param source 대상 문자열
     * @return "yyyyMMdd" 형식에 맞는 Date 객체를 리턴한다.
     * @throws ParseException 형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
     */
    public static Date dateFormatCheck(String source) throws ParseException {

        return dateFormatCheck(source, "yyyyMMdd");
    }

    /**
     * 해당 문자열이 주어진 일자 형식을 준수하는지 여부를 검사한다.
     *
     * @param source 검사할 대상 문자열
     * @param format Date 형식의 표현. 예) "yyyy-MM-dd".
     * @return 형식에 합당하는 경우 Date 객체를 리턴한다.
     * @throws ParseException 형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
     */
    public static Date dateFormatCheck(String source, String format) throws ParseException {

        if (source == null) {
            throw new ParseException("date string to check is null", 0);
        }

        if (format == null) {
            throw new ParseException("format string to check date is null", 0);
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);

        Date date;

        try {
            date = formatter.parse(source);
        } catch (ParseException e) {
            throw new ParseException(" wrong date:\"" + source + "\" with format \"" + format + "\"", 0);
        }

        if (!formatter.format(date).equals(source)) {
            throw new ParseException("Out of bound date:\"" + source + "\" with format \"" + format + "\"", 0);
        }

        return date;
    }

    /**
     * 정해진 일자 형식을 기반으로 시작일자와 종료일자 사이의 일자를 구한다.
     *
     * @param from 시작 일자
     * @param to   종료 일자
     * @return 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일수를 리턴
     * @throws ParseException 형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
     * @see #getTimeCount(String, String, String)
     */
    public static int getDayCountWithFormatter(String from, String to, String format) throws ParseException {

        long duration = getTimeCount(from, to, format);

        return (int) (duration / (1000 * 60 * 60 * 24));
    }

    /**
     * DATE 문자열을 이용한 format string을 생성
     *
     * @param date Date 문자열
     * @return Java.text.DateFormat 부분의 정규 표현 문자열
     * @throws ParseException 형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
     */
    protected static String getFormatStringWithDate(String date) throws ParseException {
        String format = null;

        if (date.length() == 4) {
            format = "HHmm";
        } else if (date.length() == 8) {
            format = "yyyyMMdd";
        } else if (date.length() == 12) {
            format = "yyyyMMddHHmm";
        } else if (date.length() == 14) {
            format = "yyyyMMddHHmmss";
        } else if (date.length() == 17) {
            format = "yyyyMMddHHmmssSSS";
        } else {
            throw new ParseException(" wrong date format!:\"" + format + "\"", 0);
        }

        return format;
    }

    /**
     * <code>yyyyMMddHHmmssSSS</code> 와 같은 Format 문자열 없이 시작 일자 시간, 끝 일자 시간을
     *
     * @param from 시작일자
     * @param to   끝일자
     * @return 두 일자 간의 차의 밀리초(long)값
     * @throws ParseException 형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
     * @see #getFormatStringWithDate(String)
     */
    public static long getTimeCount(String from, String to) throws ParseException {

        String format = getFormatStringWithDate(from);

        return getTimeCount(from, to, format);
    }

    /**
     * 정해진 일자 형식을 기반으로 시작일자와 종료일자 사이의 일자를 구한다.
     * <p>
     * <pre>
     * Symbol   Meaning                 Presentation        Example
     * ------   -------                 ------------        -------
     * G        era designator          (Text)              AD
     * y        year                    (Number)            1996
     * M        month in year           (Text & Number)     July & 07
     * d        day in month            (Number)            10
     * h        hour in am/pm (1~12)    w(Number)            12
     * H        hour in day (0~23)      (Number)            0
     * m        minute in hour          (Number)            30
     * s        second in minute        (Number)            55
     * S        millisecond             (Number)            978
     * E        day in week             (Text)              Tuesday
     * D        day in year             (Number)            189
     * F        day of week in month    (Number)            2 (2nd Wed in July)
     * w        week in year            (Number)            27
     * W        week in month           (Number)            2
     * a        am/pm marker            (Text)              PM
     * k        hour in day (1~24)      (Number)            24
     * K        hour in am/pm (0~11)    (Number)            0
     * z        time zone               (Text)              Pacific Standard Time
     * '        escape for text         (Delimiter)
     * ''       single quote            (Literal)           '
     * </pre>
     *
     * @param from   시작 일자
     * @param to     종료 일자
     * @param format format
     * @return 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일수를 리턴
     * @throws ParseException 형식이 잘못 되었거나 존재하지 않는 날짜인 경우 발생함
     */
    public static long getTimeCount(String from, String to, String format) throws ParseException {

        Date d1 = dateFormatCheck(from, format);
        Date d2 = dateFormatCheck(to, format);

        return d2.getTime() - d1.getTime();
    }

    /**
     * 시작일자와 종료일자 사이의 해당 요일이 몇번 있는지 계산한다.
     *
     * @param from         시작 일자
     * @param to           종료 일자
     * @param dayOfTheWeek 요일
     * @return 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 일자 리턴
     * @throws ParseException 발생. 형식이 잘못 되었거나 존재하지 않는 날짜
     */
    public static int getDayOfWeekCount(String from, String to, String dayOfTheWeek) throws ParseException {
        int first = 0; // from 날짜로 부터 며칠 후에 해당 요일인지에 대한 변수
        int count = 0; // 해당 요일이 반복된 횟수
        String[] sYoil = {
                "일", "월", "화", "수", "목", "금", "토"
        };

        // 두 일자 사이의 날 수
        int betweenDays = getDayCount(from, to);

        // 첫번째 일자에 대한 요일
        Calendar cd = new GregorianCalendar(Integer.parseInt(from.substring(0, 4)), Integer.parseInt(from.substring(4, 6)) - 1, Integer.parseInt(from.substring(6, 8)));
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);

        // 요일이 3자리이면 첫자리만 취한다.
        if (dayOfTheWeek.length() == 3) {
            dayOfTheWeek = dayOfTheWeek.substring(0, 1);

            // 첫번째 해당 요일을 찾는다.
        }

        // bug fixed 2009.03.23
        // while (!sYoil[dayOfWeek - 1].equals(yoil)) {
        while (!sYoil[(dayOfWeek - 1) % 7].equals(dayOfTheWeek)) {
            dayOfWeek += 1;
            first++;
        }

        if ((betweenDays - first) < 0) {
            return 0;
        } else {
            count++;
        }
        count += (betweenDays - first) / 7;

        return count;
    }

    public static String getBaseDate(String datetime) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (datetime == null) {
            cal.setTime(new Date());
        } else {
            cal.setTime(DATE_FORMAT.parse(datetime));
        }
        cal.add(Calendar.DAY_OF_YEAR, +(7 - (getDayOfWeek(datetime))));
        return DATE_FORMAT.format(cal.getTime());
    }

    public static int getDayOfWeekWeekly(String datetime) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (datetime == null) {
            cal.setTime(new Date());
        } else {
            cal.setTime(DATE_FORMAT.parse(datetime));
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String formatDateByDayWeekly(String datetime, int day) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DATE_FORMAT.parse(datetime));
        cal.add(Calendar.DAY_OF_YEAR, day);
        return DATE_FORMAT.format(cal.getTime());
    }

    /**
     * 시작일부터 종료일까지 사이의 날짜를 배열에 담아 리턴 시작일과 종료일을 모두 포함한다. 조정현 추가 (20120814)
     *
     * @param from 시작일
     * @param to   종료일
     * @param fmt  날짜 문자 포멧(예: yyyymmdd)
     * @return fmt 포멧의 날짜가 담긴 문자열 배열
     */
    public static List<String> getDiffDaysFormat(String from, String to, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.KOREA);

        ArrayList<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Date fmdate;
        Date todate;
        try {
            fmdate = sdf.parse(from);
            todate = sdf.parse(to);
            cal.setTime(fmdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        while (fmdate.compareTo(todate) <= 0) {
            list.add(sdf.format(fmdate).substring(0, 6));
            cal.add(Calendar.MONTH, 1);
            fmdate = cal.getTime();
        }
        return list;
    }

    public static Date getDate() {
        Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);

        return cal.getTime();
    }

    public static Date getDate(long offset) {
        Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);
        cal.setTime(new Date(cal.getTime().getTime() + (offset * 1000)));

        return cal.getTime();
    }

    public static Date getDate(Date date, long offset) {
        return new Date(date.getTime() + (offset * 1000));
    }

    public static String getDateString(String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        simpleFormat.setTimeZone(timeZone);
        return simpleFormat.format(getDate());
    }

    public static String getDateString() {
        return getDateString("yyyy-MM-dd HH:mm:ss");
    }

    public static long getDateLong(String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        simpleFormat.setTimeZone(timeZone);
        return Long.parseLong(simpleFormat.format(getDate()));
    }

    public static long getDateLong() {
        return getDateLong("yyyyMMddHHmmss");
    }

    public static long getDateLongS() {
        return getDateLong("yyyyMMddHHmmssSSS");
    }

    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        GregorianCalendar cal = new GregorianCalendar(timeZone, Locale.KOREAN);
        cal.set(year, month - 1, day, hour, minute, second);
        return cal.getTime();
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        simpleFormat.setTimeZone(timeZone);
        return simpleFormat.format(date);
    }

    public static String getDateToString(String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        Calendar now = Calendar.getInstance(timeZone, Locale.KOREAN);
        return simpleFormat.format(now.getTime());
    }

    public static Date stringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        simpleFormat.setTimeZone(timeZone);
        return simpleFormat.parse(dateString);
    }

    public static long dateToLong(Date date, String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        simpleFormat.setTimeZone(timeZone);
        return Long.parseLong(simpleFormat.format(date));
    }

    public static Date longToDate(long dateLong, String format) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        simpleFormat.setTimeZone(timeZone);
        return simpleFormat.parse(Long.toString(dateLong));
    }

    public static String longToString(long dateLong, String format) throws ParseException {
        return dateToString(longToDate(dateLong, "yyyyMMddHHmmss"), format);
    }

    public static int getAfterDays(Date date1, Date date2) {
        return (int) ((date1.getTime() - date2.getTime()) / 86400000);
    }

    public static int getAfterSeconds(Date date1, Date date2) {
        return (int) ((date1.getTime() - date2.getTime()) / 1000);
    }

    public static int getAfterMilliSeconds(Date date1, Date date2) {
        return (int) (date1.getTime() - date2.getTime());
    }

    /**
     * 해당 월의 첫 번째 날짜를 구한다.
     *
     * @param year   year
     * @param month  month
     * @param format format
     * @return {@link String}
     */
    @SuppressWarnings("MagicConstant")
    public static String getCurMonthFirstDate(String year, String month, String format) {
        Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);

        int curYear = Integer.parseInt(year);
        int curMonth = Integer.parseInt(month);

        cal.set(curYear, curMonth - 1, 1);
        int curMinDay = cal.getActualMinimum(Calendar.DATE);

        Date curDate = DateUtil.getDate(curYear, curMonth, curMinDay, 0, 0, 0);

        return DateUtil.dateToString(curDate, format);
    }

    /**
     * 현재 요일을 구한다.
     *
     * @return {@link String}
     */
    public static String getDay() {
        Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);
        int curDay = cal.get(Calendar.DAY_OF_WEEK);
        String[] days = {
                "", "일", "월", "화", "수", "목", "금", "토"
        };
        return days[curDay];
    }

    /**
     * 현재 요일을 숫자로 구한다.
     *
     * @return {@link int}
     */
    public static int getIntDay() {
        Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean isPastDay(String date) {
        boolean result = false;
        try {
            Date pDate = DateUtil.stringToDate(date, "yyyyMMddHHmmss");
            Date cDate = DateUtil.getDate();
            int days = DateUtil.getAfterDays(cDate, pDate);
            result = days > 3;
        } catch (Exception ignore) {
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Date dt_month = DateUtil.stringToDate("1982-02-04 12:45:00", "yyyy-MM-dd HH:mm:ss");
        Date dt_year = DateUtil.stringToDate("1982-06-06 09:36:00", "yyyy-MM-dd HH:mm:ss");
        System.out.println(DateUtil.getAfterSeconds(dt_year, dt_month));
    }

    public static String getAfterYears(int year) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, year);
        return simpleFormat.format(now.getTime());
    }

    public static String getAfterYears(int year, String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.KOREA);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, year);
        return simpleFormat.format(now.getTime());
    }

    public static String getAfterMonths(int month) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, month);
        return simpleFormat.format(now.getTime());
    }

    public static String getAfterMonths(int month, String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.KOREA);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, month);
        return simpleFormat.format(now.getTime());
    }

    /**
     * 오늘 날짜로 부터, day 가 지난 날짜를 구함
     *
     * @param day day
     * @return {@link String}
     */
    public static String getAfterDays(int day) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, day);
        return simpleFormat.format(now.getTime());
    }

    public static String getAfterDays(int day, String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.KOREA);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, day);
        return simpleFormat.format(now.getTime());
    }

    public static String getAfterHours(int hour) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR_OF_DAY, hour);
        return simpleFormat.format(now.getTime());
    }

    public static String getAfterMinute(int minute) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minute);
        return simpleFormat.format(now.getTime());
    }

    public static String getyyyyMMddHHmmssSSS() {
        return getDateString("yyyyMMddHHmmssSSS");
    }

    public boolean isHoliday(Date date) {
        boolean isHoliday = false;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            isHoliday = true;
        }

        // Now write logic to check the date for potential
        // matches among a list of public holidays stored
        // in an external location
        return isHoliday;
    }

    public static boolean isHoliday() {
        boolean isHoliday = false;

        Calendar cal = Calendar.getInstance();

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            isHoliday = true;
        }

        // Now write logic to check the date for potential
        // matches among a list of public holidays stored
        // in an external location
        return isHoliday;
    }

    /**
     * Calendar 에서 YYYYMMDDH24MISSMMM 추출
     *
     * @param c             Calendar
     * @param isMillisecond Millisecond 추가 여부
     * @return YYYYMMDDH24MISSMMM
     */
    public static String toDTime(Calendar c, boolean isMillisecond) {

        StringBuilder sb = new StringBuilder(17);

        /* 년 */
        if (c.get(Calendar.YEAR) < 10)
            sb.append('0');
        sb.append(c.get(Calendar.YEAR));

        /* 월 */
        if (c.get(Calendar.MONTH) + 1 < 10)
            sb.append('0');
        sb.append(c.get(Calendar.MONTH) + 1);

        /* 일 */
        if (c.get(Calendar.DAY_OF_MONTH) < 10)
            sb.append('0');
        sb.append(c.get(Calendar.DAY_OF_MONTH));

        /* 시 */
        if (c.get(Calendar.HOUR_OF_DAY) < 10)
            sb.append('0');
        sb.append(c.get(Calendar.HOUR_OF_DAY));

        /* 분 */
        if (c.get(Calendar.MINUTE) < 10)
            sb.append('0');
        sb.append(c.get(Calendar.MINUTE));

        /* 초 */
        if (c.get(Calendar.SECOND) < 10)
            sb.append('0');
        sb.append(c.get(Calendar.SECOND));

        /* MILLISECOND */
        if (isMillisecond) {
            int mil = c.get(Calendar.MILLISECOND);
            if (mil == 0) {
                sb.append("000");
            } else if (mil < 10) {
                sb.append("00");
            } else if (mil < 100) {
                sb.append("0");
            }

            sb.append(mil);
        }

        return sb.toString();
    }
}
