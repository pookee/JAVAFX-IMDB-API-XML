package sample;

import org.xml.sax.*;
import org.xml.sax.ContentHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.codehaus.jettison.json.*;
/**
 * Created by Alexandre Lunati on 30/11/2015.
 */

public class Model implements ContentHandler{

    // VARIABLES

    private String urlToLoad;
    private Map<String, String> response = new HashMap<String, String>();
    private XMLReader ressource;
    private String lastNode;
    private boolean rechercheSucceed;


    private String version = "short";

    // Variable pour récupérer les infos du XML
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String poster;
    private Float metascore;
    private Float imdbRating;
    private Float imdbVotes;
    private String imdbID;
    private String type;
    private String language;
    private String awards;
    private String country;

    private Integer historique = 0;
    private Float loading; // Permet de connaitre la quantité d'infos trouvées

    private List<String> listeSerie = new ArrayList<String>();




    // Génère la liste des séries pour le jeu
    public void initListeSerie() {
        listeSerie.clear();
        listeSerie.add("The Walking Dead");
        listeSerie.add("How I Met Your Mother");
        listeSerie.add("Sherlock");
        listeSerie.add("Shameless");
        listeSerie.add("Friends");
        listeSerie.add("Scrubs");
        listeSerie.add("Daredevil");
        listeSerie.add("Bones");
        listeSerie.add("Fullmetal Alchemist");
        listeSerie.add("Game of thrones");
        listeSerie.add("Black Mirror");
        listeSerie.add("Lie To Me");
        listeSerie.add("The Mentalist");
        listeSerie.add("Breaking Bad");
        listeSerie.add("30 Rock");
        listeSerie.add("Utopia");
        listeSerie.add("Malcolm In The Middle");
        listeSerie.add("Gossip Girl");
        listeSerie.add("Desperate Housewives");
        listeSerie.add("grey's anatomy");
        listeSerie.add("Community");
        listeSerie.add("Californication");
        listeSerie.add("The Wire");
        listeSerie.add("Mr Robot");
        listeSerie.add("Mr Robot");
        listeSerie.add("Orange is the new black");
        listeSerie.add("The Flash");
        listeSerie.add("Elementary");
        listeSerie.add("Broadchurch");
        listeSerie.add("Prison Break");
        listeSerie.add("Homeland");
        listeSerie.add("Wilfred");
        listeSerie.add("Lost");
        listeSerie.add("New Girl");
        listeSerie.add("Once upon a time");
        listeSerie.add("Community");
        listeSerie.add("Dexter");
        listeSerie.add("Greek");
        listeSerie.add("Futurama");
        listeSerie.add("One Tree Hill");
        listeSerie.add("Pretty little liars");
        listeSerie.add("American Dad");
        listeSerie.add("House of Cards");
        listeSerie.add("Unbreakable Kimmy Schmidt");
        listeSerie.add("The 4400");
        listeSerie.add("Silicon Valley");
        listeSerie.add("Fear The Walking Dead");
        listeSerie.add("One Piece");
        listeSerie.add("Chuck");
        listeSerie.add("Grimm");
        listeSerie.add("Misfits");
        listeSerie.add("Misfits");
        listeSerie.add("The Sopranos");
        listeSerie.add("Six Feet Under");
        listeSerie.add("The Shield");
        listeSerie.add("Hunter x Hunter");
        listeSerie.add("Death Note");
        listeSerie.add("Narcos");
        listeSerie.add("Kaamelott");
    }

    // Vide les variables
    public void clearAll(){
        awards ="";
        director="";
        writer="";
        plot="";
        poster="";
        metascore= new Float(0);
        imdbRating= new Float(0);
        imdbID="";
        type="";
        language="";
        genre ="";
        runtime="";
        country="";
        rated="";
        released="";
        actors="";
        year="";
        title="";
    }

    // Gestion de l'historique
    public void updateHistorique(){
        if(historique==15){
            setHistorique(1);
        }
        else{
            setHistorique(getHistorique()+1);
        }
    }

