package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class AdverseEventType extends SugarRecord implements Parcelable {
    public static final  Creator < AdverseEventType > CREATOR          =
            new Creator < AdverseEventType > () {
                @Override
                public AdverseEventType createFromParcel (Parcel in) {
                    return new AdverseEventType (in);
                }

                @Override
                public AdverseEventType[] newArray (int size) {
                    return new AdverseEventType[ size ];
                }
            };
    private static final long                         serialVersionUID = 1L;
    private              String                       name;
    private              String                       description;
    private              boolean                      isDeleted;
    private              String                       serverId;

    public AdverseEventType () {
    }

    public AdverseEventType (String name , String description , boolean isDeleted) {
        this.name        = name;
        this.description = description;
        this.isDeleted   = isDeleted;
    }

    public AdverseEventType (String serverId , String name , String description , boolean isDeleted) {
        this.serverId    = serverId;
        this.name        = name;
        this.description = description;
        this.isDeleted   = isDeleted;
    }

    protected AdverseEventType (Parcel in) {
        name        = in.readString ();
        description = in.readString ();
        isDeleted   = in.readByte () != 0;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public boolean isDeleted () {
        return isDeleted;
    }

    public void setDeleted (boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeString (name);
        dest.writeString (description);
        dest.writeByte (( byte ) (isDeleted ? 1 : 0));
    }
}
