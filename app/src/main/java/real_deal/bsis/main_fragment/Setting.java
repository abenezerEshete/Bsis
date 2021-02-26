package real_deal.bsis.main_fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orm.query.Select;

import real_deal.bsis.R;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.model.LoginUser;
import real_deal.bsis.model.SyncRequest;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Setting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView     firstName;
    TextView     lastName;
    TextView     email;
    TextView     userName;
    TextView     newPassword;
    TextView     oldPassword;
    TextView     confirmPassword;
    Button       saveChange;
    CheckBox     modifyPassword;
    LinearLayout linearLayout;
    LoginUser    loginUser;
    // TODO: Rename and change types of parameters
    private String                        mParam1;
    private String                        mParam2;
    private OnFragmentInteractionListener mListener;

    public Setting () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance (String param1 , String param2) {
        Setting fragment = new Setting ();
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
        View v = inflater.inflate (R.layout.fragment_setting , container , false);

        firstName       = ( EditText ) v.findViewById (R.id.input_firstname);
        lastName        = ( EditText ) v.findViewById (R.id.input_lastname);
        email           = ( EditText ) v.findViewById (R.id.input_email);
        userName        = ( EditText ) v.findViewById (R.id.input_username);
        newPassword     = ( EditText ) v.findViewById (R.id.input_new_password);
        oldPassword     = ( EditText ) v.findViewById (R.id.input_old_password);
        confirmPassword = ( EditText ) v.findViewById (R.id.input_confirm_password);
        saveChange      = v.findViewById (R.id.btn_save_change);
        modifyPassword  = v.findViewById (R.id.modify_password);
        linearLayout    = v.findViewById (R.id.main_layout);

        modifyPassword.setChecked (false);
        oldPassword.setEnabled (false);
        newPassword.setEnabled (false);
        confirmPassword.setEnabled (false);

        loginUser = Select.from (LoginUser.class).first ();

        System.out.println ("count" + loginUser.getFirsname ());
        System.out.println ("logined count==" + LoginUser.count (LoginUser.class));
        System.out.println ("logined lastname" + loginUser.getLastname ());
        System.out.println ("logined old==" + loginUser.getOldPassword ());
        System.out.println ("logined pass==" + loginUser.getPassword ());
        System.out.println ("logined new==" + loginUser.getNewPassword ());
        System.out.println ("logined id" + loginUser.getServerId ());
        System.out.println ("logined id" + loginUser.getId ());
        firstName.setText (loginUser.getFirsname ());
        lastName.setText (loginUser.getLastname ());
        userName.setText (loginUser.getUsername ());
        email.setText (loginUser.getEmail ());

        modifyPassword.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton buttonView , boolean isChecked) {

                if (oldPassword.isEnabled ()) {
                    oldPassword.setEnabled (false);
                    newPassword.setEnabled (false);
                    confirmPassword.setEnabled (false);
                } else {
                    oldPassword.setEnabled (true);
                    newPassword.setEnabled (true);
                    confirmPassword.setEnabled (true);
                    oldPassword.setError (null);
                    newPassword.setError (null);
                    confirmPassword.setError (null);
                }

            }
        });

        saveChange.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (Validation ()) {

                    System.out.println ("validated");
                    try {

                        LoginUser user =loginUser;


                        user.setFirsname (firstName.getText ().toString ());
                        user.setLastname (lastName.getText ().toString ());
                        user.setEmail (email.getText ().toString ());

                        if (modifyPassword.isChecked ()) {
                            System.out.println ("medifiy password enabled=="+modifyPassword.isSelected ());
                            user.setOldPassword (oldPassword.getText ().toString ());
                            user.setPassword (newPassword.getText ().toString ());
                            user.setNewPassword (newPassword.getText ().toString ());
                        }

                        user.setId (loginUser.getId ());
                        user.setUsername (loginUser.getUsername ());



                   //     LoginUser.deleteAll (LoginUser.class);
                        LoginUser.save (user);

                        SyncRequest.save (new SyncRequest ("setting" , "PUT" , Select.from (LoginUser.class).first ().getId ().toString ()));

                        Snackbar.make (linearLayout , "       Successfully Updated       " , Snackbar.LENGTH_LONG).show ();

                    } catch ( Exception e ) {
                        e.printStackTrace ();
                    }
                }
            }
        });


        return v;
    }

    public boolean Validation () {
        System.out.println ("validating");
        String errorMessage = new HardCodedValue ().EMPTY_MESSAGE;
        if (firstName.getText ().toString ().isEmpty () &&
                lastName.getText ().toString ().isEmpty () &&
                email.getText ().toString ().isEmpty () &&
                modifyPassword.isChecked () &&
                newPassword.getText ().toString ().isEmpty () &&
                oldPassword.getText ().toString ().isEmpty () &&
                confirmPassword.getText ().toString ().isEmpty ()) {
            firstName.setError (errorMessage);
            lastName.setError (errorMessage);
            email.setError (errorMessage);
            newPassword.setError (errorMessage);
            oldPassword.setError (errorMessage);
            confirmPassword.setError (errorMessage);

            System.out.println ("validating  1");
            return false;
        } else if (firstName.getText ().toString ().isEmpty () &&
                lastName.getText ().toString ().isEmpty () &&
                email.getText ().toString ().isEmpty () &&
                !modifyPassword.isSelected ()) {
            firstName.setError (errorMessage);
            lastName.setError (errorMessage);
            email.setError (errorMessage);
            System.out.println ("validating  2");
            return false;
        } else if (modifyPassword.isSelected () &&
                newPassword.getText ().toString ().isEmpty () &&
                oldPassword.getText ().toString ().isEmpty () &&
                confirmPassword.getText ().toString ().isEmpty ()) {
            newPassword.setError (errorMessage);
            oldPassword.setError (errorMessage);
            confirmPassword.setError (errorMessage);
            System.out.println ("validating  3");
            return false;
        } else if (firstName.getText ().toString ().isEmpty ()) {
            firstName.setError (errorMessage);
            System.out.println ("validating  4");
            return false;
        } else if (lastName.getText ().toString ().isEmpty ()) {
            lastName.setError (errorMessage);
            System.out.println ("validating  5");
            return false;
        } else if (email.getText ().toString ().isEmpty ()) {
            email.setError (errorMessage);
            System.out.println ("validating  6");
            return false;
        } else if (modifyPassword.isChecked () && newPassword.getText ().toString ().isEmpty ()) {
            newPassword.setError (errorMessage);
            System.out.println ("validating  7");
            return false;
        } else if (modifyPassword.isChecked () && oldPassword.getText ().toString ().isEmpty ()) {
            oldPassword.setError (errorMessage);
            System.out.println ("validating  8");
            return false;
        } else if (modifyPassword.isChecked () && confirmPassword.getText ().toString ().isEmpty ()) {
            confirmPassword.setError (errorMessage);
            System.out.println ("validating  9");
            return false;
        } else if (modifyPassword.isChecked () && !oldPassword.getText ().toString ().equals (loginUser.getPassword ())) {
            oldPassword.setError ("Password Incorrect");
            System.out.println ("validating  10");
            return false;
        } else if (modifyPassword.isChecked () && !newPassword.getText ().toString ().equals (confirmPassword.getText ().toString ())) {
            oldPassword.setError ("Password is not the same");
            confirmPassword.setError ("Password is not the same");
            System.out.println ("validating  11");
            return false;
        }
        firstName.setError (null);
        lastName.setError (null);
        userName.setError (null);
        newPassword.setError (null);
        oldPassword.setError (null);
        confirmPassword.setError (null);
        return true;
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
