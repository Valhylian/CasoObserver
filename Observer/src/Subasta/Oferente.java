package Subasta;

import java.net.Socket;
import java.util.ArrayList;

public class Oferente {
    public String nickname;
    public int id;
    public ArrayList<Subasta> subscritas = new ArrayList<>();

    public Oferente(String nick){
        this.nickname = nick;
        this.id = 0;

    }
}
