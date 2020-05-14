package frontend;

import backend.*;

/**
 * Extends the backend GameMaster in order to easily implement on the frontend. This when called sets up the game,
 * It has methods for when landing on a tile, buying a tile and drawing a card.
 * @author Joe C
 */
public class GameMasterGui extends GameMaster {


    public GameMasterGui(boolean[] numPlayers, Tokens[] playerTokens, int abridgeValue) {
        this.setup(numPlayers, playerTokens, abridgeValue);
    }

    /**
     * Works out what to do when a player lands on a specific tile. buy it, draw a card, etc.
     */
    public void landedTileAction(int totalRoll) {
        if (this.getBuyable(this.getPlayer(this.getCurTurn()).getPlace())){
            //can buy/auction time
            if (this.getPlayer(this.getCurTurn()).isPassedGo()){
                if(this.getPlayer(this.getCurTurn()) instanceof AI){
                     if (!(((AI) this.getPlayer(this.getCurTurn())).purchaseOrAuction((BuyableTile) this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())))){
                         buyingTile(false);
                     }
                }
                else {
                    buyingTile((Boolean) (new FirstTimeLandedDialog(this)).getR());
                }
            }
        }
        else if (this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof CardDraw){
            //card draw
            if (((CardDraw) this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).getDrawType() == DrawTypes.opportunityKnocks) {
                // get method effect opportunity knocks
                new TileEffectDialog((Card) this.getBoard().getOpportunityKnocks().get(0),this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
                this.applyTileEffect();
            }
            else{
                // get method effect potluck
                TileEffectDialog dialog = new TileEffectDialog((Card) this.getBoard().getPotLuck().get(0), this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
                if(((Card) this.getBoard().getPotLuck().get(0)).getMethodName().equals("throw")){
                    if((boolean)dialog.getR()){
                        ((Card) this.getBoard().getPotLuck().get(0)).cardEffect(this.getPlayer(this.getCurTurn()), true);
                    }
                    else{
                        new TileEffectDialog((Card) this.getBoard().getOpportunityKnocks().get(0),this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
                        ((Card) this.getBoard().getOpportunityKnocks().get(0)).cardEffect(this.getPlayer(this.getCurTurn()), false);
                    }
                }
                else {
                    this.applyTileEffect();
                }

            }

        }
        else if (this.getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof  InstructionOnCross ||
                this.getPlayer(this.getCurTurn()).getPlace() == this.getPlayer(this.getCurTurn()).getJail()){
            // go and jail do nothing as dialogs already called
            System.out.println(" ");
        }
        else if (this.getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof toJail) {
            // go to jail
            new TileEffectDialog(this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
            this.applyTileEffect();
        }
        else if(this.getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof BuyableTile){
            //rent time if not the owner
            if (((BuyableTile) this.getTile(this.getPlayer(this.getCurTurn()).getPlace())).getPlayer().equals(this.getPlayer(this.getCurTurn()))) {
                if (this.getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof Utility) {
                    new TileEffectDialog(this.applyTileEffect(totalRoll), this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
                } else {
                    new TileEffectDialog(this.applyTileEffect(), this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
                }
            }
        }
        else if (this.getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof FreeParking) {
            // free parking
            new TileEffectDialog(this.applyTileEffect(), this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
        }
    }

    /**
     * Implements the first time buying a property by working out whether it is being auctioned or brought for base price,
     * it then implements this.
     */
    private void buyingTile(boolean buyTile){
        if(buyTile){
            //buy tile
            try {
                this.applyTileEffect(this.getPlayer(this.getCurTurn()));
            } catch (NotEnoughMoneyException e) {
                this.applyTileEffect((int[]) (new AuctionDialog(this)).getR());
            }
        }
        else{
            // auction
            new TileEffectDialog(this.getPlayer(manageAuction()), this.getTile(this.getPlayer(this.getCurTurn()).getPlace()));
        }

    }

    /**
     * called to manage the auction process, with AI and Player Bids, returns int for player who won!
     * @return int
     */
    private int manageAuction(){
        int[] bids = (int[]) new AuctionDialog(this).getR();
        for(int i = 0; i < this.getPlayers().length; i++){
            if(i >= this.getPlayers().length - this.getNumAI()){
                bids[i] = ((AI) this.getPlayer(i)).getBidOffer((BuyableTile) this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace()));
            }
        }
        return this.applyTileEffect(bids);
    }

}
