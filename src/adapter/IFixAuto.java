package adapter;

import exception.AutoException;

//Interface to fix automobile exceptions
public interface IFixAuto {
	//Helper to fix exception
	public void fix(AutoException e, int errNo);

}
