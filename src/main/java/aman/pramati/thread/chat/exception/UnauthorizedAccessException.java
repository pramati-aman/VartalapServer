package aman.pramati.thread.chat.exception;

public class UnauthorizedAccessException extends VartalapCheckedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public UnauthorizedAccessException() {
		super();
	}


	public UnauthorizedAccessException(String message) {
		super(message);
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Unauthorized Access: "+super.toString();
	}
	
	

}
