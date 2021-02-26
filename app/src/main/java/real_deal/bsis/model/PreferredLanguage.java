package real_deal.bsis.model;

import com.orm.SugarRecord;

public class PreferredLanguage extends SugarRecord {

    private Long   id;
    private String preferredLanguage;


    private String serverId;

    public PreferredLanguage (String serverId , String preferredLanguage) {
        this.serverId          = serverId;
        this.preferredLanguage = preferredLanguage;
    }

    public PreferredLanguage () {
    }

    public PreferredLanguage (Long id , String preferredLanguage) {
        this.id                = id;
        this.preferredLanguage = preferredLanguage;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getPreferredLanguage () {
        return preferredLanguage;
    }

    public void setPreferredLanguage (String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }
}