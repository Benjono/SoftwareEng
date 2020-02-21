public class FreeParking extends Tile{
    private int currentPenalties;
    public FreeParking(String name){
        setBuyable(false);
        setName(name);
        setCurrentPenalties(0);
    }
    public void setCurrentPenalties(int currentPenalties){
        this.currentPenalties = currentPenalties;
    }
    public int getCurrentPenalties(){
        return currentPenalties;
    }
}
