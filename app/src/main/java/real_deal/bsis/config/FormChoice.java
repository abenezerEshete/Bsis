package real_deal.bsis.config;

import com.orm.query.Select;

import java.util.List;

import real_deal.bsis.model.AddressType;
import real_deal.bsis.model.AdverseEventType;
import real_deal.bsis.model.ContactMethodType;
import real_deal.bsis.model.DeferralReason;
import real_deal.bsis.model.DonationBatch;
import real_deal.bsis.model.DonationType;
import real_deal.bsis.model.Location;
import real_deal.bsis.model.PackType;
import real_deal.bsis.model.PreferredLanguage;


public class FormChoice {


    public String[] hbChoice ={"Label","PASS","FAIL"};

    public String[] clearChoiceArray    =
            {"Select here" , "Clear donor(s) file " , "clear Offline saved "};
    public String[] preferedContactType =
            {"Contact Type" , "None" , "phone" , "SMS" , "Email" , "Mail" , "Do not contact"};
    public String[] preferedAddressType =
            {"Address Type" , "Home Address" , "Postal Address" , "Work Address"};

    public String[] monthsArray         =
            {"Month" , "January" , "February" , "March" , "April" , "May" , "June" , "July" ,
                    "August" , "September" , "October" , "November" , "December"};
    public String[] titleArray          = {"Select Title" , "Mr" , "Mrs" , "Ms" , "Other"};
    public String[] genderArray         = {"Select Gender" , "Male" , "Female"};
    public String[] languageArray       = null;
    public String[] deferralReasonArray = null;
    public String[] donationBatchArray  = null;
    public String[] packArray           = null;
    //new String[]{"Pack","Single","Double","Triple","Quad","Apheresis","Dry Pack","Test Only","Did Not Bleed"};
    public String[] donationArray       = null;
    //new String[]{"Donation","Voluntary","Family","Autologous","Other"};
    public String[] adverEventArray     = null;
    //new String[]{"Adverse Effect","Accident","Allergy","Arterial puncture","Convulsions","Haematoma","Hyperventilation","Nausea",
    //  "Nerve injury","Thrombophlebitis","Vasovagal","Other"};
    public String[] time                = new String[]{"AM" , "PM"};


    /*
    "language", "English","Amharic","Tigrinya","French", "Portuguese","Spanish",
            "Arabic","Berber","Amharic","Tigrinya","Setswana","Comorian", "Chichewa","Kinyarwanda",
            "Kirundi","Sango","Swati","Malagasy","Seychellois","Mauritian","Shona","Sesotho","Afrikaans"
     */


    public String[] venueArray = null;

    List < PreferredLanguage > preferredLanguages;
    List < Location >          locations;
    List < DonationBatch >     donationBatches;
    List < DeferralReason >    deferralReasons;
    List < PackType >          packTypes;
    List < AdverseEventType >  adverseEventTypes;
    List < DonationType >      donationTypes;
    List < ContactMethodType > contactMethodTypes;
    List < AddressType >       addressTypes;

