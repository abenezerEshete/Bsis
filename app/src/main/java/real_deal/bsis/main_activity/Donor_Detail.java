package real_deal.bsis.main_activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import real_deal.bsis.R;
import real_deal.bsis.add_donor_fragments.Find_donor;
import real_deal.bsis.config.FormChoice;
import real_deal.bsis.config.commonImplimentationClass;
import real_deal.bsis.donor_detail.Defferals;
import real_deal.bsis.donor_detail.Demographics;
import real_deal.bsis.donor_detail.DonationTab;
import real_deal.bsis.donor_detail.Overview;
import real_deal.bsis.model.Address;
import real_deal.bsis.model.Contact;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;
import real_deal.bsis.model.Location;
import real_deal.bsis.model.PreferredLanguage;
import real_deal.bsis.model.SyncRequest;

import static android.widget.Toast.LENGTH_SHORT;

public class Donor_Detail extends commonImplimentationClass implements Find_donor.OnFragmentInteractionListener {
    EditText     firstName;
    EditText     middleName;
    EditText     lastName;
    Spinner      title;
    EditText     calling;
    Spinner      gender;
    Spinner      venie;
    Spinner      language;
    Button       callenderButton;
    CalendarView birthDayCallender;
    TextView     donornNumber;
    FormChoice   formChoice = new FormChoice ();
    Donor        donor;
    Menu         menu;

