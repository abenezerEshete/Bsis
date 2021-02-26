package real_deal.bsis.main_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import real_deal.bsis.R;

public class DonationDetail extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_donation__detail);
        assert getSupportActionBar () != null;   //null check
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);   //show back button

    }

    @Override
    public void onBackPressed () {
        finish ();
        super.onBackPressed ();
    }

    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }
}
