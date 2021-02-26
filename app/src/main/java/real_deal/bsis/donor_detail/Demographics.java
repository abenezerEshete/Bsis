package real_deal.bsis.donor_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import real_deal.bsis.R;
import real_deal.bsis.config.FormChoice;
import real_deal.bsis.main_activity.EditContact;
import real_deal.bsis.model.Address;
import real_deal.bsis.model.Donor;

public class Demographics extends Fragment {

    EditText   email;
    EditText   mobile;
    EditText   home;
    EditText   work;
    Spinner    preferedContactType;
    Spinner    preferedAddressType;
    EditText   homeAddress;
    EditText   homeCity;
    EditText   homeProvines;
    EditText   homeCountry;
    EditText   homePostalCode;
    EditText   postalAddress;
    EditText   postalCity;
    EditText   postalProvines;
    EditText   postalCountry;
    EditText   postalPostalCode;
    EditText   workAddress;
    EditText   workCity;
    EditText   workProvines;
    EditText   workCountry;
    EditText   workPostalCode;
    Button     edit;
    Donor      donor;
    FormChoice formChoice;


    private OnFragmentInteractionListener mListener;

    public Demographics () {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

    }

    @Override
    public View onCreateView (final LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState) {

        View v = inflater.inflate (R.layout.fragment_demographics , container , false);

        donor = getActivity ().getIntent ().getExtras ().getParcelable ("donor");

        try {

            ViewBending (v);
            editableProperty (false);
            setupForm ();
            fillValue ();
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        System.out.println ("donor demographics can be edited ? :"+donor.isCreatedFromMobile ());
        if(!donor.isCreatedFromMobile ())
        {
            edit.setEnabled (false);
        }
        edit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                Intent intent = new Intent (getActivity () , EditContact.class);
                Bundle bundle = new Bundle ();
                bundle.putParcelable ("donor" , donor);
                intent.putExtras (bundle);
                startActivity (intent);
            }
        });


        System.out.println ("contact page--" + v.getId ());
        return v;
    }

    private void setupForm () {
        formChoice = new FormChoice ();
        try {
            ArrayAdapter < String > preferedAddressAdapter =
                    new ArrayAdapter < String > (getContext () , R.layout.support_simple_spinner_dropdown_item , formChoice.preferedAddressType);
            preferedAddressType.setAdapter (preferedAddressAdapter);
            ArrayAdapter < String > preferedContactAdapter =
                    new ArrayAdapter <> (getContext () , R.layout.support_simple_spinner_dropdown_item , formChoice.preferedContactType);
            preferedContactType.setAdapter (preferedContactAdapter);
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
    }

    private void fillValue () {


        try {
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
                try {
                    preferedContactType.setSelection (formChoice.preferedContactTypeCheck (donor.getContactMethodType ().getContactMethodType ()));
                } catch ( Exception e ) {
                    e.printStackTrace ();
                }
                if (donor.getAddressType () != null) {
                }
                try {

                    preferedAddressType.setSelection (formChoice.prefereAddresstTypeCheck (donor.getAddressType ().getPreferredAddressType ()));
                } catch ( Exception e ) {
                    e.printStackTrace ();
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

    }

    private void editableProperty (boolean b) {


        email.setEnabled (b);
        mobile.setEnabled (b);
        home.setEnabled (b);
        work.setEnabled (b);

        preferedAddressType.setEnabled (b);
        preferedContactType.setEnabled (b);

        homeAddress.setEnabled (b);
        homeCity.setEnabled (b);
        homeProvines.setEnabled (b);
        homeCountry.setEnabled (b);
        homePostalCode.setEnabled (b);

        postalAddress.setEnabled (b);
        postalCity.setEnabled (b);
        postalProvines.setEnabled (b);
        postalCountry.setEnabled (b);
        postalPostalCode.setEnabled (b);

        workAddress.setEnabled (b);
        workCity.setEnabled (b);
        workProvines.setEnabled (b);
        workCountry.setEnabled (b);
        workPostalCode.setEnabled (b);


    }

    @Override
    public void onResume () {
        donor = Donor.findById (Donor.class , donor.getId ());
        fillValue ();
        super.onResume ();
    }

    private void ViewBending (View v) {

        preferedContactType = v.findViewById (R.id.prefered_contact_type);
        preferedAddressType = v.findViewById (R.id.prefered_address_type);

        edit = v.findViewById (R.id.edit);

        email  = v.findViewById (R.id.text_email);
        mobile = v.findViewById (R.id.text_mobile);
        home   = v.findViewById (R.id.text_home);
        work   = v.findViewById (R.id.text_work);

        homeAddress    = v.findViewById (R.id.home_address);
        homeCity       = v.findViewById (R.id.home_city);
        homeProvines   = v.findViewById (R.id.home_province);
        homeCountry    = v.findViewById (R.id.home_country);
        homePostalCode = v.findViewById (R.id.home_postal_code);

        postalAddress    = v.findViewById (R.id.postal_address);
        postalCity       = v.findViewById (R.id.postal_city);
        postalProvines   = v.findViewById (R.id.postal_province);
        postalCountry    = v.findViewById (R.id.postal_country);
        postalPostalCode = v.findViewById (R.id.postal_postal_code);

        workAddress    = v.findViewById (R.id.work_address);
        workCity       = v.findViewById (R.id.work_city);
        workProvines   = v.findViewById (R.id.work_province);
        workCountry    = v.findViewById (R.id.work_country);
        workPostalCode = v.findViewById (R.id.work_postal_code);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed (Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction (uri);
        }
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach (context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = ( OnFragmentInteractionListener ) context;
        } else {
            throw new RuntimeException (context.toString ()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach () {
        super.onDetach ();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }
}
