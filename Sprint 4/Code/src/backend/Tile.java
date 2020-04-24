package backend;

public class Tile {
    private boolean buyable;
    private String name;

    /**
     *
     * @return buyable, if the tile can be brought by a player
     */
    public boolean getBuyable(){
        return buyable;
    }

    /**
     *
     * @param canBuy - setting buyable to this
     */
    public void setBuyable(boolean canBuy){
        buyable=canBuy;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
