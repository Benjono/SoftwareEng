package backend;

public class Tile {
    private boolean buyable;
    private String name;

    public boolean getBuyable(){
        return buyable;
    }
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
