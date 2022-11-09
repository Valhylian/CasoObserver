package API;

import java.io.ObjectOutputStream;
import java.net.Socket;

public interface IObserver {
    public void notifyObserver(Paquete paquete);
    public Socket returnSocket ();
}
