package API;

public interface IObservable {
    public int addObserver(ClientHandler observer);
    public void removeObserver(ClientHandler observer);
    public void notifyAllObservers(String command, Object source);
    public void notifyPrincipals(String command, Object source);
    public int addPrincipal(ClientHandler observer);
}
