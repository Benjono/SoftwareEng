package backend;

public class CardDraw extends Tile{
    DrawTypes drawType;
    public CardDraw(String name, String drawTypes){
        setBuyable(false);
        setName(name);
        this.drawType = DrawTypes.valueOf(drawTypes);
    }
    public DrawTypes getDrawType(){
        return drawType;
    }
}
