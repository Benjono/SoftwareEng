package backend;

public class PlayerDoesNotOwnException extends Exception{

    PlayerDoesNotOwnException(String message){
        super(message);
    }
}
