import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    double shiftValue = 0;
    ArrayList<Slot> slots;
    Board() {
        slots = new ArrayList<Slot>();
        for (int i = 0; i < 14; i++) {
            Slot s = new Slot();
            slots.add(s);
        }
        slots.get(6).setNumStones(0);
        slots.get(13).setNumStones(0);
    }

    public void draw() {
        char[] border = new char[55];
        Arrays.fill(border,0,2, ' ');Arrays.fill(border,3,55, '-');

        System.out.println(border); // 55 dashes
        drawScoreBox(2,slots.get(13).getNumStones());
        System.out.println(border); // 55 dashes

        for(int i = 0; i< (slots.size()-1)/2; i++){
            /*if(i== slots.size()/4) {

            }*/
            drawLine(i);
        }
        System.out.println(border); // 55 dashes
        drawScoreBox(1,slots.get(6).getNumStones());
        System.out.println(border); // 55 dashes
    }

    public void drawScoreBox(int player,int score){
        char[] border = new char[33];
        Arrays.fill(border,0,20, ' ');Arrays.fill(border,21,33, '-');
        System.out.println("                     Player "+player+"'s cup");
        int lefttoprint = 0;
        lefttoprint = score;
        System.out.println(border);
        for (int i=0;i<4;i++){
            System.out.print("                     |");
            for (int j =0; j<10; j++) {
                if (lefttoprint != 0) {
                    System.out.print('*');
                    lefttoprint--;
                } else
                    System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(border);
    }

    public void drawLine(int slotNum){
        if((slotNum)<9)
            System.out.print((slotNum+1)+" -|");
        else
             System.out.print((slotNum + 1)+"-|");
        slots.get(slotNum).drawSlot();
        for(int i = 50; i>= slots.get(slotNum).getNumStones() + slots.get(12 - slotNum + (int)shiftValue).getNumStones(); i--)
            System.out.print(' ');
        slots.get(12 - slotNum + (int) shiftValue).drawSlot(); //The slot opposing slotNum (on the same line)
        System.out.println("|- " +(6 - slotNum + (int)((18*shiftValue)/11)));
    }

}
