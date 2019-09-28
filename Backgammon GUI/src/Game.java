import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Math.*;

public class Game implements Subject{
    Board b;
    Dice d;
    int turn =0 ;

    private ArrayList<Observer> observers;
    public Game() {
        observers = new ArrayList<>();
        d = new Dice();
        turn = d.findStartingPlayer();
        d.roll();
    }
    public void getBoard(Board b){
        this.b = b;
    }
    public void setTurn(int turn) {
        this.turn = turn;
    }
    public int getTurn() {
        return turn;
    }

    public boolean play(int pickSlot, int dropSlot) {
        int pick =pickSlot;
        int drop =dropSlot;
        ArrayList<Integer> usedDice = new ArrayList<Integer>();
        if(hasValidMove(turn))
                return attempt(turn, pickSlot, dropSlot, usedDice);
        else
            return false;
    }

    public boolean attempt(int turn, int pickSlot, int dropSlot,ArrayList<Integer> usedDice) {
        boolean done = true;
        if (pickSlot == -2) {
            pickSlot = -1;
            if (turn == 2) pickSlot = 24;
            if (!canPlace(turn, pickSlot, dropSlot))
                done = false;
            if (b.blots.get(turn - 1) == 0)
                done = false;
            if(!d.canUseDice(abs(dropSlot - pickSlot)))
                done = false;
            if(done == true) {
                d.useDice(abs(dropSlot - pickSlot));
                moveFromBar(turn, pickSlot, dropSlot);
            }
        }
        else if(dropSlot== 24 || dropSlot == -1){
            if(!canPick(turn,pickSlot))
                done = false;
            if(!allInBase(turn))
                done = false;
            if(!bearOff(turn,pickSlot,dropSlot))
                done = false;
        }
        else {
            if(!canPick(turn,pickSlot))
                done = false;
            if (!canPlace(turn, pickSlot, dropSlot))
                done = false;
            if(!d.canUseDice(abs(dropSlot - pickSlot)))
                done = false;
            if(done) {
                d.useDice(abs(dropSlot - pickSlot));
                moveFromSlot(turn, pickSlot, dropSlot);
            }
        }
        if(done) {
            if (pickSlot == -2)
                usedDice.add(abs(24 * (turn / 2) - dropSlot));
            else
                usedDice.add(abs(dropSlot - pickSlot));
        }
        return done;
    }

    public void moveFromSlot(int turn, int pickSlot, int dropSlot) {
        b.slots.get(pickSlot).reduce();
        move(turn, dropSlot);
    }

    private void move(int turn, int dropSlot) {
        if (b.slots.get(dropSlot).getNumStones() == 1 && ((Slot)b.slots.get(dropSlot)).getPlayer() != turn)
            b.blots.set(((Slot)b.slots.get(dropSlot)).getPlayer() - 1,
                    b.blots.get(((Slot)b.slots.get(dropSlot)).getPlayer() - 1) + 1);
        (b.slots.get(dropSlot)).add(turn);
    }

    private boolean canPick(int turn, int pickSlot) {
        if (pickSlot > 23 || pickSlot < 0) // reduced by one to account for previously decriminting it to put in array
            return false;
        if (b.blots.get(turn - 1) > 0)
            return false;
        if (((Slot)b.slots.get(pickSlot)).getPlayer() != turn)
            return false;
        if (b.slots.get(pickSlot).getNumStones() == 0)
            return false;
        return true;
    }

    public boolean moveFromBar(int turn,int pickSlot, int dropSlot) {
        move(turn, dropSlot);
        b.blots.set(turn - 1, b.blots.get(turn - 1) - 1);
        return true;
    }