    public String[] languageChoice () {
        try {
            preferredLanguages = PreferredLanguage.listAll (PreferredLanguage.class);
            languageArray      = new String[ preferredLanguages.size () + 1 ];
            languageArray[ 0 ] = "";
            for (int i = 0; i < preferredLanguages.size (); i++) {
                languageArray[ i + 1 ] = preferredLanguages.get (i).getPreferredLanguage ();

            }

            return languageArray;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return new String[]{};
    }

    public String[] venueChoice () {
        //venue choice list
        try {

            locations       =
                    Select.from (Location.class).where (" is_deleted = '0'  ").list ();//and is_venue  = '1';Location.find ( Location.class , "is_deleted = false"  );
            venueArray      = new String[ locations.size () + 1 ];
            venueArray[ 0 ] = "Select Venue";
            for (int i = 0; i < locations.size (); i++) {
                if (locations.get (i).getIsDeleted () == false)
                    venueArray[ i + 1 ] = locations.get (i).getName ();
            }
            return venueArray;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ("not work Venue");
        return new String[]{};
    }

    public String[] addressTypechoice () {
        //venue choice list
        try {
            addressTypes             = Select.from (AddressType.class).list ();
            preferedAddressType      = new String[ addressTypes.size () + 1 ];
            preferedAddressType[ 0 ] = "Select Preferred Address Type";
            for (int i = 0; i < addressTypes.size (); i++) {
                preferedAddressType[ i + 1 ] = addressTypes.get (i).getPreferredAddressType ();
            }
            return preferedAddressType;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ("not work addressTypeChoise");
        return new String[]{};
    }

    public String[] contactMethodchoice () {
        try {
            //venue choice list
            contactMethodTypes       =
                    ContactMethodType.find (ContactMethodType.class , "is_deleted = ?" , "0");
            preferedContactType      = new String[ contactMethodTypes.size () + 1 ];
            preferedContactType[ 0 ] = "Select Preferred Contact Type";
            for (int i = 0; i < contactMethodTypes.size (); i++) {
                if (contactMethodTypes.get (i).isDeleted () == false)
                    preferedContactType[ i + 1 ] =
                            contactMethodTypes.get (i).getContactMethodType ();
            }
            return preferedContactType;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ("not work contactmethodschoice");
        return new String[]{};
    }

    public String[] donationBatchChoice () {

        try {
            donationBatches         =
                    DonationBatch.find (DonationBatch.class , "is_closed = ?  and is_deleted = ?" , "0" , "0");
            donationBatchArray      = new String[ donationBatches.size () + 1 ];
            donationBatchArray[ 0 ] = "Select Donation Batch";
            for (int i = 0; i < donationBatches.size (); i++) {
                if (donationBatches.get (0).isClosed () == false)
                    donationBatchArray[ i + 1 ] = donationBatches.get (i).getVenue ().getName ();
            }
            return donationBatchArray;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ("not work donationbatch");
        return new String[]{};
    }

    public String[] packTypeChoice () {
        try {
            packTypes      = PackType.find (PackType.class , "is_deleted = ?" , "0");
            packArray      = new String[ packTypes.size () + 1 ];
            packArray[ 0 ] = "Select Pack";
            for (int i = 0; i < packTypes.size (); i++) {
                if (packTypes.get (i).getIsDeleted () == false)
                    packArray[ i + 1 ] = packTypes.get (i).getPackType ();
            }
            return packArray;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ("not work packtype");
        return new String[]{};
    }

    public String[] advertEventTypeChoice () {
        try {
            adverseEventTypes    =
                    AdverseEventType.find (AdverseEventType.class , "is_deleted = ?" , "0");
            adverEventArray      = new String[ adverseEventTypes.size () + 1 ];
            adverEventArray[ 0 ] = "Select Adverse Event";
            for (int i = 0; i < adverseEventTypes.size (); i++) {
                if (adverseEventTypes.get (i).isDeleted () == false)
                    adverEventArray[ i + 1 ] = adverseEventTypes.get (i).getName ();
            }
            return adverEventArray;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ("not work adverttypechoice");
        return new String[]{};
    }

    public String[] donationTypeChoice () {
        try {
            donationTypes      = DonationType.find (DonationType.class , "is_deleted = ?" , "0");
            donationArray      = new String[ donationTypes.size () + 1 ];
            donationArray[ 0 ] = "Select Donation";
            for (int i = 0; i < donationTypes.size (); i++) {
                if (donationTypes.get (i).getIsDeleted () == false)
                    donationArray[ i + 1 ] = donationTypes.get (i).getDonationType ();
            }
            return donationArray;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ("not work donationType");
        return new String[]{};
    }

    public String[] deferralReasonChoice () {
        try {
            deferralReasons          = Select.from (DeferralReason.class).list ();
            deferralReasonArray      = new String[ deferralReasons.size () + 1 ];
            deferralReasonArray[ 0 ] = "Select Reason";
            for (int i = 0; i < deferralReasons.size (); i++) {
                if (deferralReasons.get (i).getIsDeleted () == false)
                    deferralReasonArray[ i + 1 ] = deferralReasons.get (i).getReason ();
            }
            return deferralReasonArray;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ("not work DeferralReason");
        return new String[]{};
    }


    //get Donation Batch object
    public DonationBatch getDonationBatchObject (int pos) {
        if (pos > 0)
            return donationBatches.get (pos - 1);
        return donationBatches.get (pos);
    }

    //get DonationType object
    public PackType getPackTypeObject (int pos) {
        if (pos > 0)
            return packTypes.get (pos - 1);
        return packTypes.get (pos);
    }

    //get DonationType object
    public DonationType getDonationTypeObject (int pos) {
        if (pos > 0)
            return donationTypes.get (pos - 1);
        return donationTypes.get (pos);
    }

    //get DeferralReason object
    public DeferralReason getDeferralReasoneObject (int pos) {
        if (pos > 0)
            return deferralReasons.get (pos - 1);
        return deferralReasons.get (pos);
    }


    //time AM and PM setter
    public int timecheck (String ampm) {

        if (ampm.equalsIgnoreCase ("pm") || ampm.equalsIgnoreCase ("p.m."))
            return 1;
        System.out.println ("timeCheck====" + time[ 0 ] + time[ 1 ] + "=======" + ampm);
        return 0;
    }

    //get AdverseEvent type  object
    public AdverseEventType getAdverseEventObject (int pos) {
        if (pos > 0)
            return adverseEventTypes.get (pos - 1);
        return adverseEventTypes.get (pos);
    }

    //title string change to index number
    public int titlecheck (String title) {
        int i = 0;
        for (i = 0; i < titleArray.length; i++) {
            if (titleArray[ i ].equalsIgnoreCase (title))
                break;
        }
        return i;
    }

    //title string change to index number
    public int deferralReasoncheck (String reason) {
        int i = 0;
        for (i = 0; i < deferralReasonArray.length; i++) {
            if (deferralReasonArray[ i ].equalsIgnoreCase (reason))
                break;
        }
        return i;
    }

    //prefered contact string change to index number
    public int preferedContactTypeCheck (String prefContact) {
        int i = 0;
        try {
            for (i = 0; i < preferedContactType.length; i++) {
                if (preferedContactType[ i ].equalsIgnoreCase (prefContact))
                    break;
            }
        } catch ( Exception e ) {

        }
        return i;
    }

    //prefered address string change to index number
    public int prefereAddresstTypeCheck (String prefAddredd) {
        int i = 0;
        try {
            for (i = 0; i < preferedAddressType.length; i++) {
                if (preferedAddressType[ i ].equalsIgnoreCase (prefAddredd))
                    break;
            }
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return i;
    }

    //title string change to index number
    public int donorDeferralcheck (String deferral) {
        int i = 0;
        for (i = 0; i < deferralReasonArray.length; i++) {
            if (deferralReasonArray[ i ].equalsIgnoreCase (deferral))
                break;
        }
        return i;
    }

    //pack string change to index number
    public int packcheck (String pack) {
        int i = 0;
        for (i = 0; i < packArray.length; i++) {
            if (packArray[ i ].equalsIgnoreCase (pack))
                break;
        }
        return i;
    }

    //AdverseEvent string change to index number
    public int adverseEventcheck (String pack) {
        int i = 0;
        for (i = 0; i < adverEventArray.length; i++) {
            if (adverEventArray[ i ].equalsIgnoreCase (pack))
                break;
        }
        return i;
    }

    //Gender string change to index number
    public int genderCheck (String gender) {
        int i = 0;
        if (gender != null && !gender.isEmpty ())
            for (i = 0; i < genderArray.length; i++) {
                if (genderArray[ i ].equalsIgnoreCase (gender))
                    break;
            }
        return i;
    }

    //language string change to index number
    public int languageCheck (String language) {
        int i = 0;
        if (language != null && !language.isEmpty ())
            for (i = 0; i < languageArray.length; i++) {
                if (languageArray[ i ].equalsIgnoreCase (language))
                    break;
            }
        return i;
    }

    public int venueCheck (String venue) {
        int i = 0;
        if (venue != null && !venue.isEmpty ())
            for (i = 0; i < venueArray.length; i++) {
                if (venueArray[ i ].equalsIgnoreCase (venue))
                    break;
            }
        return i;
    }

    //get prefered language object
    public PreferredLanguage getlanguageObject (int pos) {
        if (pos > 0)
            return preferredLanguages.get (pos - 1);

        return preferredLanguages.get (pos);
    }

    //get prefered contact object
    public ContactMethodType preferedContactmethod (int pos) {
        if (pos > 0)
            return contactMethodTypes.get (pos - 1);

        return contactMethodTypes.get (pos);
    }

    //get prefered address object
    public AddressType preferedAddressType (int pos) {
        if (pos > 0)
            return addressTypes.get (pos - 1);

        return addressTypes.get (pos);
    }

    //get prefered language object
    public Location getVenueObject (int pos) {
        if (pos > 0)
            return locations.get (pos - 1);
        return locations.get (pos);
    }

    public Location getVenueObject (String name) {

        for (int i = 0; i < locations.size (); i++) {
            if (locations.get (i).getIsDeleted () == false
                    && locations.get (i).getName ().equalsIgnoreCase (name)) {
                return locations.get (i);
            }
        }
        return null;
    }

}
