package real_deal.bsis.main_activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import real_deal.bsis.Adapter.FindDonorAdapter;
import real_deal.bsis.R;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.model.Contact;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.Donor;


public class FindDonorActivity extends AppCompatActivity implements TextWatcher {
    Button       barcode_button;
    Button       searchButton;
    Button       addDonorButton;
    Button       clearButton;
    Button       previous;
    Button       next;
    EditText     input_FirstName;
    EditText     input_MiddelName;
    EditText     input_LastName;
    EditText     input_DonorNumber;
    EditText     input_PhoneNumber;
    EditText     input_DIN;
    RecyclerView recyclerView;
    TextView     textNumberCount;
    TextView     noOfPagination;
    LinearLayout paginationLayout;
    int          PERMISSION_REQUEST_CODE = 200;

    List < Donor > donors;
    int            noOfDonor         = 0;
    int            presentPagination = 1;
    SearchAsync    searchAsync;

    public FindDonorActivity () {

    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.fragment_find_donor);
        recyclerView = findViewById (R.id.find_result_list);
        assert getSupportActionBar () != null;   //null check
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);   //show back button


        input_FirstName   = findViewById (R.id.input_firstname);
        input_MiddelName  = findViewById (R.id.input_middelname);
        input_LastName    = findViewById (R.id.input_lastname);
        input_DonorNumber = findViewById (R.id.input_donor_hash);
        input_DIN         = findViewById (R.id.din);
        input_PhoneNumber = findViewById (R.id.input_phone);
        searchButton      = findViewById (R.id.search_button);
        clearButton       = findViewById (R.id.clear_button);
        barcode_button    = findViewById (R.id.barcode);
        addDonorButton    = findViewById (R.id.add_donor_button);
        textNumberCount   = findViewById (R.id.text_number_of_result);
        previous          = findViewById (R.id.previous);
        next              = findViewById (R.id.next);
        noOfPagination    = findViewById (R.id.no_of_pagination);
        paginationLayout  = findViewById (R.id.pagination_layout);


        addDonorButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                startActivity (new Intent (FindDonorActivity.this , AddDonor.class));
            }
        });

        barcode_button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View veiw) {
                if (checkPermission ()) {
                    barcord ();
                } else {
                    requestPermission ();
                }
            }
        });

        clearButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View veiw) {

                input_FirstName.setText ("");
                input_MiddelName.setText ("");
                input_LastName.setText ("");
                input_DIN.setText ("");
                input_DonorNumber.setText ("");
                input_PhoneNumber.setText ("");
                recyclerView.setAdapter (null);
                noOfPagination.setText ("");
                paginationLayout.setVisibility (View.GONE);
                textNumberCount.setText ("0  Donor(s) found |");

            }
        });

        searchButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (empityChecker ()) {
                    searchAsync = new SearchAsync ();
                    searchAsync.execute ();
                }

            }

        });
        next.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (presentPagination < noOfDonor) {
                    int listStart =
                            presentPagination * (new HardCodedValue ().NUMBER_OF_PAGINATION);
                    int listend = listStart + (new HardCodedValue ().NUMBER_OF_PAGINATION);
                    presentPagination++;
                    System.out.println ("presentPagination===" + presentPagination);
                    System.out.println ("liststart===" + listStart);
                    System.out.println ("listend===" + listend);
                    if (presentPagination == noOfDonor)
                        listend = donors.size () - 1;
                    setRecycleViewAddapterData (donors.subList (listStart , listend));
                    noOfPagination.setText ("  " + presentPagination + " of " + noOfDonor + " pages ");
                    if (presentPagination == noOfDonor)
                        next.setEnabled (true);
                    previous.setEnabled (true);
                }


            }

        });
        previous.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (presentPagination > 1) {
                    presentPagination--;
//                    if(presentPagination == noOfDonor-1)
//                        presentPagination--;

                    int listStart =
                            (presentPagination - 1) * (new HardCodedValue ().NUMBER_OF_PAGINATION);
                    int listEnd = listStart + (new HardCodedValue ().NUMBER_OF_PAGINATION - 1);
                    System.out.println ("listst==" + listStart);
                    System.out.println ("listt==" + listEnd);
                    setRecycleViewAddapterData (donors.subList (listStart , listEnd));
                    noOfPagination.setText ("  " + presentPagination + " of " + noOfDonor + " pages ");
                    if (presentPagination == 1)
                        previous.setEnabled (true);
                    next.setEnabled (true);
                }

            }

        });


        input_FirstName.addTextChangedListener (this);
        input_MiddelName.addTextChangedListener (this);
        input_LastName.addTextChangedListener (this);
        input_DonorNumber.addTextChangedListener (this);
        input_DIN.addTextChangedListener (this);
        input_PhoneNumber.addTextChangedListener (this);

        recyclerView.setLayoutManager (new LinearLayoutManager (this));
    }

    public void setRecycleViewAddapterData (List < Donor > donors) {
        recyclerView.setAdapter (new FindDonorAdapter (donors , FindDonorActivity.this));

    }


    public boolean empityChecker () {
        if (input_FirstName.getText ().toString ().isEmpty () &&
                input_MiddelName.getText ().toString ().isEmpty () &&
                input_LastName.getText ().toString ().isEmpty () &&
                input_DonorNumber.getText ().toString ().isEmpty () &&
                input_PhoneNumber.getText ().toString ().isEmpty () &&
                input_DIN.getText ().toString ().isEmpty ()) {
            input_FirstName.setError ("empty");
            input_LastName.setError ("empty");
            input_MiddelName.setError ("empty");
            input_PhoneNumber.setError ("empty");
            input_DIN.setError ("empty");
            input_DonorNumber.setError ("empty");
            return false;
        } else {
            input_FirstName.setError (null);
            input_LastName.setError (null);
            input_MiddelName.setError (null);
            input_PhoneNumber.setError (null);
            input_DIN.setError (null);
            input_DonorNumber.setError (null);
        }

        return true;
    }

    public void onItemSelected (Donor donor , Context context) {

        Intent intent = new Intent (context , Donor_Detail.class);
        Bundle bundle = new Bundle ();
        bundle.putParcelable ("donor" , donor);
        intent.putExtras (bundle);
        context.startActivity (intent);
        finish ();

    }

    @Override
    public boolean onSupportNavigateUp () {
        finish ();
        return true;
    }

    public void barcord () {
        Intent intent = new Intent (getBaseContext () , Barcode.class);
        startActivityForResult (intent , 14565);

    }

    @Override
    public void onActivityResult (int requestCode , int resultCode , Intent data) {
        super.onActivityResult (requestCode , resultCode , data);
        if (data != null)
            if (requestCode == 14565) {
                String text = data.getStringExtra ("result");
                input_DIN.setText (text);
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
        return ContextCompat.checkSelfPermission (getBaseContext () , Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission () {

        ActivityCompat.requestPermissions (this ,
                new String[]{Manifest.permission.CAMERA} ,
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onBackPressed () {
        if (searchAsync != null) {
            searchAsync.cancel (true);
            searchButton.setText ("Search");
        }
        super.onBackPressed ();
    }

    @Override
    protected void onPause () {
        super.onPause ();
        if (searchAsync != null) {
            searchAsync.cancel (true);
            searchButton.setText ("Search");
        }

    }

    @Override
    public void beforeTextChanged (CharSequence s , int start , int count , int after) {

    }

    @Override
    public void onTextChanged (CharSequence s , int start , int before , int count) {

    }

    @Override
    public void afterTextChanged (Editable s) {

        if (!input_FirstName.getText ().toString ().isEmpty () ||
                !input_MiddelName.getText ().toString ().isEmpty () ||
                !input_LastName.getText ().toString ().isEmpty ()) {
            input_FirstName.setEnabled (true);
            input_MiddelName.setEnabled (true);
            input_LastName.setEnabled (true);
            input_PhoneNumber.setEnabled (false);
            input_DIN.setEnabled (false);
            input_DonorNumber.setEnabled (false);
            barcode_button.setEnabled (false);
        } else if (!input_DIN.getText ().toString ().isEmpty ()) {
            input_FirstName.setEnabled (false);
            input_MiddelName.setEnabled (false);
            input_LastName.setEnabled (false);
            input_PhoneNumber.setEnabled (false);
            input_DIN.setEnabled (true);
            input_DonorNumber.setEnabled (false);
            barcode_button.setEnabled (true);
        } else if (!input_DonorNumber.getText ().toString ().isEmpty ()) {
            input_FirstName.setEnabled (false);
            input_MiddelName.setEnabled (false);
            input_LastName.setEnabled (false);
            input_PhoneNumber.setEnabled (false);
            input_DIN.setEnabled (false);
            input_DonorNumber.setEnabled (true);
            barcode_button.setEnabled (false);
        } else if (!input_PhoneNumber.getText ().toString ().isEmpty ()) {
            input_FirstName.setEnabled (false);
            input_MiddelName.setEnabled (false);
            input_LastName.setEnabled (false);
            input_PhoneNumber.setEnabled (true);
            input_DIN.setEnabled (false);
            input_DonorNumber.setEnabled (false);
            barcode_button.setEnabled (false);

        } else {
            input_FirstName.setEnabled (true);
            input_MiddelName.setEnabled (true);
            input_LastName.setEnabled (true);
            input_PhoneNumber.setEnabled (true);
            input_DIN.setEnabled (true);
            input_DonorNumber.setEnabled (true);
            barcode_button.setEnabled (true);

        }


    }


    public class SearchAsync extends AsyncTask < Void, Void, String > {
        public SearchAsync () {
            super ();
        }

        @Override
        protected void onPreExecute () {
            searchButtonEnable (false);
            super.onPreExecute ();
        }

        @Override
        protected String doInBackground (Void... voids) {


            donors = new ArrayList <> ();
            if (!input_FirstName.getText ().toString ().isEmpty () || !input_MiddelName.getText ().toString ().isEmpty () ||
                    !input_LastName.getText ().toString ().isEmpty ()) {
                String firstname = input_FirstName.getText ().toString ().toUpperCase ().trim ();
                String middlename = input_MiddelName.getText ().toString ().toLowerCase ().trim ();
                String lastname = input_LastName.getText ().toString ().toLowerCase ().trim ();

                donors = Donor.findWithQuery (Donor.class ,
                        "SELECT * FROM DONOR WHERE first_name like '%" + firstname + "%' and middle_name like '%" + middlename + "%' and last_name like '%" + lastname + "%'");
            } else if (!input_DonorNumber.getText ().toString ().isEmpty ()) {
                donors =
                        Donor.find (Donor.class , "donor_number=?" , input_DonorNumber.getText ().toString ());
            } else if (!input_DIN.getText ().toString ().isEmpty ()) {
                System.err.println ("din==" + input_DIN.getText ().toString ());
                List < Donation > donations = Donation.find (Donation.class ,
                        "donation_identification_number = ? " , input_DIN.getText ().toString ());
                if (!donations.isEmpty ())
                    donors.add (donations.get (0).getDonor ());
            } else if (!input_PhoneNumber.getText ().toString ().isEmpty ()) {
                List < Contact > contacts = Contact.find (Contact.class ,
                        "mobile_number = ?" , input_PhoneNumber.getText ().toString ());
                System.out.println ("phone search ==" + Select.from (Contact.class).count ());
                if (contacts.size () != 0)
                    donors =
                            Donor.find (Donor.class , "contact=?" , contacts.get (0).getId ().toString ());
            }


            if (searchAsync.isCancelled ())
                return null;

            int donorSize = donors.size ();

            if (donorSize > (new HardCodedValue ().NUMBER_OF_PAGINATION)) {
                paginationLayoutVisiblity (View.VISIBLE);
                noOfDonor         = donorSize / (new HardCodedValue ().NUMBER_OF_PAGINATION) + 1;
                presentPagination = 1;
                System.out.println ("donorSize--" + noOfDonor);
                previousButtonEnable (false);

                if (searchAsync.isCancelled ())
                    return null;


                setRecycleViewDataSet (donors.subList (0 , new HardCodedValue ().NUMBER_OF_PAGINATION));
                noOfPaginationTextView ("  " + presentPagination + " of " + noOfDonor + " pages ");
            } else {
                paginationLayoutVisiblity (View.GONE);
                setRecycleViewDataSet (donors.subList (0 , donorSize));

            }
            textNumberCountTextView (donors.size () + " Donor(s) found |");
            return null;
        }

        public void paginationLayoutVisiblity (final int visibility) {

            Handler handler = new Handler (Looper.getMainLooper ()) {
                @Override
                public void handleMessage (Message msg) {
                    //super.handleMessage (msg);
                    paginationLayout.setVisibility (visibility);
                }
            };
            handler.sendEmptyMessage (1);
        }

        public void previousButtonEnable (final boolean enable) {

            Handler handler = new Handler (Looper.getMainLooper ()) {
                @Override
                public void handleMessage (Message msg) {
                    //super.handleMessage (msg);
                    previous.setEnabled (enable);
                }
            };
            handler.sendEmptyMessage (1);
        }

        public void searchButtonEnable (final boolean enable) {

            Handler handler = new Handler (Looper.getMainLooper ()) {
                @Override
                public void handleMessage (Message msg) {

                    if (enable) {
                        searchButton.setText ("Search");
                        searchButton.setEnabled (true);
                    } else {
                        searchButton.setText ("Searching...");
                        searchButton.setEnabled (false);
                    }
                }
            };
            handler.sendEmptyMessage (1);
        }

        public void textNumberCountTextView (final String text) {

            Handler handler = new Handler (Looper.getMainLooper ()) {
                @Override
                public void handleMessage (Message msg) {
                    //super.handleMessage (msg);
                    textNumberCount.setText (text);
                }
            };
            handler.sendEmptyMessage (1);
        }

        public void noOfPaginationTextView (final String text) {

            Handler handler = new Handler (Looper.getMainLooper ()) {
                @Override
                public void handleMessage (Message msg) {
                    //super.handleMessage (msg);
                    noOfPagination.setText (text);
                }
            };
            handler.sendEmptyMessage (1);
        }

        public void setRecycleViewDataSet (final List < Donor > donors) {

            Handler handler = new Handler (Looper.getMainLooper ()) {
                @Override
                public void handleMessage (Message msg) {
                    //super.handleMessage (msg);
                    // recyclerView.setAdapter (null);
                    recyclerView.setAdapter (new FindDonorAdapter (donors , FindDonorActivity.this));
                }
            };
            handler.sendEmptyMessage (1);
        }


        @Override
        protected void onPostExecute (String s) {
            super.onPostExecute (s);
            searchButtonEnable (true);
        }
    }

}
