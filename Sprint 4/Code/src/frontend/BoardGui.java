package frontend;

import backend.BuyableTile;
import backend.InvalidHouseSetupException;
import backend.Tokens;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Iterator;

/**
 * The code for controlling how the board works, extends GridPane in order to correctly setup board and tokens
 * when created in the Gui class.
 * @author Joe C
 */

public class BoardGui extends GridPane {

    GameMasterGui GM;
    ImageView[] playerImages;

    public BoardGui(GameMasterGui GM, int numPlayers){
        this.GM = GM;
        setupBoard();
        setupTokens(numPlayers);
    }

    /**
     * Setup the board with all tiles images and panes
     * @author Joe C
     * @author Alex
     * @author Joe L
     */
    private void setupBoard(){
        int tileSize = 64;

        for(int i=0; i<40; i++) {
            Pane square = new Pane();
            if (GM.getTile(i).getBuyable()){
                int currentTile = i;
                square.setOnMouseClicked(mouseEvent -> {
                    new ShowTileInfoDialog((BuyableTile)GM.getTile(currentTile),GM, currentTile, this);
                });
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

            this.add(square, boardCoordinates(i)[0], boardCoordinates(i)[1]);
        }

        this.setGridLinesVisible(false);
        this.setMaxSize(1000, 1000);
    }

    /**
     * Adds the inital Token Images to the board at GO
     * @param numPlayers
     * @author Joe C
     * @author Ben
     */
    private void setupTokens(int numPlayers) {
        // Setting initial position of tokens
        playerImages = new ImageView[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerImages[i] = tokenImage(GM.getPlayer(i).getToken());
            for (Node n : this.getChildren()) {
                if (GridPane.getRowIndex(n) == boardCoordinates(0)[1] && GridPane.getColumnIndex(n) == boardCoordinates(0)[0]) {
                    setSpriteRotation(playerImages[i]);
                    ((Pane) n).getChildren().add(playerImages[i]);
                }
            }
        }
        setBoardRotation();
    }

    /**
     * Moves a player in the Gridpane
     * @author Joe C
     * @author Ben
     */
    public void movePlayer(){

        int currentPlace = GM.getPlayer(GM.getCurTurn()).getPlace();
        int[] roll = GM.moveNextPiece();
        if (roll != null){
            new ShowRollDialog(roll,GM);
            passedGo(roll,currentPlace);
            removePlayer(currentPlace);
            addPlayer();
            GM.landedTileAction();
            removePlayer(GM.getPlayer(GM.getCurTurn()).getPlace());
            addPlayer();
        }

    }

    private void passedGo(int[] roll, int currentPlace){
        if(currentPlace + roll[0] + roll[1] >= 40){
            new TileEffectDialog(GM, true, 0, null, GM.getTile(GM.getPlayer(GM.getCurTurn()).getPlace()));
        }
    }

    /**
     * Removes a player in the Gridpane
     * @author Joe C
     * @author Ben
     */
    public void removePlayer(int currentPlace){
        ImageView playerSprite = playerImages[GM.getCurTurn()];
        findPane(boardCoordinates(currentPlace)).getChildren().remove(playerSprite);
    }

    /**
     * Adds a player to the Gridpane
     * @author Joe C
     * @author Ben
     */
    public void addPlayer(){
        ImageView playerSprite = playerImages[GM.getCurTurn()];
        playerSprite = setSpriteRotation(playerSprite);
        findPane(boardCoordinates(GM.getPlayer(GM.getCurTurn()).getPlace())).getChildren().add(playerSprite);

    }

    /**
     * Given coordinates in integers it returns the correct Pane in the GridPane
     * @param coordinates
     * @return
     * @author Joe C
     */
    public Pane findPane(int[] coordinates){
        for (Node n : this.getChildren()) {
            if (GridPane.getRowIndex(n) == coordinates[1] && GridPane.getColumnIndex(n) == coordinates[0]) {
                return (Pane) n;
            }
        }
        return null;
    }

    /**
     * returns current position in grid pane
     * @param position
     * @return int[]
     * @author Joe C
     * @author Joe L
     */
    public int[] boardCoordinates(int position){
        if (position <= 9){ return new int[]{0,10 - position % 10}; }
        else if (position <= 19){ return new int[]{position % 10, 0}; }
        else if (position <= 29){ return new int[]{10, position % 10};}
        else if (position <= 39){ return new int[]{10 - position % 10, 10};}
        else {return new int[]{0,0};}
    }

    /**
     * Implements rotation of the Board
     * @author Joe C
     */
    public void setBoardRotation(){
        int place = GM.getPlayer(GM.getCurTurn()).getPlace();
        if(place < 10){ this.setRotate(270); }
        else if (place < 20){ this.setRotate(180);}
        else if (place < 31){ this.setRotate(90);}
        else{ this.setRotate(0);}
    }

    /**
     * Implements rotation of the Player sprite (same as above I know...)
     * @param  imageView
     * @author Joe C
     */
    private ImageView setSpriteRotation(ImageView imageView){
        int place = GM.getPlayer(GM.getCurTurn()).getPlace();
        if(place < 10){ imageView.setRotate(90); }
        else if (place < 20){ imageView.setRotate(180);}
        else if (place < 31){ imageView.setRotate(270);}
        else{ imageView.setRotate(0);}
        return imageView;

    }

    /**
     * Takes the player token and returns an ImageView for that Token
     * @param playerToken
     * @author Joe L
     */
    private ImageView tokenImage(Tokens playerToken){
        ImageView out = null;
        if(playerToken.equals(Tokens.Boot)){
            Image img = new Image("player_boot.png", 30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.Goblet)){
            Image img = new Image("player_goblet.png",30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.Cat)){
            Image img = new Image("player_cat.png",30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.HatStand)){
            Image img = new Image("player_hatstand.png",30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.SmartPhone)){
            Image img = new Image("player_smartphone.png",30, 30, false, true);
            out = new ImageView(img);
        } else if (playerToken.equals(Tokens.Spoon)){
            Image img = new Image("player_spoon.png",30, 30, false, true);
            out = new ImageView(img);
        }
        return out;
    }
}
