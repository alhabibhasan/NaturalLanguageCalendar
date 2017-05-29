package model;

import java.util.Calendar;
import java.util.Date;


public class ParseDay {
	public enum days {
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
	};

	public enum monthsAbr {
		Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
	};

	public enum months {
		January, February, March, April, May, June, July, August, September, October, November, December
	};

	/**
	 * Gets the date for today or tomorrow
	 * @param s
	 * @return
	 */
	public static String getDateTorY(String s) {
		int daysToAdd = 0;
		if (s.toLowerCase().contains("tomorrow")) daysToAdd = 1;
		Calendar calendar = Calendar.getInstance();

		Date d = calendar.getTime();
		Date newDate = new Date(d.getTime() + daysToAdd * 24 * 3600 * 1000);

		String suffix = "";
		int i = newDate.getDate();
		if (i == 01 || i == 21 || i == 31) {
			suffix = "st";
		} else if (i == 02 || i == 22) {
			suffix = "nd";
		} else if (i == 03 || i == 23) {
			suffix = "rd";
		} else {
			suffix = "th";
		}

		String output = days.values()[(newDate.getDay())] + " " + newDate.getDate() + suffix + " of "
				+ months.values()[(newDate.getMonth())];
		
		System.out.println("Date: " + output);
		
		return output;
	}

	/**
	 * Gets the date for any day other than today or tomorrow
	 * @param s
	 * @return
	 */
	public static String getDate(String s) {

		Calendar calendar = Calendar.getInstance();

		
		
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		int nextDay = days.valueOf(getDay(s)).ordinal();

		int daysToAdd = 0;

		if (nextDay > day) {
			daysToAdd = nextDay - day;
		} else {
			daysToAdd = 7 - (day - nextDay);
		}

		if (s.startsWith("next")) {
			daysToAdd += 7;
		} else if (s.startsWith("following")) {
			daysToAdd += 14;
		} else if (s.startsWith("nextWeek")) {
			boolean hasPassed = (nextDay > day);
			if (!hasPassed) {
				daysToAdd += 7;
			}
		} 

		
		
	Date d = calendar.getTime();
	Date newDate = new Date(d.getTime() + ((daysToAdd + 1) * 24 * 3600 * 1000));

	String suffix = "";
	int i = newDate.getDate();if(i==01||i==21||i==31)
	{
		suffix = "st";
	}else if(i==02||i==22)
	{
		suffix = "nd";
	}else if(i==03||i==23)
	{
		suffix = "rd";
	}else
	{
		suffix = "th";
	}

	String output = days.values()[(newDate.getDay())] + " " + newDate.getDate() + suffix + " of "
			+ months.values()[(newDate.getMonth())];

	System.out.println("Output: "+output);

	return output;

	}

	private static String getDay(String s) {
		String dayofweek = "";

		for (days day : days.values()) {
			if (s.contains(day.name().toLowerCase())) {
				dayofweek = day.name();
			}
		}

		return dayofweek;
	}

}