    public boolean canPlace(int turn, int pickSlot, int dropSlot) {
        if (dropSlot > 23 || dropSlot < 0) // given one extra on each side for the bearing off
            return false;

        if (turn == 1 && dropSlot < pickSlot)
            return false;
        if (turn == 2 && dropSlot > pickSlot)
            return false;
        if (((Slot)b.slots.get(dropSlot)).getPlayer() != turn && b.slots.get(dropSlot).numStones > 1)
            return false;

        return true;
    }
    public boolean hasValidMove(int turn){
        int from = 0;
        int to = 23;
        if(b.blots.get(turn-1)>0) {
            if(turn==1) {
                int pickSlot = -1;
                if ((canPlace(turn, pickSlot, d.die1-1) && d.die1Uses > 0)
                        || (canPlace(turn, pickSlot, d.die2-1) && d.die2Uses > 0))
                    return true;

            }
            if(turn==2) {
                int pickSlot = 24;
                if ((canPlace(turn, pickSlot, 24 - d.die1) && d.die1Uses > 0)
                        || (canPlace(turn, pickSlot, 24 - d.die2) && d.die2Uses > 0))
                    return true;
            }
        }
        else {
            for (int i = 0; i < 24; i++) {
                if(((Slot)b.slots.get(i)).getPlayer() == turn){
                    if ((canPlace(turn, i, i - d.die1 + 2*(turn%2)*d.die1)&&d.die1Uses>0)
                            || (canPlace(turn, i, i - d.die2 + 2*(turn%2)*d.die2)&&d.die2Uses>0))
                        return true;
                    else if (allInBase(turn)) {
                        if (canBearOff(turn)) return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean bearOff(int turn, int pickSlot, int dropSlot){
        boolean valid = false;
        if(bearOffNormally(turn,pickSlot,dropSlot)){
            d.useDice(abs(dropSlot-pickSlot));
            valid = true;

        }
        else if(canBearOffNotNormally(turn,pickSlot)){
            valid = bearOffNotNormally();
        }
        if (valid){
            ((Slot)b.slots.get(pickSlot)).reduce();
            if (turn==1) {
                b.score1++;
            }
            else
                b.score2++;
        }
        return valid;
    }
    public boolean canBearOff(int turn){// In any way, used only to check if player has valid moves.
        if(!allInBase(turn))
            return false;
        int furthestSlot = getFurthestSlot(turn);
        int movesAway = 0;
        if(turn==1){
            movesAway = 24 -furthestSlot;
            if(     (((Slot)b.slots.get(24-d.die1)).getPlayer()==1  && d.die1Uses > 0)
                    ||(((Slot)b.slots.get(24-d.die2)).getPlayer()==1 && d.die2Uses > 0))
                return true;
        }
        else if (turn==2){
            movesAway = furthestSlot + 1;
            if(     (((Slot)b.slots.get(d.die1-1)).getPlayer()==2  && d.die1Uses > 0)
                    ||(((Slot)b.slots.get(d.die2-1)).getPlayer()== 2&& d.die2Uses > 0))
                return true;
        }
        if((d.die1 > (movesAway)&&d.die1Uses>0) || (d.die2 > (movesAway)&&d.die2Uses>0))
            return true;

        return false;
    }
    public boolean bearOffNormally(int turn,int pickSlot,int dropSlot){//by only making movements that correspond to dice

        if(     ((turn==2 && dropSlot == -1) || (turn==1 && dropSlot == 24 ))
                &&d.canUseDice(abs(dropSlot-pickSlot)))
            return  true;
        return false;
    }
    public boolean canBearOffNotNormally(int turn, int pickSlot){//by making movements smaller than dice
        int furthestSlot = getFurthestSlot(turn);
        int movesAway = 0;
        if(turn==1)
            movesAway = 24 -furthestSlot;
        else
            movesAway = furthestSlot+1;
        if(pickSlot!=furthestSlot)
            return false;
        if(!((d.die1 >= (movesAway)&&d.die1Uses>0) || (d.die2 >= (movesAway)&&d.die2Uses>0))){
            return false;
        }
        return true;
    }
    public boolean bearOffNotNormally(){

        if (d.canUseDice(max(d.die1,d.die2)))
            d.useDice(max(d.die1,d.die2));
        else if (d.canUseDice(min(d.die1,d.die2)))
            d.useDice(min(d.die1,d.die2));
        else return false;
        return true;
    }


    private int getFurthestSlot(int turn) { // furthest slot from score box
        int furthestSlot = 0;
        if(turn==1){
            for (int i =18; i<24; i++){
                if (((Slot)b.slots.get(i)).getPlayer()==1){
                    furthestSlot = i;
                    break;
                }
            }
        }
        if(turn==2){
            for (int i =5; i>-1; i--){
                if (((Slot)b.slots.get(i)).getPlayer()==2){
                    furthestSlot = i;
                    break;
                }
            }
        }
        return furthestSlot;
    }

    public boolean allInBase(int player) {
        if (b.blots.get(player - 1) > 0)
            return false;
        int from = 0;
        int to = 18;
        if (player == 2) {
            from = 6;
            to = 24;
        }
        for (int i = from; i < to; i++) {
            if(((Slot)b.slots.get(i)).getPlayer() == player)
                return false;
        }
        return true;
    }
    public boolean isEnded(){
        if(winner()!=0)
            return true;
        return false;
    }
    public int winner(){
        int winner=0;
        if(b.score1==15)
            winner=1;
        else if(b.score2==15)
            winner=2;
        return winner;
    }
    @Override
    public void register(Observer obeserver){observers.add(obeserver);}

    @Override
    public void notifyObserver(){
        for(Observer each: observers)
            each.update();
    }

}
