package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class DonationBatch extends SugarRecord implements Parcelable {

    public static final  Creator < DonationBatch > CREATOR          =
            new Creator < DonationBatch > () {
                @Override
                public DonationBatch createFromParcel (Parcel in) {
                    return new DonationBatch (in);
                }

                @Override
                public DonationBatch[] newArray (int size) {
                    return new DonationBatch[ size ];
                }
            };
    private static final long                      serialVersionUID = 1L;
    Long id;
    private String   batchNumber;
    private String   donations = "";
    private Location venue;
    private String   lastUpdated;
    private String   componentBatch;
    private boolean  isDeleted = false;
    private boolean  isClosed;
    private String   notes;
    private boolean  backEntry;
    private String   donationBatchDate;
    private String   serverId;

    public DonationBatch (String serverId , String batchNumber , Location venue , boolean isClosed , String donationBatchDate) {
        this.batchNumber       = batchNumber;
        this.venue             = venue;
        this.isClosed          = isDeleted;
        this.donationBatchDate = donationBatchDate;
        this.serverId          = serverId;
    }

    public DonationBatch () {
    }

    public DonationBatch (String batchNumber , Location venue , boolean isClosed , String donationBatchDate) {
        this.batchNumber       = batchNumber;
        this.venue             = venue;
        this.isClosed          = isClosed;
        this.donationBatchDate = donationBatchDate;
    }

    protected DonationBatch (Parcel in) {
        id                = in.readLong ();
        batchNumber       = in.readString ();
        donations         = in.readString ();
        venue             = in.readParcelable (Location.class.getClassLoader ());
        componentBatch    = in.readString ();
        isDeleted         = in.readByte () != 0;
        isClosed          = in.readByte () != 0;
        notes             = in.readString ();
        backEntry         = in.readByte () != 0;
        donationBatchDate = in.readString ();
    }

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

    public String getLastUpdated () {
        return lastUpdated;
    }

    public void setLastUpdated (String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isDeleted () {
        return isDeleted;
    }

    public void setDeleted (boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isClosed () {
        return isClosed;
    }

    public void setClosed (boolean closed) {
        this.isClosed = closed;
    }

    public String getBatchNumber () {
        return batchNumber;
    }

    public void setBatchNumber (String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

    public String getDonations () {
        return donations;
    }

    public void setDonations (String donations) {
        this.donations = donations;
    }

    public void setDonation (String donations) {
        this.donations = donations;
    }


    public Location getVenue () {
        return venue;
    }

    public void setVenue (Location venue) {
        this.venue = venue;
    }

    public void copy (DonationBatch donationBatch) {
        this.setNotes (donationBatch.getNotes ());
        this.venue = donationBatch.getVenue ();
    }

    public boolean isBackEntry () {
        return backEntry;
    }

    public void setBackEntry (boolean backEntry) {
        this.backEntry = backEntry;
    }

    public String getComponentBatch () {
        return componentBatch;
    }

    public void setComponentBatch (String componentBatch) {
        this.componentBatch = componentBatch;
    }

    public String getDonationBatchDate () {
        return donationBatchDate;
    }

    public void setDonationBatchDate (String donationBatchDate) {
        this.donationBatchDate = donationBatchDate;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeLong (id);
        dest.writeString (batchNumber);
        dest.writeString (donations);
        dest.writeParcelable (venue , flags);
        dest.writeString (componentBatch);
        dest.writeByte (( byte ) (isDeleted ? 1 : 0));
        dest.writeByte (( byte ) (isClosed ? 1 : 0));
        dest.writeString (notes);
        dest.writeByte (( byte ) (backEntry ? 1 : 0));
        dest.writeString (donationBatchDate);
    }
}
