package API;

public interface IObservable {
    public int addObserver(IObserver observer);
    public void removeObserver(IObserver observer);
    public void notifyAllObservers(String command, Object source);
    public void notifyPrincipals(String command, Object source);
    public int addPrincipal(IObserver observer);
}
