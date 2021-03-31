package application.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Member {
    //variables
    private String email;
    private String username;
    private String usertype;
    private Set groupList;
    private String password;
    private LocalDate signupDate;
    private LocalTime signupTime;

    //Default constructor
    public Member(String email, String username, String usertype, Set groupList, String password, LocalDate signupDate, LocalTime signupTime){
        this.email = email;
        this.username = username;
        this.usertype = usertype;
        this.groupList = groupList;
        this.password = password;
        this.signupDate = signupDate;
        this.signupTime = signupTime;
    }

    public Member(String email) {
        this.email = email;
    }

    //Getters
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getUsertype() {
        return usertype;
    }

    public Set getGroupList() {
        return groupList;
    }

    public String getPassword(){ return password;}

    public LocalDate getSignupDate() {
        return signupDate;
    }

    public LocalTime getSignupTime() {
        return signupTime;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setGroupList(Set groupList) {
        this.groupList = groupList;
    }

    public void setPassword(String password){this.password = password;}

    public void setSignupDate(LocalDate signupDate) {
        this.signupDate = signupDate;
    }

    public void setSignupTime(LocalTime signupTime) {
        this.signupTime = signupTime;
    }

    //toString
    @Override
    public String toString() {
        return
                "email : " + email + '\'' +
                ", username : " + username + '\'' +
                ", usertype : " + usertype + '\'' +
                ", groupList : " + groupList + '\'' +
                ", signupDate : " + signupDate +
                ", signupTime : " + signupTime +
                '}';
    }
}
