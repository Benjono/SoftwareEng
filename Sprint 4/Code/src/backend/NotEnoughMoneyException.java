package backend;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException(String message){
        super(message);
    }
}
