package frontend;

import backend.Player;
import backend.Tokens;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Iterator;

/**
 * GUI implementation
 * @author Joe C
 * @author Alex
 * @author Tom
 * @author Joe L
 * @author Jonathan Morris
 */
public class Gui extends Application {

    backend.GameMaster GM;
    GridPane gp;
    Methods m;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //setup dialogues
        m = new Methods();
        int numPlayers = m.doDialoguePlayers();
        Tokens[] playerTokens = m.doPlayerTokens(numPlayers);
        GM = new backend.GameMaster();
        GM.setup(numPlayers, playerTokens);

        //Main gameplay screen
        HBox gameScreen = new HBox();
        gameScreen.setAlignment(Pos.CENTER);

        //Game board
        gp = new GridPane();
        gameScreen.getChildren().add(gp);
        //board tiles
        //setting correct sizes for screen
        int tileSize = 64;

        for(int i=0; i<40; i++) {
            Pane square = new Pane();
            Image tileImg = new Image("tile_" + GM.getBoard().getTile(i).getName().toLowerCase().replaceAll("\\s+","") + ".png");
            ImageView tile = new ImageView(tileImg);
            if (i < 11){tile.setRotate(90);}
            else if (i < 20){tile.setRotate(180);}
            else if (i < 31){tile.setRotate(270);}
            tile.setFitHeight(tileSize);
            tile.setFitWidth(tileSize);
            tile.setPreserveRatio(true);
            square.setMinSize(tileSize,tileSize);
            square.getChildren().add(tile);

            gp.add(square, m.coords(i)[0], m.coords(i)[1]);
        }

        gp.setGridLinesVisible(false);
        gp.setMaxSize(1000, 1000);

        //Scene
        Scene scene = new Scene(gameScreen, 1280, 768);
        primaryStage.setTitle("Property Tycoon");
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setScene(scene);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(768);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Setting initial position of tokens
        Player[] players = GM.getPlayers();
        ImageView[] playerImages = new ImageView[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerImages[i] = m.tokenImage(players[i].getToken());
            for (Node n : gp.getChildren()) {
                if (GridPane.getRowIndex(n) == m.coords(0)[1] && GridPane.getColumnIndex(n) == m.coords(0)[0]) {
                    ((Pane) n).getChildren().add(playerImages[i]);
                }
            }
        }
        gp = m.setBoardRotation(GM,gp);

        //Right tab
        //add active players and maybe highlight current turn player
        VBox sideTab = new VBox();
        gameScreen.getChildren().add(sideTab);
        sideTab.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        sideTab.setPrefWidth(400);
        //Player title
        Label pTabTitle = new Label("Current Turn");
        sideTab.getChildren().add(pTabTitle);
        //Player name display
        Label[] playerList = new Label[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerList[i] = new Label("Player " + (i + 1));
            sideTab.getChildren().add(playerList[i]);
        }
        //button next turn and roll dice
        Button diceRollNextTurn = new Button("Roll dice");
        diceRollNextTurn.setPrefSize(220, 120);
        sideTab.getChildren().add(diceRollNextTurn);

        m.changeActiveColour(GM.getCurTurn(), playerList); //player 1 will have a highlighted label
        diceRollNextTurn.setOnAction(new EventHandler<>() {
            @Override
            public synchronized void handle(ActionEvent event) {
                if (!GM.isCanNextTurn()) {
                    ImageView playerSprite = playerImages[GM.getCurTurn()];
                    int[] oldCoords = m.coords(GM.getPlayer(GM.getCurTurn()).getPlace());
                    Iterator<Node> children = gp.getChildren().iterator();
                    while (children.hasNext()) {
                        Node n = children.next();
                        if (GridPane.getRowIndex(n) == oldCoords[1] && GridPane.getColumnIndex(n) == oldCoords[0]) {
                            ((Pane) n).getChildren().remove(playerSprite);
                            break;
                        }
                    }
                    GM.moveNextPiece();
                    int[] newCoords = m.coords(GM.getPlayer(GM.getCurTurn()).getPlace());
                    //Look at each Pane() object within gridPane()
                    while (children.hasNext()) {
                        Node n = children.next();
                        if (GridPane.getRowIndex(n) == newCoords[1] && GridPane.getColumnIndex(n) == newCoords[0]) { //
                            playerSprite = m.setSpriteRotation(GM,playerSprite);
                            ((Pane) n).getChildren().add(playerSprite);
                            break;
                        } else {
                            playerSprite = m.setSpriteRotation(GM,playerSprite);
                            ((Pane) n).getChildren().add(playerSprite);
                            //m.waitBetweenMovements();
                            ((Pane) n).getChildren().remove(playerSprite);
                        }
                        if (!children.hasNext()) {
                            children = gp.getChildren().iterator();
                        }
                    }
                    if (GM.isCanNextTurn()) {
                        diceRollNextTurn.setText("Next Turn");
                    } else {
                        gp = m.setBoardRotation(GM, gp);
                    }

                } else {
                    GM.nextTurn();
                    m.changeActiveColour(GM.getCurTurn(), playerList);//the player with the current turn will have a highlighted label
                    gp = m.setBoardRotation(GM, gp);
                    diceRollNextTurn.setText("Roll Dice");
                }

            }
        });
    }

}