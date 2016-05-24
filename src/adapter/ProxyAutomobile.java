package adapter;

import java.util.LinkedHashMap;

import exception.AutoException;
import model.Automobile;
import scale.EditOptions;
import util.FileIO;

//Abstract class to implement interface methods
public abstract class ProxyAutomobile {
	// private static Automobile automobile;
	private static LinkedHashMap<String, Automobile> autoMobileList = new LinkedHashMap<>();

	// Given a text file name can be written to build an instance of Automobile.
	public void buildAuto(String fileName, boolean fileType) {
		if (fileType) {
			try {
				Automobile automobile = new FileIO().buildAutoObject(fileName);
				if (automobile != null)
					autoMobileList.put(automobile.getModel(), automobile);
			} catch (AutoException e) {
				fix(e, e.getErrorNo());
			}
		} else {
			Automobile automobile = new FileIO().buildAutoFromProperties(fileName);
			if (automobile != null)
				autoMobileList.put(automobile.getModel(), automobile);
		}

	}

	// This function searches and prints the properties of a given Automobile
	public void printAuto(String modelName) {
		Automobile automobile = autoMobileList.get(modelName);
		automobile.print();
	}

	// This function searches the Model for a given OptionSet and sets the name
	// of OptionSet to newName.
	public void updateOptionSetName(String modelName, String optionSetName, String newName) {
		Automobile automobile = autoMobileList.get(modelName);
		automobile.updateOptionSetName(optionSetName, newName);
	}

	// This function searches the Model for a given OptionSet and Option name,
	// and sets the price to newPrice.
	public void updateOptionPrice(String Modelname, String optionSetName, String optionName, float newprice) {
		Automobile automobile = autoMobileList.get(Modelname);
		int setIndex = automobile.findOptionSet(optionSetName);
		automobile.updateOptionPrice(setIndex, optionName, newprice);
	};

	// Fix autoexceptions
	public void fix(AutoException e, int errNo) {
		e.fix(errNo);
	}

	// This function edits automobile's optionset and option
	public void editAuto(int threadID, int editOptionID, String[] newOptions, String autoName) {
		Automobile autoMobile = null;
		autoMobile = autoMobileList.get(autoName);
		EditOptions editOption = new EditOptions(threadID, editOptionID, newOptions, autoMobile);
		editOption.start();

	}

}
