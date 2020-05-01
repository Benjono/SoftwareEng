package frontend;

import backend.GameMaster;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * When the player rolls the dice this Dialog appears displaying to the player what they rolled.
 * @author Joe C
 */
public class ShowRollDialog extends MonopolyDialog {
    public ShowRollDialog(int[] roll, GameMaster GM){
        this.setHeaderText("Dice Roll");
        this.setContentText("Player " + (GM.getCurTurn()+1) + " just rolled a " + roll[0] + " and a " + roll[1] + "!");
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().add(ok);
        this.showAndWait();
    }
}
