package Connexion;

import ConnexionExceptions.UserNotFoundException;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

//import static Connexion.Connexion.userName;

public class Ecoute extends Thread {
    InetAddress myIPadd ;
    private DatagramSocket socket;
    private boolean connecte;
    private byte[] buf = new byte[256];
    public static UserList liste = new UserList();

    public RemoteUser user1 ;

    private Connexion connexion ;

    private final ConnectionListener listener;

    public void stopSocket(){
        socket.close();
    }

    public Connexion getConnexion() {
        return this.connexion;
    }

    public Ecoute(Connexion connexion, ConnectionListener listener) throws SocketException {
        this.listener = listener;
        socket = new DatagramSocket(UDP.UDP_PORT  );
        this.connexion = connexion ;
        System.out.println("le n de tests");
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
            //int port = packet.getPort();
            String received = new String(packet.getData(), 0, packet.getLength());



            if (isMyAddress(address) ) {
                if (liste.lengthListe()==0){
                    listener.validID();
                    System.out.println("ya personne");}
                else{}
            }


            else{
                System.out.println("nouveau msg recu: "+received);

                if (received.equals("end")) {

                    System.out.println("Disconnect");
                    try {
                        liste.delUser(liste.getUserByAdd(address));
                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                    }

                }

                /*if(liste.lengthListe()==0 && !received.equals(connexion.getPseudo()) ) {
                    while (connecte) {

                        System.out.println("la liste est vide mais : "+received);
                        listener.validID();
                        liste.addUser(new RemoteUser(received,address));
                        break;

                    }
                }*/

                else if (received.equals("Id already used")) {
                    listener.invalidID();
                }

                else if (received.startsWith("Bienvenue mon id est :")) {
                    System.out.println("binvenue mon id est");
                    listener.validID();
                    String id = received.replace("Bienvenue mon id est :", "");
                    liste.addUser(new RemoteUser(id , address));
                    try {
                        System.out.println("la liste est "+liste.getids());
                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                else if(liste.verifyUserNamePresent(received) || received.equals(connexion.getPseudo())){
                    listener.invalidID();
                    //System.out.println("l'util est present");
                    String msgErreur = "Id already used" ;
                    System.out.println("le nom d'utilisateur "+received+" est deja utilisé");

                    byte[] buf = msgErreur.getBytes();

                    packet = new DatagramPacket(buf, buf.length, address, UDP.UDP_PORT);
                    try {
                        socket.send(packet);
                        System.out.println("j'envoie le packet et le port est : " +packet.getPort());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                else if (!liste.verifyUserNamePresent(received) || !received.equals(connexion.getPseudo())){
                    liste.addUser(new RemoteUser(received,address));
                    System.out.println("la nouvelle liste est : "+liste);

                    String msg = "Bienvenue mon id est :"+connexion.getPseudo() ;

                    byte[] buf = msg.getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, UDP.UDP_PORT);
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

    public boolean isMyAddress(InetAddress addr){
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> innetaddresses = networkInterface.getInetAddresses();
                while(innetaddresses.hasMoreElements()){
                    InetAddress inetAddress = innetaddresses.nextElement();
                    if (inetAddress.equals(addr)){
                        return true;
                    }
                }
            }


        } catch (SocketException e) {

        }
        return false;

    }



}
