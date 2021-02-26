package real_deal.bsis.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class PackType extends SugarRecord implements Parcelable {

    public static final  Creator < PackType > CREATOR            = new Creator < PackType > () {
        @Override
        public PackType createFromParcel (Parcel in) {
            return new PackType (in);
        }

        @Override
        public PackType[] newArray (int size) {
            return new PackType[ size ];
        }
    };
    private static final long                 serialVersionUID   = 1L;
    private              String               packType;
    private              Boolean              canSplit;
    private              Boolean              canPool;
    private              Boolean              isDeleted;
    private              String               componentType;
    private              Boolean              countAsDonation;
    private              Boolean              testSampleProduced = Boolean.TRUE;
    private              String               serverId;
    private              Integer              periodBetweenDonations;
    private              Integer              maxWeight;
    private              Integer              minWeight;
    private              Integer              lowVolumeWeight;

    public PackType (String serverId , String packType , Boolean isDeleted) {
        this.packType  = packType;
        this.isDeleted = isDeleted;
        this.serverId  = serverId;
    }

    public PackType () {
    }

    public PackType (String packType , Boolean canSplit , Boolean canPool) {
        this.packType = packType;
        this.canSplit = canSplit;
        this.canPool  = canPool;
    }

    protected PackType (Parcel in) {
        packType = in.readString ();
        byte tmpCanSplit = in.readByte ();
        canSplit = tmpCanSplit == 0 ? null : tmpCanSplit == 1;
        byte tmpCanPool = in.readByte ();
        canPool = tmpCanPool == 0 ? null : tmpCanPool == 1;
        byte tmpIsDeleted = in.readByte ();
        isDeleted     = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        componentType = in.readString ();
        byte tmpCountAsDonation = in.readByte ();
        countAsDonation = tmpCountAsDonation == 0 ? null : tmpCountAsDonation == 1;
        byte tmpTestSampleProduced = in.readByte ();
        testSampleProduced = tmpTestSampleProduced == 0 ? null : tmpTestSampleProduced == 1;
        if (in.readByte () == 0) {
            periodBetweenDonations = null;
        } else {
            periodBetweenDonations = in.readInt ();
        }
        if (in.readByte () == 0) {
            maxWeight = null;
        } else {
            maxWeight = in.readInt ();
        }
        if (in.readByte () == 0) {
            minWeight = null;
        } else {
            minWeight = in.readInt ();
        }
        if (in.readByte () == 0) {
            lowVolumeWeight = null;
        } else {
            lowVolumeWeight = in.readInt ();
        }
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    private boolean isValid () {
        return countAsDonation != true || componentType != null;
    }

    public Boolean getDeleted () {
        return isDeleted;
    }

    public void setDeleted (Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getIsDeleted () {
        return isDeleted;
    }

    public void setIsDeleted (Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPackType () {
        return packType;
    }

    public void setPackType (String packType) {
        this.packType = packType;
    }

    public Boolean getCanPool () {
        return canPool;
    }

    public void setCanPool (Boolean canPool) {
        this.canPool = canPool;
    }

    public Boolean getCanSplit () {
        return canSplit;
    }

    public void setCanSplit (Boolean canSplit) {
        this.canSplit = canSplit;
    }

    public String getComponentType () {
        return componentType;
    }

    public void setComponentType (String componentType) {
        this.componentType = componentType;
    }

    public Boolean getCountAsDonation () {
        return countAsDonation;
    }

    public void setCountAsDonation (Boolean countAsDonation) {
        this.countAsDonation = countAsDonation;
    }

    public Boolean getTestSampleProduced () {
        return testSampleProduced;
    }

    public void setTestSampleProduced (Boolean testSampleProduced) {
        this.testSampleProduced = testSampleProduced;
    }

    public Integer getPeriodBetweenDonations () {
        return periodBetweenDonations;
    }

    public void setPeriodBetweenDonations (Integer periodBetweenDonations) {
        this.periodBetweenDonations = periodBetweenDonations;
    }

    public Integer getMaxWeight () {
        return maxWeight;
    }

    public void setMaxWeight (Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMinWeight () {
        return minWeight;
    }

    public void setMinWeight (Integer minWeight) {
        this.minWeight = minWeight;
    }

    public Integer getLowVolumeWeight () {
        return lowVolumeWeight;
    }

    public void setLowVolumeWeight (Integer lowVolumeWeight) {
        this.lowVolumeWeight = lowVolumeWeight;
    }

    public void copy (PackType packType) {
        this.packType               = packType.getPackType ();
        this.componentType          = packType.getComponentType ();
        this.periodBetweenDonations = packType.getPeriodBetweenDonations ();
        this.countAsDonation        = packType.getCountAsDonation ();
        this.isDeleted              = packType.getIsDeleted ();
        this.testSampleProduced     = packType.getTestSampleProduced ();
        this.minWeight              = packType.getMinWeight ();
        this.maxWeight              = packType.getMaxWeight ();
        this.lowVolumeWeight        = packType.getLowVolumeWeight ();
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest , int flags) {
        dest.writeString (packType);
        dest.writeByte (( byte ) (canSplit == null ? 0 : canSplit ? 1 : 2));
        dest.writeByte (( byte ) (canPool == null ? 0 : canPool ? 1 : 2));
        dest.writeByte (( byte ) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeString (componentType);
        dest.writeByte (( byte ) (countAsDonation == null ? 0 : countAsDonation ? 1 : 2));
        dest.writeByte (( byte ) (testSampleProduced == null ? 0 : testSampleProduced ? 1 : 2));
        if (periodBetweenDonations == null) {
            dest.writeByte (( byte ) 0);
        } else {
            dest.writeByte (( byte ) 1);
            dest.writeInt (periodBetweenDonations);
        }
        if (maxWeight == null) {
            dest.writeByte (( byte ) 0);
        } else {
            dest.writeByte (( byte ) 1);
            dest.writeInt (maxWeight);
        }
        if (minWeight == null) {
            dest.writeByte (( byte ) 0);
        } else {
            dest.writeByte (( byte ) 1);
            dest.writeInt (minWeight);
        }
        if (lowVolumeWeight == null) {
            dest.writeByte (( byte ) 0);
        } else {
            dest.writeByte (( byte ) 1);
            dest.writeInt (lowVolumeWeight);
        }
    }
}
