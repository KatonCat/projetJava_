package Interface;


import Clavardage.MultiServeurTCP;
import Connexion.Ecoute;

public class SceneData {
    private Ecoute data1;
    private MultiServeurTCP data2;

    public SceneData(Ecoute data1, MultiServeurTCP data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public Ecoute getData1() {
        return data1;
    }

    public MultiServeurTCP getData2() {
        return data2;
    }
}