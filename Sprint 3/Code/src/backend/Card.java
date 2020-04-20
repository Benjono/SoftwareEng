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
    public Card(String Description, String potOrKnock, String Method, GameMaster gm, Object[] params){
        cardText=Description;
        methodName=Method;
        this.params=params;
        this.type=potOrKnock;
        paramTypes = new Class[params.length+1];
        paramTypes[0] = Player.class;
        for(int i=1; i<params.length+1;i++){
            paramTypes[i]=params[i].getClass();
        }
        gameMaster=gm;
    }
    public String cardEffect(Player player){
        try {
            Class<?> c = Class.forName("Card");
            Method method = c.getDeclaredMethod(methodName,paramTypes);
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
    public void birthday(Player player, int money){
        for (Player p : gameMaster.getPlayers()){
            player.setMoney(player.getMoney()+money);
            p.setMoney(p.getMoney()-money);
        }
    }
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
    public void repairBill(Player player){

    }
    public void getOutOfJail(Player player){

    }
    public void toJail(Player player){
        player.jail();
    }
    public String getCardType(){
        return type;
    }
    public String getCardText(){
        return cardText;
    }
}
