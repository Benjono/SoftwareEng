
import backend.Colours;
import backend.GameMaster;
import backend.Player;
import backend.Tokens;
import backend.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PropertyTest {
    @Test
    public void creationTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk, -1);
        int[] rent = {25, 50, 75, 100};
        Property prop = new Property(gm.getTile(1).getName(), Colours.purple, 200, rent,75);
        Assertions.assertTrue(prop.getBuyable());
        Assertions.assertEquals(gm.getTile(1).getName(), prop.getName());
    }

    @Test
    public void houseLevelTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk, -1);
        int[] rent = {25, 50, 75, 100};
        Property prop = new Property(gm.getTile(6).getName(), Colours.red, 200, rent,75);

        gm.getPlayer(0).setPlace(6);
        gm.getPlayer(0).setMoney(50000);
        try{
            gm.applyTileEffect(gm.getPlayer(0));
        } catch(Exception m){
            System.out.print(m);
        }

        try {
            prop.buyHouse(1);
            Assertions.assertEquals(1, prop.getCurrentHouseLevel());
        }
        catch(Exception e){
            System.out.print(e);
        }
        Assertions.assertEquals(1, prop.getCurrentHouseLevel());
    }

    @Test
    public void mortgageTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk, -1);
        int[] rent = {25, 50, 75, 100};
        Property prop = new Property(gm.getTile(3).getName(), Colours.blue, 200, rent,75);
        gm.getPlayer(0).setPlace(3);
        gm.getPlayer(0).setMoney(50000);
        try{
            gm.applyTileEffect(gm.getPlayer(0));
        } catch(Exception m){
            System.out.print(m);
        }

        try{
            prop.mortgageTile();
        } catch(Exception e){
            System.out.print(e);
        }

    }
}
