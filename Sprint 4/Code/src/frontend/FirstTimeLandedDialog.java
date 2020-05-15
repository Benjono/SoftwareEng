package frontend;

import backend.BuyableTile;
import backend.GameMaster;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;


/**
 * Implements landing on an unowned tile, with options to buy or auction
 * Dependant on which button is pressed returns a different boolean value to the called class.
 * @author Joe C
 */
public class FirstTimeLandedDialog extends MonopolyDialog {
    public FirstTimeLandedDialog(GameMaster GM){
        this.setHeaderText("You Landed on " + GM.getTile(GM.getPlayer(GM.getCurTurn()).getPlace()).getName());
        this.setContentText("Buy for " + ((BuyableTile)(GM.getBoard().getTileGrid())[GM.getPlayer(GM.getCurTurn()).getPlace()]).getCostToBuy());
        ButtonType buyButton = new ButtonType("BUY", ButtonBar.ButtonData.YES);
        ButtonType auctionButton = new ButtonType("AUCTION", ButtonBar.ButtonData.NO);
        this.getDialogPane().getButtonTypes().addAll(buyButton,auctionButton);
        this.setResultConverter((ButtonType b) -> {
            if (b == buyButton){
                return true;
            }
            return false;
        });
        //wait for return
        this.showAndWait();
    }
}
