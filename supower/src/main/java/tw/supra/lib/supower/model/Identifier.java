package tw.supra.lib.supower.model;

import android.net.Uri;
import android.text.TextUtils;

/**
 * 
 * @author supra
 *
 */
public abstract class Identifier implements Identifiable<Uri> {
	private Uri mUri;
	private String mUriStr;

	abstract protected Uri buildUri();

	private Uri buildUriInternal() {
		Uri uri = buildUri();
		if (null == uri) {
			throw new IllegalStateException("return null by buildUri()");
		}
		return uri;
	}
	
	@Override
	public String getAuthenticatorStr() {
		if(TextUtils.isEmpty(mUriStr)){
			mUriStr = getAuthenticator().toString();
		}
		return mUriStr;
	}

	@Override
	public Uri getAuthenticator() {
		if (null == mUri) {
			mUri = buildUriInternal();
		}
		return mUri;
	}

	@Override
	public boolean equals(Object o) {
		if (super.equals(o)) {
			return true;
		}

		if (null != o && o instanceof Identifier) {
			Identifier identifier = (Identifier) o;
			return getAuthenticator().equals(identifier.getAuthenticator());
		}
		return false;
	}

	@Override
	public String toString() {
		return getAuthenticator().toString();
	}
	
}
