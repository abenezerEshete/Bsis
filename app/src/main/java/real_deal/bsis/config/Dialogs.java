package real_deal.bsis.config;

import android.app.AlertDialog;
import android.content.Context;

public class Dialogs {
    public Dialogs (Context context , String message) {

        new AlertDialog.Builder (context)
                .setMessage (message)
                .setPositiveButton ("OK" , null)
                //.setNegativeButton("Cancel", null)
                .create ()
                .show ();
    }
}
