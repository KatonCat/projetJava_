package Connexion;

import ConnexionExceptions.UserNotFoundException;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

//import static Connexion.Connexion.userName;

public class Ecoute extends Thread {
    InetAddress myIPadd ;
    private DatagramSocket socket;
    private boolean connecte;
    private byte[] buf = new byte[256];
    public static UserList liste = new UserList();

    public RemoteUser user1 ;

    private Connexion connexion;

    private final ConnectionListener listener;

    public Ecoute(Connexion connexion, ConnectionListener listener) throws SocketException {
        this.listener = listener;
        socket = new DatagramSocket(UDP.UDP_PORT  );
        this.connexion = connexion ;

    }
    //observable 

    public void run() {
        connecte = true;

        while (connecte) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());

            try {
                Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                NetworkInterface eth = en.nextElement();
                InterfaceAddress ia = eth.getInterfaceAddresses().get(1);
                myIPadd=ia.getAddress();

                } catch (SocketException e) {
                e.printStackTrace();
            }
            if(  address.equals(myIPadd ) ){ }
                else{


                if(liste.lengthListe()==0){
                    while (connecte) {
                        listener.validID();
                        liste.addUser(new RemoteUser(received,address));
                        System.out.println("la liste est "+liste);



                        break;

                    }
                }

                else if (received.equals("end")) {

                    System.out.println("Disconnect");
                    try {
                        liste.delUser(liste.getUserByAdd(address));
                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                    }
                    connecte = false;
                    continue;
                }

                else if (received.equals("Id already used")) {
                    listener.invalidID();
                }

                else if (received.startsWith("Bienvenue mon id est :")) {
                    listener.validID();
                    String id = received.replace("Bienvenue mon id est :", "");
                    liste.addUser(new RemoteUser(id , address));
                }

                else if(liste.verifyUserNamePresent(received)){
                    String msgErreur = "Id already used" ;
                    System.out.println("le nom d'utilisateur "+received+" est deja utilisé");

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
                    System.out.println("la nouvelle liste est : "+liste);

                    String msg = "Bienvenue mon id est : "+connexion.getPseudo() ;

                    byte[] buf = msg.getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println(""+connexion.getPseudo());
                }
            }
            }

        socket.close();
        }


}