package frontend;

import backend.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class CardDrawDialog extends MonopolyDialog {
    public CardDrawDialog(Tile tile, Card card){
        this.setHeaderText("You have drawn a " + tile.getName() + " card");
        this.setContentText("It says: " + card.getCardText());
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        this.showAndWait();
    }
}
