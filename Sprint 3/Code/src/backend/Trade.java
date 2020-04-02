package backend;

import java.util.ArrayList;

public class Trade {
    Player sender;
    Player receiver;
    int senderMoney;
    int receiverMoney;
    ArrayList<BuyableTile> senderTiles;
    ArrayList<BuyableTile> receiverTiles;

    public Trade(Player sender, Player receiver){
        this.sender = sender;
        this.receiver = receiver;
        senderTiles = new ArrayList<BuyableTile>();
        receiverTiles = new ArrayList<BuyableTile>();
    }

    public Player getSender(){
        return sender;
    }
    public Player getReceiver(){
        return receiver;
    }
    public int getSenderMoney(){
        return senderMoney;
    }
    public int getReceiverMoney(){
        return receiverMoney;
    }
    public void setSenderMoney(int offer) throws PlayerDoesNotOwnException{
        if(sender.getMoney()>=offer){
            senderMoney = offer;
        }
        else{
            throw new PlayerDoesNotOwnException("Sender does not have enough money for this deal");
        }
    }
    public void setReceiverMoney(int offer) throws PlayerDoesNotOwnException{
        if(receiver.getMoney()>=offer){
            receiverMoney = offer;
        }
        else{
            throw new PlayerDoesNotOwnException("Sender does not have enough money for this deal");
        }
    }
    public void addTileSender(BuyableTile tile){
        senderTiles.add(tile);
    }
    public void addTileReceiver(BuyableTile tile){
        receiverTiles.add(tile);
    }
    public void removeTileSender(BuyableTile tile){
        senderTiles.remove(tile);
    }
    public void removeTileReceiver(BuyableTile tile){
        receiverTiles.remove(tile);
    }
    public void acceptTrade(){
        for(int i = 0; i< senderTiles.size();i++){
            senderTiles.get(i).setOwner(receiver);
        }
        for(int i = 0; i< receiverTiles.size();i++){
            receiverTiles.get(i).setOwner(sender);
        }
        sender.setMoney(sender.getMoney()-senderMoney+receiverMoney);
        receiver.setMoney(receiver.getMoney()-receiverMoney+senderMoney);
    }

}
