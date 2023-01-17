package Interface;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Connexion.Ecoute;
import Connexion.UDP;

import Connexion.Connexion;
import Connexion.RemoteUser;
import Connexion.UserList;


import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class MainWindowController {
    public static ObservableList<RemoteUser> items;
    private static MainWindowController instance;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button changeUsernameButton;

    @FXML
    private Button createGroupButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<RemoteUser, String> onlineUsersTable;

    @FXML
    private TableView<RemoteUser> onlineUsersList;



    @FXML
    private void initialize() {

        //Ecoute ecoute = (Ecoute) App.getStage().getUserData();
        instance = this;

        //App.getStage().setTitle("Home - " + Connexion.getPseudo());
        items = FXCollections.observableArrayList();
        onlineUsersList.setItems(items);
        onlineUsersTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));
        /*for (RemoteUser user : Ecoute.liste.getUsers() ){
            System.out.println(user.getUserName());
            items.add(user);
        }*/
        onlineUsersList.setItems(Ecoute.liste.getUsers());

        }


    @FXML
    void CreateGroup(ActionEvent event) {

    }

    @FXML
    void changeUsername(ActionEvent event) throws IOException {
        Ecoute ecoute = (Ecoute) App.getStage().getUserData();
        //String userName = userNameText.getText();
        ecoute.getConnexion().changePseudo("titi");
        App.getStage().setTitle("home -titi");
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Ecoute ecoute = (Ecoute) App.getStage().getUserData();
        UDP.broadcast("end" , InetAddress.getByName("255.255.255.255"));
        ecoute.stop();
        ecoute.stopSocket();
        Ecoute.liste =  new UserList();
        Connexion co = ecoute.getConnexion() ;
        co = null;
        ecoute = null;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(),580, 340);
        stage.setScene(scene);
        stage.setTitle("java app");
        stage.show();
    }



}