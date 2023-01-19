package Clavardage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ListOfMessages {

    private ObservableList<Message> message =  FXCollections.observableArrayList();

    public void addMsg(Message msg) {
        message.add(msg);
    }

    public void delMsg(Message msg) {
        message.remove(msg);
    }

    public ObservableList<Message> getMessage() {
        return message;
    }
}
