package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.Date;

public class DonorDeferral extends SugarRecord implements Parcelable {

    public static final Creator < DonorDeferral > CREATOR = new Creator < DonorDeferral > () {
        @Override
        public DonorDeferral createFromParcel (Parcel in) {
            return new DonorDeferral (in);
        }

        @Override
        public DonorDeferral[] newArray (int size) {
            return new DonorDeferral[ size ];
        }
    };
    private static final long           serialVersionUID  = 1L;
    private              Long           id;
    private              String         deferralDate;
    private              Donor          deferredDonor;
    private              String         deferredUntil;
    private              DeferralReason deferralReason;
    private              Location       venue;
    private              User           voidedBy;
    private              String         deferralReasonText;
    private              Boolean        isVoided          = Boolean.FALSE;
    private              Date           voidedDate;
    private              String         serverId;
    private              boolean        createdFromMobile = false;


    public DonorDeferral (String serverId , String deferralDate , DeferralReason deferralReason , Donor deferredDonor , String deferredUntil , Location venue , String deferralReasonText) {
        this.serverId           = serverId;
        this.deferralDate       = deferralDate;
        this.deferralReason     = deferralReason;
        this.deferredDonor      = deferredDonor;
        this.deferredUntil      = deferredUntil;
        this.venue              = venue;
        this.deferralReasonText = deferralReasonText;
    }


    public DonorDeferral (String deferralDate , DeferralReason deferralReason , Donor deferredDonor , String deferredUntil , Location venue , String deferralReasonText) {
        this.deferralDate       = deferralDate;
        this.deferralReason     = deferralReason;
        this.deferredDonor      = deferredDonor;
        this.deferredUntil      = deferredUntil;
        this.venue              = venue;
        this.deferralReasonText = deferralReasonText;
    }

    public DonorDeferral (String deferralDate , DeferralReason deferralReason , Donor deferredDonor , String deferredUntil ,
                          Location venue , String deferralReasonText , boolean createdFromMobile) {
        this.deferralDate       = deferralDate;
        this.deferralReason     = deferralReason;
        this.deferredDonor      = deferredDonor;
        this.deferredUntil      = deferredUntil;
        this.venue              = venue;
        this.deferralReasonText = deferralReasonText;
        this.createdFromMobile  = createdFromMobile;
    }

    public DonorDeferral () {
    }

    public DonorDeferral (String deferralDate , Donor deferredDonor , Location venue) {
        this.deferralDate  = deferralDate;
        this.deferredDonor = deferredDonor;
        this.venue         = venue;
    }

    protected DonorDeferral (Parcel in) {
        if (in.readByte () == 0) {
            id = null;
        } else {
            id = in.readLong ();
        }
        deferralDate       = in.readString ();
        deferredDonor      = in.readParcelable (Donor.class.getClassLoader ());
        deferredUntil      = in.readString ();
        deferralReason     = in.readParcelable (DeferralReason.class.getClassLoader ());
        venue              = in.readParcelable (Location.class.getClassLoader ());
        deferralReasonText = in.readString ();
        byte tmpIsVoided = in.readByte ();
        isVoided          = tmpIsVoided == 0 ? null : tmpIsVoided == 1;
        serverId          = in.readString ();
        createdFromMobile = in.readByte () != 0;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public Donor getDeferredDonor () {
        return deferredDonor;
    }

    public void setDeferredDonor (Donor deferredDonor) {
        this.deferredDonor = deferredDonor;
    }

    public String getDeferredUntil () {
        return deferredUntil;
    }

    public void setDeferredUntil (String deferredUntil) {
        this.deferredUntil = deferredUntil;
    }

    public DeferralReason getDeferralReason () {
        return deferralReason;
    }

    public void setDeferralReason (DeferralReason deferralReason) {
        this.deferralReason = deferralReason;
    }

    public boolean isCreatedFromMobile () {
        return createdFromMobile;
    }

    public void setCreatedFromMobile (boolean createdFromMobile) {
        this.createdFromMobile = createdFromMobile;
    }

    public String getDeferralReasonText () {
        return deferralReasonText;
    }

    public void setDeferralReasonText (String deferralReasonText) {
        this.deferralReasonText = deferralReasonText;
    }

    public Location getVenue () {
        return venue;
    }

    public void setVenue (Location venue) {
        this.venue = venue;
    }

    /**
     * @return the isVoided
     */
    public Boolean getIsVoided () {
        return isVoided;
    }

    /**
     * @param isVoided the isVoided to set
     */
    public void setIsVoided (Boolean isVoided) {
        this.isVoided = isVoided;
    }

    /**
     * @return the voidedBy
     */
    public User getVoidedBy () {
        return voidedBy;
    }

    /**
     * @param voidedBy the voidedBy to set
     */
    public void setVoidedBy (User voidedBy) {
        this.voidedBy = voidedBy;
    }

    /**
     * @return the voidedDate
     */
    public Date getVoidedDate () {
        return voidedDate;
    }

    /**
     * @param voidedDate the voidedDate to set
     */
    public void setVoidedDate (Date voidedDate) {
        this.voidedDate = voidedDate;
    }

    public String getDeferralDate () {
        return deferralDate;
    }

    public void setDeferralDate (String deferralDate) {
        this.deferralDate = deferralDate;
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
        dest.writeString (deferralDate);
        dest.writeParcelable (deferredDonor , flags);
        dest.writeString (deferredUntil);
        dest.writeParcelable (deferralReason , flags);
        dest.writeParcelable (venue , flags);
        dest.writeString (deferralReasonText);
        dest.writeByte (( byte ) (isVoided == null ? 0 : isVoided ? 1 : 2));
        dest.writeString (serverId);
        dest.writeByte (( byte ) (createdFromMobile ? 1 : 0));
    }
}
