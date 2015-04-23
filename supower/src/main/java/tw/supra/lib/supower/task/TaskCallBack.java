package tw.supra.lib.supower.task;

/**
 * Created by supra on 15/4/13.
 */
public interface TaskCallBack<I extends TaskInfo, R extends TaskResult> {
    void onTaskEvent(TaskEvent event,I info, R result);

}
