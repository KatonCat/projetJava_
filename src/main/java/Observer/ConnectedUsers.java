package Observer;


import Clavardage.Message;
import Interface.MainWindowController;

public class ConnectedUsers implements Observer{
    private MainWindowController cwc;

    public ConnectedUsers(MainWindowController cwc) {
        this.cwc = cwc;
    }

    @Override
    public void update(Message message) {



    }
}
