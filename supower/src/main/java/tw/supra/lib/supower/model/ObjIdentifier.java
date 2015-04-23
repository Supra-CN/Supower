package tw.supra.lib.supower.model;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class ObjIdentifier<T extends ModelObj> extends Identifier {
    // schemes
    public final static String SCHEME_CONTENT = "content";
    public final static String SCHEME = "suip";
    public final static String AUTHORITY_ADJUST = "tw.supra.lib.supower.obj";

    abstract public T build();

    @Override
    protected final Uri buildUri() {
        Uri.Builder builder = new Builder();
        builder.scheme(SCHEME);
        builder.authority(defineUriAuthorityInternal());
        ArrayList<String> path = defineUriPath();
        for (String segment : path) {
            builder.appendPath(segment);
        }
        HashMap<String, String> parameters = defineUriParametersInternal();
        for (Entry<String, String> entry : parameters.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private HashMap<String, String> defineUriParametersInternal() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        HashMap<String, String> customParameters = new HashMap<String, String>();
        if (null != customParameters) {
            parameters.putAll(defineUriParameters());
        }
        return parameters;
    }

    private String defineUriAuthorityInternal() {
        String authority = defineUriAuthority();
        return TextUtils.isEmpty(authority) ? AUTHORITY_ADJUST : authority;
    }

    abstract protected String defineUriAuthority();

    abstract protected HashMap<String, String> defineUriParameters();

    abstract protected ArrayList<String> defineUriPath();

    @Override
    public boolean isValid() {
        return true;
    }

}
