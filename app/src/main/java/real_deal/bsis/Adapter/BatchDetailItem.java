package real_deal.bsis.Adapter;

public class BatchDetailItem {

    String DonorNumber;
    String din;
    String packType;
    String donationType;

    public BatchDetailItem () {
    }

    public BatchDetailItem (String donorNumber , String din , String packType , String donationType) {
        DonorNumber       = donorNumber;
        this.din          = din;
        this.packType     = packType;
        this.donationType = donationType;
    }

    public String getDin () {
        return din;
    }

    public void setDin (String din) {
        this.din = din;
    }

    public String getPackType () {
        return packType;
    }

    public void setPackType (String packType) {
        this.packType = packType;
    }

    public String getDonationType () {
        return donationType;
    }

    public void setDonationType (String donationType) {
        this.donationType = donationType;
    }

    public String getDonorNumber () {
        return DonorNumber;
    }

    public void setDonorNumber (String donorNumber) {
        DonorNumber = donorNumber;
    }
}
