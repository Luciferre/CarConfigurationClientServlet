package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import adapter.BuildAuto;
import adapter.ICreateAuto;
import util.FileIO;

//This class contains client methods to build autos
public class CarModelOptionsIO {

	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	public CarModelOptionsIO(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
	}

	// Read data from the Properties file; create properties object, using the
	// load method, which transfers the object from the client to server
	public void uploadProperties(String fileName) {
		Properties properties = new FileIO().readPropertiesFile(fileName);
		uploadToServer(properties);
	}

	// sent object from client to server
	public void uploadToServer(Object object) {

		try {
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Receive a response from the Server, verifying that the Car Model object
	// is created successfully.
	public void getVeryfication() {
		Object object = null;
		try {
			object = objectInputStream.readObject();

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = (String) object;
		System.out.println(str);
	}

	// Use CreateAuto interface to build Automobile and handle different type of
	// files, passed in filetype
	public void buildLocalAuto(String fileName, boolean fileType) {
		ICreateAuto createAuto = new BuildAuto();
		createAuto.buildAuto(fileName, fileType);
	}
}
