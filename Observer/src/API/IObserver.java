package API;

public interface IObserver {
    public void notifyObserver(String command, Object source);
}
