package frontend;

import backend.*;

public class GameMasterGui extends GameMaster {
    private int plNum;
    private int okNum;

    public GameMasterGui(int numPlayers, Tokens[] playerTokens){
        this.setup(numPlayers, playerTokens);
        this.okNum = 0;
        this.plNum = 0;
    }

    public void landedTileAction() {
        if (this.getBuyable(this.getPlayer(this.getCurTurn()).getPlace())){
            //can buy/auction time
            buyingTile();
        }
        else if (this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof CardDraw){
            //card draw
            cardDraw();
        }
        else if (this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace()) instanceof toJail){

        }
        // free parking
        // go to jail
        else{
            //rent time
        }
    }

    private void buyingTile(){
        if(!(Boolean) (new FirstTimeLandedDialog(this)).getR()){
            // auction
            this.applyTileEffect((int[]) (new AuctionDialog(this)).getR());
            // winner dialog
            System.out.println(((BuyableTile)this.getBoard().getTileGrid()[this.getPlayer(this.getCurTurn()).getPlace()]).getPlayer().getToken());

            //needs winner dialog here
        }
        else {
            // player buys tile
            this.applyTileEffect(this.getPlayer(this.getCurTurn()));
        }
    }

    private void cardDraw() {
        // OK or Potluck?
        if (((CardDraw) this.getBoard().getTile(this.getPlayer(this.getCurTurn()).getPlace())).getDrawType() == DrawTypes.opportunityKnocks) {
            // get method effect opportunity knocks
            ((Card)this.getBoard().getOpportunityKnocks().get(okNum)).cardEffect(this.getPlayer(this.getCurTurn()));
            new CardDrawDialog(this.getTile(this.getPlayer(this.getCurTurn()).getPlace()),(Card) this.getBoard().getOpportunityKnocks().get(okNum));
            okNum++;
            if (okNum >= 16){okNum = 0;}
        }
        else{
            // get method effect potluck
            ((Card)this.getBoard().getPotLuck().get(plNum)).cardEffect(this.getPlayer(this.getCurTurn()));
            new CardDrawDialog(this.getTile(this.getPlayer(this.getCurTurn()).getPlace()),(Card) this.getBoard().getPotLuck().get(plNum));
            plNum++;
            if (plNum >= 16){plNum = 0;}
        }
    }
}
