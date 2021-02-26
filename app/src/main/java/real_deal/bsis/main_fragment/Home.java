package real_deal.bsis.main_fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import real_deal.bsis.R;
import real_deal.bsis.main_activity.FindDonorActivity;


public class Home extends Fragment {


    Button button_Add_Donor;
    Button button_Donors;
    Button button_Add_Donation;
    Button button_Donation_batch;
    Button button_Sync;
    Button button_Setting;
    private OnFragmentInteractionListener mListener;

    public Home () {
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_home , container , false);


        button_Add_Donor      = v.findViewById (R.id.home_add_donor_button);
        button_Donors         = v.findViewById (R.id.home_donors_button);
        button_Add_Donation   = v.findViewById (R.id.home_add_donation_button);
        button_Donation_batch = v.findViewById (R.id.home_donation_batch_button);
        button_Sync           = v.findViewById (R.id.home_sync_button);
        button_Setting        = v.findViewById (R.id.home_setting_button);

        button_Add_Donor.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                startActivity (new Intent (getActivity () , FindDonorActivity.class));
            }
        });

        button_Donors.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent;
                intent = new Intent (getActivity () , FindDonorActivity.class);
                startActivity (intent);
            }
        });

        button_Add_Donation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent;
                intent = new Intent (getActivity () , FindDonorActivity.class);
                startActivity (intent);
            }
        });
        button_Donation_batch.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                getFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new DonationButch ()).commit ();
            }
        });
        button_Sync.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                getFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new Offline ()).commit ();
            }
        });
        button_Setting.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                getFragmentManager ().beginTransaction ().replace (R.id.fragment_view , new Setting ()).commit ();
            }
        });

        return v;

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
