package adapter;

//Interface of creating and print automobile
public interface ICreateAuto {
	// Given a text file name can be written to build an instance of Automobile.
	public void buildAuto(String filename, boolean fileType);//true for text, false for properties

	// This function searches and prints the properties of a given Automobile
	public void printAuto(String Modelname);
}
