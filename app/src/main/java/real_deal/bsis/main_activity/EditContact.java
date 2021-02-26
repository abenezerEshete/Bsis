package real_deal.bsis.main_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.orm.query.Select;

import real_deal.bsis.R;
import real_deal.bsis.config.FormChoice;
import real_deal.bsis.model.Address;
import real_deal.bsis.model.Contact;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.SyncRequest;

public class EditContact extends AppCompatActivity {

    EditText email;
    EditText mobile;
    EditText home;
    EditText work;


    Spinner preferedContactType;
    Spinner preferedAddressType;

    EditText homeAddress;
    EditText homeCity;
    EditText homeProvines;
    EditText homeCountry;
    EditText homePostalCode;

    EditText postalAddress;
    EditText postalCity;
    EditText postalProvines;
    EditText postalCountry;
    EditText postalPostalCode;

    EditText workAddress;
    EditText workCity;
    EditText workProvines;
    EditText workCountry;
    EditText workPostalCode;

    Button save;
    Button edit;
    Button cancel;


    Donor      donor;
    FormChoice formChoice;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_contact);

        Donor d = getIntent ().getExtras ().getParcelable ("donor");
        donor = Donor.findById (Donor.class , d.getId ());

        ViewBending ();
        setupForm ();
        fillValue ();


        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                saveChange ();
            }
        });
        cancel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                finish ();
            }
        });


    }

    private void setupForm () {
        formChoice = new FormChoice ();
        ArrayAdapter < String > preferedAddressAdapter =
                new ArrayAdapter < String > (EditContact.this , R.layout.support_simple_spinner_dropdown_item , formChoice.addressTypechoice ());
        preferedAddressType.setAdapter (preferedAddressAdapter);
        ArrayAdapter < String > preferedContactAdapter =
                new ArrayAdapter <> (EditContact.this , R.layout.support_simple_spinner_dropdown_item , formChoice.contactMethodchoice ());
        preferedContactType.setAdapter (preferedContactAdapter);
    }

    private void saveChange () {

        donor = Donor.findById (Donor.class , donor.getId ());

        Contact contact;
        if (donor.getContact () != null)
            contact = donor.getContact ();
        else
            contact = new Contact (mobile.getText ().toString () , home.getText ().toString ()
                    , work.getText ().toString () , email.getText ().toString ());

        contact.setEmail (email.getText ().toString ());
        contact.setMobileNumber (mobile.getText ().toString ());
        contact.setHomeNumber (home.getText ().toString ());
        contact.setWorkNumber (work.getText ().toString ());
        Contact.save (contact);

        Address address;
        if (donor.getAddress () != null)
            address = donor.getAddress ();
        else
            address = new Address ();

        address.setHomeAddressLine1 (homeAddress.getText ().toString ());
        address.setHomeAddressCity (homeCountry.getText ().toString ());
        address.setHomeAddressProvince (homeProvines.getText ().toString ());
        address.setHomeAddressCountry (homeCountry.getText ().toString ());
        address.setHomeAddressZipcode (homePostalCode.getText ().toString ());

        address.setPostalAddressLine1 (postalAddress.getText ().toString ());
        address.setPostalAddressCity (postalCity.getText ().toString ());
        address.setPostalAddressProvince (postalProvines.getText ().toString ());
        address.setPostalAddressCountry (postalCountry.getText ().toString ());
        address.setPostalAddressZipcode (postalPostalCode.getText ().toString ());

        address.setWorkAddressLine1 (workAddress.getText ().toString ());
        address.setWorkAddressCity (workCity.getText ().toString ());
        address.setWorkAddressProvince (workProvines.getText ().toString ());
        address.setWorkAddressCountry (workCountry.getText ().toString ());
        address.setWorkAddressZipcode (workPostalCode.getText ().toString ());
        Address.save (address);


        if (preferedContactType.getSelectedItemPosition () != 0)
            donor.setContactMethodType (formChoice.preferedContactmethod (preferedContactType.getSelectedItemPosition ()));
        if (preferedAddressType.getSelectedItemPosition () != 0)
            donor.setAddressType (formChoice.preferedAddressType (preferedAddressType.getSelectedItemPosition ()));

        donor.setContact (contact);
        donor.setAddress (address);


        System.out.println ("success 1 ==" + Select.from (Donor.class).count ());
        System.out.println ("success 2 ==" + Donor.save (donor));
        System.out.println ("success 3 ==" + Select.from (Donor.class).count ());
        System.out.println ("success 4 ==" + donor.getContact ().getId ());
        System.out.println ("success 5 ==" + donor.getAddress ().getId ());

        Toast.makeText (this , "Successfully Saved " , Toast.LENGTH_LONG);
        SyncRequest.save (new SyncRequest ("updateDonor" , "PUT" , donor.getId ().toString ()));
        finish ();
    }

    private void fillValue () {



        System.out.println ("donor contact=" +donor.isCreatedFromMobile ());
        System.out.println ("donor contact=" + donor.getAddress ());
        if (donor.getContact () != null) {
            email.setText (donor.getContact ().getEmail ());
            mobile.setText (donor.getContact ().getMobileNumber ());
            home.setText (donor.getContact ().getHomeNumber ());
            work.setText (donor.getContact ().getWorkNumber ());
        }
        if (donor.getAddress () != null) {
            Address address = donor.getAddress ();
            homeAddress.setText (address.getHomeAddressLine1 ());
            homeCity.setText (address.getHomeAddressCity ());
            homeProvines.setText (address.getHomeAddressProvince ());
            homeCountry.setText (address.getHomeAddressCountry ());
            homePostalCode.setText (address.getHomeAddressZipcode ());

            postalAddress.setText (address.getPostalAddressLine1 ());
            postalCity.setText (address.getPostalAddressCity ());
            postalProvines.setText (address.getPostalAddressProvince ());
            postalCountry.setText (address.getPostalAddressCountry ());
            postalPostalCode.setText (address.getPostalAddressZipcode ());

            workAddress.setText (address.getWorkAddressLine1 ());
            workCity.setText (address.getWorkAddressCity ());
            workProvines.setText (address.getWorkAddressProvince ());
            workCountry.setText (address.getWorkAddressCountry ());
            workPostalCode.setText (address.getWorkAddressZipcode ());

        }

        if (donor.getContactMethodType () != null) {
            preferedContactType.setSelection (formChoice.preferedContactTypeCheck (donor.getContactMethodType ().getContactMethodType ()));
        }
        if (donor.getAddressType () != null) {
            preferedAddressType.setSelection (formChoice.prefereAddresstTypeCheck (donor.getAddressType ().getPreferredAddressType ()));
        }

    }

    private void ViewBending () {

        preferedContactType = findViewById (R.id.prefered_contact_type);
        preferedAddressType = findViewById (R.id.prefered_address_type);

        save   = findViewById (R.id.save);
        edit   = findViewById (R.id.edit);
        cancel = findViewById (R.id.cancel);

        email  = findViewById (R.id.text_email);
        mobile = findViewById (R.id.text_mobile);
        home   = findViewById (R.id.text_home);
        work   = findViewById (R.id.text_work);

        homeAddress    = findViewById (R.id.home_address);
        homeCity       = findViewById (R.id.home_city);
        homeProvines   = findViewById (R.id.home_province);
        homeCountry    = findViewById (R.id.home_country);
        homePostalCode = findViewById (R.id.home_postal_code);

        postalAddress    = findViewById (R.id.postal_address);
        postalCity       = findViewById (R.id.postal_city);
        postalProvines   = findViewById (R.id.postal_province);
        postalCountry    = findViewById (R.id.postal_country);
        postalPostalCode = findViewById (R.id.postal_postal_code);

        workAddress    = findViewById (R.id.work_address);
        workCity       = findViewById (R.id.work_city);
        workProvines   = findViewById (R.id.work_province);
        workCountry    = findViewById (R.id.work_country);
        workPostalCode = findViewById (R.id.work_postal_code);

    }

}
