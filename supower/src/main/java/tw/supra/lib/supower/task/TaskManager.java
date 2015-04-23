package tw.supra.lib.supower.task;

import android.content.res.Configuration;

import tw.supra.lib.supower.core.AppControler;

/**
 * Created by supra on 15/4/13.
 */
public interface TaskManager {

    <T extends Task> void handleTask(TaskCallBack callBack, T task);

}

