package frontend;

import backend.GameMaster;
import backend.Tokens;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;

/**
 * GUI implementation
 * @author Joe C
 * @author Alex
 * @author Tom
 * @author Joe L
 */
public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //start of player number dialog box
        Dialog<Integer> dialogNumPlayers = new Dialog<>();
        dialogNumPlayers.setTitle("Property Tycoon");
        dialogNumPlayers.setHeaderText("Select the number of players.");
        dialogNumPlayers.setResizable(false);
        Label playerLabel = new Label("Player Count: ");
        ComboBox<Integer> playerCombo = new ComboBox<>();
        // change later
        playerCombo.getItems().addAll(2,3, 4, 5, 6);
        playerCombo.setValue(2);
        HBox hboxPlayerCount = new HBox();
        HBox.setHgrow(playerLabel, Priority.ALWAYS);
        HBox.setHgrow(playerCombo, Priority.ALWAYS);
        hboxPlayerCount.setMaxHeight(50);
        hboxPlayerCount.setMinHeight(50);
        hboxPlayerCount.setSpacing(15);
        hboxPlayerCount.setAlignment(Pos.CENTER);
        hboxPlayerCount.getChildren().addAll(playerLabel, playerCombo);
        dialogNumPlayers.getDialogPane().setContent(hboxPlayerCount);

        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogNumPlayers.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialogNumPlayers.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                System.out.println(playerCombo.getValue());
                return playerCombo.getValue();
            }
            return null;
        });
        dialogNumPlayers.showAndWait();
        // end of player number dialog box



        //start of player selection dialog box
        HBox hboxPlayerTokens = new HBox();
        Dialog<Tokens[]> dialogPlayerTokens = new Dialog<>();
        dialogPlayerTokens.setTitle("Property Tycoon");
        dialogPlayerTokens.setHeaderText("Select the tokens you want to play with.");
        dialogPlayerTokens.setResizable(false);
        Tokens[] allTokens = {Tokens.Boot, Tokens.Cat, Tokens.Goblet, Tokens.HatStand, Tokens.SmartPhone, Tokens.Spoon};
        VBox hBoxHolder = new VBox();
        HBox[] hBoxArray = new HBox[dialogNumPlayers.getResult()];
        ComboBox[] tokenCombos = new ComboBox[dialogNumPlayers.getResult()];
        for (int i =0; i<dialogNumPlayers.getResult() ;i++){
            System.out.println("loop");
            playerLabel = new Label("Player " + (i+1));
            hBoxArray[i] = new HBox();
            hBoxArray[i].setMaxHeight(50);
            hBoxArray[i].setMinHeight(50);
            hBoxArray[i].setSpacing(15);
            hBoxArray[i].setAlignment(Pos.CENTER);
            tokenCombos[i]= new ComboBox<Tokens>();
            tokenCombos[i].getItems().addAll(allTokens);
            tokenCombos[i].setValue(Tokens.Boot);
            hBoxArray[i].getChildren().addAll(playerLabel, tokenCombos[i]);

        }
        hBoxHolder.getChildren().addAll(hBoxArray);
        dialogPlayerTokens.getDialogPane().setContent(hBoxHolder);


        dialogPlayerTokens.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialogPlayerTokens.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk){
                Tokens[] outputList = new Tokens[dialogNumPlayers.getResult()];
                for (int i =0; i<dialogNumPlayers.getResult() ;i++){
                    outputList[i] = (Tokens) tokenCombos[i].getValue();
                }
                return outputList;
            }
            return null;
        });
        dialogPlayerTokens.showAndWait();
        // end of player token dialog box

        //Initialise the GameMaster
        Integer numPlayers = dialogNumPlayers.getResult();
        Tokens[] playerTokens = dialogPlayerTokens.getResult();
        GameMaster gameMaster = new GameMaster();
        gameMaster.setup(numPlayers, playerTokens);

        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp, 900, 600);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Property Tycoon");
        primaryStage.setScene(scene);
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.show();
        Stage stage = primaryStage;
    }
}
