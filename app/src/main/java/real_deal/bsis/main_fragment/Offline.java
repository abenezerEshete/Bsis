package real_deal.bsis.main_fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarRecord;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import real_deal.bsis.R;
import real_deal.bsis.config.DateformatControl;
import real_deal.bsis.config.Dialogs;
import real_deal.bsis.config.FormChoice;
import real_deal.bsis.config.HardCodedValue;
import real_deal.bsis.model.Address;
import real_deal.bsis.model.AddressType;
import real_deal.bsis.model.AdverseEvent;
import real_deal.bsis.model.AdverseEventType;
import real_deal.bsis.model.Contact;
import real_deal.bsis.model.ContactMethodType;
import real_deal.bsis.model.DeferralReason;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.DonationBatch;
import real_deal.bsis.model.DonationType;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;
import real_deal.bsis.model.LastUpdateChecked;
import real_deal.bsis.model.Location;
import real_deal.bsis.model.LoginUser;
import real_deal.bsis.model.PackType;
import real_deal.bsis.model.PreferredLanguage;
import real_deal.bsis.model.ResourceLocation;
import real_deal.bsis.model.SyncRequest;
import real_deal.bsis.offline.ApiInterface;
import real_deal.bsis.offline.JsonConstractor;
import real_deal.bsis.offline.RestPort;
import real_deal.bsis.offline.SyncPort;
import retrofit2.Call;
import retrofit2.Response;

import static java.lang.System.out;

public class Offline extends Fragment {
    TextView             syncCount;
    TextView             downloadInfo;
    Spinner              clearChoice;
    FrameLayout          frameLayout;
    Button               clear;
    Button               sync;
    Button               download;
    LinearLayout         linearLayout;
    View                 v;
    List < SyncRequest > syncRequests;
    Donor                donor;
    Donation             donation;
    SyncRequest          syncRequest;
    DonorDeferral        donorDeferral;
    LoginUser            loginUser;
    JsonConstractor      jsonConstractor = new JsonConstractor ();
    ApiInterface         apiInterface;
    RestPort             restPort;
    SyncPort             syncPort;
    ProgressDialog       progressDialog;
    FormChoice           formChoice;
    syncWait             syncwait;
    downloadWait         downloadwait;
    Date                 startDate                    = null;
    Date                 endDate                      = null;
    String               cancelledText                = "";
    int                  totalNumberOfDownloadedDonor = 0;
    private OnFragmentInteractionListener mListener;

    public Offline () {
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @SuppressLint ("ResourceType")
    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState) {
        v            = inflater.inflate (R.layout.fragment_offline , container , false);
        syncCount    = v.findViewById (R.id.offline_text);
        downloadInfo = v.findViewById (R.id.download_info);
        sync         = v.findViewById (R.id.sync);
        clearChoice  = v.findViewById (R.id.clear_choice);
        clear        = v.findViewById (R.id.clear_button);
        download     = v.findViewById (R.id.download);
        linearLayout = v.findViewById (R.id.offline_layout);
        frameLayout  = v.findViewById (R.id.offline_main_layout);


        fillValue ();

        sync.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                sync ();
            }
        });

        download.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                download ();
            }
        });

        clear.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (clearChoice.getSelectedItemPosition () == 0)
                    return;
                String choice = clearChoice.getSelectedItem ().toString ();
                if (choice.equals ("Clear donor(s) file ")) {
                    Donor.deleteAll (Donor.class);
                    Donation.deleteAll (Donation.class);
                    DonorDeferral.deleteAll (DonorDeferral.class);
                    Address.deleteAll (Address.class);
                    AddressType.deleteAll (AddressType.class);
                    DonationBatch.deleteAll (DonationBatch.class);
                    Location.deleteAll (Location.class);
                    Contact.deleteAll (Contact.class);
                    ContactMethodType.deleteAll (ContactMethodType.class);
                    DeferralReason.deleteAll (DeferralReason.class);//.findAll(DeferralReason.class);
                    LastUpdateChecked.deleteAll (LastUpdateChecked.class);//.findAll(DonationType.class);
                }
                else
                    SyncRequest.deleteAll (SyncRequest.class);
                fillValue ();
                Snackbar.make (frameLayout , "    Successfully Deleted   " , Snackbar.LENGTH_LONG).show ();
            }
        });

        out.println ("DonorDeferral=" + Select.from (DonorDeferral.class).count ());
    //    out.println ("DonorDeferral=" + Select.from (DonorDeferral.class).list ().get (0).getDeferredDonor ().getDonorNumber ());


//        out.println ("Donor1="+Select.from (Donation.class).count ());
//        out.println ("Donor2="+Select.from (Contact.class).count ());
//        out.println ("Donor3="+Select.from (Address.class).count ());
//        out.println ("Donor4="+Select.from (AddressType.class).count ());
//        out.println ("Donor5="+Select.from (ContactMethodType.class).count ());
//        out.println ("Donor6="+Select.from (DonorDeferral.class).count ());
//      List<DonationBatch>  dBatchs=   DonationBatch.find ( DonationBatch.class ,
//                " batch_number  = ? " , "002028");
//        if(!dBatchs.isEmpty ())
//            out.println ("---------------"+dBatchs.get (0).getBatchNumber ());

//

