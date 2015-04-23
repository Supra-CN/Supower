package tw.supra.lib.supower.network;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by supra on 15/4/13.
 */
public abstract class HttpJsonResult<E extends ErrorFlag> extends HttpStringResult<E> {
    public HttpJsonResult(E errorFlag) {
        super(errorFlag);
    }

    @Override
    protected void parseStringResponse(String response) {

        if (TextUtils.isEmpty(response)) {
            ERROR_FLAG.setFlag(ErrorFlag.FLAG_PARSE);
            ERROR_FLAG.setDescription("cause by response string empty");
            return;
        }

        JSONObject joResult = null;

        try {
            joResult = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            ERROR_FLAG.setFlag(ErrorFlag.FLAG_PARSE);
            ERROR_FLAG.setDescription("cause by response Json illegal");
            ERROR_FLAG.addDyingMsg(response);
            return;
        }

        try {
            parseJsonResponse(joResult);
        } catch (JSONException e) {
            e.printStackTrace();
            if (!ERROR_FLAG.isReturnByApiError()) {
                ERROR_FLAG.setFlag(ErrorFlag.FLAG_PARSE);
            }
            ERROR_FLAG.addDyingMsg("\n cause by response Json decode" + response);
            return;
        }
    }

    protected abstract void parseJsonResponse(JSONObject response) throws JSONException;

}
