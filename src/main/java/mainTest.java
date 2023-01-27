import Clavardage.ServeurTCP;
import Clavardage.ClientTCP;
import ConnexionExceptions.UserNotFoundException;

import java.io.IOException;
import java.util.Scanner;

public class mainTest {
    public static void main(String args[]) throws IOException, UserNotFoundException {
        /*RemoteUser u1 = new RemoteUser("toto" , InetAddress.getByName("25.25.78.168") );
        liste.addUser(u1);
        System.out.println("la liste est "+liste);
        Connexion conn = new Connexion();



        ConnectionListener listener = new ConnectionListener() {
            @Override
            public void invalidID() {
                System.out.println("INVALID");

            }
            public void validID() { System.out.println("INVALID"); }
        };*/
       // new Ecoute(conn, listener).start();

        //StartSession.StartSession(InetAddress.getByName("127.0.0.1"));

        //conn.verifyId("toto");
        //System.out.println("pseudo : " + conn.getPseudo());
        //new Connexion().verifyId();

        //new Connexion().verifyId();
        // new Ecoute().start();
        // new Connexion().verifyId();
        ServeurTCP Server = new ServeurTCP(1769);
        Server.start();
        //int index = ServeurTCP.threadList.findThread("127.0.0.1");
        Scanner entreeClavier = new Scanner(System.in);

        System.out.println(ServeurTCP.clientList.lengthListe());
        ClientTCP client;
        String msg = entreeClavier.nextLine();
        client = ServeurTCP.clientList.getClient(0);
        msg = entreeClavier.nextLine();
        client.sendMessage(msg);
        client.sendMessage("end1");
        System.out.println("sdas");
        Server.close();

    };
}
