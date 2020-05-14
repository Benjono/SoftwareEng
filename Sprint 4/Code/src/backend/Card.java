package backend;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Card {
    private String cardText;
    private String methodName;
    private Object[] params;
    private GameMaster gameMaster;
    private String type;

    /**
     * Constructor for Card
     * @param Description description of the card, for frontend
     * @param potOrKnock where the card belongs
     * @param Method what method it uses in card
     * @param gm the gameMaster that created it
     * @param params the parameters for the card to use
     */
    public Card(String Description, String potOrKnock, String Method, GameMaster gm, Object[] params){
        cardText=Description;
        methodName=Method;
        this.params=params;
        System.out.println(params.length);
        this.type=potOrKnock;
        gameMaster=gm;
    }

    /**
     * This is the [i]only[/i] function apart from the constructor that should be called. It determines which card effect to use and executes it using reflection
     *
     * @param player the player to apply the cards effect to
     * @return returns a string, return doesn't change
     */
    public void cardEffect(Player player){
        System.out.println(methodName);
        switch (methodName){
            case "gainMoney":{
                gainMoney(player,Integer.parseInt((String)params[0]));
                break;
            } case "payMoney":{
                payMoney(player,Integer.parseInt((String)params[0]));
                break;
            } case "freeParkingFine":{
                fine(player,Integer.parseInt((String)params[0]));
                break;
            } case "transferMoney":{
                birthday(player,Integer.parseInt((String)params[0]));
                break;
            } case "moveTo":{
                try{
                    moveTo(player,Integer.parseInt((String)params[0]),(boolean)params[1]);
                } catch (Exception e){
                    System.out.println();
                    moveTo(player,(String)params[0],(boolean)params[1]);
                }
                break;
            } case "repairBill":{
                repairBill(player,Integer.parseInt((String)params[0]),Integer.parseInt((String)params[1]));
                break;
            } case "getOutOfJail":{
                getOutOfJail(player);
                break;
            } case "toJail":{
                toJail(player);
                break;
            } default:
                System.out.println("ERROR");
                System.out.println(this.type);
                System.out.println(this.methodName);
                System.out.println(this.cardText);
        }
    }
    public void cardEffect(Player player, boolean choice){
        if(choice){
            player.setMoney(player.getMoney()-10);
        } else {
            Board board = gameMaster.getBoard();
            Card c = (Card)board.getOpportunityKnocks().get(0);
            c.cardEffect(gameMaster.getPlayer(gameMaster.getCurTurn()));
            board.getOpportunityKnocks().remove(c);
            if(!c.getMethodName().equals("getOutOfJail")) {
                board.getOpportunityKnocks().add(c);
            }
        }
    }

    public String getMethodName(){
        return methodName;
    }

    /**
     * This function is for the bank paying the player money
     * @param player
     * @param money
     * @Author Jonathan Morris
     */
    public void gainMoney(Player player, int money){
        player.setMoney(player.getMoney()+money);
    }

    /**
     * This function is for the player paying the bank money
     * @param player
     * @param money
     * @Author Jonathan Morris
     */
    public void payMoney(Player player, int money){
        player.setMoney(player.getMoney()-money);
    }

    /**
     * This function is for the payer paying a fine to free parking
     * @param player
     * @param money
     * @Author Jonathan Morris
     */
    public void fine(Player player, int money){
        player.setMoney(player.getMoney()-money);
        for(Tile t: gameMaster.getBoard().getTileGrid()){
            if(t.getName().equals("Free Parking")){
                ((FreeParking)t).setCurrentPenalties(((FreeParking) t).getCurrentPenalties()+money);
            }
        }
    }

    /**
     * THis function is for the player to gain money amount of currency from all other players
     * @param player the player to apply the effect to
     * @param money the amount of money to subtract from toher players
     */
    public void birthday(Player player, int money){
        for (Player p : gameMaster.getPlayers()){ //for each player
            player.setMoney(player.getMoney()+money); //add money to the player equal to money
            p.setMoney(p.getMoney()-money); //subtract that from the other player
        }
    }

    /**
     *
     * @param player the player to apply the effect to
     * @param destination the destination to send the player to
     * @param forward whether or not to trigger go by this movement
     */
    public void moveTo(Player player, String destination, boolean forward){
        int destDiff = 0;
        for (int i=0;i<40;i++){
            if(gameMaster.getBoard().getTileGrid()[(i+player.getPlace())%40].getName()==destination){
                destDiff=i;
            }
        }
        if(forward){
            if((destDiff+player.getPlace())%40!=destDiff+player.getPlace()){
                player.setMoney(player.getMoney()+200);
            }
            player.setPlace((destDiff+player.getPlace())%40);
        }else{
            player.setPlace((destDiff+player.getPlace())%40);
        }
    }

    /**
     * THis function takes a player and moves them a number of spaces equal to destination, fowards if forward is true, backwards otherwise
     * @param player the player to apply the effect to
     * @param destination a number of spaces to move the player
     * @param forward whether or not they trigger go
     */
    public void moveTo(Player player, int destination, boolean forward){
        if(forward){
            if((destination+player.getPlace())%40!=destination+player.getPlace()){
                player.setMoney(player.getMoney()+200);
            }
            player.setPlace((destination+player.getPlace())%40);
        }else{
            if(player.getPlace()-destination<0){
                player.setPlace(40-(player.getPlace()-destination));
            }else {
                player.setPlace((player.getPlace() - destination));
            }
        }
    }

    /**
     * Makes the player pay a 'repair bill', not implemented
     * @param player the player to apply the effect to
     * @param houseCost cost of each house that the player has
     * @param apartmentCost cost of each apartment the player has
     */
    public void repairBill(Player player, int houseCost, int apartmentCost){
        Tile[] tiles = gameMaster.getPlayerProperties(player);
        int total = 0;
        for (Tile t: tiles){
            if(t instanceof Property){
                if(((Property) t).getCurrentHouseLevel()<5){
                    total+=houseCost*((Property) t).getCurrentHouseLevel();
                }
                else{
                    total+=apartmentCost;
                }
            }
        }
        player.setMoney(player.getMoney()-total);
    }

    /**
     * Gives the player a get out of jail free card, not implemented
     * @param player the player to apply the effect to
     */
    public void getOutOfJail(Player player){
        if(gameMaster.getBoard().getTile(player.getPlace()) instanceof CardDraw){
            if(((CardDraw) gameMaster.getBoard().getTile(player.getPlace())).getDrawType().equals(DrawTypes.opportunityKnocks)){
                player.getOutOfJailFreeOpportunity().add(this);
            } else{
                player.getOutOfJailFreePotLuck().add(this);
            }
        } else{
            System.out.println("You've drawn a card when not on CardDraw, congrats!");
        }
    }

    /**
     * Sends the player to jail
     * @param player the player to apply the effect to
     */
    public void toJail(Player player){
        player.jail();
    }

    /**
     * This function returns whether the card belongs to 'potLuck' or 'opportunityKnocks'
     * @return whether the card belongs to 'potLuck' or 'opportunityKnocks'
     */
    public String getCardType(){
        return type;
    }

    /**
     * A function that when called returns a String
     * @return String of the cards description
     */
    public String getCardText(){
        return cardText;
    }
}
