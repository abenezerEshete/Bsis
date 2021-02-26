package real_deal.bsis.model;

import com.orm.SugarRecord;

public class ResourceLocation extends SugarRecord {

    String url;
    String port;

    public ResourceLocation () {
    }

    public ResourceLocation (String url , String port) {
        this.url  = url;
        this.port = port;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getPort () {
        return port;
    }

    public void setPort (String port) {
        this.port = port;
    }
}
