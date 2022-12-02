package Connexion;

import ConnexionExceptions.UserNotFoundException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static Connexion.Connexion.userName;

public class Ecoute extends Thread {

    private DatagramSocket socket;
    private boolean connecte;
    private byte[] buf = new byte[256];
    public static UserList liste = new UserList();
    public RemoteUser user1 ;

    public Ecoute() throws SocketException {

        socket = new DatagramSocket(UDP.UDP_PORT);
    }

    public void run() {
        connecte = true;

        while (connecte) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(liste.lengthListe()==0){
                while (connecte) {

                    //DatagramPacket packet = new DatagramPacket(buf, buf.length);


                    /*try {
                        socket.receive(packet);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(received);


                    //if (received.equals("Id already used"))//

                    //    System.out.println("This Id is already used try new one ");
                    //    connecte = false;
                    //    continue;
                    //}
                    //else{
                        liste.addUser(new RemoteUser(received,address));
                        System.out.println("The user "+ received+ " is now connected");
                        System.out.println("The users list is "+ liste );

                    //}
                    break;

                }
            }

            /*DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);


            if (received.equals("end")) {

                System.out.println("Disconnect");
                try {
                    liste.delUser(liste.getUserByAdd(address));
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
                connecte = false;
                continue;
            }
            /*else if(liste.verifyUserNamePresent(received)) {

            }*/
            else if(received.equals(userName)){
                String msgErreur = "Id already used" ;
                byte[] buf = msgErreur.getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                liste.addUser(new RemoteUser(received,address));
                System.out.println("This user is now connected : "+ received);
            }

        }
        socket.close();
    }
}