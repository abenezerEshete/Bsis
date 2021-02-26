package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class ContactMethodType extends SugarRecord implements Parcelable {


    public static final Creator < ContactMethodType > CREATOR =
            new Creator < ContactMethodType > () {
                @Override
                public ContactMethodType createFromParcel (Parcel in) {
                    return new ContactMethodType (in);
                }

                @Override
                public ContactMethodType[] newArray (int size) {
                    return new ContactMethodType[ size ];
                }
            };
    private String  contactMethodType;
    private String  serverId;
    private boolean isDeleted;

    protected ContactMethodType (Parcel in) {
        contactMethodType = in.readString ();
        serverId          = in.readString ();
        isDeleted         = in.readByte () != 0;
    }

    public ContactMethodType (String serverId , String contactMethodType , boolean isDeleted) {
        this.contactMethodType = contactMethodType;
        this.serverId          = serverId;
        this.isDeleted         = isDeleted;
    }

    public ContactMethodType (String contactMethodType) {
        this.contactMethodType = contactMethodType;
    }

    public ContactMethodType () {

    }

    public boolean isDeleted () {
        return isDeleted;
    }

    public void setDeleted (boolean deleted) {
        isDeleted = deleted;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public String getContactMethodType () {
        return contactMethodType;
    }

    public void setContactMethodType (String contactMethodType) {
        this.contactMethodType = contactMethodType;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {

        dest.writeString (contactMethodType);
        dest.writeString (serverId);
        dest.writeByte (( byte ) (isDeleted ? 1 : 0));
    }

}
