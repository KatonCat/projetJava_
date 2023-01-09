package Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import Connexion.Connexion;

import java.io.IOException;
import javafx.scene.Node;


public class WelcomeControler  {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TextField userNameText;

    @FXML
    private Button connexionBotton;

    @FXML
    private Text messageToUser;

    private String userName ;
     public void connexion(ActionEvent actionEvent) throws IOException {
        userName = new String(userNameText.getText());
        if (new Connexion().verifyId(userName) = );
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(),580, 340);
        stage.setScene(scene);
        stage.show();
    }

}