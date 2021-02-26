package real_deal.bsis.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import real_deal.bsis.R;
import real_deal.bsis.config.FormChoice;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.Location;
import real_deal.bsis.model.PreferredLanguage;
import real_deal.bsis.model.SyncRequest;

public class AddDonor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    FormChoice formChoice;

    EditText             firstName;
    EditText             middleName;
    EditText             lastName;
    EditText             bDay;
    EditText             bYear;
    Spinner              title;
    Spinner              months;
    EditText             calling;
    Spinner              gender;
    AutoCompleteTextView venie;
    Spinner              language;
    Button               addDonor;
    TextView             donorNumber;
    Location             venueLocation = null;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_donor);
        assert getSupportActionBar () != null;   //null check
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);   //show back button

        firstName   = findViewById (R.id.input_firstname);
        middleName  = findViewById (R.id.input_middlename);
        lastName    = findViewById (R.id.input_lastname);
        bDay        = findViewById (R.id.input_day);
        bYear       = findViewById (R.id.input_year);
        lastName    = findViewById (R.id.input_lastname);
        title       = findViewById (R.id.input_title);
        months      = findViewById (R.id.input_month);
        calling     = findViewById (R.id.input_calling);
        gender      = findViewById (R.id.input_gender);
        venie       = findViewById (R.id.vienutrial);
        language    = findViewById (R.id.input_language);
        addDonor    = findViewById (R.id.button_add_donor);
        donorNumber = findViewById (R.id.text_donor_number);


        formChoise ();

        addDonor.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {


                if (!validate ())
                    return;

                try {
                    PreferredLanguage prefLang =
                            formChoice.getlanguageObject (language.getSelectedItemPosition ());
                    Location vLocation = venueLocation;
                    SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
                    Date birthDay =
                            sdf.parse (bYear.getText ().toString () + "-" + months.getSelectedItemPosition () + "-" + bDay.getText ().toString ());
                    final String dob = sdf.format (birthDay);


                    Donor donor =
                            new Donor (null , title.getSelectedItem ().toString () , firstName.getText ().toString () ,
                                    middleName.getText ().toString () , lastName.getText ().toString () , calling.getText ().toString () ,
                                    gender.getSelectedItem ().toString ().toLowerCase () , dob , vLocation , prefLang , true);
                    donor.setCreatedFromMobile (true);

                    Donor.save (donor);

                    System.err.println (" idddsss==" + donor.getId ());
                    SyncRequest.save (new SyncRequest ("addDonor" , "POST" , donor.getId ().toString ()));

                    Intent intent = new Intent (AddDonor.this , Donor_Detail.class);
                    Bundle bundle = new Bundle ();
                    bundle.putParcelable ("donor" , donor);
                    intent.putExtras (bundle);
                    startActivity (intent);
                    finish ();

                } catch ( Exception e ) {

                    e.printStackTrace ();
                }


            }
        });


        final TabLayout tabLayout = findViewById (R.id.add_donor_tab);
        final ViewPager viewPager = findViewById (R.id.viewpager);

        title.setOnItemSelectedListener (this);
        gender.setOnItemSelectedListener (this);
        //    venie.setOnItemSelectedListener ( this );
        language.setOnItemSelectedListener (this);


        gender.setPrompt ("this is gender");

    }

    private boolean validate () {

        HardCodedValue hardCodedValue = new HardCodedValue ();
        boolean empty = false;

        TextView monthSelectedView = ( TextView ) months.getSelectedView ();
        TextView titleSelectedView = ( TextView ) title.getSelectedView ();
        TextView genderSelectedView = ( TextView ) gender.getSelectedView ();
        TextView languageSelectedView = ( TextView ) language.getSelectedView ();

        if (firstName.getText ().toString ().isEmpty () &&
                middleName.getText ().toString ().isEmpty () &&
                lastName.getText ().toString ().isEmpty () &&
                bDay.getText ().toString ().isEmpty () &&
                bYear.getText ().toString ().isEmpty () &&
                months.getSelectedItemPosition () == 0 &&
                gender.getSelectedItemPosition () == 0 &&
                language.getSelectedItemPosition () == 0) {
            firstName.setError (hardCodedValue.EMPTY_MESSAGE);
            lastName.setError (hardCodedValue.EMPTY_MESSAGE);
            middleName.setError (hardCodedValue.EMPTY_MESSAGE);
            bDay.setError (hardCodedValue.EMPTY_MESSAGE);
            bYear.setError (hardCodedValue.EMPTY_MESSAGE);
            genderSelectedView.setError (hardCodedValue.EMPTY_MESSAGE);
            languageSelectedView.setError (hardCodedValue.EMPTY_MESSAGE);
            titleSelectedView.setError (hardCodedValue.EMPTY_MESSAGE);
            return false;
        }
        if (firstName.getText ().toString ().isEmpty ()) {
            firstName.setError (hardCodedValue.EMPTY_MESSAGE);
            empty = true;
        }
        if (lastName.getText ().toString ().isEmpty ()) {
            lastName.setError (hardCodedValue.EMPTY_MESSAGE);
            empty = true;
        }
        if (middleName.getText ().toString ().isEmpty ()) {
            middleName.setError (hardCodedValue.EMPTY_MESSAGE);
            empty = true;
        }
        if (gender.getSelectedItemPosition () == 0) {
            genderSelectedView.setError (hardCodedValue.EMPTY_MESSAGE);
            empty = true;
        }
        if (title.getSelectedItemPosition () == 0) {
            titleSelectedView.setError (hardCodedValue.EMPTY_MESSAGE);
            empty = true;
        }

        if (language.getSelectedItemPosition () == 0) {
            languageSelectedView.setError (hardCodedValue.EMPTY_MESSAGE);
            empty = true;
        }
        if (months.getSelectedItemPosition () == 0) {
            monthSelectedView.setError (hardCodedValue.EMPTY_MESSAGE);
            empty = true;
        }
        if (bDay.getText ().toString ().isEmpty ()) {
            bDay.setError (hardCodedValue.EMPTY_MESSAGE);
            empty = true;
        }
        try {
            if (bYear.getText ().toString ().isEmpty ()) {
                bYear.setError (hardCodedValue.EMPTY_MESSAGE);
                empty = true;
            }
        } catch ( Exception e ) {
            bDay.setError (hardCodedValue.INVALID_INPUT);
            empty = true;
            e.printStackTrace ();
        }


        try {
            if (Integer.parseInt (bDay.getText ().toString ()) > 31) {
                bDay.setError (hardCodedValue.INVALID_INPUT);
                empty = true;
            }
        } catch ( Exception e ) {
            bDay.setError (hardCodedValue.INVALID_INPUT);
            empty = true;
            e.printStackTrace ();
        }

        venueLocation = formChoice.getVenueObject (venie.getText ().toString ());
        if (venueLocation == null) {
            venie.setError ("Unkown venue");
            empty = true;
        }


        return empty != true;
    }


    private void formChoise () {
        formChoice = new FormChoice ();

        ArrayAdapter < String > titleAddapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.titleArray);
        title.setAdapter (titleAddapter);

        ArrayAdapter < String > genderAddapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.genderArray);
        gender.setAdapter (genderAddapter);
        ArrayAdapter < String > monthAdapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.monthsArray);
        months.setAdapter (monthAdapter);

        ArrayAdapter < String > venueAddapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.venueChoice ());
        venie.setAdapter (venueAddapter);


        ArrayAdapter < String > languageAddapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.languageChoice ());
        language.setAdapter (languageAddapter);
    }


    @Override
    public void onItemSelected (AdapterView < ? > parent , View view , int position , long id) {

        TextView selectedText = ( TextView ) view;
        if (parent == title && position != 0)
            selectedText.setError (null);
        if (parent == gender && position != 0)
            selectedText.setError (null);
        if (parent == view && position != 0)
            selectedText.setError (null);
        if (parent == language && position != 0)
            selectedText.setError (null);
        if (parent == months && position != 0)
            selectedText.setError (null);

    }

    @Override
    public void onNothingSelected (AdapterView < ? > parent) {

    }

    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }
}
