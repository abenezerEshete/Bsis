package real_deal.bsis.model;

import com.orm.SugarRecord;

//@Table(name = "user")
public class User extends SugarRecord {

    //  @Column(name = "username")
    private String username;

    private String password;

    private Boolean passwordReset = false;

    private String firstName;

    private String lastName;


    private String emailId;

    private Boolean isStaff;
    private Boolean isActive;
    private Boolean isAdmin;

    private Boolean isDeleted;

    private String notes;

    private boolean syncStatus;

    public User () {
    }

    public User (String username , String password , Boolean passwordReset , String firstName , String lastName , String emailId , Boolean isStaff , Boolean isActive , Boolean isAdmin , Boolean isDeleted , String notes , boolean syncStatus) {
        this.username      = username;
        this.password      = password;
        this.passwordReset = passwordReset;
        this.firstName     = firstName;
        this.lastName      = lastName;
        this.emailId       = emailId;
        this.isStaff       = isStaff;
        this.isActive      = isActive;
        this.isAdmin       = isAdmin;
        this.isDeleted     = isDeleted;
        this.notes         = notes;
        this.syncStatus    = syncStatus;
    }

    public User (String username) {
        this.username = username;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public Boolean getPasswordReset () {
        return passwordReset;
    }

    public void setPasswordReset (Boolean passwordReset) {
        this.passwordReset = passwordReset;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId () {
        return emailId;
    }

    public void setEmailId (String emailId) {
        this.emailId = emailId;
    }

    public Boolean getStaff () {
        return isStaff;
    }

    public void setStaff (Boolean staff) {
        isStaff = staff;
    }

    public Boolean getActive () {
        return isActive;
    }

    public void setActive (Boolean active) {
        isActive = active;
    }

    public Boolean getAdmin () {
        return isAdmin;
    }

    public void setAdmin (Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getDeleted () {
        return isDeleted;
    }

    public void setDeleted (Boolean deleted) {
        isDeleted = deleted;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

    public boolean isSyncStatus () {
        return syncStatus;
    }

    public void setSyncStatus (boolean syncStatus) {
        this.syncStatus = syncStatus;
    }


}
