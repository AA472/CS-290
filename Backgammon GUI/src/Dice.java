import java.util.Random;

public class Dice {
    int die1;
    int die2;
    int die1Uses;
    int die2Uses;

    Random rand;
    Dice(){
        rand = new Random();
        die1 = 0;
        die2 =0;
        die1Uses =0;
        die2Uses =0;
    }

    public int getDice1() {
        return die1;
    }

    public int getDice2() {
        return die2;
    }

    public void roll(){
        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        if(die1 == die2){
            die1Uses = die2Uses = 2;
        }else{
            die1Uses = die2Uses = 1;
        }
    }
    public int findStartingPlayer(){
        do{
            die1 = rand.nextInt(6) + 1;
            die2 = rand.nextInt(6) + 1;
        }while(die1 == die2);
        if(die1 > die2)
            return 1;
        else return 2;

    }
    public boolean canUseDice(int moveBy){
        boolean valid = false ;
        if(die1Uses> 0 && die1 == moveBy) valid = true;
        else if(die2Uses >0 && die2 == moveBy) valid = true;
        return valid;
    }
    public void useDice(int moveBy) {
        if (die1 == moveBy && die1Uses>0)
            die1Uses--;
        else if(die2 == moveBy)
            die2Uses--;
    }
}
