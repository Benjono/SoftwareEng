package backend;

public class Station extends Tile{
    int cost;
    int[] rent;

    /**
     * takes given parameters and constructs a station
     * @param cost cost to buy tile
     * @param rent array of rent payed. indexes correspond to amount of stations the player owns
     * @author Alex
     */
    public Station(String name, int cost, int[] rent){
        setBuyable(true);
        setName(name);
        this.cost = cost;
        this.rent = rent;
    }
}
