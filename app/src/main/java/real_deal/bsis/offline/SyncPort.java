package real_deal.bsis.offline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.orm.query.Select;

import org.json.JSONObject;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import real_deal.bsis.R;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.config.NetworkStateCheck;
import real_deal.bsis.model.LoginUser;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SyncPort {


    Context         context;
    Response        response;
    Object          object;
    JSONObject      jsonObject;
    Call < String > call;


    public SyncPort (Context context) {
        this.context = context;
    }

    public SyncPort () {
    }

    public boolean networkCheck () {
        return new NetworkStateCheck ().NetworkStateCheck (context);
    }

    public ApiInterface retrofitsetup () {

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder ().connectTimeout (20 , TimeUnit.SECONDS).readTimeout (6000 , TimeUnit.SECONDS).build ();

        final Retrofit retrofit = new Retrofit.Builder ().client (okHttpClient).
                baseUrl (new HardCodedValue ().URL ()).
                addConverterFactory (ScalarsConverterFactory.create ()).
                build ();
        final ApiInterface apiInterface = retrofit.create (ApiInterface.class);

        return apiInterface;
    }

    public String authString () {
        //retrive username and passwork from Database
        LoginUser user = Select.from (LoginUser.class).first ();
        //username and password encoded by Base64

        String uNamePword;
        if (user.getOldPassword () != null) {
            uNamePword = user.getUsername () + ":" + user.getOldPassword ();
        } else {
            uNamePword = user.getUsername () + ":" + user.getPassword ();
        }
        String encodedText =
                "Basic " + Base64.encodeToString (uNamePword.getBytes () , Base64.NO_WRAP);


        System.out.println ("encoed tect ==" + user.getPassword ());
        System.out.println ("encoed tect ==" + user.getUsername ());
        System.out.println ("encoed tect ==" + encodedText);

        return encodedText;
    }

    public Response methodCall (Callable callable , final Context context) {
        try {
            call = ( Call < String > ) callable.call ();
            return new waitBackground (context , call).execute ().get ();

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return null;
    }


    public void errorDialog (Context context , String message) {

        new AlertDialog.Builder (context)
                .setMessage (message)
                .setPositiveButton ("OK" , null)
                //.setNegativeButton("Cancel", null)
                .create ()
                .show ();
    }


    public class waitBackground extends AsyncTask < Void, Void, Response > {

        Context        context;
        Call           call;
        ProgressDialog progressDialog;


        public waitBackground (Context context , Call call) {
            // super();
            this.context = context;
            this.call    = call;

        }

        public waitBackground () {

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
        protected Response doInBackground (Void... voids) {
            try {

                response = call.execute ();

                //       System.err.println("Respose error-"+response.errorBody().source().readUtf8());

                return response;
            } catch ( Exception e ) {
                e.printStackTrace ();
            }
            return null;
        }

        @Override
        protected void onPostExecute (Response response) {
            super.onPostExecute (response);

            progressDialog.dismiss ();

        }


    }


}


