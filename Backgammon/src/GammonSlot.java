import java.util.ArrayList;

public class GammonSlot {
    int numStones;
    int player;

    public GammonSlot(){
        player =0;
        numStones =0;
    }

    public void setNumStones(int numStones) {
        this.numStones = numStones;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getNumStones() {
        return numStones;
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
        char shape = 'o';
        if(player==2)
            shape = 'x';
        for(int i =0; i < numStones; i++)
            System.out.print(shape);
    }
}
