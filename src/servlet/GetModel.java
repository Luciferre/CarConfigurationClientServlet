package servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.DefaultSocketClient;
import client.SocketClientConstants;
import model.Automobile;

//Servlet can interact with the Client to get the data for the list of available OptionSets.
@WebServlet("/GetModel")
public class GetModel extends HttpServlet implements SocketClientConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8924438571355564066L;

	DefaultSocketClient defaultSocketClient = null;

	// initialize servlet, start client to connect with server
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

		// Sleep to wait for connection establishment
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

		// send command 3 from client to server to get automobile object
		String choice = "3";
		defaultSocketClient.sendOutput((Object) choice);
		String modelName = request.getParameter("model");
		defaultSocketClient.sendOutput((Object) modelName);
		Automobile automobile = (Automobile) defaultSocketClient.getInput();

		// dispatch to jsp to print table of automobile
		request.getSession().setAttribute("auto", automobile);
		request.getRequestDispatcher("GetModel.jsp").forward(request, response);
	}
}
