package real_deal.bsis.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Entity that contains the Donor's mobile, home and work telephone number and email address.
 */

public class Contact extends SugarRecord implements Parcelable {


    public static final Creator < Contact > CREATOR = new Creator < Contact > () {
        @Override
        public Contact createFromParcel (Parcel in) {
            return new Contact (in);
        }

        @Override
        public Contact[] newArray (int size) {
            return new Contact[ size ];
        }
    };
    private static final long   serialVersionUID = 1L;
    public               long   id;
    private              String mobileNumber;
    private              String homeNumber;
    private              String workNumber;
    private              String email;
    private              String serverId;

    public Contact (String serverId , String mobileNumber , String homeNumber , String workNumber , String email) {
        this.mobileNumber = mobileNumber;
        this.homeNumber   = homeNumber;
        this.workNumber   = workNumber;
        this.email        = email;
        this.serverId     = serverId;
    }


    public Contact () {
    }

    public Contact (Long id , String mobileNumber , String homeNumber , String workNumber , String email) {
        this.id           = id;
        this.mobileNumber = mobileNumber;
        this.homeNumber   = homeNumber;
        this.workNumber   = workNumber;
        this.email        = email;
    }


    public Contact (String mobileNumber , String homeNumber , String workNumber , String email) {
        this.mobileNumber = mobileNumber;
        this.homeNumber   = homeNumber;
        this.workNumber   = workNumber;
        this.email        = email;
    }

    protected Contact (Parcel in) {
        if (in.readByte () == 0) {
            id = 0;
        } else {
            id = in.readLong ();
        }
        mobileNumber = in.readString ();
        homeNumber   = in.readString ();
        workNumber   = in.readString ();
        email        = in.readString ();
        serverId     = in.readString ();
    }

    public void setId (long id) {
        this.id = id;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public String getMobileNumber () {
        return mobileNumber;
    }

    public void setMobileNumber (String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHomeNumber () {
        return homeNumber;
    }

    public void setHomeNumber (String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getWorkNumber () {
        return workNumber;
    }

    public void setWorkNumber (String workNumber) {
        this.workNumber = workNumber;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {

        if (id == 0) {
            dest.writeByte (( byte ) 0);
        } else {
            dest.writeByte (( byte ) 1);
            dest.writeLong (id);
        }
        dest.writeString (mobileNumber);
        dest.writeString (homeNumber);
        dest.writeString (workNumber);
        dest.writeString (email);
        dest.writeString (serverId);
    }


}
