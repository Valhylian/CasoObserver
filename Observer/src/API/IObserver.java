package API;

import java.io.ObjectOutputStream;
import java.net.Socket;

public interface IObserver {
    public void notifyObserver(String command, Object source);
    public Socket returnSocket ();
}
