package tw.supra.lib.supower.network;

import android.text.TextUtils;
import android.widget.Toast;

import tw.supra.lib.supower.Supra;
import tw.supra.lib.supower.task.TaskCallBack;

/**
 * Created by supra on 15/4/14.
 */
public abstract class VolleyTask<I extends HttpTaskInfo, R extends HttpResult> extends HttpTask<VolleyDelegateRequest, I, R> {

    public VolleyTask(I info, R result, TaskCallBack<I,R> callBack) {
        super(info,  result, callBack);
    }

    @Override
    protected VolleyDelegateRequest createTaskDelegate() {
        return new VolleyDelegateRequest(this);
    }



}
