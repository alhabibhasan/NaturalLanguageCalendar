package model;

import java.io.Serializable;
/**
 * The parser returns the description, time, date and location in an event object.
 * @author Muhammed Hasan
 *
 */
public class Event implements Serializable{
	private String description, date, time, location;

	private Event() {
	}
		public Event(String description, String date, String time, String location) {
		this.description = description;
		this.date = date;
		this.time = time;
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "Event : " + description + " | Date: " + date + " | Time: " + time + " | Location: " + location;
	}
}
