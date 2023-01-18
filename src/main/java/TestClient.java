import java.io.IOException;
import java.net.InetAddress;

import Clavardage.MultiServeurTCP;
import Clavardage.StartSession;
import ConnexionExceptions.UserNotFoundException;

public class TestClient {
    public static void main(String args[]) throws IOException, UserNotFoundException {
        StartSession.StartSession(InetAddress.getByName("127.0.0.1"));
    }
}
