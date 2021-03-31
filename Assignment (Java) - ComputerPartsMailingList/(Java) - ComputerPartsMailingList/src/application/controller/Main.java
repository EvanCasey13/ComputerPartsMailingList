package application.controller;

import application.model.Member;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class Main extends Application {
    static AnchorPane root;
    private static Member member = null;
    static List<AnchorPane> anchor = new LinkedList<AnchorPane>();
    private static int sceneIndex = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/Anchor.fxml"));

        anchor.add((AnchorPane)FXMLLoader.load(getClass().getResource("../view/homepage.fxml"))); //index 0
        anchor.add((AnchorPane)FXMLLoader.load(getClass().getResource("../view/adminpage.fxml"))); //index 1
        anchor.add((AnchorPane)FXMLLoader.load(getClass().getResource("../view/userpage.fxml"))); //index 2

        root.getChildren().add(anchor.get(0)); // initially set to homepage.fxml
        primaryStage.setTitle("Computer Mailing List");
        primaryStage.setScene(new Scene(root, 601, 375));
        primaryStage.show();
    }

    public static AnchorPane get_pane(int index){
        return anchor.get(index);
    }

    public static void set_pane(int index) {

        for (AnchorPane anchors : anchor) {
            if (anchors.equals(anchor.get(index))) {
                root.getChildren().remove(anchor.get(sceneIndex));  //remove the existing pane from the root
                root.getChildren().add(anchor.get(index));          //add the selected pane to the root
                sceneIndex = index;//update the stored sceneIndex
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Member getMember() {
        return member;
    }

    public static void setMember(Member member) {
        Main.member = member;
    }
}
