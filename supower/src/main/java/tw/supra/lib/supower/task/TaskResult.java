package tw.supra.lib.supower.task;

import android.os.Bundle;

import tw.supra.lib.supower.network.ErrorFlag;

/**
 * Created by supra on 15/4/13.
 */
public abstract class TaskResult<E extends ErrorFlag> {

    public final E ERROR_FLAG ;
    public static final float PROGRESS_INIT = 0;
    public static final float PROGRESS_MAX = 1;
    public static final float PROGRESS_HALF = (PROGRESS_INIT + PROGRESS_MAX) / 2;
    private float mProgress = PROGRESS_INIT;

    public Bundle RESULTS = new Bundle();
    public Object OBJ;

    public TaskResult(E errorFlag){
        if (errorFlag == null) {
            throw new IllegalArgumentException("errorFlag can not be null");
        }
        ERROR_FLAG = errorFlag;
    }


    public void setProgress(float progress) {
        if (progress < PROGRESS_INIT) {
            mProgress = PROGRESS_INIT;
        } else if (progress > PROGRESS_MAX) {
            mProgress = PROGRESS_MAX;
        } else {
            mProgress = progress;
        }
    }

    public float getProgress() {
        return mProgress;
    }


    @Override
    public String toString() {
        return ERROR_FLAG.toString();
    }
}
