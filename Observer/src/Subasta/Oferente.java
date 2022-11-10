package Subasta;

import java.net.Socket;
import java.util.ArrayList;

public class Oferente {
    public String nickname;
    public int id;
    public ArrayList<Subasta> subscritas = new ArrayList<>();

    public ArrayList<Subasta> generales = new ArrayList<>();

    public Oferente(String nick){
        this.nickname = nick;
        this.id = 0;

    }

    String returnInfoSubasta(String name){
        String info = "";

        for (Subasta subasta: generales){
            if (subasta.name.equals(name)){
                info += "Subasta: "+ subasta.name;
                info += "\nUltima Oferta: "+subasta.lastOfert;
            }
        }
        return info;
    }
}
