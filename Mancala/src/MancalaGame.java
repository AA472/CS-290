import java.util.ArrayList;

public class MancalaGame {

    public int allBallsInSlots(int player, ArrayList<Slot> slots) {
        int sum = 0;
        for (int i = 0 + (player - 1) * 7; i < 6 + (player - 1) * 7; i++) {
            sum += slots.get(i).getBalls();
        }
        return sum;
    }

    public int checkForWinner(int player, ArrayList<Slot> slots) {
        int winner = 0;
        int ballsP1 = allBallsInSlots(1, slots);
        int ballsP2 = allBallsInSlots(2, slots);
        if (ballsP1 == 0 || ballsP2 == 0) {
            slots.get(6).setBalls(slots.get(6).getBalls() + ballsP1);
            slots.get(13).setBalls(slots.get(13).getBalls() + ballsP2);
            if (slots.get(6).getBalls() > slots.get(13).getBalls())
                winner = 1;
            else if (slots.get(13).getBalls() > slots.get(6).getBalls())
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
        while (slots.get(chosenSlot).getBalls() > 1) {
            slots.get(chosenSlot).addBalls(-1);
            slots.get(droplocation).addBalls(1);
            droplocation++;
            if (droplocation > 13)
                droplocation = droplocation - 13;
        }

        slots.get(chosenSlot).addBalls(-1);
        if ((droplocation == 6 && player == 1) || (droplocation == 13 && player == 2)) {
            slots.get(droplocation).addBalls(1);
            repeat = true;
        }
        else if (slots.get(droplocation).getBalls() == 0){
            if (droplocation > -1 && droplocation < 6 && player == 1) {
                slots.get(6).setBalls(slots.get(6).getBalls() + slots.get(droplocation + 7).getBalls() + 1);
                slots.get(12-droplocation).setBalls(0);
            }
            if (droplocation > 6 && droplocation < 13 && player == 2) {
                slots.get(13).setBalls(slots.get(13).getBalls() + slots.get(droplocation - 7).getBalls() + 1);
                slots.get(12-droplocation).setBalls(0);
            }
            else
                slots.get(droplocation).addBalls(1);
        }
        else
            slots.get(droplocation).addBalls(1);
        return repeat;
    }
}
