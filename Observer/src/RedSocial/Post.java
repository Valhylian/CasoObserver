package RedSocial;

import API.AbstractObservable;

import java.io.Serializable;


public class Post extends AbstractObservable implements Serializable {
    public String posterName;
    public String texto;
    public int likes;


    public Post(String _posterName,String _texto){
        this.posterName = _posterName;
        this.texto = _texto;
        //this.likes = _likes;

    }

    @Override
    public void notifyAllObservers(String command, Object source) {

    }
}