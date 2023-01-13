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
import Connexion.Ecoute;
import Connexion.ConnectionListener;
import java.io.IOException;
import java.net.SocketException;

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
    public boolean isValid;

    Connexion connexion = new Connexion();
    ConnectionListener listener = new ConnectionListener() {

        public void invalidID() {
            isValid = false;
        }

        @Override
        public void validID() {
            isValid = true;
        }
    };
    Ecoute ecoute;

    {
        try {
            ecoute = new Ecoute(connexion , listener);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void connexion(ActionEvent actionEvent) throws IOException, InterruptedException {
        System.out.println("ecoute");
        ecoute.start();

        userName = new String(userNameText.getText());

        connexion.verifyId(userName)  ;


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (isValid) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load(),580, 340);
            stage.setScene(scene);
            stage.show();
            System.out.println("username available");
        }
        else {
            System.out.println("username unavailable");
        }

    }

}