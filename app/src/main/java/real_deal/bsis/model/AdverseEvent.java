package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class AdverseEvent extends SugarRecord implements Parcelable {


    public static final  Creator < AdverseEvent > CREATOR          =
            new Creator < AdverseEvent > () {
                @Override
                public AdverseEvent createFromParcel (Parcel in) {
                    return new AdverseEvent (in);
                }

                @Override
                public AdverseEvent[] newArray (int size) {
                    return new AdverseEvent[ size ];
                }
            };
    private static final long                     serialVersionUID = 1L;
    private              AdverseEventType         type;
    private              String                   comment;
    private              String                   serverId;

    public AdverseEvent (AdverseEventType type , String comment , String serverId) {
        this.type     = type;
        this.comment  = comment;
        this.serverId = serverId;
    }

    public AdverseEvent (AdverseEventType type , String comment) {
        this.type    = type;
        this.comment = comment;
    }

    public AdverseEvent () {

    }

    protected AdverseEvent (Parcel in) {
//        System.out.println("in----------------"+in.readString());
        comment = in.readString ();
        type    = in.readParcelable (AdverseEventType.class.getClassLoader ());

    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public AdverseEventType getType () {
        return type;
    }

    public void setType (AdverseEventType type) {
        this.type = type;
    }

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeString (comment);
        dest.writeParcelable (type , flags);
    }
}
