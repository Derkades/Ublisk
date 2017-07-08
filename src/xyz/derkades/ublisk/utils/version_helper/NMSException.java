package xyz.derkades.ublisk.utils.version_helper;

@Deprecated
public class NMSException extends RuntimeException {

	public NMSException(Exception cause){
		super.setStackTrace(cause.getStackTrace());
	}
	
	private static final long serialVersionUID = -6173038377847702879L;

}
