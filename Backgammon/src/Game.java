import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Math.*;

//I just realized that the path has to be specified and I cannot just accept
// the addition of the dice as the input from the user. I wasted 5 hours trying to make that happen :(

public class Game {
    GammonBoard b;
    Dice d;
    ArrayList<Integer> blots = new ArrayList<>();
    int score1, score2; //How many stones did each player get to home

    public Game() {

            blots.add(0); //player 1
            blots.add(0); //player 2
            score1 = score2 = 0;
            b = new GammonBoard();
            d = new Dice();
            System.out.println("Player 1 is: o");
            System.out.println("Player 2 is: x");
            System.out.println("Player 1 moves CLOCKWISE.");
            System.out.println("Player 2 moves ANTI-CLOCKWISE");
            play();

    }

    public void play() {
        Scanner x = new Scanner(System.in);
        int pickSlot =-100;
        int dropSlot =-100;
        int turn = 0;
        ArrayList<Integer> usedDice = new ArrayList<Integer>();
        turn = d.findStartingPlayer();
        while (!isEnded()) { //isEnded
            d.roll();

            usedDice.removeAll(usedDice);
            while ((d.die2Uses != 0 || d.die1Uses != 0) && !isEnded()) {
                b.drawBoard(blots,score1,score2);
                if(!hasValidMove(turn)) {
                    System.out.println("The roll was: "+ d.die1 + " " + d.die2 + " Therefore " +
                            "player: " +turn +" has no more valid moves, so we will switch the turn");
                    break;
                }
                if(isEnded())break;

                System.out.println("it is player " + turn + "'s turn");
                if (d.die1 == d.die2){
                    System.out.println("Your dice are: " + d.die1 + " and " + d.die2 + " and " + d.die2 +" and " + d.die2);
                }
                else System.out.println("Your dice are: " + d.die1 + " and " + d.die2);
                //System.out.println("You have already used these dice: " + usedDice);
                boolean trial = true;
                while (trial) {
                    trial = false;
                    try {
                        System.out.println("Blots in 1 = " + blots.get(0) + " Blots in 2 = " + blots.get(1));
                        System.out.println("Enter the numbers to move one piece in this format [ from to ]");
                        System.out.println("If you want to remove a stone from the bar, use value -1 for your 'from' ");
                        System.out.println("Your Input: ");

                        try {
                            pickSlot = x.nextInt() -1;
                            dropSlot = x.nextInt() -1;
                        }
                        catch (InputMismatchException e){
                            System.out.println("Please enter only integers. Try again: ");
                            trial = true;
                            x = new Scanner(System.in);
                            continue;
                        }
                        attempt(turn, pickSlot, dropSlot,usedDice);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Illegal move. Try again.");
                        trial = true;
                    }
                }
            }
            turn = (turn % 2) + 1;
        }
        System.out.println("The winner is player : " + winner());
    }

    public void attempt(int turn, int pickSlot, int dropSlot,ArrayList<Integer> usedDice) {
        if (pickSlot == -2) {
             pickSlot = -1;
            if (turn == 2) pickSlot = 24;
            if (!canPlace(turn, pickSlot, dropSlot))
                throw new IllegalArgumentException("You cannot place your slot here. Try again. ");
            if (blots.get(turn - 1) == 0)
                throw new IllegalArgumentException("No blots. Try again. ");
            if(!d.canUseDice(abs(dropSlot - pickSlot)))
                throw new IllegalArgumentException("Invalid move. Check your dice, and try again.");
            d.useDice(abs(dropSlot-pickSlot));
            moveFromBar(turn,pickSlot, dropSlot);
        }
        else if(dropSlot== 24 || dropSlot == -1){
            if(!canPick(turn,pickSlot))
                throw new IllegalArgumentException("Illegal move. You cannot pick from this slot. Try again.");
            if(!allInBase(turn))
                throw new IllegalArgumentException("Illegal move. You cannot bear off unless all your stones are in base");
            if(!bearOff(turn,pickSlot,dropSlot))
                throw new IllegalArgumentException("Illegal move. You cannot bear off. Try again.");
        }
        else {
            if(!canPick(turn,pickSlot))
                throw new IllegalArgumentException("Illegal move. You cannot pick from this slot. Try again.");
            if (!canPlace(turn, pickSlot, dropSlot))
                throw new IllegalArgumentException("You cannot place your slot here. Try again ");
            if(!d.canUseDice(abs(dropSlot - pickSlot)))
                throw new IllegalArgumentException("Invalid move. Check your dice, and try again.");
            d.useDice(abs(dropSlot-pickSlot));
            moveFromSlot(turn,pickSlot,dropSlot);
        }
        if(pickSlot==-2)
            usedDice.add(abs(24*(turn/2) - dropSlot));
        else
            usedDice.add(abs(dropSlot-pickSlot));

    }