    String birthDay = "";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.donor_detail_fragment);
        assert getSupportActionBar () != null;   //null check
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);   //show back button


        firstName         = findViewById (R.id.input_firstname);
        middleName        = findViewById (R.id.input_middlename);
        lastName          = findViewById (R.id.input_lastname);
        title             = findViewById (R.id.input_title);
        calling           = findViewById (R.id.input_calling);
        gender            = findViewById (R.id.input_gender);
        venie             = findViewById (R.id.input_venue);
        language          = findViewById (R.id.input_language);
        callenderButton   = findViewById (R.id.birth_calender_button);
        birthDayCallender = findViewById (R.id.birthday_calendar);


        donornNumber = findViewById (R.id.text_donor_number);

        System.out.println ("setupFiledvalue");
        setupFields ();
        fillValue ();
        System.out.println ("afterfillvalue");

        final TabLayout tabLayout = findViewById (R.id.add_donor_tab);
        final ViewPager viewPager = findViewById (R.id.viewpager);
        viewPager.setAdapter (new Addapter (getSupportFragmentManager () , tabLayout.getTabCount ()));
        viewPager.addOnPageChangeListener (new TabLayout.TabLayoutOnPageChangeListener (tabLayout));
        tabLayout.addOnTabSelectedListener (new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected (TabLayout.Tab tab) {
                viewPager.setCurrentItem (tab.getPosition ());
                Toast.makeText (getApplicationContext () , tab.getText () , LENGTH_SHORT).show ();
            }

            @Override
            public void onTabUnselected (TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected (TabLayout.Tab tab) {

            }
        });

        callenderButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (birthDayCallender.getVisibility () == View.GONE)
                    birthDayCallender.setVisibility (View.VISIBLE);
                else
                    birthDayCallender.setVisibility (View.GONE);

            }
        });

        birthDayCallender.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange (CalendarView view , int year , int month , int dayOfMonth) {

                month = month+1;

                birthDay = year + "-" + month + "-" + dayOfMonth;
                callenderButton.setText ("Date of Birth: " + birthDay);


            }
        });

    }

    private void setupFields () {

        try {
            // language choice list
            ArrayAdapter < String > languageAddapter =
                    new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.languageChoice ());
            language.setAdapter (languageAddapter);

            //venue choice list

            ArrayAdapter < String > venueAddapter =
                    new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.venueChoice ());
            venie.setAdapter (venueAddapter);


            //title choice list
            ArrayAdapter < String > titleAddapter =
                    new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.titleArray);
            title.setAdapter (titleAddapter);

            ArrayAdapter < String > genderAddapter =
                    new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.genderArray);
            gender.setAdapter (genderAddapter);
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        fieldVisibility (false);
        birthDayCallender.setVisibility (View.GONE);
    }

    public void fieldVisibility (boolean val) {
        firstName.setEnabled (val);
        middleName.setEnabled (val);
        lastName.setEnabled (val);
        calling.setEnabled (val);
        gender.setEnabled (val);
        venie.setEnabled (val);
        language.setEnabled (val);
        title.setEnabled (val);
        callenderButton.setEnabled (val);
    }

    private void fillValue () {

        try {

            System.out.println ("==== in fillValue");
            Bundle extra = getIntent ().getExtras ();
            Donor d = extra.getParcelable ("donor");
            donor = Donor.findById (Donor.class , d.getId ());

            firstName.setText (donor.getFirstName ());
            middleName.setText (donor.getMiddleName ());
            lastName.setText (donor.getLastName ());
            calling.setText (donor.getCallingName ());
            donornNumber.setText ("Donor Number: " + donor.getDonorNumber ());

            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
            callenderButton.setText ("Date of Birth: " + donor.getBirthDate () + "  ");
            birthDayCallender.setDate (sdf.parse (donor.getBirthDate ()).getTime ());
            birthDay = donor.getBirthDate ();


            if (donor.getTitle () != null && !donor.getTitle ().isEmpty ())
                title.setSelection (formChoice.titlecheck (donor.getTitle ()));
            if (donor.getPreferredLanguage () != null)
                language.setSelection (formChoice.languageCheck (donor.getPreferredLanguage ().getPreferredLanguage ()));
            gender.setSelection (formChoice.genderCheck (donor.getGender ()));
            if (donor.getVenue () != null)
                venie.setSelection (formChoice.venueCheck (donor.getVenue ().getName ()));
            System.out.println ("==== in fillValue end");


        } catch ( Exception e ) {
            e.printStackTrace ();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        //  this.menu.getItem(3).setVisible(false);
        //this.menu.getItem(2).setVisible(false);


        getMenuInflater ().inflate (R.menu.donor_detail , menu);
        System.out.println ("menu 1 ==" + menu.getItem (0).getTitle ());
        System.out.println ("menu 2 ==" + menu.getItem (1).getTitle ());
        System.out.println ("menu 3 ==" + menu.getItem (2).getTitle ());
        System.out.println ("menu 4 ==" + menu.getItem (3).getTitle ());
        MenuItem voidItem = menu.getItem (0);
        if (!donor.isCreatedFromMobile ())
        {
            voidItem.setVisible (false);
            menu.getItem (1).setVisible (false);
            menu.getItem (2).setVisible (false);
            menu.getItem (3).setVisible (false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId ();


        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_edit) {
            menu.findItem (R.id.menu_void_donor).setVisible (false);
            menu.findItem (R.id.menu_save).setVisible (true);
            menu.findItem (R.id.menu_cancel).setVisible (true);
            menu.findItem (R.id.menu_edit).setVisible (false);

            fieldVisibility (true);

        }
        if (id == R.id.menu_void_donor) {

            try {
                List < SyncRequest > syncRequestDonor =
                        SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " , donor.getId ().toString () , "addDonor");
                SyncRequest.deleteInTx (syncRequestDonor);
                syncRequestDonor =
                        SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " , donor.getId ().toString () , "updateDonor");
                SyncRequest.deleteInTx (syncRequestDonor);

                List < Donation > donationList =
                        Donation.find (Donation.class , "donor = ? " , donor.getId ().toString ());

                List < SyncRequest > syncRequestDonation = new ArrayList <> ();
                for (int i = 0; i < donationList.size (); i++) {
                    List < SyncRequest > sr1 =
                            SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                    donationList.get (i).getId ().toString () , "addDonation");
                    if (!sr1.isEmpty ())
                        syncRequestDonation.add (sr1.get (i));

                    List < SyncRequest > sr2 =
                            SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                    donationList.get (i).getId ().toString () , "updateDonation");
                    if (!sr2.isEmpty ())
                        syncRequestDonation.add (sr2.get (i));


                }

                Donation.deleteInTx (donationList);

                List < DonorDeferral > donorDeferralList =
                        DonorDeferral.find (DonorDeferral.class , "deferred_donor = ? " , donor.getId ().toString ());
                for (int i = 0; i < donorDeferralList.size (); i++) {
                    List < SyncRequest > sr1 =
                            SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                    donorDeferralList.get (i).getId ().toString () , "addDeferral");
                    List < SyncRequest > sr2 =
                            SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                    donorDeferralList.get (i).getId ().toString () , "updateDeferral");

                    if (!sr1.isEmpty ())
                        syncRequestDonation.add (sr1.get (0));

                    if (!sr2.isEmpty ())
                        syncRequestDonation.add (sr2.get (0));

                }

                SyncRequest.deleteInTx (syncRequestDonation);
                DonorDeferral.deleteInTx (donorDeferralList);

                if (donor.getAddress () != null) {
                    Address address =
                            Address.findById (Address.class , donor.getAddress ().getId ());
                    if (address != null)
                        Address.delete (address);

                }
                if (donor.getContact () != null) {
                    Contact contact =
                            Contact.findById (Contact.class , donor.getContact ().getId ());
                    if (contact != null)
                        Contact.delete (contact);

                }
                Donor.delete (donor);

                finish ();

            } catch ( Exception e ) {
                e.printStackTrace ();
            }
