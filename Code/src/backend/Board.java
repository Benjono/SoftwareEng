package backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Board class, holds an array of tiles on the board
 * @author Alex
 */
public class Board {
    private Tile[] tileGrid;

    /**
     * constructs the board. Uses the boardTiles.json file, it will generate the appropriate classed tiles
     * and put them into the correct position in the tile grid
     * @author Alex Homer
     */
    public Board(){
        String filePath = new String("config/test1.json");
        tileGrid = new Tile[40];
        try {
            JSONParser parser = new JSONParser();
            FileReader path =new FileReader(filePath);
            JSONObject dictionary = (JSONObject) parser.parse(path);
            Iterator<String> keys = dictionary.keySet().iterator();
            JSONObject embeddedTile;
            while (keys.hasNext()){
                String key = keys.next();
                embeddedTile = (JSONObject) dictionary.get(key);

                switch(embeddedTile.get("typeOfTile").toString()) {
                    case "property":
                        int[] rentArray= new int[6];
                        JSONObject rentConversion = (JSONObject) embeddedTile.get("rent");
                        for (int i =0; i<6; i++){
                            String rentKey= Integer.toString(i);
                            Long convert = (Long) rentConversion.get(rentKey);
                            rentArray[i] = convert.intValue();
                        }

                        Property currentProperty = new Property(key, embeddedTile.get("colour").toString(),  Integer.valueOf(embeddedTile.get("cost").toString()), rentArray,  Integer.valueOf(embeddedTile.get("houseCost").toString()));
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentProperty;
                        break;

                    case "freeParking":
                        FreeParking currentFreeParking = new FreeParking(key);
                        tileGrid[ Integer.valueOf(embeddedTile.get("position").toString())] = currentFreeParking;
                        break;

                    case "instructionOnCross":
                        InstructionOnCross currentInstruction = new InstructionOnCross(key);
                        tileGrid[(Integer.valueOf(embeddedTile.get("position").toString()))] = currentInstruction;
                        break;

                    case "station":
                        int[] stationRentArray= new int[5];
                        JSONObject rentConversionStation = (JSONObject) embeddedTile.get("rent");
                        for (int i =0; i<5; i++){
                            String rentKey= Integer.toString(i);
                            Long convert = (Long) rentConversionStation.get(rentKey);
                            stationRentArray[i] = convert.intValue();
                        }
                        Station currentStation = new Station(key, Integer.valueOf(embeddedTile.get("cost").toString()), stationRentArray);
                        tileGrid[(Integer.valueOf(embeddedTile.get("position").toString()))] = currentStation;
                        break;

                    case "utility":
                        int[] utilRentArray = new int[3];
                        JSONObject rentConversionUtil = (JSONObject) embeddedTile.get("rent");
                        for (int i =0; i<3; i++){
                            String rentKey= Integer.toString(i);
                            Long convert = (Long) rentConversionUtil.get(rentKey);
                            utilRentArray[i] = convert.intValue();
                        }
                        Utility currentUtil = new Utility(key, Integer.valueOf(embeddedTile.get("cost").toString()), utilRentArray);
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentUtil;
                        break;

                    case "tax":
                        Tax currentTax = new Tax(key, Integer.valueOf(embeddedTile.get("behaviour").toString()));
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentTax;
                        break;

                    case "backend.toJail":
                        toJail currentToJail = new toJail(key);
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentToJail;
                        break;

                    case "doesNothing":
                        DoesNothing currentDoesNothing = new DoesNothing(key);
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentDoesNothing;
                        break;

                    case "cardDraw":
                        String draw = embeddedTile.get("behaviour").toString();
                        JSONArray drawPositionConversion = (JSONArray) embeddedTile.get("position");
                        for (int i =0; i<drawPositionConversion.size(); i++){
                            int currentPos= Integer.valueOf(drawPositionConversion.get(i).toString());
                            CardDraw currentCardDraw = new CardDraw(key, draw);
                            tileGrid[currentPos] = currentCardDraw;


                        }
                        break;

                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File cannot be found");
        }
        catch(IOException e){
            System.out.println("don't know why you seeing this tbh");
        }
        catch(ParseException e){
            System.out.println("Parse failed");
        }
        for(int i=0 ;i<40;i++){
            if(tileGrid[i]!= null){
                System.out.println(tileGrid[i].getName());
            }
            else{
                System.out.println("fail");
            }
        }



    }

    public Tile[] getTileGrid() {
        return tileGrid;
    }

    public Tile getTile(int tilePos){
        try{
            return tileGrid[tilePos];
        }
        catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

}
