package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

//Main client driver
public class Client implements SocketClientConstants {

	Socket socket;

	// Initialize socket need to connect
	public Client() {
		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			socket = new Socket(hostName, iPORT);
		} catch (IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + hostName);
		}
	}

	// run the defaultSocketClient thread
	public void runClient() {
		DefaultSocketClient defaultSocketClient = new DefaultSocketClient(socket);
		defaultSocketClient.start();
	}

	public static void main(String args[]) {
		Client client = new Client();
		client.runClient();
	}
}
