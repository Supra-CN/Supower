package tw.supra.lib.supower.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

public class VolleyDelegateRequest extends Request<NetworkResponse> implements HttpDelegate {

    private static final String LOG_TAG = VolleyDelegateRequest.class.getSimpleName();
    private final HttpTask TASK;


    public VolleyDelegateRequest(HttpTask task) {
        super(task.getRequestMethod(), task.getRequestUri(), null);
        TASK = task;
    }

    protected void parseResponse(NetworkResponse response) {
        TASK.parseResponse(response);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = TASK.getParams();
        if(null!=params &&params.size()>0){
            return params;
        }
        return super.getParams();
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        parseResponse(response);
        return createNormalResponse(response);
    }


    protected Response<NetworkResponse> createNormalResponse(NetworkResponse response) {
        return Response.success(response,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        TASK.notifyTaskFinish();
    }

    @Override
    public void deliverError(VolleyError error) {
        dispatchVolleyError(error);
        TASK.RESULT.ERROR_FLAG.setVollyError(error);
        TASK.notifyTaskFinish(true);
        super.deliverError(error);
    }


    protected void dispatchVolleyError(VolleyError error) {
    }


    @Override
    public void onCancel() {
        cancel();
    }

    @Override
    public void judgeError() {
    }
}
