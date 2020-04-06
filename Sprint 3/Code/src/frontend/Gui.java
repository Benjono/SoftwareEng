package frontend;

import backend.GameMaster;
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

/**
 * GUI implementation
 * @author Joe C
 * @author Alex
 * @author Tom
 * @author Joe L
 * @author Jonathan Morris
 */
public class Gui extends Application {

    GameMaster GM;
    GridPane gp;
    VBox sideTab;
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
        GM = new GameMaster();
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
            //System.out.println(GM.getBoard().getTile(i).getClass());
            if (GM.getTile(i).getBuyable()){
                //System.out.println(GM.getBoard().getTile(i).getName());
                int currentTile = i;
                square.setOnMouseClicked(mouseEvent -> m.showTileInfo(currentTile,GM));
            }
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

            gp.add(square, m.coordinates(i)[0], m.coordinates(i)[1]);
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
        ImageView[] playerImages = new ImageView[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerImages[i] = m.tokenImage(GM.getPlayer(i).getToken());
            for (Node n : gp.getChildren()) {
                if (GridPane.getRowIndex(n) == m.coordinates(0)[1] && GridPane.getColumnIndex(n) == m.coordinates(0)[0]) {
                    m.setSpriteRotation(GM, playerImages[i]);
                    ((Pane) n).getChildren().add(playerImages[i]);
                }
            }
        }
        gp = m.setBoardRotation(GM,gp);

        //Right tab
        //add active players and maybe highlight current turn player
        sideTab = new VBox();
        gameScreen.getChildren().add(sideTab);
        sideTab.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        sideTab.setPrefWidth(200);
        //Player title
        Label pTabTitle = new Label("Current Turn");
        sideTab.getChildren().add(pTabTitle);
        //Player name display
        Label[] playerList = new Label[numPlayers];
        Label[] playerMoney = new Label[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerList[i] = new Label("Player " + (i+1));
            Label token = new Label("   Token: " + GM.getPlayer(i).getToken().name());
            playerMoney[i] = new Label("    Money: " + GM.getPlayer(i).getMoney());
            Label place = new Label("   Place: " + GM.getBoard().getTile(GM.getPlayer(i).getPlace()).getName());
            sideTab.getChildren().addAll(playerList[i], token, playerMoney[i], place);
        }
        //button next turn and roll dice
        Button diceRollNextTurn = new Button("Roll dice");
        diceRollNextTurn.setPrefSize(100, 100);
        sideTab.getChildren().add(diceRollNextTurn);

        m.updateSideTab(GM, sideTab); //player 1 will have a highlighted label
        diceRollNextTurn.setOnAction(new EventHandler<>() {
            @Override
            public synchronized void handle(ActionEvent event) {
                if (!GM.isCanNextTurn()) {
                    m.movePlayer(GM,playerImages,gp);
                    if (GM.isCanNextTurn()) {
                        diceRollNextTurn.setText("Next Turn");
                    } else {
                        gp = m.setBoardRotation(GM, gp);
                    }

                } else {
                    GM.nextTurn();
                    m.updateSideTab(GM,sideTab);//the player with the current turn will have a highlighted label
                    gp = m.setBoardRotation(GM, gp);
                    diceRollNextTurn.setText("Roll Dice");
                }

            }
        });
    }

}