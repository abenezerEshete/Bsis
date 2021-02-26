package real_deal.bsis.offline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import real_deal.bsis.R;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.config.LoggingInterceptor;
import real_deal.bsis.config.NetworkStateCheck;
import real_deal.bsis.main_fragment.Offline;
import real_deal.bsis.model.LoginUser;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestPort {


    Context         context;
    TextView        textNumberCount;
    String          requestFrom;
    Response        response;
    Call < String > call;

    public RestPort (Context context , TextView syncCount , String requestFrom) {

        this.context         = context;
        this.requestFrom     = requestFrom;
        this.textNumberCount = syncCount;
    }


    public RestPort (Context context) {
        this.context = context;

    }

    public boolean networkCheck () {
        return new NetworkStateCheck ().NetworkStateCheck (context);
    }


    public ApiInterface retrofitsetup () {


        OkHttpClient okHttpClient =
                new OkHttpClient.Builder ().
                        retryOnConnectionFailure (true).
                        addNetworkInterceptor (new LoggingInterceptor ()).
                        connectTimeout (20 , TimeUnit.SECONDS).
                        readTimeout (4320 , TimeUnit.MINUTES).
                        build ();

        final Retrofit retrofit = new Retrofit.Builder ().client (okHttpClient).
                baseUrl (new HardCodedValue ().URL ()).
                addConverterFactory (ScalarsConverterFactory.create ()).
                build ();
        final ApiInterface apiInterface = retrofit.create (ApiInterface.class);

        return apiInterface;
    }

    public String authString () {
        //retrive username and passwork from Database
        List < LoginUser > user = LoginUser.listAll (LoginUser.class);
        //username and password encoded by Base64
        String uNamePword = user.get (0).getUsername () + ":" + user.get (0).getPassword ();
        String encodedText =
                "Basic " + Base64.encodeToString (uNamePword.getBytes () , Base64.NO_WRAP);


        return encodedText;
    }

    public void methodCall (Callable callable , final Context context) {
        try {
            call = ( Call < String > ) callable.call ();
            new waitBackground (context , call).execute ();
            return;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

    }

    public void errorDialog (Context context , String message) {

        new AlertDialog.Builder (context)
                .setMessage (message)
                .setPositiveButton ("OK" , null)
                //.setNegativeButton("Cancel", null)
                .create ()
                .show ();
    }


    public class waitBackground extends AsyncTask < Void, Void, String > {

        Context        context;
        Call           call;
        ProgressDialog progressDialog;

        public waitBackground (Context context , Call call) {
            super ();
            this.context = context;
            this.call    = call;

        }

        @Override
        protected void onPreExecute () {
            progressDialog = new ProgressDialog (context , R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate (true);
            progressDialog.setMessage ("Loading...");
            progressDialog.show ();
            super.onPreExecute ();

        }

        @Override
        protected String doInBackground (Void... voids) {
            try {
                response = call.execute ();
                if (response.body () != null && (response.code () == 200 || response.code () == 201))
                    return response.body ().toString ();
            } catch ( Exception e ) {
                e.printStackTrace ();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute (String s) {
            super.onPostExecute (s);

            if (requestFrom.equals ("Downloadformchoise")) {
                new Offline ().parseDownloadedformChoice (s);
                onProgressUpdate ();
            } else if (requestFrom.equals ("DownloadDonors")) {
                new Offline ().parseDownloadedDonors (s , new Date ());
            }

            progressDialog.dismiss ();

        }

        @Override
        protected void onProgressUpdate (Void... values) {
            progressDialog.setMessage (" please wait...");
            super.onProgressUpdate (values);
        }

        @Override
        protected void onCancelled () {
            waitBackground.this.cancel (true);
            super.onCancelled ();
        }
    }


}


