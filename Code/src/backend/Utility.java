package backend;

public class Utility extends Tile {
    private int[] rent;
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
    }
}
