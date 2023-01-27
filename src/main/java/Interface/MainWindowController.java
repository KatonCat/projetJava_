package Interface;

import DataBase.*;
import Clavardage.*;
import ConnexionExceptions.UserNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

import Connexion.RemoteUser;
import Connexion.UserList;


import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.*;


public class MainWindowController {
    public static ObservableList<RemoteUser> items;
    public static ObservableList<Message> msgs;
    private static RemoteUser selectedUser;
    private static MainWindowController instance;
    private Stage stage;
    private Scene scene;
    private StartSession session;

    private ServeurTCP.ClientHandler handler;
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
    private TextField userNameFieled;

    @FXML
    private TextField newUserName;

    @FXML
    private TableColumn<Message, String> messages;
    private String message;
    private Connection connection = null;
    @FXML
    private void initialize() {
        instance = this;
        items = FXCollections.observableArrayList();
        onlineUsersList.setItems(items);
        onlineUsersTable.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));
        onlineUsersList.setItems(Ecoute.liste.getUsers());
        ServeurTCP.handlerList.getList().addListener((ListChangeListener<ServeurTCP.ClientHandler>) changeNew -> {
            while (changeNew.next()) {
                if (changeNew.wasAdded()) {
                    ServeurTCP.ClientHandler lastHandler =ServeurTCP.handlerList.getLast();
                    InetAddress addr=lastHandler.getAddress();
                    try {
                        String pseudo = Ecoute.liste.getUserByAdd(addr).getUserName();
                        String tableName =pseudo+addr.getHostAddress().replace(".","_");

                        BDD.createNewTable("CentralMessages", tableName );
                        Thread.sleep(500);

                        lastHandler.listeMsg.getMessage().addListener((ListChangeListener<Message>) change -> {

                            while (change.next()) {
                                if (change.wasAdded()) {

                                    if(!lastHandler.listeMsg.getLast().getUserName().equals("Moi")) {
                                        System.out.println("Il rentre bien dans le if");
                                        BDD.insert("CentralMessages", tableName, lastHandler.listeMsg.getLast());
                                    }

                                }
                            }
                        });


                    } catch (UserNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        onlineUsersList.setOnMouseClicked(event ->{
           selectedUser = onlineUsersList.getSelectionModel().getSelectedItem();
           String addr = selectedUser.getAdd().getHostAddress();
                if (selectedUser != null){
                    String tableName=selectedUser.getUserName()+selectedUser.getAdd().getHostAddress().replace(".","_");
                    if (ServeurTCP.clientList.findClient(addr)==-1 && ServeurTCP.sessionList.findSession(addr)==-1) {
                        BDD.createNewTable("CentralMessages" , tableName);
                        session = new StartSession(selectedUser.getAdd());
                        session.start();

                        session.listeMsg = BDD.select("CentralMessages", selectedUser.getUserName()+selectedUser.getAdd().getHostAddress().replace(".","_"));
                        msgs = FXCollections.observableArrayList();
                        messagesTable.setItems(msgs);
                        messages.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName() + " " + cellData.getValue().getMsg() + " " + cellData.getValue().getDateTS()));
                        messagesTable.setItems(session.listeMsg.getMessage());
                        session.listeMsg.getMessage().addListener((ListChangeListener<Message>) change -> {

                            while (change.next()) {
                                if (change.wasAdded()) {

                                    if(!session.listeMsg.getLast().getUserName().equals("Moi")) {
                                        BDD.insert("CentralMessages", tableName, session.listeMsg.getLast());
                                    }

                                }
                            }
                        });

                    }
                    else if(ServeurTCP.sessionList.findSession(addr)!=-1){
                        session = ServeurTCP.sessionList.getSession(ServeurTCP.sessionList.findSession(selectedUser.getAdd().getHostAddress()));
                        session.listeMsg = BDD.select("CentralMessages", selectedUser.getUserName()+selectedUser.getAdd().getHostAddress().replace(".","_"));
                        msgs = FXCollections.observableArrayList();
                        messagesTable.setItems(msgs);
                        messages.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName() + " " + cellData.getValue().getMsg() + " " + cellData.getValue().getDateTS()));
                        messagesTable.setItems(session.listeMsg.getMessage());

                    }
                    else{
                        handler = ServeurTCP.handlerList.getHandler(ServeurTCP.handlerList.findHandler(addr));
                        System.out.println("C'est bon");
                        handler.listeMsg = BDD.select("CentralMessages", selectedUser.getUserName()+selectedUser.getAdd().getHostAddress().replace(".","_"));
                        msgs = FXCollections.observableArrayList();
                        messagesTable.setItems(msgs);
                        messages.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName() + " " + cellData.getValue().getMsg() + " " + cellData.getValue().getDateTS()));
                        messagesTable.setItems(handler.listeMsg.getMessage());


                    }


                }
        });

        }

    @FXML
    void setVisible(ActionEvent event) {
        userNameFieled.setVisible(true);
    }

    @FXML
    void changeUsername(ActionEvent event) throws IOException {
        SceneData sd = (SceneData) App.getStage().getUserData();
        Ecoute ecoute = sd.getData1();
        Ecoute.liste =  new UserList();
        String userName = newUserName.getText();
        ecoute.getConnexion().changePseudo(userName);
        App.getStage().setTitle("home -"+userName);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        SceneData sd = (SceneData) App.getStage().getUserData();
        Ecoute ecoute = sd.getData1();
        ServeurTCP server = sd.getData2();
        UDP.broadcast("end" , InetAddress.getByName("255.255.255.255"));
        ecoute.interrupt();
        ecoute.stopSocket();
        Ecoute.liste.clear();
        server.close();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("welcome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(),580, 340);
        stage.setScene(scene);
        stage.setTitle("java app");
        stage.show();
    }

    @FXML
    void SendMessage(ActionEvent event) throws UserNotFoundException {
        int  index =ServeurTCP.clientList.findClient(selectedUser.getAdd().getHostAddress());
        ClientTCP client;
        String username =selectedUser.getUserName();
        String address =selectedUser.getAdd().getHostAddress();
        message = messageUtil.getText();
        if(index==-1){
            client = session.getClient();
            session.listeMsg.addMsg(new Message("Moi",message,new Timestamp(System.currentTimeMillis())));
        }
        else{

            client = ServeurTCP.clientList.getClient(index);
            handler.listeMsg.addMsg(new Message("Moi",message,new Timestamp(System.currentTimeMillis())));
        }


        client.sendMessage(message);
        messageUtil.clear();
        BDD.insert("CentralMessages",username+selectedUser.getAdd().getHostAddress().replace(".","_"),new Message("Moi",message,new Timestamp(System.currentTimeMillis())));


    }


}