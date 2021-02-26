package real_deal.bsis.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;

public class Donor extends SugarRecord implements Parcelable {

    private Long                   id;
    private String                 donorNumber;
    private String                 title;
    private String                 firstName;
    private String                 middleName;
    private String                 lastName;
    private String                 callingName;
    private String                 gender;
    private String                 bloodAbo;
    private String                 bloodRh;
    private String                 birthDate;
    private String                 birthDateInferred;
    private Boolean                birthDateEstimated;
    private String                 age;
    private String                 donorHash;
    private Location               venue;
    private String                 notes;
    private String                 dateOfLastDonation;
    private boolean                createdFromMobile = false;
    /**
     * Never delete the rows. Just mark them as deleted.
     */
    private Boolean                isDeleted         = Boolean.FALSE;
    private String                 donations;
    /**
     * If a donor has been deferred then we can disallow him to donate the next time.
     */

    private List < DonorDeferral > deferrals;
    private Address                address;
    private Contact                contact;
    private AddressType            addressType;
    private ContactMethodType      contactMethodType;
    private String                 idType;
    private String                 idNumber;
    private String                 donorStatus;
    private String                 dateOfFirstDonation;
    private PreferredLanguage      preferredLanguage;
    private String                 dueToDonate;
    private String                 serverId;

    public Donor (String serverId , String donorNumber , String title , String firstName , String middleName , String lastName , String callingName , String gender , String birthDate , String age , Location venue , String dateOfLastDonation , Address address , Contact contact , AddressType addressType , ContactMethodType contactMethodType , String donorStatus , String dateOfFirstDonation , PreferredLanguage preferredLanguage , String dueToDonate) {
        this.donorNumber         = donorNumber;
        this.title               = title;
        this.firstName           = firstName;
        this.middleName          = middleName;
        this.lastName            = lastName;
        this.callingName         = callingName;
        this.gender              = gender;
        this.birthDate           = birthDate;
        this.age                 = age;
        this.venue               = venue;
        this.dateOfLastDonation  = dateOfLastDonation;
        this.address             = address;
        this.contact             = contact;
        this.addressType         = addressType;
        this.contactMethodType   = contactMethodType;
        this.donorStatus         = donorStatus;
        this.dateOfFirstDonation = dateOfFirstDonation;
        this.preferredLanguage   = preferredLanguage;
        this.dueToDonate         = dueToDonate;
        this.serverId            = serverId;
    }

    public Donor (String donorNumber , String title , String firstName , String middleName , String lastName , String callingName , String gender , String birthDate , Location venue , PreferredLanguage preferredLanguage) {
        this.donorNumber       = donorNumber;
        this.title             = title;
        this.firstName         = firstName;
        this.middleName        = middleName;
        this.lastName          = lastName;
        this.callingName       = callingName;
        this.gender            = gender;
        this.birthDate         = birthDate;
        this.venue             = venue;
        this.preferredLanguage = preferredLanguage;
    }

    public Donor (String donorNumber , String title , String firstName , String middleName , String lastName , String callingName , String gender , String birthDate , Location venue , PreferredLanguage preferredLanguage , boolean createdFromMobile) {
        this.donorNumber       = donorNumber;
        this.title             = title;
        this.firstName         = firstName;
        this.middleName        = middleName;
        this.lastName          = lastName;
        this.callingName       = callingName;
        this.gender            = gender;
        this.birthDate         = birthDate;
        this.venue             = venue;
        this.preferredLanguage = preferredLanguage;
        this.createdFromMobile = createdFromMobile;
    }

    public Donor () {
        super ();
    }

    public Donor (String donorNumber , String title , String firstName , String middleName , String lastName , String callingName , String gender , String birthDate , String age , Location venue , String notes , Address address , Contact contact , PreferredLanguage preferredLanguage) {
        this.donorNumber       = donorNumber;
        this.title             = title;
        this.firstName         = firstName;
        this.middleName        = middleName;
        this.lastName          = lastName;
        this.callingName       = callingName;
        this.gender            = gender;
        this.birthDate         = birthDate;
        this.age               = age;
        this.venue             = venue;
        this.notes             = notes;
        this.address           = address;
        this.contact           = contact;
        this.preferredLanguage = preferredLanguage;
    }

