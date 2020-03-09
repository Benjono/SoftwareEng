package backend;

/**
 * Instruction on cross class (currently only "Go")
 * @Author Alex
 */
public class InstructionOnCross extends Tile {
    int crossingReward;

    /**
     * setup the Instruction on cross tile. This is currently just the go tile
     * @param name name of tile
     */
    public InstructionOnCross(String name){
        setName(name);
        setBuyable(false);
        crossingReward =200;
    }

    /**
     * pays given player the crossing reward, should be called after an incremental move
     * @param crosser player to be paid
     */
    public void collect(Player crosser){
        crosser.setMoney(crosser.getMoney()+crossingReward);
    }
}
