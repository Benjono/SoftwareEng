package backend;

/**
 * tax tile class
 * @author alex
 */
public class Tax extends Tile{
    private int payment;
    public Tax(String name, int payment){
        setBuyable(false);
        setName(name);
        setPayment(payment);
    }

    /**
     * sets the tax rate of the tile
     * @param input - how much is taxed upon landing on this tile
     */
    private void setPayment(int input){
        payment = input;
    }

    /**
     * given player pays the tax amount to bank
     * @param debtor
     */
    public void payTax(Player debtor){
        debtor.setMoney(debtor.getMoney() - payment);
    }
}
