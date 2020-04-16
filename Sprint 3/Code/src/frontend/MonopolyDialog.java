package frontend;

import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MonopolyDialog extends Dialog<Object> {

    public MonopolyDialog() {
        Stage localStage = (Stage) this.getDialogPane().getScene().getWindow();
        localStage.getIcons().add(new Image("logo.png"));
        this.setTitle("Property Tycoon");
        this.setResizable(false);
    }

    public Object getR(){
        return this.getResult();
    }

}
