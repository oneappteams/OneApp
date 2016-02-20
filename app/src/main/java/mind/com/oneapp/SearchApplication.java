package mind.com.oneapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.flurry.android.FlurryAgent;
import com.quantumgraph.sdk.QG;

import mind.com.oneapp.AppConstants.Utils;

//import com.quantumgraph.sdk.QG;

/**
 * Created by SM Idris on 09-01-2016.
 */
public class SearchApplication extends Application {

    private static SearchApplication mAppInstance = null;
    public static Context appContext;

    public static SearchApplication getInstance() {
        return mAppInstance;
    }

    public static SearchApplication get() {
        return get(appContext);
    }

    public static SearchApplication get(Context context) {
        return (SearchApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // configure Flurry
            FlurryAgent.setLogEnabled(true);

            // init Flurry
            FlurryAgent.init(this, Utils.FlurryUniqueCode);

            FlurryAgent.setLogEnabled(true);
            FlurryAgent.setLogEvents(true);

            mAppInstance = this;
            appContext = getApplicationContext();

/*        // Enable UrbanAirShip
        UAirship.takeOff(this, new UAirship.OnReadyCallback() {
            @Override
            public void onAirshipReady(UAirship airship) {

                // Enable user notifications
                airship.getPushManager().setUserNotificationsEnabled(true);
            }
        });*/
            QG qg = QG.getInstance(getApplicationContext());
            qg.onStart("d8a414c455855970de8f");
            qg.setTracking(true, true, true);
        } catch (Exception e) {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
