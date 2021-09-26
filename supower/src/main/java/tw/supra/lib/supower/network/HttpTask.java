package tw.supra.lib.supower.network;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;

import java.util.Map;

import tw.supra.lib.supower.task.Task;
import tw.supra.lib.supower.task.TaskCallBack;

/**
 * Created by supra on 15/4/13.
 */
public abstract class HttpTask<D extends HttpDelegate, I extends HttpTaskInfo, R extends HttpResult> extends Task<I, R> {


    public final D HTTP_DELEGATE = createTaskDelegateInternal();

    public HttpTask(I info, R result, TaskCallBack<I,R> callBack) {
        super(info,  result ,callBack);
    }

    @Override
    public void cancel() {
        HTTP_DELEGATE.onCancel();
    }

    @Override
    public void judgeError() {
        HTTP_DELEGATE.judgeError();
    }

    private D createTaskDelegateInternal() {
        D d = createTaskDelegate();
        if (null == d) {
            throw new IllegalStateException("createTaskDelegate can not return null");
        }
        return d;
    }

    abstract protected D createTaskDelegate();

    public int getRequestMethod() {
        return INFO.getRequestMethod();
    }

    public String getRequestUri() {
        return INFO.getRequestUri();
    }

    public void parseResponse(NetworkResponse response) {
        RESULT.parseResponse(response);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return INFO.getPostParams();
    }

    }