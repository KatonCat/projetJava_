import Connexion.Connexion;
import Connexion.Ecoute;

import java.io.IOException;

public class mainTest {
    public static void main(String args[]) throws IOException {
        new Ecoute().start();
        new Connexion().verifyId();
    }
}
