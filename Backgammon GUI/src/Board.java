import java.util.ArrayList;

public class Board {
    ArrayList<Slot> slots;
    int score1, score2;
    ArrayList<Integer> blots;
    Board() {
        slots = new ArrayList<Slot>();
        blots = new ArrayList<>();
        score1 = 0; // player 1's score
        score2 = 0; // player 2's score
        blots.add(0); //player 1
        blots.add(0); //player 2
        for (int i = 0; i < 24; i++) {
            Slot s = new Slot();
            slots.add(s);
        }
        slots.get(0).setNumStones(2);
        slots.get(0).setPlayer(1);
        slots.get(5).setNumStones(5);
        slots.get(5).setPlayer(2);
        slots.get(7).setNumStones(3);
        slots.get(7).setPlayer(2);
        slots.get(11).setNumStones(5);
        slots.get(11).setPlayer(1);
        slots.get(12).setNumStones(5);
        slots.get(12).setPlayer(2);
        slots.get(16).setNumStones(3);
        slots.get(16).setPlayer(1);
        slots.get(18).setNumStones(5);
        slots.get(18).setPlayer(1);
        slots.get(23).setNumStones(2);
        slots.get(23).setPlayer(2);
    }
}