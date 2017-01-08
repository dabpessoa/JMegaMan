package me.dabpessoa.exception;

public class GameException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String reason;
	
	public GameException(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String getMessage() {
		return reason +" | "+ super.getMessage();
	}
	
}