    public void moveFromSlot(int turn, int pickSlot, int dropSlot) {
        b.gammonSlots.get(pickSlot).reduce();
        move(turn, dropSlot);
    }

    private void move(int turn, int dropSlot) {
        if (b.gammonSlots.get(dropSlot).numStones == 1 && b.gammonSlots.get(dropSlot).getPlayer() != turn)
            blots.set(b.gammonSlots.get(dropSlot).getPlayer() - 1, blots.get(b.gammonSlots.get(dropSlot).getPlayer() - 1) + 1);
        b.gammonSlots.get(dropSlot).add(turn);
    }

    private boolean canPick(int turn, int pickSlot) {
        if (pickSlot > 23 || pickSlot < 0) // reduced by one to account for previously decriminting it to put in array
            return false;
        if (blots.get(turn - 1) > 0)
            return false;
        if (b.gammonSlots.get(pickSlot).getPlayer() != turn)
            return false;
        if (b.gammonSlots.get(pickSlot).getNumStones() == 0)
            return false;
        return true;
    }

    public boolean moveFromBar(int turn,int pickSlot, int dropSlot) {
            move(turn, dropSlot);
            blots.set(turn - 1, blots.get(turn - 1) - 1);
            return true;
    }

    public boolean canPlace(int turn, int pickSlot, int dropSlot) {
        if (dropSlot > 23 || dropSlot < 0) // given one extra on each side for the bearing off
            return false;

        if (turn == 1 && dropSlot < pickSlot)
            return false;
        if (turn == 2 && dropSlot > pickSlot)
            return false;
        if (b.gammonSlots.get(dropSlot).getPlayer() != turn && b.gammonSlots.get(dropSlot).numStones > 1)
            return false;
       /* if(dropSlot - pickSlot == d.die1+d.die2){
            if(!(canPlace(turn,pickSlot,pickSlot+d.die1)|| canPlace(turn,pickSlot,pickSlot+d.die2)))
                return false;
        }
        if(d.die1==d.die2) {
            int steps = (dropSlot - pickSlot / d.die1);
            if (isValidWhenDouble(turn, pickSlot, dropSlot, steps))   //recurses all the way to check if all the steps are valid
                return false;
        }*/
        return true;
    }
    public boolean hasValidMove(int turn){
        int from = 0;
        int to = 23;
        if(blots.get(turn-1)>0) {
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
                if(b.gammonSlots.get(i).getPlayer() == turn){
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
            b.gammonSlots.get(pickSlot).reduce();
            if (turn==1)
                score1++;
            else
                score2++;
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
            if(     (b.gammonSlots.get(24-d.die1).getPlayer()==1  && d.die1Uses > 0)
                    ||(b.gammonSlots.get(24-d.die2).getPlayer()==1 && d.die2Uses > 0))
                return true;
        }
        else if (turn==2){
            movesAway = furthestSlot + 1;
            if(     (b.gammonSlots.get(d.die1-1).getPlayer()==2  && d.die1Uses > 0)
                    ||(b.gammonSlots.get(d.die2-1).getPlayer()== 2&& d.die2Uses > 0))
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
                if (b.gammonSlots.get(i).getPlayer()==1){
                    furthestSlot = i;
                    break;
                }
            }
        }
        if(turn==2){
            for (int i =5; i>-1; i--){
                if (b.gammonSlots.get(i).getPlayer()==2){
                    furthestSlot = i;
                    break;
                }
            }
        }
        return furthestSlot;
    }

    public boolean allInBase(int player) {
        if (blots.get(player - 1) > 0)
            return false;
        int from = 0;
        int to = 18;
        if (player == 2) {
            from = 6;
            to = 24;
        }
        for (int i = from; i < to; i++) {
            if(b.gammonSlots.get(i).getPlayer() == player)
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
        if(score1==15)
            winner=1;
        else if(score2==15)
            winner=2;
        return winner;
    }
}
    /*
    private boolean isValidWhenDouble(int turn, int pickSlot, int dropSlot, int steps) {
        if (steps ==0)
            return false;
        if(!(canPlace(turn,pickSlot,dropSlot-d.die1)))
            return true;
        return false;




                if (turn==1 && b.gammonSlots.get(i).player == 1) {
                    if (canPlace(turn, i, i + d.die1) || canPlace(turn, i, i + d.die2))
                        return true;
                    else if (allInBase(1)) {
                        if (canBearOff(1)) return true;
                    }
                }
                if (turn == 2 && b.gammonSlots.get(i).player == 2) {
                    if (canPlace(turn, i, i - d.die1) || canPlace(turn, i, i - d.die2))
                        return true;
                    else if (allInBase(2)) {
                        if (canBearOff(2)) return true;
                    }
    }*/
