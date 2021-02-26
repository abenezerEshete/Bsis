package real_deal.bsis.config;

import android.app.Application;

import com.orm.SugarContext;

public class AppSugerConfig extends Application {
    @Override
    public void onCreate () {
        super.onCreate ();
        SugarContext.init (this);
    }

    @Override
    public void onTerminate () {
        super.onTerminate ();
        SugarContext.terminate ();
    }
}
