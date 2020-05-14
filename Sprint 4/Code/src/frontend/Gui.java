package frontend;

import backend.Tokens;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * GUI implementation; starts initial process for GUI,
 * calls all necessary methods and classes relating to the GUI
 * also displays the overall screen to the player.
 * @author Joe C
 * @author Alex
 * @author Tom
 * @author Joe L
 * @author Jonathan Morris
 */
public class Gui extends Application {

    GameMasterGui GM;
    boolean[] numPlayers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Property Tycoon");
        primaryStage.getIcons().add(new Image("logo.png"));
        // XGA screen size 1024 x 768
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        primaryStage.setResizable(false);

        //Title Screen
        BorderPane titleScreen = new BorderPane();
        Text text = new Text("Property\nTycoon");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setLineSpacing(10);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 100));
        titleScreen.setCenter(text);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        Button playAbridged = new Button("Play Abridged Game");
        Button playFull = new Button("Play Full Game");
        playAbridged.setOnAction(actionEvent -> startGame(primaryStage,true));
        playFull.setOnAction(actionEvent -> startGame(primaryStage, false));
        hBox.getChildren().addAll(playAbridged,playFull);
        titleScreen.setBottom(hBox);
        Scene scene = new Scene(titleScreen, 1024,768);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void startGame(Stage primaryStage, boolean abridged){
        //setup dialogues and GameMaster
        int abridgedValue = -1;
        if (abridged){
            abridgedValue = (int) new AbridgedDialog().getR();
        }
        numPlayers = (boolean[]) (new NumberOfPlayersAndAIDialog()).getR();
        Tokens[] playerTokens = (Tokens[]) (new SelectingTokensDialog(numPlayers.length)).getR();

        GM = new GameMasterGui(numPlayers, playerTokens, abridgedValue);
        primaryStage.close();

        //Main gameplay screen
        BorderPane gameScreen = new BorderPane();
        VBox alignV = new VBox();
        HBox alignH = new HBox();
        alignV.setAlignment(Pos.CENTER);
        alignH.setAlignment(Pos.CENTER);
        alignV.getChildren().add(alignH);
        gameScreen.setCenter(alignV);
        //Game board
        BoardGui boardGui = new BoardGui(GM,numPlayers.length);
        alignH.getChildren().add(boardGui);
        //Right tab
        gameScreen.setRight(new SideTabGui(GM,boardGui, numPlayers.length));

        //Scene
        Scene scene = new Scene(gameScreen, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}