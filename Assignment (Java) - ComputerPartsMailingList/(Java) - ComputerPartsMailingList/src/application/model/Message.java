package application.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

public class Message {
    //Variables
    private String senderUsername;
    private String messageTitle;
    private LocalDate date;
    private LocalTime time;
    private String priority;
    private String mailingLists;
    private String messageBody;

    //Default constructor
    public Message(String messageTitle, String senderUsername,  LocalDate date, LocalTime time, String priority, String mailingLists, String messageBody) {
        this.senderUsername = senderUsername;
        this.messageTitle = messageTitle;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.mailingLists = mailingLists;
        this.messageBody = messageBody;
    }

    public Message(String senderUsername, LocalDate date, LocalTime time, String priority, String messageTitle, String messageBody) {
        this.senderUsername = senderUsername;
        this.messageTitle = messageTitle;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.messageBody = messageBody;
    }

    public Message(String mailingLists, String messageTitle) {
        this.messageTitle = messageTitle;
        this.mailingLists = mailingLists;
    }

    public Message() {

    }


    //Getters
    public String getSenderUsername() {
        return senderUsername;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getPriority() {
        return priority;
    }

    public String getMailingLists() {
        return mailingLists;
    }

    public String getMessageBody() {
        return messageBody;
    }

    //Setters
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setMailingLists(String mailingLists) {
        this.mailingLists = mailingLists;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    //toString
    @Override
    public String toString() {
        return  "Messages{" +
                "senderUsername='" + senderUsername +
                ", messageTitle='" + messageTitle +
                ", date=" + date +
                ", time=" + time +
                ", priority='" + priority +
                ", mailingLists='" + mailingLists +
                ", messageBody='" + messageBody + '\n' +
                '}';
    }
}
