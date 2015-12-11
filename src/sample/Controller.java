package sample;

import Autocomplete.Autocomplete;
import Autocomplete.Action;
import javafx.fxml.FXML;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements Initializable{

    private Autocomplete autocompleteTextfield;

    private Float version = new Float(2.0); // Version de l'app

    private Integer numeroReponse; // Aléatoire pour la question

    private boolean alreadyAnswered = false; // Permet de savoir si une réponse a déjà été donné dans le jeu

    Model model = new Model("toto"); // Instanciation du modèle

    String monFilm;

    // FONCTIONS POUR LA RECHERCHE DANS LA DATABASE (ONGLET 1)

    @FXML  // Fonction majeure, lance la recherche et update les champs
    public void CliqueSurGo() {
        Logger logger = Logger.getLogger("Début de la recherche");
        logger.log(Level.INFO, "Button Clicked");
        logger.log(Level.INFO, "Getting information ...");



        monFilm = RechecheField.getText();
        model.updateHistorique(); // On indique que l'historique augmente de 1
        updateGuiHistorique(model.getHistorique());
        model.setLoading(new Float(0));

        InputStream ressource = model.GetFilmByName(monFilm); // Appel au modèle pour connexion à l'API
        model.AnalysingXML(ressource); // Traitement du XML

        System.out.println(model.getTitle());

        // MISE A JOUR DE LA VUE

        realisateurLabel.setText("Réalisateur : " + model.getDirector());
        titleLabel.setText(model.getTitle());
        plotText.setText(model.getPlot());
        runtimeLabel.setText("Durée : " + model.getRuntime());

        if (model.getPoster().equals("")) {
            imageAffiche.setImage(new Image("https://www.ginesisnatural.com/images/no_image.jpg"));
        } else {
            imageAffiche.setImage(new Image(model.getPoster()));
        }

        if (model.getMetascore() == 0) {
            metascoreLabel.setText("Metascore : NC");
        } else {
            metascoreLabel.setText("Metascore : " + model.getMetascore().toString());
        }

        if (model.getImdbRating() == 0) {
            imdbScoreLabel.setText("Imdb Score : NC");
        } else {
            imdbScoreLabel.setText("Imdb Score :  " + model.getImdbRating().toString());
        }

        metaScoreBar.setProgress(model.getMetascore() / 100.0);

        imdbScoreBar.setProgress(model.getImdbRating() / 10.0);

        yearText.setText("Date de Sortie : " + model.getYear());

        langageText.setText("Langue : " + model.getLanguage());

        countryText.setText("Pays : " + model.getCountry());

        genreText.setText("Genre : " + model.getGenre());

        typeText.setText("Type: " + model.getType());

        ratedText.setText("Rated : " + model.getRated());

        awardText.setText("Récompenses : " + model.getAwards());

        actorsText.setText("Acteurs : " + model.getActors());

        linkImdb.setText("http://www.imdb.com/title/" + model.getImdbID());

        progressIndicator.setProgress(model.getLoading());

    }

    @FXML
    public void clickAbout() { // Auteur / Version etc..
        versionLabel.setText(version.toString());
        boiteDialog.setVisible(true);
    }

    @FXML
    public void closeDialogPane() { // Permet de fermer la fenêtre de dialogue
        boiteDialog.setVisible(false);
    }

    @FXML
    public void FullButtonPressed() {
        if (model.getVersion().equals("short")) {
            model.setVersion("full");
        } else {
            model.setVersion("short");
        }
        CliqueSurGo();
    }

    @FXML
    public void clearItem() {
        model.clearAll();
        RechecheField.setText("");
        realisateurLabel.setText("Réalisateur : " + model.getDirector());
        titleLabel.setText(model.getTitle());

        plotText.setText(model.getPlot());
        runtimeLabel.setText("Durée : " + model.getRuntime());

        if (model.getPoster().equals("")) {
            imageAffiche.setImage(new Image("https://www.ginesisnatural.com/images/no_image.jpg"));
        } else {
            imageAffiche.setImage(new Image(model.getPoster()));
        }


        if (model.getMetascore() == 0) {
            metascoreLabel.setText("Metascore : NC");
        } else {
            metascoreLabel.setText("Metascore : " + model.getMetascore().toString());
        }

        if (model.getImdbRating() == 0) {
            imdbScoreLabel.setText("Imdb Score : NC");
        } else {
            imdbScoreLabel.setText("Imdb Score :  " + model.getImdbRating().toString());
        }

        metaScoreBar.setProgress(model.getMetascore() / 100.0);

        imdbScoreBar.setProgress(model.getImdbRating() / 10.0);

        yearText.setText("Date de Sortie : " + model.getYear());

        langageText.setText("Langue : " + model.getLanguage());

        countryText.setText("Pays : " + model.getCountry());

        genreText.setText("Genre : " + model.getGenre());

        typeText.setText("Type: " + model.getType());

        ratedText.setText("Rated : " + model.getRated());

        awardText.setText("Récompenses : " + model.getAwards());

        actorsText.setText("Acteurs : " + model.getActors());

        linkImdb.setText("http://www.imdb.com/title/" + model.getImdbID());

    } // Edition ---> Clear

    @FXML
    public void exitSoft() {
        System.exit(0);
    } // Fichier --> Close

    @FXML
    public void OpenBrowser() throws URISyntaxException {

        model.openWebPage(new URI(linkImdb.getText()));
    }

    public void updateGuiHistorique(Integer historiqueNumber) {

        switch (historiqueNumber) {

            case 1:
                historyLInk1.setText(RechecheField.getText());
                break;

            case 2:
                historyLInk2.setText(RechecheField.getText());
                break;

            case 3:
                historyLInk3.setText(RechecheField.getText());
                break;

            case 4:
                historyLInk4.setText(RechecheField.getText());
                break;

            case 5:
                historyLInk5.setText(RechecheField.getText());
                break;

            case 6:
                historyLInk6.setText(RechecheField.getText());
                break;

            case 7:
                historyLInk7.setText(RechecheField.getText());
                break;

            case 8:
                historyLInk8.setText(RechecheField.getText());
                break;

            case 9:
                historyLInk9.setText(RechecheField.getText());
                break;

            case 10:
                historyLInk10.setText(RechecheField.getText());
                break;

            case 11:
                historyLInk11.setText(RechecheField.getText());
                break;

            case 12:
                historyLInk12.setText(RechecheField.getText());
                break;

            case 13:
                historyLInk13.setText(RechecheField.getText());
                break;

            case 14:
                historyLInk14.setText(RechecheField.getText());
                break;

            case 15:
                historyLInk15.setText(RechecheField.getText());
                break;

        }


    }

    @FXML
    public void clickHistory1() {
        RechecheField.setText(historyLInk1.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory2() {
        RechecheField.setText(historyLInk2.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory3() {
        RechecheField.setText(historyLInk3.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory4() {
        RechecheField.setText(historyLInk4.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory5() {
        RechecheField.setText(historyLInk5.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory6() {
        RechecheField.setText(historyLInk6.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory7() {
        RechecheField.setText(historyLInk7.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory8() {
        RechecheField.setText(historyLInk8.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory9() {
        RechecheField.setText(historyLInk9.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory10() {
        RechecheField.setText(historyLInk10.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory11() {
        RechecheField.setText(historyLInk11.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory12() {
        RechecheField.setText(historyLInk12.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory13() {
        RechecheField.setText(historyLInk13.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory14() {
        RechecheField.setText(historyLInk14.getText());
        CliqueSurGo();
    }

    @FXML
    public void clickHistory15() {
        RechecheField.setText(historyLInk15.getText());
        CliqueSurGo();
    }

    @FXML
    public void EnterPressed() {
        RechecheField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    CliqueSurGo(); // Si on appuie sur entrée, c'est comme si on appuyait sur go
                }
            }
        });
    } // Appuyer sur Entrée


    @FXML
    public void continueGame() {
        continueButton.setVisible(false);
        StartGameTvShow();
    }

    @FXML
    public void RestartGame() {
        scoreGame.setProgress(new Float(0));
        StartGameTvShow();
        retryButton.setVisible(false);
        scoreText.setText("Score : 0/10");
    }

    @FXML
    public void valideReponse1() {

        if (alreadyAnswered) {
            StartGameTvShow();
            return;
        }

        boolean reponse = false;
        alreadyAnswered = true;

        hintButton.setVisible(false);


        if (reponse1Button.getText().equals(model.getListeSerie().get(numeroReponse))) {
            gameTitle.setText("VRAI : " + model.getListeSerie().get(numeroReponse));
            reponse = true;
        } else {
            gameTitle.setText("FAUX : " + model.getListeSerie().get(numeroReponse));
        }
        updateScore(reponse);

        reponse4Button.setDisable(true);
        reponse2Button.setDisable(true);
        reponse3Button.setDisable(true);


    }

    @FXML
    public void valideReponse2() {

        if (alreadyAnswered) {
            StartGameTvShow();
            return;
        }

        boolean reponse = false;

        hintButton.setVisible(false);

        if (reponse2Button.getText().equals(model.getListeSerie().get(numeroReponse))) {
            gameTitle.setText("VRAI : " + model.getListeSerie().get(numeroReponse));
            reponse = true;
        } else {
            gameTitle.setText("FAUX : " + model.getListeSerie().get(numeroReponse));
        }
        updateScore(reponse);

        reponse1Button.setDisable(true);
        reponse4Button.setDisable(true);
        reponse3Button.setDisable(true);

    }

    @FXML
    public void valideReponse3() {

        if (alreadyAnswered) {
            StartGameTvShow();
            return;
        }

        boolean reponse = false;

        hintButton.setVisible(false);


        if (reponse3Button.getText().equals(model.getListeSerie().get(numeroReponse))) {
            gameTitle.setText("VRAI : " + model.getListeSerie().get(numeroReponse));
            reponse = true;
        } else {
            gameTitle.setText("FAUX : " + model.getListeSerie().get(numeroReponse));
        }
        updateScore(reponse);


        reponse1Button.setDisable(true);
        reponse2Button.setDisable(true);
        reponse4Button.setDisable(true);

    }

    @FXML
    public void valideReponse4() {

        if (alreadyAnswered) {
            StartGameTvShow();
            return;
        }

        boolean reponse = false;

        hintButton.setVisible(false);

        System.out.println(reponse4Button.getText() + "==" + model.getListeSerie().get(numeroReponse));

        if (reponse4Button.getText().equals(model.getListeSerie().get(numeroReponse))) {
            gameTitle.setText("VRAI : " + model.getListeSerie().get(numeroReponse));
            reponse = true;
        } else {
            gameTitle.setText("FAUX : " + model.getListeSerie().get(numeroReponse));
        }
        updateScore(reponse);

        reponse1Button.setDisable(true);
        reponse2Button.setDisable(true);
        reponse3Button.setDisable(true);

    }

    @FXML
    public void giveHint() {
        boolean reponse;
        model.setVersion("full");

        InputStream ressource = model.GetFilmByName(model.getListeSerie().get(numeroReponse));
        model.AnalysingXML(ressource);

        gameSynopsis.setText(model.getPlot());


        scoreGame.setProgress(scoreGame.getProgress() - new Float(0.05));

        hintButton.setVisible(false);

    }

    public void updateScore(boolean reponse) {


        if ((scoreGame.getProgress() < 1.0) && (scoreGame.getProgress() >= 0.0)) {

            if (reponse) // Si la réponse est juste
            {
                scoreGame.setProgress(scoreGame.getProgress() + 0.1);
            } else {
                scoreGame.setProgress(scoreGame.getProgress() - 0.1);
            }
        }

        if (scoreGame.getProgress() >= 1) {
            scoreGame.setProgress(-0.1);
            scoreText.setText("GAGNÉÉÉÉÉ");
            continueButton.setVisible(false);
            return;
        }

        if (scoreGame.getProgress() < 0) {
            scoreText.setText("PERDUUUU");
            continueButton.setVisible(false);
            retryButton.setVisible(true);
            return;
        }

        scoreText.setText("Score : " + (int) (scoreGame.getProgress() * 10) + "/10");

        continueButton.setVisible(true);
        afficheGame.setVisible(true);
        afficheGame.setImage(new Image(model.getPoster()));
        alreadyAnswered = true;
        gameTitle.setVisible(true);

    }


    @FXML
    public void StartGameTvShow() {


        model.setVersion("short");
        hintButton.setVisible(true); // on affiche le boutton astuce
        startButton.setVisible(false);
        continueButton.setVisible(false);
        afficheGame.setVisible(false);
        gameTitle.setVisible(false);


        model.initListeSerie();
        model.setLoading(new Float(0));
        scoreGame.setVisible(true);
        scoreText.setVisible(true);
        alreadyAnswered = false;


        reponse1Button.setDisable(false);
        reponse2Button.setDisable(false);
        reponse3Button.setDisable(false);
        reponse4Button.setDisable(false);

        numeroReponse = ((int) (Math.random() * model.getListeSerie().size()));
        int buttonReponse = ((int) (Math.random() * 4));


        InputStream ressource = model.GetFilmByName(model.getListeSerie().get(numeroReponse));
        System.out.println("BONNE REPONSE :" + model.getListeSerie().get(numeroReponse));
        model.AnalysingXML(ressource);

        gameSynopsis.setText(model.getPlot());

        switch (buttonReponse) {

            case 0:
                reponse1Button.setText(model.getListeSerie().get(numeroReponse));
                break;

            case 1:
                reponse2Button.setText(model.getListeSerie().get(numeroReponse));
                break;

            case 2:
                reponse3Button.setText(model.getListeSerie().get(numeroReponse));
                break;

            case 3:
                reponse4Button.setText(model.getListeSerie().get(numeroReponse));
                break;

        }

        int j;
        int k = 0;

        // Les variables ci dessous permettent de ne pas proposer plusieurs choix identiques
        int random1 = -1;
        int random2 = -1;
        int random3 = -1;
        int random4 = -1;


        // Cette boucle permet de créer les boutons de réponses aléatoires
        while (k < 4) {
            System.out.println(k);
            j = ((int) (Math.random() * model.getListeSerie().size()));


            if ((k != buttonReponse) && (j != random1) && (j != random2) && (j != random3) && (j != random4) && (j != numeroReponse)) {
                switch (k) {

                    case 0:
                        reponse1Button.setText(model.getListeSerie().get(j));
                        random1 = j;
                        break;

                    case 1:
                        reponse2Button.setText(model.getListeSerie().get(j));
                        random2 = j;

                        break;

                    case 2:
                        reponse3Button.setText(model.getListeSerie().get(j));
                        random3 = j;

                        break;

                    case 3:
                        reponse4Button.setText(model.getListeSerie().get(j));
                        random4 = j;

                        break;
                }
                k = k + 1;
            } else if (k == buttonReponse) {
                k = k + 1;
            }
        } // Fin du While
    }


    // BUTTONS

    @FXML
    public Button continueButton;

    @FXML
    public Button startButton;

    @FXML
    private Button hintButton;

    @FXML
    private Button retryButton;

    @FXML
    private Button reponse1Button;

    @FXML
    private Button reponse2Button;

    @FXML
    private Button reponse3Button;

    @FXML
    private Button reponse4Button;

    @FXML
    private Button rechercheBouton;


    // TEXT

    @FXML
    private Text gameSynopsis;

    @FXML
    private Text gameTitle;

    @FXML
    private Text countryText;

    @FXML
    private Text actorsText;

    @FXML
    private Text awardText;

    @FXML
    private Text ratedText;

    @FXML
    private Text typeText;

    @FXML
    private Text genreText;

    @FXML
    private Text realisateurLabel;

    @FXML
    private Text langageText;

    @FXML
    private Text yearText;

    @FXML
    private Text runtimeLabel;

    @FXML
    private Text plotText;

    @FXML
    private Text scoreText;

    @FXML
    private Text genreText1;


    // TEXTFIELD

    @FXML
    private TextField RechecheField;

    // PROGRESS BAR

    @FXML
    private ProgressBar scoreGame;

    @FXML
    private ProgressBar metaScoreBar;

    @FXML
    private ProgressBar imdbScoreBar;

    // HYPERLINK

    @FXML
    private Hyperlink linkImdb;

    @FXML
    private Hyperlink historyLInk1;

    @FXML
    private Hyperlink historyLInk2;

    @FXML
    private Hyperlink historyLInk3;

    @FXML
    private Hyperlink historyLInk4;

    @FXML
    private Hyperlink historyLInk5;

    @FXML
    private Hyperlink historyLInk6;

    @FXML
    private Hyperlink historyLInk7;

    @FXML
    private Hyperlink historyLInk8;

    @FXML
    private Hyperlink historyLInk9;

    @FXML
    private Hyperlink historyLInk10;

    @FXML
    private Hyperlink historyLInk11;

    @FXML
    private Hyperlink historyLInk12;

    @FXML
    private Hyperlink historyLInk13;

    @FXML
    private Hyperlink historyLInk14;

    @FXML
    private Hyperlink historyLInk15;


    //IMAGEVIEW

    @FXML
    private ImageView imageAffiche;

    @FXML
    private ImageView afficheGame;

    // LABEL

    @FXML
    private Label metascoreLabel;

    @FXML
    private Label imdbScoreLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label versionLabel;

    // PROGRESS INDICATOR
    @FXML
    private ProgressIndicator progressIndicator;

    // PANE

    @FXML
    private AnchorPane MainScreen;

    @FXML
    private DialogPane boiteDialog;

    @FXML
    private StackPane StackPane;

    @FXML
    private ContextMenu autocompletionGUI;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressIndicator.setProgress(-1);

        autocompleteTextfield = new Autocomplete(RechecheField,autocompletionGUI);
        autocompleteTextfield.setAction(new Action() {
            @Override
            public List<? extends Object> methodForGettingItem(String search) {
                // Ici on va renvoyé une liste d'objet qui sera affiché dans votre liste lorsque l'user tapera qqchose dans le textSearch.
                // Il faudra donc Override la methode toString de votre objet.
                // Dans mon cas, ce sont des actions qui sont réenvoyé lorsque la methode startRequest est appelé.

                return model.getAutocomplete(search);
            }

            @Override
            public void methodWhenAnItemIsSelected(Object obj) {

                // Ici on va appeler les methodes que vous souhaitez éxécuter lorsque le client aura selectionné un item.
                // L'objet nommé obj, est l'Object selectionné par l'utilisateur.
                if(obj instanceof ItemFilm) {
                    model.setTitle(((ItemFilm) obj).toString());
                    CliqueSurGo();
                }
            }
        });
    }


}

