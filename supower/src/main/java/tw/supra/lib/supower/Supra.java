package tw.supra.lib.supower;

import android.content.Context;

import java.util.HashMap;

import tw.supra.lib.supower.core.AppControler;
import tw.supra.lib.supower.model.ModelManager;
import tw.supra.lib.supower.network.NetworkCenter;
import tw.supra.lib.supower.task.TaskManager;

/**
 * Created by supra on 4/10/15.
 */
public class Supra {
    private static Supra ourInstance = new Supra();

    private final HashMap<Class<?>, AppControler> CONTROLERS = new HashMap<Class<?>, AppControler>();

    public static Supra getInstance() {
        ourInstance.checkState();
        return ourInstance;
    }

    private Context mContext;

    private Supra() {
    }

    public static void init(Context c) {
        if (null == c) {
            throw new IllegalStateException("c is null");
        }
        ourInstance.mContext = c.getApplicationContext();
    }

    public static Context getContext() {
        return getInstance().mContext;
    }

    public void checkState() {
        if (mContext == null) {
            throw new IllegalStateException("controler is not inited");
        }
    }


    public static <T extends AppControler> T getControler(Class<T> clazz) {
        T controler = (T) getInstance().CONTROLERS.get(clazz);
        if (controler == null) {
            try {
                controler = (T) clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalStateException("can not instance controler ", e);
            }
            getInstance().CONTROLERS.put(clazz, controler);
        }
        return controler;
    }

    public static ModelManager getModelManager() {
        return getControler(ModelManager.class);
    }

    public static NetworkCenter getNetworkCenter() {
        return getControler(NetworkCenter.class);
    }
}