package real_deal.bsis.main_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import real_deal.bsis.R;
import real_deal.bsis.config.DateformatControl;
import real_deal.bsis.config.FormChoice;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.model.DeferralReason;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;
import real_deal.bsis.model.Location;
import real_deal.bsis.model.SyncRequest;

public class AddDonorDeferal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner              reason;
    AutoCompleteTextView venue;
    EditText             comment;
    TextView             reasonText;
    TextView             viewText;
    Button               endDateButton;
    Button               save;
    Button               cancel;
    CalendarView         endDateCalender;


    Donor         donor;
    DonorDeferral donorDeferral;
    FormChoice    formChoice;
    Location      venueLocation = null;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_donor_deferal);
        assert getSupportActionBar () != null;   //null check
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);   //show back button

        reason          = findViewById (R.id.input_reason);
        venue           = findViewById (R.id.input_venue);
        endDateButton   = findViewById (R.id.end_date_button);
        save            = findViewById (R.id.save);
        cancel          = findViewById (R.id.cancel);
        comment         = findViewById (R.id.input_comment);
        reasonText      = findViewById (R.id.reason_text);
        viewText        = findViewById (R.id.venue_text);
        endDateCalender = findViewById (R.id.end_date_calendar);

        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy/MM/dd");
        endDateButton.setText (sdf.format (new Date ()));
        endDateCalender.setVisibility (View.GONE);

        Bundle bundle = getIntent ().getExtras ();
        setupFields ();
        if (bundle.getLong ("deferral") != 0) {
            Long donorDeferralId = bundle.getLong ("deferral");
            donorDeferral = DonorDeferral.findById (DonorDeferral.class , donorDeferralId);
            donor         = donorDeferral.getDeferredDonor ();

            if(!donorDeferral.isCreatedFromMobile ()) {
                reason.setEnabled (false);
                venue.setEnabled (false);
                comment.setEnabled (false);
                endDateButton.setEnabled (false);
                save.setEnabled (false);

            }
            formValue ();

        }
        if (bundle.getParcelable ("donor") != null)
            donor = bundle.getParcelable ("donor");

        endDateButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (endDateCalender.getVisibility () == View.VISIBLE)
                    endDateCalender.setVisibility (View.GONE);
                else
                    endDateCalender.setVisibility (View.VISIBLE);
            }
        });


        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {


                //save.setEnabled ( false );
                if (!validate ()) {
                    // save.setEnabled ( true );
                    return;
                }

                saveDeferral ();


            }
        });
        cancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });

        endDateCalender.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange (CalendarView view , int year , int month , int dayOfMonth) {

                endDateButton.setText (" " + year + "/" + (month + 1) + "/" + dayOfMonth + " ");

                System.out.println ("---------" + endDateCalender.getDate () + new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS").format (endDateCalender.getDate ()));
                System.out.println ("---------" + " " + year + "/" + month + "/" + dayOfMonth + " ");
            }
        });

        reason.setOnItemSelectedListener (this);

    }

    private void saveDeferral () {

        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat sdf2 = new SimpleDateFormat ("yyyy/MM/dd");
            String deferredUntil =
                    sdf1.format (sdf2.parse (endDateButton.getText ().toString ().trim ()));
            String date = sdf1.format (new Date ());

            JSONObject dReason = new JSONObject ();
            dReason.put ("id" , formChoice.getDeferralReasoneObject (reason.getSelectedItemPosition ()).getId ());

            JSONObject donorJsonObject = new JSONObject ();
            donorJsonObject.put ("id" , donor.getId ());

//            JSONObject venueObject = new JSONObject ();
//            venueObject.put ("id" , formChoice.getVenueObject (venue.getSelectedItemPosition ()).getId ());


            DeferralReason dreason =
                    formChoice.getDeferralReasoneObject (reason.getSelectedItemPosition ());
            if (dreason.getDurationType ().equals ("PERMANENT"))
                deferredUntil = "2099-12-31T21:00:00.000Z";

            System.err.println ("--------" + dreason.getDurationType () + "---" + deferredUntil);
            DonorDeferral dDeferral = new DonorDeferral (date , dreason ,
                    donor , deferredUntil , venueLocation,
                    comment.getText ().toString () , true);
            if (donorDeferral == null) {
                dDeferral.save ();
                SyncRequest.save (new SyncRequest ("addDeferral" , "POST" , dDeferral.getId ().toString ()));
            } else {
                donorDeferral =
                        DonorDeferral.findById (DonorDeferral.class , donorDeferral.getId ());
                donorDeferral.setDeferralReason (dreason);
                donorDeferral.setDeferredUntil (deferredUntil);
                donorDeferral.setDeferralReasonText (comment.getText ().toString ());
                donorDeferral.setVenue (venueLocation);
                DonorDeferral.save (donorDeferral);
                SyncRequest.save (new SyncRequest ("updateDeferral" , "PUT" , donorDeferral.getId ().toString ()));
                System.out.println ("deferral d =" + dDeferral.getId ());
            }

            finish ();

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

    }

    private boolean validate () {


        AppCompatTextView reasonError = ( AppCompatTextView ) reason.getSelectedView ();

        boolean valid = true;
        if (venue.getText ().toString ().isEmpty ()) {
            valid = false;
            venue.setError (new HardCodedValue ().EMPTY_MESSAGE);
        }
        else {
            venueLocation = formChoice.getVenueObject (venue.getText ().toString ());

            if (venueLocation == null) {
                venue.setError ("Unkown venue");
                valid = false;
            }
        }


        if (reason.getSelectedItemPosition () == 0) {
            reasonError.setError (new HardCodedValue ().EMPTY_MESSAGE);
            valid = false;
        }


        try {
            SimpleDateFormat shortDateFormat = new SimpleDateFormat ("yyyy/MM/dd");
            Date enddate = null;
            enddate = shortDateFormat.parse (endDateButton.getText ().toString ());
            System.out.println (enddate);
            if (enddate.before (new Date ())) {
                endDateButton.setTextColor (getResources ().getColor (R.color.red));
                valid = false;
            } else {
                endDateButton.setTextColor (getResources ().getColor (R.color.black));
            }
        } catch ( ParseException e ) {
            e.printStackTrace ();
        }


        return valid;
    }

    private void setupFields () {

        formChoice = new FormChoice ();
        // reason choice list
        ArrayAdapter < String > reasonAdapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.deferralReasonChoice ());
        reason.setAdapter (reasonAdapter);

        //venue choice list
        ArrayAdapter < String > venueAddapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.venueChoice ());
        venue.setAdapter (venueAddapter);
    }


    private void formValue () {

//        System.out.println("donorDeferral.getVenue().getName() ======="+donorDeferral.getVenue().getName());
        Location ve = donorDeferral.getVenue ();
        reason.setSelection (formChoice.deferralReasoncheck (donorDeferral.getDeferralReason ().getReason ()));
        venue.setText (ve.getName ());
        comment.setText (donorDeferral.getDeferralReasonText ());

        DateformatControl dateformatControl = new DateformatControl ();
        Long untilDate =
                dateformatControl.convertStringToDate (donorDeferral.getDeferredUntil ()).getTime ();
        System.out.println ("--+--" + dateformatControl.convertStringToDate (donorDeferral.getDeferredUntil ()));
        System.out.println ("--=--" + donorDeferral.getDeferredUntil ());
        endDateCalender.setDate (dateformatControl.convertStringToDate (donorDeferral.getDeferredUntil ()).getTime ());

        String year = new SimpleDateFormat ("yyyy").format (untilDate);
        String month = new SimpleDateFormat ("MM").format (untilDate);
        String day = new SimpleDateFormat ("dd").format (untilDate);
        endDateButton.setText (" " + year + "/" + month + "/" + day + " ");


    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {


        getMenuInflater ().inflate (R.menu.void_menu , menu);

        if (donorDeferral == null)
            return false;

        MenuItem voidItem = menu.getItem (0);
        if (donorDeferral.isCreatedFromMobile ())
            voidItem.setVisible (true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId ();


        //noinspection SimplifiableIfStatement
        if (id == R.id.void_record) {

            System.out.println (donorDeferral.getId () + "-------ddddd");
            try {
                List < SyncRequest > syncRequestDonor =
                        SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                donorDeferral.getId ().toString () , "addDeferral");
                SyncRequest.deleteInTx (syncRequestDonor);
                syncRequestDonor =
                        SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                donorDeferral.getId ().toString () , "updateDeferral");
                SyncRequest.deleteInTx (syncRequestDonor);

                DonorDeferral.delete (donorDeferral);

                finish ();

            } catch ( Exception e ) {
                e.printStackTrace ();
            }
//            menu.findItem ( R.id.menu_void_donor ).setVisible ( true );
//            menu.findItem ( R.id.menu_save ).setVisible ( true );
//            menu.findItem ( R.id.menu_cancel ).setVisible ( true );
//            menu.findItem ( R.id.menu_edit ).setVisible ( true );
        }

        return super.onOptionsItemSelected (item);
    }


    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }

    @Override
    public void onItemSelected (AdapterView < ? > parent , View view , int position , long id) {

        AppCompatTextView selectedText = ( AppCompatTextView ) view;

        try {
             if (parent == reason && position != 0)
                selectedText.setError (null);
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
    }

    @Override
    public void onNothingSelected (AdapterView < ? > parent) {

    }
}
