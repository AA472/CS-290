import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MancalaGame {
    Board b;
    MancalaGame(){
        b = new Board();
    }
    public void play() {
        int turn = 1;
        int input;
        int check = 0;
        System.out.println("Welcome to Mancala");
        System.out.println("Moves are counterclockwise");
        System.out.println("Player 1 is the left half of the board, and player 2 is the right half.");
        System.out.println("Score stones will be: * ");
        boolean repeat = false;
        while (true) {
            b.draw();
            if(repeat)System.out.println("Player:" + turn + " Gets another turn for ending in their cup");
            if (turn == 1)
                System.out.println("It is player 1's turn");
            else if (turn == 2)
                System.out.println("It is player 2's turn");
            System.out.print("Choose a slot: ");
            input = getInput(b.slots, turn);
            System.out.println();
            if (chooseSlot(turn, input, b.slots)) {
                repeat =true;
                turn = turn % 2 + 1;
            }else repeat = false;
            check = checkForWinner(turn,b.slots);
            if (check != 0) {
                turn = check;
                break;
            }
            turn = turn%2 +1;
        }
        if (check == 3)
            System.out.println("IT IS A DRAW!");
        else
            System.out.println("PLAYER "+check+" WINS!");
    }
    private static int getInput(ArrayList<Slot> slots, int turn) {
        int input;
        Scanner scan = new Scanner(System.in);
        while(true) {
            try {
                input = scan.nextInt();
                if (input < 0 || input > 6 || slots.get(input - (1/turn) + (turn/2)*6).getNumStones()== 0)
                    throw new InputMismatchException();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please choose an integer between 1-6 for a slot that is not empty");
                scan = new Scanner(System.in);
            }
        }
        return input;
    }
    public int allBallsInSlots(int player, ArrayList<Slot> slots) {
        int sum = 0;
        for (int i = 0 + (player - 1) * 7; i < 6 + (player - 1) * 7; i++) {
            sum += slots.get(i).getNumStones();
        }
        return sum;
    }

    public int checkForWinner(int player, ArrayList<Slot> slots) {
        int winner = 0;
        int ballsP1 = allBallsInSlots(1, slots);
        int ballsP2 = allBallsInSlots(2, slots);
        if (ballsP1 == 0 || ballsP2 == 0) {
            slots.get(6).setNumStones(slots.get(6).getNumStones() + ballsP1);
            slots.get(13).setNumStones(slots.get(13).getNumStones() + ballsP2);
            if (slots.get(6).getNumStones() > slots.get(13).getNumStones())
                winner = 1;
            else if (slots.get(13).getNumStones() > slots.get(6).getNumStones())
                winner = 2;
            else
                winner = 3;
        }
        return winner;
    }


    public boolean chooseSlot(int player, int slot, ArrayList<Slot> slots) {
        boolean repeat = false;
        int chosenSlot = slot - 1 + (player-1) * 7;
        int droplocation = chosenSlot + 1;
        while (slots.get(chosenSlot).getNumStones() > 1) {
            slots.get(chosenSlot).addStones(-1);
            slots.get(droplocation).addStones(1);
            droplocation++;
            if (droplocation > 13)
                droplocation = droplocation - 13;
        }

        slots.get(chosenSlot).addStones(-1);
        if ((droplocation == 6 && player == 1) || (droplocation == 13 && player == 2)) {
            slots.get(droplocation).addStones(1);
            repeat = true;
        }
        else if (slots.get(droplocation).getNumStones() == 0){
            if (droplocation > -1 && droplocation < 6 && player == 1) {
                slots.get(6).setNumStones(slots.get(6).getNumStones() + slots.get(droplocation + 7).getNumStones() + 1);
                slots.get(12-droplocation).setNumStones(0);
            }
            if (droplocation > 6 && droplocation < 13 && player == 2) {
                slots.get(13).setNumStones(slots.get(13).getNumStones() + slots.get(droplocation - 7).getNumStones() + 1);
                slots.get(12-droplocation).setNumStones(0);
            }
            else
                slots.get(droplocation).addStones(1);
        }
        else
            slots.get(droplocation).addStones(1);
        return repeat;
    }
}
