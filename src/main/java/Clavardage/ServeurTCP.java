package Clavardage;

import BDD.BDD;
import BDD.CreateBDD;
import BDD.Insert;
import Connexion.Ecoute;
import ConnexionExceptions.UserNotFoundException;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

public class ServeurTCP extends Thread{
    private ServerSocket serverSocket;
    private final int port;
    public ServeurTCP(int port){this.port = port;}
    public static ThreadList threadList= new ThreadList();
    public void run(){
        ClientHandler thread;
        Socket socket;
        try {
            //BDD.createNewDatabase("CentralMessages.db");
            serverSocket = new ServerSocket(port);
            while(true) {
                thread = new ClientHandler(socket=serverSocket.accept());
                thread.start();
                thread.setName("127.0.0.1");
                //System.out.println("setname reussi");
                threadList.addThread(thread);
                //System.out.println("add reussi");

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not create the server n/or accept.");
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    public static class ClientHandler extends Thread{
        private final Socket clientSocket;
        private final clientTCP client = new clientTCP();

        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            this.client.getConnexion(this.clientSocket);
        }
        public clientTCP getClient(){
            return this.client;
        }

        public void init() throws IOException, UserNotFoundException {
            //BDD.createNewTable("CentralMessages","pascal");
            //System.out.println(addr.getHostAddress() + " : " + response);
            String MsgRecu;
            MsgRecu = client.rcvMessage();

            while (!this.isInterrupted()) {
                if ("hello dude".equals(MsgRecu)) {
                    client.sendMessage("hi mate");
                }
                if(MsgRecu.equals("end1")) {
                    this.interrupt();
                    System.out.println("La connexion est finie, veuillez appuyer sur entrée");
                }
                System.out.println(MsgRecu);
                MsgRecu = client.rcvMessage();
                BDD.insert("CentralMessages" , Ecoute.liste.getUserByAdd(clientSocket.getInetAddress()).getUserName() ,new Message(Ecoute.liste.getUserByAdd(clientSocket.getInetAddress()).getUserName() , MsgRecu , new Timestamp(System.currentTimeMillis()))  );
                //ListeMsg.addMsg(new Message("Juan",msg, new Timestamp(date.getTime())));
            }

            System.out.println("La connexion est finie");

        }



        public void run() {
            try {
                init();
                client.stopConnexion();
                clientSocket.close();
            } catch (IOException | UserNotFoundException e) {
                System.err.println("Could not initialize.");
                e.printStackTrace();
            }
        }

    }




}
