import java.awt.*;
import java.util.ArrayList;

public class Slot {
    int numStones;
    int player;
    Color color;

    public Slot(){
        player =0;
        numStones =0;
    }

    public void setNumStones(int numStones) {
        this.numStones = numStones;
    }

    public void setPlayer(int player) {
        this.player = player;
        if (player == 1)
            color = Color.RED;
        else
            color = Color.WHITE;
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
            setPlayer(player);
            numStones =1;
        }
    }
}
