package backend;

public class Utility extends BuyableTile {
    /**
     * takes input parameters and created a utility tile with those values
     * @param name tile name
     * @param cost cost to buy tile
     * @param rent array of rent costs. Utilities are array * dice roll
     * @Author Alex
     */
    public Utility(String name, int cost, int[] rent){
        setBuyable(true);
        setName(name);
        this.rent = rent;
        this.costToBuy = cost;
    }

    public void buyUtility(Player newOwner){
        buyTile(newOwner);
        owner.setCountUtil(owner.getCountUtil()+1);
    }
    public void sellUtility(){
        owner.setCountUtil(owner.getCountUtil()-1);
        try {
            sellBaseTile();
        }
        catch(InvalidHouseSetupException e){
            // nothing needed here
        }
    }

    public int rent(Player debtor, int diceRoll){
        int rentOwed;
        if(owner.getCountUtil() == 1){
            rentOwed= 4*diceRoll;
        }
        else{
            rentOwed= 10*diceRoll;
        }
        debtor.setMoney(debtor.getMoney() - rentOwed);
        owner.setMoney(owner.getMoney() - rentOwed);
        return rentOwed;
    }
}
