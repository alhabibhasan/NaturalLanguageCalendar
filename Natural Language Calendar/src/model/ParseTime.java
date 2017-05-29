package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to parse a given time in the format morning, evening or
 * afternoon as well as any time in this format HH:MMAM/PM.
 * 
 * @author ASUS
 *
 */
public class ParseTime {
	/**
	 * The time to parse should be passed in to this method.
	 * 
	 * @param time
	 * @return
	 */
	public static String ParseTime(String time) {
		Pattern timeAM_PM = Pattern.compile("(0|1)?(\\d){1}((:)?[0-6]{2}){0,1}((a|p)m)", Pattern.CASE_INSENSITIVE);
		Pattern time24Hours = Pattern.compile("[0-2]{1}(\\d){1}:[0-6]{1}\\d{1}", Pattern.CASE_INSENSITIVE);
		Matcher match24Hours = time24Hours.matcher(time);
		if (match24Hours.find()) {
			SimpleDateFormat givenFormat = new SimpleDateFormat("HH:mm ");
			SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
			Date date;
			try {
				date = outputFormat.parse(time);
				return givenFormat.format(date).toString();
			} catch (ParseException e) {
				System.out.println("Failed to parse 24 hour time");
				e.printStackTrace();
			}

		}
		String timeToReturn = "";
		Matcher matchAM_PM = timeAM_PM.matcher(time);
		if (matchAM_PM.find()) {
			try {
				return get24Hour(time);
			} catch (ParseException e) {
				System.out.println("Invalid time");
				e.printStackTrace();
			}
		}
		switch (time.toLowerCase()) {
		case "morning":
			timeToReturn = "09:00";
			break;
		case "afternoon":
			timeToReturn = "16:00";
			break;
		case "evening":
			timeToReturn = "20:00";
			break;
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			timeToReturn = "0" + time + ":00";
			break;
		case "10":
		case "11":
		case "12":
			timeToReturn = time + ":00";
			break;
		default:
			timeToReturn = "";
			break;
		}

		return timeToReturn;
	}

	private static String get24Hour(String time) throws ParseException {
		/**
		 * We need different time formats as the provided format may be in
		 * various forms.
		 */
		if (time.length() == 7) {
			SimpleDateFormat givenFormat = new SimpleDateFormat("HH:mm ");
			SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mma");
			Date date = outputFormat.parse(time);
			return givenFormat.format(date).toString();
		} else if (time.length() == 6) {
			SimpleDateFormat givenFormat = new SimpleDateFormat("HH:mm ");
			SimpleDateFormat outputFormat = new SimpleDateFormat("h:mma");
			Date date = outputFormat.parse(time);
			return givenFormat.format(date).toString();
		} else if (time.length() == 4) {
			SimpleDateFormat givenFormat = new SimpleDateFormat("HH:mm ");
			SimpleDateFormat outputFormat = new SimpleDateFormat("hha");
			Date date = outputFormat.parse(time);
			return givenFormat.format(date).toString();
		} else if (time.length() == 3) {
			SimpleDateFormat givenFormat = new SimpleDateFormat("HH:mm ");
			SimpleDateFormat outputFormat = new SimpleDateFormat("ha");
			Date date = outputFormat.parse(time);
			return givenFormat.format(date).toString();
		} else {
			return "";
		}

	}
}
