package frontend;

import backend.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * Shows the player when the draw a card what card they drawn by outputting the text
 * @author Joe C
 */
public class CardDrawDialog extends MonopolyDialog {
    public CardDrawDialog(Tile tile, Card card){
        this.setHeaderText("You have drawn a " + tile.getName() + " card");
        this.getDialogPane().setContent(new Label("It says: " + card.getCardText()));
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
        this.showAndWait();
    }
}
