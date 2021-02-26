package real_deal.bsis.donor_detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import real_deal.bsis.R;
import real_deal.bsis.config.DateformatControl;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Overview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Overview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Overview extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View  v;
    Donor donor;
    // TODO: Rename and change types of parameters
    private String                        mParam1;
    private String                        mParam2;
    private OnFragmentInteractionListener mListener;

    public Overview () {
        // Required empty public constructor
    }

    public static Overview newInstance (String param1 , String param2) {
        Overview fragment = new Overview ();
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
        v = inflater.inflate (R.layout.fragment_overview , container , false);


        fillValue ();

        return v;
    }

    private void fillValue () {

        donor = getActivity ().getIntent ().getExtras ().getParcelable ("donor");

        TextView dueToDonate = v.findViewById (R.id.text_due_to_don);
        TextView currentlyDeferred = v.findViewById (R.id.currentlydefered);
        TextView tatalDonation = v.findViewById (R.id.total_donation);
        TextView donorNote = v.findViewById (R.id.input_donor_note);
        TextView dateofFirstDonation = v.findViewById (R.id.date_of_first_donation);
        TextView previousDonation = v.findViewById (R.id.previous_donation);

        List < DonorDeferral > donorDeferralList =
                DonorDeferral.find (DonorDeferral.class , "deferred_donor = ? " , donor.getId ().toString ());
        String deferredResult[] = new Overview ().currentlyDefferedCulculation (donorDeferralList);
        String isDeferred = deferredResult[ 1 ];
        String defferedDate = deferredResult[ 0 ];
        if (isDeferred.equals ("currently_referred")) {
            currentlyDeferred.setText ("Currently Deferred: " + defferedDate);
            dueToDonate.setText ("Due To Donate:  Currently deferred");
        }
        else
         currentlyDeferred.setText ("Currently Deferred: No current deferrals");


        List < Donation > donations =
                Donation.find (Donation.class , "donor=?" , donor.getId ().toString ());
        if (donations != null && donations.size () > 0) {
            tatalDonation.setText ("Total Donations:  " + donations.size ());

            DateformatControl dateformatControl = new DateformatControl ();
            dateofFirstDonation.setText ("Date of First Donation:  " + dateformatControl.convertToShortDate (donations.get (0).getDonationDate ()));
            previousDonation.setText ("Date of previous Donation:  " + dateformatControl.convertToShortDate (donations.get (donations.size () - 1).getDonationDate ()));
            Donation donation = donations.get (donations.size () - 1);

            if(!isDeferred.equals ("currently_referred")) {
                if (!donation.getDonationDate ().isEmpty ())
                    dueToDonate.setText ("Due To Donate:  " + dueDateCualculation (donation)[ 0 ]);
            }
            else
            dueToDonate.setText ("Due To Donate:  Currently deferred");



        }
        else {
            tatalDonation.setText ("Total Donations:\t" + 0);
            donorNote.setText ("Donor Notes:\t" + donor.getNotes ());
            dateofFirstDonation.setVisibility (View.GONE);
            previousDonation.setVisibility (View.GONE);
        }
         //  currentlyDeferred.setText ("Currently Deferred: "+currentlyDefferedCulculation (donorDeferralList));


    }

    public String[] dueDateCualculation (Donation donation) {

        DateformatControl dateformatControl = new DateformatControl ();
        Date donatationdate =
                dateformatControl.convertshortStringToDate (donation.getDonationDate ());
        Calendar cal1 = new GregorianCalendar ();
        cal1.setTime (donatationdate);
        cal1.add (Calendar.MONTH , +3);
        cal1.add (Calendar.DATE,-1);
        Date dueDate = cal1.getTime ();
        String[] dueToDoanteText = new String[ 2 ];

        dueToDoanteText[ 0 ] = dateformatControl.convertDateToShortString (dueDate);
        dueToDoanteText[ 1 ] = "";
        if (new Date ().after (dueDate))
            dueToDoanteText[ 1 ] = "date_is_not_reached";
        else
            dueToDoanteText[ 1 ] = "date_is_reached";



        return dueToDoanteText;
    }

    public String[] currentlyDefferedCulculation (List < DonorDeferral > donorDeferralList) {
        if (donorDeferralList == null || donorDeferralList.isEmpty ())
            return new String[]{"" , ""};
        DateformatControl dateformatControl = new DateformatControl ();
        Date untilDate = new Date ();
        Date defUntil = null;

        String[] deferedResult = new String[ 2 ];
        deferedResult[ 0 ] = "";
        deferedResult[ 1 ] = "";
        for (int i = 0; i < donorDeferralList.size (); i++) {
            DonorDeferral deferral = donorDeferralList.get (i);
            defUntil =
                    dateformatControl.convertshortStringToDate (deferral.getDeferredUntil ());//convertStringToDate (deferral.getDeferredUntil ());

            if (untilDate.before (defUntil)) {
                deferedResult[ 1 ] = "currently_referred";
                untilDate          = defUntil;
            }

        }
        deferedResult[ 0 ] =
                "Deferred Until " + dateformatControl.convertDateToShortString (defUntil);


        return deferedResult;
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


    @Override
    public void setEnterTransition (@Nullable Object transition) {
        super.setEnterTransition (transition);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }
}
