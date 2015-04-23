package tw.supra.lib.supower.task;

import android.util.Log;

/**
 * Created by supra on 15/4/13.
 */
public abstract class Task<I extends TaskInfo, R extends TaskResult> {
    public static final String LOG_TAG = Task.class.getSimpleName();
    public final I INFO;
    public final R RESULT ;
    private TaskCallBack<I,R> mCallBack;

    public Task(I info, R result, TaskCallBack<I,R> callBack) {
        if (result == null) {
            throw new IllegalArgumentException("result can not be null");
        }
        INFO = info;
        RESULT = result;
        mCallBack = callBack;
    }

    protected void notifyTaskEvent(TaskEvent event, float progress) {
        RESULT.setProgress(progress);
        if (null != mCallBack) {
            mCallBack.onTaskEvent(event,INFO, RESULT);
        }
    }


    abstract public void cancel();

    abstract public void judgeError();

    public void notifyTaskStart() {
        RESULT.setProgress(RESULT.PROGRESS_INIT);
        if (null != mCallBack) {
            mCallBack.onTaskEvent(TaskEvent.START,INFO, RESULT);
        }
        notifyTaskEvent(TaskEvent.START, RESULT.PROGRESS_INIT);
    }

    public void notifyTaskProgressing(float progress) {
        notifyTaskEvent(TaskEvent.PROGRESSING, progress);
    }


    public void notifyTaskFinish() {
        notifyTaskFinish(false);
    }

    public void notifyTaskFinish(boolean forceCancel) {
        if (forceCancel) {
            cancel();
        }
        judgeError();
        notifyTaskEvent(TaskEvent.FINISH, RESULT.PROGRESS_MAX);
        Log.i(LOG_TAG,toString());
    }

    @Override
    public String toString() {
        return "\n\n=====\nINFO : "+INFO.toString()+"\nRESULT : "+RESULT.toString();
    }

}
