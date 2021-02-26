package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class DeferralReason extends SugarRecord implements Parcelable {
    public static final  Creator < DeferralReason > CREATOR          =
            new Creator < DeferralReason > () {
                @Override
                public DeferralReason createFromParcel (Parcel in) {
                    return new DeferralReason (in);
                }

                @Override
                public DeferralReason[] newArray (int size) {
                    return new DeferralReason[ size ];
                }
            };
    private static final long                       serialVersionUID = 203754154113421034L;
    private              String                     reason;
    private              Boolean                    isDeleted;
    private              Integer                    defaultDuration; // in days
    private              String                     type             = "NORMAL";
    private              String                     durationType     = "TEMPORARY";
    private              String                     serverId;

    public DeferralReason (String reason , Boolean isDeleted , Integer defaultDuration) {
        this.reason          = reason;
        this.isDeleted       = isDeleted;
        this.defaultDuration = defaultDuration;
    }

    public DeferralReason () {

    }

    public DeferralReason (String serverId , String reason , Boolean isDeleted) {
        this.reason    = reason;
        this.isDeleted = isDeleted;
        this.serverId  = serverId;
    }

    public DeferralReason (String serverId , String reason , Boolean isDeleted , String durationType) {
        this.reason       = reason;
        this.isDeleted    = isDeleted;
        this.serverId     = serverId;
        this.durationType = durationType;
    }

    protected DeferralReason (Parcel in) {
        reason = in.readString ();
        byte tmpIsDeleted = in.readByte ();
        isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        if (in.readByte () == 0) {
            defaultDuration = null;
        } else {
            defaultDuration = in.readInt ();
        }
        type         = in.readString ();
        durationType = in.readString ();
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public String getReason () {
        return reason;
    }

    public void setReason (String reason) {
        this.reason = reason;
    }

    public Boolean getIsDeleted () {
        return isDeleted;
    }

    public void setIsDeleted (Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void copy (DeferralReason deferralReason) {
        setId (deferralReason.getId ());
        this.reason          = deferralReason.getReason ();
        this.isDeleted       = deferralReason.getIsDeleted ();
        this.defaultDuration = deferralReason.getDefaultDuration ();
        this.durationType    = deferralReason.getDurationType ();
    }

    public Integer getDefaultDuration () {
        return defaultDuration;
    }

    public void setDefaultDuration (Integer defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getDurationType () {
        return durationType;
    }

    public void setDurationType (String durationType) {
        this.durationType = durationType;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeString (reason);
        dest.writeByte (( byte ) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        if (defaultDuration == null) {
            dest.writeByte (( byte ) 0);
        } else {
            dest.writeByte (( byte ) 1);
            dest.writeInt (defaultDuration);
        }
        dest.writeString (type);
        dest.writeString (durationType);
    }
}