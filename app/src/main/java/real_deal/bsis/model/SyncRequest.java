package real_deal.bsis.model;

import com.orm.SugarRecord;

public class SyncRequest extends SugarRecord {

    Long   Id;
    String apiMethodName;
    String httpMethod;
    String payLoad;

    public SyncRequest () {
    }

    public SyncRequest (String apiMethodName , String httpMethod , String payLoad) {
        this.apiMethodName = apiMethodName;
        this.httpMethod    = httpMethod;
        this.payLoad       = payLoad;
    }

    @Override
    public Long getId () {
        return Id;
    }

    @Override
    public void setId (Long id) {
        Id = id;
    }

    public String getApiMethodName () {
        return apiMethodName;
    }

    public void setApiMethodName (String apiMethodName) {
        this.apiMethodName = apiMethodName;
    }

    public String getHttpMethod () {
        return httpMethod;
    }

    public void setHttpMethod (String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPayLoad () {
        return payLoad;
    }

    public void setPayLoad (String payLoad) {
        this.payLoad = payLoad;
    }
}
