package real_deal.bsis.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Barcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        ZXingScannerView zXingScannerView = new ZXingScannerView (this);
        setContentView (zXingScannerView);
        zXingScannerView.setResultHandler (this);
        zXingScannerView.startCamera ();
    }

    @Override
    public void handleResult (Result result) {
        Toast.makeText (getApplicationContext () , result.getText () , Toast.LENGTH_LONG).show ();
        Intent returnIntent = new Intent ();
        returnIntent.putExtra ("result" , result.getText ());
        setResult (1 , returnIntent);
        finish ();

    }

    @Override
    public void onBackPressed () {
        super.onBackPressed ();
        finish ();
    }

    @Override
    protected void onPause () {
        super.onPause ();
        finish ();
    }
}
