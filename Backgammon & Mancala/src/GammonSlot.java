public class GammonSlot extends Slot {
    int player;

     GammonSlot(){
         player =0;
         numStones = 0;
     }

    public void setPlayer(int player) {
        this.player = player;
    }
    public int getPlayer() {
        return player;
    }

    public void reduce(){
        if(numStones > 0 )
            numStones--;
        else throw new ArithmeticException();
        if (numStones ==0)
            player = 0;
    }
    public void add (int player){
        if(this.player == player)
            numStones++;
        else {
            this.player= player;
            numStones =1;
        }
    }
    public void drawSlot(){
        if(player==2)
            shape = 'x';
        else
            shape = 'o';
        super.drawSlot();
    }
}
