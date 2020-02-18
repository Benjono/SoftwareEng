public class Tile {
    private boolean buyable;
    private String name;
    private String behavior[];
    private int position;

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
    public String[] getBehavoir(){
        return behavior;
    }
    public void setBehavoir(String[] behavoir){
        behavior = behavoir;
    }
    public int getPosition(){
        return position;
    }
    public void setPosition(int newPos){
        position=newPos;
    }
}
