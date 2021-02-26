package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class AddressType extends SugarRecord implements Parcelable {


    public static final Creator < AddressType > CREATOR = new Creator < AddressType > () {
        @Override
        public AddressType createFromParcel (Parcel in) {
            return new AddressType (in);
        }

        @Override
        public AddressType[] newArray (int size) {
            return new AddressType[ size ];
        }
    };
    private             String                  preferredAddressType;
    private             String                  serverId;

    public AddressType (String serverId , String preferredAddressType) {
        this.preferredAddressType = preferredAddressType;
        this.serverId             = serverId;
    }

    public AddressType (String preferredAddressType) {
        this.preferredAddressType = preferredAddressType;
    }

    protected AddressType (Parcel in) {
        preferredAddressType = in.readString ();
    }

    public AddressType () {

    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public String getPreferredAddressType () {
        return preferredAddressType;
    }

    public void setPreferredAddressType (String preferredAddressType) {
        this.preferredAddressType = preferredAddressType;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeString (preferredAddressType);
    }
}
