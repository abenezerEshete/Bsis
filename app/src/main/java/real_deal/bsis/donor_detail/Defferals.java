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

import real_deal.bsis.Adapter.DeferalAdapter;
import real_deal.bsis.R;
import real_deal.bsis.main_activity.AddDonorDeferal;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Defferals.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Defferals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Defferals extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DonorDeferral donorDeferral;
    Donor         donor;
    RecyclerView  recyclerView;
    int           pos = 0;
    TextView currentlyDefered;
    // TODO: Rename and change types of parameters
    private String                        mParam1;
    private String                        mParam2;
    private OnFragmentInteractionListener mListener;

    public Defferals () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Defferals.
     */
    // TODO: Rename and change types and number of parameters
    public static Defferals newInstance (String param1 , String param2) {
        Defferals fragment = new Defferals ();
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
        View v = inflater.inflate (R.layout.fragment_defferals , container , false);
        Bundle extra = getActivity ().getIntent ().getExtras ();
        donor = extra.getParcelable ("donor");


        Button addDonation = v.findViewById (R.id.button_add_deferral);
        recyclerView     = v.findViewById (R.id.deferal_list);
        currentlyDefered = v.findViewById (R.id.text_currently_defferal);
        recyclerView.setLayoutManager (new LinearLayoutManager (getContext ()));

        deferalList ();

        addDonation.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Bundle bundle = new Bundle ();
                bundle.putParcelable ("donor" , donor);
                Intent intent = new Intent (getActivity () , AddDonorDeferal.class);
                intent.putExtras (bundle);
                startActivity (intent);
            }
        });


        return v;
    }

    private void deferalList () {
        List < DonorDeferral > deferrals =
                DonorDeferral.find (DonorDeferral.class , "deferred_donor=?" , donor.getId ().toString ());

        recyclerView.setAdapter (new DeferalAdapter (deferrals , getContext ()));
        String deferredResult[] = new Overview ().currentlyDefferedCulculation (deferrals);
        String isDeferred = deferredResult[ 1 ];
        String defferedDate = deferredResult[ 0 ];
        if (isDeferred.equals ("currently_referred"))
            currentlyDefered.setText ("Currently Deferred: " + defferedDate);
        else
            currentlyDefered.setText ("Currently Deferred: No current deferrals");
        //System.out.println("list of donor==="+donor.getId());
        //  System.out.println("id======"+Select.from(DonorDeferral.class).list().get(0).getDeferredDonor().getId());

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

    public void onItemSelected (DonorDeferral deferral , Context context , int pos) {
        Intent intent = new Intent (context , AddDonorDeferal.class);
        Bundle bundle = new Bundle ();

        System.out.println ("deferallllllllll---" + deferral.getId ());
        bundle.putLong ("deferral" , deferral.getId ());
        intent.putExtras (bundle);
        context.startActivity (intent);

        this.pos = pos;
    }


    @Override
    public void onDetach () {
        super.onDetach ();
        mListener = null;
    }


    @Override
    public void onResume () {
        System.out.println ("on resume----------------------------------------------------------------");
        recyclerView.setAdapter (null);
        deferalList ();
        super.onResume ();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }
}
