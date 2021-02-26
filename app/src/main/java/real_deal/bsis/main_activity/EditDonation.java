package real_deal.bsis.main_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import real_deal.bsis.R;
import real_deal.bsis.config.DateformatControl;
import real_deal.bsis.config.FormChoice;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.model.AdverseEvent;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.PackType;
import real_deal.bsis.model.SyncRequest;

public class EditDonation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    TextView batch;
    TextView dinText;
    TextView donorNumberText;
    Spinner  pack;
    TextView donationText;
    EditText startHour;
    EditText startMin;
    Spinner  startAm;
    EditText endHour;
    EditText endMin;
    Spinner  endAm;
    EditText weight;
    Spinner  hb;
    EditText bpMin;
    EditText bpMax;
    EditText pulse;
    EditText description;
    Spinner  adverseEvent;
    Button   save;
    Button   cancel;
    Button   edit;
    Button   done;


    FormChoice formChoice;
    Donation   donation;
    Donor      donor;
    String     nowTime;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_donation);
        assert getSupportActionBar () != null;   //null check
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);   //show back button

        batch           = findViewById (R.id.batch);
        dinText         = findViewById (R.id.din_text);
        donorNumberText = findViewById (R.id.donor_number_text);
        pack            = findViewById (R.id.pack);
        donationText    = findViewById (R.id.donation);
        startHour       = findViewById (R.id.start_hour);
        startMin        = findViewById (R.id.start_min);
        startAm         = findViewById (R.id.start_am);
        endHour         = findViewById (R.id.end_hour);
        endMin          = findViewById (R.id.end_min);
        endAm           = findViewById (R.id.end_am);
        weight          = findViewById (R.id.weight);
        hb              = findViewById (R.id.hb);
        bpMin           = findViewById (R.id.bp_min);
        bpMax           = findViewById (R.id.bp_max);
        pulse           = findViewById (R.id.pulse);
        description     = findViewById (R.id.description);
        adverseEvent    = findViewById (R.id.adverse_effect);
        save            = findViewById (R.id.save);
        cancel          = findViewById (R.id.cancel);
        edit            = findViewById (R.id.edit);
        done            = findViewById (R.id.done);

        Bundle extra = getIntent ().getExtras ();
        donation = extra.getParcelable ("donation");
        donor    = donation.getDonor ();


        fieldVisiblity (false);
        formChoise ();
        setupForm ();


        save.setVisibility (View.GONE);
        cancel.setVisibility (View.GONE);

        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (validation ())
                    return;

                System.out.println ("herre-------------------------");
                updateDonation ();

                //finish();
            }
        });
        edit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                fieldVisiblity (true);
                edit.setVisibility (View.GONE);
                done.setVisibility (View.GONE);
                cancel.setVisibility (View.VISIBLE);
                save.setVisibility (View.VISIBLE);

            }
        });

        done.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });

        cancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                fieldVisiblity (false);
                edit.setVisibility (View.VISIBLE);
                done.setVisibility (View.VISIBLE);
                cancel.setVisibility (View.GONE);
                save.setVisibility (View.GONE);
            }
        });


        pack.setOnItemSelectedListener (this);
        adverseEvent.setOnItemSelectedListener (this);

        System.out.println ("editable: "+donation.isCreatedFromMobile ());

        if(!donation.isCreatedFromMobile ())
            edit.setEnabled (false);
         }

    private void updateDonation () {
        try {

            //Formating starting and ending  time format

            int inputStartHour = Integer.parseInt (startHour.getText ().toString ());
            int inputStartMin = Integer.parseInt (startMin.getText ().toString ());
            String inputStartZone = startAm.getSelectedItem ().toString ();
            int inputEndHour = Integer.parseInt (endHour.getText ().toString ());
            int inputEndMin = Integer.parseInt (endMin.getText ().toString ());
            String inputEndZone = endAm.getSelectedItem ().toString ();

            if (inputStartZone == "PM")
                inputStartHour = inputStartHour + 12;
            if (inputEndZone == "PM")
                inputEndHour = inputEndHour + 12;


            SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
            String year = df.format (new Date ());
            df = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
            Date startDate = df.parse (year + " " + inputStartHour + ":" + inputStartMin);
            Date endDate = df.parse (year + " " + inputEndHour + ":" + inputEndMin);


            if (startDate.after (endDate) || startDate.equals (endDate)) {
                System.out.println ("ampm start time==" + df.format (startDate));
                System.out.println ("ampm end time==" + df.format (endDate));
                startHour.setError ("Start time should be before End time");
                endHour.setError ("Start time should be before End time");
                startMin.setError ("Start time should be before End time");
                endMin.setError ("Start time should be before End time");
                return;
            }
            Calendar calendar = Calendar.getInstance ();
            calendar.setTime(startDate);
            calendar.add(Calendar.HOUR_OF_DAY, -3);
            startDate = calendar.getTime ();

            calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.HOUR_OF_DAY, -3);
            endDate = calendar.getTime ();



            DateformatControl dateformatControl = new DateformatControl ();
            String starttime = dateformatControl.convertToLongDate (startDate) + "Z";
            String endtime = dateformatControl.convertToLongDate (endDate) + "Z";


            System.out.println ("starttime------------" + starttime);
            System.out.println ("endTime------------" + endtime);

            PackType packTypeText =
                    formChoice.getPackTypeObject (pack.getSelectedItemPosition ());

            String systolid =
                    bpMax.getText ().toString ().isEmpty () ? null : bpMax.getText ().toString ();
            String diastolic =
                    bpMin.getText ().toString ().isEmpty () ? null : bpMin.getText ().toString ();
            String dWeight =
                    weight.getText ().toString ().isEmpty () ? null : weight.getText ().toString ();
            String heamB =
                    hb.getSelectedItem ().toString ().equalsIgnoreCase ("Label") ? null : hb.getSelectedItem ().toString ();
            String pulseText =
                    pulse.getText ().toString ().isEmpty () ? null : pulse.getText ().toString ();

            AdverseEvent adverse = null;
            if (adverseEvent.getSelectedItemPosition () != 0) {
                adverse =
                        new AdverseEvent (formChoice.getAdverseEventObject (adverseEvent.getSelectedItemPosition ()) , description.getText ().toString ());
                adverse.save ();
            }


            donation = Donation.findById (Donation.class , donation.getId ());
            donation.setDonationDate (donation.getDonationDate ());
            donation.setPackType (packTypeText);
            donation.setBleedStartTime (starttime);
            donation.setBleedEndTime (endtime);
            donation.setDonorWeight (dWeight);
            donation.setHaemoglobinCount (heamB);
            donation.setBloodPressureSystolic (systolid);
            donation.setBloodPressureDiastolic (diastolic);
            donation.setDonorPulse (pulseText);
            donation.setAdverseEvent (adverse);

            Donation.save (donation);


            System.out.println ("Start time=" + starttime);
            System.out.println ("end time=" + endtime);
            SyncRequest.save (new SyncRequest ("updateDonation" , "PUT" , donation.getId ().toString ()));
            Toast.makeText (getBaseContext () , "Donation Successfull" , Toast.LENGTH_LONG).show ();

            finish ();

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

    }

    private void fieldVisiblity (boolean b) {

        batch.setEnabled (false);
        pack.setEnabled (b);
        donationText.setEnabled (false);
        startHour.setEnabled (b);
        startMin.setEnabled (b);
        startAm.setEnabled (b);
        endHour.setEnabled (b);
        endMin.setEnabled (b);
        endAm.setEnabled (b);
        weight.setEnabled (b);
        bpMax.setEnabled (b);
        bpMin.setEnabled (b);
        hb.setEnabled (b);
        pulse.setEnabled (b);
        adverseEvent.setEnabled (b);
        description.setEnabled (b);

    }

    private boolean validation () {

        AppCompatTextView packError = ( AppCompatTextView ) pack.getSelectedView ();
        boolean error = false;


        if (pack.getSelectedItemPosition () == 0) {
            packError.setError (new HardCodedValue ().EMPTY_MESSAGE);
            error = true;
        }

        if (pack.getSelectedItemPosition () == 0) {
            packError.setError (new HardCodedValue ().EMPTY_MESSAGE);
            error = true;
        }

        if (!weight.getText ().toString ().isEmpty () && Integer.parseInt (weight.getText ().toString ()) > 300) {
            weight.setError ("Please enter a value below 300");
            error = true;
        }
        if (!weight.getText ().toString ().isEmpty () && Integer.parseInt (weight.getText ().toString ()) < 30) {
            weight.setError ("Please enter a value above 30");
            error = true;
        }
        if (!bpMax.getText ().toString ().isEmpty () && Integer.parseInt (bpMax.getText ().toString ()) < 70) {
            bpMax.setError ("Please enter a value above 70");
            error = true;
        }
        if (!bpMin.getText ().toString ().isEmpty () && Integer.parseInt (bpMin.getText ().toString ()) > 100) {
            bpMin.setError ("Please enter a value below 100");
            error = true;
        }
        if (!bpMin.getText ().toString ().isEmpty () && Integer.parseInt (bpMin.getText ().toString ()) < 70) {
            bpMin.setError ("Please enter a value above 70");
            error = true;
        }
        if (!pulse.getText ().toString ().isEmpty () && Integer.parseInt (pulse.getText ().toString ()) > 200) {
            pulse.setError ("Please enter a value below 200");
            error = true;
        }
        if (!pulse.getText ().toString ().isEmpty () && Integer.parseInt (pulse.getText ().toString ()) < 30) {
            pulse.setError ("Please enter a value above 30");
            error = true;
        }
        if (!startHour.getText ().toString ().isEmpty () && Integer.parseInt (startHour.getText ().toString ()) > 12) {
            startHour.setError ("Enter a valid time\n");
            error = true;
        }
        if (!endHour.getText ().toString ().isEmpty () && Integer.parseInt (endHour.getText ().toString ()) > 12) {
            endHour.setError ("Enter a valid time\n");
            error = true;
        }
        if (!startMin.getText ().toString ().isEmpty () && Integer.parseInt (startMin.getText ().toString ()) > 60) {
            startMin.setError ("Enter a valid time\n");
            error = true;
        }
        if (!endMin.getText ().toString ().isEmpty () && Integer.parseInt (endMin.getText ().toString ()) > 60) {
            endMin.setError ("Enter a valid time\n");
            error = true;
        }


        return error;
    }

    private void formChoise () {
        formChoice = new FormChoice ();


        ArrayAdapter < String > hbChioce =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.hbChoice);
        hb.setAdapter (hbChioce);

        ArrayAdapter < String > packAddapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.packTypeChoice ());
        pack.setAdapter (packAddapter);
        ArrayAdapter < String > adverseEffectAdapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.advertEventTypeChoice ());
        adverseEvent.setAdapter (adverseEffectAdapter);
        ArrayAdapter < String > endAdapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.time);
        endAm.setAdapter (endAdapter);
        ArrayAdapter < String > startAdapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.time);
        startAm.setAdapter (startAdapter);

        SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSSZ a");
        nowTime = df.format (new Date ());

        System.out.println ("nowTime===" + nowTime);
        startHour.setText (nowTime.substring (11 , 13));
        startMin.setText (nowTime.substring (14 , 16));
        startAm.setSelection (formChoice.timecheck (nowTime.substring (29 , 31)));
        endHour.setText (nowTime.substring (11 , 13));
        endMin.setText (nowTime.substring (14 , 16));
        endAm.setSelection (formChoice.timecheck (nowTime.substring (29 , 31)));
    }

    private void setupForm () {


        try {
            System.out.println ("donation===" + donation);
            System.out.println ("donation===" + donation.getDonationDate ());
            System.out.println ("donation===" + donation);

            DateFormat hourFormat = new SimpleDateFormat ("hh");
            DateFormat minFormat = new SimpleDateFormat ("mm");
            DateFormat ampmFormat = new SimpleDateFormat ("a");

            DateformatControl dateformatControl = new DateformatControl ();

            Date bleedStart = dateformatControl.convertStringToDate (donation.getBleedStartTime ());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(bleedStart);
            calendar.add(Calendar.HOUR_OF_DAY, 3);
            bleedStart = calendar.getTime ();


            String starthour =  hourFormat.format (bleedStart);
            String startmin = minFormat.format (bleedStart);
            String startampm = ampmFormat.format (bleedStart);


            Date bleedend = dateformatControl.convertStringToDate (donation.getBleedEndTime ());

            calendar = Calendar.getInstance();
            calendar.setTime(bleedend);
            calendar.add(Calendar.HOUR_OF_DAY, 3);
            bleedend = calendar.getTime ();


            String endhour = hourFormat.format (bleedend);
            String endmin = minFormat.format (bleedend);
            String endampm = ampmFormat.format (bleedend);


            dinText.setText ("DIN: " + donation.getDonationIdentificationNumber ());
            donorNumberText.setText ("Donor Number: " + donation.getDonor ().getDonorNumber ());
            System.out.println ("dbatch===" + donation.getDonationBatch ());
            if (donation.getDonationBatch () != null)
                //     if(donation.getDonationBatch ().getVenue () != null)
                //        batch.setText ( donation.getDonationBatch ().getVenue ().getName () );
                if (donation.getVenue () != null)
                    batch.setText (donation.getVenue ().getName ());

            pack.setSelection (formChoice.packcheck (donation.getPackType ().getPackType ()));
            donationText.setText (donation.getDonationType ().getDonationType ());
            startHour.setText (starthour);
            startMin.setText (startmin);
            startAm.setSelection (formChoice.timecheck (startampm));
            endHour.setText (endhour);
            endMin.setText (endmin);
            endAm.setSelection (formChoice.timecheck (endampm));
            weight.setText (donation.getDonorWeight ());
            if(donation.getHaemoglobinCount () != null)
            if (!donation.getHaemoglobinCount ().isEmpty ())
                if (donation.getHaemoglobinCount ().equalsIgnoreCase ("PASS"))
                    hb.setSelection (1);
                else if (donation.getHaemoglobinCount ().equalsIgnoreCase ("FAIL"))
                    hb.setSelection (2);
                else hb.setSelection (0);
            bpMax.setText (donation.getBloodPressureSystolic ());
            bpMin.setText (donation.getBloodPressureDiastolic ());
            pulse.setText (donation.getDonorPulse ());
            if (donation.getAdverseEvent () != null) {
                adverseEvent.setSelection (formChoice.adverseEventcheck (donation.getAdverseEvent ().getType ().getName ()));
                description.setText (donation.getAdverseEvent ().getComment ());
            }

        } catch ( Exception e ) {
            e.printStackTrace ();
            System.out.println ("eeeeeeeeee=======" + e.getMessage ());
        }
    }

    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }


    @Override
    public void onItemSelected (AdapterView < ? > parent , View view , int position , long id) {
        System.out.println ("position" + parent + "==" + view + "====" + batch + "++++" + id);

        AppCompatTextView selectedText = ( AppCompatTextView ) view;

        if (selectedText != null) {
            try {
                if (parent == pack && position != 0)
                    selectedText.setError (null);
                if (parent == adverseEvent && position != 0)
                    selectedText.setError (null);
            } catch ( Exception e ) {
                e.printStackTrace ();
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  this.menu.getItem(3).setVisible(false);
        //this.menu.getItem(2).setVisible(false);

        getMenuInflater ().inflate (R.menu.void_menu , menu);
        MenuItem voidItem = menu.getItem (0);
        if (donation.isCreatedFromMobile ()) {
            voidItem.setVisible (true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId ();


        //noinspection SimplifiableIfStatement
        if (id == R.id.void_record) {

            try {
                List < SyncRequest > syncRequestDonor =
                        SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                donation.getId ().toString () , "addDonation");
                SyncRequest.deleteInTx (syncRequestDonor);

                syncRequestDonor =
                        SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                donation.getId ().toString () , "updateDonation");
                SyncRequest.deleteInTx (syncRequestDonor);
                syncRequestDonor =
                        SyncRequest.find (SyncRequest.class , "pay_load = ? and api_method_name = ? " ,
                                donation.getId ().toString () , "updateDonation");

                SyncRequest.deleteInTx (syncRequestDonor);
                Donation.delete (donation);

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
    public void onNothingSelected (AdapterView < ? > parent) {

    }
}
