package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class DonationType extends SugarRecord implements Parcelable {
    public static final  Creator < DonationType > CREATOR          =
            new Creator < DonationType > () {
                @Override
                public DonationType createFromParcel (Parcel in) {
                    return new DonationType (in);
                }

                @Override
                public DonationType[] newArray (int size) {
                    return new DonationType[ size ];
                }
            };
    private static final long                     serialVersionUID = 1L;
    private              String                   donationType;
    private              Boolean                  isDeleted;
    private              String                   serverId;

    public DonationType () {
    }

    public DonationType (String serverId , String donationType , Boolean isDeleted) {
        this.donationType = donationType;
        this.isDeleted    = isDeleted;
        this.serverId     = serverId;
    }

    public DonationType (String donationType , Boolean isDeleted) {
        this.donationType = donationType;
        this.isDeleted    = isDeleted;
    }

    protected DonationType (Parcel in) {
        donationType = in.readString ();
        byte tmpIsDeleted = in.readByte ();
        isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public Boolean getIsDeleted () {
        return isDeleted;
    }

    public void setIsDeleted (Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDonationType () {
        return donationType;
    }

    public void setDonationType (String donorType) {
        this.donationType = donorType;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeString (donationType);
        dest.writeByte (( byte ) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
    }
}