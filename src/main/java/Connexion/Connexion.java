package Connexion;

import java.io.IOException;
import java.net.InetAddress;

public class Connexion{

    public static String userName="toto";
    public void verifyId() throws IOException {
        UDP.broadcast(userName, InetAddress.getByName("255.255.255.255"));
    }
}
