package real_deal.bsis.model;

import com.orm.SugarRecord;

public class LoginUser extends SugarRecord {

    Long    id;
    String  username;
    String  Password;
    String  newPassword;
    String  oldPassword;
    boolean modifyPassword;
    String  firsname;
    String  lastname;
    String  email;
    String  roles;


    private String serverId;

    public LoginUser () {
    }

    public LoginUser (String serverId , String username , String password , String firsname , String lastname , String email , String roles) {
        this.serverId = serverId;
        this.username = username;
        this.Password = password;
        this.firsname = firsname;
        this.lastname = lastname;
        this.email    = email;
        this.roles    = roles;
    }

    public LoginUser (String serverId , String username , String password , String firsname , String lastname ,
                      String email , String roles , boolean modifyPassword , String newPassword) {
        this.serverId       = serverId;
        this.username       = username;
        Password            = password;
        this.firsname       = firsname;
        this.lastname       = lastname;
        this.email          = email;
        this.roles          = roles;
        this.modifyPassword = modifyPassword;
        this.newPassword    = newPassword;
    }


    public LoginUser (String serverId , String username , String password , String firsname , String lastname , String email) {
        this.serverId = serverId;
        this.username = username;
        Password      = password;
        this.firsname = firsname;
        this.lastname = lastname;
        this.email    = email;

    }

    public String getNewPassword () {
        return newPassword;
    }

    public void setNewPassword (String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean getModifyPassword () {
        return modifyPassword;
    }

    public String getServerId () {
        return serverId;
    }

    public void setServerId (String serverId) {
        this.serverId = serverId;
    }

    public String getOldPassword () {
        return oldPassword;
    }

    public void setOldPassword (String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getRoles () {
        return roles;
    }

    public void setRoles (String roles) {
        this.roles = roles;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return Password;
    }

    public void setPassword (String password) {
        Password = password;
    }

    public String getFirsname () {
        return firsname;
    }

    public void setFirsname (String firsname) {
        this.firsname = firsname;
    }

    public String getLastname () {
        return lastname;
    }

    public void setLastname (String lastname) {
        this.lastname = lastname;
    }

    public boolean isModifyPassword () {
        return modifyPassword;
    }

    public void setModifyPassword (boolean modifyPassword) {
        this.modifyPassword = modifyPassword;
    }
}
