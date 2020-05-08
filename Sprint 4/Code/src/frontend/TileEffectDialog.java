package frontend;

import backend.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class TileEffectDialog extends MonopolyDialog {
    public TileEffectDialog(Tile curTile){
        setHeaderAndContent(curTile);
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(ok);
        this.showAndWait();
    }

    private void setHeaderAndContent(Tile curTile) {
        if(curTile instanceof BuyableTile){
            this.setHeaderText("You are losing money");
            this.setContentText("You have paid " + 0 + "in rent to " + ((BuyableTile) curTile).getPlayer());
        }
        else if (curTile instanceof Tax){
            this.setHeaderText("Joe this is bad.");
            this.setContentText("The overloads have hacked the code this is a forbidden Dialog!");
        }
        else if (curTile instanceof toJail){
            this.setHeaderText("Joe this is bad.");
            this.setContentText("The overloads have hacked the code this is a forbidden Dialog!");
        }
        else if (curTile instanceof FreeParking){
            this.setHeaderText("Joe this is bad.");
            this.setContentText("The overloads have hacked the code this is a forbidden Dialog!");
        }
        else{
            this.setHeaderText("Joe this is bad.");
            this.setContentText("The overloads have hacked the code this is a forbidden Dialog!");
        }
    }
}
