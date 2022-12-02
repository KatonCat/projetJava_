package Connexion;

import java.net.InetAddress;

public class RemoteUser {

    private String userName;

    private InetAddress add ;

    public RemoteUser (String userName , InetAddress add){
        this.userName = userName;
        this.add = add;
    }

    public String getUserName() {
        return this.userName;
    }

    public InetAddress getAdd() {

        return this.add;
    }

    //faire setteurs


}
