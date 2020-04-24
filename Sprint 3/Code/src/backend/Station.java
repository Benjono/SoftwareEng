package backend;

public class Station extends BuyableTile{


    /**
     * takes given parameters and constructs a station
     * @param costToBuy cost to buy tile
     * @param rent array of rent payed. indexes correspond to amount of stations the player owns
     * @author Alex
     */
    public Station(String name, int costToBuy, int[] rent){
        setBuyable(true);
        setName(name);
        mortgaged = false;
        this.costToBuy = costToBuy;
        this.rent = rent;
    }


    public void buyStation(Player newOwner){
        buyTile(newOwner);
        owner.setCountTrain(owner.getCountTrain()+1);
    }

    public void sellStation(){
        owner.setCountTrain(owner.getCountTrain()-1);
        try {
            sellBaseTile();
        }
        catch(InvalidHouseSetupException e){
            //no catch needed
        }
    }

    /**
     * player who lands on tile pays the owner of tile the appropriate rent
     * @param debtor - player who lands on property
     */
    @Override
    public void rent(Player debtor){
        debtor.setMoney(debtor.getMoney() - rent[owner.getCountTrain()]);
        owner.setMoney(owner.getMoney() - rent[owner.getCountTrain()]);
    }

}
