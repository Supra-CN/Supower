package tw.supra.lib.supower.network;

import com.android.volley.AuthFailureError;

import java.util.Locale;
import java.util.Map;

/**
 * Created by supra on 15/4/13.
 */
public abstract class HttpTaskInfo extends UriBaseInfo {

    public abstract int getRequestMethod();

    @Override
    protected String getScheme() {
        return "HTTP";
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s:  Method = %d \n URL = %s \n ", getClass().getSimpleName(),
                getRequestMethod(), getRequestUri());
    }

    /**
     * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
     * {@link AuthFailureError} as authentication may be required to provide these values.
     *
     * @throws AuthFailureError in the event of auth failure
     */
   abstract protected Map<String, String> getPostParams() throws AuthFailureError;


}
