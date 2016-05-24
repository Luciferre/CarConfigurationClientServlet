package exception;

//This class aims to fix different exceptions
public class FixHelper {
	
	public void fixMissingPrice(){
		System.out.println("Base Price Missing! Please check the input file.\n");
		//System.exit(-1);
	}
	
	public void fixMissingOptionSet(){
		System.out.println("Option Set Missing! Please check the input file.\n");
		//System.exit(-1);
	}
	
	public void fixMissingOptionPrice(){
		System.out.println("Option Price Missing! Please check the input file.\n");
		//System.exit(-1);
	}
	
	public void fixWrongFileName(){
		System.out.println("Cannot find the file! Please change the file name.\n");
		//System.exit(-1);
	}
	
	public void fixMissingOption(){
		System.out.println("Option Missing! Please check the input file.\n");
		//System.exit(-1);
	}

}
