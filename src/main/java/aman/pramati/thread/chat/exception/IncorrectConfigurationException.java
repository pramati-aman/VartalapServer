package aman.pramati.thread.chat.exception;

public class IncorrectConfigurationException extends VartalapRuntimeException {

	public IncorrectConfigurationException() {
		super();
	}

	public IncorrectConfigurationException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Incorrect Configuration: "+super.toString();
	}
	
	

}
