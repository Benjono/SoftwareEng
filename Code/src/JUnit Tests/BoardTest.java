import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 */
public class BoardTest {
    @Test
    public void mmmTest(){
        Board board = new Board();
        Assertions.assertEquals(1,1);
    }
    @Test
    public void test1(){
        String filePath = new String("config/test1.json");
        try {
            JSONParser parser = new JSONParser();
            FileReader path =new FileReader(filePath);
            JSONObject dictionary = (JSONObject) parser.parse(path);
            System.out.println(dictionary.get("Go").toString());
        }
        catch(Exception e){
            System.out.println("RIP");
        }
    }

    @Test
    public void test2(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("config/test1.json"));
            JSONObject jsonObject = (JSONObject) obj;
            //System.out.println(dictionary.get(0).toString());
        }
        catch(Exception e){
            System.out.println("RIP");
        }
    }
}
