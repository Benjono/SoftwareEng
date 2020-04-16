package frontend;

import backend.*;
import com.sun.media.jfxmedia.events.NewFrameEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.management.monitor.MonitorSettingException;
import javax.swing.*;
import java.util.Iterator;
import java.util.Optional;

public class Methods {

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

    /**
     * Implements rotation of the Board
     * @param GM
     * @param  gp
     * @author Joe C
     */
    public GridPane setBoardRotation(GameMaster GM, GridPane gp){
        int place = GM.getPlayer(GM.getCurTurn()).getPlace();
        if(place < 10){ gp.setRotate(270); }
        else if (place < 20){ gp.setRotate(180);}
        else if (place < 31){ gp.setRotate(90);}
        else{ gp.setRotate(0);}
        return gp;
    }

    /**
     * Implements rotation of the Player sprite (same as above I know...)
     * @param GM
     * @param  imageView
     * @author Joe C
     */
    public ImageView setSpriteRotation(GameMaster GM, ImageView imageView){
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

    /**
     * adds a highlighted background to the label that represents the currently active player in the player tab
     * updates player's money, i here relates to the ith row in the list
     * @param GM
     * @param sideTab
     * @author Joe L
     * @author Joe C
     */

    public void updateSideTab(GameMaster GM, VBox sideTab){
        int playerNumber = 0;
        for(int i = 1; i < sideTab.getChildren().size()-2; i += 4){
            if (playerNumber == GM.getCurTurn()){
                sideTab.getChildren().get(i).setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
            }
            else{
                sideTab.getChildren().get(i).setStyle("");
            }
            sideTab.getChildren().set(i+2, new Label("  Money: " + GM.getPlayer(playerNumber).getMoney()));
            sideTab.getChildren().set(i+3, new Label("  Place: " + GM.getBoard().getTile(GM.getPlayer(playerNumber).getPlace()).getName()));
            playerNumber++;
        }
    }

    /**
     * returns current position in grid pane
     * @param position
     * @return int[]
     * @author Joe C
     * @author Joe L
     */
    public int[] coordinates(int position){
        if (position <= 9){ return new int[]{0,10 - position % 10}; }
        else if (position <= 19){ return new int[]{position % 10, 0}; }
        else if (position <= 29){ return new int[]{10, position % 10};}
        else if (position <= 39){ return new int[]{10 - position % 10, 10};}
        else {return new int[]{0,0};}
    }

    public void movePlayer(GameMaster GM, ImageView[] playerImages, GridPane gp){
        ImageView playerSprite = playerImages[GM.getCurTurn()];
        int[] oldCoordinates = coordinates(GM.getPlayer(GM.getCurTurn()).getPlace());
        Iterator<Node> children = gp.getChildren().iterator();
        while (children.hasNext()) {
            Node n = children.next();
            if (GridPane.getRowIndex(n) == oldCoordinates[1] && GridPane.getColumnIndex(n) == oldCoordinates[0]) {
                //((Pane) n).getChildren().remove(playerSprite);
                break;
            }
        }
        new ShowRollDialog(GM.moveNextPiece(),GM);
        int[] newCoordinates = coordinates(GM.getPlayer(GM.getCurTurn()).getPlace());
        //Look at each Pane() object within gridPane()
        while (children.hasNext()) {
            Node n = children.next();
            if (GridPane.getRowIndex(n) == newCoordinates[1] && GridPane.getColumnIndex(n) == newCoordinates[0]) { //
                //System.out.println("ok");
                playerSprite = setSpriteRotation(GM,playerSprite);
                ((Pane) n).getChildren().add(playerSprite);

                landedTileAction(GM);
                break;
            } else {
                //System.out.println("err");
                playerSprite = setSpriteRotation(GM,playerSprite);
                ((Pane) n).getChildren().add(playerSprite);
                //m.waitBetweenMovements();
                //((Pane) n).getChildren().remove(playerSprite);
            }
            if (!children.hasNext()) {
                children = gp.getChildren().iterator();
            }
        }
    }

    private void landedTileAction(GameMaster GM) {
        if (GM.getBuyable(GM.getPlayer(GM.getCurTurn()).getPlace())){
            //can buy/auction time
            if(!(Boolean) (new FirstTimeLandedDialog(GM)).getR()){
                // auction
                GM.applyTileEffect((int[]) (new AuctionDialog(GM)).getR());
                // winner dialog
                System.out.println(((BuyableTile)GM.getBoard().getTileGrid()[GM.getPlayer(GM.getCurTurn()).getPlace()]).getPlayer().getToken());

                //needs winner dialog here
            }
            else {
                // player buys tile
                GM.applyTileEffect(GM.getPlayer(GM.getCurTurn()));
            }
        }
        else{
            //rent time
        }
    }

}
