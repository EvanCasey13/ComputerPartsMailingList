package application.model;

import java.util.Set;

public class MailingList {
    //Variables
    private String groupName;
    private Set memberList;

    //Default constructor
    public MailingList(String groupName, Set memberList){

        this.groupName = groupName;
        this.memberList =  memberList;

    }

    //Getters
    public String getGroupName() {
        return groupName;
    }

    public Set getMemberList() {
        return memberList;
    }



    //Setters
    public void setMailingListName(String groupName) {
        this.groupName = groupName;
    }

    public void setMemberList(Set memberList) {
        this.memberList = memberList;
    }


    @Override
    public String toString() {
        return "MailingList{" +
                "groupName='" + groupName + '\'' +
                ", memberList='" + memberList + '\'' +
                '}';
    }
}
