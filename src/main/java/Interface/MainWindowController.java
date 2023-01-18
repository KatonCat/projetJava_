package Interface;

import BDD.Select;
import Clavardage.ListOfMessages;
import Clavardage.Message;
import Clavardage.MultiServeurTCP;
import Clavardage.StartSession;
import ConnexionExceptions.UserNotFoundException;
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


import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;


public class MainWindowController {
    public static ObservableList<RemoteUser> items;
    private static MainWindowController instance;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField messageUtil;

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
    private Button sendButton;

    @FXML
    private TableView<Message> messagesTable;

    @FXML
    private TableColumn<Message, String> messages;
    private String message;

    @FXML
    private void initialize() {
        instance = this;
        items = FXCollections.observableArrayList();
        onlineUsersList.setItems(items);
        onlineUsersTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));
        onlineUsersList.setItems(Ecoute.liste.getUsers());
        onlineUsersList.setOnMouseClicked(event ->{
           RemoteUser selectedUser = onlineUsersList.getSelectionModel().getSelectedItem();
                if (selectedUser != null){
                    try {
                        StartSession.StartSession(selectedUser.getAdd());
                        Select.selectAll(""+selectedUser.getUserName());
                        //java.util.Date date = new Date();
                        //Select.selectAll(selectedUser.getUserName()).addMsg(new Message(selectedUser.getUserName(),"hello", new Timestamp(date.getTime())));
                        //Select.selectAll(selectedUser.getUserName()).getMessage().get(0);



                    } catch (IOException | UserNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        });

        }



    @FXML
    void changeUsername(ActionEvent event) throws IOException {
        //Ecoute ecoute = (Ecoute) App.getStage().getUserData();
        //String userName = userNameText.getText();
        //ecoute.getConnexion().changePseudo("titi");
        //App.getStage().setTitle("home -titi");
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        SceneData sd = (SceneData) App.getStage().getUserData();
        Ecoute ecoute = sd.getData1();
        MultiServeurTCP server = sd.getData2();
        UDP.broadcast("end" , InetAddress.getByName("255.255.255.255"));
        ecoute.stop();
        ecoute.stopSocket();
        Ecoute.liste =  new UserList();
        Connexion co = ecoute.getConnexion() ;
        co = null;
        ecoute = null;
        server.close();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(),580, 340);
        stage.setScene(scene);
        stage.setTitle("java app");
        stage.show();
    }

    @FXML
    void SendMessage(ActionEvent event) {
        message = messageUtil.getText();
    }


}