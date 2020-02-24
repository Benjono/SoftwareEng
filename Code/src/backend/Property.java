package backend;

public class Property extends Tile {
    String name;
    Enum colour;
    int costToBuy;
    int houseCost;
    int[] rent;

    /**
     * constructs a property using the given parameters
     *
     * @param name name of tile
     * @param colour String converted to enum of color of group tile belongs to
     * @param CostToBuy integer - cost to buy the tile
     * @param rent array - rent to be paid upon landing
     * @param houseCost - cost to buy house/ hotel
     * @author Alex
     */
    public Property(String name, String colour, int CostToBuy, int[] rent,int houseCost){
        setBuyable(false);
        setName(name);
        this.colour = Colours.valueOf(colour);
        this.costToBuy = costToBuy;
        this.rent = rent;
        this.houseCost = houseCost;
    }
}
