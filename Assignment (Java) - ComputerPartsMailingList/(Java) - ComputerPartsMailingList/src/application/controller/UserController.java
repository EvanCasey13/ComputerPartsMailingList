package application.controller;

import application.model.ComputerPartsModel;
import application.model.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class UserController implements Initializable {
    ComputerPartsModel computerParts;

    ObservableList<String> priority = FXCollections.observableArrayList("All", "Normal", "Priority", "Urgent");

    ObservableList<String> mailingList;
    //computerParts.getMailingListKeys()
    //ObservableList<String> mailingList = FXCollections.observableArrayList(Main.getMember().getGroupList());

    @FXML
    private ComboBox<String> comboMailingLists;

    @FXML
    private Button detailedMessageViewButton;  //not using this yet

    @FXML
    private TableView tblView;

    @FXML
    private TableColumn<Map, Message> username;

    @FXML
    private TableColumn<Map, Message> messageTitle;

    @FXML
    private TableColumn<Map, Message> messagePriority;

    @FXML
    private TableColumn<Map, Message> messageBody;

    @FXML
    private TextField txtMessageTitle;

    @FXML
    private TextArea txtMessageBody;

    @FXML
    private Label txtFeedback;

    @FXML
    private ComboBox<String> priorityChoice;

    public void initialize(URL location, ResourceBundle resources) {
        try {
            computerParts = new ComputerPartsModel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mailingList = FXCollections.observableArrayList(computerParts.getMailingListKeys());

        ObservableList<Map<String, HashSet<Message>>> data =
                FXCollections.observableArrayList(computerParts.getMessage());

        username.setCellValueFactory(new MapValueFactory<>("username"));
        messageTitle.setCellValueFactory(new MapValueFactory<>("messageTitle"));
        messagePriority.setCellValueFactory(new MapValueFactory<>(" messagePriority"));
        messageBody.setCellValueFactory(new MapValueFactory<>("messageBody"));

        comboMailingLists.setItems(mailingList);
        comboMailingLists.setValue("All");

        priorityChoice.setItems(priority);
        tblView.setItems(data);

    }

    public void changedMailListCombo(ActionEvent e) throws Exception {
        this.detailedMessageViewButton.setDisable(true); //disable button
        tblView.setItems(FXCollections.observableArrayList(computerParts.getMessage(comboMailingLists.getValue(), priorityChoice.getValue())));
    }

    public void changedPriorityList() {
        this.detailedMessageViewButton.setDisable(true); //disable button
        tblView.setItems(FXCollections.observableArrayList(computerParts.getMessage(comboMailingLists.getValue(), priorityChoice.getValue())));
    }

    public void userClickedOnTable() {
        this.detailedMessageViewButton.setDisable(false);
    }

    /**
     * When this method is called, it will pass the selected Message object to
     * a the detailed view
     */
    public void changeSceneToDetailedView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("messageview.fxml"));
        try {
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);


            //access the controller and call a method
            messageViewController controller = loader.getController();
            Message messageToDisplay = (Message) tblView.getSelectionModel().getSelectedItem(); //cast as Message
            if (messageToDisplay == null) //sometimes no Product will have been selected
                return;

            controller.initData(messageToDisplay);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } catch (IllegalStateException e) {
        System.out.println("Illegal State Exception Caught in detailed view - Location is not set (Select a message to display a detailed view) ");
        }
    }

    public void addMessage() throws Exception {
        String username = Main.getMember().getUsername();
        String mailingList = comboMailingLists.getValue();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String priority = String.valueOf(priorityChoice.getValue());
        String messageTitle = txtMessageTitle.getText();
        String messageBody = txtMessageBody.getText();

        if (computerParts.getMailingListKeys().contains(mailingList) && priority != null) {

            computerParts.addMessage(messageTitle, username, date, time, priority, mailingList, messageBody);
            computerParts.saveMessages();

            txtFeedback.setText("Message sent");
        } else {
            txtFeedback.setText("Pick a mailing list and message priority");
        }
    }


    public void deleteMessage() throws Exception {
        if (computerParts.getMessageKeys().contains(txtMessageTitle.getText())) {

            computerParts.deleteMessage(txtMessageTitle.getText());

            computerParts.saveMessages();

            txtFeedback.setText("Message Deleted");
        } else {
            txtFeedback.setText("No message has that name - try again");
        }
    }

    public boolean listMessageDetails(){
       txtMessageBody.setText(computerParts.getMessageDetails());
        return true;
    }

    public boolean registerForList() throws Exception {

        String mailingList = comboMailingLists.getValue();
        if (computerParts.getMailingListKeys().contains(mailingList)) {
            computerParts.userRegisterForMailingList(mailingList);
            computerParts.saveMember();
            txtFeedback.setText("Registered for mailing list");
            return true;

        } else {
            txtFeedback.setText("Pick a valid list you wish to register for");
        }
        return false;
    }

}
