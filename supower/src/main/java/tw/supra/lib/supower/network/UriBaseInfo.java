package tw.supra.lib.supower.network;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tw.supra.lib.supower.task.TaskInfo;

/**
 * Created by supra on 15/4/13.
 */
public abstract class UriBaseInfo implements TaskInfo {
    private String mRequestUri;
    public Bundle ARGS = new Bundle();
    public Object OBJ;

    public String getRequestUri() {
        if (TextUtils.isEmpty(mRequestUri)) {
            mRequestUri = buildRequestUri();
        }
        return mRequestUri;
    }

    protected abstract void fillQueryParamters(HashMap<String, String> paramters);
    protected abstract String getScheme();
    protected abstract String getAuthority();
    abstract protected ArrayList<String> getUriPath();


    private void fillQueryParamtersInternal(HashMap<String, String> paramters) {
//		paramters.put("client", APIDef.CLIENT_ANDROID);
//		paramters.put("api_ver",APIDef.API_VER);
        fillQueryParamters(paramters);
    }

    protected String buildRequestUri() {
        Uri uri;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getScheme());
        builder.encodedAuthority(getAuthority());
        builder.path("/");
        ArrayList<String> path = getUriPath();
        for (String segment : path) {
            builder.appendPath(segment);
        }
        HashMap<String, String> paramters = new HashMap<String, String>();
        fillQueryParamtersInternal(paramters);
        for (Map.Entry<String, String> entry : paramters.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        uri = builder.build();
        return uri.toString();
    }

    @Override
    public String toString() {
        return "URL = "+getRequestUri();
    }
}
