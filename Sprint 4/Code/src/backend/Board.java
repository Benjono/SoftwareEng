package backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Board class, holds an array of tiles on the board
 * @author Alex
 */
public class Board {
    private Tile[] tileGrid;
    private ArrayList<Card> potLuck;
    private ArrayList<Card> opportunityKnocks;
    private GameMaster gameMaster;

    /**
     * constructs the board. Uses the boardTiles.json file, it will generate the appropriate classed tiles
     * and put them into the correct position in the tile grid
     * @author Alex Homer
     */
    public Board(GameMaster gameMaster){
        this.gameMaster = gameMaster; //gamemaster for cards use
        generateTiles(); //create tiles
        potLuck = new ArrayList<Card>(); //initialisation
        opportunityKnocks = new ArrayList<Card>();
        deckConstructor();

    }

    /**
     * Getter for the card deck of pot Luck
     * @return the ArrayList potLuck
     */
    public ArrayList getPotLuck(){
        return potLuck;
    }

    /**
     *Getter for the card deck of pot Luck
     *@return the ArrayList opportunityKnocks
     */
    public ArrayList getOpportunityKnocks(){
        return opportunityKnocks;
    }

    /**
     * Called in constructor
     */
    private void deckConstructor(){
        generateCard("opportunityKnocks");
        generateCard("potLuck");
        Collections.shuffle(potLuck);
        Collections.shuffle(opportunityKnocks);
    };

