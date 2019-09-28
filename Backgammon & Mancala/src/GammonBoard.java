import java.util.ArrayList;
import java.util.Arrays;

public class GammonBoard extends Board {

    ArrayList<Integer> blots;
    int score1, score2;
    public GammonBoard() {
        blots = new ArrayList<>();
        score1 = 0; // player 1's score
        score2 = 0; // player 2's score
        blots.add(0); //player 1
        blots.add(0); //player 2
        shiftValue  = 11;
        slots.removeAll(slots);
        for(int i = 0; i<24; i++){
            GammonSlot s = new GammonSlot();
            slots.add(s);
        }
        slots.get(0).setNumStones(2);  ((GammonSlot)slots.get(0)).setPlayer(1);
        slots.get(5).setNumStones(5);  ((GammonSlot)slots.get(5)).setPlayer(2);
        slots.get(7).setNumStones(3);  ((GammonSlot)slots.get(7)).setPlayer(2);
        slots.get(11).setNumStones(5); ((GammonSlot)slots.get(11)).setPlayer(1);
        slots.get(12).setNumStones(5); ((GammonSlot)slots.get(12)).setPlayer(2);
        slots.get(16).setNumStones(3); ((GammonSlot)slots.get(16)).setPlayer(1);
        slots.get(18).setNumStones(5); ((GammonSlot)slots.get(18)).setPlayer(1);
        slots.get(23).setNumStones(2); ((GammonSlot)slots.get(23)).setPlayer(2);
    }
    @Override
    public void drawLine(int slotNum) {

        slotNum = 11 - slotNum;
        super.drawLine(slotNum);
    }
    @Override
    public void draw(){
        char[] border = new char[55];
        Arrays.fill(border,0,2, ' ');Arrays.fill(border,3,55, '-');

        System.out.println(border);
        for(int i = 0; i< slots.size()/2; i++){
            if(i== slots.size()/4) {
                drawBar();
            }
            drawLine(i);
        }
        System.out.println(border);
        super.drawScoreBox(1,score1);
        System.out.println(border);
        super.drawScoreBox(2,score2);
    }

    public void drawBar() {
        char[] arr = new char[25 - blots.get(0)];
        char[] arr2 = new char[25 - blots.get(1)];
        char[] blots1 = new char[blots.get(0)];
        char[] blots2 = new char[blots.get(1)];
        char[] border = new char[55];
        Arrays.fill(arr, ' ');
        Arrays.fill(arr2, ' ');
        Arrays.fill(blots1, 'o');
        Arrays.fill(blots2, 'x');
        Arrays.fill(border, 0, 2, ' ');
        Arrays.fill(border, 3, 55, '-');
        System.out.println(border);
        System.out.print("-1-|");
        System.out.print(arr);
        System.out.print(blots1);
        System.out.print(blots2);
        System.out.print(arr2);
        System.out.println("|- ");
        System.out.println(border);
    }
}
