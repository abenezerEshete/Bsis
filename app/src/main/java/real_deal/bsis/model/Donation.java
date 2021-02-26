package real_deal.bsis.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Donation extends SugarRecord implements Parcelable {

    public static final Creator < Donation > CREATOR = new Creator < Donation > () {
        @Override
        public Donation createFromParcel (Parcel in) {
            return new Donation (in);
        }

        @Override
        public Donation[] newArray (int size) {
            return new Donation[ size ];
        }
    };
    private static final long          serialVersionUID  = 1L;
    /**
     * Very common usecase to search for donation by donation identification number. In most cases the
     * donation numbers will be preprinted labels.
     */
    private              Long          id;
    private              String        donationIdentificationNumber;
    private              Donor         donor;
    private              String        bloodAbo;
    private              String        bloodRh;
    private              String        bloodTestResults;
    /**
     * Index to find
     * donations done between date ranges.
     */
    private              String        donationDate;
    private              DonationType  donationType;
    private              PackType      packType;
    private              String        haemoglobinCount;
    private              String        bloodPressureSystolic;
    private              String        bloodPressureDiastolic;
    /**
     * Limit the number of bytes required to store.
     */

    private              String        donorWeight;
    private              User          donationCreatedBy;
    private              DonationBatch donationBatch;
    private              String        notes;
    private              String        bloodTypingStatus;
    private              String        bloodTypingMatchStatus;
    private              String        ttiStatus;
    private              Boolean       isDeleted         = Boolean.FALSE;
    private              String        donorPulse;
    private              String        bleedStartTime;
    private              String        bleedEndTime;
    private              Location      venue;
    private              AdverseEvent  adverseEvent;
    // If the donor was ineligible when the donation was captured
    private              boolean       ineligibleDonor   = false;
    // If this donation has been released in a test batch
    private              boolean       released          = false;
    private              String        flagCharacters;
    private              String        serverId;
    private              boolean       createdFromMobile = false;

    public Donation () {
        super ();
    }


    public Donation (String donationIdentificationNumber , Donor donor , String bloodPressureSystolic ,
                     String bloodPressureDiastolic , DonationBatch donationBatch , Location venue , String haemoglobinCount ,
                     String donorPulse , String donorWeight , String donationDate , PackType packType) {
        this.donationIdentificationNumber = donationIdentificationNumber;
        this.donor                        = donor;
        this.packType                     = packType;
        this.bloodPressureSystolic        = bloodPressureSystolic;
        this.bloodPressureDiastolic       = bloodPressureDiastolic;
        this.donationBatch                = donationBatch;
        this.venue                        = venue;
        this.haemoglobinCount             = haemoglobinCount;
        this.donorPulse                   = donorPulse;
        this.donorWeight                  = donorWeight;
        this.donationDate                 = donationDate;
    }

    public Donation (String serverId , String donationIdentificationNumber , Donor donor , String bloodPressureSystolic ,
                     String bloodPressureDiastolic , DonationBatch donationBatch , Location venue , String haemoglobinCount ,
                     String donorPulse , String donorWeight , String donationDate , PackType packType , DonationType donationType ,
                     String bleedStarttime , String bleedEndTime , AdverseEvent adverseEvent) {
        this.serverId                     = serverId;
        this.donationIdentificationNumber = donationIdentificationNumber;
        this.donor                        = donor;
        this.packType                     = packType;
        this.bloodPressureSystolic        = bloodPressureSystolic;
        this.bloodPressureDiastolic       = bloodPressureDiastolic;
        this.donationBatch                = donationBatch;
        this.venue                        = venue;
        this.haemoglobinCount             = (haemoglobinCount);
        this.donorPulse                   = donorPulse;
        this.donorWeight                  = donorWeight;
        this.donationDate                 = donationDate;
        this.donationType                 = donationType;
        this.bleedStartTime               = bleedStarttime;
        this.bleedEndTime                 = bleedEndTime;
        this.adverseEvent                 = adverseEvent;
    }

    public Donation (String donationIdentificationNumber , Donor donor , DonationType donationType , String bloodPressureSystolic ,
                     String bloodPressureDiastolic , DonationBatch donationBatch , Location venue , String haemoglobinCount ,
                     String donorPulse , String donorWeight , String donationDate , PackType packType , AdverseEvent adverseEvent ,
                     String bleedStartTime , String bleedEndTime) {
        this.donationIdentificationNumber = donationIdentificationNumber;
        this.donor                        = donor;
        this.donationType                 = donationType;
        this.packType                     = packType;
        this.bloodPressureSystolic        = bloodPressureSystolic;
        this.bloodPressureDiastolic       = bloodPressureDiastolic;
        this.donationBatch                = donationBatch;
        this.venue                        = venue;
        this.haemoglobinCount             = haemoglobinCount;
        this.donorPulse                   = donorPulse;
        this.donorWeight                  = donorWeight;
        this.donationDate                 = donationDate;
        this.adverseEvent                 = adverseEvent;
        this.bleedStartTime               = bleedStartTime;
        this.bleedEndTime                 = bleedEndTime;
    }

    public Donation (String donationIdentificationNumber , Donor donor , DonationType donationType , String bloodPressureSystolic ,
                     String bloodPressureDiastolic , DonationBatch donationBatch , Location venue , String haemoglobinCount ,
                     String donorPulse , String donorWeight , String donationDate , PackType packType , AdverseEvent adverseEvent ,
                     String bleedStartTime , String bleedEndTime , boolean createdFromMobile) {
        this.donationIdentificationNumber = donationIdentificationNumber;
        this.donor                        = donor;
        this.donationType                 = donationType;
        this.packType                     = packType;
        this.bloodPressureSystolic        = bloodPressureSystolic;
        this.bloodPressureDiastolic       = bloodPressureDiastolic;
        this.donationBatch                = donationBatch;
        this.venue                        = venue;
        this.haemoglobinCount             = haemoglobinCount;
        this.donorPulse                   = donorPulse;
        this.donorWeight                  = donorWeight;
        this.donationDate                 = donationDate;
        this.adverseEvent                 = adverseEvent;
        this.bleedStartTime               = bleedStartTime;
        this.bleedEndTime                 = bleedEndTime;
        this.createdFromMobile            = createdFromMobile;
    }

    public Donation (Donor donor , String bloodPressureSystolic ,
                     String bloodPressureDiastolic , String haemoglobinCount ,
                     String donorPulse , String donorWeight , PackType packType , AdverseEvent adverseEvent) {
        this.donor                  = donor;
        this.packType               = packType;
        this.bloodPressureSystolic  = bloodPressureSystolic;
        this.bloodPressureDiastolic = bloodPressureDiastolic;
        this.haemoglobinCount       = haemoglobinCount;
        this.donorPulse             = donorPulse;
        this.donorWeight            = (donorWeight);
        this.adverseEvent           = adverseEvent;
    }

    public Donation (Donation donation) {
        this ();
        setId (donation.getId ());
        this.donationIdentificationNumber = donation.getDonationIdentificationNumber ();
        this.donor                        = donation.getDonor ();
        this.bloodAbo                     = donation.getBloodAbo ();
        this.bloodRh                      = donation.getBloodRh ();
        this.bloodTestResults             = donation.getBloodTestResults ();
        this.donationDate                 = donation.getDonationDate ();
        this.donationType                 = donation.getDonationType ();
        this.packType                     = donation.getPackType ();
        this.bloodPressureSystolic        = donation.getBloodPressureSystolic ();
        this.bloodPressureDiastolic       = donation.getBloodPressureDiastolic ();
        this.donorWeight                  = donation.getDonorWeight ();
        this.donationBatch                = donation.getDonationBatch ();
        this.notes                        = donation.getNotes ();
        this.bloodTypingStatus            = donation.getBloodTypingStatus ();
        this.bloodTypingMatchStatus       = donation.getBloodTypingMatchStatus ();
        this.ttiStatus                    = donation.getTtiStatus ();
        this.isDeleted                    = donation.getDeleted ();
        this.donorPulse                   = donation.getDonorPulse ();
        this.bleedStartTime               = donation.getBleedStartTime ();
        this.bleedEndTime                 = donation.getBleedEndTime ();
        this.venue                        = donation.getVenue ();
        this.adverseEvent                 = donation.getAdverseEvent ();
        this.flagCharacters               = donation.getFlagCharacters ();
    }

    public Donation (String donationIdentificationNumber , Donor donor , String donationDate , DonationType donationType , PackType packType , String bloodPressureSystolic , String bloodPressureDiastolic , String donorWeight , String notes , String bloodTypingStatus , String bleedStartTime , String bleedEndTime , Location venue , AdverseEvent adverseEvent) {
        this.donationIdentificationNumber = donationIdentificationNumber;
        this.donor                        = donor;
        this.donationDate                 = donationDate;
        this.donationType                 = donationType;
        this.packType                     = packType;
        this.bloodPressureSystolic        = bloodPressureSystolic;
        this.bloodPressureDiastolic       = bloodPressureDiastolic;
        this.donorWeight                  = (donorWeight);
        this.notes                        = notes;
        this.bloodTypingStatus            = bloodTypingStatus;
        this.bleedStartTime               = bleedStartTime;
        this.bleedEndTime                 = bleedEndTime;
        this.venue                        = venue;
        this.adverseEvent                 = adverseEvent;
    }


    public Donation (Class < Donation > donationClass) {
    }

    protected Donation (Parcel in) {
        if (in.readByte () == 0) {
            id = null;
        } else {
            id = in.readLong ();
        }
        donationIdentificationNumber = in.readString ();
        donor                        = in.readParcelable (Donor.class.getClassLoader ());
        bloodAbo                     = in.readString ();
        bloodRh                      = in.readString ();
        bloodTestResults             = in.readString ();
        donationDate                 = in.readString ();
        donationType                 = in.readParcelable (DonationType.class.getClassLoader ());
        packType                     = in.readParcelable (PackType.class.getClassLoader ());
        haemoglobinCount             = in.readString ();
        bloodPressureSystolic        = in.readString ();
        bloodPressureDiastolic       = in.readString ();
        donorWeight                  = in.readString ();
        donationBatch                = in.readParcelable (DonationBatch.class.getClassLoader ());
        notes                        = in.readString ();
        bloodTypingStatus            = in.readString ();
        bloodTypingMatchStatus       = in.readString ();
        ttiStatus                    = in.readString ();
        byte tmpIsDeleted = in.readByte ();
        isDeleted         = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        donorPulse        = in.readString ();
        bleedStartTime    = in.readString ();
        bleedEndTime      = in.readString ();
        venue             = in.readParcelable (Location.class.getClassLoader ());
        adverseEvent      = in.readParcelable (AdverseEvent.class.getClassLoader ());
        ineligibleDonor   = in.readByte () != 0;
        released          = in.readByte () != 0;
        flagCharacters    = in.readString ();
        serverId          = in.readString ();
        createdFromMobile = in.readByte () != 0;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    @Override
    public Long getId () {
        return id;
    }

    @Override
    public void setId (Long id) {
        this.id = id;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public String getDonationIdentificationNumber () {
        return donationIdentificationNumber;
    }

    public void setDonationIdentificationNumber (String donationIdentificationNumber) {
        this.donationIdentificationNumber = donationIdentificationNumber;
    }

    public Donor getDonor () {
        return donor;
    }

    public void setDonor (Donor donor) {
        this.donor = donor;
//        List <Donation> donations = donor.getDonations ();
//
//         donations.add ( this );
//        donor.setDonations ( donations );
//
//        System.out.println ( "List<Donation> donations =donor.getDonations();==========" + donor );
    }

    public String getBloodAbo () {
        return bloodAbo;
    }

    public void setBloodAbo (String bloodAbo) {
        this.bloodAbo = bloodAbo;
    }

    public String getBloodRh () {
        return bloodRh;
    }

    public void setBloodRh (String bloodRh) {
        this.bloodRh = bloodRh;
    }

    public String getBloodTestResults () {
        return bloodTestResults;
    }

    public void setBloodTestResults (String bloodTestResults) {
        this.bloodTestResults = bloodTestResults;
    }

    public String getDonationDate () {
        return donationDate;
    }

    public void setDonationDate (String donationDate) {
        this.donationDate = donationDate;
    }

    public DonationType getDonationType () {
        return donationType;
    }

    public void setDonationType (DonationType donationType) {
        this.donationType = donationType;
    }

    public PackType getPackType () {
        return packType;
    }

    public void setPackType (PackType packType) {
        this.packType = packType;
    }

    public String getHaemoglobinCount () {
        return haemoglobinCount;
    }

    public void setHaemoglobinCount (String haemoglobinCount) {
        this.haemoglobinCount = haemoglobinCount;
    }

    public String getBloodPressureSystolic () {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic (String bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public String getBloodPressureDiastolic () {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic (String bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }

    public String getDonorWeight () {
        return donorWeight;
    }

    public void setDonorWeight (String donorWeight) {
        this.donorWeight = donorWeight;
    }

    public User getDonationCreatedBy () {
        return donationCreatedBy;
    }

    public void setDonationCreatedBy (User donationCreatedBy) {
        this.donationCreatedBy = donationCreatedBy;
    }

    public DonationBatch getDonationBatch () {
        return donationBatch;
    }

    public void setDonationBatch (DonationBatch donationBatch) {
        this.donationBatch = donationBatch;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

    public String getBloodTypingStatus () {
        return bloodTypingStatus;
    }

    public void setBloodTypingStatus (String bloodTypingStatus) {
        this.bloodTypingStatus = bloodTypingStatus;
    }

    public String getBloodTypingMatchStatus () {
        return bloodTypingMatchStatus;
    }

    public void setBloodTypingMatchStatus (String bloodTypingMatchStatus) {
        this.bloodTypingMatchStatus = bloodTypingMatchStatus;
    }

    public String getTtiStatus () {
        return ttiStatus;
    }

    public void setTtiStatus (String ttiStatus) {
        this.ttiStatus = ttiStatus;
    }

    public Boolean getDeleted () {
        return isDeleted;
    }

    public void setDeleted (Boolean deleted) {
        isDeleted = deleted;
    }

    public String getDonorPulse () {
        return donorPulse;
    }

    public void setDonorPulse (String donorPulse) {
        this.donorPulse = donorPulse;
    }

    public String getBleedStartTime () {
        return bleedStartTime;
    }

    public void setBleedStartTime (String bleedStartTime) {
        this.bleedStartTime = bleedStartTime;
    }

    public String getBleedEndTime () {
        return bleedEndTime;
    }

    public void setBleedEndTime (String bleedEndTime) {
        this.bleedEndTime = bleedEndTime;
    }

    public Location getVenue () {
        return venue;
    }

    public void setVenue (Location venue) {
        this.venue = venue;
    }

    public AdverseEvent getAdverseEvent () {
        return adverseEvent;
    }

    public void setAdverseEvent (AdverseEvent adverseEvent) {
        this.adverseEvent = adverseEvent;
    }

    public boolean isIneligibleDonor () {
        return ineligibleDonor;
    }

    public void setIneligibleDonor (boolean ineligibleDonor) {
        this.ineligibleDonor = ineligibleDonor;
    }

    public boolean isReleased () {
        return released;
    }

    public void setReleased (boolean released) {
        this.released = released;
    }

    public String getFlagCharacters () {
        return flagCharacters;
    }

    public void setFlagCharacters (String flagCharacters) {
        this.flagCharacters = flagCharacters;
    }

    public boolean isCreatedFromMobile () {
        return createdFromMobile;
    }

    public void setCreatedFromMobile (boolean createdFromMobile) {
        this.createdFromMobile = createdFromMobile;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        if (id == null) {
            dest.writeByte (( byte ) 0);
        } else {
            dest.writeByte (( byte ) 1);
            dest.writeLong (id);
        }
        dest.writeString (donationIdentificationNumber);
        dest.writeParcelable (donor , flags);
        dest.writeString (bloodAbo);
        dest.writeString (bloodRh);
        dest.writeString (bloodTestResults);
        dest.writeString (donationDate);
        dest.writeParcelable (donationType , flags);
        dest.writeParcelable (packType , flags);
        dest.writeString (haemoglobinCount);
        dest.writeString (bloodPressureSystolic);
        dest.writeString (bloodPressureDiastolic);
        dest.writeString (donorWeight);
        dest.writeParcelable (donationBatch , flags);
        dest.writeString (notes);
        dest.writeString (bloodTypingStatus);
        dest.writeString (bloodTypingMatchStatus);
        dest.writeString (ttiStatus);
        dest.writeByte (( byte ) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeString (donorPulse);
        dest.writeString (bleedStartTime);
        dest.writeString (bleedEndTime);
        dest.writeParcelable (venue , flags);
        dest.writeParcelable (adverseEvent , flags);
        dest.writeByte (( byte ) (ineligibleDonor ? 1 : 0));
        dest.writeByte (( byte ) (released ? 1 : 0));
        dest.writeString (flagCharacters);
        dest.writeString (serverId);
        dest.writeByte (( byte ) (createdFromMobile ? 1 : 0));
    }
}
