package Observer;

import Clavardage.Message;

import java.util.ArrayList;

public class Subject {
    private static ArrayList<Observer> observers = new ArrayList<>();
    public void addObserver (Observer observer){observers.add(observer);}

    public static void notifyObservers(Message msg){
        for (Observer o : observers){
                o.update(msg);
        }
    }

}
