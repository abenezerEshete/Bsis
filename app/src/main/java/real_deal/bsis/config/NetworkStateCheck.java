package real_deal.bsis.config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateCheck {


    Long id = new Long (23443);


    public boolean NetworkStateCheck (Context context) {

        ConnectivityManager conMgr =
                ( ConnectivityManager ) context.getSystemService (Context.CONNECTIVITY_SERVICE);
        if (conMgr.getNetworkInfo (ConnectivityManager.TYPE_MOBILE).getState () == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo (ConnectivityManager.TYPE_WIFI).getState () == NetworkInfo.State.CONNECTED) {

            // Toast.makeText(context, "connection avaliable", Toast.LENGTH_SHORT).show();

            return true;
        } else if (conMgr.getNetworkInfo (ConnectivityManager.TYPE_MOBILE).getState () == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo (ConnectivityManager.TYPE_WIFI).getState () == NetworkInfo.State.DISCONNECTED) {
            //   Toast.makeText(context, "Connection not avaliable", Toast.LENGTH_SHORT).show();
            new Dialogs (context , "Connecion is not available");

            return false;
            // notify user you are not online
        }

        return false;
    }


}
