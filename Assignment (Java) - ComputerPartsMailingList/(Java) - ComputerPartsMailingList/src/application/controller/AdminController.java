package application.controller;

import application.model.ComputerPartsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class AdminController implements Initializable {
    protected ComputerPartsModel computerParts;

    ObservableList<String> mailingList;

    @FXML
    private ChoiceBox<String> txtMailList;

    @FXML
    private TextField txtMailingListName;

    @FXML
    private TextField txtMemberEmail;

    @FXML
    private TextArea txtFeedback;

    public void removeMailingList() throws Exception {
        if (computerParts.getMailingListKeys().contains(txtMailingListName.getText())) {
            computerParts.deleteMailingList(txtMailingListName.getText());

            computerParts.saveMailingList();

            txtFeedback.setText("Mailing List deleted");
        } else {
            txtFeedback.setText("Enter a valid mailing list name to delete a list");
        }
    }

    public void removeMember() throws Exception {
        if (computerParts.getMemberListKeys().contains(txtMemberEmail.getText())) {
            computerParts.deleteMember(txtMemberEmail.getText());
            computerParts.saveMember();
            txtFeedback.setText("Member deleted");
        } else {
            txtFeedback.setText("Enter a valid user email to delete a user");
        }
    }

public boolean addMailingList() throws Exception {
    String name = txtMailingListName.getText();
    String memberList =  txtMemberEmail.getText();

    if(computerParts.getMemberListKeys().contains(txtMemberEmail.getText())) {

        computerParts.addMailingList(name, memberList);

        computerParts.saveMailingList();
        txtFeedback.setText("Mailing List Added");
        return true;
    } else if (computerParts.getMailingListKeys().contains(txtMailingListName.getText())) {
        txtFeedback.setText("No user with that email has been registered or A mailing list already exists with that name - Please enter a valid email address and mailing list ");
    }
    return false;
    }

    public boolean registerMemberForList() throws Exception {

        String groupName = txtMailList.getValue();

        String memberList = txtMemberEmail.getText();

        if (computerParts.getMailingListKeys().contains(groupName) && computerParts.getMemberListKeys().contains(memberList)) {

            computerParts.registerForMailingList(groupName, memberList);

            computerParts.saveMailingList();
            txtFeedback.setText("Member Registered");
            return true;

        } else {
        txtFeedback.setText("No member exists for that email or no list has been picked - Please enter a valid email address and mailing list");
        }
        return false;
    }

    public boolean listAllGroups(){
        txtFeedback.setText(String.valueOf(computerParts.getMailingListKeys()));
        return true;
    }

    public boolean loadEmailsFromList() {
        txtFeedback.setText(String.valueOf(computerParts.listAllEmails()));
        return true;
    }

    public boolean loadEmailsFromAList(){
        String groupName = txtMailList.getValue();
        txtFeedback.setText(String.valueOf(computerParts.listAllEmailsPerGroup(groupName)));
        return true;
    }

    public boolean listMemberDetails() {
        String email = txtMemberEmail.getText();
        if (computerParts.getMemberListKeys().contains(email)) {
            txtFeedback.setText(String.valueOf(computerParts.getMemberDetails(email)));
            return true;
        } else {
            txtFeedback.setText("Error getting details - No user exists for that email");
        }
        return false;
    }

    public boolean listMessageDetails(){
        txtFeedback.setText(computerParts.getMessageDetails());
        return true;
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            computerParts = new ComputerPartsModel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mailingList = FXCollections.observableArrayList(computerParts.getMailingListKeys());
        txtMailList.setItems(mailingList);
    }
}
