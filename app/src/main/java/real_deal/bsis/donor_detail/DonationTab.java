package real_deal.bsis.donor_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import real_deal.bsis.Adapter.DonationAdapter;
import real_deal.bsis.R;
import real_deal.bsis.main_activity.AddDonationActivity;
import real_deal.bsis.main_activity.EditDonation;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;


public class DonationTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    Donor        donor;
    TextView dueToDonate;
    Button   addDonation;
    // TODO: Rename and change types of parameters
    private String                        mParam1;
    private String                        mParam2;
    private OnFragmentInteractionListener mListener;

    public DonationTab () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Donation.
     */
    // TODO: Rename and change types and number of parameters
    public static DonationTab newInstance (String param1 , String param2) {
        DonationTab fragment = new DonationTab ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1 , param1);
        args.putString (ARG_PARAM2 , param2);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if (getArguments () != null) {
            mParam1 = getArguments ().getString (ARG_PARAM1);
            mParam2 = getArguments ().getString (ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate (R.layout.fragment_donation , container , false);
        Bundle extra = getActivity ().getIntent ().getExtras ();
        donor = extra.getParcelable ("donor");

        addDonation  = v.findViewById (R.id.button_add_donation);
        dueToDonate  = v.findViewById (R.id.text_due_to_donate);
        recyclerView = v.findViewById (R.id.list_donations);
        recyclerView.setLayoutManager (new LinearLayoutManager (getContext ()));

        donationList ();

        System.out.println ("in donation tab");

        addDonation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                Intent intent = new Intent (getActivity () , AddDonationActivity.class);
                Bundle bundle = new Bundle ();
                bundle.putParcelable ("donor" , donor);
                intent.putExtras (bundle);
                startActivity (intent);
            }
        });


        return v;
    }

    private void donationList () {

        boolean addenable = true;
        List < Donation > donations =
                Donation.find (Donation.class , "donor=?" , donor.getId ().toString ());

        recyclerView.setAdapter (new DonationAdapter (donations , getContext ()));

        boolean isDued = false;
        if (donations.size () > 0) {
            Donation donation = donations.get (donations.size () - 1);
            String dueDate = new Overview ().dueDateCualculation (donation)[ 0 ];
            String isDue = new Overview ().dueDateCualculation (donation)[ 1 ];
            dueToDonate.setText ("Due To Donate:  " + dueDate);

            if (isDue.endsWith ("date_is_reached"))
                isDued = true;
        }

        List < DonorDeferral > donorDeferralList =
                DonorDeferral.find (DonorDeferral.class , "deferred_donor = ? " ,
                        donor.getId ().toString ());
        String defDate = new Overview ().currentlyDefferedCulculation (donorDeferralList)[ 0 ];
        String defResult = new Overview ().currentlyDefferedCulculation (donorDeferralList)[ 1 ];
        boolean isDeferred = false;
        if (defResult.equals ("currently_referred")) {
            isDeferred = true;
        }

        if (isDued || isDeferred)
            addDonation.setEnabled (false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed (Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction (uri);
        }
    }

    @Override
    public void onResume () {
        System.out.println ("on resume----------------------------------------------------------------");
        recyclerView.setAdapter (null);
        donationList ();
        super.onResume ();
    }

    public void onItemSelected (Donation donation , Context context) {

        Intent intent = new Intent (context , EditDonation.class);
        Bundle bundle = new Bundle ();
        bundle.putParcelable ("donation" , donation);
        intent.putExtras (bundle);
        context.startActivity (intent);
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
        void onFragmentInteraction (Uri uri);
    }
}
