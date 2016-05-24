package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.DefaultSocketClient;
import client.SocketClientConstants;


//This Servlet interacts with the Client to get the list of available models
@WebServlet("/GetList")
public class GetListOfModels extends HttpServlet implements SocketClientConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7601588724582822111L;

	DefaultSocketClient defaultSocketClient = null;

	//initialize servlet, start client to connect with server
	@Override
	public void init(ServletConfig config) {
		String hostName = null;
		Socket socket = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			socket = new Socket(hostName, iPORT);
		} catch (IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + hostName);
		}
		defaultSocketClient = new DefaultSocketClient(socket);
		defaultSocketClient.start();
		
		//Sleep to wait for connection establishment
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//send command 2 from client to server and get model list
		String choice = "2";
		defaultSocketClient.sendOutput((Object) choice);
		ArrayList<String> list = (ArrayList<String>) defaultSocketClient.getInput();

		PrintWriter printWriter = response.getWriter();
		//HTML to print model list
		printWriter.println("<!DOCTYPE html><html><head>");
		printWriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		printWriter.println("<title>GetList</title>");
		printWriter.println("</head><body><CENTER>");

		printWriter.println("<H1>Please select automobile</H1>\n");
		printWriter.println("<form action=\"GetModel\" method=\"Get\">");
		printWriter.println("<select name = \"model\">");
		for (String str : list) {
			printWriter.println("<option value=\"" + str + "\">" + str + "</option>");
		}
		printWriter.println("</select>");
		printWriter.println("<input type=\"submit\" value=\"Done\">");
		printWriter.println("</form></CENTER></BODY></html>");

	}
}
