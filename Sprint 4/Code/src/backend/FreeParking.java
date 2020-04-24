package backend;

public class FreeParking extends Tile{
    private int currentPenalties;

    /**
     * Constructor
     * @param name
     */
    public FreeParking(String name) {
        setBuyable(false);
        setName(name);
        setCurrentPenalties(0);
    }

    /**
     * Setter for the amount of money on free parking
     * @param currentPenalties
     */
    public void setCurrentPenalties(int currentPenalties){
        this.currentPenalties = currentPenalties;
    }

    /**
     * Getter for the amount of money on free parking
     * @return
     */
    public int getCurrentPenalties(){
        return currentPenalties;
    }
}