    protected Donor (Parcel in) {
        if (in.readByte () == 0) {
            id = null;
        } else {
            id = in.readLong ();
        }
        donorNumber       = in.readString ();
        title             = in.readString ();
        firstName         = in.readString ();
        middleName        = in.readString ();
        lastName          = in.readString ();
        callingName       = in.readString ();
        gender            = in.readString ();
        bloodAbo          = in.readString ();
        bloodRh           = in.readString ();
        birthDate         = in.readString ();
        birthDateInferred = in.readString ();
        byte tmpBirthDateEstimated = in.readByte ();
        birthDateEstimated = tmpBirthDateEstimated == 0 ? null : tmpBirthDateEstimated == 1;
        age                = in.readString ();
        donorHash          = in.readString ();
        venue              = in.readParcelable (Location.class.getClassLoader ());
        notes              = in.readString ();
        dateOfLastDonation = in.readString ();
        byte tmpIscreatedFrommobile = in.readByte ();
        if(tmpIscreatedFrommobile != 0)
        createdFromMobile = tmpIscreatedFrommobile == 0 ? null : tmpIscreatedFrommobile == 1;
        byte tmpIsDeleted = in.readByte ();
        isDeleted           = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        donations           = in.readString ();
        deferrals           = in.createTypedArrayList (DonorDeferral.CREATOR);
        address             = in.readParcelable (Address.class.getClassLoader ());
        contact             = in.readParcelable (Contact.class.getClassLoader ());
        addressType         = in.readParcelable (AddressType.class.getClassLoader ());
        contactMethodType   = in.readParcelable (ContactMethodType.class.getClassLoader ());
        idType              = in.readString ();
        idNumber            = in.readString ();
        donorStatus         = in.readString ();
        dateOfFirstDonation = in.readString ();
        dueToDonate         = in.readString ();
        serverId            = in.readString ();
    }

    public static final Creator < Donor > CREATOR = new Creator < Donor > () {
        @Override
        public Donor createFromParcel (Parcel in) {
            return new Donor (in);
        }

        @Override
        public Donor[] newArray (int size) {
            return new Donor[ size ];
        }
    };

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    @Override
    public Long getId () {
        return id;
    }

    @Override
    public void setId (Long id) {
        this.id = id;
    }

    public boolean isCreatedFromMobile () {
        return createdFromMobile;
    }

    public void setCreatedFromMobile (boolean createdFromMobile) {
        this.createdFromMobile = createdFromMobile;
    }

    public String getDonorNumber () {
        return donorNumber;
    }

    public void setDonorNumber (String donorNumber) {
        this.donorNumber = donorNumber;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
//        if (firstName != null)
//            firstName = firstName.toUpperCase ();
        this.firstName = firstName;
    }

    public String getMiddleName () {
        return middleName;
    }

    public void setMiddleName (String middleName) {
//        if (middleName != null)
//            middleName = middleName.toLowerCase ();
        this.middleName = middleName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
//        if (lastName != null)
//            lastName = lastName.toUpperCase ();
        this.lastName = lastName;
    }

    public String getGender () {
        return gender;
    }

    public void setGender (String gender) {
        this.gender = gender;
    }

    public String getBirthDate () {
        return birthDate;
    }

