import Clavardage.StartSession;
import Clavardage.ClientTCP;
import ConnexionExceptions.UserNotFoundException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class mainTest2 {
    public static void main(String args[]) throws IOException, ClassNotFoundException, UnknownHostException, InterruptedException, UserNotFoundException {
       // new Ecoute().start();
       // new Connexion().verifyId();
        //MultiServeurTCP Server = new MultiServeurTCP(1769);
        //Server.start();
       // StartSession.StartSession(InetAddress.getByName("127.0.0.1"));
        StartSession Session = new StartSession(InetAddress.getByName("127.0.0.1"));
        Session.start();
        ClientTCP client = Session.getClient();
        Scanner entreeClavier = new Scanner(System.in);
        String msg = entreeClavier.nextLine();

        client.sendMessage(msg);
        client.sendMessage("end1");
        System.out.println("sdas");
        Session.interrupt();
    }
}
