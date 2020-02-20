public class CardDraw extends Tile{
    Enum drawTypes;
    public CardDraw(String name, String drawTypes){
        setBuyable(false);
        setName(name);
        this.drawTypes = DrawTypes.valueOf(drawTypes);
    }
}
