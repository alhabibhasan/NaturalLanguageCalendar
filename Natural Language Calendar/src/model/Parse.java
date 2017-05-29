package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class should be used to parse natural language input from the user.
 * 
 * @author Muhammed Hasan
 *
 */
public class Parse extends MessageSaver {

	private String toParse;
	private ArrayList<String> parsedOutput;
	private String description;
	private String time;
	private String eventLocation;
	private String date;

	private ArrayList<Pattern> regexDate = new ArrayList<Pattern>();
	private ArrayList<Pattern> regexTime = new ArrayList<Pattern>();
	private ArrayList<Pattern> regexEvent = new ArrayList<Pattern>();
	private ArrayList<Pattern> regexLocation = new ArrayList<Pattern>();
	private String wordedDate = "( )?(on |next |following |this |next week)?"
			+ "( )?(monday|tuesday|wednesday|thursday|friday|saturday|sunday)( )?"
			+ "(( )?[1-3]?[0-9](st|nd|rd|th))?( )?"
			+ "(january|february|march|april|may|june|july|august|september|october|november|december)?"
			+ "( [0-9]{2,4})?";
	private String tradDate = "\\d{1,2}\\/\\d{1,2}\\/\\d{2,4}";

	/**
	 * This method should be called to get a fully parsed calendar event.
	 * 
	 * @param toParse
	 *            The string to parse.
	 * @return A object of type Event which contains the description, time, date
	 *         and location of an event.
	 */

	public Parse() {
		super();
	}

	public boolean updateLastMessage(String toParse) {

		this.parsedOutput = new ArrayList<String>();
		Pattern timeAM_PM = Pattern.compile("(0|1)?(\\d){1}((:)?[0-6]{2}){0,1}((a|p)m)?", Pattern.CASE_INSENSITIVE);
		Pattern tradDateFormat = Pattern.compile(tradDate); // matches
															// DD/MM/YYYY
		Pattern wordedDateFormat = Pattern.compile(wordedDate, Pattern.CASE_INSENSITIVE);
		Pattern time24Hours = Pattern.compile("[0-2]{1}(\\d){1}:[0-6]{1}\\d{1}");
		Pattern timeOfDay = Pattern.compile("morning|evening|afternoon");
		Pattern skipDay = Pattern.compile("( )?(tomorrow|today)( )?", Pattern.CASE_INSENSITIVE);
		Pattern location = Pattern.compile("(?<=at|from)\\s[(a-zA-Z)'-]+", Pattern.CASE_INSENSITIVE);
		Pattern event = Pattern.compile(".+?(?=at|on)", Pattern.CASE_INSENSITIVE);
		this.toParse = toParse;

		regexDate.add(tradDateFormat);
		regexDate.add(wordedDateFormat);
		regexDate.add(skipDay);
		regexTime.add(timeAM_PM);
		regexTime.add(time24Hours);
		regexTime.add(timeOfDay);
		regexLocation.add(location);
		regexEvent.add(event);

		try {
			if (findDate().toLowerCase().contains("today") || findDate().toLowerCase().contains("tomorrow")) {
				date = ParseDay.getDateTorY(findDate().toLowerCase());
			} else {
				date = ParseDay.getDate(findDate().toLowerCase());
			}
		} catch (Exception e) {
			date = "";
		}
		time = ParseTime.ParseTime(findTime());
		eventLocation = findLocation();
		description = findEvent().replaceAll(wordedDate, "");

		if (time.length() == 0) {
			time = "-";
		}
		if (date.length() == 0) {
			if (findDate().matches(tradDate)) {
				date = findDate();
			} else {
				date = "-";
			}
		}
		if (eventLocation.length() == 0) {
			eventLocation = "-";
		}
		if (description.length() == 0) {
			description = "-";
		}
		System.out.println(time);
		setMessage(new Event(description, date, time, eventLocation).toString());

		setChanged();
		notifyObservers();

		return true;
	}

	private String findDate() {
		String date = "";
		for (Pattern pattern : regexDate) {
			Matcher match = pattern.matcher(toParse);
			if (match.find()) {
				date = match.group();
			}
		}
		if (date.length() > 0) {
			date = date.trim();
			date = date.substring(0, 1).toUpperCase() + date.substring(1, date.length());
		}

		return date;
	}

	private String findTime() {
		String time = "";
		for (Pattern pattern : regexTime) {
			Matcher match = pattern.matcher(toParse);
			if (match.find()) {
				time = match.group();
				System.out.println(time);
				return time;
			}
		}
		if (time.length() > 0) {
			time = time.trim();
			time = time.substring(0, 1).toUpperCase() + time.substring(1, time.length());
		}

		return time;
	}

	private String findLocation() {
		String location = "";
		for (Pattern patternLocation : regexLocation) {
			Matcher match = patternLocation.matcher(toParse);
			if (match.find()) {
				location = match.group();
			}
		}
		if (location.length() > 0) {
			location = location.trim();
			location = location.substring(0, 1).toUpperCase() + location.substring(1, location.length());
		}

		return location;
	}

	private String findEvent() {
		String event = "";
		for (Pattern pattern : regexEvent) {
			Matcher match = pattern.matcher(toParse);
			if (match.find()) {
				event = match.group();
			}
		}
		if (event.length() > 0) {
			event = event.trim();
			event = event.replaceAll("(o|O)n", "");
			event = event.substring(0, 1).toUpperCase() + event.substring(1, event.length());
		}

		return event;
	}

	public ArrayList<String> getParsedInput() {
		return this.parsedOutput;
	}
}
