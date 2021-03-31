package application.model;

import application.controller.Main;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ComputerPartsModel {

    HashMap<String, Member> memberMap = new HashMap<>();

    HashMap<String, MailingList> mailingListMap = new HashMap<>();

    HashMap<String, HashSet<Message>> messageMap;

    public void addMessage(String mailingLists, String senderUsername,  LocalDate date, LocalTime time, String priority, String messageTitle, String messageBody) {
        HashSet<Message> groupSets;

        Message m = new Message(mailingLists, senderUsername, date, time, priority, messageTitle, messageBody);

        if(messageMap.containsKey(mailingLists)){
            // if the key has already been used,
            // grab the set and add the value to it
            groupSets = messageMap.get(mailingLists);
            groupSets.add(m);
        } else {
            groupSets = new HashSet<>();
            groupSets.add(m);
            messageMap.put(mailingLists, groupSets);
        }
    }

    public boolean deleteMessage(String messageTitle) {
        if (messageMap != null) {
            messageMap.remove(messageTitle);
        return true;
        }
        return false;
    }

   public ComputerPartsModel() throws Exception {
       messageMap = new HashMap<>();
       loadMessages();
       loadMailingList();
       loadMembers();
   }

    public Map<String, HashSet<Message>> getMessage() {
        if (messageMap != null) {
            return messageMap;
        }
        return null;
    }

   // mailingListMap.get(group).getMemberList().add(memberList);

    public HashSet<Message> getMessage(String priority, String group) {


//new hash Set with messages that have matching priority
    HashSet<Message> mSet = new HashSet<>();

        for(Map.Entry<String, HashSet<Message>> m : messageMap.entrySet()) {

            if (m.getValue().equals(priority) && m.getValue().equals("All")) {

                mSet.add((Message) m);

            }

            else if(m.getValue().equals(group) && m.getValue().equals("All")){
                mSet.add((Message) m);
            }

            else if(m.getValue().equals(priority) && m.getValue().equals(group)){
                mSet.add((Message) m);
            }

        }
        return mSet;
    }

    /*    for(Message m : messageMap.get(group)) {

            if (m.getPriority().equals(priority) && m.getMailingLists().equals("All")) {

            mSet.add(m);

            }

            else if(m.getMailingLists().equals(group) && m.getPriority().equals("All")){
                mSet.add(m);
            }

            else if(m.getPriority().equals(priority) && m.getMailingLists().equals(group)){
                mSet.add(m);
            }

        }
            return mSet;
    }*/

    //create ComputerPartsModel constructor that calls loadMessages instantly upon the running of the program

    public String getMessageDetails() {
                    return messageMap.toString();
    }


    public Set<String> getMailingListKeys() {
        if (mailingListMap != null) {
            return mailingListMap.keySet();
        }
        return null;
    }

    public Set<String> getMemberListKeys() {
        if (memberMap != null) {
            return memberMap.keySet();
        }
        return null;
    }

    public Set<String> getMessageKeys() {
        if (messageMap != null) {
            return messageMap.keySet();
        }
        return null;
    }

    public boolean addMember(String email, Member m) {
        if (memberMap != null) {
            memberMap.put(email, m);
            return true;
        }
        memberMap = new HashMap<>();
        memberMap.put(email, m);
        return false;
    }

    public boolean deleteMember(String email) {
        if (memberMap != null) {
            memberMap.remove(email);
            return true;
        }
        return false;
    }

    public boolean getMemberUsername(String username) {
        if (memberMap != null) {
            for (Map.Entry<String, Member> member : memberMap.entrySet()) {
                if (member.getValue().getUsername().equals(username)) {
                    return true;
                }
            }
        }
            return false;
    }

    public String getMemberDetails(String email) {
        if (memberMap != null) {
            for (Map.Entry<String, Member> member : memberMap.entrySet()) {
                if (member.getValue().getEmail().equals(email)) {
                  return member.toString();
                }
            }
        }
        return null;
    }

    public Set<String> listAllEmails() {
        if (memberMap != null) {
            return memberMap.keySet();
        }
        return null;
    }

    public Set listAllEmailsPerGroup(String groupName){
        for (Map.Entry<String, MailingList> member : mailingListMap.entrySet()) {
            if (member.getValue().getGroupName().equals(groupName)) {
                return member.getValue().getMemberList();
            }
        }
        return null;
    }

    public boolean addMailingList(String groupName, String initialUser) {
        if (mailingListMap != null) {
            Set<String> groupSets = new HashSet<>();

            groupSets.add(initialUser);

            MailingList mail = new MailingList(groupName, groupSets);

            mailingListMap.put(groupName, mail);

            return true;
        }
        return false;
    }

    public boolean registerForMailingList(String group, String memberList) {
        if (mailingListMap != null) {
            mailingListMap.get(group).getMemberList().add(memberList);
            return true;
        }
        return false;
    }

    public boolean userRegisterForMailingList( String group) {
        if (Main.getMember() != null && memberMap != null) {
            memberMap.get(Main.getMember().getEmail()).getGroupList().add(group);
            return true;
        }
        return false;
    }

    public boolean deleteMailingList(String groupName) {
        if (mailingListMap != null) {
            mailingListMap.remove(groupName);
            return true;
        }
        return false;
    }

    public boolean getMailingList(String groupName) {
        if (mailingListMap != null) {
            mailingListMap.get(groupName);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateMailingName(String name, MailingList m) {
        if (mailingListMap != null) {
            mailingListMap.replace(name, m);
            return true;
        }
        return false;
    }

    public boolean updateMailingListMembership(String groupName, Member m) {
        if (mailingListMap != null) {
            mailingListMap.replace(groupName, mailingListMap.get(m));
            return true;
        }
        return false;
    }

    //Method to save Group objects to
    public void saveMailingList() throws Exception {
        try {
            XStream xstream = new XStream(new DomDriver());

            ObjectOutputStream out = xstream.createObjectOutputStream

                    (new FileWriter("src/dataFiles/mailingList.xml"));

            out.writeObject(mailingListMap);

            out.close();
        } catch (Exception e) {
    System.out.println("Error writing to mailingList file");
        }
    }

    //Method to load Group objects from
    public void loadMailingList() {
        try {
            XStream xstream = new XStream(new DomDriver());
            ObjectInputStream is = xstream.createObjectInputStream(new FileReader("src/dataFiles/mailingList.xml"));
            mailingListMap = (HashMap<String, MailingList>) is.readObject();
            is.close();
        } catch (FileNotFoundException e) {
            HashMap<String, MailingList> mailingListMap = new HashMap<>();

            System.out.println("Mailing list file not found");
        } catch (Exception ef){
            System.out.println("Error accessing mailing list file");
        }
    }


    //Method to save Message objects to messages.xml
    public void saveMessages() throws Exception {
        try {
            XStream xstream = new XStream(new DomDriver());

            ObjectOutputStream out = xstream.createObjectOutputStream

                    (new FileWriter("src/dataFiles/messages.xml"));

            out.writeObject(messageMap);

            out.close();
        } catch (Exception e) {
        System.out.println("Error writing to messages file");
        }
    }

    //Method to load Message objects from
    public void loadMessages() {
        try {
            XStream xstream = new XStream(new DomDriver());
            ObjectInputStream is = xstream.createObjectInputStream(new FileReader("src/dataFiles/messages.xml"));
            messageMap = (HashMap<String, HashSet<Message>>) is.readObject();
            is.close();
        } catch (FileNotFoundException e) {
            HashMap<String, HashSet<Message>> messageMap = new HashMap<>();

            System.out.println("Messages file not found");
        } catch (Exception ef) {
            System.out.println("Error accessing messages file");
        }
    }


    public void saveMember() throws Exception {
        try {
            XStream xstream = new XStream(new DomDriver());

            ObjectOutputStream out = xstream.createObjectOutputStream

                    (new FileWriter("src/dataFiles/members.xml"));

            out.writeObject(memberMap);

            out.close();
        } catch (Exception e) {
            System.out.println("Error writing to members file");
        }
    }

    public void loadMembers() throws Exception {
        try {
            XStream xstream = new XStream(new DomDriver());
            ObjectInputStream is = xstream.createObjectInputStream(new FileReader("src/dataFiles/members.xml"));
            memberMap = (HashMap<String, Member>) is.readObject();
            is.close();
        }  catch (FileNotFoundException e) {
            HashMap<String, Member> memberMap = new HashMap<>();

            System.out.println("Members file not found");
        } catch (Exception ef) {
            System.out.println("Error accessing members file");
        }
    }


    public void adminPage(){
        Main.set_pane(1);
    }

    public void userPage(){
        Main.set_pane(2);
    }

}
