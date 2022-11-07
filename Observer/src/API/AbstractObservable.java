package API;

import java.util.ArrayList;

public abstract class AbstractObservable implements IObservable{
    public static ArrayList<IObserver> observers = new ArrayList<>();
    public static ArrayList<IObserver> principales = new ArrayList<>();

    @Override
    public int addObserver(IObserver observer) {
        this.observers.add(observer);
        return this.observers.size();
    }

    @Override
    public int addPrincipal(IObserver observer) {
        this.principales.add(observer);
        return this.principales.size();
    }

    @Override
    public void removeObserver(IObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyPrincipals(String command, Object source) {
        for (IObserver observer : principales) {
            observer.notifyObserver(command, source);
        }
    }

    @Override
    public void notifyAllObservers(String command, Object source) {
        for (IObserver observer : observers) {
            observer.notifyObserver(command, source);
        }

    }
}
