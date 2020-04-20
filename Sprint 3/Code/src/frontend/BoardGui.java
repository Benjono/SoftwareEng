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

public class BoardGui extends GridPane {

    GameMasterGui GM;
    ImageView[] playerImages;

    public BoardGui(GameMasterGui GM, int numPlayers){
        this.GM = GM;
        setupBoard();
        setupTokens(numPlayers);
    }

        /*
    UNUSED MAY BE NEEDED IN FUTURE
    public synchronized void waitBetweenMovements(){

        try {
            wait(1000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ahhh");
        }
    }
     */

    private void setupBoard(){
        int tileSize = 64;

        for(int i=0; i<40; i++) {
            Pane square = new Pane();
            //System.out.println(GM.getBoard().getTile(i).getClass());
            if (GM.getTile(i).getBuyable()){
                //System.out.println(GM.getBoard().getTile(i).getName());
                int currentTile = i;
                square.setOnMouseClicked(mouseEvent -> {
                    try {
                        new ShowTileInfoDialog((BuyableTile)GM.getTile(currentTile),GM);
                    } catch (InvalidHouseSetupException e) {
                        e.printStackTrace();
                    }
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

    public void movePlayer(){
        ImageView playerSprite = playerImages[GM.getCurTurn()];
        int[] oldCoordinates = boardCoordinates(GM.getPlayer(GM.getCurTurn()).getPlace());
        Iterator<Node> children = this.getChildren().iterator();
        while (children.hasNext()) {
            Node n = children.next();
            if (GridPane.getRowIndex(n) == oldCoordinates[1] && GridPane.getColumnIndex(n) == oldCoordinates[0]) {
                //((Pane) n).getChildren().remove(playerSprite);
                break;
            }
        }
        new ShowRollDialog(GM.moveNextPiece(),GM);
        int[] newCoordinates = boardCoordinates(GM.getPlayer(GM.getCurTurn()).getPlace());
        //Look at each Pane() object within gridPane()
        while (children.hasNext()) {
            Node n = children.next();
            if (GridPane.getRowIndex(n) == newCoordinates[1] && GridPane.getColumnIndex(n) == newCoordinates[0]) { //
                //System.out.println("ok");
                playerSprite = setSpriteRotation(playerSprite);
                ((Pane) n).getChildren().add(playerSprite);

                GM.landedTileAction();
                break;
            } else {
                //System.out.println("err");
                playerSprite = setSpriteRotation(playerSprite);
                ((Pane) n).getChildren().add(playerSprite);
                //m.waitBetweenMovements();
                //((Pane) n).getChildren().remove(playerSprite);
            }
            if (!children.hasNext()) {
                children = this.getChildren().iterator();
            }
        }
    }

    /**
     * returns current position in grid pane
     * @param position
     * @return int[]
     * @author Joe C
     * @author Joe L
     */
    private int[] boardCoordinates(int position){
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
    public ImageView setSpriteRotation(ImageView imageView){
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
    public ImageView tokenImage(Tokens playerToken){
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
