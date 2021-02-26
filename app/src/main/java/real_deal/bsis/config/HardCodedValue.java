package real_deal.bsis.config;

import com.orm.query.Select;

import real_deal.bsis.model.ResourceLocation;

public class HardCodedValue {

    //  public String URL = "http://192.168.43.32:8080/";
    //public String URL = "http://192.168.57.2:8080/";
    //  public String URL = "http://192.168.1.111:8080/";

    public String EMPTY_MESSAGE = "This cannot be empty";

    public String LOGIN_INVALID = "Invalid UserName or Password";

    public String NETWORK_PROBLEM = "Network problem cannot login right now try again later";

    public String AUTHORIZED_FAIL =
            "You are not authorized to access this application. Contact system administrator";
    public String INVALID_INPUT   = "INVALID INPUT";

    public int NUMBER_OF_DAYS_FOR_CHUNK_DOWNLOAD = 3;
    public int NUMBER_OF_PAGINATION              = 7;


    public String URL () {
        ResourceLocation location = Select.from (ResourceLocation.class).first ();
        String URL = location.getUrl () + ":" + location.getPort ();
        return URL;
    }
}
