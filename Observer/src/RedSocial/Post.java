package RedSocial;

import API.AbstractObservable;

import java.io.Serializable;


public class Post extends AbstractObservable implements Serializable {
    public String posterName;
    public String texto;
    public int likes;
    public int id;


    public Post(String _posterName,String _texto, int id){
        this.id = id;
        this.posterName = _posterName;
        this.texto = _texto;
        this.likes = 0;

    }


}