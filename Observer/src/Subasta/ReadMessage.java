package Subasta;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ReadMessage extends Thread {
    ClienteSubasta_GUI clientePantalla;
    DataInputStream dis;
    DataOutputStream dos;
    Oferente oferente;

    public ReadMessage(Oferente oferente, DataInputStream dis, DataOutputStream dos, ClienteSubasta_GUI clientePantalla) {
        super();
        this.oferente = oferente;
        this.dis = dis;
        this.dos = dos;
        this.clientePantalla = clientePantalla;
    }

    @Override
    public void run() {
        while (true) {

            try {
                JSONParser parser = new JSONParser();
                JSONObject json;
                // read the message sent to this client
                String msg = dis.readUTF();
                json = (JSONObject) parser.parse(msg);
                Object asunto = json.get("asunto");
                String asuntoStr = asunto.toString();

                if (asuntoStr.equals("TodoListo")) {
                    /* FORMATO JSON

                    {Asunto: "aja", "Data":
                        [{"Subasta":"1",
                        "Producto": "algo",
                        "Precio Incial": "0"},
                        {"Subasta":"2",
                        "Producto": "algo",
                        "Precio Incial": "0"}]
                     }
                    *
                    JSONArray jsonArray = json.getJSONArray("data");
                    ClienteSubasta_GUI.comboBox1.
                    PantallaJugador.frame.dispose();
                    jeje = new PantallaSecundaria(jugador, dis, dos);
                    System.out.println("Jugador"+jugador.name);
                    jeje.init(); */
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
