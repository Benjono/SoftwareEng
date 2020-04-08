package backend;

public class Property extends BuyableTile {
    Enum colour;
    int houseCost;
    int currentHouseLevel;

    /**
     * constructs a property using the given parameters
     *
     * @param name name of tile
     * @param colour String converted to enum of color of group tile belongs to
     * @param costToBuy integer - cost to buy the tile
     * @param rent array - rent to be paid upon landing
     * @param houseCost - cost to buy house/ hotel
     * @author Alex
     */
    public Property(String name, String colour, int costToBuy, int[] rent,int houseCost){
        setBuyable(true);
        setName(name);
        mortgaged = false;
        currentHouseLevel = 0;
        this.colour = Colours.valueOf(colour);
        this.costToBuy = costToBuy;
        this.rent = rent;
        this.houseCost = houseCost;
    }

    /**
     * buys levels of housing dependant on input
     * @param houseLevel
     */
    public void buyHouse(int houseLevel){
        currentHouseLevel += houseLevel;
        owner.setMoney(owner.getMoney()-(houseLevel*houseCost));
    }

    /**
     * sells levels of housing dependent on input
     * @param houseLevel - how many houses to be sold
     */
    public void sellHouse(int houseLevel){
        currentHouseLevel -= houseLevel;
        owner.setMoney(owner.getMoney()+(houseLevel*houseCost));
    }

    /**
     * gets house level
     * @return int (0-5) current house level
     */
    public int getCurrentHouseLevel(){return currentHouseLevel;}

    /**
     * mortgages a property, giving the player half of its buy cost if there are no houses present
     *
     * @return money the player made from mortgaging the property
     * @throws InvalidHouseSetupException if houses are present on property
     */
    @Override
    public void mortgageTile() throws InvalidHouseSetupException{

        if(currentHouseLevel ==0) {
            mortgaged = true;
            owner.setMoney(owner.getMoney()+(costToBuy/2));
        }
        else{
            throw new InvalidHouseSetupException("cannot mortgage a property with houses/hotel built on them");
        }
    }
    @Override
    protected void sellBaseTile() throws InvalidHouseSetupException{
        if(currentHouseLevel != 0) {
            this.owner = null;
            if (!mortgaged) {
                owner.setMoney(owner.getMoney() + costToBuy);
            }
            else {
                owner.setMoney(owner.getMoney() + (costToBuy / 2));
            }
        }
        else{
            throw new InvalidHouseSetupException("must sell houses first");
        }
    }
    /**
     * player who lands on tile pays the owner of tile the appropriate rent
     * @param debtor - player who lands on property
     */
    @Override
    public void rent(Player debtor){
        debtor.setMoney(debtor.getMoney() - rent[currentHouseLevel]);
        owner.setMoney(owner.getMoney() - rent[currentHouseLevel]);
    }


    /**
     * sells all the houses on the property, and then the property
     */
    public void sellTile() throws InvalidHouseSetupException{
        sellHouse(currentHouseLevel);
            sellBaseTile();
    }


}
