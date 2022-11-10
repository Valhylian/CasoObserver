package Subasta;

public class Oferta {
    int indexOferta;
    String name_ofertante;
    int index_ofertante;
    int dineroOfertado;
    Estado estado;

    public Oferta(int indexOferta,String name_ofertante,int index_ofertante,int dineroOfertado){
        this.indexOferta = indexOferta;
        this.name_ofertante = name_ofertante;
        this.index_ofertante = index_ofertante;
        this.dineroOfertado = dineroOfertado;
        this.estado = Estado.DEFAULT;
    }
}
