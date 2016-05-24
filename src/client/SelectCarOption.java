package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import model.Automobile;

//This class contains methods to Get and Configure automobiles from server
public class SelectCarOption {

	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	public SelectCarOption(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
	}

	// Prompts the user for available models.
	@SuppressWarnings("unchecked")
	public void getAutoList() {
		ArrayList<String> list = (ArrayList<String>) getInput();
		System.out.println(list.toString());
	}

	// Allows the user to select a model and enter its respective options.
	// Displays the selected options for a class.
	public void getSelectedAuto(Scanner scanner, Automobile automobile) {
		System.out.println("Please enter the automobile name:");
		String input = scanner.nextLine();
		sendOutput((Object) input);
		automobile = (Automobile) getInput();
		automobile.print();
		System.out.println("Please enter the option set name:");
		String setName = scanner.nextLine();
		System.out.println("Please enter the option name:");
		String optionName = scanner.nextLine();
		automobile.setOptionChoice(setName, optionName);
		System.out.println("The choice of option set " + setName + " is: " + automobile.getOptionChoiceName(setName));
	}

	// Get object from socket inputsteam
	public Object getInput() {
		try {
			return objectInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// Send object to socket outputstream
	public void sendOutput(Object object) {
		try {
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
