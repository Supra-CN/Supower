package tw.supra.lib.supower.core;

import android.content.ComponentCallbacks2;
import android.content.Context;

import tw.supra.lib.supower.Supra;

/**
 * Created by supra on 4/10/15.
 */
public abstract class AppControler implements ComponentCallbacks2 {

    protected AppControler() {
        getContext().registerComponentCallbacks(this);
        onCreate();
    }

    abstract protected void onCreate();

    public Context getContext() {
        return Supra.getInstance().getContext();
    }



}
