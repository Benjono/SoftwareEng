package backend;

/**
 * exception that is thrown when a player does not have enough money to make an optional transaction
 * this is NOT used for rent
 * @author Alex
 */
public class NotEnoughMoneyException extends Exception {
    int moneyShort;
    Player player;

    /**
     *
     * @param message exception message
     * @param moneyShort money missing for the transaction
     * @param player player involved
     */
    public NotEnoughMoneyException(String message,int moneyShort, Player player){
        super(message);
        this.player = player;
        this.moneyShort=moneyShort;
    }
}
