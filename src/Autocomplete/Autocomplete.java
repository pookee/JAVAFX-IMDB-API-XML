package Autocomplete;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by gfitas on 04/12/15.
 */
public class Autocomplete {
    private TextField textSearch;
    private ContextMenu textSearchContextMenu;
    private Action action;
    public Autocomplete(TextField textSearch, ContextMenu textSearchContextMenu){
        this.textSearch = textSearch;
        this.textSearchContextMenu = textSearchContextMenu;
        textSearch.setContextMenu(textSearchContextMenu);
    }
    public void setAction(Action action) {
        this.action = action;
        this.textSearch.setContextMenu(textSearchContextMenu);
        this.textSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            this.keyPressed();
        });
    }
    private void keyPressed(){
        Thread thread = new Thread(){
            @Override
            public void run(){
                List<? extends Object> listOfObj = action.methodForGettingItem(textSearch.getText());
                if(listOfObj==null)
                    return;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        textSearchContextMenu.getItems().clear();
                        for( final Object Obj : listOfObj) {
                            MenuItem menuItem = new MenuItem(Obj.toString());
                            textSearchContextMenu.getItems().add(menuItem);
                            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent t) {
                                    textSearch.setText(Obj.toString());
                                    action.methodWhenAnItemIsSelected(Obj);
                                }
                            });
                        }
                        textSearch.getContextMenu().show(textSearch, Side.BOTTOM, 0, 0);
                    }
                });
            }
        };
        thread.start();
    }
}