    // PERMET DE DIRE à L'API LE NOM DE CE QUE l'ON RECHERCHE AVANT DE RECUPERER LE RESULTAT A ANALYSER
    public InputStream GetFilmByName(String name){
        HttpURLConnection connection = null;
        try{
            if(getVersion().equals("short")) // Short synopsis
            {
                URL url = new URL("http://www.omdbapi.com/?t="+URLEncoder.encode(name)+"&y=&plot=short&r=xml");
                URLConnection connexion = url.openConnection();
                return connexion.getInputStream();
            }
            else if(getVersion().equals("full")){ // Long synopsis
                URL url = new URL("http://www.omdbapi.com/?t="+URLEncoder.encode(name)+"&y=&plot=full&r=xml");
                URLConnection connexion = url.openConnection();
                return connexion.getInputStream();
            }
            URL url = new URL("http://www.omdbapi.com/?t="+URLEncoder.encode(name)+"&y=&plot=short&r=xml");
            URLConnection connexion = url.openConnection();
            return connexion.getInputStream();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    // PERMET D'ANALYSER LE XML
    public void AnalysingXML(InputStream xmlString)  {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader(); // On se prépare à parser le XML
            xr.setContentHandler(this);
            xr.parse(new InputSource(xmlString)); // on lance le parsage en donnant le flux xml récupéré auparavant
            this.ressource = xr;
            System.out.println(this.ressource.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //PERMET D'OUVRIR UNE PAGE WEB
    public static void openWebPage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    // GETTER & SETTER

    public Model(String urlToLoad) {
        this.urlToLoad = urlToLoad;
    }

    public List<String> getListeSerie() {
        return listeSerie;
    }

    public void setListeSerie(List<String> listeSerie) {
        this.listeSerie = listeSerie;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getHistorique() {
        return historique;
    }

    public void setHistorique(Integer historique) {
        this.historique = historique;
    }

    public XMLReader getRessource() {
        return ressource;
    }

    public void setRessource(XMLReader ressource) {
        this.ressource = ressource;
    }

    public String getResponse() { // La Key représente la colonne associée dans le dictionnaire
        return this.response.toString();
    }

    public void setResponse(Map<String, String> response) {
        this.response = response;
    }

    public String getResponseKey(String key) { // La Key représente la colonne associée dans le dictionnaire
        return this.response.get(key);
    }

    public Float getLoading() {
        return loading;
    }

    public void setLoading(Float loading) {
        this.loading = loading;
    }

    public String getUrlToLoad() {
        return urlToLoad;
    }

    public void setUrlToLoad(String urlToLoad) {
        this.urlToLoad = urlToLoad;
    }

    public String getLastNode() {
        return lastNode;
    }

    public void setLastNode(String lastNode) {
        this.lastNode = lastNode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Float getMetascore() {
        return metascore;
    }

    public void setMetascore(Float metascore) {
        this.metascore = metascore;
    }

    public Float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Float getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(Float imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    // PARSAGE !!!!!!

    @Override // !!!! PERMET DE RECUPERER LE XML
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        lastNode = qName;

        if(qName.equals("root")) {
            if (atts.getValue("response").equals("False"))
            {
                clearAll();
                setTitle("NOT FOUND PLEASE TRY AGAIN");
                rechercheSucceed = false;
                return;
            }
        }

        if (qName.equals("movie"))
        { // ICI ON RECUPERE CE DONT ON A BESOIN DANS LE XML

                if (atts.getValue("title").equals("N/A"))
                {
                    setTitle("");
                }
                else
                {
                    setTitle(atts.getValue("title"));
                    loading = loading + new Float(0.05882352941);
                    rechercheSucceed = true;
                }

                if (atts.getValue("year").equals("N/A"))
                {
                    setYear("");
                }
                else
                {
                    setYear(atts.getValue("year"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("rated").equals("N/A"))
                {
                    setRated("");
                }
                else
                {
                    setRated(atts.getValue("rated"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("released").equals("N/A"))
                {
                    setReleased("");
                }
                else {
                    setReleased(atts.getValue("released"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("runtime").equals("N/A"))
                {
                    setRuntime("");
                } else
                {
                    setRuntime(atts.getValue("runtime"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("genre").equals("N/A"))
                {
                    setGenre("");
                }
                else
                {
                    setGenre(atts.getValue("genre"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("director").equals("N/A"))
                {
                    setDirector("");
                } else
                {
                    setDirector(atts.getValue("director"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("actors").equals("N/A"))
                {
                    setActors("");
                }
                else {
                    setActors(atts.getValue("actors"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("plot").equals("N/A"))
                {
                    setPlot("");
                }
                else
                {
                    setPlot(atts.getValue("plot"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("language").equals("N/A"))
                {
                    setLanguage("");
                } else {
                    setLanguage(atts.getValue("language"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("country").equals("N/A"))
                {
                    setCountry("");
                } else {
                    setCountry(atts.getValue("country"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("awards").equals("N/A"))
                {
                    setAwards("");
                } else
                {
                    setAwards(atts.getValue("awards"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("poster").equals("N/A"))
                {
                    setPoster("http://www.ginesisnatural.com/images/no_image.jpg");
                } else {
                    setPoster(atts.getValue("poster"));
                    loading = loading + new Float(0.05882352941);
                }


                if (atts.getValue("metascore").equals("N/A"))
                {
                    setMetascore(new Float(0));
                }
                else
                {
                    setMetascore(Float.parseFloat(atts.getValue("metascore")));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("imdbRating").equals("N/A"))
                {
                    setImdbRating(new Float(0));
                }
                else
                {
                    setImdbRating(Float.parseFloat(atts.getValue("imdbRating")));
                    loading = loading + new Float(0.05882352941);
                }

                //imdbVotes = Float.parseFloat(atts.getValue("imdbVotes"));

                if (atts.getValue("imdbID").equals("N/A"))
                {
                    setImdbID("");

                } else
                {
                    setImdbID(atts.getValue("imdbID"));
                    loading = loading + new Float(0.05882352941);
                }

                if (atts.getValue("type").equals("N/A")) {
                    setType("");

                } else
                {
                    setType(atts.getValue("type"));
                    loading = loading + new Float(0.05882352941);
                }
            }

        }

    public List <ItemFilm> getAutocomplete(String textWritten){

       textWritten = textWritten.replace(" ", "_");
        textWritten = textWritten.toLowerCase();
        URL url = null;
        try {
            url = new URL("http://sg.media-imdb.com/suggests/"+textWritten.charAt(0)+"/"+textWritten+".json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLConnection connexion;

        try {
             connexion = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line);
            }
            bufferedReader.close();

            String contentS = content.substring(6+textWritten.length(),content.length()-1);

            JSONArray listOfResult = (new JSONObject(contentS)).getJSONArray("d");
            List<ItemFilm> list = new ArrayList<ItemFilm>();
            for (int i = 0; i < listOfResult.length(); i++)
            {
                String title = listOfResult.getJSONObject(i).getString("l");
                ItemFilm itemFilm = new ItemFilm();
                itemFilm.setTitle(title.toLowerCase());
                list.add(itemFilm);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        lastNode = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    @Override
    public void skippedEntity(String name) throws SAXException {

    }

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

    }

}

