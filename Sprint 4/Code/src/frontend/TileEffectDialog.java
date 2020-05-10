package frontend;

import backend.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class TileEffectDialog extends MonopolyDialog {
    public TileEffectDialog(GameMasterGui GM, Boolean passedGo, int money, Card card, Tile curTile){
        setHeaderAndContent(GM, curTile, passedGo, money, card);
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(ok);
        this.showAndWait();
    }

    private void setHeaderAndContent(GameMasterGui GM, Tile curTile, Boolean passedGo, int money, Card card) {
        if (passedGo){
            this.setHeaderText("You passed Go!");
            this.setContentText("Collect £200!");
        }
        else if (GM.getPlayer(GM.getCurTurn()).getPlace() == GM.getPlayer(GM.getCurTurn()).getJail()){
            this.setHeaderText("You are currently in Jail");
            if (GM.getPlayer(GM.getCurTurn()).getJailTime() == 1){
                this.setContentText("Your sentence currently is " + GM.getPlayer(GM.getCurTurn()).getJailTime() + " more turn.");
            }
            else{
                this.setContentText("Your sentence currently is " + GM.getPlayer(GM.getCurTurn()).getJailTime() + " more turns.");
            }
        }
        else if(curTile instanceof BuyableTile){
            this.setHeaderText("You are losing money");
            this.setContentText("You pay rent worth £" + money + " to " + ((BuyableTile) curTile).getPlayer().getToken().name());
        }
        else if (curTile instanceof Tax){
            this.setHeaderText("The authorities are on to you!");
            this.setContentText("You need to pay £" + money + " to silence the government.");
        }
        else if (curTile instanceof toJail){
            this.setHeaderText("The authorities have found you....");
            this.setContentText("...you have been done for many bad things and are sent to Jail! \uD83D\uDE25");
        }
        else if (curTile instanceof FreeParking){
            this.setHeaderText("You landed on Free Parking");
            this.setContentText("You gained £" + money + " from a bagel.");
        }
        else if (curTile instanceof CardDraw){
            this.setHeaderText("You have drawn a " + curTile.getName() + " card");
            this.setContentText("It says: " + card.getCardText());
        }
        else{
            this.setHeaderText("Joe this is bad.");
            this.setContentText("The overloads have hacked the code this is a forbidden Dialog!");
        }
    }
}