    public void setBirthDate (String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getDeleted () {
        return isDeleted;
    }

    public void setDeleted (Boolean deleted) {
        isDeleted = deleted;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

    public Boolean getIsDeleted () {
        return isDeleted;
    }

    public void setIsDeleted (Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    public void copy (Donor donor) {
        assert (donor.getId ().equals (this.getId ()));
        setTitle (donor.getTitle ());
        setFirstName (donor.getFirstName ());
        setMiddleName (donor.getMiddleName ());
        setLastName (donor.getLastName ());
        setCallingName (donor.getCallingName ());
        setBirthDate (donor.getBirthDate ());
        setBirthDateInferred (donor.getBirthDateInferred ());
        setBirthDateEstimated (donor.getBirthDateEstimated ());
        setNotes (donor.getNotes ());
        setGender (donor.getGender ());
        setVenue (donor.getVenue ());
        setPreferredLanguage (donor.getPreferredLanguage ());
        setBloodAbo (donor.getBloodAbo ());
        setBloodRh (donor.getBloodRh ());
        this.donorHash = donor.getDonorHash ();
        setDateOfFirstDonation (donor.getDateOfFirstDonation ());
        setIdType (donor.getIdType ());
        setAddressType (donor.getAddressType ());
        setContactMethodType (donor.getContactMethodType ());
        setIdNumber (donor.getIdNumber ());
        setContact (donor.getContact ());
        setAddress (donor.getAddress ());
    }


    public String getDonations () {
        return donations;
    }

    public void setDonations (String donations) {
        this.donations = donations;
    }

    public String getCallingName () {
        return callingName;
    }

    public void setCallingName (String callingName) {
        this.callingName = callingName;
    }

    public List < DonorDeferral > getDeferrals () {
        return deferrals;
    }

    public void setDeferrals (List < DonorDeferral > deferrals) {
        this.deferrals = deferrals;
    }

    public String getBirthDateInferred () {
        return birthDateInferred;
    }

    public void setBirthDateInferred (String birthDateInferred) {
        this.birthDateInferred = birthDateInferred;
    }

    public Location getVenue () {
        return venue;
    }

    public void setVenue (Location venue) {
        this.venue = venue;
    }

    public String getDateOfLastDonation () {
        return dateOfLastDonation;
    }

    public void setDateOfLastDonation (String dateOfLastDonation) {
        this.dateOfLastDonation = dateOfLastDonation;
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

    public String getDonorStatus () {
        return donorStatus;
    }

    public void setDonorStatus (String donorStatus) {
        this.donorStatus = donorStatus;
    }

    public String getAge () {
        return age;
    }

    public void setAge (String age) {
        this.age = age;
    }

    public String getDonorHash () {
        return donorHash;
    }

    public void setDonorHash (String donorHash) {
        this.donorHash = donorHash;
    }

    public String getDateOfFirstDonation () {
        return dateOfFirstDonation;
    }

    public void setDateOfFirstDonation (String dateOfFirstDonation) {
        this.dateOfFirstDonation = dateOfFirstDonation;
    }

    public Boolean getBirthDateEstimated () {
        return birthDateEstimated;
    }

    public void setBirthDateEstimated (Boolean birthDateEstimated) {
        this.birthDateEstimated = birthDateEstimated;
    }

    public PreferredLanguage getPreferredLanguage () {
        return preferredLanguage;
    }

    public void setPreferredLanguage (PreferredLanguage preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public Address getAddress () {
        return address;
    }

    public void setAddress (Address addresss) {
        this.address = addresss;
    }

    public Contact getContact () {
        return contact;
    }

    public void setContact (Contact contact) {
        this.contact = contact;
    }

    public String getIdType () {
        return idType;
    }

    public void setIdType (String idType) {
        this.idType = idType;
    }

    public String getIdNumber () {
        return idNumber;
    }

    public void setIdNumber (String idNumber) {
        this.idNumber = idNumber;
    }

    public AddressType getAddressType () {
        return addressType;
    }

    public void setAddressType (AddressType addressType) {
        this.addressType = addressType;
    }

    public ContactMethodType getContactMethodType () {
        return contactMethodType;
    }

    public void setContactMethodType (ContactMethodType contactMethodType) {
        this.contactMethodType = contactMethodType;
    }

    public String getDueToDonate () {
        return dueToDonate;
    }

    public void setDueToDonate (String dueToDonate) {
        this.dueToDonate = dueToDonate;
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
        dest.writeString (donorNumber);
        dest.writeString (title);
        dest.writeString (firstName);
        dest.writeString (middleName);
        dest.writeString (lastName);
        dest.writeString (callingName);
        dest.writeString (gender);
        dest.writeString (bloodAbo);
        dest.writeString (bloodRh);
        dest.writeString (birthDate);
        dest.writeString (birthDateInferred);
        dest.writeByte (( byte ) (birthDateEstimated == null ? 0 : birthDateEstimated ? 1 : 2));
        dest.writeString (age);
        dest.writeString (donorHash);
        dest.writeParcelable (venue , flags);
        dest.writeString (notes);
        dest.writeString (dateOfLastDonation);
        dest.writeByte (( byte ) (createdFromMobile ? 1 : 0));
        dest.writeByte (( byte ) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeString (donations);
        dest.writeTypedList (deferrals);
        dest.writeParcelable (address , flags);
        dest.writeParcelable (contact , flags);
        dest.writeParcelable (addressType , flags);
        dest.writeParcelable (contactMethodType , flags);
        dest.writeString (idType);
        dest.writeString (idNumber);
        dest.writeString (donorStatus);
        dest.writeString (dateOfFirstDonation);
        dest.writeString (dueToDonate);
        dest.writeString (serverId);
    }
}

