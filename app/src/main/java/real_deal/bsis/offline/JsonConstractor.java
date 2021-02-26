package real_deal.bsis.offline;

import org.json.JSONArray;
import org.json.JSONObject;

import real_deal.bsis.model.Address;
import real_deal.bsis.model.AddressType;
import real_deal.bsis.model.AdverseEvent;
import real_deal.bsis.model.Contact;
import real_deal.bsis.model.ContactMethodType;
import real_deal.bsis.model.DeferralReason;
import real_deal.bsis.model.Donation;
import real_deal.bsis.model.DonationBatch;
import real_deal.bsis.model.DonationType;
import real_deal.bsis.model.Donor;
import real_deal.bsis.model.DonorDeferral;
import real_deal.bsis.model.Location;
import real_deal.bsis.model.LoginUser;
import real_deal.bsis.model.PackType;
import real_deal.bsis.model.PreferredLanguage;

public class JsonConstractor {

    Donor    donor;
    Donation donation;


    public JSONObject preferedLanguage (PreferredLanguage preferredLanguage) {

        try {
            if (preferredLanguage == null)
                return null;
            JSONObject jsonObject = new JSONObject ();
            jsonObject.put ("id" , preferredLanguage.getServerId ());
            return jsonObject;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;
    }

    public JsonConstractor () {
    }

    public JSONObject venue (Location venue) {

        try {
            if (venue == null)
                return null;
            JSONObject jsonObject = new JSONObject ();
            jsonObject.put ("id" , venue.getServerId ());
            return jsonObject;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;
    }

    public JSONObject donationBatch (DonationBatch donationBatch) {
        try {
            if (donationBatch == null)
                return null;
            JSONObject jsonObject = new JSONObject ();
            jsonObject.put ("id" , donationBatch.getBatchNumber ());
            return jsonObject;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;

    }

    public JSONObject adverseEvent (AdverseEvent adverseEvent) {
        try {
            if (adverseEvent == null)
                return null;
            JSONObject jsonObject = new JSONObject ();
            jsonObject.put ("id" , adverseEvent.getServerId ());
            jsonObject.put ("type" , new JSONObject ().put ("id" , adverseEvent.getType ().getServerId ()));
            jsonObject.put ("comment" , adverseEvent.getComment ());
            return jsonObject;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;

    }

    public JSONObject donationType (DonationType donationType) {
        try {
            if (donationType == null)
                return null;
            JSONObject jsonObject = new JSONObject ();
            jsonObject.put ("id" , donationType.getServerId ());
            return jsonObject;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;

    }

    public JSONObject packType (PackType packType) {
        try {
            if (packType == null)
                return null;
            JSONObject jsonObject = new JSONObject ();
            jsonObject.put ("id" , packType.getServerId ());
            return jsonObject;
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;

    }

    public JSONObject address (Address address) {

        try {
            if (address == null)
                return null;
            JSONObject jsonObject = new JSONObject ();


            jsonObject.put ("id" , address.getServerId ());
            jsonObject.put ("homeAddressLine1" , address.getHomeAddressLine1 ());
            jsonObject.put ("homeAddressLine2" , address.getHomeAddressLine2 ());
            jsonObject.put ("homeAddressCity" , address.getHomeAddressCity ());
            jsonObject.put ("homeAddressProvince" , address.getHomeAddressProvince ());
            jsonObject.put ("homeAddressDistrict" , address.getHomeAddressDistrict ());
            jsonObject.put ("homeAddressCountry" , address.getHomeAddressCountry ());
            jsonObject.put ("homeAddressState" , address.getHomeAddressState ());
            jsonObject.put ("homeAddressZipcode" , address.getHomeAddressZipcode ());
            jsonObject.put ("workAddressLine1" , address.getWorkAddressLine1 ());
            jsonObject.put ("workAddressLine2" , address.getWorkAddressLine2 ());
            jsonObject.put ("workAddressCity" , address.getWorkAddressCity ());
            jsonObject.put ("workAddressProvince" , address.getWorkAddressProvince ());
            jsonObject.put ("workAddressDistrict" , address.getWorkAddressDistrict ());
            jsonObject.put ("workAddressCountry" , address.getWorkAddressCountry ());
            jsonObject.put ("workAddressState" , address.getWorkAddressState ());
            jsonObject.put ("workAddressZipcode" , address.getWorkAddressZipcode ());
            jsonObject.put ("postalAddressLine1" , address.getPostalAddressLine1 ());
            jsonObject.put ("postalAddressLine2" , address.getPostalAddressLine2 ());
            jsonObject.put ("postalAddressCity" , address.getPostalAddressCity ());
            jsonObject.put ("postalAddressProvince" , address.getPostalAddressProvince ());
            jsonObject.put ("postalAddressDistrict" , address.getPostalAddressDistrict ());
            jsonObject.put ("postalAddressCountry" , address.getPostalAddressCountry ());
            jsonObject.put ("postalAddressState" , address.getPostalAddressState ());
            jsonObject.put ("postalAddressZipcode" , address.getPostalAddressZipcode ());

            return jsonObject;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return null;
    }

    public JSONObject contact (Contact contact) {

        try {
            if (contact == null)
                return null;
            JSONObject jsonObject = new JSONObject ();

            jsonObject.put ("id" , contact.getServerId ());
            jsonObject.put ("mobileNumber" , contact.getMobileNumber ());
            jsonObject.put ("homeNumber" , contact.getHomeNumber ());
            jsonObject.put ("workNumber" , contact.getWorkNumber ());
            jsonObject.put ("email" , contact.getEmail ());

            return jsonObject;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return null;
    }

    public JSONObject contactMethod (ContactMethodType methodType) {

        try {
            if (methodType == null)
                return null;
            JSONObject jsonObject = new JSONObject ();

            jsonObject.put ("id" , methodType.getServerId ());
            jsonObject.put ("contactMethodType" , methodType.getContactMethodType ());

            return jsonObject;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return null;
    }

    public JSONObject preferredAddressType (AddressType addressType) {

        try {
            if (addressType == null)
                return null;
            JSONObject jsonObject = new JSONObject ();

            jsonObject.put ("id" , addressType.getServerId ());
            jsonObject.put ("preferredAddressType" , addressType.getPreferredAddressType ());

            return jsonObject;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return null;
    }

    public JSONObject deferralReason (DeferralReason deferralReason) {

        try {
            if (deferralReason == null)
                return null;
            JSONObject jsonObject = new JSONObject ();

            jsonObject.put ("id" , deferralReason.getServerId ());
            jsonObject.put ("reason" , deferralReason.getReason ());

            return jsonObject;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return null;
    }

    public JSONObject donor (Donor donor) {

        try {
            JSONObject jsonObject = new JSONObject ();
            if (donor == null)
                return jsonObject;

            jsonObject.put ("id" , donor.getServerId ());

            return jsonObject;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        return null;
    }

    public JSONObject addDonor (Donor donor) {

        try {
            System.out.println ("doorrrrr="+donor);
            final JSONObject donorJson = new JSONObject ();
            if(donor == null) {
                return donorJson;
            }
            donorJson.put ("firstName" , donor.getFirstName ());
            donorJson.put ("middleName" , donor.getMiddleName ());
            donorJson.put ("lastName" , donor.getLastName ());
            donorJson.put ("title" , donor.getTitle ());
            donorJson.put ("gender" , donor.getGender ().toLowerCase ());
            donorJson.put ("callingName" , donor.getCallingName ());
            donorJson.put ("birthDate" , donor.getBirthDate ());
            donorJson.put ("preferredLanguage" , preferedLanguage (donor.getPreferredLanguage ()));
            donorJson.put ("venue" , venue (donor.getVenue ()));

            return donorJson;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;

    }

    public JSONObject updateDonor (Donor donor) {

        try {
            final JSONObject donorJson = new JSONObject ();

            if(donor == null)
            return donorJson;
            donorJson.put ("id" , donor.getServerId ());
            donorJson.put ("firstName" , donor.getFirstName ());
            donorJson.put ("middleName" , donor.getMiddleName ());
            donorJson.put ("lastName" , donor.getLastName ());
            donorJson.put ("title" , donor.getTitle ());
            donorJson.put ("gender" , donor.getGender ().toLowerCase ());
            donorJson.put ("callingName" , donor.getCallingName ());
            donorJson.put ("birthDate" , donor.getBirthDate ());
            donorJson.put ("preferredLanguage" , preferedLanguage (donor.getPreferredLanguage ()));
            donorJson.put ("venue" , venue (donor.getVenue ()));
            donorJson.put ("contact" , contact (donor.getContact ()));
            donorJson.put ("address" , address (donor.getAddress ()));
            donorJson.put ("contactMethodType" , contactMethod (donor.getContactMethodType ()));
            donorJson.put ("preferredAddressType" , preferredAddressType (donor.getAddressType ()));

            return donorJson;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;

    }


    public JSONObject donorDefferal (DonorDeferral donorDeferral) {

        try {

            final JSONObject jsonObject = new JSONObject ();

            if(donorDeferral == null)
                return  jsonObject;
            jsonObject.put ("deferralReason" , deferralReason (donorDeferral.getDeferralReason ()));
            jsonObject.put ("deferredUntil" , donorDeferral.getDeferredUntil ());
            jsonObject.put ("deferralReasonText" , donorDeferral.getDeferralReasonText ());
            jsonObject.put ("venue" , venue (donorDeferral.getVenue ()));
            jsonObject.put ("deferralDate" , donorDeferral.getDeferralDate ());
            jsonObject.put ("deferredDonor" , donor (donorDeferral.getDeferredDonor ()));

            System.out.println ("---"+donorDeferral.getVenue ());

            return jsonObject;

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        return null;

    }


    public JSONObject addDonation (Donation donation) {


        try {
       //     System.err.println ("din-" + donation.getDonationBatch ().getServerId ());

            final JSONObject jsonObject = new JSONObject ();
            if(donation ==null)
                return jsonObject;

            if(donation.getDonor () != null)
            jsonObject.put ("donorNumber" , donation.getDonor ().getDonorNumber ());
            jsonObject.put ("id" , null);
            if(donation.getServerId () != null && !donation.getServerId ().isEmpty ())
            jsonObject.put ("id" , donation.getServerId ());
            jsonObject.put ("bleedStartTime" , donation.getBleedStartTime ());
            jsonObject.put ("donationIdentificationNumber" , donation.getDonationIdentificationNumber ());
          if(donation.getDonationBatch () != null)
            jsonObject.put ("donationBatchNumber" , donation.getDonationBatch ().getBatchNumber ());
            jsonObject.put ("bloodPressureSystolic" , donation.getBloodPressureSystolic ());
            jsonObject.put ("bloodPressureDiastolic" , donation.getBloodPressureDiastolic ());
            jsonObject.put ("haemoglobinLevel" , donation.getHaemoglobinCount ());
            jsonObject.put ("isDeleted" , false);
            jsonObject.put ("bleedEndTime" , donation.getBleedEndTime ());
            jsonObject.put ("donorPulse" , donation.getDonorPulse ());
            jsonObject.put ("donorWeight" , donation.getDonorWeight ());
            jsonObject.put ("adverseEvent" , adverseEvent (donation.getAdverseEvent ()));
            jsonObject.put ("donationType" , donationType (donation.getDonationType ()));
            jsonObject.put ("released" , false);
            jsonObject.put ("packType" , packType (donation.getPackType ()));
            jsonObject.put ("donationDate" , donation.getDonationDate ());
            jsonObject.put ("venue" , venue (donation.getVenue ()));

            return jsonObject;


        } catch ( Exception e ) {

            e.printStackTrace ();
        }
        return null;
    }

    public JSONObject setting (LoginUser loginUser) {
        try {
            JSONObject jsonObject = new JSONObject ();
            jsonObject.put ("id" , loginUser.getServerId ());
            jsonObject.put ("firstName" , loginUser.getFirsname ());
            jsonObject.put ("username" , loginUser.getUsername ());
            jsonObject.put ("lastName" , loginUser.getLastname ());
            jsonObject.put ("emailId" , loginUser.getEmail ());
            if (loginUser.getNewPassword () != null && !loginUser.getNewPassword ().isEmpty ()) {
                jsonObject.put ("modifyPassword" , true);
                jsonObject.put ("currentPassword" , loginUser.getOldPassword ());
                jsonObject.put ("password" , loginUser.getNewPassword ());
                jsonObject.put ("confirmPassword" , loginUser.getNewPassword ());
            }
            jsonObject.put ("passwordReset" , false);

            JSONArray roles = new JSONArray ();
            JSONObject role = new JSONObject ();
            JSONArray permission = new JSONArray ();
            role.put ("permissions" , permission);
            roles.put (0 , role);

            jsonObject.put ("roles" , roles);
            System.out.println ("settting json===" + jsonObject.toString ());

            return jsonObject;

        } catch ( Exception e ) {

            e.printStackTrace ();
        }
        return null;
    }



/*
    public Donation addDonatoin(Donation donation){
        //start time formate correction


        try {

            JSONObject adverseEventJsonObject = new JSONObject().put(
                    "type",new JSONObject().put("id",donation.getAdverseEvent().getId())).
                    put( "comment",donation.ad);

            JSONObject packTypeJson =new JSONObject().put("id",formChoice.getPackTypeObject(pack.getSelectedItemPosition()).getId());
            PackType packTypeText =formChoice.getPackTypeObject(pack.getSelectedItemPosition());
            JSONObject venueText = new JSONObject().put("id",formChoice.getDonationBatchObject(batch.getSelectedItemPosition()).getVenue().getId());
            JSONObject donationTypeText = new JSONObject().put("id",formChoice.getDonationTypeObject(donation.getSelectedItemPosition()).getId());

            System.out.println("bpMax.getText().toString()==="+bpMax.getText().toString().isEmpty());
            System.out.println("bpMin.getText().toString()==="+bpMax.getText().toString());
            System.out.println("weight.getText().toString()==="+weight.getText().toString());
            String systolid = bpMax.getText().toString().isEmpty()? null : bpMax.getText().toString();
            String diastolic = bpMin.getText().toString().isEmpty()? null : bpMin.getText().toString();
            String dWeight = weight.getText().toString().isEmpty()? null : weight.getText().toString();
            String heamB = hb.getText().toString().isEmpty()? null : hb.getText().toString();
            String pulseText = pulse.getText().toString().isEmpty()? null : pulse.getText().toString();





            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("donorNumber", donation.getDonor().getDonorNumber());
            jsonObject.put("id", null);
            jsonObject.put("bleedStartTime", donation.getBleedStartTime());
            jsonObject.put("donationIdentificationNumber", donation.getDonationIdentificationNumber());
            jsonObject.put("donationBatchNumber", donation.getDonationBatch());
            jsonObject.put("bloodPressureSystolic", donation.getBloodPressureSystolic());
            jsonObject.put("bloodPressureDiastolic", donation.getBloodPressureDiastolic());
            jsonObject.put("haemoglobinCount", donation.getHaemoglobinCount());
            jsonObject.put("isDeleted", false);
            jsonObject.put("bleedEndTime", donation.getBleedEndTime());
            jsonObject.put("donorPulse", donation.getDonorPulse());
            jsonObject.put("donorWeight", donation.getDonorWeight());
            jsonObject.put("adverseEvent", adverseEventJsonObject);
            jsonObject.put("donationType", donationTypeText);
            jsonObject.put("released", false);
            jsonObject.put("packType", packTypeJson);
            jsonObject.put("haemoglobinLevel", null);
            jsonObject.put("donationDate", df.format(new Date()));
            jsonObject.put("venue", venueText);


            final RestPort restPort = new RestPort(getBaseContext(), null, null, "addDonation", null, null, null, null);
            final ApiInterface apiInterface = restPort.retrofitsetup();
            restPort.methodCall(new Callable() {
                @Override
                public Object call() throws Exception {
                    return apiInterface.addDonation(restPort.authString(), "application/json", jsonObject.toString());
                }
            }, AddDonationActivity.this);


        }catch (Exception e){
            e.printStackTrace();
        }

        return donor;
    }


 */
}
