package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import model.Automobile;

public class DefaultSocketClient extends Thread implements SocketClientConstants, SocketClientInterface {

	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket socket;
	private CarModelOptionsIO carModelOptionsIO;
	private SelectCarOption selectCarOption;
	private Automobile automobile;
	Scanner scanner = new Scanner(System.in);

	public DefaultSocketClient(Socket socket) {
		this.socket = socket;
	}// constructor

	public ObjectInputStream getReader() {
		return reader;
	}

	public void setReader(ObjectInputStream reader) {
		this.reader = reader;
	}

	public ObjectOutputStream getWriter() {
		return writer;
	}

	public void setWriter(ObjectOutputStream writer) {
		this.writer = writer;
	}

	public void run() {
		if (openConnection()) {
			//handleSession();
			//closeSession();
		}
	}// run

	// Initialization
	public boolean openConnection() {
		try {
			// Always initialize output stream and flush first
			writer = new ObjectOutputStream(socket.getOutputStream());
			writer.flush();
			reader = new ObjectInputStream(socket.getInputStream());
			this.carModelOptionsIO = new CarModelOptionsIO(writer, reader);
			this.selectCarOption = new SelectCarOption(writer, reader);

		} catch (Exception e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream to/from " + socket.getInetAddress());
			return false;
		}
		return true;
	}

	// Handle interaction with user and send command to server
	public void handleSession() {
		String choice = null;
		while (true) {
			printMenu();
			if (scanner.hasNext())
				choice = scanner.nextLine();
			switch (choice) {
			case "1":// Upload properties to server
				handleUpload(choice);
				break;

			case "2":// Get automobiles list from server
				handleGetList(choice);
				break;

			case "3":// Get automobile object from server and configure locally
				handleGetAuto(choice);
				break;

			case "4":// Build local automobile using CreateAuto interface with
						// different file type
				handleBuildLocalAuto();

			case "5":// Close session
				sendOutput((Object) choice);
				break;

			default:
				System.out.println("Please enter the number of choice!");
				break;
			}
		}

	}

	public void handleGetList(String choice) {
		sendOutput((Object) choice);
		selectCarOption.getAutoList();
	}

	// Print menu
	public void printMenu() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\nPlease choose your option:\n").append("1. Upload properites file to server\n")
				.append("2. Get automobiles list from server\n").append("3. Get automobile object from server\n")
				.append("4. Build local automobile\n").append("5. Quit");
		System.out.println(stringBuilder.toString());
	}

	// Upload properties to server
	public void handleUpload(String choice) {
		sendOutput((Object) choice);
		System.out.println("Please enter the file name:");
		String input = scanner.nextLine();
		carModelOptionsIO.uploadProperties(input);
		carModelOptionsIO.getVeryfication();
	}

	// Get automobile object from server
	public void handleGetAuto(String choice) {
		sendOutput((Object) choice);
		selectCarOption.getSelectedAuto(scanner, automobile);
	}

	// Build local automobile using CreateAuto interface with different file
	// type
	public void handleBuildLocalAuto() {
		System.out.println("Please enter the file name:");
		String fileName = scanner.nextLine();
		System.out.println("Please choose the file type: 1. Text 2. Property");
		String fileTyple = scanner.nextLine();
		switch (fileTyple) {
		case "1":// Build auto from plain text file
			carModelOptionsIO.buildLocalAuto(fileName, true);
			break;

		case "2":// Build auto from properties file
			carModelOptionsIO.buildLocalAuto(fileName, false);
			break;

		default:
			break;
		}

	}

	// Get object from socket inputstream
	public Object getInput() {
		try {
			return reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			if (DEBUG)
				System.out.println("Error writing to " + socket.getInetAddress());
		}
		return null;
	}

	// Send object to socket outputstream
	public void sendOutput(Object object) {
		try {
			writer.writeObject(object);
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Error writing to " + socket.getInetAddress());
		}
	}

	// Close session
	public void closeSession() {
		try {
			writer = null;
			reader = null;
			socket.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + socket.getInetAddress());
		}
	}

}
