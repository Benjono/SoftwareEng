package backend;

public class DoesNothing extends Tile{

    /**
     * Constructor
     * @param name
     */
    public DoesNothing(String name){
        setBuyable(false);
        setName(name);
    }
}
