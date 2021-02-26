package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Location extends SugarRecord implements Parcelable {

    public static final  Creator < Location > CREATOR            = new Creator < Location > () {
        @Override
        public Location createFromParcel (Parcel in) {
            return new Location (in);
        }

        @Override
        public Location[] newArray (int size) {
            return new Location[ size ];
        }
    };
    private static final long                 serialVersionUID   = 1L;
    private              String               name;
    private              Boolean              isUsageSite        = Boolean.FALSE;
    private              Boolean              isMobileSite       = Boolean.FALSE;
    private              Boolean              isVenue            = Boolean.FALSE;
    private              boolean              isProcessingSite   = false;
    private              boolean              isDistributionSite = false;
    private              boolean              isTestingSite      = false;
    private              boolean              isReferralSite     = false;
    private              Boolean              isDeleted;
    private              String               divisionLevel1;
    private              String               divisionLevel2;
    private              String               divisionLevel3;
    private              String               notes;
    private              String               serverId;

    public Location (String name , Boolean isDeleted , String serverId) {
        this.name      = name;
        this.isDeleted = isDeleted;
        this.serverId  = serverId;
    }

    public Location () {
    }

    public Location (String name , Boolean isUsageSite , Boolean isVenue) {
        this.name        = name;
        this.isUsageSite = isUsageSite;
        this.isVenue     = isVenue;
    }

    protected Location (Parcel in) {
        name = in.readString ();
        byte tmpIsUsageSite = in.readByte ();
        isUsageSite = tmpIsUsageSite == 0 ? null : tmpIsUsageSite == 1;
        byte tmpIsMobileSite = in.readByte ();
        isMobileSite = tmpIsMobileSite == 0 ? null : tmpIsMobileSite == 1;
        byte tmpIsVenue = in.readByte ();
        isVenue            = tmpIsVenue == 0 ? null : tmpIsVenue == 1;
        isProcessingSite   = in.readByte () != 0;
        isDistributionSite = in.readByte () != 0;
        isTestingSite      = in.readByte () != 0;
        isReferralSite     = in.readByte () != 0;
        byte tmpIsDeleted = in.readByte ();
        isDeleted      = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        divisionLevel1 = in.readString ();
        divisionLevel2 = in.readString ();
        divisionLevel3 = in.readString ();
        notes          = in.readString ();
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public void copy (Location location) {
        this.name               = location.name;
        this.isVenue            = location.isVenue;
        this.isMobileSite       = location.isMobileSite;
        this.isUsageSite        = location.isUsageSite;
        this.isProcessingSite   = location.isProcessingSite;
        this.isDistributionSite = location.isDistributionSite;
        this.isTestingSite      = location.isTestingSite;
        this.isReferralSite     = location.isReferralSite;
        this.isDeleted          = location.isDeleted;
        this.notes              = location.notes;
        this.divisionLevel1     = location.divisionLevel1;
        this.divisionLevel2     = location.divisionLevel2;
        this.divisionLevel3     = location.divisionLevel3;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Boolean getIsMobileSite () {
        return isMobileSite;
    }

    public void setIsMobileSite (Boolean mobileSite) {
        isMobileSite = mobileSite;
    }

    public Boolean getIsUsageSite () {
        return isUsageSite;
    }

    public void setIsUsageSite (Boolean usageSite) {
        isUsageSite = usageSite;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

    public Boolean getIsDeleted () {
        return isDeleted;
    }

    public void setIsDeleted (Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsVenue () {
        return isVenue;
    }

    public void setIsVenue (Boolean isVenue) {
        this.isVenue = isVenue;
    }

    public boolean getIsProcessingSite () {
        return isProcessingSite;
    }

    public void setIsProcessingSite (boolean isProcessingSite) {
        this.isProcessingSite = isProcessingSite;
    }

    public boolean getIsDistributionSite () {
        return isDistributionSite;
    }

    public void setIsDistributionSite (boolean isDistributionSite) {
        this.isDistributionSite = isDistributionSite;
    }

    public boolean getIsTestingSite () {
        return isTestingSite;
    }

    public void setIsTestingSite (boolean isTestingSite) {
        this.isTestingSite = isTestingSite;
    }


    public boolean getIsReferralSite () {
        return isReferralSite;
    }

    public void setIsReferralSite (boolean isReferralSite) {
        this.isReferralSite = isReferralSite;
    }

    public String getDivisionLevel1 () {
        return divisionLevel1;
    }

    public void setDivisionLevel1 (String divisionLevel1) {
        this.divisionLevel1 = divisionLevel1;
    }

    public String getDivisionLevel2 () {
        return divisionLevel2;
    }

    public void setDivisionLevel2 (String divisionLevel2) {
        this.divisionLevel2 = divisionLevel2;
    }

    public String getDivisionLevel3 () {
        return divisionLevel3;
    }

    public void setDivisionLevel3 (String divisionLevel3) {
        this.divisionLevel3 = divisionLevel3;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeString (name);
        dest.writeByte (( byte ) (isUsageSite == null ? 0 : isUsageSite ? 1 : 2));
        dest.writeByte (( byte ) (isMobileSite == null ? 0 : isMobileSite ? 1 : 2));
        dest.writeByte (( byte ) (isVenue == null ? 0 : isVenue ? 1 : 2));
        dest.writeByte (( byte ) (isProcessingSite ? 1 : 0));
        dest.writeByte (( byte ) (isDistributionSite ? 1 : 0));
        dest.writeByte (( byte ) (isTestingSite ? 1 : 0));
        dest.writeByte (( byte ) (isReferralSite ? 1 : 0));
        dest.writeByte (( byte ) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeString (divisionLevel1);
        dest.writeString (divisionLevel2);
        dest.writeString (divisionLevel3);
        dest.writeString (notes);
    }
}
