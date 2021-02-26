package real_deal.bsis.add_donor_fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import real_deal.bsis.R;
import real_deal.bsis.main_activity.Barcode;

public class Find_donor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int      PERMISSION_REQUEST_CODE = 200;
    EditText input_din;
    View     v;
    Button   barcode_button;
    // TODO: Rename and change types of parameters
    private String                        mParam1;
    private String                        mParam2;
    private OnFragmentInteractionListener mListener;

    public Find_donor () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment find_donor.
     */
    // TODO: Rename and change types and number of parameters
    public static Find_donor newInstance (String param1 , String param2) {
        Find_donor fragment = new Find_donor ();
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
        v = inflater.inflate (R.layout.fragment_find_donor , container , false);

        input_din      = v.findViewById (R.id.din);
        barcode_button = v.findViewById (R.id.barcode);
        barcode_button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View veiw) {

                if (checkPermission ()) {
                    //main logic or main code
                    Intent intent = new Intent (v.getContext () , Barcode.class);
                    startActivityForResult (intent , 14565);
                    // . write your main code to execute, It will execute if the permission is already given.

                } else {
                    requestPermission ();
                }


                //  startActivityForResult(intent,1);
            }
        });


        return v;

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

    @Override
    public void onActivityResult (int requestCode , int resultCode , Intent data) {
        super.onActivityResult (requestCode , resultCode , data);
        if (data != null)
            if (requestCode == 14565) {
                String text = data.getStringExtra ("result");
                input_din.setText (text);
            }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode , @NonNull String[] permissions , @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText (getActivity ().getApplicationContext () , "Permission Granted" , Toast.LENGTH_SHORT).show ();
                    // main logic
                } else {
                    Toast.makeText (getContext () , "Permission Denied" , Toast.LENGTH_SHORT).show ();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission (getContext () , Manifest.permission.CAMERA)
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
        new AlertDialog.Builder (getActivity ().getApplicationContext ())
                .setMessage (message)
                .setPositiveButton ("OK" , okListener)
                .setNegativeButton ("Cancel" , null)
                .create ()
                .show ();
    }

    private boolean checkPermission () {
        // Permission is not granted
        return ContextCompat.checkSelfPermission (getContext () , Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission () {

        ActivityCompat.requestPermissions (getActivity () ,
                new String[]{Manifest.permission.CAMERA} ,
                PERMISSION_REQUEST_CODE);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }
}
