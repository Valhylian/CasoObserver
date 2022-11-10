package API;

import Subasta.Subasta;

import javax.xml.crypto.Data;
import java.io.DataOutput;
import java.io.IOException;
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
    public void notifyPrincipals(String command, Object source) throws IOException {
        for (ClientHandler observer : principales) {
            observer.notifyObserver(null);
        }
    }

    public void notifyPrincipal(int index, Paquete paquete) throws IOException {
        ClientHandler principal = principales.get(index);
        principal.dos.writeObject(paquete);

    }

    public void notifyObserver_Index(int index, Paquete paquete) throws IOException {
        ClientHandler cliente = observers.get(index);
        cliente.notifyObserver(paquete);
    }

    @Override
    public void notifyAllObservers(Paquete paquete) throws IOException {
        for (ClientHandler observer : observers) {
            Paquete paquete1 = new Paquete(paquete.asunto, paquete.contenido);
            observer.notifyObserver(paquete1);
        }

    }
}
