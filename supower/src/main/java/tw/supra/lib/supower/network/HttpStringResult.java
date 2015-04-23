package tw.supra.lib.supower.network;

import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by supra on 15/4/13.
 */
public abstract  class HttpStringResult<E extends ErrorFlag> extends HttpResult<E> {
    protected String buildRequestUrl() {
        return null;
    }

    public HttpStringResult(E errorFlag) {
        super(errorFlag);
    }

    protected abstract void parseStringResponse(String response);

    @Override
    protected void parseResponse(NetworkResponse response) {

        String responseStr;
        try {
            responseStr = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ERROR_FLAG.setFlag(ErrorFlag.FLAG_PARSE);
            ERROR_FLAG.setDescription("cause by parse body to String");
            ERROR_FLAG.addDyingMsg(String.format(
                    "\n handle a throwable \n NetworkResponse = %s \n throwable = %s", response, e));
            ERROR_FLAG.setThrowableError(e);
            return;
        }
        parseStringResponse(responseStr);
    }

}
