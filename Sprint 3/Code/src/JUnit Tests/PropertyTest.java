
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
        gm.setup(3,tk);
        int[] rent = {25, 50, 75, 100};
        Property prop = new Property(gm.getTile(1).getName(), "red", 200, rent,75);
        Assertions.assertFalse(prop.getBuyable());
        Assertions.assertEquals(gm.getTile(1).getName(), prop.getName());
    }

    @Test
    public void houseLevelTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk);
        int[] rent = {25, 50, 75, 100};
        Property prop = new Property(gm.getTile(1).getName(), "red", 200, rent,75);
        prop.buyHouse(1);

        Assertions.assertEquals(1, prop.getCurrentHouseLevel());
    }

    @Test
    public void mortgageTest(){
        GameMaster gm = new GameMaster();
        Tokens[] tk = new Tokens[]{Tokens.Boot, Tokens.Cat, Tokens.Goblet};
        gm.setup(3,tk);
        int[] rent = {25, 50, 75, 100};
        Property prop = new Property(gm.getTile(1).getName(), "red", 200, rent,75);
        prop.getCurrentHouseLevel();
    }
}
