package application.controller;

import application.model.ComputerPartsModel;
import application.model.MailingList;
import application.model.Member;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class HomepageController implements Initializable {



    ObservableList<String> mailingLists;
    ObservableList<String> userTypes = FXCollections.observableArrayList("Normal", "Admin");

    protected ComputerPartsModel computerParts;

    //FXML Fields for a Member object
    @FXML
    private TextField txtSignupDate;

    @FXML
    private TextField txtSignupTime;

    @FXML
    private TextField txtMemberEmail;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtMemberEmailLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ChoiceBox txtUsertype;

    @FXML
    private ChoiceBox<String> txtGroupList;

    //Image
    @FXML private ImageView photo;

    @FXML
    private TextArea txtFeedback;

    public void registerMember(ActionEvent e) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mm:ss");

        LocalDate signupDate = java.time.LocalDate.now();
        LocalTime signupTime = java.time.LocalTime.now();
        txtSignupDate.setText(formatter.format(signupDate));
        txtSignupTime.setText(formatterTime.format(signupTime));

        if (txtMemberEmail.getText().length() < 10 || txtUsername.getText().length() < 5) {
            txtFeedback.setText("Email must be greater than ten characters and username must be greater than five characters");

        }else if (computerParts.getMemberListKeys().contains(txtMemberEmail.getText())){
            txtFeedback.setText("That email is already registered to the system");
        }
        else if(computerParts.getMemberUsername(txtUsername.getText())){
            txtFeedback.setText("That user name has already been taken");
        }
        else if (register(txtMemberEmail.getText(), txtUsername.getText(), String.valueOf(txtUsertype.getValue()), String.valueOf(txtGroupList.getValue()), txtPassword.getText(), LocalDate.now(), LocalTime.now())) {

            txtFeedback.setText("Member added");
        }
    }

    private boolean register(String email, String username, String usertype, String groupList, String password, LocalDate signupDate, LocalTime signupTime) {
        HashMap<String, Member> members = null;
        XStream xstream = new XStream(new DomDriver());
        try {
            ObjectInputStream is = xstream.createObjectInputStream(new FileReader("src/dataFiles/members.xml"));
            members = (HashMap<String, Member>) is.readObject();
            is.close();
        } catch (FileNotFoundException e) {
            members = new HashMap<String, Member>();
            txtFeedback.setText("New Member File");
        } catch (Exception e) {
            txtFeedback.setText("Error accessing Member File");
            return false;
        }

        try {
            Set<String> groupSets = new HashSet<>();
            groupSets.add(groupList);
            Member member = new Member(email, username, usertype, groupSets, password, signupDate, signupTime);
            members.put(email, member);
            Main.setMember(member);
            ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("src/dataFiles/members.xml"));
            out.writeObject(members);
            out.close();
        } catch (Exception e) {
            txtFeedback.setText("Error writing to Members File");
            return false;
        }
        return true;
    }

    public void loginMember(ActionEvent e) {
        if (txtMemberEmailLogin.getText().length() < 10) {
            txtFeedback.setText("Please enter your email which is higher than ten characters");
        } else if (login(txtMemberEmailLogin.getText(), txtPassword.getText())) {
            txtFeedback.setText("Login successful");

        } else {
            txtFeedback.setText("Login unsuccessful");
            txtMemberEmailLogin.clear();
            txtPassword.clear();

        }

    }

    public boolean login(String email, String password) {
        HashMap<String, Member> members = null;
        XStream xstream = new XStream(new DomDriver());
        try {
            ObjectInputStream is = xstream.createObjectInputStream(new FileReader("src/dataFiles/members.xml"));
            members = (HashMap<String, Member>) is.readObject();
            is.close();
        } catch (FileNotFoundException e) {
            members = new HashMap<String, Member>();
            txtFeedback.setText("Members File not located");
        } catch (Exception e) {
            txtFeedback.setText("Error accessing Members File");

        }

        Objects.requireNonNull(members).forEach((s, member) -> {
            if (member.getEmail().contains(email) && member.getUsertype().equals("Normal") && member.getPassword().contains(password)) {
                Main.setMember(member);
                Main.set_pane(2);

            } else if (member.getEmail().contains(email) && member.getUsertype().equals("Admin") && member.getPassword().contains(password)) {
                Main.setMember(member);
                Main.set_pane(1);

            }
        });
        return false;
    }

    public void adminButton(){
        computerParts.adminPage();
    }

    public void userButton(){
        computerParts.userPage();
    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            computerParts = new ComputerPartsModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
       mailingLists =  FXCollections.observableArrayList(computerParts.getMailingListKeys());
        txtFeedback.setText("Current Mailing Lists : " + computerParts.getMailingListKeys());

        txtGroupList.setItems(mailingLists);
        txtUsertype.setItems(userTypes);
        txtUsertype.setValue("Normal");
    }

}
