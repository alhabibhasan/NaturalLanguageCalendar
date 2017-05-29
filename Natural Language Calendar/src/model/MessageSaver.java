package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;

import javax.swing.DefaultListModel;

public abstract class MessageSaver extends Observable {

	private String lastMessage;
	protected DefaultListModel<String> listModel;

	public MessageSaver() {
		this.lastMessage = "";
		this.listModel = new DefaultListModel<String>();
	}

	public abstract boolean updateLastMessage(String s);

	public DefaultListModel<String> getMessage() {
		return this.listModel;
	}

	public void setMessage(String message) {
		this.listModel.addElement(message);
	}

	public String removeMessage(int index) {
		try {
		return listModel.remove(index);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("List is empty");
			return null;
		}
	}

	public void saveCalData(String calOrRem) {
		try {
			FileOutputStream fileOut = new FileOutputStream("res/" + calOrRem + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(listModel);
			out.close();
			fileOut.close();
			System.out.println("Data saved.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void loadCalData(String calOrRem) {
		try {
			FileInputStream fileIn = new FileInputStream("res/" + calOrRem + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			DefaultListModel<String> loadedModel = (DefaultListModel<String>) in.readObject();
			for (int i =0; i<= loadedModel.size() - 1; i++) {
				listModel.addElement(loadedModel.getElementAt(i));
				setChanged();
				notifyObservers();
			}
			in.close();
			fileIn.close();
			System.out.println("data loaded");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DefaultListModel not found");
			cnfe.printStackTrace();
		}
	}

	public void printData() {
		for (int i =0; i <= listModel.size() - 1; i++) {
			System.out.println(listModel.getElementAt(i));
		}
		
	}

}
