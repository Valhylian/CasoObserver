import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SubastadorS {
    public static ObjectOutputStream dos;
    public static ObjectInputStream dis;
    final static int ServerPort = 1234;

    public Subasta subasta = null;
    private String name;

    public SubastadorS (String name){
        this.name = name;

    }

    public static void main(String[] args) throws IOException {
        System.out.printf("NICKNAME:");
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        String nickname = br.readLine();

        SubastadorS subastador = new SubastadorS(nickname);
        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        dos = new ObjectOutputStream(s.getOutputStream());
        dis = new ObjectInputStream(s.getInputStream());

        ReadMessage_Subastador readMessage = new ReadMessage_Subastador(dis,dos);
        readMessage.start();

        Subastador_GUI window = new Subastador_GUI(dis,dos,subastador);
        window.init();
    }

}
