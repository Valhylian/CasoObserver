package API;

import javax.xml.crypto.Data;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public abstract class AbstractObservable implements IObservable{
    public static ArrayList<ClientHandler> observers = new ArrayList<>();
    public static ArrayList<ClientHandler> principales = new ArrayList<>();

    @Override
    public int addObserver(ClientHandler observer) {
        this.observers.add(observer);
        return this.observers.size();
    }

    @Override
    public int addPrincipal(ClientHandler observer) {
        this.principales.add(observer);
        return this.principales.size();
    }

    @Override
    public void removeObserver(ClientHandler observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyPrincipals(String command, Object source) {
        for (ClientHandler observer : principales) {
            observer.notifyObserver(command, source);
        }
    }

    public void notifyPrincipal(int index, Paquete paquete) throws IOException {
        ClientHandler principal = principales.get(index);
        principal.dos.writeObject(paquete);

    }

    @Override
    public void notifyAllObservers(String command, Object source) {
        for (ClientHandler observer : observers) {
            observer.notifyObserver(command, source);
        }

    }
}
