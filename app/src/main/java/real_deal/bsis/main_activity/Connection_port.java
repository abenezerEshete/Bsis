package real_deal.bsis.main_activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import real_deal.bsis.R;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.config.NetworkStateCheck;
import real_deal.bsis.model.ResourceLocation;
import real_deal.bsis.offline.ApiInterface;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Connection_port extends AppCompatActivity {

    EditText _urlText;
    EditText _portText;
    Button   save;
    Button   test;
    TextView infoText;
    String URL;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_connection_port);
        _urlText  = findViewById (R.id.input_url);
        _portText = findViewById (R.id.input_port);
        save      = findViewById (R.id.save);
        test      = findViewById (R.id.test);
        infoText  = findViewById (R.id.info_text);

        save.setEnabled (false);


        test.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (validate ())
                    return;
                testConnection ();
            }
        });

        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                finish ();
            }
        });


    }

    private boolean validate () {

        boolean error = false;
        if (_urlText.getText ().toString ().isEmpty ()) {
            _urlText.setError (new HardCodedValue ().EMPTY_MESSAGE);
            error = true;
        }
//        if(_portText.getText ().toString ().isEmpty ()) {
//            _portText.setError ( new HardCodedValue ().EMPTY_MESSAGE );
//            error=true;
//        }
        if (!Patterns.WEB_URL.matcher (_urlText.getText ().toString ()).matches ()) {
            System.out.println ("---------");
            _urlText.setError ("Invalid URL");
            error = true;
        }

        return error;
    }

    private void testConnection () {
        if (!new NetworkStateCheck ().NetworkStateCheck (Connection_port.this))
            return;
        URL = _urlText.getText ().toString ();
        if (!_portText.getText ().toString ().isEmpty ())
            URL += ":" + _portText.getText ().toString () + "/";

//         if(!URL.contains ("http://"))
//             URL = "http://"+URL;


        try {

            System.out.println ("httpUrl=" + URL);
            OkHttpClient okHttpClient = new OkHttpClient.Builder ().
                    connectTimeout (20 , TimeUnit.SECONDS).readTimeout (600 , TimeUnit.SECONDS).build ();

            final Retrofit retrofit = new Retrofit.Builder ().client (okHttpClient).
                    baseUrl (URL).
                    addConverterFactory (ScalarsConverterFactory.create ()).
                    build ();
            final ApiInterface apiInterface = retrofit.create (ApiInterface.class);
            Call call = apiInterface.login ("Basic:ytr65ru65r6u5r65rutrutr654465 ");

            new waitBackground (call).execute ();

        } catch ( Exception e ) {

            _urlText.setError ("Invalid URL");
            if (!_portText.getText ().toString ().isEmpty ())
                _portText.setError ("");
            e.printStackTrace ();
        }

    }

    @Override
    public void onBackPressed () {
        // new waitBackground (  ).cancel ( true );
        super.onBackPressed ();
    }

    public class waitBackground extends AsyncTask < Void, Void, Response > {

        Call           call;
        Response       response;
        ProgressDialog progressDialog;

        public waitBackground (Call call) {
            // super();
            this.call = call;


        }

        public waitBackground () {

        }

        @Override
        protected void onPreExecute () {
            progressDialog =
                    new ProgressDialog (Connection_port.this , R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate (true);
            progressDialog.setMessage ("Checking...");
            progressDialog.show ();
            super.onPreExecute ();
        }

        @Override
        protected Response doInBackground (Void... voids) {
            try {
                return call.execute ();
            } catch ( Exception e ) {
                e.printStackTrace ();
            }
            return null;
        }

        @Override
        protected void onPostExecute (Response response) {
            super.onPostExecute (response);

            if (response == null) {
                infoText.setText ("Test failed");
                save.setEnabled (false);
            } else if (response.code () != 401) {
                infoText.setTextColor (getResources ().getColor (R.color.red , null));
                infoText.setText ("Test failed");
                save.setEnabled (false);
            } else {
                System.out.println (response.code ());
                ResourceLocation.deleteAll (ResourceLocation.class);
                ResourceLocation.save (new ResourceLocation (_urlText.getText ().toString () , _portText.getText ().toString ()));
                infoText.setTextColor (getResources ().getColor (R.color.design_default_color_primary , null));
                infoText.setText ("Testing successfull");
                save.setEnabled (true);
            }

            progressDialog.dismiss ();

        }
    }

}
