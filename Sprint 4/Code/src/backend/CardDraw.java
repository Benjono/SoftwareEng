package backend;

public class CardDraw extends Tile{
    DrawTypes drawType;

    /**
     * Constructor
     * @param name
     * @param drawTypes
     */
    public CardDraw(String name, String drawTypes){
        setBuyable(false);
        setName(name);
        this.drawType = DrawTypes.valueOf(drawTypes);
    }
    /**
     * returns the type of card to draw
     * @return
     */
    public DrawTypes getDrawType(){
        return drawType;
    }
}
