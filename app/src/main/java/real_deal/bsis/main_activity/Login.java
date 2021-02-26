package real_deal.bsis.main_activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import real_deal.bsis.MainActivity;
import real_deal.bsis.R;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.model.LoginUser;
import real_deal.bsis.model.ResourceLocation;
import real_deal.bsis.offline.ApiInterface;
import real_deal.bsis.offline.RestPort;
import retrofit2.Call;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private static final String TAG            = "LoginActivity";
    private static final int    REQUEST_SIGNUP = 0;

    @BindView (R.id.input_phone)
    EditText _userNameText;
    @BindView (R.id.input_password)
    EditText _passwordText;
    @BindView (R.id.btn_login)
    Button   _loginButton;

    TextView errorText;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        ButterKnife.bind (this);

        //  saveDemoData();
        errorText = findViewById (R.id.error_text);
        _loginButton.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick (View v) {
                login ();
            }
        });


    }

    public void login () {
        if (!validate ())
            return;

        //admin loginchech//  BSIS@Mobile_Admin
        if (_userNameText.getText ().toString ().equals ("BSIS@Mobile_Admin") && //BSIS@ADMIN //EBB@MobileAdmin
                _passwordText.getText ().toString ().equals ("BSIS_EBB@MobileConfig.Pass"))//EBB@MobileConfig.Pass
        {
            Intent intent = new Intent (Login.this , Connection_port.class);
            startActivity (intent);
        } else if (Select.from (ResourceLocation.class).count () == 0) {
            errorText.setText ("URL and Port number is empty, Please contact system administrator");
            errorText.setVisibility (View.VISIBLE);
            return;
        } else {
            //    _loginButton.setEnabled(false);
            String username = _userNameText.getText ().toString ();
            String password = _passwordText.getText ().toString ();

            RestPort restPort = new RestPort (Login.this);
            if (!restPort.networkCheck ()) {
                _loginButton.setEnabled (true);
                return;
            }


            final ApiInterface apiInterface = restPort.retrofitsetup ();
            String uNamePword = username + ":" + password;
            final String encodedText =
                    "Basic " + Base64.encodeToString (uNamePword.getBytes () , Base64.NO_WRAP);


            Call call = apiInterface.login (encodedText);
            new waitBackground (call).execute ();
        }

    }


    public boolean validate () {
        boolean valid = true;

        String username = _userNameText.getText ().toString ();
        String password = _passwordText.getText ().toString ();

        if (username.isEmpty ())// || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        {
            _userNameText.setError ("Enter UserName");
            valid = false;
        } else {
            _userNameText.setError (null);
        }

        if (password.isEmpty ())//|| password.length() < 4 || password.length() > 10) {
        {
            _passwordText.setError ("Enter Password");
            valid = false;
        } else {
            _passwordText.setError (null);
        }

        return valid;
    }

    public void onLoginSuccess (Response response) {

        try {

            if (response.isSuccessful ()) {
                JSONObject jsonObject = new JSONObject (response.body ().toString ());
                JSONArray roles =
                        jsonObject.getJSONArray ("roles");//.get(0).getJSONArray("permissions");
                int plength = 0;
                for (int j = 0; j < roles.length (); j++) {
                    JSONArray permissions =
                            roles.getJSONObject (j).getJSONArray ("permissions");
                    for (int i = 0; i < permissions.length (); i++) {
                        int id = permissions.getJSONObject (i).getInt ("id");
                        if (id == 1 || id == 2 || id == 3 || id == 5 || id == 6 || id == 7 || id == 9 || id == 10 ||
                                id == 13 || id == 14 || id == 15 || id == 16 || id == 18 || id == 19 ||
                                id == 35 || id == 110 || id == 59 || id == 60 || id == 61 || id == 69 || id == 70 || id == 71 ||
                                id == 83) {
                            ++plength;
                        }
                    }
                }
                if (plength < 23) {
                    this.onLoginFailed (new HardCodedValue ().AUTHORIZED_FAIL);
                    return;
                }


                System.out.println ("username=" + _userNameText.getText ().toString ());
                System.out.println ("password=" + _passwordText.getText ().toString ());
                LoginUser.deleteAll (LoginUser.class);
                LoginUser.save (new LoginUser (jsonObject.getString ("id") , _userNameText.getText ().toString () ,
                        _passwordText.getText ().toString () , jsonObject.getString ("firstName") ,
                        jsonObject.getString ("lastName") , jsonObject.getString ("emailId") ,
                        jsonObject.get ("roles").toString ()));


                Intent intent = new Intent (Login.this , MainActivity.class);
                startActivity (intent);
                finish ();

            } else if (response.code () == 401) {
                _userNameText.setError ("");
                _passwordText.setError ("");
                onLoginFailed (new HardCodedValue ().LOGIN_INVALID);
                return;
            } else {
                onLoginFailed (new HardCodedValue ().NETWORK_PROBLEM);
            }

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
    }

    public void onLoginFailed (final String massage) {

        Handler handler = new Handler (Looper.getMainLooper ()) {
            @Override
            public void handleMessage (Message msg) {
                errorText.setText (massage);
                errorText.setVisibility (View.VISIBLE);
                _loginButton.setEnabled (true);
            }
        };
        handler.sendEmptyMessage (1);

    }

    public class waitBackground extends AsyncTask < Void, Void, String > {

        Call           call;
        Response       response;
        ProgressDialog progressDialog;

        public waitBackground (Call call) {
            // super();
            this.call = call;

        }

        @Override
        protected void onPreExecute () {
            progressDialog = new ProgressDialog (Login.this , R.style.AppTheme_Dark_Dialog);
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

                System.out.println ("-1-----" + response.errorBody ().string ());
                System.out.println ("--2----" + response.errorBody ().contentType ().type ());
                System.out.println ("---3---" + response.errorBody ().source ().readUtf8 ());
                System.out.println ("----4--" + response.message ());
                System.out.println ("----5--" + response.raw ().body ());
                System.out.println ("----6--" + response.isSuccessful ());
            } catch ( Exception e ) {
                e.printStackTrace ();
                onLoginFailed (e.getMessage ());
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute (String s) {
            super.onPostExecute (s);

            if (response != null)
                onLoginSuccess (response);
            else {
                //      onLoginFailed ( new HardCodedValue ().NETWORK_PROBLEM );
            }

            progressDialog.dismiss ();

        }
    }


}


