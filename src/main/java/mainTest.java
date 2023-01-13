import Connexion.Connexion;
import Connexion.Ecoute;
import Connexion.RemoteUser;
import Connexion.ConnectionListener;

import java.io.IOException;
import java.net.InetAddress;

import static Connexion.Ecoute.liste;

public class mainTest {
    public static void main(String args[]) throws IOException {
        RemoteUser u1 = new RemoteUser("toto" , InetAddress.getByName("25.25.78.168") );
        liste.addUser(u1);
        System.out.println("la liste est "+liste);
        Connexion conn = new Connexion();



        ConnectionListener listener = new ConnectionListener() {
            @Override
            public void invalidID() {
                System.out.println("INVALID");

            }
            public void validID() {
                System.out.println("INVALID");

            }
        };
       // new Ecoute(conn, listener).start();


        //conn.verifyId("toto");
        //System.out.println("pseudo : " + conn.getPseudo());
        //new Connexion().verifyId();

        //new Connexion().verifyId();
    }
}
