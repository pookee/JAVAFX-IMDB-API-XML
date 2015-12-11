package sample;

/**
 * Created by alexandrelunati on 09/12/2015.
 */
public class ItemFilm {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return this.title;
    }
}
