package frontend;

import backend.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * Every tile effect that requires no input from player is outputted to the player via this dialog;
 * Passing Go, Going to Jail, being in Jail, the end of the game, the player losing, landing on any owned buyable tile,
 * tax, free parking, and card draw.
 */
public class TileEffectDialog extends MonopolyDialog {

    // Passing Go
    public TileEffectDialog(){
        this.setHeaderText("You passed Go!");
        setup(new Label("Collect £200!"));
    }

    // In Jail
    public TileEffectDialog(GameMasterGui GM){
        this.setHeaderText("You are currently in Jail");
        if (GM.getPlayer(GM.getCurTurn()).getJailTime() == 1){
            setup(new Label("Your sentence currently is " + GM.getPlayer(GM.getCurTurn()).getJailTime() + " more turn."));
        }
        else{
            setup(new Label("Your sentence currently is " + GM.getPlayer(GM.getCurTurn()).getJailTime() + " more turns."));
        }

    }

    //End of Game or the Player has lost
    public TileEffectDialog(GameMasterGui GM, int playerWon){
        if(playerWon != -1){
            this.setHeaderText(GM.getPlayer(playerWon) + "has Won!");
            this.setResultConverter(buttonType -> {
                if (buttonType == this.getDialogPane().getButtonTypes().get(0)){System.exit(0);}
                return null;
            });
            setup(new Label("Well played. GG!"));
        }
        else {
            this.setHeaderText(GM.getPlayer(GM.getCurTurn()) + " has Lost");
            setup(new Label("You ran out of money! All brought tiles are being sold back to the bank."));
        }
    }

    //buying tiles tax free parking
    public  TileEffectDialog(int money, Tile curTile){
        if(curTile instanceof BuyableTile){
            this.setHeaderText("You are losing money");
            setup(new Label("You pay rent worth £" + money + " to " + ((BuyableTile) curTile).getPlayer().getToken().name()));
        }
        else if (curTile instanceof Tax){
            this.setHeaderText("The authorities are on to you!");
            setup(new Label("You need to pay £" + money + " to silence the government."));
        }
        else if (curTile instanceof FreeParking){
            this.setHeaderText("You landed on Free Parking");
            setup(new Label("You gained £" + money + " from a bagel."));
        }

    }

    // to jail
    public TileEffectDialog(Tile curTile){
        if (curTile instanceof toJail){
            this.setHeaderText("The authorities have found you....");
            setup(new Label("...you have been done for many bad things and are sent to Jail!"));
        }

    }

    //cardDraw
    public TileEffectDialog(Card card){
        this.setHeaderText("You have drawn a " + card.getCardType() + " card");
        Label label = new Label("It says: " + card.getCardText());
        if (card.getMethodName().equals("throw")){
            label.setWrapText(true);
            this.getDialogPane().setContent(label);
            ButtonType payFine = new ButtonType("Pay Fine", ButtonBar.ButtonData.YES);
            ButtonType takeCard = new ButtonType("Draw Card", ButtonBar.ButtonData.NO);
            this.getDialogPane().getButtonTypes().addAll(payFine,takeCard);
            this.setResultConverter(buttonType -> {
                if(buttonType == payFine){
                    return true;
                }
                return false;
            });
            this.showAndWait();
        }
        else {
            setup(label);
        }
    }

    public TileEffectDialog(Player player, Tile curTile){
        this.setHeaderText(player.getToken().name() + " won the auction!");
        setup(new Label("They now own " + curTile.getName()));
    }

    /**
     * setups the ok button and shows the dialog
     */
    private void setup(Label label){
        label.setWrapText(true);
        this.getDialogPane().setContent(label);
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(ok);
        this.showAndWait();
    }
}
