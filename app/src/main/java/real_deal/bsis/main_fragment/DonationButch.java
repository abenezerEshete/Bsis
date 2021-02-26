package real_deal.bsis.main_fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.query.Select;

import java.util.List;

import real_deal.bsis.Adapter.BatchesAdapter;
import real_deal.bsis.R;
import real_deal.bsis.model.DonationBatch;


public class DonationButch extends Fragment {
    RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;


    public DonationButch () {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_donation_butch , container , false);
        recyclerView = v.findViewById (R.id.batch_list);
        donationBatchList ();
        recyclerView.setLayoutManager (new LinearLayoutManager (getContext ()));


        System.out.println ("no of batchs=: "+ Select.from (DonationBatch.class).count ());
        List < DonationBatch > donationBatches =
                DonationBatch.find (DonationBatch.class , "is_closed= ? and is_deleted = ? " , "0" , "0");

        System.out.println ("no of batchs=: "+ donationBatches.size () );


        return v;

    }


    private void donationBatchList () {

        String isCloased = "0";
        List < DonationBatch > donationBatches =
                DonationBatch.find (DonationBatch.class ,
                        "is_closed= ? and is_deleted = ? " , isCloased , isCloased);
        recyclerView.setAdapter (new BatchesAdapter (donationBatches , getContext ()));

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