//      SugarRecord.update (new ResourceLocation ("http://192.168.0.108" , "8080"));





        return v;

    }

    private void fillValue () {
        syncRequests = Select.from (SyncRequest.class).list ();
        syncCount.setText (syncRequests.size () + " record are offline saved");


        LastUpdateChecked updateChecked = Select.from (LastUpdateChecked.class).first ();
        String text = "Last Downloaded date: ";
        if (updateChecked != null)
            text += updateChecked.getLastCheckedDate ();
        downloadInfo.setTextColor (getResources ().getColor (R.color.design_default_color_primary , null));
        setDownloadInfo (text);

        formChoice = new FormChoice ();

        ArrayAdapter < String > batchAddapter =
                new ArrayAdapter <> (getActivity () , R.layout.support_simple_spinner_dropdown_item , formChoice.clearChoiceArray);
        clearChoice.setAdapter (batchAddapter);

    }

    private void download () {

        if (!new RestPort (getActivity ()).networkCheck ())
            return;


        downloadwait = new downloadWait (this);
        downloadwait.execute ();

    }

    public void parseDownloadedformChoice (String s) {

        try {
            JSONObject jsonObject = new JSONObject (s);

            JSONArray adverseEventTypes = jsonObject.getJSONArray ("adverseEventTypes");
            for (int i = 0; i < adverseEventTypes.length (); i++) {

                if (downloadwait.isCancelled ()) {
                    break;
                }


                JSONObject adversEventT = adverseEventTypes.getJSONObject (i);
                AdverseEventType adverseEventType =
                        new AdverseEventType (adversEventT.optString ("id") ,
                                adversEventT.optString ("name") , adversEventT.optString ("description") ,
                                adversEventT.getBoolean ("isDeleted"));
                List < AdverseEventType > adverseEventTypesList =
                        AdverseEventType.find (AdverseEventType.class , "server_id = ?" , adversEventT.optString ("id"));

                if (!adverseEventTypesList.isEmpty ())
                    adverseEventType.setId (adverseEventTypesList.get (0).getId ());

                AdverseEventType.save (adverseEventType);

            }

//            PreferredLanguage.deleteAll (PreferredLanguage.class);
            JSONArray languages = jsonObject.getJSONArray ("languages");
            for (int i = 0; i < languages.length (); i++) {
                JSONObject lang = languages.getJSONObject (i);
                PreferredLanguage preferredLanguage = null;
                List < PreferredLanguage > preferredLanguageList =
                        PreferredLanguage.find (PreferredLanguage.class , "server_id = ? " , lang.optString ("id"));

                out.println ("lanuge=="+lang.optString ("preferredLanguage"));
                if (lang.optString ("preferredLanguage").equalsIgnoreCase ("English"))
                {
                    if (!preferredLanguageList.isEmpty ()) {
                        preferredLanguage = preferredLanguageList.get (0);
                    } else {
                        preferredLanguage = new PreferredLanguage ();
                    }

                preferredLanguage.setServerId (lang.optString ("id"));
                preferredLanguage.setPreferredLanguage (lang.optString ("preferredLanguage"));
                PreferredLanguage.save (preferredLanguage);
            }
            }


            List < Location > locationsToSave = new ArrayList <> ();
            JSONArray venues = jsonObject.getJSONArray ("venues");
            for (int i = 0; i < venues.length (); i++) {

                if (downloadwait.isCancelled ())
                    return;


                JSONObject venue = venues.getJSONObject (i);

                Location location;
                List < Location > locationList =
                        Location.find (Location.class , "server_id = ?" , venue.optString ("id"));
                if (!locationList.isEmpty ())
                    location = locationList.get (0);
                else
                    location = new Location ();

                location.setServerId (venue.optString ("id"));
                location.setName (venue.optString ("name"));
                location.setIsDeleted (venue.getBoolean ("isDeleted"));

                locationsToSave.add (location);
            }

            Location.saveInTx (locationsToSave);

            JSONArray packTypesArray = jsonObject.getJSONArray ("packTypes");
            for (int i = 0; i < packTypesArray.length (); i++) {
                JSONObject packTypejson = packTypesArray.getJSONObject (i);

                PackType pack;
                List < PackType > packTypeList =
                        PackType.find (PackType.class , "server_id = ?" , packTypejson.optString ("id"));
                if (!packTypeList.isEmpty ())
                    pack = packTypeList.get (0);
                else
                    pack = new PackType ();
                pack.setServerId (packTypejson.optString ("id"));
                pack.setPackType (packTypejson.optString ("packType"));
                pack.setIsDeleted (packTypejson.getBoolean ("isDeleted"));
                PackType.save (pack);

            }

            //   DonationBatch.deleteAll (DonationBatch.class);
            List < DonationBatch > donBatches =Select.from (DonationBatch.class).list ();
            for (int g = 0; g < donBatches.size (); g++) {
               DonationBatch db =   donBatches.get (g);
                db.setClosed (true);
                DonationBatch.update (db);
                out.println ("db id: "+db.getVenue ()+" isclosed: "+db.isClosed ());
            }

            List < DonationBatch > donationBatchesToBeSave = new ArrayList <> ();
            JSONArray donationBatches = jsonObject.getJSONArray ("donationBatches");
            for (int i = 0; i < donationBatches.length (); i++) {

                if (downloadwait.isCancelled ())
                    return;

                JSONObject batch = donationBatches.getJSONObject (i);
                JSONObject batchVenue = batch.getJSONObject ("venue");

                Location venue = null;
                if (batchVenue != null)
                    venue =
                            Location.find (Location.class , "server_id = ?" , batchVenue.optString ("id")).get (0);

                DonationBatch donationBatch = null;
                List < DonationBatch > donationBatcheslist =
                        DonationBatch.find (DonationBatch.class , "server_id = ?" , batch.optString ("id"));
                if (!donationBatcheslist.isEmpty ())
                {
                    donationBatch = donationBatcheslist.get (0);

                    if (batch.optBoolean ("isDeleted") == true) {
                           out.println ("isDeleted="+batch.getBoolean ("isDeleted")+"=real value=="+donationBatch.getServerId ());
                        DonationBatch.delete (donationBatch);
                        donationBatch = new DonationBatch ();
                    }
                }
                else
                    donationBatch = new DonationBatch ();

                donationBatch.setServerId (batch.optString ("id"));
                donationBatch.setBatchNumber (batch.optString ("batchNumber"));
                donationBatch.setVenue (venue);
                donationBatch.setClosed (batch.getBoolean ("isClosed"));
                donationBatch.setDeleted (batch.getBoolean ("isDeleted"));
                donationBatch.setDonationBatchDate (batch.optString ("donationBatchDate"));
                //    DonationBatch.save ( donationBatch );

                //  out.println ("bnum="+donationBatch.getBatchNumber ());
                donationBatchesToBeSave.add (donationBatch);
            }
            DonationBatch.saveInTx (donationBatchesToBeSave);


            JSONArray donationTypes = jsonObject.getJSONArray ("donationTypes");
            for (int i = 0; i < donationTypes.length (); i++) {
                JSONObject donationType = donationTypes.getJSONObject (i);
                DonationType donType = new DonationType (donationType.optString ("id") ,
                        donationType.optString ("type") ,
                        donationType.getBoolean ("isDeleted"));
                List < DonationType > donationTypeList =
                        DonationType.find (DonationType.class , "server_id = ?" , donationType.optString ("id"));
                if (!donationTypeList.isEmpty ())
                    donType.setId (donationTypeList.get (0).getId ());

                DonationType.save (donType);
            }


            JSONArray contactMethodsJson = jsonObject.getJSONArray ("preferredContactMethods");
            for (int i = 0; i < contactMethodsJson.length (); i++) {

                if (downloadwait.isCancelled ())
                    return;


                JSONObject contactMethod = contactMethodsJson.getJSONObject (i);
                ContactMethodType methodType =
                        new ContactMethodType (contactMethod.optString ("id") ,
                                contactMethod.optString ("contactMethodType") , contactMethod.optBoolean ("isDeleted"));
                List < ContactMethodType > contactMethodTypeList =
                        ContactMethodType.find (ContactMethodType.class ,
                                "server_id = ? " , contactMethod.optString ("id"));

                if (!contactMethodTypeList.isEmpty ())
                    methodType.setId (contactMethodTypeList.get (0).getId ());

                ContactMethodType.save (methodType);
            }


            JSONArray addresType = jsonObject.getJSONArray ("addressTypes");
            for (int i = 0; i < addresType.length (); i++) {

                if (downloadwait.isCancelled ())
                    return;


                JSONObject addressTypeJson = addresType.getJSONObject (i);
                AddressType addressType = new AddressType (addressTypeJson.optString ("id") ,
                        addressTypeJson.optString ("preferredAddressType"));
                List < AddressType > addressTypeList =
                        AddressType.find (AddressType.class , "server_id = ? " , addressTypeJson.optString ("id"));

                if (!addressTypeList.isEmpty ())
                    addressType.setId (addressTypeList.get (0).getId ());

                AddressType.save (addressType);
            }

            JSONArray deferralReasons = jsonObject.getJSONArray ("deferralReasons");
            for (int i = 0; i < deferralReasons.length (); i++) {

                if (downloadwait.isCancelled ())
                    return;


                JSONObject dreason = deferralReasons.getJSONObject (i);
                DeferralReason deferralReason =
                        new DeferralReason (dreason.optString ("id") , dreason.optString ("reason") ,
                                dreason.getBoolean ("isDeleted") , dreason.optString ("durationType"));
                List < DeferralReason > deferralReasonList =
                        DeferralReason.find (DeferralReason.class , "server_id = ? " , dreason.optString ("id"));

                if (!deferralReasonList.isEmpty ())
                    deferralReason.setId (deferralReasonList.get (0).getId ());

                DeferralReason.save (deferralReason);
            }

//            Toast.makeText ( getActivity () , "Successfully Downloaded Form Choices" , Toast.LENGTH_SHORT ).show ();
        } catch ( Exception e ) {
            e.printStackTrace ();
        }


    }

    public void parseDownloadedDonors (String s , Date endDate) {

        try {
            JSONObject jsonObject = new JSONObject (s);
            JSONArray donorJsonArray = jsonObject.optJSONArray ("donors");

            //    System .out.println ("in parsing donor result===");

            List < Donor > donorListToSave = new ArrayList <> ();
            List < Address > addressListToSave = new ArrayList <> ();
            List < Contact > contactListToSave = new ArrayList <> ();
            List < AddressType > addressTypeListToSave = new ArrayList <> ();
            List < ContactMethodType > contactMethodListToSave = new ArrayList <> ();
            List < Donation > donationListToSave = new ArrayList <> ();
            List < DonorDeferral > donorDeferralListToSave = new ArrayList <> ();
            List < AdverseEvent > adverseEventListToSave = new ArrayList <> ();

            System.out.println ("in parsing donor result===");

            //   err.println ("total donor added ="+donorJsonArray.length ());
            for (int i = 0; i < donorJsonArray.length (); i++) {

                JSONObject object = donorJsonArray.optJSONObject (i);

                JSONObject donorJson = object.optJSONObject ("donor").optJSONObject ("donor");
                //address save
                JSONObject addressJson = donorJson.optJSONObject ("address");
                Address address = null;
                if (addressJson != null) {
                    address =
                            new Address (addressJson.optString ("id") , addressJson.optString ("homeAddressLine1") , addressJson.optString ("homeAddressLine2") , addressJson.optString ("homeAddressCity") ,
                                    addressJson.optString ("homeAddressProvince") , addressJson.optString ("homeAddressDistrict") , addressJson.optString ("homeAddressCountry") , addressJson.optString ("homeAddressState") ,
                                    addressJson.optString ("homeAddressZipcode") , addressJson.optString ("workAddressLine1") , addressJson.optString ("workAddressLine2") , addressJson.optString ("workAddressCity") ,
                                    addressJson.optString ("workAddressProvince") , addressJson.optString ("workAddressDistrict") , addressJson.optString ("workAddressCountry") , addressJson.optString ("workAddressState") ,
                                    addressJson.optString ("workAddressZipcode") , addressJson.optString ("postalAddressLine1") , addressJson.optString ("postalAddressLine2") , addressJson.optString ("postalAddressCity") ,
                                    addressJson.optString ("postalAddressProvince") , addressJson.optString ("postalAddressDistrict") , addressJson.optString ("postalAddressCountry") , addressJson.optString ("postalAddressState") ,
                                    addressJson.optString ("postalAddressZipcode"));
//                    List <Address> addressList =
//                            Address.find ( Address.class , "server_id = ? " , addressJson.optString ( "id" ) );
//                    if (!addressList.isEmpty ())
//                        address.setId ( addressList.get ( 0 ).getId () );

                    addressListToSave.add (address);
                    //SugarRecord.save ( address );
                }

                JSONObject contactJson = donorJson.optJSONObject ("contact");
                Contact contact = null;
                if (contactJson != null) {
                    contact =
                            new Contact (contactJson.optString ("id") , contactJson.optString ("mobileNumber") ,
                                    contactJson.optString ("homeNumber") , contactJson.optString ("workNumber") ,
                                    contactJson.optString ("email"));
//                    List <Contact> contactList =
//                            Contact.find ( Contact.class , "server_id = ?" , contactJson.optString ( "id" ) );
//
//                    if (!contactList.isEmpty ())
//                        contact.setId ( contactList.get ( 0 ).getId () );
//
                    contactListToSave.add (contact);

                    // Contact.save ( contact );
                }

                JSONObject addressTypeJson = donorJson.optJSONObject ("preferredAddressType");
                AddressType addressType = null;
                if (addressTypeJson != null) {
                    addressType =
                            new AddressType (addressTypeJson.optString ("preferredAddressType") , addressTypeJson.optString ("id"));
//                    List <AddressType> addressTypeList =
//                            AddressType.find ( AddressType.class , "server_id = ?" , addressTypeJson.optString ( "id" ) );
//                    if (!addressTypeList.isEmpty ())
//                        addressType.setId ( addressTypeList.get ( 0 ).getId () );


                    addressTypeListToSave.add (addressType);
                    //  SugarRecord.save ( addressType );

                }

                JSONObject contactMethodJson = donorJson.optJSONObject ("contactMethodType");
                ContactMethodType contactMethodType = null;
                if (contactMethodJson != null) {
                    contactMethodType = new ContactMethodType (contactMethodJson.optString ("id") ,
                            contactMethodJson.optString ("contactMethodType") , contactMethodJson.optBoolean ("isDeleted")
                    );
//                    List <ContactMethodType> contactMethodTypeList =
//                            SugarRecord.find ( ContactMethodType.class , "server_id = ? " ,
//                                    contactMethodJson.optString ( "id" ) );
//                    if (!contactMethodTypeList.isEmpty ())
//                        contactMethodType.setId ( contactMethodTypeList.get ( 0 ).getId () );


                    contactMethodListToSave.add (contactMethodType);
                    // SugarRecord.save ( contactMethodType );

                }

                JSONObject donorVenueJson = donorJson.optJSONObject ("venue");
                Location donorVenue = null;
                if (donorVenueJson != null) {
                    List < Location > venues = SugarRecord.find (Location.class , "server_id=?" ,
                            donorVenueJson.optString ("id"));
                    if (!venues.isEmpty ())
                        donorVenue = venues.get (0);
                }

                JSONObject languageJson = donorJson.optJSONObject ("preferredLanguage");
                PreferredLanguage pLang = null;
                if (languageJson != null) {
                    List < PreferredLanguage > pL =
                            SugarRecord.find (PreferredLanguage.class , "server_id=?" ,
                                    languageJson.optString ("id"));
                    if (pL != null && !pL.isEmpty ())
                        pLang = pL.get (0);
                }
//.get (0);

              Donor donor = new Donor ();
//                List <Donor> donorList = Donor.find ( Donor.class , "server_id = ?" ,
//                        donorJson.optString ( "id" ) );
//                if (!donorList.isEmpty ())
//                    donor.setId ( donorList.get ( 0 ).getId ());
//                 else
//                    donor = new Donor ();





                donor.setServerId (donorJson.optString ("id"));
                donor.setDonorNumber (donorJson.optString ("donorNumber"));
                donor.setTitle (donorJson.optString ("title"));
                donor.setFirstName (donorJson.optString ("firstName"));
                donor.setMiddleName (donorJson.optString ("middleName"));
                donor.setLastName (donorJson.optString ("lastName"));
                donor.setCallingName (donorJson.optString ("callingName"));
                donor.setGender (donorJson.optString ("gender"));
                donor.setBirthDate (donorJson.optString ("birthDate"));
                donor.setAge (donorJson.optString ("age"));
                donor.setVenue (donorVenue);
                donor.setDateOfLastDonation (donorJson.optString ("dateOfLastDonation"));
                donor.setAddress (address);
                donor.setContact (contact);
                donor.setAddressType (addressType);
                donor.setContactMethodType (contactMethodType);
                donor.setDonorStatus (donorJson.optString ("donorStatus"));
                donor.setDateOfFirstDonation (donorJson.optString ("dateOfLastDonation"));
                donor.setPreferredLanguage (pLang);
                donor.setDueToDonate (object.optJSONObject ("overview").optString ("dueToDonate"));

                donorListToSave.add (donor);


                //for donation
                JSONObject donationsJsonObject = object.optJSONObject ("donations");
                JSONArray donationsJsonArray = donationsJsonObject.optJSONArray ("allDonations");
                if (donationsJsonArray != null && donationsJsonArray.length () != 0)
                    for (int j = 0; j < donationsJsonArray.length (); j++) {
                        JSONObject donation = donationsJsonArray.optJSONObject (j);


                        DonationBatch donationBatch = null;
                        List < DonationBatch > dBatchs =  DonationBatch.find (DonationBatch.class ,
                                " batch_number  = ? " , donation.optString ("donationBatchNumber"));
                        if (!dBatchs.isEmpty ()) {
                            donationBatch = dBatchs.get (0);
//                            for(int p =0 ; p< dBatchs.size () ; p++)
//                            {
//                                if(dBatchs.get (p).getVenue () != null)
//                                if(dBatchs.get (p).getVenue ().getServerId () == donation.optJSONObject ( "venue" ).optString ( "id" )) {
//                                    donationBatch = dBatchs.get (p);
//                                    p  = dBatchs.size ();
//                                }
//                            }
                        }

                        JSONObject donationVenue = donation.optJSONObject ("venue");
                        Location venue = null;
                        List < Location > venues =
                                SugarRecord.find (Location.class , "server_id=?" , donationVenue.optString ("id"));
                        if (!venues.isEmpty ())
                            venue = venues.get (0);

                        JSONObject donationPack = donation.optJSONObject ("packType");
                        PackType packType = null;
                        List < PackType > packTypeList =
                                SugarRecord.find (PackType.class , "server_id=?" , donationPack.optString ("id"));
                        if (!packTypeList.isEmpty ()) {
                            packType = packTypeList.get (0);
                        }

                        JSONObject donationtype = donation.optJSONObject ("donationType");
                        DonationType donType = null;
                        if (donationtype != null) {
                            List < DonationType > donationTypeList =
                                    SugarRecord.find (DonationType.class , "server_id=?" , donationtype.optString ("id"));
                            if (!donationTypeList.isEmpty ())
                                donType = donationTypeList.get (0);
                        }

                        JSONObject adverseEventJsonObject = donation.optJSONObject ("adverseEvent");
                        AdverseEvent adverseEvent = null;
                        if (adverseEventJsonObject != null) {
                            AdverseEventType adverseEventType =
                                    SugarRecord.find (AdverseEventType.class , "server_id =?" ,
                                            adverseEventJsonObject.getJSONObject ("type").optString ("id")).get (0);
                            adverseEvent =
                                    new AdverseEvent (adverseEventType , adverseEventJsonObject.optString ("comment") ,
                                            adverseEventJsonObject.optString ("id"));
//                            List <AdverseEvent> adverseEventList =
//                                    AdverseEvent.find ( AdverseEvent.class , "server_id = ? " , adverseEventJsonObject.optString ( "id" ) );
//                            if (!adverseEventList.isEmpty ())
//                                adverseEvent.setId ( adverseEventList.get ( 0 ).getId () );
                            adverseEventListToSave.add (adverseEvent);
                        }
                        Donation don = new Donation ();
//                        List <Donation> donationList =
//                                Donation.find ( Donation.class , "server_id = ?" ,
//                                        donation.optString ( "id" ) );
//                        if (!donationList.isEmpty ())
//                            don.setId ( donationList.get (0).getId ());


                        don.setServerId (donation.optString ("id"));
                        don.setDonationIdentificationNumber (donation.optString ("donationIdentificationNumber"));
                        don.setBloodPressureSystolic (donation.optString ("bloodPressureSystolic"));
                        don.setBloodPressureDiastolic (donation.optString ("bloodPressureDiastolic"));
                        don.setDonorPulse (donation.optString ("donorPulse"));
                        don.setDonorWeight (donation.optString ("donorWeight"));
                        don.setDonationDate (donation.optString ("donationDate"));
                        don.setBleedStartTime (donation.optString ("bleedStartTime"));
                        don.setHaemoglobinCount (donation.optString ("haemoglobinCount"));
                        don.setBleedEndTime (donation.optString ("bleedEndTime"));
                        don.setDonor (donor);
                        don.setDonationBatch (donationBatch);
                        don.setVenue (venue);
                        don.setPackType (packType);
                        don.setDonationType (donType);
                        don.setAdverseEvent (adverseEvent);


                        donationListToSave.add (don);

//                        if(donation.optString ("id").equals ("11ea8729-fbf4-9cbe-a894-d4bed949f2c8") ||
//                                donation.optString ("id").equals ("11ea8729-f58e-d4cd-a894-d4bed949f2c8") )
//                        {
//                            out.println ("donorgettted="+don.getServerId ());
//                            out.println ("donorgettted="+donor);
//                            out.println ("donorgettted="+don.getDonor ());
//                            out.println ("donorgettted="+don.getDonorWeight ());
//                            out.println ("don---"+donationListToSave.contains (don));
//                            downloadwait.cancel (true);
//                        }


                        //   SugarRecord.save ( don );
                    }

                //for Defferal
                JSONObject deferrals = object.optJSONObject ("deferrals");
                JSONArray deferralJsonArray = deferrals.optJSONArray ("allDonorDeferrals");
                if (deferralJsonArray != null && deferralJsonArray.length () != 0)
                    for (int j = 0; j < deferralJsonArray.length (); j++) {
                        JSONObject deferral = deferralJsonArray.optJSONObject (j);


                        JSONObject defferalVenue = deferral.optJSONObject ("venue");
                        Location venue = null;
                        List < Location > venues =
                                SugarRecord.find (Location.class , "server_id=?" , defferalVenue.optString ("id"));
                        if (!venues.isEmpty ())
                            venue = venues.get (0);


                        JSONObject defferalReason = deferral.optJSONObject ("deferralReason");
                        DeferralReason defReason = null;
                        List < DeferralReason > deferralsRes =
                                SugarRecord.find (DeferralReason.class , "server_id=?" ,
                                        defferalReason.optString ("id"));
                        if (!deferralsRes.isEmpty ())
                            defReason = deferralsRes.get (0);

                        DonorDeferral def =
                                new DonorDeferral (deferral.optString ("id") , deferral.optString ("deferralDate") ,
                                        defReason , donor , deferral.optString ("deferredUntil") ,
                                        venue , deferral.optString ("deferralReasonText"));
//
//                        List <DonorDeferral> donorDeferralList =
//                                DonorDeferral.find ( DonorDeferral.class , "server_id = ? " ,
//                                        deferral.optString ( "id" ) );
//                        if (!donorDeferralList.isEmpty ())
//                            def.setId ( donorDeferralList.get ( 0 ).getId () );


                        donorDeferralListToSave.add (def);
                        //SugarRecord.save ( def );

                    }


            }

            //   System .out.println ("in parsing donor result===");

            Address.saveInTx (addressListToSave);
            AddressType.saveInTx (addressTypeListToSave);
            Contact.saveInTx (contactListToSave);
            ContactMethodType.saveInTx (contactMethodListToSave);
            Donor.saveInTx (donorListToSave);
            AdverseEvent.saveInTx (adverseEventListToSave);
            DonorDeferral.saveInTx (donorDeferralListToSave);
            Donation.saveInTx (donationListToSave);


            if (downloadwait.isCancelled ())
                return;


            String updatedDate = new DateformatControl ().convertToshortDate (endDate);
            LastUpdateChecked.deleteAll (LastUpdateChecked.class);
            LastUpdateChecked updateChecked = new LastUpdateChecked (updatedDate);
            SugarRecord.save (updateChecked);
            totalNumberOfDownloadedDonor += donorJsonArray.length ();
            String text =
                    "Successfully downloaded. \n\n " + totalNumberOfDownloadedDonor + " Donor(s) files are Updated.\n\n Last downloaded date: " + updatedDate;
            setDownloadInfo(text);


        } catch ( Exception e ) {

            e.printStackTrace ();
            cancelledText =
                    "Error in parsing result: getMessage " + e.getMessage () + " return Json=" + s;
            progressDialog.dismiss ();
            downloadwait.cancel (true);
        }
    }

    private void sync () {

        if (Select.from (SyncRequest.class).first () == null)
            return;

        jsonConstractor = new JsonConstractor ();
        syncPort        = new SyncPort (getActivity ());
        if (!syncPort.networkCheck ())
            return;
        apiInterface = syncPort.retrofitsetup ();
        syncwait     = new syncWait ();
        syncwait.execute ();

  //      jsonConstractor = new JsonConstractor ();

//        List<SyncRequest> syncRequestList = Scelect.from (SyncRequest.class).list ();
//        String text="string text =="+syncRequestList.size ();
//
//        Donor dd = SugarRecord.findById (Donor.class, 322344);
//        if(dd != null)
//            text += "firstname: "+dd.getFirstName () +"  ";
//
//        for(int i=0;i<syncRequestList.size ();i++)
//        {
//
//            out.println (" in sync loop"+syncRequestList.get (i).getApiMethodName ());
//            text += syncRequestList.get (i).getApiMethodName ()+"  ";
//
//            if(Integer.parseInt (syncRequestList.get (i).getPayLoad ()) < 400000)
//            {
//
//                Donor donor = Donor.findById (Donor.class ,
//                        Long.parseLong (syncRequestList.get (i).getPayLoad ()));
//
//                if(donor != null)
//                        text +=i+" donor firstname:" +donor.getFirstName ()+" middleName: "+
//                                donor.getMiddleName ()+jsonConstractor.addDonor ( donor).toString ();
//
//            }
//            else{
//                Donation donation = Donation.findById
//                        (Donation.class , Long.parseLong (syncRequestList.get (i).getPayLoad ()));
//                if(donation != null)
//                    text += "DIN: "+donation.getDonationIdentificationNumber ();
//                 //   text +=i+"  " +jsonConstractor.addDonation ( donation);
//
//            }
//
//              text += " database id : "+syncRequestList.get (i).getPayLoad () +"\n";
//
//        }
//
//        out.println (text);
//        downloadInfo.setText (text);




    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
//        if ( progressDialog!=null && progressDialog.isShowing() ){
//            progressDialog.cancel();
//        }
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
    public void onResume () {
        super.onResume ();
//        if ( progressDialog!=null && progressDialog.isShowing() ){
//            progressDialog.dismiss ();
//        }
    }

    @Override
    public void onPause () {
        super.onPause ();
//        if(syncwait != null) {
//            syncwait.cancel (true);
//       }
//        if(downloadwait != null) {
//            downloadwait.cancel (true);
//        }
//        if ( progressDialog!=null && progressDialog.isShowing() ){
//            progressDialog.dismiss ();
//        }
//        Context context= Offline.this.getContext ();
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//
//        for (ActivityManager.RunningAppProcessInfo pid  : am.getRunningAppProcesses()) {
//            err.println ("proccess name"+pid.processName);
//            am.killBackgroundProcesses(pid.processName);
//        }
    }

    public void setDownloadInfo (final String text) {

        Handler handler = new Handler (Looper.getMainLooper ()) {
            @Override
            public void handleMessage (Message msg) {
                //super.handleMessage (msg);
                downloadInfo.setTextColor (getResources ().getColor (R.color.design_default_color_primary , null));
                downloadInfo.setText (text);
            }
        };
        handler.sendEmptyMessage (1);

    }

    private void downloadToasting (final String s) {


        if (getActivity () != null) {
            Handler handler = new Handler (Looper.getMainLooper ()) {

                @Override
                public void handleMessage (Message msg) {

                           downloadInfo.setTextColor (getResources ().getColor (R.color.red , null));
                    downloadInfo.setText (s);
                    Toast.makeText (getContext () , s , Toast.LENGTH_LONG).show ();


                    //should be Deleted
              //      downloadInfo.setText (downloadInfo.getText ().toString () + " addDeferarl: " + s);

                    out.println (s);

                }
            };
            handler.sendEmptyMessage (1);
        }

    }

    private void syncToasting (final String s) {
        Handler handler = new Handler (Looper.getMainLooper ()) {
            @Override
            public void handleMessage (Message msg) {

                if (s.equals ("Successfully Synced")) {
                    downloadInfo.setTextColor (getResources ().getColor (R.color.design_default_color_primary , null));
                    syncCount.setText ("Successfully Synced");

                } else {
                    syncCount.setTextColor (getResources ().getColor (R.color.red , null));
                    syncCount.setText (s);
                    Toast.makeText (getContext () , s , Toast.LENGTH_LONG).show ();
                }
            }
        };
        handler.sendEmptyMessage (1);
    }

    private void deleteDuplicate () {

        try {

            out.println ("deleteduplicateru===");

            SugarRecord.executeQuery ("delete from donor where  id  not in ( select max(id) from donor group by server_id )");
            SugarRecord.executeQuery ("delete from donation where  id not in ( select max(id) from donation group by server_id )");
            SugarRecord.executeQuery ("delete from donor_deferral where  id not  in ( select max(id) from donor_deferral group by server_id )");
            SugarRecord.executeQuery ("delete from address_type where  not id  in ( select max(id) from address_type group by server_id )");
            SugarRecord.executeQuery ("delete from address where  id not in ( select max(id) from address group by server_id ) ");
            SugarRecord.executeQuery ("delete from adverse_event where not id  in ( select max(id) from adverse_event group by server_id )");
            SugarRecord.executeQuery ("delete from Contact where  id not in ( select max(id) from Contact group by server_id )");
            SugarRecord.executeQuery ("delete from Contact_Method_Type where  id not in ( select max(id) from Contact_Method_Type group by server_id )");
            SugarRecord.executeQuery ("delete from location where  id not in ( select max(id) from location group by server_id )");
            out.println ("don===" + Select.from (Donation.class).count ());
            System.out.println ("thank----");
//
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }

    public class syncWait extends AsyncTask < Void, Void, Response > {

        ProgressDialog progressDialog;

        public syncWait () {


        }

        @Override
        protected void onPreExecute () {
            progressDialog = new ProgressDialog (getActivity () , R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate (true);
            progressDialog.setMessage ("Loading... \nplease wait");
            progressDialog.setCanceledOnTouchOutside (false);
            progressDialog.setIcon (R.drawable.loading);
            progressDialog.setOnDismissListener (new DialogInterface.OnDismissListener () {
                @Override
                public void onDismiss (DialogInterface dialog) {
                    syncwait.cancel (true);
                }
            });

            progressDialog.show ();
            super.onPreExecute ();
        }

        @Override
        protected Response doInBackground (Void... voids) {
            try {
                out.println ("in doing background");

                StrictMode.ThreadPolicy threadPolicy =
                        new StrictMode.ThreadPolicy.Builder ().permitAll ().build ();
                StrictMode.setThreadPolicy (threadPolicy);

                boolean error = false;
                for (int i = 0; i < syncRequests.size (); i++) {

                    if (syncwait.isCancelled ())
                        return null;
                    syncRequest = syncRequests.get (i);

                    System.err.println ("in forloop=" + syncRequest.getApiMethodName ());


                    if (syncRequest.getApiMethodName ().equals ("addDonor")) {
                        donor =
                                Donor.findById (Donor.class , Long.parseLong (syncRequest.getPayLoad ()));
                        if (donor != null && donor.getId () != null) {
                            final JSONObject donorJson = jsonConstractor.addDonor (donor);

                            out.println (donorJson);

                            Call call =
                                    apiInterface.addDonor (syncPort.authString () , "application/json" ,
                                            donorJson.toString ());
                            Response response = call.execute ();

                            try {

                                error = responseHandler (response , "addDonor");
                            }catch ( Exception e ){
                                e.printStackTrace ();
                            }
                        }
                    }
                    else if (syncRequest.getApiMethodName ().equals ("addDonation")) {
                        donation =
                                Donation.findById (Donation.class , Long.parseLong (syncRequest.getPayLoad ()));
                        if (donation != null && donation.getId () != null) {

                            final JSONObject donationjson =
                                    jsonConstractor.addDonation (donation);
                            out.println ("don==" + donation.getId ());
                            out.println ("json==" + donationjson);
                            Call < String > call =
                                    apiInterface.addDonation (syncPort.authString () , "application/json" ,
                                            donationjson.toString ());
                           try {
                               error = responseHandler (call.execute () , "addDonation");
                           }catch ( Exception e ){
                               e.printStackTrace ();
                           }
                        }
                    }
                    else if (syncRequest.getApiMethodName ().equals ("updateDonor")) {
                        System.err.println ("in update");
                        donor =
                                Donor.findById (Donor.class , Long.parseLong (syncRequest.getPayLoad ()));
                        if (donor != null && donor.getId () != null) {

                            out.println ("donor name=" + donor.getFirstName ());
                            final JSONObject donorJson = jsonConstractor.updateDonor (donor);
                            Call < String > call =
                                    apiInterface.donorsUpdate (syncPort.authString () , "application/json" ,
                                            donor.getServerId () , donorJson.toString ());
                           try {
                               Response response = call.execute ();
                               error = responseHandler (response , "updateDonor");
                           }catch ( Exception e ){
                               e.printStackTrace ();
                           }
                        }
                    }
                    else if (syncRequest.getApiMethodName ().equals ("addDeferral")) {
                        donorDeferral = DonorDeferral.findById (DonorDeferral.class , Long.parseLong (syncRequest.getPayLoad ()));
                        if (donorDeferral != null && donorDeferral.getId () != null) {
                            final JSONObject donorDefferalJson =
                                    jsonConstractor.donorDefferal (donorDeferral);
                            Call < String > call =
                                    apiInterface.addDefferal (syncPort.authString () , "application/json" ,
                                            donorDefferalJson.toString ());
                           try {
                               Response response = call.execute ();
                               error = responseHandler (response , "addDeferral");
                           }catch ( Exception e ){
                               e.printStackTrace ();
                           }
                        }
                    }
                    else if (syncRequest.getApiMethodName ().equals ("updateDeferral")) {
                        donorDeferral =DonorDeferral.findById (DonorDeferral.class , Long.parseLong (syncRequest.getPayLoad ()));
                        if (donorDeferral != null && donorDeferral.getId () != null) {
                            final JSONObject donorDefferalJson =
                                    jsonConstractor.donorDefferal (donorDeferral);
                            Call < String > call =
                                    apiInterface.deferralUpdate (syncPort.authString () , "application/json" ,
                                            donorDeferral.getServerId () , donorDefferalJson.toString ());
                           try {
                               Response response = call.execute ();
                               out.println ("second");
                               error = responseHandler (response , "addDeferral");
                           }catch ( Exception e ) {
                               e.printStackTrace ();
                           }
                        }
                    }
                    else if (syncRequest.getApiMethodName ().equals ("updateDonation")) {
                        donation =
                                Donation.findById (Donation.class , Long.parseLong (syncRequest.getPayLoad ()));
                        if (donation != null && donation.getId () != null) {
                            final JSONObject donationJson =
                                    jsonConstractor.addDonation (donation);
                            out.println ("in update donation --- =" + donationJson);
                            Call < String > call =
                                    apiInterface.donationUpdate (syncPort.authString () , "application/json" ,
                                            donation.getServerId () , donationJson.toString ());
                          try {
                              Response response = call.execute ();
                              error = responseHandler (response , "addDonation");
                          }catch ( Exception e ){
                              e.printStackTrace ();
                          }
                        }
                    }
                    else if (syncRequest.getApiMethodName ().equals ("setting")) {
                        loginUser =
                                LoginUser.findById (LoginUser.class , Long.parseLong (syncRequest.getPayLoad ()));
                        if (loginUser != null && loginUser.getId () != null) {
                            final JSONObject loginUserJson = jsonConstractor.setting (loginUser);
                            Call < String > call =
                                    apiInterface.setting (syncPort.authString () , "application/json" ,
                                            loginUserJson.toString ());
                          try {
                              Response response = call.execute ();
                              error = responseHandler (response , "setting");
                          }catch ( Exception e ){
                              e.printStackTrace ();
                          }
                        }
                    }


                }
                if (error) {
                    syncToasting ("Error occur while syncing donors file, please try again");
                } else {
                    syncToasting ("Successfully Synced");
                }

            } catch ( Exception e ) {
                e.printStackTrace ();

                syncToasting (e.getMessage ());
            }

            return null;
        }

        @Override
        protected void onPostExecute (Response response) {
            super.onPostExecute (response);


            progressDialog.dismiss ();

        }

        protected boolean responseHandler (Response response , String apiType) {
            try {
                out.println ("api type =" + apiType);
                if (response != null) {
                    if (response.isSuccessful ()) {

                        if (syncwait.isCancelled ())
                            return false;


                        JSONObject jsonObject = new JSONObject (response.body ().toString ());

                        if (apiType.equalsIgnoreCase ("addDonor")) {
                            donor.setCreatedFromMobile (false);
                            donor.setServerId (jsonObject.optJSONObject ("donor").optString ("id"));
                            donor.setDonorNumber (jsonObject.optJSONObject ("donor").optString ("donorNumber"));

                            Donor.save (donor);
                        } else if (apiType.equalsIgnoreCase ("addDonation")) {
                            JSONObject donationjsonObject =
                                    new JSONObject (response.body ().toString ());
                            donation.setServerId (donationjsonObject.optString ("donationId"));
                            donation.setCreatedFromMobile (false);
                            Donation.save (donation);
                        }
                        else if (apiType.equalsIgnoreCase ("updateDonor")) {
                            out.println ("how are you");
                            JSONObject dJson = jsonObject.optJSONObject ("donor");
                            donor.setDonorNumber (jsonObject.optJSONObject ("donor").optString ("donorNumber"));

                            Contact contact = donor.getContact ();
                            if (dJson.optJSONObject ("contact") != null)
                                if (contact != null)
                                    contact.setServerId (dJson.optJSONObject ("contact").optString ("id"));
                                else {
                                    contact = new Contact ();
                                    contact.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("contact").optString ("id"));
                                    Contact.save (contact);
                                    donor.setContact (contact);
                                }
                            Address address = donor.getAddress ();
                            if (dJson.optJSONObject ("address") != null)
                                if (address != null) {
                                    address.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("address").optString ("id"));
                                } else {
                                    address = new Address ();
                                    address.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("address").optString ("id"));
                                    Address.save (address);
                                    donor.setAddress (address);
                                }

                            AddressType addressType = donor.getAddressType ();
                            if (dJson.optJSONObject ("preferredAddressType") != null)
                                if (addressType != null) {
                                    addressType.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("preferredAddressType").optString ("id"));
                                } else {
                                    addressType = new AddressType ();
                                    addressType.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("preferredAddressType").optString ("id"));
                                    AddressType.save (addressType);
                                    donor.setAddressType (addressType);
                                }

                            ContactMethodType contactMethodType = donor.getContactMethodType ();
                            if (dJson.optJSONObject ("preferredAddressType") != null)
                                if (contactMethodType != null) {
                                    contactMethodType.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("preferredAddressType").optString ("id"));
                                } else {
                                    contactMethodType = new ContactMethodType ();
                                    contactMethodType.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("contactMethodType").optString ("id"));
                                    ContactMethodType.save (contactMethodType);
                                    donor.setContactMethodType (contactMethodType);
                                }

                            PreferredLanguage preferredLanguage = donor.getPreferredLanguage ();
                            if (dJson.optJSONObject ("preferredAddressType") != null)
                                if (preferredLanguage != null) {
                                    preferredLanguage.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("preferredAddressType").optString ("id"));
                                } else {
                                    preferredLanguage = new PreferredLanguage ();
                                    preferredLanguage.setServerId (jsonObject.optJSONObject ("donor").optJSONObject ("preferredLanguage").optString ("id"));
                                    PreferredLanguage.save (preferredLanguage);
                                    donor.setPreferredLanguage (preferredLanguage);
                                }


                            out.println ("Contact  ==" + contact.getServerId ());
                            out.println ("address  ==" + address.getServerId ());
                            Donor.save (donor);
                        }

                        else if (apiType.equalsIgnoreCase ("addDeferral")) {
                            donorDeferral.setServerId (jsonObject.optJSONObject ("deferral").optString ("id"));
                            donorDeferral.setCreatedFromMobile (false);
                            DonorDeferral.save (donorDeferral);
                        }
                        else if (apiType.equalsIgnoreCase ("setting")) {

                            out.println ("setting successfull " + jsonObject);
                            loginUser.setOldPassword (null);
                            LoginUser.deleteAll (LoginUser.class);
                            loginUser.setServerId (jsonObject.optString ("id"));
                            LoginUser.update (loginUser);
                        }

                        SyncRequest.delete (syncRequest);

                        return false;
                    } else
                        out.println ("response1====" + response.message ());
                    out.println ("response==3==" + response.errorBody ().source ().readUtf8 ());
                    out.println ("response===4=" + response.raw ().request ().url ());
                }
            } catch ( Exception e ) {
                e.printStackTrace ();
            }
            return true;
        }
    }

    public class downloadWait extends AsyncTask < Void, Void, Void > {

        Call    call;
        String  requestFrom;
        String  progress = "Download Starting...";
        Offline context;

        public downloadWait (Offline context) {
            this.context = context;
        }


        @Override
        protected void onPreExecute () {
            progressDialog = new ProgressDialog (getActivity () , R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate (true);
            progressDialog.setMessage (progress);
            progressDialog.setCanceledOnTouchOutside (false);
            progressDialog.setIcon (R.drawable.loading);
            progressDialog.setOnDismissListener (new DialogInterface.OnDismissListener () {
                @Override
                public void onDismiss (DialogInterface dialog) {

                    downloadwait.cancel (true);
                }
            });

            progressDialog.show ();
            super.onPreExecute ();

        }


        @Override
        protected Void doInBackground (Void... voids) {

            try {

                DateformatControl dateformatControl = new DateformatControl ();
                LastUpdateChecked updateChecked =Select.from (LastUpdateChecked.class).first ();
                endDate = new Date ();
                if (updateChecked == null)
                    startDate = dateformatControl.convertshortStringToDate ("2017-01-01");
                else
                    startDate =
                            dateformatControl.convertshortStringToDate (updateChecked.getLastCheckedDate ());

                long timeUnit = startDate.getTime () - endDate.getTime ();
                Long noOfDays = TimeUnit.DAYS.convert (timeUnit , TimeUnit.MILLISECONDS);


                // number of Months
                int s =
                        Integer.parseInt (noOfDays / new HardCodedValue ().NUMBER_OF_DAYS_FOR_CHUNK_DOWNLOAD + "");
                s = (s * (-1)) + 1;


                for (int i = 0; i <= s; i++) {

                    if (downloadwait.isCancelled ()) {
                        break;
                    }


                    if (i == 0) {
                        restPort =
                                new RestPort (getActivity () , downloadInfo , "Downloadformchoise");
                        if (!restPort.networkCheck ()) {
                            return null;
                        }
                        apiInterface = restPort.retrofitsetup ();
                        Call call = apiInterface.downloadformchoice (restPort.authString ());


                        Response response = call.execute ();

                        //Change progressBar text
                        progress = "Downloading Form Data.... Please wait...";
                        progressDialogChange (progress);
                        if (response.code () == 401) {
                           otherDialor ();
                            return null;
                        }
                        if (response.body () != null)
                            parseDownloadedformChoice (response.body ().toString ());

                        continue;
                    }


                    Calendar endDateCal = Calendar.getInstance ();
                    endDateCal.setTime (startDate);

                    endDateCal.add (Calendar.DATE , new HardCodedValue ().NUMBER_OF_DAYS_FOR_CHUNK_DOWNLOAD);
                    endDate = endDateCal.getTime ();


                    if (endDate.after (new Date ())) {
                        endDate = new Date ();
                    }


                    progress = " " + i + " Out of " + s;
                    progressDialogChange (progress);
                    restPort = new RestPort (getActivity ());
                    if (!restPort.networkCheck ())
                        return null;
                    apiInterface = restPort.retrofitsetup ();
                    Call call = apiInterface.downloadDonors (restPort.authString () ,
                            dateformatControl.convertToLongDate (startDate) + "Z" ,
                            dateformatControl.convertToLongDate (endDate) + "Z");


                    Response response = call.execute ();
                    if (response.isSuccessful ())
                        parseDownloadedDonors (response.body ().toString () , endDate);

                    startDate = endDate;
                }

                deleteDuplicate ();

            } catch ( SocketTimeoutException e ) {
                e.printStackTrace ();
                progressDialog.dismiss ();
                downloadToasting (e.getMessage ());
            }
            catch ( Exception e ) {
                e.printStackTrace ();
                progressDialog.dismiss ();

                //  downloadToasting("Error occur while Downloading donors file, please try again");
            }
            return null;
        }


        @Override
        protected void onPostExecute (Void aVoid) {
            super.onPostExecute (aVoid);
            progressDialog.dismiss ();
        }

        @Override
        protected void onProgressUpdate (Void... values) {
            super.onProgressUpdate (values);

        }

        public void progressDialogChange (final String text) {

            Handler handler = new Handler (Looper.getMainLooper ()) {
                @Override
                public void handleMessage (Message msg) {
                    //super.handleMessage (msg);
                    progressDialog.setMessage (text);
                }
            };
            handler.sendEmptyMessage (1);
        }
        public void otherDialor () {

            Handler handler = new Handler (Looper.getMainLooper ()) {
                @Override
                public void handleMessage (Message msg) {
                    //super.handleMessage (msg);
                    new Dialogs (getContext () , "Authorization failed, Check Username/password");
                }
            };
            handler.sendEmptyMessage (1);
        }

        @Override
        protected void onCancelled () {
            super.onCancelled ();

            if (progressDialog != null && progressDialog.getOwnerActivity () != null
                    && !progressDialog.getOwnerActivity ().isFinishing () && progressDialog.isShowing ())
                progressDialog.dismiss ();
            if (cancelledText != "") {

                if (context.isVisible ()) {
                    downloadInfo.setTextColor (getResources ().getColor (R.color.red , null));
                    downloadInfo.setText (cancelledText);

                }
            }
        }
    }
}