    /**
     * generateCard takes a parameter of type String called 'cardType' and instantiates the card array for the cards
     * It will fail if given a name that isn't opportunityKnocks or potLuck
     * @author Alex Homer
     * @param cardType string input of the card deck to fetch the json file for and the array to put the instantiated cards in
     */
    private void generateCard(String cardType){
        String filePath = new String("config/"+cardType+".json");
        try {
            JSONParser parser = new JSONParser(); //create json parser
            FileReader path =new FileReader(filePath); //get file as FileReader
            JSONObject dictionary = (JSONObject) parser.parse(path); //turn file into a JSONObject
            Iterator<String> keys = dictionary.keySet().iterator(); //get the number ids of the cards as an iterator
            JSONObject embeddedCard;
            while (keys.hasNext()){ //iterate through the keys
                String key = keys.next(); //get next key
                embeddedCard = (JSONObject) dictionary.get(key); //get data for key
                ArrayList<Object> param = new ArrayList();

                JSONObject cardConversion = (JSONObject) embeddedCard.get("inputs"); //get inputs for the card
                for (int i =1; i<3; i++){ //for each input
                    String paramKey= Integer.toString(i); //turn it into a string
                    if(cardConversion.get(paramKey)!=null) { //if it's not a null

                        try {
                            param.add(Integer.valueOf(embeddedCard.get(paramKey).toString())); //try turning it into a integer
                        } catch (Exception e) {
                            if (cardConversion.get(paramKey) == "true") { //try turning it into a boolean
                                boolean input = true;
                                param.add(input);
                            } else if (cardConversion.get(paramKey) == "false") {
                                boolean input = false;
                                param.add(input);
                            } else {
                                String input = cardConversion.get(paramKey).toString(); //else keep it as a string
                                param.add(input);
                            }
                        }

                    }
                }
                Object[] paramArray= new Object[param.size()]; //turn it into an object array
                for(int i =0; i<param.size() ;i++){
                    paramArray[i] = param.get(i);
                }
                Card currentCard = new Card(embeddedCard.get("text").toString(),cardType,embeddedCard.get("method").toString(),gameMaster,paramArray); //create card
                if(cardType == "potLuck") { //add card to correct arraylist
                    potLuck.add(currentCard);
                }
                else {
                    opportunityKnocks.add(currentCard);
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
            System.out.println("parse bad"+cardType);
            for(int i =0; i<e.getStackTrace().length; i++) {
                System.out.println(e.getStackTrace()[i].toString());
            }
            System.out.println("dfiuIUFD");
        }
    }

    /**
     * Getter for tileGrid Tile array
     * @return Tile[], the array of tiles
     */
    public Tile[] getTileGrid() {
        return tileGrid;
    }

    /**
     * A function that returns the Tile at position tilePos
     * @param tilePos integer for the position of the tile on the board in the one dimensional array
     * @return the tile at the position of tilePos
     */
    public Tile getTile(int tilePos){
        try{
            return tileGrid[tilePos];
        }
        catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    /**
     * Generates the tiles for the board from the json file tileInfo
     */
    private void generateTiles(){
        String filePath = new String("config/tileInfo.json"); //file name
        tileGrid = new Tile[40]; //tile array
        try {
            JSONParser parser = new JSONParser(); //create parser
            FileReader path =new FileReader(filePath); //get file based on file path
            JSONObject dictionary = (JSONObject) parser.parse(path); //create JSONObject of the dictionary from the file
            Iterator<String> keys = dictionary.keySet().iterator(); //get keys of the dictionary
            JSONObject embeddedTile;
            while (keys.hasNext()){ //using the keys
                String key = keys.next();
                embeddedTile = (JSONObject) dictionary.get(key); //get the tile related to that key

                switch(embeddedTile.get("typeOfTile").toString()) { //get the type of tile it is
                    case "property": //if property
                        int[] rentArray= new int[6];
                        JSONObject rentConversion = (JSONObject) embeddedTile.get("rent"); //get data for property
                        for (int i =0; i<6; i++){
                            String rentKey= Integer.toString(i);
                            Long convert = (Long) rentConversion.get(rentKey);
                            rentArray[i] = convert.intValue();
                        }
                        String colour = embeddedTile.get("colour").toString();
                        Property currentProperty = new Property(key, Colours.valueOf(colour) ,  Integer.valueOf(embeddedTile.get("cost").toString()), rentArray,  Integer.valueOf(embeddedTile.get("houseCost").toString())); //instantiate property
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentProperty; //add it to the tile array
                        break;

                    case "freeParking": //if free parking
                        FreeParking currentFreeParking = new FreeParking(key); //create freeparking
                        tileGrid[ Integer.valueOf(embeddedTile.get("position").toString())] = currentFreeParking; //add it to the tile array
                        break;

                    case "instructionOnCross": //if GO
                        InstructionOnCross currentInstruction = new InstructionOnCross(key); //create Go
                        tileGrid[(Integer.valueOf(embeddedTile.get("position").toString()))] = currentInstruction; //add it to the tile array
                        break;

                    case "station": //if a station
                        int[] stationRentArray= new int[5];
                        JSONObject rentConversionStation = (JSONObject) embeddedTile.get("rent");
                        for (int i =0; i<5; i++){
                            String rentKey= Integer.toString(i);
                            Long convert = (Long) rentConversionStation.get(rentKey);
                            stationRentArray[i] = convert.intValue();
                        }
                        Station currentStation = new Station(key, Integer.valueOf(embeddedTile.get("cost").toString()), stationRentArray); //create the station
                        tileGrid[(Integer.valueOf(embeddedTile.get("position").toString()))] = currentStation; //add it to the tile array
                        break;

                    case "utility": //if utility
                        int[] utilRentArray = new int[3];
                        JSONObject rentConversionUtil = (JSONObject) embeddedTile.get("rent");
                        for (int i =0; i<3; i++){
                            String rentKey= Integer.toString(i);
                            Long convert = (Long) rentConversionUtil.get(rentKey);
                            utilRentArray[i] = convert.intValue();
                        }
                        Utility currentUtil = new Utility(key, Integer.valueOf(embeddedTile.get("cost").toString()), utilRentArray); //create utilty
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentUtil; //add it to the tile array
                        break;

                    case "tax": //if tax
                        Tax currentTax = new Tax(key, Integer.valueOf(embeddedTile.get("behaviour").toString()));
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentTax;
                        break;

                    case "toJail": //if to Jail
                        toJail currentToJail = new toJail(key);
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentToJail;
                        break;

                    case "doesNothing": //if just visiting
                        DoesNothing currentDoesNothing = new DoesNothing(key);
                        tileGrid[Integer.valueOf(embeddedTile.get("position").toString())] = currentDoesNothing;
                        break;

                    case "cardDraw": //if pot Luck or Opportunity Knocks
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
    }


}
