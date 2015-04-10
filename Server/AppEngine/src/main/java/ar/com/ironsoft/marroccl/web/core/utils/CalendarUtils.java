package ar.com.ironsoft.marroccl.web.core.utils;

import java.util.Calendar;

/**
 * @author Tomas de Priede
 */
public abstract class CalendarUtils {

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return false;
        }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE);
    }

    public static Calendar getInstance_firstDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar getInstance_firstDayOfNextYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,
                Calendar.getInstance().get(Calendar.YEAR) + 1);
        calendar.set(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 00);
        return calendar;
    }

    public static Calendar getInstance_firstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar getInstance_firstDayOfMonth(Integer month,
            Integer year) {
        Calendar calendar = Calendar.getInstance();
        if (year != null)
            calendar.set(Calendar.YEAR, year);
        if (month != null)
            calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar getInstance_firstDayOfPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //
        calendar.add(Calendar.MONTH, -1);
        return calendar;
    }

    public static Calendar getInstance_nextMonth(Calendar selectedMonth) {
        if (selectedMonth != null) {
            Calendar calendar = (Calendar) selectedMonth.clone();
            calendar.add(Calendar.MONTH, 1);
            return calendar;
        }
        return null;
    }
}
