package tw.supra.lib.supowersimple;

import android.app.Application;

import tw.supra.lib.supower.Supra;
import tw.supra.lib.supower.model.ModelManager;

/**
 * Created by supra on 15/4/23.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Supra.init(this);

        ModelManager modelManager = Supra.getControler(ModelManager.class);

//        UserIdentifier uIdentifier = new UserIdentifier(userId);
//        User user = modelManager.getObj(uIdentifier);
    }
}
