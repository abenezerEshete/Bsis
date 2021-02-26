package real_deal.bsis;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.List;

import real_deal.bsis.config.commonImplimentationClass;
import real_deal.bsis.main_activity.FindDonorActivity;
import real_deal.bsis.main_activity.Login;
import real_deal.bsis.main_fragment.DonationButch;
import real_deal.bsis.main_fragment.Home;
import real_deal.bsis.main_fragment.Offline;
import real_deal.bsis.main_fragment.Setting;
import real_deal.bsis.model.Address;
import real_deal.bsis.model.AddressType;
import real_deal.bsis.model.AdverseEvent;
import real_deal.bsis.model.AdverseEventType;
import real_deal.bsis.model.Contact;
import real_deal.bsis.model.ContactMethodType;
import real_deal.bsis.model.DeferralReason;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.DonationBatch;
import real_deal.bsis.model.DonationType;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;
import real_deal.bsis.model.LastUpdateChecked;
import real_deal.bsis.model.Location;
import real_deal.bsis.model.LoginUser;
import real_deal.bsis.model.PackType;
import real_deal.bsis.model.PreferredLanguage;
import real_deal.bsis.model.ResourceLocation;
import real_deal.bsis.model.SyncRequest;

public class MainActivity extends commonImplimentationClass {


    Context        context;
    NavigationView navigationView;
    Toolbar        toolbar;
    long           back_pressed;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        context = this;


        setUpandcheckUserLogedin ();
        checkDatabase ();
//        SugarRecord.update (new ResourceLocation ("http://192.168.0.107" , "8080"));


        if (savedInstanceState == null) {
            getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new Home ()).commit ();
            navigationView.setCheckedItem (R.id.home_menu);
        }
        navigationView.setNavigationItemSelectedListener (this);

    }

    private void checkDatabase () {


        new Donor ();//.findAll(Donor.class);
        new Donation ();//.findAll(Donation.class);
        new DonorDeferral ();//.findAll(DonorDeferral.class);
        new Location ();//.findAll(Location.class);
        new DonationBatch ();//.findAll(DonationBatch.class);
        new PreferredLanguage ();//.findAll(PreferredLanguage.class);
        new SyncRequest ();//.findAll(SyncRequest.class);
        new Address ();//.findAll(Address.class);
        new AddressType ();//.findAll(Address.class);
        new Contact ();//.findAll(Contact.class);
        new ContactMethodType ();//.findAll(Contact.class);
        new PackType ();//.findAll(Contact.class);
        new AdverseEvent ();//;//.findAll(AdverseEvent.class);
        new DeferralReason ();//.findAll(DeferralReason.class);
        new AdverseEventType ();//.findAll(AdverseEventType.class);
        new DonationType ();//.findAll(DonationType.class);
        new LastUpdateChecked ();//.findAll(DonationType.class);
        new ResourceLocation ();
    }

    private void setUpandcheckUserLogedin () {
        DrawerLayout drawer = findViewById (R.id.drawer_layout);
        navigationView = findViewById (R.id.nav_view);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle (this , drawer , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.addDrawerListener (toggle);
        toggle.syncState ();

        //set user first name and second name in the navigation bar
        View headerView = navigationView.getHeaderView (0);
        TextView textView = headerView.findViewById (R.id.login_name);
        LoginUser user = Select.from (LoginUser.class).first ();
        if (user != null)
            textView.setText (user.getFirsname () + " " + user.getLastname ());
        else {
            Intent intent = new Intent (MainActivity.this , Login.class);
            startActivity (intent);
            finish ();
        }

    }

    @Override
    public void onBackPressed () {


        DrawerLayout drawer = findViewById (R.id.drawer_layout);
        if (drawer.isDrawerOpen (GravityCompat.START)) {
            drawer.closeDrawer (GravityCompat.START);
        } else {
            if (back_pressed + 1000 > System.currentTimeMillis ()) {
                super.onBackPressed ();
            } else {
                getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new Home ()).commit ();
                navigationView.setCheckedItem (R.id.home_menu);

                Toast.makeText (getBaseContext () ,
                        "Press once again to exit!" , Toast.LENGTH_SHORT)
                        .show ();
            }
            back_pressed = System.currentTimeMillis ();
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            LoginUser.deleteAll (LoginUser.class);
            Intent intent = new Intent (MainActivity.this , Login.class);
            startActivity (intent);
            finish ();
            return true;
        }

        return super.onOptionsItemSelected (item);
    }

    @SuppressWarnings ("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId ();


        if (id == R.id.home_menu)
            getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new Home ()).commit ();
        else if (id == R.id.manage_donor_menu) {
            Intent intent = new Intent (MainActivity.this , FindDonorActivity.class);
            startActivity (intent);
        }
        if (id == R.id.donation_batches_menu)
            getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new DonationButch ()).commit ();
        else if (id == R.id.offline)
            getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new Offline ()).commit ();
        else if (id == R.id.setting)
            getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new Setting ()).commit ();


        DrawerLayout drawer = findViewById (R.id.drawer_layout);
        drawer.closeDrawer (GravityCompat.START);
        return true;
    }


    @Override
    protected void onPostResume () {

        if (Select.from (LoginUser.class).count () != 0) {
//            getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_view , new Home () ).commit ();
//            navigationView.setCheckedItem ( R.id.home_menu );
        } else {
            startActivity (new Intent (MainActivity.this , Login.class));
            finish ();
        }

        super.onPostResume ();
    }

    @Override
    public void onFragmentInteraction (Uri uri) {

    }


    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }

}
