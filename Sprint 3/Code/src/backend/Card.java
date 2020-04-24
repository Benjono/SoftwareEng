package backend;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Card {
    private String cardText;
    private String methodName;
    private Object[] params;
    private Class[] paramTypes;
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
        this.type=potOrKnock;
        paramTypes = new Class[params.length+1];
        paramTypes[0] = Player.class;
        for(int i=1; i<params.length+1;i++){
            paramTypes[i]=params[i-1].getClass();
        }
        gameMaster=gm;
    }

    /**
     * This is the [i]only[/i] function apart from the constructor that should be called. It determines which card effect to use and executes it using reflection
     *
     * @param player the player to apply the cards effect to
     * @return returns a string, return doesn't change
     */
    public String cardEffect(Player player){
        try {
            Class<?> c = Class.forName("Card"); //get the class of card
            Method method = c.getDeclaredMethod(methodName,paramTypes); //get the method with methodName and parameters of paramTypes (an array)
            Object newParams = Arrays.copyOf(new Object[]{player},params.length+1);
            System.arraycopy(params,0,newParams,1,params.length);
            Object m = method.invoke(this,newParams);
            System.out.println(m);
        } catch (Exception e) {
            System.out.print("Reee");
        }
        return "e";
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
     */
    public void repairBill(Player player){

    }

    /**
     * Gives the player a get out of jail free card, not implemented
     * @param player the player to apply the effect to
     */
    public void getOutOfJail(Player player){

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
