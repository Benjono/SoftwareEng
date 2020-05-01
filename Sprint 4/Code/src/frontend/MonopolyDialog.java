package frontend;

import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Implements each of the other dialogs and extends from Dialog.
 * This is inorder to easily setup each dialog created with Titles and Icons
 */
public class MonopolyDialog extends Dialog<Object> {

    public MonopolyDialog() {
        Stage localStage = (Stage) this.getDialogPane().getScene().getWindow();
        localStage.getIcons().add(new Image("logo.png"));
        this.setTitle("Property Tycoon");
        this.setResizable(false);
    }

    /**
     * Get result of Dialog
     * @return Result of Dialog
     */
    public Object getR(){
        return this.getResult();
    }

}
