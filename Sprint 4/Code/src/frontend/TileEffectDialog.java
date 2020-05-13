package frontend;

import backend.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Every tile effect that requires no input from player is outputted to the player via this dialog;
 * Passing Go, Going to Jail, being in Jail, the end of the game, the player losing, landing on any owned buyable tile,
 * tax, free parking, and card draw.
 */
public class TileEffectDialog extends MonopolyDialog {

    // Passing Go
    public TileEffectDialog(){
        this.setHeaderText("You passed Go!");
        this.setContentText("Collect £200!");
        setup();
    }

    // In Jail
    public TileEffectDialog(GameMasterGui GM){
        this.setHeaderText("You are currently in Jail");
        if (GM.getPlayer(GM.getCurTurn()).getJailTime() == 1){
            this.setContentText("Your sentence currently is " + GM.getPlayer(GM.getCurTurn()).getJailTime() + " more turn.");
        }
        else{
            this.setContentText("Your sentence currently is " + GM.getPlayer(GM.getCurTurn()).getJailTime() + " more turns.");
        }
        setup();
    }

    //End of Game or the Player has lost
    public TileEffectDialog(GameMasterGui GM, int playerWon){
        if(playerWon != -1){
            this.setHeaderText(GM.getPlayer(playerWon) + "has Won!");
            this.setContentText("Well played. GG!");
            this.setResultConverter(buttonType -> {
                if (buttonType == this.getDialogPane().getButtonTypes().get(0)){System.exit(0);}
                return null;
            });
        }
        else {
            this.setHeaderText(GM.getPlayer(GM.getCurTurn()) + " has Lost");
            this.setContentText("You ran out of money! All brought tiles are being sold back to the bank.");
        }
        setup();
    }

    //buying tiles tax free parking
    public  TileEffectDialog(int money, Tile curTile){
        if(curTile instanceof BuyableTile){
            this.setHeaderText("You are losing money");
            this.setContentText("You pay rent worth £" + money + " to " + ((BuyableTile) curTile).getPlayer().getToken().name());
        }
        else if (curTile instanceof Tax){
            this.setHeaderText("The authorities are on to you!");
            this.setContentText("You need to pay £" + money + " to silence the government.");
        }
        else if (curTile instanceof FreeParking){
            this.setHeaderText("You landed on Free Parking");
            this.setContentText("You gained £" + money + " from a bagel.");
        }
        setup();
    }

    // to jail
    public TileEffectDialog(Tile curTile){
        if (curTile instanceof toJail){
            this.setHeaderText("The authorities have found you....");
            this.setContentText("...you have been done for many bad things and are sent to Jail! \uD83D\uDE25");
        }
        setup();
    }

    //cardDraw
    public TileEffectDialog(Card card, Tile curTile){
        if (curTile instanceof CardDraw){
            this.setHeaderText("You have drawn a " + curTile.getName() + " card");
            this.setContentText("It says: " + card.getCardText());
        }
        setup();
    }

    public TileEffectDialog(Player player, Tile curTile){
        this.setHeaderText(player.getToken().name() + " won the auction!");
        this.setContentText("They now own " + curTile.getName());
        setup();
    }

    /**
     * setups the ok button and shows the dialog
     */
    private void setup(){
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(ok);
        this.showAndWait();
    }
}
