package real_deal.bsis.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Address extends SugarRecord implements Parcelable {

    public static final  Creator < Address > CREATOR          = new Creator < Address > () {
        @Override
        public Address createFromParcel (Parcel in) {
            return new Address (in);
        }

        @Override
        public Address[] newArray (int size) {
            return new Address[ size ];
        }
    };
    private static final long                serialVersionUID = 1L;
    private              String              homeAddressLine1;
    private              String              homeAddressLine2;
    private              String              homeAddressCity;
    private              String              homeAddressProvince;
    private              String              homeAddressDistrict;
    private              String              homeAddressCountry;
    private              String              homeAddressState;
    private              String              homeAddressZipcode;
    private              String              workAddressLine1;
    private              String              workAddressLine2;
    private              String              workAddressCity;
    private              String              workAddressProvince;
    private              String              workAddressDistrict;
    private              String              workAddressCountry;
    private              String              workAddressState;
    private              String              workAddressZipcode;
    private              String              postalAddressLine1;
    private              String              postalAddressLine2;
    private              String              postalAddressCity;
    private              String              postalAddressProvince;
    private              String              postalAddressDistrict;
    private              String              postalAddressCountry;
    private              String              postalAddressState;
    private              String              postalAddressZipcode;
    private              String              serverId;

    public Address () {
    }

    public Address (String serverId , String homeAddressLine1 , String homeAddressLine2 , String homeAddressCity , String homeAddressProvince , String homeAddressDistrict , String homeAddressCountry , String homeAddressState , String homeAddressZipcode , String workAddressLine1 , String workAddressLine2 , String workAddressCity , String workAddressProvince , String workAddressDistrict , String workAddressCountry , String workAddressState , String workAddressZipcode , String postalAddressLine1 , String postalAddressLine2 , String postalAddressCity , String postalAddressProvince , String postalAddressDistrict , String postalAddressCountry , String postalAddressState , String postalAddressZipcode) {
        this.homeAddressLine1      = homeAddressLine1;
        this.homeAddressLine2      = homeAddressLine2;
        this.homeAddressCity       = homeAddressCity;
        this.homeAddressProvince   = homeAddressProvince;
        this.homeAddressDistrict   = homeAddressDistrict;
        this.homeAddressCountry    = homeAddressCountry;
        this.homeAddressState      = homeAddressState;
        this.homeAddressZipcode    = homeAddressZipcode;
        this.workAddressLine1      = workAddressLine1;
        this.workAddressLine2      = workAddressLine2;
        this.workAddressCity       = workAddressCity;
        this.workAddressProvince   = workAddressProvince;
        this.workAddressDistrict   = workAddressDistrict;
        this.workAddressCountry    = workAddressCountry;
        this.workAddressState      = workAddressState;
        this.workAddressZipcode    = workAddressZipcode;
        this.postalAddressLine1    = postalAddressLine1;
        this.postalAddressLine2    = postalAddressLine2;
        this.postalAddressCity     = postalAddressCity;
        this.postalAddressProvince = postalAddressProvince;
        this.postalAddressDistrict = postalAddressDistrict;
        this.postalAddressCountry  = postalAddressCountry;
        this.postalAddressState    = postalAddressState;
        this.postalAddressZipcode  = postalAddressZipcode;
        this.serverId              = serverId;
    }

    public Address (String homeAddressCity , String homeAddressCountry , String homeAddressZipcode , String homeAddressProvince) {
        this.homeAddressCity     = homeAddressCity;
        this.homeAddressCountry  = homeAddressCountry;
        this.homeAddressZipcode  = homeAddressZipcode;
        this.homeAddressProvince = homeAddressProvince;
    }

    protected Address (Parcel in) {
        homeAddressLine1      = in.readString ();
        homeAddressLine2      = in.readString ();
        homeAddressCity       = in.readString ();
        homeAddressProvince   = in.readString ();
        homeAddressDistrict   = in.readString ();
        homeAddressCountry    = in.readString ();
        homeAddressState      = in.readString ();
        homeAddressZipcode    = in.readString ();
        workAddressLine1      = in.readString ();
        workAddressLine2      = in.readString ();
        workAddressCity       = in.readString ();
        workAddressProvince   = in.readString ();
        workAddressDistrict   = in.readString ();
        workAddressCountry    = in.readString ();
        workAddressState      = in.readString ();
        workAddressZipcode    = in.readString ();
        postalAddressLine1    = in.readString ();
        postalAddressLine2    = in.readString ();
        postalAddressCity     = in.readString ();
        postalAddressProvince = in.readString ();
        postalAddressDistrict = in.readString ();
        postalAddressCountry  = in.readString ();
        postalAddressState    = in.readString ();
        postalAddressZipcode  = in.readString ();
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public String getHomeAddressLine1 () {
        return homeAddressLine1;
    }

    public void setHomeAddressLine1 (String homeAddressLine1) {
        this.homeAddressLine1 = homeAddressLine1;
    }

    public String getHomeAddressLine2 () {
        return homeAddressLine2;
    }

    public void setHomeAddressLine2 (String homeAddressLine2) {
        this.homeAddressLine2 = homeAddressLine2;
    }

    public String getHomeAddressCity () {
        return homeAddressCity;
    }

    public void setHomeAddressCity (String homeAddressCity) {
        this.homeAddressCity = homeAddressCity;
    }

    public String getHomeAddressProvince () {
        return homeAddressProvince;
    }

    public void setHomeAddressProvince (String homeAddressProvince) {
        this.homeAddressProvince = homeAddressProvince;
    }

    public String getHomeAddressDistrict () {
        return homeAddressDistrict;
    }

    public void setHomeAddressDistrict (String homeAddressDistrict) {
        this.homeAddressDistrict = homeAddressDistrict;
    }

    public String getHomeAddressCountry () {
        return homeAddressCountry;
    }

    public void setHomeAddressCountry (String homeAddressCountry) {
        this.homeAddressCountry = homeAddressCountry;
    }

    public String getHomeAddressState () {
        return homeAddressState;
    }

    public void setHomeAddressState (String homeAddressState) {
        this.homeAddressState = homeAddressState;
    }

    public String getHomeAddressZipcode () {
        return homeAddressZipcode;
    }

    public void setHomeAddressZipcode (String homeAddressZipcode) {
        this.homeAddressZipcode = homeAddressZipcode;
    }

    public String getPostalAddressLine1 () {
        return postalAddressLine1;
    }

    public void setPostalAddressLine1 (String postalAddressLine1) {
        this.postalAddressLine1 = postalAddressLine1;
    }

    public String getPostalAddressLine2 () {
        return postalAddressLine2;
    }

    public void setPostalAddressLine2 (String postalAddressLine2) {
        this.postalAddressLine2 = postalAddressLine2;
    }


    public String getWorkAddressCity () {
        return workAddressCity;
    }

    public void setWorkAddressCity (String workAddressCity) {
        this.workAddressCity = workAddressCity;
    }

    public String getWorkAddressProvince () {
        return workAddressProvince;
    }

    public void setWorkAddressProvince (String workAddressProvince) {
        this.workAddressProvince = workAddressProvince;
    }

    public String getWorkAddressDistrict () {
        return workAddressDistrict;
    }

    public void setWorkAddressDistrict (String workAddressDistrict) {
        this.workAddressDistrict = workAddressDistrict;
    }

    public String getWorkAddressCountry () {
        return workAddressCountry;
    }

    public void setWorkAddressCountry (String workAddressCountry) {
        this.workAddressCountry = workAddressCountry;
    }

    public String getWorkAddressState () {
        return workAddressState;
    }

    public void setWorkAddressState (String workAddressState) {
        this.workAddressState = workAddressState;
    }

    public String getWorkAddressZipcode () {
        return workAddressZipcode;
    }

    public void setWorkAddressZipcode (String workAddressZipcode) {
        this.workAddressZipcode = workAddressZipcode;
    }

    public String getWorkAddressLine1 () {
        return workAddressLine1;
    }

    public void setWorkAddressLine1 (String workAddressLine1) {
        this.workAddressLine1 = workAddressLine1;
    }

    public String getWorkAddressLine2 () {
        return workAddressLine2;
    }

    public void setWorkAddressLine2 (String workAddressLine2) {
        this.workAddressLine2 = workAddressLine2;
    }


    public String getPostalAddressCity () {
        return postalAddressCity;
    }

    public void setPostalAddressCity (String postalAddressCity) {
        this.postalAddressCity = postalAddressCity;
    }

    public String getPostalAddressProvince () {
        return postalAddressProvince;
    }

    public void setPostalAddressProvince (String postalAddressProvince) {
        this.postalAddressProvince = postalAddressProvince;
    }

    public String getPostalAddressDistrict () {
        return postalAddressDistrict;
    }

    public void setPostalAddressDistrict (String postalAddressDistrict) {
        this.postalAddressDistrict = postalAddressDistrict;
    }

    public String getPostalAddressCountry () {
        return postalAddressCountry;
    }

    public void setPostalAddressCountry (String postalAddressCountry) {
        this.postalAddressCountry = postalAddressCountry;
    }

    public String getPostalAddressState () {
        return postalAddressState;
    }

    public void setPostalAddressState (String postalAddressState) {
        this.postalAddressState = postalAddressState;
    }

    public String getPostalAddressZipcode () {
        return postalAddressZipcode;
    }

    public void setPostalAddressZipcode (String postalAddressZipcode) {
        this.postalAddressZipcode = postalAddressZipcode;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeString (homeAddressLine1);
        dest.writeString (homeAddressLine2);
        dest.writeString (homeAddressCity);
        dest.writeString (homeAddressProvince);
        dest.writeString (homeAddressDistrict);
        dest.writeString (homeAddressCountry);
        dest.writeString (homeAddressState);
        dest.writeString (homeAddressZipcode);
        dest.writeString (workAddressLine1);
        dest.writeString (workAddressLine2);
        dest.writeString (workAddressCity);
        dest.writeString (workAddressProvince);
        dest.writeString (workAddressDistrict);
        dest.writeString (workAddressCountry);
        dest.writeString (workAddressState);
        dest.writeString (workAddressZipcode);
        dest.writeString (postalAddressLine1);
        dest.writeString (postalAddressLine2);
        dest.writeString (postalAddressCity);
        dest.writeString (postalAddressProvince);
        dest.writeString (postalAddressDistrict);
        dest.writeString (postalAddressCountry);
        dest.writeString (postalAddressState);
        dest.writeString (postalAddressZipcode);
    }
}
