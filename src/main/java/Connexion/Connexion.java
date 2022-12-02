package Connexion;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Connexion{



    public static void verifyId() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("choose id");
        String str = sc.next();
        UDP.broadcast(str , InetAddress.getByName("255.255.255.255"));

        //UDP.broadcast("toto", InetAddress.getByName("255.255.255.255"));


    }
}
