package application.controller;

import application.model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class messageViewController {

    @FXML private Label senderUsername;

    @FXML private Label title;

    @FXML private Label date;

    @FXML private Label time;

    @FXML private Label priority;

    @FXML private Label mailList;

    @FXML private TextArea body;

    @FXML private Button returnToUserViewButton;

    /**
     * This method accepts a person to initialize the view
     * @param message
     */
    public void initData(Message message)
    {
        senderUsername.setText(""+message.getSenderUsername());
        title.setText(message.getMessageTitle());
        date.setText(String.valueOf(message.getDate()));
        time.setText(""+message.getTime());
        priority.setText(""+message.getPriority());
        mailList.setText(message.getMailingLists());
        body.setText(message.getMessageBody());
    }

    /**
     * When this method is called, it will change the Scene to
     * a TableView example
     */
    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("userpage.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

}
