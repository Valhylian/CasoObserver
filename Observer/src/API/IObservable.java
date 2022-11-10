package API;

import java.io.IOException;

public interface IObservable {
    public int addObserver(ClientHandler observer);
    public void removeObserver(ClientHandler observer);
    public void notifyAllObservers(Paquete paquete) throws IOException;
    public void notifyPrincipals(String command, Object source) throws IOException;
    public int addPrincipal(ClientHandler observer);

    void notifyAllObservers(Paquete paquete, Object source) throws IOException;
}
