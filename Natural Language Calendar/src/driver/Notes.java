package driver;
import view.View;
import control.Control;
import model.MessageSaver;
import model.Parse;
import model.Reminder;

/**
 * When the app launches, enter calendar inputs in the following format:
 * <Action> at <DateTime> at <Location>
 * 
 * For reminders:
 * Remind me to <Task>
 *
 */
public class Notes {
	
	public static void main(String[] args){
		
		MessageSaver r = new Reminder();
		MessageSaver sm = new Parse();
		Control c = new Control(r, sm);
		View v = new View(c);
		r.addObserver(v);
		sm.addObserver(v);
		
	}

}
