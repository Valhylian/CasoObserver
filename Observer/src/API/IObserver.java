package API;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public interface IObserver {
    public void notifyObserver(Paquete paquete) throws IOException;
    public Socket returnSocket ();
}
