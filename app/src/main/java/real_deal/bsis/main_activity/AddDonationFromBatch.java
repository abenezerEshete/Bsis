package real_deal.bsis.main_activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
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
import real_deal.bsis.donor_detail.Overview;
import real_deal.bsis.model.AdverseEvent;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.DonationBatch;
import real_deal.bsis.model.DonationType;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;
import real_deal.bsis.model.Location;
import real_deal.bsis.model.PackType;
import real_deal.bsis.model.SyncRequest;

public class AddDonationFromBatch extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText din;
    TextView donorinfo;
    TextView errorInfo;
    EditText donorNumber;
    Spinner  pack;
    Spinner  donation;
    EditText startHour;
    EditText startMin;
    Spinner  startAm;
    EditText endHour;
    EditText endMin;
    Spinner  endAm;
    EditText weight;
    Spinner hb;
    EditText bpMin;
    EditText bpMax;
    EditText pulse;
    EditText description;
    Spinner  adverseEvent;
    Button   save;
    Button   cancel;
    Button   barcodeButton;


    FormChoice    formChoice;
    int           PERMISSION_REQUEST_CODE = 200;
    DonationBatch donationBatch;
    Donor         donor;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_donation_from_batch);
        assert getSupportActionBar () != null;   //null check
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);   //show back button
        String batchNumber = getIntent ().getExtras ().get ("batchNumber").toString ();

        List < DonationBatch > donationBatches =
                DonationBatch.find (DonationBatch.class , "batch_number = ?" , batchNumber);

        System.out.println ("batchnumber==" + batchNumber);
        if (!donationBatches.isEmpty ())
            donationBatch = donationBatches.get (0);
        System.out.println ("batchnumber=11=" + donationBatch);


        veiwBending ();
        formChoise ();

        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (validation ())
                    return;
                if (checkDueDate ())
                    return;

                SaveDonation ();
                Toast.makeText (getBaseContext () , "Donation Successfull" , Toast.LENGTH_LONG).show ();
            }
        });
        cancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });


        barcodeButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View veiw) {
                if (checkPermission ()) {
                    barcord ();
                } else {
                    requestPermission ();
                }
            }
        });

        pack.setOnItemSelectedListener (this);
        donation.setOnItemSelectedListener (this);
        adverseEvent.setOnItemSelectedListener (this);


        donorNumber.setOnFocusChangeListener (new View.OnFocusChangeListener () {
            @Override
            public void onFocusChange (View v , boolean hasFocus) {
                if (!hasFocus) {
                    String dn = donorNumber.getText ().toString ();
                    List < Donor > donorList = Donor.find (Donor.class , "donor_number = ?" , dn);
                    if (!donorList.isEmpty ()) {
                        donor = donorList.get (0);
                        donorinfo.setText (" " + donor.getFirstName () + " " + donor.getMiddleName () + " " + donor.getLastName ());
                    } else {
                        donor = null;
                    }
                }
            }
        });
        System.out.println ("doonation batch==" + batchNumber);
    }

    private boolean checkDueDate () {

        if (donor != null) {

            boolean isDued = false;
            List < Donation > donations =
                    Donation.find (Donation.class , "donor = ? " , donor.getId ().toString ());

            errorInfo.setText ("Please provide valid inputs\n" +
                    "Do not bleed donor");
            if (donations.size () > 0) {
                Donation donation = donations.get (donations.size () - 1);
                String isDue = new Overview ().dueDateCualculation (donation)[ 1 ];
                if (isDue.endsWith ("date_is_reached"))
                    isDued = true;
            }

            List < DonorDeferral > donorDeferralList =
                    DonorDeferral.find (DonorDeferral.class , "deferred_donor = ? " ,
                            donor.getId ().toString ());
            String defResult =
                    new Overview ().currentlyDefferedCulculation (donorDeferralList)[ 1 ];
            boolean isDeferred = false;
            if (defResult.equals ("currently_referred")) {
                isDeferred = true;
            }

            if (isDued || isDeferred)
                return true;
        } else
            donorNumber.setError (new HardCodedValue ().EMPTY_MESSAGE);


        return false;

    }

    private void SaveDonation () {

        System.out.println ("herre-------------------------");
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

            Calendar calendar = Calendar.getInstance();
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
            Location venue = donationBatch.getVenue ();
            PackType packTypeText =
                    formChoice.getPackTypeObject (pack.getSelectedItemPosition ());
            DonationType donationTypeO =
                    formChoice.getDonationTypeObject (donation.getSelectedItemPosition ());


            String systolid =
                    bpMax.getText ().toString ().isEmpty () ? null : bpMax.getText ().toString ();
            String diastolic =
                    bpMin.getText ().toString ().isEmpty () ? null : bpMin.getText ().toString ();
            String dWeight =
                    weight.getText ().toString ().isEmpty () ? null : weight.getText ().toString ();
            String heamB =
                    hb.getSelectedItem ().toString ().equalsIgnoreCase ("label") ? null : hb.getSelectedItem ().toString ();
            String pulseText =
                    pulse.getText ().toString ().isEmpty () ? null : pulse.getText ().toString ();


            AdverseEvent adverse = null;
            if (adverseEvent.getSelectedItemPosition () != 0) {
                adverse =
                        new AdverseEvent (formChoice.getAdverseEventObject (adverseEvent.getSelectedItemPosition ()) , description.getText ().toString ());
                adverse.save ();
            }

            Donation donation =
                    new Donation (din.getText ().toString () , donor , donationTypeO , systolid , diastolic ,
                            donationBatch , venue , heamB , pulseText ,
                            dWeight , dateformatControl.convertDateToShortString (new Date ()) , packTypeText ,
                            adverse , starttime , endtime);
            donation.setDonationDate (donationBatch.getDonationBatchDate ());

            Donation.save (donation);

            System.out.println ("Start time=" + starttime);
            System.out.println ("end time=" + endtime);
            SyncRequest.save (new SyncRequest ("addDonation" , "POST" , donation.getId ().toString ()));

            finish ();

        } catch ( Exception e ) {
            e.printStackTrace ();
        }


    }

    private void veiwBending () {


        donorinfo     = findViewById (R.id.donor_info_text);
        donorNumber   = findViewById (R.id.donor_number);
        pack          = findViewById (R.id.pack);
        donation      = findViewById (R.id.donation);
        din           = findViewById (R.id.din);
        startHour     = findViewById (R.id.start_hour);
        startMin      = findViewById (R.id.start_min);
        startAm       = findViewById (R.id.start_am);
        endHour       = findViewById (R.id.end_hour);
        endMin        = findViewById (R.id.end_min);
        endAm         = findViewById (R.id.end_am);
        weight        = findViewById (R.id.weight);
        hb            = findViewById (R.id.hb);
        bpMin         = findViewById (R.id.bp_min);
        bpMax         = findViewById (R.id.bp_max);
        pulse         = findViewById (R.id.pulse);
        description   = findViewById (R.id.description);
        adverseEvent  = findViewById (R.id.adverse_effect);
        errorInfo     = findViewById (R.id.error_info);
        save          = findViewById (R.id.save);
        cancel        = findViewById (R.id.cancel);
        barcodeButton = findViewById (R.id.barcode);


    }

    private boolean validation () {

        AppCompatTextView packError = ( AppCompatTextView ) pack.getSelectedView ();
        AppCompatTextView donationError = ( AppCompatTextView ) donation.getSelectedView ();


        boolean error = false;

        try {

            if (donorNumber.getText ().toString ().isEmpty () && din.getText ().toString ().isEmpty () &&
                    pack.getSelectedItemPosition () == 0 && donation.getSelectedItemPosition () == 0) {

                din.setError (new HardCodedValue ().EMPTY_MESSAGE);
                donorNumber.setError (new HardCodedValue ().EMPTY_MESSAGE);
                packError.setError (new HardCodedValue ().EMPTY_MESSAGE);
                donationError.setError (new HardCodedValue ().EMPTY_MESSAGE);

                error = true;

            }
            if (donorNumber.getText ().toString ().isEmpty ()) {
                donorNumber.setError (new HardCodedValue ().EMPTY_MESSAGE);
                error = true;
            }
            if (din.getText ().toString ().isEmpty ()) {
                din.setError (new HardCodedValue ().EMPTY_MESSAGE);
                error = true;
            }
            if (pack.getSelectedItemPosition () == 0) {
                packError.setError (new HardCodedValue ().EMPTY_MESSAGE);
                error = true;
            }
            if (donation.getSelectedItemPosition () == 0) {
                donationError.setError (new HardCodedValue ().EMPTY_MESSAGE);
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
            if (!pulse.getText ().toString ().isEmpty () && Integer.parseInt (pulse.getText ().toString ()) > 200) {
                pulse.setError ("Please enter a value below 200");
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
            if (donor == null) {
                donorNumber.setError (" Invalid Donor ");
                error = true;
            }


            return error;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return true;
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
        ArrayAdapter < String > donationAdapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.donationTypeChoice ());
        donation.setAdapter (donationAdapter);
        ArrayAdapter < String > endAdapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.time);
        endAm.setAdapter (endAdapter);
        ArrayAdapter < String > startAdapter =
                new ArrayAdapter <> (this , R.layout.support_simple_spinner_dropdown_item , formChoice.time);
        startAm.setAdapter (startAdapter);

        DateFormat hourFormat = new SimpleDateFormat ("hh");
        DateFormat minFormat = new SimpleDateFormat ("mm");
        DateFormat ampmFormat = new SimpleDateFormat ("a");

        String hour = hourFormat.format (new Date ());
        String min = minFormat.format (new Date ());
        String ampm = ampmFormat.format (new Date ());

        startHour.setText (hour);
        startMin.setText (min);
        startAm.setSelection (formChoice.timecheck (ampm));
        endHour.setText (hour);
        endMin.setText (min);
        endAm.setSelection (formChoice.timecheck (ampm));
    }

    public void barcord () {
        Intent intent = new Intent (getBaseContext () , Barcode.class);
        startActivityForResult (intent , 14565);

    }

    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }

    @Override
    public void onActivityResult (int requestCode , int resultCode , Intent data) {
        super.onActivityResult (requestCode , resultCode , data);
        if (data != null)
            if (requestCode == 14565) {
                String text = data.getStringExtra ("result");
                din.setText (text);
            }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode , @NonNull String[] permissions , @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText (this.getApplicationContext () , "Permission Granted" , Toast.LENGTH_SHORT).show ();
                    barcord ();

                    // main logic
                } else {
                    Toast.makeText (this , "Permission Denied" , Toast.LENGTH_SHORT).show ();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission (this , Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel ("You need to allow access permissions" ,
                                    new DialogInterface.OnClickListener () {
                                        @Override
                                        public void onClick (DialogInterface dialog , int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission ();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel (String message , DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder (this)
                .setMessage (message)
                .setPositiveButton ("OK" , okListener)
                .setNegativeButton ("Cancel" , null)
                .create ()
                .show ();
    }

    private boolean checkPermission () {
        // Permission is not granted
        return ContextCompat.checkSelfPermission (getBaseContext () , Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission () {

        ActivityCompat.requestPermissions (this ,
                new String[]{Manifest.permission.CAMERA} ,
                PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onItemSelected (AdapterView < ? > parent , View view , int position , long id) {

        AppCompatTextView selectedText = ( AppCompatTextView ) view;

        try {
            if (parent == pack && position != 0)
                selectedText.setError (null);

            if (parent == donation && position != 0)
                selectedText.setError (null);
            if (parent == adverseEvent && position != 0)
                selectedText.setError (null);
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

    }

    @Override
    public void onNothingSelected (AdapterView < ? > parent) {

    }

    @Override
    public void onPointerCaptureChanged (boolean hasCapture) {

    }

}
