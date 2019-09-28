import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MancalaApp {
    public static void main(String[] args) {

        MancalaBoard b = new MancalaBoard();
        MancalaGame g = new MancalaGame();
        int turn = 1;
        int input;
        int check = 0;
        System.out.println("Welcome to Mancala");
        System.out.println("Moves are counterclockwise");
        boolean repeat = false;
        while (true) {
            b.print();
            if(repeat)System.out.println("Player:" + turn + " Gets another turn for ending in their cup");
            if (turn == 1)
                System.out.println("It is player 1's turn");
            else if (turn == 2)
                System.out.println("It is player 2's turn");
            System.out.print("Choose a slot: ");
            input = getInput(b.slots, turn);
            System.out.println();
                if (g.chooseSlot(turn, input, b.slots)) {
                    repeat =true;
                    turn = turn % 2 + 1;
                }else repeat = false;
                check = g.checkForWinner(turn,b.slots);
                if (check != 0) {
                    turn = check;
                    break;
                }
                turn = turn%2 +1;
            }
            if (check == 3)
                System.out.println("IT IS A DRAW!");
            else
                System.out.println("PLAYER"+check+" WINS!");
        }

    private static int getInput(ArrayList<Slot> slots, int turn) {
        int input;
        Scanner scan = new Scanner(System.in);
        while(true) {
            try {
                input = scan.nextInt();
                if (input < 0 || input > 6 || slots.get(input - (1/turn) + (turn/2)*6).getBalls() == 0)
                    throw new InputMismatchException();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please choose an integer between 1-6 for a slot that is not empty");
                scan = new Scanner(System.in);
            }
        }
        return input;
    }
}


