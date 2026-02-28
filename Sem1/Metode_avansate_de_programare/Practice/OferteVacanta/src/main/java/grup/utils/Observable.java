package grup.utils;

import grup.domain.Hobby;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(Hobby hobby, String hotelName) {
        for (Observer o : observers) {
            o.update(hobby, hotelName);
        }
    }

}
