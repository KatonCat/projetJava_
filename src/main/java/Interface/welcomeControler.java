package Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class WelcomeControler {
    public void connexion(ActionEvent actionEvent) throws IOException {
        System.out.print("tested");
        App.setRoot("mainWindow");
    }
/*
    @FXML
    private TextField UserNameText;

    @FXML
    private Button connexionBotton;

    @FXML
    private Text messageToUser;

    @FXML
    void connexion(ActionEvent event) throws IOException {
        System.out.print("tested");
        App.setRoot("mainWindow");
    }
*/
}