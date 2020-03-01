package backend;

public class Tax extends Tile{
    private int payment;
    public Tax(String name, int payment){
        setBuyable(false);
        setName(name);
        setPayment(payment);
    }

    private void setPayment(int input){
        payment = input;
    }
    public int getPayment(){
        return payment;
    }
}