//            menu.findItem ( R.id.menu_void_donor ).setVisible ( true );
//            menu.findItem ( R.id.menu_save ).setVisible ( true );
//            menu.findItem ( R.id.menu_cancel ).setVisible ( true );
//            menu.findItem ( R.id.menu_edit ).setVisible ( true );
        }
        if (id == R.id.menu_save) {
            menu.findItem (R.id.menu_void_donor).setVisible (true);
            menu.findItem (R.id.menu_save).setVisible (false);
            menu.findItem (R.id.menu_cancel).setVisible (false);
            menu.findItem (R.id.menu_edit).setVisible (true);
            SaveEdit ();
            fieldVisibility (false);

        }
        if (id == R.id.menu_cancel) {
            menu.findItem (R.id.menu_void_donor).setVisible (true);
            menu.findItem (R.id.menu_save).setVisible (false);
            menu.findItem (R.id.menu_cancel).setVisible (false);
            menu.findItem (R.id.menu_edit).setVisible (true);
            fieldVisibility (false);
        }

        return super.onOptionsItemSelected (item);
    }

    private void SaveEdit () {


        try {
            String _firstNameText = firstName.getText ().toString ();
            String _middleNameText = middleName.getText ().toString ();
            String _lastNameText = lastName.getText ().toString ();
            String _callingNameText = calling.getText ().toString ();
            String _titleText = title.getSelectedItem ().toString ();
            String _genderText = gender.getSelectedItem ().toString ().toLowerCase ();
            PreferredLanguage _preferredLanguage =
                    formChoice.getlanguageObject (language.getSelectedItemPosition ());
            Location _venue = formChoice.getVenueObject (venie.getSelectedItemPosition ());

            if(_preferredLanguage != null)


            if (_firstNameText.equalsIgnoreCase (donor.getFirstName ()) &&
                    _middleNameText.equalsIgnoreCase (donor.getMiddleName ()) &&
                    _lastNameText.equalsIgnoreCase (donor.getLastName ()) &&
                    _callingNameText.equalsIgnoreCase (donor.getCallingName ()) &&
                    _genderText.equalsIgnoreCase (donor.getGender ()) &&
//                    _preferredLanguage.getServerId ().equals (donor.getPreferredLanguage ().getServerId ()) &&
                    _venue.getServerId ().equals (donor.getVenue ().getServerId ()) &&
                    birthDay.equals (donor.getBirthDate ())) {
                return;
            }
            donor.setFirstName (_firstNameText);
            donor.setMiddleName (_middleNameText);
            donor.setLastName (_lastNameText);
            donor.setCallingName (_callingNameText);
            donor.setTitle (_titleText);
            donor.setGender (_genderText);
            donor.setBirthDate (birthDay);
            donor.setPreferredLanguage (_preferredLanguage);
            donor.setVenue (_venue);
            Donor.save (donor);

            SyncRequest.save (new SyncRequest ("updateDonor" , "PUT" , donor.getId ().toString ()));

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

    }

    @Override
    public void onFragmentInteraction (Uri uri) {

    }


    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }

    static class Addapter extends FragmentPagerAdapter {


        int tabCount;

        public Addapter (FragmentManager fm , int tabCount) {
            super (fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem (int i) {
            Fragment fragment = null;

//            if (i == 0) {
//                fragment = new Find_donor();
//            }
            if (i == 0) {
                fragment = new Overview ();
            }
            if (i == 1) {
                fragment = new Demographics ();
            }
            if (i == 2) {
                fragment = new DonationTab ();
            }
            if (i == 3) {
                fragment = new Defferals ();
            }
            return fragment;
        }

        @Override
        public int getCount () {
            return tabCount;
        }
    }

}
