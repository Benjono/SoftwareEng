//import org.json.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONTokener;
import java.io.File;
import java.io.FileNotFoundException;


public class Board {
    Tile[] tileGrid;

    /**
     * constructs the board. Uses the boardTiles.json file, it will generate the appropriate classed tiles
     * and put them into the correct position in the tile grid
     * @AUTHOR Alex
     */
    public Board(){
        try {
            File filePath = new File("../../config/boardTiles.json");
            //JSONObject dictionary = new JSONObject(filePath);
        } catch (Exception e) {
            System.out.println("file bricked");
        }

    }

    public Tile[] getTileGrid() {
        return tileGrid;
    }
}
