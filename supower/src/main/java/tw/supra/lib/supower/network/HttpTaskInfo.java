package tw.supra.lib.supower.network;

import com.android.volley.error.AuthFailureError;

import java.util.Collections;
import java.util.HashMap;
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
        return String.format("%s:  Method = %d \n URL = %s \n ", getClass().getSimpleName(),
                getRequestMethod(), getRequestUri());
    }

    /**
     * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
     * {@link com.android.volley.error.AuthFailureError} as authentication may be required to provide these values.
     *
     * @throws com.android.volley.error.AuthFailureError in the event of auth failure
     */
   abstract protected Map<String, String> getPostParams() throws AuthFailureError ;


}
