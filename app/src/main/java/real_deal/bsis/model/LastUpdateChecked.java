package real_deal.bsis.model;

import com.orm.SugarRecord;

public class LastUpdateChecked extends SugarRecord {


    String lastCheckedDate;

    public LastUpdateChecked (String lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }

    public LastUpdateChecked () {

    }

    public String getLastCheckedDate () {
        return lastCheckedDate;
    }

    public void setLastCheckedDate (String lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
