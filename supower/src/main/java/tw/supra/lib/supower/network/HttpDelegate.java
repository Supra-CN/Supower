package tw.supra.lib.supower.network;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;

import tw.supra.lib.supower.task.Task;

/**
 * Created by supra on 15/4/13.
 */
public interface HttpDelegate<T extends Task> {
   void onCancel();
   void judgeError();
}
