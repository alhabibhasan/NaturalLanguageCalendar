package model;


public class Reminder extends MessageSaver{


	public Reminder(){
		super();
	}

	public boolean updateLastMessage(String s){

		String input = s;

		if(s.length() > 12){
			if(input.toLowerCase().startsWith("remind me to")){
				
				String str = (input.substring(13, input.length()));				
				setMessage(str.substring(0, 1).toUpperCase() + str.substring(1));

				setChanged();
				notifyObservers();
				
				return true;
			}
		}		
		
		return false;

	}

}
