import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String args[]) {
        Scanner x = new Scanner(System.in);
        int game = 0;
        System.out.println("Welcome to Abdullah's games. Enter 1 to play Mancala or 2 for Backgammon");
        boolean correctInput = false;
        while(!correctInput){
            try{
                game = x.nextInt();
                if(game != 1 && game != 2)
                    throw new InputMismatchException();
            }catch (InputMismatchException e){
                System.out.println("Please only enter 1 or 2");
                correctInput = false;
                continue;
            }
            correctInput = true;
        }
        Game g1 = new Game();
        g1.play();
    }
}
