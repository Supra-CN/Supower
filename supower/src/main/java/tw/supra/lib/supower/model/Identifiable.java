package tw.supra.lib.supower.model;

/**
 * 
 * @author supra
 *
 * @param <T>
 */
public interface Identifiable<T> {
	String getAuthenticatorStr();

	T getAuthenticator();

	public boolean isValid();
}
