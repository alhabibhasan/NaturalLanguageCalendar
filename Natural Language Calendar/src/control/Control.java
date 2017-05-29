package control;
import model.MessageSaver;

public class Control {
	
	private String lastCalledListener;
	
	private MessageSaver reminder;
	private MessageSaver cal;
	
	public Control(MessageSaver reminder, MessageSaver calendar){
		this.reminder = reminder;
		this.cal = calendar;
	}
	
	public MessageSaver getReminder(){
		lastCalledListener = "rem";
		return this.reminder;
	}
	
	public MessageSaver getCal(){
		lastCalledListener = "cal";
		return this.cal;
	}
	
	public String getLastCalledListener(){
		return this.lastCalledListener;
	}
}


