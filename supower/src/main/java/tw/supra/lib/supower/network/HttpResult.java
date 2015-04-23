package tw.supra.lib.supower.network;

import com.android.volley.NetworkResponse;

import tw.supra.lib.supower.task.TaskResult;

/**
 * Created by supra on 15/4/13.
 */
public abstract class HttpResult<E extends ErrorFlag> extends TaskResult<E> {

    public HttpResult(E errorFlag) {
        super(errorFlag);
    }

    protected abstract void parseResponse(NetworkResponse response);
}
