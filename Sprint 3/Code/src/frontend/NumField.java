package frontend;

import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

/**
 * Source: https://stackoverflow.com/questions/8381374/how-to-implement-a-numberfield-in-javafx-2-0
 * The Class being the accepted answer.
 * Edited by Joe C for efficiency
 * @author Joe C
 * @author Anuruddha
 */

public class NumField extends PasswordField {
    public NumField() {
        this.addEventFilter(KeyEvent.KEY_TYPED, inputCharacter -> {
            if (!Character.isDigit(inputCharacter.getCharacter().toCharArray()[inputCharacter.getCharacter().toCharArray().length - 1])) {
                inputCharacter.consume();
            }
        });
    }
}
