package Observer;


import Clavardage.Message;
import Interface.MainWindowController;
import javafx.application.Platform;

public class ReceivedMessage implements Observer{
    private MainWindowController cwc;

    public ReceivedMessage(MainWindowController cwc) {
        this.cwc = cwc;
    }

    @Override
    public void update(Message message) {

    }
}
