package ec.gob.dinardap.sftp.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class FtpException extends Exception {

	private static final long serialVersionUID = -6763340162890231952L;

	public FtpException() {
	}

	public FtpException(String message) {
		super(message);
	}

	public FtpException(Throwable cause) {
		super(cause);
	}

	public FtpException(String message, Throwable cause) {
		super(message, cause);
	}

	public FtpException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
