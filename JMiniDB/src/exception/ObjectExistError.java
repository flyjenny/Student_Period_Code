package exception;

public class ObjectExistError extends RuntimeException {

	public ObjectExistError(String tblName) {
		super(tblName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7335434666902361689L;

}
